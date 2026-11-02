# 🐛 GDB — Οδηγός αποσφαλμάτωσης GNU

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
   ██████╗ ██████╗ ██████╗ 
  ██╔════╝ ██╔══██╗██╔══██╗
  ██║  ███╗██║  ██║██████╔╝
  ██║   ██║██║  ██║██╔══██╗
  ╚██████╔╝██████╔╝██████╔╝
   ╚═════╝ ╚═════╝ ╚═════╝ 
    Αποσφαλματωτής GNU
```

<p align="center">
  <img src="https://img.shields.io/badge/GDB-Debugger-blue?style=for-the-badge" alt="GDB">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Exploit_Dev-red?style=for-the-badge" alt="Exploit">
</p>

---

## Πριν ξεκινήσετε

Το **GDB** είναι **debugger** — εργαλείο αποσφαλμάτωσης που εξετάζει ένα πρόγραμμα κατά την εκτέλεση ή από αποθηκευμένη κατάστασή του. **Breakpoint** είναι σημείο παύσης, **watchpoint** παρακολούθηση αλλαγής ή πρόσβασης δεδομένων, **register** καταχωρητής του επεξεργαστή και **stack frame** πλαίσιο μιας κλήσης στη στοίβα.

Διακρίνετε τις εντολές shell από τις εντολές μέσα στο GDB. Τα σύμβολα (gdb), gef➤, peda➤ και pwndbg> δείχνουν διαφορετικά περιβάλλοντα ή πρόσθετα και δεν πληκτρολογούνται ως μέρος της εντολής. Οι αριθμητικές διευθύνσεις είναι ενδεικτικές και αλλάζουν ανά εκτέλεση και σύστημα.

Για αρχή, επιλέξτε ένα μικρό πρόγραμμα που έχετε μεταγλωττίσει για εξάσκηση. Κάντε παύση σε γνωστή συνάρτηση, προχωρήστε λίγα βήματα και συγκρίνετε καταχωρητές και μνήμη. Η εκτέλεση, η σύνδεση σε διεργασία και οι εντολές εγγραφής ή κλήσης συνάρτησης επηρεάζουν το πρόγραμμα· εδώ εξετάζονται ως κείμενο.

## 📋 Περιεχόμενα

- [Τι είναι το GDB](#section-01)
- [Εγκατάσταση και αρχική ρύθμιση](#section-02)
- [Βασική χρήση](#section-03)
- [Breakpoints — σημεία διακοπής](#section-04)
- [Έλεγχος εκτέλεσης](#section-05)
- [Εξέταση μνήμης](#section-06)
- [Καταχωρητές](#section-07)
- [Ανάλυση στοίβας](#section-08)
- [GEF/PEDA/pwndbg](#section-09)
- [Ανάπτυξη αποδείξεων εκμετάλλευσης](#section-10)
- [Γρήγορη αναφορά](#section-11)
- [Πηγές](#section-12)

---

<a id="section-01"></a>

## 🎯 Τι είναι το GDB

Το **GDB (GNU Debugger)** είναι ευρέως χρησιμοποιούμενος debugger, ιδιαίτερα στο Linux. Βοηθά σε:

- 🐛 **Αποσφαλμάτωση** — εντοπισμό και διόρθωση προβλημάτων.
- 🔍 **Αντίστροφη μηχανική** — κατανόηση συμπεριφοράς εκτελέσιμων.
- 💥 **Εργαστηριακή διερεύνηση** — υπερχειλίσεις buffer και ROP.
- 📊 **Ανάλυση εκτελέσιμων** — εξέταση της ροής εκτέλεσης.
- 🧪 **Ασκήσεις CTF** — προκλήσεις RE και pwn.

### Υποστηριζόμενες αρχιτεκτονικές

| Αρχιτεκτονική | Σημειώσεις |
| -------------- | ------- |
| x86 | 32-bit Intel |
| x86-64 | 64-bit Intel/AMD |
| ARM | 32-bit ARM |
| ARM64 | 64-bit ARM |
| MIPS | Επεξεργαστές MIPS |
| PowerPC | Επεξεργαστές PowerPC |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση και αρχική ρύθμιση

> **Ξεχωριστά πρόσθετα:** GEF, PEDA και pwndbg δεν είναι ενσωματωμένες εντολές GDB. Οι εκδόσεις τους απαιτούν συμβατή Python/GDB και η ταυτόχρονη φόρτωση στο ίδιο αρχείο ρυθμίσεων μπορεί να συγκρούεται. Οι γραμμές λήψης εγκαταστατών είναι παραδείγματα του πρωτοτύπου και δεν εκτελούνται εδώ.


### Εγκατάσταση GDB

```bash
# Debian/Ubuntu/Kali
sudo apt install gdb

