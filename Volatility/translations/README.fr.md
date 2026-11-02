# 🧠 Volatility — aide-mémoire d’investigation mémoire

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗   ██╗ ██████╗ ██╗      █████╗ ████████╗██╗██╗     ██╗████████╗██╗   ██╗
  ██║   ██║██╔═══██╗██║     ██╔══██╗╚══██╔══╝██║██║     ██║╚══██╔══╝╚██╗ ██╔╝
  ██║   ██║██║   ██║██║     ███████║   ██║   ██║██║     ██║   ██║    ╚████╔╝ 
  ╚██╗ ██╔╝██║   ██║██║     ██╔══██║   ██║   ██║██║     ██║   ██║     ╚██╔╝  
   ╚████╔╝ ╚██████╔╝███████╗██║  ██║   ██║   ██║███████╗██║   ██║      ██║   
    ╚═══╝   ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝   
                      Framework d’investigation mémoire
```

<p align="center">
  <img src="https://img.shields.io/badge/Volatility-Memory_Forensics-purple?style=for-the-badge" alt="Volatility">
  <img src="https://img.shields.io/badge/DFIR-blue?style=for-the-badge" alt="DFIR">
  <img src="https://img.shields.io/badge/Incident_Response-red?style=for-the-badge" alt="IR">
</p>

---

## 📋 Table des matières

- [Qu’est-ce que Volatility ?](#-quest-ce-que-volatility-)
- [Installation](#-installation)
- [Utilisation de base](#-utilisation-de-base)
- [Identification de l’image mémoire](#-identification-de-limage-mémoire)
- [Analyse des processus](#-analyse-des-processus)
- [Analyse réseau](#-analyse-réseau)
- [Analyse du Registre](#-analyse-du-registre)
- [Analyse des logiciels malveillants](#-analyse-des-logiciels-malveillants)
- [Analyse des utilisateurs et des identifiants](#-analyse-des-utilisateurs-et-des-identifiants)
- [Analyse des fichiers et des DLL](#-analyse-des-fichiers-et-des-dll)
- [Analyse chronologique](#-analyse-chronologique)
- [Volatility 3](#-volatility-3)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce que Volatility ?

**Volatility** est un framework open source d’investigation mémoire (*memory forensics*) destiné à analyser des images de la mémoire vive (RAM). Il est essentiel pour :

- 🔍 **Réponse à incident** — Identifier les logiciels malveillants actifs
- 🦠 **Analyse de logiciels malveillants** — Extraire les processus cachés
- 🔐 **Récupération d’identifiants** — Extraire des mots de passe et des empreintes (hashes)
- 📊 **Analyse chronologique** — Reconstituer les événements
- 🕵️ **Recherche de menaces (threat hunting)** — Repérer des indicateurs de compromission

### Formats d’images mémoire pris en charge

| Format | Description |
|--------|-------------|
| Brut (.raw, .mem) | Image mémoire brute |
| EWF (.E01) | Format EnCase |
| AFF | Advanced Forensics Format |
| Image de plantage (crash dump) | Images mémoire de plantage Windows |
| VMware (.vmem) | Instantanés VMware |
| VirtualBox (.sav) | Instantanés VirtualBox |
| HPAK (.hpak) | Format FDPro |
| LiME (.lime) | Linux Memory Extractor |

---

## 🚀 Installation

### Volatility 2 (ancienne génération)

> **Version historique :** Volatility 2 repose sur Python 2.7 et son dépôt est archivé. Les commandes d’installation sont conservées comme dans l’original. Les formats du tableau dépendent de la génération, des couches mémoire et parfois de bibliothèques supplémentaires ; ce n’est pas une compatibilité universelle ([projet Vol2](https://github.com/volatilityfoundation/volatility), [documentation](https://github.com/volatilityfoundation/volatility/wiki/Volatility-Usage)).

```bash
# Cloner le dépôt
git clone https://github.com/volatilityfoundation/volatility.git
cd volatility

# Installer les dépendances
pip install pycrypto distorm3 yara-python pillow openpyxl

# Lancer
python vol.py -h
```

### Volatility 3 (génération actuelle)

```bash
# Installer avec pip
pip3 install volatility3

# Ou cloner le dépôt
git clone https://github.com/volatilityfoundation/volatility3.git
cd volatility3
pip3 install -r requirements.txt

