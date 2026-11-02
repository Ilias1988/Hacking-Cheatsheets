# 🔍 Subfinder – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

> **Begreper og versjoner:** Passive kilder samler allerede tilgjengelige opplysninger; senere DNS-/HTTP-verktøy kan likevel kontakte målmiljøet. Kildelister, antall, API-vilkår, gratisnivåer og standardverdier er historiske eksempler. `-recursive` velger kilder som støtter rekursiv håndtering; det garanterer ikke uttømmende gjentatt søk til enhver dybde. `-t` gjelder aktiv oppslagshåndtering i den dokumenterte hjelpen, ikke nødvendigvis parallellitet mot alle passive kilder. Se [Subfinders offisielle dokumentasjon](https://github.com/projectdiscovery/subfinder). Eksemplene er bevart.

```
  ____        _     __ _           _           
 / ___| _   _| |__ / _(_)_ __   __| | ___ _ __ 
 \___ \| | | | '_ \ |_| | '_ \ / _` |/ _ \ '__|
  ___) | |_| | |_) |  _| | | | | (_| |  __/ |   
 |____/ \__,_|_.__/|_| |_|_| |_|\__,_|\___|_|   
                                                
   Rask oppdagelse av underdomener
```

<p align="center">
  <img src="https://img.shields.io/badge/Subfinder-Subdomain_Discovery-red?style=for-the-badge" alt="Subfinder">
  <img src="https://img.shields.io/badge/Passive-Recon-orange?style=for-the-badge" alt="Passive">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🌐 Rask passiv kartlegging av underdomener fra ProjectDiscovery</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Grunnleggende syntaks](#-grunnleggende-syntaks)
- [Oppdagelse av underdomener](#-oppdagelse-av-underdomener)
- [Datakilder](#-datakilder)
- [API-konfigurasjon](#-api-konfigurasjon)
- [Utdataalternativer](#-utdataalternativer)
- [Avanserte funksjoner](#-avanserte-funksjoner)
- [Arbeidsflyt for bug bounty](#-arbeidsflyt-for-bug-bounty)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

**Subfinder** er et raskt verktøy for passiv kartlegging av underdomener fra flere kilder, blant annet søkemotorer, nettsteder for tekstutdrag og etterretningsplattformer.

### Viktige egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **Raskt** | Go-basert, med høy parallellitet |
| **Passivt** | Ingen direkte kontakt med målet |
| **Flere kilder** | 40+ datakilder |
| **API-integrasjon** | Støtte for betalte kilder |
| **Egnet for pipelines** | Samarbeider med andre verktøy |
| **JSON-utdata** | Støtte for strukturerte utdata |

### Subfinder sammenlignet med andre verktøy

| Egenskap | Subfinder | Amass | Assetfinder |
|---------|-----------|-------|-------------|
| **Hastighet** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| **Kilder** | 40+ | 50+ | 10+ |
| **Aktiv skanning** | ❌ Nei | ✅ Ja | ❌ Nei |
| **API-støtte** | ✅ Ja | ✅ Ja | ❌ Nei |
| **Brukervennlighet** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |

---

## 📥 Installasjon

### Med Go
```bash
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
```

### Kali Linux
```bash
sudo apt install subfinder
```

### Nedlasting av binærfil
```bash
# Last ned fra utgivelser
https://github.com/projectdiscovery/subfinder/releases

# Linux
wget https://github.com/projectdiscovery/subfinder/releases/download/v2.x.x/subfinder_2.x.x_linux_amd64.zip
unzip subfinder_2.x.x_linux_amd64.zip
sudo mv subfinder /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/subfinder:latest
docker run projectdiscovery/subfinder -d example.com
```

### Kontroller installasjonen
```bash
subfinder -version
subfinder -h
```

---

<a id="-grunnleggende-syntaks"></a>
## ⌨️ Grunnleggende syntaks

### Generell syntaks
```bash
subfinder [options] -d <domain>
```

### Viktige alternativer

| Innstilling | Beskrivelse |
|--------|-------------|
| `-d` | Måldomene |
| `-dL` | Fil med domeneliste |
| `-o` | Utdatafil |
| `-oJ` | JSON-utdata |
| `-silent` | Stille modus (bare underdomener) |
| `-v` | Detaljerte utdata |
| `-t` | Antall tråder |
| `-all` | Bruk alle kilder |

### Kom raskt i gang
```bash
# Grunnleggende kartlegging
subfinder -d example.com

# Stille utdata (ren liste)
subfinder -d example.com -silent

# Lagre i fil
subfinder -d example.com -o subdomains.txt

# Flere domener
subfinder -dL domains.txt -o all-subs.txt
```

---

## 🔍 Oppdagelse av underdomener

### Ett domene

```bash
# Grunnleggende skanning
subfinder -d target.com

# Detaljerte utdata
subfinder -d target.com -v

# Stille modus (bare underdomener)
subfinder -d target.com -silent

# Vis kilder
subfinder -d target.com -cs
```

### Flere domener

```bash
# Fra fil
subfinder -dL domains.txt

# Skriv resultatet til fil
subfinder -dL domains.txt -o all_subdomains.txt

# Stille modus for videreføring i pipeline
subfinder -dL domains.txt -silent | httpx
```

### Rekursiv kartlegging

```bash
# Aktiver rekursjon (finn under-underdomener)
subfinder -d target.com -recursive

# Rekursjon med alle kilder
subfinder -d target.com -recursive -all
```

---

## 📊 Datakilder

### Gratis kilder (standard)

Subfinder bruker ifølge originalen disse kildene som standard (uten API-nøkkel):
- Alienvault
- AnubisDB
- Crtsh
- Hackertarget
- Rapiddns
- Sublist3r
- ThreatCrowd
- URLScan
- Wayback Machine

### Betalte kilder (API kreves)

Disse kildene krever API-nøkler for bedre resultater:
- **Shodan**
- **SecurityTrails**
- **VirusTotal**
- **Censys**
- **Chaos**
- **GitHub**
- **PassiveTotal**
- **Binaryedge**
- **WhoisXML**
- **ZoomEye**

### Håndtering av kilder

```bash
# List alle kilder
subfinder -ls

# Bruk bare bestemte kilder
subfinder -d target.com -sources crtsh,hackertarget,alienvault

# Utelat bestemte kilder
subfinder -d target.com -es shodan,securitytrails

# Bruk alle kilder (også langsomme)
subfinder -d target.com -all

# Vis hvilken kilde som fant hvert underdomene
subfinder -d target.com -cs
```

---

## 🔑 API-konfigurasjon

### Plassering av konfigurasjonsfil

```bash
# Standardplassering
~/.config/subfinder/provider-config.yaml

# Eller angi en egendefinert plassering
subfinder -d target.com -pc /path/to/config.yaml
```

### Mal for konfigurasjonsfil

```yaml
# ~/.config/subfinder/provider-config.yaml

binaryedge:
  - your_api_key_here
censys:
  - your_api_id:your_api_secret
certspotter: []
chaos:
  - your_chaos_key
chinaz: []
dnsdb: []
fofa:
  - your_email:your_key
fullhunt: []
github:
  - your_github_token
hunter:
  - your_hunter_key
intelx:
  - your_intelx_key
passivetotal:
  - your_username:your_key
securitytrails:
  - your_security_trails_key
shodan:
  - your_shodan_key
virustotal:
  - your_virustotal_key
whoisxmlapi:
  - your_whoisxml_key
zoomeye:
  - your_zoomeye_key
```

### Hente API-nøkler

| Kilde | Gratisnivå | URL |
|--------|-----------|-----|
| **VirusTotal** | ✅ Ja | https://www.virustotal.com |
| **SecurityTrails** | ✅ Ja (begrenset) | https://securitytrails.com |
| **Shodan** | ✅ Ja (begrenset) | https://shodan.io |
| **Censys** | ✅ Ja | https://censys.io |
| **Chaos** | ✅ Gratis (begrenset) | https://chaos.projectdiscovery.io |
| **GitHub** | ✅ Gratis | https://github.com/settings/tokens |

### Anbefalt gratisoppsett

```yaml
# Anbefalt minimum for bug bounty
virustotal:
  - your_free_key
github:
  - your_github_token
chaos:
  - your_chaos_key
```

---

## 📤 Utdataalternativer

### Utdataformater

```bash
# Ren tekst (standard)
subfinder -d target.com -o subs.txt

# JSON-utdata
subfinder -d target.com -oJ -o subs.json

# JSON-linjer
subfinder -d target.com -oJL -o subs.jsonl

# CSV-format
subfinder -d target.com -oD -o subs.csv
```

### Filtrering av utdata

```bash
# Stille modus (rene utdata)
subfinder -d target.com -silent

# Vis bare nye underdomener (utelat kjente)
subfinder -d target.com -exclude-subdomains known.txt

# Uten farger
subfinder -d target.com -nc
```

### Videreføring av utdata

```bash
# Send til httpx
subfinder -d target.com -silent | httpx -silent

# Send til nuclei
subfinder -d target.com -silent | httpx -silent | nuclei

# Lagre og behandle
subfinder -d target.com -silent | tee subs.txt | httpx
```

---

## ⚡ Avanserte funksjoner

### Tråder og ytelse

```bash
# Angi tråder (standard: 10)
subfinder -d target.com -t 50

# Hastighetsbegrensning
subfinder -d target.com -rate-limit 100

# Tidsavbrudd
subfinder -d target.com -timeout 30
```

### Treff og filtrering

```bash
# Treff bestemte mønstre
subfinder -d target.com -match "api,dev,stage"

# Filtrer mønstre
subfinder -d target.com -filter "www,mail"
```

### Resolveralternativer

```bash
# Bruk egendefinerte resolvere
subfinder -d target.com -rL resolvers.txt

# Slå opp underdomener
subfinder -d target.com -nW
```

### Proxystøtte

```bash
# HTTP-proxy
subfinder -d target.com -proxy http://127.0.0.1:8080

# SOCKS-proxy
subfinder -d target.com -proxy socks5://127.0.0.1:9050
```

---

## 🔄 Arbeidsflyt for bug bounty

### Full pipeline for rekognosering

```bash
# Trinn 1: Kartlegging av underdomener
subfinder -d target.com -all -o subs.txt

# Trinn 2: Kontroller tilgjengelige verter
cat subs.txt | httpx -o live.txt

# Trinn 3: Skann med nuclei
nuclei -l live.txt -severity critical,high -o vulns.txt

# Kommando på én linje
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Arbeidsflyt for flere domener

```bash
# Kartlegg alle domenene i programmet
subfinder -dL scope.txt -all -o all-subs.txt

# Behandle resultater
cat all-subs.txt | httpx -silent | nuclei
```

### Kontinuerlig overvåking

```bash
# Lagre et utgangspunkt
subfinder -d target.com -silent > baseline.txt

# Finn nye underdomener senere
subfinder -d target.com -silent > current.txt
diff baseline.txt current.txt | grep ">" | cut -d" " -f2 > new-subs.txt
```

### Integrasjon med andre verktøy

```bash
# Med httpx (tilgjengelige verter)
subfinder -d target.com -silent | httpx

# Med ffuf (katalogfuzzing)
subfinder -d target.com -silent | httpx -silent | \
  xargs -I {} ffuf -u {}/FUZZ -w wordlist.txt

# Med nmap (portskanning)
subfinder -d target.com -silent | dnsx -a -silent | nmap -iL -

# Full pipeline
subfinder -d target.com -silent | \
  httpx -title -status-code -tech-detect -silent | \
  tee recon-results.txt
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: Grunnleggende kartlegging
```bash
subfinder -d hackerone.com -silent
```

### Eksempel 2: Med alle kilder
```bash
subfinder -d target.com -all -v
```

### Eksempel 3: Lagre resultater
```bash
subfinder -d target.com -o target-subs.txt
```

### Eksempel 4: Flere domener
```bash
echo -e "target1.com\ntarget2.com" | subfinder -silent
```

### Eksempel 5: Vis kilder
```bash
subfinder -d target.com -cs
```

### Eksempel 6: JSON-utdata
```bash
subfinder -d target.com -oJ -o results.json
```

### Eksempel 7: Med proxy
```bash
subfinder -d target.com -proxy http://127.0.0.1:8080
```

### Eksempel 8: Høy ytelse
```bash
subfinder -d target.com -all -t 100 -o subs.txt
```

### Eksempel 9: Full bug bounty-pipeline
```bash
subfinder -d target.com -all -silent | \
  httpx -silent -status-code -title | \
  tee live-hosts.txt | \
  nuclei -severity critical,high
```

### Eksempel 10: Rekursiv oppdagelse
```bash
subfinder -d target.com -recursive -all -silent
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Oppgave | Kommando |
|------|---------|
| Grunnleggende skanning | `subfinder -d target.com` |
| Stille utdata | `subfinder -d target.com -silent` |
| Lagre i fil | `subfinder -d target.com -o subs.txt` |
| Alle kilder | `subfinder -d target.com -all` |
| Vis kilder | `subfinder -d target.com -cs` |
| Flere domener | `subfinder -dL domains.txt` |
| JSON-utdata | `subfinder -d target.com -oJ` |
| List kilder | `subfinder -ls` |

### Vanlige alternativer

| Innstilling | Beskrivelse |
|--------|-------------|
| `-d` | Måldomene |
| `-dL` | Fil med domeneliste |
| `-o` | Utdatafil |
| `-oJ` | JSON-utdata |
| `-silent` | Stille modus |
| `-all` | Bruk alle kilder |
| `-cs` | Vis kilden til hvert underdomene |
| `-t` | Tråder |
| `-recursive` | Rekursiv kartlegging |

### Vanlige pipelines

| Pipeline | Kommando |
|----------|---------|
| Tilgjengelige verter | `subfinder -d target.com -silent \| httpx` |
| Full rekognosering | `subfinder -d target.com \| httpx \| nuclei` |
| Portskanning | `subfinder -d target.com -silent \| dnsx -a \| nmap -iL -` |

---

## 💡 Tips og anbefalte arbeidsmåter

### Tips for bug bounty

1. **Konfigurer API-nøkler**
   ```bash
   # Bedre resultater med API-er
   vim ~/.config/subfinder/provider-config.yaml
   ```

2. **Bruk alle kilder for bedre dekning**
   ```bash
   subfinder -d target.com -all
   ```

3. **Kombiner med andre verktøy**
   ```bash
   subfinder -d target.com -silent | httpx | nuclei
   ```

4. **Overvåk nye underdomener**
   ```bash
   # Daglig kontroll av nye ressurser
   subfinder -d target.com -silent > today.txt
   ```

### Ytelsestips

```bash
# Høyt trådantall
subfinder -d target.com -t 100 -all

# Tidsavbrudd for langsomme kilder
subfinder -d target.com -timeout 60
```

---

## ⚠️ Juridisk merknad

> **ADVARSEL:** Subfinder utfører passiv rekognosering og kontakter ikke målene direkte. Etterfølgende verktøy i pipelinen kan likevel gjøre det.
> 
> - ✅ Bruk mot autoriserte programmer
> - ✅ Bug bounty-mål innenfor avtalt omfang
> - ✅ Dine egne domener
> - ❌ Ikke kartlegg mål uten tillatelse
> - ❌ Respekter programmets omfang
> 
> **Sørg alltid for at du har tillatelse.**

---

## 📚 Ressurser

### Offisielle ressurser
- [Subfinder GitHub](https://github.com/projectdiscovery/subfinder)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentation](https://docs.projectdiscovery.io/tools/subfinder/)

### Relaterte hurtigreferanser
- [Nuclei](../../Nuclei/translations/README.nb.md)
- [ffuf](../../ffuf/translations/README.nb.md)
- [Nmap](../../Nmap/translations/README.nb.md)

---

<p align="center">
  <b>🔍 Finn underdomenene!</b><br>
  <i>Første trinn i rekognosering for bug bounty</i>
</p>
