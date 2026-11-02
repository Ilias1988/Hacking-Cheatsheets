# ⚡ Hashcat - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
  _   _           _                _   
 | | | | __ _ ___| |__   ___ __ _| |_ 
 | |_| |/ _` / __| '_ \ / __/ _` | __|
 |  _  | (_| \__ \ | | | (_| (_| | |_ 
 |_| |_|\__,_|___/_| |_|\___\__,_|\__|
                                       
    Dünyanın En Hızlı Parola Kurtarma Aracı
```

<p align="center">
  <img src="https://img.shields.io/badge/Hashcat-GPU_Cracker-red?style=for-the-badge" alt="Hashcat">
  <img src="https://img.shields.io/badge/350+-Hash_Types-orange?style=for-the-badge" alt="Hash Types">
  <img src="https://img.shields.io/badge/GPU-Accelerated-blue?style=for-the-badge" alt="GPU">
  <img src="https://img.shields.io/badge/World's-Fastest-green?style=for-the-badge" alt="Fastest">
</p>

<p align="center">
  <b>🚀 Dünyanın en hızlı ve en gelişmiş GPU destekli parola kurtarma ve hash kırma aracı</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Temel Sözdizimi](#-temel-sözdizimi)
- [Saldırı Modları (Attack Modes)](#-saldırı-modları-attack-modes)
- [Hash Modları (Hash Modes)](#-hash-modları-hash-modes)
- [Maske Saldırısı (Mask Attack)](#-maske-saldırısı-mask-attack)
- [Kurallar (Rules)](#-kurallar-rules)
- [Oturum Yönetimi](#-oturum-yönetimi)
- [GPU Optimizasyonu](#-gpu-optimizasyonu)
- [Gerçek Dünya Örnekleri](#-gerçek-dünya-örnekleri)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [İpuçları ve En İyi Uygulamalar](#-i̇puçları-ve-en-i̇yi-uygulamalar)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**Hashcat**, 350'den fazla hash formatını destekleyen, modern ekran kartlarının (GPU) hesaplama gücünden azami düzeyde faydalanan dünyanın en hızlı parola kurtarma ve güvenlik denetim aracıdır.

### Temel Özellikler

| Özellik | Açıklama |
|---------|----------|
| **350+ Hash Formatı** | MD5, SHA, NTLM, bcrypt, WPA/WPA2 ve çok daha fazlası |
| **GPU Hızlandırması** | NVIDIA CUDA, AMD OpenCL ve Intel desteği |
| **Çoklu GPU Desteği** | Birden çok ekran kartını paralel çalıştırabilme |
| **5 Farklı Saldırı Modu** | Sözlük, Kaba Kuvvet, Kombinasyon, Hibrit vb. |
| **Gelişmiş Kural Motoru** | Güçlü kelime manipülasyon ve türetme kuralları |
| **Çoklu Platform** | Linux, Windows, macOS |

### Hashcat vs John the Ripper Karşılaştırması

| Özellik | Hashcat | John the Ripper |
|---------|---------|-----------------|
| **Kırma Hızı** | ⭐⭐⭐⭐⭐ (Üstün) | ⭐⭐⭐ |
| **GPU Desteği** | Mükemmel (Doğal optimizasyon) | İyi |
| **Hash Türleri** | 350+ | 300+ |
| **Otomatik Tespit** | ❌ Hayır (Mod belirtilmelidir) | ✅ Evet |
| **Kullanım Kolaylığı** | Orta düzey | Daha kolay |
| **En İyi Alanı** | GPU ile kırma | CPU ile kırma |

---

## 📥 Kurulum

### Kali Linux

```bash
sudo apt update && sudo apt install hashcat

# Ekran kartı sürücü/OpenCL paketleri:
sudo apt install hashcat-nvidia  # NVIDIA için
sudo apt install hashcat-amd     # AMD için
```

### Ubuntu / Debian

```bash
sudo apt install hashcat
```

### Windows

```bash
# Resmi siteden zip arşivini indirin:
https://hashcat.net/hashcat/

# Arşivi çıkartıp klasörden çalıştırın:
hashcat.exe -h
```

### Kaynak Koddan Derleme

```bash
git clone https://github.com/hashcat/hashcat.git
cd hashcat
make
./hashcat -h
```

### Kurulumu Doğrulama

```bash
hashcat --version
hashcat -I                    # Algılanan OpenCL / CUDA cihazlarını listeler
hashcat -b                    # Donanım performans testi (benchmark) çalıştırır
```

---

## ⌨️ Temel Sözdizimi

### Genel Sözdizimi

```bash
hashcat [seçenekler] hash|hash_dosyası [sözlük|maske]
```

### Temel Seçenekler

| Seçenek | Açıklama |
|---------|----------|
| `-m` | Hash türü / modu (örn: 0, 1000, 1400) |
| `-a` | Saldırı modu (0=Sözlük, 3=Maske vb.) |
| `-o` | Kırılan sonuçların yazılacağı dosya |
| `-r` | Kural dosyası belirtme |
| `--show` | Kırılmış parolaları ekrana yazdırır |
| `--status` | Otomatik durum güncelleme ekranını etkinleştirir |
| `-w` | İş yükü profili (1-4 arası, 3 veya 4 yüksek performans) |
| `--force` | Uyarıları yok say ve zorla çalıştır |
| `-O` | Optimize edilmiş çekirdekler (hız artırır, max parola boyutu sınırlar) |

### Hızlı Başlangıç

```bash
# Standart sözlük saldırısı (MD5 = mod 0, Dictionary = -a 0)
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Kırılmış parolaları listele
hashcat -m 0 hash.txt --show

# İlgili hash türünün örnek formatını incele
hashcat -m 0 --example-hashes
```

---

## 🎮 Saldırı Modları (Attack Modes)

### Genel Bakış

| Mod | İsim | Açıklama |
|-----|------|----------|
| `-a 0` | Dictionary (Sözlük) | Belirtilen kelime listesindeki sözcükleri dener |
| `-a 1` | Combination (Kombinasyon) | İki farklı kelime listesindeki sözcükleri birleştirir |
| `-a 3` | Brute-Force / Mask | Maske tabanlı sistematik kaba kuvvet |
| `-a 6` | Hybrid Dict + Mask | Kelime listesi + sonuna maske ekleme |
| `-a 7` | Hybrid Mask + Dict | Başına maske + ardına kelime listesi ekleme |

### Sözlük Saldırısı (-a 0)

```bash
# Temel sözlük saldırısı
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Kurallar ile birlikte (parolaları türeterek)
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rules/best64.rule

# Birden çok sözlük dosyası kullanarak
hashcat -m 0 -a 0 hash.txt wordlist1.txt wordlist2.txt
```

### Kombinasyon Saldırısı (-a 1)

İki ayrı kelime listesindeki tüm kelimeleri birbirine yapıştırarak dener.

```bash
hashcat -m 0 -a 1 hash.txt wordlist1.txt wordlist2.txt
# Örnek: word1 + word2 şeklinde kombinasyonlar üretir
```

### Kaba Kuvvet / Maske Saldırısı (-a 3)

```bash
# 6 karakterli, tamamen küçük harflerden oluşan kombinasyonlar
hashcat -m 0 -a 3 hash.txt ?l?l?l?l?l?l

# 4 ila 8 haneli sadece rakamlardan oluşan kombinasyonlar
hashcat -m 0 -a 3 hash.txt ?d?d?d?d --increment --increment-min=4 --increment-max=8

# Özel karakter kümesi ile (?1 = küçük harf veya rakam)
hashcat -m 0 -a 3 hash.txt -1 ?l?d ?1?1?1?1?1?1
```

### Hibrit Saldırılar (-a 6, -a 7)

```bash
# Sözlük + Maske (Kelimenin sonuna 4 haneli rakam ekler)
hashcat -m 0 -a 6 hash.txt wordlist.txt ?d?d?d?d

# Maske + Sözlük (Kelimenin başına 4 haneli rakam ekler)
hashcat -m 0 -a 7 hash.txt ?d?d?d?d wordlist.txt
```

---

## 🔢 Hash Modları (Hash Modes)

### En Yaygın Hash Modları

| Mod | Hash Türü | Örnek Format |
|-----|-----------|--------------|
| 0 | MD5 | `5d41402abc4b2a76b9719d911017c592` |
| 100 | SHA1 | `aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d` |
| 1400 | SHA256 | `2cf24dba5fb0a30e26e83b2ac5b9e29e...` |
| 1700 | SHA512 | `cf83e1357eefb8bdf1542850d66d8007d620e405...` |
| 1000 | NTLM (Windows) | `32ed87bdb5fdc5e9cba88547376818d4` |
| 3000 | LM (Windows Eski) | `aad3b435b51404ee` |
| 5600 | NetNTLMv2 | Karmaşık ağ hash formatı |
| 3200 | bcrypt | `$2a$10$...` |
| 1800 | sha512crypt (Linux) | `$6$...` |
| 500 | md5crypt (Linux) | `$1$...` |

### Web Uygulamaları ve CMS Sistemleri

| Mod | Hash Türü |
|-----|-----------|
| 400 | WordPress (phpass) |
| 2500 | WPA / WPA2 (Eski EAPOL formatı) |
| 22000 | WPA-PBKDF2-PMKID + EAPOL (Yeni standart) |
| 121 | SMF (Simple Machines Forum) |
| 2611 | vBulletin |
| 3711 | MediaWiki B |
| 7900 | Drupal 7 |

### Veritabanları

| Mod | Hash Türü |
|-----|-----------|
| 300 | MySQL 4.1 / MySQL 5 |
| 200 | MySQL 323 |
| 1731 | MSSQL (2012, 2014) |
| 112 | Oracle S |
| 12300 | Oracle T |

### Belgeler ve Arşivler

| Mod | Hash Türü |
|-----|-----------|
| 9600 | Microsoft Office 2013 |
| 9500 | Microsoft Office 2010 |
| 9400 | Microsoft Office 2007 |
| 9700 | MS Office ⇐ 2003 |
| 10500 | PDF 1.4 - 1.6 |
| 10700 | PDF 1.7 Level 8 |
| 11600 | 7-Zip |
| 13600 | WinZip |
| 17200 | PKZIP |
| 13000 | RAR5 |
| 12500 | RAR3-hp |

### Kripto Para Cüzdanları

| Mod | Hash Türü |
|-----|-----------|
| 11300 | Bitcoin wallet.dat |
| 12700 | Blockchain wallet |
| 16600 | Electrum wallet |
| 15200 | Ethereum Keystore |

### Modları Arama ve Listeleme

```bash
# Desteklenen modları listeleme
hashcat --help | grep -i "hash modes"

# Belirli bir hash modunu arama
hashcat --example-hashes | grep -i "ntlm"
hashcat --example-hashes | grep -i "sha256"

# İlgili hash türü için örnek hash formatını görüntüleme
hashcat -m 1000 --example-hashes
```

---

## 🎭 Maske Saldırısı (Mask Attack)

### Karakter Kümesi Yer Tutucuları

| Yer Tutucu | Kapsadığı Karakterler |
|------------|-----------------------|
| `?l` | Küçük harfler (`abcdefghijklmnopqrstuvwxyz`) |
| `?u` | Büyük harfler (`ABCDEFGHIJKLMNOPQRSTUVWXYZ`) |
| `?d` | Rakamlar (`0123456789`) |
| `?h` | Küçük onaltılık karakterler (`0123456789abcdef`) |
| `?H` | Büyük onaltılık karakterler (`0123456789ABCDEF`) |
| `?s` | Özel karakterler (`!"#$%&'()*+,-./:;<=>?@[]^_`{\|}~`) |
| `?a` | Tümü (`?l?u?d?s` bileşimi) |
| `?b` | Tüm bayt değerleri (`0x00 - 0xff`) |

### Özel Karakter Kümeleri (-1, -2, -3, -4)

```bash
# ?1 için küçük harf ve rakamları tanımlama
hashcat -a 3 -1 ?l?d hash.txt ?1?1?1?1?1?1

# Birden çok özel küme belirleme
hashcat -a 3 -1 abc -2 123 hash.txt ?1?2?1?2

# Özel simgeler belirleme
hashcat -a 3 -1 '!@#$%' hash.txt pass?1?1?1?1
```

### Artımlı Maske Modu (--increment)

```bash
# 1 ila 8 karakter arasındaki tüm kombinasyonları dene
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment

# Minimum ve maksimum uzunluk belirterek
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment --increment-min=4 --increment-max=8
```

### Yaygın Maske Kalıpları

```bash
# 8 karakter küçük harf
?l?l?l?l?l?l?l?l

# Bilinen kelime + 4 hane rakam
password?d?d?d?d

# Büyük harf + 5 küçük harf + 2 rakam
?u?l?l?l?l?l?d?d

# Mevsim + Yıl kalıbı (örn: Winter2024)
?u?l?l?l?l?l?d?d?d?d

# 10 haneli telefon numarası
?d?d?d?d?d?d?d?d?d?d
```

---

## 📜 Kurallar (Rules)

### Yerleşik Kural Dosyaları

```bash
# Kural dosyalarının varsayılan konumu
ls /usr/share/hashcat/rules/

# Popüler kurallar:
best64.rule           # En etkili 64 türetme kuralı
rockyou-30000.rule    # En çok sonuç veren 30.000 kural
d3ad0ne.rule          # D3ad0ne kuralları
dive.rule             # Derinlemesine türetme kuralı
generated.rule        # Otomatik üretilmiş kurallar
```

### Kuralları Kullanma

```bash
# Tek bir kural dosyası belirterek
hashcat -m 0 -a 0 hash.txt wordlist.txt -r best64.rule

# Birden fazla kural dosyasını zincirleyerek
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rule1.rule -r rule2.rule

# Rastgele kural üreterek
hashcat -m 0 -a 0 hash.txt wordlist.txt -g 1000
```

### Yaygın Kural Operatörleri

| Kural | Açıklama | Örnek |
|-------|----------|-------|
| `:` | Değişiklik yapma | password |
| `l` | Hepsini küçük harf yap | PASSWORD → password |
| `u` | Hepsini büyük harf yap | password → PASSWORD |
| `c` | Baş harfi büyüt | password → Password |
| `C` | Baş harfi küçült, kalanları büyüt | password → pASSWORD |
| `t` | Harf durumlarını tersine çevir | password → PASSWORD |
| `r` | Kelimeyi tersten yaz | password → drowssap |
| `d` | Kelimeyi iki kez yaz | pass → passpass |
| `$X` | Sona X karakterini ekle | pass → passX |
| `^X` | Başa X karakterini ekle | pass → Xpass |
| `sXY` | X harflerini Y ile değiştir | password → pa$$word |

---

## 💾 Oturum Yönetimi

### İsimlendirilmiş Oturum Başlatma

```bash
# Oturuma özel isim verme
hashcat -m 0 -a 0 hash.txt wordlist.txt --session=mysession

# Oturum dosyaları ~/.hashcat/sessions/ dizininde saklanır
```

### Duraklatma ve Devam Ettirme (Pause & Resume)

```bash
# Kırma işlemi sürerken terminalde:
# 'p' tuşu: Duraklatır (Pause)
# 'r' tuşu: Devam ettirir (Resume)

# Komut satırından kesintiye uğrayan oturumu devam ettirme:
hashcat --session=mysession --restore
```

### Çalışma Sırasında Klavye Kısayolları

Kırma işlemi devam ederken basılabilecek tuşlar:
- `s` - Güncel durumu göster (status)
- `p` - Duraklat (pause)
- `r` - Devam ettir (resume)
- `b` - Mevcut saldırıyı atla (bypass)
- `c` - Kontrol noktası kaydet (checkpoint)
- `q` - Güvenle çıkış yap (quit)

### Çıktı Seçenekleri

```bash
# Sonuçları dosyaya kaydetme
hashcat -m 0 -a 0 hash.txt wordlist.txt -o cracked.txt

# Çıktı formatını belirleme (2 = hash:parola formatı)
hashcat -m 0 hash.txt --outfile-format=2

# Kırılmış olanları göster
hashcat -m 0 hash.txt --show

# Henüz kırılmamış (kalan) hashleri göster
hashcat -m 0 hash.txt --left
```

---

## 🚀 GPU Optimizasyonu

### İş Yükü Profilleri (-w)

| Profil | Seviye | Açıklama |
|--------|--------|----------|
| `-w 1` | Düşük | Masaüstü deneyimini etkilemez (etkileşimli kullanım) |
| `-w 2` | Orta | Dengeli performans |
| `-w 3` | Yüksek | Yüksek hız (ekranda donmalar yaşanabilir) |
| `-w 4` | Kabus | Özel parola kırma sunucuları için tam güç |

### Hızlandırma Seçenekleri

```bash
# Maksimum performans
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 3 -O

# Optimize edilmiş çekirdekler (-O) ile belirgin hız artışı
hashcat -m 0 -a 0 hash.txt wordlist.txt -O

# Belirli bir donanım cihazını seçme (-d)
hashcat -m 0 -a 0 hash.txt wordlist.txt -d 1

# Sadece GPU kullan, CPU'yu hariç tut (-D 2)
hashcat -m 0 -a 0 hash.txt wordlist.txt -D 2
```

### Cihaz Türü Kodları (-D)

```bash
# -D 1 = CPU
# -D 2 = GPU
# -D 3 = FPGA / DSP
```

### Donanım Testi (Benchmark)

```bash
# Tüm formatlar için genel performans testi
hashcat -b

# Belirli bir mod için test yapma (örn: MD5)
hashcat -b -m 0

# OpenCL optimizasyonlu tam donanım testi
hashcat -b --benchmark-all
```

---

## 🎬 Gerçek Dünya Örnekleri

### Örnek 1: MD5 Sözlük Saldırısı
```bash
hashcat -m 0 -a 0 md5_hashes.txt /usr/share/wordlists/rockyou.txt
```

### Örnek 2: Kurallar ile Windows NTLM Kırma
```bash
hashcat -m 1000 -a 0 ntlm_hashes.txt wordlist.txt -r /usr/share/hashcat/rules/best64.rule
```

### Örnek 3: WPA/WPA2 WiFi El Sıkışması (Handshake)
```bash
# Önce pcapng dosyasını hashcat formatına dönüştürün
hcxpcapngtool -o hash.hc22000 capture.pcapng

# Ardından WPA2 modunda kırın
hashcat -m 22000 hash.hc22000 /usr/share/wordlists/rockyou.txt
```

### Örnek 4: SHA256 Kaba Kuvvet (6 Haneli)
```bash
hashcat -m 1400 -a 3 sha256_hash.txt ?a?a?a?a?a?a
```

### Örnek 5: Şifreli Office 2013 Belgesi
```bash
# Hashi office2john ile çıkarın
office2john document.docx > office.hash

# Hashcat ile kırın
hashcat -m 9600 office.hash wordlist.txt
```

### Örnek 6: Linux /etc/shadow Parolası
```bash
hashcat -m 1800 -a 0 shadow_hash.txt wordlist.txt
```

### Örnek 7: WordPress Parola Hashi
```bash
hashcat -m 400 -a 0 wordpress_hash.txt wordlist.txt
```

### Örnek 8: Hibrit Saldırı (Şirket Adı + 4 Haneli Rakam)
```bash
hashcat -m 0 -a 6 hash.txt company_names.txt ?d?d?d?d
```

### Örnek 9: Active Directory NTDS.dit Hashleri
```bash
# secretsdump çıktısından elde edilen NTLM hashleri
hashcat -m 1000 -a 0 ntds_hashes.txt wordlist.txt -r best64.rule --username
```

### Örnek 10: Bitcoin Cüzdan Parolası
```bash
# bitcoin2john ile çıkarılan hash
hashcat -m 11300 bitcoin_hash.txt wordlist.txt
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar Tablosu

| Görev | Komut |
|-------|-------|
| MD5 sözlük saldırısı | `hashcat -m 0 -a 0 hash.txt wordlist.txt` |
| NTLM kaba kuvvet | `hashcat -m 1000 -a 3 hash.txt ?a?a?a?a?a?a` |
| SHA256 kurallar ile | `hashcat -m 1400 -a 0 hash.txt wordlist.txt -r rules.rule` |
| Kırılanları göster | `hashcat -m 0 hash.txt --show` |
| Benchmark | `hashcat -b` |
| Cihazları listele | `hashcat -I` |
| Oturumu devam ettir | `hashcat --session=name --restore` |

### Sık Kullanılan Hash Modları

| Mod | Hash Türü |
|-----|-----------|
| 0 | MD5 |
| 100 | SHA1 |
| 1000 | NTLM |
| 1400 | SHA256 |
| 1800 | sha512crypt (Linux) |
| 3200 | bcrypt |
| 22000 | WPA / WPA2 |

### Saldırı Modları

| Mod | Tür |
|-----|-----|
| -a 0 | Dictionary (Sözlük) |
| -a 1 | Combination (Kombinasyon) |
| -a 3 | Brute-force (Maske) |
| -a 6 | Hibrit (Sözlük + Maske) |
| -a 7 | Hibrit (Maske + Sözlük) |

---

## 💡 İpuçları ve En İyi Uygulamalar

### Verimlilik İpuçları

1. **Önce Sözlük + Kurallar ile Başlayın**
   ```bash
   hashcat -m 0 -a 0 hash.txt rockyou.txt -r best64.rule
   ```

2. **Her Zaman Optimize Edilmiş Çekirdekleri Deneyin**
   ```bash
   hashcat -O ...
   ```

3. **Hedefe Özel Kalıpları Hedefleyin**
   ```bash
   # Mevsim + Yıl: Winter2024
   hashcat -a 3 -1 WSFJ -2 winterumeall hash.txt ?1?2?2?2?2?2?d?d?d?d
   ```

### Maksimum Performans İpuçları

```bash
# Özel kırma sistemlerinde tam güç
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 4 -O --force

# Birden fazla ekran kartı seçimi
hashcat -d 1,2 ...
```

---

## ⚠️ Yasal Uyarı

> **UYARI:** Hashcat, yalnızca **yetkili ve yasal güvenlik değerlendirmelerinde** kullanılması gereken bir araçtır.
> 
> - ✅ Sahibi olduğunuz sistemlerin parola direncini denetleyin
> - ✅ Açık ve yazılı izin alınmış güvenlik testlerinde kullanın
> - ❌ İzniniz olmayan hiçbir parola hashini çözmeyin
> 
> **Yetkisiz parola kırma girişimleri yasalara aykırıdır.**

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [Hashcat Resmi Sitesi](https://hashcat.net/hashcat/)
- [Hashcat Wiki](https://hashcat.net/wiki/)
- [Hashcat GitHub Deposu](https://github.com/hashcat/hashcat)
- [Hashcat Forumu](https://hashcat.net/forum/)

### Kelime Listeleri
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### İlgili Kılavuzlar
- [John the Ripper](../../John-The-Ripper/translations/README.tr.md)
- [Hydra](../../Hydra/translations/README.tr.md)
- [Nmap](../../Nmap/translations/README.tr.md)

---

<p align="center">
  <b>⚡ GPU Parola Kırma Gücüne Hakim Olun!</b><br>
  <i>Dünyanın en hızlı parola kurtarma deneyimi</i>
</p>
