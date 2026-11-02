# 🎣 Phishing – Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 📊 Phishing-Arten

| Art | Zielgruppe | Beschreibung |
|------|--------|-------------|
| **Phishing** | Breite Masse | Allgemeine E-Mails an viele Benutzer |
| **Spear Phishing** | Bestimmte Personen | Auf bestimmte Personen zugeschnitten |
| **Whaling** | Führungskräfte | Gegen Geschäftsführung und Management gerichtet |
| **Clone Phishing** | Beliebig | Kopie einer legitimen E-Mail mit schädlichem Inhalt |
| **Vishing** | Beliebig | Phishing per Sprache oder Telefon |
| **Smishing** | Beliebig | Phishing per SMS |
| **Pharming** | Beliebig | Umleitung auf eine gefälschte Website durch DNS-Manipulation |

---

## 🛠️ GoPhish einrichten und verwenden

### Installation
```bash
# GoPhish herunterladen
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
cd gophish

# GoPhish starten
./gophish

# Administrationsoberfläche aufrufen
https://localhost:3333
# Standardzugang: admin / Passwort in der Konsolenausgabe nachsehen
```

### Ablauf zur Einrichtung einer Kampagne
```
1. Sending Profile   – SMTP-Server konfigurieren
2. Landing Page      – Gefälschte Anmeldeseite erstellen
3. Email Template    – Phishing-E-Mail gestalten
4. Users & Groups    – Liste der Zielpersonen importieren
5. Launch Campaign   – Phishing-E-Mails versenden
6. Monitor Results   – Öffnungen, Klicks und Zugangsdaten erfassen
```

### Beispiel einer E-Mail-Vorlage
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

### Beispiel einer Landingpage
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

## 🔥 Evilginx2 (Umgehung von Zwei-Faktor-Authentifizierung)

### Installation
```bash
# Evilginx2 installieren
go install github.com/kgretzky/evilginx2@latest

# Alternativ die Binärdatei herunterladen
wget https://github.com/kgretzky/evilginx2/releases/...

# Ausführen
sudo evilginx2
```

### Grundeinrichtung
```bash
# In der Evilginx2-Konsole
config domain yourdomain.com
config ip YOUR_SERVER_IP

# Phishlets auflisten
phishlets

# Microsoft-365-Phishlet aktivieren
phishlets hostname o365 login.yourdomain.com
phishlets enable o365

# Lock-URL (Lure) erstellen
lures create o365
lures get-url 0
```

### Zieldienste für Phishlets
```
o365        - Microsoft 365
google      - Google-Konten
linkedin    - LinkedIn
twitter     - Twitter/X
github      - GitHub
instagram   - Instagram
```

---

## 📧 E-Mail-Adressen sammeln (Email Harvesting)

### theHarvester
```bash
# E-Mail-Adressen sammeln
theHarvester -d target.com -b all

# Bestimmte Quellen
theHarvester -d target.com -b google,linkedin,bing

# Ausgabe in eine Datei schreiben
theHarvester -d target.com -b all -f output.html
```

### Hunter.io
```bash
# Über die API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Format einer E-Mail-Adresse ermitteln
curl "https://api.hunter.io/v2/email-finder?domain=target.com&first_name=John&last_name=Doe&api_key=YOUR_KEY"
```

### LinkedIn-Enumeration
```bash
# Mit linkedin2username
python linkedin2username.py -u USERNAME -c COMPANY_NAME

# Mögliche Adressformate ableiten
john.doe@company.com
jdoe@company.com
j.doe@company.com
johnd@company.com
```

---

## 📮 Versandinfrastruktur

### SMTP-Dienste für Tests
```
- Amazon SES
- SendGrid
- Mailgun
- SMTP2GO
- Selbst betrieben (hohes Risiko einer Spam-Einstufung)
```

### Umgehung von SPF/DKIM/DMARC
```bash
# SPF-Eintrag prüfen
dig txt target.com | grep spf

# Mögliche SPF-Schwachstellen:
# - "include:" mit zu großzügig berechtigten Drittanbietern
# - "~all" (Softfail) statt "-all"
# - Kein SPF-Eintrag

# DMARC prüfen
dig txt _dmarc.target.com
```

