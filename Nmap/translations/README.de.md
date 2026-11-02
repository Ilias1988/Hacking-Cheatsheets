# 🔍 Nmap – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   __                      
   / | / /___ ___  ____ _____  
  /  |/ / __ `__ \/ __ `/ __ \ 
 / /|  / / / / / / /_/ / /_/ / 
/_/ |_/_/ /_/ /_/\__,_/ .___/  
                     /_/       
    Network Mapper – der Netzwerkscanner
```

<p align="center">
  <img src="https://img.shields.io/badge/Nmap-Network_Scanner-blue?style=for-the-badge" alt="Nmap">
  <img src="https://img.shields.io/badge/Reconnaissance-red?style=for-the-badge" alt="Aufklärung">
  <img src="https://img.shields.io/badge/Port_Scanning-green?style=for-the-badge" alt="Portscans">
  <img src="https://img.shields.io/badge/NSE_Scripts-purple?style=for-the-badge" alt="NSE-Skripte">
</p>

<p align="center">
  <b>🌐 Ein leistungsfähiges Werkzeug zum Scannen und Erkunden von Netzwerken</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Grundsyntax](#-grundsyntax)
- [Ziele angeben](#-ziele-angeben)
- [Erkennung erreichbarer Hosts](#-erkennung-erreichbarer-hosts)
- [Techniken für Portscans](#-techniken-für-portscans)
- [Ports angeben](#-ports-angeben)
- [Dienst- und Versionserkennung](#-dienst--und-versionserkennung)
- [Betriebssystemerkennung](#-betriebssystemerkennung)
- [Zeitverhalten und Leistung](#-zeitverhalten-und-leistung)
- [NSE-Skripte](#-nse-skripte)
- [Ausgabeformate](#-ausgabeformate)
- [Umgehung von Firewall und IDS](#-umgehung-von-firewall-und-ids)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**Nmap** (Network Mapper) ist ein freies, quelloffenes Werkzeug zur Erkundung von Netzwerken und für Sicherheitsprüfungen. Es gehört zu den etablierten Werkzeugen für Netzwerkscans.

### Wichtige Funktionen

| Funktion | Beschreibung |
|---------|-------------|
| **Erkennung erreichbarer Hosts** | Erreichbare Hosts im Netzwerk ermitteln |
| **Portscans** | Offene Ports und Dienste erkennen |
| **Versionserkennung** | Dienstversionen ermitteln |
| **Betriebssystemerkennung** | Betriebssysteme erkennen |
| **NSE-Skripte** | Schwachstellenscans und Ausnutzung von Schwachstellen |
| **Ausgabeformate** | XML, grepbare und normale Ausgabe |

### Portzustände

| Zustand | Beschreibung |
|-------|-------------|
| `open` | Eine Anwendung nimmt Verbindungen an |
| `closed` | Port erreichbar, aber keine Anwendung lauscht |
| `filtered` | Nmap kann nicht bestimmen, ob der Port offen ist (Filter/Firewall) |
| `unfiltered` | Port erreichbar, genauer Zustand unbekannt |
| `open\|filtered` | Offen oder gefiltert lässt sich nicht unterscheiden |
| `closed\|filtered` | Geschlossen oder gefiltert lässt sich nicht unterscheiden |

---

## 📥 Installation

### Kali Linux (vorinstalliert)
```bash
# Nmap aktualisieren
sudo apt update && sudo apt install nmap
```

### Ubuntu/Debian
```bash
sudo apt install nmap
```

### CentOS/RHEL
```bash
sudo yum install nmap
```

### Windows
```bash
# Von https://nmap.org/download.html herunterladen
# Alternativ Chocolatey verwenden
choco install nmap
```

### macOS
```bash
brew install nmap
```

### Installation prüfen
```bash
nmap --version
nmap -h          # Hilfe
```

---

<a name="-grundsyntax"></a>

## ⌨️ Grundsyntax

### Allgemeine Syntax
```bash
nmap [Scan Type] [Options] {target}
```

### Beispiele für den Einstieg
```bash
# Grundlegender Scan
nmap 192.168.1.1

# Mehrere Hosts scannen
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Einen Adressbereich scannen
nmap 192.168.1.1-254

# Gesamtes Subnetz scannen
nmap 192.168.1.0/24

# Ziele aus einer Datei scannen
nmap -iL targets.txt
```

---

## 🎯 Ziele angeben

### Einzelne Ziele
```bash
# IP-Adresse
nmap 192.168.1.1

