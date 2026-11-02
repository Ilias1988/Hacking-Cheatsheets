# 🔍 Subfinder — guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de versão:** `-recursive` seleciona fontes capazes de pesquisar subdomínios em níveis adicionais; não executa, por si só, ciclos de enumeração até esgotar os resultados. `-t` controla a resolução concorrente no modo ativo; `-cs` inclui as fontes na saída JSON, e `-oD` escreve numa pasta ao usar `-dL`. Algumas opções e listas de fontes do original podem não corresponder à versão instalada. [Opções oficiais do Subfinder](https://docs.projectdiscovery.io/opensource/subfinder/usage). *Pipe* é o mecanismo que passa a saída de uma ferramenta para outra.

```
  ____        _     __ _           _           
 / ___| _   _| |__ / _(_)_ __   __| | ___ _ __ 
 \___ \| | | | '_ \ |_| | '_ \ / _` |/ _ \ '__|
  ___) | |_| | |_) |  _| | | | | (_| |  __/ |   
 |____/ \__,_|_.__/|_| |_|_| |_|\__,_|\___|_|   
                                                
   Ferramenta rápida de descoberta de subdomínios
```

<p align="center">
  <img src="https://img.shields.io/badge/Subfinder-Subdomain_Discovery-red?style=for-the-badge" alt="Subfinder">
  <img src="https://img.shields.io/badge/Passive-Recon-orange?style=for-the-badge" alt="Passive">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🌐 Ferramenta rápida de enumeração passiva de subdomínios da ProjectDiscovery</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Sintaxe básica](#-basic-syntax)
- [Deteção de subdomínios](#-subdomain-discovery)
- [Fontes de dados](#-data-sources)
- [Configuração de API](#-api-configuration)
- [Opções de saída](#-output-options)
- [Funcionalidades avançadas](#-advanced-features)
- [Fluxo de trabalho de bug bounty](#-bug-bounty-workflow)
- [Exemplos práticos](#-real-world-examples)
- [Referência rápida](#-quick-reference)
- [Conselhos e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

O **Subfinder** é uma ferramenta rápida de enumeração passiva que descobre subdomínios através de várias fontes, incluindo motores de pesquisa, sítios de partilha de texto e plataformas de informações de segurança.

### Funcionalidades principais

| Funcionalidade | Descrição |
|---------|-------------|
| **Rápido** | Desenvolvido em Go, com elevado paralelismo |
| **Passivo** | Sem interação direta com o alvo |
| **Várias fontes** | Mais de 40 fontes de dados |
| **Integração de API** | Suporte de fontes premium |
| **Adequado a cadeias de ferramentas** | Funciona com outras ferramentas |
| **Saída JSON** | Suporte de saída estruturada |

### Subfinder e outras ferramentas

| Funcionalidade | Subfinder | Amass | Assetfinder |
|---------|-----------|-------|-------------|
| **Velocidade** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| **Fontes** | 40+ | 50+ | 10+ |
| **Análise ativa** | ❌ Não | ✅ Sim | ❌ Não |
| **Suporte de API** | ✅ Sim | ✅ Sim | ❌ Não |
| **Facilidade de utilização** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |

---

<a id="-installation"></a>
## 📥 Instalação

### Com Go
```bash
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
```

### Kali Linux
```bash
sudo apt install subfinder
```

### Descarregar o binário
```bash
# Descarregar a partir das versões publicadas
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

### Verificar a instalação
```bash
subfinder -version
subfinder -h
```

---

<a id="-basic-syntax"></a>
## ⌨️ Sintaxe básica

### Sintaxe geral
```bash
subfinder [options] -d <domain>
```

### Opções essenciais

| Opção | Descrição |
|--------|-------------|
| `-d` | Domínio alvo |
| `-dL` | Ficheiro com uma lista de domínios |
| `-o` | Ficheiro de saída |
| `-oJ` | Saída JSON |
| `-silent` | Modo silencioso (apenas subdomínios) |
| `-v` | Saída detalhada |
| `-t` | Número de threads |
| `-all` | Utilizar todas as fontes |

### Primeiros passos
```bash
# Enumeração básica
subfinder -d example.com

# Saída silenciosa (lista limpa)
subfinder -d example.com -silent

# Guardar num ficheiro
subfinder -d example.com -o subdomains.txt

# Vários domínios
subfinder -dL domains.txt -o all-subs.txt
```

---

<a id="-subdomain-discovery"></a>
## 🔍 Deteção de subdomínios

### Um único domínio

```bash
# Análise básica
subfinder -d target.com

# Saída detalhada
subfinder -d target.com -v

# Silencioso (apenas subdomínios)
subfinder -d target.com -silent

# Mostrar fontes
subfinder -d target.com -cs
```

### Vários domínios

```bash
# A partir de ficheiro
subfinder -dL domains.txt

# Guardar a saída num ficheiro
subfinder -dL domains.txt -o all_subdomains.txt

# Modo silencioso para pipelines
subfinder -dL domains.txt -silent | httpx
```

### Enumeração recursiva

```bash
# Ativar recursividade (encontrar subdomínios de subdomínios)
subfinder -d target.com -recursive

# Recursividade com todas as fontes
subfinder -d target.com -recursive -all
```

---

<a id="-data-sources"></a>
## 📊 Fontes de dados

### Fontes gratuitas (predefinidas)

O Subfinder utiliza estas fontes por predefinição (sem necessidade de chave de API):
- Alienvault
- AnubisDB
- Crtsh
- Hackertarget
- Rapiddns
- Sublist3r
- ThreatCrowd
- URLScan
- Wayback Machine

### Fontes premium (é necessária uma API)

Estas fontes exigem chaves de API para obter melhores resultados:
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

### Gestão de fontes

```bash
# Listar todas as fontes
subfinder -ls

# Utilizar apenas fontes específicas
subfinder -d target.com -sources crtsh,hackertarget,alienvault

# Excluir fontes específicas
subfinder -d target.com -es shodan,securitytrails

# Utilizar todas as fontes (incluindo as lentas)
subfinder -d target.com -all

# Mostrar que fonte encontrou cada subdomínio
subfinder -d target.com -cs
```

---

<a id="-api-configuration"></a>
## 🔑 Configuração de API

### Localização do ficheiro de configuração

```bash
# Localização predefinida
~/.config/subfinder/provider-config.yaml

# Ou indicar uma localização personalizada
subfinder -d target.com -pc /path/to/config.yaml
```

### Modelo do ficheiro de configuração

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

### Obter chaves de API

| Origem | Plano gratuito | URL |
|--------|-----------|-----|
| **VirusTotal** | ✅ Sim | https://www.virustotal.com |
| **SecurityTrails** | ✅ Sim (limitado) | https://securitytrails.com |
| **Shodan** | ✅ Sim (limitado) | https://shodan.io |
| **Censys** | ✅ Sim | https://censys.io |
| **Chaos** | ✅ Gratuito (limitado) | https://chaos.projectdiscovery.io |
| **GitHub** | ✅ Gratuito | https://github.com/settings/tokens |

### Configuração gratuita recomendada

```yaml
# Mínimo recomendado para bug bounty
virustotal:
  - your_free_key
github:
  - your_github_token
chaos:
  - your_chaos_key
```

---

<a id="-output-options"></a>
## 📤 Opções de saída

### Formatos de saída

```bash
# Texto simples (predefinição)
subfinder -d target.com -o subs.txt

# Saída JSON
subfinder -d target.com -oJ -o subs.json

# Linhas JSON
subfinder -d target.com -oJL -o subs.jsonl

# Formato CSV
subfinder -d target.com -oD -o subs.csv
```

### Filtragem da saída

```bash
# Modo silencioso (saída limpa)
subfinder -d target.com -silent

# Mostrar apenas subdomínios novos (excluir os conhecidos)
subfinder -d target.com -exclude-subdomains known.txt

# Sem cores
subfinder -d target.com -nc
```

### Encadear a saída com pipes

```bash
# Encaminhar para httpx
subfinder -d target.com -silent | httpx -silent

# Encaminhar para nuclei
subfinder -d target.com -silent | httpx -silent | nuclei

# Guardar e processar
subfinder -d target.com -silent | tee subs.txt | httpx
```

---

<a id="-advanced-features"></a>
## ⚡ Funcionalidades avançadas

### Threads e desempenho

```bash
# Definir threads (predefinição: 10)
subfinder -d target.com -t 50

# Limitação da frequência de pedidos
subfinder -d target.com -rate-limit 100

# Tempo limite
subfinder -d target.com -timeout 30
```

### Seleção por correspondência e filtragem

```bash
# Selecionar padrões específicos
subfinder -d target.com -match "api,dev,stage"

# Excluir padrões
subfinder -d target.com -filter "www,mail"
```

### Opções dos resolvedores DNS

```bash
# Utilizar resolvedores personalizados
subfinder -d target.com -rL resolvers.txt

# Resolver subdomínios
subfinder -d target.com -nW
```

### Suporte de proxy

```bash
# Proxy HTTP
subfinder -d target.com -proxy http://127.0.0.1:8080

# Proxy SOCKS
subfinder -d target.com -proxy socks5://127.0.0.1:9050
```

---

<a id="-bug-bounty-workflow"></a>
## 🔄 Fluxo de trabalho de bug bounty

### Cadeia completa de reconhecimento

```bash
# Passo 1: enumeração de subdomínios
subfinder -d target.com -all -o subs.txt

# Passo 2: identificar sistemas ativos
cat subs.txt | httpx -o live.txt

# Passo 3: analisar com nuclei
nuclei -l live.txt -severity critical,high -o vulns.txt

# Comando de uma linha
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Fluxo de trabalho para vários domínios

```bash
# Enumerar todos os domínios do programa
subfinder -dL scope.txt -all -o all-subs.txt

# Processar resultados
cat all-subs.txt | httpx -silent | nuclei
```

### Monitorização contínua

```bash
# Guardar a referência inicial
subfinder -d target.com -silent > baseline.txt

# Mais tarde, procurar subdomínios novos
subfinder -d target.com -silent > current.txt
diff baseline.txt current.txt | grep ">" | cut -d" " -f2 > new-subs.txt
```

### Integração com outras ferramentas

```bash
# Com httpx (sistemas ativos)
subfinder -d target.com -silent | httpx

# Com ffuf (fuzzing de pastas)
subfinder -d target.com -silent | httpx -silent | \
  xargs -I {} ffuf -u {}/FUZZ -w wordlist.txt

# Com nmap (análise de portas)
subfinder -d target.com -silent | dnsx -a -silent | nmap -iL -

# Pipeline completo
subfinder -d target.com -silent | \
  httpx -title -status-code -tech-detect -silent | \
  tee recon-results.txt
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: enumeração básica
```bash
subfinder -d hackerone.com -silent
```

### Exemplo 2: com todas as fontes
```bash
subfinder -d target.com -all -v
```

### Exemplo 3: guardar os resultados
```bash
subfinder -d target.com -o target-subs.txt
```

### Exemplo 4: vários domínios
```bash
echo -e "target1.com\ntarget2.com" | subfinder -silent
```

### Exemplo 5: mostrar as fontes
```bash
subfinder -d target.com -cs
```

### Exemplo 6: saída JSON
```bash
subfinder -d target.com -oJ -o results.json
```

### Exemplo 7: com um proxy
```bash
subfinder -d target.com -proxy http://127.0.0.1:8080
```

### Exemplo 8: elevado desempenho
```bash
subfinder -d target.com -all -t 100 -o subs.txt
```

### Exemplo 9: cadeia completa para bug bounty
```bash
subfinder -d target.com -all -silent | \
  httpx -silent -status-code -title | \
  tee live-hosts.txt | \
  nuclei -severity critical,high
```

### Exemplo 10: deteção recursiva
```bash
subfinder -d target.com -recursive -all -silent
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Tarefa | Comando |
|------|---------|
| Análise básica | `subfinder -d target.com` |
| Saída silenciosa | `subfinder -d target.com -silent` |
| Guardar num ficheiro | `subfinder -d target.com -o subs.txt` |
| Todas as fontes | `subfinder -d target.com -all` |
| Mostrar fontes | `subfinder -d target.com -cs` |
| Vários domínios | `subfinder -dL domains.txt` |
| Saída JSON | `subfinder -d target.com -oJ` |
| Listar fontes | `subfinder -ls` |

### Opções comuns

| Opção | Descrição |
|--------|-------------|
| `-d` | Domínio alvo |
| `-dL` | Ficheiro com uma lista de domínios |
| `-o` | Ficheiro de saída |
| `-oJ` | Saída JSON |
| `-silent` | Modo silencioso |
| `-all` | Utilizar todas as fontes |
| `-cs` | Mostrar a origem de cada subdomínio |
| `-t` | Threads (linhas de execução) |
| `-recursive` | Enumeração recursiva |

### Cadeias de ferramentas comuns

| Cadeia de ferramentas | Comando |
|----------|---------|
| Sistemas ativos | `subfinder -d target.com -silent \| httpx` |
| Reconhecimento completo | `subfinder -d target.com \| httpx \| nuclei` |
| Análise de portas | `subfinder -d target.com -silent \| dnsx -a \| nmap -iL -` |

---

<a id="-tips--best-practices"></a>
## 💡 Conselhos e boas práticas

### Sugestões para bug bounty

1. **Configure as chaves de API**
   ```bash
   # Melhores resultados com APIs
   vim ~/.config/subfinder/provider-config.yaml
   ```

2. **Utilize todas as fontes para obter maior cobertura**
   ```bash
   subfinder -d target.com -all
   ```

3. **Combinar com outras ferramentas**
   ```bash
   subfinder -d target.com -silent | httpx | nuclei
   ```

4. **Monitorize novos subdomínios**
   ```bash
   # Verificação diária de novos ativos
   subfinder -d target.com -silent > today.txt
   ```

### Conselhos de desempenho

```bash
# Número elevado de threads
subfinder -d target.com -t 100 -all

# Tempo limite para fontes lentas
subfinder -d target.com -timeout 60
```

---

## ⚠️ Aviso legal

> **AVISO:** O Subfinder efetua reconhecimento passivo e não interage diretamente com os alvos. No entanto, as ferramentas seguintes da cadeia podem fazê-lo.
> 
> - ✅ Utilize-o em programas autorizados
> - ✅ Alvos de bug bounty dentro do âmbito autorizado
> - ✅ Os seus próprios domínios
> - ❌ Nunca enumere alvos sem autorização
> - ❌ Respeite o âmbito do programa
> 
> **Confirme sempre que tem autorização.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [Subfinder GitHub](https://github.com/projectdiscovery/subfinder)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentação](https://docs.projectdiscovery.io/tools/subfinder/)

### Guias de referência relacionados
- [Nuclei](../../Nuclei/translations/README.pt.md)
- [ffuf](../../ffuf/translations/README.pt.md)
- [Nmap](../../Nmap/translations/README.pt.md)

---

<p align="center">
  <b>🔍 Find Every Subdomain!</b><br>
  <i>The first step in bug bounty recon</i>
</p>
