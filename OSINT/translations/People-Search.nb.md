# 👤 OSINT for personsøk

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="People-Search.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="People-Search.el.md">🇬🇷 Ελληνικά</a>
</p>

```
██████╗ ███████╗ ██████╗ ██████╗ ██╗     ███████╗
██╔══██╗██╔════╝██╔═══██╗██╔══██╗██║     ██╔════╝
██████╔╝█████╗  ██║   ██║██████╔╝██║     █████╗  
██╔═══╝ ██╔══╝  ██║   ██║██╔═══╝ ██║     ██╔══╝  
██║     ███████╗╚██████╔╝██║     ███████╗███████╗
╚═╝     ╚══════╝ ╚═════╝ ╚═╝     ╚══════╝╚══════╝
███████╗███████╗ █████╗ ██████╗  ██████╗██╗  ██╗ 
██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝██║  ██║ 
███████╗█████╗  ███████║██████╔╝██║     ███████║ 
╚════██║██╔══╝  ██╔══██║██╔══██╗██║     ██╔══██║ 
███████║███████╗██║  ██║██║  ██║╚██████╗██║  ██║ 
╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝ 
```

---

## 🔍 Søketjenester for personer

### Gratis tjenester

> **Merknad:** Inndelingen i gratis- og betalingstjenester er fra originalen. Tilgang, pris og geografisk dekning må kontrolleres hos leverandøren; et navne- eller brukernavntreff er ikke alene bekreftet identitet.
| Verktøy | URL | Dekning |
|------|-----|----------|
| **ThatsThem** | thatsthem.com | USA |
| **FastPeopleSearch** | fastpeoplesearch.com | USA |
| **TruePeopleSearch** | truepeoplesearch.com | USA |
| **Webmii** | webmii.com | Global |
| **PeekYou** | peekyou.com | USA |
| **Pipl** | pipl.com | Global |

### Betalingstjenester
| Verktøy | URL | Funksjoner |
|------|-----|----------|
| **Spokeo** | spokeo.com | Omfattende søk og sosiale medier |
| **BeenVerified** | beenverified.com | Bakgrunnssjekker |
| **Intelius** | intelius.com | Strafferegisteropplysninger |
| **US Search** | ussearch.com | Rettsdokumenter |
| **Radaris** | radaris.com | Eiendomsopplysninger |

---

## 🔎 Målrettede Google-søk etter personer (Google dorking)

### Grunnleggende søk
```
# Søk på navn
"John Smith" site:linkedin.com
"John Smith" site:facebook.com
"John Smith" site:twitter.com

# Navn og sted
"John Smith" "New York"
"John Smith" "NYC" OR "New York City"

# Navn og virksomhet
"John Smith" "Microsoft"
"John Smith" site:microsoft.com

# Navn og e-postadresse
"John Smith" "@gmail.com"
"John Smith" email
```

### Yrkesopplysninger
```
# Søk etter CV-er
"John Smith" filetype:pdf resume
"John Smith" filetype:doc CV
intitle:"curriculum vitae" "John Smith"

# Søk spesielt på LinkedIn
site:linkedin.com/in "John Smith"
site:linkedin.com "John Smith" "Software Engineer"

# Yrkesprofiler
site:about.me "John Smith"
site:crunchbase.com "John Smith"
```

### Finne profiler i sosiale medier
```
# Finn alle profiler
"John Smith" (site:facebook.com OR site:twitter.com OR site:instagram.com)

# Søk på brukernavn
"johnsmith123" site:twitter.com
"johnsmith123" site:reddit.com
"johnsmith123" site:github.com
```

---

## 🛠️ Kommandolinjeverktøy

### Sherlock (søk etter brukernavn)

