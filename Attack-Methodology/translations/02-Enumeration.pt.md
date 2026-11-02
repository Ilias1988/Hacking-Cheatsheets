# 🔍 Fase 2: Descoberta / enumeração

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](02-Enumeration.tr.md) • [🇬🇷 Ελληνικά](02-Enumeration.el.md)

```
  ███████╗███╗   ██╗██╗   ██╗███╗   ███╗███████╗██████╗  █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝████╗  ██║██║   ██║████╗ ████║██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
  █████╗  ██╔██╗ ██║██║   ██║██╔████╔██║█████╗  ██████╔╝███████║   ██║   ██║██║   ██║██╔██╗ ██║
  ██╔══╝  ██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
  ███████╗██║ ╚████║╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**Objetivo:** Recolher informações sobre o sistema e a rede comprometidos para planear os passos seguintes.

> **Nota de leitura:** enumeração é a recolha organizada de informações sobre sistemas, utilizadores e serviços. Os comandos e coletores ilustram interfaces de versões específicas; a presença de ferramentas como `wmic`, `ifconfig`, `netstat` e `SharpHound.ps1` não deve ser presumida. Os comandos Windows referem nomes de grupos em inglês, que podem diferir em instalações localizadas. Na lista Linux, `-4000` identifica SUID e `-2000` identifica SGID.

---

## 🖥️ Enumeração em Windows

### Informações do sistema
```cmd
# Informações básicas do sistema
systeminfo
hostname
whoami /all

# Versão do sistema operativo
wmic os get caption,version,buildnumber

# Arquitetura
echo %PROCESSOR_ARCHITECTURE%

# Variáveis de ambiente
set
```

### Enumeração de utilizadores e grupos
```cmd
# Privilégios do utilizador atual
whoami /priv
whoami /groups

# Todos os utilizadores locais
net user
net user administrator

# Todos os grupos locais
net localgroup
net localgroup Administrators

# Utilizadores do domínio (se o sistema pertencer a um domínio)
net user /domain
net group /domain
net group "Domain Admins" /domain
```

### Informações de rede
```cmd
# Configuração IP
ipconfig /all

# Tabela de encaminhamento
route print

# Tabela ARP
arp -a

# Ligações ativas
netstat -ano
netstat -ano | findstr ESTABLISHED
netstat -ano | findstr LISTENING

# Cache DNS
ipconfig /displaydns

# Partilhas
net share
net view \\localhost
```

### Processos e serviços
```cmd
# Processos em execução
tasklist
tasklist /SVC
wmic process list brief

# Serviços
sc query
net start
wmic service list brief

# Tarefas agendadas
schtasks /query /fo LIST /v
```

### Software instalado
```cmd
# Programas instalados
wmic product get name,version
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall

# Correções/atualizações
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Credenciais e ficheiros sensíveis
```cmd
# Credenciais guardadas
cmdkey /list

# Palavras-passe Wi-Fi
netsh wlan show profiles
netsh wlan show profile name="SSID" key=clear

# Procurar palavras-passe em ficheiros
findstr /si password *.txt *.ini *.config *.xml
dir /s *pass* *cred* *vnc* *.config

# Ficheiros Unattend
dir /s C:\*unattend.xml
dir /s C:\*sysprep.inf
```

---

## 🐧 Enumeração em Linux

### Informações do sistema
```bash
# Informações básicas
hostname
uname -a
cat /etc/os-release
cat /proc/version

# Arquitetura
arch

# Versão do kernel
uname -r
```

### Enumeração de utilizadores e grupos
```bash
# Utilizador atual
whoami
id
groups

# Todos os utilizadores
cat /etc/passwd
cat /etc/shadow  # se houver permissão de leitura
cat /etc/group

# Utilizadores com sessão iniciada
w
who
last

# Permissões de sudo
sudo -l
cat /etc/sudoers
```

### Informações de rede
```bash
# Configuração IP
ip a
ifconfig

# Encaminhamento
ip route
route -n

# ARP
arp -a
ip neigh

# Ligações ativas
ss -tulnp
netstat -tulnp

# DNS
cat /etc/resolv.conf
```

