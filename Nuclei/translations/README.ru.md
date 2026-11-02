<a id="-nuclei---complete-cheatsheet"></a>
# 🎯 Nuclei — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Уточнения к оригиналу:** количество шаблонов и их пути отражают исходник и зависят от выпуска. В [официальной справке Nuclei](https://github.com/projectdiscovery/nuclei/blob/dev/README.md) -ut обновляет шаблоны, а не сам движок; -json в старых примерах требует сверки с версией, в текущей справке приведены -jsonl и -json-export. Файл CVE-2023-XXXXX.yaml и v3.x.x — заполнители, а не гарантированно существующие ресурсы. Исходные команды сохранены.

> **Точность YAML-примеров:** функциональные поля и строковые значения шаблонов оставлены буквально. В Check Header оригинал дважды задаёт ключ matchers на одном уровне, хотя [ключи YAML должны быть уникальны](https://yaml.org/spec/1.2.2/#3211-nodes). Несколько проверок оформляются элементами одного списка; см. [официальное описание matchers](https://docs.projectdiscovery.io/templates/reference/matchers). Пример сохранён без исправления и не считается готовым корректным шаблоном. Код 200 в Admin Panel Detection сам по себе не подтверждает наличие административной панели, а найденное совпадение и severity требуют проверки реальных последствий.

> **Подпись ASCII-эмблемы:** Fast Template-Based Scanner — быстрый сканер на основе шаблонов. Низкая частота запросов и прокси не гарантируют незаметность; настройки производительности из оригинала не являются универсально оптимальными.


[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _            _      _ 
 | \ | |_   _  ___| | ___(_)
 |  \| | | | |/ __| |/ _ \ |
 | |\  | |_| | (__| |  __/ |
 |_| \_|\__,_|\___|_|\___|_|
                            
    Fast Template-Based Scanner
```

<p align="center">
  <img src="https://img.shields.io/badge/Nuclei-Vulnerability_Scanner-red?style=for-the-badge" alt="Nuclei">
  <img src="https://img.shields.io/badge/9000+-Templates-orange?style=for-the-badge" alt="Templates">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🔥 Популярный сканер на основе шаблонов для участников Bug Bounty</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Установка](#-installation)
- [Основной синтаксис](#-basic-syntax)
- [Категории шаблонов](#-template-categories)
- [Указание целей сканирования](#-scanning-targets)
- [Выбор шаблонов](#-template-selection)
- [Параметры вывода](#-output-options)
- [Дополнительные возможности](#-advanced-features)
- [Собственные шаблоны](#-custom-templates)
- [Процесс работы в Bug Bounty](#-bug-bounty-workflow)
- [Практические примеры](#-real-world-examples)
- [Краткий справочник](#-quick-reference)
- [Советы и рекомендуемые практики](#-tips--best-practices)
- [Полезные материалы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

**Nuclei** — быстрый сканер уязвимостей на основе шаблонов от ProjectDiscovery. Он широко используется участниками Bug Bounty и исследователями безопасности.

<a id="key-features"></a>
### Основные возможности

| Возможность | Описание |
| --------- | ------------- |
| **Более 9000 шаблонов** | CVE, панели, ошибки настройки и захват поддоменов |
| **Скорость и эффективность** | Параллельные проверки с ограничением частоты запросов |
| **Основа — шаблоны** | Шаблоны YAML для удобной настройки |
| **Участие сообщества** | Постоянно обновляемые шаблоны |
| **Несколько протоколов** | HTTP, DNS, TCP, SSL, Headless |
| **Удобная интеграция** | Совместная работа с другими инструментами (httpx, subfinder) |

<a id="template-types"></a>
### Типы шаблонов

| Тип | Описание | Количество |
| ------ | ------------- | ------- |
| **CVE** | Известные уязвимости | 2000+ |
| **Доступные панели управления** | Панели администрирования и мониторинга | 500+ |
| **Ошибки настройки** | Ошибки настройки безопасности | 300+ |
| **Стандартные учётные данные** | Стандартные учётные данные | 200+ |
| **Захват поддоменов** | Захват поддомена | 100+ |
| **Технологии** | Определение технологий | 1000+ |
| **Раскрытие конфиденциальных ресурсов** | Доступность конфиденциальных файлов | 500+ |

---

<a id="-installation"></a>
## 📥 Установка

<a id="using-go-recommended"></a>
### Установка через Go (рекомендуется)
```bash
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest
```

<a id="binary-download"></a>
### Скачивание исполняемого файла
```bash
# Скачать со страницы выпусков
https://github.com/projectdiscovery/nuclei/releases

# Linux
wget https://github.com/projectdiscovery/nuclei/releases/download/v3.x.x/nuclei_3.x.x_linux_amd64.zip
unzip nuclei_3.x.x_linux_amd64.zip
sudo mv nuclei /usr/local/bin/
```

<a id="docker"></a>
### Docker
```bash
docker pull projectdiscovery/nuclei:latest
docker run -it projectdiscovery/nuclei -h
```

<a id="kali-linux"></a>
### Kali Linux
```bash
sudo apt install nuclei
```

<a id="update-templates"></a>
### Обновление шаблонов
```bash
# Обновить Nuclei и шаблоны (подпись оригинала; см. уточнение)
nuclei -ut

# Обновить только шаблоны
nuclei -update-templates
```

<a id="verify-installation"></a>
### Проверка установки
```bash
nuclei -version
nuclei -tl              # Список шаблонов
```

---

<a id="-basic-syntax"></a>
## ⌨️ Основной синтаксис

<a id="general-syntax"></a>
### Общий синтаксис
```bash
nuclei [options] -target <url>
```

<a id="essential-options"></a>
### Основные параметры

| Параметр | Описание |
| -------- | ------------- |
| `-u` | URL одной цели |
| `-l` | Файл со списком URL |
| `-t` | Шаблоны для запуска |
| `-tags` | Шаблоны с указанными тегами |
| `-severity` | Фильтр по критичности |
| `-o` | Выходной файл |
| `-silent` | Минимальный вывод |
| `-v` | Подробный вывод |

<a id="quick-start"></a>
### Быстрый старт
```bash
# Проверить одну цель
nuclei -u https://example.com

# Проверить список целей
nuclei -l urls.txt

# Проверить с выбранными шаблонами
nuclei -u https://example.com -t cves/

# Проверить по тегам
nuclei -u https://example.com -tags cve,rce
```

---

<a id="-template-categories"></a>
## 📂 Категории шаблонов

<a id="cve-templates"></a>
### Шаблоны CVE

```bash
# Все шаблоны CVE
nuclei -u target.com -t cves/

# CVE определённого года
nuclei -u target.com -t cves/2024/
nuclei -u target.com -t cves/2023/

# Только критические CVE
nuclei -u target.com -t cves/ -severity critical

# Определённая CVE
nuclei -u target.com -t cves/2023/CVE-2023-XXXXX.yaml
```

<a id="exposed-panels"></a>
### Доступные панели управления

```bash
# Все проверки панелей
nuclei -u target.com -t exposed-panels/

# Определённые панели
nuclei -u target.com -tags phpmyadmin
nuclei -u target.com -tags jenkins
nuclei -u target.com -tags grafana
nuclei -u target.com -tags kibana
```

<a id="misconfigurations"></a>
### Ошибки настройки

```bash
# Все ошибки настройки
nuclei -u target.com -t misconfiguration/

# Определённые типы
nuclei -u target.com -tags cors
nuclei -u target.com -tags headers
nuclei -u target.com -tags http-missing-security-headers
```

<a id="default-logins"></a>
### Стандартные учётные данные

```bash
# Все проверки стандартных учётных данных
nuclei -u target.com -t default-logins/

# Определённые поставщики
nuclei -u target.com -tags tomcat-default
nuclei -u target.com -tags apache-default
```

<a id="subdomain-takeover"></a>
### Захват поддомена

```bash
# Все проверки захвата поддоменов
nuclei -u target.com -t takeovers/

# Определённые провайдеры
nuclei -u target.com -tags aws-takeover
nuclei -u target.com -tags azure-takeover
nuclei -u target.com -tags github-takeover
```

<a id="technology-detection"></a>
### Определение технологий

```bash
# Определение технологий
nuclei -u target.com -t technologies/

# Определение CMS
nuclei -u target.com -tags tech,wordpress
nuclei -u target.com -tags tech,joomla
```

<a id="exposures"></a>
### Раскрытие конфиденциальных ресурсов

```bash
# Доступность конфиденциальных файлов
nuclei -u target.com -t exposures/

# Определённые виды раскрытия ресурсов
nuclei -u target.com -tags config
nuclei -u target.com -tags backup
nuclei -u target.com -tags logs
nuclei -u target.com -tags git
```

---

<a id="-scanning-targets"></a>
## 🎯 Указание целей сканирования

<a id="single-target"></a>
### Одна цель

```bash
nuclei -u https://example.com
nuclei -u https://example.com:8080
```

<a id="multiple-targets"></a>
### Несколько целей

```bash
# Из файла
nuclei -l targets.txt

# Из стандартного ввода через конвейер
cat targets.txt | nuclei
echo "https://example.com" | nuclei

# Несколько URL в строке команды
nuclei -u https://site1.com -u https://site2.com
```

<a id="with-other-tools"></a>
### Совместно с другими инструментами

```bash
# С subfinder (поддомены)
subfinder -d example.com | nuclei

# С httpx (доступные узлы)
subfinder -d example.com | httpx | nuclei

# С katana (URL, найденные обходом)
katana -u https://example.com | nuclei

# Полная цепочка разведки
subfinder -d example.com -silent | httpx -silent | nuclei -silent
```

---

<a id="-template-selection"></a>
## 🏷️ Выбор шаблонов

<a id="by-template-path"></a>
### По пути к шаблону

```bash
# Один шаблон
nuclei -u target.com -t cves/2023/CVE-2023-1234.yaml

# Каталог шаблонов
nuclei -u target.com -t cves/2023/
nuclei -u target.com -t exposed-panels/

# Несколько каталогов
nuclei -u target.com -t cves/ -t exposed-panels/

# Путь к собственным шаблонам
nuclei -u target.com -t /path/to/custom/templates/
```

<a id="by-tags"></a>
### По тегам

```bash
# Один тег
nuclei -u target.com -tags cve

# Несколько тегов (ИЛИ)
nuclei -u target.com -tags cve,rce,sqli

# Распространённые теги
nuclei -u target.com -tags xss
nuclei -u target.com -tags ssrf
nuclei -u target.com -tags lfi
nuclei -u target.com -tags rce
nuclei -u target.com -tags injection
```

<a id="by-severity"></a>
### По критичности

```bash
# Один уровень критичности
nuclei -u target.com -severity critical
nuclei -u target.com -severity high
nuclei -u target.com -severity medium
nuclei -u target.com -severity low
nuclei -u target.com -severity info

# Несколько уровней критичности
nuclei -u target.com -severity critical,high

# Исключить уровни критичности
nuclei -u target.com -exclude-severity info,low
```

<a id="exclude-templates"></a>
### Исключение шаблонов

```bash
# Исключить определённые шаблоны
nuclei -u target.com -exclude-templates cves/2020/

# Исключить по тегам
nuclei -u target.com -exclude-tags dos,fuzz

# Исключить по идентификатору
nuclei -u target.com -exclude-id CVE-2021-1234
```

---

<a id="-output-options"></a>
## 📤 Параметры вывода

<a id="output-formats"></a>
### Форматы вывода

```bash
# Стандартный вывод
nuclei -u target.com -o results.txt

# Вывод JSON
nuclei -u target.com -json -o results.json

# JSON Lines
nuclei -u target.com -jsonl -o results.jsonl

# Markdown
nuclei -u target.com -markdown-export report/
```

<a id="output-filtering"></a>
### Фильтрация вывода

```bash
# Минимальный вывод (только результаты)
nuclei -u target.com -silent

# Без цветного оформления
nuclei -u target.com -nc

# Подробный вывод
nuclei -u target.com -v

# Отладка
nuclei -u target.com -debug
```

<a id="export-options"></a>
### Экспорт результатов

```bash
# SARIF (GitHub)
nuclei -u target.com -sarif-export results.sarif

# Отчёт Markdown
nuclei -u target.com -me reports/
```

---

<a id="-advanced-features"></a>
## ⚡ Дополнительные возможности

<a id="rate-limiting"></a>
### Ограничение частоты запросов

```bash
# Запросов в секунду
nuclei -u target.com -rate-limit 100

# Число целей в пакете
nuclei -u target.com -bulk-size 25

# Число параллельных задач
nuclei -u target.com -c 50

# Параллельная обработка шаблонов
nuclei -u target.com -rl 100 -c 50
```

<a id="proxy-support"></a>
### Поддержка прокси

```bash
# HTTP-прокси
nuclei -u target.com -proxy http://127.0.0.1:8080

# SOCKS-прокси
nuclei -u target.com -proxy socks5://127.0.0.1:9050

# Прокси Burp Suite
nuclei -u target.com -proxy http://127.0.0.1:8080
```

<a id="authentication"></a>
### Аутентификация

```bash
# Аутентификация через заголовок
nuclei -u target.com -H "Authorization: Bearer token123"

# Аутентификация через cookie
nuclei -u target.com -H "Cookie: session=abc123"

# Несколько заголовков
nuclei -u target.com -H "Authorization: Bearer token" -H "X-Custom: value"
```

<a id="interactsh-integration"></a>
### Интеграция Interactsh

```bash
# Использовать Interactsh для внеполосных проверок (OOB)
nuclei -u target.com -interactsh-server https://interact.sh

# Собственный сервер Interactsh
nuclei -u target.com -iserver https://my-interactsh.com
```

<a id="headless-mode"></a>
### Браузерный режим headless

```bash
# Включить браузер без графического интерфейса
nuclei -u target.com -headless

# Только шаблоны headless
nuclei -u target.com -t headless/
```

<a id="workflows"></a>
### Сценарии работы (workflows)

```bash
# Запустить сценарий workflow
nuclei -u target.com -w workflows/wordpress-workflow.yaml

# Перечислить сценарии workflow
ls ~/.local/nuclei-templates/workflows/
```

---

<a id="-custom-templates"></a>
## 📝 Собственные шаблоны

<a id="template-structure"></a>
### Структура шаблона

```yaml
id: my-custom-template

info:
  name: My Custom Template
  author: your-name
  severity: medium
  description: Description of what this template detects
  tags: custom,web

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
    matchers:
      - type: word
        words:
          - "Admin Panel"
```

<a id="template-examples"></a>
### Примеры шаблонов

<a id="detect-specific-page"></a>
#### Определение конкретной страницы
```yaml
id: admin-panel-detect

info:
  name: Admin Panel Detection
  author: hunter
  severity: info
  tags: panel

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
      - "{{BaseURL}}/administrator"
      - "{{BaseURL}}/wp-admin"
    matchers-condition: or
    matchers:
      - type: status
        status:
          - 200
```

<a id="check-header"></a>
#### Проверка заголовка
```yaml
id: missing-security-header

info:
  name: Missing X-Frame-Options
  author: hunter
  severity: low
  tags: headers

http:
  - method: GET
    path:
      - "{{BaseURL}}"
    matchers:
      - type: word
        words:
          - "text/html"
        part: header
    matchers:
      - type: word
        negative: true
        words:
          - "X-Frame-Options"
        part: header
```

<a id="validate-template"></a>
### Проверка шаблона

```bash
# Проверить синтаксис шаблона
nuclei -validate -t my-template.yaml

# Проверить шаблон
nuclei -u target.com -t my-template.yaml -debug
```

---

<a id="-bug-bounty-workflow"></a>
## 🔄 Процесс работы в Bug Bounty

<a id="complete-recon-pipeline"></a>
### Полная цепочка разведки

```bash
# Шаг 1: поиск поддоменов
subfinder -d target.com -o subs.txt

# Шаг 2: определить доступные узлы
cat subs.txt | httpx -o live.txt

# Шаг 3: запустить Nuclei
nuclei -l live.txt -severity critical,high -o findings.txt

# Однострочная команда
subfinder -d target.com -silent | httpx -silent | nuclei -severity critical,high
```

<a id="priority-scanning"></a>
### Проверка по приоритетам

```bash
# Этап 1: только critical (быстро)
nuclei -l targets.txt -severity critical -o critical.txt

# Этап 2: высокий уровень критичности
nuclei -l targets.txt -severity high -o high.txt

# Этап 3: CVE
nuclei -l targets.txt -t cves/ -o cves.txt

# Этап 4: полная проверка
nuclei -l targets.txt -o all.txt
```

<a id="targeted-scanning"></a>
### Целевые проверки

```bash
# Цели WordPress
nuclei -l wp-sites.txt -tags wordpress

# Конечные точки API
nuclei -l apis.txt -tags api

# Страницы входа
nuclei -l logins.txt -tags login,default-login
```

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-full-scan"></a>
### Пример 1: полная проверка
```bash
nuclei -u https://target.com -o results.txt
```

<a id="example-2-critical-cves-only"></a>
### Пример 2: только критические CVE
```bash
nuclei -u https://target.com -t cves/ -severity critical,high
```

<a id="example-3-bug-bounty-quick-scan"></a>
### Пример 3: быстрая проверка для Bug Bounty
```bash
nuclei -u https://target.com -tags cve,rce,sqli,xss,ssrf -severity critical,high
```

<a id="example-4-subdomain-takeover-check"></a>
### Пример 4: проверка захвата поддоменов
```bash
cat subs.txt | nuclei -t takeovers/
```

<a id="example-5-technology-detection"></a>
### Пример 5: определение технологий
```bash
nuclei -u https://target.com -t technologies/ -silent
```

<a id="example-6-with-proxy-burp"></a>
### Пример 6: через прокси Burp
```bash
nuclei -u https://target.com -proxy http://127.0.0.1:8080
```

<a id="example-7-authenticated-scan"></a>
### Пример 7: проверка с аутентификацией
```bash
nuclei -u https://target.com -H "Authorization: Bearer eyJhbG..."
```

<a id="example-8-rate-limited-scan"></a>
### Пример 8: ограничение частоты запросов
```bash
nuclei -l targets.txt -rate-limit 10 -c 5
```

<a id="example-9-json-output-for-processing"></a>
### Пример 9: JSON для обработки результатов
```bash
nuclei -u https://target.com -json -o results.json
```

<a id="example-10-complete-pipeline"></a>
### Пример 10: полная цепочка обработки
```bash
subfinder -d target.com -silent | \
httpx -silent | \
nuclei -severity critical,high -json -o results.json
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Задача | Команда |
| ------ | --------- |
| Базовая проверка | `nuclei -u target.com` |
| С выбранными шаблонами | `nuclei -u target.com -t cves/` |
| По тегам | `nuclei -u target.com -tags cve,rce` |
| По критичности | `nuclei -u target.com -severity critical` |
| По списку целей | `nuclei -l urls.txt` |
| Обновить шаблоны | `nuclei -ut` |
| Минимальный вывод | `nuclei -u target.com -silent` |
| Вывод JSON | `nuclei -u target.com -json -o out.json` |

<a id="common-tags"></a>
### Распространённые теги

| Тег | Описание |
| ----- | ------------- |
| `cve` | Уязвимости CVE |
| `rce` | Удалённое выполнение кода |
| `sqli` | SQL-инъекция |
| `xss` | Межсайтовый скриптинг |
| `ssrf` | Подделка запросов на стороне сервера |
| `lfi` | Локальное включение файлов |
| `panel` | Панели администрирования |
| `takeover` | Захват поддомена |

<a id="severity-levels"></a>
### Уровни критичности

| Критичность | Цвет | Приоритет |
| ---------- | ------- | ---------- |
| `critical` | 🔴 Красный | Немедленный |
| `high` | 🟠 Оранжевый | Высокий |
| `medium` | 🟡 Жёлтый | Средний |
| `low` | 🟢 Зелёный | Низкий |
| `info` | 🔵 Синий | Информационный |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендуемые практики

<a id="performance-tips"></a>
### Советы по производительности

```bash
# Пример настроек для крупных проверок
nuclei -l targets.txt -c 50 -rl 150 -bs 25

# Минимальный вывод для конвейеров
nuclei -l targets.txt -silent -o results.txt
```

<a id="bug-bounty-tips"></a>
### Советы для Bug Bounty

1. **Начинать с Critical/High**
   ```bash
   nuclei -l targets.txt -severity critical,high
   ```

2. **Проверять очевидные возможности**
   ```bash
   nuclei -l targets.txt -tags takeover,exposed-panels
   ```

3. **Использовать обновлённые шаблоны**
   ```bash
   nuclei -ut  # Обновлять перед поиском уязвимостей
   ```

4. **Ограничивать частоту запросов для стабильности**
   ```bash
   nuclei -l targets.txt -rl 50
   ```

<a id="stealth-tips"></a>
### Снижение заметности

```bash
# Низкая частота запросов и минимум вывода
nuclei -l targets.txt -rl 10 -c 2

# Через прокси
nuclei -l targets.txt -proxy socks5://127.0.0.1:9050
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовая оговорка

> **ПРЕДУПРЕЖДЕНИЕ:** Nuclei — мощный сканер уязвимостей, который следует использовать только **для разрешённого тестирования**.
> 
> - ✅ Проверяйте программы, для которых получено разрешение
> - ✅ Используйте в разрешённых программах Bug Bounty
> - ✅ Проверяйте собственные системы
> - ❌ Не сканируйте без разрешения
> - ❌ Не нарушайте ограничения частоты запросов и границы проверки
> 
> **Несанкционированное сканирование незаконно и неэтично.**

---

<a id="-resources"></a>
## 📚 Полезные материалы

<a id="official-resources"></a>
### Официальные материалы
- [Nuclei на GitHub](https://github.com/projectdiscovery/nuclei)
- [Шаблоны Nuclei](https://github.com/projectdiscovery/nuclei-templates)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Документация](https://docs.projectdiscovery.io/tools/nuclei/)

<a id="template-resources"></a>
### Материалы по шаблонам
- [Шаблоны сообщества](https://github.com/projectdiscovery/nuclei-templates)
- [Руководство по шаблонам](https://docs.projectdiscovery.io/templates/)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Nmap](../../Nmap/translations/README.ru.md)
- [Burp Suite](../../Burp-Suite/translations/README.ru.md)
- [SQLMap](../../SQLMap/translations/README.ru.md)

---

<p align="center">
  <b>🎯 Освойте поиск уязвимостей!</b><br>
  <i>Полезный помощник участника Bug Bounty</i>
</p>
