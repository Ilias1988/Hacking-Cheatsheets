# 🔶 Burp Suite——完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    ____                      _____       _ __     
   / __ )__  ___________     / ___/__  __(_) /____
  / __  / / / / ___/ __ \    \__ \/ / / / / __/ _ \
 / /_/ / /_/ / /  / /_/ /   ___/ / /_/ / / /_/  __/
/_____/\__,_/_/  / .___/   /____/\__,_/_/\__/\___/ 
                /_/                                
    Web 应用安全测试平台
```

<p align="center">
  <img src="https://img.shields.io/badge/Burp-Suite-orange?style=for-the-badge" alt="Burp Suite">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/PortSwigger-blue?style=for-the-badge" alt="PortSwigger">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🌐 业界广泛使用的 Web 应用安全测试平台</b>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装与初始设置](#-installation--setup)
- [代理配置](#-proxy-configuration)
- [拦截流量](#-intercepting-traffic)
- [目标与测试范围](#-target--scope)
- [Spider／爬虫](#-spidercrawler)
- [Scanner](#-scanner)
- [Repeater](#-repeater)
- [Intruder](#-intruder)
- [Decoder](#-decoder)
- [Comparer](#-comparer)
- [Sequencer](#-sequencer)
- [扩展](#-extensions)
- [快捷键](#-keyboard-shortcuts)
- [测试流程](#-testing-workflow)
- [快速参考](#-quick-reference)
- [技巧与最佳实践](#-tips--best-practices)
- [参考资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**Burp Suite** 是用于 Web 应用安全测试的集成平台，由 PortSwigger 开发，广泛应用于 Web 渗透测试。

### 版本

> **原文版本说明：** 表中的 `$449/year` 是原文历史报价，购买前应核对 [官方价格](https://portswigger.net/burp/pro)。文中的独立 Spider、`Proxy → Options` 与 Extender 路径含旧界面名称；现行代理设置位于 Settings 对话框的 Proxy 页面，参见 [官方代理设置](https://portswigger.net/burp/documentation/desktop/settings/tools/proxy)。为便于与英文原文对照，本译文保留这些名称。

| 版本 | 功能 | 价格 |
|---------|----------|-------|
| **Community** | 基础代理与手动测试工具 | 免费 |
| **Professional** | Scanner、Intruder（不限速）与 Collaborator | $449/year |
| **Enterprise** | CI/CD 集成与团队功能 | 联系销售 |

### 核心工具

| 工具 | 说明 |
|------|-------------|
| **Proxy** | 拦截并修改 HTTP／HTTPS 流量 |
| **Spider** | 自动爬取 Web 应用 |
| **Scanner** | 自动发现漏洞 |
| **Intruder** | 自动执行自定义攻击 |
| **Repeater** | 手动修改请求 |
| **Decoder** | 编码与解码数据 |
| **Comparer** | 比较响应 |
| **Sequencer** | 分析令牌随机性 |

---

<a id="-installation--setup"></a>
## 📥 安装与初始设置

### 下载

```bash
# 从官方网站下载
https://portswigger.net/burp/releases

