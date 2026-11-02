<a id="-exiftool---metadata-extraction-cheatsheet"></a>
# 📷 ExifTool — справочник по извлечению метаданных

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

> **О версиях.** 12.xx в примере — условное обозначение версии. Поддержка чтения и записи различается по формату. Надпись в ASCII-логотипе означает «инструмент извлечения метаданных».


[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗██╗  ██╗██╗███████╗████████╗ ██████╗  ██████╗ ██╗     
  ██╔════╝╚██╗██╔╝██║██╔════╝╚══██╔══╝██╔═══██╗██╔═══██╗██║     
  █████╗   ╚███╔╝ ██║█████╗     ██║   ██║   ██║██║   ██║██║     
  ██╔══╝   ██╔██╗ ██║██╔══╝     ██║   ██║   ██║██║   ██║██║     
  ███████╗██╔╝ ██╗██║██║        ██║   ╚██████╔╝╚██████╔╝███████╗
  ╚══════╝╚═╝  ╚═╝╚═╝╚═╝        ╚═╝    ╚═════╝  ╚═════╝ ╚══════╝
                 Metadata Extraction Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/ExifTool-Metadata-blue?style=for-the-badge" alt="ExifTool">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Forensics-red?style=for-the-badge" alt="Forensics">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое ExifTool](#-what-is-exiftool)
- [Установка](#-installation)
- [Основы использования](#-basic-usage)
- [Чтение метаданных](#-reading-metadata)
- [Запись метаданных](#-writing-metadata)
- [Удаление метаданных](#-removing-metadata)
- [Типы файлов](#-file-types)
- [OSINT и цифровая криминалистика](#-osint--forensics)
- [Пакетная обработка](#-batch-processing)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-exiftool"></a>
## 🎯 Что такое ExifTool

**ExifTool** — многофункциональный инструмент командной строки для чтения, записи и изменения метаданных файлов. Он полезен для следующих задач:

- 🔍 **OSINT** — извлечение из изображений местоположения и сведений об устройстве
- 🔬 **Цифровая криминалистика** — анализ метаданных документов
- 🔐 **Конфиденциальность** — удаление чувствительных метаданных
- 📸 **Фотография** — управление метаданными снимков
- 📄 **Документы** — анализ метаданных PDF и файлов Office

<a id="why-metadata-matters"></a>
### Зачем нужны метаданные

| Задача | Пример |
|----------|---------|
| **Геолокация** | Координаты GPS в фотографиях |
| **Сведения об устройстве** | Модель камеры, серийный номер |
| **Временные метки** | Когда файл был создан или изменён |
| **Автор** | Кто создал документ |
| **Программное обеспечение** | Какое приложение создало файл |
| **История изменений** | Временные метки изменений |

---

<a id="-installation"></a>
## 🚀 Установка

<a id="linux-kaliubuntu"></a>
### Linux (Kali/Ubuntu)

```bash
# Debian/Ubuntu/Kali
sudo apt install exiftool

# Или
sudo apt install libimage-exiftool-perl
```

<a id="macos"></a>
### macOS

```bash
# Homebrew
brew install exiftool
```

<a id="windows"></a>
### Windows

```bash
# Скачать с официального сайта
https://exiftool.org/

# Распаковать exiftool(-k).exe
# Переименовать в exiftool.exe
# Добавить в PATH или запускать напрямую
```

<a id="verify-installation"></a>
### Проверка установки

```bash
exiftool -ver
# Вывод: 12.xx
```

---

<a id="-basic-usage"></a>
## 💻 Основы использования

<a id="basic-syntax"></a>
### Базовый синтаксис

```bash
# Прочитать метаданные
exiftool [OPTIONS] FILE

# Записать метаданные
exiftool -TAG=VALUE FILE

# Несколько файлов
exiftool *.jpg
exiftool -r directory/
```

<a id="common-options"></a>
### Часто используемые параметры

| Параметр | Описание |
|--------|-------------|
| `-r` | Рекурсивная обработка подкаталогов |
| `-v` | Подробный вывод |
| `-n` | Без преобразования для отображения (исходные значения) |
| `-s` | Краткие имена тегов |
| `-G` | Показывать группу каждого тега |
| `-j` | Вывод JSON |
| `-csv` | Вывод CSV |
| `-overwrite_original` | Не создавать резервную копию |

---

<a id="-reading-metadata"></a>
## 📖 Чтение метаданных

<a id="view-all-metadata"></a>
### Просмотр всех метаданных

```bash
# Все метаданные
exiftool image.jpg

# Подробный вывод
exiftool -v image.jpg

# Очень подробный вывод
exiftool -v3 image.jpg
```

<a id="specific-tags"></a>
### Отдельные теги

```bash
# Один тег
exiftool -GPSLatitude image.jpg

# Несколько тегов
exiftool -GPSLatitude -GPSLongitude -Make -Model image.jpg

# Исключить теги
exiftool --MakerNotes image.jpg
```

<a id="group-based-reading"></a>
### Чтение по группам

```bash
# Показать все теги EXIF
exiftool -EXIF:all image.jpg

# Показать все теги IPTC
exiftool -IPTC:all image.jpg

# Показать все теги XMP
exiftool -XMP:all image.jpg

# Показать все теги GPS
exiftool -GPS:all image.jpg

# Показать имена групп
exiftool -G image.jpg
```

<a id="common-metadata-groups"></a>
### Распространённые группы метаданных

| Группа | Описание |
|-------|-------------|
| EXIF | Данные камеры и изображения |
| IPTC | Метаданные для новостей и прессы |
| XMP | Расширяемые метаданные Adobe |
| GPS | Данные о местоположении |
| MakerNotes | Данные конкретного производителя камеры |
| File | Основные сведения о файле |
| Composite | Вычисляемые значения |

<a id="output-formats"></a>
### Форматы вывода

> **Уточнение.** Параметр [HtmlDump](https://exiftool.org/exiftool_pod.html) создаёт HTML-страницу с шестнадцатеричным представлением EXIF, а не обычную таблицу всех метаданных. Исходная команда сохранена.


```bash
# Вывод JSON
exiftool -j image.jpg

# Вывод CSV
exiftool -csv image.jpg

# Вывод HTML
exiftool -htmlDump image.jpg > output.html

# С разделением табуляцией
exiftool -t image.jpg

# Собственный формат
exiftool -p '$filename: $make $model' image.jpg
```

---

<a id="-writing-metadata"></a>
## ✏️ Запись метаданных

<a id="write-single-tag"></a>
### Запись одного тега

```bash
# Задать автора
exiftool -Author="John Doe" image.jpg

# Задать авторские права
exiftool -Copyright="© 2024 Company" image.jpg

# Задать комментарий
exiftool -Comment="My photo" image.jpg
```

<a id="write-multiple-tags"></a>
### Запись нескольких тегов

```bash
exiftool -Author="John Doe" -Copyright="© 2024" -Keywords="nature,landscape" image.jpg
```

<a id="write-gps-coordinates"></a>
### Запись координат GPS

> **Уточнение.** Для EXIF [направление задаётся отдельно](https://exiftool.org/TagNames/GPS.html) через GPSLatitudeRef/GPSLongitudeRef. Один знак координаты в первом примере не заменяет корректную запись этих тегов; пример с N/W показывает явное указание полушарий.


```bash
# Задать координаты GPS
exiftool -GPSLatitude=37.7749 -GPSLongitude=-122.4194 image.jpg

# С указанием полушарий
exiftool -GPSLatitude=37.7749 -GPSLatitudeRef=N -GPSLongitude=122.4194 -GPSLongitudeRef=W image.jpg

# В формате координат из URL Google Maps
exiftool "-GPSLatitude=37 46 29.64" "-GPSLongitude=122 25 9.84" -GPSLatitudeRef=N -GPSLongitudeRef=W image.jpg
```

<a id="write-datetime"></a>
### Запись даты и времени

> **Уточнение.** [AllDates](https://exiftool.org/TagNames/Shortcuts.html) объединяет только DateTimeOriginal, CreateDate и ModifyDate. Это не все временные метки файла или файловой системы.


```bash
# Задать дату и время
exiftool -DateTimeOriginal="2024:01:15 10:30:00" image.jpg

# Задать все даты (формулировка оригинала; см. уточнение)
exiftool -AllDates="2024:01:15 10:30:00" image.jpg

# Сдвинуть даты
exiftool "-DateTimeOriginal+=1:0:0 0:0:0" image.jpg  # Добавить 1 год
exiftool "-DateTimeOriginal-=0:0:7 0:0:0" image.jpg  # Вычесть 7 дней
```

<a id="copy-metadata-between-files"></a>
### Копирование метаданных между файлами

```bash
# Скопировать все метаданные
exiftool -TagsFromFile source.jpg destination.jpg

# Скопировать отдельные теги
exiftool -TagsFromFile source.jpg -GPS:all -EXIF:all destination.jpg

# Скопировать в несколько файлов
exiftool -TagsFromFile source.jpg -GPS:all *.jpg
```

---

<a id="-removing-metadata"></a>
## 🗑️ Удаление метаданных

<a id="remove-all-metadata"></a>
### Удаление всех метаданных

> **Уточнение к оригиналу.** Возможность удаления зависит от формата и записываемых тегов; -all= не гарантирует удаления любой информации. Для [PDF изменения обратимы](https://exiftool.org/TagNames/PDF.html): прежние данные остаются в файле. В [таблице поддержки](https://exiftool.org/#supported) DOCX/XLSX/PPTX относятся к чтению, поэтому показанное удаление из Office не является универсально поддерживаемой операцией. Без -overwrite_original обычно создаётся копия с суффиксом _original; этот параметр отключает её создание.


```bash
# Удалить все метаданные (формулировка оригинала; см. уточнение)
exiftool -all= image.jpg

# Удалить без резервной копии
exiftool -overwrite_original -all= image.jpg

# Обработать несколько файлов
exiftool -all= *.jpg
exiftool -r -all= directory/
```

<a id="remove-specific-metadata"></a>
### Удаление отдельных метаданных

```bash
# Удалить только данные GPS
exiftool -GPS:all= image.jpg
exiftool -geotag= image.jpg

# Удалить только EXIF
exiftool -EXIF:all= image.jpg

# Удалить комментарии
exiftool -Comment= image.jpg

# Удалить сведения об авторе
exiftool -Author= -Creator= -Artist= image.jpg
```

<a id="remove-from-specific-file-types"></a>
### Удаление из файлов определённых типов

```bash
# Изображения
exiftool -all= *.jpg *.png *.gif

# PDF
exiftool -all= document.pdf

# Файлы Office
exiftool -all= *.docx *.xlsx *.pptx

# Видео
exiftool -all= video.mp4
```

<a id="sanitize-for-privacy"></a>
### Удаление идентифицирующих данных

```bash
# Удалить идентифицирующие сведения (зависит от формата; см. уточнение)
exiftool -all= -overwrite_original \
  -gps:all= \
  -Author= \
  -Creator= \
  -XMP:all= \
  image.jpg
```

---

<a id="-file-types"></a>
## 📁 Типы файлов

<a id="supported-file-types-400"></a>
### Поддерживаемые типы файлов (400+)

| Категория | Форматы |
|----------|---------|
| **Изображения** | JPEG, PNG, GIF, TIFF, BMP, WebP, HEIC, RAW |
| **Фотографии RAW** | CR2, NEF, ARW, DNG, ORF, RW2 |
| **Видео** | MP4, MOV, AVI, MKV, WebM |
| **Аудио** | MP3, WAV, FLAC, M4A, OGG |
| **Документы** | PDF, DOCX, XLSX, PPTX |
| **Архивы** | ZIP (ограниченная поддержка) |

<a id="file-type-specific"></a>
### Особенности отдельных типов файлов

```bash
# Метаданные PDF
exiftool document.pdf
exiftool -Title -Author -Creator -Producer document.pdf

# Метаданные видео
exiftool -Duration -VideoFrameRate -ImageWidth -ImageHeight video.mp4

# Метаданные аудио
exiftool -Artist -Album -Title -Duration audio.mp3

# Документы Office
exiftool -Author -LastModifiedBy -CreateDate -ModifyDate file.docx
```

---

<a id="-osint--forensics"></a>
## 🔍 OSINT и цифровая криминалистика

<a id="extract-gps-location"></a>
### Извлечение местоположения GPS

```bash
# Показать координаты GPS
exiftool -GPS:all image.jpg

# GPS в удобном для чтения виде
exiftool -GPSPosition image.jpg

# Десятичные градусы
exiftool -n -GPSLatitude -GPSLongitude image.jpg

# URL Google Maps
exiftool -p 'https://www.google.com/maps?q=$GPSLatitude,$GPSLongitude' image.jpg
```

<a id="device-identification"></a>
### Определение устройства

```bash
# Сведения о камере
exiftool -Make -Model -SerialNumber image.jpg

# Использованная программа
exiftool -Software -CreatorTool image.jpg

# Сведения об объективе (если доступны)
exiftool -Lens -LensModel -FocalLength image.jpg

# Подробные сведения о камере
exiftool -MakerNotes:all image.jpg
```

<a id="timeline-analysis"></a>
### Анализ временной шкалы

```bash
# Все даты и время
exiftool -time:all image.jpg

# Временные метки файловой системы
exiftool -FileModifyDate -FileAccessDate -FileCreateDate image.jpg

# Временные метки EXIF
exiftool -DateTimeOriginal -CreateDate -ModifyDate image.jpg

# Найти расхождения дат
exiftool -a -G1 -s -time:all image.jpg
```

<a id="document-forensics"></a>
### Исследование документов

```bash
# Анализ PDF
exiftool -a -G1 document.pdf

# История файла Office
exiftool -a -G1 -RevisionNumber -TotalEditTime -LastPrinted file.docx

# Найти сведения об авторе
exiftool -Author -Creator -LastModifiedBy -Company document.pdf
```

<a id="image-authenticity"></a>
### Проверка подлинности изображения

> **Уточнение.** Сведения о программе и расхождения дат — признаки для сопоставления с другими данными, а не самостоятельное доказательство подделки. Метаданные можно изменять, как показано в разделе записи.


```bash
# Проверить сведения о программе редактирования
exiftool -Software -HistorySoftwareAgent image.jpg

# Сопоставить миниатюру с изображением
exiftool -b -ThumbnailImage image.jpg > thumb.jpg

# Проверить признаки изменений
exiftool -ModifyDate -CreateDate -DateTimeOriginal image.jpg
```

<a id="useful-osint-queries"></a>
### Полезные команды для OSINT

```bash
# Извлечь идентифицирующие сведения
exiftool -Make -Model -SerialNumber -Artist -Copyright -GPSPosition image.jpg

# Проверить упоминания инструментов стеганографии
exiftool -Software image.jpg | grep -i "steg\|hide"

# Извлечь встроенную миниатюру
exiftool -b -ThumbnailImage image.jpg > thumbnail.jpg

# Найти артефакты социальных сетей
exiftool -a -G1 image.jpg | grep -i "facebook\|instagram\|twitter"
```

---

<a id="-batch-processing"></a>
## ⚙️ Пакетная обработка

<a id="process-multiple-files"></a>
### Обработка нескольких файлов

```bash
# Все JPG в текущем каталоге
exiftool *.jpg

# Рекурсивная обработка
exiftool -r directory/

# Отдельные расширения
exiftool -ext jpg -ext png directory/
```

<a id="batch-remove-metadata"></a>
### Пакетное удаление метаданных

```bash
# Удалить из всех изображений
exiftool -all= -overwrite_original -r directory/

# Только указанные типы
exiftool -ext jpg -ext png -all= -overwrite_original -r directory/
```

<a id="batch-rename-files"></a>
### Пакетное переименование файлов

> **Уточнение.** [Документация FileName](https://exiftool.org/filename.html) различает присваивание значения и копирование из тегов. Коды %c/%e и их удвоение зависят от контекста, в частности от -d; пример со счётчиком ниже оставлен из оригинала и требует проверки синтаксиса для своей задачи.


```bash
# Переименовать по дате
exiftool '-filename<CreateDate' -d '%Y%m%d_%H%M%S%%-c.%%e' *.jpg

# Переименовать по модели камеры
exiftool '-filename<${Make}_${Model}_${filename}' *.jpg

# Переименовать со счётчиком
exiftool '-filename<photo_%04c.%%e' *.jpg
```

<a id="batch-copy-metadata"></a>
### Пакетное копирование метаданных

```bash
# Скопировать GPS из одного файла во все остальные
exiftool -TagsFromFile source.jpg -GPS:all -overwrite_original *.jpg
```

<a id="export-to-csv"></a>
### Экспорт в CSV

```bash
# Экспортировать метаданные в CSV
exiftool -csv *.jpg > metadata.csv

# Отдельные поля
exiftool -csv -filename -GPSLatitude -GPSLongitude -Make -Model *.jpg > gps_data.csv
```

<a id="conditional-processing"></a>
### Обработка по условию

```bash
# Только файлы с GPS
exiftool -if '$GPSLatitude' -GPSPosition *.jpg

# Только файлы определённой камеры
exiftool -if '$Make eq "Canon"' -filename *.jpg

# Файлы, изменённые после указанной даты
exiftool -if '$ModifyDate gt "2024:01:01"' *.jpg
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="most-used-commands"></a>
### Часто используемые команды

| Задача | Команда |
|---------|---------|
| Просмотр всех метаданных | `exiftool image.jpg` |
| Просмотр GPS | `exiftool -GPS:all image.jpg` |
| Удаление всех метаданных | `exiftool -all= image.jpg` |
| Удаление GPS | `exiftool -GPS:all= image.jpg` |
| Вывод JSON | `exiftool -j image.jpg` |
| Рекурсивная обработка | `exiftool -r directory/` |
| Сведения о камере | `exiftool -Make -Model image.jpg` |
| Временные метки | `exiftool -time:all image.jpg` |

<a id="common-tags"></a>
### Распространённые теги

| Тег | Описание |
|-----|-------------|
| `-Make` | Производитель камеры |
| `-Model` | Модель камеры |
| `-DateTimeOriginal` | Когда сделан снимок |
| `-GPSLatitude` | Широта GPS |
| `-GPSLongitude` | Долгота GPS |
| `-Author` | Автор файла |
| `-Copyright` | Сведения об авторских правах |
| `-Software` | Программа редактирования |
| `-ImageWidth` | Ширина изображения |
| `-ImageHeight` | Высота изображения |

<a id="privacy-checklist"></a>
### Список проверок конфиденциальности

```bash
# 1. Проверить имеющиеся метаданные
exiftool -a -G1 image.jpg

# 2. Проверить GPS
exiftool -GPS:all image.jpg

# 3. Проверить идентифицирующие сведения
exiftool -Make -Model -SerialNumber -Author -Software image.jpg

# 4. Удалить метаданные (с учётом ограничений формата)
exiftool -all= -overwrite_original image.jpg

# 5. Проверить результат удаления
exiftool image.jpg
```

<a id="forensic-checklist"></a>
### Список действий при исследовании

```bash
# 1. Сохранить оригинал (сначала сделать копию!)
cp image.jpg image_original.jpg

# 2. Полная выгрузка метаданных
exiftool -a -G1 -s image.jpg > metadata_report.txt

# 3. Извлечь GPS
exiftool -GPS:all -c "%.6f" image.jpg

# 4. Временная шкала
exiftool -time:all -G1 image.jpg

# 5. Сведения об устройстве
exiftool -Make -Model -SerialNumber -LensModel image.jpg

# 6. Проверить признаки вмешательства
exiftool -Software -HistorySoftwareAgent image.jpg
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Официальный сайт ExifTool](https://exiftool.org/)
- [Документация ExifTool](https://exiftool.org/TagNames/)
- [Форум ExifTool](https://exiftool.org/forum/)
- [Поддерживаемые типы файлов](https://exiftool.org/#supported)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [Autopsy](../../Autopsy/translations/README.ru.md)
- [Volatility](../../Volatility/translations/README.ru.md)
- [Команды Linux](../../Linux-Commands/translations/README.ru.md)

---

<p align="center">
  <b>📷 Освойте анализ метаданных!</b><br>
  <i>ExifTool — у каждого файла своя история</i>
</p>
