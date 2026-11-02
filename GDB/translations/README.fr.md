# 🐛 GDB — aide-mémoire du débogueur GNU

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██████╗ ██████╗ 
  ██╔════╝ ██╔══██╗██╔══██╗
  ██║  ███╗██║  ██║██████╔╝
  ██║   ██║██║  ██║██╔══██╗
  ╚██████╔╝██████╔╝██████╔╝
   ╚═════╝ ╚═════╝ ╚═════╝ 
    Débogueur GNU
```

<p align="center">
  <img src="https://img.shields.io/badge/GDB-Debugger-blue?style=for-the-badge" alt="GDB">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="Rétro-ingénierie">
  <img src="https://img.shields.io/badge/Exploit_Dev-red?style=for-the-badge" alt="Développement d’exploits">
</p>

---

## 📋 Table des matières

- [Qu’est-ce que GDB ?](#-quest-ce-que-gdb-)
- [Installation et configuration](#-installation-et-configuration)
- [Utilisation de base](#-utilisation-de-base)
- [Points d’arrêt](#-points-darrêt)
- [Contrôle de l’exécution](#-contrôle-de-lexécution)
- [Examiner la mémoire](#-examiner-la-mémoire)
- [Registres](#-registres)
- [Analyse de la pile](#-analyse-de-la-pile)
- [GEF/PEDA/pwndbg](#-gefpedapwndbg)
- [Développement d’exploits](#-développement-dexploits)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce que GDB ?

**GDB** (GNU Debugger, débogueur GNU) est le débogueur de référence sous Linux. Il est essentiel pour :

- 🐛 **Débogage** — Rechercher et corriger des erreurs
- 🔍 **Rétro-ingénierie** — Comprendre le comportement d’un binaire
- 💥 **Développement d’exploits** — Dépassements de tampon et programmation orientée retour (ROP)
- 📊 **Analyse binaire** — Examiner le déroulement de l’exécution
- 🧪 **Épreuves CTF** — Résoudre des défis de rétro-ingénierie (RE) et d’exploitation binaire (pwn)

### Architectures prises en charge

| Architecture | Remarques |
| -------------- | ------- |
| x86 | Intel 32 bits |
| x86-64 | Intel/AMD 64 bits |
| ARM | ARM 32 bits |
| ARM64 | ARM 64 bits |
| MIPS | Processeurs MIPS |
| PowerPC | Processeurs PPC |

---

## 🚀 Installation et configuration

### Installer GDB

```bash
# Debian/Ubuntu/Kali
sudo apt install gdb

# Fedora/RHEL
sudo dnf install gdb

# Arch Linux
sudo pacman -S gdb

# macOS (utiliser lldb, ou bien)
brew install gdb
```

### Installer GEF (GDB Enhanced Features)

```bash
# GEF — préférence exprimée dans l’original
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Ou manuellement
wget -O ~/.gdbinit-gef.py -q https://gef.blah.cat/py
echo source ~/.gdbinit-gef.py >> ~/.gdbinit
```

### Installer PEDA

```bash
git clone https://github.com/longld/peda.git ~/peda
echo "source ~/peda/peda.py" >> ~/.gdbinit
```

### Installer pwndbg

```bash
git clone https://github.com/pwndbg/pwndbg
cd pwndbg
./setup.sh
```

### Vérifier l’installation

```bash
gdb --version
gdb -q
(gdb) quit
```

---

## 💻 Utilisation de base

### Démarrer GDB

> **Précision :** les invites `(gdb)`, `gef➤`, `peda➤`, `pwndbg>` et `$` distinguent des environnements différents. La ligne shell `gdb attach <PID>` est conservée de l’original, mais ne représente pas la commande interactive `attach` ; l’invocation dépend des arguments documentés. Registres et adresses supposent aussi une architecture et une configuration compatibles ([manuel GDB](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Invoking-GDB.html)).

```bash
# Déboguer un exécutable
gdb ./program

