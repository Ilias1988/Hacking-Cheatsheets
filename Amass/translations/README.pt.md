# 🌐 Amass — mapeamento da superfície de ataque

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de versão:** o original combina sintaxe e configuração de gerações anteriores do Amass, incluindo instalação v4 e modos que podem diferir nas versões recentes. A documentação oficial já apresenta a geração v5; confirme os subcomandos, opções e esquema de configuração da versão escolhida antes de reutilizar os exemplos. [Documentação do OWASP Amass](https://owasp-amass.github.io/docs/). ASN identifica um sistema autónomo; CIDR é a notação de prefixos de rede.

```
   █████╗ ███╗   ███╗ █████╗ ███████╗███████╗
  ██╔══██╗████╗ ████║██╔══██╗██╔════╝██╔════╝
  ███████║██╔████╔██║███████║███████╗███████╗
  ██╔══██║██║╚██╔╝██║██╔══██║╚════██║╚════██║
  ██║  ██║██║ ╚═╝ ██║██║  ██║███████║███████║
  ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
         Mapeamento aprofundado da superfície de ataque
```

<p align="center">
  <img src="https://img.shields.io/badge/Amass-OWASP-blue?style=for-the-badge" alt="Amass">
  <img src="https://img.shields.io/badge/Subdomain-Discovery-green?style=for-the-badge" alt="Subdomain">
  <img src="https://img.shields.io/badge/ASN-Mapping-red?style=for-the-badge" alt="ASN"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [O que é o Amass](#-what-is-amass)
- [Instalação](#-installation)
- [Modo de enumeração](#-enumeration-mode)
- [Modo de recolha de informação (intel)](#-intel-mode)
- [Modo DNS](#-dns-mode)
- [Configuração](#-configuration)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-amass"></a>
## 🎯 O que é o Amass

O **Amass** é um projeto OWASP de mapeamento de redes e descoberta de ativos externos:

- 🔍 **Enumeração de subdomínios** - A ferramenta mais abrangente
- 🏢 **Deteção de ASN** - Encontrar os intervalos de IP da organização
- 📡 **Informação de DNS** - Reconhecimento passivo e ativo
- 🗺️ **Mapeamento da superfície de ataque** - Visualizar a infraestrutura

### Porquê o Amass?

| Funcionalidade | Vantagem |
|---------|---------|
| Mais de 55 fontes de dados | Resultados mais abrangentes |
| Passivo e ativo | Escolher o nível de atividade gerada |
| Informação sobre ASN e organizações | Encontrar infraestrutura pouco visível |
| Base de dados de grafos | Visualizar relações |

---

<a id="-installation"></a>
## 🚀 Instalação

```bash
# Instalação com Go
go install -v github.com/owasp-amass/amass/v4/...@master

# Kali Linux
sudo apt install amass

# Docker
docker pull caffix/amass

# Verificar
amass -version
```

---

<a id="-enumeration-mode"></a>
## 🔍 Modo de enumeração

### Enumeração básica de subdomínios

```bash
# Enumeração passiva (sem contacto direto com o alvo)
amass enum -passive -d example.com

# Enumeração ativa (força bruta DNS)
amass enum -active -d example.com

# Força bruta com lista de palavras
amass enum -active -brute -d example.com -w wordlist.txt
```

### Opções de saída

```bash
# Guardar num ficheiro
amass enum -passive -d example.com -o subdomains.txt

# Saída JSON
amass enum -passive -d example.com -json output.json

# Guardar numa pasta
amass enum -passive -d example.com -dir ./amass_output/
```

### Vários alvos

```bash
# Vários domínios
amass enum -passive -d example.com -d target.com

# A partir de ficheiro
amass enum -passive -df domains.txt -o results.txt
```

### Enumeração avançada

```bash
# Definir o tempo limite (minutos)
amass enum -passive -d example.com -timeout 30

# Limitar as fontes
amass enum -passive -d example.com -src -include CertSpotter,Crtsh,HackerTarget

# Excluir fontes
amass enum -passive -d example.com -exclude Google,Bing

# Mostrar fontes de dados
amass enum -passive -d example.com -src
```

### Enumeração recursiva

```bash
# Ativar pesquisa recursiva
amass enum -passive -d example.com -r

# Definir a profundidade de recursividade
amass enum -passive -d example.com -max-depth 3
```

---

<a id="-intel-mode"></a>
## 🏢 Modo de recolha de informação (intel)

> O modo intel descobre domínios de raiz, ASN e intervalos CIDR associados a organizações.

### Encontrar recursos de uma organização

```bash
# Pelo nome da organização
amass intel -org "Tesla Inc"
amass intel -org "Microsoft"

# WHOIS inverso
amass intel -whois -d example.com
```

### Deteção de ASN

```bash
# Encontrar o ASN do domínio
amass intel -d example.com

# Enumerar por ASN
amass intel -asn 13335
amass intel -active -asn 13335

# Vários ASNs
amass intel -asn 13335,15169
```

### Intervalo CIDR

```bash
# Encontrar ativos num intervalo CIDR
amass intel -active -cidr 192.168.1.0/24

# Vários intervalos CIDR
amass intel -cidr 192.168.1.0/24 -cidr 10.0.0.0/8
```

### Intervalo de IP

```bash
# Intervalo de endereços IP
amass intel -active -ip 192.168.1.1-254
```

---

<a id="-dns-mode"></a>
## 📡 Modo DNS

### Consultas DNS

```bash
# Consulta DNS básica
amass dns -d example.com

# Tipos de registo específicos
amass dns -d example.com -t A,AAAA,MX,NS,TXT
```

### Resolver subdomínios

```bash
# Resolver os subdomínios descobertos
amass dns -df subdomains.txt
```

---

<a id="-configuration"></a>
## ⚙️ Configuração

### Localização do ficheiro de configuração

```bash
# Localização predefinida
~/.config/amass/config.yaml

# Indicar uma configuração personalizada
amass enum -config ./my_config.yaml -d example.com
```

### Exemplo de ficheiro de configuração

```yaml
# config.yaml
scope:
  domains:
    - example.com
    - target.com

resolvers:
  - 8.8.8.8
  - 8.8.4.4
  - 1.1.1.1

options:
  log: /tmp/amass.log
  max_dns_queries: 10000

data_sources:
  Crtsh:
  - 
  SecurityTrails:
  - credentials:
      apikey: YOUR_API_KEY
  Shodan:
  - credentials:
      apikey: YOUR_SHODAN_KEY
  VirusTotal:
  - credentials:
      apikey: YOUR_VT_KEY
```

### Fontes de dados com chaves de API

| Origem | Plano gratuito | API necessária |
|--------|-----------|--------------|
| Crt.sh | ✅ | Não |
| SecurityTrails | ✅ | Sim |
| Shodan | ✅ | Sim |
| VirusTotal | ✅ | Sim |
| Censys | ✅ | Sim |
| PassiveTotal | Limitados | Sim |
| BinaryEdge | Limitados | Sim |

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos de enumeração

| Comando | Descrição |
|---------|-------------|
| `amass enum -passive -d domain.com` | Enumeração passiva de subdomínios |
| `amass enum -active -d domain.com` | Enumeração DNS ativa |
| `amass enum -active -brute -d domain.com` | Enumerar subdomínios por força bruta |
| `amass enum -d domain.com -o out.txt` | Guardar num ficheiro |
| `amass enum -d domain.com -json out.json` | Saída JSON |

### Comandos de recolha de informação

| Comando | Descrição |
|---------|-------------|
| `amass intel -org "Company"` | Encontrar recursos pelo nome da organização |
| `amass intel -asn 12345` | Enumerar por ASN |
| `amass intel -cidr 10.0.0.0/8` | Enumerar um intervalo CIDR |
| `amass intel -whois -d domain.com` | WHOIS inverso |

### Opções essenciais

| Opção | Descrição |
|------|-------------|
| `-d` | Domínio alvo |
| `-df` | Domínios a partir de um ficheiro |
| `-o` | Ficheiro de saída |
| `-passive` | Apenas passivo (sem DNS) |
| `-active` | Ativar DNS ativo |
| `-brute` | Enumeração DNS por força bruta |
| `-src` | Mostrar a fonte dos dados |
| `-timeout` | Tempo limite em minutos |

---

## 🎯 Fluxo de trabalho de bug bounty

```bash
# 1. Enumeração passiva (segura)
amass enum -passive -d target.com -o passive_subs.txt

# 2. Enumeração ativa com força bruta
amass enum -active -brute -d target.com -w dns_wordlist.txt -o active_subs.txt

# 3. Encontrar ativos da organização
amass intel -org "Target Inc" -o org_domains.txt

# 4. Combinar os resultados
cat passive_subs.txt active_subs.txt | sort -u > all_subdomains.txt

# 5. Identificar sistemas ativos
cat all_subdomains.txt | httpx -silent > live_hosts.txt
```

---

## 📚 Recursos

- [OWASP Amass GitHub](https://github.com/owasp-amass/amass)
- [Guia de utilização do Amass](https://github.com/owasp-amass/amass/blob/master/doc/user_guide.md)

### Guias de referência relacionados
- [Metodologia de bug bounty](../../Bug-Bounty-Methodology/translations/README.pt.md)
- [Subfinder](../../Subfinder/translations/README.pt.md)
- [httpx](../../httpx/translations/README.pt.md)

---

<p align="center">
  <b>🌐 Map the Attack Surface!</b><br>
  <i>Amass - The most comprehensive subdomain tool</i>
</p>
