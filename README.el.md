# 🔴 Hacking Cheatsheets — Ελληνική έκδοση

[🇬🇧 English](README.md) • [🇬🇷 Ελληνικά](README.el.md) • [🇹🇷 Türkçe](README.tr.md) • [🇮🇹 Italiano](README.it.md) • [🇩🇪 Deutsch](README.de.md) • [🇷🇺 Русский](README.ru.md) • [🇫🇷 Français](README.fr.md) • [🇪🇸 Español](README.es.md) • [🇵🇹 Português (Portugal)](README.pt.md) • [🌐 العربية](README.ar.md) • [🇨🇳 简体中文](README.zh-CN.md) • [🇳🇴 Norsk (Bokmål)](README.nb.md)

```
    ██╗  ██╗ █████╗  ██████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
    ██║  ██║██╔══██╗██╔════╝██║ ██╔╝██║████╗  ██║██╔════╝ 
    ███████║███████║██║     █████╔╝ ██║██╔██╗ ██║██║  ███╗
    ██╔══██║██╔══██║██║     ██╔═██╗ ██║██║╚██╗██║██║   ██║
    ██║  ██║██║  ██║╚██████╗██║  ██╗██║██║ ╚████║╚██████╔╝
    ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
     ██████╗██╗  ██╗███████╗ █████╗ ████████╗███████╗██╗  ██╗███████╗███████╗████████╗███████╗
    ██╔════╝██║  ██║██╔════╝██╔══██╗╚══██╔══╝██╔════╝██║  ██║██╔════╝██╔════╝╚══██╔══╝██╔════╝
    ██║     ███████║█████╗  ███████║   ██║   ███████╗███████║█████╗  █████╗     ██║   ███████╗
    ██║     ██╔══██║██╔══╝  ██╔══██║   ██║   ╚════██║██╔══██║██╔══╝  ██╔══╝     ██║   ╚════██║
    ╚██████╗██║  ██║███████╗██║  ██║   ██║   ███████║██║  ██║███████╗███████╗   ██║   ███████║
     ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝   ╚═╝   ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Penetration-Testing-red?style=for-the-badge" alt="Penetration Testing">
  <img src="https://img.shields.io/badge/Ethical-Hacking-orange?style=for-the-badge" alt="Ethical Hacking">
  <img src="https://img.shields.io/badge/Cybersecurity-blue?style=for-the-badge" alt="Cybersecurity">
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" alt="License">
</p>

<p align="center">
  <b>📚 Συλλογή οδηγών αναφοράς για εκμάθηση και εγκεκριμένους ελέγχους ασφάλειας</b>
</p>

<p align="center">
  <a href="#section-13">Οδηγοί</a> •
  <a href="#section-30">Γρήγορη εκκίνηση</a> •
  <a href="#section-35">Συνεισφορά</a> •
  <a href="#section-38">Άδεια χρήσης</a>
</p>

---

<a id="section-01"></a>

## 🎯 Σχετικά με τη συλλογή

Η ελληνική έκδοση οργανώνει τη συλλογή ώστε να μπορείτε να διαβάζετε τις επεξηγήσεις χωρίς να χρειάζεται να γνωρίζετε καλά αγγλικά. **Cheatsheet** σημαίνει συνοπτικός οδηγός αναφοράς, **pentesting** έλεγχος διείσδυσης, **payload** δοκιμαστική είσοδος ή δεδομένα συγκεκριμένης λειτουργίας και **scope** οι συμφωνημένοι στόχοι και περιορισμοί ενός ελέγχου. Οι απαραίτητες αγγλικές ονομασίες παραμένουν για να αναγνωρίζετε εργαλεία, επιλογές και μηνύματα.

Επιλέξτε κατηγορία και πατήστε **Προβολή** για τον ελληνικό οδηγό. Οι εντολές και τα τεχνικά δείγματα διατηρούνται από το πρωτότυπο· οι οδηγοί εξηγούν όπου χρειάζεται τις προϋποθέσεις, τις διαφορές εκδόσεων και τα όριά τους. Ξεκινήστε με μικρό εργαστήριο και ελέγξτε τα αποτελέσματα κάθε βήματος.

Οι οδηγοί προσφέρουν:

- ✅ **Σαφείς εξηγήσεις** για τις λειτουργίες των εργαλείων.
- ✅ **Σύνταξη εντολών** με πρακτικά παραδείγματα.
- ✅ **Σενάρια εφαρμογής** και περιπτώσεις χρήσης.
- ✅ **Πίνακες γρήγορης αναφοράς** για εύκολη αναζήτηση.
- ✅ **Συμβουλές και καλές πρακτικές** για οργανωμένους ελέγχους.

<details>
<summary>📋 Περιεχόμενα κατηγοριών και βασικών ενοτήτων</summary>

- [🎯 Μεθοδολογία ελέγχου — στάδια επίθεσης (Kill Chain)](#section-02)
- [🛡️ Blue Team — Αμυντική ασφάλεια](#section-03)
- [☁️ Ασφάλεια cloud](#section-04)
- [📱 Ασφάλεια εφαρμογών κινητών](#section-05)
- [🐳 Ασφάλεια containers](#section-06)
- [🎭 Κοινωνική μηχανική (Social Engineering)](#section-07)
- [📝 Πρότυπα αναφορών](#section-08)
- [🔍 OSINT — Πληροφορίες από ανοικτές πηγές](#section-09)
- [🌐 Έλεγχοι ασφάλειας δικτύων](#section-10)
- [🏁 Οδηγοί ασκήσεων CTF](#section-11)
- [📡 Έλεγχοι ασφάλειας συσκευών IoT](#section-12)
- [📖 Οδηγοί αναφοράς](#section-13)
  - [🔴 Εργαλεία αξιοποίησης ευπαθειών και διαχείρισης πρόσβασης](#section-14)
  - [🔍 Συλλογή πληροφοριών και σαρώσεις](#section-15)
  - [🌐 Έλεγχοι ασφάλειας εφαρμογών web](#section-16)
  - [🔓 Έλεγχος και ανάκτηση κωδικών (Password Cracking)](#section-17)
  - [📡 Ανάλυση δικτύου](#section-18)
  - [🐛 Bug Bounty — Αναζήτηση και αναφορά ευπαθειών](#section-19)
  - [💉 Συλλογή δοκιμαστικών εισόδων (Payloads)](#section-20)
  - [🔴 Ευπάθειες εφαρμογών web](#section-21)
  - [🛡️ Προχωρημένες τεχνικές ελέγχου](#section-22)
  - [🔎 Στοχευμένες αναζητήσεις (Dorking) και OSINT](#section-23)
  - [🔝 Κλιμάκωση προνομίων](#section-24)
  - [🔬 Ψηφιακή διερεύνηση (Digital Forensics)](#section-25)
  - [🔄 Αντίστροφη μηχανική](#section-26)
  - [📶 Έλεγχοι ασφάλειας WiFi](#section-27)
  - [🏢 Έλεγχοι Active Directory](#section-28)
  - [📚 Πηγές](#section-29)
- [🚀 Γρήγορη εκκίνηση](#section-30)
  - [Λήψη του αποθετηρίου](#section-31)
  - [Περιήγηση στους οδηγούς](#section-32)
  - [Πρόσβαση χωρίς σύνδεση](#section-33)
- [📂 Δομή αποθετηρίου](#section-34)
- [🤝 Συνεισφορά στο έργο](#section-35)
  - [Τρόποι συνεισφοράς](#section-36)
- [⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης](#section-37)
- [📜 Άδεια χρήσης](#section-38)
- [🌟 Υποστήριξη του έργου](#section-39)
- [📬 Επικοινωνία](#section-40)

</details>


Το **Hacking Cheatsheets** συγκεντρώνει οδηγούς γρήγορης αναφοράς για εργαλεία ελέγχου διείσδυσης και εξουσιοδοτημένης διερεύνησης ασφάλειας. Κάθε οδηγός περιλαμβάνει:

- ✅ **Κατανοητές επεξηγήσεις** για τη λειτουργία των εργαλείων
- ✅ **Σύνταξη εντολών** με πρακτικά παραδείγματα
- ✅ **Σενάρια χρήσης** και παραδείγματα εφαρμογής
- ✅ **Συνοπτικούς πίνακες** για γρήγορη αναζήτηση
- ✅ **Πρακτικές συμβουλές** για οργάνωση και επαλήθευση των ελέγχων

---

<a id="section-02"></a>

## 🎯 Μεθοδολογία ελέγχου — στάδια επίθεσης (Kill Chain)

> Οδηγός ελέγχων διείσδυσης οργανωμένος σε στάδια, με αναφορές στο πλαίσιο MITRE ATT&CK.

| Φάση | Περιγραφή | Οδηγός |
|-------|-------------|-------|
| **1. Αρχική πρόσβαση** | Αξιοποίηση ευπαθειών, phishing και στοιχεία πρόσβασης | [📄 Προβολή](./Attack-Methodology/translations/01-Initial-Access.el.md) |
| **2. Αναγνώριση και καταγραφή** | Αναγνώριση συστήματος και δικτύου | [📄 Προβολή](./Attack-Methodology/translations/02-Enumeration.el.md) |
| **3. Κλιμάκωση προνομίων** | Διερεύνηση πρόσβασης με δικαιώματα root/SYSTEM | [📄 Προβολή](./Attack-Methodology/translations/03-Privilege-Escalation.el.md) |
| **4. Πρόσβαση σε άλλα συστήματα** | Εξέταση πρόσβασης σε άλλα συστήματα του δικτύου | [📄 Προβολή](./Attack-Methodology/translations/04-Lateral-Movement.el.md) |
| **5. Διατήρηση πρόσβασης** | Διατήρηση πρόσβασης | [📄 Προβολή](./Attack-Methodology/translations/05-Persistence.el.md) |
| **6. Παράκαμψη αμυντικών μηχανισμών** | Έλεγχοι προστασιών AV, EDR και AMSI | [📄 Προβολή](./Attack-Methodology/translations/06-Defense-Evasion.el.md) |
| **7. Ενέργειες επίτευξης στόχων** | Εξαγωγή δεδομένων και αξιολόγηση αντίκτυπου | [📄 Προβολή](./Attack-Methodology/translations/07-Actions-Objectives.el.md) |

👉 **[Συνολική επισκόπηση μεθοδολογίας ελέγχου](./Attack-Methodology/translations/README.el.md)**


---

<a id="section-03"></a>

## 🛡️ Blue Team — Αμυντική ασφάλεια

> Οδηγοί αμυντικής ασφάλειας για αναλυτές SOC και αντιμετώπιση περιστατικών.

| Θεματική | Περιγραφή | Οδηγός |
|-------|-------------|-------|
| **Αντιμετώπιση περιστατικών** | Στάδια αντιμετώπισης περιστατικών, περιορισμός και διαδικασίες | [📄 Προβολή](./Blue-Team/translations/Incident-Response.el.md) |
| **Ανάλυση καταγραφών** | Ανάλυση αρχείων καταγραφής Windows/Linux και αναγνωριστικών συμβάντων | [📄 Προβολή](./Blue-Team/translations/Log-Analysis.el.md) |
| **Ανίχνευση με SIEM** | Ερωτήματα και πίνακες παρακολούθησης Splunk/ELK | [📄 Προβολή](./Blue-Team/translations/SIEM-Detection.el.md) |
| **Προληπτική αναζήτηση απειλών** | Προληπτική αναζήτηση ενδείξεων απειλών | [📄 Προβολή](./Blue-Team/translations/Threat-Hunting.el.md) |
| **Ενίσχυση ασφάλειας** | Λίστες ασφαλούς ρύθμισης Windows/Linux | [📄 Προβολή](./Blue-Team/translations/Hardening.el.md) |
| **Κανόνες Sigma** | Κανόνες ανίχνευσης ανεξάρτητοι από συγκεκριμένη πλατφόρμα | [📄 Προβολή](./Blue-Team/translations/Sigma-Rules.el.md) |
| **Κανόνες YARA** | Μοτίβα εντοπισμού κακόβουλου λογισμικού και ενδείξεων παραβίασης | [📄 Προβολή](./Blue-Team/translations/YARA-Rules.el.md) |

👉 **[Συνολική επισκόπηση Blue Team](./Blue-Team/translations/README.el.md)**


---

<a id="section-04"></a>

## ☁️ Ασφάλεια cloud

> Οδηγοί ελέγχων ασφάλειας σε AWS, Azure και GCP.

| Πάροχος | Περιγραφή | Οδηγός |
|----------|-------------|-------|
| **AWS** | Υπηρεσίες AWS: S3, IAM, Lambda, EC2 και IMDS | [📄 Προβολή](./Cloud-Security/translations/AWS-Pentesting.el.md) |
| **Azure** | Ταυτότητες Azure AD/Entra ID, Blob Storage, εικονικές μηχανές και Key Vault | [📄 Προβολή](./Cloud-Security/translations/Azure-Pentesting.el.md) |
| **GCP** | Υπηρεσίες GCP: GCS, IAM, Compute και Cloud Functions | [📄 Προβολή](./Cloud-Security/translations/GCP-Pentesting.el.md) |

👉 **[Συνολική επισκόπηση ασφάλειας cloud](./Cloud-Security/translations/README.el.md)**


---

<a id="section-05"></a>

## 📱 Ασφάλεια εφαρμογών κινητών

> Οδηγοί ελέγχων εφαρμογών Android και iOS.

| Πλατφόρμα | Περιγραφή | Οδηγός |
|----------|-------------|-------|
| **Android** | Ανάλυση APK, χρήση Frida και έλεγχοι ανίχνευσης root | [📄 Προβολή](./Mobile-Security/translations/Android-Pentesting.el.md) |
| **iOS** | Ανάλυση IPA, jailbreak, Objection και αποθήκευση στοιχείων στο keychain | [📄 Προβολή](./Mobile-Security/translations/iOS-Pentesting.el.md) |

👉 **[Συνολική επισκόπηση ασφάλειας κινητών](./Mobile-Security/translations/README.el.md)**


---

<a id="section-06"></a>

## 🐳 Ασφάλεια containers

> Οδηγοί ελέγχων ασφάλειας Docker και Kubernetes.

| Πλατφόρμα | Περιγραφή | Οδηγός |
|----------|-------------|-------|
| **Docker** | Έλεγχοι απομόνωσης container, ανάλυση images και αξιοποίηση αδυναμιών daemon | [📄 Προβολή](./Container-Security/translations/Docker-Pentesting.el.md) |
| **Kubernetes** | Έλεγχοι δικαιωμάτων RBAC, απομόνωσης pod και έκθεσης secrets | [📄 Προβολή](./Container-Security/translations/Kubernetes-Pentesting.el.md) |

👉 **[Συνολική επισκόπηση ασφάλειας containers](./Container-Security/translations/README.el.md)**


---

<a id="section-07"></a>

## 🎭 Κοινωνική μηχανική (Social Engineering)

> Οδηγοί κοινωνικής μηχανικής, προσομοιώσεων phishing και σεναρίων προσχημάτων.

| Θεματική | Περιγραφή | Οδηγός |
|-------|-------------|-------|
| **Ηλεκτρονικό ψάρεμα (Phishing)** | Phishing μέσω email, GoPhish, Evilginx2, τηλεφωνικές κλήσεις και SMS | [📄 Προβολή](./Social-Engineering/translations/Phishing.el.md) |
| **Κατασκευή παραπλανητικού σεναρίου (Pretexting)** | Ρόλοι, σενάρια προσχημάτων και ψυχολογική επιρροή | [📄 Προβολή](./Social-Engineering/translations/Pretexting.el.md) |

👉 **[Συνολική επισκόπηση κοινωνικής μηχανικής](./Social-Engineering/translations/README.el.md)**


---

<a id="section-08"></a>

## 📝 Πρότυπα αναφορών

> Πρότυπα αναφορών για ελέγχους διείσδυσης και υποβολή ευπαθειών Bug Bounty.

| Πρότυπο | Περιγραφή | Οδηγός |
|----------|-------------|-------|
| **Αναφορά ελέγχου διείσδυσης** | Πλήρης δομή αναφοράς ελέγχου διείσδυσης | [📄 Προβολή](./Reporting/translations/Pentest-Report-Template.el.md) |
| **Αναφορά ευπάθειας Bug Bounty** | Πρότυπο υποβολής ευρήματος σε HackerOne/Bugcrowd | [📄 Προβολή](./Reporting/translations/Bug-Bounty-Report-Template.el.md) |
| **Σύνοψη για τη διοίκηση** | Σύνοψη χωρίς τεχνικές λεπτομέρειες για τη διοίκηση | [📄 Προβολή](./Reporting/translations/Executive-Summary-Template.el.md) |

👉 **[Συνολική επισκόπηση προτύπων αναφορών](./Reporting/translations/README.el.md)**

---

<a id="section-09"></a>

## 🔍 OSINT — Πληροφορίες από ανοικτές πηγές

> Μεθοδολογία και εργαλεία συλλογής πληροφοριών από ανοικτές πηγές.

| Θεματική | Περιγραφή | Οδηγός |
|-------|-------------|-------|
| **Αναζήτηση πληροφοριών για πρόσωπα** | Αναζήτηση δημόσιων πληροφοριών για άτομα, τηλέφωνα και διευθύνσεις | [📄 Προβολή](./OSINT/translations/People-Search.el.md) |
| **Έρευνα email** | Αναζήτηση email, έλεγχος γνωστών διαρροών και επαλήθευση | [📄 Προβολή](./OSINT/translations/Email-OSINT.el.md) |
| **Κοινωνικά δίκτυα** | Αναζήτηση ονομάτων χρήστη και πληροφοριών ανά πλατφόρμα | [📄 Προβολή](./OSINT/translations/Social-Media-OSINT.el.md) |
| **Domain και IP** | Συλλογή πληροφοριών WHOIS, DNS, υποτομέων και IP | [📄 Προβολή](./OSINT/translations/Domain-IP-OSINT.el.md) |
| **OSINT — Έρευνα και ανάλυση εικόνων** | Αντίστροφη αναζήτηση εικόνας και μεταδεδομένα EXIF | [📄 Προβολή](./OSINT/translations/Image-OSINT.el.md) |

👉 **[Συνολική επισκόπηση OSINT](./OSINT/translations/README.el.md)**


---

<a id="section-10"></a>

## 🌐 Έλεγχοι ασφάλειας δικτύων

> Οδηγοί ελέγχων διείσδυσης σε δίκτυα.

| Θεματική | Περιγραφή | Οδηγός |
|-------|-------------|-------|
| **Σάρωση θυρών** | Εργαλεία Nmap, Masscan και RustScan | [📄 Προβολή](./Network-Pentesting/translations/Port-Scanning.el.md) |
| **Συλλογή πληροφοριών δικτύου** | Υπηρεσίες SMB, SNMP, NFS, LDAP και DNS | [📄 Προβολή](./Network-Pentesting/translations/Network-Enumeration.el.md) |
| **Παρεμβολή στην επικοινωνία (MITM)** | Έλεγχοι παραπλάνησης ARP/DNS και υποβάθμισης HTTPS όπου είναι εφικτή | [📄 Προβολή](./Network-Pentesting/translations/MITM-Attacks.el.md) |
| **Έλεγχος αδυναμιών υπηρεσιών** | Έλεγχοι υπηρεσιών FTP, SSH, SMB, RDP και βάσεων δεδομένων | [📄 Προβολή](./Network-Pentesting/translations/Service-Exploitation.el.md) |

👉 **[Συνολική επισκόπηση ελέγχων δικτύου](./Network-Pentesting/translations/README.el.md)**


---

<a id="section-11"></a>

## 🏁 Οδηγοί ασκήσεων CTF

> Οδηγοί εξάσκησης CTF για HackTheBox, TryHackMe και PicoCTF.

| Κατηγορία | Περιγραφή | Οδηγός |
|----------|-------------|-------|
| **Ασφάλεια ιστού (Web)** | SQL injection, XSS, SSTI, LFI και έλεγχοι ταυτοποίησης | [📄 Προβολή](./CTF/translations/Web-CTF.el.md) |
| **Κρυπτογραφία (Crypto)** | RSA, AES, αποτυπώματα, κωδικοποιήσεις και XOR | [📄 Προβολή](./CTF/translations/Crypto-CTF.el.md) |
| **Αντίστροφη μηχανική** | Ghidra, IDA, GDB και τροποποίηση δυαδικών αρχείων | [📄 Προβολή](./CTF/translations/Reverse-Engineering-CTF.el.md) |
| **Ψηφιακή διερεύνηση (Forensics)** | Στεγανογραφία, μνήμη, δίσκος και καταγραφές πακέτων PCAP | [📄 Προβολή](./CTF/translations/Forensics-CTF.el.md) |
| **Εκμετάλλευση εκτελέσιμων (Pwn/Binary)** | Υπερχείλιση buffer, ROP και κώδικας shellcode | [📄 Προβολή](./CTF/translations/Pwn-CTF.el.md) |

👉 **[Συνολική επισκόπηση CTF](./CTF/translations/README.el.md)**


---

<a id="section-12"></a>

## 📡 Έλεγχοι ασφάλειας συσκευών IoT

> Οδηγοί ελέγχου συσκευών IoT, ανάλυσης firmware και διεπαφών υλικού.

| Θεματική | Περιγραφή | Οδηγός |
|-------|-------------|-------|
| **Ανάλυση ενσωματωμένου λογισμικού (Firmware)** | Binwalk, εξαγωγή δεδομένων, αντίστροφη μηχανική και έκθεση μυστικών | [📄 Προβολή](./IoT-Hacking/translations/Firmware-Analysis.el.md) |
| **Διερεύνηση υλικού (Hardware)** | Διεπαφές UART, JTAG, SPI, I2C και θύρες αποσφαλμάτωσης | [📄 Προβολή](./IoT-Hacking/translations/Hardware-Hacking.el.md) |

👉 **[Συνολική επισκόπηση ελέγχων IoT](./IoT-Hacking/translations/README.el.md)**


---

<a id="section-13"></a>

## 📖 Οδηγοί αναφοράς

<a id="section-14"></a>

### 🔴 Εργαλεία αξιοποίησης ευπαθειών και διαχείρισης πρόσβασης

| Εργαλείο | Περιγραφή | Οδηγός |
|------|-------------|------------|
| **Metasploit Framework** | Πλαίσιο εργαλείων για ελέγχους διείσδυσης | [📄 Προβολή](./Metasploit/translations/README.el.md) |
| **Meterpreter** | Περιβάλλον διαχείρισης συνεδρίας μετά την απόκτηση πρόσβασης | [📄 Προβολή](./Metasploit/translations/Meterpreter.el.md) |
| **Mimikatz** | Εργαλείο εξέτασης και εξαγωγής στοιχείων πρόσβασης Windows | [📄 Προβολή](./Mimikatz/translations/README.el.md) |
| **PowerShell** | Αυτοματοποίηση και scripts Windows για ελέγχους ασφάλειας | [📄 Προβολή](./PowerShell/translations/README.el.md) |
| **Εντολές Linux** | Εντολές Linux και Bash για ελέγχους ασφάλειας | [📄 Προβολή](./Linux-Commands/translations/README.el.md) |

<a id="section-15"></a>

### 🔍 Συλλογή πληροφοριών και σαρώσεις

| Εργαλείο | Περιγραφή | Οδηγός |
|------|-------------|------------|
| **Nmap** | Αναγνώριση δικτύου και έλεγχοι ασφάλειας | [📄 Προβολή](./Nmap/translations/README.el.md) |
| **Gobuster** | Αναζήτηση καταλόγων, ονομάτων DNS και virtual hosts με λίστες τιμών | [📄 Προβολή](./Gobuster/translations/README.el.md) |
| **Nikto** | Εργαλείο ελέγχων web server | [📄 Προβολή](./Nikto/translations/README.el.md) |

<a id="section-16"></a>

### 🌐 Έλεγχοι ασφάλειας εφαρμογών web

| Εργαλείο | Περιγραφή | Οδηγός |
|------|-------------|------------|
| **SQLMap** | Αυτοματοποίηση ελέγχων SQL injection | [📄 Προβολή](./SQLMap/translations/README.el.md) |
| **Burp Suite** | Πλατφόρμα ελέγχων ασφάλειας εφαρμογών web | [📄 Προβολή](./Burp-Suite/translations/README.el.md) |
| **OWASP ZAP** | Δωρεάν εργαλείο ελέγχων ασφάλειας εφαρμογών web | [📄 Προβολή](./OWASP-ZAP/translations/README.el.md) |

<a id="section-17"></a>

### 🔓 Έλεγχος και ανάκτηση κωδικών (Password Cracking)

| Εργαλείο | Περιγραφή | Οδηγός |
|------|-------------|------------|
| **Hydra** | Εργαλείο δοκιμών ταυτοποίησης δικτυακών υπηρεσιών | [📄 Προβολή](./Hydra/translations/README.el.md) |
| **John the Ripper** | Εργαλείο ανάκτησης κωδικών από υποστηριζόμενα αποτυπώματα | [📄 Προβολή](./John-The-Ripper/translations/README.el.md) |
| **Hashcat** | Έλεγχος υποψήφιων κωδικών με αξιοποίηση GPU | [📄 Προβολή](./Hashcat/translations/README.el.md) |

<a id="section-18"></a>

### 📡 Ανάλυση δικτύου

| Εργαλείο | Περιγραφή | Οδηγός |
|------|-------------|------------|
| **Wireshark** | Ανάλυση πρωτοκόλλων και πακέτων δικτύου | [📄 Προβολή](./Wireshark/translations/README.el.md) |
| **tcpdump** | Καταγραφή και ανάλυση πακέτων από τη γραμμή εντολών | [📄 Προβολή](./tcpdump/translations/README.el.md) |

<a id="section-19"></a>

### 🐛 Bug Bounty — Αναζήτηση και αναφορά ευπαθειών

| Εργαλείο | Περιγραφή | Οδηγός |
|------|-------------|------------|
| **⭐ Μεθοδολογία Bug Bounty** | Μεθοδολογία αναζήτησης και αναφοράς ευπαθειών σε προγράμματα Bug Bounty | [📄 Προβολή](./Bug-Bounty-Methodology/translations/README.el.md) |
| **Nuclei** | Έλεγχοι ευπαθειών βάσει προτύπων | [📄 Προβολή](./Nuclei/translations/README.el.md) |
| **ffuf** | Δοκιμές διαφορετικών εισόδων σε αιτήματα web | [📄 Προβολή](./ffuf/translations/README.el.md) |
| **Subfinder** | Αναζήτηση υποτομέων | [📄 Προβολή](./Subfinder/translations/README.el.md) |
| **httpx** | Δοκιμαστικά αιτήματα HTTP και συλλογή πληροφοριών απόκρισης | [📄 Προβολή](./httpx/translations/README.el.md) |
| **Amass** | Χαρτογράφηση επιφάνειας επίθεσης | [📄 Προβολή](./Amass/translations/README.el.md) |
| **GAU** | Συλλογή γνωστών URLs από ιστορικές πηγές | [📄 Προβολή](./GAU/translations/README.el.md) |
| **Katana** | Εξερεύνηση ιστοτόπων και ανακάλυψη συνδέσμων | [📄 Προβολή](./Katana/translations/README.el.md) |
| **Arjun** | Αναζήτηση μη εμφανών παραμέτρων | [📄 Προβολή](./Arjun/translations/README.el.md) |
| **Dalfox** | Ανάλυση και έλεγχοι πιθανών ευπαθειών XSS | [📄 Προβολή](./Dalfox/translations/README.el.md) |

<a id="section-20"></a>

### 💉 Συλλογή δοκιμαστικών εισόδων (Payloads)

| Αδυναμία | Περιγραφή | Οδηγός |
|---------------|-------------|------------|
| **XSS** | Δοκιμαστικές είσοδοι για XSS | [📄 Προβολή](./Payloads/translations/XSS.el.md) |
| **SQLi** | Δοκιμαστικές είσοδοι SQL injection | [📄 Προβολή](./Payloads/translations/SQLi.el.md) |
| **LFI** | Δοκιμαστικές είσοδοι τοπικής συμπερίληψης αρχείων | [📄 Προβολή](./Payloads/translations/LFI.el.md) |
| **SSTI** | Εισαγωγή εκφράσεων σε πρότυπα του server | [📄 Προβολή](./Payloads/translations/SSTI.el.md) |
| **Εισαγωγή εντολών λειτουργικού (Command Injection)** | Δοκιμαστικές είσοδοι εισαγωγής εντολών λειτουργικού | [📄 Προβολή](./Payloads/translations/Command-Injection.el.md) |
| **Εισαγωγή εκφράσεων NoSQL** | Δοκιμαστικές είσοδοι MongoDB, CouchDB και Redis | [📄 Προβολή](./Payloads/translations/NoSQL-Injection.el.md) |
| **Μη ασφαλής αποσειριοποίηση (Deserialization)** | Δοκιμαστικά δεδομένα αποσειριοποίησης σε Java, PHP, Python και .NET | [📄 Προβολή](./Payloads/translations/Deserialization.el.md) |
| **Έλεγχοι WebSocket** | CSWSH, εισαγωγή μη έμπιστων δεδομένων και κατάχρηση συνεδρίας | [📄 Προβολή](./Payloads/translations/WebSocket-Attacks.el.md) |
| **Έλεγχοι εισαγωγής δεδομένων σε GraphQL** | Εξερεύνηση σχήματος, δικαιώματα IDOR και μη ασφαλής ερμηνεία εισόδου | [📄 Προβολή](./Payloads/translations/GraphQL-Injection.el.md) |

<a id="section-21"></a>

### 🔴 Ευπάθειες εφαρμογών web

| Αδυναμία | Περιγραφή | Οδηγός |
|---------------|-------------|------------|
| **Ασφάλεια διεπαφών εφαρμογών (API)** | Οδηγός ελέγχων REST, GraphQL και JWT | [📄 Προβολή](./API-Security/translations/README.el.md) |
| **IDOR** | Μη ασφαλής άμεση αναφορά σε αντικείμενο | [📄 Προβολή](./IDOR/translations/README.el.md) |
| **SSRF** | Μη επιτρεπόμενα αιτήματα από τον server | [📄 Προβολή](./SSRF/translations/README.el.md) |
| **XXE** | Μη ασφαλής επεξεργασία εξωτερικών οντοτήτων XML | [📄 Προβολή](./XXE/translations/README.el.md) |
| **Συνθήκες ανταγωνισμού** | Έλεγχοι χρονισμού και ταυτόχρονων ενεργειών | [📄 Προβολή](./Race-Conditions/translations/README.el.md) |
| **Παράκαμψη ταυτοποίησης** | Έλεγχοι μηχανισμών ταυτοποίησης | [📄 Προβολή](./Auth-Bypass/translations/README.el.md) |
| **CORS** | Λανθασμένες ρυθμίσεις ανάγνωσης μεταξύ origins | [📄 Προβολή](./CORS/translations/README.el.md) |
| **Μη ελεγχόμενες ανακατευθύνσεις** | Ευπάθειες επιλογής προορισμού ανακατεύθυνσης | [📄 Προβολή](./Open-Redirect/translations/README.el.md) |

<a id="section-22"></a>

### 🛡️ Προχωρημένες τεχνικές ελέγχου

| Θεματική | Περιγραφή | Οδηγός |
|-------|-------------|------------|
| **Έλεγχοι παράκαμψης WAF** | Αναζήτηση αρχικού server και έλεγχοι φίλτρων WAF | [📄 Προβολή](./WAF-Bypass/translations/README.el.md) |
| **Έλεγχοι έκθεσης αρχικού server πίσω από Cloudflare** | Αναζήτηση υποψήφιας IP αρχικού server πίσω από Cloudflare | [📄 Προβολή](./Cloudflare-Bypass/translations/README.el.md) |
| **Πιθανή ανάληψη εγκαταλειμμένου υποτομέα** | Έλεγχοι εγκαταλειμμένων συνδέσεων CNAME | [📄 Προβολή](./Subdomain-Takeover/translations/README.el.md) |
| **Αλλοίωση αποκρίσεων cache** | Αλλοίωση και παραπλάνηση προσωρινής αποθήκευσης web | [📄 Προβολή](./Cache-Poisoning/translations/README.el.md) |
| **Ασυμφωνία ορίων αιτημάτων HTTP** | Έλεγχοι request smuggling CL.TE και TE.CL | [📄 Προβολή](./HTTP-Request-Smuggling/translations/README.el.md) |
| **Αλλοίωση πρωτοτύπων JavaScript** | Έλεγχοι μη ασφαλούς αλλαγής πρωτοτύπων JavaScript | [📄 Προβολή](./Prototype-Pollution/translations/README.el.md) |

<a id="section-23"></a>

### 🔎 Στοχευμένες αναζητήσεις (Dorking) και OSINT

| Εργαλείο | Περιγραφή | Οδηγός |
|------|-------------|------------|
| **Εξειδικευμένες αναζητήσεις Google (Google Dorking, αγγλικά)** | Στοχευμένα ερωτήματα και τελεστές αναζήτησης Google | [📄 Προβολή](./Google-Dorking/translations/README.el.md) |
| **Shodan** | Μηχανή αναζήτησης εκτεθειμένων υπηρεσιών και συσκευών | [📄 Προβολή](./Shodan/translations/README.el.md) |
| **Εξειδικευμένες αναζητήσεις GitHub (GitHub Dorking, αγγλικά)** | Αναζήτηση εκτεθειμένων μυστικών σε αποθετήρια | [📄 Προβολή](./GitHub-Dorking/translations/README.el.md) |

<a id="section-24"></a>

### 🔝 Κλιμάκωση προνομίων

| Θεματική | Περιγραφή | Οδηγός |
|-------|-------------|------------|
| **Ανύψωση δικαιωμάτων Linux (αγγλικά)** | Έλεγχοι κλιμάκωσης προνομίων Linux | [📄 Προβολή](./Linux-PrivEsc/translations/README.el.md) |
| **Ανύψωση δικαιωμάτων Windows (αγγλικά)** | Έλεγχοι κλιμάκωσης προνομίων Windows | [📄 Προβολή](./Windows-PrivEsc/translations/README.el.md) |

<a id="section-25"></a>

### 🔬 Ψηφιακή διερεύνηση (Digital Forensics)

| Εργαλείο | Περιγραφή | Οδηγός |
|------|-------------|------------|
| **Volatility** | Πλαίσιο ψηφιακής εξέτασης μνήμης | [📄 Προβολή](./Volatility/translations/README.el.md) |
| **Autopsy** | Πλατφόρμα ψηφιακής διερεύνησης με γραφικό περιβάλλον | [📄 Προβολή](./Autopsy/translations/README.el.md) |
| **ExifTool** | Εξαγωγή και ανάλυση μεταδεδομένων | [📄 Προβολή](./ExifTool/translations/README.el.md) |
| **Binwalk** | Ανάλυση firmware και εξαγωγή ενσωματωμένων δεδομένων | [📄 Προβολή](./Binwalk/translations/README.el.md) |

<a id="section-26"></a>

### 🔄 Αντίστροφη μηχανική

| Εργαλείο | Περιγραφή | Οδηγός |
|------|-------------|------------|
| **Ghidra** | Πλατφόρμα αντίστροφης μηχανικής της NSA | [📄 Προβολή](./Ghidra/translations/README.el.md) |
| **GDB** | Αποσφαλματωτής GNU για ανάλυση προγραμμάτων | [📄 Προβολή](./GDB/translations/README.el.md) |
| **x64dbg** | Αποσφαλματωτής προγραμμάτων Windows x64/x32 | [📄 Προβολή](./x64dbg/translations/README.el.md) |

<a id="section-27"></a>

### 📶 Έλεγχοι ασφάλειας WiFi

| Εργαλείο | Περιγραφή | Οδηγός |
|------|-------------|------------|
| **Aircrack-ng** | Συλλογή εργαλείων ελέγχου WiFi και WPA/WPA2 | [📄 Προβολή](./Aircrack-ng/translations/README.el.md) |
| **Wifite** | Αυτοματοποιημένοι έλεγχοι ασφάλειας WiFi | [📄 Προβολή](./Wifite/translations/README.el.md) |
| **Bettercap** | Πλαίσιο ελέγχων δικτύου MITM και WiFi | [📄 Προβολή](./Bettercap/translations/README.el.md) |

<a id="section-28"></a>

### 🏢 Έλεγχοι Active Directory

| Εργαλείο | Περιγραφή | Οδηγός |
|------|-------------|------------|
| **⭐ Μεθοδολογία Active Directory** | Οργανωμένος οδηγός ελέγχων Active Directory | [📄 Προβολή](./AD-Attack-Methodology/translations/README.el.md) |
| **BloodHound** | Οπτικοποίηση πιθανών διαδρομών πρόσβασης AD | [📄 Προβολή](./BloodHound/translations/README.el.md) |
| **Impacket** | Εργαλεία Python για πρωτόκολλα και ελέγχους AD | [📄 Προβολή](./Impacket/translations/README.el.md) |
| **CrackMapExec** | Συλλογή εργαλείων ελέγχου υπηρεσιών AD | [📄 Προβολή](./CrackMapExec/translations/README.el.md) |
| **Rubeus** | Αλληλεπίδραση και έλεγχοι Kerberos | [📄 Προβολή](./Rubeus/translations/README.el.md) |
| **PowerView** | Καταγραφή AD μέσω PowerShell | [📄 Προβολή](./PowerView/translations/README.el.md) |
| **Responder** | Έλεγχοι παραπλάνησης επίλυσης ονομάτων LLMNR/NBT-NS | [📄 Προβολή](./Responder/translations/README.el.md) |
| **Evil-WinRM** | Διαδραστικό περιβάλλον απομακρυσμένης διαχείρισης WinRM | [📄 Προβολή](./Evil-WinRM/translations/README.el.md) |
| **Kerbrute** | Αναζήτηση χρηστών και δοκιμές κωδικών μέσω Kerberos | [📄 Προβολή](./Kerbrute/translations/README.el.md) |

<a id="section-29"></a>

### 📚 Πηγές

> **Πλήθος εργαλείων:** Το 600+ είναι αναφορά του αγγλικού πρωτοτύπου, όχι αριθμός που επαληθεύτηκε εκ νέου για κάθε έκδοση του Kali ή του καταλόγου.


| Πηγή | Περιγραφή | Οδηγός |
|----------|-------------|------------|
| **Wordlists — λίστες υποψήφιων τιμών** | Οδηγός επιλογής και χρήσης λιστών υποψήφιων τιμών | [📄 Προβολή](./Wordlists/translations/README.el.md) |
| **Εργαλεία Kali Linux** | Εργαλεία ανά κατηγορία· αναφορά 600+ του αγγλικού πρωτοτύπου | [📄 Προβολή](./Kali-Linux-Tools/translations/README.el.md) |

---

<a id="section-30"></a>

## 🚀 Γρήγορη εκκίνηση

<a id="section-31"></a>

### Λήψη του αποθετηρίου

```bash
git clone https://github.com/Ilias1988/Hacking-Cheatsheets.git
cd Hacking-Cheatsheets
```

<a id="section-32"></a>

### Περιήγηση στους οδηγούς

> **Τα παρακάτω παραδείγματα ανοίγουν αγγλικά αρχεία.** Για ελληνικά, χρησιμοποιήστε τους συνδέσμους αυτής της σελίδας ή τον φάκελο translations του αντίστοιχου εργαλείου. Για παράδειγμα, ο ελληνικός οδηγός Metasploit βρίσκεται στο Metasploit/translations/README.el.md.


Επιλέξτε τον φάκελο του εργαλείου και ανοίξτε τον αντίστοιχο οδηγό. Οι σύνδεσμοι αυτής της σελίδας οδηγούν στις ελληνικές εκδόσεις:

```bash
# Προβολή αγγλικού οδηγού Metasploit
cat Metasploit/README.md

