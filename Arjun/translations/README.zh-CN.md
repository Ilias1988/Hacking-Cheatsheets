# 🔍 Arjun - 隐藏参数发现

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

```
   █████╗ ██████╗      ██╗██╗   ██╗███╗   ██╗
  ██╔══██╗██╔══██╗     ██║██║   ██║████╗  ██║
  ███████║██████╔╝     ██║██║   ██║██╔██╗ ██║
  ██╔══██║██╔══██╗██   ██║██║   ██║██║╚██╗██║
  ██║  ██║██║  ██║╚█████╔╝╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚═╝  ╚═══╝
         HTTP 参数发现工具套件
```

<p align="center">
  <img src="https://img.shields.io/badge/Arjun-Parameter_Discovery-blue?style=for-the-badge" alt="Arjun">
  <img src="https://img.shields.io/badge/Hidden_Params-green?style=for-the-badge" alt="Hidden">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 目录

- [Arjun 是什么](#-what-is-arjun)
- [安装](#-installation)
- [基本用法](#-basic-usage)
- [发现模式](#-discovery-modes)
- [高级选项](#-advanced-options)
- [输出选项](#-output-options)
- [速查表](#-quick-reference)

---

<a id="-what-is-arjun"></a>
## 🎯 Arjun 是什么

**Arjun** 用于发现 Web 应用中的隐藏 HTTP 参数：

- 🔍 **参数发现** - 查找隐藏的 GET/POST 参数
- 🎯 **支持 JSON** - 发现 JSON 参数
- 📡 **多种请求模式** - GET、POST 和 JSON 模式
- ⚡ **速度快** - 支持高并发请求

### 为什么使用 Arjun？

| 功能 | 优势 |
|---------|---------|
| 隐藏参数 | 发现未被引用的端点 |
| 多种格式 | GET, POST, JSON |
| 被动模式 | 检查 JS 文件 |
| 从 Burp 导入 | 与现有工作流程衔接 |

---

<a id="-installation"></a>
## 🚀 安装

> **版本说明：** 下文保留原文的全部命令与示例；不同版本的参数可能不同。[官方 README](https://github.com/s0md3v/Arjun)现推荐使用 `pipx` 安装。另需区分“参数”与“端点”：Arjun 主要发现已有端点接受的参数，表格中的“发现端点”表述不够准确。

```bash
# 使用 pip 安装（原文推荐方式）
pip3 install arjun

# 从源码安装
git clone https://github.com/s0md3v/Arjun.git
cd Arjun
python3 setup.py install

# 验证安装
arjun --help
```

---

<a id="-basic-usage"></a>
## 📖 基本用法

### 单个 URL

```bash
# 基础参数发现
arjun -u https://example.com/endpoint

# 指定输出文件
arjun -u https://example.com/endpoint -o results.txt
```

### 多个 URL

```bash
# 从文件读取
arjun -i urls.txt

# 从标准输入读取（原文示例，参见下方兼容性说明）
cat urls.txt | arjun --stdin
```

> **输入参数勘误：** [当前官方命令行源码](https://github.com/s0md3v/Arjun/blob/master/arjun/__main__.py)定义 `-i` 读取文件，未定义 `--stdin`；相关管道示例须核对版本。

### 指定 HTTP 方法

```bash
# GET 参数（默认）
arjun -u https://example.com/search -m GET

# POST 参数
arjun -u https://example.com/api -m POST

# JSON 请求体
arjun -u https://example.com/api -m JSON
```

---

<a id="-discovery-modes"></a>
## 🔎 发现模式

### GET 参数

```bash
# 发现 GET 参数
arjun -u https://example.com/search?known=value -m GET

# 输出示例：
# [param] id
# [param] user
# [param] debug
```

### POST 参数

```bash
# 发现 POST 表单参数
arjun -u https://example.com/login -m POST

# 携带已有数据
arjun -u https://example.com/login -m POST --include 'username=admin'
```

### JSON 参数

```bash
# 发现 JSON 请求体中的参数
arjun -u https://example.com/api/v1/users -m JSON

# 携带基础 JSON 数据
arjun -u https://example.com/api -m JSON --include '{"known":"value"}'
```

### 组合发现

> **方法说明：** [官方请求实现](https://github.com/s0md3v/Arjun/blob/master/arjun/core/requester.py)按单个请求模式分支处理，没有拆分逗号列表。由此可知，原文的 `GET,POST,JSON` 等写法不能保证逐一测试这些模式，应分别选择所需模式。

```bash
# 所有请求方法（原文示例，参见上方说明）
arjun -u https://example.com/endpoint -m GET,POST,JSON
```

---

<a id="-advanced-options"></a>
## ⚙️ 高级选项

### 自定义字典

```bash
# 使用自定义字典
arjun -u https://example.com -w /path/to/wordlist.txt

# 使用 SecLists 参数名字典
arjun -u https://example.com -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt
```

### 线程与速率限制

```bash
# 设置线程数（原文默认值：2）
arjun -u https://example.com -t 10

# 限制请求速率（每秒请求数）
arjun -u https://example.com --rate-limit 10

# 请求间隔（秒）
arjun -u https://example.com --delay 1
```

> **性能参数勘误：** [当前命令行源码](https://github.com/s0md3v/Arjun/blob/master/arjun/__main__.py)使用 `-d`、`-T` 设置延迟和超时，线程默认值为 `5`；原文长选项及默认值 `2` 须核对版本。

### 自定义请求头

```bash
# 添加请求头
arjun -u https://example.com --headers "Authorization: Bearer token"

# 多个请求头
arjun -u https://example.com --headers "Cookie: session=abc;X-Custom: value"

# 从文件读取
arjun -u https://example.com -H headers.txt
```

> **请求头说明：** [官方用法](https://github.com/s0md3v/Arjun/wiki/Usage)要求多个请求头以 `\n` 分隔；分号通常用于同一个 Cookie 头内的多个 Cookie，不能代替请求头换行。原文的 `-H headers.txt` 也未见于[当前参数定义](https://github.com/s0md3v/Arjun/blob/master/arjun/__main__.py)。

### 携带已知参数

```bash
# 携带已知参数
arjun -u https://example.com/search --include 'q=test&page=1'

# 用于 POST 请求
arjun -u https://example.com/api -m POST --include 'action=submit'
```

### 被动模式

```bash
# 从 JavaScript 文件中提取（原文说明，参见下方勘误）
arjun -u https://example.com --passive

# 此操作检查 JS 文件中的潜在参数名（原文说明，参见下方勘误）
```

> **被动收集勘误：** [官方指南](https://github.com/s0md3v/Arjun/wiki/Usage)说明，`--passive` 从 CommonCrawl、Open Threat Exchange 和 Wayback Machine 收集候选参数名，并继续在目标上验证；它并非仅检查 JS 文件，也不代表整个过程不接触目标。

### 禁用重定向

```bash
# 不跟随重定向
arjun -u https://example.com --disable-redirects
```

### 超时时间

```bash
# 设置超时时间（秒）
arjun -u https://example.com --timeout 15
```

### 稳定模式

```bash
# 提高稳定性（速度较慢）
arjun -u https://example.com --stable
```

---

<a id="-output-options"></a>
## 📤 输出选项

### 输出格式

```bash
# 文本输出（原文称为默认格式，参见下方说明）
arjun -u https://example.com -oT results.txt

# JSON 格式输出
arjun -u https://example.com -oJ results.json

# 同时输出两种格式
arjun -u https://example.com -oT text.txt -oJ json.json
```

> **输出说明：** [当前源码](https://github.com/s0md3v/Arjun/blob/master/arjun/__main__.py)中，`-o` 与 `-oJ` 均输出 JSON，`-oT` 输出文本；“默认文本输出”的说法不准确。

### JSON 输出示例

```json
{
  "https://example.com/api": {
    "method": "GET",
    "params": [
      {"name": "id", "reason": "body differs"},
      {"name": "debug", "reason": "body differs"}
    ]
  }
}
```

> **JSON 结构说明：** [当前源码](https://github.com/s0md3v/Arjun/blob/master/arjun/__main__.py)中的 `params` 是参数名字符串列表，与上方原文示例不同。后文的 `.[].params[].name` 须按实际结构调整，提取结果也须保存到 `params.txt` 供 ffuf 读取。

### 从 Burp 导入

```bash
# 导入 Burp 请求
arjun --import burp_export.xml

# 处理 Burp 文件
arjun -i burp_requests.txt --burp
```

> **Burp 导入勘误：** [官方指南](https://github.com/s0md3v/Arjun/wiki/Usage)使用 `-i` 自动识别 Burp、文本或原始请求文件，导出 Burp 项目时应取消 Base64 编码。原文的 `--import` 和 `--burp` 未见于[当前参数定义](https://github.com/s0md3v/Arjun/blob/master/arjun/__main__.py)。

---

<a id="-quick-reference"></a>
## 📊 速查表

### 基本命令

| 命令 | 说明 |
|---------|-------------|
| `arjun -u URL` | 发现参数 |
| `arjun -u URL -m POST` | POST 方法 |
| `arjun -u URL -m JSON` | JSON 请求体 |
| `arjun -i file.txt` | 多个 URL |
| `arjun -u URL -oJ out.json` | JSON 格式输出 |

### 请求方法选项

| 参数 | 说明 |
|------|-------------|
| `-m GET` | GET 参数 |
| `-m POST` | POST 表单数据 |
| `-m JSON` | JSON 请求体 |
| `-m GET,POST` | 多个请求方法 |

### 性能选项

| 参数 | 说明 |
|------|-------------|
| `-t 10` | 线程数 |
| `--rate-limit 10` | 每秒请求数 |
| `--delay 1` | 请求间隔 |
| `--timeout 15` | 请求超时时间 |

### 参数发现选项

| 参数 | 说明 |
|------|-------------|
| `-w wordlist.txt` | 自定义字典 |
| `--include 'a=b'` | 携带参数 |
| `--passive` | 从 JS 中提取 |
| `--stable` | 稳定可靠模式 |

---

## 🎯 漏洞赏金测试流程

### 基础发现

```bash
# 快速发现参数
arjun -u https://target.com/api/endpoint -m GET,POST,JSON -oJ params.json
```

### 使用自定义字典

```bash
# 全面发现参数
arjun -u https://target.com/admin \
  -m GET,POST \
  -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
  -t 10 \
  --rate-limit 20 \
  -oJ discovered_params.json
```

### 已登录状态下的测试

```bash
# 携带会话信息
arjun -u https://target.com/dashboard \
  --headers "Cookie: session=abc123;Authorization: Bearer token" \
  -m GET,POST,JSON \
  -oJ auth_params.json
```

### 批量发现

```bash
# 多个端点
cat endpoints.txt | arjun --stdin -m GET,POST -t 5 -oJ all_params.json

# 也可以使用文件
arjun -i endpoints.txt -m GET,POST -t 5 -oJ results.json
```

### 管道集成

```bash
# 先查找端点，再发现参数
gau target.com | grep -E "\.php|\.asp" | httpx -silent | \
  arjun --stdin -m GET,POST -oJ params.json

# Katana + Arjun
katana -u https://target.com -d 3 | \
  grep -E "\.(php|asp|aspx|jsp)$" | \
  arjun --stdin -m GET,POST
```

### 测试已发现的参数

```bash
# 发现参数后，测试是否存在漏洞
# 1. 发现参数
arjun -u https://target.com/search -oJ params.json

# 2. 提取参数名
cat params.json | jq -r '.[].params[].name'

# 3. 使用 ffuf 测试
ffuf -u "https://target.com/search?FUZZ=test" -w params.txt
```

---

## 💡 实用技巧

1. **从被动收集开始** - 先检查 JS 文件
2. **使用优质字典** - 建议使用 Burp 参数名字典
3. **限制请求速率** - 避免被目标封禁
4. **测试各种请求方法** - GET、POST 和 JSON
5. **检查请求头** - X-Custom-Param、X-Debug 等
6. **在已登录状态下测试** - 测试需要登录的区域

### 常见隐藏参数

```
debug, test, admin
id, user_id, uid
token, api_key, key
callback, redirect, url
format, output, type
page, limit, offset
sort, order, filter
```

---

## 📚 参考资源

- [Arjun GitHub](https://github.com/s0md3v/Arjun)
- [Burp 参数名字典](https://github.com/danielmiessler/SecLists/blob/master/Discovery/Web-Content/burp-parameter-names.txt)

### 相关速查表
- [漏洞赏金测试方法](../../Bug-Bounty-Methodology/translations/README.zh-CN.md)
- [ffuf](../../ffuf/translations/README.zh-CN.md)
- [Burp Suite](../../Burp-Suite/translations/README.zh-CN.md)

---

<p align="center">
  <b>🔍 发现隐藏参数！</b><br>
  <i>Arjun - 参数猎手</i>
</p>
