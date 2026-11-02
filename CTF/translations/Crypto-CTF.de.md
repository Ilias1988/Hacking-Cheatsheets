# 🔐 Spickzettel für Krypto-CTFs

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Crypto-CTF.tr.md) • [🇬🇷 Ελληνικά](Crypto-CTF.el.md)

---

## 🔤 Kodierung und Dekodierung

### Base64
```bash
echo "string" | base64           # Kodieren
echo "c3RyaW5n" | base64 -d      # Dekodieren
```

### Hex
```bash
echo "string" | xxd -p           # Kodieren
echo "737472696e67" | xxd -r -p  # Dekodieren
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

### Grundlegende RSA-Mathematik
```
n = p * q           (öffentlicher Modulus)
φ(n) = (p-1)(q-1)   (Eulersche Phi-Funktion)
d = e⁻¹ mod φ(n)    (privater Schlüssel)
c = m^e mod n       (Verschlüsselung)
m = c^d mod n       (Entschlüsselung)
```

### RsaCtfTool
```bash
# Installieren
pip3 install rsactftool

# Angriff
rsactftool --publickey pub.pem --private
rsactftool --publickey pub.pem --uncipherfile cipher.txt
```

### Faktorisierung
```
factordb.com - Online-Faktorisierung
```

---

## 🔒 Hashwerte knacken

### Hashverfahren erkennen
```bash
hashid 'hash_value'
hash-identifier
```

### Knacken
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

# XOR mit einem einzelnen Schlüsselbyte systematisch ausprobieren
for key in range(256):
    result = bytes([b ^ key for b in ciphertext])
```

---

## 🛠️ Werkzeuge

| Werkzeug | Verwendung |
|------|-----|
| **CyberChef** | gchq.github.io/CyberChef |
| **dcode.fr** | Verschiedene Kodierungen und Chiffren |
| **RsaCtfTool** | Angriffe auf RSA |
| **hashcat/john** | Hashwerte knacken |
| **factordb** | Faktorisierung ganzer Zahlen |

---

## 📋 Checkliste für Krypto-CTFs

```markdown
□ Kodierung erkennen (base64, hex, rot13)
□ Art der Chiffre erkennen
□ Auf schwache RSA-Parameter prüfen (kleines e, nahe Primzahlen)
□ Datenbanken zur Hashsuche ausprobieren
□ Auf einen wiederholten XOR-Schlüssel prüfen
□ Die Magic-Funktion von CyberChef verwenden
```

---

**Zurück zu CTF:** [🏁 CTF-Übersicht](./README.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Mathematische Einordnung:** Die Formel für φ(n) setzt hier zwei verschiedene Primzahlen p und q voraus. d ist der private Exponent; die Aufstellung zeigt vereinfachtes „Textbook RSA“ ohne ein sicheres Paddingverfahren und ist kein vollständiges Verschlüsselungsformat. Ein kleines e allein beweist keine ausnutzbare RSA-Schwäche. [RSA: RFC 8017](https://www.rfc-editor.org/rfc/rfc8017.html).

> **Beispielgrenzen:** Kodierung ist keine Verschlüsselung; Hashwerte werden nicht entschlüsselt, sondern mit Kandidaten oder bekannten Werten verglichen. Die Erkennung eines Hashformats anhand des Aussehens ist nicht eindeutig. `echo` hängt üblicherweise einen Zeilenumbruch an; das beeinflusst die kodierten Bytes. Im XOR-Beispiel wird `ciphertext` vorausgesetzt; Ergebnisse werden nur berechnet, nicht ausgegeben. Der wiederholte Schlüssel darf in der Funktion nicht leer sein. Installationsnamen und CLI-Optionen entsprechen der Vorlage und sind versionsabhängig.
