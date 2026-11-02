<a id="-autopsy---digital-forensics-platform-cheatsheet"></a>
# 🔍 Autopsy — справочник по платформе цифровой криминалистики

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗   ██╗████████╗ ██████╗ ██████╗ ███████╗██╗   ██╗
  ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝╚██╗ ██╔╝
  ███████║██║   ██║   ██║   ██║   ██║██████╔╝███████╗ ╚████╔╝ 
  ██╔══██║██║   ██║   ██║   ██║   ██║██╔═══╝ ╚════██║  ╚██╔╝  
  ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║     ███████║   ██║   
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚══════╝   ╚═╝   
              Digital Forensics Platform
```

<p align="center">
  <img src="https://img.shields.io/badge/Autopsy-Digital_Forensics-blue?style=for-the-badge" alt="Autopsy">
  <img src="https://img.shields.io/badge/Sleuth_Kit-green?style=for-the-badge" alt="TSK">
  <img src="https://img.shields.io/badge/DFIR-red?style=for-the-badge" alt="DFIR">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Autopsy](#-what-is-autopsy)
- [Установка](#-installation)
- [Создание дела](#-creating-a-case)
- [Добавление источников данных](#-adding-data-sources)
- [Модули обработки данных (Ingest Modules)](#-ingest-modules)
- [Возможности анализа](#-analysis-features)
- [Анализ файлов](#-file-analysis)
- [Анализ временной шкалы](#-timeline-analysis)
- [Поиск по ключевым словам](#-keyword-search)
- [Подготовка отчётов](#-reporting)
- [Инструменты командной строки (Sleuth Kit)](#-cli-tools-sleuth-kit)

---

<a id="-what-is-autopsy"></a>
## 🎯 Что такое Autopsy

> **О версиях.** Оригинал объединяет интерфейсы разных выпусков. [Пакет Autopsy в Kali](https://www.kali.org/tools/autopsy/) — прежний Forensic Browser 2.x; установка через apt не означает установку современной платформы 4.x. В [руководстве 4.22](https://sleuthkit.org/autopsy/docs/user-docs/4.22.0/quick_start_guide.html) создание дела доступно через Case или Create New Case. Имена меню, модулей, сочетания клавиш и расположение данных зависят от версии; исходные английские подписи сохранены рядом с русскими пояснениями. Надпись в логотипе означает «платформа цифровой криминалистики».


**Autopsy** — бесплатная открытая платформа цифровой криминалистики на базе The Sleuth Kit (TSK). Её возможности:

- 🖥️ **Графический интерфейс (GUI)** — удобный криминалистический анализ
- 💾 **Анализ дисков** — исследование жёстких дисков, USB-носителей и образов
- 📁 **Восстановление файлов** — восстановление удалённых файлов
- 📊 **Анализ временной шкалы** — восстановление действий пользователя
- 🔍 **Поиск по ключевым словам** — поиск по данным
- 📧 **Анализ почты** — разбор почтовых баз
- 🌐 **Веб-артефакты** — история браузера и закладки
- 📱 **Мобильная криминалистика** — анализ данных Android/iOS

<a id="supported-evidence-types"></a>
### Поддерживаемые типы цифровых доказательств

| Тип | Форматы |
| ------ | --------- |
| **Disk Images (образы дисков)** | E01, AFF, Raw (dd), VHD, VMDK |
| **Local Disk (локальный диск)** | Физические/логические диски |
| **Файлы виртуальных машин** | VMDK, VHD, VHDX |
| **Logical Files (логические файлы)** | Каталоги, файлы |
| **Unallocated Space (нераспределённое пространство)** | Данные, извлечённые методом carving |

---

<a id="-installation"></a>
## 🚀 Установка

<a id="windows"></a>
### Windows

```bash
# Скачать с официального сайта
https://www.autopsy.com/download/

# Запустить установщик
autopsy-x.x.x-64bit.msi

# Расположение по умолчанию
C:\Program Files\Autopsy-x.x.x\
```

<a id="linux-kali"></a>
### Linux (Kali)

```bash
# Установка через apt
sudo apt update
sudo apt install autopsy

