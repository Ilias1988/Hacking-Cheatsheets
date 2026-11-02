# 🚀 Gobuster——完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗  ██████╗ ██████╗ ██╗   ██╗███████╗████████╗███████╗██████╗ 
  ██╔════╝ ██╔═══██╗██╔══██╗██║   ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗
  ██║  ███╗██║   ██║██████╔╝██║   ██║███████╗   ██║   █████╗  ██████╔╝
  ██║   ██║██║   ██║██╔══██╗██║   ██║╚════██║   ██║   ██╔══╝  ██╔══██╗
  ╚██████╔╝╚██████╔╝██████╔╝╚██████╔╝███████║   ██║   ███████╗██║  ██║
   ╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
                目录／DNS／VHost 字典穷举工具
```

<p align="center">
  <img src="https://img.shields.io/badge/Gobuster-Enumeration-blue?style=for-the-badge" alt="Gobuster">
  <img src="https://img.shields.io/badge/Directory-Bruteforce-red?style=for-the-badge" alt="Directory">
  <img src="https://img.shields.io/badge/DNS-Subdomain-green?style=for-the-badge" alt="DNS">
  <img src="https://img.shields.io/badge/VHost-Discovery-purple?style=for-the-badge" alt="VHost">
</p>

<p align="center">
  <b>🔍 使用 Go 编写的高速目录、DNS 与虚拟主机（VHost）枚举工具</b>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [模式概览](#-modes-overview)
- [目录模式（dir）](#-directory-mode-dir)
- [DNS 模式（dns）](#-dns-mode-dns)
- [虚拟主机模式（vhost）](#-vhost-mode-vhost)
- [模糊测试模式（fuzz）](#-fuzz-mode-fuzz)
- [字典](#-wordlists)
- [身份验证与请求头](#-authentication--headers)
- [性能调整](#-performance-tuning)
- [实际应用示例](#-real-world-examples)
- [快速参考](#-quick-reference)
- [技巧与最佳实践](#-tips--best-practices)
- [参考资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

> **版本说明：** 原文混用了不同版本的参数，所有命令均保留作对照。官方 3.7 更新记录指出 `verbose` 已移除并改用 `debug`；当前官方 DNS 示例使用 `-do` 指定域名，参见 [Gobuster 官方说明](https://github.com/OJ/gobuster)。使用时应按安装版本核对模式帮助。

**Gobuster** 是使用 Go 编写的高速多线程字典穷举工具，可用于：
- 枚举 Web 服务器上的目录与文件
- 发现 DNS 子域名
- 发现虚拟主机（VHost）
- URL 模糊测试（fuzzing）

### 为什么使用 Gobuster？

| 特性 | 说明 |
|---------|-------------|
| **快速** | 使用 Go 编写，支持高并发 |
| **灵活** | 针对不同任务提供多种模式 |
| **不递归** | 结果简洁，减少干扰 |
| **稳定** | 能妥善处理错误 |
| **持续开发** | 定期更新 |

### 与其他工具对比

> **译注：** 表中的速度星级是原作者的概括性评价。原文将 ffuf 的递归能力标为“否”，但 [ffuf 官方文档](https://github.com/ffuf/ffuf) 提供 `-recursion` 与 `-recursion-depth`；原表保留，供与说明对照。

| 工具 | 速度 | 递归 | 语言 |
|------|-------|-----------|----------|
| **Gobuster** | ⭐⭐⭐⭐⭐ | 否 | Go |
| Dirb | ⭐⭐ | 是 | C |
| Dirbuster | ⭐⭐⭐ | 是 | Java |
| Ffuf | ⭐⭐⭐⭐⭐ | 否 | Go |
| Feroxbuster | ⭐⭐⭐⭐⭐ | 是 | Rust |

---

<a id="-installation"></a>
## 📥 安装

### Kali Linux（预装）
```bash
# 更新 Gobuster
sudo apt update && sudo apt install gobuster
```

### Ubuntu/Debian
```bash
sudo apt install gobuster
```

### 从源码安装（Go）
```bash
go install github.com/OJ/gobuster/v3@latest
```

### 使用 Snap
```bash
sudo snap install gobuster
```

### 从 GitHub Releases 安装
```bash
# 下载最新发布版本
wget https://github.com/OJ/gobuster/releases/latest/download/gobuster-linux-amd64.7z
7z x gobuster-linux-amd64.7z
chmod +x gobuster
sudo mv gobuster /usr/local/bin/
```

### 验证安装
```bash
gobuster version
gobuster -h
```

---

<a id="-modes-overview"></a>
## 📊 模式概览

### 可用模式

| 模式 | 说明 | 适用场景 |
|------|-------------|----------|
| `dir` | 目录与文件枚举 | 查找隐藏目录与文件 |
| `dns` | DNS 子域名枚举 | 查找子域名 |
| `vhost` | 虚拟主机枚举 | 查找虚拟主机 |
| `fuzz` | URL 模糊测试（fuzzing） | 对参数与路径进行模糊测试 |
| `s3` | S3 存储桶枚举 | 查找 AWS S3 存储桶 |
| `gcs` | Google Cloud Storage | 查找 GCS 存储桶 |
| `tftp` | TFTP 枚举 | 查找 TFTP 文件 |

### 基本语法
```bash
gobuster [mode] [options]

