# 🌐 httpx — guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de versão:** os exemplos pertencem a versões específicas. Na documentação atual, `-ml`/`-fl` tratam o comprimento do conteúdo e `-mlc`/`-flc` o número de linhas; `-mt`/`-ft` não são opções documentadas para filtrar tecnologias. A deteção usa `-td`; confirme as condições e opções adequadas na versão instalada antes de reutilizar os exemplos. [Opções oficiais do httpx](https://docs.projectdiscovery.io/opensource/httpx/usage). O *favicon* é o ícone do sítio; *hash* é um resumo calculado dos dados.

```
  _     _   _           
 | |__ | |_| |_ _ __ __  __
 | '_ \| __| __| '_ \\ \/ /
 | | | | |_| |_| |_) |>  < 
 |_| |_|\__|\__| .__//_/\_\
               |_|         
                           
   Ferramenta rápida de sondagem HTTP
```

<p align="center">
  <img src="https://img.shields.io/badge/httpx-HTTP_Probe-red?style=for-the-badge" alt="httpx">
  <img src="https://img.shields.io/badge/Fast-Toolkit-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>⚡ Conjunto rápido e versátil de ferramentas HTTP da ProjectDiscovery</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Sintaxe básica](#-basic-syntax)
- [Verificação de serviços e deteção](#-probing--discovery)
- [Extração de informação](#-information-extraction)
- [Filtragem e seleção por correspondência](#-filtering--matching)
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

O **httpx** é um conjunto rápido e versátil de ferramentas HTTP que permite executar várias sondas através da biblioteca retryablehttp. Destina-se a investigadores de bug bounty e de segurança.

### Funcionalidades principais

| Funcionalidade | Descrição |
|---------|-------------|
| **Rápido** | Ferramenta desenvolvida em Go, com elevado paralelismo |
| **Várias sondas** | Código de estado, título, tecnologias e cabeçalhos |
| **Deteção de tecnologias** | Deteção baseada no Wappalyzer |
| **Captura de ecrã** | Capturas de ecrã com um navegador sem interface gráfica |
| **Cadeia de ferramentas** | Funciona com subfinder e nuclei |
| **Saída JSON** | Suporte de saída estruturada |

### httpx e outras ferramentas

| Funcionalidade | httpx | curl | wget |
|---------|-------|------|------|
| **Velocidade** | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐ |
| **Execução em paralelo** | ✅ Sim | ❌ Não | ❌ Não |
| **Deteção de tecnologias** | ✅ Sim | ❌ Não | ❌ Não |
| **Capturas de ecrã** | ✅ Sim | ❌ Não | ❌ Não |
| **Cadeia de ferramentas** | ✅ Sim | ⚠️ Manual | ⚠️ Manual |

---

<a id="-installation"></a>
## 📥 Instalação

### Com Go
```bash
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest
```

### Kali Linux
```bash
sudo apt install httpx-toolkit
```

### Descarregar o binário
```bash
# Descarregar a partir das versões publicadas
https://github.com/projectdiscovery/httpx/releases

# Linux
wget https://github.com/projectdiscovery/httpx/releases/download/v1.x.x/httpx_1.x.x_linux_amd64.zip
unzip httpx_1.x.x_linux_amd64.zip
sudo mv httpx /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/httpx:latest
docker run -it projectdiscovery/httpx -h
```

### Verificar a instalação
```bash
httpx -version
httpx -h
```

---

<a id="-basic-syntax"></a>
## ⌨️ Sintaxe básica

### Sintaxe geral
```bash
httpx [options] -u <url>
httpx [options] -l <file>
cat urls.txt | httpx [options]
```

### Opções essenciais

| Opção | Descrição |
|--------|-------------|
| `-u` | URL de um único alvo |
| `-l` | Ficheiro com uma lista de URLs |
| `-sc` | Mostrar o código de estado |
| `-title` | Mostrar o título da página |
| `-td` | Deteção de tecnologias |
| `-o` | Ficheiro de saída |
| `-silent` | Modo silencioso |
| `-json` | Saída JSON |

### Primeiros passos
```bash
# Sondar um URL
httpx -u https://example.com

# Sondar a partir de ficheiro
httpx -l urls.txt

# A partir da entrada padrão (pipeline)
cat hosts.txt | httpx

# Com subfinder
subfinder -d example.com -silent | httpx
```

---

<a id="-probing--discovery"></a>
## 🔍 Verificação de serviços e deteção

### Verificação básica de serviços

```bash
# Sondar sistemas através de HTTP/HTTPS
cat hosts.txt | httpx

# Incluir o código de estado
cat hosts.txt | httpx -sc

# Sondar portas específicas
cat hosts.txt | httpx -p 80,443,8080,8443

# Sondar todas as portas comuns
cat hosts.txt | httpx -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```

### Análise de portas

```bash
# Portas personalizadas
httpx -l hosts.txt -p 80,443,8080,8443

# Intervalo de portas
httpx -l hosts.txt -p 80-100

# Portas Web comuns
httpx -l hosts.txt -p http:80,https:443
```

### Deteção de protocolos

```bash
# Sondagem HTTP/HTTPS
cat hosts.txt | httpx

# Seguir redirecionamentos
cat hosts.txt | httpx -follow-redirects

# Máximo de redirecionamentos
cat hosts.txt | httpx -follow-redirects -max-redirects 5

# Sem fallback
cat hosts.txt | httpx -no-fallback
```

---

<a id="-information-extraction"></a>
## 📊 Extração de informação

### Códigos de estado e títulos

```bash
# Código de estado
httpx -l urls.txt -sc

# Título da página
httpx -l urls.txt -title

# Ambos
httpx -l urls.txt -sc -title

# Tamanho do conteúdo
httpx -l urls.txt -cl

# Servidor Web
httpx -l urls.txt -server

# Todas as informações básicas
httpx -l urls.txt -sc -title -cl -server
```

### Deteção de tecnologias

```bash
# Detetar tecnologias
httpx -l urls.txt -td

# Deteção de tecnologias (detalhada)
httpx -l urls.txt -tech-detect

# Com outras informações
httpx -l urls.txt -sc -title -td
```

### Cabeçalhos e resposta

```bash
# Cabeçalhos da resposta
httpx -l urls.txt -response-header

# Cabeçalho específico
httpx -l urls.txt -include-response-header "Server,X-Powered-By"

# Hash do corpo da resposta
httpx -l urls.txt -hash md5

# Pré-visualização do corpo
httpx -l urls.txt -bp
```

### Informação de IP e DNS

```bash
# Endereço IP
httpx -l urls.txt -ip

# CNAME
httpx -l urls.txt -cname

# Deteção de CDN
httpx -l urls.txt -cdn

# Informações de ASN
httpx -l urls.txt -asn

# Todas as informações DNS
httpx -l urls.txt -ip -cname -cdn -asn
```

### Informação de SSL/TLS

```bash
# Sondagem TLS
httpx -l urls.txt -tls-probe

# Recolha de dados TLS
httpx -l urls.txt -tls-grab

# Informações do certificado
httpx -l urls.txt -tlsgrab
```

### Hash do favicon

```bash
# Obter o hash do favicon (útil em pesquisas Shodan)
httpx -l urls.txt -favicon
```

### Captura de ecrã

```bash
# Capturar imagens do ecrã
httpx -l urls.txt -screenshot

# Capturas de ecrã com pasta de saída
httpx -l urls.txt -screenshot -screenshot-output ./screenshots/

# Opções de navegador sem interface gráfica
httpx -l urls.txt -screenshot -system-chrome
```

---

<a id="-filtering--matching"></a>
## 🎯 Filtragem e seleção por correspondência

### Filtrar por código de estado

```bash
# Selecionar códigos de estado
httpx -l urls.txt -mc 200,301,302

# Excluir códigos de estado
httpx -l urls.txt -fc 404,403,500

# Selecionar todos e excluir os específicos
httpx -l urls.txt -mc all -fc 404
```

### Filtrar por tamanho

```bash
# Selecionar pelo tamanho do conteúdo
httpx -l urls.txt -ml 1234

# Excluir pelo tamanho do conteúdo
httpx -l urls.txt -fl 0

# Selecionar pelo número de linhas
httpx -l urls.txt -mlc 100

# Excluir pelo número de linhas
httpx -l urls.txt -flc 50
```

### Filtrar por conteúdo

```bash
# Selecionar pelo corpo da resposta (expressão regular)
httpx -l urls.txt -mr "admin|login"

# Excluir pelo corpo da resposta (expressão regular)
httpx -l urls.txt -fr "error|not found"

# Selecionar por cadeia de caracteres
httpx -l urls.txt -ms "dashboard"
```

### Filtrar por tecnologia

```bash
# Selecionar por tecnologia
httpx -l urls.txt -td -mt "WordPress,Nginx"

# Excluir por tecnologia
httpx -l urls.txt -td -ft "Apache"
```

---

<a id="-output-options"></a>
## 📤 Opções de saída

### Formatos de saída

```bash
# Texto simples
httpx -l urls.txt -o results.txt

# Saída JSON
httpx -l urls.txt -json -o results.json

# Linhas JSON
httpx -l urls.txt -jsonl -o results.jsonl

# Saída CSV
httpx -l urls.txt -csv -o results.csv
```

### Campos de saída

```bash
# Guardar a resposta
httpx -l urls.txt -sr -srd ./responses/

# Guardar capturas de ecrã
httpx -l urls.txt -screenshot -ssd ./screenshots/

# Saída personalizada
httpx -l urls.txt -o output.txt -sc -title -td
```

### Modos silencioso e detalhado

```bash
# Silencioso (apenas URLs)
httpx -l urls.txt -silent

# Detalhado
httpx -l urls.txt -v

# Depuração
httpx -l urls.txt -debug

# Sem cores
httpx -l urls.txt -nc
```

---

<a id="-advanced-features"></a>
## ⚡ Funcionalidades avançadas

### Threads e limitação da frequência de pedidos

```bash
# Definir threads (predefinição: 50)
httpx -l urls.txt -threads 100

# Limite da frequência de pedidos
httpx -l urls.txt -rate-limit 100

# Tempo limite
httpx -l urls.txt -timeout 10

# Repetições
httpx -l urls.txt -retries 3
```

### Suporte de proxy

```bash
# Proxy HTTP
httpx -l urls.txt -proxy http://127.0.0.1:8080

# Proxy SOCKS
httpx -l urls.txt -proxy socks5://127.0.0.1:9050

# Burp Suite
httpx -l urls.txt -proxy http://127.0.0.1:8080
```

### Cabeçalhos personalizados

```bash
# Cabeçalho personalizado
httpx -l urls.txt -H "Authorization: Bearer token123"

# Vários cabeçalhos
httpx -l urls.txt -H "X-Custom: value" -H "Cookie: session=abc"

# User-Agent
httpx -l urls.txt -H "User-Agent: Mozilla/5.0"
```

### Métodos dos pedidos

```bash
# Pedido HEAD (mais rápido)
httpx -l urls.txt -method HEAD

# Método personalizado
httpx -l urls.txt -method OPTIONS

# POST com corpo
httpx -l urls.txt -method POST -body '{"test":"data"}'
```

### Retoma e paginação

```bash
# Retomar da última posição
httpx -l urls.txt -resume

# Guardar o progresso
httpx -l urls.txt -resume -resume-file progress.txt
```

---

<a id="-bug-bounty-workflow"></a>
## 🔄 Fluxo de trabalho de bug bounty

### Cadeia completa de reconhecimento

```bash
# Passo 1: enumeração de subdomínios
subfinder -d target.com -o subs.txt

# Passo 2: sondar os sistemas ativos com httpx
cat subs.txt | httpx -sc -title -td -o live.txt

# Passo 3: análise de vulnerabilidades
nuclei -l live.txt -severity critical,high

# Comando de uma linha
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Reconhecimento detalhado

```bash
# Recolha completa de informações
subfinder -d target.com -silent | httpx \
    -sc \
    -title \
    -td \
    -ip \
    -cdn \
    -server \
    -follow-redirects \
    -o full-recon.txt
```

### Capturar o ecrã de todos os sistemas ativos

```bash
subfinder -d target.com -silent | httpx -silent -screenshot -ssd ./screenshots/
```

### Encontrar endpoints relevantes

```bash
# Encontrar painéis de administração
subfinder -d target.com -silent | httpx -title -mr "admin|login|dashboard"

# Encontrar endpoints de API
cat subs.txt | httpx -path /api/ -mc 200,401,403

# Verificar caminhos específicos
httpx -l hosts.txt -path /robots.txt -mc 200
```

### Procura de vulnerabilidades por tecnologia

```bash
# Encontrar sítios WordPress
subfinder -d target.com -silent | httpx -td -mt "WordPress"

# Encontrar servidores Nginx
cat hosts.txt | httpx -server -ms "nginx"
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: verificação básica de serviços
```bash
echo "https://example.com" | httpx -sc -title
```

### Exemplo 2: reconhecimento completo a partir de subdomínios
```bash
subfinder -d hackerone.com -silent | httpx -sc -title -td -ip
```

### Exemplo 3: capturar o ecrã de todos os sistemas
```bash
httpx -l hosts.txt -screenshot -ssd ./screenshots/
```

### Exemplo 4: encontrar apenas sistemas ativos
```bash
cat subs.txt | httpx -silent -o live.txt
```

### Exemplo 5: deteção de tecnologias
```bash
httpx -l urls.txt -tech-detect -json -o tech.json
```

### Exemplo 6: com um proxy (Burp)
```bash
httpx -l urls.txt -proxy http://127.0.0.1:8080 -silent
```

### Exemplo 7: portas personalizadas
```bash
cat hosts.txt | httpx -p 80,443,8080,8443 -sc -title
```

### Exemplo 8: selecionar apenas respostas 200
```bash
cat subs.txt | httpx -mc 200 -silent
```

### Exemplo 9: saída JSON para processamento
```bash
httpx -l urls.txt -json -sc -title -td -o results.json
```

### Exemplo 10: cadeia de ferramentas completa
```bash
subfinder -d target.com -silent | \
httpx -silent -sc -title -td -ip -cdn -server -json | \
tee results.json | \
jq -r '.url' | \
nuclei -severity critical,high
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Tarefa | Comando |
|------|---------|
| Verificação básica de serviços | `httpx -u url` |
| A partir de ficheiro | `httpx -l urls.txt` |
| Código de estado | `httpx -l urls.txt -sc` |
| Título da página | `httpx -l urls.txt -title` |
| Detetar tecnologias | `httpx -l urls.txt -td` |
| Endereço IP | `httpx -l urls.txt -ip` |
| Captura de ecrã | `httpx -l urls.txt -screenshot` |
| Saída JSON | `httpx -l urls.txt -json -o out.json` |

### Opções comuns

| Opção | Descrição |
|--------|-------------|
| `-u` | Um único URL |
| `-l` | Ficheiro com uma lista de URLs |
| `-sc` | Código de estado |
| `-title` | Título da página |
| `-td` | Detetar tecnologias |
| `-ip` | Endereço IP |
| `-cdn` | Deteção de CDN |
| `-server` | Servidor Web |
| `-mc` | Selecionar códigos |
| `-fc` | Excluir códigos |
| `-json` | Saída JSON |
| `-silent` | Modo silencioso |

### Cadeias de ferramentas comuns

| Cadeia de ferramentas | Comando |
|----------|---------|
| Sistemas ativos | `subfinder -d target.com \| httpx -silent` |
| Com informação | `subfinder -d target.com \| httpx -sc -title -td` |
| Enviar para nuclei | `httpx -l hosts.txt -silent \| nuclei` |

---

<a id="-tips--best-practices"></a>
## 💡 Conselhos e boas práticas

### Sugestões para bug bounty

1. **Utilize com Subfinder**
   ```bash
   subfinder -d target.com -silent | httpx -silent -sc -title
   ```

2. **Obtenha uma visão completa**
   ```bash
   httpx -l hosts.txt -sc -title -td -ip -cdn -server
   ```

3. **Use capturas de ecrã na triagem**
   ```bash
   httpx -l hosts.txt -screenshot
   ```

4. **Utilize JSON para processamento**
   ```bash
   httpx -l hosts.txt -json | jq '.url'
   ```

### Conselhos de desempenho

```bash
# Velocidade elevada
httpx -l hosts.txt -threads 100 -timeout 5

# Pouco tráfego e baixa velocidade
httpx -l hosts.txt -threads 10 -rate-limit 20
```

---

## ⚠️ Aviso legal

> **AVISO:** O httpx envia pedidos HTTP aos alvos. Utilize-o apenas em sistemas autorizados.
> 
> - ✅ Utilize-o em programas nos quais tem autorização para realizar testes
> - ✅ Alvos de bug bounty dentro do âmbito autorizado
> - ✅ Os seus próprios domínios
> - ❌ Nunca verifique serviços de alvos sem autorização
> - ❌ Respeite os limites de pedidos
> 
> **A verificação de serviços sem autorização pode ser ilegal.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [httpx GitHub](https://github.com/projectdiscovery/httpx)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentação](https://docs.projectdiscovery.io/tools/httpx/)

### Guias de referência relacionados
- [Subfinder](../../Subfinder/translations/README.pt.md)
- [Nuclei](../../Nuclei/translations/README.pt.md)
- [ffuf](../../ffuf/translations/README.pt.md)

---

<p align="center">
  <b>🌐 Verifique todos os serviços!</b><br>
  <i>The essential HTTP toolkit for bug bounty</i>
</p>
