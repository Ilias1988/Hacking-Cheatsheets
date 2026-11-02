# ⚡ OWASP ZAP – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ____  _    _  ___   _____ _____    _____   ___  _____  
  / __ \| |  | |/ _ \ / ____|  __ \  |__  /  / _ \|  __ \ 
 | |  | | |  | | | | | (___ | |__) |   / /  / /_\ \ |__) |
 | |  | | |/\| | |_| |\___ \|  ___/   / /  |  _  |  ___/ 
 | |__| \  /\  / | | |____) | |      / /__ | | | | |     
  \____/ \/  \/|_| |_|_____/|_|     /_____|_| |_|_|     
                                                          
    Zed Attack Proxy – kostenloses Werkzeug für Sicherheitstests
```

<p align="center">
  <img src="https://img.shields.io/badge/OWASP-ZAP-blue?style=for-the-badge" alt="OWASP ZAP">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Websicherheit">
  <img src="https://img.shields.io/badge/Open-Source-green?style=for-the-badge" alt="Quelloffen">
  <img src="https://img.shields.io/badge/Free-Tool-purple?style=for-the-badge" alt="Kostenlos">
</p>

<p align="center">
  <b>🔒 Ein weit verbreiteter kostenloser Scanner für Websicherheit</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Übersicht der Oberfläche](#-übersicht-der-oberfläche)
- [Proxy-Konfiguration](#-proxy-konfiguration)
- [Manuelle Tests](#-manuelle-tests)
- [Spider und Crawler](#-spider-und-crawler)
- [Aktive Scans](#-aktive-scans)
- [Passive Scans](#-passive-scans)
- [Kontexte und Sitzungen](#-kontexte-und-sitzungen)
- [Fuzzer](#-fuzzer)
- [API und Automatisierung](#-api-und-automatisierung)
- [Kommandozeile](#-kommandozeile)
- [Add-ons](#-add-ons)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**OWASP ZAP** (Zed Attack Proxy) ist ein kostenloses, quelloffenes Werkzeug für Penetrationstests, das laut Vorlage von OWASP gepflegt wird. Es dient dazu, Sicherheitslücken in Webanwendungen zu finden.

### Warum ZAP?

| Eigenschaft | Beschreibung |
|---------|-------------|
| **Kostenlos und quelloffen** | Keine Lizenzkosten, von der Community getragen |
| **Einfach zu bedienen** | Für Einsteiger geeignet und zugleich leistungsfähig |
| **Automatisierte Scans** | Spider sowie aktive und passive Scans |
| **Manuelle Tests** | Abfangender Proxy und Fuzzer |
| **API-Unterstützung** | Umfassende REST-API zur Automatisierung |
| **Plattformübergreifend** | Windows, Linux, macOS, Docker |
| **Erweiterbar** | Marketplace mit über 100 Add-ons laut Vorlage |

### ZAP vs Burp Suite

| Eigenschaft | ZAP | Burp Suite |
|---------|-----|------------|
| **Preis** | Kostenlos | $449+/Jahr |
| **Quelloffen** | ✅ Ja | ❌ Nein |
| **Automatisierte Scans** | ✅ Vollständig | Nur Pro |
| **API-Zugriff** | ✅ Kostenlos | Nur Pro |
| **Aktive Entwicklung** | ✅ Ja | ✅ Ja |
| **Community** | OWASP | PortSwigger |

---

## 📥 Installation

### Windows
```bash
# Von der offiziellen Website herunterladen
https://www.zaproxy.org/download/

# Alternativ Chocolatey verwenden
choco install owasp-zap
```

### Linux (Kali/Ubuntu)
```bash
# Kali (vorinstalliert)
zaproxy

# Ubuntu/Debian
sudo apt install zaproxy

# Snap
sudo snap install zaproxy --classic
```

### macOS
```bash
# Homebrew
brew install --cask owasp-zap

# DMG von der offiziellen Website herunterladen
```

### Docker
```bash
# Aktuelles Image herunterladen
docker pull ghcr.io/zaproxy/zaproxy:stable

# ZAP im Daemon-Modus starten
docker run -u zap -p 8080:8080 -i ghcr.io/zaproxy/zaproxy:stable zap.sh -daemon -host 0.0.0.0 -port 8080