# Hostname
nmap scanme.nmap.org

# Domain
nmap example.com
```

### Mehrere Ziele
```bash
# Durch Leerzeichen getrennt
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Bereich
nmap 192.168.1.1-100

# CIDR-Schreibweise
nmap 192.168.1.0/24

# Platzhaltermuster
nmap 192.168.1.*

# Bereich innerhalb eines Oktetts
nmap 192.168.0-255.1-254

# Kombiniert
nmap 192.168.1.0/24 10.0.0.1
```

### Eingabe aus einer Datei
```bash
# Aus einer Datei lesen
nmap -iL targets.txt

# targets.txt:
# 192.168.1.1
# 192.168.1.0/24
# scanme.nmap.org
```

### Ziele ausschließen
```bash
# Einzelnen Host ausschließen
nmap 192.168.1.0/24 --exclude 192.168.1.1

# Ausschlüsse aus einer Datei lesen
nmap 192.168.1.0/24 --excludefile exclude.txt
```

### Zufällige Ziele
```bash
# Zufällige Hosts scannen (laut Vorlage für Forschungszwecke)
nmap -iR 100
```

---

## 🔎 Erkennung erreichbarer Hosts

### Optionen zur Host-Erkennung

| Option | Beschreibung |
|--------|-------------|
| `-sL` | List-Scan – Ziele lediglich auflisten |
| `-sn` | Host-Erkennung ohne Portscan |
| `-Pn` | Host-Erkennung überspringen (alle Hosts als erreichbar behandeln) |
| `-PS` | TCP-SYN-Ping |
| `-PA` | TCP-ACK-Ping |
| `-PU` | UDP-Ping |
| `-PE` | ICMP-Echo-Ping |
| `-PP` | ICMP-Zeitstempel-Ping |
| `-PM` | ICMP-Netzmasken-Ping |
| `-PO` | IP-Protokoll-Ping |
| `-PR` | ARP-Ping (lokales Netzwerk) |

### Host-Erkennung (ohne Portscan)
```bash
# Nur erreichbare Hosts ermitteln
nmap -sn 192.168.1.0/24

# Host-Erkennung mit einer Hostliste
nmap -sn -iL hosts.txt
```

### Host-Erkennung überspringen
```bash
# Alle Hosts scannen (keine vorgelagerte Erreichbarkeitsprüfung)
nmap -Pn 192.168.1.1

# Nützlich, wenn:
# - Hosts ICMP blockieren
# - durch eine Firewall gescannt wird
```

### TCP-SYN-/ACK-Ping
```bash
# TCP-SYN-Ping an Port 80
nmap -PS80 192.168.1.1

# TCP-ACK-Ping an Port 80
nmap -PA80 192.168.1.1

# Mehrere Ports
nmap -PS22,80,443 192.168.1.1
```

### UDP-Ping
```bash
# UDP-Ping
nmap -PU53 192.168.1.1

# Mehrere Ports
nmap -PU53,161 192.168.1.1
```

### ICMP-Ping
```bash
# ICMP-Echo
nmap -PE 192.168.1.1

# ICMP-Zeitstempel
nmap -PP 192.168.1.1

# ICMP-Netzmaske
nmap -PM 192.168.1.1
```

### ARP-Ping (lokales Netzwerk)
```bash
# ARP-Scan (laut Vorlage im lokalen Netzwerk am schnellsten)
nmap -PR 192.168.1.0/24

# Nur ARP-Host-Erkennung
nmap -sn -PR 192.168.1.0/24
```

### DNS-Auflösung deaktivieren
```bash
# Keine DNS-Auflösung (schneller)
nmap -n 192.168.1.1

# DNS-Namen immer auflösen
nmap -R 192.168.1.1
```

---

## 🔌 Techniken für Portscans

### Überblick über Scan-Typen

| Option | Name | Beschreibung |
|--------|------|-------------|
| `-sS` | SYN-Scan | Als unauffällig bezeichneter Scan (Standard mit passenden Rechten, benötigt unter Unix root) |
| `-sT` | TCP-Connect | Vollständiger TCP-Verbindungsaufbau (kein root erforderlich) |
| `-sU` | UDP-Scan | UDP-Ports scannen |
| `-sA` | ACK-Scan | Firewall-Regeln untersuchen |
| `-sW` | Window-Scan | Ähnlich dem ACK-Scan; nutzt das Window-Feld |
| `-sM` | Maimon-Scan | FIN-/ACK-Prüfpaket |
| `-sN` | NULL-Scan | Keine Flags gesetzt |
| `-sF` | FIN-Scan | FIN-Flag gesetzt |
| `-sX` | Xmas-Scan | FIN-, PSH- und URG-Flags |

### SYN-Scan (Standard mit passenden Rechten)
```bash
# Häufigster Scan (unter Unix root erforderlich)
sudo nmap -sS 192.168.1.1

