# 📊 Günlük (Log) Analizi Kopya Kağıdı (Log Analysis Cheatsheet)

```
  ██╗      ██████╗  ██████╗      █████╗ ███╗   ██╗ █████╗ ██╗  ██╗   ██╗███████╗██╗███████╗
  ██║     ██╔═══██╗██╔════╝     ██╔══██╗████╗  ██║██╔══██╗██║  ╚██╗ ██╔╝██╔════╝██║██╔════╝
  ██║     ██║   ██║██║  ███╗    ███████║██╔██╗ ██║███████║██║   ╚████╔╝ ███████╗██║███████╗
  ██║     ██║   ██║██║   ██║    ██╔══██║██║╚██╗██║██╔══██║██║    ╚██╔╝  ╚════██║██║╚════██║
  ███████╗╚██████╔╝╚██████╔╝    ██║  ██║██║ ╚████║██║  ██║███████╗██║   ███████║██║███████║
  ╚══════╝ ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝   ╚══════╝╚═╝╚══════╝
```

---

## 🖥️ Windows Olay Günlükleri (Event Logs)

### Günlük Dosyası Konumları
```
# Güvenlik Günlüğü
C:\Windows\System32\winevt\Logs\Security.evtx

# Sistem Günlüğü
C:\Windows\System32\winevt\Logs\System.evtx

# Uygulama Günlüğü
C:\Windows\System32\winevt\Logs\Application.evtx

# PowerShell Günlükleri
C:\Windows\System32\winevt\Logs\Microsoft-Windows-PowerShell%4Operational.evtx
C:\Windows\System32\winevt\Logs\Windows PowerShell.evtx

# Sysmon (Kurulu ise)
C:\Windows\System32\winevt\Logs\Microsoft-Windows-Sysmon%4Operational.evtx
```

### Kritik Güvenlik Olay Kimlikleri (Event IDs)

#### Kimlik Doğrulama Olayları
| Event ID | Açıklama | Önemi |
|----------|----------|-------|
| **4624** | Başarılı oturum açma | Kullanıcı erişim takibi |
| **4625** | Başarısız oturum açma | Kaba kuvvet saldırısı tespiti |
| **4634** | Oturum kapatma (Logoff) | Oturum süresi takibi |
| **4647** | Kullanıcı tarafından oturum kapatma | Oturum takibi |
| **4648** | Açık kimlik bilgisiyle oturum açma | Yatayda ilerleme (runas vb.) |
| **4672** | Özel ayrıcalık atanması | Yönetici / Admin oturumları |
| **4768** | Kerberos TGT bileti talebi | Active Directory kimlik doğrulama |
| **4769** | Kerberos servis bileti (TGS) talebi | Kerberoasting saldırısı tespiti |
| **4771** | Kerberos ön kimlik doğrulama başarısız | Parola püskürtme tespiti |
| **4776** | NTLM kimlik doğrulama denemesi | NTLM kimlik doğrulama takibi |

#### Oturum Açma Türleri (Logon Types - 4624 / 4625)
| Tür | Açıklama |
|-----|----------|
| 2 | İnteraktif (Fiziksel yerel klavye girişi) |
| 3 | Ağ üzerinden (SMB, dosya paylaşımı) |
| 4 | Toplu iş (Batch job) |
| 5 | Windows Servisi |
| 7 | Ekran kilidi açma (Unlock) |
| 8 | Ağ açık metin kimlik doğrulama (IIS Basic) |
| 9 | Yeni kimlik bilgileri (runas /netonly) |
| 10 | Uzak Masaüstü Bağlantısı (RDP) |
| 11 | Önbelleğe alınmış kimlik bilgisi (Cached logon) |

#### Hesap Yönetimi Olayları
| Event ID | Açıklama |
|----------|----------|
| **4720** | Yeni kullanıcı hesabı oluşturuldu |
| **4722** | Kullanıcı hesabı etkinleştirildi |
| **4723** | Parola değiştirme denemesi |
| **4724** | Parola sıfırlama işlemi yapıldı |
| **4725** | Kullanıcı hesabı devre dışı bırakıldı |
| **4726** | Kullanıcı hesabı silindi |
| **4728** | Güvenlik grubuna üye eklendi |
| **4732** | Yerel gruba üye eklendi (ör. Administrators) |
| **4756** | Evrensel gruba üye eklendi |

#### Süreç ve Servis Olayları
| Event ID | Açıklama |
|----------|----------|
| **4688** | Yeni bir süreç oluşturuldu (Process creation) |
| **4689** | Bir süreç sonlandırıldı |
| **7045** | Yeni bir Windows servisi yüklendi |
| **7036** | Servis durum değişikliği (Başlatıldı/Durduruldu) |
| **4697** | Servis yüklendi (Security logu) |

#### Zamanlanmış Görevler (Scheduled Tasks)
| Event ID | Açıklama |
|----------|----------|
| **4698** | Zamanlanmış görev oluşturuldu |
| **4699** | Zamanlanmış görev silindi |
| **4700** | Zamanlanmış görev etkinleştirildi |
| **4702** | Zamanlanmış görev güncellendi |

