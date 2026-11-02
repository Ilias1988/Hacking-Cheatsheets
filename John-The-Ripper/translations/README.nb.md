# 🔨 John the Ripper – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
       __      __         
      / /___  / /_  ____  
 __  / / __ \/ __ \/ __ \ 
/ /_/ / /_/ / / / / / / / 
\____/\____/_/ /_/_/ /_/  
  Verktøy for passordgjenoppretting
```

<p align="center">
  <img src="https://img.shields.io/badge/John-Password_Cracker-red?style=for-the-badge" alt="John">
  <img src="https://img.shields.io/badge/Hash-Cracking-orange?style=for-the-badge" alt="Hash">
  <img src="https://img.shields.io/badge/Open-Source-blue?style=for-the-badge" alt="Åpen kildekode">
  <img src="https://img.shields.io/badge/Multi-Platform-green?style=for-the-badge" alt="Flere plattformer">
</p>

<p align="center">
  <b>🔑 Et velkjent verktøy for passordrevisjon og gjenoppretting</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Grunnleggende syntaks](#-grunnleggende-syntaks)
- [Moduser for passordknekking](#-moduser-for-passordknekking)
- [Uthenting av hasher](#-uthenting-av-hasher)
- [Vanlige hashtyper](#-vanlige-hashtyper)
- [Knekking av Linux-passord](#-knekking-av-linux-passord)
- [Knekking av Windows-passord](#-knekking-av-windows-passord)
- [Knekking av filpassord](#-knekking-av-filpassord)
- [Regler og endring av ord](#-regler-og-endring-av-ord)
- [Øktbehandling](#-øktbehandling)
- [Ytelsesjustering](#-ytelsesjustering)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

**John the Ripper** (John/JtR) er et gratis verktøy med åpen kildekode for passordrevisjon og gjenoppretting. Det brukes til å avdekke svake passord og knekke passordhasher.

> **Begreper og versjoner:** En hash er en enveisberegnet verdi; verktøyet prøver kandidater, ikke generell dekryptering. En ordliste er en liste over kandidater, og regler endrer dem. Mange formater, masker, OpenCL og 2john-verktøy krever Jumbo og bestemte byggavhengigheter. CPU-/GPU-rangeringene er originalens forenkling; ytelse avhenger av format og maskinvare. Hashcat har også identifikasjonsfunksjoner i nyere versjoner; se [Hashcats dokumentasjon](https://hashcat.net/wiki/doku.php?id=hashcat).

### Viktige egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **Flere formater** | Støtter hundrevis av hashtyper |
| **Flere moduser** | Single, Wordlist, Incremental |
| **Egne regler** | Kraftige regler for endring av ord |
| **Automatisk gjenkjenning** | Automatisk identifikasjon av hashformat |
| **Flere plattformer** | Linux, Windows, macOS |
| **GPU-støtte** | OpenCL-akselerasjon (Jumbo) |

### John sammenlignet med Hashcat

| Egenskap | John | Hashcat |
|---------|------|---------|
| **Best egnet til** | CPU-basert knekking | GPU-basert knekking |
| **Brukervennlighet** | Enklere | Mer sammensatt |
| **Automatisk gjenkjenning** | ✅ Ja | ❌ Nei |
| **Regler** | Kraftige | Svært kraftige |
| **Hashtyper** | Mange | Flere |
| **Hastighet (GPU)** | God | Bedre |

### Utgaver

| Utgave | Beskrivelse |
|---------|-------------|
| **John (Core)** | Grunnutgave |
| **John Jumbo** | Utvidet fellesskapsutgave (anbefalt i originalen) |

---

## 📥 Installasjon

### Kali Linux (forhåndsinstallert)
```bash
john --version
```

### Ubuntu/Debian
```bash
# Standardutgave
sudo apt install john

# Jumbo-utgave (anbefalt i originalen)
sudo apt install john-jumbo
```

### Fra kildekode (Jumbo)
```bash
git clone https://github.com/openwall/john
cd john/src
./configure
make -s clean && make -sj4
cd ../run
./john --version
```

### macOS (Homebrew)
```bash
brew install john-jumbo
```

### Windows
```bash
# Last ned fra det offisielle nettstedet
https://www.openwall.com/john/

