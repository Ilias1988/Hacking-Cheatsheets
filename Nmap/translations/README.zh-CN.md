# 🔍 Nmap 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
    _   __                      
   / | / /___ ___  ____ _____  
  /  |/ / __ `__ \/ __ `/ __ \ 
 / /|  / / / / / / /_/ / /_/ / 
/_/ |_/_/ /_/ /_/\__,_/ .___/  
                     /_/       
    Network Mapper：网络扫描器
```

<p align="center">
  <img src="https://img.shields.io/badge/Nmap-Network_Scanner-blue?style=for-the-badge" alt="Nmap">
  <img src="https://img.shields.io/badge/Reconnaissance-red?style=for-the-badge" alt="侦察">
  <img src="https://img.shields.io/badge/Port_Scanning-green?style=for-the-badge" alt="端口扫描">
  <img src="https://img.shields.io/badge/NSE_Scripts-purple?style=for-the-badge" alt="NSE 脚本">
</p>

<p align="center">
  <b>🌐 功能强大的网络扫描与发现工具</b>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [基本语法](#-basic-syntax)
- [目标指定方式](#-target-specification)
- [主机发现](#-host-discovery)
- [端口扫描技术](#-port-scanning-techniques)
- [端口指定方式](#-port-specification)
- [服务与版本检测](#-service--version-detection)
- [操作系统检测](#-os-detection)
- [时序与性能](#-timing--performance)
- [NSE 脚本](#-nse-scripts)
- [输出格式](#-output-formats)
- [防火墙与入侵检测系统（IDS）规避](#-firewallids-evasion)
- [实际场景示例](#-real-world-examples)
- [快速参考](#-quick-reference)
- [使用技巧与最佳实践](#-tips--best-practices)
- [资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**Nmap**（Network Mapper）是一款免费开源的网络发现与安全审计工具，在网络扫描领域得到广泛应用。

### 主要特性

| 特性 | 说明 |
|---------|-------------|
| **主机发现** | 发现网络中的存活主机 |
| **端口扫描** | 识别开放端口与服务 |
| **版本检测** | 确定服务版本 |
| **操作系统检测** | 识别操作系统 |
| **NSE 脚本** | 漏洞扫描与漏洞利用 |
| **输出格式** | XML、适合 grep 处理的输出、普通输出 |

### 端口状态

| 状态 | 说明 |
|-------|-------------|
| `open` | 应用正在接受连接 |
| `closed` | 端口可达，但没有应用监听 |
| `filtered` | Nmap 无法判断是否开放（可能受防火墙影响） |
| `unfiltered` | 端口可达，但具体开放或关闭状态未知 |
| `open|filtered` | 无法判断是开放还是被过滤 |
| `closed|filtered` | 无法判断是关闭还是被过滤 |

---

<a id="-installation"></a>
## 📥 安装

### Kali Linux（预安装）
```bash
# 更新 Nmap
sudo apt update && sudo apt install nmap
```

### Ubuntu/Debian
```bash
sudo apt install nmap
```

### CentOS/RHEL
```bash
sudo yum install nmap
```

### Windows
```bash
# 从以下地址下载： https://nmap.org/download.html
# 或使用 Chocolatey
choco install nmap
```

### macOS
```bash
brew install nmap
```

### 验证安装
```bash
nmap --version
nmap -h          # 帮助
```

---

<a id="-basic-syntax"></a>
## ⌨️ 基本语法

### 通用语法
```bash
nmap [Scan Type] [Options] {target}
```

### 快速入门示例
```bash
# 基本扫描
nmap 192.168.1.1

# 扫描多个主机
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# 扫描一个范围
nmap 192.168.1.1-254

# 扫描整个子网
nmap 192.168.1.0/24

# 从文件读取目标进行扫描
nmap -iL targets.txt
```

---

<a id="-target-specification"></a>
## 🎯 目标指定方式

### 单个目标
```bash
# IP 地址
nmap 192.168.1.1

