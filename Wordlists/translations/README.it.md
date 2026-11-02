# 📚 Wordlists - Guida di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota sugli esempi originali:** dimensioni, numero di righe e percorsi dipendono dalla revisione delle raccolte e dai pacchetti installati; “preinstallato” non vale per ogni immagine o distribuzione. La disponibilità di `seclists` tramite APT va verificata nei repository usati. Le preferenze indicate come “migliori” non sono un benchmark. Inoltre `nmap.lst` è un collegamento a una lista di password, non un elenco di script Nmap, come mostra la [documentazione ufficiale Kali](https://www.kali.org/tools/wordlists/). Le strutture e i comandi restano quelli dell’originale; consulta anche [SecLists](https://github.com/danielmiessler/SecLists) e il [pacchetto Kali](https://www.kali.org/tools/seclists/). Una wordlist è un elenco di parole o valori candidati; il fuzzing usa tali input per verificare il comportamento del software.

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
  <b>📖 Riferimento completo alle wordlist per il penetration testing</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

## 📋 Indice

- [Wordlist di password](#-wordlist-di-password)
- [Wordlist di directory e file](#-wordlist-di-directory-e-file)
- [Wordlist di sottodomini](#-wordlist-di-sottodomini)
- [Wordlist di nomi utente](#-wordlist-di-nomi-utente)
- [Wordlist per il fuzzing web](#-wordlist-per-il-fuzzing-web)
- [Raccolta SecLists](#-raccolta-seclists)
- [Wordlist predefinite di Kali](#-wordlist-predefinite-di-kali)
- [Strumenti per wordlist personalizzate](#-strumenti-per-wordlist-personalizzate)
- [Download rapido](#-download-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)

---

## 🔐 Wordlist di password

### Le più popolari

| Wordlist | Dimensione | Righe | Fonte | Caso d'uso |
|----------|------|-------|--------|----------|
| **rockyou.txt** | 139 MB | 14.3M | Kali/SecLists | Recupero delle password tramite tentativi (cracking) |
| **darkweb2017-top10000.txt** | 83 KB | 10K | SecLists | Password comuni |
| **xato-net-10-million-passwords.txt** | 83 MB | 10M | SecLists | Elenco esteso di password |
| **common-passwords-win.txt** | - | - | SecLists | Password Windows |
| **10-million-password-list-top-1000000.txt** | 8.1 MB | 1M | SecLists | Le 1M password più frequenti |

### Elenchi di password per tipo

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

### Elenchi di password specializzati

| Wordlist | Scopo | Percorso |
|----------|---------|----------|
| **wifi-passwords.txt** | Cracking delle password WiFi | SecLists/Passwords/ |
| **probable-v2-top12000.txt** | Analisi statistica | SecLists/Passwords/ |
| **seasons-only.txt** | Schemi stagionali | SecLists/Passwords/Honeypot-Captures/ |
| **twitter-banned.txt** | Password vietate | SecLists/Passwords/ |
| **cirt-default-passwords.txt** | Credenziali predefinite | SecLists/Passwords/Default-Credentials/ |

### Percorsi in Kali

```bash
# RockYou (decomprimere prima)
/usr/share/wordlists/rockyou.txt.gz
gunzip /usr/share/wordlists/rockyou.txt.gz

# John the Ripper
/usr/share/john/password.lst

# Metasploit
/usr/share/metasploit-framework/data/wordlists/
```

---

## 📂 Wordlist di directory e file

### Ricerca di directory tramite brute force

| Wordlist | Dimensione | Righe | Fonte | Caso d'uso |
|----------|------|-------|--------|----------|
| **common.txt** | 36 KB | 4.6K | DirBuster | Scansione rapida |
| **directory-list-2.3-small.txt** | 756 KB | 87K | DirBuster | Enumerazione ridotta |
| **directory-list-2.3-medium.txt** | 1.9 MB | 220K | DirBuster | Enumerazione standard |
| **directory-list-2.3-big.txt** | 14 MB | 1.2M | DirBuster | Enumerazione completa |
| **big.txt** | 210 KB | 20K | SecLists | Directory comuni |

### Serie RAFT (vivamente consigliata nell'originale)

```
SecLists/Discovery/Web-Content/
├── raft-large-directories.txt      # 62K directory
├── raft-large-files.txt            # 37K file
├── raft-large-words.txt            # 120K parole
├── raft-medium-directories.txt     # 30K directory
├── raft-medium-files.txt           # 17K file
├── raft-medium-words.txt           # 63K parole
├── raft-small-directories.txt      # 20K directory
├── raft-small-files.txt            # 11K file
└── raft-small-words.txt            # 43K parole
```

### Elenchi di estensioni dei file

| Wordlist | Scopo |
|----------|---------|
| **web-extensions.txt** | Estensioni web comuni |
| **backup-extensions.txt** | File di backup (.bak, .old) |
| **raft-large-extensions.txt** | Elenco esteso di estensioni |

### Ricerca specializzata

| Wordlist | Scopo | Righe |
|----------|---------|-------|
| **apache.txt** | Specifico per Apache | - |
| **nginx.txt** | Specifico per Nginx | - |
| **iis.txt** | Specifico per IIS | - |
| **api-endpoints.txt** | Individuazione delle API | - |
| **graphql.txt** | Endpoint GraphQL | - |
| **swagger.txt** | Swagger/OpenAPI | - |

### Percorsi comuni

```bash
# DirBuster
/usr/share/dirbuster/wordlists/
/usr/share/dirb/wordlists/

# Sezione Discovery di SecLists
/usr/share/seclists/Discovery/Web-Content/

# Download rapidi
common.txt
directory-list-2.3-medium.txt
raft-medium-directories.txt
```

---

## 🌐 Wordlist di sottodomini

### Elenchi di sottodomini popolari

| Wordlist | Righe | Fonte | Caso d'uso |
|----------|-------|--------|----------|
| **subdomains-top1million-5000.txt** | 5K | SecLists | Enumerazione rapida |
| **subdomains-top1million-20000.txt** | 20K | SecLists | Enumerazione standard |
| **subdomains-top1million-110000.txt** | 110K | SecLists | Enumerazione approfondita |
| **dns-Jhaddix.txt** | 2M+ | SecLists | Bug bounty |
| **fierce-hostlist.txt** | - | SecLists | Compatibile con Fierce |
| **combined_subdomains.txt** | 648K | SecLists | Elenco combinato |

### Percorso degli elenchi di sottodomini

```
SecLists/Discovery/DNS/
├── subdomains-top1million-5000.txt
├── subdomains-top1million-20000.txt
├── subdomains-top1million-110000.txt
├── dns-Jhaddix.txt                    # La scelta migliore per il bug bounty secondo l'originale
├── combined_subdomains.txt
├── deepmagic.com-prefixes-top500.txt
├── deepmagic.com-prefixes-top50000.txt
├── fierce-hostlist.txt
├── namelist.txt
├── shubs-subdomains.txt
└── bitquark-subdomains-top100000.txt
```

### Consigli per il bug bounty

```bash
# Metodologia di Jason Haddix
dns-Jhaddix.txt              # 2M+ sottodomini

# Combinazione per una ricerca approfondita
cat subdomains-top1million-110000.txt dns-Jhaddix.txt | sort -u > mega-subdomain.txt
```

---

## 👤 Wordlist di nomi utente

### Elenchi di nomi utente comuni

| Wordlist | Righe | Scopo |
|----------|-------|---------|
| **top-usernames-shortlist.txt** | 17 | Verifica rapida |
| **xato-net-10-million-usernames.txt** | 10M | Elenco esteso |
| **cirt-default-usernames.txt** | - | Utenti predefiniti |
| **names.txt** | - | Nomi comuni |
| **familynames-usa-top1000.txt** | 1K | Cognomi |

### Percorso degli elenchi di nomi utente

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

### Credenziali predefinite

| Servizio | Wordlist |
|---------|----------|
| **FTP** | ftp-betterdefaultpasslist.txt |
| **SSH** | ssh-betterdefaultpasslist.txt |
| **Tomcat** | tomcat-betterdefaultpasslist.txt |
| **MySQL** | mysql-betterdefaultpasslist.txt |
| **MSSQL** | mssql-betterdefaultpasslist.txt |
| **PostgreSQL** | postgres-betterdefaultpasslist.txt |

---

## 💉 Wordlist per il fuzzing web

### SQL injection

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

### LFI/RFI (inclusione di file locali o remoti)

```
SecLists/Fuzzing/LFI/
├── LFI-Jhaddix.txt
├── LFI-LFISuite-pathtotest-huge.txt
├── LFI-LFISuite-pathtotest.txt
└── LFI-gracefulsecurity-linux.txt
```

### Injection di comandi

```
SecLists/Fuzzing/command-injection-commix.txt
SecLists/Fuzzing/command-injection-testing-unix.txt
SecLists/Fuzzing/command-injection-testing-windows.txt
```

### SSTI (injection nei template lato server)

```
SecLists/Fuzzing/SSTI/
├── SSTI.txt
└── tplmap-template-injection.txt
```

### Raccolte per il fuzzing

| Wordlist | Scopo |
|----------|---------|
| **special-chars.txt** | Caratteri speciali |
| **Unicode.txt** | Fuzzing Unicode |
| **big-list-of-naughty-strings.txt** | Casi limite |
| **fuzz-Bo0oM.txt** | Fuzzing generico |
| **burp-parameter-names.txt** | Nomi dei parametri |

---

## 📦 Raccolta SecLists

### Informazioni su SecLists

**SecLists** è una risorsa per il pentester: una raccolta di diversi tipi di elenchi usati durante le valutazioni di sicurezza.

### Installazione

```bash
# Kali Linux (preinstallato nell'originale)
ls /usr/share/seclists/

# Ubuntu/Debian
sudo apt install seclists

# Clonazione con Git
git clone https://github.com/danielmiessler/SecLists.git

# Download rapido di elenchi specifici
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt
```

### Struttura di SecLists

```
SecLists/
├── Discovery/
│   ├── DNS/                    # Wordlist di sottodomini
│   ├── Web-Content/            # Wordlist di directory e file
│   └── Infrastructure/         # Servizi e porte
├── Fuzzing/
│   ├── SQLi/                   # Payload di SQL injection
│   ├── XSS/                    # Payload XSS
│   ├── LFI/                    # Payload LFI/RFI
│   └── command-injection*.txt  # Injection di comandi
├── Passwords/
│   ├── Common-Credentials/     # Elenchi di password
│   ├── Leaked-Databases/       # rockyou e altri
│   └── Default-Credentials/    # Password predefinite
├── Usernames/
│   ├── Names/                  # Elenchi di nomi
│   └── top-usernames*.txt      # Elenchi di nomi utente
├── Payloads/
│   └── Anti-Antivirus/         # Aggiramento degli antivirus
└── Pattern-Matching/
    └── Hunting/                # Espressioni regolari (regex)
```

### Repository GitHub

🔗 https://github.com/danielmiessler/SecLists

---

## 🐧 Wordlist predefinite di Kali

### Percorso delle wordlist

```bash
# Directory principale delle wordlist
/usr/share/wordlists/

# Contenuto
ls -la /usr/share/wordlists/
├── rockyou.txt.gz          # Decomprimi con gunzip
├── dirb/                   # Wordlist DIRB
├── dirbuster/              # Wordlist DirBuster
├── fasttrack.txt           # Elenco fast track
├── fern-wifi/              # Wordlist WiFi
├── metasploit/             # Wordlist MSF
├── nmap.lst                # Elenco degli script Nmap (descrizione dell'originale)
├── wfuzz/                  # Wordlist wFuzz
└── seclists -> /usr/share/seclists/
```

### Elenchi DirBuster

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

### Elenchi DIRB

```bash
ls /usr/share/dirb/wordlists/
├── big.txt                  # 20K voci
├── common.txt               # 4.6K voci
├── small.txt                # 959 voci
├── catala.txt               # Catalano
├── euskera.txt              # Basco
├── extensions_common.txt    # Estensioni
├── indexes.txt              # File indice
├── mutations_common.txt     # Mutazioni
├── spanish.txt              # Spagnolo
├── stress/                  # Test di stress
└── vulns/                   # Verifiche delle vulnerabilità
```

---

## 🛠 Strumenti per wordlist personalizzate

### CeWL (generatore di wordlist personalizzate)

```bash
# Installazione
sudo apt install cewl

# Uso di base: esplora un sito web (crawling)
cewl https://target.com -w wordlist.txt

# Con profondità e lunghezza minima delle parole
cewl https://target.com -d 3 -m 6 -w wordlist.txt

# Includi le email
cewl https://target.com -e -w wordlist.txt

# Opzioni
-d <depth>      # Profondità di esplorazione (predefinita: 2)
-m <length>     # Lunghezza minima delle parole (predefinita: 3)
-w <file>       # File di output
-e              # Includi gli indirizzi email
--lowercase     # Converti in minuscolo
-c              # Conta le occorrenze delle parole
```

### Crunch (generatore di wordlist)

```bash
# Installazione
sudo apt install crunch

# Sintassi di base
crunch <min-len> <max-len> [charset] -o <output>

# Genera password di 4-6 caratteri
crunch 4 6 -o wordlist.txt

# Con caratteri specifici
crunch 8 8 abcdefghijklmnopqrstuvwxyz0123456789 -o wordlist.txt

# Con uno schema
crunch 6 6 -t @@%%^^ -o wordlist.txt
# @ = minuscole, , = maiuscole, % = cifra, ^ = speciale

# Insieme di caratteri personalizzato (charset)
crunch 4 4 -f /usr/share/crunch/charset.lst mixalpha-numeric -o wordlist.txt

# Invia l'output a hashcat tramite pipe
crunch 8 8 0123456789 | hashcat -m 0 hash.txt
```

### CUPP (creazione di profili per password comuni degli utenti)

```bash
# Installazione
git clone https://github.com/Mebus/cupp.git
cd cupp

# Modalità interattiva basata sul profilo
python3 cupp.py -i

# Informazioni richieste:
# - Nome, cognome, soprannome
# - Data di nascita
# - Informazioni sul partner
# - Nome dell'animale domestico
# - Nome dell'azienda
# - Parole chiave

# Scarica password comuni
python3 cupp.py -l

# Usa una wordlist esistente con la profilazione
python3 cupp.py -w wordlist.txt
```

### Mentalist (generatore di wordlist con interfaccia grafica)

```bash
# Strumento grafico per generare wordlist complesse
# Supporta regole, modifiche di maiuscole/minuscole e sostituzioni
# Download da: https://github.com/sc0tfree/mentalist
```

### Wordlister (Python)

```python
#!/usr/bin/env python3
# Semplice generatore di wordlist personalizzate
import itertools

# Genera combinazioni
chars = 'abc123'
length = 4

for combo in itertools.product(chars, repeat=length):
    print(''.join(combo))
```

### Regole Hashcat

```bash
# Usa le regole per modificare la wordlist
hashcat -r /usr/share/hashcat/rules/best64.rule wordlist.txt --stdout

# Regole popolari
/usr/share/hashcat/rules/
├── best64.rule         # Le migliori 64 regole secondo il nome della raccolta
├── rockyou-30000.rule  # Basate su RockYou
├── d3ad0ne.rule        # Regole di d3ad0ne
├── dive.rule           # Regole DIVE
└── toggles1-5.rule     # Alterna maiuscole e minuscole
```

---

## ⬇ Download rapido

### Download con comandi di una riga

```bash
# RockYou
wget https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt

# SecLists (raccolta completa)
git clone --depth 1 https://github.com/danielmiessler/SecLists.git

# Password comuni (1M)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt

# Elenco di directory medio
wget https://raw.githubusercontent.com/daviddias/node-dirbuster/master/lists/directory-list-2.3-medium.txt

# Elenco di sottodomini
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/subdomains-top1million-110000.txt

# DNS Jhaddix (bug bounty)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/dns-Jhaddix.txt
```

### Clona i repository

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

## 💡 Consigli e buone pratiche

### Scegliere la wordlist adatta

| Attività | Wordlist consigliata |
|------|---------------------|
| **Scansione rapida delle directory** | common.txt, raft-small-directories.txt |
| **Scansione approfondita delle directory** | directory-list-2.3-medium.txt |
| **Scansione completa delle directory** | directory-list-2.3-big.txt |
| **Enumerazione dei sottodomini** | subdomains-top1million-20000.txt |
| **DNS per bug bounty** | dns-Jhaddix.txt |
| **Cracking delle password** | rockyou.txt |
| **Verifica rapida delle password** | top-10000.txt |

### Combinare le wordlist

```bash
# Combina ed elimina i duplicati
cat list1.txt list2.txt | sort -u > combined.txt

# Aggiungi un prefisso a tutte le righe
sed 's/^/admin_/' wordlist.txt > admin_wordlist.txt

# Aggiungi un suffisso
sed 's/$/.php/' wordlist.txt > php_wordlist.txt

# Entrambi
while read line; do echo "${line}.php"; echo "${line}.html"; done < wordlist.txt > extended.txt
```

### Ottimizzare le wordlist

```bash
# Ordina ed elimina i duplicati
sort -u wordlist.txt -o wordlist.txt

# Rimuovi le righe vuote
sed -i '/^$/d' wordlist.txt

# Rimuovi le righe con meno di 4 caratteri
awk 'length >= 4' wordlist.txt > filtered.txt

# Converti in minuscolo
tr '[:upper:]' '[:lower:]' < wordlist.txt > lowercase.txt

# Conta le righe
wc -l wordlist.txt
```

### Consigli sulle prestazioni

1. **Inizia con elenchi piccoli** - Usa prima elenchi ridotti, poi amplia la ricerca
2. **Usa schemi specifici** - Se conosci il formato, usa schemi mirati
3. **Combina gli strumenti** - Genera elenchi personalizzati e usa anche quelli standard
4. **Elenchi mirati** - Crea elenchi specifici per il bersaglio con CeWL

---

## 📊 Riferimento rapido

### Le migliori wordlist per categoria secondo l'originale

| Categoria | Scelta consigliata | Percorso |
|----------|-------------|----------|
| **Password** | rockyou.txt | SecLists/Passwords/ |
| **Directory** | directory-list-2.3-medium.txt | DirBuster |
| **File** | raft-large-files.txt | SecLists |
| **Sottodomini** | dns-Jhaddix.txt | SecLists/Discovery/DNS/ |
| **Nomi utente** | xato-net-10-million-usernames.txt | SecLists/Usernames/ |
| **SQLi** | Generic-SQLi.txt | SecLists/Fuzzing/SQLi/ |
| **XSS** | XSS-Jhaddix.txt | SecLists/Fuzzing/XSS/ |
| **LFI** | LFI-Jhaddix.txt | SecLists/Fuzzing/LFI/ |

### Guida alle dimensioni

| Dimensione | Righe | Caso d'uso |
|------|-------|----------|
| **Piccola** | < 5K | Test rapidi |
| **Media** | 5K - 100K | Enumerazione standard |
| **Grande** | 100K - 1M | Scansione approfondita |
| **Enorme** | > 1M | Copertura completa |

---

## 📚 Risorse

### Repository di wordlist
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Assetnote Wordlists](https://github.com/assetnote/wordlists)

### Strumenti
- [CeWL](https://github.com/digininja/CeWL)
- [CUPP](https://github.com/Mebus/cupp)
- [Crunch](https://sourceforge.net/projects/crunch-wordlist/)
- [Mentalist](https://github.com/sc0tfree/mentalist)

### Schede correlate
- [Hydra](../../Hydra/translations/README.it.md)
- [John the Ripper](../../John-The-Ripper/translations/README.it.md)
- [Hashcat](../../Hashcat/translations/README.it.md)
- [Gobuster](../../Gobuster/translations/README.it.md)
- [ffuf](../../ffuf/translations/README.it.md)

---

<p align="center">
  <b>📚 La wordlist adatta fa la differenza!</b><br>
  <i>Scegli con criterio e combina in modo efficace</i>
</p>
