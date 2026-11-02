<a id="-ffuf---complete-cheatsheet"></a>
# 🚀 ffuf — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   __  __        __ 
  / _|/ _|_   _ / _|
 | |_| |_| | | | |_ 
 |  _|  _| |_| |  _|
 |_| |_|  \__,_|_|  
                    
   Fuzz Faster U Fool
```

<p align="center">
  <img src="https://img.shields.io/badge/ffuf-Web_Fuzzer-red?style=for-the-badge" alt="ffuf">
  <img src="https://img.shields.io/badge/Fast-Fuzzing-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Go-Language-green?style=for-the-badge" alt="Go">
</p>

<p align="center">
  <b>⚡ Быстрый веб-фаззер, написанный на Go</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Установка](#-installation)
- [Основной синтаксис](#-basic-syntax)
- [Фаззинг каталогов](#-directory-fuzzing)
- [Фаззинг поддоменов](#-subdomain-fuzzing)
- [Фаззинг параметров](#-parameter-fuzzing)
- [Фаззинг данных POST](#-post-data-fuzzing)
- [Фильтры и условия совпадения](#-filtering--matching)
- [Дополнительные возможности](#-advanced-features)
- [Практические примеры](#-real-world-examples)
- [Краткий справочник](#-quick-reference)
- [Советы и рекомендуемые практики](#-tips--best-practices)
- [Полезные материалы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

> **Уточнения к оригиналу:** параметры и примеры зависят от версии; v2.x.x в ссылке скачивания — заполнитель версии. Таблица сравнения передаёт упрощённые оценки исходника, а не результаты измерений. Название Subdomain Fuzzing объединяет разные задачи: изменение Host проверяет виртуальные хосты, а поддомен в URL требует DNS-разрешения. В [официальной справке ffuf](https://github.com/ffuf/ffuf) -r означает переход по перенаправлениям, а не выбор resolver; -se останавливает работу при неожиданных ошибках, тогда как условие более 95% ответов 403 относится к -sf. Исходные команды сохранены.

> **Фильтры и режимы:** -ft/-mt сравнивают время до первого байта ответа с порогом; официальные примеры требуют сравнения вида >100 или <100, поэтому отдельное 5000 из таблицы не является полным выражением. Несколько словарей в режиме clusterbomb дают все комбинации, что отличается от проверки готовых пар учётных данных. Автокалибровка и ограничение частоты не гарантируют точность результатов, обход WAF или незаметность. Подпись Fuzz Faster U Fool на ASCII-эмблеме оставлена без изменения рисунка.

**ffuf** (Fuzz Faster U Fool) — быстрый веб-фаззер на Go для перебора каталогов, поиска виртуальных хостов и фаззинга параметров в рамках Bug Bounty.

<a id="key-features"></a>
### Основные возможности

| Возможность | Описание |
| --------- | ------------- |
| **Скорость** | Высокая скорость благодаря Go |
| **Гибкость** | Фаззинг любой части HTTP-запроса |
| **Фильтрация** | Несколько способов фильтрации и поиска совпадений |
| **Рекурсия** | Поддержка рекурсивного сканирования |
| **Вывод** | Несколько форматов вывода |
| **Простота** | Простой и понятный синтаксис |

<a id="ffuf-vs-other-tools"></a>
### ffuf и другие инструменты

| Возможность | ffuf | Gobuster | dirb |
| --------- | ------ | ---------- | ------ |
| **Скорость** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |
| **Гибкость** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| **Фаззинг POST** | ✅ Да | ❌ Нет | ❌ Нет |
| **Рекурсия** | ✅ Да | ✅ Да | ✅ Да |
| **Фильтры** | Много | Ограниченно | Ограниченно |

---

<a id="-installation"></a>
## 📥 Установка

<a id="using-go"></a>
### Через Go
```bash
go install github.com/ffuf/ffuf/v2@latest
```

<a id="kali-linux"></a>
### Kali Linux
```bash
sudo apt install ffuf
```

<a id="binary-download"></a>
### Скачивание исполняемого файла
```bash
# Скачать из раздела releases
https://github.com/ffuf/ffuf/releases

# Linux
wget https://github.com/ffuf/ffuf/releases/download/v2.x.x/ffuf_2.x.x_linux_amd64.tar.gz
tar xzf ffuf_2.x.x_linux_amd64.tar.gz
sudo mv ffuf /usr/local/bin/
```

<a id="verify-installation"></a>
### Проверка установки
```bash
ffuf -V
ffuf -h
```

---

<a id="-basic-syntax"></a>
## ⌨️ Основной синтаксис

<a id="general-syntax"></a>
### Общий синтаксис
```bash
ffuf [options] -u <URL/FUZZ> -w <wordlist>
```

<a id="the-fuzz-keyword"></a>
### Ключевое слово FUZZ

Ключевое слово **FUZZ** указывает ffuf, куда подставлять слова из словаря.

```bash
# В пути URL
ffuf -u https://target.com/FUZZ -w wordlist.txt

