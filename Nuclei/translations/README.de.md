# 🎯 Nuclei – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _            _      _ 
 | \ | |_   _  ___| | ___(_)
 |  \| | | | |/ __| |/ _ \ |
 | |\  | |_| | (__| |  __/ |
 |_| \_|\__,_|\___|_|\___|_|
                            
    Schneller vorlagenbasierter Scanner
```

<p align="center">
  <img src="https://img.shields.io/badge/Nuclei-Vulnerability_Scanner-red?style=for-the-badge" alt="Nuclei">
  <img src="https://img.shields.io/badge/9000+-Templates-orange?style=for-the-badge" alt="Templates">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🔥 Ein etabliertes vorlagenbasiertes Werkzeug für die Bug-Bounty-Suche</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Grundsyntax](#-grundsyntax)
- [Vorlagenkategorien](#-vorlagenkategorien)
- [Ziele scannen](#-ziele-scannen)
- [Vorlagen auswählen](#-vorlagen-auswählen)
- [Ausgabeoptionen](#-ausgabeoptionen)
- [Erweiterte Funktionen](#-erweiterte-funktionen)
- [Eigene Vorlagen](#-eigene-vorlagen)
- [Bug-Bounty-Arbeitsablauf](#-bug-bounty-arbeitsablauf)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**Nuclei** ist ein schneller, vorlagenbasierter Schwachstellenscanner von ProjectDiscovery. Er wird für Bug-Bounty-Tests und Sicherheitsforschung eingesetzt.

### Wichtige Funktionen

| Eigenschaft | Beschreibung |
|---------|-------------|
| **Über 9000 Vorlagen laut Vorlage** | CVEs, Administrationsoberflächen, Fehlkonfigurationen und Übernahmen |
| **Schnell und effizient** | Parallele Scans mit begrenzter Anfragerate |
| **Vorlagenbasiert** | YAML-Vorlagen für einfache Anpassungen |
| **Von der Community getragen** | Regelmäßig aktualisierte Vorlagen |
| **Mehrere Protokolle** | HTTP, DNS, TCP, SSL, Headless |
| **Einfache Integration** | Mit anderen Werkzeugen wie httpx und subfinder kombinierbar |

### Arten von Vorlagen

| Art | Beschreibung | Anzahl |
|------|-------------|-------|
| **CVE** | Bekannte Schwachstellen | 2000+ |
| **Offengelegte Administrationsoberflächen** | Administrationsoberflächen und Dashboards | 500+ |
| **Fehlkonfigurationen** | Sicherheitsrelevante Fehlkonfigurationen | 300+ |
| **Standardanmeldungen** | Standardzugangsdaten | 200+ |
| **Übernahmen** | Übernahme von Subdomains | 100+ |
| **Technologien** | Technologieerkennung | 1000+ |
| **Offengelegte Daten** | Offengelegte vertrauliche Dateien | 500+ |

---

## 📥 Installation

### Mit Go (laut Vorlage empfohlen)
```bash
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest
```

### Binärdatei herunterladen
```bash
# Aus den Releases herunterladen
https://github.com/projectdiscovery/nuclei/releases

# Linux
wget https://github.com/projectdiscovery/nuclei/releases/download/v3.x.x/nuclei_3.x.x_linux_amd64.zip
unzip nuclei_3.x.x_linux_amd64.zip
sudo mv nuclei /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/nuclei:latest
docker run -it projectdiscovery/nuclei -h
```

### Kali Linux
```bash
sudo apt install nuclei
```

### Vorlagen aktualisieren
```bash
# Nuclei und Vorlagen laut Original aktualisieren
nuclei -ut

# Nur Vorlagen aktualisieren
nuclei -update-templates
```

### Installation prüfen
```bash
nuclei -version
nuclei -tl              # Vorlagen auflisten
```

---

<a name="-grundsyntax"></a>

## ⌨️ Grundsyntax

### Allgemeine Syntax
```bash
nuclei [options] -target <url>
```

### Wichtige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-u` | Einzelne Ziel-URL |
| `-l` | Datei mit einer URL-Liste |
| `-t` | Auszuführende Vorlagen |
| `-tags` | Vorlagen mit bestimmten Tags |
| `-severity` | Nach Schweregrad filtern |
| `-o` | Ausgabedatei |
| `-silent` | Reduzierte Ausgabe |
| `-v` | Ausführliche Ausgabe |

### Schnelleinstieg
```bash
# Einzelnes Ziel scannen
nuclei -u https://example.com

# Zielliste scannen
nuclei -l urls.txt

# Mit bestimmten Vorlagen scannen
nuclei -u https://example.com -t cves/

# Nach Tags scannen
nuclei -u https://example.com -tags cve,rce
```

---

## 📂 Vorlagenkategorien

### CVE-Vorlagen

```bash
# Alle CVE-Vorlagen
nuclei -u target.com -t cves/

# Bestimmtes CVE-Jahr
nuclei -u target.com -t cves/2024/
nuclei -u target.com -t cves/2023/

# Nur kritische CVEs
nuclei -u target.com -t cves/ -severity critical

# Bestimmte CVE
nuclei -u target.com -t cves/2023/CVE-2023-XXXXX.yaml
```

### Offengelegte Administrationsoberflächen

```bash
# Alle Vorlagen zur Erkennung von Administrationsoberflächen
nuclei -u target.com -t exposed-panels/

# Bestimmte Administrationsoberflächen
nuclei -u target.com -tags phpmyadmin
nuclei -u target.com -tags jenkins
nuclei -u target.com -tags grafana
nuclei -u target.com -tags kibana
```

### Fehlkonfigurationen

```bash
# Alle Fehlkonfigurationen
nuclei -u target.com -t misconfiguration/

# Bestimmte Arten
nuclei -u target.com -tags cors
nuclei -u target.com -tags headers
nuclei -u target.com -tags http-missing-security-headers
```

### Standardanmeldungen

```bash
# Alle Standardzugangsdaten
nuclei -u target.com -t default-logins/

# Bestimmte Anbieter
nuclei -u target.com -tags tomcat-default
nuclei -u target.com -tags apache-default
```

### Übernahme von Subdomains

```bash
# Alle Prüfungen auf mögliche Übernahmen
nuclei -u target.com -t takeovers/

# Bestimmte Anbieter
nuclei -u target.com -tags aws-takeover
nuclei -u target.com -tags azure-takeover
nuclei -u target.com -tags github-takeover
```

### Technologieerkennung

```bash
# Technologieerkennung
nuclei -u target.com -t technologies/

# CMS erkennen
nuclei -u target.com -tags tech,wordpress
nuclei -u target.com -tags tech,joomla
```

### Offengelegte Daten

```bash
# Offengelegte vertrauliche Dateien
nuclei -u target.com -t exposures/

# Bestimmte offengelegte Daten
nuclei -u target.com -tags config
nuclei -u target.com -tags backup
nuclei -u target.com -tags logs
nuclei -u target.com -tags git
```

---

## 🎯 Ziele scannen

### Einzelnes Ziel

```bash
nuclei -u https://example.com
nuclei -u https://example.com:8080
```

### Mehrere Ziele

```bash
# Aus einer Datei
nuclei -l targets.txt

# Aus der Standardeingabe (Verarbeitungskette)
cat targets.txt | nuclei
echo "https://example.com" | nuclei

# Mehrere URLs direkt angeben
nuclei -u https://site1.com -u https://site2.com
```

### Mit anderen Werkzeugen

```bash
# Mit subfinder (Subdomains)
subfinder -d example.com | nuclei

# Mit httpx (erreichbare Hosts)
subfinder -d example.com | httpx | nuclei

# Mit katana (durch Crawling erfasste URLs)
katana -u https://example.com | nuclei

# Vollständige Aufklärung als Verarbeitungskette
subfinder -d example.com -silent | httpx -silent | nuclei -silent
```

---

<a name="-vorlagen-auswählen"></a>

## 🏷️ Vorlagen auswählen

### Nach Vorlagenpfad

```bash
# Einzelne Vorlage
nuclei -u target.com -t cves/2023/CVE-2023-1234.yaml

# Vorlagenverzeichnis
nuclei -u target.com -t cves/2023/
nuclei -u target.com -t exposed-panels/

# Mehrere Verzeichnisse
nuclei -u target.com -t cves/ -t exposed-panels/

# Eigener Vorlagenpfad
nuclei -u target.com -t /path/to/custom/templates/
```

### Nach Tags

```bash
# Einzelner Tag
nuclei -u target.com -tags cve

# Mehrere Tags (ODER)
nuclei -u target.com -tags cve,rce,sqli

# Häufige Tags
nuclei -u target.com -tags xss
nuclei -u target.com -tags ssrf
nuclei -u target.com -tags lfi
nuclei -u target.com -tags rce
nuclei -u target.com -tags injection
```

### Nach Schweregrad

```bash
# Einzelner Schweregrad
nuclei -u target.com -severity critical
nuclei -u target.com -severity high
nuclei -u target.com -severity medium
nuclei -u target.com -severity low
nuclei -u target.com -severity info

# Mehrere Schweregrade
nuclei -u target.com -severity critical,high

# Schweregrade ausschließen
nuclei -u target.com -exclude-severity info,low
```

### Vorlagen ausschließen

```bash
# Bestimmte Vorlagen ausschließen
nuclei -u target.com -exclude-templates cves/2020/

# Nach Tags ausschließen
nuclei -u target.com -exclude-tags dos,fuzz

# Nach ID ausschließen
nuclei -u target.com -exclude-id CVE-2021-1234
```

---

## 📤 Ausgabeoptionen

### Ausgabeformate

```bash
# Standardausgabe
nuclei -u target.com -o results.txt

# JSON-Ausgabe
nuclei -u target.com -json -o results.json

# JSON Lines
nuclei -u target.com -jsonl -o results.jsonl

# Markdown
nuclei -u target.com -markdown-export report/
```

### Ausgabe filtern

```bash
# Reduzierte Ausgabe (nur Ergebnisse)
nuclei -u target.com -silent

# Keine Farben
nuclei -u target.com -nc

# Ausführliche Ausgabe
nuclei -u target.com -v

# Diagnoseausgabe
nuclei -u target.com -debug
```

### Exportoptionen

```bash
# SARIF (GitHub)
nuclei -u target.com -sarif-export results.sarif

# Markdown-Bericht
nuclei -u target.com -me reports/
```

---

## ⚡ Erweiterte Funktionen

### Anfragerate begrenzen

```bash
# Anfragen pro Sekunde
nuclei -u target.com -rate-limit 100

# Größe der gleichzeitig verarbeiteten Zielgruppe
nuclei -u target.com -bulk-size 25

# Parallelität
nuclei -u target.com -c 50

# Parallel ausgeführte Vorlagen
nuclei -u target.com -rl 100 -c 50
```

### Proxy-Unterstützung

```bash
# HTTP-Proxy
nuclei -u target.com -proxy http://127.0.0.1:8080

# SOCKS-Proxy
nuclei -u target.com -proxy socks5://127.0.0.1:9050

# Burp-Suite-Proxy
nuclei -u target.com -proxy http://127.0.0.1:8080
```

### Authentifizierung

```bash
# Authentifizierung über Header
nuclei -u target.com -H "Authorization: Bearer token123"

# Authentifizierung über Cookies
nuclei -u target.com -H "Cookie: session=abc123"

# Mehrere Header
nuclei -u target.com -H "Authorization: Bearer token" -H "X-Custom: value"
```

### Interactsh-Integration

```bash
# Interactsh für Out-of-Band-Tests verwenden
nuclei -u target.com -interactsh-server https://interact.sh

# Eigener Interactsh-Server
nuclei -u target.com -iserver https://my-interactsh.com
```

### Headless-Modus

```bash
# Browser ohne grafische Oberfläche aktivieren
nuclei -u target.com -headless

# Nur Headless-Vorlagen
nuclei -u target.com -t headless/
```

### Arbeitsabläufe

```bash
# Arbeitsablauf ausführen
nuclei -u target.com -w workflows/wordpress-workflow.yaml

# Arbeitsabläufe auflisten
ls ~/.local/nuclei-templates/workflows/
```

---

## 📝 Eigene Vorlagen

### Aufbau einer Vorlage

```yaml
id: my-custom-template

info:
  name: My Custom Template
  author: your-name
  severity: medium
  description: Description of what this template detects
  tags: custom,web

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
    matchers:
      - type: word
        words:
          - "Admin Panel"
```

### Vorlagenbeispiele

#### Bestimmte Seite erkennen
```yaml
id: admin-panel-detect

info:
  name: Admin Panel Detection
  author: hunter
  severity: info
  tags: panel

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
      - "{{BaseURL}}/administrator"
      - "{{BaseURL}}/wp-admin"
    matchers-condition: or
    matchers:
      - type: status
        status:
          - 200
```

#### Header prüfen
```yaml
id: missing-security-header

info:
  name: Missing X-Frame-Options
  author: hunter
  severity: low
  tags: headers

http:
  - method: GET
    path:
      - "{{BaseURL}}"
    matchers:
      - type: word
        words:
          - "text/html"
        part: header
    matchers:
      - type: word
        negative: true
        words:
          - "X-Frame-Options"
        part: header
```

### Vorlage validieren

```bash
# Vorlagensyntax prüfen
nuclei -validate -t my-template.yaml

# Vorlage testen
nuclei -u target.com -t my-template.yaml -debug
```

---

## 🔄 Bug-Bounty-Arbeitsablauf

### Vollständige Aufklärung als Verarbeitungskette

```bash
# Schritt 1: Subdomains erfassen
subfinder -d target.com -o subs.txt

# Schritt 2: Erreichbare Hosts ermitteln
cat subs.txt | httpx -o live.txt

# Schritt 3: Nuclei ausführen
nuclei -l live.txt -severity critical,high -o findings.txt

# Einzeiler
subfinder -d target.com -silent | httpx -silent | nuclei -severity critical,high
```

### Nach Priorität scannen

```bash
# Phase 1: Nur kritische Befunde (schnell laut Vorlage)
nuclei -l targets.txt -severity critical -o critical.txt

# Phase 2: Hoher Schweregrad
nuclei -l targets.txt -severity high -o high.txt

# Phase 3: CVEs
nuclei -l targets.txt -t cves/ -o cves.txt

# Phase 4: Vollständiger Scan
nuclei -l targets.txt -o all.txt
```

### Gezielte Scans

```bash
# WordPress-Ziele
nuclei -l wp-sites.txt -tags wordpress

# API-Endpunkte
nuclei -l apis.txt -tags api

# Anmeldeseiten
nuclei -l logins.txt -tags login,default-login
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: Vollständiger Scan
```bash
nuclei -u https://target.com -o results.txt
```

### Beispiel 2: Nur kritische CVEs laut Vorlage
```bash
nuclei -u https://target.com -t cves/ -severity critical,high
```

### Beispiel 3: Schneller Bug-Bounty-Scan
```bash
nuclei -u https://target.com -tags cve,rce,sqli,xss,ssrf -severity critical,high
```

### Beispiel 4: Prüfung auf Subdomain-Übernahme
```bash
cat subs.txt | nuclei -t takeovers/
```

### Beispiel 5: Technologieerkennung
```bash
nuclei -u https://target.com -t technologies/ -silent
```

### Beispiel 6: Mit Proxy (Burp)
```bash
nuclei -u https://target.com -proxy http://127.0.0.1:8080
```

### Beispiel 7: Scan mit Authentifizierung
```bash
nuclei -u https://target.com -H "Authorization: Bearer eyJhbG..."
```

### Beispiel 8: Scan mit begrenzter Anfragerate
```bash
nuclei -l targets.txt -rate-limit 10 -c 5
```

### Beispiel 9: JSON-Ausgabe zur Weiterverarbeitung
```bash
nuclei -u https://target.com -json -o results.json
```

### Beispiel 10: Vollständige Verarbeitungskette
```bash
subfinder -d target.com -silent | \
httpx -silent | \
nuclei -severity critical,high -json -o results.json
```

---

## 📊 Kurzreferenz

### Wichtige Befehle

| Aufgabe | Befehl |
|------|---------|
| Einfacher Scan | `nuclei -u target.com` |
| Mit Vorlagen | `nuclei -u target.com -t cves/` |
| Nach Tags | `nuclei -u target.com -tags cve,rce` |
| Nach Schweregrad | `nuclei -u target.com -severity critical` |
| Aus einer Liste | `nuclei -l urls.txt` |
| Vorlagen aktualisieren | `nuclei -ut` |
| Reduzierte Ausgabe | `nuclei -u target.com -silent` |
| JSON-Ausgabe | `nuclei -u target.com -json -o out.json` |

### Häufige Tags

| Tag | Beschreibung |
|-----|-------------|
| `cve` | CVE-Schwachstellen |
| `rce` | Befehlsausführung aus der Ferne |
| `sqli` | SQL-Injection |
| `xss` | Cross-Site Scripting |
| `ssrf` | Server-Side Request Forgery |
| `lfi` | Local File Inclusion |
| `panel` | Administrationsoberflächen |
| `takeover` | Übernahme von Subdomains |

### Schweregrade

| Schweregrad | Farbe | Priorität |
|----------|-------|----------|
| `critical` | 🔴 Rot | Sofort |
| `high` | 🟠 Orange | Hoch |
| `medium` | 🟡 Gelb | Mittel |
| `low` | 🟢 Grün | Niedrig |
| `info` | 🔵 Blau | Informativ |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise zur Leistung

```bash
# Laut Vorlage geeignete Einstellungen für große Scans
nuclei -l targets.txt -c 50 -rl 150 -bs 25

# Reduzierte Ausgabe für Verarbeitungsketten
nuclei -l targets.txt -silent -o results.txt
```

### Hinweise zur Bug-Bounty-Suche

1. **Mit kritischen und hohen Schweregraden beginnen**
   ```bash
   nuclei -l targets.txt -severity critical,high
   ```

2. **Nach leicht überprüfbaren Befunden suchen**
   ```bash
   nuclei -l targets.txt -tags takeover,exposed-panels
   ```

3. **Aktuelle Vorlagen verwenden**
   ```bash
   nuclei -ut  # Vor der Suche stets aktualisieren
   ```

4. **Anfragerate für einen stabilen Ablauf begrenzen**
   ```bash
   nuclei -l targets.txt -rl 50
   ```

### Hinweise zur Vermeidung einer Erkennung

```bash
# Langsam und mit geringer Parallelität
nuclei -l targets.txt -rl 10 -c 2

# Über einen Proxy
nuclei -l targets.txt -proxy socks5://127.0.0.1:9050
```

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** Nuclei ist ein leistungsfähiger Schwachstellenscanner und darf nur für **autorisierte Tests** eingesetzt werden.
> 
> - ✅ Nur Programme prüfen, für die eine Genehmigung vorliegt
> - ✅ In autorisierten Bug-Bounty-Programmen einsetzen
> - ✅ Eigene Systeme prüfen
> - ❌ Niemals ohne Genehmigung scannen
> - ✅ Anfrageraten und vereinbarten Prüfumfang einhalten
> 
> **Nicht genehmigte Scans sind laut Vorlage rechtswidrig und unethisch.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [Nuclei GitHub](https://github.com/projectdiscovery/nuclei)
- [Nuclei-Vorlagen](https://github.com/projectdiscovery/nuclei-templates)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Dokumentation](https://docs.projectdiscovery.io/tools/nuclei/)

### Ressourcen zu Vorlagen
- [Vorlagen der Community](https://github.com/projectdiscovery/nuclei-templates)
- [Leitfaden zu Vorlagen](https://docs.projectdiscovery.io/templates/)

### Verwandte Spickzettel
- [Nmap](../../Nmap/translations/README.de.md)
- [Burp Suite](../../Burp-Suite/translations/README.de.md)
- [SQLMap](../../SQLMap/translations/README.de.md)

---

<p align="center">
  <b>🎯 Schwachstellenscans sicher beherrschen!</b><br>
  <i>Ein vielseitiges Werkzeug für die Bug-Bounty-Suche</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Versionen und Pfade:** `-ut` und `-update-templates` aktualisieren Vorlagen; die Engine-Aktualisierung erfolgt über die separate Update-Option. Die Beschriftung „Nuclei und Vorlagen aktualisieren“ ist daher zu weit gefasst. Die aktuelle CLI dokumentiert JSONL-Ausgabe und getrennte Exportoptionen; `-json` ist eine historische Referenz. Vorlagenzahlen und Kategorien sind Musterangaben. Pfade wie `cves/` stammen aus älteren Verzeichnisstrukturen; heutige HTTP-Vorlagen liegen unter `http/`. `v3.x.x` und `CVE-2023-XXXXX.yaml` sind Platzhalter, keine vollständigen Versions-/Dateinamen. [Nuclei: CLI-Referenz](https://github.com/projectdiscovery/nuclei/blob/dev/README.md), [Vorlagenrepository](https://github.com/projectdiscovery/nuclei-templates).

> **Fehler im Header-Beispiel:** Das YAML enthält auf derselben Ebene zweimal `matchers`. YAML-Mapping-Schlüssel müssen eindeutig sein; je nach Parser wird die Vorlage abgelehnt oder eine Definition verdrängt. Die beiden Listen bilden in dieser Form keine verlässliche kombinierte Prüfung. Das Seitenbeispiel prüft ausschließlich Status 200 und beweist nicht, dass tatsächlich ein Administrationsbereich vorliegt. Ein fehlender Header allein belegt zudem nicht automatisch eine ausnutzbare Schwachstelle. Die Original-YAML-Schlüssel und sämtliche Zeichenfolgen bleiben unverändert, einschließlich der englischen Metadatenwerte. [YAML-Spezifikation](https://yaml.org/spec/1.2.2/#3211-nodes), [ProjectDiscovery: HTTP-Vorlagen](https://docs.projectdiscovery.io/templates/protocols/http/basic-http).

> **Ablauf und Aussagekraft:** `-c` betrifft die Vorlagenparallelität, `-bulk-size` die parallel verarbeiteten Hosts je Vorlage; eine Anfragerate ist keine allgemeine Garantie für Stabilität oder fehlende Erkennung. Headless-Vorlagen benötigen die passende Aktivierung und Laufzeitumgebung; ein Verzeichnispfad allein ersetzt diese Voraussetzung nicht. Ein „vollständiger Scan“ umfasst nicht automatisch alle verfügbaren oder standardmäßig ausgeschlossenen Vorlagen. Ergebnisse, Schweregrade und Übernahmehinweise müssen im konkreten Kontext bestätigt werden. *OOB* bedeutet Out-of-Band, also eine Beobachtung über einen getrennten Kommunikationskanal. Das widersprüchliche ❌-Symbol vor der Aufforderung, Anfrageraten und Prüfumfang einzuhalten, wurde redaktionell zu ✅ korrigiert. Keine Vorlage, kein Scan und kein Beispielbefehl wurde ausgeführt. [Nuclei: Optionen](https://github.com/projectdiscovery/nuclei/blob/dev/README.md).
