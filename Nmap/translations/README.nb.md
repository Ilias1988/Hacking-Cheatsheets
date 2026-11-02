# 🔍 Nmap – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   __                      
   / | / /___ ___  ____ _____  
  /  |/ / __ `__ \/ __ `/ __ \ 
 / /|  / / / / / / /_/ / /_/ / 
/_/ |_/_/ /_/ /_/\__,_/ .___/  
                     /_/       
    Network Mapper – nettverksskanneren
```

<p align="center">
  <img src="https://img.shields.io/badge/Nmap-Network_Scanner-blue?style=for-the-badge" alt="Nmap">
  <img src="https://img.shields.io/badge/Reconnaissance-red?style=for-the-badge" alt="Reconnaissance">
  <img src="https://img.shields.io/badge/Port_Scanning-green?style=for-the-badge" alt="Port Scanning">
  <img src="https://img.shields.io/badge/NSE_Scripts-purple?style=for-the-badge" alt="NSE Scripts">
</p>

<p align="center">
  <b>🌐 Et kraftig verktøy for nettverksskanning og oppdagelse</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Grunnleggende syntaks](#-grunnleggende-syntaks)
- [Angivelse av mål](#-angivelse-av-mål)
- [Oppdagelse av verter](#-oppdagelse-av-verter)
- [Teknikker for portskanning](#-teknikker-for-portskanning)
- [Angivelse av porter](#-angivelse-av-porter)
- [Identifikasjon av tjenester og versjoner](#-identifikasjon-av-tjenester-og-versjoner)
- [Identifikasjon av operativsystem](#-identifikasjon-av-operativsystem)
- [Tidsstyring og ytelse](#-tidsstyring-og-ytelse)
- [NSE-skript](#-nse-skript)
- [Utdataformater](#-utdataformater)
- [Omgåelse av brannmur og IDS](#-omgåelse-av-brannmur-og-ids)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

> **Begreper og presisering:** Kartlegging (enumeration) samler detaljer om tjenester; NSE er Nmaps skriptmotor. «Stealth» og omgåelsesvalg betyr ikke usynlighet eller garantert tilgang. TCP Connect fungerer heller ikke gjennom enhver filtrering. Oppdagelsesvalg som `-PR` velger metode, mens `-sn` utelater portskanning; `-Pn` kan fortsatt innebære lokal ARP. Se [vertsoppdagelse](https://nmap.org/book/man-host-discovery.html) og [skanneteknikker](https://nmap.org/book/man-port-scanning-techniques.html). Kategoriene `default` og `safe` er ikke garantier for at et skript passer ethvert miljø; se [NSE-kategoriene](https://nmap.org/book/nse-usage.html). Eksemplene er bevart.

**Nmap** (Network Mapper) er et gratis verktøy med åpen kildekode for nettverksoppdagelse og sikkerhetsrevisjon. Det er en etablert standard for nettverksskanning.

### Viktige egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **Oppdagelse av verter** | Finn aktive verter i et nettverk |
| **Portskanning** | Identifiser åpne porter og tjenester |
| **Versjonsidentifikasjon** | Identifiser tjenesteversjoner |
| **Identifikasjon av operativsystem** | Identifiser operativsystemer |
| **NSE-skript** | Sårbarhetsskanning og utnyttelse |
| **Utdataformater** | XML, grep-vennlig og vanlig utdata |

### Porttilstander

| Tilstand | Beskrivelse |
|-------|-------------|
| `open` | En applikasjon tar imot tilkoblinger |
| `closed` | Porten er tilgjengelig, men ingen applikasjon lytter |
| `filtered` | Nmap kan ikke avgjøre om porten er åpen (brannmur) |
| `unfiltered` | Porten er tilgjengelig, men tilstanden er ukjent |
| `open|filtered` | Kan ikke avgjøre om porten er åpen eller filtrert |
| `closed|filtered` | Kan ikke avgjøre om porten er lukket eller filtrert |

---

## 📥 Installasjon

### Kali Linux (forhåndsinstallert)
```bash
# Oppdater Nmap
sudo apt update && sudo apt install nmap
```

### Ubuntu/Debian
```bash
sudo apt install nmap
```

### CentOS/RHEL
```bash
sudo yum install nmap
```

### Windows
```bash
# Last ned fra https://nmap.org/download.html
# Eller bruk Chocolatey
choco install nmap
```

### macOS
```bash
brew install nmap
```

### Kontroller installasjonen
```bash
nmap --version
nmap -h          # Hjelp
```

---

<a id="-grunnleggende-syntaks"></a>
## ⌨️ Grunnleggende syntaks

### Generell syntaks
```bash
nmap [Scan Type] [Options] {target}
```

### Eksempler for å komme raskt i gang
```bash
# Grunnleggende skanning
nmap 192.168.1.1