# Eller bruk Chocolatey
choco install john
```

### Kontrollere installasjonen

> **Presisering:** `--list=formats` kan vise flere kommaavgrensede formater per linje; `wc -l` er derfor ikke nødvendigvis antall formater. Automatisk formatgjenkjenning kan være tvetydig, og avkortede eksempelhasher med `...` er ikke komplette inndata. Enkelte installasjoner bruker andre navn eller stier for 2john-skriptene. Se [Johns dokumentasjon](https://www.openwall.com/john/doc/).
```bash
john --version
john --list=formats | wc -l    # Tell støttede formater (linjetelling; se presiseringen)
```

---

<a id="-grunnleggende-syntaks"></a>
## ⌨️ Grunnleggende syntaks

### Generell syntaks
```bash
john [options] [password_file]
```

### Vanlige alternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `--wordlist=` | Sti til ordliste |
| `--format=` | Angi hashformat |
| `--rules` | Aktiver regler for endring av ord |
| `--show` | Vis gjenopprettede passord |
| `--single` | Single crack-modus |
| `--incremental` | Inkrementell modus (brute force) |
| `--session=` | Gi økten et navn |
| `--restore` | Gjenoppta en tidligere økt |
| `--pot=` | Egen pot-fil |

### Kom raskt i gang
```bash
# Gjenkjenn formatet automatisk og knekk
john hashes.txt

# Med ordliste
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Vis gjenopprettede passord
john --show hashes.txt
```

---

## 🎮 Moduser for passordknekking

### Single crack-modus

Bruker påloggingsnavn og GECOS-opplysninger til å generere passordforslag.

```bash
# Single-modus (prøver varianter av brukernavnet)
john --single hashes.txt

# Med angitt format
john --single --format=sha512crypt hashes.txt
```

### Ordliste-modus

Bruker ord fra en ordliste, eventuelt med regler.

```bash
# Grunnleggende ordlisteangrep
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Ordliste med regler
john --wordlist=wordlist.txt --rules hashes.txt

# Bestemt regelsett
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
```

### Inkrementell modus (brute force)

> **Presisering:** Inkrementell modus styres av konfigurerte tegnsett og lengder; Single-modus bruker blant annet kontonavn og GECOS-felt og er ikke alltid raskest. `--restore` leser en tidligere lagret økt, mens `--session` gir en ny økt et navn. Se [Openwalls beskrivelse av alternativene](https://www.openwall.com/john/doc/OPTIONS.shtml).

Prøver mulige tegnkombinasjoner innenfor det konfigurerte tegnsettet og lengdeområdet.

```bash
# Standard inkrementell modus (tegnsettet avhenger av oppsettet)
john --incremental hashes.txt

# Bestemt tegnsett
john --incremental=Digits hashes.txt      # Bare sifre
john --incremental=Lower hashes.txt       # Bare små bokstaver
john --incremental=Upper hashes.txt       # Bare store bokstaver
john --incremental=Alpha hashes.txt       # Bare bokstaver
john --incremental=Alnum hashes.txt       # Bokstaver og sifre
john --incremental=LowerNum hashes.txt    # Små bokstaver og sifre
```

### Maske-modus

Brute force basert på et mønster.

```bash
# Maskeangrep (? = plassholdere)
john --mask='?l?l?l?l?d?d' hashes.txt

# Plassholdere:
# ?l = små bokstaver    ?u = store bokstaver
# ?d = sifre            ?s = spesialtegn
# ?a = alle             ?h = heksadesimale små bokstaver
```

### Ekstern modus

Egne algoritmer for passordknekking.

```bash
john --external=Filter_Alpha hashes.txt
```

---

## 🔍 Uthenting av hasher

### Linux-passord

```bash
# Kombiner passwd- og shadow-filene
unshadow /etc/passwd /etc/shadow > unshadowed.txt

# Knekk den kombinerte filen
john unshadowed.txt
```

### Windows-hasher

```bash
# Fra SAM-fil (bruk pwdump, secretsdump osv.)
# Format: user:id:LM_hash:NTLM_hash:::

# Med Metasploits hashdump
meterpreter> hashdump
Administrator:500:aad3b435b51404ee:31d6cfe0d16ae931:::

# Lagre i fil og knekk
john --format=nt hashes.txt
```

### Filpassord (2john-verktøy)

```bash
# ZIP-filer
zip2john protected.zip > zip_hash.txt

# RAR-filer
rar2john protected.rar > rar_hash.txt

