# 📊 Cheatsheet Analisi dei Log

```
  ██╗      ██████╗  ██████╗      █████╗ ███╗   ██╗ █████╗ ██╗  ██╗   ██╗███████╗██╗███████╗
  ██║     ██╔═══██╗██╔════╝     ██╔══██╗████╗  ██║██╔══██╗██║  ╚██╗ ██╔╝██╔════╝██║██╔════╝
  ██║     ██║   ██║██║  ███╗    ███████║██╔██╗ ██║███████║██║   ╚████╔╝ ███████╗██║███████╗
  ██║     ██║   ██║██║   ██║    ██╔══██║██║╚██╗██║██╔══██║██║    ╚██╔╝  ╚════██║██║╚════██║
  ███████╗╚██████╔╝╚██████╔╝    ██║  ██║██║ ╚████║██║  ██║███████╗██║   ███████║██║███████║
  ╚══════╝ ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝   ╚══════╝╚═╝╚══════╝
```

---

## 🖥️ Log degli Eventi Windows

### Percorsi dei Log
```
# Security Log
C:\Windows\System32\winevt\Logs\Security.evtx

# System Log
C:\Windows\System32\winevt\Logs\System.evtx

# Application Log
C:\Windows\System32\winevt\Logs\Application.evtx

# PowerShell Logs
C:\Windows\System32\winevt\Logs\Microsoft-Windows-PowerShell%4Operational.evtx
C:\Windows\System32\winevt\Logs\Windows PowerShell.evtx

# Sysmon (se installato)
C:\Windows\System32\winevt\Logs\Microsoft-Windows-Sysmon%4Operational.evtx
```

### Event ID di Sicurezza Critici

#### Eventi di Autenticazione
| Event ID | Descrizione | Importanza |
|----------|-------------|------------|
| **4624** | Logon riuscito | Traccia accessi utenti |
| **4625** | Logon fallito | Individua brute force |
| **4634** | Logoff | Monitora durata sessioni |
| **4647** | Logoff avviato dall'utente | Monitora chiusura sessioni |
| **4648** | Logon con credenziali esplicite | Lateral Movement |
| **4672** | Privilegi speciali assegnati | Attività admin |
| **4768** | richiesta Kerberos TGT | Autenticazione AD |
| **4769** | Kerberos service ticket | Accesso servizi |
| **4771** | Kerberos pre-auth fallito | Password spray |
| **4776** | Autenticazione NTLM | Validazione credenziali |

#### Tipi di Logon (per 4624/4625)
| Tipo | Descrizione |
|------|-------------|
| 2 | Interattivo (locale) |
| 3 | Rete |
| 4 | Batch |
| 5 | Servizio |
| 7 | Sblocco |
| 8 | Rete in testo semplice |
| 9 | Nuove credenziali |
| 10 | Interattivo remoto (RDP) |
| 11 | Interattivo memorizzato (Cached) |

#### Gestione Account
| Event ID | Descrizione |
|----------|-------------|
| **4720** | Account utente creato |
| **4722** | Account utente abilitato |
| **4723** | Tentativo di cambio password |
| **4724** | Reset password |
| **4725** | Account utente disabilitato |
| **4726** | Account utente eliminato |
| **4728** | Membro aggiunto al gruppo di sicurezza |
| **4732** | Membro aggiunto al gruppo locale |
| **4756** | Membro aggiunto al gruppo universale |

#### Eventi Processi & Servizi
| Event ID | Descrizione |
|----------|-------------|
| **4688** | Creazione processo |
| **4689** | Terminazione processo |
| **7045** | Installazione servizio |
| **7036** | Cambio stato servizio |
| **4697** | Installazione servizio (Security log) |

#### Attività Pianificate
| Event ID | Descrizione |
|----------|-------------|
| **4698** | Task Programmato creato |
| **4699** | Task Programmato eliminato |
| **4700** | Task Programmato abilitato |
| **4702** | Task Programmato aggiornato |

### Comandi PowerShell per Query
```powershell
# Ricava record recenti dal Security log
Get-WinEvent -LogName Security -MaxEvents 100

# logon falliti (4625)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4625} | 
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='IP';E={$_.Properties[19].Value}}

# logon riusciti (4624)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='LogonType';E={$_.Properties[8].Value}}

# Nuovo processo creato (4688)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4688} |
    Select-Object TimeCreated, @{N='Process';E={$_.Properties[5].Value}}, @{N='CommandLine';E={$_.Properties[8].Value}}

# Installazione servizio (7045)
Get-WinEvent -FilterHashtable @{LogName='System';ID=7045} |
    Select-Object TimeCreated, @{N='ServiceName';E={$_.Properties[0].Value}}

# Cerca eventi in un intervallo di tempo
$startTime = (Get-Date).AddDays(-7)
Get-WinEvent -FilterHashtable @{LogName='Security';StartTime=$startTime}
```

### Comandi Wevtutil
```cmd
# Query Security log
wevtutil qe Security /c:50 /f:text

# Query con specifico Event ID
wevtutil qe Security /q:"*[System[(EventID=4625)]]" /c:100 /f:text

# Esporta log
wevtutil epl Security C:\evidence\security.evtx

# Pulisci log (usare con cautela)
wevtutil cl Security
```

---

## 🐧 Analisi Log Linux

### Percorsi dei Log
```bash
# Authentication
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

# Server web
/var/log/apache2/        # Apache
/var/log/nginx/          # Nginx
/var/log/httpd/          # RHEL Apache

# SSH
/var/log/auth.log
```

