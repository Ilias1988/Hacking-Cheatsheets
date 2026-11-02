# 🕷️ Nikto – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   _ _ _    _        
   | \ | (_) | _| |_ ___  
   |  \| | | |/ / __/ _ \ 
   | |\  | |   <| || (_) |
   |_| \_|_|_|\_\\__\___/ 
                          
   Webserverskanner – siden 2001
```

<p align="center">
  <img src="https://img.shields.io/badge/Nikto-Web_Scanner-blue?style=for-the-badge" alt="Nikto">
  <img src="https://img.shields.io/badge/Vulnerability-Scanner-red?style=for-the-badge" alt="Vulnerability">
  <img src="https://img.shields.io/badge/CGI-Testing-green?style=for-the-badge" alt="CGI">
  <img src="https://img.shields.io/badge/SSL-Analysis-purple?style=for-the-badge" alt="SSL">
</p>

<p align="center">
  <b>🌐 Webserverskanner med åpen kildekode for sikkerhetstesting</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Grunnleggende syntaks](#-grunnleggende-syntaks)
- [Angivelse av mål](#-angivelse-av-mål)
- [Port- og SSL-valg](#-port--og-ssl-valg)
- [Tilpasning av skanning](#-tilpasning-av-skanning)
- [Autentisering](#-autentisering)
- [Proxy og omgåelse](#-proxy-og-omgåelse)
- [Utdata og rapportering](#-utdata-og-rapportering)
- [Databaseoppdateringer](#-databaseoppdateringer)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

> **Begreper og versjoner:** Et skannerfunn er et undersøkelsesspor som må bekreftes, ikke alene bevis på en sårbarhet. CGI er et grensesnitt for serverskript; IDS er et system for innbruddsdeteksjon. Databasetallene og eksempelverdiene nedenfor er fra originalen. Den gamle `-update`-flyten og `2.1.5`-nedlastingene er historiske; se [prosjektets oppdateringsveiledning](https://github.com/sullo/nikto/wiki/Updating). `-Save` lagrer funn/forespørsler/responser i en katalog, ikke en generell gjenopptakbar skannetilstand. Originalens `-Resume`-eksempel er ikke dokumentert i den gjeldende [valglisten](https://github.com/sullo/nikto/wiki/Annotated-Option-List); se også [lagring og avspilling](https://github.com/sullo/nikto/wiki/Saving-Requests-Responses-%26-Replaying). Originalkommandoene er bevart. Anførselstegn rundt `admin:password` Base64-koder ikke verdien; cookie- og autentiseringsvalg må kontrolleres mot versjonens dokumentasjon. Omgåelsesflagg eller forsinkelse garanterer ikke skjult trafikk.

**Nikto** er en webserverskanner med åpen kildekode som gjennomfører omfattende tester av webservere, blant annet:

### Hva Nikto undersøker

| Kategori | Beskrivelse |
|----------|-------------|
| **Farlige filer** | Filer som kan være risikable (sikkerhetskopier osv.) |
| **Utdaterte versjoner** | Utdatert serverprogramvare |
| **Versjonsspesifikt** | Versjonsspesifikke sårbarheter |
| **Serverkonfigurasjon** | Feilkonfigurasjoner |
| **CGI-skript** | Potensielt farlige CGI-skript |
| **SSL/TLS** | Problemer med SSL-sertifikater |
| **Headere** | Manglende eller usikre HTTP-headere |
| **Metoder** | Farlige HTTP-metoder |

### Databasestatistikk

```
✅ 6,700+ potensielt farlige filer/CGI-er
✅ 1,250+ utdaterte serverversjoner
✅ 270+ versjonsspesifikke problemer
✅ 1,000+ problemer med serverkonfigurasjon
```

### Egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **Omfattende** | Tester for tusenvis av sårbarheter |
| **Basert på programtillegg** | Utvidbar arkitektur |
| **Flere formater** | Utdata i HTML, XML, CSV og JSON |
| **SSL-støtte** | Full HTTPS-testing |
| **Omgåelse av IDS** | Innebygde omgåelsesteknikker |
| **Autentisering** | Støtte for Basic, Digest og NTLM |

---

## 📥 Installasjon

### Kali Linux (forhåndsinstallert)
```bash
# Oppdater Nikto
sudo apt update && sudo apt install nikto

