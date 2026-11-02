# 🔍 Ghidra — aide-mémoire de rétro-ingénierie

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██╗  ██╗██╗██████╗ ██████╗  █████╗ 
  ██╔════╝ ██║  ██║██║██╔══██╗██╔══██╗██╔══██╗
  ██║  ███╗███████║██║██║  ██║██████╔╝███████║
  ██║   ██║██╔══██║██║██║  ██║██╔══██╗██╔══██║
  ╚██████╔╝██║  ██║██║██████╔╝██║  ██║██║  ██║
   ╚═════╝ ╚═╝  ╚═╝╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝
              Suite de rétro-ingénierie de la NSA
```

<p align="center">
  <img src="https://img.shields.io/badge/Ghidra-Reverse_Engineering-blue?style=for-the-badge" alt="Ghidra">
  <img src="https://img.shields.io/badge/NSA-Open_Source-green?style=for-the-badge" alt="NSA">
  <img src="https://img.shields.io/badge/FREE-red?style=for-the-badge" alt="Gratuit">
</p>

---

## 📋 Table des matières

- [Qu’est-ce que Ghidra ?](#-quest-ce-que-ghidra-)
- [Installation](#-installation)
- [Présentation de l’interface](#-présentation-de-linterface)
- [Créer un projet](#-créer-un-projet)
- [Bases de l’analyse](#-bases-de-lanalyse)
- [Navigation](#-navigation)
- [Décompilateur](#-décompilateur)
- [Types de données et structures](#-types-de-données-et-structures)
- [Appliquer des correctifs et modifier](#-appliquer-des-correctifs-et-modifier)
- [Scripts](#-scripts)
- [Raccourcis clavier](#-raccourcis-clavier)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce que Ghidra ?

**Ghidra** est une suite gratuite et open source de rétro-ingénierie logicielle (*software reverse engineering*, SRE), développée par la NSA. Elle propose :

- 🔍 **Désassembleur** — Convertir le binaire en langage assembleur
- 🔄 **Décompilateur** — Produire un pseudo-code proche du C
- 📊 **Analyse** — Analyser automatiquement le code
- 🔧 **Modification (patching)** — Modifier des binaires
- 🐍 **Scripts** — Automatiser avec Python ou Java
- 👥 **Collaboration** — Travailler à plusieurs utilisateurs

### Architectures prises en charge

| Architecture | Variantes |
| -------------- | ---------- |
| **x86** | 16/32/64-bit |
| **ARM** | ARM32, ARM64, Thumb |
| **MIPS** | 32/64-bit |
| **PowerPC** | 32/64-bit |
| **SPARC** | 32/64-bit |
| **RISC-V** | 32/64-bit |
| **AVR** | 8-bit |
| **6502** | 8-bit |

### Formats pris en charge

| Format | Description |
| -------- | ------------- |
| ELF | Exécutables Linux |
| PE/COFF | Exécutables Windows |
| Mach-O | Exécutables macOS |
| Binaire brut | Micrologiciels (firmware), ROM |
| APK/DEX | Applications Android |
| et bien d’autres… |

---

## 🚀 Installation

### Téléchargement

```bash
# Télécharger depuis le site officiel
https://ghidra-sre.org/

# Ou depuis les versions GitHub
https://github.com/NationalSecurityAgency/ghidra/releases
```

### Prérequis

> **Exemple historique :** le JDK 17 et le tableau ci-dessous reprennent l’original. Le dépôt officiel actuel demande un JDK 25 64 bits ; vérifier les prérequis de la version téléchargée ([projet officiel](https://github.com/NationalSecurityAgency/ghidra)).

| Prérequis | Version |
| ------------- | --------- |
| **JDK** | 17+ (64-bit) |
| **RAM** | 4GB minimum, 8GB+ recommandés |
| **Disque** | 1GB+ pour l’installation |

### Linux/macOS

```bash
# Installer le JDK 17+
sudo apt install openjdk-17-jdk  # Debian/Ubuntu
brew install openjdk@17          # macOS

# Extraire Ghidra
unzip ghidra_*.zip
cd ghidra_*

