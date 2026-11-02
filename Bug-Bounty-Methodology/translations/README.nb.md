# 🐛 Metodikk for bug bounty

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

> **Begreper og rettelser:** Rekognosering samler bakgrunnsopplysninger; kartlegging (enumeration) undersøker ressurser systematisk. En pipeline sender utdata videre til neste verktøy, mens en payload er testinndata. Verktøytreff, delte sertifikater og ASN-tilknytning bekrefter ikke alene eierskap, omfang eller sårbarhet. Påstanden om CORS nedenfor er for generell: nettlesere tillater ikke credentialed CORS med `Access-Control-Allow-Origin: *`; alvorlighetsgrad må vurderes ut fra faktisk lesetilgang og konsekvens. Se [MDNs forklaring](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS/Errors/CORSNotSupportingCredentials). Gamle installasjoner, datakilder og flagg er bevart; ingen pipeline eller WAF-teknikk garanterer et bekreftet funn.

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
  ██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
  ██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
  ██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
  ██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
  ╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
            Komplett metodikk for bug bounty
```

<p align="center">
  <img src="https://img.shields.io/badge/Bug_Bounty-Methodology-red?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Recon-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/Vulnerability-green?style=for-the-badge" alt="Vuln">
</p>

---

## 🗺️ Oversikt over angrepsforløpet

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      METODIKK FOR BUG BOUNTY                                │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  FASE 1: Underdomener       FASE 2: ASN og IP        FASE 3: Verter        │
│  ├── Subfinder              ├── ASN-kartl.           ├── HTTPX-test        │
│  ├── Amass                  ├── IP-innsamling        ├── Finn porter       │
│  ├── crt.sh                 └── Shodan               └── Aquatone          │
│  └── Wayback                                                                │
│                                                                             │
│  FASE 4: URL-innsamling     FASE 5: Sårbarheter      FASE 6: Fordypning    │
│  ├── Katana/Hakrawler       ├── XSS-testing          ├── Katalogtest       │
│  ├── GAU                    ├── LFI/RFI              ├── Parameterfuzzing  │
│  └── Hent parametere        ├── SSRF/CORS            └── Git-ekspon.       │
│                             └── Åpen omdir.                                 │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Innholdsfortegnelse

1. [Fase 1: Kartlegging av underdomener](#-fase-1-kartlegging-av-underdomener)
2. [Fase 2: Oppdagelse av ASN og IP-adresser](#-fase-2-oppdagelse-av-asn-og-ip-adresser)
3. [Fase 3: Oppdagelse av tilgjengelige verter](#-fase-3-oppdagelse-av-tilgjengelige-verter)
4. [Fase 4: Innsamling av URL-er](#-fase-4-innsamling-av-url-er)
5. [Fase 5: Søk etter sårbarheter](#-fase-5-søk-etter-sårbarheter)
6. [Fase 6: Oppdagelse av kataloger og filer](#-fase-6-oppdagelse-av-kataloger-og-filer)
7. [Hurtigreferanse](#-hurtigreferanse)
8. [Installasjon av verktøy](#-installasjon-av-verktøy)

---

## 🔍 Fase 1: Kartlegging av underdomener

> **Mål:** Finn så mange underdomener som mulig – en større angrepsflate gir flere muligheter til å oppdage sårbarheter.

### Verktøy for automatisert kartlegging

#### Subfinder
```bash
# Rask oppdagelse av underdomener fra flere datakilder
subfinder -d example.com -all -recursive -o subfinder.txt

# Forklaring av flagg:
# -all: Bruk alle kilder (gratis og betalte)
# -recursive: Rekursiv kartlegging av underdomener
# -o: Utdatafil
```
> 💡 **Tips:** Subfinder er raskt og pålitelig. Bruk `-all` for mer omfattende resultater.

#### Assetfinder
```bash
# Finn domener og underdomener knyttet til et mål
assetfinder --subs-only example.com > assetfinder.txt

# Raskt og enkelt – nyttig ved innledende rekognosering
```
> 💡 **Tips:** Assetfinder er raskt, men mindre omfattende. Bruk det sammen med andre verktøy.

#### Sublist3r
```bash
# Kartlegging av underdomener med OSINT-teknikker
sublist3r -d example.com -e baidu,yahoo,google,bing,ask,netcraft,virustotal,threatcrowd,crtsh,passivedns -v -o sublist3r.txt

