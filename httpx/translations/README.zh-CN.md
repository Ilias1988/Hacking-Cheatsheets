# 🌐 httpx 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

```
  _     _   _           
 | |__ | |_| |_ _ __ __  __
 | '_ \| __| __| '_ \\ \/ /
 | | | | |_| |_| |_) |>  < 
 |_| |_|\__|\__| .__//_/\_\
               |_|         
                           
   快速 HTTP 探测工具集
```

<p align="center">
  <img src="https://img.shields.io/badge/httpx-HTTP_Probe-red?style=for-the-badge" alt="httpx">
  <img src="https://img.shields.io/badge/Fast-Toolkit-orange?style=for-the-badge" alt="快速">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="漏洞赏金">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>⚡ ProjectDiscovery 开发的快速多用途 HTTP 工具集</b><br>
  <a href="./README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="./README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [基本语法](#-basic-syntax)
- [探测与发现](#-probing--discovery)
- [信息提取](#-information-extraction)
- [过滤与匹配](#-filtering--matching)
- [输出选项](#-output-options)
- [高级功能](#-advanced-features)
- [漏洞赏金工作流程](#-bug-bounty-workflow)
- [实际场景示例](#-real-world-examples)
- [快速参考](#-quick-reference)
- [使用技巧与最佳实践](#-tips--best-practices)
- [资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

> **工具说明：** 本文指 ProjectDiscovery 的 Go 工具 httpx，与 Python HTTPX 库不是同一项目。Kali 为避免与 `python3-httpx` 冲突，将其命令打包为 `httpx-toolkit`；下文保留原文的 `httpx` 命令名。参见 [Kali 官方软件包说明](https://www.kali.org/tools/httpx-toolkit/)。

**httpx** 是一款快速多用途 HTTP 工具集，借助 retryablehttp 库运行多种探测，面向漏洞赏金研究者及安全研究人员。

### 主要特性

| 特性 | 说明 |
|---------|-------------|
| **快速** | 基于 Go 的高并发工具 |
| **多种探测** | 状态码、标题、技术栈、响应头 |
| **技术栈识别** | 基于 Wappalyzer 的识别 |
| **截图** | 使用无头浏览器截图 |
| **流水线** | 可配合 subfinder、nuclei 使用 |
| **JSON 输出** | 支持结构化输出 |

### httpx 与其他工具对比

> **译注：** 下表是简化比较，不是性能基准。原文将 curl 并发能力标为“否”不准确；curl 提供 `--parallel`，参见 [curl 官方并行选项说明](https://github.com/curl/curl/blob/master/docs/cmdline-opts/parallel.md)。

| 特性 | httpx | curl | wget |
|---------|-------|------|------|
| **速度** | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐ |
| **并发** | ✅ 是 | ❌ 否 | ❌ 否 |
| **技术栈识别** | ✅ 是 | ❌ 否 | ❌ 否 |
| **截图** | ✅ 是 | ❌ 否 | ❌ 否 |
| **流水线** | ✅ 是 | ⚠️ 手动 | ⚠️ 手动 |

---

<a id="-installation"></a>
## 📥 安装

### 使用 Go
```bash
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest
```

### Kali Linux
```bash
sudo apt install httpx-toolkit
```

### 下载二进制程序
```bash
# 从发布页面下载
https://github.com/projectdiscovery/httpx/releases

# Linux
wget https://github.com/projectdiscovery/httpx/releases/download/v1.x.x/httpx_1.x.x_linux_amd64.zip
unzip httpx_1.x.x_linux_amd64.zip
sudo mv httpx /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/httpx:latest
docker run -it projectdiscovery/httpx -h
```

### 验证安装
```bash
httpx -version
httpx -h
```

---

<a id="-basic-syntax"></a>
## ⌨️ 基本语法

### 通用语法
```bash
httpx [options] -u <url>
httpx [options] -l <file>
cat urls.txt | httpx [options]
```

### 核心选项

| 选项 | 说明 |
|--------|-------------|
| `-u` | 单个目标 URL |
| `-l` | 包含 URL 列表的文件 |
| `-sc` | 显示状态码 |
| `-title` | 显示页面标题 |
| `-td` | 技术栈识别 |
| `-o` | 输出文件 |
| `-silent` | 安静模式 |
| `-json` | JSON 输出 |

### 快速入门
```bash
# 探测单个 URL
httpx -u https://example.com

# 从文件读取目标进行探测
httpx -l urls.txt

# 从标准输入读取（管道）
cat hosts.txt | httpx

# 配合 subfinder
subfinder -d example.com -silent | httpx
```

---

<a id="-probing--discovery"></a>
## 🔍 探测与发现

### 基本探测

```bash
# 探测主机上的 HTTP/HTTPS 服务
cat hosts.txt | httpx

# 包含状态码
cat hosts.txt | httpx -sc

# 探测指定端口
cat hosts.txt | httpx -p 80,443,8080,8443

# 探测常见端口
cat hosts.txt | httpx -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```

### 端口扫描

```bash
# 自定义端口
httpx -l hosts.txt -p 80,443,8080,8443

# 端口范围
httpx -l hosts.txt -p 80-100

# 常见 Web 端口
httpx -l hosts.txt -p http:80,https:443
```

### 协议检测

> **译注：** 当前 `-no-fallback` 会显示两种已探测协议（HTTP/HTTPS），与仅按输入协议探测的 `-no-fallback-scheme` 不同。参见 [官方参数定义](https://github.com/projectdiscovery/httpx/blob/dev/runner/options.go)。

```bash
# HTTP/HTTPS 探测
cat hosts.txt | httpx

# 跟随重定向
cat hosts.txt | httpx -follow-redirects

# 最大重定向次数
cat hosts.txt | httpx -follow-redirects -max-redirects 5

# 禁用回退（具体语义见译注）
cat hosts.txt | httpx -no-fallback
```

---

<a id="-information-extraction"></a>
## 📊 信息提取

### 状态码与标题

```bash
# 状态码
httpx -l urls.txt -sc

# 页面标题
httpx -l urls.txt -title

# 两者
httpx -l urls.txt -sc -title

# 内容长度
httpx -l urls.txt -cl

# Web 服务器
httpx -l urls.txt -server

# 所有基本信息
httpx -l urls.txt -sc -title -cl -server
```

### 技术栈识别

```bash
# 识别技术栈
httpx -l urls.txt -td

# 技术栈识别（原文称详细模式，实际为同一选项别名）
httpx -l urls.txt -tech-detect

# 结合其他信息
httpx -l urls.txt -sc -title -td
```

### 响应头与响应

> **译注：** `-include-response-header` 是将响应头加入 JSON 输出的布尔选项，不接收响应头名称列表。原文 `-response-header` 也未列于当前官方帮助。原命令保留，参见 [官方输出选项](https://github.com/projectdiscovery/httpx#usage)。

```bash
# 响应头
httpx -l urls.txt -response-header

# 指定响应头（原文示例参数含义有误，见译注）
httpx -l urls.txt -include-response-header "Server,X-Powered-By"

# 响应体哈希
httpx -l urls.txt -hash md5

# 响应体预览
httpx -l urls.txt -bp
```

### IP 与 DNS 信息

```bash
# IP 地址
httpx -l urls.txt -ip

# CNAME
httpx -l urls.txt -cname

# CDN 检测
httpx -l urls.txt -cdn

# ASN 信息
httpx -l urls.txt -asn

# 所有 DNS 相关信息
httpx -l urls.txt -ip -cname -cdn -asn
```

### SSL/TLS 信息

```bash
# TLS 探测
httpx -l urls.txt -tls-probe

# TLS 信息获取
httpx -l urls.txt -tls-grab

# 证书信息
httpx -l urls.txt -tlsgrab
```

### 网站图标哈希

```bash
# 获取网站图标哈希（可用于 Shodan 搜索）
httpx -l urls.txt -favicon
```

### 截图获取

```bash
# 截取页面截图
httpx -l urls.txt -screenshot

# 截图并指定输出目录
httpx -l urls.txt -screenshot -screenshot-output ./screenshots/

# 无头浏览器选项
httpx -l urls.txt -screenshot -system-chrome
```

---

<a id="-filtering--matching"></a>
## 🎯 过滤与匹配

### 按状态码过滤

```bash
# 匹配状态码
httpx -l urls.txt -mc 200,301,302

# 排除指定状态码
httpx -l urls.txt -fc 404,403,500

# 匹配全部并排除指定状态码
httpx -l urls.txt -mc all -fc 404
```

### 按大小过滤

```bash
# 匹配内容长度
httpx -l urls.txt -ml 1234

# 排除指定内容长度
httpx -l urls.txt -fl 0

# 匹配行数
httpx -l urls.txt -mlc 100

# 排除指定行数
httpx -l urls.txt -flc 50
```

### 按内容过滤

> **更正：** `-fr` 表示跟随重定向；正则排除参数是 `-fe`/`-filter-regex`。下方原命令不能按其注释理解。`-mt`、`-ft` 也未列于当前技术栈过滤参数定义。参见 [官方匹配与过滤选项](https://github.com/projectdiscovery/httpx/blob/dev/runner/options.go)。

```bash
# 匹配响应体（正则表达式）
httpx -l urls.txt -mr "admin|login"

# 排除匹配的响应体（正则表达式；原文 -fr 含义有误）
httpx -l urls.txt -fr "error|not found"

# 匹配字符串
httpx -l urls.txt -ms "dashboard"
```

### 按技术栈过滤

```bash
# 匹配技术栈
httpx -l urls.txt -td -mt "WordPress,Nginx"

# 排除指定技术栈
httpx -l urls.txt -td -ft "Apache"
```

---

<a id="-output-options"></a>
## 📤 输出选项

### 输出格式

> **译注：** 当前 `-json` 输出 JSON Lines（每行一个 JSON 对象）；原文 `-jsonl` 未列于当前帮助。参见 [官方输出参数](https://github.com/projectdiscovery/httpx#usage)。

```bash
# 纯文本
httpx -l urls.txt -o results.txt

# JSON 输出
httpx -l urls.txt -json -o results.json

# JSON Lines
httpx -l urls.txt -jsonl -o results.jsonl

# CSV 输出
httpx -l urls.txt -csv -o results.csv
```

### 输出字段

```bash
# 保存响应
httpx -l urls.txt -sr -srd ./responses/

# 保存截图
httpx -l urls.txt -screenshot -ssd ./screenshots/

# 自定义输出
httpx -l urls.txt -o output.txt -sc -title -td
```

### 安静模式与详细输出

```bash
# 安静模式（仅 URL）
httpx -l urls.txt -silent

# 详细输出
httpx -l urls.txt -v

# 调试
httpx -l urls.txt -debug

# 禁用颜色
httpx -l urls.txt -nc
```

---

<a id="-advanced-features"></a>
## ⚡ 高级功能

### 线程与速率限制

```bash
# 设置线程数（默认：50）
httpx -l urls.txt -threads 100

# 速率限制
httpx -l urls.txt -rate-limit 100

# 超时
httpx -l urls.txt -timeout 10

# 重试
httpx -l urls.txt -retries 3
```

### 代理支持

```bash
# HTTP 代理
httpx -l urls.txt -proxy http://127.0.0.1:8080

# SOCKS 代理
httpx -l urls.txt -proxy socks5://127.0.0.1:9050

# Burp Suite
httpx -l urls.txt -proxy http://127.0.0.1:8080
```

### 自定义请求头

```bash
# 自定义请求头
httpx -l urls.txt -H "Authorization: Bearer token123"

# 多个请求头
httpx -l urls.txt -H "X-Custom: value" -H "Cookie: session=abc"

# 用户-Agent
httpx -l urls.txt -H "User-Agent: Mozilla/5.0"
```

### 请求方法

> **更正：** 当前 `-method` 显示请求方法，`-x` 才指定探测方法；不能把下方 `-method HEAD` 等原文示例视为正确的方法设置。参见 [官方请求参数](https://github.com/projectdiscovery/httpx/blob/dev/runner/options.go)。

```bash
# HEAD 请求（原文称其更快，实际视环境而定）
httpx -l urls.txt -method HEAD

# 自定义方法
httpx -l urls.txt -method OPTIONS

# 携带请求体的 POST 请求
httpx -l urls.txt -method POST -body '{"test":"data"}'
```

### 恢复与分页

> **版本说明：** 当前 `-resume` 使用 `resume.cfg`。本文的 `-resume-file`、截图示例中的 `-screenshot-output`/`-ssd`、`-tlsgrab` 等未列于当前参数定义，需核对具体版本；`v1.x.x` 下载链接也是占位示例。参见 [官方选项源码](https://github.com/projectdiscovery/httpx/blob/dev/runner/options.go)。

```bash
# 从上次位置恢复
httpx -l urls.txt -resume

# 保存进度（原文示例的参数支持需核对）
httpx -l urls.txt -resume -resume-file progress.txt
```

---

<a id="-bug-bounty-workflow"></a>
## 🔄 漏洞赏金工作流程

### 完整侦察流水线

```bash
# 步骤 1：子域名枚举
subfinder -d target.com -o subs.txt

# 步骤 2：使用 httpx 探测存活主机
cat subs.txt | httpx -sc -title -td -o live.txt

# 步骤 3：漏洞扫描
nuclei -l live.txt -severity critical,high

# 单行命令
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### 详细侦察

```bash
# 完整信息收集
subfinder -d target.com -silent | httpx \
    -sc \
    -title \
    -td \
    -ip \
    -cdn \
    -server \
    -follow-redirects \
    -o full-recon.txt
```

### 对所有存活主机截图

```bash
subfinder -d target.com -silent | httpx -silent -screenshot -ssd ./screenshots/
```

### 发现值得关注的端点

```bash
# 查找管理面板
subfinder -d target.com -silent | httpx -title -mr "admin|login|dashboard"

# 查找 API 端点
cat subs.txt | httpx -path /api/ -mc 200,401,403

# 检查指定路径
httpx -l hosts.txt -path /robots.txt -mc 200
```

### 针对特定技术栈的挖掘

```bash
# 查找 WordPress 站点
subfinder -d target.com -silent | httpx -td -mt "WordPress"

# 查找 Nginx 服务器
cat hosts.txt | httpx -server -ms "nginx"
```

---

<a id="-real-world-examples"></a>
## 🎬 实际场景示例

### 示例 1：基本探测
```bash
echo "https://example.com" | httpx -sc -title
```

### 示例 2：从子域名进行完整侦察
```bash
subfinder -d hackerone.com -silent | httpx -sc -title -td -ip
```

### 示例 3：对所有主机截图
```bash
httpx -l hosts.txt -screenshot -ssd ./screenshots/
```

### 示例 4：仅发现存活主机
```bash
cat subs.txt | httpx -silent -o live.txt
```

### 示例 5：技术栈识别
```bash
httpx -l urls.txt -tech-detect -json -o tech.json
```

### 示例 6：使用代理（Burp）
```bash
httpx -l urls.txt -proxy http://127.0.0.1:8080 -silent
```

### 示例 7：自定义端口
```bash
cat hosts.txt | httpx -p 80,443,8080,8443 -sc -title
```

### 示例 8：仅保留 200 状态码
```bash
cat subs.txt | httpx -mc 200 -silent
```

### 示例 9：用于后续处理的 JSON 输出
```bash
httpx -l urls.txt -json -sc -title -td -o results.json
```

### 示例 10：完整流水线
```bash
subfinder -d target.com -silent | \
httpx -silent -sc -title -td -ip -cdn -server -json | \
tee results.json | \
jq -r '.url' | \
nuclei -severity critical,high
```

---

<a id="-quick-reference"></a>
## 📊 快速参考

### 核心命令

| 任务 | 命令 |
|------|---------|
| 基本探测 | `httpx -u url` |
| 从文件读取 | `httpx -l urls.txt` |
| 状态码 | `httpx -l urls.txt -sc` |
| 页面标题 | `httpx -l urls.txt -title` |
| 技术栈识别 | `httpx -l urls.txt -td` |
| IP 地址 | `httpx -l urls.txt -ip` |
| 截图 | `httpx -l urls.txt -screenshot` |
| JSON 输出 | `httpx -l urls.txt -json -o out.json` |

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-u` | 单个 URL |
| `-l` | URL 列表文件 |
| `-sc` | 状态码 |
| `-title` | 页面标题 |
| `-td` | 技术栈识别 |
| `-ip` | IP 地址 |
| `-cdn` | CDN 检测 |
| `-server` | Web 服务器 |
| `-mc` | 匹配状态码 |
| `-fc` | 排除状态码 |
| `-json` | JSON 输出 |
| `-silent` | 安静模式 |

### 常用流水线

| 流水线 | 命令 |
|----------|---------|
| 存活主机 | `subfinder -d target.com \| httpx -silent` |
| 附带信息 | `subfinder -d target.com \| httpx -sc -title -td` |
| 传递给 Nuclei | `httpx -l hosts.txt -silent \| nuclei` |

---

<a id="-tips--best-practices"></a>
## 💡 使用技巧与最佳实践

### 漏洞赏金建议

1. **配合 Subfinder 使用**
   ```bash
   subfinder -d target.com -silent | httpx -silent -sc -title
   ```

2. **获取完整信息**
   ```bash
   httpx -l hosts.txt -sc -title -td -ip -cdn -server
   ```

3. **通过截图辅助分诊**
   ```bash
   httpx -l hosts.txt -screenshot
   ```

4. **使用 JSON 进行后续处理**
   ```bash
   httpx -l hosts.txt -json | jq '.url'
   ```

### 性能建议

```bash
# 高速
httpx -l hosts.txt -threads 100 -timeout 5

# 低速、较低并发
httpx -l hosts.txt -threads 10 -rate-limit 20
```

---

## ⚠️ 法律免责声明

> **警告：** httpx 会向目标发送 HTTP 请求，仅应对获得授权的系统使用。
> 
> - ✅ 用于您获准测试的项目
> - ✅ 范围内的漏洞赏金目标
> - ✅ 您自己的域名
> - ❌ 不得探测未经授权的目标
> - ❌ 遵守速率限制
> 
> **未经授权的探测可能违法。**

---

<a id="-resources"></a>
## 📚 资源

### 官方资源
- [httpx GitHub 仓库](https://github.com/projectdiscovery/httpx)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [文档](https://docs.projectdiscovery.io/tools/httpx/)

### 相关速查表
- [Subfinder](../../Subfinder/translations/README.zh-CN.md)
- [Nuclei](../../Nuclei/translations/README.zh-CN.md)
- [ffuf](../../ffuf/translations/README.zh-CN.md)

---

<p align="center">
  <b>🌐 全面开展 HTTP 探测！</b><br>
  <i>漏洞赏金研究常用的 HTTP 工具集</i>
</p>
