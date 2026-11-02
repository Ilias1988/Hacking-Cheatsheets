<a id="-ghidra---reverse-engineering-cheatsheet"></a>
# 🔍 Ghidra — справочник по обратной разработке

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██╗  ██╗██╗██████╗ ██████╗  █████╗ 
  ██╔════╝ ██║  ██║██║██╔══██╗██╔══██╗██╔══██╗
  ██║  ███╗███████║██║██║  ██║██████╔╝███████║
  ██║   ██║██╔══██║██║██║  ██║██╔══██╗██╔══██║
  ╚██████╔╝██║  ██║██║██████╔╝██║  ██║██║  ██║
   ╚═════╝ ╚═╝  ╚═╝╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝
              NSA Reverse Engineering Suite
```

<p align="center">
  <img src="https://img.shields.io/badge/Ghidra-Reverse_Engineering-blue?style=for-the-badge" alt="Ghidra">
  <img src="https://img.shields.io/badge/NSA-Open_Source-green?style=for-the-badge" alt="NSA">
  <img src="https://img.shields.io/badge/FREE-red?style=for-the-badge" alt="Free">
</p>

> **О версиях и сценариях.** Требование JDK 17 относится к прежним выпускам: [текущий README Ghidra](https://github.com/NationalSecurityAgency/ghidra) указывает JDK 25; проверяйте требования конкретного релиза. [PyGhidra](https://github.com/NationalSecurityAgency/ghidra/blob/master/Ghidra/Features/PyGhidra/src/main/py/README.md) поддерживает CPython 3. Пример с f-строкой требует Python 3 и не подходит прежнему [Jython 2.7](https://ghidra.re/ghidra_docs/GhidraClass/Intermediate/Scripting.html) без изменений. Исходные команды и сценарии сохранены. Надпись в логотипе означает «набор средств обратной разработки NSA».

> **Об интерфейсе и анализе.** По [официальному учебному руководству](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html), действия зависят от окна и текущего объекта, а сочетания клавиш настраиваются. Поэтому повторяющиеся или противоречивые привязки из таблиц проверяйте в своей установке. Non-returning Functions не означает поиск всех функций. Имена сценариев и доступность пунктов вроде Fill with NOPs зависят от версии и расширений; таблица не гарантирует, что каждый названный сценарий встроен. Таблица соглашений о вызове — упрощённая схема, не описание всех ABI.

> **О сохранении изменений.** [BinaryExporter](https://ghidra.re/ghidra_docs/api/ghidra/app/util/exporter/BinaryExporter.html) экспортирует блоки памяти как необработанные байты; такой экспорт не равнозначен сохранению исходной структуры PE/ELF. Изменение условного перехода или замена CALL требует учитывать размер и кодирование конкретной инструкции. Примеры оригинала не исправлялись.

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Ghidra](#-what-is-ghidra)
- [Установка](#-installation)
- [Обзор интерфейса](#-interface-overview)
- [Создание проекта](#-creating-a-project)
- [Основы анализа](#-analysis-basics)
- [Навигация](#-navigation)
- [Декомпилятор](#-decompiler)
- [Типы данных и структуры](#-data-types--structures)
- [Исправление и изменение кода](#-patching--modifying)
- [Сценарии автоматизации](#-scripting)
- [Сочетания клавиш](#-keyboard-shortcuts)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-ghidra"></a>
## 🎯 Что такое Ghidra

**Ghidra** — бесплатный открытый набор средств обратной разработки ПО (Software Reverse Engineering, SRE), созданный NSA. Он включает:

- 🔍 **Дизассемблер** — преобразование двоичного кода в ассемблерное представление
- 🔄 **Декомпилятор** — получение псевдокода, похожего на C
- 📊 **Анализ** — автоматизированный анализ кода
- 🔧 **Исправление кода (patching)** — изменение двоичных файлов
- 🐍 **Сценарии** — автоматизация на Python и Java
- 👥 **Совместная работа** — поддержка нескольких пользователей

<a id="supported-architectures"></a>
### Поддерживаемые архитектуры

| Архитектура | Варианты |
|--------------|----------|
| **x86** | 16/32/64 бита |
| **ARM** | ARM32, ARM64, Thumb |
| **MIPS** | 32/64 бита |
| **PowerPC** | 32/64 бита |
| **SPARC** | 32/64 бита |
| **RISC-V** | 32/64 бита |
| **AVR** | 8 бит |
| **6502** | 8 бит |

<a id="supported-formats"></a>
### Поддерживаемые форматы

| Формат | Описание |
|--------|-------------|
| ELF | Исполняемые файлы Linux |
| PE/COFF | Исполняемые файлы Windows |
| Mach-O | Исполняемые файлы macOS |
| Raw Binary (необработанный двоичный формат) | Прошивки, ROM |
| APK/DEX | Приложения Android |
| и многие другие... |

---

<a id="-installation"></a>
## 🚀 Установка

<a id="download"></a>
### Загрузка

```bash
# Скачать с официального сайта
https://ghidra-sre.org/