# Fedora/RHEL
sudo dnf install gdb

# Arch Linux
sudo pacman -S gdb

# macOS: επιλογή LLDB ή εγκατάσταση GDB με τις αναγκαίες ρυθμίσεις
brew install gdb
```

### Εγκατάσταση GEF

```bash
# GEF — επιλογή του αγγλικού πρωτοτύπου
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Εναλλακτική χειροκίνητη ρύθμιση
wget -O ~/.gdbinit-gef.py -q https://gef.blah.cat/py
echo source ~/.gdbinit-gef.py >> ~/.gdbinit
```

### Εγκατάσταση PEDA

```bash
git clone https://github.com/longld/peda.git ~/peda
echo "source ~/peda/peda.py" >> ~/.gdbinit
```

### Εγκατάσταση pwndbg

```bash
git clone https://github.com/pwndbg/pwndbg
cd pwndbg
./setup.sh
```

### Επιβεβαίωση εγκατάστασης

```bash
gdb --version
gdb -q
(gdb) quit
```

---

<a id="section-03"></a>

## 💻 Βασική χρήση

### Εκκίνηση GDB

> **Προσοχή στη γραμμή attach:** Το αρχικό gdb attach PID δεν ισοδυναμεί γενικά με την εντολή attach μέσα στο GDB. Το -p και η εντολή του ήδη ανοικτού debugger είναι διαφορετικοί τρόποι σύνδεσης. Χρειάζονται και τα κατάλληλα δικαιώματα συστήματος. Βλ. [σύνδεση σε διεργασία](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Attach.html).


```bash
# Αποσφαλμάτωση εκτελέσιμου
gdb ./program

# Αποσφαλμάτωση με ορίσματα
gdb --args ./program arg1 arg2

# Σύνδεση σε διεργασία που εκτελείται
gdb -p <PID>
gdb attach <PID>

# Εκκίνηση χωρίς το αρχικό γραφικό μήνυμα
gdb -q ./program

# Εκτέλεση εντολών από αρχείο
gdb -x commands.gdb ./program
```

### Βασικές εντολές

| Εντολή | Σύντομη μορφή | Περιγραφή |
| --------- | ------- | ------------- |
| `run` | `r` | Εκκίνηση προγράμματος |
| `run args` | `r args` | Εκτέλεση με ορίσματα |
| `continue` | `c` | Συνέχιση εκτέλεσης |
| `quit` | `q` | Έξοδος από GDB |
| `help` | `h` | Εμφάνιση βοήθειας |
| `help <cmd>` | `h <cmd>` | Βοήθεια συγκεκριμένης εντολής |

### Εντολές αρχείων

```bash
# Φόρτωση εκτελέσιμου
(gdb) file ./program

# Φόρτωση συμβόλων
(gdb) symbol-file ./program.debug

# Φόρτωση αρχείου κατάστασης διεργασίας
(gdb) core-file core

# Ορισμός καταλόγου εργασίας
(gdb) cd /path/to/dir

# Ορισμός ορισμάτων
(gdb) set args arg1 arg2
```

---

<a id="section-04"></a>

## 🔴 Breakpoints — σημεία διακοπής

### Ορισμός σημείων διακοπής

```bash
# Διακοπή σε συνάρτηση
(gdb) break main
(gdb) b main

# Διακοπή σε διεύθυνση
(gdb) break *0x08048456
(gdb) b *0x08048456

# Διακοπή σε γραμμή πηγαίου κώδικα
(gdb) break file.c:25
(gdb) b 25

# Διακοπή σε μετατόπιση από συνάρτηση
(gdb) break *main+48

# Προσωρινό σημείο διακοπής που διαγράφεται αυτόματα
(gdb) tbreak main
```

### Σημεία διακοπής με συνθήκη

```bash
# Διακοπή όταν ισχύει η συνθήκη
(gdb) break main if argc > 2
(gdb) break *0x08048456 if $eax == 0

