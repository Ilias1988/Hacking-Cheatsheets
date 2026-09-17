# 🔐 Crypto CTF Cheatsheet

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

---

## 🔤 Encoding/Decoding

### Base64
```bash
echo "string" | base64           # Encode
echo "c3RyaW5n" | base64 -d      # Decode
```

### Hex
```bash
echo "string" | xxd -p           # Encode
echo "737472696e67" | xxd -r -p  # Decode
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

### Basic RSA Math
```
n = p * q           (public modulus)
φ(n) = (p-1)(q-1)   (totient)
d = e⁻¹ mod φ(n)    (private key)
c = m^e mod n       (encryption)
m = c^d mod n       (decryption)
```

### RsaCtfTool
```bash
# Install
pip3 install rsactftool

# Attack
rsactftool --publickey pub.pem --private
rsactftool --publickey pub.pem --uncipherfile cipher.txt
```

### Factorization
```
factordb.com - Online factorization
```

---

## 🔒 Hash Cracking

### Identify Hash
```bash
hashid 'hash_value'
hash-identifier
```

### Crack
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
# Python XOR
def xor(data, key):
    return bytes([b ^ key[i % len(key)] for i, b in enumerate(data)])

# Single byte XOR brute
for key in range(256):
    result = bytes([b ^ key for b in ciphertext])
```

---

## 🛠️ Tools

| Tool | Use |
|------|-----|
| **CyberChef** | gchq.github.io/CyberChef |
| **dcode.fr** | All encodings/ciphers |
| **RsaCtfTool** | RSA attacks |
| **hashcat/john** | Hash cracking |
| **factordb** | Integer factorization |

---

## 📋 Crypto Checklist

```markdown
□ Identify encoding (base64, hex, rot13)
□ Identify cipher type
□ Check for weak RSA (small e, close primes)
□ Try hash lookup databases
□ Check for repeating XOR key
□ Use CyberChef magic
```

---

**Back to CTF:** [🏁 CTF Overview](./README.md)