# Schließt den TCP-Handshake nicht vollständig ab
# Laut Vorlage schnell und unauffällig
```

### TCP-Connect-Scan
```bash
# Vollständiger TCP-Verbindungsaufbau
nmap -sT 192.168.1.1

# Keine root-Rechte erforderlich
# Laut Vorlage leichter erkennbar, aber immer funktionsfähig
```

### UDP-Scan
```bash
# UDP-Ports scannen
sudo nmap -sU 192.168.1.1

# UDP-Scans können langsam sein
# Mit der Versionserkennung kombinieren
sudo nmap -sU -sV 192.168.1.1

# Häufige UDP-Ports
sudo nmap -sU -p 53,67,68,69,123,161,500 192.168.1.1
```

### Kombinierte TCP-/UDP-Scans
```bash
# TCP und UDP scannen
sudo nmap -sS -sU 192.168.1.1

# Bestimmte Ports
sudo nmap -sS -sU -p T:80,443,U:53,161 192.168.1.1
```

### NULL-, FIN- und Xmas-Scans
```bash
# NULL-Scan (keine Flags)
sudo nmap -sN 192.168.1.1

# FIN-Scan (nur FIN-Flag)
sudo nmap -sF 192.168.1.1

# Xmas-Scan (FIN, PSH, URG)
sudo nmap -sX 192.168.1.1

# Laut Vorlage geeignet für:
# - Umgehen zustandsloser Firewalls
# - Funktioniert laut Vorlage nicht unter Windows
```

### ACK-Scan
```bash
# Firewall-Regeln untersuchen
sudo nmap -sA 192.168.1.1

# Ermitteln:
# - Welche Ports gefiltert sind
# - Firewall-Regeln
# Ergebnis: unfiltered oder filtered
```

### Window-Scan
```bash
# Ähnlich dem ACK-Scan
sudo nmap -sW 192.168.1.1

# Nutzt die TCP-Fenstergröße zur Zustandsermittlung
```

### Idle-/Zombie-Scan
```bash
# Laut Vorlage besonders unauffällig – einen Zombie-Host verwenden
sudo nmap -sI zombie_host 192.168.1.1

# Die eigene IP-Adresse kontaktiert laut Vorlage das Ziel nicht
# Setzt einen geeigneten Zombie-Host voraus
```

---

<a name="-ports-angeben"></a>

## 🎚️ Ports angeben

### Portoptionen

| Option | Beschreibung |
|--------|-------------|
| `-p <port>` | Bestimmte Ports scannen |
| `-p-` | Alle 65535 Ports scannen |
| `-p 1-1000` | Einen Portbereich scannen |
| `--top-ports <n>` | Die N häufigsten Ports scannen |
| `-F` | Schneller Scan (100 häufigste Ports) |
| `-r` | Ports der Reihe nach scannen |

### Bestimmte Ports
```bash
# Einzelner Port
nmap -p 80 192.168.1.1

# Mehrere Ports
nmap -p 80,443,22 192.168.1.1

# Portbereich
nmap -p 1-1000 192.168.1.1

# Alle Ports
nmap -p- 192.168.1.1

# Kombiniert
nmap -p 22,80,443,1000-2000 192.168.1.1
```

### Ports nach Protokoll angeben
```bash
# TCP-Ports
nmap -p T:80,443 192.168.1.1

# UDP-Ports
nmap -p U:53,161 192.168.1.1

# Beide
nmap -p T:80,443,U:53 192.168.1.1
```

### Häufigste Ports
```bash
# Die 100 häufigsten Ports (schnell)
nmap -F 192.168.1.1

# Die 10 häufigsten Ports
nmap --top-ports 10 192.168.1.1

