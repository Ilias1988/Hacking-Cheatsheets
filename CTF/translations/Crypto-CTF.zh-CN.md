# 🔐 密码学 CTF 速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Crypto-CTF.tr.md) • [🇬🇷 Ελληνικά](Crypto-CTF.el.md)

> **译注：** Base64 和十六进制是编码，不是加密；原文 `echo` 编码命令默认附加换行，因此与后面不含换行的常量解码示例不完全对应。RSA 部分采用两个不同素数的教材模型，`d` 实际是私有指数，而不是完整私钥；完整密钥表示与加密方案参见 [RFC 8017](https://www.rfc-editor.org/rfc/rfc8017.html)。哈希识别结果只能作为候选类型，不能仅凭长度断定算法。

---

## 🔤 编码与解码

### Base64
```bash
echo "string" | base64           # 编码
echo "c3RyaW5n" | base64 -d      # 解码
```

### 十六进制
```bash
echo "string" | xxd -p           # 编码
echo "737472696e67" | xxd -r -p  # 解码
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

### RSA 基础数学
```
n = p * q           （公开模数）
φ(n) = (p-1)(q-1)   （欧拉函数）
d = e⁻¹ mod φ(n)    （私钥；此处实际为私有指数）
c = m^e mod n       （加密）
m = c^d mod n       （解密）
```

### RsaCtfTool
```bash
# 安装
pip3 install rsactftool

# 攻击
rsactftool --publickey pub.pem --private
rsactftool --publickey pub.pem --uncipherfile cipher.txt
```

### 因数分解
```
factordb.com——在线因数分解
```

---

## 🔒 哈希破解

### 识别哈希类型
```bash
hashid 'hash_value'
hash-identifier
```

### 破解
```bash
# John
john --wordlist=rockyou.txt hash.txt

# Hashcat
hashcat -m 0 hash.txt rockyou.txt      # MD5
hashcat -m 100 hash.txt rockyou.txt    # SHA1
hashcat -m 1400 hash.txt rockyou.txt   # SHA256
```

### 在线服务
```
crackstation.net
hashes.com
```

---

## 🧮 XOR

```python
# 使用 Python 进行 XOR 运算
def xor(data, key):
    return bytes([b ^ key[i % len(key)] for i, b in enumerate(data)])

# 单字节 XOR 穷举
for key in range(256):
    result = bytes([b ^ key for b in ciphertext])
```

---

## 🛠️ 工具

| 工具 | 用途 |
|------|-----|
| **CyberChef** | gchq.github.io/CyberChef |
| **dcode.fr** | 各种编码与密码算法 |
| **RsaCtfTool** | RSA 攻击 |
| **hashcat/john** | 哈希破解 |
| **factordb** | 整数因数分解 |

---

## 📋 密码学检查清单

```markdown
□ 识别编码（base64、hex、rot13）
□ 识别密码算法类型
□ 检查弱 RSA 配置（较小的 e、相近的素数）
□ 尝试查询哈希数据库
□ 检查是否使用重复 XOR 密钥
□ 使用 CyberChef 的 Magic 自动识别功能
```

---

**返回 CTF:** [🏁 CTF 概览](./README.zh-CN.md)
