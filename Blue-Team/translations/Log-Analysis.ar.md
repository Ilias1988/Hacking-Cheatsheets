<a id="-log-analysis-cheatsheet"></a>
# 📊 مرجع تحليل السجلات

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Log-Analysis.tr.md) • [🇬🇷 Ελληνικά](Log-Analysis.el.md)

```
  ██╗      ██████╗  ██████╗      █████╗ ███╗   ██╗ █████╗ ██╗  ██╗   ██╗███████╗██╗███████╗
  ██║     ██╔═══██╗██╔════╝     ██╔══██╗████╗  ██║██╔══██╗██║  ╚██╗ ██╔╝██╔════╝██║██╔════╝
  ██║     ██║   ██║██║  ███╗    ███████║██╔██╗ ██║███████║██║   ╚████╔╝ ███████╗██║███████╗
  ██║     ██║   ██║██║   ██║    ██╔══██║██║╚██╗██║██╔══██║██║    ╚██╔╝  ╚════██║██║╚════██║
  ███████╗╚██████╔╝╚██████╔╝    ██║  ██║██║ ╚████║██║  ██║███████╗██║   ███████║██║███████║
  ╚══════╝ ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝   ╚══════╝╚═╝╚══════╝
```

---

<a id="-windows-event-logs"></a>
## 🖥️ سجلات أحداث Windows

<a id="log-locations"></a>
### مواقع السجلات
```
# سجل الأمان
C:\Windows\System32\winevt\Logs\Security.evtx

# سجل النظام
C:\Windows\System32\winevt\Logs\System.evtx

# سجل التطبيقات
C:\Windows\System32\winevt\Logs\Application.evtx

# سجلات PowerShell
C:\Windows\System32\winevt\Logs\Microsoft-Windows-PowerShell%4Operational.evtx
C:\Windows\System32\winevt\Logs\Windows PowerShell.evtx

# Sysmon عند تثبيته
C:\Windows\System32\winevt\Logs\Microsoft-Windows-Sysmon%4Operational.evtx
```

<a id="critical-security-event-ids"></a>
### معرّفات أحداث الأمان المهمة

<a id="authentication-events"></a>
#### أحداث المصادقة
| معرّف الحدث | الوصف | الأهمية |
|----------|-------------|------------|
| **4624** | تسجيل دخول ناجح | تتبّع وصول المستخدمين |
| **4625** | فشل تسجيل الدخول | اكتشاف التخمين الشامل |
| **4634** | تسجيل الخروج | تتبّع الجلسات |
| **4647** | تسجيل خروج بدأه المستخدم | تتبّع الجلسات |
| **4648** | تسجيل دخول باستخدام بيانات اعتماد صريحة | الحركة الجانبية |
| **4672** | إسناد امتيازات خاصة | نشاط المسؤول |
| **4768** | طلب تذكرة TGT في Kerberos | مصادقة Active Directory |
| **4769** | تذكرة خدمة Kerberos | الوصول إلى الخدمات |
| **4771** | فشل المصادقة المسبقة في Kerberos | رش كلمات المرور |
| **4776** | مصادقة NTLM | التحقق من بيانات الاعتماد |

<a id="logon-types-for-46244625"></a>
#### أنواع تسجيل الدخول للأحداث 4624/4625
| النوع | الوصف |
|------|-------------|
| 2 | تفاعلي محلي |
| 3 | الشبكة |
| 4 | دفعي |
| 5 | خدمة |
| 7 | إلغاء القفل |
| 8 | شبكي بنص صريح |
| 9 | بيانات اعتماد جديدة |
| 10 | تفاعلي عن بُعد (RDP) |
| 11 | تفاعلي ببيانات اعتماد مخزنة مؤقتًا |

<a id="account-management"></a>
#### إدارة الحسابات
| معرّف الحدث | الوصف |
|----------|-------------|
| **4720** | إنشاء حساب مستخدم |
| **4722** | تفعيل حساب مستخدم |
| **4723** | محاولة تغيير كلمة المرور |
| **4724** | إعادة تعيين كلمة المرور |
| **4725** | تعطيل حساب مستخدم |
| **4726** | حذف حساب مستخدم |
| **4728** | إضافة عضو إلى مجموعة أمان |
| **4732** | إضافة عضو إلى مجموعة محلية |
| **4756** | إضافة عضو إلى مجموعة شاملة |

<a id="process--service-events"></a>
#### أحداث العمليات والخدمات
| معرّف الحدث | الوصف |
|----------|-------------|
| **4688** | إنشاء عملية |
| **4689** | إنهاء عملية |
| **7045** | تثبيت خدمة |
| **7036** | تغيير حالة خدمة |
| **4697** | تثبيت خدمة (سجل الأمان) |

<a id="scheduled-tasks"></a>
#### المهام المجدولة
| معرّف الحدث | الوصف |
|----------|-------------|
| **4698** | إنشاء مهمة مجدولة |
| **4699** | حذف مهمة مجدولة |
| **4700** | تفعيل مهمة مجدولة |
| **4702** | تحديث مهمة مجدولة |