# Die 1000 häufigsten Ports (Standard)
nmap --top-ports 1000 192.168.1.1
```

### Kurzreferenz häufiger Ports

| Port | Dienst |
|------|---------|
| 21 | FTP |
| 22 | SSH |
| 23 | Telnet |
| 25 | SMTP |
| 53 | DNS |
| 80 | HTTP |
| 110 | POP3 |
| 135 | MSRPC |
| 139 | NetBIOS |
| 143 | IMAP |
| 443 | HTTPS |
| 445 | SMB |
| 993 | IMAPS |
| 995 | POP3S |
| 1433 | MSSQL |
| 1521 | Oracle |
| 3306 | MySQL |
| 3389 | RDP |
| 5432 | PostgreSQL |
| 5900 | VNC |
| 8080 | HTTP-Proxy |

---

## 🔬 Dienst- und Versionserkennung

### Optionen der Versionserkennung

| Option | Beschreibung |
|--------|-------------|
| `-sV` | Versionserkennung |
| `--version-intensity <0-9>` | Intensität der Prüfanfragen |
| `--version-light` | Reduzierte Versionserkennung (Intensität 2) |
| `--version-all` | Alle Prüfanfragen versuchen (Intensität 9) |
| `--version-trace` | Versionserkennung mit Diagnoseausgabe |

### Grundlegende Versionserkennung
```bash
# Dienstversionen ermitteln
nmap -sV 192.168.1.1

# Typische Ausgabe:
# PORT    STATE SERVICE VERSION
# 22/tcp  open  ssh     OpenSSH 7.9
# 80/tcp  open  http    Apache httpd 2.4.38
```

### Intensität der Versionserkennung
```bash
# Reduzierter Scan (schneller)
nmap -sV --version-light 192.168.1.1

# Alle Prüfanfragen (gründlich)
nmap -sV --version-all 192.168.1.1

# Eigene Intensität (0-9)
nmap -sV --version-intensity 5 192.168.1.1
```

---

## 💻 Betriebssystemerkennung

### Optionen der Betriebssystemerkennung

| Option | Beschreibung |
|--------|-------------|
| `-O` | Betriebssystemerkennung aktivieren |
| `--osscan-limit` | Nur aussichtsreiche Hosts untersuchen |
| `--osscan-guess` | Betriebssystem mit großzügigerer Übereinstimmung schätzen |
| `--max-os-tries` | Maximale Versuche der Betriebssystemerkennung |

### Grundlegende Betriebssystemerkennung
```bash
# Betriebssystem erkennen (unter Unix root erforderlich)
sudo nmap -O 192.168.1.1

# Beispielausgabe:
# Running: Linux 3.X|4.X
# OS CPE: cpe:/o:linux:linux_kernel:3
# OS details: Linux 3.10 - 4.11
```

### Großzügigere Betriebssystemschätzung
```bash
# Großzügigere Schätzung
sudo nmap -O --osscan-guess 192.168.1.1
```

### Erweiterter Scan (-A)
```bash
# Betriebssystemerkennung, Versionserkennung, Skripte und Traceroute aktivieren
sudo nmap -A 192.168.1.1

# Entspricht: -O -sV -sC --traceroute
```

---

<a name="-zeitverhalten-und-leistung"></a>

## ⏱️ Zeitverhalten und Leistung

### Vorlagen für das Zeitverhalten

| Option | Name | Beschreibung |
|--------|------|-------------|
| `-T0` | Paranoid | Laut Vorlage zur IDS-Umgehung, sehr langsam |
| `-T1` | Sneaky | Laut Vorlage zur IDS-Umgehung, langsam |
| `-T2` | Polite | Weniger Bandbreite, langsamer |
| `-T3` | Normal | Standard-Zeitverhalten |
| `-T4` | Aggressive | Schnell; für zuverlässige Netzwerke |
| `-T5` | Insane | Sehr schnell, kann Ports übersehen |

### Verwendung
```bash
# Laut Vorlage langsam und unauffällig
nmap -T1 192.168.1.1

# Normal (Standard)
nmap -T3 192.168.1.1

# Schnell
nmap -T4 192.168.1.1

# Sehr schnell (lokales Netzwerk)
nmap -T5 192.168.1.1
```

### Zeitverhalten im Detail steuern

| Option | Beschreibung |
|--------|-------------|
| `--min-rate <n>` | Minimale Pakete pro Sekunde |
| `--max-rate <n>` | Maximale Pakete pro Sekunde |
| `--min-parallelism <n>` | Minimale Zahl paralleler Prüfanfragen |
| `--max-parallelism <n>` | Maximale Zahl paralleler Prüfanfragen |
| `--min-hostgroup <n>` | Minimale Zahl parallel untersuchter Hosts |
| `--max-hostgroup <n>` | Maximale Zahl parallel untersuchter Hosts |
| `--host-timeout <time>` | Untersuchung eines Hosts nach dieser Zeit abbrechen |
| `--scan-delay <time>` | Verzögerung zwischen Prüfanfragen |
| `--max-retries <n>` | Maximale Zahl wiederholter Prüfanfragen |

```bash
# Mindestens 1000 Pakete pro Sekunde senden
nmap --min-rate 1000 192.168.1.0/24

