# 🔍 Spickzettel für OSINT (Open Source Intelligence)

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Open Source Intelligence** – öffentlich verfügbare Informationen sammeln und analysieren.

---

## 📑 Inhaltsverzeichnis

| Thema | Beschreibung | Leitfaden |
|-------|-------------|-------|
| **Personensuche** | Informationen über Personen finden | [📄 Ansehen](./People-Search.de.md) |
| **E-Mail-OSINT** | E-Mail-Aufklärung und -Verifizierung | [📄 Ansehen](./Email-OSINT.de.md) |
| **Soziale Netzwerke** | Untersuchungen in sozialen Netzwerken | [📄 Ansehen](./Social-Media-OSINT.de.md) |
| **Domain und IP** | Aufklärung zu Domains und IP-Adressen | [📄 Ansehen](./Domain-IP-OSINT.de.md) |
| **Bild-OSINT** | Umgekehrte Bildersuche und Bildanalyse | [📄 Ansehen](./Image-OSINT.de.md) |

---

## 🎯 OSINT-Methodik

```
┌──────────────────────────────────────────────────────────────────┐
│ OSINT-LEBENSZYKLUS                                               │
│                                                                  │
│ 1. ANFORDERUNGEN  → Informationsbedarf festlegen                 │
│          ↓                                                       │
│ 2. SAMMLUNG      → Rohdaten aus Quellen sammeln                  │
│          ↓                                                       │
│ 3. AUFBEREITUNG  → Daten in nutzbares Format überführen          │
│          ↓                                                       │
│ 4. ANALYSE       → Ergebnisse bewerten und verknüpfen            │
│          ↓                                                       │
│ 5. WEITERGABE    → Erkenntnisse berichten und präsentieren       │
└──────────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Wichtige OSINT-Werkzeuge

### Suchmaschinen
| Werkzeug | URL | Zweck |
|------|-----|---------|
| **Google** | https://google.com | Allgemeine Suche |
| **Bing** | bing.com | Alternative Suche |
| **DuckDuckGo** | duckduckgo.com | Auf Datenschutz ausgerichtet |
| **Yandex** | yandex.com | Russische Suche (gut für Bilder laut Original) |
| **Baidu** | baidu.com | Chinesische Suche |

### Personensuche
| Werkzeug | URL | Zweck |
|------|-----|---------|
| **Pipl** | pipl.com | Vertiefte Personensuche |
| **Spokeo** | spokeo.com | Personensuche in den USA |
| **WhitePages** | whitepages.com | Telefonnummern- und Adresssuche |
| **ThatsThem** | thatsthem.com | Kostenlose Personensuche |
| **BeenVerified** | beenverified.com | Hintergrundprüfungen |

### E-Mail-Werkzeuge
| Werkzeug | URL | Zweck |
|------|-----|---------|
| **Hunter.io** | hunter.io | Unternehmens-E-Mail-Adressen ermitteln |
| **Have I Been Pwned** | haveibeenpwned.com | Auf Datenlecks prüfen |
| **EmailRep** | emailrep.io | Reputation von E-Mail-Adressen |
| **Phonebook.cz** | phonebook.cz | E-Mail- und Domainsuche |
| **h8mail** | GitHub | Suche nach Zugangsdaten aus Datenlecks |

### Soziale Netzwerke
| Werkzeug | URL | Zweck |
|------|-----|---------|
| **Sherlock** | GitHub | Suche nach Benutzernamen |
| **Social Searcher** | social-searcher.com | Suche in sozialen Netzwerken |
| **Namechk** | namechk.com | Verfügbarkeit von Benutzernamen |
| **KnowEm** | knowem.com | Benutzernamen prüfen |
| **Xquik** | https://github.com/Xquik-dev/x-twitter-scraper | Unabhängige Daten-API für X (Twitter) mit Suche, Follower-Export, Monitoren und MCP. |

### Domain-/IP-Werkzeuge
| Werkzeug | URL | Zweck |
|------|-----|---------|
| **Shodan** | shodan.io | Gerätesuche |
| **Censys** | censys.io | Internetweite Scans |
| **SecurityTrails** | securitytrails.com | DNS-Verlauf |
| **ViewDNS** | viewdns.info | DNS-Werkzeuge |
| **BuiltWith** | builtwith.com | Technologien ermitteln |

---

## 🔥 Befehle als Kurzreferenz

### theHarvester
```bash
# E-Mail-Adressen und Subdomains sammeln
theHarvester -d target.com -l 500 -b all

# Bestimmte Quellen
theHarvester -d target.com -b google,linkedin,twitter

# In Datei speichern
theHarvester -d target.com -b all -f results.html
```

### Sherlock (Suche nach Benutzernamen)
```bash
# Alle Plattformen durchsuchen
python3 sherlock.py username

