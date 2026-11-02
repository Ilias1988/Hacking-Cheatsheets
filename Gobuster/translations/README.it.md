# 🚀 Gobuster - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗  ██████╗ ██████╗ ██╗   ██╗███████╗████████╗███████╗██████╗ 
  ██╔════╝ ██╔═══██╗██╔══██╗██║   ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗
  ██║  ███╗██║   ██║██████╔╝██║   ██║███████╗   ██║   █████╗  ██████╔╝
  ██║   ██║██║   ██║██╔══██╗██║   ██║╚════██║   ██║   ██╔══╝  ██╔══██╗
  ╚██████╔╝╚██████╔╝██████╔╝╚██████╔╝███████║   ██║   ███████╗██║  ██║
   ╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
                Strumento brute force per directory, DNS e host virtuali
```

<p align="center">
  <img src="https://img.shields.io/badge/Gobuster-Enumeration-blue?style=for-the-badge" alt="Gobuster">
  <img src="https://img.shields.io/badge/Directory-Bruteforce-red?style=for-the-badge" alt="Directory">
  <img src="https://img.shields.io/badge/DNS-Subdomain-green?style=for-the-badge" alt="DNS">
  <img src="https://img.shields.io/badge/VHost-Discovery-purple?style=for-the-badge" alt="VHost">
</p>

<p align="center">
  <b>🔍 Strumento veloce in Go per enumerare directory, DNS e host virtuali</b>
</p>

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Panoramica delle modalità](#-panoramica-delle-modalità)
- [Modalità directory (dir)](#-modalità-directory-dir)
- [Modalità DNS (dns)](#-modalità-dns-dns)
- [Modalità host virtuali (vhost)](#-modalità-host-virtuali-vhost)
- [Modalità fuzzing (fuzz)](#-modalità-fuzzing-fuzz)
- [Wordlist](#-wordlist)
- [Autenticazione e header](#-autenticazione-e-header)
- [Regolazione delle prestazioni](#-regolazione-delle-prestazioni)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

> **Nota sulle versioni:** l’originale combina sintassi di release diverse. Da Gobuster 3.7 la CLI è cambiata, il flag `verbose` è stato sostituito da `debug` e alcune opzioni DNS sono state rinominate. Controlla l’aiuto della versione installata; i comandi originali restano invariati. Le stelle del confronto sono valutazioni qualitative, non un benchmark. Vedi il [changelog ufficiale](https://github.com/OJ/gobuster#changes).

**Gobuster** è uno strumento veloce in Go che esegue tentativi sistematici (brute force) con più thread per:
- Enumerazione di directory e file sui server web
- Individuazione dei sottodomini DNS
- Individuazione degli host virtuali (VHost)
- Fuzzing degli URL

### Perché Gobuster?

| Funzionalità | Descrizione |
|---------|-------------|
| **Veloce** | Scritto in Go, con elevata concorrenza |
| **Flessibile** | Più modalità per attività diverse |
| **Senza ricorsione** | Risultati chiari senza rumore |
| **Stabile** | Gestisce correttamente gli errori |
| **Sviluppo attivo** | Aggiornamenti regolari |

### Confronto con altri strumenti

| Strumento | Velocità | Ricorsione | Linguaggio |
|------|-------|-----------|----------|
| **Gobuster** | ⭐⭐⭐⭐⭐ | No | Go |
| Dirb | ⭐⭐ | Sì | C |
| Dirbuster | ⭐⭐⭐ | Sì | Java |
| Ffuf | ⭐⭐⭐⭐⭐ | No | Go |
| Feroxbuster | ⭐⭐⭐⭐⭐ | Sì | Rust |

---

## 📥 Installazione

### Kali Linux (preinstallato)
```bash
# Aggiorna Gobuster
sudo apt update && sudo apt install gobuster
```

### Ubuntu/Debian
```bash
sudo apt install gobuster
```

### Dal codice sorgente (Go)
```bash
go install github.com/OJ/gobuster/v3@latest
```

### Con Snap
```bash
sudo snap install gobuster
```

### Dalle release di GitHub
```bash
# Scarica la release più recente
wget https://github.com/OJ/gobuster/releases/latest/download/gobuster-linux-amd64.7z
7z x gobuster-linux-amd64.7z
chmod +x gobuster
sudo mv gobuster /usr/local/bin/
```

### Verifica dell'installazione
```bash
gobuster version
gobuster -h
```

---

## 📊 Panoramica delle modalità

### Modalità disponibili

| Modalità | Descrizione | Caso d'uso |
|------|-------------|----------|
| `dir` | Enumerazione di directory e file | Individua directory e file nascosti |
| `dns` | Enumerazione dei sottodomini DNS | Individua i sottodomini |
| `vhost` | Enumerazione degli host virtuali | Individua gli host virtuali |
| `fuzz` | Fuzzing degli URL | Esegue fuzzing di parametri e percorsi |
| `s3` | Enumerazione dei bucket S3 | Individua i bucket AWS S3 |
| `gcs` | Google Cloud Storage | Individua i bucket GCS |
| `tftp` | Enumerazione TFTP | Individua i file TFTP |

### Sintassi di base
```bash
gobuster [mode] [options]

