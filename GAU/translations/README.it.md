# 🔗 GAU - Get All URLs

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗  █████╗ ██╗   ██╗
  ██╔════╝ ██╔══██╗██║   ██║
  ██║  ███╗███████║██║   ██║
  ██║   ██║██╔══██║██║   ██║
  ╚██████╔╝██║  ██║╚██████╔╝
   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝
       Get All URLs - Raccolta rapida degli URL
```

<p align="center">
  <img src="https://img.shields.io/badge/GAU-URL_Discovery-blue?style=for-the-badge" alt="GAU">
  <img src="https://img.shields.io/badge/Wayback-green?style=for-the-badge" alt="Wayback">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Correzioni dell'originale:** `--blacklist` esclude estensioni, non provider; `--mt` e `--ft` selezionano o escludono tipi MIME, non espressioni regolari. I dati storici non provano che un URL sia ancora raggiungibile; i filtri `gf` individuano candidati, non vulnerabilità confermate. Allo stesso modo una risposta 200 dopo la sostituzione di un parametro non dimostra l'esecuzione di JavaScript. L'originale conserva esempi di opzioni e percorsi da verificare per la release usata. Vedi il [README ufficiale GAU](https://github.com/lc/gau#usage).

---

## 📋 Indice

- [Che cos'è GAU](#-che-cosè-gau)
- [Installazione](#-installazione)
- [Utilizzo di base](#-utilizzo-di-base)
- [Provider](#-provider)
- [Filtraggio](#-filtraggio)
- [Integrazione](#-integrazione)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è GAU

**GAU** (Get All URLs) raccoglie URL noti da più fonti:

- 🔍 **Wayback Machine** - URL storici
- 🌐 **Common Crawl** - Dati di scansione del web
- 📡 **AlienVault OTX** - URL di intelligence sulle minacce
- 🔒 **URLScan.io** - URL analizzati

### Perché GAU?

| Vantaggio | Descrizione |
|---------|-------------|
| **Ricognizione passiva** | Nessun contatto diretto con il bersaglio |
| **Dati storici** | Trova endpoint vecchi o dimenticati |
| **Veloce** | Raccolta parallela |
| **Più fonti** | Risultati completi |

---

## 🚀 Installazione

```bash
# Installazione con Go (consigliata)
go install github.com/lc/gau/v2/cmd/gau@latest

# Dal sorgente
git clone https://github.com/lc/gau.git
cd gau/cmd/gau
go build

# Verifica
gau --version
```

---

## 📖 Utilizzo di base

### Singolo dominio

```bash
# Utilizzo di base
gau example.com

# Con file di output
gau example.com -o urls.txt

# Modalità silenziosa (senza banner)
gau --subs example.com
```

### Più domini

```bash
# Da stdin
echo "example.com" | gau

# Più domini
cat domains.txt | gau

# Da file
gau --threads 5 < domains.txt
```

### Inclusione dei sottodomini

```bash
# Includi i sottodomini nei risultati
gau --subs example.com
```

### Gestione dei thread

```bash
# Imposta i thread (predefinito: 1)
gau --threads 5 example.com

# Regola per limitare la frequenza
gau --threads 2 example.com
```

---

## 🌐 Provider

### Provider disponibili

| Provider | Descrizione | Predefinito |
|----------|-------------|---------|
| `wayback` | Wayback Machine | ✅ |
| `commoncrawl` | Common Crawl | ✅ |
| `otx` | AlienVault OTX | ✅ |
| `urlscan` | URLScan.io | ✅ |

### Selezione dei provider

```bash
# Usa solo provider specifici
gau --providers wayback example.com
gau --providers wayback,otx example.com

# Escludi un provider
gau --blacklist commoncrawl example.com
```

### Configurazione dei provider

```bash
# Percorso del file di configurazione
~/.gau.toml

# Configurazione di esempio
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

## 🔍 Filtraggio

### Filtraggio per codice di stato

```bash
# Solo URL che hanno restituito 200
gau --mc 200 example.com

# Più codici di stato
gau --mc 200,301,302 example.com

# ESCLUDI codici specifici
gau --fc 404,500 example.com
```

### Filtraggio per estensione

