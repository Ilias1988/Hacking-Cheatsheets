# ⚔️ Katana — crawler Web de nova geração

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de versão:** `-cs` define expressões regulares de âmbito; a estratégia de rastreio é selecionada com `-s`. `-timeout` é um tempo limite de pedido em segundos, não uma espera de JavaScript em milissegundos. Opções de capturas de ecrã, formulários e filtros apresentadas no original podem divergir da versão instalada. [Opções oficiais do Katana](https://docs.projectdiscovery.io/opensource/katana/usage). *Crawler/crawling* designa o rastreio automático de ligações; *headless* significa sem interface gráfica.

```
  ██╗  ██╗ █████╗ ████████╗ █████╗ ███╗   ██╗ █████╗ 
  ██║ ██╔╝██╔══██╗╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗
  █████╔╝ ███████║   ██║   ███████║██╔██╗ ██║███████║
  ██╔═██╗ ██╔══██║   ██║   ██╔══██║██║╚██╗██║██╔══██║
  ██║  ██╗██║  ██║   ██║   ██║  ██║██║ ╚████║██║  ██║
  ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝
         Framework de crawling Web de nova geração
```

<p align="center">
  <img src="https://img.shields.io/badge/Katana-ProjectDiscovery-blue?style=for-the-badge" alt="Katana">
  <img src="https://img.shields.io/badge/Web_Crawler-green?style=for-the-badge" alt="Crawler">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [O que é o Katana](#-what-is-katana)
- [Instalação](#-installation)
- [Utilização básica](#-basic-usage)
- [Opções de rastreio](#-crawling-options)
- [Modo sem interface gráfica (headless)](#-headless-mode)
- [Saída e filtragem](#-output--filtering)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-katana"></a>
## 🎯 O que é o Katana

O **Katana** é uma framework de rastreio Web de nova geração da ProjectDiscovery:

- ⚡ **Rápido** - Desenvolvido em Go, com elevado paralelismo
- 🔍 **Renderização de JavaScript** - Suporte de navegador sem interface gráfica
- 📡 **Web moderna** - Suporta aplicações de página única (SPA) e conteúdo dinâmico
- 🎯 **Orientado para bug bounty** - Desenvolvido para testes de segurança

### Porquê o Katana?

| Funcionalidade | Vantagem |
|---------|---------|
| Chrome sem interface gráfica | Rastrear aplicações com uso intensivo de JavaScript |
| Cabeçalhos personalizados | Rastreio com autenticação |
| Controlo do âmbito | Respeitar o âmbito autorizado |
| Preenchimento de formulários | Descobrir mais endpoints |

---

<a id="-installation"></a>
## 🚀 Instalação

```bash
# Instalação com Go (recomendada)
go install github.com/projectdiscovery/katana/cmd/katana@latest

# Docker
docker pull projectdiscovery/katana

# Verificar
katana -version
```

---

<a id="-basic-usage"></a>
## 📖 Utilização básica

### Alvo individual

```bash
# Crawling básico
katana -u https://example.com

# Guardar a saída
katana -u https://example.com -o urls.txt

# Modo silencioso
katana -u https://example.com -silent
```

### Vários alvos

```bash
# A partir da entrada padrão
echo "https://example.com" | katana

# Vários URLs
cat targets.txt | katana

# A partir de ficheiro
katana -list targets.txt
```

### Profundidade do rastreio

```bash
# Definir a profundidade (predefinição: 3)
katana -u https://example.com -d 5

# Profundidade 1 (apenas a página alvo)
katana -u https://example.com -d 1
```

---

<a id="-crawling-options"></a>
## 🕷️ Opções de rastreio

### Controlo do âmbito

```bash
# Efetuar crawling apenas dentro do âmbito (mesmo domínio)
katana -u https://example.com -fs dn

# Opções de âmbito:
# dn — nome de domínio
# rdn — domínio raiz
# fqdn — nome de domínio completo

# Âmbito definido por campo
katana -u https://example.com -fs rdn
```

### Estratégia de rastreio

```bash
# Pesquisa em profundidade (predefinição)
katana -u https://example.com -cs depth-first

# Pesquisa em largura
katana -u https://example.com -cs breadth-first
```

### Concorrência

```bash
# Pedidos concorrentes (predefinição: 10)
katana -u https://example.com -c 50

# Limite da frequência (pedidos por segundo)
katana -u https://example.com -rl 100

# Paralelismo
katana -u https://example.com -p 10
```

### Duração do rastreio

```bash
# Definir o tempo limite (segundos)
katana -u https://example.com -timeout 30

# Tempo máximo de crawling
katana -u https://example.com -ct 300
```

### Preenchimento automático de formulários

```bash
# Ativar o preenchimento de formulários
katana -u https://example.com -aff

# Com valores personalizados
katana -u https://example.com -aff -fv "username:admin,password:test123"
```

---

<a id="-headless-mode"></a>
## 🌐 Modo sem interface gráfica (headless)

### Ativar o navegador sem interface gráfica

```bash
# Ativar Chrome sem interface gráfica
katana -u https://example.com -headless

# Navegador sem interface gráfica com profundidade definida
katana -u https://example.com -headless -d 3
```

### Opções do modo sem interface gráfica

```bash
# Caminho personalizado do Chrome
katana -u https://example.com -headless -system-chrome

# Sem isolamento do navegador (para root)
katana -u https://example.com -headless -no-sandbox

# Capturar imagens das páginas
katana -u https://example.com -headless -screenshot
```

### Rastreio de JavaScript

```bash
# Aguardar o carregamento de JS (ms)
katana -u https://example.com -headless -timeout 5

# Recolher endpoints em JS
katana -u https://example.com -js-crawl
```

---

<a id="-output--filtering"></a>
## 📤 Saída e filtragem

### Formatos de saída

```bash
# Saída padrão para ficheiro
katana -u https://example.com -o results.txt

# Saída JSON
katana -u https://example.com -jsonl -o results.json

# Sem cores
katana -u https://example.com -nc
```

### Campos apresentados

```bash
# Mostrar campos específicos
katana -u https://example.com -f url,method,status

# Campos disponíveis:
# url, path, fqdn, rdn, rurl, qurl, qpath
# file, kv, dir, ufile, udir
# method, status, body, response, header
```

### Filtrar por extensão

```bash
# Selecionar extensões
katana -u https://example.com -em js,php,asp

# Excluir extensões
katana -u https://example.com -ef png,jpg,gif,css,svg,woff
```

### Filtrar por código de estado

```bash
# Selecionar por código de estado
katana -u https://example.com -mc 200,301,302

# Excluir por código de estado
katana -u https://example.com -fc 404,500
```

### Cabeçalhos personalizados

```bash
# Adicionar um cabeçalho
katana -u https://example.com -H "Authorization: Bearer TOKEN"

# Vários cabeçalhos
katana -u https://example.com -H "Cookie: session=abc" -H "User-Agent: Custom"
```

### Suporte de proxy

```bash
# Proxy HTTP
katana -u https://example.com -proxy http://127.0.0.1:8080

# Para Burp Suite
katana -u https://example.com -proxy http://127.0.0.1:8080
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos básicos

| Comando | Descrição |
|---------|-------------|
| `katana -u URL` | Rastrear um único URL |
| `katana -list file.txt` | Rastrear URLs de um ficheiro |
| `katana -u URL -d 5` | Definir a profundidade como 5 |
| `katana -u URL -headless` | Ativar o modo sem interface gráfica |
| `katana -u URL -o out.txt` | Guardar os resultados |

### Opções de âmbito

| Opção | Descrição |
|------|-------------|
| `-fs dn` | Mesmo domínio |
| `-fs rdn` | Domínio de raiz |
| `-fs fqdn` | Domínio completo |
| `-cs depth-first` | Rastreio em profundidade |
| `-cs breadth-first` | Rastreio em largura |

### Opções de desempenho

| Opção | Descrição |
|------|-------------|
| `-c 50` | Concorrência |
| `-p 10` | Paralelismo |
| `-rl 100` | Limite da frequência de pedidos |
| `-timeout 30` | Tempo limite do pedido |

### Opções de filtragem

| Opção | Descrição |
|------|-------------|
| `-em js,php` | Selecionar extensões |
| `-ef png,jpg` | Excluir extensões |
| `-mc 200` | Selecionar códigos de estado |
| `-fc 404` | Excluir códigos de estado |

---

## 🎯 Fluxos de trabalho de bug bounty

### Rastreio básico

```bash
# Crawling rápido
katana -u https://target.com -d 3 -o urls.txt
```

### Rastreio abrangente

```bash
# Crawling aprofundado com JS
katana -u https://target.com -d 5 -headless -c 50 \
  -ef png,jpg,gif,css,svg,woff,woff2,ttf,eot,ico \
  -o all_urls.txt
```

### Deteção de parâmetros

```bash
# Encontrar URLs com parâmetros
katana -u https://target.com -d 3 | grep "?" | sort -u

# Com grep para parâmetros específicos
katana -u https://target.com | grep -E "id=|user=|admin=|file="
```

### Em conjunto com outras ferramentas

```bash
# Katana + httpx
katana -u https://target.com -d 3 | httpx -silent -sc

# Katana + Nuclei
katana -u https://target.com -d 3 | nuclei -t nuclei-templates/

# Pipeline completo
subfinder -d target.com | httpx -silent | katana -d 3 | grep "=" | sort -u
```

### Rastreio com autenticação

```bash
# Com um cookie de sessão
katana -u https://target.com \
  -H "Cookie: session=abc123" \
  -d 5 \
  -headless \
  -o authenticated_urls.txt
```

---

## 📚 Recursos

- [Katana GitHub](https://github.com/projectdiscovery/katana)
- [ProjectDiscovery](https://projectdiscovery.io/)

### Guias de referência relacionados
- [Metodologia de bug bounty](../../Bug-Bounty-Methodology/translations/README.pt.md)
- [httpx](../../httpx/translations/README.pt.md)
- [Nuclei](../../Nuclei/translations/README.pt.md)
- [GAU](../../GAU/translations/README.pt.md)

---

<p align="center">
  <b>⚔️ Rastreie tudo!</b><br>
  <i>Katana — crawler Web de nova geração</i>
</p>
