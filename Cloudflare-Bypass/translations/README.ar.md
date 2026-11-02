<a id="-cloudflare-bypass-cheatsheet"></a>
# ☁️ ورقة مرجعية لتجاوز Cloudflare

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗██╗      ██████╗ ██╗   ██╗██████╗ ███████╗██╗      █████╗ ██████╗ ███████╗
  ██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗██╔════╝██║     ██╔══██╗██╔══██╗██╔════╝
  ██║     ██║     ██║   ██║██║   ██║██║  ██║█████╗  ██║     ███████║██████╔╝█████╗  
  ██║     ██║     ██║   ██║██║   ██║██║  ██║██╔══╝  ██║     ██╔══██║██╔══██╗██╔══╝  
  ╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝██║     ███████╗██║  ██║██║  ██║███████╗
   ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
                    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
                    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
                    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
                    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
                    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
                    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

الخادم الأصلي (Origin) هو الخادم الذي يستضيف التطبيق خلف وسيط Cloudflare. يحافظ الرسم أعلاه على تنسيقه الأصلي. يشير WAF إلى جدار حماية تطبيقات الويب، وDNS إلى نظام أسماء النطاقات.

<a id="-detect-cloudflare"></a>
## 🎯 التعرّف على استخدام Cloudflare

<a id="headers"></a>
### الترويسات (Headers)
```bash
curl -I https://target.com

# ابحث عن:
cf-ray: XXXXX-YYY
server: cloudflare
cf-cache-status: DYNAMIC
cf-request-id: XXXX
```

<a id="dns"></a>
### DNS
```bash
dig target.com

# نطاقات عناوين IP الخاصة بـ Cloudflare:
# 104.16.0.0/12
# 172.64.0.0/13
# 131.0.72.0/22
# 173.245.48.0/20
# 103.21.244.0/22
# 103.22.200.0/22
# 103.31.4.0/22
```

---

<a id="-find-origin-ip"></a>
## 🌐 العثور على عنوان IP للخادم الأصلي

<a id="1-dns-history-tools"></a>
### 1. أدوات سجل DNS التاريخي

| الخدمة | URL |
|---------|-----|
| SecurityTrails | https://securitytrails.com |
| ViewDNS | https://viewdns.info/iphistory |
| DNSDumpster | https://dnsdumpster.com |
| CrimeFlare | http://www.crimeflare.org:82/cfs.html |
| CloudFlair | أداة على GitHub |

```bash
# استخدام CloudFlair
python3 cloudflair.py target.com

# Bypass-Firewalls-by-DNS-History
./bypass-firewalls-by-DNS-history.sh target.com
```

<a id="2-subdomain-discovery"></a>
### 2. اكتشاف النطاقات الفرعية
```bash
# قد تكشف نطاقات فرعية عديدة عن الخادم الأصلي
subfinder -d target.com | httpx -ip

# نطاقات فرعية شائعة قد تشير إلى IP الخادم الأصلي:
mail.target.com
ftp.target.com
cpanel.target.com
webmail.target.com
direct.target.com
origin.target.com
server.target.com
api.target.com
stage.target.com
dev.target.com
old.target.com
```

<a id="3-ssl-certificate-search"></a>
### 3. البحث في شهادات SSL
```bash
# البحث عبر Censys.io
# Query: parsed.names: target.com AND NOT ip: 104.*

# شفافية الشهادات (Certificate Transparency)
curl -s "https://crt.sh/?q=%.target.com&output=json" | jq -r '.[].common_name' | sort -u

# البحث عبر Shodan
shodan search "ssl.cert.subject.cn:target.com"
```

<a id="4-email-analysis"></a>
### 4. تحليل البريد الإلكتروني
```bash
# أرسل رسالة إلى عنوان بريد غير موجود
# افحص ترويسات رسالة تعذّر التسليم المرتدة

# أو اشترك في النشرات البريدية
# افحص ترويسات Received: بحثًا عن IP الخادم الأصلي

# ابحث في الترويسات التالية:
Received: from [ORIGIN_IP]
X-Originating-IP: [IP]
X-PM-Message-Id: (sometimes contains origin)
```

<a id="5-favicon-hash-shodan"></a>
### 5. بصمة أيقونة الموقع (Favicon Hash) عبر Shodan
```python
import mmh3
import requests
import codecs

# احسب بصمة أيقونة الموقع
response = requests.get('https://target.com/favicon.ico')
favicon = codecs.encode(response.content, 'base64')
hash = mmh3.hash(favicon)
print(f"Favicon hash: {hash}")

# البحث عبر Shodan: http.favicon.hash:{hash}
```

