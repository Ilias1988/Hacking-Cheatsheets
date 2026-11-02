# 🔨 John the Ripper - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
       __      __         
      / /___  / /_  ____  
 __  / / __ \/ __ \/ __ \ 
/ /_/ / /_/ / / / / / / / 
\____/\____/_/ /_/_/ /_/  
  Efsanevi Parola Kırıcı     
```

<p align="center">
  <img src="https://img.shields.io/badge/John-Password_Cracker-red?style=for-the-badge" alt="John">
  <img src="https://img.shields.io/badge/Hash-Cracking-orange?style=for-the-badge" alt="Hash">
  <img src="https://img.shields.io/badge/Open-Source-blue?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Multi-Platform-green?style=for-the-badge" alt="Multi Platform">
</p>

<p align="center">
  <b>🔑 Efsanevi parola güvenliği denetleme ve hash kırma aracı</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Temel Sözdizimi](#-temel-sözdizimi)
- [Kırma Modları (Cracking Modes)](#-kırma-modları-cracking-modes)
- [Hash Çıkarma Araçları (2john)](#-hash-çıkarma-araçları-2john)
- [Yaygın Hash Türleri](#-yaygın-hash-türleri)
- [Linux Parola Kırma](#-linux-parola-kırma)
- [Windows Parola Kırma](#-windows-parola-kırma)
- [Dosya Parolası Kırma](#-dosya-parolası-kırma)
- [Kurallar & Türetme (Rules & Mangling)](#-kurallar--türetme-rules--mangling)
- [Oturum Yönetimi](#-oturum-yönetimi)
- [Performans Optimizasyonu](#-performans-optimizasyonu)
- [Gerçek Dünya Örnekleri](#-gerçek-dünya-örnekleri)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [İpuçları ve En İyi Uygulamalar](#-i̇puçları-ve-en-i̇yi-uygulamalar)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**John the Ripper** (John / JtR), zayıf parolaları tespit etmek ve parola hashlerini kırmak için geliştirilmiş ücretsiz, açık kaynaklı bir güvenlik denetimi ve parola kurtarma aracıdır.

### Temel Özellikler

| Özellik | Açıklama |
|---------|----------|
| **Çoklu Format Desteği** | Yüzlerce farklı hash ve şifreleme formatını destekler |
| **Çoklu Kırma Modları** | Single (Kullanıcı Adı), Wordlist (Sözlük), Incremental (Kaba Kuvvet) |
| **Özel Kural Motoru** | Güçlü kelime türetme ve dönüştürme (mangling) kuralları |
| **Otomatik Tespit** | Hash formatını otomatik tanıma |
| **Çoklu Platform** | Linux, Windows, macOS |
| **GPU Hızlandırması** | OpenCL hızlandırma desteği (Jumbo sürümü) |

### John vs Hashcat Karşılaştırması

| Özellik | John the Ripper | Hashcat |
|---------|-----------------|---------|
| **En İyi Alanı** | CPU ile kırma | GPU ile kırma |
| **Kullanım Kolaylığı** | Daha basit ve pratik | Daha karmaşık bayraklar |
| **Otomatik Hash Tespiti** | ✅ Evet | ❌ Hayır |
| **Kural Motoru** | Güçlü | Çok güçlü |
| **Hash Türleri** | Çok fazla | Daha fazla |
| **GPU Hızı** | İyi | Çok daha üstün |

### Sürümler

| Sürüm | Açıklama |
|-------|----------|
| **John (Core)** | Temel resmi sürüm |
| **John Jumbo** | Topluluk tarafından genişletilmiş, yüzlerce ek araç ve format içeren önerilen sürüm |

---

## 📥 Kurulum

### Kali Linux (Önceden Kuruludur)
```bash
john --version
```

### Ubuntu / Debian
```bash
# Standart sürüm
sudo apt install john

# Jumbo sürümü (şiddetle tavsiye edilir)
sudo apt install john-jumbo
```

### Kaynak Koddan Derleme (Jumbo)
```bash
git clone https://github.com/openwall/john
cd john/src
./configure
make -s clean && make -sj4
cd ../run
./john --version
```

### macOS (Homebrew)
```bash
brew install john-jumbo
```

### Windows
```bash
# Resmi siteden zip indirerek
https://www.openwall.com/john/

