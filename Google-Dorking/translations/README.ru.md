<a id="-google-dorking---complete-cheatsheet"></a>
# 🔍 Google Dorking — полный справочник

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

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
  <b>🎯 Расширенные приёмы поиска Google для исследователей безопасности</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Базовые операторы](#-basic-operators)
- [Расширенные операторы](#-advanced-operators)
- [Сочетание операторов](#-combining-operators)
- [Поиск файлов](#-file-discovery)
- [Просмотр содержимого каталогов](#-directory-listing)
- [Панели администрирования](#-admin-panels)
- [Базы данных и SQL](#-database--sql)
- [Файлы конфигурации](#-configuration-files)
- [Учётные данные и секреты](#-credentials--secrets)
- [Потенциально уязвимые серверы](#-vulnerable-servers)
- [Дорки для Bug Bounty](#-bug-bounty-dorks)
- [Поиск для конкретных CMS](#-cms-specific)
- [Краткий справочник](#-quick-reference)
- [Инструменты и автоматизация](#-tools--automation)
- [Ресурсы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

> **Уточнение к оригиналу.** Совпадение дорка не подтверждает уязвимость или действительность найденного секрета. [Google предупреждает](https://developers.google.com/search/docs/monitor-debug/search-operators/all-search-site), что site: не возвращает все индексируемые URL; site:domain также охватывает поддомены. Формулировки «все поддомены», -www и исключение HTTPS упрощены: исключение слова не равно точному фильтру хоста или протокола. Строки запросов и английские фразы в кавычках сохранены дословно.


**Google Dorking** (также Google Hacking) — поиск с помощью расширенных операторов, позволяющий находить конфиденциальную информацию, доступную в интернете. Дорк (dork) — специально составленный поисковый запрос.

<a id="what-can-you-find"></a>
### Что можно найти?

| Категория | Примеры |
|----------|----------|
| **Конфиденциальные файлы** | Резервные копии, конфигурации, журналы, базы данных |
| **Доступные извне панели** | Панели администрирования, информационные панели, phpMyAdmin |
| **Учётные данные** | Пароли, ключи API, токены |
| **Уязвимости** | SQL-инъекции, открытые каталоги |
| **Персональные данные** | Адреса электронной почты, номера телефонов, документы |

<a id="search-engine-urls"></a>
### Адреса поисковых систем

| Поисковая система | URL |
|--------|-----|
| **Google** | https://www.google.com |
| **Bing** | https://www.bing.com |
| **DuckDuckGo** | https://duckduckgo.com |
| **Yandex** | https://yandex.com |

---

<a id="-basic-operators"></a>
## 🔧 Базовые операторы

<a id="site-operator"></a>
### Оператор site

Ограничить поиск указанным доменом:

```
site:example.com                     # Только example.com
site:*.example.com                   # Все поддомены (формулировка оригинала; см. уточнение)
site:example.com -www                # Исключить www
site:.gov                            # Государственные сайты
site:.edu                            # Образовательные сайты
```

<a id="inurl-operator"></a>
### Оператор inurl

Поиск в URL:

```
inurl:admin                          # admin в URL
inurl:login                          # Страницы входа
inurl:dashboard                      # Информационные панели
inurl:config                         # Файлы конфигурации
inurl:backup                         # Резервные копии
```

<a id="intitle-operator"></a>
### Оператор intitle

Поиск в заголовке страницы:

```
intitle:"index of"                   # Списки содержимого каталогов
intitle:admin                        # Страницы администратора
intitle:login                        # Страницы входа
intitle:"dashboard"                  # Информационные панели
intitle:"control panel"              # Панели управления
```

<a id="intext-operator"></a>
### Оператор intext

Поиск в содержимом страницы:

```
intext:"password"                    # Слово password в тексте
intext:"username"                    # Слово username в тексте
intext:"api_key"                     # Ключи API
intext:"secret"                      # Секреты
```

<a id="filetype-operator"></a>
### Оператор filetype

Поиск определённых типов файлов:

```
filetype:pdf                         # Файлы PDF
filetype:doc                         # Документы Word
filetype:xls                         # Файлы Excel
filetype:sql                         # SQL-файлы
filetype:log                         # Файлы журналов
filetype:bak                         # Резервные копии
filetype:env                         # Файлы переменных окружения
```

---

<a id="-advanced-operators"></a>
## ⚡ Расширенные операторы

<a id="all-variants"></a>
### Варианты с all

```
allinurl:admin login                 # Все слова в URL
allintitle:admin panel               # Все слова в заголовке
allintext:password username          # Все слова в тексте
```

<a id="cache--link"></a>
### Кеш и ссылки

> **Уточнение.** В [истории документации Google](https://developers.google.com/search/updates) указано прекращение работы cache: в сентябре 2024 года и related: в июле 2023 года. Соответствующие примеры и совет ниже оставлены как исторические. Остальные старые варианты, включая link: и daterange:, сверяйте с актуальной справкой: поддержка операторов и сочетаний может различаться.


```
cache:example.com                    # Кешированная версия (устарело; см. уточнение)
link:example.com                     # Страницы со ссылками на указанный сайт
related:example.com                  # Похожие сайты (устарело; см. уточнение)
```

<a id="date-range"></a>
### Диапазон дат

> **Уточнение.** В [текущей справке Google](https://support.google.com/websearch/answer/2466433) before: и after: описаны как фильтры даты последнего обновления документа; это не гарантированная дата первой публикации.


```
after:2023-01-01                     # После указанной даты
before:2024-01-01                    # До указанной даты
daterange:2458850-2459215            # Диапазон юлианских дат
```

<a id="wildcards--or"></a>
### Подстановочные знаки и OR

```
admin * panel                        # Подстановочный знак
admin OR login                       # Любой из терминов
"admin panel"                        # Точная фраза
-inurl:https                         # Исключить HTTPS (упрощение оригинала)
```

<a id="number-range"></a>
### Числовой диапазон

```
inurl:id= 1..1000                    # Числовой диапазон
price $100..$500                     # Диапазон цен
```

---

<a id="-combining-operators"></a>
## 🔗 Сочетание операторов

<a id="basic-combinations"></a>
### Базовые сочетания

```
site:example.com filetype:pdf
site:example.com inurl:admin
site:example.com intitle:login
site:example.com intext:password
```

<a id="advanced-combinations"></a>
### Расширенные сочетания

```
site:example.com filetype:sql intext:password
site:example.com inurl:admin intitle:login
site:example.com filetype:log intext:error
site:example.com ext:php inurl:config
```

<a id="bug-bounty-combinations"></a>
### Сочетания для Bug Bounty

```
site:target.com (filetype:pdf | filetype:doc | filetype:xls)
site:target.com (inurl:admin | inurl:login | inurl:dashboard)
site:target.com (ext:sql | ext:bak | ext:log)
```

---

<a id="-file-discovery"></a>
## 📁 Поиск файлов

<a id="sensitive-documents"></a>
### Конфиденциальные документы

```
# Офисные документы
site:target.com filetype:doc
site:target.com filetype:docx
site:target.com filetype:xls
site:target.com filetype:xlsx
site:target.com filetype:ppt
site:target.com filetype:pdf

# Текстовые файлы
site:target.com filetype:txt
site:target.com filetype:rtf
site:target.com filetype:csv
```

<a id="backup-files"></a>
### Резервные копии

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
### Файлы баз данных

```
site:target.com filetype:sql
site:target.com filetype:db
site:target.com filetype:mdb
site:target.com filetype:sqlite
site:target.com filetype:dump
site:target.com ext:sql intext:INSERT
```

<a id="log-files"></a>
### Файлы журналов

```
site:target.com filetype:log
site:target.com ext:log
site:target.com inurl:log
site:target.com intext:"error" filetype:log
site:target.com intext:"exception" filetype:log
site:target.com intext:"fatal" filetype:log
```

<a id="configuration-files"></a>
### Файлы конфигурации

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
### Исходный код

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
## 📂 Просмотр содержимого каталогов

<a id="index-of"></a>
### Списки Index Of

```
intitle:"index of" site:target.com
intitle:"index of /" site:target.com
intitle:"directory listing" site:target.com
intitle:"parent directory" site:target.com
```

<a id="specific-directories"></a>
### Определённые каталоги

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
### Типы файлов в каталогах

```
intitle:"index of" "*.sql" site:target.com
intitle:"index of" "*.bak" site:target.com
intitle:"index of" "*.log" site:target.com
intitle:"index of" "*.zip" site:target.com
intitle:"index of" "*.tar.gz" site:target.com
```

---

<a id="-admin-panels"></a>
## 🔐 Панели администрирования

<a id="generic-admin"></a>
### Типовые панели администратора

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
### cPanel и хостинг

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
### Администрирование баз данных

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
### Панели управления

```
site:target.com intitle:"dashboard"
site:target.com intitle:"control panel"
site:target.com inurl:portal
site:target.com inurl:cms
site:target.com inurl:backend
```

---

<a id="-database--sql"></a>
## 💾 Базы данных и SQL

<a id="sql-files"></a>
### SQL-файлы

```
site:target.com filetype:sql
site:target.com filetype:sql intext:password
site:target.com filetype:sql intext:"INSERT INTO"
site:target.com filetype:sql intext:"CREATE TABLE"
site:target.com ext:sql "INSERT INTO `users`"
```

<a id="sql-errors"></a>
### Ошибки SQL

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
### Признаки возможной SQL-инъекции

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
### Дампы баз данных

```
site:target.com "dump" filetype:sql
site:target.com "database dump" filetype:sql
site:target.com filetype:sql "-- MySQL dump"
site:target.com filetype:sql "-- phpMyAdmin SQL Dump"
```

---

<a id="-configuration-files"></a>
## ⚙️ Файлы конфигурации

<a id="web-server-config"></a>
### Конфигурация веб-сервера

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
### Конфигурация приложения

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
### Конфигурация Git

```
site:target.com ".git/config"
site:target.com intitle:"index of" ".git"
site:target.com inurl:".git"
site:target.com filetype:git
```

<a id="aws--cloud"></a>
### AWS и облачные сервисы

```
site:target.com filetype:pem
site:target.com filetype:ppk
site:target.com "aws_access_key_id"
site:target.com "aws_secret_access_key"
site:target.com "AKIA"                    # Префикс ключа AWS
site:target.com "credentials" filetype:json
```

---

<a id="-credentials--secrets"></a>
## 🔑 Учётные данные и секреты

<a id="passwords"></a>
### Пароли

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
### Сочетания имён пользователей и паролей

```
site:target.com intext:username intext:password
site:target.com "username" "password" filetype:txt
site:target.com "login" "password" filetype:xls
site:target.com intext:"username:" intext:"password:"
```

<a id="api-keys--tokens"></a>
### Ключи API и токены

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
### Адреса электронной почты

```
site:target.com "@target.com"
site:target.com filetype:xls "@target.com"
site:target.com filetype:csv "email"
site:target.com intext:"@gmail.com"
```

<a id="private-keys"></a>
### Закрытые ключи

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
## ⚠️ Потенциально уязвимые серверы

<a id="open-directories"></a>
### Открытые каталоги

```
intitle:"index of" site:target.com
intitle:"directory listing" site:target.com
intitle:"Apache" "server at" site:target.com
```

<a id="default-pages"></a>
### Страницы по умолчанию

```
site:target.com intitle:"Apache2 Ubuntu Default Page"
site:target.com intitle:"Welcome to nginx"
site:target.com intitle:"Test Page for Apache"
site:target.com intitle:"IIS Windows Server"
site:target.com intitle:"Welcome to CentOS"
```

<a id="error-pages"></a>
### Страницы с ошибками

```
site:target.com "404 Not Found" inurl:admin
site:target.com "500 Internal Server Error"
site:target.com "Error establishing database connection"
site:target.com "Parse error" "syntax error"
site:target.com "Warning:" "mysql_"
site:target.com "Fatal error:" "php"
```

<a id="exposed-services"></a>
### Доступные извне сервисы

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
## 🐛 Дорки для Bug Bounty

<a id="quick-wins"></a>
### Быстрые находки

```
# Конфиденциальные файлы
site:target.com (filetype:sql | filetype:bak | filetype:log | filetype:env)

# Панели администрирования
site:target.com (inurl:admin | inurl:login | inurl:dashboard)

# Файлы конфигурации
site:target.com (filetype:conf | filetype:config | filetype:ini | filetype:env)

# Учётные данные
site:target.com (intext:password | intext:api_key | intext:secret)
```

<a id="subdomain-discovery"></a>
### Поиск поддоменов

```
site:*.target.com -www
site:*.target.com -www -mail
site:*.*.target.com
```

<a id="api-endpoints"></a>
### Конечные точки API

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
### Среды разработки и подготовки к выпуску

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
### Страницы аутентификации

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
### Загрузка файлов

```
site:target.com inurl:upload
site:target.com intitle:upload
site:target.com inurl:file
site:target.com "choose file"
site:target.com "upload file"
```

---

<a id="-cms-specific"></a>
## 📱 Поиск для конкретных CMS

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
## 📊 Краткий справочник

<a id="essential-operators"></a>
### Основные операторы

| Оператор | Назначение | Пример |
|----------|----------|---------|
| `site:` | Поиск по домену | `site:target.com` |
| `inurl:` | Поиск в URL | `inurl:admin` |
| `intitle:` | Поиск в заголовке | `intitle:login` |
| `intext:` | Поиск в содержимом | `intext:password` |
| `filetype:` | Расширение файла | `filetype:sql` |
| `ext:` | Расширение файла | `ext:pdf` |
| `cache:` | Кешированная версия | `cache:target.com` |
| `"..."` | Точная фраза | `"admin panel"` |
| `-` | Исключение | `-site:www` |
| `OR` / `|` | Любой из терминов | `admin OR login` |
| `*` | Подстановочный знак | `admin * panel` |

<a id="top-10-dorks-for-bug-bounty"></a>
### 10 дорков для Bug Bounty

| # | Дорк | Назначение |
|---|------|---------|
| 1 | `site:target.com filetype:sql` | Файлы баз данных |
| 2 | `site:target.com inurl:admin` | Панели администрирования |
| 3 | `site:target.com filetype:log` | Файлы журналов |
| 4 | `site:target.com filetype:env` | Файлы переменных окружения |
| 5 | `site:target.com intext:password filetype:txt` | Пароли |
| 6 | `intitle:"index of" site:target.com` | Просмотр содержимого каталога |
| 7 | `site:target.com inurl:api` | Конечные точки API |
| 8 | `site:target.com filetype:bak` | Резервные копии |
| 9 | `site:*.target.com -www` | Поддомены |
| 10 | `site:target.com "api_key"` | Ключи API |

---

<a id="-tools--automation"></a>
## 🛠️ Инструменты и автоматизация

<a id="google-dorking-tools"></a>
### Инструменты Google Dorking

| Инструмент | Описание | URL |
|------|-------------|-----|
| **Google Hacking Database** | Коллекция дорков Exploit-DB | [GHDB](https://www.exploit-db.com/google-hacking-database) |
| **DorkSearch** | Генератор дорков | [DorkSearch](https://dorksearch.com) |
| **Pagodo** | Пассивный поиск с дорками Google | GitHub |
| **GooDork** | Инструмент на Python для работы с дорками | GitHub |
| **GooFuzz** | Фаззинг с помощью Google | GitHub |

<a id="dorking-websites"></a>
### Сайты с дорками

```
# Google Hacking Database
https://www.exploit-db.com/google-hacking-database

# Pentest-Tools
https://pentest-tools.com/information-gathering/google-hacking

# DorkSearch
https://dorksearch.com
```

<a id="automation-script-example"></a>
### Пример скрипта автоматизации

> **Уточнение к скрипту.** Он только печатает дорки и URL, не отправляя поисковые запросы. Замена пробелов на + не выполняет полное URL-кодирование произвольной строки; пример сохранён дословно.


```bash
#!/bin/bash
# Простая автоматизация Google Dorking

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
## 💡 Советы и рекомендации

<a id="bug-bounty-tips"></a>
### Советы для Bug Bounty

1. **Начинайте с общего, затем уточняйте**
   ```
   site:target.com                    # Сначала
   site:target.com filetype:sql       # Затем уточнение
   ```

2. **Используйте несколько поисковых систем**
   - Google, Bing, DuckDuckGo, Yandex
   
3. **Проверяйте кешированные страницы**
   ```
   cache:target.com/admin
   ```

4. **Ищите поддомены**
   ```
   site:*.target.com -www
   ```

5. **Ограничивайте частоту поисковых запросов**
   - Google может блокировать чрезмерное число запросов

<a id="avoid-detection"></a>
### Снижение заметности

```
# Используйте разные IP-адреса/VPN
# Добавляйте задержки между поисковыми запросами
# Меняйте шаблоны поисковых запросов
# Используйте разные браузеры
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовой дисклеймер

> **ПРЕДУПРЕЖДЕНИЕ:** сам Google Dorking в оригинале назван законным, однако **доступ** к открытым данным без разрешения может быть незаконным. Правовая оценка этой общей формулировки зависит от обстоятельств и применимых правил.
> 
> - ✅ Используйте для разрешённого тестирования безопасности
> - ✅ Используйте в рамках программ Bug Bounty
> - ✅ Ответственно сообщайте об уязвимостях
> - ❌ Не получайте доступ к данным без разрешения
> - ❌ Не скачивайте и не храните конфиденциальные данные
> - ❌ Не эксплуатируйте найденные уязвимости
> 
> **Всегда получайте необходимое разрешение до начала тестирования!**

---

<a id="-resources"></a>
## 📚 Ресурсы

<a id="official-resources"></a>
### Официальные ресурсы
- [Операторы поиска Google](https://support.google.com/websearch/answer/2466433)
- [Google Hacking Database](https://www.exploit-db.com/google-hacking-database)

<a id="books--guides"></a>
### Книги и руководства
- «Google Hacking for Penetration Testers», автор Johnny Long
- Руководство OWASP по тестированию

<a id="related-cheatsheets"></a>
### Связанные справочники
- [Shodan](../../Shodan/translations/README.ru.md)
- [Поиск с дорками GitHub](../../GitHub-Dorking/translations/README.ru.md)
- [Subfinder](../../Subfinder/translations/README.ru.md)

---

<p align="center">
  <b>🔍 Находите то, что скрыто от обычного поиска!</b><br>
  <i>Освойте приёмы Google Dorking</i>
</p>
