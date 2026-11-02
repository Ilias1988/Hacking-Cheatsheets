# 🎭 社会工程学速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **译注：** 社会工程学（Social Engineering）利用人的信任和判断弱点。文中的“绕过 2FA”指部分可被钓鱼的认证流程，并不适用于所有双因素认证；FIDO2 等抗钓鱼认证需区别看待。参见 [微软抗钓鱼 MFA 说明](https://learn.microsoft.com/en-us/entra/identity/authentication/how-to-deploy-phishing-resistant-passwordless-authentication)。

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
███████╗ ██████╗  ██████╗██╗ █████╗ ██╗         ███████╗███╗   ██╗ ██████╗ ██╗███╗   ██╗███████╗███████╗██████╗ ██╗███╗   ██╗ ██████╗ 
██╔════╝██╔═══██╗██╔════╝██║██╔══██╗██║         ██╔════╝████╗  ██║██╔════╝ ██║████╗  ██║██╔════╝██╔════╝██╔══██╗██║████╗  ██║██╔════╝ 
███████╗██║   ██║██║     ██║███████║██║         █████╗  ██╔██╗ ██║██║  ███╗██║██╔██╗ ██║█████╗  █████╗  ██████╔╝██║██╔██╗ ██║██║  ███╗
╚════██║██║   ██║██║     ██║██╔══██║██║         ██╔══╝  ██║╚██╗██║██║   ██║██║██║╚██╗██║██╔══╝  ██╔══╝  ██╔══██╗██║██║╚██╗██║██║   ██║
███████║╚██████╔╝╚██████╗██║██║  ██║███████╗    ███████╗██║ ╚████║╚██████╔╝██║██║ ╚████║███████╗███████╗██║  ██║██║██║ ╚████║╚██████╔╝
╚══════╝ ╚═════╝  ╚═════╝╚═╝╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝╚═╝  ╚═══╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 什么是社会工程学？

社会工程学通过操纵人的心理，诱使对方泄露机密信息或执行危及安全的操作。它利用的是人的心理弱点，而非技术漏洞。

**核心原理：**
- 🎭 **权威** - 人们倾向于信任具有权威身份的人
- ⏰ **紧迫感** - 时间压力会削弱审慎判断
- 🤝 **互惠** - 人们往往觉得应该回报他人的帮助
- 👥 **社会认同** - 人们容易跟随群体行动
- 😊 **好感** - 人们更容易答应自己喜欢的人
- 📉 **稀缺性** - 供应有限会增强获取欲望

---

## 📖 社会工程学指南

| 主题 | 说明 | 指南 |
|-------|-------------|-------|
| **网络钓鱼（Phishing）** | 电子邮件、短信和语音钓鱼技术及工具 | [📄 查看](./Phishing.zh-CN.md) |
| **借口伪装（Pretexting）** | 构造可信的情境与身份 | [📄 查看](./Pretexting.zh-CN.md) |

---

## 🛠️ 必备社会工程学工具

| 工具 | 用途 | 类型 |
|------|---------|------|
| **GoPhish** | 钓鱼演练框架 | 网络钓鱼（Phishing） |
| **SET（Social Engineering Toolkit，社会工程学工具包）** | 综合社会工程学框架 | 多用途 |
| **Evilginx2** | 中间人（MITM）钓鱼（绕过部分双因素认证） | 网络钓鱼（Phishing） |
| **King Phisher** | 钓鱼演练平台 | 网络钓鱼（Phishing） |
| **Modlishka** | 反向代理钓鱼 | 网络钓鱼（Phishing） |
| **theHarvester** | 电子邮件与域名收集 | 信息收集 |
| **LinkedIn 工具** | 员工枚举 | OSINT |
| **Maltego** | OSINT（开源情报）与关系映射 | 信息收集 |

### 快速安装工具
```bash
# GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
./gophish

# SET（Social Engineering Toolkit，社会工程学工具包）
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
python setoolkit

# Evilginx2
go install github.com/kgretzky/evilginx2@latest

# theHarvester
pip install theHarvester
theHarvester -d target.com -b all
```

---

## 📊 社会工程学攻击类型

| 攻击类型 | 说明 | 途径 |
|-------------|-------------|--------|
| **网络钓鱼（Phishing）** | 通过欺骗性邮件窃取凭据 | 电子邮件 |
| **鱼叉式钓鱼（Spear Phishing）** | 针对特定个人的定向钓鱼 | 电子邮件 |
| **捕鲸式钓鱼（Whaling）** | 针对高管的钓鱼 | 电子邮件 |
| **语音钓鱼（Vishing）** | 通过语音或电话实施社会工程学攻击 | 电话 |
| **短信钓鱼（Smishing）** | 通过短信实施的钓鱼 | SMS |
| **借口伪装（Pretexting）** | 编造情境以套取信息 | 任意 |
| **诱饵攻击（Baiting）** | 放置感染恶意程序的介质（如 USB 设备） | 物理接触 |
| **利益交换（Quid Pro Quo）** | 以提供某种好处为条件换取信息 | 任意 |
| **尾随进入（Tailgating）** | 跟随获授权人员进入受控区域 | 物理接触 |
| **水坑攻击（Watering Hole）** | 入侵目标经常访问的网站 | Web |

---

## 🔗 速查：钓鱼迹象

### 邮件中的可疑信号
```
□ 发件人邮箱与公司域名不符
□ 使用泛化称呼（如“尊敬的客户”）
□ 营造紧迫感或进行威胁
□ 存在拼写或语法错误
□ 链接可疑（悬停查看实际地址）
□ 附件出乎预期
□ 索要敏感信息
□ 优惠好得不合常理
```

### URL 分析
```bash
# 检查域名注册时间
whois suspicious-domain.com

# 检查信誉
https://www.virustotal.com
https://urlscan.io

# 分析短链接
https://checkshorturl.com
```

---

## 📚 参考资源

- [OWASP 社会工程学](https://owasp.org/www-community/attacks/Social_Engineering)
- [MITRE ATT&CK：初始访问](https://attack.mitre.org/tactics/TA0001/)
- [Phishing.org](https://www.phishing.org/)
- [社会工程学框架](https://www.social-engineer.org/)

---

<p align="center">
  <b>🎭 社会工程学仅用于获得授权的测试！</b>
</p>
