# 🔧 x64dbg — aide-mémoire du débogueur Windows

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗ ██████╗ ██╗  ██╗██████╗ ██████╗  ██████╗ 
  ╚██╗██╔╝██╔════╝ ██║  ██║██╔══██╗██╔══██╗██╔════╝ 
   ╚███╔╝ ███████╗ ███████║██║  ██║██████╔╝██║  ███╗
   ██╔██╗ ██╔═══██╗╚════██║██║  ██║██╔══██╗██║   ██║
  ██╔╝ ██╗╚██████╔╝     ██║██████╔╝██████╔╝╚██████╔╝
  ╚═╝  ╚═╝ ╚═════╝      ╚═╝╚═════╝ ╚═════╝  ╚═════╝ 
           Débogueur Windows x64/x32
```

<p align="center">
  <img src="https://img.shields.io/badge/x64dbg-Windows_Debugger-blue?style=for-the-badge" alt="x64dbg">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="Rétro-ingénierie">
  <img src="https://img.shields.io/badge/Malware_Analysis-red?style=for-the-badge" alt="Analyse de logiciels malveillants">
</p>

---

## 📋 Table des matières

- [Qu’est-ce que x64dbg ?](#-quest-ce-que-x64dbg-)
- [Installation](#-installation)
- [Vue d’ensemble de l’interface](#-vue-densemble-de-linterface)
- [Utilisation de base](#-utilisation-de-base)
- [Points d’arrêt](#-points-darrêt)
- [Exécution pas à pas et poursuite](#-exécution-pas-à-pas-et-poursuite)
- [Opérations sur la mémoire](#-opérations-sur-la-mémoire)
- [Registres](#-registres)
- [Analyse de la pile](#-analyse-de-la-pile)
- [Modification du binaire (patching)](#-modification-du-binaire-patching)
- [Scripts](#-scripts)
- [Raccourcis clavier](#-raccourcis-clavier)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce que x64dbg ?

**x64dbg** est un débogueur open source x64/x32 pour Windows. Il reprend l’esprit d’OllyDbg avec des fonctions modernes :

- 🔍 **Débogage en mode utilisateur** — Déboguer des applications Windows
- 🦠 **Analyse de logiciels malveillants** — Analyser des exécutables suspects
- 💥 **Développement d’exploits** — Recherche sur l’exploitation de vulnérabilités Windows
- 🔧 **Rétro-ingénierie** — Comprendre le comportement des binaires
- 🧩 **Prise en charge des plugins** — Ajouter des fonctions avec des extensions

### Fonctionnalités

| Fonctionnalité | Description |
|---------|-------------|
| **Interface graphique (GUI)** | Interface moderne et personnalisable |
| **Prise en charge x64** | Débogage 64 bits complet |
| **Scripts** | Langage de script intégré |
| **Plugins** | Vaste écosystème d’extensions |
| **Symboles** | Prise en charge des symboles PDB |
| **Open source** | Gratuit et développé activement |

---

## 🚀 Installation

### Téléchargement

```
# Site officiel
https://x64dbg.com/

# Versions publiées sur GitHub
https://github.com/x64dbg/x64dbg/releases

# Télécharger : snapshot_YYYY-MM-DD_HH-MM.zip
```

### Mise en place

```
1. Extraire le ZIP dans un emplacement permanent (par exemple, C:\x64dbg\)
2. Lancer x96dbg.exe (lanceur commun aux deux versions)
   - x32dbg.exe → débogage 32 bits
   - x64dbg.exe → débogage 64 bits
