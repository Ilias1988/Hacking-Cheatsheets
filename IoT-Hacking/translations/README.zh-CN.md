# 📡 IoT（物联网）安全测试速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **译注：** IoT 指 Internet of Things（物联网），攻击面涵盖设备、通信、应用和云端。下方 `unsquashfs squashfs-root` 保留原文，但 `unsquashfs` 用于从 SquashFS 镜像提取文件，并非挂载；`squashfs-root` 通常是默认输出目录名，若它是目录，就不能当作输入镜像。参见 [SquashFS 官方工具说明](https://github.com/plougher/squashfs-tools/blob/master/Documentation/4.7.4/USAGE-UNSQUASHFS.md)。

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
██╗ ██████╗ ████████╗    ██╗  ██╗ █████╗  ██████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██║██╔═══██╗╚══██╔══╝    ██║  ██║██╔══██╗██╔════╝██║ ██╔╝██║████╗  ██║██╔════╝ 
██║██║   ██║   ██║       ███████║███████║██║     █████╔╝ ██║██╔██╗ ██║██║  ███╗
██║██║   ██║   ██║       ██╔══██║██╔══██║██║     ██╔═██╗ ██║██║╚██╗██║██║   ██║
██║╚██████╔╝   ██║       ██║  ██║██║  ██║╚██████╗██║  ██╗██║██║ ╚████║╚██████╔╝
╚═╝ ╚═════╝    ╚═╝       ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📑 目录

| 主题 | 说明 | 指南 |
|-------|-------------|-------|
| **固件分析** | 提取与逆向工程 | [📄 查看](./Firmware-Analysis.zh-CN.md) |
| **硬件安全测试** | UART, JTAG, SPI, I2C | [📄 查看](./Hardware-Hacking.zh-CN.md) |

---

## 🎯 IoT 攻击面

```
┌─────────────────────────────────────────────────────────────┐
│                         IoT 攻击面                          │
├─────────────────────────────────────────────────────────────┤
│  📡 无线通信 → WiFi、BLE、Zigbee、Z-Wave、LoRa              │
│  🔧 硬件     → UART、JTAG、SPI、调试端口                    │
│  💾 固件     → Binwalk、提取、逆向工程（RE）                │
│  🌐 网络     → Web 界面、API、协议                          │
│  📱 移动应用 → 配套应用、BLE 通信                           │
│  ☁️ 云端     → API、身份验证、数据存储                      │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔥 速查

### 固件
```bash
# 提取固件
binwalk -e firmware.bin

# 查找文件系统
binwalk firmware.bin | grep -i filesystem

# 挂载 squashfs（原文注释，实际操作是解包）
unsquashfs squashfs-root
```

### 硬件
```bash
# UART 连接
screen /dev/ttyUSB0 115200

# Bus Pirate
sudo picocom -b 115200 /dev/ttyUSB0
```

---

## 🛠️ 必备工具

| 类别 | 工具 |
|----------|-------|
| **固件** | Binwalk, firmware-mod-kit, EMBA |
| **硬件** | Bus Pirate, 逻辑分析仪（Logic Analyzer）, 万用表（Multimeter） |
| **无线通信** | HackRF, RTL-SDR, Ubertooth |
| **网络** | Wireshark, Nmap, Shodan |

---

## 🔍 Shodan IoT 搜索

```bash
# 网络摄像头
webcam country:US

# 工业控制
port:502 modbus

# MQTT 消息代理（broker）
port:1883

# 默认凭据
default password
```

---

## 📋 IoT 渗透测试检查清单

```markdown
□ 信息收集（Shodan、OSINT）
□ 固件获取与分析
□ 硬件检查（调试端口）
□ 网络流量分析
□ Web/API 测试
□ 移动应用分析
□ 无线协议分析
□ 云端后端测试
```

---

**返回主目录：** [🔴 黑客技术速查表](../../README.md)