# Или из раздела GitHub Releases
https://github.com/NationalSecurityAgency/ghidra/releases
```

<a id="requirements"></a>
### Требования

| Требование | Версия |
|-------------|---------|
| **JDK** | 17+ (64 бита) |
| **Оперативная память (RAM)** | Не менее 4 ГБ, рекомендуется 8 ГБ и больше |
| **Диск** | От 1 ГБ для установки |

<a id="linuxmacos"></a>
### Linux/macOS

```bash
# Установить JDK 17+
sudo apt install openjdk-17-jdk  # Debian/Ubuntu
brew install openjdk@17          # macOS

# Распаковать Ghidra
unzip ghidra_*.zip
cd ghidra_*

# Запустить
./ghidraRun
```

<a id="windows"></a>
### Windows

```batch
:: Установить JDK 17+ от Oracle или Adoptium
:: Распаковать ghidra_*.zip
:: Запустить ghidraRun.bat
```

---

<a id="-interface-overview"></a>
## 🖥️ Обзор интерфейса

<a id="main-windows"></a>
### Основные окна

| Окно | Описание |
|--------|-------------|
| **Program Trees (дерево программы)** | Структура файлов проекта |
| **Symbol Tree (дерево символов)** | Функции, метки и классы |
| **Data Type Manager (менеджер типов данных)** | Определённые типы данных |
| **Listing (листинг)** | Представление дизассемблированного кода |
| **Decompiler (декомпилятор)** | Псевдокод C |
| **Console (консоль)** | Вывод и сценарии |
| **Defined Strings (определённые строки)** | Найденные строки |
| **Bookmarks (закладки)** | Сохранённые позиции |

<a id="listing-window-disassembly"></a>
### Окно Listing (дизассемблирование)

```
Address    | Bytes          | Disassembly           | Comments
-----------|----------------|-----------------------|----------
00401000   | 55             | PUSH EBP              | Начало функции
00401001   | 89 e5          | MOV EBP, ESP          |
00401003   | 83 ec 10       | SUB ESP, 0x10         | Кадр стека
00401006   | e8 f5 ff ff ff | CALL FUN_00401000     |
```

---

<a id="-creating-a-project"></a>
## 📁 Создание проекта

<a id="new-project"></a>
### Новый проект

```
1. File → New Project (создать проект)
2. Выбрать тип:
   - Non-Shared Project (локальный проект)
   - Shared Project (совместная работа)
3. Выбрать каталог
4. Ввести имя проекта
5. Нажать Finish (готово)
```

<a id="import-binary"></a>
### Импорт двоичного файла

```
1. File → Import File (I) — импорт файла
2. Выбрать двоичный или другой файл
3. Выбрать формат (обычно определяется автоматически)
4. Задать параметры:
   - Language/Processor (язык/процессор)
   - Compiler spec (спецификация компилятора)
5. Нажать OK
6. Analyze? → Yes (запустить анализ; рекомендуется)
```

<a id="analysis-options"></a>
### Параметры анализа

| Параметр | Описание |
|--------|-------------|
| ASCII Strings (строки ASCII) | Поиск строк |
| Decompiler (декомпилятор) | Включение декомпиляции |
| Stack Analysis (анализ стека) | Анализ кадра стека |
| Reference Analysis (анализ ссылок) | Поиск перекрёстных ссылок |
| Function ID (идентификация функций) | Сопоставление с библиотечными функциями |
| Call Convention (соглашение о вызове) | Определение соглашений о вызове |

---

<a id="-analysis-basics"></a>
## 🔬 Основы анализа

<a id="auto-analysis"></a>
### Автоматический анализ

```
Analysis → Auto Analyze (A) — автоматический анализ
- Запускает все выбранные анализаторы
- Анализ крупных файлов может занять время
- Ход работы отображается в мониторе задач
```

<a id="one-shot-analyzers"></a>
### Однократные анализаторы

```
Analysis → One Shot → [Analyzer] (выбрать однократный анализатор)
- ASCII Strings (строки ASCII)
- Decompiler Parameter ID (параметры декомпилятора)
- Function ID (идентификация функций)
- Stack Analysis (анализ стека)
```

<a id="function-identification"></a>
### Определение функций

```
# Найти все функции (формулировка оригинала; см. уточнение)
Analysis → Auto Analyze → включить "Non-returning Functions"