# Skann flere verter
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Skann et adresseområde
nmap 192.168.1.1-254

# Skann hele delnettet
nmap 192.168.1.0/24

# Skann mål fra en fil
nmap -iL targets.txt
```

---

## 🎯 Angivelse av mål

### Enkeltmål
```bash
# IP-adresse
nmap 192.168.1.1

# Vertsnavn
nmap scanme.nmap.org

# Domene
nmap example.com
```

### Flere mål
```bash
# Atskilt med mellomrom
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Område
nmap 192.168.1.1-100

# CIDR-notasjon
nmap 192.168.1.0/24

# Jokertegn
nmap 192.168.1.*

# Område for en oktett
nmap 192.168.0-255.1-254

# Blandet
nmap 192.168.1.0/24 10.0.0.1
```

### Inndata fra fil
```bash
# Les fra fil
nmap -iL targets.txt

# targets.txt:
# 192.168.1.1
# 192.168.1.0/24
# scanme.nmap.org
```

### Utelat mål
```bash
# Utelat én vert
nmap 192.168.1.0/24 --exclude 192.168.1.1

# Utelat mål fra fil
nmap 192.168.1.0/24 --excludefile exclude.txt
```

### Tilfeldige mål
```bash
# Skann tilfeldige verter (til forskning)
nmap -iR 100
```

---

## 🔎 Oppdagelse av verter

### Valg for oppdagelse

| Valg | Beskrivelse |
|--------|-------------|
| `-sL` | Listeskanning – list bare målene |
| `-sn` | Ping-skanning – ingen portskanning |
| `-Pn` | Hopp over vertsoppdagelse (behandle alle verter som aktive) |
| `-PS` | TCP SYN-ping |
| `-PA` | TCP ACK-ping |
| `-PU` | UDP-ping |
| `-PE` | ICMP echo-ping |
| `-PP` | ICMP timestamp-ping |
| `-PM` | ICMP netmask-ping |
| `-PO` | IP-protokollping |
| `-PR` | ARP-ping (lokalnett) |

### Ping-skanning (uten portskanning)
```bash
# Finn bare aktive verter
nmap -sn 192.168.1.0/24

# Ping-skanning med vertsliste
nmap -sn -iL hosts.txt
```

### Hopp over vertsoppdagelse
```bash
# Skann alle verter (uten ping først)
nmap -Pn 192.168.1.1

# Nyttig når:
# - Verter blokkerer ICMP
# - Skanningen går gjennom en brannmur
```

### TCP SYN-/ACK-ping
```bash
# TCP SYN-ping på port 80
nmap -PS80 192.168.1.1

# TCP ACK-ping på port 80
nmap -PA80 192.168.1.1

# Flere porter
nmap -PS22,80,443 192.168.1.1
```

### UDP-ping
```bash
# UDP-ping
nmap -PU53 192.168.1.1

# Flere porter
nmap -PU53,161 192.168.1.1
```

### ICMP-ping
```bash
# ICMP echo
nmap -PE 192.168.1.1