3. Facultatif : ajouter le dossier au PATH ou créer des raccourcis
```

### Plugins recommandés

> **Précision :** compatibilité et installation dépendent du plugin, de son architecture et de la version. TitanHide comprend un pilote noyau ; ce n’est pas simplement une DLL à copier dans le dossier des plugins ([projet officiel](https://github.com/mrexodia/TitanHide)). Les noms de menus et raccourcis ci-dessous restent indicatifs.

| Plugin | Fonction |
|--------|---------|
| **ScyllaHide** | Contournement de mécanismes anti-débogage |
| **SwissArmyKnife** | Utilitaires variés |
| **x64dbgpy** | Scripts Python |
| **TitanHide** | Masquage du débogueur au niveau du noyau |
| **OllyDumpEx** | Extraction du contenu mémoire |
| **Multiline Ultimate Assembler** | Modification de plusieurs lignes d’assembleur |

### Installation d’un plugin

```
1. Télécharger le plugin (fichier DLL)
2. Copier vers : x64dbg\release\x32\plugins\ (32 bits)
           x64dbg\release\x64\plugins\ (64 bits)
3. Redémarrer x64dbg
```

---

<a id="-vue-densemble-de-linterface"></a>
## 🖥️ Vue d’ensemble de l’interface

### Fenêtres principales

| Fenêtre | Description | Raccourci |
|--------|-------------|----------|
| **CPU** | Vue du désassemblage | Alt+C |
| **Log** | Journal du débogage | Alt+L |
| **Memory Map** | Régions mémoire | Alt+M |
| **Call Stack** | Pile des appels | Alt+K |
| **Breakpoints** | Tous les points d’arrêt | Alt+B |
| **Symbols** | Symboles chargés | Alt+E |
| **Threads** | Liste des fils d’exécution | Alt+T |
| **Handles** | Liste des handles (références aux objets système) | Alt+H |

### Panneaux de la fenêtre CPU

Les libellés du schéma correspondent à Disassembly (désassemblage), Registers (registres), Hex Dump (vue hexadécimale) et Stack (pile).

```
┌─────────────────┬──────────────────┐
│   Disassembly   │    Registers     │
│                 │                  │
├─────────────────┼──────────────────┤
│   Hex Dump      │    Stack         │
│                 │                  │
└─────────────────┴──────────────────┘
```

### Barre d’état

La barre indique le module et l’adresse, les drapeaux, EIP/RIP et l’état ; les placeholders du schéma restent ceux de l’original.

```
[Module.Address] | [Flags] | [EIP/RIP] | [Status]
```

---

## 💻 Utilisation de base

### Démarrer le débogage

```
# Ouvrir un exécutable
File → Open (F3)

# S’attacher à un processus
File → Attach (Alt+A)

# Ouvrir et exécuter
Debug → Run (F9)

# Relancer le débogage
Debug → Restart (Ctrl+F2)

# Arrêter le débogage
Debug → Stop (Alt+F2)
```

### Barre de commande

> **Précision sur la syntaxe :** les exemples mélangent des conventions de débogueurs. Dans x64dbg, `g` et `r` sont des alias de poursuite de l’exécution, pas un simple déplacement de vue ni la commande de modification des registres. Les formes `goto`, `/s`, `/x`, `/p` et `r reg=val` ne doivent pas être supposées équivalentes aux commandes documentées ; elles restent inchangées comme dans l’original ([exécution](https://help.x64dbg.com/en/latest/commands/debug-control/run.html), [commandes](https://help.x64dbg.com/en/latest/commands/index.html), [affectation](https://help.x64dbg.com/en/latest/commands/general-purpose/mov.html)).

```
# Saisir les commandes dans la barre de commande en bas

# Aller à une adresse
g 0x401000
goto 0x401000

# Aller à une expression
g GetProcAddress
g kernel32.LoadLibraryA

# Rechercher une chaîne
/s "password"

# Rechercher des octets hexadécimaux
/x 90 90 90 90

# Rechercher un motif
/p "8B ?? 89"
```

### Navigation

```
# Aller à une adresse
Ctrl+G → Saisir l’adresse

# Aller au point d’entrée
Debug → Entry Point

# Suivre dans le désassembleur
Clic droit → Follow in Disassembler (Suivre dans le désassembleur)

