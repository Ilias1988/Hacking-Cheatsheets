# 📡 tcpdump – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _             _                       
 | |_ ___ _ __ | |_ _ _ _____ _ __ _ __ 
 | __/ __| '_ \| __| | | '_ ` _ \| '_ \
 | || (__| |_) | |_| |_| | | | | | |_) |
  \__\___| .__/ \__|\__,_|_| |_| | .__/ 
         |_|                     |_|    
      Paketanalysator für die Kommandozeile
```

<p align="center">
  <img src="https://img.shields.io/badge/tcpdump-Packet_Analyzer-blue?style=for-the-badge" alt="tcpdump">
  <img src="https://img.shields.io/badge/Command-Line-red?style=for-the-badge" alt="CLI">
  <img src="https://img.shields.io/badge/BPF-Filters-green?style=for-the-badge" alt="BPF">
  <img src="https://img.shields.io/badge/Cross-Platform-orange?style=for-the-badge" alt="Cross Platform">
</p>

<p align="center">
  <b>🔍 Der klassische Paketanalysator für die Kommandozeile</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Grundsyntax](#-grundsyntax)
- [Häufige Optionen](#-häufige-optionen)
- [BPF-Filter](#-bpf-filter)
- [Protokollfilter](#-protokollfilter)
- [Ausgabeoptionen](#-ausgabeoptionen)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**tcpdump** ist ein leistungsfähiger Paketanalysator für die Kommandozeile, der auf vielen Unix-artigen Betriebssystemen verfügbar ist. Er verwendet die Bibliothek libpcap und die Syntax des Berkeley Packet Filter (BPF).

### Wichtige Funktionen

| Eigenschaft | Beschreibung |
|---------|-------------|
| **Ressourcenschonend** | Keine grafische Oberfläche, geringer Ressourcenbedarf |
| **BPF-Filter** | Leistungsfähige Paketfilter |
| **pcap-kompatibel** | pcap-Dateien lesen und schreiben |
| **Skriptfähig** | Gut für die Automatisierung geeignet |
| **Vielseitig verfügbar** | Laut Vorlage auf allen Unix-Systemen verfügbar |

### tcpdump im Vergleich zu Wireshark

| Eigenschaft | tcpdump | Wireshark |
|---------|---------|-----------|
| **Oberfläche** | CLI | GUI |
| **Ressourcen** | Gering | Höher |
| **Fernzugriff** | Direkt über SSH | Einrichtung erforderlich |
| **Skripte** | Einfach | Komplex |
| **Analyse** | Grundlegend | Erweitert |

---

## 📥 Installation

### Linux (laut Vorlage meist vorinstalliert)
```bash
# Kali/Debian/Ubuntu
sudo apt install tcpdump

# CentOS/RHEL
sudo yum install tcpdump

# Arch
sudo pacman -S tcpdump
```

### macOS (vorinstalliert laut Vorlage)
```bash
tcpdump --version
```

### Installation prüfen
```bash
tcpdump --version
tcpdump -D              # Schnittstellen auflisten
```

---

<a name="-grundsyntax"></a>

## ⌨️ Grundsyntax

### Allgemeine Syntax
```bash
tcpdump [options] [filter expression]
```

### Grundlegende Befehle
```bash
# Auf der Standardschnittstelle aufzeichnen
sudo tcpdump

# Auf einer bestimmten Schnittstelle aufzeichnen
sudo tcpdump -i eth0

# Eine begrenzte Anzahl von Paketen aufzeichnen
sudo tcpdump -c 100

# Mit ausführlicher Ausgabe aufzeichnen
sudo tcpdump -v
sudo tcpdump -vv
sudo tcpdump -vvv
```

---

<a name="-häufige-optionen"></a>

## ⚙️ Häufige Optionen

### Anzeigeoptionen

| Option | Beschreibung |
|--------|-------------|
| `-i` | Schnittstelle für die Aufzeichnung |
| `-c` | Anzahl aufzuzeichnender Pakete |
| `-n` | Hostnamen nicht auflösen |
| `-nn` | Hostnamen und Ports nicht auflösen |
| `-v` | Ausführliche Ausgabe |
| `-vv` | Noch ausführlicher |
| `-vvv` | Maximale Ausführlichkeit |
| `-q` | Reduzierte Ausgabe |
| `-t` | Zeitstempel nicht ausgeben |
| `-tt` | Unix-Zeitstempel ausgeben |
| `-ttt` | Differenz zum vorigen Zeitstempel ausgeben |
| `-tttt` | Datum und Uhrzeit ausgeben |

### Aufzeichnungsoptionen

| Option | Beschreibung |
|--------|-------------|
| `-w` | In eine pcap-Datei schreiben |
| `-r` | Aus einer pcap-Datei lesen |
| `-s` | Aufzeichnungslänge je Paket (Bytes) |
| `-s0` | Pakete laut Vorlage vollständig aufzeichnen |
| `-D` | Verfügbare Schnittstellen auflisten |

### Ausgabeoptionen

| Option | Beschreibung |
|--------|-------------|
| `-A` | ASCII ausgeben |
| `-X` | Hexadezimal und ASCII ausgeben |
| `-XX` | Hexadezimal und ASCII einschließlich Link-Layer-Header ausgeben |
| `-e` | Link-Layer-Header ausgeben |
| `-l` | Zeilenweise gepufferte Ausgabe |

### Häufige Kombinationen
```bash
# Keine Namensauflösung, ausführlich, laut Vorlage vollständige Pakete
sudo tcpdump -nnvvs0 -i eth0

# In eine Datei speichern
sudo tcpdump -i eth0 -w capture.pcap

# Aus einer Datei lesen
tcpdump -r capture.pcap

# ASCII-Ausgabe
sudo tcpdump -A -i eth0

# Hexadezimal und ASCII
sudo tcpdump -X -i eth0
```

---

## 🎣 BPF-Filter

### Hostfilter

```bash
# Datenverkehr zu oder von einem bestimmten Host
tcpdump host 192.168.1.1

# Nur der Quellhost
tcpdump src host 192.168.1.1

# Nur der Zielhost
tcpdump dst host 192.168.1.1

# Mehrere Hosts
tcpdump host 192.168.1.1 or host 192.168.1.2

# Host ausschließen
tcpdump not host 192.168.1.1

# Netzwerk/Subnetz
tcpdump net 192.168.1.0/24
tcpdump src net 10.0.0.0/8
```

### Portfilter

```bash
# Bestimmter Port
tcpdump port 80
tcpdump port 443

# Quellport
tcpdump src port 443

# Zielport
tcpdump dst port 22

# Portbereich
tcpdump portrange 1-1024

# Mehrere Ports
tcpdump port 80 or port 443
tcpdump port 80 or port 443 or port 8080
```

### Protokollfilter

```bash
# Nach Protokoll
tcpdump tcp
tcpdump udp
tcpdump icmp
tcpdump arp

# Protokoll und Port
tcpdump tcp port 80
tcpdump udp port 53
```

### Logische Operatoren

| Operator | Alternative | Beschreibung |
|----------|-------------|-------------|
| `and` | `&&` | Logisches UND |
| `or` | `\|\|` | Logisches ODER |
| `not` | `!` | Logisches NICHT |
| `()` | | Gruppierung |

### Kombinierte Filter

```bash
# Host UND Port
tcpdump host 192.168.1.1 and port 80

# Host UND Protokoll
tcpdump host 192.168.1.1 and tcp

# Komplexer Filter
tcpdump 'host 192.168.1.1 and (port 80 or port 443)'

# Bestimmten Datenverkehr ausschließen
tcpdump not port 22 and not port 53

# Datenverkehr zwischen zwei Hosts
tcpdump host 192.168.1.1 and host 192.168.1.2
```

---

## 📡 Protokollfilter

### TCP-Flags

```bash
# SYN-Pakete
tcpdump 'tcp[tcpflags] & tcp-syn != 0'
tcpdump 'tcp[13] & 2 != 0'

# SYN-ACK-Pakete
tcpdump 'tcp[tcpflags] & (tcp-syn|tcp-ack) == (tcp-syn|tcp-ack)'

# FIN-Pakete
tcpdump 'tcp[tcpflags] & tcp-fin != 0'

# RST-Pakete
tcpdump 'tcp[tcpflags] & tcp-rst != 0'

# PSH-Pakete
tcpdump 'tcp[tcpflags] & tcp-push != 0'

# Nur ACK
tcpdump 'tcp[tcpflags] == tcp-ack'
```

### Kurzreferenz der TCP-Flags

| Flag | Hex | Dezimal | tcpdump |
|------|-----|-----|---------|
| FIN | 0x01 | 1 | tcp-fin |
| SYN | 0x02 | 2 | tcp-syn |
| RST | 0x04 | 4 | tcp-rst |
| PSH | 0x08 | 8 | tcp-push |
| ACK | 0x10 | 16 | tcp-ack |
| URG | 0x20 | 32 | tcp-urg |

### HTTP-Datenverkehr

```bash
# HTTP-Anfragen (Port 80)
tcpdump -A 'tcp port 80 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)'

# GET-Anfragen
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'

# POST-Anfragen
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x504f5354'

# Einfache HTTP-Aufzeichnung
tcpdump -A -s0 'tcp port 80'
```

### DNS-Datenverkehr

```bash
# Gesamter DNS-Datenverkehr
tcpdump port 53
tcpdump udp port 53

# Nur DNS-Abfragen
tcpdump 'udp port 53 and udp[10] & 0x80 = 0'

# Nur DNS-Antworten
tcpdump 'udp port 53 and udp[10] & 0x80 = 0x80'
```

### ICMP-Datenverkehr

```bash
# Gesamter ICMP-Datenverkehr
tcpdump icmp

# ICMP-Echo-Anfrage (Ping)
tcpdump 'icmp[icmptype] == icmp-echo'

# ICMP-Echo-Antwort
tcpdump 'icmp[icmptype] == icmp-echoreply'

# ICMP-Unreachable-Meldungen
tcpdump 'icmp[icmptype] == icmp-unreach'
```

### ARP-Datenverkehr

```bash
# Gesamter ARP-Datenverkehr
tcpdump arp

# ARP-Anfragen
tcpdump 'arp[6:2] == 1'

# ARP-Antworten
tcpdump 'arp[6:2] == 2'
```

---

## 💾 Ausgabeoptionen

### In eine Datei schreiben

```bash
# In eine pcap-Datei schreiben
tcpdump -w capture.pcap

# Mit Rotation schreiben (100 Dateien mit jeweils 1 MB laut Vorlage)
tcpdump -w capture.pcap -W 100 -C 1

# Mit Zeitstempel im Dateinamen schreiben
tcpdump -w capture_%Y%m%d_%H%M%S.pcap -G 3600

# Aufzeichnen und anzeigen
tcpdump -w capture.pcap | tee
```

### Aus einer Datei lesen

```bash
# pcap-Datei lesen
tcpdump -r capture.pcap

# Mit Filtern lesen
tcpdump -r capture.pcap 'host 192.168.1.1'

# Pakete zählen
tcpdump -r capture.pcap | wc -l

# Mit ausführlicher Ausgabe lesen
tcpdump -nnvvXSs0 -r capture.pcap
```

### Anzeigeformate

```bash
# ASCII-Inhalt
tcpdump -A -i eth0

# Hexadezimal und ASCII
tcpdump -X -i eth0

# Nur hexadezimale Ausgabe
tcpdump -x -i eth0

# Mit Ethernet-Headern
tcpdump -XX -i eth0

# Absolute Sequenznummern ausgeben
tcpdump -S -i eth0
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: Web-Datenverkehr aufzeichnen
```bash
sudo tcpdump -i eth0 -nnvvXSs0 'tcp port 80 or tcp port 443'
```

### Beispiel 2: DNS-Abfragen aufzeichnen
```bash
sudo tcpdump -i eth0 -nn 'udp port 53'
```

### Beispiel 3: Datenverkehr zu einem bestimmten Host aufzeichnen
```bash
sudo tcpdump -i eth0 -nn host 192.168.1.100
```

### Beispiel 4: SSH-Verbindungen finden
```bash
sudo tcpdump -i eth0 'tcp port 22'
```

### Beispiel 5: ICMP (Ping) aufzeichnen
```bash
sudo tcpdump -i eth0 icmp
```

### Beispiel 6: Portscan erkennen (SYN)
```bash
sudo tcpdump -i eth0 'tcp[tcpflags] == tcp-syn'
```

### Beispiel 7: Nur eingehenden Datenverkehr aufzeichnen
```bash
sudo tcpdump -i eth0 'dst host $(hostname -I | cut -d" " -f1)'
```

### Beispiel 8: Bestimmte Kommunikation beobachten
```bash
sudo tcpdump -i eth0 'host 192.168.1.1 and host 192.168.1.2'
```

### Beispiel 9: FTP-Zugangsdaten aufzeichnen
```bash
sudo tcpdump -A -i eth0 'tcp port 21'
```

### Beispiel 10: Für die Wireshark-Analyse speichern
```bash
sudo tcpdump -i eth0 -s0 -w capture.pcap
# Anschließend capture.pcap in Wireshark öffnen
```

### Beispiel 11: Entfernte Aufzeichnung über SSH
```bash
ssh user@remote 'sudo tcpdump -w - -i eth0' | wireshark -k -i -
```

### Beispiel 12: Aufzeichnung ohne SSH
```bash
sudo tcpdump -i eth0 'not port 22'
```

---

## 📊 Kurzreferenz

### Wichtige Befehle

| Aufgabe | Befehl |
|------|---------|
| Schnittstellen auflisten | `tcpdump -D` |
| Einfache Aufzeichnung | `tcpdump -i eth0` |
| Keine Namensauflösung | `tcpdump -nn` |
| N Pakete aufzeichnen | `tcpdump -c 100` |
| In eine Datei schreiben | `tcpdump -w file.pcap` |
| Aus einer Datei lesen | `tcpdump -r file.pcap` |
| Ausführliche Ausgabe | `tcpdump -v` |
| ASCII-Ausgabe | `tcpdump -A` |
| Hexadezimale Ausgabe | `tcpdump -X` |
| Vollständiges Paket laut Vorlage | `tcpdump -s0` |

### Häufige Filter

| Filter | Beschreibung |
|--------|-------------|
| `host 1.2.3.4` | Datenverkehr zum oder vom Host |
| `port 80` | Datenverkehr auf Port 80 |
| `tcp` | Nur TCP-Datenverkehr |
| `udp` | Nur UDP-Datenverkehr |
| `icmp` | Nur ICMP-Datenverkehr |
| `net 192.168.1.0/24` | Datenverkehr zum oder vom Subnetz |
| `src host 1.2.3.4` | Quelle ist der angegebene Host |
| `dst port 443` | Zielport 443 |

### Filterkombinationen

| Beispiel | Beschreibung |
|---------|-------------|
| `host A and port 80` | Host A, Port 80 |
| `port 80 or port 443` | Port 80 oder 443 |
| `not port 22` | SSH ausschließen |
| `tcp and port 80` | TCP-Port 80 |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise zur Leistung

```bash
# DNS-Namen nicht auflösen (schneller)
tcpdump -nn

# Aufzeichnungslänge je Paket begrenzen
tcpdump -s 96   # Nur Header laut Vorlage

# BPF-Filter zur Lastreduzierung verwenden
tcpdump 'host 192.168.1.1'  # Auf Kernel-Ebene filtern
```

### Sicherheitshinweise

```bash
# Ohne Root-Rechte ausführen, sofern entsprechend eingerichtet
# Alternativ die Aufzeichnungsdauer begrenzen

# Berechtigungen nach der Aufzeichnung abgeben (Angabe der Vorlage)
tcpdump -Z user
```

### Nützliche Aliase

```bash
# In ~/.bashrc eintragen
alias tcpdump='sudo tcpdump -nnvvXSs0'
alias tcpdump-web='sudo tcpdump -nnvvXSs0 port 80 or port 443'
alias tcpdump-dns='sudo tcpdump -nn port 53'
```

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** Netzwerkpakete dürfen nur für **autorisierte Zwecke** aufgezeichnet werden.
> 
> - ✅ In eigenen Netzwerken aufzeichnen
> - ✅ Sicherheitstests mit Genehmigung
> - ✅ Fehleranalyse an Systemen mit entsprechender Genehmigung
> - ❌ Niemals ohne Genehmigung in fremden Netzwerken aufzeichnen
> - ❌ Die Aufzeichnung fremden Datenverkehrs kann rechtswidrig sein
> 
> **Netzwerküberwachung ohne Genehmigung ist laut Vorlage rechtswidrig.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [tcpdump-Handbuch](https://www.tcpdump.org/manpages/tcpdump.1.html)
- [Offizielle tcpdump-Website](https://www.tcpdump.org/)
- [BPF-Filtersyntax](https://biot.com/capstats/bpf.html)

### Verwandte Spickzettel
- [Wireshark](../../Wireshark/translations/README.de.md)
- [Nmap](../../Nmap/translations/README.de.md)
- [Metasploit](../../Metasploit/translations/README.de.md)

---

<p align="center">
  <b>📡 Paketanalyse auf der Kommandozeile beherrschen!</b><br>
  <i>Die ressourcenschonende Alternative zu Wireshark</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Dateiausgabe und Zählung:** `-w` schreibt Paketdaten statt der normalen dekodierten Anzeige; `tcpdump -w capture.pcap | tee` zeigt deshalb nicht automatisch die Pakete an. Das Handbuch dokumentiert dafür versionsabhängige Anzeigeoptionen. `wc -l` zählt Ausgabezeilen, nicht zuverlässig Pakete. `-C 1` verwendet standardmäßig eine Million Bytes. [tcpdump-Handbuch im offiziellen Repository](https://github.com/the-tcpdump-group/tcpdump/blob/master/tcpdump.1.in).

> **Aufzeichnungslänge und Rechte:** `-s0` setzt in der geprüften Version den Standardwert 262144 Bytes, nicht eine unbegrenzte Paketgröße. `-s 96` garantiert weder ausschließlich Header noch vollständige Header. `-Z` gibt erhöhte Rechte nach dem Öffnen der Aufzeichnungsquelle und vor dem Öffnen der Ausgabedateien ab, nicht erst am Ende. [tcpdump-Handbuch](https://github.com/the-tcpdump-group/tcpdump/blob/master/tcpdump.1.in).

> **Filter und Interpretation:** Das Beispiel „nur eingehender Datenverkehr“ enthält `$(...)` in einfachen Shell-Anführungszeichen; darin erfolgt keine Befehlssubstitution. Die Originalzeile bleibt unverändert. Zieladresse, Erfassungsrichtung und Sichtbarkeit am Interface sind unterschiedliche Kriterien. Der HTTP-Filter mit `ip[...]` betrifft IPv4; einzelne Paketpräfixe erfassen nicht jede segmentierte HTTP-Nachricht. Port 443 entschlüsselt kein TLS. SYN-Pakete allein beweisen keinen Portscan. [Offizielle libpcap-Filtersyntax](https://github.com/the-tcpdump-group/libpcap/blob/master/pcap-filter.manmisc.in).

> **Umgebung:** Schnittstellen, Berechtigungen und installierte Werkzeuge hängen vom Betriebssystem ab. Die allgemeine Syntax ist eine Schablone. Befehle, BPF-Ausdrücke, Pfade und Werte wurden weder verändert noch ausgeführt.
