# 📱 OSINT de redes sociales

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Social-Media-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Social-Media-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Aclaración de versión:** Los recuentos de sitios y comandos son los del original y pueden variar. [Twint](https://github.com/twintproject/twint) está archivado desde 2023; las API y restricciones de las plataformas pueden impedir estos ejemplos. Un mismo nombre de usuario no prueba una identidad común.


---

## 🔍 Herramientas de búsqueda de nombres de usuario

| Herramienta | URL | Plataformas |
|------|-----|-----------|
| **Sherlock** | github.com/sherlock-project | Más de 300 sitios |
| **Maigret** | github.com/soxoj/maigret | Más de 2500 sitios |
| **Namechk** | namechk.com | Más de 100 sitios |
| **KnowEm** | knowem.com | Más de 500 sitios |
| **WhatsMyName** | whatsmyname.app | Desde el navegador |

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

## 📊 OSINT específico de cada plataforma

### Twitter/X
```bash
# Twint
twint -u username
twint -u username --followers
twint -s "search term" --since 2024-01-01

# URL del perfil
twitter.com/username
x.com/username
```

### Instagram
```
# Perfil: instagram.com/username
# Herramientas: Instaloader, Osintgram

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
# Herramienta: fb-friend-list-scraper
```

### TikTok
```
tiktok.com/@username
# Herramienta: TikTok-Scraper
```

### Reddit
```
reddit.com/user/username
# Herramienta: BDFR, Pushshift API
```

---

## 🛠️ Comandos rápidos

```bash
# Social Analyzer
social-analyzer --username "target" --metadata --extract

# Búsqueda integrada
echo "username" | sherlock --print-all
```

---

## 📋 Lista de comprobación de redes sociales

```markdown
□ Perfil de Twitter/X
□ Perfil de Instagram  
□ Perfil de Facebook
□ Perfil de LinkedIn
□ Cuenta de TikTok
□ Cuenta de Reddit
□ Perfil de GitHub
□ Canal de YouTube
□ Servidores de Discord
□ Grupos de Telegram
□ Perfiles de citas
□ Perfiles de videojuegos
```

---

**Volver a OSINT:** [🔍 Índice de OSINT](./README.es.md)
