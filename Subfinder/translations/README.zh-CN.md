# 🔍 Subfinder 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **版本与数据源说明：** 本文保留原文参数、数据源名称、YAML 配置和 API 占位符。`v2.x.x` 是版本占位写法，不能直接视为存在的下载版本；数据源数量、默认名单、认证要求及免费额度会变化，应以对应版本和服务商为准。配置示例中的提供商标识保持不变，参见 [官方安装与配置说明](https://docs.projectdiscovery.io/opensource/subfinder/install)。
>
> **参数勘误：** 当前官方文档中 `-oJ` 输出 JSONL，`-oD` 指输出目录（用于 `-dL`），并非 CSV；`-cs` 仅在 JSON 输出中收集来源。`-oJL` 与 `-exclude-subdomains` 未列在当前参数表中，原文示例需核对版本。`-t` 控制主动解析的并发数，并不是通用的被动数据源线程数。`-recursive` 选择支持递归子域查询的数据源，不等同于自动逐层遍历全部结果；参见 [官方参数说明](https://docs.projectdiscovery.io/opensource/subfinder/usage)。
>
> **流程解读：** `-nW`/`-active` 会解析并筛选活跃子域名，因此不能把所有运行方式都理解为完全被动。直接对未排序的两次输出执行 `diff` 可能把顺序变化误当作新增；向后续工具传递数据时还应核对输出是否为纯域名、IP 或带标题/状态码的文本。本文保留原始管道，使用时应按各工具的输入约定处理结果。

```
  ____        _     __ _           _           
 / ___| _   _| |__ / _(_)_ __   __| | ___ _ __ 
 \___ \| | | | '_ \ |_| | '_ \ / _` |/ _ \ '__|
  ___) | |_| | |_) |  _| | | | | (_| |  __/ |   
 |____/ \__,_|_.__/|_| |_|_| |_|\__,_|\___|_|   
                                                
   快速子域名发现工具
```

<p align="center">
  <img src="https://img.shields.io/badge/Subfinder-Subdomain_Discovery-red?style=for-the-badge" alt="Subfinder">
  <img src="https://img.shields.io/badge/Passive-Recon-orange?style=for-the-badge" alt="Passive">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🌐 ProjectDiscovery 开发的快速被动子域名枚举工具</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [基本语法](#-basic-syntax)
- [子域名发现](#-subdomain-discovery)
- [数据源](#-data-sources)
- [API 配置](#-api-configuration)
- [输出选项](#-output-options)
- [高级功能](#-advanced-features)
- [漏洞赏金工作流程](#-bug-bounty-workflow)
- [实践示例](#-real-world-examples)
- [速查](#-quick-reference)
- [技巧与最佳实践](#-tips--best-practices)
- [参考资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**Subfinder** 是一款快速的被动子域名枚举工具，可通过搜索引擎、文本粘贴站点和情报平台等多个数据源发现子域名。

### 主要功能

| 功能 | 说明 |
|---------|-------------|
| **快速** | 基于 Go，支持高并发 |
| **被动发现** | 不直接与目标交互 |
| **多个数据源** | 40+ 个数据源 |
| **API 集成** | 支持需授权的数据源 |
| **便于管道组合** | 可与其他工具配合 |
| **JSON 输出** | 支持结构化输出 |

### Subfinder 与其他工具对比

| 功能 | Subfinder | Amass | Assetfinder |
|---------|-----------|-------|-------------|
| **速度** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| **数据源** | 40+ | 50+ | 10+ |
| **主动扫描** | ❌ 否 | ✅ 是 | ❌ 否 |
| **API 支持** | ✅ 是 | ✅ 是 | ❌ 否 |
| **易用性** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |

---

<a id="-installation"></a>
## 📥 安装

### 使用 Go
```bash
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
```

### Kali Linux
```bash
sudo apt install subfinder
```

### 下载二进制文件
```bash
# 从发布页面下载
https://github.com/projectdiscovery/subfinder/releases

# Linux
wget https://github.com/projectdiscovery/subfinder/releases/download/v2.x.x/subfinder_2.x.x_linux_amd64.zip
unzip subfinder_2.x.x_linux_amd64.zip
sudo mv subfinder /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/subfinder:latest
docker run projectdiscovery/subfinder -d example.com
```

### 验证安装
```bash
subfinder -version
subfinder -h
```

---

<a id="-basic-syntax"></a>
## ⌨️ 基本语法

### 通用语法
```bash
subfinder [options] -d <domain>
```

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-d` | 目标域名 |
| `-dL` | 包含域名列表的文件 |
| `-o` | 输出文件 |
| `-oJ` | JSON 输出 |
| `-silent` | 静默模式（仅输出子域名） |
| `-v` | 详细输出 |
| `-t` | 线程数量（适用范围见译注） |
| `-all` | 使用所有数据源 |

### 快速开始
```bash
# 基础枚举
subfinder -d example.com

# 静默输出（纯列表）
subfinder -d example.com -silent

# 保存到文件
subfinder -d example.com -o subdomains.txt

# 多个域名
subfinder -dL domains.txt -o all-subs.txt
```

---

<a id="-subdomain-discovery"></a>
## 🔍 子域名发现

### 单个域名

```bash
# 基础发现
subfinder -d target.com

# 详细输出
subfinder -d target.com -v

# 静默模式（仅子域名）
subfinder -d target.com -silent

# 显示来源
subfinder -d target.com -cs
```

### 多个域名

```bash
# 从文件读取
subfinder -dL domains.txt

# 输出到文件
subfinder -dL domains.txt -o all_subdomains.txt

# 使用静默输出接入管道
subfinder -dL domains.txt -silent | httpx
```

### 递归枚举

```bash
# 启用递归（原文称发现更深层子域名，具体含义见译注）
subfinder -d target.com -recursive

# 递归与所有数据源组合
subfinder -d target.com -recursive -all
```

---

<a id="-data-sources"></a>
## 📊 数据源

### 免费数据源（默认）

原文列出的默认数据源如下（无需 API 密钥；当前可用性见译注）：
- Alienvault
- AnubisDB
- Crtsh
- Hackertarget
- Rapiddns
- Sublist3r
- ThreatCrowd
- URLScan
- Wayback Machine

### 需 API 的数据源

以下数据源需要配置 API 密钥，以获得更多结果：
- **Shodan**
- **SecurityTrails**
- **VirusTotal**
- **Censys**
- **Chaos**
- **GitHub**
- **PassiveTotal**
- **Binaryedge**
- **WhoisXML**
- **ZoomEye**

### 数据源管理

```bash
# 列出所有数据源
subfinder -ls

# 仅使用指定数据源
subfinder -d target.com -sources crtsh,hackertarget,alienvault

# 排除指定数据源
subfinder -d target.com -es shodan,securitytrails

# 使用所有数据源（包括较慢的数据源）
subfinder -d target.com -all

# 显示每个子域名的发现来源
subfinder -d target.com -cs
```

---

<a id="-api-configuration"></a>
## 🔑 API 配置

### 配置文件位置

```bash
# 默认位置
~/.config/subfinder/provider-config.yaml

# 或指定自定义配置
subfinder -d target.com -pc /path/to/config.yaml
```

### 配置文件模板

```yaml
# ~/.config/subfinder/provider-config.yaml

binaryedge:
  - your_api_key_here
censys:
  - your_api_id:your_api_secret
certspotter: []
chaos:
  - your_chaos_key
chinaz: []
dnsdb: []
fofa:
  - your_email:your_key
fullhunt: []
github:
  - your_github_token
hunter:
  - your_hunter_key
intelx:
  - your_intelx_key
passivetotal:
  - your_username:your_key
securitytrails:
  - your_security_trails_key
shodan:
  - your_shodan_key
virustotal:
  - your_virustotal_key
whoisxmlapi:
  - your_whoisxml_key
zoomeye:
  - your_zoomeye_key
```

### 获取 API 密钥

| 数据源 | 免费额度 | URL |
|--------|-----------|-----|
| **VirusTotal** | ✅ 是 | https://www.virustotal.com |
| **SecurityTrails** | ✅ 是（有限额） | https://securitytrails.com |
| **Shodan** | ✅ 是（有限额） | https://shodan.io |
| **Censys** | ✅ 是 | https://censys.io |
| **Chaos** | ✅ 免费（有限额） | https://chaos.projectdiscovery.io |
| **GitHub** | ✅ 免费 | https://github.com/settings/tokens |

### 推荐的免费配置

```yaml
# 用于漏洞赏金的基础推荐配置
virustotal:
  - your_free_key
github:
  - your_github_token
chaos:
  - your_chaos_key
```

---

<a id="-output-options"></a>
## 📤 输出选项

### 输出格式

```bash
# 纯文本（默认）
subfinder -d target.com -o subs.txt

# JSON 输出
subfinder -d target.com -oJ -o subs.json

# JSON Lines（逐行 JSON）
subfinder -d target.com -oJL -o subs.jsonl

# CSV 格式（原文参数含义有误，见译注）
subfinder -d target.com -oD -o subs.csv
```

### 输出过滤

```bash
# 静默模式（纯净输出）
subfinder -d target.com -silent

# 仅显示新子域名（排除已知项；参数适用性见译注）
subfinder -d target.com -exclude-subdomains known.txt

# 无颜色输出
subfinder -d target.com -nc
```

### 通过管道传递输出

```bash
# 通过管道传递给 httpx
subfinder -d target.com -silent | httpx -silent

# 通过管道传递给 nuclei
subfinder -d target.com -silent | httpx -silent | nuclei

# 保存并处理
subfinder -d target.com -silent | tee subs.txt | httpx
```

---

<a id="-advanced-features"></a>
## ⚡ 高级功能

### 并发与性能

```bash
# 设置并发数（默认：10；仅主动解析时生效）
subfinder -d target.com -t 50

# 速率限制
subfinder -d target.com -rate-limit 100

# 超时
subfinder -d target.com -timeout 30
```

### 匹配与过滤

```bash
# 匹配指定模式
subfinder -d target.com -match "api,dev,stage"

# 过滤指定模式
subfinder -d target.com -filter "www,mail"
```

### DNS 解析器选项

```bash
# 使用自定义 DNS 解析器
subfinder -d target.com -rL resolvers.txt

# 解析子域名
subfinder -d target.com -nW
```

### 代理支持

```bash
# HTTP 代理
subfinder -d target.com -proxy http://127.0.0.1:8080

# SOCKS 代理（需核对所用版本支持情况）
subfinder -d target.com -proxy socks5://127.0.0.1:9050
```

---

<a id="-bug-bounty-workflow"></a>
## 🔄 漏洞赏金工作流程

### 完整信息收集管道

```bash
# 步骤 1：枚举子域名
subfinder -d target.com -all -o subs.txt

# 步骤 2：检查可访问的主机
cat subs.txt | httpx -o live.txt

# 步骤 3：使用 nuclei 扫描
nuclei -l live.txt -severity critical,high -o vulns.txt

# 单行命令
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### 多域名工作流程

```bash
# 枚举项目范围内的全部域名
subfinder -dL scope.txt -all -o all-subs.txt

# 处理结果
cat all-subs.txt | httpx -silent | nuclei
```

### 持续监测

```bash
# 保存基线
subfinder -d target.com -silent > baseline.txt

# 稍后查找新子域名
subfinder -d target.com -silent > current.txt
diff baseline.txt current.txt | grep ">" | cut -d" " -f2 > new-subs.txt
```

### 与其他工具集成

```bash
# 配合 httpx（检查可访问主机）
subfinder -d target.com -silent | httpx

# 配合 ffuf（目录模糊测试）
subfinder -d target.com -silent | httpx -silent | \
  xargs -I {} ffuf -u {}/FUZZ -w wordlist.txt

# 配合 nmap（端口扫描）
subfinder -d target.com -silent | dnsx -a -silent | nmap -iL -

# 完整管道
subfinder -d target.com -silent | \
  httpx -title -status-code -tech-detect -silent | \
  tee recon-results.txt
```

---

<a id="-real-world-examples"></a>
## 🎬 实践示例

### 示例 1：基础枚举
```bash
subfinder -d hackerone.com -silent
```

### 示例 2：使用所有数据源
```bash
subfinder -d target.com -all -v
```

### 示例 3：保存结果
```bash
subfinder -d target.com -o target-subs.txt
```

### 示例 4：多个域名
```bash
echo -e "target1.com\ntarget2.com" | subfinder -silent
```

### 示例 5：显示来源
```bash
subfinder -d target.com -cs
```

### 示例 6：JSON 输出
```bash
subfinder -d target.com -oJ -o results.json
```

### 示例 7：使用代理
```bash
subfinder -d target.com -proxy http://127.0.0.1:8080
```

### 示例 8：高性能
```bash
subfinder -d target.com -all -t 100 -o subs.txt
```

### 示例 9：完整漏洞赏金管道
```bash
subfinder -d target.com -all -silent | \
  httpx -silent -status-code -title | \
  tee live-hosts.txt | \
  nuclei -severity critical,high
```

### 示例 10：递归发现
```bash
subfinder -d target.com -recursive -all -silent
```

---

<a id="-quick-reference"></a>
## 📊 速查

### 常用命令

| 任务 | 命令 |
|------|---------|
| 基础发现 | `subfinder -d target.com` |
| 静默输出 | `subfinder -d target.com -silent` |
| 保存到文件 | `subfinder -d target.com -o subs.txt` |
| 所有数据源 | `subfinder -d target.com -all` |
| 显示来源 | `subfinder -d target.com -cs` |
| 多个域名 | `subfinder -dL domains.txt` |
| JSON 输出 | `subfinder -d target.com -oJ` |
| 列出数据源 | `subfinder -ls` |

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-d` | 目标域名 |
| `-dL` | 域名列表文件 |
| `-o` | 输出文件 |
| `-oJ` | JSON 输出 |
| `-silent` | 静默模式 |
| `-all` | 使用所有数据源 |
| `-cs` | 显示每个子域名的来源 |
| `-t` | 并发数 |
| `-recursive` | 递归枚举 |

### 常见管道

| 管道 | 命令 |
|----------|---------|
| 可访问主机 | `subfinder -d target.com -silent \| httpx` |
| 完整信息收集 | `subfinder -d target.com \| httpx \| nuclei` |
| 端口扫描 | `subfinder -d target.com -silent \| dnsx -a \| nmap -iL -` |

---

<a id="-tips--best-practices"></a>
## 💡 技巧与最佳实践

### 漏洞赏金建议

1. **配置 API 密钥**
   ```bash
   # 使用 API 可获得更多结果
   vim ~/.config/subfinder/provider-config.yaml
   ```

2. **使用所有数据源以扩大覆盖面**
   ```bash
   subfinder -d target.com -all
   ```

3. **配合其他工具**
   ```bash
   subfinder -d target.com -silent | httpx | nuclei
   ```

4. **监测新子域名**
   ```bash
   # 每天检查新资产
   subfinder -d target.com -silent > today.txt
   ```

### 性能建议

```bash
# 较高并发数
subfinder -d target.com -t 100 -all

# 为较慢的数据源设置超时
subfinder -d target.com -timeout 60
```

---

## ⚠️ 法律免责声明

> **警告：** Subfinder 用于被动信息收集，默认发现流程不直接与目标交互（主动解析选项见译注）。但管道中的后续工具可能直接访问目标。
> 
> - ✅ 用于获得授权的项目
> - ✅ 测试范围内的漏洞赏金目标
> - ✅ 自己拥有的域名
> - ❌ 切勿枚举未获授权的目标
> - ✅ 遵守项目测试范围（原文此项图标有误）
> 
> **始终确保已经获得授权。**

---

<a id="-resources"></a>
## 📚 参考资源

### 官方资源
- [Subfinder GitHub](https://github.com/projectdiscovery/subfinder)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [文档](https://docs.projectdiscovery.io/tools/subfinder/)

### 相关速查表
- [Nuclei](../../Nuclei/README.md)
- [ffuf](../../ffuf/README.md)
- [Nmap](../../Nmap/README.md)

---

<p align="center">
  <b>🔍 发现每一个子域名！</b><br>
  <i>漏洞赏金信息收集的第一步</i>
</p>