# Mit grafischer Oberfläche starten (Linux)
docker run -u zap -p 8080:8080 -p 8090:8090 -v /tmp:/zap/wrk/:rw \
    --rm -it ghcr.io/zaproxy/zaproxy:stable zap-webswing.sh
```

### Installation prüfen
```bash
# ZAP starten
zaproxy
zap.sh       # Linux
zap.bat      # Windows
```

---

<a name="-übersicht-der-oberfläche"></a>

## 🖥️ Übersicht der Oberfläche

### Hauptbereiche

| Bereich | Beschreibung |
|-------|-------------|
| **Sites Tree** | Hierarchische Ansicht der besuchten Websites |
| **History** | Alle Anfragen und Antworten |
| **Alerts** | Gefundene Schwachstellen |
| **Active Scan** | Aktueller Scan-Fortschritt |
| **Spider** | Erfasste URLs |
| **Search** | Alle Daten durchsuchen |
| **Request/Response** | Einzelheiten anzeigen |

### Symbole der Werkzeugleiste

| Symbol | Funktion |
|------|----------|
| 🌐 | Scan über Quick Start |
| 🔍 | Spider |
| ⚡ | Active Scan |
| 🛑 | Break/Intercept |
| 📊 | Reports |
| ⚙️ | Options |

### Betriebsmodi

| Modus | Beschreibung |
|------|-------------|
| **Safe** | Keine potenziell gefährlichen Aktionen |
| **Protected** | Nur Ziele innerhalb des festgelegten Umfangs scannen |
| **Standard** | Scans ohne Einschränkung (Standard laut Vorlage) |
| **ATTACK** | Aggressive Scans laut Vorlage |

---

## 🔌 Proxy-Konfiguration

### Browser konfigurieren

**Standardproxy von ZAP:**
```
Host: localhost (127.0.0.1)
Port: 8080
```

### Firefox einrichten
```
1. Im Browser: Settings → Network Settings → Manual proxy
2. HTTP Proxy: 127.0.0.1  Port: 8080
3. „Also use this proxy for HTTPS“ aktivieren
4. Alternativ die Erweiterung FoxyProxy verwenden
```

### ZAP-Zertifikat importieren
```
1. Im Browser folgende Adresse öffnen: http://zap
2. Auf „Download ZAP Root CA Certificate“ klicken
3. In den Browser importieren:
   Firefox: Settings → Privacy → Certificates → Import
   Chrome: Settings → Security → Manage certificates
```

### Proxy-Optionen in ZAP konfigurieren
```
Tools → Options → Local Proxies

Einstellungen (Settings):
- Address: localhost
- Port: 8080
- Behind NAT: bei Bedarf aktivieren
- Remove unsupported encodings: aktivieren
```

### Vorgeschalteter Proxy (Proxy-Kette)
```
Tools → Options → Network → Connection

Proxy-Kette verwenden (Use proxy chain):
- Address: corporate-proxy.com
- Port: 8080
- Bei Bedarf Authentifizierung konfigurieren
```

---

## 🔧 Manuelle Tests

### Anfragen abfangen (Break)

```
1. In der Werkzeugleiste auf die grüne Schaltfläche „Break“ (🛑) klicken
2. Die Anwendung im Browser erkunden
3. Die abgefangene Anfrage verändern
4. Auf „Submit and Continue“ oder „Drop“ klicken

Abfangoptionen (Break):
- Alle Anfragen abfangen
- Alle Antworten abfangen
- Bei einem bestimmten URL-Muster abfangen
```

### Request Editor (Anfrageeditor)

```
Anfrage rechts anklicken → Open/Resend with Request Editor

Funktionen:
- Beliebige Teile der Anfrage verändern
- Header hinzufügen oder entfernen
- HTTP-Methode ändern
- Anfrage senden und Antwort anzeigen
```

### Manuelle Anfrage
```
Tools → Manual Request Editor

Alternativ: Ctrl+M

Eigene Anfragen von Grund auf erstellen
```

### Encode/Decode/Hash (Kodieren, Dekodieren, Hashen)
```
Tools → Encode/Decode/Hash

Unterstützte Funktionen:
- URL-Kodierung und -Dekodierung
- Base64-Kodierung und -Dekodierung
- HTML-Kodierung und -Dekodierung
- Hex-Kodierung und -Dekodierung
- Hashwerte mit MD5, SHA1 und SHA256 berechnen
```

### Suchfunktion
```
Im Reiter Search ein Suchmuster eingeben