# 示例
gobuster dir -u http://target.com -w wordlist.txt
gobuster dns -d target.com -w subdomains.txt
gobuster vhost -u http://target.com -w vhosts.txt
```

---

<a id="-directory-mode-dir"></a>
## 📁 目录模式（dir）

### 基本语法
```bash
gobuster dir -u <URL> -w <wordlist>
```

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-u` | 目标 URL |
| `-w` | 字典路径 |
| `-t` | 线程数（默认：10） |
| `-x` | 要搜索的文件扩展名 |
| `-s` | 要显示的状态码 |
| `-b` | 排除的状态码（黑名单） |
| `-o` | 输出文件 |
| `-k` | 跳过 TLS／SSL 证书验证 |
| `-r` | 跟随重定向 |
| `-a` | User-Agent 字符串 |
| `-c` | Cookie |
| `-H` | 自定义请求头 |

### 基本目录扫描
```bash
# 简单目录扫描
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt

# 增加线程数
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt -t 50
```

### 按文件扩展名搜索
```bash
# 搜索指定扩展名
gobuster dir -u http://target.com -w wordlist.txt -x php,html,txt

# 多个扩展名
gobuster dir -u http://target.com -w wordlist.txt -x php,asp,aspx,jsp,html,txt,bak,old

# 备份文件
gobuster dir -u http://target.com -w wordlist.txt -x bak,backup,old,orig,~
```

### 状态码过滤

