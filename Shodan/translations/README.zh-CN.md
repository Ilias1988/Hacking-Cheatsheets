# 🌐 Shodan 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ███████╗██╗  ██╗ ██████╗ ██████╗  █████╗ ███╗   ██╗
  ██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔══██╗████╗  ██║
  ███████╗███████║██║   ██║██║  ██║███████║██╔██╗ ██║
  ╚════██║██╔══██║██║   ██║██║  ██║██╔══██║██║╚██╗██║
  ███████║██║  ██║╚██████╔╝██████╔╝██║  ██║██║ ╚████║
  ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝
                                                      
   面向联网设备的搜索引擎
```

<p align="center">
  <img src="https://img.shields.io/badge/Shodan-IoT_Search-red?style=for-the-badge" alt="Shodan">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Reconnaissance-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/IoT-Security-green?style=for-the-badge" alt="IoT">
</p>

<p align="center">
  <b>🔍 用于搜索联网设备、服务器和基础设施的搜索引擎</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [入门](#-getting-started)
- [基础过滤器](#-basic-filters)
- [网络过滤器](#-network-filters)
- [SSL/TLS 过滤器](#-ssltls-filters)
- [HTTP 过滤器](#-http-filters)
- [存在漏洞的服务](#-vulnerable-services)
- [物联网（IoT）与工业控制系统（ICS/SCADA）](#-iot--icsscada)
- [数据库](#-databases)
- [网络摄像头与媒体服务](#-webcams--media)
- [云服务](#-cloud-services)
- [漏洞赏金查询](#-bug-bounty-queries)
- [Shodan 命令行（CLI）](#-shodan-cli)
- [速查表](#-quick-reference)
- [参考资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**Shodan** 是一个搜索引擎，用户可以通过各种过滤器查找连接到互联网的特定类型的计算机、服务器和物联网设备。

### 可以找到什么？

| 类别 | 示例 |
|----------|----------|
| **服务器** | Web 服务器、FTP、SSH、数据库 |
| **物联网设备** | 摄像头、路由器、智能设备 |
| **ICS/SCADA** | 工业控制系统 |
| **云平台** | AWS、Azure、GCP 实例 |
| **漏洞** | 暴露的服务、默认凭据 |

### Shodan 网站入口

| 服务 | URL |
|---------|-----|
| **网页搜索** | https://www.shodan.io |
| **地图** | https://maps.shodan.io |
| **漏洞利用信息** | https://exploits.shodan.io |
| **图像** | https://images.shodan.io |

---

<a id="-getting-started"></a>
## 🚀 入门

### 创建账户

1. 访问 https://www.shodan.io
2. 创建免费账户
3. 从账户设置中获取 API 密钥

### 账户套餐

| 套餐 | 价格 | 功能 |
|------|-------|----------|
| **免费** | $0 | 基础搜索，结果数量有限 |
| **Membership 会员** | $49 | 更多结果、下载及命令行功能 |
| **Small Business** | $299/mo | 更多额度及监控功能 |
| **Corporate** | $899/mo | 无限制访问（原文表述，参见下方说明） |

> **套餐说明：** 上表金额与功能保留自原文，不代表现行报价。[官方套餐页](https://account.shodan.io/billing)的价格和额度已有变化，“无限制访问”也不表示没有速率或功能限制。[官方过滤器列表](https://www.shodan.io/search/filters)还将 `vuln` 与 `tag` 列为受套餐限制的过滤器。

### API 密钥

```bash
# 获取地址：https://account.shodan.io
# 导出环境变量供命令行使用
export SHODAN_API_KEY="YOUR_API_KEY"
```

---

<a id="-basic-filters"></a>
## 🔧 基础过滤器

### 文本搜索

```
# 简单搜索
apache
nginx
Microsoft-IIS

# 按产品搜索
product:apache
product:nginx
product:openssh
```

### 国家与城市

```
# 国家（ISO 3166-1 alpha-2 两字母代码）
country:US
country:DE
country:RU
country:CN
country:GR                      # 希腊

# 城市
city:Athens
city:"New York"
city:London
city:Tokyo

# 组合条件
country:US city:Miami
country:GR city:Thessaloniki
```

### 组织与互联网服务提供商（ISP）

```
# 组织
org:"Google"
org:"Amazon"
org:"Microsoft"
org:"Facebook"

