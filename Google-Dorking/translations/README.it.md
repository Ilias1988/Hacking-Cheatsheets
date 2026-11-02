# 🔍 Google Dorking - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

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
  <b>🎯 Tecniche avanzate di ricerca Google per i ricercatori di sicurezza</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Nota sugli esempi originali:** le query mantengono parole, operatori e date inglesi. `cache:` non funziona più in Google Search, come indicato nell’[aggiornamento ufficiale del 24 settembre 2024](https://developers.google.com/search/updates#cache-docs); i relativi esempi e consigli sono riferimenti storici. Anche gli altri operatori storici vanno verificati nel motore usato: non sono tutti documentati o intercambiabili. `site:` non offre un inventario completo e una pagina indicizzata non dimostra una vulnerabilità. Lo script sostituisce solo gli spazi con `+`, non esegue una codifica URL completa. Le indicazioni sulla rilevabilità e le affermazioni legali riprendono l’originale, senza garantire invisibilità né valutare il singolo caso.

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Operatori di base](#-operatori-di-base)
- [Operatori avanzati](#-operatori-avanzati)
- [Combinare gli operatori](#-combinare-gli-operatori)
- [Individuazione dei file](#-individuazione-dei-file)
- [Elenco delle directory](#-elenco-delle-directory)
- [Pannelli di amministrazione](#-pannelli-di-amministrazione)
- [Database e SQL](#-database-e-sql)
- [File di configurazione](#-file-di-configurazione)
- [Credenziali e segreti](#-credenziali-e-segreti)
- [Server vulnerabili](#-server-vulnerabili)
- [Dork per il bug bounty](#-dork-per-il-bug-bounty)
- [Ricerche specifiche per CMS](#-ricerche-specifiche-per-cms)
- [Riferimento rapido](#-riferimento-rapido)
- [Strumenti e automazione](#-strumenti-e-automazione)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

Il **Google Dorking** (detto anche Google Hacking) usa operatori di ricerca avanzati per individuare informazioni sensibili esposte su Internet.

### Che cosa puoi trovare?

| Categoria | Esempi |
|----------|----------|
| **File sensibili** | Backup, configurazioni, log e database |
| **Pannelli esposti** | Pannelli di amministrazione, dashboard e phpMyAdmin |
| **Credenziali** | Password, chiavi API e token |
| **Vulnerabilità** | SQL injection e directory accessibili |
| **Dati personali** | Indirizzi email, numeri di telefono e documenti |

### URL dei motori di ricerca

| Motore | URL |
|--------|-----|
| **Google** | https://www.google.com |
| **Bing** | https://www.bing.com |
| **DuckDuckGo** | https://duckduckgo.com |
| **Yandex** | https://yandex.com |

---

## 🔧 Operatori di base

### Operatore site

Limita la ricerca a un dominio specifico:

```
site:example.com                     # Solo example.com
site:*.example.com                   # Tutti i sottodomini
site:example.com -www                # Escludi www
site:.gov                            # Siti governativi
site:.edu                            # Siti di istituti di istruzione
```

### Operatore inurl

Cerca nell'URL:

```
inurl:admin                          # admin nell'URL
inurl:login                          # Pagine di accesso
inurl:dashboard                      # Dashboard
inurl:config                         # File di configurazione
inurl:backup                         # File di backup
```

### Operatore intitle

Cerca nel titolo della pagina:

```
intitle:"index of"                   # Elenchi delle directory
intitle:admin                        # Pagine di amministrazione
intitle:login                        # Pagine di accesso
intitle:"dashboard"                  # Dashboard
intitle:"control panel"              # Pannelli di controllo
```

### Operatore intext

Cerca nel contenuto della pagina:

```
intext:"password"                    # Password nel testo
intext:"username"                    # Nome utente nel testo
intext:"api_key"                     # Chiavi API
intext:"secret"                      # Segreti
```

### Operatore filetype

Cerca tipi di file specifici:

```
filetype:pdf                         # File PDF
filetype:doc                         # Documenti Word
filetype:xls                         # File Excel
filetype:sql                         # File SQL
filetype:log                         # File di log
filetype:bak                         # File di backup
filetype:env                         # File delle variabili d'ambiente
```

---

## ⚡ Operatori avanzati

### Varianti all

```
allinurl:admin login                 # Tutte le parole nell'URL
allintitle:admin panel               # Tutte le parole nel titolo
allintext:password username          # Tutte le parole nel testo
```

### Cache e collegamenti

```
cache:example.com                    # Versione nella cache
link:example.com                     # Pagine che rimandano al sito
related:example.com                  # Siti correlati
```

### Intervallo di date

```
after:2023-01-01                     # Dopo la data
before:2024-01-01                    # Prima della data
daterange:2458850-2459215            # Intervallo di date giuliane
```

### Caratteri jolly e OR

```
admin * panel                        # Carattere jolly
admin OR login                       # Uno dei termini
"admin panel"                        # Frase esatta
-inurl:https                         # Escludi HTTPS
```

### Intervallo numerico

```
inurl:id= 1..1000                    # Intervallo numerico
price $100..$500                     # Intervallo di prezzi
```

---

## 🔗 Combinare gli operatori

### Combinazioni di base

```
site:example.com filetype:pdf
site:example.com inurl:admin
site:example.com intitle:login
site:example.com intext:password
```

### Combinazioni avanzate

```
site:example.com filetype:sql intext:password
site:example.com inurl:admin intitle:login
site:example.com filetype:log intext:error
site:example.com ext:php inurl:config
```

### Combinazioni per il bug bounty

```
site:target.com (filetype:pdf | filetype:doc | filetype:xls)
site:target.com (inurl:admin | inurl:login | inurl:dashboard)
site:target.com (ext:sql | ext:bak | ext:log)
```

---

## 📁 Individuazione dei file

### Documenti sensibili

```
# Documenti Office
site:target.com filetype:doc
site:target.com filetype:docx
site:target.com filetype:xls
site:target.com filetype:xlsx
site:target.com filetype:ppt
site:target.com filetype:pdf

# File di testo
site:target.com filetype:txt
site:target.com filetype:rtf
site:target.com filetype:csv
```

### File di backup

```
site:target.com filetype:bak
site:target.com filetype:backup
site:target.com filetype:old
site:target.com filetype:orig
site:target.com ext:bak
site:target.com inurl:backup
site:target.com intitle:"backup"
```

### File di database

```
site:target.com filetype:sql
site:target.com filetype:db
site:target.com filetype:mdb
site:target.com filetype:sqlite
site:target.com filetype:dump
site:target.com ext:sql intext:INSERT
```

### File di log

```
site:target.com filetype:log
site:target.com ext:log
site:target.com inurl:log
site:target.com intext:"error" filetype:log
site:target.com intext:"exception" filetype:log
site:target.com intext:"fatal" filetype:log
```

### File di configurazione

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

### Codice sorgente

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

## 📂 Elenco delle directory

### Index Of

```
intitle:"index of" site:target.com
intitle:"index of /" site:target.com
intitle:"directory listing" site:target.com
intitle:"parent directory" site:target.com
```

### Directory specifiche

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

### Tipi di file nelle directory

```
intitle:"index of" "*.sql" site:target.com
intitle:"index of" "*.bak" site:target.com
intitle:"index of" "*.log" site:target.com
intitle:"index of" "*.zip" site:target.com
intitle:"index of" "*.tar.gz" site:target.com
```

---

## 🔐 Pannelli di amministrazione

### Pannelli di amministrazione generici

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

### cPanel e hosting

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

### Amministrazione dei database

```
site:target.com inurl:phpmyadmin
site:target.com inurl:phpMyAdmin
site:target.com intitle:"phpMyAdmin"
site:target.com inurl:adminer
site:target.com inurl:pgadmin
site:target.com intitle:"pgAdmin"
site:target.com inurl:mysql
```

### Pannelli di controllo

```
site:target.com intitle:"dashboard"
site:target.com intitle:"control panel"
site:target.com inurl:portal
site:target.com inurl:cms
site:target.com inurl:backend
```

---

## 💾 Database e SQL

### File SQL

```
site:target.com filetype:sql
site:target.com filetype:sql intext:password
site:target.com filetype:sql intext:"INSERT INTO"
site:target.com filetype:sql intext:"CREATE TABLE"
site:target.com ext:sql "INSERT INTO `users`"
```

### Errori SQL

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

### Indizi di SQL injection

```
site:target.com inurl:id=
site:target.com inurl:page=
site:target.com inurl:cat=
site:target.com inurl:item=
site:target.com inurl:pid=
site:target.com inurl:product_id=
site:target.com inurl:news_id=
```

### Esportazioni dei database (dump)

```
site:target.com "dump" filetype:sql
site:target.com "database dump" filetype:sql
site:target.com filetype:sql "-- MySQL dump"
site:target.com filetype:sql "-- phpMyAdmin SQL Dump"
```

---

## ⚙ File di configurazione

### Configurazione del server web

```
site:target.com filetype:conf
site:target.com filetype:conf intext:password
site:target.com "httpd.conf"
site:target.com "nginx.conf"
site:target.com ".htaccess"
site:target.com ".htpasswd"
site:target.com "web.config"
```

### Configurazione dell'applicazione

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

### Configurazione di Git

```
site:target.com ".git/config"
site:target.com intitle:"index of" ".git"
site:target.com inurl:".git"
site:target.com filetype:git
```

### AWS e cloud

```
site:target.com filetype:pem
site:target.com filetype:ppk
site:target.com "aws_access_key_id"
site:target.com "aws_secret_access_key"
site:target.com "AKIA"                    # Prefisso delle chiavi AWS
site:target.com "credentials" filetype:json
```

---

## 🔑 Credenziali e segreti

### Password

```
site:target.com intext:password
site:target.com intext:"password is"
site:target.com intext:"default password"
site:target.com filetype:txt password
site:target.com filetype:log password
site:target.com "password" filetype:xls
site:target.com "password" filetype:csv
```

### Combinazioni di nome utente e password

```
site:target.com intext:username intext:password
site:target.com "username" "password" filetype:txt
site:target.com "login" "password" filetype:xls
site:target.com intext:"username:" intext:"password:"
```

### Chiavi API e token

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

### Indirizzi email

```
site:target.com "@target.com"
site:target.com filetype:xls "@target.com"
site:target.com filetype:csv "email"
site:target.com intext:"@gmail.com"
```

### Chiavi private

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

## ⚠ Server vulnerabili

### Directory accessibili

```
intitle:"index of" site:target.com
intitle:"directory listing" site:target.com
intitle:"Apache" "server at" site:target.com
```

### Pagine predefinite

```
site:target.com intitle:"Apache2 Ubuntu Default Page"
site:target.com intitle:"Welcome to nginx"
site:target.com intitle:"Test Page for Apache"
site:target.com intitle:"IIS Windows Server"
site:target.com intitle:"Welcome to CentOS"
```

### Pagine di errore

```
site:target.com "404 Not Found" inurl:admin
site:target.com "500 Internal Server Error"
site:target.com "Error establishing database connection"
site:target.com "Parse error" "syntax error"
site:target.com "Warning:" "mysql_"
site:target.com "Fatal error:" "php"
```

### Servizi esposti

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

## 🐛 Dork per il bug bounty

### Verifiche rapide

```
# File sensibili
site:target.com (filetype:sql | filetype:bak | filetype:log | filetype:env)

# Pannelli di amministrazione
site:target.com (inurl:admin | inurl:login | inurl:dashboard)

# File di configurazione
site:target.com (filetype:conf | filetype:config | filetype:ini | filetype:env)

# Credenziali
site:target.com (intext:password | intext:api_key | intext:secret)
```

### Individuazione dei sottodomini

```
site:*.target.com -www
site:*.target.com -www -mail
site:*.*.target.com
```

### Endpoint API

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

### Sviluppo e staging

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

### Pagine di autenticazione

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

### Caricamento di file

```
site:target.com inurl:upload
site:target.com intitle:upload
site:target.com inurl:file
site:target.com "choose file"
site:target.com "upload file"
```

---

## 📱 Ricerche specifiche per CMS

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

## 📊 Riferimento rapido

### Operatori essenziali

| Operatore | Funzione | Esempio |
|----------|----------|---------|
| `site:` | Dominio specifico | `site:target.com` |
| `inurl:` | Ricerca nell'URL | `inurl:admin` |
| `intitle:` | Ricerca nel titolo | `intitle:login` |
| `intext:` | Ricerca nel contenuto | `intext:password` |
| `filetype:` | Estensione del file | `filetype:sql` |
| `ext:` | Estensione del file | `ext:pdf` |
| `cache:` | Versione nella cache | `cache:target.com` |
| `"..."` | Frase esatta | `"admin panel"` |
| `-` | Esclusione | `-site:www` |
| `OR` / `|` | Uno dei termini | `admin OR login` |
| `*` | Carattere jolly | `admin * panel` |

### 10 dork principali per il bug bounty

| # | Dork | Scopo |
|---|------|---------|
| 1 | `site:target.com filetype:sql` | File di database |
| 2 | `site:target.com inurl:admin` | Pannelli di amministrazione |
| 3 | `site:target.com filetype:log` | File di log |
| 4 | `site:target.com filetype:env` | File delle variabili d'ambiente |
| 5 | `site:target.com intext:password filetype:txt` | Password |
| 6 | `intitle:"index of" site:target.com` | Elenco delle directory |
| 7 | `site:target.com inurl:api` | Endpoint API |
| 8 | `site:target.com filetype:bak` | File di backup |
| 9 | `site:*.target.com -www` | Sottodomini |
| 10 | `site:target.com "api_key"` | Chiavi API |

---

## 🛠 Strumenti e automazione

### Strumenti per il Google Dorking

| Strumento | Descrizione | URL |
|------|-------------|-----|
| **Google Hacking Database** | Raccolta di dork di Exploit-DB | [GHDB](https://www.exploit-db.com/google-hacking-database) |
| **DorkSearch** | Generatore di dork | [DorkSearch](https://dorksearch.com) |
| **Pagodo** | Google Dorking passivo | GitHub |
| **GooDork** | Strumento di dorking in Python | GitHub |
| **GooFuzz** | Fuzzing tramite Google | GitHub |

### Siti per il dorking

```
# Google Hacking Database
https://www.exploit-db.com/google-hacking-database

# Pentest-Tools
https://pentest-tools.com/information-gathering/google-hacking

# DorkSearch
https://dorksearch.com
```

### Esempio di script di automazione

```bash
#!/bin/bash
# Automazione semplice dei Google dork

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

## 💡 Consigli e buone pratiche

### Consigli per il bug bounty

1. **Inizia in modo ampio, poi restringi la ricerca**
   ```
   site:target.com                    # Prima ricerca
   site:target.com filetype:sql       # Poi una ricerca specifica
   ```

2. **Usa più motori di ricerca**
   - Google, Bing, DuckDuckGo, Yandex
   
3. **Controlla le pagine nella cache**
   ```
   cache:target.com/admin
   ```

4. **Cerca i sottodomini**
   ```
   site:*.target.com -www
   ```

5. **Limita la frequenza delle ricerche**
   - Google può bloccare un numero eccessivo di richieste

### Ridurre la rilevabilità

```
# Usa indirizzi IP o VPN diversi
# Inserisci pause tra le ricerche
# Varia gli schemi di ricerca
# Usa browser diversi
```

---

## ⚠ Avvertenza legale

> **ATTENZIONE:** il Google Dorking in sé è lecito, ma **accedere** senza autorizzazione ai dati esposti può essere illegale.
> 
> - ✅ Usalo per test di sicurezza autorizzati
> - ✅ Usalo nei programmi di bug bounty
> - ✅ Segnala le vulnerabilità in modo responsabile
> - ❌ Non accedere mai ai dati senza permesso
> - ❌ Non scaricare o conservare dati sensibili
> - ❌ Non sfruttare le vulnerabilità individuate
> 
> **Ottieni sempre l'autorizzazione necessaria prima dei test!**

---

## 📚 Risorse

### Risorse ufficiali
- [Operatori di ricerca Google](https://support.google.com/websearch/answer/2466433)
- [Google Hacking Database](https://www.exploit-db.com/google-hacking-database)

### Libri e guide
- "Google Hacking for Penetration Testers" di Johnny Long
- Guida ai test OWASP

### Schede correlate
- [Shodan](../../Shodan/translations/README.it.md)
- [GitHub Dorking](../../GitHub-Dorking/translations/README.it.md)
- [Subfinder](../../Subfinder/translations/README.it.md)

---

<p align="center">
  <b>🔍 Trova ciò che gli altri nascondono!</b><br>
  <i>Padroneggia l'arte del Google Dorking</i>
</p>