# Höchstens 100 Pakete pro Sekunde (verlangsamen)
nmap --max-rate 100 192.168.1.1

# Untersuchung eines Hosts nach 30 Minuten abbrechen
nmap --host-timeout 30m 192.168.1.0/24

# Verzögerung zwischen Prüfanfragen
nmap --scan-delay 1s 192.168.1.1
```

---

## 📜 NSE-Skripte

### Skriptkategorien

| Kategorie | Beschreibung |
|----------|-------------|
| `auth` | Umgehung der Authentifizierung |
| `broadcast` | Hosts über Broadcast ermitteln |
| `brute` | Brute-Force-Angriffe |
| `default` | Standardskripte (-sC) |
| `discovery` | Dienste ermitteln |
| `dos` | Dienstverweigerung (DoS) |
| `exploit` | Schwachstellen ausnutzen |
| `external` | Externe Dienste abfragen |
| `fuzzer` | Fuzzing (Eingaben systematisch variieren) |
| `intrusive` | Kann Dienste zum Absturz bringen |
| `malware` | Schadsoftware erkennen |
| `safe` | Als sicher eingestufte Skripte |
| `version` | Versionserkennung |
| `vuln` | Schwachstellen erkennen |

### Skriptoptionen

| Option | Beschreibung |
|--------|-------------|
| `-sC` | Standardskripte |
| `--script <scripts>` | Bestimmte Skripte ausführen |
| `--script-args <args>` | Skriptargumente |
| `--script-updatedb` | Skriptdatenbank aktualisieren |
| `--script-help <script>` | Skripthilfe |

### Standardskripte
```bash
# Standardskripte ausführen
nmap -sC 192.168.1.1

# Mit Versionserkennung (laut Vorlage empfohlen)
nmap -sV -sC 192.168.1.1
```

### Bestimmte Skripte ausführen
```bash
# Einzelnes Skript
nmap --script http-title 192.168.1.1

# Mehrere Skripte
nmap --script http-title,http-headers 192.168.1.1

# Kategorie
nmap --script vuln 192.168.1.1

# Platzhaltermuster
nmap --script "http-*" 192.168.1.1

# Kategorien kombinieren
nmap --script "vuln and safe" 192.168.1.1

# Skripte ausschließen
nmap --script "not intrusive" 192.168.1.1
```

### Häufig verwendete Skripte

#### Schwachstellenscans
```bash
# Alle Skripte der Kategorie vuln
nmap --script vuln 192.168.1.1

# Bestimmte CVE prüfen
nmap --script smb-vuln-ms17-010 192.168.1.1

# Heartbleed
nmap --script ssl-heartbleed 192.168.1.1

# ShellShock
nmap --script http-shellshock 192.168.1.1
```

#### HTTP-Skripte
```bash
# HTTP-Enumeration
nmap --script http-enum 192.168.1.1

# HTTP-Header
nmap --script http-headers 192.168.1.1

# HTTP-Methoden
nmap --script http-methods 192.168.1.1

# HTTP-Seitentitel
nmap --script http-title 192.168.1.1

# Robots.txt
nmap --script http-robots.txt 192.168.1.1
```

#### SMB-Skripte
```bash
# SMB-Enumeration
nmap --script smb-enum-shares 192.168.1.1
nmap --script smb-enum-users 192.168.1.1
nmap --script smb-os-discovery 192.168.1.1

# SMB-Schwachstellen
nmap --script smb-vuln* 192.168.1.1
```

#### SSH-Skripte
```bash
# SSH-Authentifizierungsverfahren
nmap --script ssh-auth-methods 192.168.1.1

# SSH-Hostschlüssel
nmap --script ssh-hostkey 192.168.1.1

# SSH-Brute-Force
nmap --script ssh-brute 192.168.1.1
```

#### DNS-Skripte
```bash
# DNS-Brute-Force
nmap --script dns-brute example.com