### Processos e serviços
```bash
# Processos em execução
ps aux
ps auxwww
top

# Serviços
systemctl list-units --type=service
service --status-all

# Tarefas cron
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

# Binários SUID (vetores de escalada de privilégios!)
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null
```

### Credenciais e ficheiros sensíveis
```bash
# Chaves SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Ficheiros de histórico
cat ~/.bash_history
cat ~/.zsh_history

# Ficheiros de configuração com palavras-passe
grep -r "password" /etc/ 2>/dev/null
grep -r "pass" /home/ 2>/dev/null

# Ficheiros relevantes
cat /etc/fstab
cat ~/.bashrc
cat ~/.profile
```

---

## 🏢 Enumeração de Active Directory

### PowerView
```powershell
# Importar o módulo
Import-Module .\PowerView.ps1

# Obter informações do domínio
Get-Domain
Get-DomainController

# Obter utilizadores
Get-DomainUser
Get-DomainUser -Identity admin
Get-DomainUser | select samaccountname,description

# Obter grupos
Get-DomainGroup
Get-DomainGroupMember -Identity "Domain Admins"

# Obter computadores
Get-DomainComputer
Get-DomainComputer | select dnshostname,operatingsystem

# Obter GPOs
Get-DomainGPO
Get-DomainGPOLocalGroup
```

### BloodHound
```bash
# Recolher dados com SharpHound
.\SharpHound.exe -c All

# Ou com PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound -CollectionMethod All

# Coletor Python (a partir de Linux)
bloodhound-python -u user -p 'password' -d domain.local -ns 192.168.1.1 -c All
```

### Enumeração de LDAP
```bash
# A partir de Linux com ldapsearch
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local"

# Utilizadores
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local" "(objectClass=user)"

# Com credenciais
ldapsearch -x -H ldap://192.168.1.1 -D "user@domain.local" -w 'password' -b "DC=domain,DC=local"
```

### Enumeração de Kerberos
```bash
# Enumerar utilizadores com Kerbrute
kerbrute userenum -d domain.local users.txt --dc 192.168.1.1

# Utilizadores suscetíveis a AS-REP roasting
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt

# Contas suscetíveis a Kerberoasting
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1
```

---

## 📡 Enumeração de rede

### Descoberta de sistemas
```bash
# Descoberta de sistemas por ping
nmap -sn 192.168.1.0/24

# Análise ARP (rede local)
arp-scan -l

# Netdiscover
netdiscover -r 192.168.1.0/24
```

### Análise de portas
```bash
# Análise rápida
nmap -F 192.168.1.10

# Análise de todas as portas
nmap -p- 192.168.1.10

# Deteção da versão dos serviços
nmap -sV -sC 192.168.1.10

# Análise UDP
nmap -sU --top-ports 100 192.168.1.10
```

### Enumeração de SMB
```bash
# Enum4linux
enum4linux -a 192.168.1.10

# Partilhas SMB
smbclient -L //192.168.1.10 -N
smbmap -H 192.168.1.10
crackmapexec smb 192.168.1.10 --shares

# Com credenciais
smbclient //192.168.1.10/share -U user
```

### Enumeração de SNMP
```bash
# Community strings predefinidas
snmpwalk -v2c -c public 192.168.1.10
snmpwalk -v2c -c private 192.168.1.10

# Enumerar com onesixtyone
onesixtyone -c community.txt 192.168.1.10
```

---

## 🛠️ Ferramentas de enumeração automática

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

## 📊 Referência rápida

### Enumeração rápida em Windows
```cmd
systeminfo
whoami /all
net user
net localgroup Administrators
ipconfig /all
netstat -ano
tasklist /SVC
```

### Enumeração rápida em Linux
```bash
uname -a && id && sudo -l
cat /etc/passwd
ps aux
netstat -tulnp
find / -perm -4000 2>/dev/null
```

---

## 🔗 Guias de referência relacionados

- [Nmap](../../Nmap/README.md)
- [BloodHound](../../BloodHound/README.md)
- [PowerView](../../PowerView/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)

---

**Fase anterior:** [← 01 - Acesso inicial](./01-Initial-Access.pt.md)

**Fase seguinte:** [03 - Escalada de privilégios →](./03-Privilege-Escalation.pt.md)