# Создание функции вручную
Контекстное меню адреса → Create Function (F) — создать функцию

# Неопределённый код
Контекстное меню → Disassemble (D) — дизассемблировать
```

---

<a id="-navigation"></a>
## 🧭 Навигация

<a id="go-to-address"></a>
### Переход к адресу

```
# Сочетание клавиш
G → ввести адрес → OK

# Примеры:
0x00401000      # Шестнадцатеричный адрес
main            # Имя функции
entry           # Точка входа
LAB_00401234    # Метка
```

<a id="navigation-shortcuts"></a>
### Сочетания клавиш навигации

| Сочетание клавиш | Действие |
|----------|--------|
| `G` | Перейти к адресу |
| `Ctrl+G` | Перейти к адресу |
| `Alt+←` | Назад по истории |
| `Alt+→` | Вперёд по истории |
| `Ctrl+Shift+E` | Перейти к точке входа |

<a id="cross-references-xrefs"></a>
### Перекрёстные ссылки (XREF)

```
# Просмотреть ссылки на текущую позицию
Контекстное меню → References → Show References To (ссылки на текущий адрес)

# Найти места вызова функции
Выбрать функцию → контекстное меню → References → Show References To

# Клавиатура
Ctrl+Shift+F → поиск ссылок (Find references)
```

<a id="search-functions"></a>
### Поиск функций

```
# Поиск
Search → Memory (S)           # Поиск в памяти
Search → For Strings          # Поиск строк
Search → For Direct References # Поиск прямых ссылок на адрес
Search → Program Text         # Поиск в тексте листинга

# Поиск функции
Symbol Tree → Filter → ввести имя функции
```

---

<a id="-decompiler"></a>
## 💻 Декомпилятор

<a id="open-decompiler"></a>
### Открытие декомпилятора

```
Window → Decompiler (окно декомпилятора)
# Или открыть вкладку Decompiler
# Автоматически декомпилирует выбранную функцию
```

<a id="reading-decompiled-code"></a>
### Чтение декомпилированного кода

```c
// Пример вывода декомпилятора
undefined4 main(int param_1, char **param_2)
{
  char local_20 [16];
  
  printf("Enter password: ");
  scanf("%15s", local_20);
  if (strcmp(local_20, "secret123") == 0) {
    puts("Access granted!");
  }
  else {
    puts("Access denied!");
  }
  return 0;
}
```

<a id="improve-decompilation"></a>
### Улучшение декомпиляции

```
# Переименовать переменные
Контекстное меню переменной → Rename Variable (L) — переименовать

# Изменить тип данных
Контекстное меню переменной → Retype Variable (Ctrl+L) — изменить тип

# Добавить комментарии
; → Set EOL comment (комментарий конца строки)
Ctrl+; → Set plate comment (общий комментарий)

# Задать сигнатуру функции
Контекстное меню функции → Edit Function Signature (изменить сигнатуру)
```

<a id="decompiler-tricks"></a>
### Приёмы работы с декомпилятором

| Действие | Способ |
|--------|-----|
| Переименовать переменную | `L` или контекстное меню |
| Изменить тип | `Ctrl+L` |
| Создать структуру | Контекстное меню → Auto Create Structure (создать структуру автоматически) |
| Разделить/объединить переменные | Контекстное меню → Split/Merge (разделить/объединить) |
| Зафиксировать сигнатуру | `P` |

---

<a id="-data-types--structures"></a>
## 📊 Типы данных и структуры

<a id="create-structure"></a>
### Создание структуры

```
# Способ 1: из Data Type Manager
Data Type Manager → контекстное меню → New → Structure (структура)

# Способ 2: автоматическое создание из декомпилятора
Decompiler → контекстное меню переменной → Auto Create Structure (создать структуру автоматически)
```

<a id="define-structure"></a>
### Определение структуры

```c
// Пример структуры
struct Player {
    char name[32];      // смещение 0x00
    int health;         // смещение 0x20
    int score;          // смещение 0x24
    float position[3];  // смещение 0x28
};
```

<a id="apply-structure"></a>
### Применение структуры

```
# В представлении Listing
Выбрать адрес → контекстное меню → Data → Choose Data Type (выбрать тип данных)

