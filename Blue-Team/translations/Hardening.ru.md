# Справочник по укреплению защиты систем

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Hardening.tr.md) • [🇬🇷 Ελληνικά](Hardening.el.md)

```
██╗  ██╗ █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗██╗███╗   ██╗ ██████╗ 
██║  ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██║████╗  ██║██╔════╝ 
███████║███████║██████╔╝██║  ██║█████╗  ██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
██╔══██║██╔══██║██╔══██╗██║  ██║██╔══╝  ██║╚██╗██║██║██║╚██╗██║██║   ██║
██║  ██║██║  ██║██║  ██║██████╔╝███████╗██║ ╚████║██║██║ ╚████║╚██████╔╝
╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## Укрепление защиты Windows

### Безопасность учётных записей
```powershell
# Отключить локальную учётную запись администратора
net user Administrator /active:no

# Переименовать учётную запись администратора
wmic useraccount where name='Administrator' rename 'NewAdminName'

# Задать строгую политику паролей (GPO или secpol.msc)
# Минимальная длина: 14 символов
# Требования к сложности: включены
# Максимальный срок действия: 60 дней

# Отключить гостевую учётную запись
net user Guest /active:no

# Ограничить использование токена администратора (UAC)
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v EnableLUA /t REG_DWORD /d 1 /f
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v ConsentPromptBehaviorAdmin /t REG_DWORD /d 2 /f
```

### Отключение ненужных служб
```powershell
# Отключить удалённый реестр
sc config RemoteRegistry start= disabled

# Отключить Windows Remote Management, если он не нужен
sc config WinRM start= disabled

# Отключить SMBv1
Disable-WindowsOptionalFeature -Online -FeatureName SMB1Protocol -NoRestart

# Отключить NetBIOS
Get-WmiObject Win32_NetworkAdapterConfiguration | where {$_.IPEnabled -eq $true} | 
    Invoke-WmiMethod -Name SetTcpipNetbios -ArgumentList 2
```

### Настройка межсетевого экрана
```powershell
# Включить межсетевой экран Windows
netsh advfirewall set allprofiles state on

# По умолчанию блокировать входящие соединения
netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound

# Включить журналирование
netsh advfirewall set allprofiles logging filename %SystemRoot%\System32\LogFiles\Firewall\pfirewall.log
netsh advfirewall set allprofiles logging maxfilesize 4096
netsh advfirewall set allprofiles logging droppedconnections enable
```

### Безопасность PowerShell
```powershell
# Включить ограниченный языковой режим (Constrained Language Mode)
$ExecutionContext.SessionState.LanguageMode = "ConstrainedLanguage"

# Включить журналирование блоков сценариев (Script Block Logging)
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" /v EnableScriptBlockLogging /t REG_DWORD /d 1 /f

# Включить журналирование модулей
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" /v EnableModuleLogging /t REG_DWORD /d 1 /f
```

### Политика аудита
```powershell
# Включить запись командной строки при создании процесса
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System\Audit" /v ProcessCreationIncludeCmdLine_Enabled /t REG_DWORD /d 1 /f

# Настроить политики аудита
auditpol /set /category:"Logon/Logoff" /success:enable /failure:enable
auditpol /set /category:"Account Logon" /success:enable /failure:enable
auditpol /set /category:"Account Management" /success:enable /failure:enable
auditpol /set /category:"Object Access" /success:enable /failure:enable
auditpol /set /category:"Privilege Use" /success:enable /failure:enable
```

### Windows Defender
```powershell
# Включить защиту в реальном времени
Set-MpPreference -DisableRealtimeMonitoring $false

# Включить облачную защиту
Set-MpPreference -MAPSReporting Advanced
Set-MpPreference -SubmitSamplesConsent SendAllSamples

# Включить правила сокращения поверхности атаки (Attack Surface Reduction)
Set-MpPreference -AttackSurfaceReductionRules_Ids 56a863a9-875e-4185-98a7-b882c64b5ce5 -AttackSurfaceReductionRules_Actions Enabled
```

### Защита LSASS
```powershell
# Включить Credential Guard (в оригинале указан TPM)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v LsaCfgFlags /t REG_DWORD /d 1 /f

# Запускать LSASS как защищённый процесс PPL (Protected Process Light)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v RunAsPPL /t REG_DWORD /d 1 /f
```

---

## Укрепление защиты Linux

### Пользователи и управление доступом
```bash
# Запретить вход под root
sudo passwd -l root

# Настроить сложность паролей (/etc/security/pwquality.conf)
minlen = 14
dcredit = -1
ucredit = -1
lcredit = -1
ocredit = -1