# DNS-Zonenübertragung
nmap --script dns-zone-transfer example.com
```

#### MySQL-/Datenbankskripte
```bash
# MySQL-Informationen
nmap --script mysql-info 192.168.1.1

# MySQL-Enumeration
nmap --script mysql-enum 192.168.1.1

# MySQL-Brute-Force
nmap --script mysql-brute 192.168.1.1
```

### Skriptargumente
```bash
# Brute Force mit einer Wortliste
nmap --script ssh-brute --script-args userdb=users.txt,passdb=passwords.txt 192.168.1.1

# Brute Force gegen HTTP-Authentifizierung
nmap --script http-brute --script-args http-brute.path=/admin 192.168.1.1
```

---

## 📄 Ausgabeformate

### Ausgabeoptionen

| Option | Beschreibung |
|--------|-------------|
| `-oN <file>` | Normale Ausgabe |
| `-oX <file>` | XML-Ausgabe |
| `-oG <file>` | Grepbare Ausgabe |
| `-oA <basename>` | Alle Formate |
| `-oS <file>` | Script-Kiddie-Ausgabe |

### Ausgabe speichern
```bash
# Normale Ausgabe
nmap -oN scan.txt 192.168.1.1

# XML-Ausgabe (für andere Werkzeuge)
nmap -oX scan.xml 192.168.1.1

# Grepbare Ausgabe
nmap -oG scan.gnmap 192.168.1.1

# Alle Formate gleichzeitig
nmap -oA scan 192.168.1.1
# Erstellt: scan.nmap, scan.xml, scan.gnmap
```

### Ausführlichkeit und Diagnose

| Option | Beschreibung |
|--------|-------------|
| `-v` | Ausführlichere Ausgabe |
| `-vv` | Noch ausführlichere Ausgabe |
| `-d` | Diagnosemodus |
| `-dd` | Zusätzliche Diagnoseausgabe |
| `--reason` | Grund für den Portzustand anzeigen |
| `--open` | Nur offene Ports anzeigen |
| `--packet-trace` | Alle Pakete anzeigen |

```bash
# Ausführliche Ausgabe
nmap -v 192.168.1.1

# Sehr ausführlich
nmap -vv 192.168.1.1

# Nur offene Ports anzeigen
nmap --open 192.168.1.1

# Begründung anzeigen
nmap --reason 192.168.1.1
```

---

<a name="-umgehung-von-firewall-und-ids"></a>

## 🛡️ Umgehung von Firewall und IDS

### Techniken zur Umgehung der Erkennung

| Option | Beschreibung |
|--------|-------------|
| `-f` | Pakete fragmentieren |
| `--mtu <size>` | Eigene MTU-Größe |
| `-D <decoys>` | Täuschhosts (Decoys) verwenden |
| `-S <IP>` | Quell-IP-Adresse fälschen |
| `--source-port <port>` | Quellport vorgeben |
| `--data-length <n>` | Zufallsdaten anhängen |
| `--randomize-hosts` | Zufällige Host-Reihenfolge |
| `--spoof-mac <mac>` | MAC-Adresse fälschen |
| `--badsum` | Fehlerhafte Prüfsummen senden |

### Paketfragmentierung
```bash
# Pakete fragmentieren
nmap -f 192.168.1.1

# Eigene MTU
nmap --mtu 16 192.168.1.1
```

### Täuschhosts (Decoys)
```bash
# Täuschhosts (Decoys) verwenden
nmap -D decoy1,decoy2,ME 192.168.1.1

# Zufällige Täuschhosts
nmap -D RND:5 192.168.1.1
```

### Quell-IP-/Port-Spoofing
```bash
# Quell-IP-Adresse fälschen
nmap -S 192.168.1.100 -e eth0 192.168.1.1

# Quellport vorgeben (häufig zugelassene Ports)
nmap --source-port 53 192.168.1.1
nmap --source-port 80 192.168.1.1
```

### MAC-Spoofing
```bash
# Zufällige MAC-Adresse
nmap --spoof-mac 0 192.168.1.1

# Bestimmter Hersteller
nmap --spoof-mac Apple 192.168.1.1

# Bestimmte MAC-Adresse
nmap --spoof-mac 00:11:22:33:44:55 192.168.1.1
```

### Zusätzliche Daten anhängen
```bash
# Zufallsdaten an Pakete anhängen
nmap --data-length 25 192.168.1.1
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: Schnelle Erreichbarkeitsprüfung im Netzwerk
```bash
# Erreichbare Hosts ermitteln
nmap -sn 192.168.1.0/24
```