# Déboguer avec des arguments
gdb --args ./program arg1 arg2

# S’attacher à un processus en cours
gdb -p <PID>
gdb attach <PID>

# Mode silencieux (sans bannière)
gdb -q ./program

# Exécuter les commandes d’un fichier
gdb -x commands.gdb ./program
```

### Commandes de base

| Commande | Abréviation | Description |
| --------- | ------- | ------------- |
| `run` | `r` | Démarrer le programme |
| `run args` | `r args` | Exécuter avec des arguments |
| `continue` | `c` | Poursuivre l’exécution |
| `quit` | `q` | Quitter GDB |
| `help` | `h` | Afficher l’aide |
| `help <cmd>` | `h <cmd>` | Aide sur une commande |

### Commandes liées aux fichiers

```bash
# Charger un exécutable
(gdb) file ./program

# Charger les symboles
(gdb) symbol-file ./program.debug

# Charger une image mémoire de plantage (core dump)
(gdb) core-file core

# Définir le répertoire de travail
(gdb) cd /path/to/dir

# Définir les arguments
(gdb) set args arg1 arg2
```

---

## 🔴 Points d’arrêt

### Définir des points d’arrêt

```bash
# Arrêter à l’entrée d’une fonction
(gdb) break main
(gdb) b main

# Arrêter à une adresse
(gdb) break *0x08048456
(gdb) b *0x08048456

# Arrêter à une ligne
(gdb) break file.c:25
(gdb) b 25

# Arrêter à un décalage depuis une fonction
(gdb) break *main+48

# Point d’arrêt temporaire (supprimé automatiquement)
(gdb) tbreak main
```

### Points d’arrêt conditionnels

```bash
# Arrêter lorsque la condition est vraie
(gdb) break main if argc > 2
(gdb) break *0x08048456 if $eax == 0

# Ajouter une condition à un point d’arrêt existant
(gdb) condition 1 $eax == 0

# Supprimer la condition
(gdb) condition 1
```

### Gérer les points d’arrêt

```bash
# Lister les points d’arrêt
(gdb) info breakpoints
(gdb) info b

# Supprimer un point d’arrêt
(gdb) delete 1
(gdb) d 1

# Supprimer tous les points d’arrêt
(gdb) delete
(gdb) d

# Désactiver un point d’arrêt
(gdb) disable 1
(gdb) dis 1

# Activer un point d’arrêt
(gdb) enable 1
(gdb) en 1

# Tout désactiver
(gdb) disable

# Tout activer
(gdb) enable
```

### Points de surveillance des données (watchpoints)

> **Précision :** un watchpoint arrête sur un changement de valeur de l’expression, pas simplement tant qu’elle est vraie. Les points de lecture ou d’accès dépendent de ressources matérielles limitées et de la cible ([manuel GDB](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Set-Watchpoints.html)).

```bash
# Surveiller les changements d’une variable
(gdb) watch variable
(gdb) watch *0x08048456

# Surveiller les lectures
(gdb) rwatch variable

# Surveiller les lectures ou écritures
(gdb) awatch variable

# Surveiller une expression
(gdb) watch ($eax > 10)
```

### Points d’arrêt sur événements (catchpoints)

```bash
# Intercepter les appels système
(gdb) catch syscall

# Intercepter un appel système précis
(gdb) catch syscall write

# Intercepter les signaux
(gdb) catch signal SIGSEGV

# Intercepter les exceptions
(gdb) catch throw
(gdb) catch catch
```

---

<a id="-contrôle-de-lexécution"></a>
## ▶️ Contrôle de l’exécution

### Exécution pas à pas

```bash
# Exécuter une instruction en entrant dans les appels
(gdb) stepi
(gdb) si

# Exécuter une ligne en entrant dans les appels
(gdb) step
(gdb) s

# Exécuter sans entrer dans les appels
(gdb) nexti
(gdb) ni