# Revenir en arrière
- (touche moins)

# Aller en avant
+ (touche plus)
```

---

## 🔴 Points d’arrêt

### Points d’arrêt logiciels (INT3)

> **Correction de l’original :** le deuxième argument de `bp` est un nom et le troisième un type, pas une condition ou un compteur de passages. Les exemples conditionnels ci-dessous ne configurent donc pas ce qu’annonce leur commentaire. Les conditions et la journalisation ont leurs commandes propres ; `bplog` seul ne garantit pas une journalisation sans arrêt. Un point d’arrêt sur une adresse de module n’est pas non plus un événement de chargement de DLL ([bp](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetBPX.html), [conditions](https://help.x64dbg.com/en/latest/commands/conditional-breakpoint-control/SetBreakpointCondition.html), [journalisation](https://help.x64dbg.com/en/latest/commands/conditional-breakpoint-control/SetBreakpointLog.html), [chargement DLL](https://help.x64dbg.com/en/latest/commands/breakpoint-control/LibrarianSetBreakpoint.html)).

```
# Ajouter ou retirer un point d’arrêt à l’adresse courante
F2

# Définir un point d’arrêt par une commande
bp 0x401000
bp kernel32.LoadLibraryA
bp module.function

# Définir un point d’arrêt conditionnel
bp 0x401000, "eax==0"
bp 0x401000, "[esp]==0x12345"

# S’arrêter au chargement du module
bp $module(kernel32)
```

### Points d’arrêt matériels

> **Précision :** pour `bph`, `r` signifie lecture **et écriture** ; 8 octets n’est pris en charge qu’en x64 et l’adresse doit être alignée. Les points d’arrêt mémoire `bpm` portent sur toute une région ; la forme abrégée `bpm adresse,r` est bien acceptée, le type non numérique remplaçant l’argument optionnel de restauration ([matériel](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetHardwareBreakpoint.html), [mémoire](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetMemoryBPX.html)).

```
# Définir un point d’arrêt matériel (limité à 4)
Clic droit → Breakpoint → Hardware → Execute (Exécution)
Clic droit → Breakpoint → Hardware → Access (Accès)
Clic droit → Breakpoint → Hardware → Write (Écriture)

# Par une commande
bph 0x401000, x, 1    # Exécution
bph 0x401000, r, 4    # Lecture (4 octets)
bph 0x401000, w, 4    # Écriture (4 octets)

# Tailles : 1, 2, 4 ou 8 octets
```

### Points d’arrêt mémoire

```
# S’arrêter lors d’un accès mémoire
Clic droit dans la vue mémoire → Breakpoint → Memory → Access (Accès)
Clic droit dans la vue mémoire → Breakpoint → Memory → Write (Écriture)

# Par une commande
bpm 0x401000, r       # Lecture
bpm 0x401000, w       # Écriture
bpm 0x401000, x       # Exécution
```

### Points d’arrêt conditionnels

```
# S’arrêter si la condition est vraie
bp 0x401000, "eax==0"
bp 0x401000, "eax!=ebx"
bp 0x401000, "[esp+4]>0x100"
bp 0x401000, "byte:[eax]==0x90"

# S’arrêter selon le nombre de passages
bp 0x401000, "", 5    # S’arrêter au 5e passage

# Journaliser sans s’arrêter
bplog 0x401000, "EAX={eax}, EBX={ebx}"
```

### Gérer les points d’arrêt

```
# Lister tous les points d’arrêt
Fenêtre Breakpoints (Alt+B)

# Supprimer un point d’arrêt
bc 0x401000          # Supprimer celui indiqué
bc                   # Tout supprimer

# Activer ou désactiver
bd 0x401000          # Désactiver
be 0x401000          # Activer

# Tout désactiver
bd *

