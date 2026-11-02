# 📱 OSINT sur les réseaux sociaux

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Social-Media-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Social-Media-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 🔍 Outils de recherche par nom d’utilisateur

| Outil | URL | Plateformes |
|------|-----|-----------|
| **Sherlock** | github.com/sherlock-project | 300+ sites |
| **Maigret** | github.com/soxoj/maigret | 2500+ sites |
| **Namechk** | namechk.com | 100+ sites |
| **KnowEm** | knowem.com | 500+ sites |
| **WhatsMyName** | whatsmyname.app | Dans le navigateur |

### Sherlock

> **Note de version :** les appels `python3 sherlock` ou `sherlock.py` reflètent les exemples historiques du document source. Vérifier la [documentation officielle d’installation](https://sherlockproject.xyz/installation) pour la version utilisée.
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

## 📊 OSINT par plateforme

### Twitter/X

> **Note historique :** les exemples Twint sont conservés, mais le [dépôt officiel](https://github.com/twintproject/twint) est archivé. Leur fonctionnement avec X actuel n’est pas garanti.
```bash
# Twint
twint -u username
twint -u username --followers
twint -s "search term" --since 2024-01-01

# URL du profil
twitter.com/username
x.com/username
```

### Instagram
```
# Profil : instagram.com/username
# Outils : Instaloader, Osintgram

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
# Outil : fb-friend-list-scraper
```

### TikTok
```
tiktok.com/@username
# Outil : TikTok-Scraper
```

### Reddit
```
reddit.com/user/username
# Outils : BDFR, API Pushshift
```

---

## 🛠️ Commandes rapides

```bash
# Social Analyzer
social-analyzer --username "target" --metadata --extract

# Recherche tout-en-un
echo "username" | sherlock --print-all
```

---

## 📋 Liste de contrôle des réseaux sociaux

```markdown
□ Profil Twitter/X
□ Profil Instagram  
□ Profil Facebook
□ Profil LinkedIn
□ Compte TikTok
□ Compte Reddit
□ Profil GitHub
□ Chaîne YouTube
□ Serveurs Discord
□ Groupes Telegram
□ Profils sur les sites de rencontre
□ Profils de jeux vidéo
```

---

**Retour à l’OSINT :** [🔍 Vue d’ensemble de l’OSINT](./README.fr.md)
