<a id="-siem-detection-cheatsheet"></a>
# 📈 مرجع الكشف باستخدام SIEM

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SIEM-Detection.tr.md) • [🇬🇷 Ελληνικά](SIEM-Detection.el.md)

```
  ███████╗██╗███████╗███╗   ███╗    ██████╗ ███████╗████████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝██║██╔════╝████╗ ████║    ██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
  ███████╗██║█████╗  ██╔████╔██║    ██║  ██║█████╗     ██║   █████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
  ╚════██║██║██╔══╝  ██║╚██╔╝██║    ██║  ██║██╔══╝     ██║   ██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
  ███████║██║███████╗██║ ╚═╝ ██║    ██████╔╝███████╗   ██║   ███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝╚══════╝╚═╝     ╚═╝    ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

<a id="-splunk-queries-spl"></a>
## 🔶 استعلامات Splunk بلغة SPL

<a id="authentication--access"></a>
### المصادقة والوصول

<a id="brute-force-detection"></a>
#### اكتشاف التخمين الشامل (Brute Force)
```spl
# محاولات دخول فاشلة متعددة من عنوان IP نفسه
index=windows EventCode=4625
| stats count by src_ip, user
| where count > 10
| sort -count

# محاولات فاشلة تليها محاولة ناجحة (اشتباه باختراق بيانات الاعتماد)
index=windows (EventCode=4625 OR EventCode=4624)
| transaction user maxspan=10m
| where eventcount > 5 AND match(EventCode, "4624")
| table _time, user, src_ip, EventCode
```

<a id="password-spray-detection"></a>
#### اكتشاف رش كلمات المرور (رش كلمات المرور)
```spl
# تجربة كلمة مرور واحدة على حسابات متعددة
index=windows EventCode=4625
| stats dc(user) as unique_users, count by src_ip
| where unique_users > 5 AND count > 10
| sort -unique_users

# مستخدمون متعددون من عنوان IP واحد خلال مدة قصيرة
index=windows EventCode=4625
| bucket _time span=5m
| stats dc(user) as unique_users, values(user) as users by _time, src_ip
| where unique_users > 5
```

<a id="successful-admin-logons"></a>
#### عمليات تسجيل الدخول الإدارية الناجحة
```spl
# عمليات دخول حسابات المسؤولين
index=windows EventCode=4624 LogonType=10
| search user IN ("Administrator", "admin", "*admin*")
| table _time, user, src_ip, dest

# عمليات الدخول خارج ساعات العمل
index=windows EventCode=4624
| eval hour=strftime(_time, "%H")
| where hour < 7 OR hour > 19
| stats count by user, src_ip
```

<a id="process--execution"></a>
### العمليات والتنفيذ

<a id="suspicious-process-creation"></a>
#### إنشاء عمليات مشبوهة
```spl
# أوامر PowerShell المرمّزة
index=windows EventCode=4688 
| search CommandLine="*-enc*" OR CommandLine="*-encoded*" OR CommandLine="*FromBase64*"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# علاقات مشبوهة بين العمليات الأم والتابعة
index=windows EventCode=4688
| search ParentProcessName="*excel.exe" OR ParentProcessName="*word.exe" OR ParentProcessName="*outlook.exe"
| search NewProcessName="*cmd.exe" OR NewProcessName="*powershell.exe" OR NewProcessName="*wscript.exe"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# تنفيذ أدوات Windows الموثوقة على نحو مسيء (LOLBAS)
index=windows EventCode=4688
| search NewProcessName IN ("*certutil.exe", "*mshta.exe", "*regsvr32.exe", "*rundll32.exe", "*bitsadmin.exe")
| table _time, user, NewProcessName, CommandLine
```

<a id="mimikatz-detection"></a>
#### اكتشاف Mimikatz
```spl
# كلمات Mimikatz الدالة داخل العملية
index=windows EventCode=4688
| search CommandLine="*sekurlsa*" OR CommandLine="*kerberos::*" OR CommandLine="*lsadump*"
| table _time, user, NewProcessName, CommandLine

# الوصول إلى LSASS (حدث Sysmon رقم 10)
index=sysmon EventCode=10 TargetImage="*lsass.exe"
| table _time, SourceImage, SourceUser, GrantedAccess
```

<a id="persistence-mechanisms"></a>
### آليات الاستمرارية

<a id="scheduled-task-creation"></a>
#### إنشاء المهام المجدولة
```spl
# مهام مجدولة جديدة
index=windows EventCode=4698
| table _time, user, TaskName, TaskContent

# أسماء مهام مجدولة مشبوهة
index=windows EventCode=4698
| rex field=TaskContent "<Command>(?<Command>[^<]+)</Command>"
| table _time, user, TaskName, Command
```

<a id="service-installation"></a>
#### تثبيت الخدمات
```spl
# خدمات جديدة مثبتة
index=windows EventCode=7045
| table _time, ServiceName, ServiceFileName, ServiceType, StartType

