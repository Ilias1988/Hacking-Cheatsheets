# 🐛 Metodologia de bug bounty

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de precisão:** a regra CORS do original é demasiado simplificada: os navegadores bloqueiam a leitura de respostas com credenciais quando `Access-Control-Allow-Origin: *` é utilizado. A gravidade depende de uma prova de acesso indevido a dados, não apenas deste cabeçalho. [Explicação do MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS/Errors/CORSNotSupportingCredentials). Nesta tradução, *pipeline* significa uma cadeia de ferramentas, e *crawler/crawling* designa o rastreio automático de ligações.

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
  ██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
  ██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
  ██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
  ██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
  ╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
            Metodologia completa de investigação em bug bounty
```

<p align="center">
  <img src="https://img.shields.io/badge/Bug_Bounty-Methodology-red?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Recon-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/Vulnerability-green?style=for-the-badge" alt="Vuln">
</p>

---

## 🗺️ Visão geral do fluxo de ataque

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                  METODOLOGIA DE INVESTIGAÇÃO EM BUG BOUNTY                  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│ FASE 1: Subdomínios      FASE 2: ASN e IP         FASE 3: Sistemas ativos   │
│ ├── Subfinder            ├── Mapeamento de ASN    ├── Sondagem HTTPX        │
│ ├── Amass                ├── Recolha de IPs       ├── Deteção de portas     │
│ ├── crt.sh               └── Shodan               └── Aquatone              │
│ └── Wayback                                                                 │
│                                                                             │
│ FASE 4: Recolha de URLs  FASE 5: Vulnerabilidades FASE 6: Análise profunda  │
│ ├── Katana/Hakrawler     ├── Testes XSS           ├── Força bruta: pastas   │
│ ├── GAU                  ├── LFI/RFI              ├── Fuzzing de parâmetros │
│ └── Extrair parâmetros   ├── SSRF/CORS            └── Exposição de Git      │
│                          └── Redir. aberto                                  │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Índice

1. [Fase 1: enumeração de subdomínios](#-phase-1-subdomain-enumeration)
2. [Fase 2: descoberta de ASN e IPs](#-phase-2-asn--ip-discovery)
3. [Fase 3: descoberta de sistemas ativos](#-phase-3-live-host-discovery)
4. [Fase 4: recolha de URLs](#-phase-4-url-collection)
5. [Fase 5: procura de vulnerabilidades](#-phase-5-vulnerability-hunting)
6. [Fase 6: descoberta de pastas e ficheiros](#-phase-6-directory--file-discovery)
7. [Referência rápida](#-quick-reference)
8. [Instalação de ferramentas](#-tool-installation)

---

<a id="-phase-1-subdomain-enumeration"></a>
## 🔍 Fase 1: enumeração de subdomínios

> **Objetivo:** Encontrar o maior número possível de subdomínios: quanto maior a superfície de ataque, maior a probabilidade de encontrar vulnerabilidades.

### Ferramentas de enumeração automática

#### Subfinder
```bash
# Descoberta rápida de subdomínios através de várias fontes de dados
subfinder -d example.com -all -recursive -o subfinder.txt

# Explicação das opções:
# -all: utilizar todas as fontes (gratuitas e premium)
# -recursive: enumeração recursiva de subdomínios
# -o: ficheiro de saída
```
> 💡 **Sugestão:** O Subfinder é rápido e fiável. Utilize `-all` para obter resultados abrangentes.

#### Assetfinder
```bash
# Encontrar domínios e subdomínios associados a um alvo
assetfinder --subs-only example.com > assetfinder.txt

# Rápido e simples — adequado ao reconhecimento inicial
```
> 💡 **Sugestão:** O Assetfinder é rápido, mas menos abrangente. Utilize-o em conjunto com outras ferramentas.

#### Sublist3r
```bash
# Enumeração de subdomínios através de técnicas OSINT
sublist3r -d example.com -e baidu,yahoo,google,bing,ask,netcraft,virustotal,threatcrowd,crtsh,passivedns -v -o sublist3r.txt

# -e: indicar motores de pesquisa
# -v: saída detalhada
```

#### Amass
```bash
# Mapeamento aprofundado da superfície de ataque (a ferramenta mais abrangente!)
amass enum -passive -d example.com | cut -d']' -f 2 | awk '{print $1}' | sort -u > amass.txt