# 支持的平台：
# - Windows（安装程序／独立版本）
# - macOS（DMG）
# - Linux（脚本／JAR）
```

### 系统要求

> **配置更新：** 原文的 `JRE 8+`、磁盘和推荐内存数据属于旧要求。当前官方说明中，独立 JAR 至少需要 Java 21，原生安装程序自带私有运行时，参见 [启动说明](https://portswigger.net/burp/documentation/desktop/troubleshooting/launch-from-command-line)；硬件配置请核对 [系统要求](https://portswigger.net/burp/documentation/desktop/getting-started/system-requirements)。表中的原始数值保持不变。

| 要求 | 最低配置 | 推荐配置 |
|-------------|---------|-------------|
| RAM | 4 GB | 8+ GB |
| 磁盘 | 500 MB | 2+ GB |
| Java | JRE 8+ | 捆绑提供的 JRE |
| 显示分辨率 | 1280x800 | 1920x1080 |

### 首次启动配置

1. **选择项目类型**
   - Temporary project（临时项目，用于测试）
   - New project on disk（磁盘上的新项目，用于保存工作）
   - Open existing project（打开现有项目）

2. **配置**
   - Use Burp defaults（使用默认设置，适合初学者）
   - 从配置文件加载

3. **内存分配**
   ```bash
   # 为大型项目增加内存
   java -jar -Xmx4g burpsuite_pro.jar
   ```

---

<a id="-proxy-configuration"></a>
## 🔌 代理配置

### 浏览器配置

#### 手动设置代理

| 浏览器 | 代理设置位置 |
|---------|------------------------|
| Firefox | Settings → Network → Manual proxy（设置 → 网络 → 手动代理） |
| Chrome | 使用系统代理或扩展 |
| Safari | System Preferences → Network → Proxies（系统偏好设置 → 网络 → 代理） |

**默认 Burp 代理:**
```
Host: 127.0.0.1
Port: 8080
```

#### Firefox 配置
```
1. 打开 Firefox Preferences（偏好设置）
2. 搜索 "proxy"（代理）
3. 点击 "Settings..."（设置）
4. 选择 "Manual proxy configuration"（手动代理配置）
5. HTTP Proxy: 127.0.0.1  Port: 8080（HTTP 代理地址与端口）
6. 勾选 "Also use this proxy for HTTPS"（HTTPS 也使用此代理）
7. 点击 OK（确定）
```

#### FoxyProxy 扩展（推荐）
```
1. 安装 FoxyProxy 扩展
2. 添加新代理：
   - Title: Burp Suite（代理名称）
   - Proxy Type: HTTP（HTTP 代理类型）
   - Proxy IP: 127.0.0.1
   - Port: 8080
3. 测试时启用
```

### 安装 SSL／TLS 证书

```bash
# 第 1 步：启用代理后访问：
http://burp

# 第 2 步：点击 "CA Certificate" 下载 CA 证书

# 第 3 步：导入证书：
# Firefox: Settings → Certificates → Import → Trust for websites（设置 → 证书 → 导入 → 信任网站证书）
# Chrome: Settings → Security → Manage certificates → Import（设置 → 安全 → 管理证书 → 导入）
# 系统：添加到受信任的根证书
```

### 代理监听器（Proxy Listeners）

```
Proxy → Options → Proxy Listeners（代理 → 选项 → 代理监听器）

默认：127.0.0.1:8080

添加自定义监听器：
- 不同测试使用不同端口
- 绑定所有接口（0.0.0.0），用于移动设备测试
- 重定向到其他主机
```

### 透明代理（适用于不支持显式代理的客户端）

```
1. Proxy → Options → Proxy Listeners（代理 → 选项 → 代理监听器）
2. 编辑监听器 → Request handling（请求处理）
3. 启用 "Support invisible proxying"（支持透明代理）
4. 配置 hosts 文件或 DNS
```

---

<a id="-intercepting-traffic"></a>
## 🎣 拦截流量

### 拦截控制

| 按钮 | 功能 |
|--------|----------|
| **Intercept is on/off** | 切换拦截状态 |
| **Forward** | 将请求发送给服务器 |
| **Drop** | 丢弃请求 |
| **操作** | 更多操作菜单 |

### 拦截选项

```
Proxy → Options → Intercept Client Requests（拦截客户端请求）

规则示例：
- 仅拦截测试范围内的条目
- 仅拦截指定文件类型
- 排除图像与静态文件
```

### 修改请求

```http
# 原始请求
GET /page.php?id=1 HTTP/1.1
Host: target.com

# 拦截时修改后的请求
GET /page.php?id=1' OR '1'='1 HTTP/1.1
Host: target.com
```

### 匹配与替换（Match and Replace）

```
Proxy → Options → Match and Replace（匹配与替换）

常见规则：
- 移除安全响应头
- 修改 User-Agent
- 添加自定义请求头
- 替换请求参数
```

| 类型 | 匹配 | 替换 | 用途 |
|------|-------|---------|---------|
| 请求头 | `User-Agent: .*` | `User-Agent: Custom` | 更改 User-Agent |
| 请求体 | `password=test` | `password=admin` | 测试凭据 |
| 响应头 | `X-Frame-Options.*` | ` ` | 移除响应头 |
| 响应体 | `disabled` | `enabled` | 启用按钮 |

### 修改响应

```
Proxy → Options → Intercept Server Responses（拦截服务器响应）

