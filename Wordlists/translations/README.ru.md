<a id="-wordlists---complete-reference-guide"></a>
# 📚 Словари — полный справочник

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🏠 Русская главная](../../README.ru.md)

```
  ██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ██╗███████╗████████╗███████╗
  ██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██║██╔════╝╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     ██║███████╗   ██║   ███████╗
  ██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██║╚════██║   ██║   ╚════██║
  ╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗██║███████║   ██║   ███████║
   ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Wordlists-Reference-red?style=for-the-badge" alt="Wordlists">
  <img src="https://img.shields.io/badge/SecLists-orange?style=for-the-badge" alt="SecLists">
  <img src="https://img.shields.io/badge/Brute_Force-blue?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/Fuzzing-green?style=for-the-badge" alt="Fuzzing">
</p>

<p align="center">
  <b>📖 Полный справочник словарей для тестирования на проникновение</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Словари паролей](#-password-wordlists)
- [Словари каталогов и файлов](#-directoryfile-wordlists)
- [Словари поддоменов](#-subdomain-wordlists)
- [Словари имён пользователей](#-username-wordlists)
- [Словари для веб-фаззинга](#-web-fuzzing-wordlists)
- [Коллекция SecLists](#-seclists-collection)
- [Стандартные словари Kali](#-kali-default-wordlists)
- [Инструменты создания собственных словарей](#-custom-wordlist-tools)
- [Быстрая загрузка](#-quick-download)
- [Советы и рекомендации](#-tips--best-practices)

---

<a id="-password-wordlists"></a>
## 🔐 Словари паролей

<a id="most-popular"></a>
### Самые популярные

| Словарь | Размер | Строк | Источник | Применение |
|----------|------|-------|--------|----------|
| **rockyou.txt** | 139 MB | 14.3M | Kali/SecLists | Подбор паролей |
| **darkweb2017-top10000.txt** | 83 KB | 10K | SecLists | Распространённые пароли |
| **xato-net-10-million-passwords.txt** | 83 MB | 10M | SecLists | Большой список паролей |
| **common-passwords-win.txt** | - | - | SecLists | Пароли Windows |
| **10-million-password-list-top-1000000.txt** | 8.1 MB | 1M | SecLists | 1M наиболее частых паролей |

<a id="password-lists-by-type"></a>
### Списки паролей по типам

> **Пути и размеры:** структура и числовые оценки отражают оригинал, а не проверенный состав любой версии. В SecLists некоторые списки перемещены или поставляются в архивах; точные имена и расположение нужно сверять с [текущим репозиторием](https://github.com/danielmiessler/SecLists). Размеры и числа строк приблизительны и зависят от редакции.

```
SecLists/Passwords/
├── Common-Credentials/
│   ├── 10-million-password-list-top-100.txt
│   ├── 10-million-password-list-top-1000.txt
│   ├── 10-million-password-list-top-10000.txt
│   ├── 10-million-password-list-top-100000.txt
│   └── 10-million-password-list-top-1000000.txt
├── Leaked-Databases/
│   └── rockyou.txt
├── darkweb2017-top1000.txt
├── darkweb2017-top10000.txt
└── Default-Credentials/
    ├── default-passwords.txt
    └── tomcat-betterdefaultpasslist.txt
```

<a id="specialized-password-lists"></a>
### Специализированные списки паролей

| Словарь | Назначение | Расположение |
|----------|---------|----------|
| **wifi-passwords.txt** | Подбор паролей WiFi | SecLists/Passwords/ |
| **probable-v2-top12000.txt** | Статистический анализ | SecLists/Passwords/ |
| **seasons-only.txt** | Сезонные шаблоны | SecLists/Passwords/Honeypot-Captures/ |
| **twitter-banned.txt** | Запрещённые пароли | SecLists/Passwords/ |
| **cirt-default-passwords.txt** | Стандартные учётные данные | SecLists/Passwords/Default-Credentials/ |

<a id="kali-locations"></a>
### Расположение в Kali

```bash
# RockYou (сначала распакуйте)
/usr/share/wordlists/rockyou.txt.gz
gunzip /usr/share/wordlists/rockyou.txt.gz

# John the Ripper
/usr/share/john/password.lst

