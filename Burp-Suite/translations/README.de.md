# 🔶 Burp Suite – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    ____                      _____       _ __     
   / __ )__  ___________     / ___/__  __(_) /____
  / __  / / / / ___/ __ \    \__ \/ / / / / __/ _ \
 / /_/ / /_/ / /  / /_/ /   ___/ / /_/ / / /_/  __/
/_____/\__,_/_/  / .___/   /____/\__,_/_/\__/\___/ 
                /_/                                
    Plattform für Sicherheitstests von Webanwendungen
```

<p align="center">
  <img src="https://img.shields.io/badge/Burp-Suite-orange?style=for-the-badge" alt="Burp Suite">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web-Sicherheit">
  <img src="https://img.shields.io/badge/PortSwigger-blue?style=for-the-badge" alt="PortSwigger">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetrationstests">
</p>

<p align="center">
  <b>🌐 Eine etablierte Plattform für Sicherheitstests von Webanwendungen</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation und Einrichtung](#-installation-und-einrichtung)
- [Proxy-Konfiguration](#-proxy-konfiguration)
- [Datenverkehr abfangen](#-datenverkehr-abfangen)
- [Ziel und Prüfumfang](#-ziel-und-prüfumfang)
- [Spider/Crawler](#-spidercrawler)
- [Scanner](#-scanner)
- [Repeater](#-repeater)
- [Intruder](#-intruder)
- [Decoder](#-decoder)
- [Comparer](#-comparer)
- [Sequencer](#-sequencer)
- [Erweiterungen](#-erweiterungen)
- [Tastenkombinationen](#-tastenkombinationen)
- [Testablauf](#-testablauf)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**Burp Suite** ist eine integrierte Plattform für Sicherheitstests von Webanwendungen. Sie wird von PortSwigger entwickelt und gehört zu den etablierten Werkzeugen für Web-Penetrationstests.

### Editionen

| Edition | Funktionen | Preis |
|---------|----------|-------|
| **Community** | Grundlegender Proxy und manuelle Werkzeuge | Kostenlos |
| **Professional** | Scanner, Intruder (unbegrenzt laut Vorlage), Collaborator | $449/year |
| **Enterprise** | CI/CD-Integration und Teamfunktionen | Vertrieb kontaktieren |

### Zentrale Werkzeuge

| Werkzeug | Beschreibung |
|------|-------------|
| **Proxy** | HTTP-/HTTPS-Verkehr abfangen und verändern |
| **Spider** | Webanwendungen automatisch durchsuchen |
| **Scanner** | Schwachstellen automatisch erkennen |
| **Intruder** | Automatisierte, angepasste Tests |
| **Repeater** | Anfragen manuell verändern |
| **Decoder** | Daten kodieren und dekodieren |
| **Comparer** | Antworten vergleichen |
| **Sequencer** | Zufälligkeit von Tokens untersuchen |

---

## 📥 Installation und Einrichtung

### Herunterladen

```bash
# Von der offiziellen Website herunterladen
https://portswigger.net/burp/releases

# Verfügbar für:
# - Windows (Installer/eigenständige Datei)
# - macOS (DMG)
# - Linux (Skript/JAR)
```

### Systemvoraussetzungen

| Anforderung | Minimum | Empfohlen |
|-------------|---------|-------------|
| RAM | 4 GB | 8+ GB |
| Speicherplatz | 500 MB | 2+ GB |
| Java | JRE 8+ | Mitgelieferte JRE |
| Bildschirm | 1280x800 | 1920x1080 |

### Einrichtung beim ersten Start

1. **Projektart auswählen**
   - Temporary project (temporäres Projekt zum Testen)
   - New project on disk (Arbeit als Projektdatei speichern)
   - Open existing project (vorhandenes Projekt öffnen)

2. **Konfiguration**
   - Use Burp defaults (Burp-Standardeinstellungen verwenden; für Einsteiger empfohlen)
   - Load from configuration file (Konfigurationsdatei laden)

3. **Arbeitsspeicher zuweisen**
   ```bash
   # Für große Projekte mehr Arbeitsspeicher zuweisen
   java -jar -Xmx4g burpsuite_pro.jar
   ```

---

## 🔌 Proxy-Konfiguration

### Browser konfigurieren

#### Proxy manuell einrichten

| Browser | Pfad zu den Proxy-Einstellungen laut Vorlage |
|---------|------------------------|
| Firefox | Settings → Network → Manual proxy |
| Chrome | Verwendet den Systemproxy oder eine Erweiterung |
| Safari | System Preferences → Network → Proxies |

**Burp-Standardproxy:**
```
Host: 127.0.0.1
Port: 8080
```

#### Firefox konfigurieren
```
1. Die Firefox-Einstellungen öffnen
2. Nach "proxy" suchen
3. Auf "Settings..." klicken
4. "Manual proxy configuration" auswählen
5. HTTP Proxy: 127.0.0.1  Port: 8080
6. "Also use this proxy for HTTPS" aktivieren
7. Auf OK klicken
```

#### FoxyProxy-Erweiterung (laut Vorlage empfohlen)
```
1. Die FoxyProxy-Erweiterung installieren
2. Einen neuen Proxy hinzufügen:
   - Title: Burp Suite
   - Proxy Type: HTTP
   - Proxy IP: 127.0.0.1
   - Port: 8080
