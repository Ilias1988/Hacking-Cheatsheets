# 🌐 httpx - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

```
  _     _   _
 | |__ | |_| |_ _ __ __  __
 | '_ \| __| __| '_ \\ \/ /
 | | | | |_| |_| |_) |>  <
 |_| |_|\__|\__| .__//_/\_\
               |_|
                           
   Strumenti rapidi per verifiche HTTP
```

<p align="center">
  <img src="https://img.shields.io/badge/httpx-HTTP_Probe-red?style=for-the-badge" alt="httpx">
  <img src="https://img.shields.io/badge/Fast-Toolkit-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>⚡ Insieme di strumenti HTTP rapido e versatile di ProjectDiscovery</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Nota di compatibilità:** questa guida riguarda httpx di ProjectDiscovery. Le stelle del confronto sono qualitative. Diversi nomi o significati delle opzioni dell'originale non corrispondono alla release documentata: `-ml`/`-fl` riguardano la lunghezza, mentre i conteggi delle righe hanno opzioni distinte; `-H` imposta header delle richieste e non estrae un singolo header della risposta. La modalità `-resume` usa il file di ripresa previsto dallo strumento, non una generica paginazione. Le opzioni di filtro per tecnologia, memorizzazione e browser vanno verificate nella release usata. I comandi sono conservati: consulta le [opzioni ufficiali](https://docs.projectdiscovery.io/opensource/httpx/usage).

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Sintassi di base](#-sintassi-di-base)
- [Verifica e individuazione](#-verifica-e-individuazione)
- [Estrazione delle informazioni](#-estrazione-delle-informazioni)
- [Filtri e criteri di corrispondenza](#-filtri-e-criteri-di-corrispondenza)
- [Opzioni di output](#-opzioni-di-output)
- [Funzionalità avanzate](#-funzionalità-avanzate)
- [Flusso di lavoro per il bug bounty](#-flusso-di-lavoro-per-il-bug-bounty)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

**httpx** è un insieme di strumenti HTTP rapido e versatile che esegue più verifiche usando la libreria retryablehttp. È progettato per chi pratica bug bounty e ricerca sulla sicurezza.

### Funzionalità principali

| Funzionalità | Descrizione |
|---------|-------------|
| **Veloce** | Strumento basato su Go con elevata concorrenza |
| **Più verifiche** | Codice di stato, titolo, tecnologie e header |
| **Rilevamento delle tecnologie** | Rilevamento basato su Wappalyzer |
| **Screenshot** | Screenshot tramite browser headless |
| **Pipeline** | Si integra con subfinder e nuclei |
| **Output JSON** | Supporto per output strutturato |

### Confronto tra httpx e altri strumenti

| Funzionalità | httpx | curl | wget |
|---------|-------|------|------|
| **Velocità** | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐ |
| **Concorrente** | ✅ Sì | ❌ No | ❌ No |
| **Rilevamento delle tecnologie** | ✅ Sì | ❌ No | ❌ No |
| **Screenshot** | ✅ Sì | ❌ No | ❌ No |
| **Pipeline** | ✅ Sì | ⚠️ Manuale | ⚠️ Manuale |

---

## 📥 Installazione

### Con Go
```bash
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest
```

### Kali Linux
```bash
sudo apt install httpx-toolkit
```

### Download del binario
```bash
# Scarica dalla pagina delle release
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

### Verifica dell'installazione
```bash
httpx -version
httpx -h
```

---

## ⌨ Sintassi di base

### Sintassi generale
```bash
httpx [options] -u <url>
httpx [options] -l <file>
cat urls.txt | httpx [options]
```

### Opzioni essenziali

| Opzione | Descrizione |
|--------|-------------|
| `-u` | URL di un singolo bersaglio |
| `-l` | File con un elenco di URL |
| `-sc` | Mostra il codice di stato |
| `-title` | Mostra il titolo della pagina |
| `-td` | Rilevamento delle tecnologie |
| `-o` | File di output |
| `-silent` | Modalità silenziosa |
| `-json` | Output JSON |

### Avvio rapido
```bash
# Verifica un singolo URL
httpx -u https://example.com

# Verifica i bersagli da file
httpx -l urls.txt

# Da stdin (pipeline)
cat hosts.txt | httpx

# Con subfinder
subfinder -d example.com -silent | httpx
```

---

## 🔍 Verifica e individuazione

### Verifiche di base

```bash
# Verifica HTTP/HTTPS sugli host
cat hosts.txt | httpx

# Includi il codice di stato
cat hosts.txt | httpx -sc

# Verifica porte specifiche
cat hosts.txt | httpx -p 80,443,8080,8443

# Verifica tutte le porte comuni
cat hosts.txt | httpx -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```

### Scansione delle porte

```bash
# Porte personalizzate
httpx -l hosts.txt -p 80,443,8080,8443

# Intervallo di porte
httpx -l hosts.txt -p 80-100

# Porte web comuni
httpx -l hosts.txt -p http:80,https:443
```

### Rilevamento del protocollo

```bash
# Verifiche HTTP/HTTPS
cat hosts.txt | httpx

# Segue i reindirizzamenti
cat hosts.txt | httpx -follow-redirects

# Numero massimo di reindirizzamenti
cat hosts.txt | httpx -follow-redirects -max-redirects 5

# Senza fallback
cat hosts.txt | httpx -no-fallback
```

---

## 📊 Estrazione delle informazioni

### Codici di stato e titoli

```bash
# Codice di stato
httpx -l urls.txt -sc

# Titolo della pagina
httpx -l urls.txt -title

# Entrambi
httpx -l urls.txt -sc -title

# Lunghezza del contenuto
httpx -l urls.txt -cl

# Server web
httpx -l urls.txt -server

# Tutte le informazioni di base
httpx -l urls.txt -sc -title -cl -server
```

### Rilevamento delle tecnologie

```bash
# Rileva le tecnologie
httpx -l urls.txt -td

# Rilevamento dettagliato delle tecnologie
httpx -l urls.txt -tech-detect

# Con altre informazioni
httpx -l urls.txt -sc -title -td
```

### Header e risposta

```bash
# Header della risposta
httpx -l urls.txt -response-header

# Header specifico
httpx -l urls.txt -include-response-header "Server,X-Powered-By"

# Hash del corpo della risposta
httpx -l urls.txt -hash md5

# Anteprima del corpo
httpx -l urls.txt -bp
```

### Informazioni IP e DNS

```bash
# Indirizzo IP
httpx -l urls.txt -ip

# CNAME
httpx -l urls.txt -cname

# Rilevamento della CDN
httpx -l urls.txt -cdn

# Informazioni ASN
httpx -l urls.txt -asn

# Tutte le informazioni DNS
httpx -l urls.txt -ip -cname -cdn -asn
```

### Informazioni SSL/TLS

```bash
# Verifica TLS
httpx -l urls.txt -tls-probe

# Acquisizione di informazioni TLS
httpx -l urls.txt -tls-grab

# Informazioni sul certificato
httpx -l urls.txt -tlsgrab
```

### Hash della favicon

```bash
# Ottieni l'hash della favicon (utile per ricerche su Shodan)
httpx -l urls.txt -favicon
```

### Acquisizione di screenshot

```bash
# Acquisisci screenshot
httpx -l urls.txt -screenshot

# Screenshot con directory di output
httpx -l urls.txt -screenshot -screenshot-output ./screenshots/

# Opzioni headless
httpx -l urls.txt -screenshot -system-chrome
```

---

## 🎯 Filtri e criteri di corrispondenza

### Filtraggio per codice di stato

```bash
# Seleziona codici di stato
httpx -l urls.txt -mc 200,301,302

# Escludi codici di stato
httpx -l urls.txt -fc 404,403,500

# Seleziona tutto, escludi casi specifici
httpx -l urls.txt -mc all -fc 404
```

### Filtraggio per dimensione

```bash
# Seleziona la lunghezza del contenuto
httpx -l urls.txt -ml 1234

# Escludi lunghezze del contenuto
httpx -l urls.txt -fl 0

# Seleziona il numero di righe
httpx -l urls.txt -mlc 100

# Escludi numeri di righe
httpx -l urls.txt -flc 50
```

### Filtraggio per contenuto

```bash
# Seleziona il corpo della risposta (regex)
httpx -l urls.txt -mr "admin|login"

# Escludi il corpo della risposta (regex)
httpx -l urls.txt -fr "error|not found"

# Seleziona una stringa
httpx -l urls.txt -ms "dashboard"
```

### Filtraggio per tecnologia

```bash
# Seleziona una tecnologia
httpx -l urls.txt -td -mt "WordPress,Nginx"

# Escludi una tecnologia
httpx -l urls.txt -td -ft "Apache"
```

---

## 📤 Opzioni di output

### Formati di output

```bash
# Testo semplice
httpx -l urls.txt -o results.txt

# Output JSON
httpx -l urls.txt -json -o results.json

# Righe JSON
httpx -l urls.txt -jsonl -o results.jsonl

# Output CSV
httpx -l urls.txt -csv -o results.csv
```

### Campi di output

```bash
# Memorizza la risposta
httpx -l urls.txt -sr -srd ./responses/

# Memorizza gli screenshot
httpx -l urls.txt -screenshot -ssd ./screenshots/

# Output personalizzato
httpx -l urls.txt -o output.txt -sc -title -td
```

### Modalità silenziosa e dettagliata

```bash
# Modalità silenziosa (solo URL)
httpx -l urls.txt -silent

# Output dettagliato
httpx -l urls.txt -v

# Debug
httpx -l urls.txt -debug

# Senza colori
httpx -l urls.txt -nc
```

---

## ⚡ Funzionalità avanzate

### Thread e limitazione della frequenza

```bash
# Imposta i thread (predefinito: 50)
httpx -l urls.txt -threads 100

# Limite di frequenza
httpx -l urls.txt -rate-limit 100

# Timeout
httpx -l urls.txt -timeout 10

# Nuovi tentativi
httpx -l urls.txt -retries 3
```

### Supporto proxy

```bash
# Proxy HTTP
httpx -l urls.txt -proxy http://127.0.0.1:8080

# Proxy SOCKS
httpx -l urls.txt -proxy socks5://127.0.0.1:9050

# Burp Suite
httpx -l urls.txt -proxy http://127.0.0.1:8080
```

### Header personalizzati

```bash
# Header personalizzato
httpx -l urls.txt -H "Authorization: Bearer token123"

# Più header
httpx -l urls.txt -H "X-Custom: value" -H "Cookie: session=abc"

# User-Agent
httpx -l urls.txt -H "User-Agent: Mozilla/5.0"
```

### Metodi delle richieste

```bash
# Richiesta HEAD (più rapida)
httpx -l urls.txt -method HEAD

# Metodo personalizzato
httpx -l urls.txt -method OPTIONS

# POST con corpo
httpx -l urls.txt -method POST -body '{"test":"data"}'
```

### Ripresa e paginazione

```bash
# Riprendi dall'ultima posizione
httpx -l urls.txt -resume

# Salva l'avanzamento
httpx -l urls.txt -resume -resume-file progress.txt
```

---

## 🔄 Flusso di lavoro per il bug bounty

### Pipeline completa di ricognizione

```bash
# Passaggio 1: enumerazione dei sottodomini
subfinder -d target.com -o subs.txt

# Passaggio 2: verifica gli host raggiungibili con httpx
cat subs.txt | httpx -sc -title -td -o live.txt

# Passaggio 3: scansione delle vulnerabilità
nuclei -l live.txt -severity critical,high

# Comando su una sola riga
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Ricognizione dettagliata

```bash
# Raccolta completa delle informazioni
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

### Screenshot di tutti gli host raggiungibili

```bash
subfinder -d target.com -silent | httpx -silent -screenshot -ssd ./screenshots/
```

### Individuazione di endpoint interessanti

```bash
# Trova pannelli di amministrazione
subfinder -d target.com -silent | httpx -title -mr "admin|login|dashboard"

# Trova endpoint API
cat subs.txt | httpx -path /api/ -mc 200,401,403

# Verifica percorsi specifici
httpx -l hosts.txt -path /robots.txt -mc 200
```

### Ricerca mirata per tecnologia

```bash
# Trova siti WordPress
subfinder -d target.com -silent | httpx -td -mt "WordPress"

# Trova server Nginx
cat hosts.txt | httpx -server -ms "nginx"
```

---

## 🎬 Esempi pratici

### Esempio 1: verifica di base
```bash
echo "https://example.com" | httpx -sc -title
```

### Esempio 2: ricognizione completa dai sottodomini
```bash
subfinder -d hackerone.com -silent | httpx -sc -title -td -ip
```

### Esempio 3: screenshot di tutti gli host
```bash
httpx -l hosts.txt -screenshot -ssd ./screenshots/
```

### Esempio 4: trova solo gli host raggiungibili
```bash
cat subs.txt | httpx -silent -o live.txt
```

### Esempio 5: rilevamento delle tecnologie
```bash
httpx -l urls.txt -tech-detect -json -o tech.json
```

### Esempio 6: con proxy (Burp)
```bash
httpx -l urls.txt -proxy http://127.0.0.1:8080 -silent
```

### Esempio 7: porte personalizzate
```bash
cat hosts.txt | httpx -p 80,443,8080,8443 -sc -title
```

### Esempio 8: seleziona solo i 200
```bash
cat subs.txt | httpx -mc 200 -silent
```

### Esempio 9: output JSON da elaborare
```bash
httpx -l urls.txt -json -sc -title -td -o results.json
```

### Esempio 10: pipeline completa
```bash
subfinder -d target.com -silent | \
httpx -silent -sc -title -td -ip -cdn -server -json | \
tee results.json | \
jq -r '.url' | \
nuclei -severity critical,high
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Attività | Comando |
|------|---------|
| Verifica di base | `httpx -u url` |
| Da file | `httpx -l urls.txt` |
| Codice di stato | `httpx -l urls.txt -sc` |
| Titolo della pagina | `httpx -l urls.txt -title` |
| Rilevamento delle tecnologie | `httpx -l urls.txt -td` |
| Indirizzo IP | `httpx -l urls.txt -ip` |
| Screenshot | `httpx -l urls.txt -screenshot` |
| Output JSON | `httpx -l urls.txt -json -o out.json` |

### Opzioni comuni

| Opzione | Descrizione |
|--------|-------------|
| `-u` | Singolo URL |
| `-l` | File con elenco di URL |
| `-sc` | Codice di stato |
| `-title` | Titolo della pagina |
| `-td` | Rilevamento delle tecnologie |
| `-ip` | Indirizzo IP |
| `-cdn` | Rilevamento della CDN |
| `-server` | Server web |
| `-mc` | Codici da selezionare |
| `-fc` | Codici da escludere |
| `-json` | Output JSON |
| `-silent` | Modalità silenziosa |

### Pipeline comuni

| Pipeline | Comando |
|----------|---------|
| Host raggiungibili | `subfinder -d target.com \| httpx -silent` |
| Con informazioni | `subfinder -d target.com \| httpx -sc -title -td` |
| Verso nuclei | `httpx -l hosts.txt -silent \| nuclei` |

---

## 💡 Consigli e buone pratiche

### Consigli per il bug bounty

1. **Usa insieme a Subfinder**
   ```bash
   subfinder -d target.com -silent | httpx -silent -sc -title
   ```

2. **Ottieni un quadro completo**
   ```bash
   httpx -l hosts.txt -sc -title -td -ip -cdn -server
   ```

3. **Screenshot per la valutazione iniziale**
   ```bash
   httpx -l hosts.txt -screenshot
   ```

4. **JSON per l'elaborazione**
   ```bash
   httpx -l hosts.txt -json | jq '.url'
   ```

### Consigli per le prestazioni

```bash
# Velocità elevata
httpx -l hosts.txt -threads 100 -timeout 5

# Volume ridotto e velocità bassa
httpx -l hosts.txt -threads 10 -rate-limit 20
```

---

## ⚠ Avvertenza legale

> **ATTENZIONE:** httpx invia richieste HTTP ai bersagli. Usalo solo su sistemi autorizzati.
>
> - ✅ Usa lo strumento su programmi per i quali hai il permesso di effettuare test
> - ✅ Bersagli bug bounty compresi nell'ambito autorizzato
> - ✅ Domini di tua proprietà
> - ❌ Non verificare mai bersagli senza autorizzazione
> - ❌ Non ignorare i limiti di frequenza
>
> **Le verifiche non autorizzate possono essere illegali.**

---

## 📚 Risorse

### Risorse ufficiali
- [httpx GitHub](https://github.com/projectdiscovery/httpx)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentazione](https://docs.projectdiscovery.io/tools/httpx/)

### Schede correlate
- [Subfinder](../../Subfinder/translations/README.it.md)
- [Nuclei](../../Nuclei/translations/README.it.md)
- [ffuf](../../ffuf/translations/README.it.md)

---

<p align="center">
  <b>🌐 Verifica ogni host!</b><br>
  <i>Gli strumenti HTTP essenziali per il bug bounty</i>
</p>
