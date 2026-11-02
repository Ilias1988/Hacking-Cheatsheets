[🇬🇧 English](../../README.md)

# 🐉 Kali Linux Araçları - Kapsamlı Referans (600+ Araç)

```
  ██╗  ██╗ █████╗ ██╗     ██╗    ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║ ██╔╝██╔══██╗██║     ██║    ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  █████╔╝ ███████║██║     ██║    ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██╔═██╗ ██╔══██║██║     ██║    ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ██║  ██╗██║  ██║███████╗██║    ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝    ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
                    ARAÇLAR REFERANS KILAVUZU
```

<p align="center">
  <img src="https://img.shields.io/badge/Kali_Linux-Tools-557C94?style=for-the-badge&logo=kalilinux" alt="Kali">
  <img src="https://img.shields.io/badge/600+-Araç-red?style=for-the-badge" alt="Tools">
  <img src="https://img.shields.io/badge/14-Kategori-blue?style=for-the-badge" alt="Categories">
</p>

---

## 📋 İçindekiler

1. [Bilgi Toplama (Information Gathering)](#1--bilgi-toplama-information-gathering)
2. [Zafiyet Analizi (Vulnerability Analysis)](#2--zafiyet-analizi-vulnerability-analysis)
3. [Web Uygulama Analizi (Web Application Analysis)](#3--web-uygulama-analizi-web-application-analysis)
4. [Veritabanı Değerlendirmesi (Database Assessment)](#4--veritabanı-değerlendirmesi-database-assessment)
5. [Parola Saldırıları (Password Attacks)](#5--parola-saldırıları-password-attacks)
6. [Kablosuz Ağ Saldırıları (Wireless Attacks)](#6--kablosuz-ağ-saldırıları-wireless-attacks)
7. [Tersine Mühendislik (Reverse Engineering)](#7--tersine-mühendislik-reverse-engineering)
8. [İstismar Araçları (Exploitation Tools)](#8--i̇stismar-araçları-exploitation-tools)
9. [Ağ Dinleme & Sahtecilik (Sniffing & Spoofing)](#9--ağ-dinleme--sahtecilik-sniffing--spoofing)
10. [İstismar Sonrası (Post Exploitation)](#10--i̇stismar-sonrası-post-exploitation)
11. [Adli Bilişim (Forensics)](#11--adli-bilişim-forensics)
12. [Raporlama Araçları (Reporting Tools)](#12--raporlama-araçları-reporting-tools)
13. [Sosyal Mühendislik (Social Engineering)](#13--sosyal-mühendislik-social-engineering)
14. [Donanım Korsanlığı (Hardware Hacking)](#14--donanım-korsanlığı-hardware-hacking)

---

## 1. 🔍 Bilgi Toplama (Information Gathering)

### DNS Analizi

| Araç | Açıklama |
|------|----------|
| **dnsrecon** | DNS numaralandırma betiği |
| **dnsenum** | DNS numaralandırma aracı |
| **fierce** | DNS keşif aracı |
| **dnsmap** | Alt alan adı kaba kuvvet (brute-force) aracı |
| **dnstracer** | DNS izleme aracı |
| **dnswalk** | DNS veritabanı hata ayıklayıcısı |

### Hedef Sistem/Host Keşfi

| Araç | Açıklama |
|------|----------|
| **nmap** | Ağ haritalayıcı ve port tarayıcı |
| **masscan** | Yüksek hızlı port tarayıcı |
| **hping3** | Ağ paket üreteci |
| **fping** | Hızlı ping sweep |
| **arping** | ARP ping yardımcı programı |
| **netdiscover** | Aktif/pasif ARP keşif aracı |
| **unicornscan** | Asenkron ağ tarayıcı |

### Ağ & Port Tarayıcıları

| Araç | Açıklama |
|------|----------|
| **nmap** | En popüler ağ tarayıcısı |
| **masscan** | En hızlı port tarayıcı |
| **rustscan** | Modern ve hızlı port tarayıcı |
| **zmap** | Hızlı tek paketli ağ tarayıcısı |
| **unicornscan** | Asenkron TCP/UDP tarayıcısı |
| **nbtscan** | NetBIOS isim tarayıcısı |
| **onesixtyone** | SNMP tarayıcısı |

### OSINT Araçları

| Araç | Açıklama |
|------|----------|
| **maltego** | OSINT ve grafiksel bağlantı analizi |
| **spiderfoot** | OSINT otomasyon aracı |
| **recon-ng** | Web keşif ve istihbarat çatısı |
| **theHarvester** | E-posta, alt alan adı, IP toplayıcı |
| **shodan** | IoT arama motoru komut satırı arayüzü |
| **amass** | Saldırı yüzeyi haritalama |
| **subfinder** | Alt alan adı keşif aracı |
| **assetfinder** | Alan adları ve alt alan adları bulma |
| **waybackurls** | Wayback Machine üzerinden URL toplama |
| **gau** | Get All URLs (Tüm URL'leri çekme) |

### Rota (Route) Analizi

| Araç | Açıklama |
|------|----------|
| **traceroute** | Ağ rota izleme |
| **mtr** | Ağ teşhis ve rota izleme aracı |
| **netmask** | Ağ maskesi yapılandırma aracı |
| **tcptraceroute** | TCP rota izleme aracı |

### SMB Analizi

| Araç | Açıklama |
|------|----------|
| **smbclient** | SMB istemcisi |
| **smbmap** | SMB paylaşım izinleri numaralandırma |
| **enum4linux** | Windows/Samba bilgi toplama |
| **enum4linux-ng** | Yeni nesil enum4linux |
| **rpcclient** | MS-RPC istemcisi |
| **nbtscan** | NetBIOS tarayıcısı |

### SMTP Analizi

| Araç | Açıklama |
|------|----------|
| **smtp-user-enum** | SMTP kullanıcı adı doğrulama ve numaralandırma |
| **swaks** | SMTP test ve e-posta gönderme aracı |

### SNMP Analizi

| Araç | Açıklama |
|------|----------|
| **snmpwalk** | SNMP veri çekme aracı |
| **snmpcheck** | SNMP cihaz bilgisi numaralandırıcı |
| **onesixtyone** | Hızlı SNMP topluluk dizesi tarayıcısı |
| **braa** | Toplu SNMP tarayıcısı |

### SSL Analizi

| Araç | Açıklama |
|------|----------|
| **sslscan** | SSL/TLS şifre paketi ve sertifika tarayıcısı |
| **sslyze** | Kapsamlı SSL/TLS analiz aracı |
| **testssl.sh** | SSL/TLS güvenlik ve şifreleme testi |

---

## 2. 🔓 Zafiyet Analizi (Vulnerability Analysis)

### Zafiyet Tarayıcıları

| Araç | Açıklama |
|------|----------|
| **nmap** | NSE zafiyet tespit betikleri |
| **nikto** | Web sunucu güvenlik açığı tarayıcısı |
| **openvas** | Açık kaynak zafiyet değerlendirme sistemi |
| **legion** | Ağ sızma testi ve numaralandırma çatısı |
| **lynis** | Güvenlik denetimi ve sistem sertleştirme aracı |
| **nuclei** | Şablon tabanlı hızlı zafiyet tarayıcısı |

### Fuzzing Araçları

| Araç | Açıklama |
|------|----------|
| **wfuzz** | Web uygulama fuzzer'ı |
| **ffuf** | Yüksek hızlı web fuzzer'ı |
| **gobuster** | Dizin/DNS kaba kuvvet aracı |
| **dirb** | Web içerik tarayıcısı |
| **dirbuster** | Grafiksel dizin kaba kuvvet aracı |
| **feroxbuster** | Özyinelemeli hızlı içerik keşfi |
| **spike** | Ağ protokol fuzzer'ı |
| **sfuzz** | Basit protokol fuzzer'ı |

### VoIP Araçları

| Araç | Açıklama |
|------|----------|
| **sipvicious** | SIP protokol analiz ve denetim araçları |
| **voiphopper** | VoIP VLAN hopping (atlama) aracı |

---

## 3. 🌐 Web Uygulama Analizi (Web Application Analysis)

### CMS Tanımlama

| Araç | Açıklama |
|------|----------|
| **wpscan** | WordPress güvenlik açığı tarayıcısı |
| **joomscan** | Joomla zafiyet tarayıcısı |
| **droopescan** | Drupal/WordPress/Joomla tarayıcısı |
| **cmseek** | CMS tespiti ve istismar aracı |

### Web Zafiyet Tarayıcıları

| Araç | Açıklama |
|------|----------|
| **nikto** | Web sunucu tarayıcısı |
| **burpsuite** | Web uygulama güvenlik testi platformu |
| **zaproxy** | OWASP ZAP vekil sunucu aracı |
| **wapiti** | Web zafiyet tarayıcısı |
| **skipfish** | Web uygulama güvenlik denetleyicisi |
| **arachni** | Modüler web güvenlik açığı tarayıcısı |
| **w3af** | Web uygulaması saldırı ve denetim çatısı |
| **vega** | Web güvenlik açığı tarayıcısı |

### Web Tarayıcılar (Crawlers)

| Araç | Açıklama |
|------|----------|
| **cutycapt** | Web sayfası ekran görüntüsü yakalama |
| **dirb** | Web içerik tarayıcısı |
| **dirbuster** | Dizin kaba kuvvet taraması |
| **gobuster** | Dizin/DNS kaba kuvvet aracı |
| **feroxbuster** | Hızlı ve özyinelemeli içerik keşfi |

### Web Uygulama Vekil Sunucuları (Proxies)

| Araç | Açıklama |
|------|----------|
| **burpsuite** | Web vekil sunucu ve tarayıcı |
| **zaproxy** | OWASP ZAP güvenlik vekili |
| **mitmproxy** | Etkileşimli HTTP/HTTPS vekili |
| **proxychains** | Proxy zincirleme aracı |

### SQL Enjeksiyonu

| Araç | Açıklama |
|------|----------|
| **sqlmap** | Otomatik SQL enjeksiyon aracı |
| **sqlninja** | MS SQL Server istismar aracı |
| **bbqsql** | Kör (Blind) SQL enjeksiyon aracı |
| **jsql-injection** | Java tabanlı SQL enjeksiyon aracı |
| **ghauri** | Gelişmiş SQL enjeksiyon aracı |

### XSS Araçları

| Araç | Açıklama |
|------|----------|
| **xsser** | XSS güvenlik açığı test aracı |
| **xssstrike** | Gelişmiş XSS tespit ve analiz aracı |
| **dalfox** | XSS için parametre analizi ve tarayıcı |

---

## 4. 💾 Veritabanı Değerlendirmesi (Database Assessment)

| Araç | Açıklama |
|------|----------|
| **sqlmap** | Otomatik SQL enjeksiyon ve veritabanı ele geçirme |
| **sqlninja** | MS SQL Server istismar aracı |
| **jsql-injection** | Java tabanlı SQLi aracı |
| **oscanner** | Oracle veritabanı tarayıcısı |
| **tnscmd10g** | Oracle TNS dinleyici komut aracı |
| **odat** | Oracle veritabanı saldırı çatısı |
| **mdbtools** | MS Access MDB dosya araçları |
| **sidguesser** | Oracle SID tahmin aracı |

---

## 5. 🔑 Parola Saldırıları (Password Attacks)

### Çevrimdışı (Offline) Saldırılar

| Araç | Açıklama |
|------|----------|
| **john** | John the Ripper parola kırıcı |
| **hashcat** | GPU hızlandırmalı gelişmiş parola kırıcı |
| **ophcrack** | Rainbow table tabanlı Windows parola kırıcı |
| **hashid** | Hash tanımlayıcı |
| **hash-identifier** | Hash türü tespit aracı |
| **chntpw** | Windows parola sıfırlama yardımcı programı |
| **samdump2** | Windows SAM veritabanı döküm aracı |
| **crunch** | Kelime listesi üreteci |
| **cewl** | Hedef odaklı özel kelime listesi oluşturucu |
| **cupp** | Kullanıcı profiline özel parola üreteci |
| **rsmangler** | Kelime listesi manipülasyon aracı |
| **wordlists** | Hazır kelime listeleri koleksiyonu |

### Çevrimiçi (Online) Saldırılar

| Araç | Açıklama |
|------|----------|
| **hydra** | Hızlı ağ oturum açma kaba kuvvet aracı |
| **medusa** | Paralel oturum kaba kuvvet aracı |
| **ncrack** | Ağ kimlik doğrulama kırıcı |
| **patator** | Çok amaçlı kaba kuvvet aracı |
| **thc-pptp-bruter** | PPTP VPN kaba kuvvet aracı |
| **crowbar** | Uzaktan kimlik doğrulama kaba kuvvet aracı |

### Hash ile Kimlik Doğrulama (Passing the Hash)

| Araç | Açıklama |
|------|----------|
| **mimikatz** | Windows kimlik bilgisi çıkarıcı |
| **pth-toolkit** | Pass-the-hash araç takımı |
| **smbmap** | SMB izinleri ve paylaşım numaralandırma |
| **crackmapexec** | Ağ sızma testi ve yanal hareket aracı |
| **evil-winrm** | Windows uzaktan yönetim kabuk aracı |

---

## 6. 📡 Kablosuz Ağ Saldırıları (Wireless Attacks)

### Bluetooth

| Araç | Açıklama |
|------|----------|
| **bluelog** | Bluetooth tarayıcı ve günlükleyici |
| **bluemaho** | Bluetooth güvenlik test aracı |
| **blueranger** | Bluetooth yakınlık tespit aracı |
| **btscanner** | Bluetooth cihaz tarayıcısı |
| **redfang** | Gizli Bluetooth cihaz bulucu |
| **spooftooph** | Bluetooth adres yanıltma (spoofing) |

### WiFi Araçları

| Araç | Açıklama |
|------|----------|
| **aircrack-ng** | WiFi güvenlik ve kırma paketi |
| **airmon-ng** | Monitör modu etkinleştirme betiği |
| **airodump-ng** | Kablosuz paket yakalama aracı |
| **aireplay-ng** | Paket enjeksiyon aracı |
| **airbase-ng** | Sahte erişim noktası (Fake AP) oluşturucu |
| **wifite** | Otomatik kablosuz ağ denetim aracı |
| **fern-wifi-cracker** | Grafiksel kablosuz ağ denetleyicisi |
| **kismet** | Kablosuz ağ tespit ve dinleme aracı |
| **reaver** | WPS kaba kuvvet saldırı aracı |
| **bully** | Hızlı WPS kaba kuvvet aracı |
| **pixiewps** | Çevrimdışı WPS Pixie Dust saldırı aracı |
| **wifiphisher** | Kablosuz ağ oltalama aracı |
| **fluxion** | MitM tabanlı WPA oltalama saldırısı |
| **hostapd-wpe** | Kablosuz kurumsal kimlik bilgisi toplama |
| **cowpatty** | WPA-PSK sözlük saldırısı aracı |
| **asleap** | LEAP/PPTP parola kurtarma |
| **eapmd5pass** | EAP-MD5 parola kırıcı |
| **mdk3** | Kablosuz ağ manipülasyon ve DoS aracı |
| **mdk4** | Kablosuz ağ sömürme aracı |

### RFID / NFC

| Araç | Açıklama |
|------|----------|
| **mfoc** | Mifare Classic çevrimdışı parola kırıcı |
| **mfcuk** | Mifare Classic evrensel istismar paketi |
| **mfterm** | Mifare Classic için terminal arayüzü |
| **libnfc** | Açık kaynak NFC kütüphanesi |
| **proxmark3** | RFID/NFC araştırma ve kopyalama platformu |

---

## 7. 🔧 Tersine Mühendislik (Reverse Engineering)

### Hata Ayıklayıcılar (Debuggers)

| Araç | Açıklama |
|------|----------|
| **gdb** | GNU Hata Ayıklayıcısı |
| **edb-debugger** | Çapraz platform hata ayıklayıcı |
| **radare2** | Kapsamlı tersine mühendislik çatısı |
| **x64dbg** | x64/x32 hata ayıklayıcı |
| **ollydbg** | x86 hata ayıklayıcı |
| **immunity-debugger** | Python ile betiklenebilir hata ayıklayıcı |

### Ayrıştırıcılar (Disassemblers)

| Araç | Açıklama |
|------|----------|
| **ghidra** | NSA açık kaynak tersine mühendislik aracı |
| **radare2** | Tersine mühendislik komut satırı çatısı |
| **cutter** | Radare2 grafiksel kullanıcı arayüzü (GUI) |
| **rizin** | Bağımsız tersine mühendislik çatısı |
| **objdump** | Nesne dosyası ayrıştırıcısı |
| **IDA Free** | Etkileşimli tersine mühendislik aracı |

### Diğer Tersine Mühendislik Araçları

| Araç | Açıklama |
|------|----------|
| **apktool** | Android APK tersine mühendislik aracı |
| **dex2jar** | DEX dosyasını JAR formatına dönüştürücü |
| **jadx** | Java kaynak kodu decompile aracı |
| **jd-gui** | Java decompiler grafik arayüzü |
| **bytecode-viewer** | Java bayt kodu görüntüleyici ve decompiler |
| **androguard** | Android uygulaması analiz aracı |
| **frida** | Dinamik ikili enjeksiyon ve enstrümantasyon |
| **angr** | Sembolik yürütme ve ikili analiz platformu |
| **binwalk** | Bellenim (firmware) analiz ve çıkarma aracı |

---

## 8. 💣 İstismar Araçları (Exploitation Tools)

### İstismar Çatıları (Frameworks)

| Araç | Açıklama |
|------|----------|
| **metasploit-framework** | Kapsamlı istismar çatısı |
| **armitage** | Metasploit grafiksel yönetim arayüzü |
| **cobalt-strike** | Tehdit simülasyonu ve C2 çatısı |
| **beef-xss** | Tarayıcı istismar çatısı (Browser Exploitation) |
| **routersploit** | Yönlendirici (Router) istismar çatısı |
| **exploitdb** | Açık istismar arşivi |
| **searchsploit** | Çevrimdışı Exploit-DB arama aracı |

### Shellcode Araçları

| Araç | Açıklama |
|------|----------|
| **msfvenom** | Payload ve kabuk kodu üreteci |
| **veil** | Antivirüs atlatma ve payload çatısı |
| **shellnoob** | Kabuk kodu (shellcode) geliştirme araç takımı |

### Ağ İstismarı

| Araç | Açıklama |
|------|----------|
| **responder** | LLMNR/NBT-NS zehirleme aracı |
| **impacket** | Ağ protokolleri istismar araç takımı |
| **crackmapexec** | İstismar sonrası yanal hareket aracı |
| **evil-winrm** | Uzaktan Windows yönetim kabuğu |
| **powershell-empire** | PowerShell tabanlı C2 ve post-exploit çatısı |

---

## 9. 📡 Ağ Dinleme & Sahtecilik (Sniffing & Spoofing)

### Ağ Dinleyicileri (Sniffers)

| Araç | Açıklama |
|------|----------|
| **wireshark** | Grafiksel ağ protokol analizörü |
| **tcpdump** | Komut satırı paket analizörü |
| **tshark** | Wireshark komut satırı arayüzü |
| **ettercap** | Kapsamlı ağ dinleyici ve MitM aracı |
| **dsniff** | Ağ denetim ve parola dinleme araçları |
| **net-creds** | Ağ trafiğinden kimlik bilgisi dinleyici |
| **pcredz** | Paket dökümlerinden kimlik bilgisi çıkarıcı |

### Sahtecilik (Spoofing) Araçları

| Araç | Açıklama |
|------|----------|
| **arpspoof** | ARP önbellek zehirleme aracı |
| **dnsspoof** | DNS yanıtı yanıltma aracı |
| **macchanger** | MAC adresi değiştirme aracı |
| **ettercap** | ARP zehirleme ve filtreleme |
| **bettercap** | Modern ve modüler ağ saldırı aracı |
| **mitmproxy** | Etkileşimli HTTP MitM vekili |
| **sslstrip** | HTTPS trafiğini HTTP'ye düşürme aracı |

### VoIP Dinleme

| Araç | Açıklama |
|------|----------|
| **sipvicious** | SIP/VoIP analiz araçları |
| **voiphopper** | VoIP VLAN hopping aracı |

---

## 10. 🎯 İstismar Sonrası (Post Exploitation)

### İşletim Sistemi Arka Kapıları (Backdoors)

| Araç | Açıklama |
|------|----------|
| **msfvenom** | Payload ve arka kapı üretimi |
| **veil** | Antivirüs atlatma ve yürütülebilir dosya üretimi |
| **shellter** | Dinamik kabuk kodu enjektörü |
| **weevely** | Gizli PHP web kabuğu (web shell) |

### Tünelleme (Tunneling)

| Araç | Açıklama |
|------|----------|
| **sshuttle** | SSH üzerinden şeffaf VPN tüneli |
| **chisel** | Hızlı TCP/UDP HTTP tüneli |
| **proxychains** | Dinamik proxy yönlendirme |
| **socat** | Çok amaçlı veri aktarım aracı |
| **plink** | Komut satırı PuTTY bağlantı aracı |
| **stunnel** | TLS şifreli tünelleme |
| **dns2tcp** | DNS üzerinden TCP tüneli |
| **iodine** | DNS üzerinden IPv4 tünelleme |
| **ptunnel** | ICMP paketleri üzerinden tünelleme |
| **pwnat** | NAT aşma ve doğrudan bağlantı aracı |
| **udptunnel** | UDP tünelleme aracı |

### Windows İstismar Sonrası

| Araç | Açıklama |
|------|----------|
| **mimikatz** | Windows bellek kimlik bilgisi dökümü |
| **crackmapexec** | Windows ve Active Directory sızma testi |
| **evil-winrm** | Yetkili WinRM kabuk aracı |
| **impacket** | Windows ağ protokolleri araç seti |
| **bloodhound** | Active Directory ilişki haritalama |
| **powershell-empire** | PowerShell tabanlı post-exploitation |
| **rubeus** | Kerberos suiistimal araç takımı |
| **kerbrute** | Kerberos ön kimlik doğrulama kaba kuvvet aracı |

### Linux İstismar Sonrası

| Araç | Açıklama |
|------|----------|
| **linpeas** | Linux yetki yükseltme aracı |
| **linenum** | Linux yerel bilgi toplama betiği |
| **pspy** | Süreç ve komut izleme (root olmadan) |
| **linux-exploit-suggester** | Linux çekirdek istismar önerici |

---

## 11. 🔬 Adli Bilişim (Forensics)

### Adli Bilişim Çatıları

| Araç | Açıklama |
|------|----------|
| **autopsy** | Grafiksel dijital adli bilişim platformu |
| **sleuthkit** | Komut satırı adli bilişim araç takımı |
| **dff** | Dijital adli bilişim inceleme çatısı |

### Dosya Kurtarma (Forensic Carving)

| Araç | Açıklama |
|------|----------|
| **foremost** | Başlık ve son bilgiye dayalı dosya kurtarma |
| **scalpel** | Yüksek performanslı dosya kurtarma aracı |
| **bulk_extractor** | Disk görüntülerinden bilgi çıkarıcı |
| **photorec** | Silinen medya ve dosya kurtarıcı |
| **testdisk** | Kayıp disk bölümleri ve veri kurtarma |

### İmaj Alma (Forensic Imaging)

| Araç | Açıklama |
|------|----------|
| **dc3dd** | Adli bilişim odaklı disk imajı alma |
| **dcfldd** | Gelişmiş dd imaj alma aracı |
| **guymager** | Grafiksel adli disk imajı alma aracı |

### Bellek Analizi (Memory Forensics)

| Araç | Açıklama |
|------|----------|
| **volatility** | RAM bellek analizi çatısı |
| **volatility3** | Yeni nesil bellek analizi çatısı |
| **lime** | Linux canlı bellek yakalama aracı |

### Ağ Adli Bilişimi

| Araç | Açıklama |
|------|----------|
| **wireshark** | Derinlemesine paket analizi |
| **tcpdump** | Komut satırı paket yakalama |
| **ngrep** | Ağ trafiğinde düzenli ifade arama |
| **NetworkMiner** | Ağ adli analizi ve dosya çıkarıcı |

### PDF Adli Bilişimi

| Araç | Açıklama |
|------|----------|
| **pdfid** | Şüpheli PDF nesnesi tarayıcısı |
| **pdf-parser** | PDF dosya yapısı ve eleman analizi |
| **peepdf** | Güvenlik odaklı PDF analiz aracı |

---

## 12. 📝 Raporlama Araçları (Reporting Tools)

| Araç | Açıklama |
|------|----------|
| **cutycapt** | Web sayfası ekran görüntüsü yakalama |
| **faraday** | İşbirlikçi sızma testi yönetim platformu |
| **maltego** | OSINT ve ilişki görselleştirme |
| **pipal** | Parola istatistikleri ve analiz aracı |
| **recordmydesktop** | Ekran kaydı aracı |
| **cherrytree** | Not alma ve düzenleme uygulaması |
| **dradis** | Güvenlik testi raporlama ve işbirliği platformu |
| **metagoofil** | Belge metaverisi çıkarma aracı |
| **eyewitness** | Çoklu web sitesi ekran görüntüsü aracı |
| **gowitness** | Hızlı web ekran görüntüsü aracı |

---

## 13. 🎭 Sosyal Mühendislik (Social Engineering)

| Araç | Açıklama |
|------|----------|
| **setoolkit** | Sosyal Mühendislik Araç Takımı (SET) |
| **gophish** | Kurumsal oltalama (phishing) simülasyon çatısı |
| **king-phisher** | Oltalama kampanyası yönetim aracı |
| **evilginx2** | İki faktörlü kimlik doğrulamayı aşan MitM oltalama çatısı |
| **beef-xss** | Tarayıcı istismar ve kontrol çatısı |
| **maltego** | Sosyal ilişkiler ve bağlantı analizi |
| **theHarvester** | E-posta ve kurum çalışanı tespiti |
| **sherlock** | Çoklu platformda kullanıcı adı arama |
| **social-analyzer** | Sosyal medya profilleri analiz aracı |

---

## 14. 🔧 Donanım Korsanlığı (Hardware Hacking)

| Araç | Açıklama |
|------|----------|
| **arduino** | Açık kaynak donanım prototipleme platformu |
| **binwalk** | Bellenim (firmware) analiz ve çıkarma aracı |
| **flashrom** | Flash bellek yongası programlayıcı |
| **openocd** | Yongada hata ayıklama (JTAG/SWD) aracı |
| **proxmark3** | RFID/NFC araştırma ve analiz donanımı |

---

## 📊 Hızlı Referans - En Çok Kullanılan 50 Araç

| # | Araç | Kategori | Kullanım Amacı |
|---|------|----------|----------------|
| 1 | **nmap** | Tarama | Port taraması |
| 2 | **metasploit** | İstismar | İstismar çatısı |
| 3 | **burpsuite** | Web | Web güvenlik testi |
| 4 | **wireshark** | Dinleme | Paket analizi |
| 5 | **john** | Parola | Parola kırma |
| 6 | **hashcat** | Parola | GPU tabanlı parola kırma |
| 7 | **hydra** | Parola | Çevrimiçi kaba kuvvet |
| 8 | **sqlmap** | Web | SQL enjeksiyonu |
| 9 | **aircrack-ng** | Kablosuz | WiFi şifresi kırma |
| 10 | **nikto** | Web | Web sunucu taraması |
| 11 | **gobuster** | Web | Dizin kaba kuvveti |
| 12 | **ffuf** | Web | Web fuzzer'ı |
| 13 | **dirb** | Web | Dizin taraması |
| 14 | **wpscan** | Web | WordPress taraması |
| 15 | **enum4linux** | Bilgi | SMB numaralandırma |
| 16 | **crackmapexec** | İstismar Sonrası | Active Directory denetimi |
| 17 | **mimikatz** | İstismar Sonrası | Kimlik bilgisi dökümü |
| 18 | **responder** | İstismar | LLMNR zehirleme |
| 19 | **impacket** | İstismar | Ağ protokolleri araçları |
| 20 | **bloodhound** | İstismar Sonrası | AD ilişki haritalama |
| 21 | **netcat** | Yardımcı Araç | TCP/UDP soket aracı |
| 22 | **socat** | Yardımcı Araç | Veri aktarım aracı |
| 23 | **tcpdump** | Dinleme | Ağ paketi yakalama |
| 24 | **ettercap** | Dinleme | MitM saldırıları |
| 25 | **bettercap** | Dinleme | Ağ saldırıları |
| 26 | **proxychains** | Yardımcı Araç | Proxy zincirleme |
| 27 | **msfvenom** | İstismar | Payload üretimi |
| 28 | **searchsploit** | İstismar | Exploit arama |
| 29 | **theHarvester** | OSINT | E-posta toplama |
| 30 | **maltego** | OSINT | Bağlantı analizi |
| 31 | **recon-ng** | OSINT | Keşif çatısı |
| 32 | **amass** | OSINT | Alt alan adı numaralandırma |
| 33 | **subfinder** | OSINT | Alt alan adı bulucu |
| 34 | **nuclei** | Zafiyet | Şablon tabanlı tarayıcı |
| 35 | **zaproxy** | Web | OWASP ZAP vekil sunucu |
| 36 | **wifite** | Kablosuz | Otomatik WiFi denetimi |
| 37 | **ghidra** | Tersine Müh. | Tersine mühendislik |
| 38 | **radare2** | Tersine Müh. | İkili dosya analizi |
| 39 | **gdb** | Tersine Müh. | Hata ayıklayıcı |
| 40 | **binwalk** | Tersine Müh. | Bellenim analizi |
| 41 | **volatility** | Adli Bilişim | Bellek analizi |
| 42 | **autopsy** | Adli Bilişim | Adli bilişim platformu |
| 43 | **foremost** | Adli Bilişim | Dosya kurtarma |
| 44 | **setoolkit** | Sosyal Müh. | Sosyal mühendislik araç takımı |
| 45 | **beef-xss** | Web | Tarayıcı istismarı |
| 46 | **linpeas** | Yetki Yükseltme | Linux yetki tespiti |
| 47 | **winpeas** | Yetki Yükseltme | Windows yetki tespiti |
| 48 | **chisel** | Tünelleme | HTTP tüneli |
| 49 | **sshuttle** | Tünelleme | SSH VPN |
| 50 | **evil-winrm** | İstismar Sonrası | WinRM kabuğu |

---

## 📚 Kaynaklar

- [Kali Linux Tools](https://www.kali.org/tools/)
- [Kali Documentation](https://www.kali.org/docs/)
- [Kali Metapackages](https://www.kali.org/docs/general-use/metapackages/)

---

<p align="center">
  <b>🐉 Kali Linux'ta Uzmanlaşın!</b><br>
  <i>600'den fazla araç parmaklarınızın ucunda</i>
</p>
