# 🐙 GitHub Dorking — Guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗ ██╗████████╗██╗  ██╗██╗   ██╗██████╗ 
  ██╔════╝ ██║╚══██╔══╝██║  ██║██║   ██║██╔══██╗
  ██║  ███╗██║   ██║   ███████║██║   ██║██████╔╝
  ██║   ██║██║   ██║   ██╔══██║██║   ██║██╔══██╗
  ╚██████╔╝██║   ██║   ██║  ██║╚██████╔╝██████╔╝
   ╚═════╝ ╚═╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ 
  ██████╗  ██████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗ 
  ██╔══██╗██╔═══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝ 
  ██║  ██║██║   ██║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗
  ██║  ██║██║   ██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║
  ██████╔╝╚██████╔╝██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝
  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

<p align="center">
  <img src="https://img.shields.io/badge/GitHub-Dorking-red?style=for-the-badge" alt="GitHub Dorking">
  <img src="https://img.shields.io/badge/Secret-Hunting-orange?style=for-the-badge" alt="Procura de segredos">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
</p>

<p align="center">
  <b>🔐 Encontra segredos, chaves de API e credenciais expostos em repositórios do GitHub</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Introdução](#-introduction)
- [Sintaxe de pesquisa do GitHub](#-github-search-syntax)
- [Chaves de API e tokens](#-api-keys--tokens)
- [Credenciais de bases de dados](#-database-credentials)
- [Chaves privadas](#-private-keys)
- [Ficheiros de configuração](#-configuration-files)
- [Segredos cloud](#-cloud-secrets)
- [Palavras-passe e credenciais](#-passwords--credentials)
- [Alvos de bug bounty](#-bug-bounty-targets)
- [Técnicas avançadas](#-advanced-techniques)
- [Ferramentas de automatização](#-automation-tools)
- [Referência rápida](#-quick-reference)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

**GitHub Dorking** é a utilização da pesquisa avançada do GitHub para encontrar segredos, credenciais e informações sensíveis expostos em repositórios públicos.

Um dork é uma consulta de pesquisa direcionada. Um token é um valor usado para acesso ou autenticação; secret hunting é a procura de segredos expostos. Um gist é um excerto ou conjunto de ficheiros partilhado através do serviço Gist. Os valores de pesquisa mantêm a grafia original para corresponder ao conteúdo procurado.

### O que podes encontrar?

| Categoria | Exemplos |
|----------|----------|
| **Chaves de API** | AWS, Google, Stripe, Twilio |
| **Tokens** | OAuth, JWT e tokens de acesso pessoal |
| **Credenciais** | Palavras-passe de bases de dados, SMTP e SSH |
| **Chaves privadas** | Ficheiros RSA, SSH e PEM |
| **Ficheiros de configuração** | .env, config.yml, settings.py |

### Porque é importante

- Os programadores incluem frequentemente segredos em commits por engano
- O histórico Git pode preservar segredos já removidos dos ficheiros
- Os repositórios públicos são indexados e pesquisáveis
- É uma fonte importante de descobertas em bug bounty

---

<a id="-github-search-syntax"></a>
## 🔧 Sintaxe de pesquisa do GitHub

### URL de pesquisa básica

```
https://github.com/search?q=YOUR_QUERY&type=code
```

### Qualificadores de pesquisa

> **Sintaxes diferentes:** o original mistura qualificadores legados e pesquisa de repositórios com Code Search. No Code Search atual, consulta os qualificadores suportados, como `repo:`, `org:`, `user:`, `path:` e `content:`; não assumes que `filename:`, `extension:`, `in:file`, `size:` ou `pushed:` são intercambiáveis entre motores. As consultas originais são preservadas. Ver a [sintaxe oficial de Code Search](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax).

| Qualificador | Descrição | Exemplo |
|-----------|-------------|---------|
| `in:file` | Pesquisar no conteúdo do ficheiro | `password in:file` |
| `in:path` | Pesquisar no caminho do ficheiro | `config in:path` |
| `filename:` | Pesquisar pelo nome do ficheiro | `filename:.env` |
| `extension:` | Pesquisar pela extensão | `extension:json` |
| `language:` | Pesquisar pela linguagem | `language:python` |
| `user:` | Pesquisar nos repositórios de um utilizador | `user:target` |
| `org:` | Pesquisar nos repositórios de uma organização | `org:company` |
| `repo:` | Pesquisar num repositório específico | `repo:user/repo` |
| `path:` | Pesquisar no caminho | `path:config/` |
| `size:` | Tamanho do ficheiro | `size:>1000` |

### Operadores booleanos

> No Code Search moderno, os operadores booleanos documentados são `AND`, `OR` e `NOT`. Uma expressão entre aspas é texto exato; uma regex usa delimitadores `/.../`. Padrões regex colocados entre aspas, como alguns exemplos abaixo, não se tornam automaticamente regex. Ver a [documentação oficial](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax).

```
# AND (espaço)
password database

# OR
password OR secret

# NOT (-)
password -test -example

# Expressão exata
"api_key"

# Combinar
org:target "api_key" NOT test
```

---

<a id="-api-keys--tokens"></a>
## 🔑 Chaves de API e tokens

### Chaves AWS

```
# Identificador de chave de acesso AWS
AKIA
AKID
"aws_access_key_id"
"AWS_ACCESS_KEY_ID"

# Chave secreta AWS
"aws_secret_access_key"
"AWS_SECRET_ACCESS_KEY"

# Combinações
filename:.env AWS_ACCESS_KEY
org:target AKIA
org:target "aws_secret"
```

### Google Cloud

```
# Chave de API GCP
"AIza"
"AIzaSy"

# Conta de serviço GCP
"type": "service_account"
filename:credentials.json "private_key"
"client_secret" extension:json

# Firebase
"apiKey" "authDomain" "databaseURL"
filename:firebase
```

### Stripe

> **Classificação:** chaves Stripe `pk_` são publicáveis, ao contrário das chaves secretas. Uma configuração Firebase visível também não prova uma fuga de segredo: as suas chaves de API identificam o projeto e não substituem o controlo de acesso. Ver [Stripe — chaves de API](https://docs.stripe.com/keys) e [Firebase — chaves de API](https://firebase.google.com/docs/projects/api-keys).

```
# Chaves Stripe
"sk_live_"
"rk_live_"
"pk_live_"
sk_live
rk_live

# Combinações
org:target sk_live
filename:.env STRIPE_SECRET
```

### Twilio

```
# SID e token Twilio
"TWILIO_ACCOUNT_SID"
"TWILIO_AUTH_TOKEN"
"twilio" "sid" "token"
```

### Tokens GitHub

```
# Tokens de acesso pessoal
ghp_
gho_
ghu_
ghs_
ghr_

# Padrões de tokens GitHub
"GITHUB_TOKEN"
"github_token"
"gh_token"
filename:.env GITHUB_TOKEN
```

### Slack

```
# Tokens Slack
"xoxb-"
"xoxp-"
"xoxs-"
"xoxa-"

# Webhook Slack
"hooks.slack.com"
"T[A-Z0-9]{8}/B[A-Z0-9]{8}/[a-zA-Z0-9]{24}"
```

### Outras chaves de API

```
# SendGrid
"SG."
"SENDGRID_API_KEY"

# Mailgun
"key-"
"MAILGUN_API_KEY"

# Mailchimp
"[0-9a-f]{32}-us[0-9]{2}"

# Square
"sq0atp-"
"sq0csp-"

# PayPal
"PAYPAL"

# Heroku
"HEROKU_API_KEY"
"[h|H]eroku.*[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}"
```

---

<a id="-database-credentials"></a>
## 💾 Credenciais de bases de dados

### MySQL

```
# Cadeias de ligação MySQL
"mysql://"
"mysql_password"
"MYSQL_PASSWORD"
"DB_PASSWORD"

# Combinações
filename:.env MYSQL_PASSWORD
filename:config.php "mysql" "password"
"mysqli_connect" password
```

### PostgreSQL

```
# PostgreSQL
"postgres://"
"postgresql://"
"POSTGRES_PASSWORD"
"PG_PASSWORD"
filename:.env POSTGRES
```

### MongoDB

```
# Ligação MongoDB
"mongodb://"
"mongodb+srv://"
"MONGO_URI"
"MONGODB_URI"

# Com credenciais
"mongodb://.*:.*@"
filename:.env MONGO
```

### Redis

```
# Redis
"redis://"
"REDIS_PASSWORD"
"REDIS_URL"
```

### SQLite

```
# SQLite
filename:*.sqlite
filename:*.db
extension:sqlite
```

### Base de dados genérica

```
# Cadeias de ligação
"connection_string"
"connectionString"
"DB_CONNECTION"
"DATABASE_URL"

# Credenciais
"db_password"
"db_pass"
"database_password"
```

---

<a id="-private-keys"></a>
## 🔐 Chaves privadas

### Chaves SSH

```
# Chaves privadas SSH
"-----BEGIN RSA PRIVATE KEY-----"
"-----BEGIN DSA PRIVATE KEY-----"
"-----BEGIN EC PRIVATE KEY-----"
"-----BEGIN OPENSSH PRIVATE KEY-----"
"-----BEGIN PRIVATE KEY-----"

# Ficheiros
filename:id_rsa
filename:id_dsa
filename:id_ed25519
extension:pem
extension:key

# Combinações
filename:id_rsa -example -sample
```

### Chaves PGP/GPG

```
"-----BEGIN PGP PRIVATE KEY BLOCK-----"
filename:*.asc "PRIVATE KEY"
```

### Certificados SSL/TLS

> Um certificado público e uma chave privada são objetos diferentes. Encontrar o marcador de um certificado não demonstra, por si só, exposição de uma chave privada.

```
"-----BEGIN CERTIFICATE-----"
filename:*.crt
filename:*.cer
extension:pem "CERTIFICATE"
```

---

<a id="-configuration-files"></a>
## ⚙️ Ficheiros de configuração

### Ficheiros de variáveis de ambiente

```
# Ficheiros .env
filename:.env
filename:.env.local
filename:.env.production
filename:.env.development
filename:.env.staging
filename:.env.backup

# Com segredos
filename:.env DB_PASSWORD
filename:.env API_KEY
filename:.env SECRET
```

### Ficheiros de configuração

```
# Vários ficheiros de configuração
filename:config.json
filename:config.yml
filename:config.yaml
filename:settings.py
filename:settings.json
filename:application.properties
filename:application.yml
filename:secrets.yml
filename:credentials.json
filename:credentials.xml
```

### Docker

```
# Configurações Docker
filename:docker-compose.yml password
filename:Dockerfile ENV
filename:.dockercfg
filename:docker-compose.yml
```

### Kubernetes

```
# Segredos Kubernetes
filename:*.yaml "kind: Secret"
filename:secrets.yaml
"kubernetes" "secret"
```

### CI/CD

```
# Jenkins
filename:Jenkinsfile password
filename:jenkins.xml

# Travis CI
filename:.travis.yml

# GitHub Actions
filename:*.yml "secrets"
path:.github/workflows

# GitLab CI
filename:.gitlab-ci.yml
```

---

<a id="-cloud-secrets"></a>
## ☁️ Segredos cloud

### AWS

```
# Ficheiro de credenciais AWS
filename:credentials aws_access_key_id
filename:.aws/credentials

# Configuração AWS
"aws_access_key_id"
"aws_secret_access_key"
"aws_session_token"

# Buckets S3
"s3.amazonaws.com"
"s3-"
".s3.amazonaws.com"
```

### Azure

```
# Azure
"AZURE_CLIENT_SECRET"
"AZURE_STORAGE_CONNECTION_STRING"
"AccountKey="
"SharedAccessSignature="

# Azure DevOps
"azure" "secret"
"azure" "token"
```

### Google Cloud

```
# GCP
"GOOGLE_APPLICATION_CREDENTIALS"
"GOOGLE_API_KEY"
"GOOGLE_CLOUD_PROJECT"
filename:credentials.json "private_key"
```

### DigitalOcean

```
"DIGITALOCEAN_ACCESS_TOKEN"
"DO_AUTH_TOKEN"
"digitalocean" "token"
```

### Heroku

```
"HEROKU_API_KEY"
"HEROKU_APP_NAME"
filename:.netrc heroku
```

---

<a id="-passwords--credentials"></a>
## 🔓 Palavras-passe e credenciais

### Palavras-passe incorporadas no código

```
# Variáveis de palavra-passe
password=
passwd=
pass=
pwd=
"password:"
"password ="

# Com valores
password="
password='
"password": "
'password': '
```

### Credenciais SMTP

```
# SMTP
"smtp_password"
"SMTP_PASSWORD"
"mail_password"
"EMAIL_PASSWORD"
"smtp" "password"
```

### Segredos OAuth

```
# OAuth
"client_secret"
"CLIENT_SECRET"
"oauth_token"
"OAUTH_SECRET"
"refresh_token"
```

### Segredos JWT

```
# JWT
"JWT_SECRET"
"jwt_secret"
"JWT_KEY"
"secret_key"
"SECRET_KEY"
```

### Credenciais de administração

```
# Administração
"admin_password"
"root_password"
"master_password"
"default_password"
```

---

<a id="-bug-bounty-targets"></a>
## 🎯 Alvos de bug bounty

### Organização alvo

```
# Pesquisar numa organização específica
org:target-company

# Repositórios de um utilizador
user:target-username

# Repositório específico
repo:target/repository

# Combinações com segredos
org:target-company password
org:target-company api_key
org:target-company "secret"
```

### Pesquisa baseada no domínio

```
# Domínio alvo
"target.com"
"@target.com"
"api.target.com"
"internal.target.com"

# Combinações
"target.com" password
"target.com" api_key
org:target "@target.com"
```

### Emails de funcionários

```
# Encontrar repositórios de funcionários
"@target.com"

# Emails de programadores
"@target.com" password
"@target.com" config
```

### URLs internos

```
# Serviços internos
"internal.target.com"
"dev.target.com"
"staging.target.com"
"admin.target.com"
"vpn.target.com"
"jira.target.com"
"confluence.target.com"
```

### Modelo de dorks para bug bounty

```
# Reconhecimento completo
org:TARGET filename:.env
org:TARGET filename:config.json password
org:TARGET extension:sql
org:TARGET "api_key"
org:TARGET "secret_key"
org:TARGET "password"
org:TARGET "token"
org:TARGET "AWS_ACCESS"
org:TARGET internal
"@TARGET.com" password
"TARGET.com" api_key
```

---

<a id="-advanced-techniques"></a>
## 🔬 Técnicas avançadas

### Pesquisa no histórico Git

> **Âmbito da pesquisa:** a pesquisa web de código não percorre todo o histórico de commits. A documentação descreve pesquisa no ramo predefinido, com limitações de indexação; `pushed:` não transforma uma consulta de código numa pesquisa histórica. Ver as [limitações oficiais](https://docs.github.com/en/search-github/github-code-search/about-github-code-search).

```
# Verificar commits
# (Utilizar ferramentas como TruffleHog e git-secrets)

# Atualizados recentemente por push
pushed:>2024-01-01 password
pushed:>2024-01-01 filename:.env
```

### Pesquisa de Gists

```
# Pesquisar Gists
https://gist.github.com/search?q=password+target

# Padrões de Gists
gist password
gist api_key
```

### Excertos de código

```
# Pesquisar no código
in:file password
in:path config

# Ficheiros grandes (podem conter mais dados)
size:>10000 password
```

### Padrões regex (com ferramentas)

```
# Padrão de chave AWS
AKIA[0-9A-Z]{16}

# Token GitHub
ghp_[a-zA-Z0-9]{36}

# Token Slack
xox[baprs]-[0-9]{12}-[0-9]{12}-[a-zA-Z0-9]{24}

# Chave privada
-----BEGIN [A-Z]+ PRIVATE KEY-----
```

---

<a id="-automation-tools"></a>
## 🛠️ Ferramentas de automatização

### TruffleHog

> **Versões:** a instalação Python do original não deve ser assumida como instalação da versão atual. A documentação atual distingue fontes `git` e `github`; um URL de organização não equivale a um repositório Git. As opções de resultados também dependem da versão. Ver o [guia oficial de TruffleHog](https://github.com/trufflesecurity/trufflehog).

```bash
# Instalar
pip install truffleHog

# Analisar um repositório
trufflehog git https://github.com/target/repo

# Analisar uma organização
trufflehog git https://github.com/target --only-verified
```

### GitLeaks

> **Exemplo legado:** `detect` foi descontinuado como comando principal a partir de v8.19.0. A documentação atual apresenta `git`, `dir` e `stdin`; a análise Git usa repositórios locais. A linha com um URL em `--source` não demonstra uma análise remota válida. Ver a [documentação oficial de Gitleaks](https://github.com/gitleaks/gitleaks).

```bash
# Instalar
brew install gitleaks

# Analisar um repositório
gitleaks detect -s /path/to/repo

# Analisar a partir de um URL
gitleaks detect --source https://github.com/target/repo

# Saída JSON
gitleaks detect -s repo --report-format json -r results.json
```

### git-secrets

```bash
# Instalar
brew install git-secrets

# Adicionar padrões
git secrets --add 'AKIA[A-Z0-9]{16}'
git secrets --add 'password\s*=\s*["\'][^"\']*["\']'

# Analisar um repositório
git secrets --scan
```

### Ferramentas de pesquisa do GitHub

```bash
# Linha de comandos de dorks do GitHub
# Existem várias ferramentas da comunidade

# GitDorker
python3 gitdorker.py -t TOKEN -org target

# gitrob
gitrob target-org
```

### shhgit

```bash
# Monitorização do GitHub em tempo real
# Monitoriza segredos em tempo real
shhgit --search-query "org:target"
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Dorks essenciais

| Finalidade | Dork |
|---------|------|
| Chaves AWS | `org:target AKIA` |
| Palavras-passe | `org:target password filename:.env` |
| Chaves de API | `org:target api_key` |
| Chaves privadas | `org:target "BEGIN RSA PRIVATE KEY"` |
| Ficheiros de configuração | `org:target filename:config.json` |
| Credenciais de bases de dados | `org:target "mongodb://"` |
| Tokens | `org:target token secret` |
| Emails | `"@target.com" password` |

### Padrões de ficheiros

| Padrão | Dork |
|---------|------|
| `.env` | `filename:.env` |
| Configuração JSON | `filename:config.json` |
| Definições | `filename:settings.py` |
| Credenciais | `filename:credentials` |
| Segredos | `filename:secrets` |
| Docker | `filename:docker-compose.yml` |

### 15 dorks de referência do GitHub

| # | Dork | Finalidade |
|---|------|---------|
| 1 | `filename:.env DB_PASSWORD` | Palavras-passe de bases de dados |
| 2 | `filename:.env AWS_` | Credenciais AWS |
| 3 | `"-----BEGIN RSA PRIVATE KEY-----"` | Chaves privadas |
| 4 | `filename:config.json password` | Palavras-passe de configuração |
| 5 | `AKIA` | Chaves de acesso AWS |
| 6 | `sk_live` | Chaves Stripe |
| 7 | `"mongodb+srv://"` | URIs MongoDB |
| 8 | `filename:credentials aws` | Ficheiro de credenciais AWS |
| 9 | `ghp_` | Tokens GitHub |
| 10 | `xoxb-` | Tokens Slack |
| 11 | `org:target password` | Palavras-passe da organização |
| 12 | `filename:.npmrc _auth` | Tokens NPM |
| 13 | `extension:sql password` | Cópias integrais de bases de dados SQL |
| 14 | `filename:wp-config.php` | WordPress |
| 15 | `"api_key" extension:json` | Chaves de API |

---

## 💡 Dicas e boas práticas

### Dicas para bug bounty

1. **Pesquisar na organização**
   ```
   org:target-company
   ```

2. **Verificar repositórios de funcionários**
   ```
   "@target.com"
   ```

3. **Procurar URLs internos**
   ```
   org:target "internal" OR "dev" OR "staging"
   ```

4. **Pesquisar no histórico Git**
   - Utilizar TruffleHog ou GitLeaks

5. **Verificar Gists**
   - Os programadores guardam frequentemente excertos de código

### Divulgação responsável

- **Comunicar** as descobertas à equipa de segurança
- **Não** aceder a sistemas com as credenciais encontradas
- **Não** partilhar credenciais publicamente
- **Documentar** adequadamente as descobertas

---

## ⚠️ Aviso legal

> **AVISO:** O GitHub Dorking revela informações acessíveis publicamente. No entanto:
> 
> - ✅ Pesquisar em repositórios públicos
> - ✅ Comunicar descobertas a programas de bug bounty
> - ✅ Fazer divulgação responsável
> - ❌ Nunca utilizar as credenciais encontradas
> - ❌ Não aceder aos sistemas
> - ❌ Não guardar nem partilhar segredos
> 
> **Utilizar as credenciais encontradas é ilegal!**

---

<a id="-resources"></a>
## 📚 Recursos

### Ferramentas
- [TruffleHog](https://github.com/trufflesecurity/trufflehog)
- [GitLeaks](https://github.com/gitleaks/gitleaks)
- [git-secrets](https://github.com/awslabs/git-secrets)
- [shhgit](https://github.com/eth0izzle/shhgit)

### Referências
- [Documentação de pesquisa do GitHub](https://docs.github.com/en/search-github)
- [Padrões de deteção de segredos](https://docs.github.com/en/code-security/secret-scanning)

### Guias de referência relacionados
- [Google Dorking](../../Google-Dorking/translations/README.pt.md)
- [Shodan](../../Shodan/translations/README.pt.md)
- [Subfinder](../../Subfinder/README.md)

---

<p align="center">
  <b>🐙 Encontra os segredos antes dos atacantes!</b><br>
  <i>Domina o GitHub Dorking para bug bounty</i>
</p>
