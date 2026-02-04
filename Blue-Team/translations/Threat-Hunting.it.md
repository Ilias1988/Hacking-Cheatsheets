# 🎯 Threat Hunting Cheatsheet

```
████████╗██╗  ██╗██████╗ ███████╗ █████╗ ████████╗    ██╗  ██╗██╗   ██╗███╗   ██╗████████╗██╗███╗   ██╗ ██████╗ 
╚══██╔══╝██║  ██║██╔══██╗██╔════╝██╔══██╗╚══██╔══╝    ██║  ██║██║   ██║████╗  ██║╚══██╔══╝██║████╗  ██║██╔════╝ 
   ██║   ███████║██████╔╝█████╗  ███████║   ██║       ███████║██║   ██║██╔██╗ ██║   ██║   ██║██╔██╗ ██║██║  ███╗
   ██║   ██╔══██║██╔══██╗██╔══╝  ██╔══██║   ██║       ██╔══██║██║   ██║██║╚██╗██║   ██║   ██║██║╚██╗██║██║   ██║
   ██║   ██║  ██║██║  ██║███████╗██║  ██║   ██║       ██║  ██║╚██████╔╝██║ ╚████║   ██║   ██║██║ ╚████║╚██████╔╝
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝       ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Processo di Threat Hunting

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│ 1. IPOTESI  │────▶│ 2. RACCOLTA │────▶│ 3. ANALISI  │────▶│ 4. RISPOSTA │
│ (Teoria)    │     │ (Dati)      │     │ (Hunt)      │     │ (Azione)    │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
                                                                    │
                          ┌─────────────┐                          │
                          │ 5. DOCUMENTA│◀─────────────────────────┘
                          │ (Apprendi)  │
                          └─────────────┘
```

---

## 📋 Esempi di Ipotesi

| Categoria | Ipotesi |
|-----------|---------|
| **Initial Access** | Gli attaccanti potrebbero usare phishing per introdurre un malware |
| **Persistence** | Gli attaccanti potrebbero creare attività pianificate per la persistenza |
| **Credential** | Gli attaccanti potrebbero effettuare il dump di LSASS per raccogliere credenziali |
| **Lateral** | Gli attaccanti potrebbero usare RDP per compiere un Lateral Movement |
| **Exfiltration** | Gli attaccanti potrebbero usare DNS tunneling per rubare dati |

---

## 🔍 Tecniche di Hunting

### Ricerca di Persistence

#### Chiavi Run del Registro di Windows
```powershell
# Query di tutte le chiavi Run
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce" -ErrorAction SilentlyContinue
```

#### Attività Pianificate
```powershell
# Elenca tutte le attività pianificate
Get-ScheduledTask | Where-Object {$_.State -ne "Disabled"} | 
    Select-Object TaskName, TaskPath, State | Format-Table

# Cerca attività sospette (non Microsoft)
Get-ScheduledTask | Where-Object {$_.Author -notlike "*Microsoft*"} | 
    Select-Object TaskName, Author, State
```

#### Servizi
```powershell
# Servizi non firmati
Get-WmiObject Win32_Service | Where-Object {$_.PathName -notlike "*Windows*"} | 
    Select-Object Name, PathName, StartMode, State
```

### Ricerca di Lateral Movement

#### Connessioni RDP
```powershell
# Sessioni RDP (Evento 4624 Tipo 10)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} | 
    Where-Object {$_.Properties[8].Value -eq 10} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='Source';E={$_.Properties[18].Value}}
```

#### Connessioni di Rete
```powershell
# Connessioni attuali
Get-NetTCPConnection -State Established | 
    Select-Object LocalAddress, LocalPort, RemoteAddress, RemotePort, OwningProcess | 
    Format-Table

# Con nomi dei processi
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

### Ricerca di Accesso alle Credenziali

#### Accesso a LSASS
```powershell
# Evento Sysmon 10 - accesso a LSASS
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=10} |
    Where-Object {$_.Properties[8].Value -like "*lsass.exe"} |
    Select-Object TimeCreated, @{N='Source';E={$_.Properties[4].Value}}
