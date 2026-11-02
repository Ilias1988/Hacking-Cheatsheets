# 🔐 Шпаргалка по криптографическим CTF

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Crypto-CTF.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Уточнение:** формулы RSA описывают базовую схему без дополнения (padding); запись φ(n) предполагает различные простые p и q. Малое e само по себе ещё не означает уязвимость. Во фрагменте XOR переменная ciphertext должна быть задана отдельно; это учебный фрагмент, а не завершённая программа.

[🇹🇷 Türkçe Sürüm](Crypto-CTF.tr.md) • [🇬🇷 Ελληνικά](Crypto-CTF.el.md)

---

## 🔤 Кодирование и декодирование

### Base64
```bash
echo "string" | base64           # Закодировать
echo "c3RyaW5n" | base64 -d      # Декодировать
```

### Шестнадцатеричное представление (Hex)
```bash
echo "string" | xxd -p           # Закодировать
echo "737472696e67" | xxd -r -p  # Декодировать
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

### Основные формулы RSA
```
n = p * q           (открытый модуль)
φ(n) = (p-1)(q-1)   (функция Эйлера)
d = e⁻¹ mod φ(n)    (закрытая экспонента)
c = m^e mod n       (шифрование)
m = c^d mod n       (расшифрование)
```

### RsaCtfTool
```bash
# Установка
pip3 install rsactftool

# Атака
rsactftool --publickey pub.pem --private
rsactftool --publickey pub.pem --uncipherfile cipher.txt
```

### Разложение на множители
```
factordb.com - разложение на множители онлайн
```

---

## 🔒 Подбор исходных значений по хешам

### Определение типа хеша
```bash
hashid 'hash_value'
hash-identifier
```

### Подбор
```bash
# John
john --wordlist=rockyou.txt hash.txt

# Hashcat
hashcat -m 0 hash.txt rockyou.txt      # MD5
hashcat -m 100 hash.txt rockyou.txt    # SHA1
hashcat -m 1400 hash.txt rockyou.txt   # SHA256
```

### Онлайн-сервисы
```
crackstation.net
hashes.com
```

---

## 🧮 XOR

```python
# XOR в Python
def xor(data, key):
    return bytes([b ^ key[i % len(key)] for i, b in enumerate(data)])

# Перебор однобайтового ключа XOR
for key in range(256):
    result = bytes([b ^ key for b in ciphertext])
```

---

## 🛠️ Инструменты

| Инструмент | Применение |
|------|-----|
| **CyberChef** | gchq.github.io/CyberChef |
| **dcode.fr** | Различные кодировки и шифры |
| **RsaCtfTool** | Атаки на RSA |
| **hashcat/john** | Подбор значений по хешам |
| **factordb** | Разложение целых чисел на множители |

---

## 📋 Контрольный список для криптографии

```markdown
□ Определить кодирование (base64, hex, rot13)
□ Определить тип шифра
□ Проверить слабые параметры RSA (малое e, близкие простые множители)
□ Проверить базы соответствий хешей
□ Проверить повторяющийся ключ XOR
□ Попробовать функцию Magic в CyberChef
```

---

**К разделу CTF:** [🏁 Обзор CTF](./README.ru.md)