# Veya Chocolatey ile
choco install john
```

### Kurulumu Doğrulama
```bash
john --version
john --list=formats | wc -l    # Desteklenen format sayısını gösterir
```

---

## ⌨️ Temel Sözdizimi

### Genel Sözdizimi
```bash
john [seçenekler] [hash_dosyası]
```

### Sık Kullanılan Seçenekler

| Seçenek | Açıklama |
|---------|----------|
| `--wordlist=` | Kelime listesi (wordlist) dosya yolu |
| `--format=` | Hash formatını açıkça belirtir |
| `--rules` | Kelime türetme kurallarını etkinleştirir |
| `--show` | Kırılmış parolaları ekranda listeler |
| `--single` | Single (tekil) kırma modunu kullanır |
| `--incremental` | Kaba kuvvet (brute-force) modunu kullanır |
| `--session=` | Oturuma özel bir isim verir |
| `--restore` | Duraklatılmış oturumu devam ettirir |
| `--pot=` | Özel bir .pot dosyası belirtir |

### Hızlı Başlangıç

```bash
# Formatı otomatik tespit et ve varsayılan ayarlarla kır
john hashes.txt

# Kelime listesi belirterek kır
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Daha önce kırılmış parolaları listele
john --show hashes.txt
```

---

## 🎮 Kırma Modları (Cracking Modes)

### Single (Tekil) Mod

Kullanıcı adı, ev dizini ve GECOS bilgilerini kullanarak çok hızlı tahminler üretir.

```bash
# Single mod (en hızlı moddur, kullanıcı adından türetir)
john --single hashes.txt

# Format belirterek single mod
john --single --format=sha512crypt hashes.txt
```

### Wordlist (Sözlük) Modu

Bir kelime listesindeki sözcükleri (ve isteğe bağlı türetme kurallarını) dener.

```bash
# Temel sözlük saldırısı
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Kurallarla birlikte sözlük saldırısı
john --wordlist=wordlist.txt --rules hashes.txt

# Belirli bir kural seti kullanarak
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
```

### Incremental (Kaba Kuvvet) Modu

Belirli bir karakter kümesindeki tüm kombinasyonları sistematik olarak dener.

```bash
# Varsayılan kaba kuvvet modu (tüm karakterler)
john --incremental hashes.txt

# Belirli karakter kümeleriyle
john --incremental=Digits hashes.txt      # Yalnızca sayılar
john --incremental=Lower hashes.txt       # Yalnızca küçük harfler
john --incremental=Upper hashes.txt       # Yalnızca büyük harfler
john --incremental=Alpha hashes.txt       # Yalnızca harfler
john --incremental=Alnum hashes.txt       # Alfanümerik karakterler
john --incremental=LowerNum hashes.txt    # Küçük harfler + sayılar
```

### Mask (Maskeleme) Modu

Bilinen kalıplara göre hedef odaklı kaba kuvvet uygular.

```bash
# Maske saldırısı (? işaretleri yer tutucudur)
john --mask='?l?l?l?l?d?d' hashes.txt

# Maske karakterleri:
# ?l = küçük harf    ?u = büyük harf
# ?d = rakam         ?s = özel karakter
# ?a = hepsi         ?h = onaltılık (hex) küçük harf
```

### Harici (External) Mod

Kullanıcı tanımlı C fonksiyonları ve özel algoritmalar ile kırma.

```bash
john --external=Filter_Alpha hashes.txt
```

---

## 🔍 Hash Çıkarma Araçları (2john)

### Linux Parolaları

```bash
# passwd ve shadow dosyalarını birleştirin
unshadow /etc/passwd /etc/shadow > unshadowed.txt

# Birleştirilmiş dosyayı kırın
john unshadowed.txt
```

### Windows Hashleri

```bash
# SAM dosyasından veya NTDS.dit'ten çıkarılan hashler
# Format: user:id:LM_hash:NTLM_hash:::

# Metasploit hashdump çıktısı
meterpreter> hashdump
Administrator:500:aad3b435b51404ee:31d6cfe0d16ae931:::

# Dosyaya kaydedip kırın
john --format=nt hashes.txt
```

### Dosya Parolalarını Çıkarma (2john Araçları)

```bash
# Parolalı ZIP dosyaları
zip2john protected.zip > zip_hash.txt

# Parolalı RAR dosyaları
rar2john protected.rar > rar_hash.txt

# Parolalı PDF dosyaları
pdf2john protected.pdf > pdf_hash.txt

# Şifreli Office belgeleri (Word, Excel)
office2john document.docx > office_hash.txt

# Parolalı SSH anahtarları
ssh2john id_rsa > ssh_hash.txt

