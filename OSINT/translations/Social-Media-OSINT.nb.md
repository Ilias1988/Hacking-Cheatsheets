# 📱 OSINT i sosiale medier

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Social-Media-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Social-Media-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 🔍 Verktøy for søk etter brukernavn

| Verktøy | URL | Plattformer |
|------|-----|-----------|
| **Sherlock** | github.com/sherlock-project | 300+ nettsteder |
| **Maigret** | github.com/soxoj/maigret | 2500+ nettsteder |
| **Namechk** | namechk.com | 100+ nettsteder |
| **KnowEm** | knowem.com | 500+ nettsteder |
| **WhatsMyName** | whatsmyname.app | Nettbasert |

### Sherlock

> **Versjonsmerknad:** Antall nettsteder i tabellen er originalens tall og endres over tid. Eksemplene med `python3 sherlock` viser et eldre oppsett; se [Sherlock-prosjektets dokumentasjon](https://github.com/sherlock-project/sherlock). Et likt brukernavn på flere nettsteder beviser ikke felles eier.
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

## 📊 OSINT for bestemte plattformer

### Twitter/X

> **Versjonsmerknad:** [Twint-repoet](https://github.com/twintproject/twint) ble arkivert 30. mars 2023. Kommandoene nedenfor er historiske eksempler, ikke bekreftet fungerende innsamling mot dagens X. Tilgang til øvrige plattformer og API-er avhenger også av versjon og tilgangsvilkår.
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
# Verktøy: Instaloader, Osintgram

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
# Verktøy: fb-friend-list-scraper
```

### TikTok
```
tiktok.com/@username
# Verktøy: TikTok-Scraper
```

### Reddit
```
reddit.com/user/username
# Verktøy: BDFR, Pushshift API
```

---

## 🛠️ Hurtigkommandoer

```bash
# Social Analyzer
social-analyzer --username "target" --metadata --extract

# Samlet søk
echo "username" | sherlock --print-all
```

---

## 📋 Sjekkliste for sosiale medier

```markdown
□ Twitter/X-profil
□ Instagram-profil
□ Facebook-profil
□ LinkedIn-profil
□ TikTok-konto
□ Reddit-konto
□ GitHub-profil
□ YouTube-kanal
□ Discord-servere
□ Telegram-grupper
□ Datingprofiler
□ Spillprofiler
```

---

**Tilbake til OSINT:** [🔍 OSINT-oversikt](./README.nb.md)
