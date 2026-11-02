# ⚡ Hashcat — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _                _   
 | | | | __ _ ___| |__   ___ __ _| |_ 
 | |_| |/ _` / __| '_ \ / __/ _` | __|
 |  _  | (_| \__ \ | | | (_| (_| | |_ 
 |_| |_|\__,_|___/_| |_|\___\__,_|\__|
                                       
    World's Fastest Password Recovery
```

<p align="center">
  <img src="https://img.shields.io/badge/Hashcat-GPU_Cracker-red?style=for-the-badge" alt="Hashcat">
  <img src="https://img.shields.io/badge/350+-Hash_Types-orange?style=for-the-badge" alt="Hash Types">
  <img src="https://img.shields.io/badge/GPU-Accelerated-blue?style=for-the-badge" alt="GPU">
  <img src="https://img.shields.io/badge/World's-Fastest-green?style=for-the-badge" alt="Fastest">
</p>

<p align="center">
  <b>🚀 Un outil de référence pour la récupération de mots de passe</b>
</p>

---

> **Repères de version :** les nombres de types, comparaisons, paquets et modes reflètent la source. « Plusieurs GPU » n’implique pas une distribution entre machines. L’aide officielle comporte désormais une détection automatique ; les modes, raccourcis de session et fonctions de restauration évoluent. Les exemples et valeurs restent inchangés ; consultez l’[aide officielle de Hashcat](https://hashcat.net/wiki/doku.php?id=hashcat).
>
> **Corrections de lecture :** `--outfile-format=2` produit le mot de passe en clair, pas `hash:plain`. `--benchmark-all` étend le test aux modes d’empreintes ; il ne désactive pas l’optimisation OpenCL. Les profils de charge et périphériques dépendent de la version ; le tableau source n’est pas une référence universelle des valeurs par défaut. Les empreintes tronquées avec `...` sont illustratives, et un fichier complet issu de secretsdump n’est pas nécessairement une entrée NTLM directement utilisable.
>
> **Longueur des masques :** le masque à quatre `?d` de l’exemple « 4-8 » ne dépasse pas quatre positions malgré `--increment-max=8`. L’incrémentation raccourcit le masque fourni, sans l’allonger au-delà de sa longueur ; voir la [documentation des masques](https://hashcat.net/wiki/doku.php?id=mask_attack). Le jeu `?s` original est conservé ; sa liste simplifiée omet notamment l’espace et la barre oblique inverse présents dans la définition officielle. Le dessin ASCII conserve le texte source.

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Syntaxe de base](#-syntaxe-de-base)
- [Modes d’attaque](#-modes-dattaque)
- [Modes d’empreintes](#-modes-dempreintes)
- [Attaque par masque](#-attaque-par-masque)
- [Règles](#-règles)
- [Gestion des sessions](#-gestion-des-sessions)
- [Optimisation GPU](#-optimisation-gpu)
- [Exemples pratiques](#-exemples-pratiques)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**Hashcat** est un outil de récupération de mots de passe présenté par la source comme particulièrement rapide et avancé. Il prend en charge plus de 350 types d’empreintes selon ce document et exploite l’accélération GPU.

### Principales fonctionnalités

| Fonctionnalité | Description |
| --------- | ------------- |
| **Plus de 350 types d’empreintes** | MD5, SHA, NTLM, bcrypt, WPA et d’autres |
| **Accélération GPU** | NVIDIA CUDA, AMD OpenCL, Intel |
| **Plusieurs GPU** | Prise en charge du cassage réparti selon la source |
| **5 modes d’attaque selon la source** | Dictionnaire, force brute, hybride, etc. |
| **Règles puissantes** | Transformations avancées des mots candidats |
| **Multiplateforme** | Linux, Windows, macOS |

### Hashcat et John the Ripper

| Fonctionnalité | Hashcat | John |
| --------- | --------- | ------ |
| **Vitesse** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| **Prise en charge des GPU** | Excellente | Bonne |
| **Types d’empreintes** | 350+ | 300+ |
| **Détection automatique** | ❌ Non | ✅ Oui |
| **Facilité d’utilisation** | Modérée | Plus simple |
| **Usage privilégié** | Cassage sur GPU | Cassage sur CPU |

---

## 📥 Installation

### Kali Linux
```bash
sudo apt update && sudo apt install hashcat

# Avec OpenCL
sudo apt install hashcat-nvidia  # NVIDIA
sudo apt install hashcat-amd     # AMD
```

### Ubuntu/Debian
```bash
sudo apt install hashcat
```

### Windows
```bash
# Télécharger depuis le site officiel
https://hashcat.net/hashcat/

# Extraire puis exécuter depuis le dossier
hashcat.exe -h
```

### Depuis les sources
```bash
git clone https://github.com/hashcat/hashcat.git
cd hashcat
make
./hashcat -h
```

### Vérifier l’installation
```bash
hashcat --version
hashcat -I                    # Afficher les périphériques OpenCL
hashcat -b                    # Lancer un test de performances
```

---

<a id="-syntaxe-de-base"></a>
## ⌨️ Syntaxe de base

### Syntaxe générale
```bash
hashcat [options] hash|hashfile [dictionary|mask]
```

### Options essentielles

| Option | Description |
| -------- | ------------- |
| `-m` | Type d’empreinte (mode) |
| `-a` | Mode d’attaque |
| `-o` | Fichier de sortie |
| `-r` | Fichier de règles |
| `--show` | Afficher les mots de passe retrouvés |
| `--status` | Activer l’affichage de l’état |
| `-w` | Profil de charge (1-4) |
| `--force` | Ignorer les avertissements |
| `-O` | Noyaux de calcul optimisés |

### Prise en main
```bash
# Attaque élémentaire par dictionnaire
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Afficher les empreintes résolues
hashcat -m 0 hash.txt --show

# Consulter les exemples d’empreintes
hashcat -m 0 --example-hashes
```

---

## 🎮 Modes d’attaque

### Vue d’ensemble

| Mode | Nom | Description |
| ------ | ------ | ------------- |
| `-a 0` | Dictionnaire | Attaque par liste de mots |
| `-a 1` | Combinaison | Combiner deux listes de mots |
| `-a 3` | Force brute | Attaque fondée sur un masque |
| `-a 6` | Hybride dictionnaire + masque | Liste de mots + masque |
| `-a 7` | Hybride masque + dictionnaire | Masque + liste de mots |
| `-a 9` | Association | Associations de mots |

### Attaque par dictionnaire (-a 0)

```bash
# Attaque élémentaire par dictionnaire
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Avec des règles
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rules/best64.rule

# Plusieurs listes de mots
hashcat -m 0 -a 0 hash.txt wordlist1.txt wordlist2.txt
```

### Attaque par combinaison (-a 1)

Combine les mots de deux listes.

```bash
# Combiner deux listes de mots
hashcat -m 0 -a 1 hash.txt wordlist1.txt wordlist2.txt

# Combinaisons word1word2
```

### Attaque par force brute ou masque (-a 3)

```bash
# Tout en minuscules, 6 caractères
hashcat -m 0 -a 3 hash.txt ?l?l?l?l?l?l

# Uniquement des chiffres, 4-8 caractères selon le libellé source
hashcat -m 0 -a 3 hash.txt ?d?d?d?d --increment --increment-min=4 --increment-max=8

# Jeu de caractères personnalisé
hashcat -m 0 -a 3 hash.txt -1 ?l?d ?1?1?1?1?1?1
```

### Attaques hybrides (-a 6, -a 7)

```bash
# Dictionnaire + masque (ajout à la fin)
hashcat -m 0 -a 6 hash.txt wordlist.txt ?d?d?d?d

# Masque + dictionnaire (ajout au début)
hashcat -m 0 -a 7 hash.txt ?d?d?d?d wordlist.txt
```

---

## 🔢 Modes d’empreintes

### Modes d’empreintes les plus courants

| Mode | Type d’empreinte | Exemple |
| ------ | ----------- | --------- |
| 0 | MD5 | `5d41402abc4b2a76b9719d911017c592` |
| 100 | SHA1 | `aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d` |
| 1400 | SHA256 | `2cf24dba5fb0a30e26e83b2ac5b9e29e...` |
| 1700 | SHA512 | `cf83e1357eefb8bdf1542850d66d8007d620e405...` |
| 1000 | NTLM | `32ed87bdb5fdc5e9cba88547376818d4` |
| 3000 | LM | `aad3b435b51404ee` |
| 5600 | NetNTLMv2 | Format complexe |
| 3200 | bcrypt | `$2a$10$...` |
| 1800 | sha512crypt (Linux) | `$6$...` |
| 500 | md5crypt (Linux) | `$1$...` |

### Applications web

| Mode | Type d’empreinte |
| ------ | ----------- |
| 400 | WordPress (phpass) |
| 2500 | WPA/WPA2 |
| 22000 | WPA-PBKDF2-PMKID+EAPOL |
| 121 | SMF |
| 2611 | vBulletin |
| 3711 | MediaWiki B |
| 7900 | Drupal7 |

### Bases de données

| Mode | Type d’empreinte |
| ------ | ----------- |
| 300 | MySQL4.1/MySQL5 |
| 200 | MySQL323 |
| 1731 | MSSQL (2012, 2014) |
| 112 | Oracle S |
| 12300 | Oracle T |

### Documents et archives

| Mode | Type d’empreinte |
| ------ | ----------- |
| 9600 | Office 2013 |
| 9500 | Office 2010 |
| 9400 | Office 2007 |
| 9700 | MS Office ⇐ 2003 |
| 10500 | PDF 1.4-1.6 |
| 10700 | PDF 1.7 Level 8 |
| 11600 | 7-Zip |
| 13600 | WinZip |
| 17200 | PKZIP |
| 13000 | RAR5 |
| 12500 | RAR3-hp |

### Cryptomonnaies

| Mode | Type d’empreinte |
| ------ | ----------- |
| 11300 | Portefeuille Bitcoin |
| 12700 | Portefeuille Blockchain |
| 16600 | Portefeuille Electrum |
| 15200 | Ethereum Keystore |

### Lister ou rechercher les modes
```bash
# Lister tous les modes d’empreintes
hashcat --help | grep -i "hash modes"

# Rechercher un type d’empreinte précis
hashcat --example-hashes | grep -i "ntlm"
hashcat --example-hashes | grep -i "sha256"

# Afficher un exemple du format d’empreinte
hashcat -m 1000 --example-hashes
```

---

## 🎭 Attaque par masque

### Symboles des jeux de caractères

| Symbole | Caractères |
| ------------- | ------------ |
| `?l` | abcdefghijklmnopqrstuvwxyz |
| `?u` | ABCDEFGHIJKLMNOPQRSTUVWXYZ |
| `?d` | 0123456789 |
| `?h` | 0123456789abcdef |
| `?H` | 0123456789ABCDEF |
| `?s` | !"#$%&'()*+,-./:;<=>?@[]^_`{\|}~ |
| `?a` | ?l?u?d?s |
| `?b` | 0x00 - 0xff |

### Jeux de caractères personnalisés

```bash
# Définir un jeu de caractères personnalisé
hashcat -a 3 -1 ?l?d hash.txt ?1?1?1?1?1?1

# Plusieurs jeux de caractères personnalisés
hashcat -a 3 -1 abc -2 123 hash.txt ?1?2?1?2

# Caractères spéciaux
hashcat -a 3 -1 '!@#$%' hash.txt pass?1?1?1?1
```

### Mode incrémental

```bash
# Essayer les longueurs 1-8
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment

# Préciser les longueurs minimale et maximale
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment --increment-min=4 --increment-max=8
```

### Motifs de masque courants

```bash
# 8 lettres minuscules
?l?l?l?l?l?l?l?l

# Mot de passe + 4 chiffres
password?d?d?d?d

# Majuscule + minuscules + chiffres
?u?l?l?l?l?l?d?d

# Motif Winter2024
?u?l?l?l?l?l?d?d?d?d

# Numéro de téléphone
?d?d?d?d?d?d?d?d?d?d
```

---

## 📜 Règles

### Fichiers de règles fournis

```bash
# Emplacement
ls /usr/share/hashcat/rules/

# Règles populaires
best64.rule           # Les 64 règles de best64
rockyou-30000.rule    # Les 30000 règles de rockyou
d3ad0ne.rule          # Règles de D3ad0ne
dive.rule             # Règles de Dive
generated.rule        # Règles générées
```

### Utiliser les règles

```bash
# Un fichier de règles
hashcat -m 0 -a 0 hash.txt wordlist.txt -r best64.rule

# Plusieurs fichiers de règles
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rule1.rule -r rule2.rule

# Générer des règles
hashcat -m 0 -a 0 hash.txt wordlist.txt -g 1000
```

### Opérations de règles courantes

| Règle | Description | Exemple |
| ------ | ------------- | --------- |
| `:` | Ne rien modifier | password |
| `l` | Tout mettre en minuscules | PASSWORD → password |
| `u` | Tout mettre en majuscules | password → PASSWORD |
| `c` | Mettre la première lettre en majuscule | password → Password |
| `C` | Première lettre en minuscule, autres en majuscules | password → pASSWORD |
| `t` | Inverser la casse | password → PASSWORD |
| `r` | Inverser l’ordre | password → drowssap |
| `d` | Dupliquer | pass → passpass |
| `$X` | Ajouter X à la fin | pass → passX |
| `^X` | Ajouter X au début | pass → Xpass |
| `sXY` | Remplacer X par Y | password → pa$$word |

---

## 💾 Gestion des sessions

### Créer une session
```bash
# Session nommée
hashcat -m 0 -a 0 hash.txt wordlist.txt --session=mysession

# Fichiers de session enregistrés dans ~/.hashcat/sessions/ selon la source
```

### Mettre en pause et reprendre
```bash
# Pause : appuyer sur 'p' pendant le cassage
# Reprise : appuyer sur 'r' pendant le cassage

# Ou restaurer depuis la ligne de commande
hashcat --session=mysession --restore
```

### Commandes d’état

Pendant le cassage :
- `s` - Afficher l’état
- `p` - Mettre en pause
- `r` - Reprendre
- `b` - Passer à la suite (ignorer l’attaque en cours)
- `c` - Point de reprise
- `q` - Quitter

### Options de sortie
```bash
# Écrire dans un fichier
hashcat -m 0 -a 0 hash.txt wordlist.txt -o cracked.txt

# Format de sortie
hashcat -m 0 hash.txt --outfile-format=2    # hash:plain

# Afficher les résultats retrouvés
hashcat -m 0 hash.txt --show

# Afficher les empreintes restantes (non résolues)
hashcat -m 0 hash.txt --left
```

---

## 🚀 Optimisation GPU

### Profils de charge

| Profil | Description |
| --------- | ------------- |
| `-w 1` | Faible (présenté comme celui par défaut et interactif dans la source) |
| `-w 2` | Moyen |
| `-w 3` | Élevé (peut bloquer l’interface du bureau) |
| `-w 4` | Nightmare (machine dédiée au cassage) |

### Commandes d’optimisation
```bash
# Hautes performances
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 3 -O

# Noyaux optimisés (plus rapides, longueur des mots de passe limitée)
hashcat -m 0 -a 0 hash.txt wordlist.txt -O

# Imposer un périphérique
hashcat -m 0 -a 0 hash.txt wordlist.txt -d 1

# Désactiver le CPU, utiliser uniquement les GPU
hashcat -m 0 -a 0 hash.txt wordlist.txt -D 2
```

### Options des périphériques
```bash
# Lister les périphériques
hashcat -I

# Sélectionner un périphérique précis
hashcat -d 1 ...

# Types de périphériques
# -D 1 = CPU
# -D 2 = GPU
# -D 3 = FPGA
```

### Test de performances
```bash
# Test complet de performances
hashcat -b

# Type d’empreinte précis
hashcat -b -m 0

# Test sans optimisation OpenCL selon le libellé source
hashcat -b --benchmark-all
```

---

## 🎬 Exemples pratiques

### Exemple 1 : attaque MD5 par dictionnaire
```bash
hashcat -m 0 -a 0 md5_hashes.txt /usr/share/wordlists/rockyou.txt
```

### Exemple 2 : NTLM avec règles
```bash
hashcat -m 1000 -a 0 ntlm_hashes.txt wordlist.txt -r /usr/share/hashcat/rules/best64.rule
```

### Exemple 3 : échange d’authentification WPA/WPA2
```bash
# Convertir d’abord au format Hashcat
hcxpcapngtool -o hash.hc22000 capture.pcapng

# Casser les empreintes
hashcat -m 22000 hash.hc22000 /usr/share/wordlists/rockyou.txt
```

### Exemple 4 : SHA256 par force brute (6 caractères)
```bash
hashcat -m 1400 -a 3 sha256_hash.txt ?a?a?a?a?a?a
```

### Exemple 5 : document Office 2013
```bash
# Extraire l’empreinte avec office2john
office2john document.docx > office.hash

# Casser les empreintes
hashcat -m 9600 office.hash wordlist.txt
```

### Exemple 6 : mot de passe Linux du fichier shadow
```bash
hashcat -m 1800 -a 0 shadow_hash.txt wordlist.txt
```

### Exemple 7 : empreinte WordPress
```bash
hashcat -m 400 -a 0 wordpress_hash.txt wordlist.txt
```

### Exemple 8 : attaque hybride
```bash
# Nom d’entreprise + 4 chiffres
hashcat -m 0 -a 6 hash.txt company_names.txt ?d?d?d?d
```

### Exemple 9 : empreintes d’un domaine Windows
```bash
# Empreintes NTLM issues de secretsdump
hashcat -m 1000 -a 0 ntds_hashes.txt wordlist.txt -r best64.rule --username
```

### Exemple 10 : portefeuille Bitcoin
```bash
# Extraire avec bitcoin2john
hashcat -m 11300 bitcoin_hash.txt wordlist.txt
```

---

## 📊 Référence rapide

### Commandes essentielles

| Tâche | Commande |
| ------ | --------- |
| MD5 par dictionnaire | `hashcat -m 0 -a 0 hash.txt wordlist.txt` |
| NTLM par force brute | `hashcat -m 1000 -a 3 hash.txt ?a?a?a?a?a?a` |
| SHA256 avec règles | `hashcat -m 1400 -a 0 hash.txt wordlist.txt -r rules.rule` |
| Afficher les résultats retrouvés | `hashcat -m 0 hash.txt --show` |
| Test de performances | `hashcat -b` |
| Lister les périphériques | `hashcat -I` |
| Restaurer une session | `hashcat --session=name --restore` |

### Modes d’empreintes courants

| Mode | Type |
| ------ | ------ |
| 0 | MD5 |
| 100 | SHA1 |
| 1000 | NTLM |
| 1400 | SHA256 |
| 1800 | sha512crypt |
| 3200 | bcrypt |
| 22000 | WPA2 |

### Modes d’attaque

| Mode | Type |
| ------ | ------ |
| -a 0 | Dictionnaire |
| -a 1 | Combinaison |
| -a 3 | Force brute |
| -a 6 | Hybride (dictionnaire + masque) |
| -a 7 | Hybride (masque + dictionnaire) |

---

## 💡 Conseils et bonnes pratiques

### Conseils d’efficacité

1. **Commencer par un dictionnaire et des règles**
   ```bash
   hashcat -m 0 -a 0 hash.txt rockyou.txt -r best64.rule
   ```

2. **Utiliser les noyaux optimisés**
   ```bash
   hashcat -O ...
   ```

3. **Cibler les motifs courants**
   ```bash
   # Saison + année : Winter2024
   hashcat -a 3 -1 WSFJ -2 winterumeall hash.txt ?1?2?2?2?2?2?d?d?d?d
   ```

### Conseils de performance

```bash
# Performances maximales (machine dédiée)
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 4 -O --force

# Plusieurs GPU
hashcat -d 1,2 ...
```

### Déroulement du travail

```
1. Identifier le type d’empreinte
2. Essayer un dictionnaire avec des règles
3. Essayer des masques ciblés (motifs)
4. Essayer la force brute complète si cela reste réalisable
5. Essayer les attaques hybrides
```

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** Hashcat est un outil puissant de cassage de mots de passe, réservé à des **usages autorisés**.
> 
> - ✅ Auditer vos propres systèmes
> - ✅ Évaluations de sécurité avec autorisation
> - ✅ Récupération de vos propres mots de passe
> - ❌ Ne jamais casser de mots de passe sans autorisation
> - ❌ L’accès non autorisé est illégal
> 
> **Le cassage de mots de passe sans autorisation est illégal et peut entraîner des poursuites pénales.**

---

## 📚 Ressources

### Ressources officielles
- [Site officiel de Hashcat](https://hashcat.net/hashcat/)
- [Wiki Hashcat](https://hashcat.net/wiki/)
- [Hashcat GitHub](https://github.com/hashcat/hashcat)
- [Forum Hashcat](https://hashcat.net/forum/)

### Listes de mots
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Fiches connexes
- [John the Ripper](../../John-The-Ripper/translations/README.fr.md)
- [Hydra](../../Hydra/translations/README.fr.md)
- [Nmap](../../Nmap/translations/README.fr.md)

---

<p align="center">
  <b>⚡ Maîtrisez le cassage de mots de passe sur GPU !</b><br>
  <i>La récupération de mots de passe à haute performance</i>
</p>