# Tout activer
be *
```

---

<a id="-exécution-pas-à-pas-et-poursuite"></a>
## ▶️ Exécution pas à pas et poursuite

### Exécution pas à pas

| Action | Raccourci | Description |
|--------|----------|-------------|
| Step Into | F7 | Exécuter une instruction en entrant dans les appels |
| Step Over | F8 | Exécuter les appels sans entrer dedans |
| Step Out | Ctrl+F9 | Exécuter jusqu’au retour |
| Run to Selection | F4 | Exécuter jusqu’à la sélection |
| Run to User Code | Alt+F9 | Revenir au code utilisateur |

### Poursuivre l’exécution

> **Précision :** le tableau original et la section Trace attribuent des rôles contradictoires à Ctrl+F7. Traçage, saut d’une instruction et changement de l’origine d’exécution sont des actions distinctes ; vérifier les raccourcis configurés et le menu de la version utilisée ([menu Debug](https://help.x64dbg.com/en/latest/gui/menus/Debug.html)).

| Action | Raccourci | Description |
|--------|----------|-------------|
| Run | F9 | Poursuivre l’exécution |
| Run Skipping | Ctrl+F7 | Sauter l’instruction courante (formulation de l’original) |
| Pause | F12 | Suspendre l’exécution |
| Restart | Ctrl+F2 | Relancer le débogage |
| Close | Alt+F2 | Arrêter le débogage |

### Traçage

```
# Tracer en entrant dans les appels (journaliser toutes les instructions)
Debug → Trace Into (Ctrl+F7)

# Tracer sans entrer dans les appels
Debug → Trace Over (Ctrl+F8)

# Enregistrer une trace
Debug → Trace Record

# Arrêter le traçage
Debug → Stop Trace
```

### Sauter une instruction

```
# Sauter l’instruction courante sans l’exécuter
Right-click → Set New Origin Here

# Ou
Ctrl+Shift+O
```

---

## 🔍 Opérations sur la mémoire

### Vue du contenu mémoire (dump)

```
# Suivre l’adresse dans la vue mémoire
Clic droit → Follow in Dump → Address/Selection (Adresse/Sélection)

# Aller à une adresse dans la vue mémoire
Ctrl+G (dans la vue mémoire)

# Changer le type d’affichage mémoire
Clic droit → Hex → [Byte/Word/Dword/Qword/ASCII/Unicode]
```

### Carte mémoire

```
# Ouvrir la carte mémoire
View → Memory Map (Alt+M)

# Afficher toutes les régions mémoire :
- Address (adresse)
- Size (taille)
- Section (section)
- Type (Image/Private/Mapped : image, privée ou mappée)
- Protection (Execute/Read/Write : exécution, lecture ou écriture)
- Nom du module
```

### Rechercher en mémoire

```
# Rechercher dans la barre de commande
/s "string"           # Chaîne ASCII
/s L"string"          # Chaîne Unicode
/x 90 90 90           # Octets hexadécimaux
/p "8B ?? 89"         # Motif avec jokers

# Rechercher les références
Clic droit → Find references to → Selected Address (Adresse sélectionnée)

# Rechercher les chaînes
Clic droit → Search for → All Strings (Toutes les chaînes)
```

### Protections mémoire

```
# Afficher les protections
Memory Map → colonne Protection

# Protections courantes
PAGE_EXECUTE_READ
PAGE_EXECUTE_READWRITE
PAGE_READONLY
PAGE_READWRITE
PAGE_NOACCESS
```

### Extraire la mémoire vers un fichier

```
# Extraire la mémoire vers un fichier
Clic droit dans Memory Map → Dump Memory to File (Extraire la mémoire vers un fichier)

# Extraire avec des plugins
Plugins → OllyDumpEx → Dump
```

---

## 📊 Registres

### Panneau des registres

```
# Registres généraux (x64)
RAX, RBX, RCX, RDX
RSI, RDI, RBP, RSP
R8-R15, RIP

# Registres généraux (x32)
EAX, EBX, ECX, EDX
ESI, EDI, EBP, ESP
EIP

