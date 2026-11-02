# ⚡ OWASP ZAP 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **项目与版本说明：** “OWASP ZAP”及由 OWASP 维护的说法沿用原文历史名称；ZAP 自 2023 年 8 月起已不再是 OWASP 项目，参见 [ZAP 官方说明](https://www.zaproxy.org/docs/nowaspzap/)。对比表中的价格、版本能力和附加组件数量也是原文数据，不应视为当前报价或完整功能清单。菜单、默认值和浏览器选项可能随版本改变；当前本地代理设置位于 Options → Network → Local Servers/Proxies，参见 [官方代理配置](https://www.zaproxy.org/docs/desktop/start/proxies/)。
>
> **自动化示例说明：** API 发起爬取或主动扫描后通常返回扫描标识，调用方还需等待相应任务和被动扫描队列完成，再汇总告警；下方 Python 示例省略了这些等待步骤，参见 [ZAP API 文档](https://www.zaproxy.org/docs/api/)。自动化 YAML 也应按实际流程核对报告前的等待安排。Docker 报告文件若需保留到主机，应配置工作目录挂载；本文仍保留原始命令。
>
> **扫描含义说明：** Baseline 会先爬取站点，再报告被动扫描结果，因此“passive only”并不表示完全不向目标发请求；参见 [官方 Baseline 说明](https://www.zaproxy.org/docs/docker/baseline-scan/)。被动扫描本身只分析已有消息，不能把原文“弱 SSL/TLS”理解为完整的 TLS 协议或密码套件审计。

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ____  _    _  ___   _____ _____    _____   ___  _____  
  / __ \| |  | |/ _ \ / ____|  __ \  |__  /  / _ \|  __ \ 
 | |  | | |  | | | | | (___ | |__) |   / /  / /_\ \ |__) |
 | |  | | |/\| | |_| |\___ \|  ___/   / /  |  _  |  ___/ 
 | |__| \  /\  / | | |____) | |      / /__ | | | | |     
  \____/ \/  \/|_| |_|_____/|_|     /_____|_| |_|_|     
                                                          
    Zed Attack Proxy——免费的安全测试工具
```

<p align="center">
  <img src="https://img.shields.io/badge/OWASP-ZAP-blue?style=for-the-badge" alt="OWASP ZAP">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/Open-Source-green?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Free-Tool-purple?style=for-the-badge" alt="Free">
</p>

<p align="center">
  <b>🔒 全球使用最广泛的免费 Web 安全扫描器</b>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [界面概览](#-interface-overview)
- [代理配置](#-proxy-configuration)
- [手动测试](#-manual-testing)
- [Spider 与爬虫](#-spider--crawler)
- [主动扫描](#-active-scanning)
- [被动扫描](#-passive-scanning)
- [上下文与会话](#-contexts--sessions)
- [模糊测试器（Fuzzer）](#-fuzzer)
- [API 与自动化](#-api--automation)
- [命令行](#-command-line)
- [附加组件（Add-ons）](#-add-ons)
- [实践示例](#-real-world-examples)
- [速查](#-quick-reference)
- [技巧与最佳实践](#-tips--best-practices)
- [参考资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**OWASP ZAP**（Zed Attack Proxy）是一款免费的开源渗透测试工具，原文称由 OWASP 维护（历史归属见译注）。它用于发现 Web 应用中的安全漏洞。

### 为什么选择 ZAP？

| 功能 | 说明 |
|---------|-------------|
| **免费且开源** | 无需许可证费用，由社区推动 |
| **易于使用** | 对初学者友好，同时具备强大功能 |
| **自动扫描** | 爬虫、主动扫描和被动扫描 |
| **手动测试** | 拦截代理、模糊测试器 |
| **API 支持** | 提供完整的 REST API 以便自动化 |
| **跨平台** | Windows, Linux, macOS, Docker |
| **可扩展** | Marketplace 提供 100+ 个附加组件 |

### ZAP 与 Burp Suite 对比

| 功能 | ZAP | Burp Suite |
|---------|-----|------------|
| **价格** | 免费 | $449+/年 |
| **开源** | ✅ 是（Yes） | ❌ 否（No） |
| **自动扫描** | ✅ 完整支持 | 仅 Pro 版 |
| **API 访问** | ✅ 免费 | 仅 Pro 版 |
| **持续开发** | ✅ 是（Yes） | ✅ 是（Yes） |
| **社区** | OWASP | PortSwigger |

---

<a id="-installation"></a>
## 📥 安装

### Windows
```bash
# 从官方网站下载
https://www.zaproxy.org/download/

