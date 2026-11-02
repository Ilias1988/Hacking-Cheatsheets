# 🔒 Systemherding – hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Hardening.md) • [🇹🇷 Türkçe Sürüm](Hardening.tr.md) • [🇬🇷 Ελληνικά](Hardening.el.md)

```
██╗  ██╗ █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗██╗███╗   ██╗ ██████╗ 
██║  ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██║████╗  ██║██╔════╝ 
███████║███████║██████╔╝██║  ██║█████╗  ██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
██╔══██║██╔══██║██╔══██╗██║  ██║██╔══╝  ██║╚██╗██║██║██║╚██╗██║██║   ██║
██║  ██║██║  ██║██║  ██║██████╔╝███████╗██║ ╚████║██║██║ ╚████║╚██████╔╝
╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🖥️ Herding av Windows

### Kontosikkerhet
```powershell
# Deaktiver den lokale administratorkontoen
net user Administrator /active:no

# Gi administratorkontoen nytt navn
wmic useraccount where name='Administrator' rename 'NewAdminName'

# Angi en sterk passordpolicy (GPO eller secpol.msc)
# Minimumslengde: 14 tegn
# Kompleksitet: aktivert
# Maksimal alder: 60 dager

# Deaktiver gjestekontoen
net user Guest /active:no

# Begrens bruken av administratortokens (UAC)
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v EnableLUA /t REG_DWORD /d 1 /f
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v ConsentPromptBehaviorAdmin /t REG_DWORD /d 2 /f
```

### Deaktiver unødvendige tjenester

> **Versjonsmerknad:** `wmic` og `Get-WmiObject` er eldre grensesnitt, og tilgjengeligheten varierer med Windows-/PowerShell-versjon. I PowerShell kan `sc` også være et alias, mens eksemplene sikter til `sc.exe`. Kommandoene er beholdt uendret.
```powershell
# Deaktiver Remote Registry
sc config RemoteRegistry start= disabled

# Deaktiver Windows Remote Management (hvis det ikke trengs)
sc config WinRM start= disabled

# Deaktiver SMBv1
Disable-WindowsOptionalFeature -Online -FeatureName SMB1Protocol -NoRestart

# Deaktiver NetBIOS
Get-WmiObject Win32_NetworkAdapterConfiguration | where {$_.IPEnabled -eq $true} | 
    Invoke-WmiMethod -Name SetTcpipNetbios -ArgumentList 2
```

### Brannmurkonfigurasjon
```powershell
# Aktiver Windows-brannmuren
netsh advfirewall set allprofiles state on

# Blokker innkommende trafikk som standard
netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound

# Aktiver logging
netsh advfirewall set allprofiles logging filename %SystemRoot%\System32\LogFiles\Firewall\pfirewall.log
netsh advfirewall set allprofiles logging maxfilesize 4096
netsh advfirewall set allprofiles logging droppedconnections enable
```

### PowerShell-sikkerhet

> **Presisering:** Å sette `LanguageMode` i én økt er ikke en varig, systemomfattende sikkerhetsgrense. Planlagt håndheving krever systempolicy, for eksempel programkontroll. [Microsofts forklaring av språkmoduser](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_language_modes).
```powershell
# Aktiver Constrained Language Mode (begrenset språkmodus)
$ExecutionContext.SessionState.LanguageMode = "ConstrainedLanguage"

# Aktiver Script Block Logging (logging av skriptblokker)
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" /v EnableScriptBlockLogging /t REG_DWORD /d 1 /f

# Aktiver Module Logging (modullogging)
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" /v EnableModuleLogging /t REG_DWORD /d 1 /f
```

### Revisjonspolicy
```powershell
# Aktiver logging av kommandolinjen ved prosessopprettelse
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System\Audit" /v ProcessCreationIncludeCmdLine_Enabled /t REG_DWORD /d 1 /f

# Konfigurer revisjonspolicyer
auditpol /set /category:"Logon/Logoff" /success:enable /failure:enable
auditpol /set /category:"Account Logon" /success:enable /failure:enable
auditpol /set /category:"Account Management" /success:enable /failure:enable
auditpol /set /category:"Object Access" /success:enable /failure:enable
auditpol /set /category:"Privilege Use" /success:enable /failure:enable
```

### Windows Defender
```powershell
# Aktiver sanntidsbeskyttelse
Set-MpPreference -DisableRealtimeMonitoring $false

# Aktiver skybeskyttelse
Set-MpPreference -MAPSReporting Advanced
Set-MpPreference -SubmitSamplesConsent SendAllSamples

# Aktiver regler for Attack Surface Reduction (reduksjon av angrepsflate)
Set-MpPreference -AttackSurfaceReductionRules_Ids 56a863a9-875e-4185-98a7-b882c64b5ce5 -AttackSurfaceReductionRules_Actions Enabled
```

### LSASS-beskyttelse
```powershell
# Aktiver Credential Guard (krever TPM ifølge originalen)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v LsaCfgFlags /t REG_DWORD /d 1 /f

# Aktiver LSASS som PPL (Protected Process Light)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v RunAsPPL /t REG_DWORD /d 1 /f
```

---

## 🐧 Herding av Linux

### Bruker- og tilgangskontroll
```bash
# Deaktiver root-pålogging
sudo passwd -l root

# Angi passordkompleksitet (/etc/security/pwquality.conf)
minlen = 14
dcredit = -1
ucredit = -1
lcredit = -1
ocredit = -1

