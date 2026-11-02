# 🌐 Shodan — Guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ███████╗██╗  ██╗ ██████╗ ██████╗  █████╗ ███╗   ██╗
  ██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔══██╗████╗  ██║
  ███████╗███████║██║   ██║██║  ██║███████║██╔██╗ ██║
  ╚════██║██╔══██║██║   ██║██║  ██║██╔══██║██║╚██╗██║
  ███████║██║  ██║╚██████╔╝██████╔╝██║  ██║██║ ╚████║
  ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝
                                                      
   O motor de pesquisa para dispositivos ligados à Internet
```

<p align="center">
  <img src="https://img.shields.io/badge/Shodan-IoT_Search-red?style=for-the-badge" alt="Shodan">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Reconnaissance-blue?style=for-the-badge" alt="Reconhecimento">
  <img src="https://img.shields.io/badge/IoT-Security-green?style=for-the-badge" alt="IoT">
</p>

<p align="center">
  <b>🔍 Motor de pesquisa para dispositivos, servidores e infraestrutura ligados à Internet</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Introdução](#-introduction)
- [Primeiros passos](#-getting-started)
- [Filtros básicos](#-basic-filters)
- [Filtros de rede](#-network-filters)
- [Filtros SSL/TLS](#-ssltls-filters)
- [Filtros HTTP](#-http-filters)
- [Serviços vulneráveis](#-vulnerable-services)
- [IoT e ICS/SCADA](#-iot--icsscada)
- [Bases de dados](#-databases)
- [Câmaras web e multimédia](#-webcams--media)
- [Serviços cloud](#-cloud-services)
- [Consultas para bug bounty](#-bug-bounty-queries)
- [Shodan CLI](#-shodan-cli)
- [Referência rápida](#-quick-reference)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

**Shodan** é um motor de pesquisa que permite encontrar tipos específicos de computadores, servidores e dispositivos IoT ligados à Internet, utilizando vários filtros.

IoT significa Internet das Coisas. ICS/SCADA são sistemas de controlo industrial e de supervisão. ISP é um fornecedor de acesso à Internet; ASN identifica um sistema autónomo de rede. O Shodan pesquisa banners, isto é, registos de informações recolhidas junto dos serviços. Ver os [fundamentos oficiais das consultas](https://help.shodan.io/the-basics/search-query-fundamentals).

### O que podes encontrar?

| Categoria | Exemplos |
|----------|----------|
| **Servidores** | Servidores web, FTP, SSH e bases de dados |
| **Dispositivos IoT** | Câmaras, routers e dispositivos inteligentes |
| **ICS/SCADA** | Sistemas de controlo industrial |
| **Cloud** | Instâncias AWS, Azure e GCP |
| **Vulnerabilidades** | Serviços expostos e credenciais predefinidas |

### URLs do Shodan

| Serviço | URL |
|---------|-----|
| **Web Search (pesquisa web)** | https://www.shodan.io |
| **Maps (mapas)** | https://maps.shodan.io |
| **Exploits (explorações)** | https://exploits.shodan.io |
| **Images (imagens)** | https://images.shodan.io |

---

<a id="-getting-started"></a>
## 🚀 Primeiros passos

### Criar uma conta

1. Aceder a https://www.shodan.io
2. Criar uma conta gratuita
3. Obter a chave de API nas definições da conta

### Planos de adesão

> **Preços e limites históricos:** os valores e a indicação de «acesso ilimitado» na tabela são os do original e não constituem uma tabela comercial atual. Confirma os preços no [guia oficial da plataforma](https://book.shodan.io/getting-started/platform/) e os limites na [explicação oficial dos créditos](https://help.shodan.io/the-basics/credit-types-explained).

| Plano | Preço | Funcionalidades |
|------|-------|----------|
| **Free (gratuito)** | $0 | Pesquisa básica, resultados limitados |
| **Membership (adesão)** | $49 | Mais resultados, downloads e linha de comandos |
| **Small Business** | $299/mo | Mais créditos e monitorização |
| **Corporate** | $899/mo | Acesso ilimitado |

### Chave de API

```bash
# Obter em: https://account.shodan.io
# Exportar para utilização na linha de comandos
export SHODAN_API_KEY="YOUR_API_KEY"
```

---

<a id="-basic-filters"></a>
## 🔧 Filtros básicos

### Pesquisa de texto

```
# Pesquisa simples
apache
nginx
Microsoft-IIS

