# 🎣 网络钓鱼速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **版本与示例说明：** 文中的 GoPhish `v0.12.1` 下载地址是固定版本示例，`releases/...` 是省略写法，不能直接作为完整下载地址使用；解压后是否存在 `gophish` 子目录须按实际归档结构确认。HTML 邮件和登录表单为保留原样的代码示例，模板变量、属性和显示文本均未改写。
>
> **认证说明：** “绕过 2FA”并非通用能力；反向代理钓鱼与 FIDO2 等抗钓鱼认证的适用条件不同。参见 [微软抗钓鱼认证部署说明](https://learn.microsoft.com/en-us/entra/identity/authentication/how-to-deploy-phishing-resistant-passwordless-authentication)。SPF、DKIM、DMARC 分别涉及发信授权、签名与域名对齐/策略，单个 SPF 弱点不能证明可以绕过三者。菜单项保留英文，方便与实际界面对照。

[🇹🇷 Türkçe Sürüm](Phishing.tr.md) • [🇬🇷 Ελληνικά](Phishing.el.md)

```
██████╗ ██╗  ██╗██╗███████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██║  ██║██║██╔════╝██║  ██║██║████╗  ██║██╔════╝ 
██████╔╝███████║██║███████╗███████║██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██║██║╚════██║██╔══██║██║██║╚██╗██║██║   ██║
██║     ██║  ██║██║███████║██║  ██║██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚═╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📊 网络钓鱼类型

| 类型 | 目标 | 说明 |
|------|--------|-------------|
| **网络钓鱼（Phishing）** | 大量用户 | 向大量用户发送通用邮件 |
| **鱼叉式钓鱼（Spear Phishing）** | 特定用户 | 针对特定个人 |
| **捕鲸式钓鱼（Whaling）** | 高管 | 针对高管及管理层 |
| **克隆钓鱼（Clone Phishing）** | 任意 | 复制合法邮件并植入恶意内容 |
| **语音钓鱼（Vishing）** | 任意 | 语音或电话钓鱼 |
| **短信钓鱼（Smishing）** | 任意 | 短信钓鱼 |
| **域名劫持钓鱼（Pharming）** | 任意 | 操纵 DNS 将访问导向伪造网站 |

---

## 🛠️ GoPhish 配置与使用

### 安装
```bash
# 下载 GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
cd gophish

# 启动 GoPhish
./gophish

# 访问管理面板
https://localhost:3333
# 默认凭据：admin / 密码见控制台输出
```

### 演练配置流程
```
1. Sending Profile  - 配置 SMTP 服务器
2. Landing Page     - 创建伪造登录页
3. Email Template   - 设计钓鱼邮件
4. Users & Groups   - 导入目标名单
5. Launch Campaign  - 发送钓鱼邮件
6. Monitor Results  - 跟踪打开、点击和凭据提交情况
```

### 电子邮件模板示例
```html
<!DOCTYPE html>
<html>
<body>
<p>Dear {{.FirstName}},</p>
<p>Your IT department requires you to verify your account credentials.</p>
<p>Please click the link below to complete verification:</p>
<p><a href="{{.URL}}">Verify Account</a></p>
<p>This action is required within 24 hours.</p>
<br>
<p>IT Support Team</p>
</body>
</html>
```

### 落地页示例
```html
<!DOCTYPE html>
<html>
<head>
    <title>Microsoft Login</title>
</head>
<body>
    <form method="post" action="">
        <h2>Sign in</h2>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Sign in</button>
    </form>
</body>
</html>
```

---

## 🔥 Evilginx2（绕过部分双因素认证）

### 安装
```bash
# 安装 Evilginx2
go install github.com/kgretzky/evilginx2@latest

# 或下载二进制文件
wget https://github.com/kgretzky/evilginx2/releases/...

# 运行
sudo evilginx2
```

### 基本配置
```bash
# 在 Evilginx2 控制台中
config domain yourdomain.com
config ip YOUR_SERVER_IP

# 列出 phishlet（站点钓鱼配置）
phishlets

# 启用 Microsoft 365 的 phishlet
phishlets hostname o365 login.yourdomain.com
phishlets enable o365

# 创建诱导链接（lure）
lures create o365
lures get-url 0
```

### Phishlet 对应目标
```
o365        - Microsoft 365
google      - Google 账户
linkedin    - LinkedIn
twitter     - Twitter/X
github      - GitHub
instagram   - Instagram
```

---

## 📧 电子邮件地址收集

### theHarvester
```bash
# 收集电子邮件地址
theHarvester -d target.com -b all

# 指定数据源
theHarvester -d target.com -b google,linkedin,bing

# 输出到文件
theHarvester -d target.com -b all -f output.html
```

### Hunter.io
```bash
# 通过 API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# 获取电子邮件地址格式
curl "https://api.hunter.io/v2/email-finder?domain=target.com&first_name=John&last_name=Doe&api_key=YOUR_KEY"
```

### LinkedIn 枚举
```bash
# 使用 linkedin2username
python linkedin2username.py -u USERNAME -c COMPANY_NAME

# 猜测地址格式
john.doe@company.com
jdoe@company.com
j.doe@company.com
johnd@company.com
```

---

## 📮 发送基础设施

### 用于测试的 SMTP 服务
```
- Amazon SES
- SendGrid
- Mailgun
- SMTP2GO
- 自建服务（较易被判定为垃圾邮件）
```

### SPF/DKIM/DMARC 绕过
```bash
# 检查 SPF 记录
dig txt target.com | grep spf

# 可利用的 SPF 薄弱点：
# - "include:" 引入权限过宽的第三方
# - 使用 "~all"（软失败）而不是 "-all"
# - 未设置 SPF 记录

# 检查 DMARC
dig txt _dmarc.target.com
```

### 钓鱼域名配置
```bash
# 相似域名（拼写仿冒，typosquatting）
target.com → tarqet.com
target.com → target-secure.com
target.com → target.co
target.com → target-login.com

# 检查域名是否可注册
whois potential-phish-domain.com
```

---

## 📱 短信钓鱼（Smishing）

### 常见借口
```
- 包裹投递通知
- 银行安全提醒
- 获奖或中奖通知
- 账户验证
- 双因素认证验证码
```

### 短信发送服务
```
- Twilio
- Nexmo/Vonage
- Plivo
- AWS SNS
```

### 消息示例
```
[FedEx] 您的包裹等待投递。请确认收货：http://short.url/abc

[BankName] 检测到异常活动。请立即验证：http://secure-link.com

[Netflix] 您的付款失败。请更新付款信息：http://netflix-billing.com
```

---

## 📞 语音钓鱼（Vishing）

### 常见情境
```
□ IT 支持要求重置密码
□ 银行反欺诈部门
□ 政府机构（如 IRS、税务机关）
□ 技术支持诈骗
□ CEO/CFO 的紧急要求
□ 供应商信息核实
```

### 语音钓鱼话术示例
```
“您好，我是 IT 服务台的 [Name]。我们检测到您的账户存在异常活动，需要核实您的凭据以防止安全事件。您能确认一下用户名和当前密码吗？我需要验证您的账户。”
```

### 工具
```bash
# 来电显示伪造
- SpoofCard
- SpoofTel

# 变声工具
- Voicemod
- Clownfish
```

---

## 🎯 SET（Social Engineering Toolkit，社会工程学工具包）

### 安装
```bash
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
sudo python setoolkit
```

### 菜单选项
```
1) Spear-Phishing Attack Vectors  （鱼叉式钓鱼攻击途径）
2) Website Attack Vectors        （网站攻击途径）
3) Infectious Media Generator    （感染介质生成器）
4) Create a Payload and Listener （创建载荷与监听器）
5) Mass Mailer Attack            （群发邮件攻击）
```

### 凭据收集器
```bash
# 在 SET 中：
1) Social-Engineering Attacks
2) Website Attack Vectors
3) Credential Harvester Attack Method
2) Site Cloner
# 输入要克隆的 URL（例如 https://login.microsoft.com）
# SET 将托管伪造站点并收集凭据
```

---

## 📝 钓鱼邮件模板

### 密码重置
```
主题：[需要操作] 密码重置请求