# В имени поддомена
ffuf -u https://FUZZ.target.com -w wordlist.txt

# В значении параметра
ffuf -u https://target.com/page?id=FUZZ -w wordlist.txt

# В данных POST
ffuf -u https://target.com/login -X POST -d "user=FUZZ&pass=test" -w wordlist.txt
```

<a id="essential-options"></a>
### Основные параметры

| Параметр | Описание |
| -------- | ------------- |
| `-u` | Целевой URL с ключевым словом FUZZ |
| `-w` | Путь к словарю |
| `-o` | Выходной файл |
| `-of` | Формат вывода (json, csv, html) |
| `-t` | Число потоков (по умолчанию: 40) |
| `-p` | Задержка между запросами |
| `-r` | Следовать перенаправлениям |
| `-v` | Подробный вывод |
| `-s` | Тихий режим |

---

<a id="-directory-fuzzing"></a>
## 📁 Фаззинг каталогов

<a id="basic-directory-brute-force"></a>
### Базовый перебор каталогов

```bash
# Базовый фаззинг каталогов
ffuf -u https://target.com/FUZZ -w /usr/share/wordlists/dirb/common.txt

# С расширениями файлов
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.html,.txt,.bak

# Несколько расширений
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.asp,.aspx,.jsp,.html
```

<a id="recursive-scanning"></a>
### Рекурсивное сканирование

```bash
# Включить рекурсию
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion

# Глубина рекурсии
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2

# Рекурсия с выбором стратегии
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-strategy greedy
```

<a id="filter-by-status-code"></a>
### Фильтрация по коду ответа

```bash
# Отобрать только ответы 200
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200

# Отобрать несколько кодов ответа
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200,301,302,403

# Исключить ответы 404 (формулировка оригинала о поведении по умолчанию)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404

# Сопоставлять все ответы и исключить 404
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

<a id="popular-wordlists"></a>
### Популярные словари

```bash
# SecLists
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
```

---

<a id="-subdomain-fuzzing"></a>
## 🌐 Фаззинг поддоменов

<a id="virtual-host-discovery"></a>
### Поиск виртуальных хостов

```bash
# Базовый фаззинг поддоменов
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt

# Фильтрация по размеру ответа (исключить типовую страницу)
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt -fs 1234

# С определённым словарём
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

<a id="dns-subdomain-fuzzing"></a>
### Фаззинг поддоменов DNS

```bash
# Поддомен в URL
ffuf -u https://FUZZ.target.com -w subdomains.txt

# С resolver (подпись оригинала; см. уточнение)
ffuf -u https://FUZZ.target.com -w subdomains.txt -r
```

<a id="common-subdomain-wordlists"></a>
### Часто используемые словари поддоменов

```bash
# SecLists DNS
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

---

<a id="-parameter-fuzzing"></a>
## 🔍 Фаззинг параметров

<a id="get-parameter-fuzzing"></a>
### Фаззинг параметров GET

```bash
# Фаззинг имени параметра
ffuf -u "https://target.com/page?FUZZ=test" -w params.txt

# Фаззинг значения параметра
ffuf -u "https://target.com/page?id=FUZZ" -w values.txt

# Несколько параметров
ffuf -u "https://target.com/page?W1=test&W2=test" -w params.txt:W1 -w values.txt:W2
```

<a id="parameter-discovery"></a>
### Поиск параметров

```bash
# Поиск скрытых параметров
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

---

<a id="-post-data-fuzzing"></a>
## 📝 Фаззинг данных POST

<a id="basic-post-fuzzing"></a>
### Базовый фаззинг POST

```bash
# POST с данными формы
ffuf -u https://target.com/login -X POST \
    -d "username=FUZZ&password=test" \
    -w usernames.txt

# POST с указанием типа содержимого
ffuf -u https://target.com/login -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=FUZZ&password=test" \
    -w usernames.txt
```

<a id="json-post-fuzzing"></a>
### Фаззинг JSON в POST

```bash
# Данные JSON в POST
ffuf -u https://target.com/api/login -X POST \
    -H "Content-Type: application/json" \
    -d '{"username":"FUZZ","password":"test"}' \
    -w usernames.txt
```

<a id="multiple-wordlists-credential-stuffing"></a>
### Несколько словарей (в оригинале — Credential Stuffing)

```bash
# Фаззинг имени пользователя и пароля
ffuf -u https://target.com/login -X POST \
    -d "username=USER&password=PASS" \
    -w users.txt:USER \
    -w passwords.txt:PASS \
    -fc 401

