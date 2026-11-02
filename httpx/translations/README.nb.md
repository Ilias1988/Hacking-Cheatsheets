# 🌐 httpx – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

> **Begreper og versjoner:** Denne siden gjelder ProjectDiscoverys httpx-verktøy, ikke Python-biblioteket med samme navn. Probing undersøker om HTTP-tjenester svarer; teknologigjenkjenning er et indisium som må kontrolleres. En 200-respons er ikke alene bevis på riktig side eller gyldig pålogging. Flagg, felter og skjermbildekrav avhenger av versjonen; tabellenes stjerner og påstander om andre verktøys parallellitet er forenklinger, ikke uttømmende sammenligninger. Se [prosjektets veiledning](https://github.com/projectdiscovery/httpx). Kommandoene er bevart.

```
  _     _   _           
 | |__ | |_| |_ _ __ __  __
 | '_ \| __| __| '_ \\ \/ /
 | | | | |_| |_| |_) |>  < 
 |_| |_|\__|\__| .__//_/\_\
               |_|         
                           
   Rask HTTP-undersøkelse og verktøykasse
```

<p align="center">
  <img src="https://img.shields.io/badge/httpx-HTTP_Probe-red?style=for-the-badge" alt="httpx">
  <img src="https://img.shields.io/badge/Fast-Toolkit-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>⚡ Rask og allsidig HTTP-verktøykasse fra ProjectDiscovery</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Grunnleggende syntaks](#-grunnleggende-syntaks)
- [Undersøkelse og oppdagelse](#-undersøkelse-og-oppdagelse)
- [Uthenting av informasjon](#-uthenting-av-informasjon)
- [Filtrering og treff](#-filtrering-og-treff)
- [Utdataalternativer](#-utdataalternativer)
- [Avanserte funksjoner](#-avanserte-funksjoner)
- [Arbeidsflyt for bug bounty](#-arbeidsflyt-for-bug-bounty)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

**httpx** er en rask og allsidig HTTP-verktøykasse som utfører flere typer undersøkelser med retryablehttp-biblioteket. Den er laget for bug bounty-jegere og sikkerhetsforskere.

### Viktige egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **Raskt** | Go-basert verktøy med høy parallellitet |
| **Flere undersøkelser** | Statuskode, tittel, teknologi og headere |
| **Teknologigjenkjenning** | Gjenkjenning basert på Wappalyzer |
| **Skjermbilde** | Skjermbilder med nettleser uten grafisk grensesnitt |
| **Pipeline** | Samarbeider med subfinder og nuclei |
| **JSON-utdata** | Støtte for strukturerte utdata |

### httpx sammenlignet med andre verktøy

| Egenskap | httpx | curl | wget |
|---------|-------|------|------|
| **Hastighet** | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐ |
| **Parallellitet** | ✅ Ja | ❌ Nei | ❌ Nei |
| **Teknologigjenkjenning** | ✅ Ja | ❌ Nei | ❌ Nei |
| **Skjermbilder** | ✅ Ja | ❌ Nei | ❌ Nei |
| **Pipeline** | ✅ Ja | ⚠️ Manuelt | ⚠️ Manuelt |

---

## 📥 Installasjon

### Med Go
```bash
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest
```

### Kali Linux
```bash
sudo apt install httpx-toolkit
```

### Nedlasting av binærfil
```bash
# Last ned fra utgivelser
https://github.com/projectdiscovery/httpx/releases

# Linux
wget https://github.com/projectdiscovery/httpx/releases/download/v1.x.x/httpx_1.x.x_linux_amd64.zip
unzip httpx_1.x.x_linux_amd64.zip
sudo mv httpx /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/httpx:latest
docker run -it projectdiscovery/httpx -h
```

### Kontroller installasjonen
```bash
httpx -version
httpx -h
```

---

<a id="-grunnleggende-syntaks"></a>
## ⌨️ Grunnleggende syntaks

### Generell syntaks
```bash
httpx [options] -u <url>
httpx [options] -l <file>
cat urls.txt | httpx [options]
```

### Viktige alternativer

| Innstilling | Beskrivelse |
|--------|-------------|
| `-u` | Én mål-URL |
| `-l` | Fil med URL-liste |
| `-sc` | Vis statuskode |
| `-title` | Vis sidetittel |
| `-td` | Teknologigjenkjenning |
| `-o` | Utdatafil |
| `-silent` | Stille modus |
| `-json` | JSON-utdata |

### Kom raskt i gang
```bash
# Undersøk én URL
httpx -u https://example.com

# Undersøk fra fil
httpx -l urls.txt

# Fra standard inndata (pipeline)
cat hosts.txt | httpx

# Med subfinder
subfinder -d example.com -silent | httpx
```

---

## 🔍 Undersøkelse og oppdagelse

### Grunnleggende undersøkelse

```bash
# Undersøk HTTP/HTTPS på vertene
cat hosts.txt | httpx

# Ta med statuskode
cat hosts.txt | httpx -sc

# Undersøk bestemte porter
cat hosts.txt | httpx -p 80,443,8080,8443

# Undersøk alle vanlige porter
cat hosts.txt | httpx -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```

### Portskanning

```bash
# Egendefinerte porter
httpx -l hosts.txt -p 80,443,8080,8443

# Portområde
httpx -l hosts.txt -p 80-100

# Vanlige webporter
httpx -l hosts.txt -p http:80,https:443
```

### Protokollgjenkjenning

```bash
# HTTP/HTTPS-undersøkelse
cat hosts.txt | httpx

# Følg omdirigeringer
cat hosts.txt | httpx -follow-redirects

# Maksimalt antall omdirigeringer
cat hosts.txt | httpx -follow-redirects -max-redirects 5

# Uten fallback
cat hosts.txt | httpx -no-fallback
```

---

## 📊 Uthenting av informasjon

### Statuskoder og titler

```bash
# Statuskode
httpx -l urls.txt -sc

# Sidetittel
httpx -l urls.txt -title

# Begge
httpx -l urls.txt -sc -title

# Innholdslengde
httpx -l urls.txt -cl

# Webserver
httpx -l urls.txt -server

# All grunnleggende informasjon
httpx -l urls.txt -sc -title -cl -server
```

### Teknologigjenkjenning

```bash
# Gjenkjenn teknologier
httpx -l urls.txt -td

# Teknologigjenkjenning (detaljert)
httpx -l urls.txt -tech-detect

# Med annen informasjon
httpx -l urls.txt -sc -title -td
```

### Headere og respons

```bash
# Responsheadere
httpx -l urls.txt -response-header

# Bestemt header
httpx -l urls.txt -include-response-header "Server,X-Powered-By"

# Hash av responsinnholdet
httpx -l urls.txt -hash md5

# Forhåndsvisning av innhold
httpx -l urls.txt -bp
```

### IP- og DNS-opplysninger

```bash
# IP-adresse
httpx -l urls.txt -ip

# CNAME
httpx -l urls.txt -cname

# CDN-gjenkjenning
httpx -l urls.txt -cdn

# ASN-opplysninger
httpx -l urls.txt -asn

# Alle DNS-opplysninger
httpx -l urls.txt -ip -cname -cdn -asn
```

### SSL/TLS-opplysninger

```bash
# TLS-undersøkelse
httpx -l urls.txt -tls-probe

# Hent TLS-data
httpx -l urls.txt -tls-grab

# Sertifikatopplysninger
httpx -l urls.txt -tlsgrab
```

### Favicon-hash

```bash
# Hent favicon-hash (nyttig til Shodan-søk)
httpx -l urls.txt -favicon
```

### Ta skjermbilder

```bash
# Ta skjermbilder
httpx -l urls.txt -screenshot

# Skjermbilder med utdatakatalog
httpx -l urls.txt -screenshot -screenshot-output ./screenshots/

# Headless-alternativer
httpx -l urls.txt -screenshot -system-chrome
```

---

## 🎯 Filtrering og treff

### Filtrere etter statuskode

```bash
# Treff statuskoder
httpx -l urls.txt -mc 200,301,302

# Filtrer statuskoder
httpx -l urls.txt -fc 404,403,500

# Treff alle, filtrer bestemte responser
httpx -l urls.txt -mc all -fc 404
```

### Filtrere etter størrelse

```bash
# Treff innholdslengde
httpx -l urls.txt -ml 1234

# Filtrer innholdslengde
httpx -l urls.txt -fl 0

# Treff antall linjer
httpx -l urls.txt -mlc 100

# Filtrer antall linjer
httpx -l urls.txt -flc 50
```

### Filtrere etter innhold

```bash
# Treff responsinnhold (regulært uttrykk)
httpx -l urls.txt -mr "admin|login"

# Filtrer responsinnhold (regulært uttrykk)
httpx -l urls.txt -fr "error|not found"

# Treff streng
httpx -l urls.txt -ms "dashboard"
```

### Filtrere etter teknologi

```bash
# Treff teknologi
httpx -l urls.txt -td -mt "WordPress,Nginx"

# Filtrer teknologi
httpx -l urls.txt -td -ft "Apache"
```

---

## 📤 Utdataalternativer

### Utdataformater

```bash
# Ren tekst
httpx -l urls.txt -o results.txt

# JSON-utdata
httpx -l urls.txt -json -o results.json

# JSON-linjer
httpx -l urls.txt -jsonl -o results.jsonl

# CSV-utdata
httpx -l urls.txt -csv -o results.csv
```

### Utdatafelt

```bash
# Lagre respons
httpx -l urls.txt -sr -srd ./responses/

# Lagre skjermbilder
httpx -l urls.txt -screenshot -ssd ./screenshots/

# Egendefinerte utdata
httpx -l urls.txt -o output.txt -sc -title -td
```

### Stille og detaljert modus

```bash
# Stille modus (bare URL-er)
httpx -l urls.txt -silent

# Detaljert
httpx -l urls.txt -v

# Feilsøking
httpx -l urls.txt -debug

# Uten farger
httpx -l urls.txt -nc
```

---

## ⚡ Avanserte funksjoner

### Tråder og hastighetsbegrensning

```bash
# Angi tråder (standard: 50)
httpx -l urls.txt -threads 100

# Hastighetsgrense
httpx -l urls.txt -rate-limit 100

# Tidsavbrudd
httpx -l urls.txt -timeout 10

# Nye forsøk
httpx -l urls.txt -retries 3
```

### Proxystøtte

```bash
# HTTP-proxy
httpx -l urls.txt -proxy http://127.0.0.1:8080

# SOCKS-proxy
httpx -l urls.txt -proxy socks5://127.0.0.1:9050

# Burp Suite
httpx -l urls.txt -proxy http://127.0.0.1:8080
```

### Egendefinerte headere

```bash
# Egendefinert header
httpx -l urls.txt -H "Authorization: Bearer token123"

# Flere headere
httpx -l urls.txt -H "X-Custom: value" -H "Cookie: session=abc"

# User-Agent
httpx -l urls.txt -H "User-Agent: Mozilla/5.0"
```

### Forespørselsmetoder

```bash
# HEAD-forespørsel (raskere)
httpx -l urls.txt -method HEAD

# Egendefinert metode
httpx -l urls.txt -method OPTIONS

# POST med innhold
httpx -l urls.txt -method POST -body '{"test":"data"}'
```

### Gjenopptakelse og paginering

```bash
# Gjenoppta fra siste posisjon
httpx -l urls.txt -resume

# Lagre fremdrift
httpx -l urls.txt -resume -resume-file progress.txt
```

---

## 🔄 Arbeidsflyt for bug bounty

### Full pipeline for rekognosering

```bash
# Trinn 1: Kartlegging av underdomener
subfinder -d target.com -o subs.txt

# Trinn 2: Undersøk tilgjengelige verter med httpx
cat subs.txt | httpx -sc -title -td -o live.txt

# Trinn 3: Sårbarhetsskanning
nuclei -l live.txt -severity critical,high

# Kommando på én linje
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Detaljert rekognosering

```bash
# Full informasjonsinnhenting
subfinder -d target.com -silent | httpx \
    -sc \
    -title \
    -td \
    -ip \
    -cdn \
    -server \
    -follow-redirects \
    -o full-recon.txt
```

### Skjermbilde av alle tilgjengelige verter

```bash
subfinder -d target.com -silent | httpx -silent -screenshot -ssd ./screenshots/
```

### Finne interessante endepunkter

```bash
# Finn administratorpaneler
subfinder -d target.com -silent | httpx -title -mr "admin|login|dashboard"

# Finn API-endepunkter
cat subs.txt | httpx -path /api/ -mc 200,401,403

# Kontroller bestemte stier
httpx -l hosts.txt -path /robots.txt -mc 200
```

### Teknologispesifikk jakt

```bash
# Finn WordPress-nettsteder
subfinder -d target.com -silent | httpx -td -mt "WordPress"

# Finn Nginx-servere
cat hosts.txt | httpx -server -ms "nginx"
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: Grunnleggende undersøkelse
```bash
echo "https://example.com" | httpx -sc -title
```

### Eksempel 2: Full rekognosering fra underdomener
```bash
subfinder -d hackerone.com -silent | httpx -sc -title -td -ip
```

### Eksempel 3: Skjermbilder av alle verter
```bash
httpx -l hosts.txt -screenshot -ssd ./screenshots/
```

### Eksempel 4: Finn bare tilgjengelige verter
```bash
cat subs.txt | httpx -silent -o live.txt
```

### Eksempel 5: Teknologigjenkjenning
```bash
httpx -l urls.txt -tech-detect -json -o tech.json
```

### Eksempel 6: Med proxy (Burp)
```bash
httpx -l urls.txt -proxy http://127.0.0.1:8080 -silent
```

### Eksempel 7: Egendefinerte porter
```bash
cat hosts.txt | httpx -p 80,443,8080,8443 -sc -title
```

### Eksempel 8: Filtrer bare 200
```bash
cat subs.txt | httpx -mc 200 -silent
```

### Eksempel 9: JSON-utdata for videre behandling
```bash
httpx -l urls.txt -json -sc -title -td -o results.json
```

### Eksempel 10: Full pipeline
```bash
subfinder -d target.com -silent | \
httpx -silent -sc -title -td -ip -cdn -server -json | \
tee results.json | \
jq -r '.url' | \
nuclei -severity critical,high
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Oppgave | Kommando |
|------|---------|
| Grunnleggende undersøkelse | `httpx -u url` |
| Fra fil | `httpx -l urls.txt` |
| Statuskode | `httpx -l urls.txt -sc` |
| Sidetittel | `httpx -l urls.txt -title` |
| Teknologigjenkjenning | `httpx -l urls.txt -td` |
| IP-adresse | `httpx -l urls.txt -ip` |
| Skjermbilde | `httpx -l urls.txt -screenshot` |
| JSON-utdata | `httpx -l urls.txt -json -o out.json` |

### Vanlige alternativer

| Innstilling | Beskrivelse |
|--------|-------------|
| `-u` | Én URL |
| `-l` | Fil med URL-liste |
| `-sc` | Statuskode |
| `-title` | Sidetittel |
| `-td` | Teknologigjenkjenning |
| `-ip` | IP-adresse |
| `-cdn` | CDN-gjenkjenning |
| `-server` | Webserver |
| `-mc` | Treffkoder |
| `-fc` | Filterkoder |
| `-json` | JSON-utdata |
| `-silent` | Stille modus |

### Vanlige pipelines

| Pipeline | Kommando |
|----------|---------|
| Tilgjengelige verter | `subfinder -d target.com \| httpx -silent` |
| Med informasjon | `subfinder -d target.com \| httpx -sc -title -td` |
| Til nuclei | `httpx -l hosts.txt -silent \| nuclei` |

---

## 💡 Tips og anbefalte arbeidsmåter

### Tips for bug bounty

1. **Bruk sammen med Subfinder**
   ```bash
   subfinder -d target.com -silent | httpx -silent -sc -title
   ```

2. **Skaff et helhetsbilde**
   ```bash
   httpx -l hosts.txt -sc -title -td -ip -cdn -server
   ```

3. **Bruk skjermbilder ved første vurdering**
   ```bash
   httpx -l hosts.txt -screenshot
   ```

4. **Bruk JSON til videre behandling**
   ```bash
   httpx -l hosts.txt -json | jq '.url'
   ```

### Ytelsestips

```bash
# Høy hastighet
httpx -l hosts.txt -threads 100 -timeout 5

# Lav hastighet og lite støy
httpx -l hosts.txt -threads 10 -rate-limit 20
```

---

## ⚠️ Juridisk merknad

> **ADVARSEL:** httpx sender HTTP-forespørsler til mål. Bruk det bare mot autoriserte systemer.
> 
> - ✅ Bruk mot programmer du har tillatelse til å teste
> - ✅ Bug bounty-mål innenfor avtalt omfang
> - ✅ Dine egne domener
> - ❌ Undersøk aldri mål uten tillatelse
> - ❌ Respekter hastighetsgrenser
> 
> **Uautorisert undersøkelse kan være ulovlig.**

---

## 📚 Ressurser

### Offisielle ressurser
- [httpx GitHub](https://github.com/projectdiscovery/httpx)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentation](https://docs.projectdiscovery.io/tools/httpx/)

### Relaterte hurtigreferanser
- [Subfinder](../../Subfinder/translations/README.nb.md)
- [Nuclei](../../Nuclei/translations/README.nb.md)
- [ffuf](../../ffuf/translations/README.nb.md)

---

<p align="center">
  <b>🌐 Undersøk vertene!</b><br>
  <i>En nyttig HTTP-verktøykasse for bug bounty</i>
</p>