# Metasploit
/usr/share/metasploit-framework/data/wordlists/
```

---

<a id="-directoryfile-wordlists"></a>
## 📂 Словари каталогов и файлов

<a id="directory-brute-forcing"></a>
### Перебор каталогов

> **Уточнение источника:** common.txt обычно относится к DIRB, а не к DirBuster; подпись таблицы оригинала сохранена. См. [пакет DIRB в Kali](https://www.kali.org/tools/dirb/).

| Словарь | Размер | Строк | Источник | Применение |
|----------|------|-------|--------|----------|
| **common.txt** | 36 KB | 4.6K | DirBuster | Быстрая проверка |
| **directory-list-2.3-small.txt** | 756 KB | 87K | DirBuster | Небольшой объём перечисления |
| **directory-list-2.3-medium.txt** | 1.9 MB | 220K | DirBuster | Стандартное перечисление |
| **directory-list-2.3-big.txt** | 14 MB | 1.2M | DirBuster | Полное перечисление |
| **big.txt** | 210 KB | 20K | SecLists | Распространённые каталоги |

<a id="raft-series-highly-recommended"></a>
### Серия RAFT (рекомендуется!)

```
SecLists/Discovery/Web-Content/
├── raft-large-directories.txt      # 62K каталогов
├── raft-large-files.txt            # 37K файлов
├── raft-large-words.txt            # 120K слов
├── raft-medium-directories.txt     # 30K каталогов
├── raft-medium-files.txt           # 17K файлов
├── raft-medium-words.txt           # 63K слов
├── raft-small-directories.txt      # 20K каталогов
├── raft-small-files.txt            # 11K файлов
└── raft-small-words.txt            # 43K слов
```

<a id="file-extension-lists"></a>
### Списки расширений файлов

| Словарь | Назначение |
|----------|---------|
| **web-extensions.txt** | Распространённые веб-расширения |
| **backup-extensions.txt** | Резервные файлы (.bak, .old) |
| **raft-large-extensions.txt** | Большой список расширений |

<a id="specialized-discovery"></a>
### Специализированный поиск

| Словарь | Назначение | Строк |
|----------|---------|-------|
| **apache.txt** | Для Apache | - |
| **nginx.txt** | Для Nginx | - |
| **iis.txt** | Для IIS | - |
| **api-endpoints.txt** | Поиск API | - |
| **graphql.txt** | Конечные точки GraphQL | - |
| **swagger.txt** | Swagger/OpenAPI | - |

<a id="common-locations"></a>
### Типичные расположения

```bash
# DirBuster
/usr/share/dirbuster/wordlists/
/usr/share/dirb/wordlists/

# SecLists Discovery — поиск ресурсов
/usr/share/seclists/Discovery/Web-Content/

# Быстрая загрузка
common.txt
directory-list-2.3-medium.txt
raft-medium-directories.txt
```

---

<a id="-subdomain-wordlists"></a>
## 🌐 Словари поддоменов

<a id="popular-subdomain-lists"></a>
### Популярные списки поддоменов

| Словарь | Строк | Источник | Применение |
|----------|-------|--------|----------|
| **subdomains-top1million-5000.txt** | 5K | SecLists | Быстрое перечисление |
| **subdomains-top1million-20000.txt** | 20K | SecLists | Стандартное перечисление |
| **subdomains-top1million-110000.txt** | 110K | SecLists | Подробное перечисление |
| **dns-Jhaddix.txt** | 2M+ | SecLists | Bug bounty |
| **fierce-hostlist.txt** | - | SecLists | Совместим с Fierce |
| **combined_subdomains.txt** | 648K | SecLists | Объединённый список |

<a id="subdomain-lists-location"></a>
### Расположение списков поддоменов

```
SecLists/Discovery/DNS/
├── subdomains-top1million-5000.txt
├── subdomains-top1million-20000.txt
├── subdomains-top1million-110000.txt
├── dns-Jhaddix.txt                    # Рекомендуется для bug bounty!
├── combined_subdomains.txt
├── deepmagic.com-prefixes-top500.txt
├── deepmagic.com-prefixes-top50000.txt
├── fierce-hostlist.txt
├── namelist.txt
├── shubs-subdomains.txt
└── bitquark-subdomains-top100000.txt
```

<a id="bug-bounty-recommended"></a>
### Рекомендуемые списки для bug bounty

```bash
# Методология Jason Haddix
dns-Jhaddix.txt              # 2M+ поддоменов

