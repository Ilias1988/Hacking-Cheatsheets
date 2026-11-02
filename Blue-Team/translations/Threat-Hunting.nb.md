# 🎯 Trusseljakt – hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Threat-Hunting.md) • [🇹🇷 Türkçe Sürüm](Threat-Hunting.tr.md) • [🇬🇷 Ελληνικά](Threat-Hunting.el.md)

```
████████╗██╗  ██╗██████╗ ███████╗ █████╗ ████████╗    ██╗  ██╗██╗   ██╗███╗   ██╗████████╗██╗███╗   ██╗ ██████╗ 
╚══██╔══╝██║  ██║██╔══██╗██╔════╝██╔══██╗╚══██╔══╝    ██║  ██║██║   ██║████╗  ██║╚══██╔══╝██║████╗  ██║██╔════╝ 
   ██║   ███████║██████╔╝█████╗  ███████║   ██║       ███████║██║   ██║██╔██╗ ██║   ██║   ██║██╔██╗ ██║██║  ███╗
   ██║   ██╔══██║██╔══██╗██╔══╝  ██╔══██║   ██║       ██╔══██║██║   ██║██║╚██╗██║   ██║   ██║██║╚██╗██║██║   ██║
   ██║   ██║  ██║██║  ██║███████╗██║  ██║   ██║       ██║  ██║╚██████╔╝██║ ╚████║   ██║   ██║██║ ╚████║╚██████╔╝
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝       ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Prosessen for trusseljakt (threat hunting)

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│ 1. HYPOTESE  │────▶│ 2. SAMLE INN│────▶│ 3. ANALYSER │────▶│ 4. HÅNDTER  │
│   (Teori)    │     │   (Data)    │     │   (Jakt)    │     │   (Tiltak)  │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
                                                                    │
                          ┌─────────────┐                          │
                          │ 5. DOKUM.   │◀─────────────────────────┘
                          │   (Lær)     │
                          └─────────────┘
```

Trinnene er hypotese (teori), innsamling (data), analyse (jakt), respons (tiltak) og dokumentasjon (læring).

---

## 📋 Eksempler på hypoteser

| Kategori | Hypotese |
|----------|------------|
| **Innledende tilgang** | Angripere kan bruke phishing til å levere skadevare |
| **Vedvarende tilgang** | Angripere kan opprette planlagte oppgaver for å opprettholde tilgang |
| **Påloggingsinformasjon** | Angripere kan dumpe LSASS for å hente ut påloggingsinformasjon |
| **Lateral bevegelse** | Angripere kan bruke RDP til å bevege seg mellom systemer |
| **Eksfiltrering** | Angripere kan bruke DNS-tunneler til å hente ut data |

---

## 🔍 Jaktteknikker

### Jakt på vedvarende tilgang

#### Run-nøkler i Windows-registeret
```powershell
# Spør mot alle Run-nøkler
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce" -ErrorAction SilentlyContinue
```

#### Planlagte oppgaver
```powershell
# Vis alle planlagte oppgaver
Get-ScheduledTask | Where-Object {$_.State -ne "Disabled"} | 
    Select-Object TaskName, TaskPath, State | Format-Table

# Se etter mistenkelige oppgaver (andre enn Microsoft)
Get-ScheduledTask | Where-Object {$_.Author -notlike "*Microsoft*"} | 
    Select-Object TaskName, Author, State
```

#### Tjenester

> **Presisering:** Filteret undersøker om tjenestens sti inneholder `Windows`; det kontrollerer ikke den digitale signaturen. Det kan derfor ikke alene identifisere usignerte tjenester.
```powershell
# Usignerte tjenester (originalens beskrivelse; filteret undersøker bare stien)
Get-WmiObject Win32_Service | Where-Object {$_.PathName -notlike "*Windows*"} | 
    Select-Object Name, PathName, StartMode, State
```

### Jakt på lateral bevegelse

#### RDP-tilkoblinger
```powershell
# RDP-økter (hendelse 4624 type 10)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} | 
    Where-Object {$_.Properties[8].Value -eq 10} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='Source';E={$_.Properties[18].Value}}
```

#### Nettverkstilkoblinger
```powershell
# Nåværende tilkoblinger
Get-NetTCPConnection -State Established | 
    Select-Object LocalAddress, LocalPort, RemoteAddress, RemotePort, OwningProcess | 
    Format-Table

# Med prosessnavn
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

### Jakt på tilgang til påloggingsinformasjon

#### Tilgang til LSASS
```powershell
# Sysmon-hendelse 10 – tilgang til LSASS
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=10} |
    Where-Object {$_.Properties[8].Value -like "*lsass.exe"} |
    Select-Object TimeCreated, @{N='Source';E={$_.Properties[4].Value}}
