[🇬🇧 English](../../README.md)

# ☁️ Cloudflare Atlama (Cloudflare Bypass) Başvuru Kılavuzu

```
   ██████╗██╗      ██████╗ ██╗   ██╗██████╗ ███████╗██╗      █████╗ ██████╗ ███████╗
  ██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗██╔════╝██║     ██╔══██╗██╔══██╗██╔════╝
  ██║     ██║     ██║   ██║██║   ██║██║  ██║█████╗  ██║     ███████║██████╔╝█████╗  
  ██║     ██║     ██║   ██║██║   ██║██║  ██║██╔══╝  ██║     ██╔══██║██╔══██╗██╔══╝  
  ╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝██║     ███████╗██║  ██║██║  ██║███████╗
   ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
                    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
                    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
                    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
                    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
                    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
                    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

## 🎯 Cloudflare Varlığını Tespit Etme

### HTTP Başlıkları (Headers)
```bash
curl -I https://target.com

# Şu başlıkları arayın:
cf-ray: XXXXX-YYY
server: cloudflare
cf-cache-status: DYNAMIC
cf-request-id: XXXX
```

### DNS Kayıtları
```bash
dig target.com

# Bilinen Cloudflare IP Blokları:
# 104.16.0.0/12
# 172.64.0.0/13
# 131.0.72.0/22
# 173.245.48.0/20
# 103.21.244.0/22
# 103.22.200.0/22
# 103.31.4.0/22
```

---

## 🌐 Gerçek Kaynak IP (Origin IP) Adresini Bulma

### 1. Geçmiş DNS Kayıtlarını İnceleme Araçları

| Servis | URL |
|--------|-----|
| SecurityTrails | https://securitytrails.com |
| ViewDNS | https://viewdns.info/iphistory |
| DNSDumpster | https://dnsdumpster.com |
| CrimeFlare | http://www.crimeflare.org:82/cfs.html |
| CloudFlair | GitHub aracı |

```bash
# CloudFlair kullanımı
python3 cloudflair.py target.com

# DNS Geçmişi ile Güvenlik Duvarı Atlama scripti
./bypass-firewalls-by-DNS-history.sh target.com
```

### 2. Alt Alan Adı Keşfi
```bash
# Çoğu zaman bazı alt alan adları Cloudflare arkasında unutulur ve gerçek IP'yi açığa çıkarır
subfinder -d target.com | httpx -ip

# Gerçek kaynak IP'yi barındırabilecek yaygın alt alan adları:
mail.target.com
ftp.target.com
cpanel.target.com
webmail.target.com
direct.target.com
origin.target.com
server.target.com
api.target.com
stage.target.com
dev.target.com
old.target.com
```

### 3. SSL Sertifikası Taraması
```bash
# Censys.io araması
# Sorgu: parsed.names: target.com AND NOT ip: 104.*

# Certificate Transparency (Sertifika Şeffaflığı)
curl -s "https://crt.sh/?q=%.target.com&output=json" | jq -r '.[].common_name' | sort -u

# Shodan araması
shodan search "ssl.cert.subject.cn:target.com"
```

### 4. E-posta Analizi
```bash
# Hedef alan adına ait var olmayan bir e-posta adresine posta gönderin
# Gelen teslim edilemedi (bounce-back) başlıklarını inceleyin

# Ya da bülten kaydı gibi bir e-posta tetikleyin
# Gelen e-postanın Received: başlıklarında gerçek kaynak IP'yi arayın