Durchsuchbare Bereiche:
- URLs
- Anfragen
- Antworten
- Header
- Alle Bereiche
```

---

<a name="-spider-und-crawler"></a>

## 🕷️ Spider und Crawler

### Standard-Spider

```
Ziel rechts anklicken → Spider → Spider...

Alternativ: Ctrl+Alt+S

Optionen:
- Maximale Erkundungstiefe
- Maximale Anzahl untergeordneter Elemente
- Maximale Dauer
- Parameter verarbeiten
```

### Spider-Optionen
```
Tools → Options → Spider

Einstellungen (Settings):
- Max Depth: 5-10 (Standard laut Vorlage: 5)
- Max Children: 10
- Max Duration: 0 (unbegrenzt)
- Thread Count: 5
- Handle forms: Yes
- Post forms: Yes
```

### AJAX Spider

```
Für Anwendungen mit hohem JavaScript-Anteil:

Ziel rechts anklicken → AJAX Spider → AJAX Spider...

Benötigt einen Browser:
- Firefox (laut Vorlage empfohlen)
- Chrome
- HtmlUnit (ohne grafische Oberfläche)
```

### AJAX-Spider-Optionen
```
Tools → Options → AJAX Spider

Einstellungen (Settings):
- Browser: Firefox
- Max Crawl Depth: 10
- Max Crawl States: 0 (unbegrenzt)
- Max Duration: 60 minutes (60 Minuten)
- Standardelemente anklicken
- Elemente mit onclick usw. anklicken
```

---

## ⚡ Aktive Scans

### Aktiven Scan starten

```
Ziel rechts anklicken → Active Scan...

Alternativ: Ctrl+Alt+A

Konfigurieren:
- Starting Point (Startpunkt)
- Context (optional; Kontext)
- User (Benutzer für die Authentifizierung)
- Policy (Scan-Richtlinie)
- Technology (Technologie)
```

### Scan-Richtlinien

```
Analyze → Scan Policy Manager

Richtlinien erstellen oder ändern:
- Auszuführende Tests auswählen
- Threshold (Schwelle für verdächtige Befunde)
- Strength (Intensität der Tests)
```

### Richtlinienkategorien

| Kategorie | Tests |
|----------|-------|
| **Injection** | SQL, OS Command, LDAP |
| **Client Browser** | XSS, DOM XSS |
| **Server Security** | Einbindung entfernter Dateien |
| **Information** | Path Traversal und Offenlegung von Informationen |
| **Miscellaneous** | Vom Benutzer beeinflussbare Weiterleitungen |

### Scan-Stärke

| Stufe | Beschreibung |
|-------|-------------|
| **Low** | Wenige Anfragen, schnell |
| **Medium** | Ausgewogen |
| **High** | Viele Anfragen, gründlich |
| **Insane** | Maximale Anzahl von Tests (langsam) |

### Technologien auswählen
```
Ziel rechts anklicken → Technology → Select...

Scans nach Technologie filtern:
- PHP, ASP, JSP
- MySQL, PostgreSQL, Oracle
- Apache, IIS, Nginx
- Linux, Windows
```

---

<a name="-passive-scans"></a>

## 👁️ Passive Scans

### Was ist passives Scannen?

Analysiert den Datenverkehr, ohne dafür zusätzliche Anfragen zu senden.

```
Automatisch – verarbeitet laut Vorlage fortlaufend den Proxy-Datenverkehr

Erkennt laut Vorlage:
- Fehlende Sicherheitsheader
- Offenlegung von Informationen
- Probleme mit Cookies
- Schwaches SSL/TLS
- Offenlegung privater IP-Adressen
```

### Passive Scan-Regeln

```
Analyze → Scan Policy Manager → Passive Scan

Beispielhafte Regeln (Originalnamen):
- Application Error Disclosure
- Cookie Without Secure Flag
- Cross-Domain JavaScript
- Information Disclosure
- Private IP Disclosure
- X-Frame-Options Header Missing
```

### Passive Scans konfigurieren
```
Tools → Options → Passive Scan

Einstellungen (Settings):
- Scan only in scope: empfohlen
- Max alerts per rule: 10
- Tags: aktivieren
```

---

## 🎯 Kontexte und Sitzungen

### Was sind Kontexte?

Logische Gruppen zusammengehöriger URLs, um Tests zu organisieren.

### Kontext erstellen
```
Sites rechts anklicken → Include in Context → New Context

