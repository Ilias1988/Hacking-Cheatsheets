# ⚔️ Katana - Web-Crawler der nächsten Generation

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗  ██╗ █████╗ ████████╗ █████╗ ███╗   ██╗ █████╗ 
  ██║ ██╔╝██╔══██╗╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗
  █████╔╝ ███████║   ██║   ███████║██╔██╗ ██║███████║
  ██╔═██╗ ██╔══██║   ██║   ██╔══██║██║╚██╗██║██╔══██║
  ██║  ██╗██║  ██║   ██║   ██║  ██║██║ ╚████║██║  ██║
  ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝
         Framework für Web-Crawling der nächsten Generation
```

<p align="center">
  <img src="https://img.shields.io/badge/Katana-ProjectDiscovery-blue?style=for-the-badge" alt="Katana">
  <img src="https://img.shields.io/badge/Web_Crawler-green?style=for-the-badge" alt="Crawler">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Katana?](#-was-ist-katana)
- [Installation](#-installation)
- [Grundlegende Verwendung](#-grundlegende-verwendung)
- [Crawling-Optionen](#-crawling-optionen)
- [Headless-Modus](#-headless-modus)
- [Ausgabe und Filterung](#-ausgabe-und-filterung)
- [Kurzreferenz](#-kurzreferenz)

---

## 🎯 Was ist Katana?

**Katana** ist ein Framework für Web-Crawling von ProjectDiscovery:

- ⚡ **Schnell** – in Go geschrieben, mit hoher Parallelität
- 🔍 **JavaScript-Rendering** – Unterstützung eines Browsers ohne grafische Oberfläche
- 📡 **Moderne Webanwendungen** – unterstützt SPAs und dynamische Inhalte
- 🎯 **Auf Bug Bounty ausgerichtet** – für Sicherheitstests entwickelt

### Warum Katana?

| Eigenschaft | Nutzen |
|---------|---------|
| Chrome ohne grafische Oberfläche | Anwendungen mit hohem JavaScript-Anteil erkunden |
| Eigene Header | Crawling mit Authentifizierung |
| Prüfumfang begrenzen | Innerhalb des vereinbarten Prüfumfangs bleiben |
| Formulare ausfüllen | Weitere Endpunkte finden |

---

## 🚀 Installation

```bash
# Mit Go installieren (laut Vorlage empfohlen)
go install github.com/projectdiscovery/katana/cmd/katana@latest

# Docker
docker pull projectdiscovery/katana

# Installation prüfen
katana -version
```

---

## 📖 Grundlegende Verwendung

### Einzelnes Ziel

```bash
# Einfaches Crawling
katana -u https://example.com

# Ausgabe speichern
katana -u https://example.com -o urls.txt

# Reduzierte Ausgabe
katana -u https://example.com -silent
```

### Mehrere Ziele

```bash
# Aus der Standardeingabe
echo "https://example.com" | katana

# Mehrere URLs
cat targets.txt | katana

# Aus einer Datei
katana -list targets.txt
```

### Crawling-Tiefe

```bash
# Tiefe festlegen (Standard laut Vorlage: 3)
katana -u https://example.com -d 5

# Tiefe 1 (laut Vorlage nur die Zielseite)
katana -u https://example.com -d 1
```

---

<a name="-crawling-optionen"></a>

## 🕷️ Crawling-Optionen

### Prüfumfang begrenzen

```bash
# Nur innerhalb des Prüfumfangs crawlen (dieselbe Domain laut Vorlage)
katana -u https://example.com -fs dn

# Optionen zum Prüfumfang:
# dn – Domainname
# rdn – Stammdomain
# fqdn – vollständig qualifizierter Domainname

# Prüfumfang anhand eines Domainfelds
katana -u https://example.com -fs rdn
```

### Crawling-Strategie

```bash
# Tiefensuche (Standard laut Vorlage)
katana -u https://example.com -cs depth-first

# Breitensuche
katana -u https://example.com -cs breadth-first
```

### Parallelität der Anfragen

```bash
# Gleichzeitige Anfragen (Standard laut Vorlage: 10)
katana -u https://example.com -c 50

# Anfragerate begrenzen (Anfragen pro Sekunde)
katana -u https://example.com -rl 100