# 7-Zip arşivleri
7z2john archive.7z > 7z_hash.txt

# KeePass veritabanları
keepass2john database.kdbx > keepass_hash.txt
```

---

## 🔢 Yaygın Hash Türleri

### Hash Format Tablosu

| Hash Türü | Format Bayrağı | Örnek |
|-----------|----------------|-------|
| MD5 | `--format=raw-md5` | `5d41402abc4b2a76b9719d911017c592` |
| SHA1 | `--format=raw-sha1` | `aaf4c61ddcc5e8a2...` |
| SHA256 | `--format=raw-sha256` | `2cf24dba5fb0a30e...` |
| SHA512 | `--format=raw-sha512` | `cf83e1357eefb8bd...` |
| NTLM | `--format=nt` | `31d6cfe0d16ae931b73c59d7e0c089c0` |
| LM | `--format=lm` | `aad3b435b51404ee` |
| MySQL | `--format=mysql-sha1` | `*2470C0C06DEE42...` |
| bcrypt | `--format=bcrypt` | `$2a$10$N9qo8u...` |
| Linux SHA512 | `--format=sha512crypt` | `$6$rounds=5000$...` |
| Linux SHA256 | `--format=sha256crypt` | `$5$rounds=5000$...` |
| Linux MD5 | `--format=md5crypt` | `$1$salt$hash` |

### Desteklenen Formatları Listeleme

```bash
# Desteklenen tüm formatları listele
john --list=formats

# Belirli bir formatı ara
john --list=formats | grep -i ntlm
john --list=formats | grep -i sha

# Format sayısını öğren
john --list=formats | wc -l
```

### Hash Türünü Tanımlama

```bash
# hash-identifier aracını kullanın
hash-identifier

# Veya hashid ile
hashid -m '$6$salt$hash...'
```

---

## 🐧 Linux Parola Kırma

### Hash Dosyasını Hazırlama

```bash
# passwd ve shadow dosyalarını birleştirin
sudo unshadow /etc/passwd /etc/shadow > linux_hashes.txt

# Ya da doğrudan shadow dosyasından ilgili satırı alın
sudo cat /etc/shadow | grep username
```

### Linux Shadow Formatı

```
# /etc/shadow satır formatı:
# username:$id$salt$hash:...

# $1$ = MD5
# $5$ = SHA-256
# $6$ = SHA-512 (modern Linux dağıtımlarında en yaygın)
# $y$ = yescrypt (en güncel dağıtımlarda)
```

### Kırma İşlemi

```bash
# Otomatik tespit ile kır
john linux_hashes.txt

# Format belirterek kır
john --format=sha512crypt linux_hashes.txt

# Wordlist ile kır
john --wordlist=/usr/share/wordlists/rockyou.txt linux_hashes.txt

# Kırılan parolayı görüntüle
john --show linux_hashes.txt
```

---

## 🪟 Windows Parola Kırma

### Windows Hash Türleri

| Tür | Açıklama | Format |
|-----|----------|--------|
| **LM** | Eski sistemler (zayıf) | `--format=lm` |
| **NTLM** | Modern Windows yerel hashleri | `--format=nt` |
| **NTLMv2** | Ağ kimlik doğrulama yanıtı | `--format=netntlmv2` |

### NTLM Hashlerini Kırma

```bash
# NTLM hash satır formatı:
# user:rid:lm_hash:ntlm_hash:::

# NTLM kırma
john --format=nt windows_hashes.txt

# Wordlist ile
john --format=nt --wordlist=rockyou.txt windows_hashes.txt

# Kurallar ile
john --format=nt --wordlist=rockyou.txt --rules windows_hashes.txt
```

### Windows Hashlerini Elde Etme Yolları

```bash
# Metasploit oturumundan
meterpreter> hashdump

# Impacket secretsdump ile
secretsdump.py -sam sam -system system LOCAL

# pwdump ile
pwdump7.exe
```

---

## 📄 Dosya Parolası Kırma

### ZIP Dosyaları

```bash
# Hashi çıkar
zip2john protected.zip > zip.hash

# Parolayı kır
john --wordlist=rockyou.txt zip.hash

# Parolayı görüntüle
john --show zip.hash
```

### RAR Dosyaları

```bash
rar2john protected.rar > rar.hash
john --wordlist=rockyou.txt rar.hash
```

### PDF Dosyaları

```bash
pdf2john protected.pdf > pdf.hash
john --wordlist=rockyou.txt pdf.hash
```

### Office Belgeleri

```bash
# Word, Excel, PowerPoint
office2john document.docx > office.hash
john --wordlist=rockyou.txt office.hash
```

### SSH Özel Anahtarları

```bash
ssh2john id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### KeePass Veritabanları

