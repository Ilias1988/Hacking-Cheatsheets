<a id="-httpx---complete-cheatsheet"></a>
# 🌐 httpx — полный справочник

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

```
  _     _   _           
 | |__ | |_| |_ _ __ __  __
 | '_ \| __| __| '_ \\ \/ /
 | | | | |_| |_| |_) |>  < 
 |_| |_|\__|\__| .__//_/\_\
               |_|         
                           
   Fast HTTP Probe & Toolkit
```

<p align="center">
  <img src="https://img.shields.io/badge/httpx-HTTP_Probe-red?style=for-the-badge" alt="httpx">
  <img src="https://img.shields.io/badge/Fast-Toolkit-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>⚡ Быстрый многофункциональный набор инструментов для HTTP от ProjectDiscovery</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Установка](#-installation)
- [Базовый синтаксис](#-basic-syntax)
- [Проверка доступности и обнаружение](#-probing--discovery)
- [Извлечение информации](#-information-extraction)
- [Фильтрация и отбор](#-filtering--matching)
- [Параметры вывода](#-output-options)
- [Расширенные возможности](#-advanced-features)
- [Рабочий процесс Bug Bounty](#-bug-bounty-workflow)
- [Практические примеры](#-real-world-examples)
- [Краткий справочник](#-quick-reference)
- [Советы и рекомендации](#-tips--best-practices)
- [Ресурсы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

**httpx** — быстрый многофункциональный набор инструментов для HTTP, который выполняет разные проверки с помощью библиотеки retryablehttp. Он предназначен для исследователей безопасности и участников программ поиска уязвимостей за вознаграждение (Bug Bounty).

> **Уточнения к оригиналу:** здесь описан [httpx от ProjectDiscovery](https://github.com/projectdiscovery/httpx), инструмент на Go, а не одноимённая Python-библиотека. Надпись в ASCII-логотипе означает «быстрый набор инструментов для проверки HTTP». Сравнительная таблица — упрощённая оценка оригинала, а не измерение производительности. `v1.x.x` в ссылке загрузки — заполнитель версии.
>
> В [официальной справке](https://docs.projectdiscovery.io/opensource/httpx/usage) `-method` показывает метод запроса, а методы для проверки задаёт `-x`; `-fr` следует перенаправлениям, тогда как regex-фильтр обозначается `-fe`. `-include-response-header` добавляет заголовки в JSON-вывод и не выбирает их по списку из аргумента; `-json` записывает JSONL. `-tech-detect` — полное имя `-td`, а не отдельный подробный режим. `-no-fallback` выводит результаты для обоих протоколов; `-tls-probe` проверяет домены из сертификата и отличается от `-tls-grab`. Параметры `-mt`, `-ft`, `-screenshot-output`, `-ssd`, `-resume-file`, `-tlsgrab`, `-jsonl` и `-response-header` отсутствуют в приведённой справке: сверяйте их с установленной версией. Исходные команды сохранены дословно.

[← Русское оглавление репозитория](../../README.ru.md)

<a id="key-features"></a>
### Основные возможности

| Возможность | Описание |
|---------|-------------|
| **Скорость** | Инструмент на Go с большим числом параллельных запросов |
| **Разные проверки** | Код состояния, заголовок страницы, технологии, HTTP-заголовки |
| **Определение технологий** | Определение на основе Wappalyzer |
| **Снимки экрана** | Снимки экрана через браузер без графического интерфейса (headless) |
| **Цепочки команд** | Совместная работа с subfinder и nuclei |
| **Вывод JSON** | Поддержка структурированного вывода |

<a id="httpx-vs-other-tools"></a>
### Сравнение httpx с другими инструментами

| Возможность | httpx | curl | wget |
|---------|-------|------|------|
| **Скорость** | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐ |
| **Параллельная работа** | ✅ Да | ❌ Нет | ❌ Нет |
| **Определение технологий** | ✅ Да | ❌ Нет | ❌ Нет |
| **Снимки экрана** | ✅ Да | ❌ Нет | ❌ Нет |
| **Цепочки команд** | ✅ Да | ⚠️ Вручную | ⚠️ Вручную |

---

<a id="-installation"></a>
## 📥 Установка

<a id="using-go"></a>
### С помощью Go
```bash
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest
```

<a id="kali-linux"></a>
### Kali Linux
```bash
sudo apt install httpx-toolkit
```

<a id="binary-download"></a>
### Загрузка готового исполняемого файла
```bash
# Загрузите из раздела Releases
https://github.com/projectdiscovery/httpx/releases

# Linux
wget https://github.com/projectdiscovery/httpx/releases/download/v1.x.x/httpx_1.x.x_linux_amd64.zip
unzip httpx_1.x.x_linux_amd64.zip
sudo mv httpx /usr/local/bin/
```

<a id="docker"></a>
### Docker
```bash
docker pull projectdiscovery/httpx:latest
docker run -it projectdiscovery/httpx -h
```

<a id="verify-installation"></a>
### Проверка установки
```bash
httpx -version
httpx -h
```

---

<a id="-basic-syntax"></a>
## ⌨️ Базовый синтаксис

<a id="general-syntax"></a>
### Общий синтаксис
```bash
httpx [options] -u <url>
httpx [options] -l <file>
cat urls.txt | httpx [options]
```

<a id="essential-options"></a>
### Основные параметры

| Параметр | Описание |
|--------|-------------|
| `-u` | URL одной цели |
| `-l` | Файл со списком URL |
| `-sc` | Показать код состояния |
| `-title` | Показать заголовок страницы |
| `-td` | Определение технологий |
| `-o` | Файл вывода |
| `-silent` | Режим минимального вывода |
| `-json` | Вывод JSON |

<a id="quick-start"></a>
### Быстрый старт
```bash
# Проверка одного URL
httpx -u https://example.com

# Проверка адресов из файла
httpx -l urls.txt

# Из стандартного ввода (цепочка команд)
cat hosts.txt | httpx

# Совместно с subfinder
subfinder -d example.com -silent | httpx
```

---

<a id="-probing--discovery"></a>
## 🔍 Проверка доступности и обнаружение

<a id="basic-probing"></a>
### Базовая проверка доступности

```bash
# Проверить узлы по HTTP/HTTPS
cat hosts.txt | httpx

# Добавить код состояния
cat hosts.txt | httpx -sc

# Проверить указанные порты
cat hosts.txt | httpx -p 80,443,8080,8443

# Проверить перечисленные распространённые порты
cat hosts.txt | httpx -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```

<a id="port-scanning"></a>
### Проверка портов

```bash
# Заданные порты
httpx -l hosts.txt -p 80,443,8080,8443

# Диапазон портов
httpx -l hosts.txt -p 80-100

# Распространённые веб-порты
httpx -l hosts.txt -p http:80,https:443
```

<a id="protocol-detection"></a>
### Определение протокола

```bash
# Проверка HTTP/HTTPS
cat hosts.txt | httpx

# Следовать перенаправлениям
cat hosts.txt | httpx -follow-redirects

# Максимальное число перенаправлений
cat hosts.txt | httpx -follow-redirects -max-redirects 5

# Параметр no-fallback (см. уточнение к оригиналу)
cat hosts.txt | httpx -no-fallback
```

---

<a id="-information-extraction"></a>
## 📊 Извлечение информации

<a id="status-codes--titles"></a>
### Коды состояния и заголовки страниц

```bash
# Код состояния
httpx -l urls.txt -sc

# Заголовок страницы
httpx -l urls.txt -title

# Код состояния и заголовок страницы
httpx -l urls.txt -sc -title

# Размер содержимого
httpx -l urls.txt -cl

# Веб-сервер
httpx -l urls.txt -server

# Все основные сведения
httpx -l urls.txt -sc -title -cl -server
```

<a id="technology-detection"></a>
### Определение технологий

```bash
# Определить технологии
httpx -l urls.txt -td

# Определение технологий (подробный вариант по оригиналу)
httpx -l urls.txt -tech-detect

# Вместе с другими сведениями
httpx -l urls.txt -sc -title -td
```

<a id="headers--response"></a>
### Заголовки и ответ

```bash
# Заголовки ответа
httpx -l urls.txt -response-header

# Определённый заголовок (см. уточнение к оригиналу)
httpx -l urls.txt -include-response-header "Server,X-Powered-By"

# Хеш тела ответа
httpx -l urls.txt -hash md5

# Предварительный просмотр тела ответа
httpx -l urls.txt -bp
```

<a id="ip--dns-information"></a>
### Сведения об IP и DNS

```bash
# IP-адрес
httpx -l urls.txt -ip

# CNAME
httpx -l urls.txt -cname

# Определение CDN
httpx -l urls.txt -cdn

# Сведения об автономной системе (ASN)
httpx -l urls.txt -asn

# Все сведения о DNS
httpx -l urls.txt -ip -cname -cdn -asn
```

<a id="ssltls-information"></a>
### Сведения об SSL/TLS

```bash
# Проверка доменов из TLS-сертификата
httpx -l urls.txt -tls-probe

# Получение данных TLS
httpx -l urls.txt -tls-grab

# Сведения о сертификате
httpx -l urls.txt -tlsgrab
```

<a id="favicon-hash"></a>
### Хеш значка сайта (favicon)

```bash
# Получить хеш значка сайта (полезен для поиска в Shodan)
httpx -l urls.txt -favicon
```

<a id="screenshot-capture"></a>
### Создание снимков экрана

```bash
# Создать снимки экрана
httpx -l urls.txt -screenshot

# Снимки экрана с каталогом сохранения
httpx -l urls.txt -screenshot -screenshot-output ./screenshots/

# Параметры браузера без графического интерфейса (headless)
httpx -l urls.txt -screenshot -system-chrome
```

---

<a id="-filtering--matching"></a>
## 🎯 Фильтрация и отбор

<a id="filter-by-status-code"></a>
### Отбор по коду состояния

```bash
# Оставить указанные коды состояния
httpx -l urls.txt -mc 200,301,302

# Исключить указанные коды состояния
httpx -l urls.txt -fc 404,403,500

# Отобрать все коды и исключить указанный
httpx -l urls.txt -mc all -fc 404
```

<a id="filter-by-size"></a>
### Отбор по размеру

```bash
# Отобрать по размеру содержимого
httpx -l urls.txt -ml 1234

# Исключить по размеру содержимого
httpx -l urls.txt -fl 0

# Отобрать по числу строк
httpx -l urls.txt -mlc 100

# Исключить по числу строк
httpx -l urls.txt -flc 50
```

<a id="filter-by-content"></a>
### Отбор по содержимому

```bash
# Отобрать по регулярному выражению в теле ответа
httpx -l urls.txt -mr "admin|login"

# Исключить по регулярному выражению в теле ответа (см. уточнение)
httpx -l urls.txt -fr "error|not found"

# Отобрать по строке
httpx -l urls.txt -ms "dashboard"
```

<a id="filter-by-technology"></a>
### Отбор по технологии

```bash
# Отобрать по технологии
httpx -l urls.txt -td -mt "WordPress,Nginx"

# Исключить по технологии
httpx -l urls.txt -td -ft "Apache"
```

---

<a id="-output-options"></a>
## 📤 Параметры вывода

<a id="output-formats"></a>
### Форматы вывода

```bash
# Обычный текст
httpx -l urls.txt -o results.txt

# Вывод JSON
httpx -l urls.txt -json -o results.json

# JSON Lines (по объекту на строку)
httpx -l urls.txt -jsonl -o results.jsonl

# Вывод CSV
httpx -l urls.txt -csv -o results.csv
```

<a id="output-fields"></a>
### Поля вывода

```bash
# Сохранить ответ
httpx -l urls.txt -sr -srd ./responses/

# Сохранить снимки экрана
httpx -l urls.txt -screenshot -ssd ./screenshots/

# Настроенный вывод
httpx -l urls.txt -o output.txt -sc -title -td
```

<a id="silent--verbose"></a>
### Минимальный и подробный вывод

```bash
# Минимальный вывод (только URL)
httpx -l urls.txt -silent

# Подробный вывод
httpx -l urls.txt -v

# Отладочный вывод
httpx -l urls.txt -debug

# Без цветового оформления
httpx -l urls.txt -nc
```

---

<a id="-advanced-features"></a>
## ⚡ Расширенные возможности

<a id="threading--rate-limiting"></a>
### Потоки и ограничение частоты запросов

```bash
# Задать число потоков (по умолчанию: 50)
httpx -l urls.txt -threads 100

# Ограничить частоту запросов
httpx -l urls.txt -rate-limit 100

# Тайм-аут
httpx -l urls.txt -timeout 10

# Повторные попытки
httpx -l urls.txt -retries 3
```

<a id="proxy-support"></a>
### Поддержка прокси

```bash
# HTTP-прокси
httpx -l urls.txt -proxy http://127.0.0.1:8080

# SOCKS-прокси
httpx -l urls.txt -proxy socks5://127.0.0.1:9050

# Burp Suite
httpx -l urls.txt -proxy http://127.0.0.1:8080
```

<a id="custom-headers"></a>
### Пользовательские заголовки

```bash
# Пользовательский заголовок
httpx -l urls.txt -H "Authorization: Bearer token123"

# Несколько заголовков
httpx -l urls.txt -H "X-Custom: value" -H "Cookie: session=abc"

# User-Agent
httpx -l urls.txt -H "User-Agent: Mozilla/5.0"
```

<a id="request-methods"></a>
### Методы запросов

```bash
# Запрос HEAD (быстрее по оригиналу; см. уточнение)
httpx -l urls.txt -method HEAD

# Пользовательский метод
httpx -l urls.txt -method OPTIONS

# POST с телом запроса
httpx -l urls.txt -method POST -body '{"test":"data"}'
```

<a id="resume--pagination"></a>
### Возобновление и постраничная обработка

```bash
# Возобновить с последней позиции
httpx -l urls.txt -resume

# Сохранить прогресс
httpx -l urls.txt -resume -resume-file progress.txt
```

---

<a id="-bug-bounty-workflow"></a>
## 🔄 Рабочий процесс Bug Bounty

<a id="complete-recon-pipeline"></a>
### Полная цепочка разведки

```bash
# Шаг 1: перечисление поддоменов
subfinder -d target.com -o subs.txt

# Шаг 2: проверка доступности узлов с помощью httpx
cat subs.txt | httpx -sc -title -td -o live.txt

# Шаг 3: сканирование уязвимостей
nuclei -l live.txt -severity critical,high

# Цепочка в одну строку
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

<a id="detailed-reconnaissance"></a>
### Подробная разведка

```bash
# Полный сбор сведений
subfinder -d target.com -silent | httpx \
    -sc \
    -title \
    -td \
    -ip \
    -cdn \
    -server \
    -follow-redirects \
    -o full-recon.txt
```

<a id="screenshot-all-live-hosts"></a>
### Снимки экрана всех доступных узлов

```bash
subfinder -d target.com -silent | httpx -silent -screenshot -ssd ./screenshots/
```

<a id="find-interesting-endpoints"></a>
### Поиск интересных конечных точек

```bash
# Найти панели администрирования
subfinder -d target.com -silent | httpx -title -mr "admin|login|dashboard"

# Найти конечные точки API
cat subs.txt | httpx -path /api/ -mc 200,401,403

# Проверить указанные пути
httpx -l hosts.txt -path /robots.txt -mc 200
```

<a id="technology-specific-hunting"></a>
### Поиск с учётом технологии

```bash
# Найти сайты на WordPress
subfinder -d target.com -silent | httpx -td -mt "WordPress"

# Найти серверы Nginx
cat hosts.txt | httpx -server -ms "nginx"
```

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-basic-probe"></a>
### Пример 1: базовая проверка
```bash
echo "https://example.com" | httpx -sc -title
```

<a id="example-2-full-recon-from-subdomains"></a>
### Пример 2: полная разведка по поддоменам
```bash
subfinder -d hackerone.com -silent | httpx -sc -title -td -ip
```

<a id="example-3-screenshot-all-hosts"></a>
### Пример 3: снимки экрана всех узлов
```bash
httpx -l hosts.txt -screenshot -ssd ./screenshots/
```

<a id="example-4-find-live-hosts-only"></a>
### Пример 4: только доступные узлы
```bash
cat subs.txt | httpx -silent -o live.txt
```

<a id="example-5-technology-detection"></a>
### Пример 5: определение технологий
```bash
httpx -l urls.txt -tech-detect -json -o tech.json
```

<a id="example-6-with-proxy-burp"></a>
### Пример 6: через прокси (Burp)
```bash
httpx -l urls.txt -proxy http://127.0.0.1:8080 -silent
```

<a id="example-7-custom-ports"></a>
### Пример 7: заданные порты
```bash
cat hosts.txt | httpx -p 80,443,8080,8443 -sc -title
```

<a id="example-8-filter-200-only"></a>
### Пример 8: отбор только кода 200
```bash
cat subs.txt | httpx -mc 200 -silent
```

<a id="example-9-json-output-for-processing"></a>
### Пример 9: вывод JSON для обработки
```bash
httpx -l urls.txt -json -sc -title -td -o results.json
```

<a id="example-10-complete-pipeline"></a>
### Пример 10: полная цепочка
```bash
subfinder -d target.com -silent | \
httpx -silent -sc -title -td -ip -cdn -server -json | \
tee results.json | \
jq -r '.url' | \
nuclei -severity critical,high
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Задача | Команда |
|------|---------|
| Базовая проверка | `httpx -u url` |
| Из файла | `httpx -l urls.txt` |
| Код состояния | `httpx -l urls.txt -sc` |
| Заголовок страницы | `httpx -l urls.txt -title` |
| Определение технологий | `httpx -l urls.txt -td` |
| IP-адрес | `httpx -l urls.txt -ip` |
| Снимок экрана | `httpx -l urls.txt -screenshot` |
| Вывод JSON | `httpx -l urls.txt -json -o out.json` |

<a id="common-options"></a>
### Часто используемые параметры

| Параметр | Описание |
|--------|-------------|
| `-u` | Один URL |
| `-l` | Файл со списком URL |
| `-sc` | Код состояния |
| `-title` | Заголовок страницы |
| `-td` | Определение технологий |
| `-ip` | IP-адрес |
| `-cdn` | Определение CDN |
| `-server` | Веб-сервер |
| `-mc` | Отобрать коды состояния |
| `-fc` | Исключить коды состояния |
| `-json` | Вывод JSON |
| `-silent` | Режим минимального вывода |

<a id="common-pipelines"></a>
### Типовые цепочки команд

| Цепочка команд | Команда |
|----------|---------|
| Доступные узлы | `subfinder -d target.com \| httpx -silent` |
| С информацией | `subfinder -d target.com \| httpx -sc -title -td` |
| Передача в nuclei | `httpx -l hosts.txt -silent \| nuclei` |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендации

<a id="bug-bounty-tips"></a>
### Советы для Bug Bounty

1. **Используйте вместе с Subfinder**
   ```bash
   subfinder -d target.com -silent | httpx -silent -sc -title
   ```

2. **Получайте полную картину**
   ```bash
   httpx -l hosts.txt -sc -title -td -ip -cdn -server
   ```

3. **Используйте снимки экрана для первичного разбора**
   ```bash
   httpx -l hosts.txt -screenshot
   ```

4. **Используйте JSON для обработки**
   ```bash
   httpx -l hosts.txt -json | jq '.url'
   ```

<a id="performance-tips"></a>
### Советы по производительности

```bash
# Высокая скорость
httpx -l hosts.txt -threads 100 -timeout 5

# Низкая скорость с ограничением нагрузки
httpx -l hosts.txt -threads 10 -rate-limit 20
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовая оговорка

> **ВНИМАНИЕ:** httpx отправляет HTTP-запросы к целевым системам. Используйте его только там, где у вас есть разрешение.
> 
> - ✅ Работайте в программах, где вам разрешено проводить тестирование
> - ✅ Проверяйте только цели, входящие в разрешённую область программы Bug Bounty
> - ✅ Проверяйте собственные домены
> - ❌ Никогда не проверяйте цели без разрешения
> - ❌ Соблюдайте ограничения частоты запросов
> 
> **Проверки без разрешения могут нарушать закон.**

---

<a id="-resources"></a>
## 📚 Ресурсы

<a id="official-resources"></a>
### Официальные ресурсы
- [httpx GitHub](https://github.com/projectdiscovery/httpx)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Документация](https://docs.projectdiscovery.io/tools/httpx/)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [Subfinder](../../Subfinder/translations/README.ru.md)
- [Nuclei](../../Nuclei/translations/README.ru.md)
- [ffuf](../../ffuf/translations/README.ru.md)

---

<p align="center">
  <b>🌐 Проверяйте всё!</b><br>
  <i>Незаменимый набор инструментов для HTTP в Bug Bounty</i>
</p>
