# 🔐 Hurtigreferanse for kryptografi i CTF

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Crypto-CTF.tr.md) • [🇬🇷 Ελληνικά](Crypto-CTF.el.md)

---

## 🔤 Koding og dekoding

### Base64
```bash
echo "string" | base64           # Kod
echo "c3RyaW5n" | base64 -d      # Dekod
```

### Heksadesimal
```bash
echo "string" | xxd -p           # Kod
echo "737472696e67" | xxd -r -p  # Dekod
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

### Grunnleggende RSA-matematikk

> **Presisering:** Formlene forutsetter vanlige RSA-betingelser, blant annet forskjellige primtall og en inverterbar e modulo φ(n). d er den private eksponenten, ikke hele den private nøkkelen. Ren RSA-matematikk viser ikke sikker padding. XOR-snippettene forutsetter at data/nøkkel eller ciphertext allerede er definert; kodingen Base64/hex er ikke kryptering.
```
n = p * q           (offentlig modulus)
φ(n) = (p-1)(q-1)   (Eulers totientfunksjon)
d = e⁻¹ mod φ(n)    (privat eksponent)
c = m^e mod n       (kryptering)
m = c^d mod n       (dekryptering)
```

### RsaCtfTool
```bash
# Installer
pip3 install rsactftool

# Angrep
rsactftool --publickey pub.pem --private
rsactftool --publickey pub.pem --uncipherfile cipher.txt
```

### Faktorisering
```
factordb.com - Nettbasert faktorisering
```

---

## 🔒 Knekking av hasher

### Identifisere hashtypen
```bash
hashid 'hash_value'
hash-identifier
```

### Knekke hashen
```bash
# John
john --wordlist=rockyou.txt hash.txt

# Hashcat
hashcat -m 0 hash.txt rockyou.txt      # MD5
hashcat -m 100 hash.txt rockyou.txt    # SHA1
hashcat -m 1400 hash.txt rockyou.txt   # SHA256
```

### Nettbaserte tjenester
```
crackstation.net
hashes.com
```

---

## 🧮 XOR

```python
# XOR i Python
def xor(data, key):
    return bytes([b ^ key[i % len(key)] for i, b in enumerate(data)])

# Prøv alle nøkler for XOR med én byte
for key in range(256):
    result = bytes([b ^ key for b in ciphertext])
```

---

## 🛠️ Verktøy

| Verktøy | Bruk |
|------|-----|
| **CyberChef** | gchq.github.io/CyberChef |
| **dcode.fr** | Koding og chifre |
| **RsaCtfTool** | RSA-angrep |
| **hashcat/john** | Knekking av hasher |
| **factordb** | Faktorisering av heltall |

---

## 📋 Sjekkliste for kryptografi

```markdown
□ Identifiser kodingen (base64, hex, rot13)
□ Identifiser typen chiffer
□ Se etter svak RSA (liten e, primtall som ligger nær hverandre)
□ Prøv oppslagsdatabaser for hasher
□ Se etter en gjentakende XOR-nøkkel
□ Bruk Magic-funksjonen i CyberChef
```

---

**Tilbake til CTF:** [🏁 CTF-oversikt](./README.nb.md)
