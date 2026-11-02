# 🏁 Spickzettel für CTFs

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 ██████╗████████╗███████╗
██╔════╝╚══██╔══╝██╔════╝
██║        ██║   █████╗  
██║        ██║   ██╔══╝  
╚██████╗   ██║   ██║     
 ╚═════╝   ╚═╝   ╚═╝     
 ██████╗██╗  ██╗███████╗ █████╗ ████████╗███████╗██╗  ██╗███████╗███████╗████████╗███████╗
██╔════╝██║  ██║██╔════╝██╔══██╗╚══██╔══╝██╔════╝██║  ██║██╔════╝██╔════╝╚══██╔══╝██╔════╝
██║     ███████║█████╗  ███████║   ██║   ███████╗███████║█████╗  █████╗     ██║   ███████╗
██║     ██╔══██║██╔══╝  ██╔══██║   ██║   ╚════██║██╔══██║██╔══╝  ██╔══╝     ██║   ╚════██║
╚██████╗██║  ██║███████╗██║  ██║   ██║   ███████║██║  ██║███████╗███████╗   ██║   ███████║
 ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝   ╚═╝   ╚══════╝
```

---

## 📑 Inhaltsverzeichnis

| Kategorie | Beschreibung | Leitfaden |
|----------|-------------|-------|
| **Web** | SQLi, XSS, SSTI, LFI, Umgehung der Authentifizierung | [📄 Ansehen](./Web-CTF.de.md) |
| **Crypto** | RSA, AES, Hashwerte und Kodierung | [📄 Ansehen](./Crypto-CTF.de.md) |
| **Reverse Engineering** | Disassemblierung, Debugging und Patching | [📄 Ansehen](./Reverse-Engineering-CTF.de.md) |
| **Forensics** | Steganografie, Speicher- und Datenträgeranalyse | [📄 Ansehen](./Forensics-CTF.de.md) |
| **Pwn/Binary** | Buffer Overflow, ROP und Shellcode | [📄 Ansehen](./Pwn-CTF.de.md) |

---

## 🎯 CTF-Plattformen

| Plattform | URL | Art |
|----------|-----|------|
| **HackTheBox** | hackthebox.com | Übungsmaschinen und CTFs |
| **TryHackMe** | tryhackme.com | Angeleitetes Lernen |
| **PicoCTF** | picoctf.org | Für Einsteiger geeignet |
| **CTFtime** | ctftime.org | CTF-Kalender |
| **OverTheWire** | overthewire.org | Wargames |
| **RootMe** | root-me.org | Übungsaufgaben |
| **CryptoHack** | cryptohack.org | Krypto-Aufgaben |
| **pwnable.kr** | pwnable.kr | Pwn-Aufgaben |

---

## 🛠️ Wichtige CTF-Werkzeuge

| Kategorie | Werkzeuge |
|----------|-------|
| **Web** | Burp Suite, SQLMap, ffuf |
| **Crypto** | CyberChef, RsaCtfTool, hashcat |
| **Reverse** | Ghidra, IDA, Radare2, GDB |
| **Forensics** | Volatility, Autopsy, Binwalk |
| **Pwn** | pwntools, ROPgadget, gef |
| **Stego** | steghide, zsteg, exiftool |

---

## 🔥 CTF-Befehle zum schnellen Nachschlagen

### Web
```bash
# SQLi
sqlmap -u "http://url?id=1" --dbs

# Verzeichnisse systematisch ausprobieren
gobuster dir -u http://url -w /usr/share/wordlists/dirb/common.txt
```

### Crypto
```bash
# Hashverfahren erkennen
hashid 'hash_string'
hash-identifier

# Base64 dekodieren
echo "string" | base64 -d
```

### Forensics
```bash
# Dateityp
file unknown_file
binwalk unknown_file

# Zeichenfolgen
strings -n 6 file | grep -i flag
```

### Pwn
```bash
# Schutzmechanismen prüfen
checksec binary

# Mit GDB starten
gdb ./binary
```

---

## 📋 CTF-Methodik

```
1. AUFKLÄRUNG
   - Aufgabenbeschreibung sorgfältig lesen
   - Alle Dateien herunterladen
   - Kategorie bestimmen (web/crypto/pwn/etc)

2. ENUMERATION
   - Passende Werkzeuge verwenden
   - Auf offensichtliche Schwachstellen prüfen
   - Nach Hinweisen in Dateinamen und Metadaten suchen

3. AUSNUTZUNG
   - Bekannte Techniken anwenden
   - Nach Fehlermeldungen suchen
   - Häufige Payloads ausprobieren

4. FLAG FINDEN
   - Nach dem Flag-Format suchen: CTF{...}
   - Alle Ausgaben prüfen
   - Einreichen und Ergebnis prüfen
```

---

## 🏆 Flag-Formate

```
Häufige Flag-Formate:
- flag{...}
- FLAG{...}
- CTF{...}
- picoCTF{...}
- HTB{...}
- DUCTF{...}
- custom_flag_format
```

---

**Zurück zur Hauptübersicht:** [🔴 Hacking Cheatsheets](../../README.md)

---

## Hinweise zur deutschen Ausgabe

> **Begriffe und Platzhalter:** CTF bedeutet *Capture the Flag*: Gesucht wird meist eine festgelegte Zeichenfolge als Lösungsnachweis. *Enumeration* ist die systematische Bestandsaufnahme, *Payload* der übergebene Testinhalt, *Pwn* bezeichnet hier Aufgaben zur Binärausnutzung. Die Flag-Formate und Werkzeuglisten sind Beispiele; verbindlich sind die Regeln der jeweiligen Aufgabe. `string` im Base64-Dekodierbeispiel ist ein Platzhalter und nicht automatisch gültige Base64-Eingabe. Werkzeugoptionen und Voraussetzungen hängen von der Version ab; die Originalbefehle bleiben unverändert.