# Exécuter une ligne sans entrer dans les appels
(gdb) next
(gdb) n

# Poursuivre jusqu’au retour de la fonction
(gdb) finish
(gdb) fin

# Poursuivre jusqu’à une adresse
(gdb) until *0x08048456
(gdb) u *0x08048456
```

### Exécuter le programme

> **Précision :** l’argument de `continue 5` concerne les prochaines occurrences du point d’arrêt à l’emplacement courant après un arrêt sur ce point ; il ne saute pas arbitrairement cinq points d’arrêt différents ([manuel GDB](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Continuing-and-Stepping.html)).

```bash
# Démarrer ou redémarrer le programme
(gdb) run
(gdb) r

# Poursuivre après un point d’arrêt
(gdb) continue
(gdb) c

# Exécuter jusqu’au prochain point d’arrêt
(gdb) continue

# Ignorer N occurrences de point d’arrêt
(gdb) continue 5

# Sauter à une adresse (opération dangereuse !)
(gdb) jump *0x08048456
```

### Contrôle des processus

```bash
# Terminer le processus courant
(gdb) kill

# Se détacher du processus
(gdb) detach

# S’attacher à un processus
(gdb) attach <PID>

# Suivre une création de processus (parent/enfant)
(gdb) set follow-fork-mode child
(gdb) set follow-fork-mode parent
```

---

## 🔍 Examiner la mémoire

### Commande print

```bash
# Afficher une variable
(gdb) print variable
(gdb) p variable

# Afficher en hexadécimal
(gdb) print/x variable
(gdb) p/x $eax

# Afficher en binaire
(gdb) p/t variable

# Afficher en décimal
(gdb) p/d variable

# Afficher comme caractère
(gdb) p/c $al

# Afficher comme chaîne
(gdb) p/s pointer

# Afficher un tableau
(gdb) p *array@10     # 10 éléments
```

### Commande d’examen (x)

> **Précision :** sans taille explicite, `x/10x` réutilise la taille d’unité précédente ; elle vaut initialement quatre octets, mais peut changer. Les exemples « mots » du tableau ne sont donc pas une garantie indépendante de l’état du débogueur ([manuel GDB](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Memory.html)).

```bash
# Syntaxe : x/NFU address
# N = nombre, F = format, U = taille de l’unité

# Formats : x(hexadécimal), d(décimal), u(non signé), o(octal),
#          t(binaire), c(caractère), s(chaîne), i(instruction)
# Unités : b(octet), h(demi-mot=2), w(mot=4), g(grand mot=8)

# Examiner la mémoire
(gdb) x/10x $esp           # 10 mots hexadécimaux à partir d’ESP
(gdb) x/20wx 0x08048456    # 20 mots en hexadécimal
(gdb) x/s 0x08048456       # Chaîne à cette adresse
(gdb) x/10i $eip           # 10 instructions à partir d’EIP
(gdb) x/10gx $rsp          # 10 unités de 8 octets à partir de RSP

# Formes courantes
(gdb) x/100x $esp          # Examiner la pile
(gdb) x/20i $eip           # Désassembler
(gdb) x/s $eax             # Pointeur vers une chaîne
```

### Régions mémoire

```bash
# Afficher la carte mémoire
(gdb) info proc mappings

# Afficher les sections
(gdb) maintenance info sections

# Afficher les fichiers
(gdb) info files
```

### Désassemblage

> **Version des exemples :** le modificateur `/m` est déprécié pour le mélange source/assembleur, notamment avec du code optimisé ou intégré en ligne. Les commandes originales restent inchangées. Les variantes Intel/AT&T concernent la famille x86 ([manuel GDB](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Machine-Code.html)).

```bash
# Désassembler une fonction
(gdb) disassemble main
(gdb) disas main

# Désassembler la fonction courante
(gdb) disassemble
(gdb) disas