# Pesquisa de produto
product:apache
product:nginx
product:openssh
```

### País e cidade

```
# País (ISO 3166-1 alpha-2)
country:US
country:DE
country:RU
country:CN
country:GR                      # Grécia

# Cidade
city:Athens
city:"New York"
city:London
city:Tokyo

# Combinar
country:US city:Miami
country:GR city:Thessaloniki
```

### Organização e ISP

```
# Organização
org:"Google"
org:"Amazon"
org:"Microsoft"
org:"Facebook"

# ISP
isp:"Comcast"
isp:"Verizon"
isp:"AT&T"
```

### Nome de máquina e domínio

```
# O nome de máquina contém
hostname:google.com
hostname:.gov
hostname:.edu
hostname:target.com

# Domínio específico
ssl.cert.subject.cn:target.com
```

### Sistema operativo

```
os:"Windows"
os:"Linux"
os:"Ubuntu"
os:"Windows Server 2019"
os:"Windows 7"
os:"FreeBSD"
```

---

<a id="-network-filters"></a>
## 🌐 Filtros de rede

### Porta

```
# Porta específica
port:22                         # SSH
port:21                         # FTP
port:23                         # Telnet
port:80                         # HTTP
port:443                        # HTTPS
port:3389                       # RDP
port:3306                       # MySQL
port:5432                       # PostgreSQL
port:27017                      # MongoDB
port:6379                       # Redis
port:9200                       # Elasticsearch

# Várias portas
port:22,80,443
```

### IP e rede

> **Contexto do exemplo:** os intervalos privados apresentados não são redes públicas normalmente observáveis pelo motor público do Shodan. São exemplos de notação CIDR, não alvos dos quais se esperem resultados públicos completos.

```
# IP específico
ip:8.8.8.8

# Intervalo de IPs (CIDR)
net:192.168.0.0/24
net:10.0.0.0/8
net:172.16.0.0/12

# ASN
asn:AS15169                     # Google
asn:AS16509                     # Amazon
asn:AS8075                      # Microsoft
```

### Localização geográfica

```
# Coordenadas geográficas
geo:37.9838,23.7275             # Coordenadas de Atenas
geo:40.7128,-74.0060,10         # Nova Iorque com raio de pesquisa

# Região ou estado
region:California
region:"New York"
```

---

<a id="-ssltls-filters"></a>
## 🔐 Filtros SSL/TLS

### Informações do certificado

> **Precisão do original:** a consulta que coloca `ssl.cert.subject.cn` como valor de `ssl.cert.issuer.cn` não compara dois campos de um certificado; trata o segundo texto como valor de pesquisa. Portanto, não demonstra um filtro válido para certificados autoassinados. Ver a [estrutura oficial dos dados SSL](https://datapedia.shodan.io/property/ssl.html) e a [sintaxe de consultas](https://help.shodan.io/the-basics/search-query-fundamentals).

```
# Titular do certificado (subject)
ssl.cert.subject.cn:target.com
ssl.cert.subject.cn:*.target.com

# Emissor do certificado
ssl.cert.issuer.cn:"Let's Encrypt"
ssl.cert.issuer.cn:"DigiCert"

# Certificados autoassinados
ssl.cert.issuer.cn:ssl.cert.subject.cn
```

### Versões SSL

```
# Versões SSL (vulneráveis)
ssl.version:sslv2
ssl.version:sslv3
ssl.version:tlsv1
ssl.version:tlsv1.1

# Versões seguras
ssl.version:tlsv1.2
ssl.version:tlsv1.3
```

### Validade do certificado

```
# Certificados expirados
ssl.cert.expired:true

# Certificados prestes a expirar
ssl.cert.expires:2024
```

### Vulnerabilidades SSL

```
# Heartbleed
vuln:CVE-2014-0160

# POODLE
ssl.version:sslv3

# DROWN
ssl.version:sslv2
```

---

<a id="-http-filters"></a>
## 🌍 Filtros HTTP

### Estado e cabeçalhos HTTP

```
# Código de estado HTTP
http.status:200
http.status:401
http.status:403
http.status:500

# Título HTTP
http.title:"Dashboard"
http.title:"Login"
http.title:"Admin"
http.title:"Index of"

# Conteúdo HTML
http.html:"password"
http.html:"admin"
http.html:"login"
```

### Tecnologias web

```
# Cabeçalho Server
http.server:apache
http.server:nginx
http.server:Microsoft-IIS

