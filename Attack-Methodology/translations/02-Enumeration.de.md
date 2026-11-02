# 🔍 Phase 2: Erkundung / Enumeration

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](02-Enumeration.tr.md) • [🇬🇷 Ελληνικά](02-Enumeration.el.md)

```
  ███████╗███╗   ██╗██╗   ██╗███╗   ███╗███████╗██████╗  █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝████╗  ██║██║   ██║████╗ ████║██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
  █████╗  ██╔██╗ ██║██║   ██║██╔████╔██║█████╗  ██████╔╝███████║   ██║   ██║██║   ██║██╔██╗ ██║
  ██╔══╝  ██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
  ███████╗██║ ╚████║╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**Ziel:** Informationen über das kompromittierte System und Netzwerk sammeln, um die nächsten Schritte zu planen.

---

## 🖥️ Windows-Enumeration

### Systeminformationen
```cmd
# Grundlegende Systeminformationen
systeminfo
hostname
whoami /all

# Betriebssystemversion
wmic os get caption,version,buildnumber

# Architektur
echo %PROCESSOR_ARCHITECTURE%

# Umgebungsvariablen
set
```

### Benutzer und Gruppen ermitteln
```cmd
# Rechte des aktuellen Benutzers
whoami /priv
whoami /groups

# Alle lokalen Benutzer
net user
net user administrator

# Alle lokalen Gruppen
net localgroup
net localgroup Administrators

# Domänenbenutzer (bei Domänenmitgliedschaft)
net user /domain
net group /domain
net group "Domain Admins" /domain
```

### Netzwerkinformationen
```cmd
# IP-Konfiguration
ipconfig /all

# Routingtabelle
route print

# ARP-Tabelle
arp -a

# Aktive Verbindungen
netstat -ano
netstat -ano | findstr ESTABLISHED
netstat -ano | findstr LISTENING

# DNS-Cache
ipconfig /displaydns

# Freigaben
net share
net view \\localhost
```

### Prozesse und Dienste
```cmd
# Laufende Prozesse
tasklist
tasklist /SVC
wmic process list brief

# Dienste
sc query
net start
wmic service list brief

# Geplante Aufgaben
schtasks /query /fo LIST /v
```

### Installierte Software
```cmd
# Installierte Programme
wmic product get name,version
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall

# Hotfixes/Patches
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Zugangsdaten und vertrauliche Dateien
```cmd
# Gespeicherte Zugangsdaten
cmdkey /list

# WLAN-Passwörter
netsh wlan show profiles
netsh wlan show profile name="SSID" key=clear

# In Dateien nach Passwörtern suchen
findstr /si password *.txt *.ini *.config *.xml
dir /s *pass* *cred* *vnc* *.config

# Unattend-Dateien
dir /s C:\*unattend.xml
dir /s C:\*sysprep.inf
```

---

## 🐧 Linux-Enumeration

### Systeminformationen
```bash
# Grundlegende Informationen
hostname
uname -a
cat /etc/os-release
cat /proc/version

# Architektur
arch

# Kernel-Version
uname -r
```

### Benutzer und Gruppen ermitteln
```bash
# Aktueller Benutzer
whoami
id
groups

# Alle Benutzer
cat /etc/passwd
cat /etc/shadow  # falls lesbar
cat /etc/group

# Angemeldete Benutzer
w
who
last

# Sudo-Berechtigungen
sudo -l
cat /etc/sudoers
```

### Netzwerkinformationen
```bash
# IP-Konfiguration
ip a
ifconfig

# Routing
ip route
route -n

# ARP
arp -a
ip neigh

# Aktive Verbindungen
ss -tulnp
netstat -tulnp

# DNS
cat /etc/resolv.conf
```

### Prozesse und Dienste
```bash
# Laufende Prozesse
ps aux
ps auxwww
top

# Dienste
systemctl list-units --type=service
service --status-all

# Cronjobs
cat /etc/crontab
ls -la /etc/cron.*
crontab -l
```

### Installierte Software
```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# SUID-Programme (Ansatzpunkte für Rechteausweitung!)
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null
```

### Zugangsdaten und vertrauliche Dateien
```bash
# SSH-Schlüssel
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Verlaufsdateien
cat ~/.bash_history
cat ~/.zsh_history

# Konfigurationsdateien mit Passwörtern
grep -r "password" /etc/ 2>/dev/null
grep -r "pass" /home/ 2>/dev/null

# Interessante Dateien
cat /etc/fstab
cat ~/.bashrc
cat ~/.profile
```

