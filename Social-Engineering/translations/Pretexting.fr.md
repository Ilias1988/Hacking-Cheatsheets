# 🎭 Fiche pratique du prétextage (pretexting)

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Pretexting.tr.md) • [🇬🇷 Ελληνικά](Pretexting.el.md)

```
██████╗ ██████╗ ███████╗████████╗███████╗██╗  ██╗████████╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝╚██╗██╔╝╚══██╔══╝██║████╗  ██║██╔════╝ 
██████╔╝██████╔╝█████╗     ██║   █████╗   ╚███╔╝    ██║   ██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝   ██╔██╗    ██║   ██║██║╚██╗██║██║   ██║
██║     ██║  ██║███████╗   ██║   ███████╗██╔╝ ██╗   ██║   ██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Qu’est-ce que le prétextage ?

Le prétextage consiste à inventer un scénario, ou prétexte, pour entrer en contact avec une cible et obtenir des informations ou l’amener à effectuer une action. Il constitue la base de nombreuses attaques d’ingénierie sociale.

**Éléments clés :**
- 🎭 **Personnage** — L’identité fictive adoptée.
- 📖 **Histoire** — Le scénario ou le motif crédible.
- 🎯 **Objectif** — Le résultat recherché.
- 🔧 **Accessoires** — Les supports utilisés pour crédibiliser le scénario : badges, courriels, etc.

---

## 👥 Personnages courants

### Assistance informatique
```
Rôle : technicien de l’assistance informatique
Objectif : réinitialisation de mot de passe, accès distant, installation de logiciel

« Bonjour, je suis Mike, du service informatique. Nous observons une
activité inhabituelle sur votre compte et devons vérifier vos
identifiants pour éviter son blocage. »
```

### Fournisseur ou prestataire
```
Rôle : prestataire externe (chauffage/climatisation, sécurité, nettoyage)
Objectif : accès physique, accès au réseau

« Je viens de la part de [Vendor Company] pour la maintenance prévue.
Pouvez-vous me laisser entrer dans la salle des serveurs ? »
```

### Nouvel employé
```
Rôle : nouvelle recrue ayant besoin d’aide
Objectif : collecte d’informations, accès au bâtiment

« Bonjour, je viens d’arriver au marketing et je n’arrive pas à accéder
au lecteur partagé. Pourriez-vous m’aider ? »
```

### Dirigeant ou figure d’autorité
```
Rôle : directeur général, directeur financier, cadre supérieur
Objectif : demandes urgentes, virements, accès

« C’est urgent. Le directeur général a besoin de ce rapport immédiatement
et je vous demande de déroger aux procédures habituelles. »
```

### Livreur
```
Rôle : livreur FedEx, UPS ou coursier
Objectif : accès physique, dépôt d’un colis