# Запуск
autopsy
```

<a id="requirements"></a>
### Требования

| Компонент | Минимум | Рекомендуется |
| ----------- | --------- | ------------- |
| **Оперативная память** | 8 GB | 16+ GB |
| **Процессор** | Многоядерный | 4+ ядра |
| **Накопитель** | SSD | NVMe SSD |
| **ОС** | Windows 10/11, Linux | Windows 10/11 |

---

<a id="-creating-a-case"></a>
## 📁 Создание дела

<a id="new-case-wizard"></a>
### Мастер создания дела

1. Выберите **File → New Case** (создать дело; см. примечание о версиях).
2. Укажите **Case Name** — имя дела, например "Investigation_2024".
3. Укажите **Base Directory** — каталог хранения файлов дела.
4. Выберите **Case Type** — тип дела:
   - Single-user — локальное дело одного пользователя
   - Multi-user — совместная работа нескольких пользователей
5. При необходимости укажите **Case Number** — номер дела.
6. Заполните **Examiner** — сведения об эксперте.
7. Нажмите **Finish** (завершить).

<a id="case-structure"></a>
### Структура дела

```
CaseName/
├── autopsy.db           # База дела (SQLite)
├── Reports/             # Созданные отчёты
├── Export/              # Экспортированные файлы
├── ModuleOutput/        # Результаты модулей
└── [HostName]/          # Данные отдельного хоста
    ├── [DataSource]/    # Файлы доказательств
    └── Reports/
```

<a id="case-properties"></a>
### Свойства дела

| Поле | Описание |
| ------- | ------------- |
| Case Name (имя дела) | Идентификатор исследования |
| Case Number (номер дела) | Регистрационный номер |
| Examiner (эксперт) | Имя специалиста |
| Base Directory (основной каталог) | Расположение файлов дела |
| Case Type (тип дела) | Один/несколько пользователей |

---

<a id="-adding-data-sources"></a>
## 📀 Добавление источников данных

> **Уточнение.** Поддержка образов и мобильных артефактов зависит от сборки, библиотек и модулей, а таблицы оригинала не гарантируют поддержку каждого варианта. По [официальному руководству](https://sleuthkit.org/autopsy/docs/user-docs/4.22.0/quick_start_guide.html), при работе с Local Disk содержимое файлов может отражать последующие изменения диска. Unallocated space — нераспределённое пространство; его не следует считать просто свободным местом файловой системы. Возможность восстановления зависит от сохранившихся данных.


<a id="data-source-types"></a>
### Типы источников данных

| Тип | Описание |
| ------ | ------------- |
| **Disk Image or VM File (образ диска или файл ВМ)** | E01, Raw, VMDK, VHD |
| **Local Disk (локальный диск)** | Анализ физического диска |
| **Logical Files (логические файлы)** | Импорт каталога или файлов |
| **Unallocated Space (нераспределённое пространство)** | Необработанный образ свободного пространства (формулировка оригинала; см. уточнение) |

<a id="adding-disk-image"></a>
### Добавление образа диска

1. Нажмите **Add Data Source** (добавить источник данных) на панели инструментов.
2. Выберите **Disk Image or VM File** — образ диска или файл виртуальной машины.
3. Укажите файл образа.
4. Выберите **Time Zone** — часовой пояс.
5. Настройте **Ingest Modules** — модули обработки данных (следующий раздел).
6. Нажмите **Finish** (завершить).

<a id="adding-local-disk"></a>
### Добавление локального диска

```
⚠️ ВНИМАНИЕ: используйте блокиратор записи (write-blocker) или режим только для чтения!
```

1. Нажмите **Add Data Source** (добавить источник данных).
2. Выберите **Local Disk** — локальный диск.
3. Выберите диск из списка.
4. Настройте модули обработки данных.
5. Нажмите **Finish** (завершить).

<a id="supported-image-formats"></a>
### Поддерживаемые форматы образов

| Формат | Расширение | Описание |
| -------- | ----------- | ------------- |
| Raw | .raw, .dd, .img | Побитовая копия |
| EnCase | .E01, .Ex01 | Формат Expert Witness |
| AFF | .aff | Формат Advanced Forensic Format |
| VMDK | .vmdk | Диск VMware |
| VHD/VHDX | .vhd, .vhdx | Диск Hyper-V |

---

<a id="-ingest-modules"></a>
## ⚙️ Модули обработки данных (Ingest Modules)

<a id="what-are-ingest-modules"></a>
### Что такое модули обработки данных?

Это модули автоматизированного анализа, которые обрабатывают источники данных и извлекают артефакты.

<a id="core-modules"></a>
### Основные модули

| Модуль | Описание |
| -------- | ------------- |
| **Recent Activity (недавние действия)** | История браузера, загрузки, cookies |
| **Hash Lookup (поиск по хешам)** | Поиск MD5/SHA1 в известных базах |
| **File Type Identification (определение типа файла)** | Определение типов файлов по сигнатуре |
| **Keyword Search (поиск по ключевым словам)** | Индексация содержимого и поиск |
| **Email Parser (разбор почты)** | Разбор PST, MBOX, EML |
| **Extension Mismatch (несоответствие расширения)** | Выявление несоответствий расширения типу файла |
| **Embedded File Extractor (извлечение вложенных файлов)** | Извлечение файлов из архивов и документов |
| **EXIF Parser (разбор EXIF)** | Извлечение метаданных изображений |
| **Encryption Detection (поиск шифрования)** | Поиск зашифрованных файлов |
| **Interesting Files (интересующие файлы)** | Поиск файлов, заслуживающих внимания |
| **PhotoRec Carver (восстановление по сигнатурам)** | Извлечение файлов методом carving |
| **Virtual Machine Extractor (извлечение ВМ)** | Извлечение файлов виртуальных машин |

<a id="hash-databases"></a>
### Базы хешей

```
# Добавить базы известных хешей
Tools → Options → Hash Database (настройка баз хешей)