# 主机名
nmap scanme.nmap.org

# 域名
nmap example.com
```

### 多个目标
```bash
# 以空格分隔
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# 范围
nmap 192.168.1.1-100

# CIDR 表示法
nmap 192.168.1.0/24

# 通配符
nmap 192.168.1.*

# 八位组范围
nmap 192.168.0-255.1-254

# 混合指定
nmap 192.168.1.0/24 10.0.0.1
```

### 从文件输入
```bash
# 从文件读取
nmap -iL targets.txt

# targets.txt:
# 192.168.1.1
# 192.168.1.0/24
# scanme.nmap.org
```

### 排除目标
```bash
# 排除单个主机
nmap 192.168.1.0/24 --exclude 192.168.1.1

# 从文件读取排除列表
nmap 192.168.1.0/24 --excludefile exclude.txt
```

### 随机目标
```bash
# 扫描随机主机（原文用于研究场景）
nmap -iR 100
```

---

<a id="-host-discovery"></a>
## 🔎 主机发现

> **译注：** “Ping 扫描”不只意味着 ICMP 回显，也可使用 TCP、UDP、ARP 等探测。`-sL` 仍可能进行反向 DNS 查询；在本地以太网上，`-Pn` 也不必然禁止 ARP 地址解析。参见 [官方主机发现说明](https://nmap.org/book/man-host-discovery.html)。

### 主机发现选项

| 选项 | 说明 |
|--------|-------------|
| `-sL` | 列表扫描，仅列出目标 |
| `-sn` | 主机发现（Ping 扫描），不进行端口扫描 |
| `-Pn` | 跳过主机发现，将所有目标视为在线 |
| `-PS` | TCP SYN 探测 |
| `-PA` | TCP ACK 探测 |
| `-PU` | UDP 探测 |
| `-PE` | ICMP 回显探测 |
| `-PP` | ICMP 时间戳探测 |
| `-PM` | ICMP 地址掩码探测 |
| `-PO` | IP 协议探测 |
| `-PR` | ARP 探测（本地网络） |

### 主机发现扫描（不扫描端口）
```bash
# 仅发现存活主机
nmap -sn 192.168.1.0/24

# 使用主机列表进行主机发现
nmap -sn -iL hosts.txt
```

### 跳过主机发现
```bash
# 扫描所有目标（不先进行主机发现探测）
nmap -Pn 192.168.1.1

# 适用情况：
# - 主机阻止 ICMP
# - 经由防火墙扫描
```

### TCP SYN/ACK 主机探测
```bash
# 在端口 80 上发送 TCP SYN 探测
nmap -PS80 192.168.1.1

# 在端口 80 上发送 TCP ACK 探测
nmap -PA80 192.168.1.1

# 多个端口
nmap -PS22,80,443 192.168.1.1
```

### UDP 主机探测
```bash
# UDP 探测
nmap -PU53 192.168.1.1

# 多个端口
nmap -PU53,161 192.168.1.1
```

### ICMP 主机探测
```bash
# ICMP 回显
nmap -PE 192.168.1.1

# ICMP 时间戳
nmap -PP 192.168.1.1

# ICMP 地址掩码
nmap -PM 192.168.1.1
```

### ARP 主机探测（本地网络）
```bash
# ARP 扫描（原文称其在本地网络最快）
nmap -PR 192.168.1.0/24

# 仅进行 ARP 扫描
nmap -sn -PR 192.168.1.0/24
```

### 禁用 DNS 解析
```bash
# 不进行 DNS 解析（更快）
nmap -n 192.168.1.1

