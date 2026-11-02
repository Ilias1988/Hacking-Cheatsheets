# 📡 Fiche pratique de sécurité des objets connectés (IoT)

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 📑 Table des matières

| Sujet | Description | Guide |
|-------|-------------|-------|
| **Analyse de firmware** | Extraction et rétro-ingénierie | [📄 Consulter](./Firmware-Analysis.fr.md) |
| **Sécurité matérielle** | UART, JTAG, SPI, I2C | [📄 Consulter](./Hardware-Hacking.fr.md) |

---

## 🎯 Surface d’attaque des objets connectés

```
┌─────────────────────────────────────────────────────────────┐
│                    IoT ATTACK SURFACE                        │
├─────────────────────────────────────────────────────────────┤
│  📡 WIRELESS      →  WiFi, BLE, Zigbee, Z-Wave, LoRa        │
│  🔧 HARDWARE      →  UART, JTAG, SPI, debug ports           │
│  💾 FIRMWARE      →  Binwalk, extraction, RE                │
│  🌐 NETWORK       →  Web interface, APIs, protocols         │
│  📱 MOBILE APP    →  Companion apps, BLE communication      │
│  ☁️ CLOUD         →  APIs, authentication, data storage     │
└─────────────────────────────────────────────────────────────┘
```

Le schéma conserve ses libellés d’origine. Il couvre le sans-fil, le matériel, le firmware, le réseau, l’application mobile associée et les services cloud.

---

## 🔥 Référence rapide

### Firmware
```bash
# Extraire le firmware
binwalk -e firmware.bin

# Rechercher les systèmes de fichiers
binwalk firmware.bin | grep -i filesystem

# Extraire SquashFS (libellé « montage » dans le document source)
unsquashfs squashfs-root
```

> **Correction du document source :** `unsquashfs` extrait une image sans la monter. L’argument `squashfs-root` est normalement le répertoire de sortie ; s’il désigne déjà un répertoire, cet exemple ne fournit pas une image valide. La commande originale est conservée. Voir le [guide officiel d’Unsquashfs](https://github.com/plougher/squashfs-tools/blob/master/Documentation/4.7.4/USAGE-UNSQUASHFS.md).

### Matériel
```bash
# Connexion UART
screen /dev/ttyUSB0 115200

# Bus Pirate
sudo picocom -b 115200 /dev/ttyUSB0
```

---

## 🛠️ Outils essentiels

| Catégorie | Outils |
|----------|-------|
| **Firmware** | Binwalk, firmware-mod-kit, EMBA |
| **Matériel** | Bus Pirate, analyseur logique, multimètre |
| **Sans-fil** | HackRF, RTL-SDR, Ubertooth |
| **Réseau** | Wireshark, Nmap, Shodan |

---

## 🔍 Recherches IoT avec Shodan

```bash
# Caméras web
webcam country:US

# Systèmes de contrôle industriels
port:502 modbus

# Brokers MQTT
port:1883

# Identifiants par défaut
default password
```

---

## 📋 Liste de contrôle du test d’intrusion IoT

```markdown
□ Collecter des informations (Shodan, OSINT)
□ Acquérir et analyser le firmware
□ Inspecter le matériel et les ports de débogage
□ Analyser le trafic réseau
□ Tester l’interface web et les API
□ Analyser l’application mobile
□ Analyser les protocoles sans fil
□ Tester les services cloud côté serveur (backend)
```

---

**Retour à l’accueil :** [🔴 Hacking Cheatsheets](../../README.md)
