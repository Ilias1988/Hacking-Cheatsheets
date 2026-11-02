# ☁️ Fiche pratique sur le contournement de Cloudflare

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗██╗      ██████╗ ██╗   ██╗██████╗ ███████╗██╗      █████╗ ██████╗ ███████╗
  ██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗██╔════╝██║     ██╔══██╗██╔══██╗██╔════╝
  ██║     ██║     ██║   ██║██║   ██║██║  ██║█████╗  ██║     ███████║██████╔╝█████╗  
  ██║     ██║     ██║   ██║██║   ██║██║  ██║██╔══╝  ██║     ██╔══██║██╔══██╗██╔══╝  
  ╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝██║     ███████╗██║  ██║██║  ██║███████╗
   ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
                    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
                    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
                    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
                    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
                    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
                    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

> **Repères de version :** les plages IP, services, requêtes de recherche et outils sont ceux du document source. La première liste utilise encore `104.16.0.0/12`, alors que la [liste officielle Cloudflare](https://www.cloudflare.com/ips/) documente des plages plus précises. Les deux listes sources restent intactes ; ce n’est pas un inventaire universel ni garanti à jour. Le dessin ASCII conserve le texte source.
>
> **Interprétation :** une IP, un certificat, un favicon, une trace DNS ou un serveur de messagerie fournit une piste, pas une preuve que l’adresse héberge l’origine actuelle. Une réponse semblable ne suffit pas, seule, à établir cette correspondance. Avec HTTPS, remplacer l’en-tête HTTP `Host` n’est pas équivalent à changer le nom TLS SNI ; `-k` désactive la vérification du certificat, pas ces différences de routage. Les commandes originales sont conservées.
>
> **Limites des contournements :** les [en-têtes Cloudflare](https://developers.cloudflare.com/fundamentals/reference/http-headers/) sont produits ou normalisés sur le chemin du proxy. Envoyer soi-même un en-tête tel que `CF-Connecting-IP` ne garantit pas de modifier l’identité retenue par la protection. Un agent utilisateur, un outil d’automatisation ou un solveur ne garantit pas de franchir un challenge ; la [documentation officielle des challenges](https://developers.cloudflare.com/cloudflare-challenges/troubleshooting/challenge-solve-issues/) décrit notamment les signaux de robot et les différences de navigateur. Aucune nouvelle méthode n’est ajoutée aux exemples.

## 🎯 Repérer Cloudflare

### En-têtes
```bash
curl -I https://target.com

# Rechercher les éléments suivants :
cf-ray: XXXXX-YYY
server: cloudflare
cf-cache-status: DYNAMIC
cf-request-id: XXXX
```

### DNS
```bash
dig target.com

# Plages IP Cloudflare :
# 104.16.0.0/12
# 172.64.0.0/13
# 131.0.72.0/22
# 173.245.48.0/20
# 103.21.244.0/22
# 103.22.200.0/22
# 103.31.4.0/22
```

---

## 🌐 Rechercher l’adresse IP du serveur d’origine

### 1. Outils d’historique DNS

| Service | URL |
|---------|-----|
| SecurityTrails | https://securitytrails.com |
| ViewDNS | https://viewdns.info/iphistory |
| DNSDumpster | https://dnsdumpster.com |
| CrimeFlare | http://www.crimeflare.org:82/cfs.html |
| CloudFlair | Outil disponible sur GitHub |

```bash
# Avec CloudFlair
python3 cloudflair.py target.com

# Bypass-Firewalls-by-DNS-History
./bypass-firewalls-by-DNS-history.sh target.com
```

### 2. Découverte de sous-domaines
```bash
# Certains sous-domaines peuvent révéler le serveur d’origine
subfinder -d target.com | httpx -ip

# Exemples de sous-domaines pouvant pointer vers l’origine :
mail.target.com
ftp.target.com
cpanel.target.com
webmail.target.com
direct.target.com
origin.target.com
server.target.com
api.target.com
stage.target.com
dev.target.com
old.target.com
```

### 3. Recherche de certificats SSL
```bash
# Recherche sur Censys.io
# Requête : parsed.names: target.com AND NOT ip: 104.*

# Journaux Certificate Transparency
curl -s "https://crt.sh/?q=%.target.com&output=json" | jq -r '.[].common_name' | sort -u

# Rechercher avec Shodan
shodan search "ssl.cert.subject.cn:target.com"
```

### 4. Analyse des courriels
```bash
# Envoyer un courriel à une adresse inexistante
# Examiner les en-têtes du message de non-remise

# Ou s’inscrire à une lettre d’information
# Examiner les en-têtes Received: pour y rechercher l’IP d’origine

# Examiner les en-têtes suivants :
Received: from [ORIGIN_IP]
X-Originating-IP: [IP]
X-PM-Message-Id: (sometimes contains origin)
```

### 5. Empreinte du favicon (Shodan)
```python
import mmh3
import requests
import codecs

# Calculer l’empreinte du favicon
response = requests.get('https://target.com/favicon.ico')
favicon = codecs.encode(response.content, 'base64')
hash = mmh3.hash(favicon)
print(f"Favicon hash: {hash}")

# Rechercher dans Shodan : http.favicon.hash:{hash}
```

```bash
# Interface en ligne de commande de Shodan
shodan search "http.favicon.hash:HASH_HERE"
```

### 6. Historique WHOIS
```bash
# Examiner l’historique WHOIS pour retrouver d’anciennes IP
# whoxy.com
# domaintools.com
```

### 7. Code source du site web
```bash
# Rechercher des IP codées en dur dans :
# - Les fichiers JavaScript
# - Les commentaires HTML
# - Les points de terminaison des API
# - Les messages d’erreur

curl -s https://target.com | grep -oE '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'

# Examiner les fichiers JavaScript
curl -s https://target.com/main.js | grep -oE 'https?://[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'
```

### 8. Enregistrements MX
```bash
dig MX target.com

# Le serveur de messagerie peut partager l’IP du serveur d’origine
nslookup mail.target.com
```

### 9. Enregistrements SPF
```bash
dig TXT target.com | grep spf

# L’enregistrement SPF peut contenir l’IP du serveur d’origine
# v=spf1 ip4:1.2.3.4 include:_spf.google.com ~all
```

---

## 🔥 Connexion directe

### Tester l’adresse IP trouvée
```bash
# Après avoir identifié une IP d’origine potentielle :
curl -k -H "Host: target.com" https://ORIGIN_IP/

# Ou modifier /etc/hosts
echo "ORIGIN_IP target.com" >> /etc/hosts

# Puis naviguer normalement
curl https://target.com
```

### Vérifier le serveur d’origine
```bash
# Vérifier si le contenu est identique
diff <(curl -s https://target.com) <(curl -sk -H "Host: target.com" https://ORIGIN_IP/)

# Examiner le certificat SSL
openssl s_client -connect ORIGIN_IP:443 -servername target.com
```

---

## 🛡️ Contournement du WAF Cloudflare

### Contournement de la limitation du débit
```http
# Faire varier les en-têtes suivants
X-Forwarded-For: RANDOM_IP
CF-Connecting-IP: RANDOM_IP
True-Client-IP: RANDOM_IP
X-Real-IP: RANDOM_IP
```

### Contournement de la protection contre les robots
```bash
# Utiliser l’agent utilisateur d’un véritable navigateur
curl -A "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" https://target.com

# Utiliser curl-impersonate
curl_chrome116 https://target.com
```

### Contournement de Turnstile et des challenges
```bash
# Utiliser l’automatisation du navigateur
# - Puppeteer + stealth plugin
# - Playwright
# - undetected-chromedriver

# FlareSolverr (Docker)
docker run -p 8191:8191 flaresolverr/flaresolverr
```

---

## 🛠️ Outils

| Outil | Utilité |
|------|---------|
| **CloudFlair** | Rechercher l’origine avec Censys |
| **CrimeFlare** | Base de données d’IP d’origine |
| **Bypass-Firewalls-by-DNS-History** | Examiner l’historique DNS |
| **FlareSolverr** | Outil de résolution des challenges de navigateur |
| **curl-impersonate** | Empreinte TLS de navigateur |
| **CloudBunny** | Outil de contournement de Cloudflare |

### Utilisation de CloudFlair
```bash
git clone https://github.com/christophetd/CloudFlair
cd CloudFlair
pip install -r requirements.txt

# Définir les identifiants de l’API Censys
export CENSYS_API_ID=YOUR_ID
export CENSYS_API_SECRET=YOUR_SECRET

python cloudflair.py target.com
```

---

## 📊 Référence rapide

### Liste de contrôle de l’IP d’origine
```markdown
- [ ] Historique DNS (SecurityTrails, ViewDNS)
- [ ] Sous-domaines (mail, ftp, cpanel, dev)
- [ ] Certificats SSL (Censys, crt.sh)
- [ ] Shodan (empreinte du favicon, SSL)
- [ ] En-têtes de courriels
- [ ] Enregistrements MX/SPF
- [ ] Analyse du code source
- [ ] Historique WHOIS
- [ ] Comparaison des plages IP (hors des plages Cloudflare)
```

### Plages IP Cloudflare
```
173.245.48.0/20
103.21.244.0/22
103.22.200.0/22
103.31.4.0/22
141.101.64.0/18
108.162.192.0/18
190.93.240.0/20
188.114.96.0/20
197.234.240.0/22
198.41.128.0/17
162.158.0.0/15
104.16.0.0/13
104.24.0.0/14
172.64.0.0/13
131.0.72.0/22
```

---

## 📚 Ressources

- [Plages IP Cloudflare](https://www.cloudflare.com/ips/)
- [Outil CloudFlair](https://github.com/christophetd/CloudFlair)
- [CrimeFlare](http://www.crimeflare.org:82/cfs.html)
- [FlareSolverr](https://github.com/FlareSolverr/FlareSolverr)

---

<p align="center">
  <b>☁️ Recherchez le serveur d’origine !</b><br>
  <i>Uniquement pour des tests autorisés !</i>
</p>
