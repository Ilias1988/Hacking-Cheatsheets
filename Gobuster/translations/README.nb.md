# 🚀 Gobuster – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗  ██████╗ ██████╗ ██╗   ██╗███████╗████████╗███████╗██████╗ 
  ██╔════╝ ██╔═══██╗██╔══██╗██║   ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗
  ██║  ███╗██║   ██║██████╔╝██║   ██║███████╗   ██║   █████╗  ██████╔╝
  ██║   ██║██║   ██║██╔══██╗██║   ██║╚════██║   ██║   ██╔══╝  ██╔══██╗
  ╚██████╔╝╚██████╔╝██████╔╝╚██████╔╝███████║   ██║   ███████╗██║  ██║
   ╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
                Kartlegging av kataloger, DNS og virtuelle verter
```

<p align="center">
  <img src="https://img.shields.io/badge/Gobuster-Enumeration-blue?style=for-the-badge" alt="Gobuster">
  <img src="https://img.shields.io/badge/Directory-Bruteforce-red?style=for-the-badge" alt="Directory">
  <img src="https://img.shields.io/badge/DNS-Subdomain-green?style=for-the-badge" alt="DNS">
  <img src="https://img.shields.io/badge/VHost-Discovery-purple?style=for-the-badge" alt="VHost">
</p>

<p align="center">
  <b>🔍 Rask kartlegging av kataloger, DNS og virtuelle verter, skrevet i Go</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Oversikt over moduser](#-oversikt-over-moduser)
- [Katalogmodus (dir)](#-katalogmodus-dir)
- [DNS-modus (dns)](#-dns-modus-dns)
- [VHost-modus (vhost)](#-vhost-modus-vhost)
- [Fuzz-modus (fuzz)](#-fuzz-modus-fuzz)
- [Ordlister](#-ordlister)
- [Autentisering og headere](#-autentisering-og-headere)
- [Ytelsesjustering](#-ytelsesjustering)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

> **Begreper og versjoner:** Kartlegging (enumeration) prøver navn fra en ordliste; en virtuell vert (VHost) er et nettsted valgt blant annet via vertsnavnet i HTTP. Tabellens hastighetsstjerner er originalens illustrative rangering, ikke en måling. Flagg og standardverdier varierer mellom versjoner: dagens prosjektveiledning viser blant annet `-do` i DNS-modus, mens originalen bruker `-d`. Ikke alle eldre flaggnavn i tabellene er nødvendigvis gyldige i din versjon. Se [Gobusters egen veiledning](https://github.com/OJ/gobuster). Eksemplene beholdes uendret. `--no-status` må heller ikke tolkes som et generelt valg for å inkludere alle HTTP-responser; filtrering og visningsformat er forskjellige forhold. I [kildekoden for v3.6.0](https://github.com/OJ/gobuster/blob/v3.6.0/cli/cmd/dir.go) er dette et valg for å skjule utskrift av statuskoder.

**Gobuster** er et raskt verktøy skrevet i Go som bruker flere tråder til systematisk utprøving (brute force) for:
- Kartlegging av kataloger og filer på webservere
- Oppdagelse av DNS-underdomener
- Oppdagelse av virtuelle verter (VHost)
- Fuzzing av URL-er

### Hvorfor Gobuster?

| Egenskap | Beskrivelse |
|---------|-------------|
| **Rask** | Skrevet i Go med høy grad av parallellitet |
| **Fleksibelt** | Flere moduser for ulike oppgaver |
| **Ingen rekursjon** | Oversiktlige resultater uten støy |
| **Stabilt** | Håndterer feil kontrollert |
| **Aktiv utvikling** | Regelmessige oppdateringer |

### Sammenligning med andre verktøy

| Verktøy | Hastighet | Rekursjon | Språk |
|------|-------|-----------|----------|
| **Gobuster** | ⭐⭐⭐⭐⭐ | Nei | Go |
| Dirb | ⭐⭐ | Ja | C |
| Dirbuster | ⭐⭐⭐ | Ja | Java |
| Ffuf | ⭐⭐⭐⭐⭐ | Nei | Go |
| Feroxbuster | ⭐⭐⭐⭐⭐ | Ja | Rust |

---

## 📥 Installasjon

### Kali Linux (forhåndsinstallert)
```bash
# Oppdater Gobuster
sudo apt update && sudo apt install gobuster
```

### Ubuntu/Debian
```bash
sudo apt install gobuster
```

### Fra kildekode (Go)
```bash
go install github.com/OJ/gobuster/v3@latest
```

### Med Snap
```bash
sudo snap install gobuster
```

### Fra GitHub-utgivelser
```bash
# Last ned nyeste utgave
wget https://github.com/OJ/gobuster/releases/latest/download/gobuster-linux-amd64.7z
7z x gobuster-linux-amd64.7z
chmod +x gobuster
sudo mv gobuster /usr/local/bin/
```

### Kontroller installasjonen
```bash
gobuster version
gobuster -h
```

---

## 📊 Oversikt over moduser

### Tilgjengelige moduser

| Modus | Beskrivelse | Bruksområde |
|------|-------------|----------|
| `dir` | Kartlegging av kataloger og filer | Finn skjulte kataloger og filer |
| `dns` | Kartlegging av DNS-underdomener | Finn underdomener |
| `vhost` | Kartlegging av virtuelle verter | Finn virtuelle verter |
| `fuzz` | Fuzzing av URL-er | Fuzz parametere og stier |
| `s3` | Kartlegging av S3-bøtter | Finn AWS S3-bøtter |
| `gcs` | Google Cloud Storage | Finn GCS-bøtter |
| `tftp` | TFTP-kartlegging | Finn TFTP-filer |

### Grunnleggende syntaks
```bash
gobuster [mode] [options]