# Типы:
- Known (NSRL) — пропускать известные файлы из справочника
- Known Bad — сообщать о совпадениях с хешами вредоносных файлов
```

<a id="nsrl-national-software-reference-library"></a>
### NSRL (National Software Reference Library)

```bash
# Скачать NSRL
https://www.nist.gov/itl/ssd/software-quality-group/national-software-reference-library-nsrl

# Импортировать в Autopsy
Tools → Options → Hash Database → Import Database
```

<a id="module-configuration"></a>
### Настройка модулей

```
# Включить или отключить модули для дела
Щёлкнуть источник данных правой кнопкой мыши → Run Ingest Modules (запустить обработку)

# Настроить параметры модуля
Нажать значок шестерёнки рядом с названием модуля
```

---

<a id="-analysis-features"></a>
## 🔬 Возможности анализа

<a id="tree-view-left-panel"></a>
### Дерево объектов (левая панель)

```
Data Sources (источники данных)
├── [Image Name] (имя образа)
│   ├── Volume 1 (том)
│   │   ├── [Folders] (каталоги)
│   │   ├── $OrphanFiles
│   │   └── $Unalloc
│   └── Volume 2 (том)
│
Views (представления)
├── File Types (типы файлов)
│   ├── By Extension (по расширению)
│   └── By MIME Type (по типу MIME)
├── Deleted Files (удалённые файлы)
├── File Size (размер файла)
└── ...

Results (результаты)
├── Extracted Content (извлечённое содержимое)
│   ├── Web History (история браузера)
│   ├── Web Downloads (загрузки браузера)
│   ├── Web Cookies (cookies браузера)
│   ├── Recent Documents (недавние документы)
│   └── ...
├── Keyword Hits (совпадения ключевых слов)
├── HashSet Hits (совпадения хешей)
├── E-mail Messages (электронные письма)
├── Interesting Items (интересующие объекты)
├── Accounts (учётные записи)
└── ...