# Modo passivo — não contacta diretamente o alvo
# Utilizar -active para obter mais resultados (mas com mais tráfego)
```
> ⚠️ **Aviso:** O modo ativo do Amass pode gerar muito tráfego. Comece pelo modo passivo!

### Fontes públicas

#### Transparência de certificados (Certificate Transparency) (crt.sh)
```bash
# Encontrar subdomínios em registos públicos de certificados SSL/TLS
curl -s https://crt.sh\?q\=\example.com\&output\=json | jq -r '.[].name_value' | grep -Po '(\w+\.\w+\.\w+)$' > crtsh.txt
```
> 💡 **Porquê crt.sh?** Os certificados SSL são registos públicos: uma fonte gratuita de informação sobre subdomínios!

#### Wayback Machine
```bash
# Descoberta de subdomínios históricos em arquivos Web
curl -s "http://web.archive.org/cdx/search/cdx?url=*.example.com/*&output=text&fl=original&collapse=urlkey" | sort | sed -e 's_https*://__' -e "s/\/.*//" -e 's/:.*//' -e 's/^www\.//' | sort -u > wayback.txt
```
> 💡 **Sugestão prática:** Os subdomínios antigos podem continuar ativos, apesar de esquecidos: podem revelar problemas fáceis de identificar!

### Processamento de subdomínios

#### Juntar resultados e eliminar duplicados
```bash
# Combinar todos os ficheiros de subdomínios e remover duplicados
cat *.txt | sort -u > final_subdomains.txt

# Contar os resultados
wc -l final_subdomains.txt
```

#### Enumeração de subdomínios por força bruta com ffuf
```bash
# Descoberta de subdomínios por força bruta com listas de palavras
ffuf -u "https://FUZZ.example.com" -w wordlist.txt -mc 200,301,302

# -mc: selecionar apenas códigos de estado específicos
# Lista de palavras sugerida: /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

#### Permutações de subdomínios com Alterx
```bash
# Gerar permutações de subdomínios e resolver os nomes
subfinder -d example.com | alterx | dnsx

# Enriquecer o domínio com padrões comuns
echo example.com | alterx -enrich | dnsx

# Utilizar uma lista de palavras para gerar permutações
echo example.com | alterx -pp word=/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt | dnsx
```
> 💡 **Exemplo:** Se encontrar `dev.example.com`, o alterx gera `dev1`, `dev2`, `dev-api`, etc.

---

<a id="-phase-2-asn--ip-discovery"></a>
## 🌐 Fase 2: descoberta de ASN e IPs

> **Objetivo:** Encontrar intervalos de IP e infraestrutura associados à organização alvo.

### Mapeamento de ASN

```bash
# Descobrir endereços IP associados ao ASN do domínio
asnmap -d example.com | dnsx -silent -resp-only

# Descobrir ativos pelo nome da organização
amass intel -org "organization_name"

# Descobrir ativos num intervalo de IPs
amass intel -active -cidr 159.69.129.82/32

# Descobrir ativos pelo número de ASN
amass intel -active -asn [asnno]
```
> 💡 **Porquê pesquisar ASN?** As organizações possuem frequentemente intervalos de IP completos: procure servidores pouco visíveis!

### Recolha de endereços IP

#### VirusTotal
```bash
# Extrair endereços IP do VirusTotal
curl -s "https://www.virustotal.com/vtapi/v2/domain/report?domain=example.com&apikey=[api-key]" | jq -r '.. | .ip_address? // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### AlienVault OTX
```bash
# Obter endereços IP do AlienVault Open Threat Exchange
curl -s "https://otx.alienvault.com/api/v1/indicators/hostname/example.com/url_list?limit=500&page=1" | jq -r '.url_list[]?.result?.urlworker?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### URLScan.io
```bash
# Extrair endereços IP do URLScan.io
curl -s "https://urlscan.io/api/v1/search/?q=domain:example.com&size=10000" | jq -r '.results[]?.page?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### Pesquisa SSL no Shodan
```bash
# Encontrar endereços IP através da pesquisa de certificados SSL/TLS no Shodan
shodan search Ssl.cert.subject.CN:"example.com" 200 --fields ip_str | httpx -sc -title -server -td
```
> 💡 **Sugestão prática:** A pesquisa SSL do Shodan encontra servidores que usam o certificado SSL do alvo, incluindo servidores pouco visíveis!

---

<a id="-phase-3-live-host-discovery"></a>
## ✅ Fase 3: descoberta de sistemas ativos

> **Objetivo:** Filtrar os subdomínios para encontrar apenas sistemas ativos e acessíveis.

### Verificação de serviços HTTP com httpx

```bash
# Sondagem básica de várias portas
cat subdomain.txt | httpx -ports 80,443,8080,8000,8888 -threads 200 > subdomains_alive.txt

