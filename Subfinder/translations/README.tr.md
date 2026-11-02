[🇬🇧 English](../../README.md)

# 🔍 Subfinder - Kapsamlı Başvuru Kılavuzu

```
  ____        _     __ _           _           
 / ___| _   _| |__ / _(_)_ __   __| | ___ _ __ 
 \___ \| | | | '_ \ |_| | '_ \ / _` |/ _ \ '__|
  ___) | |_| | |_) |  _| | | | | (_| |  __/ |   
 |____/ \__,_|_.__/|_| |_|_| |_|\__,_|\___|_|   
                                                
   Fast Subdomain Discovery Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Subfinder-Subdomain_Discovery-red?style=for-the-badge" alt="Subfinder">
  <img src="https://img.shields.io/badge/Passive-Recon-orange?style=for-the-badge" alt="Passive">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🌐 ProjectDiscovery tarafından geliştirilen hızlı pasif alt alan adı (subdomain) keşif aracı</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Temel Sözdizimi](#-temel-sözdizimi)
- [Alt Alan Adı Keşfi](#-alt-alan-adı-keşfi)
- [Veri Kaynakları](#-veri-kaynakları)
- [API Yapılandırması](#-api-yapılandırması)
- [Çıktı Seçenekleri](#-çıktı-seçenekleri)
- [Gelişmiş Özellikler](#-gelişmiş-özellikler)
- [Bug Bounty İş Akışı](#-bug-bounty-iş-akışı)
- [Gerçek Dünya Örnekleri](#-gerçek-dünya-örnekleri)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [İpuçları ve En İyi Pratikler](#-ipuçları-ve-en-iyi-pratikler)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**Subfinder**, arama motorları, paste siteleri ve siber tehdit istihbarat platformları dahil olmak üzere çok sayıda kaynaktan yararlanarak alt alan adlarını (subdomain) tespit eden hızlı ve pasif bir keşif aracıdır.

### Temel Özellikler

| Özellik | Açıklama |
|---------|----------|
| **Hızlı** | Go tabanlı, yüksek eşzamanlılık (concurrency) |
| **Pasif** | Hedefle doğrudan etkileşime girmez |
| **Çoklu Kaynak** | 40'tan fazla veri kaynağı |
| **API Entegrasyonu** | Ücretli/özel kaynak desteği |
| **Pipeline Dostu** | Diğer araçlarla boru hattı (pipe) şeklinde çalışabilir |
| **JSON Çıktısı** | Yapılandırılmış çıktı desteği |

### Subfinder ve Diğer Araçların Karşılaştırması

| Özellik | Subfinder | Amass | Assetfinder |
|---------|-----------|-------|-------------|
| **Hız** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| **Kaynaklar** | 40+ | 50+ | 10+ |
| **Aktif Tarama** | ❌ Hayır | ✅ Evet | ❌ Hayır |
| **API Desteği** | ✅ Evet | ✅ Evet | ❌ Hayır |
| **Kullanım Kolaylığı** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |

---

## 📥 Kurulum

### Go ile Kurulum
```bash
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
```

### Kali Linux
```bash
sudo apt install subfinder
```

### İkili (Binary) İndirme
```bash
# Sürümler sayfasından indirin
https://github.com/projectdiscovery/subfinder/releases

# Linux
wget https://github.com/projectdiscovery/subfinder/releases/download/v2.x.x/subfinder_2.x.x_linux_amd64.zip
unzip subfinder_2.x.x_linux_amd64.zip
sudo mv subfinder /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/subfinder:latest
docker run projectdiscovery/subfinder -d example.com
```

### Kurulumu Doğrulama
```bash
subfinder -version
subfinder -h
```

---

## ⌨️ Temel Sözdizimi

### Genel Sözdizimi
```bash
subfinder [options] -d <domain>
```

### Temel Seçenekler

| Seçenek | Açıklama |
|---------|----------|
| `-d` | Hedef alan adı |
| `-dL` | Alan adları listesini içeren dosya |
| `-o` | Çıktı dosyası |
| `-oJ` | JSON çıktısı |
| `-silent` | Sessiz mod (yalnızca alt alan adları) |
| `-v` | Ayrıntılı (verbose) çıktı |
| `-t` | İş parçacığı (thread) sayısı |
| `-all` | Tüm kaynakları kullan |

### Hızlı Başlangıç
```bash
# Temel numaralandırma
subfinder -d example.com

# Sessiz çıktı (yalnızca temiz liste)
subfinder -d example.com -silent

# Dosyaya kaydetme
subfinder -d example.com -o subdomains.txt

# Birden fazla alan adı
subfinder -dL domains.txt -o all-subs.txt
```

---

## 🔍 Alt Alan Adı Keşfi

### Tek Alan Adı

```bash
# Temel tarama
subfinder -d target.com

# Ayrıntılı çıktı
subfinder -d target.com -v

# Sessiz mod (yalnızca alt alan adları)
subfinder -d target.com -silent

# Kaynakları göster
subfinder -d target.com -cs
```

### Birden Fazla Alan Adı

```bash
# Dosyadan okuma
subfinder -dL domains.txt

# Çıktıyı dosyaya aktarma
subfinder -dL domains.txt -o all_subdomains.txt

# Boru hattı (pipe) için sessiz mod
subfinder -dL domains.txt -silent | httpx
```

### Özyinelemeli (Recursive) Numaralandırma

```bash
# Özyinelemeyi etkinleştir (alt-alt alan adlarını bulur)
subfinder -d target.com -recursive

# Tüm kaynaklarla özyinelemeli tarama
subfinder -d target.com -recursive -all
```

---

## 📊 Veri Kaynakları

### Ücretsiz Kaynaklar (Varsayılan)

Subfinder varsayılan olarak bu kaynakları API anahtarı gerektirmeden kullanır:
- Alienvault
- AnubisDB
- Crtsh
- Hackertarget
- Rapiddns
- Sublist3r
- ThreatCrowd
- URLScan
- Wayback Machine

### Premium Kaynaklar (API Anahtarı Gerekir)

Daha kapsamlı ve zengin sonuçlar için bu kaynaklarda API anahtarı yapılandırılmalıdır:
- **Shodan**
- **SecurityTrails**
- **VirusTotal**
- **Censys**
- **Chaos**
- **GitHub**
- **PassiveTotal**
- **Binaryedge**
- **WhoisXML**
- **ZoomEye**

### Kaynak Yönetimi

```bash
# Tüm kaynakları listele
subfinder -ls

# Yalnızca belirli kaynakları kullan
subfinder -d target.com -sources crtsh,hackertarget,alienvault

# Belirli kaynakları hariç tut
subfinder -d target.com -es shodan,securitytrails

# Tüm kaynakları kullan (yavaş olanlar dahil)
subfinder -d target.com -all

# Her bir alt alan adının hangi kaynaktan bulunduğunu göster
subfinder -d target.com -cs
```

---

## 🔑 API Yapılandırması

### Yapılandırma Dosyası Konumu

```bash
# Varsayılan konum
~/.config/subfinder/provider-config.yaml

# Veya özel bir yol belirtin
subfinder -d target.com -pc /path/to/config.yaml
```

### Yapılandırma Dosyası Şablonu

```yaml
# ~/.config/subfinder/provider-config.yaml

binaryedge:
  - your_api_key_here
censys:
  - your_api_id:your_api_secret
certspotter: []
chaos:
  - your_chaos_key
chinaz: []
dnsdb: []
fofa:
  - your_email:your_key
fullhunt: []
github:
  - your_github_token
hunter:
  - your_hunter_key
intelx:
  - your_intelx_key
passivetotal:
  - your_username:your_key
securitytrails:
  - your_security_trails_key
shodan:
  - your_shodan_key
virustotal:
  - your_virustotal_key
whoisxmlapi:
  - your_whoisxml_key
zoomeye:
  - your_zoomeye_key
```

### API Anahtarları Edinme

| Kaynak | Ücretsiz Katman | Bağlantı |
|--------|-----------------|----------|
| **VirusTotal** | ✅ Evet | https://www.virustotal.com |
| **SecurityTrails** | ✅ Evet (sınırlı) | https://securitytrails.com |
| **Shodan** | ✅ Evet (sınırlı) | https://shodan.io |
| **Censys** | ✅ Evet | https://censys.io |
| **Chaos** | ✅ Ücretsiz (sınırlı) | https://chaos.projectdiscovery.io |
| **GitHub** | ✅ Ücretsiz | https://github.com/settings/tokens |

### Önerilen Ücretsiz Yapılandırma

```yaml
# Bug bounty için önerilen asgari yapılandırma
virustotal:
  - your_free_key
github:
  - your_github_token
chaos:
  - your_chaos_key
```

---

## 📤 Çıktı Seçenekleri

### Çıktı Formatları

```bash
# Düz metin (varsayılan)
subfinder -d target.com -o subs.txt

# JSON çıktısı
subfinder -d target.com -oJ -o subs.json

# JSON lines çıktısı
subfinder -d target.com -oJL -o subs.jsonl

# CSV formatı
subfinder -d target.com -oD -o subs.csv
```

### Çıktı Filtreleme

```bash
# Sessiz mod (temiz çıktı)
subfinder -d target.com -silent

# Yalnızca yeni alt alan adlarını göster (bilinenleri hariç tut)
subfinder -d target.com -exclude-subdomains known.txt

# Renksiz çıktı
subfinder -d target.com -nc
```

### Çıktıyı Başka Araçlara İletme (Piping)

```bash
# httpx aracına aktarma
subfinder -d target.com -silent | httpx -silent

# nuclei aracına aktarma
subfinder -d target.com -silent | httpx -silent | nuclei

# Kaydetme ve işleme
subfinder -d target.com -silent | tee subs.txt | httpx
```

---

## ⚡ Gelişmiş Özellikler

### İş Parçacığı (Threading) ve Performans

```bash
# İş parçacığı sayısını belirleme (varsayılan: 10)
subfinder -d target.com -t 50

# İstek oranı sınırlaması (rate limiting)
subfinder -d target.com -rate-limit 100

# Zaman aşımı süresi (timeout)
subfinder -d target.com -timeout 30
```

### Eşleştirme ve Filtreleme

```bash
# Belirli kalıpları eşleştirme
subfinder -d target.com -match "api,dev,stage"

# Kalıpları filtreleme / hariç tutma
subfinder -d target.com -filter "www,mail"
```

### Çözümleyici (Resolver) Seçenekleri

```bash
# Özel DNS çözümleyicileri kullanma
subfinder -d target.com -rL resolvers.txt

# Alt alan adlarını çözümleme
subfinder -d target.com -nW
```

### Proxy Desteği

```bash
# HTTP proxy
subfinder -d target.com -proxy http://127.0.0.1:8080

# SOCKS proxy
subfinder -d target.com -proxy socks5://127.0.0.1:9050
```

---

## 🔄 Bug Bounty İş Akışı

### Eksiksiz Keşif Boru Hattı (Pipeline)

```bash
# Adım 1: Alt alan adı numaralandırması
subfinder -d target.com -all -o subs.txt

# Adım 2: Canlı sunucuları kontrol etme
cat subs.txt | httpx -o live.txt

# Adım 3: Nuclei ile tarama
nuclei -l live.txt -severity critical,high -o vulns.txt

# Tek satırda zincirleme komut
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Çoklu Alan Adı İş Akışı

```bash
# Program kapsamındaki tüm alan adlarını numaralandırma
subfinder -dL scope.txt -all -o all-subs.txt

# Sonuçları işleme
cat all-subs.txt | httpx -silent | nuclei
```

### Sürekli İzleme (Continuous Monitoring)

```bash
# Referans tabanını kaydetme
subfinder -d target.com -silent > baseline.txt

# Daha sonra yeni alt alan adlarını tespit etme
subfinder -d target.com -silent > current.txt
diff baseline.txt current.txt | grep ">" | cut -d" " -f2 > new-subs.txt
```

### Diğer Araçlarla Entegrasyon

```bash
# httpx ile (canlı sunucular)
subfinder -d target.com -silent | httpx

# ffuf ile (dizin fuzzing'i)
subfinder -d target.com -silent | httpx -silent | \
  xargs -I {} ffuf -u {}/FUZZ -w wordlist.txt

# nmap ile (port taraması)
subfinder -d target.com -silent | dnsx -a -silent | nmap -iL -

# Eksiksiz keşif zinciri
subfinder -d target.com -silent | \
  httpx -title -status-code -tech-detect -silent | \
  tee recon-results.txt
```

---

## 🎬 Gerçek Dünya Örnekleri

### Örnek 1: Temel Numaralandırma
```bash
subfinder -d hackerone.com -silent
```

### Örnek 2: Tüm Kaynaklarla Tarama
```bash
subfinder -d target.com -all -v
```

### Örnek 3: Sonuçları Kaydetme
```bash
subfinder -d target.com -o target-subs.txt
```

### Örnek 4: Çoklu Alan Adı Girişi
```bash
echo -e "target1.com\ntarget2.com" | subfinder -silent
```

### Örnek 5: Kaynakları Gösterme
```bash
subfinder -d target.com -cs
```

### Örnek 6: JSON Formatında Çıktı
```bash
subfinder -d target.com -oJ -o results.json
```

### Örnek 7: Proxy ile Çalıştırma
```bash
subfinder -d target.com -proxy http://127.0.0.1:8080
```

### Örnek 8: Yüksek Performanslı Tarama
```bash
subfinder -d target.com -all -t 100 -o subs.txt
```

### Örnek 9: Tam Kapsamlı Bug Bounty Boru Hattı
```bash
subfinder -d target.com -all -silent | \
  httpx -silent -status-code -title | \
  tee live-hosts.txt | \
  nuclei -severity critical,high
```

### Örnek 10: Özyinelemeli Keşif
```bash
subfinder -d target.com -recursive -all -silent
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar

| Görev | Komut |
|-------|-------|
| Temel tarama | `subfinder -d target.com` |
| Sessiz çıktı | `subfinder -d target.com -silent` |
| Dosyaya kaydetme | `subfinder -d target.com -o subs.txt` |
| Tüm kaynaklar | `subfinder -d target.com -all` |
| Kaynakları göster | `subfinder -d target.com -cs` |
| Birden fazla alan adı | `subfinder -dL domains.txt` |
| JSON çıktısı | `subfinder -d target.com -oJ` |
| Kaynakları listele | `subfinder -ls` |

### Sık Kullanılan Seçenekler

| Seçenek | Açıklama |
|---------|----------|
| `-d` | Hedef alan adı |
| `-dL` | Alan adı listesi dosyası |
| `-o` | Çıktı dosyası |
| `-oJ` | JSON çıktısı |
| `-silent` | Sessiz mod |
| `-all` | Tüm kaynakları kullan |
| `-cs` | Her alt alan adı için kaynağı göster |
| `-t` | İş parçacığı sayısı |
| `-recursive` | Özyinelemeli numaralandırma |

### Yaygın Boru Hatları (Pipelines)

| Boru Hattı | Komut |
|------------|-------|
| Canlı sunucular | `subfinder -d target.com -silent \| httpx` |
| Tam keşif | `subfinder -d target.com \| httpx \| nuclei` |
| Port taraması | `subfinder -d target.com -silent \| dnsx -a \| nmap -iL -` |

---

## 💡 İpuçları ve En İyi Pratikler

### Bug Bounty İpuçları

1. **API Anahtarlarını Yapılandırın**
   ```bash
   # API'ler ile çok daha kapsamlı sonuçlar elde edin
   vim ~/.config/subfinder/provider-config.yaml
   ```

2. **Eksiksizlik İçin Tüm Kaynakları Kullanın**
   ```bash
   subfinder -d target.com -all
   ```

3. **Diğer Araçlarla Birleştirin**
   ```bash
   subfinder -d target.com -silent | httpx | nuclei
   ```

4. **Yeni Alt Alan Adlarını Sürekli İzleyin**
   ```bash
   # Yeni varlıklar için günlük kontrol
   subfinder -d target.com -silent > today.txt
   ```

### Performans İpuçları

```bash
# Yüksek iş parçacığı sayısı
subfinder -d target.com -t 100 -all

# Yavaş kaynaklar için zaman aşımı belirleme
subfinder -d target.com -timeout 60
```

---

## ⚠️ Yasal Uyarı

> **UYARI:** Subfinder pasif keşif gerçekleştirir ve doğrudan hedef sunucularla bağlantı kurmaz. Ancak boru hattınızda sonraki adımlarda yer alan araçlar doğrudan etkileşime girebilir.
> 
> - ✅ Yalnızca izin verilen sistemlerde kullanın
> - ✅ Kapsam dahilindeki (in-scope) bug bounty hedeflerinde çalışın
> - ✅ Kendi alan adlarınız üzerinde test yapın
> - ❌ İzniniz olmayan hedefleri asla taramayın
> - ❌ Program kurallarına ve kapsamına titizlikle uyun
> 
> **Her zaman yetkiniz ve izniniz olduğundan emin olun.**

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [Subfinder GitHub](https://github.com/projectdiscovery/subfinder)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Dokümantasyon](https://docs.projectdiscovery.io/tools/subfinder/)

### İlgili Başvuru Kılavuzları
- [Nuclei](../../Nuclei/README.md)
- [ffuf](../../ffuf/README.md)
- [Nmap](../../Nmap/README.md)

---

<p align="center">
  <b>🔍 Tüm Alt Alan Adlarını Keşfedin!</b><br>
  <i>Bug bounty keşif aşamasının ilk adımı</i>
</p>
