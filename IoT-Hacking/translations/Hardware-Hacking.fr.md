# 🔧 Fiche pratique de sécurité matérielle

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Hardware-Hacking.tr.md) • [🇬🇷 Ελληνικά](Hardware-Hacking.el.md)

---

## 🔌 UART (console série)

### Identifier les broches UART
```
TX (Transmit) - Données émises par l’appareil
RX (Receive) - Données reçues par l’appareil
GND - Masse
VCC - Alimentation (3.3V/5V)
```

> **Précision de câblage :** les tensions du document source sont des exemples, pas une garantie de compatibilité. La tension d’alimentation et les niveaux logiques de l’interface doivent être vérifiés pour l’appareil concerné avant le raccordement.

### Outils
- **Multimètre** — Identifier GND et VCC.
- **Analyseur logique (logic analyzer)** — Repérer TX/RX et déterminer la vitesse de transmission.
- **USB-UART** — FT232, CP2102, CH340.

### Connexion
```bash
# screen sous Linux
screen /dev/ttyUSB0 115200

# minicom sous Linux
minicom -D /dev/ttyUSB0 -b 115200

# picocom
picocom -b 115200 /dev/ttyUSB0
```

### Vitesses de transmission courantes
```
9600, 19200, 38400, 57600, 115200
```

---

## 🔧 JTAG (interface de débogage)

### Broches
```
TDI - Test Data In : entrée des données de test
TDO - Test Data Out : sortie des données de test
TCK - Test Clock : horloge de test
TMS - Test Mode Select : sélection du mode de test
TRST - Test Reset : réinitialisation du test (facultative)
```

### Outils
```bash
# OpenOCD
openocd -f interface/jlink.cfg -f target/stm32f1x.cfg

# JTAGulator
# Détecter automatiquement le brochage JTAG
```

---

## 📟 SPI (mémoire flash)

### Broches
```
MOSI - Master Out Slave In : sortie du contrôleur, entrée du périphérique
MISO - Master In Slave Out : entrée du contrôleur, sortie du périphérique
CLK  - Clock : horloge
CS   - Chip Select : sélection du composant
```

### Extraire le contenu de la mémoire flash
```bash
# flashrom
flashrom -p ch341a_spi -r dump.bin

# Bus Pirate
flashrom -p buspirate_spi:dev=/dev/ttyUSB0 -r dump.bin
```

---

## 📡 I2C (EEPROM)

### Broches
```
SDA - Serial Data : données série
SCL - Serial Clock : horloge série
```

### Lire le contenu de l’EEPROM
```bash
# i2ctools
i2cdetect -y 1
i2cdump -y 1 0x50
```

> **Précision sur l’exemple :** `1` et `0x50` représentent le bus et l’adresse de l’appareil de démonstration. Le sondage d’`i2cdetect` emploie des commandes SMBus et peut perturber certains composants ; ce n’est pas une lecture passive universelle. Voir le [manuel du projet i2c-tools](https://kernel.googlesource.com/pub/scm/utils/i2c-tools/i2c-tools/+/refs/tags/v4.3/tools/i2cdetect.8).

---

## 🛠️ Outils matériels

| Outil | Utilisation |
|------|-----|
| **Bus Pirate** | Analyse et interaction avec plusieurs protocoles |
| **FT232** | Adaptateur USB vers UART |
| **Analyseur logique** | Analyse des signaux |
| **Multimètre** | Mesure de tension et de continuité |
| **JTAGulator** | Détection des broches JTAG |
| **CH341A** | Programmateur de mémoire flash SPI |

---

## 📋 Liste de contrôle de l’analyse matérielle

```markdown
□ Inspecter visuellement les composants et les points de test
□ Identifier les connecteurs de débogage
□ Repérer l’UART avec un multimètre
□ Capturer les signaux avec un analyseur logique
□ Se connecter et obtenir un shell
□ Extraire le contenu de la mémoire flash
□ Analyser le firmware extrait
```

---

**Retour à la rubrique IoT :** [📡 Sécurité des objets connectés](./README.fr.md)