# Désassembler une plage
(gdb) disas 0x08048456, 0x08048500

# Désassembler avec le code source (si disponible)
(gdb) disas /m main

# Définir la syntaxe
(gdb) set disassembly-flavor intel
(gdb) set disassembly-flavor att
```

---

## 📊 Registres

### Afficher les registres

```bash
# Tous les registres
(gdb) info registers
(gdb) i r

# Registre précis
(gdb) info registers eax
(gdb) p $eax
(gdb) p/x $rip

# Tous les registres, y compris FP/SSE
(gdb) info all-registers
```

### Modifier les registres

```bash
# Définir la valeur d’un registre
(gdb) set $eax = 0
(gdb) set $eip = 0x08048456
(gdb) set $rip = 0x400000

# Définir les indicateurs (flags)
(gdb) set $eflags = 0x246
```

### Référence des registres x86

| Registre | 64 bits | 32 bits | 16 bits | 8 bits | Rôle |
| ---------- | -------- | -------- | -------- | ------- | --------- |
| Accumulateur | RAX | EAX | AX | AL/AH | Valeur de retour |
| Base | RBX | EBX | BX | BL/BH | Usage général |
| Compteur | RCX | ECX | CX | CL/CH | Compteur de boucle |
| Données | RDX | EDX | DX | DL/DH | Entrées/sorties, multiplication |
| Index source | RSI | ESI | SI | SIL | Source des opérations sur chaînes |
| Index destination | RDI | EDI | DI | DIL | Destination des opérations sur chaînes |
| Pointeur de base | RBP | EBP | BP | BPL | Cadre de pile |
| Pointeur de pile | RSP | ESP | SP | SPL | Sommet de la pile |
| Instruction | RIP | EIP | IP | - | Instruction suivante |

### Registres supplémentaires x64

```
R8-R15 : Registres à usage général
R8D-R15D : Parties basses de 32 bits
R8W-R15W : Parties basses de 16 bits
R8B-R15B : Parties basses de 8 bits
```

---

## 📚 Analyse de la pile

### Afficher la pile

```bash
# Trace des appels de la pile
(gdb) backtrace
(gdb) bt

# Trace complète avec les variables locales
(gdb) backtrace full
(gdb) bt full

# Afficher le cadre de pile
(gdb) frame
(gdb) f

# Sélectionner un cadre de pile
(gdb) frame 2
(gdb) f 2

# Afficher le contenu de la pile
(gdb) x/50wx $esp    # 32 bits
(gdb) x/50gx $rsp    # 64 bits
```

### Cadre de pile (stack frame)

```bash
# Afficher les informations du cadre de pile
(gdb) info frame
(gdb) i f

# Afficher les variables locales
(gdb) info locals

# Afficher les arguments
(gdb) info args

# Monter ou descendre dans les cadres de pile
(gdb) up
(gdb) down
```

### Commandes liées à la pile

> **Précision :** les positions relatives à EBP/RBP supposent une disposition de cadre adaptée à l’architecture et à la convention d’appel. Les optimisations peuvent supprimer le pointeur de cadre ; ces décalages ne sont pas universels ([cadres de pile GDB](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Frames.html)).

```bash
# Examiner l’adresse de retour
(gdb) x/wx $ebp+4     # 32 bits
(gdb) x/gx $rbp+8     # 64 bits

# Examiner EBP sauvegardé
(gdb) x/wx $ebp       # 32 bits
(gdb) x/gx $rbp       # 64 bits

# Afficher l’organisation de la pile
(gdb) x/20wx $esp-20  # Avant et après
```

---

## 🎨 GEF/PEDA/pwndbg

> **Précision :** ce sont des extensions distinctes de GDB. Leurs commandes, alias et dépendances varient selon la version et les modules supplémentaires ; les listes historiques ne garantissent pas la présence de chaque commande. La préférence pour GEF dans le commentaire d’installation est celle de l’original ([GEF](https://hugsy.github.io/gef/), [pwndbg](https://github.com/pwndbg/pwndbg), [PEDA](https://github.com/longld/peda)).

### Commandes GEF

```bash
# Affichage du contexte (automatique)
gef➤ context