« J’ai un colis qui nécessite une signature.
Pouvez-vous me laisser entrer à l’accueil ? »
```

---

## 📋 Scénarios de prétextage

### Prétextes au téléphone

#### Réinitialisation de mot de passe par l’assistance informatique
```
« Bonjour, je suis [Name], du service d’assistance informatique. Nous avons
détecté que votre compte a été compromis lors d’un récent incident de sécurité.
Je dois vérifier votre identité et vous aider à réinitialiser votre mot de passe.
Pouvez-vous confirmer votre mot de passe actuel pour que je m’assure
qu’il s’agit bien de vous ? »
```

#### Vérification d’un fournisseur
```
« Bonjour, j’appelle de la comptabilité fournisseurs de [Vendor]. Nous mettons
nos dossiers à jour et devons vérifier les coordonnées bancaires de votre
entreprise pour le prochain paiement. Pouvez-vous confirmer le numéro de
compte que vous avez enregistré pour nous ? »
```

#### Enquête des ressources humaines
```
« Bonjour, je mène une enquête de satisfaction du personnel pour les ressources
humaines. L’enquête est anonyme, mais je dois vérifier que vous êtes bien employé.
Pouvez-vous m’indiquer votre service et le nom de votre responsable ? »
```

### Prétextes en personne

#### Inspecteur de sécurité incendie
```
« Bonjour, je viens du service municipal de sécurité incendie. Nous effectuons
des inspections inopinées. J’aurai besoin d’accéder à votre salle des serveurs
pour vérifier le système d’extinction incendie. »
```

#### Entretien d’embauche
```
« Je suis ici pour l’entretien de 14 heures avec les ressources humaines.
En attendant, pourriez-vous m’indiquer les toilettes ? »
(Profiter de l’occasion pour repérer les lieux.)
```

#### Agent de maintenance
```
« Nous avons reçu un appel concernant une fuite d’eau dans cette zone.
Pouvez-vous m’indiquer la salle des serveurs ? Je dois vérifier les
canalisations qui passent au-dessus du plafond. »
```

---

## 🧠 Principes psychologiques

### Autorité
```
✓ Utiliser des titres : « directeur informatique », « responsable de la sécurité »
✓ Adopter une tenue cohérente avec le rôle : costume, uniforme, badge
✓ Parler avec assurance
✓ Faire référence aux politiques et aux procédures
```

### Urgence
```
✓ « Cela doit être fait avant la réunion du directeur général à 3 heures »
✓ « Le système bloquera votre accès dans 10 minutes »
✓ « Nous faisons l’objet d’un audit et en avons besoin immédiatement »
✓ Créer une pression temporelle pour empêcher les vérifications
```

### Réciprocité
```
✓ Commencer par rendre un petit service
✓ « Je viens de résoudre ce problème d’imprimante… »
✓ « J’ai apporté du café pour tout le monde… »
✓ Créer un sentiment d’obligation à rendre le service
```

### Preuve sociale
```
✓ « Tous les autres l’ont déjà fait »
✓ « Votre responsable John l’a déjà approuvé »
✓ « Toute l’équipe marketing a rempli le formulaire »
✓ Évoquer les personnes qui ont déjà accepté
```

### Sympathie
```
✓ Trouver des points communs
✓ Reprendre le langage corporel de l’interlocuteur
✓ Faire des compliments sincères
✓ Créer une relation de confiance avant de formuler la demande
```

---

## 🔧 Accessoires et supports

### Accessoires physiques
```
□ Faux badges d’identification
□ Cartes de visite
□ Porte-documents avec des formulaires à l’apparence officielle
□ Gilet haute visibilité ou uniforme
□ Boîte à outils ou sacoche d’ordinateur
□ Courriels ou lettres d’autorisation imprimés
□ Documents du fournisseur
```

### Supports numériques
```
□ Numéro d’appelant usurpé
□ Fausse adresse électronique (domaine ressemblant)
□ Faux profil LinkedIn
□ Copie du site web de l’entreprise
□ Faux courriels d’autorisation
□ Fausses invitations à des réunions
```

### Documentation
```
□ Numéro de ticket d’assistance
□ Bon d’intervention
□ Formulaire d’autorisation
□ Numéro de référence
□ Nom du responsable (obtenu par OSINT)
```

---

## 🔍 OSINT pour le prétextage

### Recherche sur la cible
```bash
# Informations sur les employés via LinkedIn
- Noms, fonctions et services
- Structure hiérarchique
- Publications et activité récentes

# Site web de l’entreprise
- Équipe dirigeante
- Organigramme
- Implantation des bureaux
- Informations sur les fournisseurs et partenaires

# Communiqués de presse
- Actualités et acquisitions récentes
- Changements dans l’équipe dirigeante
```

### Informations utiles
```
□ Noms et fonctions des employés
□ Organisation des services
□ Noms des responsables
□ Format des adresses électroniques (first.last@company.com)
□ Numéros de téléphone
□ Implantation des bureaux
□ Relations avec les fournisseurs
□ Événements récents de l’entreprise
```

---

## 📱 Ingénierie sociale physique

### Tailgating : entrer à la suite d’une personne autorisée
```
Technique : suivre une personne autorisée à travers une porte sécurisée

« Merci, j’ai oublié mon badge aujourd’hui ! »
« Pouvez-vous tenir la porte ? J’ai les mains prises. »
« Je suis avec [Name], du service [Department]. »
```

### Piggybacking : obtenir l’aide d’une personne autorisée pour entrer
```
Technique : convaincre une personne autorisée de vous laisser entrer

