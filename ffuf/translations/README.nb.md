# 🚀 ffuf – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

> **Begreper og presisering:** Fuzzing prøver varianter av inndata. VHost-testing med Host-headeren undersøker virtuelle verter og er ikke det samme som DNS-oppslag. Treff/filtre og responsstørrelse må tilpasses målmiljøet; wildcard-svar kan ligne gyldige funn. Hastighetsstjerner og verktøysammenligninger er illustrative, ikke målinger. Verken lavere hastighet eller automatisk kalibrering garanterer WAF-omgåelse eller korrekte funn. Syntaks, standardverdier og støtte for rekursjon varierer med versjonen; se [ffufs egen veiledning](https://github.com/ffuf/ffuf).

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   __  __        __ 
  / _|/ _|_   _ / _|
 | |_| |_| | | | |_ 
 |  _|  _| |_| |  _|
 |_| |_|  \__,_|_|  
                    
   Fuzz Faster U Fool
```

<p align="center">
  <img src="https://img.shields.io/badge/ffuf-Web_Fuzzer-red?style=for-the-badge" alt="ffuf">
  <img src="https://img.shields.io/badge/Fast-Fuzzing-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Go-Language-green?style=for-the-badge" alt="Go">
</p>

<p align="center">
  <b>⚡ En rask webfuzzer skrevet i Go</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Grunnleggende syntaks](#-grunnleggende-syntaks)
- [Fuzzing av kataloger](#-fuzzing-av-kataloger)
- [Fuzzing av underdomener](#-fuzzing-av-underdomener)
- [Fuzzing av parametere](#-fuzzing-av-parametere)
- [Fuzzing av POST-data](#-fuzzing-av-post-data)
- [Filtrering og treff](#-filtrering-og-treff)
- [Avanserte funksjoner](#-avanserte-funksjoner)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

**ffuf** (Fuzz Faster U Fool) er en rask webfuzzer skrevet i Go. Den brukes til utprøving av katalognavn, oppdagelse av virtuelle verter og fuzzing av parametere i bug bounty.

### Viktige egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **Hastighet** | Svært raskt (Go-basert) |
| **Fleksibelt** | Varier alle deler av en HTTP-forespørsel |
| **Filtrering** | Flere valg for filtrering og treff |
| **Rekursjon** | Støtte for rekursiv skanning |
| **Utdata** | Flere utdataformater |
| **Enkelt** | Enkel og oversiktlig syntaks |

### ffuf sammenlignet med andre verktøy

| Egenskap | ffuf | Gobuster | dirb |
|---------|------|----------|------|
| **Hastighet** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |
| **Fleksibilitet** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| **POST-fuzzing** | ✅ Ja | ❌ Nei | ❌ Nei |
| **Rekursjon** | ✅ Ja | ✅ Ja | ✅ Ja |
| **Filtre** | Mange | Begrenset | Begrenset |

---

## 📥 Installasjon

### Med Go
```bash
go install github.com/ffuf/ffuf/v2@latest
```

### Kali Linux
```bash
sudo apt install ffuf
```

### Nedlasting av binærfil
```bash
# Last ned fra utgivelser
https://github.com/ffuf/ffuf/releases

# Linux
wget https://github.com/ffuf/ffuf/releases/download/v2.x.x/ffuf_2.x.x_linux_amd64.tar.gz
tar xzf ffuf_2.x.x_linux_amd64.tar.gz
sudo mv ffuf /usr/local/bin/
```

### Kontroller installasjonen
```bash
ffuf -V
ffuf -h
```

---

<a id="-grunnleggende-syntaks"></a>
## ⌨️ Grunnleggende syntaks

### Generell syntaks
```bash
ffuf [options] -u <URL/FUZZ> -w <wordlist>
```

### Nøkkelordet FUZZ

Nøkkelordet **FUZZ** viser hvor ffuf skal sette inn ord fra ordlisten.

```bash
# I URL-stien
ffuf -u https://target.com/FUZZ -w wordlist.txt

# I underdomenet
ffuf -u https://FUZZ.target.com -w wordlist.txt

# I parameterverdien
ffuf -u https://target.com/page?id=FUZZ -w wordlist.txt

# I POST-data
ffuf -u https://target.com/login -X POST -d "user=FUZZ&pass=test" -w wordlist.txt
```

### Viktige alternativer

| Innstilling | Beskrivelse |
|--------|-------------|
| `-u` | Mål-URL med nøkkelordet FUZZ |
| `-w` | Sti til ordlisten |
| `-o` | Utdatafil |
| `-of` | Utdataformat (json, csv, html) |
| `-t` | Antall tråder (standard: 40) |
| `-p` | Forsinkelse mellom forespørsler |
| `-r` | Følg omdirigeringer |
| `-v` | Detaljerte utdata |
| `-s` | Stille modus |

---

## 📁 Fuzzing av kataloger

### Grunnleggende utprøving av katalognavn

```bash
# Grunnleggende katalogfuzzing
ffuf -u https://target.com/FUZZ -w /usr/share/wordlists/dirb/common.txt

# Med filendelser
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.html,.txt,.bak

# Flere filendelser
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.asp,.aspx,.jsp,.html
```

### Rekursiv skanning

```bash
# Aktiver rekursjon
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion

# Rekursjonsdybde
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2

# Rekursjon med strategi
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-strategy greedy
```

### Filtrere etter statuskode

```bash
# Treff bare 200-responser
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200

# Treff flere koder
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200,301,302,403

# Filtrer bort 404 (endring av standardatferd)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404

# Treff alle, filtrer bort 404
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Vanlige ordlister

```bash
# SecLists
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
```

---

## 🌐 Fuzzing av underdomener

### Oppdagelse av virtuelle verter

```bash
# Grunnleggende underdomenefuzzing
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt

# Filtrer etter responsstørrelse (fjern standardsiden)
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt -fs 1234

# Med en bestemt ordliste
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

### Fuzzing av DNS-underdomener

```bash
# Underdomene i URL-en
ffuf -u https://FUZZ.target.com -w subdomains.txt

# Med resolver
ffuf -u https://FUZZ.target.com -w subdomains.txt -r
```

### Vanlige ordlister for underdomener

```bash
# SecLists DNS
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

---

## 🔍 Fuzzing av parametere

### Fuzzing av GET-parametere

```bash
# Varier parameternavnet
ffuf -u "https://target.com/page?FUZZ=test" -w params.txt

# Varier parameterverdien
ffuf -u "https://target.com/page?id=FUZZ" -w values.txt

# Flere parametere
ffuf -u "https://target.com/page?W1=test&W2=test" -w params.txt:W1 -w values.txt:W2
```

### Oppdagelse av parametere

```bash
# Finn skjulte parametere
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

---

## 📝 Fuzzing av POST-data

### Grunnleggende POST-fuzzing

```bash
# POST med skjemadata
ffuf -u https://target.com/login -X POST \
    -d "username=FUZZ&password=test" \
    -w usernames.txt

# POST med innholdstype
ffuf -u https://target.com/login -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=FUZZ&password=test" \
    -w usernames.txt
```

### Fuzzing av JSON-POST

```bash
# JSON-data i POST
ffuf -u https://target.com/api/login -X POST \
    -H "Content-Type: application/json" \
    -d '{"username":"FUZZ","password":"test"}' \
    -w usernames.txt
```

### Flere ordlister (credential stuffing)

```bash
# Fuzzing av brukernavn og passord
ffuf -u https://target.com/login -X POST \
    -d "username=USER&password=PASS" \
    -w users.txt:USER \
    -w passwords.txt:PASS \
    -fc 401

# Modus: clusterbomb (alle kombinasjoner)
ffuf -u https://target.com/login -X POST \
    -d "user=USER&pass=PASS" \
    -w users.txt:USER \
    -w passes.txt:PASS \
    -mode clusterbomb
```

---

## 🎯 Filtrering og treff

### Filteralternativer

| Alternativ | Beskrivelse | Eksempel |
|--------|-------------|---------|
| `-fc` | Filtrer etter statuskode | `-fc 404,403` |
| `-fs` | Filtrer etter responsstørrelse | `-fs 1234` |
| `-fw` | Filtrer etter antall ord | `-fw 100` |
| `-fl` | Filtrer etter antall linjer | `-fl 50` |
| `-ft` | Filtrer etter tid (ms) | `-ft 5000` |
| `-fr` | Filtrer etter regulært uttrykk | `-fr "error"` |

### Treffalternativer

| Alternativ | Beskrivelse | Eksempel |
|--------|-------------|---------|
| `-mc` | Treff statuskoder | `-mc 200,301` |
| `-ms` | Treff responsstørrelse | `-ms 1234` |
| `-mw` | Treff antall ord | `-mw 100` |
| `-ml` | Treff antall linjer | `-ml 50` |
| `-mt` | Treff tid (ms) | `-mt 5000` |
| `-mr` | Treff med regulært uttrykk | `-mr "success"` |

### Filtereksempler

```bash
# Filtrer bort 404 og 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404,403

# Filtrer etter størrelse (fjern standardsiden)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fs 0,1234

# Filtrer etter ord
ffuf -u https://target.com/FUZZ -w wordlist.txt -fw 10

# Treff alle, filtrer bestemte responser
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Automatisk kalibrering

```bash
# Kalibrer filtrene automatisk
ffuf -u https://target.com/FUZZ -w wordlist.txt -ac

# Kalibrering med et bestemt nøkkelord
ffuf -u https://target.com/FUZZ -w wordlist.txt -acc "randomstring123"
```

---

## ⚡ Avanserte funksjoner

### Hastighetsbegrensning

```bash
# Forespørsler per sekund
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 100

# Forsinkelse mellom forespørsler
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.5

# Tilfeldig forsinkelse (jitter)
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.1-1.0
```

### Tråder

```bash
# Angi tråder (standard: 40)
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 100

# Reduser for mindre støy
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 10 -p 1
```

### Headere

```bash
# Egendefinert header
ffuf -u https://target.com/FUZZ -w wordlist.txt -H "X-Custom: value"

# Flere headere
ffuf -u https://target.com/FUZZ -w wordlist.txt \
    -H "Authorization: Bearer token" \
    -H "X-Forwarded-For: 127.0.0.1"

# Informasjonskapsel
ffuf -u https://target.com/FUZZ -w wordlist.txt -b "session=abc123"
```

### Proxy

```bash
# HTTP-proxy
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080

# Burp Suite
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080
```

### Utdata

```bash
# JSON-utdata
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.json -of json

# CSV-utdata
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.csv -of csv

# HTML-utdata
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.html -of html

# Alle formater
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results -of all
```

### Tidsavbrudd og nye forsøk

```bash
# Tidsavbrudd for forespørsel
ffuf -u https://target.com/FUZZ -w wordlist.txt -timeout 10

# Stopp ved 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -se

# Ignorer SSL-feil
ffuf -u https://target.com/FUZZ -w wordlist.txt -k
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: Grunnleggende katalogskanning
```bash
ffuf -u https://target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/common.txt -mc 200,301,302
```

### Eksempel 2: Finn PHP-filer
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.php.bak,.php~ -mc 200
```

### Eksempel 3: Kartlegging av underdomener
```bash
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    -fs 1234 -t 100
```

### Eksempel 4: Oppdagelse av API-endepunkter
```bash
ffuf -u https://api.target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -mc 200,401,403
```

### Eksempel 5: Oppdagelse av parametere
```bash
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

### Eksempel 6: Utprøving av pålogginger
```bash
ffuf -u https://target.com/login -X POST \
    -d "user=admin&pass=FUZZ" \
    -w /usr/share/seclists/Passwords/Common-Credentials/10k-most-common.txt \
    -fc 401 -t 10
```

### Eksempel 7: Med proxy (Burp)
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080 -k
```

### Eksempel 8: Rekursiv skanning
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2 -mc 200,301
```

### Eksempel 9: Hastighetsbegrenset skanning
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 10 -t 5
```

### Eksempel 10: Omfattende bug bounty-skanning
```bash
ffuf -u https://target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt \
    -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak \
    -mc 200,301,302,403 \
    -recursion -recursion-depth 2 \
    -o results.json -of json \
    -ac
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Oppgave | Kommando |
|------|---------|
| Katalogskanning | `ffuf -u URL/FUZZ -w wordlist.txt` |
| Med filendelser | `ffuf -u URL/FUZZ -w wordlist.txt -e .php,.html` |
| Underdomeneskanning | `ffuf -u URL -H "Host: FUZZ.domain" -w subs.txt` |
| POST-fuzzing | `ffuf -u URL -X POST -d "param=FUZZ" -w wordlist.txt` |
| Filtrer bort 404 | `ffuf -u URL/FUZZ -w wordlist.txt -fc 404` |
| Filtrer etter størrelse | `ffuf -u URL/FUZZ -w wordlist.txt -fs 1234` |
| Kalibrer automatisk | `ffuf -u URL/FUZZ -w wordlist.txt -ac` |
| Lagre utdata | `ffuf -u URL/FUZZ -w wordlist.txt -o out.json -of json` |

### Vanlige alternativer

| Innstilling | Beskrivelse |
|--------|-------------|
| `-u` | URL med FUZZ |
| `-w` | Ordliste |
| `-e` | Filendelser |
| `-t` | Tråder |
| `-mc` | Treffkoder |
| `-fc` | Filterkoder |
| `-fs` | Filterstørrelse |
| `-ac` | Automatisk kalibrering |
| `-r` | Følg omdirigeringer |
| `-o` | Utdatafil |

---

## 💡 Tips og anbefalte arbeidsmåter

### Tips for bug bounty

1. **Kalibrer alltid automatisk først**
   ```bash
   ffuf -u target.com/FUZZ -w wordlist.txt -ac
   ```

2. **Bruk flere filendelser**
   ```bash
   -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak,.old,.zip
   ```

3. **Prøv vanlige endelser for sikkerhetskopier**
   ```bash
   -e .bak,.old,.orig,.backup,~,.swp
   ```

4. **Begrens hastigheten for å unngå WAF-blokkering**
   ```bash
   -rate 10 -t 5 -p 0.5
   ```

### Ytelsestips

```bash
# Høy hastighet (uten WAF)
ffuf -u URL/FUZZ -w wordlist.txt -t 200

# Lav hastighet og lite støy
ffuf -u URL/FUZZ -w wordlist.txt -t 10 -rate 5 -p 1
```

---

## ⚠️ Juridisk merknad

> **ADVARSEL:** ffuf er et kraftig fuzzingverktøy som bare skal brukes til **autorisert testing**.
> 
> - ✅ Bruk mot programmer du har tillatelse til å teste
> - ✅ Bruk i autoriserte bug bounty-programmer
> - ✅ Respekter hastighetsgrenser og omfang
> - ❌ Fuzz aldri uten tillatelse
> - ❌ Ikke forårsak tjenestenekt
> 
> **Uautorisert skanning er ulovlig.**

---

## 📚 Ressurser

### Offisielle ressurser
- [ffuf GitHub](https://github.com/ffuf/ffuf)
- [ffuf Wiki](https://github.com/ffuf/ffuf/wiki)

### Ordlister
- [SecLists](https://github.com/danielmiessler/SecLists)
- [Assetnote Wordlists](https://wordlists.assetnote.io/)

### Relaterte hurtigreferanser
- [Nuclei](../../Nuclei/translations/README.nb.md)
- [Gobuster](../../Gobuster/translations/README.nb.md)
- [Nmap](../../Nmap/translations/README.nb.md)

---

<p align="center">
  <b>🚀 Fuzz raskere og finn mer!</b><br>
  <i>En nyttig fuzzer for bug bounty-jegeren</i>
</p>
