# 🚀 Gobuster — guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗  ██████╗ ██████╗ ██╗   ██╗███████╗████████╗███████╗██████╗ 
  ██╔════╝ ██╔═══██╗██╔══██╗██║   ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗
  ██║  ███╗██║   ██║██████╔╝██║   ██║███████╗   ██║   █████╗  ██████╔╝
  ██║   ██║██║   ██║██╔══██╗██║   ██║╚════██║   ██║   ██╔══╝  ██╔══██╗
  ╚██████╔╝╚██████╔╝██████╔╝╚██████╔╝███████║   ██║   ███████╗██║  ██║
   ╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
                Ferramenta de força bruta para pastas/DNS/VHosts
```

<p align="center">
  <img src="https://img.shields.io/badge/Gobuster-Enumeration-blue?style=for-the-badge" alt="Gobuster">
  <img src="https://img.shields.io/badge/Directory-Bruteforce-red?style=for-the-badge" alt="Directory">
  <img src="https://img.shields.io/badge/DNS-Subdomain-green?style=for-the-badge" alt="DNS">
  <img src="https://img.shields.io/badge/VHost-Discovery-purple?style=for-the-badge" alt="VHost">
</p>

<p align="center">
  <b>🔍 Ferramenta rápida de enumeração de pastas, DNS e VHosts escrita em Go</b>
</p>

> **Termos usados:** reconhecimento (*reconnaissance*) é a recolha de informações sobre o alvo; enumeração é a identificação organizada de sistemas, serviços ou recursos. Os comandos, opções e valores dos exemplos foram mantidos; a sua disponibilidade depende da versão e da plataforma.

> **Vocabulário:** wordlist é uma lista de palavras/candidatos; threads são tarefas concorrentes. Um virtual host (VHost) permite servir um sítio específico através do nome enviado ao mesmo servidor. Fuzzing consiste em variar entradas para observar respostas.

> **Precisão e versões:** `--no-status` oculta os códigos na apresentação; não passa a incluir respostas 404 nem altera o filtro de resultados. A blacklist pode sobrepor-se a `-s`; confirma as opções da versão usada no [código oficial do modo dir](https://github.com/OJ/gobuster/blob/master/cli/dir/dir.go). O exemplo chamado “vários resolvedores” contém apenas um endereço com a porta 53. As estrelas de velocidade são ilustrativas; a tabela de comparação está desatualizada ao indicar ausência de recursividade no ffuf, que [suporta essa funcionalidade](https://github.com/ffuf/ffuf). Flags longas de TLS e nomes dos ficheiros de versões publicadas variam; os exemplos mantêm a sintaxe do original.

---

## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Visão geral dos modos](#-modes-overview)
- [Modo de pastas (dir)](#-directory-mode-dir)
- [Modo DNS (dns)](#-dns-mode-dns)
- [Modo VHost (vhost)](#-vhost-mode-vhost)
- [Modo de fuzzing (fuzz)](#-fuzz-mode-fuzz)
- [Listas de palavras](#-wordlists)
- [Autenticação e cabeçalhos](#-authentication--headers)
- [Ajuste do desempenho](#-performance-tuning)
- [Exemplos práticos](#-real-world-examples)
- [Referência rápida](#-quick-reference)
- [Conselhos e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

O **Gobuster** é uma ferramenta rápida de força bruta, escrita em Go e com várias threads, que permite:
- Enumerar pastas e ficheiros em servidores Web
- Descobrir subdomínios DNS
- Descobrir virtual hosts (VHosts)
- Fuzzing de URLs

### Porquê o Gobuster?

| Funcionalidade | Descrição |
|---------|-------------|
| **Rápido** | Escrito em Go, com elevado paralelismo |
| **Flexível** | Vários modos para tarefas diferentes |
| **Sem recursividade** | Resultados claros, sem ruído |
| **Estável** | Gere erros de forma controlada |
| **Desenvolvimento ativo** | Atualizações regulares |

### Comparação com outras ferramentas

| Ferramenta | Velocidade | Recursividade | Linguagem |
|------|-------|-----------|----------|
| **Gobuster** | ⭐⭐⭐⭐⭐ | Não | Go |
| Dirb | ⭐⭐ | Sim | C |
| Dirbuster | ⭐⭐⭐ | Sim | Java |
| Ffuf | ⭐⭐⭐⭐⭐ | Não | Go |
| Feroxbuster | ⭐⭐⭐⭐⭐ | Sim | Rust |

---

<a id="-installation"></a>
## 📥 Instalação

### Kali Linux (pré-instalado)
```bash
# Atualizar o Gobuster
sudo apt update && sudo apt install gobuster
```

### Ubuntu/Debian
```bash
sudo apt install gobuster
```

### A partir do código-fonte (Go)
```bash
go install github.com/OJ/gobuster/v3@latest
```

### Utilizar Snap
```bash
sudo snap install gobuster
```

### A partir das versões publicadas no GitHub
```bash
# Descarregar a versão mais recente
wget https://github.com/OJ/gobuster/releases/latest/download/gobuster-linux-amd64.7z
7z x gobuster-linux-amd64.7z
chmod +x gobuster
sudo mv gobuster /usr/local/bin/
```

### Verificar a instalação
```bash
gobuster version
gobuster -h
```

---

<a id="-modes-overview"></a>
## 📊 Visão geral dos modos

### Modos disponíveis

| Modo | Descrição | Caso de utilização |
|------|-------------|----------|
| `dir` | Enumeração de pastas/ficheiros | Encontrar pastas/ficheiros ocultos |
| `dns` | Enumeração de subdomínios DNS | Encontrar subdomínios |
| `vhost` | Enumeração de virtual hosts | Encontrar virtual hosts |
| `fuzz` | Fuzzing de URLs | Efetuar fuzzing de parâmetros/caminhos |
| `s3` | Enumeração de buckets S3 | Encontrar buckets AWS S3 |
| `gcs` | Google Cloud Storage | Encontrar buckets GCS |
| `tftp` | Enumeração TFTP | Encontrar ficheiros TFTP |

### Sintaxe básica
```bash
gobuster [mode] [options]

