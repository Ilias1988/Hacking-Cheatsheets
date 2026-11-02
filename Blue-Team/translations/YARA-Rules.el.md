# 🔬 Κανόνες αναγνώρισης αρχείων και μνήμης YARA

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../YARA-Rules.md) • [🇮🇹 Italiano](YARA-Rules.it.md) • [🇹🇷 Türkçe](YARA-Rules.tr.md)

[🛡️ Ελληνική επισκόπηση Blue Team](README.el.md)

```
██╗   ██╗ █████╗ ██████╗  █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
╚██╗ ██╔╝██╔══██╗██╔══██╗██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
 ╚████╔╝ ███████║██████╔╝███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
  ╚██╔╝  ██╔══██║██╔══██╗██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
   ██║   ██║  ██║██║  ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

Η **YARA** είναι εργαλείο αναγνώρισης και ταξινόμησης αρχείων ή περιεχομένου μνήμης βάσει μοτίβων κειμένου ή δυαδικών δεδομένων, συχνά για ανάλυση κακόβουλου λογισμικού.

---

## Πριν ξεκινήσετε

Η **YARA** συγκρίνει περιεχόμενο αρχείων ή μνήμης με κανόνες που περιγράφουν συμβολοσειρές, ακολουθίες bytes και συνθήκες. Ένα ταίριασμα δείχνει ότι ικανοποιήθηκαν τα κριτήρια του κανόνα· δεν αποδεικνύει από μόνο του ότι το αρχείο είναι κακόβουλο.

**Ανάγνωση κανόνα:** Το `meta` περιέχει περιγραφικά στοιχεία, το `strings` τα μοτίβα και το `condition` τη λογική της αντιστοίχισης. Μεταβλητές όπως `$s1` ονομάζουν μοτίβα. Το `#s1` μετρά εμφανίσεις και το `@s1` αναφέρεται σε θέση μέσα στο εξεταζόμενο περιεχόμενο. Τα μοτίβα αναζήτησης και οι αγγλικές περιγραφές μέσα στους κανόνες παραμένουν όπως στο πρωτότυπο.

Ένα **PE** είναι μορφή εκτελέσιμου Windows. Μια **hex string** αναπαριστά bytes σε δεκαεξαδική μορφή και ένα **wildcard** επιτρέπει μεταβλητά bytes. Οι γενικοί κανόνες χρειάζονται έλεγχο σε γνωστά νόμιμα και κακόβουλα δείγματα. Τα παρακάτω αποσπάσματα είναι εκπαιδευτικά παραδείγματα και δεν έχουν μεταγλωττιστεί ή δοκιμαστεί ως μέρος της μετάφρασης.

## Περιεχόμενα

