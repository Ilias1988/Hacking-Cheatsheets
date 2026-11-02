# 🏁 Fiches pratiques pour les CTF

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 ██████╗████████╗███████╗
██╔════╝╚══██╔══╝██╔════╝
██║        ██║   █████╗  
██║        ██║   ██╔══╝  
╚██████╗   ██║   ██║     
 ╚═════╝   ╚═╝   ╚═╝     
 ██████╗██╗  ██╗███████╗ █████╗ ████████╗███████╗██╗  ██╗███████╗███████╗████████╗███████╗
██╔════╝██║  ██║██╔════╝██╔══██╗╚══██╔══╝██╔════╝██║  ██║██╔════╝██╔════╝╚══██╔══╝██╔════╝
██║     ███████║█████╗  ███████║   ██║   ███████╗███████║█████╗  █████╗     ██║   ███████╗
██║     ██╔══██║██╔══╝  ██╔══██║   ██║   ╚════██║██╔══██║██╔══╝  ██╔══╝     ██║   ╚════██║
╚██████╗██║  ██║███████╗██║  ██║   ██║   ███████║██║  ██║███████╗███████╗   ██║   ███████║
 ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝   ╚═╝   ╚══════╝
```

---

## 📑 Table des matières

| Catégorie | Description | Guide |
|----------|-------------|-------|
| **Web** | SQLi, XSS, SSTI, LFI, contournement de l’authentification | [📄 Consulter](./Web-CTF.fr.md) |
| **Cryptographie** | RSA, AES, empreintes, encodage | [📄 Consulter](./Crypto-CTF.fr.md) |
| **Rétro-ingénierie** | Désassemblage, débogage, modification des binaires | [📄 Consulter](./Reverse-Engineering-CTF.fr.md) |
| **Investigation numérique** | Stéganographie, analyse de la mémoire et des disques | [📄 Consulter](./Forensics-CTF.fr.md) |
| **Pwn / binaire** | Débordement de tampon, ROP, shellcode | [📄 Consulter](./Pwn-CTF.fr.md) |

---

## 🎯 Plateformes de CTF

> **Repères :** CTF signifie *Capture The Flag*. Le flag est la chaîne attendue pour valider le défi ; les formats et catégories ci-dessous sont des exemples.

| Plateforme | URL | Type |
|----------|-----|------|
| **HackTheBox** | hackthebox.com | Machines et CTF |
| **TryHackMe** | tryhackme.com | Apprentissage guidé |
| **PicoCTF** | picoctf.org | Adapté aux débutants |
| **CTFtime** | ctftime.org | Calendrier des CTF |
| **OverTheWire** | overthewire.org | Jeux de défis (wargames) |
| **RootMe** | root-me.org | Défis |
| **CryptoHack** | cryptohack.org | Défis de cryptographie |
| **pwnable.kr** | pwnable.kr | Défis d’exploitation binaire |

---

## 🛠️ Outils essentiels pour les CTF

| Catégorie | Outils |
|----------|-------|
| **Web** | Burp Suite, SQLMap, ffuf |
| **Cryptographie** | CyberChef, RsaCtfTool, hashcat |
| **Rétro-ingénierie** | Ghidra, IDA, Radare2, GDB |
| **Investigation numérique** | Volatility, Autopsy, Binwalk |
| **Pwn** | pwntools, ROPgadget, gef |
| **Stéganographie** | steghide, zsteg, exiftool |

---

## 🔥 Commandes rapides pour les CTF

### Web
```bash
# SQLi
sqlmap -u "http://url?id=1" --dbs

# Force brute sur les répertoires
gobuster dir -u http://url -w /usr/share/wordlists/dirb/common.txt
```

### Cryptographie
```bash
# Identification du type d’empreinte
hashid 'hash_string'
hash-identifier

# Décodage Base64
echo "string" | base64 -d
```

### Investigation numérique
```bash
# Type de fichier
file unknown_file
binwalk unknown_file

# Chaînes de caractères
strings -n 6 file | grep -i flag
```

### Pwn
```bash
# Vérifier les protections
checksec binary

# Exécuter avec GDB
gdb ./binary
```

---

## 📋 Méthodologie des CTF

```
1. RECONNAISSANCE
   - Lire attentivement l’énoncé du défi
   - Télécharger tous les fichiers
   - Identifier la catégorie (web/cryptographie/pwn/etc.)

2. ÉNUMÉRATION
   - Utiliser les outils appropriés
   - Rechercher les vulnérabilités évidentes
   - Rechercher des indices dans les noms de fichiers et les métadonnées

3. EXPLOITATION
   - Appliquer les techniques connues
   - Rechercher les messages d’erreur dans Google
   - Essayer les charges utiles courantes

4. CAPTURE DU FLAG
   - Rechercher le format du flag : CTF{...}
   - Examiner toutes les sorties
   - Soumettre le flag et vérifier sa validité
```

---

## 🏆 Formats de flags

```
Formats de flags courants :
- flag{...}
- FLAG{...}
- CTF{...}
- picoCTF{...}
- HTB{...}
- DUCTF{...}
- custom_flag_format
```

---

**Retour à l’accueil :** [🔴 Hacking Cheatsheets](../../README.md)
