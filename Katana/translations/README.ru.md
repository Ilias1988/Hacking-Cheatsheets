<a id="-katana---next-gen-web-crawler"></a>
# ⚔️ Katana — веб-краулер нового поколения

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗  ██╗ █████╗ ████████╗ █████╗ ███╗   ██╗ █████╗ 
  ██║ ██╔╝██╔══██╗╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗
  █████╔╝ ███████║   ██║   ███████║██╔██╗ ██║███████║
  ██╔═██╗ ██╔══██║   ██║   ██╔══██║██║╚██╗██║██╔══██║
  ██║  ██╗██║  ██║   ██║   ██║  ██║██║ ╚████║██║  ██║
  ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝
         Next-Generation Web Crawling Framework
```

<p align="center">
  <img src="https://img.shields.io/badge/Katana-ProjectDiscovery-blue?style=for-the-badge" alt="Katana">
  <img src="https://img.shields.io/badge/Web_Crawler-green?style=for-the-badge" alt="Crawler">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Katana](#-what-is-katana)
- [Установка](#-installation)
- [Базовое использование](#-basic-usage)
- [Параметры обхода](#-crawling-options)
- [Режим без графического интерфейса](#-headless-mode)
- [Вывод и фильтрация](#-output--filtering)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-katana"></a>
## 🎯 Что такое Katana

[← Русское содержание репозитория](../../README.ru.md) · [Английский оригинал](../README.md)

> **Уточнение к примерам.** Команды оригинала сохранены. Согласно [официальной справке Katana](https://docs.projectdiscovery.io/opensource/katana/usage), стратегию задаёт `-s`, а `-cs` принимает regex области обхода. `-timeout` измеряется в секундах и ограничивает ожидание запроса, а не загрузку JS; `-ct` задаёт длительность обхода. `-system-chrome` выбирает установленный Chrome; путь задаётся `-system-chrome-path`. `-fc` обозначает файл настройки форм, а не фильтр кодов; для ответов предусмотрены DSL-параметры `-mdc` и `-fdc`. В этой справке отсутствуют `-fv`, `-screenshot` и `-mc`; доступные поля `-f` не включают `method`, `status`, `body`, `response`, `header`. `-aff` экспериментален. Проверяйте параметры своей версии. Глубина ограничивает обход; формулировка «только целевая страница» упрощена. Надпись в ASCII-логотипе означает «Фреймворк обхода веб-сайтов нового поколения».

**Katana** — фреймворк нового поколения от ProjectDiscovery для обхода веб-сайтов (web crawling):

- ⚡ **Высокая скорость** — написан на Go, поддерживает множество параллельных запросов
- 🔍 **Выполнение JavaScript** — поддерживает браузер без графического интерфейса (headless)
- 📡 **Современные веб-приложения** — работает с одностраничными приложениями (SPA) и динамическим содержимым
- 🎯 **Ориентация на Bug Bounty** — создан для проверки безопасности

<a id="why-katana"></a>
### Зачем использовать Katana?

| Возможность | Преимущество |
|---------|---------|
| Chrome без графического интерфейса | Обход приложений, активно использующих JS |
| Пользовательские заголовки | Обход с аутентификацией |
| Управление областью обхода | Соблюдение заданных границ |
| Заполнение форм | Обнаружение дополнительных конечных точек (endpoints) |

---

<a id="-installation"></a>
## 🚀 Установка

```bash
# Установка через Go (рекомендуется)
go install github.com/projectdiscovery/katana/cmd/katana@latest

# Docker
docker pull projectdiscovery/katana

# Проверить версию
katana -version
```

---

<a id="-basic-usage"></a>
## 📖 Базовое использование

<a id="single-target"></a>
### Одна цель

```bash
# Базовый обход
katana -u https://example.com

# Сохранить вывод
katana -u https://example.com -o urls.txt

# Тихий режим
katana -u https://example.com -silent
```

<a id="multiple-targets"></a>
### Несколько целей

```bash
# Из стандартного ввода
echo "https://example.com" | katana

# Несколько URL
cat targets.txt | katana

