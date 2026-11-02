# 🔍 Этап 2: Обнаружение и перечисление

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[Английский оригинал](../02-Enumeration.md) • [Русский указатель](../../README.ru.md)
[🇹🇷 Türkçe Sürüm](02-Enumeration.tr.md) • [🇬🇷 Ελληνικά](02-Enumeration.el.md)

```
  ███████╗███╗   ██╗██╗   ██╗███╗   ███╗███████╗██████╗  █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝████╗  ██║██║   ██║████╗ ████║██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
  █████╗  ██╔██╗ ██║██║   ██║██╔████╔██║█████╗  ██████╔╝███████║   ██║   ██║██║   ██║██╔██╗ ██║
  ██╔══╝  ██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
  ███████╗██║ ╚████║╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**Цель:** Собрать сведения о скомпрометированной системе и сети для планирования дальнейших действий.

---

## 🖥️ Перечисление в Windows

### Сведения о системе
```cmd
# Основные сведения о системе
systeminfo
hostname
whoami /all

# Версия ОС
wmic os get caption,version,buildnumber

# Архитектура
echo %PROCESSOR_ARCHITECTURE%

# Переменные окружения
set
```

### Перечисление пользователей и групп
```cmd
# Привилегии текущего пользователя
whoami /priv
whoami /groups

# Все локальные пользователи
net user
net user administrator

# Все локальные группы
net localgroup
net localgroup Administrators

# Пользователи домена (если система включена в домен)
net user /domain
net group /domain
net group "Domain Admins" /domain
```

### Сведения о сети
```cmd
# Конфигурация IP
ipconfig /all

# Таблица маршрутизации
route print

# Таблица ARP
arp -a

# Активные соединения
netstat -ano
netstat -ano | findstr ESTABLISHED
netstat -ano | findstr LISTENING

# Кэш DNS
ipconfig /displaydns

# Общие ресурсы
net share
net view \\localhost
```

### Процессы и службы
```cmd
# Работающие процессы
tasklist
tasklist /SVC
wmic process list brief

# Службы
sc query
net start
wmic service list brief

# Запланированные задания
schtasks /query /fo LIST /v
```

### Установленное ПО
```cmd
# Установленные программы
wmic product get name,version
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall

# Исправления и обновления
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Учётные данные и конфиденциальные файлы
```cmd
# Сохранённые учётные данные
cmdkey /list

# Пароли Wi-Fi
netsh wlan show profiles
netsh wlan show profile name="SSID" key=clear

# Поиск паролей в файлах
findstr /si password *.txt *.ini *.config *.xml
dir /s *pass* *cred* *vnc* *.config

# Файлы автоматической установки Unattend
dir /s C:\*unattend.xml
dir /s C:\*sysprep.inf
```

---

## 🐧 Перечисление в Linux

### Сведения о системе
```bash
# Основные сведения
hostname
uname -a
cat /etc/os-release
cat /proc/version

# Архитектура
arch

# Версия ядра
uname -r
```

### Перечисление пользователей и групп
```bash
# Текущий пользователь
whoami
id
groups

# Все пользователи
cat /etc/passwd
cat /etc/shadow  # если файл доступен для чтения
cat /etc/group

# Пользователи, вошедшие в систему
w
who
last

# Разрешения sudo
sudo -l
cat /etc/sudoers
```

### Сведения о сети
```bash
# Конфигурация IP
ip a
ifconfig

# Маршрутизация
ip route
route -n

# ARP
arp -a
ip neigh

# Активные соединения
ss -tulnp
netstat -tulnp

# DNS
cat /etc/resolv.conf
```

### Процессы и службы
```bash
# Работающие процессы
ps aux
ps auxwww
top

# Службы
systemctl list-units --type=service
service --status-all

# Задания cron
cat /etc/crontab
ls -la /etc/cron.*
crontab -l
```

### Установленное ПО
```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Исполняемые файлы SUID (векторы повышения привилегий!)
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null
```

### Учётные данные и конфиденциальные файлы
```bash
# Ключи SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Файлы истории команд
cat ~/.bash_history
cat ~/.zsh_history

# Файлы конфигурации с паролями
grep -r "password" /etc/ 2>/dev/null
grep -r "pass" /home/ 2>/dev/null

# Файлы, заслуживающие внимания
cat /etc/fstab
cat ~/.bashrc
cat ~/.profile
```

