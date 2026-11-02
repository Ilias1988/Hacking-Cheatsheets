# 🔧 Donanım Korsanlığı Kopya Kağıdı (Hardware Hacking Cheatsheet)

---

## 🔌 UART (Seri Konsol)

### UART Pinlerini Belirleme
```
TX (Transmit) - Cihazdan dışarı giden veri
RX (Receive) - Cihaza gelen veri
GND - Toprak (Ground)
VCC - Güç (3.3V/5V)
```

### Araçlar
- **Multimetre** - GND ve VCC pinlerini tespit etme
- **Mantık Analizörü (Logic Analyzer)** - TX/RX ve baud hızını tespit etme
- **USB-UART Dönüştürücü** - FT232, CP2102, CH340

### Bağlantı
```bash
# Linux screen
screen /dev/ttyUSB0 115200

# Linux minicom
minicom -D /dev/ttyUSB0 -b 115200

# picocom
picocom -b 115200 /dev/ttyUSB0
```

### Yaygın Baud Hızları
```
9600, 19200, 38400, 57600, 115200
```

---

## 🔧 JTAG (Hata Ayıklama Arayüzü)

### Pinler
```
TDI - Test Veri Girişi (Test Data In)
TDO - Test Veri Çıkışı (Test Data Out)
TCK - Test Saati (Test Clock)
TMS - Test Modu Seçimi (Test Mode Select)
TRST - Test Sıfırlama (İsteğe bağlı - Test Reset)
```

### Araçlar
```bash
# OpenOCD
openocd -f interface/jlink.cfg -f target/stm32f1x.cfg

# JTAGulator
# JTAG pin dağılımını otomatik olarak tespit eder
```

---

## 📟 SPI (Flaş Bellek)

### Pinler
```
MOSI - Master Çıkış Slave Giriş (Master Out Slave In)
MISO - Master Giriş Slave Çıkış (Master In Slave Out)
CLK  - Saat Sinyali (Clock)
CS   - Çip Seçimi (Chip Select)
```

### Flaş Belleği Dışa Aktarma (Dump)
```bash
# flashrom
flashrom -p ch341a_spi -r dump.bin

# Bus Pirate
flashrom -p buspirate_spi:dev=/dev/ttyUSB0 -r dump.bin
```

---

## 📡 I2C (EEPROM)

### Pinler
```
SDA - Seri Veri (Serial Data)
SCL - Seri Saat (Serial Clock)
```

### EEPROM Belleğini Dışa Aktarma (Dump)
```bash
# i2ctools
i2cdetect -y 1
i2cdump -y 1 0x50
```

---

## 🛠️ Donanım Araçları

| Araç | Kullanım Amacı |
|------|----------------|
| **Bus Pirate** | Çoklu protokol korsanlığı |
| **FT232** | USB - UART dönüştürücü |
| **Logic Analyzer** | Sinyal ve dalga analizi |
| **Multimetre** | Voltaj, süreklilik (diyot) testi |
| **JTAGulator** | JTAG pinlerinin tespiti |
| **CH341A** | SPI flaş bellek programlayıcı |

---

## 📋 Donanım Korsanlığı Kontrol Listesi

```markdown
□ Görsel inceleme (çipler, test noktaları, etiketler)
□ Hata ayıklama başlıklarını (headers) tespit edin
□ Multimetre ile UART pinlerini (GND, TX, RX, VCC) bulun
□ Sinyalleri yakalayın (mantık analizörü ile)
□ Cihaza bağlanın ve kabuk (shell) erişimi elde edin
□ Flaş bellek içeriğini dışa aktarın (dump)
□ Çıkarılan bellenimi analiz edin
```

---

**IoT'ye Dön:** [📡 IoT Hacking](./README.tr.md)
