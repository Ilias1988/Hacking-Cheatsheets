<a id="-crypto-ctf-cheatsheet"></a>
# 🔐 مرجع تحديات التشفير في CTF

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Crypto-CTF.tr.md) • [🇬🇷 Ελληνικά](Crypto-CTF.el.md)

---

<a id="-encodingdecoding"></a>
## 🔤 الترميز وفك الترميز

<a id="base64"></a>
### Base64
```bash
echo "string" | base64           # ترميز
echo "c3RyaW5n" | base64 -d      # فك الترميز
```

<a id="hex"></a>
### Hex
```bash
echo "string" | xxd -p           # ترميز
echo "737472696e67" | xxd -r -p  # فك الترميز
```

<a id="rot13"></a>
### ROT13
```bash
echo "string" | tr 'A-Za-z' 'N-ZA-Mn-za-m'
```

<a id="ascii"></a>
### ASCII
```python
# Python
chr(65)      # → 'A'
ord('A')     # → 65
```

---

<a id="-rsa"></a>
## 🔑 RSA

<a id="basic-rsa-math"></a>
### الرياضيات الأساسية لـ RSA
```
n = p * q           (المعامل العلني)
φ(n) = (p-1)(q-1)   (دالة أويلر)
d = e⁻¹ mod φ(n)    (المفتاح الخاص)
c = m^e mod n       (التشفير)
m = c^d mod n       (فك التشفير)
```

<a id="rsactftool"></a>
### RsaCtfTool
```bash
# تثبيت
pip3 install rsactftool

# هجوم
rsactftool --publickey pub.pem --private
rsactftool --publickey pub.pem --uncipherfile cipher.txt
```

<a id="factorization"></a>
### التحليل إلى عوامل
```
factordb.com - تحليل الأعداد إلى عوامل عبر الإنترنت
```

---

<a id="-hash-cracking"></a>
## 🔒 كسر البصمات (Hashes)

<a id="identify-hash"></a>
### تحديد نوع البصمة
```bash
hashid 'hash_value'
hash-identifier
```

<a id="crack"></a>
### كسر البصمة
```bash
# John
john --wordlist=rockyou.txt hash.txt

# Hashcat
hashcat -m 0 hash.txt rockyou.txt      # MD5
hashcat -m 100 hash.txt rockyou.txt    # SHA1
hashcat -m 1400 hash.txt rockyou.txt   # SHA256
```

<a id="online"></a>
### عبر الإنترنت
```
crackstation.net
hashes.com
```

---

<a id="-xor"></a>
## 🧮 XOR

```python
# عملية XOR في Python
def xor(data, key):
    return bytes([b ^ key[i % len(key)] for i, b in enumerate(data)])

# تجربة جميع مفاتيح XOR المكوّنة من بايت واحد
for key in range(256):
    result = bytes([b ^ key for b in ciphertext])
```

---

<a id="-tools"></a>
## 🛠️ الأدوات

| الأداة | الاستخدام |
|------|-----|
| **CyberChef** | gchq.github.io/CyberChef |
| **dcode.fr** | الترميزات وخوارزميات التشفير المختلفة |
| **RsaCtfTool** | هجمات RSA |
| **hashcat/john** | كسر البصمات |
| **factordb** | تحليل الأعداد الصحيحة إلى عوامل |

---

<a id="-crypto-checklist"></a>
## 📋 قائمة تحديات التشفير

```markdown
□ تحديد الترميز (base64 أو hex أو rot13)
□ تحديد نوع خوارزمية التشفير
□ فحص حالات ضعف RSA (قيمة e صغيرة أو عددان أوليان متقاربان)
□ تجربة قواعد البحث عن البصمات
□ فحص وجود مفتاح XOR متكرر
□ استخدام عملية Magic في CyberChef
```

---

**العودة إلى CTF:** [🏁 نظرة عامة على CTF](README.ar.md)

> **توضيح للمثال الرياضي:** صيغة دالة أويلر المعروضة تفترض أن p وq عددان أوليان مختلفان. الأس `^` في مخطط RSA ترميز رياضي للأسس، وليس عامل رفع إلى قوة في Python. كذلك لا تعني قيمة e صغيرة وحدها أن RSA قابل للكسر دون شروط إضافية. أمثلة XOR تستخدم المتغير ciphertext بوصفه مدخلًا يجب توفيره، وليست برنامجًا كاملًا قائمًا بذاته.