启用以进行：
- 移除安全响应头
- 修改 JavaScript
- 更改响应内容
- 测试客户端控制措施
```

---

<a id="-target--scope"></a>
## 🎯 目标与测试范围

### 加入测试范围

```
方法 1：在 Site map 中右键 → Add to scope（加入测试范围）
方法 2：Target → Scope → Add（手动添加）
方法 3：从 Proxy history → Add to scope（从代理历史添加）
```

### 测试范围配置

```
Target → Scope（目标 → 测试范围）

纳入测试范围：
- Protocol: Any/HTTP/HTTPS
- Host/IP: target.com
- Port: Any/Specific
- File: Regex pattern（正则表达式模式）

模式示例：
.*\.target\.com$          # 所有子域名
^https://target\.com/app  # 指定路径
```

### 高级范围设置

```
Target → Scope → Advanced scope control（高级范围控制）

包含：
  Protocol: HTTPS
  Host: target.com
  Port: 443
  File: ^/api/.*

排除：
  Host: .*\.google\.com
  File: .*\.(jpg|png|gif|css|js)$
```

### 站点地图（Site map）

```
Target → Site map（目标 → 站点地图）

功能：
- 查看所有发现的内容
- 按测试范围过滤
- 搜索或过滤内容
- 高亮值得关注的条目
- 比较站点地图
```

---

<a id="-spidercrawler"></a>
## 🕷️ Spider／爬虫

### 启动 Spider

```
1. 在 Site map 中右键点击目标
2. 选择 "Spider this host" 或 "Spider this branch"（爬取此主机／分支）
3. 或：Target → Site map → 右键 → Scan → Crawl（扫描 → 爬取）
```

### Spider 设置

```
Spider → Options（旧版本），或
Dashboard → New scan → Crawl（新建爬取扫描）

爬虫设置：
- 最大链接深度
- 最长爬取时间
- 表单提交
- 登录处理
```

### 爬取配置

| 设置项 | 说明 | 推荐配置 |
|---------|-------------|-------------|
| **Link depth（链接深度）** | 跟随链接的深度 | 5-10 |
| **Max requests（最大请求数）** | 请求总数上限 | 1000-5000 |
| **Duplicate removal（去重）** | 跳过相似页面 | 启用 |
| **被动 scanning** | 爬取过程中扫描 | 启用 |

### 提交表单

```
Spider → Options → Application Login（应用登录，旧界面）

配置以下登录方式：
- 单组凭据
- 凭据列表
- 会话检测
```

---

<a id="-scanner"></a>
## 🔍 Scanner

> **功能范围说明：** 本节自动爬取与审计功能需要对应产品许可；官方桌面文档将运行扫描标为 Professional 功能，参见 [扫描文档](https://portswigger.net/burp/documentation/desktop/running-scans)。不能据此认为 Community 提供完整自动扫描能力。

### 扫描类型

| 类型 | 说明 | 适用场景 |
|------|-------------|----------|
| **被动** | 分析代理流量 | 持续启用 |
| **主动** | 发送 payload 以发现漏洞 | 取得许可后使用 |
| **完整** | 全面扫描 | 时间充足时 |
| **自定义** | 仅执行指定测试 | 有针对性的测试 |

### 启动扫描

```
方法 1：右键 → Scan（扫描）
方法 2：Dashboard → New scan（新建扫描）
方法 3：从其他工具发送到 Scanner
```

### 扫描配置

```
Dashboard → New scan → Scan configuration（扫描配置）

内置配置：
- Audit checks - all except JavaScript analysis（除 JavaScript 分析外的全部审计检查）
- Audit checks - critical issues only（仅检查严重问题）
- Audit checks - light, active（轻量主动审计）
- Audit checks - medium, active（中等强度主动审计）
- Audit checks - full, active（完整主动审计）
```

### 漏洞类别

```
Scanner 检查以下问题：

注入：
- SQL 注入
- 操作系统命令注入
- LDAP 注入
- XML／XPath 注入

跨站脚本：
- 反射型 XSS
- 存储型 XSS
- 基于 DOM 的 XSS

身份验证：
- 身份验证缺陷
- 会话管理问题
- 弱密码

其他：
- CSRF
- 目录遍历
- 文件包含
- 信息泄露
- 服务端问题
```

### 扫描结果

```
Dashboard → Issue activity（问题活动）
Target → Site map → Issues（站点地图中的问题）

