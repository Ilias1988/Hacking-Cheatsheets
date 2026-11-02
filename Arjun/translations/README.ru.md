<a id="-arjun---hidden-parameter-discovery"></a>
# 🔍 Arjun — поиск скрытых параметров

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

> Надпись ASCII-логотипа означает «набор инструментов для поиска HTTP-параметров». Поиск скрытых параметров не равнозначен обнаружению новых endpoints или подтверждению уязвимостей.

```
   █████╗ ██████╗      ██╗██╗   ██╗███╗   ██╗
  ██╔══██╗██╔══██╗     ██║██║   ██║████╗  ██║
  ███████║██████╔╝     ██║██║   ██║██╔██╗ ██║
  ██╔══██║██╔══██╗██   ██║██║   ██║██║╚██╗██║
  ██║  ██║██║  ██║╚█████╔╝╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚═╝  ╚═══╝
         HTTP Parameter Discovery Suite
```

<p align="center">
  <img src="https://img.shields.io/badge/Arjun-Parameter_Discovery-blue?style=for-the-badge" alt="Arjun">
  <img src="https://img.shields.io/badge/Hidden_Params-green?style=for-the-badge" alt="Hidden">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Arjun](#-what-is-arjun)
- [Установка](#-installation)
- [Основы использования](#-basic-usage)
- [Режимы поиска](#-discovery-modes)
- [Дополнительные параметры](#-advanced-options)
- [Параметры вывода](#-output-options)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-arjun"></a>
## 🎯 Что такое Arjun

**Arjun** находит скрытые HTTP-параметры веб-приложений:

- 🔍 **Поиск параметров** — обнаружение скрытых параметров GET/POST
- 🎯 **Поддержка JSON** — обнаружение параметров JSON
- 📡 **Разные методы** — режимы GET, POST и JSON
- ⚡ **Скорость** — параллельное выполнение запросов

<a id="why-arjun"></a>
### Зачем использовать Arjun?

| Возможность | Преимущество |
|---------|---------|
| Скрытые параметры | Поиск неуказанных точек входа (endpoints) |
| Несколько форматов | GET, POST, JSON |
| Пассивный режим | Проверка JS-файлов (формулировка оригинала; см. уточнение) |
| Импорт из Burp | Включение в существующий рабочий процесс |

---

<a id="-installation"></a>
## 🚀 Установка

```bash
# Установка через pip (рекомендуется в оригинале)
pip3 install arjun

# Из исходного кода
git clone https://github.com/s0md3v/Arjun.git
cd Arjun
python3 setup.py install

# Проверка
arjun --help
```

---

<a id="-basic-usage"></a>
## 📖 Основы использования

<a id="single-url"></a>
### Один URL

```bash
# Базовый поиск параметров
arjun -u https://example.com/endpoint

# С сохранением в файл
arjun -u https://example.com/endpoint -o results.txt
```

<a id="multiple-urls"></a>
### Несколько URL

> **Уточнение.** В [официальном CLI](https://github.com/s0md3v/Arjun/blob/master/arjun/__main__.py) параметр --stdin не объявлен. Примеры конвейеров ниже сохранены из оригинала и требуют проверки для установленной версии.

```bash
# Из файла
arjun -i urls.txt

# Из стандартного ввода
cat urls.txt | arjun --stdin
```

<a id="specify-http-method"></a>
### Выбор метода HTTP

```bash
# Параметры GET (по умолчанию)
arjun -u https://example.com/search -m GET

# Параметры POST
arjun -u https://example.com/api -m POST

# Тело JSON
arjun -u https://example.com/api -m JSON
```

---

<a id="-discovery-modes"></a>
## 🔎 Режимы поиска

<a id="get-parameters"></a>
### Параметры GET

```bash
# Поиск параметров GET
arjun -u https://example.com/search?known=value -m GET

# Пример вывода:
# [param] id
# [param] user
# [param] debug
```

<a id="post-parameters"></a>
### Параметры POST

```bash
# Поиск параметров формы POST
arjun -u https://example.com/login -m POST

# С имеющимися данными
arjun -u https://example.com/login -m POST --include 'username=admin'
```

<a id="json-parameters"></a>
### Параметры JSON

```bash
# Поиск параметров в теле JSON
arjun -u https://example.com/api/v1/users -m JSON

# С исходным объектом JSON
arjun -u https://example.com/api -m JSON --include '{"known":"value"}'
```

<a id="combined-discovery"></a>
### Комбинированный поиск

> **Уточнение.** Официальный CLI принимает один режим GET, POST, JSON или XML; список через запятую не разбирается на отдельные методы. Исходные команды сохранены.

```bash
# Все методы (пример оригинала; см. уточнение)
arjun -u https://example.com/endpoint -m GET,POST,JSON
```

---

<a id="-advanced-options"></a>
## ⚙️ Дополнительные параметры

<a id="custom-wordlist"></a>
### Свой словарь

```bash
# Использование своего словаря
arjun -u https://example.com -w /path/to/wordlist.txt

# Использование имён параметров из SecLists
arjun -u https://example.com -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt
```

<a id="threading--rate-limiting"></a>
### Потоки и ограничение частоты запросов

> **О версиях.** Текущий официальный CLI задаёт 5 потоков по умолчанию, хотя Wiki указывает 2. Поддерживаются --rate-limit, задержка -d и таймаут -T; --delay и --timeout в этом CLI отсутствуют. В старой Wiki ограничение частоты названо --ratelimit.

```bash
# Количество потоков (в оригинале по умолчанию: 2; см. уточнение)
arjun -u https://example.com -t 10

# Ограничение частоты (запросов в секунду)
arjun -u https://example.com --rate-limit 10

# Задержка между запросами (секунды)
arjun -u https://example.com --delay 1
```

<a id="custom-headers"></a>
### Свои заголовки

> **Уточнение.** По [официальной Wiki](https://github.com/s0md3v/Arjun/wiki/Usage) заголовки разделяются переводом строки (\n), а не точкой с запятой; -H для файла в текущем CLI отсутствует. Примеры оригинала сохранены.

```bash
# Добавление заголовка
arjun -u https://example.com --headers "Authorization: Bearer token"

# Несколько заголовков
arjun -u https://example.com --headers "Cookie: session=abc;X-Custom: value"

# Из файла
arjun -u https://example.com -H headers.txt
```

<a id="include-known-parameters"></a>
### Добавление известных параметров

```bash
# Добавление известных параметров
arjun -u https://example.com/search --include 'q=test&page=1'

# Для POST
arjun -u https://example.com/api -m POST --include 'action=submit'
```

<a id="passive-mode"></a>
### Пассивный режим

> **Уточнение.** По официальной Wiki --passive собирает имена из CommonCrawl, OTX и Wayback Machine, после чего проверяет их на цели. Это не отдельный режим анализа JS без запросов к цели.

```bash
# Извлечение из JavaScript-файлов (описание оригинала; см. уточнение)
arjun -u https://example.com --passive

# В оригинале описано как поиск возможных имён параметров в JS-файлах
```

<a id="disable-redirects"></a>
### Отключение перенаправлений

```bash
# Не следовать перенаправлениям
arjun -u https://example.com --disable-redirects
```

<a id="timeout"></a>
### Время ожидания

```bash
# Время ожидания ответа (секунды)
arjun -u https://example.com --timeout 15
```

<a id="stable-mode"></a>
### Режим стабильной работы

```bash
# Стабильнее, но медленнее
arjun -u https://example.com --stable
```

---

<a id="-output-options"></a>
## 📤 Параметры вывода

<a id="output-formats"></a>
### Форматы вывода

> **Уточнение.** В текущем CLI -o — синоним -oJ (JSON), поэтому results.txt в предыдущем примере не задаёт текстовый формат. Текст выбирается через -oT.

```bash
# Текстовый вывод (в оригинале назван форматом по умолчанию; см. уточнение)
arjun -u https://example.com -oT results.txt

# Вывод JSON
arjun -u https://example.com -oJ results.json

# Оба формата
arjun -u https://example.com -oT text.txt -oJ json.json
```

<a id="json-output-example"></a>
### Пример вывода JSON

> **Уточнение.** По [исходному коду формирования результата](https://github.com/s0md3v/Arjun/blob/master/arjun/__main__.py) params содержит строки имён, а не объекты name/reason; есть также поле headers. JSON ниже — иллюстрация оригинала. Соответственно, jq-фильтр ниже зависит от фактической схемы вывода; params.txt в этом примере заранее не создаётся.

```json
{
  "https://example.com/api": {
    "method": "GET",
    "params": [
      {"name": "id", "reason": "body differs"},
      {"name": "debug", "reason": "body differs"}
    ]
  }
}
```

<a id="import-from-burp"></a>
### Импорт из Burp

> **Уточнение.** Официальная Wiki описывает автоопределение формата через -i, включая Burp и сырые HTTP-запросы. --import и --burp в текущем CLI отсутствуют; при экспорте из Burp предлагается отключать base64.

```bash
# Импорт запросов Burp
arjun --import burp_export.xml

# Обработка файла Burp
arjun -i burp_requests.txt --burp
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="basic-commands"></a>
### Основные команды

| Команда | Описание |
|---------|-------------|
| `arjun -u URL` | Поиск параметров |
| `arjun -u URL -m POST` | Метод POST |
| `arjun -u URL -m JSON` | Тело JSON |
| `arjun -i file.txt` | Несколько URL |
| `arjun -u URL -oJ out.json` | Вывод JSON |

<a id="method-options"></a>
### Параметры метода

| Параметр | Описание |
|------|-------------|
| `-m GET` | Параметры GET |
| `-m POST` | Данные формы POST |
| `-m JSON` | Тело JSON |
| `-m GET,POST` | Несколько методов (пример оригинала; см. уточнение) |

<a id="performance-options"></a>
### Параметры производительности

| Параметр | Описание |
|------|-------------|
| `-t 10` | Потоки |
| `--rate-limit 10` | Запросы в секунду |
| `--delay 1` | Задержка между запросами (пример оригинала; см. уточнение) |
| `--timeout 15` | Время ожидания ответа (пример оригинала; см. уточнение) |

<a id="discovery-options"></a>
### Параметры поиска

| Параметр | Описание |
|------|-------------|
| `-w wordlist.txt` | Свой словарь |
| `--include 'a=b'` | Добавление параметров |
| `--passive` | Извлечение из JS (описание оригинала; см. уточнение) |
| `--stable` | Режим стабильной работы |

---

<a id="-bug-bounty-workflows"></a>
## 🎯 Рабочие процессы Bug Bounty

<a id="basic-discovery"></a>
### Базовый поиск

```bash
# Быстрый поиск параметров
arjun -u https://target.com/api/endpoint -m GET,POST,JSON -oJ params.json
```

<a id="with-custom-wordlist"></a>
### Со своим словарём

```bash
# Подробный поиск
arjun -u https://target.com/admin \
  -m GET,POST \
  -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
  -t 10 \
  --rate-limit 20 \
  -oJ discovered_params.json
```

<a id="authenticated-testing"></a>
### Проверка с аутентификацией

```bash
# С данными сеанса
arjun -u https://target.com/dashboard \
  --headers "Cookie: session=abc123;Authorization: Bearer token" \
  -m GET,POST,JSON \
  -oJ auth_params.json
```

<a id="mass-discovery"></a>
### Массовый поиск

```bash
# Несколько точек входа
cat endpoints.txt | arjun --stdin -m GET,POST -t 5 -oJ all_params.json

# Или из файла
arjun -i endpoints.txt -m GET,POST -t 5 -oJ results.json
```

<a id="pipeline-integration"></a>
### Объединение инструментов в цепочку

```bash
# Найти точки входа, затем искать параметры
gau target.com | grep -E "\.php|\.asp" | httpx -silent | \
  arjun --stdin -m GET,POST -oJ params.json

# Katana + Arjun
katana -u https://target.com -d 3 | \
  grep -E "\.(php|asp|aspx|jsp)$" | \
  arjun --stdin -m GET,POST
```

<a id="testing-discovered-parameters"></a>
### Проверка найденных параметров

```bash
# После обнаружения проверить на уязвимости
# 1. Найти параметры
arjun -u https://target.com/search -oJ params.json

# 2. Извлечь имена параметров
cat params.json | jq -r '.[].params[].name'

# 3. Проверить с помощью ffuf
ffuf -u "https://target.com/search?FUZZ=test" -w params.txt
```

---

<a id="-pro-tips"></a>
## 💡 Практические советы

1. **Начните с пассивного сбора** — сначала проверьте JS-файлы; значение параметра --passive уточнено выше
2. **Подбирайте словари** — в оригинале рекомендуется список имён параметров Burp
3. **Ограничивайте частоту запросов** — это снижает вероятность блокировки
4. **Проверяйте все методы** — GET, POST и JSON
5. **Проверяйте заголовки** — X-Custom-Param, X-Debug и другие
6. **Используйте аутентификацию** — проверяйте разделы после входа в систему

<a id="common-hidden-parameters"></a>
### Распространённые скрытые параметры

```
debug, test, admin
id, user_id, uid
token, api_key, key
callback, redirect, url
format, output, type
page, limit, offset
sort, order, filter
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Arjun на GitHub](https://github.com/s0md3v/Arjun)
- [Имена параметров Burp](https://github.com/danielmiessler/SecLists/blob/master/Discovery/Web-Content/burp-parameter-names.txt)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [Методология Bug Bounty](../../Bug-Bounty-Methodology/translations/README.ru.md)
- [ffuf](../../ffuf/translations/README.ru.md)
- [Burp Suite](../../Burp-Suite/translations/README.ru.md)

---

<p align="center">
  <b>🔍 Находите скрытые параметры!</b><br>
  <i>Arjun — охотник за параметрами</i>
</p>
