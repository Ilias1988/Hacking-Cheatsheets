# 🎭 Sosial manipulering – hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
███████╗ ██████╗  ██████╗██╗ █████╗ ██╗         ███████╗███╗   ██╗ ██████╗ ██╗███╗   ██╗███████╗███████╗██████╗ ██╗███╗   ██╗ ██████╗ 
██╔════╝██╔═══██╗██╔════╝██║██╔══██╗██║         ██╔════╝████╗  ██║██╔════╝ ██║████╗  ██║██╔════╝██╔════╝██╔══██╗██║████╗  ██║██╔════╝ 
███████╗██║   ██║██║     ██║███████║██║         █████╗  ██╔██╗ ██║██║  ███╗██║██╔██╗ ██║█████╗  █████╗  ██████╔╝██║██╔██╗ ██║██║  ███╗
╚════██║██║   ██║██║     ██║██╔══██║██║         ██╔══╝  ██║╚██╗██║██║   ██║██║██║╚██╗██║██╔══╝  ██╔══╝  ██╔══██╗██║██║╚██╗██║██║   ██║
███████║╚██████╔╝╚██████╗██║██║  ██║███████╗    ███████╗██║ ╚████║╚██████╔╝██║██║ ╚████║███████╗███████╗██║  ██║██║██║ ╚████║╚██████╔╝
╚══════╝ ╚═════╝  ╚═════╝╚═╝╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝╚═╝  ╚═══╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Hva er sosial manipulering?

Sosial manipulering (social engineering) går ut på å påvirke mennesker til å avsløre fortrolig informasjon eller utføre handlinger som svekker sikkerheten. Metoden utnytter menneskelig psykologi fremfor tekniske sårbarheter.

**Grunnprinsipper:**
- 🎭 **Autoritet** – folk stoler på autoritetspersoner
- ⏰ **Tidspress** – dårlig tid reduserer evnen til kritisk vurdering
- 🤝 **Gjensidighet** – folk føler seg forpliktet til å gjengjelde tjenester
- 👥 **Sosial bekreftelse** – folk følger flertallet
- 😊 **Sympati** – folk etterkommer ønsker fra personer de liker
- 📉 **Knapphet** – begrenset tilgjengelighet øker interessen

---

## 📖 Veiledninger i sosial manipulering

| Tema | Beskrivelse | Veiledning |
|-------|-------------|-------|
| **Phishing (nettfisking)** | Teknikker og verktøy for e-post, SMS og telefon | [📄 Les](./Phishing.nb.md) |
| **Pretexting (oppdiktede påskudd)** | Utforme troverdige situasjoner og roller | [📄 Les](./Pretexting.nb.md) |

---

> **Begreper:** OSINT er innhenting fra åpne kilder. Rekognosering betyr å samle bakgrunnsinformasjon. Phishing er nettfisking; pretexting bruker en oppdiktet rolle eller begrunnelse.

## 🛠️ Viktige verktøy for sosial manipulering

| Verktøy | Formål | Type |
|------|---------|------|
| **GoPhish** | Rammeverk for phishingkampanjer | Phishing |
| **SET (Social Engineering Toolkit)** | Omfattende rammeverk for sosial manipulering | Flere bruksområder |
| **Evilginx2** | Phishing med en mellommann (MITM), for omgåelse av 2FA | Phishing |
| **King Phisher** | Plattform for phishingkampanjer | Phishing |
| **Modlishka** | Phishing via omvendt proxy (reverse proxy) | Phishing |
| **theHarvester** | Innsamling av e-postadresser og domener | Rekognosering |
| **LinkedIn-verktøy** | Kartlegging av ansatte | OSINT |
| **Maltego** | OSINT og kartlegging av forbindelser | Rekognosering |

> **Presisering:** «Omgåelse av 2FA» er ikke universelt. FIDO/WebAuthn binder autentiseringen til riktig nettsted og gir motstand mot phishing med en mellomliggende proxy. Se [CISAs veiledning](https://www.cisa.gov/sites/default/files/2023-01/fact-sheet-implementing-phishing-resistant-mfa-508c.pdf).

### Rask installasjon av verktøy

> **Versjonsmerknad:** Eksemplene inneholder en fast GoPhish-versjon (`v0.12.1`), mens andre kommandoer bruker `@latest`. De er bevart fra originalen og er ikke en bekreftelse på dagens installasjonsmåte.
```bash
# GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
./gophish

# SET (Social Engineering Toolkit)
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
python setoolkit

# Evilginx2
go install github.com/kgretzky/evilginx2@latest

# theHarvester
pip install theHarvester
theHarvester -d target.com -b all
```

---

## 📊 Angrepstyper innen sosial manipulering

| Angrepstype | Beskrivelse | Kanal |
|-------------|-------------|--------|
| **Phishing** | Villedende e-poster for å stjele påloggingsopplysninger | E-post |
| **Spear phishing** | Målrettet nettfisking mot bestemte personer | E-post |
| **Whaling** | Nettfisking rettet mot toppledere | E-post |
| **Vishing** | Sosial manipulering via tale eller telefon | Telefon |
| **Smishing** | Nettfisking via SMS | SMS |
| **Pretexting** | Oppdiktede situasjoner for å hente ut informasjon | Alle |
| **Baiting** | Utplassering av infiserte lagringsmedier, som USB-enheter | Fysisk |
| **Quid pro quo** | Tilby noe i bytte mot informasjon | Alle |
| **Tailgating** | Følge etter en person med adgang inn i et sikret område | Fysisk |
| **Watering hole** | Kompromittere nettsteder målpersonene besøker | Web |

---

## 🔗 Hurtigreferanse: indikatorer på phishing

### Faresignaler i e-post
```
□ Avsenderadressen samsvarer ikke med virksomhetens domene
□ Generelle hilsener («Kjære kunde»)
□ Tidspress eller trusler
□ Stavefeil eller grammatikkfeil
□ Mistenkelige lenker (hold pekeren over for å kontrollere)
□ Uventede vedlegg
□ Forespørsler om sensitive opplysninger
□ Tilbud som er for gode til å være sanne
```

### Analyse av URL-er
```bash
# Kontroller domenets alder
whois suspicious-domain.com

# Kontroller omdømmet
https://www.virustotal.com
https://urlscan.io

# Undersøk forkortede URL-er
https://checkshorturl.com
```

---

## 📚 Ressurser

- [OWASP om sosial manipulering](https://owasp.org/www-community/attacks/Social_Engineering)
- [MITRE ATT&CK – innledende tilgang](https://attack.mitre.org/tactics/TA0001/)
- [Phishing.org](https://www.phishing.org/)
- [Social Engineering Framework](https://www.social-engineer.org/)

---

<p align="center">
  <b>🎭 Bruk sosial manipulering bare ved autorisert testing!</b>
</p>