每个问题包含：
- Severity（严重程度：High/Medium/Low/Info）
- Confidence（可信度：Certain/Firm/Tentative）
- 说明
- 修复建议
- 证据（请求／响应）
```

### 自定义扫描配置

```
1. Dashboard → New scan（新建扫描）
2. 选择 "Scan configuration"（扫描配置）
3. 点击 "New"（新建）或修改现有配置
4. 保存以供复用

自定义内容：
- 测试哪些漏洞
- 载荷强度
- 请求速率
- 身份验证处理
```

---

<a id="-repeater"></a>
## 🔁 Repeater

### 用途

手动测试并修改单个请求。

### 发送到 Repeater

```
1. 右键点击任意请求
2. 选择 "Send to Repeater"（发送到 Repeater）
3. 快捷键：Ctrl+R
```

### 使用 Repeater

```
1. 在左侧面板修改请求
2. 点击 "Send"（发送），或按 Ctrl+Space
3. 在右侧面板查看响应
4. 比较响应
```

### Repeater 功能

| 功能 | 说明 |
|---------|-------------|
| **多个标签页** | 测试多个请求 |
| **请求历史** | 使用 < > 按钮浏览历史 |
| **跟随重定向** | 切换自动跟随状态 |
| **处理 Cookie** | 自动更新 Cookie |
| **Content-Length** | 自动更新请求头 |

### 测试示例

```http
# 测试 SQL 注入
GET /user.php?id=1' HTTP/1.1

# 测试 XSS
GET /search?q=<script>alert(1)</script> HTTP/1.1

# 测试身份验证绕过
GET /admin HTTP/1.1
Cookie: role=admin

# 测试不安全的直接对象引用（IDOR）
GET /api/user/1 HTTP/1.1  # 自己的用户
GET /api/user/2 HTTP/1.1  # 其他用户
```

---

<a id="-intruder"></a>
## 🔫 Intruder

### 攻击类型

| 类型 | 位置数 | 载荷 | 适用场景 |
|------|-----------|----------|----------|
| **Sniper** | 1+ | 1 组载荷，每次一个位置 | 单个参数测试 |
| **Battering Ram** | 1+ | 1 组载荷，所有位置使用相同值 | 各处使用相同值 |
| **Pitchfork** | 1+ | 多组载荷，按位置并行配对 | 用户名与密码配对 |
| **Cluster Bomb** | 1+ | 多组载荷，尝试全部组合 | 完整凭据组合穷举 |

### 发送到 Intruder

```
1. 右键点击请求
2. 选择 "Send to Intruder"（发送到 Intruder）
3. 快捷键：Ctrl+I
```

### 设置插入位置

```
Intruder → Positions（载荷插入位置）

使用 § 符号标记插入点：

GET /login.php?user=§admin§&pass=§test§ HTTP/1.1

按钮：
- Add §——将选中内容标为插入点
- Clear §——清除标记
- Auto §——自动检测
```

### 载荷类型

| 类型 | 说明 |
|------|-------------|
| **Simple list（简单列表）** | 自定义字典 |
| **Runtime file（运行时文件）** | 从文件加载 |
| **Numbers（数字）** | 顺序或随机数字 |
| **Dates（日期）** | 日期序列 |
| **Brute forcer（字符穷举）** | 字符组合 |
| **Null payloads（空载荷）** | 空载荷（用于请求速率测试） |
| **Username generator（用户名生成器）** | 常见用户名模式 |

### 载荷处理

```
Intruder → Payloads → Payload Processing（载荷处理）

添加规则：
- 添加前缀／后缀
- URL 编码
- Base64 编码
- 哈希（MD5、SHA）
- 匹配／替换
```

### Grep - 匹配

```
Intruder → Options → Grep - Match（响应字符串匹配）

添加需要在响应中标记的字符串：
- "Login successful"
- "Invalid password"
- "error"
- 自定义正则表达式

帮助识别成功的攻击。
```

### 攻击示例

#### 登录穷举
```
POST /login HTTP/1.1
Content-Type: application/x-www-form-urlencoded

username=§admin§&password=§password§