# 或使用 Chocolatey
choco install owasp-zap
```

### Linux (Kali/Ubuntu)
```bash
# Kali（预装）
zaproxy

# Ubuntu/Debian
sudo apt install zaproxy

# Snap
sudo snap install zaproxy --classic
```

### macOS
```bash
# Homebrew
brew install --cask owasp-zap

# 从官方网站下载 DMG
```

### Docker
```bash
# 拉取最新镜像
docker pull ghcr.io/zaproxy/zaproxy:stable

# 以守护进程模式运行 ZAP
docker run -u zap -p 8080:8080 -i ghcr.io/zaproxy/zaproxy:stable zap.sh -daemon -host 0.0.0.0 -port 8080

# 运行图形界面（Linux）
docker run -u zap -p 8080:8080 -p 8090:8090 -v /tmp:/zap/wrk/:rw \
    --rm -it ghcr.io/zaproxy/zaproxy:stable zap-webswing.sh
```

### 验证安装
```bash
# 启动 ZAP
zaproxy
zap.sh       # Linux
zap.bat      # Windows
```

---

<a id="-interface-overview"></a>
## 🖥️ 界面概览

### 主要面板

| 面板 | 说明 |
|-------|-------------|
| **站点树（Sites Tree）** | 按层级展示访问过的站点 |
| **历史记录（History）** | 所有请求与响应 |
| **告警（Alerts）** | 发现的漏洞 |
| **主动扫描（Active Scan）** | 当前扫描进度 |
| **爬虫（Spider）** | 已爬取的 URL |
| **搜索（Search）** | 搜索全部数据 |
| **请求/响应（Request/Response）** | 查看详情 |

### 工具栏图标

| 图标 | 功能 |
|------|----------|
| 🌐 | Quick Start 快速开始扫描 |
| 🔍 | 爬虫（Spider） |
| ⚡ | 主动扫描（Active Scan） |
| 🛑 | 断点/拦截（Break/Intercept） |
| 📊 | 报告（Reports） |
| ⚙️ | 选项（Options） |

### 模式

| 模式 | 说明 |
|------|-------------|
| **安全模式（Safe）** | 不执行危险操作 |
| **保护模式（Protected）** | 仅扫描范围内的目标 |
| **标准模式（Standard）** | 完整扫描（默认） |
| **攻击模式（ATTACK）** | 进攻性扫描 |

---

<a id="-proxy-configuration"></a>
## 🔌 代理配置

### 浏览器配置

**默认 ZAP 代理：**
```
主机（Host）： localhost (127.0.0.1)
端口（Port）： 8080
```

### Firefox 配置
```
1. 设置（Settings）→ 网络设置（Network Settings）→ 手动代理（Manual proxy）
2. HTTP 代理（HTTP Proxy）： 127.0.0.1  端口（Port）： 8080
3. 勾选“Also use this proxy for HTTPS”（HTTPS 也使用此代理）
4. 或使用 FoxyProxy 扩展
```

### 导入 ZAP 证书
```
1. 在浏览器中访问： http://zap
2. 点击“Download ZAP Root CA Certificate”（下载 ZAP 根 CA 证书）
3. 导入到浏览器：
   Firefox：设置（Settings）→ 隐私（Privacy）→ 证书（Certificates）→ 导入（Import）
   Chrome：设置（Settings）→ 安全（Security）→ 管理证书（Manage certificates）
