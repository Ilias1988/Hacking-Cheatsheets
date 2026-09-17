# 📊 Log Analysis Cheatsheet

```
  ██╗      ██████╗  ██████╗      █████╗ ███╗   ██╗ █████╗ ██╗  ██╗   ██╗███████╗██╗███████╗
  ██║     ██╔═══██╗██╔════╝     ██╔══██╗████╗  ██║██╔══██╗██║  ╚██╗ ██╔╝██╔════╝██║██╔════╝
  ██║     ██║   ██║██║  ███╗    ███████║██╔██╗ ██║███████║██║   ╚████╔╝ ███████╗██║███████╗
  ██║     ██║   ██║██║   ██║    ██╔══██║██║╚██╗██║██╔══██║██║    ╚██╔╝  ╚════██║██║╚════██║
  ███████╗╚██████╔╝╚██████╔╝    ██║  ██║██║ ╚████║██║  ██║███████╗██║   ███████║██║███████║
  ╚══════╝ ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝   ╚══════╝╚═╝╚══════╝
```

---

## 🖥️ Windows Event Logs

### Log Locations
```
# Security Log
C:\Windows\System32\winevt\Logs\Security.evtx

# System Log
C:\Windows\System32\winevt\Logs\System.evtx

# Application Log
C:\Windows\System32\winevt\Logs\Application.evtx

# PowerShell Logs
C:\Windows\System32\winevt\Logs\Microsoft-Windows-PowerShell%4Operational.evtx
C:\Windows\System32\winevt\Logs\Windows PowerShell.evtx

# Sysmon (if installed)
C:\Windows\System32\winevt\Logs\Microsoft-Windows-Sysmon%4Operational.evtx
```

### Critical Security Event IDs

#### Authentication Events
| Event ID | Description | Importance |
|----------|-------------|------------|
| **4624** | Successful logon | Track user access |
| **4625** | Failed logon | Brute force detection |
| **4634** | Logoff | Session tracking |
| **4647** | User-initiated logoff | Session tracking |
| **4648** | Explicit credential logon | Lateral movement |
| **4672** | Special privileges assigned | Admin activity |
| **4768** | Kerberos TGT request | AD authentication |
| **4769** | Kerberos service ticket | Service access |
| **4771** | Kerberos pre-auth failed | Password spray |
| **4776** | NTLM authentication | Credential validation |

#### Logon Types (for 4624/4625)
| Type | Description |
|------|-------------|
| 2 | Interactive (local) |
| 3 | Network |
| 4 | Batch |
| 5 | Service |
| 7 | Unlock |
| 8 | Network cleartext |
| 9 | New credentials |
| 10 | Remote interactive (RDP) |
| 11 | Cached interactive |

#### Account Management
| Event ID | Description |
|----------|-------------|
| **4720** | User account created |
| **4722** | User account enabled |
| **4723** | Password change attempt |
| **4724** | Password reset |
| **4725** | User account disabled |
| **4726** | User account deleted |
| **4728** | Member added to security group |
| **4732** | Member added to local group |
| **4756** | Member added to universal group |

#### Process & Service Events
| Event ID | Description |
|----------|-------------|
| **4688** | Process creation |
| **4689** | Process termination |
| **7045** | Service installed |
| **7036** | Service state change |
| **4697** | Service installed (Security log) |

#### Scheduled Tasks
| Event ID | Description |
|----------|-------------|
| **4698** | Scheduled task created |
| **4699** | Scheduled task deleted |
| **4700** | Scheduled task enabled |
| **4702** | Scheduled task updated |

### PowerShell Query Commands
```powershell
# Get Security log entries
Get-WinEvent -LogName Security -MaxEvents 100

# Failed logons (4625)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4625} | 
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='IP';E={$_.Properties[19].Value}}

# Successful logons (4624)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='LogonType';E={$_.Properties[8].Value}}

# New process creation (4688)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4688} |
    Select-Object TimeCreated, @{N='Process';E={$_.Properties[5].Value}}, @{N='CommandLine';E={$_.Properties[8].Value}}

# Service installations (7045)
Get-WinEvent -FilterHashtable @{LogName='System';ID=7045} |
    Select-Object TimeCreated, @{N='ServiceName';E={$_.Properties[0].Value}}

# Search by time range
$startTime = (Get-Date).AddDays(-7)
Get-WinEvent -FilterHashtable @{LogName='Security';StartTime=$startTime}
```

### Wevtutil Commands
```cmd
# Query Security log
wevtutil qe Security /c:50 /f:text

# Query for specific Event ID
wevtutil qe Security /q:"*[System[(EventID=4625)]]" /c:100 /f:text

# Export log
wevtutil epl Security C:\evidence\security.evtx

# Clear log (use with caution)
wevtutil cl Security
```

---

## 🐧 Linux Log Analysis

### Log Locations
```bash
# Authentication
/var/log/auth.log        # Debian/Ubuntu
/var/log/secure          # RHEL/CentOS

# System
/var/log/syslog          # Debian/Ubuntu
/var/log/messages        # RHEL/CentOS

# Kernel
/var/log/kern.log
/var/log/dmesg

# Cron
/var/log/cron

# Audit
/var/log/audit/audit.log

# Web servers
/var/log/apache2/        # Apache
/var/log/nginx/          # Nginx
/var/log/httpd/          # RHEL Apache

# SSH
/var/log/auth.log
```

