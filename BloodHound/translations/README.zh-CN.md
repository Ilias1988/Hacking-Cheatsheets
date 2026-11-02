# 🩸 BloodHound：Active Directory 攻击路径可视化

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗      ██████╗  ██████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗   ██╗███╗   ██╗██████╗ 
  ██╔══██╗██║     ██╔═══██╗██╔═══██╗██╔══██╗██║  ██║██╔═══██╗██║   ██║████╗  ██║██╔══██╗
  ██████╔╝██║     ██║   ██║██║   ██║██║  ██║███████║██║   ██║██║   ██║██╔██╗ ██║██║  ██║
  ██╔══██╗██║     ██║   ██║██║   ██║██║  ██║██╔══██║██║   ██║██║   ██║██║╚██╗██║██║  ██║
  ██████╔╝███████╗╚██████╔╝╚██████╔╝██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ 
                            AD 攻击路径可视化
```

<p align="center">
  <img src="https://img.shields.io/badge/BloodHound-Active_Directory-red?style=for-the-badge" alt="BloodHound">
  <img src="https://img.shields.io/badge/Attack_Paths-blue?style=for-the-badge" alt="Attack Paths">
  <img src="https://img.shields.io/badge/Graph_Database-green?style=for-the-badge" alt="Graph">
</p>

---

## 📋 目录

- [BloodHound 是什么](#-what-is-bloodhound)
- [安装](#-installation)
- [SharpHound 数据收集](#-sharphound-collection)
- [使用 BloodHound 图形界面](#-using-bloodhound-gui)
- [内置查询](#-built-in-queries)
- [自定义 Cypher 查询](#-custom-cypher-queries)
- [攻击路径分析](#-attack-path-analysis)
- [BloodHound CE](#-bloodhound-ce)
- [速查表](#-quick-reference)

---

<a id="-what-is-bloodhound"></a>
## 🎯 BloodHound 是什么

**BloodHound** 利用图论揭示 Active Directory 中隐藏的攻击路径，可将以下信息可视化：

- 🔗 **攻击路径** - 通往域管理员权限的路径
- 👤 **用户关系** - 组成员关系与会话
- 💻 **计算机信任关系** - 管理员权限与会话
- 🔓 **错误配置** - 可被滥用的权限
- 🎯 **高价值目标** - 关键账户

### 组成部分

| 组件 | 说明 |
|-----------|-------------|
| **BloodHound** | 用于可视化的图形界面（基于 Neo4j，指旧版） |
| **SharpHound** | 数据收集器（.exe 或 .ps1） |
| **Neo4j** | 图数据库后端 |
| **BloodHound CE** | 新的云／企业版本（原文表述，参见下方勘误） |

---

<a id="-installation"></a>
## 🚀 安装

> **版本范围：** 原文混合了桌面版 BloodHound Legacy 与 BloodHound CE 的安装、界面和收集器示例，以下命令均原样保留，不能视为同一套部署流程。CE 指 **Community Edition（社区版）**，不是 Enterprise（企业版）的简称。现行 CE 安装请对照[官方快速入门](https://bloodhound.specterops.io/get-started/quickstart/community-edition-quickstart)；独立启动 Neo4j 与桌面客户端的步骤属于旧版流程。

### Kali Linux

```bash
# 安装 BloodHound
sudo apt update
sudo apt install bloodhound

# 安装 Neo4j
sudo apt install neo4j

# 启动 Neo4j
sudo neo4j start

# 访问 Neo4j 浏览器界面：http://localhost:7474
# 默认凭据：neo4j / neo4j（首次登录时更改）

# 启动 BloodHound
bloodhound
```

### Windows

```powershell
# 从 GitHub Releases 下载
https://github.com/BloodHoundAD/BloodHound/releases

# 安装 Neo4j Community Edition
https://neo4j.com/download/

# 启动 Neo4j
# 以管理员身份运行并启动服务

# 运行 BloodHound.exe
```

### Docker（原文推荐）

```bash
# 采用可供 BloodHound 使用的配置启动 Neo4j
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/bloodhound \
  neo4j:4.4

