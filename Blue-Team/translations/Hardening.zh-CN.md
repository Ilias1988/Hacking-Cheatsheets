# 🔒 系统加固速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Hardening.tr.md) • [🇬🇷 Ελληνικά](Hardening.el.md)

> **译注：** 原文混合了命令、配置文件片段及不同 shell 的语法，示例按原样保留；不能将整个代码块作为脚本直接执行。直接设置 `LanguageMode` 仅适合实验，正式约束需要应用控制策略或会话配置，参见 [Microsoft 文档](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_language_modes)。`kernel.randomize_va_space = 2` 设置的是地址空间布局随机化（ASLR），并非原文所称的 exec shield，参见 [Linux 内核文档](https://docs.kernel.org/admin-guide/sysctl/kernel.html#randomize-va-space)。

```
██╗  ██╗ █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗██╗███╗   ██╗ ██████╗ 
██║  ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██║████╗  ██║██╔════╝ 
███████║███████║██████╔╝██║  ██║█████╗  ██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
██╔══██║██╔══██║██╔══██╗██║  ██║██╔══╝  ██║╚██╗██║██║██║╚██╗██║██║   ██║
██║  ██║██║  ██║██║  ██║██████╔╝███████╗██║ ╚████║██║██║ ╚████║╚██████╔╝
╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🖥️ Windows 系统加固

### 账户安全
```powershell
# 禁用本地管理员账户
net user Administrator /active:no

# 重命名管理员账户
wmic useraccount where name='Administrator' rename 'NewAdminName'

# 设置强密码策略（GPO 或 secpol.msc）
# 最小长度：14 个字符
# 复杂度：启用
# 最长有效期：60 天

# 禁用来宾账户
net user Guest /active:no

# 限制管理员令牌使用（UAC）
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v EnableLUA /t REG_DWORD /d 1 /f
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v ConsentPromptBehaviorAdmin /t REG_DWORD /d 2 /f
```

### 禁用不必要的服务
```powershell
# 禁用远程注册表
sc config RemoteRegistry start= disabled

# 禁用 Windows 远程管理（如不需要）
sc config WinRM start= disabled

# 禁用 SMBv1
Disable-WindowsOptionalFeature -Online -FeatureName SMB1Protocol -NoRestart

# 禁用 NetBIOS
Get-WmiObject Win32_NetworkAdapterConfiguration | where {$_.IPEnabled -eq $true} | 
    Invoke-WmiMethod -Name SetTcpipNetbios -ArgumentList 2
```

### 防火墙配置
```powershell
# 启用 Windows 防火墙
netsh advfirewall set allprofiles state on

# 默认阻止入站连接
netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound

# 启用日志记录
netsh advfirewall set allprofiles logging filename %SystemRoot%\System32\LogFiles\Firewall\pfirewall.log
netsh advfirewall set allprofiles logging maxfilesize 4096
netsh advfirewall set allprofiles logging droppedconnections enable
```

### PowerShell 安全
```powershell
# 启用受限语言模式
$ExecutionContext.SessionState.LanguageMode = "ConstrainedLanguage"

# 启用脚本块日志
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" /v EnableScriptBlockLogging /t REG_DWORD /d 1 /f

# 启用模块日志
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" /v EnableModuleLogging /t REG_DWORD /d 1 /f
```

### 审计策略
```powershell
# 在进程创建事件中记录命令行
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System\Audit" /v ProcessCreationIncludeCmdLine_Enabled /t REG_DWORD /d 1 /f

# 配置审计策略
auditpol /set /category:"Logon/Logoff" /success:enable /failure:enable
auditpol /set /category:"Account Logon" /success:enable /failure:enable
auditpol /set /category:"Account Management" /success:enable /failure:enable
auditpol /set /category:"Object Access" /success:enable /failure:enable
auditpol /set /category:"Privilege Use" /success:enable /failure:enable
```

### Windows Defender
```powershell
# 启用实时保护
Set-MpPreference -DisableRealtimeMonitoring $false

# 启用云保护
Set-MpPreference -MAPSReporting Advanced
Set-MpPreference -SubmitSamplesConsent SendAllSamples

# 启用攻击面减少（ASR）规则
Set-MpPreference -AttackSurfaceReductionRules_Ids 56a863a9-875e-4185-98a7-b882c64b5ce5 -AttackSurfaceReductionRules_Actions Enabled
```

### LSASS 保护
```powershell
# 启用 Credential Guard（原文标注需要 TPM）
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v LsaCfgFlags /t REG_DWORD /d 1 /f

# 将 LSASS 作为 PPL（受保护的轻量级进程）运行
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v RunAsPPL /t REG_DWORD /d 1 /f
```

---

## 🐧 Linux 系统加固

### 用户与访问控制
```bash
# 禁用 root 登录
sudo passwd -l root

# 设置密码复杂度 (/etc/security/pwquality.conf)
minlen = 14
dcredit = -1
ucredit = -1
lcredit = -1
ocredit = -1

# 密码有效期 (/etc/login.defs)
PASS_MAX_DAYS 90
PASS_MIN_DAYS 7
PASS_WARN_AGE 14

# 禁用未使用的账户
usermod -s /sbin/nologin username
```

### SSH 加固 (/etc/ssh/sshd_config)
```bash
# 禁用 root 登录
PermitRootLogin no

# 禁用密码认证（使用密钥）
PasswordAuthentication no
PubkeyAuthentication yes

# 更改默认端口
Port 2222

# 限制允许登录的用户
AllowUsers admin operator

# 仅使用协议版本 2
Protocol 2

# 禁止空密码
PermitEmptyPasswords no

# 超时设置
ClientAliveInterval 300
ClientAliveCountMax 2

# 重启 SSH
sudo systemctl restart sshd
```

### 防火墙（iptables/nftables）
```bash
# 默认拒绝策略
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -P OUTPUT ACCEPT

# 允许回环流量
iptables -A INPUT -i lo -j ACCEPT

# 允许已建立及相关连接
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

# 允许 SSH（自定义端口）
iptables -A INPUT -p tcp --dport 2222 -j ACCEPT

# 保存规则
iptables-save > /etc/iptables/rules.v4
```

### 文件权限
```bash
# 保护关键文件
chmod 644 /etc/passwd
chmod 000 /etc/shadow
chmod 644 /etc/group
chmod 600 /etc/ssh/sshd_config
chmod 700 /root

# 查找 SUID/SGID 文件
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null

# 移除不必要的 SUID 位
chmod u-s /usr/bin/unnecessary_binary
```

### 禁用未使用的服务
```bash
# 列出已启用的服务
systemctl list-unit-files --state=enabled

# 禁用不必要的服务
systemctl disable bluetooth
systemctl disable cups
systemctl disable avahi-daemon
systemctl disable rpcbind

# 如不需要则禁用 IPv6
echo "net.ipv6.conf.all.disable_ipv6 = 1" >> /etc/sysctl.conf
```

### 内核加固 (/etc/sysctl.conf)
```bash
# 禁用 IP 转发
net.ipv4.ip_forward = 0

# 禁用源路由
net.ipv4.conf.all.accept_source_route = 0
net.ipv4.conf.default.accept_source_route = 0

# 启用 SYN cookies
net.ipv4.tcp_syncookies = 1

# 禁用 ICMP 重定向
net.ipv4.conf.all.accept_redirects = 0
net.ipv4.conf.default.accept_redirects = 0
net.ipv4.conf.all.send_redirects = 0

# 启用 exec shield（原文表述；实际设置为 ASLR）
kernel.randomize_va_space = 2

# 应用更改
sysctl -p
```

### 审计系统（auditd）
```bash
# 安装 auditd
apt install auditd

# 规则示例 (/etc/audit/rules.d/audit.rules)
-w /etc/passwd -p wa -k passwd_changes
-w /etc/shadow -p wa -k shadow_changes
-w /etc/sudoers -p wa -k sudoers_changes
-a always,exit -F arch=b64 -S execve -k command_execution

# 重启 auditd
systemctl restart auditd
```

---

## 📊 加固快速检查清单

### Windows
```markdown
□ 禁用不必要的服务（SMBv1、远程注册表）
□ 启用 Windows 防火墙
□ 配置强密码策略
□ 启用 PowerShell 日志
□ 配置审计策略
□ 启用 Windows Defender
□ 启用 LSASS 保护
□ 安装安全更新
□ 移除未使用的软件
```

### Linux
```markdown
□ 禁用 root 登录
□ 配置 SSH 加固
□ 启用防火墙（iptables/ufw）
□ 设置合适的文件权限
□ 禁用未使用的服务
□ 配置内核加固
□ 启用 auditd
□ 安装安全更新
□ 配置 fail2ban
```

---

## 📚 参考资源

- [CIS 安全基准](https://www.cisecurity.org/cis-benchmarks/)
- [NIST 指南](https://www.nist.gov/cyberframework)
- [Microsoft 安全基线](https://docs.microsoft.com/en-us/windows/security/threat-protection/windows-security-baselines)

---

## 🔗 相关速查表

- [事件响应](./Incident-Response.zh-CN.md)
- 网络防御（原仓库尚未提供 `Network-Defense.md`）

---

**上一篇:** ← 网络防御（原仓库尚未提供 `Network-Defense.md`）

**返回概览:** [🛡️ 蓝队](./README.zh-CN.md)
