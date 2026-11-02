# 🔐 Guía de referencia de criptografía para CTF

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Crypto-CTF.tr.md) • [🇬🇷 Ελληνικά](Crypto-CTF.el.md)

---

## 🔤 Codificación/decodificación

### Base64
```bash
echo "string" | base64           # Codificar
echo "c3RyaW5n" | base64 -d      # Decodificar
```

### Hex
```bash
echo "string" | xxd -p           # Codificar
echo "737472696e67" | xxd -r -p  # Decodificar
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

### Matemáticas básicas de RSA

> **Alcance:** las fórmulas describen RSA elemental con dos primos distintos y un exponente invertible módulo φ(n). No incluyen padding ni representan por sí solas un esquema seguro de cifrado para aplicaciones reales.
```
n = p * q           (módulo público)
φ(n) = (p-1)(q-1)   (función φ de Euler)
d = e⁻¹ mod φ(n)    (clave privada)
c = m^e mod n       (cifrado)
m = c^d mod n       (descifrado)
```

### RsaCtfTool
```bash
# Instalar
pip3 install rsactftool

# Ataque
rsactftool --publickey pub.pem --private
rsactftool --publickey pub.pem --uncipherfile cipher.txt
```

### Factorización
```
factordb.com - Factorización en línea
```

---

## 🔒 Recuperación de hashes mediante cracking

### Identificar el hash
```bash
hashid 'hash_value'
hash-identifier
```

### Recuperar el valor original
```bash
# John
john --wordlist=rockyou.txt hash.txt

# Hashcat
hashcat -m 0 hash.txt rockyou.txt      # MD5
hashcat -m 100 hash.txt rockyou.txt    # SHA1
hashcat -m 1400 hash.txt rockyou.txt   # SHA256
```

### En línea
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

# Fuerza bruta de XOR de un solo byte
for key in range(256):
    result = bytes([b ^ key for b in ciphertext])
```

---

## 🛠️ Herramientas

| Herramienta | Uso |
|------|-----|
| **CyberChef** | gchq.github.io/CyberChef |
| **dcode.fr** | Codificaciones y cifrados de todo tipo |
| **RsaCtfTool** | Ataques a RSA |
| **hashcat/john** | Recuperación de hashes mediante cracking |
| **factordb** | Factorización de enteros |

---

## 📋 Lista de comprobación de criptografía

```markdown
□ Identificar la codificación (base64, hex, rot13)
□ Identificar el tipo de cifrado
□ Comprobar debilidades de RSA (e pequeño y primos cercanos)
□ Probar bases de datos de consulta de hashes
□ Comprobar si se repite la clave XOR
□ Usar la operación Magic de CyberChef
```

---

**Volver a CTF:** [🏁 Resumen de CTF](./README.es.md)