3. Für den Test aktivieren
```

### SSL-/TLS-Zertifikat installieren

```bash
# Schritt 1: Bei aktiviertem Proxy folgende Adresse aufrufen:
http://burp

# Schritt 2: Zum Herunterladen auf "CA Certificate" klicken

# Schritt 3: Das Zertifikat importieren:
# Firefox-Menüpfad: Settings → Certificates → Import → Trust for websites
# Chrome-Menüpfad: Settings → Security → Manage certificates → Import
# System: Zu den vertrauenswürdigen Stammzertifikaten hinzufügen
```

### Proxy-Listener

```
Proxy → Options → Proxy Listeners

Standard: 127.0.0.1:8080

Eigene Listener hinzufügen:
- Unterschiedliche Ports für verschiedene Tests
- Für mobile Tests an alle Schnittstellen binden (0.0.0.0)
- An andere Hosts weiterleiten
```

### Unsichtbarer Proxy (für Clients ohne Proxy-Unterstützung)

```
1. Proxy → Options → Proxy Listeners
2. Edit listener (Listener bearbeiten) → Request handling
3. "Support invisible proxying" aktivieren
4. Hosts-Datei oder DNS konfigurieren
```

---

## 🎣 Datenverkehr abfangen

### Steuerung des Abfangens

| Schaltfläche | Funktion |
|--------|----------|
| **Intercept is on/off** | Abfangen ein- oder ausschalten |
| **Forward** | Anfrage an den Server senden |
| **Drop** | Anfrage verwerfen |
| **Action** | Menü mit weiteren Optionen |

### Optionen zum Abfangen

```
Proxy → Options → Intercept Client Requests

Beispielregeln:
- Nur Elemente innerhalb des Prüfumfangs abfangen
- Nur bestimmte Dateitypen abfangen
- Bilder und statische Dateien ausschließen
```

### Anfragen verändern

```http
# Ursprüngliche Anfrage
GET /page.php?id=1 HTTP/1.1
Host: target.com

# Veränderte Anfrage (während des Abfangens)
GET /page.php?id=1' OR '1'='1 HTTP/1.1
Host: target.com
```

### Suchen und Ersetzen (Match and Replace)

```
Proxy → Options → Match and Replace

Häufige Regeln:
- Sicherheitsheader entfernen
- User-Agent verändern
- Eigene Header hinzufügen
- Anfrageparameter ersetzen
```

| Typ | Suchmuster | Ersetzung | Zweck |
|------|-------|---------|---------|
| Request header | `User-Agent: .*` | `User-Agent: Custom` | User-Agent ändern |
| Request body | `password=test` | `password=admin` | Zugangsdaten testen |
| Response header | `X-Frame-Options.*` | ` ` | Header entfernen |
| Response body | `disabled` | `enabled` | Schaltflächen aktivieren |

### Antworten verändern

```
Proxy → Options → Intercept Server Responses

Aktivieren, um:
- Sicherheitsheader zu entfernen
- JavaScript zu verändern
- Antwortinhalte zu ändern
- Clientseitige Prüfungen zu testen
```

---

## 🎯 Ziel und Prüfumfang

### Zum Prüfumfang hinzufügen

```
Methode 1: In Site map rechtsklicken → Add to scope
Methode 2: Target → Scope → Add (manuell)
Methode 3: In Proxy history → Add to scope
```

### Prüfumfang konfigurieren

```
Target → Scope