# Sondagem detalhada com códigos de estado, títulos e deteção de tecnologias
cat subdomain.txt | httpx -sc -title -server -td -ports 80,443,8080,8000,8888 -threads 200

# Opções:
# -sc: código de estado
# -title: título da página
# -server: cabeçalho Server
# -td: deteção de tecnologias
```

### Reconhecimento visual com Aquatone

```bash
# Capturar imagens do ecrã dos sistemas ativos
cat hosts.txt | aquatone

# Portas personalizadas
cat hosts.txt | aquatone -ports 80,443,8000,8080,8443

# Conjunto alargado de portas (abrangente)
cat hosts.txt | aquatone -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```
> 💡 **Porquê capturas de ecrã?** Identifique rapidamente aplicações relevantes, painéis de autenticação e páginas de erro.

---

<a id="-phase-4-url-collection"></a>
## 🔗 Fase 4: recolha de URLs

> **Objetivo:** Recolher o maior número possível de URLs para testar parâmetros e procurar vulnerabilidades.

### Rastreio ativo

#### Katana
```bash
# Crawler Web rápido para descoberta de URLs
katana -u livesubdomains.txt -d 2 -o urls.txt

# -d 2: profundidade de 2 níveis
# Rápido e eficiente em aplicações Web modernas
```

#### Hakrawler
```bash
# Crawler Web simples e rápido
cat urls.txt | hakrawler -u > crawled_urls.txt
```

### Recolha passiva de URLs

#### GAU (Get All URLs)
```bash
# Recolher URLs conhecidos do AlienVault OTX, Wayback Machine e Common Crawl
cat livesubdomains.txt | gau | sort -u > passive_urls.txt

# Obter URLs com código de estado 200 e remover duplicados
echo example.com | gau --mc 200 | urldedupe > urls.txt
```
> 💡 **Porquê GAU?** Encontra URLs em dados históricos que podem revelar endpoints ocultos!

#### URLFinder
```bash
# Encontrar URLs em várias fontes
urlfinder -d example.com | sort -u > urlfinder_urls.txt
```

### Extração de parâmetros

```bash
# Extrair URLs que contenham parâmetros (muito úteis para testes!)
cat allurls.txt | grep '=' | urldedupe | tee params_urls.txt

# Correspondência de padrões de parâmetros
cat allurls.txt | grep -E '\?[^=]+=.+$' | tee params_urls.txt

# Padrão GF SQLi — URLs potencialmente vulneráveis a injeção SQL
cat allurls.txt | gf sqli

# Padrão GF XSS
cat allurls.txt | gf xss