# Προσθήκη συνθήκης σε υπάρχον σημείο διακοπής
(gdb) condition 1 $eax == 0

# Αφαίρεση συνθήκης
(gdb) condition 1
```

### Διαχείριση σημείων διακοπής

```bash
# Λίστα σημείων διακοπής
(gdb) info breakpoints
(gdb) info b

# Διαγραφή σημείου διακοπής
(gdb) delete 1
(gdb) d 1

# Διαγραφή όλων των σημείων διακοπής
(gdb) delete
(gdb) d

# Απενεργοποίηση σημείου διακοπής
(gdb) disable 1
(gdb) dis 1

# Ενεργοποίηση σημείου διακοπής
(gdb) enable 1
(gdb) en 1

# Απενεργοποίηση όλων
(gdb) disable

# Ενεργοποίηση όλων
(gdb) enable
```

### Watchpoints — παρακολούθηση δεδομένων

```bash
# Παρακολούθηση αλλαγών μεταβλητής
(gdb) watch variable
(gdb) watch *0x08048456

# Παρακολούθηση αναγνώσεων
(gdb) rwatch variable

# Παρακολούθηση αναγνώσεων ή εγγραφών
(gdb) awatch variable

# Παρακολούθηση τιμής έκφρασης
(gdb) watch ($eax > 10)
```

### Catchpoints — διακοπή σε συμβάντα

```bash
# Διακοπή σε κλήσεις συστήματος
(gdb) catch syscall

# Διακοπή σε συγκεκριμένη κλήση συστήματος
(gdb) catch syscall write

# Διακοπή σε σήματα
(gdb) catch signal SIGSEGV

# Διακοπή σε εξαιρέσεις
(gdb) catch throw
(gdb) catch catch
```

---

<a id="section-05"></a>

## ▶️ Έλεγχος εκτέλεσης

### Βηματική εκτέλεση

```bash
# Μία εντολή, με είσοδο στις κλήσεις
(gdb) stepi
(gdb) si

# Μία γραμμή κώδικα, με είσοδο στις κλήσεις
(gdb) step
(gdb) s

# Μία εντολή χωρίς είσοδο σε κλήση
(gdb) nexti
(gdb) ni

# Μία γραμμή χωρίς είσοδο σε κλήση
(gdb) next
(gdb) n

# Συνέχιση μέχρι επιστροφή
(gdb) finish
(gdb) fin

# Συνέχιση μέχρι τη διεύθυνση
(gdb) until *0x08048456
(gdb) u *0x08048456
```

### Εκτέλεση

> **Το continue 5 δεν παραλείπει γενικά πέντε διαφορετικά breakpoints:** Ο αριθμός αφορά αγνοήσεις του σημείου όπου έχει σταματήσει η διεργασία, υπό τις προϋποθέσεις της εντολής. Το jump αλλάζει την επόμενη θέση εκτέλεσης χωρίς να προσαρμόζει αυτόματα ορίσματα και στοίβα. Βλ. [συνέχιση και βηματισμό](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Continuing-and-Stepping.html).


```bash
# Εκκίνηση ή επανεκκίνηση προγράμματος
(gdb) run
(gdb) r

# Συνέχιση μετά το σημείο διακοπής
(gdb) continue
(gdb) c

# Εκτέλεση μέχρι το επόμενο σημείο διακοπής
(gdb) continue

# Αριθμός αγνόησης για το σχετικό breakpoint· δείτε διευκρίνιση
(gdb) continue 5

# Αλλαγή ροής εκτέλεσης προς διεύθυνση· δεν αποκαθιστά την κατάσταση προγράμματος
(gdb) jump *0x08048456
```

### Έλεγχος διεργασιών

```bash
# Τερματισμός τρέχουσας διεργασίας
(gdb) kill

# Αποσύνδεση από διεργασία
(gdb) detach

# Σύνδεση σε διεργασία
(gdb) attach <PID>