# Checksec (mécanismes de protection)
gef➤ checksec

# Rechercher un motif
gef➤ pattern create 200
gef➤ pattern search $rsp

# Rechercher une chaîne ou une valeur
gef➤ search-pattern "/bin/sh"
gef➤ grep "/bin/sh"

# Analyse du tas (heap)
gef➤ heap chunks
gef➤ heap bins

# Gadgets ROP
gef➤ ropgadget

# Assistant pour les chaînes de format
gef➤ format-string-helper

# VMMap (carte mémoire)
gef➤ vmmap

# Chaîne de déréférencement
gef➤ dereference $rsp 20

# Telescope (déréférencement intelligent)
gef➤ telescope $rsp 20

# Informations ELF
gef➤ elf-info

# Valeur du canari
gef➤ canary

# Informations PIE/ASLR
gef➤ pie
gef➤ aslr
```

### Commandes PEDA

```bash
# Vérification des protections
peda➤ checksec

# Rechercher une chaîne
peda➤ find "/bin/sh"
peda➤ searchmem "/bin/sh"

# Créer ou rechercher un motif
peda➤ pattern create 200
peda➤ pattern offset $eip

# Gadgets ROP
peda➤ ropgadget
peda➤ ropsearch "pop rdi"

# Shellcode (code machine injecté)
peda➤ shellcode generate x86/linux exec

# Contexte
peda➤ context all

# Assemblage
peda➤ assemble
```

### Commandes pwndbg

```bash
# Contexte
pwndbg> context

# Checksec
pwndbg> checksec

# Recherche
pwndbg> search -s "/bin/sh"
pwndbg> search -p 0xdeadbeef

# Motif cyclique
pwndbg> cyclic 200
pwndbg> cyclic -l <value>

# ROP
pwndbg> rop --grep "pop rdi"

# Tas
pwndbg> heap
pwndbg> bins
pwndbg> arena

# GOT/PLT
pwndbg> got
pwndbg> plt
```

---

## 💥 Développement d’exploits

### Rechercher le décalage (offset)

> **Précision :** le premier exemple envoie des `A` répétés, pas le motif cyclique qu’il vient de créer. Le second ne crée pas `input.txt` et l’affichage Python 3 d’un objet bytes inclut sa représentation ; il mélange aussi les commandes d’extensions. Les lignes sont conservées comme schéma incomplet, sans garantir un décalage exploitable ([motifs GEF](https://hugsy.github.io/gef/commands/pattern/), [pwntools](https://docs.pwntools.com/en/stable/util/cyclic.html)).

```bash
# Méthode 1 : motif (GEF)
gef➤ pattern create 200
gef➤ run < <(python3 -c 'print("A"*200)')
gef➤ pattern search $rsp

# Méthode 2 : motif (pwntools)
$ python3 -c "from pwn import *; print(cyclic(200))"
gef➤ run < input.txt
gef➤ cyclic -l 0x61616167
```

### Principes du dépassement de tampon

> **Précision :** la modification de `/proc/sys/kernel/randomize_va_space` concerne la politique globale du noyau. Le réglage GDB présenté plus bas vise les processus qu’il lance et est normalement activé par défaut sur les cibles prises en charge ; il ne réorganise pas un processus déjà attaché ([manuel GDB](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Starting.html)).

```bash
# Vérifier l’ASLR
$ cat /proc/sys/kernel/randomize_va_space
# 0=désactivé, 1=partiel, 2=complet

# Désactiver l’ASLR (pour les tests)
$ echo 0 | sudo tee /proc/sys/kernel/randomize_va_space

# Vérifier les protections du binaire
gef➤ checksec

