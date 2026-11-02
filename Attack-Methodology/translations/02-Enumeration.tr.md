# 🔍 Faz 2: Keşif ve Numaralandırma (Enumeration)

[🇬🇧 English](../02-Enumeration.md) • [🇮🇹 Versione Italiana](02-Enumeration.it.md)

```
  ███████╗███╗   ██╗██╗   ██╗███╗   ███╗███████╗██████╗  █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝████╗  ██║██║   ██║████╗ ████║██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
  █████╗  ██╔██╗ ██║██║   ██║██╔████╔██║█████╗  ██████╔╝███████║   ██║   ██║██║   ██║██╔██╗ ██║
  ██╔══╝  ██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
  ███████╗██║ ╚████║╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**Hedef:** Sonraki adımları planlamak ve olası saldırı yollarını tespit etmek için ele geçirilen sistem ve ağ hakkında kapsamlı bilgi toplamak.

---

## 🖥️ Windows Sistem Keşfi

### Sistem Bilgileri
```cmd
# Temel sistem bilgileri
systeminfo
hostname
whoami /all

# İşletim sistemi sürümü ve derleme numarası
wmic os get caption,version,buildnumber

# İşlemci mimarisi
echo %PROCESSOR_ARCHITECTURE%

# Ortam değişkenleri
set
```

### Kullanıcı ve Grup Keşfi
```cmd
# Mevcut kullanıcının yetkileri ve grupları
whoami /priv
whoami /groups

# Yerel kullanıcılar
net user
net user administrator

# Yerel gruplar
net localgroup
net localgroup Administrators

# Domain kullanıcıları ve grupları (Domain ortamındaysa)
net user /domain
net group /domain
net group "Domain Admins" /domain
```

### Ağ Yapılandırması
```cmd
# Ağ arayüzleri ve IP yapılandırması
ipconfig /all

# Yönlendirme (Routing) tablosu
route print

# ARP tablosu (Ağdaki diğer cihazlar)
arp -a

# Aktif bağlantılar ve dinlenen portlar
netstat -ano
netstat -ano | findstr ESTABLISHED
netstat -ano | findstr LISTENING

# DNS önbelleği
ipconfig /displaydns

# Ağ paylaşımları
net share
net view \\localhost
```

### Süreçler ve Servisler (Processes & Services)
```cmd
# Çalışan süreçler ve bağlı servisler
tasklist
tasklist /SVC
wmic process list brief

# Servisler
sc query
net start
wmic service list brief

# Zamanlanmış görevler (Scheduled Tasks)
schtasks /query /fo LIST /v
```

### Yüklü Yazılımlar ve Yamalar
```cmd
# Yüklü programlar
wmic product get name,version
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall

# Yüklü güvenlik güncellemeleri ve yamalar (Hotfixes)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Parolalar ve Hassas Dosyalar
```cmd
# Kaydedilmiş kimlik bilgileri
cmdkey /list

# Kayıtlı WiFi parolaları
netsh wlan show profiles
netsh wlan show profile name="SSID" key=clear

# Dosyalarda parola arama
findstr /si password *.txt *.ini *.config *.xml
dir /s *pass* *cred* *vnc* *.config

# Unattend kurulum dosyaları (genelde açık metin parola içerir)
dir /s C:\*unattend.xml
dir /s C:\*sysprep.inf
```

---

## 🐧 Linux Sistem Keşfi

### Sistem Bilgileri
```bash
# Temel bilgiler
hostname
uname -a
cat /etc/os-release
cat /proc/version

# Mimari ve Kernel sürümü
arch
uname -r
```

### Kullanıcı ve Grup Keşfi
```bash
# Mevcut kullanıcı ve ID
whoami
id
groups

# Tüm kullanıcılar ve gruplar
cat /etc/passwd
cat /etc/shadow  # Okunabiliyorsa hash'ler buradadır
cat /etc/group

# Aktif oturum açmış kullanıcılar
w
who
last

# Sudo izinleri
sudo -l
cat /etc/sudoers
```

### Ağ Yapılandırması
```bash
# IP adresleri
ip a
ifconfig

# Yönlendirme tablosu
ip route
route -n

# ARP komşuluk tablosu
arp -a
ip neigh

# Dinlenen portlar ve aktif soketler
ss -tulnp
netstat -tulnp

# DNS yapılandırması
cat /etc/resolv.conf
```

### Süreçler ve Servisler
```bash
# Çalışan süreçler
ps aux
ps auxwww
top

# Servis durumları
systemctl list-units --type=service
service --status-all

# Zamanlanmış görevler (Cron Jobs)
cat /etc/crontab
ls -la /etc/cron.*
crontab -l
```

### Yüklü Yazılımlar ve SUID Dosyaları
```bash
# Debian / Ubuntu paketleri
dpkg -l
apt list --installed

# RHEL / CentOS paketleri
rpm -qa
yum list installed

# SUID ve SGID yetkili ikili dosyalar (Yetki yükseltme vektörleri!)
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null
```