# Exemplos
gobuster dir -u http://target.com -w wordlist.txt
gobuster dns -d target.com -w subdomains.txt
gobuster vhost -u http://target.com -w vhosts.txt
```

---

<a id="-directory-mode-dir"></a>
## 📁 Modo de pastas (dir)

### Sintaxe básica
```bash
gobuster dir -u <URL> -w <wordlist>
```

### Opções essenciais

| Opção | Descrição |
|--------|-------------|
| `-u` | URL alvo |
| `-w` | Caminho da lista de palavras |
| `-t` | Threads (predefinição: 10) |
| `-x` | Extensões de ficheiros a procurar |
| `-s` | Códigos de estado a incluir |
| `-b` | Códigos de estado a excluir (blacklist) |
| `-o` | Ficheiro de saída |
| `-k` | Ignorar a verificação de certificados TLS |
| `-r` | Seguir redirecionamentos |
| `-a` | Cadeia de caracteres User-Agent |
| `-c` | Cookies |
| `-H` | Cabeçalhos personalizados |

### Análise básica de pastas
```bash
# Análise simples de pastas
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt

# Com mais threads
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt -t 50
```

### Pesquisa por extensões de ficheiros
```bash
# Procurar extensões específicas
gobuster dir -u http://target.com -w wordlist.txt -x php,html,txt

# Várias extensões
gobuster dir -u http://target.com -w wordlist.txt -x php,asp,aspx,jsp,html,txt,bak,old

# Ficheiros de cópia de segurança
gobuster dir -u http://target.com -w wordlist.txt -x bak,backup,old,orig,~
```

### Filtragem por código de estado
```bash
# Mostrar apenas códigos de estado específicos
gobuster dir -u http://target.com -w wordlist.txt -s 200,204,301,302,307,401

# Excluir códigos de estado
gobuster dir -u http://target.com -w wordlist.txt -b 404,403

# Mostrar todos (incluindo 404)
gobuster dir -u http://target.com -w wordlist.txt --no-status
```

### Opções de saída
```bash
# Guardar num ficheiro
gobuster dir -u http://target.com -w wordlist.txt -o results.txt

# Saída detalhada
gobuster dir -u http://target.com -w wordlist.txt -v

