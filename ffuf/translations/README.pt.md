# 🚀 ffuf — guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de precisão:** os comandos do original foram preservados. No exemplo «Com resolvedor», `-r` significa seguir redirecionamentos, não configurar um resolvedor DNS. `-se` interrompe a execução em erros anómalos; `-sf` interrompe quando mais de 95% das respostas são 403. As comparações entre ferramentas são indicativas e dependem da versão. [Ajuda oficial do ffuf](https://github.com/ffuf/ffuf#usage). *Fuzzing* consiste em testar entradas variadas; WAF significa firewall de aplicações Web.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   __  __        __ 
  / _|/ _|_   _ / _|
 | |_| |_| | | | |_ 
 |  _|  _| |_| |  _|
 |_| |_|  \__,_|_|  
                    
   Fuzz Faster U Fool
```

<p align="center">
  <img src="https://img.shields.io/badge/ffuf-Web_Fuzzer-red?style=for-the-badge" alt="ffuf">
  <img src="https://img.shields.io/badge/Fast-Fuzzing-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Go-Language-green?style=for-the-badge" alt="Go">
</p>

<p align="center">
  <b>⚡ O fuzzer Web mais rápido escrito em Go</b>
</p>

---

## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Sintaxe básica](#-basic-syntax)
- [Fuzzing de diretórios](#-directory-fuzzing)
- [Fuzzing de subdomínios](#-subdomain-fuzzing)
- [Fuzzing de parâmetros](#-parameter-fuzzing)
- [Fuzzing de dados POST](#-post-data-fuzzing)
- [Filtragem e seleção por correspondência](#-filtering--matching)
- [Funcionalidades avançadas](#-advanced-features)
- [Exemplos práticos](#-real-world-examples)
- [Referência rápida](#-quick-reference)
- [Conselhos e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

O **ffuf** (Fuzz Faster U Fool) é um fuzzer Web rápido escrito em Go. É uma ferramenta de referência para descoberta de pastas por força bruta, descoberta de virtual hosts e fuzzing de parâmetros em bug bounty.

### Funcionalidades principais

| Funcionalidade | Descrição |
|---------|-------------|
| **Velocidade** | Muito rápido (desenvolvido em Go) |
| **Flexível** | Fuzzing de qualquer parte de um pedido HTTP |
| **Filtragem** | Várias opções de filtragem e de seleção por correspondência |
| **Recursividade** | Suporte de análise recursiva |
| **Saída** | Vários formatos de saída |
| **Simples** | Sintaxe simples e intuitiva |

### ffuf e outras ferramentas

| Funcionalidade | ffuf | Gobuster | dirb |
|---------|------|----------|------|
| **Velocidade** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |
| **Flexibilidade** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| **Fuzzing de POST** | ✅ Sim | ❌ Não | ❌ Não |
| **Recursividade** | ✅ Sim | ✅ Sim | ✅ Sim |
| **Filtros** | Muitos | Limitados | Limitados |

---

<a id="-installation"></a>
## 📥 Instalação

### Com Go
```bash
go install github.com/ffuf/ffuf/v2@latest
```

### Kali Linux
```bash
sudo apt install ffuf
```

### Descarregar o binário
```bash
# Descarregar a partir das versões publicadas
https://github.com/ffuf/ffuf/releases

# Linux
wget https://github.com/ffuf/ffuf/releases/download/v2.x.x/ffuf_2.x.x_linux_amd64.tar.gz
tar xzf ffuf_2.x.x_linux_amd64.tar.gz
sudo mv ffuf /usr/local/bin/
```

### Verificar a instalação
```bash
ffuf -V
ffuf -h
```

---

<a id="-basic-syntax"></a>
## ⌨️ Sintaxe básica

### Sintaxe geral
```bash
ffuf [options] -u <URL/FUZZ> -w <wordlist>
```

### A palavra-chave FUZZ

A palavra-chave **FUZZ** indica ao ffuf onde deve inserir as palavras da lista.

```bash
# No caminho do URL
ffuf -u https://target.com/FUZZ -w wordlist.txt

# No subdomínio
ffuf -u https://FUZZ.target.com -w wordlist.txt

