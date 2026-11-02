# 🐉 Kali Linux 工具：完整参考（600+ 项工具）

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗ █████╗ ██╗     ██╗    ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║ ██╔╝██╔══██╗██║     ██║    ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  █████╔╝ ███████║██║     ██║    ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██╔═██╗ ██╔══██║██║     ██║    ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ██║  ██╗██║  ██║███████╗██║    ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝    ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
                       工具参考指南
```

<p align="center">
  <img src="https://img.shields.io/badge/Kali_Linux-Tools-557C94?style=for-the-badge&logo=kalilinux" alt="Kali">
  <img src="https://img.shields.io/badge/600+-Tools-red?style=for-the-badge" alt="Tools">
  <img src="https://img.shields.io/badge/14-Categories-blue?style=for-the-badge" alt="Categories">
</p>

---

## 📋 目录

> **范围说明：** 标题中的 `600+` 与工具名称沿用原文；本文是分类选录，同一工具会出现在多处，不是逐项列出 600 个独立工具的清单，也不表示全部已预装。[Kali 官方元软件包说明](https://www.kali.org/docs/general-use/metapackages/)表明，不同镜像、架构和元软件包的工具集合不同，实际可用性还需核对版本。

1. [信息收集](#1--information-gathering)
2. [漏洞分析](#2--vulnerability-analysis)
3. [Web 应用分析](#3--web-application-analysis)
4. [数据库评估](#4--database-assessment)
5. [密码攻击](#5--password-attacks)
6. [无线攻击](#6--wireless-attacks)
7. [逆向工程](#7--reverse-engineering)
8. [漏洞利用工具](#8--exploitation-tools)
9. [嗅探与欺骗](#9--sniffing--spoofing)
10. [后渗透](#10--post-exploitation)
11. [取证](#11--forensics)
12. [报告工具](#12--reporting-tools)
13. [社会工程](#13--social-engineering)
14. [硬件安全测试](#14--hardware-hacking)

---

<a id="1--information-gathering"></a>
## 1. 🔍 信息收集

### DNS 分析

| 工具 | 说明 |
|------|-------------|
| **dnsrecon** | DNS 枚举脚本 |
| **dnsenum** | DNS 枚举工具 |
| **fierce** | DNS 侦察工具 |
| **dnsmap** | 子域名暴力枚举工具 |
| **dnstracer** | DNS 跟踪工具 |
| **dnswalk** | DNS 数据库调试工具 |

### 主机发现

| 工具 | 说明 |
|------|-------------|
| **nmap** | 网络探测与端口扫描器 |
| **masscan** | 快速端口扫描器 |
| **hping3** | 网络数据包生成器 |
| **fping** | 快速 ping 扫描 |
| **arping** | ARP ping 工具 |
| **netdiscover** | 主动／被动 ARP 侦察 |
| **unicornscan** | 异步网络扫描器 |

### 网络与端口扫描器

| 工具 | 说明 |
|------|-------------|
| **nmap** | 最受欢迎的网络扫描器（原文描述） |
| **masscan** | 速度最快的端口扫描器（原文描述） |
| **rustscan** | 现代化的快速扫描器 |
| **zmap** | 快速单包网络扫描器 |
| **unicornscan** | 异步 TCP/UDP 扫描器 |
| **nbtscan** | NetBIOS 名称扫描器 |
| **onesixtyone** | SNMP 扫描器 |

### 开源情报（OSINT）工具

| 工具 | 说明 |
|------|-------------|
| **maltego** | 开源情报（OSINT）与图形化关联分析 |
| **spiderfoot** | OSINT 自动化工具 |
| **recon-ng** | Web 侦察框架 |
| **theHarvester** | 电子邮件地址、子域名和 IP 收集工具 |
| **shodan** | 物联网（IoT）搜索引擎命令行工具 |
| **amass** | 攻击面映射 |
| **subfinder** | 子域名发现工具 |
| **assetfinder** | 查找域名和子域名 |
| **waybackurls** | 从 Wayback Machine 获取 URL |
| **gau** | 获取各类 URL（Get All URLs） |

### 路由分析

| 工具 | 说明 |
|------|-------------|
| **traceroute** | 网络路由跟踪 |
| **mtr** | 网络诊断工具 |
| **netmask** | 网络掩码工具 |
| **tcptraceroute** | 基于 TCP 的路由跟踪 |

### SMB 分析

| 工具 | 说明 |
|------|-------------|
| **smbclient** | SMB 客户端 |
| **smbmap** | SMB 共享枚举 |
| **enum4linux** | Windows／Samba 枚举 |
| **enum4linux-ng** | 新一代 enum4linux |
| **rpcclient** | MS-RPC 客户端 |
| **nbtscan** | NetBIOS 扫描器 |

### SMTP 分析

| 工具 | 说明 |
|------|-------------|
| **smtp-user-enum** | SMTP 用户枚举 |
| **swaks** | SMTP 测试工具 |

### SNMP 分析

| 工具 | 说明 |
|------|-------------|
| **snmpwalk** | SNMP 数据获取 |
| **snmpcheck** | SNMP 设备枚举工具 |
| **onesixtyone** | 快速 SNMP 扫描器 |
| **braa** | 批量 SNMP 扫描器 |

### SSL 分析

| 工具 | 说明 |
|------|-------------|
| **sslscan** | SSL/TLS 扫描器 |
| **sslyze** | SSL/TLS 分析工具 |
| **testssl.sh** | SSL/TLS 测试 |

---

<a id="2--vulnerability-analysis"></a>
## 2. 🔓 漏洞分析

### 漏洞扫描器

| 工具 | 说明 |
|------|-------------|
| **nmap** | Nmap 脚本引擎（NSE）的漏洞检测脚本 |
| **nikto** | Web 服务器扫描器 |
| **openvas** | 开放式漏洞评估 |
| **legion** | 网络渗透测试 |
| **lynis** | 安全审计工具 |
| **nuclei** | 基于模板的漏洞扫描器 |

### 模糊测试（Fuzzing）工具

| 工具 | 说明 |
|------|-------------|
| **wfuzz** | Web 模糊测试工具 |
| **ffuf** | 快速 Web 模糊测试工具 |
| **gobuster** | 目录／DNS 暴力枚举工具 |
| **dirb** | Web 内容扫描器 |
| **dirbuster** | 目录暴力枚举工具 |
| **feroxbuster** | 递归内容发现 |
| **spike** | 协议模糊测试工具 |
| **sfuzz** | 简单模糊测试工具 |

### VoIP 工具

| 工具 | 说明 |
|------|-------------|
| **sipvicious** | SIP 协议工具 |
| **voiphopper** | VoIP VLAN 跳跃 |

---

<a id="3--web-application-analysis"></a>
## 3. 🌐 Web 应用分析

### 内容管理系统（CMS）识别

> **支持范围：** [droopescan 项目](https://github.com/SamJoan/droopescan)对不同 CMS 的支持程度不同，例如 Joomla 仅支持版本枚举与相关 URL 检查，不能将表中的概括理解为各项检测能力完全相同。

| 工具 | 说明 |
|------|-------------|
| **wpscan** | WordPress 漏洞扫描器 |
| **joomscan** | Joomla 扫描器 |
| **droopescan** | Drupal／WordPress／Joomla 扫描器 |
| **cmseek** | CMS 检测与漏洞利用 |

### Web 漏洞扫描器

| 工具 | 说明 |
|------|-------------|
| **nikto** | Web 服务器扫描器 |
| **burpsuite** | Web 应用安全测试 |
| **zaproxy** | OWASP ZAP 代理 |
| **wapiti** | Web 漏洞扫描器 |
| **skipfish** | Web 应用安全扫描器 |
| **arachni** | Web 应用安全扫描器 |
| **w3af** | Web 应用攻击框架 |
| **vega** | Web 安全扫描器 |

### Web 爬虫

| 工具 | 说明 |
|------|-------------|
| **cutycapt** | 网页截图 |
| **dirb** | Web 内容扫描器 |
| **dirbuster** | 目录暴力枚举 |
| **gobuster** | 目录／DNS 暴力枚举工具 |
| **feroxbuster** | 快速内容发现 |

### Web 应用代理

| 工具 | 说明 |
|------|-------------|
| **burpsuite** | Web 代理与扫描器 |
| **zaproxy** | OWASP ZAP |
| **mitmproxy** | HTTP 代理 |
| **proxychains** | 代理链 |

### SQL 注入

| 工具 | 说明 |
|------|-------------|
| **sqlmap** | SQL 注入自动化 |
| **sqlninja** | SQL Server 注入 |
| **bbqsql** | SQL 盲注 |
| **jsql-injection** | 基于 Java 的 SQL 注入工具 |
| **ghauri** | SQL 注入工具 |

### 跨站脚本（XSS）工具

> **名称说明：** 表中 `xssstrike` 保留原文拼写；相关项目正式名称为 [XSStrike](https://github.com/s0md3v/XSStrike)，查找项目或命令时应核对其实际名称。

| 工具 | 说明 |
|------|-------------|
| **xsser** | XSS 漏洞测试工具 |
| **xssstrike** | 高级 XSS 检测 |
| **dalfox** | 面向 XSS 的参数分析 |

---

<a id="4--database-assessment"></a>
## 4. 💾 数据库评估

| 工具 | 说明 |
|------|-------------|
| **sqlmap** | SQL 注入自动化 |
| **sqlninja** | MS SQL 注入 |
| **jsql-injection** | 基于 Java 的 SQL 注入工具 |
| **oscanner** | Oracle 扫描器 |
| **tnscmd10g** | Oracle TNS 监听器工具 |
| **odat** | Oracle 数据库攻击工具 |
| **mdbtools** | MS Access 工具 |
| **sidguesser** | Oracle SID 猜测工具 |

---

<a id="5--password-attacks"></a>
## 5. 🔑 密码攻击

### 离线攻击

| 工具 | 说明 |
|------|-------------|
| **john** | John the Ripper 密码破解工具 |
| **hashcat** | GPU 密码破解工具 |
| **ophcrack** | Windows 密码破解工具 |
| **hashid** | 哈希识别工具 |
| **hash-identifier** | 哈希类型识别工具 |
| **chntpw** | Windows 密码重置 |
| **samdump2** | SAM 导出工具 |
| **crunch** | 字典生成器 |
| **cewl** | 自定义字典生成器 |
| **cupp** | 用户密码特征分析工具 |
| **rsmangler** | 字典变形工具 |
| **wordlists** | 字典集合 |

### 在线攻击

| 工具 | 说明 |
|------|-------------|
| **hydra** | 网络登录密码破解工具 |
| **medusa** | 并行登录暴力破解工具 |
| **ncrack** | 网络身份验证破解工具 |
| **patator** | 多用途暴力破解工具 |
| **thc-pptp-bruter** | PPTP 暴力破解工具 |
| **crowbar** | 暴力破解工具 |

### 哈希传递（Pass-the-Hash）

| 工具 | 说明 |
|------|-------------|
| **mimikatz** | Windows 凭据提取工具 |
| **pth-toolkit** | 哈希传递工具集 |
| **smbmap** | SMB 枚举 |
| **crackmapexec** | 网络渗透测试工具 |
| **evil-winrm** | Windows 远程管理 |

---

<a id="6--wireless-attacks"></a>
## 6. 📡 无线攻击

### 蓝牙

| 工具 | 说明 |
|------|-------------|
| **bluelog** | 蓝牙扫描器 |
| **bluemaho** | 蓝牙安全测试 |
| **blueranger** | 蓝牙近距离检测 |
| **btscanner** | 蓝牙设备扫描器 |
| **redfang** | 隐藏蓝牙设备扫描器 |
| **spooftooph** | 蓝牙欺骗 |

### Wi-Fi 工具

| 工具 | 说明 |
|------|-------------|
| **aircrack-ng** | Wi-Fi 安全工具套件 |
| **airmon-ng** | 启用监听模式的工具 |
| **airodump-ng** | 数据包捕获 |
| **aireplay-ng** | 数据包注入 |
| **airbase-ng** | 伪造接入点（AP） |
| **wifite** | 自动化 Wi-Fi 审计工具 |
| **fern-wifi-cracker** | Wi-Fi 审计工具 |
| **kismet** | 无线网络探测工具 |
| **reaver** | WPS 攻击工具 |
| **bully** | WPS 暴力破解 |
| **pixiewps** | WPS 离线攻击 |
| **wifiphisher** | Wi-Fi 钓鱼工具 |
| **fluxion** | WPA 中间人（MitM）攻击 |
| **hostapd-wpe** | 无线网络凭据收集 |
| **cowpatty** | WPA-PSK 破解工具 |
| **asleap** | LEAP/PPTP 密码恢复 |
| **eapmd5pass** | EAP-MD5 密码破解工具 |
| **mdk3** | 无线攻击工具 |
| **mdk4** | 无线漏洞利用工具 |

### RFID／NFC（射频识别／近场通信）

| 工具 | 说明 |
|------|-------------|
| **mfoc** | Mifare Classic 离线破解工具 |
| **mfcuk** | Mifare Classic 通用工具集 |
| **mfterm** | Mifare Classic 交互终端 |
| **libnfc** | NFC 库 |
| **proxmark3** | RFID 研究工具 |

---

<a id="7--reverse-engineering"></a>
## 7. 🔧 逆向工程

### 调试器

> **平台说明：** 该目录也列出了其他平台上的安全工具；例如 [x64dbg](https://github.com/x64dbg/x64dbg)是 Windows 用户态调试器，不能仅凭归入此表就认定它是可直接在 Kali 原生运行的预装工具。

| 工具 | 说明 |
|------|-------------|
| **gdb** | GNU 调试器 |
| **edb-debugger** | 跨平台调试器 |
| **radare2** | 逆向工程框架 |
| **x64dbg** | x64/x32 调试器 |
| **ollydbg** | x86 调试器 |
| **immunity-debugger** | 支持 Python 脚本的调试器 |

### 反汇编器

| 工具 | 说明 |
|------|-------------|
| **ghidra** | 美国国家安全局（NSA）的逆向工程工具 |
| **radare2** | 逆向工程框架 |
| **cutter** | Radare2 图形界面（原文描述，参见下方勘误） |
| **rizin** | 逆向工程框架 |
| **objdump** | 目标文件反汇编器 |
| **IDA Free** | 交互式反汇编器 |

### 其他逆向工程工具

> **Cutter 勘误：** 上表“Radare2 图形界面”是旧描述。[Cutter 官方网站](https://cutter.re/)说明当前使用 Rizin 作为核心引擎；原表中的工具名与历史描述均保留，并在此说明版本差异。

| 工具 | 说明 |
|------|-------------|
| **apktool** | Android APK 工具 |
| **dex2jar** | DEX 到 JAR 的转换工具 |
| **jadx** | Java 反编译器 |
| **jd-gui** | Java 反编译器图形界面 |
| **bytecode-viewer** | Java 字节码查看工具 |
| **androguard** | Android 应用分析 |
| **frida** | 动态插桩 |
| **angr** | 二进制分析平台 |
| **binwalk** | 固件分析工具 |

---

<a id="8--exploitation-tools"></a>
## 8. 💣 漏洞利用工具

### 漏洞利用框架

| 工具 | 说明 |
|------|-------------|
| **metasploit-framework** | 漏洞利用框架 |
| **armitage** | Metasploit 图形界面 |
| **cobalt-strike** | 对手模拟 |
| **beef-xss** | 浏览器漏洞利用 |
| **routersploit** | 路由器漏洞利用 |
| **exploitdb** | 漏洞利用数据库 |
| **searchsploit** | Exploit-DB 搜索 |

### Shellcode（机器码载荷）工具

| 工具 | 说明 |
|------|-------------|
| **msfvenom** | 载荷（Payload）生成器 |
| **veil** | 载荷生成框架 |
| **shellnoob** | Shellcode 工具集 |

### 网络漏洞利用

| 工具 | 说明 |
|------|-------------|
| **responder** | LLMNR/NBT-NS 响应投毒工具 |
| **impacket** | 网络协议工具集 |
| **crackmapexec** | 后渗透工具 |
| **evil-winrm** | WinRM 命令行会话 |
| **powershell-empire** | 后渗透框架 |

---

<a id="9--sniffing--spoofing"></a>
## 9. 📡 嗅探与欺骗

### 网络嗅探器

| 工具 | 说明 |
|------|-------------|
| **wireshark** | 网络协议分析器 |
| **tcpdump** | 命令行嗅探器 |
| **tshark** | 终端版 Wireshark |
| **ettercap** | 网络嗅探／中间人攻击工具 |
| **dsniff** | 网络审计工具 |
| **net-creds** | 凭据嗅探工具 |
| **pcredz** | 凭据提取工具 |

### 欺骗工具

| 工具 | 说明 |
|------|-------------|
| **arpspoof** | ARP 欺骗 |
| **dnsspoof** | DNS 欺骗 |
| **macchanger** | MAC 地址修改工具 |
| **ettercap** | ARP 投毒 |
| **bettercap** | 网络攻击工具 |
| **mitmproxy** | HTTP 中间人代理 |
| **sslstrip** | HTTPS 降级剥离 |

### VoIP 嗅探

| 工具 | 说明 |
|------|-------------|
| **sipvicious** | SIP／VoIP 工具 |
| **voiphopper** | VoIP VLAN 跳跃 |

---

<a id="10--post-exploitation"></a>
## 10. 🎯 后渗透

### 操作系统后门

| 工具 | 说明 |
|------|-------------|
| **msfvenom** | 载荷（Payload）生成器 |
| **veil** | 反病毒检测规避 |
| **shellter** | 动态 Shellcode 注入工具 |
| **weevely** | PHP Web Shell（网页后门） |

### 隧道

| 工具 | 说明 |
|------|-------------|
| **sshuttle** | 基于 SSH 隧道的 VPN |
| **chisel** | HTTP 隧道 |
| **proxychains** | 代理链 |
| **socat** | 转发工具 |
| **plink** | PuTTY 连接工具 |
| **stunnel** | TLS 隧道 |
| **dns2tcp** | DNS 隧道 |
| **iodine** | DNS 隧道 |
| **ptunnel** | ICMP 隧道 |
| **pwnat** | NAT 穿透 |
| **udptunnel** | UDP 隧道 |

### Windows 后渗透

| 工具 | 说明 |
|------|-------------|
| **mimikatz** | Windows 凭据导出工具 |
| **crackmapexec** | Windows／AD 渗透测试 |
| **evil-winrm** | WinRM 命令行会话 |
| **impacket** | Windows 协议工具 |
| **bloodhound** | Active Directory 侦察 |
| **powershell-empire** | PowerShell 后渗透 |
| **rubeus** | Kerberos 滥用 |
| **kerbrute** | Kerberos 暴力破解 |

### Linux 后渗透

| 工具 | 说明 |
|------|-------------|
| **linpeas** | Linux 权限提升 |
| **linenum** | Linux 枚举 |
| **pspy** | 进程监视工具 |
| **linux-exploit-suggester** | 内核漏洞利用查找工具 |

---

<a id="11--forensics"></a>
## 11. 🔬 取证

### 取证框架

| 工具 | 说明 |
|------|-------------|
| **autopsy** | 数字取证平台 |
| **sleuthkit** | 取证工具集 |
| **dff** | 数字取证框架 |

### 文件雕刻恢复

| 工具 | 说明 |
|------|-------------|
| **foremost** | 文件雕刻恢复工具 |
| **scalpel** | 文件雕刻恢复工具 |
| **bulk_extractor** | 信息提取工具 |
| **photorec** | 照片恢复 |
| **testdisk** | 数据恢复 |

### 取证镜像制作

| 工具 | 说明 |
|------|-------------|
| **dc3dd** | 取证磁盘镜像制作 |
| **dcfldd** | 增强版 dd |
| **guymager** | 图形化取证镜像制作工具 |

### 内存取证

| 工具 | 说明 |
|------|-------------|
| **volatility** | 内存取证 |
| **volatility3** | 内存分析 |
| **lime** | Linux 内存提取工具 |

### 网络取证

| 工具 | 说明 |
|------|-------------|
| **wireshark** | 数据包分析 |
| **tcpdump** | 数据包捕获 |
| **ngrep** | 网络 grep 工具 |
| **NetworkMiner** | 网络取证 |

### PDF 取证

| 工具 | 说明 |
|------|-------------|
| **pdfid** | PDF 扫描器 |
| **pdf-parser** | PDF 分析 |
| **peepdf** | PDF 分析工具 |

---

<a id="12--reporting-tools"></a>
## 12. 📝 报告工具

| 工具 | 说明 |
|------|-------------|
| **cutycapt** | 网页截图 |
| **faraday** | 协作式渗透测试工具 |
| **maltego** | OSINT 可视化 |
| **pipal** | 密码分析 |
| **recordmydesktop** | 屏幕录制工具 |
| **cherrytree** | 笔记应用 |
| **dradis** | 协作平台 |
| **metagoofil** | 元数据提取 |
| **eyewitness** | 网站截图 |
| **gowitness** | 网站截图 |

---

<a id="13--social-engineering"></a>
## 13. 🎭 社会工程

| 工具 | 说明 |
|------|-------------|
| **setoolkit** | 社会工程工具集 |
| **gophish** | 钓鱼框架 |
| **king-phisher** | 钓鱼活动管理 |
| **evilginx2** | 中间人钓鱼框架 |
| **beef-xss** | 浏览器漏洞利用 |
| **maltego** | OSINT 关联分析 |
| **theHarvester** | 电子邮件地址收集 |
| **sherlock** | 用户名 OSINT 调查 |
| **social-analyzer** | 社交媒体分析 |

---

<a id="14--hardware-hacking"></a>
## 14. 🔧 硬件安全测试

| 工具 | 说明 |
|------|-------------|
| **arduino** | 硬件平台 |
| **binwalk** | 固件分析 |
| **flashrom** | 闪存编程工具 |
| **openocd** | 片上调试 |
| **proxmark3** | RFID 研究 |

---

## 📊 速查表：50 项重点工具

| # | 工具 | 类别 | 用途 |
|---|------|----------|----------|
| 1 | **nmap** | 扫描 | 端口扫描 |
| 2 | **metasploit** | 漏洞利用 | 漏洞利用框架 |
| 3 | **burpsuite** | Web | Web 测试 |
| 4 | **wireshark** | 嗅探 | 数据包分析 |
| 5 | **john** | 密码攻击 | 密码破解 |
| 6 | **hashcat** | 密码攻击 | GPU 破解 |
| 7 | **hydra** | 密码攻击 | 在线暴力破解 |
| 8 | **sqlmap** | Web | SQL 注入 |
| 9 | **aircrack-ng** | 无线 | Wi-Fi 破解 |
| 10 | **nikto** | Web | Web 扫描 |
| 11 | **gobuster** | Web | 目录暴力枚举 |
| 12 | **ffuf** | Web | Web 模糊测试 |
| 13 | **dirb** | Web | 目录扫描 |
| 14 | **wpscan** | Web | WordPress 扫描 |
| 15 | **enum4linux** | 信息收集 | SMB 枚举 |
| 16 | **crackmapexec** | 后渗透 | AD 渗透测试 |
| 17 | **mimikatz** | 后渗透 | 凭据导出 |
| 18 | **responder** | 漏洞利用 | LLMNR 投毒 |
| 19 | **impacket** | 漏洞利用 | 协议工具 |
| 20 | **bloodhound** | 后渗透 | AD 关系映射 |
| 21 | **netcat** | 实用工具 | TCP/UDP 工具 |
| 22 | **socat** | 实用工具 | 数据转发 |
| 23 | **tcpdump** | 嗅探 | 数据包捕获 |
| 24 | **ettercap** | 嗅探 | 中间人攻击 |
| 25 | **bettercap** | 嗅探 | 网络攻击 |
| 26 | **proxychains** | 实用工具 | 代理链 |
| 27 | **msfvenom** | 漏洞利用 | 载荷生成 |
| 28 | **searchsploit** | 漏洞利用 | 漏洞利用搜索 |
| 29 | **theHarvester** | OSINT | 电子邮件地址收集 |
| 30 | **maltego** | OSINT | 关联分析 |
| 31 | **recon-ng** | OSINT | 侦察框架 |
| 32 | **amass** | OSINT | 子域名枚举 |
| 33 | **subfinder** | OSINT | 子域名查找 |
| 34 | **nuclei** | 漏洞检测 | 基于模板的扫描器 |
| 35 | **zaproxy** | Web | OWASP ZAP |
| 36 | **wifite** | 无线 | 自动化 Wi-Fi 审计 |
| 37 | **ghidra** | 逆向工程 | 逆向工程 |
| 38 | **radare2** | 逆向工程 | 二进制分析 |
| 39 | **gdb** | 逆向工程 | 调试器 |
| 40 | **binwalk** | 逆向工程 | 固件分析 |
| 41 | **volatility** | 取证 | 内存分析 |
| 42 | **autopsy** | 取证 | 取证平台 |
| 43 | **foremost** | 取证 | 文件雕刻恢复 |
| 44 | **setoolkit** | 社会工程 | 社会工程工具集 |
| 45 | **beef-xss** | Web | 浏览器漏洞利用 |
| 46 | **linpeas** | 权限提升 | Linux 枚举 |
| 47 | **winpeas** | 权限提升 | Windows 枚举 |
| 48 | **chisel** | 隧道 | HTTP 隧道 |
| 49 | **sshuttle** | 隧道 | SSH VPN |
| 50 | **evil-winrm** | 后渗透 | WinRM 命令行会话 |

---

## 📚 参考资源

- [Kali Linux 工具](https://www.kali.org/tools/)
- [Kali 文档](https://www.kali.org/docs/)
- [Kali 元软件包](https://www.kali.org/docs/general-use/metapackages/)

---

<p align="center">
  <b>🐉 掌握 Kali Linux！</b><br>
  <i>600+ 项工具，随时查阅</i>
</p>
