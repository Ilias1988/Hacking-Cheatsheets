<a id="-subfinder---complete-cheatsheet"></a>
# 🔍 Subfinder — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Уточнения к оригиналу:** в [официальной справке ProjectDiscovery](https://docs.projectdiscovery.io/opensource/subfinder/usage) -recursive выбирает источники, способные обрабатывать вложенные поддомены; это не обещание полного рекурсивного обхода DNS. -t управляет параллельным разрешением имён только при -active, а -nW включает активную проверку. -cs добавляет источники в JSON-вывод, а не гарантирует их отображение в обычном тексте.

> **Форматы и версии:** -oJ выдаёт JSONL, а -oD задаёт каталог вывода для -dL, не CSV. Параметры -oJL и -exclude-subdomains не перечислены в приведённой официальной справке. Для -proxy документирован HTTP-прокси; применимость SOCKS-примера зависит от реализации. Команды и YAML исходника оставлены буквально. Состав источников, API, тарифы и сравнительные оценки таблиц отражают оригинал; проверяйте требования выбранной версии. Показанные конвейеры с httpx/nuclei могут обращаться к целям и не превращают найденный адрес в подтверждённую уязвимость.

> **Подпись ASCII-эмблемы:** Fast Subdomain Discovery Tool — быстрый инструмент поиска поддоменов. Рисунок сохранён.


