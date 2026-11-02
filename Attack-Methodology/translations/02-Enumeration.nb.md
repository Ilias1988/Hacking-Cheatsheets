# 🔍 Fase 2: Oppdagelse og kartlegging

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](02-Enumeration.tr.md) • [🇬🇷 Ελληνικά](02-Enumeration.el.md)

```
  ███████╗███╗   ██╗██╗   ██╗███╗   ███╗███████╗██████╗  █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝████╗  ██║██║   ██║████╗ ████║██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
  █████╗  ██╔██╗ ██║██║   ██║██╔████╔██║█████╗  ██████╔╝███████║   ██║   ██║██║   ██║██╔██╗ ██║
  ██╔══╝  ██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
  ███████╗██║ ╚████║╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**Mål:** Samle informasjon om det kompromitterte systemet og nettverket for å planlegge neste steg.

---

## 🖥️ Kartlegging av Windows

> **Versjonsmerknad:** WMIC er utfaset fra Windows 10 21H1. WMI-teknologien er fortsatt tilgjengelig, men `wmic` kan mangle på nyere installasjoner. Se [Microsofts WMIC-dokumentasjon](https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/wmic).

### Systeminformasjon
```cmd
# Grunnleggende systeminformasjon
systeminfo
hostname
whoami /all

# OS-versjon
wmic os get caption,version,buildnumber

# Arkitektur
echo %PROCESSOR_ARCHITECTURE%

# Miljøvariabler
set
```

### Kartlegging av brukere og grupper
```cmd
# Gjeldende brukers rettigheter
whoami /priv
whoami /groups

# Alle lokale brukere
net user
net user administrator

# Alle lokale grupper
net localgroup
net localgroup Administrators

# Domenebrukere (hvis maskinen er domenetilknyttet)
net user /domain
net group /domain
net group "Domain Admins" /domain
```

### Nettverksinformasjon
```cmd
# IP-konfigurasjon
ipconfig /all

# Rutingtabell
route print

# ARP-tabell
arp -a

# Aktive forbindelser
netstat -ano
netstat -ano | findstr ESTABLISHED
netstat -ano | findstr LISTENING

# DNS-hurtigbuffer
ipconfig /displaydns

# Delte ressurser
net share
net view \\localhost
```

### Prosesser og tjenester
```cmd
# Prosesser som kjører
tasklist
tasklist /SVC
wmic process list brief

# Tjenester
sc query
net start
wmic service list brief

# Planlagte oppgaver
schtasks /query /fo LIST /v
```

### Installert programvare
```cmd
# Installerte programmer
wmic product get name,version
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall

# Hurtigreparasjoner og oppdateringer
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Påloggingsopplysninger og sensitive filer
```cmd
# Lagrede påloggingsopplysninger
cmdkey /list

# WiFi-passord
netsh wlan show profiles
netsh wlan show profile name="SSID" key=clear

# Søk etter passord i filer
findstr /si password *.txt *.ini *.config *.xml
dir /s *pass* *cred* *vnc* *.config

# Unattend-filer
dir /s C:\*unattend.xml
dir /s C:\*sysprep.inf
```

---

## 🐧 Kartlegging av Linux

### Systeminformasjon
```bash
# Grunnleggende informasjon
hostname
uname -a
cat /etc/os-release
cat /proc/version

# Arkitektur
arch

# Kjerneversjon
uname -r
```

### Kartlegging av brukere og grupper
```bash
# Gjeldende bruker
whoami
id
groups

# Alle brukere
cat /etc/passwd
cat /etc/shadow  # hvis filen kan leses
cat /etc/group

# Innloggede brukere
w
who
last

# Sudo-tillatelser
sudo -l
cat /etc/sudoers
```

### Nettverksinformasjon
```bash
# IP-konfigurasjon
ip a
ifconfig

# Ruting
ip route
route -n

# ARP
arp -a
ip neigh

# Aktive forbindelser
ss -tulnp
netstat -tulnp

# DNS
cat /etc/resolv.conf
```

### Prosesser og tjenester
```bash
# Prosesser som kjører
ps aux
ps auxwww
top

# Tjenester
systemctl list-units --type=service
service --status-all

# Cron-jobber
cat /etc/crontab
ls -la /etc/cron.*
crontab -l
```

### Installert programvare

