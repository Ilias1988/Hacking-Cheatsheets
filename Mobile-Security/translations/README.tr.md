# 📱 Mobil Güvenlik Kopya Kağıdı (Mobile Security Cheatsheets)

```
███╗   ███╗ ██████╗ ██████╗ ██╗██╗     ███████╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
████╗ ████║██╔═══██╗██╔══██╗██║██║     ██╔════╝    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
██╔████╔██║██║   ██║██████╔╝██║██║     █████╗      ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
██║╚██╔╝██║██║   ██║██╔══██╗██║██║     ██╔══╝      ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
██║ ╚═╝ ██║╚██████╔╝██████╔╝██║███████╗███████╗    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
╚═╝     ╚═╝ ╚═════╝ ╚═════╝ ╚═╝╚══════╝╚══════╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

---

## 🎯 Mobil Uygulama Güvenlik Testi

Mobil uygulama güvenlik testi, aşağıdaki alanlardaki zafiyetlerin tespit edilmesini kapsar:
- 📦 **Uygulama Paketi (Application Package)** - APK / IPA analizi
- 💾 **Veri Depolama (Data Storage)** - Güvensiz yerel veri depolama
- 🔐 **Kimlik Doğrulama (Authentication)** - Zayıf kimlik doğrulama mekanizmaları
- 🌐 **Ağ İletişimi (Network)** - Güvensiz ağ trafiği ve iletişim
- 🔑 **Kriptografi (Cryptography)** - Zayıf şifreleme algoritmaları
- 🛡️ **Platform Güvenliği (Platform)** - İşletim sistemine özgü güvenlik açıkları

---

## 📖 Mobil Güvenlik Kılavuzları

| Platform | Açıklama | Kılavuz |
|----------|----------|---------|
| **Android** | APK analizi, Frida, root tespiti atlatma | [📄 Görüntüle](./Android-Pentesting.tr.md) |
| **iOS** | IPA analizi, jailbreak, Objection, Keychain | [📄 Görüntüle](./iOS-Pentesting.tr.md) |

---

## 🛠️ Temel Mobil Güvenlik Araçları

| Araç | Platform | Amacı |
|------|----------|-------|
| **Frida** | Her İkisi | Dinamik enstrümantasyon (kod kancalama) |
| **Objection** | Her İkisi | Çalışma zamanı mobil güvenlik keşfi |
| **MobSF** | Her İkisi | Otomatik güvenlik analizi |
| **jadx** | Android | APK kaynak kodu dönüştürme (decompile) |
| **apktool** | Android | APK tersine mühendislik ve kaynak çıkarma |
| **Burp Suite** | Her İkisi | Ağ trafiği yakalama ve araya girme |
| **Ghidra** | Her İkisi | İkili (native library) analizi |
| **drozer** | Android | Android güvenlik değerlendirme çatısı |

### Hızlı Araç Kurulumu
```bash
# Frida
pip install frida-tools

# Objection
pip install objection

# MobSF (Docker)
docker pull opensecurity/mobile-security-framework-mobsf
docker run -p 8000:8000 opensecurity/mobile-security-framework-mobsf

# jadx
# https://github.com/skylot/jadx/releases adresinden indirin

# apktool
# https://ibotpeaches.github.io/Apktool/install/ adresinden indirin

# drozer
pip install drozer
```

---

## 📊 OWASP Mobile Top 10 (2024)

| # | Güvenlik Açığı | Açıklama |
|---|----------------|----------|
| M1 | Yanlış Kimlik Bilgisi Kullanımı (Improper Credential Usage) | Sabit kodlanmış kimlik bilgileri, güvensiz depolama |
| M2 | Yetersiz Tedarik Zinciri Güvenliği (Inadequate Supply Chain Security) | Zararlı SDK'ler, ele geçirilmiş kütüphaneler |
| M3 | Güvensiz Kimlik Doğrulama / Yetkilendirme | Zayıf doğrulama, bozuk erişim kontrolü |
| M4 | Yetersiz Girdi / Çıktı Doğrulama | Enjeksiyon, WebView'lerde XSS |
| M5 | Güvensiz İletişim (Insecure Communication) | TLS eksikliği, sertifika doğrulama hataları |
| M6 | Yetersiz Gizlilik Kontrolleri (Inadequate Privacy Controls) | PII sızıntısı, aşırı yetki istekleri |
| M7 | Yetersiz İkili Koruması (Insufficient Binary Protections) | Kod karartma (obfuscation) olmaması, kurcalama |
| M8 | Güvenlik Yapılandırma Hataları (Security Misconfiguration) | Hata ayıklama açık bırakılmış, güvensiz varsayılanlar |
| M9 | Güvensiz Veri Depolama (Insecure Data Storage) | Düz metin depolama, sistem logları |
| M10 | Yetersiz Kriptografi (Insufficient Cryptography) | Zayıf algoritmalar, sabit şifreleme anahtarları |

---

## 🔗 Hızlı Başvuru: Frida Komutları

```bash
# Çalışan süreçleri listeleme
frida-ps -U

# Sürece bağlanma (attach)
frida -U -f com.app.package

# Betik ile süreci başlatma (spawn)
frida -U -f com.app.package -l script.js --no-pause

# Objection ile keşif başlatma
objection -g com.app.package explore
```

---

## 📚 Kaynaklar

- [OWASP Mobile Security](https://owasp.org/www-project-mobile-security/)
- [OWASP MASTG](https://mas.owasp.org/MASTG/)
- [Frida Documentation](https://frida.re/docs/)
- [HackTricks Mobile](https://book.hacktricks.xyz/mobile-pentesting)

---

<p align="center">
  <b>📱 Mobil Uygulamaları Sorumlu Bir Şekilde Test Edin!</b>
</p>