Alternativ: File → New Context
```

### Kontexteinstellungen

| Einstellung | Zweck |
|---------|---------|
| **Include in Context** | Einzubeziehende URLs |
| **Exclude from Context** | Auszuschließende URLs |
| **Technology** | Servertechnologie |
| **Authentication** | Anmeldeverfahren |
| **Session Management** | Umgang mit Cookies und Tokens |
| **Users** | Konten für Testbenutzer |

### Authentifizierungsarten

| Art | Anwendungsfall |
|------|----------|
| **Form-based** | Anmeldeformulare |
| **HTTP Basic** | HTTP-Basisauthentifizierung |
| **JSON-based** | API-Authentifizierung |
| **Script-based** | Eigene Authentifizierung |

### Formularbasierte Authentifizierung
```
1. Die Anmeldeanfrage in ZAP aufzeichnen
2. Rechts anklicken → Include in Context
3. Context → Authentication → Form-based
4. Login URL festlegen
5. Login Request POST Data festlegen
6. Parameter für Benutzername und Passwort konfigurieren
7. Merkmale für an- und abgemeldete Sitzungen festlegen
```

### Sitzungsverwaltung
```
Context → Session Management

Arten:
- Cookie-based (laut Vorlage am häufigsten)
- HTTP Auth
- Script-based
```

### Benutzer
```
Context → Users → Add

Testbenutzer hinzufügen:
- Name: admin
- Credentials: admin/password123
```

---

## 🎲 Fuzzer

### Fuzzer starten
```
1. Eine Anfrage im Reiter History auswählen
2. Rechts anklicken → Attack → Fuzz...
3. Die zu variierende Position markieren
4. Payloads hinzufügen
5. Starten
```

### Fuzz-Position hinzufügen
```
1. Im Fuzzer-Dialog den zu variierenden Text markieren
2. Auf „Add...“ klicken
3. Die Payload-Art auswählen
```

### Payload-Arten

| Art | Beschreibung |
|------|-------------|
| **Strings** | Eigene Liste von Zeichenfolgen |
| **File** | Aus einer Datei laden |
| **File Fuzzers** | Mitgelieferte Wortlisten |
| **Numbers** | Zahlenfolgen |
| **Regex** | Aus einem regulären Ausdruck erzeugt |
| **Script** | Eigenes Skript |

### Mitgelieferte Fuzzer
```
Add → File Fuzzers

Kategorien:
- jbrofuzz (XSS, SQL usw.)
- dirbuster (Verzeichnisse)
- fuzzdb (umfassende Sammlung)
```

### Fuzzer-Optionen
```
Optionen:
- Gleichzeitig arbeitende Threads
- Verzögerung zwischen Anfragen
- Weiterleitungen folgen
- Alle oder nur gefilterte Ergebnisse anzeigen
```

### Fuzzer-Beispiel: Parameter-Fuzzing
```
1. Aufzeichnen: GET /page?id=1
2. Rechts anklicken → Fuzz
3. Den Wert „1“ markieren
4. SQL-Injection-Payloads hinzufügen
5. Fuzzer starten
6. Ergebnisse anhand von Antwortlänge und Statuscode auswerten
```

---

## 🤖 API und Automatisierung

### API aktivieren
```
Tools → Options → API

Einstellungen (Settings):
- Enabled: Yes (aktiviert)
- API Key: automatisch erzeugt oder selbst festgelegt
- Permitted addresses: localhost
```

### API-Zugriff
```bash
# API-Wurzel
http://localhost:8080/JSON/

# Mit API-Schlüssel
http://localhost:8080/JSON/core/view/alerts/?apikey=YOUR_API_KEY

# Version abrufen
curl "http://localhost:8080/JSON/core/view/version/?apikey=API_KEY"
```

### Häufige API-Endpunkte

| Endpunkt | Zweck |
|----------|---------|
| `/core/view/alerts/` | Alle Warnmeldungen abrufen |
| `/core/view/hosts/` | Alle Hosts abrufen |
| `/spider/action/scan/` | Spider starten |
| `/ascan/action/scan/` | Aktiven Scan starten |
| `/core/view/urls/` | Alle URLs abrufen |
| `/core/action/shutdown/` | ZAP beenden |

### API-Beispiele

```bash
# Eine URL mit dem Spider erkunden
curl "http://localhost:8080/JSON/spider/action/scan/?url=http://target.com&apikey=API_KEY"