# Eksempler
gobuster dir -u http://target.com -w wordlist.txt
gobuster dns -d target.com -w subdomains.txt
gobuster vhost -u http://target.com -w vhosts.txt
```

---

## 📁 Katalogmodus (dir)

### Grunnleggende syntaks
```bash
gobuster dir -u <URL> -w <wordlist>
```

### Viktige valg

| Valg | Beskrivelse |
|--------|-------------|
| `-u` | Mål-URL |
| `-w` | Sti til ordliste |
| `-t` | Tråder (standard: 10) |
| `-x` | Filendelser som skal søkes etter |
| `-s` | Statuskoder som skal tas med |
| `-b` | Statuskoder som skal utelates (blacklist) |
| `-o` | Utdatafil |
| `-k` | Hopp over sertifikatkontroll |
| `-r` | Følg omdirigeringer |
| `-a` | User-Agent-streng |
| `-c` | Informasjonskapsler |
| `-H` | Egendefinerte headere |

### Grunnleggende katalogskanning
```bash
# Enkel katalogskanning
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt

# Med flere tråder
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt -t 50
```

### Søk etter filendelser
```bash
# Søk etter bestemte filendelser
gobuster dir -u http://target.com -w wordlist.txt -x php,html,txt

# Flere filendelser
gobuster dir -u http://target.com -w wordlist.txt -x php,asp,aspx,jsp,html,txt,bak,old

# Sikkerhetskopier
gobuster dir -u http://target.com -w wordlist.txt -x bak,backup,old,orig,~
```

### Filtrering etter statuskode
```bash
# Vis bare bestemte statuskoder
gobuster dir -u http://target.com -w wordlist.txt -s 200,204,301,302,307,401

# Utelat statuskoder
gobuster dir -u http://target.com -w wordlist.txt -b 404,403

# Vis alt (inkludert 404)
gobuster dir -u http://target.com -w wordlist.txt --no-status
```

### Utdatavalg
```bash
# Lagre i fil
gobuster dir -u http://target.com -w wordlist.txt -o results.txt

# Detaljert utdata
gobuster dir -u http://target.com -w wordlist.txt -v

# Uten fremdriftsvisning
gobuster dir -u http://target.com -w wordlist.txt -q