<a id="powershell-query-commands"></a>
### أوامر الاستعلام في PowerShell
```powershell
# جلب إدخالات سجل الأمان
Get-WinEvent -LogName Security -MaxEvents 100

# محاولات تسجيل الدخول الفاشلة (4625)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4625} | 
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='IP';E={$_.Properties[19].Value}}

# عمليات تسجيل الدخول الناجحة (4624)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='LogonType';E={$_.Properties[8].Value}}

# إنشاء عملية جديدة (4688)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4688} |
    Select-Object TimeCreated, @{N='Process';E={$_.Properties[5].Value}}, @{N='CommandLine';E={$_.Properties[8].Value}}

# عمليات تثبيت الخدمات (7045)
Get-WinEvent -FilterHashtable @{LogName='System';ID=7045} |
    Select-Object TimeCreated, @{N='ServiceName';E={$_.Properties[0].Value}}

# البحث ضمن نطاق زمني
$startTime = (Get-Date).AddDays(-7)
Get-WinEvent -FilterHashtable @{LogName='Security';StartTime=$startTime}
```

<a id="wevtutil-commands"></a>
### أوامر Wevtutil
```cmd
# الاستعلام من سجل الأمان
wevtutil qe Security /c:50 /f:text

# الاستعلام عن معرّف حدث محدد
wevtutil qe Security /q:"*[System[(EventID=4625)]]" /c:100 /f:text

# تصدير السجل
wevtutil epl Security C:\evidence\security.evtx

# مسح السجل (يُستخدم بحذر)
wevtutil cl Security
```

---

<a id="-linux-log-analysis"></a>
## 🐧 تحليل سجلات Linux

<a id="log-locations-1"></a>
### مواقع السجلات
```bash
# المصادقة
/var/log/auth.log        # Debian/Ubuntu
/var/log/secure          # RHEL/CentOS

# النظام
/var/log/syslog          # Debian/Ubuntu
/var/log/messages        # RHEL/CentOS

# النواة
/var/log/kern.log
/var/log/dmesg

# Cron
/var/log/cron

# التدقيق
/var/log/audit/audit.log

# خوادم الويب
/var/log/apache2/        # Apache
/var/log/nginx/          # Nginx
/var/log/httpd/          # RHEL Apache

# SSH
/var/log/auth.log
```

<a id="common-log-analysis-commands"></a>
### أوامر شائعة لتحليل السجلات

<a id="authentication-analysis"></a>
#### تحليل المصادقة
```bash
# محاولات SSH الفاشلة
grep "Failed password" /var/log/auth.log
grep "authentication failure" /var/log/auth.log

# عمليات تسجيل دخول SSH الناجحة
grep "Accepted" /var/log/auth.log

# تخمين SSH الشامل (العدّ حسب عنوان IP)
grep "Failed password" /var/log/auth.log | awk '{print $(NF-3)}' | sort | uniq -c | sort -rn

# رفع امتيازات المستخدم (sudo)
grep "sudo:" /var/log/auth.log
grep "session opened for user root" /var/log/auth.log

# محاولات su
grep "su:" /var/log/auth.log
```

<a id="system-analysis"></a>
#### تحليل النظام
```bash
# أوقات الإقلاع والإيقاف
last -x | grep shutdown
last -x | grep reboot

# المستخدمون المسجلون حاليًا
who
w

# سجل تسجيل الدخول
last
lastlog

# محاولات تسجيل الدخول الفاشلة
lastb
```

<a id="processservice-analysis"></a>
#### تحليل العمليات والخدمات
```bash
# تنفيذ مهام Cron
grep CRON /var/log/syslog

# بدء الخدمات وإيقافها
grep -E "Started|Stopped" /var/log/syslog
journalctl -u <service-name>

# رسائل النواة
dmesg | tail -100
grep -i error /var/log/kern.log
```

<a id="useful-grepawk-patterns"></a>
### أنماط مفيدة باستخدام Grep/Awk
```bash
# استخراج عناوين IP من السجلات
grep -oE '\b[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\b' /var/log/auth.log

# عدّ مرات الظهور حسب عنوان IP
cat /var/log/auth.log | grep "Failed" | awk '{print $11}' | sort | uniq -c | sort -rn | head -20

# البحث حسب الوقت
awk '/Jan 15 10:00/,/Jan 15 11:00/' /var/log/auth.log

# البحث في ملفات متعددة
grep -r "error" /var/log/

# المراقبة الآنية
tail -f /var/log/auth.log | grep --line-buffered "Failed"
```

<a id="journalctl-systemd"></a>
### Journalctl (Systemd)
```bash
# جميع السجلات
journalctl

# وضع المتابعة
journalctl -f

# منذ وقت محدد
journalctl --since "2024-01-15 10:00:00"
journalctl --since "1 hour ago"

# حسب الوحدة أو الخدمة
journalctl -u sshd
journalctl -u nginx

# حسب الأولوية
journalctl -p err       # Errors only
journalctl -p warning   # Warnings and above

# رسائل النواة
journalctl -k

# إخراج بصيغة JSON
journalctl -o json
```

