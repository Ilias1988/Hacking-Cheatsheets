# 👤 OSINT zur Personensuche

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🔍 Suchdienste für Personen

### Kostenlose Dienste
| Werkzeug | URL | Abdeckung |
|------|-----|----------|
| **ThatsThem** | thatsthem.com | USA |
| **FastPeopleSearch** | fastpeoplesearch.com | USA |
| **TruePeopleSearch** | truepeoplesearch.com | USA |
| **Webmii** | webmii.com | Weltweit |
| **PeekYou** | peekyou.com | USA |
| **Pipl** | pipl.com | Weltweit |

### Kostenpflichtige Dienste
| Werkzeug | URL | Funktionen |
|------|-----|----------|
| **Spokeo** | spokeo.com | Vertiefte Suche, soziale Netzwerke |
| **BeenVerified** | beenverified.com | Hintergrundprüfungen |
| **Intelius** | intelius.com | Strafregisterdaten |
| **US Search** | ussearch.com | Gerichtsunterlagen |
| **Radaris** | radaris.com | Immobilienunterlagen |

---

## 🔎 Google Dorking für die Personensuche

### Grundlegende Suchabfragen
```
# Namenssuche
"John Smith" site:linkedin.com
"John Smith" site:facebook.com
"John Smith" site:twitter.com

# Name und Ort
"John Smith" "New York"
"John Smith" "NYC" OR "New York City"

# Name und Unternehmen
"John Smith" "Microsoft"
"John Smith" site:microsoft.com

# Name und E-Mail-Adresse
"John Smith" "@gmail.com"
"John Smith" email
```

### Berufliche Informationen
```
# Suche nach Lebensläufen
"John Smith" filetype:pdf resume
"John Smith" filetype:doc CV
intitle:"curriculum vitae" "John Smith"

# LinkedIn-spezifische Suche
site:linkedin.com/in "John Smith"
site:linkedin.com "John Smith" "Software Engineer"

# Berufliche Profile
site:about.me "John Smith"
site:crunchbase.com "John Smith"
```

### Profile in sozialen Netzwerken ermitteln
```
# Alle Profile finden
"John Smith" (site:facebook.com OR site:twitter.com OR site:instagram.com)

# Suche nach Benutzernamen
"johnsmith123" site:twitter.com
"johnsmith123" site:reddit.com
"johnsmith123" site:github.com
```

---

## 🛠️ Kommandozeilenwerkzeuge

### Sherlock (Suche nach Benutzernamen)
```bash
# Installieren
git clone https://github.com/sherlock-project/sherlock.git
cd sherlock
pip3 install -r requirements.txt

# Auf verschiedenen Plattformen nach einem Benutzernamen suchen
python3 sherlock username

# Mehrere Benutzernamen
python3 sherlock user1 user2 user3

# Nur bestimmte Websites
python3 sherlock username --site twitter instagram facebook

# Ergebnisse exportieren
python3 sherlock username --output results.txt
python3 sherlock username --csv
python3 sherlock username --xlsx
```

### Maigret (erweiterte Suche nach Benutzernamen)
```bash
# Installieren
pip3 install maigret

# Suchen
maigret username

# Mit Tor
maigret username --tor

# JSON-Ausgabe
maigret username --json results.json
```

### holehe (E-Mail-Registrierungen prüfen)
```bash
# Installieren
pip3 install holehe

# E-Mail-Adresse bei verschiedenen Diensten prüfen
holehe email@example.com

# Nur Dienste mit gefundener Registrierung anzeigen
holehe email@example.com --only-used
```

---

## 📧 Von der E-Mail-Adresse zur Person

### Person anhand einer E-Mail-Adresse ermitteln
```
1. E-Mail-Adresse bei Google suchen: "email@example.com"
2. HIBP prüfen: haveibeenpwned.com
3. In Datenbanken zu Datenlecks suchen
4. Registrierungen in sozialen Netzwerken prüfen
5. Dienste für Reverse-E-Mail-Abfragen nutzen
```

### E-Mail-Adressformat ermitteln
```bash
# Häufige E-Mail-Adressformate
first.last@company.com
flast@company.com
firstl@company.com
first_last@company.com
first@company.com

# Formatermittlung mit Hunter.io
https://hunter.io/email-finder
```

---

## 📱 OSINT zu Telefonnummern

### Dienste zur Telefonnummernsuche
| Dienst | URL | Art |
|---------|-----|------|
| **TrueCaller** | truecaller.com | Anruferkennung |
| **Sync.me** | sync.me | Anruferkennung |
| **NumLookup** | numlookup.com | Kostenlose Abfrage |
| **SpyDialer** | spydialer.com | Rückwärtssuche |
| **CallerID Test** | calleridtest.com | Informationen zum Netzbetreiber |

### Google Dorks für Telefonnummern
```
# Grundlegende Suche
"555-123-4567"
"+1 555 123 4567"
"5551234567"

# Zugehörige Informationen finden
"555-123-4567" site:facebook.com
"555-123-4567" "@"
"555-123-4567" "email"
```

### OSINT zu Telefonnummern
```bash
# phoneinfoga
pip3 install phoneinfoga
phoneinfoga scan -n "+1234567890"
phoneinfoga serve -p 8080  # Weboberfläche

# APIs verwenden
curl "https://api.numlookupapi.com/v1/validate/14155551234?apikey=YOUR_KEY"
```

