# 🔧 硬件安全测试速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **译注：** 下方 `3.3V/5V` 是原文中的示例，实际供电和信号电平必须以目标芯片及适配器规格为准；不能假定所有设备兼容这两种电压。部分 CH341A 黑色编程板的信号电平还可能不同于插座供电，参见 [flashrom 官方硬件说明](https://github.com/flashrom/flashrom/blob/main/doc/supported_hw/supported_prog/ch341ab.rst)。OpenOCD 示例中的接口和目标配置，以及 I2C 的总线 `1`、地址 `0x50`，也都依赖具体硬件。

[🇹🇷 Türkçe Sürüm](Hardware-Hacking.tr.md) • [🇬🇷 Ελληνικά](Hardware-Hacking.el.md)

---

## 🔌 UART（串行控制台）

### 识别 UART 引脚
```
TX（Transmit，发送）- 设备的数据输出
RX（Receive，接收）- 设备的数据输入
GND - 接地
VCC - 电源（3.3V/5V）
```

### 工具
- **万用表（Multimeter）** - 识别 GND、VCC
- **逻辑分析仪（Logic Analyzer）** - 查找 TX/RX 和波特率
- **USB-UART** - FT232, CP2102, CH340

### 连接
```bash
# Linux 下的 screen
screen /dev/ttyUSB0 115200

# Linux 下的 minicom
minicom -D /dev/ttyUSB0 -b 115200

# picocom
picocom -b 115200 /dev/ttyUSB0
```

### 常见波特率
```
9600, 19200, 38400, 57600, 115200
```

---

## 🔧 JTAG（调试接口）

### 引脚
```
TDI - 测试数据输入（Test Data In）
TDO - 测试数据输出（Test Data Out）
TCK - 测试时钟（Test Clock）
TMS - 测试模式选择（Test Mode Select）
TRST - 测试复位（可选）
```

### 工具
```bash
# OpenOCD
openocd -f interface/jlink.cfg -f target/stm32f1x.cfg

# JTAGulator
# 自动检测 JTAG 引脚排列
```

---

## 📟 SPI（闪存）

### 引脚
```
MOSI - 主机输出、从机输入（Master Out Slave In）
MISO - 主机输入、从机输出（Master In Slave Out）
CLK  - 时钟
CS   - 片选
```

### 转储闪存
```bash
# flashrom
flashrom -p ch341a_spi -r dump.bin

# Bus Pirate
flashrom -p buspirate_spi:dev=/dev/ttyUSB0 -r dump.bin
```

---

## 📡 I2C（EEPROM，电可擦除可编程只读存储器）

### 引脚
```
SDA - 串行数据
SCL - 串行时钟
```

### 转储 EEPROM
```bash
# i2ctools
i2cdetect -y 1
i2cdump -y 1 0x50
```

---

## 🛠️ 硬件工具

| 工具 | 用途 |
|------|-----|
| **Bus Pirate** | 多协议安全测试 |
| **FT232** | USB 转 UART |
| **逻辑分析仪（Logic Analyzer）** | 信号分析 |
| **万用表（Multimeter）** | 电压与通断测量 |
| **JTAGulator** | JTAG 引脚检测 |
| **CH341A** | SPI 闪存编程器 |

---

## 📋 硬件安全测试检查清单

```markdown
□ 目视检查（芯片、测试点）
□ 识别调试排针
□ 使用万用表查找 UART
□ 捕获信号（逻辑分析仪）
□ 连接并获取 shell
□ 转储闪存
□ 分析提取出的固件
```

---

**返回 IoT 目录：** [📡 IoT 安全测试](./README.zh-CN.md)