### Comandi Comuni per Analisi Log

#### Analisi Autenticazione
```bash
# Tentativi SSH falliti
grep "Failed password" /var/log/auth.log
grep "authentication failure" /var/log/auth.log

# Login SSH riusciti
grep "Accepted" /var/log/auth.log

# SSH brute force (conteggio per IP)
grep "Failed password" /var/log/auth.log | awk '{print $(NF-3)}' | sort | uniq -c | sort -rn

# PrivEsc utente (sudo)
grep "sudo:" /var/log/auth.log
grep "session opened for user root" /var/log/auth.log

# Tentativi su
grep "su:" /var/log/auth.log
```

#### Analisi Sistema
```bash
# Tempi di avvio/spegnimento
last -x | grep shutdown
last -x | grep reboot

# Utenti attualmente loggati
who
w

# Storico login
last
lastlog

# Tentativi di login errati
lastb
```

#### Analisi Processi/Servizi
```bash
# Esecuzioni cron job
grep CRON /var/log/syslog

# Avvio/arresto servizi
grep -E "Started|Stopped" /var/log/syslog
journalctl -u <service-name>

# Messaggi kernel
dmesg | tail -100
grep -i error /var/log/kern.log
```

### Pattern Utili Grep/Awk
```bash
# Estrai IP dai log
grep -oE '\b[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\b' /var/log/auth.log

# Conta occorrenze per IP
cat /var/log/auth.log | grep "Failed" | awk '{print $11}' | sort | uniq -c | sort -rn | head -20

# Ricerca per intervallo temporale
awk '/Jan 15 10:00/,/Jan 15 11:00/' /var/log/auth.log

# Cerca in più file
grep -r "error" /var/log/

# Monitoraggio in tempo reale
tail -f /var/log/auth.log | grep --line-buffered "Failed"
```

### Journalctl (Systemd)
```bash
# Tutti i log
journalctl

# Modalità follow
journalctl -f

# Da tempo specifico
journalctl --since "2024-01-15 10:00:00"
journalctl --since "1 hour ago"

# Per unità/servizio
journalctl -u sshd
journalctl -u nginx

# Per priorità (Severity)
journalctl -p err       # Solo errori
journalctl -p warning   # Warning e superiori

# Messaggi kernel
journalctl -k

# Output JSON
journalctl -o json
```

---

## 🌐 Log Web Server

### Formato Log Apache
```
# Combined Log Format
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."

# Campi:
# IP - Remote Logname - Remote User - [timestamp] "method path protocol" status size "referer" "user-agent"
```

### Formato Log Nginx
```
# Formato di default simile ad Apache
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."
```

### Analisi Log Web
```bash
# URL più richiesti
awk '{print $7}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# IP più attivi
awk '{print $1}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# Codici di stato HTTP
awk '{print $9}' /var/log/apache2/access.log | sort | uniq -c | sort -rn

# Errori 404
grep ' 404 ' /var/log/apache2/access.log

# Richieste POST (potenziali attacchi)
grep '"POST' /var/log/apache2/access.log

# Tentativi SQL injection
grep -iE "(union|select|insert|update|delete|drop|script)" /var/log/apache2/access.log

# Tentativi path traversal
grep -E "(\.\./|\.\.\\)" /var/log/apache2/access.log

# Richieste per ora
awk '{print $4}' /var/log/apache2/access.log | cut -d: -f2 | sort | uniq -c
```

---

| Event ID | Descrizione |
|----------|-------------|
| 1 | Creazione processo |
| 2 | Modifica orario creazione file |
| 3 | Connessione di rete |
| 5 | Terminazione processo |
| 6 | Driver caricato |
| 7 | Immagine caricata (DLL) |
| 8 | CreateRemoteThread |
| 10 | Accesso al processo |
| 11 | Creazione file |
| 12-14 | Eventi Registro |
| 15 | FileCreateStreamHash |
| 17-18 | Eventi Pipe |
| 22 | Query DNS |

### Query PowerShell Sysmon
```powershell
# Creazione processo (Event 1)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='CommandLine';E={$_.Properties[10].Value}}

# Connessioni di rete (Event 3)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=3} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='DestIP';E={$_.Properties[14].Value}}

# Query DNS (Event 22)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=22} |
    Select-Object TimeCreated, @{N='Query';E={$_.Properties[4].Value}}
```

---

## 📊 Riferimento Rapido

### Pattern Sospetti da Cercare

| Categoria | Pattern |
|----------|---------|
| **Brute Force** | Multiple 4625 from same IP |
| **Spray Attack** | 4625 to many users from same IP |
| **Lateral Movement** | 4624 Type 3 + 4648 |
| **Persistence** | 4698 (new scheduled task) |
| **Privesc** | 4672 on non-admin accounts |
| **Credential Dump** | Access to lsass.exe |

### Strumenti Analisi Log
| Tool | Uso |
|------|-----|
| **grep/awk/sed** | Linux log parsing |
| **PowerShell** | Windows log analysis |
| **Splunk** | Enterprise SIEM |
| **ELK Stack** | Open source SIEM |
| **LogParser** | SQL queries on logs |
| **Chainsaw** | Fast Windows log analysis |

---

## 🔗 Cheatsheet Correlate

- [Incident Response](./Incident-Response.it.md)
- [SIEM Detection](./SIEM-Detection.it.md)
- [Sigma Rules](./Sigma-Rules.it.md)

---

**Precedente:** [← Incident Response](./Incident-Response.it.md)

**Successivo:** [SIEM Detection →](./SIEM-Detection.it.md)