# -e: Angi søkemotorer
# -v: Detaljerte utdata
```

#### Amass
```bash
# Grundig kartlegging av angrepsflaten
amass enum -passive -d example.com | cut -d']' -f 2 | awk '{print $1}' | sort -u > amass.txt

# Passiv modus – kontakter ikke målet direkte
# Bruk -active for flere resultater (men mer støy)
```
> ⚠️ **Advarsel:** Aktiv Amass kan lage mye støy. Begynn med passiv modus!

### Offentlige kilder

#### Certificate Transparency (crt.sh)
```bash
# Finn underdomener i logger over SSL-sertifikater
curl -s https://crt.sh\?q\=\example.com\&output\=json | jq -r '.[].name_value' | grep -Po '(\w+\.\w+\.\w+)$' > crtsh.txt
```
> 💡 **Hvorfor crt.sh?** SSL-sertifikater er offentlige opplysninger – en gratis kilde til underdomener!

#### Wayback Machine
```bash
# Finn historiske underdomener i nettarkiver
curl -s "http://web.archive.org/cdx/search/cdx?url=*.example.com/*&output=text&fl=original&collapse=urlkey" | sort | sed -e 's_https*://__' -e "s/\/.*//" -e 's/:.*//' -e 's/^www\.//' | sort -u > wayback.txt
```
> 💡 **Tips:** Gamle underdomener kan fortsatt eksistere selv om de er glemt – undersøk dem!

### Behandling av underdomener

#### Slå sammen resultater og fjern duplikater
```bash
# Slå sammen alle underdomenefilene og fjern duplikater
cat *.txt | sort -u > final_subdomains.txt

# Tell resultatene
wc -l final_subdomains.txt
```

#### Utprøving av underdomener med FFUF
```bash
# Finn underdomener ved å prøve navn fra ordlister
ffuf -u "https://FUZZ.example.com" -w wordlist.txt -mc 200,301,302

# -mc: Treff bare bestemte statuskoder
# Forslag til ordliste: /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

#### Varianter av underdomener med Alterx
```bash
# Generer varianter av underdomener og slå dem opp
subfinder -d example.com | alterx | dnsx

# Utvid domenet med vanlige mønstre
echo example.com | alterx -enrich | dnsx

# Bruk en ordliste til variantgenerering
echo example.com | alterx -pp word=/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt | dnsx
```
> 💡 **Eksempel:** Finner du `dev.example.com`, genererer alterx blant annet `dev1`, `dev2` og `dev-api`.

---

## 🌐 Fase 2: Oppdagelse av ASN og IP-adresser

> **Mål:** Finn IP-områder og infrastruktur knyttet til målorganisasjonen.

### ASN-kartlegging

```bash
# Finn IP-adresser knyttet til domenets ASN
asnmap -d example.com | dnsx -silent -resp-only

# Finn ressurser etter organisasjonsnavn
amass intel -org "organization_name"

# Finn ressurser innenfor et IP-område
amass intel -active -cidr 159.69.129.82/32

# Finn ressurser etter ASN-nummer
amass intel -active -asn [asnno]
```
> 💡 **Hvorfor ASN?** Organisasjoner kan disponere hele IP-områder – se etter mindre synlige servere!

### Innsamling av IP-adresser