In den Prüfumfang einschließen:
- Protocol: Any/HTTP/HTTPS
- Host/IP: target.com
- Port: Any/Specific
- File: Regex pattern

Beispielmuster:
.*\.target\.com$          # Alle Subdomains
^https://target\.com/app  # Bestimmter Pfad
```

### Erweiterte Einstellungen des Prüfumfangs

```
Target → Scope → Advanced scope control

Include (einschließen):
  Protocol: HTTPS
  Host: target.com
  Port: 443
  File: ^/api/.*

Exclude (ausschließen):
  Host: .*\.google\.com
  File: .*\.(jpg|png|gif|css|js)$
```

### Site map (Übersicht der Anwendung)

```
Target → Site map

Funktionen:
- Alle gefundenen Inhalte anzeigen
- Nach Prüfumfang filtern
- Inhalte durchsuchen und filtern
- Interessante Elemente hervorheben
- Site Maps vergleichen
```

---

<a name="-spidercrawler"></a>

## 🕷️ Spider/Crawler

### Spider starten

```
1. In Site map mit der rechten Maustaste auf das Ziel klicken
2. "Spider this host" oder "Spider this branch" auswählen
3. Alternativ: Target → Site map → Rechtsklick → Scan → Crawl
```

### Spider-Einstellungen

```
Spider → Options (ältere Oberfläche) ODER
Dashboard → New scan → Crawl

Crawler-Einstellungen:
- Maximale Linktiefe
- Maximale Crawl-Dauer
- Formulare absenden
- Anmeldungen verarbeiten
```

### Crawl-Konfiguration

| Einstellung | Beschreibung | Empfohlen |
|---------|-------------|-------------|
| **Link depth** (Linktiefe) | Wie tief Links verfolgt werden | 5-10 |
| **Max requests** (maximale Anfragen) | Obergrenze für die Gesamtzahl der Anfragen | 1000-5000 |
| **Duplicate removal** (Duplikate entfernen) | Ähnliche Seiten überspringen | Aktiviert |
| **Passive scanning** (passive Analyse) | Während des Crawlens analysieren | Aktiviert |

### Formulare absenden

```
Spider → Options → Application Login

Anmeldung konfigurieren für:
- Ein einzelnes Zugangsdatenpaar
- Eine Liste von Zugangsdaten
- Sitzungserkennung
```

---

## 🔍 Scanner

### Scan-Arten

| Typ | Beschreibung | Anwendungsfall |
|------|-------------|----------|
| **Passive** (passiv) | Proxy-Verkehr analysieren | Laut Vorlage dauerhaft aktiv |
| **Active** (aktiv) | Payloads senden, um Schwachstellen zu finden | Mit Genehmigung |
| **Full** (vollständig) | Umfassender Scan | Wenn ausreichend Zeit vorhanden ist |
| **Custom** (angepasst) | Nur bestimmte Tests | Gezielte Prüfung |

### Scan starten

```
Methode 1: Rechtsklick → Scan
Methode 2: Dashboard → New scan
Methode 3: Aus anderen Werkzeugen an den Scanner senden
```

### Scan-Konfiguration

```
Dashboard → New scan → Scan configuration

Integrierte Konfigurationen:
- Audit checks - all except JavaScript analysis
- Audit checks - critical issues only
- Audit checks - light, active
- Audit checks - medium, active
- Audit checks - full, active
```

### Schwachstellenkategorien

```
Der Scanner prüft unter anderem:

Injection:
- SQL Injection
- OS Command Injection
- LDAP Injection
- XML/XPath Injection

Cross-Site Scripting:
- Reflected XSS
- Stored XSS
- DOM-based XSS

Authentifizierung:
- Fehlerhafte Authentifizierung
- Probleme der Sitzungsverwaltung
- Schwache Passwörter

Weitere Kategorien:
- CSRF
- Pfadmanipulation (Directory Traversal)
- Einbinden von Dateien (File Inclusion)
- Offenlegung von Informationen
- Serverseitige Probleme
```

### Scan-Ergebnisse

```
Dashboard → Issue activity
Target → Site map → Issues