# PDF-filer
pdf2john protected.pdf > pdf_hash.txt

# Office-dokumenter
office2john document.docx > office_hash.txt

# SSH-nøkler
ssh2john id_rsa > ssh_hash.txt

# 7zip files
7z2john archive.7z > 7z_hash.txt

# KeePass-databaser
keepass2john database.kdbx > keepass_hash.txt
```

---

## 🔢 Vanlige hashtyper

### Tabell over hashformater

| Hashtype | Formatflagg | Eksempel |
|-----------|-------------|---------|
| MD5 | `--format=raw-md5` | `5d41402abc4b2a76b9719d911017c592` |
| SHA1 | `--format=raw-sha1` | `aaf4c61ddcc5e8a2...` |
| SHA256 | `--format=raw-sha256` | `2cf24dba5fb0a30e...` |
| SHA512 | `--format=raw-sha512` | `cf83e1357eefb8bd...` |
| NTLM | `--format=nt` | `31d6cfe0d16ae931b73c59d7e0c089c0` |
| LM | `--format=lm` | `aad3b435b51404ee` |
| MySQL | `--format=mysql-sha1` | `*2470C0C06DEE42...` |
| bcrypt | `--format=bcrypt` | `$2a$10$N9qo8u...` |
| Linux SHA512 | `--format=sha512crypt` | `$6$rounds=5000$...` |
| Linux SHA256 | `--format=sha256crypt` | `$5$rounds=5000$...` |
| Linux MD5 | `--format=md5crypt` | `$1$salt$hash` |

### Liste over alle formater
```bash
# List alle støttede formater
john --list=formats

# Søk etter et bestemt format
john --list=formats | grep -i ntlm
john --list=formats | grep -i sha

# Tell formater (linjetelling; se presiseringen)
john --list=formats | wc -l
```

### Identifisere hashtypen
```bash
# Bruk hash-identifier
hash-identifier

# Eller hashid
hashid -m '$6$salt$hash...'
```

---

## 🐧 Knekking av Linux-passord

### Klargjøre hasher
```bash
# Kombiner passwd og shadow
sudo unshadow /etc/passwd /etc/shadow > linux_hashes.txt

# Eller hent ut bare hashen
sudo cat /etc/shadow | grep username
```

### Eksempler på hashformater
```
# /etc/shadow format:
# username:$id$salt$hash:...

# $1$ = MD5
# $5$ = SHA-256
# $6$ = SHA-512 (most common modern Linux)
# $y$ = yescrypt (newest)
```

### Knekke Linux-hasher
```bash
# Automatisk gjenkjenning (anbefalt i originalen)
john linux_hashes.txt

# Angi format
john --format=sha512crypt linux_hashes.txt

# Med ordliste
john --wordlist=/usr/share/wordlists/rockyou.txt linux_hashes.txt

# Vis resultater
john --show linux_hashes.txt
```

---

## 🪟 Knekking av Windows-passord

### Hashtyper

| Type | Beskrivelse | Format |
|------|-------------|--------|
| **LM** | Eldre og svak | `--format=lm` |
| **NTLM** | Moderne Windows | `--format=nt` |
| **NTLMv2** | Nettverksautentisering | `--format=netntlmv2` |

### Knekke NTLM-hasher
```bash
# Format for NTLM-hash:
# user:rid:lm_hash:ntlm_hash:::

# Knekk NTLM
john --format=nt windows_hashes.txt

# Med ordliste
john --format=nt --wordlist=rockyou.txt windows_hashes.txt

# Med regler
john --format=nt --wordlist=rockyou.txt --rules windows_hashes.txt
```

### Hente ut Windows-hasher
```bash
# Med Metasploit
meterpreter> hashdump

# Med secretsdump (Impacket)
secretsdump.py -sam sam -system system LOCAL

# Med pwdump
pwdump7.exe
```

---

## 📄 Knekking av filpassord

### ZIP-filer
```bash
# Hent ut hash
zip2john protected.zip > zip.hash

# Knekk
john --wordlist=rockyou.txt zip.hash

