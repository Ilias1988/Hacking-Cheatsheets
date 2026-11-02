# ⚡ OWASP ZAP – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)
> **Navn og versjoner:** ZAP er ikke lenger et OWASP-prosjekt; det er et selvstendig prosjekt styrt av ZAP Core Team og støttet av Checkmarx. Navnet og fellesskapstabellen er bevart fra originalen; se [ZAP Ownership](https://www.zaproxy.org/docs/zap-ownership/). Priser, utgavefunksjoner, snarveier, standardverdier og UI-stier er historiske eksempler, ikke aktuelle garantier. Engelske meny- og innstillingsnavn er beholdt som konkrete grensesnittverdier. En proxy videresender trafikk, en spider følger lenker, og en fuzzer varierer inndata.


```
   ____  _    _  ___   _____ _____    _____   ___  _____  
  / __ \| |  | |/ _ \ / ____|  __ \  |__  /  / _ \|  __ \ 
 | |  | | |  | | | | | (___ | |__) |   / /  / /_\ \ |__) |
 | |  | | |/\| | |_| |\___ \|  ___/   / /  |  _  |  ___/ 
 | |__| \  /\  / | | |____) | |      / /__ | | | | |     
  \____/ \/  \/|_| |_|_____/|_|     /_____|_| |_|_|     
                                                          
    Zed Attack Proxy – gratis verktøy for sikkerhetstesting
```

<p align="center">
  <img src="https://img.shields.io/badge/OWASP-ZAP-blue?style=for-the-badge" alt="OWASP ZAP">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/Open-Source-green?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Free-Tool-purple?style=for-the-badge" alt="Free">
</p>

<p align="center">
  <b>🔒 En mye brukt gratis skanner for websikkerhet</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Oversikt over grensesnittet](#-oversikt-over-grensesnittet)
- [Proxyoppsett](#-proxyoppsett)
- [Manuell testing](#-manuell-testing)
- [Spider og crawler](#-spider-og-crawler)
- [Aktiv skanning](#-aktiv-skanning)
- [Passiv skanning](#-passiv-skanning)
- [Kontekster og økter](#-kontekster-og-økter)
- [Fuzzer](#-fuzzer)
- [API og automatisering](#-api-og-automatisering)
- [Kommandolinje](#-kommandolinje)
- [Tillegg](#-tillegg)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

**OWASP ZAP** (Zed Attack Proxy) er et gratis verktøy med åpen kildekode for penetrasjonstesting, omtalt i originalen som vedlikeholdt av OWASP. Det er laget for å finne sikkerhetssårbarheter i webapplikasjoner.

### Hvorfor ZAP?

| Egenskap | Beskrivelse |
|---------|-------------|
| **Gratis og åpen kildekode** | Ingen lisenskostnader, drevet av fellesskapet |
| **Enkelt å bruke** | Nybegynnervennlig med kraftige funksjoner |
| **Automatisk skanning** | Spider og aktiv/passiv skanning |
| **Manuell testing** | Avskjærende proxy og fuzzer |
| **API-støtte** | Fullt REST-API for automatisering |
| **Flere plattformer** | Windows, Linux, macOS, Docker |
| **Utvidbart** | Marketplace med 100+ tillegg |

### ZAP sammenlignet med Burp Suite

| Egenskap | ZAP | Burp Suite |
|---------|-----|------------|
| **Pris** | Gratis | $449+/year |
| **Åpen kildekode** | ✅ Ja | ❌ Nei |
| **Automatisk skanning** | ✅ Full | Bare Pro |
| **API-tilgang** | ✅ Gratis | Bare Pro |
| **Aktiv utvikling** | ✅ Ja | ✅ Ja |
| **Fellesskap** | OWASP | PortSwigger |

---

## 📥 Installasjon

### Windows
```bash
# Last ned fra det offisielle nettstedet
https://www.zaproxy.org/download/

# Eller bruk Chocolatey
choco install owasp-zap
```

### Linux (Kali/Ubuntu)
```bash
# Kali (forhåndsinstallert)
zaproxy

# Ubuntu/Debian
sudo apt install zaproxy

# Snap
sudo snap install zaproxy --classic
```

### macOS
```bash
# Homebrew
brew install --cask owasp-zap

# Last ned DMG fra det offisielle nettstedet
```

### Docker
```bash
# Hent nyeste avbildning
docker pull ghcr.io/zaproxy/zaproxy:stable

# Kjør ZAP som daemon
docker run -u zap -p 8080:8080 -i ghcr.io/zaproxy/zaproxy:stable zap.sh -daemon -host 0.0.0.0 -port 8080

# Kjør med grafisk grensesnitt (Linux)
docker run -u zap -p 8080:8080 -p 8090:8090 -v /tmp:/zap/wrk/:rw \
    --rm -it ghcr.io/zaproxy/zaproxy:stable zap-webswing.sh
```

### Kontroller installasjonen
```bash
# Start ZAP
zaproxy
zap.sh       # Linux
zap.bat      # Windows
```

---

<a id="-oversikt-over-grensesnittet"></a>
## 🖥️ Oversikt over grensesnittet

### Hovedpaneler

| Panel | Beskrivelse |
|-------|-------------|
| **Sites Tree** | Hierarkisk oversikt over besøkte nettsteder |
| **History** | Alle forespørsler og responser |
| **Alerts** | Oppdagede sårbarheter |
| **Active Scan** | Fremdrift for gjeldende skanning |
| **Spider** | URL-er som er gjennomgått |
| **Search** | Søk i alle data |
| **Request/Response** | Vis detaljer |

### Ikoner på verktøylinjen

| Ikon | Funksjon |
|------|----------|
| 🌐 | Quick Start-skanning |
| 🔍 | Spider |
| ⚡ | Active Scan |
| 🛑 | Break/Intercept (avskjæring) |
| 📊 | Rapporter |
| ⚙️ | Innstillinger |

### Moduser

| Modus | Beskrivelse |
|------|-------------|
| **Safe** | Ingen farlige operasjoner |
| **Protected** | Skann bare mål innenfor omfanget |
| **Standard** | Full skanning (standard) |
| **ATTACK** | Aggressiv skanning |

---

## 🔌 Proxyoppsett

### Nettleseroppsett

**Standardproxy i ZAP:**
```
Host: localhost (127.0.0.1)
Port: 8080
```

### Oppsett av Firefox
```
1. Settings → Network Settings → Manual proxy
2. HTTP Proxy: 127.0.0.1  Port: 8080
3. Kryss av for "Also use this proxy for HTTPS"
4. Eller bruk FoxyProxy-utvidelsen
```

### Importer ZAP-sertifikatet
```
1. Gå til http://zap i nettleseren
2. Klikk "Download ZAP Root CA Certificate"
3. Importer i nettleseren:
   Firefox: Settings → Privacy → Certificates → Import
   Chrome: Settings → Security → Manage certificates
```

### Konfigurer ZAPs proxyvalg
```
Tools → Options → Local Proxies

Innstillinger:
- Address: localhost
- Port: 8080
- Behind NAT: Aktiver ved behov
- Remove unsupported encodings: Aktiver
```

### Oppstrømsproxy (proxykjede)
```
Tools → Options → Network → Connection

Bruk proxykjede:
- Address: corporate-proxy.com
- Port: 8080
- Autentisering ved behov
```

---

## 🔧 Manuell testing

### Avskjære forespørsler (Break)

```
1. Klikk den grønne "Break"-knappen (🛑) på verktøylinjen
2. Utforsk applikasjonen
3. Endre den avskårne forespørselen
4. Klikk "Submit and Continue" eller "Drop"

Break-valg:
- Stopp ved alle forespørsler
- Stopp ved alle responser
- Stopp ved bestemte URL-mønstre
```

### Redigering av forespørsler

```
Høyreklikk forespørselen → Open/Resend with Request Editor

Egenskaper:
- Endre en hvilken som helst del av forespørselen
- Legg til eller fjern headere
- Endre HTTP-metoden
- Send og vis responsen
```

### Manuell forespørsel
```
Tools → Manual Request Editor

Eller: Ctrl+M

Opprett egne forespørsler fra grunnen av
```

### Koding, dekoding og hashing
```
Tools → Encode/Decode/Hash

Støtter:
- URL-koding og -dekoding
- Base64-koding og -dekoding
- HTML-koding og -dekoding
- Hex-koding og -dekoding
- Hashing med MD5, SHA1 og SHA256
```

### Søkefunksjon
```
Search-fanen → skriv inn mønster

Søk i:
- URL-er
- Forespørsler
- Responser
- Headere
- Alt
```

---

<a id="-spider-og-crawler"></a>
## 🕷️ Spider og crawler

### Standard Spider

```
Høyreklikk målet → Spider → Spider...

Eller: Ctrl+Alt+S

Valg:
- Maksimal dybde for gjennomgang
- Maksimalt antall undernoder
- Maksimal varighet
- Håndtering av parametere
```

### Spider-valg
```
Tools → Options → Spider

Innstillinger:
- Max Depth: 5-10 (standard: 5)
- Max Children: 10
- Max Duration: 0 (ubegrenset)
- Thread Count: 5
- Handle forms: Ja
- Post forms: Ja
```

### AJAX Spider

```
For applikasjoner med mye JavaScript:

Høyreklikk målet → AJAX Spider → AJAX Spider...

Krever nettleser:
- Firefox (anbefalt)
- Chrome
- HtmlUnit (uten grafisk grensesnitt)
```

### AJAX Spider-valg
```
Tools → Options → AJAX Spider

Innstillinger:
- Browser: Firefox
- Max Crawl Depth: 10
- Max Crawl States: 0 (ubegrenset)
- Max Duration: 60 minutter
- Klikk standardelementer
- Klikk elementer med blant annet onclick
```

---

## ⚡ Aktiv skanning

### Start aktiv skanning

```
Høyreklikk målet → Active Scan...

Eller: Ctrl+Alt+A

Konfigurer:
- Startpunkt
- Kontekst (valgfritt)
- Bruker (for autentisering)
- Policy
- Teknologi
```

### Skannepolicyer

```
Analyze → Scan Policy Manager

Opprett eller endre policyer:
- Hvilke tester som skal kjøres
- Threshold (hvor sterk mistanke som kreves)
- Strength (hvor grundig testen skal være)
```

### Policykategorier

| Kategori | Tester |
|----------|-------|
| **Injection** | SQL, OS-kommandoer, LDAP |
| **Client Browser** | XSS, DOM XSS |
| **Server Security** | Ekstern filinkludering |
| **Information** | Stitraversering og informasjonslekkasje |
| **Miscellaneous** | Brukerstyrte videresendinger |

### Skannestyrke

| Nivå | Beskrivelse |
|-------|-------------|
| **Low** | Få forespørsler, raskt |
| **Medium** | Balansert |
| **High** | Mange forespørsler, grundig |
| **Insane** | Maksimalt antall tester (tregt) |

### Valg av teknologi
```
Høyreklikk målet → Technology → Select...

Filtrer skanningene etter teknologi:
- PHP, ASP, JSP
- MySQL, PostgreSQL, Oracle
- Apache, IIS, Nginx
- Linux, Windows
```

---

<a id="-passiv-skanning"></a>
## 👁️ Passiv skanning

### Hva er passiv skanning?

Analyserer trafikk uten å sende flere forespørsler.

```
Automatisk – kjører på trafikken gjennom proxyen

Finner:
- Manglende sikkerhetsheadere
- Informasjonslekkasje
- Problemer med informasjonskapsler
- Svak SSL/TLS
- Eksponerte private IP-adresser
```

### Regler for passiv skanning

```
Analyze → Scan Policy Manager → Passive Scan

Reglene omfatter:
- Application Error Disclosure (feilmeldinger)
- Cookie Without Secure Flag (manglende Secure-flagg)
- Cross-Domain JavaScript (JavaScript på tvers av domener)
- Information Disclosure (informasjonslekkasje)
- Private IP Disclosure (privat IP-adresse)
- X-Frame-Options Header Missing (manglende header)
```

### Konfigurer passiv skanning
```
Tools → Options → Passive Scan

Innstillinger:
- Scan only in scope: Anbefalt
- Max alerts per rule: 10
- Tags: Aktiver
```

---

## 🎯 Kontekster og økter

### Hva er kontekster?

Logiske grupper av tilknyttede URL-er for organisert testing.

### Opprett kontekst
```
Høyreklikk Sites → Include in Context → New Context

Eller: File → New Context
```

### Kontekstinnstillinger

| Innstilling | Formål |
|---------|---------|
| **Include in Context** | URL-er som skal tas med |
| **Exclude from Context** | URL-er som skal utelates |
| **Technology** | Serverteknologi |
| **Authentication** | Påloggingsmetode |
| **Session Management** | Håndtering av informasjonskapsler og tokener |
| **Users** | Testbrukerkontoer |

### Autentiseringstyper

| Type | Bruksområde |
|------|----------|
| **Form-based** | Påloggingsskjemaer |
| **HTTP Basic** | Basic-autentisering |
| **JSON-based** | API-autentisering |
| **Script-based** | Egendefinert autentisering |

### Skjemabasert autentisering
```
1. Registrer påloggingsforespørselen i ZAP
2. Right-click → Include in Context
3. Context → Authentication → Form-based
4. Angi Login URL
5. Angi Login Request POST Data
6. Konfigurer brukernavn- og passordparametere
7. Angi indikatorer for innlogget og utlogget tilstand
```

### Økthåndtering
```
Context → Session Management

Typer:
- Cookie-based (vanligst)
- HTTP Auth
- Script-based (skriptbasert)
```

### Brukere
```
Context → Users → Add

Legg til testbrukere:
- Name: admin
- Credentials: admin/password123
```

---

## 🎲 Fuzzer

### Start Fuzzer
```
1. Velg en forespørsel i History
2. Right-click → Attack → Fuzz...
3. Marker posisjonen som skal fuzzes
4. Legg til payloads
5. Start
```

### Legg til en fuzz-posisjon
```
1. Marker teksten som skal fuzzes i Fuzzer-dialogen
2. Klikk "Add..."
3. Velg payload-type
```

### Payload-typer

| Type | Beskrivelse |
|------|-------------|
| **Strings** | Egen liste over strenger |
| **File** | Last fra fil |
| **File Fuzzers** | Innebygde ordlister |
| **Numbers** | Tallsekvenser |
| **Regex** | Generert fra regulært uttrykk |
| **Script** | Eget skript |

### Innebygde fuzzere
```
Add → File Fuzzers

Kategorier:
- jbrofuzz (XSS, SQL osv.)
- dirbuster (kataloger)
- fuzzdb (omfattende)
```

### Fuzzer-valg
```
Valg:
- Samtidige tråder
- Forsinkelse mellom forespørsler
- Følg videresendinger
- Vis alle resultater eller filtrer
```

### Fuzzer-eksempel: fuzzing av parametere
```
1. Fang opp: GET /page?id=1
2. Right-click → Fuzz
3. Marker "1"
4. Legg til payloads for SQL-injeksjon
5. Start fuzzeren
6. Analyser responslengde og statuskode
```

---

## 🤖 API og automatisering

### Aktiver API-et
```
Tools → Options → API

Innstillinger:
- Enabled: Yes
- API Key: (automatisk generert eller egendefinert)
- Permitted addresses: localhost
```

### API-tilgang
```bash
# API-rot
http://localhost:8080/JSON/

# Med API-nøkkel
http://localhost:8080/JSON/core/view/alerts/?apikey=YOUR_API_KEY

# Hent versjon
curl "http://localhost:8080/JSON/core/view/version/?apikey=API_KEY"
```

### Vanlige API-endepunkter

| Endepunkt | Formål |
|----------|---------|
| `/core/view/alerts/` | Hent alle varsler |
| `/core/view/hosts/` | Hent alle verter |
| `/spider/action/scan/` | Start Spider |
| `/ascan/action/scan/` | Start aktiv skanning |
| `/core/view/urls/` | Hent alle URL-er |
| `/core/action/shutdown/` | Stopp ZAP |

### API-eksempler

```bash
# Gjennomgå en URL med Spider
curl "http://localhost:8080/JSON/spider/action/scan/?url=http://target.com&apikey=API_KEY"

# Kontroller Spider-status
curl "http://localhost:8080/JSON/spider/view/status/?apikey=API_KEY"

# Start aktiv skanning
curl "http://localhost:8080/JSON/ascan/action/scan/?url=http://target.com&apikey=API_KEY"

# Hent varsler
curl "http://localhost:8080/JSON/core/view/alerts/?apikey=API_KEY"

# Generer HTML-rapport
curl "http://localhost:8080/OTHER/core/other/htmlreport/?apikey=API_KEY" > report.html
```

### Python-klient for API-et

> **Presisering:** Skanningene i Python-eksemplet startes asynkront; koden venter ikke på at de blir ferdige før den henter varsler. `htmlreport` under core er et eldre API; dokumentasjonen viser til rapporttilleggets generate-endepunkt. Koden er bevart; se [ZAPs API-dokumentasjon](https://www.zaproxy.org/docs/api/). Baseline-skanning innebærer også nettlesing/spidertrafikk; passiv analyse betyr ikke at hele arbeidsflyten er uten forespørsler.
```python
from zapv2 import ZAPv2

# Koble til ZAP
zap = ZAPv2(apikey='your-api-key', proxies={'http': 'http://127.0.0.1:8080'})

# Spider
zap.spider.scan(url='http://target.com')

# Aktiv skanning
zap.ascan.scan(url='http://target.com')

# Hent varsler
alerts = zap.core.alerts()
for alert in alerts:
    print(f"{alert['risk']}: {alert['name']}")
```

---

<a id="-kommandolinje"></a>
## ⌨️ Kommandolinje

### Grunnleggende kommandolinjevalg
```bash
# Start ZAP som daemon
zap.sh -daemon

# Start på en bestemt port
zap.sh -daemon -port 8090

# Rask skanning
zap.sh -quickurl http://target.com -quickout report.html

# Baseline-skanning (Docker)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t http://target.com
```

### Automation Framework
```bash
# Kjør automatisert skanning
zap.sh -cmd -autorun automation.yaml
```

### Eksempel på automasjons-YAML
```yaml
env:
  contexts:
    - name: "Default Context"
      urls:
        - "http://target.com"

jobs:
  - type: spider
    parameters:
      url: "http://target.com"
      maxDuration: 5
      
  - type: passiveScan-wait
    parameters:
      maxDuration: 5
      
  - type: activeScan
    parameters:
      scanOnlyInScope: true
      
  - type: report
    parameters:
      template: "traditional-html"
      reportFile: "report.html"
```

### Docker-skanninger

```bash
# Baseline Scan (bare passiv)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://target.com \
    -r report.html

# Full Scan (aktiv)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-full-scan.py \
    -t https://target.com \
    -r report.html

# API-skanning
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py \
    -t https://target.com/api/openapi.json \
    -f openapi \
    -r report.html
```

---

## 🧩 Tillegg

### Installer tillegg
```
Help → Check for Updates → Marketplace

Eller: Tools → Add-ons → Marketplace
```

### Viktige tillegg

| Tillegg | Beskrivelse |
|--------|-------------|
| **Active Scan Rules** | Flere skanneregler |
| **Passive Scan Rules** | Flere passive regler |
| **FuzzDB** | Payloads for fuzzing |
| **Retire.js** | Oppdag sårbare JavaScript-biblioteker |
| **AJAX Spider** | Crawler med JavaScript-støtte |
| **Selenium** | Nettleserautomatisering |
| **Wappalyzer** | Identifisering av teknologi |
| **Access Control** | Testing av tilgangskontroll |
| **Directory List** | Ordlister med katalognavn |

### Administrere tillegg
```
# List installerte tillegg
Tools → Add-ons → Installed

# Oppdater alle
Help → Check for Updates → Update All
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: Rask webskanning
```
1. Skriv URL-en i Quick Start-fanen
2. Klikk "Attack"
3. ZAP gjennomgår og skanner automatisk
4. Gjennomgå varslene når skanningen er ferdig
```

### Eksempel 2: Manuell og automatisk testing
```
1. Sett nettleserens proxy til ZAP
2. Utforsk applikasjonen manuelt (alle funksjoner)
3. Høyreklikk målet i Sites → Spider
4. Vent til Spider er ferdig
5. Høyreklikk målet → Active Scan
6. Gjennomgå varsler
7. Generer rapport
```

### Eksempel 3: Autentisert skanning
```
1. Opprett en Context for målet
2. Konfigurer Form-based Authentication
3. Legg til testbrukerens påloggingsopplysninger
4. Konfigurer forced user mode
5. Kjør Spider som autentisert bruker
6. Active Scan
```

### Eksempel 4: API-testing
```
1. Importer API-definisjonen (OpenAPI/Swagger)
2. File → Import → Import OpenAPI definition
3. Gjennomgå API-et med Spider
4. Active Scan
```

### Eksempel 5: CI/CD-integrasjon
```bash
# Jenkins Pipeline
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://staging.myapp.com \
    -r zap_report.html \
    -x zap_report.xml
```

---

## 📊 Hurtigreferanse

### Viktige handlinger

| Oppgave | Metode |
|------|--------|
| Start proxy | ZAP starter automatisk på port 8080 |
| Spider | Right-click → Spider |
| Active Scan | Right-click → Active Scan |
| Avskjær | Klikk Break-knappen (🛑) |
| Fuzz | Right-click request → Fuzz |
| Generer rapport | Report → Generate Report |

### Tastatursnarveier

| Snarvei | Handling |
|----------|--------|
| `Ctrl+Alt+S` | Spider-dialog |
| `Ctrl+Alt+A` | Active Scan-dialog |
| `Ctrl+M` | Manuell forespørsel |
| `Ctrl+B` | Slå Break av/på |
| `Ctrl+F` | Finn |
| `Ctrl+R` | Send forespørselen på nytt |

### Skannemoduser

| Modus | Brukes når |
|------|----------|
| **Safe** | Du ikke ønsker å gjøre endringer |
| **Protected** | Du tester bare et bestemt omfang |
| **Standard** | Vanlig testing |
| **ATTACK** | Aggressiv testing (med tillatelse!) |

---

## 💡 Tips og anbefalte arbeidsmåter

### Ytelsestips

1. **Begrens omfanget**
   ```
   Definer en kontekst og skann bare elementer innenfor omfanget
   ```

2. **Bruk AJAX Spider til SPA-er**
   ```
   Moderne apper trenger mer enn bare standard Spider
   ```

3. **Velg teknologi**
   ```
   Velg bare relevante teknologier for å redusere skannetiden
   ```

### Tips for nøyaktighet

1. **Autentiser først**
   ```
   Konfigurer autentisering for bedre dekning
   ```

2. **Utforsk manuelt før automatisering**
   ```
   Gå gjennom appen manuelt først for å finne alle funksjoner
   ```

3. **Gjennomgå alle varsler**
   ```
   Se etter falske positive og bekreft reelle funn
   ```

### Arbeidsflyt

```
1. Konfigurer proxyen
2. Manuell utforsking (med autentisering)
3. Spider (standard + AJAX)
4. Gjennomgang av passiv skanning
5. Aktiv skanning
6. Manuell testing (fuzzing og grensetilfeller)
7. Generer rapport
```

---

## ⚠️ Juridisk merknad

> **ADVARSEL:** OWASP ZAP er et sikkerhetsverktøy som bare skal brukes til **autorisert testing**.
> 
> - ✅ Test applikasjoner du eier
> - ✅ Test med uttrykkelig skriftlig tillatelse
> - ✅ Bruk i test- og stagingmiljøer
> - ❌ Skann aldri produksjon uten tillatelse
> - ❌ Skann aldri systemer du ikke eier
> 
> **Uautorisert sikkerhetstesting er ulovlig og kan føre til straffeforfølgning.**

---

## 📚 Ressurser

### Offisielle ressurser
- [OWASP ZAP-nettstedet](https://www.zaproxy.org/)
- [ZAP-dokumentasjon](https://www.zaproxy.org/docs/)
- [ZAP på GitHub](https://github.com/zaproxy/zaproxy)
- [ZAPs API-dokumentasjon](https://www.zaproxy.org/docs/api/)

### Læringsressurser
- [OWASPs testveiledning](https://owasp.org/www-project-web-security-testing-guide/)
- [ZAP in Ten](https://www.zaproxy.org/zap-in-ten/)
- [ZAP Deep Dive-videoer](https://www.zaproxy.org/docs/videos/)

### Relaterte hurtigreferanser
- [Burp Suite](../../Burp-Suite/translations/README.nb.md)
- [Nikto](../../Nikto/translations/README.nb.md)
- [SQLMap](../../SQLMap/translations/README.nb.md)

---

<p align="center">
  <b>⚡ Lær sikkerhetstesting av webapplikasjoner!</b><br>
  <i>Gratis, kraftig og drevet av fellesskapet</i>
</p>