尊敬的 {Name}：

我们收到了一项重置您密码的请求。如果不是您本人发起，请立即点击下方链接保护账户。

[保护我的账户]

此链接将在 24 小时后失效。

此致
安全团队
```

### IT 支持
```
主题：计划内系统维护——需要您操作

尊敬的 {Name}：

IT 团队将按计划进行系统维护。为确保访问不中断，请验证您的凭据：

[立即验证]

未完成验证可能导致账户锁定。

IT 部门
```

### 包裹投递
```
主题：您的包裹未能送达

您在 Amazon 购买的包裹未能送达。请确认收货地址：

[确认投递]

追踪编号：{Random}

谢谢！
配送团队
```

---

## 📋 钓鱼演练检查清单

```markdown
演练前：
□ 明确范围和目标（获得授权的测试）
□ 收集目标电子邮件地址
□ 注册相似域名
□ 搭建 SMTP 基础设施
□ 尽可能配置 SPF/DKIM
□ 创建落地页
□ 创建电子邮件模板

演练实施：
□ 测试邮件投递情况（垃圾邮件过滤器）
□ 导入目标名单
□ 安排演练时间
□ 监控投递率
□ 跟踪打开、点击和提交情况

演练后：
□ 生成报告
□ 分析点击率
□ 审查收集的数据
□ 确定培训需求
□ 记录发现
```

---

## 🔗 相关速查表

- [借口伪装（Pretexting）](./Pretexting.zh-CN.md)
- [OSINT：Google 高级搜索](../../Google-Dorking/README.md)

---

**返回概览：** [🎭 社会工程学](./README.zh-CN.md)