# 始终进行 DNS 解析
nmap -R 192.168.1.1
```

---

<a id="-port-scanning-techniques"></a>
## 🔌 端口扫描技术

### 扫描类型概览

| 选项 | 名称 | 说明 |
|--------|------|-------------|
| `-sS` | SYN 扫描 | 原文称为隐蔽扫描（默认，需要 root 权限） |
| `-sT` | TCP connect 扫描 | 完整建立 TCP 连接（无需 root 权限） |
| `-sU` | UDP 扫描 | 扫描 UDP 端口 |
| `-sA` | ACK 扫描 | 探测防火墙过滤规则 |
| `-sW` | 窗口扫描（Window Scan） | 类似 ACK 扫描，利用窗口字段 |
| `-sM` | Maimon 扫描 | FIN/ACK 探测 |
| `-sN` | NULL 扫描 | 不设置标志位 |
| `-sF` | FIN 扫描 | 设置 FIN 标志位 |
| `-sX` | Xmas 扫描 | 设置 FIN、PSH、URG 标志位 |

### SYN 隐蔽扫描（默认）
```bash
# 常用扫描方式（需要 root 权限）
sudo nmap -sS 192.168.1.1

# 不完成完整的 TCP 三次握手
# 速度快，原文称其较隐蔽
```

### TCP connect 扫描
```bash
# 完整建立 TCP 连接
nmap -sT 192.168.1.1

# 无需 root 权限
# 更容易被检测，原文称其始终可用（这一说法过于绝对）
```

### UDP 扫描
```bash
# 扫描 UDP 端口
sudo nmap -sU 192.168.1.1

# UDP 扫描速度较慢
# 结合版本检测
sudo nmap -sU -sV 192.168.1.1

# 常用 UDP 端口
sudo nmap -sU -p 53,67,68,69,123,161,500 192.168.1.1
```

### 组合 TCP/UDP 扫描
```bash
# 同时扫描 TCP 和 UDP
sudo nmap -sS -sU 192.168.1.1

# 指定端口
sudo nmap -sS -sU -p T:80,443,U:53,161 192.168.1.1
```

### NULL、FIN 与 Xmas 扫描
```bash
# NULL 扫描（无标志位）
sudo nmap -sN 192.168.1.1

# FIN 扫描（仅 FIN 标志位）
sudo nmap -sF 192.168.1.1

# Xmas 扫描（FIN、PSH、URG）
sudo nmap -sX 192.168.1.1

# 适用情况与限制：
# - 绕过某些无状态防火墙
# - 原文指出不适用于 Windows
```

### ACK 扫描
```bash
# 探测防火墙过滤规则
sudo nmap -sA 192.168.1.1

# 用于判断：
# - 哪些端口被过滤
# - 防火墙过滤规则
# 返回状态：unfiltered 或 filtered
```

### 窗口扫描（Window Scan）
```bash
# 类似 ACK 扫描
sudo nmap -sW 192.168.1.1

# 利用 TCP 窗口大小判断状态
```

### 空闲扫描（Idle/Zombie Scan）
```bash
# 原文称其极为隐蔽：使用僵尸主机
sudo nmap -sI zombie_host 192.168.1.1

# 原文称目标不会直接收到来自您的 IP 的探测
# 需要合适的僵尸主机
```

---

<a id="-port-specification"></a>
## 🎚️ 端口指定方式

### 端口选项

| 选项 | 说明 |
|--------|-------------|
| `-p <port>` | 扫描指定端口 |
| `-p-` | 扫描 1-65535 端口 |
| `-p 1-1000` | 扫描端口范围 |
| `--top-ports <n>` | 扫描最常见的 N 个端口 |
| `-F` | 快速扫描（最常见的 100 个端口） |
| `-r` | 按顺序扫描端口 |

### 指定端口
```bash
# 单个端口
nmap -p 80 192.168.1.1

# 多个端口
nmap -p 80,443,22 192.168.1.1

# 端口范围
nmap -p 1-1000 192.168.1.1

# 所有端口
nmap -p- 192.168.1.1

