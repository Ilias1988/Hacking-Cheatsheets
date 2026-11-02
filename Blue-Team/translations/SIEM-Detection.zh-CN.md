# 📈 SIEM 检测速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SIEM-Detection.tr.md) • [🇬🇷 Ελληνικά](SIEM-Detection.el.md)

> **译注：** 以下为检测思路与查询示例，索引名和字段映射需匹配实际数据源。登录失败记录通常不能证明多个账户使用了“相同密码”；原文相应查询通过来源、时间窗和账户数量识别可疑模式。4672 表示授予特殊权限，单独出现并不证明令牌操纵。

```
  ███████╗██╗███████╗███╗   ███╗    ██████╗ ███████╗████████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝██║██╔════╝████╗ ████║    ██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
  ███████╗██║█████╗  ██╔████╔██║    ██║  ██║█████╗     ██║   █████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
  ╚════██║██║██╔══╝  ██║╚██╔╝██║    ██║  ██║██╔══╝     ██║   ██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
  ███████║██║███████╗██║ ╚═╝ ██║    ██████╔╝███████╗   ██║   ███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝╚══════╝╚═╝     ╚═╝    ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## 🔶 Splunk 查询（SPL）

### 身份验证与访问

#### 暴力破解检测
```spl
# 同一 IP 的多次登录失败
index=windows EventCode=4625
| stats count by src_ip, user
| where count > 10
| sort -count

# 失败后登录成功（可能发生凭据失陷）
index=windows (EventCode=4625 OR EventCode=4624)
| transaction user maxspan=10m
| where eventcount > 5 AND match(EventCode, "4624")
| table _time, user, src_ip, EventCode
```

#### 密码喷洒检测
```spl
# 针对多个账户尝试同一密码
index=windows EventCode=4625
| stats dc(user) as unique_users, count by src_ip
| where unique_users > 5 AND count > 10
| sort -unique_users

# 短时间内同一 IP 访问多个用户账户
index=windows EventCode=4625
| bucket _time span=5m
| stats dc(user) as unique_users, values(user) as users by _time, src_ip
| where unique_users > 5
```

#### 管理员成功登录
```spl
# 管理员账户登录
index=windows EventCode=4624 LogonType=10
| search user IN ("Administrator", "admin", "*admin*")
| table _time, user, src_ip, dest

# 工作时间以外的登录
index=windows EventCode=4624
| eval hour=strftime(_time, "%H")
| where hour < 7 OR hour > 19
| stats count by user, src_ip
```

### 进程与执行

#### 可疑进程创建
```spl
# PowerShell 编码命令
index=windows EventCode=4688 
| search CommandLine="*-enc*" OR CommandLine="*-encoded*" OR CommandLine="*FromBase64*"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# 可疑的父子进程关系
index=windows EventCode=4688
| search ParentProcessName="*excel.exe" OR ParentProcessName="*word.exe" OR ParentProcessName="*outlook.exe"
| search NewProcessName="*cmd.exe" OR NewProcessName="*powershell.exe" OR NewProcessName="*wscript.exe"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# LOLBAS 执行
index=windows EventCode=4688
| search NewProcessName IN ("*certutil.exe", "*mshta.exe", "*regsvr32.exe", "*rundll32.exe", "*bitsadmin.exe")
| table _time, user, NewProcessName, CommandLine
```

#### Mimikatz 检测
```spl
# 进程中的 Mimikatz 关键词
index=windows EventCode=4688
| search CommandLine="*sekurlsa*" OR CommandLine="*kerberos::*" OR CommandLine="*lsadump*"
| table _time, user, NewProcessName, CommandLine

# LSASS 访问（Sysmon 事件 10）
index=sysmon EventCode=10 TargetImage="*lsass.exe"
| table _time, SourceImage, SourceUser, GrantedAccess
```

### 持久化机制

#### 创建计划任务
```spl
# 新增计划任务
index=windows EventCode=4698
| table _time, user, TaskName, TaskContent

