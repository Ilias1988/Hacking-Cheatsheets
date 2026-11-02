# 🕷️ Nikto 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **版本说明：** 本文保留原文命令、统计数字和示例文件。数据库规模、插件和格式支持会随版本变化。`-update` 及 `UPDATES/2.1.5` 地址属于旧版更新示例；官方说明指出从 2.1.6 起使用 Git 更新源码安装版本，发行版安装则需按其软件包维护方式处理。参见 [Nikto 官方更新说明](https://github.com/sullo/nikto/wiki/Updating)。
>
> **参数勘误：** `-id "admin:password"` 仍是用户名与密码，并不是 Base64 数据。`-Save` 在官方文档中用于把命中的响应保存到目录，不能当作完整扫描状态保存；当前选项表也未列出 `-Resume` 和 `-cookies`，因此本文的恢复扫描、Cookie 文件示例需要按所用版本核对。参见 [官方参数说明](https://github.com/sullo/nikto/wiki/Annotated-Option-List)。
>
> **结果解读：** `-maxtime` 是时间限制，并非 TLS 专用选项。规避参数或更改 User-Agent 不保证避开检测；“完整审计”也只是原文中的命令组合名称，实际覆盖面受插件、调优及目标配置影响。

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   _ _ _    _        
   | \ | (_) | _| |_ ___  
   |  \| | | |/ / __/ _ \ 
   | |\  | |   <| || (_) |
   |_| \_|_|_|\_\\__\___/ 
                          
   Web 服务器扫描器——始于 2001 年
```

<p align="center">
  <img src="https://img.shields.io/badge/Nikto-Web_Scanner-blue?style=for-the-badge" alt="Nikto">
  <img src="https://img.shields.io/badge/Vulnerability-Scanner-red?style=for-the-badge" alt="Vulnerability">
  <img src="https://img.shields.io/badge/CGI-Testing-green?style=for-the-badge" alt="CGI">
  <img src="https://img.shields.io/badge/SSL-Analysis-purple?style=for-the-badge" alt="SSL">
</p>

<p align="center">
  <b>🌐 用于安全测试的开源 Web 服务器扫描器</b>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [基本语法](#-basic-syntax)
- [指定目标](#-target-specification)
- [端口与 SSL 选项](#-port--ssl-options)
- [扫描调优](#-scan-tuning)
- [身份验证](#-authentication)
- [代理与规避](#-proxy--evasion)
- [输出与报告](#-output--reporting)
- [数据库更新](#-database-updates)
- [实践示例](#-real-world-examples)
- [速查](#-quick-reference)
- [技巧与最佳实践](#-tips--best-practices)
- [参考资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**Nikto** 是一款开源 Web 服务器扫描器，可对 Web 服务器开展多项检查，包括以下内容：

### Nikto 检查哪些内容

| 类别 | 说明 |
|----------|-------------|
| **危险文件** | 可能带来风险的文件（备份文件等） |
| **过时版本** | 过时的服务器软件 |
| **特定版本问题** | 特定版本存在的漏洞 |
| **服务器配置** | 配置错误 |
| **CGI 脚本** | 可能存在风险的 CGI 脚本 |
| **SSL/TLS** | SSL 证书问题 |
| **响应头** | 缺失或不安全的 HTTP 响应头 |
| **请求方法** | 危险的 HTTP 请求方法 |

### 数据库统计

```
✅ 6,700+ 可能存在风险的文件/CGI
✅ 1,250+ 过时的服务器版本
✅ 270+ 特定版本问题
✅ 1,000+ 服务器配置问题
```

### 功能特点

| 功能 | 说明 |
|---------|-------------|
| **覆盖范围广** | 检查数千种漏洞 |
| **插件式设计** | 可扩展架构 |
| **多种格式** | HTML、XML、CSV、JSON 输出 |
| **SSL 支持** | 完整的 HTTPS 测试 |
| **IDS（入侵检测系统）规避** | 内置规避技术 |
| **身份验证** | 支持 Basic、Digest、NTLM |

---

<a id="-installation"></a>
## 📥 安装

### Kali Linux（预装）
```bash
# 更新 Nikto
sudo apt update && sudo apt install nikto

# 更新数据库
nikto -update
```

### Ubuntu/Debian
```bash
sudo apt install nikto
```

### 从 GitHub 获取
```bash
git clone https://github.com/sullo/nikto
cd nikto/program
perl nikto.pl -h
```

### macOS (Homebrew)
```bash
brew install nikto
```

### 验证安装
```bash
nikto -Version
nikto -h          # 帮助
```

---

<a id="-basic-syntax"></a>
## ⌨️ 基本语法

### 通用语法
```bash
nikto -h <host> [options]
```

### 快速开始
```bash
# 基础扫描
nikto -h http://target.com

# 指定端口扫描
nikto -h target.com -p 80

# 扫描 HTTPS
nikto -h https://target.com

# 扫描 IP 地址
nikto -h 192.168.1.1
```

---

<a id="-target-specification"></a>
## 🎯 指定目标

### 单个目标
```bash
# 按主机名
nikto -h target.com

# 按 IP 地址
nikto -h 192.168.1.1

# 指定协议
nikto -h http://target.com
nikto -h https://target.com
```

### 多个目标
```bash
# 从文件读取
nikto -h targets.txt

# targets.txt:
# http://target1.com
# http://target2.com:8080
# https://target3.com
```

### 带路径的目标
```bash
# 扫描指定路径
nikto -h http://target.com/app

# 扫描指定目录
nikto -h http://target.com/admin/
```

---

<a id="-port--ssl-options"></a>
## 🔌 端口与 SSL 选项

### 端口选项
```bash
# 单个端口
nikto -h target.com -p 80

# 多个端口
nikto -h target.com -p 80,443,8080

# 端口范围
nikto -h target.com -p 80-90

# 常见 Web 端口
nikto -h target.com -p 80,443,8000,8080,8443
```

### SSL/TLS 选项

| 选项 | 说明 |
|--------|-------------|
| `-ssl` | 强制使用 SSL 模式 |
| `-nossl` | 禁用 SSL |
| `-maxtime` | 最长扫描时间 |

```bash
# 强制使用 SSL
nikto -h target.com -ssl

# 禁用 SSL（仅 HTTP）
nikto -h target.com -nossl

# 在非标准端口上使用 SSL
nikto -h target.com -p 8443 -ssl
```

### 虚拟主机
```bash
# 指定虚拟主机
nikto -h 192.168.1.1 -vhost target.com

# 适用情境：
# - 目标位于负载均衡器之后
# - 同一个 IP 上托管多个站点
```

---

<a id="-scan-tuning"></a>
## 🎚️ 扫描调优

### 调优选项（-Tuning）

| 代码 | 说明 |
|------|-------------|
| `0` | 文件上传 |
| `1` | 值得关注的文件 / 日志中出现的文件 |
| `2` | 配置错误 / 默认文件 |
| `3` | 信息泄露 |
| `4` | 注入（XSS/脚本/HTML） |
| `5` | 远程文件读取——Web 根目录内 |
| `6` | 拒绝服务 |
| `7` | 远程文件读取——整个服务器范围 |
| `8` | 命令执行 / 远程 shell |
| `9` | SQL 注入 |
| `a` | 身份验证绕过 |
| `b` | 软件识别 |
| `c` | 远程源码包含 |
| `d` | Web 服务 |
| `e` | 管理控制台 |
| `x` | 反向选择调优选项 |

### 使用调优选项
```bash
# 仅检查 SQL 注入
nikto -h target.com -Tuning 9

# 检查 XSS 和 SQL 注入
nikto -h target.com -Tuning 49

# 检查多种类型
nikto -h target.com -Tuning 1234

# 排除指定测试（x 前缀）
nikto -h target.com -Tuning x6    # 排除 DoS 测试

# 除 DoS 之外的全部测试
nikto -h target.com -Tuning x6
```

### 插件
```bash
# 列出可用插件
nikto -list-plugins

# 运行指定插件
nikto -h target.com -Plugins apacheusers

# 运行多个插件
nikto -h target.com -Plugins "apacheusers;robots"

# 排除插件
nikto -h target.com -Plugins "@@ALL;-robots"
```

### 常见插件

| 插件 | 说明 |
|--------|-------------|
| `apacheusers` | Apache 用户枚举 |
| `cgi` | CGI 脚本测试 |
| `robots` | robots.txt 分析 |
| `headers` | HTTP 响应头分析 |
| `httpoptions` | HTTP 请求方法测试 |
| `outdated` | 检查软件是否过时 |
| `ssl` | SSL/TLS 分析 |
| `dictionary` | 字典攻击 |

---

<a id="-authentication"></a>
## 🔐 身份验证

### Basic 身份验证
```bash
# 用户名和密码
nikto -h target.com -id admin:password

# Base64 格式（原文标注有误，见译注）
nikto -h target.com -id "admin:password"
```

### Digest 身份验证
```bash
# Digest 认证（自动检测）
nikto -h target.com -id admin:password
```

### NTLM 身份验证
```bash
# NTLM 认证
nikto -h target.com -id admin:password:domain
```

### 基于 Cookie 的身份验证
```bash
# 发送 Cookie
nikto -h target.com -cookies "session=abc123; token=xyz789"

# 从文件读取
nikto -h target.com -cookies cookies.txt
```

---

<a id="-proxy--evasion"></a>
## 🛡️ 代理与规避

### 代理支持
```bash
# HTTP 代理
nikto -h target.com -useproxy http://127.0.0.1:8080

# 需要身份验证的代理
nikto -h target.com -useproxy http://user:pass@proxy:8080

# 通过 Burp Suite
nikto -h target.com -useproxy http://127.0.0.1:8080
```

### IDS 规避（-evasion）

| 代码 | 技术 |
|------|-----------|
| `1` | 随机 URI 编码（非 UTF8） |
| `2` | 目录自引用（/./） |
| `3` | 提前结束 URL |
| `4` | 在前面添加长随机字符串 |
| `5` | 伪造参数 |
| `6` | 使用 TAB 作为请求分隔符 |
| `7` | 更改 URL 的大小写 |
| `8` | 使用 Windows 目录分隔符（\） |
| `A` | 使用回车字符（0x0d） |
| `B` | 使用二进制值 0x0b |

```bash
# 单种规避技术
nikto -h target.com -evasion 1

# 多种规避技术
nikto -h target.com -evasion 1234

# 所有规避技术（原文示例，具体选项依版本而异）
nikto -h target.com -evasion 12345678AB
```

### User-Agent
```bash
# 自定义 User-Agent
nikto -h target.com -useragent "Mozilla/5.0 (Windows NT 10.0)"

# Googlebot
nikto -h target.com -useragent "Googlebot/2.1"
```

### 请求时间控制
```bash
# 请求之间暂停的时间（秒）
nikto -h target.com -Pause 2

# 最长扫描时间（秒）
nikto -h target.com -maxtime 3600

# 单次请求超时
nikto -h target.com -timeout 10
```

---

<a id="-output--reporting"></a>
## 📄 输出与报告

### 输出格式

| 选项 | 格式 |
|--------|--------|
| `-o file` | 输出到文件 |
| `-Format htm` | HTML 格式 |
| `-Format xml` | XML 格式 |
| `-Format csv` | CSV 格式 |
| `-Format txt` | 文本格式 |
| `-Format json` | JSON 格式 |
| `-Format nbe` | NBE 格式 |
| `-Format sql` | SQL 格式 |

### 保存结果
```bash
# 文本输出
nikto -h target.com -o report.txt

# HTML 报告
nikto -h target.com -o report.html -Format htm

# XML 报告
nikto -h target.com -o report.xml -Format xml

# JSON 报告
nikto -h target.com -o report.json -Format json

# CSV 报告
nikto -h target.com -o report.csv -Format csv
```

### 详细程度选项
```bash
# 显示选项
nikto -h target.com -Display V    # 详细输出
nikto -h target.com -Display D    # 调试输出
nikto -h target.com -Display E    # HTTP 错误
nikto -h target.com -Display P    # 进度
nikto -h target.com -Display S    # 从输出中隐藏 IP
nikto -h target.com -Display 1    # 显示重定向
nikto -h target.com -Display 2    # 显示 Cookie
nikto -h target.com -Display 3    # 显示 200/OK 响应
nikto -h target.com -Display 4    # 显示需要认证的 URL

# 组合多个显示选项
nikto -h target.com -Display VEP
```

### 保存进度
```bash
# 保存扫描状态（原文称可稍后恢复，见译注）
nikto -h target.com -Save scan_state.txt

# 恢复扫描（原文示例，见译注）
nikto -Resume scan_state.txt
```

---

<a id="-database-updates"></a>
## 🔄 数据库更新

### 更新 Nikto 数据库
```bash
# 更新所有数据库
nikto -update

# 手动更新（如果 -update 失败）
cd /var/lib/nikto
wget https://cirt.net/nikto/UPDATES/2.1.5/db_outdated.csv
wget https://cirt.net/nikto/UPDATES/2.1.5/db_tests.csv
```

### 数据库存放位置
```bash
# 默认位置
/usr/share/nikto/databases/
/var/lib/nikto/

# 数据库文件
db_tests         # 主测试数据库
db_outdated      # 过时的软件版本
db_server_msgs   # 服务器消息
db_variables     # 变量
db_404_strings   # 404 页面字符串
```

---

<a id="-real-world-examples"></a>
## 🎬 实践示例

### 示例 1：基础 Web 服务器扫描
```bash
nikto -h http://target.com
```

### 示例 2：综合 HTTPS 扫描
```bash
nikto -h https://target.com -ssl -p 443 -o report.html -Format htm
```

### 示例 3：扫描多个端口
```bash
nikto -h target.com -p 80,443,8080,8443 -o results.txt
```

### 示例 4：经过身份验证的扫描
```bash
nikto -h http://target.com/admin \
    -id admin:password \
    -o auth_scan.html \
    -Format htm
```

### 示例 5：隐蔽扫描（IDS 规避；并不保证不被检测）
```bash
nikto -h target.com \
    -evasion 1234 \
    -Pause 2 \
    -useragent "Mozilla/5.0 (Windows NT 10.0)" \
    -o stealth_scan.txt
```

### 示例 6：通过 Burp 代理
```bash
nikto -h https://target.com \
    -useproxy http://127.0.0.1:8080 \
    -o proxy_scan.html \
    -Format htm
```

### 示例 7：重点检查 SQL 注入
```bash
nikto -h target.com \
    -Tuning 9 \
    -o sqli_scan.txt
```

### 示例 8：完整审计
```bash
nikto -h https://target.com \
    -ssl \
    -p 443 \
    -Tuning x6 \
    -Display VEP \
    -o full_audit.html \
    -Format htm
```

### 示例 9：虚拟主机测试
```bash
nikto -h 192.168.1.10 \
    -vhost app.target.com \
    -p 80,443 \
    -o vhost_scan.txt
```

### 示例 10：从目标文件读取并扫描
```bash
nikto -h targets.txt \
    -o batch_scan.html \
    -Format htm
```

---

<a id="-quick-reference"></a>
## 📊 速查

### 常用命令

| 任务 | 命令 |
|------|---------|
| 基础扫描 | `nikto -h target.com` |
| HTTPS 扫描 | `nikto -h target.com -ssl` |
| 多个端口 | `nikto -h target.com -p 80,443,8080` |
| 使用身份验证 | `nikto -h target.com -id user:pass` |
| HTML 报告 | `nikto -h target.com -o report.html -Format htm` |
| 规避 | `nikto -h target.com -evasion 123` |
| 通过代理 | `nikto -h target.com -useproxy http://127.0.0.1:8080` |
| 更新数据库 | `nikto -update` |

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-h` | 目标主机 |
| `-p` | 要扫描的端口 |
| `-ssl` | 强制使用 SSL 模式 |
| `-id` | 身份验证凭据 |
| `-o` | 输出文件 |
| `-Format` | 输出格式 |
| `-Tuning` | 扫描调优 |
| `-evasion` | IDS 规避 |
| `-useproxy` | 代理服务器 |
| `-vhost` | 虚拟主机 |
| `-Pause` | 测试之间的延迟 |
| `-timeout` | 请求超时 |

### 调优速查

| 代码 | 类别 |
|------|----------|
| 1-4 | 信息/配置错误 |
| 4-5 | 注入/读取 |
| 6 | DoS（避免用于生产环境） |
| 7-8 | 远程访问 |
| 9 | SQL 注入 |
| a | 身份验证绕过 |
| b | 软件识别 |
| x | 排除 |

---

<a id="-tips--best-practices"></a>
## 💡 技巧与最佳实践

### 性能建议

1. **从基础扫描开始**
   ```bash
   nikto -h target.com
   ```

2. **排除 DoS 测试**
   ```bash
   nikto -h target.com -Tuning x6
   ```

3. **使用合适的超时时间**
   ```bash
   nikto -h target.com -timeout 15 -Pause 1
   ```

### 准确性建议

1. **定期更新数据库**
   ```bash
   nikto -update
   ```

2. **使用详细输出模式**
   ```bash
   nikto -h target.com -Display VE
   ```

3. **结合其他工具**
   ```bash
   # 先用 Nmap，再用 Nikto
   nmap -sV -p 80,443 target.com
   nikto -h target.com -p 80,443
   ```

### 常见问题

| 问题 | 解决办法 |
|-------|----------|
| 扫描缓慢 | 减小 `-Pause`，增大 `-timeout` |
| SSL 错误 | 显式添加 `-ssl` 参数 |
| 身份验证问题 | 检查 `-id` 格式 |
| 缺少结果 | 更新数据库并检查插件 |

---

## ⚠️ 法律免责声明

> **警告：** Nikto 是 Web 服务器扫描器，仅应用于 **获得授权的测试**。
> 
> - ✅ 扫描自己拥有的服务器
> - ✅ 获得明确书面许可后进行扫描
> - ✅ 用于获得授权的渗透测试
> - ❌ 切勿在未获授权时扫描
> - ❌ 未获授权的扫描属于违法行为
> 
> **Nikto 可能产生大量流量，并触发安全告警。**

---

<a id="-resources"></a>
## 📚 参考资源

### 官方资源
- [Nikto GitHub](https://github.com/sullo/nikto)
- [Nikto 文档](https://cirt.net/Nikto2)
- [CIRT.net](https://cirt.net/)

### 学习资源
- [OWASP 测试指南](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### 相关速查表
- [Nmap](../../Nmap/README.md)
- [Gobuster](../../Gobuster/README.md)
- [Burp Suite](../../Burp-Suite/README.md)

---

<p align="center">
  <b>🕷️ 掌握 Web 服务器扫描技术！</b><br>
  <i>在攻击者之前发现漏洞</i>
</p>
