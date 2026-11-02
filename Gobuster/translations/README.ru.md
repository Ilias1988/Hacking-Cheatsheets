<a id="-gobuster---complete-cheatsheet"></a>
# 🚀 Gobuster — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Пояснение логотипа:** Directory/DNS/VHost Brute-Forcing Tool — инструмент перебора каталогов, поддоменов DNS и виртуальных хостов.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗  ██████╗ ██████╗ ██╗   ██╗███████╗████████╗███████╗██████╗ 
  ██╔════╝ ██╔═══██╗██╔══██╗██║   ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗
  ██║  ███╗██║   ██║██████╔╝██║   ██║███████╗   ██║   █████╗  ██████╔╝
  ██║   ██║██║   ██║██╔══██╗██║   ██║╚════██║   ██║   ██╔══╝  ██╔══██╗
  ╚██████╔╝╚██████╔╝██████╔╝╚██████╔╝███████║   ██║   ███████╗██║  ██║
   ╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
                Directory/DNS/VHost Brute-Forcing Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Gobuster-Enumeration-blue?style=for-the-badge" alt="Gobuster">
  <img src="https://img.shields.io/badge/Directory-Bruteforce-red?style=for-the-badge" alt="Directory">
  <img src="https://img.shields.io/badge/DNS-Subdomain-green?style=for-the-badge" alt="DNS">
  <img src="https://img.shields.io/badge/VHost-Discovery-purple?style=for-the-badge" alt="VHost">
</p>

