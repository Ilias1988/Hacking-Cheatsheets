# 🔍 OSINT (Open Source Intelligence) – hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
 ██████╗ ███████╗██╗███╗   ██╗████████╗
██╔═══██╗██╔════╝██║████╗  ██║╚══██╔══╝
██║   ██║███████╗██║██╔██╗ ██║   ██║   
██║   ██║╚════██║██║██║╚██╗██║   ██║   
╚██████╔╝███████║██║██║ ╚████║   ██║   
 ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝   ╚═╝   
```

> **OSINT (Open Source Intelligence)** – innsamling og analyse av offentlig tilgjengelig informasjon, altså etterretning fra åpne kilder.

---

## 📑 Innholdsfortegnelse

| Tema | Beskrivelse | Veiledning |
|-------|-------------|-------|
| **Personsøk** | Finn opplysninger om enkeltpersoner | [📄 Les](./People-Search.nb.md) |
| **E-post-OSINT** | Kartlegging og kontroll av e-postadresser | [📄 Les](./Email-OSINT.nb.md) |
| **Sosiale medier** | Undersøkelser i sosiale medier | [📄 Les](./Social-Media-OSINT.nb.md) |
| **Domener og IP-adresser** | Kartlegging av domener og IP-adresser | [📄 Les](./Domain-IP-OSINT.nb.md) |
| **Bilde-OSINT** | Omvendt bildesøk og bildeanalyse | [📄 Les](./Image-OSINT.nb.md) |

---

## 🎯 OSINT-metodikk

```
┌─────────────────────────────────────────────────────────────┐
│                         OSINT-FORLØP                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│        1. BEHOV          →  Avklar informasjonsbehov        │
│         ↓                                                   │
│         2. INNSAMLING     →  Samle rådata fra kilder        │
│         ↓                                                   │
│           3. BEHANDLING     →  Gjør data brukbare           │
│         ↓                                                   │
│        4. ANALYSE        →  Vurder og sammenhold funn       │
│         ↓                                                   │
│       5. FORMIDLING     →  Rapporter og presenter funn      │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Viktige OSINT-verktøy

> **Presisering:** Verktøy, priser, datakilder og dekning endres. Tabellene gjengir originalens oversikt, ikke en garanti for dagens tilgjengelighet. Kartlegging (reconnaissance/enumeration) betyr systematisk innsamling av opplysninger; OPSEC handler om å begrense egne spor og utilsiktet eksponering.

### Søkemotorer
| Verktøy | URL | Formål |
|------|-----|---------|
| **Google** | https://google.com | Generelle søk |
| **Bing** | bing.com | Alternativ søkemotor |
| **DuckDuckGo** | duckduckgo.com | Fokus på personvern |
| **Yandex** | yandex.com | Russisk søkemotor (nyttig for bilder) |
| **Baidu** | baidu.com | Kinesisk søkemotor |

### Personsøk
| Verktøy | URL | Formål |
|------|-----|---------|
| **Pipl** | pipl.com | Omfattende personsøk |
| **Spokeo** | spokeo.com | Personsøk i USA |
| **WhitePages** | whitepages.com | Oppslag på telefonnummer og adresse |
| **ThatsThem** | thatsthem.com | Gratis personsøk |
| **BeenVerified** | beenverified.com | Bakgrunnssjekker |

### E-postverktøy
| Verktøy | URL | Formål |
|------|-----|---------|
| **Hunter.io** | hunter.io | Finn virksomheters e-postadresser |
| **Have I Been Pwned** | haveibeenpwned.com | Sjekk datalekkasjer |
| **EmailRep** | emailrep.io | Omdømme for e-postadresser |
| **Phonebook.cz** | phonebook.cz | Søk etter e-postadresser og domener |
| **h8mail** | GitHub | Søk etter påloggingsopplysninger i datalekkasjer |

### Sosiale medier
| Verktøy | URL | Formål |
|------|-----|---------|
| **Sherlock** | GitHub | Søk etter brukernavn |
| **Social Searcher** | social-searcher.com | Søk i sosiale medier |
| **Namechk** | namechk.com | Tilgjengelighet for brukernavn |
| **KnowEm** | knowem.com | Kontroll av brukernavn |
| **Xquik** | https://github.com/Xquik-dev/x-twitter-scraper | Uavhengig data-API for X (Twitter), med søk, eksport av følgere, overvåking og MCP. |

### Verktøy for domener og IP-adresser
| Verktøy | URL | Formål |
|------|-----|---------|
| **Shodan** | shodan.io | Søk etter enheter |
| **Censys** | censys.io | Kartlegging av Internett |
| **SecurityTrails** | securitytrails.com | DNS-historikk |
| **ViewDNS** | viewdns.info | DNS-verktøy |
| **BuiltWith** | builtwith.com | Kartlegging av teknologi |

