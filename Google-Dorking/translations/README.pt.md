# 🔍 Google Dorking — Guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗  ██████╗  ██████╗  ██████╗ ██╗     ███████╗
  ██╔════╝ ██╔═══██╗██╔═══██╗██╔════╝ ██║     ██╔════╝
  ██║  ███╗██║   ██║██║   ██║██║  ███╗██║     █████╗  
  ██║   ██║██║   ██║██║   ██║██║   ██║██║     ██╔══╝  
  ╚██████╔╝╚██████╔╝╚██████╔╝╚██████╔╝███████╗███████╗
   ╚═════╝  ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚══════╝
  ██████╗  ██████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗ 
  ██╔══██╗██╔═══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝ 
  ██║  ██║██║   ██║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗
  ██║  ██║██║   ██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║
  ██████╔╝╚██████╔╝██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝
  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

<p align="center">
  <img src="https://img.shields.io/badge/Google-Dorking-red?style=for-the-badge" alt="Google Dorking">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Information-Gathering-green?style=for-the-badge" alt="Recolha de informação">
</p>

<p align="center">
  <b>🎯 Técnicas avançadas de pesquisa no Google para investigadores de segurança</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Introdução](#-introduction)
- [Operadores básicos](#-basic-operators)
- [Operadores avançados](#-advanced-operators)
- [Combinação de operadores](#-combining-operators)
- [Descoberta de ficheiros](#-file-discovery)
- [Listagem de diretórios](#-directory-listing)
- [Painéis de administração](#-admin-panels)
- [Bases de dados e SQL](#-database--sql)
- [Ficheiros de configuração](#-configuration-files)
- [Credenciais e segredos](#-credentials--secrets)
- [Servidores vulneráveis](#-vulnerable-servers)
- [Dorks para bug bounty](#-bug-bounty-dorks)
- [Pesquisas específicas por CMS](#-cms-specific)
- [Referência rápida](#-quick-reference)
- [Ferramentas e automatização](#-tools--automation)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

**Google Dorking**, também conhecido como Google Hacking, é uma técnica que utiliza operadores de pesquisa avançada para encontrar informações sensíveis expostas na Internet.

Um dork é uma consulta construída para localizar um tipo de informação. OSINT é a recolha e análise de informação em fontes abertas; um endpoint é um ponto de acesso a um serviço ou API. CMS significa sistema de gestão de conteúdos.

### O que podes encontrar?

| Categoria | Exemplos |
|----------|----------|
| **Ficheiros sensíveis** | Cópias de segurança, configurações, registos e bases de dados |
| **Painéis expostos** | Painéis de administração, painéis de controlo e phpMyAdmin |
| **Credenciais** | Palavras-passe, chaves de API e tokens |
| **Vulnerabilidades** | Injeção de SQL e diretórios abertos |
| **Dados pessoais** | Emails, números de telefone e documentos |

### URLs dos motores de pesquisa

| Motor | URL |
|--------|-----|
| **Google** | https://www.google.com |
| **Bing** | https://www.bing.com |
| **DuckDuckGo** | https://duckduckgo.com |
| **Yandex** | https://yandex.com |

---

<a id="-basic-operators"></a>
## 🔧 Operadores básicos

### Operador site

> **Leitura do exemplo:** `-www` exclui o termo pesquisado; não é um inventário rigoroso de subdomínios nem um teste de alcance de rede. Os exemplos com `site:` mostram resultados indexados, não todos os ativos existentes.

Limitar a pesquisa a um domínio específico:

```
site:example.com                     # Apenas example.com
site:*.example.com                   # Todos os subdomínios
site:example.com -www                # Excluir www
site:.gov                            # Sites governamentais
site:.edu                            # Sites de instituições de ensino
```

### Operador inurl

Pesquisar no URL:

```
inurl:admin                          # admin no URL
inurl:login                          # Páginas de início de sessão
inurl:dashboard                      # Painéis de controlo
inurl:config                         # Ficheiros de configuração
inurl:backup                         # Cópias de segurança
```

### Operador intitle

Pesquisar no título da página:

```
intitle:"index of"                   # Listagens de diretórios
intitle:admin                        # Páginas de administração
intitle:login                        # Páginas de início de sessão
intitle:"dashboard"                  # Painéis de controlo
intitle:"control panel"              # Painéis de controlo
```

### Operador intext

Pesquisar no conteúdo da página:

```
intext:"password"                    # Palavra-passe no texto
intext:"username"                    # Nome de utilizador no texto
intext:"api_key"                     # Chaves de API
intext:"secret"                      # Segredos
```

### Operador filetype

Pesquisar tipos de ficheiro específicos:

```
filetype:pdf                         # Ficheiros PDF
filetype:doc                         # Documentos Word
filetype:xls                         # Ficheiros Excel
filetype:sql                         # Ficheiros SQL
filetype:log                         # Ficheiros de registo
filetype:bak                         # Cópias de segurança
filetype:env                         # Ficheiros de variáveis de ambiente
```

---

<a id="-advanced-operators"></a>
## ⚡ Operadores avançados

### Variantes all

```
allinurl:admin login                 # Todas as palavras no URL
allintitle:admin panel               # Todas as palavras no título
allintext:password username          # Todas as palavras no texto
```

### Cache e ligações

> **Exemplos legados:** o operador `cache:` deixou de funcionar no Google Search, segundo o [registo oficial de atualizações](https://developers.google.com/search/updates). As consultas originais são preservadas, incluindo a dica de páginas em cache, para referência histórica. Não pressuponhas que todos os operadores antigos ou de outros motores continuam suportados; consulta a [ajuda atual do Google](https://support.google.com/websearch/answer/2466433?hl=en).

```
cache:example.com                    # Versão em cache
link:example.com                     # Páginas com ligações para o destino
related:example.com                  # Sites relacionados
```

### Intervalo de datas

```
after:2023-01-01                     # Depois da data
before:2024-01-01                    # Antes da data
daterange:2458850-2459215            # Intervalo de datas julianas
```

### Caracteres universais e OR

```
admin * panel                        # Carácter universal
admin OR login                       # Qualquer um dos termos
"admin panel"                        # Expressão exata
-inurl:https                         # Excluir HTTPS
```

### Intervalo numérico

```
inurl:id= 1..1000                    # Intervalo numérico
price $100..$500                     # Intervalo de preços
```

---

<a id="-combining-operators"></a>
## 🔗 Combinação de operadores

### Combinações básicas

```
site:example.com filetype:pdf
site:example.com inurl:admin
site:example.com intitle:login
site:example.com intext:password
```

### Combinações avançadas

```
site:example.com filetype:sql intext:password
site:example.com inurl:admin intitle:login
site:example.com filetype:log intext:error
site:example.com ext:php inurl:config
```

### Combinações para bug bounty

```
site:target.com (filetype:pdf | filetype:doc | filetype:xls)
site:target.com (inurl:admin | inurl:login | inurl:dashboard)
site:target.com (ext:sql | ext:bak | ext:log)
```

---

<a id="-file-discovery"></a>
## 📁 Descoberta de ficheiros

### Documentos sensíveis

```
# Documentos Office
site:target.com filetype:doc
site:target.com filetype:docx
site:target.com filetype:xls
site:target.com filetype:xlsx
site:target.com filetype:ppt
site:target.com filetype:pdf

# Ficheiros de texto
site:target.com filetype:txt
site:target.com filetype:rtf
site:target.com filetype:csv
```

### Cópias de segurança

```
site:target.com filetype:bak
site:target.com filetype:backup
site:target.com filetype:old
site:target.com filetype:orig
site:target.com ext:bak
site:target.com inurl:backup
site:target.com intitle:"backup"
```

### Ficheiros de bases de dados

```
site:target.com filetype:sql
site:target.com filetype:db
site:target.com filetype:mdb
site:target.com filetype:sqlite
site:target.com filetype:dump
site:target.com ext:sql intext:INSERT
```

### Ficheiros de registo

```
site:target.com filetype:log
site:target.com ext:log
site:target.com inurl:log
site:target.com intext:"error" filetype:log
site:target.com intext:"exception" filetype:log
site:target.com intext:"fatal" filetype:log
```

### Ficheiros de configuração

```
site:target.com filetype:conf
site:target.com filetype:config
site:target.com filetype:cfg
site:target.com filetype:ini
site:target.com filetype:env
site:target.com filetype:yml
site:target.com filetype:yaml
site:target.com filetype:xml
site:target.com filetype:json
```

### Código-fonte

```
site:target.com filetype:php
site:target.com filetype:asp
site:target.com filetype:aspx
site:target.com filetype:jsp
site:target.com filetype:py
site:target.com filetype:rb
site:target.com filetype:js
```

---

<a id="-directory-listing"></a>
## 📂 Listagem de diretórios

### Index Of (índice de diretório)

```
intitle:"index of" site:target.com
intitle:"index of /" site:target.com
intitle:"directory listing" site:target.com
intitle:"parent directory" site:target.com
```

### Diretórios específicos

```
intitle:"index of" "backup" site:target.com
intitle:"index of" "password" site:target.com
intitle:"index of" "admin" site:target.com
intitle:"index of" "config" site:target.com
intitle:"index of" "ftp" site:target.com
intitle:"index of" "logs" site:target.com
intitle:"index of" "uploads" site:target.com
intitle:"index of" ".git" site:target.com
intitle:"index of" ".svn" site:target.com
```

### Tipos de ficheiro em diretórios

```
intitle:"index of" "*.sql" site:target.com
intitle:"index of" "*.bak" site:target.com
intitle:"index of" "*.log" site:target.com
intitle:"index of" "*.zip" site:target.com
intitle:"index of" "*.tar.gz" site:target.com
```

---

<a id="-admin-panels"></a>
## 🔐 Painéis de administração

### Administração genérica

```
site:target.com inurl:admin
site:target.com inurl:administrator
site:target.com inurl:adminlogin
site:target.com inurl:admin/login
site:target.com inurl:admin.php
site:target.com inurl:admin.asp
site:target.com intitle:admin
site:target.com intitle:"admin login"
site:target.com intitle:"admin panel"
site:target.com intitle:"administrator"
```

### cPanel e alojamento

```
site:target.com inurl:cpanel
site:target.com inurl:webmail
site:target.com inurl:whm
site:target.com intitle:"cPanel"
site:target.com inurl:2082
site:target.com inurl:2083
site:target.com inurl:2086
site:target.com inurl:2087
```

### Administração de bases de dados

```
site:target.com inurl:phpmyadmin
site:target.com inurl:phpMyAdmin
site:target.com intitle:"phpMyAdmin"
site:target.com inurl:adminer
site:target.com inurl:pgadmin
site:target.com intitle:"pgAdmin"
site:target.com inurl:mysql
```

### Painéis de controlo

```
site:target.com intitle:"dashboard"
site:target.com intitle:"control panel"
site:target.com inurl:portal
site:target.com inurl:cms
site:target.com inurl:backend
```

---

<a id="-database--sql"></a>
## 💾 Bases de dados e SQL

### Ficheiros SQL

```
site:target.com filetype:sql
site:target.com filetype:sql intext:password
site:target.com filetype:sql intext:"INSERT INTO"
site:target.com filetype:sql intext:"CREATE TABLE"
site:target.com ext:sql "INSERT INTO `users`"
```

### Erros SQL

```
site:target.com "SQL syntax" error
site:target.com "mysql_fetch_array"
site:target.com "mysql_num_rows"
site:target.com "mysql_connect"
site:target.com "mysqli_"
site:target.com "ORA-" error
site:target.com "PostgreSQL" error
site:target.com "ODBC" error
```

### Indicadores de injeção de SQL

> **Precisão do original:** encontrar um parâmetro no URL identifica uma superfície de entrada, não demonstra injeção de SQL. Do mesmo modo, um painel de login, erro ou página predefinida não confirma, por si só, uma vulnerabilidade.

```
site:target.com inurl:id=
site:target.com inurl:page=
site:target.com inurl:cat=
site:target.com inurl:item=
site:target.com inurl:pid=
site:target.com inurl:product_id=
site:target.com inurl:news_id=
```

### Cópias integrais de bases de dados (dumps)

```
site:target.com "dump" filetype:sql
site:target.com "database dump" filetype:sql
site:target.com filetype:sql "-- MySQL dump"
site:target.com filetype:sql "-- phpMyAdmin SQL Dump"
```

---

<a id="-configuration-files"></a>
## ⚙️ Ficheiros de configuração

### Configuração do servidor web

```
site:target.com filetype:conf
site:target.com filetype:conf intext:password
site:target.com "httpd.conf"
site:target.com "nginx.conf"
site:target.com ".htaccess"
site:target.com ".htpasswd"
site:target.com "web.config"
```

### Configuração da aplicação

```
site:target.com filetype:env
site:target.com ".env" filetype:env
site:target.com "config.php" intext:password
site:target.com "settings.php" intext:password
site:target.com "database.php" intext:password
site:target.com "wp-config.php"
site:target.com "configuration.php"
site:target.com "config.yml"
site:target.com "config.json"
```

### Configuração do Git

```
site:target.com ".git/config"
site:target.com intitle:"index of" ".git"
site:target.com inurl:".git"
site:target.com filetype:git
```

### AWS e cloud

```
site:target.com filetype:pem
site:target.com filetype:ppk
site:target.com "aws_access_key_id"
site:target.com "aws_secret_access_key"
site:target.com "AKIA"                    # Prefixo de chave AWS
site:target.com "credentials" filetype:json
```

---

<a id="-credentials--secrets"></a>
## 🔑 Credenciais e segredos

### Palavras-passe

```
site:target.com intext:password
site:target.com intext:"password is"
site:target.com intext:"default password"
site:target.com filetype:txt password
site:target.com filetype:log password
site:target.com "password" filetype:xls
site:target.com "password" filetype:csv
```

### Combinações de utilizador e palavra-passe

```
site:target.com intext:username intext:password
site:target.com "username" "password" filetype:txt
site:target.com "login" "password" filetype:xls
site:target.com intext:"username:" intext:"password:"
```

### Chaves de API e tokens

```
site:target.com intext:api_key
site:target.com intext:apikey
site:target.com intext:api-key
site:target.com intext:"api key"
site:target.com intext:access_token
site:target.com intext:auth_token
site:target.com intext:secret_key
site:target.com intext:bearer
```

### Endereços de email

```
site:target.com "@target.com"
site:target.com filetype:xls "@target.com"
site:target.com filetype:csv "email"
site:target.com intext:"@gmail.com"
```

### Chaves privadas

```
site:target.com "BEGIN RSA PRIVATE KEY"
site:target.com "BEGIN DSA PRIVATE KEY"
site:target.com "BEGIN EC PRIVATE KEY"
site:target.com "BEGIN PRIVATE KEY"
site:target.com filetype:pem
site:target.com filetype:key
site:target.com "id_rsa"
site:target.com "id_dsa"
```

---

<a id="-vulnerable-servers"></a>
## ⚠️ Servidores vulneráveis

### Diretórios abertos

```
intitle:"index of" site:target.com
intitle:"directory listing" site:target.com
intitle:"Apache" "server at" site:target.com
```

### Páginas predefinidas

```
site:target.com intitle:"Apache2 Ubuntu Default Page"
site:target.com intitle:"Welcome to nginx"
site:target.com intitle:"Test Page for Apache"
site:target.com intitle:"IIS Windows Server"
site:target.com intitle:"Welcome to CentOS"
```

### Páginas de erro

```
site:target.com "404 Not Found" inurl:admin
site:target.com "500 Internal Server Error"
site:target.com "Error establishing database connection"
site:target.com "Parse error" "syntax error"
site:target.com "Warning:" "mysql_"
site:target.com "Fatal error:" "php"
```

### Serviços expostos

```
site:target.com intitle:"Webmin"
site:target.com intitle:"Jenkins"
site:target.com intitle:"Grafana"
site:target.com intitle:"Kibana"
site:target.com intitle:"Elasticsearch"
site:target.com intitle:"Solr Admin"
site:target.com intitle:"RabbitMQ Management"
site:target.com intitle:"Redis Commander"
```

---

<a id="-bug-bounty-dorks"></a>
## 🐛 Dorks para bug bounty

### Descobertas rápidas

```
# Ficheiros sensíveis
site:target.com (filetype:sql | filetype:bak | filetype:log | filetype:env)

# Painéis de administração
site:target.com (inurl:admin | inurl:login | inurl:dashboard)

# Ficheiros de configuração
site:target.com (filetype:conf | filetype:config | filetype:ini | filetype:env)

# Credenciais
site:target.com (intext:password | intext:api_key | intext:secret)
```

### Descoberta de subdomínios

```
site:*.target.com -www
site:*.target.com -www -mail
site:*.*.target.com
```

### Endpoints de API

```
site:target.com inurl:api
site:target.com inurl:api/v1
site:target.com inurl:api/v2
site:target.com inurl:/rest/
site:target.com inurl:graphql
site:target.com inurl:swagger
site:target.com inurl:swagger-ui
site:target.com filetype:json inurl:api
```

### Desenvolvimento e pré-produção

```
site:dev.target.com
site:staging.target.com
site:test.target.com
site:uat.target.com
site:qa.target.com
site:demo.target.com
site:beta.target.com
site:sandbox.target.com
```

### Páginas de autenticação

```
site:target.com inurl:login
site:target.com inurl:signin
site:target.com inurl:signup
site:target.com inurl:register
site:target.com inurl:auth
site:target.com inurl:oauth
site:target.com inurl:sso
site:target.com inurl:forgot
site:target.com inurl:reset
```

### Carregamento de ficheiros

```
site:target.com inurl:upload
site:target.com intitle:upload
site:target.com inurl:file
site:target.com "choose file"
site:target.com "upload file"
```

---

<a id="-cms-specific"></a>
## 📱 Pesquisas específicas por CMS

### WordPress

```
site:target.com inurl:wp-content
site:target.com inurl:wp-includes
site:target.com inurl:wp-admin
site:target.com inurl:wp-login
site:target.com "wp-config.php"
site:target.com filetype:sql "wp_users"
site:target.com intitle:"WordPress" inurl:readme
```

### Joomla

```
site:target.com inurl:administrator
site:target.com inurl:com_
site:target.com "configuration.php"
site:target.com "Joomla"
```

### Drupal

```
site:target.com inurl:node/
site:target.com inurl:sites/default/files
site:target.com "Drupal"
site:target.com "CHANGELOG.txt"
```

### Magento

```
site:target.com inurl:app/etc/local.xml
site:target.com inurl:downloader
site:target.com "Magento"
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Operadores essenciais

| Operador | Função | Exemplo |
|----------|----------|---------|
| `site:` | Restrito a um domínio | `site:target.com` |
| `inurl:` | Pesquisar no URL | `inurl:admin` |
| `intitle:` | Pesquisar no título | `intitle:login` |
| `intext:` | Pesquisar no conteúdo | `intext:password` |
| `filetype:` | Extensão de ficheiro | `filetype:sql` |
| `ext:` | Extensão de ficheiro | `ext:pdf` |
| `cache:` | Versão em cache | `cache:target.com` |
| `"..."` | Expressão exata | `"admin panel"` |
| `-` | Excluir | `-site:www` |
| `OR` / `|` | Qualquer um dos termos | `admin OR login` |
| `*` | Carácter universal | `admin * panel` |

### 10 dorks de referência para bug bounty

| # | Dork | Finalidade |
|---|------|---------|
| 1 | `site:target.com filetype:sql` | Ficheiros de bases de dados |
| 2 | `site:target.com inurl:admin` | Painéis de administração |
| 3 | `site:target.com filetype:log` | Ficheiros de registo |
| 4 | `site:target.com filetype:env` | Ficheiros de variáveis de ambiente |
| 5 | `site:target.com intext:password filetype:txt` | Palavras-passe |
| 6 | `intitle:"index of" site:target.com` | Listagem de diretórios |
| 7 | `site:target.com inurl:api` | Endpoints de API |
| 8 | `site:target.com filetype:bak` | Cópias de segurança |
| 9 | `site:*.target.com -www` | Subdomínios |
| 10 | `site:target.com "api_key"` | Chaves de API |

---

<a id="-tools--automation"></a>
## 🛠️ Ferramentas e automatização

### Ferramentas de Google Dorking

| Ferramenta | Descrição | URL |
|------|-------------|-----|
| **Google Hacking Database** | Coleção de dorks do Exploit-DB | [GHDB](https://www.exploit-db.com/google-hacking-database) |
| **DorkSearch** | Gerador de dorks | [DorkSearch](https://dorksearch.com) |
| **Pagodo** | Google Dorking passivo | GitHub |
| **GooDork** | Ferramenta de dorking em Python | GitHub |
| **GooFuzz** | Fuzzing com o Google | GitHub |

### Sites de dorking

```
# Google Hacking Database
https://www.exploit-db.com/google-hacking-database

# Pentest-Tools
https://pentest-tools.com/information-gathering/google-hacking

# DorkSearch
https://dorksearch.com
```

### Exemplo de script de automatização

> **Comportamento do script:** este exemplo apenas imprime dorks e URLs; não envia pedidos de pesquisa. A substituição de espaços por `+` não é uma codificação completa de todos os caracteres de um URL. O código original é mantido.

```bash
#!/bin/bash
# Automatização simples de Google Dorking

TARGET="target.com"

DORKS=(
    "site:$TARGET filetype:sql"
    "site:$TARGET filetype:log"
    "site:$TARGET filetype:env"
    "site:$TARGET inurl:admin"
    "site:$TARGET intext:password"
)

for dork in "${DORKS[@]}"; do
    echo "[*] Dork: $dork"
    echo "URL: https://www.google.com/search?q=$(echo $dork | sed 's/ /+/g')"
    echo ""
done
```

---

## 💡 Dicas e boas práticas

### Dicas para bug bounty

1. **Começar com uma pesquisa ampla e depois restringi-la**
   ```
   site:target.com                    # Primeiro
   site:target.com filetype:sql       # Depois restringir
   ```

2. **Utilizar vários motores de pesquisa**
   - Google, Bing, DuckDuckGo, Yandex
   
3. **Consultar páginas em cache**
   ```
   cache:target.com/admin
   ```

4. **Procurar subdomínios**
   ```
   site:*.target.com -www
   ```

5. **Limitar a frequência das pesquisas**
   - O Google pode bloquear um número excessivo de consultas

### Evitar a deteção

```
# Utilizar IPs/VPNs diferentes
# Adicionar intervalos entre pesquisas
# Variar os padrões de pesquisa
# Utilizar navegadores diferentes
```

---

## ⚠️ Aviso legal

> **AVISO:** O Google Dorking, por si só, é legal, mas **aceder** a dados expostos sem autorização pode ser ilegal.
> 
> - ✅ Utilizar em testes de segurança autorizados
> - ✅ Utilizar em programas de bug bounty
> - ✅ Comunicar vulnerabilidades de forma responsável
> - ❌ Nunca aceder a dados sem autorização
> - ❌ Nunca descarregar nem guardar dados sensíveis
> - ❌ Não explorar as vulnerabilidades encontradas
> 
> **Obtém sempre a autorização adequada antes de testar!**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [Operadores de pesquisa do Google](https://support.google.com/websearch/answer/2466433)
- [Google Hacking Database](https://www.exploit-db.com/google-hacking-database)

### Livros e guias
- «Google Hacking for Penetration Testers», de Johnny Long
- Guia de testes da OWASP

### Guias de referência relacionados
- [Shodan](../../Shodan/translations/README.pt.md)
- [GitHub Dorking](../../GitHub-Dorking/translations/README.pt.md)
- [Subfinder](../../Subfinder/README.md)

---

<p align="center">
  <b>🔍 Encontra o que os outros escondem!</b><br>
  <i>Domina a arte do Google Dorking</i>
</p>