# Объединение для более полного охвата
cat subdomains-top1million-110000.txt dns-Jhaddix.txt | sort -u > mega-subdomain.txt
```

---

<a id="-username-wordlists"></a>
## 👤 Словари имён пользователей

<a id="common-username-lists"></a>
### Распространённые списки имён пользователей

| Словарь | Строк | Назначение |
|----------|-------|---------|
| **top-usernames-shortlist.txt** | 17 | Быстрая проверка |
| **xato-net-10-million-usernames.txt** | 10M | Большой список |
| **cirt-default-usernames.txt** | - | Стандартные имена пользователей |
| **names.txt** | - | Распространённые имена |
| **familynames-usa-top1000.txt** | 1K | Фамилии |

<a id="username-lists-location"></a>
### Расположение списков имён пользователей

```
SecLists/Usernames/
├── top-usernames-shortlist.txt
├── xato-net-10-million-usernames.txt
├── cirt-default-usernames.txt
├── Names/
│   ├── names.txt
│   ├── familynames-usa-top1000.txt
│   └── malenames-usa-top1000.txt
└── Honeypot-Captures/
    └── multiplesources-users-fabian-fingerle.de.txt
```

<a id="default-credentials"></a>
### Стандартные учётные данные

| Служба | Словарь |
|---------|----------|
| **FTP** | ftp-betterdefaultpasslist.txt |
| **SSH** | ssh-betterdefaultpasslist.txt |
| **Tomcat** | tomcat-betterdefaultpasslist.txt |
| **MySQL** | mysql-betterdefaultpasslist.txt |
| **MSSQL** | mssql-betterdefaultpasslist.txt |
| **PostgreSQL** | postgres-betterdefaultpasslist.txt |

---

<a id="-web-fuzzing-wordlists"></a>
## 💉 Словари для веб-фаззинга

<a id="sql-injection"></a>
### SQL-инъекции

> **Совместимость дерева:** показанные пути Fuzzing, включая SQLi/SSTI, не гарантируются в текущей структуре SecLists. Например, актуальное дерево содержит Databases. Файлы и примеры оригинала оставлены без изменений; см. [официальный каталог Fuzzing](https://github.com/danielmiessler/SecLists/tree/master/Fuzzing).

```
SecLists/Fuzzing/SQLi/
├── Generic-SQLi.txt
├── quick-SQLi.txt
├── MSSQL.fuzzdb.txt
├── MySQL.fuzzdb.txt
├── PostgreSQL.fuzzdb.txt
└── oracle.fuzzdb.txt
```

<a id="xss-cross-site-scripting"></a>
### XSS (межсайтовый скриптинг)

```
SecLists/Fuzzing/XSS/
├── XSS-Bypass-Strings-BruteLogic.txt
├── XSS-Cheat-Sheet-PortSwigger.txt
├── XSS-Jhaddix.txt
├── XSS-RSNAKE.txt
├── XSS-Vectors-Mario.txt
└── xss-without-parentheses-semi-colons-portswigger.txt
```

<a id="lfirfi-localremote-file-inclusion"></a>
### LFI/RFI (локальное и удалённое включение файлов)

```
SecLists/Fuzzing/LFI/
├── LFI-Jhaddix.txt
├── LFI-LFISuite-pathtotest-huge.txt
├── LFI-LFISuite-pathtotest.txt
└── LFI-gracefulsecurity-linux.txt
```

<a id="command-injection"></a>
### Инъекция команд

```
SecLists/Fuzzing/command-injection-commix.txt
SecLists/Fuzzing/command-injection-testing-unix.txt
SecLists/Fuzzing/command-injection-testing-windows.txt
```

<a id="ssti-server-side-template-injection"></a>
### SSTI (инъекция в серверные шаблоны)

```
SecLists/Fuzzing/SSTI/
├── SSTI.txt
└── tplmap-template-injection.txt
```

<a id="fuzzing-collections"></a>
### Коллекции для фаззинга

| Словарь | Назначение |
|----------|---------|
| **special-chars.txt** | Специальные символы |
| **Unicode.txt** | Фаззинг Unicode |
| **big-list-of-naughty-strings.txt** | Пограничные случаи |
| **fuzz-Bo0oM.txt** | Общий фаззинг |
| **burp-parameter-names.txt** | Имена параметров |

---

<a id="-seclists-collection"></a>
## 📦 Коллекция SecLists

<a id="about-seclists"></a>
### О SecLists

**SecLists** — коллекция разных типов списков для оценки безопасности: имён пользователей, паролей, путей и данных для фаззинга (fuzzing — проверки поведения на разных входных значениях).

<a id="installation"></a>
### Установка

> **Пакеты:** наличие SecLists зависит от образа и установленных пакетов Kali. Пример apt для Ubuntu/Debian не гарантирует наличие пакета в их стандартных репозиториях. См. [страницу пакета Kali](https://www.kali.org/tools/seclists/); команды оригинала сохранены.

```bash
# Kali Linux (предустановлен)
ls /usr/share/seclists/