Für jeden Befund:
- Severity (Schweregrad: High/Medium/Low/Info)
- Confidence (Sicherheit der Einstufung: Certain/Firm/Tentative)
- Beschreibung
- Behebung
- Nachweise (Anfrage/Antwort)
```

### Eigene Scan-Profile

```
1. Dashboard → New scan
2. "Scan configuration" auswählen
3. Auf "New" klicken oder eine vorhandene Konfiguration ändern
4. Zur Wiederverwendung speichern

Anpassen:
- Zu prüfende Schwachstellen
- Intensität der Payloads
- Anfragerate
- Verarbeitung der Authentifizierung
```

---

## 🔁 Repeater

### Zweck

Einzelne Anfragen manuell prüfen und verändern.

### An Repeater senden

```
1. Mit der rechten Maustaste auf eine Anfrage klicken
2. "Send to Repeater" auswählen
3. Tastenkombination: Ctrl+R
```

### Repeater verwenden

```
1. Anfrage im linken Bereich verändern
2. Auf "Send" klicken (oder Ctrl+Space)
3. Antwort im rechten Bereich ansehen
4. Antworten vergleichen
```

### Repeater-Funktionen

| Funktion | Beschreibung |
|---------|-------------|
| **Multiple tabs** (mehrere Tabs) | Mehrere Anfragen prüfen |
| **Request history** (Anfrageverlauf) | Mit den Schaltflächen < > navigieren |
| **Follow redirects** (Weiterleitungen folgen) | Automatisches Folgen ein- oder ausschalten |
| **Process cookies** (Cookies verarbeiten) | Cookies automatisch aktualisieren |
| **Content-Length** | Header automatisch aktualisieren |

### Testbeispiele

```http
# Auf SQL-Injection prüfen
GET /user.php?id=1' HTTP/1.1

# Auf XSS prüfen
GET /search?q=<script>alert(1)</script> HTTP/1.1

# Umgehung der Authentifizierung prüfen
GET /admin HTTP/1.1
Cookie: role=admin

# Auf IDOR prüfen
GET /api/user/1 HTTP/1.1  # Eigener Benutzer
GET /api/user/2 HTTP/1.1  # Anderer Benutzer
```

---

## 🔫 Intruder

### Angriffstypen

| Typ | Positionen | Payloads | Anwendungsfall |
|------|-----------|----------|----------|
| **Sniper** | 1+ | 1 Satz, jeweils eine Position | Einzelnen Parameter prüfen |
| **Battering Ram** | 1+ | 1 Satz, überall derselbe Wert | Überall denselben Wert einsetzen |
| **Pitchfork** | 1+ | Mehrere Sätze, parallel | Benutzername-Passwort-Paare |
| **Cluster Bomb** | 1+ | Mehrere Sätze, alle Kombinationen | Alle Kombinationen von Zugangsdaten testen |

### An Intruder senden

```
1. Mit der rechten Maustaste auf die Anfrage klicken
2. "Send to Intruder" auswählen
3. Tastenkombination: Ctrl+I
```

### Positionen festlegen

```
Intruder → Positions

Einfügepositionen mit §-Zeichen markieren:

GET /login.php?user=§admin§&pass=§test§ HTTP/1.1

Schaltflächen:
- Add § – Auswahl markieren
- Clear § – Markierungen entfernen
- Auto § – Automatisch erkennen
```

### Payload-Typen

| Typ | Beschreibung |
|------|-------------|
| **Simple list** | Eigene Wortliste |
| **Runtime file** | Aus einer Datei laden |
| **Numbers** | Fortlaufende oder zufällige Zahlen |
| **Dates** | Datumsfolgen |
| **Brute forcer** | Zeichenkombinationen |
| **Null payloads** | Leere Payloads (Anfragerate testen) |
| **Username generator** | Häufige Muster für Benutzernamen |

### Payload-Verarbeitung

```
Intruder → Payloads → Payload Processing

Regeln hinzufügen:
- Präfix oder Suffix hinzufügen
- URL-kodieren
- Base64-kodieren
- Hash (MD5, SHA)
- Suchen/Ersetzen
```

### Grep - Match

```
Intruder → Options → Grep - Match

Zeichenfolgen hinzufügen, die in Antworten markiert werden sollen:
- "Login successful"
- "Invalid password"
- "error"
- Eigener regulärer Ausdruck

