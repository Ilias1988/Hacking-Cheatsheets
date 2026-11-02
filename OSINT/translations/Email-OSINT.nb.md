# 📧 E-post-OSINT – hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Email-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Email-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

```
███████╗███╗   ███╗ █████╗ ██╗██╗     
██╔════╝████╗ ████║██╔══██╗██║██║     
█████╗  ██╔████╔██║███████║██║██║     
██╔══╝  ██║╚██╔╝██║██╔══██║██║██║     
███████╗██║ ╚═╝ ██║██║  ██║██║███████╗
╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚══════╝
```

---

## 🔍 Verktøy for å finne e-postadresser

| Verktøy | URL | Formål |
|------|-----|---------|
| **Hunter.io** | hunter.io | Finn virksomheters e-postadresser |
| **Phonebook.cz** | phonebook.cz | Gratis søk etter e-postadresser |
| **Snov.io** | snov.io | Søk etter e-postadresser |
| **Clearbit Connect** | clearbit.com | Berikelse av e-postopplysninger |
| **RocketReach** | rocketreach.co | Søk etter kontaktpersoner |
| **Voila Norbert** | voilanorbert.com | E-postoppslag |

---

## 🛠️ Kommandoer for kartlegging av e-postadresser

### theHarvester
```bash
# Innsamling av e-postadresser
theHarvester -d target.com -l 500 -b all
theHarvester -d target.com -b google,linkedin,bing
theHarvester -d target.com -b all -f emails.html
```

### Hunter.io fra kommandolinjen
```bash
# API-spørring
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Kontroll av e-postadresse
curl "https://api.hunter.io/v2/email-verifier?email=test@target.com&api_key=YOUR_KEY"
```

### h8mail (søk i datalekkasjer)
```bash
pip3 install h8mail
h8mail -t target@email.com
h8mail -t target@email.com -c h8mail_config.ini
```

### holehe (kontroll av registreringer)
```bash
pip3 install holehe
holehe target@email.com
holehe target@email.com --only-used
```

---

## 📧 Mønstre for e-postformater

```bash
# Vanlige adresseformater i virksomheter
first.last@company.com     # john.smith@company.com
flast@company.com          # jsmith@company.com
firstl@company.com         # johns@company.com
first_last@company.com     # john_smith@company.com
first@company.com          # john@company.com
last.first@company.com     # smith.john@company.com
```

### Generering av adressevarianter
```python
# Generer varianter av e-postadressen
names = ["john", "smith"]
domain = "company.com"

patterns = [
    f"{names[0]}.{names[1]}@{domain}",
    f"{names[0][0]}{names[1]}@{domain}",
    f"{names[0]}{names[1][0]}@{domain}",
    f"{names[0]}_{names[1]}@{domain}",
    f"{names[0]}@{domain}",
]
```

---

## 🔐 Kontroll av datalekkasjer

| Tjeneste | URL | Type |
|---------|-----|------|
| **Have I Been Pwned** | haveibeenpwned.com | Gratis |
| **DeHashed** | dehashed.com | Betalt |
| **LeakCheck** | leakcheck.io | Betalt |
| **Snusbase** | snusbase.com | Betalt |
| **IntelX** | intelx.io | Gratis grunnnivå med betalte tillegg |

### HIBP API

> **Presisering:** «Gratis» i tjenesteoversikten betyr ikke at alle API-er eller søk er gratis. Kontroller tilgangskrav og abonnement for API-endepunktet du bruker.
```bash
curl -H "hibp-api-key: YOUR_KEY" \
  "https://haveibeenpwned.com/api/v3/breachedaccount/test@example.com"
```

---

## ✅ Kontroll av e-postadresser

### Kontrollmetoder
```
1. Kontroll av MX-oppføring - Domenet har en e-postserver
2. SMTP-kontroll - Postkassen finnes
3. Syntakskontroll - Gyldig e-postformat
4. Kontroll av engangsadresse - Adressen er ikke midlertidig
5. Kontroll av rolleadresse - Adressen er ikke generell (info@, admin@)
```

### Enkel SMTP-kontroll

> **Rettelse:** `250` ved `RCPT TO` betyr at SMTP-serveren godtar forespørselen på det tidspunktet; det beviser ikke at en bestemt postkasse finnes. `550` kan også skyldes tilgangs- eller policyregler. Den forenklede kommentaren i originalen er bevart med henvisning hit. Se [RFC 5321](https://datatracker.ietf.org/doc/html/rfc5321).
```bash
# Kontroller MX-oppføringer
nslookup -type=mx target.com
dig mx target.com

# Kontroll med Telnet
telnet mail.target.com 25
HELO test.com
MAIL FROM:<test@test.com>
RCPT TO:<target@target.com>
# 250 = finnes, 550 = finnes ikke (forenkling fra originalen; se merknaden)
```

---

## 📋 Sjekkliste for e-post-OSINT

```markdown
## Måladresse: [email@domain.com]

□ Kontroller om e-postadressen finnes (SMTP/Hunter)
□ Sjekk datalekkasjer (HIBP)
□ Finn tilknyttede kontoer (holehe)
□ Søk i Google/Bing
□ Kontroller registreringer i sosiale medier
□ Se etter offentlige profiler
□ Søk på nettsteder for deling av tekstutdrag
□ Sjekk yrkesnettverk
```

---

**Tilbake til OSINT:** [🔍 OSINT-oversikt](./README.nb.md)