# 等待启动完成，然后运行 BloodHound
bloodhound
```

---

<a id="-sharphound-collection"></a>
## 📡 SharpHound 数据收集

### 下载 SharpHound

```bash
# 从 BloodHound Releases 获取
https://github.com/BloodHoundAD/BloodHound/tree/master/Collectors

# 文件：
# - SharpHound.exe（独立可执行文件）
# - SharpHound.ps1（PowerShell 脚本）
```

### 基本收集

> **收集器说明：** [官方 SharpHound CE 文档](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound)说明，无参数运行使用 `Default`，并不等于显式指定 `All`。收集方式、参数、输出名称与数据格式会随版本变化；原文列出的 `--skipdns`、旧收集方式及文件列表需逐项核对。CE 收集器可从界面的 Download Collectors 获取；“隐蔽”或“低活动量”也不保证不会产生网络活动或告警。

```powershell
# 运行 SharpHound（原文将默认方式称为全部方式，参见上方说明）
.\SharpHound.exe

# 指定域
.\SharpHound.exe -d domain.local

# 指定输出位置
.\SharpHound.exe -o C:\Temp\

# PowerShell 版本
Import-Module .\SharpHound.ps1
Invoke-BloodHound
```

### 收集方式

```powershell
# 所有收集方式
.\SharpHound.exe -c All

# 指定收集方式
.\SharpHound.exe -c Default
.\SharpHound.exe -c Group
.\SharpHound.exe -c Session
.\SharpHound.exe -c Trusts
.\SharpHound.exe -c ACL
.\SharpHound.exe -c Container
.\SharpHound.exe -c RDP
.\SharpHound.exe -c DCOM
.\SharpHound.exe -c PSRemote
.\SharpHound.exe -c LocalAdmin
.\SharpHound.exe -c LocalGroup
.\SharpHound.exe -c SPNTargets
.\SharpHound.exe -c DCOnly
```

### 收集方式说明

| 方式 | 说明 | 活动显著程度（原文估计） |
|--------|-------------|-------|
| Default | Group, LocalAdmin, Session, Trusts | 低 |
| All | 所有收集方式 | 高 |
| Session | 已登录用户 | 中 |
| LocalGroup | 本地组成员 | 中 |
| ACL | ACL 权限 | 低 |
| DCOnly | 仅从域控制器（DC）收集 | 很低 |
| Group | 组成员关系 | 低 |
| Trusts | 域信任关系 | 低 |

### 隐蔽收集（原文名称）

```powershell
# 隐蔽模式（原文称较慢、活动较少）
.\SharpHound.exe -c DCOnly
.\SharpHound.exe --stealth

# 不进行 DNS 解析（原文说明，需核对参数版本）
.\SharpHound.exe --skipdns

# 请求之间的延迟
.\SharpHound.exe --throttle 1000
```

### 循环收集（会话）

```powershell
# 在一段时间内持续收集会话
.\SharpHound.exe -c Session --loop --loopduration 02:00:00

# 按指定间隔循环收集
.\SharpHound.exe -c Session --loop --loopinterval 00:05:00
```

### 从 Linux 收集（bloodhound.py）

> **Python 收集器兼容性：** [BloodHound.py 项目](https://github.com/dirkjanm/BloodHound.py)区分 Legacy 与 CE 分支和安装包。原文的 `bloodhound`／`bloodhound-python` 示例属于 Legacy 系列，不能直接假定其输出兼容 CE。

```bash
# 安装
pip install bloodhound

# 运行收集器
bloodhound-python -u user -p 'password' -d domain.local -ns 10.10.10.1 -c All

# 使用哈希
bloodhound-python -u user --hashes :NTLMHASH -d domain.local -c All
```

---

<a id="-using-bloodhound-gui"></a>
## 🖥️ 使用 BloodHound 图形界面

### 启动 BloodHound

```bash
# 1. 启动 Neo4j
sudo neo4j start

# 2. 等待 Neo4j 启动（检查 http://localhost:7474）

# 3. 启动 BloodHound
bloodhound

