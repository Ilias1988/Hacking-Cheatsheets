# 📱 OSINT в социальных сетях

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Social-Media-OSINT.md) • [🇷🇺 Русский раздел](../../README.ru.md)

<p align="center">
  <a href="Social-Media-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Social-Media-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 🔍 Инструменты поиска по имени пользователя

| Инструмент | URL | Платформы |
| ------ | ----- | ----------- |
| **Sherlock** | github.com/sherlock-project | 300+ сайтов |
| **Maigret** | github.com/soxoj/maigret | 2500+ сайтов |
| **Namechk** | namechk.com | 100+ сайтов |
| **KnowEm** | knowem.com | 500+ сайтов |
| **WhatsMyName** | whatsmyname.app | Веб-интерфейс |

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

## 📊 OSINT с учётом платформы

### Twitter/X

> **Устаревшие примеры:** [Twint](https://github.com/twintproject/twint) архивирован; его команды сохранены для соответствия оригиналу. Варианты запуска Sherlock также зависят от структуры установленной версии; см. [официальную документацию](https://sherlockproject.xyz/installation). Число поддерживаемых сайтов в таблице — данные оригинала, а не актуальная гарантия.

```bash
# Twint
twint -u username
twint -u username --followers
twint -s "search term" --since 2024-01-01

# URL профиля
twitter.com/username
x.com/username
```

### Instagram
```
# Профиль: instagram.com/username
# Инструменты: Instaloader, Osintgram

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
# Инструмент: fb-friend-list-scraper
```

### TikTok
```
tiktok.com/@username
# Инструмент: TikTok-Scraper
```

### Reddit
```
reddit.com/user/username
# Инструменты: BDFR, Pushshift API
```

---

## 🛠️ Краткий справочник команд

```bash
# Social Analyzer
social-analyzer --username "target" --metadata --extract

# Общий поиск
echo "username" | sherlock --print-all
```

---

## 📋 Контрольный список социальных сетей

```markdown
□ Профиль Twitter/X
□ Профиль Instagram
□ Профиль Facebook
□ Профиль LinkedIn
□ Аккаунт TikTok
□ Аккаунт Reddit
□ Профиль GitHub
□ Канал YouTube
□ Серверы Discord
□ Группы Telegram
□ Профили на сайтах знакомств
□ Игровые профили
```

---

**К разделу OSINT:** [🔍 Обзор OSINT](./README.ru.md)