# Esempi
gobuster dir -u http://target.com -w wordlist.txt
gobuster dns -d target.com -w subdomains.txt
gobuster vhost -u http://target.com -w vhosts.txt
```

---

## 📁 Modalità directory (dir)

### Sintassi di base
```bash
gobuster dir -u <URL> -w <wordlist>
```

### Opzioni essenziali

| Opzione | Descrizione |
|--------|-------------|
| `-u` | URL del bersaglio |
| `-w` | Percorso della wordlist |
| `-t` | Thread (predefinito: 10) |
| `-x` | Estensioni dei file da cercare |
| `-s` | Codici di stato da includere |
| `-b` | Codici di stato da escludere (blacklist) |
| `-o` | File di output |
| `-k` | Salta la verifica SSL |
| `-r` | Segue i reindirizzamenti |
| `-a` | Stringa User-Agent |
| `-c` | Cookie |
| `-H` | Header personalizzati |

### Scansione di base delle directory
```bash
# Scansione semplice delle directory
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt

# Con più thread
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt -t 50
```

### Ricerca per estensione dei file
```bash
# Cerca estensioni specifiche
gobuster dir -u http://target.com -w wordlist.txt -x php,html,txt

# Più estensioni
gobuster dir -u http://target.com -w wordlist.txt -x php,asp,aspx,jsp,html,txt,bak,old

# File di backup
gobuster dir -u http://target.com -w wordlist.txt -x bak,backup,old,orig,~
```

### Filtro dei codici di stato

> **Correzione dell’originale:** `--no-status` nasconde i codici di stato nell’output; non include automaticamente le risposte 404. Inoltre, `-s` può entrare in conflitto con la blacklist predefinita. Vedi le [opzioni ufficiali della modalità dir](https://github.com/OJ/gobuster/blob/master/cli/dir/dir.go).
```bash
# Mostra solo codici di stato specifici
gobuster dir -u http://target.com -w wordlist.txt -s 200,204,301,302,307,401

# Escludi codici di stato
gobuster dir -u http://target.com -w wordlist.txt -b 404,403

# Mostra tutto, inclusi i 404 (etichetta inesatta nell'originale: vedi nota)
gobuster dir -u http://target.com -w wordlist.txt --no-status
```

### Opzioni di output
```bash
# Salva su file
gobuster dir -u http://target.com -w wordlist.txt -o results.txt

# Output dettagliato
gobuster dir -u http://target.com -w wordlist.txt -v

# Senza indicatore di avanzamento
gobuster dir -u http://target.com -w wordlist.txt -q

# Modalità estesa (URL completi)
gobuster dir -u http://target.com -w wordlist.txt -e
```

### Opzioni SSL/TLS

> **Nota sulle opzioni:** nelle versioni recenti `-k` è l’alias di `--no-tls-validation`, quindi usarli insieme è ridondante; il nome `--insecuressl` nella tabella finale appartiene a sintassi precedente. `-q` indica la modalità silenziosa, distinta dall’opzione che disattiva l’avanzamento. Vedi le [definizioni ufficiali delle opzioni](https://github.com/OJ/gobuster/blob/master/cli/options.go).
```bash
# Salta la verifica SSL
gobuster dir -u https://target.com -w wordlist.txt -k

