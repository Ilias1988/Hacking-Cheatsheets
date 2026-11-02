# 📱 OSINT de redes sociais

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Social-Media-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Social-Media-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Notas de versão:** As quantidades de sites são valores do original, não contagens verificadas. O [Twint está arquivado desde março de 2023](https://github.com/twintproject/twint); os exemplos são históricos. Os comandos antigos de Sherlock e a passagem de um nome por stdin devem ser comparados com a [sintaxe documentada](https://sherlockproject.xyz/usage), que recebe os nomes como argumentos. A opção `--output` destina-se à pesquisa de um único nome de utilizador; pesquisas múltiplas têm opções próprias. A disponibilidade de funcionalidades, autenticação e API depende de cada plataforma; um resultado com o mesmo nome de utilizador não confirma que pertença à mesma pessoa.

---

## 🔍 Ferramentas de pesquisa de nomes de utilizador

| Ferramenta | URL | Plataformas |
|------|-----|-----------|
| **Sherlock** | github.com/sherlock-project | Mais de 300 sites |
| **Maigret** | github.com/soxoj/maigret | Mais de 2500 sites |
| **Namechk** | namechk.com | Mais de 100 sites |
| **KnowEm** | knowem.com | Mais de 500 sites |
| **WhatsMyName** | whatsmyname.app | No navegador |

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

# URL do perfil
twitter.com/username
x.com/username
```

### Instagram
```
# Perfil: instagram.com/username
# Ferramentas: Instaloader, Osintgram

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
# Ferramenta: fb-friend-list-scraper
```

### TikTok
```
tiktok.com/@username
# Ferramenta: TikTok-Scraper
```

### Reddit
```
reddit.com/user/username
# Ferramenta: BDFR, API Pushshift
```

---

## 🛠️ Comandos rápidos

```bash
# Social Analyzer
social-analyzer --username "target" --metadata --extract

# Pesquisa integrada
echo "username" | sherlock --print-all
```

---

## 📋 Lista de verificação de redes sociais

```markdown
□ Perfil de Twitter/X
□ Perfil de Instagram  
□ Perfil de Facebook
□ Perfil de LinkedIn
□ Conta de TikTok
□ Conta de Reddit
□ Perfil de GitHub
□ Canal de YouTube
□ Servidores de Discord
□ Grupos de Telegram
□ Perfis em plataformas de encontros
□ Perfis de jogos
```

---

**Voltar a OSINT:** [🔍 Visão geral de OSINT](README.pt.md)