### Beispiel 2: Grundlegender Portscan
```bash
# Scan mit Versionserkennung
nmap -sV 192.168.1.1
```

### Beispiel 3: Umfassender Scan
```bash
# Umfassender Scan mit Betriebssystemerkennung und Skripten
sudo nmap -sS -sV -O -sC 192.168.1.1
```

### Beispiel 4: Erweiterter vollständiger Scan
```bash
# Alle Ports, erweiterte Erkennung
sudo nmap -A -T4 -p- 192.168.1.1
```

### Beispiel 5: Schwachstellenscan
```bash
# Auf Schwachstellen prüfen
nmap --script vuln 192.168.1.1
```

### Beispiel 6: Laut Vorlage unauffälliger Scan
```bash
# Laut Vorlage unauffälliger Scan
sudo nmap -sS -T2 -f --data-length 25 192.168.1.1
```

### Beispiel 7: Webserverscan
```bash
# Umfassender Webscan
nmap -sV -p 80,443,8080,8443 --script "http-*" 192.168.1.1
```

### Beispiel 8: SMB-Enumeration
```bash
# Umfassende SMB-Enumeration
nmap -p 445 --script smb-enum-shares,smb-enum-users,smb-vuln* 192.168.1.1
```

### Beispiel 9: Vollständiger Subnetzscan
```bash
# Gesamtes Subnetz scannen
nmap -sS -sV -O -T4 --open 192.168.1.0/24 -oA subnet_scan
```

### Beispiel 10: UDP-Dienste
```bash
# Häufige UDP-Dienste
sudo nmap -sU -sV -p 53,67,68,69,123,161,500 192.168.1.1
```

---

## 📊 Kurzreferenz

### Wichtige Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `nmap <target>` | Grundlegender Scan |
| `nmap -sn <target>` | Nur Host-Erkennung |
| `nmap -Pn <target>` | Host-Erkennung überspringen |
| `nmap -sS <target>` | SYN-Scan |
| `nmap -sV <target>` | Versionserkennung |
| `nmap -O <target>` | Betriebssystemerkennung |
| `nmap -A <target>` | Erweiterter Scan |
| `nmap -sC <target>` | Standardskripte |
| `nmap -p- <target>` | Alle Ports |
| `nmap --script vuln <target>` | Schwachstellenscan |

### Geschwindigkeit und Unauffälligkeit

| Anforderung | Optionen |
|------|---------|
| **Schnell** | `-T4 --min-rate 1000` |
| **Laut Vorlage unauffällig** | `-T1 -f --data-length 25` |
| **Ausgewogen** | `-T3` (Standard) |

### Häufige Scans

```bash
# Schneller Scan
nmap -F 192.168.1.1

# Vollständiger TCP-Scan
nmap -sT -p- 192.168.1.1

# Dienstscan
nmap -sV 192.168.1.1

# Schwachstellenscan
nmap --script vuln 192.168.1.1

# Umfassende Erkennung
sudo nmap -A -T4 -p- 192.168.1.1
```

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise zur Leistung

1. **SYN-Scan verwenden (Standard mit passenden Rechten)**
   ```bash
   # Benötigt unter Unix root; laut Vorlage am schnellsten
   sudo nmap -sS 192.168.1.1
   ```

2. **Portbereich zunächst begrenzen**
   ```bash
   # Mit häufigen Ports beginnen
   nmap --top-ports 100 192.168.1.1
   ```

3. **Geschwindigkeit in lokalen Netzwerken erhöhen**
   ```bash
   nmap -T5 --min-rate 5000 192.168.1.0/24
   ```

### Hinweise zur Genauigkeit

1. **Mehrere Scan-Typen**
   ```bash
   # TCP und UDP
   sudo nmap -sS -sU -sV 192.168.1.1
   ```

2. **In unzuverlässigen Netzwerken mehr Wiederholungen zulassen**
   ```bash
   nmap --max-retries 3 192.168.1.1
   ```

### Rechtliche Aspekte

> ⚠️ **WICHTIG:** Nur eigene Netzwerke oder Netzwerke mit ausdrücklicher Scan-Genehmigung untersuchen.

