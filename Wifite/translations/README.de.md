# 📶 Wifite – Cheatsheet für automatisierte WLAN-Prüfungen

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗    ██╗██╗███████╗██╗████████╗███████╗
  ██║    ██║██║██╔════╝██║╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║█████╗  ██║   ██║   █████╗  
  ██║███╗██║██║██╔══╝  ██║   ██║   ██╔══╝  
  ╚███╔███╔╝██║██║     ██║   ██║   ███████╗
   ╚══╝╚══╝ ╚═╝╚═╝     ╚═╝   ╚═╝   ╚══════╝
         Werkzeug für automatisierte WLAN-Prüfungen
```

<p align="center">
  <img src="https://img.shields.io/badge/Wifite-Automated_WiFi-blue?style=for-the-badge" alt="Wifite">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Python-yellow?style=for-the-badge" alt="Python">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Wifite?](#-was-ist-wifite)
- [Installation](#-installation)
- [Grundlegende Verwendung](#-grundlegende-verwendung)
- [Zielauswahl](#-zielauswahl)
- [Angriffsoptionen](#-angriffsoptionen)
- [WPS-Angriffe](#-wps-angriffe)
- [PMKID-Angriffe](#-pmkid-angriffe)
- [Erweiterte Optionen](#-erweiterte-optionen)
- [Cracking-Optionen](#-cracking-optionen)
- [Beispiele](#-beispiele)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist Wifite?

**Wifite** (Wifite2) ist ein Werkzeug für automatisierte WLAN-Sicherheitsprüfungen. ESSID bezeichnet den Netzwerknamen, BSSID die MAC-Adresse des Access Points. Beim Cracking werden Passwortkandidaten gegen erfasste Prüfdaten getestet. Wifite bietet:

- 🤖 **Automatisierte Angriffe** – Befehle nicht einzeln eingeben müssen
- 🎯 **Mehrere Ziele** – Mehrere Netzwerke prüfen
- 🔓 **WPA/WPA2/WEP/WPS** – Alle Verschlüsselungsarten
- 💾 **Fortschritt speichern** – Unterbrochene Angriffe fortsetzen
- 🔄 **PMKID-Erfassung** – Moderne Angriffe ohne verbundenen Client

### Wifite und Wifite2 im Vergleich

| Merkmal | Wifite (Original) | Wifite2 |
| --------- | ------------------- | --------- |
| Gepflegt | Nein | Ja |
| Python | Python 2 | Python 3 |
| PMKID | Nein | Ja |
| Code | Unübersichtlich | Sauber neu geschrieben |
| Verwendung | Nicht verwenden | **Empfohlen** |

### Funktionen

| Merkmal | Beschreibung |
| --------- | ------------- |
| **Automatisiert** | WLAN-Prüfung mit wenigen Eingaben |
| **Gezielte Auswahl** | Ziele nach Signalstärke auswählen |
| **Alle Angriffe** | WPA, WEP, WPS, PMKID |
| **Cracking** | Verwendet Hashcat/aircrack |
| **Fortsetzbar** | Unterbrochene Sitzungen fortsetzen |

---

## 🚀 Installation

### Kali Linux (vorinstalliert)

```bash
# In Kali bereits installiert
wifite --help

# Auf die neueste Version aktualisieren
sudo apt update && sudo apt install wifite
```

### Wifite2 von GitHub installieren

```bash
# Repository klonen
git clone https://github.com/derv82/wifite2.git
cd wifite2

# Direkt starten
sudo python3 wifite.py

# Alternativ installieren
sudo python3 setup.py install
```

### Abhängigkeiten

```bash
# Erforderlich
sudo apt install aircrack-ng

# Für den vollen Funktionsumfang empfohlen
sudo apt install reaver bully pixiewps hashcat hcxdumptool hcxtools
```

### Installation prüfen

```bash
wifite --help
wifite --version
```

---

## 💻 Grundlegende Verwendung

### Schnellstart

```bash
# Wifite starten (erkennt die Schnittstelle automatisch)
sudo wifite

# Schnittstelle angeben
sudo wifite -i wlan0
```

### Grundlegender Ablauf

```
1. Wifite starten
2. Warten, bis Netzwerke angezeigt werden
3. Mit Ctrl+C die Netzwerksuche beenden
4. Ziel oder Ziele anhand ihrer Nummer auswählen
5. Wifite führt die Angriffe automatisch aus
6. Erfasste Handshakes knacken
```

### Interaktiver Modus

```bash
# Wifite starten
sudo wifite