# Padrão GF LFI
cat allurls.txt | gf lfi
```
> 💡 **Padrões do GF:** Instalar `gf` e descarregue os padrões de github.com/1ndianl33t/Gf-Patterns

---

<a id="-phase-5-vulnerability-hunting"></a>
## 🔓 Fase 5: procura de vulnerabilidades

### 🔴 Deteção de XSS

#### Testes de XSS refletido
```bash
# Pipeline abrangente de testes de XSS refletido
subfinder -d "example.com" -all -recursive | \
httpx -mc 200 -silent | \
sed -E 's,https?://(www\.)?,,' | anew | \
urlfinder -all | \
iconv -f ISO-8859-1 -t UTF-8 -c | \
grep -aE '\?.*=.*(&.*)?' | \
grep -aiEv "\.(css|ico|woff|woff2|svg|ttf|eot|png|jpg|jpeg|js|json|pdf|gif|xml|webp)($|\s|\?|&|#|/|\.)" | \
awk -F'[?&=]' '!seen[$1$2]++' | anew
```

#### Testes de XSS cego (blind XSS)
```bash
# Testes de blind XSS com payloads específicos
# Utilizar serviços como XSS Hunter ou Burp Collaborator
# Inserir o payload em todos os campos de entrada, cabeçalhos e parâmetros
```
> 💡 **XSS cego (blind XSS):** Os payloads são executados mais tarde, quando um administrador consulta os dados: utilize servidores que recebam as interações de retorno (callbacks)!

### 🔴 Deteção de LFI

```bash
# Testes de LFI com FFUF e deteção do ficheiro passwd
echo "https://example.com/" | gau | gf lfi | uro | \
sed 's/=.*/=/' | qsreplace "FUZZ" | sort -u | \
xargs -I{} ffuf -u {} -w payloads/lfi.txt -c -mr "root:(x|\*|\$[^\:]*):0:0:" -v

# Testes de LFI com curl e processamento paralelo
gau example.com | gf lfi | qsreplace "/etc/passwd" | \
xargs -I% -P 25 sh -c 'curl -s "%" 2>&1 | grep -q "root:x" && echo "VULN! %"'

# Teste de LFI com httpx
echo 'https://example.com/index.php?page=' | \
httpx -paths payloads/lfi.txt -threads 50 -random-agent -mc 200 -mr "root:(x|\*|\$[^\:]*):0:0:"
```
> 💡 **Sugestão para LFI:** Experimente `/etc/passwd`, `....//....//etc/passwd`, e variantes codificadas!

### 🔴 Deteção de redirecionamentos abertos

```bash
# Encontrar parâmetros de redirecionamento
cat urls.txt | grep -Pi "returnUrl=|continue=|dest=|destination=|forward=|go=|goto=|next=|redirect=|redirect_to=|redirect_uri=|redirect_url=|return=|returnTo=|return_url=|rurl=|target=|to=|url=" | tee redirect_params.txt

# Padrão GF de redirecionamento
cat urls.txt | gf redirect | uro | sort -u | tee redirect_params.txt

# Testar parâmetros de redirecionamento
cat redirect_params.txt | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"

# Pipeline completo de testes de redirecionamento aberto
subfinder -d example.com -all | httpx -silent | gau | gf redirect | uro | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"
```

### 🔴 Deteção de SSRF

```bash
# Procurar parâmetros suscetíveis a SSRF
cat urls.txt | grep -E 'url=|uri=|redirect=|next=|data=|path=|dest=|proxy=|file=|img=|out=|continue=' | sort -u

# Procurar padrões de API/webhook
cat urls.txt | grep -i 'webhook\|callback\|upload\|fetch\|import\|api' | sort -u

# Análise de SSRF com Nuclei
cat urls.txt | nuclei -t nuclei-templates/vulnerabilities/ssrf/

# Teste básico de SSRF contra localhost
curl "https://example.com/page?url=http://127.0.0.1:80/"

# SSRF contra metadados cloud (AWS)
curl "https://example.com/api?endpoint=http://169.254.169.254/latest/meta-data/"
```
> ⚠️ **Impacto de SSRF:** Pode permitir o acesso a serviços internos, metadados da nuvem e outros recursos!

### 🔴 Testes de CORS

```bash
# Testar a configuração CORS com uma origem personalizada
curl -H "Origin: http://evil.com" -I https://example.com/api/

# Análise detalhada de CORS
curl -H "Origin: http://evil.com" -I https://example.com/api/ | \
grep -i -e "access-control-allow-origin" -e "access-control-allow-methods" -e "access-control-allow-credentials"

# Análise automática de CORS com Nuclei
cat subdomains.txt | httpx -silent | nuclei -t nuclei-templates/vulnerabilities/cors/ -o cors_results.txt
```
> 💡 **Vulnerabilidade CORS:** Se `Access-Control-Allow-Origin: *` com credenciais = crítico!

### Análise de vulnerabilidades com Nuclei