Hilft, erfolgreiche Tests zu erkennen.
```

### Angriffsbeispiele

#### Anmeldungen durch systematisches Ausprobieren testen
```
POST /login HTTP/1.1
Content-Type: application/x-www-form-urlencoded

username=§admin§&password=§password§

Angriffstyp: Cluster bomb
Payload 1: Liste der Benutzernamen
Payload 2: Passwortliste
```

#### Parameter-Fuzzing
```
GET /api/user/§1§ HTTP/1.1

Angriffstyp: Sniper
Payload: Numbers 1-1000 (Zahlenbereich)
```

#### Inhalte ermitteln
```
GET /§admin§ HTTP/1.1

Angriffstyp: Sniper
Payload: Wortliste mit Verzeichnisnamen
Filter: Statuscode != 404
```

### Ergebnisse auswerten

```
Nach dem Test:
- Nach Länge sortieren (abweichende Antworten finden)
- Nach Statuscode sortieren
- Nach Grep-Treffern filtern
- Ergebnisse exportieren
```

---

## 🔓 Decoder

### Kodieren und Dekodieren

```
Tab Decoder

Operationen:
- URL-kodieren und -dekodieren
- HTML-kodieren und -dekodieren
- Base64-kodieren und -dekodieren
- Hex-kodieren und -dekodieren
- Mit Gzip komprimieren und dekomprimieren
- ASCII Hex
- Binärdarstellung
```

### Smart decode (automatische Dekodierung)

```
Decoder → Smart decode

Erkennt und dekodiert automatisch:
- Base64
- URL-Kodierung
- HTML-Entitäten
- Kombinierte Kodierungen
```

### Häufige Anwendungen

```
# JWT dekodieren
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
→ Decode as Base64 (als Base64 dekodieren)

# URL-Parameter dekodieren
%3Cscript%3Ealert(1)%3C%2Fscript%3E
→ URL decode (URL-dekodieren)
→ <script>alert(1)</script>

# Payload kodieren
<script>alert(1)</script>
→ Base64 encode (Base64-kodieren)
→ Im Test verwenden
```

---

## 📊 Comparer

### Zweck

Zwei Datensätze vergleichen, etwa Anfragen, Antworten oder andere Daten.

### Verwendung

```
1. Element 1 rechtsklicken → Send to Comparer
2. Element 2 rechtsklicken → Send to Comparer
3. Zum Tab Comparer wechseln
4. Elemente auswählen und auf "Words" oder "Bytes" klicken
```

### Vergleichsmodi

| Modus | Beschreibung |
|------|-------------|
| **Words** | Wortweise vergleichen |
| **Bytes** | Byteweise vergleichen (exakt) |

### Anwendungsfälle

```
- Antworten erfolgreicher und fehlgeschlagener Anmeldungen vergleichen
- Unterschiede in Sitzungstokens finden
- Zustand vor und nach Änderungen vergleichen
- Verborgene Formularfelder erkennen
```

---

## 🔢 Sequencer

### Zweck

Die Qualität der Zufälligkeit von Tokens untersuchen, etwa Sitzungskennungen oder CSRF-Tokens.

### Verwendung

```
1. Eine Anfrage erfassen, deren Antwort ein Token enthält
2. Rechtsklick → Send to Sequencer
3. Position des Tokens konfigurieren
4. Live-Erfassung starten
5. Ergebnisse auswerten
```

### Analysekennzahlen

| Kennzahl | Guter Wert laut Vorlage | Beschreibung |
|--------|------------|-------------|
| **Overall quality** | >100 bits | Bewertung der Zufälligkeit |
| **Character-level** | Hohe Entropie | Zufälligkeit je Zeichen |
| **Bit-level** | Hohe Entropie | Zufälligkeit je Bit |

### Interpretation

```
Ergebnisse:
- Excellent: Token ist laut Vorlage kryptografisch zufällig
- Good: Laut Vorlage für die meisten Anwendungen ausreichend
- Poor: Möglicherweise vorhersagbar
- Very poor: Token könnte erraten werden
```

---

## 🧩 Erweiterungen

### BApp Store

```
Extender → BApp Store

