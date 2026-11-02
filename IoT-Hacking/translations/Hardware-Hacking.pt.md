# 🔧 Guia de referência de hacking de hardware

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Hardware-Hacking.tr.md) • [🇬🇷 Ελληνικά](Hardware-Hacking.el.md)

---

## 🔌 UART (consola série)

UART é uma interface de comunicação série assíncrona. A baud rate indica a taxa de símbolos por segundo; os valores comuns abaixo são exemplos, não uma configuração garantida para todos os equipamentos.

### Identificar os pinos UART

> **Precisão elétrica:** os valores `3.3V/5V` do original são exemplos, não uma garantia de compatibilidade. Verifica a tensão e os níveis lógicos do equipamento e do adaptador antes de os ligar; níveis diferentes podem exigir conversão. Ver a [explicação de níveis lógicos da SparkFun](https://learn.sparkfun.com/tutorials/logic-levels/all?print=1).
```
TX (transmissão) — Saída de dados do dispositivo
RX (receção) — Entrada de dados no dispositivo
GND — Massa elétrica
VCC — Alimentação (3.3V/5V)
```

### Ferramentas
- **Multímetro** — Identificar GND e VCC
- **Analisador lógico** — Encontrar TX/RX e a taxa de símbolos (baud rate)
- **USB-UART** — FT232, CP2102, CH340

### Ligação
```bash
# Linux screen
screen /dev/ttyUSB0 115200

# Linux minicom
minicom -D /dev/ttyUSB0 -b 115200

# picocom
picocom -b 115200 /dev/ttyUSB0
```

### Taxas de símbolos (baud rates) comuns
```
9600, 19200, 38400, 57600, 115200
```

---

## 🔧 JTAG (interface de depuração)

### Pinos
```
TDI — Entrada de dados de teste (Test Data In)
TDO — Saída de dados de teste (Test Data Out)
TCK — Relógio de teste (Test Clock)
TMS — Seleção do modo de teste (Test Mode Select)
TRST — Reposição do teste, opcional (Test Reset)
```

### Ferramentas
```bash
# OpenOCD
openocd -f interface/jlink.cfg -f target/stm32f1x.cfg

# JTAGulator
# Detetar automaticamente a disposição dos pinos JTAG
```

---

## 📟 SPI (memória flash)

### Pinos
```
MOSI — Saída do mestre, entrada do periférico (Master Out Slave In)
MISO — Entrada do mestre, saída do periférico (Master In Slave Out)
CLK  — Relógio
CS   — Seleção do circuito integrado (Chip Select)
```

### Obter uma cópia da memória flash
```bash
# flashrom
flashrom -p ch341a_spi -r dump.bin

# Bus Pirate
flashrom -p buspirate_spi:dev=/dev/ttyUSB0 -r dump.bin
```

---

## 📡 I2C (EEPROM)

EEPROM é uma memória não volátil que pode ser apagada e programada eletricamente. SDA transporta dados e SCL transporta o sinal de relógio.

### Pinos
```
SDA — Dados série (Serial Data)
SCL — Relógio série (Serial Clock)
```

### Obter uma cópia da EEPROM
```bash
# i2ctools
i2cdetect -y 1
i2cdump -y 1 0x50
```

---

## 🛠️ Ferramentas de hardware

| Ferramenta | Utilização |
|------|-----|
| **Bus Pirate** | Hacking de vários protocolos |
| **FT232** | Conversão de USB para UART |
| **Analisador lógico** | Análise de sinais |
| **Multímetro** | Tensão, continuidade |
| **JTAGulator** | Deteção de pinos JTAG |
| **CH341A** | Programador de memória flash SPI |

---

## 📋 Lista de verificação de hacking de hardware

```markdown
□ Inspecionar visualmente (circuitos integrados, pontos de teste)
□ Identificar os conectores de depuração
□ Encontrar a interface UART com um multímetro
□ Capturar sinais (analisador lógico)
□ Ligar e obter uma shell (consola de comandos)
□ Obter uma cópia da memória flash
□ Analisar o firmware extraído
```

---

**Voltar a IoT:** [📡 Hacking de IoT](./README.pt.md)