# Или
Нажать T → выбрать структуру

# В Decompiler
Контекстное меню переменной → Retype Variable → выбрать структуру
```

<a id="arrays"></a>
### Массивы

```
# Создать массив
Выбрать начальный адрес → контекстное меню → Data → Create Array (массив)
Ввести число элементов
```

<a id="enums"></a>
### Перечисления

```
# Создать перечисление
Data Type Manager → контекстное меню → New → Enum (перечисление)

# Определить значения
enum Status {
    INACTIVE = 0,
    ACTIVE = 1,
    PAUSED = 2
};
```

---

<a id="-patching--modifying"></a>
## 🔧 Исправление и изменение кода

<a id="patch-instruction"></a>
### Изменение инструкции

```
# Способ 1: с клавиатуры
Выбрать инструкцию → Ctrl+Shift+G
Ввести новую инструкцию → нажать Enter

# Способ 2: через меню
Контекстное меню → Patch Instruction (изменить инструкцию)
```

<a id="patch-data"></a>
### Изменение данных

```
# Изменить байты
Контекстное меню → Patch Data (изменить данные)
Ввести байты в шестнадцатеричном виде

# Или
Выбрать байты → Ctrl+Shift+G
```

<a id="common-patches"></a>
### Типичные исправления

| Исходное значение | После исправления | Назначение |
|----------|---------|---------|
| `JE` | `JMP` | Всегда выполнять переход |
| `JNE` | `JMP` | Всегда выполнять переход |
| `JE` | `NOP` | Не выполнять переход |
| `CALL` | `NOP NOP NOP NOP NOP` | Пропустить вызов |
| `74` | `75` | JE → JNE |
| `75` | `74` | JNE → JE |
| `74` | `EB` | JE → JMP short |

<a id="export-patched-binary"></a>
### Экспорт изменённого двоичного файла

```
File → Export Program (экспорт программы)
Format: Binary (формат необработанных двоичных данных)
Выбрать файл назначения
```

<a id="nop-sled"></a>
### Последовательность NOP

```
# Заполнить инструкциями NOP
Выделить диапазон → контекстное меню → Clear Code Block
Затем: Select → контекстное меню → Patch → Fill with NOPs (заполнить NOP)
```

---

<a id="-scripting"></a>
## 🐍 Сценарии автоматизации

<a id="script-manager"></a>
### Менеджер сценариев (Script Manager)

```
Window → Script Manager (менеджер сценариев)
# Или: Tools → Script Manager
```

<a id="run-script"></a>
### Запуск сценария

```
Script Manager → выбрать сценарий → Run (зелёная кнопка запуска)
# Или дважды щёлкнуть сценарий
```

<a id="python-scripting-ghidra-python"></a>
### Сценарии Python в Ghidra

```python
# Пример: список всех функций
from ghidra.program.model.listing import Function

fm = currentProgram.getFunctionManager()
funcs = fm.getFunctions(True)  # True = в прямом порядке

for func in funcs:
    print(f"Function: {func.getName()} at {func.getEntryPoint()}")
```

<a id="common-script-operations"></a>
### Типичные операции в сценариях

```python
# Получить текущий адрес
currentAddress

# Получить текущую программу
currentProgram

# Получить менеджер функций
fm = currentProgram.getFunctionManager()

# Получить листинг (дизассемблированный код)
listing = currentProgram.getListing()

# Получить инструкцию по адресу
instr = listing.getInstructionAt(addr)

# Получить байты по адресу
memory = currentProgram.getMemory()
bytes = getBytes(addr, length)

# Создать метку
createLabel(addr, "my_label", True)

# Добавить комментарий
setEOLComment(addr, "My comment")