Attack type（攻击类型）：Cluster bomb
Payload 1（载荷 1）：用户名列表
Payload 2（载荷 2）：密码列表
```

#### 参数模糊测试
```
GET /api/user/§1§ HTTP/1.1

Attack type（攻击类型）：Sniper
Payload（载荷）：Numbers 1-1000（数字 1–1000）
```

#### 内容发现
```
GET /§admin§ HTTP/1.1

Attack type（攻击类型）：Sniper
Payload（载荷）：目录字典
Filter（过滤）：Status code != 404
```

### 结果分析

```
攻击完成后：
- 按长度排序（寻找不同响应）
- 按状态码排序
- 按 grep 匹配结果过滤
- 导出结果
```

---

<a id="-decoder"></a>
## 🔓 Decoder

### 编码与解码

```
Decoder 标签页

操作：
- URL 编码／解码
- HTML 编码／解码
- Base64 编码／解码
- 十六进制编码／解码
- Gzip 压缩／解压
- ASCII Hex（ASCII 十六进制）
- 二进制
```

### 智能解码（Smart decode）

```
Decoder → Smart decode（智能解码）

自动识别并解码：
- Base64
- URL 编码
- HTML 实体
- 组合编码
```

### 常见用途

> **JWT 说明：** 原文令牌中的 `...` 是省略占位符。常见签名 JWT 的各段采用 Base64url 编码，必须分别处理；解码不等于验证签名，参见 [RFC 7515](https://www.rfc-editor.org/rfc/rfc7515.html)。原始示例与编码字符串保持不变。

```
# 解码 JWT 令牌
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
→ Decode as Base64（按 Base64 解码）

# 解码 URL 参数
%3Cscript%3Ealert(1)%3C%2Fscript%3E
→ URL 解码
→ <script>alert(1)</script>

# 编码 payload
<script>alert(1)</script>
→ Base64 编码
→ 用于攻击
```

---

<a id="-comparer"></a>
## 📊 Comparer

### 用途

比较两份数据，可以是请求、响应或其他任意数据。

### 使用方法

```
1. 右键点击条目 1 → Send to Comparer（发送到 Comparer）
2. 右键点击条目 2 → Send to Comparer（发送到 Comparer）
3. 转到 Comparer 标签页
4. 选择条目并点击 "Words"（逐词）或 "Bytes"（逐字节）
```

### 比较模式

| 模式 | 说明 |
|------|-------------|
| **Words** | 逐词比较 |
| **Bytes** | 逐字节精确比较 |

### 适用场景

```
- 比较登录成功与失败的响应
- 寻找会话令牌差异
- 比较修改前后的数据
- 识别隐藏表单字段
```

---

<a id="-sequencer"></a>
## 🔢 Sequencer

### 用途

分析令牌的随机性质量，例如会话 ID 与 CSRF 令牌。

### 使用方法

```
1. 捕获会返回令牌的请求
2. 右键 → Send to Sequencer（发送到 Sequencer）
3. 配置令牌位置
4. 启动实时捕获
5. 分析结果
```

### 分析指标

| 指标 | 良好结果 | 说明 |
|--------|------------|-------------|
| **Overall quality（总体质量）** | >100 bits | 随机性评分 |
| **Character-level（字符级）** | 高熵 | 逐字符随机性 |
| **Bit-level（比特级）** | 高熵 | 逐比特随机性 |

### 结果解读

> **原文结论校准：** Sequencer 对样本进行统计评估，结果受样本量和显著性水平影响，可能误判；“Excellent”不能单独证明令牌具有密码学安全性，`>100 bits` 也不是适用于所有场景的统一合格线。参见 [官方结果说明](https://portswigger.net/burp/documentation/desktop/tools/sequencer/results)。

```
结果：
- Excellent（极佳）：令牌具有密码学随机性（原文结论，参见译注）
- Good（良好）：适用于多数用途  
- Poor（较差）：可能可预测
- Very poor（极差）：令牌可能被猜中
```

---

<a id="-extensions"></a>
## 🧩 扩展

### BApp Store

```
Extender → BApp Store（扩展商店，旧界面名称）

