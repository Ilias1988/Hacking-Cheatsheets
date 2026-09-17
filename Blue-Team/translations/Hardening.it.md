# 🔒 Cheatsheet Hardening di Sistema

```
██╗  ██╗ █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗██╗███╗   ██╗ ██████╗ 
██║  ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██║████╗  ██║██╔════╝ 
███████║███████║██████╔╝██║  ██║█████╗  ██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
██╔══██║██╔══██║██╔══██╗██║  ██║██╔══╝  ██║╚██╗██║██║██║╚██╗██║██║   ██║
██║  ██║██║  ██║██║  ██║██████╔╝███████╗██║ ╚████║██║██║ ╚████║╚██████╔╝
╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🖥️ Hardening Windows

### Sicurezza degli Account
```powershell
# Disabilita amministratore locale
net user Administrator /active:no

# Rinomina account amministratore
wmic useraccount where name='Administrator' rename 'NewAdminName'

# Imposta policy password forte (GPO o secpol.msc)
# Lunghezza minima: 14 caratteri
# Complessità: Abilitata
# Durata massima: 60 giorni

# Disabilita account guest
net user Guest /active:no

# Limita uso token admin (UAC)
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v EnableLUA /t REG_DWORD /d 1 /f
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v ConsentPromptBehaviorAdmin /t REG_DWORD /d 2 /f
```

### Disabilita Servizi Non Necessari
```powershell
# Disabilita Remote Registry
sc config RemoteRegistry start= disabled

# Disabilita Windows Remote Management (se non necessario)
sc config WinRM start= disabled

# Disabilita SMBv1
Disable-WindowsOptionalFeature -Online -FeatureName SMB1Protocol -NoRestart

# Disabilita NetBIOS
Get-WmiObject Win32_NetworkAdapterConfiguration | where {$_.IPEnabled -eq $true} | 
    Invoke-WmiMethod -Name SetTcpipNetbios -ArgumentList 2
```

### Configurazione Firewall
```powershell
# Abilita Windows Firewall
netsh advfirewall set allprofiles state on

# Blocca inbound di default
netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound

# Abilita logging
netsh advfirewall set allprofiles logging filename %SystemRoot%\System32\LogFiles\Firewall\pfirewall.log
netsh advfirewall set allprofiles logging maxfilesize 4096
netsh advfirewall set allprofiles logging droppedconnections enable
```

### Sicurezza PowerShell
```powershell
# Abilita Constrained Language Mode
$ExecutionContext.SessionState.LanguageMode = "ConstrainedLanguage"

# Abilita Script Block Logging
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" /v EnableScriptBlockLogging /t REG_DWORD /d 1 /f

# Abilita Module Logging
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" /v EnableModuleLogging /t REG_DWORD /d 1 /f
```

### Audit Policy
```powershell
# Abilita logging della command line nella creazione processi
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System\Audit" /v ProcessCreationIncludeCmdLine_Enabled /t REG_DWORD /d 1 /f

# Configura audit policy
auditpol /set /category:"Logon/Logoff" /success:enable /failure:enable
auditpol /set /category:"Account Logon" /success:enable /failure:enable
auditpol /set /category:"Account Management" /success:enable /failure:enable
auditpol /set /category:"Object Access" /success:enable /failure:enable
auditpol /set /category:"Privilege Use" /success:enable /failure:enable
```

### Windows Defender
```powershell
# Abilita protezione in tempo reale
Set-MpPreference -DisableRealtimeMonitoring $false

# Abilita protezione cloud
Set-MpPreference -MAPSReporting Advanced
Set-MpPreference -SubmitSamplesConsent SendAllSamples

# Abilita Attack Surface Reduction Rules
Set-MpPreference -AttackSurfaceReductionRules_Ids 56a863a9-875e-4185-98a7-b882c64b5ce5 -AttackSurfaceReductionRules_Actions Enabled
```

### Protezione LSASS
```powershell
# Abilita Credential Guard (richiede TPM)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v LsaCfgFlags /t REG_DWORD /d 1 /f

# Abilita LSASS come PPL (Protected Process Light)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v RunAsPPL /t REG_DWORD /d 1 /f
```

---

## 🐧 Hardening Linux

### Controllo Utenti & Accesso
```bash
# Disabilita login root
sudo passwd -l root

# Imposta complessità password (/etc/security/pwquality.conf)
minlen = 14
dcredit = -1
ucredit = -1
lcredit = -1
ocredit = -1

