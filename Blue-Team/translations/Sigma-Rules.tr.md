# 📝 Sigma Kuralları Kopya Kağıdı (Sigma Rules Cheatsheet)

```
███████╗██╗ ██████╗ ███╗   ███╗ █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
██╔════╝██║██╔════╝ ████╗ ████║██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
███████╗██║██║  ███╗██╔████╔██║███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
╚════██║██║██║   ██║██║╚██╔╝██║██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
███████║██║╚██████╔╝██║ ╚═╝ ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
╚══════╝╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**Sigma**; SIEM sistemleri ve log analiz platformları için açık, genel geçer ve platformdan bağımsız bir kural tanımlama biçimidir. Bir kez yazılan kural Splunk, Elasticsearch, QRadar vb. sistemlere dönüştürülebilir.

---

## 📖 Sigma Kural Yapısı

```yaml
title: Kural Başlığı
id: benzersiz-uuid
status: experimental|test|stable
description: Bu kuralın neyi tespit ettiğinin açıklaması
author: Yazar Adı
date: 2024/01/15
references:
    - https://attack.mitre.org/techniques/T1059/
logsource:
    category: process_creation
    product: windows
detection:
    selection:
        FieldName: 'aranan_deger'
    condition: selection
falsepositives:
    - Meşru sistem yöneticisi işlemleri
level: high|medium|low|critical
tags:
    - attack.execution
    - attack.t1059
```

---

## 🎯 Tespit Kuralı Örnekleri

### Mimikatz Komut Satırı Tespiti
```yaml
title: Mimikatz Komut Satırı Tespiti
id: a642964e-5618-4fa6-bf2b-a98c6dba52df
status: stable
description: Komut satırı argümanları üzerinden Mimikatz kullanımını tespit eder
author: Güvenlik Ekibi
logsource:
    category: process_creation
    product: windows
detection:
    selection:
        CommandLine|contains:
            - 'sekurlsa::'
            - 'kerberos::'
            - 'lsadump::'
            - 'crypto::'
            - 'dpapi::'
            - 'privilege::debug'
    condition: selection
level: critical
tags:
    - attack.credential_access
    - attack.t1003
```

### Kodlanmış PowerShell Komutu
```yaml
title: Kodlanmış PowerShell Çalıştırma Tespiti
id: fb603c23-7a2c-4c89-b8e7-2d6fa33c9b2e
status: stable
description: Base64 ile gizlenmiş PowerShell komutlarını tespit eder
logsource:
    category: process_creation
    product: windows
detection:
    selection:
        Image|endswith: '\powershell.exe'
        CommandLine|contains:
            - '-enc'
            - '-EncodedCommand'
            - '-e '
            - 'FromBase64String'
    condition: selection
level: high
tags:
    - attack.execution
    - attack.t1059.001
```

### Şüpheli Zamanlanmış Görev Oluşturma
```yaml
title: Şüpheli Dizinlerde Zamanlanmış Görev Oluşturuldu
id: 7a4e6b3c-9f1c-4d8a-b2e5-8c3f6a9d2e1b
status: stable
description: Komut satırından geçici dizinlere işaret eden zamanlanmış görevleri tespit eder
logsource:
    category: process_creation
    product: windows
detection:
    selection:
        CommandLine|contains|all:
            - 'schtasks'
            - '/create'
    suspicious_paths:
        CommandLine|contains:
            - '\AppData\'
            - '\Temp\'
            - '\ProgramData\'
    condition: selection and suspicious_paths
level: medium
tags:
    - attack.persistence
    - attack.t1053.005
```

### LSASS Bellek Erişimi (Sysmon Olay 10)
```yaml
title: LSASS Bellek Erişimi Tespiti
id: 32d0d3e2-e58d-4d41-926b-18b520b2b32d
status: stable
description: LSASS sürecine yetkisiz bellek erişim girişimlerini yakalar
logsource:
    category: process_access
    product: windows
detection:
    selection:
        TargetImage|endswith: '\lsass.exe'
        GrantedAccess|contains:
            - '0x1010'
            - '0x1410'
            - '0x1438'
            - '0x143a'
    filter:
        SourceImage|endswith:
            - '\wmiprvse.exe'
            - '\svchost.exe'
    condition: selection and not filter
level: critical
tags:
    - attack.credential_access
    - attack.t1003.001
```

---

## 🔧 Alan Değiştiricileri (Field Modifiers)

| Değiştirici | Açıklama | Örnek |
|-------------|----------|-------|
| `contains` | Değer belirtilen dizeyi içerir | `CommandLine\|contains: 'mimikatz'` |
| `startswith` | Değer belirtilen dizeyle başlar | `Image\|startswith: 'C:\Temp'` |
| `endswith` | Değer belirtilen uzantıyla biter | `Image\|endswith: '.exe'` |
| `all` | Listedeki tüm değerlerin eşleşmesi gerekir | `contains\|all:` |
| `base64` | Base64 kodlu arama | `CommandLine\|base64:` |
| `re` | Düzenli ifade (Regex) | `CommandLine\|re: '.*password.*'` |

---

## 🛠️ Sigma Araçları ve Dönüştürme

### sigmac ile SIEM Sorgusuna Çevirme
```bash
# Splunk SPL formatına dönüştürme
sigmac -t splunk rule.yml

# Elastic Elasticsearch sorgusuna dönüştürme
sigmac -t es-qs rule.yml

# QRadar formatına dönüştürme
sigmac -t qradar rule.yml
```

### sigma-cli Kullanımı
```bash
# Kural doğruluğunu denetleme
sigma check rule.yml

# Dönüştürme
sigma convert -t splunk rule.yml
```

---

## 🔗 İlgili Kopya Kağıtları

- [SIEM Tespiti](./SIEM-Detection.tr.md)
- [YARA Kuralları](./YARA-Rules.tr.md)
- [Log Analizi](./Log-Analysis.tr.md)

---

**Önceki:** [← Tehdit Avcılığı](./Threat-Hunting.tr.md) | **Sonraki:** [YARA Kuralları →](./YARA-Rules.tr.md)