# ICMP-tidsstempel
nmap -PP 192.168.1.1

# ICMP-nettmaske
nmap -PM 192.168.1.1
```

### ARP-ping (lokalnett)
```bash
# ARP-skanning (raskest på lokalnettet)
nmap -PR 192.168.1.0/24

# Bare ARP-skanning
nmap -sn -PR 192.168.1.0/24
```

### Deaktiver DNS-oppslag
```bash
# Ingen DNS-oppslag (raskere)
nmap -n 192.168.1.1

# Utfør alltid DNS-oppslag
nmap -R 192.168.1.1
```

---

## 🔌 Teknikker for portskanning

### Oversikt over skannetyper

| Valg | Navn | Beskrivelse |
|--------|------|-------------|
| `-sS` | SYN-skanning | Stealth-skanning (standard, krever root) |
| `-sT` | TCP-tilkobling | Full TCP-tilkobling (krever ikke root) |
| `-sU` | UDP-skanning | Skann UDP-porter |
| `-sA` | ACK-skanning | Kartlegg brannmurregler |
| `-sW` | Window-skanning | Ligner ACK, bruker vindusfeltet |
| `-sM` | Maimon-skanning | FIN-/ACK-probe |
| `-sN` | NULL-skanning | Ingen flagg satt |
| `-sF` | FIN-skanning | FIN-flagget er satt |
| `-sX` | Xmas-skanning | FIN-, PSH- og URG-flagg |

### SYN stealth-skanning (standard)
```bash
# Den vanligste skanningen (krever root)
sudo nmap -sS 192.168.1.1

# Fullfører ikke TCP-håndtrykket
# Rask og omtalt som stealth i originalen
```

### TCP Connect-skanning
```bash
# Full TCP-tilkobling
nmap -sT 192.168.1.1

# Krever ikke root
# Lettere å oppdage, men omtalt som alltid fungerende i originalen
```

### UDP-skanning
```bash
# Skann UDP-porter
sudo nmap -sU 192.168.1.1

# UDP-skanning er LANGSOM
# Kombiner med versjonsidentifikasjon
sudo nmap -sU -sV 192.168.1.1

# Vanlige UDP-porter
sudo nmap -sU -p 53,67,68,69,123,161,500 192.168.1.1
```

### Kombinert TCP/UDP
```bash
# Skann både TCP og UDP
sudo nmap -sS -sU 192.168.1.1

# Bestemte porter
sudo nmap -sS -sU -p T:80,443,U:53,161 192.168.1.1
```

### NULL-, FIN- og Xmas-skanning
```bash
# NULL-skanning (ingen flagg)
sudo nmap -sN 192.168.1.1

# FIN-skanning (bare FIN-flagget)
sudo nmap -sF 192.168.1.1

# Xmas-skanning (FIN, PSH, URG)
sudo nmap -sX 192.168.1.1

# Egnet til:
# - Omgåelse av tilstandsløse brannmurer
# - Fungerer ikke på Windows, ifølge originalen
```

### ACK-skanning
```bash
# Kartlegg brannmurregler
sudo nmap -sA 192.168.1.1

# Undersøk:
# - Hvilke porter som er filtrert
# - Brannmurregler
# Returnerer: unfiltered eller filtered
```

### Window-skanning
```bash
# Ligner ACK-skanning
sudo nmap -sW 192.168.1.1

# Bruker TCP-vindusstørrelsen til å vurdere tilstanden
```

### Idle-/zombieskanning
```bash
# Bruk en zombievert (omtalt som svært skjult i originalen)
sudo nmap -sI zombie_host 192.168.1.1

