# 🔬 Шпаргалка по компьютерной криминалистике в CTF

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Forensics-CTF.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Версии и интерфейс:** команды imageinfo и --profile относятся к Volatility 2. [Volatility 3 не использует профили](https://github.com/volatilityfoundation/volatility3/blob/develop/doc/source/vol2to3.rst). В исходном аудиопримере [Plot Spectrum](https://manual.audacityteam.org/man/plot_spectrum.html) строит спектр выделенного звука; для представления частот во времени используется [Spectrogram View](https://manual.audacityteam.org/man/spectrogram_view.html). Исходный путь меню оставлен без изменений.

[🇹🇷 Türkçe Sürüm](Forensics-CTF.tr.md) • [🇬🇷 Ελληνικά](Forensics-CTF.el.md)

---

## 📁 Анализ файлов

```bash
# Тип файла
file unknown_file

# Шестнадцатеричный дамп
xxd file | head
hexdump -C file | head

# Строки
strings -n 6 file
strings -e l file    # Порядок байтов little-endian

# Восстановление файлов по содержимому (file carving)
binwalk file
binwalk -e file      # Извлечь
foremost file
```

---

## 🖼️ Стеганография

### Изображения

```bash
# Метаданные
exiftool image.jpg
identify -verbose image.png

# Steghide (JPEG)
steghide extract -sf image.jpg

# Zsteg (PNG)
zsteg image.png
zsteg -a image.png   # Все методы

# Анализ младших битов (LSB)
stegsolve.jar        # Инструмент с графическим интерфейсом

# Строки внутри изображения
strings image.png | grep -i flag
```

### Аудио
```bash
# Спектрограмма (Audacity; см. уточнение ниже)
Open file → Analyze → Plot Spectrum

# Sonic Visualiser
# Проверить скрытые данные в частотном представлении
```

---

## 🧠 Анализ оперативной памяти

### Volatility
```bash
# Определить профиль
volatility -f dump.raw imageinfo

# Список процессов
volatility -f dump.raw --profile=Win7SP1x64 pslist
volatility -f dump.raw --profile=Win7SP1x64 pstree

# История команд
volatility -f dump.raw --profile=Win7SP1x64 cmdscan
volatility -f dump.raw --profile=Win7SP1x64 consoles

# Выгрузить память процесса
volatility -f dump.raw --profile=Win7SP1x64 procdump -p 1234 -D output/

# Файлы
volatility -f dump.raw --profile=Win7SP1x64 filescan | grep -i flag
```

---

## 💽 Анализ дисков

```bash
# Подключить образ
mount -o loop image.dd /mnt/disk

# Сведения о файловой системе
fsstat image.dd

# Autopsy (GUI)
autopsy

# Sleuth Kit
fls image.dd
icat image.dd 1234 > recovered_file
```

---

## 📊 Сетевой анализ (PCAP)

```bash
# Wireshark
wireshark capture.pcap

# Tshark
tshark -r capture.pcap
tshark -r capture.pcap -Y "http" -T fields -e http.file_data

# Извлечь файлы
tcpflow -r capture.pcap
```

---

## 📋 Контрольный список для компьютерной криминалистики

```markdown
□ Проверить тип файла (командой file)
□ Поискать флаг среди строк
□ Проверить метаданные (exiftool)
□ Проверить встроенные файлы с помощью Binwalk
□ Применить инструменты стеганографии
□ Проанализировать память (Volatility)
□ Проанализировать PCAP (Wireshark)
```

---

**К разделу CTF:** [🏁 Обзор CTF](./README.ru.md)