# Versões específicas
http.server:"Apache/2.4.49"     # Vulnerável
http.server:"nginx/1.18"

# Hash do favicon
http.favicon.hash:116323821     # Favicon específico
http.favicon.hash:-1137975059   # Jenkins
```

### Componentes

```
# Frameworks web
http.component:"WordPress"
http.component:"Joomla"
http.component:"Drupal"
http.component:"Laravel"
http.component:"Django"

# Tecnologias
http.component:"PHP"
http.component:"jQuery"
http.component:"Bootstrap"
```

---

<a id="-vulnerable-services"></a>
## ⚠️ Serviços vulneráveis

### CVEs conhecidas

```
# Pesquisar por CVE
vuln:CVE-2021-44228            # Log4Shell
vuln:CVE-2021-26855            # ProxyLogon
vuln:CVE-2019-0708             # BlueKeep
vuln:CVE-2017-0144             # EternalBlue
vuln:CVE-2014-0160             # Heartbleed
vuln:CVE-2014-6271             # Shellshock

# Várias CVEs
vuln:CVE-2021-44228,CVE-2021-45046
```

### Credenciais predefinidas

```
# Palavras-passe predefinidas
"default password"
"admin" "password"
http.title:"Login" "admin"

# Valores predefinidos específicos
"admin:admin"
"root:root"
"admin:123456"
```

### Serviços abertos ou expostos

> **Interpretação dos resultados:** pesquisar um título de login ou excluir a palavra «authentication» não comprova que o serviço está sem autenticação. Um banner ou uma associação a CVE também não substitui a validação técnica autorizada; os dados representam observações recolhidas.

```
# FTP anónimo
port:21 "Anonymous access granted"

# Telnet aberto
port:23 -authentication

# MongoDB aberto
port:27017 "MongoDB Server Information"

# Redis aberto
port:6379 -authentication

# Elasticsearch aberto
port:9200 "cluster_name"

# Memcached aberto
port:11211 "STAT"
```

### Painéis expostos

```
# phpMyAdmin
http.title:"phpMyAdmin"

# cPanel
http.title:"cPanel"

# Webmin
http.title:"Webmin"

# Jenkins (sem autenticação)
http.title:"Dashboard [Jenkins]"

# Grafana
http.title:"Grafana"
```

---

<a id="-iot--icsscada"></a>
## 🏭 IoT e ICS/SCADA

### Sistemas de controlo industrial

```
# ICS em geral
tag:ics

# Modbus
port:502

# Siemens S7
port:102

# DNP3
port:20000

# BACnet
port:47808

# EtherNet/IP
port:44818

# Niagara Fox
port:1911
```

### Sistemas SCADA

```
# SCADA
tag:scada

# SCADA específico
"SCADA" port:502
"PLC" country:US
"Siemens" port:102
"Schneider"
```

### Dispositivos IoT

```
# IoT em geral
tag:iot

# Routers
"router" port:80
http.title:"Router"

# Impressoras
port:9100
"printer"
http.title:"HP"

# Casa inteligente
"smart home"
"IoT Gateway"
```

### Dispositivos de rede

```
# Cisco
"cisco" product:cisco

# MikroTik
product:mikrotik
http.title:"RouterOS"

# Ubiquiti
product:ubiquiti

# Fortinet
product:fortigate
```

---

<a id="-databases"></a>
## 💾 Bases de dados

### MySQL

```
port:3306
port:3306 "MySQL"
port:3306 "unauthorized"
product:mysql
```

### PostgreSQL

```
port:5432
port:5432 "PostgreSQL"
product:postgresql
```

### MongoDB

```
port:27017
port:27017 "MongoDB Server Information"
port:27017 "totalSize"
product:mongodb
"MongoDB Server Information" -authentication
```

### Redis

```
port:6379
port:6379 "redis"
port:6379 -authentication
product:redis
```

### Elasticsearch

```
port:9200
port:9200 "elastic"
port:9200 "cluster_name"
port:9200 "You Know, for Search"
product:elasticsearch
```

### CouchDB

```
port:5984
port:5984 "couchdb"
"couchdb" "Welcome"
```

### Memcached

```
port:11211
port:11211 "STAT"
product:memcached
```

### Cassandra

```
port:9042
port:9160
product:cassandra
```

---

<a id="-webcams--media"></a>
## 📹 Câmaras web e multimédia

### Câmaras IP

```
# Câmaras web genéricas
webcam
"webcam" country:US