# Lancer
python3 vol.py -h
vol -h  # Si l’installation a été faite avec pip
```

### Télécharger les tables de symboles (Vol3)

> **Précision :** Vol3 utilise des tables de symboles plutôt que les profils Vol2. Windows peut télécharger les informations nécessaires automatiquement ; Linux et macOS exigent des symboles correspondant au noyau analysé. Les archives ZIP peuvent être utilisées directement selon la configuration, sans extraction systématique ([projet Vol3](https://github.com/volatilityfoundation/volatility3), [tutoriel Linux](https://volatility3.readthedocs.io/en/stable/getting-started-linux-tutorial.html)).

```bash
# Symboles Windows
wget https://downloads.volatilityfoundation.org/volatility3/symbols/windows.zip

# Symboles Linux
wget https://downloads.volatilityfoundation.org/volatility3/symbols/linux.zip

# Symboles Mac
wget https://downloads.volatilityfoundation.org/volatility3/symbols/mac.zip

# Extraire dans volatility3/symbols/
```

---

## 💻 Utilisation de base

### Syntaxe de Volatility 2

```bash
# Syntaxe générale
python vol.py -f <memory_dump> --profile=<profile> <plugin>

# Exemple
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
```

### Syntaxe de Volatility 3

```bash
# Syntaxe générale (détection automatique du profil)
vol -f <memory_dump> <plugin>

# Exemple
vol -f memory.raw windows.pslist
```

### Options courantes

```bash
# Volatility 2
-f FILE          # Fichier d’image mémoire
--profile=PROFILE # Profil du système d’exploitation
-h               # Aide
--info           # Lister les plugins disponibles

# Volatility 3
-f FILE          # Fichier d’image mémoire
-o DIR           # Répertoire de sortie
-r RENDERER      # Format de sortie (pretty, json, csv)
-p PLUGIN_DIR    # Plugins supplémentaires
-h               # Aide
```

---

## 🔍 Identification de l’image mémoire

### Identifier le profil (Vol2)

```bash
# Obtenir les profils suggérés
python vol.py -f memory.raw imageinfo

# Informations plus détaillées
python vol.py -f memory.raw kdbgscan
```

### Identifier le système d’exploitation (Vol3)

> **Précision :** les noms de plugins et leur disponibilité dépendent de la version. Ne pas supposer que `linux.info` ou `mac.info` existe dans toute installation. Plusieurs plugins Windows ont changé d’espace de noms (namespace), notamment ceux de `windows.malware` et `windows.registry` ; les exemples historiques restent inchangés ([plugins actuels](https://volatility3.readthedocs.io/en/stable/volatility3.plugins.windows.malware.html), [Registre](https://volatility3.readthedocs.io/en/stable/volatility3.plugins.windows.registry.html)).

```bash
# Détection automatique (Vol3 ne demande pas de profil manuel)
vol -f memory.raw windows.info

# Linux
vol -f memory.lime linux.info

# Mac
vol -f memory.raw mac.info
```

### Profils courants (Vol2)

> **Précision :** la ligne « Windows Server 2012 » conserve le profil `Win2012R2x64`, qui correspond en réalité à Windows Server 2012 R2. Le profil doit correspondre à l’image analysée ([définition officielle](https://raw.githubusercontent.com/volatilityfoundation/volatility/master/volatility/plugins/overlays/windows/win8.py)).

| Système d’exploitation | Profil |
|----|---------|
| Windows XP SP2 x86 | WinXPSP2x86 |
| Windows XP SP3 x86 | WinXPSP3x86 |
| Windows 7 SP1 x86 | Win7SP1x86 |
| Windows 7 SP1 x64 | Win7SP1x64 |
| Windows 10 x64 | Win10x64_* |
| Windows Server 2012 | Win2012R2x64 |

---

<a id="-analyse-des-processus"></a>
## ⚙️ Analyse des processus

### Lister les processus

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
python vol.py -f memory.raw --profile=Win7SP1x64 pstree  # Vue arborescente
python vol.py -f memory.raw --profile=Win7SP1x64 psscan  # Processus cachés

# Volatility 3
vol -f memory.raw windows.pslist
vol -f memory.raw windows.pstree
vol -f memory.raw windows.psscan
```

### Détails des processus

```bash
# Arguments de ligne de commande
python vol.py -f memory.raw --profile=Win7SP1x64 cmdline
vol -f memory.raw windows.cmdline

# Variables d’environnement
python vol.py -f memory.raw --profile=Win7SP1x64 envars

# Handles (références) du processus
python vol.py -f memory.raw --profile=Win7SP1x64 handles -p <PID>
vol -f memory.raw windows.handles --pid <PID>

# DLL chargées
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>
vol -f memory.raw windows.dlllist --pid <PID>
```

