<a id="-tcpdump---complete-cheatsheet"></a>
# 📡 tcpdump — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _             _                       
 | |_ ___ _ __ | |_ _ _ _____ _ __ _ __ 
 | __/ __| '_ \| __| | | '_ ` _ \| '_ \
 | || (__| |_) | |_| |_| | | | | | |_) |
  \__\___| .__/ \__|\__,_|_| |_| | .__/ 
         |_|                     |_|    
      Command-Line Packet Analyzer
```

<p align="center">
  <img src="https://img.shields.io/badge/tcpdump-Packet_Analyzer-blue?style=for-the-badge" alt="tcpdump">
  <img src="https://img.shields.io/badge/Command-Line-red?style=for-the-badge" alt="CLI">
  <img src="https://img.shields.io/badge/BPF-Filters-green?style=for-the-badge" alt="BPF">
  <img src="https://img.shields.io/badge/Cross-Platform-orange?style=for-the-badge" alt="Cross Platform">
</p>

<p align="center">
  <b>🔍 Классический анализатор пакетов командной строки</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Установка](#-installation)
- [Основной синтаксис](#-basic-syntax)
- [Основные параметры](#-common-options)
- [Фильтры BPF](#-bpf-filters)
- [Фильтры протоколов](#-protocol-filters)
- [Параметры вывода](#-output-options)
- [Практические примеры](#-real-world-examples)
- [Краткий справочник](#-quick-reference)
- [Советы и рекомендуемые практики](#-tips--best-practices)
- [Полезные материалы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

> **Уточнения к оригиналу:** [руководство tcpdump](https://github.com/the-tcpdump-group/tcpdump/blob/master/tcpdump.1.in) описывает -w как запись сырых пакетов в файл, поэтому исходный пример с -w capture.pcap | tee сам по себе не отображает описания пакетов. -Z снижает привилегии после открытия устройства захвата или входного файла, а не после окончания захвата. -s0 выбирает стандартный snaplen версии (в текущем руководстве 262144), а не бесконечный размер; -s96 не гарантирует «только заголовки». wc -l считает строки вывода и не является универсальным счётчиком пакетов. Исходные команды сохранены.

> **Фильтры и shell:** в примере входящего трафика $(hostname ...) находится в [одинарных кавычках Bash](https://www.gnu.org/software/bash/manual/html_node/Single-Quotes.html) и не подставляется оболочкой; пример не следует считать готовой рабочей командой. Портовые и побайтовые [фильтры libpcap](https://github.com/the-tcpdump-group/libpcap/blob/master/pcap-filter.manmisc.in) из HTTP/DNS-разделов предполагают конкретные протоколы и расположение заголовков; они не расшифровывают HTTPS. Подпись «HTTP requests» в первом примере слишком узкая: выражение проверяет TCP-полезную нагрузку на порту 80, а не только запросы. Совпадение SYN также само по себе не доказывает сканирование.

> **Подпись ASCII-эмблемы:** Command-Line Packet Analyzer — анализатор пакетов командной строки. Рисунок оставлен без изменений.

**tcpdump** — анализатор пакетов командной строки, доступный в большинстве Unix-подобных операционных систем. Он использует библиотеку libpcap и синтаксис Berkeley Packet Filter (BPF).

<a id="key-features"></a>
### Основные возможности

| Возможность | Описание |
| --------- | ------------- |
| **Небольшие требования к ресурсам** | Без графического интерфейса, минимум ресурсов |
| **Фильтры BPF** | Гибкая фильтрация пакетов |
| **Совместимость с pcap** | Чтение и запись файлов pcap |
| **Использование в скриптах** | Подходит для автоматизации |
| **Широкая доступность** | Доступен в Unix-системах |

<a id="tcpdump-vs-wireshark"></a>
### tcpdump и Wireshark

| Возможность | tcpdump | Wireshark |
| --------- | --------- | ----------- |
| **Интерфейс** | CLI | GUI |
| **Полезные материалы** | Минимальные | Более высокие |
| **Удалённая работа** | Через SSH | Требует настройки |
| **Использование в скриптах** | Простое | Более сложное |
| **Анализ** | Базовый | Расширенный |

---

<a id="-installation"></a>
## 📥 Установка

<a id="linux-pre-installed-on-most"></a>
### Linux (часто предустановлен)
```bash
# Kali/Debian/Ubuntu
sudo apt install tcpdump

# CentOS/RHEL
sudo yum install tcpdump

# Arch
sudo pacman -S tcpdump
```

<a id="macos-pre-installed"></a>
### macOS (предустановлен)
```bash
tcpdump --version
```

<a id="verify-installation"></a>
### Проверка установки
```bash
tcpdump --version
tcpdump -D              # Перечислить интерфейсы
```

---

<a id="-basic-syntax"></a>
## ⌨️ Основной синтаксис

<a id="general-syntax"></a>
### Общий синтаксис
```bash
tcpdump [options] [filter expression]
```

<a id="basic-commands"></a>
### Основные команды
```bash
# Захват на интерфейсе по умолчанию
sudo tcpdump

# Захват на выбранном интерфейсе
sudo tcpdump -i eth0

# Захват ограниченного числа пакетов
sudo tcpdump -c 100

# Захват с подробным выводом
sudo tcpdump -v
sudo tcpdump -vv
sudo tcpdump -vvv
```

---

<a id="-common-options"></a>
## ⚙️ Основные параметры

<a id="display-options"></a>
### Параметры отображения

| Параметр | Описание |
| -------- | ------------- |
| `-i` | Интерфейс для захвата |
| `-c` | Число захватываемых пакетов |
| `-n` | Не определять имена узлов |
| `-nn` | Не определять имена узлов и названия портов |
| `-v` | Подробный вывод |
| `-vv` | Более подробный вывод |
| `-vvv` | Максимальная подробность |
| `-q` | Сокращённый вывод |
| `-t` | Не выводить метку времени |
| `-tt` | Выводить время Unix |
| `-ttt` | Выводить разницу времени |
| `-tttt` | Выводить дату и время |

<a id="capture-options"></a>
### Параметры захвата

| Параметр | Описание |
| -------- | ------------- |
| `-w` | Записывать в файл pcap |
| `-r` | Читать из файла pcap |
| `-s` | Длина сохраняемой части пакета в байтах (snaplen) |
| `-s0` | Захватывать полные пакеты (в пределах snaplen; см. уточнение) |
| `-D` | Перечислить доступные интерфейсы |

<a id="output-options"></a>
### Параметры вывода

| Параметр | Описание |
| -------- | ------------- |
| `-A` | Выводить ASCII |
| `-X` | Выводить hex и ASCII |
| `-XX` | Выводить hex и ASCII с заголовками канального уровня |
| `-e` | Выводить заголовок канального уровня |
| `-l` | Построчная буферизация вывода |

<a id="common-combinations"></a>
### Типичные сочетания параметров
```bash
# Без разрешения имён, подробный вывод, полные пакеты
sudo tcpdump -nnvvs0 -i eth0

# Сохранить в файл
sudo tcpdump -i eth0 -w capture.pcap

# Чтение из файла
tcpdump -r capture.pcap

# Вывод ASCII
sudo tcpdump -A -i eth0

# Hex и ASCII
sudo tcpdump -X -i eth0
```

---

<a id="-bpf-filters"></a>
## 🎣 Фильтры BPF

<a id="host-filters"></a>
### Фильтры по узлам

```bash
# Трафик от выбранного узла и к нему
tcpdump host 192.168.1.1

# Только узел-источник
tcpdump src host 192.168.1.1

# Только узел назначения
tcpdump dst host 192.168.1.1

# Несколько узлов
tcpdump host 192.168.1.1 or host 192.168.1.2

# Исключить узел
tcpdump not host 192.168.1.1

# Сеть или подсеть
tcpdump net 192.168.1.0/24
tcpdump src net 10.0.0.0/8
```

<a id="port-filters"></a>
### Фильтры по портам

```bash
# Определённый порт
tcpdump port 80
tcpdump port 443

# Порт источника
tcpdump src port 443

# Порт назначения
tcpdump dst port 22

# Диапазон портов
tcpdump portrange 1-1024

# Несколько портов
tcpdump port 80 or port 443
tcpdump port 80 or port 443 or port 8080
```

<a id="protocol-filters"></a>
### Фильтры протоколов

```bash
# По протоколу
tcpdump tcp
tcpdump udp
tcpdump icmp
tcpdump arp

# Протокол и порт
tcpdump tcp port 80
tcpdump udp port 53
```

<a id="logical-operators"></a>
### Логические операторы

| Оператор | Альтернативная запись | Описание |
| ---------- | ------------- | ------------- |
| `and` | `&&` | Логическое И |
| `or` | `\|\|` | Логическое ИЛИ |
| `not` | `!` | Логическое НЕ |
| `()` |  | Группировка |

<a id="combined-filters"></a>
### Сочетания фильтров

```bash
# Узел И порт
tcpdump host 192.168.1.1 and port 80

# Узел И протокол
tcpdump host 192.168.1.1 and tcp

# Составной фильтр
tcpdump 'host 192.168.1.1 and (port 80 or port 443)'

# Исключить определённый трафик
tcpdump not port 22 and not port 53

# Трафик между двумя узлами
tcpdump host 192.168.1.1 and host 192.168.1.2
```

---

<a id="-protocol-filters"></a>
## 📡 Фильтры протоколов

<a id="tcp-flags"></a>
### Флаги TCP

```bash
# Пакеты с установленным SYN
tcpdump 'tcp[tcpflags] & tcp-syn != 0'
tcpdump 'tcp[13] & 2 != 0'

# Пакеты с установленными SYN и ACK
tcpdump 'tcp[tcpflags] & (tcp-syn|tcp-ack) == (tcp-syn|tcp-ack)'

# Пакеты с установленным FIN
tcpdump 'tcp[tcpflags] & tcp-fin != 0'

# Пакеты с установленным RST
tcpdump 'tcp[tcpflags] & tcp-rst != 0'

# Пакеты с установленным PSH
tcpdump 'tcp[tcpflags] & tcp-push != 0'

# Только флаг ACK
tcpdump 'tcp[tcpflags] == tcp-ack'
```

<a id="tcp-flag-reference"></a>
### Справочник флагов TCP

| Флаг | Hex | Десятичное значение | tcpdump |
| ------ | ----- | ----- | --------- |
| FIN | 0x01 | 1 | tcp-fin |
| SYN | 0x02 | 2 | tcp-syn |
| RST | 0x04 | 4 | tcp-rst |
| PSH | 0x08 | 8 | tcp-push |
| ACK | 0x10 | 16 | tcp-ack |
| URG | 0x20 | 32 | tcp-urg |

<a id="http-traffic"></a>
### Трафик HTTP

```bash
# HTTP-запросы (порт 80; см. уточнение)
tcpdump -A 'tcp port 80 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)'

# Запросы GET
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'

# Запросы POST
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x504f5354'

# Простой захват HTTP
tcpdump -A -s0 'tcp port 80'
```

<a id="dns-traffic"></a>
### Трафик DNS

```bash
# Весь трафик DNS
tcpdump port 53
tcpdump udp port 53

# Только DNS-запросы
tcpdump 'udp port 53 and udp[10] & 0x80 = 0'

# Только DNS-ответы
tcpdump 'udp port 53 and udp[10] & 0x80 = 0x80'
```

<a id="icmp-traffic"></a>
### Трафик ICMP

```bash
# Весь трафик ICMP
tcpdump icmp

# Эхо-запрос ICMP (ping)
tcpdump 'icmp[icmptype] == icmp-echo'

# Эхо-ответ ICMP
tcpdump 'icmp[icmptype] == icmp-echoreply'

# Сообщение ICMP о недоступности
tcpdump 'icmp[icmptype] == icmp-unreach'
```

<a id="arp-traffic"></a>
### Трафик ARP

```bash
# Весь трафик ARP
tcpdump arp

# Запросы ARP
tcpdump 'arp[6:2] == 1'

# Ответы ARP
tcpdump 'arp[6:2] == 2'
```

---

<a id="-output-options"></a>
## 💾 Параметры вывода

<a id="write-to-file"></a>
### Запись в файл

```bash
# Записывать в файл pcap
tcpdump -w capture.pcap

# Запись с ротацией (100 файлов, по 1 MB)
tcpdump -w capture.pcap -W 100 -C 1

# Запись с меткой времени в имени файла
tcpdump -w capture_%Y%m%d_%H%M%S.pcap -G 3600

# Захват и отображение (подпись оригинала; см. уточнение)
tcpdump -w capture.pcap | tee
```

<a id="read-from-file"></a>
### Чтение из файла

```bash
# Читать файл pcap
tcpdump -r capture.pcap

# Чтение с фильтрами
tcpdump -r capture.pcap 'host 192.168.1.1'

# Подсчёт пакетов (в оригинале через число строк; см. уточнение)
tcpdump -r capture.pcap | wc -l

# Чтение с подробным выводом
tcpdump -nnvvXSs0 -r capture.pcap
```

<a id="display-formats"></a>
### Форматы отображения

```bash
# Содержимое в ASCII
tcpdump -A -i eth0

# Hex и ASCII
tcpdump -X -i eth0

# Только hex
tcpdump -x -i eth0

# С заголовками Ethernet
tcpdump -XX -i eth0

# Выводить абсолютные номера последовательности
tcpdump -S -i eth0
```

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-capture-web-traffic"></a>
### Пример 1: захват веб-трафика
```bash
sudo tcpdump -i eth0 -nnvvXSs0 'tcp port 80 or tcp port 443'
```

<a id="example-2-capture-dns-queries"></a>
### Пример 2: захват DNS-запросов
```bash
sudo tcpdump -i eth0 -nn 'udp port 53'
```

<a id="example-3-capture-traffic-to-specific-host"></a>
### Пример 3: захват трафика выбранного узла
```bash
sudo tcpdump -i eth0 -nn host 192.168.1.100
```

<a id="example-4-find-ssh-connections"></a>
### Пример 4: поиск соединений SSH
```bash
sudo tcpdump -i eth0 'tcp port 22'
```

<a id="example-5-capture-icmp-ping"></a>
### Пример 5: захват ICMP (ping)
```bash
sudo tcpdump -i eth0 icmp
```

<a id="example-6-detect-port-scan-syn"></a>
### Пример 6: поиск признаков сканирования портов (SYN)
```bash
sudo tcpdump -i eth0 'tcp[tcpflags] == tcp-syn'
```

<a id="example-7-capture-only-incoming-traffic"></a>
### Пример 7: захват только входящего трафика
```bash
sudo tcpdump -i eth0 'dst host $(hostname -I | cut -d" " -f1)'
```

<a id="example-8-monitor-specific-conversation"></a>
### Пример 8: наблюдение за выбранным обменом
```bash
sudo tcpdump -i eth0 'host 192.168.1.1 and host 192.168.1.2'
```

<a id="example-9-capture-ftp-credentials"></a>
### Пример 9: захват учётных данных FTP
```bash
sudo tcpdump -A -i eth0 'tcp port 21'
```

<a id="example-10-save-for-wireshark-analysis"></a>
### Пример 10: сохранение для анализа в Wireshark
```bash
sudo tcpdump -i eth0 -s0 -w capture.pcap
# Затем открыть capture.pcap в Wireshark
```

<a id="example-11-remote-capture-via-ssh"></a>
### Пример 11: удалённый захват через SSH
```bash
ssh user@remote 'sudo tcpdump -w - -i eth0' | wireshark -k -i -
```

<a id="example-12-capture-excluding-ssh"></a>
### Пример 12: захват с исключением SSH
```bash
sudo tcpdump -i eth0 'not port 22'
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Задача | Команда |
| ------ | --------- |
| Перечислить интерфейсы | `tcpdump -D` |
| Базовый захват | `tcpdump -i eth0` |
| Без разрешения имён | `tcpdump -nn` |
| Захват N пакетов | `tcpdump -c 100` |
| Запись в файл | `tcpdump -w file.pcap` |
| Чтение из файла | `tcpdump -r file.pcap` |
| Подробный вывод | `tcpdump -v` |
| Вывод ASCII | `tcpdump -A` |
| Вывод hex | `tcpdump -X` |
| Полный пакет | `tcpdump -s0` |

<a id="common-filters"></a>
### Типичные фильтры

| Фильтр | Описание |
| -------- | ------------- |
| `host 1.2.3.4` | Трафик от узла и к нему |
| `port 80` | Трафик порта 80 |
| `tcp` | Только трафик TCP |
| `udp` | Только трафик UDP |
| `icmp` | Только трафик ICMP |
| `net 192.168.1.0/24` | Трафик от подсети и к ней |
| `src host 1.2.3.4` | Узел является источником |
| `dst port 443` | Порт назначения 443 |

<a id="filter-combinations"></a>
### Сочетания фильтров

| Пример | Описание |
| --------- | ------------- |
| `host A and port 80` | Узел A, порт 80 |
| `port 80 or port 443` | Порт 80 или 443 |
| `not port 22` | Исключить SSH |
| `tcp and port 80` | Порт TCP 80 |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендуемые практики

<a id="performance-tips"></a>
### Советы по производительности

```bash
# Не разрешать имена DNS (быстрее)
tcpdump -nn

# Ограничить сохраняемый размер пакета
tcpdump -s 96   # Только заголовки (приблизительная подпись; см. уточнение)

# Использовать фильтры BPF для снижения нагрузки
tcpdump 'host 192.168.1.1'  # Фильтрация на уровне ядра
```

<a id="security-tips"></a>
### Рекомендации по безопасности

```bash
# Запуск без root (при соответствующей настройке)
# Или ограничить время захвата необходимым минимумом

# Снизить привилегии после открытия устройства захвата (см. уточнение)
tcpdump -Z user
```

<a id="useful-aliases"></a>
### Полезные псевдонимы

```bash
# Добавить в ~/.bashrc
alias tcpdump='sudo tcpdump -nnvvXSs0'
alias tcpdump-web='sudo tcpdump -nnvvXSs0 port 80 or port 443'
alias tcpdump-dns='sudo tcpdump -nn port 53'
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовая оговорка

> **ПРЕДУПРЕЖДЕНИЕ:** Захват сетевых пакетов следует выполнять только **в разрешённых целях**.
> 
> - ✅ Захватывайте трафик собственных сетей
> - ✅ Проверяйте безопасность с разрешения
> - ✅ Диагностируйте системы в рамках разрешённых работ
> - ❌ Не захватывайте трафик сетей без разрешения
> - ❌ Захват чужого трафика может быть незаконным
> 
> **Несанкционированное наблюдение за сетевым трафиком незаконно.**

---

<a id="-resources"></a>
## 📚 Полезные материалы

<a id="official-resources"></a>
### Официальные материалы
- [Руководство tcpdump](https://www.tcpdump.org/manpages/tcpdump.1.html)
- [Официальный сайт tcpdump](https://www.tcpdump.org/)
- [Синтаксис фильтров BPF](https://biot.com/capstats/bpf.html)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Wireshark](../../Wireshark/translations/README.ru.md)
- [Nmap](../../Nmap/translations/README.ru.md)
- [Metasploit](../../Metasploit/translations/README.ru.md)

---

<p align="center">
  <b>📡 Освойте анализ пакетов в командной строке!</b><br>
  <i>Альтернатива Wireshark с небольшими требованиями к ресурсам</i>
</p>