---

<a id="-web-server-logs"></a>
## 🌐 سجلات خوادم الويب

<a id="apache-log-format"></a>
### تنسيق سجلات Apache
```
# تنسيق السجل المجمّع
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."

# الحقول:
# IP - ident - user - [timestamp] "method path protocol" status size "referer" "user-agent"
```

<a id="nginx-log-format"></a>
### تنسيق سجلات Nginx
```
# التنسيق الافتراضي مشابه لتنسيق Apache
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."
```

<a id="web-log-analysis"></a>
### تحليل سجلات الويب
```bash
# الروابط الأكثر طلبًا
awk '{print $7}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# عناوين IP الأكثر ظهورًا
awk '{print $1}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# رموز حالة HTTP
awk '{print $9}' /var/log/apache2/access.log | sort | uniq -c | sort -rn

# أخطاء 404
grep ' 404 ' /var/log/apache2/access.log

# طلبات POST (هجمات محتملة)
grep '"POST' /var/log/apache2/access.log

# محاولات حقن SQL
grep -iE "(union|select|insert|update|delete|drop|script)" /var/log/apache2/access.log

# محاولات اجتياز المسار
grep -E "(\.\./|\.\.\\)" /var/log/apache2/access.log

# الطلبات حسب الساعة
awk '{print $4}' /var/log/apache2/access.log | cut -d: -f2 | sort | uniq -c
```

---

<a id="-sysmon-log-analysis"></a>
## 🛡️ تحليل سجلات Sysmon

<a id="key-sysmon-event-ids"></a>
### معرّفات أحداث Sysmon الأساسية
| معرّف الحدث | الوصف |
|----------|-------------|
| 1 | إنشاء عملية |
| 2 | تغيير وقت إنشاء ملف |
| 3 | اتصال شبكي |
| 5 | إنهاء عملية |
| 6 | تحميل برنامج تشغيل |
| 7 | تحميل صورة تنفيذية (DLL) |
| 8 | إنشاء خيط تنفيذ عن بُعد (CreateRemoteThread) |
| 10 | الوصول إلى عملية (ProcessAccess) |
| 11 | إنشاء ملف (FileCreate) |
| 12-14 | أحداث السجل |
| 15 | بصمة تدفق ملف (FileCreateStreamHash) |
| 17-18 | أحداث الأنابيب |
| 22 | استعلام DNS |

<a id="powershell-sysmon-queries"></a>
### استعلامات Sysmon باستخدام PowerShell
```powershell
# إنشاء عملية (الحدث 1)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='CommandLine';E={$_.Properties[10].Value}}

# اتصالات الشبكة (الحدث 3)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=3} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='DestIP';E={$_.Properties[14].Value}}

# استعلامات DNS (الحدث 22)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=22} |
    Select-Object TimeCreated, @{N='Query';E={$_.Properties[4].Value}}
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="suspicious-patterns-to-look-for"></a>
### أنماط مشبوهة ينبغي البحث عنها

| الفئة | النمط |
|----------|---------|
| **Brute Force** | أحداث 4625 متعددة من عنوان IP نفسه |
| **Spray Attack** | أحداث 4625 لمستخدمين متعددين من عنوان IP نفسه |
| **الحركة الجانبية** | 4624 النوع 3 + 4648 |
| **الاستمرارية** | 4698 (مهمة مجدولة جديدة) |
| **Privesc** | 4672 لحسابات غير إدارية |
| **استخراج بيانات الاعتماد** | الوصول إلى lsass.exe |

<a id="log-analysis-tools"></a>
### أدوات تحليل السجلات
| الأداة | الاستخدام |
|------|-----|
| **grep/awk/sed** | تحليل سجلات Linux واستخراج حقولها |
| **PowerShell** | تحليل سجلات Windows |
| **Splunk** | منصة SIEM للمؤسسات |
| **ELK Stack** | منصة SIEM مفتوحة المصدر |
| **LogParser** | استعلامات SQL على السجلات |
| **Chainsaw** | تحليل سريع لسجلات Windows |

---

<a id="-related-cheatsheets"></a>
## 🔗 مراجع ذات صلة

- [الاستجابة للحوادث](Incident-Response.ar.md)
- [الكشف باستخدام SIEM](SIEM-Detection.ar.md)
- [قواعد Sigma](Sigma-Rules.ar.md)

---

**السابق:** [← الاستجابة للحوادث](Incident-Response.ar.md)

**التالي:** [الكشف باستخدام SIEM →](SIEM-Detection.ar.md)

> **توضيح:** الحدث 4672 يعني منح امتيازات خاصة لجلسة تسجيل دخول جديدة، وليس دليلًا كافيًا وحده على رفع الامتيازات. تختلف مسارات سجلات Linux حسب التوزيعة وإعداد التسجيل. [مرجع Microsoft للحدث 4672](https://learn.microsoft.com/en-us/previous-versions/windows/it-pro/windows-10/security/threat-protection/auditing/event-4672).