常用扩展：
```

### 常用扩展

| 扩展 | 说明 |
|-----------|-------------|
| **Autorize** | 授权测试 |
| **Logger++** | 增强日志功能 |
| **Retire.js** | 检测存在漏洞的 JavaScript 库 |
| **主动 Scan++** | 额外的扫描检查 |
| **AuthMatrix** | 授权矩阵测试 |
| **Turbo Intruder** | 高速 Intruder 替代工具 |
| **Param Miner** | 发现隐藏参数 |
| **JSON Web Tokens** | JWT 分析与修改 |
| **Hackvertor** | 高级编码与解码 |
| **CO2** | 实用工具集合 |

### 安装扩展

```
方法 1：BApp Store
1. Extender → BApp Store（扩展商店，旧界面名称）
2. 查找扩展
3. 点击 Install（安装）

方法 2：手动安装
1. 下载 .jar 或 .py 文件
2. Extender → Extensions → Add（手动添加扩展，旧界面）
3. 选择文件类型与路径
```

### 扩展设置

```
Extender → Options（扩展选项，旧界面）

- Python 环境（用于 Python 扩展）
- Java 环境设置
- 日志选项
```

---

<a id="-keyboard-shortcuts"></a>
## ⌨️ 快捷键

> **快捷键说明：** 原文为 `Ctrl+Shift+T` 列出了不同功能，因此不能把此表视为通用默认映射。快捷键受平台和用户配置影响，应在 `Settings → User interface → Hotkeys` 核对，参见 [官方设置说明](https://portswigger.net/burp/documentation/desktop/settings/ui/hotkeys)。原表按原样保留。

### 全局快捷键

| 快捷键 | 操作 |
|----------|--------|
| `Ctrl+I` | 发送到 Intruder |
| `Ctrl+R` | 发送到 Repeater |
| `Ctrl+S` | 搜索 |
| `Ctrl+F` | Forward（Proxy，转发） |
| `Ctrl+D` | Drop（Proxy，丢弃） |
| `Ctrl+T` | 切换 Intercept 状态 |
| `Ctrl+Shift+T` | 切换 Proxy 拦截状态 |

### 标签页导航

| 快捷键 | 操作 |
|----------|--------|
| `Ctrl+Shift+D` | Dashboard |
| `Ctrl+Shift+T` | Target |
| `Ctrl+Shift+P` | Proxy |
| `Ctrl+Shift+I` | Intruder |
| `Ctrl+Shift+R` | Repeater |

### 在 Repeater 中

| 快捷键 | 操作 |
|----------|--------|
| `Ctrl+Space` | 发送请求 |
| `Ctrl+G` | 转到请求历史 |
| `Ctrl+Plus` | 新建标签页 |

### 在编辑器中

| 快捷键 | 操作 |
|----------|--------|
| `Ctrl+U` | 对选中内容进行 URL 编码 |
| `Ctrl+Shift+U` | 对选中内容进行 URL 解码 |
| `Ctrl+B` | Base64 编码 |
| `Ctrl+Shift+B` | Base64 解码 |

---

<a id="-testing-workflow"></a>
## 🎯 测试流程

### 标准 Web 应用测试流程

```
第 1 步：设置
├── 配置浏览器代理
├── 安装 SSL 证书
└── 定义测试范围

第 2 步：侦察
├── 手动浏览应用
├── 运行 Spider／爬虫
└── 检查 Site map（站点地图）

第 3 步：分析
├── 检查 Proxy history（代理历史）
├── 检查被动扫描结果
└── 识别攻击面

第 4 步：测试
├── 使用 Repeater 测试（手动）
├── 使用 Intruder 测试（自动）
├── 运行主动扫描
└── 使用扩展

第 5 步：报告
├── 复核全部发现
├── 生成报告
└── 记录证据
```

### 测试检查清单

```
身份验证：
□ 登录穷举
□ 密码策略
□ 账户锁定
□ 会话管理
□ “记住我”功能

授权：
□ IDOR 测试
□ 权限提升
□ 功能级访问控制

输入验证：
□ SQL 注入
□ XSS（反射型／存储型／DOM 型）
□ 命令注入
□ 文件上传
□ XXE

会话：
□ 会话固定
□ 会话超时
□ 令牌随机性

