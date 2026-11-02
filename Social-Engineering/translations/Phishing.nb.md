# 🎣 Phishing – hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Phishing.tr.md) • [🇬🇷 Ελληνικά](Phishing.el.md)

```
██████╗ ██╗  ██╗██╗███████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██║  ██║██║██╔════╝██║  ██║██║████╗  ██║██╔════╝ 
██████╔╝███████║██║███████╗███████║██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██║██║╚════██║██╔══██║██║██║╚██╗██║██║   ██║
██║     ██║  ██║██║███████║██║  ██║██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚═╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📊 Typer phishing

| Type | Målgruppe | Beskrivelse |
|------|--------|-------------|
| **Phishing** | Mange mottakere | Generelle e-poster til mange brukere |
| **Spear phishing** | Bestemte personer | Målrettet mot enkeltpersoner |
| **Whaling** | Ledere | Rettet mot toppledelse og andre ledere |
| **Clone phishing** | Alle | Kopi av en legitim e-post med ondsinnet innhold |
| **Vishing** | Alle | Nettfisking via tale eller telefon |
| **Smishing** | Alle | Nettfisking via SMS |
| **Pharming** | Alle | DNS-manipulering som leder til et falskt nettsted |

---

## 🛠️ Oppsett og bruk av GoPhish

### Installasjon
```bash
# Last ned GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
cd gophish

# Start GoPhish
./gophish

# Åpne administrasjonspanelet
https://localhost:3333
# Standard pålogging: admin / se konsollutskriften
```

### Arbeidsflyt for kampanjeoppsett
```
1. Sending Profile   - Konfigurer SMTP-serveren
2. Landing Page      - Opprett en falsk påloggingsside
3. Email Template    - Utform phishingmeldingen
4. Users & Groups    - Importer listen over målpersoner
5. Launch Campaign   - Send phishingmeldingene
6. Monitor Results   - Følg åpninger, klikk og påloggingsopplysninger
```

> **Begreper:** En landingsside er siden lenken leder til. En phishlet beskriver hvordan en phishingproxy håndterer et bestemt nettsted; en lure er lenken målpersonen mottar. Nettfisking via telefon kalles vishing, og via SMS smishing.

### Eksempel på e-postmal

> **Om kodeeksemplene:** HTML-malene er bevart på engelsk, inkludert synlig tekst, attributter og GoPhish-feltene `{{.FirstName}}` og `{{.URL}}`. Den første ber mottakeren bekrefte kontoen innen 24 timer; den andre viser et forenklet påloggingsskjema.
```html
<!DOCTYPE html>
<html>
<body>
<p>Dear {{.FirstName}},</p>
<p>Your IT department requires you to verify your account credentials.</p>
<p>Please click the link below to complete verification:</p>
<p><a href="{{.URL}}">Verify Account</a></p>
<p>This action is required within 24 hours.</p>
<br>
<p>IT Support Team</p>
</body>
</html>
```

### Eksempel på landingsside
```html
<!DOCTYPE html>
<html>
<head>
    <title>Microsoft Login</title>
</head>
<body>
    <form method="post" action="">
        <h2>Sign in</h2>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Sign in</button>
    </form>
</body>
</html>
```

---

## 🔥 Evilginx2 (omgåelse av 2FA)

### Installasjon
```bash
# Installer Evilginx2
go install github.com/kgretzky/evilginx2@latest

# Eller last ned en binærfil
wget https://github.com/kgretzky/evilginx2/releases/...

# Kjør
sudo evilginx2
```

> **Versjonsmerknad:** Kommandoer, phishlet-navn og plassholderen `releases/...` er beholdt fra originalen. Tilgjengelige phishlets og installasjonsmåte avhenger av versjon og konfigurasjon.

> **Presisering:** Proxybasert phishing kan omgå enkelte MFA-metoder, men ikke all tofaktorautentisering. FIDO/WebAuthn gir phishingmotstand gjennom binding til nettstedet. Se [CISA](https://www.cisa.gov/sites/default/files/2023-01/fact-sheet-implementing-phishing-resistant-mfa-508c.pdf).

### Grunnleggende oppsett
```bash
# I Evilginx2-konsollen
config domain yourdomain.com
config ip YOUR_SERVER_IP

