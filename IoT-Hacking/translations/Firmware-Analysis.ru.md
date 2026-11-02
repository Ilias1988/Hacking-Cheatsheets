# 💾 Шпаргалка по анализу прошивок

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Firmware-Analysis.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Уточнения к оригиналу:** высокая энтропия может указывать как на сжатие, так и на шифрование, и сама по себе не доказывает шифрование. Синтаксис параметров зависит от версии Binwalk; примеры сохранены без изменений, см. [официальное руководство по энтропии](https://github.com/ReFirmLabs/binwalk/wiki/Generating-Entropy-Graphs). unsquashfs распаковывает образ без монтирования; squashfs-root обычно обозначает выходной каталог, тогда как входным аргументом должен быть образ или блочное устройство. См. [руководство Unsquashfs](https://github.com/plougher/squashfs-tools/blob/master/Documentation/4.7.4/USAGE-UNSQUASHFS.md).


[🇹🇷 Türkçe Sürüm](Firmware-Analysis.tr.md) • [🇬🇷 Ελληνικά](Firmware-Analysis.el.md)

---

## 📥 Получение прошивки

```bash
# Скачать с сайта производителя
# Перехватить обновление по воздуху (OTA) с помощью MITM
# Прочитать дамп с устройства (UART, SPI, JTAG)
# Извлечь из мобильного приложения
```

---

## 🔍 Первичный анализ

```bash
# Тип файла
file firmware.bin

# Строки
strings firmware.bin | grep -i password
strings firmware.bin | grep -i admin
strings firmware.bin | grep -i http

# Энтропия (признаки сжатия или шифрования)
binwalk -E firmware.bin
```

---

## 📦 Извлечение с помощью Binwalk

```bash
# Проанализировать прошивку
binwalk firmware.bin

# Извлечь всё
binwalk -e firmware.bin

# Извлечь в каталог
binwalk -e -C output firmware.bin

# Рекурсивное извлечение
binwalk -eM firmware.bin

# Определённые сигнатуры
binwalk -D 'elf:elf' firmware.bin
```

---

## 🗂️ Файловые системы

### SquashFS
```bash
# Извлечь
unsquashfs squashfs-root
unsquashfs -d output filesystem.squashfs

# Упаковать заново
mksquashfs output modified.squashfs -comp xz
```

### JFFS2
```bash
# Смонтировать
modprobe jffs2
modprobe mtdram total_size=32768 erase_size=256
dd if=jffs2.img of=/dev/mtdblock0
mount -t jffs2 /dev/mtdblock0 /mnt
```

### UBIFS
```bash
# Извлечь с помощью ubi_reader
ubireader_extract_files image.ubi
```

---

## 🔐 Секреты и учётные данные

```bash
# Найти учётные данные
grep -rni "password" filesystem/
grep -rni "admin" filesystem/
grep -rni "api_key" filesystem/
grep -rni "secret" filesystem/

# Найти ключи SSH
find . -name "id_rsa*"
find . -name "*.pem"

# Найти файлы конфигурации
find . -name "*.conf"
find . -name "*.cfg"
cat etc/shadow
cat etc/passwd
```

---

## 🔄 Анализ исполняемых файлов

```bash
# Найти исполняемые файлы ELF
find . -type f -exec file {} \; | grep ELF

# Проанализировать с помощью Ghidra
ghidraRun

# Найти строки, встроенные в код
strings binary | grep -E "http|ftp|telnet|ssh"
```

---

## 🛠️ Инструменты

| Инструмент | Назначение |
|------|---------|
| **Binwalk** | Извлечение |
| **firmware-mod-kit** | Изменение прошивки |
| **EMBA** | Автоматизированный анализ |
| **Ghidra** | Обратная разработка исполняемых файлов |
| **firmwalker** | Поиск секретов |

---

## 📋 Контрольный список анализа прошивки

```markdown
□ Определить тип файла
□ Проверить энтропию (есть ли признаки шифрования?)
□ Извлечь содержимое с помощью binwalk
□ Смонтировать файловую систему
□ Найти учётные данные
□ Проанализировать исполняемые файлы
□ Проверить наличие бэкдоров (скрытых способов доступа)
□ Найти отладочные интерфейсы
```

---

**К разделу IoT:** [📡 Исследование безопасности IoT](./README.ru.md)
