# 🚀 ffuf - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   __  __        __
  / _|/ _|_   _ / _|
 | |_| |_| | | | |_
 |  _|  _| |_| |  _|
 |_| |_|  \__,_|_|
                    
   Fuzz Faster U Fool
```

<p align="center">
  <img src="https://img.shields.io/badge/ffuf-Web_Fuzzer-red?style=for-the-badge" alt="ffuf">
  <img src="https://img.shields.io/badge/Fast-Fuzzing-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Go-Language-green?style=for-the-badge" alt="Go">
</p>

<p align="center">
  <b>⚡ Il fuzzer web più rapido scritto in Go</b>
</p>

> **Correzioni dell'originale:** `-r` segue i reindirizzamenti, non sceglie un resolver DNS. `-se` interrompe in presenza di errori anomali, mentre l'opzione relativa a una prevalenza di risposte 403 è distinta. `-mt` e `-ft` confrontano il tempo al primo byte e richiedono una condizione adatta alla versione, non necessariamente il solo numero mostrato. La ricerca cambiando l'header Host individua host virtuali, non prova una risoluzione DNS. Stelle e affermazioni di velocità sono qualitative; ridurre la frequenza non garantisce l'aggiramento di un WAF. Gli esempi originali restano invariati; vedi le [opzioni ufficiali](https://github.com/ffuf/ffuf#usage).

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Sintassi di base](#-sintassi-di-base)
- [Fuzzing delle directory](#-fuzzing-delle-directory)
- [Fuzzing dei sottodomini](#-fuzzing-dei-sottodomini)
- [Fuzzing dei parametri](#-fuzzing-dei-parametri)
- [Fuzzing dei dati POST](#-fuzzing-dei-dati-post)
- [Filtri e criteri di corrispondenza](#-filtri-e-criteri-di-corrispondenza)
- [Funzionalità avanzate](#-funzionalità-avanzate)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

**ffuf** (Fuzz Faster U Fool) è un fuzzer web rapido scritto in Go. Si usa nel bug bounty per cercare directory tramite tentativi sistematici, individuare host virtuali ed eseguire il fuzzing dei parametri.

### Funzionalità principali

| Funzionalità | Descrizione |
|---------|-------------|
| **Velocità** | Molto rapido (basato su Go) |
| **Flessibile** | Esegue il fuzzing di qualsiasi parte di una richiesta HTTP |
| **Filtraggio** | Più opzioni di filtro e corrispondenza |
| **Ricorsione** | Supporto per la scansione ricorsiva |
| **Output** | Più formati di output |
| **Semplice** | Sintassi semplice e intuitiva |

### Confronto tra ffuf e altri strumenti

| Funzionalità | ffuf | Gobuster | dirb |
|---------|------|----------|------|
| **Velocità** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |
| **Flessibilità** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| **POST fuzzing** | ✅ Sì | ❌ No | ❌ No |
| **Ricorsione** | ✅ Sì | ✅ Sì | ✅ Sì |
| **Filtri** | Numerosi | Limitati | Limitati |

---

## 📥 Installazione

### Con Go
```bash
go install github.com/ffuf/ffuf/v2@latest
```

### Kali Linux
```bash
sudo apt install ffuf
```

### Download del binario
```bash
# Scarica dalla pagina delle release
https://github.com/ffuf/ffuf/releases

# Linux
wget https://github.com/ffuf/ffuf/releases/download/v2.x.x/ffuf_2.x.x_linux_amd64.tar.gz
tar xzf ffuf_2.x.x_linux_amd64.tar.gz
sudo mv ffuf /usr/local/bin/
```

### Verifica dell'installazione
```bash
ffuf -V
ffuf -h
```

---

## ⌨ Sintassi di base

### Sintassi generale
```bash
ffuf [options] -u <URL/FUZZ> -w <wordlist>
```

### La parola chiave FUZZ

La parola chiave **FUZZ** indica a ffuf dove inserire le parole della wordlist.

```bash
# Nel percorso dell'URL
ffuf -u https://target.com/FUZZ -w wordlist.txt