Häufig verwendete Erweiterungen:
```

### Nützliche Erweiterungen laut Vorlage

| Erweiterung | Beschreibung |
|-----------|-------------|
| **Autorize** | Autorisierung prüfen |
| **Logger++** | Erweiterte Protokollierung |
| **Retire.js** | Verwundbare JavaScript-Bibliotheken erkennen |
| **Active Scan++** | Zusätzliche Scan-Prüfungen |
| **AuthMatrix** | Autorisierungsmatrix prüfen |
| **Turbo Intruder** | Schnelle Alternative zu Intruder |
| **Param Miner** | Verborgene Parameter ermitteln |
| **JSON Web Tokens** | JWTs analysieren und verändern |
| **Hackvertor** | Erweitertes Kodieren und Dekodieren |
| **CO2** | Sammlung nützlicher Werkzeuge |

### Erweiterungen installieren

```
Methode 1: BApp Store
1. Extender → BApp Store
2. Erweiterung suchen
3. Auf Install klicken

Methode 2: Manuell
1. Eine .jar- oder .py-Datei herunterladen
2. Extender → Extensions → Add
3. Dateityp und Pfad auswählen
```

### Einstellungen für Erweiterungen

```
Extender → Options

- Python-Umgebung (für Python-Erweiterungen)
- Einstellungen der Java-Umgebung
- Protokollierungsoptionen
```

---

<a name="-tastenkombinationen"></a>

## ⌨️ Tastenkombinationen

### Globale Tastenkombinationen

| Tastenkombination | Aktion |
|----------|--------|
| `Ctrl+I` | An Intruder senden |
| `Ctrl+R` | An Repeater senden |
| `Ctrl+S` | Suchen |
| `Ctrl+F` | Weiterleiten (Proxy) |
| `Ctrl+D` | Verwerfen (Proxy) |
| `Ctrl+T` | Abfangen ein- oder ausschalten |
| `Ctrl+Shift+T` | Proxy-Abfangen ein- oder ausschalten |

### Zwischen Tabs wechseln

| Tastenkombination | Aktion |
|----------|--------|
| `Ctrl+Shift+D` | Dashboard |
| `Ctrl+Shift+T` | Target |
| `Ctrl+Shift+P` | Proxy |
| `Ctrl+Shift+I` | Intruder |
| `Ctrl+Shift+R` | Repeater |

### In Repeater

| Tastenkombination | Aktion |
|----------|--------|
| `Ctrl+Space` | Anfrage senden |
| `Ctrl+G` | Im Anfrageverlauf wechseln |
| `Ctrl+Plus` | Neuer Tab |

### Im Editor

| Tastenkombination | Aktion |
|----------|--------|
| `Ctrl+U` | Auswahl URL-kodieren |
| `Ctrl+Shift+U` | Auswahl URL-dekodieren |
| `Ctrl+B` | Base64-kodieren |
| `Ctrl+Shift+B` | Base64-dekodieren |

---

## 🎯 Testablauf

### Üblicher Ablauf eines Webanwendungstests

```
Schritt 1: Einrichtung
├── Browserproxy konfigurieren
├── SSL-Zertifikat installieren
└── Prüfumfang festlegen

Schritt 2: Aufklärung
├── Anwendung manuell durchgehen
├── Spider/Crawler verwenden
└── Site map prüfen

Schritt 3: Analyse
├── Proxy history prüfen
├── Ergebnisse der passiven Analyse prüfen
└── Angriffsfläche bestimmen

Schritt 4: Prüfung
├── Mit Repeater testen (manuell)
├── Mit Intruder testen (automatisiert)
├── Aktiven Scan ausführen
└── Erweiterungen verwenden

Schritt 5: Bericht
├── Alle Befunde prüfen
├── Bericht erstellen
└── Nachweise dokumentieren
```

### Testcheckliste

```
Authentifizierung:
□ Anmeldung durch systematisches Ausprobieren testen
□ Passwortrichtlinie
□ Kontosperrung
□ Sitzungsverwaltung
□ Funktion zum Merken der Anmeldung

Autorisierung:
□ Auf IDOR prüfen
□ Rechteausweitung
□ Zugriff auf Funktionsebene

Eingabevalidierung:
□ SQL-Injection
□ XSS (reflected/stored/DOM)
□ Command Injection
□ Dateiupload
□ XXE

Sitzung:
□ Session Fixation
□ Sitzungsablauf
□ Zufälligkeit von Tokens

