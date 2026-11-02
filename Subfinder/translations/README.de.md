# 🔍 Subfinder – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ____        _     __ _           _           
 / ___| _   _| |__ / _(_)_ __   __| | ___ _ __ 
 \___ \| | | | '_ \ |_| | '_ \ / _` |/ _ \ '__|
  ___) | |_| | |_) |  _| | | | | (_| |  __/ |   
 |____/ \__,_|_.__/|_| |_|_| |_|\__,_|\___|_|   
                                                
   Schnelles Werkzeug zur Ermittlung von Subdomains
```

<p align="center">
  <img src="https://img.shields.io/badge/Subfinder-Subdomain_Discovery-red?style=for-the-badge" alt="Subfinder">
  <img src="https://img.shields.io/badge/Passive-Recon-orange?style=for-the-badge" alt="Passive">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🌐 Schnelles Werkzeug von ProjectDiscovery zur passiven Erfassung von Subdomains</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Grundsyntax](#-grundsyntax)
- [Subdomains ermitteln](#-subdomains-ermitteln)
- [Datenquellen](#-datenquellen)
- [API-Konfiguration](#-api-konfiguration)
- [Ausgabeoptionen](#-ausgabeoptionen)
- [Erweiterte Funktionen](#-erweiterte-funktionen)
- [Bug-Bounty-Arbeitsablauf](#-bug-bounty-arbeitsablauf)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**Subfinder** ist ein schnelles Werkzeug zur passiven Erfassung von Subdomains. Es nutzt verschiedene Quellen, darunter Suchmaschinen, Paste-Seiten und Plattformen mit Aufklärungsdaten.

### Wichtige Funktionen

| Eigenschaft | Beschreibung |
|---------|-------------|
| **Schnell** | In Go geschrieben, mit hoher Parallelität |
| **Passiv** | Keine direkte Interaktion mit dem Ziel laut Vorlage |
| **Mehrere Quellen** | Über 40 Datenquellen laut Vorlage |
| **API-Integration** | Unterstützung zusätzlicher API-Quellen |
| **Für Verarbeitungsketten geeignet** | Lässt sich mit anderen Werkzeugen kombinieren |
| **JSON-Ausgabe** | Unterstützt strukturierte Ausgaben |

### Subfinder im Vergleich zu anderen Werkzeugen

| Eigenschaft | Subfinder | Amass | Assetfinder |
|---------|-----------|-------|-------------|
| **Geschwindigkeit** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| **Quellen** | 40+ | 50+ | 10+ |
| **Aktive Scans** | ❌ Nein | ✅ Ja | ❌ Nein |
| **API-Unterstützung** | ✅ Ja | ✅ Ja | ❌ Nein |
| **Bedienbarkeit** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |

---

## 📥 Installation

### Mit Go
```bash
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
```

### Kali Linux
```bash
sudo apt install subfinder
```

### Binärdatei herunterladen
```bash
# Aus den Releases herunterladen
https://github.com/projectdiscovery/subfinder/releases

# Linux
wget https://github.com/projectdiscovery/subfinder/releases/download/v2.x.x/subfinder_2.x.x_linux_amd64.zip
unzip subfinder_2.x.x_linux_amd64.zip
sudo mv subfinder /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/subfinder:latest
docker run projectdiscovery/subfinder -d example.com
```

### Installation prüfen
```bash
subfinder -version
subfinder -h
```

---

<a name="-grundsyntax"></a>

## ⌨️ Grundsyntax

### Allgemeine Syntax
```bash
subfinder [options] -d <domain>
```

### Wichtige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-d` | Zieldomain |
| `-dL` | Datei mit einer Domainliste |
| `-o` | Ausgabedatei |
| `-oJ` | JSON-Ausgabe |
| `-silent` | Stiller Modus (nur Subdomains) |
| `-v` | Ausführliche Ausgabe |
| `-t` | Anzahl der Threads |
| `-all` | Alle Quellen verwenden |

### Schnelleinstieg
```bash
# Einfache Erfassung
subfinder -d example.com

# Stille Ausgabe (bereinigte Liste)
subfinder -d example.com -silent

# In eine Datei speichern
subfinder -d example.com -o subdomains.txt

# Mehrere Domains
subfinder -dL domains.txt -o all-subs.txt
```

---

## 🔍 Subdomains ermitteln

### Einzelne Domain

```bash
# Einfache Erfassung
subfinder -d target.com

# Ausführliche Ausgabe
subfinder -d target.com -v

# Stiller Modus (nur Subdomains)
subfinder -d target.com -silent

# Quellen anzeigen
subfinder -d target.com -cs
```

### Mehrere Domains

```bash
# Aus einer Datei
subfinder -dL domains.txt

# Ausgabe in eine Datei
subfinder -dL domains.txt -o all_subdomains.txt

# Stiller Modus für Verarbeitungsketten
subfinder -dL domains.txt -silent | httpx
```

### Rekursive Erfassung

```bash
# Rekursive Erfassung aktivieren (laut Vorlage weitere Subdomain-Ebenen finden)
subfinder -d target.com -recursive

# Rekursive Erfassung mit allen Quellen
subfinder -d target.com -recursive -all
```

---

## 📊 Datenquellen

### Kostenlose Quellen (Standard laut Vorlage)

Laut Vorlage verwendet Subfinder diese Quellen standardmäßig ohne API-Schlüssel:
- Alienvault
- AnubisDB
- Crtsh
- Hackertarget
- Rapiddns
- Sublist3r
- ThreatCrowd
- URLScan
- Wayback Machine

### Zusätzliche Quellen (API-Schlüssel erforderlich)

Diese Quellen erfordern API-Schlüssel, um zusätzliche Ergebnisse zu liefern:
- **Shodan**
- **SecurityTrails**
- **VirusTotal**
- **Censys**
- **Chaos**
- **GitHub**
- **PassiveTotal**
- **Binaryedge**
- **WhoisXML**
- **ZoomEye**

### Quellen verwalten

```bash
# Alle Quellen auflisten
subfinder -ls

# Nur bestimmte Quellen verwenden
subfinder -d target.com -sources crtsh,hackertarget,alienvault

# Bestimmte Quellen ausschließen
subfinder -d target.com -es shodan,securitytrails

# Alle Quellen verwenden (einschließlich langsamer Quellen)
subfinder -d target.com -all

# Anzeigen, welche Quelle eine Subdomain gefunden hat
subfinder -d target.com -cs
```

---

## 🔑 API-Konfiguration

### Speicherort der Konfigurationsdatei

```bash
# Standardspeicherort
~/.config/subfinder/provider-config.yaml

# Alternativ einen eigenen Pfad angeben
subfinder -d target.com -pc /path/to/config.yaml
```

### Vorlage für die Konfigurationsdatei

```yaml
# ~/.config/subfinder/provider-config.yaml

binaryedge:
  - your_api_key_here
censys:
  - your_api_id:your_api_secret
certspotter: []
chaos:
  - your_chaos_key
chinaz: []
dnsdb: []
fofa:
  - your_email:your_key
fullhunt: []
github:
  - your_github_token
hunter:
  - your_hunter_key
intelx:
  - your_intelx_key
passivetotal:
  - your_username:your_key
securitytrails:
  - your_security_trails_key
shodan:
  - your_shodan_key
virustotal:
  - your_virustotal_key
whoisxmlapi:
  - your_whoisxml_key
zoomeye:
  - your_zoomeye_key
```

### API-Schlüssel beziehen

| Quelle | Kostenloses Kontingent | URL |
|--------|-----------|-----|
| **VirusTotal** | ✅ Ja | https://www.virustotal.com |
| **SecurityTrails** | ✅ Ja (begrenzt) | https://securitytrails.com |
| **Shodan** | ✅ Ja (begrenzt) | https://shodan.io |
| **Censys** | ✅ Ja | https://censys.io |
| **Chaos** | ✅ Kostenlos (begrenzt) | https://chaos.projectdiscovery.io |
| **GitHub** | ✅ Kostenlos | https://github.com/settings/tokens |

### Kostenlose Grundeinrichtung laut Vorlage

```yaml
# Laut Vorlage empfohlene Mindestkonfiguration für Bug Bounty
virustotal:
  - your_free_key
github:
  - your_github_token
chaos:
  - your_chaos_key
```

---

## 📤 Ausgabeoptionen

### Ausgabeformate

```bash
# Klartext (Standard)
subfinder -d target.com -o subs.txt

# JSON-Ausgabe
subfinder -d target.com -oJ -o subs.json

# JSON-Zeilenformat
subfinder -d target.com -oJL -o subs.jsonl

# CSV-Format
subfinder -d target.com -oD -o subs.csv
```

### Ausgabe filtern

```bash
# Stiller Modus (bereinigte Ausgabe)
subfinder -d target.com -silent

# Nur neue Subdomains anzeigen (bekannte ausschließen)
subfinder -d target.com -exclude-subdomains known.txt

# Ohne Farben
subfinder -d target.com -nc
```

### Ausgabe weiterverarbeiten

```bash
# An httpx weiterleiten
subfinder -d target.com -silent | httpx -silent

# An nuclei weiterleiten
subfinder -d target.com -silent | httpx -silent | nuclei

# Speichern und verarbeiten
subfinder -d target.com -silent | tee subs.txt | httpx
```

---

## ⚡ Erweiterte Funktionen

### Threads und Leistung

```bash
# Threads festlegen (Standard laut Vorlage: 10)
subfinder -d target.com -t 50

# Anfragerate begrenzen
subfinder -d target.com -rate-limit 100

# Zeitlimit
subfinder -d target.com -timeout 30
```

### Ergebnisse auswählen und filtern

```bash
# Bestimmte Muster einschließen
subfinder -d target.com -match "api,dev,stage"

# Muster ausschließen
subfinder -d target.com -filter "www,mail"
```

### Resolver-Optionen

```bash
# Eigene Resolver verwenden
subfinder -d target.com -rL resolvers.txt

# Subdomains auflösen
subfinder -d target.com -nW
```

### Proxy-Unterstützung

```bash
# HTTP-Proxy
subfinder -d target.com -proxy http://127.0.0.1:8080

# SOCKS-Proxy
subfinder -d target.com -proxy socks5://127.0.0.1:9050
```

---

## 🔄 Bug-Bounty-Arbeitsablauf

### Vollständige Verarbeitungskette zur Aufklärung

```bash
# Schritt 1: Subdomains erfassen
subfinder -d target.com -all -o subs.txt

# Schritt 2: Erreichbare Hosts prüfen
cat subs.txt | httpx -o live.txt

# Schritt 3: Mit nuclei scannen
nuclei -l live.txt -severity critical,high -o vulns.txt

# Einzeiler
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Arbeitsablauf für mehrere Domains

```bash
# Alle Domains des Programms erfassen
subfinder -dL scope.txt -all -o all-subs.txt

# Ergebnisse verarbeiten
cat all-subs.txt | httpx -silent | nuclei
```

### Fortlaufende Überwachung

```bash
# Ausgangsstand speichern
subfinder -d target.com -silent > baseline.txt

# Später neue Subdomains ermitteln
subfinder -d target.com -silent > current.txt
diff baseline.txt current.txt | grep ">" | cut -d" " -f2 > new-subs.txt
```

### Mit anderen Werkzeugen kombinieren

```bash
# Mit httpx (erreichbare Hosts)
subfinder -d target.com -silent | httpx

# Mit ffuf (Verzeichnisse durch variierte Eingaben prüfen)
subfinder -d target.com -silent | httpx -silent | \
  xargs -I {} ffuf -u {}/FUZZ -w wordlist.txt

# Mit nmap (Portscans)
subfinder -d target.com -silent | dnsx -a -silent | nmap -iL -

# Vollständige Verarbeitungskette
subfinder -d target.com -silent | \
  httpx -title -status-code -tech-detect -silent | \
  tee recon-results.txt
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: Einfache Erfassung
```bash
subfinder -d hackerone.com -silent
```

### Beispiel 2: Alle Quellen verwenden
```bash
subfinder -d target.com -all -v
```

### Beispiel 3: Ergebnisse speichern
```bash
subfinder -d target.com -o target-subs.txt
```

### Beispiel 4: Mehrere Domains
```bash
echo -e "target1.com\ntarget2.com" | subfinder -silent
```

### Beispiel 5: Quellen anzeigen
```bash
subfinder -d target.com -cs
```

### Beispiel 6: JSON-Ausgabe
```bash
subfinder -d target.com -oJ -o results.json
```

### Beispiel 7: Mit Proxy
```bash
subfinder -d target.com -proxy http://127.0.0.1:8080
```

### Beispiel 8: Hohe Leistung
```bash
subfinder -d target.com -all -t 100 -o subs.txt
```

### Beispiel 9: Vollständige Bug-Bounty-Verarbeitungskette
```bash
subfinder -d target.com -all -silent | \
  httpx -silent -status-code -title | \
  tee live-hosts.txt | \
  nuclei -severity critical,high
```

### Beispiel 10: Rekursive Erfassung
```bash
subfinder -d target.com -recursive -all -silent
```

---

## 📊 Kurzreferenz

### Wichtige Befehle

| Aufgabe | Befehl |
|------|---------|
| Einfache Erfassung | `subfinder -d target.com` |
| Stille Ausgabe | `subfinder -d target.com -silent` |
| In eine Datei speichern | `subfinder -d target.com -o subs.txt` |
| Alle Quellen | `subfinder -d target.com -all` |
| Quellen anzeigen | `subfinder -d target.com -cs` |
| Mehrere Domains | `subfinder -dL domains.txt` |
| JSON-Ausgabe | `subfinder -d target.com -oJ` |
| Quellen auflisten | `subfinder -ls` |

### Häufige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-d` | Zieldomain |
| `-dL` | Datei mit einer Domainliste |
| `-o` | Ausgabedatei |
| `-oJ` | JSON-Ausgabe |
| `-silent` | Stiller Modus |
| `-all` | Alle Quellen verwenden |
| `-cs` | Quelle jeder Subdomain anzeigen |
| `-t` | Threads |
| `-recursive` | Rekursive Erfassung |

### Häufige Verarbeitungsketten

| Verarbeitungskette | Befehl |
|----------|---------|
| Erreichbare Hosts | `subfinder -d target.com -silent \| httpx` |
| Umfassende Aufklärung | `subfinder -d target.com \| httpx \| nuclei` |
| Portscan | `subfinder -d target.com -silent \| dnsx -a \| nmap -iL -` |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise für Bug Bounty

1. **API-Schlüssel konfigurieren**
   ```bash
   # Bessere Ergebnisse mit APIs
   vim ~/.config/subfinder/provider-config.yaml
   ```

2. **Für eine breitere Abdeckung alle Quellen verwenden**
   ```bash
   subfinder -d target.com -all
   ```

3. **Mit anderen Werkzeugen kombinieren**
   ```bash
   subfinder -d target.com -silent | httpx | nuclei
   ```

4. **Neue Subdomains überwachen**
   ```bash
   # Tägliche Prüfung auf neue Systeme
   subfinder -d target.com -silent > today.txt
   ```

### Hinweise zur Leistung

```bash
# Hohe Threadzahl
subfinder -d target.com -t 100 -all

# Zeitlimit für langsame Quellen
subfinder -d target.com -timeout 60
```

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** Subfinder arbeitet laut Vorlage passiv und greift nicht direkt auf Zielsysteme zu. Nachgeschaltete Werkzeuge in einer Verarbeitungskette können dies jedoch tun.
> 
> - ✅ Nur in autorisierten Programmen verwenden
> - ✅ Nur Bug-Bounty-Ziele innerhalb des vereinbarten Umfangs
> - ✅ Eigene Domains
> - ❌ Keine Ziele ohne Genehmigung erfassen
> - ❌ Den vereinbarten Programmumfang nicht überschreiten
> 
> **Immer sicherstellen, dass eine Genehmigung vorliegt.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [Subfinder GitHub](https://github.com/projectdiscovery/subfinder)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Dokumentation](https://docs.projectdiscovery.io/tools/subfinder/)

### Verwandte Spickzettel
- [Nuclei](../../Nuclei/translations/README.de.md)
- [ffuf](../../ffuf/translations/README.de.md)
- [Nmap](../../Nmap/translations/README.de.md)

---

<p align="center">
  <b>🔍 Subdomains systematisch erfassen!</b><br>
  <i>Der erste Schritt bei der Bug-Bounty-Aufklärung</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Quellen und Grenzen:** Die Zahl und Auswahl der Quellen sowie kostenlose Kontingente sind Angaben der Vorlage, keine aktuelle Bestands- oder Tarifprüfung. `-all` ersetzt keine API-Schlüssel und garantiert keine vollständige Erfassung. Anbieter, Schlüsselanforderungen und Konfigurationsfelder hängen von Version und Zugang ab. `v2.x.x` ist ein Platzhalter, keine vollständige Release-Adresse.

> **Rekursion und Auflösung:** `-recursive` wählt nur Quellen aus, die Abfragen für weitere Subdomain-Ebenen unterstützen; es führt nicht pauschal eine vollständige rekursive Suche aus. `-t` steuert laut aktueller Hilfe die parallele DNS-Auflösung im aktiven Modus. `-nW` aktiviert eine Prüfung auf aktive Subdomains; die pauschale Beschreibung „keine direkte Interaktion“ gilt deshalb nicht für jede Option oder nachgeschaltete Verarbeitungskette. [ProjectDiscovery: Subfinder-Verwendung](https://docs.projectdiscovery.io/opensource/subfinder/usage).

> **Ausgabeoptionen:** Die aktuelle Hilfe beschreibt `-oJ` als JSONL-Ausgabe und `-cs` für die Quellensammlung nur zusammen mit JSON. `-oD` bezeichnet ein Ausgabeverzeichnis bei Domainlisten, kein CSV-Format. `-oJL` und `-exclude-subdomains` stehen nicht in der geprüften aktuellen Optionenliste. Die Originalaufrufe bleiben unverändert. [Offizielle Subfinder-Referenz](https://github.com/projectdiscovery/subfinder).

> **Verarbeitungsketten:** DNS-Namen, IP-Adressen und HTTP-URLs sind unterschiedliche Eingabeformate. Die Form der dnsx-/httpx-Ausgabe muss zum nächsten Werkzeug passen. Zusätzliche Status-/Titelspalten sind keine reine URL-Liste. `diff` vergleicht Zeilenfolgen; ungeordnete oder veränderliche Quellenergebnisse belegen allein keine neuen Systeme. Musterdateien und API-Werte werden vorausgesetzt. Alle Befehle, YAML-Werte und Queries bleiben wörtlich erhalten; es wurde kein Beispiel ausgeführt.
