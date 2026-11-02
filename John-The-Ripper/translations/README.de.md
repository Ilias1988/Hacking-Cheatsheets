# 🔨 John the Ripper – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
       __      __         
      / /___  / /_  ____  
 __  / / __ \/ __ \/ __ \ 
/ /_/ / /_/ / / / / / / / 
\____/\____/_/ /_/_/ /_/  
  Werkzeug zur Passwortwiederherstellung     
```

<p align="center">
  <img src="https://img.shields.io/badge/John-Password_Cracker-red?style=for-the-badge" alt="John">
  <img src="https://img.shields.io/badge/Hash-Cracking-orange?style=for-the-badge" alt="Hash">
  <img src="https://img.shields.io/badge/Open-Source-blue?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Multi-Platform-green?style=for-the-badge" alt="Multi Platform">
</p>

<p align="center">
  <b>🔑 Das bekannte Werkzeug zur Prüfung und Wiederherstellung von Passwörtern</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Grundsyntax](#-grundsyntax)
- [Modi zur Passwortsuche](#-modi-zur-passwortsuche)
- [Hashwerte extrahieren](#-hashwerte-extrahieren)
- [Häufige Hashverfahren](#-häufige-hashverfahren)
- [Linux-Passwörter wiederherstellen](#-linux-passwörter-wiederherstellen)
- [Windows-Passwörter wiederherstellen](#-windows-passwörter-wiederherstellen)
- [Dateipasswörter wiederherstellen](#-dateipasswörter-wiederherstellen)
- [Regeln und Wortvarianten](#-regeln-und-wortvarianten)
- [Sitzungsverwaltung](#-sitzungsverwaltung)
- [Leistungsoptimierung](#-leistungsoptimierung)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**John the Ripper** (John/JtR) ist ein kostenloses, quelloffenes Werkzeug zur Prüfung und Wiederherstellung von Passwörtern. Es dient dazu, schwache Passwörter zu erkennen und zu Hashwerten passende Passwörter zu ermitteln.

### Wichtige Funktionen

| Eigenschaft | Beschreibung |
|---------|-------------|
| **Viele Formate** | Unterstützt Hunderte von Hashverfahren |
| **Mehrere Modi** | Single, Wordlist, Incremental |
| **Eigene Regeln** | Leistungsfähige Regeln zur Bildung von Wortvarianten |
| **Automatische Erkennung** | Automatische Erkennung des Hashformats |
| **Plattformübergreifend** | Linux, Windows, macOS |
| **GPU-Unterstützung** | OpenCL-Beschleunigung (Jumbo) |

### John im Vergleich zu Hashcat

| Eigenschaft | John | Hashcat |
|---------|------|---------|
| **Laut Vorlage besonders geeignet für** | Passwortsuche mit der CPU | Passwortsuche mit der GPU |
| **Bedienbarkeit** | Einfacher | Komplexer |
| **Automatische Erkennung** | ✅ Ja | ❌ Nein |
| **Regeln** | Leistungsfähig | Sehr leistungsfähig |
| **Hashverfahren** | Viele | Mehr |
| **Geschwindigkeit (GPU)** | Gut | Besser |

### Versionen

| Version | Beschreibung |
|---------|-------------|
| **John (Core)** | Grundversion |
| **John Jumbo** | Von der Community erweiterte Version (laut Vorlage empfohlen) |

---

## 📥 Installation

### Kali Linux (vorinstalliert)
```bash
john --version
```

### Ubuntu/Debian
```bash
# Standardversion
sudo apt install john

# Jumbo-Version (laut Vorlage empfohlen)
sudo apt install john-jumbo
```

### Aus dem Quellcode (Jumbo)
```bash
git clone https://github.com/openwall/john
cd john/src
./configure
make -s clean && make -sj4
cd ../run
./john --version
```

### macOS (Homebrew)
```bash
brew install john-jumbo
```

### Windows
```bash
# Von der offiziellen Website herunterladen
https://www.openwall.com/john/

