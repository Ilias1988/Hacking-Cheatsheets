# 🔒 Sistem Sıkılaştırma Kopya Kağıdı (System Hardening Cheatsheet)

```
██╗  ██╗ █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗██╗███╗   ██╗ ██████╗ 
██║  ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██║████╗  ██║██╔════╝ 
███████║███████║██████╔╝██║  ██║█████╗  ██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
██╔══██║██╔══██║██╔══██╗██║  ██║██╔══╝  ██║╚██╗██║██║██║╚██╗██║██║   ██║
██║  ██║██║  ██║██║  ██║██████╔╝███████╗██║ ╚████║██║██║ ╚████║╚██████╔╝
╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🖥️ Windows Sistem Sıkılaştırma

### Hesap Güvenliği
```powershell
# Yerel Administrator hesabını devre dışı bırakma
net user Administrator /active:no

# Administrator hesabının adını değiştirme
wmic useraccount where name='Administrator' rename 'YeniYoneticiAdi'

# Guest (Konuk) hesabını devre dışı bırakma
net user Guest /active:no

# UAC (Kullanıcı Hesabı Denetimi) korumasını en üst seviyeye alma
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v EnableLUA /t REG_DWORD /d 1 /f
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v ConsentPromptBehaviorAdmin /t REG_DWORD /d 2 /f
```

### Gereksiz Servisleri Kapatma
```powershell
# Uzak Kayıt Defterini (Remote Registry) devre dışı bırakma
sc config RemoteRegistry start= disabled

# SMBv1 protokolünü kaldırma
Disable-WindowsOptionalFeature -Online -FeatureName SMB1Protocol -NoRestart

# NetBIOS protokolünü kapatma
Get-WmiObject Win32_NetworkAdapterConfiguration | where {$_.IPEnabled -eq $true} | 
    Invoke-WmiMethod -Name SetTcpipNetbios -ArgumentList 2
```

### Güvenlik Duvarı Yapılandırması
```powershell
# Windows Güvenlik Duvarını tüm profillerde açma
netsh advfirewall set allprofiles state on

# Gelen bağlantıları varsayılan olarak engelleme
netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound

# Engellenen bağlantıları günlüğe kaydetme
netsh advfirewall set allprofiles logging droppedconnections enable
```

### PowerShell Güvenliği ve Günlükleme
```powershell
# Betik Bloğu Günlüklemeyi (Script Block Logging) etkinleştirme
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" /v EnableScriptBlockLogging /t REG_DWORD /d 1 /f

# Modül Günlüklemeyi etkinleştirme
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" /v EnableModuleLogging /t REG_DWORD /d 1 /f
```

### LSASS ve Kimlik Bilgisi Koruması
```powershell
# LSASS sürecini korumalı süreç (PPL) olarak çalıştırma
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v RunAsPPL /t REG_DWORD /d 1 /f

# Credential Guard özelliğini etkinleştirme
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v LsaCfgFlags /t REG_DWORD /d 1 /f
```

---

## 🐧 Linux Sistem Sıkılaştırma

### Kullanıcı ve Parola Güvenliği
```bash
# root doğrudan girişini kilitleme
sudo passwd -l root

# Parola karmaşıklık kuralları (/etc/security/pwquality.conf)
minlen = 14
dcredit = -1
ucredit = -1
lcredit = -1
ocredit = -1

# Kullanılmayan hesapların kabuğunu kapatma
usermod -s /sbin/nologin kullanici_adi
```

### SSH Sıkılaştırma (/etc/ssh/sshd_config)
```bash
# Doğrudan root girişini engelleme
PermitRootLogin no

# Parola yerine anahtar tabanlı kimlik doğrulamayı zorunlu kılma
PasswordAuthentication no
PubkeyAuthentication yes

# Varsayılan portu değiştirme
Port 2222

# Yalnızca belirli kullanıcılara SSH izni verme
AllowUsers admin operator

# Boş parolaları engelleme
PermitEmptyPasswords no

# Servisi yeniden başlatma
sudo systemctl restart sshd
```

### Güvenlik Duvarı (iptables)
```bash
# Varsayılan engelleme politikası
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -P OUTPUT ACCEPT

# Geri döngü (loopback) trafiğine izin verme
iptables -A INPUT -i lo -j ACCEPT

# Mevcut bağlantılara izin verme
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

# Yalnızca tanımlı SSH portuna izin verme
iptables -A INPUT -p tcp --dport 2222 -j ACCEPT
```

### Kritik Dosya İzinleri
```bash
chmod 644 /etc/passwd
chmod 000 /etc/shadow
chmod 644 /etc/group
chmod 600 /etc/ssh/sshd_config
chmod 700 /root

# Gereksiz SUID izinlerini kaldırma
chmod u-s /usr/bin/gereksiz_ikili_dosya
```

### Çekirdek (Kernel) Sıkılaştırma (/etc/sysctl.conf)
```bash
# IP yönlendirmeyi kapatma
net.ipv4.ip_forward = 0

# Kaynak yönlendirmeyi (Source routing) kapatma
net.ipv4.conf.all.accept_source_route = 0

# SYN Flood saldırılarına karşı SYN cookies açma
net.ipv4.tcp_syncookies = 1

# ASLR bellek korumasını en üst seviyeye alma
kernel.randomize_va_space = 2

# Değişiklikleri uygulama
sysctl -p
```

---

## 📋 Sıkılaştırma Kontrol Listesi

```markdown
Windows İçin:
□ Gereksiz servisleri kapatın (SMBv1, Remote Registry)
□ Windows Güvenlik Duvarını etkinleştirin
□ Güçlü parola ve kilitleme politikası uygulayın
□ PowerShell Script Block Logging açın
□ Komut satırı denetim günlüğünü (Event 4688) aktifleştirin
□ LSASS RunAsPPL ve Credential Guard korumasını açın
□ Güvenlik güncellemelerini düzenli yükleyin

Linux İçin:
□ root doğrudan girişini engelleyin
□ SSH üzerinde parola girişini kapatıp anahtar zorunlu kılın
□ iptables / ufw güvenlik duvarını yapılandırın
□ Kritik sistem dosyalarının (/etc/shadow vb.) izinlerini sıkılaştırın
□ Gereksiz açık servisleri ve portları kapatın
□ sysctl ile çekirdek koruma parametrelerini uygulayın
□ auditd denetim servisini kurup yapılandırın
```

---

## 🔗 İlgili Kopya Kağıtları

- [Olay Müdahalesi (Incident Response)](./Incident-Response.tr.md)
- [Log Analizi](./Log-Analysis.tr.md)

---

**Genel Bakışa Dön:** [🛡️ Mavi Takım](./README.tr.md)
