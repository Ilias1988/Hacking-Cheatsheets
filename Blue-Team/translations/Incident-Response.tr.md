# 🚨 Olay Müdahalesi Kopya Kağıdı (Incident Response Cheatsheet)

```
  ██╗███╗   ██╗ ██████╗██╗██████╗ ███████╗███╗   ██╗████████╗
  ██║████╗  ██║██╔════╝██║██╔══██╗██╔════╝████╗  ██║╚══██╔══╝
  ██║██╔██╗ ██║██║     ██║██║  ██║█████╗  ██╔██╗ ██║   ██║   
  ██║██║╚██╗██║██║     ██║██║  ██║██╔══╝  ██║╚██╗██║   ██║   
  ██║██║ ╚████║╚██████╗██║██████╔╝███████╗██║ ╚████║   ██║   
  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝   
        ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗███████╗███████╗
        ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔════╝██╔════╝
        ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║███████╗█████╗  
        ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║╚════██║██╔══╝  
        ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║███████║███████╗
        ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚══════╝
```

---

## 📊 Olay Müdahale Yaşam Döngüsü (NIST SP 800-61)

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│ 1. HAZIRLIK  │────▶│ 2. TESPİT VE │────▶│ 3. SINIRLAMA │────▶│ 4. YOK ETME  │
│(Preparation) │     │    ANALİZ    │     │(Containment) │     │(Eradication) │
└──────────────┘     └──────────────┘     └──────────────┘     └──────────────┘
       ▲                                                              │
       │         ┌──────────────┐     ┌──────────────┐               │
       └─────────│ 6. ÇIKARILAN │◀────│ 5. İYİLEŞTİRME│◀──────────────┘
                 │   DERSLER    │     │  VE KURTARMA │
                 └──────────────┘     └──────────────┘
```

---

## 1️⃣ Hazırlık (Preparation)

### Dokümantasyon Kontrol Listesi
```markdown
- [ ] Olay Müdahale Planı (IR Plan) belgelendi mi?
- [ ] İletişim listesi (dahili ve harici paydaşlar) güncel mi?
- [ ] Kriz iletişim planı hazır mı?
- [ ] Varlık ve donanım/yazılım envanteri tam mı?
- [ ] Ağ topolojisi ve mimari diyagramları erişilebilir mi?
- [ ] Temel sistem yapılandırma (baseline) kayıtları mevcut mu?
- [ ] Düzenli yedeklerin sağlamlığı test edildi mi?
```

### Olay Müdahale Ekip Şablonu
```
Olay Müdahale Lideri: [İsim] - [Telefon] - [E-posta]
BT Güvenlik Ekibi:    [İsim] - [Telefon] - [E-posta]
Hukuk Departmanı:     [İsim] - [Telefon] - [E-posta]
Kurumsal İletişim:    [İsim] - [Telefon] - [E-posta]
İnsan Kaynakları:     [İsim] - [Telefon] - [E-posta]
Harici Adli Bilişim:  [Firma] - [Telefon]
Kolluk Kuvvetleri:    [Siber Suçlar Birimi] - [Telefon]
```

### Temel Müdahale ve Delil Toplama Araçları
```bash
# Adli Bilişim ve İmaj Alma
- FTK Imager
- Volatility 3
- Velociraptor
- KAPE

# Ağ Analizi
- Wireshark
- tcpdump
- Zeek

# Günlük Analizi
- Splunk / ELK Stack
- grep / awk / sed
```

---

## 2️⃣ Tespit ve Analiz (Detection & Analysis)

### İlk Triyaj Soruları
```markdown
1. Olayın türü nedir?
   - [ ] Zararlı yazılım bulaşması (Ransomware/Trojan)
   - [ ] Yetkisiz erişim / Kimlik hırsızlığı
   - [ ] Veri sızıntısı (Exfiltration)
   - [ ] Hizmet aksatma (DDoS)
   - [ ] İç tehdit (Insider threat)
   - [ ] Oltalama (Phishing)

2. Olay ne zaman tespit edildi?
3. Nasıl ve hangi alarm/log ile tespit edildi?
4. Hangi sistemler ve IP'ler etkilendi?
5. Hangi veriler risk altında?
6. Saldırı aktivitesi halen devam ediyor mu?
```

### Windows Hızlı Triyaj Komutları
```cmd
# Aktif kullanıcı oturumları
query user
qwinsta

# Aktif ağ bağlantıları
netstat -ano | findstr ESTABLISHED

# Çalışan süreçler
tasklist /v
wmic process get processid,parentprocessid,commandline

# Son değiştirilen dosyalar
forfiles /P C:\ /S /D +01/01/2024

# Zamanlanmış görevler
schtasks /query /fo LIST /v

# Servisler
sc query state=all
wmic service get name,startmode,state

# Başlangıç kayıt defteri anahtarları
reg query "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"
reg query "HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"

# Son PowerShell geçmişi
type %APPDATA%\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt
```

### Linux Hızlı Triyaj Komutları
```bash
# Oturum açmış kullanıcılar
w
who
last -a

# Aktif bağlantılar ve dinlenen portlar
ss -tulnp
netstat -tulnp
lsof -i

# Çalışan süreçler
ps auxwww
pstree -p

# Son 24 saatte değişen dosyalar
find / -mtime -1 -type f 2>/dev/null

