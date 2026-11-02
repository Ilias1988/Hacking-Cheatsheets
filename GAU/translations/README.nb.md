# 🔗 GAU – Get All URLs

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

> **Rettelse og versjoner:** GAU henter historiske URL-er fra tilbydere; statuskoder i slike data beviser ikke dagens tilgjengelighet. Originalens `--blacklist commoncrawl` er misvisende: blacklist gjelder filendelser, mens providers velger datakilder. `--mt` og `--ft` gjelder MIME-typer i den offisielle hjelpen, ikke vilkårlige regulære uttrykk. Konfigurasjonsstier og flagg må kontrolleres for versjonen. Se [GAUs egen dokumentasjon](https://github.com/lc/gau). Kodeeksemplene er uendret.

```
   ██████╗  █████╗ ██╗   ██╗
  ██╔════╝ ██╔══██╗██║   ██║
  ██║  ███╗███████║██║   ██║
  ██║   ██║██╔══██║██║   ██║
  ╚██████╔╝██║  ██║╚██████╔╝
   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ 
       Get All URLs – rask innsamling av URL-er
```

<p align="center">
  <img src="https://img.shields.io/badge/GAU-URL_Discovery-blue?style=for-the-badge" alt="GAU">
  <img src="https://img.shields.io/badge/Wayback-green?style=for-the-badge" alt="Wayback">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er GAU](#-hva-er-gau)
- [Installasjon](#-installasjon)
- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Tilbydere](#-tilbydere)
- [Filtrering](#-filtrering)
- [Integrasjon](#-integrasjon)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er GAU

**GAU** (Get All URLs) henter kjente URL-er fra flere kilder:

- 🔍 **Wayback Machine** – historiske URL-er
- 🌐 **Common Crawl** – data fra webgjennomgang
- 📡 **AlienVault OTX** – URL-er fra trusseletterretning
- 🔒 **URLScan.io** – skannede URL-er

### Hvorfor GAU?

| Fordel | Beskrivelse |
|---------|-------------|
| **Passiv rekognosering** | Ingen direkte kontakt med målet |
| **Historiske data** | Finn gamle eller glemte endepunkter |
| **Raskt** | Parallell innhenting |
| **Flere kilder** | Omfattende resultater |

---

## 🚀 Installasjon

```bash
# Installasjon med Go (anbefalt)
go install github.com/lc/gau/v2/cmd/gau@latest

# Fra kildekode
git clone https://github.com/lc/gau.git
cd gau/cmd/gau
go build

# Kontroller
gau --version
```

---

## 📖 Grunnleggende bruk

### Ett domene

```bash
# Grunnleggende bruk
gau example.com

# Med utdatafil
gau example.com -o urls.txt

# Stille modus (uten banner)
gau --subs example.com
```

### Flere domener

```bash
# Fra standard inndata
echo "example.com" | gau

# Flere domener
cat domains.txt | gau

# Fra fil
gau --threads 5 < domains.txt
```

### Ta med underdomener

```bash
# Ta med underdomener i resultatene
gau --subs example.com
```

### Tråder

```bash
# Angi tråder (standard: 1)
gau --threads 5 example.com

# Tilpass til hastighetsgrenser
gau --threads 2 example.com
```

---

## 🌐 Tilbydere

### Tilgjengelige tilbydere

| Tilbyder | Beskrivelse | Standard |
|----------|-------------|---------|
| `wayback` | Wayback Machine | ✅ |
| `commoncrawl` | Common Crawl | ✅ |
| `otx` | AlienVault OTX | ✅ |
| `urlscan` | URLScan.io | ✅ |

### Velge tilbydere

```bash
# Bruk bare bestemte tilbydere
gau --providers wayback example.com
gau --providers wayback,otx example.com

# Utelat tilbyder
gau --blacklist commoncrawl example.com
```

### Tilbyderkonfigurasjon

```bash
# Plassering av konfigurasjonsfil
~/.gau.toml

# Eksempelkonfigurasjon
cat > ~/.gau.toml << EOF
threads = 5
verbose = false
retries = 3
subdomains = false
providers = ["wayback","commoncrawl","otx","urlscan"]
blacklist = []
json = false
EOF
```

---

## 🔍 Filtrering

### Filtrere etter statuskode

```bash
# Bare URL-er som returnerte 200
gau --mc 200 example.com

# Flere statuskoder
gau --mc 200,301,302 example.com

# Filtrer BORT bestemte koder
gau --fc 404,500 example.com
```

### Filtrere etter filendelse

```bash
# Treff filendelser
gau --mt "\.js$" example.com
gau --mt "\.php$" example.com

# Filtrer BORT filendelser
gau --ft "\.jpg$|\.png$|\.gif$|\.css$" example.com
```

### Filtrere etter MIME-type

```bash
# Filtrer etter innholdstype
gau --mt "application/json" example.com
gau --mt "text/html" example.com
```

### Utdataformat

```bash
# JSON-utdata
gau --json example.com

# Detaljert modus
gau --verbose example.com
```

---

## 🔗 Integrasjon

### Med httpx

```bash
# Kontroller tilgjengelige URL-er
gau example.com | httpx -silent

# Med statuskoder
gau example.com | httpx -sc -title
```

### Med grep / uthenting av parametere

```bash
# Finn URL-er med parametere
gau example.com | grep "="

# Finn bestemte mønstre
gau example.com | grep -E "\.php\?|\.asp\?"

# GF-mønstre
gau example.com | gf xss
gau example.com | gf sqli
gau example.com | gf lfi
gau example.com | gf redirect
```

### Med urldedupe

```bash
# Fjern duplikater
gau example.com | urldedupe

# Alternativ: sort -u
gau example.com | sort -u
```

### Med uro

```bash
# Intelligent fjerning av dupliserte URL-er
gau example.com | uro
```

### Full pipeline

```bash
# Full pipeline for innsamling av URL-er
echo "example.com" | gau --subs --threads 5 | \
  uro | \
  httpx -silent -mc 200 | \
  grep "=" > params.txt
```

---

## 📊 Hurtigreferanse

### Grunnleggende kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `gau domain.com` | Hent URL-er for domenet |
| `gau --subs domain.com` | Ta med underdomener |
| `gau -o urls.txt domain.com` | Skriv til fil |
| `gau --threads 5 domain.com` | Flere tråder |

### Filteralternativer

| Flagg | Beskrivelse |
|------|-------------|
| `--mc 200` | Treff statuskode |
| `--fc 404` | Filtrer statuskode |
| `--mt "regex"` | Treff regulært uttrykk |
| `--ft "regex"` | Filtrer regulært uttrykk |
| `--json` | JSON-utdata |

### Tilbyderalternativer

| Flagg | Beskrivelse |
|------|-------------|
| `--providers wayback,otx` | Bruk bestemte tilbydere |
| `--blacklist commoncrawl` | Utelat tilbyder |

### Nyttige kommandoer på én linje

```bash
# Hent alle JS-filer
gau example.com | grep -E "\.js$" | sort -u

# Finn API-endepunkter
gau example.com | grep -iE "/api/|/v[0-9]/"

# Finn parametere
gau example.com | grep "?" | grep "=" | sort -u

# Finn mulig SQL-injeksjon
gau example.com | gf sqli

# Finn mulig XSS
gau example.com | gf xss

# Tilgjengelige URL-er med parametere
gau example.com | grep "=" | httpx -silent
```

---

## 🎯 Arbeidsflyt for bug bounty

```bash
# 1. Samle alle URL-er
gau --subs --threads 5 target.com > all_urls.txt

# 2. Filtrer interessante URL-er
cat all_urls.txt | grep "=" | uro > params.txt

# 3. Kontroller tilgjengelighet
cat params.txt | httpx -silent > live_params.txt

# 4. Finn sårbarheter
cat live_params.txt | gf sqli > sqli.txt
cat live_params.txt | gf xss > xss.txt
cat live_params.txt | gf lfi > lfi.txt
cat live_params.txt | gf redirect > redirect.txt

# 5. Test
cat xss.txt | qsreplace '"><script>alert(1)</script>' | \
  httpx -silent -mc 200
```

---

## 📚 Ressurser

- [GAU GitHub](https://github.com/lc/gau)
- [GF Patterns](https://github.com/1ndianl33t/Gf-Patterns)

### Relaterte hurtigreferanser
- [Bug Bounty Methodology](../../Bug-Bounty-Methodology/translations/README.nb.md)
- [Subfinder](../../Subfinder/translations/README.nb.md)
- [httpx](../../httpx/translations/README.nb.md)

---

<p align="center">
  <b>🔗 Samle URL-ene!</b><br>
  <i>GAU – passiv oppdagelse av URL-er</i>
</p>
