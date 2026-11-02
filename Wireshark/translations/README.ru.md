<a id="-wireshark---complete-cheatsheet"></a>
# 🦈 Wireshark — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)


[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 __        ___           ____  _                _    
 \ \      / (_)_ __ ___ / ___|| |__   __ _ _ __| | __
  \ \ /\ / /| | '__/ _ \\___ \| '_ \ / _` | '__| |/ /
   \ V  V / | | | |  __/ ___) | | | | (_| | |  |   < 
    \_/\_/  |_|_|  \___|____/|_| |_|\__,_|_|  |_|\_\
                                                     
           The Network Protocol Analyzer
```

<p align="center">
  <img src="https://img.shields.io/badge/Wireshark-Network_Analyzer-blue?style=for-the-badge" alt="Wireshark">
  <img src="https://img.shields.io/badge/Packet-Capture-red?style=for-the-badge" alt="Packet Capture">
  <img src="https://img.shields.io/badge/Protocol-Analysis-green?style=for-the-badge" alt="Protocol">
  <img src="https://img.shields.io/badge/Open-Source-orange?style=for-the-badge" alt="Open Source">
</p>

<p align="center">
  <b>🌐 Один из самых популярных анализаторов сетевых протоколов</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Установка](#-installation)
- [Обзор интерфейса](#-interface-overview)
- [Основы захвата пакетов](#-capture-basics)
- [Фильтры отображения](#-display-filters)
- [Фильтры захвата](#-capture-filters)
- [Анализ протоколов](#-protocol-analysis)
- [Просмотр потоков](#-following-streams)
- [Статистика и анализ](#-statistics--analysis)
- [tshark (командная строка)](#-tshark-cli)
- [Практические примеры](#-real-world-examples)
- [Краткий справочник](#-quick-reference)
- [Сочетания клавиш](#-keyboard-shortcuts)
- [Советы и рекомендуемые практики](#-tips--best-practices)
- [Полезные материалы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

> **Уточнения к оригиналу:** фильтры отображения и фильтры захвата используют разный синтаксис. В [официальном руководстве по выражениям](https://www.wireshark.org/docs/wsug_html_chunked/ChWorkBuildDisplayFilterSection.html) описаны изменения сравнения != начиная с 3.6 и особенности полей с несколькими значениями. В примере диапазона tcp.port две части условия могут совпасть с разными портами одного пакета; это не всегда проверка одного порта внутри диапазона. Исходные фильтры оставлены буквально.

> **Интерпретация результатов:** фильтр SYN или ACK из оригинала не выделяет только трёхэтапное рукопожатие, поскольку ACK присутствует и у многих других пакетов. Значение TLS record 0x0303 само по себе не доказывает согласование TLS 1.2; TLS 1.3 также использует legacy-поле этой версии. Видимость полей зависит от протокола, расшифрования и версии Wireshark. См. [справочник фильтров](https://www.wireshark.org/docs/man-pages/wireshark-filter) и [RFC 8446](https://www.rfc-editor.org/rfc/rfc8446#section-5.1).

> **Подпись ASCII-эмблемы:** The Network Protocol Analyzer — анализатор сетевых протоколов. Имена столбцов, меню и сочетания клавиш сохранены на английском для сопоставления с интерфейсом.

**Wireshark** — бесплатный анализатор пакетов с открытым исходным кодом для диагностики сетевых проблем, анализа, разработки программ и аудита безопасности.

<a id="key-features"></a>
### Основные возможности

| Возможность | Описание |
| --------- | ------------- |
| **Подробный анализ** | Поддержка сотен протоколов |
| **Захват в реальном времени** | Захват пакетов в реальном времени |
| **Анализ сохранённых данных** | Чтение сохранённых файлов (pcap, pcapng) |
| **Гибкие фильтры** | Фильтры отображения и захвата |
| **Поддержка разных платформ** | Windows, Linux, macOS |
| **Графический и командный интерфейсы** | Wireshark (графический интерфейс) и tshark (командная строка) |

<a id="use-cases"></a>
### Области применения

| Применение | Описание |
| ---------- | ------------- |
| **Диагностика сети** | Поиск причин проблем с соединениями |
| **Анализ безопасности** | Выявление вредоносного трафика |
| **Разработка протоколов** | Тестирование новых протоколов |
| **Цифровая криминалистика** | Анализ сохранённого трафика |
| **Обучение** | Изучение сетей |

---

<a id="-installation"></a>
## 📥 Установка

<a id="windows"></a>
### Windows
```bash
# Скачать с официального сайта
https://www.wireshark.org/download.html

# Или через Chocolatey
choco install wireshark
```

<a id="linux-kaliubuntu"></a>
### Linux (Kali/Ubuntu)
```bash
# Kali (предустановлен)
wireshark

# Ubuntu/Debian
sudo apt install wireshark

# Разрешить захват без root
sudo usermod -aG wireshark $USER
# Выйдите из сеанса и войдите снова
```

<a id="macos"></a>
### macOS
```bash
brew install --cask wireshark
```

<a id="verify-installation"></a>
### Проверка установки
```bash
wireshark --version
tshark --version
```

---

<a id="-interface-overview"></a>
## 🖥️ Обзор интерфейса

<a id="main-window-areas"></a>
### Основные области окна

| Область | Описание |
| ------ | ------------- |
| **Строка меню** | File, Edit, View, Go, Capture, Analyze |
| **Основная панель инструментов** | Кнопки быстрого доступа |
| **Строка фильтра** | Ввод фильтров отображения |
| **Список пакетов** | Все захваченные пакеты |
| **Сведения о пакете** | Уровни протоколов выбранного пакета |
| **Байты пакета** | Исходные данные в hex/ASCII |

<a id="packet-list-columns-default"></a>
### Столбцы списка пакетов по умолчанию

| Столбец | Описание |
| -------- | ------------- |
| No. | Номер пакета |
| Time | Временная отметка |
| Source | IP/MAC источника |
| Destination | IP/MAC назначения |
| Protocol | Протокол верхнего уровня |
| Length | Размер пакета в байтах |
| Info | Сведения, зависящие от протокола |

---

<a id="-capture-basics"></a>
## 📡 Основы захвата пакетов

<a id="start-capture"></a>
### Начало захвата

```
1. Выберите интерфейс на начальном экране
2. Нажмите "Start Capture" (синий плавник акулы)
3. Или: Capture → Start

Быстро: дважды нажмите имя интерфейса
```

<a id="stop-capture"></a>
### Остановка захвата

```
Нажмите красный квадрат на панели инструментов
Или: Capture → Stop
Или: Ctrl+E
```

<a id="save-capture"></a>
### Сохранение захвата

```
File → Save As
Форматы: pcapng (по умолчанию), pcap и другие
```

<a id="open-capture-file"></a>
### Открытие файла захвата

```
File → Open
Или: Ctrl+O
Или: wireshark file.pcap
```

---

<a id="-display-filters"></a>
## 🔍 Фильтры отображения

<a id="filter-syntax"></a>
### Синтаксис фильтров

Фильтры отображения используют **синтаксис сравнения полей**:

```
field operator value
```

<a id="comparison-operators"></a>
### Операторы сравнения

| Оператор | Описание | Пример |
| ---------- | ------------- | --------- |
| `==` или `eq` | Равно | `ip.addr == 192.168.1.1` |
| `!=` или `ne` | Не равно | `ip.addr != 10.0.0.1` |
| `>` или `gt` | Больше | `frame.len > 100` |
| `<` или `lt` | Меньше | `tcp.port < 1024` |
| `>=` или `ge` | Больше или равно | `frame.len >= 64` |
| `<=` или `le` | Меньше или равно | `tcp.port <= 1024` |
| `contains` | Содержит строку | `http contains "password"` |
| `matches` | Соответствие регулярному выражению | `http.host matches ".*\.com"` |

<a id="logical-operators"></a>
### Логические операторы

| Оператор | Описание | Пример |
| ---------- | ------------- | --------- |
| `and` или `&&` | Логическое И | `ip.src == 192.168.1.1 and tcp.port == 80` |
| `or` или `\|\|` | Логическое ИЛИ | `tcp.port == 80 or tcp.port == 443` |
| `not` или `!` | Логическое НЕ | `not arp` |
| `()` | Группировка | `(ip.src == 10.0.0.1) and (tcp or udp)` |

<a id="ip-filters"></a>
### Фильтры IP-адресов

```bash
# Определённый IP-адрес (источник или назначение)
ip.addr == 192.168.1.1

# Только IP источника
ip.src == 192.168.1.1

# Только IP назначения
ip.dst == 192.168.1.1

# Диапазон IP-адресов (подсеть)
ip.addr == 192.168.1.0/24

# Исключить IP-адрес
ip.addr != 192.168.1.1

# Между двумя узлами
ip.addr == 192.168.1.1 and ip.addr == 192.168.1.2
```

<a id="port-filters"></a>
### Фильтры портов

```bash
# Определённый порт (источник или назначение)
tcp.port == 80
udp.port == 53

# Только порт источника
tcp.srcport == 443

# Только порт назначения
tcp.dstport == 22

# Диапазон портов
tcp.port >= 1 and tcp.port <= 1024

# Несколько портов
tcp.port == 80 or tcp.port == 443 or tcp.port == 8080
tcp.port in {80 443 8080}
```

<a id="protocol-filters"></a>
### Фильтры протоколов

```bash
# По имени протокола
http
dns
tcp
udp
arp
icmp
tls
ssh
ftp
smtp
dhcp

# Поля HTTP
http.request
http.response
http.request.method == "GET"
http.request.method == "POST"
http.response.code == 200
http.response.code >= 400

# Поля DNS
dns.qry.name == "google.com"
dns.qry.type == 1          # Запись A
dns.flags.response == 1    # Ответы DNS

# Поля TCP
tcp.flags.syn == 1         # Пакеты SYN
tcp.flags.fin == 1         # Пакеты FIN
tcp.flags.reset == 1       # Пакеты RST
tcp.analysis.retransmission  # Повторные передачи
tcp.stream == 5            # Определённый поток
```

<a id="content-filters"></a>
### Фильтры содержимого

```bash
# Содержит строку
http contains "password"
tcp contains "login"
frame contains "secret"

# Содержимое HTTP
http.request.uri contains "admin"
http.host == "example.com"
http.user_agent contains "Mozilla"
http.cookie contains "session"

# Соответствие без учёта регистра
http.host matches "(?i)example"
```

<a id="common-filter-examples"></a>
### Типичные примеры фильтров

```bash
# Только HTTP-трафик
http

# HTTPS-трафик
tls

# Запросы DNS
dns.flags.response == 0

# Сброшенные соединения (RST)
tcp.flags.reset == 1

# Большие пакеты
frame.len > 1000

# Определённый MAC-адрес
eth.addr == aa:bb:cc:dd:ee:ff

# Широковещательный трафик
eth.dst == ff:ff:ff:ff:ff:ff

# Нелокальный трафик
!(ip.addr == 192.168.0.0/16)

# Трёхэтапное рукопожатие TCP (подпись оригинала; см. уточнение)
tcp.flags.syn == 1 or tcp.flags.ack == 1

# POST-запросы HTTP с данными
http.request.method == "POST" and http.file_data
```

---

<a id="-capture-filters"></a>
## 🎣 Фильтры захвата

<a id="bpf-syntax-berkeley-packet-filter"></a>
### Синтаксис BPF (Berkeley Packet Filter)

Фильтры захвата используют **синтаксис BPF**, который отличается от фильтров отображения.

<a id="basic-syntax"></a>
### Основной синтаксис

```bash
# Узел
host 192.168.1.1
src host 192.168.1.1
dst host 192.168.1.1

# Сеть
net 192.168.1.0/24
src net 10.0.0.0/8

# Порт
port 80
src port 443
dst port 22
portrange 1-1024

# Протокол
tcp
udp
icmp
arp
```

<a id="combined-filters"></a>
### Комбинированные фильтры

```bash
# Узел и порт
host 192.168.1.1 and port 80

# Определённый обмен
host 192.168.1.1 and host 192.168.1.2

# Исключить трафик
not port 22
not host 10.0.0.1

# Составной фильтр
tcp and (port 80 or port 443) and host 192.168.1.1
```

<a id="common-capture-filters"></a>
### Типичные фильтры захвата

| Назначение | Фильтр |
| --------- | -------- |
| Определённый узел | `host 192.168.1.1` |
| Веб-трафик | `port 80 or port 443` |
| Только DNS | `port 53` |
| Только SSH | `port 22` |
| Без широковещательного трафика | `not broadcast` |
| Определённая подсеть | `net 192.168.1.0/24` |

---

<a id="-protocol-analysis"></a>
## 🔬 Анализ протоколов

<a id="tcp-analysis"></a>
### Анализ TCP

```bash
# Флаги TCP
tcp.flags.syn == 1 and tcp.flags.ack == 0    # Только SYN
tcp.flags.syn == 1 and tcp.flags.ack == 1    # SYN-ACK
tcp.flags.fin == 1                            # FIN
tcp.flags.reset == 1                          # RST

# Проблемы TCP
tcp.analysis.retransmission
tcp.analysis.duplicate_ack
tcp.analysis.zero_window
tcp.analysis.lost_segment

# Поток TCP
tcp.stream == 0
```

<a id="http-analysis"></a>
### Анализ HTTP

```bash
# Запросы HTTP
http.request
http.request.method == "GET"
http.request.method == "POST"
http.request.uri contains "/login"

# Ответы HTTP
http.response
http.response.code == 200
http.response.code == 404
http.response.code >= 400    # Ошибки

# Содержимое HTTP
http.content_type contains "json"
http.content_type contains "html"
http.file_data contains "password"
```

<a id="dns-analysis"></a>
### Анализ DNS

```bash
# Запросы DNS
dns.flags.response == 0
dns.qry.name == "example.com"
dns.qry.type == 1      # Запись A
dns.qry.type == 28     # Запись AAAA
dns.qry.type == 5      # CNAME
dns.qry.type == 15     # MX

# Ответы DNS
dns.flags.response == 1
dns.flags.rcode != 0   # Ошибки
```

<a id="tlsssl-analysis"></a>
### Анализ TLS/SSL

```bash
# TLS-трафик
tls

# Рукопожатие TLS
tls.handshake
tls.handshake.type == 1     # Client Hello
tls.handshake.type == 2     # Server Hello

# Сертификаты
tls.handshake.certificate

# Версия TLS
tls.record.version == 0x0303  # TLS 1.2 (подпись оригинала; см. уточнение)
```

<a id="arp-analysis"></a>
### Анализ ARP

```bash
# Все ARP-пакеты
arp

# Запросы ARP
arp.opcode == 1

# Ответы ARP
arp.opcode == 2

# Gratuitous ARP (возможная подмена)
arp.isgratuitous == 1
```

---

<a id="-following-streams"></a>
## 🔗 Просмотр потоков

<a id="follow-tcp-stream"></a>
### Просмотр потока TCP

```
Right-click packet → Follow → TCP Stream

Или: Analyze → Follow → TCP Stream
```

<a id="follow-http-stream"></a>
### Просмотр потока HTTP

```
Right-click HTTP packet → Follow → HTTP Stream
```

<a id="follow-udp-stream"></a>
### Просмотр потока UDP

```
Right-click UDP packet → Follow → UDP Stream
```

<a id="stream-window"></a>
### Окно потока

- Показывает весь обмен
- Цвета разделяют клиента и сервер
- Сохранение как текста или исходных данных
- Кнопки фильтрации по направлению

---

<a id="-statistics--analysis"></a>
## 📊 Статистика и анализ

<a id="protocol-hierarchy"></a>
### Иерархия протоколов

```
Statistics → Protocol Hierarchy

Показывает распределение захваченного трафика по протоколам
```

<a id="conversations"></a>
### Обмены между узлами

```
Statistics → Conversations

Просмотр всех обменов по типам:
- Ethernet
- IPv4/IPv6
- TCP
- UDP
```

<a id="endpoints"></a>
### Конечные узлы

```
Statistics → Endpoints

Список всех уникальных конечных узлов
```

<a id="io-graph"></a>
### График ввода-вывода

```
Statistics → IO Graph

Отображение изменения трафика во времени
```

<a id="expert-information"></a>
### Экспертная информация

```
Analyze → Expert Information

Показывает предупреждения, ошибки и замечания:
- Повторные передачи TCP
- Некорректные пакеты
- Ошибки
```

<a id="http-statistics"></a>
### Статистика HTTP

```
Statistics → HTTP → Requests
Statistics → HTTP → Packet Counter
```

---

<a id="-tshark-cli"></a>
## 💻 tshark (командная строка)

<a id="basic-usage"></a>
### Основное использование

```bash
# Захватить пакеты
tshark -i eth0

# Прочитать файл захвата
tshark -r capture.pcap

# Записать в файл
tshark -i eth0 -w output.pcap
```

<a id="apply-filters"></a>
### Применение фильтров

```bash
# Фильтр захвата
tshark -i eth0 -f "port 80"

# Фильтр отображения
tshark -r capture.pcap -Y "http"
```

<a id="output-options"></a>
### Параметры вывода

```bash
# Вывести определённые поля
tshark -r capture.pcap -T fields -e ip.src -e ip.dst -e tcp.port

# Вывод JSON
tshark -r capture.pcap -T json

# Вывести статистику
tshark -r capture.pcap -q -z io,stat,1
```

<a id="common-tshark-examples"></a>
### Типичные примеры tshark

```bash
# Вывод HTTP-запросов при захвате в реальном времени
tshark -i eth0 -Y "http.request" -T fields -e http.host -e http.request.uri

# Извлечь запросы DNS
tshark -r capture.pcap -Y "dns.flags.response == 0" -T fields -e dns.qry.name

# Подсчитать пакеты по IP
tshark -r capture.pcap -q -z ip_hosts,tree

# Статистика HTTP
tshark -r capture.pcap -q -z http,tree
```

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-capture-http-traffic"></a>
### Пример 1: захват HTTP-трафика
```bash
# Фильтр захвата: port 80
# Фильтр отображения: http
# Просмотрите HTTP Stream для полных запросов и ответов
```

<a id="example-2-find-credentials"></a>
### Пример 2: поиск учётных данных
```bash
# Фильтр отображения
http.request.method == "POST" and http contains "password"

# Или для FTP
ftp contains "PASS"
```

<a id="example-3-detect-port-scan"></a>
### Пример 3: обнаружение сканирования портов
```bash
# Несколько SYN на разные порты
tcp.flags.syn == 1 and tcp.flags.ack == 0

# Найти много назначений от одного источника
Statistics → Conversations → TCP
```

<a id="example-4-dns-exfiltration"></a>
### Пример 4: вывод данных через DNS
```bash
# Большие запросы DNS
dns and frame.len > 512

# Необычные записи TXT
dns.qry.type == 16
```

<a id="example-5-extract-files"></a>
### Пример 5: извлечение файлов
```
File → Export Objects → HTTP

Выберите файлы для экспорта
```

<a id="example-6-analyze-tls-handshake"></a>
### Пример 6: анализ рукопожатия TLS
```bash
tls.handshake

# Посмотрите Client Hello для SNI
tls.handshake.type == 1
```

<a id="example-7-find-cleartext-passwords"></a>
### Пример 7: поиск паролей в открытом виде
```bash
http contains "password" or http contains "passwd" or ftp or telnet
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-display-filters"></a>
### Основные фильтры отображения

| Назначение | Фильтр |
| --------- | -------- |
| HTTP-трафик | `http` |
| DNS-трафик | `dns` |
| Определённый IP-адрес | `ip.addr == 192.168.1.1` |
| Определённый порт | `tcp.port == 80` |
| Проблемы TCP | `tcp.analysis.flags` |
| Ошибки HTTP | `http.response.code >= 400` |
| Содержит строку | `frame contains "text"` |
| Исключение протокола | `not arp` |

<a id="common-capture-filters-1"></a>
### Типичные фильтры захвата

| Назначение | Фильтр |
| --------- | -------- |
| Определённый узел | `host 192.168.1.1` |
| Веб-трафик | `port 80 or port 443` |
| Только DNS | `port 53` |
| Без широковещательного трафика | `not broadcast` |

---

<a id="-keyboard-shortcuts"></a>
## ⌨️ Сочетания клавиш

| Сочетание клавиш | Действие |
| ---------- | -------- |
| `Ctrl+E` | Начать или остановить захват |
| `Ctrl+K` | Параметры захвата |
| `Ctrl+O` | Открыть файл |
| `Ctrl+S` | Сохранить файл |
| `Ctrl+F` | Найти пакет |
| `Ctrl+G` | Перейти к пакету |
| `Ctrl+→` | Следующий пакет обмена |
| `Ctrl+←` | Предыдущий пакет обмена |
| `Ctrl+.` | Следующий отмеченный пакет |
| `Ctrl+M` | Установить или снять отметку пакета |
| `Ctrl+Shift+M` | Отметить все отображаемые пакеты |
| `Space` | Переключить дерево протоколов |
| `Tab` | Следующая панель |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендуемые практики

<a id="capture-tips"></a>
### Советы по захвату

1. **Используйте фильтры захвата**
   ```
   Уменьшайте размер файла, отбирая пакеты при захвате
   ```

2. **Кольцевой буфер для длительного захвата**
   ```
   Capture → Options → Output → Ring buffer
   ```

3. **Удалённый захват**
   ```bash
   ssh user@host tcpdump -w - | wireshark -k -i -
   ```

<a id="analysis-tips"></a>
### Советы по анализу

1. **Правила раскраски**
   ```
   View → Coloring Rules
   Настройте цвета протоколов
   ```

2. **Отображение времени**
   ```
   View → Time Display Format
   Попробуйте "Seconds Since Beginning of Capture"
   ```

3. **Разрешение имён**
   ```
   View → Name Resolution
   Включайте или отключайте по необходимости
   ```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовая оговорка

> **ПРЕДУПРЕЖДЕНИЕ:** Захват сетевых пакетов следует выполнять только **для разрешённых целей**.
> 
> - ✅ Выполняйте захват в собственных сетях
> - ✅ Тестируйте безопасность с разрешения
> - ✅ Диагностируйте системы в согласованных границах
> - ❌ Не захватывайте трафик в чужих сетях без разрешения
> - ❌ Захват чужого трафика может быть незаконным
> 
> **Несанкционированное наблюдение за сетью незаконно в большинстве юрисдикций.**

---

<a id="-resources"></a>
## 📚 Полезные материалы

<a id="official-resources"></a>
### Официальные материалы
- [Официальный сайт Wireshark](https://www.wireshark.org/)
- [Руководство пользователя Wireshark](https://www.wireshark.org/docs/wsug_html/)
- [Справочник фильтров отображения](https://www.wireshark.org/docs/dfref/)
- [Вики Wireshark](https://wiki.wireshark.org/)

<a id="learning-resources"></a>
### Учебные материалы
- [Учебное руководство Wireshark](https://www.wireshark.org/docs/wsug_html/)
- [Примеры файлов захвата](https://wiki.wireshark.org/SampleCaptures)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Nmap](../../Nmap/translations/README.ru.md)
- [Metasploit](../../Metasploit/translations/README.ru.md)
- [Hydra](../../Hydra/translations/README.ru.md)

---

<p align="center">
  <b>🦈 Освойте анализ сетевого трафика!</b><br>
  <i>Исследуйте всё, что передаётся по сети</i>
</p>
