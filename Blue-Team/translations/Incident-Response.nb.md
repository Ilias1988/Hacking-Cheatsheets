# 🚨 Hendelseshåndtering – hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Incident-Response.md) • [🇹🇷 Türkçe Sürüm](Incident-Response.tr.md) • [🇬🇷 Ελληνικά](Incident-Response.el.md)

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

## 📊 Livssyklus for hendelseshåndtering (NIST)

> **Versjonsmerknad:** Originalen bruker en tradisjonell trinnmodell. NIST SP 800-61 Rev. 3 fra april 2025 erstatter Rev. 2 og knytter hendelseshåndtering til CSF 2.0. [NIST-publikasjonen](https://csrc.nist.gov/pubs/sp/800/61/r3/final).

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│ 1.FORBERED   │────▶│ 2.OPPDAG     │────▶│3.BEGRENS     │────▶│4.FJERN       │
│              │     │ OG ANALYSER  │     │              │     │              │
└──────────────┘     └──────────────┘     └──────────────┘     └──────────────┘
       ▲                                                              │
       │         ┌──────────────┐     ┌──────────────┐               │
       └─────────│ 6.LÆR AV     │◀────│ 5.GJENOPPR.  │◀──────────────┘
                 │   HENDELSEN  │     │              │
                 └──────────────┘     └──────────────┘
```

Diagrammet viser forberedelse, oppdagelse og analyse, begrensning, fjerning, gjenoppretting og læring etter hendelsen.

---

## 1️⃣ Forberedelse

### Sjekkliste for dokumentasjon
```markdown
- [ ] Dokumentert plan for hendelseshåndtering
- [ ] Kontaktliste (interne og eksterne kontakter)
- [ ] Kommunikasjonsplan
- [ ] Oversikt over eiendeler
- [ ] Nettverksdiagrammer
- [ ] Referansekonfigurasjoner
- [ ] Verifiserte sikkerhetskopier
```

### Mal for kontaktliste til hendelseshåndteringsteamet
```
IR-leder:           [Navn] - [Telefon] - [E-post]
IT-sikkerhet:       [Navn] - [Telefon] - [E-post]
Juridisk:           [Navn] - [Telefon] - [E-post]
Kommunikasjon:      [Navn] - [Telefon] - [E-post]
HR:                [Navn] - [Telefon] - [E-post]
Ekstern etterforskning: [Firma] - [Telefon]
Politi:            [Etaten] - [Telefon]
```

### Viktige IR-verktøy
```bash
# Innsamling for digital etterforskning
- FTK Imager
- Volatility
- Velociraptor
- KAPE

# Nettverksanalyse
- Wireshark
- tcpdump
- Zeek

# Logganalyse
- Splunk/ELK
- grep/awk/sed
```

---

## 2️⃣ Oppdagelse og analyse

### Spørsmål ved første vurdering (triage)
```markdown
1. Hva slags hendelse er dette?
   - [ ] Skadevareinfeksjon
   - [ ] Uautorisert tilgang
   - [ ] Datainnbrudd
   - [ ] Tjenestenekt
   - [ ] Innsidetrussel
   - [ ] Phishing

2. Når ble den oppdaget?
3. Hvordan ble den oppdaget?
4. Hvilke systemer er berørt?
5. Hvilke data kan være berørt?
6. Pågår hendelsen fortsatt?
```

### Rask triage i Windows
```cmd
# Aktive brukerøkter
query user
qwinsta

# Aktive tilkoblinger
netstat -ano | findstr ESTABLISHED

# Prosesser som kjører
tasklist /v
wmic process get processid,parentprocessid,commandline

# Nylige filendringer
forfiles /P C:\ /S /D +01/01/2024

# Planlagte oppgaver
schtasks /query /fo LIST /v

# Tjenester
sc query state=all
wmic service get name,startmode,state

# Run-nøkler i registeret
reg query "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"
reg query "HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"

# Nylig PowerShell-historikk
type %APPDATA%\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt
```

### Rask triage i Linux
```bash
# Hvem er pålogget
w
who
last -a

# Aktive tilkoblinger
ss -tulnp
netstat -tulnp
lsof -i

# Prosesser som kjører
ps auxwww
pstree -p

# Nylige filendringer
find / -mtime -1 -type f 2>/dev/null

# Cron-jobber
cat /etc/crontab
ls -la /etc/cron.*
for user in $(cut -f1 -d: /etc/passwd); do crontab -u $user -l 2>/dev/null; done

# Oppstartsskript
ls -la /etc/init.d/
systemctl list-unit-files --type=service

# Historikk
cat ~/.bash_history
cat /root/.bash_history
```

### Nettverksanalyse
```bash
# Ta opp trafikk
tcpdump -i eth0 -w capture.pcap

# Filtrer på en bestemt vert
tcpdump -i eth0 host 192.168.1.100

# HTTP-trafikk
tcpdump -i eth0 port 80 or port 443

# DNS-spørringer
tcpdump -i eth0 port 53
```

---

## 3️⃣ Begrensning

### Kortsiktig begrensning

> **Presisering:** `passwd -l` låser passordet; det garanterer ikke at andre autentiseringsmetoder, som SSH-nøkler, er blokkert. Vurder alle tilgangsveier ved kontoisolering. [Dokumentasjon for passwd](https://www.man7.org/linux/man-pages/man1/passwd.1.html).
```bash
# Isoler nettverket (Windows)
netsh advfirewall set allprofiles state on
netsh advfirewall firewall add rule name="Block All" dir=in action=block
netsh advfirewall firewall add rule name="Block All Out" dir=out action=block

# Isoler nettverket (Linux)
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

# Deaktiver kompromittert konto
net user compromised_user /active:no   # Windows
passwd -l compromised_user             # Linux

# Avslutt ondsinnet prosess
taskkill /PID <pid> /F               # Windows
kill -9 <pid>                        # Linux
```

### Langsiktig begrensning

> **Presisering:** `systemctl disable` hindrer normal automatisk oppstart, men stopper ikke en tjeneste som allerede kjører. Kildeeksempelet er beholdt. [Dokumentasjon for systemctl](https://www.freedesktop.org/software/systemd/man/latest/systemctl.html).
```bash
# Segmenter nettverket
# Bruk brannmurregler til å isolere berørt VLAN
# Oppdater tilgangslister (ACL-er) på svitsjer og rutere

# Blokker ondsinnede IP-adresser og domener i brannmuren
# Legg til i proxyens blokkeringsliste
# Oppdater DNS-sinkhole

# Deaktiver berørte tjenester midlertidig
sc config <service> start= disabled   # Windows
systemctl disable <service>           # Linux
```

### Sikring av bevis

> **Presisering:** Lesing fra `/dev/mem` er begrenset på mange Linux-systemer og gir ikke nødvendigvis en full minneavbildning. Innsamlingsmetoden må passe til kjernen og systemets konfigurasjon. Eksempelet er beholdt uendret. [Linux-dokumentasjonen for mem](https://www.man7.org/linux/man-pages/man4/mem.4.html).
```bash
# Minnedump (Windows – bruk WinPMEM eller DumpIt)
winpmem_mini_x64.exe memory.raw

# Minnedump (Linux)
dd if=/dev/mem of=/evidence/memory.dump bs=1M

# Diskavbildning
dd if=/dev/sda of=/evidence/disk.img bs=4M status=progress

# Hash for integritetskontroll
sha256sum /evidence/disk.img > disk.img.sha256

# Samle flyktige data først (etter hvor raskt de kan gå tapt):
# 1. Minne
# 2. Nettverkstilstand
# 3. Prosesser som kjører
# 4. Disk
```

---

## 4️⃣ Fjerning

### Fjerning av skadevare
```bash
# Identifiser og fjern mekanismer som opprettholder tilgang

# Windows – register
reg delete "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v "MalwareName" /f

# Windows – planlagte oppgaver
schtasks /delete /tn "MalwareTask" /f

# Windows – tjenester
sc delete "MalwareService"

# Linux – Cron
crontab -r
rm /etc/cron.d/malware

# Linux – Systemd
systemctl disable malware
rm /etc/systemd/system/malware.service
```

### Tilbakestilling av passord
```bash
# Krev tilbakestilling av passord for alle kompromitterte kontoer
# Tilbakestill passord for tjenestekontoer
# Roter API-nøkler og tokens
# Ugyldiggjør økter

# Windows – krev passordendring
net user username /logonpasswordchg:yes

# Tilbakestill Kerberos-billetter (ved kompromittering av AD)
# - Tilbakestill krbtgt-passordet TO GANGER
# - Tilbakestill passord for alle administratorkontoer
```

### Sikkerhetsrettinger og oppdateringer
```bash
# Installer manglende sikkerhetsrettinger
# Oppdater antivirus-signaturer
# Rett feilkonfigurasjoner som gjorde angrepet mulig
```

---

## 5️⃣ Gjenoppretting

### Gjenoppretting av systemer
```bash
# Gjenopprett fra en ren sikkerhetskopi
# Bygg kompromitterte systemer på nytt
# Kontroller integriteten før systemene kobles til igjen

# Gradvis gjenoppretting:
1. Gjenopprett kritiske systemer først
2. Overvåk tegn på ny infeksjon
3. Gjenopprett flere systemer
4. Gå tilbake til normal drift
```

### Overvåking etter gjenoppretting
```bash
# Øk detaljnivået i loggingen
# Innfør ytterligere overvåking
# Se etter tegn på at angriperen kommer tilbake

# Viktige indikatorer å overvåke:
- De samme IOC-ene (IP-adresser, domener, hasher)
- Lignende taktikker, teknikker og prosedyrer (TTP-er)
- Forsøk på tilgang til de samme kontoene
- Kommunikasjon med C2-infrastruktur
```

---

## 6️⃣ Læring etter hendelsen

### Mal for hendelsesrapport
```markdown
## Hendelsessammendrag
- Hendelses-ID:
- Dato oppdaget:
- Dato begrenset:
- Dato løst:
- Hendelsestype:
- Alvorlighetsgrad:

## Tidslinje
| Dato/tid | Hendelse |
|-----------|-------|
| YYYY-MM-DD HH:MM | Første oppdagelse |
| ... | ... |

## Konsekvensvurdering
- Berørte systemer:
- Kompromitterte data:
- Konsekvenser for virksomheten:
- Økonomiske konsekvenser:

## Rotårsaksanalyse
- Hvordan fikk angriperen tilgang?
- Hvilke sårbarheter ble utnyttet?
- Hvilke sikkerhetstiltak sviktet?

## Gjennomførte tiltak
1. Oppdagelse
2. Begrensning
3. Fjerning
4. Gjenoppretting

## Anbefalinger
1. Umiddelbare forbedringer
2. Langsiktige sikkerhetsforbedringer
3. Endringer i retningslinjer

## Læring etter hendelsen
- Hva fungerte godt?
- Hva kan forbedres?
- Hvilke opplæringsbehov ble avdekket?
```

---

## 📊 Hurtigreferanse

### Klassifisering av alvorlighetsgrad

| Nivå | Beskrivelse | Responstid |
|-------|-------------|---------------|
| **Kritisk** | Pågående innbrudd eller dataeksfiltrering | Umiddelbart |
| **Høy** | Bekreftet kompromittering | < 1 time |
| **Middels** | Mulig kompromittering | < 4 timer |
| **Lav** | Mistenkelig aktivitet | < 24 timer |

### Vanlige kompromitteringsindikatorer (IOC-er) som bør samles inn

| IOC-type | Eksempler |
|----------|----------|
| **Hash** | MD5, SHA1, SHA256 |
| **IP** | Kilde- og mål-IP-adresser |
| **Domene** | C2-domener, brukt til kommando og kontroll |
| **URL** | Ondsinnede URL-er |
| **E-post** | Avsender av phishing |
| **Fil** | Filnavn for skadevare |
| **Register** | Nøkler som opprettholder tilgang (persistence) |

---

## 🔗 Relaterte hurtigreferanser

- [Logganalyse](Log-Analysis.nb.md)
- [SIEM-deteksjon](SIEM-Detection.nb.md)
- [Volatility (digital etterforskning)](../../Volatility/README.md)

---

**Neste:** [Logganalyse →](Log-Analysis.nb.md)
