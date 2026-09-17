# 📱 Social Media OSINT

> **Last verified:** 2026-09-17  
> **Scope:** Public information, authorized investigations, and applicable platform terms

Social platforms change APIs, markup, rate limits, and access rules frequently. Prefer documented APIs, public search, and manual verification; label archived scrapers as historical rather than presenting them as reliable tooling.

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
# Profile URL
https://x.com/username

# Search-engine pivots for public indexed content
site:x.com/username "search term"
site:x.com "exact phrase" after:2025-01-01
```

For automation, use the current documented X API or an approved data provider. **Twint is archived and has not been maintained since 2023**, so old `twint` commands should be treated as historical notes, not a dependable workflow.

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

## References and Handling

- [Sherlock](https://github.com/sherlock-project/sherlock)
- [Maigret](https://github.com/soxoj/maigret)
- [X developer platform](https://developer.x.com/)
- [Archived Twint repository](https://github.com/twintproject/twint)

Record source URLs and collection time, distinguish account matches from unverified correlations, and minimize collection of unrelated personal data.

---

**Back to OSINT:** [🔍 OSINT Overview](./README.md)
