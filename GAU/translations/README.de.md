# 🔗 GAU – Get All URLs

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗  █████╗ ██╗   ██╗
  ██╔════╝ ██╔══██╗██║   ██║
  ██║  ███╗███████║██║   ██║
  ██║   ██║██╔══██║██║   ██║
  ╚██████╔╝██║  ██║╚██████╔╝
   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ 
       Get All URLs – schnelle Sammlung von URLs
```

<p align="center">
  <img src="https://img.shields.io/badge/GAU-URL_Discovery-blue?style=for-the-badge" alt="GAU">
  <img src="https://img.shields.io/badge/Wayback-green?style=for-the-badge" alt="Wayback">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist GAU?](#-was-ist-gau)
- [Installation](#-installation)
- [Grundlegende Verwendung](#-grundlegende-verwendung)
- [Datenquellen](#-datenquellen)
- [Filterung](#-filterung)
- [Integration](#-integration)
- [Kurzreferenz](#-kurzreferenz)

---

## 🎯 Was ist GAU?

**GAU** (Get All URLs) sammelt bekannte URLs aus mehreren Quellen:

- 🔍 **Wayback Machine** - Historische URLs
- 🌐 **Common Crawl** - Daten aus Web-Crawls
- 📡 **AlienVault OTX** - URLs aus Bedrohungsinformationen
- 🔒 **URLScan.io** - Bereits untersuchte URLs

### Warum GAU?

| Vorteil | Beschreibung |
|---------|-------------|
| **Passive Aufklärung** | Kein direkter Kontakt mit dem Ziel |
| **Historische Daten** | Alte oder vergessene Endpunkte finden |
| **Schnell** | Paralleler Abruf |
| **Mehrere Quellen** | Umfassende Ergebnisse |

---

## 🚀 Installation

```bash
# Mit Go installieren (laut Vorlage empfohlen)
go install github.com/lc/gau/v2/cmd/gau@latest

# Aus dem Quellcode
git clone https://github.com/lc/gau.git
cd gau/cmd/gau
go build

# Installation prüfen
gau --version
```

---

## 📖 Grundlegende Verwendung

### Einzelne Domain

```bash
# Grundlegende Verwendung
gau example.com

# Mit Ausgabedatei
gau example.com -o urls.txt

# Stiller Modus ohne Banner laut Vorlage
gau --subs example.com
```

### Mehrere Domains

```bash
# Von der Standardeingabe
echo "example.com" | gau

# Mehrere Domains
cat domains.txt | gau

# Aus einer Datei
gau --threads 5 < domains.txt
```

### Subdomains einschließen

```bash
# Subdomains in die Ergebnisse aufnehmen
gau --subs example.com
```

### Parallelität

```bash
# Threads festlegen (Standard laut Vorlage: 1)
gau --threads 5 example.com

# An Anfrageratenbegrenzungen anpassen
gau --threads 2 example.com
```

---

## 🌐 Datenquellen

### Verfügbare Datenquellen

| Datenquelle | Beschreibung | Standard |
|----------|-------------|---------|
| `wayback` | Wayback Machine | ✅ |
| `commoncrawl` | Common Crawl | ✅ |
| `otx` | AlienVault OTX | ✅ |
| `urlscan` | URLScan.io | ✅ |

### Datenquellen auswählen

```bash
# Nur bestimmte Datenquellen verwenden
gau --providers wayback example.com
gau --providers wayback,otx example.com

# Datenquelle ausschließen laut Vorlage
gau --blacklist commoncrawl example.com
```

### Konfiguration der Datenquellen

```bash
# Speicherort der Konfigurationsdatei
~/.gau.toml

# Konfigurationsbeispiel
cat > ~/.gau.toml << EOF
threads = 5
verbose = false
retries = 3
subdomains = false
providers = ["wayback","commoncrawl","otx","urlscan"]
blacklist = []
json = false
EOF
```

---

## 🔍 Filterung

### Nach Statuscode filtern

```bash
# Nur URLs mit dem erfassten Statuscode 200
gau --mc 200 example.com

# Mehrere Statuscodes
gau --mc 200,301,302 example.com

# Bestimmte Statuscodes ausschließen
gau --fc 404,500 example.com
```

### Nach Dateiendung filtern

```bash
# Dateiendungen auswählen laut Vorlage
gau --mt "\.js$" example.com
gau --mt "\.php$" example.com

# Dateiendungen ausschließen laut Vorlage
gau --ft "\.jpg$|\.png$|\.gif$|\.css$" example.com
```

### Nach MIME-Typ filtern

```bash
# Nach Inhaltstyp filtern
gau --mt "application/json" example.com
gau --mt "text/html" example.com
```

### Ausgabeformat

```bash
# JSON-Ausgabe
gau --json example.com

# Ausführliche Ausgabe
gau --verbose example.com
```

---

## 🔗 Integration

### Mit httpx

```bash
# Erreichbarkeit der URLs prüfen
gau example.com | httpx -silent

# Mit Statuscodes
gau example.com | httpx -sc -title
```

### Mit grep und Parameterextraktion

```bash
# URLs mit Parametern finden
gau example.com | grep "="