# Lancer
./ghidraRun
```

### Windows

```batch
:: Installer le JDK 17+ depuis Oracle ou Adoptium
:: Extraire ghidra_*.zip
:: Lancer ghidraRun.bat
```

---

<a id="-présentation-de-linterface"></a>
## 🖥️ Présentation de l’interface

### Fenêtres principales

> **Précision :** les libellés anglais facilitent le repérage dans l’interface. Les actions et raccourcis dépendent de la fenêtre active, de la version et des réglages ; plusieurs associations du guide sont indicatives ou incohérentes, notamment `P`. L’aide contextuelle documente l’action effective ([guide officiel](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html)).

| Fenêtre | Description |
| -------- | ------------- |
| **Program Trees** | Structure du programme dans le projet |
| **Symbol Tree** | Fonctions, étiquettes, classes |
| **Data Type Manager** | Types de données définis |
| **Listing** | Vue du désassemblage |
| **Decompiler** | Pseudo-code C |
| **Console** | Sorties et scripts |
| **Defined Strings** | Chaînes de caractères repérées |
| **Bookmarks** | Emplacements enregistrés |

### Fenêtre Listing (désassemblage)

```
Adresse    | Octets         | Désassemblage         | Commentaires
-----------|----------------|-----------------------|----------
00401000   | 55             | PUSH EBP              | Début de fonction
00401001   | 89 e5          | MOV EBP, ESP          |
00401003   | 83 ec 10       | SUB ESP, 0x10         | Cadre de pile
00401006   | e8 f5 ff ff ff | CALL FUN_00401000     |
```

---

## 📁 Créer un projet

### Nouveau projet

```
1. File → New Project (Fichier → Nouveau projet)
2. Choisir :
   - Non-Shared Project (projet local non partagé)
   - Shared Project (projet partagé pour collaborer)
3. Sélectionner le répertoire
4. Saisir le nom du projet
5. Cliquer sur Finish (Terminer)
```

### Importer un binaire

```
1. File → Import File (I) : importer un fichier
2. Sélectionner le binaire ou le fichier
3. Choisir le format, généralement détecté automatiquement
4. Régler les options :
   - Language/Processor : langage ou processeur
   - Compiler spec : spécification du compilateur
5. Cliquer sur OK
6. Analyze? → Yes : accepter l’analyse (recommandé)
```

### Options d’analyse

| Option | Description |
| -------- | ------------- |
| ASCII Strings | Rechercher les chaînes de caractères |
| Decompiler | Activer la décompilation |
| Stack Analysis | Analyser les cadres de pile |
| Reference Analysis | Rechercher les références croisées |
| Function ID | Reconnaître les fonctions de bibliothèques |
| Call Convention | Identifier les conventions d’appel |

---

## 🔬 Bases de l’analyse

### Analyse automatique

```
Analysis → Auto Analyze (A)
- Exécute tous les analyseurs sélectionnés
- Peut prendre du temps pour les gros binaires
- La progression s’affiche dans le moniteur des tâches
```

### Analyseurs ponctuels

```
Analysis → One Shot → [Analyzer]
- ASCII Strings : chaînes ASCII
- Decompiler Parameter ID : identification des paramètres par le décompilateur
- Function ID : identification des fonctions
- Stack Analysis : analyse de la pile
```

### Identification des fonctions

> **Précision :** « Non-returning Functions » concerne les fonctions qui ne reviennent pas à leur appelant ; cette option ne constitue pas à elle seule une recherche de toutes les fonctions. L’analyse automatique combine plusieurs analyseurs et peut nécessiter une définition manuelle ([guide officiel](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html)).

```
# Rechercher toutes les fonctions
Analysis → Auto Analyze → Activer "Non-returning Functions"

# Créer une fonction manuellement
Clic droit sur l’adresse → Create Function (F)

# Code non défini
Clic droit → Disassemble (D)
```

---

## 🧭 Navigation

### Aller à une adresse

```
# Raccourci clavier
G → Saisir l’adresse → OK

# Exemples :
0x00401000      # Adresse hexadécimale
main            # Nom de fonction
entry           # Point d’entrée
LAB_00401234    # Étiquette
```

### Raccourcis de navigation

| Raccourci | Action |
| ---------- | -------- |
| `G` | Aller à une adresse |
| `Ctrl+G` | Aller à une adresse |
| `Alt+←` | Revenir à l’emplacement précédent |
| `Alt+→` | Aller à l’emplacement suivant |
| `Ctrl+Shift+E` | Aller au point d’entrée |

### Références croisées (XREF)

```
# Afficher les références vers l’emplacement courant
Clic droit → References → Show References To

