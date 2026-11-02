<a id="-hashcat---complete-cheatsheet"></a>
# ⚡ Hashcat — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Уточнения к оригиналу:** число типов, число режимов, сравнение скорости и поддержка устройств отражают исходник и зависят от версии; современные версии имеют [параметр --identify и другие режимы](https://hashcat.net/wiki/doku.php?id=hashcat). Пути сеансов и пакеты драйверов также зависят от установки. В [Hashcat 8.0.0](https://github.com/hashcat/hashcat/releases/tag/v8.0.0) параметр -w принимается, но игнорируется, а --restore печатает сохранённую команду вместо запуска. Сокращённые хеши с ... — иллюстрации, а не готовые входные данные.

> **Маски и вывод:** [--increment не увеличивает маску сверх её собственной длины](https://hashcat.net/wiki/doku.php?id=mask_attack): пример с четырьмя ?d не охватывает восемь позиций. В [справке Hashcat](https://hashcat.net/wiki/doku.php?id=hashcat) outfile-format 2 означает plain, а benchmark-all — проверку всех режимов вместе с -b, не отключение оптимизации. Примеры сохранены буквально; таблица профилей нагрузки относится к прежним версиям.

> **Правила и символы:** sXY заменяет X на Y; показанный pa$$word не соответствует буквальным аргументам sXY (см. [официальное описание правил](https://hashcat.net/wiki/doku.php?id=rule_based_attack)). Строка ?s оригинала неполна: [официальный набор](https://hashcat.net/wiki/doku.php?id=mask_attack) содержит также пробел и обратную косую черту. Исходные символы сохранены; вертикальная черта экранирована только для ячейки Markdown. World's Fastest Password Recovery на ASCII-эмблеме означает «самое быстрое восстановление паролей» — рекламная формулировка оригинала, а не результат сравнительных измерений.


[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _                _   
 | | | | __ _ ___| |__   ___ __ _| |_ 
 | |_| |/ _` / __| '_ \ / __/ _` | __|
 |  _  | (_| \__ \ | | | (_| (_| | |_ 
 |_| |_|\__,_|___/_| |_|\___\__,_|\__|
                                       
    World's Fastest Password Recovery
```

<p align="center">
  <img src="https://img.shields.io/badge/Hashcat-GPU_Cracker-red?style=for-the-badge" alt="Hashcat">
  <img src="https://img.shields.io/badge/350+-Hash_Types-orange?style=for-the-badge" alt="Hash Types">
  <img src="https://img.shields.io/badge/GPU-Accelerated-blue?style=for-the-badge" alt="GPU">
  <img src="https://img.shields.io/badge/World's-Fastest-green?style=for-the-badge" alt="Fastest">
</p>

<p align="center">
  <b>🚀 Высокопроизводительная утилита восстановления паролей с широкими возможностями</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Установка](#-installation)
- [Основной синтаксис](#-basic-syntax)
- [Режимы атак](#-attack-modes)
- [Режимы хешей](#-hash-modes)
- [Атака по маске](#-mask-attack)
- [Правила](#-rules)
- [Управление сеансами](#-session-management)
- [Оптимизация GPU](#-gpu-optimization)
- [Практические примеры](#-real-world-examples)
- [Краткий справочник](#-quick-reference)
- [Советы и рекомендуемые практики](#-tips--best-practices)
- [Полезные материалы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

**Hashcat** — высокопроизводительная утилита восстановления паролей с поддержкой более 350 типов хешей и ускорением на GPU. Числа и сравнительные оценки ниже переданы из оригинала.

<a id="key-features"></a>
### Основные возможности

| Возможность | Описание |
| --------- | ------------- |
| **Более 350 типов хешей** | MD5, SHA, NTLM, bcrypt, WPA и другие |
| **Ускорение на GPU** | NVIDIA CUDA, AMD OpenCL, Intel |
| **Несколько GPU** | Поддержка распределённого подбора |
| **5 режимов атак** | Словарь, перебор, гибридные режимы и другие |
| **Гибкие правила** | Расширенные преобразования слов |
| **Поддержка разных платформ** | Linux, Windows, macOS |

<a id="hashcat-vs-john-the-ripper"></a>
### Hashcat и John the Ripper

| Возможность | Hashcat | John |
| --------- | --------- | ------ |
| **Скорость** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| **Поддержка GPU** | Отличная | Хорошая |
| **Типы хешей** | 350+ | 300+ |
| **Автоматическое определение** | ❌ Нет | ✅ Да |
| **Удобство использования** | Среднее | Проще |
| **Лучше подходит для** | Подбора на GPU | Подбора на CPU |

---

<a id="-installation"></a>
## 📥 Установка

<a id="kali-linux"></a>
### Kali Linux
```bash
sudo apt update && sudo apt install hashcat

# С OpenCL
sudo apt install hashcat-nvidia  # NVIDIA
sudo apt install hashcat-amd     # AMD
```

<a id="ubuntudebian"></a>
### Ubuntu/Debian
```bash
sudo apt install hashcat
```

<a id="windows"></a>
### Windows
```bash
# Скачать с официального сайта
https://hashcat.net/hashcat/

# Распаковать и запустить из каталога
hashcat.exe -h
```

<a id="from-source"></a>
### Из исходного кода
```bash
git clone https://github.com/hashcat/hashcat.git
cd hashcat
make
./hashcat -h
```

<a id="verify-installation"></a>
### Проверка установки
```bash
hashcat --version
hashcat -I                    # Показать устройства OpenCL
hashcat -b                    # Запустить проверку производительности
```

---

<a id="-basic-syntax"></a>
## ⌨️ Основной синтаксис

<a id="general-syntax"></a>
### Общий синтаксис
```bash
hashcat [options] hash|hashfile [dictionary|mask]
```

<a id="essential-options"></a>
### Основные параметры

| Параметр | Описание |
| -------- | ------------- |
| `-m` | Тип хеша (режим) |
| `-a` | Режим атаки |
| `-o` | Выходной файл |
| `-r` | Файл правил |
| `--show` | Показать найденные пароли |
| `--status` | Включить отображение состояния |
| `-w` | Профиль нагрузки (1-4) |
| `--force` | Игнорировать предупреждения |
| `-O` | Оптимизированные ядра |

<a id="quick-start"></a>
### Быстрый старт
```bash
# Базовая атака по словарю
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Показать найденные значения хешей
hashcat -m 0 hash.txt --show

# Проверить примеры хешей
hashcat -m 0 --example-hashes
```

---

<a id="-attack-modes"></a>
## 🎮 Режимы атак

<a id="overview"></a>
### Обзор

| Режим | Название | Описание |
| ------ | ------ | ------------- |
| `-a 0` | Словарь | Атака по словарю |
| `-a 1` | Комбинация | Объединить два словаря |
| `-a 3` | Полный перебор | Атака по маске |
| `-a 6` | Гибрид: словарь и маска | Словарь + маска |
| `-a 7` | Гибрид: маска и словарь | Маска + словарь |
| `-a 9` | Ассоциации | Ассоциации слов |

<a id="dictionary-attack--a-0"></a>
### Атака по словарю (-a 0)

```bash
# Базовая атака по словарю
hashcat -m 0 -a 0 hash.txt wordlist.txt

# С правилами
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rules/best64.rule

# Несколько словарей
hashcat -m 0 -a 0 hash.txt wordlist1.txt wordlist2.txt
```

<a id="combination-attack--a-1"></a>
### Комбинированная атака (-a 1)

Объединяет слова из двух словарей.

```bash
# Объединить два словаря
hashcat -m 0 -a 1 hash.txt wordlist1.txt wordlist2.txt

# Комбинации word1word2
```

<a id="brute-forcemask-attack--a-3"></a>
### Перебор по маске (-a 3)

```bash
# Все буквы строчные, 6 символов
hashcat -m 0 -a 3 hash.txt ?l?l?l?l?l?l

# Все символы — цифры, 4-8 символов (подпись оригинала; см. уточнение)
hashcat -m 0 -a 3 hash.txt ?d?d?d?d --increment --increment-min=4 --increment-max=8

# Пользовательский набор символов
hashcat -m 0 -a 3 hash.txt -1 ?l?d ?1?1?1?1?1?1
```

<a id="hybrid-attacks--a-6--a-7"></a>
### Гибридные атаки (-a 6, -a 7)

```bash
# Словарь + маска (добавление в конец)
hashcat -m 0 -a 6 hash.txt wordlist.txt ?d?d?d?d

# Маска + словарь (добавление в начало)
hashcat -m 0 -a 7 hash.txt ?d?d?d?d wordlist.txt
```

---

<a id="-hash-modes"></a>
## 🔢 Режимы хешей

<a id="most-common-hash-modes"></a>
### Распространённые режимы хешей

| Режим | Тип хеша | Пример |
| ------ | ----------- | --------- |
| 0 | MD5 | `5d41402abc4b2a76b9719d911017c592` |
| 100 | SHA1 | `aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d` |
| 1400 | SHA256 | `2cf24dba5fb0a30e26e83b2ac5b9e29e...` |
| 1700 | SHA512 | `cf83e1357eefb8bdf1542850d66d8007d620e405...` |
| 1000 | NTLM | `32ed87bdb5fdc5e9cba88547376818d4` |
| 3000 | LM | `aad3b435b51404ee` |
| 5600 | NetNTLMv2 | Составной формат |
| 3200 | bcrypt | `$2a$10$...` |
| 1800 | sha512crypt (Linux) | `$6$...` |
| 500 | md5crypt (Linux) | `$1$...` |

<a id="web-applications"></a>
### Веб-приложения

| Режим | Тип хеша |
| ------ | ----------- |
| 400 | WordPress (phpass) |
| 2500 | WPA/WPA2 |
| 22000 | WPA-PBKDF2-PMKID+EAPOL |
| 121 | SMF |
| 2611 | vBulletin |
| 3711 | MediaWiki B |
| 7900 | Drupal7 |

<a id="databases"></a>
### Базы данных

| Режим | Тип хеша |
| ------ | ----------- |
| 300 | MySQL4.1/MySQL5 |
| 200 | MySQL323 |
| 1731 | MSSQL (2012, 2014) |
| 112 | Oracle S |
| 12300 | Oracle T |

<a id="documents--archives"></a>
### Документы и архивы

| Режим | Тип хеша |
| ------ | ----------- |
| 9600 | Office 2013 |
| 9500 | Office 2010 |
| 9400 | Office 2007 |
| 9700 | MS Office ⇐ 2003 |
| 10500 | PDF 1.4-1.6 |
| 10700 | PDF 1.7 Level 8 |
| 11600 | 7-Zip |
| 13600 | WinZip |
| 17200 | PKZIP |
| 13000 | RAR5 |
| 12500 | RAR3-hp |

<a id="cryptocurrency"></a>
### Криптовалюты

| Режим | Тип хеша |
| ------ | ----------- |
| 11300 | Кошелёк Bitcoin |
| 12700 | Кошелёк Blockchain |
| 16600 | Кошелёк Electrum |
| 15200 | Ethereum Keystore |

<a id="listsearch-modes"></a>
### Просмотр и поиск режимов
```bash
# Показать все режимы хешей
hashcat --help | grep -i "hash modes"

# Найти определённый тип хеша
hashcat --example-hashes | grep -i "ntlm"
hashcat --example-hashes | grep -i "sha256"

# Показать пример формата хеша
hashcat -m 1000 --example-hashes
```

---

<a id="-mask-attack"></a>
## 🎭 Атака по маске

<a id="charset-placeholders"></a>
### Обозначения наборов символов

| Обозначение | Символы |
| ------------- | ------------ |
| `?l` | abcdefghijklmnopqrstuvwxyz |
| `?u` | ABCDEFGHIJKLMNOPQRSTUVWXYZ |
| `?d` | 0123456789 |
| `?h` | 0123456789abcdef |
| `?H` | 0123456789ABCDEF |
| `?s` | !"#$%&'()*+,-./:;<=>?@[]^_`{\|}~ |
| `?a` | ?l?u?d?s |
| `?b` | 0x00 - 0xff |

<a id="custom-charsets"></a>
### Пользовательские наборы символов

```bash
# Задать пользовательский набор символов
hashcat -a 3 -1 ?l?d hash.txt ?1?1?1?1?1?1

# Несколько пользовательских наборов символов
hashcat -a 3 -1 abc -2 123 hash.txt ?1?2?1?2

# Специальные символы
hashcat -a 3 -1 '!@#$%' hash.txt pass?1?1?1?1
```

<a id="increment-mode"></a>
### Режим увеличения длины

```bash
# Проверить длины 1-8
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment

# Указать минимальную и максимальную длину
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment --increment-min=4 --increment-max=8
```

<a id="common-mask-patterns"></a>
### Типичные шаблоны масок

```bash
# 8 строчных букв
?l?l?l?l?l?l?l?l

# Пароль + 4 цифры
password?d?d?d?d

# Заглавная буква + строчные буквы + цифры
?u?l?l?l?l?l?d?d

# Шаблон Winter2024
?u?l?l?l?l?l?d?d?d?d

# Номер телефона
?d?d?d?d?d?d?d?d?d?d
```

---

<a id="-rules"></a>
## 📜 Правила

<a id="built-in-rule-files"></a>
### Встроенные файлы правил

```bash
# Расположение
ls /usr/share/hashcat/rules/

# Популярные правила
best64.rule           # 64 основных правила
rockyou-30000.rule    # 30000 популярных правил
d3ad0ne.rule          # Правила D3ad0ne
dive.rule             # Правила Dive
generated.rule        # Сгенерированные правила
```

<a id="using-rules"></a>
### Использование правил

```bash
# Один файл правил
hashcat -m 0 -a 0 hash.txt wordlist.txt -r best64.rule

# Несколько файлов правил
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rule1.rule -r rule2.rule

# Сгенерировать правила
hashcat -m 0 -a 0 hash.txt wordlist.txt -g 1000
```

<a id="common-rule-operations"></a>
### Основные операции правил

| Правило | Описание | Пример |
| ------ | ------------- | --------- |
| `:` | Ничего не менять | password |
| `l` | Перевести все буквы в нижний регистр | PASSWORD → password |
| `u` | Перевести все буквы в верхний регистр | password → PASSWORD |
| `c` | Первая буква заглавная | password → Password |
| `C` | Первая буква строчная, остальные заглавные | password → pASSWORD |
| `t` | Инвертировать регистр | password → PASSWORD |
| `r` | Обратить порядок символов | password → drowssap |
| `d` | Удвоить слово | pass → passpass |
| `$X` | Добавить X в конец | pass → passX |
| `^X` | Добавить X в начало | pass → Xpass |
| `sXY` | Заменить X на Y | password → pa$$word |

---

<a id="-session-management"></a>
## 💾 Управление сеансами

<a id="create-session"></a>
### Создание сеанса
```bash
# Именованный сеанс
hashcat -m 0 -a 0 hash.txt wordlist.txt --session=mysession

# Файлы сеанса хранятся в ~/.hashcat/sessions/ (путь оригинала; зависит от версии и платформы)
```

<a id="pause--resume"></a>
### Пауза и продолжение
```bash
# Пауза: нажмите 'p' во время подбора
# Продолжение: нажмите 'r' во время подбора

# Или восстановить из командной строки
hashcat --session=mysession --restore
```

<a id="status-commands"></a>
### Команды состояния

Во время подбора:
- `s` — показать состояние
- `p` — приостановить
- `r` — продолжить
- `b` — пропустить текущую атаку
- `c` — создать контрольную точку
- `q` — выйти

<a id="output-options"></a>
### Параметры вывода
```bash
# Записать результат в файл
hashcat -m 0 -a 0 hash.txt wordlist.txt -o cracked.txt

# Формат вывода
hashcat -m 0 hash.txt --outfile-format=2    # hash:plain (подпись оригинала; см. уточнение)

# Показать найденные значения
hashcat -m 0 hash.txt --show

# Показать оставшиеся ненайденные значения
hashcat -m 0 hash.txt --left
```

---

<a id="-gpu-optimization"></a>
## 🚀 Оптимизация GPU

<a id="workload-profiles"></a>
### Профили нагрузки

| Профиль | Описание |
| --------- | ------------- |
| `-w 1` | Низкая (в оригинале: по умолчанию, интерактивная) |
| `-w 2` | Средняя |
| `-w 3` | Высокая (интерфейс рабочего стола может зависать) |
| `-w 4` | Максимальная (выделенная система для подбора) |

<a id="optimize-commands"></a>
### Оптимизация команд
```bash
# Высокая производительность
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 3 -O

# Оптимизированные ядра (быстрее, но длина пароля ограничена)
hashcat -m 0 -a 0 hash.txt wordlist.txt -O

# Выбрать устройство
hashcat -m 0 -a 0 hash.txt wordlist.txt -d 1

# Не использовать CPU, применять только GPU
hashcat -m 0 -a 0 hash.txt wordlist.txt -D 2
```

<a id="device-options"></a>
### Параметры устройств
```bash
# Список устройств
hashcat -I

# Выбрать определённое устройство
hashcat -d 1 ...

# Типы устройств
# -D 1 = CPU
# -D 2 = GPU
# -D 3 = FPGA
```

<a id="benchmark"></a>
### Проверка производительности
```bash
# Полная проверка производительности
hashcat -b

# Определённый тип хеша
hashcat -b -m 0

# Проверка без оптимизации OpenCL (подпись оригинала; см. уточнение)
hashcat -b --benchmark-all
```

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-md5-dictionary-attack"></a>
### Пример 1: подбор MD5 по словарю
```bash
hashcat -m 0 -a 0 md5_hashes.txt /usr/share/wordlists/rockyou.txt
```

<a id="example-2-ntlm-with-rules"></a>
### Пример 2: NTLM с правилами
```bash
hashcat -m 1000 -a 0 ntlm_hashes.txt wordlist.txt -r /usr/share/hashcat/rules/best64.rule
```

<a id="example-3-wpawpa2-handshake"></a>
### Пример 3: рукопожатие WPA/WPA2
```bash
# Сначала преобразовать в формат Hashcat
hcxpcapngtool -o hash.hc22000 capture.pcapng

# Подбор
hashcat -m 22000 hash.hc22000 /usr/share/wordlists/rockyou.txt
```

<a id="example-4-sha256-brute-force-6-chars"></a>
### Пример 4: перебор SHA256 (6 символов)
```bash
hashcat -m 1400 -a 3 sha256_hash.txt ?a?a?a?a?a?a
```

<a id="example-5-office-2013-document"></a>
### Пример 5: документ Office 2013
```bash
# Извлечь хеш с помощью office2john
office2john document.docx > office.hash

# Подбор
hashcat -m 9600 office.hash wordlist.txt
```

<a id="example-6-linux-shadow-password"></a>
### Пример 6: пароль Linux из shadow
```bash
hashcat -m 1800 -a 0 shadow_hash.txt wordlist.txt
```

<a id="example-7-wordpress-hash"></a>
### Пример 7: хеш WordPress
```bash
hashcat -m 400 -a 0 wordpress_hash.txt wordlist.txt
```

<a id="example-8-hybrid-attack"></a>
### Пример 8: гибридная атака
```bash
# Название компании + 4 цифры
hashcat -m 0 -a 6 hash.txt company_names.txt ?d?d?d?d
```

<a id="example-9-windows-domain-hashes"></a>
### Пример 9: хеши домена Windows
```bash
# Хеши NTLM из secretsdump
hashcat -m 1000 -a 0 ntds_hashes.txt wordlist.txt -r best64.rule --username
```

<a id="example-10-bitcoin-wallet"></a>
### Пример 10: кошелёк Bitcoin
```bash
# Извлечь с помощью bitcoin2john
hashcat -m 11300 bitcoin_hash.txt wordlist.txt
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Задача | Команда |
| ------ | --------- |
| MD5 по словарю | `hashcat -m 0 -a 0 hash.txt wordlist.txt` |
| Полный перебор NTLM | `hashcat -m 1000 -a 3 hash.txt ?a?a?a?a?a?a` |
| SHA256 с правилами | `hashcat -m 1400 -a 0 hash.txt wordlist.txt -r rules.rule` |
| Показать найденные значения | `hashcat -m 0 hash.txt --show` |
| Проверка производительности | `hashcat -b` |
| Список устройств | `hashcat -I` |
| Восстановить сеанс | `hashcat --session=name --restore` |

<a id="common-hash-modes"></a>
### Распространённые режимы хешей

| Режим | Тип |
| ------ | ------ |
| 0 | MD5 |
| 100 | SHA1 |
| 1000 | NTLM |
| 1400 | SHA256 |
| 1800 | sha512crypt |
| 3200 | bcrypt |
| 22000 | WPA2 |

<a id="attack-modes"></a>
### Режимы атак

| Режим | Тип |
| ------ | ------ |
| -a 0 | Словарь |
| -a 1 | Комбинация |
| -a 3 | Полный перебор |
| -a 6 | Гибрид (словарь + маска) |
| -a 7 | Гибрид (маска + словарь) |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендуемые практики

<a id="efficiency-tips"></a>
### Советы по эффективности

1. **Начать со словаря и правил**
   ```bash
   hashcat -m 0 -a 0 hash.txt rockyou.txt -r best64.rule
   ```

2. **Использовать оптимизированные ядра**
   ```bash
   hashcat -O ...
   ```

3. **Проверять типичные шаблоны**
   ```bash
   # Сезон + год: Winter2024
   hashcat -a 3 -1 WSFJ -2 winterumeall hash.txt ?1?2?2?2?2?2?d?d?d?d
   ```

<a id="performance-tips"></a>
### Советы по производительности

```bash
# Максимальная производительность (выделенная система)
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 4 -O --force

# Несколько GPU
hashcat -d 1,2 ...
```

<a id="workflow"></a>
### Последовательность работы

```
1. Определить тип хеша
2. Попробовать атаку по словарю с правилами
3. Проверить целевые маски (шаблоны)
4. Попробовать полный перебор, если это осуществимо
5. Попробовать гибридные атаки
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовая оговорка

> **ПРЕДУПРЕЖДЕНИЕ:** Hashcat — мощный инструмент подбора паролей, который следует использовать только **в разрешённых целях**.
> 
> - ✅ Проверяйте собственные системы
> - ✅ Проводите оценку безопасности с разрешения
> - ✅ Восстанавливайте собственные пароли
> - ❌ Не подбирайте пароли без разрешения
> - ❌ Несанкционированный доступ незаконен
> 
> **Подбор паролей без разрешения незаконен и может повлечь уголовную ответственность.**

---

<a id="-resources"></a>
## 📚 Полезные материалы

<a id="official-resources"></a>
### Официальные материалы
- [Hashcat Official](https://hashcat.net/hashcat/)
- [Hashcat Wiki](https://hashcat.net/wiki/)
- [Hashcat GitHub](https://github.com/hashcat/hashcat)
- [Hashcat Forum](https://hashcat.net/forum/)

<a id="wordlists"></a>
### Словари
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [John the Ripper](../../John-The-Ripper/translations/README.ru.md)
- [Hydra](../../Hydra/translations/README.ru.md)
- [Nmap](../../Nmap/translations/README.ru.md)

---

<p align="center">
  <b>⚡ Освойте подбор паролей на GPU!</b><br>
  <i>Высокопроизводительное восстановление паролей</i>
</p>
