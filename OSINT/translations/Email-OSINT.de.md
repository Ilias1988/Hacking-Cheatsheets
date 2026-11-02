# 📧 Spickzettel für E-Mail-OSINT

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Email-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Email-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

```
███████╗███╗   ███╗ █████╗ ██╗██╗     
██╔════╝████╗ ████║██╔══██╗██║██║     
█████╗  ██╔████╔██║███████║██║██║     
██╔══╝  ██║╚██╔╝██║██╔══██║██║██║     
███████╗██║ ╚═╝ ██║██║  ██║██║███████╗
╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚══════╝
```

---

## 🔍 Werkzeuge zum Ermitteln von E-Mail-Adressen

| Werkzeug | URL | Zweck |
|------|-----|---------|
| **Hunter.io** | hunter.io | Unternehmens-E-Mail-Adressen ermitteln |
| **Phonebook.cz** | phonebook.cz | Kostenlose E-Mail-Suche |
| **Snov.io** | snov.io | E-Mail-Adressen ermitteln |
| **Clearbit Connect** | clearbit.com | E-Mail-Daten anreichern |
| **RocketReach** | rocketreach.co | Kontakte ermitteln |
| **Voila Norbert** | voilanorbert.com | E-Mail-Abfrage |

---

## 🛠️ Befehle zur E-Mail-Enumeration

### theHarvester
```bash
# E-Mail-Adressen sammeln
theHarvester -d target.com -l 500 -b all
theHarvester -d target.com -b google,linkedin,bing
theHarvester -d target.com -b all -f emails.html
```

### Hunter.io CLI
```bash
# API-Abfrage
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# E-Mail-Verifizierung
curl "https://api.hunter.io/v2/email-verifier?email=test@target.com&api_key=YOUR_KEY"
```

### h8mail (Suche nach Datenlecks)
```bash
pip3 install h8mail
h8mail -t target@email.com
h8mail -t target@email.com -c h8mail_config.ini
```

### holehe (Registrierungen prüfen)
```bash
pip3 install holehe
holehe target@email.com
holehe target@email.com --only-used
```

---

## 📧 Muster für E-Mail-Adressen

```bash
# Häufige Adressformate in Unternehmen
first.last@company.com     # john.smith@company.com
flast@company.com          # jsmith@company.com
firstl@company.com         # johns@company.com
first_last@company.com     # john_smith@company.com
first@company.com          # john@company.com
last.first@company.com     # smith.john@company.com
```

### Varianten von E-Mail-Adressen erzeugen
```python
# Varianten von E-Mail-Adressen erzeugen
names = ["john", "smith"]
domain = "company.com"

patterns = [
    f"{names[0]}.{names[1]}@{domain}",
    f"{names[0][0]}{names[1]}@{domain}",
    f"{names[0]}{names[1][0]}@{domain}",
    f"{names[0]}_{names[1]}@{domain}",
    f"{names[0]}@{domain}",
]
```

---

## 🔐 Auf Datenlecks prüfen

| Dienst | URL | Art |
|---------|-----|------|
| **Have I Been Pwned** | haveibeenpwned.com | Kostenlos |
| **DeHashed** | dehashed.com | Kostenpflichtig |
| **LeakCheck** | leakcheck.io | Kostenpflichtig |
| **Snusbase** | snusbase.com | Kostenpflichtig |
| **IntelX** | intelx.io | Freemium |

### HIBP API
```bash
curl -H "hibp-api-key: YOUR_KEY" \
  "https://haveibeenpwned.com/api/v3/breachedaccount/test@example.com"
```

---

## ✅ E-Mail-Verifizierung

### Verifizierungsmethoden
```
1. MX-Eintrag prüfen – die Domain hat einen Mailserver
2. SMTP-Verifizierung – das Postfach existiert
3. Syntax prüfen – gültiges E-Mail-Format
4. Wegwerfadressen prüfen – keine temporäre E-Mail-Adresse
5. Rollenadressen prüfen – keine allgemeine Adresse (info@, admin@)
```

### Einfache SMTP-Prüfung
```bash
# MX-Einträge prüfen
nslookup -type=mx target.com
dig mx target.com

# Verifizierung über Telnet
telnet mail.target.com 25
HELO test.com
MAIL FROM:<test@test.com>
RCPT TO:<target@target.com>
# 250 = existiert, 550 = existiert nicht
```

---

## 📋 Checkliste für E-Mail-OSINT

```markdown
## Zieladresse: [email@domain.com]

□ Existenz der E-Mail-Adresse prüfen (SMTP/Hunter)
□ Auf Datenlecks prüfen (HIBP)
□ Zugehörige Konten ermitteln (holehe)
□ In Google/Bing suchen
□ Registrierungen bei sozialen Netzwerken prüfen
□ Nach öffentlichen Profilen suchen
□ Auf Paste-Seiten suchen
□ Berufliche Netzwerke prüfen
```

---

**Zurück zu OSINT:** [🔍 OSINT-Übersicht](./README.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Korrektur zur SMTP-Prüfung:** Die Kurzform „250 = existiert, 550 = existiert nicht“ ist zu pauschal. Eine Annahme bei `RCPT TO` beweist kein individuelles Postfach; Server können Empfänger erst später prüfen. Ein Fehler kann auch durch Richtlinien oder fehlende Zugriffsrechte entstehen. Ein MX-Eintrag allein beweist ebenfalls keine Zustellbarkeit einer bestimmten Adresse. [SMTP: RFC 5321](https://datatracker.ietf.org/doc/html/rfc5321).

> **Werkzeug- und Datenquellen:** Unterstützte Quellen, API-Schlüssel, Tarife und Optionen können sich ändern. Ein Treffer in einem Datenleck oder einer Registrierungsprüfung beweist weder aktuelle Kontonutzung noch die Identität der Person. Die Python-Vorlage erzeugt nur die Liste `patterns`; sie gibt sie nicht aus. Adressmuster sind Vorschläge, keine bestätigten Adressen.
