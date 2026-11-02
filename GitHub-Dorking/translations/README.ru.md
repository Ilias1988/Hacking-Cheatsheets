<a id="-github-dorking---complete-cheatsheet"></a>
# 🐙 GitHub Dorking — полный справочник

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

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
  <img src="https://img.shields.io/badge/Secret-Hunting-orange?style=for-the-badge" alt="Secret Hunting">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
</p>

<p align="center">
  <b>🔐 Поиск опубликованных секретов, ключей API и учётных данных в репозиториях GitHub</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Синтаксис поиска GitHub](#-github-search-syntax)
- [Ключи API и токены](#-api-keys--tokens)
- [Учётные данные баз данных](#-database-credentials)
- [Закрытые ключи](#-private-keys)
- [Файлы конфигурации](#-configuration-files)
- [Облачные секреты](#-cloud-secrets)
- [Пароли и учётные данные](#-passwords--credentials)
- [Цели для Bug Bounty](#-bug-bounty-targets)
- [Расширенные приёмы](#-advanced-techniques)
- [Инструменты автоматизации](#-automation-tools)
- [Краткий справочник](#-quick-reference)
- [Ресурсы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

**GitHub Dorking** — поиск опубликованных секретов, учётных данных и конфиденциальной информации в открытых репозиториях с помощью расширенного поиска GitHub.

<a id="what-can-you-find"></a>
### Что можно найти?

| Категория | Примеры |
| ---------- | ---------- |
| **Ключи API** | AWS, Google, Stripe, Twilio |
| **Токены** | OAuth, JWT, персональные токены доступа (Personal Access Tokens) |
| **Учётные данные** | Пароли баз данных, SMTP, SSH |
| **Закрытые ключи** | RSA, SSH, файлы PEM |
| **Файлы конфигурации** | .env, config.yml, settings.py |

<a id="why-this-matters"></a>
### Почему это важно

- Разработчики нередко случайно добавляют секреты в коммиты
- Удалённые из файлов секреты могут сохраняться в истории Git
- Открытые репозитории индексируются и доступны для поиска
- Это частый источник находок в программах Bug Bounty

---

<a id="-github-search-syntax"></a>
## 🔧 Синтаксис поиска GitHub

> **Уточнение к синтаксису.** Оригинал сочетает [старый синтаксис REST Code Search](https://docs.github.com/en/search-github/searching-on-github/searching-code) с [современным поиском кода](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax). Параметры in:file, in:path, filename:, extension: и size: нельзя автоматически переносить между интерфейсами. В современном поиске используются, в частности, content: и path:, исключение задаётся NOT, а регулярное выражение — между /.../. Шаблон в кавычках обычно ищется как строка, а не как regex. pushed: относится к поиску репозиториев, а не кода. Все запросы оригинала сохранены дословно.


<a id="basic-search-url"></a>
### Базовый URL поиска

```
https://github.com/search?q=YOUR_QUERY&type=code
```

<a id="search-qualifiers"></a>
### Уточняющие параметры поиска (qualifiers)

| Параметр | Описание | Пример |
| ----------- | ------------- | --------- |
| `in:file` | Поиск в содержимом файла | `password in:file` |
| `in:path` | Поиск в пути файла | `config in:path` |
| `filename:` | Поиск по имени файла | `filename:.env` |
| `extension:` | Поиск по расширению | `extension:json` |
| `language:` | Поиск по языку | `language:python` |
| `user:` | Поиск в репозиториях пользователя | `user:target` |
| `org:` | Поиск в репозиториях организации | `org:company` |
| `repo:` | Поиск в указанном репозитории | `repo:user/repo` |
| `path:` | Поиск по пути | `path:config/` |
| `size:` | Размер файла | `size:>1000` |

<a id="boolean-operators"></a>
### Логические операторы

```
# AND (пробел)
password database

# OR
password OR secret

# NOT (-)
password -test -example

# Точная фраза
"api_key"

# Объединение условий
org:target "api_key" NOT test
```

---

<a id="-api-keys--tokens"></a>
## 🔑 Ключи API и токены

> **Уточнение.** Совпадение с шаблоном не подтверждает, что найден действующий секрет. Например, [Stripe pk_ — публикуемый ключ](https://docs.stripe.com/keys), предназначенный для клиентского кода; сам по себе он не доказывает утечку секретного ключа. Файл сертификата также не равнозначен закрытому ключу. Префиксы и regex в оригинале — неполный набор примеров.


<a id="aws-keys"></a>
### Ключи AWS

```
# Идентификатор ключа доступа AWS
AKIA
AKID
"aws_access_key_id"
"AWS_ACCESS_KEY_ID"

# Секретный ключ AWS
"aws_secret_access_key"
"AWS_SECRET_ACCESS_KEY"

# Комбинированные запросы
filename:.env AWS_ACCESS_KEY
org:target AKIA
org:target "aws_secret"
```

<a id="google-cloud"></a>
### Google Cloud

```
# Ключ API GCP
"AIza"
"AIzaSy"

# Сервисная учётная запись GCP
"type": "service_account"
filename:credentials.json "private_key"
"client_secret" extension:json

# Firebase
"apiKey" "authDomain" "databaseURL"
filename:firebase
```

<a id="stripe"></a>
### Stripe

```
# Ключи Stripe
"sk_live_"
"rk_live_"
"pk_live_"
sk_live
rk_live

# Комбинированные запросы
org:target sk_live
filename:.env STRIPE_SECRET
```

<a id="twilio"></a>
### Twilio

```
# SID и токен Twilio
"TWILIO_ACCOUNT_SID"
"TWILIO_AUTH_TOKEN"
"twilio" "sid" "token"
```

<a id="github-tokens"></a>
### Токены GitHub

```
# Персональные токены доступа
ghp_
gho_
ghu_
ghs_
ghr_

# Шаблоны токенов GitHub
"GITHUB_TOKEN"
"github_token"
"gh_token"
filename:.env GITHUB_TOKEN
```

<a id="slack"></a>
### Slack

```
# Токены Slack
"xoxb-"
"xoxp-"
"xoxs-"
"xoxa-"

# Вебхук Slack
"hooks.slack.com"
"T[A-Z0-9]{8}/B[A-Z0-9]{8}/[a-zA-Z0-9]{24}"
```

<a id="other-api-keys"></a>
### Другие ключи API

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
## 💾 Учётные данные баз данных

<a id="mysql"></a>
### MySQL

```
# Строки подключения MySQL
"mysql://"
"mysql_password"
"MYSQL_PASSWORD"
"DB_PASSWORD"

# Комбинированные запросы
filename:.env MYSQL_PASSWORD
filename:config.php "mysql" "password"
"mysqli_connect" password
```

<a id="postgresql"></a>
### PostgreSQL

```
# PostgreSQL
"postgres://"
"postgresql://"
"POSTGRES_PASSWORD"
"PG_PASSWORD"
filename:.env POSTGRES
```

<a id="mongodb"></a>
### MongoDB

```
# Подключение MongoDB
"mongodb://"
"mongodb+srv://"
"MONGO_URI"
"MONGODB_URI"

# С учётными данными
"mongodb://.*:.*@"
filename:.env MONGO
```

<a id="redis"></a>
### Redis

```
# Redis
"redis://"
"REDIS_PASSWORD"
"REDIS_URL"
```

<a id="sqlite"></a>
### SQLite

```
# SQLite
filename:*.sqlite
filename:*.db
extension:sqlite
```

<a id="generic-database"></a>
### Другие базы данных

```
# Строки подключения
"connection_string"
"connectionString"
"DB_CONNECTION"
"DATABASE_URL"

# Учётные данные
"db_password"
"db_pass"
"database_password"
```

---

<a id="-private-keys"></a>
## 🔐 Закрытые ключи

<a id="ssh-keys"></a>
### Ключи SSH

```
# Закрытые ключи SSH
"-----BEGIN RSA PRIVATE KEY-----"
"-----BEGIN DSA PRIVATE KEY-----"
"-----BEGIN EC PRIVATE KEY-----"
"-----BEGIN OPENSSH PRIVATE KEY-----"
"-----BEGIN PRIVATE KEY-----"

# Файлы
filename:id_rsa
filename:id_dsa
filename:id_ed25519
extension:pem
extension:key

# Комбинированные запросы
filename:id_rsa -example -sample
```

<a id="pgpgpg-keys"></a>
### Ключи PGP/GPG

```
"-----BEGIN PGP PRIVATE KEY BLOCK-----"
filename:*.asc "PRIVATE KEY"
```

<a id="ssltls-certificates"></a>
### Сертификаты SSL/TLS

```
"-----BEGIN CERTIFICATE-----"
filename:*.crt
filename:*.cer
extension:pem "CERTIFICATE"
```

---

<a id="-configuration-files"></a>
## ⚙️ Файлы конфигурации

<a id="environment-files"></a>
### Файлы окружения

```
# Файлы .env
filename:.env
filename:.env.local
filename:.env.production
filename:.env.development
filename:.env.staging
filename:.env.backup

# С секретами
filename:.env DB_PASSWORD
filename:.env API_KEY
filename:.env SECRET
```

<a id="config-files"></a>
### Файлы конфигурации

```
# Различные файлы конфигурации
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

<a id="docker"></a>
### Docker

```
# Настройки Docker
filename:docker-compose.yml password
filename:Dockerfile ENV
filename:.dockercfg
filename:docker-compose.yml
```

<a id="kubernetes"></a>
### Kubernetes

```
# Секреты K8s
filename:*.yaml "kind: Secret"
filename:secrets.yaml
"kubernetes" "secret"
```

<a id="cicd"></a>
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
## ☁️ Облачные секреты

<a id="aws"></a>
### AWS

```
# Файл учётных данных AWS
filename:credentials aws_access_key_id
filename:.aws/credentials

# Конфигурация AWS
"aws_access_key_id"
"aws_secret_access_key"
"aws_session_token"

# Бакеты S3
"s3.amazonaws.com"
"s3-"
".s3.amazonaws.com"
```

<a id="azure"></a>
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

<a id="google-cloud-1"></a>
### Google Cloud

```
# GCP
"GOOGLE_APPLICATION_CREDENTIALS"
"GOOGLE_API_KEY"
"GOOGLE_CLOUD_PROJECT"
filename:credentials.json "private_key"
```

<a id="digitalocean"></a>
### DigitalOcean

```
"DIGITALOCEAN_ACCESS_TOKEN"
"DO_AUTH_TOKEN"
"digitalocean" "token"
```

<a id="heroku"></a>
### Heroku

```
"HEROKU_API_KEY"
"HEROKU_APP_NAME"
filename:.netrc heroku
```

---

<a id="-passwords--credentials"></a>
## 🔓 Пароли и учётные данные

<a id="hardcoded-passwords"></a>
### Пароли в исходном коде

```
# Переменные с паролями
password=
passwd=
pass=
pwd=
"password:"
"password ="

# Со значениями
password="
password='
"password": "
'password': '
```

<a id="smtp-credentials"></a>
### Учётные данные SMTP

```
# SMTP
"smtp_password"
"SMTP_PASSWORD"
"mail_password"
"EMAIL_PASSWORD"
"smtp" "password"
```

<a id="oauth-secrets"></a>
### Секреты OAuth

```
# OAuth
"client_secret"
"CLIENT_SECRET"
"oauth_token"
"OAUTH_SECRET"
"refresh_token"
```

<a id="jwt-secrets"></a>
### Секреты JWT

```
# JWT
"JWT_SECRET"
"jwt_secret"
"JWT_KEY"
"secret_key"
"SECRET_KEY"
```

<a id="admin-credentials"></a>
### Учётные данные администратора

```
# Администратор
"admin_password"
"root_password"
"master_password"
"default_password"
```

---

<a id="-bug-bounty-targets"></a>
## 🎯 Цели для Bug Bounty

<a id="target-organization"></a>
### Целевая организация

```
# Поиск по указанной организации
org:target-company

# Репозитории пользователя
user:target-username

# Указанный репозиторий
repo:target/repository

# Совместно с поиском секретов
org:target-company password
org:target-company api_key
org:target-company "secret"
```

<a id="domain-based-search"></a>
### Поиск по домену

```
# Целевой домен
"target.com"
"@target.com"
"api.target.com"
"internal.target.com"

# Комбинированные запросы
"target.com" password
"target.com" api_key
org:target "@target.com"
```

<a id="employee-emails"></a>
### Адреса сотрудников

```
# Поиск репозиториев сотрудников
"@target.com"

# Адреса разработчиков
"@target.com" password
"@target.com" config
```

<a id="internal-urls"></a>
### Внутренние URL

```
# Внутренние сервисы
"internal.target.com"
"dev.target.com"
"staging.target.com"
"admin.target.com"
"vpn.target.com"
"jira.target.com"
"confluence.target.com"
```

<a id="bug-bounty-dork-template"></a>
### Шаблон поисковых запросов для Bug Bounty

```
# Полный набор запросов для разведки
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
## 🔬 Расширенные приёмы

<a id="git-history-search"></a>
### Поиск в истории Git

> **Уточнение.** [Современный поиск кода GitHub](https://docs.github.com/en/search-github/github-code-search/about-github-code-search) индексирует ветку по умолчанию с ограничениями, а не всю историю Git. Запрос pushed: ниже не ищет удалённые секреты в старых коммитах. Историю проверяют отдельно подходящим инструментом.


```
# Проверка коммитов
# (Используйте инструменты TruffleHog и git-secrets)

# Недавно обновлённые репозитории
pushed:>2024-01-01 password
pushed:>2024-01-01 filename:.env
```

<a id="gist-search"></a>
### Поиск в Gist

```
# Поиск Gist
https://gist.github.com/search?q=password+target

# Шаблоны запросов для Gist
gist password
gist api_key
```

<a id="code-snippets"></a>
### Фрагменты кода

```
# Поиск в коде
in:file password
in:path config

# Большие файлы могут содержать больше данных
size:>10000 password
```

<a id="regex-patterns-with-tools"></a>
### Регулярные выражения (для инструментов)

```
# Шаблон ключа AWS
AKIA[0-9A-Z]{16}

# Токен GitHub
ghp_[a-zA-Z0-9]{36}

# Токен Slack
xox[baprs]-[0-9]{12}-[0-9]{12}-[a-zA-Z0-9]{24}

# Закрытый ключ
-----BEGIN [A-Z]+ PRIVATE KEY-----
```

---

<a id="-automation-tools"></a>
## 🛠️ Инструменты автоматизации

> **О версиях инструментов.** [TruffleHog v3](https://github.com/trufflesecurity/trufflehog) переписан на Go: команда pip из оригинала относится к прежней реализации; git-источник ожидает репозиторий, а для организации предусмотрен github-режим. Проверка валидности секретов может обращаться к API сервисов. В [Gitleaks](https://github.com/gitleaks/gitleaks) detect устарел начиная с v8.19.0; актуальные режимы — git, dir и stdin. Путь источника Gitleaks не заменяется URL удалённого репозитория. Команды оригинала оставлены без изменений; сверяйте версию инструмента.


<a id="trufflehog"></a>
### TruffleHog

```bash
# Установка
pip install truffleHog

# Проверка репозитория
trufflehog git https://github.com/target/repo

# Проверка организации
trufflehog git https://github.com/target --only-verified
```

<a id="gitleaks"></a>
### GitLeaks

```bash
# Установка
brew install gitleaks

# Проверка репозитория
gitleaks detect -s /path/to/repo

# Проверка по URL (пример оригинала; см. уточнение)
gitleaks detect --source https://github.com/target/repo

# Вывод JSON
gitleaks detect -s repo --report-format json -r results.json
```

<a id="git-secrets"></a>
### git-secrets

```bash
# Установка
brew install git-secrets

# Добавление шаблонов
git secrets --add 'AKIA[A-Z0-9]{16}'
git secrets --add 'password\s*=\s*["\'][^"\']*["\']'

# Проверка репозитория
git secrets --scan
```

<a id="github-search-tools"></a>
### Инструменты поиска GitHub

```bash
# CLI для GitHub Dorking
# Доступны различные инструменты сообщества

# GitDorker
python3 gitdorker.py -t TOKEN -org target

# gitrob
gitrob target-org
```

<a id="shhgit"></a>
### shhgit

```bash
# Мониторинг GitHub в реальном времени
# Поиск секретов в реальном времени
shhgit --search-query "org:target"
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-dorks"></a>
### Основные поисковые запросы

| Назначение | Поисковый запрос (dork) |
| --------- | ------ |
| Ключи AWS | `org:target AKIA` |
| Пароли | `org:target password filename:.env` |
| Ключи API | `org:target api_key` |
| Закрытые ключи | `org:target "BEGIN RSA PRIVATE KEY"` |
| Файлы конфигурации | `org:target filename:config.json` |
| Учётные данные БД | `org:target "mongodb://"` |
| Токены | `org:target token secret` |
| Адреса электронной почты | `"@target.com" password` |

<a id="file-patterns"></a>
### Шаблоны имён файлов

| Шаблон | Поисковый запрос (dork) |
| --------- | ------ |
| `.env` | `filename:.env` |
| Конфигурация JSON | `filename:config.json` |
| Настройки | `filename:settings.py` |
| Учётные данные | `filename:credentials` |
| Секреты | `filename:secrets` |
| Docker | `filename:docker-compose.yml` |

<a id="top-15-github-dorks"></a>
### 15 основных поисковых запросов GitHub

| # | Поисковый запрос (dork) | Назначение |
| --- | ------ | --------- |
| 1 | `filename:.env DB_PASSWORD` | Пароли БД |
| 2 | `filename:.env AWS_` | Учётные данные AWS |
| 3 | `"-----BEGIN RSA PRIVATE KEY-----"` | Закрытые ключи |
| 4 | `filename:config.json password` | Пароли в конфигурации |
| 5 | `AKIA` | Ключи доступа AWS |
| 6 | `sk_live` | Ключи Stripe |
| 7 | `"mongodb+srv://"` | URI MongoDB |
| 8 | `filename:credentials aws` | Файл учётных данных AWS |
| 9 | `ghp_` | Токены GitHub |
| 10 | `xoxb-` | Токены Slack |
| 11 | `org:target password` | Пароли организации |
| 12 | `filename:.npmrc _auth` | Токены NPM |
| 13 | `extension:sql password` | Дампы SQL |
| 14 | `filename:wp-config.php` | WordPress |
| 15 | `"api_key" extension:json` | Ключи API |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендации

<a id="bug-bounty-tips"></a>
### Советы для Bug Bounty

1. **Ищите по организации**
   ```
   org:target-company
   ```

2. **Проверяйте репозитории сотрудников**
   ```
   "@target.com"
   ```

3. **Ищите внутренние URL**
   ```
   org:target "internal" OR "dev" OR "staging"
   ```

4. **Проверяйте историю Git**
   - Используйте TruffleHog или GitLeaks

5. **Проверяйте Gist**
   - Разработчики часто сохраняют там фрагменты кода

<a id="responsible-disclosure"></a>
### Ответственное раскрытие информации

- **Сообщайте** о находках команде безопасности
- **Не входите** в системы с найденными учётными данными
- **Не публикуйте** учётные данные
- **Документируйте** находки надлежащим образом

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовое предупреждение

> **ПРЕДУПРЕЖДЕНИЕ:** GitHub Dorking позволяет находить общедоступную информацию. При этом:
> 
> - ✅ Ищите в открытых репозиториях
> - ✅ Сообщайте о находках через программы Bug Bounty
> - ✅ Соблюдайте принципы ответственного раскрытия
> - ❌ Не используйте найденные учётные данные
> - ❌ Не входите в системы
> - ❌ Не сохраняйте и не передавайте секреты
> 
> **Использовать найденные учётные данные незаконно!**

---

<a id="-resources"></a>
## 📚 Ресурсы

<a id="tools"></a>
### Инструменты
- [TruffleHog](https://github.com/trufflesecurity/trufflehog)
- [GitLeaks](https://github.com/gitleaks/gitleaks)
- [git-secrets](https://github.com/awslabs/git-secrets)
- [shhgit](https://github.com/eth0izzle/shhgit)

<a id="references"></a>
### Справочные материалы
- [Документация по поиску GitHub](https://docs.github.com/en/search-github)
- [Обнаружение секретов](https://docs.github.com/en/code-security/secret-scanning)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [Google Dorking](../../Google-Dorking/translations/README.ru.md)
- [Shodan](../../Shodan/translations/README.ru.md)
- [Subfinder](../../Subfinder/translations/README.ru.md)

---

<p align="center">
  <b>🐙 Находите секреты раньше злоумышленников!</b><br>
  <i>Освойте GitHub Dorking для Bug Bounty</i>
</p>
