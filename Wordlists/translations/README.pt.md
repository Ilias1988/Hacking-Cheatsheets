# 📚 Listas de palavras — Guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ██╗███████╗████████╗███████╗
  ██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██║██╔════╝╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     ██║███████╗   ██║   ███████╗
  ██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██║╚════██║   ██║   ╚════██║
  ╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗██║███████║   ██║   ███████║
   ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Wordlists-Reference-red?style=for-the-badge" alt="Listas de palavras">
  <img src="https://img.shields.io/badge/SecLists-orange?style=for-the-badge" alt="SecLists">
  <img src="https://img.shields.io/badge/Brute_Force-blue?style=for-the-badge" alt="Força bruta">
  <img src="https://img.shields.io/badge/Fuzzing-green?style=for-the-badge" alt="Fuzzing">
</p>

<p align="center">
  <b>📖 Referência completa de listas de palavras para testes de intrusão</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Listas de palavras-passe](#-password-wordlists)
- [Listas de diretórios e ficheiros](#-directoryfile-wordlists)
- [Listas de subdomínios](#-subdomain-wordlists)
- [Listas de nomes de utilizador](#-username-wordlists)
- [Listas para fuzzing web](#-web-fuzzing-wordlists)
- [Coleção SecLists](#-seclists-collection)
- [Listas de palavras predefinidas no Kali](#-kali-default-wordlists)
- [Ferramentas de criação de listas de palavras](#-custom-wordlist-tools)
- [Download rápido](#-quick-download)
- [Dicas e boas práticas](#-tips--best-practices)

---

<a id="-password-wordlists"></a>
Uma wordlist é uma lista de candidatos usada em testes, como palavras-passe, nomes de ficheiros ou subdomínios. Enumeração é a descoberta sistemática de informação; fuzzing testa entradas variadas, incluindo casos limite. Uma máscara descreve um padrão de geração; uma regra transforma candidatos.

## 🔐 Listas de palavras-passe

### As mais populares

> **Versões e medidas:** os nomes, árvores de diretórios, tamanhos e contagens são os do original. Variam com a revisão da coleção, a compressão e o empacotamento; confirma a estrutura no [SecLists oficial](https://github.com/danielmiessler/SecLists) ou no [pacote Kali](https://www.kali.org/tools/seclists/). Uma dimensão maior não garante cobertura completa.

| Lista de palavras | Tamanho | Linhas | Origem | Caso de utilização |
|----------|------|-------|--------|----------|
| **rockyou.txt** | 139 MB | 14.3M | Kali/SecLists | Quebra de palavras-passe |
| **darkweb2017-top10000.txt** | 83 KB | 10K | SecLists | Palavras-passe comuns |
| **xato-net-10-million-passwords.txt** | 83 MB | 10M | SecLists | Lista grande de palavras-passe |
| **common-passwords-win.txt** | - | - | SecLists | Palavras-passe Windows |
| **10-million-password-list-top-1000000.txt** | 8.1 MB | 1M | SecLists | As 1M palavras-passe mais frequentes |

### Listas de palavras-passe por tipo

```
SecLists/Passwords/
├── Common-Credentials/
│   ├── 10-million-password-list-top-100.txt
│   ├── 10-million-password-list-top-1000.txt
│   ├── 10-million-password-list-top-10000.txt
│   ├── 10-million-password-list-top-100000.txt
│   └── 10-million-password-list-top-1000000.txt
├── Leaked-Databases/
│   └── rockyou.txt
├── darkweb2017-top1000.txt
├── darkweb2017-top10000.txt
└── Default-Credentials/
    ├── default-passwords.txt
    └── tomcat-betterdefaultpasslist.txt
```

### Listas especializadas de palavras-passe

| Lista de palavras | Finalidade | Localização |
|----------|---------|----------|
| **wifi-passwords.txt** | Quebra de palavras-passe WiFi | SecLists/Passwords/ |
| **probable-v2-top12000.txt** | Análise estatística | SecLists/Passwords/ |
| **seasons-only.txt** | Padrões sazonais | SecLists/Passwords/Honeypot-Captures/ |
| **twitter-banned.txt** | Palavras-passe proibidas | SecLists/Passwords/ |
| **cirt-default-passwords.txt** | Credenciais predefinidas | SecLists/Passwords/Default-Credentials/ |

### Localizações no Kali

```bash
# RockYou (descomprimir primeiro)
/usr/share/wordlists/rockyou.txt.gz
gunzip /usr/share/wordlists/rockyou.txt.gz

# John the Ripper
/usr/share/john/password.lst

# Metasploit
/usr/share/metasploit-framework/data/wordlists/
```

---

<a id="-directoryfile-wordlists"></a>
## 📂 Listas de diretórios e ficheiros

### Descoberta de diretórios por força bruta

> **Precisão da origem:** `common.txt` é um nome genérico. A lista usada no caminho `/usr/share/dirb/wordlists/` pertence ao DIRB, enquanto DirBuster tem a sua própria coleção; não assumes que o nome identifica a mesma lista em todas as ferramentas. Ver a [referência oficial de DIRB no Kali](https://www.kali.org/tools/dirb/).

| Lista de palavras | Tamanho | Linhas | Origem | Caso de utilização |
|----------|------|-------|--------|----------|
| **common.txt** | 36 KB | 4.6K | DirBuster | Análise rápida |
| **directory-list-2.3-small.txt** | 756 KB | 87K | DirBuster | Enumeração reduzida |
| **directory-list-2.3-medium.txt** | 1.9 MB | 220K | DirBuster | Enumeração normal |
| **directory-list-2.3-big.txt** | 14 MB | 1.2M | DirBuster | Enumeração completa |
| **big.txt** | 210 KB | 20K | SecLists | Diretórios comuns |

### Série RAFT (fortemente recomendada!)

```
SecLists/Discovery/Web-Content/
├── raft-large-directories.txt      # 62K diretórios
├── raft-large-files.txt            # 37K ficheiros
├── raft-large-words.txt            # 120K palavras
├── raft-medium-directories.txt     # 30K diretórios
├── raft-medium-files.txt           # 17K ficheiros
├── raft-medium-words.txt           # 63K palavras
├── raft-small-directories.txt      # 20K diretórios
├── raft-small-files.txt            # 11K ficheiros
└── raft-small-words.txt            # 43K palavras
```

### Listas de extensões de ficheiro

| Lista de palavras | Finalidade |
|----------|---------|
| **web-extensions.txt** | Extensões web comuns |
| **backup-extensions.txt** | Cópias de segurança (.bak, .old) |
| **raft-large-extensions.txt** | Lista grande de extensões |

### Descoberta especializada

| Lista de palavras | Finalidade | Linhas |
|----------|---------|-------|
| **apache.txt** | Específica para Apache | - |
| **nginx.txt** | Específica para Nginx | - |
| **iis.txt** | Específica para IIS | - |
| **api-endpoints.txt** | Descoberta de APIs | - |
| **graphql.txt** | Endpoints GraphQL | - |
| **swagger.txt** | Swagger/OpenAPI | - |

### Localizações comuns

```bash
# DirBuster
/usr/share/dirbuster/wordlists/
/usr/share/dirb/wordlists/

# Descoberta com SecLists
/usr/share/seclists/Discovery/Web-Content/

# Downloads rápidos
common.txt
directory-list-2.3-medium.txt
raft-medium-directories.txt
```

---

<a id="-subdomain-wordlists"></a>
## 🌐 Listas de subdomínios

### Listas de subdomínios populares

| Lista de palavras | Linhas | Origem | Caso de utilização |
|----------|-------|--------|----------|
| **subdomains-top1million-5000.txt** | 5K | SecLists | Enumeração rápida |
| **subdomains-top1million-20000.txt** | 20K | SecLists | Enumeração normal |
| **subdomains-top1million-110000.txt** | 110K | SecLists | Enumeração aprofundada |
| **dns-Jhaddix.txt** | 2M+ | SecLists | Bug bounty |
| **fierce-hostlist.txt** | - | SecLists | Compatível com Fierce |
| **combined_subdomains.txt** | 648K | SecLists | Lista combinada |

### Localização das listas de subdomínios

```
SecLists/Discovery/DNS/
├── subdomains-top1million-5000.txt
├── subdomains-top1million-20000.txt
├── subdomains-top1million-110000.txt
├── dns-Jhaddix.txt                    # A melhor para bug bounty!
├── combined_subdomains.txt
├── deepmagic.com-prefixes-top500.txt
├── deepmagic.com-prefixes-top50000.txt
├── fierce-hostlist.txt
├── namelist.txt
├── shubs-subdomains.txt
└── bitquark-subdomains-top100000.txt
```

### Recomendadas para bug bounty

```bash
# Metodologia de Jason Haddix
dns-Jhaddix.txt              # 2M+ subdomínios

# Combinar para uma análise mais aprofundada
cat subdomains-top1million-110000.txt dns-Jhaddix.txt | sort -u > mega-subdomain.txt
```

---

<a id="-username-wordlists"></a>
## 👤 Listas de nomes de utilizador

### Listas de nomes de utilizador comuns

| Lista de palavras | Linhas | Finalidade |
|----------|-------|---------|
| **top-usernames-shortlist.txt** | 17 | Verificação rápida |
| **xato-net-10-million-usernames.txt** | 10M | Lista grande |
| **cirt-default-usernames.txt** | - | Utilizadores predefinidos |
| **names.txt** | - | Nomes comuns |
| **familynames-usa-top1000.txt** | 1K | Apelidos |

### Localização das listas de nomes de utilizador

```
SecLists/Usernames/
├── top-usernames-shortlist.txt
├── xato-net-10-million-usernames.txt
├── cirt-default-usernames.txt
├── Names/
│   ├── names.txt
│   ├── familynames-usa-top1000.txt
│   └── malenames-usa-top1000.txt
└── Honeypot-Captures/
    └── multiplesources-users-fabian-fingerle.de.txt
```

### Credenciais predefinidas

| Serviço | Lista de palavras |
|---------|----------|
| **FTP** | ftp-betterdefaultpasslist.txt |
| **SSH** | ssh-betterdefaultpasslist.txt |
| **Tomcat** | tomcat-betterdefaultpasslist.txt |
| **MySQL** | mysql-betterdefaultpasslist.txt |
| **MSSQL** | mssql-betterdefaultpasslist.txt |
| **PostgreSQL** | postgres-betterdefaultpasslist.txt |

---

<a id="-web-fuzzing-wordlists"></a>
## 💉 Listas para fuzzing web

### Injeção de SQL

```
SecLists/Fuzzing/SQLi/
├── Generic-SQLi.txt
├── quick-SQLi.txt
├── MSSQL.fuzzdb.txt
├── MySQL.fuzzdb.txt
├── PostgreSQL.fuzzdb.txt
└── oracle.fuzzdb.txt
```

### XSS (Cross-Site Scripting)

```
SecLists/Fuzzing/XSS/
├── XSS-Bypass-Strings-BruteLogic.txt
├── XSS-Cheat-Sheet-PortSwigger.txt
├── XSS-Jhaddix.txt
├── XSS-RSNAKE.txt
├── XSS-Vectors-Mario.txt
└── xss-without-parentheses-semi-colons-portswigger.txt
```

### LFI/RFI (inclusão de ficheiros locais ou remotos)

```
SecLists/Fuzzing/LFI/
├── LFI-Jhaddix.txt
├── LFI-LFISuite-pathtotest-huge.txt
├── LFI-LFISuite-pathtotest.txt
└── LFI-gracefulsecurity-linux.txt
```

### Injeção de comandos

```
SecLists/Fuzzing/command-injection-commix.txt
SecLists/Fuzzing/command-injection-testing-unix.txt
SecLists/Fuzzing/command-injection-testing-windows.txt
```

### SSTI (injeção em modelos do lado do servidor)

```
SecLists/Fuzzing/SSTI/
├── SSTI.txt
└── tplmap-template-injection.txt
```

### Coleções para fuzzing

| Lista de palavras | Finalidade |
|----------|---------|
| **special-chars.txt** | Caracteres especiais |
| **Unicode.txt** | Fuzzing com Unicode |
| **big-list-of-naughty-strings.txt** | Casos limite |
| **fuzz-Bo0oM.txt** | Fuzzing genérico |
| **burp-parameter-names.txt** | Nomes de parâmetros |

---

<a id="-seclists-collection"></a>
## 📦 Coleção SecLists

### Sobre o SecLists

**SecLists** é uma coleção de vários tipos de listas usadas em avaliações de segurança, concebida para acompanhar o trabalho de quem realiza testes de intrusão.

### Instalação

> **Distribuição e pacotes:** SecLists não está necessariamente pré-instalado em todas as imagens Kali. A disponibilidade de `apt install seclists` também depende dos repositórios configurados; a indicação Ubuntu/Debian do original não garante esse pacote em qualquer instalação. Ver os [metapacotes Kali](https://www.kali.org/docs/general-use/metapackages/) e a [página oficial de SecLists no Kali](https://www.kali.org/tools/seclists/).

```bash
# Kali Linux (pré-instalado)
ls /usr/share/seclists/

# Ubuntu/Debian
sudo apt install seclists

# Clonar com Git
git clone https://github.com/danielmiessler/SecLists.git

# Download rápido (listas específicas)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt
```

### Estrutura do SecLists

```
SecLists/
├── Discovery/
│   ├── DNS/                    # Listas de subdomínios
│   ├── Web-Content/            # Listas de diretórios e ficheiros
│   └── Infrastructure/         # Serviços e portas
├── Fuzzing/
│   ├── SQLi/                   # Payloads de injeção de SQL
│   ├── XSS/                    # Payloads XSS
│   ├── LFI/                    # Payloads LFI/RFI
│   └── command-injection*.txt  # Injeção de comandos
├── Passwords/
│   ├── Common-Credentials/     # Listas de palavras-passe
│   ├── Leaked-Databases/       # rockyou, etc.
│   └── Default-Credentials/    # Palavras-passe predefinidas
├── Usernames/
│   ├── Names/                  # Listas de nomes
│   └── top-usernames*.txt      # Listas de nomes de utilizador
├── Payloads/
│   └── Anti-Antivirus/         # Contorno de antivírus
└── Pattern-Matching/
    └── Hunting/                # Padrões de expressões regulares
```

### Repositório no GitHub

🔗 https://github.com/danielmiessler/SecLists

---

<a id="-kali-default-wordlists"></a>
## 🐧 Listas de palavras predefinidas no Kali

### Localização das listas de palavras

> **Correção do comentário original:** `nmap.lst` aponta para uma lista de palavras-passe do Nmap, não para uma lista de scripts NSE. A árvore depende dos pacotes instalados; ver a [documentação oficial do pacote wordlists](https://www.kali.org/tools/wordlists/).

```bash
# Diretório principal de listas de palavras
/usr/share/wordlists/

# Conteúdo
ls -la /usr/share/wordlists/
├── rockyou.txt.gz          # Descomprimir com gunzip
├── dirb/                   # Listas do DIRB
├── dirbuster/              # Listas do DirBuster
├── fasttrack.txt           # Lista fasttrack
├── fern-wifi/              # Listas para WiFi
├── metasploit/             # Listas do MSF
├── nmap.lst                # Lista de scripts Nmap
├── wfuzz/                  # Listas do wFuzz
└── seclists -> /usr/share/seclists/
```

### Listas do DirBuster

```bash
ls /usr/share/dirbuster/wordlists/
├── apache-user-enum-1.0.txt
├── apache-user-enum-2.0.txt
├── directories.jbrofuzz
├── directory-list-1.0.txt
├── directory-list-2.3-big.txt
├── directory-list-2.3-medium.txt
├── directory-list-2.3-small.txt
└── directory-list-lowercase-2.3-big.txt
```

### Listas do DIRB

```bash
ls /usr/share/dirb/wordlists/
├── big.txt                  # 20K entradas
├── common.txt               # 4.6K entradas
├── small.txt                # 959 entradas
├── catala.txt               # Catalão
├── euskera.txt              # Basco
├── extensions_common.txt    # Extensões
├── indexes.txt              # Ficheiros de índice
├── mutations_common.txt     # Variações
├── spanish.txt              # Espanhol
├── stress/                  # Testes de esforço
└── vulns/                   # Verificações de vulnerabilidades
```

---

<a id="-custom-wordlist-tools"></a>
## 🛠️ Ferramentas de criação de listas de palavras

### CeWL (gerador de listas de palavras personalizadas)

```bash
# Instalação
sudo apt install cewl

# Utilização básica — Percorrer um site
cewl https://target.com -w wordlist.txt

# Com profundidade e comprimento mínimo das palavras
cewl https://target.com -d 3 -m 6 -w wordlist.txt

# Incluir emails
cewl https://target.com -e -w wordlist.txt

# Opções
-d <depth>      # Profundidade de exploração (predefinição: 2)
-m <length>     # Comprimento mínimo das palavras (predefinição: 3)
-w <file>       # Ficheiro de saída
-e              # Incluir endereços de email
--lowercase     # Converter em minúsculas
-c              # Contar ocorrências das palavras
```

### Crunch (gerador de listas de palavras)

> **Escala dos exemplos:** gerar todas as combinações cresce exponencialmente com o conjunto de caracteres e o comprimento. As linhas com nomes de listas ou árvores ilustrativas não são, por si só, comandos para executar. Os símbolos da opção `-t` são preservados; ver a [documentação oficial de Crunch no Kali](https://www.kali.org/tools/crunch/).

```bash
# Instalação
sudo apt install crunch

# Sintaxe básica
crunch <min-len> <max-len> [charset] -o <output>

# Gerar palavras-passe de 4-6 caracteres
crunch 4 6 -o wordlist.txt

# Com caracteres específicos
crunch 8 8 abcdefghijklmnopqrstuvwxyz0123456789 -o wordlist.txt

# Com um padrão
crunch 6 6 -t @@%%^^ -o wordlist.txt
# @ = minúsculas, , = maiúsculas, % = algarismo, ^ = especial

# Conjunto de caracteres personalizado
crunch 4 4 -f /usr/share/crunch/charset.lst mixalpha-numeric -o wordlist.txt

# Encaminhar a saída para o Hashcat
crunch 8 8 0123456789 | hashcat -m 0 hash.txt
```

### CUPP (criação de perfis de palavras-passe de utilizadores)

```bash
# Instalação
git clone https://github.com/Mebus/cupp.git
cd cupp

# Modo interativo baseado num perfil
python3 cupp.py -i

# Perguntas apresentadas:
# - Nome próprio, apelido e alcunha
# - Data de nascimento
# - Informações do parceiro ou parceira
# - Nome do animal de estimação
# - Nome da empresa
# - Palavras-chave

# Descarregar palavras-passe comuns
python3 cupp.py -l

# Utilizar uma lista existente com criação de perfil
python3 cupp.py -w wordlist.txt
```

### Mentalist (gerador de listas de palavras com interface gráfica)

```bash
# Ferramenta gráfica para gerar listas de palavras complexas
# Suporta regras, alterações de maiúsculas e minúsculas e substituições
# Descarregar em: https://github.com/sc0tfree/mentalist
```

### Wordlister (Python)

```python
#!/usr/bin/env python3
# Gerador simples de listas de palavras personalizadas
import itertools

# Gerar combinações
chars = 'abc123'
length = 4

for combo in itertools.product(chars, repeat=length):
    print(''.join(combo))
```

### Regras do Hashcat

```bash
# Utilizar regras para transformar a lista de palavras
hashcat -r /usr/share/hashcat/rules/best64.rule wordlist.txt --stdout

# Regras populares
/usr/share/hashcat/rules/
├── best64.rule         # As 64 melhores regras
├── rockyou-30000.rule  # Baseadas no RockYou
├── d3ad0ne.rule        # Regras d3ad0ne
├── dive.rule           # Regras DIVE
└── toggles1-5.rule     # Alternar maiúsculas e minúsculas
```

---

<a id="-quick-download"></a>
## ⬇️ Download rápido

### Downloads numa só linha

```bash
# RockYou
wget https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt

# SecLists (completo)
git clone --depth 1 https://github.com/danielmiessler/SecLists.git

# Palavras-passe comuns (1M)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt

# Lista média de diretórios
wget https://raw.githubusercontent.com/daviddias/node-dirbuster/master/lists/directory-list-2.3-medium.txt

# Lista de subdomínios
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/subdomains-top1million-110000.txt

# DNS de Jhaddix (bug bounty)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/dns-Jhaddix.txt
```

### Clonar repositórios

```bash
# SecLists
git clone https://github.com/danielmiessler/SecLists.git

# FuzzDB
git clone https://github.com/fuzzdb-project/fuzzdb.git

# PayloadsAllTheThings
git clone https://github.com/swisskyrepo/PayloadsAllTheThings.git

# Assetnote Wordlists
git clone https://github.com/assetnote/wordlists.git
```

---

<a id="-tips--best-practices"></a>
## 💡 Dicas e boas práticas

### Escolher a lista de palavras adequada

| Tarefa | Lista de palavras recomendada |
|------|---------------------|
| **Análise rápida de diretórios** | common.txt, raft-small-directories.txt |
| **Análise aprofundada de diretórios** | directory-list-2.3-medium.txt |
| **Análise completa de diretórios** | directory-list-2.3-big.txt |
| **Enumeração de subdomínios** | subdomains-top1million-20000.txt |
| **Bug Bounty DNS** | dns-Jhaddix.txt |
| **Quebra de palavras-passe** | rockyou.txt |
| **Teste rápido de palavras-passe** | top-10000.txt |

### Combinar listas de palavras

```bash
# Combinar e remover duplicados
cat list1.txt list2.txt | sort -u > combined.txt

# Acrescentar um prefixo a todas as linhas
sed 's/^/admin_/' wordlist.txt > admin_wordlist.txt

# Acrescentar um sufixo
sed 's/$/.php/' wordlist.txt > php_wordlist.txt

# Ambos
while read line; do echo "${line}.php"; echo "${line}.html"; done < wordlist.txt > extended.txt
```

### Otimização de listas de palavras

```bash
# Ordenar e remover duplicados
sort -u wordlist.txt -o wordlist.txt

# Remover linhas vazias
sed -i '/^$/d' wordlist.txt

# Remover linhas com menos de 4 caracteres
awk 'length >= 4' wordlist.txt > filtered.txt

# Converter em minúsculas
tr '[:upper:]' '[:lower:]' < wordlist.txt > lowercase.txt

# Contar linhas
wc -l wordlist.txt
```

### Dicas de desempenho

1. **Começar com listas pequenas** — Utilizar primeiro listas menores e depois aumentar
2. **Utilizar padrões** — Se conheceres o formato, usar padrões específicos
3. **Combinar ferramentas** — Gerar listas personalizadas e utilizar listas existentes
4. **Adaptar as listas ao alvo** — Criar listas específicas para o alvo com o CeWL

---

## 📊 Referência rápida

### Listas de palavras de referência por categoria

| Categoria | Escolha recomendada | Localização |
|----------|-------------|----------|
| **Palavras-passe** | rockyou.txt | SecLists/Passwords/ |
| **Diretórios** | directory-list-2.3-medium.txt | DirBuster |
| **Ficheiros** | raft-large-files.txt | SecLists |
| **Subdomínios** | dns-Jhaddix.txt | SecLists/Discovery/DNS/ |
| **Nomes de utilizador** | xato-net-10-million-usernames.txt | SecLists/Usernames/ |
| **SQLi** | Generic-SQLi.txt | SecLists/Fuzzing/SQLi/ |
| **XSS** | XSS-Jhaddix.txt | SecLists/Fuzzing/XSS/ |
| **LFI** | LFI-Jhaddix.txt | SecLists/Fuzzing/LFI/ |

### Guia de tamanhos

| Tamanho | Linhas | Caso de utilização |
|------|-------|----------|
| **Pequena** | < 5K | Testes rápidos |
| **Média** | 5K - 100K | Enumeração normal |
| **Grande** | 100K - 1M | Análise aprofundada |
| **Muito grande** | > 1M | Cobertura completa |

---

## 📚 Recursos

### Repositórios de listas de palavras
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Assetnote Wordlists](https://github.com/assetnote/wordlists)

### Ferramentas
- [CeWL](https://github.com/digininja/CeWL)
- [CUPP](https://github.com/Mebus/cupp)
- [Crunch](https://sourceforge.net/projects/crunch-wordlist/)
- [Mentalist](https://github.com/sc0tfree/mentalist)

### Guias de referência relacionados
- [Hydra](../../Hydra/translations/README.pt.md)
- [John the Ripper](../../John-The-Ripper/translations/README.pt.md)
- [Hashcat](../../Hashcat/translations/README.pt.md)
- [Gobuster](../../Gobuster/README.md)
- [ffuf](../../ffuf/README.md)

---

<p align="center">
  <b>📚 A lista de palavras certa faz toda a diferença!</b><br>
  <i>Escolhe com critério e combina de forma inteligente</i>
</p>
