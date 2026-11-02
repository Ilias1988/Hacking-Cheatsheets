# 🔐 Guia de referência de criptografia para CTF

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Crypto-CTF.tr.md) • [🇬🇷 Ελληνικά](Crypto-CTF.el.md)

> **Notas de precisão:** Codificação (Base64, hexadecimal) e hashing não são cifragem. As fórmulas RSA mostram a operação matemática sem padding, para o caso de dois primos distintos; `d` é o expoente privado, e não a representação completa da chave. A inversa exige que `e` seja coprimo com o totiente; esquemas completos incluem passos adicionais ([RFC 8017](https://www.rfc-editor.org/rfc/rfc8017.html)). Um expoente pequeno não implica vulnerabilidade sem outras condições. `echo` normalmente acrescenta uma mudança de linha, pelo que os bytes codificados podem diferir dos exemplos sem esse carácter. Identificar um hash pelo formato é uma hipótese. Os exemplos XOR pressupõem dados e chave em bytes; o segundo pressupõe `ciphertext` já definido.

---

## 🔤 Codificação/descodificação

### Base64
```bash
echo "string" | base64           # Codificar
echo "c3RyaW5n" | base64 -d      # Descodificar
```

### Hexadecimal
```bash
echo "string" | xxd -p           # Codificar
echo "737472696e67" | xxd -r -p  # Descodificar
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

### Matemática básica de RSA
```
n = p * q           (módulo público)
φ(n) = (p-1)(q-1)   (função totiente)
d = e⁻¹ mod φ(n)    (chave privada)
c = m^e mod n       (cifragem)
m = c^d mod n       (decifragem)
```

### RsaCtfTool
```bash
# Instalar
pip3 install rsactftool

# Ataque
rsactftool --publickey pub.pem --private
rsactftool --publickey pub.pem --uncipherfile cipher.txt
```

### Fatorização
```
factordb.com - Fatorização online
```

---

## 🔒 Quebra de hashes

### Identificar o hash
```bash
hashid 'hash_value'
hash-identifier
```

### Quebrar o hash
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
# XOR em Python
def xor(data, key):
    return bytes([b ^ key[i % len(key)] for i, b in enumerate(data)])

# Força bruta de XOR com chave de um byte
for key in range(256):
    result = bytes([b ^ key for b in ciphertext])
```

---

## 🛠️ Ferramentas

| Ferramenta | Utilização |
|------|-----|
| **CyberChef** | gchq.github.io/CyberChef |
| **dcode.fr** | Codificações/cifras diversas |
| **RsaCtfTool** | Ataques a RSA |
| **hashcat/john** | Quebra de hashes |
| **factordb** | Fatorização de inteiros |

---

## 📋 Lista de verificação de criptografia

```markdown
□ Identificar a codificação (base64, hex, rot13)
□ Identificar o tipo de cifra
□ Verificar RSA fraco (e pequeno, números primos próximos)
□ Experimentar bases de dados de consulta de hashes
□ Verificar a repetição da chave XOR
□ Utilizar a operação Magic do CyberChef
```

---

**Voltar a CTF:** [🏁 Visão geral de CTF](README.pt.md)
