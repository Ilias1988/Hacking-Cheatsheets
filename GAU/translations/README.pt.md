# 🔗 GAU — Get All URLs

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de precisão:** `--subs` inclui subdomínios, não ativa um modo silencioso; `--blacklist` exclui extensões, não fornecedores. `--mt` e `--ft` selecionam ou excluem tipos MIME, não expressões regulares de extensões. Os códigos de estado recolhidos de arquivos não garantem o estado atual dos URLs; confirme os sistemas ativos separadamente. Os exemplos originais foram preservados. [Opções oficiais do GAU](https://github.com/lc/gau#usage). Um *endpoint* é um ponto de acesso de uma aplicação ou API.

```
   ██████╗  █████╗ ██╗   ██╗
  ██╔════╝ ██╔══██╗██║   ██║
  ██║  ███╗███████║██║   ██║
  ██║   ██║██╔══██║██║   ██║
  ╚██████╔╝██║  ██║╚██████╔╝
   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ 
       Get All URLs — recolha rápida de URLs
```

<p align="center">
  <img src="https://img.shields.io/badge/GAU-URL_Discovery-blue?style=for-the-badge" alt="GAU">
  <img src="https://img.shields.io/badge/Wayback-green?style=for-the-badge" alt="Wayback">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [O que é o GAU](#-what-is-gau)
- [Instalação](#-installation)
- [Utilização básica](#-basic-usage)
- [Fornecedores de dados](#-providers)
- [Filtragem](#-filtering)
- [Integração](#-integration)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-gau"></a>
## 🎯 O que é o GAU

O **GAU** (Get All URLs) recolhe URLs conhecidos a partir de várias fontes:

- 🔍 **Wayback Machine** - URLs históricos
- 🌐 **Common Crawl** - Dados de rastreio da Web
- 📡 **AlienVault OTX** - URLs de informação sobre ameaças
- 🔒 **URLScan.io** - URLs analisados

### Porquê GAU?

| Vantagem | Descrição |
|---------|-------------|
| **Reconhecimento passivo** | Sem contacto direto com o alvo |
| **Dados históricos** | Encontrar endpoints antigos ou esquecidos |
| **Rápido** | Recolha em paralelo |
| **Várias fontes** | Resultados abrangentes |

---

<a id="-installation"></a>
## 🚀 Instalação

```bash
# Instalação com Go (recomendada)
go install github.com/lc/gau/v2/cmd/gau@latest

# A partir do código-fonte
git clone https://github.com/lc/gau.git
cd gau/cmd/gau
go build

# Verificar
gau --version
```

---

<a id="-basic-usage"></a>
## 📖 Utilização básica

### Um único domínio

```bash
# Utilização básica
gau example.com

# Com ficheiro de saída
gau example.com -o urls.txt

# Modo silencioso (sem banner)
gau --subs example.com
```

### Vários domínios

```bash
# A partir da entrada padrão
echo "example.com" | gau

# Vários domínios
cat domains.txt | gau

# A partir de ficheiro
gau --threads 5 < domains.txt
```

### Incluir subdomínios

```bash
# Incluir subdomínios nos resultados
gau --subs example.com
```

### Utilização de threads

```bash
# Definir threads (predefinição: 1)
gau --threads 5 example.com

# Ajustar à limitação de frequência
gau --threads 2 example.com
```

---

<a id="-providers"></a>
## 🌐 Fornecedores de dados

### Fornecedores disponíveis

| Fornecedor | Descrição | Predefinido |
|----------|-------------|---------|
| `wayback` | Wayback Machine | ✅ |
| `commoncrawl` | Common Crawl | ✅ |
| `otx` | AlienVault OTX | ✅ |
| `urlscan` | URLScan.io | ✅ |

### Selecionar fornecedores

```bash
# Utilizar apenas fornecedores específicos
gau --providers wayback example.com
gau --providers wayback,otx example.com

# Excluir fornecedor
gau --blacklist commoncrawl example.com
```

### Configuração dos fornecedores

```bash
# Localização do ficheiro de configuração
~/.gau.toml

# Exemplo de configuração
cat > ~/.gau.toml << EOF
threads = 5
verbose = false
retries = 3
subdomains = false
providers = ["wayback","commoncrawl","otx","urlscan"]
blacklist = []
json = false
EOF
```

---

<a id="-filtering"></a>
## 🔍 Filtragem

### Filtrar por código de estado

```bash
# Apenas URLs que devolveram 200
gau --mc 200 example.com

# Vários códigos de estado
gau --mc 200,301,302 example.com

# Excluir códigos específicos
gau --fc 404,500 example.com
```

### Filtrar por extensão

```bash
# Selecionar extensões
gau --mt "\.js$" example.com
gau --mt "\.php$" example.com

# Excluir extensões
gau --ft "\.jpg$|\.png$|\.gif$|\.css$" example.com
```

### Filtrar por tipo MIME

```bash
# Filtrar pelo tipo de conteúdo
gau --mt "application/json" example.com
gau --mt "text/html" example.com
```

### Formato de saída

```bash
# Saída JSON
gau --json example.com

# Modo detalhado
gau --verbose example.com
```

---

<a id="-integration"></a>
## 🔗 Integração

### Com httpx

```bash
# Verificar URLs ativos
gau example.com | httpx -silent

# Com códigos de estado
gau example.com | httpx -sc -title
```

### Com grep e extração de parâmetros

```bash
# Encontrar URLs com parâmetros
gau example.com | grep "="

# Encontrar padrões específicos
gau example.com | grep -E "\.php\?|\.asp\?"

# Padrões GF
gau example.com | gf xss
gau example.com | gf sqli
gau example.com | gf lfi
gau example.com | gf redirect
```

### Com urldedupe

```bash
# Remover duplicados
gau example.com | urldedupe

# Alternativa: sort -u
gau example.com | sort -u
```

### Com uro

```bash
# Remoção inteligente de URLs duplicados
gau example.com | uro
```

### Cadeia de ferramentas completa

```bash
# Pipeline completo de recolha de URLs
echo "example.com" | gau --subs --threads 5 | \
  uro | \
  httpx -silent -mc 200 | \
  grep "=" > params.txt
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos básicos

| Comando | Descrição |
|---------|-------------|
| `gau domain.com` | Obter URLs de um domínio |
| `gau --subs domain.com` | Incluir subdomínios |
| `gau -o urls.txt domain.com` | Guardar a saída num ficheiro |
| `gau --threads 5 domain.com` | Com várias threads |

### Opções de filtragem

| Opção | Descrição |
|------|-------------|
| `--mc 200` | Selecionar por código de estado |
| `--fc 404` | Excluir por código de estado |
| `--mt "regex"` | Selecionar por expressão regular |
| `--ft "regex"` | Excluir por expressão regular |
| `--json` | Saída JSON |

### Opções dos fornecedores

| Opção | Descrição |
|------|-------------|
| `--providers wayback,otx` | Utilizar fornecedores específicos |
| `--blacklist commoncrawl` | Excluir fornecedor |

### Comandos de uma linha úteis

```bash
# Obter todos os ficheiros JS
gau example.com | grep -E "\.js$" | sort -u

# Encontrar endpoints de API
gau example.com | grep -iE "/api/|/v[0-9]/"

# Encontrar parâmetros
gau example.com | grep "?" | grep "=" | sort -u

# Procurar possíveis casos de SQLi
gau example.com | gf sqli

# Procurar possíveis casos de XSS
gau example.com | gf xss

# URLs ativos com parâmetros
gau example.com | grep "=" | httpx -silent
```

---

## 🎯 Fluxo de trabalho de bug bounty

```bash
# 1. Recolher todos os URLs
gau --subs --threads 5 target.com > all_urls.txt

# 2. Selecionar URLs de interesse
cat all_urls.txt | grep "=" | uro > params.txt

# 3. Verificar se estão ativos
cat params.txt | httpx -silent > live_params.txt

# 4. Procurar vulnerabilidades
cat live_params.txt | gf sqli > sqli.txt
cat live_params.txt | gf xss > xss.txt
cat live_params.txt | gf lfi > lfi.txt
cat live_params.txt | gf redirect > redirect.txt

# 5. Testar
cat xss.txt | qsreplace '"><script>alert(1)</script>' | \
  httpx -silent -mc 200
```

---

## 📚 Recursos

- [GAU GitHub](https://github.com/lc/gau)
- [Padrões do GF](https://github.com/1ndianl33t/Gf-Patterns)

### Guias de referência relacionados
- [Metodologia de bug bounty](../../Bug-Bounty-Methodology/translations/README.pt.md)
- [Subfinder](../../Subfinder/translations/README.pt.md)
- [httpx](../../httpx/translations/README.pt.md)

---

<p align="center">
  <b>🔗 Recolha todos os URLs!</b><br>
  <i>GAU: deteção passiva de URLs</i>
</p>
