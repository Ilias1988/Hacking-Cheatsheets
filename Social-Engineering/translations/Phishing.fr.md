# 🎣 Fiche pratique du hameçonnage (phishing)

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Phishing.tr.md) • [🇬🇷 Ελληνικά](Phishing.el.md)

```
██████╗ ██╗  ██╗██╗███████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██║  ██║██║██╔════╝██║  ██║██║████╗  ██║██╔════╝ 
██████╔╝███████║██║███████╗███████║██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██║██║╚════██║██╔══██║██║██║╚██╗██║██║   ██║
██║     ██║  ██║██║███████║██║  ██║██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚═╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📊 Types de hameçonnage

| Type | Cible | Description |
|------|--------|-------------|
| **Phishing** | Grand nombre de personnes | Courriels génériques envoyés à de nombreux utilisateurs |
| **Spear phishing** | Personnes précises | Hameçonnage ciblé |
| **Whaling** | Dirigeants | Ciblage de la direction et des cadres |
| **Clone phishing** | Toute cible | Copie d’un courriel légitime avec du contenu malveillant |
| **Vishing** | Toute cible | Hameçonnage vocal ou téléphonique |
| **Smishing** | Toute cible | Hameçonnage par SMS |
| **Pharming** | Toute cible | Manipulation du DNS redirigeant vers un faux site |

---

## 🛠️ Installation et utilisation de GoPhish

### Installation
```bash
# Télécharger GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
cd gophish

# Démarrer GoPhish
./gophish

# Accéder à l’interface d’administration
https://localhost:3333
# Identifiants initiaux : admin / consulter la sortie de la console
```

### Étapes de configuration d’une campagne
```
1. Sending Profile   — Configurer le serveur SMTP
2. Landing Page      — Créer une fausse page de connexion
3. Email Template    — Concevoir le courriel de hameçonnage
4. Users & Groups    — Importer la liste des cibles
5. Launch Campaign   — Envoyer les courriels de hameçonnage
6. Monitor Results   — Suivre les ouvertures, clics et identifiants recueillis
```

### Exemple de modèle de courriel
```html
<!DOCTYPE html>
<html>
<body>
<p>Bonjour {{.FirstName}},</p>
<p>Votre service informatique vous demande de vérifier les identifiants de votre compte.</p>
<p>Veuillez cliquer sur le lien ci-dessous pour terminer la vérification :</p>
<p><a href="{{.URL}}">Vérifier mon compte</a></p>
<p>Cette action doit être effectuée sous 24 heures.</p>
<br>
<p>Équipe d’assistance informatique</p>
</body>
</html>
```

### Exemple de page d’atterrissage
```html
<!DOCTYPE html>
<html>
<head>
    <title>Connexion Microsoft</title>
</head>
<body>
    <form method="post" action="">
        <h2>Connexion</h2>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Se connecter</button>
    </form>
</body>
</html>
```

---

## 🔥 Evilginx2 (contournement de la 2FA)

> **Précision :** le contournement de la 2FA n’est pas universel. Les attaques par intermédiaire (AiTM) visent notamment les sessions ; les méthodes résistantes au hameçonnage, comme FIDO2, répondent à ce risque. Voir la [documentation Microsoft sur les identités cloud](https://www.microsoft.com/en-us/security/blog/2023/12/05/microsoft-incident-response-lessons-on-preventing-cloud-identity-compromise/).

### Installation
```bash
# Installer Evilginx2
go install github.com/kgretzky/evilginx2@latest

# Ou télécharger un binaire
wget https://github.com/kgretzky/evilginx2/releases/...

# Exécuter
sudo evilginx2
```

> **Précision :** le lien de téléchargement contenant `...` est un emplacement indicatif incomplet dans le document source, pas une URL utilisable telle quelle. Les identifiants de phishlets et les libellés des menus restent ceux des exemples ; leur présence dépend de la version et de la configuration.

### Configuration de base
```bash
# Dans la console Evilginx2
config domain yourdomain.com
config ip YOUR_SERVER_IP

# Lister les phishlets (profils de proxy)
phishlets

# Activer le phishlet Microsoft 365
phishlets hostname o365 login.yourdomain.com
phishlets enable o365

# Créer un leurre
lures create o365
lures get-url 0
```

### Cibles des phishlets
```
o365        - Microsoft 365
google      - Comptes Google
linkedin    - LinkedIn
twitter     - Twitter/X
github      - GitHub
instagram   - Instagram
```

---

## 📧 Collecte d’adresses électroniques

### theHarvester
```bash
# Collecter des adresses électroniques
theHarvester -d target.com -b all

# Sources précises
theHarvester -d target.com -b google,linkedin,bing

# Enregistrer les résultats dans un fichier
theHarvester -d target.com -b all -f output.html
```

### Hunter.io
```bash
# Via l’API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Déterminer le format d’une adresse électronique
curl "https://api.hunter.io/v2/email-finder?domain=target.com&first_name=John&last_name=Doe&api_key=YOUR_KEY"
```

### Énumération sur LinkedIn
```bash
# Avec linkedin2username
python linkedin2username.py -u USERNAME -c COMPANY_NAME