# Из файла
katana -list targets.txt
```

<a id="crawl-depth"></a>
### Глубина обхода

```bash
# Установить глубину (по умолчанию: 3)
katana -u https://example.com -d 5

# Глубина 1 (в оригинале — только целевая страница; см. уточнение)
katana -u https://example.com -d 1
```

---

<a id="-crawling-options"></a>
## 🕷️ Параметры обхода

<a id="scope-control"></a>
### Управление областью обхода

```bash
# Обход только в заданной области (тот же домен)
katana -u https://example.com -fs dn

# Варианты области обхода:
# dn — доменное имя
# rdn — корневой домен
# fqdn — полное доменное имя

# Область обхода по полю
katana -u https://example.com -fs rdn
```

<a id="crawling-strategy"></a>
### Стратегия обхода

```bash
# Обход в глубину (по умолчанию; см. уточнение о параметре)
katana -u https://example.com -cs depth-first

# Обход в ширину (см. уточнение о параметре)
katana -u https://example.com -cs breadth-first
```

<a id="concurrency"></a>
### Параллельное выполнение

```bash
# Параллельные запросы (по умолчанию: 10)
katana -u https://example.com -c 50

# Ограничение частоты (запросов в секунду)
katana -u https://example.com -rl 100

# Одновременная обработка входных целей
katana -u https://example.com -p 10
```

<a id="crawl-duration"></a>
### Длительность обхода

```bash
# Установить тайм-аут (в секундах)
katana -u https://example.com -timeout 30

# Максимальное время обхода
katana -u https://example.com -ct 300
```

<a id="automatic-form-filling"></a>
### Автоматическое заполнение форм

```bash
# Включить заполнение форм
katana -u https://example.com -aff

# С пользовательскими значениями (см. уточнение о параметре)
katana -u https://example.com -aff -fv "username:admin,password:test123"
```

---

<a id="-headless-mode"></a>
## 🌐 Режим без графического интерфейса

<a id="enable-headless-browser"></a>
### Включение браузера без графического интерфейса

```bash
# Включить Chrome без графического интерфейса
katana -u https://example.com -headless

# Браузер без графического интерфейса с заданной глубиной
katana -u https://example.com -headless -d 3
```

<a id="headless-options"></a>
### Параметры браузера без графического интерфейса

```bash
# В оригинале — свой путь Chrome; команда выбирает установленный Chrome
katana -u https://example.com -headless -system-chrome

# Без песочницы браузера (в оригинале — для root)
katana -u https://example.com -headless -no-sandbox

# Снимки страниц (см. уточнение о параметре)
katana -u https://example.com -headless -screenshot
```

<a id="javascript-crawling"></a>
### Обход JavaScript

```bash
# В оригинале — ожидание JS в мс; -timeout задаёт тайм-аут запроса в секундах
katana -u https://example.com -headless -timeout 5

# Обход конечных точек из JS
katana -u https://example.com -js-crawl
```

---

<a id="-output--filtering"></a>
## 📤 Вывод и фильтрация

<a id="output-formats"></a>
### Форматы вывода

```bash
# Обычный вывод в файл
katana -u https://example.com -o results.txt

# Вывод JSON (формат JSONL)
katana -u https://example.com -jsonl -o results.json

# Без цветового оформления
katana -u https://example.com -nc
```

<a id="display-fields"></a>
### Отображаемые поля

```bash
# Показать выбранные поля (см. уточнение о допустимых полях)
katana -u https://example.com -f url,method,status

# Доступные поля (список оригинала; см. уточнение):
# url, path, fqdn, rdn, rurl, qurl, qpath
# file, kv, dir, ufile, udir
# method, status, body, response, header
```

<a id="filter-by-extension"></a>
### Фильтрация по расширению

```bash
# Выбрать указанные расширения
katana -u https://example.com -em js,php,asp

# Исключить указанные расширения
katana -u https://example.com -ef png,jpg,gif,css,svg,woff
```

<a id="filter-by-status-code"></a>
### Фильтрация по коду состояния

```bash
# Выбрать коды состояния (см. уточнение о параметре)
katana -u https://example.com -mc 200,301,302

