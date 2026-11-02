# 🔍 Fase 2: Descubrimiento / enumeración

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](02-Enumeration.tr.md) • [🇬🇷 Ελληνικά](02-Enumeration.el.md)

```
  ███████╗███╗   ██╗██╗   ██╗███╗   ███╗███████╗██████╗  █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝████╗  ██║██║   ██║████╗ ████║██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
  █████╗  ██╔██╗ ██║██║   ██║██╔████╔██║█████╗  ██████╔╝███████║   ██║   ██║██║   ██║██╔██╗ ██║
  ██╔══╝  ██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
  ███████╗██║ ╚████║╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**Objetivo:** Recopilar información sobre el sistema y la red comprometidos para planificar los siguientes pasos.

---

## 🖥️ Enumeración en Windows

### Información del sistema
```cmd
# Información básica del sistema
systeminfo
hostname
whoami /all

# Versión del sistema operativo
wmic os get caption,version,buildnumber

# Arquitectura
echo %PROCESSOR_ARCHITECTURE%

# Variables de entorno
set
```

### Enumeración de usuarios y grupos
```cmd
# Privilegios del usuario actual
whoami /priv
whoami /groups

# Todos los usuarios locales
net user
net user administrator

# Todos los grupos locales
net localgroup
net localgroup Administrators

# Usuarios del dominio (si el equipo está unido al dominio)
net user /domain
net group /domain
net group "Domain Admins" /domain
```

### Información de red
```cmd
# Configuración IP
ipconfig /all

# Tabla de enrutamiento
route print

# Tabla ARP
arp -a

# Conexiones activas
netstat -ano
netstat -ano | findstr ESTABLISHED
netstat -ano | findstr LISTENING

# Caché DNS
ipconfig /displaydns

# Recursos compartidos
net share
net view \\localhost
```

### Procesos y servicios
```cmd
# Procesos en ejecución
tasklist
tasklist /SVC
wmic process list brief

# Servicios
sc query
net start
wmic service list brief

# Tareas programadas
schtasks /query /fo LIST /v
```

### Software instalado
```cmd
# Programas instalados
wmic product get name,version
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall

# Revisiones y parches
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Credenciales y archivos sensibles
```cmd
# Credenciales guardadas
cmdkey /list

# Contraseñas WiFi
netsh wlan show profiles
netsh wlan show profile name="SSID" key=clear

# Buscar contraseñas en archivos
findstr /si password *.txt *.ini *.config *.xml
dir /s *pass* *cred* *vnc* *.config

# Archivos de instalación desatendida
dir /s C:\*unattend.xml
dir /s C:\*sysprep.inf
```

---

## 🐧 Enumeración en Linux

### Información del sistema
```bash
# Información básica
hostname
uname -a
cat /etc/os-release
cat /proc/version

# Arquitectura
arch

# Versión del kernel
uname -r
```

### Enumeración de usuarios y grupos
```bash
# Usuario actual
whoami
id
groups

# Todos los usuarios
cat /etc/passwd
cat /etc/shadow  # if readable
cat /etc/group

# Usuarios con sesión iniciada
w
who
last

# Permisos de sudo
sudo -l
cat /etc/sudoers
```

### Información de red
```bash
# Configuración IP
ip a
ifconfig

# Enrutamiento
ip route
route -n

# ARP
arp -a
ip neigh

# Conexiones activas
ss -tulnp
netstat -tulnp

# DNS
cat /etc/resolv.conf
```

### Procesos y servicios
```bash
# Procesos en ejecución
ps aux
ps auxwww
top

# Servicios
systemctl list-units --type=service
service --status-all

# Tareas de cron
cat /etc/crontab
ls -la /etc/cron.*
crontab -l
```

### Software instalado
```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Binarios SUID (¡vectores de escalada de privilegios!)
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null
```

