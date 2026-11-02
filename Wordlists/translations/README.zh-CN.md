<a id="-wordlists---complete-reference-guide"></a>
# 📚 字典（Wordlists）完整参考指南

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ██╗███████╗████████╗███████╗
  ██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██║██╔════╝╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     ██║███████╗   ██║   ███████╗
  ██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██║╚════██║   ██║   ╚════██║
  ╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗██║███████║   ██║   ███████║
   ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Wordlists-Reference-red?style=for-the-badge" alt="Wordlists">
  <img src="https://img.shields.io/badge/SecLists-orange?style=for-the-badge" alt="SecLists">
  <img src="https://img.shields.io/badge/Brute_Force-blue?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/Fuzzing-green?style=for-the-badge" alt="Fuzzing">
</p>

<p align="center">
  <b>📖 渗透测试字典完整参考</b><br>
  <a href="./README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="./README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 目录

- [密码字典](#-password-wordlists)
- [目录／文件字典](#-directoryfile-wordlists)
- [子域名字典](#-subdomain-wordlists)
- [用户名字典](#-username-wordlists)
- [Web 模糊测试字典](#-web-fuzzing-wordlists)
- [SecLists 合集](#-seclists-collection)
- [Kali 默认字典](#-kali-default-wordlists)
- [自定义字典工具](#-custom-wordlist-tools)
- [快速下载](#-quick-download)
- [技巧与最佳实践](#-tips--best-practices)

---

<a id="-password-wordlists"></a>
## 🔐 密码字典

<a id="most-popular"></a>
### 常用字典

| 字典 | 大小 | 行数 | 来源 | 用途 |
|----------|------|-------|--------|----------|
| **rockyou.txt** | 139 MB | 14.3M | Kali/SecLists | 密码恢复 |
| **darkweb2017-top10000.txt** | 83 KB | 10K | SecLists | 常见密码 |
| **xato-net-10-million-passwords.txt** | 83 MB | 10M | SecLists | 大型密码字典 |
| **common-passwords-win.txt** | - | - | SecLists | Windows 密码 |
| **10-million-password-list-top-1000000.txt** | 8.1 MB | 1M | SecLists | 前 1M 个密码 |

<a id="password-lists-by-type"></a>
### 按类型划分的密码字典

```
SecLists/Passwords/
├── Common-Credentials/
│   ├── 10-million-password-list-top-100.txt
│   ├── 10-million-password-list-top-1000.txt
│   ├── 10-million-password-list-top-10000.txt
│   ├── 10-million-password-list-top-100000.txt
│   └── 10-million-password-list-top-1000000.txt
├── Leaked-Databases/
│   └── rockyou.txt
├── darkweb2017-top1000.txt
├── darkweb2017-top10000.txt
└── Default-Credentials/
    ├── default-passwords.txt
    └── tomcat-betterdefaultpasslist.txt
```

<a id="specialized-password-lists"></a>
### 专门用途的密码字典

| 字典 | 用途 | 位置 |
|----------|---------|----------|
| **wifi-passwords.txt** | Wi-Fi 密钥恢复 | SecLists/Passwords/ |
| **probable-v2-top12000.txt** | 统计分析 | SecLists/Passwords/ |
| **seasons-only.txt** | 季节性模式 | SecLists/Passwords/Honeypot-Captures/ |
| **twitter-banned.txt** | 禁止使用的密码 | SecLists/Passwords/ |
| **cirt-default-passwords.txt** | 默认凭据 | SecLists/Passwords/Default-Credentials/ |

<a id="kali-locations"></a>
### Kali 中的位置

```bash
# RockYou（先解压）
/usr/share/wordlists/rockyou.txt.gz
gunzip /usr/share/wordlists/rockyou.txt.gz

# John the Ripper
/usr/share/john/password.lst

# Metasploit
/usr/share/metasploit-framework/data/wordlists/
```

---

<a id="-directoryfile-wordlists"></a>
## 📂 目录／文件字典

<a id="directory-brute-forcing"></a>
### 目录枚举

| 字典 | 大小 | 行数 | 来源 | 用途 |
|----------|------|-------|--------|----------|
| **common.txt** | 36 KB | 4.6K | DirBuster | 快速扫描 |
| **directory-list-2.3-small.txt** | 756 KB | 87K | DirBuster | 小规模枚举 |
| **directory-list-2.3-medium.txt** | 1.9 MB | 220K | DirBuster | 常规枚举 |
| **directory-list-2.3-big.txt** | 14 MB | 1.2M | DirBuster | 完整枚举 |
| **big.txt** | 210 KB | 20K | SecLists | 常见目录 |

<a id="raft-series-highly-recommended"></a>
### RAFT 系列（强烈推荐）

```
SecLists/Discovery/Web-Content/
├── raft-large-directories.txt      # 62K 个目录
├── raft-large-files.txt            # 37K 个文件
├── raft-large-words.txt            # 120K 个词项
├── raft-medium-directories.txt     # 30K 个目录
├── raft-medium-files.txt           # 17K 个文件
├── raft-medium-words.txt           # 63K 个词项
├── raft-small-directories.txt      # 20K 个目录
├── raft-small-files.txt            # 11K 个文件
└── raft-small-words.txt            # 43K 个词项
```

<a id="file-extension-lists"></a>
### 文件扩展名字典

| 字典 | 用途 |
|----------|---------|
| **web-extensions.txt** | 常见 Web 扩展名 |
| **backup-extensions.txt** | 备份文件（.bak、.old） |
| **raft-large-extensions.txt** | 大型扩展名字典 |

<a id="specialized-discovery"></a>
### 专项发现

| 字典 | 用途 | 行数 |
|----------|---------|-------|
| **apache.txt** | 针对 Apache | - |
| **nginx.txt** | 针对 Nginx | - |
| **iis.txt** | 针对 IIS | - |
| **api-endpoints.txt** | API 发现 | - |
| **graphql.txt** | GraphQL 端点 | - |
| **swagger.txt** | Swagger/OpenAPI | - |

<a id="common-locations"></a>
### 常见位置

```bash
# DirBuster
/usr/share/dirbuster/wordlists/
/usr/share/dirb/wordlists/

# SecLists 发现类字典
/usr/share/seclists/Discovery/Web-Content/

# 快速下载
common.txt
directory-list-2.3-medium.txt
raft-medium-directories.txt
```

---

<a id="-subdomain-wordlists"></a>
## 🌐 子域名字典

<a id="popular-subdomain-lists"></a>
### 常用子域名字典

| 字典 | 行数 | 来源 | 用途 |
|----------|-------|--------|----------|
| **subdomains-top1million-5000.txt** | 5K | SecLists | 快速枚举 |
| **subdomains-top1million-20000.txt** | 20K | SecLists | 常规枚举 |
| **subdomains-top1million-110000.txt** | 110K | SecLists | 深入枚举 |
| **dns-Jhaddix.txt** | 2M+ | SecLists | 漏洞赏金 |
| **fierce-hostlist.txt** | - | SecLists | 兼容 Fierce |
| **combined_subdomains.txt** | 648K | SecLists | 合并字典 |

<a id="subdomain-lists-location"></a>
### 子域名字典位置

```
SecLists/Discovery/DNS/
├── subdomains-top1million-5000.txt
├── subdomains-top1million-20000.txt
├── subdomains-top1million-110000.txt
├── dns-Jhaddix.txt                    # 适合漏洞赏金研究！
├── combined_subdomains.txt
├── deepmagic.com-prefixes-top500.txt
├── deepmagic.com-prefixes-top50000.txt
├── fierce-hostlist.txt
├── namelist.txt
├── shubs-subdomains.txt
└── bitquark-subdomains-top100000.txt
```

<a id="bug-bounty-recommended"></a>
### 漏洞赏金推荐字典

```bash
# Jason Haddix 方法
dns-Jhaddix.txt              # 2M+ 个子域名

# 合并以扩大覆盖范围
cat subdomains-top1million-110000.txt dns-Jhaddix.txt | sort -u > mega-subdomain.txt
```

---

<a id="-username-wordlists"></a>
## 👤 用户名字典

<a id="common-username-lists"></a>
### 常用用户名字典

| 字典 | 行数 | 用途 |
|----------|-------|---------|
| **top-usernames-shortlist.txt** | 17 | 快速检查 |
| **xato-net-10-million-usernames.txt** | 10M | 大型字典 |
| **cirt-default-usernames.txt** | - | 默认用户 |
| **names.txt** | - | 常见姓名 |
| **familynames-usa-top1000.txt** | 1K | 姓氏 |

<a id="username-lists-location"></a>
### 用户名字典位置

```
SecLists/Usernames/
├── top-usernames-shortlist.txt
├── xato-net-10-million-usernames.txt
├── cirt-default-usernames.txt
├── Names/
│   ├── names.txt
│   ├── familynames-usa-top1000.txt
│   └── malenames-usa-top1000.txt
└── Honeypot-Captures/
    └── multiplesources-users-fabian-fingerle.de.txt
```

<a id="default-credentials"></a>
### 默认凭据

| 服务 | 字典 |
|---------|----------|
| **FTP** | ftp-betterdefaultpasslist.txt |
| **SSH** | ssh-betterdefaultpasslist.txt |
| **Tomcat** | tomcat-betterdefaultpasslist.txt |
| **MySQL** | mysql-betterdefaultpasslist.txt |
| **MSSQL** | mssql-betterdefaultpasslist.txt |
| **PostgreSQL** | postgres-betterdefaultpasslist.txt |

---

<a id="-web-fuzzing-wordlists"></a>
## 💉 Web 模糊测试字典

<a id="sql-injection"></a>
### SQL 注入

```
SecLists/Fuzzing/SQLi/
├── Generic-SQLi.txt
├── quick-SQLi.txt
├── MSSQL.fuzzdb.txt
├── MySQL.fuzzdb.txt
├── PostgreSQL.fuzzdb.txt
└── oracle.fuzzdb.txt
```

<a id="xss-cross-site-scripting"></a>
### XSS（跨站脚本）

```
SecLists/Fuzzing/XSS/
├── XSS-Bypass-Strings-BruteLogic.txt
├── XSS-Cheat-Sheet-PortSwigger.txt
├── XSS-Jhaddix.txt
├── XSS-RSNAKE.txt
├── XSS-Vectors-Mario.txt
└── xss-without-parentheses-semi-colons-portswigger.txt
```

<a id="lfirfi-localremote-file-inclusion"></a>
### LFI/RFI（本地／远程文件包含）

```
SecLists/Fuzzing/LFI/
├── LFI-Jhaddix.txt
├── LFI-LFISuite-pathtotest-huge.txt
├── LFI-LFISuite-pathtotest.txt
└── LFI-gracefulsecurity-linux.txt
```

<a id="command-injection"></a>
### 命令注入

```
SecLists/Fuzzing/command-injection-commix.txt
SecLists/Fuzzing/command-injection-testing-unix.txt
SecLists/Fuzzing/command-injection-testing-windows.txt
```

<a id="ssti-server-side-template-injection"></a>
### SSTI（服务端模板注入）

```
SecLists/Fuzzing/SSTI/
├── SSTI.txt
└── tplmap-template-injection.txt
```

<a id="fuzzing-collections"></a>
### 模糊测试字典合集

| 字典 | 用途 |
|----------|---------|
| **special-chars.txt** | 特殊字符 |
| **Unicode.txt** | Unicode 模糊测试 |
| **big-list-of-naughty-strings.txt** | 边界情况 |
| **fuzz-Bo0oM.txt** | 通用模糊测试 |
| **burp-parameter-names.txt** | 参数名 |

---

<a id="-seclists-collection"></a>
## 📦 SecLists 合集

<a id="about-seclists"></a>
### 关于 SecLists

**SecLists** 是渗透测试人员的实用合集，汇集了安全评估中使用的多种字典。

<a id="installation"></a>
### 安装

```bash
# Kali Linux（原文称预装，实际取决于镜像及软件包）
ls /usr/share/seclists/

# Ubuntu/Debian
sudo apt install seclists

# 使用 Git 克隆
git clone https://github.com/danielmiessler/SecLists.git

# 快速下载（指定字典）
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt
```

<a id="seclists-structure"></a>
### SecLists 目录结构

```
SecLists/
├── Discovery/
│   ├── DNS/                    # 子域名字典
│   ├── Web-Content/            # 目录／文件字典
│   └── Infrastructure/         # 服务／端口
├── Fuzzing/
│   ├── SQLi/                   # SQL 注入载荷
│   ├── XSS/                    # XSS 载荷
│   ├── LFI/                    # LFI/RFI 载荷
│   └── command-injection*.txt  # 命令注入
├── Passwords/
│   ├── Common-Credentials/     # 密码字典
│   ├── Leaked-Databases/       # rockyou 等字典
│   └── Default-Credentials/    # 默认密码
├── Usernames/
│   ├── Names/                  # 姓名字典
│   └── top-usernames*.txt      # 用户名字典
├── Payloads/
│   └── Anti-Antivirus/         # 反病毒检测绕过
└── Pattern-Matching/
    └── Hunting/                # 正则表达式模式
```

<a id="github-repository"></a>
### GitHub 仓库

🔗 https://github.com/danielmiessler/SecLists

---

<a id="-kali-default-wordlists"></a>
## 🐧 Kali 默认字典

<a id="wordlists-location"></a>
### 字典位置

```bash
# 主字典目录
/usr/share/wordlists/

# 内容
ls -la /usr/share/wordlists/
├── rockyou.txt.gz          # 使用 gunzip 解压
├── dirb/                   # DIRB 字典
├── dirbuster/              # DirBuster 字典
├── fasttrack.txt           # Fast track 字典
├── fern-wifi/              # Wi-Fi 字典
├── metasploit/             # MSF 字典
├── nmap.lst                # Nmap 脚本列表（原文标注，实际用途见译注）
├── wfuzz/                  # wFuzz 字典
└── seclists -> /usr/share/seclists/
```

<a id="dirbuster-lists"></a>
### DirBuster 字典

```bash
ls /usr/share/dirbuster/wordlists/
├── apache-user-enum-1.0.txt
├── apache-user-enum-2.0.txt
├── directories.jbrofuzz
├── directory-list-1.0.txt
├── directory-list-2.3-big.txt
├── directory-list-2.3-medium.txt
├── directory-list-2.3-small.txt
└── directory-list-lowercase-2.3-big.txt
```

<a id="dirb-lists"></a>
### DIRB 字典

```bash
ls /usr/share/dirb/wordlists/
├── big.txt                  # 20K 条
├── common.txt               # 4.6K 条
├── small.txt                # 959 条
├── catala.txt               # 加泰罗尼亚语
├── euskera.txt              # 巴斯克语
├── extensions_common.txt    # 扩展名
├── indexes.txt              # 索引文件
├── mutations_common.txt     # 变形规则
├── spanish.txt              # 西班牙语
├── stress/                  # 压力测试
└── vulns/                   # 漏洞检查
```

---

<a id="-custom-wordlist-tools"></a>
## 🛠️ 自定义字典工具

<a id="cewl-custom-word-list-generator"></a>
### CeWL（自定义词表生成器）

```bash
# 安装
sudo apt install cewl

# 基本用法：爬取网站
cewl https://target.com -w wordlist.txt

# 设置深度和最小词长
cewl https://target.com -d 3 -m 6 -w wordlist.txt

# 包含邮箱地址
cewl https://target.com -e -w wordlist.txt

# 选项
-d <depth>      # 爬取深度（默认：2）
-m <length>     # 最小词长（默认：3）
-w <file>       # 输出文件
-e              # 包含邮箱地址
--lowercase     # 转换为小写
-c              # 统计词项出现次数
```

<a id="crunch-wordlist-generator"></a>
### Crunch（字典生成器）

```bash
# 安装
sudo apt install crunch

# 基本语法
crunch <min-len> <max-len> [charset] -o <output>

# 生成 4-6 字符密码
crunch 4 6 -o wordlist.txt

# 使用指定字符集
crunch 8 8 abcdefghijklmnopqrstuvwxyz0123456789 -o wordlist.txt

# 使用模式
crunch 6 6 -t @@%%^^ -o wordlist.txt
# @ = 小写字母, , = 大写字母, % = 数字, ^ = 特殊字符

# 自定义字符集
crunch 4 4 -f /usr/share/crunch/charset.lst mixalpha-numeric -o wordlist.txt

# 通过管道传给 Hashcat
crunch 8 8 0123456789 | hashcat -m 0 hash.txt
```

<a id="cupp-common-user-passwords-profiler"></a>
### CUPP（基于用户画像的密码字典工具）

```bash
# 安装
git clone https://github.com/Mebus/cupp.git
cd cupp

# 交互模式：基于用户画像
python3 cupp.py -i

# 询问信息：
# - 名字、姓氏、昵称
# - 出生日期
# - 伴侣信息
# - 宠物名字
# - 公司名称
# - 关键词

# 下载常见密码字典
python3 cupp.py -l

# 基于现有字典进行画像处理
python3 cupp.py -w wordlist.txt
```

<a id="mentalist-gui-wordlist-generator"></a>
### Mentalist（图形界面字典生成器）

```bash
# 用于生成复杂字典的图形界面工具
# 支持规则、大小写变换和字符替换
# 下载地址：https://github.com/sc0tfree/mentalist
```

<a id="wordlister-python"></a>
### Wordlister（Python）

```python
#!/usr/bin/env python3
# 简单的自定义字典生成器
import itertools

# 生成组合
chars = 'abc123'
length = 4

for combo in itertools.product(chars, repeat=length):
    print(''.join(combo))
```

<a id="hashcat-rules"></a>
### Hashcat 规则

```bash
# 使用规则对字典进行变形
hashcat -r /usr/share/hashcat/rules/best64.rule wordlist.txt --stdout

# 常用规则
/usr/share/hashcat/rules/
├── best64.rule         # 精选 64 条规则
├── rockyou-30000.rule  # 基于 RockYou
├── d3ad0ne.rule        # d3ad0ne 的规则
├── dive.rule           # DIVE 规则
└── toggles1-5.rule     # 切换大小写
```

---

<a id="-quick-download"></a>
## ⬇️ 快速下载

<a id="one-liner-downloads"></a>
### 单行下载命令

```bash
# RockYou
wget https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt

# SecLists（完整合集）
git clone --depth 1 https://github.com/danielmiessler/SecLists.git

# 常见密码（1M）
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt

# 中型目录字典
wget https://raw.githubusercontent.com/daviddias/node-dirbuster/master/lists/directory-list-2.3-medium.txt

# 子域名字典
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/subdomains-top1million-110000.txt

# Jhaddix DNS（漏洞赏金）
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/dns-Jhaddix.txt
```

<a id="clone-repositories"></a>
### 克隆仓库

```bash
# SecLists
git clone https://github.com/danielmiessler/SecLists.git

# FuzzDB
git clone https://github.com/fuzzdb-project/fuzzdb.git

# PayloadsAllTheThings
git clone https://github.com/swisskyrepo/PayloadsAllTheThings.git

# Assetnote Wordlists
git clone https://github.com/assetnote/wordlists.git
```

---

<a id="-tips--best-practices"></a>
## 💡 技巧与最佳实践

<a id="choosing-the-right-wordlist"></a>
### 选择合适的字典

| 任务 | 推荐字典 |
|------|---------------------|
| **快速目录扫描** | common.txt, raft-small-directories.txt |
| **深入目录扫描** | directory-list-2.3-medium.txt |
| **完整目录扫描** | directory-list-2.3-big.txt |
| **子域名枚举** | subdomains-top1million-20000.txt |
| **漏洞赏金 DNS 发现** | dns-Jhaddix.txt |
| **密码恢复** | rockyou.txt |
| **快速密码测试** | top-10000.txt |

<a id="combining-wordlists"></a>
### 合并字典

```bash
# 合并并去重
cat list1.txt list2.txt | sort -u > combined.txt

# 为所有行添加前缀
sed 's/^/admin_/' wordlist.txt > admin_wordlist.txt

# 添加后缀
sed 's/$/.php/' wordlist.txt > php_wordlist.txt

# 两种后缀
while read line; do echo "${line}.php"; echo "${line}.html"; done < wordlist.txt > extended.txt
```

<a id="wordlist-optimization"></a>
### 字典优化

```bash
# 排序并去重
sort -u wordlist.txt -o wordlist.txt

# 删除空行
sed -i '/^$/d' wordlist.txt

# 删除长度小于 4 字符的行
awk 'length >= 4' wordlist.txt > filtered.txt

# 转换为小写
tr '[:upper:]' '[:lower:]' < wordlist.txt > lowercase.txt

# 统计行数
wc -l wordlist.txt
```

<a id="performance-tips"></a>
### 性能建议

1. **从小字典开始** - 先使用较小的字典，再扩大范围
2. **使用模式** - 已知格式时，使用特定模式
3. **组合工具** - 生成自定义字典并配合标准字典
4. **针对目标的字典** - 使用 CeWL 创建针对目标的字典

---

<a id="-quick-reference"></a>
## 📊 速查

<a id="top-wordlists-by-category"></a>
### 按类别选择常用字典

| 类别 | 优先选择 | 位置 |
|----------|-------------|----------|
| **密码** | rockyou.txt | SecLists/Passwords/ |
| **目录** | directory-list-2.3-medium.txt | DirBuster |
| **文件** | raft-large-files.txt | SecLists |
| **子域名** | dns-Jhaddix.txt | SecLists/Discovery/DNS/ |
| **用户名** | xato-net-10-million-usernames.txt | SecLists/Usernames/ |
| **SQLi** | Generic-SQLi.txt | SecLists/Fuzzing/SQLi/ |
| **XSS** | XSS-Jhaddix.txt | SecLists/Fuzzing/XSS/ |
| **LFI** | LFI-Jhaddix.txt | SecLists/Fuzzing/LFI/ |

<a id="size-guide"></a>
### 规模指南

| 大小 | 行数 | 用途 |
|------|-------|----------|
| **小型** | < 5K | 快速测试 |
| **中型** | 5K - 100K | 常规枚举 |
| **大型** | 100K - 1M | 深入扫描 |
| **超大型** | > 1M | 全面覆盖 |

---

<a id="-resources"></a>
## 📚 参考资料

<a id="wordlist-repositories"></a>
### 字典仓库
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Assetnote Wordlists](https://github.com/assetnote/wordlists)

<a id="tools"></a>
### 工具
- [CeWL](https://github.com/digininja/CeWL)
- [CUPP](https://github.com/Mebus/cupp)
- [Crunch](https://sourceforge.net/projects/crunch-wordlist/)
- [Mentalist](https://github.com/sc0tfree/mentalist)

<a id="related-cheatsheets"></a>
### 相关速查表
- [Hydra](../../Hydra/translations/README.zh-CN.md)
- [John the Ripper](../../John-The-Ripper/translations/README.zh-CN.md)
- [Hashcat](../../Hashcat/translations/README.zh-CN.md)
- [Gobuster](../../Gobuster/translations/README.zh-CN.md)
- [ffuf](../../ffuf/translations/README.zh-CN.md)

---

<p align="center">
  <b>📚 合适的字典能显著影响测试效果！</b><br>
  <i>合理选择，灵活组合</i>
</p>

> 译注：文件名、路径、大小和行数均按原文保留，可能因字典版本、压缩状态或发行版而变化，不能视为当前文件清单或精确统计。[SecLists 官方说明](https://github.com/danielmiessler/SecLists)中的 apt 安装示例针对 Kali；不能据此假设原文 Ubuntu/Debian 片段在默认软件源可用。Kali 是否预装 SecLists，也取决于所用镜像和安装的软件包。

> 译注：[Kali 官方字典文档](https://www.kali.org/tools/wordlists/)将 nmap.lst 链接到 Nmap 的 passwords.lst，因此原文“Nmap scripts list”注释不准确：它是密码字典，不是 NSE 脚本清单。文件树是参考示意，路径和旧版 Hashcat 规则文件名须按本地版本核对。

> 译注：排序去重会改变候选项的优先顺序；去空行、改小写和长度过滤也会移除或改变候选项，是否适合取决于测试目的。Crunch 的组合数会随字符集和长度迅速增长，完整穷举不保证可行；字典更大也不代表覆盖所有目标。原始生成、转换及下载命令均保留原样，本次翻译未执行它们。