# Utvidet modus (fullstendige URL-er)
gobuster dir -u http://target.com -w wordlist.txt -e
```

### SSL-/TLS-valg
```bash
# Hopp over sertifikatkontroll
gobuster dir -u https://target.com -w wordlist.txt -k

# Hopp over sertifikatkontroll med utvidede valg
gobuster dir -u https://target.com -w wordlist.txt -k --no-tls-validation
```

### Eksempler på katalogmodus
```bash
# Vanlig skanning av webkataloger
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt -t 50

# PHP-applikasjon
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x php -t 50

# Oppdagelse av API-endepunkter
gobuster dir -u http://target.com/api -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt -t 30

# Finn sikkerhetskopier
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x bak,backup,old,zip,tar.gz -t 50
```

---

## 🌐 DNS-modus (dns)

### Grunnleggende syntaks
```bash
gobuster dns -d <domain> -w <wordlist>
```

### DNS-valg

| Valg | Beskrivelse |
|--------|-------------|
| `-d` | Måldomene |
| `-w` | Sti til ordliste |
| `-t` | Tråder |
| `-r` | Egendefinert DNS-resolver |
| `-i` | Vis IP-adresser |
| `-c` | Vis CNAME-oppføringer |
| `--wildcard` | Tving behandling av jokertegn |
| `--timeout` | Tidsavbrudd for DNS |

### Grunnleggende DNS-kartlegging
```bash
# Enkel skanning etter underdomener
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# Vis IP-adresser
gobuster dns -d target.com -w subdomains.txt -i

# Vis CNAME-oppføringer
gobuster dns -d target.com -w subdomains.txt -c
```

### Egendefinert DNS-resolver
```bash
# Bruk en bestemt DNS-server
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8

# Flere resolvere
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8:53
```

### DNS-eksempler
```bash
# Omfattende skanning etter underdomener
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt -t 50 -i

# Rask skanning etter underdomener
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt -t 100

# Med utdata
gobuster dns -d target.com -w subdomains.txt -i -o subdomains_found.txt
```

---

## 🏠 VHost-modus (vhost)

### Grunnleggende syntaks
```bash
gobuster vhost -u <URL> -w <wordlist>
```

### VHost-valg

| Valg | Beskrivelse |
|--------|-------------|
| `-u` | Mål-URL |
| `-w` | Sti til ordliste |
| `-t` | Tråder |
| `--append-domain` | Legg domenet til ordlisteoppføringene |
| `--domain` | Domene for virtuell vert |
| `-r` | Følg omdirigeringer |

### Grunnleggende VHost-kartlegging
```bash
# Enkel VHost-skanning
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# Med tilføying av domene
gobuster vhost -u http://target.com -w subdomains.txt --append-domain
```

### VHost-eksempler
```bash
# Vanlig oppdagelse av virtuelle verter
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/namelist.txt -t 50

# Utviklingsverter
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt --append-domain

# Med egendefinert domene
gobuster vhost -u http://10.10.10.10 -w subdomains.txt --domain target.htb
```

---

## 🔧 Fuzz-modus (fuzz)

### Grunnleggende syntaks
```bash
gobuster fuzz -u <URL with FUZZ> -w <wordlist>
```

### Fuzz-valg

| Valg | Beskrivelse |
|--------|-------------|
| `-u` | URL med nøkkelordet FUZZ |
| `-w` | Sti til ordliste |
| `-t` | Tråder |
| `-b` | Utelat statuskoder |
| `--exclude-length` | Utelat etter responslengde |

### Fuzz-eksempler
```bash
# Fuzz en parameterverdi
gobuster fuzz -u "http://target.com/page?id=FUZZ" -w numbers.txt

# Fuzz en sti
gobuster fuzz -u "http://target.com/FUZZ/admin" -w wordlist.txt

# Utelat bestemte lengder
gobuster fuzz -u "http://target.com/FUZZ" -w wordlist.txt --exclude-length 0,42
```

---

## 📚 Ordlister

### SecLists-plasseringer (Kali Linux)

```bash
# Kartlegging av kataloger og filer
/usr/share/seclists/Discovery/Web-Content/