# Din IP-adresse kontakter ikke målet, ifølge originalen
# Krever en egnet zombievert
```

---

<a id="-angivelse-av-porter"></a>
## 🎚️ Angivelse av porter

### Portvalg

| Valg | Beskrivelse |
|--------|-------------|
| `-p <port>` | Skann bestemte porter |
| `-p-` | Skann alle 65535 porter |
| `-p 1-1000` | Skann et portområde |
| `--top-ports <n>` | Skann de N vanligste portene |
| `-F` | Rask skanning (de 100 vanligste portene) |
| `-r` | Skann porter i stigende rekkefølge |

### Bestemte porter
```bash
# Én port
nmap -p 80 192.168.1.1

# Flere porter
nmap -p 80,443,22 192.168.1.1

# Portområde
nmap -p 1-1000 192.168.1.1

# Alle porter
nmap -p- 192.168.1.1

# Blandet
nmap -p 22,80,443,1000-2000 192.168.1.1
```

### Porter etter protokoll
```bash
# TCP-porter
nmap -p T:80,443 192.168.1.1

# UDP-porter
nmap -p U:53,161 192.168.1.1

# Begge
nmap -p T:80,443,U:53 192.168.1.1
```

### De vanligste portene
```bash
# De 100 vanligste portene (raskt)
nmap -F 192.168.1.1

# De 10 vanligste portene
nmap --top-ports 10 192.168.1.1

# De 1000 vanligste portene (standard)
nmap --top-ports 1000 192.168.1.1
```

### Oversikt over vanlige porter

| Port | Tjeneste |
|------|---------|
| 21 | FTP |
| 22 | SSH |
| 23 | Telnet |
| 25 | SMTP |
| 53 | DNS |
| 80 | HTTP |
| 110 | POP3 |
| 135 | MSRPC |
| 139 | NetBIOS |
| 143 | IMAP |
| 443 | HTTPS |
| 445 | SMB |
| 993 | IMAPS |
| 995 | POP3S |
| 1433 | MSSQL |
| 1521 | Oracle |
| 3306 | MySQL |
| 3389 | RDP |
| 5432 | PostgreSQL |
| 5900 | VNC |
| 8080 | HTTP-Proxy |

---

## 🔬 Identifikasjon av tjenester og versjoner

### Valg for versjonsidentifikasjon

| Valg | Beskrivelse |
|--------|-------------|
| `-sV` | Versjonsidentifikasjon |
| `--version-intensity <0-9>` | Intensitet for sondering |
| `--version-light` | Lett versjonsskanning (intensitet 2) |
| `--version-all` | Prøv alle prober (intensitet 9) |
| `--version-trace` | Feilsøk versjonsskanning |

### Grunnleggende versjonsidentifikasjon
```bash
# Identifiser tjenesteversjoner
nmap -sV 192.168.1.1

# Vanlig utdata:
# PORT    STATE SERVICE VERSION
# 22/tcp  open  ssh     OpenSSH 7.9
# 80/tcp  open  http    Apache httpd 2.4.38
```

### Intensitet for versjonsskanning
```bash
# Lett skanning (raskere)
nmap -sV --version-light 192.168.1.1

# Alle prober (grundig)
nmap -sV --version-all 192.168.1.1

# Egendefinert intensitet (0-9)
nmap -sV --version-intensity 5 192.168.1.1
```

---

## 💻 Identifikasjon av operativsystem

### Valg for identifikasjon av operativsystem

| Valg | Beskrivelse |
|--------|-------------|
| `-O` | Aktiver identifikasjon av operativsystem |
| `--osscan-limit` | Skann bare verter som egner seg |
| `--osscan-guess` | Gjett operativsystem mer aggressivt |
| `--max-os-tries` | Maksimalt antall forsøk på OS-identifikasjon |

### Grunnleggende OS-identifikasjon
```bash
# Identifiser operativsystem (krever root)
sudo nmap -O 192.168.1.1