# Salta la verifica SSL con opzioni aggiuntive
gobuster dir -u https://target.com -w wordlist.txt -k --no-tls-validation
```

### Esempi della modalità directory
```bash
# Scansione standard delle directory web
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt -t 50

# Applicazione PHP
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x php -t 50

# Individuazione degli endpoint API
gobuster dir -u http://target.com/api -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt -t 30

# Individua i file di backup
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x bak,backup,old,zip,tar.gz -t 50
```

---

## 🌐 Modalità DNS (dns)

### Sintassi di base
```bash
gobuster dns -d <domain> -w <wordlist>
```

### Opzioni DNS

| Opzione | Descrizione |
|--------|-------------|
| `-d` | Dominio bersaglio |
| `-w` | Percorso della wordlist |
| `-t` | Thread |
| `-r` | Resolver DNS personalizzato |
| `-i` | Mostra gli indirizzi IP |
| `-c` | Mostra i record CNAME |
| `--wildcard` | Forza l'elaborazione dei caratteri jolly |
| `--timeout` | Timeout DNS |

### Enumerazione DNS di base
```bash
# Scansione semplice dei sottodomini
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# Mostra gli IP
gobuster dns -d target.com -w subdomains.txt -i

# Mostra i CNAME
gobuster dns -d target.com -w subdomains.txt -c
```

### Resolver DNS personalizzato
```bash
# Usa un server DNS specifico
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8

# Più resolver (l'esempio indica un solo resolver con porta)
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8:53
```

### Esempi DNS
```bash
# Scansione approfondita dei sottodomini
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt -t 50 -i

# Scansione rapida dei sottodomini
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt -t 100

# Con output
gobuster dns -d target.com -w subdomains.txt -i -o subdomains_found.txt
```

---

## 🏠 Modalità host virtuali (vhost)

### Sintassi di base
```bash
gobuster vhost -u <URL> -w <wordlist>
```

### Opzioni VHost

| Opzione | Descrizione |
|--------|-------------|
| `-u` | URL del bersaglio |
| `-w` | Percorso della wordlist |
| `-t` | Thread |
| `--append-domain` | Aggiunge il dominio alle voci della wordlist |
| `--domain` | Dominio per l'host virtuale |
| `-r` | Segue i reindirizzamenti |

### Enumerazione di base degli host virtuali
```bash
# Scansione semplice degli host virtuali
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# Con aggiunta del dominio
gobuster vhost -u http://target.com -w subdomains.txt --append-domain
```

### Esempi VHost
```bash
# Individuazione standard degli host virtuali
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/namelist.txt -t 50

# Host di sviluppo
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt --append-domain

# Con dominio personalizzato
gobuster vhost -u http://10.10.10.10 -w subdomains.txt --domain target.htb
```

---

## 🔧 Modalità fuzzing (fuzz)

### Sintassi di base
```bash
gobuster fuzz -u <URL with FUZZ> -w <wordlist>
```

### Opzioni di fuzzing

| Opzione | Descrizione |
|--------|-------------|
| `-u` | URL con la parola chiave FUZZ |
| `-w` | Percorso della wordlist |
| `-t` | Thread |
| `-b` | Codici di stato da escludere |
| `--exclude-length` | Esclude in base alla lunghezza della risposta |

### Esempi di fuzzing
```bash
# Esegue fuzzing del valore del parametro
gobuster fuzz -u "http://target.com/page?id=FUZZ" -w numbers.txt

# Esegue fuzzing del percorso
gobuster fuzz -u "http://target.com/FUZZ/admin" -w wordlist.txt

# Escludi lunghezze specifiche
gobuster fuzz -u "http://target.com/FUZZ" -w wordlist.txt --exclude-length 0,42
```

---

## 📚 Wordlist

### Percorsi di SecLists (Kali Linux)

```bash
# Enumerazione di directory e file
/usr/share/seclists/Discovery/Web-Content/

