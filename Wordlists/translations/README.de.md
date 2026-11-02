# 📚 Wordlists – Umfassender Leitfaden für Wortlisten

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ██╗███████╗████████╗███████╗
  ██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██║██╔════╝╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     ██║███████╗   ██║   ███████╗
  ██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██║╚════██║   ██║   ╚════██║
  ╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗██║███████║   ██║   ███████║
   ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Wordlists-Reference-red?style=for-the-badge" alt="Wordlists">
  <img src="https://img.shields.io/badge/SecLists-orange?style=for-the-badge" alt="SecLists">
  <img src="https://img.shields.io/badge/Brute_Force-blue?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/Fuzzing-green?style=for-the-badge" alt="Fuzzing">
</p>

<p align="center">
  <b>📖 Umfassende Referenz zu Wortlisten für Penetrationstests</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

## 📋 Inhaltsverzeichnis

- [Passwortlisten](#passwortlisten)
- [Wortlisten für Verzeichnisse und Dateien](#verzeichnisse-und-dateien)
- [Wortlisten für Subdomains](#subdomains)
- [Listen mit Benutzernamen](#benutzernamen)
- [Wortlisten für Web-Fuzzing](#web-fuzzing)
- [SecLists-Sammlung](#seclists)
- [Standardwortlisten unter Kali](#kali-wortlisten)
- [Werkzeuge für eigene Wortlisten](#eigene-wortlisten)
- [Schneller Download](#schneller-download)
- [Tipps und bewährte Vorgehensweisen](#tipps)
- [Kurzreferenz](#kurzreferenz)
- [Weiterführende Quellen](#quellen)
- [Hinweise zum Original und zu Versionen](#hinweise)

---

<a id="passwortlisten"></a>

## 🔐 Passwortlisten

### Besonders verbreitete Listen

| Wortliste | Größe | Zeilen | Quelle | Einsatzgebiet |
|----------|------|-------|--------|----------|
| **rockyou.txt** | 139 MB | 14.3M | Kali/SecLists | Passwörter knacken |
| **darkweb2017-top10000.txt** | 83 KB | 10K | SecLists | Häufige Passwörter |
| **xato-net-10-million-passwords.txt** | 83 MB | 10M | SecLists | Umfangreiche Passwortliste |
| **common-passwords-win.txt** | - | - | SecLists | Windows-Passwörter |
| **10-million-password-list-top-1000000.txt** | 8.1 MB | 1M | SecLists | Die häufigsten 1M Passwörter |

### Passwortlisten nach Typ

```
SecLists/Passwords/
├── Common-Credentials/
│   ├── 10-million-password-list-top-100.txt
│   ├── 10-million-password-list-top-1000.txt
│   ├── 10-million-password-list-top-10000.txt
│   ├── 10-million-password-list-top-100000.txt
│   └── 10-million-password-list-top-1000000.txt
├── Leaked-Databases/
│   └── rockyou.txt
├── darkweb2017-top1000.txt
├── darkweb2017-top10000.txt
└── Default-Credentials/
    ├── default-passwords.txt
    └── tomcat-betterdefaultpasslist.txt
```

### Spezielle Passwortlisten

| Wortliste | Zweck | Speicherort |
|----------|---------|----------|
| **wifi-passwords.txt** | WLAN-Passwörter knacken | SecLists/Passwords/ |
| **probable-v2-top12000.txt** | Statistische Analyse | SecLists/Passwords/ |
| **seasons-only.txt** | Jahreszeitliche Muster | SecLists/Passwords/Honeypot-Captures/ |
| **twitter-banned.txt** | Gesperrte Passwörter | SecLists/Passwords/ |
| **cirt-default-passwords.txt** | Standardzugangsdaten | SecLists/Passwords/Default-Credentials/ |

### Speicherorte unter Kali

```bash
# RockYou (zuerst entpacken)
/usr/share/wordlists/rockyou.txt.gz
gunzip /usr/share/wordlists/rockyou.txt.gz

# John the Ripper
/usr/share/john/password.lst

# Metasploit
/usr/share/metasploit-framework/data/wordlists/
```

---

<a id="verzeichnisse-und-dateien"></a>

## 📂 Wortlisten für Verzeichnisse und Dateien

### Verzeichnisse per Brute Force ermitteln

| Wortliste | Größe | Zeilen | Quelle | Einsatzgebiet |
|----------|------|-------|--------|----------|
| **common.txt** | 36 KB | 4.6K | DirBuster | Schneller Scan |
| **directory-list-2.3-small.txt** | 756 KB | 87K | DirBuster | Kleine Bestandsaufnahme |
| **directory-list-2.3-medium.txt** | 1.9 MB | 220K | DirBuster | Standardmäßige Bestandsaufnahme |
| **directory-list-2.3-big.txt** | 14 MB | 1.2M | DirBuster | Umfassende Bestandsaufnahme |
| **big.txt** | 210 KB | 20K | SecLists | Häufige Verzeichnisse |

### RAFT-Reihe (besonders empfohlen!)

```
SecLists/Discovery/Web-Content/
├── raft-large-directories.txt      # 62K Verzeichnisse
├── raft-large-files.txt            # 37K Dateien
├── raft-large-words.txt            # 120K Wörter
├── raft-medium-directories.txt     # 30K Verzeichnisse
├── raft-medium-files.txt           # 17K Dateien
├── raft-medium-words.txt           # 63K Wörter
├── raft-small-directories.txt      # 20K Verzeichnisse
├── raft-small-files.txt            # 11K Dateien
└── raft-small-words.txt            # 43K Wörter
```

### Listen mit Dateiendungen

| Wortliste | Zweck |
|----------|---------|
| **web-extensions.txt** | Häufige Dateiendungen im Web |
| **backup-extensions.txt** | Sicherungsdateien (.bak, .old) |
| **raft-large-extensions.txt** | Umfangreiche Liste mit Dateiendungen |

### Gezielte Suche nach Inhalten

| Wortliste | Zweck | Zeilen |
|----------|---------|-------|
| **apache.txt** | Apache-spezifisch | - |
| **nginx.txt** | Nginx-spezifisch | - |
| **iis.txt** | IIS-spezifisch | - |
| **api-endpoints.txt** | API-Endpunkte ermitteln | - |
| **graphql.txt** | GraphQL-Endpunkte | - |
| **swagger.txt** | Swagger/OpenAPI | - |

### Übliche Speicherorte

```bash
# DirBuster
/usr/share/dirbuster/wordlists/
/usr/share/dirb/wordlists/

# SecLists für die Bestandsaufnahme
/usr/share/seclists/Discovery/Web-Content/

# Schnelle Downloads
common.txt
directory-list-2.3-medium.txt
raft-medium-directories.txt
```

---

<a id="subdomains"></a>

## 🌐 Wortlisten für Subdomains

### Verbreitete Subdomain-Listen

| Wortliste | Zeilen | Quelle | Einsatzgebiet |
|----------|-------|--------|----------|
| **subdomains-top1million-5000.txt** | 5K | SecLists | Schnelle Bestandsaufnahme |
| **subdomains-top1million-20000.txt** | 20K | SecLists | Standardmäßige Bestandsaufnahme |
| **subdomains-top1million-110000.txt** | 110K | SecLists | Gründliche Bestandsaufnahme |
| **dns-Jhaddix.txt** | 2M+ | SecLists | Bug Bounty |
| **fierce-hostlist.txt** | - | SecLists | Mit Fierce kompatibel |
| **combined_subdomains.txt** | 648K | SecLists | Zusammengeführte Liste |

### Speicherort der Subdomain-Listen

```
SecLists/Discovery/DNS/
├── subdomains-top1million-5000.txt
├── subdomains-top1million-20000.txt
├── subdomains-top1million-110000.txt
├── dns-Jhaddix.txt                    # Besonders für Bug Bountys geeignet!
├── combined_subdomains.txt
├── deepmagic.com-prefixes-top500.txt
├── deepmagic.com-prefixes-top50000.txt
├── fierce-hostlist.txt
├── namelist.txt
├── shubs-subdomains.txt
└── bitquark-subdomains-top100000.txt
```

### Für Bug Bountys empfohlen

```bash
# Methodik von Jason Haddix
dns-Jhaddix.txt              # 2M+ Subdomains

# Für eine gründliche Suche zusammenführen
cat subdomains-top1million-110000.txt dns-Jhaddix.txt | sort -u > mega-subdomain.txt
```

---

<a id="benutzernamen"></a>

## 👤 Listen mit Benutzernamen

### Verbreitete Benutzernamenlisten

| Wortliste | Zeilen | Zweck |
|----------|-------|---------|
| **top-usernames-shortlist.txt** | 17 | Schnelle Prüfung |
| **xato-net-10-million-usernames.txt** | 10M | Umfangreiche Liste |
| **cirt-default-usernames.txt** | - | Standardbenutzer |
| **names.txt** | - | Häufige Namen |
| **familynames-usa-top1000.txt** | 1K | Nachnamen |

### Speicherort der Benutzernamenlisten

```
SecLists/Usernames/
├── top-usernames-shortlist.txt
├── xato-net-10-million-usernames.txt
├── cirt-default-usernames.txt
├── Names/
│   ├── names.txt
│   ├── familynames-usa-top1000.txt
│   └── malenames-usa-top1000.txt
└── Honeypot-Captures/
    └── multiplesources-users-fabian-fingerle.de.txt
```

### Standardzugangsdaten

| Dienst | Wortliste |
|---------|----------|
| **FTP** | ftp-betterdefaultpasslist.txt |
| **SSH** | ssh-betterdefaultpasslist.txt |
| **Tomcat** | tomcat-betterdefaultpasslist.txt |
| **MySQL** | mysql-betterdefaultpasslist.txt |
| **MSSQL** | mssql-betterdefaultpasslist.txt |
| **PostgreSQL** | postgres-betterdefaultpasslist.txt |

---

<a id="web-fuzzing"></a>

## 💉 Wortlisten für Web-Fuzzing

### SQL-Injection

```
SecLists/Fuzzing/SQLi/
├── Generic-SQLi.txt
├── quick-SQLi.txt
├── MSSQL.fuzzdb.txt
├── MySQL.fuzzdb.txt
├── PostgreSQL.fuzzdb.txt
└── oracle.fuzzdb.txt
```

### XSS (Cross-Site Scripting)

```
SecLists/Fuzzing/XSS/
├── XSS-Bypass-Strings-BruteLogic.txt
├── XSS-Cheat-Sheet-PortSwigger.txt
├── XSS-Jhaddix.txt
├── XSS-RSNAKE.txt
├── XSS-Vectors-Mario.txt
└── xss-without-parentheses-semi-colons-portswigger.txt
```

### LFI/RFI (Einbinden lokaler oder entfernter Dateien)

```
SecLists/Fuzzing/LFI/
├── LFI-Jhaddix.txt
├── LFI-LFISuite-pathtotest-huge.txt
├── LFI-LFISuite-pathtotest.txt
└── LFI-gracefulsecurity-linux.txt
```

### Command Injection (Einschleusen von Befehlen)

```
SecLists/Fuzzing/command-injection-commix.txt
SecLists/Fuzzing/command-injection-testing-unix.txt
SecLists/Fuzzing/command-injection-testing-windows.txt
```

### SSTI (Injection in serverseitige Templates)

```
SecLists/Fuzzing/SSTI/
├── SSTI.txt
└── tplmap-template-injection.txt
```

### Sammlungen für Fuzzing

| Wortliste | Zweck |
|----------|---------|
| **special-chars.txt** | Sonderzeichen |
| **Unicode.txt** | Unicode-Fuzzing |
| **big-list-of-naughty-strings.txt** | Grenzfälle |
| **fuzz-Bo0oM.txt** | Allgemeines Fuzzing |
| **burp-parameter-names.txt** | Parameternamen |

---

<a id="seclists"></a>

## 📦 SecLists-Sammlung

### Über SecLists

**SecLists** begleitet Penetrationstester bei der Arbeit: Die Sammlung enthält unterschiedliche Arten von Listen für Sicherheitsprüfungen.

### Installation

```bash
# Kali Linux (vorinstalliert)
ls /usr/share/seclists/

# Ubuntu/Debian
sudo apt install seclists

# Git-Repository klonen
git clone https://github.com/danielmiessler/SecLists.git

# Schneller Download (einzelne Listen)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt
```

### Struktur von SecLists

```
SecLists/
├── Discovery/
│   ├── DNS/                    # Wortlisten für Subdomains
│   ├── Web-Content/            # Wortlisten für Verzeichnisse und Dateien
│   └── Infrastructure/         # Dienste/Ports
├── Fuzzing/
│   ├── SQLi/                   # SQL-Injection-Payloads
│   ├── XSS/                    # XSS-Payloads
│   ├── LFI/                    # LFI-/RFI-Payloads
│   └── command-injection*.txt  # Command Injection
├── Passwords/
│   ├── Common-Credentials/     # Passwortlisten
│   ├── Leaked-Databases/       # rockyou usw.
│   └── Default-Credentials/    # Standardpasswörter
├── Usernames/
│   ├── Names/                  # Namenslisten
│   └── top-usernames*.txt      # Benutzernamenlisten
├── Payloads/
│   └── Anti-Antivirus/         # Virenschutz umgehen
└── Pattern-Matching/
    └── Hunting/                # Regex-Muster
```

### GitHub-Repository

🔗 https://github.com/danielmiessler/SecLists

---

<a id="kali-wortlisten"></a>

## 🐧 Standardwortlisten unter Kali

### Speicherort der Wortlisten

```bash
# Hauptverzeichnis für Wortlisten
/usr/share/wordlists/

# Inhalt
ls -la /usr/share/wordlists/
├── rockyou.txt.gz          # Mit gunzip entpacken
├── dirb/                   # DIRB-Wortlisten
├── dirbuster/              # DirBuster-Wortlisten
├── fasttrack.txt           # Fasttrack-Liste
├── fern-wifi/              # WLAN-Wortlisten
├── metasploit/             # MSF-Wortlisten
├── nmap.lst                # Liste von Nmap-Skripten
├── wfuzz/                  # wFuzz-Wortlisten
└── seclists -> /usr/share/seclists/
```

### DirBuster-Listen

```bash
ls /usr/share/dirbuster/wordlists/
├── apache-user-enum-1.0.txt
├── apache-user-enum-2.0.txt
├── directories.jbrofuzz
├── directory-list-1.0.txt
├── directory-list-2.3-big.txt
├── directory-list-2.3-medium.txt
├── directory-list-2.3-small.txt
└── directory-list-lowercase-2.3-big.txt
```

### DIRB-Listen

```bash
ls /usr/share/dirb/wordlists/
├── big.txt                  # 20K Einträge
├── common.txt               # 4.6K Einträge
├── small.txt                # 959 Einträge
├── catala.txt               # Katalanisch
├── euskera.txt              # Baskisch
├── extensions_common.txt    # Dateiendungen
├── indexes.txt              # Indexdateien
├── mutations_common.txt     # Abwandlungen
├── spanish.txt              # Spanisch
├── stress/                  # Belastungstests
└── vulns/                   # Schwachstellenprüfungen
```

---

<a id="eigene-wortlisten"></a>

## 🛠️ Werkzeuge für eigene Wortlisten

### CeWL (Generator für eigene Wortlisten)

```bash
# Installation
sudo apt install cewl

# Grundlegende Verwendung – Website durchsuchen
cewl https://target.com -w wordlist.txt

# Mit Suchtiefe und Mindestwortlänge
cewl https://target.com -d 3 -m 6 -w wordlist.txt

# E-Mail-Adressen einbeziehen
cewl https://target.com -e -w wordlist.txt

# Optionen
-d <depth>      # Suchtiefe des Crawlers (Standard: 2)
-m <length>     # Mindestwortlänge (Standard: 3)
-w <file>       # Ausgabedatei
-e              # E-Mail-Adressen einbeziehen
--lowercase     # In Kleinbuchstaben umwandeln
-c              # Wortvorkommen zählen
```

### Crunch (Wortlistengenerator)

```bash
# Installation
sudo apt install crunch

# Grundsyntax
crunch <min-len> <max-len> [charset] -o <output>

# Passwörter mit 4–6 Zeichen erzeugen
crunch 4 6 -o wordlist.txt

# Mit vorgegebenen Zeichen
crunch 8 8 abcdefghijklmnopqrstuvwxyz0123456789 -o wordlist.txt

# Mit Muster
crunch 6 6 -t @@%%^^ -o wordlist.txt
# @ = Kleinbuchstabe, , = Großbuchstabe, % = Ziffer, ^ = Sonderzeichen

# Eigener Zeichensatz
crunch 4 4 -f /usr/share/crunch/charset.lst mixalpha-numeric -o wordlist.txt

# Über eine Pipe an hashcat weitergeben
crunch 8 8 0123456789 | hashcat -m 0 hash.txt
```

### CUPP (Common User Passwords Profiler)

```bash
# Installation
git clone https://github.com/Mebus/cupp.git
cd cupp

# Interaktiver Modus – profilbasiert
python3 cupp.py -i

# Abgefragte Angaben:
# - Vorname, Nachname, Spitzname
# - Geburtsdatum
# - Angaben zur Partnerin oder zum Partner
# - Name des Haustiers
# - Firmenname
# - Schlüsselwörter

# Häufige Passwörter herunterladen
python3 cupp.py -l

# Vorhandene Wortliste durch Profilbildung ergänzen
python3 cupp.py -w wordlist.txt
```

### Mentalist (Wortlistengenerator mit grafischer Oberfläche)

```bash
# Grafisches Werkzeug zum Erzeugen komplexer Wortlisten
# Unterstützt Regeln, Änderungen der Groß-/Kleinschreibung und Ersetzungen
# Download von: https://github.com/sc0tfree/mentalist
```

### Wordlister (Python)

```python
#!/usr/bin/env python3
# Einfacher Generator für eigene Wortlisten
import itertools

# Kombinationen erzeugen
chars = 'abc123'
length = 4

for combo in itertools.product(chars, repeat=length):
    print(''.join(combo))
```

### Hashcat-Regeln

```bash
# Wortliste mithilfe von Regeln abwandeln
hashcat -r /usr/share/hashcat/rules/best64.rule wordlist.txt --stdout

# Verbreitete Regeln
/usr/share/hashcat/rules/
├── best64.rule         # Die besten 64 Regeln
├── rockyou-30000.rule  # Auf RockYou basierend
├── d3ad0ne.rule        # Regeln von d3ad0ne
├── dive.rule           # DIVE-Regeln
└── toggles1-5.rule     # Groß-/Kleinschreibung wechseln
```

---

<a id="schneller-download"></a>

## ⬇️ Schneller Download

### Downloads als Einzeiler

```bash
# RockYou
wget https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt

# SecLists (vollständig)
git clone --depth 1 https://github.com/danielmiessler/SecLists.git

# Häufige Passwörter (1M)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt

# Mittelgroße Verzeichnisliste
wget https://raw.githubusercontent.com/daviddias/node-dirbuster/master/lists/directory-list-2.3-medium.txt

# Subdomain-Liste
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/subdomains-top1million-110000.txt

# Jhaddix DNS (Bug Bounty)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/dns-Jhaddix.txt
```

### Repositorys klonen

```bash
# SecLists
git clone https://github.com/danielmiessler/SecLists.git

# FuzzDB
git clone https://github.com/fuzzdb-project/fuzzdb.git

# PayloadsAllTheThings
git clone https://github.com/swisskyrepo/PayloadsAllTheThings.git

# Assetnote Wordlists
git clone https://github.com/assetnote/wordlists.git
```

---

<a id="tipps"></a>

## 💡 Tipps und bewährte Vorgehensweisen

### Die passende Wortliste auswählen

| Aufgabe | Empfohlene Wortliste |
|------|---------------------|
| **Schneller Verzeichnisscan** | common.txt, raft-small-directories.txt |
| **Gründlicher Verzeichnisscan** | directory-list-2.3-medium.txt |
| **Umfassender Verzeichnisscan** | directory-list-2.3-big.txt |
| **Subdomains ermitteln** | subdomains-top1million-20000.txt |
| **DNS bei Bug Bountys** | dns-Jhaddix.txt |
| **Passwörter knacken** | rockyou.txt |
| **Schneller Passworttest** | top-10000.txt |

### Wortlisten zusammenführen

```bash
# Zusammenführen und Duplikate entfernen
cat list1.txt list2.txt | sort -u > combined.txt

# Allen Zeilen ein Präfix voranstellen
sed 's/^/admin_/' wordlist.txt > admin_wordlist.txt

# Ein Suffix anhängen
sed 's/$/.php/' wordlist.txt > php_wordlist.txt

# Beide Varianten
while read line; do echo "${line}.php"; echo "${line}.html"; done < wordlist.txt > extended.txt
```

### Wortlisten optimieren

```bash
# Sortieren und Duplikate entfernen
sort -u wordlist.txt -o wordlist.txt

# Leere Zeilen entfernen
sed -i '/^$/d' wordlist.txt

# Zeilen mit weniger als 4 Zeichen entfernen
awk 'length >= 4' wordlist.txt > filtered.txt

# In Kleinbuchstaben umwandeln
tr '[:upper:]' '[:lower:]' < wordlist.txt > lowercase.txt

# Zeilen zählen
wc -l wordlist.txt
```

### Tipps zur Effizienz

1. **Klein anfangen** – Zuerst kleinere Listen verwenden und den Umfang anschließend erweitern
2. **Muster nutzen** – Bei bekanntem Format gezielt passende Muster verwenden
3. **Werkzeuge kombinieren** – Eigene Listen erzeugen und mit Standardlisten ergänzen
4. **Gezielte Listen erstellen** – Mit CeWL auf das Ziel zugeschnittene Listen erstellen

---

<a id="kurzreferenz"></a>

## 📊 Kurzreferenz

### Empfohlene Wortlisten nach Kategorie

| Kategorie | Empfehlung | Speicherort |
|----------|-------------|----------|
| **Passwörter** | rockyou.txt | SecLists/Passwords/ |
| **Verzeichnisse** | directory-list-2.3-medium.txt | DirBuster |
| **Dateien** | raft-large-files.txt | SecLists |
| **Subdomains** | dns-Jhaddix.txt | SecLists/Discovery/DNS/ |
| **Benutzernamen** | xato-net-10-million-usernames.txt | SecLists/Usernames/ |
| **SQLi** | Generic-SQLi.txt | SecLists/Fuzzing/SQLi/ |
| **XSS** | XSS-Jhaddix.txt | SecLists/Fuzzing/XSS/ |
| **LFI** | LFI-Jhaddix.txt | SecLists/Fuzzing/LFI/ |

### Orientierung zur Listengröße

| Größe | Zeilen | Einsatzgebiet |
|------|-------|----------|
| **Klein** | < 5K | Schnelle Tests |
| **Mittel** | 5K - 100K | Standardmäßige Bestandsaufnahme |
| **Groß** | 100K - 1M | Gründlicher Scan |
| **Sehr groß** | > 1M | Vollständige Abdeckung |

---

<a id="quellen"></a>

## 📚 Weiterführende Quellen

### Wortlisten-Repositorys
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Assetnote Wordlists](https://github.com/assetnote/wordlists)

### Werkzeuge
- [CeWL](https://github.com/digininja/CeWL)
- [CUPP](https://github.com/Mebus/cupp)
- [Crunch](https://sourceforge.net/projects/crunch-wordlist/)
- [Mentalist](https://github.com/sc0tfree/mentalist)

### Verwandte Cheatsheets
- [Hydra](../../Hydra/translations/README.de.md)
- [John the Ripper](../../John-The-Ripper/translations/README.de.md)
- [Hashcat](../../Hashcat/translations/README.de.md)
- [Gobuster](../../Gobuster/translations/README.de.md)
- [ffuf](../../ffuf/translations/README.de.md)


---

<a id="hinweise"></a>

## 📝 Hinweise zum Original und zu Versionen

Die Beispiele, Dateinamen und Größenangaben des englischen Originals bleiben erhalten; folgende Einschränkungen helfen bei ihrer Einordnung (Quellenprüfung: 02.11.2026):

- **Dateinamen und Größen:** Die Verzeichnisbäume sind schematische Momentaufnahmen, keine vollständige aktuelle Dateiliste. Namen, Speicherorte, Komprimierung und Zeilenzahlen ändern sich. Im geprüften SecLists-Baum liegt beispielsweise die Datenbank-Fuzzing-Sammlung unter `Fuzzing/Databases/`; die gezeigten Pfade `Fuzzing/SQLi/` und `Fuzzing/SSTI/` sind dort nicht als entsprechende Verzeichnisse vorhanden. Auch die Kurzangabe für RockYou ersetzt keinen vollständigen Dateipfad. [Offizieller SecLists-Baum](https://github.com/danielmiessler/SecLists/tree/master), [Fuzzing-Verzeichnis](https://github.com/danielmiessler/SecLists/tree/master/Fuzzing).
- **Kali und Paketquellen:** SecLists ist nicht auf jeder Kali-Installation vorinstalliert; das hängt vom gewählten Abbild und den Metapaketen ab. Der gezeigte APT-Aufruf setzt eine Paketquelle voraus, die `seclists` anbietet, und gilt nicht pauschal für jede Ubuntu-/Debian-Installation. Verknüpfungen unter `/usr/share/wordlists/` hängen ebenfalls von installierten Paketen ab. [Kali-Paketdokumentation](https://www.kali.org/tools/seclists/), [Upstream-Installation](https://github.com/danielmiessler/SecLists#install).
- **Zuordnung der Listen:** `common.txt` gehört im gezeigten Kali-Kontext zu DIRB, nicht zu DirBuster. `nmap.lst` verweist auf die Passwortliste `nselib/data/passwords.lst`, nicht auf eine Liste von Nmap-Skripten. [DIRB-Dokumentation](https://www.kali.org/tools/dirb/), [Kali-Wortlisten](https://www.kali.org/tools/wordlists/).
- **Beispielsyntax und Ressourcen:** Die Bäume mit eingerückten Einträgen sowie die allein stehenden Dateipfade sind Veranschaulichungen, keine durchgehend ausführbaren Shell-Skripte. Crunch erzeugt Kombinationen; die Zahl der Kandidaten wächst mit Zeichensatz und Länge exponentiell. Die Pipe an Hashcat übergibt Kandidaten über die Standardeingabe; Modus `0` bezeichnet MD5 und muss zum Hashformat passen. [Crunch-Dokumentation](https://www.kali.org/tools/crunch/), [Hashcat-Referenz](https://hashcat.net/wiki/doku.php?id=hashcat).
- **Abdeckung und Verarbeitung:** „Vollständige Abdeckung“ ist keine Zusicherung: Eine große Liste enthält nicht automatisch jeden passenden Kandidaten. Beim Zusammenführen ändern Sortierung und Deduplizierung die Reihenfolge. Die gezeigte Schleife mit `read` bewahrt führenden oder abschließenden IFS-Leerraum und Backslashes nicht zuverlässig; dadurch können Kandidaten verändert werden. [Bash-Handbuch: read](https://www.gnu.org/software/bash/manual/html_node/Bash-Builtins.html#index-read).

---

<p align="center">
  <b>📚 Die passende Wortliste macht den Unterschied!</b><br>
  <i>Gezielt auswählen, sinnvoll kombinieren</i>
</p>