---

## 🏢 Active-Directory-Enumeration

### PowerView
```powershell
# Modul importieren
Import-Module .\PowerView.ps1

# Domäneninformationen abrufen
Get-Domain
Get-DomainController

# Benutzer abrufen
Get-DomainUser
Get-DomainUser -Identity admin
Get-DomainUser | select samaccountname,description

# Gruppen abrufen
Get-DomainGroup
Get-DomainGroupMember -Identity "Domain Admins"

# Computer abrufen
Get-DomainComputer
Get-DomainComputer | select dnshostname,operatingsystem

# Gruppenrichtlinienobjekte (GPOs) abrufen
Get-DomainGPO
Get-DomainGPOLocalGroup
```

### BloodHound
```bash
# Daten mit SharpHound sammeln
.\SharpHound.exe -c All

# Alternativ mit PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound -CollectionMethod All

# Python-Collector (unter Linux)
bloodhound-python -u user -p 'password' -d domain.local -ns 192.168.1.1 -c All
```

### LDAP-Enumeration
```bash
# Unter Linux mit ldapsearch
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local"

# Benutzer
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local" "(objectClass=user)"

# Mit Zugangsdaten
ldapsearch -x -H ldap://192.168.1.1 -D "user@domain.local" -w 'password' -b "DC=domain,DC=local"
```

### Kerberos-Enumeration
```bash
# Benutzer mit Kerbrute ermitteln
kerbrute userenum -d domain.local users.txt --dc 192.168.1.1

# Für AS-REP Roasting geeignete Benutzer
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt

# Für Kerberoasting geeignete Konten
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1
```

---

## 📡 Netzwerk-Enumeration

### Hosts ermitteln
```bash
# Ping-Sweep
nmap -sn 192.168.1.0/24

# ARP-Scan (lokales Netzwerk)
arp-scan -l

# Netdiscover
netdiscover -r 192.168.1.0/24
```

### Portscans
```bash
# Schneller Scan
nmap -F 192.168.1.10

# Scan aller Ports
nmap -p- 192.168.1.10

# Dienstversionen erkennen
nmap -sV -sC 192.168.1.10

# UDP-Scan
nmap -sU --top-ports 100 192.168.1.10
```

### SMB-Enumeration
```bash
# Enum4linux
enum4linux -a 192.168.1.10

# SMB-Freigaben
smbclient -L //192.168.1.10 -N
smbmap -H 192.168.1.10
crackmapexec smb 192.168.1.10 --shares

# Mit Zugangsdaten
smbclient //192.168.1.10/share -U user
```

### SNMP-Enumeration
```bash
# Standard-Community-Strings
snmpwalk -v2c -c public 192.168.1.10
snmpwalk -v2c -c private 192.168.1.10

# Mit onesixtyone ermitteln
onesixtyone -c community.txt 192.168.1.10
```

---

## 🛠️ Werkzeuge zur automatisierten Enumeration

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

## 📊 Kurzreferenz

### Windows-Enumeration auf einen Blick
```cmd
systeminfo
whoami /all
net user
net localgroup Administrators
ipconfig /all
netstat -ano
tasklist /SVC
```

### Linux-Enumeration auf einen Blick
```bash
uname -a && id && sudo -l
cat /etc/passwd
ps aux
netstat -tulnp
find / -perm -4000 2>/dev/null
```

---

## 🔗 Weiterführende Cheatsheets

- [Nmap](../../Nmap/README.md)
- [BloodHound](../../BloodHound/README.md)
- [PowerView](../../PowerView/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)

---

**Vorherige Phase:** [← 01 - Erstzugang (Initial Access)](./01-Initial-Access.de.md)

**Nächste Phase:** [03 - Rechteausweitung (Privilege Escalation) →](./03-Privilege-Escalation.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Hinweis zu den Beispielen:** `#` ist in `cmd.exe` kein Kommentarzeichen; die so markierten Zeilen dienen hier nur als Erläuterungen. Gruppenbezeichnungen wie `Administrators` und `Domain Admins` können auf lokalisierten Systemen abweichen. Die Originalbefehle und Namen bleiben unverändert. `2024.xlsx` bezeichnet eine vorausgesetzte Datenbankdatei, die diese Anleitung nicht mitliefert. Die erste `find`-Abfrage unter installierter Software findet SUID-, die zweite SGID-Programme.