# Bestimmte Muster finden
gau example.com | grep -E "\.php\?|\.asp\?"

# GF-Muster
gau example.com | gf xss
gau example.com | gf sqli
gau example.com | gf lfi
gau example.com | gf redirect
```

### Mit urldedupe

```bash
# Duplikate entfernen
gau example.com | urldedupe

# Alternative: sort -u
gau example.com | sort -u
```

### Mit uro

```bash
# Intelligente Entfernung mehrfacher URL-Varianten
gau example.com | uro
```

### Vollständige Verarbeitungskette

```bash
# Vollständige Verarbeitungskette zur URL-Sammlung
echo "example.com" | gau --subs --threads 5 | \
  uro | \
  httpx -silent -mc 200 | \
  grep "=" > params.txt
```

---

## 📊 Kurzreferenz

### Grundlegende Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `gau domain.com` | URLs zur Domain abrufen |
| `gau --subs domain.com` | Subdomains einschließen |
| `gau -o urls.txt domain.com` | In eine Datei schreiben |
| `gau --threads 5 domain.com` | Mit mehreren Threads |

### Filteroptionen

| Option | Beschreibung |
|------|-------------|
| `--mc 200` | Statuscode auswählen |
| `--fc 404` | Statuscode ausschließen |
| `--mt "regex"` | Regulären Ausdruck auswählen laut Vorlage |
| `--ft "regex"` | Regulären Ausdruck ausschließen laut Vorlage |
| `--json` | JSON-Ausgabe |

### Optionen der Datenquellen

| Option | Beschreibung |
|------|-------------|
| `--providers wayback,otx` | Bestimmte Datenquellen verwenden |
| `--blacklist commoncrawl` | Datenquelle ausschließen laut Vorlage |

### Nützliche Einzeiler

```bash
# Alle JS-Dateien finden
gau example.com | grep -E "\.js$" | sort -u

# API-Endpunkte finden
gau example.com | grep -iE "/api/|/v[0-9]/"

# Parameter finden
gau example.com | grep "?" | grep "=" | sort -u

# Mögliche SQLi-Kandidaten finden
gau example.com | gf sqli

# Mögliche XSS-Kandidaten finden
gau example.com | gf xss

# Erreichbare URLs mit Parametern
gau example.com | grep "=" | httpx -silent
```

---

## 🎯 Bug-Bounty-Arbeitsablauf

```bash
# 1. Alle URLs sammeln
gau --subs --threads 5 target.com > all_urls.txt

# 2. Interessante URLs auswählen
cat all_urls.txt | grep "=" | uro > params.txt

# 3. Erreichbarkeit prüfen
cat params.txt | httpx -silent > live_params.txt

# 4. Schwachstellen suchen laut Vorlage
cat live_params.txt | gf sqli > sqli.txt
cat live_params.txt | gf xss > xss.txt
cat live_params.txt | gf lfi > lfi.txt
cat live_params.txt | gf redirect > redirect.txt

# 5. Prüfen
cat xss.txt | qsreplace '"><script>alert(1)</script>' | \
  httpx -silent -mc 200
```

---

## 📚 Ressourcen

- [GAU GitHub](https://github.com/lc/gau)
- [GF Patterns](https://github.com/1ndianl33t/Gf-Patterns)

### Verwandte Spickzettel
- [Bug-Bounty-Methodik](../../Bug-Bounty-Methodology/translations/README.de.md)
- [Subfinder](../../Subfinder/translations/README.de.md)
- [httpx](../../httpx/translations/README.de.md)

---

<p align="center">
  <b>🔗 URLs systematisch sammeln!</b><br>
  <i>GAU – passive Ermittlung von URLs</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Optionskorrekturen:** `--subs` schließt Subdomains ein; es ist kein Schalter für einen stillen Modus. `--blacklist` schließt Dateiendungen aus, keine Datenquellen. `--mt` und `--ft` erwarten MIME-Typen, keine allgemeinen regulären Ausdrücke für Dateiendungen. Die entsprechenden Originalbeispiele und Tabellenwerte bleiben unverändert. [Offizielle GAU-Referenz](https://github.com/lc/gau#usage).

> **Archivdaten und Aktualität:** Status- und MIME-Filter beziehen sich auf Informationen der Datenquelle, nicht automatisch auf eine neue HTTP-Prüfung des Zielsystems. Historische Ergebnisse beweisen keine aktuelle Erreichbarkeit oder vollständige Abdeckung. Welche Daten verfügbar sind, hängt von Quelle und Zeitpunkt ab. [GAU: Wayback-Implementierung](https://github.com/lc/gau/blob/master/pkg/providers/wayback/wayback.go).

> **Verarbeitungsketten:** `gf` wählt Musterkandidaten aus; es bestätigt keine SQL-Injection, XSS oder offene Weiterleitung. Ebenso beweist Status 200 nach dem Einsetzen einer XSS-Payload keine Ausführung im Browser. Nachgeschaltete Werkzeuge wie httpx kontaktieren Ziele aktiv. Dateipfade, Konfiguration und Eingaben setzen die passende Umgebung voraus. Befehle, TOML-Werte und Queries wurden unverändert belassen; kein Beispiel wurde ausgeführt.