# Elenchi di directory più usati
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-small.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# DNS/sottodomini
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

### Wordlist predefinite

```bash
# Wordlist di Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
/usr/share/wordlists/dirb/small.txt

# Wordlist di Dirbuster
/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
/usr/share/wordlists/dirbuster/directory-list-2.3-small.txt
```

### Wordlist consigliate

| Caso d'uso | Wordlist |
|----------|----------|
| Scansione rapida | `common.txt` |
| Standard | `directory-list-2.3-medium.txt` |
| Approfondita | `directory-list-2.3-big.txt` |
| API | `api-endpoints.txt` |
| Sottodomini | `subdomains-top1million-5000.txt` |
| File | `raft-large-files.txt` |

---

## 🔐 Autenticazione e header

### Autenticazione Basic
```bash
# Autenticazione HTTP Basic
gobuster dir -u http://target.com -w wordlist.txt -U username -P password
```

### Autenticazione tramite cookie
```bash
# Cookie singolo
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123"

# Più cookie
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123; token=xyz789"

# Sessione PHP
gobuster dir -u http://target.com -w wordlist.txt -c "PHPSESSID=abc123def456"
```

### Header personalizzati
```bash
# Header singolo
gobuster dir -u http://target.com -w wordlist.txt -H "Authorization: Bearer token123"

# Più header
gobuster dir -u http://target.com -w wordlist.txt -H "X-Forwarded-For: 127.0.0.1" -H "X-Custom: value"

# Chiave API
gobuster dir -u http://target.com/api -w wordlist.txt -H "X-API-Key: your-api-key"
```

### User-Agent
```bash
# User-Agent personalizzato
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Googlebot
gobuster dir -u http://target.com -w wordlist.txt -a "Googlebot/2.1"
```

### Supporto proxy
```bash
# Proxy HTTP
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080

# Proxy SOCKS5
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050

# Proxy Burp Suite
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080 -k
```

---

## ⚡ Regolazione delle prestazioni

### Gestione dei thread
```bash
# Predefinito: 10 thread
gobuster dir -u http://target.com -w wordlist.txt

# Aumenta i thread (rete veloce)
gobuster dir -u http://target.com -w wordlist.txt -t 100

# Riduci i thread (rete lenta o instabile)
gobuster dir -u http://target.com -w wordlist.txt -t 5
```

### Impostazioni dei timeout
```bash
# Aumenta il timeout
gobuster dir -u http://target.com -w wordlist.txt --timeout 30s

# Timeout DNS
gobuster dns -d target.com -w subdomains.txt --timeout 5s
```

### Ritardo tra le richieste
```bash
# Aggiungi un ritardo (limitazione della frequenza)
gobuster dir -u http://target.com -w wordlist.txt --delay 100ms

# Rallenta per l'elusione del WAF
gobuster dir -u http://target.com -w wordlist.txt --delay 500ms
```

---

## 🎬 Esempi pratici

### Esempio 1: enumerazione web standard
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt \
    -t 50 \
    -x php,html,txt \
    -o results.txt
```

### Esempio 2: individuazione degli endpoint API
```bash
gobuster dir -u http://target.com/api/v1 \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -t 30 \
    -H "Content-Type: application/json"
```

### Esempio 3: scansione autenticata
```bash
gobuster dir -u http://target.com/admin \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -c "PHPSESSID=abc123" \
    -t 20
```

### Esempio 4: enumerazione dei sottodomini
```bash
gobuster dns -d target.com \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt \
    -t 100 \
    -i \
    -o subdomains.txt
```

### Esempio 5: individuazione di host virtuali (HackTheBox)
```bash
gobuster vhost -u http://10.10.10.10 \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    --domain target.htb \
    --append-domain
```

### Esempio 6: tramite il proxy Burp
```bash
gobuster dir -u https://target.com \
    -w /usr/share/wordlists/dirb/common.txt \
    --proxy http://127.0.0.1:8080 \
    -k \
    -t 20