# Получить ссылки
refMgr = currentProgram.getReferenceManager()
refs = refMgr.getReferencesTo(addr)
```

<a id="useful-built-in-scripts"></a>
### Полезные встроенные сценарии

| Сценарий | Назначение |
|--------|---------|
| FindCrypt | Найти криптографические константы |
| FunctionID | Сопоставить библиотечные функции |
| ExportFunctionInfoScript | Экспортировать список функций |
| FindStrings | Найти ссылки на строки |
| SearchMemory | Искать шаблоны |

---

<a id="-keyboard-shortcuts"></a>
## ⌨️ Сочетания клавиш

<a id="navigation"></a>
### Навигация

| Сочетание клавиш | Действие |
|----------|--------|
| `G` | Перейти к адресу |
| `Alt+←` | Назад |
| `Alt+→` | Вперёд |
| `Ctrl+Shift+E` | Точка входа |
| `Ctrl+E` | Экспорт таблицы |
| `N` | Следующая неопределённая область |

<a id="analysis"></a>
### Анализ

| Сочетание клавиш | Действие |
|----------|--------|
| `F` | Создать функцию |
| `D` | Дизассемблировать |
| `C` | Очистить определение |
| `P` | Создать указатель |
| `T` | Применить тип данных |
| `A` | Запустить автоматический анализ |

<a id="editing"></a>
### Редактирование

| Сочетание клавиш | Действие |
|----------|--------|
| `L` | Переименовать метку или переменную |
| `;` | Добавить комментарий конца строки |
| `Ctrl+;` | Добавить общий комментарий (plate) |
| `Ctrl+L` | Изменить тип переменной |
| `Ctrl+Shift+G` | Изменить инструкцию |

<a id="search"></a>
### Поиск

| Сочетание клавиш | Действие |
|----------|--------|
| `Ctrl+Shift+F` | Найти ссылки |
| `Ctrl+F` | Поиск текста |
| `S` | Поиск в памяти |
| `Ctrl+B` | Поиск байтов |

<a id="windows-1"></a>
### Окна

| Сочетание клавиш | Действие |
|----------|--------|
| `Ctrl+D` | Показать/скрыть декомпилятор |
| `Ctrl+T` | Показать/скрыть менеджер типов данных |
| `Ctrl+S` | Дерево символов |

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="analysis-workflow"></a>
### Порядок анализа

```
1. Импортировать файл → File → Import
2. Автоанализ         → Analysis → Auto Analyze (принять настройки по умолчанию)
3. Найти вход/main    → G → main или entry
4. Просмотреть функции → Symbol Tree → Functions
5. Изучить строки     → Search → For Strings
6. Проследить выполнение → следовать вызовам и переходам
7. Переименовать символы → L на функциях и переменных
8. Документировать    → добавить комментарии (;)
```

<a id="common-tasks"></a>
### Типичные задачи

| Задача | Способ |
|------|-----|
| Найти main() | `G` → `main` или поиск в Symbol Tree |
| Просмотреть строки | Search → For Strings (поиск строк) |
| Найти криптографию | Запустить сценарий FindCrypt |
| Проверить импорты | Symbol Tree → Imports (импорты) |
| Найти перекрёстные ссылки | Выделить → Ctrl+Shift+F |
| Создать функцию | Выделить адрес → `F` |
| Декомпилировать | Window → Decompiler (окно декомпилятора) |

<a id="reversing-checklist"></a>
### Список проверок обратной разработки

```
☐ Определить тип файла (PE, ELF и т. д.)
☐ Запустить автоматический анализ
☐ Найти точку входа / main
☐ Изучить строки в поисках подсказок
☐ Найти интересующие функции
☐ Восстановить поток выполнения
☐ Проанализировать подозрительные функции
☐ Документировать находки комментариями
☐ Переименовать функции и переменные
☐ При необходимости создать структуры
```

<a id="function-calling-conventions"></a>
### Соглашения о вызове функций

| Соглашение | Возвращаемое значение | Аргументы | Кто очищает стек |
|------------|--------|------|----------------|
| **cdecl** | EAX | Стек (R→L) | Вызывающая функция |
| **stdcall** | EAX | Стек (R→L) | Вызываемая функция |
| **fastcall** | EAX | ECX, EDX, Stack | Вызываемая функция |
| **x64 Win** | RAX | RCX, RDX, R8, R9 | Вызывающая функция |
| **x64 Linux** | RAX | RDI, RSI, RDX, RCX, R8, R9 | Вызывающая функция |

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Официальный сайт Ghidra](https://ghidra-sre.org/)
- [Ghidra на GitHub](https://github.com/NationalSecurityAgency/ghidra)
- [Документация Ghidra](https://ghidra.re/ghidra_docs/api/)
- [Awesome Ghidra](https://github.com/AllsafeCyberSecurity/awesome-ghidra)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [Binwalk](../../Binwalk/translations/README.ru.md)
- [Команды Linux](../../Linux-Commands/translations/README.ru.md)
- [Volatility](../../Volatility/translations/README.ru.md)

---

<p align="center">
  <b>🔍 Освойте обратную разработку!</b><br>
  <i>Ghidra — открытый инструмент NSA для исследователей безопасности</i>
</p>
