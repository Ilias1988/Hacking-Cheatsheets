# 🔗 GAU - 获取所有 URL（Get All URLs）

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **参数勘误：** 原文命令保持不变，但 `--subs` 用于包含子域名，并非静默或关闭横幅。`--blacklist` 排除的是扩展名，不能用来排除 `commoncrawl` 提供方；`--mt` / `--ft` 分别按 MIME 类型匹配或过滤，并不把参数当作 URL 扩展名正则表达式。因此下面相应示例与速查表的原文标签需要区别理解。参见 [GAU 官方参数说明](https://github.com/lc/gau#usage)。
>
> **结果解读：** 已知或历史 URL 不代表端点当前仍可访问；后续 `httpx` 等步骤会主动访问目标。`gf` 根据已配置的模式筛选候选 URL，本身不验证 SQLi、XSS 等漏洞。最后一个管道中的 HTTP 200 响应也不能证明载荷执行或 XSS 成立。配置项、默认值及提供方可用性应按所用版本核对，本文保留原始配置与技术字面量。

```
   ██████╗  █████╗ ██╗   ██╗
  ██╔════╝ ██╔══██╗██║   ██║
  ██║  ███╗███████║██║   ██║
  ██║   ██║██╔══██║██║   ██║
  ╚██████╔╝██║  ██║╚██████╔╝
   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ 
       Get All URLs——快速 URL 收集
```

<p align="center">
  <img src="https://img.shields.io/badge/GAU-URL_Discovery-blue?style=for-the-badge" alt="GAU">
  <img src="https://img.shields.io/badge/Wayback-green?style=for-the-badge" alt="Wayback">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 目录

- [什么是 GAU](#-what-is-gau)
- [安装](#-installation)
- [基本用法](#-basic-usage)
- [数据提供方](#-providers)
- [过滤](#-filtering)
- [集成](#-integration)
- [速查](#-quick-reference)

---

<a id="-what-is-gau"></a>
## 🎯 什么是 GAU

**GAU**（Get All URLs，获取所有 URL）可从多个来源获取已知 URL：

- 🔍 **Wayback Machine** - 历史 URL
- 🌐 **Common Crawl** - Web 爬取数据
- 📡 **AlienVault OTX** - 威胁情报中的 URL
- 🔒 **URLScan.io** - 已被扫描的 URL

### 为什么选择 GAU？

| 优势 | 说明 |
|---------|-------------|
| **被动信息收集** | 不直接访问目标 |
| **历史数据** | 发现旧的或被遗忘的端点 |
| **快速** | 并行获取 |
| **多个数据源** | 更全面的结果 |

---

<a id="-installation"></a>
## 🚀 安装

```bash
# 使用 Go 安装（推荐）
go install github.com/lc/gau/v2/cmd/gau@latest

# 从源码构建
git clone https://github.com/lc/gau.git
cd gau/cmd/gau
go build

# 验证
gau --version
```

---

<a id="-basic-usage"></a>
## 📖 基本用法

### 单个域名

```bash
# 基本用法
gau example.com

# 指定输出文件
gau example.com -o urls.txt

# 静默模式（无横幅；原文注释与参数不符，见译注）
gau --subs example.com
```

### 多个域名

```bash
# 从标准输入读取
echo "example.com" | gau

# 多个域名
cat domains.txt | gau

# 从文件读取
gau --threads 5 < domains.txt
```

### 包含子域名

```bash
# 在结果中包含子域名
gau --subs example.com
```

### 并发处理

```bash
# 设置工作线程数（原文默认值：1）
gau --threads 5 example.com

# 根据速率限制调整
gau --threads 2 example.com
```

---

<a id="-providers"></a>
## 🌐 数据提供方

### 可用数据提供方

| 数据提供方 | 说明 | 默认 |
|----------|-------------|---------|
| `wayback` | Wayback Machine | ✅ |
| `commoncrawl` | Common Crawl | ✅ |
| `otx` | AlienVault OTX | ✅ |
| `urlscan` | URLScan.io | ✅ |

### 选择数据提供方

```bash
# 仅使用指定提供方
gau --providers wayback example.com
gau --providers wayback,otx example.com

# 排除提供方（原文参数用法有误，见译注）
gau --blacklist commoncrawl example.com
```

### 提供方配置

```bash
# 配置文件位置
~/.gau.toml

# 配置示例
cat > ~/.gau.toml << EOF
threads = 5
verbose = false
retries = 3
subdomains = false
providers = ["wayback","commoncrawl","otx","urlscan"]
blacklist = []
json = false
EOF
```

---

<a id="-filtering"></a>
## 🔍 过滤

### 按状态码过滤

```bash
# 仅保留记录中返回 200 的 URL
gau --mc 200 example.com

# 多个状态码
gau --mc 200,301,302 example.com

# 排除指定状态码
gau --fc 404,500 example.com
```

### 按扩展名过滤（参数解释见译注）

```bash
# 匹配扩展名（原文参数用法有误，见译注）
gau --mt "\.js$" example.com
gau --mt "\.php$" example.com

# 排除扩展名（原文参数用法有误，见译注）
gau --ft "\.jpg$|\.png$|\.gif$|\.css$" example.com
```

### 按 MIME 类型过滤

```bash
# 按内容类型过滤
gau --mt "application/json" example.com
gau --mt "text/html" example.com
```

### 输出格式

```bash
# JSON 输出
gau --json example.com

# 详细输出模式
gau --verbose example.com
```

---

<a id="-integration"></a>
## 🔗 集成

### 配合 httpx

```bash
# 检查当前可访问的 URL
gau example.com | httpx -silent

# 显示状态码
gau example.com | httpx -sc -title
```

### 配合 grep / 提取参数

```bash
# 查找带参数的 URL
gau example.com | grep "="

# 查找指定模式
gau example.com | grep -E "\.php\?|\.asp\?"

# GF 匹配模式
gau example.com | gf xss
gau example.com | gf sqli
gau example.com | gf lfi
gau example.com | gf redirect
```

### 配合 urldedupe

```bash
# 去除重复项
gau example.com | urldedupe

# 替代方式：sort -u
gau example.com | sort -u
```

### 配合 uro

```bash
# 智能 URL 去重
gau example.com | uro
```

### 完整管道

```bash
# 完整 URL 收集管道
echo "example.com" | gau --subs --threads 5 | \
  uro | \
  httpx -silent -mc 200 | \
  grep "=" > params.txt
```

---

<a id="-quick-reference"></a>
## 📊 速查

### 基础命令

| 命令 | 说明 |
|---------|-------------|
| `gau domain.com` | 获取域名对应的 URL |
| `gau --subs domain.com` | 包含子域名 |
| `gau -o urls.txt domain.com` | 输出到文件 |
| `gau --threads 5 domain.com` | 多线程 |

### 过滤选项

| 参数 | 说明 |
|------|-------------|
| `--mc 200` | 匹配状态码 |
| `--fc 404` | 排除状态码 |
| `--mt "regex"` | 匹配正则表达式（原文标签有误，见译注） |
| `--ft "regex"` | 过滤正则表达式（原文标签有误，见译注） |
| `--json` | JSON 输出 |

### 数据提供方选项

| 参数 | 说明 |
|------|-------------|
| `--providers wayback,otx` | 使用指定提供方 |
| `--blacklist commoncrawl` | 排除提供方（原文参数用法有误，见译注） |

### 实用单行命令

```bash
# 获取所有 JS 文件
gau example.com | grep -E "\.js$" | sort -u

# 查找 API 端点
gau example.com | grep -iE "/api/|/v[0-9]/"

# 查找参数
gau example.com | grep "?" | grep "=" | sort -u

# 查找可能存在 SQLi 的候选 URL
gau example.com | gf sqli

# 查找可能存在 XSS 的候选 URL
gau example.com | gf xss

# 带参数且可访问的 URL
gau example.com | grep "=" | httpx -silent
```

---

## 🎯 漏洞赏金工作流程

```bash
# 1. 收集所有 URL
gau --subs --threads 5 target.com > all_urls.txt

# 2. 筛选值得关注的 URL
cat all_urls.txt | grep "=" | uro > params.txt

# 3. 检查当前可访问性
cat params.txt | httpx -silent > live_params.txt

# 4. 查找漏洞（此处仅按模式筛选候选项，见译注）
cat live_params.txt | gf sqli > sqli.txt
cat live_params.txt | gf xss > xss.txt
cat live_params.txt | gf lfi > lfi.txt
cat live_params.txt | gf redirect > redirect.txt

# 5. 测试
cat xss.txt | qsreplace '"><script>alert(1)</script>' | \
  httpx -silent -mc 200
```

---

## 📚 参考资源

- [GAU GitHub](https://github.com/lc/gau)
- [GF 模式库](https://github.com/1ndianl33t/Gf-Patterns)

### 相关速查表
- [漏洞赏金方法论](../../Bug-Bounty-Methodology/README.md)
- [Subfinder](../../Subfinder/README.md)
- [httpx](../../httpx/README.md)

---

<p align="center">
  <b>🔗 收集所有 URL！</b><br>
  <i>GAU——被动 URL 发现</i>
</p>