Geschäftslogik:
□ Preismanipulation
□ Umgehung des vorgesehenen Ablaufs
□ Race Conditions
```

---

## 📊 Kurzreferenz

### Werkzeugübersicht

| Werkzeug | Hauptzweck | Wichtige Tastenkombination |
|------|-------------|--------------|
| Proxy | Datenverkehr abfangen | Ctrl+T (umschalten) |
| Repeater | Manuelles Testen | Ctrl+R (senden an) |
| Intruder | Automatisierte Tests | Ctrl+I (senden an) |
| Scanner | Schwachstellen finden | Right-click → Scan |
| Spider | Inhalte ermitteln | Right-click → Spider |
| Decoder | Kodieren/Dekodieren | - |
| Comparer | Antwortunterschiede untersuchen | Send to Comparer |
| Sequencer | Token-Analyse | Send to Sequencer |

### Kurzreferenz der Intruder-Angriffstypen

| Angriffstyp | Anwendung |
|--------|----------|
| **Sniper** | Jeweils einen Parameter prüfen |
| **Battering Ram** | Überall dieselbe Payload |
| **Pitchfork** | Benutzername-Passwort-Paare |
| **Cluster Bomb** | Alle Kombinationen von Benutzername × Passwort |

### Häufige HTTP-Statuscodes

| Code | Bedeutung | Bedeutung für Intruder |
|------|---------|----------------------|
| 200 | OK | Normale Antwort |
| 301/302 | Weiterleitung | Kann auf Erfolg hinweisen |
| 401 | Nicht authentifiziert | Falsche Zugangsdaten laut Vorlage |
| 403 | Zugriff verboten | Zugriff verweigert |
| 404 | Nicht gefunden | Ungültiger Pfad laut Vorlage |
| 500 | Serverfehler | Mögliche Schwachstelle |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise zur Leistung

1. **Prüfumfang begrenzen**
   ```
   Nur Zieldomains im Prüfumfang behalten
   Verringert irrelevante Einträge in Proxy history
   ```

2. **Erweiterungen deaktivieren**
   ```
   Nicht verwendete Erweiterungen zur Leistungsverbesserung deaktivieren
   ```

3. **Projektdateien verwenden**
   ```
   Arbeit regelmäßig speichern
   Für umfangreiche Tests Projektdateien verwenden
   ```

### Hinweise zum Testen

1. **Passiv beginnen**
   ```
   Mit der passiven Analyse offensichtliche Probleme finden
   Ergebnisse vor aktiven Scans prüfen
   ```

2. **Zuerst Repeater verwenden**
   ```
   Manuelle Tests liefern zusätzliche Erkenntnisse
   Die Anwendung vor der Automatisierung verstehen
   ```

3. **Mit Tabs organisieren**
   ```
   Repeater-Tabs aussagekräftig benennen
   Intruder-Tests benennen
   ```

### Vermeidung einer Erkennung

```
Einstellungen für laut Vorlage unauffälliges Testen:

Project options → Connections:
- Anfragerate begrenzen
- Zufällige Verzögerungen
- Anzahl gleichzeitiger Verbindungen begrenzen