# Rechercher les appels d’une fonction
Sélectionner la fonction → Clic droit → References → Show References To

# Raccourci clavier
Ctrl+Shift+F → Rechercher les références
```

### Fonctions de recherche

```
# Recherche
Search → Memory (S)           # Rechercher dans la mémoire
Search → For Strings          # Rechercher les chaînes
Search → For Direct References # Rechercher les références directes vers l’adresse
Search → Program Text         # Rechercher dans le désassemblage

# Recherche de fonction
Symbol Tree → Filter → Saisir le nom de la fonction
```

---

## 💻 Décompilateur

### Ouvrir le décompilateur

```
Window → Decompiler
# Ou cliquer sur l’onglet Decompiler
# Décompile automatiquement la fonction sélectionnée
```

### Lire le code décompilé

> **Précision :** le pseudo-code est une reconstruction dépendant des types et des signatures déduits ; ce n’est pas la restitution garantie du code source original ([guide officiel](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html)).

```c
// Exemple de sortie du décompilateur
undefined4 main(int param_1, char **param_2)
{
  char local_20 [16];
  
  printf("Enter password: ");
  scanf("%15s", local_20);
  if (strcmp(local_20, "secret123") == 0) {
    puts("Access granted!");
  }
  else {
    puts("Access denied!");
  }
  return 0;
}
```

### Améliorer la décompilation

```
# Renommer les variables
Clic droit sur la variable → Rename Variable (L)

# Changer le type de données
Clic droit sur la variable → Retype Variable (Ctrl+L)

# Ajouter des commentaires
; → Définir un commentaire de fin de ligne
Ctrl+; → Définir un commentaire d’en-tête

# Définir la signature de fonction
Clic droit sur la fonction → Edit Function Signature
```

### Astuces du décompilateur

| Action | Méthode |
| -------- | ----- |
| Renommer une variable | `L` ou clic droit |
| Changer le type | `Ctrl+L` |
| Créer une structure | Clic droit → Auto Create Structure |
| Séparer ou fusionner des variables | Clic droit → Split/Merge |
| Figer la signature | `P` |

---

## 📊 Types de données et structures

### Créer une structure

```
# Méthode 1 : depuis le gestionnaire de types
Data Type Manager → Clic droit → New → Structure

# Méthode 2 : création automatique depuis le décompilateur
Decompiler → Clic droit sur la variable → Auto Create Structure
```

### Définir une structure

```c
// Exemple de structure
struct Player {
    char name[32];      // décalage 0x00
    int health;         // décalage 0x20
    int score;          // décalage 0x24
    float position[3];  // décalage 0x28
};
```

### Appliquer une structure

```
# Dans la vue Listing
Sélectionner l’adresse → Clic droit → Data → Choose Data Type

# Ou
Appuyer sur T → Sélectionner la structure

# Dans le décompilateur
Clic droit sur la variable → Retype Variable → Sélectionner la structure
```

### Tableaux

```
# Créer un tableau
Sélectionner l’adresse de début → Clic droit → Data → Create Array
Saisir le nombre d’éléments
```

### Énumérations

```
# Créer une énumération
Data Type Manager → Clic droit → New → Enum

# Définir les valeurs
enum Status {
    INACTIVE = 0,
    ACTIVE = 1,
    PAUSED = 2
};
```

---

## 🔧 Appliquer des correctifs et modifier

### Modifier une instruction

```
# Méthode 1 : clavier
Sélectionner l’instruction → Ctrl+Shift+G
Saisir la nouvelle instruction → Appuyer sur Entrée

# Méthode 2 : menu
Clic droit → Patch Instruction
```

### Modifier des données

```
# Modifier les octets
Clic droit → Patch Data
Saisir les octets hexadécimaux