# 混合指定
nmap -p 22,80,443,1000-2000 192.168.1.1
```

### 按协议指定端口

> **译注（示例缺项）：** `-p` 中的 `T:`、`U:` 用于分配协议对应的端口列表，本身不启用扫描类型。因此下方 UDP 及 TCP/UDP 示例还需要对应的 `-sU` 和 TCP 扫描类型选项，不能仅凭 `U:` 就认为已扫描 UDP。`-p-` 默认为 1-65535，不包含端口 0。参见 [官方端口指定说明](https://nmap.org/book/man-port-specification.html)。
```bash
# TCP 端口
nmap -p T:80,443 192.168.1.1

# UDP 端口
nmap -p U:53,161 192.168.1.1

# 两者
nmap -p T:80,443,U:53 192.168.1.1
```

### 最常见端口
```bash
# 最常见的 100 个端口（快速）
nmap -F 192.168.1.1

# 最常见的 10 个端口
nmap --top-ports 10 192.168.1.1

# 最常见的 1000 个端口（默认）
nmap --top-ports 1000 192.168.1.1
```

### 常见端口参考

| 端口 | 服务 |
|------|---------|
| 21 | FTP |
| 22 | SSH |
| 23 | Telnet |
| 25 | SMTP |
| 53 | DNS |
| 80 | HTTP |
| 110 | POP3 |
| 135 | MSRPC |
| 139 | NetBIOS |
| 143 | IMAP |
| 443 | HTTPS |
| 445 | SMB |
| 993 | IMAPS |
| 995 | POP3S |
| 1433 | MSSQL |
| 1521 | Oracle |
| 3306 | MySQL |
| 3389 | RDP |
| 5432 | PostgreSQL |
| 5900 | VNC |
| 8080 | HTTP-Proxy |

---

<a id="-service--version-detection"></a>
## 🔬 服务与版本检测

### 版本检测选项

| 选项 | 说明 |
|--------|-------------|
| `-sV` | 版本 detection |
| `--version-intensity <0-9>` | 探测强度 |
| `--version-light` | 轻量版本扫描（强度 2） |
| `--version-all` | 尝试所有探针（强度 9） |
| `--version-trace` | 调试版本扫描 |

### 基本版本检测
```bash
# 检测服务版本
nmap -sV 192.168.1.1

# 常见输出：
# PORT    STATE SERVICE VERSION
# 22/tcp  open  ssh     OpenSSH 7.9
# 80/tcp  open  http    Apache httpd 2.4.38
```

### 版本探测强度
```bash
# 轻量扫描（更快）
nmap -sV --version-light 192.168.1.1

# 所有探针（更全面）
nmap -sV --version-all 192.168.1.1

# 自定义强度（0-9）
nmap -sV --version-intensity 5 192.168.1.1
```

---

<a id="-os-detection"></a>
## 💻 操作系统检测

### 操作系统检测选项

| 选项 | 说明 |
|--------|-------------|
| `-O` | 启用操作系统检测 |
| `--osscan-limit` | 仅对适合进行检测的主机执行检测 |
| `--osscan-guess` | 更积极地猜测操作系统 |
| `--max-os-tries` | 操作系统检测的最大尝试次数 |

### 基本操作系统检测
```bash
# 检测操作系统（需要 root 权限）
sudo nmap -O 192.168.1.1

# 输出示例：
# Running: Linux 3.X|4.X
# OS CPE: cpe:/o:linux:linux_kernel:3
# OS details: Linux 3.10 - 4.11
```

### 积极猜测操作系统
```bash
# 更积极地猜测
sudo nmap -O --osscan-guess 192.168.1.1
```

### 综合扫描（-A）
```bash
# 启用操作系统检测、版本检测、脚本扫描与路由跟踪
sudo nmap -A 192.168.1.1