# Die Ausgabe zeigt:
   NUM  ESSID              ENCR  POWER  WPS  CLIENT
   ---  -----------------  ----  -----  ---  ------
    1   HomeNetwork        WPA2   -45   Yes     2
    2   Office_WiFi        WPA2   -52   No      0
    3   Free_Hotspot       OPN    -60   No      5

# Zielnummern eingeben oder mit Ctrl+C alle sichtbaren Ziele auswählen
```

---

## 🎯 Zielauswahl

### Nach Nummer auswählen

```bash
# Nach der Suche Ziele anhand ihrer Nummer auswählen
# Beispiel: Ziele 1 und 3 auswählen
1, 3

# Einen Bereich auswählen
1-5

# Alle auswählen
all
```

### Nach ESSID filtern

```bash
# Einen bestimmten Netzwerknamen auswählen
sudo wifite --essid "TargetNetwork"

# Mehrere ESSIDs
sudo wifite --essid "Network1" --essid "Network2"
```

### Nach BSSID filtern

```bash
# Eine bestimmte MAC-Adresse auswählen
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Nach Verschlüsselung filtern

```bash
# Nur WPA-Netzwerke
sudo wifite --wpa

# Nur WEP-Netzwerke
sudo wifite --wep

# Nur Netzwerke mit aktiviertem WPS
sudo wifite --wps
```

### Nach Kanal filtern

```bash
# Bestimmter Kanal
sudo wifite -c 6

# Nur 5 GHz
sudo wifite --5ghz

# Nur 2,4 GHz
sudo wifite --2ghz
```

### Nach Signalstärke filtern

```bash
# Mindestpegel (dBm; näher an 0 bedeutet stärker)
sudo wifite --power -50
```

---

<a name="-angriffsoptionen"></a>

## ⚔️ Angriffsoptionen

### WPA-/WPA2-Optionen

```bash
# Deauthentifizierungs-Timeout setzen
sudo wifite --wpa-deauth-timeout 30

# Anzahl zu sendender Deauthentifizierungen
sudo wifite --num-deauths 5

# Handshake-Erfassung überspringen, nur PMKID
sudo wifite --pmkid
```

### Deauthentifizierungsoptionen

```bash
# Anzahl der Deauthentifizierungspakete
sudo wifite --num-deauths 10

# Fortlaufende Deauthentifizierung
sudo wifite --num-deauths 0
```

### Angriffsreihenfolge

```bash
# WPA-Angriffe überspringen
sudo wifite --no-wpa

# WEP-Angriffe überspringen
sudo wifite --no-wep

# WPS-Angriffe überspringen
sudo wifite --no-wps

# PMKID-Angriffe überspringen
sudo wifite --no-pmkid
```

### NetworkManager beenden

```bash
# Störende Prozesse automatisch beenden
sudo wifite --kill

# Prozesse nicht beenden
sudo wifite --no-kill
```

---

## 🔓 WPS-Angriffe

### WPS aktivieren

```bash
# Nur Netzwerke mit aktiviertem WPS angreifen
sudo wifite --wps

# Nur WPS-Angriffe
sudo wifite --wps-only
```

### WPS-Werkzeuge

```bash
# Reaver verwenden (Standard)
sudo wifite --wps --reaver

# Bully verwenden
sudo wifite --wps --bully
```

### Pixie-Dust-Angriff

```bash
# Pixie Dust aktivieren (Offline-WPS-Angriff)
sudo wifite --wps --pixie

# Pixie-Dust-Timeout
sudo wifite --wps --pixie-timeout 300
```

### WPS-Optionen

```bash
# WPS-Timeout je PIN-Versuch
sudo wifite --wps-time 120

# Maximale Anzahl von WPS-Wiederholungen
sudo wifite --wps-fails 10

# Gesamter WPS-Timeout
sudo wifite --wps-timeout 300
```

---

## 🔐 PMKID-Angriffe

### Was ist PMKID?

Ein PMKID-Angriff (Pairwise Master Key Identifier, Kennung eines paarweisen Hauptschlüssels):
- ✅ Erfasst den PMK direkt vom Access Point (AP)
- ✅ Kein verbundener Client erforderlich
- ✅ Keine Deauthentifizierung erforderlich
- ✅ Funktioniert bei den meisten WPA2-Netzwerken

### PMKID-Optionen

```bash
# Nur PMKID-Angriffe (kein Handshake)
sudo wifite --pmkid

# PMKID-Timeout
sudo wifite --pmkid-timeout 120
```

