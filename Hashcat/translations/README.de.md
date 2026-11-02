# ⚡ Hashcat – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _                _   
 | | | | __ _ ___| |__   ___ __ _| |_ 
 | |_| |/ _` / __| '_ \ / __/ _` | __|
 |  _  | (_| \__ \ | | | (_| (_| | |_ 
 |_| |_|\__,_|___/_| |_|\___\__,_|\__|
                                       
    Laut Vorlage besonders schnelle Passwortwiederherstellung
```

<p align="center">
  <img src="https://img.shields.io/badge/Hashcat-GPU_Cracker-red?style=for-the-badge" alt="Hashcat">
  <img src="https://img.shields.io/badge/350+-Hash_Types-orange?style=for-the-badge" alt="Hash Types">
  <img src="https://img.shields.io/badge/GPU-Accelerated-blue?style=for-the-badge" alt="GPU">
  <img src="https://img.shields.io/badge/World's-Fastest-green?style=for-the-badge" alt="Fastest">
</p>

<p align="center">
  <b>🚀 Ein leistungsfähiges Werkzeug zur Passwortwiederherstellung</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Grundsyntax](#-grundsyntax)
- [Angriffsmodi](#-angriffsmodi)
- [Hash-Modi](#-hash-modi)
- [Maskenangriff](#-maskenangriff)
- [Regeln](#-regeln)
- [Sitzungsverwaltung](#-sitzungsverwaltung)
- [GPU-Optimierung](#-gpu-optimierung)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**Hashcat** ist ein leistungsfähiges Werkzeug zur Passwortwiederherstellung. Laut Vorlage unterstützt es über 350 Hash-Typen und nutzt GPU-Beschleunigung.

### Wichtige Funktionen

| Eigenschaft | Beschreibung |
|---------|-------------|
| **350+ Hash-Typen** | MD5, SHA, NTLM, bcrypt, WPA und weitere |
| **GPU-beschleunigt** | NVIDIA CUDA, AMD OpenCL, Intel |
| **Multi-GPU** | Unterstützung verteilter Berechnungen laut Vorlage |
| **5 Angriffsmodi laut Vorlage** | Wörterbuch, Brute Force, Hybrid und weitere |
| **Leistungsfähige Regeln** | Erweiterte Veränderung von Wortkandidaten |
| **Plattformübergreifend** | Linux, Windows, macOS |

### Hashcat im Vergleich mit John the Ripper

| Eigenschaft | Hashcat | John |
|---------|---------|------|
| **Geschwindigkeit** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| **GPU-Unterstützung** | Sehr gut | Gut |
| **Hash-Typen** | 350+ | 300+ |
| **Automatische Erkennung** | ❌ Nein | ✅ Ja |
| **Bedienbarkeit** | Mittel | Einfacher |
| **Besonders geeignet für** | Passwortprüfung mit GPUs | Passwortprüfung mit CPUs |

---

## 📥 Installation

### Kali Linux
```bash
sudo apt update && sudo apt install hashcat

# Mit OpenCL
sudo apt install hashcat-nvidia  # NVIDIA
sudo apt install hashcat-amd     # AMD
```

### Ubuntu/Debian
```bash
sudo apt install hashcat
```

### Windows
```bash
# Von der offiziellen Website herunterladen
https://hashcat.net/hashcat/

# Entpacken und aus dem Verzeichnis starten
hashcat.exe -h
```

### Aus dem Quellcode
```bash
git clone https://github.com/hashcat/hashcat.git
cd hashcat
make
./hashcat -h
```

### Installation prüfen
```bash
hashcat --version
hashcat -I                    # OpenCL-Geräte anzeigen
hashcat -b                    # Benchmark ausführen
```

---

<a name="-grundsyntax"></a>

## ⌨️ Grundsyntax

### Allgemeine Syntax
```bash
hashcat [options] hash|hashfile [dictionary|mask]
```

### Wichtige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-m` | Hash-Typ (Modus) |
| `-a` | Angriffsmodus |
| `-o` | Ausgabedatei |
| `-r` | Regeldatei |
| `--show` | Gefundene Passwörter anzeigen |
| `--status` | Statusanzeige aktivieren |
| `-w` | Auslastungsprofil (1-4) |
| `--force` | Warnungen ignorieren |
| `-O` | Optimierte Kernel |

### Schnelleinstieg
```bash
# Einfacher Wörterbuchangriff
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Gefundene Hashwerte anzeigen
hashcat -m 0 hash.txt --show

# Beispiel-Hashwerte ansehen
hashcat -m 0 --example-hashes
```

---

## 🎮 Angriffsmodi

### Übersicht

| Modus | Name | Beschreibung |
|------|------|-------------|
| `-a 0` | Wörterbuch | Wortlistenangriff |
| `-a 1` | Kombination | Zwei Wortlisten kombinieren |
| `-a 3` | Brute-Force | Maskenbasierter Angriff |
| `-a 6` | Hybrid: Wörterbuch + Maske | Wortliste + Maske |
| `-a 7` | Hybrid: Maske + Wörterbuch | Maske + Wortliste |
| `-a 9` | Zuordnung | Wortzuordnungen |

### Wörterbuchangriff (-a 0)

```bash
# Einfacher Wörterbuchangriff
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Mit Regeln
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rules/best64.rule

# Mehrere Wortlisten
hashcat -m 0 -a 0 hash.txt wordlist1.txt wordlist2.txt
```

### Kombinationsangriff (-a 1)

Kombiniert Wörter aus zwei Wortlisten.

```bash
# Zwei Wortlisten kombinieren
hashcat -m 0 -a 1 hash.txt wordlist1.txt wordlist2.txt

# word1word2-Kombinationen
```

### Brute-Force-/Maskenangriff (-a 3)

```bash
# Nur Kleinbuchstaben, 6 Zeichen
hashcat -m 0 -a 3 hash.txt ?l?l?l?l?l?l

# Nur Ziffern, 4-8 Zeichen laut Vorlage
hashcat -m 0 -a 3 hash.txt ?d?d?d?d --increment --increment-min=4 --increment-max=8

# Eigener Zeichensatz
hashcat -m 0 -a 3 hash.txt -1 ?l?d ?1?1?1?1?1?1
```

### Hybridangriffe (-a 6, -a 7)

```bash
# Wörterbuch + Maske (anhängen)
hashcat -m 0 -a 6 hash.txt wordlist.txt ?d?d?d?d

# Maske + Wörterbuch (voranstellen)
hashcat -m 0 -a 7 hash.txt ?d?d?d?d wordlist.txt
```

---

## 🔢 Hash-Modi

### Häufig verwendete Hash-Modi

| Modus | Hash-Typ | Beispiel |
|------|-----------|---------|
| 0 | MD5 | `5d41402abc4b2a76b9719d911017c592` |
| 100 | SHA1 | `aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d` |
| 1400 | SHA256 | `2cf24dba5fb0a30e26e83b2ac5b9e29e...` |
| 1700 | SHA512 | `cf83e1357eefb8bdf1542850d66d8007d620e405...` |
| 1000 | NTLM | `32ed87bdb5fdc5e9cba88547376818d4` |
| 3000 | LM | `aad3b435b51404ee` |
| 5600 | NetNTLMv2 | Komplexes Format |
| 3200 | bcrypt | `$2a$10$...` |
| 1800 | sha512crypt (Linux) | `$6$...` |
| 500 | md5crypt (Linux) | `$1$...` |

### Webanwendungen

| Modus | Hash-Typ |
|------|-----------|
| 400 | WordPress (phpass) |
| 2500 | WPA/WPA2 |
| 22000 | WPA-PBKDF2-PMKID+EAPOL |
| 121 | SMF |
| 2611 | vBulletin |
| 3711 | MediaWiki B |
| 7900 | Drupal7 |

### Datenbanken

| Modus | Hash-Typ |
|------|-----------|
| 300 | MySQL4.1/MySQL5 |
| 200 | MySQL323 |
| 1731 | MSSQL (2012, 2014) |
| 112 | Oracle S |
| 12300 | Oracle T |

### Dokumente und Archive

| Modus | Hash-Typ |
|------|-----------|
| 9600 | Office 2013 |
| 9500 | Office 2010 |
| 9400 | Office 2007 |
| 9700 | MS Office ⇐ 2003 |
| 10500 | PDF 1.4-1.6 |
| 10700 | PDF 1.7 Level 8 |
| 11600 | 7-Zip |
| 13600 | WinZip |
| 17200 | PKZIP |
| 13000 | RAR5 |
| 12500 | RAR3-hp |

### Kryptowährungen

| Modus | Hash-Typ |
|------|-----------|
| 11300 | Bitcoin-Wallet |
| 12700 | Blockchain-Wallet |
| 16600 | Electrum-Wallet |
| 15200 | Ethereum Keystore |

### Modi auflisten und suchen
```bash
# Alle Hash-Modi auflisten
hashcat --help | grep -i "hash modes"

# Nach einem bestimmten Hash-Typ suchen
hashcat --example-hashes | grep -i "ntlm"
hashcat --example-hashes | grep -i "sha256"

# Beispiel für das Hash-Format anzeigen
hashcat -m 1000 --example-hashes
```

---

## 🎭 Maskenangriff

### Platzhalter für Zeichensätze

| Platzhalter | Zeichen |
|-------------|------------|
| `?l` | abcdefghijklmnopqrstuvwxyz |
| `?u` | ABCDEFGHIJKLMNOPQRSTUVWXYZ |
| `?d` | 0123456789 |
| `?h` | 0123456789abcdef |
| `?H` | 0123456789ABCDEF |
| `?s` | !"#$%&'()*+,-./:;<=>?@[]^_\`{\|}~ |
| `?a` | ?l?u?d?s |
| `?b` | 0x00 - 0xff |

### Eigene Zeichensätze

```bash
# Eigenen Zeichensatz festlegen
hashcat -a 3 -1 ?l?d hash.txt ?1?1?1?1?1?1

# Mehrere eigene Zeichensätze
hashcat -a 3 -1 abc -2 123 hash.txt ?1?2?1?2

# Sonderzeichen
hashcat -a 3 -1 '!@#$%' hash.txt pass?1?1?1?1
```

### Inkrementmodus

```bash
# Längen von 1 bis 8 versuchen
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment

# Minimum und Maximum angeben
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment --increment-min=4 --increment-max=8
```

### Häufige Maskenmuster

```bash
# 8 Kleinbuchstaben
?l?l?l?l?l?l?l?l

# Passwort + 4 Ziffern
password?d?d?d?d

# Großbuchstabe + Kleinbuchstaben + Ziffern
?u?l?l?l?l?l?d?d

# Muster Winter2024
?u?l?l?l?l?l?d?d?d?d

# Telefonnummer
?d?d?d?d?d?d?d?d?d?d
```

---

## 📜 Regeln

### Mitgelieferte Regeldateien

```bash
# Speicherort
ls /usr/share/hashcat/rules/

# Häufig verwendete Regeln
best64.rule           # Laut Vorlage die besten 64 Regeln
rockyou-30000.rule    # Laut Vorlage die wichtigsten 30000 Regeln
d3ad0ne.rule          # D3ad0ne-Regeln
dive.rule             # Dive-Regeln
generated.rule        # Erzeugte Regeln
```

### Regeln verwenden

```bash
# Einzelne Regeldatei
hashcat -m 0 -a 0 hash.txt wordlist.txt -r best64.rule

# Mehrere Regeldateien
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rule1.rule -r rule2.rule

# Regeln erzeugen
hashcat -m 0 -a 0 hash.txt wordlist.txt -g 1000
```

### Häufige Regeloperationen

| Regel | Beschreibung | Beispiel |
|------|-------------|---------|
| `:` | Nichts verändern | password |
| `l` | Alle Zeichen kleinschreiben | PASSWORD → password |
| `u` | Alle Zeichen großschreiben | password → PASSWORD |
| `c` | Erstes Zeichen großschreiben | password → Password |
| `C` | Erstes Zeichen klein-, übrige großschreiben | password → pASSWORD |
| `t` | Groß-/Kleinschreibung umschalten | password → PASSWORD |
| `r` | Umkehren | password → drowssap |
| `d` | Verdoppeln | pass → passpass |
| `$X` | X anhängen | pass → passX |
| `^X` | X voranstellen | pass → Xpass |
| `sXY` | X durch Y ersetzen | password → pa$$word |

---

## 💾 Sitzungsverwaltung

### Sitzung erstellen
```bash
# Benannte Sitzung
hashcat -m 0 -a 0 hash.txt wordlist.txt --session=mysession

# Sitzungsdateien laut Vorlage unter ~/.hashcat/sessions/
```

### Anhalten und fortsetzen
```bash
# Anhalten: Während der Berechnung 'p' drücken
# Fortsetzen: Während der Berechnung 'r' drücken

# Alternativ über die Kommandozeile wiederherstellen
hashcat --session=mysession --restore
```

### Statusbefehle

Während der Berechnung:
- `s` - Status anzeigen
- `p` - Anhalten
- `r` - Fortsetzen
- `b` - Bypass (aktuellen Angriff überspringen)
- `c` - Kontrollpunkt
- `q` - Beenden

### Ausgabeoptionen
```bash
# Ausgabe in eine Datei schreiben
hashcat -m 0 -a 0 hash.txt wordlist.txt -o cracked.txt

# Ausgabeformat
hashcat -m 0 hash.txt --outfile-format=2    # hash:plain

# Gefundene Passwörter anzeigen
hashcat -m 0 hash.txt --show

# Übrige, noch nicht gefundene Passwörter anzeigen
hashcat -m 0 hash.txt --left
```

---

## 🚀 GPU-Optimierung

### Auslastungsprofile

| Profil | Beschreibung |
|---------|-------------|
| `-w 1` | Niedrig (laut Vorlage Standard, interaktiv) |
| `-w 2` | Mittel |
| `-w 3` | Hoch (kann die Desktop-Bedienung beeinträchtigen) |
| `-w 4` | Nightmare (dediziertes Prüfsystem) |

### Befehle optimieren
```bash
# Hohe Leistung
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 3 -O

# Optimierte Kernel (schneller, begrenzte Passwortlänge)
hashcat -m 0 -a 0 hash.txt wordlist.txt -O

# Gerät festlegen
hashcat -m 0 -a 0 hash.txt wordlist.txt -d 1

# CPU ausschließen, nur GPU verwenden
hashcat -m 0 -a 0 hash.txt wordlist.txt -D 2
```

### Geräteoptionen
```bash
# Geräte auflisten
hashcat -I

# Ein bestimmtes Gerät auswählen
hashcat -d 1 ...

# Gerätetypen
# -D 1 = CPU
# -D 2 = GPU
# -D 3 = FPGA
```

### Benchmark
```bash
# Vollständiger Benchmark
hashcat -b

# Bestimmter Hash-Typ
hashcat -b -m 0

# Laut Vorlage Benchmark ohne OpenCL-Optimierung
hashcat -b --benchmark-all
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: MD5-Wörterbuchangriff
```bash
hashcat -m 0 -a 0 md5_hashes.txt /usr/share/wordlists/rockyou.txt
```

### Beispiel 2: NTLM mit Regeln
```bash
hashcat -m 1000 -a 0 ntlm_hashes.txt wordlist.txt -r /usr/share/hashcat/rules/best64.rule
```

### Beispiel 3: WPA-/WPA2-Handshake
```bash
# Zuerst in das Hashcat-Format konvertieren
hcxpcapngtool -o hash.hc22000 capture.pcapng

# Passwort ermitteln
hashcat -m 22000 hash.hc22000 /usr/share/wordlists/rockyou.txt
```

### Beispiel 4: SHA256 mit Brute Force (6 Zeichen)
```bash
hashcat -m 1400 -a 3 sha256_hash.txt ?a?a?a?a?a?a
```

### Beispiel 5: Office-2013-Dokument
```bash
# Hash mit office2john extrahieren
office2john document.docx > office.hash

# Passwort ermitteln
hashcat -m 9600 office.hash wordlist.txt
```

### Beispiel 6: Linux-Shadow-Passwort
```bash
hashcat -m 1800 -a 0 shadow_hash.txt wordlist.txt
```

### Beispiel 7: WordPress-Hash
```bash
hashcat -m 400 -a 0 wordpress_hash.txt wordlist.txt
```

### Beispiel 8: Hybridangriff
```bash
# Unternehmensname + 4 Ziffern
hashcat -m 0 -a 6 hash.txt company_names.txt ?d?d?d?d
```

### Beispiel 9: Hashwerte einer Windows-Domäne
```bash
# NTLM-Hashes aus secretsdump
hashcat -m 1000 -a 0 ntds_hashes.txt wordlist.txt -r best64.rule --username
```

### Beispiel 10: Bitcoin-Wallet
```bash
# Mit bitcoin2john extrahieren
hashcat -m 11300 bitcoin_hash.txt wordlist.txt
```

---

## 📊 Kurzreferenz

### Wichtige Befehle

| Aufgabe | Befehl |
|------|---------|
| MD5 mit Wörterbuch | `hashcat -m 0 -a 0 hash.txt wordlist.txt` |
| NTLM mit Brute Force | `hashcat -m 1000 -a 3 hash.txt ?a?a?a?a?a?a` |
| SHA256 mit Regeln | `hashcat -m 1400 -a 0 hash.txt wordlist.txt -r rules.rule` |
| Gefundene Passwörter anzeigen | `hashcat -m 0 hash.txt --show` |
| Benchmark | `hashcat -b` |
| Geräte auflisten | `hashcat -I` |
| Sitzung wiederherstellen | `hashcat --session=name --restore` |

### Häufige Hash-Modi

| Modus | Typ |
|------|------|
| 0 | MD5 |
| 100 | SHA1 |
| 1000 | NTLM |
| 1400 | SHA256 |
| 1800 | sha512crypt |
| 3200 | bcrypt |
| 22000 | WPA2 |

### Angriffsmodi

| Modus | Typ |
|------|------|
| -a 0 | Wörterbuch |
| -a 1 | Kombination |
| -a 3 | Brute Force |
| -a 6 | Hybrid (Wörterbuch + Maske) |
| -a 7 | Hybrid (Maske + Wörterbuch) |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise für effizientes Arbeiten

1. **Mit Wörterbuch und Regeln beginnen**
```bash
   hashcat -m 0 -a 0 hash.txt rockyou.txt -r best64.rule
```

2. **Optimierte Kernel verwenden**
```bash
   hashcat -O ...
```

3. **Häufige Muster gezielt prüfen**
```bash
   # Jahreszeit+Jahr: Winter2024
   hashcat -a 3 -1 WSFJ -2 winterumeall hash.txt ?1?2?2?2?2?2?d?d?d?d
```

### Hinweise zur Leistung

```bash
# Maximale Leistung (dediziertes System)
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 4 -O --force

# Mehrere GPUs
hashcat -d 1,2 ...
```

### Arbeitsablauf

```
1. Hash-Typ bestimmen
2. Wörterbuchangriff mit Regeln versuchen
3. Gezielte Masken (Muster) versuchen
4. Vollständige Brute-Force-Suche versuchen, sofern praktikabel
5. Hybridangriffe versuchen
```

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** Hashcat ist ein leistungsfähiges Werkzeug zur Passwortprüfung und darf nur für **autorisierte Zwecke** eingesetzt werden.
> 
> - ✅ Eigene Systeme prüfen
> - ✅ Sicherheitsprüfungen mit Genehmigung
> - ✅ Eigene Passwörter wiederherstellen
> - ❌ Passwörter niemals ohne Genehmigung prüfen
> - ❌ Unbefugter Zugriff ist rechtswidrig
> 
> **Das Knacken von Passwörtern ohne Genehmigung ist laut Vorlage rechtswidrig und kann strafrechtliche Folgen haben.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [Offizielle Hashcat-Website](https://hashcat.net/hashcat/)
- [Hashcat Wiki](https://hashcat.net/wiki/)
- [Hashcat GitHub](https://github.com/hashcat/hashcat)
- [Hashcat Forum](https://hashcat.net/forum/)

### Wortlisten
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Verwandte Spickzettel
- [John the Ripper](../../John-The-Ripper/translations/README.de.md)
- [Hydra](../../Hydra/translations/README.de.md)
- [Nmap](../../Nmap/translations/README.de.md)

---

<p align="center">
  <b>⚡ GPU-gestützte Passwortprüfung beherrschen!</b><br>
  <i>Laut Vorlage besonders schnelle Passwortwiederherstellung</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Versionen und Einordnung:** Hash-Anzahlen, Geschwindigkeitssterne und Plattformvergleiche sind Angaben der Vorlage, keine hier gemessenen Werte. Die Liste enthält bereits sechs Modi trotz der Angabe „5“ und ist nicht vollständig für jede Version. Aktuelle Hashcat-Versionen bieten mit `--identify` eine Hash-Typ-Erkennung; ähnliche Hashformate bleiben mehrdeutig. Mehrere lokale GPUs bedeuten nicht automatisch eine über mehrere Rechner verteilte Ausführung. [Hashcat-Dokumentation](https://hashcat.net/wiki/doku.php?id=hashcat).

> **Konkrete Optionskorrekturen:** In Hashcat 6.2.6 ist das Standard-Auslastungsprofil `-w 2`, nicht `-w 1`; die Optionen der Vorlage sind versionsabhängig. `--outfile-format=2` gibt Klartext aus, nicht das kommentierte Format `hash:plain`. `--benchmark-all` erweitert den Benchmark auf alle Hash-Modi und deaktiviert nicht einfach OpenCL-Optimierungen. `--force` unterdrückt Warnungen und ist keine allgemeine Leistungsempfehlung. Paketnamen, Backend-Treiber, Sitzungsdateipfade und extrahierte Eingabeformate hängen von Installation und Version ab. [Hashcat: Optionen](https://hashcat.net/wiki/doku.php?id=hashcat), [Vorgaben in 6.2.6](https://github.com/hashcat/hashcat/blob/v6.2.6/include/types.h).

> **Masken und Regeln:** Eine Maske mit vier Positionen wird durch `--increment-max=8` nicht auf acht Positionen verlängert. Das entsprechende Originalbeispiel prüft deshalb nicht den gesamten angegebenen Längenbereich. Masken können von der Shell interpretiert werden; die gezeigten Zeichenfolgen sind Referenzwerte. Der Sonderzeichensatz `?s` enthält laut Dokumentation zusätzlich ein Leerzeichen und einen Backslash, die in der verkürzten Tabelle fehlen. Die vorhandenen Tabellenzeichen bleiben unverändert dargestellt; Backtick und Pipe sind nur für korrektes Markdown maskiert. Das Regelbeispiel `sXY` erklärt einen Operator mit Platzhaltern und ist keine konkret ausgefüllte Ersetzung für das abgebildete Wort. [Hashcat: Masken](https://hashcat.net/wiki/doku.php?id=mask_attack).

> **Hash- und Eingabeformate:** Gekürzte Werte mit `...` sind keine vollständigen Hashes. Historische Modusnummern wie WPA `2500`, WordPress-Varianten und Extraktionsausgaben müssen zur verwendeten Version und zum konkreten Dateiformat passen; Dateinamenpräfixe aus Extraktionswerkzeugen sind nicht automatisch Teil des Hashes. Ein grep-Treffer auf „hash modes“ listet nur passende Zeilen, nicht den gesamten folgenden Abschnitt. Die Originalbefehle, Hashwerte, Regeln und Masken wurden weder geändert noch ausgeführt. [Hashcat: Beispiel-Hashes](https://hashcat.net/wiki/doku.php?id=example_hashes).
