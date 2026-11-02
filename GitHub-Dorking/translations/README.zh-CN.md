# 🐙 GitHub Dorking — 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

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
  <img src="https://img.shields.io/badge/GitHub-Dorking-red?style=for-the-badge" alt="GitHub 高级搜索">
  <img src="https://img.shields.io/badge/Secret-Hunting-orange?style=for-the-badge" alt="敏感信息查找">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="漏洞赏金">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
</p>

<p align="center">
  <b>🔐 查找 GitHub 仓库中泄露的敏感信息、API 密钥和凭据</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [GitHub 搜索语法](#-github-search-syntax)
- [API 密钥与令牌](#-api-keys--tokens)
- [数据库凭据](#-database-credentials)
- [私钥](#-private-keys)
- [配置文件](#-configuration-files)
- [云服务敏感信息](#-cloud-secrets)
- [密码与凭据](#-passwords--credentials)
- [漏洞赏金目标](#-bug-bounty-targets)
- [进阶技巧](#-advanced-techniques)
- [自动化工具](#-automation-tools)
- [快速参考](#-quick-reference)
- [参考资料](#-resources)

---

<a id="-introduction"></a>

## 🎯 简介

**GitHub Dorking** 是利用 GitHub 高级搜索，在公开仓库中查找泄露密钥、凭据和其他敏感信息的技术；这里的 dork 指经过组合的搜索语句。

### 可以找到什么？

| 类别 | 示例 |
|----------|----------|
| **API 密钥** | AWS, Google, Stripe, Twilio |
| **令牌** | OAuth、JWT、个人访问令牌 |
| **凭据** | 数据库密码、SMTP、SSH |
| **私钥** | RSA、SSH、PEM 文件 |
| **配置文件** | .env, config.yml, settings.py |

### 为什么值得关注

- 开发者可能误将敏感信息提交到仓库
- Git 历史记录中可能仍保留已删除的敏感信息
- 公开仓库会被索引，并可供搜索
- 这是漏洞赏金项目中发现问题的重要途径

---

<a id="-github-search-syntax"></a>

## 🔧 GitHub 搜索语法

> **译注：**本文混用了旧版代码搜索与当前网页搜索语法。`in:file`、`in:path`、`filename:`、`extension:`、`size:` 等示例应结合 [旧版／代码搜索 API 文档](https://docs.github.com/en/search-github/searching-on-github/searching-code)理解；当前网页代码搜索使用 `content:`、`path:` 等限定符，排除条件使用 `NOT`。原表与查询保留，具体适用范围参见 [当前语法](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax)。

### 基本搜索 URL

```
https://github.com/search?q=YOUR_QUERY&type=code
```

### 搜索限定符

| 限定符 | 说明 | 示例 |
|-----------|-------------|---------|
| `in:file` | 搜索文件内容 | `password in:file` |
| `in:path` | 搜索文件路径 | `config in:path` |
| `filename:` | 按文件名搜索 | `filename:.env` |
| `extension:` | 按扩展名搜索 | `extension:json` |
| `language:` | 按编程语言搜索 | `language:python` |
| `user:` | 搜索指定用户的仓库 | `user:target` |
| `org:` | 搜索指定组织的仓库 | `org:company` |
| `repo:` | 搜索指定仓库 | `repo:user/repo` |
| `path:` | 搜索指定路径 | `path:config/` |
| `size:` | 文件大小 | `size:>1000` |

### 布尔运算符

```
# AND（空格）
password database

# OR
password OR secret

# NOT（-）
password -test -example

# 精确短语
"api_key"

# 组合查询
org:target "api_key" NOT test
```

---

<a id="-api-keys--tokens"></a>

## 🔑 API 密钥与令牌

> **译注：**前缀、变量名或模式匹配只提供候选线索，不证明凭据真实、仍有效或具有权限；不同服务会更新令牌格式。本文正则式并不覆盖所有版本，参见 [GitHub 支持的敏感信息扫描模式](https://docs.github.com/en/code-security/reference/secret-security/supported-secret-scanning-patterns)。

### AWS 密钥

```
# AWS 访问密钥 ID
AKIA
AKID
"aws_access_key_id"
"AWS_ACCESS_KEY_ID"

# AWS 秘密访问密钥
"aws_secret_access_key"
"AWS_SECRET_ACCESS_KEY"

# 组合查询
filename:.env AWS_ACCESS_KEY
org:target AKIA
org:target "aws_secret"
```

### Google Cloud

```
# GCP API 密钥
"AIza"
"AIzaSy"

# GCP 服务账号
"type": "service_account"
filename:credentials.json "private_key"
"client_secret" extension:json

# Firebase
"apiKey" "authDomain" "databaseURL"
filename:firebase
```

### Stripe

```
# Stripe 密钥
"sk_live_"
"rk_live_"
"pk_live_"
sk_live
rk_live

# 组合查询
org:target sk_live
filename:.env STRIPE_SECRET
```

### Twilio

```
# Twilio SID 与令牌
"TWILIO_ACCOUNT_SID"
"TWILIO_AUTH_TOKEN"
"twilio" "sid" "token"
```

### GitHub 令牌

```
# 个人访问令牌
ghp_
gho_
ghu_
ghs_
ghr_

# GitHub 令牌模式
"GITHUB_TOKEN"
"github_token"
"gh_token"
filename:.env GITHUB_TOKEN
```

### Slack

```
# Slack 令牌
"xoxb-"
"xoxp-"
"xoxs-"
"xoxa-"

# Slack Webhook
"hooks.slack.com"
"T[A-Z0-9]{8}/B[A-Z0-9]{8}/[a-zA-Z0-9]{24}"
```

### 其他 API 密钥

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

## 💾 数据库凭据

### MySQL

```
# MySQL 连接字符串
"mysql://"
"mysql_password"
"MYSQL_PASSWORD"
"DB_PASSWORD"

# 组合查询
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
# MongoDB 连接
"mongodb://"
"mongodb+srv://"
"MONGO_URI"
"MONGODB_URI"

# 含凭据
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

### 通用数据库

```
# 连接字符串
"connection_string"
"connectionString"
"DB_CONNECTION"
"DATABASE_URL"

# 凭据
"db_password"
"db_pass"
"database_password"
```

---

<a id="-private-keys"></a>

## 🔐 私钥

### SSH 密钥

```
# SSH 私钥
"-----BEGIN RSA PRIVATE KEY-----"
"-----BEGIN DSA PRIVATE KEY-----"
"-----BEGIN EC PRIVATE KEY-----"
"-----BEGIN OPENSSH PRIVATE KEY-----"
"-----BEGIN PRIVATE KEY-----"

# 文件
filename:id_rsa
filename:id_dsa
filename:id_ed25519
extension:pem
extension:key

# 组合查询
filename:id_rsa -example -sample
```

### PGP/GPG 密钥

```
"-----BEGIN PGP PRIVATE KEY BLOCK-----"
filename:*.asc "PRIVATE KEY"
```

### SSL/TLS 证书

```
"-----BEGIN CERTIFICATE-----"
filename:*.crt
filename:*.cer
extension:pem "CERTIFICATE"
```

---

<a id="-configuration-files"></a>

## ⚙️ 配置文件

### 环境变量文件

```
# .env 文件
filename:.env
filename:.env.local
filename:.env.production
filename:.env.development
filename:.env.staging
filename:.env.backup

# 包含敏感信息
filename:.env DB_PASSWORD
filename:.env API_KEY
filename:.env SECRET
```

### 配置文件

```
# 各类配置文件
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
# Docker 配置
filename:docker-compose.yml password
filename:Dockerfile ENV
filename:.dockercfg
filename:docker-compose.yml
```

### Kubernetes

```
# K8s 敏感信息
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

## ☁️ 云服务敏感信息

### AWS

```
# AWS 凭据文件
filename:credentials aws_access_key_id
filename:.aws/credentials

# AWS 配置
"aws_access_key_id"
"aws_secret_access_key"
"aws_session_token"

# S3 存储桶
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

## 🔓 密码与凭据

### 硬编码密码

```
# 密码变量
password=
passwd=
pass=
pwd=
"password:"
"password ="

# 含变量值
password="
password='
"password": "
'password': '
```

### SMTP 凭据

```
# SMTP
"smtp_password"
"SMTP_PASSWORD"
"mail_password"
"EMAIL_PASSWORD"
"smtp" "password"
```

### OAuth 敏感信息

```
# OAuth
"client_secret"
"CLIENT_SECRET"
"oauth_token"
"OAUTH_SECRET"
"refresh_token"
```

### JWT 密钥

```
# JWT
"JWT_SECRET"
"jwt_secret"
"JWT_KEY"
"secret_key"
"SECRET_KEY"
```

### 管理员凭据

```
# 管理员
"admin_password"
"root_password"
"master_password"
"default_password"
```

---

<a id="-bug-bounty-targets"></a>

## 🎯 漏洞赏金目标

### 目标组织

```
# 搜索指定组织
org:target-company

# 用户仓库
user:target-username

# 指定仓库
repo:target/repository

# 结合敏感信息关键词
org:target-company password
org:target-company api_key
org:target-company "secret"
```

### 基于域名搜索

```
# 目标域名
"target.com"
"@target.com"
"api.target.com"
"internal.target.com"

# 组合查询
"target.com" password
"target.com" api_key
org:target "@target.com"
```

### 员工邮箱

```
# 查找员工仓库
"@target.com"

# 开发者邮箱
"@target.com" password
"@target.com" config
```

### 内部 URL

```
# 内部服务
"internal.target.com"
"dev.target.com"
"staging.target.com"
"admin.target.com"
"vpn.target.com"
"jira.target.com"
"confluence.target.com"
```

### 漏洞赏金搜索模板

```
# 完整信息收集
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

## 🔬 进阶技巧

### Git 历史记录搜索

> **译注：**Git 历史可能保留已删除内容，但 [网页代码搜索](https://docs.github.com/en/search-github/github-code-search/about-github-code-search)只搜索默认分支的索引，并非完整历史；`pushed:` 是 [仓库搜索](https://docs.github.com/en/search-github/searching-on-github/searching-for-repositories)的更新时间限定符，不会把下面的查询变成提交内容搜索。

```
# 检查提交记录
# （使用 TruffleHog、git-secrets 等工具）

# 最近推送的仓库
pushed:>2024-01-01 password
pushed:>2024-01-01 filename:.env
```

### Gist 搜索

```
# 搜索 Gist
https://gist.github.com/search?q=password+target

# Gist 模式
gist password
gist api_key
```

### 代码片段

```
# 搜索代码
in:file password
in:path config

# 大文件（可能包含更多数据）
size:>10000 password
```

### 正则表达式模式（配合工具）

> **译注：**下列模式用于支持正则的工具。当前网页搜索要求用斜杠包围正则；例如上文双引号中的 Slack、Mailchimp 或 Heroku 模式不会自动按正则解析。布尔组合涉及不同作用域时也应明确括号分组。参见 [官方代码搜索语法](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax)。

```
# AWS 密钥模式
AKIA[0-9A-Z]{16}

# GitHub 令牌
ghp_[a-zA-Z0-9]{36}

# Slack 令牌
xox[baprs]-[0-9]{12}-[0-9]{12}-[a-zA-Z0-9]{24}

# 私钥
-----BEGIN [A-Z]+ PRIVATE KEY-----
```

---

<a id="-automation-tools"></a>

## 🛠️ 自动化工具

### TruffleHog

```bash
# 安装
pip install truffleHog

# 扫描仓库
trufflehog git https://github.com/target/repo

# 扫描组织
trufflehog git https://github.com/target --only-verified
```

### GitLeaks

```bash
# 安装
brew install gitleaks

# 扫描仓库
gitleaks detect -s /path/to/repo

# 从 URL 扫描
gitleaks detect --source https://github.com/target/repo

# 输出 JSON
gitleaks detect -s repo --report-format json -r results.json
```

### git-secrets

```bash
# 安装
brew install git-secrets

# 添加匹配模式
git secrets --add 'AKIA[A-Z0-9]{16}'
git secrets --add 'password\s*=\s*["\'][^"\']*["\']'

# 扫描仓库
git secrets --scan
```

### GitHub 搜索工具

```bash
# GitHub Dork 命令行工具
# 有多种社区工具可用

# GitDorker
python3 gitdorker.py -t TOKEN -org target

# gitrob
gitrob target-org
```

### shhgit

```bash
# 实时监控 GitHub
# 实时监测敏感信息
shhgit --search-query "org:target"
```

---

<a id="-quick-reference"></a>

## 📊 快速参考

### 常用搜索语句

| 用途 | 搜索语句 |
|---------|------|
| AWS 密钥 | `org:target AKIA` |
| 密码 | `org:target password filename:.env` |
| API 密钥 | `org:target api_key` |
| 私钥 | `org:target "BEGIN RSA PRIVATE KEY"` |
| 配置文件 | `org:target filename:config.json` |
| 数据库凭据 | `org:target "mongodb://"` |
| 令牌 | `org:target token secret` |
| 邮箱 | `"@target.com" password` |

### 文件模式

| 模式 | 搜索语句 |
|---------|------|
| `.env` | `filename:.env` |
| JSON 配置 | `filename:config.json` |
| 设置文件 | `filename:settings.py` |
| 凭据文件 | `filename:credentials` |
| 敏感信息文件 | `filename:secrets` |
| Docker | `filename:docker-compose.yml` |

### 15 条常用 GitHub 搜索语句

| # | 搜索语句 | 用途 |
|---|------|---------|
| 1 | `filename:.env DB_PASSWORD` | 数据库密码 |
| 2 | `filename:.env AWS_` | AWS 凭据 |
| 3 | `"-----BEGIN RSA PRIVATE KEY-----"` | 私钥 |
| 4 | `filename:config.json password` | 配置文件中的密码 |
| 5 | `AKIA` | AWS 访问密钥 |
| 6 | `sk_live` | Stripe 密钥 |
| 7 | `"mongodb+srv://"` | MongoDB URI |
| 8 | `filename:credentials aws` | AWS 凭据文件 |
| 9 | `ghp_` | GitHub 令牌 |
| 10 | `xoxb-` | Slack 令牌 |
| 11 | `org:target password` | 组织的密码 |
| 12 | `filename:.npmrc _auth` | NPM 令牌 |
| 13 | `extension:sql password` | SQL 导出文件 |
| 14 | `filename:wp-config.php` | WordPress |
| 15 | `"api_key" extension:json` | API 密钥 |

---

## 💡 技巧与最佳实践

### 漏洞赏金技巧

1. **搜索目标组织**
   ```
   org:target-company
   ```

2. **检查员工仓库**
   ```
   "@target.com"
   ```

3. **查找内部 URL**
   ```
   org:target "internal" OR "dev" OR "staging"
   ```

4. **搜索 Git 历史记录**
   - 使用 TruffleHog 或 GitLeaks

5. **检查 Gist**
   - 开发者经常在其中保存代码片段

### 负责任披露

- 向安全团队**报告**发现的问题
- **不要**使用发现的凭据访问系统
- **不要**公开分享凭据
- 妥善**记录**发现的问题

---

## ⚠️ 法律免责声明

> **警告：**GitHub Dorking 可以发现公开可访问的信息。不过：
> 
> - ✅ 搜索公开仓库
> - ✅ 向漏洞赏金项目报告问题
> - ✅ 负责任披露
> - ❌ 不得使用发现的凭据
> - ❌ 不得访问相关系统
> - ❌ 不得存储或分享敏感信息
> 
> **使用发现的凭据属于违法行为！**

---

<a id="-resources"></a>

## 📚 参考资料

### 工具
- [TruffleHog](https://github.com/trufflesecurity/trufflehog)
- [GitLeaks](https://github.com/gitleaks/gitleaks)
- [git-secrets](https://github.com/awslabs/git-secrets)
- [shhgit](https://github.com/eth0izzle/shhgit)

### 参考文档
- [GitHub 搜索文档](https://docs.github.com/en/search-github)
- [敏感信息扫描模式](https://docs.github.com/en/code-security/secret-scanning)

### 相关速查表
- [Google Dorking](../../Google-Dorking/README.md)
- [Shodan](../../Shodan/README.md)
- [Subfinder](../../Subfinder/README.md)

---

<p align="center">
  <b>🐙 在攻击者之前发现泄露的敏感信息！</b><br>
  <i>掌握 GitHub Dorking，应用于漏洞赏金测试</i>
</p>