# Vis passordet
john --show zip.hash
```

### RAR-filer
```bash
rar2john protected.rar > rar.hash
john --wordlist=rockyou.txt rar.hash
```

### PDF-filer
```bash
pdf2john protected.pdf > pdf.hash
john --wordlist=rockyou.txt pdf.hash
```

### Office-dokumenter
```bash
# Word, Excel, PowerPoint
office2john document.docx > office.hash
john --wordlist=rockyou.txt office.hash
```

### Private SSH-nøkler
```bash
ssh2john id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### KeePass-database
```bash
keepass2john database.kdbx > keepass.hash
john --wordlist=rockyou.txt keepass.hash
```

---

<a id="-regler-og-endring-av-ord"></a>
## ⚙️ Regler og endring av ord

### Aktivere regler
```bash
# Standardregler
john --wordlist=wordlist.txt --rules hashes.txt

# Bestemt regelsett
john --wordlist=wordlist.txt --rules=Single hashes.txt
john --wordlist=wordlist.txt --rules=Wordlist hashes.txt
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
john --wordlist=wordlist.txt --rules=KoreLogic hashes.txt
```

### Liste over tilgjengelige regler
```bash
john --list=rules
```

### Vanlige regeloperasjoner

| Regel | Beskrivelse | Eksempel |
|------|-------------|---------|
| `:` | Ingen endring | password → password |
| `l` | Gjør alle bokstaver små | PassWord → password |
| `u` | Gjør alle bokstaver store | password → PASSWORD |
| `c` | Gjør første bokstav stor | password → Password |
| `r` | Snu rekkefølgen | password → drowssap |
| `d` | Dupliser | pass → passpass |
| `$X` | Legg tegnet X til slutt | pass → passX |
| `^X` | Legg tegnet X først | pass → Xpass |
| `sXY` | Erstatt X med Y | password → pa$$word |

### Egne regler