<p align="center">
  <b>🔍 Быстрый инструмент перебора каталогов, DNS и VHost, написанный на Go</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Установка](#-installation)
- [Обзор режимов](#-modes-overview)
- [Режим каталогов (dir)](#-directory-mode-dir)
- [Режим DNS (dns)](#-dns-mode-dns)
- [Режим виртуальных хостов (vhost)](#-vhost-mode-vhost)
- [Режим фаззинга (fuzz)](#-fuzz-mode-fuzz)
- [Словари](#-wordlists)
- [Аутентификация и заголовки](#-authentication--headers)
- [Настройка производительности](#-performance-tuning)
- [Практические примеры](#-real-world-examples)
- [Краткий справочник](#-quick-reference)
- [Советы и рекомендуемые практики](#-tips--best-practices)
- [Полезные материалы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

> **Версии и точность примеров:** параметры и названия режимов зависят от выпуска. [Официальный журнал изменений](https://github.com/OJ/gobuster) отмечает изменения DNS-флагов и замену verbose на debug в 3.7; таблица сравнения передаёт оценки оригинала, а не результаты измерений. Указание «Нет» для рекурсии Ffuf в оригинале неточно: [официальный README ffuf](https://github.com/ffuf/ffuf) описывает рекурсивный поиск с параметром -recursion. Исходные команды сохранены.

> **Уточнение фильтрации:** [параметр --no-status](https://github.com/OJ/gobuster/blob/master/cli/dir/dir.go) скрывает коды в выводе, а не включает ответы 404. Положительный список -s может конфликтовать с установленным по умолчанию чёрным списком. В [общих параметрах](https://github.com/OJ/gobuster/blob/master/cli/options.go) quiet и no-progress различаются; -k и --no-tls-validation — две формы одного параметра. Старые имена вроде --insecuressl в таблице требуют сверки с выбранной версией.


**Gobuster** — быстрый многопоточный инструмент перебора, написанный на Go, для следующих задач:
- Перебор каталогов и файлов на веб-серверах
- Поиск поддоменов DNS
- Поиск виртуальных хостов (VHost)
- Фаззинг URL

<a id="why-gobuster"></a>
### Почему Gobuster?

| Возможность | Описание |
| --------- | ------------- |
| **Быстрый** | Написан на Go, поддерживает высокую параллельность |
| **Гибкий** | Несколько режимов для разных задач |
| **Без рекурсии** | Результаты без рекурсивного обхода |
| **Стабильный** | Корректно обрабатывает ошибки |
| **Активная разработка** | Регулярные обновления |

<a id="comparison-with-other-tools"></a>
### Сравнение с другими инструментами

| Инструмент | Скорость | Рекурсия | Язык |
| ------ | ------- | ----------- | ---------- |
| **Gobuster** | ⭐⭐⭐⭐⭐ | Нет | Go |
| Dirb | ⭐⭐ | Да | C |
| Dirbuster | ⭐⭐⭐ | Да | Java |
| Ffuf | ⭐⭐⭐⭐⭐ | Нет | Go |
| Feroxbuster | ⭐⭐⭐⭐⭐ | Да | Rust |

---

<a id="-installation"></a>
## 📥 Установка

<a id="kali-linux-pre-installed"></a>
### Kali Linux (предустановлен)
```bash
# Обновить Gobuster
sudo apt update && sudo apt install gobuster
```

<a id="ubuntudebian"></a>
### Ubuntu/Debian
```bash
sudo apt install gobuster
```

<a id="from-source-go"></a>
### Из исходного кода (Go)
```bash
go install github.com/OJ/gobuster/v3@latest
```

<a id="using-snap"></a>
### Через Snap
```bash
sudo snap install gobuster
```

<a id="from-github-releases"></a>
### Из выпусков GitHub
```bash
# Скачать последний выпуск
wget https://github.com/OJ/gobuster/releases/latest/download/gobuster-linux-amd64.7z
7z x gobuster-linux-amd64.7z
chmod +x gobuster
sudo mv gobuster /usr/local/bin/
```

<a id="verify-installation"></a>
### Проверка установки
```bash
gobuster version
gobuster -h
```

---

<a id="-modes-overview"></a>
## 📊 Обзор режимов

<a id="available-modes"></a>
### Доступные режимы

| Режим | Описание | Применение |
| ------ | ------------- | ---------- |
| `dir` | Перебор каталогов и файлов | Поиск скрытых каталогов и файлов |
| `dns` | Перебор поддоменов DNS | Поиск поддоменов |
| `vhost` | Перебор виртуальных хостов | Поиск виртуальных хостов |
| `fuzz` | Фаззинг URL | Фаззинг параметров и путей |
| `s3` | Перебор бакетов S3 | Поиск бакетов AWS S3 |
| `gcs` | Google Cloud Storage | Поиск бакетов GCS |
| `tftp` | Перебор ресурсов TFTP | Поиск файлов TFTP |

<a id="basic-syntax"></a>
### Базовый синтаксис
```bash
gobuster [mode] [options]

# Примеры
gobuster dir -u http://target.com -w wordlist.txt
gobuster dns -d target.com -w subdomains.txt
gobuster vhost -u http://target.com -w vhosts.txt
```

---

<a id="-directory-mode-dir"></a>
## 📁 Режим каталогов (dir)

<a id="basic-syntax-1"></a>
### Базовый синтаксис
```bash
gobuster dir -u <URL> -w <wordlist>
```

<a id="essential-options"></a>
### Основные параметры

| Параметр | Описание |
| -------- | ------------- |
| `-u` | URL цели |
| `-w` | Путь к словарю |
| `-t` | Число потоков (по умолчанию: 10) |
| `-x` | Расширения файлов для поиска |
| `-s` | Разрешённые коды состояния |
| `-b` | Исключаемые коды состояния (чёрный список) |
| `-o` | Файл результатов |
| `-k` | Пропустить проверку сертификата SSL |
| `-r` | Следовать перенаправлениям |
| `-a` | Строка User-Agent |
| `-c` | Cookie |
| `-H` | Собственные заголовки |

<a id="basic-directory-scan"></a>
### Базовый поиск каталогов
```bash
# Простой поиск каталогов
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt

# С большим числом потоков
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt -t 50
```

<a id="file-extension-search"></a>
### Поиск по расширениям файлов
```bash
# Искать определённые расширения
gobuster dir -u http://target.com -w wordlist.txt -x php,html,txt

# Несколько расширений
gobuster dir -u http://target.com -w wordlist.txt -x php,asp,aspx,jsp,html,txt,bak,old

# Резервные копии файлов
gobuster dir -u http://target.com -w wordlist.txt -x bak,backup,old,orig,~
```

<a id="status-code-filtering"></a>
### Фильтрация кодов состояния
```bash
# Показывать только заданные коды состояния
gobuster dir -u http://target.com -w wordlist.txt -s 200,204,301,302,307,401

# Исключить коды состояния
gobuster dir -u http://target.com -w wordlist.txt -b 404,403

# Показать всё, включая 404 (подпись оригинала; см. уточнение)
gobuster dir -u http://target.com -w wordlist.txt --no-status
```

<a id="output-options"></a>
### Параметры вывода
```bash
# Сохранить в файл
gobuster dir -u http://target.com -w wordlist.txt -o results.txt

# Подробный вывод
gobuster dir -u http://target.com -w wordlist.txt -v

# Без индикатора прогресса (подпись оригинала; см. уточнение)
gobuster dir -u http://target.com -w wordlist.txt -q

# Расширенный режим (полные URL)
gobuster dir -u http://target.com -w wordlist.txt -e
```

<a id="ssltls-options"></a>
### Параметры SSL/TLS
```bash
# Пропустить проверку сертификата SSL
gobuster dir -u https://target.com -w wordlist.txt -k

# Пропустить проверку SSL с дополнительными параметрами
gobuster dir -u https://target.com -w wordlist.txt -k --no-tls-validation
```

<a id="directory-mode-examples"></a>
### Примеры режима каталогов
```bash
# Стандартный поиск веб-каталогов
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt -t 50

# Приложение PHP
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x php -t 50

# Поиск конечных точек API
gobuster dir -u http://target.com/api -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt -t 30

# Найти резервные копии
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x bak,backup,old,zip,tar.gz -t 50
```

---

<a id="-dns-mode-dns"></a>
## 🌐 Режим DNS (dns)

<a id="basic-syntax-2"></a>
### Базовый синтаксис
```bash
gobuster dns -d <domain> -w <wordlist>
```

<a id="dns-options"></a>
### Параметры DNS

| Параметр | Описание |
| -------- | ------------- |
| `-d` | Домен цели |
| `-w` | Путь к словарю |
| `-t` | Потоки |
| `-r` | Собственный DNS-резолвер |
| `-i` | Показывать IP-адреса |
| `-c` | Показывать записи CNAME |
| `--wildcard` | Принудительно обрабатывать wildcard-ответы |
| `--timeout` | Время ожидания DNS |

<a id="basic-dns-enumeration"></a>
### Базовый сбор сведений DNS
```bash
# Простой поиск поддоменов
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# Показывать IP-адреса
gobuster dns -d target.com -w subdomains.txt -i

# Показывать CNAME
gobuster dns -d target.com -w subdomains.txt -c
```

<a id="custom-dns-resolver"></a>
### Собственный DNS-резолвер
```bash
# Использовать определённый сервер DNS
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8

# Несколько резолверов (подпись оригинала; здесь задан один адрес с портом)
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8:53
```

<a id="dns-examples"></a>
### Примеры DNS
```bash
# Подробный поиск поддоменов
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt -t 50 -i

# Быстрый поиск поддоменов
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt -t 100

# С сохранением результатов
gobuster dns -d target.com -w subdomains.txt -i -o subdomains_found.txt
```

---

<a id="-vhost-mode-vhost"></a>
## 🏠 Режим виртуальных хостов (vhost)

<a id="basic-syntax-3"></a>
### Базовый синтаксис
```bash
gobuster vhost -u <URL> -w <wordlist>
```

<a id="vhost-options"></a>
### Параметры VHost

| Параметр | Описание |
| -------- | ------------- |
| `-u` | URL цели |
| `-w` | Путь к словарю |
| `-t` | Потоки |
| `--append-domain` | Добавлять домен к записям словаря |
| `--domain` | Домен для виртуальных хостов |
| `-r` | Следовать перенаправлениям |

<a id="basic-vhost-enumeration"></a>
### Базовый поиск виртуальных хостов
```bash
# Простой поиск виртуальных хостов
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# С добавлением домена
gobuster vhost -u http://target.com -w subdomains.txt --append-domain
```

<a id="vhost-examples"></a>
### Примеры VHost
```bash
# Стандартный поиск виртуальных хостов
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/namelist.txt -t 50

# Хосты для разработки
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt --append-domain

# С собственным доменом
gobuster vhost -u http://10.10.10.10 -w subdomains.txt --domain target.htb
```

---

<a id="-fuzz-mode-fuzz"></a>
## 🔧 Режим фаззинга (fuzz)

<a id="basic-syntax-4"></a>
### Базовый синтаксис
```bash
gobuster fuzz -u <URL with FUZZ> -w <wordlist>
```

<a id="fuzz-options"></a>
### Параметры фаззинга

| Параметр | Описание |
| -------- | ------------- |
| `-u` | URL с маркером FUZZ |
| `-w` | Путь к словарю |
| `-t` | Потоки |
| `-b` | Исключаемые коды состояния |
| `--exclude-length` | Исключать по длине ответа |

<a id="fuzz-examples"></a>
### Примеры фаззинга
```bash
# Фаззинг значения параметра
gobuster fuzz -u "http://target.com/page?id=FUZZ" -w numbers.txt

# Фаззинг пути
gobuster fuzz -u "http://target.com/FUZZ/admin" -w wordlist.txt

# Исключить определённые длины
gobuster fuzz -u "http://target.com/FUZZ" -w wordlist.txt --exclude-length 0,42
```

---

<a id="-wordlists"></a>
## 📚 Словари

<a id="seclists-locations-kali-linux"></a>
### Расположение SecLists (Kali Linux)

```bash
# Перебор каталогов и файлов
/usr/share/seclists/Discovery/Web-Content/

# Популярные словари каталогов
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-small.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# DNS и поддомены
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

<a id="default-wordlists"></a>
### Стандартные словари

```bash
# Словари Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
/usr/share/wordlists/dirb/small.txt

# Словари Dirbuster
/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
/usr/share/wordlists/dirbuster/directory-list-2.3-small.txt
```

<a id="wordlist-recommendations"></a>
### Выбор словаря

| Применение | Словарь |
| ---------- | ---------- |
| Быстрая проверка | `common.txt` |
| Стандартная проверка | `directory-list-2.3-medium.txt` |
| Подробная проверка | `directory-list-2.3-big.txt` |
| API | `api-endpoints.txt` |
| Поддомены | `subdomains-top1million-5000.txt` |
| Файлы | `raft-large-files.txt` |

---

<a id="-authentication--headers"></a>
## 🔐 Аутентификация и заголовки

<a id="basic-authentication"></a>
### Базовая аутентификация
```bash
# Базовая аутентификация HTTP
gobuster dir -u http://target.com -w wordlist.txt -U username -P password
```

<a id="cookie-authentication"></a>
### Аутентификация с помощью cookie
```bash
# Одна cookie
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123"

# Несколько cookie
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123; token=xyz789"

# Сеанс PHP
gobuster dir -u http://target.com -w wordlist.txt -c "PHPSESSID=abc123def456"
```

<a id="custom-headers"></a>
### Собственные заголовки
```bash
# Один заголовок
gobuster dir -u http://target.com -w wordlist.txt -H "Authorization: Bearer token123"

# Несколько заголовков
gobuster dir -u http://target.com -w wordlist.txt -H "X-Forwarded-For: 127.0.0.1" -H "X-Custom: value"

# Ключ API
gobuster dir -u http://target.com/api -w wordlist.txt -H "X-API-Key: your-api-key"
```

<a id="user-agent"></a>
### User-Agent
```bash
# Собственный User-Agent
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Googlebot
gobuster dir -u http://target.com -w wordlist.txt -a "Googlebot/2.1"
```

<a id="proxy-support"></a>
### Поддержка прокси
```bash
# Прокси HTTP
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080

# Прокси SOCKS5
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050

# Прокси Burp Suite
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080 -k
```

---

<a id="-performance-tuning"></a>
## ⚡ Настройка производительности

<a id="thread-management"></a>
### Управление потоками
```bash
# По умолчанию: 10 потоков
gobuster dir -u http://target.com -w wordlist.txt

# Увеличить число потоков (быстрая сеть)
gobuster dir -u http://target.com -w wordlist.txt -t 100

# Уменьшить число потоков (медленная или нестабильная сеть)
gobuster dir -u http://target.com -w wordlist.txt -t 5
```

<a id="timeout-settings"></a>
### Настройка времени ожидания
```bash
# Увеличить время ожидания
gobuster dir -u http://target.com -w wordlist.txt --timeout 30s

# Время ожидания DNS
gobuster dns -d target.com -w subdomains.txt --timeout 5s
```

<a id="delay-between-requests"></a>
### Задержка между запросами
```bash
# Добавить задержку (ограничение частоты запросов)
gobuster dir -u http://target.com -w wordlist.txt --delay 100ms

# Замедлить для снижения заметности для WAF
gobuster dir -u http://target.com -w wordlist.txt --delay 500ms
```

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-standard-web-enumeration"></a>
### Пример 1: стандартное исследование веб-ресурсов
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt \
    -t 50 \
    -x php,html,txt \
    -o results.txt
```

<a id="example-2-api-endpoint-discovery"></a>
### Пример 2: поиск конечных точек API
```bash
gobuster dir -u http://target.com/api/v1 \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -t 30 \
    -H "Content-Type: application/json"
```

<a id="example-3-authenticated-scan"></a>
### Пример 3: проверка с аутентификацией
```bash
gobuster dir -u http://target.com/admin \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -c "PHPSESSID=abc123" \
    -t 20
```

<a id="example-4-subdomain-enumeration"></a>
### Пример 4: поиск поддоменов
```bash
gobuster dns -d target.com \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt \
    -t 100 \
    -i \
    -o subdomains.txt
```

<a id="example-5-vhost-discovery-hackthebox"></a>
### Пример 5: поиск VHost (HackTheBox)
```bash
gobuster vhost -u http://10.10.10.10 \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    --domain target.htb \
    --append-domain
```

<a id="example-6-through-burp-proxy"></a>
### Пример 6: через прокси Burp
```bash
gobuster dir -u https://target.com \
    -w /usr/share/wordlists/dirb/common.txt \
    --proxy http://127.0.0.1:8080 \
    -k \
    -t 20
```

<a id="example-7-backup-file-search"></a>
### Пример 7: поиск резервных копий
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -x bak,backup,old,orig,save,swp,txt,zip,tar,gz,sql \
    -t 50
```

<a id="example-8-cms-detection"></a>
### Пример 8: определение CMS
```bash
# WordPress
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/wordpress.fuzz.txt \
    -t 50

# Joomla
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/joomla-plugins.fuzz.txt
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Задача | Команда |
| ------ | --------- |
| Базовый поиск каталогов | `gobuster dir -u URL -w wordlist.txt` |
| С расширениями | `gobuster dir -u URL -w wordlist.txt -x php,html` |
| Поиск поддоменов | `gobuster dns -d domain.com -w wordlist.txt` |
| Поиск виртуальных хостов | `gobuster vhost -u URL -w wordlist.txt` |
| С сохранением результатов | `gobuster dir -u URL -w wordlist.txt -o output.txt` |
| Пропустить ошибки SSL | `gobuster dir -u URL -w wordlist.txt -k` |
| С cookie | `gobuster dir -u URL -w wordlist.txt -c "session=x"` |
| Через прокси | `gobuster dir -u URL -w wordlist.txt --proxy http://127.0.0.1:8080` |

<a id="common-options"></a>
### Распространённые параметры

| Параметр | Краткая форма | Описание |
| -------- | ------- | ------------- |
| `--url` | `-u` | URL цели |
| `--wordlist` | `-w` | Файл словаря |
| `--threads` | `-t` | Число потоков |
| `--extensions` | `-x` | Расширения файлов |
| `--status-codes` | `-s` | Разрешённые коды состояния |
| `--cookies` | `-c` | Отправляемые cookie |
| `--output` | `-o` | Файл результатов |
| `--insecuressl` | `-k` | Пропустить проверку сертификата SSL |
| `--verbose` | `-v` | Подробный вывод |
| `--quiet` | `-q` | Тихий режим |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендуемые практики

<a id="efficiency-tips"></a>
### Советы по эффективности

1. **Начинайте с малого и расширяйте поиск**
   ```bash
   # Сначала быстрая проверка
   gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt
   
   # Затем более крупный словарь
   gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
   ```

2. **Выбирайте расширения под целевую технологию**
   ```bash
   # Сайт на PHP
   gobuster dir -u http://target.com -w wordlist.txt -x php
   
   # Сайт на ASP.NET
   gobuster dir -u http://target.com -w wordlist.txt -x asp,aspx
   ```

3. **Сочетайте с другими инструментами**
   ```bash
   # Последовательность Nmap → Gobuster
   nmap -sV -p 80,443 target.com
   gobuster dir -u http://target.com -w wordlist.txt
   ```

<a id="avoiding-detection"></a>
### Снижение заметности

```bash
# Медленная проверка с задержкой
gobuster dir -u http://target.com -w wordlist.txt -t 5 --delay 200ms

# Собственный User-Agent
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0..."

# Через Tor
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовая оговорка

> **ПРЕДУПРЕЖДЕНИЕ:** Gobuster — мощный инструмент перебора ресурсов, который следует использовать только **для разрешённого тестирования**.
> 
> - ✅ Проверяйте собственные системы
> - ✅ Получайте явное письменное разрешение
> - ✅ Используйте в заданиях CTF и лабораториях
> - ❌ Не сканируйте без разрешения
> - ❌ Несанкционированный перебор ресурсов может быть незаконным
> 
> **Всегда получайте разрешение перед сканированием любой системы.**

---

<a id="-resources"></a>
## 📚 Полезные материалы

<a id="official-resources"></a>
### Официальные материалы
- [Gobuster GitHub](https://github.com/OJ/gobuster)
- [Gobuster Wiki](https://github.com/OJ/gobuster/wiki)

<a id="wordlists"></a>
### Словари
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Nmap](../../Nmap/translations/README.ru.md)
- [Burp Suite](../../Burp-Suite/README.md)
- [SQLMap](../../SQLMap/README.md)

---

<p align="center">
  <b>🚀 Освойте исследование веб-ресурсов!</b><br>
  <i>Находите то, что не видно другим</i>
</p>
