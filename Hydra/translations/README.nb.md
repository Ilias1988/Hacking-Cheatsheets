# 🔓 Hydra – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _           
 | | | |_   _  __| |_ __ __ _ 
 | |_| | | | |/ _` | '__/ _` |
 |  _  | |_| | (_| | | | (_| |
 |_| |_|\__, |\__,_|_|  \__,_|
        |___/                  
    Rask testing av nettverkspålogging
```

<p align="center">
  <img src="https://img.shields.io/badge/Hydra-Password_Cracker-red?style=for-the-badge" alt="Hydra">
  <img src="https://img.shields.io/badge/Brute-Force-orange?style=for-the-badge" alt="Brute force">
  <img src="https://img.shields.io/badge/50+-Protocols-blue?style=for-the-badge" alt="Protokoller">
  <img src="https://img.shields.io/badge/Network-Login-green?style=for-the-badge" alt="Nettverk">
</p>

<p align="center">
  <b>🔑 Et kjent verktøy for parallell testing av nettverkspålogging</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Grunnleggende syntaks](#-grunnleggende-syntaks)
- [Støttede protokoller](#-støttede-protokoller)
- [Angrep mot SSH](#-angrep-mot-ssh)
- [Angrep mot FTP](#-angrep-mot-ftp)
- [Angrep mot HTTP-skjemaer](#-angrep-mot-http-skjemaer)
- [Angrep mot databaser](#-angrep-mot-databaser)
- [Angrep mot eksterne skrivebord](#-angrep-mot-eksterne-skrivebord)
- [Andre tjenester](#-andre-tjenester)
- [Ordlister og passord](#-ordlister-og-passord)
- [Avanserte alternativer](#-avanserte-alternativer)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

**Hydra** (THC-Hydra) er et raskt og fleksibelt verktøy for passordtesting mot nettverkstjenester. Det støtter mange protokoller og bruker parallelle forbindelser for høyere hastighet.

> **Begreper og versjoner:** Online-testing prøver pålogging mot en tjeneste; offline-knekking prøver kandidater mot innhentede hasher. Brute force prøver kombinasjoner, passordspraying bruker ett passord mot flere kontoer, og credential stuffing prøver kjente par. Protokollstøtte og byggavhengigheter varierer. Tabellenes standardverdier er fra originalen.

### Viktige egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **50+ protokoller** | SSH, FTP, HTTP, RDP, SMB og flere |
| **Parallell kjøring** | Flere samtidige forbindelser |
| **Fleksibelt** | Egne headere, informasjonskapsler og proxy |
| **Gjenopptakelse** | Lagre og gjenoppta testøkter |
| **Flere plattformer** | Linux, Windows, macOS |

### Angrepstyper

| Type | Beskrivelse |
|------|-------------|
| **Ordlisteangrep** | Prøv passord fra en ordliste |
| **Brute force** | Generer alle kombinasjoner |
| **Passordspraying** | Ett passord mot flere brukere |
| **Credential stuffing** | Par av brukernavn og passord |

---

## 📥 Installasjon

### Kali Linux (forhåndsinstallert)
```bash
hydra -h
```

### Ubuntu/Debian
```bash
sudo apt install hydra
```

### Fra kildekode
```bash
git clone https://github.com/vanhauser-thc/thc-hydra
cd thc-hydra
./configure
make
sudo make install
```

### macOS (Homebrew)
```bash
brew install hydra
```

### Kontrollere installasjonen

> **Presisering:** `-V` viser hvert påloggings-/passordforsøk, ikke versjonen. `-t` styrer parallelle forbindelser, ikke totalt antall forsøk, og hindrer ikke kontosperring. `-w` venter på svar; `-W` gjelder mellom forbindelser per oppgave. `-T` er samlet parallellitet ved flere mål, ikke timeout. `-R` gjenopptar en tidligere økt, ikke oppretter en ny lagringsøkt. Originaleksemplene er bevart; se [Hydras egen hjelpetekst i kildekoden](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c).
```bash
hydra -h
hydra -V
```

---

<a id="-grunnleggende-syntaks"></a>
## ⌨️ Grunnleggende syntaks

### Generell syntaks
```bash
hydra [options] target service
```

### Vanlige alternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `-l` | Ett brukernavn |
| `-L` | Liste over brukernavn |
| `-p` | Ett passord |
| `-P` | Passordliste |
| `-C` | Kolonseparert fil (user:pass) |
| `-t` | Parallelle oppgaver (standard i originalen: 16) |
| `-s` | Portnummer |
| `-o` | Utdatafil |
| `-f` | Stopp ved første gyldige par |
| `-V` | Detaljerte utdata (se presiseringen) |
| `-vV` | Svært detaljerte utdata |
| `-d` | Feilsøkingsmodus |

### Kom raskt i gang
```bash
# Én bruker, passordliste
hydra -l admin -P passwords.txt target ssh

# Brukerliste, ett passord
hydra -L users.txt -p password123 target ssh

# Begge listene
hydra -L users.txt -P passwords.txt target ssh
```

---

## 📡 Støttede protokoller

### Fullstendig protokolliste

```
adam6500    afp         asterisk    cisco       cisco-enable
cvs         firebird    ftp         ftps        http-form-get
http-form-post          http-get    http-head   http-post
http-proxy  http-proxy-urlenum      https-form-get
https-form-post         https-get   https-head  https-post
imap        imaps       irc         ldap2       ldap2s
ldap3       ldap3-crammd5           ldap3-digestmd5
ldap3s      ldap3s-crammd5          ldap3s-digestmd5
memcached   mongodb     mssql       mysql       nntp
oracle      oracle-listener         oracle-sid  pcanywhere
pcnfs       pop3        pop3s       postgres    radmin2
rdp         redis       rexec       rlogin      rpcap
rsh         rtsp        s7-300      sapr3       sip
smb         smtp        smtp-enum   smtps       snmp
socks5      ssh         sshkey      svn         teamspeak
telnet      vmauthd     vnc         xmpp
```

---

## 🔐 Angrep mot SSH

### Grunnleggende SSH-angrep
```bash
# Én bruker
hydra -l root -P /usr/share/wordlists/rockyou.txt ssh://192.168.1.1

# Flere brukere
hydra -L users.txt -P passwords.txt ssh://192.168.1.1

# Bestemt port
hydra -l admin -P passwords.txt -s 2222 192.168.1.1 ssh
```

### SSH med alternativer
```bash
# Begrens forsøk (forenkling; se presiseringen)
hydra -l admin -P passwords.txt -t 4 192.168.1.1 ssh

# Stopp ved første vellykkede pålogging
hydra -l admin -P passwords.txt -f 192.168.1.1 ssh

# Detaljerte utdata
hydra -l admin -P passwords.txt -V 192.168.1.1 ssh
```

### Autentisering med SSH-nøkkel

> **Rettelse:** `sshkey` leser kandidater som filnavn på private nøkler. Den viste `-x`-linjen er derfor ikke i seg selv en generell kontroll av svake nøkler eller nøkkelpassfraser. Se [modulens kildekode](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra-sshkey.c).
```bash
# Bruk privat nøkkel (kontroll av svake nøkler; se presiseringen)
hydra -l root -x 6:8:a 192.168.1.1 sshkey
```

---

## 📂 Angrep mot FTP

### Grunnleggende FTP-angrep
```bash
# Brute force mot FTP
hydra -l admin -P passwords.txt ftp://192.168.1.1

# Kontroller anonym FTP
hydra -l anonymous -p anonymous ftp://192.168.1.1

# Flere brukere
hydra -L users.txt -P passwords.txt ftp://192.168.1.1
```

### FTPS (FTP over SSL)
```bash
hydra -l admin -P passwords.txt ftps://192.168.1.1
```

---

## 🌐 Angrep mot HTTP-skjemaer

### HTTP-GET-FORM

```bash
hydra -l admin -P passwords.txt target http-get-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid"

# Format:
# "/path:params:failure_string"
```

### HTTP-POST-FORM (vanligst)

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid credentials"
```

### Syntaks for skjemaangrep

```
"/page:POST_params:Failure_string"

Her betyr:
- ^USER^ = plassholder for brukernavn
- ^PASS^ = plassholder for passord
- Failure_string = tekst som vises ved mislykket pålogging
```

### Deteksjon basert på vellykket pålogging

```bash
# Bruk S= for en suksessmelding i stedet for en feilmelding
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:S=Welcome"
```

### Med informasjonskapsler

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:Invalid:H=Cookie: PHPSESSID=abc123"
```

### HTTP Basic-autentisering

```bash
# HTTP Basic-autentisering
hydra -l admin -P passwords.txt http-get://192.168.1.1/admin/

# HTTPS
hydra -l admin -P passwords.txt https-get://192.168.1.1/admin/
```

### HTTP-proxy

```bash
hydra -l admin -P passwords.txt http-proxy://proxy.server:3128
```

---

<a id="-angrep-mot-databaser"></a>
## 🗄️ Angrep mot databaser

### MySQL

```bash
hydra -l root -P passwords.txt mysql://192.168.1.1

# Bestemt port
hydra -l root -P passwords.txt -s 3307 192.168.1.1 mysql
```

### PostgreSQL

```bash
hydra -l postgres -P passwords.txt postgres://192.168.1.1
```

### Microsoft SQL Server

```bash
hydra -l sa -P passwords.txt mssql://192.168.1.1
```

### Oracle

```bash
# Angrep mot Oracle SID
hydra -l SYS -P passwords.txt oracle://192.168.1.1/SID

# Oracle-lytter
hydra -l admin -P passwords.txt oracle-listener://192.168.1.1
```

### MongoDB

```bash
hydra -l admin -P passwords.txt mongodb://192.168.1.1
```

### Redis

```bash
hydra -P passwords.txt redis://192.168.1.1
```

---

<a id="-angrep-mot-eksterne-skrivebord"></a>
## 🖥️ Angrep mot eksterne skrivebord

### RDP (Windows Remote Desktop)

```bash
hydra -l administrator -P passwords.txt rdp://192.168.1.1

# Begrens parallelle oppgaver (RDP er langsomt)
hydra -l administrator -P passwords.txt -t 1 rdp://192.168.1.1
```

### VNC

```bash
# VNC (bare passord)
hydra -P passwords.txt vnc://192.168.1.1

# VNC på en bestemt port
hydra -P passwords.txt -s 5901 192.168.1.1 vnc
```

### Telnet

```bash
hydra -l admin -P passwords.txt telnet://192.168.1.1
```

---

## 🔧 Andre tjenester

### SMB (Windows-delinger)

```bash
hydra -l administrator -P passwords.txt smb://192.168.1.1
```

### SMTP

```bash
# SMTP-autentisering
hydra -l user@domain.com -P passwords.txt smtp://192.168.1.1

# Kartlegging via SMTP
hydra -L users.txt smtp-enum://192.168.1.1
```

### POP3/IMAP

```bash
# POP3
hydra -l user@domain.com -P passwords.txt pop3://192.168.1.1

# IMAP
hydra -l user@domain.com -P passwords.txt imap://192.168.1.1
```

### LDAP

```bash
hydra -l "cn=admin,dc=example,dc=com" -P passwords.txt ldap://192.168.1.1
```

### SNMP

```bash
hydra -P community_strings.txt snmp://192.168.1.1
```

---

## 📚 Ordlister og passord

### Populære ordlister

```bash
# Plasseringer i Kali Linux
/usr/share/wordlists/rockyou.txt
/usr/share/wordlists/dirb/common.txt
/usr/share/seclists/Passwords/

# Passord i SecLists
/usr/share/seclists/Passwords/Common-Credentials/10-million-password-list-top-100000.txt
/usr/share/seclists/Passwords/darkweb2017-top10000.txt
/usr/share/seclists/Passwords/Default-Credentials/
```

### Passordgenerering (-x)

```bash
# Generer passord: min:max:charset
hydra -l admin -x 4:6:a 192.168.1.1 ssh    # 4-6 små bokstaver
hydra -l admin -x 4:6:A 192.168.1.1 ssh    # 4-6 store bokstaver
hydra -l admin -x 4:6:1 192.168.1.1 ssh    # 4-6 sifre
hydra -l admin -x 4:6:aA1 192.168.1.1 ssh  # Blandet

# Alternativer for tegnsett:
# a = små bokstaver    A = store bokstaver
# 1 = sifre           ! = spesialtegn
```

### Kolonseparert fil (-C)

```bash
# Formatet user:password
hydra -C creds.txt 192.168.1.1 ssh

# creds.txt:
# admin:admin
# admin:password
# root:toor
# guest:guest
```

### Tilpasning av brukernavn og passord

```bash
# Tomme passord
hydra -l admin -e n 192.168.1.1 ssh

# Prøv brukernavnet som passord
hydra -l admin -e s 192.168.1.1 ssh

# Prøv brukernavnet baklengs som passord
hydra -l admin -e r 192.168.1.1 ssh

# Alle spesialkontroller
hydra -l admin -e nsr -P passwords.txt 192.168.1.1 ssh
```

---

## ⚡ Avanserte alternativer

### Utdata og logging

```bash
# Lagre resultater i fil
hydra -l admin -P passwords.txt -o results.txt 192.168.1.1 ssh

# Utdata i JSON
hydra -l admin -P passwords.txt -o results.json -b json 192.168.1.1 ssh
```

### Gjenoppta angrep

```bash
# Lagre økt for gjenopptakelse (originalens beskrivelse; se presiseringen)
hydra -l admin -P large_wordlist.txt -o results.txt -R 192.168.1.1 ssh

# Gjenoppta et avbrutt angrep
hydra -R
```

### Proxystøtte

```bash
# HTTP-proxy
export HYDRA_PROXY=http://proxy:8080
hydra -l admin -P passwords.txt 192.168.1.1 ssh

# SOCKS-proxy
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt 192.168.1.1 ssh
```

### Ytelsesjustering

```bash
# Parallelle oppgaver (standard i originalen: 16)
hydra -l admin -P passwords.txt -t 32 192.168.1.1 ssh

# Ventetid mellom forsøk (originalens beskrivelse; se presiseringen)
hydra -l admin -P passwords.txt -w 10 192.168.1.1 ssh

# Tidsavbrudd for forbindelse (originalens beskrivelse; se presiseringen)
hydra -l admin -P passwords.txt -T 30 192.168.1.1 ssh
```

### Flere mål

```bash
# Fra fil
hydra -L users.txt -P passwords.txt -M targets.txt ssh

# targets.txt:
# 192.168.1.1
# 192.168.1.2
# 192.168.1.3
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: Brute force mot SSH
```bash
hydra -l root -P /usr/share/wordlists/rockyou.txt \
    -t 4 -V 192.168.1.1 ssh
```

### Eksempel 2: Påloggingsskjema på web
```bash
hydra -l admin -P passwords.txt \
    192.168.1.1 http-post-form \
    "/login:username=^USER^&password=^PASS^:Invalid"
```

### Eksempel 3: WordPress-pålogging
```bash
hydra -l admin -P passwords.txt \
    target.com http-post-form \
    "/wp-login.php:log=^USER^&pwd=^PASS^:Invalid username"
```

### Eksempel 4: Flere tjenester

> **Presisering:** Den viste linjen med `ssh ftp mysql` stemmer ikke med den dokumenterte syntaksen for én tjeneste per kjøring. Den er bevart fra originalen og skal ikke leses som et fungerende eksempel på automatisk testing av alle tre tjenestene. HTTP-suksess-/feilstrenger må dessuten samsvare med det faktiske skjemaet; et treff er ikke alene bevis på gyldig pålogging.
```bash
# Undersøk flere tjenester på én vert (se presiseringen)
hydra -l admin -P passwords.txt 192.168.1.1 ssh ftp mysql
```

### Eksempel 5: Passordspraying
```bash
hydra -L users.txt -p "Winter2024!" \
    192.168.1.1 smb
```

### Eksempel 6: Med proxy/Tor
```bash
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt target.com http-post-form \
    "/login:user=^USER^&pass=^PASS^:Failed"
```

### Eksempel 7: HTTP Basic-autentisering (administrasjonspanel)
```bash
hydra -l admin -P passwords.txt \
    https-get://192.168.1.1/admin/
```

### Eksempel 8: RDP-angrep (vær forsiktig!)
```bash
hydra -l administrator -P passwords.txt \
    -t 1 -V rdp://192.168.1.1
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Oppgave | Kommando |
|------|---------|
| SSH-angrep | `hydra -l user -P pass.txt ssh://target` |
| FTP-angrep | `hydra -l user -P pass.txt ftp://target` |
| HTTP POST-skjema | `hydra -l user -P pass.txt target http-post-form "/login:u=^USER^&p=^PASS^:Failed"` |
| RDP-angrep | `hydra -l user -P pass.txt rdp://target` |
| Lagre utdata | `hydra ... -o results.txt` |
| Gjenoppta angrep | `hydra -R` |

### Vanlige alternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `-l/-L` | Brukernavn/liste |
| `-p/-P` | Passord/liste |
| `-C` | Fil med user:pass |
| `-t` | Parallelle oppgaver |
| `-s` | Port |
| `-f` | Stopp ved vellykket pålogging |
| `-V` | Detaljerte utdata |
| `-o` | Utdatafil |
| `-x` | Generer passord |
| `-e nsr` | Prøv tomt/samme/baklengs |

---

## 💡 Tips og anbefalte arbeidsmåter

### Ytelsestips

1. **Begrens parallelliteten for følsomme tjenester**
   ```bash
   # RDP, SMB – bruk få parallelle oppgaver
   hydra -t 1 ...
   
   # SSH – moderat parallellitet
   hydra -t 4 ...
   
   # Webskjemaer – kan bruke flere oppgaver
   hydra -t 16 ...
   ```

2. **Bruk målrettede ordlister**
   ```bash
   # Bedre enn rockyou.txt:
   # - De 1000 vanligste passordene
   # - Standard påloggingsopplysninger
   # - Virksomhetsspesifikke ord
   ```

3. **Kontroller regler for kontosperring først**
   ```bash
   # Test med en liten ordliste først
   hydra -l admin -P small.txt -t 1 target ssh
   ```

### Tips for å begrense synlighet

```bash
# Legg inn pauser (originalens beskrivelse; se presiseringen)
hydra -l admin -P passwords.txt -w 30 target ssh

# Få parallelle oppgaver
hydra -l admin -P passwords.txt -t 2 target ssh

# Gjennom proxy
export HYDRA_PROXY=socks5://127.0.0.1:9050
```

---

## ⚠️ Juridisk ansvarsfraskrivelse

> **ADVARSEL:** Hydra er et kraftig angrepsverktøy som bare skal brukes til **autorisert testing**.
> 
> - ✅ Test systemer du eier
> - ✅ Test med uttrykkelig skriftlig tillatelse
> - ✅ Bruk i autoriserte penetrasjonstester
> - ❌ Ikke angrip uten tillatelse
> - ❌ Brute force uten tillatelse er ulovlig
> 
> **Uautoriserte tilgangsforsøk er ulovlige og kan føre til straffansvar.**

---

## 📚 Ressurser

### Offisielle ressurser
- [THC-Hydra GitHub](https://github.com/vanhauser-thc/thc-hydra)
- [Hydra-manual](https://github.com/vanhauser-thc/thc-hydra/blob/master/README.md)

### Ordlister
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [Standard påloggingsopplysninger](https://github.com/ihebski/DefaultCreds-cheat-sheet)

### Relaterte hurtigreferanser
- [Nmap](../../Nmap/translations/README.nb.md)
- [Metasploit](../../Metasploit/translations/README.nb.md)
- [SQLMap](../../SQLMap/translations/README.nb.md)

---

<p align="center">
  <b>🔓 Lær testing av nettverkspålogging!</b><br>
  <i>Innhent alltid tillatelse først</i>
</p>