# Исключить коды состояния (см. уточнение о параметре)
katana -u https://example.com -fc 404,500
```

<a id="custom-headers"></a>
### Пользовательские заголовки

```bash
# Добавить заголовок
katana -u https://example.com -H "Authorization: Bearer TOKEN"

# Несколько заголовков
katana -u https://example.com -H "Cookie: session=abc" -H "User-Agent: Custom"
```

<a id="proxy-support"></a>
### Поддержка прокси

```bash
# HTTP-прокси
katana -u https://example.com -proxy http://127.0.0.1:8080

# Для Burp Suite
katana -u https://example.com -proxy http://127.0.0.1:8080
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="basic-commands"></a>
### Основные команды

| Команда | Описание |
|---------|-------------|
| `katana -u URL` | Обход одного URL |
| `katana -list file.txt` | Обход адресов из файла |
| `katana -u URL -d 5` | Установить глубину 5 |
| `katana -u URL -headless` | Включить браузер без графического интерфейса |
| `katana -u URL -o out.txt` | Сохранить результаты |

<a id="scope-options"></a>
### Параметры области обхода

| Параметр | Описание |
|------|-------------|
| `-fs dn` | Тот же домен |
| `-fs rdn` | Корневой домен |
| `-fs fqdn` | Полное доменное имя |
| `-cs depth-first` | Обход в глубину (см. уточнение о параметре) |
| `-cs breadth-first` | Обход в ширину (см. уточнение о параметре) |

<a id="performance-flags"></a>
### Параметры производительности

| Параметр | Описание |
|------|-------------|
| `-c 50` | Число параллельных обработчиков запросов |
| `-p 10` | Число одновременно обрабатываемых входных целей |
| `-rl 100` | Ограничение частоты запросов |
| `-timeout 30` | Тайм-аут запроса |

<a id="filtering-flags"></a>
### Параметры фильтрации

| Параметр | Описание |
|------|-------------|
| `-em js,php` | Выбрать указанные расширения |
| `-ef png,jpg` | Исключить указанные расширения |
| `-mc 200` | Выбрать коды состояния (см. уточнение о версии) |
| `-fc 404` | Исключить коды состояния (см. уточнение о версии) |

---

<a id="-bug-bounty-workflows"></a>
## 🎯 Рабочие процессы Bug Bounty

<a id="basic-crawl"></a>
### Базовый обход

```bash
# Быстрый обход
katana -u https://target.com -d 3 -o urls.txt
```

<a id="comprehensive-crawl"></a>
### Подробный обход

```bash
# Глубокий обход с JS
katana -u https://target.com -d 5 -headless -c 50 \
  -ef png,jpg,gif,css,svg,woff,woff2,ttf,eot,ico \
  -o all_urls.txt
```

<a id="parameter-discovery"></a>
### Поиск параметров

```bash
# Найти URL с параметрами
katana -u https://target.com -d 3 | grep "?" | sort -u

# Поиск определённых параметров через grep
katana -u https://target.com | grep -E "id=|user=|admin=|file="
```

<a id="combined-with-other-tools"></a>
### Совместная работа с другими инструментами

```bash
# Katana + httpx
katana -u https://target.com -d 3 | httpx -silent -sc

# Katana + Nuclei
katana -u https://target.com -d 3 | nuclei -t nuclei-templates/

# Полная цепочка команд
subfinder -d target.com | httpx -silent | katana -d 3 | grep "=" | sort -u
```

<a id="authenticated-crawling"></a>
### Обход с аутентификацией

```bash
# С cookie сессии
katana -u https://target.com \
  -H "Cookie: session=abc123" \
  -d 5 \
  -headless \
  -o authenticated_urls.txt
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Katana на GitHub](https://github.com/projectdiscovery/katana)
- [ProjectDiscovery](https://projectdiscovery.io/)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [Методология Bug Bounty](../../Bug-Bounty-Methodology/translations/README.ru.md)
- [httpx](../../httpx/translations/README.ru.md)
- [Nuclei](../../Nuclei/translations/README.ru.md)
- [GAU](../../GAU/translations/README.ru.md)

---

<p align="center">
  <b>⚔️ Исследуйте веб-ресурсы!</b><br>
  <i>Katana — веб-краулер нового поколения</i>
</p>
