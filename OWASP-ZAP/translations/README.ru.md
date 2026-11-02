<a id="-owasp-zap---complete-cheatsheet"></a>
# ⚡ OWASP ZAP — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ____  _    _  ___   _____ _____    _____   ___  _____
  / __ \| |  | |/ _ \ / ____|  __ \  |__  /  / _ \|  __ \
 | |  | | |  | | | | | (___ | |__) |   / /  / /_\ \ |__) |
 | |  | | |/\| | |_| |\___ \|  ___/   / /  |  _  |  ___/
 | |__| \  /\  / | | |____) | |      / /__ | | | | |
  \____/ \/  \/|_| |_|_____/|_|     /_____|_| |_|_|
                                                          
    Zed Attack Proxy - Free Security Testing Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/OWASP-ZAP-blue?style=for-the-badge" alt="OWASP ZAP">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/Open-Source-green?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Free-Tool-purple?style=for-the-badge" alt="Free">
</p>

<p align="center">
  <b>🔒 Один из самых известных бесплатных сканеров безопасности веб-приложений</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Установка](#-installation)
- [Обзор интерфейса](#-interface-overview)
- [Настройка прокси](#-proxy-configuration)
- [Ручное тестирование](#-manual-testing)
- [Spider и обход приложения](#-spider--crawler)
- [Активное сканирование](#-active-scanning)
- [Пассивное сканирование](#-passive-scanning)
- [Контексты и сеансы](#-contexts--sessions)
- [Фаззер](#-fuzzer)
- [API и автоматизация](#-api--automation)
- [Командная строка](#-command-line)
- [Дополнения](#-add-ons)
- [Практические примеры](#-real-world-examples)
- [Краткий справочник](#-quick-reference)
- [Советы и рекомендуемые практики](#-tips--best-practices)
- [Полезные материалы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

> **Уточнения к оригиналу:** название OWASP ZAP сохранено для соответствия исходнику, но [ZAP больше не является проектом OWASP](https://www.zaproxy.org/docs/nowaspzap/) с 2023 года. Цены, сравнительные оценки и число дополнений в таблицах — сведения оригинала, а не актуальная гарантия. Названия меню, поля интерфейса и сочетания клавиш оставлены на английском для сопоставления; их доступность зависит от версии и установленных дополнений.

> **Пассивные проверки и автоматизация:** сам механизм passive scan не создаёт дополнительных запросов, но [Baseline Scan](https://www.zaproxy.org/docs/docker/baseline-scan/) сначала запускает Spider и потому обращается к приложению. Пример Python показывает запуск задач, но не ожидание их завершения: немедленный запрос alerts может вернуть неполные результаты. Старый endpoint htmlreport сохранён буквально; актуальные способы создания отчётов см. в [API дополнения Report Generation](https://www.zaproxy.org/docs/desktop/addons/report-generation/api/).

> **Подпись ASCII-эмблемы:** Zed Attack Proxy — бесплатный инструмент тестирования безопасности. Изображение сохранено без изменения выравнивания.

**OWASP ZAP** (Zed Attack Proxy) — бесплатный открытый инструмент тестирования на проникновение для поиска уязвимостей веб-приложений. Утверждение оригинала о сопровождении OWASP уточнено в примечании выше.

<a id="why-zap"></a>
### Зачем использовать ZAP?

| Возможность | Описание |
| --------- | ------------- |
| **Бесплатный и открытый** | Без лицензионных платежей, с участием сообщества |
| **Удобство использования** | Доступен новичкам и предоставляет широкие возможности |
| **Автоматизированное сканирование** | Spider, активное и пассивное сканирование |
| **Ручное тестирование** | Перехватывающий прокси и фаззер |
| **Поддержка API** | Полный REST API для автоматизации |
| **Поддержка разных платформ** | Windows, Linux, macOS, Docker |
| **Расширяемость** | Marketplace с более чем 100 дополнениями |

<a id="zap-vs-burp-suite"></a>
### ZAP и Burp Suite

| Возможность | ZAP | Burp Suite |
| --------- | ----- | ------------ |
| **Цена** | Бесплатно | $449+/год |
| **Открытый исходный код** | ✅ Да | ❌ Нет |
| **Автоматизированное сканирование** | ✅ Полностью | Только Pro |
| **Доступ к API** | ✅ Бесплатно | Только Pro |
| **Активная разработка** | ✅ Да | ✅ Да |
| **Сообщество** | OWASP | PortSwigger |

---

<a id="-installation"></a>
## 📥 Установка

<a id="windows"></a>
### Windows
```bash
# Скачать с официального сайта
https://www.zaproxy.org/download/

# Или установить через Chocolatey
choco install owasp-zap
```

<a id="linux-kaliubuntu"></a>
### Linux (Kali/Ubuntu)
```bash
# Kali (предустановлен)
zaproxy

# Ubuntu/Debian
sudo apt install zaproxy

# Snap
sudo snap install zaproxy --classic
```

<a id="macos"></a>
### macOS
```bash
# Homebrew
brew install --cask owasp-zap

# Скачать DMG с официального сайта
```

<a id="docker"></a>
### Docker
```bash
# Скачать последний образ
docker pull ghcr.io/zaproxy/zaproxy:stable

# Запустить ZAP в режиме демона
docker run -u zap -p 8080:8080 -i ghcr.io/zaproxy/zaproxy:stable zap.sh -daemon -host 0.0.0.0 -port 8080

# Запустить графический интерфейс (Linux)
docker run -u zap -p 8080:8080 -p 8090:8090 -v /tmp:/zap/wrk/:rw \
    --rm -it ghcr.io/zaproxy/zaproxy:stable zap-webswing.sh
```

<a id="verify-installation"></a>
### Проверка установки
```bash
# Запустить ZAP
zaproxy
zap.sh       # Linux
zap.bat      # Windows
```

---

<a id="-interface-overview"></a>
## 🖥️ Обзор интерфейса

<a id="main-panels"></a>
### Основные панели

| Панель | Описание |
| ------- | ------------- |
| **Sites Tree** | Иерархия посещённых сайтов |
| **History** | Все запросы и ответы |
| **Alerts** | Найденные уязвимости |
| **Active Scan** | Ход текущей проверки |
| **Spider** | URL, найденные при обходе |
| **Search** | Поиск по всем данным |
| **Request/Response** | Просмотр подробностей |

<a id="toolbar-icons"></a>
### Значки панели инструментов

| Значок | Функция |
| ------ | ---------- |
| 🌐 | Проверка через Quick Start |
| 🔍 | Spider |
| ⚡ | Active Scan |
| 🛑 | Остановка и перехват |
| 📊 | Отчёты |
| ⚙️ | Параметры |

<a id="modes"></a>
### Режимы

| Режим | Описание |
| ------ | ------------- |
| **Safe** | Без потенциально опасных операций |
| **Protected** | Проверки только в согласованных границах |
| **Standard** | Полные проверки (по умолчанию) |
| **ATTACK** | Агрессивные проверки |

---

<a id="-proxy-configuration"></a>
## 🔌 Настройка прокси

<a id="browser-configuration"></a>
### Настройка браузера

**Прокси ZAP по умолчанию:**
```
Host: localhost (127.0.0.1)
Port: 8080
```

<a id="firefox-setup"></a>
### Настройка Firefox
```
1. Settings → Network Settings → Manual proxy
2. HTTP Proxy: 127.0.0.1  Port: 8080
3. Установите флажок "Also use this proxy for HTTPS"
4. Или используйте расширение FoxyProxy
```

<a id="import-zap-certificate"></a>
### Импорт сертификата ZAP
```
1. Откройте в браузере: http://zap
2. Нажмите "Download ZAP Root CA Certificate"
3. Импортируйте сертификат в браузер:
   Firefox: Settings → Privacy → Certificates → Import
   Chrome: Settings → Security → Manage certificates
```

<a id="configure-zap-proxy-options"></a>
### Настройка параметров прокси ZAP
```
Tools → Options → Local Proxies

Настройки:
- Address: localhost
- Port: 8080
- Behind NAT: Enable if needed — включить при необходимости
- Remove unsupported encodings: Enable — включить
```

<a id="upstream-proxy-proxy-chain"></a>
### Вышестоящий прокси (цепочка прокси)
```
Tools → Options → Network → Connection

Использовать цепочку прокси:
- Address: corporate-proxy.com
- Port: 8080
- Аутентификация при необходимости
```

---

<a id="-manual-testing"></a>
## 🔧 Ручное тестирование

<a id="intercepting-requests-break"></a>
### Перехват запросов (Break)

```
1. Нажмите зелёную кнопку "Break" (🛑) на панели инструментов
2. Откройте приложение и перейдите по его страницам
3. Измените перехваченный запрос
4. Нажмите "Submit and Continue" или "Drop"

Параметры перехвата:
- Перехватывать все запросы
- Перехватывать все ответы
- Перехватывать URL по заданному шаблону
```

<a id="request-editor"></a>
### Редактор запросов

```
Right-click request → Open/Resend with Request Editor

Возможности:
- Изменение любой части запроса
- Добавление и удаление заголовков
- Изменение метода HTTP
- Отправка и просмотр ответа
```

<a id="manual-request"></a>
### Ручной запрос
```
Tools → Manual Request Editor

Или: Ctrl+M

Создание собственных запросов с нуля
```

<a id="encodedecodehash"></a>
### Кодирование, декодирование и хеширование
```
Tools → Encode/Decode/Hash

Поддерживается:
- Кодирование и декодирование URL
- Кодирование и декодирование Base64
- Кодирование и декодирование HTML
- Преобразование в шестнадцатеричное представление и обратно
- Хеширование MD5, SHA1, SHA256
```

<a id="search-functionality"></a>
### Поиск
```
Search tab → Enter pattern — введите шаблон на вкладке Search

Поиск по:
- URL
- Запросам
- Ответам
- Заголовкам
- Всем данным
```

---

<a id="-spider--crawler"></a>
## 🕷️ Spider и обход приложения

<a id="standard-spider"></a>
### Обычный Spider

```
Right-click target → Spider → Spider...

Или: Ctrl+Alt+S

Параметры:
- Максимальная глубина обхода
- Максимальное число дочерних узлов
- Максимальная длительность
- Обработка параметров
```

<a id="spider-options"></a>
### Параметры Spider
```
Tools → Options → Spider

Настройки:
- Max Depth: 5-10 (default: 5)
- Max Children: 10
- Max Duration: 0 (unlimited)
- Thread Count: 5
- Handle forms: Yes
- Post forms: Yes
```

<a id="ajax-spider"></a>
### AJAX Spider

```
Для приложений, активно использующих JavaScript:

Right-click target → AJAX Spider → AJAX Spider...

Требуется браузер:
- Firefox (рекомендуется)
- Chrome
- HtmlUnit (без графического интерфейса)
```

<a id="ajax-spider-options"></a>
### Параметры AJAX Spider
```
Tools → Options → AJAX Spider

Настройки:
- Browser: Firefox
- Max Crawl Depth: 10
- Max Crawl States: 0 (unlimited)
- Max Duration: 60 minutes
- Нажимать стандартные элементы
- Нажимать элементы с onclick и другими обработчиками
```

---

<a id="-active-scanning"></a>
## ⚡ Активное сканирование

<a id="start-active-scan"></a>
### Запуск активного сканирования

```
Right-click target → Active Scan...

Или: Ctrl+Alt+A

Настройте:
- Starting Point — начальную точку
- Context — контекст (необязательно)
- User — пользователя для аутентификации
- Policy — политику
- Technology — технологии
```

<a id="scan-policies"></a>
### Политики сканирования

```
Analyze → Scan Policy Manager

Создание и изменение политик:
- Какие проверки выполнять
- Threshold — порог срабатывания
- Strength — интенсивность проверок
```

<a id="policy-categories"></a>
### Категории политик

| Категория | Проверки |
| ---------- | ------- |
| **Инъекции** | SQL, команды ОС, LDAP |
| **Браузер клиента** | XSS, DOM XSS |
| **Безопасность сервера** | Удалённое включение файлов |
| **Информация** | Обход каталогов и раскрытие информации |
| **Разное** | Перенаправления, управляемые пользователем |

<a id="scan-strength"></a>
### Интенсивность проверок

| Уровень | Описание |
| ------- | ------------- |
| **Low (низкая)** | Мало запросов, быстро |
| **Medium (средняя)** | Сбалансированный режим |
| **High (высокая)** | Много запросов, подробная проверка |
| **Insane (максимальная)** | Максимум проверок, медленно |

<a id="technology-selection"></a>
### Выбор технологий
```
Right-click target → Technology → Select...

Ограничение проверок по технологиям:
- PHP, ASP, JSP
- MySQL, PostgreSQL, Oracle
- Apache, IIS, Nginx
- Linux, Windows
```

---

<a id="-passive-scanning"></a>
## 👁️ Пассивное сканирование

<a id="what-is-passive-scanning"></a>
### Что такое пассивное сканирование?

Анализирует трафик без отправки дополнительных запросов самим механизмом пассивной проверки.

```
Автоматически — работает с трафиком, проходящим через прокси

Обнаруживает:
- Отсутствующие защитные заголовки
- Раскрытие информации
- Проблемы cookie
- Слабую конфигурацию SSL/TLS
- Раскрытие внутренних IP-адресов
```

<a id="passive-scan-rules"></a>
### Правила пассивного сканирования

```
Analyze → Scan Policy Manager → Passive Scan

Правила включают:
- Application Error Disclosure
- Cookie Without Secure Flag
- Cross-Domain JavaScript
- Information Disclosure
- Private IP Disclosure
- X-Frame-Options Header Missing
```

<a id="configure-passive-scanning"></a>
### Настройка пассивного сканирования
```
Tools → Options → Passive Scan

Настройки:
- Scan only in scope: Recommended
- Max alerts per rule: 10
- Tags: Enable
```

---

<a id="-contexts--sessions"></a>
## 🎯 Контексты и сеансы

<a id="what-are-contexts"></a>
### Что такое контексты?

Логические группы связанных URL для упорядоченного тестирования.

<a id="create-context"></a>
### Создание контекста
```
Right-click Sites → Include in Context → New Context

Or: File → New Context
```

<a id="context-settings"></a>
### Параметры контекста

| Настройка | Назначение |
| --------- | --------- |
| **Include in Context** | URL для включения |
| **Exclude from Context** | URL для исключения |
| **Technology** | Технологии сервера |
| **Authentication** | Способ входа |
| **Управление сеансами** | Работа с cookie и токенами |
| **Пользователи** | Тестовые учётные записи |

<a id="authentication-types"></a>
### Виды аутентификации

| Тип | Применение |
| ------ | ---------- |
| **Form-based (через форму)** | Формы входа |
| **HTTP Basic** | Базовая аутентификация |
| **JSON-based (на основе JSON)** | Аутентификация API |
| **Script-based (через скрипт)** | Собственная реализация аутентификации |

<a id="form-based-authentication"></a>
### Аутентификация через форму
```
1. Запишите запрос входа в ZAP
2. Right-click → Include in Context
3. Context → Authentication → Form-based
4. Укажите Login URL
5. Укажите Login Request POST Data
6. Настройте параметры имени пользователя и пароля
7. Настройте признаки входа и выхода из учётной записи
```

<a id="session-management"></a>
### Управление сеансами
```
Context → Session Management

Варианты:
- Cookie-based — на основе cookie (наиболее распространённый)
- HTTP Auth — аутентификация HTTP
- Script-based — на основе скрипта
```

<a id="users"></a>
### Пользователи
```
Context → Users → Add

Добавьте тестовых пользователей:
- Name: admin
- Credentials: admin/password123
```

---

<a id="-fuzzer"></a>
## 🎲 Фаззер

<a id="start-fuzzer"></a>
### Запуск фаззера
```
1. Выберите запрос в History
2. Right-click → Attack → Fuzz...
3. Выделите позицию для фаззинга
4. Добавьте полезные нагрузки
5. Запустите фаззинг
```

<a id="add-fuzz-location"></a>
### Добавление позиции фаззинга
```
1. В диалоге Fuzzer выделите текст для фаззинга
2. Нажмите "Add..."
3. Выберите тип полезной нагрузки
```

<a id="payload-types"></a>
### Виды полезной нагрузки

| Тип | Описание |
| ------ | ------------- |
| **Strings (строки)** | Собственный список строк |
| **File (файл)** | Загрузка из файла |
| **File Fuzzers (готовые наборы)** | Встроенные словари |
| **Numbers (числа)** | Числовые последовательности |
| **Regex (регулярное выражение)** | Генерация по регулярному выражению |
| **Script (скрипт)** | Собственный скрипт |

<a id="built-in-fuzzers"></a>
### Встроенные наборы фаззинга
```
Add → File Fuzzers

Категории:
- jbrofuzz (XSS, SQL и т. п.)
- dirbuster (каталоги)
- fuzzdb (обширный набор)
```

<a id="fuzzer-options"></a>
### Параметры фаззера
```
Параметры:
- Число параллельных потоков
- Задержка между запросами
- Переходы по перенаправлениям
- Показ всех результатов или отфильтрованных
```

<a id="fuzzer-example-parameter-fuzzing"></a>
### Пример: фаззинг параметра
```
1. Перехватите: GET /page?id=1
2. Right-click → Fuzz
3. Выделите "1"
4. Добавьте полезные нагрузки SQL-инъекций
5. Запустите фаззер
6. Проанализируйте результаты по длине и коду ответа
```

---

<a id="-api--automation"></a>
## 🤖 API и автоматизация

<a id="enable-api"></a>
### Включение API
```
Tools → Options → API

Настройки:
- Enabled: Yes
- API Key: (auto-generated or custom)
- Permitted addresses: localhost
```

<a id="api-access"></a>
### Доступ к API
```bash
# Корень API
http://localhost:8080/JSON/

# С ключом API
http://localhost:8080/JSON/core/view/alerts/?apikey=YOUR_API_KEY

# Получить версию
curl "http://localhost:8080/JSON/core/view/version/?apikey=API_KEY"
```

<a id="common-api-endpoints"></a>
### Основные конечные точки API

| Конечная точка | Назначение |
| ---------- | --------- |
| `/core/view/alerts/` | Получить все предупреждения |
| `/core/view/hosts/` | Получить все узлы |
| `/spider/action/scan/` | Запустить Spider |
| `/ascan/action/scan/` | Запустить активную проверку |
| `/core/view/urls/` | Получить все URL |
| `/core/action/shutdown/` | Завершить ZAP |

<a id="api-examples"></a>
### Примеры API

```bash
# Выполнить обход URL с помощью Spider
curl "http://localhost:8080/JSON/spider/action/scan/?url=http://target.com&apikey=API_KEY"

# Проверить состояние Spider
curl "http://localhost:8080/JSON/spider/view/status/?apikey=API_KEY"

# Запустить активную проверку
curl "http://localhost:8080/JSON/ascan/action/scan/?url=http://target.com&apikey=API_KEY"

# Получить предупреждения
curl "http://localhost:8080/JSON/core/view/alerts/?apikey=API_KEY"

# Сформировать отчёт HTML
curl "http://localhost:8080/OTHER/core/other/htmlreport/?apikey=API_KEY" > report.html
```

<a id="python-api-client"></a>
### Клиент API на Python
```python
from zapv2 import ZAPv2

# Подключиться к ZAP
zap = ZAPv2(apikey='your-api-key', proxies={'http': 'http://127.0.0.1:8080'})

# Spider
zap.spider.scan(url='http://target.com')

# Active Scan
zap.ascan.scan(url='http://target.com')

# Получить предупреждения
alerts = zap.core.alerts()
for alert in alerts:
    print(f"{alert['risk']}: {alert['name']}")
```

---

<a id="-command-line"></a>
## ⌨️ Командная строка

<a id="basic-command-line-options"></a>
### Основные параметры командной строки
```bash
# Запустить ZAP в режиме демона
zap.sh -daemon

# Запустить на заданном порту
zap.sh -daemon -port 8090

# Быстрая проверка
zap.sh -quickurl http://target.com -quickout report.html

# Baseline-проверка (Docker)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t http://target.com
```

<a id="automation-framework"></a>
### Фреймворк автоматизации
```bash
# Выполнить план автоматизации
zap.sh -cmd -autorun automation.yaml
```

<a id="automation-yaml-example"></a>
### Пример YAML для автоматизации
```yaml
env:
  contexts:
    - name: "Default Context"
      urls:
        - "http://target.com"

jobs:
  - type: spider
    parameters:
      url: "http://target.com"
      maxDuration: 5
      
  - type: passiveScan-wait
    parameters:
      maxDuration: 5
      
  - type: activeScan
    parameters:
      scanOnlyInScope: true
      
  - type: report
    parameters:
      template: "traditional-html"
      reportFile: "report.html"
```

<a id="docker-scans"></a>
### Проверки в Docker

```bash
# Baseline-проверка (пассивные результаты; включает Spider — см. уточнение)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://target.com \
    -r report.html

# Полная проверка (активная)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-full-scan.py \
    -t https://target.com \
    -r report.html

# Проверка API
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py \
    -t https://target.com/api/openapi.json \
    -f openapi \
    -r report.html
```

---

<a id="-add-ons"></a>
## 🧩 Дополнения

<a id="install-add-ons"></a>
### Установка дополнений
```
Help → Check for Updates → Marketplace

Or: Tools → Add-ons → Marketplace
```

<a id="essential-add-ons"></a>
### Полезные дополнения

| Дополнение | Описание |
| -------- | ------------- |
| **Active Scan Rules** | Дополнительные правила активных проверок |
| **Правила пассивного сканирования** | Дополнительные правила пассивных проверок |
| **FuzzDB** | Полезные нагрузки для фаззинга |
| **Retire.js** | Поиск уязвимых библиотек JavaScript |
| **AJAX Spider** | Обход с учётом JavaScript |
| **Selenium** | Автоматизация браузера |
| **Wappalyzer** | Определение технологий |
| **Access Control** | Проверка контроля доступа |
| **Directory List** | Словари каталогов |

<a id="add-on-management"></a>
### Управление дополнениями
```
# Список установленных дополнений
Tools → Add-ons → Installed

# Обновить все дополнения
Help → Check for Updates → Update All
```

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-quick-web-scan"></a>
### Пример 1: быстрая проверка веб-приложения
```
1. Введите URL на вкладке Quick Start
2. Нажмите "Attack"
3. ZAP автоматически выполнит обход и проверки
4. После завершения изучите предупреждения
```

<a id="example-2-manual--automated-testing"></a>
### Пример 2: ручное и автоматизированное тестирование
```
1. Укажите ZAP в качестве прокси браузера
2. Исследуйте приложение вручную, открыв все его функции
3. Right-click target in Sites → Spider
4. Дождитесь завершения обхода Spider
5. Right-click target → Active Scan
6. Изучите предупреждения
7. Сформировать отчёт
```

<a id="example-3-authenticated-scan"></a>
### Пример 3: проверка с аутентификацией
```
1. Создайте Context для цели
2. Настройте Form-based Authentication
3. Добавьте учётные данные тестового пользователя
4. Настройте режим принудительного выбора пользователя
5. Выполните обход от имени аутентифицированного пользователя
6. Выполните Active Scan
```

<a id="example-4-api-testing"></a>
### Пример 4: тестирование API
```
1. Импортируйте описание API (OpenAPI/Swagger)
2. File → Import → Import OpenAPI definition
3. Выполните обход API
4. Выполните Active Scan
```

<a id="example-5-cicd-integration"></a>
### Пример 5: интеграция с CI/CD
```bash
# Конвейер Jenkins
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://staging.myapp.com \
    -r zap_report.html \
    -x zap_report.xml
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-actions"></a>
### Основные действия

| Задача | Способ |
| ------ | -------- |
| Запустить прокси | ZAP автоматически запускает прокси на порту 8080 |
| Spider | Right-click → Spider |
| Active Scan | Right-click → Active Scan |
| Перехватить запрос | Нажать кнопку Break (🛑) |
| Fuzz | Right-click request → Fuzz |
| Создать отчёт | Report → Generate Report |

<a id="keyboard-shortcuts"></a>
### Горячие клавиши

| Сочетание клавиш | Действие |
| ---------- | -------- |
| `Ctrl+Alt+S` | Диалог Spider |
| `Ctrl+Alt+A` | Диалог Active Scan |
| `Ctrl+M` | Ручной запрос |
| `Ctrl+B` | Переключить Break |
| `Ctrl+F` | Найти |
| `Ctrl+R` | Повторить запрос |

<a id="scan-modes"></a>
### Режимы сканирования

| Режим | Когда применять |
| ------ | ---------- |
| **Safe** | Когда изменения недопустимы |
| **Protected** | Проверка только заданных границ |
| **Standard** | Обычное тестирование |
| **ATTACK** | Агрессивные проверки (с разрешения!) |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендуемые практики

<a id="performance-tips"></a>
### Советы по производительности

1. **Ограничивайте границы проверки**
   ```
   Задайте контекст и проверяйте только ресурсы в согласованных границах
   ```

2. **Используйте AJAX Spider для SPA**
   ```
   Современным приложениям может потребоваться AJAX Spider в дополнение к обычному Spider
   ```

3. **Указывайте используемые технологии**
   ```
   Выбирайте только используемые технологии, чтобы сократить время проверки
   ```

<a id="accuracy-tips"></a>
### Советы по точности результатов

1. **Сначала настройте аутентификацию**
   ```
   Настройте аутентификацию для более полного охвата
   ```

2. **Перед автоматизацией исследуйте приложение вручную**
   ```
   Сначала вручную исследуйте приложение и найдите все функции
   ```

3. **Проверяйте все предупреждения**
   ```
   Проверяйте ложные срабатывания и подтверждайте реальные проблемы
   ```

<a id="workflow"></a>
### Последовательность работы

```
1. Настроить прокси
2. Исследовать приложение вручную с аутентификацией
3. Выполнить обход обычным и AJAX Spider
4. Изучить результаты пассивных проверок
5. Выполнить активную проверку
6. Выполнить ручные проверки: фаззинг и граничные случаи
7. Сформировать отчёт
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовая оговорка

> **ПРЕДУПРЕЖДЕНИЕ:** OWASP ZAP — инструмент тестирования безопасности, предназначенный только **для разрешённых проверок**.
> 
> - ✅ Проверяйте собственные приложения
> - ✅ Получайте явное письменное разрешение
> - ✅ Используйте тестовые и промежуточные среды
> - ❌ Не сканируйте рабочие системы без разрешения
> - ❌ Не сканируйте чужие системы
> 
> **Несанкционированное тестирование безопасности незаконно и может повлечь уголовную ответственность.**

---

<a id="-resources"></a>
## 📚 Полезные материалы

<a id="official-resources"></a>
### Официальные материалы
- [Сайт ZAP](https://www.zaproxy.org/)
- [Документация ZAP](https://www.zaproxy.org/docs/)
- [ZAP на GitHub](https://github.com/zaproxy/zaproxy)
- [Документация API ZAP](https://www.zaproxy.org/docs/api/)

<a id="learning-resources"></a>
### Учебные материалы
- [Руководство OWASP по тестированию](https://owasp.org/www-project-web-security-testing-guide/)
- [ZAP in Ten](https://www.zaproxy.org/zap-in-ten/)
- [Подробные видеоразборы ZAP](https://www.zaproxy.org/docs/videos/)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Burp Suite](../../Burp-Suite/translations/README.ru.md)
- [Nikto](../../Nikto/translations/README.ru.md)
- [SQLMap](../../SQLMap/translations/README.ru.md)

---

<p align="center">
  <b>⚡ Освойте тестирование безопасности веб-приложений!</b><br>
  <i>Бесплатный, функциональный и развиваемый сообществом</i>
</p>
