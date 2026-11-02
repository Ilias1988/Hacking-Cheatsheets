# 🔐 Kriptografi CTF Kopya Kağıdı (Crypto CTF Cheatsheet)

---

## 🔤 Kodlama ve Çözme (Encoding / Decoding)

### Base64
```bash
echo "metin" | base64           # Kodlama (Encode)
echo "bWV0aW4=" | base64 -d      # Çözme (Decode)
```

### Onaltılık (Hex)
```bash
echo "metin" | xxd -p           # Kodlama (Encode)
echo "6d6574696e" | xxd -r -p   # Çözme (Decode)
```

### ROT13
```bash
echo "metin" | tr 'A-Za-z' 'N-ZA-Mn-za-m'
```

### ASCII Karakter Dönüşümü
```python
# Python
chr(65)      # → 'A'
ord('A')     # → 65
```

---

## 🔑 RSA Şifreleme

### Temel RSA Matematiği
```
n = p * q           (Açık modül / public modulus)
φ(n) = (p-1)(q-1)   (Euler totient fonksiyonu)
d = e⁻¹ mod φ(n)    (Özel anahtar / private key)
c = m^e mod n       (Şifreleme / encryption)
m = c^d mod n       (Şifre çözme / decryption)
```

### RsaCtfTool ile Çözüm
```bash
# Kurulum
pip3 install rsactftool

# Özel anahtarı kurtarma
rsactftool --publickey pub.pem --private

# Şifreli metni doğrudan çözme
rsactftool --publickey pub.pem --uncipherfile cipher.txt
```

### Asal Çarpanlara Ayırma (Factorization)
```
factordb.com - Çevrim içi tam sayı çarpanlara ayırma veritabanı
```

---

## 🔒 Hash Kırma

### Hash Türünü Belirleme
```bash
hashid 'hash_degeri'
hash-identifier
```

### Kırma Komutları
```bash
# John the Ripper
john --wordlist=rockyou.txt hash.txt

# Hashcat
hashcat -m 0 hash.txt rockyou.txt      # MD5
hashcat -m 100 hash.txt rockyou.txt    # SHA1
hashcat -m 1400 hash.txt rockyou.txt   # SHA256
```

### Çevrim İçi Hash Veritabanları
```
crackstation.net
hashes.com
```

---

## 🧮 XOR İşlemleri

```python
# Python XOR fonksiyonu
def xor(data, key):
    return bytes([b ^ key[i % len(key)] for i, b in enumerate(data)])

# Tek baytlık XOR kaba kuvvet (brute force)
for key in range(256):
    result = bytes([b ^ key for b in ciphertext])
```

---

## 🛠️ Araçlar

| Araç | Kullanım Amacı |
|------|----------------|
| **CyberChef** | Çok yönlü veri dönüştürme ve analiz aracı (gchq.github.io/CyberChef) |
| **dcode.fr** | Kapsamlı şifre ve kodlama çözüm platformu |
| **RsaCtfTool** | Zayıf RSA anahtar saldırıları |
| **hashcat / john** | Parola ve hash kırma |
| **factordb** | Çevrim içi tam sayı asal çarpanlara ayırma |

---

## 📋 Kriptografi Kontrol Listesi

```markdown
□ Kodlama türünü belirleyin (base64, hex, rot13, binary)
□ Şifreleme türünü tespit edin (Klasik şifreler, simetrik, asimetrik)
□ Zayıf RSA parametrelerini (küçük e, birbirine yakın p ve q) kontrol edin
□ Hash arama veritabanlarını (CrackStation) deneyin
□ Tekrarlayan XOR anahtarlarını arayın
□ CyberChef "Magic" tarifini çalıştırın
```

---

**CTF'e Dön:** [🏁 CTF Genel Bakış](./README.tr.md)
