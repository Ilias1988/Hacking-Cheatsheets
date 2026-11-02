<a id="-binwalk---firmware-analysis-cheatsheet"></a>
# 🔧 Binwalk — справочник по анализу прошивок

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

> **О версиях.** Оригинал смешивает параметры разных выпусков. [Binwalk v3 переписан на Rust](https://github.com/ReFirmLabs/binwalk); установка через setup.py и многие примеры относятся к v2. В [CLI v3](https://github.com/ReFirmLabs/binwalk/blob/master/src/cliparser.rs) изменены параметры каталога и журнала, а JSON записывается через log. Нельзя считать --json, --disable-plugin, -R, -W и остальные старые примеры взаимозаменяемыми между версиями. Исходные команды сохранены.

> **Уточнения к параметрам.** В [v2 -R принимает последовательность байтов](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/signature.py), а не файл сигнатур; отдельный пример без искомой последовательности неполон. В [модуле энтропии v2](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/entropy.py) -J/--save сохраняет PNG, поэтому подпись «с легендой» неверна; --edge там не указан. В [v2 -W уже выполняет hexdump/diff](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/hexdiff.py); отдельный --diff в этом модуле не определён. [CSV в v2 относится к журналу](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/general.py), а перенаправление stdout само по себе не меняет формат.

> **Об интерпретации.** Интервалы энтропии ниже — упрощённые ориентиры, а не доказательство шифрования или типа данных: высокая энтропия встречается и при сжатии. Сигнатура требует подтверждения структурой файла, а извлечение зависит от формата и [доступных внешних средств](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/extractor.py). По [структурам UBI в Linux](https://github.com/torvalds/linux/blob/master/drivers/mtd/ubi/ubi-media.h), UBI# — сигнатура заголовка UBI, а не доказательство наличия UBIFS. Надпись в логотипе означает «инструмент анализа и извлечения прошивок».

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗███╗   ██╗██╗    ██╗ █████╗ ██╗     ██╗  ██╗
  ██╔══██╗██║████╗  ██║██║    ██║██╔══██╗██║     ██║ ██╔╝
  ██████╔╝██║██╔██╗ ██║██║ █╗ ██║███████║██║     █████╔╝ 
  ██╔══██╗██║██║╚██╗██║██║███╗██║██╔══██║██║     ██╔═██╗ 
  ██████╔╝██║██║ ╚████║╚███╔███╔╝██║  ██║███████╗██║  ██╗
  ╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
           Firmware Analysis & Extraction Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Binwalk-Firmware_Analysis-blue?style=for-the-badge" alt="Binwalk">
  <img src="https://img.shields.io/badge/IoT-Hacking-green?style=for-the-badge" alt="IoT">
  <img src="https://img.shields.io/badge/CTF-red?style=for-the-badge" alt="CTF">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Binwalk](#-what-is-binwalk)
- [Установка](#-installation)
- [Основы использования](#-basic-usage)
- [Поиск сигнатур](#-signature-scanning)
- [Извлечение](#-extraction)
- [Анализ энтропии](#-entropy-analysis)
- [Анализ файловых систем](#-file-system-analysis)
- [Задания CTF](#-ctf-challenges)
- [Расширенные параметры](#-advanced-options)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-binwalk"></a>
## 🎯 Что такое Binwalk

**Binwalk** — быстрый и удобный инструмент для анализа, обратной разработки (reverse engineering) и извлечения данных из образов прошивок. Он полезен для следующих задач:

- 🔧 **Анализ прошивок** — обнаружение встроенных файлов и кода
- 🔓 **Исследование IoT** — извлечение прошивок маршрутизаторов и камер
- 🎯 **Задания CTF** — поиск скрытых данных
- 🔬 **Цифровая криминалистика** — анализ двоичных файлов
- 🔍 **Обратная разработка** — исследование структуры файлов

<a id="what-binwalk-can-detect"></a>
### Что может обнаруживать Binwalk

| Категория | Примеры |
| ---------- | ---------- |
| **Сжатые данные** | gzip, bzip2, lzma, xz, zlib |
| **Архивы** | tar, zip, rar, 7z, cpio |
| **Файловые системы** | squashfs, cramfs, jffs2, ext, ubifs |
| **Исполняемый код** | ELF, PE, ARM, MIPS |
| **Изображения** | JPEG, PNG, GIF, BMP |
| **Криптографические данные** | OpenSSL, сертификаты |
| **Загрузчики** | U-Boot, заголовки ARM |

---

<a id="-installation"></a>
## 🚀 Установка

<a id="linux-kaliubuntu"></a>
### Linux (Kali/Ubuntu)

```bash
# Установить binwalk
sudo apt update
sudo apt install binwalk

# Установить все средства извлечения (рекомендация оригинала)
sudo apt install mtd-utils gzip bzip2 tar arj lhasa p7zip p7zip-full cabextract \
  cramfsswap squashfs-tools sleuthkit default-jdk lzop srecord

# Или выполнить установку из исходников
cd /opt
git clone https://github.com/ReFirmLabs/binwalk.git
cd binwalk
sudo python3 setup.py install

# Установить sasquatch для squashfs
sudo apt install zlib1g-dev liblzma-dev liblzo2-dev
git clone https://github.com/devttys0/sasquatch
cd sasquatch && ./build.sh
```

<a id="macos"></a>
### macOS

```bash
brew install binwalk
```

<a id="verify-installation"></a>
### Проверка установки

```bash
binwalk --help
binwalk -h
```

---

<a id="-basic-usage"></a>
## 💻 Основы использования

<a id="basic-syntax"></a>
### Базовый синтаксис

```bash
# Проанализировать файл и показать сигнатуры
binwalk firmware.bin

# Извлечь файлы
binwalk -e firmware.bin

# Рекурсивное извлечение
binwalk -eM firmware.bin
```

<a id="common-options"></a>
### Основные параметры

| Параметр | Описание |
| -------- | ------------- |
| `-e, --extract` | Извлечение известных типов файлов |
| `-M, --matryoshka` | Рекурсивный анализ извлечённых файлов |
| `-r, --rm` | Удаление вырезанных файлов после извлечения |
| `-q, --quiet` | Тихий режим |
| `-v, --verbose` | Подробный вывод |
| `-d, --depth` | Глубина рекурсии (по умолчанию: 8) |
| `-C, --directory` | Каталог вывода |
| `-f, --log` | Записать журнал в файл |

---

<a id="-signature-scanning"></a>
## 🔍 Поиск сигнатур

<a id="basic-scan"></a>
### Базовое сканирование

```bash
# Найти сигнатуры
binwalk firmware.bin

# Пример вывода:
# DECIMAL       HEXADECIMAL     DESCRIPTION
# --------------------------------------------------------------------------------
# 0             0x0             TRX firmware header, little endian
# 28            0x1C            gzip compressed data
# 1572892       0x18001C        Squashfs filesystem
```

<a id="specific-signature-types"></a>
### Отдельные типы сигнатур

```bash
# Показать только определённые сигнатуры
binwalk -A firmware.bin          # Машинные инструкции и исполняемый код
binwalk -R firmware.bin          # Необработанные байты (подпись оригинала; см. уточнение)
binwalk -B firmware.bin          # Стандартные сигнатуры (по умолчанию)

# Показать все машинные инструкции
binwalk -A firmware.bin

# Найти строку или последовательность байтов
binwalk -R "\x89PNG" firmware.bin
binwalk -R "password" firmware.bin
```

<a id="includeexclude-filters"></a>
### Фильтры включения и исключения

```bash
# Включить только указанные типы
binwalk --include="filesystem" firmware.bin
binwalk --include="compressed" firmware.bin

# Исключить указанные типы
binwalk --exclude="jpeg" firmware.bin

# Показать только указанные сигнатуры
binwalk -y "squashfs" firmware.bin
binwalk -y "gzip" firmware.bin
```

<a id="custom-signatures"></a>
### Пользовательские сигнатуры

```bash
# Использовать собственный файл сигнатур
binwalk -m custom_signatures.txt firmware.bin

# Формат файла сигнатур:
# 0   string  MZ  Microsoft executable
# 0   belong  0x89504E47  PNG image
```

---

<a id="-extraction"></a>
## 📦 Извлечение

<a id="basic-extraction"></a>
### Базовое извлечение

```bash
# Извлечь все распознанные файлы
binwalk -e firmware.bin
# Создаётся: _firmware.bin.extracted/

# Извлечь в указанный каталог
binwalk -e -C /output/dir firmware.bin

# Извлечь с рекурсией (matryoshka)
binwalk -eM firmware.bin
binwalk --extract --matryoshka firmware.bin
```

<a id="extraction-options"></a>
### Параметры извлечения

```bash
# Удалить вырезанные файлы после извлечения
binwalk -er firmware.bin

# Ограничить глубину рекурсии
binwalk -eM --depth=3 firmware.bin

# Извлечение в тихом режиме
binwalk -eq firmware.bin

# Извлечение с подробным выводом
binwalk -ev firmware.bin
```

<a id="manual-extraction-dd"></a>
### Ручное извлечение (dd)

```bash
# Если binwalk не может извлечь данные автоматически
# Получить смещение из результата анализа binwalk
binwalk firmware.bin
# Пример: Squashfs по смещению 1572892

# Извлечь с помощью dd
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892

# Или указать count, если известен размер
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892 count=5000000
```

<a id="carving-specific-types"></a>
### Вырезание файлов определённых типов

```bash
# Извлечь только определённые типы файлов
binwalk -D "jpeg:jpg" firmware.bin
binwalk -D "png:png" firmware.bin
binwalk -D "elf:elf" firmware.bin

# Несколько типов
binwalk -D "jpeg:jpg" -D "png:png" firmware.bin

# Формат: type:extension:command
binwalk -D "gzip:gz:gunzip %e" firmware.bin
```

---

<a id="-entropy-analysis"></a>
## 📊 Анализ энтропии

<a id="what-is-entropy"></a>
### Что такое энтропия?

Энтропия оценивает степень случайности данных:
- **Высокая энтропия (0.9-1.0)** = зашифрованные или сжатые данные
- **Средняя энтропия (0.5-0.8)** = исполняемый код
- **Низкая энтропия (0.0-0.4)** = обычный текст или повторяющиеся данные

<a id="generate-entropy-graph"></a>
### Построение графика энтропии

```bash
# Вычислить энтропию
binwalk -E firmware.bin
binwalk --entropy firmware.bin

# Сохранить график энтропии
binwalk -E --save firmware.bin
# Создаётся: firmware.bin.png

# С легендой (подпись оригинала; см. уточнение)
binwalk -EJ firmware.bin
```

<a id="entropy-options"></a>
### Параметры анализа энтропии

```bash
# Задать размер блока
binwalk -E --block=1024 firmware.bin

# Показать значения энтропии без графика
binwalk -E --nplot firmware.bin

# Найти резкие повышения и понижения энтропии
binwalk -E --edge firmware.bin
```

<a id="interpreting-entropy"></a>
### Интерпретация энтропии

```
Высокая ровная линия (~1.0)  → Зашифрованные данные
Пики на графике (~0.7)      → Сжатые данные
Волнистая линия (~0.5)     → Код или данные
Низкая ровная линия (~0.2) → Обычный текст
Резкое падение до 0       → Заполнение нулевыми байтами
```

---

<a id="-file-system-analysis"></a>
## 💾 Анализ файловых систем

<a id="common-firmware-file-systems"></a>
### Распространённые файловые системы прошивок

| Файловая система | Описание |
| ------------- | ------------- |
| **SquashFS** | Сжатая файловая система только для чтения (наиболее распространённая) |
| **JFFS2** | Журналируемая файловая система для флеш-памяти |
| **UBIFS** | Развитие идей JFFS2 для флеш-памяти большого объёма |
| **CramFS** | Сжатая файловая система для ПЗУ |
| **YAFFS** | Yet Another Flash File System (ещё одна файловая система для флеш-памяти) |
| **ext2/3/4** | Стандартная файловая система Linux |

<a id="extract-squashfs"></a>
### Извлечение SquashFS

```bash
# Извлечение через Binwalk
binwalk -e firmware.bin

# Ручное извлечение через unsquashfs
unsquashfs squashfs-root.bin
ls squashfs-root/

# Смонтировать, если формат поддерживается
sudo mount -t squashfs squashfs.bin /mnt
```

<a id="extract-jffs2"></a>
### Извлечение JFFS2

```bash
# С помощью jefferson
pip3 install jefferson
jefferson firmware.jffs2 -d output/

# Или с помощью binwalk
binwalk -e firmware.bin
```

<a id="extract-ubifs"></a>
### Извлечение UBIFS

```bash
# С помощью ubireader
pip3 install ubi_reader
ubireader_extract_images firmware.ubi -o output/
ubireader_extract_files firmware.ubi -o output/
```

<a id="analyze-extracted-files"></a>
### Анализ извлечённых файлов

```bash
# Изучить содержимое после извлечения
cd _firmware.bin.extracted/

# Найти интересующие файлы
find . -name "*.conf"
find . -name "passwd"
find . -name "shadow"
find . -name "*.key"
find . -name "*.pem"

# Найти пароли и секреты
grep -r "password" .
grep -r "admin" .
grep -r "root:" .

# Найти исполняемые файлы
find . -type f -executable
file */bin/*
```

---

<a id="-ctf-challenges"></a>
## 🎯 Задания CTF

<a id="common-ctf-scenarios"></a>
### Типичные сценарии CTF

<a id="hidden-file-in-image"></a>
#### Скрытый файл в изображении

```bash
# Проверить изображение на скрытые данные
binwalk image.png
binwalk image.jpg

# Извлечь скрытые файлы
binwalk -e image.png

# Также проверить строки
strings image.png | head -50
```

<a id="stacked-archives"></a>
#### Вложенные архивы

```bash
# Рекурсивное извлечение (matryoshka)
binwalk -eM challenge.bin

# Несколько слоёв сжатия
binwalk -eM --depth=10 nested.bin
```

<a id="find-hidden-text"></a>
#### Поиск скрытого текста

```bash
# Использовать вместе со strings
binwalk challenge.bin
strings challenge.bin | grep -i "flag"
strings challenge.bin | grep "CTF{"
```

<a id="encrypted-sections"></a>
#### Зашифрованные разделы

```bash
# Анализ энтропии указывает на возможное шифрование
binwalk -E challenge.bin

# Высокая энтропия может указывать на шифрование
# Проверить типичные пароли или поискать ключ в другом месте
```

<a id="ctf-workflow"></a>
### Порядок работы в CTF

```bash
# 1. Базовый анализ
binwalk challenge.bin

# 2. Проверить энтропию
binwalk -E challenge.bin

# 3. Извлечь всё содержимое
binwalk -eM challenge.bin

# 4. Поискать в извлечённых файлах
cd _challenge.bin.extracted/
find . -type f -exec file {} \;
grep -r "flag" .
strings * | grep -i flag

# 5. Проверить наличие скрытых данных
binwalk -A challenge.bin    # Машинные инструкции
xxd challenge.bin | head    # Шестнадцатеричный дамп
```

<a id="steganography-detection"></a>
### Обнаружение стеганографии

```bash
# Найти скрытые данные в изображениях
binwalk -B suspicious.jpg

# Проверить наличие данных в конце файла
binwalk suspicious.png

# Извлечь найденные данные
binwalk -e suspicious.jpg

# Сравнить размер файла с ожидаемым
ls -la suspicious.jpg
```

---

<a id="-advanced-options"></a>
## ⚙️ Расширенные параметры

<a id="hexdump-output"></a>
### Шестнадцатеричный вывод

```bash
# Показать шестнадцатеричный дамп по смещениям
binwalk -W firmware.bin
binwalk --hexdump firmware.bin

# Шестнадцатеричный дамп по указанному смещению
binwalk -o 0x1000 -l 512 -W firmware.bin
```

<a id="offset-options"></a>
### Параметры смещения

```bash
# Начать с указанного смещения
binwalk -o 1024 firmware.bin
binwalk --offset=1024 firmware.bin

# Проанализировать только указанное число байтов
binwalk -l 10000 firmware.bin
binwalk --length=10000 firmware.bin
```

<a id="performance-options"></a>
### Параметры производительности

```bash
# Использовать несколько процессов
binwalk --threads=4 firmware.bin

# Отключить определённые плагины
binwalk --disable-plugin=lzma firmware.bin
```

<a id="output-formats"></a>
### Форматы вывода

```bash
# Вывод CSV
binwalk --csv firmware.bin > results.csv

# Вывод JSON
binwalk --json firmware.bin > results.json

# Записать журнал в файл
binwalk -f scan.log firmware.bin
```

<a id="diff-two-files"></a>
### Сравнение двух файлов

```bash
# Сравнить две версии прошивки
binwalk -W --diff firmware_v1.bin firmware_v2.bin

# Показать различия в шестнадцатеричном виде
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="most-used-commands"></a>
### Часто используемые команды

| Назначение | Команда |
| --------- | --------- |
| Анализ файла | `binwalk firmware.bin` |
| Извлечение всего содержимого | `binwalk -e firmware.bin` |
| Рекурсивное извлечение | `binwalk -eM firmware.bin` |
| Анализ энтропии | `binwalk -E firmware.bin` |
| Поиск машинных инструкций | `binwalk -A firmware.bin` |
| Поиск строки | `binwalk -R "pattern" file` |
| Извлечение в каталог | `binwalk -e -C outdir/ file` |
| Извлечение с удалением вырезанных файлов | `binwalk -er firmware.bin` |

<a id="common-file-signatures"></a>
### Распространённые сигнатуры файлов

| Сигнатура | Описание |
| ----------- | ------------- |
| `\x1f\x8b\x08` | Сжатие gzip |
| `BZh` | Сжатие bzip2 |
| `\xfd7zXZ` | Сжатие xz |
| `hsqs` | SquashFS (порядок байтов little endian) |
| `sqsh` | SquashFS (порядок байтов big endian) |
| `\x85\x19` | JFFS2 |
| `UBI#` | UBIFS |
| `\x89PNG` | Изображение PNG |
| `\xff\xd8\xff` | Изображение JPEG |
| `PK\x03\x04` | Архив ZIP |

<a id="extraction-workflow"></a>
### Порядок извлечения

```
1. binwalk firmware.bin       → Определить содержимое
2. binwalk -E firmware.bin    → Проверить признаки шифрования
3. binwalk -e firmware.bin    → Извлечь
4. cd _firmware.bin.extracted → Изучить содержимое
5. find . -name "*" -type f   → Перечислить все файлы
6. grep -r "password" .       → Найти секреты
```

<a id="manual-extraction"></a>
### Ручное извлечение

```bash
# Получить смещение из вывода binwalk
binwalk firmware.bin
# Squashfs по смещению 0x180000 (1572864 в десятичной системе)

# Извлечь через dd
dd if=firmware.bin of=fs.squashfs bs=1 skip=1572864

# Извлечь squashfs
unsquashfs fs.squashfs
```

<a id="useful-file-locations-firmware"></a>
### Полезные пути в прошивке

| Путь | Содержимое |
| ------ | ---------- |
| `/etc/passwd` | Учётные записи пользователей |
| `/etc/shadow` | Хеши паролей |
| `/etc/config/` | Конфигурация устройства |
| `/etc/init.d/` | Сценарии запуска |
| `/usr/bin/` | Исполняемые файлы пользователя |
| `/www/` | Веб-интерфейс |
| `/etc/ssl/` | Сертификаты SSL |

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Binwalk на GitHub](https://github.com/ReFirmLabs/binwalk)
- [Wiki Binwalk](https://github.com/ReFirmLabs/binwalk/wiki)
- [Основы анализа прошивок](https://www.youtube.com/results?search_query=firmware+analysis+binwalk)
- [Анализ прошивок IoT: OWASP](https://owasp.org/www-project-iot-security/)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [ExifTool](../../ExifTool/translations/README.ru.md)
- [Volatility](../../Volatility/translations/README.ru.md)
- [Autopsy](../../Autopsy/translations/README.ru.md)
- [Команды Linux](../../Linux-Commands/translations/README.ru.md)

---

<p align="center">
  <b>🔧 Освойте анализ прошивок!</b><br>
  <i>Binwalk — исследуйте содержимое прошивок</i>
</p>