```bash
keepass2john database.kdbx > keepass.hash
john --wordlist=rockyou.txt keepass.hash
```

---

## ⚙️ Kurallar & Türetme (Rules & Mangling)

### Kuralları Etkinleştirme

```bash
# Varsayılan kuralları uygula
john --wordlist=wordlist.txt --rules hashes.txt

# Belirli bir kural seti kullan
john --wordlist=wordlist.txt --rules=Single hashes.txt
john --wordlist=wordlist.txt --rules=Wordlist hashes.txt
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
john --wordlist=wordlist.txt --rules=KoreLogic hashes.txt
```

### Mevcut Kuralları Listeleme

```bash
john --list=rules
```

### Yaygın Kural Operatörleri

| Kural | Açıklama | Örnek |
|-------|----------|-------|
| `:` | Değişiklik yapma | password → password |
| `l` | Hepsini küçük harfe çevir | PassWord → password |
| `u` | Hepsini büyük harfe çevir | password → PASSWORD |
| `c` | Baş harfi büyüt | password → Password |
| `r` | Kelimeyi tersten yaz | password → drowssap |
| `d` | Kelimeyi iki kez yaz | pass → passpass |
| `$X` | Sona X karakterini ekle | pass → passX |
| `^X` | Başa X karakterini ekle | pass → Xpass |
| `sXY` | X harflerini Y ile değiştir | password → pa$$word |

### Özel Kural Tanımlama

```bash
# john.conf dosyasında [List.Rules:OzelKural] bloğu ekleyin:
[List.Rules:Custom]
:
l
u
c
$1
$!
$123
^2023
```

---

## 💾 Oturum Yönetimi

### İsimlendirilmiş Oturum Oluşturma

```bash
john --wordlist=rockyou.txt --session=mycrack hashes.txt
```

### Oturumu Devam Ettirme (Restore)

```bash
# Belirli bir oturumu sürdür
john --restore=mycrack

# Varsayılan oturumu sürdür
john --restore
```

### Durum Kontrolü

```bash
# Kırma işlemi sürerken terminalde herhangi bir tuşa basarak durumu görebilirsiniz
# Ya da .rec dosyasını inceleyin
cat ~/.john/mycrack.rec
```

### Pot Dosyası Yönetimi

```bash
# Varsayılan pot dosyası konumu
~/.john/john.pot

# Özel pot dosyası kullanma
john --pot=custom.pot hashes.txt

# Belirli pot dosyasına göre sonuçları listeleme
john --show --pot=custom.pot hashes.txt
```

---

## 🚀 Performans Optimizasyonu

### Çoklu İşlemci / Çekirdek Kullanımı (--fork)

```bash
# 4 ayrı CPU çekirdeğine bölerek kır
john --fork=4 --wordlist=rockyou.txt hashes.txt
```

### OpenCL ile GPU Kullanımı

```bash
# OpenCL destekli cihazları listele
john --list=opencl-devices

# GPU hızlandırmalı formatı seç
john --format=sha512crypt-opencl --wordlist=rockyou.txt hashes.txt
```

### Parola Uzunluğunu Sınırlandırma

```bash
# Uzunluk filtresi koyarak süreyi kısaltın
john --min-length=6 --max-length=12 --incremental hashes.txt
```

### Bellek Tüketimini Sınırlandırma

```bash
# Çok büyük listelerde bellek kullanımını optimize edin
john --mem-file-size=32 --wordlist=huge.txt hashes.txt
```

---

## 🎬 Gerçek Dünya Örnekleri

### Örnek 1: Linux /etc/shadow Dosyası
```bash
# Dosyaları birleştir
sudo unshadow /etc/passwd /etc/shadow > hashes.txt

# RockYou ile kır
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Sonuçları göster
john --show hashes.txt
```

### Örnek 2: Windows NTLM
```bash
# Hash çıktısını dosyaya yaz
echo "admin:500:aad3b435b51404ee:31d6cfe0d16ae931:::" > ntlm.txt

# NTLM formatı ile kır
john --format=nt --wordlist=rockyou.txt ntlm.txt
```

### Örnek 3: Parolalı ZIP Dosyası
```bash
zip2john secret.zip > zip.hash
john --wordlist=rockyou.txt zip.hash
john --show zip.hash
```