Tags (метки)
├── Follow Up (проверить позже)
├── Notable Item (объект, заслуживающий внимания)
└── [Custom Tags] (пользовательские метки)
```

<a id="quick-analysis-areas"></a>
### Основные области анализа

| Категория | Что искать |
| ---------- | ----------------- |
| **Deleted Files (удалённые файлы)** | Восстановленные файлы и следы удаления пользователем |
| **Web History (история браузера)** | Посещённые сайты и поисковые запросы |
| **Downloads (загрузки)** | Загруженные файлы |
| **Recent Documents (недавние документы)** | Недавно открывавшиеся файлы |
| **USB Devices (устройства USB)** | Подключавшиеся устройства |
| **Электронная почта** | Переписка |
| **Изображения** | Фотографии и снимки экрана |
| **Архивы** | Содержимое ZIP и RAR |

---

<a id="-file-analysis"></a>
## 📄 Анализ файлов

<a id="viewing-files"></a>
### Просмотр файлов

```
# Content Viewer — просмотр содержимого (нижняя панель)
- Hex        : просмотр необработанных шестнадцатеричных данных
- Text       : текстовое содержимое
- Application: представление в формате приложения
- Strings    : извлечённые строки
- Metadata   : метаданные файла
- Results    : результаты анализа
- Indexed Text: индексированный текст для поиска
```

<a id="file-properties"></a>
### Свойства файла

| Свойство | Описание |
| ---------- | ------------- |
| Name (имя) | Имя файла |
| Location (расположение) | Полный путь |
| Size (размер) | Размер файла |
| Created (создан) | Время создания |
| Modified (изменён) | Время последнего изменения |
| Accessed (доступ) | Время последнего доступа |
| MD5/SHA1/SHA256 | Значения хешей |
| Known (известный) | Статус NSRL |
| MIME Type (тип MIME) | Определённый тип |

<a id="extracting-files"></a>
### Извлечение файлов

```
# Извлечь отдельный файл
Щёлкнуть правой кнопкой мыши → Extract File(s) (извлечь файлы)

# Извлечь несколько файлов
Выделить файлы → щёлкнуть правой кнопкой мыши → Extract (извлечь)

# Извлечь в указанное расположение
Actions → Extract to... (извлечь в указанное расположение)
```

<a id="deleted-file-recovery"></a>
### Восстановление удалённых файлов

```
Views → Deleted Files

# Отображаются:
- Файлы в $Recycle.Bin
- Файлы, отмеченные как удалённые в MFT
- Файлы, восстановленные по сигнатурам (PhotoRec)
```

---

<a id="-timeline-analysis"></a>
## 📊 Анализ временной шкалы

<a id="timeline-view"></a>
### Представление временной шкалы

```
# Открыть Timeline (временную шкалу)
Tools → Timeline

# Или
Results → Timeline
```

<a id="timeline-features"></a>
### Возможности временной шкалы

| Возможность | Описание |
| --------- | ------------- |
| **Counts View (количество событий)** | Частота событий во времени |
| **Details View (подробности)** | Отдельные события |
| **List View (список)** | Список событий в таблице |
| **Filters (фильтры)** | Отбор по типу, дате и другим условиям |

<a id="event-types"></a>
### Типы событий

| Тип | Цвет | Описание |
| ------ | ------- | ------------- |
| File Modified (изменение содержимого) | Blue (синий) | Изменено содержимое файла |
| File Accessed (доступ к файлу) | Green (зелёный) | Файл открыт |
| File Created (создание файла) | Yellow (жёлтый) | Файл создан |
| File Changed (изменение метаданных) | Red (красный) | Изменены метаданные |
| Web Activity (веб-действия) | Purple (фиолетовый) | События браузера |

<a id="timeline-filters"></a>
### Фильтры временной шкалы

```
# Отбор по диапазону дат
Start Date: 2024-01-01 (начальная дата)
End Date: 2024-12-31 (конечная дата)

# Отбор по типу
☑ File System (файловая система)
☑ Web Activity (веб-действия)
☐ Registry (реестр, если разобран)

# Отбор по источнику данных
☑ Disk Image 1 (образ диска)
☐ Disk Image 2 (образ диска)
```

<a id="timeline-export"></a>
### Экспорт временной шкалы

```
# Экспортировать временную шкалу
File → Export → Timeline (CSV/TSV)