# Nel sottodominio
ffuf -u https://FUZZ.target.com -w wordlist.txt

# Nel valore del parametro
ffuf -u https://target.com/page?id=FUZZ -w wordlist.txt

# Nei dati POST
ffuf -u https://target.com/login -X POST -d "user=FUZZ&pass=test" -w wordlist.txt
```

### Opzioni essenziali

| Opzione | Descrizione |
|--------|-------------|
| `-u` | URL bersaglio con la parola chiave FUZZ |
| `-w` | Percorso della wordlist |
| `-o` | File di output |
| `-of` | Formato di output (json, csv, html) |
| `-t` | Numero di thread (predefinito: 40) |
| `-p` | Ritardo tra le richieste |
| `-r` | Segue i reindirizzamenti |
| `-v` | Output dettagliato |
| `-s` | Modalità silenziosa |

---

## 📁 Fuzzing delle directory

### Ricerca di directory tramite tentativi sistematici

```bash
# Fuzzing di base delle directory
ffuf -u https://target.com/FUZZ -w /usr/share/wordlists/dirb/common.txt

# Con estensioni dei file
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.html,.txt,.bak

# Più estensioni
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.asp,.aspx,.jsp,.html
```

### Scansione ricorsiva

```bash
# Abilita la ricorsione
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion

# Profondità della ricorsione
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2

# Ricorsione con una strategia
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-strategy greedy
```

### Filtraggio per codice di stato

```bash
# Seleziona solo le risposte 200
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200

# Seleziona più codici
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200,301,302,403

# Escludi i 404 (modifica del comportamento predefinito)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404

# Seleziona tutto, escludi i 404
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Wordlist diffuse

```bash
# SecLists
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
```

---

## 🌐 Fuzzing dei sottodomini

### Individuazione degli host virtuali

```bash
# Fuzzing di base dei sottodomini
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt

# Filtra per dimensione della risposta (rimuovi la pagina predefinita)
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt -fs 1234

# Con una wordlist specifica
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

### Fuzzing dei sottodomini DNS

```bash
# Sottodominio nell'URL
ffuf -u https://FUZZ.target.com -w subdomains.txt

# Con resolver
ffuf -u https://FUZZ.target.com -w subdomains.txt -r
```

### Wordlist comuni per i sottodomini

```bash
# SecLists DNS
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

---

## 🔍 Fuzzing dei parametri

### Fuzzing dei parametri GET

```bash
# Esegui il fuzzing del nome del parametro
ffuf -u "https://target.com/page?FUZZ=test" -w params.txt

# Esegue fuzzing del valore del parametro
ffuf -u "https://target.com/page?id=FUZZ" -w values.txt

# Più parametri
ffuf -u "https://target.com/page?W1=test&W2=test" -w params.txt:W1 -w values.txt:W2
```

### Individuazione dei parametri

```bash
# Trova i parametri nascosti
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

---

## 📝 Fuzzing dei dati POST

### Fuzzing POST di base

```bash
# POST con dati del modulo
ffuf -u https://target.com/login -X POST \
    -d "username=FUZZ&password=test" \
    -w usernames.txt

# POST con tipo di contenuto
ffuf -u https://target.com/login -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=FUZZ&password=test" \
    -w usernames.txt
```

### Fuzzing POST JSON

```bash
# Dati POST JSON
ffuf -u https://target.com/api/login -X POST \
    -H "Content-Type: application/json" \
    -d '{"username":"FUZZ","password":"test"}' \
    -w usernames.txt
```

### Più wordlist (credential stuffing)

```bash
# Fuzzing di nome utente e password
ffuf -u https://target.com/login -X POST \
    -d "username=USER&password=PASS" \
    -w users.txt:USER \
    -w passwords.txt:PASS \
    -fc 401

# Modalità clusterbomb (tutte le combinazioni)
ffuf -u https://target.com/login -X POST \
    -d "user=USER&pass=PASS" \
    -w users.txt:USER \
    -w passes.txt:PASS \
    -mode clusterbomb
