# 📶 Wifite 自动化 Wi-Fi 审计速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗██╗███████╗██╗████████╗███████╗
  ██║    ██║██║██╔════╝██║╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║█████╗  ██║   ██║   █████╗  
  ██║███╗██║██║██╔══╝  ██║   ██║   ██╔══╝  
  ╚███╔███╔╝██║██║     ██║   ██║   ███████╗
   ╚══╝╚══╝ ╚═╝╚═╝     ╚═╝   ╚═╝   ╚══════╝
           自动化 Wi-Fi 安全测试工具
```

<p align="center">
  <img src="https://img.shields.io/badge/Wifite-Automated_WiFi-blue?style=for-the-badge" alt="Wifite">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Python-yellow?style=for-the-badge" alt="Python">
</p>

---

## 📋 目录

- [Wifite 是什么](#-what-is-wifite)
- [安装](#-installation)
- [基本用法](#-basic-usage)
- [目标选择](#-target-selection)
- [攻击选项](#-attack-options)
- [WPS 攻击](#-wps-attacks)
- [PMKID 攻击](#-pmkid-attacks)
- [高级选项](#-advanced-options)
- [密码破解选项](#-cracking-options)
- [示例](#-examples)
- [速查表](#-quick-reference)

---

<a id="-what-is-wifite"></a>
## 🎯 Wifite 是什么

**Wifite** （Wifite2）是一款自动化无线网络审计工具，具备以下功能：

- 🤖 **自动执行攻击流程** - 无需逐条手动输入命令
- 🎯 **多目标** - 可测试多个网络
- 🔓 **WPA/WPA2/WEP/WPS** - 多种加密与认证相关机制（原文概括）
- 💾 **保存进度** - 继续中断的测试
- 🔄 **PMKID 捕获** - 无需已连接客户端的攻击方式

### Wifite 与 Wifite2 对比

| 功能 | Wifite（原始版本） | Wifite2 |
|---------|-------------------|---------|
| 是否维护 | 否 | 是 |
| Python | Python 2 | Python 3 |
| PMKID | 否 | 是 |
| 代码 | 较为杂乱 | 重新编写，结构更清晰 |
| 使用建议 | 不建议使用 | **推荐** |

### 功能特点

| 功能 | 说明 |
|---------|-------------|
| **自动化** | 选定目标后自动进行 Wi-Fi 审计 |
| **智能目标选择** | 按信号强度选择目标 |
| **攻击方式** | WPA, WEP, WPS, PMKID |
| **密码破解** | 集成 Hashcat／aircrack |
| **可继续执行** | 继续中断的会话 |

---

<a id="-installation"></a>
## 🚀 安装

> **版本说明：** 原文主要围绕 Wifite2，安装与参数示例均原样保留。[原项目](https://github.com/derv82/wifite2)现将 Wifite2 用户指向 kimocoder 维护的分支；不同发行版和分支的参数、依赖及保存路径可能不同。WPS 是配网机制，并非与 WPA/WPA2 并列的加密算法；功能列表中的“全部类型”也是概括。

### Kali Linux（预装）

```bash
# Kali 中已安装
wifite --help

# 更新到最新版本
sudo apt update && sudo apt install wifite
```

### 从 GitHub 安装 Wifite2

```bash
# 克隆仓库
git clone https://github.com/derv82/wifite2.git
cd wifite2

# 直接运行
sudo python3 wifite.py

# 也可以安装
sudo python3 setup.py install
```

### 依赖工具

```bash
# 必需工具
sudo apt install aircrack-ng

# 建议安装以获得完整功能
sudo apt install reaver bully pixiewps hashcat hcxdumptool hcxtools
```

### 验证安装

```bash
wifite --help
wifite --version
```

---

<a id="-basic-usage"></a>
## 💻 基本用法

### 快速开始

```bash
# 直接运行 wifite（自动检测接口）
sudo wifite

# 指定接口
sudo wifite -i wlan0
```

### 基本流程

```
1. 运行 wifite
2. 等待网络出现在列表中
3. 按 Ctrl+C 停止扫描
4. 按编号选择目标
5. Wifite 自动执行攻击流程
6. 对捕获的握手进行密码破解
```

### 交互模式

```bash
# 启动 wifite
sudo wifite

# 示例输出：
   NUM  ESSID              ENCR  POWER  WPS  CLIENT
   ---  -----------------  ----  -----  ---  ------
    1   HomeNetwork        WPA2   -45   Yes     2
    2   Office_WiFi        WPA2   -52   No      0
    3   Free_Hotspot       OPN    -60   No      5

# 输入目标编号，或按 Ctrl+C 选择全部可见目标（原文说明，需按实际提示操作）
```

---

<a id="-target-selection"></a>
## 🎯 目标选择

### 按编号选择

```bash
# 扫描后，按编号选择目标
# 示例：选择目标 1 和 3
1, 3