# Alternativ Chocolatey verwenden
choco install john
```

### Installation prüfen
```bash
john --version
john --list=formats | wc -l    # Unterstützte Formate zählen
```

---

<a name="-grundsyntax"></a>

## ⌨️ Grundsyntax

### Allgemeine Syntax
```bash
john [options] [password_file]
```

### Häufige Optionen

| Option | Beschreibung |
|--------|-------------|
| `--wordlist=` | Pfad zur Wortliste |
| `--format=` | Hashformat angeben |
| `--rules` | Regeln zur Bildung von Wortvarianten aktivieren |
| `--show` | Gefundene Passwörter anzeigen |
| `--single` | Single-Crack-Modus |
| `--incremental` | Brute-Force-Modus |
| `--session=` | Sitzung benennen |
| `--restore` | Vorherige Sitzung fortsetzen |
| `--pot=` | Eigene Pot-Datei |

### Schnelleinstieg
```bash
# Format automatisch erkennen und Passwortsuche starten
john hashes.txt

# Mit Wortliste
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Gefundene Passwörter anzeigen
john --show hashes.txt
```

---

## 🎮 Modi zur Passwortsuche

### Single-Crack-Modus

Verwendet Anmeldenamen und GECOS-Informationen zur Bildung möglicher Passwörter.

```bash
# Single-Modus (laut Vorlage am schnellsten; prüft Varianten des Benutzernamens)
john --single hashes.txt

# Mit Formatangabe
john --single --format=sha512crypt hashes.txt
```

### Wortlistenmodus

Verwendet Wörter aus einer Liste, bei Bedarf zusammen mit Regeln.

```bash
# Einfache Wortlistensuche
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Wortliste mit Regeln
john --wordlist=wordlist.txt --rules hashes.txt

# Bestimmter Regelsatz
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
```

### Inkrementeller Modus (Brute Force)

Probiert Zeichenkombinationen innerhalb der konfigurierten Grenzen aus.

```bash
# Standardmäßig konfigurierter inkrementeller Modus (laut Vorlage alle Zeichen)
john --incremental hashes.txt

# Bestimmter Zeichensatz
john --incremental=Digits hashes.txt      # Nur Ziffern
john --incremental=Lower hashes.txt       # Nur Kleinbuchstaben
john --incremental=Upper hashes.txt       # Nur Großbuchstaben
john --incremental=Alpha hashes.txt       # Nur Buchstaben
john --incremental=Alnum hashes.txt       # Buchstaben und Ziffern
john --incremental=LowerNum hashes.txt    # Kleinbuchstaben und Ziffern
```

### Maskenmodus

Brute-Force-Suche anhand eines vorgegebenen Musters.

```bash
# Maskensuche (? = Platzhalter)
john --mask='?l?l?l?l?d?d' hashes.txt

# Platzhalter:
# ?l = Kleinbuchstaben    ?u = Großbuchstaben
# ?d = Ziffern             ?s = Sonderzeichen
# ?a = alle               ?h = Hexadezimalzeichen in Kleinschreibung
```

### Externer Modus

Eigene Algorithmen zur Passwortsuche.

```bash
john --external=Filter_Alpha hashes.txt
```

---

## 🔍 Hashwerte extrahieren

### Linux-Passwörter

```bash
# Dateien passwd und shadow zusammenführen
unshadow /etc/passwd /etc/shadow > unshadowed.txt

# Passwortsuche mit der zusammengeführten Datei
john unshadowed.txt
```

### Windows-Hashwerte

```bash
# Aus der SAM-Datei (mit pwdump, secretsdump usw.)
# Format: user:id:LM_hash:NTLM_hash:::

# Mit Metasploit hashdump
meterpreter> hashdump
Administrator:500:aad3b435b51404ee:31d6cfe0d16ae931:::

# In einer Datei speichern und Passwortsuche starten
john --format=nt hashes.txt
```

### Dateipasswörter (2john-Werkzeuge)

```bash
# ZIP-Dateien
zip2john protected.zip > zip_hash.txt

# RAR-Dateien
rar2john protected.rar > rar_hash.txt

# PDF-Dateien
pdf2john protected.pdf > pdf_hash.txt

# Office-Dokumente
office2john document.docx > office_hash.txt

