[🇬🇧 English](../Social-Media-OSINT.md)

# 📱 Sosyal Medya OSINT

---

## 🔍 Kullanıcı Adı Arama Araçları

| Araç | URL | Kapsanan Platform Sayısı |
|------|-----|-------------------------|
| **Sherlock** | github.com/sherlock-project | 300+ platform |
| **Maigret** | github.com/soxoj/maigret | 2500+ platform |
| **Namechk** | namechk.com | 100+ platform |
| **KnowEm** | knowem.com | 500+ platform |
| **WhatsMyName** | whatsmyname.app | Web tabanlı |

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

## 📊 Platforma Özel OSINT

### Twitter / X
```bash
# Twint
twint -u username
twint -u username --followers
twint -s "search term" --since 2024-01-01

# Profil URL yapısı
twitter.com/username
x.com/username
```

### Instagram
```
# Profil: instagram.com/username
# Araçlar: Instaloader, Osintgram

# Instaloader
pip3 install instaloader
instaloader profile username --no-videos
```

### LinkedIn
```
site:linkedin.com/in "Ahmet Yılmaz"
site:linkedin.com/in "Ahmet Yılmaz" "Şirket Adı"
```

### Facebook
```
facebook.com/username
facebook.com/search/people/?q=ad%20soyad
# Araç: fb-friend-list-scraper
```

### TikTok
```
tiktok.com/@username
# Araç: TikTok-Scraper
```

### Reddit
```
reddit.com/user/username
# Araç: BDFR, Pushshift API
```

---

## 🛠️ Hızlı Komutlar

```bash
# Social Analyzer
social-analyzer --username "target" --metadata --extract

# Tek satırda arama
echo "username" | sherlock --print-all
```

---

## 📋 Sosyal Medya Kontrol Listesi

```markdown
□ Twitter/X profili
□ Instagram profili  
□ Facebook profili
□ LinkedIn profili
□ TikTok hesabı
□ Reddit hesabı
□ GitHub profili
□ YouTube kanalı
□ Discord sunucuları
□ Telegram grupları
□ Flört (dating) profilleri
□ Oyun (gaming/Steam vb.) profilleri
```

---

**OSINT Ana Sayfasına Dön:** [🔍 OSINT Genel Bakış](./README.tr.md)