---

## 🏢 Перечисление в Active Directory

### PowerView
```powershell
# Импорт модуля
Import-Module .\PowerView.ps1

# Получить сведения о домене
Get-Domain
Get-DomainController

# Получить пользователей
Get-DomainUser
Get-DomainUser -Identity admin
Get-DomainUser | select samaccountname,description

# Получить группы
Get-DomainGroup
Get-DomainGroupMember -Identity "Domain Admins"

# Получить компьютеры
Get-DomainComputer
Get-DomainComputer | select dnshostname,operatingsystem

# Получить объекты групповой политики (GPO)
Get-DomainGPO
Get-DomainGPOLocalGroup
```

### BloodHound
```bash
# Собрать данные с помощью SharpHound
.\SharpHound.exe -c All

# Или через PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound -CollectionMethod All

# Сборщик на Python (из Linux)
bloodhound-python -u user -p 'password' -d domain.local -ns 192.168.1.1 -c All
```

### Перечисление через LDAP
```bash
# Из Linux с помощью ldapsearch
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local"

# Пользователи
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local" "(objectClass=user)"

# С учётными данными
ldapsearch -x -H ldap://192.168.1.1 -D "user@domain.local" -w 'password' -b "DC=domain,DC=local"
```

### Перечисление через Kerberos
```bash
# Перечислить пользователей с помощью Kerbrute
kerbrute userenum -d domain.local users.txt --dc 192.168.1.1

# Пользователи, подверженные AS-REP Roasting
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt

# Учётные записи, подверженные Kerberoasting
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1
```

---

## 📡 Перечисление сетевых ресурсов

### Обнаружение узлов
```bash
# Обнаружение узлов с помощью ping
nmap -sn 192.168.1.0/24

# ARP-сканирование локальной сети
arp-scan -l

# Netdiscover
netdiscover -r 192.168.1.0/24
```

### Сканирование портов
```bash
# Быстрое сканирование
nmap -F 192.168.1.10

# Сканирование всех портов
nmap -p- 192.168.1.10

# Определение версий служб
nmap -sV -sC 192.168.1.10

# Сканирование UDP
nmap -sU --top-ports 100 192.168.1.10
```

### Перечисление через SMB
```bash
# Enum4linux
enum4linux -a 192.168.1.10

# Общие ресурсы SMB
smbclient -L //192.168.1.10 -N
smbmap -H 192.168.1.10
crackmapexec smb 192.168.1.10 --shares

# С учётными данными
smbclient //192.168.1.10/share -U user
```

### Перечисление через SNMP
```bash
# Строки community по умолчанию
snmpwalk -v2c -c public 192.168.1.10
snmpwalk -v2c -c private 192.168.1.10

# Перечисление с помощью onesixtyone
onesixtyone -c community.txt 192.168.1.10
```

---

## 🛠️ Инструменты автоматического перечисления

### Windows
```bash
# WinPEAS
.\winPEAS.exe

# Seatbelt
.\Seatbelt.exe -group=all

# PowerUp
Import-Module .\PowerUp.ps1
Invoke-AllChecks

# Windows-Exploit-Suggester
python windows-exploit-suggester.py --database 2024.xlsx --systeminfo systeminfo.txt
```

### Linux
```bash
# LinPEAS
./linpeas.sh

# LinEnum
./LinEnum.sh

# Linux-exploit-suggester
./linux-exploit-suggester.sh

# LSE (автоматическое перечисление в Linux)
./lse.sh -l 2
```

---

## 📊 Краткий справочник

### Быстрое перечисление в Windows
```cmd
systeminfo
whoami /all
net user
net localgroup Administrators
ipconfig /all
netstat -ano
tasklist /SVC
```

### Быстрое перечисление в Linux
```bash
uname -a && id && sudo -l
cat /etc/passwd
ps aux
netstat -tulnp
find / -perm -4000 2>/dev/null
```

---

## 🔗 Связанные шпаргалки

- [Nmap](../../Nmap/README.md)
- [BloodHound](../../BloodHound/README.md)
- [PowerView](../../PowerView/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)

---

**Предыдущий этап:** [← 01 - Первоначальный доступ](./01-Initial-Access.ru.md)

**Следующий этап:** [03 - Повышение привилегий →](./03-Privilege-Escalation.ru.md)
