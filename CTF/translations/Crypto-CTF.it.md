# 🔐 Scheda di riferimento per le CTF di crittografia

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Crypto-CTF.md) • [🇹🇷 Türkçe Sürüm](Crypto-CTF.tr.md) • [🇬🇷 Ελληνικά](Crypto-CTF.el.md)

---

## 🔤 Codifica e decodifica

### Base64
```bash
echo "string" | base64           # Codifica
echo "c3RyaW5n" | base64 -d      # Decodifica
```

### Hex
```bash
echo "string" | xxd -p           # Codifica
echo "737472696e67" | xxd -r -p  # Decodifica
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

> **Nota sulla codifica:** `echo` aggiunge normalmente un carattere di nuova riga; questo cambia i byte codificati rispetto alla sola stringa. I comandi originali sono conservati. La codifica è una rappresentazione dei dati, non una cifratura.

## 🔑 RSA

### Matematica di base di RSA
```
n = p * q           (modulo pubblico)
φ(n) = (p-1)(q-1)   (funzione totiente di Eulero)
d = e⁻¹ mod φ(n)    (chiave privata)
c = m^e mod n       (cifratura)
m = c^d mod n       (decifratura)
```

> **Nota sulla notazione:** qui `^` indica l'elevamento a potenza nella notazione matematica; in Python è invece l'operatore XOR. Le formule RSA presuppongono due primi distinti e un esponente pubblico invertibile modulo φ(n). Un esponente piccolo, da solo, non basta a rendere vulnerabile ogni istanza di RSA.

### RsaCtfTool
```bash
# Installazione
pip3 install rsactftool

# Attacco
rsactftool --publickey pub.pem --private
rsactftool --publickey pub.pem --uncipherfile cipher.txt
```

### Fattorizzazione
```
factordb.com - Fattorizzazione online
```

---

## 🔒 Recupero delle password dagli hash (hash cracking)

### Identifica l'hash
```bash
hashid 'hash_value'
hash-identifier
```

### Recupera la password
```bash
# John
john --wordlist=rockyou.txt hash.txt

# Hashcat
hashcat -m 0 hash.txt rockyou.txt      # MD5
hashcat -m 100 hash.txt rockyou.txt    # SHA1
hashcat -m 1400 hash.txt rockyou.txt   # SHA256
```

### Online
```
crackstation.net
hashes.com
```

---

## 🧮 XOR

```python
# XOR in Python
def xor(data, key):
    return bytes([b ^ key[i % len(key)] for i, b in enumerate(data)])

# Ricerca esaustiva di una chiave XOR di un solo byte
for key in range(256):
    result = bytes([b ^ key for b in ciphertext])
```

---

## 🛠️ Strumenti

| Strumento | Utilizzo |
|------|-----|
| **CyberChef** | gchq.github.io/CyberChef |
| **dcode.fr** | Codifiche e cifrari di vario tipo |
| **RsaCtfTool** | Attacchi a RSA |
| **hashcat/john** | Recupero delle password dagli hash |
| **factordb** | Fattorizzazione di interi |

---

## 📋 Checklist di crittografia

```markdown
□ Identifica la codifica (base64, hex, rot13)
□ Identifica il tipo di cifrario
□ Cerca debolezze in RSA (e piccolo, numeri primi vicini)
□ Prova i database di ricerca degli hash
□ Verifica se la chiave XOR si ripete
□ Prova l'operazione Magic di CyberChef
```

---

**Torna alle CTF:** [🏁 Panoramica CTF](./README.it.md)