# Marcas de câmaras
"hikvision"
"dahua"
"axis"
"foscam"
"netcam"

# Fluxos RTSP
port:554 has_screenshot:true
"rtsp" port:554
```

### Sistemas DVR/NVR

```
"dvr" port:80
"nvr" 
http.title:"DVR"
http.title:"NVR"
"Network Video Recorder"
```

### Transmissão de multimédia

```
# Transmissão de multimédia com VLC
"VLC media player"

# Servidores multimédia
product:plex
"Plex Media Server"
```

### Interfaces específicas de câmaras

```
# Hikvision
http.title:"DVRDVS-Webs"
"App-webs" port:80

# Dahua
http.title:"WEB SERVICE"

# Axis
http.title:"AXIS"
```

---

<a id="-cloud-services"></a>
## ☁️ Serviços cloud

### Amazon AWS

```
# AWS em geral
org:"Amazon"
asn:AS16509

# Buckets S3
"AmazonS3"

# EC2
"ec2" org:"Amazon"

# Serviços AWS
http.title:"AWS"
```

### Microsoft Azure

```
org:"Microsoft"
asn:AS8075
hostname:azure.com
hostname:cloudapp.azure.com
```

### Google Cloud

```
org:"Google"
asn:AS15169
hostname:googleusercontent.com
```

### DigitalOcean

```
org:"DigitalOcean"
asn:AS14061
```

### Painéis cloud expostos

```
# Painel de controlo Kubernetes
http.title:"Kubernetes Dashboard"

# Docker
product:docker
port:2375

# Jenkins
http.title:"Jenkins"

# GitLab
http.title:"GitLab"
```

---

<a id="-bug-bounty-queries"></a>
## 🐛 Consultas para bug bounty

### Reconhecimento da organização

```
# Organização alvo
org:"Target Company"
ssl.cert.subject.cn:target.com
hostname:target.com

# Consulta de ASN
asn:AS12345
net:1.2.3.0/24
```

### Encontrar serviços expostos

```
# Todos os serviços do alvo
org:"Target" port:22,80,443,3306,5432,27017

# Painéis de administração
org:"Target" http.title:"admin"

# Páginas de início de sessão
org:"Target" http.title:"login"
```

### Descoberta de subdomínios

```
ssl.cert.subject.cn:*.target.com
ssl.cert.subject.cn:target.com
hostname:target.com
```

### Serviços vulneráveis

```
# Verificar CVEs
org:"Target" vuln:CVE-2021-44228

# Credenciais predefinidas
org:"Target" "default password"

# Bases de dados abertas
org:"Target" port:27017,6379,9200
```

### Consulta completa para bug bounty

> **Sintaxe do original:** não pressuponhas que parênteses e `||` seguem a gramática de outra linguagem. Confirma as combinações suportadas na [documentação do Shodan](https://help.shodan.io/the-basics/search-query-fundamentals). Os exemplos com `vuln:` sem valor e as datas de 2024 são incompletos ou históricos, não consultas universais atuais.

```
# Reconhecimento completo
ssl.cert.subject.cn:target.com || hostname:target.com || org:"Target Company"

# Com filtros
(ssl.cert.subject.cn:target.com || hostname:target.com) port:22,80,443,3306,5432
```

---

<a id="-shodan-cli"></a>
## 💻 Shodan CLI

### Instalação

```bash
# Instalar através do pip
pip install shodan

# Inicializar com a chave de API
shodan init YOUR_API_KEY
```

### Comandos básicos

```bash
# Informações da conta
shodan info

# Pesquisar
shodan search "apache"

# Pesquisar com filtros
shodan search "port:22 country:US"

# Contar resultados
shodan count "nginx"

# Descarregar resultados
shodan download results.json.gz "apache"

# Processar os resultados
shodan parse results.json.gz

# Obter informações da máquina
shodan host 8.8.8.8
```

### Linha de comandos avançada

```bash
# Informações do domínio
shodan domain target.com

# Analisar (requer créditos)
shodan scan submit 1.2.3.4

# Honeyscore (detetar honeypots)
shodan honeyscore 1.2.3.4

# Informações do meu IP
shodan myip

# Estatísticas
shodan stats "apache" --facets country,org
```

### Utilização da API (Python)

```python
import shodan

api = shodan.Shodan('YOUR_API_KEY')

# Pesquisar
results = api.search('apache')
for result in results['matches']:
    print(result['ip_str'], result['port'])

