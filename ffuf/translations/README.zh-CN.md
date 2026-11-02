# 🚀 ffuf——完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   __  __        __ 
  / _|/ _|_   _ / _|
 | |_| |_| | | | |_ 
 |  _|  _| |_| |  _|
 |_| |_|  \__,_|_|  
                    
   Web 模糊测试工具
```

<p align="center">
  <img src="https://img.shields.io/badge/ffuf-Web_Fuzzer-red?style=for-the-badge" alt="ffuf">
  <img src="https://img.shields.io/badge/Fast-Fuzzing-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Go-Language-green?style=for-the-badge" alt="Go">
</p>

<p align="center">
  <b>⚡ 使用 Go 编写的高速 Web 模糊测试工具</b>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [基本语法](#-basic-syntax)
- [目录模糊测试](#-directory-fuzzing)
- [子域名模糊测试](#-subdomain-fuzzing)
- [参数模糊测试](#-parameter-fuzzing)
- [POST 数据模糊测试](#-post-data-fuzzing)
- [过滤与匹配](#-filtering--matching)
- [高级功能](#-advanced-features)
- [实际应用示例](#-real-world-examples)
- [快速参考](#-quick-reference)
- [技巧与最佳实践](#-tips--best-practices)
- [参考资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**ffuf**（Fuzz Faster U Fool）是使用 Go 编写的高速 Web 模糊测试工具，常用于漏洞赏金测试中的目录穷举、虚拟主机发现与参数模糊测试。

### 主要功能

| 功能 | 说明 |
|---------|-------------|
| **速度** | 速度很快（基于 Go） |
| **灵活** | 对 HTTP 请求的各部分进行模糊测试 |
| **过滤** | 多种过滤与匹配选项 |
| **递归** | 支持递归扫描 |
| **输出** | 多种输出格式 |
| **易用** | 语法简洁直观 |

### ffuf 与其他工具对比

> **原表说明：** 星级属于概括性评价。Gobuster 的 POST 功能被原文标为“否”，但其 [官方 fuzz 示例](https://github.com/OJ/gobuster#-fuzz-mode-fuzz) 包含 POST 数据测试；递归能力也不能仅凭此表推断，需按具体工具版本核对。原表保留。

| 功能 | ffuf | Gobuster | dirb |
|---------|------|----------|------|
| **速度** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |
| **灵活性** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| **POST 模糊测试** | ✅ 是 | ❌ 否 | ❌ 否 |
| **递归** | ✅ 是 | ✅ 是 | ✅ 是 |
| **过滤器** | 多 | 有限 | 有限 |

---

<a id="-installation"></a>
## 📥 安装

### 使用 Go
```bash
go install github.com/ffuf/ffuf/v2@latest
```

### Kali Linux
```bash
sudo apt install ffuf
```

### 下载二进制文件

> **占位说明：** `v2.x.x` 和文件名中的 `2.x.x` 是版本占位符，原始下载命令不能按字面视为有效发布地址；实际文件应以 [官方 Releases](https://github.com/ffuf/ffuf/releases) 为准。
```bash
# 从 Releases 下载
https://github.com/ffuf/ffuf/releases

# Linux
wget https://github.com/ffuf/ffuf/releases/download/v2.x.x/ffuf_2.x.x_linux_amd64.tar.gz
tar xzf ffuf_2.x.x_linux_amd64.tar.gz
sudo mv ffuf /usr/local/bin/
```

### 验证安装
```bash
ffuf -V
ffuf -h
```

---

<a id="-basic-syntax"></a>
## ⌨️ 基本语法

### 通用语法
```bash
ffuf [options] -u <URL/FUZZ> -w <wordlist>
```

### FUZZ 关键字

**FUZZ** 关键字用于指定将字典词条插入请求的位置。

```bash
# 在 URL 路径中
ffuf -u https://target.com/FUZZ -w wordlist.txt

# 在子域名中
ffuf -u https://FUZZ.target.com -w wordlist.txt

# 在参数值中
ffuf -u https://target.com/page?id=FUZZ -w wordlist.txt