# 可疑的计划任务名称
index=windows EventCode=4698
| rex field=TaskContent "<Command>(?<Command>[^<]+)</Command>"
| table _time, user, TaskName, Command
```

#### 安装服务
```spl
# 新安装的服务
index=windows EventCode=7045
| table _time, ServiceName, ServiceFileName, ServiceType, StartType

# 可疑服务路径
index=windows EventCode=7045
| search ServiceFileName="*temp*" OR ServiceFileName="*appdata*" OR ServiceFileName="*.ps1*"
| table _time, ServiceName, ServiceFileName
```

#### 注册表修改
```spl
# Run 项修改（Sysmon 事件 12–14）
index=sysmon EventCode IN (12, 13, 14)
| search TargetObject="*\\Run\\*" OR TargetObject="*\\RunOnce\\*"
| table _time, EventCode, Image, TargetObject, Details
```

### 横向移动

#### RDP 活动
```spl
# RDP 连接（类型 10）
index=windows EventCode=4624 LogonType=10
| stats count by src_ip, dest, user
| sort -count

# 同一来源通过 RDP 连接多台主机
index=windows EventCode=4624 LogonType=10
| stats dc(dest) as unique_hosts, values(dest) as hosts by src_ip, user
| where unique_hosts > 3
```

#### PSExec/WMI 检测
```spl
# 类似 PSExec 的活动
index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"
| table _time, src_ip, dest, ServiceFileName

# WMI 远程执行
index=windows EventCode=4688 
| search ParentProcessName="*WmiPrvSE.exe"
| table _time, user, dest, NewProcessName, CommandLine
```

### 数据外传

#### 大量数据传输
```spl
# 大量出站流量
index=firewall action=allowed direction=outbound
| stats sum(bytes_out) as total_bytes by src_ip, dest_ip
| where total_bytes > 100000000
| eval MB=round(total_bytes/1024/1024,2)
| table src_ip, dest_ip, MB

# DNS 数据外传（超长查询）
index=dns
| eval query_length=len(query)
| where query_length > 50
| stats count by src_ip, query
```

---

## 🟢 Elasticsearch/Kibana（KQL 与 Lucene）

### 身份验证与访问

#### 暴力破解检测
```kql
# Windows 登录失败
event.code: 4625 AND winlog.event_data.LogonType: 3

# 多次登录失败（聚合）
event.code: 4625
# 然后使用可视化按 source.ip 聚合

# 多次失败后的成功登录
event.code: (4625 OR 4624)
```

#### 密码喷洒
```kql
# 同一 IP 针对多个用户的身份验证失败
event.code: 4625 AND source.ip: *
# 按 source.ip 聚合，统计不同 user.name 的数量
```

### 进程与执行

#### 可疑进程
```kql
# 经过编码的 PowerShell
event.code: 4688 AND process.command_line: (*-enc* OR *-encoded* OR *FromBase64*)

# LOLBAS
event.code: 4688 AND process.name: (certutil.exe OR mshta.exe OR regsvr32.exe OR rundll32.exe)

# Office 启动 shell
event.code: 4688 AND process.parent.name: (WINWORD.EXE OR EXCEL.EXE OR OUTLOOK.EXE) AND process.name: (cmd.exe OR powershell.exe)
```

#### Mimikatz 检测
```kql
# 包含 mimikatz 关键词的进程
process.command_line: (*sekurlsa* OR *kerberos::* OR *lsadump* OR *mimikatz*)

# LSASS 访问
event.code: 10 AND winlog.event_data.TargetImage: *lsass.exe
```

### 持久化

#### 计划任务
```kql
# 创建任务
event.code: 4698

# 可疑任务名称
event.code: 4698 AND winlog.event_data.TaskName: (*update* OR *sync* OR *helper*)
```

#### 服务
```kql
# 安装服务
event.code: 7045