### Funktionsweise von PMKID

```
1. Eine Verbindungsanfrage an den AP senden
2. Der AP antwortet mit einer PMKID in einem EAPOL-Frame
3. PMKID erfassen
4. Offline mit Hashcat knacken
```

---

<a name="-erweiterte-optionen"></a>

## ⚙️ Erweiterte Optionen

### Schnittstellenoptionen

```bash
# Schnittstelle angeben
sudo wifite -i wlan0

# Monitor-Schnittstelle angeben
sudo wifite -i wlan0mon
```

### Ausgabeoptionen

```bash
# Ausführliche Ausgabe
sudo wifite -v
sudo wifite --verbose

# Sehr ausführliche Ausgabe
sudo wifite -vv

# Normale Ausgabe unterdrücken
sudo wifite --quiet
```

### Zeitoptionen

```bash
# Suchdauer in Sekunden
sudo wifite --scan-time 30

# Gesamter Timeout pro Ziel
sudo wifite --attack-timeout 300
```

### Ermittelte Passwörter

```bash
# Ermittelte Passwörter werden hier gespeichert:
# ~/.config/wifite/cracked.txt

# Netzwerke mit ermitteltem Passwort anzeigen
cat ~/.config/wifite/cracked.txt

# Alternativ eine eigene Datei angeben
sudo wifite --cracked cracked_passwords.txt
```

### Optionen zum Überspringen

```bash
# Cracking überspringen (nur erfassen)
sudo wifite --skip-crack

# Bereits erfolgreich geprüfte Ziele überspringen
sudo wifite --skip-cracked
```

### Zufällige MAC-Adresse

```bash
# MAC-Adresse zufällig wählen
sudo wifite --random-mac

# MAC-Adresse nicht ändern
sudo wifite --no-random-mac
```

---

## 🔓 Cracking-Optionen

### Wörterbuchangriff

```bash
# Wortliste angeben
sudo wifite --dict /usr/share/wordlists/rockyou.txt

# Mehrere Wortlisten
sudo wifite --dict wordlist1.txt --dict wordlist2.txt
```

### Hashcat-Integration

```bash
# Hashcat zum Cracking verwenden
sudo wifite --hashcat

# Pfad zu Hashcat
sudo wifite --hashcat-path /usr/bin/hashcat
```

### Aircrack-ng-Optionen

```bash
# Aircrack-ng verwenden (Standard)
sudo wifite --aircrack

# Pfad zu Aircrack-ng
sudo wifite --aircrack-path /usr/bin/aircrack-ng
```

### Modus für vorhandene Mitschnitte

```bash
# Vorhandene Handshake-Dateien knacken
sudo wifite --crack

# Mitschnittdatei angeben
sudo wifite --crack --dict wordlist.txt /path/to/capture.cap
```

---

## 📋 Beispiele

### Einfache WLAN-Prüfung

```bash
# Alle Netzwerke suchen und angreifen
sudo wifite

# Mit eigener Wortliste angreifen
sudo wifite --dict /usr/share/wordlists/rockyou.txt
```

### Ein bestimmtes Netzwerk auswählen

```bash
# Ziel anhand des Namens auswählen
sudo wifite --essid "TargetNetwork" --dict rockyou.txt

# Ziel anhand der MAC-Adresse auswählen
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Angriff nur auf WPA2

```bash
# Nur WPA2-Netzwerke mit PMKID
sudo wifite --wpa --pmkid --dict rockyou.txt
```

### WPS-Angriff

```bash
# WPS mit Pixie Dust
sudo wifite --wps --pixie

# Nur WPS-Brute-Force
sudo wifite --wps-only --bully
```

### Nur starke Signale

```bash
# Nur Netzwerke mit starkem Signal angreifen
sudo wifite --power -50 --wpa
```

### 5-GHz-Netzwerke

```bash
# Nur im 5-GHz-Band angreifen
sudo wifite --5ghz --wpa
```

### Schnelle Prüfung ohne Cracking

```bash
# Nur Handshakes erfassen und später knacken
sudo wifite --skip-crack
```

### Angriff fortsetzen

```bash
# Wifite speichert den Fortschritt automatisch
# Zum Fortsetzen erneut starten
sudo wifite

