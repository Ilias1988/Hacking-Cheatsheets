# 🔬 Adli Bilişim CTF Kopya Kağıdı (Forensics CTF Cheatsheet)

---

## 📁 Dosya Analizi

```bash
# Dosya türü tespiti
file bilinmeyen_dosya

# Hex dökümü
xxd dosya | head
hexdump -C dosya | head

# Dize analizi
strings -n 6 dosya
strings -e l dosya    # Little-endian dizeleri

# Dosya ayrıştırma (carving)
binwalk dosya
binwalk -e dosya      # Gömülü dosyaları çıkarma
foremost dosya
```

---

## 🖼️ Steganografi (Veri Gizleme)

### Görsel Dosyaları (Images)

```bash
# Üstveri (metadata) analizi
exiftool resim.jpg
identify -verbose resim.png

# Steghide (JPEG dosyaları)
steghide extract -sf resim.jpg

# Zsteg (PNG dosyaları)
zsteg resim.png
zsteg -a resim.png   # Tüm yöntemleri deneme

# LSB analizi (Grafik arayüz)
stegsolve.jar

# Resim içindeki gizli metinler
strings resim.png | grep -i flag
```

### Ses Dosyaları (Audio)
```bash
# Spektrogram analizi (Audacity)
# Dosyayı Aç → Parça Adı Menüsü → Spektrogram Görünümü

# Sonic Visualiser
# Frekanslara gizlenmiş verileri ve görsel metinleri inceleyin
```

---

## 🧠 Bellek (RAM) Adli Bilişimi

### Volatility Kullanımı
```bash
# Bellek profilini belirleme
volatility -f dump.raw imageinfo

# Süreç listesi
volatility -f dump.raw --profile=Win7SP1x64 pslist
volatility -f dump.raw --profile=Win7SP1x64 pstree

# Komut geçmişi
volatility -f dump.raw --profile=Win7SP1x64 cmdscan
volatility -f dump.raw --profile=Win7SP1x64 consoles

# Süreç dökümü alma
volatility -f dump.raw --profile=Win7SP1x64 procdump -p 1234 -D output/

# Dosya tarama
volatility -f dump.raw --profile=Win7SP1x64 filescan | grep -i flag
```

---

## 💽 Disk Adli Bilişimi

```bash
# İmaj dosyasını bağlama (Mount)
mount -o loop image.dd /mnt/disk

# Dosya sistemi bilgisi
fsstat image.dd

# Autopsy grafik arayüzü
autopsy

# Sleuth Kit komutları
fls image.dd
icat image.dd 1234 > kurtarilan_dosya
```

---

## 📊 Ağ Adli Bilişimi (PCAP)

```bash
# Wireshark ile görsel analiz
wireshark capture.pcap

# Tshark ile komut satırından filtreleme
tshark -r capture.pcap
tshark -r capture.pcap -Y "http" -T fields -e http.file_data

# Aktarılan dosyaları çıkarma
tcpflow -r capture.pcap
```

---

## 📋 Adli Bilişim Kontrol Listesi

```markdown
□ Dosya türünü doğrulayın (file komutu)
□ strings ile bayrak (flag) anahtar kelimesini arayın
□ Üstverileri inceleyin (exiftool)
□ Binwalk ile gömülü dosyaları araştırın
□ Steganografi araçlarını çalıştırın (zsteg, steghide)
□ Bellek dökümü ise Volatility ile analiz edin
□ PCAP dosyalarında HTTP/DNS trafiğini ve dosyaları çıkarın
```

---

**CTF'e Dön:** [🏁 CTF Genel Bakış](./README.tr.md)