# Spider-Status prüfen
curl "http://localhost:8080/JSON/spider/view/status/?apikey=API_KEY"

# Aktiven Scan starten
curl "http://localhost:8080/JSON/ascan/action/scan/?url=http://target.com&apikey=API_KEY"

# Warnmeldungen abrufen
curl "http://localhost:8080/JSON/core/view/alerts/?apikey=API_KEY"

# HTML-Bericht erzeugen
curl "http://localhost:8080/OTHER/core/other/htmlreport/?apikey=API_KEY" > report.html
```

### Python-API-Client
```python
from zapv2 import ZAPv2

# Mit ZAP verbinden
zap = ZAPv2(apikey='your-api-key', proxies={'http': 'http://127.0.0.1:8080'})

# Spider
zap.spider.scan(url='http://target.com')

# Aktiver Scan
zap.ascan.scan(url='http://target.com')

# Warnmeldungen abrufen
alerts = zap.core.alerts()
for alert in alerts:
    print(f"{alert['risk']}: {alert['name']}")
```

---

<a name="-kommandozeile"></a>

## ⌨️ Kommandozeile

### Grundlegende Kommandozeilenoptionen
```bash
# ZAP im Daemon-Modus starten
zap.sh -daemon

# Auf einem bestimmten Port starten
zap.sh -daemon -port 8090

# Schneller Scan
zap.sh -quickurl http://target.com -quickout report.html

# Baseline-Scan (Docker)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t http://target.com
```

### Automation Framework
```bash
# Automatisierungsplan ausführen
zap.sh -cmd -autorun automation.yaml
```

### YAML-Beispiel für die Automatisierung
```yaml
env:
  contexts:
    - name: "Default Context"
      urls:
        - "http://target.com"

jobs:
  - type: spider
    parameters:
      url: "http://target.com"
      maxDuration: 5
      
  - type: passiveScan-wait
    parameters:
      maxDuration: 5
      
  - type: activeScan
    parameters:
      scanOnlyInScope: true
      
  - type: report
    parameters:
      template: "traditional-html"
      reportFile: "report.html"
```

### Docker-Scans

```bash
# Baseline-Scan (nur passive Regeln laut Vorlage)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://target.com \
    -r report.html

# Vollständiger Scan (aktiv)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-full-scan.py \
    -t https://target.com \
    -r report.html

# API-Scan
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py \
    -t https://target.com/api/openapi.json \
    -f openapi \
    -r report.html
```

---

## 🧩 Add-ons

### Add-ons installieren
```
Help → Check for Updates → Marketplace

Alternativ: Tools → Add-ons → Marketplace
```

### Wichtige Add-ons

| Add-on | Beschreibung |
|--------|-------------|
| **Active Scan Rules** | Zusätzliche Scan-Regeln |
| **Passive Scan Rules** | Zusätzliche passive Regeln |
| **FuzzDB** | Fuzzing-Payloads |
| **Retire.js** | Verwundbare JavaScript-Bibliotheken erkennen |
| **AJAX Spider** | Crawler mit JavaScript-Unterstützung |
| **Selenium** | Browserautomatisierung |
| **Wappalyzer** | Technologien erkennen |
| **Access Control** | Zugriffskontrolle prüfen |
| **Directory List** | Wortlisten für Verzeichnisse |

### Add-ons verwalten
```
# Installierte Add-ons auflisten
Tools → Add-ons → Installed

# Alle aktualisieren
Help → Check for Updates → Update All
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: Schneller Webscan
```
1. Im Reiter Quick Start die URL eingeben
2. Auf „Attack“ klicken
3. ZAP erkundet und scannt die Anwendung automatisch
4. Nach Abschluss die Warnmeldungen prüfen
```

### Beispiel 2: Manuelle und automatisierte Tests
```
1. ZAP als Browserproxy einrichten
2. Die Anwendung manuell erkunden und alle Funktionen ausprobieren
3. Das Ziel in Sites rechts anklicken → Spider
4. Den Abschluss des Spiders abwarten
5. Ziel rechts anklicken → Active Scan
6. Warnmeldungen prüfen
7. Bericht erzeugen
```