### Domains für Phishing einrichten
```bash
# Ähnliche Domains (Typosquatting)
target.com → tarqet.com
target.com → target-secure.com
target.com → target.co
target.com → target-login.com

# Verfügbarkeit prüfen
whois potential-phish-domain.com
```

---

## 📱 Smishing (Phishing per SMS)

### Häufige Vorwände
```
- Benachrichtigung über eine Paketzustellung
- Sicherheitswarnung einer Bank
- Preis- oder Lotteriegewinn
- Kontoverifizierung
- Code für die Zwei-Faktor-Authentifizierung
```

### Dienste zum SMS-Versand
```
- Twilio
- Nexmo/Vonage
- Plivo
- AWS SNS
```

### Beispielnachrichten
```
[FedEx] Ihr Paket wartet auf Sie. Zustellung bestätigen: http://short.url/abc

[BankName] Ungewöhnliche Aktivität erkannt. Jetzt prüfen: http://secure-link.com

[Netflix] Ihre Zahlung ist fehlgeschlagen. Aktualisieren: http://netflix-billing.com
```

---

## 📞 Vishing (Phishing per Telefon)

### Häufige Szenarien
```
□ IT-Support benötigt eine Passwortzurücksetzung
□ Betrugsabteilung einer Bank
□ Behörde (IRS, Finanzamt)
□ Betrug durch angeblichen technischen Support
□ Dringende Anfrage des Geschäftsführers oder Finanzvorstands
□ Überprüfung eines Dienstleisters
```

### Beispiel eines Vishing-Gesprächsleitfadens
```
„Guten Tag, hier ist [Name] vom IT-Helpdesk. Wir haben
ungewöhnliche Aktivitäten in Ihrem Konto festgestellt und müssen
Ihre Zugangsdaten überprüfen, um einen Sicherheitsvorfall zu
verhindern. Können Sie mir Ihren Benutzernamen und Ihr aktuelles
Passwort nennen, damit ich Ihr Konto überprüfen kann?“
```

### Werkzeuge
```bash
# Absenderrufnummer fälschen (Caller-ID-Spoofing)
- SpoofCard
- SpoofTel

# Stimmenveränderer
- Voicemod
- Clownfish
```

---

## 🎯 SET (Social Engineering Toolkit)

### Installation
```bash
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
sudo python setoolkit
```

### Menüoptionen
```
1) Spear-Phishing Attack Vectors
2) Website Attack Vectors
3) Infectious Media Generator
4) Create a Payload and Listener
5) Mass Mailer Attack
```

### Credential Harvester (Zugangsdaten erfassen)
```bash
# In SET:
1) Social-Engineering Attacks
2) Website Attack Vectors
3) Credential Harvester Attack Method
2) Site Cloner
# Zu klonende URL eingeben (z. B. https://login.microsoft.com)
# SET stellt die gefälschte Website bereit und erfasst Zugangsdaten
```

---

## 📝 Vorlagen für Phishing-E-Mails

### Passwort zurücksetzen
```
Betreff: [HANDLUNG ERFORDERLICH] Anfrage zur Passwortzurücksetzung

Guten Tag {Name},

wir haben eine Anfrage zur Zurücksetzung Ihres Passworts erhalten.
Falls diese Anfrage nicht von Ihnen stammt, klicken Sie bitte unten,
um Ihr Konto sofort abzusichern.

[Mein Konto absichern]

Dieser Link läuft in 24 Stunden ab.

Mit freundlichen Grüßen
Ihr Sicherheitsteam
```

### IT-Support
```
Betreff: Geplante Systemwartung – Handlung erforderlich

Guten Tag {Name},

unser IT-Team führt eine geplante Wartung durch. Bitte bestätigen
Sie Ihre Zugangsdaten, damit Ihr Zugang ohne Unterbrechung erhalten bleibt:

[Jetzt bestätigen]

Ohne Bestätigung kann Ihr Konto gesperrt werden.

Ihre IT-Abteilung
```

