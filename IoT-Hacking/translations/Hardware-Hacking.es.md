# 🔧 Guía rápida de pruebas de seguridad de hardware

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Hardware-Hacking.tr.md) • [🇬🇷 Ελληνικά](Hardware-Hacking.el.md)

> **Aclaración técnica:** Las tensiones `3.3V/5V` son ejemplos del original, no valores universales. Deben verificarse la alimentación y los niveles lógicos del dispositivo y del adaptador antes de conectarlos. Las etiquetas de pines se conservan; MOSI y MISO usan la terminología histórica maestro/esclavo.


---

## 🔌 UART (consola serie)

### Identificar los pines UART
```
TX (Transmit): salida de datos del dispositivo
RX (Receive): entrada de datos al dispositivo
GND: masa
VCC: alimentación (3.3V/5V)
```

### Herramientas
- **Multímetro** - Identificar GND y VCC
- **Analizador lógico** - Encontrar TX/RX y la velocidad en baudios
- **USB-UART** - FT232, CP2102, CH340

### Conexión
```bash
# Linux screen
screen /dev/ttyUSB0 115200

# Linux minicom
minicom -D /dev/ttyUSB0 -b 115200

# picocom
picocom -b 115200 /dev/ttyUSB0
```

### Velocidades habituales en baudios
```
9600, 19200, 38400, 57600, 115200
```

---

## 🔧 JTAG (interfaz de depuración)

### Pines
```
TDI: entrada de datos de prueba
TDO: salida de datos de prueba
TCK: reloj de prueba
TMS: selección del modo de prueba
TRST: reinicio de prueba (opcional)
```

### Herramientas
```bash
# OpenOCD
openocd -f interface/jlink.cfg -f target/stm32f1x.cfg

# JTAGulator
# Detectar automáticamente la disposición de pines JTAG
```

---

## 📟 SPI (memoria flash)

### Pines
```
MOSI: salida del maestro, entrada del esclavo
MISO: entrada del maestro, salida del esclavo
CLK: reloj
CS: selección del chip
```

### Obtener un volcado de la memoria flash
```bash
# flashrom
flashrom -p ch341a_spi -r dump.bin

# Bus Pirate
flashrom -p buspirate_spi:dev=/dev/ttyUSB0 -r dump.bin
```

---

## 📡 I2C (EEPROM)

### Pines
```
SDA: datos serie
SCL: reloj serie
```

### Obtener un volcado de la EEPROM
```bash
# i2ctools
i2cdetect -y 1
i2cdump -y 1 0x50
```

---

## 🛠️ Herramientas de hardware

| Herramienta | Uso |
|------|-----|
| **Bus Pirate** | Pruebas de seguridad de varios protocolos |
| **FT232** | Conversión de USB a UART |
| **Analizador lógico** | Análisis de señales |
| **Multímetro** | Tensión y continuidad |
| **JTAGulator** | Detección de pines JTAG |
| **CH341A** | Programador de memoria flash SPI |

---

## 📋 Lista de comprobación de pruebas de hardware

```markdown
□ Inspección visual (chips y puntos de prueba)
□ Identificar los conectores de depuración
□ Encontrar UART con un multímetro
□ Capturar señales (analizador lógico)
□ Conectarse y obtener una shell
□ Obtener un volcado de la memoria flash
□ Analizar el firmware extraído
```

---

**Volver a IoT:** [📡 Pruebas de seguridad de IoT](./README.es.md)
