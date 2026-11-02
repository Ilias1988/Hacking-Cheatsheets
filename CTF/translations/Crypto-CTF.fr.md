# 🔐 Fiche pratique de cryptographie pour les CTF

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Crypto-CTF.tr.md) • [🇬🇷 Ελληνικά](Crypto-CTF.el.md)

---

## 🔤 Encodage et décodage

> **Précision :** l’encodage (Base64, hexadécimal, ROT13) n’assure pas la confidentialité. Les exemples `echo` peuvent inclure un saut de ligne dans les données ; la valeur encodée fournie pour le décodage est conservée telle quelle. Les conditions mathématiques de RSA supposent notamment des nombres premiers distincts et un exposant public inversible modulo φ(n).

### Base64
```bash
echo "string" | base64           # Encoder
echo "c3RyaW5n" | base64 -d      # Décoder
```

### Hexadécimal
```bash
echo "string" | xxd -p           # Encoder
echo "737472696e67" | xxd -r -p  # Décoder
```

### ROT13
```bash
echo "string" | tr 'A-Za-z' 'N-ZA-Mn-za-m'
```

### ASCII
```python
# Python
chr(65)      # → 'A'
ord('A')     # → 65
```

---

## 🔑 RSA

### Principes mathématiques de RSA
```
n = p * q           (module public)
φ(n) = (p-1)(q-1)   (indicatrice d’Euler)
d = e⁻¹ mod φ(n)    (clé privée)
c = m^e mod n       (chiffrement)
m = c^d mod n       (déchiffrement)
```

### RsaCtfTool
```bash
# Installer
pip3 install rsactftool

# Attaquer
rsactftool --publickey pub.pem --private
rsactftool --publickey pub.pem --uncipherfile cipher.txt
```

### Factorisation
```
factordb.com — Factorisation en ligne
```

---

## 🔒 Cassage d’empreintes (hashes)

### Identifier le type d’empreinte
```bash
hashid 'hash_value'
hash-identifier
```

### Casser une empreinte
```bash
# John
john --wordlist=rockyou.txt hash.txt

# Hashcat
hashcat -m 0 hash.txt rockyou.txt      # MD5
hashcat -m 100 hash.txt rockyou.txt    # SHA1
hashcat -m 1400 hash.txt rockyou.txt   # SHA256
```

### Services en ligne
```
crackstation.net
hashes.com
```

---

## 🧮 XOR

```python
# XOR en Python
def xor(data, key):
    return bytes([b ^ key[i % len(key)] for i, b in enumerate(data)])

# Force brute d’une clé XOR d’un octet
for key in range(256):
    result = bytes([b ^ key for b in ciphertext])
```

---

## 🛠️ Outils

| Outil | Utilité |
|------|-----|
| **CyberChef** | gchq.github.io/CyberChef |
| **dcode.fr** | Encodages et chiffrements variés |
| **RsaCtfTool** | Attaques contre RSA |
| **hashcat/john** | Cassage d’empreintes |
| **factordb** | Factorisation d’entiers |

---

## 📋 Liste de contrôle de cryptographie

```markdown
□ Identifier l’encodage (base64, hex, rot13)
□ Identifier le type de chiffrement
□ Rechercher des faiblesses RSA (petit e, nombres premiers proches)
□ Consulter les bases de correspondance d’empreintes
□ Rechercher une clé XOR répétée
□ Utiliser l’opération Magic de CyberChef
```

---

**Retour aux CTF :** [🏁 Vue d’ensemble des CTF](./README.fr.md)