```bash
# Um alvo
nuclei -u https://example.com -bs 50 -c 30

# Vários alvos
nuclei -l live_domains.txt -bs 50 -c 30

# Apenas gravidade crítica e elevada
nuclei -l live_domains.txt -s critical,high -bs 50 -c 30

# -bs: número de alvos por lote
# -c: concorrência
```

---

<a id="-phase-6-directory--file-discovery"></a>
## 📂 Fase 6: descoberta de pastas e ficheiros

### Deteção de ficheiros sensíveis

```bash
# Extensões básicas de ficheiros sensíveis
cat allurls.txt | grep -E "\.xls|\.xml|\.xlsx|\.json|\.pdf|\.sql|\.doc|\.docx|\.pptx|\.txt|\.zip|\.tar\.gz|\.tgz|\.bak|\.7z|\.rar|\.log|\.cache|\.secret|\.db|\.backup|\.yml|\.gz|\.config|\.csv|\.yaml|\.md|\.md5"

# Expressão regular alargada para ficheiros sensíveis
cat allurls.txt | grep -E "\.(xls|xml|xlsx|json|pdf|sql|doc|docx|pptx|txt|zip|tar\.gz|tgz|bak|7z|rar|log|cache|secret|db|backup|yml|gz|config|csv|yaml|md|md5|tar|xz|7zip|p12|pem|key|crt|csr|sh|pl|py|java|class|jar|war|ear|sqlitedb|sqlite3|dbf|db3|accdb|mdb|sqlcipher|gitignore|env|ini|conf|properties|plist|cfg)$"

# Google dork para ficheiros
# site:*.example.com (ext:doc OR ext:docx OR ext:pdf OR ext:xls OR ext:xlsx OR ext:txt OR ext:xml OR ext:json OR ext:zip OR ext:sql OR ext:bak OR ext:conf OR ext:log)
```

### Enumeração de diretórios por força bruta

#### Dirsearch
```bash
# Descoberta básica de pastas
dirsearch -u https://example.com --full-url --deep-recursive -r

# Análise alargada com várias extensões
dirsearch -u https://example.com -e php,cgi,htm,html,js,txt,bak,zip,old,conf,log,asp,aspx,jsp,sql,json,xml,yml,yaml,py,rb --random-agent --recursive -R 3 -t 20 --exclude-status=404 --follow-redirects
```

#### Deteção de diretórios com ffuf
```bash
# Descoberta abrangente de pastas por força bruta com FFUF
ffuf -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt \
-u https://example.com/FUZZ \
-fc 400,401,402,403,404,429,500,501,502,503 \
-recursion -recursion-depth 2 \
-e .html,.php,.txt,.pdf,.js,.css,.zip,.bak,.old,.log,.json,.xml,.config,.env \
-ac -c \
-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0" \
-t 10
```

### Deteção de exposição de Git

```bash
# Detetar pastas .git expostas
cat domains.txt | httpx -sc -server -cl -path "/.git/" -mc 200 -location -ms "Index of" -probe

# Verificar .git/config
curl -s https://example.com/.git/config

# Extrair o repositório Git, se estiver exposto
git-dumper https://example.com/.git/ output/
```
> ⚠️ **Exposição de Git:** Pode expor código-fonte, credenciais e segredos!

### Deteção de parâmetros ocultos com Arjun

```bash
# Descoberta passiva de parâmetros
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -t 10 --rate-limit 10 --passive -m GET,POST --headers "User-Agent: Mozilla/5.0"

# Descoberta ativa de parâmetros com lista de palavras
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -m GET,POST \
-w /usr/share/wordlists/seclists/Discovery/Web-Content/burp-parameter-names.txt \
-t 10 --rate-limit 10 --headers "User-Agent: Mozilla/5.0"
```

### Apropriação de subdomínios (subdomain takeover)

```bash
# Deteção automática de possível tomada de controlo de subdomínios
subzy run --targets subdomains.txt --concurrency 100 --hide_fails --verify_ssl
```
> 💡 **Indícios de apropriação de subdomínios:** CNAME que aponta para serviços sem titular (S3, Heroku, etc.)

### Testes de WordPress

