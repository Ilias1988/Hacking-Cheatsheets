# 📧 Fiche pratique d’OSINT sur les adresses électroniques

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Email-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Email-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

```
███████╗███╗   ███╗ █████╗ ██╗██╗     
██╔════╝████╗ ████║██╔══██╗██║██║     
█████╗  ██╔████╔██║███████║██║██║     
██╔══╝  ██║╚██╔╝██║██╔══██║██║██║     
███████╗██║ ╚═╝ ██║██║  ██║██║███████╗
╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚══════╝
```

---

## 🔍 Outils de découverte d’adresses électroniques

| Outil | URL | Utilité |
|------|-----|---------|
| **Hunter.io** | hunter.io | Recherche d’adresses professionnelles |
| **Phonebook.cz** | phonebook.cz | Recherche gratuite d’adresses électroniques |
| **Snov.io** | snov.io | Recherche d’adresses électroniques |
| **Clearbit Connect** | clearbit.com | Enrichissement des informations sur les adresses |
| **RocketReach** | rocketreach.co | Recherche de contacts |
| **Voila Norbert** | voilanorbert.com | Recherche d’adresses électroniques |

---

## 🛠️ Commandes d’énumération des adresses électroniques

### theHarvester
```bash
# Collecte d’adresses électroniques
theHarvester -d target.com -l 500 -b all
theHarvester -d target.com -b google,linkedin,bing
theHarvester -d target.com -b all -f emails.html
```

### Hunter.io en ligne de commande
```bash
# Requête d’API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Vérification d’une adresse électronique
curl "https://api.hunter.io/v2/email-verifier?email=test@target.com&api_key=YOUR_KEY"
```

### h8mail (recherche dans les fuites de données)
```bash
pip3 install h8mail
h8mail -t target@email.com
h8mail -t target@email.com -c h8mail_config.ini
```

### holehe (vérification de l’inscription aux services)
```bash
pip3 install holehe
holehe target@email.com
holehe target@email.com --only-used
```

---

## 📧 Formats d’adresses électroniques

```bash
# Formats courants en entreprise
first.last@company.com     # john.smith@company.com
flast@company.com          # jsmith@company.com
firstl@company.com         # johns@company.com
first_last@company.com     # john_smith@company.com
first@company.com          # john@company.com
last.first@company.com     # smith.john@company.com
```

### Générateur de variantes d’adresses électroniques
```python
# Générer des variantes d’adresses électroniques
names = ["john", "smith"]
domain = "company.com"

patterns = [
    f"{names[0]}.{names[1]}@{domain}",
    f"{names[0][0]}{names[1]}@{domain}",
    f"{names[0]}{names[1][0]}@{domain}",
    f"{names[0]}_{names[1]}@{domain}",
    f"{names[0]}@{domain}",
]
```

---

## 🔐 Recherche de fuites de données

| Service | URL | Type |
|---------|-----|------|
| **Have I Been Pwned** | haveibeenpwned.com | Gratuit |
| **DeHashed** | dehashed.com | Payant |
| **LeakCheck** | leakcheck.io | Payant |
| **Snusbase** | snusbase.com | Payant |
| **IntelX** | intelx.io | Freemium (offre gratuite et options payantes) |

### API HIBP
```bash
curl -H "hibp-api-key: YOUR_KEY" \
  "https://haveibeenpwned.com/api/v3/breachedaccount/test@example.com"
```

---

## ✅ Vérification des adresses électroniques

### Méthodes de vérification
```
1. Vérification MX — Le domaine possède un serveur de messagerie
2. Vérification SMTP — La boîte aux lettres existe
3. Vérification syntaxique — Le format de l’adresse est valide
4. Détection des adresses jetables — L’adresse n’est pas temporaire
5. Détection des adresses de fonction — L’adresse n’est pas générique (info@, admin@)
```

### Vérification SMTP simple

> **Précision :** un code SMTP `250` ne prouve pas à lui seul l’existence d’une boîte ; `550` peut également traduire un refus de politique. Voir la [RFC 5321, §3.3](https://www.rfc-editor.org/rfc/rfc5321.html#section-3.3).
```bash
# Vérifier les enregistrements MX
nslookup -type=mx target.com
dig mx target.com

# Vérification avec Telnet
telnet mail.target.com 25
HELO test.com
MAIL FROM:<test@test.com>
RCPT TO:<target@target.com>
# 250 = existe, 550 = n’existe pas (interprétation simplifiée du document source)
```

---

## 📋 Liste de contrôle de l’OSINT sur les adresses électroniques

```markdown
## Adresse cible : [email@domain.com]

□ Vérifier l’existence de l’adresse (SMTP/Hunter)
□ Rechercher des fuites de données (HIBP)
□ Rechercher les comptes associés (holehe)
□ Effectuer des recherches dans Google/Bing
□ Vérifier les inscriptions sur les réseaux sociaux
□ Rechercher des profils publics
□ Rechercher sur les sites de partage de texte
□ Vérifier les réseaux professionnels
```

---

**Retour à l’OSINT :** [🔍 Vue d’ensemble de l’OSINT](./README.fr.md)