# Passordaldring (/etc/login.defs)
PASS_MAX_DAYS 90
PASS_MIN_DAYS 7
PASS_WARN_AGE 14

# Deaktiver ubrukte kontoer
usermod -s /sbin/nologin username
```

### SSH-herding (/etc/ssh/sshd_config)
```bash
# Deaktiver root-pålogging
PermitRootLogin no

# Deaktiver passordautentisering (bruk nøkler)
PasswordAuthentication no
PubkeyAuthentication yes

# Endre standardport
Port 2222

# Begrens tillatte brukere
AllowUsers admin operator

# Bare protokoll 2
Protocol 2

# Deaktiver tomme passord
PermitEmptyPasswords no

# Innstillinger for tidsavbrudd
ClientAliveInterval 300
ClientAliveCountMax 2

# Start SSH på nytt
sudo systemctl restart sshd
```

### Brannmur (iptables/nftables)
```bash
# Avvis som standard
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -P OUTPUT ACCEPT

# Tillat loopback
iptables -A INPUT -i lo -j ACCEPT

# Tillat etablerte tilkoblinger
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

# Tillat SSH (egendefinert port)
iptables -A INPUT -p tcp --dport 2222 -j ACCEPT

# Lagre regler
iptables-save > /etc/iptables/rules.v4
```

### Filtillatelser
```bash
# Sikre kritiske filer
chmod 644 /etc/passwd
chmod 000 /etc/shadow
chmod 644 /etc/group
chmod 600 /etc/ssh/sshd_config
chmod 700 /root

# Finn SUID-/SGID-filer
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null

# Fjern unødvendig SUID
chmod u-s /usr/bin/unnecessary_binary
```

### Deaktiver ubrukte tjenester
```bash
# Vis aktiverte tjenester
systemctl list-unit-files --state=enabled

# Deaktiver unødvendige tjenester
systemctl disable bluetooth
systemctl disable cups
systemctl disable avahi-daemon
systemctl disable rpcbind

# Deaktiver IPv6 hvis det ikke trengs
echo "net.ipv6.conf.all.disable_ipv6 = 1" >> /etc/sysctl.conf
```

### Kjerneherding (/etc/sysctl.conf)

> **Presisering:** `kernel.randomize_va_space = 2` aktiverer full adresserandomisering for prosesser (ASLR), inkludert heap-randomisering. Originalens kommentar «exec shield» er upresis. [Linux-kjernens dokumentasjon](https://docs.kernel.org/admin-guide/sysctl/kernel.html#randomize-va-space).
```bash
# Deaktiver IP-videresending
net.ipv4.ip_forward = 0

# Deaktiver kilderuting
net.ipv4.conf.all.accept_source_route = 0
net.ipv4.conf.default.accept_source_route = 0

# Aktiver SYN-cookies
net.ipv4.tcp_syncookies = 1

# Deaktiver ICMP-omdirigeringer
net.ipv4.conf.all.accept_redirects = 0
net.ipv4.conf.default.accept_redirects = 0
net.ipv4.conf.all.send_redirects = 0

# Aktiver exec shield (originalens betegnelse; se merknad om ASLR)
kernel.randomize_va_space = 2

# Ta endringene i bruk
sysctl -p
```

### Revisjonssystem (auditd)
```bash
# Installer auditd
apt install auditd

# Eksempelregler (/etc/audit/rules.d/audit.rules)
-w /etc/passwd -p wa -k passwd_changes
-w /etc/shadow -p wa -k shadow_changes
-w /etc/sudoers -p wa -k sudoers_changes
-a always,exit -F arch=b64 -S execve -k command_execution

# Start auditd på nytt
systemctl restart auditd
```

---

## 📊 Kort sjekkliste for herding

### Windows
```markdown
□ Deaktiver unødvendige tjenester (SMBv1, Remote Registry)
□ Aktiver Windows-brannmuren
□ Konfigurer en sterk passordpolicy
□ Aktiver PowerShell-logging
□ Konfigurer revisjonspolicy
□ Aktiver Windows Defender
□ Aktiver LSASS-beskyttelse
□ Installer sikkerhetsoppdateringer
□ Fjern ubrukt programvare
```

### Linux
```markdown
□ Deaktiver root-pålogging
□ Konfigurer SSH-herding
□ Aktiver brannmur (iptables/ufw)
□ Angi riktige filtillatelser
□ Deaktiver ubrukte tjenester
□ Konfigurer kjerneherding
□ Aktiver auditd
□ Installer sikkerhetsoppdateringer
□ Konfigurer fail2ban
```

---

## 📚 Ressurser

- [CIS Benchmarks](https://www.cisecurity.org/cis-benchmarks/)
- [NIST-retningslinjer](https://www.nist.gov/cyberframework)
- [Microsoft Security Baselines](https://docs.microsoft.com/en-us/windows/security/threat-protection/windows-security-baselines)

---

## 🔗 Relaterte hurtigreferanser

- [Hendelseshåndtering](Incident-Response.nb.md)
- Nettverksforsvar – kildefilen `Network-Defense.md` finnes ikke i denne kopien.

---

**Forrige:** ← Nettverksforsvar – kildefilen `Network-Defense.md` er ikke tilgjengelig.

**Tilbake til oversikten:** [🛡️ Blue Team](README.nb.md)