# 可疑服务路径
event.code: 7045 AND winlog.event_data.ImagePath: (*temp* OR *appdata* OR *.ps1)
```

### 横向移动

#### RDP
```kql
# RDP 登录
event.code: 4624 AND winlog.event_data.LogonType: 10

# 来自异常来源的 RDP
event.code: 4624 AND winlog.event_data.LogonType: 10 AND NOT source.ip: 192.168.*
```

#### PsExec
```kql
# PsExec 服务
event.code: 7045 AND winlog.event_data.ServiceFileName: *PSEXESVC*
```

### 实用聚合（Elasticsearch DSL）
```json
{
  "aggs": {
    "failed_logins_by_ip": {
      "terms": {
        "field": "source.ip",
        "size": 10
      },
      "aggs": {
        "unique_users": {
          "cardinality": {
            "field": "user.name"
          }
        }
      }
    }
  },
  "query": {
    "bool": {
      "must": [
        { "match": { "event.code": 4625 }}
      ]
    }
  }
}
```

---

## 📊 按 MITRE ATT&CK 分类的检测

### 初始访问 (TA0001)
| 技术 | Splunk 查询 |
|-----------|--------------|
| 网络钓鱼 | `index=email \| search subject="*urgent*" OR attachment="*.exe"` |
| 有效账户 | `index=windows EventCode=4624 LogonType=10 user!="SYSTEM"` |

### 执行 (TA0002)
| 技术 | Splunk 查询 |
|-----------|--------------|
| PowerShell | `index=windows EventCode=4688 NewProcessName="*powershell*"` |
| 脚本执行 | `index=windows EventCode=4688 NewProcessName IN ("*wscript*", "*cscript*")` |

### 持久化 (TA0003)
| 技术 | Splunk 查询 |
|-----------|--------------|
| 计划任务 | `index=windows EventCode=4698` |
| 注册表 Run 项 | `index=sysmon EventCode=13 TargetObject="*\\Run\\*"` |
| 服务 | `index=windows EventCode=7045` |

### 权限提升 (TA0004)
| 技术 | Splunk 查询 |
|-----------|--------------|
| 令牌操纵 | `index=windows EventCode=4672 NOT user="SYSTEM"` |
| 绕过 UAC | `index=sysmon EventCode=1 Image="*fodhelper*"` |

### 防御规避 (TA0005)
| 技术 | Splunk 查询 |
|-----------|--------------|
| 清除日志 | `index=windows EventCode=1102` |
| 伪装 | `index=sysmon EventCode=1 Image="*temp*\\svchost.exe"` |

### 凭据访问 (TA0006)
| 技术 | Splunk 查询 |
|-----------|--------------|
| LSASS 转储 | `index=sysmon EventCode=10 TargetImage="*lsass.exe"` |
| 凭据转储 | `index=windows EventCode=4688 CommandLine="*sekurlsa*"` |

### 横向移动 (TA0008)
| 技术 | Splunk 查询 |
|-----------|--------------|
| RDP | `index=windows EventCode=4624 LogonType=10` |
| PSExec | `index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"` |
| WMI | `index=windows EventCode=4688 ParentProcessName="*WmiPrvSE*"` |

### 数据外传 (TA0010)
| 技术 | Splunk 查询 |
|-----------|--------------|
| DNS 隧道 | `index=dns \| eval len=len(query) \| where len>50` |
| Web | `index=proxy bytes_out>10000000` |

---

## 🔗 相关速查表

- [日志分析](./Log-Analysis.zh-CN.md)
- [Sigma 规则](./Sigma-Rules.zh-CN.md)
- [威胁狩猎](./Threat-Hunting.zh-CN.md)

---

**上一篇:** [← 日志分析](./Log-Analysis.zh-CN.md)

**下一篇:** [威胁狩猎 →](./Threat-Hunting.zh-CN.md)