# ISP
isp:"Comcast"
isp:"Verizon"
isp:"AT&T"
```

### 主机名与域名

```
# 主机名包含指定内容
hostname:google.com
hostname:.gov
hostname:.edu
hostname:target.com

# 指定域名
ssl.cert.subject.cn:target.com
```

### 操作系统

```
os:"Windows"
os:"Linux"
os:"Ubuntu"
os:"Windows Server 2019"
os:"Windows 7"
os:"FreeBSD"
```

---

<a id="-network-filters"></a>
## 🌐 网络过滤器

### 端口

```
# 指定端口
port:22                         # SSH
port:21                         # FTP
port:23                         # Telnet
port:80                         # HTTP
port:443                        # HTTPS
port:3389                       # RDP
port:3306                       # MySQL
port:5432                       # PostgreSQL
port:27017                      # MongoDB
port:6379                       # Redis
port:9200                       # Elasticsearch

# 多个端口
port:22,80,443
```

### IP 与网络

```
# 指定 IP
ip:8.8.8.8

# IP 地址范围（CIDR）
net:192.168.0.0/24
net:10.0.0.0/8
net:172.16.0.0/12

# ASN
asn:AS15169                     # Google
asn:AS16509                     # Amazon
asn:AS8075                      # Microsoft
```

### 地理位置

```
# 地理坐标
geo:37.9838,23.7275             # 雅典坐标
geo:40.7128,-74.0060,10         # 纽约坐标及半径

# 地区／州
region:California
region:"New York"
```

---

<a id="-ssltls-filters"></a>
## 🔐 SSL/TLS 过滤器

### 证书信息

```
# 证书主体
ssl.cert.subject.cn:target.com
ssl.cert.subject.cn:*.target.com

# 证书签发者
ssl.cert.issuer.cn:"Let's Encrypt"
ssl.cert.issuer.cn:"DigiCert"

# 自签名证书（原文示例，参见下方勘误）
ssl.cert.issuer.cn:ssl.cert.subject.cn
```

> **证书查询勘误：** 按[官方过滤器语法](https://help.shodan.io/the-basics/search-query-fundamentals)，冒号右侧是查询值；原文的 `ssl.cert.issuer.cn:ssl.cert.subject.cn` 不能当作比较签发者与主体两个字段是否相等的表达式。

### SSL 版本

```
# SSL 版本（存在安全风险）
ssl.version:sslv2
ssl.version:sslv3
ssl.version:tlsv1
ssl.version:tlsv1.1

# 安全版本（仍需结合配置评估）
ssl.version:tlsv1.2
ssl.version:tlsv1.3
```

### 证书有效期

```
# 已过期的证书
ssl.cert.expired:true

# 即将过期的证书（原文旧日期示例）
ssl.cert.expires:2024
```

> **日期说明：** 原文的 `ssl.cert.expires:2024` 是旧年份示例，不能再表示“即将过期”。该字段也未列于[当前官方过滤器参考](https://www.shodan.io/search/filters)；应核对现行支持的证书字段和日期语法。

### SSL 相关漏洞

```
# Heartbleed
vuln:CVE-2014-0160

# POODLE
ssl.version:sslv3

# DROWN
ssl.version:sslv2
```

---

<a id="-http-filters"></a>
## 🌍 HTTP 过滤器

### HTTP 状态与响应头

```
# HTTP 状态码
http.status:200
http.status:401
http.status:403
http.status:500

# HTTP 页面标题
http.title:"Dashboard"
http.title:"Login"
http.title:"Admin"
http.title:"Index of"

# HTML 内容
http.html:"password"
http.html:"admin"
http.html:"login"
```

### Web 技术

```
# Server 响应头
http.server:apache
http.server:nginx
http.server:Microsoft-IIS

# 指定版本
http.server:"Apache/2.4.49"     # 存在漏洞
http.server:"nginx/1.18"

# 网站图标哈希
http.favicon.hash:116323821     # 指定网站图标
http.favicon.hash:-1137975059   # Jenkins
```

### 组件

```
# Web 框架
http.component:"WordPress"
http.component:"Joomla"
http.component:"Drupal"
http.component:"Laravel"
http.component:"Django"