> **Rettelse:** `$X` og `^X` behandler ett tegn om gangen. Eksempellinjene `$123` og `^2023` betyr derfor ikke generelt «legg til hele tallet»; de inneholder videre regeltegn. Tabellen med `sXY` viser en abstrakt erstatning, mens eksempelresultatet forutsetter bestemte tegn. Syntaksen er bevart; se [Openwalls regeldokumentasjon](https://www.openwall.com/john/doc/RULES.shtml).
```bash
# Legg til i john.conf [List.Rules:Custom]
[List.Rules:Custom]
:
l
u
c
$1
$!
$123
^2023
```

---

## 💾 Øktbehandling

### Opprette en navngitt økt
```bash
john --wordlist=rockyou.txt --session=mycrack hashes.txt
```

### Gjenoppta en økt
```bash
# Gjenoppta en avbrutt økt
john --restore=mycrack

# Gjenoppta standardøkten
john --restore
```

### Kontrollere status
```bash
# Trykk på en tast under knekking for å se status
# Eller kontroller .rec-filen
cat ~/.john/mycrack.rec
```

### Pot-fil
```bash
# Standard pot-fil
~/.john/john.pot

# Egen pot-fil
john --pot=custom.pot hashes.txt

# Vis alle gjenopprettede passord
john --show --pot=custom.pot hashes.txt
```

---

## 🚀 Ytelsesjustering

### Fork (flere prosesser)
```bash
# Bruk flere CPU-kjerner
john --fork=4 --wordlist=rockyou.txt hashes.txt
```

### OpenCL (GPU)
```bash
# List OpenCL-enheter
john --list=opencl-devices

# Bruk GPU
john --format=sha512crypt-opencl --wordlist=rockyou.txt hashes.txt
```

### Begrense kandidater
```bash
# Begrens passordlengden
john --min-length=6 --max-length=12 --incremental hashes.txt
```

### Minnebruk
```bash
# Reduser minnebruken
john --mem-file-size=32 --wordlist=huge.txt hashes.txt
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: Linux-shadowfil
```bash
# Kombiner filene
sudo unshadow /etc/passwd /etc/shadow > hashes.txt

# Knekk med rockyou
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Vis resultater
john --show hashes.txt
```

### Eksempel 2: Windows NTLM
```bash
# Fra hashdump-utdata
echo "admin:500:aad3b435b51404ee:31d6cfe0d16ae931:::" > ntlm.txt

# Knekk
john --format=nt --wordlist=rockyou.txt ntlm.txt
```

### Eksempel 3: Passord til ZIP-fil
```bash
zip2john secret.zip > zip.hash
john --wordlist=rockyou.txt zip.hash
john --show zip.hash
```

### Eksempel 4: Passfrase til SSH-nøkkel
```bash
ssh2john ~/.ssh/id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Eksempel 5: MD5-hasher
```bash
echo "5d41402abc4b2a76b9719d911017c592" > md5.txt
john --format=raw-md5 --wordlist=rockyou.txt md5.txt
```

### Eksempel 6: Hasher fra webapplikasjon
```bash
# bcrypt-hash
echo '$2a$10$N9qo8uLOickgx2ZMRZoMy...' > bcrypt.txt
john --format=bcrypt --wordlist=rockyou.txt bcrypt.txt
```

### Eksempel 7: Med regler
```bash
john --wordlist=small_wordlist.txt --rules=Jumbo hashes.txt
```

### Eksempel 8: Inkrementelt angrep
```bash
# Prøv alle kombinasjoner av små bokstaver og sifre med lengde 6-8
john --incremental=LowerNum --min-length=6 --max-length=8 hashes.txt
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Oppgave | Kommando |
|------|---------|
| Grunnleggende knekking | `john hashes.txt` |
| Ordlisteangrep | `john --wordlist=rockyou.txt hashes.txt` |
| Med regler | `john --wordlist=words.txt --rules hashes.txt` |
| Vis gjenopprettede passord | `john --show hashes.txt` |
| Bestemt format | `john --format=raw-md5 hashes.txt` |
| Inkrementelt | `john --incremental hashes.txt` |
| Gjenoppta økt | `john --restore=session` |
| List formater | `john --list=formats` |

### 2john-verktøy

| Verktøy | Formål |
|------|---------|
| `unshadow` | Linux passwd/shadow |
| `zip2john` | ZIP-filer |
| `rar2john` | RAR-filer |
| `pdf2john` | PDF-filer |
| `office2john` | Office-dokumenter |
| `ssh2john` | SSH-nøkler |
| `keepass2john` | KeePass-databaser |

### Vanlige formater

| Hash | Formatflagg |
|------|-------------|
| MD5 | `raw-md5` |
| SHA1 | `raw-sha1` |
| SHA256 | `raw-sha256` |
| NTLM | `nt` |
| Linux SHA512 | `sha512crypt` |
| bcrypt | `bcrypt` |
| MySQL | `mysql-sha1` |

---

## 💡 Tips og anbefalte arbeidsmåter

### Effektivitetstips

1. **Prøv Single-modus først**
   ```bash
   john --single hashes.txt
   ```

2. **Bruk målrettede ordlister**
   ```bash
   # Virksomhetsnavn, by osv.
   john --wordlist=targeted.txt hashes.txt
   ```

3. **Begynn med små ordlister**
   ```bash
   john --wordlist=top1000.txt hashes.txt
   # Prøv deretter større
   ```

4. **Kombiner moduser**
   ```bash
   john --single hashes.txt
   john --wordlist=rockyou.txt --rules hashes.txt
   john --incremental hashes.txt
   ```

### Ytelsestips

```bash
# Bruk flere kjerner
john --fork=4 hashes.txt

# GPU-akselerasjon (Jumbo)
john --format=sha512crypt-opencl hashes.txt
```

---

## ⚠️ Juridisk ansvarsfraskrivelse

> **ADVARSEL:** John the Ripper er et kraftig verktøy for passordknekking som bare skal brukes til **autoriserte formål**.
> 
> - ✅ Kontroller dine egne passord
> - ✅ Test med uttrykkelig tillatelse
> - ✅ Sikkerhetsvurderinger med tillatelse
> - ❌ Ikke knekk passord uten tillatelse
> - ❌ Uautorisert tilgang er ulovlig
> 
> **Passordknekking uten tillatelse er ulovlig og kan føre til straffansvar.**

---

## 📚 Ressurser

### Offisielle ressurser
- [John the Ripper – offisielt nettsted](https://www.openwall.com/john/)
- [John Jumbo GitHub](https://github.com/openwall/john)
- [John-wiki](https://openwall.info/wiki/john)

### Ordlister
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Relaterte hurtigreferanser
- [Hydra](../../Hydra/translations/README.nb.md)
- [Metasploit](../../Metasploit/translations/README.nb.md)
- [Nmap](../../Nmap/translations/README.nb.md)

---

<p align="center">
  <b>🔨 Lær passordknekking!</b><br>
  <i>Knekk alltid ansvarlig</i>
</p>