# 在 POST 数据中
ffuf -u https://target.com/login -X POST -d "user=FUZZ&pass=test" -w wordlist.txt
```

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-u` | 包含 FUZZ 关键字的目标 URL |
| `-w` | 字典路径 |
| `-o` | 输出文件 |
| `-of` | 输出格式（json、csv、html） |
| `-t` | 线程数（默认：40） |
| `-p` | 请求间延迟 |
| `-r` | 跟随重定向 |
| `-v` | 详细输出 |
| `-s` | 静默模式 |

---

<a id="-directory-fuzzing"></a>
## 📁 目录模糊测试

### 基本目录穷举

```bash
# 基本目录模糊测试
ffuf -u https://target.com/FUZZ -w /usr/share/wordlists/dirb/common.txt

# 指定文件扩展名
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.html,.txt,.bak

# 多个扩展名
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.asp,.aspx,.jsp,.html
```

### 递归扫描

```bash
# 启用递归
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion

# 递归深度
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2

# 指定递归策略
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-strategy greedy
```

### 按状态码过滤

```bash
# 仅匹配 200 响应
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200

# 匹配多个状态码
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200,301,302,403

# 排除 404（原文称改变默认行为，参见译注）
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404

# 匹配全部，再排除 404
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### 常用字典

```bash
# SecLists
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
```

---

<a id="-subdomain-fuzzing"></a>
## 🌐 子域名模糊测试

### 虚拟主机发现

```bash
# 基本子域名模糊测试（此处修改 Host 请求头）
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt

# 按响应大小排除（去除默认页面）
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt -fs 1234

# 使用指定字典
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

### DNS 子域名模糊测试