```

### 配置 ZAP 代理选项
```
工具（Tools）→ 选项（Options）→ 本地代理（Local Proxies）

设置：
- 地址（Address）： localhost
- 端口（Port）： 8080
- Behind NAT（位于 NAT 后）：按需启用
- Remove unsupported encodings（移除不支持的编码）：启用
```

### 上游代理（代理链）
```
工具（Tools）→ 选项（Options）→ 网络（Network）→ 连接（Connection）

使用代理链：
- 地址（Address）： corporate-proxy.com
- 端口（Port）： 8080
- 按需设置身份验证
```

---

<a id="-manual-testing"></a>
## 🔧 手动测试

### 拦截请求（Break）

```
1. 点击工具栏中绿色的“Break”（断点）按钮（🛑）
2. 浏览应用
3. 修改拦截到的请求
4. 点击“Submit and Continue”（提交并继续）或“Drop”（丢弃）

断点选项：
- 在所有请求处中断
- 在所有响应处中断
- 在匹配指定 URL 模式时中断
```

### 请求编辑器（Request Editor）

```
右键单击请求 → Open/Resend with Request Editor（使用请求编辑器打开/重发）

功能：
- 修改请求的任意部分
- 添加或删除请求头
- 更改 HTTP 方法
- 发送并查看响应
```

### 手动请求（Manual Request）
```
工具（Tools）→ 手动请求编辑器（Manual Request Editor）

或： Ctrl+M

从头创建自定义请求
```

### 编码/解码/哈希（Encode/Decode/Hash）
```
工具（Tools）→ 编码/解码/哈希（Encode/Decode/Hash）

支持：
- URL 编码/解码
- Base64 编码/解码
- HTML 编码/解码
- 十六进制编码/解码
- MD5、SHA1、SHA256 哈希计算
```

### 搜索功能
```
搜索（Search）选项卡 → 输入匹配模式

搜索范围：
- URL
- 请求
- 响应
- 请求头/响应头
- 全部
```

---

<a id="-spider--crawler"></a>
## 🕷️ Spider 与爬虫

### 标准爬虫（Standard Spider）

```
右键单击目标 → Spider（爬虫）→ Spider...

或： Ctrl+Alt+S

选项：
- 最大爬取深度
- 最大子节点数
- 最长运行时间
- 处理参数
```

### 爬虫选项
```
工具（Tools）→ 选项（Options）→ Spider（爬虫）

设置：
- 最大深度（Max Depth）：5-10（默认：5）
- 最大子节点数（Max Children）：10
- 最长运行时间（Max Duration）：0（无限制）
- 线程数（Thread Count）：5
- 处理表单（Handle forms）：Yes（是）
- 提交表单（Post forms）：Yes（是）
```

### AJAX 爬虫（AJAX Spider）

```
对于大量使用 JavaScript 的应用：

右键单击目标 → AJAX Spider（AJAX 爬虫）→ AJAX Spider...

需要浏览器：
- Firefox（推荐）
- Chrome
- HtmlUnit（无头模式）
```

### AJAX 爬虫选项
```
工具（Tools）→ 选项（Options）→ AJAX Spider（AJAX 爬虫）

设置：
- 浏览器（Browser）：Firefox
- 最大爬取深度（Max Crawl Depth）：10
- 最大爬取状态数（Max Crawl States）：0（无限制）
- 最长运行时间（Max Duration）：60 分钟
- 点击默认元素
- 点击带有 onclick 等事件的元素
```

---

<a id="-active-scanning"></a>
## ⚡ 主动扫描

### 启动主动扫描

```
右键单击目标 → Active Scan...（主动扫描）

或： Ctrl+Alt+A

配置：
- 起始点（Starting Point）
- 上下文（Context，可选）
- 用户（User，用于身份验证）
- 策略（Policy）
- 技术栈（Technology）
```

### 扫描策略

```
分析（Analyze）→ 扫描策略管理器（Scan Policy Manager）

