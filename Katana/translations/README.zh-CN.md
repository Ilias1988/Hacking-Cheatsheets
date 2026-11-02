# ⚔️ Katana：新一代 Web 爬虫

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗  ██╗ █████╗ ████████╗ █████╗ ███╗   ██╗ █████╗ 
  ██║ ██╔╝██╔══██╗╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗
  █████╔╝ ███████║   ██║   ███████║██╔██╗ ██║███████║
  ██╔═██╗ ██╔══██║   ██║   ██╔══██║██║╚██╗██║██╔══██║
  ██║  ██╗██║  ██║   ██║   ██║  ██║██║ ╚████║██║  ██║
  ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝
         新一代 Web 爬取框架
```

<p align="center">
  <img src="https://img.shields.io/badge/Katana-ProjectDiscovery-blue?style=for-the-badge" alt="Katana">
  <img src="https://img.shields.io/badge/Web_Crawler-green?style=for-the-badge" alt="爬虫">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="漏洞赏金"><br>
  <a href="./README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="./README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 目录

- [什么是 Katana](#-what-is-katana)
- [安装](#-installation)
- [基本用法](#-basic-usage)
- [爬取选项](#-crawling-options)
- [无头浏览器模式（Headless）](#-headless-mode)
- [输出与过滤](#-output--filtering)
- [快速参考](#-quick-reference)

---

<a id="-what-is-katana"></a>
## 🎯 什么是 Katana

**Katana** 是 ProjectDiscovery 开发的新一代 Web 爬取框架：

- ⚡ **快速** - 基于 Go，支持高并发
- 🔍 **JavaScript 渲染** - 支持无头浏览器
- 📡 **现代 Web** - 处理单页应用（SPA）与动态内容
- 🎯 **面向漏洞赏金研究** - 专为安全测试设计

### 为什么使用 Katana？

| 特性 | 优势 |
|---------|---------|
| 无头 Chrome | 爬取大量使用 JavaScript 的应用 |
| 自定义请求头 | 带身份验证的爬取 |
| 范围控制 | 限制在范围内 |
| 表单填充 | 发现更多端点 |

---

<a id="-installation"></a>
## 🚀 安装

```bash
# 使用 Go 安装（推荐）
go install github.com/projectdiscovery/katana/cmd/katana@latest

# Docker
docker pull projectdiscovery/katana

# 验证
katana -version
```

---

<a id="-basic-usage"></a>
## 📖 基本用法

### 单个目标

```bash
# 基本爬取
katana -u https://example.com

# 保存输出
katana -u https://example.com -o urls.txt

# 安静模式
katana -u https://example.com -silent
```

### 多个目标

```bash
# 从标准输入读取
echo "https://example.com" | katana

# 多个 URL
cat targets.txt | katana

# 从文件读取
katana -list targets.txt
```

### 爬取深度

> **译注：** `-d` 控制跟随 URL 的最大爬取深度；原文“深度 1 仅目标页”是过度简化，不能据此推断只会输出一个 URL。参见 [官方深度说明](https://github.com/projectdiscovery/katana#crawler-configuration)。

```bash
# 设置深度（默认：3）
katana -u https://example.com -d 5

# 深度 1（原文概括为仅目标页，实际深度语义见译注）
katana -u https://example.com -d 1
```

---

<a id="-crawling-options"></a>
## 🕷️ 爬取选项

### 范围控制

> **译注：** `dn` 按域名关键字限定范围，并不严格等同于“同一完整域名”；`rdn` 包括根域名及子域名，`fqdn` 限定给定子域名。参见 [官方范围说明](https://github.com/projectdiscovery/katana#scope-control)。

```bash
# 仅在范围内爬取（原文称同一域名）
katana -u https://example.com -fs dn

# 范围选项：
# dn：域名
# rdn：根域名
# fqdn：完全限定域名