```

---

## 🎯 Filtri e criteri di corrispondenza

### Opzioni di filtro

| Opzione | Descrizione | Esempio |
|--------|-------------|---------|
| `-fc` | Filtra per codice di stato | `-fc 404,403` |
| `-fs` | Filtra per dimensione della risposta | `-fs 1234` |
| `-fw` | Filtra per numero di parole | `-fw 100` |
| `-fl` | Filtra per numero di righe | `-fl 50` |
| `-ft` | Filtra per tempo (ms) | `-ft 5000` |
| `-fr` | Filtra tramite espressione regolare | `-fr "error"` |

### Opzioni di corrispondenza

| Opzione | Descrizione | Esempio |
|--------|-------------|---------|
| `-mc` | Seleziona codici di stato | `-mc 200,301` |
| `-ms` | Seleziona la dimensione della risposta | `-ms 1234` |
| `-mw` | Seleziona il numero di parole | `-mw 100` |
| `-ml` | Seleziona il numero di righe | `-ml 50` |
| `-mt` | Seleziona il tempo (ms) | `-mt 5000` |
| `-mr` | Seleziona tramite espressione regolare | `-mr "success"` |

### Esempi di filtri

```bash
# Escludi i 404 e i 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404,403

# Filtra per dimensione (rimuovi la pagina predefinita)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fs 0,1234

# Filtra per parole
ffuf -u https://target.com/FUZZ -w wordlist.txt -fw 10

# Seleziona tutto, escludi casi specifici
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Calibrazione automatica

```bash
# Calibra automaticamente i filtri
ffuf -u https://target.com/FUZZ -w wordlist.txt -ac

# Calibrazione con una parola chiave specifica
ffuf -u https://target.com/FUZZ -w wordlist.txt -acc "randomstring123"
```

---

## ⚡ Funzionalità avanzate

### Limitazione della frequenza delle richieste

```bash
# Richieste al secondo
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 100

# Ritardo tra le richieste
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.5

# Ritardo casuale (jitter)
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.1-1.0
```

### Thread

```bash
# Imposta i thread (predefinito: 40)
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 100

# Riduci per una minore visibilità
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 10 -p 1
```

### Header

```bash
# Header personalizzato
ffuf -u https://target.com/FUZZ -w wordlist.txt -H "X-Custom: value"

# Più header
ffuf -u https://target.com/FUZZ -w wordlist.txt \
    -H "Authorization: Bearer token" \
    -H "X-Forwarded-For: 127.0.0.1"

# Cookie
ffuf -u https://target.com/FUZZ -w wordlist.txt -b "session=abc123"
```

### Proxy

```bash
# Proxy HTTP
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080

# Burp Suite
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080
```

### Output

```bash
# Output JSON
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.json -of json

# Output CSV
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.csv -of csv

# Output HTML
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.html -of html

# Tutti i formati
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results -of all
```

### Timeout e nuovi tentativi

```bash
# Timeout della richiesta
ffuf -u https://target.com/FUZZ -w wordlist.txt -timeout 10

# Interrompi in presenza di 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -se

# Ignora gli errori SSL
ffuf -u https://target.com/FUZZ -w wordlist.txt -k
```

---

## 🎬 Esempi pratici

### Esempio 1: scansione di base delle directory
```bash
ffuf -u https://target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/common.txt -mc 200,301,302
```

### Esempio 2: trova file PHP
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.php.bak,.php~ -mc 200
```

### Esempio 3: enumerazione dei sottodomini
```bash
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    -fs 1234 -t 100
```

### Esempio 4: individuazione degli endpoint API
```bash
ffuf -u https://api.target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -mc 200,401,403
```

### Esempio 5: individuazione dei parametri
```bash
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

### Esempio 6: tentativi sistematici di accesso
```bash
ffuf -u https://target.com/login -X POST \
    -d "user=admin&pass=FUZZ" \
    -w /usr/share/seclists/Passwords/Common-Credentials/10k-most-common.txt \
    -fc 401 -t 10
```

