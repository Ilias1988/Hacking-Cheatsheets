# 📱 OSINT zu sozialen Netzwerken

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Social-Media-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Social-Media-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 🔍 Werkzeuge zur Suche nach Benutzernamen

| Werkzeug | URL | Plattformen |
|------|-----|-----------|
| **Sherlock** | github.com/sherlock-project | 300+ Websites |
| **Maigret** | github.com/soxoj/maigret | 2500+ Websites |
| **Namechk** | namechk.com | 100+ Websites |
| **KnowEm** | knowem.com | 500+ Websites |
| **WhatsMyName** | whatsmyname.app | Webbasiert |

### Sherlock
```bash
python3 sherlock username
python3 sherlock user1 user2 --output results.txt
```

### Maigret
```bash
maigret username
maigret username --tor --json results.json
```

---

## 📊 Plattformspezifisches OSINT

### Twitter/X
```bash
# Twint
twint -u username
twint -u username --followers
twint -s "search term" --since 2024-01-01

# Profil-URL
twitter.com/username
x.com/username
```

### Instagram
```
# Profil: instagram.com/username
# Werkzeuge: Instaloader, Osintgram

# Instaloader
pip3 install instaloader
instaloader profile username --no-videos
```

### LinkedIn
```
site:linkedin.com/in "John Smith"
site:linkedin.com/in "John Smith" "Company"
```

### Facebook
```
facebook.com/username
facebook.com/search/people/?q=name
# Werkzeug: fb-friend-list-scraper
```

### TikTok
```
tiktok.com/@username
# Werkzeug: TikTok-Scraper
```

### Reddit
```
reddit.com/user/username
# Werkzeug: BDFR, Pushshift API
```

---

## 🛠️ Schnellbefehle

```bash
# Social Analyzer
social-analyzer --username "target" --metadata --extract

# Alles in einem Durchlauf suchen
echo "username" | sherlock --print-all
```

---

## 📋 Checkliste für soziale Netzwerke

```markdown
□ Twitter-/X-Profil
□ Instagram-Profil  
□ Facebook-Profil
□ LinkedIn-Profil
□ TikTok-Konto
□ Reddit-Konto
□ GitHub-Profil
□ YouTube-Kanal
□ Discord-Server
□ Telegram-Gruppen
□ Dating-Profile
□ Gaming-Profile
```

---

**Zurück zu OSINT:** [🔍 OSINT-Übersicht](./README.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Versionshinweise:** Twint ist seit 2023 archiviert; seine Beispiele können mit aktuellen X-Schnittstellen nicht funktionieren. Die Website-Zahlen sind historische Angaben. Sherlock verwendet heute einen installierten CLI-Einstieg; die Optionen `--site` werden für mehrere Websites wiederholt. Der letzte Pipeline-Aufruf übergibt den Benutzernamen nur an die Standardeingabe und setzt voraus, dass die verwendete Version diese unterstützt; die aktuelle dokumentierte Syntax erwartet positionale Benutzernamen. [Twint-Archiv](https://github.com/twintproject/twint), [Sherlock-Dokumentation](https://github.com/sherlock-project/sherlock).

> **Ergebnisgrenzen:** Plattformen können Zugriffe einschränken, Anmeldungen verlangen oder Suchschnittstellen ändern. Eine als verwendet gemeldete Kennung beweist nicht, dass alle gefundenen Konten derselben Person gehören. Profil-URLs und Suchabfragen bleiben wörtlich erhalten; es wurden keine realen Profile aufgerufen.
