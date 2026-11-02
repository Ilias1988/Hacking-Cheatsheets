# 📊 Spickzettel zur Protokollanalyse

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./Log-Analysis.tr.md) • [🇬🇷 Ελληνικά](./Log-Analysis.el.md)

```
  ██╗      ██████╗  ██████╗      █████╗ ███╗   ██╗ █████╗ ██╗  ██╗   ██╗███████╗██╗███████╗
  ██║     ██╔═══██╗██╔════╝     ██╔══██╗████╗  ██║██╔══██╗██║  ╚██╗ ██╔╝██╔════╝██║██╔════╝
  ██║     ██║   ██║██║  ███╗    ███████║██╔██╗ ██║███████║██║   ╚████╔╝ ███████╗██║███████╗
  ██║     ██║   ██║██║   ██║    ██╔══██║██║╚██╗██║██╔══██║██║    ╚██╔╝  ╚════██║██║╚════██║
  ███████╗╚██████╔╝╚██████╔╝    ██║  ██║██║ ╚████║██║  ██║███████╗██║   ███████║██║███████║
  ╚══════╝ ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝   ╚══════╝╚═╝╚══════╝
```

---

> **Hinweise zu den Beispielen:** Protokolldateien und aktivierte Ereignisse hängen von Distribution, Dienst- und Überwachungsrichtlinien ab. Numerische `.Properties[...]`-Positionen müssen gegen die konkrete Ereignisversion geprüft werden. Das gezeigte IPv4-Muster prüft keine gültigen Oktettwerte, feste awk-Feldpositionen hängen vom Logformat ab. Die Windows-Muster sind Untersuchungshinweise, keine Beweise: Ereignis 4672 allein belegt keine Rechteausweitung. `wevtutil cl Security` löscht das Sicherheitsprotokoll und ist keine Beweissicherung. Die Path-Traversal-Regex des Originals muss für die verwendete Shell und grep-Version geprüft werden.

## 🖥️ Windows-Ereignisprotokolle

### Speicherorte der Protokolle
```
# Sicherheitsprotokoll
C:\Windows\System32\winevt\Logs\Security.evtx

# Systemprotokoll
C:\Windows\System32\winevt\Logs\System.evtx

# Anwendungsprotokoll
C:\Windows\System32\winevt\Logs\Application.evtx

# PowerShell-Protokolle
C:\Windows\System32\winevt\Logs\Microsoft-Windows-PowerShell%4Operational.evtx
C:\Windows\System32\winevt\Logs\Windows PowerShell.evtx

# Sysmon (falls installiert)
C:\Windows\System32\winevt\Logs\Microsoft-Windows-Sysmon%4Operational.evtx
```

### Wichtige IDs für Sicherheitsereignisse

#### Authentifizierungsereignisse
| Ereignis-ID | Beschreibung | Bedeutung |
|----------|-------------|------------|
| **4624** | Erfolgreiche Anmeldung | Benutzerzugriffe nachvollziehen |
| **4625** | Fehlgeschlagene Anmeldung | Brute-Force-Versuche erkennen |
| **4634** | Abmeldung | Sitzungen nachverfolgen |
| **4647** | Vom Benutzer ausgelöste Abmeldung | Sitzungen nachverfolgen |
| **4648** | Anmeldung mit expliziten Anmeldedaten | Seitwärtsbewegung im Netzwerk |
| **4672** | Besondere Rechte zugewiesen | Administratoraktivität |
| **4768** | Kerberos-TGT-Anforderung | AD-Authentifizierung |
| **4769** | Kerberos-Dienstticket | Zugriff auf Dienste |
| **4771** | Kerberos-Vorauthentifizierung fehlgeschlagen | Password Spraying |
| **4776** | NTLM-Authentifizierung | Anmeldedaten prüfen |

#### Anmeldetypen (für 4624/4625)
| Typ | Beschreibung |
|------|-------------|
| 2 | Interaktiv (lokal) |
| 3 | Netzwerk |
| 4 | Batch |
| 5 | Dienst |
| 7 | Entsperren |
| 8 | Netzwerk mit Klartext-Anmeldedaten |
| 9 | Neue Anmeldedaten |
| 10 | Interaktiv über Fernzugriff (RDP) |
| 11 | Interaktiv mit zwischengespeicherten Anmeldedaten |

#### Kontoverwaltung
| Ereignis-ID | Beschreibung |
|----------|-------------|
| **4720** | Benutzerkonto erstellt |
| **4722** | Benutzerkonto aktiviert |
| **4723** | Versuch einer Kennwortänderung |
| **4724** | Kennwort zurückgesetzt |
| **4725** | Benutzerkonto deaktiviert |
| **4726** | Benutzerkonto gelöscht |
| **4728** | Mitglied zu einer Sicherheitsgruppe hinzugefügt |
| **4732** | Mitglied zu einer lokalen Gruppe hinzugefügt |
| **4756** | Mitglied zu einer universellen Gruppe hinzugefügt |

