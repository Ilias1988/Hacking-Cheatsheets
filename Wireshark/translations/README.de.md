# 🦈 Wireshark – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 __        ___           ____  _                _    
 \ \      / (_)_ __ ___ / ___|| |__   __ _ _ __| | __
  \ \ /\ / /| | '__/ _ \\___ \| '_ \ / _` | '__| |/ /
   \ V  V / | | | |  __/ ___) | | | | (_| | |  |   < 
    \_/\_/  |_|_|  \___|____/|_| |_|\__,_|_|  |_|\_\
                                                     
           Analysewerkzeug für Netzwerkprotokolle
```

<p align="center">
  <img src="https://img.shields.io/badge/Wireshark-Network_Analyzer-blue?style=for-the-badge" alt="Wireshark">
  <img src="https://img.shields.io/badge/Packet-Capture-red?style=for-the-badge" alt="Packet Capture">
  <img src="https://img.shields.io/badge/Protocol-Analysis-green?style=for-the-badge" alt="Protocol">
  <img src="https://img.shields.io/badge/Open-Source-orange?style=for-the-badge" alt="Open Source">
</p>

<p align="center">
  <b>🌐 Ein weit verbreitetes Analysewerkzeug für Netzwerkprotokolle</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Übersicht der Oberfläche](#-übersicht-der-oberfläche)
- [Grundlagen der Paketaufzeichnung](#-grundlagen-der-paketaufzeichnung)
- [Anzeigefilter](#-anzeigefilter)
- [Aufzeichnungsfilter](#-aufzeichnungsfilter)
- [Protokollanalyse](#-protokollanalyse)
- [Datenströme verfolgen](#-datenströme-verfolgen)
- [Statistik und Analyse](#-statistik-und-analyse)
- [tshark (CLI)](#-tshark-cli)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tastenkombinationen](#-tastenkombinationen)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**Wireshark** ist ein kostenloses, quelloffenes Werkzeug zur Paketanalyse. Es wird zur Fehlersuche in Netzwerken, zur Analyse, zur Softwareentwicklung und für Sicherheitsprüfungen eingesetzt.

### Wichtige Funktionen

| Eigenschaft | Beschreibung |
|---------|-------------|
| **Detaillierte Protokollanalyse** | Unterstützt Hunderte von Protokollen |
| **Live-Aufzeichnung** | Pakete in Echtzeit aufzeichnen |
| **Offline-Analyse** | Aufgezeichnete Dateien lesen (pcap, pcapng) |
| **Leistungsfähige Filter** | Anzeige- und Aufzeichnungsfilter |
| **Plattformübergreifend** | Windows, Linux, macOS |
| **Grafische Oberfläche und Kommandozeile** | Wireshark (GUI) und tshark (CLI) |

### Anwendungsfälle

| Anwendungsfall | Beschreibung |
|----------|-------------|
| **Fehlersuche im Netzwerk** | Verbindungsprobleme untersuchen |
| **Sicherheitsanalyse** | Schädlichen Datenverkehr erkennen |
| **Protokollentwicklung** | Neue Protokolle testen |
| **Forensik** | Aufgezeichneten Datenverkehr analysieren |
| **Lernen** | Netzwerkgrundlagen erlernen |

---

## 📥 Installation

### Windows
```bash
# Von der offiziellen Website herunterladen
https://www.wireshark.org/download.html

# Alternativ mit Chocolatey
choco install wireshark
```

### Linux (Kali/Ubuntu)
```bash
# Kali Linux (vorinstalliert)
wireshark

# Ubuntu/Debian
sudo apt install wireshark

# Aufzeichnung ohne Root-Konto ermöglichen
sudo usermod -aG wireshark $USER
# Abmelden und erneut anmelden
```

### macOS
```bash
brew install --cask wireshark
```

### Installation prüfen
```bash
wireshark --version
tshark --version
```

---

<a name="-übersicht-der-oberfläche"></a>

## 🖥️ Übersicht der Oberfläche

### Hauptbereiche des Fensters

| Bereich | Beschreibung |
|------|-------------|
| **Menu Bar (Menüleiste)** | File, Edit, View, Go, Capture, Analyze |
| **Main Toolbar (Werkzeugleiste)** | Schaltflächen für den Schnellzugriff |
| **Filter Bar (Filterleiste)** | Anzeigefilter eingeben |
| **Packet List (Paketliste)** | Alle aufgezeichneten Pakete |
| **Packet Details (Paketdetails)** | Protokollschichten des ausgewählten Pakets |
| **Packet Bytes (Paketbytes)** | Unverarbeitete Hex-/ASCII-Daten |

### Spalten der Paketliste (Standard)

| Spalte | Beschreibung |
|--------|-------------|
| No. | Paketnummer |
| Time | Zeitstempel |
| Source | IP-/MAC-Adresse der Quelle |
| Destination | IP-/MAC-Adresse des Ziels |
| Protocol | Protokoll der höchsten erkannten Schicht |
| Length | Paketgröße in Bytes |
| Info | Protokollspezifische Informationen |

---

## 📡 Grundlagen der Paketaufzeichnung

### Aufzeichnung starten

```
1. Auf der Startseite eine Schnittstelle auswählen
2. Auf "Start Capture" klicken (blaue Haifischflosse)
3. Alternativ: Capture → Start

Schnellzugriff: Auf den Schnittstellennamen doppelklicken
```

### Aufzeichnung stoppen

```
Auf das rote Quadrat in der Werkzeugleiste klicken
Alternativ: Capture → Stop
Alternativ: Ctrl+E
```

### Aufzeichnung speichern

```
File → Save As
Formate: pcapng (Standard), pcap usw.
```

### Aufzeichnungsdatei öffnen

```
File → Open
Alternativ: Ctrl+O
Alternativ: wireshark file.pcap
```

---

## 🔍 Anzeigefilter

### Filtersyntax

Anzeigefilter verwenden eine **Syntax zum Vergleichen von Feldwerten**:

```
field operator value
```

### Vergleichsoperatoren

| Operator | Beschreibung | Beispiel |
|----------|-------------|---------|
| `==` oder `eq` | Gleich | `ip.addr == 192.168.1.1` |
| `!=` oder `ne` | Ungleich | `ip.addr != 10.0.0.1` |
| `>` oder `gt` | Größer als | `frame.len > 100` |
| `<` oder `lt` | Kleiner als | `tcp.port < 1024` |
| `>=` oder `ge` | Größer oder gleich | `frame.len >= 64` |
| `<=` oder `le` | Kleiner oder gleich | `tcp.port <= 1024` |
| `contains` | Enthält eine Zeichenfolge | `http contains "password"` |
| `matches` | Übereinstimmung mit regulärem Ausdruck | `http.host matches ".*\.com"` |

### Logische Operatoren

| Operator | Beschreibung | Beispiel |
|----------|-------------|---------|
| `and` oder `&&` | Logisches UND | `ip.src == 192.168.1.1 and tcp.port == 80` |
| `or` oder `\|\|` | Logisches ODER | `tcp.port == 80 or tcp.port == 443` |
| `not` oder `!` | Logisches NICHT | `not arp` |
| `()` | Gruppierung | `(ip.src == 10.0.0.1) and (tcp or udp)` |

### IP-Filter

```bash
# Bestimmte IP-Adresse als Quelle oder Ziel
ip.addr == 192.168.1.1

# Nur Quell-IP-Adresse
ip.src == 192.168.1.1

# Nur Ziel-IP-Adresse
ip.dst == 192.168.1.1

# IP-Bereich (Subnetz)
ip.addr == 192.168.1.0/24

# IP-Adresse ausschließen
ip.addr != 192.168.1.1

# Zwischen zwei Hosts
ip.addr == 192.168.1.1 and ip.addr == 192.168.1.2
```

### Portfilter

```bash
# Bestimmter Quell- oder Zielport
tcp.port == 80
udp.port == 53

# Nur Quellport
tcp.srcport == 443

# Nur Zielport
tcp.dstport == 22

# Portbereich
tcp.port >= 1 and tcp.port <= 1024

# Mehrere Ports
tcp.port == 80 or tcp.port == 443 or tcp.port == 8080
tcp.port in {80 443 8080}
```

### Protokollfilter

```bash
# Nach Protokollname
http
dns
tcp
udp
arp
icmp
tls
ssh
ftp
smtp
dhcp

# HTTP-spezifisch
http.request
http.response
http.request.method == "GET"
http.request.method == "POST"
http.response.code == 200
http.response.code >= 400

# DNS-spezifisch
dns.qry.name == "google.com"
dns.qry.type == 1          # A-Eintrag
dns.flags.response == 1    # DNS-Antworten

# TCP-spezifisch
tcp.flags.syn == 1         # SYN-Pakete
tcp.flags.fin == 1         # FIN-Pakete
tcp.flags.reset == 1       # RST-Pakete
tcp.analysis.retransmission  # Erneute Übertragungen
tcp.stream == 5            # Bestimmter Datenstrom
```

### Inhaltsfilter

```bash
# Enthält eine Zeichenfolge
http contains "password"
tcp contains "login"
frame contains "secret"

# HTTP-Inhalte
http.request.uri contains "admin"
http.host == "example.com"
http.user_agent contains "Mozilla"
http.cookie contains "session"

# Groß- und Kleinschreibung ignorieren
http.host matches "(?i)example"
```

### Häufige Filterbeispiele

```bash
# Nur HTTP-Verkehr
http

# HTTPS-Verkehr
tls

# DNS-Abfragen
dns.flags.response == 0

# Fehlgeschlagene Verbindungen (RST laut Vorlage)
tcp.flags.reset == 1

# Große Pakete
frame.len > 1000

# Bestimmte MAC-Adresse
eth.addr == aa:bb:cc:dd:ee:ff

# Broadcast-Verkehr
eth.dst == ff:ff:ff:ff:ff:ff

# Nicht lokaler Verkehr laut Vorlage
!(ip.addr == 192.168.0.0/16)

# TCP-Drei-Wege-Handshake laut Vorlage
tcp.flags.syn == 1 or tcp.flags.ack == 1

# HTTP-POST-Anfragen mit Daten
http.request.method == "POST" and http.file_data
```

---

## 🎣 Aufzeichnungsfilter

### BPF-Syntax (Berkeley Packet Filter)

Aufzeichnungsfilter verwenden **BPF-Syntax**. Sie unterscheidet sich von der Syntax der Anzeigefilter.

### Grundsyntax

```bash
# Host
host 192.168.1.1
src host 192.168.1.1
dst host 192.168.1.1

# Netzwerk
net 192.168.1.0/24
src net 10.0.0.0/8

# Port
port 80
src port 443
dst port 22
portrange 1-1024

# Protokoll
tcp
udp
icmp
arp
```

### Kombinierte Filter

```bash
# Host und Port
host 192.168.1.1 and port 80

# Bestimmte Kommunikationsbeziehung
host 192.168.1.1 and host 192.168.1.2

# Datenverkehr ausschließen
not port 22
not host 10.0.0.1

# Komplexer Filter
tcp and (port 80 or port 443) and host 192.168.1.1
```

### Häufige Aufzeichnungsfilter

| Zweck | Filter |
|---------|--------|
| Bestimmter Host | `host 192.168.1.1` |
| Webverkehr | `port 80 or port 443` |
| Nur DNS | `port 53` |
| Nur SSH | `port 22` |
| Kein Broadcast | `not broadcast` |
| Bestimmtes Subnetz | `net 192.168.1.0/24` |

---

## 🔬 Protokollanalyse

### TCP-Analyse

```bash
# TCP-Flags
tcp.flags.syn == 1 and tcp.flags.ack == 0    # Nur SYN
tcp.flags.syn == 1 and tcp.flags.ack == 1    # SYN-ACK
tcp.flags.fin == 1                            # FIN
tcp.flags.reset == 1                          # RST

# TCP-Probleme
tcp.analysis.retransmission
tcp.analysis.duplicate_ack
tcp.analysis.zero_window
tcp.analysis.lost_segment

# TCP-Datenstrom
tcp.stream == 0
```

### HTTP-Analyse

```bash
# HTTP-Anfragen
http.request
http.request.method == "GET"
http.request.method == "POST"
http.request.uri contains "/login"

# HTTP-Antworten
http.response
http.response.code == 200
http.response.code == 404
http.response.code >= 400    # Fehler

# HTTP-Inhalte
http.content_type contains "json"
http.content_type contains "html"
http.file_data contains "password"
```

### DNS-Analyse

```bash
# DNS-Abfragen
dns.flags.response == 0
dns.qry.name == "example.com"
dns.qry.type == 1      # A-Eintrag
dns.qry.type == 28     # AAAA-Eintrag
dns.qry.type == 5      # CNAME
dns.qry.type == 15     # MX

# DNS-Antworten
dns.flags.response == 1
dns.flags.rcode != 0   # Fehler
```

### TLS-/SSL-Analyse

```bash
# TLS-Verkehr
tls

# TLS-Handshake
tls.handshake
tls.handshake.type == 1     # Client Hello
tls.handshake.type == 2     # Server Hello

# Zertifikate
tls.handshake.certificate

# TLS-Version
tls.record.version == 0x0303  # TLS 1.2
```

### ARP-Analyse

```bash
# Alle ARP-Pakete
arp

# ARP-Anfragen
arp.opcode == 1

# ARP-Antworten
arp.opcode == 2

# Gratuitous ARP (möglicher Spoofing-Hinweis)
arp.isgratuitous == 1
```

---

## 🔗 Datenströme verfolgen

### TCP-Datenstrom verfolgen

```
Paket rechts anklicken → Follow → TCP Stream

Alternativ: Analyze → Follow → TCP Stream
```

### HTTP-Datenstrom verfolgen

```
HTTP-Paket rechts anklicken → Follow → HTTP Stream
```

### UDP-Datenstrom verfolgen

```
UDP-Paket rechts anklicken → Follow → UDP Stream
```

### Fenster des Datenstroms

- Zeigt die vollständige Kommunikation
- Farbliche Kennzeichnung von Client und Server
- Als Text oder Rohdaten speichern
- Filter-Schaltflächen für die Übertragungsrichtung

---

## 📊 Statistik und Analyse

### Protokollhierarchie

```
Statistics → Protocol Hierarchy

Zeigt die Verteilung der aufgezeichneten Protokolle
```

### Kommunikationsbeziehungen

```
Statistics → Conversations

Alle Kommunikationsbeziehungen nach folgenden Kriterien anzeigen:
- Ethernet
- IPv4/IPv6
- TCP
- UDP
```

### Endpunkte

```
Statistics → Endpoints

Alle unterschiedlichen Endpunkte auflisten
```

### E/A-Diagramm

```
Statistics → IO Graph

Den zeitlichen Verlauf des Datenverkehrs darstellen
```

### Experteninformationen

```
Analyze → Expert Information

Zeigt Warnungen, Fehler und Hinweise:
- Erneute TCP-Übertragungen
- Fehlerhaft aufgebaute Pakete
- Fehler
```

### HTTP-Statistik

```
Statistics → HTTP → Requests
Statistics → HTTP → Packet Counter
```

---

## 💻 tshark (CLI)

### Grundlegende Verwendung

```bash
# Pakete aufzeichnen
tshark -i eth0

# Aufzeichnungsdatei lesen
tshark -r capture.pcap

# In eine Datei schreiben
tshark -i eth0 -w output.pcap
```

### Filter anwenden

```bash
# Aufzeichnungsfilter
tshark -i eth0 -f "port 80"

# Anzeigefilter
tshark -r capture.pcap -Y "http"
```

### Ausgabeoptionen

```bash
# Bestimmte Felder anzeigen
tshark -r capture.pcap -T fields -e ip.src -e ip.dst -e tcp.port

# JSON-Ausgabe
tshark -r capture.pcap -T json

# Statistiken anzeigen
tshark -r capture.pcap -q -z io,stat,1
```

### Häufige tshark-Beispiele

```bash
# HTTP-Anfragen live aufzeichnen
tshark -i eth0 -Y "http.request" -T fields -e http.host -e http.request.uri

# DNS-Abfragen extrahieren
tshark -r capture.pcap -Y "dns.flags.response == 0" -T fields -e dns.qry.name

# Pakete nach IP-Adresse zählen
tshark -r capture.pcap -q -z ip_hosts,tree

# HTTP-Statistik
tshark -r capture.pcap -q -z http,tree
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: HTTP-Verkehr aufzeichnen
```bash
# Aufzeichnungsfilter: port 80
# Anzeigefilter: http
# HTTP-Datenstrom verfolgen, um vollständige Anfragen und Antworten zu sehen
```

### Beispiel 2: Zugangsdaten finden
```bash
# Anzeigefilter
http.request.method == "POST" and http contains "password"

# Alternativ für FTP
ftp contains "PASS"
```

### Beispiel 3: Portscan erkennen
```bash
# Mehrere SYN-Pakete an unterschiedliche Ports
tcp.flags.syn == 1 and tcp.flags.ack == 0

# Nach vielen Zielen ausgehend von einer Quelle suchen
Statistics → Conversations → TCP
```

### Beispiel 4: DNS-Exfiltration
```bash
# Große DNS-Abfragen laut Vorlage
dns and frame.len > 512

# Ungewöhnliche TXT-Einträge laut Vorlage
dns.qry.type == 16
```

### Beispiel 5: Dateien extrahieren
```
File → Export Objects → HTTP

Die zu exportierenden Dateien auswählen
```

### Beispiel 6: TLS-Handshake analysieren
```bash
tls.handshake

# Client Hello auf SNI untersuchen
tls.handshake.type == 1
```

### Beispiel 7: Klartextpasswörter finden
```bash
http contains "password" or http contains "passwd" or ftp or telnet
```

---

## 📊 Kurzreferenz

### Wichtige Anzeigefilter

| Zweck | Filter |
|---------|--------|
| HTTP-Verkehr | `http` |
| DNS-Verkehr | `dns` |
| Bestimmte IP-Adresse | `ip.addr == 192.168.1.1` |
| Bestimmter Port | `tcp.port == 80` |
| TCP-Probleme | `tcp.analysis.flags` |
| HTTP-Fehler | `http.response.code >= 400` |
| Enthält eine Zeichenfolge | `frame contains "text"` |
| Protokoll ausschließen | `not arp` |

### Häufige Aufzeichnungsfilter

| Zweck | Filter |
|---------|--------|
| Bestimmter Host | `host 192.168.1.1` |
| Webverkehr | `port 80 or port 443` |
| Nur DNS | `port 53` |
| Kein Broadcast | `not broadcast` |

---

<a name="-tastenkombinationen"></a>

## ⌨️ Tastenkombinationen

| Tastenkombination | Aktion |
|----------|--------|
| `Ctrl+E` | Aufzeichnung starten oder stoppen |
| `Ctrl+K` | Aufzeichnungsoptionen |
| `Ctrl+O` | Datei öffnen |
| `Ctrl+S` | Datei speichern |
| `Ctrl+F` | Paket suchen |
| `Ctrl+G` | Zu einem Paket springen |
| `Ctrl+→` | Nächstes Paket der Kommunikationsbeziehung |
| `Ctrl+←` | Vorheriges Paket der Kommunikationsbeziehung |
| `Ctrl+.` | Nächstes markiertes Paket |
| `Ctrl+M` | Paket markieren oder Markierung entfernen |
| `Ctrl+Shift+M` | Alle angezeigten Pakete markieren |
| `Space` | Protokollbaum auf- oder zuklappen |
| `Tab` | Nächster Fensterbereich |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise zur Aufzeichnung

1. **Aufzeichnungsfilter verwenden**
   ```
   Dateigröße durch Filtern während der Aufzeichnung verringern
   ```

2. **Ringpuffer für lange Aufzeichnungen**
   ```
   Capture → Options → Output → Ring buffer
   ```

3. **Auf einem entfernten System aufzeichnen**
   ```bash
   ssh user@host tcpdump -w - | wireshark -k -i -
   ```

### Hinweise zur Analyse

1. **Farbregeln**
   ```
   View → Coloring Rules
   Farben für Protokolle anpassen
   ```

2. **Zeitanzeige**
   ```
   View → Time Display Format
   "Seconds Since Beginning of Capture" ausprobieren (Sekunden seit Beginn der Aufzeichnung)
   ```

3. **Namensauflösung**
   ```
   View → Name Resolution
   Je nach Bedarf aktivieren oder deaktivieren
   ```

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** Netzwerkpakete dürfen nur für **autorisierte Zwecke** aufgezeichnet werden.
> 
> - ✅ In eigenen Netzwerken aufzeichnen
> - ✅ Sicherheitstests mit Genehmigung
> - ✅ Fehlersuche auf freigegebenen Systemen
> - ❌ Niemals ohne Genehmigung in fremden Netzwerken aufzeichnen
> - ❌ Das Aufzeichnen fremden Datenverkehrs kann rechtswidrig sein
> 
> **Eine nicht genehmigte Netzwerküberwachung ist laut Vorlage in den meisten Rechtsordnungen rechtswidrig.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [Offizielle Wireshark-Website](https://www.wireshark.org/)
- [Wireshark-Benutzerhandbuch](https://www.wireshark.org/docs/wsug_html/)
- [Referenz der Anzeigefilter](https://www.wireshark.org/docs/dfref/)
- [Wireshark Wiki](https://wiki.wireshark.org/)

### Lernmaterial
- [Wireshark-Anleitung](https://www.wireshark.org/docs/wsug_html/)
- [Beispielaufzeichnungen](https://wiki.wireshark.org/SampleCaptures)

### Verwandte Spickzettel
- [Nmap](../../Nmap/translations/README.de.md)
- [Metasploit](../../Metasploit/translations/README.de.md)
- [Hydra](../../Hydra/translations/README.de.md)

---

<p align="center">
  <b>🦈 Netzwerkanalyse sicher beherrschen!</b><br>
  <i>Den Datenverkehr im Netzwerk verstehen</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Filter und Versionen:** Anzeigefilter bestimmen die sichtbaren Pakete; sie entfernen keine Pakete aus der Aufzeichnungsdatei. Aufzeichnungsfilter verwenden eine andere Sprache und begrenzen bereits die erfassten Daten. `ip.addr` kann mehrere Feldwerte pro Paket haben; die Interpretation von `!=` wurde in Wireshark 3.6 geändert. Ältere Versionen können deshalb bei Ausschlussfiltern andere Ergebnisse liefern. Die mit `bash` markierten Filter sind Wireshark-/BPF-Ausdrücke und keine Shell-Skripte. [Wireshark: Filterausdrücke](https://www.wireshark.org/docs/wsug_html_chunked/ChWorkBuildDisplayFilterSection.html).

> **Protokolle und Aussagekraft:** `tls` zeigt TLS-Verkehr, nicht ausschließlich HTTPS. Der Record-Wert `0x0303` beweist nicht TLS 1.2: TLS 1.3 verwendet ihn ebenfalls als Legacy-Wert. Verschlüsselter Anwendungsinhalt ist ohne geeignete Entschlüsselungsinformationen nicht als HTTP-Klartext sichtbar. Das ACK-/SYN-Beispiel isoliert nicht nur vollständige Drei-Wege-Handshakes. `frame.len > 512` misst die gesamte Paketlänge und beweist keine DNS-Exfiltration; TXT-Abfragen und Gratuitous ARP sind ebenfalls nur Untersuchungshinweise. [TLS 1.3: RFC 8446](https://www.rfc-editor.org/rfc/rfc8446.html).

> **Aufzeichnung und Bedienung:** Die Mitgliedschaft in der Gruppe `wireshark` allein ersetzt nicht die passende Einrichtung von `dumpcap` und den Betriebssystemrechten. Schnittstelle, Netzposition und Aufzeichnungsmodus entscheiden, welcher Verkehr sichtbar ist. Menünamen, Tastenkombinationen und verfügbare Protokollfelder können versionsabhängig sein; echte englische UI-Bezeichnungen bleiben als Referenz erhalten. Namensauflösung kann zusätzliche Abfragen auslösen. Die Originalbefehle und Filter wurden unverändert belassen; keine Aufzeichnung und kein Beispiel wurde ausgeführt. [Wireshark: Aufzeichnungsrechte](https://wiki.wireshark.org/CaptureSetup/CapturePrivileges).
