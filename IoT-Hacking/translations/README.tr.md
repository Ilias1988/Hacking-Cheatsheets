# 📡 IoT Hacking Kopya Kağıdı (IoT Hacking Cheatsheet)

```
██╗ ██████╗ ████████╗    ██╗  ██╗ █████╗  ██████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██║██╔═══██╗╚══██╔══╝    ██║  ██║██╔══██╗██╔════╝██║ ██╔╝██║████╗  ██║██╔════╝ 
██║██║   ██║   ██║       ███████║███████║██║     █████╔╝ ██║██╔██╗ ██║██║  ███╗
██║██║   ██║   ██║       ██╔══██║██╔══██║██║     ██╔═██╗ ██║██║╚██╗██║██║   ██║
██║╚██████╔╝   ██║       ██║  ██║██║  ██║╚██████╗██║  ██╗██║██║ ╚████║╚██████╔╝
╚═╝ ╚═════╝    ╚═╝       ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📑 İçindekiler

| Konu | Açıklama | Kılavuz |
|------|----------|---------|
| **Bellenim Analizi (Firmware Analysis)** | Çıkarma, tersine mühendislik | [📄 Görüntüle](./Firmware-Analysis.tr.md) |
| **Donanım Korsanlığı (Hardware Hacking)** | UART, JTAG, SPI, I2C | [📄 Görüntüle](./Hardware-Hacking.tr.md) |

---

## 🎯 IoT Saldırı Yüzeyi

```
┌─────────────────────────────────────────────────────────────┐
│                    IoT SALDIRI YÜZEYİ                       │
├─────────────────────────────────────────────────────────────┤
│  📡 KABLOSUZ      →  WiFi, BLE, Zigbee, Z-Wave, LoRa        │
│  🔧 DONANIM       →  UART, JTAG, SPI, hata ayıklama portları│
│  💾 BELLENİM      →  Binwalk, dosya çıkarma, RE             │
│  🌐 AĞ            →  Web arayüzü, API'ler, protokoller      │
│  📱 MOBİL UYG     →  Eşlikçi uygulamalar, BLE iletişimi     │
│  ☁️ BULUT         →  API'ler, kimlik doğrulama, veri depolama│
└─────────────────────────────────────────────────────────────┘
```

---

## 🔥 Hızlı Başvuru

### Bellenim (Firmware)
```bash
# Bellenimi dışa aktarma (extract)
binwalk -e firmware.bin

# Dosya sistemlerini bulma
binwalk firmware.bin | grep -i filesystem

# squashfs bağlama/açma
unsquashfs squashfs-root
```

### Donanım (Hardware)
```bash
# UART bağlantısı
screen /dev/ttyUSB0 115200

# Bus Pirate
sudo picocom -b 115200 /dev/ttyUSB0
```

---

## 🛠️ Temel Araçlar

| Kategori | Araçlar |
|----------|---------|
| **Bellenim (Firmware)** | Binwalk, firmware-mod-kit, EMBA |
| **Donanım (Hardware)** | Bus Pirate, Logic Analyzer, Multimetre |
| **Kablosuz (Wireless)** | HackRF, RTL-SDR, Ubertooth |
| **Ağ (Network)** | Wireshark, Nmap, Shodan |

---

## 🔍 Shodan IoT Aramaları

```bash
# Web kameraları
webcam country:US

# Endüstriyel kontrol sistemleri
port:502 modbus

# MQTT aracıları (brokers)
port:1883

# Varsayılan kimlik bilgileri
default password
```

---

## 📋 IoT Sızma Testi Kontrol Listesi

```markdown
□ Bilgi toplama (Shodan, OSINT)
□ Bellenim elde etme ve analiz etme
□ Donanım incelemesi (hata ayıklama portları)
□ Ağ trafiği analizi
□ Web/API güvenlik testleri
□ Mobil uygulama analizi
□ Kablosuz protokol analizi
□ Bulut arka uç güvenlik testi
```

---

**Ana Sayfaya Dön:** [🔴 Hacking Kopya Kağıtları](../../README.tr.md)