# Ubuntu/Debian
sudo apt install seclists

# Клонирование Git
git clone https://github.com/danielmiessler/SecLists.git

# Быстрая загрузка отдельных списков
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt
```

<a id="seclists-structure"></a>
### Структура SecLists

```
SecLists/
├── Discovery/
│   ├── DNS/                    # Словари поддоменов
│   ├── Web-Content/            # Словари каталогов и файлов
│   └── Infrastructure/         # Службы и порты
├── Fuzzing/
│   ├── SQLi/                   # Payloads для SQL-инъекций
│   ├── XSS/                    # Payloads для XSS
│   ├── LFI/                    # Payloads для LFI/RFI
│   └── command-injection*.txt  # Инъекция команд
├── Passwords/
│   ├── Common-Credentials/     # Списки паролей
│   ├── Leaked-Databases/       # rockyou и другие
│   └── Default-Credentials/    # Стандартные пароли
├── Usernames/
│   ├── Names/                  # Списки имён
│   └── top-usernames*.txt      # Списки имён пользователей
├── Payloads/
│   └── Anti-Antivirus/         # Обход AV
└── Pattern-Matching/
    └── Hunting/                # Шаблоны regex (регулярных выражений)
```

<a id="github-repository"></a>
### Репозиторий GitHub

🔗 https://github.com/danielmiessler/SecLists

---

<a id="-kali-default-wordlists"></a>
## 🐧 Стандартные словари Kali

<a id="wordlists-location"></a>
### Расположение словарей

> **Уточнение состава:** дерево зависит от установленных пакетов. nmap.lst в Kali указывает на список паролей passwords.lst, а не на список скриптов Nmap. См. [официальное описание wordlists](https://www.kali.org/tools/wordlists/).

```bash
# Основной каталог словарей
/usr/share/wordlists/

# Содержимое
ls -la /usr/share/wordlists/
├── rockyou.txt.gz          # Распакуйте с помощью gunzip
├── dirb/                   # Словари DIRB
├── dirbuster/              # Словари DirBuster
├── fasttrack.txt           # Список Fast track
├── fern-wifi/              # Словари WiFi
├── metasploit/             # Словари MSF
├── nmap.lst                # Список скриптов Nmap
├── wfuzz/                  # Словари wFuzz
└── seclists -> /usr/share/seclists/
```

<a id="dirbuster-lists"></a>
### Списки DirBuster

```bash
ls /usr/share/dirbuster/wordlists/
├── apache-user-enum-1.0.txt
├── apache-user-enum-2.0.txt
├── directories.jbrofuzz
├── directory-list-1.0.txt
├── directory-list-2.3-big.txt
├── directory-list-2.3-medium.txt
├── directory-list-2.3-small.txt
└── directory-list-lowercase-2.3-big.txt
```

<a id="dirb-lists"></a>
### Списки DIRB

```bash
ls /usr/share/dirb/wordlists/
├── big.txt                  # 20K записей
├── common.txt               # 4.6K записей
├── small.txt                # 959 записей
├── catala.txt               # Каталанский
├── euskera.txt              # Баскский
├── extensions_common.txt    # Расширения
├── indexes.txt              # Индексные файлы
├── mutations_common.txt     # Преобразования
├── spanish.txt              # Испанский
├── stress/                  # Нагрузочные проверки
└── vulns/                   # Проверки уязвимостей
```

---

<a id="-custom-wordlist-tools"></a>
## 🛠️ Инструменты создания собственных словарей

<a id="cewl-custom-word-list-generator"></a>
### CeWL (генератор собственных словарей)

```bash
# Установка
sudo apt install cewl

# Основной пример — обход веб-сайта
cewl https://target.com -w wordlist.txt

# Глубина обхода и минимальная длина слова
cewl https://target.com -d 3 -m 6 -w wordlist.txt

# Включить адреса электронной почты
cewl https://target.com -e -w wordlist.txt

