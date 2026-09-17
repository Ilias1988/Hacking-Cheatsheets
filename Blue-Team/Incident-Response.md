# 🚨 Incident Response Cheatsheet

```
  ██╗███╗   ██╗ ██████╗██╗██████╗ ███████╗███╗   ██╗████████╗
  ██║████╗  ██║██╔════╝██║██╔══██╗██╔════╝████╗  ██║╚══██╔══╝
  ██║██╔██╗ ██║██║     ██║██║  ██║█████╗  ██╔██╗ ██║   ██║   
  ██║██║╚██╗██║██║     ██║██║  ██║██╔══╝  ██║╚██╗██║   ██║   
  ██║██║ ╚████║╚██████╗██║██████╔╝███████╗██║ ╚████║   ██║   
  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝   
        ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗███████╗███████╗
        ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔════╝██╔════╝
        ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║███████╗█████╗  
        ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║╚════██║██╔══╝  
        ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║███████║███████╗
        ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚══════╝
```

---

## 📊 Incident Response Lifecycle (NIST)

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│ 1.PREPARATION│────▶│ 2.DETECTION  │────▶│3.CONTAINMENT │────▶│4.ERADICATION │
│              │     │ & ANALYSIS   │     │              │     │              │
└──────────────┘     └──────────────┘     └──────────────┘     └──────────────┘
       ▲                                                              │
       │         ┌──────────────┐     ┌──────────────┐               │
       └─────────│ 6.LESSONS    │◀────│ 5.RECOVERY   │◀──────────────┘
                 │   LEARNED    │     │              │
                 └──────────────┘     └──────────────┘
```

---

## 1️⃣ Preparation

### Documentation Checklist
```markdown
- [ ] Incident Response Plan documented
- [ ] Contact list (internal & external)
- [ ] Communication plan
- [ ] Asset inventory
- [ ] Network diagrams
- [ ] Baseline configurations
- [ ] Backup verification
```

### IR Team Contacts Template
```
IR Lead:          [Name] - [Phone] - [Email]
IT Security:      [Name] - [Phone] - [Email]
Legal:            [Name] - [Phone] - [Email]
Communications:   [Name] - [Phone] - [Email]
HR:               [Name] - [Phone] - [Email]
External Forensics: [Company] - [Phone]
Law Enforcement:  [Agency] - [Phone]
```

### Essential IR Tools
```bash
# Forensic collection
- FTK Imager
- Volatility
- Velociraptor
- KAPE

# Network analysis
- Wireshark
- tcpdump
- Zeek

# Log analysis
- Splunk/ELK
- grep/awk/sed
```

---

## 2️⃣ Detection & Analysis

### Initial Triage Questions
```markdown
1. What type of incident is this?
   - [ ] Malware infection
   - [ ] Unauthorized access
   - [ ] Data breach
   - [ ] Denial of Service
   - [ ] Insider threat
   - [ ] Phishing

2. When was it detected?
3. How was it detected?
4. What systems are affected?
5. What data may be impacted?
6. Is it still active?
```

### Windows Quick Triage
```cmd
# Current user sessions
query user
qwinsta

# Active connections
netstat -ano | findstr ESTABLISHED

# Running processes
tasklist /v
wmic process get processid,parentprocessid,commandline

# Recent file modifications
forfiles /P C:\ /S /D +01/01/2024

# Scheduled tasks
schtasks /query /fo LIST /v

# Services
sc query state=all
wmic service get name,startmode,state

# Registry Run keys
reg query "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"
reg query "HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"

# Recent PowerShell history
type %APPDATA%\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt
```

### Linux Quick Triage
```bash
# Who is logged in
w
who
last -a

# Active connections
ss -tulnp
netstat -tulnp
lsof -i

# Running processes
ps auxwww
pstree -p

# Recent file modifications
find / -mtime -1 -type f 2>/dev/null

# Cron jobs
cat /etc/crontab
ls -la /etc/cron.*
for user in $(cut -f1 -d: /etc/passwd); do crontab -u $user -l 2>/dev/null; done

# Startup scripts
ls -la /etc/init.d/
systemctl list-unit-files --type=service

# History
cat ~/.bash_history
cat /root/.bash_history
```

### Network Analysis
```bash
# Capture traffic
tcpdump -i eth0 -w capture.pcap

# Filter specific host
tcpdump -i eth0 host 192.168.1.100

# HTTP traffic
tcpdump -i eth0 port 80 or port 443

# DNS queries
tcpdump -i eth0 port 53
```

---

## 3️⃣ Containment

### Short-term Containment
```bash
# Isolate network (Windows)
netsh advfirewall set allprofiles state on
netsh advfirewall firewall add rule name="Block All" dir=in action=block
netsh advfirewall firewall add rule name="Block All Out" dir=out action=block

