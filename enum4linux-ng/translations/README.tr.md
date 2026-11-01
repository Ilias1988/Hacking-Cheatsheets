# enum4linux-ng Bilgi Notu (Cheatsheet)

[enum4linux-ng](https://github.com/cddmp/enum4linux-ng), eski `enum4linux` Perl aracının Python 3 ile sıfırdan yeniden yazılmış modern sürümüdür. SMB (Server Message Block), NetBIOS, RPC ve LDAP protokolleri üzerinden Windows ve Samba sunucularında kapsamlı bilgi toplama, kullanıcı listeleme, grup üyeleri, ağ paylaşımları, parola politikaları ve RID döngüsü (RID cycling) işlemlerini gerçekleştirir.

---

## İçindekiler

- [Eski Sürüme Göre Temel Farklar](#eski-sürüme-göre-temel-farklar)
- [Kurulum & Gereksinimler](#kurulum--gereksinimler)
- [Temel SMB & Domain Numaralandırma](#temel-smb--domain-numaralandırma)
- [Kimlik Doğrulama Modları (Null Session vs. Geçerli Kullanıcı)](#kimlik-doğrulama-modları-null-session-vs-geçerli-kullanıcı)
- [Hedefe Yönelik Numaralandırma Modülleri](#hedefe-yönelik-numaralandırma-modülleri)
- [RID Döngüsü & Kullanıcı Listesi Toplama](#rid-döngüsü--kullanıcı-listesi-toplama)
- [Raporlama & Dışa Aktarma (JSON, YAML, TXT)](#raporlama--dışa-aktarma-json-yaml-txt)
- [SMB & RPC Güvenlik Sıkılaştırma Önlemleri](#smb--rpc-güvenlik-sıkılaştırma-önlemleri)

---

## Eski Sürüme Göre Temel Farklar

- **Modern Python 3 Motoru:** `impacket` tabanlı çok iş parçacıklı mimari sayesinde eski Perl script kilitlenmelerini ortadan kaldırır.
- **Gelişmiş İşletim Sistemi Tespiti:** Windows Server (2012–2025), Windows 10/11 ve Samba sürümlerini tam doğrulukla belirler.
- **Yapılandırılmış Rapor Çıktısı:** Sonuçları doğrudan JSON ve YAML formatlarında üreterek SIEM ve script entegrasyonlarını kolaylaştırır.
- **Akıllı RID Döngüsü:** Otomatik aralık genişletme ve Administrator/Guest RID'lerini özel yakalama yeteneği.
- **Renkli Konsol Çıktısı:** Güvenlik açıkları ve zayıf ayarlar konsolda net renk kodlarıyla vurgulanır.

---

## Kurulum & Gereksinimler

```bash
# Depoyu klonlayın
git clone https://github.com/cddmp/enum4linux-ng.git
cd enum4linux-ng

# Python bağımlılıklarını kurun
pip3 install -r requirements.txt

# Sistemi genelinden çalıştırmak için sembolik bağ oluşturun
sudo ln -sf "$(pwd)/enum4linux-ng.py" /usr/local/bin/enum4linux-ng

# Kurulumu doğrulayın
enum4linux-ng -h
```

---

## Temel SMB & Domain Numaralandırma

### 1. Varsayılan Tam Tarama (`-A`)
Hedef sunucuya yönelik tüm standart ve saldırgan olmayan keşif kontrollerini yürütün:
```bash
enum4linux-ng -A 192.168.1.50
```
Bu komut sırasıyla şunları sorgular:
- NetBIOS bilgisayar adı, çalışma grubu ve domain adı.
- İşletim sistemi sürümü ve mimarisi.
- Paylaşılan klasörler ve okuma/yazma izinleri.
- Parola politikası (kilitlenme eşiği, minimum uzunluk, geçmiş).
- SAMR/RPC üzerinden kullanıcılar ve yerel gruplar.

---

## Kimlik Doğrulama Modları (Null Session vs. Geçerli Kullanıcı)

### 1. Anonim / Null Session Taraması (`-u '' -p ''`)
Hedef makinenin kimlik doğrulaması olmadan IPC$ oturumuna izin verip vermediğini test edin:
```bash
enum4linux-ng -u "" -p "" 192.168.1.50
```

### 2. Geçerli Kullanıcı Kimliği ile Tarama
Düşük yetkili bir etki alanı kullanıcısıyla tarama kapsamını maksimuma çıkarın:
```bash
enum4linux-ng -u "ayilmaz" -p "Parola123!" 192.168.1.50
```

### 3. Belirli Bir Domain Adı Belirtme (`-d`)
```bash
enum4linux-ng -u "ayilmaz" -p "Parola123!" -d "SIRKET.YEREL" 192.168.1.50
```

---

## Hedefe Yönelik Numaralandırma Modülleri

Ağ trafiğini ve alarm riskini azaltmak için yalnızca ilgili modülü çalıştırın:

### 1. Paylaşılan Klasörler & İzinler (`-S`)
Paylaşımları, açıklamalarını ve anonim okuma/yazma erişimlerini denetleyin:
```bash
enum4linux-ng -S 192.168.1.50
```

### 2. Parola Politikası Çıkarma (`-P`)
Parola püskürtme (password spraying) öncesi hesap kilitlenme eşiğini öğrenin:
```bash
enum4linux-ng -P 192.168.1.50
```
İncelenmesi gereken kritik değerler:
- **Minimum Password Length:** örn. 8 karakter.
- **Account Lockout Threshold:** örn. 5 deneme (hesapların kilitlenmemesi için bu sınıra dikkat edilmelidir).
- **Reset Lockout Counter:** Kilitlenme sıfırlama süresi (örn. 30 dakika).

### 3. Yalnızca Kullanıcılar ve Gruplar (`-U` ve `-G`)
```bash
# Kullanıcı listesini çek
enum4linux-ng -U 192.168.1.50

# Domain ve yerel grupları listele
enum4linux-ng -G 192.168.1.50
```

### 4. İşletim Sistemi ve NetBIOS Bilgisi (`-O` ve `-I`)
```bash
enum4linux-ng -O -I 192.168.1.50
```

---

## RID Döngüsü & Kullanıcı Listesi Toplama

Standart kullanıcı listeleme kısıtlandığında, SAMR sorguları üzerinden Göreli Tanımlayıcı (RID) brute-force döngüsü yürütün:

### 1. Varsayılan RID Döngüsü (500 - 1100 Aralığı)
```bash
enum4linux-ng -R 192.168.1.50
```

### 2. Özel RID Aralığı (Büyük Şirketler İçin 500 - 5000)
```bash
enum4linux-ng -r 500-5000 192.168.1.50
```

### 3. Temiz Kullanıcı Adı Listesi Ayıklama
Parola püskürtmede kullanılmak üzere bulunan tüm kullanıcı adlarını TXT dosyasına yazın:
```bash
enum4linux-ng -R 192.168.1.50 -oJ /tmp/enum_cikti
jq -r '.users[].username' /tmp/enum_cikti.json | sort -u > /tmp/kullanicilar.txt
```

---

## Raporlama & Dışa Aktarma (JSON, YAML, TXT)

Elde edilen bilgileri raporlama amacıyla diske kaydedin:

### 1. Tüm Formatlarda Eşzamanlı Kaydetme (`-oA`)
```bash
enum4linux-ng -A 192.168.1.50 -oA /tmp/hedef_rapor
```
Üretilen dosyalar:
- `/tmp/hedef_rapor.json`
- `/tmp/hedef_rapor.yaml`
- `/tmp/hedef_rapor.txt`

### 2. Belirli Bir Formatta Kaydetme
```bash
# Yalnızca JSON
enum4linux-ng -A 192.168.1.50 -oJ /tmp/hedef.json

# Yalnızca YAML
enum4linux-ng -A 192.168.1.50 -oY /tmp/hedef.yaml
```

---

## SMB & RPC Güvenlik Sıkılaştırma Önlemleri

| Saldırı Vektörü | Risk Nedeni | Savunma ve Önlem Tavsiyesi |
|---|---|---|
| **Null Session (Anonim IPC$)** | Kimlik doğrulamadan iç ağ topolojisini dökme | Kayıt Defterinde `RestrictNullSessAccess=1` ve SAM ayarında `RestrictAnonymous=2` yapılmalıdır. |
| **Aktif SMBv1 Protokolü** | EternalBlue (MS17-010) ve ortadaki adam saldırıları | SMBv1 protokolü tüm sistemlerde tamamen devre dışı bırakılmalıdır. |
| **Açık Paylaşım İzinleri** | Hassas dosya ve yedeklerin sızdırılması | Paylaşım izinleri denetlenmeli ve Access-Based Enumeration (ABE) aktif edilmelidir. |
| **RID Numaralandırması** | Tüm kullanıcı listesinin çıkarılması | Anonim kullanıcıların SAM hesaplarını sorgulaması Yerel Güvenlik İlkesi üzerinden kısıtlanmalıdır. |
