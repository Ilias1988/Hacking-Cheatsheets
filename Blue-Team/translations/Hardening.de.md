# 🔒 Spickzettel zur Systemhärtung

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./Hardening.tr.md) • [🇬🇷 Ελληνικά](./Hardening.el.md)

```
██╗  ██╗ █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗██╗███╗   ██╗ ██████╗ 
██║  ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██║████╗  ██║██╔════╝ 
███████║███████║██████╔╝██║  ██║█████╗  ██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
██╔══██║██╔══██║██╔══██╗██║  ██║██╔══╝  ██║╚██╗██║██║██║╚██╗██║██║   ██║
██║  ██║██║  ██║██║  ██║██████╔╝███████╗██║ ╚████║██║██║ ╚████║╚██████╔╝
╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

> **Versionshinweise:** `wmic` ist veraltet und kann fehlen. Die direkte Zuweisung von `ConstrainedLanguage` betrifft die aktuelle Sitzung; eine belastbare Erzwingung erfolgt über systemweite Anwendungskontrolle. Die gezeigte einzelne Module-Logging-Einstellung ersetzt keine vollständige Richtlinienkonfiguration. [PowerShell und Anwendungskontrolle](https://learn.microsoft.com/en-us/powershell/scripting/learn/application-control?view=powershell-7.4).

> **Korrekturen zu den Originalbeispielen:** Credential Guard setzt unterstützte Virtualisierungssicherheit und weitere Plattformvoraussetzungen voraus; der einzelne Registry-Wert ist keine vollständige Einrichtungsanleitung. Ein TPM wird für zusätzlichen Schutz empfohlen und ist keine pauschale Pflicht. `kernel.randomize_va_space = 2` steuert ASLR (Adressraumrandomisierung), nicht „Exec Shield“. [Credential Guard](https://learn.microsoft.com/en-us/windows/security/identity-protection/credential-guard/), [Linux-Kernel zu sysctl](https://docs.kernel.org/admin-guide/sysctl/kernel.html).

> **Kontext:** Die Kennwortlaufzeiten sind Beispiele, keine allgemeingültigen Vorgaben. Das Sperren des Root-Kennworts verhindert nicht automatisch Schlüsselanmeldungen. `Protocol 2` ist eine Altversionsangabe. Dienstnamen, Audit-Kategorien und Dateiberechtigungen unterscheiden sich je nach System. Die Firewall-Beispiele können bestehende Verwaltungsverbindungen unterbrechen; `systemctl disable` stoppt laufende Dienste nicht. In PowerShell kann `sc` ein Alias und `%SystemRoot%` eine cmd-spezifische Schreibweise sein. Die Originalbefehle wurden weder geändert noch ausgeführt.

## 🖥️ Windows härten

### Kontosicherheit
```powershell
# Lokales Administratorkonto deaktivieren
net user Administrator /active:no

# Administratorkonto umbenennen
wmic useraccount where name='Administrator' rename 'NewAdminName'

# Sichere Kennwortrichtlinie festlegen (GPO oder secpol.msc)
# Mindestlänge: 14 Zeichen
# Komplexität: aktiviert
# Maximales Alter: 60 Tage

# Gastkonto deaktivieren
net user Guest /active:no

# Verwendung von Administratortokens einschränken (UAC)
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v EnableLUA /t REG_DWORD /d 1 /f
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v ConsentPromptBehaviorAdmin /t REG_DWORD /d 2 /f
```

### Nicht benötigte Dienste deaktivieren
```powershell
# Remote Registry deaktivieren
sc config RemoteRegistry start= disabled

# Windows Remote Management deaktivieren (falls nicht benötigt)
sc config WinRM start= disabled

# SMBv1 deaktivieren
Disable-WindowsOptionalFeature -Online -FeatureName SMB1Protocol -NoRestart

# NetBIOS deaktivieren
Get-WmiObject Win32_NetworkAdapterConfiguration | where {$_.IPEnabled -eq $true} | 
    Invoke-WmiMethod -Name SetTcpipNetbios -ArgumentList 2