### Esempio 7: con proxy (Burp)
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080 -k
```

### Esempio 8: scansione ricorsiva
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2 -mc 200,301
```

### Esempio 9: scansione con frequenza limitata
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 10 -t 5
```

### Esempio 10: scansione completa per bug bounty
```bash
ffuf -u https://target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt \
    -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak \
    -mc 200,301,302,403 \
    -recursion -recursion-depth 2 \
    -o results.json -of json \
    -ac
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Attività | Comando |
|------|---------|
| Scansione delle directory | `ffuf -u URL/FUZZ -w wordlist.txt` |
| Con estensioni | `ffuf -u URL/FUZZ -w wordlist.txt -e .php,.html` |
| Scansione dei sottodomini | `ffuf -u URL -H "Host: FUZZ.domain" -w subs.txt` |
| POST fuzzing | `ffuf -u URL -X POST -d "param=FUZZ" -w wordlist.txt` |
| Escludi i 404 | `ffuf -u URL/FUZZ -w wordlist.txt -fc 404` |
| Filtra per dimensione | `ffuf -u URL/FUZZ -w wordlist.txt -fs 1234` |
| Calibra automaticamente | `ffuf -u URL/FUZZ -w wordlist.txt -ac` |
| Salva l'output | `ffuf -u URL/FUZZ -w wordlist.txt -o out.json -of json` |

### Opzioni comuni

| Opzione | Descrizione |
|--------|-------------|
| `-u` | URL con FUZZ |
| `-w` | Wordlist |
| `-e` | Estensioni |
| `-t` | Thread |
| `-mc` | Codici da selezionare |
| `-fc` | Codici da escludere |
| `-fs` | Dimensione da escludere |
| `-ac` | Calibra automaticamente |
| `-r` | Segue i reindirizzamenti |
| `-o` | File di output |

---

## 💡 Consigli e buone pratiche

### Consigli per il bug bounty

1. **Esegui sempre prima la calibrazione automatica**
   ```bash
   ffuf -u target.com/FUZZ -w wordlist.txt -ac
   ```

2. **Usa più estensioni**
   ```bash
   -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak,.old,.zip
   ```

3. **Prova le estensioni comuni dei backup**
   ```bash
   -e .bak,.old,.orig,.backup,~,.swp
   ```

4. **Limitazione della frequenza per aggirare il WAF**
   ```bash
   -rate 10 -t 5 -p 0.5
   ```

### Consigli per le prestazioni

```bash
# Velocità elevata (senza WAF)
ffuf -u URL/FUZZ -w wordlist.txt -t 200

# Volume ridotto e velocità bassa (minore visibilità)
ffuf -u URL/FUZZ -w wordlist.txt -t 10 -rate 5 -p 1
```

---

## ⚠ Avvertenza legale

> **ATTENZIONE:** ffuf è un potente strumento di fuzzing da usare solo per **test autorizzati**.
>
> - ✅ Usa lo strumento su programmi per i quali hai il permesso di effettuare test
> - ✅ Usa lo strumento nei programmi bug bounty autorizzati
> - ✅ Rispetta i limiti di frequenza e l'ambito autorizzato
> - ❌ Non eseguire mai fuzzing senza autorizzazione
> - ❌ Non causare interruzioni del servizio
>
> **Le scansioni non autorizzate sono illegali.**

---

## 📚 Risorse

### Risorse ufficiali
- [ffuf GitHub](https://github.com/ffuf/ffuf)
- [ffuf Wiki](https://github.com/ffuf/ffuf/wiki)

### Wordlist
- [SecLists](https://github.com/danielmiessler/SecLists)
- [Wordlist Assetnote](https://wordlists.assetnote.io/)

### Schede correlate
- [Nuclei](../../Nuclei/translations/README.it.md)
- [Gobuster](../../Gobuster/translations/README.it.md)
- [Nmap](../../Nmap/translations/README.it.md)

---

<p align="center">
  <b>🚀 Fuzzing più rapido, più risultati!</b><br>
  <i>Il fuzzer scelto da chi pratica bug bounty</i>
</p>