---

## 🔥 Kommandoer som hurtigreferanse

### theHarvester
```bash
# Innsamling av e-postadresser og underdomener
theHarvester -d target.com -l 500 -b all

# Bestemte kilder
theHarvester -d target.com -b google,linkedin,twitter

# Lagre i fil
theHarvester -d target.com -b all -f results.html
```

### Sherlock (søk etter brukernavn)

> **Versjonsmerknad:** Sherlock-eksemplene med `sherlock.py` eller `python3 sherlock` gjenspeiler eldre katalogoppsett. Prosjektets nåværende dokumentasjon bruker kommandoen `sherlock`; tilgjengelige nettsteder og bruk av `--site` avhenger av versjonen. Se [Sherlock-prosjektet](https://github.com/sherlock-project/sherlock).
```bash
# Søk på alle plattformer
python3 sherlock.py username

# Bestemte nettsteder
python3 sherlock.py username --site twitter instagram facebook

# Skriv resultatet til fil
python3 sherlock.py username --output results.txt
```

### Maltego
```
# Typer transformasjoner:
- Fra person til e-postadresse
- Fra e-postadresse til domene
- Fra domene til IP-adresse
- Fra IP-adresse til geografisk sted
- Profiler i sosiale medier
```

### Recon-ng
```bash
# Start recon-ng
recon-ng

# Opprett arbeidsområde
workspaces create target_name

# Last inn moduler
modules load recon/domains-hosts/hackertarget
modules load recon/contacts-credentials/hibp_breach

# Angi mål
options set SOURCE target.com

# Kjør modulen
run
```

---

## 📊 OSINT-kategorier

### Passiv OSINT
```
Ingen direkte samhandling med målet:
- Søkemotorer
- Sosiale medier
- Offentlige registre
- Archive.org
- WHOIS-data
```

### Aktiv OSINT

> **Presisering:** Originalen bruker en vid inndeling. Portskanning og enkelte bekreftelsestester innebærer aktiv teknisk rekognosering, og er ikke passiv innsamling fra åpne kilder. DNS-trafikk kan gå til mellomliggende resolvere; hvem som ser trafikken avhenger av oppsettet.
```
Noe samhandling med målet:
- Portskanning
- DNS-spørringer
- Gjennomgang av nettsteder (web crawling)
- Kontroll av e-postadresser
```

---

## 🔒 Operasjonssikkerhet (OPSEC)

### Anbefalte arbeidsmåter
```
✓ Bruk VPN/Tor
✓ Opprett separate dekkontoer (sock puppets)
✓ Bruk virtuelle maskiner
✓ Ikke bruk private kontoer
✓ Tøm informasjonskapsler og hurtigbuffer regelmessig
✓ Bruk separate nettleserprofiler
✓ Dokumenter alt
```

### Dekkontoer (sock puppets)
```
- Bruk konstruerte identiteter
- Bruk en konsekvent rolle på tvers av plattformer
- Eldre kontoer (oppfattes som mer troverdige)
- Bruk troverdige profilopplysninger
- Ikke knytt kontoen til den virkelige identiteten
```

---

## 📋 OSINT-sjekkliste

```markdown
## Mål: [Name/Company/Domain]

### Personer
□ Varianter av fullt navn
□ Telefonnumre
□ E-postadresser
□ Fysiske adresser
□ Profiler i sosiale medier
□ Yrkesbakgrunn
□ Utdanningsbakgrunn
□ Fotografier og bilder

### Virksomhet
□ Domeneregistrering (WHOIS)
□ Teknologistakk
□ Ansattliste
□ E-postformat
□ Tilstedeværelse i sosiale medier
□ Nyheter og pressemeldinger
□ Økonomiske opplysninger
□ Fysiske lokasjoner

### Teknisk
□ IP-adresser
□ Underdomener
□ Åpne porter og tjenester
□ DNS-oppføringer
□ SSL-sertifikater
□ Historiske data
```

---

## 🔗 OSINT-rammeverk

### OSINT Framework
```
https://osintframework.com/
- Organisert etter kategori
- Lenker til hundrevis av verktøy
- Visuell trestruktur
```

### IntelTechniques
```
https://inteltechniques.com/tools/
- Verktøy fra Michael Bazzell
- Tilpassede søkeverktøy
- Arbeidsflyter for undersøkelser
```

---

## 📚 Relaterte veiledninger i dette repoet

- [Google Dorking](../../Google-Dorking/README.md)
- [GitHub Dorking](../../GitHub-Dorking/README.md)
- [Shodan](../../Shodan/README.md)
- [Amass](../../Amass/README.md)
- [Subfinder](../../Subfinder/README.md)

---

**Tilbake til hovedoversikten:** [🔴 Hacking Cheatsheets](../../README.md)
