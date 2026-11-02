# 🔧 Hurtigreferanse for maskinvarehacking

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Hardware-Hacking.tr.md) • [🇬🇷 Ελληνικά](Hardware-Hacking.el.md)

---

## 🔌 UART (seriell konsoll)

> **Presisering:** `3.3V/5V` og pinneetikettene er eksempler, ikke en universell koblingsplan. Logikknivået må samsvare mellom enheten og adapteren; strømforsyning og signalnivå er ulike forhold. Se [SparkFuns forklaring av serielle signalnivåer](https://learn.sparkfun.com/tutorials/serial-communication/wiring-and-hardware).


### Identifiser UART-pinnene
```
TX (Transmit) - Data ut fra enheten
RX (Receive) - Data inn til enheten
GND - Jord
VCC - Strømforsyning (3.3V/5V)
```

### Verktøy
- **Multimeter** – identifiser GND og VCC
- **Logikkanalysator** – finn TX/RX og baudrate
- **USB-UART** - FT232, CP2102, CH340

### Tilkobling
```bash
# screen på Linux
screen /dev/ttyUSB0 115200

# minicom på Linux
minicom -D /dev/ttyUSB0 -b 115200

# picocom
picocom -b 115200 /dev/ttyUSB0
```

### Vanlige baudrater

> **Begrep:** Baudrate angir symbolhastighet. Både hastighet og øvrige serielle innstillinger må passe enhetens konsoll.

```
9600, 19200, 38400, 57600, 115200
```

---

## 🔧 JTAG (feilsøkingsgrensesnitt)

### Pinner
```
TDI - Test Data In (testdata inn)
TDO - Test Data Out (testdata ut)
TCK - Test Clock (testklokke)
TMS - Test Mode Select (valg av testmodus)
TRST - Test Reset (valgfri tilbakestilling)
```

### Verktøy
```bash
# OpenOCD
openocd -f interface/jlink.cfg -f target/stm32f1x.cfg

# JTAGulator
# Finn JTAG-pinnefordelingen automatisk
```

---

## 📟 SPI (flashminne)

### Pinner
```
MOSI - Master Out Slave In (data til periferien)
MISO - Master In Slave Out (data fra periferien)
CLK  - Klokke
CS   - Valg av brikke (Chip Select)
```

### Les ut flashminnet
```bash
# flashrom
flashrom -p ch341a_spi -r dump.bin

# Bus Pirate
flashrom -p buspirate_spi:dev=/dev/ttyUSB0 -r dump.bin
```

---

## 📡 I2C (EEPROM)

### Pinner
```
SDA - Serial Data (serielle data)
SCL - Serial Clock (seriell klokke)
```

### Les ut EEPROM
```bash
# i2ctools
i2cdetect -y 1
i2cdump -y 1 0x50
```

---

## 🛠️ Maskinvareverktøy

| Verktøy | Bruk |
|------|-----|
| **Bus Pirate** | Hacking med flere protokoller |
| **FT232** | USB til UART |
| **Logikkanalysator** | Signalanalyse |
| **Multimeter** | Spenning og kontinuitet |
| **JTAGulator** | Identifikasjon av JTAG-pinner |
| **CH341A** | Programmerer for SPI-flash |

---

## 📋 Sjekkliste for maskinvarehacking

```markdown
□ Visuell undersøkelse (brikker og testpunkter)
□ Identifiser kontakter for feilsøking
□ Finn UART med et multimeter
□ Fang opp signaler med en logikkanalysator
□ Koble til og få et shell
□ Les ut flashminnet
□ Analyser fastvaren som er hentet ut
```

---

**Tilbake til IoT:** [📡 IoT-hacking](./README.nb.md)