# Eksempel på utdata:
# Running: Linux 3.X|4.X
# OS CPE: cpe:/o:linux:linux_kernel:3
# OS details: Linux 3.10 - 4.11
```

### Aggressiv gjetting av operativsystem
```bash
# Mer aggressiv gjetting
sudo nmap -O --osscan-guess 192.168.1.1
```

### Aggressiv skanning (-A)
```bash
# Aktiver OS-identifikasjon, versjonsidentifikasjon, skript og traceroute
sudo nmap -A 192.168.1.1

# Tilsvarer: -O -sV -sC --traceroute
```

---

<a id="-tidsstyring-og-ytelse"></a>
## ⏱️ Tidsstyring og ytelse

### Forhåndsvalg for tidsstyring

| Valg | Navn | Beskrivelse |
|--------|------|-------------|
| `-T0` | Paranoid | Omgåelse av IDS, svært langsomt |
| `-T1` | Sneaky | Omgåelse av IDS, langsomt |
| `-T2` | Polite | Mindre båndbredde, langsommere |
| `-T3` | Normal | Standard tidsstyring |
| `-T4` | Aggressive | Raskt på et pålitelig nettverk |
| `-T5` | Insane | Svært raskt, kan overse porter |

### Bruk
```bash
# Langsomt og omtalt som stealth i originalen
nmap -T1 192.168.1.1

# Normalt (standard)
nmap -T3 192.168.1.1

# Raskt
nmap -T4 192.168.1.1

# Svært raskt (lokalnett)
nmap -T5 192.168.1.1
```

### Detaljert tidsstyring

| Valg | Beskrivelse |
|--------|-------------|
| `--min-rate <n>` | Minste antall pakker per sekund |
| `--max-rate <n>` | Største antall pakker per sekund |
| `--min-parallelism <n>` | Minste antall parallelle prober |
| `--max-parallelism <n>` | Største antall parallelle prober |
| `--min-hostgroup <n>` | Minste antall verter samtidig |
| `--max-hostgroup <n>` | Største antall verter samtidig |
| `--host-timeout <time>` | Gi opp verten etter angitt tid |
| `--scan-delay <time>` | Forsinkelse mellom prober |
| `--max-retries <n>` | Maksimalt antall nye forsøk per probe |

```bash
# Send minst 1000 pakker/sek
nmap --min-rate 1000 192.168.1.0/24

# Maksimalt 100 pakker/sek (senk farten)
nmap --max-rate 100 192.168.1.1

# Avbryt testing av verten etter 30 minutter
nmap --host-timeout 30m 192.168.1.0/24

# Forsinkelse mellom prober
nmap --scan-delay 1s 192.168.1.1
```

---

## 📜 NSE-skript

### Skriptkategorier

| Kategori | Beskrivelse |
|----------|-------------|
| `auth` | Omgåelse av autentisering |
| `broadcast` | Oppdag verter via kringkasting |
| `brute` | Brute force-angrep |
| `default` | Standardskript (-sC) |
| `discovery` | Oppdagelse av tjenester |
| `dos` | Tjenestenekt |
| `exploit` | Utnytt sårbarheter |
| `external` | Spør eksterne tjenester |
| `fuzzer` | Fuzzing |
| `intrusive` | Kan krasje tjenester |
| `malware` | Deteksjon av skadevare |
| `safe` | Skript i kategorien safe |
| `version` | Versjonsidentifikasjon |
| `vuln` | Deteksjon av sårbarheter |

### Skriptvalg

| Valg | Beskrivelse |
|--------|-------------|
| `-sC` | Standardskript |
| `--script <scripts>` | Kjør bestemte skript |
| `--script-args <args>` | Skriptargumenter |
| `--script-updatedb` | Oppdater skriptdatabasen |
| `--script-help <script>` | Skripthjelp |

### Standardskript
```bash
# Kjør standardskript
nmap -sC 192.168.1.1

# Med versjonsidentifikasjon (anbefalt)
nmap -sV -sC 192.168.1.1
```

### Kjør bestemte skript
```bash
# Ett skript
nmap --script http-title 192.168.1.1