> **Presisering:** `-perm -4000` finner SUID-filer; `-perm -2000` finner SGID-filer. Originalens samleoverskrift omtaler bare SUID.

```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# SUID-binærfiler (muligheter for rettighetseskalering!)
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null
```

### Påloggingsopplysninger og sensitive filer
```bash
# SSH-nøkler
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Historikkfiler
cat ~/.bash_history
cat ~/.zsh_history

# Konfigurasjonsfiler med passord
grep -r "password" /etc/ 2>/dev/null
grep -r "pass" /home/ 2>/dev/null

# Interessante filer
cat /etc/fstab
cat ~/.bashrc
cat ~/.profile
```

---

## 🏢 Kartlegging av Active Directory

### PowerView
```powershell
# Importer modul
Import-Module .\PowerView.ps1

# Hent domeneinformasjon
Get-Domain
Get-DomainController

# Hent brukere
Get-DomainUser
Get-DomainUser -Identity admin
Get-DomainUser | select samaccountname,description

# Hent grupper
Get-DomainGroup
Get-DomainGroupMember -Identity "Domain Admins"

# Hent datamaskiner
Get-DomainComputer
Get-DomainComputer | select dnshostname,operatingsystem

# Hent gruppepolicyobjekter (GPO-er)
Get-DomainGPO
Get-DomainGPOLocalGroup
```

### BloodHound
```bash
# Samle data med SharpHound
.\SharpHound.exe -c All

# Eller med PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound -CollectionMethod All

# Python-innsamler (fra Linux)
bloodhound-python -u user -p 'password' -d domain.local -ns 192.168.1.1 -c All
```

### Kartlegging via LDAP
```bash
# Fra Linux med ldapsearch
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local"

# Brukere
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local" "(objectClass=user)"

# Med påloggingsopplysninger
ldapsearch -x -H ldap://192.168.1.1 -D "user@domain.local" -w 'password' -b "DC=domain,DC=local"
```

### Kartlegging via Kerberos
```bash
# Kartlegg brukere med Kerbrute
kerbrute userenum -d domain.local users.txt --dc 192.168.1.1

# Brukere som er utsatt for AS-REP roasting
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt

# Kontoer som er utsatt for Kerberoasting
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1
```

---

## 📡 Nettverkskartlegging

### Oppdagelse av verter
```bash
# Ping-sveip
nmap -sn 192.168.1.0/24

# ARP-skanning (lokalt nettverk)
arp-scan -l

# Netdiscover
netdiscover -r 192.168.1.0/24
```

### Portskanning
```bash
# Rask skanning
nmap -F 192.168.1.10

# Full portskanning
nmap -p- 192.168.1.10

# Identifiser tjenesteversjoner
nmap -sV -sC 192.168.1.10

# UDP-skanning
nmap -sU --top-ports 100 192.168.1.10
```

### Kartlegging av SMB
```bash
# Enum4linux
enum4linux -a 192.168.1.10

# SMB-ressurser
smbclient -L //192.168.1.10 -N
smbmap -H 192.168.1.10
crackmapexec smb 192.168.1.10 --shares

# Med påloggingsopplysninger
smbclient //192.168.1.10/share -U user
```

### Kartlegging av SNMP
```bash
# Standard community-strenger
snmpwalk -v2c -c public 192.168.1.10
snmpwalk -v2c -c private 192.168.1.10

# Kartlegg med onesixtyone
onesixtyone -c community.txt 192.168.1.10
```

---

## 🛠️ Verktøy for automatisert kartlegging

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

## 📊 Hurtigreferanse

### Rask kartlegging av Windows
```cmd
systeminfo
whoami /all
net user
net localgroup Administrators
ipconfig /all
netstat -ano
tasklist /SVC
```

### Rask kartlegging av Linux
```bash
uname -a && id && sudo -l
cat /etc/passwd
ps aux
netstat -tulnp
find / -perm -4000 2>/dev/null
```

---

## 🔗 Relaterte hurtigguider

- [Nmap](../../Nmap/README.md)
- [BloodHound](../../BloodHound/README.md)
- [PowerView](../../PowerView/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)

---

**Forrige fase:** [← 01 - Innledende tilgang](./01-Initial-Access.nb.md)

**Neste fase:** [03 - Rettighetseskalering →](./03-Privilege-Escalation.nb.md)