# Vanlige kataloglister
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-small.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# DNS/underdomener
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

### Standardordlister

```bash
# Dirb-ordlister
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
/usr/share/wordlists/dirb/small.txt

# Dirbuster-ordlister
/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
/usr/share/wordlists/dirbuster/directory-list-2.3-small.txt
```

### Anbefalte ordlister

| Bruksområde | Ordliste |
|----------|----------|
| Rask skanning | `common.txt` |
| Standard | `directory-list-2.3-medium.txt` |
| Grundig | `directory-list-2.3-big.txt` |
| API | `api-endpoints.txt` |
| Underdomener | `subdomains-top1million-5000.txt` |
| Filer | `raft-large-files.txt` |

---

## 🔐 Autentisering og headere

### Basic-autentisering
```bash
# HTTP Basic-autentisering
gobuster dir -u http://target.com -w wordlist.txt -U username -P password
```

### Autentisering med informasjonskapsler
```bash
# Én informasjonskapsel
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123"

# Flere informasjonskapsler
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123; token=xyz789"

# PHP-økt
gobuster dir -u http://target.com -w wordlist.txt -c "PHPSESSID=abc123def456"
```

### Egendefinerte headere
```bash
# Én header
gobuster dir -u http://target.com -w wordlist.txt -H "Authorization: Bearer token123"

# Flere headere
gobuster dir -u http://target.com -w wordlist.txt -H "X-Forwarded-For: 127.0.0.1" -H "X-Custom: value"

# API-nøkkel
gobuster dir -u http://target.com/api -w wordlist.txt -H "X-API-Key: your-api-key"
```

### User-Agent
```bash
# Egendefinert User-Agent
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Googlebot
gobuster dir -u http://target.com -w wordlist.txt -a "Googlebot/2.1"
```

### Proxystøtte
```bash
# HTTP-proxy
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080

# SOCKS5-proxy
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050

# Burp Suite-proxy
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080 -k
```

---

## ⚡ Ytelsesjustering

### Trådstyring
```bash
# Standard: 10 tråder
gobuster dir -u http://target.com -w wordlist.txt

# Øk antall tråder (raskt nettverk)
gobuster dir -u http://target.com -w wordlist.txt -t 100

# Reduser antall tråder (langsomt eller ustabilt)
gobuster dir -u http://target.com -w wordlist.txt -t 5
```

### Innstillinger for tidsavbrudd
```bash
# Øk tidsavbruddet
gobuster dir -u http://target.com -w wordlist.txt --timeout 30s

# Tidsavbrudd for DNS
gobuster dns -d target.com -w subdomains.txt --timeout 5s
```

### Forsinkelse mellom forespørsler
```bash
# Legg til forsinkelse (begrens forespørselsraten)
gobuster dir -u http://target.com -w wordlist.txt --delay 100ms

# Langsommere for å omgå WAF
gobuster dir -u http://target.com -w wordlist.txt --delay 500ms
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: Vanlig webkartlegging
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt \
    -t 50 \
    -x php,html,txt \
    -o results.txt
```

### Eksempel 2: Oppdagelse av API-endepunkter
```bash
gobuster dir -u http://target.com/api/v1 \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -t 30 \
    -H "Content-Type: application/json"
```

### Eksempel 3: Autentisert skanning
```bash
gobuster dir -u http://target.com/admin \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -c "PHPSESSID=abc123" \
    -t 20
```

### Eksempel 4: Kartlegging av underdomener
```bash
gobuster dns -d target.com \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt \
    -t 100 \
    -i \
    -o subdomains.txt
```

### Eksempel 5: VHost-oppdagelse (HackTheBox)
```bash
gobuster vhost -u http://10.10.10.10 \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    --domain target.htb \
    --append-domain
```

### Eksempel 6: Gjennom Burp-proxy
```bash
gobuster dir -u https://target.com \
    -w /usr/share/wordlists/dirb/common.txt \
    --proxy http://127.0.0.1:8080 \
    -k \
    -t 20
```