# Flere skript
nmap --script http-title,http-headers 192.168.1.1

# Kategori
nmap --script vuln 192.168.1.1

# Jokertegn
nmap --script "http-*" 192.168.1.1

# Kombiner kategorier
nmap --script "vuln and safe" 192.168.1.1

# Utelat skript
nmap --script "not intrusive" 192.168.1.1
```

### Vanlige skript

#### Sårbarhetsskanning
```bash
# Alle sårbarhetsskript
nmap --script vuln 192.168.1.1

# Kontroll for en bestemt CVE
nmap --script smb-vuln-ms17-010 192.168.1.1

# Heartbleed
nmap --script ssl-heartbleed 192.168.1.1

# ShellShock
nmap --script http-shellshock 192.168.1.1
```

#### HTTP-skript
```bash
# HTTP-kartlegging
nmap --script http-enum 192.168.1.1

# HTTP-headere
nmap --script http-headers 192.168.1.1

# HTTP-metoder
nmap --script http-methods 192.168.1.1

# HTTP-tittel
nmap --script http-title 192.168.1.1

# Robots.txt
nmap --script http-robots.txt 192.168.1.1
```

#### SMB-skript
```bash
# SMB-kartlegging
nmap --script smb-enum-shares 192.168.1.1
nmap --script smb-enum-users 192.168.1.1
nmap --script smb-os-discovery 192.168.1.1

# SMB-sårbarheter
nmap --script smb-vuln* 192.168.1.1
```

#### SSH-skript
```bash
# Autentiseringsmetoder for SSH
nmap --script ssh-auth-methods 192.168.1.1

# SSH-vertsnøkkel
nmap --script ssh-hostkey 192.168.1.1

# Brute force mot SSH
nmap --script ssh-brute 192.168.1.1
```

#### DNS-skript
```bash
# Brute force mot DNS
nmap --script dns-brute example.com

# DNS-soneoverføring
nmap --script dns-zone-transfer example.com
```

#### MySQL-/databaseskript
```bash
# MySQL-opplysninger
nmap --script mysql-info 192.168.1.1

# MySQL-kartlegging
nmap --script mysql-enum 192.168.1.1

# Brute force mot MySQL
nmap --script mysql-brute 192.168.1.1
```

### Skriptargumenter
```bash
# Brute force med ordliste
nmap --script ssh-brute --script-args userdb=users.txt,passdb=passwords.txt 192.168.1.1

# Brute force mot HTTP-autentisering
nmap --script http-brute --script-args http-brute.path=/admin 192.168.1.1
```

---

## 📄 Utdataformater

### Utdatavalg

| Valg | Beskrivelse |
|--------|-------------|
| `-oN <file>` | Vanlig utdata |
| `-oX <file>` | XML-utdata |
| `-oG <file>` | Grep-vennlig utdata |
| `-oA <basename>` | Alle formater |
| `-oS <file>` | Script kiddie-utdata |

### Lagre utdata
```bash
# Vanlig utdata
nmap -oN scan.txt 192.168.1.1

# XML-utdata (for verktøy)
nmap -oX scan.xml 192.168.1.1

# Grep-vennlig utdata
nmap -oG scan.gnmap 192.168.1.1

# Alle formater samtidig
nmap -oA scan 192.168.1.1
# Oppretter: scan.nmap, scan.xml, scan.gnmap
```

### Detaljnivå og feilsøking

| Valg | Beskrivelse |
|--------|-------------|
| `-v` | Øk detaljnivået |
| `-vv` | Mer detaljert |
| `-d` | Feilsøkingsmodus |
| `-dd` | Mer feilsøking |
| `--reason` | Vis begrunnelse for porttilstanden |
| `--open` | Vis bare åpne porter |
| `--packet-trace` | Vis alle pakker |

```bash
# Detaljert utdata
nmap -v 192.168.1.1