# Oppdater databasen
nikto -update
```

### Ubuntu/Debian
```bash
sudo apt install nikto
```

### Fra GitHub
```bash
git clone https://github.com/sullo/nikto
cd nikto/program
perl nikto.pl -h
```

### macOS (Homebrew)
```bash
brew install nikto
```

### Kontroller installasjonen
```bash
nikto -Version
nikto -h          # Hjelp
```

---

<a id="-grunnleggende-syntaks"></a>
## ⌨️ Grunnleggende syntaks

### Generell syntaks
```bash
nikto -h <host> [options]
```

### Kom raskt i gang
```bash
# Grunnleggende skanning
nikto -h http://target.com

# Skann med angitt port
nikto -h target.com -p 80

# Skann HTTPS
nikto -h https://target.com

# Skann en IP-adresse
nikto -h 192.168.1.1
```

---

## 🎯 Angivelse av mål

### Ett mål
```bash
# Etter vertsnavn
nikto -h target.com

# Etter IP-adresse
nikto -h 192.168.1.1

# Med protokoll
nikto -h http://target.com
nikto -h https://target.com
```

### Flere mål
```bash
# Fra fil
nikto -h targets.txt

# targets.txt:
# http://target1.com
# http://target2.com:8080
# https://target3.com
```

### Mål med sti
```bash
# Skann en bestemt sti
nikto -h http://target.com/app

# Skann en bestemt katalog
nikto -h http://target.com/admin/
```

---

## 🔌 Port- og SSL-valg

### Portvalg
```bash
# Én port
nikto -h target.com -p 80

# Flere porter
nikto -h target.com -p 80,443,8080

# Portområde
nikto -h target.com -p 80-90

# Vanlige webporter
nikto -h target.com -p 80,443,8000,8080,8443
```

### SSL-/TLS-valg

| Valg | Beskrivelse |
|--------|-------------|
| `-ssl` | Tving SSL-modus |
| `-nossl` | Deaktiver SSL |
| `-maxtime` | Maksimal skannetid |

```bash
# Tving SSL
nikto -h target.com -ssl

# Deaktiver SSL (bare HTTP)
nikto -h target.com -nossl

# SSL på en uvanlig port
nikto -h target.com -p 8443 -ssl
```

### Virtuelle verter
```bash
# Angi virtuell vert
nikto -h 192.168.1.1 -vhost target.com

# Nyttig når:
# - Målet ligger bak en lastbalanserer
# - Flere nettsteder bruker samme IP-adresse
```

---

<a id="-tilpasning-av-skanning"></a>
## 🎚️ Tilpasning av skanning

### Tilpasningsvalg (-Tuning)

| Kode | Beskrivelse |
|------|-------------|
| `0` | Filopplasting |
| `1` | Interessant fil / observert i logger |
| `2` | Feilkonfigurasjon / standardfil |
| `3` | Informasjonslekkasje |
| `4` | Injeksjon (XSS/skript/HTML) |
| `5` | Ekstern filhenting – innenfor webroten |
| `6` | Tjenestenekt |
| `7` | Ekstern filhenting – hele serveren |
| `8` | Kommandokjøring / eksternt shell |
| `9` | SQL-injeksjon |
| `a` | Omgåelse av autentisering |
| `b` | Identifikasjon av programvare |
| `c` | Inkludering av ekstern kildekode |
| `d` | Webtjeneste |
| `e` | Administrasjonskonsoll |
| `x` | Inverter tilpasningsvalgene |

### Bruk av tilpasning
```bash
# Test bare SQL-injeksjon
nikto -h target.com -Tuning 9

# Test XSS og SQL-injeksjon
nikto -h target.com -Tuning 49

# Test flere typer
nikto -h target.com -Tuning 1234