创建或修改策略：
- 运行哪些测试
- 阈值（Threshold，对可疑现象的判定门槛）
- 强度（Strength，测试的进攻程度）
```

### 策略类别

| 类别 | 测试 |
|----------|-------|
| **注入（Injection）** | SQL、操作系统命令、LDAP |
| **客户端浏览器（Client Browser）** | XSS, DOM XSS |
| **服务器安全（Server Security）** | 远程文件包含 |
| **信息（Information）** | 路径遍历、信息泄露 |
| **其他（Miscellaneous）** | 用户可控的重定向 |

### 扫描强度

| 级别 | 说明 |
|-------|-------------|
| **低（Low）** | 请求较少，速度较快 |
| **中（Medium）** | 平衡 |
| **高（High）** | 请求较多，检查更深入 |
| **极高（Insane）** | 测试最多（速度较慢） |

### 选择技术栈
```
右键单击目标 → Technology（技术栈）→ Select...（选择）

按技术栈筛选扫描：
- PHP, ASP, JSP
- MySQL, PostgreSQL, Oracle
- Apache, IIS, Nginx
- Linux, Windows
```

---

<a id="-passive-scanning"></a>
## 👁️ 被动扫描

### 什么是被动扫描？

分析已有流量，不额外发送请求。

```
自动处理——持续分析经过代理的流量

可发现：
- 缺失安全响应头
- 信息泄露
- Cookie 问题
- 薄弱的 SSL/TLS 配置
- 私有 IP 地址泄露
```

### 被动扫描规则（Passive Scan Rules）

```
分析（Analyze）→ 扫描策略管理器（Scan Policy Manager）→ 被动扫描（Passive Scan）

规则包括：
- 应用错误信息泄露（Application Error Disclosure）
- Cookie 缺少 Secure 标志（Cookie Without Secure Flag）
- 跨域 JavaScript（Cross-Domain JavaScript）
- 信息泄露（Information Disclosure）
- 私有 IP 地址泄露（Private IP Disclosure）
- 缺少 X-Frame-Options 响应头（X-Frame-Options Header Missing）
```

### 配置被动扫描
```
工具（Tools）→ 选项（Options）→ 被动扫描（Passive Scan）

设置：
- Scan only in scope（仅扫描范围内目标）：推荐
- Max alerts per rule（每条规则的告警上限）：10
- Tags（标签）：启用
```

---

<a id="-contexts--sessions"></a>
## 🎯 上下文与会话

### 什么是上下文？

上下文（Context）将相关 URL 按逻辑分组，便于组织测试。

### 创建上下文
```
右键单击 Sites（站点）→ Include in Context（加入上下文）→ New Context（新建上下文）

或： 文件（File）→ 新建上下文（New Context）
```

### 上下文设置

| 设置项 | 用途 |
|---------|---------|
| **加入上下文（Include in Context）** | 要纳入的 URL |
| **从上下文中排除（Exclude from Context）** | 要排除的 URL |
| **技术栈（Technology）** | 服务器技术栈 |
| **身份验证（Authentication）** | 登录方式 |
| **会话管理（Session Management）** | Cookie/令牌处理 |
| **用户（Users）** | 测试用户账户 |

### 身份验证类型

| 类型 | 应用场景 |
|------|----------|
| **基于表单（Form-based）** | 登录表单 |
| **HTTP Basic** | Basic 认证 |
| **基于 JSON（JSON-based）** | API 身份验证 |
| **基于脚本（Script-based）** | 自定义身份验证 |

### 基于表单的身份验证
```
1. 在 ZAP 中记录登录请求
2. 右键单击 → Include in Context（加入上下文）
3. 上下文（Context）→ 身份验证（Authentication）→ Form-based（基于表单）
4. 设置 Login URL（登录 URL）
5. 设置 Login Request POST Data（登录请求的 POST 数据）
6. 配置用户名和密码参数
7. 设置已登录/已退出状态的判定标志
```

### 会话管理（Session Management）
```
上下文（Context）→ Session Management（会话管理）

