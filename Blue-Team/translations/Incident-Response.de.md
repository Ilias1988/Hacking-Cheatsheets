# 🚨 Spickzettel zur Incident Response

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./Incident-Response.tr.md) • [🇬🇷 Ελληνικά](./Incident-Response.el.md)

```
  ██╗███╗   ██╗ ██████╗██╗██████╗ ███████╗███╗   ██╗████████╗
  ██║████╗  ██║██╔════╝██║██╔══██╗██╔════╝████╗  ██║╚══██╔══╝
  ██║██╔██╗ ██║██║     ██║██║  ██║█████╗  ██╔██╗ ██║   ██║   
  ██║██║╚██╗██║██║     ██║██║  ██║██╔══╝  ██║╚██╗██║   ██║   
  ██║██║ ╚████║╚██████╗██║██████╔╝███████╗██║ ╚████║   ██║   
  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝   
        ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗███████╗███████╗
        ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔════╝██╔════╝
        ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║███████╗█████╗  
        ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║╚════██║██╔══╝  
        ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║███████║███████╗
        ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚══════╝
```

---

> **Einordnung der Vorlage:** Die sechs Schritte sind eine didaktische Aufteilung des älteren IR-Modells. NIST SP 800-61 Rev. 3 ersetzt Rev. 2 und ordnet Incident Response in das Risikomanagement nach CSF 2.0 ein. Die Reaktionszeiten in der Tabelle sind Beispielwerte, keine verbindlichen NIST-Vorgaben. [NIST SP 800-61 Rev. 3](https://csrc.nist.gov/pubs/sp/800/61/r3/final).

> **Hinweise zu den Originalbeispielen:** `wmic` ist veraltet und kann auf aktuellen Windows-Versionen fehlen; Microsoft verweist auf PowerShell als Ersatz. `forfiles` enthält ein festes Beispieldatum. Verkehr auf Port 443 ist meist verschlüsseltes HTTPS. Das Linux-Beispiel mit `/dev/mem` liefert auf modernen Systemen häufig kein vollständiges RAM-Abbild. `systemctl disable` verhindert den automatischen Start, beendet aber einen laufenden Dienst nicht. `crontab -r` entfernt die gesamte Crontab des aktuellen Benutzers. Die zweimalige krbtgt-Rücksetzung erfordert einen abgestimmten AD-Wiederherstellungsplan mit Replikation zwischen den Schritten. [Microsoft zu WMIC](https://support.microsoft.com/en-us/servicing/os/windows/docs/2025/09/windows-management-instrumentation-command-line-wmic-removal-from-windows).

## 📊 Ablauf der Vorfallbearbeitung (NIST)

```
┌──────────────────────────────┐
│       1. Vorbereitung        │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│   2. Erkennung und Analyse   │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│        3. Eindämmung         │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│        4. Beseitigung        │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│     5. Wiederherstellung     │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│  6. Erkenntnisse auswerten   │
│     Zurück zu Schritt 1      │
└──────────────────────────────┘
```

---

## 1️⃣ Vorbereitung

### Checkliste zur Dokumentation
```markdown
- [ ] Plan zur Vorfallbearbeitung dokumentiert
- [ ] Kontaktliste (intern und extern)
- [ ] Kommunikationsplan
- [ ] Inventar der IT-Ressourcen
- [ ] Netzwerkdiagramme
- [ ] Referenzkonfigurationen
- [ ] Sicherungskopien überprüft
```

### Kontaktvorlage für das IR-Team
```
IR-Leitung:         [Name]        - [Telefon] - [E-Mail]
IT-Sicherheit:      [Name]        - [Telefon] - [E-Mail]
Rechtsabteilung:    [Name]        - [Telefon] - [E-Mail]
Kommunikation:      [Name]        - [Telefon] - [E-Mail]
Personalabteilung:  [Name]        - [Telefon] - [E-Mail]
Externe Forensik:   [Unternehmen] - [Telefon]
Strafverfolgung:    [Behörde]     - [Telefon]
```

### Wichtige Werkzeuge zur Vorfallbearbeitung
```bash
# Forensische Datensicherung
- FTK Imager
- Volatility
- Velociraptor
- KAPE

# Netzwerkanalyse
- Wireshark
- tcpdump
- Zeek

# Protokollanalyse
- Splunk/ELK
- grep/awk/sed
```

---

## 2️⃣ Erkennung und Analyse

### Erste Fragen zur Triage (Ersteinschätzung)
```markdown
1. Um welche Art von Vorfall handelt es sich?
   - [ ] Malware-Infektion
   - [ ] Unbefugter Zugriff
   - [ ] Datenkompromittierung
   - [ ] Denial of Service (Dienstverweigerung)
   - [ ] Bedrohung durch interne Personen
   - [ ] Phishing

2. Wann wurde der Vorfall erkannt?
3. Wie wurde der Vorfall erkannt?
4. Welche Systeme sind betroffen?
5. Welche Daten könnten betroffen sein?
6. Dauert der Vorfall noch an?
```

### Schnelle Triage unter Windows
```cmd
# Aktuelle Benutzersitzungen
query user
qwinsta

# Aktive Verbindungen
netstat -ano | findstr ESTABLISHED

# Laufende Prozesse
tasklist /v
wmic process get processid,parentprocessid,commandline

# Kürzlich geänderte Dateien
forfiles /P C:\ /S /D +01/01/2024

# Geplante Aufgaben
schtasks /query /fo LIST /v

# Dienste
sc query state=all
wmic service get name,startmode,state

# Run-Schlüssel in der Registry
reg query "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"
reg query "HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"

# Zuletzt verwendete PowerShell-Befehle
type %APPDATA%\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt
```

### Schnelle Triage unter Linux
```bash
# Angemeldete Benutzer ermitteln
w
who
last -a

# Aktive Verbindungen
ss -tulnp
netstat -tulnp
lsof -i

# Laufende Prozesse
ps auxwww
pstree -p

# Kürzlich geänderte Dateien
find / -mtime -1 -type f 2>/dev/null

# Cron-Aufgaben
cat /etc/crontab
ls -la /etc/cron.*
for user in $(cut -f1 -d: /etc/passwd); do crontab -u $user -l 2>/dev/null; done

# Startskripte
ls -la /etc/init.d/
systemctl list-unit-files --type=service

# Befehlsverlauf
cat ~/.bash_history
cat /root/.bash_history
```

### Netzwerkanalyse
```bash
# Netzwerkverkehr aufzeichnen
tcpdump -i eth0 -w capture.pcap

# Nach einem bestimmten Host filtern
tcpdump -i eth0 host 192.168.1.100

# HTTP-Verkehr
tcpdump -i eth0 port 80 or port 443

# DNS-Abfragen
tcpdump -i eth0 port 53
```

---

## 3️⃣ Eindämmung

### Kurzfristige Eindämmung
```bash
# Netzwerk isolieren (Windows)
netsh advfirewall set allprofiles state on
netsh advfirewall firewall add rule name="Block All" dir=in action=block
netsh advfirewall firewall add rule name="Block All Out" dir=out action=block

# Netzwerk isolieren (Linux)
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

# Kompromittiertes Konto deaktivieren
net user compromised_user /active:no   # Windows
passwd -l compromised_user             # Linux

# Schädlichen Prozess beenden
taskkill /PID <pid> /F               # Windows
kill -9 <pid>                        # Linux
```

### Langfristige Eindämmung
```bash
# Netzwerk segmentieren
# Firewall-Regeln zur Isolation des betroffenen VLAN anwenden
# ACLs auf Switches und Routern aktualisieren

# Schädliche IP-Adressen und Domains an der Firewall blockieren
# Zur Sperrliste des Proxys hinzufügen
# DNS-Sinkhole aktualisieren

# Betroffene Dienste vorübergehend deaktivieren
sc config <service> start= disabled   # Windows
systemctl disable <service>           # Linux
```

### Beweissicherung
```bash
# Speicherabbild (Windows – WinPMEM oder DumpIt verwenden)
winpmem_mini_x64.exe memory.raw

# Speicherabbild (Linux)
dd if=/dev/mem of=/evidence/memory.dump bs=1M

# Datenträgerabbild
dd if=/dev/sda of=/evidence/disk.img bs=4M status=progress

# Hashwert zur Integritätsprüfung
sha256sum /evidence/disk.img > disk.img.sha256

# Flüchtige Daten zuerst sichern (Reihenfolge nach Flüchtigkeit):
# 1. Arbeitsspeicher
# 2. Netzwerkzustand
# 3. Laufende Prozesse
# 4. Datenträger
```

---

## 4️⃣ Beseitigung

### Malware entfernen
```bash
# Persistenzmechanismen erkennen und entfernen

# Windows - Registry
reg delete "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v "MalwareName" /f

# Windows – geplante Aufgaben
schtasks /delete /tn "MalwareTask" /f

# Windows – Dienste
sc delete "MalwareService"

# Linux - Cron
crontab -r
rm /etc/cron.d/malware

# Linux - Systemd
systemctl disable malware
rm /etc/systemd/system/malware.service
```

### Kennwörter zurücksetzen
```bash
# Kennwortzurücksetzung für alle kompromittierten Konten erzwingen
# Kennwörter der Dienstkonten zurücksetzen
# API-Schlüssel und Tokens erneuern
# Sitzungen ungültig machen

# Windows – Kennwortänderung erzwingen
net user username /logonpasswordchg:yes

# Kerberos-Tickets erneuern (bei AD-Kompromittierung)
# - krbtgt-Kennwort ZWEIMAL zurücksetzen
# - Kennwörter aller Administratorkonten zurücksetzen
```

### Patches und Updates
```bash
# Fehlende Sicherheitspatches einspielen
# Virenschutzsignaturen aktualisieren
# Fehlkonfigurationen beheben, die den Angriff ermöglicht haben
```

---

## 5️⃣ Wiederherstellung

### Systeme wiederherstellen
```bash
# Aus einer sauberen Sicherung wiederherstellen
# Kompromittierte Systeme neu aufsetzen
# Integrität vor der Wiederinbetriebnahme prüfen

# Schrittweise Wiederherstellung:
1. Kritische Systeme zuerst wiederherstellen
2. Auf Anzeichen einer erneuten Infektion achten
3. Weitere Systeme wiederherstellen
4. Zum Normalbetrieb zurückkehren
```

### Überwachung nach der Wiederherstellung
```bash
# Ausführlichkeit der Protokollierung erhöhen
# Zusätzliche Überwachung einrichten
# Auf eine Rückkehr des Angreifers achten

# Wichtige zu überwachende Indikatoren:
- Dieselben IOCs (IP-Adressen, Domains, Hashwerte)
- Ähnliche TTPs (Taktiken, Techniken und Vorgehensweisen)
- Zugriffsversuche auf dieselben Konten
- Kommunikation mit Command-and-Control-Infrastruktur (C2)
```

---

## 6️⃣ Erkenntnisse aus dem Vorfall

### Vorlage für den Abschlussbericht
```markdown
## Zusammenfassung des Vorfalls
- Vorfallskennung:
- Erkannt am:
- Eingedämmt am:
- Behoben am:
- Art des Vorfalls:
- Schweregrad:

## Zeitlicher Ablauf
| Datum/Uhrzeit | Ereignis |
|-----------|-------|
| YYYY-MM-DD HH:MM | Erste Erkennung |
| ... | ... |

## Bewertung der Auswirkungen
- Betroffene Systeme:
- Kompromittierte Daten:
- Geschäftliche Auswirkungen:
- Finanzielle Auswirkungen:

## Analyse der Grundursache
- Wie hat der Angreifer Zugriff erlangt?
- Welche Schwachstellen wurden ausgenutzt?
- Welche Schutzmaßnahmen haben versagt?

## Ergriffene Maßnahmen
1. Erkennung
2. Eindämmung
3. Beseitigung
4. Wiederherstellung

## Empfehlungen
1. Sofortige Verbesserungen
2. Langfristige Sicherheitsverbesserungen
3. Änderungen an Richtlinien

## Erkenntnisse aus dem Vorfall
- Was hat gut funktioniert?
- Was könnte verbessert werden?
- Welcher Schulungsbedarf wurde erkannt?
```

---

## 📊 Kurzreferenz

### Einstufung des Schweregrads

| Stufe | Beschreibung | Reaktionszeit |
|-------|-------------|---------------|
| **Kritisch** | Aktiver Einbruch, Datenabfluss | Sofort |
| **Hoch** | Bestätigte Kompromittierung | < 1 Stunde |
| **Mittel** | Mögliche Kompromittierung | < 4 Stunden |
| **Niedrig** | Verdächtige Aktivität | < 24 Stunden |

### Häufig zu sichernde IOCs (Kompromittierungsindikatoren)

| IOC-Typ | Beispiele |
|----------|----------|
| **Hash** | MD5, SHA1, SHA256 |
| **IP** | Quell-/Ziel-IP-Adressen |
| **Domain** | C2-Domains |
| **URL** | Schädliche URLs |
| **E-Mail** | Phishing-Absender |
| **Datei** | Dateiname der Malware |
| **Registry** | Registry-Schlüssel zur Persistenz |

---

## 🔗 Verwandte Spickzettel

- [Protokollanalyse](./Log-Analysis.de.md)
- [SIEM-Erkennung](./SIEM-Detection.de.md)
- [Volatility (Forensik)](../../Volatility/README.md)

---

**Weiter:** [Protokollanalyse →](./Log-Analysis.de.md)
