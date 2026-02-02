# 🔍 Fase 2: Discovery / Enumeration

```
  ███████╗███╗   ██╗██╗   ██╗███╗   ███╗███████╗██████╗  █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝████╗  ██║██║   ██║████╗ ████║██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
  █████╗  ██╔██╗ ██║██║   ██║██╔████╔██║█████╗  ██████╔╝███████║   ██║   ██║██║   ██║██╔██╗ ██║
  ██╔══╝  ██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
  ███████╗██║ ╚████║╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**Obiettivo:** Raccogliere informazioni sul sistema e sulla rete compromessi per pianificare i prossimi passi.

---

## 🖥️ Enumeration Windows

### Informazioni di Sistema
```cmd
# Info di base sul sistema
systeminfo
hostname
whoami /all

# Versione OS
wmic os get caption,version,buildnumber

# Architettura
echo %PROCESSOR_ARCHITECTURE%

# Variabili d'ambiente
set
```

### Enumeration Utenti & Gruppi
```cmd
# Privilegi utente corrente
whoami /priv
whoami /groups

# Tutti gli utenti locali
net user
net user administrator

# Tutti i gruppi locali
net localgroup
net localgroup Administrators

# Utenti di dominio (se in dominio)
net user /domain
net group /domain
net group "Domain Admins" /domain
```

### Informazioni di Rete
```cmd
# Configurazione IP
ipconfig /all

# Tabella di routing
route print

# Tabella ARP
arp -a

# Connessioni attive
netstat -ano
netstat -ano | findstr ESTABLISHED
netstat -ano | findstr LISTENING

# Cache DNS
ipconfig /displaydns

# Condivisioni
net share
net view \\localhost
```

### Processi & Servizi
```cmd
# Processi in esecuzione
tasklist
tasklist /SVC
wmic process list brief

# Servizi
sc query
net start
wmic service list brief

# Task pianificati
schtasks /query /fo LIST /v
```

### Software Installato
```cmd
# Programmi installati
wmic product get name,version
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall

# Hotfix/Patch
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Credenziali & File Sensibili
```cmd
# Credenziali salvate
cmdkey /list

# Password WiFi
netsh wlan show profiles
netsh wlan show profile name="SSID" key=clear

# Cerca password in file
findstr /si password *.txt *.ini *.config *.xml
dir /s *pass* *cred* *vnc* *.config

# File unattend
dir /s C:\*unattend.xml
dir /s C:\*sysprep.inf
```

---

## 🐧 Enumeration Linux

### Informazioni di Sistema
```bash
# Info di base
hostname
uname -a
cat /etc/os-release
cat /proc/version

# Architettura
arch

# Versione kernel
uname -r
```

### Enumeration Utenti & Gruppi
```bash
# Utente corrente
whoami
id
groups

# Tutti gli utenti
cat /etc/passwd
cat /etc/shadow  # se leggibile
cat /etc/group

# Utenti loggati
w
who
last

# Permessi sudo
sudo -l
cat /etc/sudoers
```

### Informazioni di Rete
```bash
# Configurazione IP
ip a
ifconfig

# Routing
ip route
route -n

# ARP
arp -a
ip neigh

# Connessioni attive
ss -tulnp
netstat -tulnp

# DNS
cat /etc/resolv.conf
```

### Processi & Servizi
```bash
# Processi in esecuzione
ps aux
ps auxwww
top

# Servizi
systemctl list-units --type=service
service --status-all

# Cron job
cat /etc/crontab
ls -la /etc/cron.*
crontab -l
```

### Software Installati
```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Binari SUID (vettori privesc!)
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null
```

### Credenziali & File Sensibili
```bash
# Chiavi SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# File di history
cat ~/.bash_history
cat ~/.zsh_history

# Config con password
grep -r "password" /etc/ 2>/dev/null
grep -r "pass" /home/ 2>/dev/null

# File interessanti
cat /etc/fstab
cat ~/.bashrc
cat ~/.profile
```

---

## 🏢 Enumeration Active Directory

### PowerView
```powershell
# Importa modulo
Import-Module .\PowerView.ps1

# Info dominio
Get-Domain
Get-DomainController

# Utenti
Get-DomainUser
Get-DomainUser -Identity admin
Get-DomainUser | select samaccountname,description

# Gruppi
Get-DomainGroup
Get-DomainGroupMember -Identity "Domain Admins"

# Computer
Get-DomainComputer
Get-DomainComputer | select dnshostname,operatingsystem

# GPO
Get-DomainGPO
Get-DomainGPOLocalGroup
```

### BloodHound
```bash
# Raccolta dati con SharpHound
.\SharpHound.exe -c All

# O con PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound -CollectionMethod All

# Collector Python (da Linux)
bloodhound-python -u user -p 'password' -d domain.local -ns 192.168.1.1 -c All
```

### Enumerazione LDAP
```bash
# Da Linux con ldapsearch
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local"

# Utenti
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local" "(objectClass=user)"

# Con credenziali
ldapsearch -x -H ldap://192.168.1.1 -D "user@domain.local" -w 'password' -b "DC=domain,DC=local"
```

### Enumeration Kerberos
```bash
# Enumera utenti con Kerbrute
kerbrute userenum -d domain.local users.txt --dc 192.168.1.1

# Utenti AS-REP Roastable
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt

# Account Kerberoastable
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1
```

---

## 📡 Enumeration di Rete

### Scoperta Host
```bash
# Ping sweep
nmap -sn 192.168.1.0/24

# ARP scan (rete locale)
arp-scan -l

# Netdiscover
netdiscover -r 192.168.1.0/24
```

### Scansione Porte
```bash
# Scansione rapida
nmap -F 192.168.1.10

# Scansione completa
nmap -p- 192.168.1.10

# Rilevamento versione servizi
nmap -sV -sC 192.168.1.10

# Scansione UDP
nmap -sU --top-ports 100 192.168.1.10
```

### Enumeration SMB
```bash
# Enum4linux
enum4linux -a 192.168.1.10

# Condivisioni SMB
smbclient -L //192.168.1.10 -N
smbmap -H 192.168.1.10
crackmapexec smb 192.168.1.10 --shares

# Con credenziali
smbclient //192.168.1.10/share -U user
```

### Enumeration SNMP
```bash
# Community string di default
snmpwalk -v2c -c public 192.168.1.10
snmpwalk -v2c -c private 192.168.1.10

# Enumerazione con onesixtyone
onesixtyone -c community.txt 192.168.1.10
```

---

## 🛠️ Strumenti di Enumeration Automatizzata

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

## 📊 Riferimento Rapido

### Enum Veloce Windows
```cmd
systeminfo
whoami /all
net user
net localgroup Administrators
ipconfig /all
netstat -ano
tasklist /SVC
```

### Enum Veloce Linux
```bash
uname -a && id && sudo -l
cat /etc/passwd
ps aux
netstat -tulnp
find / -perm -4000 2>/dev/null
```

---

## 🔗 Cheatsheet Correlate

- [Nmap](../Nmap/translations/README.it.md)
- [BloodHound](../BloodHound/translations/README.it.md)
- [PowerView](../PowerView/translations/README.it.md)
- [CrackMapExec](../CrackMapExec/translations/README.it.md)

---

**Fase Precedente:** [← 01 - Initial Access](./01-Initial-Access.it.md)

**Fase Successiva:** [03 - Privilege Escalation →](./03-Privilege-Escalation.it.md)
