# 🔧 Scheda di riferimento per l'hacking hardware

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Hardware-Hacking.md) • [🇹🇷 Türkçe Sürüm](Hardware-Hacking.tr.md) • [🇬🇷 Ελληνικά](Hardware-Hacking.el.md)

---

## 🔌 UART (console seriale)

### Identifica i pin UART
```
TX (Transmit) - Dati in uscita dal dispositivo
RX (Receive) - Dati in ingresso al dispositivo
GND - Massa
VCC - Alimentazione (3.3V/5V)
```

> **Nota sulle tensioni:** `3.3V/5V` riprende l’originale, ma non vale per tutti i dispositivi. Verifica la tensione di alimentazione e i livelli logici del dispositivo e dell’adattatore; non presumere che coincidano. Il pin VCC non va collegato automaticamente a un dispositivo già alimentato.

### Strumenti
- **Multimetro** - Identifica GND e VCC
- **Analizzatore logico** - Individua TX/RX e la velocità in baud
- **USB-UART** - FT232, CP2102, CH340

### Connessione
```bash
# screen su Linux
screen /dev/ttyUSB0 115200

# minicom su Linux
minicom -D /dev/ttyUSB0 -b 115200

# picocom
picocom -b 115200 /dev/ttyUSB0
```

### Velocità in baud comuni
```
9600, 19200, 38400, 57600, 115200
```

---

## 🔧 JTAG (interfaccia di debug)

### Pin
```
TDI - Test Data In (ingresso dei dati di test)
TDO - Test Data Out (uscita dei dati di test)
TCK - Test Clock (clock di test)
TMS - Test Mode Select (selezione della modalità di test)
TRST - Test Reset (reset di test, opzionale)
```

### Strumenti
```bash
# OpenOCD
openocd -f interface/jlink.cfg -f target/stm32f1x.cfg

# JTAGulator
# Rilevamento automatico della disposizione dei pin JTAG
```

---

## 📟 SPI (memoria flash)

### Pin
```
MOSI - Master Out Slave In (uscita del master, ingresso dello slave)
MISO - Master In Slave Out (ingresso del master, uscita dello slave)
CLK  - Clock
CS   - Chip Select (selezione del chip)
```

### Estrai il contenuto della memoria flash
```bash
# flashrom
flashrom -p ch341a_spi -r dump.bin

# Bus Pirate
flashrom -p buspirate_spi:dev=/dev/ttyUSB0 -r dump.bin
```

> **Nota sull’adattatore:** la sigla CH341A non garantisce da sola livelli logici adatti alla flash. Alcune schede espongono segnali a 5 V anche quando l’alimentazione della flash è a 3,3 V; verifica il modello e il chip. Vedi le [indicazioni del progetto Libreboot sulle connessioni SPI](https://libreboot.org/docs/install/spi.html).

---

## 📡 I2C (EEPROM)

### Pin
```
SDA - Serial Data (dati seriali)
SCL - Serial Clock (clock seriale)
```

### Estrai il contenuto della EEPROM
```bash
# i2ctools
i2cdetect -y 1
i2cdump -y 1 0x50
```

---

## 🛠️ Strumenti hardware

| Strumento | Utilizzo |
|------|-----|
| **Bus Pirate** | Interazione con più protocolli |
| **FT232** | Conversione da USB a UART |
| **Analizzatore logico** | Analisi dei segnali |
| **Multimetro** | Tensione e continuità |
| **JTAGulator** | Rilevamento dei pin JTAG |
| **CH341A** | Programmatore di memorie flash SPI |

---

## 📋 Checklist per l'hacking hardware

```markdown
□ Ispezione visiva (chip, punti di test)
□ Identifica i connettori di debug
□ Individua l'UART con il multimetro
□ Acquisisci i segnali (analizzatore logico)
□ Collegati e ottieni una shell
□ Estrai il contenuto della memoria flash
□ Analizza il firmware estratto
```

---

**Torna a IoT:** [📡 Hacking IoT](./README.it.md)