# Bestimmte Websites
python3 sherlock.py username --site twitter instagram facebook

# Ausgabe in Datei schreiben
python3 sherlock.py username --output results.txt
```

### Maltego
```
# Transformationsarten:
- Person zu E-Mail-Adresse
- E-Mail-Adresse zu Domain
- Domain zu IP-Adresse
- IP-Adresse zu Standort
- Profile in sozialen Netzwerken
```

### Recon-ng
```bash
# recon-ng starten
recon-ng

# Arbeitsbereich erstellen
workspaces create target_name

# Module laden
modules load recon/domains-hosts/hackertarget
modules load recon/contacts-credentials/hibp_breach

# Ziel festlegen
options set SOURCE target.com

# Modul ausführen
run
```

---

## 📊 OSINT-Kategorien

### Passives OSINT
```
Keine direkte Interaktion mit dem Ziel:
- Suchmaschinen
- Soziale Netzwerke
- Öffentliche Register
- Archive.org
- WHOIS-Daten
```

### Aktives OSINT
```
Gewisse Interaktion mit dem Ziel:
- Portscans
- DNS-Abfragen
- Web-Crawling
- E-Mail-Verifizierung
```

---

## 🔒 Operative Sicherheit (OPSEC)

### Bewährte Vorgehensweisen
```
✓ VPN/Tor verwenden
✓ Getrennte Recherchekonten (Sock Puppets) erstellen
✓ Virtuelle Maschinen verwenden
✓ Keine persönlichen Konten verwenden
✓ Cookies und Cache regelmäßig löschen
✓ Getrennte Browserprofile verwenden
✓ Alles dokumentieren
```

### Recherchekonten (Sock Puppets)
```
- Generierte Identitäten verwenden
- Über Plattformen hinweg eine konsistente Rolle verwenden
- Ältere Konten (laut Original vertrauenswürdiger)
- Glaubwürdige Profildetails
- Keine Verbindung zur echten Identität herstellen
```

---

## 📋 OSINT-Checkliste

```markdown
## Ziel: [Name/Company/Domain]

### Personen
□ Varianten des vollständigen Namens
□ Telefonnummern
□ E-Mail-Adressen
□ Postanschriften
□ Profile in sozialen Netzwerken
□ Beruflicher Werdegang
□ Ausbildungsweg
□ Fotos und Bilder

### Unternehmen
□ Domainregistrierung (WHOIS)
□ Verwendete Technologien
□ Beschäftigtenliste
□ E-Mail-Adressformat
□ Präsenz in sozialen Netzwerken
□ Nachrichten und Pressemitteilungen
□ Finanzinformationen
□ Physische Standorte

### Technisches
□ IP-Adressen
□ Subdomains
□ Offene Ports und Dienste
□ DNS-Einträge
□ SSL-Zertifikate
□ Historische Daten
```

---

## 🔗 OSINT-Frameworks

### OSINT Framework
```
https://osintframework.com/
- Nach Kategorien geordnet
- Links zu Hunderten von Werkzeugen
- Visuelle Baumstruktur
```

### IntelTechniques
```
https://inteltechniques.com/tools/
- Werkzeuge von Michael Bazzell
- Spezielle Suchwerkzeuge
- Rechercheabläufe
```

---

## 📚 Verwandte Leitfäden in diesem Repository

- [Google Dorking](../../Google-Dorking/README.md)
- [GitHub Dorking](../../GitHub-Dorking/README.md)
- [Shodan](../../Shodan/README.md)
- [Amass](../../Amass/README.md)
- [Subfinder](../../Subfinder/README.md)

---

**Zurück zur Hauptübersicht:** [🔴 Hacking Cheatsheets](../../README.md)

---

## Hinweise zur deutschen Ausgabe

> **Begriffe und Beispielgrenzen:** OSINT wertet öffentlich zugängliche Informationen aus; *Enumeration* bezeichnet eine systematische Bestandsaufnahme. Die Einteilung in aktiv und passiv hängt davon ab, wer kontaktiert wird: Suchabfragen können Spuren bei Plattformen hinterlassen, DNS-Abfragen beim Resolver oder autoritativen Server. VPN/Tor garantiert keine Anonymität. Treffer zu gleichen Benutzernamen beweisen keine gemeinsame Identität. Die aufgeführten Anbieter, Datenquellen, Preise und Abdeckungszahlen stammen aus der Vorlage und sind keine aktuell verifizierte Empfehlung.

> **Sherlock-Versionen:** Die Vorlage zeigt ältere Python-Dateipfade. Die aktuelle Projektdokumentation verwendet den installierten CLI-Befehl `sherlock`; mehrere Websites werden mit wiederholten `--site`-Optionen angegeben. Die Originalaufrufe bleiben unverändert. [Sherlock-Dokumentation](https://github.com/sherlock-project/sherlock).