# Επιλογή παρακολούθησης γονικής ή θυγατρικής διεργασίας
(gdb) set follow-fork-mode child
(gdb) set follow-fork-mode parent
```

---

<a id="section-06"></a>

## 🔍 Εξέταση μνήμης

> **Μορφή και μέγεθος:** Στο x, το μέγεθος μονάδας μπορεί να προέρχεται από προηγούμενη εντολή αν δεν δοθεί ρητά. Το print/s ερμηνεύει κατάλληλους δείκτες ή πίνακες ως συμβολοσειρές, ενώ το x/s εξετάζει μνήμη στη δοσμένη διεύθυνση. Βλ. [εξέταση μνήμης GDB](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Memory.html).


### Εντολή print

```bash
# Εμφάνιση μεταβλητής
(gdb) print variable
(gdb) p variable

# Εμφάνιση σε δεκαεξαδική μορφή
(gdb) print/x variable
(gdb) p/x $eax

# Εμφάνιση σε δυαδική μορφή
(gdb) p/t variable

# Εμφάνιση σε δεκαδική μορφή
(gdb) p/d variable

# Εμφάνιση ως χαρακτήρα
(gdb) p/c $al

# Εμφάνιση ως συμβολοσειρά όπου το επιτρέπει ο τύπος της τιμής
(gdb) p/s pointer

# Εμφάνιση πίνακα
(gdb) p *array@10     # 10 στοιχεία
```

### Εντολή εξέτασης x

```bash
# Σύνταξη: x/NFU address
# N = πλήθος, F = μορφή, U = μέγεθος μονάδας

# Μορφές: x(hex), d(decimal), u(unsigned), o(octal),
# t(binary), c(char), s(string), i(instruction) — δυαδικό, χαρακτήρας, συμβολοσειρά, εντολή
# Μονάδες: b(byte), h(half=2), w(word=4), g(giant=8)

# Εξέταση μνήμης
(gdb) x/10x $esp           # 10 τιμές σε δεκαεξαδική μορφή από ESP, με το τρέχον μέγεθος μονάδας
(gdb) x/20wx 0x08048456    # 20 λέξεις σε δεκαεξαδική μορφή
(gdb) x/s 0x08048456       # Συμβολοσειρά στη διεύθυνση
(gdb) x/10i $eip           # 10 εντολές από τη διεύθυνση EIP
(gdb) x/10gx $rsp          # 10 μονάδες των 8 bytes από RSP

# Συνήθη μοτίβα
(gdb) x/100x $esp          # Εξέταση στοίβας
(gdb) x/20i $eip           # Ερμηνεία bytes ως εντολές assembly
(gdb) x/s $eax             # Δείκτης συμβολοσειράς
```

### Περιοχές μνήμης

```bash
# Εμφάνιση χάρτη μνήμης
(gdb) info proc mappings

# Εμφάνιση ενοτήτων
(gdb) maintenance info sections

# Εμφάνιση αρχείων
(gdb) info files
```

### Μετατροπή κώδικα μηχανής σε εντολές assembly

```bash
# Εμφάνιση εντολών συνάρτησης
(gdb) disassemble main
(gdb) disas main

# Εμφάνιση εντολών τρέχουσας συνάρτησης
(gdb) disassemble
(gdb) disas

# Εμφάνιση εντολών εύρους διευθύνσεων
(gdb) disas 0x08048456, 0x08048500

# Προβολή μαζί με πηγαίο κώδικα, εφόσον υπάρχει
(gdb) disas /m main

# Ορισμός σύνταξης assembly
(gdb) set disassembly-flavor intel
(gdb) set disassembly-flavor att
```

---

<a id="section-07"></a>

## 📊 Καταχωρητές

### Προβολή καταχωρητών

```bash
# Όλοι οι καταχωρητές
(gdb) info registers
(gdb) i r

# Συγκεκριμένος καταχωρητής
(gdb) info registers eax
(gdb) p $eax
(gdb) p/x $rip

# Όλοι οι καταχωρητές, μαζί με FP/SSE
(gdb) info all-registers
```

### Τροποποίηση καταχωρητών

```bash
# Ορισμός τιμής καταχωρητή
(gdb) set $eax = 0
(gdb) set $eip = 0x08048456
(gdb) set $rip = 0x400000