# Registres de segment
CS, DS, SS, ES, FS, GS

# Drapeaux (EFLAGS/RFLAGS)
CF, PF, AF, ZF, SF, TF, IF, DF, OF
```

### Modifier les registres

```
# Double-cliquer sur le registre pour le modifier
# Ou clic droit → Modify Value (Modifier la valeur)

# Par la barre de commande
r eax=0
r rax=0x12345678
r eip=0x401000
r zf=1            # Activer Zero Flag
```

### Drapeaux importants

| Drapeau | Nom | Condition d’activation |
|------|------|----------|
| ZF | Zero Flag (zéro) | Le résultat est nul |
| CF | Carry Flag (retenue) | Débordement non signé |
| SF | Sign Flag (signe) | Le résultat est négatif |
| OF | Overflow Flag (débordement) | Débordement signé |
| DF | Direction Flag (direction) | Direction des opérations sur les chaînes |
| TF | Trap Flag (pas à pas) | Exécution pas à pas |

### Inverser les drapeaux

```
# Cliquer sur la lettre du drapeau dans le panneau des registres
# Ou utiliser les commandes :
r zf=!zf         # Inverser Zero Flag
r cf=0           # Désactiver Carry Flag
r sf=1           # Activer Sign Flag
```

---

## 📚 Analyse de la pile

### Panneau de la pile

```
# Afficher le contenu de la pile avec :
- Address (adresse)
- Value (valeur)
- Comments (commentaires, si disponibles)

# Options du clic droit :
- Follow DWORD in Disassembler (Suivre le DWORD dans le désassembleur)
- Follow DWORD in Dump (Suivre le DWORD dans la vue mémoire)
- Modify (Modifier)
```

### Pile des appels

```
# Ouvrir la pile des appels
View → Call Stack (Alt+K)

# Afficher :
- Return Address (adresse de retour)
- From (appelant)
- To (fonction appelée)
- Size (taille)
- Comment (commentaire)
```

### Opérations sur la pile

> **Précision :** modifier ESP/RSP ne simule que le déplacement du pointeur : les lignes ci-dessous n’écrivent pas une valeur comme PUSH et ne la lisent pas comme POP. Le schéma suivant concerne une convention 32 bits précise, pas l’organisation des appels Windows x64 ([documentation x64dbg des expressions](https://help.x64dbg.com/en/latest/introduction/Expressions.html)).

```
# Aller à une adresse de la pile
Clic droit → Follow DWORD in Stack (Suivre le DWORD dans la pile)

# Modifier une valeur de la pile
Clic droit → Modify → Edit (Modifier)

# Simulation de Push/Pop
r esp=esp-4      # Push (32 bits)
r esp=esp+4      # Pop (32 bits)
r rsp=rsp-8      # Push (64 bits)
r rsp=rsp+8      # Pop (64 bits)
```

### Organisation de la pile (stdcall 32 bits)

```
[ESP]      → Adresse de retour
[ESP+4]    → Arg1
[ESP+8]    → Arg2
[ESP+C]    → Arg3
...

Après PUSH EBP; MOV EBP, ESP :
[EBP]      → EBP sauvegardé
[EBP+4]    → Adresse de retour
[EBP+8]    → Arg1
[EBP-4]    → Local1
```

---

## 🔧 Modification du binaire (patching)

### Assembler des instructions

```
# Clic droit sur une instruction → Assemble (Space)
# Saisir les nouvelles instructions

# Exemples :
NOP                    # Aucune opération
JMP 0x401020           # Saut
MOV EAX, 0             # Mettre EAX à zéro
XOR EAX, EAX           # Mettre EAX à zéro (plus court)
RET                    # Retour
```

### Remplir avec des NOP

```
# Sélectionner la plage → Clic droit → Binary → Fill with NOPs

# Ou sélectionner puis appuyer sur Space :
# Saisir plusieurs NOP
```

### Modification des octets

```
# Modifier directement les octets
Clic droit → Binary → Edit (Modifier)