# Svært detaljert
nmap -vv 192.168.1.1

# Vis bare åpne porter
nmap --open 192.168.1.1

# Vis begrunnelse
nmap --reason 192.168.1.1
```

---

<a id="-omgåelse-av-brannmur-og-ids"></a>
## 🛡️ Omgåelse av brannmur og IDS

### Omgåelsesteknikker

| Valg | Beskrivelse |
|--------|-------------|
| `-f` | Fragmenter pakker |
| `--mtu <size>` | Egendefinert MTU-størrelse |
| `-D <decoys>` | Bruk lokkeverter (decoys) |
| `-S <IP>` | Forfalsk kilde-IP |
| `--source-port <port>` | Forfalsk kildeport |
| `--data-length <n>` | Legg til tilfeldige data |
| `--randomize-hosts` | Tilfeldig rekkefølge på verter |
| `--spoof-mac <mac>` | Forfalsk MAC-adresse |
| `--badsum` | Send ugyldige kontrollsummer |

### Pakkefragmentering
```bash
# Fragmenter pakker
nmap -f 192.168.1.1

# Egendefinert MTU
nmap --mtu 16 192.168.1.1
```

### Lokkeverter (decoys)
```bash
# Bruk lokkeverter (decoys)
nmap -D decoy1,decoy2,ME 192.168.1.1

# Tilfeldige lokkeverter
nmap -D RND:5 192.168.1.1
```

### Forfalskning av kilde-IP og kildeport
```bash
# Forfalsk kilde-IP
nmap -S 192.168.1.100 -e eth0 192.168.1.1

# Forfalsk kildeport (porter som ofte tillates)
nmap --source-port 53 192.168.1.1
nmap --source-port 80 192.168.1.1
```

### Forfalskning av MAC-adresse
```bash
# Tilfeldig MAC-adresse
nmap --spoof-mac 0 192.168.1.1

# Bestemt leverandør
nmap --spoof-mac Apple 192.168.1.1

# Bestemt MAC-adresse
nmap --spoof-mac 00:11:22:33:44:55 192.168.1.1
```

### Utfylling med data
```bash
# Legg til tilfeldige data i pakkene
nmap --data-length 25 192.168.1.1
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: Rask gjennomgang av nettverket
```bash
# Finn aktive verter
nmap -sn 192.168.1.0/24
```

### Eksempel 2: Grunnleggende portskanning
```bash
# Skann med versjonsidentifikasjon
nmap -sV 192.168.1.1
```

### Eksempel 3: Omfattende skanning
```bash
# Full skanning med OS-identifikasjon og skript
sudo nmap -sS -sV -O -sC 192.168.1.1
```

### Eksempel 4: Aggressiv fullskanning
```bash
# Alle porter, aggressivt
sudo nmap -A -T4 -p- 192.168.1.1
```

### Eksempel 5: Sårbarhetsskanning
```bash
# Se etter sårbarheter
nmap --script vuln 192.168.1.1
```

### Eksempel 6: Stealth-skanning
```bash
# Stealth-skanning
sudo nmap -sS -T2 -f --data-length 25 192.168.1.1
```

### Eksempel 7: Skanning av webserver
```bash
# Omfattende webskanning
nmap -sV -p 80,443,8080,8443 --script "http-*" 192.168.1.1
```

### Eksempel 8: SMB-kartlegging
```bash
# Full SMB-kartlegging
nmap -p 445 --script smb-enum-shares,smb-enum-users,smb-vuln* 192.168.1.1
```

### Eksempel 9: Skanning av hele delnettet
```bash
# Skann hele delnettet
nmap -sS -sV -O -T4 --open 192.168.1.0/24 -oA subnet_scan
```

