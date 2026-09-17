# 🔧 Hardware Hacking Cheatsheet

---

## 🔌 UART (Serial Console)

### Identify UART Pins
```
TX (Transmit) - Data out from device
RX (Receive) - Data in to device
GND - Ground
VCC - Power (3.3V/5V)
```

### Tools
- **Multimeter** - Identify GND, VCC
- **Logic Analyzer** - Find TX/RX, baud rate
- **USB-UART** - FT232, CP2102, CH340

### Connection
```bash
# Linux screen
screen /dev/ttyUSB0 115200

# Linux minicom
minicom -D /dev/ttyUSB0 -b 115200

# picocom
picocom -b 115200 /dev/ttyUSB0
```

### Common Baud Rates
```
9600, 19200, 38400, 57600, 115200
```

---

## 🔧 JTAG (Debug Interface)

### Pins
```
TDI - Test Data In
TDO - Test Data Out
TCK - Test Clock
TMS - Test Mode Select
TRST - Test Reset (optional)
```

### Tools
```bash
# OpenOCD
openocd -f interface/jlink.cfg -f target/stm32f1x.cfg

# JTAGulator
# Auto-detect JTAG pinout
```

---

## 📟 SPI (Flash Memory)

### Pins
```
MOSI - Master Out Slave In
MISO - Master In Slave Out
CLK  - Clock
CS   - Chip Select
```

### Dump Flash
```bash
# flashrom
flashrom -p ch341a_spi -r dump.bin

# Bus Pirate
flashrom -p buspirate_spi:dev=/dev/ttyUSB0 -r dump.bin
```

---

## 📡 I2C (EEPROM)

### Pins
```
SDA - Serial Data
SCL - Serial Clock
```

### Dump EEPROM
```bash
# i2ctools
i2cdetect -y 1
i2cdump -y 1 0x50
```

---

## 🛠️ Hardware Tools

| Tool | Use |
|------|-----|
| **Bus Pirate** | Multi-protocol hacking |
| **FT232** | USB to UART |
| **Logic Analyzer** | Signal analysis |
| **Multimeter** | Voltage, continuity |
| **JTAGulator** | JTAG pin detection |
| **CH341A** | SPI flash programmer |

---

## 📋 Hardware Hacking Checklist

```markdown
□ Visual inspection (chips, test points)
□ Identify debug headers
□ Find UART with multimeter
□ Capture signals (logic analyzer)
□ Connect and get shell
□ Dump flash memory
□ Analyze extracted firmware
```

---

**Back to IoT:** [📡 IoT Hacking](./README.md)
