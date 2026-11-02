# 📡 tcpdump 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **原文示例勘误：** “仅抓取入站流量”的命令把 `$(...)` 放在单引号内，Bash 不会展开其中的命令替换，因此不能按原文意图自动得到本机 IP。原始命令保留不变，参见 [Bash 单引号说明](https://www.gnu.org/software/bash/manual/html_node/Single-Quotes.html)。
>
> **输出与权限说明：** `-w capture.pcap | tee` 不会自动显示解析后的数据包，因为抓包数据写入的是指定文件；`-Z user` 的降权时机是在打开抓包设备或输入文件后、打开输出文件前，并非抓包完成后。`-s 96` 只限制截取字节数，不能保证总是“仅头部”；`-s0` 通常恢复默认的较大截取长度，并不是取消所有长度限制。`wc -l` 统计输出行数，多行解码时不等于数据包数量。参见 [tcpdump 官方手册源文件](https://github.com/the-tcpdump-group/tcpdump/blob/master/tcpdump.1.in)。
>
> **过滤器解读：** HTTP 小节的首个过滤器判断 IPv4/TCP 数据包是否含有负载，并未区分请求与响应。GET/POST 字节匹配依赖对应字符串位于单包中的预期位置，不能代替 TCP 流重组。DNS 示例 2 的 `udp port 53` 同时匹配查询与响应；仅出现 SYN 也不足以单独确认端口扫描。TLS/HTTPS 的应用数据不会因使用 ASCII 输出而自动解密。所有 BPF 表达式、十六进制匹配值与命令均保持原样；语法可参阅 [libpcap 官方过滤器手册](https://github.com/the-tcpdump-group/libpcap/blob/master/pcap-filter.manmisc.in)。

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _             _                       
 | |_ ___ _ __ | |_ _ _ _____ _ __ _ __ 
 | __/ __| '_ \| __| | | '_ ` _ \| '_ \
 | || (__| |_) | |_| |_| | | | | | |_) |
  \__\___| .__/ \__|\__,_|_| |_| | .__/ 
         |_|                     |_|    
      命令行数据包分析器
```

<p align="center">
  <img src="https://img.shields.io/badge/tcpdump-Packet_Analyzer-blue?style=for-the-badge" alt="tcpdump">
  <img src="https://img.shields.io/badge/Command-Line-red?style=for-the-badge" alt="CLI">
  <img src="https://img.shields.io/badge/BPF-Filters-green?style=for-the-badge" alt="BPF">
  <img src="https://img.shields.io/badge/Cross-Platform-orange?style=for-the-badge" alt="Cross Platform">
</p>

<p align="center">
  <b>🔍 经典的命令行数据包分析器</b>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [基本语法](#-basic-syntax)
- [常用选项](#-common-options)
- [BPF 过滤器](#-bpf-filters)
- [协议过滤器](#-protocol-filters)
- [输出选项](#-output-options)
- [实践示例](#-real-world-examples)
- [速查](#-quick-reference)
- [技巧与最佳实践](#-tips--best-practices)
- [参考资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**tcpdump** 是一款强大的命令行数据包分析器，可在大多数类 Unix 操作系统上使用。它采用 libpcap 库和 Berkeley Packet Filter（BPF，伯克利包过滤器）语法。

### 主要功能

| 功能 | 说明 |
|---------|-------------|
| **轻量** | 无图形界面，占用资源少 |
| **BPF 过滤器** | 强大的数据包过滤能力 |
| **兼容 pcap** | 读写 pcap 文件 |
| **可脚本化** | 适合自动化 |
| **通用** | 可在各类 Unix 系统上使用 |

### tcpdump 与 Wireshark 对比

| 功能 | tcpdump | Wireshark |
|---------|---------|-----------|
| **界面** | CLI | GUI |
| **资源** | 少 | 较高 |
| **远程操作** | 可直接通过 SSH 使用 | 需要配置 |
| **脚本化** | 简单 | 较复杂 |
| **分析** | 基础 | 高级 |

---

<a id="-installation"></a>
## 📥 安装

### Linux（多数发行版预装）
```bash
# Kali/Debian/Ubuntu
sudo apt install tcpdump

# CentOS/RHEL
sudo yum install tcpdump

# Arch
sudo pacman -S tcpdump
```

### macOS（预装）
```bash
tcpdump --version
```

### 验证安装
```bash
tcpdump --version
tcpdump -D              # 列出接口
```

---

<a id="-basic-syntax"></a>
## ⌨️ 基本语法

### 通用语法
```bash
tcpdump [options] [filter expression]
```

### 基础命令
```bash
# 在默认接口上抓包
sudo tcpdump

# 在指定接口上抓包
sudo tcpdump -i eth0

# 限定抓取的数据包数量
sudo tcpdump -c 100

# 抓包并显示详细输出
sudo tcpdump -v
sudo tcpdump -vv
sudo tcpdump -vvv
```

---

<a id="-common-options"></a>
## ⚙️ 常用选项

### 显示选项

| 选项 | 说明 |
|--------|-------------|
| `-i` | 用于抓包的接口 |
| `-c` | 要抓取的数据包数量 |
| `-n` | 不解析主机名 |
| `-nn` | 不解析主机名或端口名称 |
| `-v` | 详细输出 |
| `-vv` | 更详细的输出 |
| `-vvv` | 最详细的输出 |
| `-q` | 精简输出 |
| `-t` | 不显示时间戳 |
| `-tt` | 显示 Unix 时间戳 |
| `-ttt` | 显示时间差 |
| `-tttt` | 显示日期和时间 |

### 抓包选项

| 选项 | 说明 |
|--------|-------------|
| `-w` | 写入 pcap 文件 |
| `-r` | 读取 pcap 文件 |
| `-s` | 截取长度（字节） |
| `-s0` | 抓取完整数据包（实际受截取长度上限限制） |
| `-D` | 列出可用接口 |

### 输出选项

| 选项 | 说明 |
|--------|-------------|
| `-A` | 显示 ASCII |
| `-X` | 显示十六进制与 ASCII |
| `-XX` | 显示十六进制与 ASCII，包含链路层头部 |
| `-e` | 显示链路层头部 |
| `-l` | 按行缓冲输出 |

### 常用组合
```bash
# 不进行 DNS 解析、详细输出、完整抓包
sudo tcpdump -nnvvs0 -i eth0

# 保存到文件
sudo tcpdump -i eth0 -w capture.pcap

# 从文件读取
tcpdump -r capture.pcap

# ASCII 输出
sudo tcpdump -A -i eth0

# 十六进制 + ASCII
sudo tcpdump -X -i eth0
```

---

<a id="-bpf-filters"></a>
## 🎣 BPF 过滤器

### 主机过滤器

```bash
# 与指定主机往来的流量
tcpdump host 192.168.1.1

# 仅匹配源主机
tcpdump src host 192.168.1.1

# 仅匹配目标主机
tcpdump dst host 192.168.1.1

# 多个主机
tcpdump host 192.168.1.1 or host 192.168.1.2

# 排除主机
tcpdump not host 192.168.1.1

# 网络/子网
tcpdump net 192.168.1.0/24
tcpdump src net 10.0.0.0/8
```

### 端口过滤器

```bash
# 指定端口
tcpdump port 80
tcpdump port 443

# 源端口
tcpdump src port 443

# 目标端口
tcpdump dst port 22

# 端口范围
tcpdump portrange 1-1024

# 多个端口
tcpdump port 80 or port 443
tcpdump port 80 or port 443 or port 8080
```

### 协议过滤器

```bash
# 按协议过滤
tcpdump tcp
tcpdump udp
tcpdump icmp
tcpdump arp

# 协议与端口
tcpdump tcp port 80
tcpdump udp port 53
```

### 逻辑运算符

| 运算符 | 替代写法 | 说明 |
|----------|-------------|-------------|
| `and` | `&&` | 逻辑与（AND） |
| `or` | `\|\|` | 逻辑或（OR） |
| `not` | `!` | 逻辑非（NOT） |
| `()` | | 分组 |

### 组合过滤器

```bash
# 主机与端口同时匹配
tcpdump host 192.168.1.1 and port 80

# 主机与协议同时匹配
tcpdump host 192.168.1.1 and tcp

# 复杂过滤器
tcpdump 'host 192.168.1.1 and (port 80 or port 443)'

# 排除指定流量
tcpdump not port 22 and not port 53

# 两个主机之间的流量
tcpdump host 192.168.1.1 and host 192.168.1.2
```

---

<a id="-protocol-filters"></a>
## 📡 协议过滤器

### TCP 标志位

```bash
# SYN 数据包
tcpdump 'tcp[tcpflags] & tcp-syn != 0'
tcpdump 'tcp[13] & 2 != 0'

# SYN-ACK 数据包
tcpdump 'tcp[tcpflags] & (tcp-syn|tcp-ack) == (tcp-syn|tcp-ack)'

# FIN 数据包
tcpdump 'tcp[tcpflags] & tcp-fin != 0'

# RST 数据包
tcpdump 'tcp[tcpflags] & tcp-rst != 0'

# PSH 数据包
tcpdump 'tcp[tcpflags] & tcp-push != 0'

# 仅设置 ACK 标志
tcpdump 'tcp[tcpflags] == tcp-ack'
```

### TCP 标志位速查

| 标志位 | 十六进制 | 十进制 | tcpdump |
|------|-----|-----|---------|
| FIN | 0x01 | 1 | tcp-fin |
| SYN | 0x02 | 2 | tcp-syn |
| RST | 0x04 | 4 | tcp-rst |
| PSH | 0x08 | 8 | tcp-push |
| ACK | 0x10 | 16 | tcp-ack |
| URG | 0x20 | 32 | tcp-urg |

### HTTP 流量

```bash
# HTTP 请求（端口 80；原文过滤器实际匹配有负载的 TCP 包，见译注）
tcpdump -A 'tcp port 80 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)'

# GET 请求
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'

# POST 请求
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x504f5354'

# 简单的 HTTP 抓包
tcpdump -A -s0 'tcp port 80'
```

### DNS 流量

```bash
# 所有 DNS 流量
tcpdump port 53
tcpdump udp port 53

# 仅 DNS 查询
tcpdump 'udp port 53 and udp[10] & 0x80 = 0'

# 仅 DNS 响应
tcpdump 'udp port 53 and udp[10] & 0x80 = 0x80'
```

### ICMP 流量

```bash
# 所有 ICMP 流量
tcpdump icmp

# ICMP 回显请求（ping）
tcpdump 'icmp[icmptype] == icmp-echo'

# ICMP 回显应答
tcpdump 'icmp[icmptype] == icmp-echoreply'

# ICMP 不可达消息
tcpdump 'icmp[icmptype] == icmp-unreach'
```

### ARP 流量

```bash
# 所有 ARP 流量
tcpdump arp

# ARP 请求
tcpdump 'arp[6:2] == 1'

# ARP 应答
tcpdump 'arp[6:2] == 2'
```

---

<a id="-output-options"></a>
## 💾 输出选项

### 写入文件

```bash
# 写入 pcap 文件
tcpdump -w capture.pcap

# 轮换写入（100 个文件，每个 1MB）
tcpdump -w capture.pcap -W 100 -C 1

# 在文件名中加入时间戳
tcpdump -w capture_%Y%m%d_%H%M%S.pcap -G 3600

# 抓包并显示（原文管道示例有误，见译注）
tcpdump -w capture.pcap | tee
```

### 从文件读取

```bash
# 读取 pcap 文件
tcpdump -r capture.pcap

# 读取时应用过滤器
tcpdump -r capture.pcap 'host 192.168.1.1'

# 统计数据包数量（以输出行数估算，详见译注）
tcpdump -r capture.pcap | wc -l

# 读取并显示详细输出
tcpdump -nnvvXSs0 -r capture.pcap
```

### 显示格式

```bash
# ASCII 内容
tcpdump -A -i eth0

# 十六进制与 ASCII
tcpdump -X -i eth0

# 仅十六进制
tcpdump -x -i eth0

# 包含以太网头部
tcpdump -XX -i eth0

# 显示绝对序列号
tcpdump -S -i eth0
```

---

<a id="-real-world-examples"></a>
## 🎬 实践示例

### 示例 1：抓取 Web 流量
```bash
sudo tcpdump -i eth0 -nnvvXSs0 'tcp port 80 or tcp port 443'
```

### 示例 2：抓取 DNS 查询
```bash
sudo tcpdump -i eth0 -nn 'udp port 53'
```

### 示例 3：抓取与指定主机往来的流量
```bash
sudo tcpdump -i eth0 -nn host 192.168.1.100
```

### 示例 4：查找 SSH 连接
```bash
sudo tcpdump -i eth0 'tcp port 22'
```

### 示例 5：抓取 ICMP（Ping）
```bash
sudo tcpdump -i eth0 icmp
```

### 示例 6：检测端口扫描（SYN）
```bash
sudo tcpdump -i eth0 'tcp[tcpflags] == tcp-syn'
```

### 示例 7：仅抓取入站流量（原文引用方式有误，见译注）
```bash
sudo tcpdump -i eth0 'dst host $(hostname -I | cut -d" " -f1)'
```

### 示例 8：监控指定通信
```bash
sudo tcpdump -i eth0 'host 192.168.1.1 and host 192.168.1.2'
```

### 示例 9：抓取 FTP 凭据
```bash
sudo tcpdump -A -i eth0 'tcp port 21'
```

### 示例 10：保存供 Wireshark 分析
```bash
sudo tcpdump -i eth0 -s0 -w capture.pcap
# 然后在 Wireshark 中打开 capture.pcap
```

### 示例 11：通过 SSH 远程抓包
```bash
ssh user@remote 'sudo tcpdump -w - -i eth0' | wireshark -k -i -
```

### 示例 12：抓包时排除 SSH
```bash
sudo tcpdump -i eth0 'not port 22'
```

---

<a id="-quick-reference"></a>
## 📊 速查

### 常用命令

| 任务 | 命令 |
|------|---------|
| 列出接口 | `tcpdump -D` |
| 基础抓包 | `tcpdump -i eth0` |
| 不进行 DNS 解析 | `tcpdump -nn` |
| 抓取 N 个数据包 | `tcpdump -c 100` |
| 写入文件 | `tcpdump -w file.pcap` |
| 从文件读取 | `tcpdump -r file.pcap` |
| 详细输出 | `tcpdump -v` |
| ASCII 输出 | `tcpdump -A` |
| 十六进制输出 | `tcpdump -X` |
| 完整数据包 | `tcpdump -s0` |

### 常用过滤器

| 过滤器 | 说明 |
|--------|-------------|
| `host 1.2.3.4` | 与主机往来的流量 |
| `port 80` | 端口 80 上的流量 |
| `tcp` | 仅 TCP 流量 |
| `udp` | 仅 UDP 流量 |
| `icmp` | 仅 ICMP 流量 |
| `net 192.168.1.0/24` | 与子网往来的流量 |
| `src host 1.2.3.4` | 源地址为指定主机 |
| `dst port 443` | 目标端口为 443 |

### 过滤器组合

| 示例 | 说明 |
|---------|-------------|
| `host A and port 80` | 主机 A，端口 80 |
| `port 80 or port 443` | 端口 80 或 443 |
| `not port 22` | 排除 SSH |
| `tcp and port 80` | TCP 端口 80 |

---

<a id="-tips--best-practices"></a>
## 💡 技巧与最佳实践

### 性能建议

```bash
# 不进行 DNS 解析（更快）
tcpdump -nn

# 限制每个数据包的抓取长度
tcpdump -s 96   # 仅头部（原文说法并非对所有数据包都成立）

# 使用 BPF 过滤器减少负载
tcpdump 'host 192.168.1.1'  # 在内核层过滤
```

### 安全建议

```bash
# 以非 root 用户运行（需预先配置）
# 或尽量缩短抓包时间

# 抓包后降低权限（原文时机描述有误，见译注）
tcpdump -Z user
```

### 实用别名

```bash
# 添加到 ~/.bashrc
alias tcpdump='sudo tcpdump -nnvvXSs0'
alias tcpdump-web='sudo tcpdump -nnvvXSs0 port 80 or port 443'
alias tcpdump-dns='sudo tcpdump -nn port 53'
```

---

## ⚠️ 法律免责声明

> **警告：** 网络抓包仅应用于 **获得授权的用途**。
> 
> - ✅ 在自己拥有的网络中抓包
> - ✅ 获得许可后开展安全测试
> - ✅ 对获授权系统进行故障排查
> - ❌ 切勿在未获授权的网络中抓包
> - ❌ 抓取他人的流量可能违法
> 
> **未获授权的网络监控属于违法行为。**

---

<a id="-resources"></a>
## 📚 参考资源

### 官方资源
- [tcpdump 手册](https://www.tcpdump.org/manpages/tcpdump.1.html)
- [tcpdump 官网](https://www.tcpdump.org/)
- [BPF 过滤语法](https://biot.com/capstats/bpf.html)

### 相关速查表
- [Wireshark](../../Wireshark/README.md)
- [Nmap](../../Nmap/README.md)
- [Metasploit](../../Metasploit/README.md)

---

<p align="center">
  <b>📡 掌握命令行数据包分析技术！</b><br>
  <i>Wireshark 的轻量替代工具</i>
</p>