```bash
# Seleziona estensioni
gau --mt "\.js$" example.com
gau --mt "\.php$" example.com

# ESCLUDI estensioni
gau --ft "\.jpg$|\.png$|\.gif$|\.css$" example.com
```

### Filtraggio per tipo MIME

```bash
# Filtra per tipo di contenuto
gau --mt "application/json" example.com
gau --mt "text/html" example.com
```

### Formato di output

```bash
# Output JSON
gau --json example.com

# Modalità dettagliata
gau --verbose example.com
```

---

## 🔗 Integrazione

### Con httpx

```bash
# Verifica gli URL raggiungibili
gau example.com | httpx -silent

# Con codici di stato
gau example.com | httpx -sc -title
```

### Con grep/estrazione dei parametri

```bash
# Trova URL con parametri
gau example.com | grep "="

# Trova pattern specifici
gau example.com | grep -E "\.php\?|\.asp\?"

# Pattern GF
gau example.com | gf xss
gau example.com | gf sqli
gau example.com | gf lfi
gau example.com | gf redirect
```

### Con urldedupe

```bash
# Rimuovi i duplicati
gau example.com | urldedupe

# Alternativa: sort -u
gau example.com | sort -u
```

### Con uro

```bash
# Deduplicazione intelligente degli URL
gau example.com | uro
```

### Pipeline completa

```bash
# Pipeline completa di raccolta degli URL
echo "example.com" | gau --subs --threads 5 | \
  uro | \
  httpx -silent -mc 200 | \
  grep "=" > params.txt
```

---

## 📊 Riferimento rapido

### Comandi di base

| Comando | Descrizione |
|---------|-------------|
| `gau domain.com` | Ottieni URL per il dominio |
| `gau --subs domain.com` | Includi i sottodomini |
| `gau -o urls.txt domain.com` | Salva l'output su file |
| `gau --threads 5 domain.com` | Con più thread |

### Opzioni di filtraggio

| Opzione | Descrizione |
|------|-------------|
| `--mc 200` | Seleziona il codice di stato |
| `--fc 404` | Escludi il codice di stato |
| `--mt "regex"` | Seleziona tramite regex |
| `--ft "regex"` | Escludi tramite regex |
| `--json` | Output JSON |

### Opzioni dei provider

| Opzione | Descrizione |
|------|-------------|
| `--providers wayback,otx` | Usa provider specifici |
| `--blacklist commoncrawl` | Escludi un provider |

### Comandi utili su una sola riga

```bash
# Ottieni tutti i file JS
gau example.com | grep -E "\.js$" | sort -u

# Trova endpoint API
gau example.com | grep -iE "/api/|/v[0-9]/"

# Trova i parametri
gau example.com | grep "?" | grep "=" | sort -u

# Trova potenziali SQLi
gau example.com | gf sqli

# Trova potenziali XSS
gau example.com | gf xss

# URL raggiungibili con parametri
gau example.com | grep "=" | httpx -silent
```

---

## 🎯 Flusso di lavoro per il bug bounty

```bash
# 1. Raccogli tutti gli URL
gau --subs --threads 5 target.com > all_urls.txt

# 2. Filtra gli URL interessanti
cat all_urls.txt | grep "=" | uro > params.txt

# 3. Verifica la raggiungibilità
cat params.txt | httpx -silent > live_params.txt

# 4. Trova vulnerabilità
cat live_params.txt | gf sqli > sqli.txt
cat live_params.txt | gf xss > xss.txt
cat live_params.txt | gf lfi > lfi.txt
cat live_params.txt | gf redirect > redirect.txt

# 5. Verifica
cat xss.txt | qsreplace '"><script>alert(1)</script>' | \
  httpx -silent -mc 200
```

---

## 📚 Risorse

- [GAU GitHub](https://github.com/lc/gau)
- [GF Patterns](https://github.com/1ndianl33t/Gf-Patterns)

### Schede correlate
- [Metodologia per il bug bounty](../../Bug-Bounty-Methodology/translations/README.it.md)
- [Subfinder](../../Subfinder/translations/README.it.md)
- [httpx](../../httpx/translations/README.it.md)

---

<p align="center">
  <b>🔗 Raccogli tutti gli URL!</b><br>
  <i>GAU - Individuazione passiva degli URL</i>
</p>
