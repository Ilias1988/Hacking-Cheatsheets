# 📚 Ordlister – fullstendig referanseguide

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ██╗███████╗████████╗███████╗
  ██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██║██╔════╝╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     ██║███████╗   ██║   ███████╗
  ██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██║╚════██║   ██║   ╚════██║
  ╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗██║███████║   ██║   ███████║
   ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Wordlists-Reference-red?style=for-the-badge" alt="Ordlister">
  <img src="https://img.shields.io/badge/SecLists-orange?style=for-the-badge" alt="SecLists">
  <img src="https://img.shields.io/badge/Brute_Force-blue?style=for-the-badge" alt="Systematisk gjetting">
  <img src="https://img.shields.io/badge/Fuzzing-green?style=for-the-badge" alt="Fuzzing">
</p>

<p align="center">
  <b>📖 Fullstendig referanse for ordlister til penetrasjonstesting</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Innhold

- [Passordordlister](#-passordordlister)
- [Ordlister for kataloger og filer](#-ordlister-for-kataloger-og-filer)
- [Ordlister for underdomener](#-ordlister-for-underdomener)
- [Ordlister for brukernavn](#-ordlister-for-brukernavn)
- [Ordlister for fuzzing av webapplikasjoner](#-ordlister-for-fuzzing-av-webapplikasjoner)
- [SecLists-samlingen](#-seclists-samlingen)
- [Standardordlister i Kali](#-standardordlister-i-kali)
- [Verktøy for egne ordlister](#-verktøy-for-egne-ordlister)
- [Rask nedlasting](#-rask-nedlasting)
- [Tips og god praksis](#-tips-og-god-praksis)
- [Hurtigreferanse](#-hurtigreferanse)
- [Ressurser](#-ressurser)

---

> En **ordliste (wordlist)** er en liste over mulige verdier, for eksempel passord eller katalognavn. **Fuzzing** tester mange ulike inndata; **enumeration** betyr systematisk kartlegging. Bug bounty er et belønningsprogram for rapportering av sårbarheter. Størrelser, linjetall og katalogtrær nedenfor er eksempler fra originalen og kan variere med utgaven.

## 🔐 Passordordlister

### Mest brukte

| Ordliste | Størrelse | Linjer | Kilde | Bruksområde |
|----------|------|-------|--------|----------|
| **rockyou.txt** | 139 MB | 14.3M | Kali/SecLists | Passordknekking |
| **darkweb2017-top10000.txt** | 83 KB | 10K | SecLists | Vanlige passord |
| **xato-net-10-million-passwords.txt** | 83 MB | 10M | SecLists | Stor passordliste |
| **common-passwords-win.txt** | - | - | SecLists | Windows-passord |
| **10-million-password-list-top-1000000.txt** | 8.1 MB | 1M | SecLists | De 1M vanligste passordene |

### Passordlister etter type

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

### Spesialiserte passordlister

| Ordliste | Formål | Plassering |
|----------|---------|----------|
| **wifi-passwords.txt** | Knekking av WiFi-passord | SecLists/Passwords/ |
| **probable-v2-top12000.txt** | Statistisk analyse | SecLists/Passwords/ |
| **seasons-only.txt** | Årstidsbaserte mønstre | SecLists/Passwords/Honeypot-Captures/ |
| **twitter-banned.txt** | Forbudte passord | SecLists/Passwords/ |
| **cirt-default-passwords.txt** | Standard påloggingsopplysninger | SecLists/Passwords/Default-Credentials/ |

### Plasseringer i Kali

```bash
# RockYou (pakk ut først)
/usr/share/wordlists/rockyou.txt.gz
gunzip /usr/share/wordlists/rockyou.txt.gz

# John the Ripper
/usr/share/john/password.lst

# Metasploit
/usr/share/metasploit-framework/data/wordlists/
```

---

## 📂 Ordlister for kataloger og filer

### Systematisk gjetting av katalognavn (brute force)

| Ordliste | Størrelse | Linjer | Kilde | Bruksområde |
|----------|------|-------|--------|----------|
| **common.txt** | 36 KB | 4.6K | DirBuster | Rask skanning |
| **directory-list-2.3-small.txt** | 756 KB | 87K | DirBuster | Begrenset kartlegging |
| **directory-list-2.3-medium.txt** | 1.9 MB | 220K | DirBuster | Standard kartlegging |
| **directory-list-2.3-big.txt** | 14 MB | 1.2M | DirBuster | Fullstendig kartlegging |
| **big.txt** | 210 KB | 20K | SecLists | Vanlige kataloger |

### RAFT-serien (anbefales!)

```
SecLists/Discovery/Web-Content/
├── raft-large-directories.txt      # 62K kataloger
├── raft-large-files.txt            # 37K filer
├── raft-large-words.txt            # 120K ord
├── raft-medium-directories.txt     # 30K kataloger
├── raft-medium-files.txt           # 17K filer
├── raft-medium-words.txt           # 63K ord
├── raft-small-directories.txt      # 20K kataloger
├── raft-small-files.txt            # 11K filer
└── raft-small-words.txt            # 43K ord
```

### Lister over filendelser

| Ordliste | Formål |
|----------|---------|
| **web-extensions.txt** | Vanlige filendelser på nettet |
| **backup-extensions.txt** | Sikkerhetskopifiler (.bak, .old) |
| **raft-large-extensions.txt** | Stor liste over filendelser |

### Spesialisert kartlegging

| Ordliste | Formål | Linjer |
|----------|---------|-------|
| **apache.txt** | Spesifikt for Apache | - |
| **nginx.txt** | Spesifikt for Nginx | - |
| **iis.txt** | Spesifikt for IIS | - |
| **api-endpoints.txt** | Kartlegging av API-er | - |
| **graphql.txt** | GraphQL-endepunkter | - |
| **swagger.txt** | Swagger/OpenAPI | - |

### Vanlige plasseringer

```bash
# DirBuster
/usr/share/dirbuster/wordlists/
/usr/share/dirb/wordlists/

# Kartleggingslister i SecLists
/usr/share/seclists/Discovery/Web-Content/

# Raske nedlastinger
common.txt
directory-list-2.3-medium.txt
raft-medium-directories.txt
```

---

## 🌐 Ordlister for underdomener

### Populære lister over underdomener

| Ordliste | Linjer | Kilde | Bruksområde |
|----------|-------|--------|----------|
| **subdomains-top1million-5000.txt** | 5K | SecLists | Rask kartlegging |
| **subdomains-top1million-20000.txt** | 20K | SecLists | Standard kartlegging |
| **subdomains-top1million-110000.txt** | 110K | SecLists | Grundig kartlegging |
| **dns-Jhaddix.txt** | 2M+ | SecLists | Bug bounty |
| **fierce-hostlist.txt** | - | SecLists | Kompatibel med Fierce |
| **combined_subdomains.txt** | 648K | SecLists | Kombinert liste |

### Plassering av lister over underdomener

```
SecLists/Discovery/DNS/
├── subdomains-top1million-5000.txt
├── subdomains-top1million-20000.txt
├── subdomains-top1million-110000.txt
├── dns-Jhaddix.txt                    # Best egnet til bug bounty!
├── combined_subdomains.txt
├── deepmagic.com-prefixes-top500.txt
├── deepmagic.com-prefixes-top50000.txt
├── fierce-hostlist.txt
├── namelist.txt
├── shubs-subdomains.txt
└── bitquark-subdomains-top100000.txt
```

### Anbefalt til bug bounty

```bash
# Jason Haddix sin metode
dns-Jhaddix.txt              # 2M+ underdomener

# Kombiner lister for grundigere dekning
cat subdomains-top1million-110000.txt dns-Jhaddix.txt | sort -u > mega-subdomain.txt
```

---

## 👤 Ordlister for brukernavn

### Vanlige brukernavnlister

| Ordliste | Linjer | Formål |
|----------|-------|---------|
| **top-usernames-shortlist.txt** | 17 | Rask kontroll |
| **xato-net-10-million-usernames.txt** | 10M | Stor liste |
| **cirt-default-usernames.txt** | - | Standardbrukere |
| **names.txt** | - | Vanlige navn |
| **familynames-usa-top1000.txt** | 1K | Etternavn |

### Plassering av brukernavnlister

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

### Standard påloggingsopplysninger

| Tjeneste | Ordliste |
|---------|----------|
| **FTP** | ftp-betterdefaultpasslist.txt |
| **SSH** | ssh-betterdefaultpasslist.txt |
| **Tomcat** | tomcat-betterdefaultpasslist.txt |
| **MySQL** | mysql-betterdefaultpasslist.txt |
| **MSSQL** | mssql-betterdefaultpasslist.txt |
| **PostgreSQL** | postgres-betterdefaultpasslist.txt |

---

## 💉 Ordlister for fuzzing av webapplikasjoner

### SQL-injeksjon

```
SecLists/Fuzzing/SQLi/
├── Generic-SQLi.txt
├── quick-SQLi.txt
├── MSSQL.fuzzdb.txt
├── MySQL.fuzzdb.txt
├── PostgreSQL.fuzzdb.txt
└── oracle.fuzzdb.txt
```

### XSS (Cross-Site Scripting – skriptinjeksjon på tvers av nettsteder)

```
SecLists/Fuzzing/XSS/
├── XSS-Bypass-Strings-BruteLogic.txt
├── XSS-Cheat-Sheet-PortSwigger.txt
├── XSS-Jhaddix.txt
├── XSS-RSNAKE.txt
├── XSS-Vectors-Mario.txt
└── xss-without-parentheses-semi-colons-portswigger.txt
```

### LFI/RFI (inkludering av lokale eller eksterne filer)

```
SecLists/Fuzzing/LFI/
├── LFI-Jhaddix.txt
├── LFI-LFISuite-pathtotest-huge.txt
├── LFI-LFISuite-pathtotest.txt
└── LFI-gracefulsecurity-linux.txt
```

### Kommandoinjeksjon

```
SecLists/Fuzzing/command-injection-commix.txt
SecLists/Fuzzing/command-injection-testing-unix.txt
SecLists/Fuzzing/command-injection-testing-windows.txt
```

### SSTI (injeksjon i maler på serversiden)

```
SecLists/Fuzzing/SSTI/
├── SSTI.txt
└── tplmap-template-injection.txt
```

### Samlinger for fuzzing

| Ordliste | Formål |
|----------|---------|
| **special-chars.txt** | Spesialtegn |
| **Unicode.txt** | Fuzzing med Unicode |
| **big-list-of-naughty-strings.txt** | Grensetilfeller |
| **fuzz-Bo0oM.txt** | Generell fuzzing |
| **burp-parameter-names.txt** | Parameternavn |

---

## 📦 SecLists-samlingen

### Om SecLists

**SecLists** er en nyttig følgesvenn for penetrasjonstestere: en samling av ulike listetyper som brukes i sikkerhetsvurderinger.

### Installasjon

> **Merknad om tilgjengelighet:** SecLists er en egen pakke og er ikke garantert forhåndsinstallert i alle Kali-utgaver. Installasjonseksemplet med APT forutsetter at pakken finnes i de konfigurerte pakkekildene; det gjelder ikke automatisk alle Ubuntu-/Debian-oppsett. Filnavn og underkataloger kan endres mellom utgaver. Se [Kalis pakkeside](https://www.kali.org/tools/seclists/) og [SecLists-repositoriet](https://github.com/danielmiessler/SecLists).

```bash
# Kali Linux (forhåndsinstallert ifølge originalen; se merknaden)
ls /usr/share/seclists/

# Ubuntu/Debian
sudo apt install seclists

# Kloning med Git
git clone https://github.com/danielmiessler/SecLists.git

# Rask nedlasting (utvalgte lister)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt
```

### Strukturen i SecLists

```
SecLists/
├── Discovery/
│   ├── DNS/                    # Ordlister for underdomener
│   ├── Web-Content/            # Ordlister for kataloger og filer
│   └── Infrastructure/         # Tjenester og porter
├── Fuzzing/
│   ├── SQLi/                   # Nyttelaster for SQL-injeksjon
│   ├── XSS/                    # Nyttelaster for XSS
│   ├── LFI/                    # Nyttelaster for LFI/RFI
│   └── command-injection*.txt  # Kommandoinjeksjon
├── Passwords/
│   ├── Common-Credentials/     # Passordlister
│   ├── Leaked-Databases/       # rockyou og lignende
│   └── Default-Credentials/    # Standardpassord
├── Usernames/
│   ├── Names/                  # Navnelister
│   └── top-usernames*.txt      # Brukernavnlister
├── Payloads/
│   └── Anti-Antivirus/         # Omgåelse av antivirus
└── Pattern-Matching/
    └── Hunting/                # Mønstre med regulære uttrykk
```

### GitHub-repositorium

🔗 https://github.com/danielmiessler/SecLists

---

## 🐧 Standardordlister i Kali

### Plassering av ordlister

> **Merknad om stier:** Innholdet avhenger av installerte pakker. RockYou leveres komprimert som `rockyou.txt.gz` i Kalis `wordlists`-pakke. `nmap.lst` peker på `nselib/data/passwords.lst`, altså en passordliste brukt av Nmap, og er ikke en liste over skript. Originalens tre og kommandoer er beholdt. Se [Kalis dokumentasjon for wordlists](https://www.kali.org/tools/wordlists/).

```bash
# Hovedkatalog for ordlister
/usr/share/wordlists/

# Innhold
ls -la /usr/share/wordlists/
├── rockyou.txt.gz          # Pakk ut med gunzip
├── dirb/                   # DIRB-ordlister
├── dirbuster/              # DirBuster-ordlister
├── fasttrack.txt           # Fasttrack-liste
├── fern-wifi/              # WiFi-ordlister
├── metasploit/             # MSF-ordlister
├── nmap.lst                # Liste for Nmap-skript (se merknaden)
├── wfuzz/                  # wFuzz-ordlister
└── seclists -> /usr/share/seclists/
```

### DirBuster-lister

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

### DIRB-lister

```bash
ls /usr/share/dirb/wordlists/
├── big.txt                  # 20K oppføringer
├── common.txt               # 4.6K oppføringer
├── small.txt                # 959 oppføringer
├── catala.txt               # Katalansk
├── euskera.txt              # Baskisk
├── extensions_common.txt    # Filendelser
├── indexes.txt              # Indeksfiler
├── mutations_common.txt     # Mutasjoner
├── spanish.txt              # Spansk
├── stress/                  # Belastningstester
└── vulns/                   # Sårbarhetskontroller
```

---

<a id="-verktøy-for-egne-ordlister"></a>
## 🛠️ Verktøy for egne ordlister

### CeWL (generator for egne ordlister)

```bash
# Installasjon
sudo apt install cewl

# Grunnleggende bruk – gjennomgå et nettsted
cewl https://target.com -w wordlist.txt

# Med dybde og minste ordlengde
cewl https://target.com -d 3 -m 6 -w wordlist.txt

# Ta med e-postadresser
cewl https://target.com -e -w wordlist.txt

# Alternativer
-d <depth>      # Dybde for gjennomgang (standard: 2)
-m <length>     # Minste ordlengde (standard: 3)
-w <file>       # Utdatafil
-e              # Ta med e-postadresser
--lowercase     # Konverter til små bokstaver
-c              # Tell forekomster av ord
```

### Crunch (ordlistegenerator)

```bash
# Installasjon
sudo apt install crunch

# Grunnleggende syntaks
crunch <min-len> <max-len> [charset] -o <output>

# Generer passord med 4–6 tegn
crunch 4 6 -o wordlist.txt

# Med bestemte tegn
crunch 8 8 abcdefghijklmnopqrstuvwxyz0123456789 -o wordlist.txt

# Med mønster
crunch 6 6 -t @@%%^^ -o wordlist.txt
# @ = små bokstaver, , = store bokstaver, % = tall, ^ = spesialtegn

# Eget tegnsett
crunch 4 4 -f /usr/share/crunch/charset.lst mixalpha-numeric -o wordlist.txt

# Send videre til hashcat med en pipe
crunch 8 8 0123456789 | hashcat -m 0 hash.txt
```

### CUPP (Common User Passwords Profiler – profilbasert passordgenerator)

```bash
# Installasjon
git clone https://github.com/Mebus/cupp.git
cd cupp

# Interaktiv modus basert på en profil
python3 cupp.py -i

# Spørsmål som stilles:
# - Fornavn, etternavn, kallenavn
# - Fødselsdato
# - Opplysninger om partneren
# - Navn på kjæledyr
# - Firmanavn
# - Nøkkelord

# Last ned vanlige passord
python3 cupp.py -l

# Bruk en eksisterende ordliste med profilering
python3 cupp.py -w wordlist.txt
```

### Mentalist (ordlistegenerator med grafisk grensesnitt)

```bash
# Grafisk verktøy for generering av komplekse ordlister
# Støtter regler, endring av store og små bokstaver og tegnerstatning
# Last ned fra: https://github.com/sc0tfree/mentalist
```

### Wordlister (Python)

```python
#!/usr/bin/env python3
# Enkel generator for egne ordlister
import itertools

# Generer kombinasjoner
chars = 'abc123'
length = 4

for combo in itertools.product(chars, repeat=length):
    print(''.join(combo))
```

### Hashcat-regler

```bash
# Bruk regler til å endre ordlisten
hashcat -r /usr/share/hashcat/rules/best64.rule wordlist.txt --stdout

# Populære regler
/usr/share/hashcat/rules/
├── best64.rule         # De beste 64 reglene
├── rockyou-30000.rule  # Basert på RockYou
├── d3ad0ne.rule        # Reglene til d3ad0ne
├── dive.rule           # DIVE-regler
└── toggles1-5.rule     # Bytt mellom store og små bokstaver
```

---

<a id="-rask-nedlasting"></a>
## ⬇️ Rask nedlasting

### Nedlastinger med én linje

```bash
# RockYou
wget https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt

# SecLists (hele samlingen)
git clone --depth 1 https://github.com/danielmiessler/SecLists.git

# Vanlige passord (1M)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt

# Mellomstor katalogliste
wget https://raw.githubusercontent.com/daviddias/node-dirbuster/master/lists/directory-list-2.3-medium.txt

# Liste over underdomener
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/subdomains-top1million-110000.txt

# Jhaddix DNS (bug bounty)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/dns-Jhaddix.txt
```

### Klon repositorier

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

## 💡 Tips og god praksis

### Velg riktig ordliste

| Oppgave | Anbefalt ordliste |
|------|---------------------|
| **Rask katalogskanning** | common.txt, raft-small-directories.txt |
| **Grundig katalogskanning** | directory-list-2.3-medium.txt |
| **Fullstendig katalogskanning** | directory-list-2.3-big.txt |
| **Kartlegging av underdomener** | subdomains-top1million-20000.txt |
| **DNS for bug bounty** | dns-Jhaddix.txt |
| **Passordknekking** | rockyou.txt |
| **Rask passordtest** | top-10000.txt |

### Kombiner ordlister

```bash
# Kombiner og fjern duplikater
cat list1.txt list2.txt | sort -u > combined.txt

# Legg til et prefiks på alle linjer
sed 's/^/admin_/' wordlist.txt > admin_wordlist.txt

# Legg til et suffiks
sed 's/$/.php/' wordlist.txt > php_wordlist.txt

# Begge deler
while read line; do echo "${line}.php"; echo "${line}.html"; done < wordlist.txt > extended.txt
```

### Optimalisering av ordlister

```bash
# Sorter og fjern duplikater
sort -u wordlist.txt -o wordlist.txt

# Fjern tomme linjer
sed -i '/^$/d' wordlist.txt

# Fjern linjer med færre enn 4 tegn
awk 'length >= 4' wordlist.txt > filtered.txt

# Konverter til små bokstaver
tr '[:upper:]' '[:lower:]' < wordlist.txt > lowercase.txt

# Tell linjer
wc -l wordlist.txt
```

### Tips for bedre ytelse

1. **Begynn i det små** – Bruk mindre lister først, og utvid deretter
2. **Bruk mønstre** – Bruk bestemte mønstre når du kjenner formatet
3. **Kombiner verktøy** – Generer egne lister og bruk standardlister i tillegg
4. **Tilpass listene** – Lag lister som er tilpasset målet, med CeWL

---

## 📊 Hurtigreferanse

### Anbefalte ordlister etter kategori

| Kategori | Beste valg | Plassering |
|----------|-------------|----------|
| **Passord** | rockyou.txt | SecLists/Passwords/ |
| **Kataloger** | directory-list-2.3-medium.txt | DirBuster |
| **Filer** | raft-large-files.txt | SecLists |
| **Underdomener** | dns-Jhaddix.txt | SecLists/Discovery/DNS/ |
| **Brukernavn** | xato-net-10-million-usernames.txt | SecLists/Usernames/ |
| **SQLi** | Generic-SQLi.txt | SecLists/Fuzzing/SQLi/ |
| **XSS** | XSS-Jhaddix.txt | SecLists/Fuzzing/XSS/ |
| **LFI** | LFI-Jhaddix.txt | SecLists/Fuzzing/LFI/ |

### Størrelsesguide

| Størrelse | Linjer | Bruksområde |
|------|-------|----------|
| **Liten** | < 5K | Raske tester |
| **Middels** | 5K - 100K | Standard kartlegging |
| **Stor** | 100K - 1M | Grundig skanning |
| **Svært stor** | > 1M | Fullstendig dekning |

---

## 📚 Ressurser

### Repositorier med ordlister
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Assetnote Wordlists](https://github.com/assetnote/wordlists)

### Verktøy
- [CeWL](https://github.com/digininja/CeWL)
- [CUPP](https://github.com/Mebus/cupp)
- [Crunch](https://sourceforge.net/projects/crunch-wordlist/)
- [Mentalist](https://github.com/sc0tfree/mentalist)

### Relaterte hurtigreferanser
- [Hydra](../../Hydra/translations/README.nb.md)
- [John the Ripper](../../John-The-Ripper/translations/README.nb.md)
- [Hashcat](../../Hashcat/translations/README.nb.md)
- [Gobuster](../../Gobuster/translations/README.nb.md)
- [ffuf](../../ffuf/translations/README.nb.md)

---

<p align="center">
  <b>📚 Riktig ordliste kan være avgjørende!</b><br>
  <i>Velg med omhu, kombiner smart</i>
</p>