# Bereits erfolgreich geprüfte Ziele überspringen
sudo wifite --skip-cracked
```

### Ausführlicher Modus

```bash
# Alle Einzelheiten anzeigen
sudo wifite -vv --dict rockyou.txt
```

### Unauffälliger Modus

```bash
# Wenige Deauthentifizierungen, wenig Ausgabe
sudo wifite --num-deauths 1 --quiet
```

---

## 📊 Schnellübersicht

### Wichtige Befehle

| Zweck | Befehl |
| --------- | --------- |
| Einfache Netzwerksuche | `sudo wifite` |
| Ziel anhand des Namens auswählen | `sudo wifite --essid "Name"` |
| Ziel anhand der MAC-Adresse auswählen | `sudo wifite --bssid MAC` |
| Nur WPA | `sudo wifite --wpa` |
| Nur WPS | `sudo wifite --wps-only` |
| PMKID-Angriff | `sudo wifite --pmkid` |
| Mit Wortliste | `sudo wifite --dict wordlist.txt` |
| Cracking überspringen | `sudo wifite --skip-crack` |

### Angriffstypen

| Angriff | Option | Ziel |
| -------- | ------ | -------- |
| WPA-Handshake | `--wpa` | WPA/WPA2-PSK |
| PMKID | `--pmkid` | WPA/WPA2-PSK |
| WPS Pixie | `--wps --pixie` | WPS aktiviert |
| WPS-Brute-Force | `--wps-only` | WPS aktiviert |
| WEP | `--wep` | WEP (veraltet) |

### Häufige Wortlisten

| Pfad | Beschreibung |
| ------ | ------------- |
| `/usr/share/wordlists/rockyou.txt` | Sehr häufig verwendete Passwörter |
| `/usr/share/wordlists/fasttrack.txt` | Schnelle Prüfung |
| `/usr/share/seclists/Passwords/WiFi-WPA/` | WLAN-spezifisch |

### Erfasste Dateien

```
~/.config/wifite/
├── cracked.txt          # Ermittelte Passwörter
├── hs/                  # Handshake-Mitschnitte
│   └── handshake_*.cap
└── pmkid/               # PMKID-Mitschnitte
    └── pmkid_*.22000
```

### Optionsübersicht

| Option | Beschreibung |
| ------ | ------------- |
| `-i` | Schnittstelle |
| `-c` | Kanal |
| `-v` | Ausführliche Ausgabe |
| `--wpa` | Nur WPA/WPA2 |
| `--wep` | Nur WEP |
| `--wps` | WPS aktiviert |
| `--pmkid` | PMKID-Angriff |
| `--dict` | Wortliste |
| `--kill` | Prozesse beenden |
| `--power` | Minimale Signalstärke |
| `--5ghz` | 5-GHz-Band |
| `--2ghz` | 2,4-GHz-Band |

### Fehlerbehebung

```bash
# Schnittstelle nicht gefunden
sudo airmon-ng check kill
sudo wifite -i wlan0

# Keine Netzwerke gefunden
# - Prüfen, ob der Adapter den Monitor-Modus unterstützt
# - Andere Frequenzbänder prüfen: --5ghz oder --2ghz
# - Abstand zum Ziel verringern

# Handshake nicht erfasst
# - Anzahl der Deauthentifizierungen erhöhen: --num-deauths 10
# - Länger warten: --wpa-deauth-timeout 60
# - Andere Clients prüfen

# Cracking schlägt fehl
# - Eine passendere Wortliste verwenden
# - Hashcat mit GPU prüfen: --hashcat
```

---

## 🔄 Wifite und manuelles Aircrack-ng im Vergleich

| Aufgabe | Wifite | Aircrack-ng Suite |
| ------ | -------- | ------------------- |
| Monitor-Modus | Automatisch | `airmon-ng start wlan0` |
| Netzwerksuche | Automatisch | `airodump-ng wlan0mon` |
| Ziel | Interaktiv | Manuelle Filterung |
| Deauthentifizierung | Automatisch | `aireplay-ng -0 ...` |
| Erfassung | Automatisch | `airodump-ng -w ...` |
| Cracking | Automatisch | `aircrack-ng -w ...` |
| **Aufwand** | **Gering** | Manuell |

---

## ⚠️ Rechtlicher Hinweis

```
⚠️ WARNUNG: Wifite nur in eigenen Netzwerken oder mit 
AUSDRÜCKLICHER SCHRIFTLICHER TESTGENEHMIGUNG verwenden.

✅ Autorisierte Penetrationstests
✅ Sicherheitsprüfungen mit Erlaubnis
✅ Lernen in einer Laborumgebung

