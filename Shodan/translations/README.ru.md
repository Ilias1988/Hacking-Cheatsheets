<a id="-shodan---complete-cheatsheet"></a>
# 🌐 Shodan — полный справочник

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

> **Уточнения к оригиналу.** По [основам поиска Shodan](https://help.shodan.io/the-basics/search-query-fundamentals), поиск работает по собранным баннерам сервисов. Совпадение версии, CVE, favicon или текста о пароле не доказывает текущую уязвимость, отсутствие аутентификации либо работоспособность учётных данных. Примеры частных диапазонов net относятся к синтаксису фильтра и не означают, что Shodan индексирует внутреннюю сеть пользователя. Надпись в ASCII-логотипе означает «поисковая система для устройств, подключённых к интернету».

> В [официальном списке фильтров](https://www.shodan.io/search/filters) tag и vuln ограничены более высокими тарифами; http.server и ssl.cert.expires в этом списке не приведены. Сравнение issuer.cn с текстом другого поля не является проверкой равенства полей. Дата 2024 устарела; пустой vuln: и конструкции с ||/скобками не следует считать проверенным универсальным синтаксисом. Исходные запросы сохранены дословно.

```
  ███████╗██╗  ██╗ ██████╗ ██████╗  █████╗ ███╗   ██╗
  ██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔══██╗████╗  ██║
  ███████╗███████║██║   ██║██║  ██║███████║██╔██╗ ██║
  ╚════██║██╔══██║██║   ██║██║  ██║██╔══██║██║╚██╗██║
  ███████║██║  ██║╚██████╔╝██████╔╝██║  ██║██║ ╚████║
  ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝
                                                      
   The Search Engine for Internet-Connected Devices
```

<p align="center">
  <img src="https://img.shields.io/badge/Shodan-IoT_Search-red?style=for-the-badge" alt="Shodan">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Reconnaissance-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/IoT-Security-green?style=for-the-badge" alt="IoT">
</p>

<p align="center">
  <b>🔍 Поисковая система для подключённых к интернету устройств, серверов и инфраструктуры</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Начало работы](#-getting-started)
- [Базовые фильтры](#-basic-filters)
- [Сетевые фильтры](#-network-filters)
- [Фильтры SSL/TLS](#-ssltls-filters)
- [Фильтры HTTP](#-http-filters)
- [Уязвимые сервисы](#-vulnerable-services)
- [IoT и ICS/SCADA](#-iot--icsscada)
- [Базы данных](#-databases)
- [Веб-камеры и мультимедиа](#-webcams--media)
- [Облачные сервисы](#-cloud-services)
- [Запросы для Bug Bounty](#-bug-bounty-queries)
- [Интерфейс командной строки Shodan](#-shodan-cli)
- [Краткий справочник](#-quick-reference)
- [Ресурсы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

**Shodan** — поисковая система, которая позволяет с помощью фильтров находить определённые типы компьютеров, серверов и IoT-устройств, подключённых к интернету.

<a id="what-can-you-find"></a>
### Что можно найти?

| Категория | Примеры |
|----------|----------|
| **Серверы** | Веб-серверы, FTP, SSH и базы данных |
| **IoT-устройства** | Камеры, маршрутизаторы и умные устройства |
| **ICS/SCADA** | Промышленные системы управления |
| **Облако** | Экземпляры AWS, Azure и GCP |
| **Уязвимости** | Доступные извне сервисы и учётные данные по умолчанию |

<a id="shodan-urls"></a>
### Адреса Shodan

| Сервис | URL |
|---------|-----|
| **Веб-поиск** | https://www.shodan.io |
| **Карты** | https://maps.shodan.io |
| **Эксплойты** | https://exploits.shodan.io |
| **Изображения** | https://images.shodan.io |

---

<a id="-getting-started"></a>
## 🚀 Начало работы

<a id="create-account"></a>
### Создание учётной записи

1. Откройте https://www.shodan.io
2. Создайте бесплатную учётную запись
3. Получите ключ API в настройках учётной записи

<a id="membership-tiers"></a>
### Уровни подписки

> Цены и названия тарифов ниже перенесены из оригинала и не являются актуальным предложением. Доступ зависит от тарифа и [query/scan credits](https://help.shodan.io/the-basics/credit-types-explained); таблица не гарантирует безлимитный доступ.

| Тариф | Цена | Возможности |
|------|-------|----------|
| **Бесплатный** | $0 | Базовый поиск, ограниченное число результатов |
| **Участник** | $49 | Больше результатов, загрузка данных и CLI |
| **Малый бизнес** | $299/mo | Больше кредитов и мониторинг |
| **Корпоративный** | $899/mo | Неограниченный доступ (формулировка оригинала) |

<a id="api-key"></a>
### Ключ API

> Для [настройки официального CLI](https://help.shodan.io/command-line-interface/0-installation) используется shodan init. Одного export переменной из примера недостаточно считать подтверждённой настройкой CLI. Команда scan submit инициирует активное сканирование, в отличие от чтения уже собранных данных. Аргументы и доступность команд сверяйте с установленной версией и [справкой API](https://developer.shodan.io/api).

```bash
# Получите на странице: https://account.shodan.io
# Экспорт переменной для CLI (см. уточнение)
export SHODAN_API_KEY="YOUR_API_KEY"
```

---

<a id="-basic-filters"></a>
## 🔧 Базовые фильтры

<a id="text-search"></a>
### Текстовый поиск

```
# Простой поиск
apache
nginx
Microsoft-IIS

# Поиск по продукту
product:apache
product:nginx
product:openssh
```

<a id="country--city"></a>
### Страна и город

```
# Страна (ISO 3166-1 alpha-2)
country:US
country:DE
country:RU
country:CN
country:GR                      # Греция

# Город
city:Athens
city:"New York"
city:London
city:Tokyo

# Объединить условия
country:US city:Miami
country:GR city:Thessaloniki
```

<a id="organization--isp"></a>
### Организация и интернет-провайдер (ISP)

```
# Организация
org:"Google"
org:"Amazon"
org:"Microsoft"
org:"Facebook"

# ISP
isp:"Comcast"
isp:"Verizon"
isp:"AT&T"
```

<a id="hostname--domain"></a>
### Имя хоста и домен

```
# Имя хоста содержит
hostname:google.com
hostname:.gov
hostname:.edu
hostname:target.com

# Определённый домен
ssl.cert.subject.cn:target.com
```

<a id="operating-system"></a>
### Операционная система

```
os:"Windows"
os:"Linux"
os:"Ubuntu"
os:"Windows Server 2019"
os:"Windows 7"
os:"FreeBSD"
```

---

<a id="-network-filters"></a>
## 🌐 Сетевые фильтры

<a id="port"></a>
### Порт

```
# Определённый порт
port:22                         # SSH
port:21                         # FTP
port:23                         # Telnet
port:80                         # HTTP
port:443                        # HTTPS
port:3389                       # RDP
port:3306                       # MySQL
port:5432                       # PostgreSQL
port:27017                      # MongoDB
port:6379                       # Redis
port:9200                       # Elasticsearch

# Несколько портов
port:22,80,443
```

<a id="ip--network"></a>
### IP-адрес и сеть

```
# Определённый IP-адрес
ip:8.8.8.8

# Диапазон IP (CIDR)
net:192.168.0.0/24
net:10.0.0.0/8
net:172.16.0.0/12

# ASN
asn:AS15169                     # Google
asn:AS16509                     # Amazon
asn:AS8075                      # Microsoft
```

<a id="geo-location"></a>
### Географическое положение

```
# Географические координаты
geo:37.9838,23.7275             # Координаты Афин
geo:40.7128,-74.0060,10         # Нью-Йорк с радиусом поиска

# Регион или штат
region:California
region:"New York"
```

---

<a id="-ssltls-filters"></a>
## 🔐 Фильтры SSL/TLS

<a id="certificate-information"></a>
### Сведения о сертификате

```
# Субъект сертификата
ssl.cert.subject.cn:target.com
ssl.cert.subject.cn:*.target.com

# Издатель сертификата
ssl.cert.issuer.cn:"Let's Encrypt"
ssl.cert.issuer.cn:"DigiCert"

# Самоподписанные сертификаты (подпись оригинала; см. уточнение)
ssl.cert.issuer.cn:ssl.cert.subject.cn
```

<a id="ssl-versions"></a>
### Версии SSL/TLS

```
# Версии SSL/TLS, отмеченные в оригинале как уязвимые
ssl.version:sslv2
ssl.version:sslv3
ssl.version:tlsv1
ssl.version:tlsv1.1

# Защищённые версии (оценка оригинала)
ssl.version:tlsv1.2
ssl.version:tlsv1.3
```

<a id="certificate-expiry"></a>
### Срок действия сертификата

```
# Просроченные сертификаты
ssl.cert.expired:true

# Сертификаты, срок которых скоро истекает (пример оригинала)
ssl.cert.expires:2024
```

<a id="ssl-vulnerabilities"></a>
### Уязвимости SSL

```
# Heartbleed
vuln:CVE-2014-0160

# POODLE
ssl.version:sslv3

# DROWN
ssl.version:sslv2
```

---

<a id="-http-filters"></a>
## 🌍 Фильтры HTTP

<a id="http-status--headers"></a>
### Статус HTTP и заголовки

```
# Код состояния HTTP
http.status:200
http.status:401
http.status:403
http.status:500

# Заголовок страницы HTTP
http.title:"Dashboard"
http.title:"Login"
http.title:"Admin"
http.title:"Index of"

# Содержимое HTML
http.html:"password"
http.html:"admin"
http.html:"login"
```

<a id="web-technologies"></a>
### Веб-технологии

```
# Заголовок Server
http.server:apache
http.server:nginx
http.server:Microsoft-IIS

# Определённые версии
http.server:"Apache/2.4.49"     # Уязвимая версия (оценка оригинала)
http.server:"nginx/1.18"

# Хеш favicon
http.favicon.hash:116323821     # Определённая favicon
http.favicon.hash:-1137975059   # Jenkins
```

<a id="components"></a>
### Компоненты

```
# Веб-фреймворки
http.component:"WordPress"
http.component:"Joomla"
http.component:"Drupal"
http.component:"Laravel"
http.component:"Django"

# Технологии
http.component:"PHP"
http.component:"jQuery"
http.component:"Bootstrap"
```

---

<a id="-vulnerable-services"></a>
## ⚠️ Уязвимые сервисы

<a id="known-cves"></a>
### Известные CVE

```
# Поиск по CVE
vuln:CVE-2021-44228            # Log4Shell
vuln:CVE-2021-26855            # ProxyLogon
vuln:CVE-2019-0708             # BlueKeep
vuln:CVE-2017-0144             # EternalBlue
vuln:CVE-2014-0160             # Heartbleed
vuln:CVE-2014-6271             # Shellshock

# Несколько CVE
vuln:CVE-2021-44228,CVE-2021-45046
```

<a id="default-credentials"></a>
### Учётные данные по умолчанию

```
# Пароли по умолчанию
"default password"
"admin" "password"
http.title:"Login" "admin"

# Конкретные учётные данные по умолчанию
"admin:admin"
"root:root"
"admin:123456"
```

<a id="openexposed-services"></a>
### Открытые и доступные извне сервисы

```
# Анонимный FTP
port:21 "Anonymous access granted"

# Открытый Telnet
port:23 -authentication

# Открытая MongoDB
port:27017 "MongoDB Server Information"

# Открытый Redis
port:6379 -authentication

# Открытый Elasticsearch
port:9200 "cluster_name"

# Открытый Memcached
port:11211 "STAT"
```

<a id="exposed-panels"></a>
### Доступные извне панели

```
# phpMyAdmin
http.title:"phpMyAdmin"

# cPanel
http.title:"cPanel"

# Webmin
http.title:"Webmin"

# Jenkins без аутентификации (предположение оригинала)
http.title:"Dashboard [Jenkins]"

# Grafana
http.title:"Grafana"
```

---

<a id="-iot--icsscada"></a>
## 🏭 IoT и ICS/SCADA

<a id="industrial-control-systems"></a>
### Промышленные системы управления

```
# Общий поиск ICS
tag:ics

# Modbus
port:502

# Siemens S7
port:102

# DNP3
port:20000

# BACnet
port:47808

# EtherNet/IP
port:44818

# Niagara Fox
port:1911
```

<a id="scada-systems"></a>
### Системы SCADA

```
# SCADA
tag:scada

# Определённые системы SCADA
"SCADA" port:502
"PLC" country:US
"Siemens" port:102
"Schneider"
```

<a id="iot-devices"></a>
### IoT-устройства

```
# Общий поиск IoT
tag:iot

# Маршрутизаторы
"router" port:80
http.title:"Router"

# Принтеры
port:9100
"printer"
http.title:"HP"

# Умный дом
"smart home"
"IoT Gateway"
```

<a id="network-devices"></a>
### Сетевые устройства

```
# Cisco
"cisco" product:cisco

# MikroTik
product:mikrotik
http.title:"RouterOS"

# Ubiquiti
product:ubiquiti

# Fortinet
product:fortigate
```

---

<a id="-databases"></a>
## 💾 Базы данных

<a id="mysql"></a>
### MySQL

```
port:3306
port:3306 "MySQL"
port:3306 "unauthorized"
product:mysql
```

<a id="postgresql"></a>
### PostgreSQL

```
port:5432
port:5432 "PostgreSQL"
product:postgresql
```

<a id="mongodb"></a>
### MongoDB

```
port:27017
port:27017 "MongoDB Server Information"
port:27017 "totalSize"
product:mongodb
"MongoDB Server Information" -authentication
```

<a id="redis"></a>
### Redis

```
port:6379
port:6379 "redis"
port:6379 -authentication
product:redis
```

<a id="elasticsearch"></a>
### Elasticsearch

```
port:9200
port:9200 "elastic"
port:9200 "cluster_name"
port:9200 "You Know, for Search"
product:elasticsearch
```

<a id="couchdb"></a>
### CouchDB

```
port:5984
port:5984 "couchdb"
"couchdb" "Welcome"
```

<a id="memcached"></a>
### Memcached

```
port:11211
port:11211 "STAT"
product:memcached
```

<a id="cassandra"></a>
### Cassandra

```
port:9042
port:9160
product:cassandra
```

---

<a id="-webcams--media"></a>
## 📹 Веб-камеры и мультимедиа

<a id="ip-cameras"></a>
### IP-камеры

```
# Общий поиск веб-камер
webcam
"webcam" country:US

# Производители камер
"hikvision"
"dahua"
"axis"
"foscam"
"netcam"

# Потоки RTSP
port:554 has_screenshot:true
"rtsp" port:554
```

<a id="dvrnvr-systems"></a>
### Системы DVR/NVR

```
"dvr" port:80
"nvr" 
http.title:"DVR"
http.title:"NVR"
"Network Video Recorder"
```

<a id="streaming"></a>
### Потоковое вещание

```
# Потоковое вещание VLC
"VLC media player"

# Медиасерверы
product:plex
"Plex Media Server"
```

<a id="specific-camera-interfaces"></a>
### Интерфейсы отдельных производителей камер

```
# Hikvision
http.title:"DVRDVS-Webs"
"App-webs" port:80

# Dahua
http.title:"WEB SERVICE"

# Axis
http.title:"AXIS"
```

---

<a id="-cloud-services"></a>
## ☁️ Облачные сервисы

<a id="amazon-aws"></a>
### Amazon AWS

```
# Общий поиск AWS
org:"Amazon"
asn:AS16509

# Бакеты S3
"AmazonS3"

# EC2
"ec2" org:"Amazon"

# Сервисы AWS
http.title:"AWS"
```

<a id="microsoft-azure"></a>
### Microsoft Azure

```
org:"Microsoft"
asn:AS8075
hostname:azure.com
hostname:cloudapp.azure.com
```

<a id="google-cloud"></a>
### Google Cloud

```
org:"Google"
asn:AS15169
hostname:googleusercontent.com
```

<a id="digitalocean"></a>
### DigitalOcean

```
org:"DigitalOcean"
asn:AS14061
```

<a id="exposed-cloud-panels"></a>
### Доступные извне облачные панели

```
# Kubernetes Dashboard
http.title:"Kubernetes Dashboard"

# Docker
product:docker
port:2375

# Jenkins
http.title:"Jenkins"

# GitLab
http.title:"GitLab"
```

---

<a id="-bug-bounty-queries"></a>
## 🐛 Запросы для Bug Bounty

<a id="organization-recon"></a>
### Разведка организации

```
# Целевая организация
org:"Target Company"
ssl.cert.subject.cn:target.com
hostname:target.com

# Поиск по ASN
asn:AS12345
net:1.2.3.0/24
```

<a id="find-exposed-services"></a>
### Поиск доступных извне сервисов

```
# Все сервисы цели
org:"Target" port:22,80,443,3306,5432,27017

# Административные панели
org:"Target" http.title:"admin"

# Страницы входа
org:"Target" http.title:"login"
```

<a id="subdomain-discovery"></a>
### Поиск поддоменов

```
ssl.cert.subject.cn:*.target.com
ssl.cert.subject.cn:target.com
hostname:target.com
```

<a id="vulnerable-services"></a>
### Уязвимые сервисы

```
# Проверить CVE
org:"Target" vuln:CVE-2021-44228

# Учётные данные по умолчанию
org:"Target" "default password"

# Открытые базы данных
org:"Target" port:27017,6379,9200
```

<a id="complete-bug-bounty-query"></a>
### Составной запрос для Bug Bounty

```
# Полная разведка
ssl.cert.subject.cn:target.com || hostname:target.com || org:"Target Company"

# С дополнительными фильтрами
(ssl.cert.subject.cn:target.com || hostname:target.com) port:22,80,443,3306,5432
```

---

<a id="-shodan-cli"></a>
## 💻 Интерфейс командной строки Shodan

<a id="installation"></a>
### Установка

```bash
# Установка через pip
pip install shodan

# Инициализация ключом API
shodan init YOUR_API_KEY
```

<a id="basic-commands"></a>
### Базовые команды

```bash
# Сведения об учётной записи
shodan info

# Поиск
shodan search "apache"

# Поиск с фильтрами
shodan search "port:22 country:US"

# Подсчитать результаты
shodan count "nginx"

# Загрузить результаты
shodan download results.json.gz "apache"

# Разобрать результаты
shodan parse results.json.gz

# Получить сведения о хосте
shodan host 8.8.8.8
```

<a id="advanced-cli"></a>
### Расширенные команды CLI

```bash
# Сведения о домене
shodan domain target.com

# Сканирование (требуются кредиты)
shodan scan submit 1.2.3.4

# Honeyscore — оценка вероятности honeypot
shodan honeyscore 1.2.3.4

# Сведения о собственном IP
shodan myip

# Статистика
shodan stats "apache" --facets country,org
```

<a id="api-usage-python"></a>
### Использование API (Python)

```python
import shodan

api = shodan.Shodan('YOUR_API_KEY')

# Поиск
results = api.search('apache')
for result in results['matches']:
    print(result['ip_str'], result['port'])

# Получение сведений о хосте
host = api.host('8.8.8.8')
print(host['ip_str'], host['ports'])
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-filters"></a>
### Основные фильтры

| Фильтр | Описание | Пример |
|--------|-------------|---------|
| `port:` | Номер порта | `port:22` |
| `country:` | Код страны | `country:US` |
| `city:` | Название города | `city:London` |
| `org:` | Организация | `org:"Google"` |
| `hostname:` | Имя хоста | `hostname:google.com` |
| `net:` | Диапазон IP/CIDR | `net:10.0.0.0/8` |
| `os:` | Операционная система | `os:"Windows"` |
| `product:` | Название продукта | `product:nginx` |
| `version:` | Версия | `version:"2.4.49"` |
| `vuln:` | Идентификатор CVE | `vuln:CVE-2021-44228` |

<a id="http-filters"></a>
### Фильтры HTTP

| Фильтр | Описание | Пример |
|--------|-------------|---------|
| `http.title:` | Заголовок страницы | `http.title:"Admin"` |
| `http.status:` | Статус HTTP | `http.status:200` |
| `http.server:` | Заголовок Server | `http.server:nginx` |
| `http.html:` | Содержимое HTML | `http.html:"login"` |
| `http.component:` | Технология | `http.component:"WordPress"` |

<a id="ssl-filters"></a>
### Фильтры SSL

| Фильтр | Описание | Пример |
|--------|-------------|---------|
| `ssl.cert.subject.cn:` | CN сертификата | `ssl.cert.subject.cn:target.com` |
| `ssl.cert.issuer.cn:` | CN издателя | `ssl.cert.issuer.cn:"DigiCert"` |
| `ssl.version:` | Версия SSL | `ssl.version:sslv3` |
| `ssl.cert.expired:` | Просроченный сертификат | `ssl.cert.expired:true` |

<a id="top-10-shodan-queries"></a>
### Десять полезных запросов Shodan

| # | Запрос | Назначение |
|---|-------|---------|
| 1 | `port:22 country:US` | SSH в США |
| 2 | `port:3389 country:US` | RDP в США |
| 3 | `port:27017 -authentication` | Открытая MongoDB |
| 4 | `vuln:CVE-2021-44228` | Log4Shell |
| 5 | `http.title:"Dashboard"` | Панели управления |
| 6 | `"default password"` | Учётные данные по умолчанию |
| 7 | `port:9200 "elastic"` | Elasticsearch |
| 8 | `webcam` | IP-камеры |
| 9 | `product:apache vuln:` | Уязвимый Apache |
| 10 | `ssl.cert.expired:true` | Просроченные сертификаты |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендации

<a id="bug-bounty-tips"></a>
### Советы для Bug Bounty

1. **Начните с поиска сертификатов**
   ```
   ssl.cert.subject.cn:target.com
   ```

2. **Проверьте все порты**
   ```
   hostname:target.com port:22,80,443,3306,5432
   ```

3. **Ищите доступные извне сервисы**
   ```
   org:"Target" (port:27017 || port:9200 || port:6379)
   ```

4. **Проверьте уязвимости**
   ```
   org:"Target" vuln:
   ```

<a id="combine-filters"></a>
### Объединение фильтров

```
# Составные запросы
port:22 country:US org:"Target" os:"Ubuntu"
http.title:"admin" -http.title:"Login" country:US
ssl.cert.subject.cn:target.com port:443 http.status:200
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовое предупреждение

Ниже переведено предупреждение оригинала.

> **ПРЕДУПРЕЖДЕНИЕ:** Shodan показывает общедоступную информацию. Однако:
> 
> - ✅ Используйте для разрешённой разведки
> - ✅ Исследуйте цели, входящие в область программы Bug Bounty
> - ✅ Ответственно сообщайте об уязвимостях
> - ❌ Не эксплуатируйте найденные уязвимости
> - ❌ Не обращайтесь к системам без разрешения
> - ❌ Соблюдайте конфиденциальность и законы
> 
> **Сканирование без разрешения незаконно!**

---

<a id="-resources"></a>
## 📚 Ресурсы

<a id="official-resources"></a>
### Официальные ресурсы
- [Shodan](https://www.shodan.io)
- [Справка Shodan](https://help.shodan.io)
- [Интерфейс командной строки Shodan](https://cli.shodan.io)
- [API Shodan](https://developer.shodan.io)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [Google Dorking — поиск с операторами](../../Google-Dorking/translations/README.ru.md)
- [GitHub Dorking — поиск по коду](../../GitHub-Dorking/translations/README.ru.md)
- [Nmap](../../Nmap/translations/README.ru.md)

---

<p align="center">
  <b>🌐 Увидьте скрытую сторону интернета!</b><br>
  <i>Освойте Shodan для разведки</i>
</p>