- ✅ Eigene Netzwerke
- ✅ Laborumgebungen
- ✅ Autorisierte Penetrationstests
- ❌ Zufällige Hosts im Internet
- ❌ Netzwerke ohne Genehmigung

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** Nmap ist ein leistungsfähiges Werkzeug und darf nur für **autorisierte Scans** verwendet werden.
> 
> - ✅ Eigene Netzwerke scannen
> - ✅ Mit ausdrücklicher schriftlicher Genehmigung scannen
> - ✅ Im Rahmen autorisierter Penetrationstests einsetzen
> - ❌ Niemals Netzwerke ohne Genehmigung scannen
> - ❌ Nicht autorisierte Scans können rechtswidrig sein
> 
> **Nicht autorisierte Netzwerkscans sind in vielen Rechtsordnungen rechtswidrig.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [Offizielle Nmap-Website](https://nmap.org/)
- [Nmap-Referenzhandbuch](https://nmap.org/book/man.html)
- [NSE-Skriptdokumentation](https://nmap.org/nsedoc/)

### Lernmaterial
- [Buch „Nmap Network Scanning“](https://nmap.org/book/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Verwandte Spickzettel
- [Metasploit Framework](../../Metasploit/translations/README.de.md)
- [SQLMap](../../SQLMap/README.md)
- [Burp Suite](../../Burp-Suite/README.md)

---

<p align="center">
  <b>🔍 Netzwerkaufklärung sicher beherrschen!</b><br>
  <i>Das eigene Netzwerk kennen, bevor Angreifer es tun</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Host-Erkennung und Protokolle:** `-sn` unterdrückt den Portscan; Optionen wie `-PS`, `-PU` oder `-PR` allein tun dies nicht. `-Pn` überspringt die normale Host-Erkennung, kann im lokalen Ethernet aber weiterhin ARP beziehungsweise Neighbor Discovery verwenden. `-sL` kann DNS-Abfragen auslösen und ist nicht pauschal ohne Netzwerkverkehr. `-p-` umfasst die Ports 1 bis 65535 und schließt Port 0 nicht ein. `U:` in einer Portliste aktiviert keinen UDP-Scan; dafür ist ein entsprechender Scan-Typ erforderlich. [Nmap: Host Discovery](https://nmap.org/book/man-host-discovery.html), [Portangaben](https://nmap.org/book/man-port-specification.html).

> **Rechte, Zuverlässigkeit und Erkennung:** Der Standard ist ein SYN-Scan nur mit geeigneten Raw-Packet-Rechten; andernfalls verwendet Nmap einen Connect-Scan. Ein SYN-Scan ist nicht unsichtbar, und ein Connect-Scan funktioniert nicht unabhängig von Routing, Filtern und Zielverhalten „immer“. Die Aussagen zu Geschwindigkeit, Betriebssystemen, Zombie-Hosts und IDS-Umgehung sind vereinfachte Beschreibungen; kein einzelner Schalter garantiert eine Umgehung. Die Option `--max-retries 3` setzt eine Obergrenze und erhöht den Standard nicht zwangsläufig. [Scan-Techniken](https://nmap.org/book/man-port-scanning-techniques.html), [Firewall-/IDS-Optionen](https://nmap.org/book/man-bypass-firewalls-ids.html).

> **NSE und Ausgaben:** `auth` umfasst mehr als Authentifizierungsumgehungen; die Kategorie untersucht Authentifizierung. `default`, `safe` und `vuln` sind keine Zusage, dass jeder Aufruf wirkungslos oder störungsfrei ist. `--script-updatedb` erstellt den lokalen Skriptindex neu und lädt keine neuen Skripte herunter. `-oA` schreibt die drei üblichen Dateiformate, nicht alle fünf Ausgabearten. Grepbare Ausgabe ist veraltet; XML unterstützt die vollständige Weiterverarbeitung. `--open` kann auch möglicherweise offene Zustände zeigen. Versions- und Betriebssystemangaben in den Beispielen sind historische Musterwerte, keine aktuellen Prüfergebnisse. [NSE-Referenz](https://nmap.org/book/man-nse.html), [Ausgabeformate](https://nmap.org/book/man-output.html).

> **Shell-Kontext:** Nicht zitierte `*`-Muster können bereits von der Shell expandiert werden; `targets.txt`, Wortlisten und Bezeichner wie `zombie_host` werden als Eingaben vorausgesetzt. Die allgemeine Syntax mit eckigen und geschweiften Klammern ist eine Syntaxschablone, kein direkt ausführbarer Aufruf. Alle ursprünglichen Befehle, Parameter und Beispielausgaben bleiben unverändert; kein Scan wurde ausgeführt.