❌ Unbefugter Netzwerkzugriff ist ILLEGAL
❌ Niemals für schädliche Zwecke verwenden
```

---

## 📚 Ressourcen

- [Wifite2 GitHub](https://github.com/derv82/wifite2)
- [Wifite Wiki](https://github.com/derv82/wifite2/wiki)
- [Aircrack-ng](https://www.aircrack-ng.org/)

### Verwandte Cheatsheets
- [Aircrack-ng](../../Aircrack-ng/translations/README.de.md)
- [Hashcat](../../Hashcat/translations/README.de.md)
- [Linux-Befehle](../../Linux-Commands/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Versionen und Protokolle:** Die Vorlage mischt verschiedene Generationen und Varianten. Kali verweist inzwischen auf die gepflegte kimocoder-Fassung; die ursprünglichen derv82-Beispiele bleiben erhalten. WPS ist ein Einrichtungsverfahren, keine eigene Verschlüsselungsart. Die Liste ist keine Zusage, dass jedes WLAN unterstützt wird; Adapter, Treiber, Monitor-Modus und verwendete Hilfsprogramme müssen passen. [Kali: Paket und Hilfe](https://www.kali.org/tools/wifite/), [Wifite2: Originalprojekt](https://github.com/derv82/wifite2).
- **Optionen und Wiederholungen:** Die Beispieloptionen sind nicht überall gültig. Insbesondere stehen etwa --wpa-deauth-timeout und --hashcat-path nicht im geprüften Parser. --wps filtert Ziele, während --wps-only WPS-Verfahren beschränkt und nicht ausschließlich PIN-Brute-Force bedeutet. --cracked zeigt gespeicherte Ergebnisse an und nimmt keinen Dateipfad entgegen. Wiederholtes --essid oder --dict ersetzt beim gezeigten store-Parser den vorherigen Wert, statt automatisch eine Liste aufzubauen. [Wifite2: aktuelle Argumentdefinition](https://github.com/kimocoder/wifite2/blob/master/wifite/args.py).
- **Signal, Frequenzband und Dateien:** Negative dBm-Angaben sind nicht allgemein die interne Wifite-Skala: Die ursprüngliche Zielklasse addiert bei negativen Empfangswerten 100. Auch Bandauswahl und verfügbare Optionen unterscheiden sich zwischen Fassungen. Die Verzeichnisgrafik ist schematisch; die ursprüngliche Konfiguration verwendet cracked.txt und hs relativ zur Arbeitsumgebung, nicht zwingend den angegebenen Konfigurationspfad. Ein Neustart bedeutet außerdem nicht in jeder Version eine vollständige Sitzungswiederaufnahme. [Wifite2: Zielmodell](https://github.com/derv82/wifite2/blob/master/wifite/model/target.py), [ursprüngliche Konfiguration](https://github.com/derv82/wifite2/blob/master/wifite/config.py).
- **PMKID:** Erfasst wird eine vom PMK abgeleitete Kennung, nicht der PMK selbst. Der beschriebene PSK-Prüfansatz benötigt passende AP-Unterstützung und eignet sich nicht pauschal für die meisten WPA2- oder für beliebige Enterprise-/WPA3-Netze. „Clientlos“ bedeutet ohne regulär verbundenen Nutzer; das Werkzeug kommuniziert trotzdem aktiv mit dem AP. Ob ein Passwort gefunden wird, hängt unter anderem von den geprüften Kandidaten ab. [Hashcat: ursprünglicher PMKID-Bericht](https://hashcat.net/forum/thread-7717.html).
- **Ablauf und Auswirkung:** --crack wird in der aktuellen Hilfe als Anzeige geeigneter Befehle beschrieben; der gezeigte Aufruf mit angehängtem Capture-Pfad ist kein universeller Dateiaufruf. Wenig Konsolenausgabe oder eine veränderte MAC-Adresse garantiert keine unbemerkte oder störungsfreie Prüfung. Auch --num-deauths 0 darf nicht ohne Berücksichtigung des Wrappers und seiner Zeitgrenzen als unbegrenzte Dauer verstanden werden. Die Originalbeispiele bleiben unverändert. [Kali: Befehlssemantik](https://www.kali.org/tools/wifite/), [Wifite2: Aireplay-Anbindung](https://github.com/derv82/wifite2/blob/master/wifite/tools/aireplay.py).

---

<p align="center">
  <b>📶 Automatisierte WLAN-Sicherheitsprüfung!</b><br>
  <i>Wifite – WLAN-Prüfungen automatisieren und verantwortungsvoll durchführen</i>
</p>
