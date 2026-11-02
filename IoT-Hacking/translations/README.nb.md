# 📡 Hurtigreferanse for IoT-hacking

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 📑 Innholdsfortegnelse

| Tema | Beskrivelse | Veiledning |
|-------|-------------|-------|
| **Fastvareanalyse** | Uthenting og omvendt utvikling | [📄 Les](./Firmware-Analysis.nb.md) |
| **Maskinvarehacking** | UART, JTAG, SPI, I2C | [📄 Les](./Hardware-Hacking.nb.md) |

---

## 🎯 Angrepsflaten i IoT

> **Begreper:** IoT (Internet of Things) er nettverkstilkoblede fysiske enheter. Fastvare (firmware) er programvaren som styrer enheten. Omvendt utvikling (reverse engineering, RE) undersøker hvordan programvare eller maskinvare er bygget opp. UART, JTAG, SPI og I2C er ulike kommunikasjons- og feilsøkingsgrensesnitt.


```
┌─────────────────────────────────────────────────────────────┐
│                       IoT-ANGREPSFLATE                       │
├─────────────────────────────────────────────────────────────┤
│  📡 TRÅDLØST      →  WiFi, BLE, Zigbee, Z-Wave, LoRa        │
│  🔧 MASKINVARE    →  UART, JTAG, SPI, feilsøkingsporter     │
│  💾 FASTVARE      →  Binwalk, utpakking, RE                 │
│  🌐 NETTVERK      →  Webgrensesnitt, API-er, protokoller    │
│  📱 MOBILAPP      →  Tilhørende apper, BLE-kommunikasjon    │
│  ☁️ SKY           →  API-er, autentisering, datalagring     │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔥 Hurtigreferanse

### Fastvare (firmware)

> **Rettelse:** `unsquashfs` pakker ut en SquashFS-avbildning; det monterer ikke et filsystem. `squashfs-root` er normalt utdatakatalogen, ikke avbildningen som skal leses. Den opprinnelige kommandoen er bevart; den forutsetter at argumentet faktisk er en avbildningsfil. Se [verktøyets man-side](https://manpages.debian.org/testing/squashfs-tools/unsquashfs.1.en.html).

```bash
# Pakk ut fastvaren
binwalk -e firmware.bin

# Finn filsystemer
binwalk firmware.bin | grep -i filesystem

# Monter squashfs (se rettelsen ovenfor)
unsquashfs squashfs-root
```

### Maskinvare
```bash
# UART-tilkobling
screen /dev/ttyUSB0 115200

# Bus Pirate
sudo picocom -b 115200 /dev/ttyUSB0
```

---

## 🛠️ Viktige verktøy

| Kategori | Verktøy |
|----------|-------|
| **Fastvare** | Binwalk, firmware-mod-kit, EMBA |
| **Maskinvare** | Bus Pirate, logikkanalysator, multimeter |
| **Trådløst** | HackRF, RTL-SDR, Ubertooth |
| **Nettverk** | Wireshark, Nmap, Shodan |

---

## 🔍 Shodan-søk etter IoT-enheter

> **Presisering:** Linjene nedenfor er Shodan-søk, selv om originalens blokk er merket `bash`. Et treff på `default password` beviser ikke at en enhet fortsatt bruker et standardpassord.


```bash
# Nettkameraer
webcam country:US

# Industrielle kontrollsystemer
port:502 modbus

# MQTT-meldingsformidlere (brokers)
port:1883

# Standard påloggingsopplysninger
default password
```

---

## 📋 Sjekkliste for penetrasjonstesting av IoT

```markdown
□ Informasjonsinnhenting (Shodan, OSINT)
□ Innhenting og analyse av fastvare
□ Undersøkelse av maskinvare (feilsøkingsporter)
□ Analyse av nettverkstrafikk
□ Testing av webgrensesnitt og API-er
□ Analyse av mobilappen
□ Analyse av trådløse protokoller
□ Testing av skytjenestene i bakenden
```

---

**Tilbake til hovedoversikten:** [🔴 Hacking Cheatsheets](../../README.md)