# 技术组件
http.component:"PHP"
http.component:"jQuery"
http.component:"Bootstrap"
```

---

<a id="-vulnerable-services"></a>
## ⚠️ 存在漏洞的服务

> **结果解释：** [Shodan 官方漏洞评估说明](https://help.shodan.io/mastery/vulnerability-assessment)区分已验证与未验证的漏洞；后者可能只依据版本等元数据关联，存在误报。页面标题、默认口令字符串、开放端口或 `-authentication` 文本排除也不能单独证明服务未认证或可被利用。

### 已知 CVE 漏洞

```
# 按 CVE 搜索
vuln:CVE-2021-44228            # Log4Shell
vuln:CVE-2021-26855            # ProxyLogon
vuln:CVE-2019-0708             # BlueKeep
vuln:CVE-2017-0144             # EternalBlue
vuln:CVE-2014-0160             # Heartbleed
vuln:CVE-2014-6271             # Shellshock

# 多个 CVE
vuln:CVE-2021-44228,CVE-2021-45046
```

### 默认凭据

```
# 默认密码
"default password"
"admin" "password"
http.title:"Login" "admin"

# 特定默认凭据
"admin:admin"
"root:root"
"admin:123456"
```

### 开放／暴露的服务

```
# 匿名 FTP
port:21 "Anonymous access granted"

# 开放的 Telnet 服务
port:23 -authentication

# 开放的 MongoDB 服务
port:27017 "MongoDB Server Information"

# 开放的 Redis 服务
port:6379 -authentication

# 开放的 Elasticsearch 服务
port:9200 "cluster_name"

# 开放的 Memcached 服务
port:11211 "STAT"
```

### 暴露的管理面板

```
# phpMyAdmin
http.title:"phpMyAdmin"

# cPanel
http.title:"cPanel"

# Webmin
http.title:"Webmin"

# Jenkins（未认证访问，原文说明）
http.title:"Dashboard [Jenkins]"

# Grafana
http.title:"Grafana"
```

---

<a id="-iot--icsscada"></a>
## 🏭 物联网（IoT）与工业控制系统（ICS/SCADA）

### 工业控制系统

```
# 一般工业控制系统（ICS）
tag:ics

# Modbus
port:502

# Siemens S7
port:102

# DNP3
port:20000

# BACnet
port:47808

# EtherNet/IP
port:44818

# Niagara Fox
port:1911
```

### SCADA 系统

```
# SCADA
tag:scada

# 特定 SCADA 系统
"SCADA" port:502
"PLC" country:US
"Siemens" port:102
"Schneider"
```

### 物联网设备

```
# 一般物联网设备（IoT）
tag:iot

# 路由器
"router" port:80
http.title:"Router"

# 打印机
port:9100
"printer"
http.title:"HP"

# 智能家居
"smart home"
"IoT Gateway"
```

### 网络设备

```
# Cisco
"cisco" product:cisco

# MikroTik
product:mikrotik
http.title:"RouterOS"

# Ubiquiti
product:ubiquiti

# Fortinet
product:fortigate
```

---

<a id="-databases"></a>
## 💾 数据库

### MySQL

```
port:3306
port:3306 "MySQL"
port:3306 "unauthorized"
product:mysql
```

### PostgreSQL

```
port:5432
port:5432 "PostgreSQL"
product:postgresql
```

### MongoDB

```
port:27017
port:27017 "MongoDB Server Information"
port:27017 "totalSize"
product:mongodb
"MongoDB Server Information" -authentication
```

### Redis

```
port:6379
port:6379 "redis"
port:6379 -authentication
product:redis
```

### Elasticsearch

```
port:9200
port:9200 "elastic"
port:9200 "cluster_name"
port:9200 "You Know, for Search"
product:elasticsearch
```

### CouchDB

```
port:5984
port:5984 "couchdb"
"couchdb" "Welcome"
```

### Memcached

```
port:11211
port:11211 "STAT"
product:memcached
```

### Cassandra

```
port:9042
port:9160
product:cassandra
```

---

<a id="-webcams--media"></a>
## 📹 网络摄像头与媒体服务

### IP 摄像头

```
# 通用网络摄像头
webcam
"webcam" country:US

# 摄像头品牌
"hikvision"
"dahua"
"axis"
"foscam"
"netcam"

# RTSP 流
port:554 has_screenshot:true
"rtsp" port:554
```

### DVR/NVR 录像系统

```
"dvr" port:80
"nvr" 
http.title:"DVR"
http.title:"NVR"
"Network Video Recorder"
```

### 流媒体

```
# VLC 流媒体
"VLC media player"