# No valor do parâmetro
ffuf -u https://target.com/page?id=FUZZ -w wordlist.txt

# Nos dados POST
ffuf -u https://target.com/login -X POST -d "user=FUZZ&pass=test" -w wordlist.txt
```

### Opções essenciais

| Opção | Descrição |
|--------|-------------|
| `-u` | URL alvo com a palavra-chave FUZZ |
| `-w` | Caminho da lista de palavras |
| `-o` | Ficheiro de saída |
| `-of` | Formato de saída (json, csv, html) |
| `-t` | Número de threads (predefinição: 40) |
| `-p` | Intervalo entre pedidos |
| `-r` | Seguir redirecionamentos |
| `-v` | Saída detalhada |
| `-s` | Modo silencioso |

---

<a id="-directory-fuzzing"></a>
## 📁 Fuzzing de diretórios

### Enumeração básica de diretórios por força bruta

```bash
# Fuzzing básico de pastas
ffuf -u https://target.com/FUZZ -w /usr/share/wordlists/dirb/common.txt

# Com extensões de ficheiros
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.html,.txt,.bak

# Várias extensões
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.asp,.aspx,.jsp,.html
```

### Análise recursiva

```bash
# Ativar a recursividade
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion

# Profundidade de recursividade
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2

# Recursividade com estratégia definida
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-strategy greedy
```

### Filtrar por código de estado

```bash
# Selecionar apenas respostas 200
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200

# Selecionar vários códigos
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200,301,302,403

# Excluir respostas 404 (alteração do comportamento predefinido)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404

# Selecionar todos e excluir 404
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Listas de palavras populares

```bash
# SecLists
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
```

---

<a id="-subdomain-fuzzing"></a>
## 🌐 Fuzzing de subdomínios

### Deteção de virtual hosts (sites alojados no mesmo servidor)

```bash
# Fuzzing básico de subdomínios
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt

# Filtrar pelo tamanho da resposta (remover a página predefinida)
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt -fs 1234

# Com lista de palavras específica
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

### Fuzzing de subdomínios DNS

```bash
# Subdomínio no URL
ffuf -u https://FUZZ.target.com -w subdomains.txt

# Com resolvedor
ffuf -u https://FUZZ.target.com -w subdomains.txt -r
```

### Listas de palavras comuns para subdomínios

```bash
# SecLists DNS
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

---

<a id="-parameter-fuzzing"></a>
## 🔍 Fuzzing de parâmetros

### Fuzzing de parâmetros GET

```bash
# Efetuar fuzzing do nome do parâmetro
ffuf -u "https://target.com/page?FUZZ=test" -w params.txt

# Efetuar fuzzing do valor de um parâmetro
ffuf -u "https://target.com/page?id=FUZZ" -w values.txt

# Vários parâmetros
ffuf -u "https://target.com/page?W1=test&W2=test" -w params.txt:W1 -w values.txt:W2
```

### Deteção de parâmetros

```bash
# Encontrar parâmetros ocultos
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

---

<a id="-post-data-fuzzing"></a>
## 📝 Fuzzing de dados POST

### Fuzzing básico de POST

```bash
# POST com dados de formulário
ffuf -u https://target.com/login -X POST \
    -d "username=FUZZ&password=test" \
    -w usernames.txt

# POST com tipo de conteúdo
ffuf -u https://target.com/login -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=FUZZ&password=test" \
    -w usernames.txt
```

### Fuzzing de POST com JSON

```bash
# Dados JSON em POST
ffuf -u https://target.com/api/login -X POST \
    -H "Content-Type: application/json" \
    -d '{"username":"FUZZ","password":"test"}' \
    -w usernames.txt
```

### Várias listas de palavras (credential stuffing, reutilização de credenciais divulgadas)

```bash
# Fuzzing de nome de utilizador e palavra-passe
ffuf -u https://target.com/login -X POST \
    -d "username=USER&password=PASS" \
    -w users.txt:USER \
    -w passwords.txt:PASS \
    -fc 401

# Modo: clusterbomb (todas as combinações)
ffuf -u https://target.com/login -X POST \
    -d "user=USER&pass=PASS" \
    -w users.txt:USER \
    -w passes.txt:PASS \
    -mode clusterbomb
