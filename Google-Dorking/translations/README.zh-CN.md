<a id="-google-dorking---complete-cheatsheet"></a>
# 🔍 Google Dorking 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

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
  <img src="https://img.shields.io/badge/Information-Gathering-green?style=for-the-badge" alt="Info Gathering">
</p>

<p align="center">
  <b>🎯 面向安全研究人员的 Google 高级搜索技巧</b><br>
  <a href="./README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="./README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 目录

- [简介](#-introduction)
- [基本运算符](#-basic-operators)
- [高级运算符](#-advanced-operators)
- [组合运算符](#-combining-operators)
- [文件发现](#-file-discovery)
- [目录列表](#-directory-listing)
- [管理面板](#-admin-panels)
- [数据库与 SQL](#-database--sql)
- [配置文件](#-configuration-files)
- [凭据与机密信息](#-credentials--secrets)
- [可能存在漏洞的服务器](#-vulnerable-servers)
- [漏洞赏金搜索语句](#-bug-bounty-dorks)
- [针对特定 CMS 的搜索](#-cms-specific)
- [速查](#-quick-reference)
- [工具与自动化](#-tools--automation)
- [参考资料](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**Google Dorking**（也称 Google Hacking）是一种利用高级搜索运算符查找互联网上暴露的敏感信息的技术。

<a id="what-can-you-find"></a>
### 可以找到什么？

| 类别 | 示例 |
|----------|----------|
| **敏感文件** | 备份、配置、日志、数据库 |
| **暴露的管理面板** | 管理面板、仪表板、phpMyAdmin |
| **凭据** | 密码、API 密钥、令牌 |
| **漏洞** | SQL 注入、开放目录 |
| **个人数据** | 邮箱、电话号码、文档 |

<a id="search-engine-urls"></a>
### 搜索引擎网址

| 搜索引擎 | URL |
|--------|-----|
| **Google** | https://www.google.com |
| **Bing** | https://www.bing.com |
| **DuckDuckGo** | https://duckduckgo.com |
| **Yandex** | https://yandex.com |

---

<a id="-basic-operators"></a>
## 🔧 基本运算符

<a id="site-operator"></a>
### site: 运算符

将搜索限制在指定域名：

```
site:example.com                     # 仅限 example.com
site:*.example.com                   # 所有子域名
site:example.com -www                # 排除 www
site:.gov                            # 政府网站
site:.edu                            # 教育机构网站
```

<a id="inurl-operator"></a>
### inurl: 运算符

在 URL 中搜索：

```
inurl:admin                          # URL 中包含 admin
inurl:login                          # login 登录页面
inurl:dashboard                      # dashboard 仪表板
inurl:config                         # config 配置文件
inurl:backup                         # backup 备份文件
```

<a id="intitle-operator"></a>
### intitle: 运算符

在页面标题中搜索：

```
intitle:"index of"                   # 目录列表
intitle:admin                        # admin 管理页面
intitle:login                        # login 登录页面
intitle:"dashboard"                  # dashboard 仪表板
intitle:"control panel"              # control panel 控制面板
```

<a id="intext-operator"></a>
### intext: 运算符

在页面内容中搜索：

```
intext:"password"                    # 文本中包含 password
intext:"username"                    # 文本中包含 username
intext:"api_key"                     # API 密钥
intext:"secret"                      # 机密信息
```

<a id="filetype-operator"></a>
### filetype: 运算符

搜索指定文件类型：

```
filetype:pdf                         # PDF 文件
filetype:doc                         # Word 文档
filetype:xls                         # Excel 文件
filetype:sql                         # SQL 文件
filetype:log                         # 日志文件
filetype:bak                         # 备份文件
filetype:env                         # 环境文件
```

---

<a id="-advanced-operators"></a>
## ⚡ 高级运算符

<a id="all-variants"></a>
### all 前缀变体

```
allinurl:admin login                 # URL 中包含所有词项
allintitle:admin panel               # 标题中包含所有词项
allintext:password username          # 文本中包含所有词项
```

<a id="cache--link"></a>
### 缓存与链接

```
cache:example.com                    # 缓存版本（运算符已停用，见译注）
link:example.com                     # 链接到目标的页面
related:example.com                  # 相关网站（运算符已停用，见译注）
```

<a id="date-range"></a>
### 日期范围

```
after:2023-01-01                     # 指定日期之后
before:2024-01-01                    # 指定日期之前
daterange:2458850-2459215            # 儒略日范围
```

<a id="wildcards--or"></a>
### 通配符与 OR

```
admin * panel                        # 通配符
admin OR login                       # 任一词项
"admin panel"                        # 精确短语
-inurl:https                         # 排除 HTTPS
```

<a id="number-range"></a>
### 数值范围

```
inurl:id= 1..1000                    # 数值范围
price $100..$500                     # 价格范围
```

---

<a id="-combining-operators"></a>
## 🔗 组合运算符

<a id="basic-combinations"></a>
### 基本组合

```
site:example.com filetype:pdf
site:example.com inurl:admin
site:example.com intitle:login
site:example.com intext:password
```

<a id="advanced-combinations"></a>
### 高级组合

```
site:example.com filetype:sql intext:password
site:example.com inurl:admin intitle:login
site:example.com filetype:log intext:error
site:example.com ext:php inurl:config
```

<a id="bug-bounty-combinations"></a>
### 漏洞赏金搜索组合

```
site:target.com (filetype:pdf | filetype:doc | filetype:xls)
site:target.com (inurl:admin | inurl:login | inurl:dashboard)
site:target.com (ext:sql | ext:bak | ext:log)
```

---

<a id="-file-discovery"></a>
## 📁 文件发现

<a id="sensitive-documents"></a>
### 敏感文档

```
# Office 文档
site:target.com filetype:doc
site:target.com filetype:docx
site:target.com filetype:xls
site:target.com filetype:xlsx
site:target.com filetype:ppt
site:target.com filetype:pdf

# 文本文件
site:target.com filetype:txt
site:target.com filetype:rtf
site:target.com filetype:csv
```

<a id="backup-files"></a>
### 备份文件

```
site:target.com filetype:bak
site:target.com filetype:backup
site:target.com filetype:old
site:target.com filetype:orig
site:target.com ext:bak
site:target.com inurl:backup
site:target.com intitle:"backup"
```

<a id="database-files"></a>
### 数据库文件

```
site:target.com filetype:sql
site:target.com filetype:db
site:target.com filetype:mdb
site:target.com filetype:sqlite
site:target.com filetype:dump
site:target.com ext:sql intext:INSERT
```

<a id="log-files"></a>
### 日志文件

```
site:target.com filetype:log
site:target.com ext:log
site:target.com inurl:log
site:target.com intext:"error" filetype:log
site:target.com intext:"exception" filetype:log
site:target.com intext:"fatal" filetype:log
```

<a id="configuration-files"></a>
### 配置文件

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

<a id="source-code"></a>
### 源代码

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
## 📂 目录列表

<a id="index-of"></a>
### 目录索引（Index Of）

```
intitle:"index of" site:target.com
intitle:"index of /" site:target.com
intitle:"directory listing" site:target.com
intitle:"parent directory" site:target.com
```

<a id="specific-directories"></a>
### 指定目录

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

<a id="file-types-in-directories"></a>
### 目录中的文件类型

```
intitle:"index of" "*.sql" site:target.com
intitle:"index of" "*.bak" site:target.com
intitle:"index of" "*.log" site:target.com
intitle:"index of" "*.zip" site:target.com
intitle:"index of" "*.tar.gz" site:target.com
```

---

<a id="-admin-panels"></a>
## 🔐 管理面板

<a id="generic-admin"></a>
### 通用管理面板

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

<a id="cpanel--hosting"></a>
### cPanel 与主机管理

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

<a id="database-admin"></a>
### 数据库管理

```
site:target.com inurl:phpmyadmin
site:target.com inurl:phpMyAdmin
site:target.com intitle:"phpMyAdmin"
site:target.com inurl:adminer
site:target.com inurl:pgadmin
site:target.com intitle:"pgAdmin"
site:target.com inurl:mysql
```

<a id="control-panels"></a>
### 控制面板

```
site:target.com intitle:"dashboard"
site:target.com intitle:"control panel"
site:target.com inurl:portal
site:target.com inurl:cms
site:target.com inurl:backend
```

---

<a id="-database--sql"></a>
## 💾 数据库与 SQL

<a id="sql-files"></a>
### SQL 文件

```
site:target.com filetype:sql
site:target.com filetype:sql intext:password
site:target.com filetype:sql intext:"INSERT INTO"
site:target.com filetype:sql intext:"CREATE TABLE"
site:target.com ext:sql "INSERT INTO `users`"
```

<a id="sql-errors"></a>
### SQL 错误

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

<a id="sql-injection-indicators"></a>
### SQL 注入线索

```
site:target.com inurl:id=
site:target.com inurl:page=
site:target.com inurl:cat=
site:target.com inurl:item=
site:target.com inurl:pid=
site:target.com inurl:product_id=
site:target.com inurl:news_id=
```

<a id="database-dumps"></a>
### 数据库转储

```
site:target.com "dump" filetype:sql
site:target.com "database dump" filetype:sql
site:target.com filetype:sql "-- MySQL dump"
site:target.com filetype:sql "-- phpMyAdmin SQL Dump"
```

---

<a id="-configuration-files"></a>
## ⚙️ 配置文件

<a id="web-server-config"></a>
### Web 服务器配置

```
site:target.com filetype:conf
site:target.com filetype:conf intext:password
site:target.com "httpd.conf"
site:target.com "nginx.conf"
site:target.com ".htaccess"
site:target.com ".htpasswd"
site:target.com "web.config"
```

<a id="application-config"></a>
### 应用配置

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

<a id="git-config"></a>
### Git 配置

```
site:target.com ".git/config"
site:target.com intitle:"index of" ".git"
site:target.com inurl:".git"
site:target.com filetype:git
```

<a id="aws--cloud"></a>
### AWS 与云服务

```
site:target.com filetype:pem
site:target.com filetype:ppk
site:target.com "aws_access_key_id"
site:target.com "aws_secret_access_key"
site:target.com "AKIA"                    # AWS 密钥前缀
site:target.com "credentials" filetype:json
```

---

<a id="-credentials--secrets"></a>
## 🔑 凭据与机密信息

<a id="passwords"></a>
### 密码

```
site:target.com intext:password
site:target.com intext:"password is"
site:target.com intext:"default password"
site:target.com filetype:txt password
site:target.com filetype:log password
site:target.com "password" filetype:xls
site:target.com "password" filetype:csv
```

<a id="username--password-combos"></a>
### 用户名与密码组合

```
site:target.com intext:username intext:password
site:target.com "username" "password" filetype:txt
site:target.com "login" "password" filetype:xls
site:target.com intext:"username:" intext:"password:"
```

<a id="api-keys--tokens"></a>
### API 密钥与令牌

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

<a id="email-addresses"></a>
### 邮箱地址

```
site:target.com "@target.com"
site:target.com filetype:xls "@target.com"
site:target.com filetype:csv "email"
site:target.com intext:"@gmail.com"
```

<a id="private-keys"></a>
### 私钥

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
## ⚠️ 可能存在漏洞的服务器

<a id="open-directories"></a>
### 开放目录

```
intitle:"index of" site:target.com
intitle:"directory listing" site:target.com
intitle:"Apache" "server at" site:target.com
```

<a id="default-pages"></a>
### 默认页面

```
site:target.com intitle:"Apache2 Ubuntu Default Page"
site:target.com intitle:"Welcome to nginx"
site:target.com intitle:"Test Page for Apache"
site:target.com intitle:"IIS Windows Server"
site:target.com intitle:"Welcome to CentOS"
```

<a id="error-pages"></a>
### 错误页面

```
site:target.com "404 Not Found" inurl:admin
site:target.com "500 Internal Server Error"
site:target.com "Error establishing database connection"
site:target.com "Parse error" "syntax error"
site:target.com "Warning:" "mysql_"
site:target.com "Fatal error:" "php"
```

<a id="exposed-services"></a>
### 暴露的服务

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
## 🐛 漏洞赏金搜索语句

<a id="quick-wins"></a>
### 快速寻找线索

```
# 敏感文件
site:target.com (filetype:sql | filetype:bak | filetype:log | filetype:env)

# 管理面板
site:target.com (inurl:admin | inurl:login | inurl:dashboard)

# 配置文件
site:target.com (filetype:conf | filetype:config | filetype:ini | filetype:env)

# 凭据
site:target.com (intext:password | intext:api_key | intext:secret)
```

<a id="subdomain-discovery"></a>
### 子域名发现

```
site:*.target.com -www
site:*.target.com -www -mail
site:*.*.target.com
```

<a id="api-endpoints"></a>
### API 端点

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

<a id="development--staging"></a>
### 开发与预发布环境

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

<a id="authentication-pages"></a>
### 身份验证页面

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

<a id="file-upload"></a>
### 文件上传

```
site:target.com inurl:upload
site:target.com intitle:upload
site:target.com inurl:file
site:target.com "choose file"
site:target.com "upload file"
```

---

<a id="-cms-specific"></a>
## 📱 针对特定 CMS 的搜索

<a id="wordpress"></a>
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

<a id="joomla"></a>
### Joomla

```
site:target.com inurl:administrator
site:target.com inurl:com_
site:target.com "configuration.php"
site:target.com "Joomla"
```

<a id="drupal"></a>
### Drupal

```
site:target.com inurl:node/
site:target.com inurl:sites/default/files
site:target.com "Drupal"
site:target.com "CHANGELOG.txt"
```

<a id="magento"></a>
### Magento

```
site:target.com inurl:app/etc/local.xml
site:target.com inurl:downloader
site:target.com "Magento"
```

---

<a id="-quick-reference"></a>
## 📊 速查

<a id="essential-operators"></a>
### 常用运算符

| 运算符 | 功能 | 示例 |
|----------|----------|---------|
| `site:` | 限定域名 | `site:target.com` |
| `inurl:` | 在 URL 中搜索 | `inurl:admin` |
| `intitle:` | 在标题中搜索 | `intitle:login` |
| `intext:` | 在内容中搜索 | `intext:password` |
| `filetype:` | 文件扩展名 | `filetype:sql` |
| `ext:` | 文件扩展名 | `ext:pdf` |
| `cache:` | 缓存版本 | `cache:target.com` |
| `"..."` | 精确短语 | `"admin panel"` |
| `-` | 排除 | `-site:www` |
| `OR` / `|` | 任一词项 | `admin OR login` |
| `*` | 通配符 | `admin * panel` |

<a id="top-10-dorks-for-bug-bounty"></a>
### 漏洞赏金常用 10 条搜索语句

| # | 搜索语句 | 用途 |
|---|------|---------|
| 1 | `site:target.com filetype:sql` | 数据库文件 |
| 2 | `site:target.com inurl:admin` | 管理面板 |
| 3 | `site:target.com filetype:log` | 日志文件 |
| 4 | `site:target.com filetype:env` | 环境文件 |
| 5 | `site:target.com intext:password filetype:txt` | 密码 |
| 6 | `intitle:"index of" site:target.com` | 目录列表 |
| 7 | `site:target.com inurl:api` | API 端点 |
| 8 | `site:target.com filetype:bak` | 备份文件 |
| 9 | `site:*.target.com -www` | 子域名 |
| 10 | `site:target.com "api_key"` | API 密钥 |

---

<a id="-tools--automation"></a>
## 🛠️ 工具与自动化

<a id="google-dorking-tools"></a>
### Google Dorking 工具

| 工具 | 说明 | URL |
|------|-------------|-----|
| **Google Hacking Database** | Exploit-DB 搜索语句合集 | [GHDB](https://www.exploit-db.com/google-hacking-database) |
| **DorkSearch** | 搜索语句生成器 | [DorkSearch](https://dorksearch.com) |
| **Pagodo** | 利用 Google 进行被动搜索 | GitHub |
| **GooDork** | Python 搜索工具 | GitHub |
| **GooFuzz** | 利用 Google 进行模糊测试 | GitHub |

<a id="dorking-websites"></a>
### Dorking 网站

```
# Google Hacking Database
https://www.exploit-db.com/google-hacking-database

# Pentest-Tools
https://pentest-tools.com/information-gathering/google-hacking

# DorkSearch
https://dorksearch.com
```

<a id="automation-script-example"></a>
### 自动化脚本示例

```bash
#!/bin/bash
# 简单的 Google Dork 自动化

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

<a id="-tips--best-practices"></a>
## 💡 技巧与最佳实践

<a id="bug-bounty-tips"></a>
### 漏洞赏金技巧

1. **先广泛搜索，再逐步细化**
   ```
   site:target.com                    # 首先
   site:target.com filetype:sql       # 然后细化
   ```

2. **使用多个搜索引擎**
   - Google, Bing, DuckDuckGo, Yandex
   
3. **查看缓存页面**
   ```
   cache:target.com/admin
   ```

4. **寻找子域名**
   ```
   site:*.target.com -www
   ```

5. **限制搜索频率**
   - Google 可能阻止过于频繁的查询

<a id="avoid-detection"></a>
### 避免被检测

```
# 使用不同的 IP／VPN
# 在搜索之间增加延迟
# 变换搜索模式
# 使用不同浏览器
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ 法律免责声明

> **警告：** 原文称 Google Dorking 本身合法，但**未经授权访问**暴露的数据可能违法。
> 
> - ✅ 用于已获授权的安全测试
> - ✅ 用于漏洞赏金项目
> - ✅ 负责任地报告漏洞
> - ❌ 绝不未经许可访问数据
> - ❌ 绝不下载／存储敏感数据
> - ❌ 不要利用发现的漏洞
> 
> **测试前务必获得适当授权！**

---

<a id="-resources"></a>
## 📚 参考资料

<a id="official-resources"></a>
### 官方资源
- [Google 搜索运算符](https://support.google.com/websearch/answer/2466433)
- [Google Hacking Database](https://www.exploit-db.com/google-hacking-database)

<a id="books--guides"></a>
### 书籍与指南
- Johnny Long 著 "Google Hacking for Penetration Testers"
- OWASP 测试指南

<a id="related-cheatsheets"></a>
### 相关速查表
- [Shodan](../../Shodan/translations/README.zh-CN.md)
- [GitHub Dorking](../../GitHub-Dorking/translations/README.zh-CN.md)
- [Subfinder](../../Subfinder/translations/README.zh-CN.md)

---

<p align="center">
  <b>🔍 发现他人隐藏的信息！</b><br>
  <i>掌握 Google Dorking 技巧</i>
</p>

> 译注：本页保留了历史搜索示例，并不保证所有运算符仍受支持。[Google 官方文档更新记录](https://developers.google.com/search/updates)说明 cache: 已失效，related: 也已停止支持；相关示例仅作历史参考。其他运算符、通配符和范围写法的效果应以当前搜索引擎文档为准，不同引擎的语法并不完全相同。

> 译注：[Google 的 site: 说明](https://developers.google.com/search/docs/monitor-debug/search-operators/all-search-site)指出，结果并非完整索引清单，未返回结果不能证明目标不存在或没有暴露信息。页面包含某个参数、错误关键词或默认标题也不能单独证明存在可利用漏洞；搜索到某种扩展名不表示能够获取服务器源码。示例脚本只打印搜索 URL，未发起搜索，且仅将空格替换为加号，并未完成通用 URL 编码。

> 译注：原文法律声明是概括性表述，不能视为适用于所有司法辖区、平台条款或数据处理情形的法律结论。