# 对应功能：-O -sV -sC --traceroute
```

---

<a id="-timing--performance"></a>
## ⏱️ 时序与性能

### 时序模板

| 选项 | 名称 | 说明 |
|--------|------|-------------|
| `-T0` | Paranoid（极慢） | 原文用于规避 IDS，速度很慢 |
| `-T1` | Sneaky（慢速） | 原文用于规避 IDS，速度较慢 |
| `-T2` | Polite（温和） | 占用较少带宽，速度较慢 |
| `-T3` | Normal（常规） | 默认时序 |
| `-T4` | Aggressive（积极） | 较快，适用于可靠网络 |
| `-T5` | Insane（极快） | 速度很快，可能漏掉端口 |

### 用法
```bash
# 慢速，原文称其较隐蔽
nmap -T1 192.168.1.1

# 常规（默认）
nmap -T3 192.168.1.1

# 快速
nmap -T4 192.168.1.1

# 非常快（本地网络）
nmap -T5 192.168.1.1
```

### 精细时序控制

| 选项 | 说明 |
|--------|-------------|
| `--min-rate <n>` | 每秒最少数据包数 |
| `--max-rate <n>` | 每秒最多数据包数 |
| `--min-parallelism <n>` | 最少并行探测数 |
| `--max-parallelism <n>` | 最多并行探测数 |
| `--min-hostgroup <n>` | 最少并行主机数 |
| `--max-hostgroup <n>` | 最多并行主机数 |
| `--host-timeout <time>` | 达到指定时长后放弃扫描该主机 |
| `--scan-delay <time>` | 探测间隔 |
| `--max-retries <n>` | 最大探测重试次数 |

```bash
# 每秒至少发送 1000 个数据包
nmap --min-rate 1000 192.168.1.0/24

# 每秒最多发送 100 个数据包（降低速度）
nmap --max-rate 100 192.168.1.1

# 30 分钟后对该主机停止扫描
nmap --host-timeout 30m 192.168.1.0/24

# 探测间隔
nmap --scan-delay 1s 192.168.1.1
```

---

<a id="-nse-scripts"></a>
## 📜 NSE 脚本

> **译注：** 脚本类别不是无副作用保证，`-sC` 中也可能包含侵入性脚本。`--script "not intrusive"` 是选择表达式，会选择所有符合条件的脚本，不能简单理解为仅从某个已选列表中排除脚本。本文保留原文示例；参见 [官方 NSE 参数说明](https://nmap.org/book/man-nse.html)。

### 脚本分类

| 类别 | 说明 |
|----------|-------------|
| `auth` | 身份验证相关操作（原文概括为绕过） |
| `broadcast` | 通过广播发现主机 |
| `brute` | 暴力破解攻击 |
| `default` | 默认脚本（-sC） |
| `discovery` | 服务发现 |
| `dos` | 拒绝服务 |
| `exploit` | 利用漏洞 |
| `external` | 查询外部服务 |
| `fuzzer` | 模糊测试 |
| `intrusive` | 可能导致服务崩溃 |
| `malware` | 恶意软件检测 |
| `safe` | safe 类别脚本（不等于无条件安全） |
| `version` | 版本 detection |
| `vuln` | 漏洞检测 |

### 脚本选项

| 选项 | 说明 |
|--------|-------------|
| `-sC` | 默认脚本 |
| `--script <scripts>` | 运行指定脚本 |
| `--script-args <args>` | 脚本参数 |
| `--script-updatedb` | 更新脚本数据库 |
| `--script-help <script>` | 脚本帮助 |

### 默认脚本
```bash
# 运行默认脚本
nmap -sC 192.168.1.1

# 配合版本检测（原文推荐）
nmap -sV -sC 192.168.1.1
```

### 运行指定脚本
```bash
# 单个脚本
nmap --script http-title 192.168.1.1

# 多个脚本
nmap --script http-title,http-headers 192.168.1.1

# 类别
nmap --script vuln 192.168.1.1

# 通配符
nmap --script "http-*" 192.168.1.1

# 组合类别
nmap --script "vuln and safe" 192.168.1.1