# مسارات خدمات مشبوهة
index=windows EventCode=7045
| search ServiceFileName="*temp*" OR ServiceFileName="*appdata*" OR ServiceFileName="*.ps1*"
| table _time, ServiceName, ServiceFileName
```

<a id="registry-modifications"></a>
#### تعديلات السجل
```spl
# تعديلات مفاتيح Run (أحداث Sysmon من 12 إلى 14)
index=sysmon EventCode IN (12, 13, 14)
| search TargetObject="*\\Run\\*" OR TargetObject="*\\RunOnce\\*"
| table _time, EventCode, Image, TargetObject, Details
```

<a id="lateral-movement"></a>
### الحركة الجانبية

<a id="rdp-activity"></a>
#### نشاط RDP
```spl
# اتصالات RDP (النوع 10)
index=windows EventCode=4624 LogonType=10
| stats count by src_ip, dest, user
| sort -count

# اتصالات RDP بمضيفين متعددين من مصدر واحد
index=windows EventCode=4624 LogonType=10
| stats dc(dest) as unique_hosts, values(dest) as hosts by src_ip, user
| where unique_hosts > 3
```

<a id="psexecwmi-detection"></a>
#### اكتشاف PSExec/WMI
```spl
# نشاط شبيه بـ PSExec
index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"
| table _time, src_ip, dest, ServiceFileName

# تنفيذ عن بُعد باستخدام WMI
index=windows EventCode=4688 
| search ParentProcessName="*WmiPrvSE.exe"
| table _time, user, dest, NewProcessName, CommandLine
```

<a id="data-exfiltration"></a>
### تسريب البيانات

<a id="large-data-transfers"></a>
#### نقل كميات كبيرة من البيانات
```spl
# حجم مرتفع من الحركة الصادرة
index=firewall action=allowed direction=outbound
| stats sum(bytes_out) as total_bytes by src_ip, dest_ip
| where total_bytes > 100000000
| eval MB=round(total_bytes/1024/1024,2)
| table src_ip, dest_ip, MB

# تسريب عبر DNS (استعلامات طويلة)
index=dns
| eval query_length=len(query)
| where query_length > 50
| stats count by src_ip, query
```

---

<a id="-elasticsearchkibana-kql--lucene"></a>
## 🟢 Elasticsearch/Kibana (KQL & Lucene)

<a id="authentication--access-1"></a>
### المصادقة والوصول

<a id="brute-force-detection-1"></a>
#### اكتشاف التخمين الشامل (Brute Force)
```kql
# محاولات الدخول الفاشلة في Windows
event.code: 4625 AND winlog.event_data.LogonType: 3

# محاولات دخول فاشلة متعددة (بالتجميع)
event.code: 4625
# استخدم بعد ذلك عرضًا مرئيًا للتجميع حسب source.ip

# تسجيل دخول ناجح بعد محاولات فاشلة
event.code: (4625 OR 4624)
```

<a id="password-spray"></a>
#### رش كلمات المرور
```kql
# فشل المصادقة من عنوان IP واحد لمستخدمين متعددين
event.code: 4625 AND source.ip: *
# جمّع حسب source.ip واحسب قيم user.name الفريدة
```

<a id="process--execution-1"></a>
### العمليات والتنفيذ

<a id="suspicious-processes"></a>
#### عمليات مشبوهة
```kql
# PowerShell مرمّز
event.code: 4688 AND process.command_line: (*-enc* OR *-encoded* OR *FromBase64*)

# LOLBAS
event.code: 4688 AND process.name: (certutil.exe OR mshta.exe OR regsvr32.exe OR rundll32.exe)

# إنشاء Office لصدفة أوامر
event.code: 4688 AND process.parent.name: (WINWORD.EXE OR EXCEL.EXE OR OUTLOOK.EXE) AND process.name: (cmd.exe OR powershell.exe)
```

<a id="mimikatz-detection-1"></a>
#### اكتشاف Mimikatz
```kql
# عملية تتضمن كلمات mimikatz الدالة
process.command_line: (*sekurlsa* OR *kerberos::* OR *lsadump* OR *mimikatz*)

# الوصول إلى LSASS
event.code: 10 AND winlog.event_data.TargetImage: *lsass.exe
```

<a id="persistence"></a>
### الاستمرارية

<a id="scheduled-tasks"></a>
#### المهام المجدولة
```kql
# إنشاء مهمة
event.code: 4698

# أسماء مهام مشبوهة
event.code: 4698 AND winlog.event_data.TaskName: (*update* OR *sync* OR *helper*)
```

<a id="services"></a>
#### الخدمات
```kql
# تثبيت خدمة
event.code: 7045

# مسارات خدمات مشبوهة
event.code: 7045 AND winlog.event_data.ImagePath: (*temp* OR *appdata* OR *.ps1)
```

<a id="lateral-movement-1"></a>
### الحركة الجانبية

<a id="rdp"></a>
#### RDP
```kql
# عمليات دخول RDP
event.code: 4624 AND winlog.event_data.LogonType: 10

