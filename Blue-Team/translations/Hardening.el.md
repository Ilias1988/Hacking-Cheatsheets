# 🔒 Ενίσχυση ασφάλειας συστημάτων (System Hardening)

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Hardening.md) • [🇮🇹 Italiano](Hardening.it.md) • [🇹🇷 Türkçe](Hardening.tr.md)

[🛡️ Ελληνική επισκόπηση Blue Team](README.el.md)

```
██╗  ██╗ █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗██╗███╗   ██╗ ██████╗ 
██║  ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██║████╗  ██║██╔════╝ 
███████║███████║██████╔╝██║  ██║█████╗  ██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
██╔══██║██╔══██║██╔══██╗██║  ██║██╔══╝  ██║╚██╗██║██║██║╚██╗██║██║   ██║
██║  ██║██║  ██║██║  ██║██████╔╝███████╗██║ ╚████║██║██║ ╚████║╚██████╔╝
╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## Πριν ξεκινήσετε

Η **ενίσχυση ασφάλειας (hardening)** μειώνει τις δυνατότητες κατάχρησης ενός συστήματος: περιορίζει υπηρεσίες και προνόμια, προστατεύει λογαριασμούς και ενεργοποιεί καταγραφή. Η **baseline** είναι η συμφωνημένη αρχική ασφαλής ρύθμιση. Το **least privilege** σημαίνει ότι κάθε χρήστης ή υπηρεσία διαθέτει μόνο τα αναγκαία δικαιώματα.

Τα παραδείγματα δεν είναι καθολική συνταγή. Η απενεργοποίηση διαχείρισης, η αλλαγή SSH, το κλείδωμα λογαριασμού και οι κανόνες αποκλεισμού δικτύου μπορούν να διακόψουν νόμιμη πρόσβαση. Απαιτούνται έλεγχος εξαρτήσεων και διαθέσιμος τρόπος επαναφοράς. Ορισμένα πλαίσια συνδυάζουν εντολές με γραμμές αρχείων ρυθμίσεων· οι γραμμές όπως `PermitRootLogin no` ανήκουν στο αναφερόμενο αρχείο και δεν εκτελούνται ως εντολές κελύφους.

Οι πολιτικές κωδικών και τα δικαιώματα αρχείων πρέπει να συμφωνούν με τις απαιτήσεις και την τεκμηρίωση του συστήματος. Τα ενδεικτικά όρια ηλικίας κωδικών του πρωτοτύπου δεν αποτελούν γενική σύσταση. Εφαρμόζουμε αλλαγές σταδιακά και ελέγχουμε τη λειτουργία των υπηρεσιών.

## Περιεχόμενα

- [🖥️ Ενίσχυση ασφάλειας Windows](#section-01)
- [🐧 Ενίσχυση ασφάλειας Linux](#section-02)
- [📊 Σύντομη λίστα ελέγχου ενίσχυσης ασφάλειας](#section-03)
- [📚 Πηγές](#section-04)
- [🔗 Σχετικοί οδηγοί αναφοράς](#section-05)

<a id="section-01"></a>

## 🖥️ Ενίσχυση ασφάλειας Windows

### Ασφάλεια λογαριασμών
```powershell
# Απενεργοποίηση τοπικού λογαριασμού Administrator
net user Administrator /active:no

# Μετονομασία λογαριασμού Administrator
wmic useraccount where name='Administrator' rename 'NewAdminName'

# Ρύθμιση πολιτικής κωδικών μέσω GPO ή secpol.msc
# Ενδεικτικό ελάχιστο μήκος του πρωτοτύπου: 14 χαρακτήρες
# Ενδεικτική ρύθμιση πολυπλοκότητας: ενεργή
# Ενδεικτική μέγιστη ηλικία κωδικού του πρωτοτύπου: 60 ημέρες

# Απενεργοποίηση λογαριασμού Guest
net user Guest /active:no