# 4. 登录
# URL：bolt://localhost:7687
# 用户名：neo4j
# 密码：（你的密码）
```

### 导入数据

```
1. 点击 "Upload Data"（上传数据）按钮，即文件夹图标
2. 选择 SharpHound 生成的 .zip 文件
3. 等待导入完成
4. 数据出现在关系图中
```

### 界面概览

| 区域 | 说明 |
|---------|-------------|
| **Search（搜索）** | 查找用户、计算机和组 |
| **Analysis（分析）** | 预设查询 |
| **Filters（筛选器）** | 设置边和节点的可见性 |
| **Graph（关系图）** | 图形化展示 |
| **Node Info（节点信息）** | 所选对象的详细信息 |

### 导航操作

```
- 单击鼠标左键：选择节点
- 单击鼠标右键：打开上下文菜单
- 滚动滚轮：放大或缩小
- 拖动：平移视图
- Ctrl+Click：添加到当前选择
```

---

<a id="-built-in-queries"></a>
## 🔍 内置查询

### 域信息

```
- Find all Domain Admins（查找所有域管理员）
- Find all Domain Controllers（查找所有域控制器）
- Find high value targets（查找高价值目标）
- Map Domain Trusts（绘制域信任关系）
```

### 最短路径

```
- Shortest Paths to Domain Admins（通往域管理员的最短路径）
- Shortest Path from Owned Principals（从已控制安全主体出发的最短路径）
- Shortest Path to High Value Targets（通往高价值目标的最短路径）
- Shortest Path to Unconstrained Delegation（通往非约束委派的最短路径）
```

### Kerberos

```
- Find Kerberoastable Accounts（查找可进行 Kerberoasting 的账户）
- Find AS-REP Roastable Users（查找可进行 AS-REP Roasting 的用户）
- Shortest Path from Kerberoastable Users（从可进行 Kerberoasting 的用户出发的最短路径）
- Find Principals with Unconstrained Delegation（查找配置了非约束委派的安全主体）
```

### ACL 分析

```
- Find Principals with DCSync Rights（查找具备 DCSync 权限的安全主体）
- Find Dangerous Rights for Domain Users（查找 Domain Users 的危险权限）
- Find Users with Foreign Domain Group Membership（查找属于其他域中组的用户）
```

### 计算机

```
- Find Computers with Unsupported OS（查找操作系统已不受支持的计算机）
- Find Computers where Domain Users are Local Admin（查找 Domain Users 拥有本地管理员权限的计算机）
- Find Computers with Sessions of High Value Users（查找存在高价值用户会话的计算机）
```

### GPO

```
- Find all GPO Owned by Owned Principals（查找由已控制安全主体拥有的所有 GPO）
- Find GPO Affecting High Value Targets（查找影响高价值目标的 GPO）
```

---

<a id="-custom-cypher-queries"></a>
## 📝 自定义 Cypher 查询

> **查询说明：** 下列 Cypher 的对象名、属性、关系和语法全部保留原文；实际结果取决于收集器、图谱模式与数据库版本。硬编码的英文组名和示例域名需与环境对应；`owned`／`highvalue` 也不能假定在所有 CE 版本中具有相同语义，可对照[官方标签迁移说明](https://specterops.io/blog/2026/04/15/whats-new-in-the-bloodhound-query-library-byol-opengraph-multi-server-and-more/)。无类型限制的路径查询可能包含[不可直接遍历利用的关系](https://specterops.io/blog/2025/08/01/attack-graph-model-design-requirements-and-examples/)，且 `SET` 会修改图数据库属性。[当前查询界面与查询库说明](https://bloodhound.specterops.io/analyze-data/explore/cypher-search)可用于核对可用查询。

### 基础查询

```cypher
// 查找所有用户
MATCH (u:User) RETURN u

// 查找所有计算机
MATCH (c:Computer) RETURN c