#### Prozess- und Dienstereignisse
| Ereignis-ID | Beschreibung |
|----------|-------------|
| **4688** | Prozesserstellung |
| **4689** | Prozessbeendigung |
| **7045** | Dienst installiert |
| **7036** | Dienststatus geändert |
| **4697** | Dienst installiert (Sicherheitsprotokoll) |

#### Geplante Aufgaben
| Ereignis-ID | Beschreibung |
|----------|-------------|
| **4698** | Geplante Aufgabe erstellt |
| **4699** | Geplante Aufgabe gelöscht |
| **4700** | Geplante Aufgabe aktiviert |
| **4702** | Geplante Aufgabe aktualisiert |

### PowerShell-Abfragen
```powershell
# Einträge des Sicherheitsprotokolls abrufen
Get-WinEvent -LogName Security -MaxEvents 100

# Fehlgeschlagene Anmeldungen (4625)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4625} | 
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='IP';E={$_.Properties[19].Value}}

# Erfolgreiche Anmeldungen (4624)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='LogonType';E={$_.Properties[8].Value}}

# Neue Prozesserstellungen (4688)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4688} |
    Select-Object TimeCreated, @{N='Process';E={$_.Properties[5].Value}}, @{N='CommandLine';E={$_.Properties[8].Value}}

# Dienstinstallationen (7045)
Get-WinEvent -FilterHashtable @{LogName='System';ID=7045} |
    Select-Object TimeCreated, @{N='ServiceName';E={$_.Properties[0].Value}}

# Nach Zeitbereich suchen
$startTime = (Get-Date).AddDays(-7)
Get-WinEvent -FilterHashtable @{LogName='Security';StartTime=$startTime}
```

### Wevtutil-Befehle
```cmd
# Sicherheitsprotokoll abfragen
wevtutil qe Security /c:50 /f:text

# Nach einer bestimmten Ereignis-ID suchen
wevtutil qe Security /q:"*[System[(EventID=4625)]]" /c:100 /f:text

# Protokoll exportieren
wevtutil epl Security C:\evidence\security.evtx

# Protokoll löschen (mit Vorsicht verwenden)
wevtutil cl Security
```

---

## 🐧 Linux-Protokollanalyse

### Speicherorte der Protokolle
```bash
# Authentifizierung
/var/log/auth.log        # Debian/Ubuntu
/var/log/secure          # RHEL/CentOS

# System
/var/log/syslog          # Debian/Ubuntu
/var/log/messages        # RHEL/CentOS

# Kernel
/var/log/kern.log
/var/log/dmesg

# Cron
/var/log/cron

# Audit
/var/log/audit/audit.log

# Webserver
/var/log/apache2/        # Apache
/var/log/nginx/          # Nginx
/var/log/httpd/          # RHEL Apache

# SSH
/var/log/auth.log
```

### Häufige Befehle zur Protokollanalyse

#### Authentifizierung analysieren
```bash
# Fehlgeschlagene SSH-Anmeldeversuche
grep "Failed password" /var/log/auth.log
grep "authentication failure" /var/log/auth.log

# Erfolgreiche SSH-Anmeldungen
grep "Accepted" /var/log/auth.log

# SSH-Brute-Force-Versuche (nach IP-Adresse zählen)
grep "Failed password" /var/log/auth.log | awk '{print $(NF-3)}' | sort | uniq -c | sort -rn

# Rechteausweitung durch Benutzer (sudo)
grep "sudo:" /var/log/auth.log
grep "session opened for user root" /var/log/auth.log

# su-Versuche
grep "su:" /var/log/auth.log
```

#### Systemanalyse
```bash
# Start- und Abschaltzeiten
last -x | grep shutdown
last -x | grep reboot

# Aktuell angemeldete Benutzer
who
w

# Anmeldeverlauf
last
lastlog

# Fehlgeschlagene Anmeldeversuche
lastb
```

#### Prozesse und Dienste analysieren
```bash
# Ausführungen von Cron-Aufgaben
grep CRON /var/log/syslog

# Dienststarts und -stopps
grep -E "Started|Stopped" /var/log/syslog
journalctl -u <service-name>

# Kernel-Meldungen
dmesg | tail -100
grep -i error /var/log/kern.log
```

### Nützliche Grep-/Awk-Muster
```bash
# IP-Adressen aus Protokollen extrahieren
grep -oE '\b[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\b' /var/log/auth.log

# Vorkommen nach IP-Adresse zählen
cat /var/log/auth.log | grep "Failed" | awk '{print $11}' | sort | uniq -c | sort -rn | head -20

# Zeitbasierte Suche
awk '/Jan 15 10:00/,/Jan 15 11:00/' /var/log/auth.log

# Mehrere Dateien durchsuchen
grep -r "error" /var/log/

# Echtzeitüberwachung
tail -f /var/log/auth.log | grep --line-buffered "Failed"
```

