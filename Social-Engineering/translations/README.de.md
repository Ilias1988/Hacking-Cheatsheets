# 🎭 Social Engineering – Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 Was ist Social Engineering?

Social Engineering bezeichnet die gezielte Manipulation von Menschen, damit sie vertrauliche Informationen preisgeben oder sicherheitskritische Handlungen ausführen. Dabei werden psychologische Mechanismen statt technischer Schwachstellen ausgenutzt.

**Grundprinzipien:**
- 🎭 **Autorität** – Menschen vertrauen Autoritätspersonen
- ⏰ **Dringlichkeit** – Zeitdruck verringert die kritische Prüfung
- 🤝 **Gegenseitigkeit** – Menschen fühlen sich verpflichtet, Gefälligkeiten zu erwidern
- 👥 **Soziale Bewährtheit (Social Proof)** – Menschen orientieren sich am Verhalten anderer
- 😊 **Sympathie** – Menschen kommen Bitten eher nach, wenn ihnen die andere Person sympathisch ist
- 📉 **Knappheit** – Begrenzte Verfügbarkeit steigert das Interesse

---

## 📖 Leitfäden zu Social Engineering

| Thema | Beschreibung | Leitfaden |
|-------|-------------|-------|
| **Phishing** | Techniken und Werkzeuge für Phishing per E-Mail, SMS und Telefon | [📄 Ansehen](./Phishing.de.md) |
| **Pretexting** | Glaubwürdige Szenarien und Rollen entwickeln | [📄 Ansehen](./Pretexting.de.md) |

---

## 🛠️ Wichtige Werkzeuge für Social Engineering

| Tool | Zweck | Art |
|------|---------|------|
| **GoPhish** | Framework für Phishing-Kampagnen | Phishing |
| **SET (Social Engineering Toolkit)** | Umfassendes Social-Engineering-Framework | Vielseitig |
| **Evilginx2** | Phishing über einen Man-in-the-Middle-Proxy (2FA-Umgehung) | Phishing |
| **King Phisher** | Plattform für Phishing-Kampagnen | Phishing |
| **Modlishka** | Phishing über einen Reverse Proxy | Phishing |
| **theHarvester** | E-Mail-Adressen und Domains sammeln | Aufklärung |
| **LinkedIn-Werkzeuge** | Beschäftigte systematisch erfassen | OSINT |
| **Maltego** | OSINT und Darstellung von Beziehungen | Aufklärung |

### Werkzeuge schnell installieren
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

## 📊 Arten von Social-Engineering-Angriffen

| Angriffsart | Beschreibung | Angriffsweg |
|-------------|-------------|--------|
| **Phishing** | Täuschende E-Mails zum Diebstahl von Zugangsdaten | E-Mail |
| **Spear Phishing** | Gezieltes Phishing gegen bestimmte Personen | E-Mail |
| **Whaling** | Phishing gegen die Unternehmensleitung | E-Mail |
| **Vishing** | Social Engineering über Sprache oder Telefon | Telefon |
| **Smishing** | Phishing per SMS | SMS |
| **Pretexting** | Erfundene Szenarien zur Informationsgewinnung | Beliebig |
| **Baiting** | Platzieren infizierter Datenträger, etwa USB-Sticks | Physisch |
| **Quid Pro Quo** | Eine Gegenleistung für Informationen anbieten | Beliebig |
| **Tailgating** | Einer berechtigten Person in einen geschützten Bereich folgen | Physisch |
| **Watering Hole** | Websites kompromittieren, die Zielpersonen besuchen | Web |

---

## 🔗 Kurzreferenz: Anzeichen für Phishing

### Warnsignale in E-Mails
```
□ Absenderadresse passt nicht zur Unternehmensdomain
□ Unpersönliche Anrede („Sehr geehrter Kunde“)
□ Zeitdruck oder Drohungen
□ Rechtschreib- oder Grammatikfehler
□ Verdächtige Links (Zieladresse beim Darüberfahren prüfen)
□ Unerwartete Anhänge
□ Aufforderung zur Preisgabe sensibler Informationen
□ Angebote, die zu gut erscheinen, um wahr zu sein
```

### URL-Analyse
```bash
# Alter der Domain prüfen
whois suspicious-domain.com

# Reputation prüfen
https://www.virustotal.com
https://urlscan.io

# Verkürzte URLs analysieren
https://checkshorturl.com
```

---

## 📚 Weiterführende Ressourcen

- [OWASP Social Engineering](https://owasp.org/www-community/attacks/Social_Engineering)
- [MITRE ATT&CK - Initial Access](https://attack.mitre.org/tactics/TA0001/)
- [Phishing.org](https://www.phishing.org/)
- [Social Engineering Framework](https://www.social-engineer.org/)

---

<p align="center">
  <b>🎭 Social Engineering nur für ausdrücklich autorisierte Tests einsetzen!</b>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Begriffe:** OSINT bedeutet Open Source Intelligence, also die Auswertung öffentlich zugänglicher Informationen. Ein *Reverse Proxy* vermittelt zwischen Browser und Zieldienst. *2FA* bezeichnet Zwei-Faktor-Authentifizierung. Die Bezeichnung „2FA-Umgehung“ ist keine allgemeine Erfolgsgarantie: Phishing-resistente Verfahren wie FIDO2/WebAuthn binden die Anmeldung an den richtigen Dienstnamen. [NIST SP 800-63B-4](https://pages.nist.gov/800-63-4/sp800-63b.html).

> **Versionen und Aussagekraft:** Die Download-URL nennt ausdrücklich GoPhish v0.12.1; sie bezeichnet keine allgemeingültige aktuelle Version. Befehle mit `@latest`, Modulnamen und Datenquellen können sich ändern. Einzelne Warnsignale sind Indizien und kein Beweis für Phishing; auch sprachlich fehlerfreie E-Mails können täuschen. Alle technischen Originalwerte und Befehle bleiben erhalten.