### Eksempel 10: UDP-tjenester
```bash
# Vanlige UDP-tjenester
sudo nmap -sU -sV -p 53,67,68,69,123,161,500 192.168.1.1
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `nmap <target>` | Grunnleggende skanning |
| `nmap -sn <target>` | Bare ping-skanning |
| `nmap -Pn <target>` | Hopp over vertsoppdagelse |
| `nmap -sS <target>` | SYN stealth-skanning |
| `nmap -sV <target>` | Versjonsidentifikasjon |
| `nmap -O <target>` | OS-identifikasjon |
| `nmap -A <target>` | Aggressiv skanning |
| `nmap -sC <target>` | Standardskript |
| `nmap -p- <target>` | Alle porter |
| `nmap --script vuln <target>` | Sårbarhetsskanning |

### Hastighet sammenlignet med synlighet

| Behov | Valg |
|------|---------|
| **Rask** | `-T4 --min-rate 1000` |
| **Skjult** | `-T1 -f --data-length 25` |
| **Balansert** | `-T3` (standard) |

### Vanlige skanninger

```bash
# Rask skanning
nmap -F 192.168.1.1

# Full TCP-skanning
nmap -sT -p- 192.168.1.1

# Tjenesteskanning
nmap -sV 192.168.1.1

# Sårbarhetsskanning
nmap --script vuln 192.168.1.1

# Alt
sudo nmap -A -T4 -p- 192.168.1.1
```

---

## 💡 Tips og anbefalte arbeidsmåter

### Ytelsestips

1. **Bruk SYN-skanning (standard)**
   ```bash
   # Krever root, men er raskest ifølge originalen
   sudo nmap -sS 192.168.1.1
   ```

2. **Begrens portområdet i starten**
   ```bash
   # Start med de vanligste portene
   nmap --top-ports 100 192.168.1.1
   ```

3. **Øk hastigheten på lokalnett**
   ```bash
   nmap -T5 --min-rate 5000 192.168.1.0/24
   ```

### Tips for bedre nøyaktighet

1. **Bruk flere skannetyper**
   ```bash
   # TCP og UDP
   sudo nmap -sS -sU -sV 192.168.1.1
   ```

2. **Øk antallet nye forsøk på ustabile nettverk**
   ```bash
   nmap --max-retries 3 192.168.1.1
   ```

### Juridiske hensyn

> ⚠️ **VIKTIG:** Skann bare nettverk du eier eller har tillatelse til å skanne.

- ✅ Egne nettverk
- ✅ Labmiljøer
- ✅ Autoriserte penetrasjonstester
- ❌ Tilfeldige verter på Internett
- ❌ Nettverk uten tillatelse

---

## ⚠️ Juridisk ansvarsfraskrivelse

> **ADVARSEL:** Nmap er et kraftig verktøy som bare skal brukes til **autorisert skanning**.
> 
> - ✅ Skann nettverk du eier
> - ✅ Skann med uttrykkelig skriftlig tillatelse
> - ✅ Bruk i autoriserte penetrasjonstester
> - ❌ Skann aldri nettverk uten tillatelse
> - ❌ Uautorisert skanning kan være ulovlig
> 
> **Uautorisert nettverksskanning er ulovlig i mange jurisdiksjoner.**

---

## 📚 Ressurser

### Offisielle ressurser
- [Nmaps offisielle nettsted](https://nmap.org/)
- [Nmaps referanseveiledning](https://nmap.org/book/man.html)
- [Dokumentasjon for NSE-skript](https://nmap.org/nsedoc/)

### Læringsressurser
- [Boken Nmap Network Scanning](https://nmap.org/book/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Relaterte hurtigreferanser
- [Metasploit Framework](../../Metasploit/translations/README.nb.md)
- [SQLMap](../../SQLMap/translations/README.nb.md)
- [Burp Suite](../../Burp-Suite/translations/README.nb.md)

---

<p align="center">
  <b>🔍 Lær nettverksrekognosering!</b><br>
  <i>Bli kjent med nettverket før angriperne gjør det</i>
</p>
