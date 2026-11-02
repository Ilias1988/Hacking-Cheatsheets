# 📊 Logganalyse – hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Log-Analysis.md) • [🇹🇷 Türkçe Sürüm](Log-Analysis.tr.md) • [🇬🇷 Ελληνικά](Log-Analysis.el.md)

```
  ██╗      ██████╗  ██████╗      █████╗ ███╗   ██╗ █████╗ ██╗  ██╗   ██╗███████╗██╗███████╗
  ██║     ██╔═══██╗██╔════╝     ██╔══██╗████╗  ██║██╔══██╗██║  ╚██╗ ██╔╝██╔════╝██║██╔════╝
  ██║     ██║   ██║██║  ███╗    ███████║██╔██╗ ██║███████║██║   ╚████╔╝ ███████╗██║███████╗
  ██║     ██║   ██║██║   ██║    ██╔══██║██║╚██╗██║██╔══██║██║    ╚██╔╝  ╚════██║██║╚════██║
  ███████╗╚██████╔╝╚██████╔╝    ██║  ██║██║ ╚████║██║  ██║███████╗██║   ███████║██║███████║
  ╚══════╝ ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝   ╚══════╝╚═╝╚══════╝
```

---

## 🖥️ Windows-hendelseslogger

### Loggplasseringer
```
# Security-logg
C:\Windows\System32\winevt\Logs\Security.evtx

# System-logg
C:\Windows\System32\winevt\Logs\System.evtx

# Application-logg
C:\Windows\System32\winevt\Logs\Application.evtx

# PowerShell-logger
C:\Windows\System32\winevt\Logs\Microsoft-Windows-PowerShell%4Operational.evtx
C:\Windows\System32\winevt\Logs\Windows PowerShell.evtx

# Sysmon (hvis installert)
C:\Windows\System32\winevt\Logs\Microsoft-Windows-Sysmon%4Operational.evtx
```

### Viktige sikkerhetshendelses-ID-er

#### Autentiseringshendelser
| Hendelses-ID | Beskrivelse | Betydning |
|----------|-------------|------------|
| **4624** | Vellykket pålogging | Spore brukertilgang |
| **4625** | Mislykket pålogging | Oppdage brute force, altså gjentatte innloggingsforsøk |
| **4634** | Avlogging | Spore økter |
| **4647** | Brukerinitiert avlogging | Spore økter |
| **4648** | Pålogging med eksplisitt angitt påloggingsinformasjon | Lateral bevegelse |
| **4672** | Spesielle rettigheter tildelt | Administratoraktivitet |
| **4768** | Forespørsel om Kerberos-TGT | AD-autentisering |
| **4769** | Kerberos-tjenestebillett | Tilgang til tjenester |
| **4771** | Mislykket Kerberos-forhåndsautentisering | Passordspraying |
| **4776** | NTLM-autentisering | Validering av påloggingsinformasjon |

#### Påloggingstyper (for 4624/4625)
| Type | Beskrivelse |
|------|-------------|
| 2 | Interaktiv (lokal) |
| 3 | Nettverk |
| 4 | Satsvis kjøring (batch) |
| 5 | Tjeneste |
| 7 | Opplåsing |
| 8 | Nettverk, klartekst |
| 9 | Ny påloggingsinformasjon |
| 10 | Ekstern interaktiv pålogging (RDP) |
| 11 | Bufret interaktiv pålogging |

#### Kontoadministrasjon
| Hendelses-ID | Beskrivelse |
|----------|-------------|
| **4720** | Brukerkonto opprettet |
| **4722** | Brukerkonto aktivert |
| **4723** | Forsøk på passordendring |
| **4724** | Tilbakestilling av passord |
| **4725** | Brukerkonto deaktivert |
| **4726** | Brukerkonto slettet |
| **4728** | Medlem lagt til i sikkerhetsgruppe |
| **4732** | Medlem lagt til i lokal gruppe |
| **4756** | Medlem lagt til i universell gruppe |

#### Prosess- og tjenestehendelser
| Hendelses-ID | Beskrivelse |
|----------|-------------|
| **4688** | Prosess opprettet |
| **4689** | Prosess avsluttet |
| **7045** | Tjeneste installert |
| **7036** | Tjenestetilstand endret |
| **4697** | Tjeneste installert (Security-loggen) |

