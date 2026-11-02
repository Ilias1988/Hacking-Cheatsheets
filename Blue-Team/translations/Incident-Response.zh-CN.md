# 🚨 事件响应速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Incident-Response.tr.md) • [🇬🇷 Ελληνικά](Incident-Response.el.md)

> **译注：** 本文保留原文的六步教学流程；它将传统响应阶段进一步拆分，并非当前 NIST 文档的逐字阶段列表。2025 年发布的 [NIST SP 800-61 Rev. 3](https://csrc.nist.gov/pubs/sp/800/61/r3/final) 已取代 Rev. 2。原文的 `2024` 日期是示例值，响应时限是示例分级，不是统一的 NIST 要求。

```
  ██╗███╗   ██╗ ██████╗██╗██████╗ ███████╗███╗   ██╗████████╗
  ██║████╗  ██║██╔════╝██║██╔══██╗██╔════╝████╗  ██║╚══██╔══╝
  ██║██╔██╗ ██║██║     ██║██║  ██║█████╗  ██╔██╗ ██║   ██║   
  ██║██║╚██╗██║██║     ██║██║  ██║██╔══╝  ██║╚██╗██║   ██║   
  ██║██║ ╚████║╚██████╗██║██████╔╝███████╗██║ ╚████║   ██║   
  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝   
        ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗███████╗███████╗
        ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔════╝██╔════╝
        ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║███████╗█████╗  
        ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║╚════██║██╔══╝  
        ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║███████║███████╗
        ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚══════╝
```

---

## 📊 事件响应生命周期（NIST）

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│ 1.准备       │────▶│ 2.检测       │────▶│3.遏制        │────▶│4.根除        │
│              │     │ 与分析       │     │              │     │              │
└──────────────┘     └──────────────┘     └──────────────┘     └──────────────┘
       ▲                                                              │
       │         ┌──────────────┐     ┌──────────────┐               │
       └─────────│ 6.经验       │◀────│ 5.恢复       │◀──────────────┘
                 │   总结       │     │              │
                 └──────────────┘     └──────────────┘
```

---

## 1️⃣ 准备

### 文档检查清单
```markdown
- [ ] 已编写事件响应计划
- [ ] 联系名单（内部与外部）
- [ ] 沟通计划
- [ ] 资产清单
- [ ] 网络拓扑图
- [ ] 基线配置
- [ ] 备份验证
```

### 事件响应（IR）团队联系信息模板
```
事件响应负责人：          [姓名] - [电话] - [邮箱]
IT 安全：      [姓名] - [电话] - [邮箱]
法务：            [姓名] - [电话] - [邮箱]
沟通负责人：   [姓名] - [电话] - [邮箱]
人力资源：               [姓名] - [电话] - [邮箱]
外部取证团队： [公司] - [电话]
执法机构：  [机构] - [电话]
```

### 事件响应必备工具
```bash
# 取证采集
- FTK Imager
- Volatility
- Velociraptor
- KAPE

# 网络分析
- Wireshark
- tcpdump
- Zeek

# 日志分析
- Splunk/ELK
- grep/awk/sed
```

---

## 2️⃣ 检测与分析

### 初步研判问题
```markdown
1. 这是什么类型的事件？
   - [ ] 恶意软件感染
   - [ ] 未授权访问
   - [ ] 数据泄露
   - [ ] 拒绝服务
   - [ ] 内部威胁
   - [ ] 网络钓鱼

2. 何时发现？
3. 如何发现？
4. 哪些系统受到影响？
5. 哪些数据可能受到影响？
6. 事件是否仍在持续？
```

### Windows 快速研判
```cmd
# 当前用户会话
query user
qwinsta

# 活动连接
netstat -ano | findstr ESTABLISHED

# 正在运行的进程
tasklist /v
wmic process get processid,parentprocessid,commandline

# 最近修改的文件
forfiles /P C:\ /S /D +01/01/2024

# 计划任务
schtasks /query /fo LIST /v

# 服务
sc query state=all
wmic service get name,startmode,state

# 注册表 Run 项
reg query "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"
reg query "HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"

# 最近的 PowerShell 历史记录
type %APPDATA%\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt
```

### Linux 快速研判
```bash
# 查看当前登录用户
w
who
last -a

# 活动连接
ss -tulnp
netstat -tulnp
lsof -i

# 正在运行的进程
ps auxwww
pstree -p

# 最近修改的文件
find / -mtime -1 -type f 2>/dev/null

# Cron 定时任务
cat /etc/crontab
ls -la /etc/cron.*
for user in $(cut -f1 -d: /etc/passwd); do crontab -u $user -l 2>/dev/null; done

# 启动脚本
ls -la /etc/init.d/
systemctl list-unit-files --type=service

# 历史记录
cat ~/.bash_history
cat /root/.bash_history
```

### 网络分析
```bash
# 捕获流量
tcpdump -i eth0 -w capture.pcap

# 筛选指定主机
tcpdump -i eth0 host 192.168.1.100

# HTTP 流量
tcpdump -i eth0 port 80 or port 443

# DNS 查询
tcpdump -i eth0 port 53
```

---

## 3️⃣ 遏制

### 短期遏制
```bash
# 隔离网络（Windows）
netsh advfirewall set allprofiles state on
netsh advfirewall firewall add rule name="Block All" dir=in action=block
netsh advfirewall firewall add rule name="Block All Out" dir=out action=block

# 隔离网络（Linux）
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

# 禁用失陷账户
net user compromised_user /active:no   # Windows
passwd -l compromised_user             # Linux

# 终止恶意进程
taskkill /PID <pid> /F               # Windows
kill -9 <pid>                        # Linux
```

### 长期遏制
```bash
# 划分网络区域
# 应用防火墙规则以隔离受影响的 VLAN
# 更新交换机和路由器上的 ACL

# 在防火墙上阻止恶意 IP 与域名
# 加入代理黑名单
# 更新 DNS 黑洞规则

# 临时禁用受影响的服务
sc config <service> start= disabled   # Windows
systemctl disable <service>           # Linux
```

### 证据保全
```bash
# 内存转储（Windows，使用 WinPMEM 或 DumpIt）
winpmem_mini_x64.exe memory.raw

# 内存转储（Linux）
dd if=/dev/mem of=/evidence/memory.dump bs=1M

# 磁盘镜像
dd if=/dev/sda of=/evidence/disk.img bs=4M status=progress

# 计算哈希以验证完整性
sha256sum /evidence/disk.img > disk.img.sha256

# 优先采集易失性数据（按易失性顺序）：
# 1. 内存
# 2. 网络状态
# 3. 正在运行的进程
# 4. 磁盘
```

---

## 4️⃣ 根除

### 清除恶意软件
```bash
# 识别并清除持久化机制

# Windows — 注册表
reg delete "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v "MalwareName" /f

# Windows — 计划任务
schtasks /delete /tn "MalwareTask" /f

# Windows — 服务
sc delete "MalwareService"

# Linux — Cron
crontab -r
rm /etc/cron.d/malware

# Linux — systemd
systemctl disable malware
rm /etc/systemd/system/malware.service
```

### 密码重置
```bash
# 强制重置所有失陷账户的密码
# 重置服务账户密码
# 轮换 API 密钥与令牌
# 使现有会话失效

# Windows — 强制更改密码
net user username /logonpasswordchg:yes

# 重置 Kerberos 票据（如果 AD 已失陷）
# - 重置 krbtgt 密码两次
# - 重置所有管理员账户密码
```

### 修补与更新
```bash
# 安装缺失的安全补丁
# 更新防病毒特征库
# 修复导致攻击得逞的配置错误
```

---

## 5️⃣ 恢复

### 系统恢复
```bash
# 从干净备份恢复
# 重建失陷系统
# 上线前验证完整性

# 逐步恢复：
1. 优先恢复关键系统
2. 监控再次感染的迹象
3. 恢复其他系统
4. 恢复正常运营
```

### 恢复后的监控
```bash
# 增加日志记录的详细程度
# 部署额外监控
# 关注攻击者是否再次进入

# 需要监控的关键指标：
- 相同的 IOC（IP、域名、哈希值）
- 相似的战术、技术与程序（TTP）
- 尝试访问相同账户
- 与 C2 基础设施通信
```

---

## 6️⃣ 经验总结

### 事件复盘报告模板
```markdown
## 事件摘要
- 事件编号：
- 发现日期：
- 遏制日期：
- 解决日期：
- 事件类型：
- 严重程度：

## 时间线
| 日期/时间 | 事件 |
|-----------|-------|
| YYYY-MM-DD HH:MM | 首次发现 |
| ... | ... |

## 影响评估
- 受影响的系统：
- 失陷数据：
- 业务影响：
- 财务影响：

## 根本原因分析
- 攻击者如何取得访问权限？
- 利用了哪些漏洞？
- 哪些控制措施失效？

## 已采取的措施
1. 检测
2. 遏制
3. 根除
4. 恢复

## 改进建议
1. 立即采取的改进
2. 长期安全增强措施
3. 策略调整

## 经验总结
- 哪些措施有效？
- 哪些方面需要改进？
- 发现了哪些培训需求？
```

---

## 📊 快速参考

### 严重程度分级

| 等级 | 说明 | 响应时间 |
|-------|-------------|---------------|
| **严重** | 正在发生的入侵或数据外传 | 立即 |
| **高** | 已确认失陷 | < 1 小时 |
| **中** | 可能失陷 | < 4 小时 |
| **低** | 可疑活动 | < 24 小时 |

### 需收集的常见失陷指标（IOC）

| IOC 类型 | 示例 |
|----------|----------|
| **哈希** | MD5, SHA1, SHA256 |
| **IP** | 源 IP 与目标 IP |
| **域名** | 命令与控制（C2）域名 |
| **URL** | 恶意 URL |
| **电子邮件** | 钓鱼邮件发件人 |
| **文件** | 恶意软件文件名 |
| **注册表** | 持久化注册表项 |

---

## 🔗 相关速查表

- [日志分析](./Log-Analysis.zh-CN.md)
- [SIEM 检测](./SIEM-Detection.zh-CN.md)
- [Volatility（数字取证）](../../Volatility/README.md)

---

**下一篇:** [日志分析 →](./Log-Analysis.zh-CN.md)
