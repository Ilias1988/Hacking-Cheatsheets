# 🌐 Amass - 攻击面测绘

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

```
   █████╗ ███╗   ███╗ █████╗ ███████╗███████╗
  ██╔══██╗████╗ ████║██╔══██╗██╔════╝██╔════╝
  ███████║██╔████╔██║███████║███████╗███████╗
  ██╔══██║██║╚██╔╝██║██╔══██║╚════██║╚════██║
  ██║  ██║██║ ╚═╝ ██║██║  ██║███████║███████║
  ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
         深入绘制攻击面
```

<p align="center">
  <img src="https://img.shields.io/badge/Amass-OWASP-blue?style=for-the-badge" alt="Amass">
  <img src="https://img.shields.io/badge/Subdomain-Discovery-green?style=for-the-badge" alt="Subdomain">
  <img src="https://img.shields.io/badge/ASN-Mapping-red?style=for-the-badge" alt="ASN"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 目录

- [Amass 是什么](#-what-is-amass)
- [安装](#-installation)
- [枚举模式](#-enumeration-mode)
- [Intel 情报模式](#-intel-mode)
- [DNS 模式](#-dns-mode)
- [配置](#-configuration)
- [速查表](#-quick-reference)

---

<a id="-what-is-amass"></a>
## 🎯 Amass 是什么

**Amass** 是 OWASP 旗下用于网络测绘和外部资产发现的项目：

- 🔍 **子域名枚举** - 功能最全面的工具
- 🏢 **ASN（自治系统编号）发现** - 查找组织的 IP 地址范围
- 📡 **DNS 情报** - 被动与主动侦察
- 🗺️ **攻击面测绘** - 可视化展示基础设施

### 为什么使用 Amass？

| 功能 | 优势 |
|---------|---------|
| 55+ 个数据源 | 尽可能全面的结果 |
| 被动与主动模式 | 选择侦察活动的可见程度 |
| ASN 与组织情报 | 发现隐藏的基础设施 |
| 图数据库 | 可视化展示关联关系 |

---

<a id="-installation"></a>
## 🚀 安装

> **版本说明：** 原文混用了旧版命令与 v4 安装写法，以下示例均按原样保留。[当前官方安装文档](https://owasp-amass.github.io/docs/)已采用 v5；[Kali 的 v5.1.1 帮助](https://www.kali.org/tools/amass/)列出 `assoc`、`engine`、`enum`、`subs`、`track`、`viz`，并未列出下文的 `intel` 或 `dns`。安装方式、子命令和参数须与实际版本匹配。

```bash
# 使用 Go 安装
go install -v github.com/owasp-amass/amass/v4/...@master

# Kali Linux
sudo apt install amass

# Docker
docker pull caffix/amass

# 验证版本
amass -version
```

---

<a id="-enumeration-mode"></a>
## 🔍 枚举模式

### 基础子域名枚举

```bash
# 被动枚举（不直接接触目标）
amass enum -passive -d example.com

# 主动枚举（原文称为 DNS 暴力枚举，参见下方说明）
amass enum -active -d example.com

# 使用字典进行暴力枚举
amass enum -active -brute -d example.com -w wordlist.txt
```

> **参数说明：** 在[官方旧版指南](https://github.com/owasp-amass/amass/wiki/User-Guide)中，`-active` 用于启用主动侦察，`-brute` 才控制子域名暴力枚举；两者并非同义。

### 输出选项

```bash
# 保存到文件
amass enum -passive -d example.com -o subdomains.txt

# JSON 格式输出
amass enum -passive -d example.com -json output.json

# 保存到目录
amass enum -passive -d example.com -dir ./amass_output/
```

### 多个目标

```bash
# 多个域名
amass enum -passive -d example.com -d target.com

# 从文件读取
amass enum -passive -df domains.txt -o results.txt
```

### 高级枚举

```bash
# 设置超时时间（分钟）
amass enum -passive -d example.com -timeout 30

# 限制使用的数据源
amass enum -passive -d example.com -src -include CertSpotter,Crtsh,HackerTarget

# 排除指定数据源
amass enum -passive -d example.com -exclude Google,Bing

# 显示数据源
amass enum -passive -d example.com -src
```

### 递归枚举

```bash
# 启用递归搜索（原文说明，参见下方参数勘误）
amass enum -passive -d example.com -r

# 设置递归深度（原文说明，需核对版本支持）
amass enum -passive -d example.com -max-depth 3
```

---

> **递归参数勘误：** [官方旧版指南](https://github.com/owasp-amass/amass/wiki/User-Guide)中的 `-r` 接收 DNS 解析器的 IP 地址，并非递归开关；递归暴力枚举相关参数为 `-min-for-recursive` 和 `-norecursive`。原文中的 `-max-depth` 应先核对所用版本是否支持。

<a id="-intel-mode"></a>
## 🏢 Intel 情报模式

> Intel 情报模式用于发现与组织相关的根域名、ASN 和 CIDR（无类别域间路由）地址范围。

### 查找组织资产

```bash
# 按组织名称查询
amass intel -org "Tesla Inc"
amass intel -org "Microsoft"

# 反向 WHOIS 查询
amass intel -whois -d example.com
```

> **范围说明：** [官方旧版指南](https://github.com/owasp-amass/amass/wiki/User-Guide)中的 `-org` 是对自治系统描述进行文本搜索，不保证列出组织的全部域名或资产。

### ASN（自治系统编号）发现

```bash
# 查找域名的 ASN（原文说明，输出含义需核对版本）
amass intel -d example.com

# 按 ASN 枚举
amass intel -asn 13335
amass intel -active -asn 13335

# 多个 ASN
amass intel -asn 13335,15169
```

### CIDR 地址范围

```bash
# 查找 CIDR 地址范围内的资产
amass intel -active -cidr 192.168.1.0/24

# 多个 CIDR 地址范围
amass intel -cidr 192.168.1.0/24 -cidr 10.0.0.0/8
```

### IP 地址范围

```bash
# IP 地址范围（原文说明，参见下方参数勘误）
amass intel -active -ip 192.168.1.1-254
```

---

> **IP 范围参数勘误：** [官方旧版指南](https://github.com/owasp-amass/amass/wiki/User-Guide)使用 `-addr` 输入 IP 地址或范围，`-ip` 则控制显示发现名称对应的 IP 地址；上方原文示例将两者混淆。

<a id="-dns-mode"></a>
## 📡 DNS 模式

### DNS 查询

```bash
# 基础 DNS 查询
amass dns -d example.com

# 指定记录类型
amass dns -d example.com -t A,AAAA,MX,NS,TXT
```

### 解析子域名

```bash
# 解析已发现的子域名
amass dns -df subdomains.txt
```

---

<a id="-configuration"></a>
## ⚙️ 配置

### 配置文件位置

```bash
# 默认位置
~/.config/amass/config.yaml

# 指定自定义配置
amass enum -config ./my_config.yaml -d example.com
```

### 配置文件示例

```yaml
# config.yaml
scope:
  domains:
    - example.com
    - target.com

resolvers:
  - 8.8.8.8
  - 8.8.4.4
  - 1.1.1.1

options:
  log: /tmp/amass.log
  max_dns_queries: 10000

data_sources:
  Crtsh:
  - 
  SecurityTrails:
  - credentials:
      apikey: YOUR_API_KEY
  Shodan:
  - credentials:
      apikey: YOUR_SHODAN_KEY
  VirusTotal:
  - credentials:
      apikey: YOUR_VT_KEY
```

> **配置说明：** 下方表格及上方 YAML 均保留自原文。配置路径、字段与凭据格式随版本变化，不能把这份示例当作所有版本通用的配置；应对照[对应版本的官方配置文档](https://owasp-amass.github.io/docs/configuration/)。数据源数量、免费额度与密钥要求也可能变化。

### 使用 API 密钥的数据源

| 数据源 | 免费额度 | 是否需要 API 密钥 |
|--------|-----------|--------------|
| Crt.sh | ✅ | 否 |
| SecurityTrails | ✅ | 是 |
| Shodan | ✅ | 是 |
| VirusTotal | ✅ | 是 |
| Censys | ✅ | 是 |
| PassiveTotal | 有限 | 是 |
| BinaryEdge | 有限 | 是 |

---

<a id="-quick-reference"></a>
## 📊 速查表

### 枚举命令

| 命令 | 说明 |
|---------|-------------|
| `amass enum -passive -d domain.com` | 被动子域名枚举 |
| `amass enum -active -d domain.com` | 主动 DNS 枚举 |
| `amass enum -active -brute -d domain.com` | 暴力枚举子域名 |
| `amass enum -d domain.com -o out.txt` | 保存到文件 |
| `amass enum -d domain.com -json out.json` | JSON 格式输出 |

### Intel 情报命令

| 命令 | 说明 |
|---------|-------------|
| `amass intel -org "Company"` | 按组织名称查找资产 |
| `amass intel -asn 12345` | 按 ASN 枚举 |
| `amass intel -cidr 10.0.0.0/8` | 枚举 CIDR 地址范围 |
| `amass intel -whois -d domain.com` | 反向 WHOIS 查询 |

### 常用参数

| 参数 | 说明 |
|------|-------------|
| `-d` | 目标域名 |
| `-df` | 从文件读取域名 |
| `-o` | 输出文件 |
| `-passive` | 仅使用被动模式（不进行 DNS 查询） |
| `-active` | 启用主动 DNS 侦察 |
| `-brute` | DNS 暴力枚举 |
| `-src` | 显示数据源 |
| `-timeout` | 超时时间，单位为分钟 |

---

## 🎯 漏洞赏金测试流程

```bash
# 1. 被动枚举（原文称为安全）
amass enum -passive -d target.com -o passive_subs.txt

# 2. 主动侦察并进行暴力枚举
amass enum -active -brute -d target.com -w dns_wordlist.txt -o active_subs.txt

# 3. 查找组织资产
amass intel -org "Target Inc" -o org_domains.txt

# 4. 合并结果
cat passive_subs.txt active_subs.txt | sort -u > all_subdomains.txt

# 5. 解析存活主机（原文说明；此处实际探测 HTTP 服务）
cat all_subdomains.txt | httpx -silent > live_hosts.txt
```

---

> **流程说明：** 最后一步中的 [ProjectDiscovery httpx](https://docs.projectdiscovery.io/opensource/httpx/overview)用于探测 HTTP/HTTPS 服务；其结果不等同于全部存活主机，也不只是 DNS 解析结果。

## 📚 参考资源

- [OWASP Amass GitHub](https://github.com/owasp-amass/amass)
- [Amass 用户指南](https://github.com/owasp-amass/amass/blob/master/doc/user_guide.md)

### 相关速查表
- [漏洞赏金测试方法](../../Bug-Bounty-Methodology/translations/README.zh-CN.md)
- [Subfinder](../../Subfinder/translations/README.zh-CN.md)
- [httpx](../../httpx/translations/README.zh-CN.md)

---

<p align="center">
  <b>🌐 绘制攻击面！</b><br>
  <i>Amass - 功能最全面的子域名工具</i>
</p>