// 查找所有域管理员
MATCH (g:Group {name: "DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (u:User)-[:MemberOf*1..]->(g)
RETURN u.name

// 查找所有域控制器
MATCH (c:Computer)-[:MemberOf*1..]->(g:Group)
WHERE g.name CONTAINS "DOMAIN CONTROLLERS"
RETURN c.name
```

### 会话查询

```cypher
// 查找域管理员存在会话的计算机
MATCH (u:User)-[:MemberOf*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (c:Computer)-[:HasSession]->(u)
RETURN u.name, c.name

// 查找在多台计算机上存在会话的用户
MATCH (c:Computer)-[:HasSession]->(u:User)
WITH u, COUNT(c) as sessions
WHERE sessions > 5
RETURN u.name, sessions
ORDER BY sessions DESC
```

### 路径查询

```cypher
// 通往域管理员的最短路径
MATCH p=shortestPath(
  (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
  -[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p

// 通往域管理员的所有路径（最多返回 10 条）
MATCH p=(u:User)-[*1..5]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
RETURN p LIMIT 10

// 从已控制安全主体出发的路径
MATCH p=shortestPath(
  (u {owned:true})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p
```

### ACL 查询

```cypher
// 具备 DCSync 权限的用户
MATCH (u)-[:GetChanges]->(d:Domain)
MATCH (u)-[:GetChangesAll]->(d)
RETURN u.name

// 能够重置密码的用户
MATCH (u:User)-[:ForceChangePassword]->(t:User)
RETURN u.name, t.name

// 对计算机拥有 GenericAll 权限的用户
MATCH (u:User)-[:GenericAll]->(c:Computer)
RETURN u.name, c.name
```

### Kerberos 查询

```cypher
// 存在通往 DA 路径且可进行 Kerberoasting 的用户
MATCH (u:User {hasspn:true})
MATCH p=shortestPath((u)-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"}))
RETURN u.name, LENGTH(p)

// 可进行 AS-REP Roasting 的用户
MATCH (u:User {dontreqpreauth:true})
RETURN u.name

// 配置了非约束委派的计算机
MATCH (c:Computer {unconstraineddelegation:true})
RETURN c.name
```

### 高价值目标

```cypher
// 标记高价值目标
MATCH (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
SET u.highvalue = true

// 查找通往高价值目标的路径
MATCH p=shortestPath(
  (u:User)-[*1..]->(t {highvalue:true})
)
RETURN p
```

---

<a id="-attack-path-analysis"></a>
## 🎯 攻击路径分析

### 查找攻击路径

```
1. 搜索起始节点（已控制的用户）
2. 单击鼠标右键 → "Mark as Owned"（标记为已控制）
3. 运行查询："Shortest Path from Owned Principals"（从已控制安全主体出发的最短路径）
4. 分析路径上的关系边
```

### 常见攻击关系边

| 关系边 | 利用方式 |
|------|---------------|
| **MemberOf** | 组成员关系 |
| **AdminTo** | 本地管理员权限 |
| **HasSession** | 收集凭据 |
| **CanRDP** | RDP 访问 |
| **CanPSRemote** | PowerShell 远程管理 |
| **ExecuteDCOM** | DCOM 执行 |
| **GenericAll** | 完全控制权限 |
| **GenericWrite** | 修改对象 |
| **WriteOwner** | 更改所有者 |
| **WriteDacl** | 修改 ACL |
| **ForceChangePassword** | 重置密码 |
| **AddMember** | 添加到组 |
| **AllExtendedRights** | DCSync 等 |
| **GetChanges** | DCSync 所需权限之一 |
| **GetChangesAll** | DCSync 所需权限之二 |

### 利用攻击关系边

#### 用户对象上的 GenericAll

```powershell
# 重置密码
net user targetuser NewP@ssw0rd /domain

# 也可以使用 PowerView
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'NewP@ssw0rd' -AsPlainText -Force)
```

#### 计算机对象上的 GenericAll

```powershell
# 基于资源的约束委派（RBCD）攻击
# 使用 Rubeus + PowerMad
```

#### ForceChangePassword

```powershell
# 重置密码
net user targetuser NewP@ssw0rd /domain
```

#### AddMember

```powershell
# 将用户添加到组
net group "Domain Admins" attackeruser /add /domain

# 也可以使用 PowerView
Add-DomainGroupMember -Identity "Domain Admins" -Members "attackeruser"
```

#### WriteDacl

```powershell
# 添加 DCSync 权限
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attackeruser -Rights DCSync
```

---

<a id="-bloodhound-ce"></a>
## 🆕 BloodHound CE

### 关于 BloodHound CE

> **后端勘误：** 原文“不使用 Neo4j”的表述过于绝对。[官方自定义安装文档](https://bloodhound.specterops.io/get-started/custom-installation)说明 CE 可采用 PostgreSQL 或 Neo4j 图后端，具体取决于版本和配置；不能仅凭这份速查表判定后端。下面的短链接安装示例保留原文，现行推荐流程请参照官方快速入门。

**BloodHound Community Edition** 是新版 BloodHound：
- 现代化的网页界面
- PostgreSQL 后端（原文称不使用 Neo4j，参见上方说明）
- REST API
- 性能得到改进

### 安装

```bash
# Docker Compose（原文推荐）
curl -L https://ghst.ly/getbhce | docker compose -f - up

# 访问：http://localhost:8080
# 默认账户：admin /（密码请查看 Docker 日志）
```

### 适用于 CE 的 SharpHound

```powershell
# 下载与 CE 兼容的收集器
# 从 BloodHound CE Releases 获取

# 运行收集器（原文称语法相同，需核对版本）
.\SharpHound.exe -c All
```

---

<a id="-quick-reference"></a>
## 📊 速查表

### SharpHound 命令

| 命令 | 说明 |
|---------|-------------|
| `.\SharpHound.exe` | 默认收集 |
| `.\SharpHound.exe -c All` | 所有方式 |
| `.\SharpHound.exe -c DCOnly` | 仅域控制器（原文称隐蔽收集） |
| `.\SharpHound.exe -c Session --loop` | 循环收集会话 |
| `.\SharpHound.exe -d domain.local` | 指定域 |
| `.\SharpHound.exe --stealth` | 隐蔽模式（原文名称） |

### 常用查询

| 查询 | 用途 |
|-------|---------|
| Find all Domain Admins（查找所有域管理员） | 识别域管理员（DA）成员 |
| Shortest Path to DA（通往域管理员的最短路径） | 查找攻击路径 |
| Kerberoastable Users（可进行 Kerberoasting 的用户） | 查找可用于 Kerberoasting 的 SPN |
| AS-REP Roastable（可进行 AS-REP Roasting） | 无需 Kerberos 预身份验证的用户 |
| Unconstrained Delegation（非约束委派） | 委派滥用 |
| DCSync Rights（DCSync 权限） | 查找具备 DCSync 权限的安全主体 |

### 分析流程

```
1. 使用 SharpHound 收集数据
2. 将数据导入 BloodHound
3. 标记已控制的安全主体
4. 运行 "Shortest Path from Owned"（从已控制对象出发的最短路径）
5. 分析攻击关系边
6. 执行攻击
7. 标记新控制的安全主体
8. 重复上述流程
```

### 关键文件

```
SharpHound 输出：
- *_BloodHound.zip（JSON 数据）
- computers.json
- users.json
- groups.json
- domains.json
- sessions.json
- ous.json
- gpos.json
- containers.json
```

---

## 📚 参考资源

- [BloodHound GitHub](https://github.com/BloodHoundAD/BloodHound)
- [SharpHound](https://github.com/BloodHoundAD/SharpHound)
- [BloodHound 文档](https://bloodhound.readthedocs.io/)
- [bloodhound.py](https://github.com/fox-it/BloodHound.py)

### 相关速查表
- [Mimikatz](../../Mimikatz/translations/README.zh-CN.md)
- [PowerShell](../../PowerShell/translations/README.zh-CN.md)
- [Windows 权限提升](../../Windows-PrivEsc/translations/README.zh-CN.md)

---

<p align="center">
  <b>🩸 查找通往域管理员权限的路径！</b><br>
  <i>BloodHound：AD 渗透测试人员的实用伙伴</i>
</p>