#### VirusTotal
```bash
# Hent ut IP-adresser fra VirusTotal
curl -s "https://www.virustotal.com/vtapi/v2/domain/report?domain=example.com&apikey=[api-key]" | jq -r '.. | .ip_address? // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### AlienVault OTX
```bash
# Hent IP-adresser fra AlienVault Open Threat Exchange
curl -s "https://otx.alienvault.com/api/v1/indicators/hostname/example.com/url_list?limit=500&page=1" | jq -r '.url_list[]?.result?.urlworker?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### URLScan.io
```bash
# Hent ut IP-adresser fra URLScan.io
curl -s "https://urlscan.io/api/v1/search/?q=domain:example.com&size=10000" | jq -r '.results[]?.page?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### Shodan-søk i SSL-sertifikater
```bash
# Finn IP-adresser med Shodans søk i SSL-sertifikater
shodan search Ssl.cert.subject.CN:"example.com" 200 --fields ip_str | httpx -sc -title -server -td
```
> 💡 **Tips:** Shodan-søk i SSL-sertifikater kan finne servere som bruker målets sertifikat, også mindre synlige servere!

---

## ✅ Fase 3: Oppdagelse av tilgjengelige verter

> **Mål:** Filtrer underdomenene og finn verter som svarer og er tilgjengelige.

### HTTP-undersøkelse med HTTPX

```bash
# Grunnleggende undersøkelse på flere porter
cat subdomain.txt | httpx -ports 80,443,8080,8000,8888 -threads 200 > subdomains_alive.txt

# Detaljert undersøkelse med statuskoder, titler og teknologigjenkjenning
cat subdomain.txt | httpx -sc -title -server -td -ports 80,443,8080,8000,8888 -threads 200

# Flagg:
# -sc: Statuskode
# -title: Sidetittel
# -server: Server-header
# -td: Teknologigjenkjenning
```

### Visuell rekognosering med Aquatone

```bash
# Ta skjermbilder av tilgjengelige verter
cat hosts.txt | aquatone

# Egendefinerte porter
cat hosts.txt | aquatone -ports 80,443,8000,8080,8443

# Utvidet portområde (omfattende)
cat hosts.txt | aquatone -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```
> 💡 **Hvorfor skjermbilder?** Identifiser interessante applikasjoner, påloggingspaneler og feilsider raskt.

---

## 🔗 Fase 4: Innsamling av URL-er

> **Mål:** Samle så mange URL-er som mulig til testing av parametere og sårbarheter.

### Aktiv gjennomgang av nettsteder

#### Katana
```bash
# Rask crawler for å oppdage URL-er
katana -u livesubdomains.txt -d 2 -o urls.txt

# -d 2: Dybde på 2 nivåer
# Raskt og effektivt for moderne webapplikasjoner
```

#### Hakrawler
```bash
# Enkel og rask crawler
cat urls.txt | hakrawler -u > crawled_urls.txt
```

### Passiv innsamling av URL-er

#### GAU (Get All URLs)
```bash
# Hent kjente URL-er fra AlienVault OTX, Wayback Machine og Common Crawl
cat livesubdomains.txt | gau | sort -u > passive_urls.txt

# Hent URL-er med statuskode 200 og fjern duplikater
echo example.com | gau --mc 200 | urldedupe > urls.txt
```
> 💡 **Hvorfor GAU?** Historiske data kan inneholde URL-er som avslører skjulte endepunkter!

#### URLFinder
```bash
# Finn URL-er fra ulike kilder
urlfinder -d example.com | sort -u > urlfinder_urls.txt
```

### Uthenting av parametere

```bash
# Hent ut URL-er som inneholder parametere (nyttige ved testing!)
cat allurls.txt | grep '=' | urldedupe | tee params_urls.txt

# Mønstergjenkjenning for parametere
cat allurls.txt | grep -E '\?[^=]+=.+$' | tee params_urls.txt

# GF SQLi-mønster – URL-er som kan være utsatt for SQL-injeksjon
cat allurls.txt | gf sqli

# GF XSS-mønster
cat allurls.txt | gf xss