```

---

<a id="-filtering--matching"></a>
## 🎯 Filtragem e seleção por correspondência

### Opções de filtragem

| Opção | Descrição | Exemplo |
|--------|-------------|---------|
| `-fc` | Filtrar por código de estado | `-fc 404,403` |
| `-fs` | Filtrar por tamanho da resposta | `-fs 1234` |
| `-fw` | Filtrar por número de palavras | `-fw 100` |
| `-fl` | Filtrar por número de linhas | `-fl 50` |
| `-ft` | Filtrar por tempo (ms) | `-ft 5000` |
| `-fr` | Filtrar por expressão regular | `-fr "error"` |

### Opções de seleção por correspondência

| Opção | Descrição | Exemplo |
|--------|-------------|---------|
| `-mc` | Selecionar códigos de estado | `-mc 200,301` |
| `-ms` | Selecionar por tamanho da resposta | `-ms 1234` |
| `-mw` | Selecionar por número de palavras | `-mw 100` |
| `-ml` | Selecionar por número de linhas | `-ml 50` |
| `-mt` | Selecionar por tempo (ms) | `-mt 5000` |
| `-mr` | Selecionar por expressão regular | `-mr "success"` |

### Exemplos de filtragem

```bash
# Excluir respostas 404 e 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404,403

# Filtrar pelo tamanho (remover a página predefinida)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fs 0,1234

# Filtrar por palavras
ffuf -u https://target.com/FUZZ -w wordlist.txt -fw 10

# Selecionar todos e excluir os específicos
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Calibração automática

```bash
# Calibrar automaticamente os filtros
ffuf -u https://target.com/FUZZ -w wordlist.txt -ac

# Calibrar com uma palavra-chave específica
ffuf -u https://target.com/FUZZ -w wordlist.txt -acc "randomstring123"
```

---

<a id="-advanced-features"></a>
## ⚡ Funcionalidades avançadas

### Limitação da frequência de pedidos

```bash
# Pedidos por segundo
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 100

# Intervalo entre pedidos
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.5

# Intervalo aleatório (jitter)
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.1-1.0
```

### Threads (linhas de execução)

```bash
# Definir threads (predefinição: 40)
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 100

# Reduzir para maior discrição
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 10 -p 1
```

### Cabeçalhos

```bash
# Cabeçalho personalizado
ffuf -u https://target.com/FUZZ -w wordlist.txt -H "X-Custom: value"

# Vários cabeçalhos
ffuf -u https://target.com/FUZZ -w wordlist.txt \
    -H "Authorization: Bearer token" \
    -H "X-Forwarded-For: 127.0.0.1"

# Cookie
ffuf -u https://target.com/FUZZ -w wordlist.txt -b "session=abc123"
```

### Proxy

```bash
# Proxy HTTP
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080

# Burp Suite
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080
```

### Saída

```bash
# Saída JSON
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.json -of json

# Saída CSV
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.csv -of csv

# Saída HTML
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.html -of html

# Todos os formatos
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results -of all
```

### Tempo limite e novas tentativas

```bash
# Tempo limite do pedido
ffuf -u https://target.com/FUZZ -w wordlist.txt -timeout 10

# Parar em respostas 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -se

# Ignorar erros de certificados TLS
ffuf -u https://target.com/FUZZ -w wordlist.txt -k
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: Análise básica de pastas
```bash
ffuf -u https://target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/common.txt -mc 200,301,302
```

### Exemplo 2: encontrar ficheiros PHP
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.php.bak,.php~ -mc 200
```

### Exemplo 3: enumeração de subdomínios
```bash
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    -fs 1234 -t 100
```

### Exemplo 4: deteção de endpoints de API
```bash
ffuf -u https://api.target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -mc 200,401,403
```

### Exemplo 5: Deteção de parâmetros
```bash
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

### Exemplo 6: autenticação por força bruta
```bash
ffuf -u https://target.com/login -X POST \
    -d "user=admin&pass=FUZZ" \
    -w /usr/share/seclists/Passwords/Common-Credentials/10k-most-common.txt \
    -fc 401 -t 10
