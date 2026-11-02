# 🔶 Burp Suite – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)
> **Versjonsmerknad:** Priser, utgavenavn, krav, menystier og snarveier gjengir originalens eksempler, ikke dagens bekreftede tilbud. Spider/Extender og flere Options-stier er fra eldre grensesnitt; se [PortSwiggers dokumentasjon](https://portswigger.net/burp/documentation/desktop). Konkrete engelske UI-navn og stier er beholdt slik at de kan gjenkjennes; norsk tekst forklarer handlingene. Snarveier kan tilpasses, og originalen gir `Ctrl+Shift+T` to ulike betydninger; se [Hotkey-innstillinger](https://portswigger.net/burp/documentation/desktop/settings/ui/hotkeys). Proxy avskjærer trafikk, scope avgrenser testomfanget, og fuzzing prøver variasjoner av inndata.


```
    ____                      _____       _ __     
   / __ )__  ___________     / ___/__  __(_) /____
  / __  / / / / ___/ __ \    \__ \/ / / / / __/ _ \
 / /_/ / /_/ / /  / /_/ /   ___/ / /_/ / / /_/  __/
/_____/\__,_/_/  / .___/   /____/\__,_/_/\__/\___/ 
                /_/                                
    Plattform for sikkerhetstesting av webapplikasjoner
```

<p align="center">
  <img src="https://img.shields.io/badge/Burp-Suite-orange?style=for-the-badge" alt="Burp Suite">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/PortSwigger-blue?style=for-the-badge" alt="PortSwigger">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🌐 En etablert plattform for sikkerhetstesting av webapplikasjoner</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon og oppsett](#-installasjon-og-oppsett)
- [Proxyoppsett](#-proxyoppsett)
- [Avskjæring av trafikk](#-avskjæring-av-trafikk)
- [Mål og omfang](#-mål-og-omfang)
- [Spider/crawler](#-spidercrawler)
- [Scanner](#-scanner)
- [Repeater](#-repeater)
- [Intruder](#-intruder)
- [Decoder](#-decoder)
- [Comparer](#-comparer)
- [Sequencer](#-sequencer)
- [Utvidelser](#-utvidelser)
- [Tastatursnarveier](#-tastatursnarveier)
- [Arbeidsflyt for testing](#-arbeidsflyt-for-testing)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

**Burp Suite** er en samlet plattform for sikkerhetstesting av webapplikasjoner. Den utvikles av PortSwigger og er mye brukt til penetrasjonstesting av webapplikasjoner.

### Utgaver

| Utgave | Egenskaper | Pris |
|---------|----------|-------|
| **Community** | Grunnleggende proxy og manuelle verktøy | Gratis |
| **Professional** | Scanner, Intruder (ubegrenset), Collaborator | $449/year |
| **Enterprise** | CI/CD-integrasjon og teamfunksjoner | Kontakt salgsavdelingen |

### Hovedverktøy

| Verktøy | Beskrivelse |
|------|-------------|
| **Proxy** | Avskjær og endre HTTP-/HTTPS-trafikk |
| **Spider** | Gjennomgå webapplikasjoner automatisk |
| **Scanner** | Finn sårbarheter automatisk |
| **Intruder** | Automatiserte, tilpassede angrep |
| **Repeater** | Manuell endring av forespørsler |
| **Decoder** | Kod og dekod data |
| **Comparer** | Sammenlign responser |
| **Sequencer** | Analyser tilfeldighet i tokener |

---

## 📥 Installasjon og oppsett

### Nedlasting

```bash
# Last ned fra det offisielle nettstedet
https://portswigger.net/burp/releases

# Tilgjengelig for:
# - Windows (installasjonsprogram/selvstendig utgave)
# - macOS (DMG)
# - Linux (skript/JAR)
```

### Systemkrav

| Krav | Minimum | Anbefalt |
|-------------|---------|-------------|
| RAM | 4 GB | 8+ GB |
| Diskplass | 500 MB | 2+ GB |
| Java | JRE 8+ | Medfølgende JRE |
| Skjerm | 1280x800 | 1920x1080 |

### Oppsett ved første start

1. **Velg prosjekttype**
   - Midlertidig prosjekt (testing)
   - Nytt prosjekt på disk (lagre arbeidet)
   - Åpne eksisterende prosjekt

2. **Konfigurasjon**
   - Bruk Burps standardinnstillinger (anbefalt for nybegynnere)
   - Last fra konfigurasjonsfil

3. **Minnetildeling**
   ```bash
   # Øk minnet for store prosjekter
   java -jar -Xmx4g burpsuite_pro.jar
   ```

---

## 🔌 Proxyoppsett

### Nettleseroppsett

#### Manuelt proxyoppsett

| Nettleser | Plassering av proxyinnstillinger |
|---------|------------------------|
| Firefox | Settings → Network → Manual proxy |
| Chrome | Bruker systemproxy eller utvidelse |
| Safari | System Preferences → Network → Proxies |

**Standardproxy i Burp:**
```
Host: 127.0.0.1
Port: 8080
```

#### Oppsett av Firefox
```
1. Åpne Firefox Preferences
2. Søk etter "proxy"
3. Klikk "Settings..."
4. Velg "Manual proxy configuration"
5. HTTP Proxy: 127.0.0.1  Port: 8080
6. Kryss av for "Also use this proxy for HTTPS"
7. Klikk OK
```

#### FoxyProxy-utvidelse (anbefalt)
```
1. Installer FoxyProxy-utvidelsen
2. Legg til en ny proxy:
   - Title: Burp Suite
   - Proxy Type: HTTP
   - Proxy IP: 127.0.0.1
   - Port: 8080
3. Aktiver den under testing
```

### Installasjon av SSL-/TLS-sertifikat

```bash
# Trinn 1: Slå på proxyen og besøk:
http://burp

# Trinn 2: Klikk "CA Certificate" for å laste ned

# Trinn 3: Importer sertifikatet:
# Firefox: Settings → Certificates → Import → Trust for websites
# Chrome: Settings → Security → Manage certificates → Import
# System: Legg til blant klarerte rotsertifikater
```

### Proxylyttere

```
Proxy → Options → Proxy Listeners

Standard: 127.0.0.1:8080

Legg til egne lyttere:
- Ulike porter for ulike tester
- Bind til alle grensesnitt (0.0.0.0) for mobiltesting
- Videresend til andre verter
```

### Usynlig proxy (for klienter uten proxystøtte)

```
1. Proxy → Options → Proxy Listeners
2. Edit listener → Request handling
3. Aktiver "Support invisible proxying"
4. Konfigurer hosts-filen eller DNS
```

---

## 🎣 Avskjæring av trafikk

### Kontroller for avskjæring

| Knapp | Funksjon |
|--------|----------|
| **Intercept is on/off** | Slå avskjæring av eller på |
| **Forward** | Send forespørselen til serveren |
| **Drop** | Forkast forespørselen |
| **Action** | Meny med flere valg |

### Valg for avskjæring

```
Proxy → Options → Intercept Client Requests

Eksempler på regler:
- Avskjær bare elementer innenfor omfanget
- Avskjær bare bestemte filtyper
- Utelat bilder og statiske filer
```

### Endring av forespørsler

```http
# Opprinnelig forespørsel
GET /page.php?id=1 HTTP/1.1
Host: target.com

# Endret forespørsel (mens den er avskåret)
GET /page.php?id=1' OR '1'='1 HTTP/1.1
Host: target.com
```

### Samsvar og erstatning (Match and Replace)

```
Proxy → Options → Match and Replace

Vanlige regler:
- Fjern sikkerhetsheadere
- Endre User-Agent
- Legg til egne headere
- Erstatt forespørselsparametere
```

| Type | Samsvar | Erstatt | Formål |
|------|-------|---------|---------|
| Request header | `User-Agent: .*` | `User-Agent: Custom` | Endre UA |
| Request body | `password=test` | `password=admin` | Test påloggingsopplysninger |
| Response header | `X-Frame-Options.*` | ` ` | Fjern header |
| Response body | `disabled` | `enabled` | Aktiver knapper |

### Endring av responser

```
Proxy → Options → Intercept Server Responses

Aktiver for å:
- Fjern sikkerhetsheadere
- Endre JavaScript
- Endre responsinnhold
- Teste kontroller på klientsiden
```

---

## 🎯 Mål og omfang

### Legg til i omfanget

```
Metode 1: Høyreklikk i Site map → Add to scope
Metode 2: Target → Scope → Add (manuelt)
Metode 3: Fra Proxy history → Add to scope
```

### Konfigurasjon av omfang

```
Target → Scope

Innenfor omfanget:
- Protocol: Any/HTTP/HTTPS
- Host/IP: target.com
- Port: Any/Specific
- File: Regex pattern

Eksempelmønstre:
.*\.target\.com$          # Alle underdomener
^https://target\.com/app  # Bestemt sti
```

### Utvidede omfangsinnstillinger

```
Target → Scope → Advanced scope control

Ta med:
  Protocol: HTTPS
  Host: target.com
  Port: 443
  File: ^/api/.*

Utelat:
  Host: .*\.google\.com
  File: .*\.(jpg|png|gif|css|js)$
```

### Nettstedskart (Site Map)

```
Target → Site map

Egenskaper:
- Vis alt oppdaget innhold
- Filtrer etter omfang
- Søk i og filtrer innhold
- Fremhev interessante elementer
- Sammenlign nettstedskart
```

---

<a id="-spidercrawler"></a>
## 🕷️ Spider/crawler

### Starte Spider

```
1. Høyreklikk målet i Site map
2. Velg "Spider this host" eller "Spider this branch"
3. Eller: Target → Site map → Right-click → Scan → Crawl
```

### Spider-innstillinger

```
Spider → Options (eldre utgave) ELLER
Dashboard → New scan → Crawl

Crawler-innstillinger:
- Maksimal lenkedybde
- Maksimal gjennomgangstid
- Innsending av skjemaer
- Håndtering av pålogging
```

### Oppsett av gjennomgang

| Innstilling | Beskrivelse | Anbefalt |
|---------|-------------|-------------|
| **Link depth** | Hvor langt lenker skal følges | 5-10 |
| **Max requests** | Grense for totalt antall forespørsler | 1000-5000 |
| **Duplicate removal** | Hopp over lignende sider | Aktivert |
| **Passive scanning** | Skann under gjennomgang | Aktivert |

### Innsending av skjemaer

```
Spider → Options → Application Login

Konfigurer pålogging med:
- Ett sett påloggingsopplysninger
- Liste over påloggingsopplysninger
- Øktgjenkjenning
```

---

## 🔍 Scanner

### Skannetyper

| Type | Beskrivelse | Bruksområde |
|------|-------------|----------|
| **Passive** | Analyser proxytrafikk | Alltid på |
| **Active** | Send payloads for å finne sårbarheter | Med tillatelse |
| **Full** | Omfattende skanning | Når tiden tillater det |
| **Custom** | Bare bestemte tester | Målrettet testing |

### Starte en skanning

```
Metode 1: Right-click → Scan
Metode 2: Dashboard → New scan
Metode 3: Send to Scanner fra andre verktøy
```

### Oppsett av skanning

```
Dashboard → New scan → Scan configuration

Innebygde konfigurasjoner:
- Audit checks - all except JavaScript analysis
- Audit checks - critical issues only
- Audit checks - light, active
- Audit checks - medium, active
- Audit checks - full, active
```

### Sårbarhetskategorier

```
Scanner ser etter:

Injeksjon:
- SQL-injeksjon
- OS-kommandoinjeksjon
- LDAP-injeksjon
- XML-/XPath-injeksjon

Cross-Site Scripting:
- Reflektert XSS
- Lagret XSS
- DOM-basert XSS

Autentisering:
- Mangelfull autentisering
- Problemer med økthåndtering
- Svake passord

Annet:
- CSRF
- Katalogtraversering
- Filinkludering
- Informasjonslekkasje
- Problemer på serversiden
```

### Skanneresultater

```
Dashboard → Issue activity
Target → Site map → Issues

For hvert funn:
- Alvorlighetsgrad (High/Medium/Low/Info)
- Sikkerhet i vurderingen (Certain/Firm/Tentative)
- Beskrivelse
- Utbedring
- Dokumentasjon (forespørsel/respons)
```

### Egne skanneprofiler

```
1. Dashboard → New scan
2. Velg "Scan configuration"
3. Klikk "New" eller endre en eksisterende
4. Lagre for gjenbruk

Tilpass:
- Hvilke sårbarheter som skal testes
- Intensiteten i payloadene
- Forespørselsfrekvens
- Håndtering av autentisering
```

---

## 🔁 Repeater

### Formål

Manuell testing og endring av enkeltforespørsler.

### Send til Repeater

```
1. Høyreklikk en forespørsel
2. Velg "Send to Repeater"
3. Tastatur: Ctrl+R
```

### Bruk av Repeater

```
1. Endre forespørselen i venstre panel
2. Klikk "Send" (eller Ctrl+Space)
3. Vis responsen i høyre panel
4. Sammenlign responser
```

### Egenskaper i Repeater

| Egenskap | Beskrivelse |
|---------|-------------|
| **Flere faner** | Test flere forespørsler |
| **Forespørselshistorikk** | Bla med knappene < > |
| **Følg videresendinger** | Slå automatisk følging av/på |
| **Behandle informasjonskapsler** | Oppdater informasjonskapsler automatisk |
| **Content-Length** | Oppdater headeren automatisk |

### Testeksempler

```http
# Test SQL-injeksjon
GET /user.php?id=1' HTTP/1.1

# Test XSS
GET /search?q=<script>alert(1)</script> HTTP/1.1

# Test omgåelse av autentisering
GET /admin HTTP/1.1
Cookie: role=admin

# Test IDOR
GET /api/user/1 HTTP/1.1  # Din bruker
GET /api/user/2 HTTP/1.1  # En annen bruker
```

---

## 🔫 Intruder

### Angrepstyper

| Type | Posisjoner | Payloads | Bruksområde |
|------|-----------|----------|----------|
| **Sniper** | 1+ | 1 sett, én om gangen | Testing av én parameter |
| **Battering Ram** | 1+ | 1 sett, likt overalt | Samme verdi overalt |
| **Pitchfork** | 1+ | Flere sett, parallelt | Par av brukernavn og passord |
| **Cluster Bomb** | 1+ | Flere sett, alle kombinasjoner | Full utprøving av påloggingskombinasjoner |

### Send til Intruder

```
1. Høyreklikk forespørselen
2. Velg "Send to Intruder"
3. Tastatur: Ctrl+I
```

### Velge posisjoner

```
Intruder → Positions

Marker injeksjonspunktene med §-tegn:

GET /login.php?user=§admin§&pass=§test§ HTTP/1.1

Knapper:
- Add § - Marker utvalget
- Clear § - Fjern markørene
- Auto § - Finn automatisk
```

### Payload-typer

| Type | Beskrivelse |
|------|-------------|
| **Simple list** | Egen ordliste |
| **Runtime file** | Last fra fil |
| **Numbers** | Tall i rekkefølge eller tilfeldig |
| **Dates** | Datosekvenser |
| **Brute forcer** | Tegnkombinasjoner |
| **Null payloads** | Tomme payloads (frekvenstesting) |
| **Username generator** | Vanlige brukernavnmønstre |

### Behandling av payloads

```
Intruder → Payloads → Payload Processing

Legg til regler:
- Legg til prefiks eller suffiks
- URL-kod
- Base64-kod
- Hash (MD5, SHA)
- Samsvar/erstatning
```

### Grep - Match

```
Intruder → Options → Grep - Match

Legg til strenger som skal markeres i responser:
- "Login successful"
- "Invalid password"
- "error"
- Eget regulært uttrykk

Hjelper deg å identifisere vellykkede angrep.
```

### Angrepseksempler

#### Utprøving av passord ved pålogging
```
POST /login HTTP/1.1
Content-Type: application/x-www-form-urlencoded

username=§admin§&password=§password§

Attack type: Cluster bomb
Payload 1: Liste over brukernavn
Payload 2: Liste over passord
```

#### Fuzzing av parametere
```
GET /api/user/§1§ HTTP/1.1

Attack type: Sniper
Payload: Tall 1-1000
```

#### Oppdagelse av innhold
```
GET /§admin§ HTTP/1.1

Attack type: Sniper
Payload: Ordliste med katalognavn
Filter: Statuskode != 404
```

### Analyse av resultater

```
Etter angrepet:
- Sorter etter lengde (finn avvikende responser)
- Sorter etter statuskode
- Filtrer etter grep-treff
- Eksporter resultater
```

---

## 🔓 Decoder

### Koding og dekoding

```
Decoder-fanen

Operasjoner:
- URL-koding og -dekoding
- HTML-koding og -dekoding
- Base64-koding og -dekoding
- Hex-koding og -dekoding
- Gzip-komprimering og -utpakking
- ASCII Hex
- Binær
```

### Smart Decode

```
Decoder → Smart decode

Oppdager og dekoder automatisk:
- Base64
- URL-koding
- HTML-entiteter
- Kombinerte kodinger
```

### Vanlige bruksområder

```
# Dekod JWT-token
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
→ Dekod som Base64

# Dekod URL-parameter
%3Cscript%3Ealert(1)%3C%2Fscript%3E
→ URL-dekod
→ <script>alert(1)</script>

# Kod payload
<script>alert(1)</script>
→ Base64-kod
→ Bruk i angrepet
```

---

## 📊 Comparer

### Formål

Sammenlign to dataelementer (forespørsler, responser eller andre data).

### Slik brukes verktøyet

```
1. Høyreklikk element 1 → Send to Comparer
2. Høyreklikk element 2 → Send to Comparer
3. Gå til Comparer-fanen
4. Velg elementene og klikk "Words" eller "Bytes"
```

### Sammenligningsmoduser

| Modus | Beskrivelse |
|------|-------------|
| **Words** | Sammenlign ord for ord |
| **Bytes** | Sammenlign byte for byte (nøyaktig) |

### Bruksområder

```
- Sammenlign responser ved vellykket og mislykket pålogging
- Finn forskjeller i økttokener
- Sammenlign før og etter endringer
- Identifiser skjulte skjemafelt
```

---

## 🔢 Sequencer

### Formål

Analyser kvaliteten på tilfeldigheten i tokener (økt-ID-er og CSRF-tokener).

### Slik brukes verktøyet

```
1. Fang opp en forespørsel som returnerer et token
2. Høyreklikk → Send to Sequencer
3. Konfigurer tokenets plassering
4. Start løpende innsamling
5. Analyser resultatene
```

### Analysemål

| Måling | God verdi | Beskrivelse |
|--------|------------|-------------|
| **Overall quality** | >100 bits | Vurdering av tilfeldighet |
| **Character-level** | Høy entropi | Tilfeldighet per tegn |
| **Bit-level** | Høy entropi | Tilfeldighet per bit |

### Tolkning

> **Presisering:** Sequencer vurderer statistiske egenskaper i et utvalg; en god testscore beviser ikke alene en kryptografisk sikker generator. `>100 bits` er originalens tommelfingerverdi, ikke en universell grense. Se [PortSwiggers analyseforklaring](https://portswigger.net/burp/documentation/desktop/tools/sequencer/results). Statuskoder alene beviser heller ikke vellykket innlogging eller en sårbarhet. JWT-eksemplet er forenklet: vanlige signerte JWT-er består av separate base64url-kodede deler.

```
Resultater:
- Excellent: Tokenet er kryptografisk tilfeldig
- Good: Akseptabelt for de fleste formål
- Poor: Mulig forutsigbarhet
- Very poor: Tokenet kan kanskje gjettes
```

---

## 🧩 Utvidelser

### BApp Store

```
Extender → BApp Store

Populære utvidelser:
```

### Nyttige utvidelser

| Utvidelse | Beskrivelse |
|-----------|-------------|
| **Autorize** | Testing av autorisasjon |
| **Logger++** | Utvidet logging |
| **Retire.js** | Oppdag sårbare JavaScript-biblioteker |
| **Active Scan++** | Flere skannekontroller |
| **AuthMatrix** | Testing med autorisasjonsmatrise |
| **Turbo Intruder** | Raskt alternativ til Intruder |
| **Param Miner** | Oppdag skjulte parametere |
| **JSON Web Tokens** | Analyse og endring av JWT |
| **Hackvertor** | Utvidet koding og dekoding |
| **CO2** | Samling av nyttige verktøy |

### Installere utvidelser

```
Metode 1: BApp Store
1. Extender → BApp Store
2. Finn utvidelsen
3. Klikk Install

Metode 2: Manuelt
1. Last ned en .jar- eller .py-fil
2. Extender → Extensions → Add
3. Velg filtype og sti
```

### Innstillinger for utvidelser

```
Extender → Options

- Python-miljø (for Python-utvidelser)
- Innstillinger for Java-miljø
- Loggvalg
```

---

<a id="-tastatursnarveier"></a>
## ⌨️ Tastatursnarveier

### Globale snarveier

| Snarvei | Handling |
|----------|--------|
| `Ctrl+I` | Send til Intruder |
| `Ctrl+R` | Send til Repeater |
| `Ctrl+S` | Søk |
| `Ctrl+F` | Videresend (Proxy) |
| `Ctrl+D` | Forkast (Proxy) |
| `Ctrl+T` | Slå avskjæring av/på |
| `Ctrl+Shift+T` | Slå Proxy-avskjæring av/på |

### Navigasjon mellom faner

| Snarvei | Handling |
|----------|--------|
| `Ctrl+Shift+D` | Dashboard |
| `Ctrl+Shift+T` | Target |
| `Ctrl+Shift+P` | Proxy |
| `Ctrl+Shift+I` | Intruder |
| `Ctrl+Shift+R` | Repeater |

### I Repeater

| Snarvei | Handling |
|----------|--------|
| `Ctrl+Space` | Send forespørsel |
| `Ctrl+G` | Gå til (forespørselshistorikk) |
| `Ctrl+Plus` | Ny fane |

### I redigeringsverktøyet

| Snarvei | Handling |
|----------|--------|
| `Ctrl+U` | URL-kod utvalget |
| `Ctrl+Shift+U` | URL-dekod utvalget |
| `Ctrl+B` | Base64-kod |
| `Ctrl+Shift+B` | Base64-dekod |

---

## 🎯 Arbeidsflyt for testing

### Vanlig arbeidsflyt for test av webapplikasjon

```
Trinn 1: Oppsett
├── Konfigurer nettleserens proxy
├── Installer SSL-sertifikatet
└── Definer omfanget

Trinn 2: Rekognosering
├── Utforsk applikasjonen manuelt
├── Kjør Spider/crawler
└── Gjennomgå Site map

Trinn 3: Analyse
├── Kontroller Proxy history
├── Gjennomgå resultatene fra passiv skanning
└── Identifiser angrepsflaten

Trinn 4: Testing
├── Test med Repeater (manuelt)
├── Test med Intruder (automatisert)
├── Kjør aktiv skanning
└── Bruk utvidelser

Trinn 5: Rapportering
├── Gjennomgå alle funn
├── Generer rapport
└── Dokumenter bevis
```

### Sjekkliste for testing

```
Autentisering:
□ Utprøving av passord ved pålogging
□ Passordpolicy
□ Kontosperring
□ Økthåndtering
□ Funksjonen for å huske pålogging

Autorisasjon:
□ IDOR-testing
□ Rettighetseskalering
□ Tilgang på funksjonsnivå

Validering av inndata:
□ SQL-injeksjon
□ XSS (reflektert/lagret/DOM)
□ Kommandoinjeksjon
□ Filopplasting
□ XXE

Økter:
□ Øktfiksering
□ Tidsavbrudd for økter
□ Tilfeldighet i tokener

Forretningslogikk:
□ Manipulering av priser
□ Omgåelse av arbeidsflyt
□ Kappløpsfeil (race conditions)
```

---

## 📊 Hurtigreferanse

### Verktøyoversikt

| Verktøy | Hovedbruk | Viktig snarvei |
|------|-------------|--------------|
| Proxy | Avskjær trafikk | Ctrl+T (veksle) |
| Repeater | Manuell testing | Ctrl+R (send til) |
| Intruder | Automatiserte angrep | Ctrl+I (send til) |
| Scanner | Finn sårbarheter | Right-click → Scan |
| Spider | Oppdag innhold | Right-click → Spider |
| Decoder | Kod/dekod | - |
| Comparer | Sammenlign responser | Send to Comparer |
| Sequencer | Tokenanalyse | Send to Sequencer |

### Hurtigreferanse for Intruder-angrepstyper

| Angrep | Brukes når |
|--------|----------|
| **Sniper** | Du tester én parameter om gangen |
| **Battering Ram** | Samme payload skal brukes overalt |
| **Pitchfork** | Par av brukernavn:passord |
| **Cluster Bomb** | Alle kombinasjoner av brukernavn × passord |

### Vanlige statuskoder

| Kode | Betydning | Tolkning i Intruder |
|------|---------|----------------------|
| 200 | OK | Vanlig respons |
| 301/302 | Videresending | Kan tyde på at forsøket lykkes |
| 401 | Ikke autentisert | Feil påloggingsopplysninger |
| 403 | Forbudt | Tilgang nektet |
| 404 | Ikke funnet | Ugyldig sti |
| 500 | Serverfeil | Mulig sårbarhet |

---

## 💡 Tips og anbefalte arbeidsmåter

### Ytelsestips

1. **Begrens omfanget**
   ```
   Ta bare med måldomenene i omfanget
   Reduserer støy i Proxy history
   ```

2. **Deaktiver utvidelser**
   ```
   Deaktiver ubrukte utvidelser for bedre ytelse
   ```

3. **Bruk prosjektfiler**
   ```
   Lagre arbeidet ofte
   Bruk prosjektfiler til store tester
   ```

### Testtips

1. **Start passivt**
   ```
   La den passive skanneren finne de enkleste problemene
   Gjennomgå dem før aktiv skanning
   ```

2. **Bruk Repeater først**
   ```
   Manuell testing kan avdekke mer
   Forstå applikasjonen før automatisering
   ```

3. **Organiser med faner**
   ```
   Gi Repeater-fanene meningsfulle navn
   Bruk navn på Intruder-angrep
   ```

### Redusere deteksjon

```
Innstillinger for lavere synlighet:

Project options → Connections:
- Begrens forespørselsfrekvensen
- Tilfeldige forsinkelser
- Begrens antall samtidige forbindelser

Intruder → Options:
- Forsinkelse mellom forespørsler
- Starttidspunkt
```

### Feilsøking

| Problem | Løsning |
|-------|----------|
| Kan ikke avskjære HTTPS | Installer CA-sertifikatet |
| Treg ytelse | Reduser omfanget, deaktiver utvidelser |
| Manglende forespørsler | Kontroller "Intercept Server Responses" |
| Scanner finner ikke sårbarheter | Øk skanneintensiteten |

---

## ⚠️ Juridisk merknad

> **ADVARSEL:** Burp Suite er et kraftig sikkerhetsverktøy som bare skal brukes til **autorisert testing**.
> 
> - ✅ Test applikasjoner du eier
> - ✅ Test med uttrykkelig skriftlig tillatelse
> - ✅ Test i egne testmiljøer
> - ❌ Skann aldri produksjonssystemer uten tillatelse
> - ❌ Bruk aldri til uautorisert tilgang
> 
> **Uautorisert sikkerhetstesting er ulovlig og kan føre til straffeforfølgning.**

---

## 📚 Ressurser

### Offisielle ressurser
- [PortSwigger Web Security Academy](https://portswigger.net/web-security)
- [Burp Suite-dokumentasjon](https://portswigger.net/burp/documentation)
- [BApp Store](https://portswigger.net/bappstore)

### Læringsressurser
- [OWASPs testveiledning](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Relaterte hurtigreferanser
- [SQLMap](../../SQLMap/translations/README.nb.md)
- [Metasploit Framework](../../Metasploit/translations/README.nb.md)

---

<p align="center">
  <b>🔶 Lær sikkerhetstesting av webapplikasjoner!</b><br>
  <i>Den beste måten å lære på er å gjøre – sett opp en lab og øv!</i>
</p>