类型：
- 基于 Cookie（最常用）
- HTTP 身份验证
- 基于脚本（Script-based）
```

### 用户（Users）
```
上下文（Context）→ Users（用户）→ Add（添加）

添加测试用户：
- Name（名称）：admin
- Credentials（凭据）：admin/password123
```

---

<a id="-fuzzer"></a>
## 🎲 模糊测试器（Fuzzer）

### 启动模糊测试器
```
1. 在 History（历史记录）中选择请求
2. 右键单击 → Attack（攻击）→ Fuzz...（模糊测试）
3. 选中要进行模糊测试的位置
4. 添加载荷（payload）
5. 开始（Start）
```

### 添加模糊测试位置
```
1. 在 Fuzzer（模糊测试器）对话框中，选中待测试文本
2. 点击“Add...” （添加）
3. 选择载荷类型
```

### 载荷类型

| 类型 | 说明 |
|------|-------------|
| **字符串（Strings）** | 自定义字符串列表 |
| **文件（File）** | 从文件加载 |
| **文件型载荷生成器（File Fuzzers）** | 内置字典 |
| **数字（Numbers）** | 数字序列 |
| **正则表达式（Regex）** | 根据正则表达式生成 |
| **脚本（Script）** | 自定义脚本 |

### 内置模糊测试载荷库
```
添加（Add）→ File Fuzzers（文件型载荷生成器）

类别：
- jbrofuzz（XSS、SQL 等）
- dirbuster（目录）
- fuzzdb（综合）
```

### 模糊测试器选项
```
选项：
- 并发线程数
- 请求之间的延迟
- 跟随重定向
- 显示全部结果或筛选后的结果
```

### 模糊测试示例：参数测试
```
1. 捕获请求：GET /page?id=1
2. 右键单击 → Fuzz（模糊测试）
3. 选中 "1" 
4. 添加 SQL 注入载荷
5. 启动模糊测试器
6. 按响应长度或状态码分析结果
```

---

<a id="-api--automation"></a>
## 🤖 API 与自动化

### 启用 API
```
工具（Tools）→ 选项（Options）→ API

设置：
- Enabled（启用）：Yes（是）
- API Key（API 密钥）：自动生成或自定义
- Permitted addresses（允许的地址）：localhost
```

### API 访问
```bash
# API 根路径
http://localhost:8080/JSON/

# 携带 API 密钥
http://localhost:8080/JSON/core/view/alerts/?apikey=YOUR_API_KEY

# 获取版本
curl "http://localhost:8080/JSON/core/view/version/?apikey=API_KEY"
```

### 常见 API 端点

| 端点 | 用途 |
|----------|---------|
| `/core/view/alerts/` | 获取所有告警 |
| `/core/view/hosts/` | 获取所有主机 |
| `/spider/action/scan/` | 启动爬虫 |
| `/ascan/action/scan/` | 启动主动扫描 |
| `/core/view/urls/` | 获取所有 URL |
| `/core/action/shutdown/` | 关闭 ZAP |

### API 示例

```bash
# 爬取一个 URL
curl "http://localhost:8080/JSON/spider/action/scan/?url=http://target.com&apikey=API_KEY"

# 检查爬虫状态
curl "http://localhost:8080/JSON/spider/view/status/?apikey=API_KEY"

# 启动主动扫描
curl "http://localhost:8080/JSON/ascan/action/scan/?url=http://target.com&apikey=API_KEY"

# 获取告警
curl "http://localhost:8080/JSON/core/view/alerts/?apikey=API_KEY"

# 生成 HTML 报告
curl "http://localhost:8080/OTHER/core/other/htmlreport/?apikey=API_KEY" > report.html
```

### Python API 客户端
```python
from zapv2 import ZAPv2

# 连接 ZAP
zap = ZAPv2(apikey='your-api-key', proxies={'http': 'http://127.0.0.1:8080'})

# 爬虫（Spider）
zap.spider.scan(url='http://target.com')

# 主动扫描（Active Scan）
zap.ascan.scan(url='http://target.com')