```bash
# Análise de segurança WordPress completa
wpscan --url https://example.com --disable-tls-checks --api-token YOUR_API_TOKEN \
-e at -e ap -e u --enumerate ap --plugins-detection aggressive --force

# -e at: todos os temas
# -e ap: todos os plugins
# -e u: utilizadores
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Cadeia de reconhecimento

```bash
# 1. Enumeração de subdomínios
subfinder -d example.com -all -recursive -o subs.txt
amass enum -passive -d example.com >> subs.txt
cat subs.txt | sort -u > all_subs.txt

# 2. Descoberta de sistemas ativos
cat all_subs.txt | httpx -ports 80,443,8080,8000 -threads 200 > alive.txt

# 3. Recolha de URLs
cat alive.txt | gau | sort -u > urls.txt
cat alive.txt | katana -d 2 >> urls.txt

# 4. URLs com parâmetros
cat urls.txt | grep '=' | urldedupe > params.txt

# 5. Análise de vulnerabilidades
nuclei -l alive.txt -s critical,high -o nuclei_results.txt
```

### Comandos de uma linha essenciais

| Finalidade | Comando |
|---------|---------|
| Encontrar subdomínios | `subfinder -d target.com -silent` |
| Verificar os sistemas ativos | `cat subs.txt \| httpx -silent` |
| Obter todos os URLs | `echo target.com \| gau` |
| Encontrar parâmetros | `cat urls.txt \| grep '='` |
| Testar redirecionamentos | `cat urls.txt \| gf redirect` |
| Análise com Nuclei | `nuclei -l hosts.txt -s critical,high` |

### Extensões de ficheiros a procurar

| Tipo | Extensões |
|------|------------|
| **Configuração** | `.env, .config, .conf, .ini, .yml, .yaml` |
| **Cópia de segurança** | `.bak, .old, .backup, .zip, .tar.gz` |
| **Origem** | `.php, .asp, .aspx, .jsp, .py, .rb` |
| **Dados** | `.sql, .db, .sqlite, .json, .xml` |
| **Documentos** | `.pdf, .doc, .docx, .xls, .xlsx` |

---

<a id="-tool-installation"></a>
## 🛠️ Instalação de ferramentas

### Ferramentas essenciais

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

### Ferramentas de XSS e redirecionamento

```bash
# OR (testador de redirecionamentos abertos)
go install -v github.com/h6nt3r/or@latest

# RC (XSS refletido)
go install -v github.com/h6nt3r/rc@latest

# CXSS (analisador contextual de XSS)
go install github.com/haxshadow/cxss@latest

# XSSER
go install -v github.com/h6nt3r/xsser@latest

# GF (padrões de grep)
go install github.com/tomnomnom/gf@latest
```

### Ferramentas auxiliares

```bash
# URO (eliminação de URLs duplicados)
pip3 install uro

# Arjun (deteção de parâmetros)
pip3 install arjun

# Subzy (deteção de apropriação de subdomínios)
go install -v github.com/PentestPad/subzy@latest
```

---

## 🎯 Sugestões para bug bounty

1. **Automatize, mas confirme manualmente** - As ferramentas identificam potenciais problemas; cabe-lhe confirmar as vulnerabilidades
2. **Verifique robots.txt e sitemap.xml** - Revelam frequentemente caminhos ocultos
3. **Analise os ficheiros JavaScript** - Endpoints de API, segredos e credenciais incorporadas no código
4. **Teste todos os parâmetros** - Incluindo os ocultos (utilize Arjun)
5. **Encadeie vulnerabilidades** - SSRF + XSS = impacto mais elevado
6. **A qualidade do relatório conta** - Prova de conceito clara, impacto e passos de reprodução
7. **Tenha paciência** - Encontrar vulnerabilidades relevantes leva tempo

---

## 📚 Guias de referência relacionados

- [Nuclei](../../Nuclei/translations/README.pt.md)
- [ffuf](../../ffuf/translations/README.pt.md)
- [Subfinder](../../Subfinder/translations/README.pt.md)
- [httpx](../../httpx/translations/README.pt.md)
- [Google Dorking](../../Google-Dorking/README.md)
- [Burp Suite](../../Burp-Suite/translations/README.pt.md)

---

<p align="center">
  <b>🐛 Boa procura de vulnerabilidades!</b><br>
  <i>Bug bounty: a persistência pode ser recompensada</i>
</p>