### Détecter les processus cachés

> **Précision :** un résultat de scan ou une différence entre listes peut aussi provenir de processus terminés ou de données résiduelles ; `malfind` fournit des pistes à examiner, pas une preuve suffisante de logiciel malveillant. `hollowfind` est un plugin tiers ([référence Vol2](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference), [HollowFind](https://github.com/monnappa22/HollowFind)).

```bash
# Comparer pslist et psscan
python vol.py -f memory.raw --profile=Win7SP1x64 psxview

# Détection du process hollowing (remplacement de l’image d’un processus)
python vol.py -f memory.raw --profile=Win7SP1x64 hollowfind
```

### Extraire un processus

```bash
# Extraire l’exécutable
python vol.py -f memory.raw --profile=Win7SP1x64 procdump -p <PID> -D output/
vol -f memory.raw windows.pslist --pid <PID> --dump

# Extraire la mémoire
python vol.py -f memory.raw --profile=Win7SP1x64 memdump -p <PID> -D output/
vol -f memory.raw windows.memmap --pid <PID> --dump
```

---

## 🌐 Analyse réseau

### Connexions réseau

```bash
# Connexions actives (Vol2)
python vol.py -f memory.raw --profile=Win7SP1x64 netscan    # Vista+
python vol.py -f memory.raw --profile=WinXPSP3x86 connections # XP
python vol.py -f memory.raw --profile=WinXPSP3x86 connscan    # XP

# Vol3
vol -f memory.raw windows.netscan
vol -f memory.raw windows.netstat
```

### Champs de sortie

| Champ | Description |
|-------|-------------|
| Offset | Décalage dans la mémoire |
| Proto | TCP/UDP |
| LocalAddr | IP:Port local |
| ForeignAddr | IP:Port distant |
| State | État de la connexion |
| PID | Identifiant du processus |
| Owner | Nom du processus |

### Traces réseau

```bash
# Sockets (points de communication réseau)
python vol.py -f memory.raw --profile=WinXPSP3x86 sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockscan
```

---

## 📝 Analyse du Registre

### Lister les ruches du Registre

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 hivelist

# Vol3
vol -f memory.raw windows.registry.hivelist
```

### Extraire le Registre

```bash
# Afficher une clé précise
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
vol -f memory.raw windows.registry.printkey --key "Software\Microsoft\Windows\CurrentVersion\Run"

# Extraire une ruche dans un fichier
python vol.py -f memory.raw --profile=Win7SP1x64 dumpregistry -D output/
```

### Clés importantes du Registre

```bash
# Clés de lancement automatique
printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
printkey -K "Software\Microsoft\Windows\CurrentVersion\RunOnce"

# Services
printkey -K "SYSTEM\CurrentControlSet\Services"

# Comptes utilisateurs
printkey -K "SAM\Domains\Account\Users"

# Fichiers récents
printkey -K "Software\Microsoft\Windows\CurrentVersion\Explorer\RecentDocs"
```

### User Assist (programmes exécutés)

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 userassist

# Vol3
vol -f memory.raw windows.registry.userassist
```

### Shellbags

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 shellbags
```

---

## 🦠 Analyse des logiciels malveillants

### Malfind (code injecté)

```bash
# Rechercher du code injecté
python vol.py -f memory.raw --profile=Win7SP1x64 malfind
vol -f memory.raw windows.malfind

# Extraire dans des fichiers
python vol.py -f memory.raw --profile=Win7SP1x64 malfind -D output/
```

### Détournements SSDT (hooks)

```bash
# Table des descripteurs de services système (System Service Descriptor Table)
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt

# Comparer avec un système sain
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt | grep -v "ntoskrnl\|win32k"
```

### Analyse IDT/GDT

```bash
# Table des descripteurs d’interruptions (Interrupt Descriptor Table)
python vol.py -f memory.raw --profile=Win7SP1x64 idt

# Table globale des descripteurs (Global Descriptor Table)
python vol.py -f memory.raw --profile=Win7SP1x64 gdt
```

### Analyse des pilotes

```bash
# Lister les pilotes
python vol.py -f memory.raw --profile=Win7SP1x64 driverscan
vol -f memory.raw windows.driverscan

# Liste des modules
python vol.py -f memory.raw --profile=Win7SP1x64 modules
vol -f memory.raw windows.modules

# Modules déchargés
python vol.py -f memory.raw --profile=Win7SP1x64 unloadedmodules

# Détournements des routines IRP des pilotes
python vol.py -f memory.raw --profile=Win7SP1x64 driverirp
```

### Recherche avec YARA

> **Correction du texte original :** dans Vol2, `-Y` attend une chaîne ou expression YARA et `-y` un fichier de règles. Les deux exemples anglais inversent ces options ; ils sont conservés ici à l’identique. La disponibilité des plugins et dépendances doit correspondre à la version ([code officiel](https://raw.githubusercontent.com/volatilityfoundation/volatility/master/volatility/plugins/malware/malfind.py)).

```bash
# Rechercher avec des règles YARA
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -Y "rule_file.yar"
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -y "string_to_find"

# Vol3
vol -f memory.raw yarascan.YaraScan --yara-file rules.yar
```

### Détournements d’API (hooks)

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 apihooks
```

---

## 🔐 Analyse des utilisateurs et des identifiants

### Informations sur les utilisateurs

```bash
# Obtenir les SID des utilisateurs
python vol.py -f memory.raw --profile=Win7SP1x64 getsids

# Dernier utilisateur connecté
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\LogonUI"
```

### Empreintes de mots de passe

```bash
# Extraire les empreintes SAM
python vol.py -f memory.raw --profile=Win7SP1x64 hashdump
vol -f memory.raw windows.hashdump

# Secrets LSA
python vol.py -f memory.raw --profile=Win7SP1x64 lsadump

# Identifiants mis en cache
python vol.py -f memory.raw --profile=Win7SP1x64 cachedump
```

### Intégration de Mimikatz

> **Précision :** le plugin `mimikatz` Vol2 est une contribution externe. Le plugin intégré `lsadump` extrait les secrets LSA et ne constitue pas une intégration complète de Mimikatz ; son espace de noms peut varier selon la version ([contributions](https://github.com/volatilityfoundation/community/tree/master/FrancescoPicasso), [documentation Lsadump](https://volatility3.readthedocs.io/en/stable/volatility3.plugins.windows.registry.lsadump.html)).

```bash
# Extraire les identifiants (plugin mimikatz)
python vol.py -f memory.raw --profile=Win7SP1x64 mimikatz

# Vol3 (intégré)
vol -f memory.raw windows.lsadump
```

### Presse-papiers

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 clipboard
```

---

## 📁 Analyse des fichiers et des DLL

### Recherche de fichiers

```bash
# Rechercher des fichiers en mémoire
python vol.py -f memory.raw --profile=Win7SP1x64 filescan
vol -f memory.raw windows.filescan

# Filtrer des fichiers précis
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "\.exe$"
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "password"
```

### Extraire des fichiers

> **Précision :** un offset physique Vol2 fourni à `-Q` et une adresse virtuelle Vol3 fournie à `--virtaddr` ne sont pas interchangeables. Le contenu récupérable dépend des pages présentes dans l’image ([référence Vol2](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference#dumpfiles), [DumpFiles Vol3](https://volatility3.readthedocs.io/en/stable/volatility3.plugins.windows.dumpfiles.html)).

```bash
# Extraire un fichier par son offset
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -Q <OFFSET> -D output/
vol -f memory.raw windows.dumpfiles --virtaddr <OFFSET>

# Extraire tous les fichiers
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -D output/
```

### Analyse des DLL

```bash
# Lister les DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist
vol -f memory.raw windows.dlllist

# Processus précis
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>

# Extraire une DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlldump -p <PID> -D output/
```

### Chaînes de caractères en mémoire

> **Précision :** le plugin `strings` exploite un fichier de chaînes préextraites avec leurs offsets ; la commande présentée ne crée pas à elle seule `strings.txt` ([référence officielle](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference#strings)).

```bash
# Extraire les chaînes de caractères
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt

# Chaînes propres à un processus
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt -p <PID>
```

---

## 📅 Analyse chronologique

### Créer une chronologie

```bash
# Créer une chronologie
python vol.py -f memory.raw --profile=Win7SP1x64 timeliner --output-file timeline.body

# Vol3
vol -f memory.raw timeliner.Timeliner
```

### Analyse de la MFT

```bash
# Table principale des fichiers (Master File Table)
python vol.py -f memory.raw --profile=Win7SP1x64 mftparser
vol -f memory.raw windows.mftscan
```

---

## 🔄 Volatility 3

### Différences principales

> **Précision :** « texte uniquement » simplifie excessivement Vol2, qui prévoit plusieurs moteurs de rendu selon les plugins. La vitesse relative dépend aussi de l’image et de l’analyse ; le tableau conserve les formulations de l’original ([documentation des sorties Vol2](https://github.com/volatilityfoundation/volatility/wiki/Volatility-Usage#choosing-an-output-format)).

| Fonctionnalité | Vol2 | Vol3 |
|---------|------|------|
| Profil | Manuel (`--profile=`) | Détection automatique |
| Nommage des plugins | `pslist` | `windows.pslist` |
| Performances | Plus lent | Plus rapide |
| Python | Python 2.7 | Python 3 |
| Sortie | Texte uniquement | JSON, CSV, etc. |

### Référence des plugins Vol3

```bash
# Processus
vol -f dump.raw windows.pslist
vol -f dump.raw windows.pstree
vol -f dump.raw windows.psscan
vol -f dump.raw windows.cmdline
vol -f dump.raw windows.dlllist
vol -f dump.raw windows.handles

# Réseau
vol -f dump.raw windows.netscan
vol -f dump.raw windows.netstat

# Registre
vol -f dump.raw windows.registry.hivelist
vol -f dump.raw windows.registry.printkey
vol -f dump.raw windows.registry.userassist

# Logiciels malveillants
vol -f dump.raw windows.malfind
vol -f dump.raw windows.driverscan

# Identifiants
vol -f dump.raw windows.hashdump
vol -f dump.raw windows.lsadump
vol -f dump.raw windows.cachedump

# Fichiers
vol -f dump.raw windows.filescan
vol -f dump.raw windows.dumpfiles
```

### Plugins Linux (Vol3)

```bash
vol -f dump.lime linux.pslist
vol -f dump.lime linux.bash
vol -f dump.lime linux.check_syscall
vol -f dump.lime linux.lsmod
vol -f dump.lime linux.lsof
```

---

## 📊 Référence rapide

### Commandes les plus utilisées

| Objectif | Commande Vol2 | Commande Vol3 |
|---------|--------------|--------------|
| Identifier l’image | `imageinfo` | `windows.info` |
| Lister les processus | `pslist` | `windows.pslist` |
| Arbre des processus | `pstree` | `windows.pstree` |
| Processus cachés | `psscan` | `windows.psscan` |
| Réseau | `netscan` | `windows.netscan` |
| DLLs | `dlllist` | `windows.dlllist` |
| Extraire un processus | `procdump` | `windows.pslist --dump` |
| Rechercher des logiciels malveillants | `malfind` | `windows.malfind` |
| Registre | `hivelist` | `windows.registry.hivelist` |
| Empreintes | `hashdump` | `windows.hashdump` |
| Fichiers | `filescan` | `windows.filescan` |
| Chaînes de caractères | `strings` | - |

### Déroulement d’une investigation

```
1. imageinfo/windows.info    → Identifier le système d’exploitation
2. pslist/pstree/psscan      → Lister les processus
3. cmdline                    → Examiner les lignes de commande
4. netscan                    → Connexions réseau
5. malfind                    → Rechercher du code injecté
6. dlllist                    → Examiner les DLL chargées
7. filescan                   → Rechercher les fichiers intéressants
8. hashdump                   → Extraire les identifiants
9. timeliner                  → Créer une chronologie
```

---

## 📚 Ressources

- [Fondation Volatility](https://www.volatilityfoundation.org/)
- [Volatility3 sur GitHub](https://github.com/volatilityfoundation/volatility3)
- [Wiki de Volatility](https://github.com/volatilityfoundation/volatility/wiki)
- [Aide-mémoire d’investigation mémoire](https://digital-forensics.sans.org/media/memory-forensics-cheat-sheet.pdf)

### Aide-mémoire associés
- [Mimikatz](../../Mimikatz/translations/README.fr.md)
- [Commandes Linux](../../Linux-Commands/translations/README.fr.md)
- [Élévation de privilèges Windows](../../Windows-PrivEsc/translations/README.fr.md)

---

<p align="center">
  <b>🧠 Maîtrisez l’investigation mémoire !</b><br>
  <i>La mémoire ne ment jamais — extrayez la vérité de la RAM</i>
</p>