# SSH-Schlüssel
ssh2john id_rsa > ssh_hash.txt

# 7zip-Dateien
7z2john archive.7z > 7z_hash.txt

# KeePass-Datenbanken
keepass2john database.kdbx > keepass_hash.txt
```

---

## 🔢 Häufige Hashverfahren

### Tabelle der Hashformate

| Hashverfahren | Formatoption | Beispiel |
|-----------|-------------|---------|
| MD5 | `--format=raw-md5` | `5d41402abc4b2a76b9719d911017c592` |
| SHA1 | `--format=raw-sha1` | `aaf4c61ddcc5e8a2...` |
| SHA256 | `--format=raw-sha256` | `2cf24dba5fb0a30e...` |
| SHA512 | `--format=raw-sha512` | `cf83e1357eefb8bd...` |
| NTLM | `--format=nt` | `31d6cfe0d16ae931b73c59d7e0c089c0` |
| LM | `--format=lm` | `aad3b435b51404ee` |
| MySQL | `--format=mysql-sha1` | `*2470C0C06DEE42...` |
| bcrypt | `--format=bcrypt` | `$2a$10$N9qo8u...` |
| Linux SHA512 | `--format=sha512crypt` | `$6$rounds=5000$...` |
| Linux SHA256 | `--format=sha256crypt` | `$5$rounds=5000$...` |
| Linux MD5 | `--format=md5crypt` | `$1$salt$hash` |

### Alle Formate auflisten
```bash
# Alle unterstützten Formate auflisten
john --list=formats

# Nach einem bestimmten Format suchen
john --list=formats | grep -i ntlm
john --list=formats | grep -i sha

# Formate zählen
john --list=formats | wc -l
```

### Hashverfahren erkennen
```bash
# hash-identifier verwenden
hash-identifier

# Alternativ hashid verwenden
hashid -m '$6$salt$hash...'
```

---

## 🐧 Linux-Passwörter wiederherstellen

### Hashwerte vorbereiten
```bash
# passwd und shadow zusammenführen
sudo unshadow /etc/passwd /etc/shadow > linux_hashes.txt

# Alternativ nur den Hashwert extrahieren
sudo cat /etc/shadow | grep username
```

### Beispiele für Hashformate
```
# Format von /etc/shadow:
# username:$id$salt$hash:...

# $1$ = MD5
# $5$ = SHA-256
# $6$ = SHA-512 (laut Vorlage auf modernen Linux-Systemen am häufigsten)
# $y$ = yescrypt (laut Vorlage am neuesten)
```

### Linux-Hashwerte prüfen
```bash
# Automatische Erkennung (laut Vorlage empfohlen)
john linux_hashes.txt

# Format angeben
john --format=sha512crypt linux_hashes.txt

# Mit Wortliste
john --wordlist=/usr/share/wordlists/rockyou.txt linux_hashes.txt

# Ergebnisse anzeigen
john --show linux_hashes.txt
```

---

## 🪟 Windows-Passwörter wiederherstellen

### Hashverfahren

| Art | Beschreibung | Format |
|------|-------------|--------|
| **LM** | Veraltet (schwach) | `--format=lm` |
| **NTLM** | Aktuelle Windows-Systeme laut Vorlage | `--format=nt` |
| **NTLMv2** | Netzwerkauthentifizierung | `--format=netntlmv2` |

### NTLM-Hashwerte prüfen
```bash
# Format der NTLM-Hashwerte:
# user:rid:lm_hash:ntlm_hash:::

# NTLM-Passwortsuche
john --format=nt windows_hashes.txt

# Mit Wortliste
john --format=nt --wordlist=rockyou.txt windows_hashes.txt

# Mit Regeln
john --format=nt --wordlist=rockyou.txt --rules windows_hashes.txt
```

### Windows-Hashwerte extrahieren
```bash
# Mit Metasploit
meterpreter> hashdump

# Mit secretsdump (Impacket)
secretsdump.py -sam sam -system system LOCAL

# Mit pwdump
pwdump7.exe
```

---

## 📄 Dateipasswörter wiederherstellen

### ZIP-Dateien
```bash
# Hashwert extrahieren
zip2john protected.zip > zip.hash