### Kimlik Bilgileri ve Hassas Dosyalar
```bash
# SSH anahtarları
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Komut geçmişi dosyaları
cat ~/.bash_history
cat ~/.zsh_history

# Konfigürasyon dosyalarında parola arama
grep -r "password" /etc/ 2>/dev/null
grep -r "pass" /home/ 2>/dev/null

# İlgi çekici dosyalar
cat /etc/fstab
cat ~/.bashrc
cat ~/.profile
```

---

## 🏢 Active Directory Keşfi

### PowerView ile Keşif
```powershell
# Modülü yükleme
Import-Module .\PowerView.ps1

# Domain ve DC bilgileri
Get-Domain
Get-DomainController

# Kullanıcılar
Get-DomainUser
Get-DomainUser -Identity admin
Get-DomainUser | select samaccountname,description

# Gruplar ve grup üyeleri
Get-DomainGroup
Get-DomainGroupMember -Identity "Domain Admins"

# Bilgisayarlar ve işletim sistemleri
Get-DomainComputer
Get-DomainComputer | select dnshostname,operatingsystem

# Grup İlkeleri (GPO)
Get-DomainGPO
Get-DomainGPOLocalGroup
```

### BloodHound ile Veri Toplama
```bash
# SharpHound ile veri toplama
.\SharpHound.exe -c All

# PowerShell ile:
Import-Module .\SharpHound.ps1
Invoke-BloodHound -CollectionMethod All

# Linux üzerinden Python toplayıcı:
bloodhound-python -u user -p 'password' -d domain.local -ns 192.168.1.1 -c All
```

### LDAP Keşfi
```bash
# Anonim LDAP sorgusu
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local"

# Kullanıcıları listeleme
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local" "(objectClass=user)"

# Kimlik bilgileri ile sorgulama
ldapsearch -x -H ldap://192.168.1.1 -D "user@domain.local" -w 'password' -b "DC=domain,DC=local"
```

### Kerberos Keşfi
```bash
# Kerbrute ile kullanıcı listesi deneme
kerbrute userenum -d domain.local users.txt --dc 192.168.1.1

# AS-REP Roast edilebilir kullanıcıları tespit etme
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt

# Kerberoast edilebilir hesapları çekme
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1
```

---

## 📡 Ağ Keşfi (Network Enumeration)

### Canlı Host Keşfi
```bash
# Ping taraması
nmap -sn 192.168.1.0/24

# Yerel ağ ARP taraması
arp-scan -l

# Netdiscover pasif/aktif dinleme
netdiscover -r 192.168.1.0/24
```

### Port Taraması
```bash
# Hızlı port taraması
nmap -F 192.168.1.10

# Tüm portlar (1-65535)
nmap -p- 192.168.1.10

# Servis versiyonu ve varsayılan NSE scriptleri
nmap -sV -sC 192.168.1.10

# En yaygın 100 UDP portu taraması
nmap -sU --top-ports 100 192.168.1.10
```

### SMB Keşfi
```bash
# Enum4linux taraması
enum4linux -a 192.168.1.10

# Anonim paylaşımları listeleme
smbclient -L //192.168.1.10 -N
smbmap -H 192.168.1.10
crackmapexec smb 192.168.1.10 --shares

# Kimlik bilgisi ile bağlanma
smbclient //192.168.1.10/share -U user
```

### SNMP Keşfi
```bash
# Varsayılan topluluk dizgileri (community strings)
snmpwalk -v2c -c public 192.168.1.10
snmpwalk -v2c -c private 192.168.1.10

# Onesixtyone ile hızlı topluluk adı deneme
onesixtyone -c community.txt 192.168.1.10
```

---

## 🛠️ Otomatik Keşif Araçları

### Windows
```bash
# WinPEAS (En kapsamlı otomatik keşif aracı)
.\winPEAS.exe

# Seatbelt (Güvenlik yapılandırması denetimi)
.\Seatbelt.exe -group=all

# PowerUp (Yanlış yapılandırma arayıcı)
Import-Module .\PowerUp.ps1
Invoke-AllChecks

# Windows Exploit Suggester
python windows-exploit-suggester.py --database 2024.xlsx --systeminfo systeminfo.txt
```

### Linux
```bash
# LinPEAS
./linpeas.sh

# LinEnum
./LinEnum.sh

# Linux Exploit Suggester
./linux-exploit-suggester.sh

# LSE (Linux Smart Enumeration)
./lse.sh -l 2
```

---

## 📊 Hızlı Başvuru Tablosu

### Windows Hızlı Keşif
```cmd
systeminfo
whoami /all
net user
net localgroup Administrators
ipconfig /all
netstat -ano
tasklist /SVC
```

### Linux Hızlı Keşif
```bash
uname -a && id && sudo -l
cat /etc/passwd
ps aux
netstat -tulnp
find / -perm -4000 2>/dev/null
```

---

## 🔗 İlgili Kılavuzlar

- [Nmap](../../Nmap/README.md)
- [BloodHound](../../BloodHound/README.md)
- [PowerView](../../PowerView/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)

---

**Önceki Aşama:** [← 01 - İlk Erişim (Initial Access)](./01-Initial-Access.tr.md)  
**Sonraki Aşama:** [03 - Yetki Yükseltme (Privilege Escalation) →](./03-Privilege-Escalation.tr.md)