# GF LFI-mønster
cat allurls.txt | gf lfi
```
> 💡 **GF-mønstre:** Installer `gf` og hent mønstre fra github.com/1ndianl33t/Gf-Patterns

---

## 🔓 Fase 5: Søk etter sårbarheter

### 🔴 Oppdagelse av XSS

#### Testing av reflektert XSS
```bash
# Omfattende pipeline for testing av reflektert XSS
subfinder -d "example.com" -all -recursive | \
httpx -mc 200 -silent | \
sed -E 's,https?://(www\.)?,,' | anew | \
urlfinder -all | \
iconv -f ISO-8859-1 -t UTF-8 -c | \
grep -aE '\?.*=.*(&.*)?' | \
grep -aiEv "\.(css|ico|woff|woff2|svg|ttf|eot|png|jpg|jpeg|js|json|pdf|gif|xml|webp)($|\s|\?|&|#|/|\.)" | \
awk -F'[?&=]' '!seen[$1$2]++' | anew
```

#### Testing av blind XSS
```bash
# Testing av blind XSS med spesialiserte payloads
# Bruk tjenester som XSS Hunter eller Burp Collaborator
# Sett inn payloaden i alle inndatafelt, headere og parametere
```
> 💡 **Blind XSS:** Payloaden kan kjøres senere når en administrator viser dataene – bruk en server for tilbakemeldinger!

### 🔴 Oppdagelse av LFI

```bash
# LFI-testing med FFUF og gjenkjenning av passwd-filen
echo "https://example.com/" | gau | gf lfi | uro | \
sed 's/=.*/=/' | qsreplace "FUZZ" | sort -u | \
xargs -I{} ffuf -u {} -w payloads/lfi.txt -c -mr "root:(x|\*|\$[^\:]*):0:0:" -v

# LFI-testing med curl og parallell behandling
gau example.com | gf lfi | qsreplace "/etc/passwd" | \
xargs -I% -P 25 sh -c 'curl -s "%" 2>&1 | grep -q "root:x" && echo "VULN! %"'

# LFI-test med HTTPx
echo 'https://example.com/index.php?page=' | \
httpx -paths payloads/lfi.txt -threads 50 -random-agent -mc 200 -mr "root:(x|\*|\$[^\:]*):0:0:"
```
> 💡 **LFI-tips:** Prøv `/etc/passwd`, `....//....//etc/passwd` og kodede varianter!

### 🔴 Oppdagelse av åpne omdirigeringer

```bash
# Finn omdirigeringsparametere
cat urls.txt | grep -Pi "returnUrl=|continue=|dest=|destination=|forward=|go=|goto=|next=|redirect=|redirect_to=|redirect_uri=|redirect_url=|return=|returnTo=|return_url=|rurl=|target=|to=|url=" | tee redirect_params.txt

# GF-mønster for omdirigering
cat urls.txt | gf redirect | uro | sort -u | tee redirect_params.txt

# Test omdirigeringsparametere
cat redirect_params.txt | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"

# Full pipeline for åpne omdirigeringer
subfinder -d example.com -all | httpx -silent | gau | gf redirect | uro | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"
```

### 🔴 Oppdagelse av SSRF

```bash
# Finn parametere som kan være utsatt for SSRF
cat urls.txt | grep -E 'url=|uri=|redirect=|next=|data=|path=|dest=|proxy=|file=|img=|out=|continue=' | sort -u

# Finn API-/Webhook-mønstre
cat urls.txt | grep -i 'webhook\|callback\|upload\|fetch\|import\|api' | sort -u

# SSRF-skanning med Nuclei
cat urls.txt | nuclei -t nuclei-templates/vulnerabilities/ssrf/

# Grunnleggende SSRF-test mot localhost
curl "https://example.com/page?url=http://127.0.0.1:80/"

# SSRF mot metadata i skyen (AWS)
curl "https://example.com/api?endpoint=http://169.254.169.254/latest/meta-data/"
```
> ⚠️ **SSRF-konsekvenser:** Kan gi tilgang til interne tjenester, metadata i skyen og mer!

### 🔴 CORS-testing

```bash
# Test CORS-konfigurasjonen med en egendefinert origin
curl -H "Origin: http://evil.com" -I https://example.com/api/

# Detaljert CORS-analyse
curl -H "Origin: http://evil.com" -I https://example.com/api/ | \
grep -i -e "access-control-allow-origin" -e "access-control-allow-methods" -e "access-control-allow-credentials"

# Automatisert CORS-skanning med Nuclei
cat subdomains.txt | httpx -silent | nuclei -t nuclei-templates/vulnerabilities/cors/ -o cors_results.txt
```
> 💡 **CORS-sårbarhet:** Originalen hevder at `Access-Control-Allow-Origin: *` sammen med credentials er kritisk; se rettelsen ovenfor.

### Sårbarhetsskanning med Nuclei