# İncelenecek başlıklar:
Received: from [ORIGIN_IP]
X-Originating-IP: [IP]
X-PM-Message-Id: (bazen kaynak sunucu adını/IP'sini içerir)
```

### 5. Favicon Hash Arama (Shodan)
```python
import mmh3
import requests
import codecs

# Favicon hash değerini hesaplama
response = requests.get('https://target.com/favicon.ico')
favicon = codecs.encode(response.content, 'base64')
hash = mmh3.hash(favicon)
print(f"Favicon hash: {hash}")

# Shodan üzerinde arama: http.favicon.hash:{hash}
```

```bash
# Shodan CLI ile arama
shodan search "http.favicon.hash:HASH_DEGERI"
```

### 6. Geçmiş WHOIS Kayıtları
```bash
# Eski IP adresleri için geçmiş WHOIS verilerini inceleyin
# whoxy.com
# domaintools.com
```

### 7. Web Sitesi Kaynak Kodu İncelemesi
```bash
# Kaynak kodda sabit kodlanmış (hardcoded) IP adreslerini arayın:
# - JavaScript dosyaları
# - HTML yorum satırları
# - API uç noktaları
# - Hata mesajları

curl -s https://target.com | grep -oE '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'

# JS dosyalarını kontrol etme
curl -s https://target.com/main.js | grep -oE 'https?://[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'
```

### 8. MX Kayıtları
```bash
dig MX target.com

# Posta sunucusu ana web sunucusu ile aynı IP üzerinde barınıyor olabilir
nslookup mail.target.com
```

### 9. SPF Kayıtları
```bash
dig TXT target.com | grep spf

# SPF kaydı doğrudan kaynak IP bloğunu içeriyor olabilir:
# v=spf1 ip4:1.2.3.4 include:_spf.google.com ~all
```

---

## 🔥 Doğrudan Bağlantı Kurma

### Bulunan IP'yi Test Etme
```bash
# Olası kaynak IP adresini Host başlığı ile test edin:
curl -k -H "Host: target.com" https://ORIGIN_IP/

# Veya /etc/hosts dosyasını düzenleyin:
echo "ORIGIN_IP target.com" >> /etc/hosts

# Ardından doğrudan alan adına gidin:
curl https://target.com
```

### Kaynak Sunucuyu Doğrulama
```bash
# İçeriğin birebir aynı olup olmadığını karşılaştırın
diff <(curl -s https://target.com) <(curl -sk -H "Host: target.com" https://ORIGIN_IP/)

# SSL sertifikasını kontrol edin
openssl s_client -connect ORIGIN_IP:443 -servername target.com
```

---

## 🛡️ Cloudflare WAF Atlama (Bypass)

### İstek Oranı Sınırlamasını (Rate Limiting) Atlama
```http
# Bu başlıkları sırayla döndürerek kullanın
X-Forwarded-For: RANDOM_IP
CF-Connecting-IP: RANDOM_IP
True-Client-IP: RANDOM_IP
X-Real-IP: RANDOM_IP
```

### Bot Korumasını Atlama
```bash
# Gerçek tarayıcı User-Agent başlığı kullanın
curl -A "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" https://target.com

# curl-impersonate ile tarayıcı TLS parmak izini taklit edin
curl_chrome116 https://target.com
```

### Turnstile / Tarayıcı Doğrulamasını Atlama
```bash
# Tarayıcı otomasyon araçları kullanın:
# - Puppeteer + stealth eklentisi
# - Playwright
# - undetected-chromedriver

# FlareSolverr (Docker ile çalıştırma)
docker run -p 8191:8191 flaresolverr/flaresolverr
```

---

## 🛠️ Araçlar

| Araç | Amaç |
|------|------|
| **CloudFlair** | Censys aracılığıyla kaynak IP bulma |
| **CrimeFlare** | Kaynak IP veritabanı |
| **Bypass-Firewalls-by-DNS-History** | DNS geçmişi tarama aracı |
| **FlareSolverr** | Cloudflare CAPTCHA/Challenge çözücü |
| **curl-impersonate** | Tarayıcı TLS parmak izi simülasyonu |
| **CloudBunny** | Cloudflare atlatma aracı |

### CloudFlair Kullanımı
```bash
git clone https://github.com/christophetd/CloudFlair
cd CloudFlair
pip install -r requirements.txt

# Censys API kimlik bilgilerini ayarlayın
export CENSYS_API_ID=YOUR_ID
export CENSYS_API_SECRET=YOUR_SECRET

python cloudflair.py target.com
```

---

## 📊 Hızlı Başvuru

### Kaynak IP Tespit Kontrol Listesi
```markdown
- [ ] DNS Geçmişi (SecurityTrails, ViewDNS)
- [ ] Alt Alan Adları (mail, ftp, cpanel, dev)
- [ ] SSL Sertifikaları (Censys, crt.sh)
- [ ] Shodan (Favicon hash, SSL)
- [ ] E-posta başlıkları (Received from)
- [ ] MX ve SPF kayıtları
- [ ] Kaynak kod ve JS analizi
- [ ] Geçmiş WHOIS kayıtları
- [ ] IP blok karşılaştırması (Cloudflare aralığı dışında kalanlar)
```

### Cloudflare IP Blokları
```
173.245.48.0/20
103.21.244.0/22
103.22.200.0/22
103.31.4.0/22
141.101.64.0/18
108.162.192.0/18
190.93.240.0/20
188.114.96.0/20
197.234.240.0/22
198.41.128.0/17
162.158.0.0/15
104.16.0.0/13
104.24.0.0/14
172.64.0.0/13
131.0.72.0/22
```

---

## 📚 Kaynaklar

- [CloudFlare IP Blokları](https://www.cloudflare.com/ips/)
- [CloudFlair Aracı](https://github.com/christophetd/CloudFlair)
- [CrimeFlare](http://www.crimeflare.org:82/cfs.html)
- [FlareSolverr](https://github.com/FlareSolverr/FlareSolverr)

---

<p align="center">
  <b>☁️ Gerçek Sunucu Kaynağını Bulun!</b><br>
  <i>Yalnızca yetkili ve yasal testler içindir!</i>
</p>