### Paketzustellung
```
Betreff: Ihr Paket konnte nicht zugestellt werden

Ihr Paket von Amazon konnte nicht zugestellt werden. Bitte
bestätigen Sie Ihre Lieferadresse:

[Zustellung bestätigen]

Sendungsnummer: {Random}

Vielen Dank
Ihr Zustellteam
```

---

## 📋 Checkliste für Phishing-Kampagnen

```markdown
Vor der Kampagne:
□ Umfang und Zielpersonen festlegen (autorisierter Test)
□ E-Mail-Adressen der Zielpersonen sammeln
□ Ähnliche Domain registrieren
□ SMTP-Infrastruktur einrichten
□ SPF/DKIM nach Möglichkeit konfigurieren
□ Landingpage erstellen
□ E-Mail-Vorlage erstellen

Durchführung der Kampagne:
□ E-Mail-Zustellung testen (Spamfilter)
□ Liste der Zielpersonen importieren
□ Kampagne zeitlich planen
□ Zustellraten überwachen
□ Öffnungen, Klicks und Formulareingaben erfassen

Nach der Kampagne:
□ Berichte erstellen
□ Klickraten analysieren
□ Erfasste Daten prüfen
□ Schulungsbedarf ermitteln
□ Ergebnisse dokumentieren
```

---

## 🔗 Verwandte Spickzettel

- [Pretexting](./Pretexting.de.md)
- [OSINT - Google Dorking](../../Google-Dorking/README.md)

---

**Zurück zur Übersicht:** [🎭 Social Engineering](./README.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Grenzen der „2FA-Umgehung“:** Reverse-Proxy-Phishing kann bestimmte Anmeldeverfahren und Sitzungen angreifen. Es umgeht nicht pauschal jede Mehrfaktor-Authentifizierung. FIDO2/WebAuthn bietet durch die Bindung an den Dienstnamen Phishing-Resistenz. [NIST SP 800-63B-4](https://pages.nist.gov/800-63-4/sp800-63b.html).

> **SPF/DKIM/DMARC:** Die Überschrift des Originals fasst verschiedene Mechanismen zu stark zusammen. Ein SPF-Softfail (`~all`) ist kein SPF-Pass und belegt für sich weder einen DKIM- noch einen DMARC-Bypass. DMARC benötigt einen erfolgreichen SPF- oder DKIM-Nachweis mit passender Domain-Zuordnung (*Alignment*) zur sichtbaren Absenderdomain. [SPF: RFC 7208](https://datatracker.ietf.org/doc/html/rfc7208), [DMARC: RFC 9989](https://www.rfc-editor.org/rfc/rfc9989.html).

> **Historische Beispiele:** `wget .../releases/...` enthält einen Platzhalter und ist keine vollständige Download-Adresse. Die tatsächlich verfügbaren Phishlets und Datenquellen hängen von Werkzeugversion und Installation ab. Nach dem Entpacken von GoPhish muss das Arbeitsverzeichnis die Programmdatei enthalten; ein Unterordner `gophish` entsteht nicht zwangsläufig. [GoPhish-Installation](https://docs.getgophish.com/user-guide/installation). SMTP- und SMS-Anbieternamen sind Beispiele aus dem Original, keine Zusage zu deren Nutzungsbedingungen oder Eignung.

> **Wörtliche Vorlagen:** Die HTML-Payloads einschließlich Textknoten, Formularattributen und GoPhish-Platzhaltern bleiben unverändert, ebenso die tatsächlichen englischen Menübezeichnungen von SET und GoPhish. Die beiden HTML-Beispiele zeigen eine Aufforderung zur Kontoprüfung und ein Anmeldeformular. Freie Gesprächs- und Nachrichtenvorlagen wurden ins Deutsche übertragen; URLs und technische Platzhalter bleiben erhalten. Die URL-Beispiele sind keine verifizierten, ungefährlichen Testadressen.
