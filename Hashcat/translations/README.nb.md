# ⚡ Hashcat – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _                _   
 | | | | __ _ ___| |__   ___ __ _| |_ 
 | |_| |/ _` / __| '_ \ / __/ _` | __|
 |  _  | (_| \__ \ | | | (_| (_| | |_ 
 |_| |_|\__,_|___/_| |_|\___\__,_|\__|
                                       
    Rask passordgjenoppretting
```

<p align="center">
  <img src="https://img.shields.io/badge/Hashcat-GPU_Cracker-red?style=for-the-badge" alt="Hashcat">
  <img src="https://img.shields.io/badge/350+-Hash_Types-orange?style=for-the-badge" alt="Hashtyper">
  <img src="https://img.shields.io/badge/GPU-Accelerated-blue?style=for-the-badge" alt="GPU">
  <img src="https://img.shields.io/badge/World's-Fastest-green?style=for-the-badge" alt="Raskest">
</p>

<p align="center">
  <b>🚀 Et raskt og avansert verktøy for passordgjenoppretting</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Grunnleggende syntaks](#-grunnleggende-syntaks)
- [Angrepsmoduser](#-angrepsmoduser)
- [Hashmoduser](#-hashmoduser)
- [Maskeangrep](#-maskeangrep)
- [Regler](#-regler)
- [Øktbehandling](#-øktbehandling)
- [GPU-optimalisering](#-gpu-optimalisering)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

**Hashcat** beskrives i originalen som verdens raskeste og mest avanserte verktøy for passordgjenoppretting, med støtte for over 350 hashtyper og GPU-akselerasjon.

> **Begreper og versjoner:** En maske beskriver kandidatens tegnposisjoner, og regler endrer ordlisteord. Tallene, hastighetsstjernene og CPU-/GPU-sammenligningene er historiske forenklinger. Hashcat støtter også identifikasjon med `--identify`; antall formater, moduser, plattformstøtte og standardverdier endres. Flere GPU-er er ikke alene det samme som automatisk fordeling mellom maskiner. Se [Hashcats dokumentasjon](https://hashcat.net/wiki/doku.php?id=hashcat).

### Viktige egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **350+ hashtyper** | MD5, SHA, NTLM, bcrypt, WPA og flere |
| **GPU-akselerasjon** | NVIDIA CUDA, AMD OpenCL, Intel |
| **Flere GPU-er** | Støtte for fordelt knekking |
| **5 angrepsmoduser** | Ordliste, brute force, hybrid osv. |
| **Kraftige regler** | Avanserte endringer av ord |
| **Flere plattformer** | Linux, Windows, macOS |

### Hashcat sammenlignet med John the Ripper

| Egenskap | Hashcat | John |
|---------|---------|------|
| **Hastighet** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| **GPU-støtte** | Svært god | God |
| **Hashtyper** | 350+ | 300+ |
| **Automatisk gjenkjenning** | ❌ Nei | ✅ Ja |
| **Brukervennlighet** | Moderat | Enklere |
| **Best egnet til** | GPU-basert knekking | CPU-basert knekking |

---

## 📥 Installasjon

### Kali Linux
```bash
sudo apt update && sudo apt install hashcat

# Med OpenCL
sudo apt install hashcat-nvidia  # NVIDIA
sudo apt install hashcat-amd     # AMD
```

### Ubuntu/Debian
```bash
sudo apt install hashcat
```

### Windows
```bash
# Last ned fra det offisielle nettstedet
https://hashcat.net/hashcat/

# Pakk ut og kjør fra katalogen
hashcat.exe -h
```

### Fra kildekode
```bash
git clone https://github.com/hashcat/hashcat.git
cd hashcat
make
./hashcat -h
```

### Kontrollere installasjonen
```bash
hashcat --version
hashcat -I                    # Vis OpenCL-enheter
hashcat -b                    # Kjør ytelsestest
```

---

<a id="-grunnleggende-syntaks"></a>
## ⌨️ Grunnleggende syntaks

### Generell syntaks
```bash
hashcat [options] hash|hashfile [dictionary|mask]
```

### Viktige alternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `-m` | Hashtype (modus) |
| `-a` | Angrepsmodus |
| `-o` | Utdatafil |
| `-r` | Regelfil |
| `--show` | Vis gjenopprettede passord |
| `--status` | Aktiver statusvisning |
| `-w` | Belastningsprofil (1-4) |
| `--force` | Ignorer advarsler |
| `-O` | Optimaliserte kjerner |

### Kom raskt i gang
```bash
# Grunnleggende ordlisteangrep
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Vis knekte hasher
hashcat -m 0 hash.txt --show

# Kontroller eksempelhasher
hashcat -m 0 --example-hashes
```

---

## 🎮 Angrepsmoduser

### Oversikt

| Modus | Navn | Beskrivelse |
|------|------|-------------|
| `-a 0` | Ordliste | Ordlisteangrep |
| `-a 1` | Kombinasjon | Kombiner to ordlister |
| `-a 3` | Brute force | Maskebasert angrep |
| `-a 6` | Hybrid ordliste+maske | Ordliste + maske |
| `-a 7` | Hybrid maske+ordliste | Maske + ordliste |
| `-a 9` | Assosiasjon | Ordassosiasjoner |

### Ordlisteangrep (-a 0)

```bash
# Grunnleggende ordlisteangrep
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Med regler
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rules/best64.rule

# Flere ordlister
hashcat -m 0 -a 0 hash.txt wordlist1.txt wordlist2.txt
```

### Kombinasjonsangrep (-a 1)

Kombinerer ord fra to ordlister.

```bash
# Kombiner to ordlister
hashcat -m 0 -a 1 hash.txt wordlist1.txt wordlist2.txt

# Kombinasjoner på formen word1word2
```

### Brute force og maskeangrep (-a 3)

> **Rettelse:** En maske med fire `?d`-posisjoner blir ikke åtte tegn lang selv om `--increment-max=8` står på linjen. Maskens egen lengde er også en grense. Den viste kommandoen er bevart. Tegnsettraden for `?s` er forenklet i originalen; kontroller hele definisjonen for valgt versjon. Se [maskedokumentasjonen](https://hashcat.net/wiki/doku.php?id=mask_attack).

```bash
# Bare små bokstaver, 6 tegn
hashcat -m 0 -a 3 hash.txt ?l?l?l?l?l?l

# Bare sifre, 4-8 tegn (originalens beskrivelse; se presiseringen)
hashcat -m 0 -a 3 hash.txt ?d?d?d?d --increment --increment-min=4 --increment-max=8

# Eget tegnsett
hashcat -m 0 -a 3 hash.txt -1 ?l?d ?1?1?1?1?1?1
```

### Hybridangrep (-a 6, -a 7)

```bash
# Ordliste + maske (legg til slutt)
hashcat -m 0 -a 6 hash.txt wordlist.txt ?d?d?d?d

# Maske + ordliste (legg først)
hashcat -m 0 -a 7 hash.txt ?d?d?d?d wordlist.txt
```

---

## 🔢 Hashmoduser

### De vanligste hashmodusene

| Modus | Hashtype | Eksempel |
|------|-----------|---------|
| 0 | MD5 | `5d41402abc4b2a76b9719d911017c592` |
| 100 | SHA1 | `aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d` |
| 1400 | SHA256 | `2cf24dba5fb0a30e26e83b2ac5b9e29e...` |
| 1700 | SHA512 | `cf83e1357eefb8bdf1542850d66d8007d620e405...` |
| 1000 | NTLM | `32ed87bdb5fdc5e9cba88547376818d4` |
| 3000 | LM | `aad3b435b51404ee` |
| 5600 | NetNTLMv2 | Sammensatt format |
| 3200 | bcrypt | `$2a$10$...` |
| 1800 | sha512crypt (Linux) | `$6$...` |
| 500 | md5crypt (Linux) | `$1$...` |

### Webapplikasjoner

| Modus | Hashtype |
|------|-----------|
| 400 | WordPress (phpass) |
| 2500 | WPA/WPA2 |
| 22000 | WPA-PBKDF2-PMKID+EAPOL |
| 121 | SMF |
| 2611 | vBulletin |
| 3711 | MediaWiki B |
| 7900 | Drupal7 |

### Databaser

| Modus | Hashtype |
|------|-----------|
| 300 | MySQL4.1/MySQL5 |
| 200 | MySQL323 |
| 1731 | MSSQL (2012, 2014) |
| 112 | Oracle S |
| 12300 | Oracle T |

### Dokumenter og arkiver

| Modus | Hashtype |
|------|-----------|
| 9600 | Office 2013 |
| 9500 | Office 2010 |
| 9400 | Office 2007 |
| 9700 | MS Office ⇐ 2003 |
| 10500 | PDF 1.4-1.6 |
| 10700 | PDF 1.7 Level 8 |
| 11600 | 7-Zip |
| 13600 | WinZip |
| 17200 | PKZIP |
| 13000 | RAR5 |
| 12500 | RAR3-hp |

### Kryptovaluta

| Modus | Hashtype |
|------|-----------|
| 11300 | Bitcoin-lommebok |
| 12700 | Blockchain-lommebok |
| 16600 | Electrum-lommebok |
| 15200 | Ethereum Keystore |

### Liste over og søk i moduser
```bash
# List alle hashmoduser
hashcat --help | grep -i "hash modes"

# Søk etter en bestemt hash
hashcat --example-hashes | grep -i "ntlm"
hashcat --example-hashes | grep -i "sha256"

# Vis eksempel på hashformat
hashcat -m 1000 --example-hashes
```

---

## 🎭 Maskeangrep

### Plassholdere for tegnsett

| Plassholder | Tegn |
|-------------|------------|
| `?l` | abcdefghijklmnopqrstuvwxyz |
| `?u` | ABCDEFGHIJKLMNOPQRSTUVWXYZ |
| `?d` | 0123456789 |
| `?h` | 0123456789abcdef |
| `?H` | 0123456789ABCDEF |
| `?s` | ``!"#$%&'()*+,-./:;<=>?@[]^_`{\|}~`` |
| `?a` | ?l?u?d?s |
| `?b` | 0x00 - 0xff |

### Egne tegnsett

```bash
# Definer et eget tegnsett
hashcat -a 3 -1 ?l?d hash.txt ?1?1?1?1?1?1

# Flere egne tegnsett
hashcat -a 3 -1 abc -2 123 hash.txt ?1?2?1?2

# Spesialtegn
hashcat -a 3 -1 '!@#$%' hash.txt pass?1?1?1?1
```

### Inkrementell modus

```bash
# Prøv lengder fra 1 til 8
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment

# Angi minimum og maksimum
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment --increment-min=4 --increment-max=8
```

### Vanlige maskemønstre

```bash
# 8 små bokstaver
?l?l?l?l?l?l?l?l

# Password + 4 sifre
password?d?d?d?d

# Stor bokstav + små bokstaver + sifre
?u?l?l?l?l?l?d?d

# Mønster for Winter2024
?u?l?l?l?l?l?d?d?d?d

# Telefonnummer
?d?d?d?d?d?d?d?d?d?d
```

---

## 📜 Regler

### Medfølgende regelfiler

```bash
# Plassering
ls /usr/share/hashcat/rules/

# Populære regler
best64.rule           # De beste 64 reglene
rockyou-30000.rule    # De øverste 30000 reglene
d3ad0ne.rule          # D3ad0ne-regler
dive.rule             # Dive-regler
generated.rule        # Genererte regler
```

### Bruke regler

```bash
# Én regelfil
hashcat -m 0 -a 0 hash.txt wordlist.txt -r best64.rule

# Flere regelfiler
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rule1.rule -r rule2.rule

# Generer regler
hashcat -m 0 -a 0 hash.txt wordlist.txt -g 1000
```

### Vanlige regeloperasjoner

> **Presisering:** `sXY` er en skjematisk erstatning av tegnet X med Y; resultatet `pa$$word` forutsetter andre konkrete argumenter. Flere regelfiler kan kombinere regeloperasjoner, og regeldialekter er ikke alltid identiske mellom verktøy. Originalens regelverdier er beholdt; se [regeldokumentasjonen](https://hashcat.net/wiki/doku.php?id=rule_based_attack).

| Regel | Beskrivelse | Eksempel |
|------|-------------|---------|
| `:` | Ingen endring | password |
| `l` | Gjør alle bokstaver små | PASSWORD → password |
| `u` | Gjør alle bokstaver store | password → PASSWORD |
| `c` | Gjør første bokstav stor | password → Password |
| `C` | Liten første bokstav, resten store | password → pASSWORD |
| `t` | Bytt mellom store og små bokstaver | password → PASSWORD |
| `r` | Snu rekkefølgen | password → drowssap |
| `d` | Dupliser | pass → passpass |
| `$X` | Legg X til slutt | pass → passX |
| `^X` | Legg X først | pass → Xpass |
| `sXY` | Erstatt X med Y | password → pa$$word |

---

## 💾 Øktbehandling

### Opprette en økt
```bash
# Navngitt økt
hashcat -m 0 -a 0 hash.txt wordlist.txt --session=mysession

# Øktfiler lagres i ~/.hashcat/sessions/ (originalens sti; oppsettet kan variere)
```

### Pause og gjenopptakelse
```bash
# Pause: trykk på 'p' under knekking
# Fortsett: trykk på 'r' under knekking (versjonsavhengig)

# Eller gjenoppta fra kommandolinjen
hashcat --session=mysession --restore
```

### Statuskommandoer

Under knekking:
- `s` – vis status
- `p` – sett på pause
- `r` – fortsett (versjonsavhengig)
- `b` – hopp over gjeldende angrep
- `c` – kontrollpunkt
- `q` – avslutt

### Alternativer for utdata

> **Rettelse:** `--outfile-format=2` betyr klartekstpassordet alene, ikke `hash:plain` som kommentaren hevder. Utdataformat og interaktive taster kan variere med versjonen; den opprinnelige linjen er bevart. Se [Hashcats alternativ- og formattabeller](https://hashcat.net/wiki/doku.php?id=hashcat).
```bash
# Skriv utdata til fil
hashcat -m 0 -a 0 hash.txt wordlist.txt -o cracked.txt

# Utdataformat
hashcat -m 0 hash.txt --outfile-format=2    # hash:plain (originalens beskrivelse; se presiseringen)

# Vis knekte hasher
hashcat -m 0 hash.txt --show

# Vis gjenværende hasher som ikke er knekt
hashcat -m 0 hash.txt --left
```

---

## 🚀 GPU-optimalisering

### Belastningsprofiler

> **Versjonsmerknad:** Profilnavn, standardprofil, pakker og enhets-/backend-støtte må kontrolleres for installasjonen; tabellen gjengir originalen. `--benchmark-all` endrer omfanget av ytelsestesten, ikke generelt om OpenCL-optimalisering brukes. `--force` ignorerer advarsler og er ikke en generell ytelsesgaranti. `-O` kan begrense støttet passordlengde. Se [Hashcats versjonsspesifikke hjelp](https://hashcat.net/wiki/doku.php?id=hashcat).

| Profil | Beskrivelse |
|---------|-------------|
| `-w 1` | Lav (standard ifølge originalen, interaktiv) |
| `-w 2` | Middels |
| `-w 3` | Høy (kan fryse skrivebordet) |
| `-w 4` | Nightmare (dedikert knekking) |

### Optimaliserte kommandoer
```bash
# Høy ytelse
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 3 -O

# Optimaliserte kjerner (raskere, begrenset passordlengde)
hashcat -m 0 -a 0 hash.txt wordlist.txt -O

# Velg enhet
hashcat -m 0 -a 0 hash.txt wordlist.txt -d 1

# Deaktiver CPU, bruk bare GPU
hashcat -m 0 -a 0 hash.txt wordlist.txt -D 2
```

### Enhetsalternativer
```bash
# List enheter
hashcat -I

# Velg en bestemt enhet
hashcat -d 1 ...

# Enhetstyper
# -D 1 = CPU
# -D 2 = GPU
# -D 3 = FPGA
```

### Ytelsestest
```bash
# Full ytelsestest
hashcat -b

# Bestemt hashtype
hashcat -b -m 0

# Ytelsestest uten OpenCL-optimalisering (originalens beskrivelse; se presiseringen)
hashcat -b --benchmark-all
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: Ordlisteangrep mot MD5
```bash
hashcat -m 0 -a 0 md5_hashes.txt /usr/share/wordlists/rockyou.txt
```

### Eksempel 2: NTLM med regler
```bash
hashcat -m 1000 -a 0 ntlm_hashes.txt wordlist.txt -r /usr/share/hashcat/rules/best64.rule
```

### Eksempel 3: WPA/WPA2-håndtrykk
```bash
# Konverter først til Hashcat-format
hcxpcapngtool -o hash.hc22000 capture.pcapng

# Knekk
hashcat -m 22000 hash.hc22000 /usr/share/wordlists/rockyou.txt
```

### Eksempel 4: Brute force mot SHA256 (6 tegn)
```bash
hashcat -m 1400 -a 3 sha256_hash.txt ?a?a?a?a?a?a
```

### Eksempel 5: Office 2013-dokument
```bash
# Hent ut hash med office2john
office2john document.docx > office.hash

# Knekk
hashcat -m 9600 office.hash wordlist.txt
```

### Eksempel 6: Passord fra Linux-shadow
```bash
hashcat -m 1800 -a 0 shadow_hash.txt wordlist.txt
```

### Eksempel 7: WordPress-hash
```bash
hashcat -m 400 -a 0 wordpress_hash.txt wordlist.txt
```

### Eksempel 8: Hybridangrep
```bash
# Virksomhetsnavn + 4 sifre
hashcat -m 0 -a 6 hash.txt company_names.txt ?d?d?d?d
```

### Eksempel 9: Hasher fra Windows-domene
```bash
# NTLM-hasher fra secretsdump
hashcat -m 1000 -a 0 ntds_hashes.txt wordlist.txt -r best64.rule --username
```

### Eksempel 10: Bitcoin-lommebok
```bash
# Hent ut med bitcoin2john
hashcat -m 11300 bitcoin_hash.txt wordlist.txt
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Oppgave | Kommando |
|------|---------|
| MD5 med ordliste | `hashcat -m 0 -a 0 hash.txt wordlist.txt` |
| Brute force mot NTLM | `hashcat -m 1000 -a 3 hash.txt ?a?a?a?a?a?a` |
| SHA256 med regler | `hashcat -m 1400 -a 0 hash.txt wordlist.txt -r rules.rule` |
| Vis knekte hasher | `hashcat -m 0 hash.txt --show` |
| Ytelsestest | `hashcat -b` |
| List enheter | `hashcat -I` |
| Gjenoppta økt | `hashcat --session=name --restore` |

### Vanlige hashmoduser

| Modus | Type |
|------|------|
| 0 | MD5 |
| 100 | SHA1 |
| 1000 | NTLM |
| 1400 | SHA256 |
| 1800 | sha512crypt |
| 3200 | bcrypt |
| 22000 | WPA2 |

### Angrepsmoduser

| Modus | Type |
|------|------|
| -a 0 | Ordliste |
| -a 1 | Kombinasjon |
| -a 3 | Brute force |
| -a 6 | Hybrid (ordliste+maske) |
| -a 7 | Hybrid (maske+ordliste) |

---

## 💡 Tips og anbefalte arbeidsmåter

### Effektivitetstips

1. **Begynn med ordliste og regler**
   ```bash
   hashcat -m 0 -a 0 hash.txt rockyou.txt -r best64.rule
   ```

2. **Bruk optimaliserte kjerner**
   ```bash
   hashcat -O ...
   ```

3. **Sikt mot vanlige mønstre**
   ```bash
   # Årstid+år: Winter2024
   hashcat -a 3 -1 WSFJ -2 winterumeall hash.txt ?1?2?2?2?2?2?d?d?d?d
   ```

### Ytelsestips

```bash
# Maksimal ytelse (dedikert system)
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 4 -O --force

# Flere GPU-er
hashcat -d 1,2 ...
```

### Arbeidsflyt

```
1. Identifiser hashtypen
2. Prøv ordlisteangrep med regler
3. Prøv målrettede masker (mønstre)
4. Prøv full brute force (hvis gjennomførbart)
5. Prøv hybridangrep
```

---

## ⚠️ Juridisk ansvarsfraskrivelse

> **ADVARSEL:** Hashcat er et kraftig verktøy for passordknekking som bare skal brukes til **autoriserte formål**.
> 
> - ✅ Kontroller dine egne systemer
> - ✅ Sikkerhetsvurderinger med tillatelse
> - ✅ Gjenoppretting av dine egne passord
> - ❌ Ikke knekk passord uten tillatelse
> - ❌ Uautorisert tilgang er ulovlig
> 
> **Passordknekking uten tillatelse er ulovlig og kan føre til straffansvar.**

---

## 📚 Ressurser

### Offisielle ressurser
- [Hashcat – offisielt nettsted](https://hashcat.net/hashcat/)
- [Hashcat-wiki](https://hashcat.net/wiki/)
- [Hashcat GitHub](https://github.com/hashcat/hashcat)
- [Hashcat-forum](https://hashcat.net/forum/)

### Ordlister
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Relaterte hurtigreferanser
- [John the Ripper](../../John-The-Ripper/translations/README.nb.md)
- [Hydra](../../Hydra/translations/README.nb.md)
- [Nmap](../../Nmap/translations/README.nb.md)

---

<p align="center">
  <b>⚡ Lær GPU-basert passordknekking!</b><br>
  <i>Rask passordgjenoppretting</i>
</p>
