# 🎯 Tehdit Avcılığı Kopya Kağıdı (Threat Hunting Cheatsheet)

```
████████╗██╗  ██╗██████╗ ███████╗ █████╗ ████████╗    ██╗  ██╗██╗   ██╗███╗   ██╗████████╗██╗███╗   ██╗ ██████╗ 
╚══██╔══╝██║  ██║██╔══██╗██╔════╝██╔══██╗╚══██╔══╝    ██║  ██║██║   ██║████╗  ██║╚══██╔══╝██║████╗  ██║██╔════╝ 
   ██║   ███████║██████╔╝█████╗  ███████║   ██║       ███████║██║   ██║██╔██╗ ██║   ██║   ██║██╔██╗ ██║██║  ███╗
   ██║   ██╔══██║██╔══██╗██╔══╝  ██╔══██║   ██║       ██╔══██║██║   ██║██║╚██╗██║   ██║   ██║██║╚██╗██║██║   ██║
   ██║   ██║  ██║██║  ██║███████╗██║  ██║   ██║       ██║  ██║╚██████╔╝██║ ╚████║   ██║   ██║██║ ╚████║╚██████╔╝
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝       ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Tehdit Avcılığı Süreci (Threat Hunting Process)

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│ 1. HİPOTEZ  │────▶│ 2. VERİ     │────▶│ 3. ANALİZ   │────▶│ 4. MÜDAHALE │
│  (Teori)    │     │  TOPLAMA    │     │   (Avcılık) │     │   (Aksiyon) │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
                                                                    │
                          ┌─────────────┐                          │
                          │ 5. BELGELEME│◀─────────────────────────┘
                          │   (Öğrenme) │
                          └─────────────┘
```

---

## 📋 Hipotez Örnekleri

| Kategori | Hipotez |
|----------|---------|
| **Başlangıç Erişimi** | Saldırganlar sisteme sızmak için makrolu Word belgesi içeren oltalama kullanıyor olabilir |
| **Kalıcılık** | Saldırganlar sistemde kalıcı olmak için zamanlanmış görev oluşturmuş olabilir |
| **Kimlik Bilgisi Hırsızlığı** | Saldırganlar LSASS bellek dökümü alarak parola çalmaya çalışıyor olabilir |
| **Yatayda İlerleme** | Saldırganlar diğer sunuculara sıçramak için RDP veya WMI kullanıyor olabilir |
| **Veri Sızdırma** | Saldırganlar ağ kontrollerini atlatmak için DNS tünelleme yöntemiyle veri kaçırıyor olabilir |

---

## 🔍 Tehdit Avlama Teknikleri

### Kalıcılık (Persistence) Avcılığı

#### Windows Kayıt Defteri Başlangıç Anahtarları
```powershell
# Run ve RunOnce anahtarlarını sorgulama
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce" -ErrorAction SilentlyContinue
```

#### Şüpheli Zamanlanmış Görevler
```powershell
# Etkin zamanlanmış görevleri listeleme
Get-ScheduledTask | Where-Object {$_.State -ne "Disabled"} | 
    Select-Object TaskName, TaskPath, State | Format-Table

# Microsoft harici yazarlar tarafından oluşturulmuş görevler
Get-ScheduledTask | Where-Object {$_.Author -notlike "*Microsoft*"} | 
    Select-Object TaskName, Author, State
```

#### Windows Servisleri
```powershell
# Windows dizinleri dışından çalışan servisler
Get-WmiObject Win32_Service | Where-Object {$_.PathName -notlike "*Windows*"} | 
    Select-Object Name, PathName, StartMode, State
```

### Yatay İlerleme (Lateral Movement) Avcılığı

#### RDP Bağlantıları
```powershell
# RDP oturumları (Olay 4624 LogonType 10)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} | 
    Where-Object {$_.Properties[8].Value -eq 10} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='Source';E={$_.Properties[18].Value}}
```

#### Anlık Ağ Bağlantıları ve Süreç Eşleştirmesi
```powershell
# Aktif bağlantılar ve bağlı süreç adları
Get-NetTCPConnection -State Established | ForEach-Object {
    $proc = Get-Process -Id $_.OwningProcess -ErrorAction SilentlyContinue
    [PSCustomObject]@{
        LocalAddress = $_.LocalAddress
        RemoteAddress = $_.RemoteAddress
        RemotePort = $_.RemotePort
        Process = $proc.ProcessName
    }
} | Format-Table
```

### Kod Çalıştırma (Execution) Avcılığı

#### Kodlanmış PowerShell Komutları
```powershell
# PowerShell betik bloğu günlüğünde şüpheli komutlar (Event 4104)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-PowerShell/Operational';ID=4104} |
    Where-Object {$_.Message -match "FromBase64" -or $_.Message -match "-enc"} |
    Select-Object TimeCreated, Message
```

#### Şüpheli Üst-Alt Süreç İlişkisi
```powershell
# Word/Excel tarafından cmd veya powershell başlatılması (Sysmon Event 1)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Where-Object {
        ($_.Properties[20].Value -match "WINWORD|EXCEL|OUTLOOK") -and
        ($_.Properties[4].Value -match "cmd|powershell")
    }
```

---

## 🐧 Linux Üzerinde Tehdit Avcılığı

```bash
# Zamanlanmış görevler (Cron)
cat /etc/crontab
for user in $(cut -f1 -d: /etc/passwd); do crontab -l -u $user 2>/dev/null; done

# SSH Yetkili Anahtarları
find / -name "authorized_keys" 2>/dev/null

# SUID yetkili dosyalar
find / -perm -4000 -type f 2>/dev/null

# Aktif bağlantılar ve dinlenen portlar
ss -tnp state established
lsof -i -P -n | grep ESTABLISHED

# Son 7 günde değiştirilmiş sistem ikili dosyaları
find /usr/bin /usr/sbin -mtime -7 2>/dev/null
```

---

## 🛠️ Tehdit Avcılığı Araçları

| Araç | Kullanım Amacı |
|------|----------------|
| **Velociraptor** | Geniş ölçekli uç nokta tehdit avcılığı ve sorgulama |
| **OSQuery** | SQL sorguları ile sistem durumunu analiz etme |
| **KAPE** | Hızlı adli bilişim ve triyaj veri toplama |
| **Chainsaw** | Hızlı Windows olay günlüğü analizi ve kural eşleme |
| **DeepBlueCLI** | PowerShell tabanlı otomatik günlük tehdit avı |

---

## 📋 Hızlı Avcılık Kontrol Listesi

```markdown
□ Başlangıç noktalarını denetleyin (Kayıt defteri, servisler, cron, görevler)
□ Son süreç oluşturma olaylarını inceleyin (Event 4688, Sysmon 1)
□ Olağan dışı ağ bağlantılarını ve uzak IP'leri analiz edin
□ Kodlanmış (Base64) komutları tarayın
□ LSASS bellek erişim girişimlerini araştırın
□ Şüpheli üst-alt süreç türemelerini denetleyin
□ PowerShell işlem geçmişini ve günlüklerini inceleyin
□ Yatay ilerleme (RDP, PsExec, WMI) göstergelerini arayın
```

---

## 🔗 İlgili Kopya Kağıtları

- [Log Analizi](./Log-Analysis.tr.md)
- [SIEM Tespiti](./SIEM-Detection.tr.md)
- [Sigma Kuralları](./Sigma-Rules.tr.md)

---

**Önceki:** [← SIEM Tespiti](./SIEM-Detection.tr.md) | **Sonraki:** [Sigma Kuralları →](./Sigma-Rules.tr.md)