# Ou
Sélectionner les octets → Ctrl+Shift+G
```

### Modifications courantes

> **Précision :** les remplacements sont schématiques : taille, encodage et architecture comptent. Cinq NOP ne correspondent pas à toute instruction CALL, et changer une condition ne garantit pas un programme cohérent. Les modifications portent d’abord sur la base d’analyse.

| Original | Modifié | Objectif |
| ---------- | --------- | --------- |
| `JE` | `JMP` | Toujours effectuer le saut |
| `JNE` | `JMP` | Toujours effectuer le saut |
| `JE` | `NOP` | Ne jamais effectuer le saut |
| `CALL` | `NOP NOP NOP NOP NOP` | Ignorer l’appel |
| `74` | `75` | JE → JNE |
| `75` | `74` | JNE → JE |
| `74` | `EB` | JE → JMP court |

### Exporter le binaire modifié

> **Précision :** l’export binaire brut ne préserve pas nécessairement l’organisation du fichier exécutable. L’export « Original File » traite la disposition d’origine et les octets modifiés ; les menus et formats du bloc sont conservés comme dans l’original ([exports officiels](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html)).

```
File → Export Program
Format: Binary
Sélectionner la destination
```

### Suite d’instructions NOP (NOP sled)

```
# Remplir avec des NOP
Sélectionner la plage → Clic droit → Clear Code Block
Puis : Select → Clic droit → Patch → Fill with NOPs
```

---

## 🐍 Scripts

### Gestionnaire de scripts

```
Window → Script Manager
# Ou : Tools → Script Manager
```

### Exécuter un script

```
Script Manager → Sélectionner le script → Run (bouton de lecture vert)
# Ou double-cliquer sur le script
```

### Scripts Python (Ghidra Python)

> **Précision :** l’exemple contient une f-string Python 3, incompatible avec l’ancien interpréteur Jython 2.7. PyGhidra fournit un contexte CPython 3 ; `currentProgram` et les fonctions utilitaires supposent l’environnement de script Ghidra, tandis que `addr` et `length` restent des variables à définir ([PyGhidra officiel](https://github.com/NationalSecurityAgency/ghidra/blob/master/Ghidra/Features/PyGhidra/src/main/py/README.md)).

```python
# Exemple : lister toutes les fonctions
from ghidra.program.model.listing import Function

fm = currentProgram.getFunctionManager()
funcs = fm.getFunctions(True)  # True = parcours vers l’avant

for func in funcs:
    print(f"Function: {func.getName()} at {func.getEntryPoint()}")
```

### Opérations courantes des scripts

```python
# Obtenir l’adresse courante
currentAddress

# Obtenir le programme courant
currentProgram

# Obtenir le gestionnaire de fonctions
fm = currentProgram.getFunctionManager()

# Obtenir le Listing (désassemblage)
listing = currentProgram.getListing()

# Obtenir l’instruction à l’adresse indiquée
instr = listing.getInstructionAt(addr)

# Obtenir les octets à l’adresse indiquée
memory = currentProgram.getMemory()
bytes = getBytes(addr, length)

# Créer une étiquette
createLabel(addr, "my_label", True)

# Ajouter un commentaire
setEOLComment(addr, "My comment")