# Ορισμός σημαιών
(gdb) set $eflags = 0x246
```

### Αναφορά καταχωρητών x86

| Καταχωρητής | 64-bit | 32-bit | 16-bit | 8-bit | Σκοπός |
| ---------- | -------- | -------- | -------- | ------- | --------- |
| Συσσωρευτής | RAX | EAX | AX | AL/AH | Τιμή επιστροφής |
| Βάση | RBX | EBX | BX | BL/BH | Γενικής χρήσης |
| Μετρητής | RCX | ECX | CX | CL/CH | Μετρητής επαναλήψεων |
| Δεδομένα | RDX | EDX | DX | DL/DH | Είσοδος/έξοδος και πολλαπλασιασμός |
| Δείκτης προέλευσης | RSI | ESI | SI | SIL | Προέλευση συμβολοσειράς |
| Δείκτης προορισμού | RDI | EDI | DI | DIL | Προορισμός συμβολοσειράς |
| Δείκτης βάσης πλαισίου | RBP | EBP | BP | BPL | Πλαίσιο στοίβας |
| Δείκτης στοίβας | RSP | ESP | SP | SPL | Κορυφή στοίβας |
| Δείκτης εντολής | RIP | EIP | IP | - | Επόμενη εντολή |

### Πρόσθετοι καταχωρητές x64

```
R8-R15 : Καταχωρητές γενικής χρήσης
R8D-R15D : Κατώτερα 32 bits
R8W-R15W : Κατώτερα 16 bits
R8B-R15B : Κατώτερα 8 bits
```

---

<a id="section-08"></a>

## 📚 Ανάλυση στοίβας

### Προβολή στοίβας

```bash
# Αλυσίδα κλήσεων στοίβας
(gdb) backtrace
(gdb) bt

# Πλήρης αλυσίδα κλήσεων με τοπικές μεταβλητές
(gdb) backtrace full
(gdb) bt full

# Εμφάνιση πλαισίου στοίβας
(gdb) frame
(gdb) f

# Επιλογή πλαισίου
(gdb) frame 2
(gdb) f 2

# Εμφάνιση περιεχομένων στοίβας
(gdb) x/50wx $esp    # 32-bit
(gdb) x/50gx $rsp    # 64-bit
```

### Πλαίσιο στοίβας

```bash
# Πληροφορίες πλαισίου
(gdb) info frame
(gdb) i f

# Εμφάνιση τοπικών μεταβλητών
(gdb) info locals

# Εμφάνιση ορισμάτων
(gdb) info args

# Μετακίνηση μεταξύ πλαισίων
(gdb) up
(gdb) down
```

### Εντολές στοίβας

> **Υπόθεση διάταξης πλαισίου:** Οι θέσεις EBP+4/RBP+8 υποθέτουν συγκεκριμένο συμβατικό πλαίσιο. Βελτιστοποιήσεις, παράλειψη frame pointer και διαφορετικό ABI μπορεί να τις καταστήσουν λανθασμένες. Εξετάστε το πραγματικό backtrace και τις εντολές.


```bash
# Εξέταση διεύθυνσης επιστροφής
(gdb) x/wx $ebp+4     # 32-bit
(gdb) x/gx $rbp+8     # 64-bit

# Εξέταση αποθηκευμένου EBP
(gdb) x/wx $ebp       # 32-bit
(gdb) x/gx $rbp       # 64-bit

# Εμφάνιση διάταξης στοίβας
(gdb) x/20wx $esp-20  # Πριν και μετά τη θέση
```

---

<a id="section-09"></a>

## 🎨 GEF/PEDA/pwndbg

> **Δεν είναι εναλλάξιμα περιβάλλοντα:** Ονόματα και ορίσματα όπως ropgadget, cyclic, telescope ή got εξαρτώνται από το πρόσθετο και την έκδοση. Η εμφάνιση διαφορετικών prompts στον ίδιο οδηγό δεν σημαίνει ότι όλες οι εντολές λειτουργούν σε μία εγκατάσταση.


### Εντολές GEF

```bash
# Αυτόματη εμφάνιση περιβάλλοντος εκτέλεσης
gef➤ context

# Checksec — χαρακτηριστικά προστασίας
gef➤ checksec

# Αναζήτηση μοτίβου
gef➤ pattern create 200
gef➤ pattern search $rsp

# Αναζήτηση συμβολοσειράς ή τιμής
gef➤ search-pattern "/bin/sh"
gef➤ grep "/bin/sh"

# Ανάλυση δυναμικής μνήμης — heap
gef➤ heap chunks
gef➤ heap bins

# Ακολουθίες εντολών ROP
gef➤ ropgadget

# Βοήθημα συμβολοσειρών μορφοποίησης
gef➤ format-string-helper

