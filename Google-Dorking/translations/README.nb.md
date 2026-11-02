# 🔍 Google dorking – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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
  <img src="https://img.shields.io/badge/Information-Gathering-green?style=for-the-badge" alt="Info Gathering">
</p>

<p align="center">
  <b>🎯 Avanserte Google-søk for sikkerhetsforskere</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Innholdsfortegnelse

- [🎯 Innledning](#-innledning)
- [🔧 Grunnleggende operatorer](#-grunnleggende-operatorer)
- [⚡ Avanserte operatorer](#-avanserte-operatorer)
- [🔗 Kombinere operatorer](#-kombinere-operatorer)
- [📁 Finne filer](#-finne-filer)
- [📂 Kataloglister](#-kataloglister)
- [🔐 Administrasjonspaneler](#-administrasjonspaneler)
- [💾 Databaser og SQL](#-databaser-og-sql)
- [⚙️ Konfigurasjonsfiler](#-konfigurasjonsfiler)
- [🔑 Påloggingsopplysninger og hemmeligheter](#-påloggingsopplysninger-og-hemmeligheter)
- [⚠️ Sårbare servere](#-sårbare-servere)
- [🐛 Dorker for bug bounty](#-dorker-for-bug-bounty)
- [📱 CMS-spesifikke søk](#-cms-spesifikke-søk)
- [📊 Hurtigreferanse](#-hurtigreferanse)
- [🛠️ Verktøy og automatisering](#-verktøy-og-automatisering)
- [📚 Ressurser](#-ressurser)

---

> **Begreper og versjoner:** En dork er en søkespørring med bestemte operatorer; OSINT er innhenting fra åpne kilder. Treff er undersøkelsesspor, ikke alene bevis på sårbarhet eller full dekning. Originalens `cache:`-eksempler er historiske: Google oppgir at operatoren ikke lenger virker; se [Googles dokumentasjonsendringer](https://developers.google.com/search/updates). Eldre operatorer som `link:`, `related:` og `daterange:` må ikke antas støttet på samme måte som dagens dokumenterte filtre. `site:` og jokertegn garanterer heller ikke en uttømmende liste over alle underdomener. Alle spørringer er bevart. Automatiseringsskriptet skriver søke-URL-er; det gjennomfører ikke søk, og erstatning av mellomrom med pluss er ikke full URL-koding.

## 🎯 Innledning

**Google dorking** (også kalt Google hacking) bruker avanserte søkeoperatorer til å finne sensitive opplysninger som er eksponert på Internett.

### Hva kan du finne?

| Kategori | Eksempler |
|----------|----------|
| **Sensitive filer** | Sikkerhetskopier, konfigurasjoner, logger og databaser |
| **Eksponerte paneler** | Administrasjonspaneler, dashbord og phpMyAdmin |
| **Påloggingsopplysninger** | Passord, API-nøkler og tokener |
| **Sårbarheter** | SQL-injeksjon og åpne kataloger |
| **Personopplysninger** | E-postadresser, telefonnumre og dokumenter |

### Søkemotorenes URL-er

| Søkemotor | URL |
|--------|-----|
| **Google** | https://www.google.com |
| **Bing** | https://www.bing.com |
| **DuckDuckGo** | https://duckduckgo.com |
| **Yandex** | https://yandex.com |

---

## 🔧 Grunnleggende operatorer

### Operatoren site

Begrens søket til et bestemt domene:

```
site:example.com                     # Bare example.com
site:*.example.com                   # Alle underdomener
site:example.com -www                # Utelat www
site:.gov                            # Offentlige nettsteder
site:.edu                            # Utdanningsnettsteder
```

### Operatoren inurl

Søk i URL-en:

```
inurl:admin                          # admin i URL-en
inurl:login                          # Påloggingssider
inurl:dashboard                      # Dashbord
inurl:config                         # Konfigurasjonsfiler
inurl:backup                         # Sikkerhetskopier
```

### Operatoren intitle

Søk i sidetittelen:

```
intitle:"index of"                   # Kataloglister
intitle:admin                        # Administrasjonssider
intitle:login                        # Påloggingssider
intitle:"dashboard"                  # Dashbord
intitle:"control panel"              # Kontrollpaneler
```

### Operatoren intext

Søk i sideinnholdet:

```
intext:"password"                    # Passord i teksten
intext:"username"                    # Brukernavn i teksten
intext:"api_key"                     # API-nøkler
intext:"secret"                      # Hemmeligheter
```

### Operatoren filetype

Søk etter bestemte filtyper:

```
filetype:pdf                         # PDF-filer
filetype:doc                         # Word-dokumenter
filetype:xls                         # Excel-filer
filetype:sql                         # SQL-filer
filetype:log                         # Loggfiler
filetype:bak                         # Sikkerhetskopier
filetype:env                         # Filer med miljøvariabler
```

---

## ⚡ Avanserte operatorer

### Varianter med all

```
allinurl:admin login                 # Alle ordene i URL-en
allintitle:admin panel               # Alle ordene i tittelen
allintext:password username          # Alle ordene i teksten
```

### Hurtigbuffer og lenker

```
cache:example.com                    # Hurtigbufret versjon
link:example.com                     # Sider som lenker til målet
related:example.com                  # Relaterte nettsteder
```

### Datointervall

```
after:2023-01-01                     # Etter datoen
before:2024-01-01                    # Før datoen
daterange:2458850-2459215            # Intervall med julianske datoer
```

### Jokertegn og OR

```
admin * panel                        # Jokertegn
admin OR login                       # Minst ett av søkeordene
"admin panel"                        # Nøyaktig frase
-inurl:https                         # Utelat HTTPS
```

### Tallintervall

```
inurl:id= 1..1000                    # Tallintervall
price $100..$500                     # Prisintervall
```

---

## 🔗 Kombinere operatorer

### Grunnleggende kombinasjoner

```
site:example.com filetype:pdf
site:example.com inurl:admin
site:example.com intitle:login
site:example.com intext:password
```

### Avanserte kombinasjoner

```
site:example.com filetype:sql intext:password
site:example.com inurl:admin intitle:login
site:example.com filetype:log intext:error
site:example.com ext:php inurl:config
```

### Kombinasjoner for bug bounty

```
site:target.com (filetype:pdf | filetype:doc | filetype:xls)
site:target.com (inurl:admin | inurl:login | inurl:dashboard)
site:target.com (ext:sql | ext:bak | ext:log)
```

---

## 📁 Finne filer

### Sensitive dokumenter

```
# Office-dokumenter
site:target.com filetype:doc
site:target.com filetype:docx
site:target.com filetype:xls
site:target.com filetype:xlsx
site:target.com filetype:ppt
site:target.com filetype:pdf

# Tekstfiler
site:target.com filetype:txt
site:target.com filetype:rtf
site:target.com filetype:csv
```

### Sikkerhetskopier

```
site:target.com filetype:bak
site:target.com filetype:backup
site:target.com filetype:old
site:target.com filetype:orig
site:target.com ext:bak
site:target.com inurl:backup
site:target.com intitle:"backup"
```

### Databasefiler

```
site:target.com filetype:sql
site:target.com filetype:db
site:target.com filetype:mdb
site:target.com filetype:sqlite
site:target.com filetype:dump
site:target.com ext:sql intext:INSERT
```

### Loggfiler

```
site:target.com filetype:log
site:target.com ext:log
site:target.com inurl:log
site:target.com intext:"error" filetype:log
site:target.com intext:"exception" filetype:log
site:target.com intext:"fatal" filetype:log
```

### Konfigurasjonsfiler

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

### Kildekode

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

## 📂 Kataloglister

### Index of

```
intitle:"index of" site:target.com
intitle:"index of /" site:target.com
intitle:"directory listing" site:target.com
intitle:"parent directory" site:target.com
```

### Bestemte kataloger

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

### Filtyper i kataloger

```
intitle:"index of" "*.sql" site:target.com
intitle:"index of" "*.bak" site:target.com
intitle:"index of" "*.log" site:target.com
intitle:"index of" "*.zip" site:target.com
intitle:"index of" "*.tar.gz" site:target.com
```

---

## 🔐 Administrasjonspaneler

### Generelle administrasjonssider

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

### cPanel og hosting

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

### Databaseadministrasjon

```
site:target.com inurl:phpmyadmin
site:target.com inurl:phpMyAdmin
site:target.com intitle:"phpMyAdmin"
site:target.com inurl:adminer
site:target.com inurl:pgadmin
site:target.com intitle:"pgAdmin"
site:target.com inurl:mysql
```

### Kontrollpaneler

```
site:target.com intitle:"dashboard"
site:target.com intitle:"control panel"
site:target.com inurl:portal
site:target.com inurl:cms
site:target.com inurl:backend
```

---

## 💾 Databaser og SQL

### SQL-filer

```
site:target.com filetype:sql
site:target.com filetype:sql intext:password
site:target.com filetype:sql intext:"INSERT INTO"
site:target.com filetype:sql intext:"CREATE TABLE"
site:target.com ext:sql "INSERT INTO `users`"
```

### SQL-feil

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

### Indikatorer på SQL-injeksjon

```
site:target.com inurl:id=
site:target.com inurl:page=
site:target.com inurl:cat=
site:target.com inurl:item=
site:target.com inurl:pid=
site:target.com inurl:product_id=
site:target.com inurl:news_id=
```

### Databaseuttrekk

```
site:target.com "dump" filetype:sql
site:target.com "database dump" filetype:sql
site:target.com filetype:sql "-- MySQL dump"
site:target.com filetype:sql "-- phpMyAdmin SQL Dump"
```

---

<a id="-konfigurasjonsfiler"></a>
## ⚙️ Konfigurasjonsfiler

### Webserverkonfigurasjon

```
site:target.com filetype:conf
site:target.com filetype:conf intext:password
site:target.com "httpd.conf"
site:target.com "nginx.conf"
site:target.com ".htaccess"
site:target.com ".htpasswd"
site:target.com "web.config"
```

### Applikasjonskonfigurasjon

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

### Git-konfigurasjon

```
site:target.com ".git/config"
site:target.com intitle:"index of" ".git"
site:target.com inurl:".git"
site:target.com filetype:git
```

### AWS og skyen

```
site:target.com filetype:pem
site:target.com filetype:ppk
site:target.com "aws_access_key_id"
site:target.com "aws_secret_access_key"
site:target.com "AKIA"                    # Prefiks for AWS-nøkkel
site:target.com "credentials" filetype:json
```

---

## 🔑 Påloggingsopplysninger og hemmeligheter

### Passord

```
site:target.com intext:password
site:target.com intext:"password is"
site:target.com intext:"default password"
site:target.com filetype:txt password
site:target.com filetype:log password
site:target.com "password" filetype:xls
site:target.com "password" filetype:csv
```

### Kombinasjoner av brukernavn og passord

```
site:target.com intext:username intext:password
site:target.com "username" "password" filetype:txt
site:target.com "login" "password" filetype:xls
site:target.com intext:"username:" intext:"password:"
```

### API-nøkler og tokener

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

### E-postadresser

```
site:target.com "@target.com"
site:target.com filetype:xls "@target.com"
site:target.com filetype:csv "email"
site:target.com intext:"@gmail.com"
```

### Private nøkler

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

<a id="-sårbare-servere"></a>
## ⚠️ Sårbare servere

### Åpne kataloger

```
intitle:"index of" site:target.com
intitle:"directory listing" site:target.com
intitle:"Apache" "server at" site:target.com
```

### Standardsider

```
site:target.com intitle:"Apache2 Ubuntu Default Page"
site:target.com intitle:"Welcome to nginx"
site:target.com intitle:"Test Page for Apache"
site:target.com intitle:"IIS Windows Server"
site:target.com intitle:"Welcome to CentOS"
```

### Feilsider

```
site:target.com "404 Not Found" inurl:admin
site:target.com "500 Internal Server Error"
site:target.com "Error establishing database connection"
site:target.com "Parse error" "syntax error"
site:target.com "Warning:" "mysql_"
site:target.com "Fatal error:" "php"
```

### Eksponerte tjenester

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

## 🐛 Dorker for bug bounty

### Raske undersøkelsesspor

```
# Sensitive filer
site:target.com (filetype:sql | filetype:bak | filetype:log | filetype:env)

# Administrasjonspaneler
site:target.com (inurl:admin | inurl:login | inurl:dashboard)

# Konfigurasjonsfiler
site:target.com (filetype:conf | filetype:config | filetype:ini | filetype:env)

# Påloggingsopplysninger
site:target.com (intext:password | intext:api_key | intext:secret)
```

### Oppdagelse av underdomener

```
site:*.target.com -www
site:*.target.com -www -mail
site:*.*.target.com
```

### API-endepunkter

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

### Utvikling og staging

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

### Autentiseringssider

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

### Filopplasting

```
site:target.com inurl:upload
site:target.com intitle:upload
site:target.com inurl:file
site:target.com "choose file"
site:target.com "upload file"
```

---

## 📱 CMS-spesifikke søk

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

## 📊 Hurtigreferanse

### Viktige operatorer

| Operator | Funksjon | Eksempel |
|----------|----------|---------|
| `site:` | Avgrens til domene | `site:target.com` |
| `inurl:` | Søk i URL | `inurl:admin` |
| `intitle:` | Søk i tittel | `intitle:login` |
| `intext:` | Søk i innhold | `intext:password` |
| `filetype:` | Filendelse | `filetype:sql` |
| `ext:` | Filendelse | `ext:pdf` |
| `cache:` | Hurtigbufret versjon | `cache:target.com` |
| `"..."` | Nøyaktig frase | `"admin panel"` |
| `-` | Utelat | `-site:www` |
| `OR` / `\|` | Minst ett av søkeordene | `admin OR login` |
| `*` | Jokertegn | `admin * panel` |

### Ti dorker for bug bounty

| # | Dork | Formål |
|---|------|---------|
| 1 | `site:target.com filetype:sql` | Databasefiler |
| 2 | `site:target.com inurl:admin` | Administrasjonspaneler |
| 3 | `site:target.com filetype:log` | Loggfiler |
| 4 | `site:target.com filetype:env` | Filer med miljøvariabler |
| 5 | `site:target.com intext:password filetype:txt` | Passord |
| 6 | `intitle:"index of" site:target.com` | Katalogliste |
| 7 | `site:target.com inurl:api` | API-endepunkter |
| 8 | `site:target.com filetype:bak` | Sikkerhetskopier |
| 9 | `site:*.target.com -www` | Underdomener |
| 10 | `site:target.com "api_key"` | API-nøkler |

---

<a id="-verktøy-og-automatisering"></a>
## 🛠️ Verktøy og automatisering

### Verktøy for Google dorking

| Verktøy | Beskrivelse | URL |
|------|-------------|-----|
| **Google Hacking Database** | Exploit-DBs dorksamling | [GHDB](https://www.exploit-db.com/google-hacking-database) |
| **DorkSearch** | Generator for dorker | [DorkSearch](https://dorksearch.com) |
| **Pagodo** | Passiv Google dorking | GitHub |
| **GooDork** | Python-verktøy for dorking | GitHub |
| **GooFuzz** | Fuzzing med Google | GitHub |

### Nettsteder for dorking

```
# Google Hacking Database
https://www.exploit-db.com/google-hacking-database

# Pentest-Tools
https://pentest-tools.com/information-gathering/google-hacking

# DorkSearch
https://dorksearch.com
```

### Eksempel på automatiseringsskript

```bash
#!/bin/bash
# Enkel automatisering av Google-dorker

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

## 💡 Tips og anbefalte arbeidsmåter

### Tips for bug bounty

1. **Begynn bredt og snevr inn**
   ```
   site:target.com                    # Først
   site:target.com filetype:sql       # Deretter mer spesifikt
   ```

2. **Bruk flere søkemotorer**
   - Google, Bing, DuckDuckGo, Yandex
   
3. **Se etter hurtigbufrede sider**
   ```
   cache:target.com/admin
   ```

4. **Se etter underdomener**
   ```
   site:*.target.com -www
   ```

5. **Begrens søkehastigheten**
   - Google kan blokkere svært mange søk

### Unngå deteksjon

```
# Bruk ulike IP-adresser/VPN-er
# Legg inn forsinkelser mellom søk
# Varier søkemønstrene
# Bruk forskjellige nettlesere
```

---

## ⚠️ Juridisk merknad

> **ADVARSEL:** Originalen omtaler Google dorking som lovlig i seg selv, men **tilgang** til eksponerte data uten tillatelse kan være ulovlig.
>
> - ✅ Bruk til autorisert sikkerhetstesting
> - ✅ Bruk i autoriserte bug bounty-programmer
> - ✅ Rapporter sårbarheter på en ansvarlig måte
> - ❌ Ikke få tilgang til data uten tillatelse
> - ❌ Ikke last ned eller lagre sensitive data
> - ❌ Ikke utnytt sårbarhetene du finner
>
> **Innhent alltid nødvendig tillatelse før testing!**

---

## 📚 Ressurser

### Offisielle ressurser
- [Googles søkeoperatorer](https://support.google.com/websearch/answer/2466433)
- [Google Hacking Database](https://www.exploit-db.com/google-hacking-database)

### Bøker og veiledninger
- «Google Hacking for Penetration Testers» av Johnny Long
- OWASP Testing Guide

### Relaterte hurtigreferanser
- [Shodan](../../Shodan/translations/README.nb.md)
- [GitHub-dorking](../../GitHub-Dorking/translations/README.nb.md)
- [Subfinder](../../Subfinder/translations/README.nb.md)

---

<p align="center">
  <b>🔍 Finn det som er vanskelig å oppdage!</b><br>
  <i>Lær Google dorking</i>
</p>
