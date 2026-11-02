# 🔄 Шпаргалка по обратной разработке в CTF

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Reverse-Engineering-CTF.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Уточнение интерфейса:** упоминание F5 в разделе Ghidra передано из оригинала. В [официальном руководстве Ghidra](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html) окно декомпилятора открывается через Window → Decompiler; горячие клавиши зависят от инструмента и настроек. Поддержка псевдокода в IDA зависит от доступного декомпилятора и архитектуры. Названия меню и команд оставлены на английском для сопоставления с интерфейсом.

[🇹🇷 Türkçe Sürüm](Reverse-Engineering-CTF.tr.md) • [🇬🇷 Ελληνικά](Reverse-Engineering-CTF.el.md)

---

## 🔍 Первоначальный анализ

```bash
# Тип файла
file binary

# Строки
strings binary | grep -i flag
strings -n 8 binary

# ELF info
readelf -h binary

# Проверить механизмы защиты
checksec binary
```

---

## 🛠️ Дизассемблеры

### Ghidra (бесплатно)
```
1. File → Import File
2. Analyze → Auto Analyze
3. Найти функцию main()
4. Найти проверки пароля через strcmp/memcmp
5. F5 для декомпиляции (как указано в оригинале; см. уточнение)
```

### IDA Free
```
1. Открыть исполняемый файл
2. F5 для просмотра псевдокода (Hex-Rays)
3. X для поиска перекрёстных ссылок
4. N для переименования переменных
```

### Radare2
```bash
r2 binary
aaa          # Проанализировать
afl          # Список функций
pdf @main    # Дизассемблировать main
VV           # Визуальный режим
```

---

## 🐛 Отладка с помощью GDB

```bash
# Запуск
gdb ./binary

# Точки останова
b main
b *0x401234

# Выполнение
r
r arg1 arg2

# Пошаговое выполнение
n           # Следующая строка
s           # Шаг с заходом
c           # Продолжить

# Просмотр памяти и регистров
x/s $rdi    # Строка
x/10x $rsp  # 10 шестнадцатеричных значений из стека
info reg    # Регистры

# Найти флаг
find 0x0, 0xffffffff, "flag"
```

### GEF/PEDA
```bash
# Установить GEF
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Команды
vmmap
heap
telescope $rsp
```

---

## 🐍 Изменение бинарного файла с помощью Python

```python
# Прочитать бинарный файл
with open("binary", "rb") as f:
    data = bytearray(f.read())

# Изменить байты
data[0x1234] = 0x90  # NOP

# Запись
with open("patched", "wb") as f:
    f.write(data)
```

---

## 📊 Типичные проверки

| Проверка | Способ обхода |
|-------|--------|
| `strcmp(input, "password")` | Извлечь строку "password" |
| `if (x == 0x1337)` | Найти значение 0x1337 |
| Защита от отладки | Изменить вызов ptrace |
| Обфусцированные строки | Динамический анализ |

---

## 📋 Контрольный список обратной разработки

```markdown
□ Выполнить команды file/strings
□ Проверить наличие отладочных символов
□ Открыть файл в Ghidra/IDA
□ Найти функцию main
□ Найти сравнения строк
□ Проследить обработку пользовательского ввода
□ При необходимости изменить проверки защиты от отладки
□ Выполнить динамический анализ в GDB
```

---

**К разделу CTF:** [🏁 Обзор CTF](./README.ru.md)
