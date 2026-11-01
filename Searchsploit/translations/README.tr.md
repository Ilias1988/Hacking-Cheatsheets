# Searchsploit Bilgi Notu (Cheatsheet)

[Searchsploit](https://www.exploit-db.com/searchsploit), [Exploit-Database](https://www.exploit-db.com/) (Exploit-DB) arşivinin resmi komut satırı arama aracıdır. Güvenlik uzmanları ve sızma testi araştırmacılarının internet bağlantısına ihtiyaç duymadan, yerel disk üzerinde saklanan on binlerce exploit, kabuk kodu (shellcode) ve güvenlik makalesi arasında anlık arama yapmasına ve PoC kodlarını çalışma dizinine kopyalamasına olanak tanır.

---

## İçindekiler

- [Genel Bakış & Çalışma Mantığı](#genel-bakış--çalışma-mantığı)
- [Kurulum & Veritabanı Güncelleme](#kurulum--veritabanı-güncelleme)
- [Temel & İleri Düzey Exploit Arama](#temel--ileri-düzey-exploit-arama)
- [Exploit İnceleme & Kopyalama (`-m` / `-p` / `-x`)](#exploit-i̇nceleme--kopyalama--m---p---x)
- [Platform ve Türe Göre Filtreleme](#platform-ve-türe-göre-filtreleme)
- [Nmap XML Tarama Entegrasyonu](#nmap-xml-tarama-entegrasyonu)
- [JSON Çıktısı & Betik Otomasyonu](#json-çıktısı--betik-otomasyonu)
- [Sızma Testlerinde En İyi Pratikler](#sızma-testlerinde-en-i̇yi-pratikler)

---

## Genel Bakış & Çalışma Mantığı

Searchsploit, `/usr/share/exploitdb/` dizini altında barındırılan yerel Git klonu üzerinde çalışır.
- **Sıfır Ağ Trafiği:** Arama işlemleri hedef ağa veya dış internete paket göndermez, gizli ve izole testler için idealdir.
- **Çevrim Dışı Güvenilirlik:** İzole edilmiş kapalı ağlarda (air-gapped) ve kısıtlı iç ağ testlerinde eksiksiz çalışır.
- **Tek Tuşla Aynalama (Mirroring):** Bulunan exploit dosyalarını anında yerel çalışma dizinine kopyalar.

---

## Kurulum & Veritabanı Güncelleme

### Debian / Ubuntu / Kali Linux / Parrot OS
```bash
# Kali ve Parrot sistemlerinde varsayılan olarak yüklüdür
sudo apt-get update && sudo apt-get install -y exploitdb

# Yerel exploit veritabanını en son sürüme güncelleyin
searchsploit -u
```

### Git Deposundan Kurulum (Tüm Linux Dağıtımları)
```bash
git clone https://gitlab.com/exploit-database/exploitdb.git /opt/exploitdb
sudo ln -sf /opt/exploitdb/searchsploit /usr/local/bin/searchsploit

# Sürüm ve durumu kontrol edin
searchsploit -v
```

---

## Temel & İleri Düzey Exploit Arama

### 1. Anahtar Kelime ile Arama
Hedef sistemde tespit edilen servis, yazılım adı ve sürüm numarasını aratın:
```bash
searchsploit apache 2.4.49
searchsploit openssh 7.2
searchsploit wordpress core 5.8
```

### 2. Yalnızca Başlıkta Arama Yapma (`-t`)
Açıklamalardaki gereksiz eşleşmeleri eleyerek yalnızca exploit başlıklarında arama yapın:
```bash
searchsploit -t "Privilege Escalation" linux kernel
searchsploit -t "Remote Code Execution" smb
```

### 3. Birebir Tam Eşleşme (`-e`)
```bash
searchsploit -e "Apache Struts"
```

### 4. CVE Numarasına Göre Arama
Common Vulnerabilities and Exposures (CVE) koduna karşılık gelen exploitleri getirin:
```bash
searchsploit --cve 2021-44228
searchsploit --cve 2017-0144
```

### 5. İstenmeyen Sonuçları Dışlama (`--exclude`)
Uzaktan kod yürütme (RCE) ararken servis dışı bırakma (DoS) exploitlerini eleyin:
```bash
searchsploit linux kernel 5.4 --exclude="Denial of Service"
```

---

## Exploit İnceleme & Kopyalama (`-m` / `-p` / `-x`)

Orijinal `/usr/share/exploitdb/` dizinindeki dosyaları doğrudan düzenlemek yerine Searchsploit'in kopyalama işlevini kullanın.

### 1. Exploiti Çalışma Dizinine Kopyalama (`-m`)
Hedef exploit dosyasını (Python, C, Ruby, bash) doğrudan mevcut klasörünüze kopyalayın:
```bash
# EDB-ID numarası ile kopyalama
searchsploit -m 50383

# Arama yoluyla kopyalama
searchsploit -m windows/remote/48537.py
```

### 2. Exploit Yolunu ve Bilgilerini Görüntüleme (`-p`)
Dosyanın diskteki tam yolunu ve web bağlantısını gösterir:
```bash
searchsploit -p 50383
```
Örnek çıktı:
```text
  Exploit: Apache HTTP Server 2.4.49 - Path Traversal & Remote Code Execution (RCE)
      URL: https://www.exploit-db.com/exploits/50383
     Path: /usr/share/exploitdb/exploits/multiple/webapps/50383.sh
File Type: Bourne-Again shell script, ASCII text executable
```

### 3. Terminalde Exploit Kodunu İnceleme (`-x`)
Kodu terminalde `less` ile doğrudan okuyun:
```bash
searchsploit -x 50383
```

---

## Platform ve Türe Göre Filtreleme

### 1. İşletim Sistemine Göre Süzme
```bash
# Sadece Linux exploitleri
searchsploit linux kernel 4.4

# Sadece Windows exploitleri
searchsploit windows smb eternal

# Web uygulamaları
searchsploit webapps tomcat
```

### 2. Kabuk Kodlarını (Shellcode) Arama (`-s`)
Hedef mimariye özgü ham shellcode yüklerini listeleyin:
```bash
searchsploit -s linux/x64 "execve"
searchsploit -s windows/x86 "reverse shell"
```

### 3. Güvenlik Raporları ve Makaleleri (`-w`)
```bash
searchsploit -w "Active Directory Kerberos"
```

---

## Nmap XML Tarama Entegrasyonu

Nmap servis taraması çıktısını Searchsploit'e vererek açık portlardaki yazılımlarla eşleşen exploitleri otomatik tespit edin:

```bash
# 1. Servis sürüm taraması yapıp XML formatında kaydedin
nmap -sV -p- -T4 -oX /tmp/hedef_tarama.xml 192.168.1.50

# 2. XML dosyasını doğrudan Searchsploit'e besleyin
searchsploit --nmap /tmp/hedef_tarama.xml
```

---

## JSON Çıktısı & Betik Otomasyonu

Otomasyon hatları ve raporlama araçları için çıktıları JSON formatında dışa aktarın:

```bash
searchsploit "Apache Struts" --json > struts_exploitleri.json
```

`jq` aracıyla dosya yollarını filtreleyin:
```bash
searchsploit --cve 2021-41773 --json | jq -r '.RESULTS_EXPLOIT[].Path'
```

---

## Sızma Testlerinde En İyi Pratikler

1. **Çalıştırmadan Önce Kodu İnceleyin:** Herhangi bir exploiti derlemeden veya çalıştırmadan önce `searchsploit -x <id>` ile mutlaka okuyun. Sabit kodlanmış IP'leri, ters bağlantı adreslerini ve potansiyel arka kapıları denetleyin.
2. **Doğru Bayraklarla Derleyin:** C diliyle yazılmış birçok exploit GCC tarafında özel kütüphane bayrakları gerektirir:
   ```bash
   searchsploit -m 40839
   gcc -pthread 40839.c -o dirtycow -lcrypt
   ```
3. **Veritabanını Güncel Tutun:** Yeni yayınlanan kritik güvenlik açıklarını kaçırmamak için düzenli olarak `searchsploit -u` çalıştırın.