# Consulta de máquina
host = api.host('8.8.8.8')
print(host['ip_str'], host['ports'])
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Filtros essenciais

| Filtro | Descrição | Exemplo |
|--------|-------------|---------|
| `port:` | Número da porta | `port:22` |
| `country:` | Código do país | `country:US` |
| `city:` | Nome da cidade | `city:London` |
| `org:` | Organização | `org:"Google"` |
| `hostname:` | Nome da máquina | `hostname:google.com` |
| `net:` | Intervalo de IPs/CIDR | `net:10.0.0.0/8` |
| `os:` | Sistema operativo | `os:"Windows"` |
| `product:` | Nome do produto | `product:nginx` |
| `version:` | Version | `version:"2.4.49"` |
| `vuln:` | Identificador CVE | `vuln:CVE-2021-44228` |

### Filtros HTTP

| Filtro | Descrição | Exemplo |
|--------|-------------|---------|
| `http.title:` | Título da página | `http.title:"Admin"` |
| `http.status:` | Estado HTTP | `http.status:200` |
| `http.server:` | Cabeçalho Server | `http.server:nginx` |
| `http.html:` | Conteúdo HTML | `http.html:"login"` |
| `http.component:` | Tecnologia | `http.component:"WordPress"` |

### Filtros SSL

| Filtro | Descrição | Exemplo |
|--------|-------------|---------|
| `ssl.cert.subject.cn:` | CN do certificado | `ssl.cert.subject.cn:target.com` |
| `ssl.cert.issuer.cn:` | CN do emissor | `ssl.cert.issuer.cn:"DigiCert"` |
| `ssl.version:` | Versão SSL | `ssl.version:sslv3` |
| `ssl.cert.expired:` | Certificado expirado | `ssl.cert.expired:true` |

### 10 consultas de referência do Shodan

| # | Consulta | Finalidade |
|---|-------|---------|
| 1 | `port:22 country:US` | SSH nos Estados Unidos |
| 2 | `port:3389 country:US` | RDP nos Estados Unidos |
| 3 | `port:27017 -authentication` | MongoDB aberto |
| 4 | `vuln:CVE-2021-44228` | Log4Shell |
| 5 | `http.title:"Dashboard"` | Painéis de controlo |
| 6 | `"default password"` | Credenciais predefinidas |
| 7 | `port:9200 "elastic"` | Elasticsearch |
| 8 | `webcam` | Câmaras IP |
| 9 | `product:apache vuln:` | Apache vulnerável |
| 10 | `ssl.cert.expired:true` | Certificados expirados |

---

## 💡 Dicas e boas práticas

### Dicas para bug bounty

1. **Começar pela pesquisa de certificados**
   ```
   ssl.cert.subject.cn:target.com
   ```

2. **Verificar todas as portas**
   ```
   hostname:target.com port:22,80,443,3306,5432
   ```

3. **Procurar serviços expostos**
   ```
   org:"Target" (port:27017 || port:9200 || port:6379)
   ```

4. **Verificar vulnerabilidades**
   ```
   org:"Target" vuln:
   ```

### Combinar filtros

```
# Consultas complexas
port:22 country:US org:"Target" os:"Ubuntu"
http.title:"admin" -http.title:"Login" country:US
ssl.cert.subject.cn:target.com port:443 http.status:200
```

---

## ⚠️ Aviso legal

> **AVISO:** O Shodan apresenta informações acessíveis publicamente. No entanto:
> 
> - ✅ Utilizar para reconhecimento autorizado
> - ✅ Utilizar em alvos abrangidos pelo âmbito de programas de bug bounty
> - ✅ Comunicar vulnerabilidades de forma responsável
> - ❌ Nunca explorar as vulnerabilidades encontradas
> - ❌ Não aceder a sistemas sem autorização
> - ❌ Respeitar a privacidade e a legislação
> 
> **Realizar análises sem autorização é ilegal!**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [Shodan](https://www.shodan.io)
- [Ajuda do Shodan](https://help.shodan.io)
- [Shodan CLI](https://cli.shodan.io)
- [Shodan API](https://developer.shodan.io)

### Guias de referência relacionados
- [Google Dorking](../../Google-Dorking/translations/README.pt.md)
- [GitHub Dorking](../../GitHub-Dorking/translations/README.pt.md)
- [Nmap](../../Nmap/README.md)

---

<p align="center">
  <b>🌐 Descobre a Internet oculta!</b><br>
  <i>Domina o Shodan para reconhecimento</i>
</p>
