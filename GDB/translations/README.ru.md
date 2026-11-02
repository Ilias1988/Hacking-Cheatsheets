<a id="-gdb---gnu-debugger-cheatsheet"></a>
# 🐛 GDB — справочник по отладчику GNU

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

> **Уточнения к оригиналу.** В [GDB attach — команда отладчика](https://sourceware.org/gdb/current/onlinedocs/gdb/Attach.html); показанное `gdb attach <PID>` не равно консольному `attach` или запуску с `-p`. У [continue с числом](https://sourceware.org/gdb/current/onlinedocs/gdb/Continuing-and-Stepping.html) это число — количество игнорируемых срабатываний текущей точки, а не любых следующих точек. В [x/NFU](https://sourceware.org/gdb/current/onlinedocs/gdb/Memory.html) размер элемента сохраняется между вызовами, поэтому `x/10x` не всегда означает четыре байта на элемент. Смещения кадров и регистры ниже предполагают соответствующую архитектуру, ABI и наличие указателя кадра.
>
> Команды расширений зависят от их версии; это отдельные интерфейсы GEF/PEDA/pwndbg. [GEF pattern](https://hugsy.github.io/gef/commands/pattern/) создаёт циклическую последовательность: строка из одинаковых A её не заменяет. Пример с pwntools только печатает результат и не создаёт input.txt, а [Python 3 print](https://docs.python.org/3/library/stdtypes.html#str) для bytes может выводить представление с префиксом b. Примеры и строковые значения оригинала сохранены дословно. Надпись в логотипе означает «отладчик GNU».


[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██████╗ ██████╗ 
  ██╔════╝ ██╔══██╗██╔══██╗
  ██║  ███╗██║  ██║██████╔╝
  ██║   ██║██║  ██║██╔══██╗
  ╚██████╔╝██████╔╝██████╔╝
   ╚═════╝ ╚═════╝ ╚═════╝ 
    GNU Debugger
```

<p align="center">
  <img src="https://img.shields.io/badge/GDB-Debugger-blue?style=for-the-badge" alt="GDB">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Exploit_Dev-red?style=for-the-badge" alt="Exploit">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое GDB](#-what-is-gdb)
- [Установка и настройка](#-installation--setup)
- [Основы использования](#-basic-usage)
- [Точки останова](#-breakpoints)
- [Управление выполнением](#-execution-control)
- [Исследование памяти](#-examining-memory)
- [Регистры](#-registers)
- [Анализ стека](#-stack-analysis)
- [GEF/PEDA/pwndbg](#-gefpedapwndbg)
- [Разработка эксплойтов](#-exploit-development)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-gdb"></a>
## 🎯 Что такое GDB

**GDB** (GNU Debugger) — стандартный отладчик Linux. Он полезен для следующих задач:

- 🐛 **Отладка** — поиск и исправление ошибок
- 🔍 **Обратная разработка** — изучение поведения двоичных программ
- 💥 **Разработка эксплойтов** — переполнения буфера и ROP
- 📊 **Анализ двоичных файлов** — исследование потока выполнения
- 🧪 **Задачи CTF** — решение заданий RE/pwn

<a id="supported-architectures"></a>
### Поддерживаемые архитектуры

| Архитектура | Примечания |
| -------------- | ------- |
| x86 | 32-разрядная Intel |
| x86-64 | 64-разрядная Intel/AMD |
| ARM | 32-разрядная ARM |
| ARM64 | 64-разрядная ARM |
| MIPS | Процессоры MIPS |
| PowerPC | Процессоры PPC |

---

<a id="-installation--setup"></a>
## 🚀 Установка и настройка

<a id="install-gdb"></a>
### Установка GDB

```bash
# Debian/Ubuntu/Kali
sudo apt install gdb

# Fedora/RHEL
sudo dnf install gdb

# Arch Linux
sudo pacman -S gdb

# macOS (можно использовать lldb либо установить GDB)
brew install gdb
```

<a id="install-gef-gdb-enhanced-features"></a>
### Установка GEF (GDB Enhanced Features)

```bash
# GEF — рекомендуемый в оригинале вариант
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Или вручную
wget -O ~/.gdbinit-gef.py -q https://gef.blah.cat/py
echo source ~/.gdbinit-gef.py >> ~/.gdbinit
```

<a id="install-peda"></a>
### Установка PEDA

```bash
git clone https://github.com/longld/peda.git ~/peda
echo "source ~/peda/peda.py" >> ~/.gdbinit
```

<a id="install-pwndbg"></a>
### Установка pwndbg

```bash
git clone https://github.com/pwndbg/pwndbg
cd pwndbg
./setup.sh
```

<a id="verify-installation"></a>
### Проверка установки

```bash
gdb --version
gdb -q
(gdb) quit
```

---

<a id="-basic-usage"></a>
## 💻 Основы использования

<a id="starting-gdb"></a>
### Запуск GDB

```bash
# Отладить исполняемый файл
gdb ./program

# Отладить с аргументами
gdb --args ./program arg1 arg2

# Подключиться к работающему процессу
gdb -p <PID>
gdb attach <PID>

# Тихий режим (без приветствия)
gdb -q ./program

# Выполнить команды из файла
gdb -x commands.gdb ./program
```

<a id="basic-commands"></a>
### Базовые команды

| Команда | Сокращение | Описание |
| --------- | ------- | ------------- |
| `run` | `r` | Запустить программу |
| `run args` | `r args` | Запустить с аргументами |
| `continue` | `c` | Продолжить выполнение |
| `quit` | `q` | Выйти из GDB |
| `help` | `h` | Показать справку |
| `help <cmd>` | `h <cmd>` | Справка по команде |

<a id="file-commands"></a>
### Команды работы с файлами

```bash
# Загрузить исполняемый файл
(gdb) file ./program

# Загрузить символы
(gdb) symbol-file ./program.debug

# Загрузить дамп core
(gdb) core-file core

# Задать рабочий каталог
(gdb) cd /path/to/dir

# Задать аргументы
(gdb) set args arg1 arg2
```

---

<a id="-breakpoints"></a>
## 🔴 Точки останова

<a id="setting-breakpoints"></a>
### Установка точек останова

```bash
# Остановиться в функции
(gdb) break main
(gdb) b main

# Остановиться по адресу
(gdb) break *0x08048456
(gdb) b *0x08048456

# Остановиться на строке
(gdb) break file.c:25
(gdb) b 25

# Остановиться по смещению от начала функции
(gdb) break *main+48

# Временная точка останова (удаляется автоматически)
(gdb) tbreak main
```

<a id="conditional-breakpoints"></a>
### Условные точки останова

```bash
# Остановиться при истинном условии
(gdb) break main if argc > 2
(gdb) break *0x08048456 if $eax == 0

# Добавить условие к существующей точке останова
(gdb) condition 1 $eax == 0

# Удалить условие
(gdb) condition 1
```

<a id="managing-breakpoints"></a>
### Управление точками останова

```bash
# Показать точки останова
(gdb) info breakpoints
(gdb) info b

# Удалить точку останова
(gdb) delete 1
(gdb) d 1

# Удалить все точки останова
(gdb) delete
(gdb) d

# Отключить точку останова
(gdb) disable 1
(gdb) dis 1

# Включить точку останова
(gdb) enable 1
(gdb) en 1

# Отключить все
(gdb) disable

# Включить все
(gdb) enable
```

<a id="watchpoints-data-breakpoints"></a>
### Точки наблюдения (останов по данным)

```bash
# Следить за изменением переменной
(gdb) watch variable
(gdb) watch *0x08048456

# Следить за чтением
(gdb) rwatch variable

# Следить за чтением и записью
(gdb) awatch variable

# Следить за выражением
(gdb) watch ($eax > 10)
```

<a id="catchpoints"></a>
### Точки перехвата событий

```bash
# Перехватывать системные вызовы
(gdb) catch syscall

# Перехватывать конкретный системный вызов
(gdb) catch syscall write

# Перехватывать сигналы
(gdb) catch signal SIGSEGV

# Перехватывать исключения
(gdb) catch throw
(gdb) catch catch
```

---

<a id="-execution-control"></a>
## ▶️ Управление выполнением

<a id="stepping"></a>
### Пошаговое выполнение

```bash
# Выполнить одну инструкцию с заходом в вызовы
(gdb) stepi
(gdb) si

# Выполнить одну строку с заходом в вызовы
(gdb) step
(gdb) s

# Выполнить инструкцию без захода в вызовы
(gdb) nexti
(gdb) ni

# Выполнить строку без захода в вызовы
(gdb) next
(gdb) n

# Продолжить до возврата из функции
(gdb) finish
(gdb) fin

# Продолжить до указанного адреса
(gdb) until *0x08048456
(gdb) u *0x08048456
```

<a id="running"></a>
### Запуск и продолжение

```bash
# Запустить или перезапустить программу
(gdb) run
(gdb) r

# Продолжить после точки останова
(gdb) continue
(gdb) c

# Выполнять до следующей точки останова
(gdb) continue

# Пропустить N срабатываний текущей точки (см. уточнение)
(gdb) continue 5

# Перейти к адресу (может нарушить выполнение!)
(gdb) jump *0x08048456
```

<a id="process-control"></a>
### Управление процессом

```bash
# Завершить текущий процесс
(gdb) kill

# Отключиться от процесса
(gdb) detach

# Подключиться к процессу
(gdb) attach <PID>

# Следовать за fork (родительский/дочерний процесс)
(gdb) set follow-fork-mode child
(gdb) set follow-fork-mode parent
```

---

<a id="-examining-memory"></a>
## 🔍 Исследование памяти

<a id="print-command"></a>
### Команда print

```bash
# Вывести переменную
(gdb) print variable
(gdb) p variable

# Вывести в шестнадцатеричном виде
(gdb) print/x variable
(gdb) p/x $eax

# Вывести в двоичном виде
(gdb) p/t variable

# Вывести в десятичном виде
(gdb) p/d variable

# Вывести как символ
(gdb) p/c $al

# Вывести как строку
(gdb) p/s pointer

# Вывести массив
(gdb) p *array@10     # 10 элементов
```

<a id="examine-command-x"></a>
### Команда исследования памяти (x)

```bash
# Синтаксис: x/NFU address
# N = количество, F = формат, U = размер элемента

# Форматы: x(шестнадцатеричный), d(десятичный), u(без знака), o(восьмеричный),
#          t(двоичный), c(символ), s(строка), i(инструкция)
# Размеры: b(байт), h(полуслово=2), w(слово=4), g(восьмибайтовое слово=8)

# Исследовать память
(gdb) x/10x $esp           # 10 шестнадцатеричных слов от ESP
(gdb) x/20wx 0x08048456    # 20 слов в шестнадцатеричном виде
(gdb) x/s 0x08048456       # Строка по адресу
(gdb) x/10i $eip           # 10 инструкций от EIP
(gdb) x/10gx $rsp          # 10 восьмибайтовых элементов от RSP

# Типичные варианты
(gdb) x/100x $esp          # Исследовать стек
(gdb) x/20i $eip           # Дизассемблировать
(gdb) x/s $eax             # Указатель на строку
```

<a id="memory-regions"></a>
### Области памяти

```bash
# Показать карту памяти
(gdb) info proc mappings

# Показать секции
(gdb) maintenance info sections

# Показать файлы
(gdb) info files
```

<a id="disassembly"></a>
### Дизассемблирование

```bash
# Дизассемблировать функцию
(gdb) disassemble main
(gdb) disas main

# Дизассемблировать текущую функцию
(gdb) disassemble
(gdb) disas

# Дизассемблировать диапазон
(gdb) disas 0x08048456, 0x08048500

# Дизассемблировать с исходным кодом (если доступен)
(gdb) disas /m main

# Задать синтаксис
(gdb) set disassembly-flavor intel
(gdb) set disassembly-flavor att
```

---

<a id="-registers"></a>
## 📊 Регистры

<a id="view-registers"></a>
### Просмотр регистров

```bash
# Все регистры
(gdb) info registers
(gdb) i r

# Конкретный регистр
(gdb) info registers eax
(gdb) p $eax
(gdb) p/x $rip

# Все регистры, включая FP/SSE
(gdb) info all-registers
```

<a id="modify-registers"></a>
### Изменение регистров

```bash
# Задать значение регистра
(gdb) set $eax = 0
(gdb) set $eip = 0x08048456
(gdb) set $rip = 0x400000

# Задать флаги
(gdb) set $eflags = 0x246
```

<a id="x86-registers-reference"></a>
### Справочник по регистрам x86

| Регистр | 64-разрядный | 32-разрядный | 16-разрядный | 8-разрядный | Назначение |
| ---------- | -------- | -------- | -------- | ------- | --------- |
| Аккумулятор | RAX | EAX | AX | AL/AH | Возвращаемое значение |
| Базовый | RBX | EBX | BX | BL/BH | Общего назначения |
| Счётчик | RCX | ECX | CX | CL/CH | Счётчик цикла |
| Данные | RDX | EDX | DX | DL/DH | Ввод-вывод, умножение |
| Индекс источника | RSI | ESI | SI | SIL | Источник строковых операций |
| Индекс назначения | RDI | EDI | DI | DIL | Назначение строковых операций |
| Базовый указатель | RBP | EBP | BP | BPL | Кадр стека |
| Указатель стека | RSP | ESP | SP | SPL | Вершина стека |
| Указатель инструкции | RIP | EIP | IP | - | Следующая инструкция |

<a id="x64-additional-registers"></a>
### Дополнительные регистры x64

```
R8-R15 : Регистры общего назначения
R8D-R15D : Младшие 32-разрядные части
R8W-R15W : Младшие 16-разрядные части
R8B-R15B : Младшие 8-разрядные части
```

---

<a id="-stack-analysis"></a>
## 📚 Анализ стека

<a id="view-stack"></a>
### Просмотр стека

```bash
# Трассировка стека
(gdb) backtrace
(gdb) bt

# Полная трассировка стека с локальными переменными
(gdb) backtrace full
(gdb) bt full

# Показать кадр
(gdb) frame
(gdb) f

# Выбрать кадр
(gdb) frame 2
(gdb) f 2

# Показать содержимое стека
(gdb) x/50wx $esp    # 32-разрядный
(gdb) x/50gx $rsp    # 64-разрядный
```

<a id="stack-frame"></a>
### Кадр стека

```bash
# Показать информацию о кадре
(gdb) info frame
(gdb) i f

# Показать локальные переменные
(gdb) info locals

# Показать аргументы
(gdb) info args

# Перейти к предыдущему или следующему кадру
(gdb) up
(gdb) down
```

<a id="stack-commands"></a>
### Команды работы со стеком

```bash
# Исследовать адрес возврата
(gdb) x/wx $ebp+4     # 32-разрядный
(gdb) x/gx $rbp+8     # 64-разрядный

# Исследовать сохранённый EBP
(gdb) x/wx $ebp       # 32-разрядный
(gdb) x/gx $rbp       # 64-разрядный

# Показать расположение данных в стеке
(gdb) x/20wx $esp-20  # До и после
```

---

<a id="-gefpedapwndbg"></a>
## 🎨 GEF/PEDA/pwndbg

<a id="gef-commands"></a>
### Команды GEF

```bash
# Отображение контекста (автоматически)
gef➤ context

# Checksec (защитные механизмы)
gef➤ checksec

# Поиск шаблона
gef➤ pattern create 200
gef➤ pattern search $rsp

# Найти строку или значение
gef➤ search-pattern "/bin/sh"
gef➤ grep "/bin/sh"

# Анализ кучи
gef➤ heap chunks
gef➤ heap bins

# Гаджеты ROP
gef➤ ropgadget

# Помощник для форматных строк
gef➤ format-string-helper

# VMMap (карта памяти)
gef➤ vmmap

# Цепочка разыменований
gef➤ dereference $rsp 20

# Telescope (расширенное разыменование)
gef➤ telescope $rsp 20

# Информация об ELF
gef➤ elf-info

# Значение стекового канареечного маркера (canary)
gef➤ canary

# Информация о PIE/ASLR
gef➤ pie
gef➤ aslr
```

<a id="peda-commands"></a>
### Команды PEDA

```bash
# Проверка защитных механизмов
peda➤ checksec

# Найти строку
peda➤ find "/bin/sh"
peda➤ searchmem "/bin/sh"

# Создать или найти шаблон
peda➤ pattern create 200
peda➤ pattern offset $eip

# Гаджеты ROP
peda➤ ropgadget
peda➤ ropsearch "pop rdi"

# Шелл-код
peda➤ shellcode generate x86/linux exec

# Контекст
peda➤ context all

# Ассемблирование
peda➤ assemble
```

<a id="pwndbg-commands"></a>
### Команды pwndbg

```bash
# Контекст
pwndbg> context

# Checksec
pwndbg> checksec

# Поиск
pwndbg> search -s "/bin/sh"
pwndbg> search -p 0xdeadbeef

# Циклический шаблон
pwndbg> cyclic 200
pwndbg> cyclic -l <value>

# ROP
pwndbg> rop --grep "pop rdi"

# Куча
pwndbg> heap
pwndbg> bins
pwndbg> arena

# GOT/PLT
pwndbg> got
pwndbg> plt
```

---

<a id="-exploit-development"></a>
## 💥 Разработка эксплойтов

<a id="finding-offset"></a>
### Определение смещения

```bash
# Способ 1: шаблон (GEF)
gef➤ pattern create 200
gef➤ run < <(python3 -c 'print("A"*200)')
gef➤ pattern search $rsp

# Способ 2: шаблон (pwntools)
$ python3 -c "from pwn import *; print(cyclic(200))"
gef➤ run < input.txt
gef➤ cyclic -l 0x61616167
```

<a id="buffer-overflow-basics"></a>
### Основы переполнения буфера

```bash
# Проверить ASLR
$ cat /proc/sys/kernel/randomize_va_space
# 0=выключено, 1=частично, 2=полностью

# Отключить ASLR (для тестирования)
$ echo 0 | sudo tee /proc/sys/kernel/randomize_va_space

# Проверить защитные механизмы двоичного файла
gef➤ checksec

# Найти расположение адреса возврата
(gdb) x/20wx $esp     # Перед аварийным завершением
(gdb) i f             # Информация о кадре
```

<a id="finding-gadgets"></a>
### Поиск гаджетов

```bash
# Найти "/bin/sh"
gef➤ search-pattern "/bin/sh"

# Найти адрес system()
(gdb) p system
(gdb) p &system

# Найти гаджет pop rdi (64-bit)
gef➤ ropgadget --grep "pop rdi"

# Найти pop; ret (32-bit)
gef➤ ropgadget --grep "pop"
```

<a id="write-memory"></a>
### Запись в память

```bash
# Задать значение памяти
(gdb) set {int}0x08048456 = 0x41414141
(gdb) set {char}0x08048456 = 'A'

# Записать строку
(gdb) set {char[10]}0x08048456 = "AAAA"

# Вызвать функцию
(gdb) call system("/bin/sh")
```

<a id="useful-for-exploits"></a>
### Полезные приёмы для эксплойтов

```bash
# Следовать за дочерними процессами
(gdb) set follow-fork-mode child

# Отключить ASLR в GDB
(gdb) set disable-randomization on

# Показать общие библиотеки
(gdb) info sharedlibrary

# Найти базовый адрес libc
gef➤ vmmap libc

# Найти PLT/GOT
(gdb) info functions @plt
gef➤ got
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Команда | Описание |
| --------- | ------------- |
| `r` | Запустить программу |
| `c` | Продолжить |
| `b *addr` | Установить точку останова |
| `si` | Выполнить одну инструкцию с заходом в вызовы |
| `ni` | Выполнить одну инструкцию без захода в вызовы |
| `fin` | Довести функцию до возврата |
| `x/Nx addr` | Исследовать память |
| `p $reg` | Вывести регистр |
| `i r` | Информация о регистрах |
| `bt` | Трассировка стека |
| `disas` | Дизассемблировать |
| `q` | Выйти |

<a id="memory-examination"></a>
### Исследование памяти

| Команда | Описание |
| --------- | ------------- |
| `x/10x $esp` | 10 шестнадцатеричных слов от ESP |
| `x/s addr` | Строка по адресу |
| `x/i $eip` | Инструкция по адресу EIP |
| `x/20wx addr` | 20 слов в шестнадцатеричном виде |
| `x/10gx $rsp` | 10 восьмибайтовых слов от RSP |

<a id="quick-debugging"></a>
### Быстрая отладка

```bash
# Запустить программу и остановиться в main
$ gdb -q ./program
(gdb) break main
(gdb) run

# Исследовать аварийное завершение
(gdb) run < payload
(gdb) x/10i $eip
(gdb) i r
(gdb) bt
```

<a id="exploit-dev-workflow"></a>
### Порядок разработки эксплойта

```
1. checksec           → Проверить защитные механизмы
2. pattern create     → Создать шаблон
3. run with pattern   → Вызвать аварийное завершение программы
4. pattern search     → Найти смещение
5. find gadgets       → Гаджеты ROP
6. build payload      → Создать эксплойт
7. test               → Отладить с помощью GDB
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Официальное руководство GDB](https://sourceware.org/gdb/current/onlinedocs/gdb/)
- [Документация GEF](https://hugsy.github.io/gef/)
- [Документация pwndbg](https://github.com/pwndbg/pwndbg)
- [PEDA](https://github.com/longld/peda)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [Ghidra](../../Ghidra/translations/README.ru.md)
- [Команды Linux](../../Linux-Commands/translations/README.ru.md)
- [Повышение привилегий Linux](../../Linux-PrivEsc/translations/README.ru.md)

---

<p align="center">
  <b>🐛 Освойте отладку Linux!</b><br>
  <i>GDB — основной инструмент для обратной разработки в Linux</i>
</p>