# Remplir avec une valeur
Clic droit → Binary → Fill (Remplir)

# Copier ou coller les octets
Clic droit → Binary → Copy (Copier)
Clic droit → Binary → Paste (Coller)
```

### Modifications courantes

> **Précision :** les remplacements supposent la bonne longueur d’instruction et les bons offsets. Cinq NOP ne remplacent correctement qu’un appel de cinq octets ; les appels indirects peuvent avoir une autre taille. Modifier la mémoire et enregistrer les changements dans un fichier sont deux opérations distinctes ([interface de modification](https://help.x64dbg.com/en/latest/gui/menus/File.html#patch-file)).

| Avant | Après | Objectif |
|--------|-------|---------|
| `74 XX` (JE) | `75 XX` (JNE) | Inverser la condition |
| `75 XX` (JNE) | `74 XX` (JE) | Inverser la condition |
| `74 XX` (JE) | `EB XX` (JMP) | Toujours effectuer le saut |
| `74 XX` (JE) | `90 90` (NOP) | Ne jamais effectuer le saut |
| `0F 84` (JE) | `0F 85` (JNE) | Inverser la condition (saut proche) |
| `CALL xxx` | `NOP NOP NOP NOP NOP` | Sauter l’appel |

### Enregistrer les modifications

```
# Méthode 1 : Patch File
File → Patch File

# Méthode 2 : exporter les modifications
Right-click → Copy → Selection
# Coller dans un éditeur hexadécimal

# Méthode 3 : plugin
Plugins → Export modified executable
```

---

## 📝 Scripts

### Syntaxe des commandes

```
# Variables
$var = 0x401000

# Comparaison
cmp eax, 0

# Condition
je label
jne label

# Étiquettes
label:

# Commentaires
; Ceci est un commentaire
```

### Exemple de script

> **Correction de l’original :** `8B EC` correspond à MOV EBP, ESP, sans le PUSH EBP annoncé. `find` renvoie une adresse absolue dans `$result`, limitée à une région mémoire ; l’addition à `$addr` n’est donc pas une progression fiable. Ce motif ne démontre pas que WinMain a été trouvé. Les points-virgules sont aussi des séparateurs de commandes dans la syntaxe documentée : les annotations historiques ci-dessous ne garantissent pas un script directement exécutable ([find](https://help.x64dbg.com/en/latest/commands/searching/find.html), [syntaxe](https://help.x64dbg.com/en/latest/commands/index.html)).

```
; Script pour rechercher WinMain
mov $addr, 0x401000        ; Adresse de départ
loop:
  find $addr, "8B EC"       ; Rechercher PUSH EBP; MOV EBP, ESP
  cmp $result, 0
  je notfound
  msg "Found at: {$result}"
  add $addr, $result
  add $addr, 1
  jmp loop
notfound:
  msg "Pattern not found"
ret
```

### Commandes des scripts

| Commande | Description |
|---------|-------------|
| `msg "text"` | Afficher un message |
| `log "text"` | Écrire dans le journal de la console |
| `find addr, "pattern"` | Rechercher un motif |
| `bp addr` | Définir un point d’arrêt |
| `bc addr` | Supprimer un point d’arrêt |
| `sti` | Entrer dans un appel |
| `sto` | Exécuter sans entrer dans les appels |
| `run` | Poursuivre l’exécution |
| `ret` | Retourner ou arrêter le script |

### Exécuter un script

```
# Charger et exécuter un script
Scripts → Run Script

