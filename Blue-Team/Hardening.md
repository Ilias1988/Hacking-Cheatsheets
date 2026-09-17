# 🔒 System Hardening Cheatsheet

```
██╗  ██╗ █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗██╗███╗   ██╗ ██████╗ 
██║  ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██║████╗  ██║██╔════╝ 
███████║███████║██████╔╝██║  ██║█████╗  ██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
██╔══██║██╔══██║██╔══██╗██║  ██║██╔══╝  ██║╚██╗██║██║██║╚██╗██║██║   ██║
██║  ██║██║  ██║██║  ██║██████╔╝███████╗██║ ╚████║██║██║ ╚████║╚██████╔╝
╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🖥️ Windows Hardening

### Account Security
```powershell
# Disable local administrator
net user Administrator /active:no

# Rename administrator account
wmic useraccount where name='Administrator' rename 'NewAdminName'

# Set strong password policy (GPO or secpol.msc)
# Minimum length: 14 characters
# Complexity: Enabled
# Maximum age: 60 days

# Disable guest account
net user Guest /active:no

# Limit admin token usage (UAC)
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v EnableLUA /t REG_DWORD /d 1 /f
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v ConsentPromptBehaviorAdmin /t REG_DWORD /d 2 /f
```

### Disable Unnecessary Services
```powershell
# Disable Remote Registry
sc config RemoteRegistry start= disabled

# Disable Windows Remote Management (if not needed)
sc config WinRM start= disabled

# Disable SMBv1
Disable-WindowsOptionalFeature -Online -FeatureName SMB1Protocol -NoRestart

# Disable NetBIOS
Get-WmiObject Win32_NetworkAdapterConfiguration | where {$_.IPEnabled -eq $true} | 
    Invoke-WmiMethod -Name SetTcpipNetbios -ArgumentList 2
```

### Firewall Configuration
```powershell
# Enable Windows Firewall
netsh advfirewall set allprofiles state on

# Block inbound by default
netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound

# Enable logging
netsh advfirewall set allprofiles logging filename %SystemRoot%\System32\LogFiles\Firewall\pfirewall.log
netsh advfirewall set allprofiles logging maxfilesize 4096
netsh advfirewall set allprofiles logging droppedconnections enable
```

### PowerShell Security
```powershell
# Enable Constrained Language Mode
$ExecutionContext.SessionState.LanguageMode = "ConstrainedLanguage"

# Enable Script Block Logging
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" /v EnableScriptBlockLogging /t REG_DWORD /d 1 /f

# Enable Module Logging
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" /v EnableModuleLogging /t REG_DWORD /d 1 /f
```

### Audit Policy
```powershell
# Enable command line logging in process creation
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System\Audit" /v ProcessCreationIncludeCmdLine_Enabled /t REG_DWORD /d 1 /f

# Configure audit policies
auditpol /set /category:"Logon/Logoff" /success:enable /failure:enable
auditpol /set /category:"Account Logon" /success:enable /failure:enable
auditpol /set /category:"Account Management" /success:enable /failure:enable
auditpol /set /category:"Object Access" /success:enable /failure:enable
auditpol /set /category:"Privilege Use" /success:enable /failure:enable
```

### Windows Defender
```powershell
# Enable Real-time protection
Set-MpPreference -DisableRealtimeMonitoring $false

# Enable cloud protection
Set-MpPreference -MAPSReporting Advanced
Set-MpPreference -SubmitSamplesConsent SendAllSamples

# Enable Attack Surface Reduction rules
Set-MpPreference -AttackSurfaceReductionRules_Ids 56a863a9-875e-4185-98a7-b882c64b5ce5 -AttackSurfaceReductionRules_Actions Enabled
```

### LSASS Protection
```powershell
# Enable Credential Guard (requires TPM)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v LsaCfgFlags /t REG_DWORD /d 1 /f

# Enable LSASS as PPL (Protected Process Light)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v RunAsPPL /t REG_DWORD /d 1 /f
```

---

## 🐧 Linux Hardening

### User & Access Control
```bash
# Disable root login
sudo passwd -l root

# Set password complexity (/etc/security/pwquality.conf)
minlen = 14
dcredit = -1
ucredit = -1
lcredit = -1
ocredit = -1