# Περιορισμός χρήσης διακριτικού διαχειριστή μέσω UAC
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v EnableLUA /t REG_DWORD /d 1 /f
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v ConsentPromptBehaviorAdmin /t REG_DWORD /d 2 /f
```

### Απενεργοποίηση περιττών υπηρεσιών
```powershell
# Απενεργοποίηση υπηρεσίας Remote Registry
sc config RemoteRegistry start= disabled

# Απενεργοποίηση WinRM, εφόσον δεν απαιτείται
sc config WinRM start= disabled

# Απενεργοποίηση SMBv1
Disable-WindowsOptionalFeature -Online -FeatureName SMB1Protocol -NoRestart

# Απενεργοποίηση NetBIOS
Get-WmiObject Win32_NetworkAdapterConfiguration | where {$_.IPEnabled -eq $true} | 
    Invoke-WmiMethod -Name SetTcpipNetbios -ArgumentList 2
```

### Ρύθμιση τείχους προστασίας (Firewall)
```powershell
# Ενεργοποίηση τείχους προστασίας Windows
netsh advfirewall set allprofiles state on

# Προεπιλεγμένος αποκλεισμός εισερχόμενων συνδέσεων
netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound

# Ενεργοποίηση καταγραφής
netsh advfirewall set allprofiles logging filename %SystemRoot%\System32\LogFiles\Firewall\pfirewall.log
netsh advfirewall set allprofiles logging maxfilesize 4096
netsh advfirewall set allprofiles logging droppedconnections enable
```

### Ασφάλεια PowerShell

Η αλλαγή της λειτουργίας γλώσσας στην τρέχουσα συνεδρία δεν ισοδυναμεί με μόνιμη επιβολή πολιτικής σε όλο το σύστημα.

```powershell
# Περιορισμός δυνατοτήτων γλώσσας στην τρέχουσα συνεδρία PowerShell
$ExecutionContext.SessionState.LanguageMode = "ConstrainedLanguage"

# Ενεργοποίηση καταγραφής τμημάτων σεναρίων PowerShell
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" /v EnableScriptBlockLogging /t REG_DWORD /d 1 /f

# Ενεργοποίηση καταγραφής μονάδων PowerShell
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" /v EnableModuleLogging /t REG_DWORD /d 1 /f
```

### Πολιτική καταγραφής συμβάντων ελέγχου (Audit Policy)
```powershell
# Καταγραφή γραμμής εντολών στα συμβάντα δημιουργίας διεργασίας
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System\Audit" /v ProcessCreationIncludeCmdLine_Enabled /t REG_DWORD /d 1 /f

# Ρύθμιση πολιτικών καταγραφής ελέγχου
auditpol /set /category:"Logon/Logoff" /success:enable /failure:enable
auditpol /set /category:"Account Logon" /success:enable /failure:enable
auditpol /set /category:"Account Management" /success:enable /failure:enable
auditpol /set /category:"Object Access" /success:enable /failure:enable
auditpol /set /category:"Privilege Use" /success:enable /failure:enable
```

### Windows Defender
```powershell
# Ενεργοποίηση προστασίας σε πραγματικό χρόνο
Set-MpPreference -DisableRealtimeMonitoring $false

# Ενεργοποίηση προστασίας μέσω υπηρεσιών νέφους
Set-MpPreference -MAPSReporting Advanced
Set-MpPreference -SubmitSamplesConsent SendAllSamples

# Ενεργοποίηση κανόνα περιορισμού επιφάνειας επίθεσης (ASR)
Set-MpPreference -AttackSurfaceReductionRules_Ids 56a863a9-875e-4185-98a7-b882c64b5ce5 -AttackSurfaceReductionRules_Actions Enabled
```

### Προστασία διεργασίας LSASS
```powershell
# Παράδειγμα ρύθμισης Credential Guard — απαιτεί έλεγχο συνολικών προϋποθέσεων
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v LsaCfgFlags /t REG_DWORD /d 1 /f

