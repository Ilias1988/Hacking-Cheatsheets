# 🔍 Phase 2: Découverte / énumération

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](02-Enumeration.tr.md) • [🇬🇷 Ελληνικά](02-Enumeration.el.md)

```
  ███████╗███╗   ██╗██╗   ██╗███╗   ███╗███████╗██████╗  █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝████╗  ██║██║   ██║████╗ ████║██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
  █████╗  ██╔██╗ ██║██║   ██║██╔████╔██║█████╗  ██████╔╝███████║   ██║   ██║██║   ██║██╔██╗ ██║
  ██╔══╝  ██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
  ███████╗██║ ╚████║╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**Objectif :** Recueillir des informations sur le système et le réseau compromis pour préparer les étapes suivantes.

> **Précision :** les lignes commençant par `#` dans les blocs `cmd` sont des annotations pédagogiques ; elles ne constituent pas des commentaires valides dans `cmd.exe`. Les exemples WMIC et les anciens collecteurs sont conservés pour correspondre au texte source ; leur disponibilité et leur compatibilité dépendent de l’environnement.

---

## 🖥️ Énumération sous Windows

### Informations système
```cmd
# Informations système de base
systeminfo
hostname
whoami /all

# Version du système d’exploitation
wmic os get caption,version,buildnumber

# Architecture
echo %PROCESSOR_ARCHITECTURE%

# Variables d’environnement
set
```

### Énumération des utilisateurs et des groupes
```cmd
# Privilèges de l’utilisateur courant
whoami /priv
whoami /groups

# Tous les utilisateurs locaux
net user
net user administrator

# Tous les groupes locaux
net localgroup
net localgroup Administrators

# Utilisateurs du domaine (si la machine est jointe à un domaine)
net user /domain
net group /domain
net group "Domain Admins" /domain
```

### Informations réseau
```cmd
# Configuration IP
ipconfig /all

# Table de routage
route print

# Table ARP
arp -a

# Connexions actives
netstat -ano
netstat -ano | findstr ESTABLISHED
netstat -ano | findstr LISTENING

# Cache DNS
ipconfig /displaydns

# Partages
net share
net view \\localhost
```

### Processus et services
```cmd
# Processus en cours
tasklist
tasklist /SVC
wmic process list brief

# Services
sc query
net start
wmic service list brief

# Tâches planifiées
schtasks /query /fo LIST /v
```

### Logiciels installés
```cmd
# Programmes installés
wmic product get name,version
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall

# Correctifs et mises à jour
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Identifiants et fichiers sensibles
```cmd
# Identifiants enregistrés
cmdkey /list

# Mots de passe Wi-Fi
netsh wlan show profiles
netsh wlan show profile name="SSID" key=clear

# Rechercher des mots de passe dans les fichiers
findstr /si password *.txt *.ini *.config *.xml
dir /s *pass* *cred* *vnc* *.config

# Fichiers Unattend
dir /s C:\*unattend.xml
dir /s C:\*sysprep.inf
```

---

## 🐧 Énumération sous Linux

### Informations système
```bash
# Informations de base
hostname
uname -a
cat /etc/os-release
cat /proc/version

# Architecture
arch

# Version du noyau
uname -r
```

### Énumération des utilisateurs et des groupes
```bash
# Utilisateur courant
whoami
id
groups

# Tous les utilisateurs
cat /etc/passwd
cat /etc/shadow  # si le fichier est lisible
cat /etc/group

# Utilisateurs connectés
w
who
last

# Autorisations sudo
sudo -l
cat /etc/sudoers
```

### Informations réseau
```bash
# Configuration IP
ip a
ifconfig

# Routage
ip route
route -n

# ARP
arp -a
ip neigh

# Connexions actives
ss -tulnp
netstat -tulnp

# DNS
cat /etc/resolv.conf
```

### Processus et services
```bash
# Processus en cours
ps aux
ps auxwww
top

# Services
systemctl list-units --type=service
service --status-all

# Tâches cron
cat /etc/crontab
ls -la /etc/cron.*
crontab -l
```

### Logiciels installés
```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Binaires SUID (vecteurs d’élévation de privilèges !)
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null
```

### Identifiants et fichiers sensibles
```bash
# Clés SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Fichiers d’historique
cat ~/.bash_history
cat ~/.zsh_history

# Fichiers de configuration contenant des mots de passe
grep -r "password" /etc/ 2>/dev/null
grep -r "pass" /home/ 2>/dev/null

