# 🚨 Cheatsheet di Incident Response

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

## 📊 Ciclo di Incident Response (NIST)

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│ 1.PREPARAZIONE│────▶│ 2.RILEVAMENTO│────▶│3.CONTENIMENTO│────▶│4.ERADICAZIONE│
│              │     │ & ANALISI    │     │              │     │              │
└──────────────┘     └──────────────┘     └──────────────┘     └──────────────┘
       ▲                                                              │
       │         ┌──────────────┐     ┌──────────────┐               │
       └─────────│ 6.LEZIONI    │◀────│ 5.RIPRISTINO │◀──────────────┘
                 │   APPRESE    │     │              │
                 └──────────────┘     └──────────────┘
```

---

## 1️⃣ Preparazione

### Checklist Documentale
```markdown
- [ ] Incident Response Plan documentato
- [ ] Lista contatti (interni & esterni)
- [ ] Piano di comunicazione
- [ ] Inventario asset
- [ ] Diagrammi di rete
- [ ] Configurazioni baseline
- [ ] Verifica dei backup
```

### Template Contatti IR Team
```
IR Lead:          [Nome] - [Telefono] - [Email]
IT Security:      [Nome] - [Telefono] - [Email]
Legal:            [Nome] - [Telefono] - [Email]
Comunicazioni:    [Nome] - [Telefono] - [Email]
HR:               [Nome] - [Telefono] - [Email]
Forensics esterni: [Azienda] - [Telefono]
Forze dell'ordine: [Agenzia] - [Telefono]
```

### Strumenti IR Essenziali
```bash
# Raccolta forense
- FTK Imager
- Volatility
- Velociraptor
- KAPE

# Analisi di rete
- Wireshark
- tcpdump
- Zeek

# Analisi log
- Splunk/ELK
- grep/awk/sed
```

---

## 2️⃣ Rilevamento & Analisi

### Domande di Triage Iniziale
```markdown
1. Che tipo di incidente è?
   - [ ] Malware infection
   - [ ] Unauthorized access
   - [ ] Data breach
   - [ ] Denial of Service
   - [ ] Insider threat
   - [ ] Phishing

2. Quando è stato rilevato?
3. Come è stato rilevato?
4. Quali sistemi sono coinvolti?
5. Quali dati potrebbero essere impattati?
6. È ancora attivo?
```

### Windows Quick Triage
```cmd
# Sessioni utente correnti
query user
qwinsta

# Connessioni attive
netstat -ano | findstr ESTABLISHED

# Processi in esecuzione
tasklist /v
wmic process get processid,parentprocessid,commandline

# Modifiche recenti ai file
forfiles /P C:\ /S /D +01/01/2024

# Task programmati
schtasks /query /fo LIST /v

# Servizi
sc query state=all
wmic service get name,startmode,state

# Chiavi Run del registro
reg query "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"
reg query "HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"

# Cronologia PowerShell recente
type %APPDATA%\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt
```

### Triage Rapido Linux
```bash
# Chi è loggato
w
who
last -a

# Connessioni attive
ss -tulnp
netstat -tulnp
lsof -i

# Processi in esecuzione
ps auxwww
pstree -p

# Modifiche recenti ai file
find / -mtime -1 -type f 2>/dev/null

# Cron jobs
cat /etc/crontab
ls -la /etc/cron.*
for user in $(cut -f1 -d: /etc/passwd); do crontab -u $user -l 2>/dev/null; done

# Script di avvio
ls -la /etc/init.d/
systemctl list-unit-files --type=service

# Cronologia
cat ~/.bash_history
cat /root/.bash_history
```

### Analisi di Rete
```bash
# Cattura traffico
tcpdump -i eth0 -w capture.pcap

# Filtra host specifico
tcpdump -i eth0 host 192.168.1.100

# Traffico HTTP
tcpdump -i eth0 port 80 or port 443

# Query DNS
tcpdump -i eth0 port 53
```

---

## 3️⃣ Contenimento

### Contenimento a Breve Termine
```bash
# Isola la rete (Windows)
netsh advfirewall set allprofiles state on
netsh advfirewall firewall add rule name="Block All" dir=in action=block
netsh advfirewall firewall add rule name="Block All Out" dir=out action=block

# Isola la rete (Linux)
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

# Disabilita account compromesso
net user compromised_user /active:no   # Windows
passwd -l compromised_user             # Linux

# Termina processo malevolo
taskkill /PID <pid> /F               # Windows
kill -9 <pid>                        # Linux
```

### Contenimento a Lungo Termine
```bash
# Segmenta la rete
# Applica regole firewall per isolare la VLAN coinvolta
# Aggiorna ACL su switch/router

# Blocca IP/domain malevoli sul firewall
# Aggiungi alla blacklist del proxy
# Aggiorna DNS sinkhole