# 媒体服务器
product:plex
"Plex Media Server"
```

### 特定摄像头管理界面

```
# Hikvision
http.title:"DVRDVS-Webs"
"App-webs" port:80

# Dahua
http.title:"WEB SERVICE"

# Axis
http.title:"AXIS"
```

---

<a id="-cloud-services"></a>
## ☁️ 云服务

### Amazon AWS

```
# 一般 AWS 资产
org:"Amazon"
asn:AS16509

# S3 存储桶
"AmazonS3"

# EC2
"ec2" org:"Amazon"

# AWS 服务
http.title:"AWS"
```

### Microsoft Azure

```
org:"Microsoft"
asn:AS8075
hostname:azure.com
hostname:cloudapp.azure.com
```

### Google 云平台

```
org:"Google"
asn:AS15169
hostname:googleusercontent.com
```

### DigitalOcean

```
org:"DigitalOcean"
asn:AS14061
```

### 暴露的云管理面板

```
# Kubernetes 仪表盘
http.title:"Kubernetes Dashboard"

# Docker
product:docker
port:2375

# Jenkins
http.title:"Jenkins"

# GitLab
http.title:"GitLab"
```

---

<a id="-bug-bounty-queries"></a>
## 🐛 漏洞赏金查询

### 组织侦察

```
# 目标组织
org:"Target Company"
ssl.cert.subject.cn:target.com
hostname:target.com

# ASN 查询
asn:AS12345
net:1.2.3.0/24
```

### 查找暴露的服务

```
# 目标的所有服务
org:"Target" port:22,80,443,3306,5432,27017

# 管理面板
org:"Target" http.title:"admin"

# 登录页面
org:"Target" http.title:"login"
```

### 子域名发现

```
ssl.cert.subject.cn:*.target.com
ssl.cert.subject.cn:target.com
hostname:target.com
```

### 存在漏洞的服务

```
# 检查 CVE 漏洞
org:"Target" vuln:CVE-2021-44228

# 默认凭据
org:"Target" "default password"

# 开放的数据库
org:"Target" port:27017,6379,9200
```

> **复杂语法说明：** 原文中的 `||`、括号与空值 `vuln:` 示例均保持原样，不应直接假定所有搜索界面或版本均支持。请对照[基础语法](https://help.shodan.io/the-basics/search-query-fundamentals)和[现行过滤器参考](https://www.shodan.io/search/filters)核对；后者提供 `has_vuln`，并不意味着空的 `vuln:` 等价有效。

### 综合漏洞赏金查询

```
# 全面侦察
ssl.cert.subject.cn:target.com || hostname:target.com || org:"Target Company"

# 附加过滤条件
(ssl.cert.subject.cn:target.com || hostname:target.com) port:22,80,443,3306,5432
```

---

<a id="-shodan-cli"></a>
## 💻 Shodan 命令行（CLI）

### 安装

```bash
# 通过 pip 安装
pip install shodan

# 使用 API 密钥初始化
shodan init YOUR_API_KEY
```

### 基本命令

```bash
# 账户信息
shodan info

# 搜索
shodan search "apache"

# 带过滤条件的搜索
shodan search "port:22 country:US"

# 统计结果数量
shodan count "nginx"

# 下载结果
shodan download results.json.gz "apache"

# 解析结果
shodan parse results.json.gz

# 获取主机信息
shodan host 8.8.8.8
```

> **额度与行为说明：** [官方文档](https://help.shodan.io/the-basics/credit-types-explained)区分 query credits 与 scan credits，前者用于查询／下载，后者用于请求网络扫描。下方 `scan submit` 会触发扫描，不是单纯读取已有索引；本文命令仅作原文示例保留。

### 高级命令行用法

```bash
# 域名信息
shodan domain target.com

# 提交扫描（消耗扫描额度）
shodan scan submit 1.2.3.4

# Honeyscore（检测蜜罐）
shodan honeyscore 1.2.3.4

# 本机公网 IP 信息
shodan myip

# 统计分析
shodan stats "apache" --facets country,org
```

### API 用法（Python）

```python
import shodan

api = shodan.Shodan('YOUR_API_KEY')

# 搜索
results = api.search('apache')
for result in results['matches']:
    print(result['ip_str'], result['port'])