# 获取告警
alerts = zap.core.alerts()
for alert in alerts:
    print(f"{alert['risk']}: {alert['name']}")
```

---

<a id="-command-line"></a>
## ⌨️ 命令行

### 基本命令行选项
```bash
# 以守护进程模式启动 ZAP
zap.sh -daemon

# 在指定端口启动
zap.sh -daemon -port 8090

# 快速扫描
zap.sh -quickurl http://target.com -quickout report.html

# 基线扫描（Docker）
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t http://target.com
```

### 自动化框架（Automation Framework）
```bash
# 执行自动化扫描
zap.sh -cmd -autorun automation.yaml
```

### 自动化 YAML 示例
```yaml
env:
  contexts:
    - name: "Default Context"
      urls:
        - "http://target.com"

jobs:
  - type: spider
    parameters:
      url: "http://target.com"
      maxDuration: 5
      
  - type: passiveScan-wait
    parameters:
      maxDuration: 5
      
  - type: activeScan
    parameters:
      scanOnlyInScope: true
      
  - type: report
    parameters:
      template: "traditional-html"
      reportFile: "report.html"
```

### Docker 扫描

```bash
# 基线扫描（原文称仅被动扫描，爬取行为见译注）
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://target.com \
    -r report.html

# 完整扫描（主动）
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-full-scan.py \
    -t https://target.com \
    -r report.html

# API 扫描
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py \
    -t https://target.com/api/openapi.json \
    -f openapi \
    -r report.html
```

---

<a id="-add-ons"></a>
## 🧩 附加组件（Add-ons）

### 安装附加组件
```
帮助（Help）→ 检查更新（Check for Updates）→ Marketplace（市场）

或： 工具（Tools）→ Add-ons（附加组件）→ Marketplace（市场）
```

### 必备附加组件

| 附加组件 | 说明 |
|--------|-------------|
| **主动扫描规则（Active Scan Rules）** | 额外扫描规则 |
| **被动扫描规则（Passive Scan Rules）** | 额外被动扫描规则 |
| **FuzzDB** | 模糊测试载荷 |
| **Retire.js** | 检测存在漏洞的 JS 库 |
| **AJAX 爬虫（AJAX Spider）** | 支持 JavaScript 的爬虫 |
| **Selenium** | 浏览器自动化 |
| **Wappalyzer** | 技术栈识别 |
| **访问控制（Access Control）** | 访问控制测试 |
| **目录列表（Directory List）** | 目录字典 |

### 附加组件管理
```
# 列出已安装的组件
工具（Tools）→ Add-ons（附加组件）→ Installed（已安装）

# 更新全部
帮助（Help）→ Check for Updates（检查更新）→ Update All（更新全部）
```

---

<a id="-real-world-examples"></a>
## 🎬 实践示例

### 示例 1：快速 Web 扫描
```
1. 在 Quick Start（快速开始）选项卡中输入 URL
2. 点击“Attack”（攻击）
3. ZAP 将自动爬取并扫描
4. 完成后检查告警
```

### 示例 2：手动与自动化测试结合
```
1. 将浏览器代理配置为 ZAP
2. 手动浏览应用（探索全部功能）
3. 在 Sites（站点）中右键单击目标 → Spider（爬虫）
4. 等待爬虫完成
5. 右键单击目标 → Active Scan（主动扫描）
6. 检查告警
7. 生成报告
```

### 示例 3：经过身份验证的扫描
```
1. 为目标创建 Context（上下文）
2. 配置基于表单的身份验证
3. 添加测试用户凭据
4. 配置强制用户模式（Forced User Mode）
5. 以经过身份验证的用户身份爬取
6. 主动扫描（Active Scan）
```

### 示例 4：API 测试
```
1. 导入 API 定义（OpenAPI/Swagger）
2. 文件（File）→ 导入（Import）→ Import OpenAPI definition（导入 OpenAPI 定义）
3. 爬取 API
4. 主动扫描（Active Scan）
```

### 示例 5：CI/CD 集成
```bash
# Jenkins 流水线（Pipeline）
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://staging.myapp.com \
    -r zap_report.html \
    -x zap_report.xml
