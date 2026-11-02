# ⚔️ Katana – moderne webcrawler

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

> **Rettelse og versjoner:** En crawler følger lenker og finner endepunkter. `-cs` beskriver omfang med URL-regex i gjeldende veiledning, ikke valg mellom depth-first/breadth-first slik tabellen antyder. `dn`, `rdn` og `fqdn` har ulike avgrensninger; et navnelikt domene er ikke automatisk innenfor tillatt omfang. Flere andre flagg og utdatafelt i originalen er versjonsavhengige. Se [Katanas offisielle veiledning](https://github.com/projectdiscovery/katana). Eksemplene er bevart.

```
  ██╗  ██╗ █████╗ ████████╗ █████╗ ███╗   ██╗ █████╗ 
  ██║ ██╔╝██╔══██╗╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗
  █████╔╝ ███████║   ██║   ███████║██╔██╗ ██║███████║
  ██╔═██╗ ██╔══██║   ██║   ██╔══██║██║╚██╗██║██╔══██║
  ██║  ██╗██║  ██║   ██║   ██║  ██║██║ ╚████║██║  ██║
  ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝
         Moderne rammeverk for webgjennomgang
```

<p align="center">
  <img src="https://img.shields.io/badge/Katana-ProjectDiscovery-blue?style=for-the-badge" alt="Katana">
  <img src="https://img.shields.io/badge/Web_Crawler-green?style=for-the-badge" alt="Crawler">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er Katana](#-hva-er-katana)
- [Installasjon](#-installasjon)
- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Alternativer for gjennomgang](#-alternativer-for-gjennomgang)
- [Headless-modus](#-headless-modus)
- [Utdata og filtrering](#-utdata-og-filtrering)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er Katana

**Katana** er et moderne rammeverk for gjennomgang av nettsteder fra ProjectDiscovery:

- ⚡ **Raskt** – Go-basert, med høy parallellitet
- 🔍 **JavaScript-rendering** – støtte for nettleser uten grafisk grensesnitt
- 📡 **Moderne web** – håndterer SPA-er og dynamisk innhold
- 🎯 **Rettet mot bug bounty** – laget for sikkerhetstesting

### Hvorfor Katana?

| Egenskap | Fordel |
|---------|---------|
| Headless Chrome | Gå gjennom apper med mye JavaScript |
| Egendefinerte headere | Autentisert gjennomgang |
| Omfangskontroll | Hold deg innenfor omfanget |
| Utfylling av skjemaer | Oppdag flere endepunkter |

---

## 🚀 Installasjon

```bash
# Installasjon med Go (anbefalt)
go install github.com/projectdiscovery/katana/cmd/katana@latest

# Docker
docker pull projectdiscovery/katana

# Kontroller
katana -version
```

---

## 📖 Grunnleggende bruk

### Ett mål

```bash
# Grunnleggende gjennomgang
katana -u https://example.com

# Lagre utdata
katana -u https://example.com -o urls.txt

# Stille modus
katana -u https://example.com -silent
```

### Flere mål

```bash
# Fra standard inndata
echo "https://example.com" | katana

# Flere URL-er
cat targets.txt | katana

# Fra fil
katana -list targets.txt
```

### Dybde for gjennomgang

```bash
# Angi dybde (standard: 3)
katana -u https://example.com -d 5

# Dybde 1 (bare målsiden)
katana -u https://example.com -d 1
```

---

<a id="-alternativer-for-gjennomgang"></a>
## 🕷️ Alternativer for gjennomgang

### Omfangskontroll

```bash
# Gå bare gjennom avtalt omfang (samme domene)
katana -u https://example.com -fs dn

# Omfangsalternativer:
# dn – domenenavn
# rdn – rotdomene
# fqdn – fullt kvalifisert domenenavn

# Feltbasert omfang
katana -u https://example.com -fs rdn
```

### Strategi for gjennomgang

```bash
# Dybde først (standard)
katana -u https://example.com -cs depth-first

# Bredde først
katana -u https://example.com -cs breadth-first
```

### Parallellitet

```bash
# Parallelle forespørsler (standard: 10)
katana -u https://example.com -c 50

# Hastighetsgrense (forespørsler per sekund)
katana -u https://example.com -rl 100

# Parallell kjøring
katana -u https://example.com -p 10
```

### Varighet for gjennomgang

```bash
# Angi tidsavbrudd (sekunder)
katana -u https://example.com -timeout 30

# Maksimal gjennomgangstid
katana -u https://example.com -ct 300
```

### Automatisk utfylling av skjemaer

```bash
# Aktiver utfylling av skjemaer
katana -u https://example.com -aff

# Med egendefinerte verdier
katana -u https://example.com -aff -fv "username:admin,password:test123"
```

---

## 🌐 Headless-modus

### Aktivere nettleser uten grafisk grensesnitt

```bash
# Aktiver headless Chrome
katana -u https://example.com -headless

# Headless med dybde
katana -u https://example.com -headless -d 3
```

### Headless-alternativer

```bash
# Egendefinert Chrome-sti
katana -u https://example.com -headless -system-chrome

# Uten sandbox (for root)
katana -u https://example.com -headless -no-sandbox

# Ta skjermbilder av sider
katana -u https://example.com -headless -screenshot
```

### Gjennomgang av JavaScript

```bash
# Vent på at JavaScript lastes (ms)
katana -u https://example.com -headless -timeout 5

# Gå gjennom JS-endepunkter
katana -u https://example.com -js-crawl
```

---

## 📤 Utdata og filtrering

### Utdataformater

```bash
# Standard utdata til fil
katana -u https://example.com -o results.txt

# JSON-utdata
katana -u https://example.com -jsonl -o results.json

# Uten farger
katana -u https://example.com -nc
```

### Visningsfelt

```bash
# Vis bestemte felt
katana -u https://example.com -f url,method,status

# Tilgjengelige felt:
# url, path, fqdn, rdn, rurl, qurl, qpath
# file, kv, dir, ufile, udir
# method, status, body, response, header
```

### Filtrere etter filendelse

```bash
# Treff filendelser
katana -u https://example.com -em js,php,asp

# Utelat filendelser
katana -u https://example.com -ef png,jpg,gif,css,svg,woff
```

### Filtrere etter statuskode

```bash
# Treff statuskode
katana -u https://example.com -mc 200,301,302

# Filtrer statuskode
katana -u https://example.com -fc 404,500
```

### Egendefinerte headere

```bash
# Legg til header
katana -u https://example.com -H "Authorization: Bearer TOKEN"

# Flere headere
katana -u https://example.com -H "Cookie: session=abc" -H "User-Agent: Custom"
```

### Proxystøtte

```bash
# HTTP-proxy
katana -u https://example.com -proxy http://127.0.0.1:8080

# For Burp Suite
katana -u https://example.com -proxy http://127.0.0.1:8080
```

---

## 📊 Hurtigreferanse

### Grunnleggende kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `katana -u URL` | Gå gjennom én URL |
| `katana -list file.txt` | Gå gjennom fra fil |
| `katana -u URL -d 5` | Angi dybde 5 |
| `katana -u URL -headless` | Aktiver headless |
| `katana -u URL -o out.txt` | Lagre resultater |

### Omfangsalternativer

| Flagg | Beskrivelse |
|------|-------------|
| `-fs dn` | Samme domene |
| `-fs rdn` | Rotdomene |
| `-fs fqdn` | Fullt domene |
| `-cs depth-first` | Gjennomgang i dybden først |
| `-cs breadth-first` | Gjennomgang i bredden først |

### Ytelsesflagg

| Flagg | Beskrivelse |
|------|-------------|
| `-c 50` | Parallellitet |
| `-p 10` | Parallell kjøring |
| `-rl 100` | Hastighetsgrense |
| `-timeout 30` | Tidsavbrudd for forespørsel |

### Filterflagg

| Flagg | Beskrivelse |
|------|-------------|
| `-em js,php` | Treff filendelser |
| `-ef png,jpg` | Filtrer filendelser |
| `-mc 200` | Treff statuskoder |
| `-fc 404` | Filtrer statuskoder |

---

## 🎯 Arbeidsflyter for bug bounty

### Grunnleggende gjennomgang

```bash
# Rask gjennomgang
katana -u https://target.com -d 3 -o urls.txt
```

### Omfattende gjennomgang

```bash
# Dyp gjennomgang med JavaScript
katana -u https://target.com -d 5 -headless -c 50 \
  -ef png,jpg,gif,css,svg,woff,woff2,ttf,eot,ico \
  -o all_urls.txt
```

### Oppdagelse av parametere

```bash
# Finn URL-er med parametere
katana -u https://target.com -d 3 | grep "?" | sort -u

# Med grep for bestemte parametere
katana -u https://target.com | grep -E "id=|user=|admin=|file="
```

### Kombinert med andre verktøy

```bash
# Katana + httpx
katana -u https://target.com -d 3 | httpx -silent -sc

# Katana + Nuclei
katana -u https://target.com -d 3 | nuclei -t nuclei-templates/

# Full pipeline
subfinder -d target.com | httpx -silent | katana -d 3 | grep "=" | sort -u
```

### Autentisert gjennomgang

```bash
# Med øktens informasjonskapsel
katana -u https://target.com \
  -H "Cookie: session=abc123" \
  -d 5 \
  -headless \
  -o authenticated_urls.txt
```

---

## 📚 Ressurser

- [Katana GitHub](https://github.com/projectdiscovery/katana)
- [ProjectDiscovery](https://projectdiscovery.io/)

### Relaterte hurtigreferanser
- [Bug Bounty Methodology](../../Bug-Bounty-Methodology/translations/README.nb.md)
- [httpx](../../httpx/translations/README.nb.md)
- [Nuclei](../../Nuclei/translations/README.nb.md)
- [GAU](../../GAU/translations/README.nb.md)

---

<p align="center">
  <b>⚔️ Gå gjennom nettstedene!</b><br>
  <i>Katana – moderne webcrawler</i>
</p>
