# 🦈 Wireshark——完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 __        ___           ____  _                _    
 \ \      / (_)_ __ ___ / ___|| |__   __ _ _ __| | __
  \ \ /\ / /| | '__/ _ \\___ \| '_ \ / _` | '__| |/ /
   \ V  V / | | | |  __/ ___) | | | | (_| | |  |   < 
    \_/\_/  |_|_|  \___|____/|_| |_|\__,_|_|  |_|\_\
                                                     
           网络协议分析工具
```

<p align="center">
  <img src="https://img.shields.io/badge/Wireshark-Network_Analyzer-blue?style=for-the-badge" alt="Wireshark">
  <img src="https://img.shields.io/badge/Packet-Capture-red?style=for-the-badge" alt="Packet Capture">
  <img src="https://img.shields.io/badge/Protocol-Analysis-green?style=for-the-badge" alt="Protocol">
  <img src="https://img.shields.io/badge/Open-Source-orange?style=for-the-badge" alt="Open Source">
</p>

<p align="center">
  <b>🌐 广受欢迎的网络协议分析工具</b>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [界面概览](#-interface-overview)
- [捕获基础](#-capture-basics)
- [显示过滤器](#-display-filters)
- [捕获过滤器](#-capture-filters)
- [协议分析](#-protocol-analysis)
- [追踪数据流](#-following-streams)
- [统计与分析](#-statistics--analysis)
- [tshark（命令行）](#-tshark-cli)
- [实际应用示例](#-real-world-examples)
- [快速参考](#-quick-reference)
- [快捷键](#-keyboard-shortcuts)
- [技巧与最佳实践](#-tips--best-practices)
- [参考资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**Wireshark** 是免费的开源网络数据包分析工具，用于网络故障排查、分析、软件开发与安全审计。

### 主要功能

| 功能 | 说明 |
|---------|-------------|
| **深入解析** | 支持数百种协议 |
| **实时捕获** | 实时捕获数据包 |
| **离线分析** | 读取捕获文件（pcap、pcapng） |
| **强大的过滤功能** | 显示过滤器与捕获过滤器 |
| **跨平台** | Windows, Linux, macOS |
| **图形界面与命令行** | Wireshark（GUI）与 tshark（CLI） |

### 适用场景

| 适用场景 | 说明 |
|----------|-------------|
| **网络故障排查** | 排查连接问题 |
| **安全分析** | 检测恶意流量 |
| **协议开发** | 测试新协议 |
| **数字取证** | 分析捕获的流量 |
| **教学** | 学习网络知识 |

---

<a id="-installation"></a>
## 📥 安装

### Windows
```bash
# 从官方网站下载
https://www.wireshark.org/download.html

# 或使用 Chocolatey
choco install wireshark
```

### Linux（Kali／Ubuntu）

> **捕获权限说明：** 把用户加入 `wireshark` 组只是流程的一部分，还需要按发行版配置 `dumpcap` 的捕获权限。不同安装方式和接口类型的要求不同，参见 [Wireshark 官方权限说明](https://wiki.wireshark.org/CaptureSetup/CapturePrivileges)。本译文保留原始安装示例，未执行任何捕获。
```bash
# Kali（预装）
wireshark

# Ubuntu/Debian
sudo apt install wireshark

# 允许非 root 用户捕获
sudo usermod -aG wireshark $USER
# 退出后重新登录
```

### macOS
```bash
brew install --cask wireshark
```

### 验证安装
```bash
wireshark --version
tshark --version
```

---

<a id="-interface-overview"></a>
## 🖥️ 界面概览

### 主窗口区域

| 区域 | 说明 |
|------|-------------|
| **Menu Bar（菜单栏）** | File、Edit、View、Go、Capture、Analyze（文件、编辑、视图、跳转、捕获、分析） |
| **Main Toolbar（主工具栏）** | 快捷操作按钮 |
| **Filter Bar（过滤栏）** | 输入显示过滤器 |
| **Packet List（数据包列表）** | 全部已捕获数据包 |
| **Packet Details（数据包详细信息）** | 选中数据包的各协议层 |
| **Packet Bytes（数据包字节）** | 原始十六进制／ASCII 数据 |

### 数据包列表默认列

| 列 | 说明 |
|--------|-------------|
| No. | 数据包编号 |
| Time | 时间戳 |
| Source | 源 IP／MAC |
| Destination | 目标 IP／MAC |
| Protocol | 最高层协议 |
| Length | 数据包字节数 |
| Info | 协议相关信息 |

---

<a id="-capture-basics"></a>
## 📡 捕获基础

### 开始捕获

```
1. 从欢迎界面选择网络接口
2. 点击 "Start Capture"（开始捕获，蓝色鲨鱼鳍图标）
3. 或：Capture → Start（捕获 → 开始）

快捷方式：双击接口名称
```

### 停止捕获

```
点击工具栏中的红色方块
或：Capture → Stop（捕获 → 停止）
或：Ctrl+E
```

### 保存捕获结果

```
File → Save As（文件 → 另存为）
格式：pcapng（默认）、pcap 等
```

### 打开捕获文件

```
File → Open（文件 → 打开）
或：Ctrl+O
或：wireshark file.pcap
```

---

<a id="-display-filters"></a>
## 🔍 显示过滤器

> **语法说明：** 本文不少显示过滤表达式放在标为 `bash` 的代码块中，它们应输入 Wireshark／TShark 的显示过滤位置，而不是作为 shell 命令执行。字段名称、字符串与过滤表达式均按原样保留。捕获过滤器则使用 BPF 语法。

### 过滤器语法

显示过滤器使用**字段比较语法**：

```
field operator value（字段 运算符 值）
```

### 比较运算符

| 运算符 | 说明 | 示例 |
|----------|-------------|---------|
| `==` 或 `eq` | 相等 | `ip.addr == 192.168.1.1` |
| `!=` 或 `ne` | 不相等 | `ip.addr != 10.0.0.1` |
| `>` 或 `gt` | 大于 | `frame.len > 100` |
| `<` 或 `lt` | 小于 | `tcp.port < 1024` |
| `>=` 或 `ge` | 大于或等于 | `frame.len >= 64` |
| `<=` 或 `le` | 小于或等于 | `tcp.port <= 1024` |
| `contains` | 包含字符串 | `http contains "password"` |
| `matches` | 正则表达式匹配 | `http.host matches ".*\.com"` |

### 逻辑运算符

| 运算符 | 说明 | 示例 |
|----------|-------------|---------|
| `and` 或 `&&` | 逻辑与 | `ip.src == 192.168.1.1 and tcp.port == 80` |
| `or` 或 `\|\|` | 逻辑或 | `tcp.port == 80 or tcp.port == 443` |
| `not` 或 `!` | 逻辑非 | `not arp` |
| `()` | 分组 | `(ip.src == 10.0.0.1) and (tcp or udp)` |

### IP 过滤器

```bash
# 指定 IP 地址（源或目标）
ip.addr == 192.168.1.1

# 仅源 IP
ip.src == 192.168.1.1

# 仅目标 IP
ip.dst == 192.168.1.1

# IP 地址范围（子网）
ip.addr == 192.168.1.0/24

# 排除 IP
ip.addr != 192.168.1.1

# 两台主机之间
ip.addr == 192.168.1.1 and ip.addr == 192.168.1.2
```

### 端口过滤器

> **多值字段说明：** `tcp.port` 在一个包中可能同时包含源端口与目标端口。原文范围示例中的两个比较可分别匹配不同的字段值，因此不能简单理解为“同一个端口落在范围内”。对 `ip.addr` 等重复字段，等于／不等于的 any／all 语义也要按版本核对，参见 [官方显示过滤器手册](https://www.wireshark.org/docs/man-pages/wireshark-filter.html)。

```bash
# 指定端口（源或目标）
tcp.port == 80
udp.port == 53

# 仅源端口
tcp.srcport == 443

# 仅目标端口
tcp.dstport == 22

# 端口范围
tcp.port >= 1 and tcp.port <= 1024

# 多个端口
tcp.port == 80 or tcp.port == 443 or tcp.port == 8080
tcp.port in {80 443 8080}
```

### 协议过滤器

```bash
# 按协议名称
http
dns
tcp
udp
arp
icmp
tls
ssh
ftp
smtp
dhcp

# HTTP 专用
http.request
http.response
http.request.method == "GET"
http.request.method == "POST"
http.response.code == 200
http.response.code >= 400

# DNS 专用
dns.qry.name == "google.com"
dns.qry.type == 1          # A 记录
dns.flags.response == 1    # DNS 响应

# TCP 专用
tcp.flags.syn == 1         # SYN 数据包
tcp.flags.fin == 1         # FIN 数据包
tcp.flags.reset == 1       # RST 数据包
tcp.analysis.retransmission  # 重传
tcp.stream == 5            # 指定数据流
```

### 内容过滤器

```bash
# 包含字符串
http contains "password"
tcp contains "login"
frame contains "secret"

# HTTP 内容
http.request.uri contains "admin"
http.host == "example.com"
http.user_agent contains "Mozilla"
http.cookie contains "session"

# 不区分大小写的匹配
http.host matches "(?i)example"
```

### 常见过滤器示例

> **原文校准：** `tcp.flags.syn == 1 or tcp.flags.ack == 1` 会匹配普通带 ACK 的 TCP 包，并不只显示三次握手。`tls` 表示被解析为 TLS 的流量，也不等于“全部 HTTPS”。原始过滤式与数值保持不变。

```bash
# 仅 HTTP 流量
http

# HTTPS 流量（原文简写；tls 并非仅用于 HTTPS）
tls

# DNS 查询
dns.flags.response == 0

# 连接失败（RST，原文概括；需结合会话判断）
tcp.flags.reset == 1

# 大数据包
frame.len > 1000

# 指定 MAC 地址
eth.addr == aa:bb:cc:dd:ee:ff

# 广播流量
eth.dst == ff:ff:ff:ff:ff:ff

# 非本地流量
!(ip.addr == 192.168.0.0/16)

# TCP 三次握手（原文说法；该过滤器也会匹配普通 ACK）
tcp.flags.syn == 1 or tcp.flags.ack == 1

# 带数据的 HTTP POST 请求
http.request.method == "POST" and http.file_data
```

---

<a id="-capture-filters"></a>
## 🎣 捕获过滤器

### BPF 语法（Berkeley Packet Filter）

捕获过滤器使用 **BPF 语法**，与显示过滤器的语法不同。

### 基本语法

```bash
# 主机
host 192.168.1.1
src host 192.168.1.1
dst host 192.168.1.1

# 网络
net 192.168.1.0/24
src net 10.0.0.0/8

# 端口
port 80
src port 443
dst port 22
portrange 1-1024

# 协议
tcp
udp
icmp
arp
```

### 组合过滤器

```bash
# 主机与端口
host 192.168.1.1 and port 80

# 指定会话
host 192.168.1.1 and host 192.168.1.2

# 排除流量
not port 22
not host 10.0.0.1

# 复杂过滤器
tcp and (port 80 or port 443) and host 192.168.1.1
```

### 常见捕获过滤器

| 用途 | 过滤器 |
|---------|--------|
| 指定主机 | `host 192.168.1.1` |
| Web 流量 | `port 80 or port 443` |
| 仅 DNS | `port 53` |
| 仅 SSH | `port 22` |
| 排除广播 | `not broadcast` |
| 指定子网 | `net 192.168.1.0/24` |

---

<a id="-protocol-analysis"></a>
## 🔬 协议分析

### TCP 分析

```bash
# TCP 标志位
tcp.flags.syn == 1 and tcp.flags.ack == 0    # 仅 SYN
tcp.flags.syn == 1 and tcp.flags.ack == 1    # SYN-ACK
tcp.flags.fin == 1                            # FIN
tcp.flags.reset == 1                          # RST

# TCP 问题
tcp.analysis.retransmission
tcp.analysis.duplicate_ack
tcp.analysis.zero_window
tcp.analysis.lost_segment

# TCP 数据流
tcp.stream == 0
```

### HTTP 分析

```bash
# HTTP 请求
http.request
http.request.method == "GET"
http.request.method == "POST"
http.request.uri contains "/login"

# HTTP 响应
http.response
http.response.code == 200
http.response.code == 404
http.response.code >= 400    # 错误

# HTTP 内容
http.content_type contains "json"
http.content_type contains "html"
http.file_data contains "password"
```

### DNS 分析

```bash
# DNS 查询
dns.flags.response == 0
dns.qry.name == "example.com"
dns.qry.type == 1      # A 记录
dns.qry.type == 28     # AAAA 记录
dns.qry.type == 5      # CNAME
dns.qry.type == 15     # MX

# DNS 响应
dns.flags.response == 1
dns.flags.rcode != 0   # 错误
```

### TLS／SSL 分析

> **TLS 版本说明：** `tls.record.version == 0x0303` 不能单独确认协商版本为 TLS 1.2；TLS 1.3 也使用 `0x0303` 作为兼容性的记录层版本值。实际协商版本应结合握手与 `supported_versions` 分析，参见 [RFC 8446](https://www.rfc-editor.org/rfc/rfc8446.html)。

```bash
# TLS 流量
tls

# TLS 握手
tls.handshake
tls.handshake.type == 1     # Client Hello（客户端问候）
tls.handshake.type == 2     # Server Hello（服务器问候）

# 证书
tls.handshake.certificate

# TLS 版本
tls.record.version == 0x0303  # TLS 1.2
```

### ARP 分析

```bash
# 全部 ARP
arp

# ARP 请求
arp.opcode == 1

# ARP 响应
arp.opcode == 2

# 免费 ARP／主动通告 ARP（可能需要排查欺骗）
arp.isgratuitous == 1
```

---

<a id="-following-streams"></a>
## 🔗 追踪数据流

### 追踪 TCP 流

```
右键点击数据包 → Follow → TCP Stream（追踪 TCP 流）

或：Analyze → Follow → TCP Stream（分析 → 追踪 TCP 流）
```

### 追踪 HTTP 流

```
右键点击 HTTP 数据包 → Follow → HTTP Stream（追踪 HTTP 流）
```

### 追踪 UDP 流

```
右键点击 UDP 数据包 → Follow → UDP Stream（追踪 UDP 流）
```

### 数据流窗口

- 显示完整会话
- 以颜色区分客户端与服务器
- 保存为文本或原始数据
- 使用按钮按方向筛选

---

<a id="-statistics--analysis"></a>
## 📊 统计与分析

### Protocol Hierarchy（协议层级）

```
Statistics → Protocol Hierarchy（统计 → 协议层级）

显示已捕获协议的分布
```

### Conversations（会话）

```
Statistics → Conversations（统计 → 会话）

按以下类别查看全部会话：
- Ethernet
- IPv4/IPv6
- TCP
- UDP
```

### Endpoints（端点）

```
Statistics → Endpoints（统计 → 端点）

列出全部唯一端点
```

### IO Graph（I/O 图表）

```
Statistics → IO Graph（统计 → I/O 图表）

按时间可视化流量
```

### Expert Information（专家信息）

```
Analyze → Expert Information（分析 → 专家信息）

显示警告、错误与提示：
- TCP 重传
- 格式异常的数据包
- 错误
```

### HTTP 统计

```
Statistics → HTTP → Requests（HTTP 请求统计）
Statistics → HTTP → Packet Counter（HTTP 数据包计数）
```

---

<a id="-tshark-cli"></a>
## 💻 tshark（命令行）

### 基本用法

```bash
# 捕获数据包
tshark -i eth0

# 读取捕获文件
tshark -r capture.pcap

# 写入文件
tshark -i eth0 -w output.pcap
```

### 应用过滤器

```bash
# 捕获过滤器
tshark -i eth0 -f "port 80"

# 显示过滤器
tshark -r capture.pcap -Y "http"
```

### 输出选项

```bash
# 显示指定字段
tshark -r capture.pcap -T fields -e ip.src -e ip.dst -e tcp.port

# JSON 输出
tshark -r capture.pcap -T json

# 显示统计信息
tshark -r capture.pcap -q -z io,stat,1
```

### 常见 tshark 示例

```bash
# 实时捕获 HTTP 请求
tshark -i eth0 -Y "http.request" -T fields -e http.host -e http.request.uri

# 提取 DNS 查询
tshark -r capture.pcap -Y "dns.flags.response == 0" -T fields -e dns.qry.name

# 按 IP 统计数据包
tshark -r capture.pcap -q -z ip_hosts,tree

# HTTP 统计
tshark -r capture.pcap -q -z http,tree
```

---

<a id="-real-world-examples"></a>
## 🎬 实际应用示例

### 示例 1：捕获 HTTP 流量
```bash
# 捕获过滤器：port 80
# 显示过滤器：http
# 追踪 HTTP 流，查看完整请求与响应
```

### 示例 2：查找凭据
```bash
# 显示过滤器
http.request.method == "POST" and http contains "password"

# 或针对 FTP
ftp contains "PASS"
```

### 示例 3：检测端口扫描
```bash
# 向不同端口发送多个 SYN
tcp.flags.syn == 1 and tcp.flags.ack == 0

# 查找同一来源到多个目标的连接
Statistics → Conversations → TCP（TCP 会话统计）
```

### 示例 4：DNS 数据外传

> **调查线索说明：** 大于 512 字节的 DNS 帧或 TXT 查询本身不能证明数据外传，需结合查询内容、频率、来源与正常业务交叉验证。同样，免费 ARP 和 RST 数据包也可出现在正常网络行为中。
```bash
# 较大的 DNS 查询
dns and frame.len > 512

# 异常 TXT 记录
dns.qry.type == 16
```

### 示例 5：提取文件
```
File → Export Objects → HTTP（文件 → 导出对象 → HTTP）

选择要导出的文件
```

### 示例 6：分析 TLS 握手
```bash
tls.handshake

# 在 Client Hello 中查看 SNI（服务器名称指示）
tls.handshake.type == 1
```

### 示例 7：查找明文密码
```bash
http contains "password" or http contains "passwd" or ftp or telnet
```

---

<a id="-quick-reference"></a>
## 📊 快速参考

### 常用显示过滤器

| 用途 | 过滤器 |
|---------|--------|
| HTTP 流量 | `http` |
| DNS 流量 | `dns` |
| 指定 IP | `ip.addr == 192.168.1.1` |
| 指定端口 | `tcp.port == 80` |
| TCP 问题 | `tcp.analysis.flags` |
| HTTP 错误 | `http.response.code >= 400` |
| 包含字符串 | `frame contains "text"` |
| 排除协议 | `not arp` |

### 常见捕获过滤器

| 用途 | 过滤器 |
|---------|--------|
| 指定主机 | `host 192.168.1.1` |
| Web 流量 | `port 80 or port 443` |
| 仅 DNS | `port 53` |
| 排除广播 | `not broadcast` |

---

<a id="-keyboard-shortcuts"></a>
## ⌨️ 快捷键

| 快捷键 | 操作 |
|----------|--------|
| `Ctrl+E` | 开始／停止捕获 |
| `Ctrl+K` | 捕获选项 |
| `Ctrl+O` | 打开文件 |
| `Ctrl+S` | 保存文件 |
| `Ctrl+F` | 查找数据包 |
| `Ctrl+G` | 跳转到数据包 |
| `Ctrl+→` | 会话中的下一个数据包 |
| `Ctrl+←` | 会话中的上一个数据包 |
| `Ctrl+.` | 下一个已标记的数据包 |
| `Ctrl+M` | 标记／取消标记数据包 |
| `Ctrl+Shift+M` | 标记全部已显示数据包 |
| `Space` | 展开／折叠协议树 |
| `Tab` | 下一个窗格 |

---

<a id="-tips--best-practices"></a>
## 💡 技巧与最佳实践

### 捕获技巧

1. **使用捕获过滤器**
   ```
   捕获时过滤，减少文件大小
   ```

2. **长时间捕获使用环形缓冲**
   ```
   Capture → Options → Output → Ring buffer（捕获 → 选项 → 输出 → 环形缓冲）
   ```

3. **远程捕获**
   ```bash
   ssh user@host tcpdump -w - | wireshark -k -i -
   ```

### 分析技巧

1. **着色规则**
   ```
   View → Coloring Rules（视图 → 着色规则）
   为协议自定义颜色
   ```

2. **时间显示**
   ```
   View → Time Display Format（视图 → 时间显示格式）
   尝试 "Seconds Since Beginning of Capture"（自捕获开始以来的秒数）
   ```

3. **名称解析**
   ```
   View → Name Resolution（视图 → 名称解析）
   按需要启用或禁用
   ```

---

## ⚠️ 法律免责声明

> **警告：** 网络数据包捕获只能用于**已获授权的用途**。
> 
> - ✅ 在自己拥有的网络上捕获
> - ✅ 取得许可后开展安全测试
> - ✅ 排查已获授权系统的故障
> - ❌ 不得在未授权网络上捕获流量
> - ❌ 捕获他人的流量可能违法
> 
> **未经授权的网络监控在多数司法管辖区属于违法行为。**

---

<a id="-resources"></a>
## 📚 参考资源

### 官方资源
- [Wireshark 官方网站](https://www.wireshark.org/)
- [Wireshark 用户指南](https://www.wireshark.org/docs/wsug_html/)
- [显示过滤器参考](https://www.wireshark.org/docs/dfref/)
- [Wireshark Wiki](https://wiki.wireshark.org/)

### 学习资源
- [Wireshark 教程](https://www.wireshark.org/docs/wsug_html/)
- [示例捕获文件](https://wiki.wireshark.org/SampleCaptures)

### 相关速查表
- [Nmap](../../Nmap/translations/README.zh-CN.md)
- [Metasploit](../../Metasploit/translations/README.zh-CN.md)
- [Hydra](../../Hydra/translations/README.zh-CN.md)

---

<p align="center">
  <b>🦈 掌握网络分析！</b><br>
  <i>洞察网络中的流量</i>
</p>