```bash
# Ett mål
nuclei -u https://example.com -bs 50 -c 30

# Flere mål
nuclei -l live_domains.txt -bs 50 -c 30

# Bare kritisk og høy alvorlighetsgrad
nuclei -l live_domains.txt -s critical,high -bs 50 -c 30

# -bs: Gruppestørrelse
# -c: Parallellitet
```

---

## 📂 Fase 6: Oppdagelse av kataloger og filer

### Oppdagelse av sensitive filer

```bash
# Grunnleggende filendelser for sensitive filer
cat allurls.txt | grep -E "\.xls|\.xml|\.xlsx|\.json|\.pdf|\.sql|\.doc|\.docx|\.pptx|\.txt|\.zip|\.tar\.gz|\.tgz|\.bak|\.7z|\.rar|\.log|\.cache|\.secret|\.db|\.backup|\.yml|\.gz|\.config|\.csv|\.yaml|\.md|\.md5"

# Utvidet regulært uttrykk for sensitive filer
cat allurls.txt | grep -E "\.(xls|xml|xlsx|json|pdf|sql|doc|docx|pptx|txt|zip|tar\.gz|tgz|bak|7z|rar|log|cache|secret|db|backup|yml|gz|config|csv|yaml|md|md5|tar|xz|7zip|p12|pem|key|crt|csr|sh|pl|py|java|class|jar|war|ear|sqlitedb|sqlite3|dbf|db3|accdb|mdb|sqlcipher|gitignore|env|ini|conf|properties|plist|cfg)$"

# Google-søk etter filer
# site:*.example.com (ext:doc OR ext:docx OR ext:pdf OR ext:xls OR ext:xlsx OR ext:txt OR ext:xml OR ext:json OR ext:zip OR ext:sql OR ext:bak OR ext:conf OR ext:log)
```

### Systematisk utprøving av katalognavn

#### Dirsearch
```bash
# Grunnleggende katalogoppdagelse
dirsearch -u https://example.com --full-url --deep-recursive -r

# Utvidet med flere filendelser
dirsearch -u https://example.com -e php,cgi,htm,html,js,txt,bak,zip,old,conf,log,asp,aspx,jsp,sql,json,xml,yml,yaml,py,rb --random-agent --recursive -R 3 -t 20 --exclude-status=404 --follow-redirects
```

#### Katalogoppdagelse med FFUF
```bash
# Omfattende utprøving av katalognavn med FFUF
ffuf -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt \
-u https://example.com/FUZZ \
-fc 400,401,402,403,404,429,500,501,502,503 \
-recursion -recursion-depth 2 \
-e .html,.php,.txt,.pdf,.js,.css,.zip,.bak,.old,.log,.json,.xml,.config,.env \
-ac -c \
-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0" \
-t 10
```

### Oppdagelse av eksponerte Git-data

```bash
# Finn eksponerte .git-kataloger
cat domains.txt | httpx -sc -server -cl -path "/.git/" -mc 200 -location -ms "Index of" -probe

# Se etter .git/config
curl -s https://example.com/.git/config

# Hent Git-repoet hvis det er eksponert
git-dumper https://example.com/.git/ output/
```
> ⚠️ **Git-eksponering:** Kan avsløre kildekode, påloggingsopplysninger og hemmeligheter!

### Oppdagelse av skjulte parametere med Arjun

```bash
# Passiv oppdagelse av parametere
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -t 10 --rate-limit 10 --passive -m GET,POST --headers "User-Agent: Mozilla/5.0"

# Aktiv oppdagelse av parametere med ordliste
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -m GET,POST \
-w /usr/share/wordlists/seclists/Discovery/Web-Content/burp-parameter-names.txt \
-t 10 --rate-limit 10 --headers "User-Agent: Mozilla/5.0"
```

### Overtakelse av underdomener

```bash
# Automatisert oppdagelse av mulige underdomeneovertakelser
subzy run --targets subdomains.txt --concurrency 100 --hide_fails --verify_ssl
```
> 💡 **Tegn på overtakelse:** CNAME som peker til tjenester uten eier (S3, Heroku osv.)

### WordPress-testing

```bash
# Full sikkerhetsskanning av WordPress
wpscan --url https://example.com --disable-tls-checks --api-token YOUR_API_TOKEN \
-e at -e ap -e u --enumerate ap --plugins-detection aggressive --force

# -e at: Alle temaer
# -e ap: Alle utvidelser
# -e u: Brukere
```

