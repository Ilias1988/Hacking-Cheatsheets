<a id="-amass---attack-surface-mapping"></a>
# 🌐 Amass — исследование поверхности атаки

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское содержание](../../README.ru.md) · [Английский оригинал](../README.md)

> **О версиях.** Оригинал сочетает примеры разных выпусков Amass, включая путь установки v4. Доступность подкоманды `dns`, параметров `-max-depth`, `-ip` и формат YAML зависят от версии. Команды и конфигурация сохранены дословно; сверяйте их с документацией своей версии в [официальном репозитории OWASP Amass](https://github.com/owasp-amass/amass).

> Надпись в логотипе означает «Подробное исследование поверхности атаки». Оценки полноты, число источников и сведения о бесплатных тарифах перенесены из оригинала и не гарантируют текущие условия.

```
   █████╗ ███╗   ███╗ █████╗ ███████╗███████╗
  ██╔══██╗████╗ ████║██╔══██╗██╔════╝██╔════╝
  ███████║██╔████╔██║███████║███████╗███████╗
  ██╔══██║██║╚██╔╝██║██╔══██║╚════██║╚════██║
  ██║  ██║██║ ╚═╝ ██║██║  ██║███████║███████║
  ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
         In-depth Attack Surface Mapping
```

<p align="center">
  <img src="https://img.shields.io/badge/Amass-OWASP-blue?style=for-the-badge" alt="Amass">
  <img src="https://img.shields.io/badge/Subdomain-Discovery-green?style=for-the-badge" alt="Subdomain">
  <img src="https://img.shields.io/badge/ASN-Mapping-red?style=for-the-badge" alt="ASN"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Amass](#-what-is-amass)
- [Установка](#-installation)
- [Режим сбора сведений (enum)](#-enumeration-mode)
- [Режим разведки (intel)](#-intel-mode)
- [Режим DNS](#-dns-mode)
- [Настройка](#-configuration)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-amass"></a>
## 🎯 Что такое Amass

**Amass** — проект OWASP для исследования сетей и поиска внешних ресурсов:

- 🔍 **Поиск поддоменов** — наиболее полный инструмент
- 🏢 **Поиск ASN** — поиск диапазонов IP-адресов организации
- 📡 **Разведка DNS** — пассивное и активное исследование
- 🗺️ **Исследование поверхности атаки** — визуализация инфраструктуры

<a id="why-amass"></a>
### Зачем использовать Amass?

| Возможность | Преимущество |
| --------- | --------- |
| Более 55 источников данных | Наиболее полные результаты |
| Пассивный и активный режимы | Выбор степени взаимодействия с целью |
| Сведения об ASN и организациях | Поиск неочевидных компонентов инфраструктуры |
| Графовая база данных | Визуализация связей |

---

<a id="-installation"></a>
## 🚀 Установка

```bash
# Установка через Go
go install -v github.com/owasp-amass/amass/v4/...@master

# Kali Linux
sudo apt install amass

# Docker
docker pull caffix/amass

# Проверить версию
amass -version
```

---

<a id="-enumeration-mode"></a>
## 🔍 Режим сбора сведений (enum)

<a id="basic-subdomain-enumeration"></a>
### Базовый поиск поддоменов

> **Уточнение.** В [руководстве v3.23.3](https://github.com/owasp-amass/amass/blob/v3.23.3/doc/user_guide.md) параметр `-active` включает дополнительные активные проверки; перебор задаётся отдельно через `-brute`.

```bash
# Пассивный поиск (без прямого взаимодействия с целью)
amass enum -passive -d example.com

# Активный поиск (подпись оригинала о переборе DNS; см. уточнение)
amass enum -active -d example.com

# Перебор по словарю
amass enum -active -brute -d example.com -w wordlist.txt
```

<a id="output-options"></a>
### Параметры вывода

```bash
# Сохранить в файл
amass enum -passive -d example.com -o subdomains.txt

# Вывод JSON
amass enum -passive -d example.com -json output.json

# Сохранить в каталог
amass enum -passive -d example.com -dir ./amass_output/
```

<a id="multiple-targets"></a>
### Несколько целей

```bash
# Несколько доменов
amass enum -passive -d example.com -d target.com

# Из файла
amass enum -passive -df domains.txt -o results.txt
```

<a id="advanced-enumeration"></a>
### Дополнительные возможности сбора сведений

```bash
# Задать время работы в минутах
amass enum -passive -d example.com -timeout 30

# Ограничить набор источников
amass enum -passive -d example.com -src -include CertSpotter,Crtsh,HackerTarget

# Исключить источники
amass enum -passive -d example.com -exclude Google,Bing

# Показать источники данных
amass enum -passive -d example.com -src
```

<a id="recursive-enumeration"></a>
### Рекурсивный поиск

> **Ошибка оригинала.** В [руководстве v3.23.3](https://github.com/owasp-amass/amass/blob/v3.23.3/doc/user_guide.md) параметр `-r` принимает адреса DNS-резолверов и не включает рекурсию. Параметр `-max-depth` там ограничивает число уровней имени при переборе, а не глубину пассивного поиска. Примеры сохранены для сопоставления с оригиналом.

```bash
# Включить рекурсивный поиск (подпись оригинала; см. уточнение о -r)
amass enum -passive -d example.com -r

# Задать глубину рекурсии (подпись оригинала; см. уточнение о переборе)
amass enum -passive -d example.com -max-depth 3
```

---

<a id="-intel-mode"></a>
## 🏢 Режим разведки (intel)

> Режим intel помогает находить корневые домены, автономные системы ASN и диапазоны CIDR, связанные с организациями.

<a id="find-organization-assets"></a>
### Поиск ресурсов организации

```bash
# По названию организации
amass intel -org "Tesla Inc"
amass intel -org "Microsoft"

# Обратный поиск WHOIS
amass intel -whois -d example.com
```

<a id="asn-discovery"></a>
### Поиск автономных систем (ASN)

```bash
# Найти ASN для домена
amass intel -d example.com

# Сбор сведений по ASN
amass intel -asn 13335
amass intel -active -asn 13335

# Несколько ASN
amass intel -asn 13335,15169
```

<a id="cidr-range"></a>
### Диапазон CIDR

```bash
# Найти ресурсы в диапазоне CIDR
amass intel -active -cidr 192.168.1.0/24

# Несколько диапазонов CIDR
amass intel -cidr 192.168.1.0/24 -cidr 10.0.0.0/8
```

<a id="ip-range"></a>
### Диапазон IP-адресов

> **Уточнение версии.** В [руководстве v3.23.3](https://github.com/owasp-amass/amass/blob/v3.23.3/doc/user_guide.md) диапазон IP-адресов для `intel` задаётся параметром `-addr`; пример оригинала использует `-ip`.

```bash
# Диапазон IP-адресов
amass intel -active -ip 192.168.1.1-254
```

---

<a id="-dns-mode"></a>
## 📡 Режим DNS

> **Уточнение версии.** Самостоятельная подкоманда `amass dns` отсутствует в перечне подкоманд [руководства v3.23.3](https://github.com/owasp-amass/amass/blob/v3.23.3/doc/user_guide.md); приведённый синтаксис оригинала нельзя считать универсальным.

<a id="dns-queries"></a>
### DNS-запросы

```bash
# Базовый DNS-запрос
amass dns -d example.com

# Определённые типы DNS-записей
amass dns -d example.com -t A,AAAA,MX,NS,TXT
```

<a id="resolve-subdomains"></a>
### Разрешение имён поддоменов

```bash
# Разрешить имена найденных поддоменов
amass dns -df subdomains.txt
```

---

<a id="-configuration"></a>
## ⚙️ Настройка

<a id="config-file-location"></a>
### Расположение файла настроек

```bash
# Расположение по умолчанию
~/.config/amass/config.yaml

# Указать свой файл настроек
amass enum -config ./my_config.yaml -d example.com
```

<a id="sample-config-file"></a>
### Пример файла настроек

> В [руководстве v3.23.3](https://github.com/owasp-amass/amass/blob/v3.23.3/doc/user_guide.md) описан файл `config.ini`, поэтому показанный YAML не является конфигурацией для этой версии. Схема оригинала сохранена дословно.

```yaml
# config.yaml
scope:
  domains:
    - example.com
    - target.com

resolvers:
  - 8.8.8.8
  - 8.8.4.4
  - 1.1.1.1

options:
  log: /tmp/amass.log
  max_dns_queries: 10000

data_sources:
  Crtsh:
  - 
  SecurityTrails:
  - credentials:
      apikey: YOUR_API_KEY
  Shodan:
  - credentials:
      apikey: YOUR_SHODAN_KEY
  VirusTotal:
  - credentials:
      apikey: YOUR_VT_KEY
```

<a id="data-sources-with-api-keys"></a>
### Источники данных с API-ключами

| Источник | Бесплатный тариф | Нужен API |
| -------- | ----------- | -------------- |
| Crt.sh | ✅ | Нет |
| SecurityTrails | ✅ | Да |
| Shodan | ✅ | Да |
| VirusTotal | ✅ | Да |
| Censys | ✅ | Да |
| PassiveTotal | Ограниченно | Да |
| BinaryEdge | Ограниченно | Да |

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="enumeration-commands"></a>
### Команды сбора сведений

| Команда | Описание |
| --------- | ------------- |
| `amass enum -passive -d domain.com` | Пассивный поиск поддоменов |
| `amass enum -active -d domain.com` | Активное исследование DNS |
| `amass enum -active -brute -d domain.com` | Перебор имён поддоменов |
| `amass enum -d domain.com -o out.txt` | Сохранить в файл |
| `amass enum -d domain.com -json out.json` | Вывод JSON |

<a id="intel-commands"></a>
### Команды разведки

| Команда | Описание |
| --------- | ------------- |
| `amass intel -org "Company"` | Поиск ресурсов по названию организации |
| `amass intel -asn 12345` | Сбор сведений по ASN |
| `amass intel -cidr 10.0.0.0/8` | Исследование диапазона CIDR |
| `amass intel -whois -d domain.com` | Обратный поиск WHOIS |

<a id="essential-flags"></a>
### Основные параметры

| Параметр | Описание |
| ------ | ------------- |
| `-d` | Целевой домен |
| `-df` | Домены из файла |
| `-o` | Файл вывода |
| `-passive` | Только пассивный режим (без DNS-запросов) |
| `-active` | Включить активное исследование DNS |
| `-brute` | Перебор имён DNS |
| `-src` | Показать источник данных |
| `-timeout` | Время работы в минутах |

---

<a id="-bug-bounty-workflow"></a>
## 🎯 Последовательность работы в Bug Bounty

> Последний шаг проверяет доступность HTTP-сервисов с помощью `httpx`. Это шире, чем простое разрешение DNS-имён, указанное в подписи оригинала.

```bash
# 1. Пассивный поиск (в оригинале отмечен как безопасный)
amass enum -passive -d target.com -o passive_subs.txt

# 2. Активное исследование с перебором
amass enum -active -brute -d target.com -w dns_wordlist.txt -o active_subs.txt

# 3. Найти ресурсы организации
amass intel -org "Target Inc" -o org_domains.txt

# 4. Объединить результаты
cat passive_subs.txt active_subs.txt | sort -u > all_subdomains.txt

# 5. Проверить доступность HTTP-сервисов
cat all_subdomains.txt | httpx -silent > live_hosts.txt
```

---

<a id="-resources"></a>
## 📚 Полезные материалы

- [OWASP Amass GitHub](https://github.com/owasp-amass/amass)
- [Руководство пользователя Amass](https://github.com/owasp-amass/amass/blob/master/doc/user_guide.md)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Методология Bug Bounty](../../Bug-Bounty-Methodology/translations/README.ru.md)
- [Subfinder](../../Subfinder/translations/README.ru.md)
- [httpx](../../httpx/translations/README.ru.md)

---

<p align="center">
  <b>🌐 Исследуйте поверхность атаки!</b><br>
  <i>Amass — наиболее полный инструмент поиска поддоменов</i>
</p>