### Common Log Analysis Commands

#### Authentication Analysis
```bash
# Failed SSH attempts
grep "Failed password" /var/log/auth.log
grep "authentication failure" /var/log/auth.log

# Successful SSH logins
grep "Accepted" /var/log/auth.log

# SSH brute force (count by IP)
grep "Failed password" /var/log/auth.log | awk '{print $(NF-3)}' | sort | uniq -c | sort -rn

# User privilege escalation (sudo)
grep "sudo:" /var/log/auth.log
grep "session opened for user root" /var/log/auth.log

# su attempts
grep "su:" /var/log/auth.log
```

#### System Analysis
```bash
# Boot/shutdown times
last -x | grep shutdown
last -x | grep reboot

# Currently logged in users
who
w

# Login history
last
lastlog

# Bad login attempts
lastb
```

#### Process/Service Analysis
```bash
# Cron job executions
grep CRON /var/log/syslog

# Service starts/stops
grep -E "Started|Stopped" /var/log/syslog
journalctl -u <service-name>

# Kernel messages
dmesg | tail -100
grep -i error /var/log/kern.log
```

### Useful Grep/Awk Patterns
```bash
# Extract IPs from logs
grep -oE '\b[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\b' /var/log/auth.log

# Count occurrences by IP
cat /var/log/auth.log | grep "Failed" | awk '{print $11}' | sort | uniq -c | sort -rn | head -20

# Time-based search
awk '/Jan 15 10:00/,/Jan 15 11:00/' /var/log/auth.log

# Search multiple files
grep -r "error" /var/log/

# Real-time monitoring
tail -f /var/log/auth.log | grep --line-buffered "Failed"
```

### Journalctl (Systemd)
```bash
# All logs
journalctl

# Follow mode
journalctl -f

# Since specific time
journalctl --since "2024-01-15 10:00:00"
journalctl --since "1 hour ago"

# By unit/service
journalctl -u sshd
journalctl -u nginx

# By priority
journalctl -p err       # Errors only
journalctl -p warning   # Warnings and above

# Kernel messages
journalctl -k

# JSON output
journalctl -o json
```

---

## 🌐 Web Server Logs

### Apache Log Format
```
# Combined Log Format
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."

# Fields:
# IP - ident - user - [timestamp] "method path protocol" status size "referer" "user-agent"
```

### Nginx Log Format
```
# Default format similar to Apache
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."
```

### Web Log Analysis
```bash
# Top requested URLs
awk '{print $7}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# Top IPs
awk '{print $1}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# HTTP status codes
awk '{print $9}' /var/log/apache2/access.log | sort | uniq -c | sort -rn

# 404 errors
grep ' 404 ' /var/log/apache2/access.log

# POST requests (potential attacks)
grep '"POST' /var/log/apache2/access.log

# SQL injection attempts
grep -iE "(union|select|insert|update|delete|drop|script)" /var/log/apache2/access.log

# Path traversal attempts
grep -E "(\.\./|\.\.\\)" /var/log/apache2/access.log

# Requests by hour
awk '{print $4}' /var/log/apache2/access.log | cut -d: -f2 | sort | uniq -c
```

---

## 🛡️ Sysmon Log Analysis

### Key Sysmon Event IDs
| Event ID | Description |
|----------|-------------|
| 1 | Process creation |
| 2 | File creation time changed |
| 3 | Network connection |
| 5 | Process terminated |
| 6 | Driver loaded |
| 7 | Image loaded (DLL) |
| 8 | CreateRemoteThread |
| 10 | ProcessAccess |
| 11 | FileCreate |
| 12-14 | Registry events |
| 15 | FileCreateStreamHash |
| 17-18 | Pipe events |
| 22 | DNS query |

### PowerShell Sysmon Queries
```powershell
# Process creation (Event 1)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='CommandLine';E={$_.Properties[10].Value}}

# Network connections (Event 3)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=3} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='DestIP';E={$_.Properties[14].Value}}

# DNS queries (Event 22)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=22} |
    Select-Object TimeCreated, @{N='Query';E={$_.Properties[4].Value}}
```

---

## 📊 Quick Reference

### Suspicious Patterns to Look For

| Category | Pattern |
|----------|---------|
| **Brute Force** | Multiple 4625 from same IP |
| **Spray Attack** | 4625 to many users from same IP |
| **Lateral Movement** | 4624 Type 3 + 4648 |
| **Persistence** | 4698 (new scheduled task) |
| **Privesc** | 4672 on non-admin accounts |
| **Credential Dump** | Access to lsass.exe |

### Log Analysis Tools
| Tool | Use |
|------|-----|
| **grep/awk/sed** | Linux log parsing |
| **PowerShell** | Windows log analysis |
| **Splunk** | Enterprise SIEM |
| **ELK Stack** | Open source SIEM |
| **LogParser** | SQL queries on logs |
| **Chainsaw** | Fast Windows log analysis |

---

## 🔗 Related Cheatsheets

- [Incident Response](./Incident-Response.md)
- [SIEM Detection](./SIEM-Detection.md)
- [Sigma Rules](./Sigma-Rules.md)

---

**Previous:** [← Incident Response](./Incident-Response.md)

**Next:** [SIEM Detection →](./SIEM-Detection.md)