# Repérer l’emplacement de l’adresse de retour
(gdb) x/20wx $esp     # Avant le plantage
(gdb) i f             # Informations sur le cadre de pile
```

### Rechercher des gadgets

```bash
# Rechercher "/bin/sh"
gef➤ search-pattern "/bin/sh"

# Rechercher l’adresse de system()
(gdb) p system
(gdb) p &system

# Rechercher un gadget pop rdi (64 bits)
gef➤ ropgadget --grep "pop rdi"

# Rechercher pop; ret (32 bits)
gef➤ ropgadget --grep "pop"
```

### Écrire en mémoire

```bash
# Définir une valeur en mémoire
(gdb) set {int}0x08048456 = 0x41414141
(gdb) set {char}0x08048456 = 'A'

# Écrire une chaîne
(gdb) set {char[10]}0x08048456 = "AAAA"

# Appeler une fonction
(gdb) call system("/bin/sh")
```

### Commandes utiles pour les exploits

```bash
# Suivre les processus enfants
(gdb) set follow-fork-mode child

# Désactiver l’ASLR dans GDB
(gdb) set disable-randomization on

# Afficher les bibliothèques partagées
(gdb) info sharedlibrary

# Rechercher la base de libc
gef➤ vmmap libc

# Rechercher PLT/GOT
(gdb) info functions @plt
gef➤ got
```

---

## 📊 Référence rapide

### Commandes essentielles

| Commande | Description |
| --------- | ------------- |
| `r` | Exécuter le programme |
| `c` | Continuer |
| `b *addr` | Définir un point d’arrêt |
| `si` | Exécuter une instruction en entrant dans les appels |
| `ni` | Exécuter une instruction sans entrer dans les appels |
| `fin` | Poursuivre jusqu’au retour de la fonction |
| `x/Nx addr` | Examiner la mémoire |
| `p $reg` | Afficher un registre |
| `i r` | Informations sur les registres |
| `bt` | Trace des appels de fonctions |
| `disas` | Désassembler |
| `q` | Quitter |

### Examen de la mémoire

| Commande | Description |
| --------- | ------------- |
| `x/10x $esp` | 10 unités en hexadécimal à partir d’ESP |
| `x/s addr` | Chaîne à l’adresse indiquée |
| `x/i $eip` | Instruction à EIP |
| `x/20wx addr` | 20 mots en hexadécimal |
| `x/10gx $rsp` | 10 unités de 8 octets à partir de RSP |

### Débogage rapide

```bash
# Démarrer et arrêter à main
$ gdb -q ./program
(gdb) break main
(gdb) run

# Examiner un plantage
(gdb) run < payload
(gdb) x/10i $eip
(gdb) i r
(gdb) bt
```

### Déroulement du développement d’un exploit

```
1. checksec           → Vérifier les protections
2. pattern create     → Créer un motif
3. exécuter avec le motif → Faire planter le programme avec ce motif
4. pattern search     → Rechercher le décalage
5. rechercher des gadgets → Rechercher des gadgets ROP
6. construire la charge utile → Créer l’exploit
7. tester             → Déboguer avec GDB
```

---

## 📚 Ressources

- [Manuel officiel de GDB](https://sourceware.org/gdb/current/onlinedocs/gdb/)
- [Documentation GEF](https://hugsy.github.io/gef/)
- [Documentation pwndbg](https://github.com/pwndbg/pwndbg)
- [PEDA](https://github.com/longld/peda)

### Aide-mémoire associés
- [Ghidra](../../Ghidra/translations/README.fr.md)
- [Commandes Linux](../../Linux-Commands/translations/README.fr.md)
- [Élévation de privilèges Linux](../../Linux-PrivEsc/translations/README.fr.md)

---

<p align="center">
  <b>🐛 Maîtrisez le débogage sous Linux !</b><br>
  <i>GDB — l’outil essentiel de la rétro-ingénierie sous Linux</i>
</p>
