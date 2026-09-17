# 📱 Social Media OSINT

---

## 🔍 Username Search Tools

| Tool | URL | Platforms |
|------|-----|-----------|
| **Sherlock** | github.com/sherlock-project | 300+ sites |
| **Maigret** | github.com/soxoj/maigret | 2500+ sites |
| **Namechk** | namechk.com | 100+ sites |
| **KnowEm** | knowem.com | 500+ sites |
| **WhatsMyName** | whatsmyname.app | Web-based |

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

## 📊 Platform-Specific OSINT

### Twitter/X
```bash
# Twint
twint -u username
twint -u username --followers
twint -s "search term" --since 2024-01-01

# Profile URL
twitter.com/username
x.com/username
```

### Instagram
```
# Profile: instagram.com/username
# Tools: Instaloader, Osintgram

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
# Tool: fb-friend-list-scraper
```

### TikTok
```
tiktok.com/@username
# Tool: TikTok-Scraper
```

### Reddit
```
reddit.com/user/username
# Tool: BDFR, Pushshift API
```

---

## 🛠️ Quick Commands

```bash
# Social Analyzer
social-analyzer --username "target" --metadata --extract

# All-in-one search
echo "username" | sherlock --print-all
```

---

## 📋 Social Media Checklist

```markdown
□ Twitter/X profile
□ Instagram profile  
□ Facebook profile
□ LinkedIn profile
□ TikTok account
□ Reddit account
□ GitHub profile
□ YouTube channel
□ Discord servers
□ Telegram groups
□ Dating profiles
□ Gaming profiles
```

---

**Back to OSINT:** [🔍 OSINT Overview](./README.md)