# 字段范围
katana -u https://example.com -fs rdn
```

### 爬取策略

> **更正：** 原文误用了 `-cs`，它是爬取范围正则参数。策略由 `-s`/`-strategy` 控制；下方命令仍按原文保留，不能按注释理解。参见 [官方参数定义](https://github.com/projectdiscovery/katana/blob/main/cmd/katana/main.go)。

```bash
# 深度优先（默认）
katana -u https://example.com -cs depth-first

# 广度优先
katana -u https://example.com -cs breadth-first
```

### 并发数

```bash
# 并发请求数（默认：10）
katana -u https://example.com -c 50

# 速率限制（每秒请求数）
katana -u https://example.com -rl 100

# 并行目标数
katana -u https://example.com -p 10
```

### 爬取时长

```bash
# 设置请求超时（秒）
katana -u https://example.com -timeout 30

# 最长爬取时长
katana -u https://example.com -ct 300
```

### 自动表单填充

> **版本说明：** 当前官方使用表单配置文件自定义填充值，未定义原文的 `-fv` 参数；`-aff` 仍为实验功能。参见 [官方表单填充说明](https://github.com/projectdiscovery/katana#crawler-configuration)。

```bash
# 启用表单填充
katana -u https://example.com -aff

# 使用自定义值
katana -u https://example.com -aff -fv "username:admin,password:test123"
```

---

<a id="-headless-mode"></a>
## 🌐 无头浏览器模式（Headless）

### 启用无头浏览器

```bash
# 启用无头 Chrome
katana -u https://example.com -headless

# 设置无头模式的爬取深度
katana -u https://example.com -headless -d 3
```

### 无头模式选项

> **译注：** `-system-chrome` 使用已安装的 Chrome，不是自定义路径参数；当前帮助中也没有 `-screenshot`。这些原文示例需核对版本。参见 [官方无头模式选项](https://github.com/projectdiscovery/katana#usage)。

```bash
# 自定义 Chrome 路径（原文注释不准确，见译注）
katana -u https://example.com -headless -system-chrome

# 禁用沙箱（原文用于 root 场景）
katana -u https://example.com -headless -no-sandbox

# 对页面截图
katana -u https://example.com -headless -screenshot
```

### JavaScript 爬取

> **更正：** `-timeout` 是请求等待时间，单位为秒，不是以毫秒设置 JavaScript 加载等待；下方原文注释不准确。参见 [官方参数定义](https://github.com/projectdiscovery/katana/blob/main/cmd/katana/main.go)。

```bash
# 等待 JavaScript 加载（原文标为毫秒，-timeout 实际以秒计）
katana -u https://example.com -headless -timeout 5

# 爬取 JavaScript 中的端点
katana -u https://example.com -js-crawl
```

---

<a id="-output--filtering"></a>
## 📤 输出与过滤

### 输出格式

```bash
# 将标准输出保存到文件
katana -u https://example.com -o results.txt

# JSON 输出
katana -u https://example.com -jsonl -o results.json

# 禁用颜色
katana -u https://example.com -nc
```

### 显示字段

> **版本说明：** `-f` 已弃用，官方建议使用 `-output-template`；原文将 `method`、`status`、`body` 等混入旧式字段列表，不能假定 `-f url,method,status` 有效。原字段样例保留。参见 [官方字段说明](https://github.com/projectdiscovery/katana#filters)。

```bash
# 显示指定字段
katana -u https://example.com -f url,method,status

# 可用字段：
# url, path, fqdn, rdn, rurl, qurl, qpath
# file, kv, dir, ufile, udir
# method, status, body, response, header
```

### 按扩展名过滤

```bash
# 匹配扩展名
katana -u https://example.com -em js,php,asp

# 排除扩展名
katana -u https://example.com -ef png,jpg,gif,css,svg,woff
```

### 按状态码过滤

> **更正：** 当前 `-fc` 是表单配置文件路径，非状态码排除选项；原文的 `-mc` 也未列于现行帮助。当前条件过滤采用 DSL 匹配与过滤机制。参见 [官方参数定义](https://github.com/projectdiscovery/katana/blob/main/cmd/katana/main.go)。

```bash
# 匹配状态码
katana -u https://example.com -mc 200,301,302