# اتصالات RDP من مصدر غير معتاد
event.code: 4624 AND winlog.event_data.LogonType: 10 AND NOT source.ip: 192.168.*
```

<a id="psexec"></a>
#### PsExec
```kql
# خدمة PsExec
event.code: 7045 AND winlog.event_data.ServiceFileName: *PSEXESVC*
```

<a id="useful-aggregations-elasticsearch-dsl"></a>
### عمليات تجميع مفيدة (Elasticsearch DSL)
```json
{
  "aggs": {
    "failed_logins_by_ip": {
      "terms": {
        "field": "source.ip",
        "size": 10
      },
      "aggs": {
        "unique_users": {
          "cardinality": {
            "field": "user.name"
          }
        }
      }
    }
  },
  "query": {
    "bool": {
      "must": [
        { "match": { "event.code": 4625 }}
      ]
    }
  }
}
```

---

<a id="-detection-by-mitre-attck"></a>
## 📊 الكشف حسب MITRE ATT&CK

<a id="initial-access-ta0001"></a>
### الوصول الأولي (TA0001)
| التقنية | استعلام Splunk |
|-----------|--------------|
| التصيد | `index=email \| search subject="*urgent*" OR attachment="*.exe"` |
| حسابات صالحة | `index=windows EventCode=4624 LogonType=10 user!="SYSTEM"` |

<a id="execution-ta0002"></a>
### التنفيذ (TA0002)
| التقنية | استعلام Splunk |
|-----------|--------------|
| PowerShell | `index=windows EventCode=4688 NewProcessName="*powershell*"` |
| البرمجة النصية | `index=windows EventCode=4688 NewProcessName IN ("*wscript*", "*cscript*")` |

<a id="persistence-ta0003"></a>
### الاستمرارية (TA0003)
| التقنية | استعلام Splunk |
|-----------|--------------|
| مهمة مجدولة | `index=windows EventCode=4698` |
| مفاتيح Run في السجل | `index=sysmon EventCode=13 TargetObject="*\\Run\\*"` |
| خدمة | `index=windows EventCode=7045` |

<a id="privilege-escalation-ta0004"></a>
### رفع الامتيازات (TA0004)
| التقنية | استعلام Splunk |
|-----------|--------------|
| التلاعب برموز الوصول | `index=windows EventCode=4672 NOT user="SYSTEM"` |
| تجاوز UAC | `index=sysmon EventCode=1 Image="*fodhelper*"` |

<a id="defense-evasion-ta0005"></a>
### تفادي الدفاعات (TA0005)
| التقنية | استعلام Splunk |
|-----------|--------------|
| مسح السجلات | `index=windows EventCode=1102` |
| التمويه بانتحال المظهر | `index=sysmon EventCode=1 Image="*temp*\\svchost.exe"` |

<a id="credential-access-ta0006"></a>
### الوصول إلى بيانات الاعتماد (TA0006)
| التقنية | استعلام Splunk |
|-----------|--------------|
| تفريغ ذاكرة LSASS | `index=sysmon EventCode=10 TargetImage="*lsass.exe"` |
| استخراج بيانات الاعتماد | `index=windows EventCode=4688 CommandLine="*sekurlsa*"` |

<a id="lateral-movement-ta0008"></a>
### الحركة الجانبية (TA0008)
| التقنية | استعلام Splunk |
|-----------|--------------|
| RDP | `index=windows EventCode=4624 LogonType=10` |
| PSExec | `index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"` |
| WMI | `index=windows EventCode=4688 ParentProcessName="*WmiPrvSE*"` |

<a id="exfiltration-ta0010"></a>
### تسريب البيانات (TA0010)
| التقنية | استعلام Splunk |
|-----------|--------------|
| نفق DNS | `index=dns \| eval len=len(query) \| where len>50` |
| الويب | `index=proxy bytes_out>10000000` |

---

<a id="-related-cheatsheets"></a>
## 🔗 مراجع ذات صلة

- [تحليل السجلات](Log-Analysis.ar.md)
- [قواعد Sigma](Sigma-Rules.ar.md)
- [صيد التهديدات](Threat-Hunting.ar.md)

---

**السابق:** [← تحليل السجلات](Log-Analysis.ar.md)

**التالي:** [صيد التهديدات →](Threat-Hunting.ar.md)

> **ملاحظة تطبيقية:** هذه الاستعلامات أمثلة أولية تحتاج إلى مطابقة أسماء الحقول ومصادر السجلات في بيئتك. المؤشرات المعروضة ترجّح نشاطًا يستحق التحقيق ولا تثبت بمفردها التقنية المسماة؛ مثلًا، فشل المصادقة مع حسابات متعددة لا يكشف قيمة كلمة المرور التي جُرّبت. SIEM هو نظام إدارة معلومات الأمن والأحداث، وSPL لغة استعلامات Splunk.
