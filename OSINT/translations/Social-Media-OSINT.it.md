# 📱 OSINT sui social media

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Social-Media-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Social-Media-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Nota sulle versioni:** il [repository Twint](https://github.com/twintproject/twint) è archiviato dal 2023; gli esempi sono storici e non garantiscono compatibilità con X. Anche i comandi Sherlock vanno confrontati con la [documentazione ufficiale](https://sherlockproject.xyz/installation). I conteggi dei siti supportati sono quelli dell’originale e possono cambiare.

---

## 🔍 Strumenti per la ricerca di nomi utente

| Strumento | URL | Piattaforme |
|------|-----|-----------|
| **Sherlock** | github.com/sherlock-project | Oltre 300 siti |
| **Maigret** | github.com/soxoj/maigret | Oltre 2500 siti |
| **Namechk** | namechk.com | Oltre 100 siti |
| **KnowEm** | knowem.com | Oltre 500 siti |
| **WhatsMyName** | whatsmyname.app | Accessibile dal browser |

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

## 📊 OSINT specifica per piattaforma

### Twitter/X
```bash
# Twint
twint -u username
twint -u username --followers
twint -s "search term" --since 2024-01-01

# URL del profilo
twitter.com/username
x.com/username
```

### Instagram
```
# Profilo: instagram.com/username
# Strumenti: Instaloader, Osintgram

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
# Strumento: fb-friend-list-scraper
```

### TikTok
```
tiktok.com/@username
# Strumento: TikTok-Scraper
```

### Reddit
```
reddit.com/user/username
# Strumenti: BDFR, Pushshift API
```

---

## 🛠️ Comandi rapidi

```bash
# Social Analyzer
social-analyzer --username "target" --metadata --extract

# Ricerca integrata
echo "username" | sherlock --print-all
```

---

## 📋 Checklist sui social media

```markdown
□ Profilo Twitter/X
□ Profilo Instagram  
□ Profilo Facebook
□ Profilo LinkedIn
□ Account TikTok
□ Account Reddit
□ Profilo GitHub
□ Canale YouTube
□ Server Discord
□ Gruppi Telegram
□ Profili sui siti di incontri
□ Profili sulle piattaforme di videogiochi
```

---

**Torna a OSINT:** [🔍 Panoramica OSINT](./README.it.md)
