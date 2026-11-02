# 🔍 Arjun — descoberta de parâmetros ocultos

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de versão e precisão:** a documentação oficial indica `-oT`/`-oJ` para exportação, `-i` para importar ficheiros (incluindo Burp), `-d` para o intervalo, `-T` para o tempo limite e `--ratelimit` para pedidos por segundo. `--passive` recolhe nomes de parâmetros de fontes como Common Crawl, OTX e Wayback Machine; não se limita a analisar JavaScript. Vários exemplos do original usam outra sintaxe e precisam de adaptação à versão instalada. [Guia oficial de utilização](https://github.com/s0md3v/Arjun/wiki/Usage).

```
   █████╗ ██████╗      ██╗██╗   ██╗███╗   ██╗
  ██╔══██╗██╔══██╗     ██║██║   ██║████╗  ██║
  ███████║██████╔╝     ██║██║   ██║██╔██╗ ██║
  ██╔══██║██╔══██╗██   ██║██║   ██║██║╚██╗██║
  ██║  ██║██║  ██║╚█████╔╝╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚═╝  ╚═══╝
         Conjunto de ferramentas de descoberta de parâmetros HTTP
```

<p align="center">
  <img src="https://img.shields.io/badge/Arjun-Parameter_Discovery-blue?style=for-the-badge" alt="Arjun">
  <img src="https://img.shields.io/badge/Hidden_Params-green?style=for-the-badge" alt="Hidden">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [O que é o Arjun](#-what-is-arjun)
- [Instalação](#-installation)
- [Utilização básica](#-basic-usage)
- [Modos de deteção](#-discovery-modes)
- [Opções avançadas](#-advanced-options)
- [Opções de saída](#-output-options)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-arjun"></a>
## 🎯 O que é o Arjun

O **Arjun** descobre parâmetros HTTP ocultos em aplicações Web:

- 🔍 **Deteção de parâmetros** - Encontrar parâmetros GET/POST ocultos
- 🎯 **Suporte de JSON** - Descobrir parâmetros JSON
- 📡 **Vários métodos** - Modos GET, POST e JSON
- ⚡ **Rápido** - Pedidos com elevado paralelismo

### Porquê o Arjun?

| Funcionalidade | Vantagem |
|---------|---------|
| Parâmetros ocultos | Encontrar endpoints não referenciados |
| Vários formatos | GET, POST, JSON |
| Modo passivo | Analisar ficheiros JavaScript |
| Importar do Burp | Integrar no fluxo de trabalho existente |

---

<a id="-installation"></a>
## 🚀 Instalação

```bash
# Instalação com pip (recomendada)
pip3 install arjun

# A partir do código-fonte
git clone https://github.com/s0md3v/Arjun.git
cd Arjun
python3 setup.py install

# Verificar
arjun --help
```

---

<a id="-basic-usage"></a>
## 📖 Utilização básica

### Um único URL

```bash
# Descoberta básica de parâmetros
arjun -u https://example.com/endpoint

# Com ficheiro de saída
arjun -u https://example.com/endpoint -o results.txt
```

### Vários URLs

```bash
# A partir de ficheiro
arjun -i urls.txt

# A partir da entrada padrão
cat urls.txt | arjun --stdin
```

### Especificar o método HTTP

```bash
# Parâmetros GET (predefinição)
arjun -u https://example.com/search -m GET

# Parâmetros POST
arjun -u https://example.com/api -m POST

# Corpo JSON
arjun -u https://example.com/api -m JSON
```

---

<a id="-discovery-modes"></a>
## 🔎 Modos de deteção

### Parâmetros GET

```bash
# Descobrir parâmetros GET
arjun -u https://example.com/search?known=value -m GET

# Exemplo de saída:
# [param] id
# [param] user
# [param] debug
```

### Parâmetros POST

```bash
# Descobrir parâmetros de formulários POST
arjun -u https://example.com/login -m POST

# Com dados existentes
arjun -u https://example.com/login -m POST --include 'username=admin'
```

### Parâmetros JSON

```bash
# Descobrir parâmetros do corpo JSON
arjun -u https://example.com/api/v1/users -m JSON

# Com JSON de base
arjun -u https://example.com/api -m JSON --include '{"known":"value"}'
```

### Deteção combinada

```bash
# Todos os métodos
arjun -u https://example.com/endpoint -m GET,POST,JSON
```

---

<a id="-advanced-options"></a>
## ⚙️ Opções avançadas

### Lista de palavras personalizada

```bash
# Utilizar uma lista de palavras personalizada
arjun -u https://example.com -w /path/to/wordlist.txt

# Utilizar a lista de parâmetros do SecLists
arjun -u https://example.com -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt
```

### Threads e limitação da frequência de pedidos

```bash
# Definir threads (predefinição: 2)
arjun -u https://example.com -t 10

# Limite da frequência (pedidos por segundo)
arjun -u https://example.com --rate-limit 10

# Intervalo entre pedidos (segundos)
arjun -u https://example.com --delay 1
```

### Cabeçalhos personalizados

```bash
# Adicionar um cabeçalho
arjun -u https://example.com --headers "Authorization: Bearer token"

# Vários cabeçalhos
arjun -u https://example.com --headers "Cookie: session=abc;X-Custom: value"

# A partir de ficheiro
arjun -u https://example.com -H headers.txt
```

### Incluir parâmetros conhecidos

```bash
# Incluir parâmetros conhecidos
arjun -u https://example.com/search --include 'q=test&page=1'

# Para POST
arjun -u https://example.com/api -m POST --include 'action=submit'
```

### Modo passivo

```bash
# Extrair de ficheiros JavaScript
arjun -u https://example.com --passive

# Verifica ficheiros JS à procura de possíveis nomes de parâmetros
```

### Desativar redirecionamentos

```bash
# Não seguir redirecionamentos
arjun -u https://example.com --disable-redirects
```

### Tempo limite

```bash
# Definir o tempo limite (segundos)
arjun -u https://example.com --timeout 15
```

### Modo estável

```bash
# Mais fiável (mais lento)
arjun -u https://example.com --stable
```

---

<a id="-output-options"></a>
## 📤 Opções de saída

### Formatos de saída

```bash
# Saída de texto (predefinição)
arjun -u https://example.com -oT results.txt

# Saída JSON
arjun -u https://example.com -oJ results.json

# Ambos
arjun -u https://example.com -oT text.txt -oJ json.json
```

### Exemplo de saída JSON

```json
{
  "https://example.com/api": {
    "method": "GET",
    "params": [
      {"name": "id", "reason": "body differs"},
      {"name": "debug", "reason": "body differs"}
    ]
  }
}
```

### Importar do Burp

```bash
# Importar pedidos do Burp
arjun --import burp_export.xml

# Processar um ficheiro do Burp
arjun -i burp_requests.txt --burp
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos básicos

| Comando | Descrição |
|---------|-------------|
| `arjun -u URL` | Descobrir parâmetros |
| `arjun -u URL -m POST` | Método POST |
| `arjun -u URL -m JSON` | Corpo JSON |
| `arjun -i file.txt` | Vários URLs |
| `arjun -u URL -oJ out.json` | Saída JSON |

### Opções dos métodos

| Opção | Descrição |
|------|-------------|
| `-m GET` | Parâmetros GET |
| `-m POST` | Dados de formulário POST |
| `-m JSON` | Corpo JSON |
| `-m GET,POST` | Vários métodos |

### Opções de desempenho

| Opção | Descrição |
|------|-------------|
| `-t 10` | Threads (linhas de execução) |
| `--rate-limit 10` | Pedidos/segundo |
| `--delay 1` | Intervalo entre pedidos |
| `--timeout 15` | Tempo limite do pedido |

### Opções de descoberta

| Opção | Descrição |
|------|-------------|
| `-w wordlist.txt` | Lista de palavras personalizada |
| `--include 'a=b'` | Incluir parâmetros |
| `--passive` | Extrair de JavaScript |
| `--stable` | Modo fiável |

---

## 🎯 Fluxos de trabalho de bug bounty

### Deteção básica

```bash
# Descoberta rápida de parâmetros
arjun -u https://target.com/api/endpoint -m GET,POST,JSON -oJ params.json
```

### Com uma lista de palavras personalizada

```bash
# Descoberta abrangente
arjun -u https://target.com/admin \
  -m GET,POST \
  -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
  -t 10 \
  --rate-limit 20 \
  -oJ discovered_params.json
```

### Testes com autenticação

```bash
# Com uma sessão
arjun -u https://target.com/dashboard \
  --headers "Cookie: session=abc123;Authorization: Bearer token" \
  -m GET,POST,JSON \
  -oJ auth_params.json
```

### Deteção em grande escala

```bash
# Vários endpoints
cat endpoints.txt | arjun --stdin -m GET,POST -t 5 -oJ all_params.json

# Ou com um ficheiro
arjun -i endpoints.txt -m GET,POST -t 5 -oJ results.json
```

### Integração numa cadeia de ferramentas

```bash
# Encontrar endpoints e depois descobrir parâmetros
gau target.com | grep -E "\.php|\.asp" | httpx -silent | \
  arjun --stdin -m GET,POST -oJ params.json

# Katana + Arjun
katana -u https://target.com -d 3 | \
  grep -E "\.(php|asp|aspx|jsp)$" | \
  arjun --stdin -m GET,POST
```

### Testar os parâmetros descobertos

```bash
# Após a descoberta, testar vulnerabilidades
# 1. Descobrir parâmetros
arjun -u https://target.com/search -oJ params.json

# 2. Extrair nomes de parâmetros
cat params.json | jq -r '.[].params[].name'

# 3. Testar com ffuf
ffuf -u "https://target.com/search?FUZZ=test" -w params.txt
```

---

## 💡 Conselhos práticos

1. **Comece pelo modo passivo** - Analise primeiro os ficheiros JavaScript
2. **Utilize boas listas de palavras** - Recomendam-se os nomes de parâmetros do Burp
3. **Limite da frequência de pedidos** - Evite ser bloqueado
4. **Teste todos os métodos** - GET, POST e JSON
5. **Verifique os cabeçalhos** - X-Custom-Param, X-Debug, etc.
6. **Com autenticação** - Teste áreas com sessão iniciada

### Parâmetros ocultos comuns

```
debug, test, admin
id, user_id, uid
token, api_key, key
callback, redirect, url
format, output, type
page, limit, offset
sort, order, filter
```

---

## 📚 Recursos

- [Arjun GitHub](https://github.com/s0md3v/Arjun)
- [Nomes de parâmetros do Burp](https://github.com/danielmiessler/SecLists/blob/master/Discovery/Web-Content/burp-parameter-names.txt)

### Guias de referência relacionados
- [Metodologia de bug bounty](../../Bug-Bounty-Methodology/translations/README.pt.md)
- [ffuf](../../ffuf/translations/README.pt.md)
- [Burp Suite](../../Burp-Suite/translations/README.pt.md)

---

<p align="center">
  <b>🔍 Descubra parâmetros ocultos!</b><br>
  <i>Arjun: à procura de parâmetros</i>
</p>