« Bonjour, je viens de la part de [Vendor]. On m’attend, mais la personne
à l’accueil s’est absentée. Pourriez-vous me laisser entrer ? »
```

### Dumpster diving : fouille des déchets
```
Cible : documents et matériel mis au rebut

□ Organigrammes
□ Listes des employés
□ Documents financiers
□ Notes contenant des mots de passe
□ Ancien matériel contenant des données
```

### Dépôt de clés USB piégées (baiting)
```
Technique : laisser des clés USB infectées

Emplacements :
□ Parking
□ Accueil
□ Salles de pause
□ À proximité des espaces fumeurs

Idées d’étiquettes :
□ « Confidentiel — RH »
□ « Informations salariales 2024 »
□ « Liste des licenciements »
□ « Photos privées »
```

---

## 📝 Modèles de scénarios

### Scénario téléphonique — assistance informatique
```
[Sonnerie]
Cible : « Allô ? »
Vous : « Bonjour, suis-je bien en ligne avec [Target Name] ? »
Cible : « Oui. »
Vous : « Très bien, je suis [Your Name], de l’équipe de sécurité informatique.
Nous observons des tentatives de connexion inhabituelles à votre compte
depuis une adresse IP étrangère. Nous voulons nous assurer que ce n’était
pas vous. Avez-vous essayé de vous connecter depuis [Random Country]
aujourd’hui ? »
Cible : « Non, ce n’était pas moi ! »
Vous : « C’est ce que je pensais. Par sécurité, je dois vérifier votre compte.
Pouvez-vous confirmer votre nom d’utilisateur ? Nous réinitialiserons
votre mot de passe pour bloquer l’attaquant. »
```

### Modèle de courriel — demande d’un dirigeant
```
Objet : Urgent — virement nécessaire

Bonjour [Name],

Je suis en réunion et ne peux pas appeler. J’ai besoin que vous
effectuiez un virement urgent pour une acquisition soumise à un délai serré.

Veuillez virer $45,000 sur le compte suivant :
[Details]

Je vous expliquerai à la fin de cette réunion. Merci de confirmer
une fois le virement effectué.

[Executive Name]
Envoyé de mon iPhone
```

---

## ⚠️ Considérations juridiques et éthiques

### Règles d’engagement
```
□ Toujours disposer d’une autorisation écrite
□ Définir clairement le périmètre et ses limites
□ Ne pas accéder aux comptes personnels
□ Ne pas recourir au chantage ou à la contrainte
□ Tout documenter
□ Prévoir des contacts d’urgence
□ Garder sur soi la lettre d’autorisation de mission
```

### Pratiques à éviter
```
✗ Se faire passer pour les forces de l’ordre
✗ Proférer de véritables menaces
✗ Accéder à des systèmes hors périmètre
✗ Voler de l’argent ou des biens réels
✗ Créer une situation engageant une responsabilité juridique
✗ Causer des dommages psychologiques
```

---

## 📋 Liste de contrôle du prétextage

```markdown
Préparation :
□ Étudier l’organisation cible (OSINT)
□ Identifier les personnes clés
□ Construire un personnage et son histoire
□ Préparer les supports et accessoires
□ Répéter le scénario et les réponses
□ Préparer des histoires de repli

Déroulement :
□ Rester calme et sûr de soi
□ Garder la maîtrise de la conversation
□ Être prêt à adapter le scénario en cas de contestation
□ Ne pas en faire trop ni donner trop de détails
□ Savoir quand interrompre la tentative

Après la mission :
□ Documenter ce qui a fonctionné et ce qui a échoué
□ Protéger les données recueillies
□ Rapporter les constats
□ Formuler des recommandations
```

---

## 🔗 Fiches connexes

- [Hameçonnage](./Phishing.fr.md)
- [OSINT — Google Dorking](../../Google-Dorking/README.md)

---

**Retour à la vue d’ensemble :** [🎭 Ingénierie sociale](./README.fr.md)