# List phishlets
phishlets

# Aktiver phishlet for Microsoft 365
phishlets hostname o365 login.yourdomain.com
phishlets enable o365

# Opprett lokkelenke (lure)
lures create o365
lures get-url 0
```

### Mål for phishlets
```
o365        - Microsoft 365
google      - Google-kontoer
linkedin    - LinkedIn
twitter     - Twitter/X
github      - GitHub
instagram   - Instagram
```

---

## 📧 Innsamling av e-postadresser

### theHarvester
```bash
# Samle inn e-postadresser
theHarvester -d target.com -b all

# Bestemte kilder
theHarvester -d target.com -b google,linkedin,bing

# Skriv resultatet til fil
theHarvester -d target.com -b all -f output.html
```

### Hunter.io
```bash
# Via API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Hent e-postformat
curl "https://api.hunter.io/v2/email-finder?domain=target.com&first_name=John&last_name=Doe&api_key=YOUR_KEY"
```

### Kartlegging via LinkedIn
```bash
# Bruk linkedin2username
python linkedin2username.py -u USERNAME -c COMPANY_NAME

# Gjett adresseformat
john.doe@company.com
jdoe@company.com
j.doe@company.com
johnd@company.com
```

---

## 📮 Infrastruktur for utsending

### SMTP-tjenester for testing
```
- Amazon SES
- SendGrid
- Mailgun
- SMTP2GO
- Egen drift (stor risiko for å havne i spamfilteret)
```

### Omgåelse av SPF/DKIM/DMARC

> **Presisering:** `~all` eller en manglende SPF-oppføring er ikke alene en generell omgåelse av DKIM/DMARC. DMARC vurderer autentiserte domener opp mot avsenderdomenet (alignment); et godkjent SPF-resultat alene er utilstrekkelig hvis domenene ikke samsvarer. Se [DMARC, RFC 9989](https://www.rfc-editor.org/rfc/rfc9989.html).
```bash
# Kontroller SPF-oppføringen
dig txt target.com | grep spf

# SPF-svakheter som kan utnyttes:
# - "include:" med tredjepartstjenester som tillater for mye
# - "~all" (softfail) i stedet for "-all"
# - Ingen SPF-oppføring

# Kontroller DMARC
dig txt _dmarc.target.com
```

### Domeneoppsett for phishing
```bash
# Domener som ligner (typosquatting)
target.com → tarqet.com
target.com → target-secure.com
target.com → target.co
target.com → target-login.com

# Kontroller tilgjengelighet
whois potential-phish-domain.com
```

---

## 📱 Smishing (nettfisking via SMS)

### Vanlige påskudd
```
- Varsel om pakkelevering
- Sikkerhetsvarsel fra banken
- Premie eller lotterigevinst
- Bekreftelse av konto
- Kode for tofaktorautentisering
```

### Tjenester for SMS-utsending
```
- Twilio
- Nexmo/Vonage
- Plivo
- AWS SNS
```

### Eksempelmeldinger
```
[FedEx] Pakken din venter. Bekreft levering: http://short.url/abc

[BankName] Uvanlig aktivitet oppdaget. Bekreft nå: http://secure-link.com

