# ⚔ Katana - Crawler web di nuova generazione

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗  ██╗ █████╗ ████████╗ █████╗ ███╗   ██╗ █████╗
  ██║ ██╔╝██╔══██╗╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗
  █████╔╝ ███████║   ██║   ███████║██╔██╗ ██║███████║
  ██╔═██╗ ██╔══██║   ██║   ██╔══██║██║╚██╗██║██╔══██║
  ██║  ██╗██║  ██║   ██║   ██║  ██║██║ ╚████║██║  ██║
  ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝
         Framework di crawling web di nuova generazione
```

<p align="center">
  <img src="https://img.shields.io/badge/Katana-ProjectDiscovery-blue?style=for-the-badge" alt="Katana">
  <img src="https://img.shields.io/badge/Web_Crawler-green?style=for-the-badge" alt="Crawler">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Correzioni dell'originale:** `-cs` è il filtro regex dell'ambito di crawling; la strategia di visita usa un'opzione distinta, quindi gli esempi con `-cs depth-first` o `breadth-first` sono inesatti. `-system-chrome` seleziona Chrome installato, non un percorso personalizzato; `-timeout` misura secondi, non un'attesa JavaScript in millisecondi. La profondità 1 non implica necessariamente la sola pagina iniziale. Alcune opzioni mostrate per screenshot, campi, stato HTTP e valori dei moduli dipendono dalla versione o non figurano nell'elenco consultato. I comandi restano identici: confrontali con la [guida ufficiale](https://docs.projectdiscovery.io/opensource/katana/usage).

---

## 📋 Indice

- [Che cos'è Katana](#-che-cosè-katana)
- [Installazione](#-installazione)
- [Utilizzo di base](#-utilizzo-di-base)
- [Opzioni di crawling](#-opzioni-di-crawling)
- [Modalità headless (browser senza interfaccia grafica)](#-modalità-headless-browser-senza-interfaccia-grafica)
- [Output e filtraggio](#-output-e-filtraggio)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è Katana

**Katana** è un framework di nuova generazione di ProjectDiscovery per l'esplorazione automatica del web (crawling):

- ⚡ **Rapido** - Basato su Go, con elevata concorrenza
- 🔍 **Rendering JavaScript** - Supporto per browser headless
- 📡 **Web moderno** - Gestisce SPA e contenuti dinamici
- 🎯 **Orientato al bug bounty** - Creato per i test di sicurezza

### Perché Katana?

| Funzionalità | Vantaggio |
|---------|---------|
| Headless Chrome | Esplora app che usano molto JavaScript |
| Header personalizzati | Crawling autenticato |
| Controllo dell'ambito | Rimani nell'ambito autorizzato |
| Compilazione dei moduli | Individua più endpoint |

---

## 🚀 Installazione

```bash
# Installazione con Go (consigliata)
go install github.com/projectdiscovery/katana/cmd/katana@latest

# Docker
docker pull projectdiscovery/katana

# Verifica
katana -version
```

---

## 📖 Utilizzo di base

### Bersaglio singolo

```bash
# Crawling di base
katana -u https://example.com

# Salva l'output
katana -u https://example.com -o urls.txt

# Modalità silenziosa
katana -u https://example.com -silent
```

### Bersagli multipli

```bash
# Da stdin
echo "https://example.com" | katana

# Più URL
cat targets.txt | katana

# Da file
katana -list targets.txt
```

### Profondità di crawling

```bash
# Imposta la profondità (predefinita: 3)
katana -u https://example.com -d 5

# Profondità 1 (solo la pagina bersaglio)
katana -u https://example.com -d 1
```

---

## 🕷 Opzioni di crawling

### Controllo dell'ambito

```bash
# Esplora solo l'ambito autorizzato (stesso dominio)
katana -u https://example.com -fs dn

# Opzioni dell'ambito:
# dn - nome del dominio
# rdn - dominio radice
# fqdn - nome di dominio completo

# Campo dell'ambito
katana -u https://example.com -fs rdn
```

### Strategia di crawling

```bash
# Prima in profondità (predefinita)
katana -u https://example.com -cs depth-first

# Prima in ampiezza
katana -u https://example.com -cs breadth-first
```

### Concorrenza

```bash
# Richieste concorrenti (predefinite: 10)
katana -u https://example.com -c 50

# Limite di frequenza (richieste al secondo)
katana -u https://example.com -rl 100

# Parallelismo
katana -u https://example.com -p 10
```

### Durata del crawling

```bash
# Imposta il timeout (secondi)
katana -u https://example.com -timeout 30

# Tempo massimo di crawling
katana -u https://example.com -ct 300
```

### Compilazione automatica dei moduli

```bash
# Abilita la compilazione dei moduli
katana -u https://example.com -aff

# Con valori personalizzati
katana -u https://example.com -aff -fv "username:admin,password:test123"
```

---

## 🌐 Modalità headless (browser senza interfaccia grafica)

### Abilitazione del browser headless

```bash
# Abilita Chrome headless
katana -u https://example.com -headless