### Beispiel 3: Scan mit Authentifizierung
```
1. Für das Ziel einen Kontext erstellen
2. Formularbasierte Authentifizierung einrichten
3. Zugangsdaten eines Testbenutzers hinzufügen
4. Forced User Mode konfigurieren
5. Als authentifizierter Benutzer mit dem Spider erkunden
6. Aktiven Scan durchführen
```

### Beispiel 4: API-Tests
```
1. Die API-Definition importieren (OpenAPI/Swagger)
2. File → Import → Import OpenAPI definition
3. Die API mit dem Spider erkunden
4. Aktiven Scan durchführen
```

### Beispiel 5: CI/CD-Integration
```bash
# Jenkins-Pipeline
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://staging.myapp.com \
    -r zap_report.html \
    -x zap_report.xml
```

---

## 📊 Kurzreferenz

### Wichtige Aktionen

| Aufgabe | Methode |
|------|--------|
| Proxy starten | ZAP startet automatisch auf Port 8080 |
| Spider | Rechtsklick → Spider |
| Active Scan | Rechtsklick → Active Scan |
| Intercept | Auf die Schaltfläche Break (🛑) klicken |
| Fuzz | Anfrage rechts anklicken → Fuzz |
| Bericht erzeugen | Report → Generate Report (Bericht erzeugen) |

### Tastenkombinationen

| Tastenkombination | Aktion |
|----------|--------|
| `Ctrl+Alt+S` | Spider-Dialog |
| `Ctrl+Alt+A` | Active-Scan-Dialog |
| `Ctrl+M` | Manuelle Anfrage |
| `Ctrl+B` | Break umschalten |
| `Ctrl+F` | Suchen |
| `Ctrl+R` | Anfrage erneut senden |

### Scan-Modi

| Modus | Einsatzbereich |
|------|----------|
| **Safe** | Wenn keine Änderungen beabsichtigt sind |
| **Protected** | Nur innerhalb eines festgelegten Umfangs testen |
| **Standard** | Normale Tests |
| **ATTACK** | Aggressive Tests (mit Genehmigung!) |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise zur Leistung

1. **Prüfumfang begrenzen**
```
   Kontext festlegen und nur Elemente im vereinbarten Umfang scannen
```

2. **AJAX Spider für SPAs verwenden**
```
   Moderne Anwendungen benötigen laut Vorlage einen AJAX Spider zusätzlich zum Standard-Spider
```

3. **Technologien konfigurieren**
```
   Nur relevante Technologien auswählen, um die Scandauer zu verkürzen
```

### Hinweise zur Genauigkeit

1. **Zuerst authentifizieren**
```
   Authentifizierung für eine umfassendere Abdeckung einrichten
```

2. **Vor der Automatisierung manuell erkunden**
```
   Die Anwendung zuerst manuell erkunden, um alle Funktionen zu erfassen
```

3. **Alle Warnmeldungen prüfen**
```
   Fehlalarme prüfen und tatsächliche Befunde bestätigen
```

### Arbeitsablauf