### Örnek 4: SSH Anahtar Parolası
```bash
ssh2john ~/.ssh/id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Örnek 5: Düz MD5 Hashleri
```bash
echo "5d41402abc4b2a76b9719d911017c592" > md5.txt
john --format=raw-md5 --wordlist=rockyou.txt md5.txt
```

### Örnek 6: bcrypt Web Parolaları
```bash
echo '$2a$10$N9qo8uLOickgx2ZMRZoMy...' > bcrypt.txt
john --format=bcrypt --wordlist=rockyou.txt bcrypt.txt
```

### Örnek 7: Türetme Kuralları ile Kırma
```bash
john --wordlist=small_wordlist.txt --rules=Jumbo hashes.txt
```

### Örnek 8: Artımlı (Incremental) Kaba Kuvvet
```bash
# 6-8 karakter uzunluğunda küçük harf ve rakamları dene
john --incremental=LowerNum --min-length=6 --max-length=8 hashes.txt
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar

| Görev | Komut |
|-------|-------|
| Temel kırma | `john hashes.txt` |
| Sözlük saldırısı | `john --wordlist=rockyou.txt hashes.txt` |
| Kurallarla sözlük | `john --wordlist=words.txt --rules hashes.txt` |
| Kırılanları göster | `john --show hashes.txt` |
| Format belirtme | `john --format=raw-md5 hashes.txt` |
| Kaba kuvvet | `john --incremental hashes.txt` |
| Oturumu devam ettirme | `john --restore=session` |
| Formatları listeleme | `john --list=formats` |

### 2john Araçları

| Araç | Kullanım Amacı |
|------|----------------|
| `unshadow` | Linux passwd ve shadow dosyaları |
| `zip2john` | Parolalı ZIP arşivleri |
| `rar2john` | Parolalı RAR arşivleri |
| `pdf2john` | Şifreli PDF dosyaları |
| `office2john` | Microsoft Office belgeleri |
| `ssh2john` | SSH özel anahtarları |
| `keepass2john` | KeePass kasa dosyaları |

### Yaygın Format Bayrakları

| Hash | Format Bayrağı |
|------|----------------|
| MD5 | `raw-md5` |
| SHA1 | `raw-sha1` |
| SHA256 | `raw-sha256` |
| NTLM | `nt` |
| Linux SHA512 | `sha512crypt` |
| bcrypt | `bcrypt` |
| MySQL | `mysql-sha1` |

---

## 💡 İpuçları ve En İyi Uygulamalar

### Verimlilik İpuçları

1. **Önce Single Modu Deneyin**
   ```bash
   john --single hashes.txt
   ```
   *Kullanıcı adı ve temel türevler genelde ilk birkaç saniyede birçok parolayı çözer.*

2. **Hedefe Yönelik Özel Sözlükler Kullanın**
   - Şirket adı, şehir, departman ve ilgili yıl kombinasyonlarını deneyin.

3. **Küçük Sözlüklerle Başlayın**
   - Önce ilk 1000 parola, ardından genişletilmiş listeler.

4. **Sıralı Saldırı Stratejisi İzleyin**
   ```bash
   john --single hashes.txt
   john --wordlist=rockyou.txt --rules hashes.txt
   john --incremental hashes.txt
   ```

---

## ⚠️ Yasal Uyarı

> **UYARI:** John the Ripper, yalnızca **yetkili ve yasal denetimlerde** kullanılması gereken güçlü bir güvenlik test aracıdır.
> 
> - ✅ Kendi parolalarınızın güvenliğini denetleyin
> - ✅ Yazılı izin alınmış sistemlerde test gerçekleştirin
> - ❌ İzniniz olmayan hiçbir parola hashini kırmayın
> 
> **Yetkisiz parola kırma eylemleri hukuken suç teşkil eder.**

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [John the Ripper Resmi Sitesi](https://www.openwall.com/john/)
- [John Jumbo GitHub](https://github.com/openwall/john)
- [John Wiki](https://openwall.info/wiki/john)

### Kelime Listeleri
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation Sözlüğü](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### İlgili Kılavuzlar
- [Hydra](../../Hydra/translations/README.tr.md)
- [Metasploit](../../Metasploit/translations/README.tr.md)
- [Nmap](../../Nmap/translations/README.tr.md)

---

<p align="center">
  <b>🔨 Parola Güvenliği Denetiminde Usta Olun!</b><br>
  <i>Her zaman sorumlu ve yasal sınırlar içinde test edin</i>
</p>