### Journalctl (Systemd)
```bash
# Alle Protokolle
journalctl

# Fortlaufende Ausgabe
journalctl -f

# Seit einem bestimmten Zeitpunkt
journalctl --since "2024-01-15 10:00:00"
journalctl --since "1 hour ago"

# Nach Unit oder Dienst
journalctl -u sshd
journalctl -u nginx

# Nach Priorität
journalctl -p err       # Nur Fehler
journalctl -p warning   # Warnungen und höhere Schweregrade

# Kernel-Meldungen
journalctl -k

# JSON-Ausgabe
journalctl -o json
```

---

## 🌐 Webserver-Protokolle

### Apache-Protokollformat
```
# Combined Log Format (kombiniertes Protokollformat)
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."

# Felder:
# IP - Ident - Benutzer - [Zeitstempel] "Methode Pfad Protokoll" Status Größe "Referer" "User-Agent"
```

### Nginx-Protokollformat
```
# Standardformat ähnlich wie bei Apache
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."
```

### Webprotokolle analysieren
```bash
# Am häufigsten angeforderte URLs
awk '{print $7}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# Häufigste IP-Adressen
awk '{print $1}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# HTTP-Statuscodes
awk '{print $9}' /var/log/apache2/access.log | sort | uniq -c | sort -rn

# 404-Fehler
grep ' 404 ' /var/log/apache2/access.log

# POST-Anfragen (mögliche Angriffe)
grep '"POST' /var/log/apache2/access.log

# SQL-Injection-Versuche
grep -iE "(union|select|insert|update|delete|drop|script)" /var/log/apache2/access.log

# Path-Traversal-Versuche
grep -E "(\.\./|\.\.\\)" /var/log/apache2/access.log

# Anfragen pro Stunde
awk '{print $4}' /var/log/apache2/access.log | cut -d: -f2 | sort | uniq -c
```

---

## 🛡️ Sysmon-Protokollanalyse

### Wichtige Sysmon-Ereignis-IDs
| Ereignis-ID | Beschreibung |
|----------|-------------|
| 1 | Prozesserstellung |
| 2 | Dateierstellungszeit geändert |
| 3 | Netzwerkverbindung |
| 5 | Prozess beendet |
| 6 | Treiber geladen |
| 7 | Image geladen (DLL) |
| 8 | CreateRemoteThread (Thread in einem anderen Prozess erstellt) |
| 10 | ProcessAccess (Prozesszugriff) |
| 11 | FileCreate (Dateierstellung) |
| 12-14 | Registry-Ereignisse |
| 15 | FileCreateStreamHash (Dateistream und Hashwert) |
| 17-18 | Pipe-Ereignisse |
| 22 | DNS-Abfrage |

### Sysmon-Abfragen mit PowerShell
```powershell
# Prozesserstellung (Ereignis 1)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='CommandLine';E={$_.Properties[10].Value}}

# Netzwerkverbindungen (Ereignis 3)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=3} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='DestIP';E={$_.Properties[14].Value}}

# DNS-Abfragen (Ereignis 22)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=22} |
    Select-Object TimeCreated, @{N='Query';E={$_.Properties[4].Value}}
```

---

## 📊 Kurzreferenz

### Verdächtige Muster, auf die zu achten ist

| Kategorie | Muster |
|----------|---------|
| **Brute Force (systematisches Ausprobieren)** | Mehrere Ereignisse 4625 von derselben IP-Adresse |
| **Password Spraying** | 4625 für viele Benutzer von derselben IP-Adresse |
| **Lateral Movement (Seitwärtsbewegung im Netzwerk)** | 4624 Typ 3 + 4648 |
| **Persistenz (dauerhafter Zugriff)** | 4698 (neue geplante Aufgabe) |
| **Rechteausweitung** | 4672 bei Konten ohne Administratorrolle |
| **Auslesen von Anmeldedaten** | Zugriff auf lsass.exe |

### Werkzeuge zur Protokollanalyse
| Werkzeug | Verwendung |
|------|-----|
| **grep/awk/sed** | Linux-Protokolle auswerten |
| **PowerShell** | Windows-Protokollanalyse |
| **Splunk** | SIEM für Unternehmen |
| **ELK Stack** | Open-Source-SIEM |
| **LogParser** | SQL-Abfragen auf Protokolldaten |
| **Chainsaw** | Schnelle Windows-Protokollanalyse |

---

## 🔗 Verwandte Spickzettel

- [Incident Response (Vorfallbearbeitung)](./Incident-Response.de.md)
- [SIEM-Erkennung](./SIEM-Detection.de.md)
- [Sigma-Regeln](./Sigma-Rules.de.md)

---

**Zurück:** [← Incident Response (Vorfallbearbeitung)](./Incident-Response.de.md)

**Weiter:** [SIEM-Erkennung →](./SIEM-Detection.de.md)