```bash
# Shodan CLI
shodan search "http.favicon.hash:HASH_HERE"
```

<a id="6-historical-whois"></a>
### 6. سجلات WHOIS التاريخية
```bash
# افحص سجلات WHOIS التاريخية بحثًا عن عناوين IP قديمة
# whoxy.com
# domaintools.com
```

<a id="7-website-source-code"></a>
### 7. الشيفرة المصدرية للموقع
```bash
# ابحث عن عناوين IP مكتوبة مباشرة في:
# - ملفات JavaScript
# - تعليقات HTML
# - نقاط نهاية API
# - رسائل الخطأ

curl -s https://target.com | grep -oE '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'

# افحص ملفات JavaScript
curl -s https://target.com/main.js | grep -oE 'https?://[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'
```

<a id="8-mx-records"></a>
### 8. سجلات MX
```bash
dig MX target.com

# قد يستخدم خادم البريد عنوان IP نفسه الذي يستخدمه الخادم الأصلي
nslookup mail.target.com
```

<a id="9-spf-records"></a>
### 9. سجلات SPF
```bash
dig TXT target.com | grep spf

# قد يتضمن سجل SPF عنوان IP للخادم الأصلي
# v=spf1 ip4:1.2.3.4 include:_spf.google.com ~all
```

---

<a id="-direct-connection"></a>
## 🔥 الاتصال المباشر

<a id="test-found-ip"></a>
### اختبار عنوان IP المكتشف
```bash
# بعد الحصول على عنوان IP محتمل للخادم الأصلي:
curl -k -H "Host: target.com" https://ORIGIN_IP/

# أو عدّل /etc/hosts
echo "ORIGIN_IP target.com" >> /etc/hosts

# ثم تصفّح الموقع كالمعتاد
curl https://target.com
```

<a id="verify-origin"></a>
### التحقق من الخادم الأصلي
```bash
# تحقق من تطابق المحتوى
diff <(curl -s https://target.com) <(curl -sk -H "Host: target.com" https://ORIGIN_IP/)

# افحص شهادة SSL
openssl s_client -connect ORIGIN_IP:443 -servername target.com
```

---

<a id="-cloudflare-waf-bypass"></a>
## 🛡️ تجاوز جدار حماية تطبيقات الويب (WAF) في Cloudflare

<a id="rate-limiting-bypass"></a>
### تجاوز تحديد معدل الطلبات (Rate Limiting)
```http
# بدّل قيم هذه الترويسات
X-Forwarded-For: RANDOM_IP
CF-Connecting-IP: RANDOM_IP
True-Client-IP: RANDOM_IP
X-Real-IP: RANDOM_IP
```

<a id="bot-protection-bypass"></a>
### تجاوز الحماية من الروبوتات
```bash
# استخدم ترويسة user-agent لمتصفح حقيقي
curl -A "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" https://target.com

# استخدم curl-impersonate
curl_chrome116 https://target.com
```

<a id="turnstilechallenge-bypass"></a>
### تجاوز Turnstile والتحديات
```bash
# استخدم أتمتة المتصفح
# - Puppeteer مع إضافة stealth
# - Playwright
# - undetected-chromedriver

# FlareSolverr (Docker)
docker run -p 8191:8191 flaresolverr/flaresolverr
```

---

<a id="-tools"></a>
## 🛠️ الأدوات

| الأداة | الغرض |
|------|---------|
| **CloudFlair** | العثور على الخادم الأصلي عبر Censys |
| **CrimeFlare** | قاعدة بيانات لعناوين IP الخاصة بالخوادم الأصلية |
| **Bypass-Firewalls-by-DNS-History** | فحص سجل DNS التاريخي |
| **FlareSolverr** | أداة لحل تحديات المتصفح |
| **curl-impersonate** | محاكاة بصمة TLS الخاصة بالمتصفح |
| **CloudBunny** | أداة لتجاوز Cloudflare |