# Obtenir les références
refMgr = currentProgram.getReferenceManager()
refs = refMgr.getReferencesTo(addr)
```

### Scripts intégrés utiles

> **Précision :** les noms ci-dessous sont indicatifs : disponibilité, nom exact et origine intégrée ou tierce varient selon la distribution et les extensions. Vérifier la liste réelle du Script Manager.

| Script | Objectif |
| -------- | --------- |
| FindCrypt | Rechercher les constantes cryptographiques |
| FunctionID | Reconnaître les fonctions de bibliothèques |
| ExportFunctionInfoScript | Exporter la liste des fonctions |
| FindStrings | Rechercher les références aux chaînes |
| SearchMemory | Rechercher des motifs |

---

<a id="-raccourcis-clavier"></a>
## ⌨️ Raccourcis clavier

### Navigation

| Raccourci | Action |
| ---------- | -------- |
| `G` | Aller à une adresse |
| `Alt+←` | Précédent |
| `Alt+→` | Suivant |
| `Ctrl+Shift+E` | Point d’entrée |
| `Ctrl+E` | Exporter un tableau |
| `N` | Emplacement non défini suivant |

### Analyse

| Raccourci | Action |
| ---------- | -------- |
| `F` | Créer une fonction |
| `D` | Désassembler |
| `C` | Effacer la définition |
| `P` | Créer un pointeur |
| `T` | Appliquer un type de données |
| `A` | Lancer l’analyse automatique |

### Modification

| Raccourci | Action |
| ---------- | -------- |
| `L` | Renommer une étiquette ou une variable |
| `;` | Définir un commentaire de fin de ligne |
| `Ctrl+;` | Définir un commentaire d’en-tête (plate comment) |
| `Ctrl+L` | Changer le type d’une variable |
| `Ctrl+Shift+G` | Modifier une instruction |

### Recherche

| Raccourci | Action |
| ---------- | -------- |
| `Ctrl+Shift+F` | Rechercher les références |
| `Ctrl+F` | Rechercher du texte |
| `S` | Rechercher dans la mémoire |
| `Ctrl+B` | Rechercher des octets |

### Fenêtres

| Raccourci | Action |
| ---------- | -------- |
| `Ctrl+D` | Afficher ou masquer le décompilateur |
| `Ctrl+T` | Afficher ou masquer le gestionnaire de types |
| `Ctrl+S` | Arbre des symboles |

---

## 📊 Référence rapide

### Déroulement de l’analyse

```
1. Importer le binaire → File → Import
2. Analyser automatiquement → Analysis → Auto Analyze (accepter les réglages par défaut)
3. Rechercher entry/main → G → main ou entry
4. Examiner les fonctions → Symbol Tree → Functions
5. Examiner les chaînes → Search → For Strings
6. Suivre l’exécution → Suivre les appels et les sauts
7. Renommer les symboles → L sur les fonctions ou variables
8. Documenter → Ajouter des commentaires (;)
```

### Tâches courantes

| Tâche | Méthode |
| ------ | ----- |
| Rechercher main() | `G` → `main` ou rechercher dans Symbol Tree |
| Afficher les chaînes | Search → For Strings |
| Rechercher la cryptographie | Exécuter le script FindCrypt |
| Examiner les imports | Symbol Tree → Imports |
| Rechercher les références croisées | Sélectionner → Ctrl+Shift+F |
| Créer une fonction | Sélectionner l’adresse → `F` |
| Décompiler | Window → Decompiler |

### Liste de vérification de rétro-ingénierie

```
☐ Identifier le type de fichier (PE, ELF, etc.)
☐ Lancer l’analyse automatique
☐ Rechercher le point d’entrée / main
☐ Examiner les chaînes pour trouver des indices
☐ Identifier les fonctions intéressantes
☐ Représenter le déroulement du programme
☐ Analyser les fonctions suspectes
☐ Documenter les découvertes avec des commentaires
☐ Renommer les fonctions et les variables
☐ Créer les structures nécessaires
```

### Conventions d’appel des fonctions

> **Précision :** ce tableau résume surtout les arguments entiers de conventions courantes. Les flottants, structures, arguments supplémentaires et choix du compilateur suivent d’autres règles ; les registres de retour ne sont pas universels ([ABI x64 Microsoft](https://learn.microsoft.com/en-us/cpp/build/x64-calling-convention)).

| Convention | Retour | Arguments | Nettoyage de la pile |
| ------------ | -------- | ------ | ---------------- |
| **cdecl** | EAX | Pile (droite → gauche) | Appelant |
| **stdcall** | EAX | Pile (droite → gauche) | Fonction appelée |
| **fastcall** | EAX | ECX, EDX, pile | Fonction appelée |
| **x64 Win** | RAX | RCX, RDX, R8, R9 | Appelant |
| **x64 Linux** | RAX | RDI, RSI, RDX, RCX, R8, R9 | Appelant |

---

## 📚 Ressources

- [Site officiel de Ghidra](https://ghidra-sre.org/)
- [Ghidra sur GitHub](https://github.com/NationalSecurityAgency/ghidra)
- [Documentation de Ghidra](https://ghidra.re/ghidra_docs/api/)
- [Awesome Ghidra](https://github.com/AllsafeCyberSecurity/awesome-ghidra)

### Aide-mémoire associés
- [Binwalk](../../Binwalk/translations/README.fr.md)
- [Commandes Linux](../../Linux-Commands/translations/README.fr.md)
- [Volatility](../../Volatility/translations/README.fr.md)

---

<p align="center">
  <b>🔍 Maîtrisez la rétro-ingénierie !</b><br>
  <i>Ghidra — le cadeau de la NSA à la communauté de la sécurité</i>
</p>