# Режим: clusterbomb (все комбинации)
ffuf -u https://target.com/login -X POST \
    -d "user=USER&pass=PASS" \
    -w users.txt:USER \
    -w passes.txt:PASS \
    -mode clusterbomb
```

---

<a id="-filtering--matching"></a>
## 🎯 Фильтры и условия совпадения

<a id="filter-options"></a>
### Параметры фильтрации

| Параметр | Описание | Пример |
| -------- | ------------- | --------- |
| `-fc` | Фильтровать по коду ответа | `-fc 404,403` |
| `-fs` | Фильтровать по размеру ответа | `-fs 1234` |
| `-fw` | Фильтровать по числу слов | `-fw 100` |
| `-fl` | Фильтровать по числу строк | `-fl 50` |
| `-ft` | Фильтровать по времени (мс) | `-ft 5000` |
| `-fr` | Фильтровать по регулярному выражению | `-fr "error"` |

<a id="match-options"></a>
### Параметры совпадений

| Параметр | Описание | Пример |
| -------- | ------------- | --------- |
| `-mc` | Искать совпадения по кодам ответа | `-mc 200,301` |
| `-ms` | Искать совпадения по размеру ответа | `-ms 1234` |
| `-mw` | Искать совпадения по числу слов | `-mw 100` |
| `-ml` | Искать совпадения по числу строк | `-ml 50` |
| `-mt` | Искать совпадения по времени (мс) | `-mt 5000` |
| `-mr` | Искать совпадения по регулярному выражению | `-mr "success"` |

<a id="filter-examples"></a>
### Примеры фильтрации

```bash
# Исключить ответы 404 и 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404,403

# Фильтрация по размеру (исключить типовую страницу)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fs 0,1234

# Фильтрация по числу слов
ffuf -u https://target.com/FUZZ -w wordlist.txt -fw 10

# Сопоставлять все ответы, исключая выбранные
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

<a id="auto-calibration"></a>
### Автокалибровка

```bash
# Автоматически настроить фильтры
ffuf -u https://target.com/FUZZ -w wordlist.txt -ac

# Калибровка с заданной строкой
ffuf -u https://target.com/FUZZ -w wordlist.txt -acc "randomstring123"
```

---

<a id="-advanced-features"></a>
## ⚡ Дополнительные возможности

<a id="rate-limiting"></a>
### Ограничение частоты запросов

```bash
# Запросов в секунду
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 100

# Задержка между запросами
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.5

# Случайная задержка (jitter)
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.1-1.0
```

<a id="threads"></a>
### Потоки

```bash
# Установить число потоков (по умолчанию: 40)
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 100

# Уменьшить параллелизм (в оригинале — для скрытности)
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 10 -p 1
```

<a id="headers"></a>
### Заголовки

```bash
# Собственный заголовок
ffuf -u https://target.com/FUZZ -w wordlist.txt -H "X-Custom: value"

# Несколько заголовков
ffuf -u https://target.com/FUZZ -w wordlist.txt \
    -H "Authorization: Bearer token" \
    -H "X-Forwarded-For: 127.0.0.1"

# Cookie
ffuf -u https://target.com/FUZZ -w wordlist.txt -b "session=abc123"
```

<a id="proxy"></a>
### Прокси

```bash
# HTTP-прокси
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080

# Burp Suite
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080
```

<a id="output"></a>
### Вывод

```bash
# Вывод JSON
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.json -of json

# Вывод CSV
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.csv -of csv

# Вывод HTML
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.html -of html

# Все форматы
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results -of all
```

<a id="timeout--retries"></a>
### Время ожидания и повторы

```bash
# Время ожидания ответа
ffuf -u https://target.com/FUZZ -w wordlist.txt -timeout 10

# Остановиться при 403 (подпись оригинала; см. уточнение)
ffuf -u https://target.com/FUZZ -w wordlist.txt -se

# Игнорировать ошибки SSL
ffuf -u https://target.com/FUZZ -w wordlist.txt -k
```

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-basic-directory-scan"></a>
### Пример 1: базовый поиск каталогов
```bash
ffuf -u https://target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/common.txt -mc 200,301,302
```

<a id="example-2-find-php-files"></a>
### Пример 2: поиск файлов PHP
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.php.bak,.php~ -mc 200
```

<a id="example-3-subdomain-enumeration"></a>
### Пример 3: сбор сведений о поддоменах
```bash
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    -fs 1234 -t 100
```

<a id="example-4-api-endpoint-discovery"></a>
### Пример 4: поиск конечных точек API
```bash
ffuf -u https://api.target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -mc 200,401,403
```

<a id="example-5-parameter-discovery"></a>
### Пример 5: поиск параметров
```bash
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