# 主机查询
host = api.host('8.8.8.8')
print(host['ip_str'], host['ports'])
```

---

<a id="-quick-reference"></a>
## 📊 速查表

### 常用过滤器

| 过滤器 | 说明 | 示例 |
|--------|-------------|---------|
| `port:` | 端口号 | `port:22` |
| `country:` | 国家代码 | `country:US` |
| `city:` | 城市名称 | `city:London` |
| `org:` | 组织 | `org:"Google"` |
| `hostname:` | 主机名 | `hostname:google.com` |
| `net:` | IP 地址范围／CIDR | `net:10.0.0.0/8` |
| `os:` | 操作系统 | `os:"Windows"` |
| `product:` | 产品名称 | `product:nginx` |
| `version:` | 版本 | `version:"2.4.49"` |
| `vuln:` | CVE 编号 | `vuln:CVE-2021-44228` |

### HTTP 过滤器

| 过滤器 | 说明 | 示例 |
|--------|-------------|---------|
| `http.title:` | 页面标题 | `http.title:"Admin"` |
| `http.status:` | HTTP 状态码 | `http.status:200` |
| `http.server:` | Server 响应头 | `http.server:nginx` |
| `http.html:` | HTML 内容 | `http.html:"login"` |
| `http.component:` | 技术 | `http.component:"WordPress"` |

### SSL 过滤器

| 过滤器 | 说明 | 示例 |
|--------|-------------|---------|
| `ssl.cert.subject.cn:` | 证书通用名称（CN） | `ssl.cert.subject.cn:target.com` |
| `ssl.cert.issuer.cn:` | 签发者通用名称（CN） | `ssl.cert.issuer.cn:"DigiCert"` |
| `ssl.version:` | SSL 版本 | `ssl.version:sslv3` |
| `ssl.cert.expired:` | 已过期的证书 | `ssl.cert.expired:true` |

### 10 个常用 Shodan 查询

| # | 查询 | 用途 |
|---|-------|---------|
| 1 | `port:22 country:US` | 美国的 SSH 服务 |
| 2 | `port:3389 country:US` | 美国的 RDP 服务 |
| 3 | `port:27017 -authentication` | 开放的 MongoDB 服务 |
| 4 | `vuln:CVE-2021-44228` | Log4Shell |
| 5 | `http.title:"Dashboard"` | 仪表盘 |
| 6 | `"default password"` | 默认凭据 |
| 7 | `port:9200 "elastic"` | Elasticsearch |
| 8 | `webcam` | IP 摄像头 |
| 9 | `product:apache vuln:` | 存在漏洞的 Apache 服务 |
| 10 | `ssl.cert.expired:true` | 已过期的证书 |

---

## 💡 技巧与最佳实践

### 漏洞赏金测试技巧

1. **从证书搜索开始**
   ```
   ssl.cert.subject.cn:target.com
   ```

2. **检查所有端口**
   ```
   hostname:target.com port:22,80,443,3306,5432
   ```

3. **查找暴露的服务**
   ```
   org:"Target" (port:27017 || port:9200 || port:6379)
   ```

4. **检查漏洞**
   ```
   org:"Target" vuln:
   ```

### 组合过滤器

```
# 复杂查询
port:22 country:US org:"Target" os:"Ubuntu"
http.title:"admin" -http.title:"Login" country:US
ssl.cert.subject.cn:target.com port:443 http.status:200
```

---

## ⚠️ 法律免责声明

> **警告：** Shodan 展示的是公开可访问的信息，但仍须注意：
> 
> - ✅ 仅用于经授权的侦察
> - ✅ 仅用于漏洞赏金计划范围内的目标
> - ✅ 以负责任的方式报告漏洞
> - ❌ 不要利用发现的漏洞
> - ❌ 未经许可不要访问系统
> - ❌ 尊重隐私并遵守法律
> 
> **未经授权的扫描违法！（原文警告）**

---

<a id="-resources"></a>
## 📚 参考资源

### 官方资源
- [Shodan](https://www.shodan.io)
- [Shodan 帮助中心](https://help.shodan.io)
- [Shodan 命令行（CLI）](https://cli.shodan.io)
- [Shodan API](https://developer.shodan.io)

### 相关速查表
- [Google 高级搜索](../../Google-Dorking/translations/README.zh-CN.md)
- [GitHub 高级搜索](../../GitHub-Dorking/translations/README.zh-CN.md)
- [Nmap](../../Nmap/translations/README.zh-CN.md)

---

<p align="center">
  <b>🌐 发现隐藏的互联网资产！</b><br>
  <i>掌握 Shodan 侦察技巧</i>
</p>