<a id="cloudflair-usage"></a>
### استخدام CloudFlair
```bash
git clone https://github.com/christophetd/CloudFlair
cd CloudFlair
pip install -r requirements.txt

# اضبط بيانات اعتماد Censys API
export CENSYS_API_ID=YOUR_ID
export CENSYS_API_SECRET=YOUR_SECRET

python cloudflair.py target.com
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="origin-ip-checklist"></a>
### قائمة التحقق للعثور على IP الخادم الأصلي
```markdown
- [ ] سجل DNS التاريخي (SecurityTrails, ViewDNS)
- [ ] النطاقات الفرعية (mail, ftp, cpanel, dev)
- [ ] شهادات SSL ‏(Censys, crt.sh)
- [ ] Shodan (بصمة أيقونة الموقع، SSL)
- [ ] ترويسات البريد الإلكتروني
- [ ] سجلات MX/SPF
- [ ] تحليل الشيفرة المصدرية
- [ ] سجلات WHOIS التاريخية
- [ ] مقارنة نطاقات IP (خارج نطاقات Cloudflare)
```

<a id="cloudflare-ip-ranges"></a>
### نطاقات عناوين IP الخاصة بـ Cloudflare
```
173.245.48.0/20
103.21.244.0/22
103.22.200.0/22
103.31.4.0/22
141.101.64.0/18
108.162.192.0/18
190.93.240.0/20
188.114.96.0/20
197.234.240.0/22
198.41.128.0/17
162.158.0.0/15
104.16.0.0/13
104.24.0.0/14
172.64.0.0/13
131.0.72.0/22
```

---

<a id="-resources"></a>
## 📚 المراجع

- [نطاقات عناوين IP الخاصة بـ Cloudflare](https://www.cloudflare.com/ips/)
- [أداة CloudFlair](https://github.com/christophetd/CloudFlair)
- [CrimeFlare](http://www.crimeflare.org:82/cfs.html)
- [FlareSolverr](https://github.com/FlareSolverr/FlareSolverr)

---



> **توضيحات الدقة والتوافق:** حُفظت الأمثلة الأصلية كما هي. النطاق `104.16.0.0/12` الوارد في التعليق الأول قديم؛ استُبدل في القائمة الرسمية منذ 2021 بالنطاقين `104.16.0.0/13` و`104.24.0.0/14`. قائمة المرجع السريع في هذا الملف تشمل IPv4 فقط؛ تتوفر نطاقات IPv6 أيضًا في [القائمة الرسمية](https://www.cloudflare.com/ips/).
>
> تدوير ترويسات IP لا يضمن تجاوز تحديد معدل الطلبات: تعتمد النتيجة على خصائص العدّ المعرّفة في القاعدة، ويضيف Cloudflare عنوان العميل في `CF-Connecting-IP` عند تمرير الطلب إلى الخادم الأصلي. لا تعادل قيمة يرسلها العميل عنوان الاتصال الفعلي. راجع [ترويسات Cloudflare](https://developers.cloudflare.com/fundamentals/reference/http-headers/) و[معلمات تحديد معدل الطلبات](https://developers.cloudflare.com/waf/rate-limiting-rules/parameters/).
>
> مثال الاتصال بعنوان IP مع ترويسة `Host` يغيّر ترويسة HTTP، وقد لا يطابق اسم المضيف المطلوب في TLS/SNI؛ كما أن `-k` يعطّل التحقق من الشهادة. لا يكفي تشابه المحتوى وحده لإثبات أن العنوان هو الخادم الأصلي. تشرح وثائق curl [تخصيص الاتصال دون تغيير اسم TLS](https://curl.se/docs/manpage.html#--connect-to) و[التحقق من الشهادة](https://curl.se/docs/sslcerts.html). وقد تمنع حماية الخادم الأصلي الوصول المباشر حتى إن كان عنوانه معروفًا؛ راجع [حماية الخادم الأصلي](https://developers.cloudflare.com/fundamentals/security/protect-your-origin-server/).
>
> أسماء أدوات الأتمتة ومحاكاة المتصفح أمثلة من المصدر وليست ضمانًا لتجاوز Turnstile أو تحديات Cloudflare. توضّح [وثائق اختبار Turnstile](https://developers.cloudflare.com/turnstile/troubleshooting/testing/) أن المتصفحات المؤتمتة قد تُكتشف وتُحظر، وتوصي بمفاتيح الاختبار المخصّصة عند اختبار التكامل في بيئة تملكها. كذلك، ترويسات البريد ونتائج البحث التاريخية قرائن تحتاج إلى تحقق؛ لا تثبت بمفردها أن عنوانًا ما يستضيف تطبيق الويب حاليًا.

<p align="center">
  <b>☁️ اعثر على الخادم الأصلي!</b><br>
  <i>للاختبارات المصرّح بها فقط!</i>
</p>
