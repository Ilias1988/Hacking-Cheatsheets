# 🎯 Spickzettel zum Threat Hunting

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./Threat-Hunting.tr.md) • [🇬🇷 Ελληνικά](./Threat-Hunting.el.md)

```
████████╗██╗  ██╗██████╗ ███████╗ █████╗ ████████╗    ██╗  ██╗██╗   ██╗███╗   ██╗████████╗██╗███╗   ██╗ ██████╗ 
╚══██╔══╝██║  ██║██╔══██╗██╔════╝██╔══██╗╚══██╔══╝    ██║  ██║██║   ██║████╗  ██║╚══██╔══╝██║████╗  ██║██╔════╝ 
   ██║   ███████║██████╔╝█████╗  ███████║   ██║       ███████║██║   ██║██╔██╗ ██║   ██║   ██║██╔██╗ ██║██║  ███╗
   ██║   ██╔══██║██╔══██╗██╔══╝  ██╔══██║   ██║       ██╔══██║██║   ██║██║╚██╗██║   ██║   ██║██║╚██╗██║██║   ██║
   ██║   ██║  ██║██║  ██║███████╗██║  ██║   ██║       ██║  ██║╚██████╔╝██║ ╚████║   ██║   ██║██║ ╚████║╚██████╔╝
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝       ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

> **Hinweise zu den Originalbeispielen:** Threat Hunting ist die hypothesengeleitete, proaktive Suche nach bisher unerkannten Bedrohungen. Die Dienstabfrage prüft lediglich, ob der Pfad „Windows“ enthält; eine digitale Signatur prüft sie nicht. Die Linux-Abfrage zu versteckten Prozessen listet von `ps` bereits gemeldete Prozesse und ihre `/proc`-Verweise auf; sie weist keine verborgenen Prozesse nach. Numerische `.Properties[...]`-Positionen sind versionsabhängig. `Get-WmiObject` ist für Windows PowerShell gedacht; in PowerShell 7 sind CIM-Cmdlets zu verwenden. Ein Treffer in einer Heuristik ist ein Anlass zur Untersuchung, kein alleiniger Kompromittierungsnachweis.

## 🎯 Ablauf des Threat Hunting

```
┌──────────────────────────────┐
│     1. Hypothese bilden      │
│     Annahme formulieren      │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│       2. Daten sammeln       │
│    Beobachtungen erfassen    │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│        3. Analysieren        │
│      Bedrohungen suchen      │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│         4. Reagieren         │
│     Maßnahmen ergreifen      │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│       5. Dokumentieren       │
│   Erkenntnisse festhalten    │
└──────────────────────────────┘
```

---

## 📋 Beispielhypothesen

| Kategorie | Hypothese |
|----------|------------|
| **Erstzugriff** | Angreifer könnten Phishing zur Verbreitung von Malware einsetzen |
| **Persistenz (dauerhafter Zugriff)** | Angreifer könnten geplante Aufgaben zur Aufrechterhaltung ihres Zugriffs erstellen |
| **Anmeldedaten** | Angreifer könnten LSASS auslesen, um Anmeldedaten zu erlangen |
| **Seitwärtsbewegung** | Angreifer könnten RDP für Seitwärtsbewegungen im Netzwerk nutzen |
| **Exfiltration (Datenabfluss)** | Angreifer könnten Daten durch DNS-Tunneling ausschleusen |

---

## 🔍 Techniken zur Bedrohungssuche

### Nach Persistenz suchen

#### Run-Schlüssel der Windows-Registry
```powershell
# Alle Run-Schlüssel abfragen
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce" -ErrorAction SilentlyContinue
```

#### Geplante Aufgaben
```powershell
# Alle geplanten Aufgaben auflisten
Get-ScheduledTask | Where-Object {$_.State -ne "Disabled"} | 
    Select-Object TaskName, TaskPath, State | Format-Table

# Nach verdächtigen Aufgaben suchen (nicht von Microsoft)
Get-ScheduledTask | Where-Object {$_.Author -notlike "*Microsoft*"} | 
    Select-Object TaskName, Author, State
```

#### Dienste
```powershell
# Nicht signierte Dienste (Behauptung im Original; siehe Hinweis)
Get-WmiObject Win32_Service | Where-Object {$_.PathName -notlike "*Windows*"} | 
    Select-Object Name, PathName, StartMode, State
```

### Nach Seitwärtsbewegungen im Netzwerk suchen

#### RDP-Verbindungen
```powershell
# RDP-Sitzungen (Ereignis 4624, Typ 10)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} | 
    Where-Object {$_.Properties[8].Value -eq 10} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='Source';E={$_.Properties[18].Value}}
```

#### Netzwerkverbindungen
```powershell
# Aktuelle Verbindungen
Get-NetTCPConnection -State Established | 
    Select-Object LocalAddress, LocalPort, RemoteAddress, RemotePort, OwningProcess | 
    Format-Table