业务逻辑：
□ 价格篡改
□ 工作流程绕过
□ 竞态条件
```

---

<a id="-quick-reference"></a>
## 📊 快速参考

### 工具概览

| 工具 | 主要用途 | 主要快捷键 |
|------|-------------|--------------|
| Proxy | 拦截流量 | Ctrl+T（切换） |
| Repeater | 手动测试 | Ctrl+R（发送到） |
| Intruder | 自动攻击 | Ctrl+I（发送到） |
| Scanner | 发现漏洞 | 右键 → Scan（扫描） |
| Spider | 发现内容 | 右键 → Spider（爬取） |
| Decoder | 编码／解码 | - |
| Comparer | 比较响应差异 | 发送到 Comparer |
| Sequencer | 令牌分析 | 发送到 Sequencer |

### Intruder 攻击类型速查

| 攻击类型 | 使用情形 |
|--------|----------|
| **Sniper** | 每次测试一个参数 |
| **Battering Ram** | 所有位置使用相同载荷 |
| **Pitchfork** | Username:password（用户名与密码）配对 |
| **Cluster Bomb** | 全部用户名 × 密码组合 |

### 常见状态码

| 状态码 | 含义 | 对 Intruder 分析的意义 |
|------|---------|----------------------|
| 200 | OK | 正常响应 |
| 301/302 | 重定向 | 可能表示成功 |
| 401 | 未通过身份验证 | 凭据错误 |
| 403 | 禁止访问 | 访问被拒绝 |
| 404 | 未找到 | 无效路径 |
| 500 | 服务器错误 | 可能存在漏洞 |

---

<a id="-tips--best-practices"></a>
## 💡 技巧与最佳实践

### 性能技巧

1. **限制测试范围**
   ```
   仅将目标域名保留在测试范围内
   减少 Proxy history 中的干扰信息
   ```

2. **禁用扩展**
   ```
   禁用不使用的扩展以改善性能
   ```

3. **使用项目文件**
   ```
   经常保存工作
   大型测试使用项目文件
   ```

### 测试技巧

1. **先进行被动分析**
   ```
   让被动扫描器先发现较明显的问题
   主动扫描前先复核
   ```

2. **优先使用 Repeater**
   ```
   手动测试可揭示更多问题
   自动化之前先理解应用
   ```

3. **使用标签页组织工作**
   ```
   为 Repeater 标签页设置清晰的名称
   为 Intruder 攻击命名
   ```

### 减少被检测的可能

```
减少检测的设置：

Project options → Connections:（项目选项 → 连接，旧界面）
- 限制请求速率
- 随机延迟
- 并发连接数上限

Intruder → Options:（Intruder 选项）
- 请求延迟
- 开始时间
```

### 故障排查

> **原文说明：** 开启响应拦截并不是解决所有“请求缺失”的通用方法；需同时检查浏览器是否使用了正确代理、监听器是否工作，以及 HTTP history 的过滤／记录设置，参见 [代理设置文档](https://portswigger.net/burp/documentation/desktop/settings/tools/proxy)。

| 问题 | 解决方法 |
|-------|----------|
| 无法拦截 HTTPS | 安装 CA 证书 |
| 运行缓慢 | 缩小测试范围，禁用扩展 |
| 请求缺失 | 检查 "Intercept Server Responses"（拦截服务器响应） |
| Scanner 未发现漏洞 | 提高扫描强度 |

---

## ⚠️ 法律免责声明

> **警告：** Burp Suite 是功能强大的安全测试工具，只能用于**已获授权的测试**。
> 
> - ✅ 测试自己拥有的应用
> - ✅ 取得明确书面许可后测试
> - ✅ 在专用测试环境中测试
> - ❌ 未经授权，不得扫描生产系统
> - ❌ 不得用于未授权访问
> 
> **未经授权的安全测试属于违法行为，可能导致刑事指控。**

---

<a id="-resources"></a>
## 📚 参考资源

### 官方资源
- [PortSwigger Web Security Academy](https://portswigger.net/web-security)
- [Burp Suite 文档](https://portswigger.net/burp/documentation)
- [BApp Store](https://portswigger.net/bappstore)

### 学习资源
- [OWASP 测试指南](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### 相关速查表
- [SQLMap](../../SQLMap/translations/README.zh-CN.md)
- [Metasploit Framework](../../Metasploit/translations/README.zh-CN.md)

---

<p align="center">
  <b>🔶 掌握 Web 应用安全测试！</b><br>
  <i>实践是学习的好方法——搭建实验环境并动手练习！</i>
</p>