# Sem indicação de progresso
gobuster dir -u http://target.com -w wordlist.txt -q

# Modo expandido (URLs completos)
gobuster dir -u http://target.com -w wordlist.txt -e
```

### Opções SSL/TLS
```bash
# Ignorar a verificação de certificados TLS
gobuster dir -u https://target.com -w wordlist.txt -k

# Ignorar a verificação TLS com opções adicionais
gobuster dir -u https://target.com -w wordlist.txt -k --no-tls-validation
```

### Exemplos do modo de pastas
```bash
# Análise habitual de pastas Web
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt -t 50

# Aplicação PHP
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x php -t 50

# Descoberta de endpoints de API
gobuster dir -u http://target.com/api -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt -t 30

# Procurar ficheiros de cópia de segurança
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x bak,backup,old,zip,tar.gz -t 50
```

---

<a id="-dns-mode-dns"></a>
## 🌐 Modo DNS (dns)

### Sintaxe básica
```bash
gobuster dns -d <domain> -w <wordlist>
```

### Opções DNS

| Opção | Descrição |
|--------|-------------|
| `-d` | Domínio alvo |
| `-w` | Caminho da lista de palavras |
| `-t` | Threads |
| `-r` | Resolvedor DNS personalizado |
| `-i` | Mostrar endereços IP |
| `-c` | Mostrar registos CNAME |
| `--wildcard` | Forçar o processamento de wildcards |
| `--timeout` | Tempo limite DNS |

### Enumeração básica de DNS
```bash
# Análise simples de subdomínios
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# Mostrar IPs
gobuster dns -d target.com -w subdomains.txt -i

# Mostrar CNAMEs
gobuster dns -d target.com -w subdomains.txt -c
```

### Resolvedor DNS personalizado
```bash
# Utilizar um servidor DNS específico
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8

# Vários resolvedores
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8:53
```

### Exemplos DNS
```bash
# Análise abrangente de subdomínios
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt -t 50 -i

# Análise rápida de subdomínios
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt -t 100

# Com saída guardada
gobuster dns -d target.com -w subdomains.txt -i -o subdomains_found.txt
```

---

<a id="-vhost-mode-vhost"></a>
## 🏠 Modo VHost (vhost)

### Sintaxe básica
```bash
gobuster vhost -u <URL> -w <wordlist>
```

### Opções VHost

| Opção | Descrição |
|--------|-------------|
| `-u` | URL alvo |
| `-w` | Caminho da lista de palavras |
| `-t` | Threads |
| `--append-domain` | Acrescentar o domínio às entradas da lista de palavras |
| `--domain` | Domínio para o virtual host |
| `-r` | Seguir redirecionamentos |

### Enumeração básica de VHosts
```bash
# Análise simples de VHosts
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# Com o domínio acrescentado
gobuster vhost -u http://target.com -w subdomains.txt --append-domain
```

### Exemplos de VHosts
```bash
# Descoberta habitual de VHosts
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/namelist.txt -t 50

# Sistemas de desenvolvimento
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt --append-domain

# Com um domínio personalizado
gobuster vhost -u http://10.10.10.10 -w subdomains.txt --domain target.htb
```

---

<a id="-fuzz-mode-fuzz"></a>
## 🔧 Modo de fuzzing (fuzz)

### Sintaxe básica
```bash
gobuster fuzz -u <URL with FUZZ> -w <wordlist>
```

### Opções de fuzzing

| Opção | Descrição |
|--------|-------------|
| `-u` | URL com a palavra-chave FUZZ |
| `-w` | Caminho da lista de palavras |
| `-t` | Threads |
| `-b` | Códigos de estado a excluir |
| `--exclude-length` | Excluir pelo tamanho da resposta |

### Exemplos de fuzzing
```bash
# Efetuar fuzzing do valor de um parâmetro
gobuster fuzz -u "http://target.com/page?id=FUZZ" -w numbers.txt

# Efetuar fuzzing do caminho
gobuster fuzz -u "http://target.com/FUZZ/admin" -w wordlist.txt