# Сроки действия паролей (/etc/login.defs)
PASS_MAX_DAYS 90
PASS_MIN_DAYS 7
PASS_WARN_AGE 14

# Отключить неиспользуемые учётные записи
usermod -s /sbin/nologin username
```

### Укрепление защиты SSH (/etc/ssh/sshd_config)
```bash
# Запретить вход под root
PermitRootLogin no

# Отключить аутентификацию по паролю (использовать ключи)
PasswordAuthentication no
PubkeyAuthentication yes

# Изменить порт по умолчанию
Port 2222

# Ограничить список пользователей
AllowUsers admin operator

# Разрешить только протокол 2
Protocol 2

# Запретить пустые пароли
PermitEmptyPasswords no

# Параметры тайм-аутов
ClientAliveInterval 300
ClientAliveCountMax 2

# Перезапустить SSH
sudo systemctl restart sshd
```

### Межсетевой экран (iptables/nftables)
```bash
# Политика запрета по умолчанию
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -P OUTPUT ACCEPT

# Разрешить трафик loopback
iptables -A INPUT -i lo -j ACCEPT

# Разрешить установленные соединения
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

# Разрешить SSH на нестандартном порту
iptables -A INPUT -p tcp --dport 2222 -j ACCEPT

# Сохранить правила
iptables-save > /etc/iptables/rules.v4
```

### Права доступа к файлам
```bash
# Защитить критически важные файлы
chmod 644 /etc/passwd
chmod 000 /etc/shadow
chmod 644 /etc/group
chmod 600 /etc/ssh/sshd_config
chmod 700 /root

# Найти файлы с SUID/SGID
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null

# Убрать ненужный бит SUID
chmod u-s /usr/bin/unnecessary_binary
```

### Отключение неиспользуемых служб
```bash
# Вывести включённые службы
systemctl list-unit-files --state=enabled

# Отключить ненужные службы
systemctl disable bluetooth
systemctl disable cups
systemctl disable avahi-daemon
systemctl disable rpcbind

# Отключить IPv6, если он не нужен
echo "net.ipv6.conf.all.disable_ipv6 = 1" >> /etc/sysctl.conf
```

### Укрепление защиты ядра (/etc/sysctl.conf)
```bash
# Отключить IP-переадресацию
net.ipv4.ip_forward = 0

# Отключить маршрутизацию от источника
net.ipv4.conf.all.accept_source_route = 0
net.ipv4.conf.default.accept_source_route = 0

# Включить SYN cookies
net.ipv4.tcp_syncookies = 1

# Отключить ICMP redirects
net.ipv4.conf.all.accept_redirects = 0
net.ipv4.conf.default.accept_redirects = 0
net.ipv4.conf.all.send_redirects = 0

# Включить exec shield (формулировка оригинала)
kernel.randomize_va_space = 2

# Применить изменения
sysctl -p
```

### Система аудита (auditd)
```bash
# Установить auditd
apt install auditd

# Примеры правил (/etc/audit/rules.d/audit.rules)
-w /etc/passwd -p wa -k passwd_changes
-w /etc/shadow -p wa -k shadow_changes
-w /etc/sudoers -p wa -k sudoers_changes
-a always,exit -F arch=b64 -S execve -k command_execution

# Перезапустить auditd
systemctl restart auditd
```

---

## Краткий контрольный список укрепления защиты

### Windows
```markdown
□ Отключить ненужные службы (SMBv1, Remote Registry)
□ Включить межсетевой экран Windows
□ Настроить строгую политику паролей
□ Включить журналирование PowerShell
□ Настроить политику аудита
□ Включить Windows Defender
□ Включить защиту LSASS
□ Установить обновления безопасности
□ Удалить неиспользуемое ПО
```

### Linux
```markdown
□ Запретить вход под root
□ Усилить защиту SSH
□ Включить межсетевой экран (iptables/ufw)
□ Настроить корректные права доступа к файлам
□ Отключить неиспользуемые службы
□ Усилить защиту ядра
□ Включить auditd
□ Установить обновления безопасности
□ Настроить fail2ban
```

---

## Ресурсы

- [Эталонные конфигурации CIS](https://www.cisecurity.org/cis-benchmarks/)
- [Рекомендации NIST](https://www.nist.gov/cyberframework)
- [Базовые конфигурации безопасности Microsoft](https://docs.microsoft.com/en-us/windows/security/threat-protection/windows-security-baselines)

---

## Связанные справочники

- [Реагирование на инциденты](Incident-Response.ru.md)
- Защита сети (исходный файл отсутствует; см. примечание в обзоре)

---

**Предыдущий раздел:** ← Защита сети (исходный файл отсутствует; см. примечание в обзоре)

**К обзору:** [🛡️ Blue Team](README.ru.md)