# Ενεργοποίηση προστασίας διεργασίας LSASS ως PPL
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v RunAsPPL /t REG_DWORD /d 1 /f
```

---

<a id="section-02"></a>

## 🐧 Ενίσχυση ασφάλειας Linux

### Έλεγχος χρηστών και πρόσβασης
```bash
# Περιορισμός πρόσβασης root
sudo passwd -l root

# Ρυθμίσεις κωδικών στο /etc/security/pwquality.conf
minlen = 14
dcredit = -1
ucredit = -1
lcredit = -1
ocredit = -1

# Ρυθμίσεις ηλικίας κωδικών στο /etc/login.defs
PASS_MAX_DAYS 90
PASS_MIN_DAYS 7
PASS_WARN_AGE 14

# Περιορισμός διαδραστικής πρόσβασης λογαριασμών που δεν χρησιμοποιούνται
usermod -s /sbin/nologin username
```

### Ενίσχυση ασφάλειας SSH (/etc/ssh/sshd_config)
```bash
# Περιορισμός πρόσβασης root
PermitRootLogin no

# Απενεργοποίηση ταυτοποίησης SSH με κωδικό και χρήση κλειδιών
PasswordAuthentication no
PubkeyAuthentication yes

# Αλλαγή προεπιλεγμένης θύρας
Port 2222

# Περιορισμός επιτρεπόμενων χρηστών
AllowUsers admin operator

# Παλιότερη ρύθμιση Protocol 2 — ελέγξτε την υποστήριξη στην έκδοση OpenSSH
Protocol 2

# Απαγόρευση κενών κωδικών
PermitEmptyPasswords no

# Ρυθμίσεις χρονικών ορίων σύνδεσης
ClientAliveInterval 300
ClientAliveCountMax 2

# Επανεκκίνηση υπηρεσίας SSH
sudo systemctl restart sshd
```

### Τείχος προστασίας — παραδείγματα iptables
```bash
# Πολιτική προεπιλεγμένου αποκλεισμού
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -P OUTPUT ACCEPT

# Αποδοχή επικοινωνίας εσωτερικού βρόχου (Loopback)
iptables -A INPUT -i lo -j ACCEPT

# Αποδοχή συνδέσεων που έχουν ήδη εγκαθιδρυθεί και σχετικής κίνησης
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

# Αποδοχή SSH στην προσαρμοσμένη θύρα
iptables -A INPUT -p tcp --dport 2222 -j ACCEPT

# Αποθήκευση κανόνων
iptables-save > /etc/iptables/rules.v4
```

### Δικαιώματα αρχείων

Τα δικαιώματα ευαίσθητων αρχείων, όπως το `/etc/shadow`, πρέπει να συμφωνούν με τη διανομή. Η τιμή `000` του παραδείγματος δεν αποτελεί καθολική προεπιλογή για κάθε σύστημα.

```bash
# Ρυθμίσεις δικαιωμάτων κρίσιμων αρχείων — απαιτείται έλεγχος ανά διανομή
chmod 644 /etc/passwd
chmod 000 /etc/shadow
chmod 644 /etc/group
chmod 600 /etc/ssh/sshd_config
chmod 700 /root

# Εντοπισμός αρχείων με SUID/SGID
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null

# Αφαίρεση μη απαραίτητου δικαιώματος SUID
chmod u-s /usr/bin/unnecessary_binary
```

### Απενεργοποίηση υπηρεσιών που δεν χρησιμοποιούνται
```bash
# Εμφάνιση ενεργοποιημένων υπηρεσιών
systemctl list-unit-files --state=enabled

# Απενεργοποίηση περιττών υπηρεσιών
systemctl disable bluetooth
systemctl disable cups
systemctl disable avahi-daemon
systemctl disable rpcbind