<a id="example-6-login-brute-force"></a>
### Пример 6: перебор данных входа
```bash
ffuf -u https://target.com/login -X POST \
    -d "user=admin&pass=FUZZ" \
    -w /usr/share/seclists/Passwords/Common-Credentials/10k-most-common.txt \
    -fc 401 -t 10
```

<a id="example-7-with-proxy-burp"></a>
### Пример 7: через прокси Burp
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080 -k
```

<a id="example-8-recursive-scan"></a>
### Пример 8: рекурсивное сканирование
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2 -mc 200,301
```

<a id="example-9-rate-limited-scan"></a>
### Пример 9: ограничение частоты запросов
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 10 -t 5
```

<a id="example-10-complete-bug-bounty-scan"></a>
### Пример 10: комплексная проверка для Bug Bounty
```bash
ffuf -u https://target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt \
    -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak \
    -mc 200,301,302,403 \
    -recursion -recursion-depth 2 \
    -o results.json -of json \
    -ac
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Задача | Команда |
| ------ | --------- |
| Поиск каталогов | `ffuf -u URL/FUZZ -w wordlist.txt` |
| С расширениями | `ffuf -u URL/FUZZ -w wordlist.txt -e .php,.html` |
| Поиск поддоменов | `ffuf -u URL -H "Host: FUZZ.domain" -w subs.txt` |
| Фаззинг POST | `ffuf -u URL -X POST -d "param=FUZZ" -w wordlist.txt` |
| Исключить ответы 404 | `ffuf -u URL/FUZZ -w wordlist.txt -fc 404` |
| Фильтровать по размеру | `ffuf -u URL/FUZZ -w wordlist.txt -fs 1234` |
| Автокалибровка | `ffuf -u URL/FUZZ -w wordlist.txt -ac` |
| Сохранить вывод | `ffuf -u URL/FUZZ -w wordlist.txt -o out.json -of json` |

<a id="common-options"></a>
### Часто используемые параметры

| Параметр | Описание |
| -------- | ------------- |
| `-u` | URL с FUZZ |
| `-w` | Словарь |
| `-e` | Расширения |
| `-t` | Потоки |
| `-mc` | Коды совпадений |
| `-fc` | Исключаемые коды |
| `-fs` | Исключаемый размер |
| `-ac` | Автокалибровка |
| `-r` | Следовать перенаправлениям |
| `-o` | Выходной файл |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендуемые практики

<a id="bug-bounty-tips"></a>
### Советы для Bug Bounty

1. **Сначала выполняйте автокалибровку**
   ```bash
   ffuf -u target.com/FUZZ -w wordlist.txt -ac
   ```

2. **Используйте несколько расширений**
   ```bash
   -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak,.old,.zip
   ```

3. **Проверяйте типичные расширения резервных копий**
   ```bash
   -e .bak,.old,.orig,.backup,~,.swp
   ```

4. **Ограничение частоты для проверки обхода WAF**
   ```bash
   -rate 10 -t 5 -p 0.5
   ```

<a id="performance-tips"></a>
### Советы по производительности

```bash
# Высокая скорость (без WAF)
ffuf -u URL/FUZZ -w wordlist.txt -t 200

# Малая частота и скорость (в оригинале — для скрытности)
ffuf -u URL/FUZZ -w wordlist.txt -t 10 -rate 5 -p 1
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовая оговорка

> **ПРЕДУПРЕЖДЕНИЕ:** ffuf — мощный инструмент фаззинга, который следует использовать только **для разрешённого тестирования**.
> 
> - ✅ Проверяйте программы, на тестирование которых получено разрешение
> - ✅ Используйте в согласованных программах Bug Bounty
> - ✅ Соблюдайте ограничения частоты и границы проверки
> - ❌ Не выполняйте фаззинг без разрешения
> - ❌ Не вызывайте отказ в обслуживании
> 
> **Несанкционированное сканирование незаконно.**

---

<a id="-resources"></a>
## 📚 Полезные материалы

<a id="official-resources"></a>
### Официальные материалы
- [ffuf GitHub](https://github.com/ffuf/ffuf)
- [ffuf Wiki](https://github.com/ffuf/ffuf/wiki)

<a id="wordlists"></a>
### Словари
- [SecLists](https://github.com/danielmiessler/SecLists)
- [Assetnote Wordlists](https://wordlists.assetnote.io/)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Nuclei](../../Nuclei/translations/README.ru.md)
- [Gobuster](../../Gobuster/translations/README.ru.md)
- [Nmap](../../Nmap/translations/README.ru.md)

---

<p align="center">
  <b>🚀 Быстрее исследуйте — больше находите!</b><br>
  <i>Фаззер для исследователя Bug Bounty</i>
</p>