# 排除指定状态码
katana -u https://example.com -fc 404,500
```

### 自定义请求头

```bash
# 添加请求头
katana -u https://example.com -H "Authorization: Bearer TOKEN"

# 多个请求头
katana -u https://example.com -H "Cookie: session=abc" -H "User-Agent: Custom"
```

### 代理支持

```bash
# HTTP 代理
katana -u https://example.com -proxy http://127.0.0.1:8080

# 配合 Burp Suite
katana -u https://example.com -proxy http://127.0.0.1:8080
```

---

<a id="-quick-reference"></a>
## 📊 快速参考

### 基本命令

| 命令 | 说明 |
|---------|-------------|
| `katana -u URL` | 爬取单个 URL |
| `katana -list file.txt` | 从文件读取目标爬取 |
| `katana -u URL -d 5` | 将深度设为 5 |
| `katana -u URL -headless` | 启用无头模式 |
| `katana -u URL -o out.txt` | 保存结果 |

### 范围选项

| 选项 | 说明 |
|------|-------------|
| `-fs dn` | 同一域名 |
| `-fs rdn` | 根域名 |
| `-fs fqdn` | 完整域名 |
| `-cs depth-first` | 深度优先爬取 |
| `-cs breadth-first` | 广度优先爬取 |

### 性能选项

| 选项 | 说明 |
|------|-------------|
| `-c 50` | 并发数 |
| `-p 10` | 并行目标数 |
| `-rl 100` | 速率限制 |
| `-timeout 30` | 请求超时 |

### 过滤选项

| 选项 | 说明 |
|------|-------------|
| `-em js,php` | 匹配扩展名 |
| `-ef png,jpg` | 排除扩展名 |
| `-mc 200` | 匹配状态码 |
| `-fc 404` | 排除指定状态码 |

---

## 🎯 漏洞赏金工作流程

### 基本爬取

```bash
# 快速爬取
katana -u https://target.com -d 3 -o urls.txt
```

### 综合爬取

```bash
# 结合 JavaScript 的深入爬取
katana -u https://target.com -d 5 -headless -c 50 \
  -ef png,jpg,gif,css,svg,woff,woff2,ttf,eot,ico \
  -o all_urls.txt
```

### 参数发现

```bash
# 查找带参数的 URL
katana -u https://target.com -d 3 | grep "?" | sort -u

# 使用 grep 筛选指定参数
katana -u https://target.com | grep -E "id=|user=|admin=|file="
```

### 结合其他工具

```bash
# Katana + httpx
katana -u https://target.com -d 3 | httpx -silent -sc

# Katana + Nuclei
katana -u https://target.com -d 3 | nuclei -t nuclei-templates/

# 完整流水线
subfinder -d target.com | httpx -silent | katana -d 3 | grep "=" | sort -u
```

### 带身份验证的爬取

```bash
# 携带会话 Cookie
katana -u https://target.com \
  -H "Cookie: session=abc123" \
  -d 5 \
  -headless \
  -o authenticated_urls.txt
```

---

## 📚 资源

- [Katana GitHub 仓库](https://github.com/projectdiscovery/katana)
- [ProjectDiscovery](https://projectdiscovery.io/)

### 相关速查表
- [漏洞赏金方法论](../../Bug-Bounty-Methodology/translations/README.zh-CN.md)
- [httpx](../../httpx/translations/README.zh-CN.md)
- [Nuclei](../../Nuclei/translations/README.zh-CN.md)
- [GAU](../../GAU/translations/README.zh-CN.md)

---

<p align="center">
  <b>⚔️ 全面发现 Web 页面与端点！</b><br>
  <i>Katana：新一代 Web 爬虫</i>
</p>