```

### Firewall-Konfiguration
```powershell
# Windows-Firewall aktivieren
netsh advfirewall set allprofiles state on

# Eingehende Verbindungen standardmäßig blockieren
netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound

# Protokollierung aktivieren
netsh advfirewall set allprofiles logging filename %SystemRoot%\System32\LogFiles\Firewall\pfirewall.log
netsh advfirewall set allprofiles logging maxfilesize 4096
netsh advfirewall set allprofiles logging droppedconnections enable
```

### PowerShell absichern
```powershell
# Constrained Language Mode aktivieren
$ExecutionContext.SessionState.LanguageMode = "ConstrainedLanguage"

# Script Block Logging aktivieren
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" /v EnableScriptBlockLogging /t REG_DWORD /d 1 /f

# Modulprotokollierung aktivieren
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" /v EnableModuleLogging /t REG_DWORD /d 1 /f
```

### Überwachungsrichtlinie
```powershell
# Befehlszeilen bei der Prozesserstellung protokollieren
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System\Audit" /v ProcessCreationIncludeCmdLine_Enabled /t REG_DWORD /d 1 /f

# Überwachungsrichtlinien konfigurieren
auditpol /set /category:"Logon/Logoff" /success:enable /failure:enable
auditpol /set /category:"Account Logon" /success:enable /failure:enable
auditpol /set /category:"Account Management" /success:enable /failure:enable
auditpol /set /category:"Object Access" /success:enable /failure:enable
auditpol /set /category:"Privilege Use" /success:enable /failure:enable
```

### Windows Defender
```powershell
# Echtzeitschutz aktivieren
Set-MpPreference -DisableRealtimeMonitoring $false

# Cloudschutz aktivieren
Set-MpPreference -MAPSReporting Advanced
Set-MpPreference -SubmitSamplesConsent SendAllSamples

# Regeln zur Verringerung der Angriffsfläche aktivieren (ASR)
Set-MpPreference -AttackSurfaceReductionRules_Ids 56a863a9-875e-4185-98a7-b882c64b5ce5 -AttackSurfaceReductionRules_Actions Enabled
```

### LSASS-Schutz
```powershell
# Credential Guard aktivieren (laut Original mit TPM)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v LsaCfgFlags /t REG_DWORD /d 1 /f

# LSASS als PPL aktivieren (Protected Process Light)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v RunAsPPL /t REG_DWORD /d 1 /f
```

---

## 🐧 Linux härten

### Benutzer- und Zugriffskontrolle
```bash
# Root-Anmeldung deaktivieren
sudo passwd -l root

# Kennwortkomplexität festlegen (/etc/security/pwquality.conf)
minlen = 14
dcredit = -1
ucredit = -1
lcredit = -1
ocredit = -1

# Kennwortalter festlegen (/etc/login.defs)
PASS_MAX_DAYS 90
PASS_MIN_DAYS 7
PASS_WARN_AGE 14

# Nicht verwendete Konten deaktivieren
usermod -s /sbin/nologin username
```

### SSH härten (/etc/ssh/sshd_config)
```bash
# Root-Anmeldung deaktivieren
PermitRootLogin no

# Kennwortanmeldung deaktivieren (Schlüssel verwenden)
PasswordAuthentication no
PubkeyAuthentication yes

# Standardport ändern
Port 2222

# Zugelassene Benutzer einschränken
AllowUsers admin operator

# Nur Protokollversion 2
Protocol 2

# Leere Kennwörter verbieten
PermitEmptyPasswords no

# Zeitlimits konfigurieren
ClientAliveInterval 300
ClientAliveCountMax 2

# SSH neu starten
sudo systemctl restart sshd
```

### Firewall (iptables/nftables)
```bash
# Standardmäßig verweigern
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -P OUTPUT ACCEPT

# Loopback erlauben
iptables -A INPUT -i lo -j ACCEPT

# Bestehende Verbindungen erlauben
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

# SSH erlauben (angepasster Port)
iptables -A INPUT -p tcp --dport 2222 -j ACCEPT

