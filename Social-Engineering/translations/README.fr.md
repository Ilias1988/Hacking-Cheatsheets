# 🎭 Fiches pratiques d’ingénierie sociale

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
███████╗ ██████╗  ██████╗██╗ █████╗ ██╗         ███████╗███╗   ██╗ ██████╗ ██╗███╗   ██╗███████╗███████╗██████╗ ██╗███╗   ██╗ ██████╗ 
██╔════╝██╔═══██╗██╔════╝██║██╔══██╗██║         ██╔════╝████╗  ██║██╔════╝ ██║████╗  ██║██╔════╝██╔════╝██╔══██╗██║████╗  ██║██╔════╝ 
███████╗██║   ██║██║     ██║███████║██║         █████╗  ██╔██╗ ██║██║  ███╗██║██╔██╗ ██║█████╗  █████╗  ██████╔╝██║██╔██╗ ██║██║  ███╗
╚════██║██║   ██║██║     ██║██╔══██║██║         ██╔══╝  ██║╚██╗██║██║   ██║██║██║╚██╗██║██╔══╝  ██╔══╝  ██╔══██╗██║██║╚██╗██║██║   ██║
███████║╚██████╔╝╚██████╗██║██║  ██║███████╗    ███████╗██║ ╚████║╚██████╔╝██║██║ ╚████║███████╗███████╗██║  ██║██║██║ ╚████║╚██████╔╝
╚══════╝ ╚═════╝  ╚═════╝╚═╝╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝╚═╝  ╚═══╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Qu’est-ce que l’ingénierie sociale ?

L’ingénierie sociale consiste à manipuler des personnes pour les amener à divulguer des informations confidentielles ou à effectuer des actions qui compromettent la sécurité. Elle exploite la psychologie humaine plutôt que des vulnérabilités techniques.

**Principes clés :**
- 🎭 **Autorité** — Les personnes font confiance aux figures d’autorité.
- ⏰ **Urgence** — La pression du temps réduit l’esprit critique.
- 🤝 **Réciprocité** — Les personnes se sentent obligées de rendre les services reçus.
- 👥 **Preuve sociale** — Les personnes tendent à suivre le groupe.
- 😊 **Sympathie** — Les personnes accèdent plus facilement aux demandes de ceux qu’elles apprécient.
- 📉 **Rareté** — Une disponibilité limitée renforce l’envie.

---

## 📖 Guides d’ingénierie sociale

| Sujet | Description | Guide |
|-------|-------------|-------|
| **Hameçonnage (phishing)** | Techniques et outils par courriel, SMS et téléphone | [📄 Consulter](./Phishing.fr.md) |
| **Prétextage (pretexting)** | Création de scénarios et d’identités crédibles | [📄 Consulter](./Pretexting.fr.md) |

---

## 🛠️ Outils essentiels d’ingénierie sociale

| Outil | Utilité | Type |
|------|---------|------|
| **GoPhish** | Framework de campagnes de hameçonnage | Hameçonnage |
| **SET (Social Engineering Toolkit)** | Framework complet d’ingénierie sociale | Polyvalent |
| **Evilginx2** | Hameçonnage par intermédiaire (MITM), visant à contourner la 2FA | Hameçonnage |
| **King Phisher** | Plateforme de campagnes de hameçonnage | Hameçonnage |
| **Modlishka** | Hameçonnage par proxy inverse | Hameçonnage |
| **theHarvester** | Collecte d’adresses électroniques et de domaines | Reconnaissance |
| **Outils LinkedIn** | Énumération des employés | OSINT |
| **Maltego** | OSINT et cartographie des relations | Reconnaissance |

> **Note de version :** l’archive GoPhish ci-dessous est explicitement la version `v0.12.1` du document source ; elle ne désigne pas automatiquement la version actuelle. Les commandes sont conservées pour fidélité.

> **Précision sur la 2FA :** le contournement évoqué dans le tableau dépend du mécanisme d’authentification ; il n’est pas universel. Voir les [méthodes résistantes au hameçonnage](https://learn.microsoft.com/en-us/security/zero-trust/sfi/phishing-resistant-mfa).

### Installation rapide des outils
```bash
# GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
./gophish

# SET (Social Engineering Toolkit)
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
python setoolkit

# Evilginx2
go install github.com/kgretzky/evilginx2@latest

# theHarvester
pip install theHarvester
theHarvester -d target.com -b all
```

---

## 📊 Types d’attaques d’ingénierie sociale

| Type d’attaque | Description | Vecteur |
|-------------|-------------|--------|
| **Phishing** | Courriels trompeurs visant à voler des identifiants | Courriel |
| **Spear phishing** | Hameçonnage ciblant des personnes précises | Courriel |
| **Whaling** | Hameçonnage visant les cadres dirigeants | Courriel |
| **Vishing** | Ingénierie sociale par téléphone | Téléphone |
| **Smishing** | Hameçonnage par SMS | SMS |
| **Pretexting** | Création de faux scénarios pour obtenir des informations | Tout vecteur |
| **Baiting** | Abandon de supports piégés, notamment des clés USB | Physique |
| **Quid pro quo** | Offre d’un avantage en échange d’informations | Tout vecteur |
| **Tailgating** | Entrée dans une zone sécurisée à la suite d’une personne autorisée | Physique |
| **Watering hole** | Compromission de sites web fréquentés par les cibles | Web |

---

## 🔗 Référence rapide : indices de hameçonnage

### Signaux d’alerte dans les courriels
```
□ L’adresse de l’expéditeur ne correspond pas au domaine de l’entreprise
□ Formule d’appel générique (« Cher client »)
□ Urgence ou menaces
□ Fautes d’orthographe ou de grammaire
□ Liens suspects (survoler le lien pour vérifier sa destination)
□ Pièces jointes inattendues
□ Demande d’informations sensibles
□ Offres trop belles pour être vraies
```

### Analyse des URL
```bash
# Vérifier l’ancienneté du domaine
whois suspicious-domain.com

# Vérifier la réputation
https://www.virustotal.com
https://urlscan.io

# Analyser les URL raccourcies
https://checkshorturl.com
```

---

## 📚 Ressources

- [OWASP Social Engineering](https://owasp.org/www-community/attacks/Social_Engineering)
- [MITRE ATT&CK - Initial Access](https://attack.mitre.org/tactics/TA0001/)
- [Phishing.org](https://www.phishing.org/)
- [Social Engineering Framework](https://www.social-engineer.org/)

---

<p align="center">
  <b>🎭 Réservez l’ingénierie sociale aux tests autorisés !</b>
</p>