> **参数纠正：** `-r` 表示跟随重定向，不是选择 DNS 解析服务器，参见 [官方帮助](https://github.com/ffuf/ffuf#usage)。修改 HTTP Host 头是在探测虚拟主机；把 FUZZ 放进 URL 主机名则依赖 DNS 解析。

```bash
# 将子域名放在 URL 中
ffuf -u https://FUZZ.target.com -w subdomains.txt

# 指定解析服务器（原文说法；-r 实际表示跟随重定向）
ffuf -u https://FUZZ.target.com -w subdomains.txt -r
```

### 常用子域名字典

```bash
# SecLists DNS 字典
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

---

<a id="-parameter-fuzzing"></a>
## 🔍 参数模糊测试

### GET 参数模糊测试

```bash
# 对参数名进行模糊测试
ffuf -u "https://target.com/page?FUZZ=test" -w params.txt

# 对参数值进行模糊测试
ffuf -u "https://target.com/page?id=FUZZ" -w values.txt

# 多个参数
ffuf -u "https://target.com/page?W1=test&W2=test" -w params.txt:W1 -w values.txt:W2
```

### 参数发现

```bash
# 寻找隐藏参数
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

---

<a id="-post-data-fuzzing"></a>
## 📝 POST 数据模糊测试

### 基本 POST 模糊测试

```bash
# 使用表单数据的 POST
ffuf -u https://target.com/login -X POST \
    -d "username=FUZZ&password=test" \
    -w usernames.txt

# 指定 Content-Type 的 POST
ffuf -u https://target.com/login -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=FUZZ&password=test" \
    -w usernames.txt
```

### JSON POST 模糊测试

```bash
# JSON POST 数据
ffuf -u https://target.com/api/login -X POST \
    -H "Content-Type: application/json" \
    -d '{"username":"FUZZ","password":"test"}' \
    -w usernames.txt
```

### 多字典测试（凭据填充）

> **模式说明：** 多字典默认使用 `clusterbomb`，会尝试组合，并不自动将用户名与密码按行配对，参见 [官方模式选项](https://github.com/ffuf/ffuf#usage)。

```bash
# 对用户名与密码进行模糊测试
ffuf -u https://target.com/login -X POST \
    -d "username=USER&password=PASS" \
    -w users.txt:USER \
    -w passwords.txt:PASS \
    -fc 401

# 模式：clusterbomb（全部组合）
ffuf -u https://target.com/login -X POST \
    -d "user=USER&pass=PASS" \
    -w users.txt:USER \
    -w passes.txt:PASS \
    -mode clusterbomb
```

---

<a id="-filtering--matching"></a>
## 🎯 过滤与匹配

> **过滤说明：** 默认状态码匹配集合不包含全部响应；`-fc 404` 不会自动等于匹配所有非 404。`-ft`／`-mt` 比较的是首字节时间（毫秒），需带大于／小于条件；原文 `-ft 5000`、`-mt 5000` 缺少比较符。参见 [官方参数定义](https://github.com/ffuf/ffuf#usage)。

### 过滤选项

| 选项 | 说明 | 示例 |
|--------|-------------|---------|
| `-fc` | 按状态码排除 | `-fc 404,403` |
| `-fs` | 按响应大小排除 | `-fs 1234` |
| `-fw` | 按词数排除 | `-fw 100` |
| `-fl` | 按行数排除 | `-fl 50` |
| `-ft` | 按时间排除（毫秒） | `-ft 5000` |
| `-fr` | 按正则表达式排除 | `-fr "error"` |

### 匹配选项

| 选项 | 说明 | 示例 |
|--------|-------------|---------|
| `-mc` | 匹配状态码 | `-mc 200,301` |
| `-ms` | 匹配响应大小 | `-ms 1234` |
| `-mw` | 匹配词数 | `-mw 100` |
| `-ml` | 匹配行数 | `-ml 50` |
| `-mt` | 匹配时间（毫秒） | `-mt 5000` |
| `-mr` | 按正则表达式匹配 | `-mr "success"` |

### 过滤示例

```bash
# 排除 404 与 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404,403

# 按大小排除（去除默认页面）
ffuf -u https://target.com/FUZZ -w wordlist.txt -fs 0,1234

# 按词数排除
ffuf -u https://target.com/FUZZ -w wordlist.txt -fw 10

# 匹配全部，再排除指定响应
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### 自动校准

> **参数纠正：** `-acc` 提供自定义校准字符串；校准关键字由 `-ack` 指定，参见 [官方帮助](https://github.com/ffuf/ffuf#usage)。

```bash
# 自动校准过滤器
ffuf -u https://target.com/FUZZ -w wordlist.txt -ac

# 使用指定关键字校准（原文说法；-acc 指定校准字符串）
ffuf -u https://target.com/FUZZ -w wordlist.txt -acc "randomstring123"
```

---

<a id="-advanced-features"></a>
## ⚡ 高级功能

### 速率限制

```bash
# 每秒请求数
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 100

# 请求间延迟
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.5

# 随机延迟（jitter）
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.1-1.0
```

### 线程

```bash
# 设置线程数（默认：40）
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 100

# 减少线程数以降低请求强度
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 10 -p 1
```

### 请求头

```bash
# 自定义请求头
ffuf -u https://target.com/FUZZ -w wordlist.txt -H "X-Custom: value"

# 多个请求头
ffuf -u https://target.com/FUZZ -w wordlist.txt \
    -H "Authorization: Bearer token" \
    -H "X-Forwarded-For: 127.0.0.1"

# Cookie
ffuf -u https://target.com/FUZZ -w wordlist.txt -b "session=abc123"
```

### 代理

```bash
# HTTP 代理
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080

# Burp Suite
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080
```

### 输出

```bash
# JSON 输出
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.json -of json

# CSV 输出
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.csv -of csv

# HTML 输出
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.html -of html

# 全部格式
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results -of all
```

### 超时与重试

> **停止条件纠正：** `-se` 遇到异常错误时停止，不表示遇到任意 403 就停止；`-sf` 才是在超过 95% 响应为 403 时停止。参见 [官方帮助](https://github.com/ffuf/ffuf#usage)。本节原始命令全部保留。

```bash
# 请求超时
ffuf -u https://target.com/FUZZ -w wordlist.txt -timeout 10

# 遇到 403 时停止（原文说法，参见译注）
ffuf -u https://target.com/FUZZ -w wordlist.txt -se

# 忽略 TLS／SSL 证书错误
ffuf -u https://target.com/FUZZ -w wordlist.txt -k
```

---

<a id="-real-world-examples"></a>
## 🎬 实际应用示例

### 示例 1：基本目录扫描
```bash
ffuf -u https://target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/common.txt -mc 200,301,302
```

### 示例 2：查找 PHP 文件
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.php.bak,.php~ -mc 200
```

### 示例 3：子域名枚举
```bash
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    -fs 1234 -t 100
```

### 示例 4：发现 API 端点
```bash
ffuf -u https://api.target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -mc 200,401,403
```

### 示例 5：参数发现
```bash
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

### 示例 6：登录穷举
```bash
ffuf -u https://target.com/login -X POST \
    -d "user=admin&pass=FUZZ" \
    -w /usr/share/seclists/Passwords/Common-Credentials/10k-most-common.txt \
    -fc 401 -t 10
```

### 示例 7：使用代理（Burp）
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080 -k
```

### 示例 8：递归扫描
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2 -mc 200,301
```

### 示例 9：限制速率的扫描
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 10 -t 5
```

### 示例 10：综合漏洞赏金扫描
```bash
ffuf -u https://target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt \
    -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak \
    -mc 200,301,302,403 \
    -recursion -recursion-depth 2 \
    -o results.json -of json \
    -ac
```

---

<a id="-quick-reference"></a>
## 📊 快速参考

### 常用命令

| 任务 | 命令 |
|------|---------|
| 目录扫描 | `ffuf -u URL/FUZZ -w wordlist.txt` |
| 指定扩展名 | `ffuf -u URL/FUZZ -w wordlist.txt -e .php,.html` |
| 子域名扫描 | `ffuf -u URL -H "Host: FUZZ.domain" -w subs.txt` |
| POST 模糊测试 | `ffuf -u URL -X POST -d "param=FUZZ" -w wordlist.txt` |
| 排除 404 | `ffuf -u URL/FUZZ -w wordlist.txt -fc 404` |
| 按大小排除 | `ffuf -u URL/FUZZ -w wordlist.txt -fs 1234` |
| 自动校准 | `ffuf -u URL/FUZZ -w wordlist.txt -ac` |
| 保存输出 | `ffuf -u URL/FUZZ -w wordlist.txt -o out.json -of json` |

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-u` | 包含 FUZZ 的 URL |
| `-w` | 字典 |
| `-e` | 扩展名 |
| `-t` | 线程 |
| `-mc` | 匹配状态码 |
| `-fc` | 排除状态码 |
| `-fs` | 排除指定大小 |
| `-ac` | 自动校准 |
| `-r` | 跟随重定向 |
| `-o` | 输出文件 |

---

<a id="-tips--best-practices"></a>
## 💡 技巧与最佳实践

### 漏洞赏金测试技巧

> **示例说明：** `target.com/FUZZ` 和 `URL/FUZZ` 属于占位写法，需要完整目标 URL。限速可降低请求强度，但不保证绕过 WAF；自动校准也可能过滤需要人工复核的响应。

1. **先进行自动校准**
   ```bash
   ffuf -u target.com/FUZZ -w wordlist.txt -ac
   ```

2. **使用多个扩展名**
   ```bash
   -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak,.old,.zip
   ```

3. **尝试常见备份扩展名**
   ```bash
   -e .bak,.old,.orig,.backup,~,.swp
   ```

4. **通过限速尝试减少 WAF 拦截**
   ```bash
   -rate 10 -t 5 -p 0.5
   ```

### 性能技巧

```bash
# 高速（无 WAF）
ffuf -u URL/FUZZ -w wordlist.txt -t 200

# 低速、低强度（原文称隐蔽模式）
ffuf -u URL/FUZZ -w wordlist.txt -t 10 -rate 5 -p 1
```

---

## ⚠️ 法律免责声明

> **警告：** ffuf 是功能强大的模糊测试工具，只能用于**已获授权的测试**。
> 
> - ✅ 仅测试已获许可的项目
> - ✅ 用于已获授权的漏洞赏金项目
> - ✅ 遵守速率限制与测试范围
> - ❌ 不得进行未授权模糊测试
> - ❌ 不要造成拒绝服务
> 
> **未经授权的扫描属于违法行为。**

---

<a id="-resources"></a>
## 📚 参考资源

### 官方资源
- [ffuf GitHub](https://github.com/ffuf/ffuf)
- [ffuf Wiki](https://github.com/ffuf/ffuf/wiki)

### 字典
- [SecLists](https://github.com/danielmiessler/SecLists)
- [Assetnote 字典](https://wordlists.assetnote.io/)

### 相关速查表
- [Nuclei](../../Nuclei/translations/README.zh-CN.md)
- [Gobuster](../../Gobuster/translations/README.zh-CN.md)
- [Nmap](../../Nmap/translations/README.zh-CN.md)

---

<p align="center">
  <b>🚀 更快地模糊测试，发现更多问题！</b><br>
  <i>漏洞赏金测试者常用的模糊测试工具</i>
</p>
