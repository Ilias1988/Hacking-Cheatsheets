# 🎯 Nuclei 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
  _   _            _      _ 
 | \ | |_   _  ___| | ___(_)
 |  \| | | | |/ __| |/ _ \ |
 | |\  | |_| | (__| |  __/ |
 |_| \_|\__,_|\___|_|\___|_|
                            
    快速模板扫描器
```

<p align="center">
  <img src="https://img.shields.io/badge/Nuclei-Vulnerability_Scanner-red?style=for-the-badge" alt="Nuclei">
  <img src="https://img.shields.io/badge/9000+-Templates-orange?style=for-the-badge" alt="模板">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="漏洞赏金">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🔥 面向漏洞赏金研究者的常用模板化漏洞扫描器</b>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [基本语法](#-basic-syntax)
- [模板分类](#-template-categories)
- [扫描目标](#-scanning-targets)
- [模板选择](#-template-selection)
- [输出选项](#-output-options)
- [高级功能](#-advanced-features)
- [自定义模板](#-custom-templates)
- [漏洞赏金工作流程](#-bug-bounty-workflow)
- [实际场景示例](#-real-world-examples)
- [快速参考](#-quick-reference)
- [使用技巧与最佳实践](#-tips--best-practices)
- [资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

> **版本说明：** 模板数量、分类计数及路径保留原文示例，不是实时统计；`v3.x.x`、`CVE-2023-XXXXX` 等也是占位写法。现行官方示例使用 `http/cves/` 等路径，旧的 `cves/`、`exposed-panels/` 需与实际模板库核对。参见 [官方参数与示例](https://github.com/projectdiscovery/nuclei#command-line-flags)。

**Nuclei** 是 ProjectDiscovery 开发的快速模板化漏洞扫描器，被漏洞赏金研究者及安全研究人员广泛使用。

### 主要特性

| 特性 | 说明 |
|---------|-------------|
| **9000+ 个模板** | CVE、管理面板、配置错误、接管问题 |
| **快速高效** | 支持速率限制的并行扫描 |
| **基于模板** | 通过 YAML 模板便捷地自定义 |
| **社区驱动** | 持续更新的模板 |
| **多种协议** | HTTP, DNS, TCP, SSL, Headless |
| **易于集成** | 可配合 httpx、subfinder 等工具 |

### 模板类型

| 类型 | 说明 | 数量 |
|------|-------------|-------|
| **CVE** | 已知漏洞 | 2000+ |
| **暴露的面板** | 管理面板、仪表盘 | 500+ |
| **配置错误** | 安全配置错误 | 300+ |
| **默认登录凭据** | 默认凭据 | 200+ |
| **接管** | 子域名接管 | 100+ |
| **技术栈** | 技术栈识别 | 1000+ |
| **信息暴露** | 敏感文件暴露 | 500+ |

---

<a id="-installation"></a>
## 📥 安装

### 使用 Go（推荐）
```bash
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest
```

### 下载二进制程序
```bash
# 从发布页面下载
https://github.com/projectdiscovery/nuclei/releases

# Linux
wget https://github.com/projectdiscovery/nuclei/releases/download/v3.x.x/nuclei_3.x.x_linux_amd64.zip
unzip nuclei_3.x.x_linux_amd64.zip
sudo mv nuclei /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/nuclei:latest
docker run -it projectdiscovery/nuclei -h
```

### Kali Linux
```bash
sudo apt install nuclei
```

### 更新模板

> **译注：** `-ut` 与 `-update-templates` 都是更新模板，不能理解为同时更新扫描器本体；引擎更新另有 `-update`。参见 [官方更新参数](https://github.com/projectdiscovery/nuclei#command-line-flags)。
```bash
# 原文称更新 Nuclei 和模板（此参数仅更新模板，见译注）
nuclei -ut

# 仅更新模板
nuclei -update-templates
```

### 验证安装
```bash
nuclei -version
nuclei -tl              # 列出模板
```

---

<a id="-basic-syntax"></a>
## ⌨️ 基本语法

### 通用语法
```bash
nuclei [options] -target <url>
```

### 核心选项

| 选项 | 说明 |
|--------|-------------|
| `-u` | 单个目标 URL |
| `-l` | 包含 URL 列表的文件 |
| `-t` | 要运行的模板 |
| `-tags` | 具有指定标签的模板 |
| `-severity` | 按严重程度筛选 |
| `-o` | 输出文件 |
| `-silent` | 安静模式 |
| `-v` | 详细输出 |

### 快速入门
```bash
# 扫描单个目标
nuclei -u https://example.com

# 扫描目标列表
nuclei -l urls.txt

# 使用指定模板扫描
nuclei -u https://example.com -t cves/

# 使用标签扫描
nuclei -u https://example.com -tags cve,rce
```

---

<a id="-template-categories"></a>
## 📂 模板分类

### CVE 模板

```bash
# 所有 CVE 模板
nuclei -u target.com -t cves/

# 指定 CVE 年份
nuclei -u target.com -t cves/2024/
nuclei -u target.com -t cves/2023/

# 仅严重级别 CVE
nuclei -u target.com -t cves/ -severity critical

# 指定 CVE
nuclei -u target.com -t cves/2023/CVE-2023-XXXXX.yaml
```

### 暴露的面板

```bash
# 所有面板检测模板
nuclei -u target.com -t exposed-panels/

# 指定面板
nuclei -u target.com -tags phpmyadmin
nuclei -u target.com -tags jenkins
nuclei -u target.com -tags grafana
nuclei -u target.com -tags kibana
```

### 配置错误

```bash
# 所有配置错误模板
nuclei -u target.com -t misconfiguration/

# 指定类型
nuclei -u target.com -tags cors
nuclei -u target.com -tags headers
nuclei -u target.com -tags http-missing-security-headers
```

### 默认登录凭据

```bash
# 所有默认凭据模板
nuclei -u target.com -t default-logins/

# 指定厂商
nuclei -u target.com -tags tomcat-default
nuclei -u target.com -tags apache-default
```

### 子域名接管（Subdomain Takeover）

```bash
# 所有接管检查
nuclei -u target.com -t takeovers/

# 指定服务提供商
nuclei -u target.com -tags aws-takeover
nuclei -u target.com -tags azure-takeover
nuclei -u target.com -tags github-takeover
```

### 技术栈识别

```bash
# 技术栈识别
nuclei -u target.com -t technologies/

# CMS 检测
nuclei -u target.com -tags tech,wordpress
nuclei -u target.com -tags tech,joomla
```

### 信息暴露

```bash
# 敏感文件暴露
nuclei -u target.com -t exposures/

# 指定信息暴露类型
nuclei -u target.com -tags config
nuclei -u target.com -tags backup
nuclei -u target.com -tags logs
nuclei -u target.com -tags git
```

---

<a id="-scanning-targets"></a>
## 🎯 扫描目标

### 单个目标

```bash
nuclei -u https://example.com
nuclei -u https://example.com:8080
```

### 多个目标

```bash
# 从文件读取
nuclei -l targets.txt

# 从标准输入读取（管道）
cat targets.txt | nuclei
echo "https://example.com" | nuclei

# 在命令行中指定多个 URL
nuclei -u https://site1.com -u https://site2.com
```

### 配合其他工具

```bash
# 配合 subfinder（子域名）
subfinder -d example.com | nuclei

# 配合 httpx（存活主机）
subfinder -d example.com | httpx | nuclei

# 配合 katana（爬取到的 URL）
katana -u https://example.com | nuclei

# 完整侦察流水线
subfinder -d example.com -silent | httpx -silent | nuclei -silent
```

---

<a id="-template-selection"></a>
## 🏷️ 模板选择

### 按模板路径

```bash
# 单个模板
nuclei -u target.com -t cves/2023/CVE-2023-1234.yaml

# 模板目录
nuclei -u target.com -t cves/2023/
nuclei -u target.com -t exposed-panels/

# 多个目录
nuclei -u target.com -t cves/ -t exposed-panels/

# 自定义模板路径
nuclei -u target.com -t /path/to/custom/templates/
```

### 按标签

```bash
# 单个标签
nuclei -u target.com -tags cve

# 多个标签（OR，满足任意一个）
nuclei -u target.com -tags cve,rce,sqli

# 常用标签
nuclei -u target.com -tags xss
nuclei -u target.com -tags ssrf
nuclei -u target.com -tags lfi
nuclei -u target.com -tags rce
nuclei -u target.com -tags injection
```

### 按严重程度

```bash
# 单个严重程度
nuclei -u target.com -severity critical
nuclei -u target.com -severity high
nuclei -u target.com -severity medium
nuclei -u target.com -severity low
nuclei -u target.com -severity info

# 多个严重程度
nuclei -u target.com -severity critical,high

# 排除严重程度
nuclei -u target.com -exclude-severity info,low
```

### 排除模板

```bash
# 排除指定模板
nuclei -u target.com -exclude-templates cves/2020/

# 按标签排除
nuclei -u target.com -exclude-tags dos,fuzz

# 按 ID 排除
nuclei -u target.com -exclude-id CVE-2021-1234
```

---

<a id="-output-options"></a>
## 📤 输出选项

### 输出格式

> **版本说明：** 原文采用了 `-json` 写法。当前官方帮助使用 `-j`/`-jsonl` 表示 JSON Lines，并另列 `-json-export` 输出 JSON 文件；需核对本地版本，不能把 JSONL 与完整 JSON 数组混为一谈。原命令保持不变。参见 [官方输出参数](https://github.com/projectdiscovery/nuclei#command-line-flags)。

```bash
# 标准输出
nuclei -u target.com -o results.txt

# JSON 输出
nuclei -u target.com -json -o results.json

# JSON Lines（JSONL）
nuclei -u target.com -jsonl -o results.jsonl

# Markdown
nuclei -u target.com -markdown-export report/
```

### 输出过滤

```bash
# 安静模式（仅结果）
nuclei -u target.com -silent

# 禁用颜色
nuclei -u target.com -nc

# 详细输出
nuclei -u target.com -v

# 调试
nuclei -u target.com -debug
```

### 导出选项

```bash
# SARIF (GitHub)
nuclei -u target.com -sarif-export results.sarif

# Markdown 报告
nuclei -u target.com -me reports/
```

---

<a id="-advanced-features"></a>
## ⚡ 高级功能

### 速率限制

```bash
# 每秒请求数
nuclei -u target.com -rate-limit 100

# 批量大小
nuclei -u target.com -bulk-size 25

# 并发数
nuclei -u target.com -c 50

# 模板并发数
nuclei -u target.com -rl 100 -c 50
```

### 代理支持

```bash
# HTTP 代理
nuclei -u target.com -proxy http://127.0.0.1:8080

# SOCKS 代理
nuclei -u target.com -proxy socks5://127.0.0.1:9050

# Burp Suite 代理
nuclei -u target.com -proxy http://127.0.0.1:8080
```

### 身份验证

```bash
# 通过请求头进行身份验证
nuclei -u target.com -H "Authorization: Bearer token123"

# 通过 Cookie 进行身份验证
nuclei -u target.com -H "Cookie: session=abc123"

# 多个请求头
nuclei -u target.com -H "Authorization: Bearer token" -H "X-Custom: value"
```

### Interactsh 集成

```bash
# 使用 Interactsh 进行带外（OOB）测试
nuclei -u target.com -interactsh-server https://interact.sh

# 自定义 Interactsh 服务端
nuclei -u target.com -iserver https://my-interactsh.com
```

### 无头浏览器模式（Headless）

> **译注：** 选择 `headless/` 目录与启用浏览器能力不是同一件事；当前 CLI 使用 `-headless` 启用需要无头浏览器的模板。参见 [官方 Headless 参数](https://github.com/projectdiscovery/nuclei#command-line-flags)。

```bash
# 启用无头浏览器支持
nuclei -u target.com -headless

# 仅无头浏览器模板
nuclei -u target.com -t headless/
```

### 工作流

```bash
# 运行工作流
nuclei -u target.com -w workflows/wordpress-workflow.yaml

# 列出工作流
ls ~/.local/nuclei-templates/workflows/
```

---

<a id="-custom-templates"></a>
## 📝 自定义模板

### 模板结构

```yaml
id: my-custom-template

info:
  name: My Custom Template
  author: your-name
  severity: medium
  description: Description of what this template detects
  tags: custom,web

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
    matchers:
      - type: word
        words:
          - "Admin Panel"
```

### 模板示例

#### 检测指定页面

> **示例限制：** 下方模板仅按 HTTP 200 状态码匹配，不能单独证明页面是管理面板或存在漏洞；通用登录页、统一响应页等也可能返回 200。
```yaml
id: admin-panel-detect

info:
  name: Admin Panel Detection
  author: hunter
  severity: info
  tags: panel

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
      - "{{BaseURL}}/administrator"
      - "{{BaseURL}}/wp-admin"
    matchers-condition: or
    matchers:
      - type: status
        status:
          - 200
```

#### 检查响应头

> **原文问题：** 下方 YAML 在同一映射中重复定义了 `matchers` 键，不是两个匹配器自动组合。YAML 要求映射键唯一，解析器可能拒绝或覆盖前一项。此处按要求保留原模板，使用前应修正并验证；参见 [YAML 1.2.2 映射键唯一性](https://yaml.org/spec/1.2.2/#mapping)。
```yaml
id: missing-security-header

info:
  name: Missing X-Frame-Options
  author: hunter
  severity: low
  tags: headers

http:
  - method: GET
    path:
      - "{{BaseURL}}"
    matchers:
      - type: word
        words:
          - "text/html"
        part: header
    matchers:
      - type: word
        negative: true
        words:
          - "X-Frame-Options"
        part: header
```

### 校验模板

```bash
# 校验模板语法
nuclei -validate -t my-template.yaml

# 测试模板
nuclei -u target.com -t my-template.yaml -debug
```

---

<a id="-bug-bounty-workflow"></a>
## 🔄 漏洞赏金工作流程

### 完整侦察流水线

```bash
# 步骤 1：子域名枚举
subfinder -d target.com -o subs.txt

# 步骤 2：探测存活主机
cat subs.txt | httpx -o live.txt

# 步骤 3：运行 Nuclei
nuclei -l live.txt -severity critical,high -o findings.txt

# 单行命令
subfinder -d target.com -silent | httpx -silent | nuclei -severity critical,high
```

### 按优先级扫描

```bash
# 阶段 1：仅严重级别（快速）
nuclei -l targets.txt -severity critical -o critical.txt

# 阶段 2：高危级别
nuclei -l targets.txt -severity high -o high.txt

# 阶段 3：CVE
nuclei -l targets.txt -t cves/ -o cves.txt

# 阶段 4：完整扫描
nuclei -l targets.txt -o all.txt
```

### 针对性扫描

```bash
# WordPress 目标
nuclei -l wp-sites.txt -tags wordpress

# API 端点
nuclei -l apis.txt -tags api

# 登录页面
nuclei -l logins.txt -tags login,default-login
```

---

<a id="-real-world-examples"></a>
## 🎬 实际场景示例

### 示例 1：完整扫描
```bash
nuclei -u https://target.com -o results.txt
```

### 示例 2：仅严重 CVE（原命令还包含 high）
```bash
nuclei -u https://target.com -t cves/ -severity critical,high
```

### 示例 3：漏洞赏金快速扫描
```bash
nuclei -u https://target.com -tags cve,rce,sqli,xss,ssrf -severity critical,high
```

### 示例 4：子域名接管检查
```bash
cat subs.txt | nuclei -t takeovers/
```

### 示例 5：技术栈识别
```bash
nuclei -u https://target.com -t technologies/ -silent
```

### 示例 6：使用代理（Burp）
```bash
nuclei -u https://target.com -proxy http://127.0.0.1:8080
```

### 示例 7：带身份验证的扫描
```bash
nuclei -u https://target.com -H "Authorization: Bearer eyJhbG..."
```

### 示例 8：限制速率的扫描
```bash
nuclei -l targets.txt -rate-limit 10 -c 5
```

### 示例 9：用于后续处理的 JSON 输出
```bash
nuclei -u https://target.com -json -o results.json
```

### 示例 10：完整流水线
```bash
subfinder -d target.com -silent | \
httpx -silent | \
nuclei -severity critical,high -json -o results.json
```

---

<a id="-quick-reference"></a>
## 📊 快速参考

### 核心命令

| 任务 | 命令 |
|------|---------|
| 基本扫描 | `nuclei -u target.com` |
| 使用模板 | `nuclei -u target.com -t cves/` |
| 按标签 | `nuclei -u target.com -tags cve,rce` |
| 按严重程度 | `nuclei -u target.com -severity critical` |
| 从列表读取 | `nuclei -l urls.txt` |
| 更新模板 | `nuclei -ut` |
| 安静输出 | `nuclei -u target.com -silent` |
| JSON 输出 | `nuclei -u target.com -json -o out.json` |

### 常用标签

| 标签 | 说明 |
|-----|-------------|
| `cve` | CVE 漏洞 |
| `rce` | 远程代码执行 |
| `sqli` | SQL 注入 |
| `xss` | 跨站脚本 |
| `ssrf` | 服务端请求伪造 |
| `lfi` | 本地文件包含 |
| `panel` | 管理面板 |
| `takeover` | 子域名接管 |

### 严重程度级别

| 严重程度 | 颜色 | 优先级 |
|----------|-------|----------|
| `critical` | 🔴 红色 | 立即 |
| `high` | 🟠 橙色 | 高 |
| `medium` | 🟡 黄色 | 中 |
| `low` | 🟢 绿色 | 低 |
| `info` | 🔵 蓝色 | 信息性 |

---

<a id="-tips--best-practices"></a>
## 💡 使用技巧与最佳实践

### 性能建议

```bash
# 原文建议的大规模扫描设置（需根据环境调整）
nuclei -l targets.txt -c 50 -rl 150 -bs 25

# 在流水线中使用安静模式
nuclei -l targets.txt -silent -o results.txt
```

### 漏洞赏金建议

1. **从严重与高危级别开始**
   ```bash
   nuclei -l targets.txt -severity critical,high
   ```

2. **检查容易验证的问题**
   ```bash
   nuclei -l targets.txt -tags takeover,exposed-panels
   ```

3. **使用更新后的模板**
   ```bash
   nuclei -ut  # 挖掘前先更新模板
   ```

4. **通过速率限制提升稳定性**
   ```bash
   nuclei -l targets.txt -rl 50
   ```

### 隐蔽性建议

```bash
# 慢速、低输出
nuclei -l targets.txt -rl 10 -c 2

# 经由代理
nuclei -l targets.txt -proxy socks5://127.0.0.1:9050
```

---

## ⚠️ 法律免责声明

> **警告：** Nuclei 是功能强大的漏洞扫描器，仅可用于 **获得授权的测试**.
> 
> - ✅ 用于您获准测试的项目
> - ✅ 用于获得授权的漏洞赏金项目
> - ✅ 用于您自己的系统
> - ❌ 未经授权不得扫描
> - ❌ 遵守速率限制与项目范围
> 
> **未经授权的扫描属于违法且不合道德的行为。**

---

<a id="-resources"></a>
## 📚 资源

### 官方资源
- [Nuclei GitHub 仓库](https://github.com/projectdiscovery/nuclei)
- [Nuclei 模板](https://github.com/projectdiscovery/nuclei-templates)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [文档](https://docs.projectdiscovery.io/tools/nuclei/)

### 模板资源
- [社区模板](https://github.com/projectdiscovery/nuclei-templates)
- [模板指南](https://docs.projectdiscovery.io/templates/)

### 相关速查表
- [Nmap](../../Nmap/translations/README.zh-CN.md)
- [Burp Suite](../../Burp-Suite/translations/README.zh-CN.md)
- [SQLMap](../../SQLMap/translations/README.zh-CN.md)

---

<p align="center">
  <b>🎯 掌握漏洞扫描！</b><br>
  <i>漏洞赏金研究者的得力工具</i>
</p>