# Fichiers intéressants
cat /etc/fstab
cat ~/.bashrc
cat ~/.profile
```

---

## 🏢 Énumération d’Active Directory

### PowerView
```powershell
# Importer le module
Import-Module .\PowerView.ps1

# Obtenir les informations du domaine
Get-Domain
Get-DomainController

# Obtenir les utilisateurs
Get-DomainUser
Get-DomainUser -Identity admin
Get-DomainUser | select samaccountname,description

# Obtenir les groupes
Get-DomainGroup
Get-DomainGroupMember -Identity "Domain Admins"

# Obtenir les ordinateurs
Get-DomainComputer
Get-DomainComputer | select dnshostname,operatingsystem

# Obtenir les objets de stratégie de groupe (GPO)
Get-DomainGPO
Get-DomainGPOLocalGroup
```

### BloodHound
```bash
# Collecter les données avec SharpHound
.\SharpHound.exe -c All

# Ou avec PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound -CollectionMethod All

# Collecteur Python (depuis Linux)
bloodhound-python -u user -p 'password' -d domain.local -ns 192.168.1.1 -c All
```

### Énumération LDAP
```bash
# Depuis Linux avec ldapsearch
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local"

# Utilisateurs
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local" "(objectClass=user)"

# Avec des identifiants
ldapsearch -x -H ldap://192.168.1.1 -D "user@domain.local" -w 'password' -b "DC=domain,DC=local"
```

### Énumération Kerberos
```bash
# Énumérer les utilisateurs avec Kerbrute
kerbrute userenum -d domain.local users.txt --dc 192.168.1.1

# Utilisateurs exposés à l’AS-REP Roasting
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt

# Comptes exposés au Kerberoasting
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1
```

---

## 📡 Énumération du réseau

### Découverte des hôtes
```bash
# Balayage par ping
nmap -sn 192.168.1.0/24

# Balayage ARP (réseau local)
arp-scan -l

# Netdiscover
netdiscover -r 192.168.1.0/24
```

### Balayage des ports
```bash
# Balayage rapide
nmap -F 192.168.1.10

# Balayage de tous les ports
nmap -p- 192.168.1.10

# Détection des versions des services
nmap -sV -sC 192.168.1.10

# Balayage UDP
nmap -sU --top-ports 100 192.168.1.10
```

### Énumération SMB
```bash
# Enum4linux
enum4linux -a 192.168.1.10

# Partages SMB
smbclient -L //192.168.1.10 -N
smbmap -H 192.168.1.10
crackmapexec smb 192.168.1.10 --shares

# Avec des identifiants
smbclient //192.168.1.10/share -U user
```

### Énumération SNMP
```bash
# Chaînes de communauté par défaut
snmpwalk -v2c -c public 192.168.1.10
snmpwalk -v2c -c private 192.168.1.10

# Énumérer avec onesixtyone
onesixtyone -c community.txt 192.168.1.10
```

---

## 🛠️ Outils d’énumération automatisée

### Windows
```bash
# WinPEAS
.\winPEAS.exe

# Seatbelt
.\Seatbelt.exe -group=all

# PowerUp
Import-Module .\PowerUp.ps1
Invoke-AllChecks

# Windows-Exploit-Suggester
python windows-exploit-suggester.py --database 2024.xlsx --systeminfo systeminfo.txt
```

### Linux
```bash
# LinPEAS
./linpeas.sh

# LinEnum
./LinEnum.sh

# Linux-exploit-suggester
./linux-exploit-suggester.sh

# LSE (Linux Smart Enumeration)
./lse.sh -l 2
```

---

## 📊 Aide-mémoire

### Énumération rapide sous Windows
```cmd
systeminfo
whoami /all
net user
net localgroup Administrators
ipconfig /all
netstat -ano
tasklist /SVC
```

### Énumération rapide sous Linux
```bash
uname -a && id && sudo -l
cat /etc/passwd
ps aux
netstat -tulnp
find / -perm -4000 2>/dev/null
```

---

## 🔗 Fiches associées

- [Nmap](../../Nmap/README.md)
- [BloodHound](../../BloodHound/README.md)
- [PowerView](../../PowerView/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)

---

**Phase précédente :** [← 01 - Accès initial](./01-Initial-Access.fr.md)

**Phase suivante :** [03 - Élévation de privilèges →](./03-Privilege-Escalation.fr.md)