# Déduire le format des adresses
john.doe@company.com
jdoe@company.com
j.doe@company.com
johnd@company.com
```

---

## 📮 Infrastructure d’envoi

### Services SMTP pour les tests
```
- Amazon SES
- SendGrid
- Mailgun
- SMTP2GO
- Auto-hébergement (risque élevé de classement comme spam)
```

### Contournement de SPF/DKIM/DMARC
```bash
# Vérifier l’enregistrement SPF
dig txt target.com | grep spf

# Faiblesses SPF à rechercher :
# - "include:" référençant des tiers trop permissifs
# - "~all" (softfail) plutôt que "-all"
# - Absence d’enregistrement SPF

# Vérifier DMARC
dig txt _dmarc.target.com
```

### Configuration d’un domaine de hameçonnage
```bash
# Domaines ressemblants (typosquatting)
target.com → tarqet.com
target.com → target-secure.com
target.com → target.co
target.com → target-login.com

# Vérifier la disponibilité
whois potential-phish-domain.com
```

---

## 📱 Smishing (hameçonnage par SMS)

### Prétextes courants
```
- Notification de livraison d’un colis
- Alerte de sécurité bancaire
- Gain à un concours ou à une loterie
- Vérification de compte
- Code d’authentification à deux facteurs
```

### Services d’envoi de SMS
```
- Twilio
- Nexmo/Vonage
- Plivo
- AWS SNS
```

### Exemples de messages
```
[FedEx] Votre colis est en attente. Confirmez la livraison : http://short.url/abc

[BankName] Activité inhabituelle détectée. Vérifiez maintenant : http://secure-link.com

[Netflix] Votre paiement a échoué. Mettez vos informations à jour : http://netflix-billing.com
```

---

## 📞 Vishing (hameçonnage vocal)

### Scénarios courants
```
□ Assistance informatique demandant une réinitialisation de mot de passe
□ Service antifraude d’une banque
□ Administration publique (IRS, service des impôts)
□ Arnaque à l’assistance technique
□ Demande urgente du directeur général ou financier
□ Vérification d’un fournisseur
```

### Exemple de scénario de vishing
```
« Bonjour, je suis [Name], du service d’assistance informatique.
Nous avons détecté une activité inhabituelle sur votre compte et devons
vérifier vos identifiants pour éviter un incident de sécurité.
Pouvez-vous confirmer votre nom d’utilisateur et votre mot de passe
actuel afin que je puisse valider votre compte ? »
```

### Outils
```bash
# Usurpation du numéro de l’appelant
- SpoofCard
- SpoofTel

# Modificateurs de voix
- Voicemod
- Clownfish
```

---

## 🎯 SET (Social Engineering Toolkit)

### Installation
```bash
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
sudo python setoolkit
```

### Options du menu
```
1) Spear-Phishing Attack Vectors
2) Website Attack Vectors
3) Infectious Media Generator
4) Create a Payload and Listener
5) Mass Mailer Attack
```

### Collecteur d’identifiants
```bash
# Dans SET :
1) Social-Engineering Attacks
2) Website Attack Vectors
3) Credential Harvester Attack Method
2) Site Cloner
# Saisir l’URL à cloner (ex. : https://login.microsoft.com)
# SET hébergera le faux site et recueillera les identifiants
```

---

## 📝 Modèles de courriels de hameçonnage

### Réinitialisation de mot de passe
```
Objet : [ACTION REQUISE] Demande de réinitialisation de mot de passe

Bonjour {Name},

Nous avons reçu une demande de réinitialisation de votre mot de passe.
Si vous n’êtes pas à l’origine de cette demande, cliquez ci-dessous
pour sécuriser votre compte immédiatement.

[Sécuriser mon compte]

Ce lien expirera dans 24 heures.

Cordialement,
L’équipe de sécurité
```

### Assistance informatique
```
Objet : Maintenance planifiée du système — action requise

Bonjour {Name},

Notre équipe informatique va effectuer une maintenance planifiée.
Pour garantir la continuité de votre accès, veuillez vérifier vos identifiants :

[Vérifier maintenant]

Sans cette vérification, votre compte pourrait être bloqué.

Le service informatique
```

### Livraison d’un colis
```
Objet : Votre colis n’a pas pu être livré

Votre colis Amazon n’a pas pu être livré. Veuillez confirmer
votre adresse de livraison :

[Confirmer la livraison]

Numéro de suivi : {Random}

Merci,
L’équipe de livraison
```

---

## 📋 Liste de contrôle d’une campagne de hameçonnage

```markdown
Avant la campagne :
□ Définir le périmètre et les cibles (tests autorisés)
□ Recueillir les adresses électroniques des cibles
□ Enregistrer un domaine ressemblant
□ Mettre en place l’infrastructure SMTP
□ Configurer SPF/DKIM si possible
□ Créer la page d’atterrissage
□ Créer le modèle de courriel

Pendant la campagne :
□ Tester la délivrabilité des courriels (filtres antispam)
□ Importer la liste des cibles
□ Planifier la campagne
□ Surveiller les taux de délivrabilité
□ Suivre les ouvertures, clics et envois de formulaires

Après la campagne :
□ Produire les rapports
□ Analyser les taux de clic
□ Examiner les données recueillies
□ Identifier les besoins de formation
□ Documenter les constats
```

---

## 🔗 Fiches connexes

- [Prétextage](./Pretexting.fr.md)
- [OSINT — Google Dorking](../../Google-Dorking/README.md)

---

**Retour à la vue d’ensemble :** [🎭 Ingénierie sociale](./README.fr.md)