# Parallelität der Eingabeziele
katana -u https://example.com -p 10
```

### Crawling-Dauer

```bash
# Zeitlimit festlegen (Sekunden)
katana -u https://example.com -timeout 30

# Maximale Crawling-Dauer
katana -u https://example.com -ct 300
```

### Automatisches Ausfüllen von Formularen

```bash
# Automatisches Ausfüllen von Formularen aktivieren
katana -u https://example.com -aff

# Mit eigenen Werten
katana -u https://example.com -aff -fv "username:admin,password:test123"
```

---

## 🌐 Headless-Modus

### Headless-Browser aktivieren

```bash
# Chrome ohne grafische Oberfläche aktivieren
katana -u https://example.com -headless

# Headless-Modus mit Tiefenbegrenzung
katana -u https://example.com -headless -d 3
```

### Headless-Optionen

```bash
# Eigener Chrome-Pfad laut Vorlage
katana -u https://example.com -headless -system-chrome

# Ohne Sandbox (laut Vorlage für root)
katana -u https://example.com -headless -no-sandbox

# Bildschirmaufnahmen der Seiten erstellen
katana -u https://example.com -headless -screenshot
```

### JavaScript-Crawling

```bash
# Auf das Laden von JavaScript warten (laut Vorlage in Millisekunden)
katana -u https://example.com -headless -timeout 5

# JavaScript-Endpunkte erfassen
katana -u https://example.com -js-crawl
```

---

## 📤 Ausgabe und Filterung

### Ausgabeformate

```bash
# Normale Ausgabe in eine Datei schreiben
katana -u https://example.com -o results.txt

# JSON-Ausgabe
katana -u https://example.com -jsonl -o results.json

# Keine Farben
katana -u https://example.com -nc
```

### Ausgabefelder

```bash
# Bestimmte Felder anzeigen
katana -u https://example.com -f url,method,status

# Verfügbare Felder laut Vorlage:
# url, path, fqdn, rdn, rurl, qurl, qpath
# file, kv, dir, ufile, udir
# method, status, body, response, header
```

### Nach Dateiendung filtern

```bash
# Dateiendungen auswählen
katana -u https://example.com -em js,php,asp

# Dateiendungen ausschließen
katana -u https://example.com -ef png,jpg,gif,css,svg,woff
```

### Nach Statuscode filtern

```bash
# Statuscode auswählen
katana -u https://example.com -mc 200,301,302

# Statuscode ausschließen
katana -u https://example.com -fc 404,500
```

### Eigene Header

```bash
# Header hinzufügen
katana -u https://example.com -H "Authorization: Bearer TOKEN"

# Mehrere Header
katana -u https://example.com -H "Cookie: session=abc" -H "User-Agent: Custom"
```

### Proxy-Unterstützung

```bash
# HTTP-Proxy
katana -u https://example.com -proxy http://127.0.0.1:8080

# Für Burp Suite
katana -u https://example.com -proxy http://127.0.0.1:8080
```

---

## 📊 Kurzreferenz

### Grundlegende Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `katana -u URL` | Einzelne URL erkunden |
| `katana -list file.txt` | Ziele aus einer Datei erkunden |
| `katana -u URL -d 5` | Tiefe auf 5 setzen |
| `katana -u URL -headless` | Headless-Modus aktivieren |
| `katana -u URL -o out.txt` | Ergebnisse speichern |

### Optionen zum Prüfumfang

| Option | Beschreibung |
|------|-------------|
| `-fs dn` | Dieselbe Domain |
| `-fs rdn` | Stammdomain |
| `-fs fqdn` | Vollständiger Domainname |
| `-cs depth-first` | Tiefensuche |
| `-cs breadth-first` | Breitensuche |

### Leistungsoptionen

| Option | Beschreibung |
|------|-------------|
| `-c 50` | Parallelität der Anfragen |
| `-p 10` | Parallelität der Eingabeziele |
| `-rl 100` | Begrenzung der Anfragerate |
| `-timeout 30` | Zeitlimit pro Anfrage |

### Filteroptionen

| Option | Beschreibung |
|------|-------------|
| `-em js,php` | Dateiendungen auswählen |
| `-ef png,jpg` | Dateiendungen ausschließen |
| `-mc 200` | Statuscodes auswählen |
| `-fc 404` | Statuscodes ausschließen |

---

## 🎯 Bug-Bounty-Arbeitsabläufe

### Einfaches Crawling

```bash
# Schnelles Crawling
katana -u https://target.com -d 3 -o urls.txt
```

### Umfassendes Crawling

```bash
# Tiefes Crawling mit JavaScript
katana -u https://target.com -d 5 -headless -c 50 \
  -ef png,jpg,gif,css,svg,woff,woff2,ttf,eot,ico \
  -o all_urls.txt
