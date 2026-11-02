# 🎯 Nuclei — guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Notas de versão e precisão:** os números de modelos são os do original e variam. `-ut` atualiza os modelos; a atualização do motor usa `-up`. Caminhos e opções de exportação devem ser confirmados para a versão instalada. O exemplo «Verificar um cabeçalho» repete a chave `matchers:` no mesmo mapeamento: YAML exige chaves únicas, pelo que o exemplo precisa de revisão antes de ser utilizado. [Opções oficiais](https://docs.projectdiscovery.io/opensource/nuclei/running), [especificação YAML](https://yaml.org/spec/1.2.2/#mapping) e [matchers do Nuclei](https://docs.projectdiscovery.io/templates/reference/matchers). Um *template* é um modelo de verificação; um *workflow* encadeia verificações.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _            _      _ 
 | \ | |_   _  ___| | ___(_)
 |  \| | | | |/ __| |/ _ \ |
 | |\  | |_| | (__| |  __/ |
 |_| \_|\__,_|\___|_|\___|_|
                            
    Analisador rápido baseado em modelos
```

<p align="center">
  <img src="https://img.shields.io/badge/Nuclei-Vulnerability_Scanner-red?style=for-the-badge" alt="Nuclei">
  <img src="https://img.shields.io/badge/9000+-Templates-orange?style=for-the-badge" alt="Templates">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🔥 O analisador de vulnerabilidades baseado em modelos de referência para investigadores de bug bounty</b>
</p>

---

## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Sintaxe básica](#-basic-syntax)
- [Categorias de modelos](#-template-categories)
- [Análise de alvos](#-scanning-targets)
- [Seleção de modelos](#-template-selection)
- [Opções de saída](#-output-options)
- [Funcionalidades avançadas](#-advanced-features)
- [Modelos personalizados](#-custom-templates)
- [Fluxo de trabalho de bug bounty](#-bug-bounty-workflow)
- [Exemplos práticos](#-real-world-examples)
- [Referência rápida](#-quick-reference)
- [Conselhos e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

O **Nuclei** é um analisador rápido de vulnerabilidades baseado em modelos, desenvolvido pela ProjectDiscovery. É uma ferramenta de referência para investigadores de bug bounty e de segurança.

### Funcionalidades principais

| Funcionalidade | Descrição |
|---------|-------------|
| **Mais de 9000 modelos** | CVEs, painéis, configurações incorretas e apropriação de subdomínios |
| **Rápido e eficiente** | Análise em paralelo com limitação da frequência dos pedidos |
| **Baseado em modelos** | Modelos YAML que facilitam a personalização |
| **Mantido pela comunidade** | Modelos atualizados continuamente |
| **Vários protocolos** | HTTP, DNS, TCP, SSL e navegação sem interface gráfica (headless) |
| **Integração simples** | Funciona com outras ferramentas (httpx, subfinder) |

### Tipos de modelos

| Tipo | Descrição | Quantidade |
|------|-------------|-------|
| **CVE** | Vulnerabilidades conhecidas | 2000+ |
| **Painéis expostos** | Painéis de administração e de controlo | 500+ |
| **Configurações incorretas** | Configurações de segurança incorretas | 300+ |
| **Autenticações predefinidas** | Credenciais predefinidas | 200+ |
| **Apropriação de subdomínios** | Apropriação de subdomínios (subdomain takeover) | 100+ |
| **Tecnologias** | Deteção de tecnologias | 1000+ |
| **Exposição de informação** | Exposição de ficheiros sensíveis | 500+ |

---

<a id="-installation"></a>
## 📥 Instalação

### Com Go (recomendado)
```bash
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest
```

### Descarregar o binário
```bash
# Descarregar a partir das versões publicadas
https://github.com/projectdiscovery/nuclei/releases

# Linux
wget https://github.com/projectdiscovery/nuclei/releases/download/v3.x.x/nuclei_3.x.x_linux_amd64.zip
unzip nuclei_3.x.x_linux_amd64.zip
sudo mv nuclei /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/nuclei:latest
docker run -it projectdiscovery/nuclei -h
```

### Kali Linux
```bash
sudo apt install nuclei
```

### Atualizar os modelos
```bash
# Atualizar nuclei e modelos
nuclei -ut

# Atualizar apenas os modelos
nuclei -update-templates
```

### Verificar a instalação
```bash
nuclei -version
nuclei -tl              # Listar modelos
```

---

<a id="-basic-syntax"></a>
## ⌨️ Sintaxe básica

### Sintaxe geral
```bash
nuclei [options] -target <url>
```

### Opções essenciais

| Opção | Descrição |
|--------|-------------|
| `-u` | URL de um único alvo |
| `-l` | Ficheiro com uma lista de URLs |
| `-t` | Modelo(s) a executar |
| `-tags` | Modelos com etiquetas específicas |
| `-severity` | Filtrar por gravidade |
| `-o` | Ficheiro de saída |
| `-silent` | Modo silencioso |
| `-v` | Saída detalhada |

### Primeiros passos
```bash
# Analisar um alvo
nuclei -u https://example.com

# Analisar uma lista de alvos
nuclei -l urls.txt

# Analisar com modelos específicos
nuclei -u https://example.com -t cves/

# Analisar com tags
nuclei -u https://example.com -tags cve,rce
```

---

<a id="-template-categories"></a>
## 📂 Categorias de modelos

### Modelos de CVE

```bash
# Todos os modelos de CVEs
nuclei -u target.com -t cves/

# Ano de CVE específico
nuclei -u target.com -t cves/2024/
nuclei -u target.com -t cves/2023/

# Apenas CVEs de gravidade crítica
nuclei -u target.com -t cves/ -severity critical

# CVE específica
nuclei -u target.com -t cves/2023/CVE-2023-XXXXX.yaml
```

### Painéis expostos

```bash
# Deteção de todos os painéis
nuclei -u target.com -t exposed-panels/

# Painéis específicos
nuclei -u target.com -tags phpmyadmin
nuclei -u target.com -tags jenkins
nuclei -u target.com -tags grafana
nuclei -u target.com -tags kibana
```

### Configurações incorretas

```bash
# Todas as configurações incorretas
nuclei -u target.com -t misconfiguration/

# Tipos específicos
nuclei -u target.com -tags cors
nuclei -u target.com -tags headers
nuclei -u target.com -tags http-missing-security-headers
```

### Autenticações predefinidas

```bash
# Todas as credenciais predefinidas
nuclei -u target.com -t default-logins/

# Fabricantes específicos
nuclei -u target.com -tags tomcat-default
nuclei -u target.com -tags apache-default
```

### Apropriação de subdomínios (subdomain takeover)

```bash
# Todas as verificações de takeover
nuclei -u target.com -t takeovers/

# Fornecedores específicos
nuclei -u target.com -tags aws-takeover
nuclei -u target.com -tags azure-takeover
nuclei -u target.com -tags github-takeover
```

### Deteção de tecnologias

```bash
# Deteção de tecnologias
nuclei -u target.com -t technologies/

# Deteção de CMS
nuclei -u target.com -tags tech,wordpress
nuclei -u target.com -tags tech,joomla
```

### Exposição de informação

```bash
# Exposição de ficheiros sensíveis
nuclei -u target.com -t exposures/

# Exposições específicas
nuclei -u target.com -tags config
nuclei -u target.com -tags backup
nuclei -u target.com -tags logs
nuclei -u target.com -tags git
```

---

<a id="-scanning-targets"></a>
## 🎯 Análise de alvos

### Alvo individual

```bash
nuclei -u https://example.com
nuclei -u https://example.com:8080
```

### Vários alvos

```bash
# A partir de ficheiro
nuclei -l targets.txt

# A partir da entrada padrão (pipeline)
cat targets.txt | nuclei
echo "https://example.com" | nuclei

# Vários URLs na mesma linha
nuclei -u https://site1.com -u https://site2.com
```

### Com outras ferramentas

```bash
# Com subfinder (subdomínios)
subfinder -d example.com | nuclei

# Com httpx (sistemas ativos)
subfinder -d example.com | httpx | nuclei

# Com katana (URLs recolhidos pelo crawler)
katana -u https://example.com | nuclei

# Pipeline completo de reconhecimento
subfinder -d example.com -silent | httpx -silent | nuclei -silent
```

---

<a id="-template-selection"></a>
## 🏷️ Seleção de modelos

### Por caminho do modelo

```bash
# Um modelo
nuclei -u target.com -t cves/2023/CVE-2023-1234.yaml

# Pasta de modelos
nuclei -u target.com -t cves/2023/
nuclei -u target.com -t exposed-panels/

# Várias pastas
nuclei -u target.com -t cves/ -t exposed-panels/

# Caminho personalizado de modelos
nuclei -u target.com -t /path/to/custom/templates/
```

### Por etiquetas

```bash
# Uma tag
nuclei -u target.com -tags cve

# Várias tags (OU)
nuclei -u target.com -tags cve,rce,sqli

# Tags comuns
nuclei -u target.com -tags xss
nuclei -u target.com -tags ssrf
nuclei -u target.com -tags lfi
nuclei -u target.com -tags rce
nuclei -u target.com -tags injection
```

### Por gravidade

```bash
# Um nível de gravidade
nuclei -u target.com -severity critical
nuclei -u target.com -severity high
nuclei -u target.com -severity medium
nuclei -u target.com -severity low
nuclei -u target.com -severity info

# Vários níveis de gravidade
nuclei -u target.com -severity critical,high

# Excluir níveis de gravidade
nuclei -u target.com -exclude-severity info,low
```

### Excluir modelos

```bash
# Excluir modelos específicos
nuclei -u target.com -exclude-templates cves/2020/

# Excluir por tags
nuclei -u target.com -exclude-tags dos,fuzz

# Excluir por ID
nuclei -u target.com -exclude-id CVE-2021-1234
```

---

<a id="-output-options"></a>
## 📤 Opções de saída

### Formatos de saída

```bash
# Saída padrão
nuclei -u target.com -o results.txt

# Saída JSON
nuclei -u target.com -json -o results.json

# JSON Linhas
nuclei -u target.com -jsonl -o results.jsonl

# Markdown
nuclei -u target.com -markdown-export report/
```

### Filtragem da saída

```bash
# Modo silencioso (apenas resultados)
nuclei -u target.com -silent

# Sem cores
nuclei -u target.com -nc

# Detalhado
nuclei -u target.com -v

# Depuração
nuclei -u target.com -debug
```

### Opções de exportação

```bash
# SARIF (GitHub)
nuclei -u target.com -sarif-export results.sarif

# Relatório Markdown
nuclei -u target.com -me reports/
```

---

<a id="-advanced-features"></a>
## ⚡ Funcionalidades avançadas

### Limitação da frequência de pedidos

```bash
# Pedidos por segundo
nuclei -u target.com -rate-limit 100

# Número de alvos por lote
nuclei -u target.com -bulk-size 25

# Concorrência
nuclei -u target.com -c 50

# Concorrência de modelos
nuclei -u target.com -rl 100 -c 50
```

### Suporte de proxy

```bash
# Proxy HTTP
nuclei -u target.com -proxy http://127.0.0.1:8080

# Proxy SOCKS
nuclei -u target.com -proxy socks5://127.0.0.1:9050

# Proxy Burp Suite
nuclei -u target.com -proxy http://127.0.0.1:8080
```

### Autenticação

```bash
# Autenticação por cabeçalho
nuclei -u target.com -H "Authorization: Bearer token123"

# Autenticação por cookie
nuclei -u target.com -H "Cookie: session=abc123"

# Vários cabeçalhos
nuclei -u target.com -H "Authorization: Bearer token" -H "X-Custom: value"
```

### Integração com Interactsh

```bash
# Utilizar interactsh em testes fora de banda (OOB)
nuclei -u target.com -interactsh-server https://interact.sh

# Servidor interactsh personalizado
nuclei -u target.com -iserver https://my-interactsh.com
```

### Modo sem interface gráfica (headless)

```bash
# Ativar o navegador sem interface gráfica
nuclei -u target.com -headless

# Apenas modelos headless
nuclei -u target.com -t headless/
```

### Fluxos de trabalho

```bash
# Executar um workflow
nuclei -u target.com -w workflows/wordpress-workflow.yaml

# Listar workflows
ls ~/.local/nuclei-templates/workflows/
```

---

<a id="-custom-templates"></a>
## 📝 Modelos personalizados

### Estrutura dos modelos

```yaml
id: my-custom-template

info:
  name: My Custom Template
  author: your-name
  severity: medium
  description: Description of what this template detects
  tags: custom,web

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
    matchers:
      - type: word
        words:
          - "Admin Panel"
```

### Exemplos de modelos

#### Detetar uma página específica
```yaml
id: admin-panel-detect

info:
  name: Admin Panel Detection
  author: hunter
  severity: info
  tags: panel

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
      - "{{BaseURL}}/administrator"
      - "{{BaseURL}}/wp-admin"
    matchers-condition: or
    matchers:
      - type: status
        status:
          - 200
```

#### Verificar um cabeçalho
```yaml
id: missing-security-header

info:
  name: Missing X-Frame-Options
  author: hunter
  severity: low
  tags: headers

http:
  - method: GET
    path:
      - "{{BaseURL}}"
    matchers:
      - type: word
        words:
          - "text/html"
        part: header
    matchers:
      - type: word
        negative: true
        words:
          - "X-Frame-Options"
        part: header
```

### Validar um modelo

```bash
# Validar a sintaxe do modelo
nuclei -validate -t my-template.yaml

# Testar o modelo
nuclei -u target.com -t my-template.yaml -debug
```

---

<a id="-bug-bounty-workflow"></a>
## 🔄 Fluxo de trabalho de bug bounty

### Cadeia completa de reconhecimento

```bash
# Passo 1: enumeração de subdomínios
subfinder -d target.com -o subs.txt

# Passo 2: identificar sistemas ativos
cat subs.txt | httpx -o live.txt

# Passo 3: executar nuclei
nuclei -l live.txt -severity critical,high -o findings.txt

# Comando de uma linha
subfinder -d target.com -silent | httpx -silent | nuclei -severity critical,high
```

### Análise por prioridade

```bash
# Fase 1: apenas gravidade crítica (rápido)
nuclei -l targets.txt -severity critical -o critical.txt

# Fase 2: gravidade elevada
nuclei -l targets.txt -severity high -o high.txt

# Fase 3: CVEs
nuclei -l targets.txt -t cves/ -o cves.txt

# Fase 4: análise completa
nuclei -l targets.txt -o all.txt
```

### Análise direcionada

```bash
# Alvos WordPress
nuclei -l wp-sites.txt -tags wordpress

# Endpoints de API
nuclei -l apis.txt -tags api

# Páginas de início de sessão
nuclei -l logins.txt -tags login,default-login
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: análise completa
```bash
nuclei -u https://target.com -o results.txt
```

### Exemplo 2: apenas CVEs críticos
```bash
nuclei -u https://target.com -t cves/ -severity critical,high
```

### Exemplo 3: análise rápida para bug bounty
```bash
nuclei -u https://target.com -tags cve,rce,sqli,xss,ssrf -severity critical,high
```

### Exemplo 4: verificar a apropriação de subdomínios
```bash
cat subs.txt | nuclei -t takeovers/
```

### Exemplo 5: deteção de tecnologias
```bash
nuclei -u https://target.com -t technologies/ -silent
```

### Exemplo 6: com um proxy (Burp)
```bash
nuclei -u https://target.com -proxy http://127.0.0.1:8080
```

### Exemplo 7: análise com autenticação
```bash
nuclei -u https://target.com -H "Authorization: Bearer eyJhbG..."
```

### Exemplo 8: análise com limitação da frequência dos pedidos
```bash
nuclei -l targets.txt -rate-limit 10 -c 5
```

### Exemplo 9: saída JSON para processamento
```bash
nuclei -u https://target.com -json -o results.json
```

### Exemplo 10: cadeia de ferramentas completa
```bash
subfinder -d target.com -silent | \
httpx -silent | \
nuclei -severity critical,high -json -o results.json
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Tarefa | Comando |
|------|---------|
| Análise básica | `nuclei -u target.com` |
| Com modelos | `nuclei -u target.com -t cves/` |
| Por etiquetas | `nuclei -u target.com -tags cve,rce` |
| Por gravidade | `nuclei -u target.com -severity critical` |
| A partir de uma lista | `nuclei -l urls.txt` |
| Atualizar os modelos | `nuclei -ut` |
| Saída silenciosa | `nuclei -u target.com -silent` |
| Saída JSON | `nuclei -u target.com -json -o out.json` |

### Etiquetas comuns

| Tag | Descrição |
|-----|-------------|
| `cve` | Vulnerabilidades com CVE |
| `rce` | Execução remota de código |
| `sqli` | Injeção de SQL |
| `xss` | Cross-site scripting (XSS, execução de scripts no contexto de outra página) |
| `ssrf` | Server-side request forgery (SSRF, falsificação de pedidos pelo servidor) |
| `lfi` | Inclusão de ficheiros locais (LFI) |
| `panel` | Painéis de administração |
| `takeover` | Apropriação de subdomínios (subdomain takeover) |

### Níveis de gravidade

| Gravidade | Cor | Prioridade |
|----------|-------|----------|
| `critical` | 🔴 Vermelho | Imediato |
| `high` | 🟠 Laranja | Elevada |
| `medium` | 🟡 Amarelo | Média |
| `low` | 🟢 Verde | Baixa |
| `info` | 🔵 Azul | Informativa |

---

<a id="-tips--best-practices"></a>
## 💡 Conselhos e boas práticas

### Conselhos de desempenho

```bash
# Definições adequadas a análises extensas
nuclei -l targets.txt -c 50 -rl 150 -bs 25

# Modo silencioso para pipelines
nuclei -l targets.txt -silent -o results.txt
```

### Sugestões para bug bounty

1. **Comece pelas gravidades crítica e elevada**
   ```bash
   nuclei -l targets.txt -severity critical,high
   ```

2. **Procure problemas fáceis de identificar**
   ```bash
   nuclei -l targets.txt -tags takeover,exposed-panels
   ```

3. **Utilize modelos atualizados**
   ```bash
   nuclei -ut  # Atualizar sempre antes de investigar
   ```

4. **Limite a frequência dos pedidos para melhorar a estabilidade**
   ```bash
   nuclei -l targets.txt -rl 50
   ```

### Conselhos para reduzir a visibilidade

```bash
# Lento e silencioso
nuclei -l targets.txt -rl 10 -c 2

# Através de proxy
nuclei -l targets.txt -proxy socks5://127.0.0.1:9050
```

---

## ⚠️ Aviso legal

> **AVISO:** O Nuclei é um analisador de vulnerabilidades potente que só deve ser utilizado em **testes autorizados**.
> 
> - ✅ Utilize-o em programas nos quais tem autorização para realizar testes
> - ✅ Utilize-o em programas de bug bounty autorizados
> - ✅ Utilize-o nos seus próprios sistemas
> - ❌ Nunca analisar sem autorização
> - ❌ Respeite os limites de pedidos e o âmbito autorizado
> 
> **A análise sem autorização é ilegal e eticamente incorreta.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [Nuclei GitHub](https://github.com/projectdiscovery/nuclei)
- [Nuclei Templates](https://github.com/projectdiscovery/nuclei-templates)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentação](https://docs.projectdiscovery.io/tools/nuclei/)

### Recursos sobre modelos
- [Modelos da comunidade](https://github.com/projectdiscovery/nuclei-templates)
- [Guia de modelos](https://docs.projectdiscovery.io/templates/)

### Guias de referência relacionados
- [Nmap](../../Nmap/translations/README.pt.md)
- [Burp Suite](../../Burp-Suite/translations/README.pt.md)
- [SQLMap](../../SQLMap/translations/README.pt.md)

---

<p align="center">
  <b>🎯 Domine a análise de vulnerabilidades!</b><br>
  <i>The bug bounty hunter's best friend</i>
</p>