# Utelat bestemte tester (prefikset x)
nikto -h target.com -Tuning x6    # Utelat tester for tjenestenekt

# Alt unntatt tjenestenekt
nikto -h target.com -Tuning x6
```

### Programtillegg
```bash
# List tilgjengelige programtillegg
nikto -list-plugins

# Kjør et bestemt programtillegg
nikto -h target.com -Plugins apacheusers

# Kjør flere programtillegg
nikto -h target.com -Plugins "apacheusers;robots"

# Utelat programtillegg
nikto -h target.com -Plugins "@@ALL;-robots"
```

### Vanlige programtillegg

| Programtillegg | Beskrivelse |
|--------|-------------|
| `apacheusers` | Kartlegging av Apache-brukere |
| `cgi` | Testing av CGI-skript |
| `robots` | Analyse av robots.txt |
| `headers` | Analyse av HTTP-headere |
| `httpoptions` | Testing av HTTP-metoder |
| `outdated` | Kontroll av utdatert programvare |
| `ssl` | SSL-/TLS-analyse |
| `dictionary` | Ordlisteangrep |

---

## 🔐 Autentisering

### Basic-autentisering
```bash
# Brukernavn og passord
nikto -h target.com -id admin:password

# Base64-format
nikto -h target.com -id "admin:password"
```

### Digest-autentisering
```bash
# Digest-autentisering (automatisk identifisert)
nikto -h target.com -id admin:password
```

### NTLM-autentisering
```bash
# NTLM-autentisering
nikto -h target.com -id admin:password:domain
```

### Autentisering med informasjonskapsler
```bash
# Send informasjonskapsler
nikto -h target.com -cookies "session=abc123; token=xyz789"

# Fra fil
nikto -h target.com -cookies cookies.txt
```

---

<a id="-proxy-og-omgåelse"></a>
## 🛡️ Proxy og omgåelse

### Proxystøtte
```bash
# HTTP-proxy
nikto -h target.com -useproxy http://127.0.0.1:8080

# Proxy med autentisering
nikto -h target.com -useproxy http://user:pass@proxy:8080

# Gjennom Burp Suite
nikto -h target.com -useproxy http://127.0.0.1:8080
```

### Omgåelse av IDS (-evasion)

| Kode | Teknikk |
|------|-----------|
| `1` | Tilfeldig URI-koding (ikke UTF8) |
| `2` | Selvreferanse i katalogsti (/./) |
| `3` | For tidlig avslutning av URL |
| `4` | Sett inn en lang tilfeldig streng først |
| `5` | Falsk parameter |
| `6` | TAB som mellomrom i forespørselen |
| `7` | Endre store og små bokstaver i URL-en |
| `8` | Bruk Windows-katalogskilletegn (\) |
| `A` | Bruk vognretur (0x0d) |
| `B` | Bruk binærverdien 0x0b |

```bash
# Én omgåelsesteknikk
nikto -h target.com -evasion 1

# Flere omgåelsesteknikker
nikto -h target.com -evasion 1234

# Alle omgåelsesteknikker
nikto -h target.com -evasion 12345678AB
```

### User-Agent
```bash
# Egendefinert User-Agent
nikto -h target.com -useragent "Mozilla/5.0 (Windows NT 10.0)"

# Googlebot
nikto -h target.com -useragent "Googlebot/2.1"
```

### Tidsstyring av forespørsler
```bash
# Pause mellom forespørsler (sekunder)
nikto -h target.com -Pause 2

# Maksimal skannetid (sekunder)
nikto -h target.com -maxtime 3600

# Tidsavbrudd per forespørsel
nikto -h target.com -timeout 10
```

---

## 📄 Utdata og rapportering

### Utdataformater

| Valg | Format |
|--------|--------|
| `-o file` | Skriv utdata til fil |
| `-Format htm` | HTML-format |
| `-Format xml` | XML-format |
| `-Format csv` | CSV-format |
| `-Format txt` | Tekstformat |
| `-Format json` | JSON-format |
| `-Format nbe` | NBE-format |
| `-Format sql` | SQL-format |

### Lagre resultater
```bash
# Tekstutdata
nikto -h target.com -o report.txt