# 排除脚本
nmap --script "not intrusive" 192.168.1.1
```

### 常用脚本

#### 漏洞扫描
```bash
# 所有漏洞类别脚本
nmap --script vuln 192.168.1.1

# 检查指定 CVE
nmap --script smb-vuln-ms17-010 192.168.1.1

# Heartbleed
nmap --script ssl-heartbleed 192.168.1.1

# ShellShock
nmap --script http-shellshock 192.168.1.1
```

#### HTTP 脚本
```bash
# HTTP 枚举
nmap --script http-enum 192.168.1.1

# HTTP 响应头
nmap --script http-headers 192.168.1.1

# HTTP 方法
nmap --script http-methods 192.168.1.1

# HTTP 标题
nmap --script http-title 192.168.1.1

# Robots.txt
nmap --script http-robots.txt 192.168.1.1
```

#### SMB 脚本
```bash
# SMB 枚举
nmap --script smb-enum-shares 192.168.1.1
nmap --script smb-enum-users 192.168.1.1
nmap --script smb-os-discovery 192.168.1.1

# SMB 漏洞
nmap --script smb-vuln* 192.168.1.1
```

#### SSH 脚本
```bash
# SSH 身份验证方法
nmap --script ssh-auth-methods 192.168.1.1

# SSH 主机密钥
nmap --script ssh-hostkey 192.168.1.1

# SSH 暴力破解
nmap --script ssh-brute 192.168.1.1
```

#### DNS 脚本
```bash
# DNS 暴力枚举
nmap --script dns-brute example.com

# DNS 区域传送
nmap --script dns-zone-transfer example.com
```

#### MySQL 与数据库脚本
```bash
# MySQL 信息
nmap --script mysql-info 192.168.1.1

# MySQL 枚举
nmap --script mysql-enum 192.168.1.1

# MySQL 暴力破解
nmap --script mysql-brute 192.168.1.1
```

### 脚本参数
```bash
# 使用字典暴力破解
nmap --script ssh-brute --script-args userdb=users.txt,passdb=passwords.txt 192.168.1.1

# HTTP 身份验证暴力破解
nmap --script http-brute --script-args http-brute.path=/admin 192.168.1.1
```

---

<a id="-output-formats"></a>
## 📄 输出格式

> **译注：** `-oA` 同时生成 normal、XML、grepable 三种格式，不包括 `-oS`。`--open` 还会保留 `open|filtered` 和 `unfiltered` 等可能开放的状态，因此不能把所有显示结果都断定为开放端口。参见 [官方输出说明](https://nmap.org/book/man-output.html)。

### 输出选项

| 选项 | 说明 |
|--------|-------------|
| `-oN <file>` | 普通输出 |
| `-oX <file>` | XML 输出 |
| `-oG <file>` | 适合 grep 处理的输出 |
| `-oA <basename>` | 全部格式（原文概括，实际为三种） |
| `-oS <file>` | sCRiPt KiDDi3 风格输出 |

### 保存输出
```bash
# 普通输出
nmap -oN scan.txt 192.168.1.1

# XML 输出（供工具使用）
nmap -oX scan.xml 192.168.1.1

# 适合 grep 处理的输出
nmap -oG scan.gnmap 192.168.1.1

# 同时保存多种格式
nmap -oA scan 192.168.1.1
# 生成：scan.nmap、scan.xml、scan.gnmap
```

### 详细程度与调试

| 选项 | 说明 |
|--------|-------------|
| `-v` | 增加输出详细程度 |
| `-vv` | 更加详细 |
| `-d` | 调试模式 |
| `-dd` | 更多调试信息 |
| `--reason` | 显示判定端口状态的原因 |
| `--open` | 仅显示开放端口（原文简述，实际包含可能开放状态） |
| `--packet-trace` | 显示所有数据包 |

```bash
# 详细输出
nmap -v 192.168.1.1

# 非常详细
nmap -vv 192.168.1.1

# 仅显示开放或可能开放的端口
nmap --open 192.168.1.1