### Eksempel 7: Søk etter sikkerhetskopier
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -x bak,backup,old,orig,save,swp,txt,zip,tar,gz,sql \
    -t 50
```

### Eksempel 8: Identifikasjon av CMS
```bash
# WordPress
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/wordpress.fuzz.txt \
    -t 50

# Joomla
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/joomla-plugins.fuzz.txt
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Oppgave | Kommando |
|------|---------|
| Grunnleggende katalogskanning | `gobuster dir -u URL -w wordlist.txt` |
| Med filendelser | `gobuster dir -u URL -w wordlist.txt -x php,html` |
| Skanning etter underdomener | `gobuster dns -d domain.com -w wordlist.txt` |
| VHost-skanning | `gobuster vhost -u URL -w wordlist.txt` |
| Med utdata | `gobuster dir -u URL -w wordlist.txt -o output.txt` |
| Hopp over SSL-feil | `gobuster dir -u URL -w wordlist.txt -k` |
| Med informasjonskapsler | `gobuster dir -u URL -w wordlist.txt -c "session=x"` |
| Gjennom proxy | `gobuster dir -u URL -w wordlist.txt --proxy http://127.0.0.1:8080` |

### Vanlige valg

| Valg | Kortform | Beskrivelse |
|--------|-------|-------------|
| `--url` | `-u` | Mål-URL |
| `--wordlist` | `-w` | Ordlistefil |
| `--threads` | `-t` | Antall tråder |
| `--extensions` | `-x` | Filendelser |
| `--status-codes` | `-s` | Statuskoder som skal tas med |
| `--cookies` | `-c` | Informasjonskapsler som skal sendes |
| `--output` | `-o` | Utdatafil |
| `--insecuressl` | `-k` | Hopp over sertifikatkontroll |
| `--verbose` | `-v` | Detaljert utdata |
| `--quiet` | `-q` | Stille modus |

---

## 💡 Tips og anbefalte arbeidsmåter

### Effektivitetstips

1. **Start smått og utvid**
   ```bash
   # Rask skanning først
   gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt
   
   # Deretter en større ordliste
   gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
   ```

2. **Tilpass filendelsene til målet**
   ```bash
   # PHP-nettsted
   gobuster dir -u http://target.com -w wordlist.txt -x php
   
   # ASP.NET-nettsted
   gobuster dir -u http://target.com -w wordlist.txt -x asp,aspx
   ```

3. **Kombiner med andre verktøy**
   ```bash
   # Arbeidsflyt fra Nmap til Gobuster
   nmap -sV -p 80,443 target.com
   gobuster dir -u http://target.com -w wordlist.txt
   ```

### Unngå deteksjon

```bash
# Langsom skanning med forsinkelse
gobuster dir -u http://target.com -w wordlist.txt -t 5 --delay 200ms

# Egendefinert User-Agent
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0..."

# Gjennom Tor
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050
```

---

## ⚠️ Juridisk ansvarsfraskrivelse

> **ADVARSEL:** Gobuster er et kraftig kartleggingsverktøy som bare skal brukes til **autorisert testing**.
> 
> - ✅ Test på systemer du eier
> - ✅ Test med uttrykkelig skriftlig tillatelse
> - ✅ Bruk i CTF-oppgaver og labmiljøer
> - ❌ Skann aldri uten tillatelse
> - ❌ Uautorisert kartlegging kan være ulovlig
> 
> **Innhent alltid tillatelse før du skanner et system.**

---

## 📚 Ressurser

### Offisielle ressurser
- [Gobuster på GitHub](https://github.com/OJ/gobuster)
- [Gobuster-wiki](https://github.com/OJ/gobuster/wiki)

### Ordlister
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)

### Relaterte hurtigreferanser
- [Nmap](../../Nmap/translations/README.nb.md)
- [Burp Suite](../../Burp-Suite/translations/README.nb.md)
- [SQLMap](../../SQLMap/translations/README.nb.md)

---

<p align="center">
  <b>🚀 Lær webkartlegging!</b><br>
  <i>Finn det som ikke er synlig ved første øyekast</i>
</p>