---

## 🏠 Adress- und Immobilienunterlagen

### Immobiliensuche
| Dienst | URL | Abdeckung |
|---------|-----|----------|
| **Zillow** | zillow.com | USA |
| **Redfin** | redfin.com | USA |
| **County Assessor** | [varies] | Örtliche Unterlagen |
| **Whitepages** | whitepages.com | USA |

### Öffentliche Register
```
# Gerichtsunterlagen
PACER (pacer.gov) – US-Bundesgerichte
Websites der Gerichte der US-Bundesstaaten

# Wählerverzeichnisse
Datenbanken zur Wählerregistrierung (je nach US-Bundesstaat unterschiedlich)

# Unternehmensregistrierungen
Websites der Secretary-of-State-Behörden der US-Bundesstaaten
```

---

## 👥 Analyse sozialer Netzwerke

### LinkedIn-OSINT
```
# Profilsuche
site:linkedin.com/in "John Smith"
site:linkedin.com/in "John Smith" "Company Name"

# Verbindungen ermitteln
„People Also Viewed“ prüfen
Gemeinsame Kontakte prüfen
Beschäftigtenliste des Unternehmens
```

### Facebook-OSINT
```
# Profil-URLs
facebook.com/search/people/?q=john%20smith
facebook.com/search/pages/?q=john%20smith

# Graph Search (inzwischen eingeschränkt)
People named "John" who work at "Company"
People who live in "City" and like "Page"
```

### Werkzeuge zur Analyse sozialer Netzwerke
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

## 📸 Foto- und Gesichtssuche

### Umgekehrte Bildersuche
| Dienst | URL | Besonders geeignet für |
|---------|-----|----------|
| **Google Images** | images.google.com | Allgemeine Suche |
| **TinEye** | tineye.com | Exakte Treffer |
| **Yandex** | yandex.com/images | Laut Original besonders gut für Gesichter |
| **Bing Images** | bing.com/images | Gute Alternative |
| **PimEyes** | pimeyes.com | Gesichtserkennung |

### Gesichtssuche
```
1. Yandex Images – laut Original am besten für die Gesichtssuche
2. PimEyes – kostenpflichtige Gesichtserkennung
3. Social Catfish – umgekehrte Bildersuche für soziale Netzwerke
```

---

## 📋 Checkliste für die Personensuche

```markdown
## Zielperson: [Full Name]

### Grundlegende Informationen
□ Vollständiger amtlicher Name
□ Geburtsdatum
□ Aktuelle Adresse
□ Telefonnummern
□ E-Mail-Adressen

### Online-Präsenz
□ LinkedIn-Profil
□ Facebook-Profil
□ Twitter-/X-Konto
□ Instagram
□ TikTok
□ Weitere soziale Netzwerke
□ Persönliche Website oder Blog

### Berufliches
□ Aktueller Arbeitgeber
□ Berufsbezeichnung
□ Beruflicher Werdegang
□ Ausbildung
□ Berufszulassungen
□ Veröffentlichungen und Patente

### Register und Unterlagen
□ Immobilienunterlagen
□ Gerichtsunterlagen
□ Unternehmensregistrierungen
□ Wählerregistrierung
□ Heirats- und Scheidungsunterlagen

### Verbindungen
□ Familienmitglieder
□ Kontaktpersonen
□ Geschäftspartner
□ Soziale Kontakte
```

---

## ⚠️ Rechtliche und ethische Aspekte

```
✓ Nur öffentlich verfügbare Informationen verwenden
✓ Datenschutzgesetze beachten (GDPR, CCPA)
✓ Keine fremde Identität vortäuschen oder täuschen
✓ Die Vorgehensweise dokumentieren
✓ Nur für legitime Zwecke verwenden
✗ Niemanden stalken oder belästigen
✗ Nicht auf private Konten zugreifen
✗ Ergebnisse nicht ohne Einwilligung veröffentlichen
```

---

**Zurück zu OSINT:** [🔍 OSINT-Übersicht](./README.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Historische Werkzeuge:** Das Twint-Repository wurde 2023 archiviert; die gezeigten Aufrufe sind keine Zusage aktueller Funktion mit X. Auch Facebook-Graph-Suchbeispiele und plattformspezifische Menüs sind historische Referenzen. Sherlock zeigt in der aktuellen Dokumentation den installierten CLI-Befehl und wiederholte `--site`-Optionen; die älteren Originalaufrufe bleiben erhalten. [Twint-Archiv](https://github.com/twintproject/twint), [Sherlock-Dokumentation](https://github.com/sherlock-project/sherlock).

> **Bewertung:** Anbieterklassifikation, Kosten und Datenabdeckung wurden aus der Vorlage übernommen. Ein gleichnamiger Treffer, eine ähnliche Telefonnummer oder ein gleiches Profilbild ist ein Recherchehinweis, kein Identitätsbeweis. Die Beispiele für Register beziehen sich überwiegend auf die USA. „Öffentlich verfügbar“ ersetzt keine Prüfung der anwendbaren Datenschutz- und Nutzungsregeln.