# Использовать во внешних инструментах
- Excel
- plaso/log2timeline
- timesketch
```

---

<a id="-keyword-search"></a>
## 🔍 Поиск по ключевым словам

<a id="creating-keyword-lists"></a>
### Создание списков ключевых слов

```
# Tools → Options → Keyword Search

# Добавить новый список
1. Нажать "New List" (новый список)
2. Указать имя списка
3. Добавить ключевые слова:
   - Literal — точное совпадение
   - Regex — регулярное выражение
   - Substring — подстрока
```

<a id="common-search-keywords"></a>
### Примеры ключевых слов для поиска

```
# Финансовые понятия
credit card
bank account
swift
iban
bitcoin
wallet

# Учётные данные
password
login
username
secret
key

# Конфиденциальные сведения
confidential
secret
classified
internal only

# Сетевые данные
ip address: \b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b
email: [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}
```

<a id="regex-examples"></a>
### Примеры регулярных выражений

| Назначение | Регулярное выражение |
| --------- | ------- |
| Электронная почта | `[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}` |
| IP-адрес | `\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b` |
| Телефон (США) | `\b\d{3}[-.]?\d{3}[-.]?\d{4}\b` |
| Платёжная карта | `\b\d{4}[- ]?\d{4}[- ]?\d{4}[- ]?\d{4}\b` |
| SSN (номер социального страхования США) | `\b\d{3}-\d{2}-\d{4}\b` |
| URL | `https?://[^\s]+` |

<a id="running-search"></a>
### Запуск поиска

```
# Разовый поиск
Keyword Search (вверху справа) → ввести искомый текст → Search (поиск)

# Поиск с использованием списков слов
Щёлкнуть источник данных правой кнопкой мыши → Run Ingest Modules → Keyword Search (поиск по спискам)
```

---

<a id="-reporting"></a>
## 📝 Подготовка отчётов

<a id="generate-report"></a>
### Создание отчёта

```
# Создать отчёт (Generate Report)
Tools → Generate Report

# Или
Щёлкнуть дело правой кнопкой мыши → Generate Report (создать отчёт)
```

<a id="report-types"></a>
### Типы отчётов

| Тип | Описание |
| ------ | ------------- |
| **HTML Report (отчёт HTML)** | Веб-отчёт с изображениями |
| **Excel Report (отчёт Excel)** | Электронная таблица |
| **Text Report (текстовый отчёт)** | Обычный текст |
| **TSK Body File** | Файл исходных событий для временной шкалы |
| **Files - FS** | Отчёт по файловой системе |
| **Portable Case (переносимое дело)** | Подмножество данных для передачи |

<a id="html-report-options"></a>
### Параметры отчёта HTML

```
# Выбрать данные для включения
☑ Tagged Results (помеченные результаты)
☑ Interesting Items (интересующие объекты)
☑ Hashset Hits (совпадения с наборами хешей)
☑ Keyword Hits (совпадения ключевых слов)
☑ Recent Activity (Web) (недавние веб-действия)
☑ Accounts (учётные записи)
☑ File Types (типы файлов)

# Параметры
☑ Include thumbnails (включить миниатюры)
☑ Include metadata (включить метаданные)
```

<a id="portable-case"></a>
### Переносимое дело (Portable Case)

```
# Создать переносимое дело для передачи
Generate Report → Portable Case

# Содержит:
- Выбранные доказательства
- Результаты анализа
- Можно открыть в Autopsy
```

---

<a id="-cli-tools-sleuth-kit"></a>
## 💻 Инструменты командной строки (Sleuth Kit)

