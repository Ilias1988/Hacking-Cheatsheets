# wafw00f Bilgi Notu (Cheatsheet)

[wafw00f](https://github.com/EnableSecurity/wafw00f), hedef web uygulamalarını koruyan Web Uygulama Güvenlik Duvarlarını (WAF) parmak izi analiziyle tespit eden popüler ve açık kaynaklı bir komut satırı aracıdır. Normal ve kışkırtıcı (provocative) HTTP istekleri göndererek dönen başlıkları, durum kodlarını, çerezleri ve sayfa gövdelerini inceler ve 150'den fazla ticari ve açık kaynaklı WAF çözümünü ayırt eder.

---

## İçindekiler

- [Neden WAF Tespiti Yapılmalıdır?](#neden-waf-tespiti-yapılmalıdır)
- [Kurulum](#kurulum)
- [Temel & Hedef Alan Adı Taraması](#temel--hedef-alan-adı-taraması)
- [Tüm WAF'ları Kapsamlı Arama (`-a`)](#tüm-wafları-kapsamlı-arama--a)
- [Dosyadan Toplu Hedef Taraması (`-i`)](#dosyadan-toplu-hedef-taraması--i)
- [Proxy & Tor Ağı Üzerinden Yönlendirme (`-p`)](#proxy--tor-ağı-üzerinden-yönlendirme--p)
- [Dışa Aktarma Formatları (JSON, CSV, TXT)](#dışa-aktarma-formatları-json-csv-txt)
- [Desteklenen WAF İmzalarını Listeleme (`-l`)](#desteklenen-waf-i̇mzalarını-listeleme--l)
- [Bug Bounty & Sızma Testi Entegrasyonu](#bug-bounty--sızma-testi-entegrasyonu)

---

## Neden WAF Tespiti Yapılmalıdır?

WAF arkasında çalışan bir web uygulamasına doğrudan saldırı yükleri (SQLi, XSS, RCE) göndermek, IP adresinizin anında bloklanmasına veya güvenlik açığı olmasına rağmen yanlış negatif (false-negative) sonuç almanıza neden olur. WAF türünü önceden bilmek şu avantajları sağlar:
- **Hedefe Yönelik Atlatma (Bypass):** Tespit edilen WAF'a özel kaçırma teknikleri seçilir (örn. ModSecurity için Unicode normalizasyonu, Cloudflare için chunked transfer encoding).
- **Gerçek Kaynak IP Avı:** Eğer Cloudflare veya Akamai tespit edilirse, doğrudan web sunucusunun arka plandaki gerçek IP'si (Origin IP) araştırılır.
- **Hız ve İstek Limiti Ayarı:** Otomatik tarayıcıların (sqlmap, ffuf) istek hızları WAF engelleme eşiğinin altında tutulur.

---

## Kurulum

```bash
# Debian / Ubuntu / Kali Linux
sudo apt-get update && sudo apt-get install -y wafw00f

# Pipx ile izole kurulum (Önerilen)
pipx install wafw00f

# Python pip ile kurulum
python3 -m pip install --user wafw00f

# Kurulumu doğrulayın
wafw00f --version
```

---

## Temel & Hedef Alan Adı Taraması

### 1. Tekil Alan Adını Tarama
```bash
wafw00f https://hedef.com
```
*wafw00f önce standart bir HTTP isteği yapar, ardından güvenlik filtrelerini tetiklemek için özel payload dizgileri göndererek yanıt başlıkları ve hata sayfalarındaki imzaları analiz eder.*

### 2. Ayrıntılı (Verbose) Çıktı Modu (`-v` / `-vv`)
Eşleşen HTTP başlıklarını ve tetiklenen kuralları inceleyin:
```bash
wafw00f https://hedef.com -v
```

### 3. Yönlendirmeleri Takip Etmeyi Kapatma (`-r`)
301/302 HTTP yönlendirmelerini takip etmeyerek ters vekil (reverse proxy) ile backend uygulamasını ayrıştırın:
```bash
wafw00f https://hedef.com -r
```

---

## Tüm WAF'ları Kapsamlı Arama (`-a`)

Varsayılan olarak wafw00f ilk bulduğu WAF eşleşmesinde taramayı durdurur. `-a` parametresi ise tüm imzaları sonuna kadar dener (örn. Cloudflare arkasında AWS WAF barındıran katmanlı mimariler için gereklidir):

```bash
wafw00f https://hedef.com -a
```

---

## Dosyadan Toplu Hedef Taraması (`-i`)

`subfinder` veya `amass` ile toplanan alt alan adları listesini topluca tarayın:

```bash
wafw00f -i /path/to/alan_adlari.txt -o /tmp/waf_sonuclari.txt
```

---

## Proxy & Tor Ağı Üzerinden Yönlendirme (`-p`)

IP adresinizin kara listeye girmesini önlemek veya trafiği Burp Suite üzerinden izlemek için:

### 1. Burp Suite Proxy'si Üzerinden Geçirme
```bash
wafw00f https://hedef.com -p http://127.0.0.1:8080
```

### 2. Yerel Tor SOCKS5 Proxy'si Kullanma
```bash
wafw00f https://hedef.com -p socks5://127.0.0.1:9050
```

---

## Dışa Aktarma Formatları (JSON, CSV, TXT)

Sonuçları raporlamak veya sonraki araçlara iletmek için yapılandırılmış formatlarda kaydedin:

### 1. JSON Formatında Dışa Aktarma
```bash
wafw00f https://hedef.com -o sonuclar.json --format json
```

### 2. CSV Formatında Kaydetme
```bash
wafw00f -i hedefler.txt -o sonuclar.csv --format csv
```

---

## Desteklenen WAF İmzalarını Listeleme (`-l`)

Desteklenen 150+ WAF ürününü terminalde listeleyin:
```bash
wafw00f -l
```
Öne çıkan WAF çözümleri:
- **Bulut Çözümleri:** Cloudflare, AWS WAF, Akamai, Microsoft Azure Web Application Firewall, Google Cloud Armor.
- **Kurumsal Donanımlar:** F5 BIG-IP ASM, Imperva SecureSphere, Barracuda, Fortinet FortiWeb, Citrix NetScaler.
- **Açık Kaynak:** ModSecurity, NAXSI, Shadow Daemon, OpenResty.

---

## Bug Bounty & Sızma Testi Entegrasyonu

wafw00f çıktısına göre sonraki exploit aracını otomatik yapılandıran örnek bash boru hattı:

```bash
#!/usr/bin/env bash
HEDEF="https://app.hedef.corp"

echo "[*] $HEDEF icin WAF analizi yapiliyor..."
WAF_VARMI=$(wafw00f "$HEDEF" -o - --format json | jq -r '.[0].detected')

if [ "$WAF_VARMI" == "true" ]; then
    WAF_ADI=$(wafw00f "$HEDEF" -o - --format json | jq -r '.[0].firewall')
    echo "[!] Aktif WAF tespit edildi: $WAF_ADI"
    echo "[+] sqlmap ozel tamper scriptleri ile calistiriliyor..."
    if [[ "$WAF_ADI" =~ "Cloudflare" ]]; then
        sqlmap -u "$HEDEF/item?id=1" --tamper=between,randomcase,space2comment
    fi
else
    echo "[+] Aktif WAF bulunamadi. Standart taramaya geciliyor."
    sqlmap -u "$HEDEF/item?id=1" --batch
fi
```