# Regeln speichern
iptables-save > /etc/iptables/rules.v4
```

### Dateiberechtigungen
```bash
# Kritische Dateien absichern
chmod 644 /etc/passwd
chmod 000 /etc/shadow
chmod 644 /etc/group
chmod 600 /etc/ssh/sshd_config
chmod 700 /root

# SUID-/SGID-Dateien finden
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null

# Nicht benötigtes SUID-Bit entfernen
chmod u-s /usr/bin/unnecessary_binary
```

### Nicht verwendete Dienste deaktivieren
```bash
# Aktivierte Dienste auflisten
systemctl list-unit-files --state=enabled

# Nicht benötigte Dienste deaktivieren
systemctl disable bluetooth
systemctl disable cups
systemctl disable avahi-daemon
systemctl disable rpcbind

# IPv6 deaktivieren, falls nicht benötigt
echo "net.ipv6.conf.all.disable_ipv6 = 1" >> /etc/sysctl.conf
```

### Kernel härten (/etc/sysctl.conf)
```bash
# IP-Weiterleitung deaktivieren
net.ipv4.ip_forward = 0

# Source Routing deaktivieren
net.ipv4.conf.all.accept_source_route = 0
net.ipv4.conf.default.accept_source_route = 0

# SYN-Cookies aktivieren
net.ipv4.tcp_syncookies = 1

# ICMP-Weiterleitungsnachrichten deaktivieren
net.ipv4.conf.all.accept_redirects = 0
net.ipv4.conf.default.accept_redirects = 0
net.ipv4.conf.all.send_redirects = 0

# Exec Shield aktivieren (Bezeichnung im Original; siehe Hinweis)
kernel.randomize_va_space = 2

# Änderungen übernehmen
sysctl -p
```

### Überwachungssystem (auditd)
```bash
# auditd installieren
apt install auditd

# Beispielregeln (/etc/audit/rules.d/audit.rules)
-w /etc/passwd -p wa -k passwd_changes
-w /etc/shadow -p wa -k shadow_changes
-w /etc/sudoers -p wa -k sudoers_changes
-a always,exit -F arch=b64 -S execve -k command_execution

# auditd neu starten
systemctl restart auditd
```

---

## 📊 Kurze Checkliste zur Systemhärtung

### Windows
```markdown
□ Nicht benötigte Dienste deaktivieren (SMBv1, Remote Registry)
□ Windows-Firewall aktivieren
□ Sichere Kennwortrichtlinie konfigurieren
□ PowerShell-Protokollierung aktivieren
□ Überwachungsrichtlinie konfigurieren
□ Windows Defender aktivieren
□ LSASS-Schutz aktivieren
□ Sicherheitsupdates installieren
□ Nicht verwendete Software entfernen
```

### Linux
```markdown
□ Root-Anmeldung deaktivieren
□ SSH-Härtung konfigurieren
□ Firewall aktivieren (iptables/ufw)
□ Passende Dateiberechtigungen setzen
□ Nicht verwendete Dienste deaktivieren
□ Kernel-Härtung konfigurieren
□ auditd aktivieren
□ Sicherheitsupdates installieren
□ fail2ban konfigurieren
```

---

## 📚 Weiterführende Quellen

- [CIS Benchmarks](https://www.cisecurity.org/cis-benchmarks/)
- [NIST-Richtlinien](https://www.nist.gov/cyberframework)
- [Microsoft-Sicherheitsbaselines](https://docs.microsoft.com/en-us/windows/security/threat-protection/windows-security-baselines)

---

## 🔗 Verwandte Spickzettel

- [Incident Response (Vorfallbearbeitung)](./Incident-Response.de.md)
- Netzwerkverteidigung (im englischen Original verlinkt; Quelldatei fehlt)

---

**Zurück:** ← Netzwerkverteidigung (im englischen Original verlinkt; Quelldatei fehlt)

**Zurück zur Übersicht:** [🛡️ Blue Team](./README.de.md)
