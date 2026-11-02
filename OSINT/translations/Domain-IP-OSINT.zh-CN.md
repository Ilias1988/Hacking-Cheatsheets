# 🌐 域名与 IP 开源情报

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Domain-IP-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Domain-IP-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **译注：** DNS `ANY` 查询不保证返回全部记录；权威服务器可能仅返回很小的记录子集，参见 [RFC 8482](https://datatracker.ietf.org/doc/html/rfc8482)。AXFR 区域传送会直接向目标 DNS 服务器发出请求，属于主动交互。IP 地理位置通常只是估计结果，不能据此确定个人的精确位置。

---

## 🔍 域名信息收集工具

| 工具 | URL | 用途 |
|------|-----|---------|
| **WHOIS** | who.is | 域名注册信息 |
| **SecurityTrails** | securitytrails.com | DNS 历史记录 |
| **ViewDNS** | viewdns.info | DNS 工具 |
| **BuiltWith** | builtwith.com | 技术栈 |
| **Wappalyzer** | wappalyzer.com | 技术识别 |

---

## 🛠️ 命令

### WHOIS
```bash
whois target.com
whois 1.2.3.4
```

### DNS 枚举
```bash
# DNS 记录
dig target.com ANY
dig target.com MX
dig target.com TXT
nslookup -type=any target.com

# 区域传送（AXFR）
dig axfr @ns1.target.com target.com
host -t axfr target.com ns1.target.com
```

### 子域名发现
```bash
# Subfinder
subfinder -d target.com -o subs.txt

# Amass
amass enum -d target.com -o subs.txt

# Assetfinder
assetfinder target.com
```

---

## 🔎 IP 开源情报

| 工具 | URL | 用途 |
|------|-----|---------|
| **Shodan** | shodan.io | 设备搜索 |
| **Censys** | censys.io | 互联网资产扫描 |
| **IPinfo** | ipinfo.io | IP 地理位置查询 |
| **AbuseIPDB** | abuseipdb.com | IP 信誉查询 |

### Shodan 命令行工具
```bash
shodan host 1.2.3.4
shodan search "hostname:target.com"
shodan domain target.com
```

### IP 查询
```bash
curl ipinfo.io/1.2.3.4
curl ip-api.com/json/1.2.3.4
whois 1.2.3.4
```

---

## 📋 域名检查清单

```markdown
□ WHOIS 记录
□ DNS 记录（A、MX、TXT、NS）
□ 子域名
□ 历史 DNS（SecurityTrails）
□ 技术栈
□ SSL 证书信息
□ IP 地址范围
□ 反向 IP 查询
□ 自治系统号（ASN）信息
```

---

**返回 OSINT:** [🔍 OSINT 概览](./README.zh-CN.md)