# Mit Prozessnamen
Get-NetTCPConnection | ForEach-Object {
    $proc = Get-Process -Id $_.OwningProcess -ErrorAction SilentlyContinue
    [PSCustomObject]@{
        LocalAddress = $_.LocalAddress
        RemoteAddress = $_.RemoteAddress
        RemotePort = $_.RemotePort
        Process = $proc.ProcessName
    }
} | Format-Table
```

### Nach Zugriffen auf Anmeldedaten suchen

#### Zugriff auf LSASS
```powershell
# Sysmon-Ereignis 10 – Zugriff auf LSASS
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=10} |
    Where-Object {$_.Properties[8].Value -like "*lsass.exe"} |
    Select-Object TimeCreated, @{N='Source';E={$_.Properties[4].Value}}
```

### Nach verdächtiger Ausführung suchen

#### Kodierte PowerShell-Befehle
```powershell
# PowerShell mit Kodierung
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-PowerShell/Operational';ID=4104} |
    Where-Object {$_.Message -match "FromBase64" -or $_.Message -match "-enc"} |
    Select-Object TimeCreated, Message
```

#### Verdächtige Eltern-Kind-Beziehungen
```powershell
# Office startet cmd oder PowerShell
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Where-Object {
        ($_.Properties[20].Value -match "WINWORD|EXCEL|OUTLOOK") -and
        ($_.Properties[4].Value -match "cmd|powershell")
    }
```

---

## 🐧 Bedrohungssuche unter Linux

### Persistenz (dauerhafter Zugriff)
```bash
# Cron-Aufgaben
cat /etc/crontab
for user in $(cut -f1 -d: /etc/passwd); do crontab -l -u $user 2>/dev/null; done

# Für SSH autorisierte Schlüssel
find / -name "authorized_keys" 2>/dev/null

# Startskripte
ls -la /etc/init.d/
systemctl list-unit-files --type=service | grep enabled

# SUID-Programme
find / -perm -4000 -type f 2>/dev/null
```

### Netzwerk
```bash
# Lauschende Ports
ss -tulnp
netstat -tulnp

# Bestehende Verbindungen
ss -tnp state established
lsof -i -P -n | grep ESTABLISHED

# Ungewöhnlichen DNS-Verkehr erfassen
tcpdump -i any port 53 -w dns.pcap
```

### Prozesse
```bash
# Alle Prozesse mit vollständiger Befehlszeile
ps auxwww

# Prozessbaum
pstree -p

# Versteckte Prozesse (Behauptung im Original; siehe Hinweis)
ps aux | awk '{print $2}' | xargs -I{} ls -la /proc/{}/exe 2>/dev/null

# Kürzlich geänderte Programme
find /usr/bin /usr/sbin -mtime -7 2>/dev/null
```

---

## 📊 Nach Kompromittierungsindikatoren suchen

### Nach Hashwerten suchen
```powershell
# Hashwerte berechnen
Get-FileHash -Algorithm SHA256 C:\suspicious.exe

# Nach einem bekannten schädlichen Hashwert suchen
$badHash = "abc123..."
Get-ChildItem -Path C:\ -Recurse -File | Get-FileHash -Algorithm SHA256 |
    Where-Object {$_.Hash -eq $badHash}
```

### Nach IP-Adressen und Domains suchen
```powershell
# DNS-Cache
Get-DnsClientCache | Where-Object {$_.Name -like "*suspicious*"}

# Verbindung zu einer bekannten schädlichen IP-Adresse
Get-NetTCPConnection | Where-Object {$_.RemoteAddress -eq "1.2.3.4"}
```

---

## 🛠️ Werkzeuge

| Werkzeug | Zweck |
|------|---------|
| **Velociraptor** | Bedrohungssuche auf vielen Endgeräten |
| **OSQuery** | SQL-basierte Abfragen auf Endgeräten |
| **KAPE** | Datensicherung für die Ersteinschätzung |
| **Chainsaw** | Schnelle Windows-Protokollanalyse |
| **DeepBlueCLI** | Bedrohungssuche mit PowerShell |

---

## 📋 Kurze Checkliste zur Bedrohungssuche

```markdown
□ Autostartorte prüfen (Registry, Dienste, Aufgaben)
□ Kürzlich erstellte Prozesse prüfen (Ereignis 4688, Sysmon 1)
□ Netzwerkverbindungen analysieren
□ Nach kodierten Befehlen suchen
□ Nach Zugriffen auf LSASS suchen
□ Ungewöhnliche Eltern-Kind-Beziehungen bei Prozessen prüfen
□ PowerShell-Protokolle prüfen
□ Nach Hinweisen auf Seitwärtsbewegungen im Netzwerk suchen
```

---

## 🔗 Verwandte Spickzettel

- [Protokollanalyse](./Log-Analysis.de.md)
- [SIEM-Erkennung](./SIEM-Detection.de.md)
- [Sigma-Regeln](./Sigma-Rules.de.md)

---

**Zurück:** [← SIEM-Erkennung](./SIEM-Detection.de.md)

**Weiter:** [Sigma-Regeln →](./Sigma-Rules.de.md)