# Passwortsuche
john --wordlist=rockyou.txt zip.hash

# Passwort anzeigen
john --show zip.hash
```

### RAR-Dateien
```bash
rar2john protected.rar > rar.hash
john --wordlist=rockyou.txt rar.hash
```

### PDF-Dateien
```bash
pdf2john protected.pdf > pdf.hash
john --wordlist=rockyou.txt pdf.hash
```

### Office-Dokumente
```bash
# Word, Excel, PowerPoint
office2john document.docx > office.hash
john --wordlist=rockyou.txt office.hash
```

### Private SSH-Schlüssel
```bash
ssh2john id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### KeePass-Datenbank
```bash
keepass2john database.kdbx > keepass.hash
john --wordlist=rockyou.txt keepass.hash
```

---

<a name="-regeln-und-wortvarianten"></a>

## ⚙️ Regeln und Wortvarianten

### Regeln aktivieren
```bash
# Standardregeln
john --wordlist=wordlist.txt --rules hashes.txt

# Bestimmter Regelsatz
john --wordlist=wordlist.txt --rules=Single hashes.txt
john --wordlist=wordlist.txt --rules=Wordlist hashes.txt
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
john --wordlist=wordlist.txt --rules=KoreLogic hashes.txt
```

### Verfügbare Regeln auflisten
```bash
john --list=rules
```

### Häufige Regeloperationen

| Regel | Beschreibung | Beispiel |
|------|-------------|---------|
| `:` | Unverändert lassen | password → password |
| `l` | Alles kleinschreiben | PassWord → password |
| `u` | Alles großschreiben | password → PASSWORD |
| `c` | Anfangsbuchstaben großschreiben | password → Password |
| `r` | Zeichenfolge umkehren | password → drowssap |
| `d` | Verdoppeln | pass → passpass |
| `$X` | Zeichen X anhängen | pass → passX |
| `^X` | Zeichen X voranstellen | pass → Xpass |
| `sXY` | X durch Y ersetzen | password → pa$$word |

### Eigene Regeln
```bash
# In john.conf unter [List.Rules:Custom] hinzufügen
[List.Rules:Custom]
:
l
u
c
$1
$!
$123
^2023
```

---

## 💾 Sitzungsverwaltung

### Benannte Sitzung erstellen
```bash
john --wordlist=rockyou.txt --session=mycrack hashes.txt
```

### Sitzung fortsetzen
```bash
# Unterbrochene Sitzung fortsetzen
john --restore=mycrack

# Standardsitzung fortsetzen
john --restore
```

### Status prüfen
```bash
# Während der Passwortsuche eine Taste drücken, um den Status anzuzeigen
# Alternativ die .rec-Datei prüfen
cat ~/.john/mycrack.rec
```

### Pot-Datei
```bash
# Standard-Pot-Datei
~/.john/john.pot

# Eigene Pot-Datei
john --pot=custom.pot hashes.txt

# Alle gefundenen Passwörter anzeigen
john --show --pot=custom.pot hashes.txt
```

---

## 🚀 Leistungsoptimierung

### Fork (mehrere Prozesse)
```bash
# Mehrere CPU-Kerne verwenden
john --fork=4 --wordlist=rockyou.txt hashes.txt
```

### OpenCL (GPU)
```bash
# OpenCL-Geräte auflisten
john --list=opencl-devices

# GPU verwenden
john --format=sha512crypt-opencl --wordlist=rockyou.txt hashes.txt
```

### Kandidaten begrenzen
```bash
# Passwortlänge begrenzen
john --min-length=6 --max-length=12 --incremental hashes.txt
```

### Speichernutzung
```bash
# Speichernutzung verringern
john --mem-file-size=32 --wordlist=huge.txt hashes.txt
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: Linux-Shadow-Datei
```bash
# Dateien zusammenführen
sudo unshadow /etc/passwd /etc/shadow > hashes.txt