# Aging della password (/etc/login.defs)
PASS_MAX_DAYS 90
PASS_MIN_DAYS 7
PASS_WARN_AGE 14

# Disabilita account inutilizzati
usermod -s /sbin/nologin username
```

### Hardening SSH (/etc/ssh/sshd_config)
```bash
# Disabilita login root
PermitRootLogin no

# Disabilita autenticazione password (usa chiavi)
PasswordAuthentication no
PubkeyAuthentication yes

# Cambia porta di default
Port 2222

# Limita utenti
AllowUsers admin operator

# Solo Protocollo 2
Protocol 2

# Disabilita password vuote
PermitEmptyPasswords no

# Timeout
ClientAliveInterval 300
ClientAliveCountMax 2

# Riavvia SSH
sudo systemctl restart sshd
```

### Firewall (iptables/nftables)
```bash
# Policy di default deny
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -P OUTPUT ACCEPT

# Permetti loopback
iptables -A INPUT -i lo -j ACCEPT

# Permetti established
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

# Permetti SSH (porta custom)
iptables -A INPUT -p tcp --dport 2222 -j ACCEPT

# Salva regole
iptables-save > /etc/iptables/rules.v4
```

### Permessi File
```bash
# Proteggi file critici
chmod 644 /etc/passwd
chmod 000 /etc/shadow
chmod 644 /etc/group
chmod 600 /etc/ssh/sshd_config
chmod 700 /root

# Trova file SUID/SGID
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null

# Rimuovi SUID non necessari
chmod u-s /usr/bin/unnecessary_binary
```

### Disabilita Servizi Non Utilizzati
```bash
# Lista servizi abilitati
systemctl list-unit-files --state=enabled

# Disabilita servizi non necessari
systemctl disable bluetooth
systemctl disable cups
systemctl disable avahi-daemon
systemctl disable rpcbind

# Disabilita IPv6 se non necessario
echo "net.ipv6.conf.all.disable_ipv6 = 1" >> /etc/sysctl.conf
```

### Hardening Kernel (/etc/sysctl.conf)
```bash
# Disabilita IP forwarding
net.ipv4.ip_forward = 0

# Disabilita source routing
net.ipv4.conf.all.accept_source_route = 0
net.ipv4.conf.default.accept_source_route = 0

# Abilita SYN cookies
net.ipv4.tcp_syncookies = 1

# Disabilita ICMP redirects
net.ipv4.conf.all.accept_redirects = 0
net.ipv4.conf.default.accept_redirects = 0
net.ipv4.conf.all.send_redirects = 0

# Abilita exec shield
kernel.randomize_va_space = 2

# Applica modifiche
sysctl -p
```

### Audit System (auditd)
```bash
# Installa auditd
apt install auditd

# Esempio regole (/etc/audit/rules.d/audit.rules)
-w /etc/passwd -p wa -k passwd_changes
-w /etc/shadow -p wa -k shadow_changes
-w /etc/sudoers -p wa -k sudoers_changes
-a always,exit -F arch=b64 -S execve -k command_execution

# Riavvia auditd
systemctl restart auditd
```

---

## 📊 Checklist Hardening Rapida

### Windows
```markdown
□ Disabilita servizi non necessari (SMBv1, Remote Registry)
□ Abilita Windows Firewall
□ Configura policy password forte
□ Abilita logging PowerShell
□ Configura audit policy
□ Abilita Windows Defender
□ Abilita protezione LSASS
□ Installa aggiornamenti di sicurezza
□ Rimuovi software non utilizzato
```

### Linux
```markdown
□ Disabilita login root
□ Configura hardening SSH
□ Abilita firewall (iptables/ufw)
□ Imposta permessi file corretti
□ Disabilita servizi non utilizzati
□ Configura hardening kernel
□ Abilita auditd
□ Installa aggiornamenti di sicurezza
□ Configura fail2ban
```

---

## 📚 Risorse

- [CIS Benchmarks](https://www.cisecurity.org/cis-benchmarks/)
- [Linee guida NIST](https://www.nist.gov/cyberframework)
- [Microsoft Security Baselines](https://docs.microsoft.com/en-us/windows/security/threat-protection/windows-security-baselines)

---

## 🔗 Cheatsheet Correlate

- [Incident Response](./Incident-Response.it.md)
- [Network Defense](./Network-Defense.it.md)

---

**Prossimo:** [Incident Response →](./Incident-Response.it.md)

**Torna all'Indice:** [🛡️ Blue Team](./README.it.md)