```

---

<a id="-quick-reference"></a>
## 📊 速查

### 常用操作

| 任务 | 操作方式 |
|------|--------|
| 启动代理 | ZAP 自动在 8080 端口启动 |
| 爬虫（Spider） | 右键单击 → Spider（爬虫） |
| 主动扫描（Active Scan） | 右键单击 → Active Scan（主动扫描） |
| 拦截 | 点击 Break（断点）按钮（🛑） |
| 模糊测试（Fuzz） | 右键单击请求 → Fuzz（模糊测试） |
| 生成报告（Generate Report） | 报告（Report）→ Generate Report（生成报告） |

### 键盘快捷键

| 快捷键 | 操作 |
|----------|--------|
| `Ctrl+Alt+S` | 爬虫对话框 |
| `Ctrl+Alt+A` | 主动扫描对话框 |
| `Ctrl+M` | 手动请求（Manual Request） |
| `Ctrl+B` | 切换断点状态 |
| `Ctrl+F` | 查找 |
| `Ctrl+R` | 重发请求 |

### 扫描模式

| 模式 | 适用情境 |
|------|----------|
| **安全模式（Safe）** | 不希望进行任何更改 |
| **保护模式（Protected）** | 仅测试指定范围 |
| **标准模式（Standard）** | 常规测试 |
| **攻击模式（ATTACK）** | 进攻性测试（须获得许可！） |

---

<a id="-tips--best-practices"></a>
## 💡 技巧与最佳实践

### 性能建议

1. **限定范围**
   ```
   定义上下文，仅扫描范围内的项目
   ```

2. **对 SPA（单页应用）使用 AJAX 爬虫**
   ```
   对现代应用应结合 AJAX 爬虫，不能只依赖标准爬虫
   ```

3. **配置技术栈**
   ```
   只选择相关技术栈，以缩短扫描时间
   ```

### 准确性建议

1. **先完成身份验证**
   ```
   配置身份验证，以扩大覆盖范围
   ```

2. **自动化前先手动探索**
   ```
   先手动浏览应用，发现全部功能
   ```

3. **检查所有告警**
   ```
   排查误报，验证实际存在的问题
   ```

### 工作流程

```
1. 配置代理
2. 手动探索（完成身份验证后）
3. 爬虫（标准 + AJAX）
4. 审查被动扫描结果
5. 主动扫描
6. 手动测试（模糊测试、边界情况）
7. 生成报告
```

---

## ⚠️ 法律免责声明

> **警告：** OWASP ZAP 是安全测试工具，仅应用于 **获得授权的测试**。
> 
> - ✅ 测试自己拥有的应用
> - ✅ 获得明确书面许可后进行测试
> - ✅ 用于测试或预发布环境
> - ❌ 切勿在未获授权时扫描生产环境
> - ❌ 切勿扫描不属于自己的系统
> 
> **未获授权的安全测试属于违法行为，可能导致刑事指控。**

---

<a id="-resources"></a>
## 📚 参考资源

### 官方资源
- [OWASP ZAP 网站](https://www.zaproxy.org/)
- [ZAP 文档](https://www.zaproxy.org/docs/)
- [ZAP GitHub](https://github.com/zaproxy/zaproxy)
- [ZAP API 文档](https://www.zaproxy.org/docs/api/)

### 学习资源
- [OWASP 测试指南](https://owasp.org/www-project-web-security-testing-guide/)
- [ZAP in Ten](https://www.zaproxy.org/zap-in-ten/)
- [ZAP 深入讲解视频](https://www.zaproxy.org/docs/videos/)

### 相关速查表
- [Burp Suite](../../Burp-Suite/README.md)
- [Nikto](../../Nikto/README.md)
- [SQLMap](../../SQLMap/README.md)

---

<p align="center">
  <b>⚡ 掌握 Web 应用安全测试！</b><br>
  <i>免费、强大，由社区推动</i>
</p>