# VMMap — χάρτης μνήμης
gef➤ vmmap

# Αλυσίδα αποαναφορών δεικτών
gef➤ dereference $rsp 20

# Telescope — οργανωμένη εμφάνιση αποαναφορών
gef➤ telescope $rsp 20

# Πληροφορίες μορφής εκτελέσιμου ELF
gef➤ elf-info

# Τιμή canary
gef➤ canary

# Πληροφορίες PIE/ASLR
gef➤ pie
gef➤ aslr
```

### Εντολές PEDA

```bash
# Έλεγχος προστασιών
peda➤ checksec

# Αναζήτηση συμβολοσειράς
peda➤ find "/bin/sh"
peda➤ searchmem "/bin/sh"

# Δημιουργία και αναζήτηση μοτίβου
peda➤ pattern create 200
peda➤ pattern offset $eip

# Ακολουθίες εντολών ROP
peda➤ ropgadget
peda➤ ropsearch "pop rdi"

# Κώδικας εντολών μηχανής (Shellcode)
peda➤ shellcode generate x86/linux exec

# Πλαίσιο εισαγωγής
peda➤ context all

# Κώδικας assembly
peda➤ assemble
```

### Εντολές pwndbg

```bash
# Πλαίσιο εισαγωγής
pwndbg> context

# Checksec
pwndbg> checksec

# Search — αναζήτηση
pwndbg> search -s "/bin/sh"
pwndbg> search -p 0xdeadbeef

# Κυκλικό μοτίβο
pwndbg> cyclic 200
pwndbg> cyclic -l <value>

# ROP
pwndbg> rop --grep "pop rdi"

# Δυναμική μνήμη — heap
pwndbg> heap
pwndbg> bins
pwndbg> arena

# GOT/PLT
pwndbg> got
pwndbg> plt
```

---

<a id="section-10"></a>

## 💥 Ανάπτυξη αποδείξεων εκμετάλλευσης

### Προσδιορισμός μετατόπισης — offset

> **Το αρχικό παράδειγμα είναι ελλιπές:** Το επαναλαμβανόμενο A δεν είναι κυκλικό μοτίβο και δεν δίνει μοναδική θέση. Η εκτύπωση bytes μέσω Python 3 μπορεί να παράγει αναπαράσταση με πρόθεμα και εισαγωγικά. Επιπλέον, δεν δημιουργείται αυτόματα το input.txt που διαβάζει επόμενη γραμμή. Χρειάζεται συνεπές αρχείο εισόδου και συμβατή εντολή του πρόσθετου.


```bash
# Μέθοδος 1: μοτίβο μέσω GEF
gef➤ pattern create 200
gef➤ run < <(python3 -c 'print("A"*200)')
gef➤ pattern search $rsp

# Μέθοδος 2: μοτίβο μέσω pwntools
$ python3 -c "from pwn import *; print(cyclic(200))"
gef➤ run < input.txt
gef➤ cyclic -l 0x61616167
```

### Βασικές έννοιες υπερχείλισης buffer

> **Όριο αλλαγής ASLR:** Η αλλαγή στο proc/sys επηρεάζει συνολικά το σύστημα, ενώ το disable-randomization του debugger αφορά τις διεργασίες που ξεκινά όπου υποστηρίζεται. Οι απόλυτες διευθύνσεις και τα αποτελέσματα εξαρτώνται και από PIE, βιβλιοθήκες και περιβάλλον.


```bash
# Έλεγχος ASLR
$ cat /proc/sys/kernel/randomize_va_space
# 0=ανενεργό, 1=μερική τυχαιοποίηση, 2=πληρέστερη τυχαιοποίηση

# Αλλαγή ASLR για εργαστηριακή δοκιμή· επηρεάζει το σύστημα
$ echo 0 | sudo tee /proc/sys/kernel/randomize_va_space

# Έλεγχος προστασιών εκτελέσιμου
gef➤ checksec

# Εντοπισμός θέσης διεύθυνσης επιστροφής
(gdb) x/20wx $esp     # Πριν από τη διακοπή
(gdb) i f             # Πληροφορίες πλαισίου
```

### Αναζήτηση σύντομων ακολουθιών εντολών — gadgets

```bash
# Αναζήτηση "/bin/sh"
gef➤ search-pattern "/bin/sh"