# 显示判定原因
nmap --reason 192.168.1.1
```

---

<a id="-firewallids-evasion"></a>
## 🛡️ 防火墙与入侵检测系统（IDS）规避

> **适用范围：** “隐蔽”“始终可用”等原文措辞过于绝对，SYN、慢速时序、分片、诱饵或填充均不能保证逃避检测或绕过过滤。部分选项仅影响特定探测类型，版本检测或脚本产生的连接也不一定继承相同效果。参见 [官方规避选项说明](https://nmap.org/book/man-bypass-firewalls-ids.html)。

### 规避技术

| 选项 | 说明 |
|--------|-------------|
| `-f` | 对数据包分片 |
| `--mtu <size>` | 自定义 MTU 大小 |
| `-D <decoys>` | 使用诱饵 |
| `-S <IP>` | 伪造源 IP |
| `--source-port <port>` | 伪造源端口 |
| `--data-length <n>` | 追加随机数据 |
| `--randomize-hosts` | 随机主机顺序 |
| `--spoof-mac <mac>` | 伪造 MAC 地址 |
| `--badsum` | 发送校验和错误的数据包 |

### 数据包分片
```bash
# 对数据包分片
nmap -f 192.168.1.1

# 自定义 MTU
nmap --mtu 16 192.168.1.1
```

### 诱饵
```bash
# 使用诱饵
nmap -D decoy1,decoy2,ME 192.168.1.1

# 随机诱饵
nmap -D RND:5 192.168.1.1
```

### 源 IP 与端口伪造
```bash
# 伪造源 IP
nmap -S 192.168.1.100 -e eth0 192.168.1.1

# 伪造源端口（原文选用常见允许端口）
nmap --source-port 53 192.168.1.1
nmap --source-port 80 192.168.1.1
```

### MAC 地址伪造
```bash
# 随机 MAC 地址
nmap --spoof-mac 0 192.168.1.1

# 指定厂商
nmap --spoof-mac Apple 192.168.1.1

# 指定 MAC 地址
nmap --spoof-mac 00:11:22:33:44:55 192.168.1.1
```

### 数据填充
```bash
# 向数据包追加随机数据
nmap --data-length 25 192.168.1.1
```

---

<a id="-real-world-examples"></a>
## 🎬 实际场景示例

### 示例 1：快速网络主机探测
```bash
# 发现存活主机
nmap -sn 192.168.1.0/24
```

### 示例 2：基本端口扫描
```bash
# 扫描并检测版本
nmap -sV 192.168.1.1
```

### 示例 3：综合扫描
```bash
# 包含操作系统检测和脚本的扫描
sudo nmap -sS -sV -O -sC 192.168.1.1
```

### 示例 4：综合全端口扫描
```bash
# 所有端口，启用综合检测
sudo nmap -A -T4 -p- 192.168.1.1
```

### 示例 5：漏洞扫描
```bash
# 检查漏洞
nmap --script vuln 192.168.1.1
```

### 示例 6：隐蔽扫描
```bash
# 原文称为隐蔽扫描
sudo nmap -sS -T2 -f --data-length 25 192.168.1.1
```

### 示例 7：Web 服务器扫描
```bash
# 综合 Web 扫描
nmap -sV -p 80,443,8080,8443 --script "http-*" 192.168.1.1
```

### 示例 8：SMB 枚举
```bash
# 完整 SMB 枚举
nmap -p 445 --script smb-enum-shares,smb-enum-users,smb-vuln* 192.168.1.1
```

### 示例 9：整个子网扫描
```bash
# 扫描整个子网
nmap -sS -sV -O -T4 --open 192.168.1.0/24 -oA subnet_scan
```

### 示例 10：UDP 服务
```bash
# 常见 UDP 服务
sudo nmap -sU -sV -p 53,67,68,69,123,161,500 192.168.1.1
```

---

<a id="-quick-reference"></a>
## 📊 快速参考

### 核心命令

| 命令 | 说明 |
|---------|-------------|
| `nmap <target>` | 基本扫描 |
| `nmap -sn <target>` | 仅进行主机发现 |
| `nmap -Pn <target>` | 跳过主机发现 |
| `nmap -sS <target>` | SYN 隐蔽扫描 |
| `nmap -sV <target>` | 版本 detection |
| `nmap -O <target>` | 操作系统检测 |
| `nmap -A <target>` | 综合扫描 |
| `nmap -sC <target>` | 默认脚本 |
| `nmap -p- <target>` | 所有端口 |
| `nmap --script vuln <target>` | 漏洞扫描 |

### 速度与隐蔽性

| 需求 | 选项 |
|------|---------|
| **快速** | `-T4 --min-rate 1000` |
| **隐蔽** | `-T1 -f --data-length 25` |
| **均衡** | `-T3` （默认） |

### 常见扫描组合

```bash
# 快速扫描
nmap -F 192.168.1.1