```
1. Proxy konfigurieren
2. Manuell erkunden (mit Authentifizierung)
3. Spider ausführen (Standard und AJAX)
4. Passive Scan-Ergebnisse prüfen
5. Aktiven Scan durchführen
6. Manuell testen (Fuzzing und Grenzfälle)
7. Bericht erzeugen
```

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** OWASP ZAP ist ein Werkzeug für Sicherheitstests und darf nur für **autorisierte Tests** eingesetzt werden.
> 
> - ✅ Eigene Anwendungen testen
> - ✅ Mit ausdrücklicher schriftlicher Genehmigung testen
> - ✅ In Test- und Staging-Umgebungen einsetzen
> - ❌ Produktivsysteme niemals ohne Genehmigung scannen
> - ❌ Fremde Systeme niemals ohne Genehmigung scannen
> 
> **Unbefugte Sicherheitstests sind laut Vorlage rechtswidrig und können strafrechtliche Folgen haben.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [OWASP-ZAP-Website](https://www.zaproxy.org/)
- [ZAP-Dokumentation](https://www.zaproxy.org/docs/)
- [ZAP GitHub](https://github.com/zaproxy/zaproxy)
- [ZAP-API-Dokumentation](https://www.zaproxy.org/docs/api/)

### Lernmaterial
- [OWASP-Leitfaden für Sicherheitstests](https://owasp.org/www-project-web-security-testing-guide/)
- [ZAP in Ten](https://www.zaproxy.org/zap-in-ten/)
- [Ausführliche ZAP-Videos](https://www.zaproxy.org/docs/videos/)

### Verwandte Spickzettel
- [Burp Suite](../../Burp-Suite/translations/README.de.md)
- [Nikto](../../Nikto/translations/README.de.md)
- [SQLMap](../../SQLMap/translations/README.de.md)

---

<p align="center">
  <b>⚡ Sicherheitstests für Webanwendungen beherrschen!</b><br>
  <i>Kostenlos, leistungsfähig und von der Community getragen</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Projekt und Vergleichstabelle:** Die Bezeichnung „OWASP ZAP“ stammt aus der Vorlage. ZAP ist seit September 2023 kein OWASP-Projekt mehr; das unabhängige Projekt wird vom ZAP Core Team kontrolliert und von Checkmarx unterstützt. Preisangaben, Add-on-Zahlen und Editionseigenschaften der Vergleichstabelle sind historische Angaben der Vorlage, keine aktuelle Kaufberatung. [ZAP: Trägerschaft](https://www.zaproxy.org/docs/zap-ownership/).

> **Oberfläche und Versionen:** Englische Menü-, Feld- und Regelnamen bleiben als Referenz erhalten; die Schritte sind deutsch erläutert. Pfade, Schaltflächen und Browseroptionen hängen von Version, Sprache und Add-ons ab. Aktuelle Proxy-Einstellungen liegen unter `Options → Network → Local Servers/Proxies`; „Local Proxies“ ist eine ältere Bezeichnung. Passive Scans besitzen eigene Konfigurationsseiten und sind nicht allgemein Teil des Active Scan Policy Manager. [Proxy-Konfiguration](https://www.zaproxy.org/docs/desktop/start/proxies/), [Passive Scanner](https://www.zaproxy.org/docs/desktop/addons/passive-scanner/options/scanner/).

> **Modi und passive Analyse:** `Protected` begrenzt potenziell gefährliche Aktionen auf den Scope; `ATTACK` scannt neu entdeckte Knoten innerhalb des Scopes automatisch aktiv. Der gewählte Modus wird zwischen Sitzungen gespeichert. Der passive Scanner selbst sendet keine zusätzlichen Testanfragen; ein Baseline-Scan startet jedoch einen Spider und erzeugt dadurch Anfragen. HTTP-Nachrichtenanalyse ersetzt keine vollständige TLS-Prüfung. [Betriebsmodi](https://www.zaproxy.org/docs/desktop/start/features/modes/), [Baseline-Scan](https://www.zaproxy.org/docs/docker/baseline-scan/).

> **API und Berichte:** Das Python-Beispiel startet Spider und aktiven Scan, wartet deren Abschluss aber nicht ab; unmittelbar abgefragte Ergebnisse können daher unvollständig sein. Der gezeigte alte Endpunkt `core/other/htmlreport` wurde zugunsten des Report Generation Add-ons als veraltet markiert. API-Schlüssel, erlaubte Adressen und benötigte Add-ons müssen zur Installation passen. In Docker erzeugte Berichte liegen ohne passende Einbindung nicht automatisch als Dateien im Host-Arbeitsordner vor. [API-Leitfaden](https://www.zaproxy.org/docs/api/), [ZAP 2.11.0: veraltete Berichts-Endpunkte](https://www.zaproxy.org/docs/desktop/releases/2.11.0/), [Docker-Leitfaden](https://www.zaproxy.org/docs/docker/about/).

> **Abdeckung und Beispiele:** *Spidering* erkundet erreichbare Inhalte, *Fuzzing* variiert Eingaben; ein *Context* gruppiert URLs und zugehörige Testeinstellungen. Die Auswahl einer Technologie oder höhere Scan-Stärke garantiert keine vollständige Abdeckung. Authentifizierung, Crawler-Browser und Wortlisten benötigen die passenden Voraussetzungen. Die Regelnamen und Bedienpfade sind Beispiele; Warnmeldungen sind zu prüfen. Befehle, URLs, Zugangsdaten-Platzhalter, YAML-/Python-Werte und API-Anfragen wurden unverändert belassen und nicht ausgeführt.
