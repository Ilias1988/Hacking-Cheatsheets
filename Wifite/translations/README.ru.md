<a id="-wifite---automated-wifi-auditor-cheatsheet"></a>
# 📶 Wifite — шпаргалка по автоматизированному аудиту Wi-Fi

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇷🇺 Русский — главная](../../README.ru.md) • [🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗██╗███████╗██╗████████╗███████╗
  ██║    ██║██║██╔════╝██║╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║█████╗  ██║   ██║   █████╗  
  ██║███╗██║██║██╔══╝  ██║   ██║   ██╔══╝  
  ╚███╔███╔╝██║██║     ██║   ██║   ███████╗
   ╚══╝╚══╝ ╚═╝╚═╝     ╚═╝   ╚═╝   ╚══════╝
         Автоматизированный аудит Wi-Fi
```

<p align="center">
  <img src="https://img.shields.io/badge/Wifite-Automated_WiFi-blue?style=for-the-badge" alt="Wifite">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Python-yellow?style=for-the-badge" alt="Python">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Wifite](#-what-is-wifite)
- [Установка](#-installation)
- [Основы использования](#-basic-usage)
- [Выбор целей](#-target-selection)
- [Параметры атак](#-attack-options)
- [Атаки на WPS](#-wps-attacks)
- [Атаки с использованием PMKID](#-pmkid-attacks)
- [Дополнительные параметры](#-advanced-options)
- [Параметры подбора паролей](#-cracking-options)
- [Примеры](#-examples)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-wifite"></a>
## 🎯 Что такое Wifite

**Wifite** (Wifite2) — инструмент автоматизированного аудита беспроводных сетей, который предоставляет следующие возможности:

- 🤖 **Автоматизация атак** — без ручного ввода команд
- 🎯 **Несколько целей** — атака на несколько сетей
- 🔓 **WPA/WPA2/WEP/WPS** — все типы шифрования
- 💾 **Сохранение прогресса** — возобновление прерванных атак
- 🔄 **Захват PMKID** — современные атаки без подключённого клиента

<a id="wifite-vs-wifite2"></a>
### Wifite и Wifite2

| Возможность | Wifite (исходный) | Wifite2 |
|---------|-------------------|---------|
| Поддерживается | Нет | Да |
| Python | Python 2 | Python 3 |
| PMKID | Нет | Да |
| Код | Запутанный | Полностью переписан и упорядочен |
| Использование | Не рекомендуется | **Рекомендуется** |

> **Уточнение:** WPS — механизм настройки подключения, а не тип шифрования; перечень выше не охватывает все современные режимы Wi-Fi. Исходный проект теперь направляет пользователей Wifite2 к [ветке kimocoder](https://github.com/kimocoder/wifite2), а также сообщает об отдельном Wifit3. Эта шпаргалка сохраняет примеры Wifite2; сведения о поддержке и Python зависят от ветки. [Исходный README](https://github.com/derv82/wifite2/blob/master/README.md).

<a id="features"></a>
### Возможности

| Возможность | Описание |
|---------|-------------|
| **Автоматизация** | Аудит Wi-Fi после выбора цели |
| **Умный выбор целей** | Выбор по уровню сигнала |
| **Все атаки** | WPA, WEP, WPS, PMKID |
| **Подбор паролей** | Интеграция с Hashcat/aircrack |
| **Возобновление** | Продолжение прерванных сеансов |

---

<a id="-installation"></a>
## 🚀 Установка

<a id="kali-linux-pre-installed"></a>
### Kali Linux (предустановлен)

```bash
# Уже установлен в Kali
wifite --help

# Обновление до последней версии
sudo apt update && sudo apt install wifite
```

<a id="install-wifite2-from-github"></a>
### Установка Wifite2 из GitHub

```bash
# Клонирование репозитория
git clone https://github.com/derv82/wifite2.git
cd wifite2

# Непосредственный запуск
sudo python3 wifite.py

# Или установка
sudo python3 setup.py install
```

<a id="dependencies"></a>
### Зависимости

```bash
# Обязательный компонент
sudo apt install aircrack-ng

# Рекомендуется для полной функциональности
sudo apt install reaver bully pixiewps hashcat hcxdumptool hcxtools
```

> **Зависимости:** одной установки Aircrack-ng недостаточно для всех функций. Нужны совместимые версии внешних инструментов, адаптер и драйвер с режимом мониторинга (monitor mode) и инъекцией пакетов; набор системных утилит зависит от ветки. См. [требования Wifite2](https://github.com/kimocoder/wifite2#required-tools). Наличие Wifite в Kali зависит от выбранного образа и набора пакетов.

<a id="verify-installation"></a>
### Проверка установки

```bash
wifite --help
wifite --version
```

---

> **Совместимость примеров:** в [парсере аргументов исходной ветки Wifite2](https://github.com/derv82/wifite2/blob/master/wifite/args.py) отсутствуют многие флаги ниже: `--version`, `--power`, `--2ghz`, `--quiet`, `--scan-time`, `--attack-timeout`, `--skip-crack`, `--skip-cracked`, `--no-kill`, `--no-random-mac`, `--hashcat`, `--hashcat-path`, `--aircrack`, `--aircrack-path`, `--no-wpa`, `--no-wep`, `--no-pmkid`, `--reaver`, `--pixie-timeout`, `--wps-timeout`, `--wpa-deauth-timeout`. Примеры сохранены как в оригинале, но могут завершиться ошибкой. Фактические параметры следует сверять со справкой своей версии. В этой ветке `--wpadt` задаёт интервал деаутентификации, `--wpat` — тайм-аут WPA; `--5ghz` добавляет каналы 5 ГГц, а не ограничивает сканирование только ими.

<a id="-basic-usage"></a>
## 💻 Основы использования

<a id="quick-start"></a>
### Быстрый старт

```bash
# Просто запустите wifite (интерфейс определится автоматически)
sudo wifite

# Указание интерфейса
sudo wifite -i wlan0
```

<a id="basic-workflow"></a>
### Основной порядок работы

```
1. Запустите wifite
2. Дождитесь появления сетей
3. Нажмите Ctrl+C, чтобы остановить сканирование
4. Выберите одну или несколько целей по номеру
5. Wifite автоматически выполняет атаки
6. Подберите пароль по захваченным рукопожатиям (handshake)
```

<a id="interactive-mode"></a>
### Интерактивный режим

```bash
# Запуск wifite
sudo wifite

# Пример вывода:
   NUM  ESSID              ENCR  POWER  WPS  CLIENT
   ---  -----------------  ----  -----  ---  ------
    1   HomeNetwork        WPA2   -45   Yes     2
    2   Office_WiFi        WPA2   -52   No      0
    3   Free_Hotspot       OPN    -60   No      5

# Введите номера целей или нажмите Ctrl+C, чтобы выбрать все видимые
```

---

<a id="-target-selection"></a>
## 🎯 Выбор целей

<a id="select-by-number"></a>
### Выбор по номеру

```bash
# После сканирования выберите цели по номерам
# Пример: выбор целей 1 и 3
1, 3

# Выбор диапазона
1-5

# Выбор всех целей
all
```

<a id="filter-by-essid"></a>
### Фильтрация по ESSID

```bash
# Выбор сети с конкретным именем
sudo wifite --essid "TargetNetwork"

# Несколько ESSID (имён сетей)
sudo wifite --essid "Network1" --essid "Network2"
```

> **ESSID:** в [исходном парсере](https://github.com/derv82/wifite2/blob/master/wifite/args.py) повторные `--essid` и `--dict` сохраняют последнее значение, а не создают список.

<a id="filter-by-bssid"></a>
### Фильтрация по BSSID

```bash
# Выбор конкретного MAC-адреса
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

<a id="filter-by-encryption"></a>
### Фильтрация по типу шифрования

```bash
# Только сети WPA
sudo wifite --wpa

# Только сети WEP
sudo wifite --wep

# Только сети с включённым WPS
sudo wifite --wps
```

<a id="filter-by-channel"></a>
### Фильтрация по каналу

```bash
# Конкретный канал
sudo wifite -c 6

# Только 5 ГГц
sudo wifite --5ghz

# Только 2,4 ГГц
sudo wifite --2ghz
```

<a id="filter-by-signal"></a>
### Фильтрация по уровню сигнала

```bash
# Минимальный уровень сигнала (dBm: чем ближе к 0, тем сильнее)
sudo wifite --power -50
```

---

<a id="-attack-options"></a>
## ⚔️ Параметры атак

<a id="wpawpa2-options"></a>
### Параметры WPA/WPA2

```bash
# Установка тайм-аута деаутентификации
sudo wifite --wpa-deauth-timeout 30

# Количество отправляемых кадров деаутентификации
sudo wifite --num-deauths 5

# Без захвата рукопожатия, только PMKID
sudo wifite --pmkid
```

<a id="deauthentication-options"></a>
### Параметры деаутентификации

```bash
# Количество пакетов деаутентификации
sudo wifite --num-deauths 10

# Непрерывная деаутентификация
sudo wifite --num-deauths 0
```

> **Уточнение:** в [конфигурации исходной ветки](https://github.com/derv82/wifite2/blob/master/wifite/config.py) `--num-deauths 0` не устанавливает непрерывную деаутентификацию: применяются только положительные значения.

<a id="attack-order"></a>
### Порядок атак

```bash
# Пропуск атак на WPA
sudo wifite --no-wpa

# Пропуск атак на WEP
sudo wifite --no-wep

# Пропуск атак на WPS
sudo wifite --no-wps

# Пропуск атак с PMKID
sudo wifite --no-pmkid
```

<a id="kill-networkmanager"></a>
### Завершение NetworkManager

```bash
# Автоматическое завершение мешающих процессов
sudo wifite --kill

# Не завершать процессы
sudo wifite --no-kill
```

---

<a id="-wps-attacks"></a>
## 🔓 Атаки на WPS

<a id="enable-wps"></a>
### Включение WPS

```bash
# Атаковать только сети с включённым WPS
sudo wifite --wps

# Режим только WPS
sudo wifite --wps-only
```

<a id="wps-tools"></a>
### Инструменты WPS

```bash
# Использовать Reaver (по умолчанию)
sudo wifite --wps --reaver

# Использовать Bully
sudo wifite --wps --bully
```

<a id="pixie-dust-attack"></a>
### Атака Pixie-Dust

```bash
# Включение Pixie-Dust (автономная атака на WPS)
sudo wifite --wps --pixie

# Тайм-аут Pixie-Dust
sudo wifite --wps --pixie-timeout 300
```

<a id="wps-options"></a>
### Параметры WPS

```bash
# Тайм-аут WPS на одну попытку PIN
sudo wifite --wps-time 120

# Максимальное количество повторных попыток WPS
sudo wifite --wps-fails 10

# Общий тайм-аут WPS
sudo wifite --wps-timeout 300
```

---

> **WPS:** [исходный парсер](https://github.com/derv82/wifite2/blob/master/wifite/args.py) различает фильтр `--wps` и ограничение атак `--wps-only`. `--wps-only` допускает PIN и Pixie-Dust; `--pixie` исключает обычный перебор PIN, а `--wps-time` ограничивает всё ожидание Pixie-Dust, а не отдельную попытку PIN. Наличие WPS само по себе не гарантирует уязвимость к Pixie-Dust: см. [описание Pixiewps](https://github.com/wiire-a/pixiewps#overview).

<a id="-pmkid-attacks"></a>
## 🔐 Атаки с использованием PMKID

<a id="what-is-pmkid"></a>
### Что такое PMKID?

Атака с использованием PMKID (Pairwise Master Key Identifier — идентификатор парного главного ключа):
- ✅ Получение PMK непосредственно от точки доступа (AP)
- ✅ Подключённый клиент не нужен
- ✅ Деаутентификация не требуется
- ✅ Работает в большинстве сетей WPA2

> **Уточнение PMKID:** захватывается **идентификатор PMKID, а не сам секретный PMK**. Он позволяет проверять догадки о пароле автономно; поддерживающая такой обмен точка доступа может предоставить его без обычного подключённого клиента. Применимость зависит от реализации и настроек AP: утверждение о «большинстве сетей WPA2» не является гарантией. См. [описание авторов Hashcat](https://hashcat.net/forum/thread-7717.html).

<a id="pmkid-options"></a>
### Параметры PMKID

```bash
# Только атаки с PMKID (без рукопожатия)
sudo wifite --pmkid

# Тайм-аут PMKID
sudo wifite --pmkid-timeout 120
```

<a id="how-pmkid-works"></a>
### Как работает атака с PMKID

```
1. Отправьте точке доступа запрос ассоциации
2. Точка доступа отвечает кадром EAPOL с PMKID
3. Захватите PMKID
4. Выполните автономный подбор пароля с hashcat
```

---

<a id="-advanced-options"></a>
## ⚙️ Дополнительные параметры

<a id="interface-options"></a>
### Параметры интерфейса

```bash
# Указание интерфейса
sudo wifite -i wlan0

# Указание интерфейса в режиме мониторинга
sudo wifite -i wlan0mon
```

<a id="output-options"></a>
### Параметры вывода

```bash
# Подробный вывод
sudo wifite -v
sudo wifite --verbose

# Очень подробный вывод
sudo wifite -vv

# Тихий режим
sudo wifite --quiet
```

<a id="timing-options"></a>
### Временные параметры

```bash
# Время сканирования (секунды)
sudo wifite --scan-time 30

# Общий тайм-аут на одну цель
sudo wifite --attack-timeout 300
```

<a id="cracked-passwords"></a>
### Подобранные пароли

```bash
# Подобранные пароли сохраняются в:
# ~/.config/wifite/cracked.txt

# Просмотр сетей с подобранными паролями
cat ~/.config/wifite/cracked.txt

# Или указание собственного файла
sudo wifite --cracked cracked_passwords.txt
```

> **Файлы и возобновление:** в [конфигурации исходной ветки](https://github.com/derv82/wifite2/blob/master/wifite/config.py) по умолчанию указаны `cracked.txt` и `hs` относительно текущего каталога. Дерево `~/.config/wifite/` ниже — пример оригинала, а не универсальное расположение. Здесь `--cracked` выводит ранее полученные результаты, а не принимает имя файла. В [исходном парсере](https://github.com/derv82/wifite2/blob/master/wifite/args.py) `--crack` выводит команды подбора и не принимает позиционный путь из примера ниже. Сохранение результатов и захватов само по себе не означает восстановление состояния прерванной атаки; возможность возобновления нужно проверять для своей версии.

<a id="skip-options"></a>
### Параметры пропуска

```bash
# Пропуск подбора паролей (только захват)
sudo wifite --skip-crack

# Пропуск целей, пароли которых уже подобраны
sudo wifite --skip-cracked
```

<a id="random-mac"></a>
### Случайный MAC-адрес

```bash
# Случайный MAC-адрес
sudo wifite --random-mac

# Не менять MAC-адрес
sudo wifite --no-random-mac
```

---

<a id="-cracking-options"></a>
## 🔓 Параметры подбора паролей

<a id="dictionary-attack"></a>
### Атака по словарю

```bash
# Указание словаря паролей
sudo wifite --dict /usr/share/wordlists/rockyou.txt

# Несколько словарей паролей
sudo wifite --dict wordlist1.txt --dict wordlist2.txt
```

<a id="hashcat-integration"></a>
### Интеграция с Hashcat

```bash
# Использовать hashcat для подбора паролей
sudo wifite --hashcat

# Расположение Hashcat
sudo wifite --hashcat-path /usr/bin/hashcat
```

<a id="aircrack-ng-options"></a>
### Параметры Aircrack-ng

```bash
# Использовать aircrack-ng (по умолчанию)
sudo wifite --aircrack

# Путь к Aircrack-ng
sudo wifite --aircrack-path /usr/bin/aircrack-ng
```

<a id="crack-only-mode"></a>
### Режим только подбора паролей

```bash
# Подбор паролей по существующим файлам рукопожатий
sudo wifite --crack

# Указание файла захвата
sudo wifite --crack --dict wordlist.txt /path/to/capture.cap
```

---

<a id="-examples"></a>
## 📋 Примеры

<a id="basic-wifi-audit"></a>
### Базовый аудит Wi-Fi

```bash
# Сканирование и атака на все сети
sudo wifite

# Атака с собственным словарём паролей
sudo wifite --dict /usr/share/wordlists/rockyou.txt
```

<a id="target-specific-network"></a>
### Выбор конкретной сети

```bash
# Выбор по имени
sudo wifite --essid "TargetNetwork" --dict rockyou.txt

# Выбор по MAC-адресу
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

<a id="wpa2-only-attack"></a>
### Атака только на WPA2

```bash
# Только сети WPA2 с PMKID
sudo wifite --wpa --pmkid --dict rockyou.txt
```

<a id="wps-attack"></a>
### Атака на WPS

```bash
# WPS с Pixie-Dust
sudo wifite --wps --pixie

# Только полный перебор WPS
sudo wifite --wps-only --bully
```

<a id="strong-signal-only"></a>
### Только сильный сигнал

```bash
# Атаковать только сети с сильным сигналом
sudo wifite --power -50 --wpa
```

<a id="5ghz-networks"></a>
### Сети 5 ГГц

```bash
# Атака только в диапазоне 5 ГГц
sudo wifite --5ghz --wpa
```

<a id="quick-audit-no-cracking"></a>
### Быстрый аудит (без подбора паролей)

```bash
# Только захват рукопожатий, подбор пароля позже
sudo wifite --skip-crack
```

<a id="resume-attack"></a>
### Возобновление атаки

```bash
# Wifite автоматически сохраняет прогресс
# Для возобновления достаточно запустить снова
sudo wifite

# Пропуск сетей с уже подобранными паролями
sudo wifite --skip-cracked
```

<a id="verbose-mode"></a>
### Подробный вывод

```bash
# Показать все подробности
sudo wifite -vv --dict rockyou.txt
```

<a id="stealth-mode"></a>
### Скрытный режим

```bash
# Минимум кадров деаутентификации, тихий режим
sudo wifite --num-deauths 1 --quiet
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Назначение | Команда |
|---------|---------|
| Базовое сканирование | `sudo wifite` |
| Выбор по имени | `sudo wifite --essid "Name"` |
| Выбор по MAC-адресу | `sudo wifite --bssid MAC` |
| Только WPA | `sudo wifite --wpa` |
| Только WPS | `sudo wifite --wps-only` |
| Атака с PMKID | `sudo wifite --pmkid` |
| Со словарём паролей | `sudo wifite --dict wordlist.txt` |
| Без подбора паролей | `sudo wifite --skip-crack` |

<a id="attack-types"></a>
### Типы атак

| Атака | Флаг | Цель |
|--------|------|--------|
| Рукопожатие WPA | `--wpa` | WPA/WPA2-PSK |
| PMKID | `--pmkid` | WPA/WPA2-PSK |
| WPS Pixie | `--wps --pixie` | WPS включён |
| Полный перебор WPS | `--wps-only` | WPS включён |
| WEP | `--wep` | WEP (устарел) |

<a id="common-wordlists"></a>
### Распространённые словари паролей

| Путь | Описание |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | Наиболее распространённые пароли |
| `/usr/share/wordlists/fasttrack.txt` | Быстрая проверка |
| `/usr/share/seclists/Passwords/WiFi-WPA/` | Специально для Wi-Fi |

<a id="captured-files"></a>
### Файлы захвата

```
~/.config/wifite/
├── cracked.txt          # Подобранные пароли
├── hs/                  # Захваты рукопожатий
│   └── handshake_*.cap
└── pmkid/               # Захваты PMKID
    └── pmkid_*.22000
```

<a id="flags-quick-reference"></a>
### Краткий справочник флагов

| Флаг | Описание |
|------|-------------|
| `-i` | Интерфейс |
| `-c` | Канал |
| `-v` | Подробный вывод |
| `--wpa` | Только WPA/WPA2 |
| `--wep` | Только WEP |
| `--wps` | WPS включён |
| `--pmkid` | Атака с PMKID |
| `--dict` | Словарь паролей |
| `--kill` | Завершение процессов |
| `--power` | Минимальный уровень сигнала |
| `--5ghz` | Диапазон 5 ГГц |
| `--2ghz` | Диапазон 2,4 ГГц |

<a id="troubleshooting"></a>
### Устранение неполадок

```bash
# Интерфейс не найден
sudo airmon-ng check kill
sudo wifite -i wlan0

# Сети не найдены
# - Проверьте поддержку режима мониторинга у адаптера
# - Попробуйте другие диапазоны: --5ghz или --2ghz
# - Подойдите ближе к целям

# Рукопожатие не захвачено
# - Увеличьте количество кадров деаутентификации: --num-deauths 10
# - Подождите дольше: --wpa-deauth-timeout 60
# - Попробуйте других клиентов

# Не удаётся подобрать пароль
# - Используйте более подходящий словарь паролей
# - Попробуйте hashcat с GPU: --hashcat
```

---

<a id="-wifite-vs-manual-aircrack-ng"></a>
## 🔄 Wifite и ручная работа с Aircrack-ng

| Задача | Wifite | Набор Aircrack-ng |
|------|--------|-------------------|
| Режим мониторинга | Автоматически | `airmon-ng start wlan0` |
| Сканирование | Автоматически | `airodump-ng wlan0mon` |
| Выбор цели | Интерактивно | Ручная фильтрация |
| Деаутентификация | Автоматически | `aireplay-ng -0 ...` |
| Захват | Автоматически | `airodump-ng -w ...` |
| Подбор пароля | Автоматически | `aircrack-ng -w ...` |
| **Трудозатраты** | **Просто** | Вручную |

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовое предупреждение

```
⚠️ ВНИМАНИЕ: используйте Wifite только в СВОИХ сетях или при наличии
ЯВНОГО ПИСЬМЕННОГО РАЗРЕШЕНИЯ на тестирование.

✅ Разрешённое тестирование на проникновение
✅ Аудит безопасности (с разрешения)
✅ Обучение (в лабораторной среде)

❌ Несанкционированный доступ к сетям НЕЗАКОНЕН
❌ Не используйте инструмент во вредоносных целях
```

---

<a id="-resources"></a>
## 📚 Материалы

- [Wifite2 GitHub](https://github.com/derv82/wifite2)
- [Wifite Wiki](https://github.com/derv82/wifite2/wiki)
- [Aircrack-ng](https://www.aircrack-ng.org/)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Aircrack-ng](../../Aircrack-ng/translations/README.ru.md)
- [Hashcat](../../Hashcat/translations/README.ru.md)
- [Команды Linux](../../Linux-Commands/translations/README.ru.md)

---

<p align="center">
  <b>📶 Автоматизированный аудит Wi-Fi!</b><br>
  <i>Wifite — выберите цель, нажмите и проверьте защиту (этично!)</i>
</p>
