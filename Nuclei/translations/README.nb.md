# 🎯 Nuclei – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

> **Begreper og versjoner:** En mal beskriver forespørsler og treffvilkår i YAML. Tallene, katalogstiene, utdataflaggene og alvorlighetsgradene er originalens eksempler og avhenger av motor og malsamling. Et treff må valideres; tagger og prioriteringsnivåer er ikke alene bevis på konsekvens. Malene kan ha ulike protokoller og forutsetninger, og hastighetsgrenser gjør ikke alle maler skadefrie. Se [Nucleis offisielle prosjekt](https://github.com/projectdiscovery/nuclei). De opprinnelige YAML-verdiene og kommandoene er beholdt.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _            _      _ 
 | \ | |_   _  ___| | ___(_)
 |  \| | | | |/ __| |/ _ \ |
 | |\  | |_| | (__| |  __/ |
 |_| \_|\__,_|\___|_|\___|_|
                            
    Rask malbasert skanner
```

<p align="center">
  <img src="https://img.shields.io/badge/Nuclei-Vulnerability_Scanner-red?style=for-the-badge" alt="Nuclei">
  <img src="https://img.shields.io/badge/9000+-Templates-orange?style=for-the-badge" alt="Templates">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🔥 En populær malbasert sårbarhetsskanner for bug bounty</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Grunnleggende syntaks](#-grunnleggende-syntaks)
- [Malkategorier](#-malkategorier)
- [Skanning av mål](#-skanning-av-mål)
- [Valg av maler](#-valg-av-maler)
- [Utdataalternativer](#-utdataalternativer)
- [Avanserte funksjoner](#-avanserte-funksjoner)
- [Egendefinerte maler](#-egendefinerte-maler)
- [Arbeidsflyt for bug bounty](#-arbeidsflyt-for-bug-bounty)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

**Nuclei** er en rask, malbasert sårbarhetsskanner fra ProjectDiscovery. Den brukes av bug bounty-jegere og sikkerhetsforskere.

### Viktige egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **9000+ maler** | CVE-er, paneler, feilkonfigurasjoner og overtakelser |
| **Rask og effektiv** | Parallell skanning med hastighetsbegrensning |
| **Malbasert** | YAML-maler som enkelt kan tilpasses |
| **Drevet av fellesskapet** | Maler som oppdateres fortløpende |
| **Flere protokoller** | HTTP, DNS, TCP, SSL, Headless |
| **Enkel integrasjon** | Samarbeider med andre verktøy (httpx, subfinder) |

### Maltyper

| Type | Beskrivelse | Antall |
|------|-------------|-------|
| **CVE** | Kjente sårbarheter | 2000+ |
| **Eksponerte paneler** | Administratorpaneler og dashbord | 500+ |
| **Feilkonfigurasjoner** | Feil i sikkerhetskonfigurasjonen | 300+ |
| **Standardpålogging** | Standard påloggingsopplysninger | 200+ |
| **Overtakelser** | Overtakelse av underdomener | 100+ |
| **Teknologier** | Teknologigjenkjenning | 1000+ |
| **Eksponeringer** | Eksponering av sensitive filer | 500+ |

---

## 📥 Installasjon

### Med Go (anbefalt)
```bash
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest
```

### Nedlasting av binærfil
```bash
# Last ned fra utgivelser
https://github.com/projectdiscovery/nuclei/releases

# Linux
wget https://github.com/projectdiscovery/nuclei/releases/download/v3.x.x/nuclei_3.x.x_linux_amd64.zip
unzip nuclei_3.x.x_linux_amd64.zip
sudo mv nuclei /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/nuclei:latest
docker run -it projectdiscovery/nuclei -h
```

### Kali Linux
```bash
sudo apt install nuclei
```

### Oppdatere maler
```bash
# Oppdater nuclei og malene
nuclei -ut

# Oppdater bare malene
nuclei -update-templates
```

### Kontroller installasjonen
```bash
nuclei -version
nuclei -tl              # List maler
```

---

<a id="-grunnleggende-syntaks"></a>
## ⌨️ Grunnleggende syntaks

### Generell syntaks
```bash
nuclei [options] -target <url>
```

### Viktige alternativer

| Innstilling | Beskrivelse |
|--------|-------------|
| `-u` | Én mål-URL |
| `-l` | Fil med URL-liste |
| `-t` | Malene som skal kjøres |
| `-tags` | Maler med bestemte tagger |
| `-severity` | Filtrer etter alvorlighetsgrad |
| `-o` | Utdatafil |
| `-silent` | Stille modus |
| `-v` | Detaljerte utdata |

### Kom raskt i gang
```bash
# Skann ett mål
nuclei -u https://example.com

# Skann en målliste
nuclei -l urls.txt

# Skann med bestemte maler
nuclei -u https://example.com -t cves/

# Skann med tagger
nuclei -u https://example.com -tags cve,rce
```

---

## 📂 Malkategorier

### CVE-maler

```bash
# Alle CVE-maler
nuclei -u target.com -t cves/

# Bestemt CVE-år
nuclei -u target.com -t cves/2024/
nuclei -u target.com -t cves/2023/

# Bare kritiske CVE-er
nuclei -u target.com -t cves/ -severity critical

# Bestemt CVE
nuclei -u target.com -t cves/2023/CVE-2023-XXXXX.yaml
```

### Eksponerte paneler

```bash
# All panelgjenkjenning
nuclei -u target.com -t exposed-panels/

# Bestemte paneler
nuclei -u target.com -tags phpmyadmin
nuclei -u target.com -tags jenkins
nuclei -u target.com -tags grafana
nuclei -u target.com -tags kibana
```

### Feilkonfigurasjoner

```bash
# Alle feilkonfigurasjoner
nuclei -u target.com -t misconfiguration/

# Bestemte typer
nuclei -u target.com -tags cors
nuclei -u target.com -tags headers
nuclei -u target.com -tags http-missing-security-headers
```

### Standardpålogging

```bash
# Alle standard påloggingsopplysninger
nuclei -u target.com -t default-logins/

# Bestemte leverandører
nuclei -u target.com -tags tomcat-default
nuclei -u target.com -tags apache-default
```

### Overtakelse av underdomener

```bash
# Alle kontroller for overtakelse
nuclei -u target.com -t takeovers/

# Bestemte tilbydere
nuclei -u target.com -tags aws-takeover
nuclei -u target.com -tags azure-takeover
nuclei -u target.com -tags github-takeover
```

### Teknologigjenkjenning

```bash
# Teknologigjenkjenning
nuclei -u target.com -t technologies/

# CMS-gjenkjenning
nuclei -u target.com -tags tech,wordpress
nuclei -u target.com -tags tech,joomla
```

### Eksponeringer

```bash
# Eksponering av sensitive filer
nuclei -u target.com -t exposures/

# Bestemte eksponeringer
nuclei -u target.com -tags config
nuclei -u target.com -tags backup
nuclei -u target.com -tags logs
nuclei -u target.com -tags git
```

---

## 🎯 Skanning av mål

### Ett mål

```bash
nuclei -u https://example.com
nuclei -u https://example.com:8080
```

### Flere mål

```bash
# Fra fil
nuclei -l targets.txt

# Fra standard inndata (pipeline)
cat targets.txt | nuclei
echo "https://example.com" | nuclei

# Flere URL-er på samme linje
nuclei -u https://site1.com -u https://site2.com
```

### Sammen med andre verktøy

```bash
# Med subfinder (underdomener)
subfinder -d example.com | nuclei

# Med httpx (tilgjengelige verter)
subfinder -d example.com | httpx | nuclei

# Med katana (URL-er fra crawling)
katana -u https://example.com | nuclei

# Full pipeline for rekognosering
subfinder -d example.com -silent | httpx -silent | nuclei -silent
```

---

<a id="-valg-av-maler"></a>
## 🏷️ Valg av maler

### Etter malsti

```bash
# Én mal
nuclei -u target.com -t cves/2023/CVE-2023-1234.yaml

# Katalog med maler
nuclei -u target.com -t cves/2023/
nuclei -u target.com -t exposed-panels/

# Flere kataloger
nuclei -u target.com -t cves/ -t exposed-panels/

# Egendefinert malsti
nuclei -u target.com -t /path/to/custom/templates/
```

### Etter tagger

```bash
# Én tagg
nuclei -u target.com -tags cve

# Flere tagger (ELLER)
nuclei -u target.com -tags cve,rce,sqli

# Vanlige tagger
nuclei -u target.com -tags xss
nuclei -u target.com -tags ssrf
nuclei -u target.com -tags lfi
nuclei -u target.com -tags rce
nuclei -u target.com -tags injection
```

### Etter alvorlighetsgrad

```bash
# Én alvorlighetsgrad
nuclei -u target.com -severity critical
nuclei -u target.com -severity high
nuclei -u target.com -severity medium
nuclei -u target.com -severity low
nuclei -u target.com -severity info

# Flere alvorlighetsgrader
nuclei -u target.com -severity critical,high

# Utelat alvorlighetsgrad
nuclei -u target.com -exclude-severity info,low
```

### Utelate maler

```bash
# Utelat bestemte maler
nuclei -u target.com -exclude-templates cves/2020/

# Utelat etter tagger
nuclei -u target.com -exclude-tags dos,fuzz

# Utelat etter ID
nuclei -u target.com -exclude-id CVE-2021-1234
```

---

## 📤 Utdataalternativer

### Utdataformater

```bash
# Standard utdata
nuclei -u target.com -o results.txt

# JSON-utdata
nuclei -u target.com -json -o results.json

# JSON Lines
nuclei -u target.com -jsonl -o results.jsonl

# Markdown
nuclei -u target.com -markdown-export report/
```

### Filtrering av utdata

```bash
# Stille modus (bare resultater)
nuclei -u target.com -silent

# Uten farger
nuclei -u target.com -nc

# Detaljert
nuclei -u target.com -v

# Feilsøking
nuclei -u target.com -debug
```

### Eksportalternativer

```bash
# SARIF (GitHub)
nuclei -u target.com -sarif-export results.sarif

# Markdown-rapport
nuclei -u target.com -me reports/
```

---

## ⚡ Avanserte funksjoner

### Hastighetsbegrensning

```bash
# Forespørsler per sekund
nuclei -u target.com -rate-limit 100

# Gruppestørrelse
nuclei -u target.com -bulk-size 25

# Parallellitet
nuclei -u target.com -c 50

# Parallellitet for maler
nuclei -u target.com -rl 100 -c 50
```

### Proxystøtte

```bash
# HTTP-proxy
nuclei -u target.com -proxy http://127.0.0.1:8080

# SOCKS-proxy
nuclei -u target.com -proxy socks5://127.0.0.1:9050

# Burp Suite-proxy
nuclei -u target.com -proxy http://127.0.0.1:8080
```

### Autentisering

```bash
# Autentisering med header
nuclei -u target.com -H "Authorization: Bearer token123"

# Autentisering med informasjonskapsel
nuclei -u target.com -H "Cookie: session=abc123"

# Flere headere
nuclei -u target.com -H "Authorization: Bearer token" -H "X-Custom: value"
```

### Interactsh-integrasjon

```bash
# Bruk interactsh til OOB-testing
nuclei -u target.com -interactsh-server https://interact.sh

# Egendefinert interactsh-server
nuclei -u target.com -iserver https://my-interactsh.com
```

### Headless-modus

```bash
# Aktiver nettleser uten grafisk grensesnitt
nuclei -u target.com -headless

# Bare headless-maler
nuclei -u target.com -t headless/
```

### Arbeidsflyter

```bash
# Kjør arbeidsflyt
nuclei -u target.com -w workflows/wordpress-workflow.yaml

# List arbeidsflyter
ls ~/.local/nuclei-templates/workflows/
```

---

## 📝 Egendefinerte maler

### Malstruktur

```yaml
id: my-custom-template

info:
  name: My Custom Template
  author: your-name
  severity: medium
  description: Description of what this template detects
  tags: custom,web

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
    matchers:
      - type: word
        words:
          - "Admin Panel"
```

### Maleksempler

#### Gjenkjenn en bestemt side
```yaml
id: admin-panel-detect

info:
  name: Admin Panel Detection
  author: hunter
  severity: info
  tags: panel

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
      - "{{BaseURL}}/administrator"
      - "{{BaseURL}}/wp-admin"
    matchers-condition: or
    matchers:
      - type: status
        status:
          - 200
```

#### Kontroller header
```yaml
id: missing-security-header

info:
  name: Missing X-Frame-Options
  author: hunter
  severity: low
  tags: headers

http:
  - method: GET
    path:
      - "{{BaseURL}}"
    matchers:
      - type: word
        words:
          - "text/html"
        part: header
    matchers:
      - type: word
        negative: true
        words:
          - "X-Frame-Options"
        part: header
```

### Validere mal

```bash
# Valider malsyntaksen
nuclei -validate -t my-template.yaml

# Test malen
nuclei -u target.com -t my-template.yaml -debug
```

---

## 🔄 Arbeidsflyt for bug bounty

### Full pipeline for rekognosering

```bash
# Trinn 1: Kartlegging av underdomener
subfinder -d target.com -o subs.txt

# Trinn 2: Finn tilgjengelige verter
cat subs.txt | httpx -o live.txt

# Trinn 3: Kjør nuclei
nuclei -l live.txt -severity critical,high -o findings.txt

# Kommando på én linje
subfinder -d target.com -silent | httpx -silent | nuclei -severity critical,high
```

### Prioritert skanning

```bash
# Fase 1: Bare kritiske funn (raskt)
nuclei -l targets.txt -severity critical -o critical.txt

# Fase 2: Høy alvorlighetsgrad
nuclei -l targets.txt -severity high -o high.txt

# Fase 3: CVE-er
nuclei -l targets.txt -t cves/ -o cves.txt

# Fase 4: Full skanning
nuclei -l targets.txt -o all.txt
```

### Målrettet skanning

```bash
# WordPress-mål
nuclei -l wp-sites.txt -tags wordpress

# API-endepunkter
nuclei -l apis.txt -tags api

# Påloggingssider
nuclei -l logins.txt -tags login,default-login
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: Full skanning
```bash
nuclei -u https://target.com -o results.txt
```

### Eksempel 2: Bare kritiske CVE-er
```bash
nuclei -u https://target.com -t cves/ -severity critical,high
```

### Eksempel 3: Rask bug bounty-skanning
```bash
nuclei -u https://target.com -tags cve,rce,sqli,xss,ssrf -severity critical,high
```

### Eksempel 4: Kontroll for overtakelse av underdomener
```bash
cat subs.txt | nuclei -t takeovers/
```

### Eksempel 5: Teknologigjenkjenning
```bash
nuclei -u https://target.com -t technologies/ -silent
```

### Eksempel 6: Med proxy (Burp)
```bash
nuclei -u https://target.com -proxy http://127.0.0.1:8080
```

### Eksempel 7: Autentisert skanning
```bash
nuclei -u https://target.com -H "Authorization: Bearer eyJhbG..."
```

### Eksempel 8: Hastighetsbegrenset skanning
```bash
nuclei -l targets.txt -rate-limit 10 -c 5
```

### Eksempel 9: JSON-utdata for videre behandling
```bash
nuclei -u https://target.com -json -o results.json
```

### Eksempel 10: Full pipeline
```bash
subfinder -d target.com -silent | \
httpx -silent | \
nuclei -severity critical,high -json -o results.json
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Oppgave | Kommando |
|------|---------|
| Grunnleggende skanning | `nuclei -u target.com` |
| Med maler | `nuclei -u target.com -t cves/` |
| Etter tagger | `nuclei -u target.com -tags cve,rce` |
| Etter alvorlighetsgrad | `nuclei -u target.com -severity critical` |
| Fra liste | `nuclei -l urls.txt` |
| Oppdater maler | `nuclei -ut` |
| Stille utdata | `nuclei -u target.com -silent` |
| JSON-utdata | `nuclei -u target.com -json -o out.json` |

### Vanlige tagger

| Tagg | Beskrivelse |
|-----|-------------|
| `cve` | CVE-sårbarheter |
| `rce` | Ekstern kodekjøring |
| `sqli` | SQL-injeksjon |
| `xss` | Cross-site scripting |
| `ssrf` | Server-side request forgery |
| `lfi` | Lokal filinkludering |
| `panel` | Administratorpaneler |
| `takeover` | Overtakelse av underdomener |

### Alvorlighetsgrader

| Alvorlighetsgrad | Farge | Prioritet |
|----------|-------|----------|
| `critical` | 🔴 Rød | Umiddelbart |
| `high` | 🟠 Oransje | Høy |
| `medium` | 🟡 Gul | Middels |
| `low` | 🟢 Grønn | Lav |
| `info` | 🔵 Blå | Til informasjon |

---

## 💡 Tips og anbefalte arbeidsmåter

### Ytelsestips

```bash
# Optimale innstillinger for store skanninger
nuclei -l targets.txt -c 50 -rl 150 -bs 25

# Stille modus for pipelines
nuclei -l targets.txt -silent -o results.txt
```

### Tips for bug bounty

1. **Begynn med kritisk/høy alvorlighetsgrad**
   ```bash
   nuclei -l targets.txt -severity critical,high
   ```

2. **Se etter raske funn**
   ```bash
   nuclei -l targets.txt -tags takeover,exposed-panels
   ```

3. **Bruk oppdaterte maler**
   ```bash
   nuclei -ut  # Oppdater alltid før jakten
   ```

4. **Begrens hastigheten for stabilitet**
   ```bash
   nuclei -l targets.txt -rl 50
   ```

### Tips for å unngå oppdagelse

```bash
# Langsomt og stille
nuclei -l targets.txt -rl 10 -c 2

# Gjennom proxy
nuclei -l targets.txt -proxy socks5://127.0.0.1:9050
```

---

## ⚠️ Juridisk merknad

> **ADVARSEL:** Nuclei er en kraftig sårbarhetsskanner som bare skal brukes til **autorisert testing**.
> 
> - ✅ Bruk mot programmer du har tillatelse til å teste
> - ✅ Bruk i autoriserte bug bounty-programmer
> - ✅ Bruk mot dine egne systemer
> - ❌ Skann aldri uten tillatelse
> - ❌ Respekter hastighetsgrenser og omfang
> 
> **Uautorisert skanning er ulovlig og uetisk.**

---

## 📚 Ressurser

### Offisielle ressurser
- [Nuclei GitHub](https://github.com/projectdiscovery/nuclei)
- [Nuclei Templates](https://github.com/projectdiscovery/nuclei-templates)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentation](https://docs.projectdiscovery.io/tools/nuclei/)

### Ressurser for maler
- [Community Templates](https://github.com/projectdiscovery/nuclei-templates)
- [Template Guide](https://docs.projectdiscovery.io/templates/)

### Relaterte hurtigreferanser
- [Nmap](../../Nmap/translations/README.nb.md)
- [Burp Suite](../../Burp-Suite/translations/README.nb.md)
- [SQLMap](../../SQLMap/translations/README.nb.md)

---

<p align="center">
  <b>🎯 Lær sårbarhetsskanning!</b><br>
  <i>En nyttig følgesvenn for bug bounty-jegeren</i>
</p>