# Disabilita temporaneamente i servizi coinvolti
sc config <service> start= disabled   # Windows
systemctl disable <service>           # Linux
```

### Preservazione delle Prove
```bash
# Dump memoria (Windows - usa WinPMEM o DumpIt)
winpmem_mini_x64.exe memory.raw

# Dump memoria (Linux)
dd if=/dev/mem of=/evidence/memory.dump bs=1M

# Immagine disco
dd if=/dev/sda of=/evidence/disk.img bs=4M status=progress

# Hash per integrità
sha256sum /evidence/disk.img > disk.img.sha256

# Cattura prima i dati volatili (ordine di volatilità):
# 1. Memoria
# 2. Stato di rete
# 3. Processi in esecuzione
# 4. Disco
```

---

## 4️⃣ Eradicazione

### Rimozione Malware
```bash
# Identifica e rimuovi la persistenza

# Windows - Registro
reg delete "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v "MalwareName" /f

# Windows - Task Programmati
schtasks /delete /tn "MalwareTask" /f

# Windows - Servizi
sc delete "MalwareService"

# Linux - Cron
crontab -r
rm /etc/cron.d/malware

# Linux - Systemd
systemctl disable malware
rm /etc/systemd/system/malware.service
```

### Reset Password
```bash
# Forza reset password per tutti gli account compromessi
# Reset password account di servizio
# Ruota Chiavi API/token
# Invalida le sessioni

# Windows - Forza cambio password
net user username /logonpasswordchg:yes

# Reset ticket Kerberos (se compromesso AD)
# - Reset password krbtgt DUE VOLTE
# - Reset password di tutti gli account admin
```

### Patch & Update
```bash
# Applica patch di sicurezza mancanti
# Aggiorna firme AV
# Correggi le configurazioni errate che hanno permesso l'attacco
```

---

## 5️⃣ Recupero

### Ripristino Sistemi
```bash
# Ripristina da backup pulito
# Ricostruisci i sistemi compromessi
# Valida l'integrità prima di rimettere online

# Ripristino graduale:
1. Ripristina prima i sistemi critici
2. Monitora segni di reinfezione
3. Ripristina altri sistemi
4. Ritorna alle normali operazioni
```

### Monitoraggio (Post-Recupero)
```bash
# Aumenta la verbosità dei log
# Implementa monitoraggio aggiuntivo
# Monitora il ritorno dell'attaccante

# Indicatori chiave da monitorare:
- Stessi IOC (IP, domini, hash)
- TTP simili
- Tentativi di accesso agli stessi account
- Comunicazione verso infrastruttura C2
```

---

## 6️⃣ Lezioni Apprese

### Template Report Post-Incident
```markdown
## Sommario Incidente
- Incident ID:
- Data rilevamento:
- Data contenimento:
- Data risoluzione:
- Tipo di incidente:
- Gravità:

## Timeline
| Data/Ora | Evento |
|----------|--------|
| AAAA-MM-GG HH:MM | Rilevamento iniziale |
| ... | ... |

## Valutazione Impatto
- Sistemi coinvolti:
- Dati compromessi:
- Impatto business:
- Impatto finanziario:

## Analisi Root Cause
- Come ha avuto accesso l'attaccante?
- Quali vulnerabilità sono state sfruttate?
- Quali controlli hanno fallito?

## Azioni intraprese
1. Rilevamento
2. Contenimento
3. Eradicazione
4. Recupero

## Raccomandazioni
1. Miglioramenti immediati
2. Miglioramenti di sicurezza a lungo termine
3. Modifiche alle policy

## Lezioni apprese
- Cosa ha funzionato bene?
- Cosa può essere migliorato?
- Necessità di formazione individuate?
```

---

## 📊 Riferimento Rapido

### Classificazione Gravità

| Livello | Descrizione | Tempo di risposta |
|---------|-------------|-------------------|
| **Critical** | Active breach, data exfil | Immediato |
| **High** | Compromissione confermata | < 1 ora |
| **Medium** | Potenziale compromissione | < 4 ore |
| **Low** | Attività sospetta | < 24 ore |

### IOC comuni da raccogliere

| Tipo IOC | Esempi |
|----------|--------|
| **Hash** | MD5, SHA1, SHA256 |
| **IP** | IP sorgente/destinazione |
| **Domain** | Domini C2 |
| **URL** | URL malevoli |
| **Email** | Mittente phishing |
| **File** | Nome file malware |
| **Registry** | Meccanismi di persistenza |

---

## 🔗 Cheatsheet correlate

- [Analisi Log](./Log-Analysis.it.md)
- [Rilevamento SIEM](./SIEM-Detection.it.md)
- [Volatility (Forensics)](../Volatility/translations/README.it.md)

---

**Precedente:** [← Hardening](./Hardening.it.md)

**Succesivo:** [Analisi Log →](./Log-Analysis.it.md)
