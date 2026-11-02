<a id="-domain--ip-osint"></a>
# 🌐 استخبارات المصادر المفتوحة للنطاقات وعناوين IP

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Domain-IP-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Domain-IP-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-domain-reconnaissance-tools"></a>
## 🔍 أدوات استطلاع النطاقات

| الأداة | URL | الغرض |
|------|-----|---------|
| **WHOIS** | who.is | تسجيل النطاقات |
| **SecurityTrails** | securitytrails.com | سجل DNS التاريخي |
| **ViewDNS** | viewdns.info | أدوات DNS |
| **BuiltWith** | builtwith.com | التقنيات المستخدمة |
| **Wappalyzer** | wappalyzer.com | كشف التقنيات |

---

<a id="-commands"></a>
## 🛠️ الأوامر

<a id="whois"></a>
### WHOIS
```bash
whois target.com
whois 1.2.3.4
```

<a id="dns-enumeration"></a>
### استطلاع DNS
```bash
# سجلات DNS
dig target.com ANY
dig target.com MX
dig target.com TXT
nslookup -type=any target.com

# نقل منطقة DNS
dig axfr @ns1.target.com target.com
host -t axfr target.com ns1.target.com
```

<a id="subdomain-discovery"></a>
### اكتشاف النطاقات الفرعية
```bash
# Subfinder
subfinder -d target.com -o subs.txt

# Amass
amass enum -d target.com -o subs.txt

# Assetfinder
assetfinder target.com
```

---

<a id="-ip-osint"></a>
## 🔎 استخبارات المصادر المفتوحة لعناوين IP

| الأداة | URL | الغرض |
|------|-----|---------|
| **Shodan** | shodan.io | البحث عن الأجهزة |
| **Censys** | censys.io | فحص الإنترنت |
| **IPinfo** | ipinfo.io | تحديد الموقع الجغرافي التقريبي لعنوان IP |
| **AbuseIPDB** | abuseipdb.com | سمعة عنوان IP |

<a id="shodan-cli"></a>
### Shodan CLI
```bash
shodan host 1.2.3.4
shodan search "hostname:target.com"
shodan domain target.com
```

<a id="ip-lookup"></a>
### الاستعلام عن عنوان IP
```bash
curl ipinfo.io/1.2.3.4
curl ip-api.com/json/1.2.3.4
whois 1.2.3.4
```

---

<a id="-domain-checklist"></a>
## 📋 قائمة فحص النطاق

```markdown
□ سجلات WHOIS
□ سجلات DNS من الأنواع A وMX وTXT وNS
□ النطاقات الفرعية
□ سجل DNS التاريخي (SecurityTrails)
□ التقنيات المستخدمة
□ معلومات شهادة SSL
□ نطاقات عناوين IP
□ البحث العكسي حسب عنوان IP
□ معلومات رقم النظام المستقل (ASN)
```

---

**العودة إلى OSINT:** [🔍 نظرة عامة على OSINT](README.ar.md)

> **ملاحظة تحديث:** أُبقيت أمثلة WHOIS الأصلية. طُوّر RDAP ليحل محل WHOIS ويقدّم بيانات التسجيل بصيغة موحّدة؛ لذلك يمكن الرجوع إلى RDAP أيضًا عند البحث عن بيانات تسجيل النطاق. [مرجع ICANN الرسمي](https://www.icann.org/en/contracted-parties/registry-operators/resources/registration-data-access-protocol). DNS هو نظام أسماء النطاقات، وASN رقم النظام المستقل في توجيه الشبكات.