### Credenciales y archivos sensibles
```bash
# Claves SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Archivos de historial
cat ~/.bash_history
cat ~/.zsh_history

# Archivos de configuración con contraseñas
grep -r "password" /etc/ 2>/dev/null
grep -r "pass" /home/ 2>/dev/null

# Archivos de interés
cat /etc/fstab
cat ~/.bashrc
cat ~/.profile
```

---

## 🏢 Enumeración de Active Directory

### PowerView
```powershell
# Importar el módulo
Import-Module .\PowerView.ps1

# Obtener información del dominio
Get-Domain
Get-DomainController

# Obtener usuarios
Get-DomainUser
Get-DomainUser -Identity admin
Get-DomainUser | select samaccountname,description

# Obtener grupos
Get-DomainGroup
Get-DomainGroupMember -Identity "Domain Admins"

# Obtener equipos
Get-DomainComputer
Get-DomainComputer | select dnshostname,operatingsystem

# Obtener GPO
Get-DomainGPO
Get-DomainGPOLocalGroup
```

### BloodHound
```bash
# Recopilar datos con SharpHound
.\SharpHound.exe -c All

# O con PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound -CollectionMethod All

# Recolector en Python (desde Linux)
bloodhound-python -u user -p 'password' -d domain.local -ns 192.168.1.1 -c All
```

### Enumeración LDAP
```bash
# Desde Linux con ldapsearch
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local"

# Usuarios
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local" "(objectClass=user)"

# Con credenciales
ldapsearch -x -H ldap://192.168.1.1 -D "user@domain.local" -w 'password' -b "DC=domain,DC=local"
```

### Enumeración Kerberos
```bash
# Enumerar usuarios con Kerbrute
kerbrute userenum -d domain.local users.txt --dc 192.168.1.1

# Usuarios susceptibles a AS-REP Roasting
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt

# Cuentas susceptibles a Kerberoasting
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1
```

---

## 📡 Enumeración de red

### Descubrimiento de equipos
```bash
# Barrido de ping
nmap -sn 192.168.1.0/24

# Escaneo ARP (red local)
arp-scan -l

# Netdiscover
netdiscover -r 192.168.1.0/24
```

### Escaneo de puertos
```bash
# Escaneo rápido
nmap -F 192.168.1.10

# Escaneo de todos los puertos
nmap -p- 192.168.1.10

# Detección de versiones de servicios
nmap -sV -sC 192.168.1.10

# Escaneo UDP
nmap -sU --top-ports 100 192.168.1.10
```

### Enumeración SMB
```bash
# Enum4linux
enum4linux -a 192.168.1.10

# Recursos compartidos SMB
smbclient -L //192.168.1.10 -N
smbmap -H 192.168.1.10
crackmapexec smb 192.168.1.10 --shares

# Con credenciales
smbclient //192.168.1.10/share -U user
```

### Enumeración SNMP
```bash
# Cadenas de comunidad predeterminadas
snmpwalk -v2c -c public 192.168.1.10
snmpwalk -v2c -c private 192.168.1.10

# Enumerar con onesixtyone
onesixtyone -c community.txt 192.168.1.10
```

---

## 🛠️ Herramientas de enumeración automatizada

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

## 📊 Referencia rápida

### Enumeración rápida de Windows
```cmd
systeminfo
whoami /all
net user
net localgroup Administrators
ipconfig /all
netstat -ano
tasklist /SVC
```

### Enumeración rápida de Linux
```bash
uname -a && id && sudo -l
cat /etc/passwd
ps aux
netstat -tulnp
find / -perm -4000 2>/dev/null
```

---

## 🔗 Guías de referencia relacionadas

- [Nmap](../../Nmap/translations/README.es.md)
- [BloodHound](../../BloodHound/translations/README.es.md)
- [PowerView](../../PowerView/translations/README.es.md)
- [CrackMapExec](../../CrackMapExec/translations/README.es.md)

---

**Fase anterior:** [← 01 - Acceso inicial](./01-Initial-Access.es.md)

**Fase siguiente:** [03 - Escalada de privilegios →](./03-Privilege-Escalation.es.md)