> **Versjonsmerknad:** Installasjonen og startkommandoene nedenfor er bevart fra originalen og kan vise et eldre katalogoppsett. Se [Sherlocks egen installasjons- og bruksveiledning](https://github.com/sherlock-project/sherlock).
```bash
# Installer
git clone https://github.com/sherlock-project/sherlock.git
cd sherlock
pip3 install -r requirements.txt

# Søk etter brukernavnet på tvers av plattformer
python3 sherlock username

# Flere brukernavn
python3 sherlock user1 user2 user3

# Bare bestemte nettsteder
python3 sherlock username --site twitter instagram facebook

# Eksporter resultater
python3 sherlock username --output results.txt
python3 sherlock username --csv
python3 sherlock username --xlsx
```

### Maigret (utvidet søk etter brukernavn)
```bash
# Installer
pip3 install maigret

# Søk
maigret username

# Med Tor
maigret username --tor

# Resultat i JSON
maigret username --json results.json
```

### holehe (kontroll av registrerte e-postadresser)
```bash
# Installer
pip3 install holehe

# Kontroller e-postadressen på tvers av tjenester
holehe email@example.com

# Vis bare nettsteder der adressen er registrert
holehe email@example.com --only-used
```

---

## 📧 Fra e-postadresse til person

### Finne personen bak en e-postadresse
```
1. Søk etter e-postadressen i Google: "email@example.com"
2. Sjekk HIBP: haveibeenpwned.com
3. Søk i databaser over datalekkasjer
4. Kontroller registreringer i sosiale medier
5. Bruk tjenester for omvendt e-postoppslag
```

### Finne e-postformatet
```bash
# Vanlige e-postformater
first.last@company.com
flast@company.com
firstl@company.com
first_last@company.com
first@company.com

# Finn adresseformat med Hunter.io
https://hunter.io/email-finder
```

---

## 📱 OSINT for telefonnumre

### Oppslagstjenester for telefonnumre
| Tjeneste | URL | Type |
|---------|-----|------|
| **TrueCaller** | truecaller.com | Identifikasjon av innringer |
| **Sync.me** | sync.me | Identifikasjon av innringer |
| **NumLookup** | numlookup.com | Gratis oppslag |
| **SpyDialer** | spydialer.com | Omvendt oppslag |
| **CallerID Test** | calleridtest.com | Operatøropplysninger |

### Målrettede Google-søk etter telefonnumre
```
# Grunnleggende søk
"555-123-4567"
"+1 555 123 4567"
"5551234567"

# Finn tilknyttede opplysninger
"555-123-4567" site:facebook.com
"555-123-4567" "@"
"555-123-4567" "email"
```

### OSINT om telefonnumre

> **Versjonsmerknad:** `pip3 install phoneinfoga` samsvarer ikke med den dokumenterte installasjonen av PhoneInfoga v2. Prosjektet beskriver binærfiler, containere eller bygging fra kildekode; originalkommandoen er bevart. Se [PhoneInfogas installasjonsveiledning](https://sundowndev.github.io/phoneinfoga/getting-started/install/).
```bash
# phoneinfoga
pip3 install phoneinfoga
phoneinfoga scan -n "+1234567890"
phoneinfoga serve -p 8080  # Webgrensesnitt

# Med API-er
curl "https://api.numlookupapi.com/v1/validate/14155551234?apikey=YOUR_KEY"
```

---

## 🏠 Adresse- og eiendomsopplysninger

### Eiendomssøk
| Tjeneste | URL | Dekning |
|---------|-----|----------|
| **Zillow** | zillow.com | USA |
| **Redfin** | redfin.com | USA |
| **County Assessor** | [varies] | Lokale registre |
| **Whitepages** | whitepages.com | USA |

### Offentlige registre
```
# Rettsdokumenter
PACER (pacer.gov) - Føderale domstoler
Nettstedene til delstatsdomstolene - Delstatsdomstoler

# Velgerregistre
Databaser over velgerregistrering (varierer mellom delstatene)

# Foretaksregistrering
Nettstedene til delstatenes Secretary of State
```

---

## 👥 Analyse av sosiale nettverk

### OSINT på LinkedIn
```
# Søk etter profiler
site:linkedin.com/in "John Smith"
site:linkedin.com/in "John Smith" "Company Name"

# Finn forbindelser
Se «People Also Viewed»
Se etter felles forbindelser
Virksomhetens ansattliste
```

### OSINT på Facebook
```
# Profil-URL-er
facebook.com/search/people/?q=john%20smith
facebook.com/search/pages/?q=john%20smith

# Graph Search (nå begrenset)
People named "John" who work at "Company"
People who live in "City" and like "Page"
```

### Verktøy for analyse av sosiale nettverk

> **Versjonsmerknad:** [Twint-repoet](https://github.com/twintproject/twint) ble arkivert 30. mars 2023. Eksemplene er historiske og gir ingen garanti for at de fungerer mot dagens X. Graph Search-formuleringene ovenfor er også eldre eksempler, ikke en garanti for tilgjengelige Facebook-søk.
```bash
# Social Analyzer
pip3 install social-analyzer
social-analyzer --username "target" --metadata

# Twint (Twitter)
pip3 install twint
twint -u username --followers
twint -u username --following
```

---

## 📸 Søk etter bilder og ansikter

### Omvendt bildesøk
| Tjeneste | URL | Best egnet til |
|---------|-----|----------|
| **Google Images** | images.google.com | Generelle søk |
| **TinEye** | tineye.com | Nøyaktige treff |
| **Yandex** | yandex.com/images | Særlig ansikter, ifølge originalen |
| **Bing Images** | bing.com/images | Et godt alternativ |
| **PimEyes** | pimeyes.com | Ansiktsgjenkjenning |

### Ansiktssøk

> **Presisering:** Rangeringen «best» er originalens vurdering, ikke en dokumentert sammenlignende test. Et visuelt treff eller et treff fra ansiktsgjenkjenning må kontrolleres mot uavhengige opplysninger.
```
1. Yandex Images - Best til å finne ansikter, ifølge originalen
2. PimEyes - Betalt ansiktsgjenkjenning
3. Social Catfish - Omvendt bildesøk for sosiale profiler
```

---

## 📋 Sjekkliste for personsøk

```markdown
## Mål: [Full Name]

### Grunnleggende opplysninger
□ Fullt juridisk navn
□ Fødselsdato
□ Nåværende adresse
□ Telefonnumre
□ E-postadresser

### Tilstedeværelse på nettet
□ LinkedIn-profil
□ Facebook-profil
□ Twitter/X-konto
□ Instagram
□ TikTok
□ Andre sosiale medier
□ Personlig nettsted eller blogg

### Yrkesopplysninger
□ Nåværende arbeidsgiver
□ Stillingstittel
□ Arbeidshistorikk
□ Utdanning
□ Yrkesautorisasjoner
□ Publikasjoner og patenter

### Registeropplysninger
□ Eiendomsopplysninger
□ Rettsdokumenter
□ Foretaksregistreringer
□ Velgerregistrering
□ Opplysninger om ekteskap og skilsmisse

### Forbindelser
□ Familiemedlemmer
□ Bekjente og andre forbindelser
□ Forretningspartnere
□ Sosiale forbindelser
```

---

## ⚠️ Juridiske og etiske hensyn

```
✓ Bruk bare offentlig tilgjengelige opplysninger
✓ Respekter personvernregler (GDPR, CCPA)
✓ Ikke utgi deg for å være andre eller villed noen
✓ Dokumenter metoden din
✓ Bruk opplysningene bare til legitime formål
✗ Ikke forfølg eller trakasser noen
✗ Ikke gå inn på private kontoer
✗ Ikke del funn offentlig uten samtykke
```

---

**Tilbake til OSINT:** [🔍 OSINT-oversikt](./README.nb.md)