# Passwortsuche mit rockyou
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Ergebnisse anzeigen
john --show hashes.txt
```

### Beispiel 2: Windows-NTLM
```bash
# Aus der Ausgabe von hashdump
echo "admin:500:aad3b435b51404ee:31d6cfe0d16ae931:::" > ntlm.txt

# Passwortsuche
john --format=nt --wordlist=rockyou.txt ntlm.txt
```

### Beispiel 3: Passwort einer ZIP-Datei
```bash
zip2john secret.zip > zip.hash
john --wordlist=rockyou.txt zip.hash
john --show zip.hash
```

### Beispiel 4: Passphrase eines SSH-Schlüssels
```bash
ssh2john ~/.ssh/id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Beispiel 5: MD5-Hashwerte
```bash
echo "5d41402abc4b2a76b9719d911017c592" > md5.txt
john --format=raw-md5 --wordlist=rockyou.txt md5.txt
```

### Beispiel 6: Hashwerte einer Webanwendung
```bash
# bcrypt-Hashwert
echo '$2a$10$N9qo8uLOickgx2ZMRZoMy...' > bcrypt.txt
john --format=bcrypt --wordlist=rockyou.txt bcrypt.txt
```

### Beispiel 7: Mit Regeln
```bash
john --wordlist=small_wordlist.txt --rules=Jumbo hashes.txt
```

### Beispiel 8: Inkrementelle Suche
```bash
# Alle Kombinationen aus 6-8 Kleinbuchstaben und Ziffern versuchen
john --incremental=LowerNum --min-length=6 --max-length=8 hashes.txt
```

---

## 📊 Kurzreferenz

### Wichtige Befehle

| Aufgabe | Befehl |
|------|---------|
| Einfache Passwortsuche | `john hashes.txt` |
| Wortlistensuche | `john --wordlist=rockyou.txt hashes.txt` |
| Mit Regeln | `john --wordlist=words.txt --rules hashes.txt` |
| Gefundene Passwörter anzeigen | `john --show hashes.txt` |
| Bestimmtes Format | `john --format=raw-md5 hashes.txt` |
| Inkrementell | `john --incremental hashes.txt` |
| Sitzung fortsetzen | `john --restore=session` |
| Formate auflisten | `john --list=formats` |

### 2john-Werkzeuge

| Werkzeug | Zweck |
|------|---------|
| `unshadow` | Linux-Dateien passwd/shadow |
| `zip2john` | ZIP-Dateien |
| `rar2john` | RAR-Dateien |
| `pdf2john` | PDF-Dateien |
| `office2john` | Office-Dokumente |
| `ssh2john` | SSH-Schlüssel |
| `keepass2john` | KeePass-Datenbanken |

### Häufige Formate

| Hash | Formatoption |
|------|-------------|
| MD5 | `raw-md5` |
| SHA1 | `raw-sha1` |
| SHA256 | `raw-sha256` |
| NTLM | `nt` |
| Linux SHA512 | `sha512crypt` |
| bcrypt | `bcrypt` |
| MySQL | `mysql-sha1` |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise für effizientes Arbeiten

1. **Zuerst den Single-Modus versuchen**
   ```bash
   john --single hashes.txt
   ```

2. **Zum Ziel passende Wortlisten verwenden**
   ```bash
   # Unternehmensname, Stadt usw.
   john --wordlist=targeted.txt hashes.txt
   ```

3. **Mit kleinen Wortlisten beginnen**
   ```bash
   john --wordlist=top1000.txt hashes.txt
   # Anschließend größere Listen versuchen
   ```

4. **Modi kombinieren**
   ```bash
   john --single hashes.txt
   john --wordlist=rockyou.txt --rules hashes.txt
   john --incremental hashes.txt
   ```

### Hinweise zur Leistung