# Excluir tamanhos específicos
gobuster fuzz -u "http://target.com/FUZZ" -w wordlist.txt --exclude-length 0,42
```

---

<a id="-wordlists"></a>
## 📚 Listas de palavras

### Localizações do SecLists (Kali Linux)

```bash
# Enumeração de pastas/ficheiros
/usr/share/seclists/Discovery/Web-Content/

# Listas de pastas populares
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-small.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# DNS/subdomínios
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

### Listas de palavras predefinidas

```bash
# Listas de palavras do Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
/usr/share/wordlists/dirb/small.txt

# Listas de palavras do Dirbuster
/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
/usr/share/wordlists/dirbuster/directory-list-2.3-small.txt
```

### Listas de palavras recomendadas

| Caso de utilização | Lista de palavras |
|----------|----------|
| Análise rápida | `common.txt` |
| Habitual | `directory-list-2.3-medium.txt` |
| Exaustiva | `directory-list-2.3-big.txt` |
| API | `api-endpoints.txt` |
| Subdomínios | `subdomains-top1million-5000.txt` |
| Ficheiros | `raft-large-files.txt` |

---

<a id="-authentication--headers"></a>
## 🔐 Autenticação e cabeçalhos

### Autenticação Basic
```bash
# Autenticação HTTP Basic
gobuster dir -u http://target.com -w wordlist.txt -U username -P password
```

### Autenticação por cookie
```bash
# Um cookie
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123"

# Vários cookies
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123; token=xyz789"

# Sessão PHP
gobuster dir -u http://target.com -w wordlist.txt -c "PHPSESSID=abc123def456"
```

### Cabeçalhos personalizados
```bash
# Um cabeçalho
gobuster dir -u http://target.com -w wordlist.txt -H "Authorization: Bearer token123"

# Vários cabeçalhos
gobuster dir -u http://target.com -w wordlist.txt -H "X-Forwarded-For: 127.0.0.1" -H "X-Custom: value"

# Chave de API
gobuster dir -u http://target.com/api -w wordlist.txt -H "X-API-Key: your-api-key"
```

### User-Agent
```bash
# User-Agent personalizado
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Googlebot
gobuster dir -u http://target.com -w wordlist.txt -a "Googlebot/2.1"
```

### Suporte de proxy
```bash
# Proxy HTTP
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080

# Proxy SOCKS5
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050

# Proxy Burp Suite
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080 -k
```

---

<a id="-performance-tuning"></a>
## ⚡ Ajuste do desempenho

### Gestão de threads
```bash
# Predefinição: 10 threads
gobuster dir -u http://target.com -w wordlist.txt

# Aumentar as threads (rede rápida)
gobuster dir -u http://target.com -w wordlist.txt -t 100

# Reduzir as threads (rede lenta/instável)
gobuster dir -u http://target.com -w wordlist.txt -t 5
```

### Definições de tempo limite
```bash
# Aumentar o tempo limite
gobuster dir -u http://target.com -w wordlist.txt --timeout 30s

# Tempo limite DNS
gobuster dns -d target.com -w subdomains.txt --timeout 5s
```

### Intervalo entre pedidos
```bash
# Adicionar um intervalo (limitação da frequência de pedidos)
gobuster dir -u http://target.com -w wordlist.txt --delay 100ms

# Mais lento para evasão de WAF
gobuster dir -u http://target.com -w wordlist.txt --delay 500ms
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: enumeração Web habitual
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt \
    -t 50 \
    -x php,html,txt \
    -o results.txt
```

### Exemplo 2: descoberta de endpoints de API
```bash
gobuster dir -u http://target.com/api/v1 \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -t 30 \
    -H "Content-Type: application/json"
```

### Exemplo 3: análise autenticada
```bash
gobuster dir -u http://target.com/admin \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -c "PHPSESSID=abc123" \
    -t 20
```

### Exemplo 4: enumeração de subdomínios
```bash
gobuster dns -d target.com \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt \
    -t 100 \
    -i \
    -o subdomains.txt
```

### Exemplo 5: descoberta de VHosts (HackTheBox)
```bash
gobuster vhost -u http://10.10.10.10 \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    --domain target.htb \
    --append-domain
```

