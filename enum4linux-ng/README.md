# enum4linux-ng Cheatsheet

[enum4linux-ng](https://github.com/cddmp/enum4linux-ng) is a next-generation rewrite of the legacy `enum4linux` tool written in Python 3. It performs comprehensive Windows and Samba information gathering over SMB (Server Message Block), NetBIOS, RPC (Remote Procedure Call), and LDAP. It extracts user accounts, group memberships, shared folders, password policies, OS information, and RID cycles.

---

## Table of Contents

- [Key Improvements Over Legacy enum4linux](#key-improvements-over-legacy-enum4linux)
- [Installation & Requirements](#installation--requirements)
- [Basic SMB & Domain Enumeration](#basic-smb--domain-enumeration)
- [Authentication Modes (Null Session vs. Valid User)](#authentication-modes-null-session-vs-valid-user)
- [Targeted Enumeration Modules](#targeted-enumeration-modules)
- [RID Cycling & User Account Harvesting](#rid-cycling--user-account-harvesting)
- [Exporting Reports (JSON, YAML, Text)](#exporting-reports-json-yaml-text)
- [Defensive Hardening for SMB & RPC](#defensive-hardening-for-smb--rpc)

---

## Key Improvements Over Legacy enum4linux

- **Modern Python 3 Engine:** Multi-threaded execution based on `impacket`, eliminating old Perl script crashes.
- **Enhanced OS Detection:** Precise detection of Windows Server (2012–2025), Windows 10/11, and modern Samba versions.
- **Structured Reporting:** Native export to JSON and YAML for seamless SIEM or scripting integration.
- **Smarter RID Cycling:** Flexible range adjustments and automatic administrator/guest RID detection.
- **Colorized CLI Output:** Clear visual indicators for security misconfigurations and null sessions.

---

## Installation & Requirements

```bash
# Clone the repository
git clone https://github.com/cddmp/enum4linux-ng.git
cd enum4linux-ng

# Install Python dependencies (impacket)
pip3 install -r requirements.txt

# Or install globally as a symlink
sudo ln -sf "$(pwd)/enum4linux-ng.py" /usr/local/bin/enum4linux-ng

# Verify command
enum4linux-ng -h
```

---

## Basic SMB & Domain Enumeration

### 1. Default Enumeration Run (`-A`)
Run all non-intrusive enumeration checks against the target:
```bash
enum4linux-ng -A 192.168.1.50
```
This module queries:
- NetBIOS names and workgroup/domain names.
- Operating system version and architecture.
- Shared directories and access permissions.
- Password policy (lockout threshold, minimum length, history).
- Users and local groups via RPC/SAMR.

---

## Authentication Modes (Null Session vs. Valid User)

### 1. Anonymous / Null Session (`-u '' -p ''`)
Test if the target permits unauthenticated IPC$ connections:
```bash
enum4linux-ng -u "" -p "" 192.168.1.50
```

### 2. Authenticated Domain / Local Account
Supply valid low-privilege credentials to dramatically expand harvested data:
```bash
enum4linux-ng -u "jdoe" -p "Password123!" 192.168.1.50
```

### 3. Supply Specific Domain Name (`-d`)
```bash
enum4linux-ng -u "jdoe" -p "Password123!" -d "CORP.LOCAL" 192.168.1.50
```

---

## Targeted Enumeration Modules

Run specific modules to minimize network footprints and avoid intrusion detection alerts:

### 1. Shared Folders & Access Permissions (`-S`)
Enumerate shares, comments, and check read/write access:
```bash
enum4linux-ng -S 192.168.1.50
```

### 2. Password Policy Extraction (`-P`)
Identify account lockout thresholds to safely plan password spraying:
```bash
enum4linux-ng -P 192.168.1.50
```
Key outputs to observe:
- **Minimum Password Length:** e.g., 8 characters.
- **Account Lockout Threshold:** e.g., 5 attempts (do not spray faster than lockout window!).
- **Reset Lockout Counter:** e.g., 30 minutes.

### 3. Users and Groups Only (`-U` and `-G`)
```bash
# Enumerate user list
enum4linux-ng -U 192.168.1.50

# Enumerate domain and local groups
enum4linux-ng -G 192.168.1.50
```

### 4. Operating System & NetBIOS Info Only (`-O` and `-I`)
```bash
enum4linux-ng -O -I 192.168.1.50
```

---

## RID Cycling & User Account Harvesting

When user enumeration via standard RPC queries is restricted, perform Relative Identifier (RID) cycling via SAMR query calls:

### 1. Default RID Cycling (Range 500 - 1100)
```bash
enum4linux-ng -R 192.168.1.50
```

### 2. Custom RID Range (e.g. 500 to 5000 for Large Organizations)
```bash
enum4linux-ng -r 500-5000 192.168.1.50
```

### 3. Extract Clean Username Wordlist
Filter discovered RID usernames into a text file for password spraying:
```bash
enum4linux-ng -R 192.168.1.50 -oJ /tmp/enum_out
jq -r '.users[].username' /tmp/enum_out.json | sort -u > /tmp/users.txt
```

---

## Exporting Reports (JSON, YAML, Text)

Export enumeration results for documentation or programmatic parsing:

### 1. Export in All Formats Simultaneously (`-oA`)
```bash
enum4linux-ng -A 192.168.1.50 -oA /tmp/target_recon
```
Generates:
- `/tmp/target_recon.json`
- `/tmp/target_recon.yaml`
- `/tmp/target_recon.txt`

### 2. Export to Specific Format
```bash
# JSON only
enum4linux-ng -A 192.168.1.50 -oJ /tmp/target.json

# YAML only
enum4linux-ng -A 192.168.1.50 -oY /tmp/target.yaml
```

---

## Defensive Hardening for SMB & RPC

| Vector | Risk | Hardening Recommendation |
|---|---|---|
| **Null Session (Anonymous IPC$)** | Unauthenticated domain reconnaissance | Set `RestrictNullSessAccess=1` in Windows Registry; configure `RestrictAnonymous=2` in SAM. |
| **SMBv1 Active** | EternalBlue (MS17-010) & MITM attacks | Completely disable SMBv1 across all endpoints and Domain Controllers. |
| **Cleartext Share Permissions** | Sensitive files and credentials exposed | Audit share permissions; enforce access-based enumeration (ABE). |
| **RID Cycling Enumeration** | Full user account list extraction | Restrict anonymous SAM accounts enumeration via Local Security Policy. |