```

### Exemplo 7: com um proxy (Burp)
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080 -k
```

### Exemplo 8: análise recursiva
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2 -mc 200,301
```

### Exemplo 9: análise com limitação da frequência dos pedidos
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 10 -t 5
```

### Exemplo 10: análise completa para bug bounty
```bash
ffuf -u https://target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt \
    -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak \
    -mc 200,301,302,403 \
    -recursion -recursion-depth 2 \
    -o results.json -of json \
    -ac
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Tarefa | Comando |
|------|---------|
| Análise de diretórios | `ffuf -u URL/FUZZ -w wordlist.txt` |
| Com extensões | `ffuf -u URL/FUZZ -w wordlist.txt -e .php,.html` |
| Análise de subdomínios | `ffuf -u URL -H "Host: FUZZ.domain" -w subs.txt` |
| Fuzzing de POST | `ffuf -u URL -X POST -d "param=FUZZ" -w wordlist.txt` |
| Excluir respostas 404 | `ffuf -u URL/FUZZ -w wordlist.txt -fc 404` |
| Filtrar por tamanho | `ffuf -u URL/FUZZ -w wordlist.txt -fs 1234` |
| Calibrar automaticamente | `ffuf -u URL/FUZZ -w wordlist.txt -ac` |
| Guardar a saída | `ffuf -u URL/FUZZ -w wordlist.txt -o out.json -of json` |

### Opções comuns

| Opção | Descrição |
|--------|-------------|
| `-u` | URL com FUZZ |
| `-w` | Lista de palavras |
| `-e` | Extensões |
| `-t` | Threads (linhas de execução) |
| `-mc` | Selecionar códigos |
| `-fc` | Excluir códigos |
| `-fs` | Filtrar por tamanho |
| `-ac` | Calibrar automaticamente |
| `-r` | Seguir redirecionamentos |
| `-o` | Ficheiro de saída |

---

<a id="-tips--best-practices"></a>
## 💡 Conselhos e boas práticas

### Sugestões para bug bounty

1. **Comece sempre pela calibração automática**
   ```bash
   ffuf -u target.com/FUZZ -w wordlist.txt -ac
   ```

2. **Utilize várias extensões**
   ```bash
   -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak,.old,.zip
   ```

3. **Experimente extensões comuns de cópias de segurança**
   ```bash
   -e .bak,.old,.orig,.backup,~,.swp
   ```

4. **Limite a frequência dos pedidos para contornar WAF**
   ```bash
   -rate 10 -t 5 -p 0.5
   ```

### Conselhos de desempenho

```bash
# Velocidade elevada (sem WAF)
ffuf -u URL/FUZZ -w wordlist.txt -t 200

# Pouco tráfego e baixa velocidade (discrição)
ffuf -u URL/FUZZ -w wordlist.txt -t 10 -rate 5 -p 1
```

---

## ⚠️ Aviso legal

> **AVISO:** O ffuf é uma ferramenta de fuzzing potente que só deve ser utilizada em **testes autorizados**.
> 
> - ✅ Utilize-o em programas nos quais tem autorização para realizar testes
> - ✅ Utilize-o em programas de bug bounty autorizados
> - ✅ Respeite os limites de pedidos e o âmbito autorizado
> - ❌ Nunca faça fuzzing sem autorização
> - ❌ Não provoque negação de serviço
> 
> **A análise não autorizada é ilegal.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [ffuf GitHub](https://github.com/ffuf/ffuf)
- [ffuf Wiki](https://github.com/ffuf/ffuf/wiki)

### Listas de palavras
- [SecLists](https://github.com/danielmiessler/SecLists)
- [Listas de palavras da Assetnote](https://wordlists.assetnote.io/)

### Guias de referência relacionados
- [Nuclei](../../Nuclei/translations/README.pt.md)
- [Gobuster](../../Gobuster/translations/README.pt.md)
- [Nmap](../../Nmap/translations/README.pt.md)

---

<p align="center">
  <b>🚀 Fuzz Faster, Find More!</b><br>
  <i>The bug bounty hunter's fuzzer of choice</i>
</p>
