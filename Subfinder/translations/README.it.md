# 🔍 Subfinder - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ____        _     __ _           _
 / ___| _   _| |__ / _(_)_ __   __| | ___ _ __
 \___ \| | | | '_ \ |_| | '_ \ / _` |/ _ \ '__|
  ___) | |_| | |_) |  _| | | | | (_| |  __/ |
 |____/ \__,_|_.__/|_| |_|_| |_|\__,_|\___|_|
                                                
   Strumento rapido per individuare sottodomini
```

<p align="center">
  <img src="https://img.shields.io/badge/Subfinder-Subdomain_Discovery-red?style=for-the-badge" alt="Subfinder">
  <img src="https://img.shields.io/badge/Passive-Recon-orange?style=for-the-badge" alt="Passive">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🌐 Strumento rapido di ProjectDiscovery per l'enumerazione passiva dei sottodomini</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Nota sulle versioni:** fonti disponibili, piani gratuiti e confronti dell'originale possono cambiare. `-recursive` seleziona fonti capaci di gestire ricorsivamente i sottodomini, non un crawler ricorsivo universale; `-t` riguarda la risoluzione nella modalità attiva. La verifica con `-nW` comporta attività di risoluzione: “passivo” non descrive ogni opzione o pipeline. `-oJ` produce JSONL, `-oD` indica una directory di output, non CSV; `-oJL` e `-exclude-subdomains` non compaiono nell'elenco ufficiale consultato. `-cs` è documentato per l'output JSON. Manteniamo i comandi originali: verifica la [guida ufficiale](https://docs.projectdiscovery.io/opensource/subfinder/usage) della release usata.

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Sintassi di base](#-sintassi-di-base)
- [Individuazione dei sottodomini](#-individuazione-dei-sottodomini)
- [Fonti dei dati](#-fonti-dei-dati)
- [Configurazione delle API](#-configurazione-delle-api)
- [Opzioni di output](#-opzioni-di-output)
- [Funzionalità avanzate](#-funzionalità-avanzate)
- [Flusso di lavoro per il bug bounty](#-flusso-di-lavoro-per-il-bug-bounty)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

**Subfinder** è uno strumento rapido per l'enumerazione passiva dei sottodomini. Usa più fonti, tra cui motori di ricerca, siti di condivisione di testo e piattaforme di intelligence.

### Funzionalità principali

| Funzionalità | Descrizione |
|---------|-------------|
| **Veloce** | Basato su Go, con elevata concorrenza |
| **Passivo** | Nessuna interazione diretta con il bersaglio |
| **Più fonti** | Oltre 40 fonti di dati |
| **Integrazione API** | Supporto per fonti premium |
| **Adatto alle pipeline** | Si integra con altri strumenti |
| **Output JSON** | Supporto per output strutturato |

### Confronto tra Subfinder e altri strumenti

| Funzionalità | Subfinder | Amass | Assetfinder |
|---------|-----------|-------|-------------|
| **Velocità** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| **Fonti** | 40+ | 50+ | 10+ |
| **Scansione attiva** | ❌ No | ✅ Sì | ❌ No |
| **Supporto API** | ✅ Sì | ✅ Sì | ❌ No |
| **Facilità d'uso** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |

---

## 📥 Installazione

### Con Go
```bash
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
```

### Kali Linux
```bash
sudo apt install subfinder
```

### Download del binario
```bash
# Scarica dalla pagina delle release
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

### Verifica dell'installazione
```bash
subfinder -version
subfinder -h
```

---

## ⌨ Sintassi di base

### Sintassi generale
```bash
subfinder [options] -d <domain>
```

### Opzioni essenziali

| Opzione | Descrizione |
|--------|-------------|
| `-d` | Dominio bersaglio |
| `-dL` | File con un elenco di domini |
| `-o` | File di output |
| `-oJ` | Output JSON |
| `-silent` | Modalità silenziosa (solo sottodomini) |
| `-v` | Output dettagliato |
| `-t` | Numero di thread |
| `-all` | Usa tutte le fonti |

### Avvio rapido
```bash
# Enumerazione di base
subfinder -d example.com

# Output silenzioso (elenco pulito)
subfinder -d example.com -silent

# Salva su file
subfinder -d example.com -o subdomains.txt

# Più domini
subfinder -dL domains.txt -o all-subs.txt
```

---

## 🔍 Individuazione dei sottodomini

### Singolo dominio

```bash
# Scansione di base
subfinder -d target.com

# Output dettagliato
subfinder -d target.com -v

# Modalità silenziosa (solo sottodomini)
subfinder -d target.com -silent

# Mostra le fonti
subfinder -d target.com -cs
```

### Più domini

```bash
# Da file
subfinder -dL domains.txt

# Salva l'output su file
subfinder -dL domains.txt -o all_subdomains.txt

# Modalità silenziosa per le pipeline
subfinder -dL domains.txt -silent | httpx
```

### Enumerazione ricorsiva

```bash
# Abilita la ricorsione (individua sottodomini di sottodomini)
subfinder -d target.com -recursive

# Ricorsione con tutte le fonti
subfinder -d target.com -recursive -all
```

---

## 📊 Fonti dei dati

### Fonti gratuite (predefinite)

Subfinder usa queste fonti per impostazione predefinita (senza chiave API):
- Alienvault
- AnubisDB
- Crtsh
- Hackertarget
- Rapiddns
- Sublist3r
- ThreatCrowd
- URLScan
- Wayback Machine

### Fonti premium (API necessaria)

Queste fonti richiedono chiavi API per ottenere risultati migliori:
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

### Gestione delle fonti

```bash
# Elenca tutte le fonti
subfinder -ls

# Usa solo fonti specifiche
subfinder -d target.com -sources crtsh,hackertarget,alienvault

# Escludi fonti specifiche
subfinder -d target.com -es shodan,securitytrails

# Usa tutte le fonti (comprese quelle lente)
subfinder -d target.com -all

# Mostra la fonte che ha trovato ogni sottodominio
subfinder -d target.com -cs
```

---

## 🔑 Configurazione delle API

### Percorso del file di configurazione

```bash
# Percorso predefinito
~/.config/subfinder/provider-config.yaml

# Oppure specifica un percorso personalizzato
subfinder -d target.com -pc /path/to/config.yaml
```

### Modello del file di configurazione

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

### Come ottenere le chiavi API

| Fonte | Piano gratuito | URL |
|--------|-----------|-----|
| **VirusTotal** | ✅ Sì | https://www.virustotal.com |
| **SecurityTrails** | ✅ Sì (limitato) | https://securitytrails.com |
| **Shodan** | ✅ Sì (limitato) | https://shodan.io |
| **Censys** | ✅ Sì | https://censys.io |
| **Chaos** | ✅ Gratuito (limitato) | https://chaos.projectdiscovery.io |
| **GitHub** | ✅ Gratuito | https://github.com/settings/tokens |

### Configurazione gratuita consigliata

```yaml
# Configurazione minima consigliata per il bug bounty
virustotal:
  - your_free_key
github:
  - your_github_token
chaos:
  - your_chaos_key
```

---

## 📤 Opzioni di output

### Formati di output

```bash
# Testo semplice (predefinito)
subfinder -d target.com -o subs.txt

# Output JSON
subfinder -d target.com -oJ -o subs.json

# Righe JSON
subfinder -d target.com -oJL -o subs.jsonl

# Formato CSV
subfinder -d target.com -oD -o subs.csv
```

### Filtraggio dell'output

```bash
# Modalità silenziosa (output pulito)
subfinder -d target.com -silent

# Mostra solo i nuovi sottodomini (escludi quelli noti)
subfinder -d target.com -exclude-subdomains known.txt

# Senza colori
subfinder -d target.com -nc
```

### Invio dell'output a una pipeline

```bash
# Invia a httpx tramite pipe
subfinder -d target.com -silent | httpx -silent

# Invia a nuclei tramite pipe
subfinder -d target.com -silent | httpx -silent | nuclei

# Salva ed elabora
subfinder -d target.com -silent | tee subs.txt | httpx
```

---

## ⚡ Funzionalità avanzate

### Thread e prestazioni

```bash
# Imposta i thread (predefinito: 10)
subfinder -d target.com -t 50

# Limitazione della frequenza
subfinder -d target.com -rate-limit 100

# Timeout
subfinder -d target.com -timeout 30
```

### Criteri di corrispondenza e filtri

```bash
# Seleziona pattern specifici
subfinder -d target.com -match "api,dev,stage"

# Escludi pattern
subfinder -d target.com -filter "www,mail"
```

### Opzioni dei resolver

```bash
# Usa resolver personalizzati
subfinder -d target.com -rL resolvers.txt

# Risolvi i sottodomini
subfinder -d target.com -nW
```

### Supporto proxy

```bash
# Proxy HTTP
subfinder -d target.com -proxy http://127.0.0.1:8080

# Proxy SOCKS
subfinder -d target.com -proxy socks5://127.0.0.1:9050
```

---

## 🔄 Flusso di lavoro per il bug bounty

### Pipeline completa di ricognizione

```bash
# Passaggio 1: enumerazione dei sottodomini
subfinder -d target.com -all -o subs.txt

# Passaggio 2: verifica gli host raggiungibili
cat subs.txt | httpx -o live.txt

# Passaggio 3: analizza con nuclei
nuclei -l live.txt -severity critical,high -o vulns.txt

# Comando su una sola riga
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Flusso di lavoro per più domini

```bash
# Enumera tutti i domini del programma
subfinder -dL scope.txt -all -o all-subs.txt

# Elabora i risultati
cat all-subs.txt | httpx -silent | nuclei
```

### Monitoraggio continuo

```bash
# Salva lo stato di riferimento
subfinder -d target.com -silent > baseline.txt

# In seguito, individua i nuovi sottodomini
subfinder -d target.com -silent > current.txt
diff baseline.txt current.txt | grep ">" | cut -d" " -f2 > new-subs.txt
```

### Integrazione con altri strumenti

```bash
# Con httpx (host raggiungibili)
subfinder -d target.com -silent | httpx

# Con ffuf (fuzzing delle directory)
subfinder -d target.com -silent | httpx -silent | \
  xargs -I {} ffuf -u {}/FUZZ -w wordlist.txt

# Con nmap (scansione delle porte)
subfinder -d target.com -silent | dnsx -a -silent | nmap -iL -

# Pipeline completa
subfinder -d target.com -silent | \
  httpx -title -status-code -tech-detect -silent | \
  tee recon-results.txt
```

---

## 🎬 Esempi pratici

### Esempio 1: enumerazione di base
```bash
subfinder -d hackerone.com -silent
```

### Esempio 2: con tutte le fonti
```bash
subfinder -d target.com -all -v
```

### Esempio 3: salva i risultati
```bash
subfinder -d target.com -o target-subs.txt
```

### Esempio 4: più domini
```bash
echo -e "target1.com\ntarget2.com" | subfinder -silent
```

### Esempio 5: mostra le fonti
```bash
subfinder -d target.com -cs
```

### Esempio 6: output JSON
```bash
subfinder -d target.com -oJ -o results.json
```

### Esempio 7: con proxy
```bash
subfinder -d target.com -proxy http://127.0.0.1:8080
```

### Esempio 8: prestazioni elevate
```bash
subfinder -d target.com -all -t 100 -o subs.txt
```

### Esempio 9: pipeline completa per bug bounty
```bash
subfinder -d target.com -all -silent | \
  httpx -silent -status-code -title | \
  tee live-hosts.txt | \
  nuclei -severity critical,high
```

### Esempio 10: individuazione ricorsiva
```bash
subfinder -d target.com -recursive -all -silent
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Attività | Comando |
|------|---------|
| Scansione di base | `subfinder -d target.com` |
| Output silenzioso | `subfinder -d target.com -silent` |
| Salva su file | `subfinder -d target.com -o subs.txt` |
| Tutte le fonti | `subfinder -d target.com -all` |
| Mostra le fonti | `subfinder -d target.com -cs` |
| Più domini | `subfinder -dL domains.txt` |
| Output JSON | `subfinder -d target.com -oJ` |
| Elenca le fonti | `subfinder -ls` |

### Opzioni comuni

| Opzione | Descrizione |
|--------|-------------|
| `-d` | Dominio bersaglio |
| `-dL` | File con l'elenco dei domini |
| `-o` | File di output |
| `-oJ` | Output JSON |
| `-silent` | Modalità silenziosa |
| `-all` | Usa tutte le fonti |
| `-cs` | Mostra la fonte di ogni sottodominio |
| `-t` | Thread |
| `-recursive` | Enumerazione ricorsiva |

### Pipeline comuni

| Pipeline | Comando |
|----------|---------|
| Host raggiungibili | `subfinder -d target.com -silent \| httpx` |
| Ricognizione completa | `subfinder -d target.com \| httpx \| nuclei` |
| Scansione delle porte | `subfinder -d target.com -silent \| dnsx -a \| nmap -iL -` |

---

## 💡 Consigli e buone pratiche

### Consigli per il bug bounty

1. **Configura le chiavi API**
   ```bash
   # Risultati migliori con le API
   vim ~/.config/subfinder/provider-config.yaml
   ```

2. **Usa tutte le fonti per una maggiore completezza**
   ```bash
   subfinder -d target.com -all
   ```

3. **Combina con altri strumenti**
   ```bash
   subfinder -d target.com -silent | httpx | nuclei
   ```

4. **Monitora i nuovi sottodomini**
   ```bash
   # Verifica giornaliera dei nuovi asset
   subfinder -d target.com -silent > today.txt
   ```

### Consigli per le prestazioni

```bash
# Numero elevato di thread
subfinder -d target.com -t 100 -all

# Timeout per le fonti lente
subfinder -d target.com -timeout 60
```

---

## ⚠ Avvertenza legale

> **ATTENZIONE:** Subfinder esegue ricognizione passiva senza interagire direttamente con i bersagli. Gli strumenti successivi nella pipeline possono però farlo.
>
> - ✅ Usa lo strumento sui programmi autorizzati
> - ✅ Bersagli bug bounty compresi nell'ambito autorizzato
> - ✅ Domini di tua proprietà
> - ❌ Non enumerare mai bersagli senza autorizzazione
> - ❌ Non oltrepassare l'ambito del programma
>
> **Assicurati sempre di avere l'autorizzazione.**

---

## 📚 Risorse

### Risorse ufficiali
- [Subfinder GitHub](https://github.com/projectdiscovery/subfinder)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentazione](https://docs.projectdiscovery.io/tools/subfinder/)

### Schede correlate
- [Nuclei](../../Nuclei/translations/README.it.md)
- [ffuf](../../ffuf/translations/README.it.md)
- [Nmap](../../Nmap/translations/README.it.md)

---

<p align="center">
  <b>🔍 Trova ogni sottodominio!</b><br>
  <i>Il primo passo della ricognizione per bug bounty</i>
</p>