# Параметры
-d <depth>      # Глубина обхода (по умолчанию: 2)
-m <length>     # Минимальная длина слова (по умолчанию: 3)
-w <file>       # Выходной файл
-e              # Включить адреса электронной почты
--lowercase     # Преобразовать в нижний регистр
-c              # Подсчитать вхождения слов
```

<a id="crunch-wordlist-generator"></a>
### Crunch (генератор словарей)

> **Уточнение примеров:** длины и маски Crunch сохранены; вывод при больших наборах может быть очень объёмным. Режим Hashcat 0 в примере относится к MD5, а не к любому типу хеша. См. [документацию Crunch в Kali](https://www.kali.org/tools/crunch/) и [официальные форматы Hashcat](https://hashcat.net/wiki/doku.php?id=example_hashes).

```bash
# Установка
sudo apt install crunch

# Основной синтаксис
crunch <min-len> <max-len> [charset] -o <output>

# Создать пароли длиной 4-6 символов
crunch 4 6 -o wordlist.txt

# С заданными символами
crunch 8 8 abcdefghijklmnopqrstuvwxyz0123456789 -o wordlist.txt

# С шаблоном
crunch 6 6 -t @@%%^^ -o wordlist.txt
# @ = нижний регистр, , = верхний регистр, % = цифра, ^ = специальный символ

# Собственный набор символов
crunch 4 4 -f /usr/share/crunch/charset.lst mixalpha-numeric -o wordlist.txt

# Передать поток в hashcat
crunch 8 8 0123456789 | hashcat -m 0 hash.txt
```

<a id="cupp-common-user-passwords-profiler"></a>
### CUPP (профилирование вероятных паролей пользователя)

```bash
# Установка
git clone https://github.com/Mebus/cupp.git
cd cupp

# Интерактивный режим на основе профиля
python3 cupp.py -i

# Вопросы:
# - Имя, фамилия, псевдоним
# - Дата рождения
# - Сведения о партнёре
# - Имя домашнего животного
# - Название компании
# - Ключевые слова

# Загрузить распространённые пароли
python3 cupp.py -l

# Использовать существующий словарь для профилирования
python3 cupp.py -w wordlist.txt
```

<a id="mentalist-gui-wordlist-generator"></a>
### Mentalist (генератор словарей с графическим интерфейсом)

```bash
# Инструмент с графическим интерфейсом для создания сложных словарей
# Поддерживает правила, изменение регистра и подстановки
# Загрузка: https://github.com/sc0tfree/mentalist
```

<a id="wordlister-python"></a>
### Wordlister (Python)

```python
#!/usr/bin/env python3
# Простой генератор собственного словаря
import itertools

# Создать комбинации
chars = 'abc123'
length = 4

for combo in itertools.product(chars, repeat=length):
    print(''.join(combo))
```

<a id="hashcat-rules"></a>
### Правила Hashcat

> **Версии правил:** набор файлов правил зависит от версии и пакета. Перечень отражает оригинал; его следует сверять с [каталогом rules разработчиков Hashcat](https://github.com/hashcat/hashcat/tree/master/rules).

```bash
# Применить правила для преобразования словаря
hashcat -r /usr/share/hashcat/rules/best64.rule wordlist.txt --stdout

# Популярные правила
/usr/share/hashcat/rules/
├── best64.rule         # Лучшие 64 правила
├── rockyou-30000.rule  # На основе RockYou
├── d3ad0ne.rule        # Правила d3ad0ne
├── dive.rule           # Правила DIVE
└── toggles1-5.rule     # Переключение регистра
```

---

<a id="-quick-download"></a>
## ⬇️ Быстрая загрузка

<a id="one-liner-downloads"></a>
### Загрузка одной командой

```bash
# RockYou
wget https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt

# SecLists целиком
git clone --depth 1 https://github.com/danielmiessler/SecLists.git

# Распространённые пароли (1M)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt

# Список каталогов medium
wget https://raw.githubusercontent.com/daviddias/node-dirbuster/master/lists/directory-list-2.3-medium.txt

# Список поддоменов
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/subdomains-top1million-110000.txt

# Jhaddix DNS (bug bounty)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/dns-Jhaddix.txt
```

<a id="clone-repositories"></a>
### Клонирование репозиториев

```bash
# SecLists
git clone https://github.com/danielmiessler/SecLists.git

# FuzzDB
git clone https://github.com/fuzzdb-project/fuzzdb.git

# PayloadsAllTheThings
git clone https://github.com/swisskyrepo/PayloadsAllTheThings.git

