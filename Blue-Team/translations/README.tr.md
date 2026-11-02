# 🛡️ Mavi Takım Kopya Kağıdı (Blue Team Cheatsheets)

```
  ██████╗ ██╗     ██╗   ██╗███████╗    ████████╗███████╗ █████╗ ███╗   ███╗
  ██╔══██╗██║     ██║   ██║██╔════╝    ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║
  ██████╔╝██║     ██║   ██║█████╗         ██║   █████╗  ███████║██╔████╔██║
  ██╔══██╗██║     ██║   ██║██╔══╝         ██║   ██╔══╝  ██╔══██║██║╚██╔╝██║
  ██████╔╝███████╗╚██████╔╝███████╗       ██║   ███████╗██║  ██║██║ ╚═╝ ██║
  ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝       ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝
```

---

## 🎯 Mavi Takım (Blue Team) Nedir?

**Mavi Takım (Blue Team)**, kurumun bilgi sistemlerini korumakla görevli savunma güvenliği ekibidir ve şu temel sorumlulukları üstlenir:
- 🔍 **Tespit Etme (Detecting)** - Tehdit ve saldırıları tespit etmek
- 🛡️ **Savunma (Defending)** - Sistem ve ağ altyapılarını korumak
- 🚨 **Müdahale Etme (Responding)** - Güvenlik olaylarına (incident) hızlı müdahale etmek
- 📊 **İzleme (Monitoring)** - Şüpheli anomalileri ve ağ hareketlerini izlemek
- 🔒 **Sıkılaştırma (Hardening)** - Altyapıyı olası saldırılara karşı güçlendirmek

---

## 📊 Savunma Yaşam Döngüsü

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        SAVUNMA YAŞAM DÖNGÜSÜ                                │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐          │
│   │ 1. HAZIRLIK  │───▶│ 2. TESPİT    │───▶│ 3. ANALİZ            │          │
│   │(Sıkılaştırma)│    │   (İzleme)   │    │  (İnceleme/Araştırma)│          │
│   └──────────────┘    └──────────────┘    └──────────────────────┘          │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐           │
│          │                               │ 4. MÜDAHALE          │           │
│          │                               │    (Sınırlandırma)   │           │
│          │                               └──────────────────────┘           │
│          │                                           │                      │
│          │                                           ▼                      │
│          ▼                               ┌──────────────────────┐           │
│   ┌──────────────┐    ┌──────────────┐   │ 5. KURTARMA          │           │
│   │ 7. GELİŞTİR  │◀───│ 6. DERS ÇIKAR│◀──│   (Geri Yükleme)     │           │
│   │              │    │   (Raporlama)│   └──────────────────────┘           │
│   └──────────────┘    └──────────────┘                                      │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 Mavi Takım Kılavuzları

### 🚨 Olay Müdahalesi ve Tespit
| Konu | Açıklama | Kılavuz |
|------|----------|---------|
| **Olay Müdahalesi (Incident Response)** | IR süreçleri, izolasyon, delil toplama prosedürleri | [📄 Görüntüle](./Incident-Response.tr.md) |
| **Log Analizi** | Windows ve Linux log analizi, kritik Event ID'leri | [📄 Görüntüle](./Log-Analysis.tr.md) |
| **SIEM Tespiti** | Splunk ve ELK tespit sorguları, korelasyon kuralları | [📄 Görüntüle](./SIEM-Detection.tr.md) |
| **Tehdit Avcılığı (Threat Hunting)** | Proaktif tehdit avlama teknikleri ve hipotezler | [📄 Görüntüle](./Threat-Hunting.tr.md) |

### 🔬 Savunma ve Sıkılaştırma
| Konu | Açıklama | Kılavuz |
|------|----------|---------|
| **Sıkılaştırma (Hardening)** | Windows ve Linux sistem sıkılaştırma kontrol listeleri | [📄 Görüntüle](./Hardening.tr.md) |

