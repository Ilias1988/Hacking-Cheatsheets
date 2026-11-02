[🇬🇧 English](../../README.md)

# 🐙 GitHub Dorking - Kapsamlı Başvuru Kılavuzu

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
  <b>🔐 GitHub Depolarında Unutulmuş Gizli Bilgileri, API Anahtarlarını ve Parolaları Bulma</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [GitHub Arama Sözdizimi](#-github-arama-sözdizimi)
- [API Anahtarları ve Belirteçler (Tokens)](#-api-anahtarları-ve-belirteçler-tokens)
- [Veritabanı Kimlik Bilgileri](#-veritabanı-kimlik-bilgileri)
- [Özel Anahtarlar (Private Keys)](#-özel-anahtarlar-private-keys)
- [Yapılandırma Dosyaları](#-yapılandırma-dosyaları)
- [Bulut (Cloud) Gizli Anahtarları](#-bulut-cloud-gizli-anahtarları)
- [Parolalar ve Kimlik Bilgileri](#-parolalar-ve-kimlik-bilgileri)
- [Bug Bounty Hedefleri](#-bug-bounty-hedefleri)
- [İleri Düzey Teknikler](#-i̇leri-düzey-teknikler)
- [Otomasyon Araçları](#-otomasyon-araçları)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**GitHub Dorking**, GitHub'ın gelişmiş arama parametrelerini ve filtrelerini kullanarak genele açık depolarda (public repositories) unutulmuş gizli anahtarları, hassas yapılandırmaları ve parolaları tespit etme tekniğidir.

### Neler Bulabilirsiniz?

| Kategori | Örnekler |
|----------|----------|
| **API Anahtarları** | AWS, Google Cloud, Stripe, Twilio |
| **Belirteçler (Tokens)** | OAuth, JWT, GitHub Kişisel Erişim Belirteçleri (PAT) |
| **Kimlik Bilgileri** | Veritabanı bağlantıları, SMTP şifreleri, SSH parolaları |
| **Özel Anahtarlar** | RSA, SSH, PEM uzantılı özel anahtarlar |
| **Yapılandırma Dosyaları** | .env, config.yml, settings.py, credentials.json |

### Bu Neden Önemlidir?

- Geliştiriciler sıklıkla yanlışlıkla hassas anahtarları repoya commit eder.
- Git commit geçmişi (history), dosyadan silinmiş anahtarları bile saklar.
- Genele açık tüm depolar GitHub tarafından indekslenir ve taranabilir.
- Bug bounty avcıları için en zengin kritik bulgu kaynaklarından biridir.

---

## 🔧 GitHub Arama Sözdizimi

### Temel Arama URL'si

```
https://github.com/search?q=YOUR_QUERY&type=code
```

### Arama Niteliği Niteleyicileri (Qualifiers)

| Niteleyici | Açıklama | Örnek |
|------------|----------|-------|
| `in:file` | Dosya içeriğinde ara | `password in:file` |
| `in:path` | Dosya yolunda ara | `config in:path` |
| `filename:` | Dosya adına göre ara | `filename:.env` |
| `extension:` | Dosya uzantısına göre ara | `extension:json` |
| `language:` | Programlama diline göre ara | `language:python` |
| `user:` | Belirli bir kullanıcının depolarında | `user:target` |
| `org:` | Belirli bir organizasyon depolarında | `org:company` |
| `repo:` | Belirli bir repoda ara | `repo:user/repo` |
| `path:` | Belirli bir dizin yolunda ara | `path:config/` |
| `size:` | Dosya boyutuna göre ara | `size:>1000` |

### Mantıksal (Boolean) Operatörler

```
# AND (Boşluk bırakarak)
password database

# OR
password OR secret

# NOT (-)
password -test -example

# Birebir Dize Eşleşmesi
"api_key"

# Kombinasyon
org:target "api_key" NOT test
```

---

## 🔑 API Anahtarları ve Belirteçler (Tokens)

### AWS Anahtarları

```
# AWS Access Key ID
AKIA
AKID
"aws_access_key_id"
"AWS_ACCESS_KEY_ID"

# AWS Secret Access Key
"aws_secret_access_key"
"AWS_SECRET_ACCESS_KEY"

# Kombine Aramalar
filename:.env AWS_ACCESS_KEY
org:target AKIA
org:target "aws_secret"
```

### Google Cloud & Firebase

```
# GCP API Key
"AIza"
"AIzaSy"

# GCP Hizmet Hesabı (Service Account)
"type": "service_account"
filename:credentials.json "private_key"
"client_secret" extension:json

# Firebase
"apiKey" "authDomain" "databaseURL"
filename:firebase
```

### Stripe

```
# Stripe Anahtarları
"sk_live_"
"rk_live_"
"pk_live_"
sk_live
rk_live

# Kombine Aramalar
org:target sk_live
filename:.env STRIPE_SECRET
```

### Twilio

```
# Twilio SID & Token
"TWILIO_ACCOUNT_SID"
"TWILIO_AUTH_TOKEN"
"twilio" "sid" "token"
```

### GitHub Belirteçleri (Tokens)

```
# Kişisel Erişim Belirteçleri (Personal Access Tokens)
ghp_
gho_
ghu_
ghs_
ghr_

# Genel Belirteç İsimleri
"GITHUB_TOKEN"
"github_token"
"gh_token"
filename:.env GITHUB_TOKEN
```

### Slack

```
# Slack Belirteçleri
"xoxb-"
"xoxp-"
"xoxs-"
"xoxa-"

# Slack Webhook URL'leri
"hooks.slack.com"
"T[A-Z0-9]{8}/B[A-Z0-9]{8}/[a-zA-Z0-9]{24}"
```

### Diğer API Anahtarları

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

## 💾 Veritabanı Kimlik Bilgileri

### MySQL

```
# MySQL Bağlantı Dizgileri
"mysql://"
"mysql_password"
"MYSQL_PASSWORD"
"DB_PASSWORD"

# Kombine Aramalar
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
# MongoDB Bağlantı URI'si
"mongodb://"
"mongodb+srv://"
"MONGO_URI"
"MONGODB_URI"

# Kullanıcı adı ve şifre içeren URI
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

### Genel Veritabanı Bağlantıları

```
# Bağlantı dizgileri
"connection_string"
"connectionString"
"DB_CONNECTION"
"DATABASE_URL"

# Kimlik bilgileri
"db_password"
"db_pass"
"database_password"
```

---

## 🔐 Özel Anahtarlar (Private Keys)

### SSH Anahtarları

```
# Özel SSH Anahtar Başlıkları
"-----BEGIN RSA PRIVATE KEY-----"
"-----BEGIN DSA PRIVATE KEY-----"
"-----BEGIN EC PRIVATE KEY-----"
"-----BEGIN OPENSSH PRIVATE KEY-----"
"-----BEGIN PRIVATE KEY-----"

# Dosya adları
filename:id_rsa
filename:id_dsa
filename:id_ed25519
extension:pem
extension:key

# Kombine arama
filename:id_rsa -example -sample
```

### PGP/GPG Anahtarları

```
"-----BEGIN PGP PRIVATE KEY BLOCK-----"
filename:*.asc "PRIVATE KEY"
```

### SSL/TLS Sertifikaları

```
"-----BEGIN CERTIFICATE-----"
filename:*.crt
filename:*.cer
extension:pem "CERTIFICATE"
```

---

## ⚙️ Yapılandırma Dosyaları

### Ortam Dosyaları (.env)

```
# .env dosyaları
filename:.env
filename:.env.local
filename:.env.production
filename:.env.development
filename:.env.staging
filename:.env.backup

# İçinde parola ve anahtar geçenler
filename:.env DB_PASSWORD
filename:.env API_KEY
filename:.env SECRET
```

### Yapılandırma Dosyaları

```
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
# Docker yapılandırmaları
filename:docker-compose.yml password
filename:Dockerfile ENV
filename:.dockercfg
filename:docker-compose.yml
```

### Kubernetes

```
# Kubernetes gizli nesneleri (secrets)
filename:*.yaml "kind: Secret"
filename:secrets.yaml
"kubernetes" "secret"
```

### CI/CD Yapılandırmaları

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

## ☁️ Bulut (Cloud) Gizli Anahtarları

### AWS

```
# AWS Kimlik Bilgisi Dosyası
filename:credentials aws_access_key_id
filename:.aws/credentials

# AWS Yapılandırma
"aws_access_key_id"
"aws_secret_access_key"
"aws_session_token"

# S3 Bucket Adresleri
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

## 🔓 Parolalar ve Kimlik Bilgileri

### Sabit Kodlanmış Parolalar

```
# Parola değişken adları
password=
passwd=
pass=
pwd=
"password:"
"password ="

# Değer atanmış değişkenler
password="
password='
"password": "
'password': '
```

### SMTP / E-posta Kimlik Bilgileri

```
"smtp_password"
"SMTP_PASSWORD"
"mail_password"
"EMAIL_PASSWORD"
"smtp" "password"
```

### OAuth ve Yetkilendirme Gizli Dizgileri

```
"client_secret"
"CLIENT_SECRET"
"oauth_token"
"OAUTH_SECRET"
"refresh_token"
```

### JWT Gizli Anahtarları

```
"JWT_SECRET"
"jwt_secret"
"JWT_KEY"
"secret_key"
"SECRET_KEY"
```

### Yönetici Kimlik Bilgileri

```
"admin_password"
"root_password"
"master_password"
"default_password"
```

---

## 🎯 Bug Bounty Hedefleri

### Hedef Organizasyon Taraması

```
# Belirli organizasyon araması
org:target-company

# Kullanıcı depoları
user:target-username

# Belirli bir depo
repo:target/repository

# Anahtar kelimelerle birleştirme
org:target-company password
org:target-company api_key
org:target-company "secret"
```

### Alan Adı Odaklı Arama

```
# Hedef alan adı
"target.com"
"@target.com"
"api.target.com"
"internal.target.com"

# Gizli anahtarlarla birleştirme
"target.com" password
"target.com" api_key
org:target "@target.com"
```

### Şirket Çalışanı E-postaları

```
# Çalışanların kişisel depolarını bulma
"@target.com"

# Geliştirici e-postaları ve parolalar
"@target.com" password
"@target.com" config
```

### Dahili (Internal) URL'ler

```
# Dahili servisler ve test sunucuları
"internal.target.com"
"dev.target.com"
"staging.target.com"
"admin.target.com"
"vpn.target.com"
"jira.target.com"
"confluence.target.com"
```

### Kapsamlı Bug Bounty Dork Şablonu

```
# Eksiksiz keşif seti
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

## 🔬 İleri Düzey Teknikler

### Git Commit Geçmişi Taraması

```
# Commit geçmişi tarama araçları:
# (TruffleHog, GitLeaks, git-secrets vb.)

# Yakın zamanda push edilmiş commit'ler
pushed:>2024-01-01 password
pushed:>2024-01-01 filename:.env
```

### Gist Taraması

```
# GitHub Gist üzerinde arama
https://gist.github.com/search?q=password+target

# Gist kalıpları
gist password
gist api_key
```

### Kod Parçaları ve Boyut Filtreleri

```
# Kod içerisinde arama
in:file password
in:path config

# Büyük dosyalar (daha fazla veri içerebilir)
size:>10000 password
```

### Düzenli İfade (Regex) Kalıpları (Araçlar İçin)

```
# AWS Anahtar Deseni
AKIA[0-9A-Z]{16}

# GitHub Belirteç Deseni
ghp_[a-zA-Z0-9]{36}

# Slack Belirteç Deseni
xox[baprs]-[0-9]{12}-[0-9]{12}-[a-zA-Z0-9]{24}

# Özel Anahtar Başlığı
-----BEGIN [A-Z]+ PRIVATE KEY-----
```

---

## 🛠️ Otomasyon Araçları

### TruffleHog

```bash
# Kurulum
pip install truffleHog

# Depoyu tarama
trufflehog git https://github.com/target/repo

# Tüm organizasyonu tarama (yalnızca doğrulanmış sızıntılar)
trufflehog git https://github.com/target --only-verified
```

### GitLeaks

```bash
# Kurulum
brew install gitleaks

# Yerel repoyu tarama
gitleaks detect -s /path/to/repo

# Uzak URL üzerinden tarama
gitleaks detect --source https://github.com/target/repo

# JSON formatında raporlama
gitleaks detect -s repo --report-format json -r results.json
```

### git-secrets

```bash
# Kurulum
brew install git-secrets

# Kalıp ekleme
git secrets --add 'AKIA[A-Z0-9]{16}'
git secrets --add 'password\s*=\s*["\'][^"\']*["\']'

# Repoyu tarama
git secrets --scan
```

### GitHub Arama ve Dorking Araçları

```bash
# GitDorker
python3 gitdorker.py -t TOKEN -org target

# gitrob
gitrob target-org
```

### shhgit

```bash
# Gerçek zamanlı GitHub sızıntı izleme
shhgit --search-query "org:target"
```

---

## 📊 Hızlı Başvuru

### Temel Dork'lar

| Amaç | Dork |
|------|------|
| AWS Anahtarları | `org:target AKIA` |
| Parolalar | `org:target password filename:.env` |
| API Anahtarları | `org:target api_key` |
| Özel Anahtarlar | `org:target "BEGIN RSA PRIVATE KEY"` |
| Yapılandırma Dosyaları | `org:target filename:config.json` |
| Veritabanı Bilgileri | `org:target "mongodb://"` |
| Belirteçler (Tokens) | `org:target token secret` |
| E-postalar | `"@target.com" password` |

### Dosya Kalıpları

| Dosya Deseni | Dork |
|--------------|------|
| `.env` | `filename:.env` |
| Config JSON | `filename:config.json` |
| Ayarlar | `filename:settings.py` |
| Kimlik Bilgileri | `filename:credentials` |
| Gizli Anahtarlar | `filename:secrets` |
| Docker | `filename:docker-compose.yml` |

### En İyi 15 GitHub Dork'u

| # | Dork | Amaç |
|---|------|------|
| 1 | `filename:.env DB_PASSWORD` | Veritabanı parolaları |
| 2 | `filename:.env AWS_` | AWS kimlik bilgileri |
| 3 | `"-----BEGIN RSA PRIVATE KEY-----"` | Özel anahtarlar |
| 4 | `filename:config.json password` | Yapılandırma şifreleri |
| 5 | `AKIA` | AWS Erişim Anahtarları |
| 6 | `sk_live` | Stripe canlı anahtarları |
| 7 | `"mongodb+srv://"` | MongoDB bağlantı adresleri |
| 8 | `filename:credentials aws` | AWS credentials dosyası |
| 9 | `ghp_` | GitHub belirteçleri |
| 10 | `xoxb-` | Slack belirteçleri |
| 11 | `org:target password` | Organizasyon şifreleri |
| 12 | `filename:.npmrc _auth` | NPM yetki belirteçleri |
| 13 | `extension:sql password` | SQL veritabanı dökümleri |
| 14 | `filename:wp-config.php` | WordPress veritabanı şifresi |
| 15 | `"api_key" extension:json` | JSON formatında API anahtarları |

---

## 💡 İpuçları ve En İyi Pratikler

### Bug Bounty İpuçları

1. **Organizasyonu Arayın**
   ```
   org:target-company
   ```

2. **Çalışanların Kişisel Depolarını İnceleyin**
   ```
   "@target.com"
   ```

3. **Dahili URL'leri Tespit Edin**
   ```
   org:target "internal" OR "dev" OR "staging"
   ```

4. **Git Commit Geçmişini Mutlaka Tarayın**
   - TruffleHog veya GitLeaks kullanın.

5. **Gist'leri İhmal Etmeyin**
   - Geliştiriciler sıklıkla geçici kod parçalarını Gist olarak paylaşır.

### Sorumlu Açıklama (Responsible Disclosure)

- Tespit ettiğiniz bulguları vakit kaybetmeden ilgili güvenlik ekibine bildirin.
- Bulduğunuz kimlik bilgileriyle **asla sistemlere giriş yapmayın**.
- Hassas anahtarları veya parolaları asla halka açık platformlarda paylaşmayın.
- Bulgularınızı tarih, repo URL'si ve dosya yoluyla birlikte eksiksiz raporlayın.

---

## ⚠️ Yasal Uyarı

> **UYARI:** GitHub Dorking herkese açık bilgileri ortaya çıkarır. Ancak:
> 
> - ✅ Genele açık depoları araştırabilirsiniz
> - ✅ Bug bounty programlarına bulguları raporlayabilirsiniz
> - ✅ Sorumlu açıklama ilkelerine uymalısınız
> - ❌ Bulunan kimlik bilgilerini **kesinlikle kullanmayın**
> - ❌ Hedef sistemlere izinsiz erişim sağlamayın
> - ❌ Hassas verileri indirmeyin veya yaymayın
> 
> **Başkasına ait kimlik bilgilerini kullanmak yasa dışıdır!**

---

## 📚 Kaynaklar

### Araçlar
- [TruffleHog](https://github.com/trufflesecurity/trufflehog)
- [GitLeaks](https://github.com/gitleaks/gitleaks)
- [git-secrets](https://github.com/awslabs/git-secrets)
- [shhgit](https://github.com/eth0izzle/shhgit)

### Referanslar
- [GitHub Arama Dokümantasyonu](https://docs.github.com/en/search-github)
- [GitHub Gizli Anahtar Tarama Desenleri](https://docs.github.com/en/code-security/secret-scanning)

### İlgili Başvuru Kılavuzları
- [Google Dorking](../../Google-Dorking/translations/README.tr.md)
- [Shodan](../../Shodan/README.md)
- [Subfinder](../../Subfinder/translations/README.tr.md)

---

<p align="center">
  <b>🐙 Saldırganlardan Önce Gizli Anahtarları Keşfedin!</b><br>
  <i>Bug bounty için GitHub Dorking uzmanlığı</i>
</p>