# Modalità headless con profondità
katana -u https://example.com -headless -d 3
```

### Opzioni headless

```bash
# Percorso personalizzato di Chrome
katana -u https://example.com -headless -system-chrome

# Senza sandbox (per root)
katana -u https://example.com -headless -no-sandbox

# Acquisisci screenshot delle pagine
katana -u https://example.com -headless -screenshot
```

### Crawling JavaScript

```bash
# Attendi il caricamento di JS (ms)
katana -u https://example.com -headless -timeout 5

# Esplora gli endpoint JS
katana -u https://example.com -js-crawl
```

---

## 📤 Output e filtraggio

### Formati di output

```bash
# Output standard su file
katana -u https://example.com -o results.txt

# Output JSON
katana -u https://example.com -jsonl -o results.json

# Senza colori
katana -u https://example.com -nc
```

### Campi da visualizzare

```bash
# Mostra campi specifici
katana -u https://example.com -f url,method,status

# Campi disponibili:
# url, path, fqdn, rdn, rurl, qurl, qpath
# file, kv, dir, ufile, udir
# method, status, body, response, header
```

### Filtraggio per estensione

```bash
# Seleziona estensioni
katana -u https://example.com -em js,php,asp

# Escludi estensioni
katana -u https://example.com -ef png,jpg,gif,css,svg,woff
```

### Filtraggio per codice di stato

```bash
# Seleziona il codice di stato
katana -u https://example.com -mc 200,301,302

# Escludi il codice di stato
katana -u https://example.com -fc 404,500
```

### Header personalizzati

```bash
# Aggiungi un header
katana -u https://example.com -H "Authorization: Bearer TOKEN"

# Più header
katana -u https://example.com -H "Cookie: session=abc" -H "User-Agent: Custom"
```

### Supporto proxy

```bash
# Proxy HTTP
katana -u https://example.com -proxy http://127.0.0.1:8080

# Per Burp Suite
katana -u https://example.com -proxy http://127.0.0.1:8080
```

---

## 📊 Riferimento rapido

### Comandi di base

| Comando | Descrizione |
|---------|-------------|
| `katana -u URL` | Esplora un singolo URL |
| `katana -list file.txt` | Esplora gli URL da file |
| `katana -u URL -d 5` | Imposta la profondità a 5 |
| `katana -u URL -headless` | Abilita la modalità headless |
| `katana -u URL -o out.txt` | Salva i risultati |

### Opzioni dell'ambito

| Opzione | Descrizione |
|------|-------------|
| `-fs dn` | Stesso dominio |
| `-fs rdn` | Dominio radice |
| `-fs fqdn` | Dominio completo |
| `-cs depth-first` | Crawling prima in profondità |
| `-cs breadth-first` | Crawling prima in ampiezza |

### Opzioni delle prestazioni

| Opzione | Descrizione |
|------|-------------|
| `-c 50` | Concorrenza |
| `-p 10` | Parallelismo |
| `-rl 100` | Limite di frequenza |
| `-timeout 30` | Timeout della richiesta |

### Opzioni di filtraggio

| Opzione | Descrizione |
|------|-------------|
| `-em js,php` | Seleziona estensioni |
| `-ef png,jpg` | Escludi estensioni |
| `-mc 200` | Seleziona codici di stato |
| `-fc 404` | Escludi codici di stato |

---

## 🎯 Flussi di lavoro per il bug bounty

### Crawling di base

```bash
# Crawling rapido
katana -u https://target.com -d 3 -o urls.txt
```

### Crawling completo

```bash
# Crawling approfondito con JS
katana -u https://target.com -d 5 -headless -c 50 \
  -ef png,jpg,gif,css,svg,woff,woff2,ttf,eot,ico \
  -o all_urls.txt
```

### Individuazione dei parametri

```bash
# Trova URL con parametri
katana -u https://target.com -d 3 | grep "?" | sort -u

# Con grep per parametri specifici
katana -u https://target.com | grep -E "id=|user=|admin=|file="
```

### Insieme ad altri strumenti

```bash
# Katana + httpx
katana -u https://target.com -d 3 | httpx -silent -sc

# Katana + Nuclei
katana -u https://target.com -d 3 | nuclei -t nuclei-templates/

# Pipeline completa
subfinder -d target.com | httpx -silent | katana -d 3 | grep "=" | sort -u
```

### Crawling autenticato

```bash
# Con cookie di sessione
katana -u https://target.com \
  -H "Cookie: session=abc123" \
  -d 5 \
  -headless \
  -o authenticated_urls.txt
```

---

## 📚 Risorse

- [Katana GitHub](https://github.com/projectdiscovery/katana)
- [ProjectDiscovery](https://projectdiscovery.io/)

### Schede correlate
- [Metodologia per il bug bounty](../../Bug-Bounty-Methodology/translations/README.it.md)
- [httpx](../../httpx/translations/README.it.md)
- [Nuclei](../../Nuclei/translations/README.it.md)
- [GAU](../../GAU/translations/README.it.md)

---

<p align="center">
  <b>⚔️ Esplora ogni pagina!</b><br>
  <i>Katana - Crawler web di nuova generazione</i>
</p>
