# 🎯 Nuclei - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _            _      _
 | \ | |_   _  ___| | ___(_)
 |  \| | | | |/ __| |/ _ \ |
 | |\  | |_| | (__| |  __/ |
 |_| \_|\__,_|\___|_|\___|_|
                            
    Scanner rapido basato su template
```

<p align="center">
  <img src="https://img.shields.io/badge/Nuclei-Vulnerability_Scanner-red?style=for-the-badge" alt="Nuclei">
  <img src="https://img.shields.io/badge/9000+-Templates-orange?style=for-the-badge" alt="Templates">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🔥 Lo scanner di vulnerabilità basato su template di riferimento per chi pratica bug bounty</b>
</p>

> **Nota sugli esempi originali:** conteggi, percorsi dei template e opzioni dipendono dalla release; `v3.x.x` e `CVE-2023-XXXXX` sono segnaposto. `-ut` e `-update-templates` aggiornano i template, non il motore. Le opzioni JSON storiche vanno confrontate con quelle della versione installata. Nell'esempio “Verifica di un header” compaiono due chiavi YAML `matchers` nello stesso oggetto: è un errore dell'originale, da non interpretare come due condizioni combinate. Anche una risposta 200 non prova da sola l'esistenza di un pannello amministrativo. Codice e valori sono conservati; vedi la [documentazione ufficiale](https://docs.projectdiscovery.io/opensource/nuclei/running).

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Sintassi di base](#-sintassi-di-base)
- [Categorie dei template](#-categorie-dei-template)
- [Scansione dei bersagli](#-scansione-dei-bersagli)
- [Selezione dei template](#-selezione-dei-template)
- [Opzioni di output](#-opzioni-di-output)
- [Funzionalità avanzate](#-funzionalità-avanzate)
- [Template personalizzati](#-template-personalizzati)
- [Flusso di lavoro per il bug bounty](#-flusso-di-lavoro-per-il-bug-bounty)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

**Nuclei** è uno scanner rapido di vulnerabilità basato su template, sviluppato da ProjectDiscovery. È uno strumento di riferimento per chi pratica bug bounty e ricerca sulla sicurezza.

### Funzionalità principali

| Funzionalità | Descrizione |
|---------|-------------|
| **Oltre 9000 template** | CVE, pannelli, configurazioni errate e takeover |
| **Rapido ed efficiente** | Scansione parallela con limitazione della frequenza delle richieste |
| **Basato su template** | Template YAML facilmente personalizzabili |
| **Sostenuto dalla comunità** | Template aggiornati continuamente |
| **Più protocolli** | HTTP, DNS, TCP, SSL, Headless |
| **Integrazione semplice** | Si integra con altri strumenti (httpx, subfinder) |

### Tipi di template

| Tipo | Descrizione | Numero |
|------|-------------|-------|
| **CVE** | Vulnerabilità note | 2000+ |
| **Pannelli esposti** | Pannelli di amministrazione e dashboard | 500+ |
| **Configurazioni errate** | Configurazioni di sicurezza errate | 300+ |
| **Credenziali di accesso predefinite** | Credenziali predefinite | 200+ |
| **Acquisizioni di sottodomini** | Acquisizione di un sottodominio (subdomain takeover) | 100+ |
| **Tecnologie** | Rilevamento delle tecnologie | 1000+ |
| **Esposizioni di dati** | Esposizione di file sensibili | 500+ |

---

## 📥 Installazione

### Con Go (consigliato)
```bash
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest
```

### Download del binario
```bash
# Scarica dalla pagina delle release
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

### Aggiornamento dei template
```bash
# Aggiorna nuclei e i template
nuclei -ut

# Aggiorna solo i template
nuclei -update-templates
```

### Verifica dell'installazione
```bash
nuclei -version
nuclei -tl              # Elenca i template
```

---

## ⌨ Sintassi di base

### Sintassi generale
```bash
nuclei [options] -target <url>
```

### Opzioni essenziali

| Opzione | Descrizione |
|--------|-------------|
| `-u` | URL di un singolo bersaglio |
| `-l` | File con un elenco di URL |
| `-t` | Template da eseguire |
| `-tags` | Template con tag specifici |
| `-severity` | Filtra per gravità |
| `-o` | File di output |
| `-silent` | Modalità silenziosa |
| `-v` | Output dettagliato |

### Avvio rapido
```bash
# Analizza un singolo bersaglio
nuclei -u https://example.com

# Analizza un elenco di bersagli
nuclei -l urls.txt

# Analizza con template specifici
nuclei -u https://example.com -t cves/

# Analizza con i tag indicati
nuclei -u https://example.com -tags cve,rce
```

---

## 📂 Categorie dei template

### Template CVE

```bash
# Tutti i template CVE
nuclei -u target.com -t cves/

# Anno specifico delle CVE
nuclei -u target.com -t cves/2024/
nuclei -u target.com -t cves/2023/

# Solo CVE critiche
nuclei -u target.com -t cves/ -severity critical

# CVE specifica
nuclei -u target.com -t cves/2023/CVE-2023-XXXXX.yaml
```

### Pannelli esposti

```bash
# Rilevamento di tutti i pannelli
nuclei -u target.com -t exposed-panels/

# Pannelli specifici
nuclei -u target.com -tags phpmyadmin
nuclei -u target.com -tags jenkins
nuclei -u target.com -tags grafana
nuclei -u target.com -tags kibana
```

### Configurazioni errate

```bash
# Tutte le configurazioni errate
nuclei -u target.com -t misconfiguration/

# Tipi specifici
nuclei -u target.com -tags cors
nuclei -u target.com -tags headers
nuclei -u target.com -tags http-missing-security-headers
```

### Credenziali di accesso predefinite

```bash
# Tutte le credenziali predefinite
nuclei -u target.com -t default-logins/

# Produttori specifici
nuclei -u target.com -tags tomcat-default
nuclei -u target.com -tags apache-default
```

### Acquisizione di un sottodominio (subdomain takeover)

```bash
# Tutte le verifiche di takeover
nuclei -u target.com -t takeovers/

# Provider specifici
nuclei -u target.com -tags aws-takeover
nuclei -u target.com -tags azure-takeover
nuclei -u target.com -tags github-takeover
```

### Rilevamento delle tecnologie

```bash
# Rilevamento delle tecnologie
nuclei -u target.com -t technologies/

# Rilevamento dei CMS
nuclei -u target.com -tags tech,wordpress
nuclei -u target.com -tags tech,joomla
```

### Esposizioni di dati

```bash
# Esposizione di file sensibili
nuclei -u target.com -t exposures/

# Esposizioni specifiche
nuclei -u target.com -tags config
nuclei -u target.com -tags backup
nuclei -u target.com -tags logs
nuclei -u target.com -tags git
```

---

## 🎯 Scansione dei bersagli

### Bersaglio singolo

```bash
nuclei -u https://example.com
nuclei -u https://example.com:8080
```

### Bersagli multipli

```bash
# Da file
nuclei -l targets.txt

# Da stdin (pipeline)
cat targets.txt | nuclei
echo "https://example.com" | nuclei

# Più URL sulla stessa riga
nuclei -u https://site1.com -u https://site2.com
```

### Con altri strumenti

```bash
# Con subfinder (sottodomini)
subfinder -d example.com | nuclei

# Con httpx (host raggiungibili)
subfinder -d example.com | httpx | nuclei

# Con katana (URL raccolti dal crawler)
katana -u https://example.com | nuclei

# Pipeline completa di ricognizione
subfinder -d example.com -silent | httpx -silent | nuclei -silent
```

---

## 🏷 Selezione dei template

### Per percorso del template

```bash
# Singolo template
nuclei -u target.com -t cves/2023/CVE-2023-1234.yaml

# Directory dei template
nuclei -u target.com -t cves/2023/
nuclei -u target.com -t exposed-panels/

# Più directory
nuclei -u target.com -t cves/ -t exposed-panels/

# Percorso di un template personalizzato
nuclei -u target.com -t /path/to/custom/templates/
```

### Per tag

```bash
# Singolo tag
nuclei -u target.com -tags cve

# Più tag (OR)
nuclei -u target.com -tags cve,rce,sqli

# Tag comuni
nuclei -u target.com -tags xss
nuclei -u target.com -tags ssrf
nuclei -u target.com -tags lfi
nuclei -u target.com -tags rce
nuclei -u target.com -tags injection
```

### Per gravità

```bash
# Singolo livello di gravità
nuclei -u target.com -severity critical
nuclei -u target.com -severity high
nuclei -u target.com -severity medium
nuclei -u target.com -severity low
nuclei -u target.com -severity info

# Più livelli di gravità
nuclei -u target.com -severity critical,high

# Escludi livelli di gravità
nuclei -u target.com -exclude-severity info,low
```

### Esclusione dei template

```bash
# Escludi template specifici
nuclei -u target.com -exclude-templates cves/2020/

# Escludi per tag
nuclei -u target.com -exclude-tags dos,fuzz

# Escludi per ID
nuclei -u target.com -exclude-id CVE-2021-1234
```

---

## 📤 Opzioni di output

### Formati di output

```bash
# Output standard
nuclei -u target.com -o results.txt

# Output JSON
nuclei -u target.com -json -o results.json

# JSON Lines
nuclei -u target.com -jsonl -o results.jsonl

# Markdown
nuclei -u target.com -markdown-export report/
```

### Filtraggio dell'output

```bash
# Modalità silenziosa (solo risultati)
nuclei -u target.com -silent

# Senza colori
nuclei -u target.com -nc

# Output dettagliato
nuclei -u target.com -v

# Debug
nuclei -u target.com -debug
```

### Opzioni di esportazione

```bash
# SARIF (GitHub)
nuclei -u target.com -sarif-export results.sarif

# Report Markdown
nuclei -u target.com -me reports/
```

---

## ⚡ Funzionalità avanzate

### Limitazione della frequenza delle richieste

```bash
# Richieste al secondo
nuclei -u target.com -rate-limit 100

# Dimensione del gruppo di bersagli
nuclei -u target.com -bulk-size 25

# Concorrenza
nuclei -u target.com -c 50

# Concorrenza dei template
nuclei -u target.com -rl 100 -c 50
```

### Supporto proxy

```bash
# Proxy HTTP
nuclei -u target.com -proxy http://127.0.0.1:8080

# Proxy SOCKS
nuclei -u target.com -proxy socks5://127.0.0.1:9050

# Proxy Burp Suite
nuclei -u target.com -proxy http://127.0.0.1:8080
```

### Autenticazione

```bash
# Autenticazione tramite header
nuclei -u target.com -H "Authorization: Bearer token123"

# Autenticazione tramite cookie
nuclei -u target.com -H "Cookie: session=abc123"

# Più header
nuclei -u target.com -H "Authorization: Bearer token" -H "X-Custom: value"
```

### Integrazione con Interactsh

```bash
# Usa Interactsh per verifiche fuori banda (OOB)
nuclei -u target.com -interactsh-server https://interact.sh

# Server Interactsh personalizzato
nuclei -u target.com -iserver https://my-interactsh.com
```

### Modalità headless (browser senza interfaccia grafica)

```bash
# Abilita il browser headless
nuclei -u target.com -headless

# Solo template headless
nuclei -u target.com -t headless/
```

### Flussi di lavoro

```bash
# Esegui il flusso di lavoro
nuclei -u target.com -w workflows/wordpress-workflow.yaml

# Elenca i flussi di lavoro
ls ~/.local/nuclei-templates/workflows/
```

---

## 📝 Template personalizzati

### Struttura del template

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

### Esempi di template

#### Rilevamento di una pagina specifica
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

#### Verifica di un header
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

### Validazione del template

```bash
# Valida la sintassi del template
nuclei -validate -t my-template.yaml

# Prova il template
nuclei -u target.com -t my-template.yaml -debug
```

---

## 🔄 Flusso di lavoro per il bug bounty

### Pipeline completa di ricognizione

```bash
# Passaggio 1: enumerazione dei sottodomini
subfinder -d target.com -o subs.txt

# Passaggio 2: individua gli host raggiungibili
cat subs.txt | httpx -o live.txt

# Passaggio 3: esegui nuclei
nuclei -l live.txt -severity critical,high -o findings.txt

# Comando su una sola riga
subfinder -d target.com -silent | httpx -silent | nuclei -severity critical,high
```

### Scansione per priorità

```bash
# Fase 1: solo criticità (rapida)
nuclei -l targets.txt -severity critical -o critical.txt

# Fase 2: gravità alta
nuclei -l targets.txt -severity high -o high.txt

# Fase 3: CVE
nuclei -l targets.txt -t cves/ -o cves.txt

# Fase 4: scansione completa
nuclei -l targets.txt -o all.txt
```

### Scansione mirata

```bash
# Bersagli WordPress
nuclei -l wp-sites.txt -tags wordpress

# Endpoint API
nuclei -l apis.txt -tags api

# Pagine di accesso
nuclei -l logins.txt -tags login,default-login
```

---

## 🎬 Esempi pratici

### Esempio 1: scansione completa
```bash
nuclei -u https://target.com -o results.txt
```

### Esempio 2: solo CVE critiche
```bash
nuclei -u https://target.com -t cves/ -severity critical,high
```

### Esempio 3: scansione rapida per bug bounty
```bash
nuclei -u https://target.com -tags cve,rce,sqli,xss,ssrf -severity critical,high
```

### Esempio 4: verifica del takeover di sottodomini
```bash
cat subs.txt | nuclei -t takeovers/
```

### Esempio 5: rilevamento delle tecnologie
```bash
nuclei -u https://target.com -t technologies/ -silent
```

### Esempio 6: con proxy (Burp)
```bash
nuclei -u https://target.com -proxy http://127.0.0.1:8080
```

### Esempio 7: scansione autenticata
```bash
nuclei -u https://target.com -H "Authorization: Bearer eyJhbG..."
```

### Esempio 8: scansione con frequenza limitata
```bash
nuclei -l targets.txt -rate-limit 10 -c 5
```

### Esempio 9: output JSON da elaborare
```bash
nuclei -u https://target.com -json -o results.json
```

### Esempio 10: pipeline completa
```bash
subfinder -d target.com -silent | \
httpx -silent | \
nuclei -severity critical,high -json -o results.json
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Attività | Comando |
|------|---------|
| Scansione di base | `nuclei -u target.com` |
| Con template | `nuclei -u target.com -t cves/` |
| Per tag | `nuclei -u target.com -tags cve,rce` |
| Per gravità | `nuclei -u target.com -severity critical` |
| Da elenco | `nuclei -l urls.txt` |
| Aggiorna i template | `nuclei -ut` |
| Output silenzioso | `nuclei -u target.com -silent` |
| Output JSON | `nuclei -u target.com -json -o out.json` |

### Tag comuni

| Tag | Descrizione |
|-----|-------------|
| `cve` | Vulnerabilità CVE |
| `rce` | Esecuzione di codice remoto |
| `sqli` | SQL injection |
| `xss` | Cross-site scripting |
| `ssrf` | Falsificazione di richieste lato server (SSRF) |
| `lfi` | Inclusione di file locali (LFI) |
| `panel` | Pannelli di amministrazione |
| `takeover` | Acquisizione di un sottodominio (subdomain takeover) |

### Livelli di gravità

| Gravità | Colore | Priorità |
|----------|-------|----------|
| `critical` | 🔴 Rosso | Immediata |
| `high` | 🟠 Arancione | High (alta) |
| `medium` | 🟡 Giallo | Medium (media) |
| `low` | 🟢 Verde | Low (bassa) |
| `info` | 🔵 Blu | Informativa |

---

## 💡 Consigli e buone pratiche

### Consigli per le prestazioni

```bash
# Impostazioni ottimali per scansioni estese
nuclei -l targets.txt -c 50 -rl 150 -bs 25

# Modalità silenziosa per le pipeline
nuclei -l targets.txt -silent -o results.txt
```

### Consigli per il bug bounty

1. **Inizia dai livelli critical/high**
   ```bash
   nuclei -l targets.txt -severity critical,high
   ```

2. **Cerca risultati rapidi**
   ```bash
   nuclei -l targets.txt -tags takeover,exposed-panels
   ```

3. **Usa template aggiornati**
   ```bash
   nuclei -ut  # Aggiorna sempre prima della ricerca
   ```

4. **Limita la frequenza per mantenere la stabilità**
   ```bash
   nuclei -l targets.txt -rl 50
   ```

### Consigli per ridurre la visibilità

```bash
# Lento e silenzioso
nuclei -l targets.txt -rl 10 -c 2

# Tramite proxy
nuclei -l targets.txt -proxy socks5://127.0.0.1:9050
```

---

## ⚠ Avvertenza legale

> **ATTENZIONE:** Nuclei è un potente scanner di vulnerabilità da usare solo per **test autorizzati**.
>
> - ✅ Usa lo strumento su programmi per i quali hai il permesso di effettuare test
> - ✅ Usa lo strumento nei programmi bug bounty autorizzati
> - ✅ Usa lo strumento sui tuoi sistemi
> - ❌ Non eseguire mai scansioni senza autorizzazione
> - ❌ Non ignorare i limiti di frequenza e l'ambito autorizzato
>
> **Le scansioni non autorizzate sono illegali e contrarie all'etica.**

---

## 📚 Risorse

### Risorse ufficiali
- [Nuclei GitHub](https://github.com/projectdiscovery/nuclei)
- [Nuclei Templates](https://github.com/projectdiscovery/nuclei-templates)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentazione](https://docs.projectdiscovery.io/tools/nuclei/)

### Risorse sui template
- [Template della comunità](https://github.com/projectdiscovery/nuclei-templates)
- [Guida ai template](https://docs.projectdiscovery.io/templates/)

### Schede correlate
- [Nmap](../../Nmap/translations/README.it.md)
- [Burp Suite](../../Burp-Suite/translations/README.it.md)
- [SQLMap](../../SQLMap/translations/README.it.md)

---

<p align="center">
  <b>🎯 Padroneggia la scansione delle vulnerabilità!</b><br>
  <i>Un alleato per chi pratica bug bounty</i>
</p>