# Αναζήτηση διεύθυνσης system()
(gdb) p system
(gdb) p &system

# Αναζήτηση ακολουθίας pop rdi σε 64-bit
gef➤ ropgadget --grep "pop rdi"

# Αναζήτηση ακολουθίας pop και ret σε 32-bit
gef➤ ropgadget --grep "pop"
```

### Εγγραφή στη μνήμη

```bash
# Ορισμός τιμής μνήμης
(gdb) set {int}0x08048456 = 0x41414141
(gdb) set {char}0x08048456 = 'A'

# Εγγραφή συμβολοσειράς
(gdb) set {char[10]}0x08048456 = "AAAA"

# Κλήση συνάρτησης
(gdb) call system("/bin/sh")
```

### Χρήσιμες ενέργειες εργαστηριακής διερεύνησης

```bash
# Παρακολούθηση θυγατρικών διεργασιών
(gdb) set follow-fork-mode child

# Απενεργοποίηση τυχαιοποίησης για τη διεργασία που ξεκινά το GDB, όπου υποστηρίζεται
(gdb) set disable-randomization on

# Εμφάνιση κοινόχρηστων βιβλιοθηκών
(gdb) info sharedlibrary

# Εντοπισμός βάσης libc
gef➤ vmmap libc

# Εντοπισμός PLT/GOT
(gdb) info functions @plt
gef➤ got
```

---

<a id="section-11"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `r` | Εκτέλεση προγράμματος |
| `c` | Συνέχιση |
| `b *addr` | Ορισμός σημείου διακοπής |
| `si` | Εκτέλεση μίας εντολής |
| `ni` | Βήμα χωρίς είσοδο σε κλήση |
| `fin` | Συνέχιση μέχρι επιστροφή συνάρτησης |
| `x/Nx addr` | Εξέταση μνήμης |
| `p $reg` | Εμφάνιση καταχωρητή |
| `i r` | Πληροφορίες καταχωρητών |
| `bt` | Αλυσίδα κλήσεων — backtrace |
| `disas` | Ερμηνεία bytes ως εντολές assembly |
| `q` | Έξοδος |

### Εξέταση μνήμης

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `x/10x $esp` | 10 τιμές σε δεκαεξαδική μορφή από ESP, με το τρέχον μέγεθος μονάδας |
| `x/s addr` | Συμβολοσειρά στη διεύθυνση |
| `x/i $eip` | Εντολή στη διεύθυνση EIP |
| `x/20wx addr` | 20 λέξεις σε δεκαεξαδική μορφή |
| `x/10gx $rsp` | 10 τιμές των 8 bytes από RSP |

### Σύντομη ροή αποσφαλμάτωσης

```bash
# Εκκίνηση και διακοπή στη main
$ gdb -q ./program
(gdb) break main
(gdb) run

# Εξέταση κατάστασης διακοπής
(gdb) run < payload
(gdb) x/10i $eip
(gdb) i r
(gdb) bt
```

### Ροή εργαστηριακής διερεύνησης

```
1. checksec — έλεγχος χαρακτηριστικών προστασίας
2. pattern create — δημιουργία μοτίβου
3. Εκτέλεση με μοτίβο και διερεύνηση της διακοπής
4. pattern search — προσδιορισμός μετατόπισης
5. Εύρεση ακολουθιών εντολών για μελέτη ROP
6. Δημιουργία εργαστηριακής απόδειξης
7. Δοκιμή και αποσφαλμάτωση με GDB
```

---

<a id="section-12"></a>

## 📚 Πηγές

- [GDB Official Manual](https://sourceware.org/gdb/current/onlinedocs/gdb/)
- [GEF Documentation](https://hugsy.github.io/gef/)
- [pwndbg Documentation](https://github.com/pwndbg/pwndbg)
- [PEDA](https://github.com/longld/peda)

### Σχετικοί οδηγοί
- [Ghidra](../../Ghidra/translations/README.el.md)
- [Εντολές Linux](../../Linux-Commands/translations/README.el.md)
- [Κλιμάκωση προνομίων Linux](../../Linux-PrivEsc/translations/README.el.md)

---

<p align="center">
  <b>🐛 Εξασκηθείτε στην αποσφαλμάτωση Linux!</b><br>
  <i>GDB — εξέταση προγράμματος κατά την εκτέλεση</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