- [📖 Δομή κανόνα YARA](#section-01)
- [🎯 Παραδείγματα κανόνων](#section-02)
- [🔧 Τροποποιητές συμβολοσειρών](#section-03)
- [📊 Τελεστές συνθηκών](#section-04)
- [🛠️ Εντολές YARA](#section-05)
- [📚 Πηγές](#section-06)
- [🔗 Σχετικοί οδηγοί αναφοράς](#section-07)

<a id="section-01"></a>

## 📖 Δομή κανόνα YARA

```yara
rule RuleName : tag1 tag2
{
    meta:
        author = "Your Name"
        description = "Description of the rule"
        date = "2024-01-15"
        reference = "https://example.com"
        hash = "abc123..."
        
    strings:
        $string1 = "malware string"
        $string2 = { 4D 5A 90 00 }  // δεκαεξαδικά bytes
        $regex1 = /password[0-9]+/i  // κανονική έκφραση
        
    condition:
        $string1 or $string2
}
```

---

<a id="section-02"></a>

## 🎯 Παραδείγματα κανόνων

### Ενδείξεις χρήσης Mimikatz
```yara
rule Mimikatz
{
    meta:
        author = "Security Team"
        description = "Detects Mimikatz tool"
        
    strings:
        $s1 = "sekurlsa::" ascii wide
        $s2 = "kerberos::" ascii wide
        $s3 = "lsadump::" ascii wide
        $s4 = "privilege::debug" ascii wide
        $s5 = "mimikatz" ascii wide nocase
        $s6 = "gentilkiwi" ascii wide
        
    condition:
        uint16(0) == 0x5A4D and 3 of ($s*)
}
```

### Εντοπισμός μοτίβων Cobalt Strike Beacon
```yara
rule CobaltStrike_Beacon
{
    meta:
        description = "Detects Cobalt Strike Beacon"
        
    strings:
        $s1 = "%02d/%02d/%02d %02d:%02d:%02d" ascii
        $s2 = "beacon.dll" ascii
        $s3 = "ReflectiveLoader" ascii
        $pipe1 = "\\\\.\\pipe\\msagent_" ascii
        $pipe2 = "\\\\.\\pipe\\MSSE-" ascii
        
    condition:
        uint16(0) == 0x5A4D and (2 of ($s*) or any of ($pipe*))
}
```

### Εντοπισμός κώδικα Web Shell
```yara
rule Webshell_Generic
{
    meta:
        description = "Detects generic webshells"
        
    strings:
        $php1 = "<?php" ascii nocase
        $asp1 = "<%@ " ascii nocase
        
        $eval1 = "eval(" ascii nocase
        $eval2 = "assert(" ascii nocase
        $exec1 = "system(" ascii nocase
        $exec2 = "shell_exec(" ascii nocase
        $exec3 = "passthru(" ascii nocase
        $exec4 = "exec(" ascii nocase
        
        $b64 = "base64_decode" ascii nocase
        $input = "$_REQUEST" ascii nocase
        $input2 = "$_POST" ascii nocase
        $input3 = "$_GET" ascii nocase
        
    condition:
        ($php1 or $asp1) and 
        any of ($eval*) and 
        ($b64 or any of ($input*))
}
```

### Εντοπισμός λήψης κώδικα μέσω PowerShell
```yara
rule PowerShell_Downloader
{
    meta:
        description = "Detects PowerShell download cradles"
        
    strings:
        $ps = "powershell" ascii nocase
        
        $dl1 = "DownloadString" ascii nocase
        $dl2 = "DownloadFile" ascii nocase
        $dl3 = "DownloadData" ascii nocase
        $dl4 = "Net.WebClient" ascii nocase
        $dl5 = "Invoke-WebRequest" ascii nocase
        $dl6 = "wget" ascii nocase
        $dl7 = "curl" ascii nocase
        $dl8 = "IWR" ascii nocase
        
        $exec1 = "IEX" ascii nocase
        $exec2 = "Invoke-Expression" ascii nocase
        $exec3 = "-enc" ascii nocase
        $exec4 = "-EncodedCommand" ascii nocase
        
    condition:
        $ps and (any of ($dl*)) and (any of ($exec*))
}
```

### Εντοπισμός μοτίβων λογισμικού εκβιασμού (Ransomware)
```yara
rule Ransomware_Generic
{
    meta:
        description = "Detects generic ransomware patterns"
        
    strings:
        $note1 = "your files have been encrypted" ascii wide nocase
        $note2 = "bitcoin" ascii wide nocase
        $note3 = "decrypt" ascii wide nocase
        $note4 = "ransom" ascii wide nocase
        $note5 = ".onion" ascii wide
        
        $ext1 = ".encrypted" ascii wide
        $ext2 = ".locked" ascii wide
        $ext3 = ".crypto" ascii wide
        
        $api1 = "CryptEncrypt" ascii
        $api2 = "CryptGenKey" ascii
        $api3 = "CryptAcquireContext" ascii
        
    condition:
        uint16(0) == 0x5A4D and
        (2 of ($note*) or any of ($ext*)) and
        2 of ($api*)
}
```

### Εκτελέσιμο PE με συναρτήσεις που χρειάζονται διερεύνηση
```yara
rule Suspicious_PE_Imports
{
    meta:
        description = "PE with suspicious imports"
        
    strings:
        $import1 = "VirtualAllocEx" ascii
        $import2 = "WriteProcessMemory" ascii
        $import3 = "CreateRemoteThread" ascii
        $import4 = "NtUnmapViewOfSection" ascii
        $import5 = "SetThreadContext" ascii
        
    condition:
        uint16(0) == 0x5A4D and 3 of them
}
```

---

<a id="section-03"></a>

## 🔧 Τροποποιητές συμβολοσειρών

| Τροποποιητής | Περιγραφή |
| ---------- | ------------- |
| `ascii` | Συμβολοσειρές ASCII |
| `wide` | Συμβολοσειρές με μορφή wide, συνηθισμένη στα Windows |
| `nocase` | Σύγκριση χωρίς διάκριση πεζών και κεφαλαίων |
| `fullword` | Ταίριασμα μόνο ως πλήρης λέξη |
| `base64` | Συμβολοσειρές κωδικοποιημένες με Base64 |
| `xor` | Συμβολοσειρές μετασχηματισμένες με XOR |

### Παραδείγματα
```yara
$s1 = "malware" ascii wide nocase
$s2 = "password" fullword
$s3 = "secret" base64
$s4 = "payload" xor
$hex = { 4D 5A ?? ?? 00 00 }  // ?? = οποιοδήποτε byte
```

---

<a id="section-04"></a>

## 📊 Τελεστές συνθηκών

| Τελεστής | Περιγραφή |
| ---------- | ------------- |
| `and` | Λογικό ΚΑΙ — πρέπει να ισχύουν και τα δύο |
| `or` | Λογικό Ή — αρκεί να ισχύει ένα |
| `not` | Λογική άρνηση — δεν πρέπει να ισχύει |
| `any of` | Τουλάχιστον μία από τις συμβολοσειρές |
| `all of` | Όλες οι συμβολοσειρές |
| `X of` | Ο καθορισμένος αριθμός συμβολοσειρών |

### Παραδείγματα

Στον πίνακα τελεστών YARA τα επόμενα παραδείγματα συνθηκών διαβάζονται ξεχωριστά. Η διαδοχική παράθεσή τους κάτω από ένα `condition:` δεν αποτελεί από μόνη της ένα πλήρες έγκυρο λογικό σύνολο.

```yara
condition:
    all of them                    // όλες οι συμβολοσειρές
    any of ($s*)                   // οποιοδήποτε μοτίβο με όνομα που αρχίζει από $s
    2 of ($s1, $s2, $s3)          // δύο από αυτά τα τρία μοτίβα
    #s1 > 5                        // το $s1 εμφανίζεται πάνω από πέντε φορές
    @s1 < 100                      // το $s1 εντοπίζεται πριν από τη θέση 100
    filesize < 100KB               // συνθήκη μεγέθους αρχείου
    uint16(0) == 0x5A4D            // επικεφαλίδα MZ, ένδειξη μορφής PE
```

---

<a id="section-05"></a>

## 🛠️ Εντολές YARA

**Σημείο του πρωτοτύπου που απαιτεί διόρθωση:** στο YARA η επιλογή `-p` ορίζει αριθμό νημάτων σάρωσης, όχι «λειτουργία σάρωσης διεργασίας». Το PID είναι ο στόχος. Η γραμμή `yara -p rules.yar <pid>` διατηρείται για αντιστοιχία με την πηγή, αλλά δεν πρέπει να χρησιμοποιηθεί ως έτοιμη εντολή. Δείτε την [επίσημη σύνταξη εντολών](https://yara.readthedocs.io/en/stable/commandline.html).


```bash
# Σάρωση αρχείου
yara rules.yar file.exe

# Σάρωση καταλόγου
yara -r rules.yar /path/to/scan/

# Σάρωση με πολλά αρχεία κανόνων
yara rule1.yar rule2.yar file.exe

# Εμφάνιση συμβολοσειρών που ταίριαξαν
yara -s rules.yar file.exe

# Εμφάνιση μεταδεδομένων
yara -m rules.yar file.exe

# Το πρωτότυπο περιέχει εσφαλμένη χρήση του -p — διαβάστε τη διευκρίνιση παραπάνω
yara -p rules.yar <pid>

# Ορισμός χρονικού ορίου
yara -t 60 rules.yar file.exe

# Μεταγλώττιση κανόνων
yarac rules.yar compiled.yarc
```

---

<a id="section-06"></a>

## 📚 Πηγές

- [YARA Documentation](https://yara.readthedocs.io/)
- [YARA Rules Repository](https://github.com/Yara-Rules/rules)
- [VirusTotal Hunting](https://www.virustotal.com/gui/hunting-overview)

---

<a id="section-07"></a>

## 🔗 Σχετικοί οδηγοί αναφοράς

- [Κανόνες Sigma](Sigma-Rules.el.md)
- Ανάλυση κακόβουλου λογισμικού — δεν υπάρχει ακόμη ξεχωριστός οδηγός
- [Προληπτική αναζήτηση απειλών](Threat-Hunting.el.md)

---

**Προηγούμενο θέμα:** [← Κανόνες Sigma](Sigma-Rules.el.md)

**Επιστροφή στην επισκόπηση:** [🛡️ Blue Team](README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