### PowerShell ile Günlük Sorgulama Komutları
```powershell
# Güvenlik günlüğünden son 100 kaydı alma
Get-WinEvent -LogName Security -MaxEvents 100

# Başarısız oturum açma denemelerini sorgulama (4625)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4625} | 
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='IP';E={$_.Properties[19].Value}}

# Başarılı oturum açmaları listeleme (4624)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='LogonType';E={$_.Properties[8].Value}}

# Süreç oluşturma olayları (4688)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4688} |
    Select-Object TimeCreated, @{N='Process';E={$_.Properties[5].Value}}, @{N='CommandLine';E={$_.Properties[8].Value}}

# Yeni kurulan servisler (7045)
Get-WinEvent -FilterHashtable @{LogName='System';ID=7045} |
    Select-Object TimeCreated, @{N='ServiceName';E={$_.Properties[0].Value}}

# Tarih aralığına göre sorgulama (Son 7 gün)
$startTime = (Get-Date).AddDays(-7)
Get-WinEvent -FilterHashtable @{LogName='Security';StartTime=$startTime}
```

---

## 🐧 Linux Günlük Analizi

### Günlük Dosya Konumları
```bash
# Kimlik Doğrulama
/var/log/auth.log        # Debian / Ubuntu
/var/log/secure          # RHEL / CentOS / Rocky

# Sistem Günlükleri
/var/log/syslog          # Debian / Ubuntu
/var/log/messages        # RHEL / CentOS

# Çekirdek (Kernel)
/var/log/kern.log
/var/log/dmesg

# Denetim (Audit)
/var/log/audit/audit.log

# Web Sunucuları
/var/log/apache2/        # Debian Apache
/var/log/nginx/          # Nginx
/var/log/httpd/          # RHEL Apache
```

### Yaygın Analiz Komutları

#### Kimlik Doğrulama Analizi
```bash
# Başarısız SSH oturum denemeleri
grep "Failed password" /var/log/auth.log
grep "authentication failure" /var/log/auth.log

# Başarılı SSH oturumları
grep "Accepted" /var/log/auth.log

# Kaba kuvvet saldırısı yapan IP'leri sayma ve sıralama
grep "Failed password" /var/log/auth.log | awk '{print $(NF-3)}' | sort | uniq -c | sort -rn

# sudo yetki yükseltme hareketleri
grep "sudo:" /var/log/auth.log
grep "session opened for user root" /var/log/auth.log
```

#### Oturum ve Sistem Geçmişi
```bash
# Oturum açmış kullanıcılar
who
w

# Geçmiş oturumlar
last
lastlog

# Başarısız oturum açma kayıtları
lastb

# Sistemin yeniden başlatılma ve kapanma zamanları
last -x | grep reboot
last -x | grep shutdown
```

#### journalctl (Systemd) Kullanımı
```bash
# Gerçek zamanlı günlük izleme (Follow)
journalctl -f

# Belirli bir tarihten itibaren sorgulama
journalctl --since "2024-01-15 10:00:00"
journalctl --since "1 hour ago"

# Belirli bir servisin günlükleri
journalctl -u sshd
journalctl -u nginx

# Yalnızca hata kayıtları
journalctl -p err
```

---

## 🌐 Web Sunucusu Günlük Analizi

```bash
# En çok istek alan ilk 20 URL
awk '{print $7}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# En çok istek gönderen ilk 20 IP
awk '{print $1}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# HTTP durum kodlarının dağılımı
awk '{print $9}' /var/log/apache2/access.log | sort | uniq -c | sort -rn

# SQL Enjeksiyonu denemelerini arama
grep -iE "(union|select|insert|update|delete|drop|script)" /var/log/apache2/access.log

# Dizin atlatma (Path Traversal) denemelerini arama
grep -E "(\.\./|\.\.\\)" /var/log/apache2/access.log
```

---

## 🛡️ Sysmon Günlük Analizi

### Temel Sysmon Olay Kimlikleri (Event IDs)
| Event ID | Açıklama |
|----------|----------|
| **1** | Süreç oluşturma (Process creation - hash ve üst süreç dahil) |
| **2** | Dosya oluşturma zamanı değiştirildi (Timestomping tespiti) |
| **3** | Ağ bağlantısı kuruldu |
| **7** | İmaj/DLL yüklendi |
| **8** | CreateRemoteThread (Süreç enjeksiyonu tespiti) |
| **10** | ProcessAccess (LSASS bellek okuma tespiti) |
| **11** | FileCreate (Dosya oluşturma) |
| **12-14**| Kayıt defteri değişiklikleri |
| **22** | DNS sorgusu yapıldı |

---

## 🔗 İlgili Kopya Kağıtları

- [Olay Müdahalesi (Incident Response)](./Incident-Response.tr.md)
- [SIEM Tespiti](./SIEM-Detection.tr.md)
- [Sigma Kuralları](./Sigma-Rules.tr.md)

---

**Önceki:** [← Olay Müdahalesi](./Incident-Response.tr.md) | **Sonraki:** [SIEM Tespiti →](./SIEM-Detection.tr.md)