```

### Esempio 7: ricerca dei file di backup
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -x bak,backup,old,orig,save,swp,txt,zip,tar,gz,sql \
    -t 50
```

### Esempio 8: rilevamento del CMS
```bash
# WordPress
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/wordpress.fuzz.txt \
    -t 50

# Joomla
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/joomla-plugins.fuzz.txt
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Attività | Comando |
|------|---------|
| Scansione di base delle directory | `gobuster dir -u URL -w wordlist.txt` |
| Con estensioni | `gobuster dir -u URL -w wordlist.txt -x php,html` |
| Scansione dei sottodomini | `gobuster dns -d domain.com -w wordlist.txt` |
| Scansione degli host virtuali | `gobuster vhost -u URL -w wordlist.txt` |
| Con output | `gobuster dir -u URL -w wordlist.txt -o output.txt` |
| Ignora gli errori SSL | `gobuster dir -u URL -w wordlist.txt -k` |
| Con cookie | `gobuster dir -u URL -w wordlist.txt -c "session=x"` |
| Tramite proxy | `gobuster dir -u URL -w wordlist.txt --proxy http://127.0.0.1:8080` |

### Opzioni comuni

| Opzione | Forma breve | Descrizione |
|--------|-------|-------------|
| `--url` | `-u` | URL del bersaglio |
| `--wordlist` | `-w` | File della wordlist |
| `--threads` | `-t` | Numero di thread |
| `--extensions` | `-x` | Estensioni dei file |
| `--status-codes` | `-s` | Codici di stato da includere |
| `--cookies` | `-c` | Cookie da inviare |
| `--output` | `-o` | File di output |
| `--insecuressl` | `-k` | Salta la verifica SSL |
| `--verbose` | `-v` | Output dettagliato |
| `--quiet` | `-q` | Modalità silenziosa |

---

## 💡 Consigli e buone pratiche

### Consigli per l'efficienza

1. **Inizia con liste piccole, poi amplia**
   ```bash
   # Prima una scansione rapida
   gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt
   
   # Poi una wordlist più grande
   gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
   ```

2. **Scegli estensioni adatte al bersaglio**
   ```bash
   # Sito PHP
   gobuster dir -u http://target.com -w wordlist.txt -x php
   
   # Sito ASP.NET
   gobuster dir -u http://target.com -w wordlist.txt -x asp,aspx
   ```

3. **Combina con altri strumenti**
   ```bash
   # Flusso di lavoro Nmap → Gobuster
   nmap -sV -p 80,443 target.com
   gobuster dir -u http://target.com -w wordlist.txt
   ```

### Riduzione della rilevabilità

```bash
# Scansione lenta con ritardo
gobuster dir -u http://target.com -w wordlist.txt -t 5 --delay 200ms

# User-Agent personalizzato
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0..."

# Tramite Tor
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050
```

---

## ⚠ Avvertenza legale

> **ATTENZIONE:** Gobuster è uno strumento potente di enumerazione da usare esclusivamente per **test autorizzati**.
> 
> - ✅ Esegui test sui sistemi di tua proprietà
> - ✅ Esegui test con esplicito permesso scritto
> - ✅ Usalo nelle sfide CTF e nei laboratori
> - ❌ Non eseguire mai scansioni senza autorizzazione
> - ❌ L'enumerazione non autorizzata può essere illegale
> 
> **Ottieni sempre il permesso prima di eseguire scansioni su un sistema.**

---

## 📚 Risorse

### Risorse ufficiali
- [Gobuster GitHub](https://github.com/OJ/gobuster)
- [Gobuster Wiki](https://github.com/OJ/gobuster/wiki)

### Wordlist
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)

### Schede correlate
- [Nmap](../../Nmap/translations/README.it.md)
- [Burp Suite](../../Burp-Suite/translations/README.it.md)
- [SQLMap](../../SQLMap/README.md)

---

<p align="center">
  <b>🚀 Padroneggia l'enumerazione web!</b><br>
  <i>Scopri ciò che gli altri non vedono</i>
</p>