# 选择编号范围
1-5

# 选择全部目标
all
```

### 按 ESSID 筛选

```bash
# 指定目标网络名称
sudo wifite --essid "TargetNetwork"

# 多个 ESSID
sudo wifite --essid "Network1" --essid "Network2"
```

### 按 BSSID 筛选

```bash
# 指定目标 MAC 地址
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### 按加密类型筛选

```bash
# 仅 WPA 网络
sudo wifite --wpa

# 仅 WEP 网络
sudo wifite --wep

# 仅启用了 WPS 的网络
sudo wifite --wps
```

### 按信道筛选

```bash
# 指定信道
sudo wifite -c 6

# 仅 5GHz（原文说明，需核对版本）
sudo wifite --5ghz

# 仅 2.4GHz
sudo wifite --2ghz
```

### 按信号强度筛选

```bash
# 最低信号强度（原文按 dBm 描述，越接近 0 越强；参见下方说明）
sudo wifite --power -50
```

> **信号说明：** [当前维护分支的目标解析代码](https://github.com/kimocoder/wifite2/blob/master/wifite/model/target.py)会对负的信号值加上 `100` 后再用于内部强度表示。因此，原文的 `--power -50` 不能直接当作通用的 dBm 门槛，需核对所用版本的标度。

---

<a id="-attack-options"></a>
## ⚔️ 攻击选项

### WPA/WPA2 选项

```bash
# 设置取消认证的超时时间
sudo wifite --wpa-deauth-timeout 30

# 发送的取消认证帧数量
sudo wifite --num-deauths 5

# 跳过握手捕获，仅使用 PMKID
sudo wifite --pmkid
```

### 取消认证（Deauthentication）选项

```bash
# 取消认证帧数量
sudo wifite --num-deauths 10

# 持续取消认证（原文说明，需核对工具与版本）
sudo wifite --num-deauths 0
```

### 攻击顺序

```bash
# 跳过 WPA 攻击
sudo wifite --no-wpa

# 跳过 WEP 攻击
sudo wifite --no-wep

# 跳过 WPS 攻击
sudo wifite --no-wps

# 跳过 PMKID 攻击
sudo wifite --no-pmkid
```

### 终止 NetworkManager

```bash
# 自动终止干扰进程
sudo wifite --kill

# 不终止进程
sudo wifite --no-kill
```

> **参数兼容性：** [维护分支的参数定义](https://github.com/kimocoder/wifite2/blob/master/wifite/args.py)与原文部分长选项不同，例如 WPA 时间参数使用 `--wpadt`／`--wpat`，跳过已破解目标使用 `--ignore-cracked`。其他开关应逐项核对实际版本；仅凭原文名称不能认定支持。

---

<a id="-wps-attacks"></a>
## 🔓 WPS 攻击

### 启用 WPS 测试

```bash
# 仅测试启用了 WPS 的网络
sudo wifite --wps

# 仅 WPS 模式
sudo wifite --wps-only
```

### WPS 工具

```bash
# 使用 Reaver（默认）
sudo wifite --wps --reaver

# 使用 Bully
sudo wifite --wps --bully
```

### Pixie-Dust 攻击

```bash
# 启用 Pixie-Dust（离线 WPS 攻击）
sudo wifite --wps --pixie

# Pixie-Dust 超时时间
sudo wifite --wps --pixie-timeout 300
```

### WPS 选项

```bash
# 每次 PIN 尝试的 WPS 超时时间
sudo wifite --wps-time 120

# WPS 最大重试次数
sudo wifite --wps-fails 10

# WPS 总超时时间
sudo wifite --wps-timeout 300
```

> **WPS 模式说明：** [参数定义](https://github.com/kimocoder/wifite2/blob/master/wifite/args.py)中的 `--wps-only` 包含 PIN 与 Pixie-Dust 两类 WPS 攻击，并不等于“仅 PIN 暴力破解”。

---

<a id="-pmkid-attacks"></a>
## 🔐 PMKID 攻击

### PMKID 是什么？

PMKID（成对主密钥标识符）攻击：
- ✅ 直接从接入点（AP）捕获 PMK（原文表述，参见下方勘误）
- ✅ 无需已连接的客户端
- ✅ 无需取消认证操作
- ✅ 适用于大多数 WPA2 网络（原文概括，需核对条件）

### PMKID 选项

> **PMKID 勘误：** [原始研究说明](https://hashcat.net/forum/thread-7717.html)捕获的是由 PMK 等信息计算出的 PMKID 标识符，不是直接取得 PMK。适用性取决于接入点的实现与配置，“大多数 WPA2 网络都可用”不能作为保证；“无需客户端”指无需已有的正常客户端参与，并非完全不与 AP 通信。

```bash
# 仅 PMKID 攻击（不捕获完整握手）
sudo wifite --pmkid

# PMKID 超时时间
sudo wifite --pmkid-timeout 120
```

### PMKID 的工作原理

```
1. 向 AP 发送关联请求
2. AP 在 EAPOL 帧中返回 PMKID
3. 捕获 PMKID
4. 使用 hashcat 进行离线密码破解
```

---

<a id="-advanced-options"></a>
## ⚙️ 高级选项

### 接口选项

```bash
# 指定接口
sudo wifite -i wlan0

# 指定监听接口
sudo wifite -i wlan0mon
```

### 输出选项

```bash
# 详细输出
sudo wifite -v
sudo wifite --verbose

# 更详细的输出
sudo wifite -vv

# 安静输出模式
sudo wifite --quiet
```

### 时间设置

```bash
# 扫描时间（秒）
sudo wifite --scan-time 30

# 每个目标的总超时时间
sudo wifite --attack-timeout 300
```

### 已破解的密码

```bash
# 已破解密码保存位置：
# ~/.config/wifite/cracked.txt

# 查看已破解的网络
cat ~/.config/wifite/cracked.txt

# 也可以指定自定义文件（原文说明，参见下方勘误）
sudo wifite --cracked cracked_passwords.txt
```

> **路径与命令说明：** 原文路径树不是所有版本的固定布局。[原分支配置](https://github.com/derv82/wifite2/blob/master/wifite/config.py)使用相对的 `cracked.txt` 与 `hs`；[维护分支](https://github.com/kimocoder/wifite2/blob/master/wifite/args.py)的 `--cracked` 是显示结果的布尔开关，不接受自定义文件名，`--crack` 则用于显示已有捕获的破解命令。

### 跳过选项

```bash
# 跳过密码破解（仅捕获）
sudo wifite --skip-crack

# 跳过已破解的目标
sudo wifite --skip-cracked
```

### 随机 MAC 地址

```bash
# 随机化 MAC 地址
sudo wifite --random-mac

# 不更改 MAC 地址
sudo wifite --no-random-mac
```

---

<a id="-cracking-options"></a>
## 🔓 密码破解选项

### 字典攻击

```bash
# 指定字典
sudo wifite --dict /usr/share/wordlists/rockyou.txt

# 多个字典
sudo wifite --dict wordlist1.txt --dict wordlist2.txt
```

### Hashcat 集成

```bash
# 使用 hashcat 破解密码
sudo wifite --hashcat

# Hashcat 路径
sudo wifite --hashcat-path /usr/bin/hashcat
```

### Aircrack-ng 选项

```bash
# 使用 aircrack-ng（默认）
sudo wifite --aircrack

# Aircrack-ng 路径
sudo wifite --aircrack-path /usr/bin/aircrack-ng
```

### 仅破解模式

```bash
# 破解已有握手文件（原文说明，参见下方补充）
sudo wifite --crack

# 指定捕获文件
sudo wifite --crack --dict wordlist.txt /path/to/capture.cap
```

---

<a id="-examples"></a>
## 📋 示例

### 基础 Wi-Fi 审计

```bash
# 扫描并测试所有网络
sudo wifite

# 使用自定义字典测试
sudo wifite --dict /usr/share/wordlists/rockyou.txt
```

### 针对指定网络

```bash
# 按名称选择目标
sudo wifite --essid "TargetNetwork" --dict rockyou.txt

# 按 MAC 地址选择目标
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### 仅针对 WPA2 的攻击

```bash
# 仅针对支持 PMKID 的 WPA2 网络（原文说明）
sudo wifite --wpa --pmkid --dict rockyou.txt
```

### WPS 攻击

```bash
# 通过 Pixie-Dust 测试 WPS
sudo wifite --wps --pixie

# 仅进行 WPS 暴力破解（原文说明，参见下方补充）
sudo wifite --wps-only --bully
```

### 仅针对强信号目标

```bash
# 仅测试信号较强的网络
sudo wifite --power -50 --wpa
```

### 5GHz 网络

```bash
# 仅测试 5GHz 频段（原文说明，需核对版本）
sudo wifite --5ghz --wpa
```

### 快速审计（不破解密码）

```bash
# 仅捕获握手，之后再破解
sudo wifite --skip-crack
```

### 继续中断的测试

```bash
# Wifite 自动保存进度
# 再次运行即可继续（原文说明，需核对版本）
sudo wifite

# 跳过已破解的目标
sudo wifite --skip-cracked
```

### 详细输出模式

```bash
# 显示全部详细信息
sudo wifite -vv --dict rockyou.txt
```

### 隐蔽模式（原文名称）

```bash
# 尽量减少取消认证帧，并使用安静输出
sudo wifite --num-deauths 1 --quiet
```

> **模式名称说明：** 原文“隐蔽模式”仍包含取消认证帧发送；安静输出只影响终端显示，不能据此推断无线活动不可被检测。恢复会话与重复字典参数的行为同样取决于版本：[维护分支参数](https://github.com/kimocoder/wifite2/blob/master/wifite/args.py)有专门的恢复开关，而重复 `--dict` 并不自动表示合并多个字典。

---

<a id="-quick-reference"></a>
## 📊 速查表

### 常用命令

| 用途 | 命令 |
|---------|---------|
| 基础扫描 | `sudo wifite` |
| 按网络名称选择目标 | `sudo wifite --essid "Name"` |
| 按 MAC 地址选择目标 | `sudo wifite --bssid MAC` |
| 仅 WPA 网络 | `sudo wifite --wpa` |
| 仅 WPS 攻击 | `sudo wifite --wps-only` |
| PMKID 攻击 | `sudo wifite --pmkid` |
| 指定字典 | `sudo wifite --dict wordlist.txt` |
| 跳过密码破解 | `sudo wifite --skip-crack` |

### 攻击类型

| 攻击方式 | 参数 | 目标 |
|--------|------|--------|
| WPA 握手 | `--wpa` | WPA/WPA2-PSK |
| PMKID | `--pmkid` | WPA/WPA2-PSK |
| WPS Pixie-Dust | `--wps --pixie` | 已启用 WPS |
| WPS PIN 暴力破解 | `--wps-only` | 已启用 WPS |
| WEP | `--wep` | WEP（已过时） |

### 常见字典

| 路径 | 说明 |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | 常见密码 |
| `/usr/share/wordlists/fasttrack.txt` | 快速检查 |
| `/usr/share/seclists/Passwords/WiFi-WPA/` | Wi-Fi 专用字典 |

### 捕获文件

```
~/.config/wifite/
├── cracked.txt          # 已破解的密码
├── hs/                  # 握手捕获文件
│   └── handshake_*.cap
└── pmkid/               # PMKID 捕获文件
    └── pmkid_*.22000
```

### 参数速查表

| 参数 | 说明 |
|------|-------------|
| `-i` | 无线接口 |
| `-c` | 信道 |
| `-v` | 详细输出 |
| `--wpa` | 仅 WPA/WPA2 网络 |
| `--wep` | 仅 WEP 网络 |
| `--wps` | 已启用 WPS |
| `--pmkid` | PMKID 攻击 |
| `--dict` | 字典 |
| `--kill` | 终止冲突进程 |
| `--power` | 最低信号强度 |
| `--5ghz` | 5GHz 频段 |
| `--2ghz` | 2.4GHz 频段 |

### 故障排查

```bash
# 找不到接口
sudo airmon-ng check kill
sudo wifite -i wlan0

# 未发现网络
# - 检查网卡是否支持监听模式
# - 尝试不同频段：--5ghz 或 --2ghz
# - 靠近目标

# 未捕获到握手
# - 增加取消认证帧数量：--num-deauths 10
# - 延长等待时间：--wpa-deauth-timeout 60
# - 尝试不同客户端

# 密码破解失败
# - 使用更合适的字典
# - 尝试通过 GPU 使用 hashcat：--hashcat
```

---

## 🔄 Wifite 与手动使用 Aircrack-ng 对比

| 任务 | Wifite | Aircrack-ng 工具套件 |
|------|--------|-------------------|
| 监听模式（Monitor Mode） | 自动 | `airmon-ng start wlan0` |
| 扫描 | 自动 | `airodump-ng wlan0mon` |
| 目标 | 交互选择 | 手动筛选 |
| 取消认证 | 自动 | `aireplay-ng -0 ...` |
| 捕获 | 自动 | `airodump-ng -w ...` |
| 破解 | 自动 | `aircrack-ng -w ...` |
| **操作难度** | **简单** | 手动 |

---

## ⚠️ 法律免责声明

```
⚠️ 警告：仅可在你拥有的网络，或已获得
明确书面许可的网络中进行测试。

✅ 经授权的渗透测试
✅ 经许可的安全审计
✅ 教育用途（实验室环境）

❌ 未经授权访问网络违法（原文警告）
❌ 不得用于恶意目的
```

---

## 📚 参考资源

- [Wifite2 GitHub](https://github.com/derv82/wifite2)
- [Wifite 维基](https://github.com/derv82/wifite2/wiki)
- [Aircrack-ng](https://www.aircrack-ng.org/)

### 相关速查表
- [Aircrack-ng](../../Aircrack-ng/translations/README.zh-CN.md)
- [Hashcat](../../Hashcat/translations/README.zh-CN.md)
- [Linux 命令](../../Linux-Commands/translations/README.zh-CN.md)

---

<p align="center">
  <b>📶 自动化 Wi-Fi 审计！</b><br>
  <i>Wifite - 选择目标，开展合乎授权的安全测试</i>
</p>
