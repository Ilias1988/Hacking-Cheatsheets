# 📡 Guia de referência de hacking de IoT

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 📑 Índice

| Tema | Descrição | Guia |
|-------|-------------|-------|
| **Análise de firmware** | Extração, engenharia inversa | [📄 Ver](./Firmware-Analysis.pt.md) |
| **Hacking de hardware** | UART, JTAG, SPI, I2C | [📄 Ver](./Hardware-Hacking.pt.md) |

---

## 🎯 Superfície de ataque de IoT

IoT (Internet of Things) significa Internet das Coisas: dispositivos com componentes físicos, software e conectividade. Firmware é o software integrado no equipamento; RE (reverse engineering) significa engenharia inversa. OTA (over-the-air) refere-se a atualizações recebidas por uma ligação sem fios ou de rede.

```
┌─────────────────────────────────────────────────────────────┐
│                    SUPERFÍCIE DE ATAQUE DE IoT               │
├─────────────────────────────────────────────────────────────┤
│  📡 SEM FIOS      →  WiFi, BLE, Zigbee, Z-Wave, LoRa        │
│  🔧 HARDWARE      →  UART, JTAG, SPI, portas de depuração   │
│  💾 FIRMWARE      →  Binwalk, extração, engenharia inversa  │
│  🌐 REDE          →  Interface web, APIs, protocolos        │
│  📱 APP MÓVEL     →  Apps associadas, comunicação BLE       │
│  ☁️ CLOUD         →  APIs, autenticação, guardar dados      │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔥 Referência rápida

### Firmware

> **Precisão do original:** `unsquashfs` extrai uma imagem SquashFS; não monta um diretório. O argumento deve identificar a imagem do sistema de ficheiros. Se `squashfs-root` já for o diretório extraído, o comando original com esse argumento não se aplica. Ver o [manual de unsquashfs distribuído pelo Debian](https://manpages.debian.org/testing/squashfs-tools/unsquashfs.1.en.html).
```bash
# Extrair o firmware
binwalk -e firmware.bin

# Encontrar sistemas de ficheiros
binwalk firmware.bin | grep -i filesystem

# Montar o SquashFS
unsquashfs squashfs-root
```

### Hardware
```bash
# Ligação UART
screen /dev/ttyUSB0 115200

# Bus Pirate
sudo picocom -b 115200 /dev/ttyUSB0
```

---

## 🛠️ Ferramentas essenciais

| Categoria | Ferramentas |
|----------|-------|
| **Firmware** | Binwalk, firmware-mod-kit, EMBA |
| **Hardware** | Bus Pirate, analisador lógico, multímetro |
| **Sem fios** | HackRF, RTL-SDR, Ubertooth |
| **Rede** | Wireshark, Nmap, Shodan |

---

## 🔍 Pesquisas de IoT no Shodan

```bash
# Câmaras web
webcam country:US

# Controlo industrial
port:502 modbus

# Brokers MQTT (intermediários de mensagens)
port:1883

# Credenciais predefinidas
default password
```

---

## 📋 Lista de verificação de testes de intrusão em IoT

```markdown
□ Recolher informações (Shodan, OSINT)
□ Obter e analisar o firmware
□ Inspecionar o hardware (portas de depuração)
□ Analisar o tráfego de rede
□ Testar interfaces web e APIs
□ Analisar a aplicação móvel
□ Analisar protocolos sem fios
□ Testar os serviços de suporte na cloud (backend)
```

---

**Voltar ao índice principal:** [🔴 Hacking Cheatsheets](../../README.md)