# Απενεργοποίηση IPv6 μόνο εφόσον δεν απαιτείται
echo "net.ipv6.conf.all.disable_ipv6 = 1" >> /etc/sysctl.conf
```

### Ενίσχυση ασφάλειας πυρήνα (/etc/sysctl.conf)

Το `kernel.randomize_va_space` αφορά τυχαιοποίηση θέσεων μνήμης (ASLR), παρά το σχόλιο «exec shield» του πρωτοτύπου. Οι υπόλοιπες ρυθμίσεις επιλέγονται με βάση τον ρόλο του συστήματος.

```bash
# Απενεργοποίηση προώθησης πακέτων IP
net.ipv4.ip_forward = 0

# Απόρριψη δρομολόγησης που καθορίζεται από την πηγή
net.ipv4.conf.all.accept_source_route = 0
net.ipv4.conf.default.accept_source_route = 0

# Ενεργοποίηση μηχανισμού SYN cookies
net.ipv4.tcp_syncookies = 1

# Απενεργοποίηση ανακατευθύνσεων ICMP
net.ipv4.conf.all.accept_redirects = 0
net.ipv4.conf.default.accept_redirects = 0
net.ipv4.conf.all.send_redirects = 0

# Ενεργοποίηση τυχαιοποίησης θέσεων μνήμης (ASLR)
kernel.randomize_va_space = 2

# Εφαρμογή αλλαγών
sysctl -p
```

### Σύστημα καταγραφής ελέγχου auditd
```bash
# Εγκατάσταση auditd
apt install auditd

# Ενδεικτικοί κανόνες στο /etc/audit/rules.d/audit.rules
-w /etc/passwd -p wa -k passwd_changes
-w /etc/shadow -p wa -k shadow_changes
-w /etc/sudoers -p wa -k sudoers_changes
-a always,exit -F arch=b64 -S execve -k command_execution

# Επανεκκίνηση auditd
systemctl restart auditd
```

---

<a id="section-03"></a>

## 📊 Σύντομη λίστα ελέγχου ενίσχυσης ασφάλειας

### Windows
```markdown
□ Απενεργοποίηση περιττών υπηρεσιών, όπως SMBv1 και Remote Registry
□ Ενεργοποίηση τείχους προστασίας Windows
□ Ρύθμιση κατάλληλης πολιτικής κωδικών
□ Ενεργοποίηση καταγραφής PowerShell
□ Ρύθμιση πολιτικής καταγραφής ελέγχου
□ Ενεργοποίηση Windows Defender
□ Ενεργοποίηση προστασίας LSASS
□ Εγκατάσταση ενημερώσεων ασφαλείας
□ Αφαίρεση λογισμικού που δεν χρησιμοποιείται
```

### Linux
```markdown
□ Περιορισμός πρόσβασης root
□ Ρύθμιση ενίσχυσης ασφάλειας SSH
□ Ενεργοποίηση τείχους προστασίας με iptables ή ufw
□ Κατάλληλα δικαιώματα αρχείων
□ Απενεργοποίηση υπηρεσιών που δεν χρησιμοποιούνται
□ Ρυθμίσεις ενίσχυσης ασφάλειας πυρήνα
□ Ενεργοποίηση auditd
□ Εγκατάσταση ενημερώσεων ασφαλείας
□ Ρύθμιση fail2ban
```

---

<a id="section-04"></a>

## 📚 Πηγές

- [CIS Benchmarks](https://www.cisecurity.org/cis-benchmarks/)
- [NIST Guidelines](https://www.nist.gov/cyberframework)
- [Microsoft Security Baselines](https://docs.microsoft.com/en-us/windows/security/threat-protection/windows-security-baselines)

---

<a id="section-05"></a>

## 🔗 Σχετικοί οδηγοί αναφοράς

- [Αντιμετώπιση περιστατικών](Incident-Response.el.md)
- Προστασία δικτύου — δεν υπάρχει ακόμη ξεχωριστός οδηγός

---

**Προηγούμενο θέμα:** [← Προληπτική αναζήτηση απειλών](Threat-Hunting.el.md)

**Επόμενο θέμα:** [Κανόνες Sigma →](Sigma-Rules.el.md)

**Επιστροφή στην επισκόπηση:** [🛡️ Blue Team](README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
