# 🔓 Hydra - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
  _   _           _           
 | | | |_   _  __| |_ __ __ _ 
 | |_| | | | |/ _` | '__/ _` |
 |  _  | |_| | (_| | | | (_| |
 |_| |_|\__, |\__,_|_|  \__,_|
        |___/                  
    Hızlı Ağ Girişi (Login) Kırıcı
```

<p align="center">
  <img src="https://img.shields.io/badge/Hydra-Password_Cracker-red?style=for-the-badge" alt="Hydra">
  <img src="https://img.shields.io/badge/Brute-Force-orange?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/50+-Protocols-blue?style=for-the-badge" alt="Protocols">
  <img src="https://img.shields.io/badge/Network-Login-green?style=for-the-badge" alt="Network">
</p>

<p align="center">
  <b>🔑 Dünyanın en popüler çok iş parçacıklı ağ giriş kırma aracı</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Temel Sözdizimi](#-temel-sözdizimi)
- [Desteklenen Protokoller](#-desteklenen-protokoller)
- [SSH Saldırıları](#-ssh-saldırıları)
- [FTP Saldırıları](#-ftp-saldırıları)
- [HTTP Form Saldırıları](#-http-form-saldırıları)
- [Veritabanı Saldırıları](#-veritabanı-saldırıları)
- [Uzak Masaüstü Saldırıları](#-uzak-masaüstü-saldırıları)
- [Diğer Servisler](#-diğer-servisler)
- [Kelime Listeleri & Parolalar](#-kelime-listeleri--parolalar)
- [Gelişmiş Seçenekler](#-gelişmiş-seçenekler)
- [Gerçek Dünya Örnekleri](#-gerçek-dünya-örnekleri)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [İpuçları ve En İyi Uygulamalar](#-i̇puçları-ve-en-i̇yi-uygulamalar)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**Hydra** (THC-Hydra), çevrimiçi (online) kimlik doğrulama servislerine karşı hızlı ve esnek parola kırma saldırıları düzenlemek için geliştirilmiştir. Çok sayıda protokolü destekler ve hız için paralel iş parçacıkları (threading) kullanır.

### Temel Özellikler

| Özellik | Açıklama |
|---------|----------|
| **50+ Protokol** | SSH, FTP, HTTP, RDP, SMB ve çok daha fazlası |
| **Paralel Çalışma** | Eşzamanlı birden çok bağlantı kurabilme |
| **Esnek Yapılandırma** | Özel başlıklar (headers), çerezler (cookies), proxy desteği |
| **Oturumu Devam Ettirme** | Saldırı oturumunu kaydedip daha sonra kaldığı yerden devam ettirme (`-R`) |
| **Çoklu Platform** | Linux, Windows, macOS |

### Saldırı Türleri

| Tür | Açıklama |
|-----|----------|
| **Sözlük Saldırısı (Dictionary)** | Kelime listesindeki parolaları sırayla dener |
| **Kaba Kuvvet (Brute Force)** | Olası tüm karakter kombinasyonlarını üretip dener |
| **Parola Püskürtme (Password Spray)** | Tek bir yaygın parolayı birden çok kullanıcı hesabına karşı dener |
| **Kimlik Bilgisi Doldurma (Credential Stuffing)** | Önceden sızdırılmış kullanıcı:parola çiftlerini test eder |

---

## 📥 Kurulum

### Kali Linux (Önceden Kuruludur)
```bash
hydra -h
```

### Ubuntu / Debian
```bash
sudo apt update && sudo apt install hydra
```

### Kaynak Koddan Derleme
```bash
git clone https://github.com/vanhauser-thc/thc-hydra
cd thc-hydra
./configure
make
sudo make install
```

### macOS (Homebrew)
```bash
brew install hydra
```

### Kurulum Doğrulama
```bash
hydra -h
hydra -V
```

---

## ⌨️ Temel Sözdizimi

### Genel Sözdizimi
```bash
hydra [seçenekler] hedef servis
```

### Sık Kullanılan Seçenekler

| Seçenek | Açıklama |
|---------|----------|
| `-l` | Tek bir kullanıcı adı belirtir |
| `-L` | Kullanıcı adlarının bulunduğu liste |
| `-p` | Tek bir parola belirtir |
| `-P` | Parolaların bulunduğu kelime listesi |
| `-C` | İki nokta üst üste ile ayrılmış kullanıcı:parola dosyası |
| `-t` | Eşzamanlı iş parçacığı sayısı (varsayılan: 16) |
| `-s` | Servis port numarası |
| `-o` | Başarılı sonuçları dosyaya yazdır |
| `-f` | İlk geçerli kimlik bilgisi bulunduğunda dur |
| `-V` | Ayrıntılı mod (denenen parolaları gösterir) |
| `-vV` | Çok ayrıntılı mod |
| `-d` | Hata ayıklama (debug) modu |

### Hızlı Başlangıç Örnekleri

```bash
# Tek kullanıcı, parola listesi
hydra -l admin -P passwords.txt target ssh

# Kullanıcı listesi, tek parola (Password Spraying)
hydra -L users.txt -p password123 target ssh

# Hem kullanıcı hem parola listesi
hydra -L users.txt -P passwords.txt target ssh
```

---

## 📡 Desteklenen Protokoller

### Protokol Listesi

```
adam6500    afp         asterisk    cisco       cisco-enable
cvs         firebird    ftp         ftps        http-form-get
http-form-post          http-get    http-head   http-post
http-proxy  http-proxy-urlenum      https-form-get
https-form-post         https-get   https-head  https-post
imap        imaps       irc         ldap2       ldap2s
ldap3       ldap3-crammd5           ldap3-digestmd5
ldap3s      ldap3s-crammd5          ldap3s-digestmd5
memcached   mongodb     mssql       mysql       nntp
oracle      oracle-listener         oracle-sid  pcanywhere
pcnfs       pop3        pop3s       postgres    radmin2
rdp         redis       rexec       rlogin      rpcap
rsh         rtsp        s7-300      sapr3       sip
smb         smtp        smtp-enum   smtps       snmp
socks5      ssh         sshkey      svn         teamspeak
telnet      vmauthd     vnc         xmpp
```

---

## 🔐 SSH Saldırıları

### Temel SSH Saldırısı

```bash
# Tek kullanıcı ile
hydra -l root -P /usr/share/wordlists/rockyou.txt ssh://192.168.1.1

# Kullanıcı listesi ile
hydra -L users.txt -P passwords.txt ssh://192.168.1.1

# Özel port belirterek (örn: 2222)
hydra -l admin -P passwords.txt -s 2222 192.168.1.1 ssh
```

### SSH Seçenekleri

```bash
# İş parçacığını sınırla (hesap kilitlenmesini veya banlanmayı önlemek için)
hydra -l admin -P passwords.txt -t 4 192.168.1.1 ssh

# İlk eşleşmede saldırıyı sonlandır
hydra -l admin -P passwords.txt -f 192.168.1.1 ssh

# Ayrıntılı çıktı alarak ilerlemeyi izle
hydra -l admin -P passwords.txt -V 192.168.1.1 ssh
```

### SSH Anahtar Doğrulaması

```bash
# Özel anahtarlarla parola denemesi
hydra -l root -x 6:8:a 192.168.1.1 sshkey
```

---

## 📂 FTP Saldırıları

### Temel FTP Saldırısı

```bash
# Standart FTP brute-force
hydra -l admin -P passwords.txt ftp://192.168.1.1

# Anonim FTP girişi kontrolü
hydra -l anonymous -p anonymous ftp://192.168.1.1

# Kullanıcı listesi ile
hydra -L users.txt -P passwords.txt ftp://192.168.1.1
```

### FTPS (SSL Üzerinden FTP)

```bash
hydra -l admin -P passwords.txt ftps://192.168.1.1
```

---

## 🌐 HTTP Form Saldırıları

### HTTP-GET-FORM

```bash
hydra -l admin -P passwords.txt target http-get-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid"

# Format:
# "/dizin:parametreler:hata_mesajı"
```

### HTTP-POST-FORM (En Yaygın)

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid credentials"
```

### Form Sözdizimi Kuralları

```
"/sayfa:POST_parametreleri:Hata_ifadesi"

Burada:
- ^USER^ = Kullanıcı adı yer tutucusu
- ^PASS^ = Parola yer tutucusu
- Hata_ifadesi = Başarısız girişte sayfada beliren metin
```

### Başarı Mesajına Göre Tespit (S=)

```bash
# Hata mesajı yerine başarılı girişteki metni aramak için S= kullanılır
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:S=Welcome"
```

### Çerezler (Cookies) ile Kullanım

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:Invalid:H=Cookie: PHPSESSID=abc123"
```

### HTTP Basic Auth

```bash
# HTTP Basic Kimlik Doğrulaması
hydra -l admin -P passwords.txt http-get://192.168.1.1/admin/

# HTTPS
hydra -l admin -P passwords.txt https-get://192.168.1.1/admin/
```

### HTTP Proxy

```bash
hydra -l admin -P passwords.txt http-proxy://proxy.server:3128
```

---

## 🗄️ Veritabanı Saldırıları

### MySQL

```bash
hydra -l root -P passwords.txt mysql://192.168.1.1

# Özel port belirterek
hydra -l root -P passwords.txt -s 3307 192.168.1.1 mysql
```

### PostgreSQL

```bash
hydra -l postgres -P passwords.txt postgres://192.168.1.1
```

### Microsoft SQL Server (MSSQL)

```bash
hydra -l sa -P passwords.txt mssql://192.168.1.1
```

### Oracle

```bash
# Oracle SID saldırısı
hydra -l SYS -P passwords.txt oracle://192.168.1.1/SID

# Oracle dinleyicisi
hydra -l admin -P passwords.txt oracle-listener://192.168.1.1
```

### MongoDB

```bash
hydra -l admin -P passwords.txt mongodb://192.168.1.1
```

### Redis

```bash
hydra -P passwords.txt redis://192.168.1.1
```

---

## 🖥️ Uzak Masaüstü Saldırıları

### RDP (Windows Uzak Masaüstü)

```bash
hydra -l administrator -P passwords.txt rdp://192.168.1.1

# İş parçacığını 1'e düşürün (RDP yavaş yanıt verir ve kilitlenmeye yatkındır)
hydra -l administrator -P passwords.txt -t 1 rdp://192.168.1.1
```

### VNC

```bash
# VNC (yalnızca parola gerektirir)
hydra -P passwords.txt vnc://192.168.1.1

# Belirli bir ekrana/porta yönelik
hydra -P passwords.txt -s 5901 192.168.1.1 vnc
```

### Telnet

```bash
hydra -l admin -P passwords.txt telnet://192.168.1.1
```

---

## 🔧 Diğer Servisler

### SMB (Windows Dosya Paylaşımı)

```bash
hydra -l administrator -P passwords.txt smb://192.168.1.1
```

### SMTP

```bash
# SMTP kimlik doğrulaması
hydra -l user@domain.com -P passwords.txt smtp://192.168.1.1

# SMTP kullanıcı keşfi (Enumeration)
hydra -L users.txt smtp-enum://192.168.1.1
```

### POP3 / IMAP

```bash
# POP3
hydra -l user@domain.com -P passwords.txt pop3://192.168.1.1

# IMAP
hydra -l user@domain.com -P passwords.txt imap://192.168.1.1
```

### LDAP

```bash
hydra -l "cn=admin,dc=example,dc=com" -P passwords.txt ldap://192.168.1.1
```

### SNMP

```bash
hydra -P community_strings.txt snmp://192.168.1.1
```

---

## 📚 Kelime Listeleri & Parolalar

### Popüler Kelime Listeleri

```bash
# Kali Linux varsayılan konumları
/usr/share/wordlists/rockyou.txt
/usr/share/wordlists/dirb/common.txt
/usr/share/seclists/Passwords/

# SecLists parolaları
/usr/share/seclists/Passwords/Common-Credentials/10-million-password-list-top-100000.txt
/usr/share/seclists/Passwords/darkweb2017-top10000.txt
/usr/share/seclists/Passwords/Default-Credentials/
```

### Parola Üretimi (-x Parametresi)

```bash
# Sözdizimi: min:max:karakter_kümesi
hydra -l admin -x 4:6:a 192.168.1.1 ssh    # 4-6 karakterli küçük harfler
hydra -l admin -x 4:6:A 192.168.1.1 ssh    # 4-6 karakterli büyük harfler
hydra -l admin -x 4:6:1 192.168.1.1 ssh    # 4-6 karakterli sayılar
hydra -l admin -x 4:6:aA1 192.168.1.1 ssh  # Karışık kombinasyon

# Karakter kümesi seçenekleri:
# a = küçük harfler    A = büyük harfler
# 1 = sayılar          ! = özel karakterler
```

### İki Nokta Üst Üste ile Ayrılmış Liste (-C)

```bash
# kullanıcı:parola formatı
hydra -C creds.txt 192.168.1.1 ssh

# creds.txt içeriği:
# admin:admin
# admin:password
# root:toor
# guest:guest
```

### Kullanıcı/Parola Kombinasyon Ayarları (-e)

```bash
# Boş parolaları dene
hydra -l admin -e n 192.168.1.1 ssh

# Kullanıcı adını parola olarak dene
hydra -l admin -e s 192.168.1.1 ssh

# Kullanıcı adının tersten yazılışını parola olarak dene
hydra -l admin -e r 192.168.1.1 ssh

# Tüm özel varyasyonları kelime listesiyle birlikte dene
hydra -l admin -e nsr -P passwords.txt 192.168.1.1 ssh
```

---

## ⚡ Gelişmiş Seçenekler

### Çıktı & Günlük Kaydı (Logging)

```bash
# Başarılı girişleri metin dosyasına kaydet
hydra -l admin -P passwords.txt -o results.txt 192.168.1.1 ssh

# JSON formatında çıktı al
hydra -l admin -P passwords.txt -o results.json -b json 192.168.1.1 ssh
```

### Kesilen Saldırıyı Devam Ettirme (Resume)

```bash
# Oturumu kaydetme desteği ile başlat (-R)
hydra -l admin -P large_wordlist.txt -o results.txt -R 192.168.1.1 ssh

# Kesintiye uğrayan saldırıyı kaldığı yerden devam ettir
hydra -R
```

### Proxy Desteği

```bash
# HTTP proxy kullanımı
export HYDRA_PROXY=http://proxy:8080
hydra -l admin -P passwords.txt 192.168.1.1 ssh

# Tor / SOCKS5 proxy kullanımı
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt 192.168.1.1 ssh
```

### Performans ve Zaman Aşımı Ayarları

```bash
# İş parçacığı sayısı (varsayılan: 16)
hydra -l admin -P passwords.txt -t 32 192.168.1.1 ssh

# Denemeler arası bekleme süresi (saniye)
hydra -l admin -P passwords.txt -w 10 192.168.1.1 ssh

# Bağlantı zaman aşımı süresi
hydra -l admin -P passwords.txt -T 30 192.168.1.1 ssh
```

### Çoklu Hedef Taraması (-M)

```bash
# Dosyadan hedefleri oku
hydra -L users.txt -P passwords.txt -M targets.txt ssh

# targets.txt içeriği:
# 192.168.1.1
# 192.168.1.2
# 192.168.1.3
```

---

## 🎬 Gerçek Dünya Örnekleri

### Örnek 1: SSH Kaba Kuvvet Saldırısı
```bash
hydra -l root -P /usr/share/wordlists/rockyou.txt \
    -t 4 -V 192.168.1.1 ssh
```

### Örnek 2: Web Giriş Formu
```bash
hydra -l admin -P passwords.txt \
    192.168.1.1 http-post-form \
    "/login:username=^USER^&password=^PASS^:Invalid"
```

### Örnek 3: WordPress Yönetici Paneli
```bash
hydra -l admin -P passwords.txt \
    target.com http-post-form \
    "/wp-login.php:log=^USER^&pwd=^PASS^:Invalid username"
```

### Örnek 4: Tek Sunucuda Çoklu Servis
```bash
hydra -l admin -P passwords.txt 192.168.1.1 ssh ftp mysql
```

### Örnek 5: Parola Püskürtme (Password Spraying)
```bash
hydra -L users.txt -p "Winter2024!" \
    192.168.1.1 smb
```

### Örnek 6: Tor / Proxy ile Anonim Saldırı
```bash
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt target.com http-post-form \
    "/login:user=^USER^&pass=^PASS^:Failed"
```

### Örnek 7: HTTP Basic Auth (Yönetici Paneli)
```bash
hydra -l admin -P passwords.txt \
    https-get://192.168.1.1/admin/
```

### Örnek 8: RDP Saldırısı (Dikkatle Kullanın!)
```bash
hydra -l administrator -P passwords.txt \
    -t 1 -V rdp://192.168.1.1
```

---

## 📊 Hızlı Başvuru

### Temel Komut Tablosu

| Görev | Komut |
|-------|-------|
| SSH saldırısı | `hydra -l user -P pass.txt ssh://target` |
| FTP saldırısı | `hydra -l user -P pass.txt ftp://target` |
| HTTP POST form | `hydra -l user -P pass.txt target http-post-form "/login:u=^USER^&p=^PASS^:Failed"` |
| RDP saldırısı | `hydra -l user -P pass.txt rdp://target` |
| Sonuçları kaydetme | `hydra ... -o results.txt` |
| Kaldığı yerden devam etme | `hydra -R` |

### Sık Kullanılan Seçenekler

| Seçenek | Açıklama |
|---------|----------|
| `-l/-L` | Kullanıcı adı / kullanıcı listesi |
| `-p/-P` | Parola / parola listesi |
| `-C` | user:pass dosyası |
| `-t` | İş parçacığı sayısı |
| `-s` | Servis portu |
| `-f` | Başarıda dur |
| `-V` | Ayrıntılı mod |
| `-o` | Çıktı dosyası |
| `-x` | Parola üretimi |
| `-e nsr` | Boş/aynı/ters kombinasyonları dene |

---

## 💡 İpuçları ve En İyi Uygulamalar

### Performans İpuçları

1. **Hassas Servislerde İş Parçacığını Düşürün**
   ```bash
   # RDP ve SMB için düşük thread kullanın
   hydra -t 1 ...
   
   # SSH için dengeli bir değer seçin
   hydra -t 4 ...
   
   # Web formlarında daha yüksek değerler kullanılabilir
   hydra -t 16 ...
   ```

2. **Hedefe Özel Kelime Listeleri Kullanın**
   - rockyou.txt yerine önce hedef kuruma özel veya en sık kullanılan ilk 1000 parolayı deneyin.
   - Varsayılan cihaz/yazılım kimlik bilgilerini test edin.

3. **Öncelikle Hesap Kilitleme Politikasını Öğrenin**
   ```bash
   # Küçük bir test listesi ile kontrol edin
   hydra -l admin -P small.txt -t 1 target ssh
   ```

### Gizlilik ve Güvenlik İpuçları

```bash
# İstekler arasına bekleme süresi koyun
hydra -l admin -P passwords.txt -w 30 target ssh

# Düşük iş parçacığı ile çalışın
hydra -l admin -P passwords.txt -t 2 target ssh

# Proxy / Tor üzerinden yönlendirin
export HYDRA_PROXY=socks5://127.0.0.1:9050
```

---

## ⚠️ Yasal Uyarı

> **UYARI:** Hydra, yalnızca **yetkili ve izinli güvenlik testlerinde** kullanılması gereken güçlü bir saldırı aracıdır.
> 
> - ✅ Sahibi olduğunuz veya yazılı izin aldığınız sistemlerde test edin
> - ✅ Yasal sızma testi sözleşmeleri çerçevesinde kullanın
> - ❌ İzniniz olmayan hiçbir sisteme karşı parola denemesi yapmayın
> 
> **Yetkisiz erişim girişimleri suç teşkil eder ve yasal yaptırımlara tabidir.**

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [THC-Hydra GitHub](https://github.com/vanhauser-thc/thc-hydra)
- [Hydra Kılavuzu](https://github.com/vanhauser-thc/thc-hydra/blob/master/README.md)

### Kelime Listeleri
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [Default Credentials](https://github.com/ihebski/DefaultCreds-cheat-sheet)

### İlgili Kılavuzlar
- [Nmap](../../Nmap/translations/README.tr.md)
- [Metasploit](../../Metasploit/translations/README.tr.md)
- [SQLMap](../../SQLMap/translations/README.tr.md)

---

<p align="center">
  <b>🔓 Ağ Giriş Kırma Tekniklerinde Uzmanlaşın!</b><br>
  <i>Her zaman önce yasal izin alın</i>
</p>