#### Planlagte oppgaver
| Hendelses-ID | Beskrivelse |
|----------|-------------|
| **4698** | Planlagt oppgave opprettet |
| **4699** | Planlagt oppgave slettet |
| **4700** | Planlagt oppgave aktivert |
| **4702** | Planlagt oppgave oppdatert |

### Spørringskommandoer i PowerShell

> **Presisering:** Numeriske `Properties`-indekser avhenger av hendelsesskjema og versjon. Kontroller feltnavn og skjema før tolkning av resultatene; eksemplene er beholdt uendret.
```powershell
# Hent oppføringer fra Security-loggen
Get-WinEvent -LogName Security -MaxEvents 100

# Mislykkede pålogginger (4625)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4625} | 
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='IP';E={$_.Properties[19].Value}}

# Vellykkede pålogginger (4624)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='LogonType';E={$_.Properties[8].Value}}

# Ny prosess opprettet (4688)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4688} |
    Select-Object TimeCreated, @{N='Process';E={$_.Properties[5].Value}}, @{N='CommandLine';E={$_.Properties[8].Value}}

# Tjenesteinstallasjoner (7045)
Get-WinEvent -FilterHashtable @{LogName='System';ID=7045} |
    Select-Object TimeCreated, @{N='ServiceName';E={$_.Properties[0].Value}}

# Søk innenfor et tidsrom
$startTime = (Get-Date).AddDays(-7)
Get-WinEvent -FilterHashtable @{LogName='Security';StartTime=$startTime}
```

### Wevtutil-kommandoer
```cmd
# Spør mot Security-loggen
wevtutil qe Security /c:50 /f:text

# Spør etter en bestemt hendelses-ID
wevtutil qe Security /q:"*[System[(EventID=4625)]]" /c:100 /f:text

# Eksporter logg
wevtutil epl Security C:\evidence\security.evtx

# Tøm logg (bruk med varsomhet)
wevtutil cl Security
```

---

## 🐧 Logganalyse i Linux

### Loggplasseringer
```bash
# Autentisering
/var/log/auth.log        # Debian/Ubuntu
/var/log/secure          # RHEL/CentOS

# System
/var/log/syslog          # Debian/Ubuntu
/var/log/messages        # RHEL/CentOS

# Kjerne
/var/log/kern.log
/var/log/dmesg

# Cron
/var/log/cron

# Revisjonslogg
/var/log/audit/audit.log

# Webservere
/var/log/apache2/        # Apache
/var/log/nginx/          # Nginx
/var/log/httpd/          # Apache på RHEL

# SSH
/var/log/auth.log
```

### Vanlige kommandoer for logganalyse

#### Autentiseringsanalyse
```bash
# Mislykkede SSH-forsøk
grep "Failed password" /var/log/auth.log
grep "authentication failure" /var/log/auth.log

# Vellykkede SSH-pålogginger
grep "Accepted" /var/log/auth.log

# SSH-brute-force (tell per IP-adresse)
grep "Failed password" /var/log/auth.log | awk '{print $(NF-3)}' | sort | uniq -c | sort -rn

# Brukeres rettighetseskalering (sudo)
grep "sudo:" /var/log/auth.log
grep "session opened for user root" /var/log/auth.log

# su-forsøk
grep "su:" /var/log/auth.log
```

#### Systemanalyse
```bash
# Tidspunkter for oppstart og avslutning
last -x | grep shutdown
last -x | grep reboot

# Brukere som er pålogget nå
who
w

# Påloggingshistorikk
last
lastlog

# Mislykkede påloggingsforsøk
lastb
```

#### Analyse av prosesser og tjenester
```bash
# Kjøring av cron-jobber
grep CRON /var/log/syslog

# Oppstart og stopp av tjenester
grep -E "Started|Stopped" /var/log/syslog
journalctl -u <service-name>

# Kjernemeldinger
dmesg | tail -100
grep -i error /var/log/kern.log
```

### Nyttige grep-/awk-mønstre
```bash
# Hent IP-adresser fra logger
grep -oE '\b[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\b' /var/log/auth.log

# Tell forekomster per IP-adresse
cat /var/log/auth.log | grep "Failed" | awk '{print $11}' | sort | uniq -c | sort -rn | head -20

# Tidsbasert søk
awk '/Jan 15 10:00/,/Jan 15 11:00/' /var/log/auth.log

# Søk i flere filer
grep -r "error" /var/log/

# Sanntidsovervåking
tail -f /var/log/auth.log | grep --line-buffered "Failed"
```

