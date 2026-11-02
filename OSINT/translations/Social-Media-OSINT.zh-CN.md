# 📱 社交媒体 OSINT

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Social-Media-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Social-Media-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **版本说明：** [Twint 原仓库](https://github.com/twintproject/twint) 已归档，旧命令不能视为当前 X 服务上可用的保证。Sherlock 示例中的 `python3 sherlock` 是旧调用形式，现代安装方式使用 `sherlock` 入口，参见 [官方文档](https://sherlockproject.xyz/installation)。工具支持的网站数量保留原文数字，实际覆盖率及接口可用性会变化。用户名相同也不能单独证明账户属于同一人。

---

## 🔍 用户名搜索工具

| 工具 | URL | 平台 |
|------|-----|-----------|
| **Sherlock** | github.com/sherlock-project | 300+ 个网站 |
| **Maigret** | github.com/soxoj/maigret | 2500+ 个网站 |
| **Namechk** | namechk.com | 100+ 个网站 |
| **KnowEm** | knowem.com | 500+ 个网站 |
| **WhatsMyName** | whatsmyname.app | 基于网页 |

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

## 📊 按平台开展 OSINT

### Twitter/X
```bash
# Twint
twint -u username
twint -u username --followers
twint -s "search term" --since 2024-01-01

# 个人资料 URL
twitter.com/username
x.com/username
```

### Instagram
```
# 个人资料：instagram.com/username
# 工具：Instaloader、Osintgram

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
# 工具：fb-friend-list-scraper
```

### TikTok
```
tiktok.com/@username
# 工具：TikTok-Scraper
```

### Reddit
```
reddit.com/user/username
# 工具：BDFR、Pushshift API
```

---

## 🛠️ 常用命令

```bash
# Social Analyzer
social-analyzer --username "target" --metadata --extract

# 综合搜索
echo "username" | sherlock --print-all
```

---

## 📋 社交媒体检查清单

```markdown
□ Twitter/X 个人资料
□ Instagram 个人资料  
□ Facebook 个人资料
□ LinkedIn 个人资料
□ TikTok 账户
□ Reddit 账户
□ GitHub 个人资料
□ YouTube 频道
□ Discord 服务器
□ Telegram 群组
□ 交友资料
□ 游戏资料
```

---

**返回 OSINT:** [🔍 OSINT 概览](./README.zh-CN.md)