# HTML-rapport
nikto -h target.com -o report.html -Format htm

# XML-rapport
nikto -h target.com -o report.xml -Format xml

# JSON-rapport
nikto -h target.com -o report.json -Format json

# CSV-rapport
nikto -h target.com -o report.csv -Format csv
```

### Valg for detaljnivå
```bash
# Visningsvalg
nikto -h target.com -Display V    # Detaljert
nikto -h target.com -Display D    # Feilsøking
nikto -h target.com -Display E    # HTTP-feil
nikto -h target.com -Display P    # Fremdrift
nikto -h target.com -Display S    # Fjern IP-adresser fra utdata
nikto -h target.com -Display 1    # Vis omdirigeringer
nikto -h target.com -Display 2    # Vis informasjonskapsler
nikto -h target.com -Display 3    # Vis 200/OK-responser
nikto -h target.com -Display 4    # Vis URL-er som krever autentisering

# Flere visningsvalg
nikto -h target.com -Display VEP
```

### Lagre fremdrift
```bash
# Lagre skannetilstand (kan gjenopptas senere)
nikto -h target.com -Save scan_state.txt

# Gjenoppta skanning
nikto -Resume scan_state.txt
```

---

## 🔄 Databaseoppdateringer

### Oppdater Nikto-databasen
```bash
# Oppdater alle databaser
nikto -update

# Manuell oppdatering (hvis -update mislykkes)
cd /var/lib/nikto
wget https://cirt.net/nikto/UPDATES/2.1.5/db_outdated.csv
wget https://cirt.net/nikto/UPDATES/2.1.5/db_tests.csv
```

### Databaseplassering
```bash
# Standardplasseringer
/usr/share/nikto/databases/
/var/lib/nikto/

# Databasefiler
db_tests         # Hoveddatabase for tester
db_outdated      # Utdaterte programvareversjoner
db_server_msgs   # Servermeldinger
db_variables     # Variabler
db_404_strings   # Strenger fra 404-sider
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: Grunnleggende webserverskanning
```bash
nikto -h http://target.com
```

### Eksempel 2: Omfattende HTTPS-skanning
```bash
nikto -h https://target.com -ssl -p 443 -o report.html -Format htm
```

### Eksempel 3: Skanning av flere porter
```bash
nikto -h target.com -p 80,443,8080,8443 -o results.txt
```

### Eksempel 4: Autentisert skanning
```bash
nikto -h http://target.com/admin \
    -id admin:password \
    -o auth_scan.html \
    -Format htm
```

### Eksempel 5: Stealth-skanning (omgåelse av IDS)
```bash
nikto -h target.com \
    -evasion 1234 \
    -Pause 2 \
    -useragent "Mozilla/5.0 (Windows NT 10.0)" \
    -o stealth_scan.txt
```

### Eksempel 6: Gjennom Burp-proxy
```bash
nikto -h https://target.com \
    -useproxy http://127.0.0.1:8080 \
    -o proxy_scan.html \
    -Format htm
```

### Eksempel 7: Vekt på SQL-injeksjon
```bash
nikto -h target.com \
    -Tuning 9 \
    -o sqli_scan.txt
```

### Eksempel 8: Full sikkerhetsrevisjon
```bash
nikto -h https://target.com \
    -ssl \
    -p 443 \
    -Tuning x6 \
    -Display VEP \
    -o full_audit.html \
    -Format htm
```

### Eksempel 9: Testing av virtuell vert
```bash
nikto -h 192.168.1.10 \
    -vhost app.target.com \
    -p 80,443 \
    -o vhost_scan.txt
```