# Zamanlanmış görevler (Cron)
cat /etc/crontab
ls -la /etc/cron.*
for user in $(cut -f1 -d: /etc/passwd); do crontab -u $user -l 2>/dev/null; done

# Başlangıç servisleri
ls -la /etc/init.d/
systemctl list-unit-files --type=service

# Komut geçmişi
cat ~/.bash_history
cat /root/.bash_history
```

### Ağ Trafiği Yakalama
```bash
# Arayüzden trafiği yakalama
tcpdump -i eth0 -w capture.pcap

# Belirli bir hedef veya kaynağı filtreleme
tcpdump -i eth0 host 192.168.1.100

# HTTP ve HTTPS trafiği
tcpdump -i eth0 port 80 or port 443

# DNS sorguları
tcpdump -i eth0 port 53
```

---

## 3️⃣ Sınırlandırma ve İzolasyon (Containment)

### Kısa Vadeli Sınırlandırma
```bash
# Ağı izole etme (Windows Güvenlik Duvarı)
netsh advfirewall set allprofiles state on
netsh advfirewall firewall add rule name="Block All" dir=in action=block
netsh advfirewall firewall add rule name="Block All Out" dir=out action=block

# Ağı izole etme (Linux iptables)
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

# Ele geçirilen hesabı devre dışı bırakma
net user tehlikeli_hesap /active:no   # Windows
passwd -l tehlikeli_hesap             # Linux

# Zararlı süreci sonlandırma
taskkill /PID <pid> /F               # Windows
kill -9 <pid>                        # Linux
```

### Uzun Vadeli Sınırlandırma
```bash
# Ağ segmentasyonu uygulama (Etkilenen VLAN'ı izole etme)
# Güvenlik duvarı üzerinde zararlı IP ve alan adlarını engelleme
# Proxy ve DNS sinkhole kurallarını güncelleme

# Etkilenen servisleri geçici olarak durdurma
sc config <servis_adi> start= disabled   # Windows
systemctl disable <servis_adi>           # Linux
```

### Adli Delilleri Koruma (Uçuculuk Sırası)
```bash
# Bellek dökümü (Windows - WinPMEM veya DumpIt)
winpmem_mini_x64.exe memory.raw

# Disk imajı alma
dd if=/dev/sda of=/evidence/disk.img bs=4M status=progress

# Bütünlük için SHA-256 hash alma
sha256sum /evidence/disk.img > disk.img.sha256

# Uçuculuk Sırası (Order of Volatility):
# 1. RAM Bellek (En uçucu)
# 2. Ağ bağlantı durumu ve önbellekler
# 3. Çalışan süreçler
# 4. Sabit disk ve kalıcı depolama
```

---

## 4️⃣ Yok Etme (Eradication)

### Kalıcılık Mekanizmalarını Temizleme
```bash
# Windows Kayıt Defteri Kalıcılığı
reg delete "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v "MalwareName" /f

# Windows Zamanlanmış Görevleri
schtasks /delete /tn "MalwareTask" /f

# Windows Servisleri
sc delete "MalwareService"

# Linux Cron Görevleri
crontab -r
rm /etc/cron.d/malware

# Linux Systemd Servisleri
systemctl disable malware
rm /etc/systemd/system/malware.service
```

### Kimlik Bilgilerini Sıfırlama
```bash
# Etkilenen tüm kullanıcıların parolalarını zorunlu değiştirin
# Servis hesabı parolalarını sıfırlayın
# API belirteçlerini ve SSH anahtarlarını yenileyin
# Tüm oturumları geçersiz kılın (revoke sessions)

# Active Directory Kerberos Biletlerini Sıfırlama:
# - krbtgt hesabının parolasını 10 saat arayla İKİ KEZ sıfırlayın
# - Tüm Domain Admin parolalarını yenileyin
```

---

## 5️⃣ İyileştirme ve Kurtarma (Recovery)

### Sistemleri Yeniden Devreye Alma
```bash
# Temiz yedekten geri yükleme veya sıfırdan kurulum
# Ağ bağlantısını kontrollü ve aşamalı açma:
1. Kritik çekirdek sistemleri devreye alın
2. Sistemleri yeniden bulaşma belirtilerine karşı yakından izleyin
3. Kalan alt sistemleri kademeli olarak başlatın
4. Normal işleyişe dönüşü teyit edin
```

---

## 6️⃣ Çıkarılan Dersler (Lessons Learned)

```markdown
## Olay Sonrası Değerlendirme Raporu

### Olay Özeti
- Olay Kimliği (ID):
- Tespit Tarihi:
- Kontrol Altına Alınma Tarihi:
- Çözüm Tarihi:
- Olay Türü:
- Kritiklik Düzeyi:

### Kök Neden Analizi (Root Cause Analysis)
- Saldırgan sisteme nasıl sızdı?
- Hangi zafiyetler veya insan hataları istismar edildi?
- Hangi güvenlik kontrolleri başarısız oldu?

### İyileştirme Önerileri
1. Acil teknik iyileştirmeler
2. Uzun vadeli mimari düzenlemeler
3. Politika ve farkındalık eğitimleri
```

---

## 🔗 İlgili Kopya Kağıtları

- [Log Analizi](./Log-Analysis.tr.md)
- [SIEM Tespiti](./SIEM-Detection.tr.md)
- [Volatility (Bellek Analizi)](../../Volatility/translations/README.tr.md)

---

**Sonraki Adım:** [Log Analizi →](./Log-Analysis.tr.md)
