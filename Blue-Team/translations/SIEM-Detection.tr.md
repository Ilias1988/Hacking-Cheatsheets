# 📈 SIEM Tespit ve Korelasyon Kopya Kağıdı (SIEM Detection Cheatsheet)

```
  ███████╗██╗███████╗███╗   ███╗    ██████╗ ███████╗████████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝██║██╔════╝████╗ ████║    ██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
  ███████╗██║█████╗  ██╔████╔██║    ██║  ██║█████╗     ██║   █████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
  ╚════██║██║██╔══╝  ██║╚██╔╝██║    ██║  ██║██╔══╝     ██║   ██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
  ███████║██║███████╗██║ ╚═╝ ██║    ██████╔╝███████╗   ██║   ███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝╚══════╝╚═╝     ╚═╝    ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## 🔶 Splunk Sorguları (SPL)

### Kimlik Doğrulama ve Erişim (Authentication & Access)

#### Kaba Kuvvet (Brute Force) Tespiti
```spl
# Aynı IP'den çok sayıda başarısız oturum denemesi
index=windows EventCode=4625
| stats count by src_ip, user
| where count > 10
| sort -count

# Başarısız denemelerin ardından gelen başarılı oturum (Hesap ele geçirme)
index=windows (EventCode=4625 OR EventCode=4624)
| transaction user maxspan=10m
| where eventcount > 5 AND match(EventCode, "4624")
| table _time, user, src_ip, EventCode
```

#### Parola Püskürtme (Password Spraying) Tespiti
```spl
# Tek bir IP'den birçok farklı kullanıcı hesabına deneme yapılması
index=windows EventCode=4625
| stats dc(user) as unique_users, count by src_ip
| where unique_users > 5 AND count > 10
| sort -unique_users

# Kısa sürede çok sayıda kullanıcı denenmesi
index=windows EventCode=4625
| bucket _time span=5m
| stats dc(user) as unique_users, values(user) as users by _time, src_ip
| where unique_users > 5
```

#### Şüpheli Yönetici Oturumları
```spl
# Yönetici hesaplarının mesai saatleri dışındaki oturumları
index=windows EventCode=4624
| eval hour=strftime(_time, "%H")
| where hour < 7 OR hour > 19
| search user IN ("Administrator", "admin", "*admin*")
| stats count by user, src_ip
```

### Süreç ve Kod Çalıştırma (Process & Execution)

#### Şüpheli Süreç Oluşturma
```spl
# Base64 ile kodlanmış PowerShell komutları
index=windows EventCode=4688 
| search CommandLine="*-enc*" OR CommandLine="*-encoded*" OR CommandLine="*FromBase64*"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Office uygulamalarının komut satırı başlatması (Malware Word/Excel)
index=windows EventCode=4688
| search ParentProcessName="*excel.exe" OR ParentProcessName="*word.exe" OR ParentProcessName="*outlook.exe"
| search NewProcessName="*cmd.exe" OR NewProcessName="*powershell.exe" OR NewProcessName="*wscript.exe"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# LOLBAS araçlarının kullanımı
index=windows EventCode=4688
| search NewProcessName IN ("*certutil.exe", "*mshta.exe", "*regsvr32.exe", "*rundll32.exe", "*bitsadmin.exe")
| table _time, user, NewProcessName, CommandLine
```

#### Mimikatz ve Kimlik Bilgisi Hırsızlığı
```spl
# Komut satırında Mimikatz anahtar kelimeleri
index=windows EventCode=4688
| search CommandLine="*sekurlsa*" OR CommandLine="*kerberos::*" OR CommandLine="*lsadump*"
| table _time, user, NewProcessName, CommandLine

# LSASS sürecine yetkisiz erişim (Sysmon Olay 10)
index=sysmon EventCode=10 TargetImage="*lsass.exe"
| table _time, SourceImage, SourceUser, GrantedAccess
```

### Kalıcılık ve Yatay İlerleme (Persistence & Lateral Movement)

#### Zamanlanmış Görev ve Servis Kurulumu
```spl
# Yeni zamanlanmış görev oluşturma
index=windows EventCode=4698
| table _time, user, TaskName, TaskContent

# Yeni servis kurulumu
index=windows EventCode=7045
| table _time, ServiceName, ServiceFileName, ServiceType, StartType
```

#### RDP ve PsExec ile Yatay İlerleme
```spl
# Tek bir kaynaktan çok sayıda hedefe RDP bağlantısı
index=windows EventCode=4624 LogonType=10
| stats dc(dest) as unique_hosts, values(dest) as hosts by src_ip, user
| where unique_hosts > 3

# PsExec aktivitesi
index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"
| table _time, src_ip, dest, ServiceFileName
```

---

## 🟢 Elasticsearch / Kibana (KQL ve Lucene)

### Kimlik Doğrulama
```kql
# Başarısız ağ oturumları
event.code: 4625 AND winlog.event_data.LogonType: 3

# Başarılı ve başarısız oturumların takibi
event.code: (4625 OR 4624)
```

### Süreç Analizi
```kql
# Kodlanmış PowerShell çalıştırma
event.code: 4688 AND process.command_line: (*-enc* OR *-encoded* OR *FromBase64*)

# LOLBAS ikili dosyaları
event.code: 4688 AND process.name: (certutil.exe OR mshta.exe OR regsvr32.exe OR rundll32.exe)

# Ofis yazılımlarının komut kabuğu türetmesi
event.code: 4688 AND process.parent.name: (WINWORD.EXE OR EXCEL.EXE OR OUTLOOK.EXE) AND process.name: (cmd.exe OR powershell.exe)

# Mimikatz tespiti
process.command_line: (*sekurlsa* OR *kerberos::* OR *lsadump* OR *mimikatz*)
```

---

## 📊 MITRE ATT&CK Tespit Matrisi

| Taktik | Teknik | Splunk Tespit Sorgusu |
|--------|--------|-----------------------|
| **Başlangıç Erişimi** | Oltalama | `index=email \| search subject="*urgent*" OR attachment="*.exe"` |
| **Kod Çalıştırma** | PowerShell | `index=windows EventCode=4688 NewProcessName="*powershell*"` |
| **Kalıcılık** | Zamanlanmış Görev | `index=windows EventCode=4698` |
| **Yetki Yükseltme** | Jeton Manipülasyonu | `index=windows EventCode=4672 NOT user="SYSTEM"` |
| **Savunma Atlatma** | Log Temizleme | `index=windows EventCode=1102` |
| **Kimlik Erişimi** | LSASS Dökümü | `index=sysmon EventCode=10 TargetImage="*lsass.exe"` |
| **Yatay İlerleme** | RDP / PsExec | `index=windows EventCode=4624 LogonType=10` |
| **Veri Sızdırma** | DNS Tünelleme | `index=dns \| eval len=len(query) \| where len>50` |

---

## 🔗 İlgili Kopya Kağıtları

- [Log Analizi](./Log-Analysis.tr.md)
- [Sigma Kuralları](./Sigma-Rules.tr.md)
- [Tehdit Avcılığı](./Threat-Hunting.tr.md)

---

**Önceki:** [← Log Analizi](./Log-Analysis.tr.md) | **Sonraki:** [Tehdit Avcılığı →](./Threat-Hunting.tr.md)