### Eksempel 10: Skanning fra målfil
```bash
nikto -h targets.txt \
    -o batch_scan.html \
    -Format htm
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Oppgave | Kommando |
|------|---------|
| Grunnleggende skanning | `nikto -h target.com` |
| HTTPS-skanning | `nikto -h target.com -ssl` |
| Flere porter | `nikto -h target.com -p 80,443,8080` |
| Med autentisering | `nikto -h target.com -id user:pass` |
| HTML-rapport | `nikto -h target.com -o report.html -Format htm` |
| Omgåelse | `nikto -h target.com -evasion 123` |
| Gjennom proxy | `nikto -h target.com -useproxy http://127.0.0.1:8080` |
| Oppdater database | `nikto -update` |

### Vanlige valg

| Valg | Beskrivelse |
|--------|-------------|
| `-h` | Målvert |
| `-p` | Porter som skal skannes |
| `-ssl` | Tving SSL-modus |
| `-id` | Påloggingsopplysninger |
| `-o` | Utdatafil |
| `-Format` | Utdataformat |
| `-Tuning` | Tilpasning av skanning |
| `-evasion` | Omgåelse av IDS |
| `-useproxy` | Proxyserver |
| `-vhost` | Virtuell vert |
| `-Pause` | Forsinkelse mellom tester |
| `-timeout` | Tidsavbrudd for forespørsler |

### Hurtigreferanse for tilpasning

| Kode | Kategori |
|------|----------|
| 1-4 | Informasjon/feilkonfigurasjon |
| 4-5 | Injeksjon/henting |
| 6 | Tjenestenekt (unngå i produksjon) |
| 7-8 | Fjerntilgang |
| 9 | SQL-injeksjon |
| a | Omgåelse av autentisering |
| b | Identifikasjon av programvare |
| x | Utelat |

---

## 💡 Tips og anbefalte arbeidsmåter

### Ytelsestips

1. **Start med grunnleggende skanning**
   ```bash
   nikto -h target.com
   ```

2. **Utelat tester for tjenestenekt**
   ```bash
   nikto -h target.com -Tuning x6
   ```

3. **Bruk passende tidsavbrudd**
   ```bash
   nikto -h target.com -timeout 15 -Pause 1
   ```

### Tips for bedre nøyaktighet

1. **Oppdater databasen regelmessig**
   ```bash
   nikto -update
   ```

2. **Bruk detaljert modus**
   ```bash
   nikto -h target.com -Display VE
   ```

3. **Kombiner med andre verktøy**
   ```bash
   # Nmap først, deretter Nikto
   nmap -sV -p 80,443 target.com
   nikto -h target.com -p 80,443
   ```

### Vanlige problemer

| Problem | Løsning |
|-------|----------|
| Langsom skanning | Reduser `-Pause`, øk `-timeout` |
| SSL-feil | Angi `-ssl` uttrykkelig |
| Autentiseringsproblemer | Kontroller formatet for `-id` |
| Manglende resultater | Oppdater databasen og kontroller programtilleggene |

---

## ⚠️ Juridisk ansvarsfraskrivelse

> **ADVARSEL:** Nikto er en webserverskanner som bare skal brukes til **autorisert testing**.
> 
> - ✅ Skann servere du eier
> - ✅ Skann med uttrykkelig skriftlig tillatelse
> - ✅ Bruk i autoriserte penetrasjonstester
> - ❌ Skann aldri uten tillatelse
> - ❌ Uautorisert skanning er ulovlig
> 
> **Nikto kan generere betydelig trafikk og utløse sikkerhetsvarsler.**

---

## 📚 Ressurser

### Offisielle ressurser
- [Nikto på GitHub](https://github.com/sullo/nikto)
- [Nikto-dokumentasjon](https://cirt.net/Nikto2)
- [CIRT.net](https://cirt.net/)

### Læringsressurser
- [OWASPs testveiledning](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Relaterte hurtigreferanser
- [Nmap](../../Nmap/translations/README.nb.md)
- [Gobuster](../../Gobuster/translations/README.nb.md)
- [Burp Suite](../../Burp-Suite/translations/README.nb.md)

---

<p align="center">
  <b>🕷️ Lær webserverskanning!</b><br>
  <i>Finn sårbarheter før angriperne gjør det</i>
</p>
