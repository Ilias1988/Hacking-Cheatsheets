# 🔄 Fiche pratique de rétro-ingénierie pour les CTF

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Reverse-Engineering-CTF.tr.md) • [🇬🇷 Ελληνικά](Reverse-Engineering-CTF.el.md)

---

## 🔍 Analyse initiale

```bash
# Type de fichier
file binary

# Chaînes de caractères
strings binary | grep -i flag
strings -n 8 binary

# Informations ELF
readelf -h binary

# Vérifier les protections
checksec binary
```

---

## 🛠️ Désassembleurs

### Ghidra (gratuit)

> **Correction des repères Ghidra :** les étapes originales ci-dessous utilisent `Analyze` et `F5`. Le [guide officiel Ghidra](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html) indique `Analysis → Auto Analyze` et `Window → Decompiler`. `F5` est notamment le raccourci documenté pour [IDA/Hex-Rays](https://hex-rays.com/blog/igors-tip-of-the-week-40-decompiler-basics) ; il ne faut pas transposer automatiquement les raccourcis entre ces outils.

> **Précision :** les noms des menus anglais et raccourcis sont conservés pour retrouver les commandes. Leur comportement dépend de l’outil, de la configuration et, pour Hex-Rays, du décompilateur disponible.
```
1. Utiliser File → Import File pour importer le fichier
2. Lancer Analyze → Auto Analyze
3. Rechercher la fonction main()
4. Rechercher les appels strcmp/memcmp utilisés pour vérifier le mot de passe
5. Appuyer sur F5 pour décompiler
```

### IDA Free
```
1. Ouvrir le binaire
2. Appuyer sur F5 pour afficher le pseudocode avec Hex-Rays
3. Appuyer sur X pour rechercher les références croisées
4. Appuyer sur N pour renommer les variables
```

### Radare2
```bash
r2 binary
aaa          # Analyser
afl          # Lister les fonctions
pdf @main    # Désassembler main
VV           # Mode visuel
```

---

## 🐛 Débogage avec GDB

```bash
# Démarrer
gdb ./binary

# Points d’arrêt
b main
b *0x401234

# Exécuter
r
r arg1 arg2

# Exécuter pas à pas
n           # Ligne suivante
s           # Entrer dans la fonction
c           # Continuer

# Examiner
x/s $rdi    # Chaîne de caractères
x/10x $rsp  # 10 valeurs hexadécimales de la pile
info reg    # Registres

# Rechercher le flag
find 0x0, 0xffffffff, "flag"
```

### GEF/PEDA
```bash
# Installer GEF
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Commandes
vmmap
heap
telescope $rsp
```

---

## 🐍 Modification du binaire avec Python

```python
# Lire le binaire
with open("binary", "rb") as f:
    data = bytearray(f.read())

# Modifier les octets
data[0x1234] = 0x90  # NOP

# Écrire
with open("patched", "wb") as f:
    f.write(data)
```

---

## 📊 Motifs courants

| Vérification | Contournement |
|-------|--------|
| `strcmp(input, "password")` | Extraire "password" |
| `if (x == 0x1337)` | Rechercher la valeur 0x1337 |
| Anti-débogage | Modifier le contrôle ptrace |
| Chaînes obscurcies | Analyse dynamique |

---

## 📋 Liste de contrôle de rétro-ingénierie

```markdown
□ Utiliser les commandes file et strings
□ Rechercher les symboles de débogage
□ Ouvrir le binaire dans Ghidra ou IDA
□ Trouver la fonction main
□ Rechercher les comparaisons de chaînes
□ Suivre le traitement de l’entrée utilisateur
□ Neutraliser les contrôles anti-débogage si nécessaire
□ Réaliser une analyse dynamique avec GDB
```

---

**Retour aux CTF :** [🏁 Vue d’ensemble des CTF](./README.fr.md)