# Εναλλακτικά, άνοιγμα στον επεξεργαστή κειμένου
code Metasploit/
```

<a id="section-33"></a>

### Πρόσβαση χωρίς σύνδεση

Οι οδηγοί είναι αρχεία Markdown, επομένως προσφέρονται για:
- 📱 **Ανάγνωση από κινητό** ή άλλη συσκευή με κατάλληλο πρόγραμμα προβολής
- 🔌 **Πρόσβαση χωρίς σύνδεση** μετά τη λήψη του αποθετηρίου
- 🖨️ **Εκτύπωση** των οδηγών
- 🔍 **Αναζήτηση** με grep ή με τον επεξεργαστή κειμένου

---

<a id="section-34"></a>

## 📂 Δομή αποθετηρίου

> **Ενδεικτικό δέντρο του πρωτοτύπου:** Δεν εμφανίζει όλους τους φακέλους ή τις γλώσσες. Η ελληνική αρχική σελίδα είναι το README.el.md στη ρίζα, ενώ οι ελληνικοί οδηγοί βρίσκονται σε φακέλους translations με κατάληξη .el.md. Το κεντρικό README.md παραμένει στα αγγλικά.


```
Hacking-Cheatsheets/
│
├── README.md                # Κεντρικό ευρετήριο στα αγγλικά
├── README.it.md             # Κεντρικό ευρετήριο στα ιταλικά
├── LICENSE                  # Άδεια MIT
├── CONTRIBUTING.md          # Οδηγίες συνεισφοράς στα αγγλικά
├── CONTRIBUTING.it.md       # Οδηγίες συνεισφοράς στα ιταλικά
├── .gitignore               # Κανόνες εξαίρεσης αρχείων από το Git
│
├── Metasploit/              # Metasploit Framework
│   ├── README.md            # Οδηγός msfconsole
│   └── Meterpreter.md       # Οδηγός Meterpreter
│
├── Nmap/                    # Εργαλείο αναγνώρισης και ελέγχου δικτύου
│   └── README.md            # Οδηγός Nmap
│
├── Gobuster/                # Αναζήτηση καταλόγων και ονομάτων DNS
│   └── README.md            # Οδηγός Gobuster
│
├── Nikto/                   # Εργαλείο ελέγχων web server
│   └── README.md            # Οδηγός Nikto
│
├── SQLMap/                  # Εργαλείο ελέγχων SQL injection
│   └── README.md            # Οδηγός SQLMap
│
├── Burp-Suite/              # Έλεγχοι εφαρμογών web
│   └── README.md            # Οδηγός Burp Suite
│
├── OWASP-ZAP/               # OWASP Zed Attack Proxy
│   └── README.md            # Οδηγός ZAP
│
├── Hydra/                   # Εργαλείο δοκιμών ταυτοποίησης δικτυακών υπηρεσιών
│   └── README.md            # Οδηγός Hydra
│
├── John-The-Ripper/         # Εργαλείο ελέγχου υποψήφιων κωδικών
│   └── README.md            # Οδηγός John
│
├── Hashcat/                 # Έλεγχος υποψήφιων κωδικών με GPU
│   └── README.md            # Οδηγός Hashcat
│
├── Wireshark/               # Εργαλείο ανάλυσης πρωτοκόλλων δικτύου
│   └── README.md            # Οδηγός Wireshark
│
├── tcpdump/                 # Ανάλυση πακέτων από τη γραμμή εντολών
│   └── README.md            # Οδηγός tcpdump
│
├── Nuclei/                  # Εργαλείο ελέγχων ευπαθειών
│   └── README.md            # Οδηγός Nuclei
│
├── ffuf/                    # Εργαλείο δοκιμών εισόδων web
│   └── README.md            # Οδηγός ffuf
│
├── Subfinder/               # Αναζήτηση υποτομέων
│   └── README.md            # Οδηγός Subfinder
│
├── httpx/                   # Εργαλείο αιτημάτων και πληροφοριών HTTP
│   └── README.md            # Οδηγός httpx
│
├── Google-Dorking/          # Στοχευμένες αναζητήσεις Google
│   └── README.md            # Οδηγός Google Dorking
│
├── Shodan/                  # Μηχανή αναζήτησης εκτεθειμένων υπηρεσιών και συσκευών
│   └── README.md            # Οδηγός Shodan
│
├── GitHub-Dorking/          # Αναζήτηση εκτεθειμένων μυστικών
│   └── README.md            # Οδηγός GitHub Dorking
│
└── ...
```

---

<a id="section-35"></a>

## 🤝 Συνεισφορά στο έργο

Οι συνεισφορές είναι ευπρόσδεκτες. Διαβάστε τις [οδηγίες συνεισφοράς στα αγγλικά](CONTRIBUTING.md) πριν υποβάλετε pull request.

<a id="section-36"></a>

### Τρόποι συνεισφοράς

- 📝 **Προσθέστε οδηγούς** για εργαλεία που δεν καλύπτονται ακόμη
- 🔧 **Βελτιώστε τους υπάρχοντες οδηγούς** με σαφέστερα παραδείγματα
- 🐛 **Αναφέρετε προβλήματα** ή προτείνετε βελτιώσεις
- 🌐 **Μεταφράστε οδηγούς** σε άλλες γλώσσες
- ⭐ **Προσθέστε αστέρι στο αποθετήριο** για να δείξετε την υποστήριξή σας

---

<a id="section-37"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

> **ΣΗΜΑΝΤΙΚΟ:** Οι οδηγοί προορίζονται **για εκπαίδευση** και **μόνο για εξουσιοδοτημένους ελέγχους ασφάλειας**.
> 
> - ✅ Χρησιμοποιήστε τους σε δικά σας συστήματα
> - ✅ Χρησιμοποιήστε τους με ρητή γραπτή άδεια
> - ✅ Χρησιμοποιήστε τους σε νόμιμες, εξουσιοδοτημένες εργασίες ελέγχου διείσδυσης
> - ❌ Μην τους χρησιμοποιείτε για μη εξουσιοδοτημένη πρόσβαση
> - ❌ Μην τους χρησιμοποιείτε για κακόβουλους σκοπούς
> 
> **Η μη εξουσιοδοτημένη πρόσβαση σε υπολογιστικά συστήματα είναι παράνομη.** Οι συντάκτες δεν αναλαμβάνουν ευθύνη για κακή χρήση των πληροφοριών.

---

<a id="section-38"></a>

## 📜 Άδεια χρήσης

Το έργο διατίθεται με άδεια MIT. Δείτε το αρχείο [LICENSE](LICENSE) για τους όρους.

---

<a id="section-39"></a>

## 🌟 Υποστήριξη του έργου

Αν οι οδηγοί σάς βοηθούν, μπορείτε να υποστηρίξετε το έργο:

- ⭐ **Προσθέτοντας αστέρι** στο αποθετήριο
- 🍴 **Δημιουργώντας fork**, δηλαδή δικό σας αντίγραφο στο GitHub, για συνεισφορά
- 📢 **Κοινοποιώντας τη συλλογή** σε ανθρώπους που ασχολούνται με την ασφάλεια
- 💬 **Στέλνοντας παρατηρήσεις** για βελτιώσεις

---

<a id="section-40"></a>

## 📬 Επικοινωνία

- **GitHub Issues** — αναφορές προβλημάτων και προτάσεις δυνατοτήτων
- **Pull Requests** — προτεινόμενες αλλαγές και συνεισφορές

---

<p align="center">
  <b>Καλή εξάσκηση στην ασφάλεια! 🔴</b><br>
  <i>Με υπευθυνότητα και μέσα στα όρια της εξουσιοδότησής σας.</i>
</p>

---

<p align="center">
  Δημιουργήθηκε με ❤️ για την κοινότητα κυβερνοασφάλειας
</p>