[Netflix] Betalingen din mislyktes. Oppdater: http://netflix-billing.com
```

---

## 📞 Vishing (nettfisking via telefon)

### Vanlige situasjoner
```
□ IT-brukerstøtte som trenger å tilbakestille et passord
□ Bankens svindelavdeling
□ Offentlig myndighet (IRS, skattekontor)
□ Falsk teknisk brukerstøtte
□ Hasteforespørsel fra administrerende direktør eller økonomidirektør
□ Bekreftelse av leverandøropplysninger
```

### Eksempel på samtalemanus for vishing
```
«Hei, dette er [Name] fra IT-brukerstøtte. Vi har oppdaget
uvanlig aktivitet på kontoen din og må kontrollere
påloggingsopplysningene dine for å forhindre et sikkerhetsbrudd.
Kan du bekrefte brukernavnet og det nåværende passordet ditt,
slik at jeg kan kontrollere kontoen din?»
```

### Verktøy
```bash
# Forfalskning av vist telefonnummer (caller ID spoofing)
- SpoofCard
- SpoofTel

# Stemmeforvrengere
- Voicemod
- Clownfish
```

---

## 🎯 SET (Social Engineering Toolkit)

### Installasjon
```bash
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
sudo python setoolkit
```

### Menyvalg

Menytekstene nedenfor er beholdt nøyaktig slik de står i originalen, slik at de kan kjennes igjen i verktøyet. De dekker målrettet phishing, nettsideangrep, infiserte medier, payload med lytter og masseutsending. Nummerering og navn kan variere mellom versjoner.
```
1) Spear-Phishing Attack Vectors
2) Website Attack Vectors
3) Infectious Media Generator
4) Create a Payload and Listener
5) Mass Mailer Attack
```

### Innsamler av påloggingsopplysninger (credential harvester)
```bash
# I SET:
1) Social-Engineering Attacks
2) Website Attack Vectors
3) Credential Harvester Attack Method
2) Site Cloner
# Angi URL-en som skal klones (f.eks. https://login.microsoft.com)
# SET er vert for det falske nettstedet og samler inn påloggingsopplysninger
```

---

## 📝 E-postmaler for phishing

### Tilbakestilling av passord
```
Emne: [HANDLING KREVES] Forespørsel om tilbakestilling av passord

Hei {Name},

Vi har mottatt en forespørsel om å tilbakestille passordet ditt.
Hvis du ikke ba om dette, klikker du nedenfor for å sikre
kontoen din umiddelbart.

[Sikre kontoen min]

Denne lenken utløper om 24 timer.

Vennlig hilsen
Sikkerhetsteamet
```

### IT-brukerstøtte
```
Emne: Planlagt systemvedlikehold – handling kreves

Hei {Name},

IT-teamet vårt skal utføre planlagt vedlikehold. For å beholde
uavbrutt tilgang må du bekrefte påloggingsopplysningene dine:

[Bekreft nå]

Hvis du ikke bekrefter, kan kontoen bli sperret.

IT-avdelingen
```

### Pakkelevering
```
Emne: Pakken din kunne ikke leveres

Pakken din fra Amazon kunne ikke leveres. Bekreft
leveringsadressen din:

[Bekreft levering]

Sporingsnummer: {Random}

Takk,
Leveringsteamet
```

---

## 📋 Sjekkliste for phishingkampanjer

```markdown
Før kampanjen:
□ Definer omfang og målpersoner (autorisert testing)
□ Samle inn målpersonenes e-postadresser
□ Registrer et domene som ligner
□ Sett opp SMTP-infrastrukturen
□ Konfigurer SPF/DKIM hvis mulig
□ Opprett landingssiden
□ Opprett e-postmalen

Gjennomføring av kampanjen:
□ Test e-postlevering (spamfiltre)
□ Importer listen over målpersoner
□ Planlegg kampanjen
□ Overvåk leveringsraten
□ Følg åpninger, klikk og innsendte opplysninger

Etter kampanjen:
□ Lag rapporter
□ Analyser klikkrater
□ Gjennomgå innsamlede data
□ Identifiser opplæringsbehov
□ Dokumenter funn
```

---

## 🔗 Relaterte hurtigreferanser

- [Pretexting](./Pretexting.nb.md)
- [OSINT – Google Dorking](../../Google-Dorking/README.md)

---

**Tilbake til oversikten:** [🎭 Sosial manipulering](./README.nb.md)
