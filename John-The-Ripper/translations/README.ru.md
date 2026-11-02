<a id="-john-the-ripper---complete-cheatsheet"></a>
# 🔨 John the Ripper — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Уточнения к оригиналу:** набор параметров, форматов, charset-файлов и утилит 2john зависит от сборки Core/Jumbo и способа установки; имена пакетов в примерах не гарантированы для каждого дистрибутива. Сверяйте их с [официальной документацией Jumbo](https://github.com/openwall/john/blob/bleeding-jumbo/doc/OPTIONS). Автоопределение формата может быть неоднозначным; см. [FAQ Openwall](https://www.openwall.com/john/doc/FAQ.shtml). Таблица сравнения John/Hashcat передаёт упрощённые оценки исходника, а не актуальный тест скорости или гарантию возможностей.

> **Точность примеров:** сокращённые хеши с многоточием и укороченные LM/NTLM-поля показаны как иллюстрации, а не готовые входные данные. В [синтаксисе правил Openwall](https://www.openwall.com/john/doc/RULES.shtml) $X и ^X работают с одним символом X: строки $123 и ^2023 нельзя понимать как добавление всей строки 123 или 2023. Исходные правила сохранены буквально. Команда с wc -l считает строки вывода, а не число перечисленных через запятую форматов.

> **Подпись эмблемы:** The Password Cracker — инструмент подбора паролей. ASCII-рисунок сохранён.


[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
       __      __         
      / /___  / /_  ____  
 __  / / __ \/ __ \/ __ \ 
/ /_/ / /_/ / / / / / / / 
\____/\____/_/ /_/_/ /_/  
  The Password Cracker     
```

<p align="center">
  <img src="https://img.shields.io/badge/John-Password_Cracker-red?style=for-the-badge" alt="John">
  <img src="https://img.shields.io/badge/Hash-Cracking-orange?style=for-the-badge" alt="Hash">
  <img src="https://img.shields.io/badge/Open-Source-blue?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Multi-Platform-green?style=for-the-badge" alt="Multi Platform">
</p>

<p align="center">
  <b>🔑 Известный инструмент аудита стойкости и восстановления паролей</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Установка](#-installation)
- [Основной синтаксис](#-basic-syntax)
- [Режимы подбора паролей](#-cracking-modes)
- [Извлечение хешей](#-hash-extraction)
- [Распространённые типы хешей](#-common-hash-types)
- [Подбор паролей Linux](#-linux-password-cracking)
- [Подбор паролей Windows](#-windows-password-cracking)
- [Подбор паролей файлов](#-file-password-cracking)
- [Правила и преобразование слов](#-rules--mangling)
- [Управление сеансами](#-session-management)
- [Настройка производительности](#-performance-tuning)
- [Практические примеры](#-real-world-examples)
- [Краткий справочник](#-quick-reference)
- [Советы и рекомендуемые практики](#-tips--best-practices)
- [Полезные материалы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

**John the Ripper** (John/JtR) — бесплатный открытый инструмент аудита стойкости и восстановления паролей. Он помогает находить слабые пароли и подбирать исходные значения по их хешам.

<a id="key-features"></a>
### Основные возможности

| Возможность | Описание |
| --------- | ------------- |
| **Множество форматов** | Поддержка сотен типов хешей |
| **Несколько режимов** | Single, Wordlist, Incremental |
| **Собственные правила** | Гибкие правила преобразования слов |
| **Автоопределение** | Автоматическое определение формата хеша |
| **Поддержка разных платформ** | Linux, Windows, macOS |
| **Поддержка GPU** | Ускорение OpenCL (Jumbo) |

<a id="john-vs-hashcat"></a>
### John и Hashcat

| Возможность | John | Hashcat |
| --------- | ------ | --------- |
| **Предпочтительное применение** | Подбор на CPU | Подбор на GPU |
| **Удобство использования** | Проще | Сложнее |
| **Автоопределение** | ✅ Да | ❌ Нет |
| **Правила** | Гибкие | Очень гибкие |
| **Типы хешей** | Много | Больше |
| **Скорость (GPU)** | Хорошая | Выше |

<a id="versions"></a>
### Версии

| Версия | Описание |
| --------- | ------------- |
| **John (Core)** | Базовая версия |
| **John Jumbo** | Расширенная версия сообщества (рекомендуется) |

---

<a id="-installation"></a>
## 📥 Установка

<a id="kali-linux-pre-installed"></a>
### Kali Linux (предустановлен)
```bash
john --version
```

<a id="ubuntudebian"></a>
### Ubuntu/Debian
```bash
# Стандартная версия
sudo apt install john

# Версия Jumbo (рекомендуется)
sudo apt install john-jumbo
```

<a id="from-source-jumbo"></a>
### Сборка из исходного кода (Jumbo)
```bash
git clone https://github.com/openwall/john
cd john/src
./configure
make -s clean && make -sj4
cd ../run
./john --version
```

<a id="macos-homebrew"></a>
### macOS (Homebrew)
```bash
brew install john-jumbo
```

<a id="windows"></a>
### Windows
```bash
# Скачать с официального сайта
https://www.openwall.com/john/

# Или использовать Chocolatey
choco install john
```

<a id="verify-installation"></a>
### Проверка установки
```bash
john --version
john --list=formats | wc -l    # Подсчитать поддерживаемые форматы (см. уточнение о строках вывода)
```

---

<a id="-basic-syntax"></a>
## ⌨️ Основной синтаксис

<a id="general-syntax"></a>
### Общий синтаксис
```bash
john [options] [password_file]
```

<a id="common-options"></a>
### Основные параметры

| Параметр | Описание |
| -------- | ------------- |
| `--wordlist=` | Путь к словарю |
| `--format=` | Указать формат хеша |
| `--rules` | Включить правила преобразования слов |
| `--show` | Показать подобранные пароли |
| `--single` | Режим Single Crack |
| `--incremental` | Режим полного перебора |
| `--session=` | Задать имя сеанса |
| `--restore` | Восстановить предыдущий сеанс |
| `--pot=` | Собственный файл pot |

<a id="quick-start"></a>
### Быстрый старт
```bash
# Автоматически определить формат и начать подбор
john hashes.txt

# Со словарём
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Показать подобранные пароли
john --show hashes.txt
```

---

<a id="-cracking-modes"></a>
## 🎮 Режимы подбора паролей

<a id="single-crack-mode"></a>
### Режим Single Crack

Использует имена учётных записей и сведения GECOS для создания кандидатов паролей.

```bash
# Режим Single (быстрый перебор вариантов имени пользователя)
john --single hashes.txt

# С указанием формата
john --single --format=sha512crypt hashes.txt
```

<a id="wordlist-mode"></a>
### Режим словаря

Использует слова из словаря и, при необходимости, правила их преобразования.

```bash
# Базовый подбор по словарю
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Словарь с правилами
john --wordlist=wordlist.txt --rules hashes.txt

# Определённый набор правил
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
```

<a id="incremental-mode-brute-force"></a>
### Инкрементальный режим (полный перебор)

Перебирает комбинации символов в пределах выбранного набора и ограничений.

```bash
# Инкрементальный режим по умолчанию (набор символов зависит от конфигурации)
john --incremental hashes.txt

# Определённый набор символов
john --incremental=Digits hashes.txt      # Только цифры
john --incremental=Lower hashes.txt       # Только строчные буквы
john --incremental=Upper hashes.txt       # Только прописные буквы
john --incremental=Alpha hashes.txt       # Только буквы
john --incremental=Alnum hashes.txt       # Буквы и цифры
john --incremental=LowerNum hashes.txt    # Строчные буквы и цифры
```

<a id="mask-mode"></a>
### Режим маски

Перебор по заданному шаблону.

```bash
# Подбор по маске (? обозначает позиции шаблона)
john --mask='?l?l?l?l?d?d' hashes.txt

# Обозначения позиций:
# ?l = строчные буквы    ?u = прописные буквы
# ?d = цифры             ?s = специальные символы
# ?a = все символы       ?h = шестнадцатеричные строчные
```

<a id="external-mode"></a>
### Внешний режим (External)

Собственные алгоритмы подбора.

```bash
john --external=Filter_Alpha hashes.txt
```

---

<a id="-hash-extraction"></a>
## 🔍 Извлечение хешей

<a id="linux-passwords"></a>
### Пароли Linux

```bash
# Объединить файлы passwd и shadow
unshadow /etc/passwd /etc/shadow > unshadowed.txt

# Выполнить подбор по объединённому файлу
john unshadowed.txt
```

<a id="windows-hashes"></a>
### Хеши Windows

```bash
# Из файла SAM (с помощью pwdump, secretsdump и т. п.)
# Format: user:id:LM_hash:NTLM_hash:::

# С помощью hashdump в Metasploit
meterpreter> hashdump
Administrator:500:aad3b435b51404ee:31d6cfe0d16ae931:::

# Сохранить в файл и выполнить подбор
john --format=nt hashes.txt
```

<a id="file-passwords-2john-tools"></a>
### Пароли файлов (инструменты 2john)

```bash
# Файлы ZIP
zip2john protected.zip > zip_hash.txt

# Файлы RAR
rar2john protected.rar > rar_hash.txt

# Файлы PDF
pdf2john protected.pdf > pdf_hash.txt

# Документы Office
office2john document.docx > office_hash.txt

# Ключи SSH
ssh2john id_rsa > ssh_hash.txt

# Файлы 7zip
7z2john archive.7z > 7z_hash.txt

# Базы KeePass
keepass2john database.kdbx > keepass_hash.txt
```

---

<a id="-common-hash-types"></a>
## 🔢 Распространённые типы хешей

<a id="hash-format-table"></a>
### Таблица форматов хешей

| Тип хеша | Параметр формата | Пример |
| ----------- | ------------- | --------- |
| MD5 | `--format=raw-md5` | `5d41402abc4b2a76b9719d911017c592` |
| SHA1 | `--format=raw-sha1` | `aaf4c61ddcc5e8a2...` |
| SHA256 | `--format=raw-sha256` | `2cf24dba5fb0a30e...` |
| SHA512 | `--format=raw-sha512` | `cf83e1357eefb8bd...` |
| NTLM | `--format=nt` | `31d6cfe0d16ae931b73c59d7e0c089c0` |
| LM | `--format=lm` | `aad3b435b51404ee` |
| MySQL | `--format=mysql-sha1` | `*2470C0C06DEE42...` |
| bcrypt | `--format=bcrypt` | `$2a$10$N9qo8u...` |
| Linux SHA512 | `--format=sha512crypt` | `$6$rounds=5000$...` |
| Linux SHA256 | `--format=sha256crypt` | `$5$rounds=5000$...` |
| Linux MD5 | `--format=md5crypt` | `$1$salt$hash` |

<a id="list-all-formats"></a>
### Перечень всех форматов
```bash
# Перечислить все поддерживаемые форматы
john --list=formats

# Найти определённый формат
john --list=formats | grep -i ntlm
john --list=formats | grep -i sha

# Подсчитать строки перечня форматов (см. уточнение)
john --list=formats | wc -l
```

<a id="identify-hash-type"></a>
### Определение типа хеша
```bash
# Использовать hash-identifier
hash-identifier

# Или hashid
hashid -m '$6$salt$hash...'
```

---

<a id="-linux-password-cracking"></a>
## 🐧 Подбор паролей Linux

<a id="prepare-hashes"></a>
### Подготовка хешей
```bash
# Объединить passwd и shadow
sudo unshadow /etc/passwd /etc/shadow > linux_hashes.txt

# Или извлечь нужную запись с хешем
sudo cat /etc/shadow | grep username
```

<a id="hash-format-examples"></a>
### Примеры форматов хешей
```
# Формат /etc/shadow:
# username:$id$salt$hash:...

# $1$ = MD5
# $5$ = SHA-256
# $6$ = SHA-512 (в оригинале назван распространённым в современных Linux)
# $y$ = yescrypt (в оригинале назван новейшим)
```

<a id="crack-linux-hashes"></a>
### Подбор паролей по хешам Linux
```bash
# Автоопределение (рекомендуется)
john linux_hashes.txt

# Указать формат
john --format=sha512crypt linux_hashes.txt

# Со словарём
john --wordlist=/usr/share/wordlists/rockyou.txt linux_hashes.txt

# Показать результаты
john --show linux_hashes.txt
```

---

<a id="-windows-password-cracking"></a>
## 🪟 Подбор паролей Windows

<a id="hash-types"></a>
### Типы хешей

| Тип | Описание | Формат |
| ------ | ------------- | -------- |
| **LM** | Устаревший (слабый) | `--format=lm` |
| **NTLM** | Современная Windows | `--format=nt` |
| **NTLMv2** | Сетевая аутентификация | `--format=netntlmv2` |

<a id="crack-ntlm-hashes"></a>
### Подбор паролей по хешам NTLM
```bash
# Формат хеша NTLM:
# user:rid:lm_hash:ntlm_hash:::

# Выполнить подбор NTLM
john --format=nt windows_hashes.txt

# Со словарём
john --format=nt --wordlist=rockyou.txt windows_hashes.txt

# С правилами
john --format=nt --wordlist=rockyou.txt --rules windows_hashes.txt
```

<a id="extract-windows-hashes"></a>
### Извлечение хешей Windows
```bash
# С помощью Metasploit
meterpreter> hashdump

# С помощью secretsdump (Impacket)
secretsdump.py -sam sam -system system LOCAL

# С помощью pwdump
pwdump7.exe
```

---

<a id="-file-password-cracking"></a>
## 📄 Подбор паролей файлов

<a id="zip-files"></a>
### Файлы ZIP
```bash
# Извлечь хеш
zip2john protected.zip > zip.hash

# Выполнить подбор
john --wordlist=rockyou.txt zip.hash

# Показать пароль
john --show zip.hash
```

<a id="rar-files"></a>
### Файлы RAR
```bash
rar2john protected.rar > rar.hash
john --wordlist=rockyou.txt rar.hash
```

<a id="pdf-files"></a>
### Файлы PDF
```bash
pdf2john protected.pdf > pdf.hash
john --wordlist=rockyou.txt pdf.hash
```

<a id="office-documents"></a>
### Документы Office
```bash
# Word, Excel, PowerPoint
office2john document.docx > office.hash
john --wordlist=rockyou.txt office.hash
```

<a id="ssh-private-keys"></a>
### Закрытые ключи SSH
```bash
ssh2john id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

<a id="keepass-database"></a>
### База KeePass
```bash
keepass2john database.kdbx > keepass.hash
john --wordlist=rockyou.txt keepass.hash
```

---

<a id="-rules--mangling"></a>
## ⚙️ Правила и преобразование слов

<a id="enable-rules"></a>
### Включение правил
```bash
# Правила по умолчанию
john --wordlist=wordlist.txt --rules hashes.txt

# Определённый набор правил
john --wordlist=wordlist.txt --rules=Single hashes.txt
john --wordlist=wordlist.txt --rules=Wordlist hashes.txt
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
john --wordlist=wordlist.txt --rules=KoreLogic hashes.txt
```

<a id="list-available-rules"></a>
### Перечень доступных правил
```bash
john --list=rules
```

<a id="common-rule-operations"></a>
### Основные операции правил

| Правило | Описание | Пример |
| ------ | ------------- | --------- |
| `:` | Ничего не менять | password → password |
| `l` | Перевести всё в нижний регистр | PassWord → password |
| `u` | Перевести всё в верхний регистр | password → PASSWORD |
| `c` | Сделать первую букву прописной | password → Password |
| `r` | Развернуть слово | password → drowssap |
| `d` | Повторить слово | pass → passpass |
| `$X` | Добавить символ X в конец | pass → passX |
| `^X` | Добавить символ X в начало | pass → Xpass |
| `sXY` | Заменить X на Y | password → pa$$word |

<a id="custom-rules"></a>
### Собственные правила
```bash
# Добавить в john.conf [List.Rules:Custom]
[List.Rules:Custom]
:
l
u
c
$1
$!
$123
^2023
```

---

<a id="-session-management"></a>
## 💾 Управление сеансами

<a id="create-named-session"></a>
### Создание именованного сеанса
```bash
john --wordlist=rockyou.txt --session=mycrack hashes.txt
```

<a id="restore-session"></a>
### Восстановление сеанса
```bash
# Восстановить прерванный сеанс
john --restore=mycrack

# Восстановить сеанс по умолчанию
john --restore
```

<a id="check-status"></a>
### Проверка состояния
```bash
# Нажать любую клавишу во время подбора для просмотра состояния
# Или проверить файл .rec
cat ~/.john/mycrack.rec
```

<a id="pot-file"></a>
### Файл pot
```bash
# Файл pot по умолчанию
~/.john/john.pot

# Собственный файл pot
john --pot=custom.pot hashes.txt

# Показать все найденные пароли
john --show --pot=custom.pot hashes.txt
```

---

<a id="-performance-tuning"></a>
## 🚀 Настройка производительности

<a id="fork-multi-process"></a>
### Fork (несколько процессов)
```bash
# Использовать несколько ядер CPU
john --fork=4 --wordlist=rockyou.txt hashes.txt
```

<a id="opencl-gpu"></a>
### OpenCL (GPU)
```bash
# Перечислить устройства OpenCL
john --list=opencl-devices

# Использовать GPU
john --format=sha512crypt-opencl --wordlist=rockyou.txt hashes.txt
```

<a id="limit-candidates"></a>
### Ограничение кандидатов
```bash
# Ограничить длину пароля
john --min-length=6 --max-length=12 --incremental hashes.txt
```

<a id="memory-usage"></a>
### Использование памяти
```bash
# Уменьшить использование памяти
john --mem-file-size=32 --wordlist=huge.txt hashes.txt
```

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-linux-shadow-file"></a>
### Пример 1: файл shadow Linux
```bash
# Объединить файлы
sudo unshadow /etc/passwd /etc/shadow > hashes.txt

# Выполнить подбор со словарём rockyou
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Показать результаты
john --show hashes.txt
```

<a id="example-2-windows-ntlm"></a>
### Пример 2: Windows NTLM
```bash
# Из вывода hashdump
echo "admin:500:aad3b435b51404ee:31d6cfe0d16ae931:::" > ntlm.txt

# Выполнить подбор
john --format=nt --wordlist=rockyou.txt ntlm.txt
```

<a id="example-3-zip-file-password"></a>
### Пример 3: пароль файла ZIP
```bash
zip2john secret.zip > zip.hash
john --wordlist=rockyou.txt zip.hash
john --show zip.hash
```

<a id="example-4-ssh-key-passphrase"></a>
### Пример 4: парольная фраза ключа SSH
```bash
ssh2john ~/.ssh/id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

<a id="example-5-md5-hashes"></a>
### Пример 5: хеши MD5
```bash
echo "5d41402abc4b2a76b9719d911017c592" > md5.txt
john --format=raw-md5 --wordlist=rockyou.txt md5.txt
```

<a id="example-6-web-application-hashes"></a>
### Пример 6: хеши веб-приложения
```bash
# Хеш bcrypt
echo '$2a$10$N9qo8uLOickgx2ZMRZoMy...' > bcrypt.txt
john --format=bcrypt --wordlist=rockyou.txt bcrypt.txt
```

<a id="example-7-with-rules"></a>
### Пример 7: применение правил
```bash
john --wordlist=small_wordlist.txt --rules=Jumbo hashes.txt
```

<a id="example-8-incremental-attack"></a>
### Пример 8: инкрементальная атака
```bash
# Перебрать кандидаты длиной 6-8 символов из строчных букв и цифр
john --incremental=LowerNum --min-length=6 --max-length=8 hashes.txt
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Задача | Команда |
| ------ | --------- |
| Базовый подбор | `john hashes.txt` |
| Подбор по словарю | `john --wordlist=rockyou.txt hashes.txt` |
| С правилами | `john --wordlist=words.txt --rules hashes.txt` |
| Показать найденные пароли | `john --show hashes.txt` |
| Определённый формат | `john --format=raw-md5 hashes.txt` |
| Инкрементальный режим | `john --incremental hashes.txt` |
| Восстановить сеанс | `john --restore=session` |
| Перечислить форматы | `john --list=formats` |

<a id="2john-tools"></a>
### Инструменты 2john

| Инструмент | Назначение |
| ------ | --------- |
| `unshadow` | Файлы passwd/shadow Linux |
| `zip2john` | Файлы ZIP |
| `rar2john` | Файлы RAR |
| `pdf2john` | Файлы PDF |
| `office2john` | Документы Office |
| `ssh2john` | Ключи SSH |
| `keepass2john` | Базы KeePass |

<a id="common-formats"></a>
### Распространённые форматы

| Хеш | Параметр формата |
| ------ | ------------- |
| MD5 | `raw-md5` |
| SHA1 | `raw-sha1` |
| SHA256 | `raw-sha256` |
| NTLM | `nt` |
| Linux SHA512 | `sha512crypt` |
| bcrypt | `bcrypt` |
| MySQL | `mysql-sha1` |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендуемые практики

<a id="efficiency-tips"></a>
### Советы по эффективности

1. **Сначала попробовать режим Single**
   ```bash
   john --single hashes.txt
   ```

2. **Использовать словари с учётом цели**
   ```bash
   # Название компании, город и т. п.
   john --wordlist=targeted.txt hashes.txt
   ```

3. **Начать с небольших словарей**
   ```bash
   john --wordlist=top1000.txt hashes.txt
   # Затем попробовать более крупные словари
   ```

4. **Сочетать режимы**
   ```bash
   john --single hashes.txt
   john --wordlist=rockyou.txt --rules hashes.txt
   john --incremental hashes.txt
   ```

<a id="performance-tips"></a>
### Советы по производительности

```bash
# Использовать несколько ядер
john --fork=4 hashes.txt

# Ускорение на GPU (Jumbo)
john --format=sha512crypt-opencl hashes.txt
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовая оговорка

> **ПРЕДУПРЕЖДЕНИЕ:** John the Ripper — мощный инструмент подбора паролей, который следует использовать **только с разрешения**.
> 
> - ✅ Проверяйте стойкость собственных паролей
> - ✅ Проводите проверки с явного разрешения
> - ✅ Выполняйте согласованные проверки безопасности
> - ❌ Не подбирайте пароли без разрешения
> - ❌ Несанкционированный доступ незаконен
> 
> **Подбор паролей без разрешения незаконен и может повлечь уголовное преследование.**

---

<a id="-resources"></a>
## 📚 Полезные материалы

<a id="official-resources"></a>
### Официальные материалы
- [Официальный сайт John the Ripper](https://www.openwall.com/john/)
- [John Jumbo на GitHub](https://github.com/openwall/john)
- [Вики John](https://openwall.info/wiki/john)

<a id="wordlists"></a>
### Словари
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Hydra](../../Hydra/translations/README.ru.md)
- [Metasploit](../../Metasploit/translations/README.ru.md)
- [Nmap](../../Nmap/translations/README.ru.md)

---

<p align="center">
  <b>🔨 Освойте проверку стойкости паролей!</b><br>
  <i>Всегда действуйте ответственно</i>
</p>
