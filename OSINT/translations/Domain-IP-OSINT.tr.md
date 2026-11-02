[🇬🇧 English](../Domain-IP-OSINT.md)

# 🌐 Alan Adı ve IP OSINT (Domain & IP OSINT)

---

## 🔍 Alan Adı Keşif Araçları

| Araç | URL | Amaç |
|------|-----|------|
| **WHOIS** | who.is | Alan adı tescil bilgileri |
| **SecurityTrails** | securitytrails.com | Geçmiş DNS kayıtları |
| **ViewDNS** | viewdns.info | Kapsamlı DNS araçları |
| **BuiltWith** | builtwith.com | Kullanılan teknoloji yığını |
| **Wappalyzer** | wappalyzer.com | Teknoloji tespiti |

---

## 🛠️ Komutlar

### WHOIS
```bash
whois target.com
whois 1.2.3.4
```

### DNS Numaralandırma
```bash
# DNS kayıtlarını sorgulama
dig target.com ANY
dig target.com MX
dig target.com TXT
nslookup -type=any target.com

# Bölge aktarımı (Zone transfer) testi
dig axfr @ns1.target.com target.com
host -t axfr target.com ns1.target.com
```

### Alt Alan Adı Keşfi
```bash
# Subfinder
subfinder -d target.com -o subs.txt

# Amass
amass enum -d target.com -o subs.txt

# Assetfinder
assetfinder target.com
```

---

## 🔎 IP OSINT

| Araç | URL | Amaç |
|------|-----|------|
| **Shodan** | shodan.io | Bağlı cihaz ve servis arama |
| **Censys** | censys.io | İnternet genel taraması |
| **IPinfo** | ipinfo.io | IP coğrafi konum tespiti |
| **AbuseIPDB** | abuseipdb.com | IP itibar ve kötüye kullanım geçmişi |

### Shodan CLI
```bash
shodan host 1.2.3.4
shodan search "hostname:target.com"
shodan domain target.com
```

### IP Sorgulama
```bash
curl ipinfo.io/1.2.3.4
curl ip-api.com/json/1.2.3.4
whois 1.2.3.4
```

---

## 📋 Alan Adı Kontrol Listesi

```markdown
□ WHOIS kayıtları
□ DNS kayıtları (A, MX, TXT, NS)
□ Alt alan adları
□ Geçmiş DNS kayıtları (SecurityTrails)
□ Kullanılan teknoloji yığını
□ SSL/TLS sertifika detayları
□ IP adres aralıkları
□ Ters IP (Reverse IP) araması
□ ASN bilgileri
```

---

**OSINT Ana Sayfasına Dön:** [🔍 OSINT Genel Bakış](./README.tr.md)