> **原文纠正：** `--no-status` 隐藏输出中的状态码，并不表示显示全部响应或取消 404 过滤。以 [Gobuster 3.6 源码](https://github.com/OJ/gobuster/blob/v3.6.0/cli/cmd/dir.go) 为例，状态码黑名单默认含 404，设置 `-s` 时还需处理默认黑名单的冲突；原始示例未调整。
```bash
# 仅显示指定状态码
gobuster dir -u http://target.com -w wordlist.txt -s 200,204,301,302,307,401

# 排除指定状态码
gobuster dir -u http://target.com -w wordlist.txt -b 404,403

# 显示全部（包括 404；原文说法，参见译注）
gobuster dir -u http://target.com -w wordlist.txt --no-status
```

### 输出选项

> **原文纠正：** `-q` 是安静模式，主要减少横幅与杂项输出；关闭进度显示的是 `--no-progress`，参见 [3.6 全局选项定义](https://github.com/OJ/gobuster/blob/v3.6.0/cli/cmd/root.go)。这与原文“不显示进度”的注释不同。
```bash
# 保存到文件
gobuster dir -u http://target.com -w wordlist.txt -o results.txt

# 详细输出
gobuster dir -u http://target.com -w wordlist.txt -v

# 不显示进度（原文说法，参见译注）
gobuster dir -u http://target.com -w wordlist.txt -q

# 展开模式（完整 URL）
gobuster dir -u http://target.com -w wordlist.txt -e
```

### SSL／TLS 选项
```bash
# 跳过 TLS／SSL 证书验证
gobuster dir -u https://target.com -w wordlist.txt -k

# 使用扩展选项跳过 TLS／SSL 验证
gobuster dir -u https://target.com -w wordlist.txt -k --no-tls-validation
```

### 目录模式示例
```bash
# 常规 Web 目录扫描
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt -t 50

# PHP 应用
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x php -t 50

# 发现 API 端点
gobuster dir -u http://target.com/api -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt -t 30

# 查找备份文件
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x bak,backup,old,zip,tar.gz -t 50
```

---

<a id="-dns-mode-dns"></a>
## 🌐 DNS 模式（dns）

### 基本语法
```bash
gobuster dns -d <domain> -w <wordlist>
```

### DNS 选项

| 选项 | 说明 |
|--------|-------------|
| `-d` | 目标域名 |
| `-w` | 字典路径 |
| `-t` | 线程数 |
| `-r` | 自定义 DNS 解析服务器 |
| `-i` | 显示 IP 地址 |
| `-c` | 显示 CNAME 记录 |
| `--wildcard` | 强制继续处理通配符 DNS |
| `--timeout` | DNS 超时 |

### 基本 DNS 枚举
```bash
# 简单子域名扫描
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# 显示 IP 地址
gobuster dns -d target.com -w subdomains.txt -i

# 显示 CNAME
gobuster dns -d target.com -w subdomains.txt -c
```

### 自定义 DNS 解析服务器

> **译注：** `8.8.8.8:53` 只指定一个解析服务器及其端口，原文“多个解析服务器”的注释不准确。
```bash
# 使用指定 DNS 服务器
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8

# 多个解析服务器（原文说法；此处实际仅指定一个服务器的端口）
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8:53
```

### DNS 示例
```bash
# 全面子域名扫描
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt -t 50 -i

# 快速子域名扫描
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt -t 100

# 写入输出文件
gobuster dns -d target.com -w subdomains.txt -i -o subdomains_found.txt
```

---

<a id="-vhost-mode-vhost"></a>
## 🏠 虚拟主机模式（vhost）

### 基本语法
```bash
gobuster vhost -u <URL> -w <wordlist>
```

### 虚拟主机选项

| 选项 | 说明 |
|--------|-------------|
| `-u` | 目标 URL |
| `-w` | 字典路径 |
| `-t` | 线程数 |
| `--append-domain` | 在字典条目后追加域名 |
| `--domain` | 虚拟主机使用的域名 |
| `-r` | 跟随重定向 |

### 基本虚拟主机枚举

> **用法说明：** 如果字典条目只是子域名前缀，应核对 `--append-domain`；`--domain` 与字典内容需要配合使用。官方虚拟主机示例包含追加域名选项，参见 [Gobuster 用法](https://github.com/OJ/gobuster#-virtual-host-mode-vhost)。VHost 指通过 HTTP Host 名称区分的虚拟主机。
```bash
# 简单虚拟主机扫描
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# 追加域名
gobuster vhost -u http://target.com -w subdomains.txt --append-domain
```

### 虚拟主机示例
```bash
# 常规虚拟主机发现
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/namelist.txt -t 50

# 开发环境主机
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt --append-domain

# 使用自定义域名
gobuster vhost -u http://10.10.10.10 -w subdomains.txt --domain target.htb
```

---

<a id="-fuzz-mode-fuzz"></a>
## 🔧 模糊测试模式（fuzz）

### 基本语法
```bash
gobuster fuzz -u <URL with FUZZ> -w <wordlist>
```

### 模糊测试选项

| 选项 | 说明 |
|--------|-------------|
| `-u` | 包含 FUZZ 关键字的 URL |
| `-w` | 字典路径 |
| `-t` | 线程数 |
| `-b` | 排除的状态码 |
| `--exclude-length` | 按响应长度排除 |

### 模糊测试示例
```bash
# 对参数值进行模糊测试
gobuster fuzz -u "http://target.com/page?id=FUZZ" -w numbers.txt

# 对路径进行模糊测试
gobuster fuzz -u "http://target.com/FUZZ/admin" -w wordlist.txt

# 排除指定长度
gobuster fuzz -u "http://target.com/FUZZ" -w wordlist.txt --exclude-length 0,42
```

---

<a id="-wordlists"></a>
## 📚 字典

### SecLists 路径（Kali Linux）

```bash
# 目录与文件枚举
/usr/share/seclists/Discovery/Web-Content/

# 常用目录字典
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-small.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# DNS／子域名
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

### 默认字典

```bash
# Dirb 字典
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
/usr/share/wordlists/dirb/small.txt

# Dirbuster 字典
/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
/usr/share/wordlists/dirbuster/directory-list-2.3-small.txt
```

### 字典建议

| 适用场景 | 字典 |
|----------|----------|
| 快速扫描 | `common.txt` |
| 常规扫描 | `directory-list-2.3-medium.txt` |
| 全面扫描 | `directory-list-2.3-big.txt` |
| API | `api-endpoints.txt` |
| 子域名 | `subdomains-top1million-5000.txt` |
| 文件 | `raft-large-files.txt` |

---

<a id="-authentication--headers"></a>
## 🔐 身份验证与请求头

### HTTP Basic 身份验证
```bash
# HTTP Basic 身份验证
gobuster dir -u http://target.com -w wordlist.txt -U username -P password
```

### Cookie 身份验证
```bash
# 单个 Cookie
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123"

# 多个 Cookie
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123; token=xyz789"

# PHP 会话
gobuster dir -u http://target.com -w wordlist.txt -c "PHPSESSID=abc123def456"
```

### 自定义请求头
```bash
# 单个请求头
gobuster dir -u http://target.com -w wordlist.txt -H "Authorization: Bearer token123"

# 多个请求头
gobuster dir -u http://target.com -w wordlist.txt -H "X-Forwarded-For: 127.0.0.1" -H "X-Custom: value"

# API 密钥
gobuster dir -u http://target.com/api -w wordlist.txt -H "X-API-Key: your-api-key"
```

### User-Agent
```bash
# 自定义 User-Agent
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Googlebot
gobuster dir -u http://target.com -w wordlist.txt -a "Googlebot/2.1"
```

### 代理支持
```bash
# HTTP 代理
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080

# SOCKS5 代理
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050

# Burp Suite 代理
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080 -k
```

---

<a id="-performance-tuning"></a>
## ⚡ 性能调整

### 线程管理
```bash
# 默认：10 个线程
gobuster dir -u http://target.com -w wordlist.txt

# 增加线程数（网络较快）
gobuster dir -u http://target.com -w wordlist.txt -t 100

# 减少线程数（网络较慢或不稳定）
gobuster dir -u http://target.com -w wordlist.txt -t 5
```

### 超时设置
```bash
# 增加超时时间
gobuster dir -u http://target.com -w wordlist.txt --timeout 30s

# DNS 超时
gobuster dns -d target.com -w subdomains.txt --timeout 5s
```

### 请求间延迟

> **速率说明：** `--delay` 是每个工作线程两次请求之间的等待时间，不能直接当作全局请求间隔，参见 [官方参数定义](https://github.com/OJ/gobuster/blob/v3.6.0/cli/cmd/root.go)。降低速率或更换 User-Agent 也不保证避开检测。
```bash
# 添加延迟（限制请求速率）
gobuster dir -u http://target.com -w wordlist.txt --delay 100ms

# 降低速率以尝试减少 WAF 检测
gobuster dir -u http://target.com -w wordlist.txt --delay 500ms
```

---

<a id="-real-world-examples"></a>
## 🎬 实际应用示例

### 示例 1：常规 Web 枚举
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt \
    -t 50 \
    -x php,html,txt \
    -o results.txt
```

### 示例 2：发现 API 端点
```bash
gobuster dir -u http://target.com/api/v1 \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -t 30 \
    -H "Content-Type: application/json"
```

### 示例 3：经过身份验证的扫描
```bash
gobuster dir -u http://target.com/admin \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -c "PHPSESSID=abc123" \
    -t 20
```

### 示例 4：子域名枚举
```bash
gobuster dns -d target.com \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt \
    -t 100 \
    -i \
    -o subdomains.txt
```

### 示例 5：发现虚拟主机（HackTheBox）
```bash
gobuster vhost -u http://10.10.10.10 \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    --domain target.htb \
    --append-domain
```

### 示例 6：通过 Burp 代理
```bash
gobuster dir -u https://target.com \
    -w /usr/share/wordlists/dirb/common.txt \
    --proxy http://127.0.0.1:8080 \
    -k \
    -t 20
```

### 示例 7：搜索备份文件
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -x bak,backup,old,orig,save,swp,txt,zip,tar,gz,sql \
    -t 50
```

### 示例 8：检测内容管理系统（CMS）
```bash
# WordPress
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/wordpress.fuzz.txt \
    -t 50

# Joomla
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/joomla-plugins.fuzz.txt
```

---

<a id="-quick-reference"></a>
## 📊 快速参考

### 常用命令

| 任务 | 命令 |
|------|---------|
| 基本目录扫描 | `gobuster dir -u URL -w wordlist.txt` |
| 指定扩展名 | `gobuster dir -u URL -w wordlist.txt -x php,html` |
| 子域名扫描 | `gobuster dns -d domain.com -w wordlist.txt` |
| 虚拟主机扫描 | `gobuster vhost -u URL -w wordlist.txt` |
| 写入输出文件 | `gobuster dir -u URL -w wordlist.txt -o output.txt` |
| 跳过 TLS／SSL 证书错误 | `gobuster dir -u URL -w wordlist.txt -k` |
| 携带 Cookie | `gobuster dir -u URL -w wordlist.txt -c "session=x"` |
| 通过代理 | `gobuster dir -u URL -w wordlist.txt --proxy http://127.0.0.1:8080` |

### 常用选项

| 选项 | 短选项 | 说明 |
|--------|-------|-------------|
| `--url` | `-u` | 目标 URL |
| `--wordlist` | `-w` | 字典文件 |
| `--threads` | `-t` | 线程数 |
| `--extensions` | `-x` | 文件扩展名 |
| `--status-codes` | `-s` | 要显示的状态码 |
| `--cookies` | `-c` | 要发送的 Cookie |
| `--output` | `-o` | 输出文件 |
| `--insecuressl` | `-k` | 跳过 TLS／SSL 证书验证 |
| `--verbose` | `-v` | 详细输出 |
| `--quiet` | `-q` | 安静模式 |

---

<a id="-tips--best-practices"></a>
## 💡 技巧与最佳实践

### 效率技巧

1. **先用小字典，再扩大范围**
   ```bash
   # 先进行快速扫描
   gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt
   
   # 再使用更大的字典
   gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
   ```

2. **根据目标选择扩展名**
   ```bash
   # PHP 网站
   gobuster dir -u http://target.com -w wordlist.txt -x php
   
   # ASP.NET 网站
   gobuster dir -u http://target.com -w wordlist.txt -x asp,aspx
   ```

3. **结合其他工具**
   ```bash
   # Nmap → Gobuster 工作流程
   nmap -sV -p 80,443 target.com
   gobuster dir -u http://target.com -w wordlist.txt
   ```

### 减少被检测的可能

```bash
# 带延迟的慢速扫描
gobuster dir -u http://target.com -w wordlist.txt -t 5 --delay 200ms

# 自定义 User-Agent
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0..."

# 通过 Tor
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050
```

---

## ⚠️ 法律免责声明

> **警告：** Gobuster 是功能强大的枚举工具，只能用于**已获授权的测试**。
> 
> - ✅ 在自己拥有的系统上测试
> - ✅ 取得明确的书面许可后测试
> - ✅ 用于 CTF 题目与实验环境
> - ❌ 切勿进行未授权扫描
> - ❌ 未经授权的枚举可能违法
> 
> **扫描任何系统前，都应先取得许可。**

---

<a id="-resources"></a>
## 📚 参考资源

### 官方资源
- [Gobuster GitHub](https://github.com/OJ/gobuster)
- [Gobuster Wiki](https://github.com/OJ/gobuster/wiki)

### 字典
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)

### 相关速查表
- [Nmap](../../Nmap/README.md)
- [Burp Suite](../../Burp-Suite/README.md)
- [SQLMap](../../SQLMap/README.md)

---

<p align="center">
  <b>🚀 掌握 Web 枚举！</b><br>
  <i>发现他人未注意到的信息</i>
</p>