### Exemplo 6: através do proxy Burp
```bash
gobuster dir -u https://target.com \
    -w /usr/share/wordlists/dirb/common.txt \
    --proxy http://127.0.0.1:8080 \
    -k \
    -t 20
```

### Exemplo 7: procura de cópias de segurança
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -x bak,backup,old,orig,save,swp,txt,zip,tar,gz,sql \
    -t 50
```

### Exemplo 8: deteção de CMS
```bash
# WordPress
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/wordpress.fuzz.txt \
    -t 50

# Joomla
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/joomla-plugins.fuzz.txt
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Tarefa | Comando |
|------|---------|
| Análise básica de pastas | `gobuster dir -u URL -w wordlist.txt` |
| Com extensões | `gobuster dir -u URL -w wordlist.txt -x php,html` |
| Análise de subdomínios | `gobuster dns -d domain.com -w wordlist.txt` |
| Análise de VHosts | `gobuster vhost -u URL -w wordlist.txt` |
| Com saída guardada | `gobuster dir -u URL -w wordlist.txt -o output.txt` |
| Ignorar erros de certificados TLS | `gobuster dir -u URL -w wordlist.txt -k` |
| Com cookies | `gobuster dir -u URL -w wordlist.txt -c "session=x"` |
| Através de proxy | `gobuster dir -u URL -w wordlist.txt --proxy http://127.0.0.1:8080` |

### Opções comuns

| Opção | Forma curta | Descrição |
|--------|-------|-------------|
| `--url` | `-u` | URL alvo |
| `--wordlist` | `-w` | Ficheiro da lista de palavras |
| `--threads` | `-t` | Número de threads |
| `--extensions` | `-x` | Extensões de ficheiros |
| `--status-codes` | `-s` | Códigos de estado a incluir |
| `--cookies` | `-c` | Cookies a enviar |
| `--output` | `-o` | Ficheiro de saída |
| `--insecuressl` | `-k` | Ignorar a verificação de certificados TLS |
| `--verbose` | `-v` | Saída detalhada |
| `--quiet` | `-q` | Modo silencioso |

---

<a id="-tips--best-practices"></a>
## 💡 Conselhos e boas práticas

### Conselhos de eficiência

1. **Começar com uma lista pequena e aumentar gradualmente**
   ```bash
   # Começar por uma análise rápida
   gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt
   
   # Depois, utilizar uma lista de palavras maior
   gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
   ```

2. **Extensões adequadas ao alvo**
   ```bash
   # Sítio PHP
   gobuster dir -u http://target.com -w wordlist.txt -x php
   
   # Sítio ASP.NET
   gobuster dir -u http://target.com -w wordlist.txt -x asp,aspx
   ```

3. **Combinar com outras ferramentas**
   ```bash
   # Fluxo Nmap → Gobuster
   nmap -sV -p 80,443 target.com
   gobuster dir -u http://target.com -w wordlist.txt
   ```

### Evitar a deteção

```bash
# Análise lenta com intervalo entre pedidos
gobuster dir -u http://target.com -w wordlist.txt -t 5 --delay 200ms

# User-Agent personalizado
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0..."

# Através de Tor
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050
```

---

## ⚠️ Aviso legal

> **AVISO:** O Gobuster é uma ferramenta poderosa de enumeração que deve ser utilizada apenas em **testes autorizados**.
> 
> - ✅ Testar sistemas que te pertençam
> - ✅ Testar com autorização escrita explícita
> - ✅ Utilizar em desafios CTF e laboratórios
> - ❌ Nunca analisar sem autorização
> - ❌ A enumeração não autorizada pode ser ilegal
> 
> **Obtém sempre autorização antes de analisar qualquer sistema.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [Gobuster GitHub](https://github.com/OJ/gobuster)
- [Gobuster Wiki](https://github.com/OJ/gobuster/wiki)

### Listas de palavras
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)

### Guias de referência relacionados
- [Nmap](../../Nmap/translations/README.pt.md)
- [Burp Suite](../../Burp-Suite/translations/README.pt.md)
- [SQLMap](../../SQLMap/translations/README.pt.md)

---

<p align="center">
  <b>🚀 Domina a enumeração Web!</b><br>
  <i>Descobre o que os outros não conseguem ver</i>
</p>