# Assetnote Wordlists
git clone https://github.com/assetnote/wordlists.git
```

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендации

<a id="choosing-the-right-wordlist"></a>
### Выбор подходящего словаря

| Задача | Рекомендуемый словарь |
|------|---------------------|
| **Быстрая проверка каталогов** | common.txt, raft-small-directories.txt |
| **Подробная проверка каталогов** | directory-list-2.3-medium.txt |
| **Полная проверка каталогов** | directory-list-2.3-big.txt |
| **Перечисление поддоменов** | subdomains-top1million-20000.txt |
| **DNS для bug bounty** | dns-Jhaddix.txt |
| **Подбор паролей** | rockyou.txt |
| **Быстрая проверка паролей** | top-10000.txt |

<a id="combining-wordlists"></a>
### Объединение словарей

```bash
# Объединить и удалить дубликаты
cat list1.txt list2.txt | sort -u > combined.txt

# Добавить префикс ко всем строкам
sed 's/^/admin_/' wordlist.txt > admin_wordlist.txt

# Добавить суффикс
sed 's/$/.php/' wordlist.txt > php_wordlist.txt

# Оба варианта
while read line; do echo "${line}.php"; echo "${line}.html"; done < wordlist.txt > extended.txt
```

<a id="wordlist-optimization"></a>
### Оптимизация словарей

```bash
# Отсортировать и удалить дубликаты
sort -u wordlist.txt -o wordlist.txt

# Удалить пустые строки
sed -i '/^$/d' wordlist.txt

# Удалить строки короче 4 символов
awk 'length >= 4' wordlist.txt > filtered.txt

# Преобразовать в нижний регистр
tr '[:upper:]' '[:lower:]' < wordlist.txt > lowercase.txt

# Подсчитать строки
wc -l wordlist.txt
```

<a id="performance-tips"></a>
### Советы по производительности

1. **Начинайте с малого** — сначала используйте небольшие списки, затем расширяйте их
2. **Используйте шаблоны** — если формат известен, задавайте подходящие шаблоны
3. **Сочетайте инструменты** — объединяйте собственные и стандартные списки
4. **Учитывайте контекст цели** — создавайте специализированные списки с CeWL

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="top-wordlists-by-category"></a>
### Основные словари по категориям

| Категория | Оптимальный вариант | Расположение |
|----------|-------------|----------|
| **Пароли** | rockyou.txt | SecLists/Passwords/ |
| **Каталоги** | directory-list-2.3-medium.txt | DirBuster |
| **Файлы** | raft-large-files.txt | SecLists |
| **Поддомены** | dns-Jhaddix.txt | SecLists/Discovery/DNS/ |
| **Имена пользователей** | xato-net-10-million-usernames.txt | SecLists/Usernames/ |
| **SQLi** | Generic-SQLi.txt | SecLists/Fuzzing/SQLi/ |
| **XSS** | XSS-Jhaddix.txt | SecLists/Fuzzing/XSS/ |
| **LFI** | LFI-Jhaddix.txt | SecLists/Fuzzing/LFI/ |

<a id="size-guide"></a>
### Ориентир по размеру

> **Охват:** обозначение «полное покрытие» в таблице — условная характеристика размера. Даже большой словарь не гарантирует нахождение всех ресурсов или нужного пароля.

| Размер | Строк | Применение |
|------|-------|----------|
| **Малый** | < 5K | Быстрые проверки |
| **Средний** | 5K - 100K | Стандартное перечисление |
| **Большой** | 100K - 1M | Подробная проверка |
| **Огромный** | > 1M | Полное покрытие |

---

<a id="-resources"></a>
## 📚 Ресурсы

<a id="wordlist-repositories"></a>
### Репозитории словарей
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Assetnote Wordlists](https://github.com/assetnote/wordlists)

<a id="tools"></a>
### Инструменты
- [CeWL](https://github.com/digininja/CeWL)
- [CUPP](https://github.com/Mebus/cupp)
- [Crunch](https://sourceforge.net/projects/crunch-wordlist/)
- [Mentalist](https://github.com/sc0tfree/mentalist)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Hydra](../../Hydra/translations/README.ru.md)
- [John the Ripper](../../John-The-Ripper/translations/README.ru.md)
- [Hashcat](../../Hashcat/translations/README.ru.md)
- [Gobuster](../../Gobuster/translations/README.ru.md)
- [ffuf](../../ffuf/translations/README.ru.md)

---

<p align="center">
  <b>📚 Подходящий словарь влияет на результат!</b><br>
  <i>Выбирайте осмысленно и сочетайте списки</i>
</p>