```
  ____        _     __ _           _           
 / ___| _   _| |__ / _(_)_ __   __| | ___ _ __ 
 \___ \| | | | '_ \ |_| | '_ \ / _` |/ _ \ '__|
  ___) | |_| | |_) |  _| | | | | (_| |  __/ |   
 |____/ \__,_|_.__/|_| |_|_| |_|\__,_|\___|_|   
                                                
   Fast Subdomain Discovery Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Subfinder-Subdomain_Discovery-red?style=for-the-badge" alt="Subfinder">
  <img src="https://img.shields.io/badge/Passive-Recon-orange?style=for-the-badge" alt="Passive">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🌐 Быстрый инструмент пассивного поиска поддоменов от ProjectDiscovery</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Установка](#-installation)
- [Основной синтаксис](#-basic-syntax)
- [Поиск поддоменов](#-subdomain-discovery)
- [Источники данных](#-data-sources)
- [Настройка API](#-api-configuration)
- [Параметры вывода](#-output-options)
- [Дополнительные возможности](#-advanced-features)
- [Процесс работы в Bug Bounty](#-bug-bounty-workflow)
- [Практические примеры](#-real-world-examples)
- [Краткий справочник](#-quick-reference)
- [Советы и рекомендуемые практики](#-tips--best-practices)
- [Полезные материалы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

**Subfinder** — быстрый инструмент пассивного поиска поддоменов по нескольким источникам, включая поисковые системы, сайты публикации текстовых фрагментов и платформы разведки.

<a id="key-features"></a>
### Основные возможности

| Возможность | Описание |
| --------- | ------------- |
| **Быстрый** | Написан на Go, поддерживает высокую параллельность |
| **Пассивный** | Без прямого взаимодействия с целью |
| **Несколько источников** | Более 40 источников данных |
| **Интеграция с API** | Поддержка источников с API-ключами |
| **Удобен в конвейерах** | Работает совместно с другими инструментами |
| **Вывод JSON** | Поддержка структурированного вывода |

<a id="subfinder-vs-other-tools"></a>
### Subfinder и другие инструменты

| Возможность | Subfinder | Amass | Assetfinder |
| --------- | ----------- | ------- | ------------- |
| **Скорость** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| **Источники** | 40+ | 50+ | 10+ |
| **Активное сканирование** | ❌ Нет | ✅ Да | ❌ Нет |
| **Поддержка API** | ✅ Да | ✅ Да | ❌ Нет |
| **Удобство использования** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |

---

<a id="-installation"></a>
## 📥 Установка

<a id="using-go"></a>
### С помощью Go
```bash
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
```

<a id="kali-linux"></a>
### Kali Linux
```bash
sudo apt install subfinder
```

<a id="binary-download"></a>
### Скачивание готового исполняемого файла
```bash
# Скачать из раздела Releases
https://github.com/projectdiscovery/subfinder/releases

# Linux
wget https://github.com/projectdiscovery/subfinder/releases/download/v2.x.x/subfinder_2.x.x_linux_amd64.zip
unzip subfinder_2.x.x_linux_amd64.zip
sudo mv subfinder /usr/local/bin/
```

<a id="docker"></a>
### Docker
```bash
docker pull projectdiscovery/subfinder:latest
docker run projectdiscovery/subfinder -d example.com
```

<a id="verify-installation"></a>
### Проверка установки
```bash
subfinder -version
subfinder -h
```

---

<a id="-basic-syntax"></a>
## ⌨️ Основной синтаксис

<a id="general-syntax"></a>
### Общий синтаксис
```bash
subfinder [options] -d <domain>
```

<a id="essential-options"></a>
### Основные параметры

| Параметр | Описание |
| -------- | ------------- |
| `-d` | Целевой домен |
| `-dL` | Файл со списком доменов |
| `-o` | Файл вывода |
| `-oJ` | Вывод JSON |
| `-silent` | Тихий режим (только поддомены) |
| `-v` | Подробный вывод |
| `-t` | Число потоков |
| `-all` | Использовать все источники |

<a id="quick-start"></a>
### Быстрый старт
```bash
# Базовый поиск
subfinder -d example.com

# Тихий вывод (чистый список)
subfinder -d example.com -silent

# Сохранить в файл
subfinder -d example.com -o subdomains.txt

# Несколько доменов
subfinder -dL domains.txt -o all-subs.txt
```

---

<a id="-subdomain-discovery"></a>
## 🔍 Поиск поддоменов

<a id="single-domain"></a>
### Один домен

```bash
# Базовый поиск
subfinder -d target.com

# Подробный вывод
subfinder -d target.com -v

# Тихий режим (только поддомены)
subfinder -d target.com -silent

# Показать источники
subfinder -d target.com -cs
```

<a id="multiple-domains"></a>
### Несколько доменов

```bash
# Из файла
subfinder -dL domains.txt

# Вывод в файл
subfinder -dL domains.txt -o all_subdomains.txt

# Тихий режим для передачи по конвейеру
subfinder -dL domains.txt -silent | httpx
```

<a id="recursive-enumeration"></a>
### Рекурсивный поиск

```bash
# Включить рекурсивный режим (подпись оригинала; см. уточнение)
subfinder -d target.com -recursive

# Рекурсивный режим со всеми источниками
subfinder -d target.com -recursive -all
```

---

<a id="-data-sources"></a>
## 📊 Источники данных

<a id="free-sources-default"></a>
### Бесплатные источники (по умолчанию)

Subfinder по умолчанию использует следующие источники без API-ключа:
- Alienvault
- AnubisDB
- Crtsh
- Hackertarget
- Rapiddns
- Sublist3r
- ThreatCrowd
- URLScan
- Wayback Machine

<a id="premium-sources-api-required"></a>
### Источники, требующие API

Для использования этих источников нужны API-ключи:
- **Shodan**
- **SecurityTrails**
- **VirusTotal**
- **Censys**
- **Chaos**
- **GitHub**
- **PassiveTotal**
- **Binaryedge**
- **WhoisXML**
- **ZoomEye**

<a id="source-management"></a>
### Управление источниками

```bash
# Перечислить все источники
subfinder -ls

# Использовать только выбранные источники
subfinder -d target.com -sources crtsh,hackertarget,alienvault

# Исключить выбранные источники
subfinder -d target.com -es shodan,securitytrails

# Использовать все источники, включая медленные
subfinder -d target.com -all

# Показать, какой источник нашёл каждый поддомен
subfinder -d target.com -cs
```

---

<a id="-api-configuration"></a>
## 🔑 Настройка API

<a id="config-file-location"></a>
### Расположение файла конфигурации

```bash
# Расположение по умолчанию
~/.config/subfinder/provider-config.yaml

# Или указать свой файл
subfinder -d target.com -pc /path/to/config.yaml
```

<a id="config-file-template"></a>
### Шаблон конфигурации

```yaml
# ~/.config/subfinder/provider-config.yaml

binaryedge:
  - your_api_key_here
censys:
  - your_api_id:your_api_secret
certspotter: []
chaos:
  - your_chaos_key
chinaz: []
dnsdb: []
fofa:
  - your_email:your_key
fullhunt: []
github:
  - your_github_token
hunter:
  - your_hunter_key
intelx:
  - your_intelx_key
passivetotal:
  - your_username:your_key
securitytrails:
  - your_security_trails_key
shodan:
  - your_shodan_key
virustotal:
  - your_virustotal_key
whoisxmlapi:
  - your_whoisxml_key
zoomeye:
  - your_zoomeye_key
```

<a id="getting-api-keys"></a>
### Получение API-ключей

| Источник | Бесплатный тариф | URL |
| -------- | ----------- | ----- |
| **VirusTotal** | ✅ Да | https://www.virustotal.com |
| **SecurityTrails** | ✅ Да (с ограничениями) | https://securitytrails.com |
| **Shodan** | ✅ Да (с ограничениями) | https://shodan.io |
| **Censys** | ✅ Да | https://censys.io |
| **Chaos** | ✅ Бесплатно (с ограничениями) | https://chaos.projectdiscovery.io |
| **GitHub** | ✅ Бесплатно | https://github.com/settings/tokens |

<a id="recommended-free-setup"></a>
### Рекомендуемая бесплатная конфигурация

```yaml
# Минимальная рекомендуемая конфигурация для Bug Bounty
virustotal:
  - your_free_key
github:
  - your_github_token
chaos:
  - your_chaos_key
```

---

<a id="-output-options"></a>
## 📤 Параметры вывода

<a id="output-formats"></a>
### Форматы вывода

```bash
# Обычный текст (по умолчанию)
subfinder -d target.com -o subs.txt

# Вывод JSON
subfinder -d target.com -oJ -o subs.json

# Построчный JSON
subfinder -d target.com -oJL -o subs.jsonl

# Формат CSV (подпись оригинала; см. уточнение)
subfinder -d target.com -oD -o subs.csv
```

<a id="output-filtering"></a>
### Фильтрация вывода

```bash
# Тихий режим (чистый вывод)
subfinder -d target.com -silent

# Показать только новые поддомены, исключив известные
subfinder -d target.com -exclude-subdomains known.txt

# Без цветов
subfinder -d target.com -nc
```

<a id="piping-output"></a>
### Передача вывода по конвейеру

```bash
# Передать вывод в httpx
subfinder -d target.com -silent | httpx -silent

# Передать вывод в nuclei
subfinder -d target.com -silent | httpx -silent | nuclei

# Сохранить и обработать
subfinder -d target.com -silent | tee subs.txt | httpx
```

---

<a id="-advanced-features"></a>
## ⚡ Дополнительные возможности

<a id="threading--performance"></a>
### Параллелизм и производительность

```bash
# Задать число потоков (по умолчанию: 10)
subfinder -d target.com -t 50

# Ограничение частоты запросов
subfinder -d target.com -rate-limit 100

# Время ожидания
subfinder -d target.com -timeout 30
```

<a id="matching--filtering"></a>
### Отбор и фильтрация

```bash
# Отобрать заданные шаблоны
subfinder -d target.com -match "api,dev,stage"

# Исключить заданные шаблоны
subfinder -d target.com -filter "www,mail"
```

<a id="resolver-options"></a>
### Параметры DNS-резолверов

```bash
# Использовать свои резолверы
subfinder -d target.com -rL resolvers.txt

# Разрешать имена поддоменов
subfinder -d target.com -nW
```

<a id="proxy-support"></a>
### Поддержка прокси

```bash
# HTTP-прокси
subfinder -d target.com -proxy http://127.0.0.1:8080

# SOCKS-прокси (пример оригинала; поддержка зависит от версии)
subfinder -d target.com -proxy socks5://127.0.0.1:9050
```

---

<a id="-bug-bounty-workflow"></a>
## 🔄 Процесс работы в Bug Bounty

<a id="complete-recon-pipeline"></a>
### Полный конвейер разведки

```bash
# Шаг 1: поиск поддоменов
subfinder -d target.com -all -o subs.txt

# Шаг 2: проверка доступных узлов
cat subs.txt | httpx -o live.txt

# Шаг 3: проверка с помощью nuclei
nuclei -l live.txt -severity critical,high -o vulns.txt

# Однострочная команда
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

<a id="multi-domain-workflow"></a>
### Работа с несколькими доменами

```bash
# Найти поддомены для всех доменов программы
subfinder -dL scope.txt -all -o all-subs.txt

# Обработать результаты
cat all-subs.txt | httpx -silent | nuclei
```

<a id="continuous-monitoring"></a>
### Непрерывное наблюдение

```bash
# Сохранить исходный список
subfinder -d target.com -silent > baseline.txt

# Позже найти новые поддомены
subfinder -d target.com -silent > current.txt
diff baseline.txt current.txt | grep ">" | cut -d" " -f2 > new-subs.txt
```

<a id="integration-with-other-tools"></a>
### Интеграция с другими инструментами

```bash
# С httpx (доступные узлы)
subfinder -d target.com -silent | httpx

# С ffuf (фаззинг каталогов)
subfinder -d target.com -silent | httpx -silent | \
  xargs -I {} ffuf -u {}/FUZZ -w wordlist.txt

# С nmap (сканирование портов)
subfinder -d target.com -silent | dnsx -a -silent | nmap -iL -

# Полный конвейер
subfinder -d target.com -silent | \
  httpx -title -status-code -tech-detect -silent | \
  tee recon-results.txt
```

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-basic-enumeration"></a>
### Пример 1: базовый поиск
```bash
subfinder -d hackerone.com -silent
```

<a id="example-2-with-all-sources"></a>
### Пример 2: все источники
```bash
subfinder -d target.com -all -v
```

<a id="example-3-save-results"></a>
### Пример 3: сохранение результатов
```bash
subfinder -d target.com -o target-subs.txt
```

<a id="example-4-multiple-domains"></a>
### Пример 4: несколько доменов
```bash
echo -e "target1.com\ntarget2.com" | subfinder -silent
```

<a id="example-5-show-sources"></a>
### Пример 5: отображение источников
```bash
subfinder -d target.com -cs
```

<a id="example-6-json-output"></a>
### Пример 6: вывод JSON
```bash
subfinder -d target.com -oJ -o results.json
```

<a id="example-7-with-proxy"></a>
### Пример 7: через прокси
```bash
subfinder -d target.com -proxy http://127.0.0.1:8080
```

<a id="example-8-high-performance"></a>
### Пример 8: высокая производительность
```bash
subfinder -d target.com -all -t 100 -o subs.txt
```

<a id="example-9-full-bug-bounty-pipeline"></a>
### Пример 9: полный конвейер Bug Bounty
```bash
subfinder -d target.com -all -silent | \
  httpx -silent -status-code -title | \
  tee live-hosts.txt | \
  nuclei -severity critical,high
```

<a id="example-10-recursive-discovery"></a>
### Пример 10: рекурсивный поиск
```bash
subfinder -d target.com -recursive -all -silent
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Задача | Команда |
| ------ | --------- |
| Базовый поиск | `subfinder -d target.com` |
| Тихий вывод | `subfinder -d target.com -silent` |
| Сохранить в файл | `subfinder -d target.com -o subs.txt` |
| Все источники | `subfinder -d target.com -all` |
| Показать источники | `subfinder -d target.com -cs` |
| Несколько доменов | `subfinder -dL domains.txt` |
| Вывод JSON | `subfinder -d target.com -oJ` |
| Перечислить источники | `subfinder -ls` |

<a id="common-options"></a>
### Часто используемые параметры

| Параметр | Описание |
| -------- | ------------- |
| `-d` | Целевой домен |
| `-dL` | Файл со списком доменов |
| `-o` | Файл вывода |
| `-oJ` | Вывод JSON |
| `-silent` | Тихий режим |
| `-all` | Использовать все источники |
| `-cs` | Показать источник каждого поддомена |
| `-t` | Потоки |
| `-recursive` | Рекурсивный поиск |

<a id="common-pipelines"></a>
### Типовые конвейеры

| Конвейер | Команда |
| ---------- | --------- |
| Доступные узлы | `subfinder -d target.com -silent \| httpx` |
| Полная разведка | `subfinder -d target.com \| httpx \| nuclei` |
| Сканирование портов | `subfinder -d target.com -silent \| dnsx -a \| nmap -iL -` |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендуемые практики

<a id="bug-bounty-tips"></a>
### Советы для Bug Bounty

1. **Настройте API-ключи**
   ```bash
   # Расширение результатов с помощью API
   vim ~/.config/subfinder/provider-config.yaml
   ```

2. **Используйте все источники для расширения результатов**
   ```bash
   subfinder -d target.com -all
   ```

3. **Объединяйте с другими инструментами**
   ```bash
   subfinder -d target.com -silent | httpx | nuclei
   ```

4. **Отслеживайте новые поддомены**
   ```bash
   # Ежедневная проверка новых ресурсов
   subfinder -d target.com -silent > today.txt
   ```

<a id="performance-tips"></a>
### Советы по производительности

```bash
# Большое число потоков
subfinder -d target.com -t 100 -all

# Время ожидания медленных источников
subfinder -d target.com -timeout 60
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовая оговорка

> **ПРЕДУПРЕЖДЕНИЕ:** Subfinder выполняет пассивную разведку без прямого взаимодействия с целью. Однако следующие инструменты в конвейере могут обращаться к ней.
> 
> - ✅ Используйте в программах, где у вас есть разрешение
> - ✅ Проверяйте цели Bug Bounty в согласованных границах
> - ✅ Исследуйте собственные домены
> - ❌ Не исследуйте цели без разрешения
> - ❌ Не выходите за границы программы
> 
> **Всегда убеждайтесь, что у вас есть разрешение.**

---

<a id="-resources"></a>
## 📚 Полезные материалы

<a id="official-resources"></a>
### Официальные материалы
- [Subfinder GitHub](https://github.com/projectdiscovery/subfinder)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Документация](https://docs.projectdiscovery.io/tools/subfinder/)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Nuclei](../../Nuclei/translations/README.ru.md)
- [ffuf](../../ffuf/translations/README.ru.md)
- [Nmap](../../Nmap/translations/README.ru.md)

---

<p align="center">
  <b>🔍 Находите поддомены!</b><br>
  <i>Первый шаг разведки в Bug Bounty</i>
</p>