### Journalctl (Systemd)
```bash
# Alle logger
journalctl

# Følg nye oppføringer
journalctl -f

# Fra et bestemt tidspunkt
journalctl --since "2024-01-15 10:00:00"
journalctl --since "1 hour ago"

# Etter enhet/tjeneste
journalctl -u sshd
journalctl -u nginx

# Etter prioritet
journalctl -p err       # Bare feil
journalctl -p warning   # Advarsler og høyere alvorlighetsgrad

# Kjernemeldinger
journalctl -k

# JSON-utdata
journalctl -o json
```

---

## 🌐 Webserverlogger

### Apache-loggformat
```
# Combined Log Format (kombinert loggformat)
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."

# Felter:
# IP - ident - bruker - [tidsstempel] "metode sti protokoll" status størrelse "referer" "user-agent"
```

### Nginx-loggformat
```
# Standardformatet ligner Apaches format
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."
```

### Analyse av webserverlogger
```bash
# Mest etterspurte URL-er
awk '{print $7}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# Hyppigste IP-adresser
awk '{print $1}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# HTTP-statuskoder
awk '{print $9}' /var/log/apache2/access.log | sort | uniq -c | sort -rn

# 404-feil
grep ' 404 ' /var/log/apache2/access.log

# POST-forespørsler (mulige angrep)
grep '"POST' /var/log/apache2/access.log

# Forsøk på SQL-injeksjon
grep -iE "(union|select|insert|update|delete|drop|script)" /var/log/apache2/access.log

# Forsøk på katalogtraversering (path traversal)
grep -E "(\.\./|\.\.\\)" /var/log/apache2/access.log

# Forespørsler per time
awk '{print $4}' /var/log/apache2/access.log | cut -d: -f2 | sort | uniq -c
```

---

## 🛡️ Analyse av Sysmon-logger

### Viktige Sysmon-hendelses-ID-er
| Hendelses-ID | Beskrivelse |
|----------|-------------|
| 1 | Prosess opprettet |
| 2 | Filens opprettelsestid endret |
| 3 | Nettverkstilkobling |
| 5 | Prosess avsluttet |
| 6 | Driver lastet |
| 7 | Programbilde lastet (DLL) |
| 8 | CreateRemoteThread |
| 10 | ProcessAccess |
| 11 | FileCreate |
| 12-14 | Registerhendelser |
| 15 | FileCreateStreamHash |
| 17-18 | Pipe-hendelser |
| 22 | DNS-spørring |

### Sysmon-spørringer i PowerShell
```powershell
# Prosess opprettet (hendelse 1)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='CommandLine';E={$_.Properties[10].Value}}

# Nettverkstilkoblinger (hendelse 3)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=3} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='DestIP';E={$_.Properties[14].Value}}

# DNS-spørringer (hendelse 22)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=22} |
    Select-Object TimeCreated, @{N='Query';E={$_.Properties[4].Value}}
```

---

## 📊 Hurtigreferanse

### Mistenkelige mønstre å se etter

> **Presisering:** Disse mønstrene er undersøkelsesspor. Hendelse 4672 eller tilgang til LSASS er ikke alene bevis på et angrep.

| Kategori | Mønster |
|----------|---------|
| **Brute force** | Mange 4625-hendelser fra samme IP-adresse |
| **Passordspraying** | 4625 for mange brukere fra samme IP-adresse |
| **Lateral bevegelse** | 4624 type 3 + 4648 |
| **Vedvarende tilgang** | 4698 (ny planlagt oppgave) |
| **Rettighetseskalering** | 4672 for kontoer uten administratorrolle |
| **Uthenting av påloggingsinformasjon** | Tilgang til lsass.exe |

### Verktøy for logganalyse
| Verktøy | Bruk |
|------|-----|
| **grep/awk/sed** | Tolking av Linux-logger |
| **PowerShell** | Analyse av Windows-logger |
| **Splunk** | SIEM for virksomheter |
| **ELK Stack** | SIEM med åpen kildekode |
| **LogParser** | SQL-spørringer mot logger |
| **Chainsaw** | Rask analyse av Windows-logger |

---

## 🔗 Relaterte hurtigreferanser

- [Hendelseshåndtering](Incident-Response.nb.md)
- [SIEM-deteksjon](SIEM-Detection.nb.md)
- [Sigma-regler](Sigma-Rules.nb.md)

---

**Forrige:** [← Hendelseshåndtering](Incident-Response.nb.md)

**Neste:** [SIEM-deteksjon →](SIEM-Detection.nb.md)