```

### Ricerca di Esecuzione

#### PowerShell codificato
```powershell
# PowerShell con encoding
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-PowerShell/Operational';ID=4104} |
    Where-Object {$_.Message -match "FromBase64" -or $_.Message -match "-enc"} |
    Select-Object TimeCreated, Message
```

#### Parent-Child sospetti
```powershell
# Office che genera cmd/powershell
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Where-Object {
        ($_.Properties[20].Value -match "WINWORD|EXCEL|OUTLOOK") -and
        ($_.Properties[4].Value -match "cmd|powershell")
    }
```

---

## 🐧 Threat Hunting su Linux

### Persistence
```bash
# Cron jobs
cat /etc/crontab
for user in $(cut -f1 -d: /etc/passwd); do crontab -l -u $user 2>/dev/null; done

# Chiavi SSH autorizzate
find / -name "authorized_keys" 2>/dev/null

# Script di avvio
ls -la /etc/init.d/
systemctl list-unit-files --type=service | grep enabled

# Ricerca di binari con bit SUID impostato (potenziale PrivEsc)
find / -perm -4000 -type f 2>/dev/null
```

### Rete
```bash
# Porte in ascolto
ss -tulnp
netstat -tulnp

# Connessioni stabilite
ss -tnp state established
lsof -i -P -n | grep ESTABLISHED

# DNS insoliti
tcpdump -i any port 53 -w dns.pcap
```

### Processi
```bash
# Tutti i processi con comando completo
ps auxwww

# Albero dei processi
pstree -p

# Processi nascosti
ps aux | awk '{print $2}' | xargs -I{} ls -la /proc/{}/exe 2>/dev/null

# Binari modificati di recente
find /usr/bin /usr/sbin -mtime -7 2>/dev/null
```

---

## 📊 Ricerca IOC

### Ricerca Hash
```powershell
# Calcola hash
Get-FileHash -Algorithm SHA256 C:\sospetto.exe

# Cerca hash noto malevolo
$badHash = "abc123..."
Get-ChildItem -Path C:\ -Recurse -File | Get-FileHash -Algorithm SHA256 |
    Where-Object {$_.Hash -eq $badHash}
```

### Ricerca IP/Dominio
```powershell
# Cache DNS
Get-DnsClientCache | Where-Object {$_.Name -like "*sospetto*"}

# Connessione a IP noto malevolo
Get-NetTCPConnection | Where-Object {$_.RemoteAddress -eq "1.2.3.4"}
```

---

## 🛠️ Strumenti

| Strumento | Scopo |
|-----------|-------|
| **Velociraptor** | Endpoint hunting su larga scala |
| **OSQuery** | Query SQL sugli endpoint |
| **KAPE** | Raccolta triage |
| **Chainsaw** | Analisi rapida dei log Windows |
| **DeepBlueCLI** | Threat hunting su PowerShell |

---

## 📋 Checklist Rapida di Hunting

```markdown
□ Controlla le location di autorun (registro, servizi, attività)
□ Rivedi la creazione recente di processi (Evento 4688, Sysmon 1)
□ Analizza le connessioni di rete
□ Cerca comandi codificati
□ Cerca accesso a LSASS
□ Controlla relazioni parent-child di processi insolite
□ Rivedi i log di PowerShell
□ Cerca indicatori di lateral movement
```

---

## 🔗 Cheatsheet Correlate

- [Analisi Log](./Log-Analysis.it.md)
- [Rilevamento SIEM](./SIEM-Detection.it.md)
- [Regole Sigma](./Sigma-Rules.it.md)

---

**Precedente:** [← SIEM Detection](./SIEM-Detection.it.md)

**Successivo:** [Sigma Rules →](./Sigma-Rules.it.md)
