# 🔍 Google Dorking – vollständiges Cheatsheet

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗  ██████╗  ██████╗  ██████╗ ██╗     ███████╗
  ██╔════╝ ██╔═══██╗██╔═══██╗██╔════╝ ██║     ██╔════╝
  ██║  ███╗██║   ██║██║   ██║██║  ███╗██║     █████╗  
  ██║   ██║██║   ██║██║   ██║██║   ██║██║     ██╔══╝  
  ╚██████╔╝╚██████╔╝╚██████╔╝╚██████╔╝███████╗███████╗
   ╚═════╝  ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚══════╝
  ██████╗  ██████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗ 
  ██╔══██╗██╔═══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝ 
  ██║  ██║██║   ██║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗
  ██║  ██║██║   ██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║
  ██████╔╝╚██████╔╝██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝
  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

<p align="center">
  <img src="https://img.shields.io/badge/Google-Dorking-red?style=for-the-badge" alt="Google Dorking">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Information-Gathering-green?style=for-the-badge" alt="Informationsbeschaffung">
</p>

<p align="center">
  <b>🎯 Erweiterte Google-Suchtechniken für Sicherheitsforscher</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Grundlegende Operatoren](#-grundlegende-operatoren)
- [Erweiterte Operatoren](#-erweiterte-operatoren)
- [Operatoren kombinieren](#-operatoren-kombinieren)
- [Dateien finden](#-dateien-finden)
- [Verzeichnisauflistung](#-verzeichnisauflistung)
- [Administrationsoberflächen](#-administrationsoberflächen)
- [Datenbanken und SQL](#-datenbanken-und-sql)
- [Konfigurationsdateien](#-konfigurationsdateien)
- [Zugangsdaten und Secrets](#-zugangsdaten-und-secrets)
- [Anfällige Server](#-anfällige-server)
- [Dorks für Bug Bounties](#-dorks-für-bug-bounties)
- [CMS-spezifische Suchen](#-cms-spezifische-suchen)
- [Schnellübersicht](#-schnellübersicht)
- [Werkzeuge und Automatisierung](#-werkzeuge-und-automatisierung)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**Google Dorking** (auch Google Hacking genannt) nutzt erweiterte Suchoperatoren, um vertrauliche Informationen zu finden, die im Internet offenliegen.

### Was lässt sich finden?

| Kategorie | Beispiele |
|----------|----------|
| **Vertrauliche Dateien** | Sicherungen, Konfigurationen, Protokolle, Datenbanken |
| **Öffentlich erreichbare Oberflächen** | Administrationsoberflächen, Dashboards, phpMyAdmin |
| **Zugangsdaten** | Passwörter, API-Schlüssel, Tokens |
| **Schwachstellen** | SQL-Injection, offene Verzeichnisse |
| **Personenbezogene Daten** | E-Mail-Adressen, Telefonnummern, Dokumente |

### URLs der Suchmaschinen

| Suchmaschine | URL |
|--------|-----|
| **Google** | https://www.google.com |
| **Bing** | https://www.bing.com |
| **DuckDuckGo** | https://duckduckgo.com |
| **Yandex** | https://yandex.com |

---

## 🔧 Grundlegende Operatoren

### Site-Operator

Die Suche auf eine bestimmte Domain begrenzen:

```
site:example.com                     # Nur example.com
site:*.example.com                   # Alle Subdomains
site:example.com -www                # www ausschließen
site:.gov                            # Behördenseiten
site:.edu                            # Bildungseinrichtungen
```

### Inurl-Operator

In der URL suchen:

```
inurl:admin                          # admin in der URL
inurl:login                          # Anmeldeseiten
inurl:dashboard                      # Dashboards
inurl:config                         # Konfigurationsdateien
inurl:backup                         # Sicherungsdateien
```

### Intitle-Operator

Im Seitentitel suchen:

```
intitle:"index of"                   # Verzeichnisauflistungen
intitle:admin                        # Administrationsseiten
intitle:login                        # Anmeldeseiten
intitle:"dashboard"                  # Dashboards
intitle:"control panel"              # Verwaltungsoberflächen
```

### Intext-Operator

Im Seiteninhalt suchen:

```
intext:"password"                    # Passwort im Text
intext:"username"                    # Benutzername im Text
intext:"api_key"                     # API-Schlüssel
intext:"secret"                      # Secrets (geheime Werte)
```

### Filetype-Operator

Nach bestimmten Dateitypen suchen:

```
filetype:pdf                         # PDF-Dateien
filetype:doc                         # Word-Dokumente
filetype:xls                         # Excel-Dateien
filetype:sql                         # SQL-Dateien
filetype:log                         # Protokolldateien
filetype:bak                         # Sicherungsdateien
filetype:env                         # Umgebungsdateien
```

---

## ⚡ Erweiterte Operatoren

### All-Varianten

```
allinurl:admin login                 # Alle Wörter in der URL
allintitle:admin panel               # Alle Wörter im Seitentitel
allintext:password username          # Alle Wörter im Text
```

### Cache und Links

```
cache:example.com                    # Zwischengespeicherte Version
link:example.com                     # Seiten mit Links auf das Ziel
related:example.com                  # Ähnliche Websites
```

### Zeitraum

```
after:2023-01-01                     # Nach diesem Datum
before:2024-01-01                    # Vor diesem Datum
daterange:2458850-2459215            # Zeitraum in julianischen Tageszahlen
```

### Platzhalter und OR

```
admin * panel                        # Platzhalter
admin OR login                       # Einer der Begriffe
"admin panel"                        # Exakte Wortgruppe
-inurl:https                         # HTTPS ausschließen
```

### Zahlenbereich

```
inurl:id= 1..1000                    # Zahlenbereich
price $100..$500                     # Preisbereich
```

---

## 🔗 Operatoren kombinieren

### Einfache Kombinationen

```
site:example.com filetype:pdf
site:example.com inurl:admin
site:example.com intitle:login
site:example.com intext:password
```

### Erweiterte Kombinationen

```
site:example.com filetype:sql intext:password
site:example.com inurl:admin intitle:login
site:example.com filetype:log intext:error
site:example.com ext:php inurl:config
```

### Kombinationen für Bug Bounties

```
site:target.com (filetype:pdf | filetype:doc | filetype:xls)
site:target.com (inurl:admin | inurl:login | inurl:dashboard)
site:target.com (ext:sql | ext:bak | ext:log)
```

---

## 📁 Dateien finden

### Vertrauliche Dokumente

```
# Office-Dokumente
site:target.com filetype:doc
site:target.com filetype:docx
site:target.com filetype:xls
site:target.com filetype:xlsx
site:target.com filetype:ppt
site:target.com filetype:pdf

# Textdateien
site:target.com filetype:txt
site:target.com filetype:rtf
site:target.com filetype:csv
```

### Sicherungsdateien

```
site:target.com filetype:bak
site:target.com filetype:backup
site:target.com filetype:old
site:target.com filetype:orig
site:target.com ext:bak
site:target.com inurl:backup
site:target.com intitle:"backup"
```

### Datenbankdateien

```
site:target.com filetype:sql
site:target.com filetype:db
site:target.com filetype:mdb
site:target.com filetype:sqlite
site:target.com filetype:dump
site:target.com ext:sql intext:INSERT
```

### Protokolldateien

```
site:target.com filetype:log
site:target.com ext:log
site:target.com inurl:log
site:target.com intext:"error" filetype:log
site:target.com intext:"exception" filetype:log
site:target.com intext:"fatal" filetype:log
```

### Konfigurationsdateien

```
site:target.com filetype:conf
site:target.com filetype:config
site:target.com filetype:cfg
site:target.com filetype:ini
site:target.com filetype:env
site:target.com filetype:yml
site:target.com filetype:yaml
site:target.com filetype:xml
site:target.com filetype:json
```

### Quellcode

```
site:target.com filetype:php
site:target.com filetype:asp
site:target.com filetype:aspx
site:target.com filetype:jsp
site:target.com filetype:py
site:target.com filetype:rb
site:target.com filetype:js
```

---

## 📂 Verzeichnisauflistung

### Index-of-Seiten

```
intitle:"index of" site:target.com
intitle:"index of /" site:target.com
intitle:"directory listing" site:target.com
intitle:"parent directory" site:target.com
```

### Bestimmte Verzeichnisse

```
intitle:"index of" "backup" site:target.com
intitle:"index of" "password" site:target.com
intitle:"index of" "admin" site:target.com
intitle:"index of" "config" site:target.com
intitle:"index of" "ftp" site:target.com
intitle:"index of" "logs" site:target.com
intitle:"index of" "uploads" site:target.com
intitle:"index of" ".git" site:target.com
intitle:"index of" ".svn" site:target.com
```

### Dateitypen in Verzeichnissen

```
intitle:"index of" "*.sql" site:target.com
intitle:"index of" "*.bak" site:target.com
intitle:"index of" "*.log" site:target.com
intitle:"index of" "*.zip" site:target.com
intitle:"index of" "*.tar.gz" site:target.com
```

---

## 🔐 Administrationsoberflächen

### Allgemeine Administration

```
site:target.com inurl:admin
site:target.com inurl:administrator
site:target.com inurl:adminlogin
site:target.com inurl:admin/login
site:target.com inurl:admin.php
site:target.com inurl:admin.asp
site:target.com intitle:admin
site:target.com intitle:"admin login"
site:target.com intitle:"admin panel"
site:target.com intitle:"administrator"
```

### cPanel und Hosting

```
site:target.com inurl:cpanel
site:target.com inurl:webmail
site:target.com inurl:whm
site:target.com intitle:"cPanel"
site:target.com inurl:2082
site:target.com inurl:2083
site:target.com inurl:2086
site:target.com inurl:2087
```

### Datenbankverwaltung

```
site:target.com inurl:phpmyadmin
site:target.com inurl:phpMyAdmin
site:target.com intitle:"phpMyAdmin"
site:target.com inurl:adminer
site:target.com inurl:pgadmin
site:target.com intitle:"pgAdmin"
site:target.com inurl:mysql
```

### Verwaltungsoberflächen

```
site:target.com intitle:"dashboard"
site:target.com intitle:"control panel"
site:target.com inurl:portal
site:target.com inurl:cms
site:target.com inurl:backend
```

---

## 💾 Datenbanken und SQL

### SQL-Dateien

```
site:target.com filetype:sql
site:target.com filetype:sql intext:password
site:target.com filetype:sql intext:"INSERT INTO"
site:target.com filetype:sql intext:"CREATE TABLE"
site:target.com ext:sql "INSERT INTO `users`"
```

### SQL-Fehler

```
site:target.com "SQL syntax" error
site:target.com "mysql_fetch_array"
site:target.com "mysql_num_rows"
site:target.com "mysql_connect"
site:target.com "mysqli_"
site:target.com "ORA-" error
site:target.com "PostgreSQL" error
site:target.com "ODBC" error
```

### Anhaltspunkte für SQL-Injection

```
site:target.com inurl:id=
site:target.com inurl:page=
site:target.com inurl:cat=
site:target.com inurl:item=
site:target.com inurl:pid=
site:target.com inurl:product_id=
site:target.com inurl:news_id=
```

### Datenbank-Dumps

```
site:target.com "dump" filetype:sql
site:target.com "database dump" filetype:sql
site:target.com filetype:sql "-- MySQL dump"
site:target.com filetype:sql "-- phpMyAdmin SQL Dump"
```

---

<a name="-konfigurationsdateien"></a>

## ⚙️ Konfigurationsdateien

### Webserverkonfiguration

```
site:target.com filetype:conf
site:target.com filetype:conf intext:password
site:target.com "httpd.conf"
site:target.com "nginx.conf"
site:target.com ".htaccess"
site:target.com ".htpasswd"
site:target.com "web.config"
```

### Anwendungskonfiguration

```
site:target.com filetype:env
site:target.com ".env" filetype:env
site:target.com "config.php" intext:password
site:target.com "settings.php" intext:password
site:target.com "database.php" intext:password
site:target.com "wp-config.php"
site:target.com "configuration.php"
site:target.com "config.yml"
site:target.com "config.json"
```

### Git-Konfiguration

```
site:target.com ".git/config"
site:target.com intitle:"index of" ".git"
site:target.com inurl:".git"
site:target.com filetype:git
```

### AWS und Cloud

```
site:target.com filetype:pem
site:target.com filetype:ppk
site:target.com "aws_access_key_id"
site:target.com "aws_secret_access_key"
site:target.com "AKIA"                    # Präfix eines AWS-Schlüssels
site:target.com "credentials" filetype:json
```

---

## 🔑 Zugangsdaten und Secrets

### Passwörter

```
site:target.com intext:password
site:target.com intext:"password is"
site:target.com intext:"default password"
site:target.com filetype:txt password
site:target.com filetype:log password
site:target.com "password" filetype:xls
site:target.com "password" filetype:csv
```

### Kombinationen aus Benutzername und Passwort

```
site:target.com intext:username intext:password
site:target.com "username" "password" filetype:txt
site:target.com "login" "password" filetype:xls
site:target.com intext:"username:" intext:"password:"
```

### API-Schlüssel und Tokens

```
site:target.com intext:api_key
site:target.com intext:apikey
site:target.com intext:api-key
site:target.com intext:"api key"
site:target.com intext:access_token
site:target.com intext:auth_token
site:target.com intext:secret_key
site:target.com intext:bearer
```

### E-Mail-Adressen

```
site:target.com "@target.com"
site:target.com filetype:xls "@target.com"
site:target.com filetype:csv "email"
site:target.com intext:"@gmail.com"
```

### Private Schlüssel

```
site:target.com "BEGIN RSA PRIVATE KEY"
site:target.com "BEGIN DSA PRIVATE KEY"
site:target.com "BEGIN EC PRIVATE KEY"
site:target.com "BEGIN PRIVATE KEY"
site:target.com filetype:pem
site:target.com filetype:key
site:target.com "id_rsa"
site:target.com "id_dsa"
```

---

<a name="-anfällige-server"></a>

## ⚠️ Anfällige Server

### Offene Verzeichnisse

```
intitle:"index of" site:target.com
intitle:"directory listing" site:target.com
intitle:"Apache" "server at" site:target.com
```

### Standardseiten

```
site:target.com intitle:"Apache2 Ubuntu Default Page"
site:target.com intitle:"Welcome to nginx"
site:target.com intitle:"Test Page for Apache"
site:target.com intitle:"IIS Windows Server"
site:target.com intitle:"Welcome to CentOS"
```

### Fehlerseiten

```
site:target.com "404 Not Found" inurl:admin
site:target.com "500 Internal Server Error"
site:target.com "Error establishing database connection"
site:target.com "Parse error" "syntax error"
site:target.com "Warning:" "mysql_"
site:target.com "Fatal error:" "php"
```

### Öffentlich erreichbare Dienste

```
site:target.com intitle:"Webmin"
site:target.com intitle:"Jenkins"
site:target.com intitle:"Grafana"
site:target.com intitle:"Kibana"
site:target.com intitle:"Elasticsearch"
site:target.com intitle:"Solr Admin"
site:target.com intitle:"RabbitMQ Management"
site:target.com intitle:"Redis Commander"
```

---

## 🐛 Dorks für Bug Bounties

### Schnelle Ansatzpunkte

```
# Vertrauliche Dateien
site:target.com (filetype:sql | filetype:bak | filetype:log | filetype:env)

# Administrationsoberflächen
site:target.com (inurl:admin | inurl:login | inurl:dashboard)

# Konfigurationsdateien
site:target.com (filetype:conf | filetype:config | filetype:ini | filetype:env)

# Zugangsdaten
site:target.com (intext:password | intext:api_key | intext:secret)
```

### Subdomains ermitteln

```
site:*.target.com -www
site:*.target.com -www -mail
site:*.*.target.com
```

### API-Endpunkte

```
site:target.com inurl:api
site:target.com inurl:api/v1
site:target.com inurl:api/v2
site:target.com inurl:/rest/
site:target.com inurl:graphql
site:target.com inurl:swagger
site:target.com inurl:swagger-ui
site:target.com filetype:json inurl:api
```

### Entwicklung und Staging

```
site:dev.target.com
site:staging.target.com
site:test.target.com
site:uat.target.com
site:qa.target.com
site:demo.target.com
site:beta.target.com
site:sandbox.target.com
```

### Authentifizierungsseiten

```
site:target.com inurl:login
site:target.com inurl:signin
site:target.com inurl:signup
site:target.com inurl:register
site:target.com inurl:auth
site:target.com inurl:oauth
site:target.com inurl:sso
site:target.com inurl:forgot
site:target.com inurl:reset
```

### Datei-Uploads

```
site:target.com inurl:upload
site:target.com intitle:upload
site:target.com inurl:file
site:target.com "choose file"
site:target.com "upload file"
```

---

## 📱 CMS-spezifische Suchen

### WordPress

```
site:target.com inurl:wp-content
site:target.com inurl:wp-includes
site:target.com inurl:wp-admin
site:target.com inurl:wp-login
site:target.com "wp-config.php"
site:target.com filetype:sql "wp_users"
site:target.com intitle:"WordPress" inurl:readme
```

### Joomla

```
site:target.com inurl:administrator
site:target.com inurl:com_
site:target.com "configuration.php"
site:target.com "Joomla"
```

### Drupal

```
site:target.com inurl:node/
site:target.com inurl:sites/default/files
site:target.com "Drupal"
site:target.com "CHANGELOG.txt"
```

### Magento

```
site:target.com inurl:app/etc/local.xml
site:target.com inurl:downloader
site:target.com "Magento"
```

---

## 📊 Schnellübersicht

### Wichtige Operatoren

| Operator | Funktion | Beispiel |
|----------|----------|---------|
| `site:` | Auf eine Domain begrenzen | `site:target.com` |
| `inurl:` | In der URL suchen | `inurl:admin` |
| `intitle:` | Im Seitentitel suchen | `intitle:login` |
| `intext:` | Im Seiteninhalt suchen | `intext:password` |
| `filetype:` | Dateiendung | `filetype:sql` |
| `ext:` | Dateiendung | `ext:pdf` |
| `cache:` | Zwischengespeicherte Version | `cache:target.com` |
| `"..."` | Exakte Wortgruppe | `"admin panel"` |
| `-` | Ausschließen | `-site:www` |
| `OR` / `\|` | Einer der Begriffe | `admin OR login` |
| `*` | Platzhalter | `admin * panel` |

### Zehn Dorks für Bug Bounties

| # | Dork | Zweck |
|---|------|---------|
| 1 | `site:target.com filetype:sql` | Datenbankdateien |
| 2 | `site:target.com inurl:admin` | Administrationsoberflächen |
| 3 | `site:target.com filetype:log` | Protokolldateien |
| 4 | `site:target.com filetype:env` | Umgebungsdateien |
| 5 | `site:target.com intext:password filetype:txt` | Passwörter |
| 6 | `intitle:"index of" site:target.com` | Verzeichnisauflistung |
| 7 | `site:target.com inurl:api` | API-Endpunkte |
| 8 | `site:target.com filetype:bak` | Sicherungsdateien |
| 9 | `site:*.target.com -www` | Subdomains |
| 10 | `site:target.com "api_key"` | API-Schlüssel |

---

<a name="-werkzeuge-und-automatisierung"></a>

## 🛠️ Werkzeuge und Automatisierung

### Werkzeuge für Google-Dorking

| Werkzeug | Beschreibung | URL |
|------|-------------|-----|
| **Google Hacking Database** | Dork-Sammlung von Exploit-DB | [GHDB](https://www.exploit-db.com/google-hacking-database) |
| **DorkSearch** | Dork-Generator | [DorkSearch](https://dorksearch.com) |
| **Pagodo** | Passives Google-Dorking | GitHub |
| **GooDork** | Python-Werkzeug für Dorking | GitHub |
| **GooFuzz** | Fuzzing mit Google | GitHub |

### Websites für Dorking

```
# Google Hacking Database
https://www.exploit-db.com/google-hacking-database

# Pentest-Tools
https://pentest-tools.com/information-gathering/google-hacking

# DorkSearch
https://dorksearch.com
```

### Beispielskript zur Automatisierung

```bash
#!/bin/bash
# Einfache Automatisierung von Google-Dorks

TARGET="target.com"

DORKS=(
    "site:$TARGET filetype:sql"
    "site:$TARGET filetype:log"
    "site:$TARGET filetype:env"
    "site:$TARGET inurl:admin"
    "site:$TARGET intext:password"
)

for dork in "${DORKS[@]}"; do
    echo "[*] Dork: $dork"
    echo "URL: https://www.google.com/search?q=$(echo $dork | sed 's/ /+/g')"
    echo ""
done
```

---

## 💡 Tipps und bewährte Vorgehensweisen

### Tipps für Bug Bounties

1. **Breit beginnen und dann eingrenzen**
   ```
   site:target.com                    # Zuerst
   site:target.com filetype:sql       # Danach gezielter
   ```

2. **Mehrere Suchmaschinen verwenden**
   - Google, Bing, DuckDuckGo, Yandex
   
3. **Zwischengespeicherte Seiten prüfen**
   ```
   cache:target.com/admin
   ```

4. **Nach Subdomains suchen**
   ```
   site:*.target.com -www
   ```

5. **Die Häufigkeit der Suchanfragen begrenzen**
   - Google kann übermäßig viele Suchanfragen blockieren

### Erkennung vermeiden

```
# Unterschiedliche IP-Adressen/VPNs verwenden
# Zwischen Suchanfragen Pausen einlegen
# Suchmuster variieren
# Unterschiedliche Browser verwenden
```

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** Google-Dorking an sich ist legal; der **Zugriff** auf offengelegte Daten ohne Genehmigung kann jedoch rechtswidrig sein.
> 
> - ✅ Für autorisierte Sicherheitstests verwenden
> - ✅ Im Rahmen von Bug-Bounty-Programmen verwenden
> - ✅ Schwachstellen verantwortungsvoll melden
> - ❌ Niemals ohne Erlaubnis auf Daten zugreifen
> - ❌ Niemals vertrauliche Daten herunterladen oder speichern
> - ❌ Gefundene Schwachstellen nicht ausnutzen
> 
> **Vor Tests immer eine passende Genehmigung einholen!**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [Google-Suchoperatoren](https://support.google.com/websearch/answer/2466433)
- [Google Hacking Database](https://www.exploit-db.com/google-hacking-database)

### Bücher und Leitfäden
- „Google Hacking for Penetration Testers“ von Johnny Long
- OWASP Testing Guide (Leitfaden für Sicherheitstests)

### Verwandte Cheatsheets
- [Shodan](../../Shodan/translations/README.de.md)
- [GitHub-Dorking](../../GitHub-Dorking/translations/README.de.md)
- [Subfinder](../../Subfinder/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Historische Operatoren:** Google unterstützt cache: nicht mehr; die zugehörige Dokumentation wurde im September 2024 entfernt. related: wird seit 2023 nicht mehr unterstützt. Die Beispiele und der Cache-Tipp bleiben als historische Vorlage erhalten. Auch link: und daterange: sind keine verlässliche Grundlage für aktuelle Recherchen; sie fehlen in der aktuellen Betreiberübersicht. Das ist keine Zusage zur Wirkung aller übrigen alten Operatoren. [Google: Dokumentationsänderungen](https://developers.google.com/search/updates), [Google: aktuelle Suchoperatoren](https://developers.google.com/search/docs/monitor-debug/search-operators).
- **Suchumfang und Aussagekraft:** site: liefert keine vollständige Bestandsaufnahme. Bereits site:example.com kann Subdomains einschließen; Sternchenmuster und -www garantieren weder eine vollständige Subdomainliste noch den gezielten Ausschluss nur eines Hosts. Ein Suchtreffer, eine Standardseite oder ein Parameter wie id ist außerdem kein Nachweis einer ausnutzbaren Schwachstelle. filetype: berücksichtigt Content-Type beziehungsweise Dateiendung; eine Suche nach PHP-Dateien legt nicht automatisch deren serverseitigen Quellcode offen. [Google: site:-Grenzen](https://developers.google.com/search/docs/monitor-debug/search-operators/all-search-site), [Google: Operatorenübersicht](https://developers.google.com/search/docs/monitor-debug/search-operators).
- **Kommentare, Datumsfilter und Script:** Die Erklärungen nach # sind Anmerkungen, keine Bestandteile der Suchanfragen. before: und after: beziehen sich auf Googles Einordnung des letzten Aktualisierungsdatums, nicht auf einen sicher belegten Veröffentlichungszeitpunkt. Das Bash-Beispiel druckt lediglich Such-URLs; es führt selbst keine Google-Abfragen aus. Das Ersetzen von Leerzeichen allein ist keine vollständige URL-Kodierung. [Google: Suche verfeinern](https://support.google.com/websearch/answer/2466433).
- **Automatisierung und Einordnung:** Die allgemeinen Aussagen zur Rechtmäßigkeit ersetzen keine Prüfung des konkreten Vorgangs und der Genehmigung. Die Tipps zum IP-/Browserwechsel garantieren keine unbemerkte Nutzung. Google kann auch bei gemeinsam genutzten VPN-Adressen ungewöhnlichen Datenverkehr melden; automatisierter Suchzugriff ohne ausdrückliche Erlaubnis widerspricht laut Google seinen Richtlinien. [Google: ungewöhnlicher Datenverkehr](https://support.google.com/websearch/answer/86640), [Google: maschinell erzeugter Datenverkehr](https://developers.google.com/search/docs/essentials/spam-policies#machine-generated-traffic).

---

<p align="center">
  <b>🔍 Finden, was andere verbergen!</b><br>
  <i>Google-Dorking gezielt beherrschen</i>
</p>