---

## 📊 Hurtigreferanse

### Pipeline for rekognosering

```bash
# 1. Kartlegging av underdomener
subfinder -d example.com -all -recursive -o subs.txt
amass enum -passive -d example.com >> subs.txt
cat subs.txt | sort -u > all_subs.txt

# 2. Oppdagelse av tilgjengelige verter
cat all_subs.txt | httpx -ports 80,443,8080,8000 -threads 200 > alive.txt

# 3. Innsamling av URL-er
cat alive.txt | gau | sort -u > urls.txt
cat alive.txt | katana -d 2 >> urls.txt

# 4. URL-er med parametere
cat urls.txt | grep '=' | urldedupe > params.txt

# 5. Sårbarhetsskanning
nuclei -l alive.txt -s critical,high -o nuclei_results.txt
```

### Nyttige kommandoer på én linje

| Formål | Kommando |
|---------|---------|
| Finn underdomener | `subfinder -d target.com -silent` |
| Kontroller tilgjengelige verter | `cat subs.txt \| httpx -silent` |
| Hent alle URL-er | `echo target.com \| gau` |
| Finn parametere | `cat urls.txt \| grep '='` |
| Test omdirigeringer | `cat urls.txt \| gf redirect` |
| Nuclei-skanning | `nuclei -l hosts.txt -s critical,high` |

### Filendelser å undersøke

| Type | Filendelser |
|------|------------|
| **Konfigurasjon** | `.env, .config, .conf, .ini, .yml, .yaml` |
| **Sikkerhetskopi** | `.bak, .old, .backup, .zip, .tar.gz` |
| **Kildekode** | `.php, .asp, .aspx, .jsp, .py, .rb` |
| **Data** | `.sql, .db, .sqlite, .json, .xml` |
| **Dokumenter** | `.pdf, .doc, .docx, .xls, .xlsx` |

---

<a id="-installasjon-av-verktøy"></a>
## 🛠️ Installasjon av verktøy

### Viktige verktøy

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

### Verktøy for XSS og omdirigering

```bash
# OR (tester for åpne omdirigeringer)
go install -v github.com/h6nt3r/or@latest

# RC (reflektert XSS)
go install -v github.com/h6nt3r/rc@latest

# CXSS (kontekstuell XSS-skanner)
go install github.com/haxshadow/cxss@latest

# XSSER
go install -v github.com/h6nt3r/xsser@latest

# GF (Grep-mønstre)
go install github.com/tomnomnom/gf@latest
```

### Hjelpeverktøy

```bash
# URO (fjerning av dupliserte URL-er)
pip3 install uro

# Arjun (oppdagelse av parametere)
pip3 install arjun

# Subzy (oppdagelse av mulige overtakelser)
go install -v github.com/PentestPad/subzy@latest
```

---

## 🎯 Tips for bug bounty

1. **Automatiser, men kontroller manuelt** – verktøy finner kandidater; du bekrefter sårbarhetene
2. **Undersøk robots.txt og sitemap.xml** – de kan avsløre skjulte stier
3. **Se på JavaScript-filer** – API-endepunkter, hemmeligheter og hardkodede påloggingsopplysninger
4. **Test alle parametere** – også skjulte (bruk Arjun)
5. **Kombiner sårbarheter** – SSRF + XSS kan gi større konsekvenser
6. **Rapportkvalitet betyr mye** – tydelig PoC, konsekvenser og reproduksjonstrinn
7. **Vær tålmodig** – gode funn tar tid

---

## 📚 Relaterte hurtigreferanser

- [Nuclei](../../Nuclei/translations/README.nb.md)
- [ffuf](../../ffuf/translations/README.nb.md)
- [Subfinder](../../Subfinder/translations/README.nb.md)
- [httpx](../../httpx/translations/README.nb.md)
- [Google Dorking](../../Google-Dorking/README.md)
- [Burp Suite](../../Burp-Suite/translations/README.nb.md)

---

<p align="center">
  <b>🐛 Lykke til med jakten!</b><br>
  <i>Bug bounty – der utholdenhet kan gi uttelling</i>
</p>