### 📝 Tespit Kuralları
| Konu | Açıklama | Kılavuz |
|------|----------|---------|
| **Sigma Kuralları** | Platform bağımsız SIEM tespit kuralları | [📄 Görüntüle](./Sigma-Rules.tr.md) |
| **YARA Kuralları** | Zararlı yazılım ve dosya tabanlı IOC tespit kuralları | [📄 Görüntüle](./YARA-Rules.tr.md) |

---

## 🔗 Kırmızı Takım vs Mavi Takım

| Boyut | Kırmızı Takım (Ofansif) | Mavi Takım (Defansif) |
|-------|-------------------------|------------------------|
| **Amaç** | Güvenlik açıklarını bulup sızmak | Sistemleri korumak ve savunmak |
| **Yaklaşım** | Saldırı simülasyonu | Savunma, izleme ve tespit |
| **Araçlar** | Metasploit, Burp Suite, Cobalt Strike | SIEM, EDR, IDS/IPS, Zeek |
| **Çıktı** | Bulunan açıklar ve istismarlar | Engellenen ve tespit edilen olaylar |
| **Odak** | İçeri sızma | Dışarıda tutma ve içeride yakalama |

---

## 📚 Hızlı Başvuru

### Kritik Windows Olay Günlüğü Kimlikleri (Event IDs)

| Event ID | Açıklama |
|----------|----------|
| **4624** | Başarılı oturum açma (Logon) |
| **4625** | Başarısız oturum açma denemesi (Failed logon) |
| **4648** | Açık kimlik bilgisiyle oturum açma (Explicit credentials) |
| **4672** | Yönetici yetkileriyle oturum açma (Special privileges assigned) |
| **4688** | Yeni süreç başlatma (Process creation - komut satırı dahil) |
| **4698** | Zamanlanmış görev oluşturuldu (Scheduled task created) |
| **4720** | Yeni kullanıcı hesabı oluşturuldu |
| **7045** | Yeni bir servis yüklendi (System logu) |

### Önemli Linux Günlük Konumları

| Günlük Türü | Dosya Yolu |
|-------------|------------|
| Kimlik Doğrulama | `/var/log/auth.log` (Debian/Ubuntu) veya `/var/log/secure` (RHEL) |
| Sistem Günlüğü | `/var/log/syslog` veya `/var/log/messages` |
| Denetim Günlüğü | `/var/log/audit/audit.log` |
| Çekirdek Günlüğü | `/var/log/kern.log` |

---

## 🛠️ Temel Mavi Takım Araçları

| Kategori | Araçlar |
|----------|---------|
| **SIEM** | Splunk, ELK Stack, Microsoft Sentinel, IBM QRadar |
| **EDR / XDR** | CrowdStrike Falcon, Microsoft Defender for Endpoint, Carbon Black |
| **IDS / IPS** | Snort, Suricata, Zeek |
| **Adli Bilişim (Forensics)** | Volatility, Autopsy, FTK Imager, Velociraptor |
| **Zararlı Yazılım Analizi** | VirusTotal, Any.run, Cuckoo Sandbox, Ghidra |
| **Ağ Analizi** | Wireshark, tcpdump, NetworkMiner |

---

## 📚 Kaynaklar

- [MITRE ATT&CK Çerçevesi](https://attack.mitre.org/)
- [Sigma Kuralları Resmi Deposu](https://github.com/SigmaHQ/sigma)
- [YARA Kuralları Resmi Deposu](https://github.com/Yara-Rules/rules)
- [SANS Blue Team Wiki](https://wiki.sans.blue/)
- [Splunk Security Essentials](https://splunkbase.splunk.com/app/3435/)

---

<p align="center">
  <b>🛡️ Savunmayı Proaktif Yapın!</b><br>
  <i>En güçlü savunma, tehditleri önceden öngören savunmadır!</i>
</p>