Intruder → Options:
- Verzögerung zwischen Anfragen
- Startzeit
```

### Fehlerbehebung

| Problem | Lösung |
|-------|----------|
| HTTPS lässt sich nicht abfangen | CA-Zertifikat installieren |
| Langsame Verarbeitung | Prüfumfang verkleinern, Erweiterungen deaktivieren |
| Fehlende Anfragen | "Intercept Server Responses" prüfen (Angabe der Vorlage) |
| Scanner findet keine Schwachstellen | Scan-Intensität erhöhen (Vorschlag der Vorlage) |

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** Burp Suite ist ein leistungsfähiges Werkzeug für Sicherheitstests und darf nur für **autorisierte Tests** eingesetzt werden.
> 
> - ✅ Eigene Anwendungen testen
> - ✅ Mit ausdrücklicher schriftlicher Genehmigung testen
> - ✅ In vorgesehenen Testumgebungen testen
> - ❌ Produktivsysteme niemals ohne Genehmigung scannen
> - ❌ Niemals für unbefugten Zugriff verwenden
> 
> **Unbefugte Sicherheitstests sind laut Vorlage rechtswidrig und können strafrechtliche Folgen haben.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [PortSwigger Web Security Academy](https://portswigger.net/web-security)
- [Burp-Suite-Dokumentation](https://portswigger.net/burp/documentation)
- [BApp Store](https://portswigger.net/bappstore)

### Lernmaterial
- [OWASP-Leitfaden für Sicherheitstests](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Verwandte Spickzettel
- [SQLMap](../../SQLMap/translations/README.de.md)
- [Metasploit Framework](../../Metasploit/translations/README.de.md)

---

<p align="center">
  <b>🔶 Sicherheitstests für Webanwendungen beherrschen!</b><br>
  <i>Am besten lernst du durch eigenes Ausprobieren – richte ein Labor ein und übe!</i>
</p>

---

---

## Hinweise zur deutschen Ausgabe

> **Versionen und Editionen:** Die Vorlage kombiniert ältere und neuere Oberflächen. Spider und Scanner wurden bereits mit Burp 2.0 in das Aufgabenmodell auf dem Dashboard integriert; Pfade mit `Spider → Options`, `Proxy → Options` oder `Extender` sind deshalb historische Referenzen. Echte englische Menübezeichnungen bleiben zur Orientierung erhalten; Beschriftungen und Verfügbarkeit hängen von Version und Edition ab. Automatisierte Scanner-Funktionen stehen nicht einfach in jeder Community-Installation zur Verfügung. Preis, Produktnamen und Systemwerte der Tabelle sind Angaben der Vorlage, keine aktuelle Preisauskunft. Die heutige Dokumentation unterscheidet unter anderem Professional, Community und DAST. [PortSwigger: Spider und Scanner](https://portswigger.net/blog/burp-2-0-where-are-the-spider-and-scanner), [aktuelle Dokumentation](https://portswigger.net/burp/documentation/desktop).

> **Installation und Speicher:** Die Tabelle mit `JRE 8+`, `500 MB` und `8+ GB` ist keine aktuelle allgemeine Systemempfehlung; Ressourcenbedarf und Laufzeit richten sich nach Version, Paket und Arbeitslast. Native Installer und eigenständig gestartete JAR-Dateien sind zu unterscheiden. Beim normalen Java-Launcher folgt auf `-jar` der JAR-Dateiname; JVM-Optionen wie `-Xmx4g` gehören davor. Der ursprüngliche Startaufruf bleibt unverändert. [PortSwigger: Systemvoraussetzungen](https://portswigger.net/burp/documentation/desktop/getting-started/system-requirements), [Oracle: Java-Aufruf](https://docs.oracle.com/en/java/javase/21/docs/specs/man/java.html).

> **Auswertung und Beispielgrenzen:** Sequencer liefert eine statistische Einschätzung anhand der Stichprobe, keinen Beweis kryptografischer Sicherheit. Die Qualität hängt auch von Stichprobengröße und Signifikanzniveau ab; `>100 bits` ist keine allgemeingültige Freigabe für jeden Einsatzzweck. Ein JWT besteht aus Segmenten und nutzt typischerweise Base64url; die abgekürzte Decoder-Zeile ist kein vollständiges JWT-Prüfverfahren. HTTP-Statuscodes, abweichende Antwortlängen und Scanner-Treffer allein belegen weder einen erfolgreichen Angriff noch eine ausnutzbare Schwachstelle. Die mit `#` kommentierten HTTP-Blöcke sind Lehrbeispiele, keine unmittelbar versandfertigen Roh-Anfragen. [PortSwigger: Sequencer-Ergebnisse](https://portswigger.net/burp/documentation/desktop/tools/sequencer/results).

> **Bedienung und Wirkung:** Tastenkombinationen sind konfigurierbar; die Vorlage weist `Ctrl+Shift+T` mehreren Aktionen zu und ist daher nicht als verbindliche aktuelle Tastenbelegung zu verstehen. Empfangene Antworten und fehlende Anfragen sind unterschiedliche Proxy-Sachverhalte. Größere Scan-Intensität, Verzögerungen oder ein anderer User-Agent garantieren weder Vollständigkeit noch fehlende Erkennung. Das Vertrauen in ein Proxy-CA-Zertifikat und ein Listener auf `0.0.0.0` verändern die jeweilige Testumgebung; das Dokument hat keine dieser Aktionen ausgeführt. [PortSwigger: Hotkeys](https://portswigger.net/burp/documentation/desktop/settings/ui/hotkeys).
