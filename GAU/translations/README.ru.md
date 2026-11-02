<a id="-gau---get-all-urls"></a>
# 🔗 GAU — сбор всех URL

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Уточнения к оригиналу:** в [официальной справке GAU](https://github.com/lc/gau) --subs включает поддомены, а не тихий режим; --blacklist исключает расширения, а не источники. --mt/--ft принимают MIME-типы, поэтому regex-примеры расширений и подписи таблицы неточны. Флаги и конфигурация зависят от версии; исходные команды и TOML-данные сохранены буквально.

> **Интерпретация результатов:** архивный URL не гарантирует текущую доступность. GAU получает данные из сторонних источников, но последующие шаги с httpx обращаются к ресурсам; шаблоны gf и ответ HTTP 200 сами по себе не подтверждают XSS, SQLi или LFI. Подпись ASCII-эмблемы Get All URLs — Fast URL Harvesting означает «сбор URL из нескольких источников»; рисунок оставлен без изменения выравнивания.


```
   ██████╗  █████╗ ██╗   ██╗
  ██╔════╝ ██╔══██╗██║   ██║
  ██║  ███╗███████║██║   ██║
  ██║   ██║██╔══██║██║   ██║
  ╚██████╔╝██║  ██║╚██████╔╝
   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ 
       Get All URLs - Fast URL Harvesting
```

<p align="center">
  <img src="https://img.shields.io/badge/GAU-URL_Discovery-blue?style=for-the-badge" alt="GAU">
  <img src="https://img.shields.io/badge/Wayback-green?style=for-the-badge" alt="Wayback">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое GAU](#-what-is-gau)
- [Установка](#-installation)
- [Основы использования](#-basic-usage)
- [Источники данных](#-providers)
- [Фильтрация](#-filtering)
- [Интеграция](#-integration)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-gau"></a>
## 🎯 Что такое GAU

**GAU** (Get All URLs) собирает известные URL из нескольких источников:

- 🔍 **Wayback Machine** — исторические URL
- 🌐 **Common Crawl** — данные обхода веб-сайтов
- 📡 **AlienVault OTX** — URL из данных об угрозах
- 🔒 **URLScan.io** — URL из результатов сканирования

<a id="why-gau"></a>
### Зачем использовать GAU?

| Преимущество | Описание |
| --------- | ------------- |
| **Пассивная разведка** | Без прямого обращения к цели |
| **Исторические данные** | Поиск старых или забытых конечных точек |
| **Быстрая работа** | Параллельный сбор |
| **Несколько источников** | Более полный набор результатов |

---

<a id="-installation"></a>
## 🚀 Установка

```bash
# Установка через Go (рекомендуется в оригинале)
go install github.com/lc/gau/v2/cmd/gau@latest

# Из исходного кода
git clone https://github.com/lc/gau.git
cd gau/cmd/gau
go build

# Проверить установку
gau --version
```

---

<a id="-basic-usage"></a>
## 📖 Основы использования

<a id="single-domain"></a>
### Один домен

```bash
# Основной вариант запуска
gau example.com

# С записью в файл
gau example.com -o urls.txt

# Тихий режим без баннера (подпись оригинала; см. уточнение)
gau --subs example.com
```

<a id="multiple-domains"></a>
### Несколько доменов

```bash
# Из стандартного ввода
echo "example.com" | gau

# Несколько доменов
cat domains.txt | gau

# Из файла
gau --threads 5 < domains.txt
```

<a id="include-subdomains"></a>
### Включение поддоменов

```bash
# Включить поддомены в результаты
gau --subs example.com
```

<a id="threading"></a>
### Параллельная обработка

```bash
# Задать число потоков (в оригинале по умолчанию: 1)
gau --threads 5 example.com

# Снизить параллелизм с учётом ограничений частоты
gau --threads 2 example.com
```

---

<a id="-providers"></a>
## 🌐 Источники данных

<a id="available-providers"></a>
### Доступные источники

| Источник | Описание | По умолчанию |
| ---------- | ------------- | --------- |
| `wayback` | Wayback Machine | ✅ |
| `commoncrawl` | Common Crawl | ✅ |
| `otx` | AlienVault OTX | ✅ |
| `urlscan` | URLScan.io | ✅ |

<a id="select-providers"></a>
### Выбор источников

```bash
# Использовать только выбранные источники
gau --providers wayback example.com
gau --providers wayback,otx example.com

# Исключить источник (подпись оригинала; см. уточнение)
gau --blacklist commoncrawl example.com
```

<a id="provider-configuration"></a>
### Настройка источников

```bash
# Расположение файла настроек
~/.gau.toml

# Пример настроек
cat > ~/.gau.toml << EOF
threads = 5
verbose = false
retries = 3
subdomains = false
providers = ["wayback","commoncrawl","otx","urlscan"]
blacklist = []
json = false
EOF
```

---

<a id="-filtering"></a>
## 🔍 Фильтрация

<a id="filter-by-status-code"></a>
### Фильтрация по коду состояния

```bash
# Только URL с кодом ответа 200
gau --mc 200 example.com

# Несколько кодов состояния
gau --mc 200,301,302 example.com

# Исключить определённые коды
gau --fc 404,500 example.com
```

<a id="filter-by-extension"></a>
### Фильтрация по расширению

```bash
# Отобрать расширения (подпись оригинала; см. уточнение)
gau --mt "\.js$" example.com
gau --mt "\.php$" example.com

# Исключить расширения (подпись оригинала; см. уточнение)
gau --ft "\.jpg$|\.png$|\.gif$|\.css$" example.com
```

<a id="filter-by-mime-type"></a>
### Фильтрация по MIME-типу

```bash
# Отобрать по типу содержимого
gau --mt "application/json" example.com
gau --mt "text/html" example.com
```

<a id="output-format"></a>
### Формат вывода

```bash
# Вывод JSON
gau --json example.com

# Подробный вывод
gau --verbose example.com
```

---

<a id="-integration"></a>
## 🔗 Интеграция

<a id="with-httpx"></a>
### Совместно с httpx

```bash
# Проверить доступность URL
gau example.com | httpx -silent

# С кодами состояния
gau example.com | httpx -sc -title
```

<a id="with-grepparameter-extraction"></a>
### grep и извлечение параметров

```bash
# Найти URL с параметрами
gau example.com | grep "="

# Найти определённые шаблоны
gau example.com | grep -E "\.php\?|\.asp\?"

# Шаблоны GF
gau example.com | gf xss
gau example.com | gf sqli
gau example.com | gf lfi
gau example.com | gf redirect
```

<a id="with-urldedupe"></a>
### Совместно с urldedupe

```bash
# Удалить дубликаты
gau example.com | urldedupe

# Альтернатива: sort -u
gau example.com | sort -u
```

<a id="with-uro"></a>
### Совместно с uro

```bash
# Обработка повторяющихся URL
gau example.com | uro
```

<a id="full-pipeline"></a>
### Полный конвейер

```bash
# Полный конвейер сбора URL
echo "example.com" | gau --subs --threads 5 | \
  uro | \
  httpx -silent -mc 200 | \
  grep "=" > params.txt
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="basic-commands"></a>
### Основные команды

| Команда | Описание |
| --------- | ------------- |
| `gau domain.com` | Получить URL домена |
| `gau --subs domain.com` | Включить поддомены |
| `gau -o urls.txt domain.com` | Записать в файл |
| `gau --threads 5 domain.com` | Параллельная обработка |

<a id="filtering-options"></a>
### Параметры фильтрации

| Параметр | Описание |
| ------ | ------------- |
| `--mc 200` | Отобрать по коду состояния |
| `--fc 404` | Исключить код состояния |
| `--mt "regex"` | Отобрать по regex (подпись оригинала; см. уточнение) |
| `--ft "regex"` | Исключить по regex (подпись оригинала; см. уточнение) |
| `--json` | Вывод JSON |

<a id="provider-options"></a>
### Параметры источников

| Параметр | Описание |
| ------ | ------------- |
| `--providers wayback,otx` | Использовать выбранные источники |
| `--blacklist commoncrawl` | Исключить источник (подпись оригинала; см. уточнение) |

<a id="useful-one-liners"></a>
### Полезные однострочные команды

```bash
# Получить все файлы JS
gau example.com | grep -E "\.js$" | sort -u

# Найти конечные точки API
gau example.com | grep -iE "/api/|/v[0-9]/"

# Найти параметры
gau example.com | grep "?" | grep "=" | sort -u

# Найти кандидатов для проверки SQL-инъекций
gau example.com | gf sqli

# Найти кандидатов для проверки XSS
gau example.com | gf xss

# Доступные URL с параметрами
gau example.com | grep "=" | httpx -silent
```

---

<a id="-bug-bounty-workflow"></a>
## 🎯 Процесс работы в Bug Bounty

```bash
# 1. Собрать все URL
gau --subs --threads 5 target.com > all_urls.txt

# 2. Отобрать интересующие URL
cat all_urls.txt | grep "=" | uro > params.txt

# 3. Проверить доступность
cat params.txt | httpx -silent > live_params.txt

# 4. Найти кандидатов для проверки уязвимостей
cat live_params.txt | gf sqli > sqli.txt
cat live_params.txt | gf xss > xss.txt
cat live_params.txt | gf lfi > lfi.txt
cat live_params.txt | gf redirect > redirect.txt

# 5. Проверить
cat xss.txt | qsreplace '"><script>alert(1)</script>' | \
  httpx -silent -mc 200
```

---

<a id="-resources"></a>
## 📚 Полезные материалы

- [GAU GitHub](https://github.com/lc/gau)
- [Шаблоны GF](https://github.com/1ndianl33t/Gf-Patterns)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Методология Bug Bounty](../../Bug-Bounty-Methodology/translations/README.ru.md)
- [Subfinder](../../Subfinder/translations/README.ru.md)
- [httpx](../../httpx/translations/README.ru.md)

---

<p align="center">
  <b>🔗 Собирайте URL из доступных источников!</b><br>
  <i>GAU — пассивный сбор URL</i>
</p>