```

### Parameter ermitteln

```bash
# URLs mit Parametern finden
katana -u https://target.com -d 3 | grep "?" | sort -u

# Mit grep nach bestimmten Parametern suchen
katana -u https://target.com | grep -E "id=|user=|admin=|file="
```

### Mit anderen Werkzeugen kombinieren

```bash
# Katana + httpx
katana -u https://target.com -d 3 | httpx -silent -sc

# Katana + Nuclei
katana -u https://target.com -d 3 | nuclei -t nuclei-templates/

# Vollständige Verarbeitungskette
subfinder -d target.com | httpx -silent | katana -d 3 | grep "=" | sort -u
```

### Crawling mit Authentifizierung

```bash
# Mit Sitzungscookie
katana -u https://target.com \
  -H "Cookie: session=abc123" \
  -d 5 \
  -headless \
  -o authenticated_urls.txt
```

---

## 📚 Ressourcen

- [Katana GitHub](https://github.com/projectdiscovery/katana)
- [ProjectDiscovery](https://projectdiscovery.io/)

### Verwandte Spickzettel
- [Bug-Bounty-Methodik](../../Bug-Bounty-Methodology/translations/README.de.md)
- [httpx](../../httpx/translations/README.de.md)
- [Nuclei](../../Nuclei/translations/README.de.md)
- [GAU](../../GAU/translations/README.de.md)

---

<p align="center">
  <b>⚔️ Webinhalte systematisch erkunden!</b><br>
  <i>Katana - Web-Crawler der nächsten Generation</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Optionen und Versionen:** Die Vorlage mischt CLI-Angaben. `-cs` bezeichnet in der offiziellen Referenz den Regex-basierten Crawl-Scope, nicht die Suchstrategie; dafür ist eine gesonderte Strategieoption vorgesehen. `-timeout` ist ein Anfragezeitlimit in Sekunden, keine Wartezeit auf JavaScript in Millisekunden. `-system-chrome` wählt den lokal installierten Browser; es ist keine Pfadangabe. Die dokumentierte Pfadoption ist separat. Die Originalaufrufe bleiben unverändert. [ProjectDiscovery: Katana-Referenz](https://github.com/projectdiscovery/katana), [offizielle Einführung](https://projectdiscovery.io/blog/introducing-katana-the-best-cli-web-crawler).

> **Formulare und Ausgabe:** Automatisches Ausfüllen ist versionsabhängig und wurde als experimentelle Funktion dokumentiert. Eigene Formwerte werden in der offiziellen Einführung über eine Konfigurationsdatei beschrieben; `-fv` ist dort keine dokumentierte Option. Die Schalter `-screenshot` und `-mc` stehen nicht in der geprüften aktuellen CLI-Liste; `-fc` bezeichnet dort die Formularkonfiguration, keinen Statuscodefilter. `-f` ist inzwischen als veraltet markiert und akzeptiert die dokumentierten Felder, nicht pauschal alle Felder der Beispielsammlung. JSONL enthält mehrere JSON-Datensätze; ein Dateiname mit `.json` ändert dieses Format nicht. [Katana: aktuelle Optionen und Ausgabe](https://github.com/projectdiscovery/katana).

> **Prüfumfang und Ergebnisse:** Root-Domain, vollständiger Hostname und Regex-Scope sind unterschiedliche Begrenzungen. Tiefenbegrenzung, Headless-Modus und hohe Parallelität garantieren weder vollständige Abdeckung noch fehlende Erkennung. JavaScript-Parsing und das Ausführen einer Seite im Browser sind unterschiedliche Verfahren. Eine gefundene URL mit Parametern belegt keine Schwachstelle; an nachgeschaltete Werkzeuge muss das tatsächliche Ausgabeformat passen. Alle Befehle, Headerwerte, Pfade und Queries wurden beibehalten; kein Crawling und kein Beispiel wurde ausgeführt.