> **Уточнение к командам.** По документации [fls](https://sleuthkit.org/sleuthkit/man/fls.html) и [icat](https://sleuthkit.org/sleuthkit/man/icat.html), -o задаёт смещение в секторах, поэтому 2048 — пример, а не универсальное начало раздела. Параметр icat -r включает приёмы восстановления удалённого файла. Команды и значения оригинала оставлены дословно; regex-примеры выше являются шаблонами поиска и не проверяют корректность найденных адресов или номеров.


<a id="sleuth-kit-commands"></a>
### Команды Sleuth Kit

Autopsy использует The Sleuth Kit (TSK) как базовый компонент. Доступны следующие команды CLI:

<a id="image-information"></a>
### Информация об образе

```bash
# Получить информацию об образе
img_stat image.E01
img_stat image.dd

# Получить информацию о файловой системе
fsstat -o 2048 image.dd
```

<a id="file-listing"></a>
### Список файлов

```bash
# Вывести список файлов
fls -r -o 2048 image.dd

# Вывести список удалённых файлов
fls -r -d -o 2048 image.dd

# Параметры:
# -r    Рекурсивный обход
# -d    Только удалённые файлы
# -o    Смещение начала раздела (в секторах)
```

<a id="file-recovery"></a>
### Восстановление файлов

```bash
# Извлечь файл по inode
icat -o 2048 image.dd 12345 > recovered_file

# Получить содержимое файла (подпись оригинала; -r — восстановление удалённого)
icat -r -o 2048 image.dd 12345 > file.txt
```

<a id="timeline"></a>
### Временная шкала

```bash
# Создать body-файл
fls -r -m "/" -o 2048 image.dd > body.txt

# Создать временную шкалу
mactime -b body.txt -d > timeline.csv
```

<a id="hash-calculation"></a>
### Вычисление хешей

```bash
# Вычислить MD5
md5sum image.dd

# Вычислить SHA256
sha256sum image.dd

# Проверить образ
sha256sum -c image.dd.sha256
```

<a id="common-tsk-tools"></a>
### Основные инструменты TSK

| Инструмент | Назначение |
| ------ | --------- |
| `img_stat` | Информация об образе |
| `mmls` | Таблица разделов |
| `fsstat` | Информация о файловой системе |
| `fls` | Список файлов |
| `icat` | Извлечение файла по inode |
| `istat` | Информация об inode |
| `blkls` | Извлечение нераспределённого пространства |
| `srch_strings` | Поиск строк |
| `tsk_recover` | Восстановление файлов |
| `mactime` | Создание временной шкалы |

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="investigation-workflow"></a>
### Порядок исследования

```
1. Create Case          → Новое дело со сведениями об исследовании
2. Add Data Source      → Образ диска или накопитель
3. Run Ingest Modules   → Автоматизированный анализ
4. Review Results       → Проверить извлечённое содержимое
5. Keyword Search       → Поиск по заданным словам
6. Timeline Analysis    → Восстановить последовательность событий
7. Tag Evidence         → Отметить важные объекты
8. Generate Report      → Создать итоговый отчёт
```

<a id="keyboard-shortcuts"></a>
### Сочетания клавиш

| Сочетание клавиш | Действие |
| ---------- | -------- |
| Ctrl+N | New Case (новое дело) |
| Ctrl+O | Open Case (открыть дело) |
| Ctrl+F | Find (поиск в таблице) |
| Ctrl+G | Generate Report (создать отчёт) |
| F5 | Refresh (обновить) |

<a id="common-locations-to-check"></a>
### Типичные расположения для проверки

| Windows | Путь |
| --------- | ------ |
| Недавние файлы | `Users\*\AppData\Roaming\Microsoft\Windows\Recent` |
| Загрузки | `Users\*\Downloads` |
| История браузера | `Users\*\AppData\Local\[Browser]\User Data` |
| Временные файлы | `Users\*\AppData\Local\Temp` |
| Корзина | `$Recycle.Bin` |
| Prefetch | `Windows\Prefetch` |
| Журналы событий | `Windows\System32\winevt\Logs` |

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Официальный сайт Autopsy](https://www.autopsy.com/)
- [Sleuth Kit](https://www.sleuthkit.org/)
- [Документация Autopsy](https://sleuthkit.org/autopsy/docs/user-docs/latest/)
- [DFIR Training](https://www.dfir.training/)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [Volatility](../../Volatility/translations/README.ru.md)
- [Команды Linux](../../Linux-Commands/translations/README.ru.md)
- [Wireshark](../../Wireshark/translations/README.ru.md)

---

<p align="center">
  <b>🔍 Освойте цифровую криминалистику!</b><br>
  <i>Autopsy — помощник исследователя цифровых доказательств</i>
</p>