```

### Jakt på kjøring

#### Kodet PowerShell
```powershell
# PowerShell med koding
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-PowerShell/Operational';ID=4104} |
    Where-Object {$_.Message -match "FromBase64" -or $_.Message -match "-enc"} |
    Select-Object TimeCreated, Message
```

#### Mistenkelige foreldre- og underprosesser
```powershell
# Office starter cmd/powershell
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Where-Object {
        ($_.Properties[20].Value -match "WINWORD|EXCEL|OUTLOOK") -and
        ($_.Properties[4].Value -match "cmd|powershell")
    }
```

---

## 🐧 Trusseljakt i Linux

### Vedvarende tilgang
```bash
# Cron-jobber
cat /etc/crontab
for user in $(cut -f1 -d: /etc/passwd); do crontab -l -u $user 2>/dev/null; done

# Autoriserte SSH-nøkler
find / -name "authorized_keys" 2>/dev/null

# Oppstartsskript
ls -la /etc/init.d/
systemctl list-unit-files --type=service | grep enabled

# SUID-programfiler
find / -perm -4000 -type f 2>/dev/null
```

### Nettverk
```bash
# Lyttende porter
ss -tulnp
netstat -tulnp

# Etablerte tilkoblinger
ss -tnp state established
lsof -i -P -n | grep ESTABLISHED

# Uvanlig DNS-trafikk
tcpdump -i any port 53 -w dns.pcap
```

### Prosesser

> **Presisering:** Eksempelet merket «skjulte prosesser» undersøker `/proc` for prosesser som allerede er synlige i `ps`. Det gir ikke en uavhengig deteksjon av skjulte prosesser.
```bash
# Alle prosesser med full kommandolinje
ps auxwww

# Prosesstre
pstree -p

# Skjulte prosesser (originalens beskrivelse; listen bygger på ps-utdata)
ps aux | awk '{print $2}' | xargs -I{} ls -la /proc/{}/exe 2>/dev/null

# Nylig endrede programfiler
find /usr/bin /usr/sbin -mtime -7 2>/dev/null
```

---

## 📊 Jakt på kompromitteringsindikatorer (IOC-er)

### Jakt etter hasher
```powershell
# Beregn hasher
Get-FileHash -Algorithm SHA256 C:\suspicious.exe

# Søk etter en kjent ondsinnet hash
$badHash = "abc123..."
Get-ChildItem -Path C:\ -Recurse -File | Get-FileHash -Algorithm SHA256 |
    Where-Object {$_.Hash -eq $badHash}
```

### Jakt etter IP-adresser og domener
```powershell
# DNS-hurtigbuffer
Get-DnsClientCache | Where-Object {$_.Name -like "*suspicious*"}

# Tilkobling til kjent ondsinnet IP-adresse
Get-NetTCPConnection | Where-Object {$_.RemoteAddress -eq "1.2.3.4"}
```

---

## 🛠️ Verktøy

| Verktøy | Formål |
|------|---------|
| **Velociraptor** | Trusseljakt på mange endepunkter |
| **OSQuery** | SQL-baserte spørringer mot endepunkter |
| **KAPE** | Innsamling ved triage |
| **Chainsaw** | Rask analyse av Windows-logger |
| **DeepBlueCLI** | Trusseljakt med PowerShell |

---

## 📋 Kort sjekkliste for trusseljakt

```markdown
□ Kontroller plasseringer for automatisk kjøring (register, tjenester, oppgaver)
□ Gjennomgå nylig opprettede prosesser (hendelse 4688, Sysmon 1)
□ Analyser nettverkstilkoblinger
□ Se etter kodede kommandoer
□ Søk etter tilgang til LSASS
□ Se etter uvanlige forhold mellom foreldre- og underprosesser
□ Gjennomgå PowerShell-logger
□ Se etter indikatorer på lateral bevegelse
```

---

## 🔗 Relaterte hurtigreferanser

- [Logganalyse](Log-Analysis.nb.md)
- [SIEM-deteksjon](SIEM-Detection.nb.md)
- [Sigma-regler](Sigma-Rules.nb.md)

---

**Forrige:** [← SIEM-deteksjon](SIEM-Detection.nb.md)

**Neste:** [Sigma-regler →](Sigma-Rules.nb.md)