# Isolate network (Linux)
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

# Disable compromised account
net user compromised_user /active:no   # Windows
passwd -l compromised_user             # Linux

# Kill malicious process
taskkill /PID <pid> /F               # Windows
kill -9 <pid>                        # Linux
```

### Long-term Containment
```bash
# Segment network
# Apply firewall rules to isolate affected VLAN
# Update ACLs on switches/routers

# Block malicious IPs/domains at firewall
# Add to proxy blacklist
# Update DNS sinkhole

# Disable affected services temporarily
sc config <service> start= disabled   # Windows
systemctl disable <service>           # Linux
```

### Evidence Preservation
```bash
# Memory dump (Windows - use WinPMEM or DumpIt)
winpmem_mini_x64.exe memory.raw

# Memory dump (Linux)
dd if=/dev/mem of=/evidence/memory.dump bs=1M

# Disk image
dd if=/dev/sda of=/evidence/disk.img bs=4M status=progress

# Hash for integrity
sha256sum /evidence/disk.img > disk.img.sha256

# Capture volatile data first (order of volatility):
# 1. Memory
# 2. Network state
# 3. Running processes
# 4. Disk
```

---

## 4️⃣ Eradication

### Malware Removal
```bash
# Identify and remove persistence

# Windows - Registry
reg delete "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v "MalwareName" /f

# Windows - Scheduled tasks
schtasks /delete /tn "MalwareTask" /f

# Windows - Services
sc delete "MalwareService"

# Linux - Cron
crontab -r
rm /etc/cron.d/malware

# Linux - Systemd
systemctl disable malware
rm /etc/systemd/system/malware.service
```

### Password Reset
```bash
# Force password reset for all compromised accounts
# Reset service account passwords
# Rotate API keys/tokens
# Invalidate sessions

# Windows - Force password change
net user username /logonpasswordchg:yes

# Reset Kerberos tickets (if AD compromise)
# - Reset krbtgt password TWICE
# - Reset all admin account passwords
```

### Patch & Update
```bash
# Apply missing security patches
# Update AV signatures
# Fix misconfigurations that allowed the attack
```

---

## 5️⃣ Recovery

### System Restoration
```bash
# Restore from clean backup
# Rebuild compromised systems
# Validate integrity before bringing online

# Gradual restoration:
1. Restore critical systems first
2. Monitor for signs of re-infection
3. Restore additional systems
4. Return to normal operations
```

### Monitoring (Post-Recovery)
```bash
# Increase logging verbosity
# Deploy additional monitoring
# Watch for attacker return

# Key indicators to monitor:
- Same IOCs (IPs, domains, hashes)
- Similar TTPs
- Attempted access to same accounts
- Communication to C2 infrastructure
```

---

## 6️⃣ Lessons Learned

### Post-Incident Report Template
```markdown
## Incident Summary
- Incident ID:
- Date Detected:
- Date Contained:
- Date Resolved:
- Type of Incident:
- Severity:

## Timeline
| Date/Time | Event |
|-----------|-------|
| YYYY-MM-DD HH:MM | Initial detection |
| ... | ... |

## Impact Assessment
- Systems affected:
- Data compromised:
- Business impact:
- Financial impact:

## Root Cause Analysis
- How did attacker gain access?
- What vulnerabilities were exploited?
- What controls failed?

## Actions Taken
1. Detection
2. Containment
3. Eradication
4. Recovery

## Recommendations
1. Immediate improvements
2. Long-term security enhancements
3. Policy changes

## Lessons Learned
- What worked well?
- What could be improved?
- Training needs identified?
```

---

## 📊 Quick Reference

### Severity Classification

| Level | Description | Response Time |
|-------|-------------|---------------|
| **Critical** | Active breach, data exfil | Immediate |
| **High** | Confirmed compromise | < 1 hour |
| **Medium** | Potential compromise | < 4 hours |
| **Low** | Suspicious activity | < 24 hours |

### Common IOCs to Collect

| IOC Type | Examples |
|----------|----------|
| **Hash** | MD5, SHA1, SHA256 |
| **IP** | Source/Dest IPs |
| **Domain** | C2 domains |
| **URL** | Malicious URLs |
| **Email** | Phishing sender |
| **File** | Malware filename |
| **Registry** | Persistence keys |

---

## 🔗 Related Cheatsheets

- [Log Analysis](./Log-Analysis.md)
- [SIEM Detection](./SIEM-Detection.md)
- [Volatility (Forensics)](../Volatility/README.md)

---

**Next:** [Log Analysis →](./Log-Analysis.md)
