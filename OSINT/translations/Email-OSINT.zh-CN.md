# 📧 电子邮件 OSINT 速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Email-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Email-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **译注：** SMTP 的 `250` 表示该次请求被接受，`550` 可表示邮箱不存在、不可访问或被策略拒绝；它们不能单独可靠证明邮箱是否真实存在，参见 [RFC 5321](https://www.rfc-editor.org/rfc/rfc5321)。HIBP 网页查询与 API 的收费、认证条件不同；按邮箱查询泄露记录的 API 需要相应订阅密钥，且请求应使用能识别调用应用的 User-Agent，参见 [HIBP API 文档](https://haveibeenpwned.com/API/v3)。原始命令均保留以便对照。

```
███████╗███╗   ███╗ █████╗ ██╗██╗     
██╔════╝████╗ ████║██╔══██╗██║██║     
█████╗  ██╔████╔██║███████║██║██║     
██╔══╝  ██║╚██╔╝██║██╔══██║██║██║     
███████╗██║ ╚═╝ ██║██║  ██║██║███████╗
╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚══════╝
```

---

## 🔍 电子邮件发现工具

| 工具 | URL | 用途 |
|------|-----|---------|
| **Hunter.io** | hunter.io | 查找企业电子邮箱 |
| **Phonebook.cz** | phonebook.cz | 免费电子邮箱搜索 |
| **Snov.io** | snov.io | 电子邮箱发现 |
| **Clearbit Connect** | clearbit.com | 电子邮箱信息补充 |
| **RocketReach** | rocketreach.co | 联系人搜索 |
| **Voila Norbert** | voilanorbert.com | 电子邮箱查询 |

---

## 🛠️ 电子邮件枚举命令

### theHarvester
```bash
# 收集电子邮箱
theHarvester -d target.com -l 500 -b all
theHarvester -d target.com -b google,linkedin,bing
theHarvester -d target.com -b all -f emails.html
```

### Hunter.io CLI
```bash
# API 查询
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# 电子邮箱验证
curl "https://api.hunter.io/v2/email-verifier?email=test@target.com&api_key=YOUR_KEY"
```

### h8mail（数据泄露搜索）
```bash
pip3 install h8mail
h8mail -t target@email.com
h8mail -t target@email.com -c h8mail_config.ini
```

### holehe（注册状态检查）
```bash
pip3 install holehe
holehe target@email.com
holehe target@email.com --only-used
```

---

## 📧 电子邮箱格式模式

```bash
# 常见企业电子邮箱格式
first.last@company.com     # john.smith@company.com
flast@company.com          # jsmith@company.com
firstl@company.com         # johns@company.com
first_last@company.com     # john_smith@company.com
first@company.com          # john@company.com
last.first@company.com     # smith.john@company.com
```

### 电子邮箱组合生成器
```python
# 生成电子邮箱变体
names = ["john", "smith"]
domain = "company.com"

patterns = [
    f"{names[0]}.{names[1]}@{domain}",
    f"{names[0][0]}{names[1]}@{domain}",
    f"{names[0]}{names[1][0]}@{domain}",
    f"{names[0]}_{names[1]}@{domain}",
    f"{names[0]}@{domain}",
]
```

---

## 🔐 数据泄露查询

| 服务 | URL | 类型 |
|---------|-----|------|
| **Have I Been Pwned** | haveibeenpwned.com | 免费 |
| **DeHashed** | dehashed.com | 付费 |
| **LeakCheck** | leakcheck.io | 付费 |
| **Snusbase** | snusbase.com | 付费 |
| **IntelX** | intelx.io | 基础功能免费，高级功能付费 |

### HIBP API
```bash
curl -H "hibp-api-key: YOUR_KEY" \
  "https://haveibeenpwned.com/api/v3/breachedaccount/test@example.com"
```

---

## ✅ 电子邮箱验证

### 验证方法
```
1. MX 记录检查——域名是否配置了邮件服务器
2. SMTP 验证——检查邮箱是否存在（存在限制，参见译注）
3. 语法检查——邮箱格式是否有效
4. 一次性邮箱检查——是否为临时邮箱
5. 角色邮箱检查——是否为通用职能邮箱（info@、admin@）
```

### 简单 SMTP 检查
```bash
# 检查 MX 记录
nslookup -type=mx target.com
dig mx target.com

# 通过 Telnet 验证
telnet mail.target.com 25
HELO test.com
MAIL FROM:<test@test.com>
RCPT TO:<target@target.com>
# 250 = 存在，550 = 不存在（原文简化说法，参见译注）
```

---

## 📋 电子邮件 OSINT 检查清单

```markdown
## 目标邮箱：[email@domain.com]

□ 验证电子邮箱是否存在（SMTP/Hunter）
□ 查询数据泄露记录（HIBP）
□ 寻找关联账户（holehe）
□ 使用 Google/Bing 搜索
□ 检查社交媒体注册情况
□ 寻找公开个人资料
□ 搜索文本粘贴分享网站
□ 检查职业社交网络
```

---

**返回 OSINT:** [🔍 OSINT 概览](./README.zh-CN.md)