# Or command bar
scr "scriptfile.txt"
```

---

<a id="-raccourcis-clavier"></a>
## ⌨️ Raccourcis clavier

### Général

| Raccourci | Action |
|----------|--------|
| F3 | Ouvrir un fichier |
| Alt+A | S’attacher à un processus |
| Ctrl+F2 | Relancer |
| Alt+F2 | Arrêter le débogage |
| Ctrl+G | Aller à une adresse |
| Ctrl+F | Rechercher |

### Débogage

| Raccourci | Action |
|----------|--------|
| F2 | Ajouter ou retirer un point d’arrêt |
| F4 | Exécuter jusqu’à la sélection |
| F7 | Entrer dans un appel |
| F8 | Exécuter sans entrer dans les appels |
| F9 | Poursuivre l’exécution |
| F12 | Suspendre |
| Ctrl+F9 | Exécuter jusqu’au retour |

### Navigation

| Raccourci | Action |
|----------|--------|
| - | Revenir en arrière |
| + | Aller en avant |
| * | Aller à EIP/RIP |
| Enter | Suivre l’appel ou le saut |
| Escape | Revenir en arrière |

### Vues

| Raccourci | Action |
|----------|--------|
| Alt+C | CPU |
| Alt+L | Log |
| Alt+M | Memory Map |
| Alt+K | Call Stack |
| Alt+B | Breakpoints |
| Alt+E | Symbols |
| Alt+T | Threads |

### Modification

| Raccourci | Action |
|----------|--------|
| Space | Assembler |
| ; | Ajouter un commentaire |
| : | Ajouter une étiquette |
| N | Renommer |

---

## 📊 Référence rapide

### Commandes essentielles

| Commande | Description |
|---------|-------------|
| `bp addr` | Définir un point d’arrêt |
| `bc addr` | Supprimer un point d’arrêt |
| `bph addr, x` | Point d’arrêt matériel |
| `g addr` | Aller à l’adresse (formulation de l’original) |
| `r reg=val` | Définir un registre (syntaxe de l’original) |
| `sti` | Entrer dans un appel |
| `sto` | Exécuter sans entrer dans les appels |
| `run` | Poursuivre l’exécution |

### Motifs courants

| Octets | Instruction |
|-------|-------------|
| `90` | NOP |
| `CC` | INT3 (point d’arrêt) |
| `C3` | RET |
| `55` | PUSH EBP |
| `8B EC` | MOV EBP, ESP |
| `74 XX` | JE court |
| `75 XX` | JNE court |
| `EB XX` | JMP court |
| `E8 XX XX XX XX` | CALL proche |
| `E9 XX XX XX XX` | JMP proche |

### Déroulement d’une analyse

```
1. Charger l’exécutable           → F3
2. Définir un point d’arrêt à l’entrée   → F2 à l’entrée
3. Exécuter jusqu’au point d’arrêt         → F9
4. Parcourir le code pas à pas         → F7/F8
5. Examiner les registres et la mémoire  → Panneaux
6. Définir des points d’arrêt supplémentaires
7. Poursuivre l’analyse
8. Modifier le binaire si nécessaire           → Space
9. Enregistrer le fichier modifié
```

### Contournement des mécanismes anti-débogage

```
# Installer le plugin ScyllaHide
Plugins → ScyllaHide → Options → Sélectionner un profil

# Modifications manuelles :
- Remplacer les appels IsDebuggerPresent par des NOP
- Modifier PEB.BeingDebugged
- Gérer correctement les exceptions
```

---

## 📚 Ressources

- [Site officiel de x64dbg](https://x64dbg.com/)
- [x64dbg GitHub](https://github.com/x64dbg/x64dbg)
- [Documentation de x64dbg](https://help.x64dbg.com/)
- [x64dbg Plugins](https://github.com/x64dbg/x64dbg/wiki/Plugins)

### Aide-mémoire associés
- [Ghidra](../../Ghidra/translations/README.fr.md)
- [GDB](../../GDB/translations/README.fr.md)
- [Élévation de privilèges Windows](../../Windows-PrivEsc/translations/README.fr.md)

---

<p align="center">
  <b>🔧 Maîtrisez le débogage Windows !</b><br>
  <i>x64dbg — le successeur moderne d’OllyDbg</i>
</p>
