<a id="-volatility---memory-forensics-cheatsheet"></a>
# 🧠 Volatility — справочник по анализу оперативной памяти

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

> **О версиях.** Примеры Vol2 и Vol3 сохранены дословно. Поддержка формата зависит от загрузчика, ОС и доступных символов; отсутствие ручного профиля в Vol3 не означает универсального распознавания. Для Linux/Mac [таблица символов должна точно соответствовать образу](https://volatility3.readthedocs.io/en/latest/symbol-tables.html). Имена плагинов меняются: актуальная документация размещает [malfind в windows.malware](https://volatility3.readthedocs.io/en/latest/volatility3.plugins.windows.malware.malfind.html), а [hashdump — в windows.registry](https://volatility3.readthedocs.io/en/latest/volatility3.plugins.windows.registry.hashdump.html). Доступность linux.info/mac.info и остальных коротких имён проверяйте для своей версии. Надпись в логотипе означает «фреймворк анализа оперативной памяти».

> **Об интерпретации.** psscan может обнаруживать остатки завершённых процессов; malfind показывает потенциально внедрённый код, а не доказанное вредоносное ПО. Вывод нужно сопоставлять с другими артефактами. Сравнения скорости и форматов в таблице упрощены: [Vol2 поддерживает разные форматы вывода](https://github.com/volatilityfoundation/volatility/wiki/Volatility-Usage#choosing-an-output-format). hollowfind и mimikatz могут требовать сторонних плагинов; lsadump извлекает секреты LSA и не заменяет все функции Mimikatz.

> **О YARA.** В [исходном коде Vol2](https://github.com/volatilityfoundation/volatility/blob/master/volatility/plugins/malware/malfind.py) -Y задаёт правило или строку, а -y — файл правил. Подписи и аргументы двух примеров ниже перепутаны в оригинале; команды сохранены без исправления.


[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗   ██╗ ██████╗ ██╗      █████╗ ████████╗██╗██╗     ██╗████████╗██╗   ██╗
  ██║   ██║██╔═══██╗██║     ██╔══██╗╚══██╔══╝██║██║     ██║╚══██╔══╝╚██╗ ██╔╝
  ██║   ██║██║   ██║██║     ███████║   ██║   ██║██║     ██║   ██║    ╚████╔╝ 
  ╚██╗ ██╔╝██║   ██║██║     ██╔══██║   ██║   ██║██║     ██║   ██║     ╚██╔╝  
   ╚████╔╝ ╚██████╔╝███████╗██║  ██║   ██║   ██║███████╗██║   ██║      ██║   
    ╚═══╝   ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝   
                      Memory Forensics Framework
```

<p align="center">
  <img src="https://img.shields.io/badge/Volatility-Memory_Forensics-purple?style=for-the-badge" alt="Volatility">
  <img src="https://img.shields.io/badge/DFIR-blue?style=for-the-badge" alt="DFIR">
  <img src="https://img.shields.io/badge/Incident_Response-red?style=for-the-badge" alt="IR">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Volatility](#-what-is-volatility)
- [Установка](#-installation)
- [Основы использования](#-basic-usage)
- [Определение образа памяти](#-image-identification)
- [Анализ процессов](#-process-analysis)
- [Анализ сети](#-network-analysis)
- [Анализ реестра](#-registry-analysis)
- [Анализ вредоносного ПО](#-malware-analysis)
- [Анализ пользователей и учётных данных](#-user--credential-analysis)
- [Анализ файлов и DLL](#-file--dll-analysis)
- [Анализ временной шкалы](#-timeline-analysis)
- [Volatility 3](#-volatility-3)

---

<a id="-what-is-volatility"></a>
## 🎯 Что такое Volatility

**Volatility** — открытый фреймворк для криминалистического анализа дампов оперативной памяти (RAM). Он помогает решать следующие задачи:

- 🔍 **Реагирование на инциденты** — выявление активного вредоносного ПО
- 🦠 **Анализ вредоносного ПО** — извлечение скрытых процессов
- 🔐 **Восстановление учётных данных** — извлечение паролей и хешей
- 📊 **Анализ временной шкалы** — восстановление последовательности событий
- 🕵️ **Поиск угроз** — обнаружение индикаторов компрометации

<a id="supported-memory-formats"></a>
### Поддерживаемые форматы памяти

| Формат | Описание |
|--------|-------------|
| Raw (.raw, .mem) | Необработанный дамп памяти |
| EWF (.E01) | Формат EnCase |
| AFF | Формат Advanced Forensics Format |
| Аварийный дамп | Аварийные дампы Windows |
| VMware (.vmem) | Снимки VMware |
| VirtualBox (.sav) | Снимки VirtualBox |
| HPAK (.hpak) | Формат FDPro |
| LiME (.lime) | Средство извлечения памяти Linux (LiME) |

---

<a id="-installation"></a>
## 🚀 Установка

<a id="volatility-2-legacy"></a>
### Volatility 2 (устаревшая версия)

```bash
# Клонировать репозиторий
git clone https://github.com/volatilityfoundation/volatility.git
cd volatility

# Установить зависимости
pip install pycrypto distorm3 yara-python pillow openpyxl

# Запустить
python vol.py -h
```

<a id="volatility-3-current"></a>
### Volatility 3 (актуальная версия)

```bash
# Установить через pip
pip3 install volatility3

# Или клонировать репозиторий
git clone https://github.com/volatilityfoundation/volatility3.git
cd volatility3
pip3 install -r requirements.txt

# Запустить
python3 vol.py -h
vol -h  # При установке через pip
```

<a id="download-symbol-tables-vol3"></a>
### Загрузка таблиц символов (Vol3)

```bash
# Символы Windows
wget https://downloads.volatilityfoundation.org/volatility3/symbols/windows.zip

# Символы Linux
wget https://downloads.volatilityfoundation.org/volatility3/symbols/linux.zip

# Символы Mac
wget https://downloads.volatilityfoundation.org/volatility3/symbols/mac.zip

# Распаковать в volatility3/symbols/
```

---

<a id="-basic-usage"></a>
## 💻 Основы использования

<a id="volatility-2-syntax"></a>
### Синтаксис Volatility 2

```bash
# Общий синтаксис
python vol.py -f <memory_dump> --profile=<profile> <plugin>

# Пример
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
```

<a id="volatility-3-syntax"></a>
### Синтаксис Volatility 3

```bash
# Общий синтаксис (автоматическое определение, см. уточнение)
vol -f <memory_dump> <plugin>

# Пример
vol -f memory.raw windows.pslist
```

<a id="common-options"></a>
### Общие параметры

```bash
# Volatility 2
-f FILE          # Файл дампа памяти
--profile=PROFILE # Профиль ОС
-h               # Справка
--info           # Список доступных плагинов

# Volatility 3
-f FILE          # Файл дампа памяти
-o DIR           # Каталог вывода
-r RENDERER      # Формат вывода (pretty, json, csv)
-p PLUGIN_DIR    # Дополнительные плагины
-h               # Справка
```

---

<a id="-image-identification"></a>
## 🔍 Определение образа памяти

<a id="identify-profile-vol2"></a>
### Определение профиля (Vol2)

```bash
# Получить предполагаемые профили
python vol.py -f memory.raw imageinfo

# Более подробные сведения
python vol.py -f memory.raw kdbgscan
```

<a id="identify-os-vol3"></a>
### Определение ОС (Vol3)

```bash
# Автоматическое определение (Vol3 не требует ручного профиля)
vol -f memory.raw windows.info

# Linux
vol -f memory.lime linux.info

# Mac
vol -f memory.raw mac.info
```

<a id="common-profiles-vol2"></a>
### Распространённые профили (Vol2)

| ОС | Профиль |
|----|---------|
| Windows XP SP2 x86 | WinXPSP2x86 |
| Windows XP SP3 x86 | WinXPSP3x86 |
| Windows 7 SP1 x86 | Win7SP1x86 |
| Windows 7 SP1 x64 | Win7SP1x64 |
| Windows 10 x64 | Win10x64_* |
| Windows Server 2012 | Win2012R2x64 |

---

<a id="-process-analysis"></a>
## ⚙️ Анализ процессов

<a id="list-processes"></a>
### Список процессов

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
python vol.py -f memory.raw --profile=Win7SP1x64 pstree  # Древовидное представление
python vol.py -f memory.raw --profile=Win7SP1x64 psscan  # Скрытые процессы

# Volatility 3
vol -f memory.raw windows.pslist
vol -f memory.raw windows.pstree
vol -f memory.raw windows.psscan
```

<a id="process-details"></a>
### Сведения о процессе

```bash
# Аргументы командной строки
python vol.py -f memory.raw --profile=Win7SP1x64 cmdline
vol -f memory.raw windows.cmdline

# Переменные окружения
python vol.py -f memory.raw --profile=Win7SP1x64 envars

# Дескрипторы процесса
python vol.py -f memory.raw --profile=Win7SP1x64 handles -p <PID>
vol -f memory.raw windows.handles --pid <PID>

# Загруженные DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>
vol -f memory.raw windows.dlllist --pid <PID>
```

<a id="detect-hidden-processes"></a>
### Обнаружение скрытых процессов

```bash
# Сравнить pslist и psscan
python vol.py -f memory.raw --profile=Win7SP1x64 psxview

# Обнаружение подмены содержимого процесса (process hollowing)
python vol.py -f memory.raw --profile=Win7SP1x64 hollowfind
```

<a id="dump-process"></a>
### Выгрузка процесса

```bash
# Выгрузить исполняемый файл
python vol.py -f memory.raw --profile=Win7SP1x64 procdump -p <PID> -D output/
vol -f memory.raw windows.pslist --pid <PID> --dump

# Выгрузить память
python vol.py -f memory.raw --profile=Win7SP1x64 memdump -p <PID> -D output/
vol -f memory.raw windows.memmap --pid <PID> --dump
```

---

<a id="-network-analysis"></a>
## 🌐 Анализ сети

<a id="network-connections"></a>
### Сетевые соединения

```bash
# Активные соединения (Vol2)
python vol.py -f memory.raw --profile=Win7SP1x64 netscan    # Vista+
python vol.py -f memory.raw --profile=WinXPSP3x86 connections # XP
python vol.py -f memory.raw --profile=WinXPSP3x86 connscan    # XP

# Vol3
vol -f memory.raw windows.netscan
vol -f memory.raw windows.netstat
```

<a id="output-fields"></a>
### Поля вывода

| Поле | Описание |
|-------|-------------|
| Offset | Смещение в памяти |
| Proto | TCP/UDP |
| LocalAddr | Локальный IP:порт |
| ForeignAddr | Удалённый IP:порт |
| State | Состояние соединения |
| PID | Идентификатор процесса |
| Owner | Имя процесса |

<a id="network-artifacts"></a>
### Сетевые артефакты

```bash
# Сокеты
python vol.py -f memory.raw --profile=WinXPSP3x86 sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockscan
```

---

<a id="-registry-analysis"></a>
## 📝 Анализ реестра

<a id="list-registry-hives"></a>
### Список кустов реестра

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 hivelist

# Vol3
vol -f memory.raw windows.registry.hivelist
```

<a id="dump-registry"></a>
### Выгрузка реестра

```bash
# Вывести определённый ключ
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
vol -f memory.raw windows.registry.printkey --key "Software\Microsoft\Windows\CurrentVersion\Run"

# Выгрузить куст в файл
python vol.py -f memory.raw --profile=Win7SP1x64 dumpregistry -D output/
```

<a id="important-registry-keys"></a>
### Важные ключи реестра

```bash
# Ключи автозапуска
printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
printkey -K "Software\Microsoft\Windows\CurrentVersion\RunOnce"

# Службы
printkey -K "SYSTEM\CurrentControlSet\Services"

# Учётные записи пользователей
printkey -K "SAM\Domains\Account\Users"

# Недавние файлы
printkey -K "Software\Microsoft\Windows\CurrentVersion\Explorer\RecentDocs"
```

<a id="user-assist-executed-programs"></a>
### User Assist (запускавшиеся программы)

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 userassist

# Vol3
vol -f memory.raw windows.registry.userassist
```

<a id="shellbags"></a>
### Shellbags

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 shellbags
```

---

<a id="-malware-analysis"></a>
## 🦠 Анализ вредоносного ПО

<a id="malfind-injected-code"></a>
### Malfind (внедрённый код)

```bash
# Найти внедрённый код
python vol.py -f memory.raw --profile=Win7SP1x64 malfind
vol -f memory.raw windows.malfind

# Выгрузить в файлы
python vol.py -f memory.raw --profile=Win7SP1x64 malfind -D output/
```

<a id="ssdt-hooks"></a>
### Перехваты SSDT

```bash
# Таблица дескрипторов системных служб
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt

# Сравнить с чистой системой
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt | grep -v "ntoskrnl\|win32k"
```

<a id="idtgdt-analysis"></a>
### Анализ IDT/GDT

```bash
# Таблица дескрипторов прерываний
python vol.py -f memory.raw --profile=Win7SP1x64 idt

# Глобальная таблица дескрипторов
python vol.py -f memory.raw --profile=Win7SP1x64 gdt
```

<a id="driver-analysis"></a>
### Анализ драйверов

```bash
# Список драйверов
python vol.py -f memory.raw --profile=Win7SP1x64 driverscan
vol -f memory.raw windows.driverscan

# Список модулей
python vol.py -f memory.raw --profile=Win7SP1x64 modules
vol -f memory.raw windows.modules

# Выгруженные модули
python vol.py -f memory.raw --profile=Win7SP1x64 unloadedmodules

# Перехваты IRP драйверов
python vol.py -f memory.raw --profile=Win7SP1x64 driverirp
```

<a id="yara-scanning"></a>
### Поиск по правилам YARA

```bash
# Поиск по правилам YARA
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -Y "rule_file.yar"
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -y "string_to_find"

# Vol3
vol -f memory.raw yarascan.YaraScan --yara-file rules.yar
```

<a id="api-hooks"></a>
### Перехваты API

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 apihooks
```

---

<a id="-user--credential-analysis"></a>
## 🔐 Анализ пользователей и учётных данных

<a id="user-information"></a>
### Сведения о пользователях

```bash
# Получить SID пользователей
python vol.py -f memory.raw --profile=Win7SP1x64 getsids

# Последний вошедший пользователь
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\LogonUI"
```

<a id="password-hashes"></a>
### Хеши паролей

```bash
# Выгрузить хеши SAM
python vol.py -f memory.raw --profile=Win7SP1x64 hashdump
vol -f memory.raw windows.hashdump

# Секреты LSA
python vol.py -f memory.raw --profile=Win7SP1x64 lsadump

# Кешированные учётные данные
python vol.py -f memory.raw --profile=Win7SP1x64 cachedump
```

<a id="mimikatz-integration"></a>
### Интеграция с Mimikatz

```bash
# Извлечь учётные данные (плагин mimikatz)
python vol.py -f memory.raw --profile=Win7SP1x64 mimikatz

# Vol3 (встроенный плагин)
vol -f memory.raw windows.lsadump
```

<a id="clipboard"></a>
### Буфер обмена

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 clipboard
```

---

<a id="-file--dll-analysis"></a>
## 📁 Анализ файлов и DLL

<a id="file-scan"></a>
### Поиск файлов

```bash
# Найти файлы в памяти
python vol.py -f memory.raw --profile=Win7SP1x64 filescan
vol -f memory.raw windows.filescan

# Отфильтровать нужные файлы
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "\.exe$"
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "password"
```

<a id="dump-files"></a>
### Выгрузка файлов

```bash
# Выгрузить файл по смещению
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -Q <OFFSET> -D output/
vol -f memory.raw windows.dumpfiles --virtaddr <OFFSET>

# Выгрузить все файлы
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -D output/
```

<a id="dll-analysis"></a>
### Анализ DLL

```bash
# Список DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist
vol -f memory.raw windows.dlllist

# Определённый процесс
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>

# Выгрузить DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlldump -p <PID> -D output/
```

<a id="memory-strings"></a>
### Строки в памяти

```bash
# Извлечь строки
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt

# Строки определённого процесса
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt -p <PID>
```

---

<a id="-timeline-analysis"></a>
## 📅 Анализ временной шкалы

<a id="timeline-creation"></a>
### Построение временной шкалы

```bash
# Создать временную шкалу
python vol.py -f memory.raw --profile=Win7SP1x64 timeliner --output-file timeline.body

# Vol3
vol -f memory.raw timeliner.Timeliner
```

<a id="mft-analysis"></a>
### Анализ MFT

```bash
# Главная файловая таблица
python vol.py -f memory.raw --profile=Win7SP1x64 mftparser
vol -f memory.raw windows.mftscan
```

---

<a id="-volatility-3"></a>
## 🔄 Volatility 3

<a id="key-differences"></a>
### Основные различия

| Возможность | Vol2 | Vol3 |
|---------|------|------|
| Профиль | Вручную (`--profile=`) | Автоматическое определение |
| Именование плагинов | `pslist` | `windows.pslist` |
| Производительность | Ниже | Выше |
| Python | Python 2.7 | Python 3 |
| Вывод | Только текст (утверждение оригинала; см. уточнение) | JSON, CSV и другие форматы |

<a id="vol3-plugin-reference"></a>
### Справочник плагинов Vol3

```bash
# Процессы
vol -f dump.raw windows.pslist
vol -f dump.raw windows.pstree
vol -f dump.raw windows.psscan
vol -f dump.raw windows.cmdline
vol -f dump.raw windows.dlllist
vol -f dump.raw windows.handles

# Сеть
vol -f dump.raw windows.netscan
vol -f dump.raw windows.netstat

# Реестр
vol -f dump.raw windows.registry.hivelist
vol -f dump.raw windows.registry.printkey
vol -f dump.raw windows.registry.userassist

# Вредоносное ПО
vol -f dump.raw windows.malfind
vol -f dump.raw windows.driverscan

# Учётные данные
vol -f dump.raw windows.hashdump
vol -f dump.raw windows.lsadump
vol -f dump.raw windows.cachedump

# Файлы
vol -f dump.raw windows.filescan
vol -f dump.raw windows.dumpfiles
```

<a id="linux-plugins-vol3"></a>
### Плагины Linux (Vol3)

```bash
vol -f dump.lime linux.pslist
vol -f dump.lime linux.bash
vol -f dump.lime linux.check_syscall
vol -f dump.lime linux.lsmod
vol -f dump.lime linux.lsof
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="most-used-commands"></a>
### Часто используемые команды

| Задача | Команда Vol2 | Команда Vol3 |
|---------|--------------|--------------|
| Определение образа памяти | `imageinfo` | `windows.info` |
| Список процессов | `pslist` | `windows.pslist` |
| Дерево процессов | `pstree` | `windows.pstree` |
| Скрытые процессы | `psscan` | `windows.psscan` |
| Сеть | `netscan` | `windows.netscan` |
| DLLs | `dlllist` | `windows.dlllist` |
| Выгрузка процесса | `procdump` | `windows.pslist --dump` |
| Поиск вредоносного ПО | `malfind` | `windows.malfind` |
| Реестр | `hivelist` | `windows.registry.hivelist` |
| Хеши | `hashdump` | `windows.hashdump` |
| Файлы | `filescan` | `windows.filescan` |
| Строки | `strings` | - |

<a id="investigation-workflow"></a>
### Порядок исследования

```
1. imageinfo/windows.info    → Определить ОС
2. pslist/pstree/psscan      → Список процессов
3. cmdline                    → Проверить командные строки
4. netscan                    → Сетевые соединения
5. malfind                    → Найти внедрённый код
6. dlllist                    → Проверить загруженные DLL
7. filescan                   → Найти интересующие файлы
8. hashdump                   → Извлечь учётные данные
9. timeliner                  → Создать временную шкалу
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Volatility Foundation](https://www.volatilityfoundation.org/)
- [Volatility3 на GitHub](https://github.com/volatilityfoundation/volatility3)
- [Wiki Volatility](https://github.com/volatilityfoundation/volatility/wiki)
- [Справочник по анализу памяти](https://digital-forensics.sans.org/media/memory-forensics-cheat-sheet.pdf)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [Mimikatz](../../Mimikatz/translations/README.ru.md)
- [Команды Linux](../../Linux-Commands/translations/README.ru.md)
- [Повышение привилегий Windows](../../Windows-PrivEsc/translations/README.ru.md)

---

<p align="center">
  <b>🧠 Освойте анализ оперативной памяти!</b><br>
  <i>Извлекайте факты из оперативной памяти и проверяйте их контекст</i>
</p>
