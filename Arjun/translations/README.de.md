# 🔍 Arjun – verborgene Parameter ermitteln

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

```
   █████╗ ██████╗      ██╗██╗   ██╗███╗   ██╗
  ██╔══██╗██╔══██╗     ██║██║   ██║████╗  ██║
  ███████║██████╔╝     ██║██║   ██║██╔██╗ ██║
  ██╔══██║██╔══██╗██   ██║██║   ██║██║╚██╗██║
  ██║  ██║██║  ██║╚█████╔╝╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚═╝  ╚═══╝
         Werkzeugsammlung zur Ermittlung von HTTP-Parametern
```

<p align="center">
  <img src="https://img.shields.io/badge/Arjun-Parameter_Discovery-blue?style=for-the-badge" alt="Arjun">
  <img src="https://img.shields.io/badge/Hidden_Params-green?style=for-the-badge" alt="Hidden">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Arjun?](#-was-ist-arjun)
- [Installation](#-installation)
- [Grundlegende Verwendung](#-grundlegende-verwendung)
- [Erfassungsmodi](#-erfassungsmodi)
- [Erweiterte Optionen](#-erweiterte-optionen)
- [Ausgabeoptionen](#-ausgabeoptionen)
- [Kurzreferenz](#-kurzreferenz)

---

## 🎯 Was ist Arjun?

**Arjun** ermittelt verborgene HTTP-Parameter in Webanwendungen:

- 🔍 **Parametererfassung** – Verborgene GET-/POST-Parameter finden
- 🎯 **JSON-Unterstützung** – JSON-Parameter ermitteln
- 📡 **Mehrere Methoden** – Modi für GET, POST und JSON
- ⚡ **Schnell** – Viele Anfragen parallel ausführen

### Warum Arjun?

| Eigenschaft | Nutzen |
|---------|---------|
| Verborgene Parameter | Nicht verlinkte Endpunkte finden laut Vorlage |
| Mehrere Formate | GET, POST, JSON |
| Passiver Modus | JavaScript-Dateien prüfen laut Vorlage |
| Import aus Burp | In vorhandene Arbeitsabläufe integrieren |

---

## 🚀 Installation

```bash
# Installation mit pip (laut Vorlage empfohlen)
pip3 install arjun

# Aus dem Quellcode
git clone https://github.com/s0md3v/Arjun.git
cd Arjun
python3 setup.py install

# Installation prüfen
arjun --help
```

---

## 📖 Grundlegende Verwendung

### Einzelne URL

```bash
# Einfache Parametererfassung
arjun -u https://example.com/endpoint

# Mit Ausgabedatei
arjun -u https://example.com/endpoint -o results.txt
```

### Mehrere URLs

```bash
# Aus einer Datei
arjun -i urls.txt

# Von der Standardeingabe
cat urls.txt | arjun --stdin
```

### HTTP-Methode angeben

```bash
# GET-Parameter (Standard)
arjun -u https://example.com/search -m GET

# POST-Parameter
arjun -u https://example.com/api -m POST

# JSON-Anfragetext
arjun -u https://example.com/api -m JSON
```

---

## 🔎 Erfassungsmodi

### GET-Parameter

```bash
# GET-Parameter ermitteln
arjun -u https://example.com/search?known=value -m GET

# Beispielausgabe:
# [param] id
# [param] user
# [param] debug
```

### POST-Parameter

```bash
# POST-Formularparameter ermitteln
arjun -u https://example.com/login -m POST

# Mit vorhandenen Daten
arjun -u https://example.com/login -m POST --include 'username=admin'
```

### JSON-Parameter

```bash
# Parameter im JSON-Anfragetext ermitteln
arjun -u https://example.com/api/v1/users -m JSON

# Mit grundlegenden JSON-Daten
arjun -u https://example.com/api -m JSON --include '{"known":"value"}'
```

### Kombinierte Erfassung

```bash
# Alle Methoden
arjun -u https://example.com/endpoint -m GET,POST,JSON
```

---

<a name="-erweiterte-optionen"></a>

## ⚙️ Erweiterte Optionen

### Eigene Wortliste

```bash
# Eigene Wortliste verwenden
arjun -u https://example.com -w /path/to/wordlist.txt

# Parameterliste aus SecLists verwenden
arjun -u https://example.com -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt
```

### Threads und Begrenzung der Anfragerate

```bash
# Threadzahl festlegen (Standard laut Vorlage: 2)
arjun -u https://example.com -t 10

# Anfragerate begrenzen (Anfragen pro Sekunde)
arjun -u https://example.com --rate-limit 10

# Verzögerung zwischen Anfragen (Sekunden)
arjun -u https://example.com --delay 1
```

### Eigene Header

```bash
# Header hinzufügen
arjun -u https://example.com --headers "Authorization: Bearer token"

# Mehrere Header
arjun -u https://example.com --headers "Cookie: session=abc;X-Custom: value"

# Aus einer Datei
arjun -u https://example.com -H headers.txt
```

### Bekannte Parameter einbeziehen

```bash
# Bekannte Parameter einbeziehen
arjun -u https://example.com/search --include 'q=test&page=1'

# Für POST
arjun -u https://example.com/api -m POST --include 'action=submit'
```

### Passiver Modus

```bash
# Laut Vorlage aus JavaScript-Dateien extrahieren
arjun -u https://example.com --passive

# Laut Vorlage werden JavaScript-Dateien auf mögliche Parameternamen geprüft
```

### Weiterleitungen deaktivieren

```bash
# Weiterleitungen nicht folgen
arjun -u https://example.com --disable-redirects
```

### Zeitlimit

```bash
# Zeitlimit festlegen (Sekunden)
arjun -u https://example.com --timeout 15
```

### Stabiler Modus

```bash
# Laut Vorlage zuverlässiger (langsamer)
arjun -u https://example.com --stable
```

---

## 📤 Ausgabeoptionen

### Ausgabeformate

```bash
# Textausgabe (Standard laut Vorlage)
arjun -u https://example.com -oT results.txt

# JSON-Ausgabe
arjun -u https://example.com -oJ results.json

# Beide Formate
arjun -u https://example.com -oT text.txt -oJ json.json
```

### Beispiel einer JSON-Ausgabe

```json
{
  "https://example.com/api": {
    "method": "GET",
    "params": [
      {"name": "id", "reason": "body differs"},
      {"name": "debug", "reason": "body differs"}
    ]
  }
}
```

### Import aus Burp

```bash
# Burp-Anfragen importieren
arjun --import burp_export.xml

# Burp-Datei verarbeiten
arjun -i burp_requests.txt --burp
```

---

## 📊 Kurzreferenz

### Grundlegende Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `arjun -u URL` | Parameter ermitteln |
| `arjun -u URL -m POST` | POST-Methode |
| `arjun -u URL -m JSON` | JSON-Anfragetext |
| `arjun -i file.txt` | Mehrere URLs |
| `arjun -u URL -oJ out.json` | JSON-Ausgabe |

### Methodenoptionen

| Option | Beschreibung |
|------|-------------|
| `-m GET` | GET-Parameter |
| `-m POST` | POST-Formulardaten |
| `-m JSON` | JSON-Anfragetext |
| `-m GET,POST` | Mehrere Methoden |

### Leistungsoptionen

| Option | Beschreibung |
|------|-------------|
| `-t 10` | Threads |
| `--rate-limit 10` | Anfragen pro Sekunde |
| `--delay 1` | Verzögerung zwischen Anfragen |
| `--timeout 15` | Zeitlimit für Anfragen |

### Optionen zur Parametererfassung

| Option | Beschreibung |
|------|-------------|
| `-w wordlist.txt` | Eigene Wortliste |
| `--include 'a=b'` | Parameter einbeziehen |
| `--passive` | Aus JavaScript extrahieren laut Vorlage |
| `--stable` | Zuverlässiger Modus laut Vorlage |

---

## 🎯 Bug-Bounty-Arbeitsabläufe

### Einfache Parametererfassung

```bash
# Schnelle Parametererfassung
arjun -u https://target.com/api/endpoint -m GET,POST,JSON -oJ params.json
```

### Mit eigener Wortliste

```bash
# Umfassende Parametererfassung
arjun -u https://target.com/admin \
  -m GET,POST \
  -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
  -t 10 \
  --rate-limit 20 \
  -oJ discovered_params.json
```

### Tests mit Authentifizierung

```bash
# Mit Sitzung
arjun -u https://target.com/dashboard \
  --headers "Cookie: session=abc123;Authorization: Bearer token" \
  -m GET,POST,JSON \
  -oJ auth_params.json
```

### Viele Ziele erfassen

```bash
# Mehrere Endpunkte
cat endpoints.txt | arjun --stdin -m GET,POST -t 5 -oJ all_params.json

# Alternativ mit einer Datei
arjun -i endpoints.txt -m GET,POST -t 5 -oJ results.json
```

### Integration in Verarbeitungsketten

```bash
# Endpunkte finden und anschließend Parameter ermitteln
gau target.com | grep -E "\.php|\.asp" | httpx -silent | \
  arjun --stdin -m GET,POST -oJ params.json

# Katana + Arjun
katana -u https://target.com -d 3 | \
  grep -E "\.(php|asp|aspx|jsp)$" | \
  arjun --stdin -m GET,POST
```

### Gefundene Parameter prüfen

```bash
# Nach der Erfassung auf Schwachstellen prüfen
# 1. Parameter ermitteln
arjun -u https://target.com/search -oJ params.json

# 2. Parameternamen extrahieren
cat params.json | jq -r '.[].params[].name'

# 3. Mit ffuf prüfen
ffuf -u "https://target.com/search?FUZZ=test" -w params.txt
```

---

## 💡 Praktische Tipps

1. **Passiv beginnen** – Laut Vorlage zuerst JavaScript-Dateien prüfen
2. **Geeignete Wortlisten verwenden** – Die Vorlage empfiehlt die Burp-Parameterliste
3. **Anfragerate begrenzen** – Laut Vorlage, um Blockierungen zu vermeiden
4. **Alle Methoden prüfen** – GET, POST und JSON
5. **Header prüfen** – X-Custom-Param, X-Debug usw.
6. **Authentifiziert testen** – Bereiche nach der Anmeldung prüfen

### Häufige verborgene Parameter

```
debug, test, admin
id, user_id, uid
token, api_key, key
callback, redirect, url
format, output, type
page, limit, offset
sort, order, filter
```

---

## 📚 Ressourcen

- [Arjun GitHub](https://github.com/s0md3v/Arjun)
- [Burp-Parameterliste](https://github.com/danielmiessler/SecLists/blob/master/Discovery/Web-Content/burp-parameter-names.txt)

### Verwandte Spickzettel
- [Bug-Bounty-Methodik](../../Bug-Bounty-Methodology/translations/README.de.md)
- [ffuf](../../ffuf/translations/README.de.md)
- [Burp Suite](../../Burp-Suite/translations/README.de.md)

---

<p align="center">
  <b>🔍 Verborgene Parameter systematisch ermitteln!</b><br>
  <i>Arjun – Werkzeug zur Parametersuche</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Optionen und Versionen:** Die Vorlage mischt abweichende CLI-Angaben. Im geprüften offiziellen Parser sind `-o` und `-oJ` JSON-Ausgaben; die Dateiendung `.txt` macht daraus keine Textausgabe. `--stdin`, `--import`, `--burp`, `-H`, `--delay` und `--timeout` stehen dort nicht als Optionen. Import erfolgt über `-i`; Verzögerung und Zeitlimit haben eigene Kurzoptionen. Die Thread-Vorgabe der geprüften Implementierung ist 5, während die ältere Wiki-Anleitung 2 nennt. Die Originalaufrufe bleiben unverändert. [Arjun: CLI-Parser](https://github.com/s0md3v/Arjun/blob/master/arjun/__main__.py).

> **Passive Quellen und Header:** `--passive` sammelt Kandidaten aus Quellen wie Common Crawl, OTX und Wayback und ergänzt die Wortliste; anschließend werden Parameter am Ziel geprüft. Es ist kein allgemeiner Schalter zum reinen Prüfen von JavaScript-Dateien. Die Dokumentation trennt mehrere Header durch einen Zeilenumbruch; die gezeigten Semikolons erzeugen nicht automatisch getrennte HTTP-Header. `--stable` beziehungsweise eine Verzögerung begrenzen die Parallelität, garantieren aber keine fehlende Blockierung. [Offizielle Verwendung](https://github.com/s0md3v/Arjun/wiki/Usage).

> **Formate und Ergebnisgrenzen:** Das JSON-Beispiel ist eine didaktische Vorlage. Die geprüfte Implementierung exportiert `params` als Liste von Namen und zusätzlich Header, nicht zwingend als Objekte mit `name` und `reason`; der gezeigte jq-Ausdruck muss zum tatsächlichen Schema passen. Die Ausgabe der jq-Zeile wird außerdem nicht in die später vorausgesetzte Datei `params.txt` geschrieben. Ein gefundener Parameter ist kein Nachweis einer Schwachstelle oder eines neuen Endpunkts. Befehle, JSON-Werte und Ausgabe-Literale wurden weder geändert noch ausgeführt. [Arjun: Ergebnisaufbau](https://github.com/s0md3v/Arjun/blob/master/arjun/__main__.py), [Exporter](https://github.com/s0md3v/Arjun/blob/master/arjun/core/exporter.py).