# Password aging (/etc/login.defs)
PASS_MAX_DAYS 90
PASS_MIN_DAYS 7
PASS_WARN_AGE 14

# Disable unused accounts
usermod -s /sbin/nologin username
```

### SSH Hardening (/etc/ssh/sshd_config)
```bash
# Disable root login
PermitRootLogin no

# Disable password auth (use keys)
PasswordAuthentication no
PubkeyAuthentication yes

# Change default port
Port 2222

# Limit users
AllowUsers admin operator

# Protocol 2 only
Protocol 2

# Disable empty passwords
PermitEmptyPasswords no

# Timeout settings
ClientAliveInterval 300
ClientAliveCountMax 2

# Restart SSH
sudo systemctl restart sshd
```

### Firewall (iptables/nftables)
```bash
# Default deny policy
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -P OUTPUT ACCEPT

# Allow loopback
iptables -A INPUT -i lo -j ACCEPT

# Allow established
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

# Allow SSH (custom port)
iptables -A INPUT -p tcp --dport 2222 -j ACCEPT

# Save rules
iptables-save > /etc/iptables/rules.v4
```

### File Permissions
```bash
# Secure critical files
chmod 644 /etc/passwd
chmod 000 /etc/shadow
chmod 644 /etc/group
chmod 600 /etc/ssh/sshd_config
chmod 700 /root

# Find SUID/SGID files
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null

# Remove unnecessary SUID
chmod u-s /usr/bin/unnecessary_binary
```

### Disable Unused Services
```bash
# List enabled services
systemctl list-unit-files --state=enabled

# Disable unnecessary services
systemctl disable bluetooth
systemctl disable cups
systemctl disable avahi-daemon
systemctl disable rpcbind

# Disable IPv6 if not needed
echo "net.ipv6.conf.all.disable_ipv6 = 1" >> /etc/sysctl.conf
```

### Kernel Hardening (/etc/sysctl.conf)
```bash
# Disable IP forwarding
net.ipv4.ip_forward = 0

# Disable source routing
net.ipv4.conf.all.accept_source_route = 0
net.ipv4.conf.default.accept_source_route = 0

# Enable SYN cookies
net.ipv4.tcp_syncookies = 1

# Disable ICMP redirects
net.ipv4.conf.all.accept_redirects = 0
net.ipv4.conf.default.accept_redirects = 0
net.ipv4.conf.all.send_redirects = 0

# Enable exec shield
kernel.randomize_va_space = 2

# Apply changes
sysctl -p
```

### Audit System (auditd)
```bash
# Install auditd
apt install auditd

# Example rules (/etc/audit/rules.d/audit.rules)
-w /etc/passwd -p wa -k passwd_changes
-w /etc/shadow -p wa -k shadow_changes
-w /etc/sudoers -p wa -k sudoers_changes
-a always,exit -F arch=b64 -S execve -k command_execution

# Restart auditd
systemctl restart auditd
```

---

## 📊 Quick Hardening Checklist

### Windows
```markdown
□ Disable unnecessary services (SMBv1, Remote Registry)
□ Enable Windows Firewall
□ Configure strong password policy
□ Enable PowerShell logging
□ Configure audit policy
□ Enable Windows Defender
□ Enable LSASS protection
□ Install security updates
□ Remove unused software
```

### Linux
```markdown
□ Disable root login
□ Configure SSH hardening
□ Enable firewall (iptables/ufw)
□ Set proper file permissions
□ Disable unused services
□ Configure kernel hardening
□ Enable auditd
□ Install security updates
□ Configure fail2ban
```

---

## 📚 Resources

- [CIS Benchmarks](https://www.cisecurity.org/cis-benchmarks/)
- [NIST Guidelines](https://www.nist.gov/cyberframework)
- [Microsoft Security Baselines](https://docs.microsoft.com/en-us/windows/security/threat-protection/windows-security-baselines)

---

## 🔗 Related Cheatsheets

- [Incident Response](./Incident-Response.md)
- [Network Defense](./Network-Defense.md)

---

**Previous:** [← Network Defense](./Network-Defense.md)

**Back to Overview:** [🛡️ Blue Team](./README.md)