# 完整 TCP 端口扫描
nmap -sT -p- 192.168.1.1

# 服务扫描
nmap -sV 192.168.1.1

# 漏洞扫描
nmap --script vuln 192.168.1.1

# 综合检测全部端口
sudo nmap -A -T4 -p- 192.168.1.1
```

---

<a id="-tips--best-practices"></a>
## 💡 使用技巧与最佳实践

### 性能建议

1. **使用 SYN 扫描（默认）**
   ```bash
   # 需要 root 权限，原文称其最快
   sudo nmap -sS 192.168.1.1
   ```

2. **先限制端口范围**
   ```bash
   # 从最常见端口开始
   nmap --top-ports 100 192.168.1.1
   ```

3. **提高本地网络扫描速度**
   ```bash
   nmap -T5 --min-rate 5000 192.168.1.0/24
   ```

### 准确性建议

1. **结合多种扫描类型**
   ```bash
   # TCP 与 UDP
   sudo nmap -sS -sU -sV 192.168.1.1
   ```

2. **为不可靠网络增加重试次数（原文标题，示例限制见译注）**

   > **译注：** `--max-retries 3` 是将重传上限设为 3；官方说明在不指定 `-T` 模板时默认最多允许 10 次，因此此示例不是相对默认值“增加重试”。过高发包速率还可能降低准确性。参见 [官方时序说明](https://nmap.org/book/man-performance.html)。
   ```bash
   nmap --max-retries 3 192.168.1.1
   ```

### 法律注意事项

> ⚠️ **重要提示：** 仅扫描您拥有或已获准扫描的网络。

- ✅ 您自己的网络
- ✅ 实验环境
- ✅ 获得授权的渗透测试
- ❌ 随机互联网主机
- ❌ 未经授权的网络

---

## ⚠️ 法律免责声明

> **警告：** Nmap 是功能强大的工具，仅可用于 **获得授权的扫描**.
> 
> - ✅ 扫描您拥有的网络
> - ✅ 获得明确书面许可后扫描
> - ✅ 用于获得授权的渗透测试
> - ❌ 未经授权不得扫描网络
> - ❌ 未经授权扫描可能违法
> 
> **在许多司法辖区，未经授权的网络扫描属于违法行为。**

---

<a id="-resources"></a>
## 📚 资源

### 官方资源
- [Nmap 官方网站](https://nmap.org/)
- [Nmap 参考指南](https://nmap.org/book/man.html)
- [NSE 脚本文档](https://nmap.org/nsedoc/)

### 学习资源
- [Nmap Network Scanning 官方书籍](https://nmap.org/book/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### 相关速查表
- [Metasploit Framework](../../Metasploit/README.md)
- [SQLMap](../../SQLMap/README.md)
- [Burp Suite](../../Burp-Suite/README.md)

---

<p align="center">
  <b>🔍 掌握网络侦察！</b><br>
  <i>在攻击者之前了解您的网络</i>
</p>
