# 🐙 GitHub Dorking - Guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

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
  <img src="https://img.shields.io/badge/Secret-Hunting-orange?style=for-the-badge" alt="Búsqueda de secretos">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
</p>

<p align="center">
  <b>🔐 Encuentra secretos, claves API y credenciales expuestos en repositorios de GitHub</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Introducción](#-introducción)
- [Sintaxis de búsqueda de GitHub](#-sintaxis-de-búsqueda-de-github)
- [Claves API y tokens](#-claves-api-y-tokens)
- [Credenciales de bases de datos](#-credenciales-de-bases-de-datos)
- [Claves privadas](#-claves-privadas)
- [Archivos de configuración](#-archivos-de-configuración)
- [Secretos de servicios cloud](#-secretos-de-servicios-cloud)
- [Contraseñas y credenciales](#-contraseñas-y-credenciales)
- [Objetivos de bug bounty](#-objetivos-de-bug-bounty)
- [Técnicas avanzadas](#-técnicas-avanzadas)
- [Herramientas de automatización](#-herramientas-de-automatización)
- [Referencia rápida](#-referencia-rápida)
- [Recursos](#-recursos)

---

## 🎯 Introducción

**GitHub dorking** consiste en utilizar las búsquedas avanzadas de GitHub para encontrar secretos, credenciales e información sensible expuestos en repositorios públicos.

### ¿Qué puedes encontrar?

| Categoría | Ejemplos |
|----------|----------|
| **Claves API** | AWS, Google, Stripe, Twilio |
| **Tokens** | OAuth, JWT, Tokens de acceso personal |
| **Credenciales** | Contraseñas de bases de datos, SMTP y SSH |
| **Claves privadas** | RSA, SSH y archivos PEM |
| **Archivos de configuración** | .env, config.yml, settings.py |

### Por qué es importante

- Los desarrolladores pueden incluir secretos accidentalmente en commits
- El historial de Git puede conservar secretos eliminados
- Los repositorios públicos se indexan y pueden consultarse
- Una fuente importante de hallazgos de bug bounty

---

## 🔧 Sintaxis de búsqueda de GitHub

### URL básica de búsqueda

```
https://github.com/search?q=YOUR_QUERY&type=code
```

### Calificadores de búsqueda

| Calificador | Descripción | Ejemplo |
|-----------|-------------|---------|
| `in:file` | Buscar en el contenido de archivos | `password in:file` |
| `in:path` | Buscar en la ruta de los archivos | `config in:path` |
| `filename:` | Buscar por nombre de archivo | `filename:.env` |
| `extension:` | Buscar por extensión | `extension:json` |
| `language:` | Buscar por lenguaje | `language:python` |
| `user:` | Buscar en los repositorios de un usuario | `user:target` |
| `org:` | Buscar en los repositorios de una organización | `org:company` |
| `repo:` | Buscar en un repositorio concreto | `repo:user/repo` |
| `path:` | Buscar en una ruta | `path:config/` |
| `size:` | Tamaño del archivo | `size:>1000` |

### Operadores booleanos

```
# AND (espacio)
password database

# OR
password OR secret

# NOT (-)
password -test -example

# Frase exacta
"api_key"

# Combinar
org:target "api_key" NOT test
```

---

> **Sintaxis histórica:** el original mezcla calificadores de búsquedas antiguas y actuales. En Code Search moderno se utilizan `content:` y `path:` para restringir contenido y rutas; las expresiones regulares se delimitan con `/.../`. `filename:`, `extension:`, `in:file`, `size:` y `pushed:` no deben suponerse equivalentes entre tipos de búsqueda. Conservamos todas las consultas literalmente; consulta la [sintaxis oficial](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax).

## 🔑 Claves API y tokens

### Claves de AWS

```
# Identificador de clave de acceso de AWS
AKIA
AKID
"aws_access_key_id"
"AWS_ACCESS_KEY_ID"

# Clave secreta de AWS
"aws_secret_access_key"
"AWS_SECRET_ACCESS_KEY"

# Combinaciones
filename:.env AWS_ACCESS_KEY
org:target AKIA
org:target "aws_secret"
```

### Google Cloud

```
# Clave API de GCP
"AIza"
"AIzaSy"

# Cuenta de servicio de GCP
"type": "service_account"
filename:credentials.json "private_key"
"client_secret" extension:json

# Firebase
"apiKey" "authDomain" "databaseURL"
filename:firebase
```

### Stripe

```
# Claves de Stripe
"sk_live_"
"rk_live_"
"pk_live_"
sk_live
rk_live

# Combinaciones
org:target sk_live
filename:.env STRIPE_SECRET
```

### Twilio

```
# SID y token de Twilio
"TWILIO_ACCOUNT_SID"
"TWILIO_AUTH_TOKEN"
"twilio" "sid" "token"
```

### Tokens de GitHub

```
# Tokens de acceso personal, según el encabezado del original
ghp_
gho_
ghu_
ghs_
ghr_

# Patrones de tokens de GitHub
"GITHUB_TOKEN"
"github_token"
"gh_token"
filename:.env GITHUB_TOKEN
```

### Slack

```
# Tokens de Slack
"xoxb-"
"xoxp-"
"xoxs-"
"xoxa-"

# Webhook de Slack
"hooks.slack.com"
"T[A-Z0-9]{8}/B[A-Z0-9]{8}/[a-zA-Z0-9]{24}"
```

### Otras claves API

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

> **Interpretación de coincidencias:** no todos estos valores son secretos. Los certificados públicos, claves publicables y ejemplos pueden estar destinados a distribución pública. Un patrón o prefijo solo identifica un candidato; no prueba vigencia, permisos ni exposición de una credencial privada.

## 💾 Credenciales de bases de datos

### MySQL

```
# Cadenas de conexión de MySQL
"mysql://"
"mysql_password"
"MYSQL_PASSWORD"
"DB_PASSWORD"

# Combinaciones
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
# Conexión de MongoDB
"mongodb://"
"mongodb+srv://"
"MONGO_URI"
"MONGODB_URI"

# Con credenciales
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

### Bases de datos en general

```
# Cadenas de conexión
"connection_string"
"connectionString"
"DB_CONNECTION"
"DATABASE_URL"

# Credenciales
"db_password"
"db_pass"
"database_password"
```

---

## 🔐 Claves privadas

### Claves SSH

```
# Claves privadas SSH
"-----BEGIN RSA PRIVATE KEY-----"
"-----BEGIN DSA PRIVATE KEY-----"
"-----BEGIN EC PRIVATE KEY-----"
"-----BEGIN OPENSSH PRIVATE KEY-----"
"-----BEGIN PRIVATE KEY-----"

# Archivos
filename:id_rsa
filename:id_dsa
filename:id_ed25519
extension:pem
extension:key

# Combinaciones
filename:id_rsa -example -sample
```

### Claves PGP/GPG

```
"-----BEGIN PGP PRIVATE KEY BLOCK-----"
filename:*.asc "PRIVATE KEY"
```

### Certificados SSL/TLS

```
"-----BEGIN CERTIFICATE-----"
filename:*.crt
filename:*.cer
extension:pem "CERTIFICATE"
```

---

<a id="-archivos-de-configuración"></a>
## ⚙️ Archivos de configuración

### Archivos de entorno

```
# Archivos .env
filename:.env
filename:.env.local
filename:.env.production
filename:.env.development
filename:.env.staging
filename:.env.backup

# Con secretos
filename:.env DB_PASSWORD
filename:.env API_KEY
filename:.env SECRET
```

### Archivos de configuración

```
# Varios archivos de configuración
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
# Configuraciones de Docker
filename:docker-compose.yml password
filename:Dockerfile ENV
filename:.dockercfg
filename:docker-compose.yml
```

### Kubernetes

```
# Secretos de Kubernetes
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

<a id="-secretos-de-servicios-cloud"></a>
## ☁️ Secretos de servicios cloud

### AWS

```
# Archivo de credenciales de AWS
filename:credentials aws_access_key_id
filename:.aws/credentials

# Configuración de AWS
"aws_access_key_id"
"aws_secret_access_key"
"aws_session_token"

# Buckets de S3
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

## 🔓 Contraseñas y credenciales

### Contraseñas incrustadas en el código

```
# Variables de contraseña
password=
passwd=
pass=
pwd=
"password:"
"password ="

# Con valores
password="
password='
"password": "
'password': '
```

### Credenciales SMTP

```
# SMTP
"smtp_password"
"SMTP_PASSWORD"
"mail_password"
"EMAIL_PASSWORD"
"smtp" "password"
```

### Secretos OAuth

```
# OAuth
"client_secret"
"CLIENT_SECRET"
"oauth_token"
"OAUTH_SECRET"
"refresh_token"
```

### Secretos JWT

```
# JWT
"JWT_SECRET"
"jwt_secret"
"JWT_KEY"
"secret_key"
"SECRET_KEY"
```

### Credenciales administrativas

```
# Administración
"admin_password"
"root_password"
"master_password"
"default_password"
```

---

## 🎯 Objetivos de bug bounty

### Organización objetivo

```
# Buscar en una organización concreta
org:target-company

# Repositorios de un usuario
user:target-username

# Repositorio concreto
repo:target/repository

# Combinado con secretos
org:target-company password
org:target-company api_key
org:target-company "secret"
```

### Búsqueda por dominio

```
# Dominio objetivo
"target.com"
"@target.com"
"api.target.com"
"internal.target.com"

# Combinaciones
"target.com" password
"target.com" api_key
org:target "@target.com"
```

### Correos de empleados

```
# Buscar repositorios de empleados
"@target.com"

# Correos de desarrolladores
"@target.com" password
"@target.com" config
```

### URL internas

```
# Servicios internos
"internal.target.com"
"dev.target.com"
"staging.target.com"
"admin.target.com"
"vpn.target.com"
"jira.target.com"
"confluence.target.com"
```

### Plantilla de dorks para bug bounty

```
# Reconocimiento completo
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

## 🔬 Técnicas avanzadas

### Búsqueda en el historial de Git

```
# Revisar los commits
# (Utilizar herramientas como TruffleHog y git-secrets)

# Actualizados recientemente
pushed:>2024-01-01 password
pushed:>2024-01-01 filename:.env
```

> **Historial:** buscar texto en el código actual o usar `pushed:` no equivale a examinar todos los commits y versiones eliminadas. La revisión del historial requiere una herramienta o un flujo específico.

### Búsqueda de gists

```
# Buscar gists
https://gist.github.com/search?q=password+target

# Patrones de gists
gist password
gist api_key
```

### Fragmentos de código

```
# Buscar en el código
in:file password
in:path config

# Archivos grandes (pueden contener más datos)
size:>10000 password
```

### Patrones de expresiones regulares (con herramientas)

```
# Patrón de claves de AWS
AKIA[0-9A-Z]{16}

# Token de GitHub
ghp_[a-zA-Z0-9]{36}

# Token de Slack
xox[baprs]-[0-9]{12}-[0-9]{12}-[a-zA-Z0-9]{24}

# Clave privada
-----BEGIN [A-Z]+ PRIVATE KEY-----
```

---

<a id="-herramientas-de-automatización"></a>
## 🛠️ Herramientas de automatización

### TruffleHog

```bash
# Instalar
pip install truffleHog

# Analizar un repositorio
trufflehog git https://github.com/target/repo

# Analizar una organización
trufflehog git https://github.com/target --only-verified
```

> **Compatibilidad de TruffleHog:** el comando de instalación mediante pip pertenece al flujo histórico del original. En versiones modernas, el modo `git` recibe un repositorio, mientras que una organización utiliza el modo `github` y sus opciones. `--only-verified` puede implicar comprobaciones activas del proveedor; interpreta ese comportamiento dentro del alcance autorizado. Véase el [manual oficial](https://github.com/trufflesecurity/trufflehog/blob/main/docs/man/trufflehog.1).

### GitLeaks

```bash
# Instalar
brew install gitleaks

# Analizar un repositorio
gitleaks detect -s /path/to/repo

# Analizar desde una URL
gitleaks detect --source https://github.com/target/repo

# Salida JSON
gitleaks detect -s repo --report-format json -r results.json
```

> **Compatibilidad de Gitleaks:** los ejemplos `detect` reflejan una interfaz histórica. El origen del análisis Git es normalmente un repositorio local; una URL HTTP no se convierte automáticamente en un directorio de trabajo. Consulta la interfaz de tu versión antes de reutilizar el ejemplo con `--source`.

### git-secrets

```bash
# Instalar
brew install git-secrets

# Añadir patrones
git secrets --add 'AKIA[A-Z0-9]{16}'
git secrets --add 'password\s*=\s*["\'][^"\']*["\']'

# Analizar un repositorio
git secrets --scan
```

### Herramientas de búsqueda en GitHub

```bash
# CLI de dorks de GitHub
# Existen varias herramientas de la comunidad

# GitDorker
python3 gitdorker.py -t TOKEN -org target

# gitrob
gitrob target-org
```

### shhgit

```bash
# Supervisión de GitHub en tiempo real
# Busca secretos en tiempo real
shhgit --search-query "org:target"
```

---

## 📊 Referencia rápida

### Dorks esenciales

| Finalidad | Dork |
|---------|------|
| Claves de AWS | `org:target AKIA` |
| Contraseñas | `org:target password filename:.env` |
| Claves API | `org:target api_key` |
| Claves privadas | `org:target "BEGIN RSA PRIVATE KEY"` |
| Archivos de configuración | `org:target filename:config.json` |
| Credenciales de bases de datos | `org:target "mongodb://"` |
| Tokens | `org:target token secret` |
| Correos electrónicos | `"@target.com" password` |

### Patrones de archivos

| Patrón | Dork |
|---------|------|
| `.env` | `filename:.env` |
| Configuración JSON | `filename:config.json` |
| Ajustes | `filename:settings.py` |
| Credenciales | `filename:credentials` |
| Secretos | `filename:secrets` |
| Docker | `filename:docker-compose.yml` |

### 15 dorks destacados de GitHub

| # | Dork | Finalidad |
|---|------|---------|
| 1 | `filename:.env DB_PASSWORD` | Contraseñas de bases de datos |
| 2 | `filename:.env AWS_` | Credenciales de AWS |
| 3 | `"-----BEGIN RSA PRIVATE KEY-----"` | Claves privadas |
| 4 | `filename:config.json password` | Contraseñas de configuración |
| 5 | `AKIA` | Claves de acceso de AWS |
| 6 | `sk_live` | Claves de Stripe |
| 7 | `"mongodb+srv://"` | URI de MongoDB |
| 8 | `filename:credentials aws` | Archivo de credenciales de AWS |
| 9 | `ghp_` | Tokens de GitHub |
| 10 | `xoxb-` | Tokens de Slack |
| 11 | `org:target password` | Contraseñas de la organización |
| 12 | `filename:.npmrc _auth` | Tokens de NPM |
| 13 | `extension:sql password` | Volcados SQL |
| 14 | `filename:wp-config.php` | WordPress |
| 15 | `"api_key" extension:json` | Claves API |

---

## 💡 Consejos y buenas prácticas

### Consejos de bug bounty

1. **Buscar en la organización**
   ```
   org:target-company
   ```

2. **Revisar los repositorios de empleados**
   ```
   "@target.com"
   ```

3. **Buscar URL internas**
   ```
   org:target "internal" OR "dev" OR "staging"
   ```

4. **Revisar el historial de Git**
   - Utilizar TruffleHog o GitLeaks

5. **Revisar los gists**
   - Los desarrolladores suelen guardar fragmentos de código

### Divulgación responsable

- **Comunica** los hallazgos al equipo de seguridad
- **No** accedas a sistemas con las credenciales encontradas
- **No** publiques las credenciales
- **Documenta** los hallazgos de forma adecuada

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** GitHub dorking revela información accesible públicamente. Aun así:
> 
> - ✅ Buscar en repositorios públicos
> - ✅ Informar mediante programas de bug bounty
> - ✅ Divulgación responsable
> - ❌ No utilizar las credenciales encontradas
> - ❌ No acceder a sistemas
> - ❌ No almacenar ni compartir secretos
> 
> **¡Utilizar credenciales encontradas es ilegal!**

---

## 📚 Recursos

### Herramientas
- [TruffleHog](https://github.com/trufflesecurity/trufflehog)
- [GitLeaks](https://github.com/gitleaks/gitleaks)
- [git-secrets](https://github.com/awslabs/git-secrets)
- [shhgit](https://github.com/eth0izzle/shhgit)

### Referencias
- [Documentación de búsquedas de GitHub](https://docs.github.com/en/search-github)
- [Patrones de detección de secretos](https://docs.github.com/en/code-security/secret-scanning)

### Guías rápidas relacionadas
- [Google Dorking](../../Google-Dorking/translations/README.es.md)
- [Shodan](../../Shodan/translations/README.es.md)
- [Subfinder](../../Subfinder/translations/README.es.md)

---

<p align="center">
  <b>🐙 ¡Encuentra los secretos antes que los atacantes!</b><br>
  <i>Domina GitHub dorking para bug bounty</i>
</p>