```bash
# Mehrere Kerne verwenden
john --fork=4 hashes.txt

# GPU-Beschleunigung (Jumbo)
john --format=sha512crypt-opencl hashes.txt
```

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** John the Ripper ist ein leistungsfähiges Werkzeug zur Passwortprüfung und darf nur für **autorisierte Zwecke** eingesetzt werden.
> 
> - ✅ Eigene Passwörter prüfen
> - ✅ Mit ausdrücklicher Genehmigung testen
> - ✅ Sicherheitsprüfungen mit Genehmigung durchführen
> - ❌ Niemals ohne Genehmigung Passwörter ermitteln
> - ❌ Unbefugter Zugriff ist laut Vorlage rechtswidrig
> 
> **Passwortangriffe ohne Genehmigung sind laut Vorlage rechtswidrig und können strafrechtliche Folgen haben.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [Offizielle John-the-Ripper-Website](https://www.openwall.com/john/)
- [John Jumbo GitHub](https://github.com/openwall/john)
- [John-Wiki](https://openwall.info/wiki/john)

### Wortlisten
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Verwandte Spickzettel
- [Hydra](../../Hydra/translations/README.de.md)
- [Metasploit](../../Metasploit/translations/README.de.md)
- [Nmap](../../Nmap/translations/README.de.md)

---

<p align="center">
  <b>🔨 Passwortprüfungen sicher beherrschen!</b><br>
  <i>Stets verantwortungsbewusst vorgehen</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Versionen und Installation:** Die Vorlage mischt Core- und Jumbo-Funktionen. Maskenmodus, OpenCL-Formate, Konverter und Optionen hängen vom Build ab. Paketnamen wie `john-jumbo`, Konverterskripte und deren Dateiendungen unterscheiden sich je nach Distribution und Installation. Auch `--version` ist kein universeller Nachweis für jeden John-Build. `--list=formats` kann mehrere Formate pro Zeile ausgeben; `wc -l` zählt daher nicht zuverlässig deren Gesamtzahl. [Openwall: Optionen](https://www.openwall.com/john/doc/OPTIONS.shtml), [Jumbo-Optionen](https://github.com/openwall/john/blob/bleeding-jumbo/doc/OPTIONS).

> **Modi und Formatwahl:** Der Single-Modus benötigt geeignete Kontoinformationen für seine Kandidaten. Der inkrementelle Modus arbeitet mit konfigurierten Zeichensätzen und Längengrenzen und ist keine unbeschränkte Suche über jede denkbare Zeichenfolge. Automatische Formaterkennung kann mehrdeutig sein; Vergleiche von John und Hashcat sowie die Geschwindigkeitsbewertungen sind vereinfachte Einschätzungen der Vorlage, kein hier ausgeführter Benchmark. Die Angabe „keine automatische Erkennung“ bei Hashcat ist veraltet: Hashcat dokumentiert `--identify` zur Ermittlung möglicher Algorithmen. [Hashcat: Optionen](https://hashcat.net/wiki/doku.php?id=hashcat). Die Kennungen für Linux-Passworthashes sind Formatbeispiele, keine allgemeine Aussage über die aktuelle Standardkonfiguration sämtlicher Distributionen. [Openwall: Modi](https://www.openwall.com/john/doc/MODES.shtml).

> **Hash- und Regelbeispiele:** Mehrere Hashwerte sind mit `...` abgekürzt; auch die kurzen LM-/NTLM-Felder im Hashdump-Beispiel sind keine vollständigen Eingabewerte. Regeln lesen `$X` beziehungsweise `^X` mit genau einem nachfolgenden Zeichen. Die Muster `$123` und `^2023` sind daher keine verlässliche Kurzschreibweise für das Anhängen beziehungsweise Voranstellen ganzer Zahlenfolgen. Im Beispiel `sXY` muss die tatsächliche Zeichenersetzung zum gezeigten Ergebnis passen. Originalwerte, Regeln und Befehle bleiben unverändert. [Openwall: Regelsyntax](https://www.openwall.com/john/doc/RULES.shtml).

> **Dateien und Ergebnisse:** 2john-Werkzeuge erzeugen Eingaben für John; sie liefern nicht bereits das Passwort. Hashformat, Dateiversion und Verschlüsselungsverfahren bestimmen die Unterstützung. Pot- und Sitzungsdateien hängen von Installation und Konfiguration ab. Ein erfolgloser Lauf beweist weder Passwortstärke noch fehlende Prüfbarkeit. Es wurde kein Beispiel und kein Benchmark ausgeführt.
