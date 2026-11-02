[🇬🇧 English](../Image-OSINT.md)

# 🖼️ Görsel OSINT (Image OSINT)

---

## 🔍 Tersine Görsel Arama (Reverse Image Search)

| Servis | URL | En İyi Alanı |
|--------|-----|--------------|
| **Google Images** | images.google.com | Genel aramalar |
| **Yandex** | yandex.com/images | **Yüz tanıma** (en iyisi) |
| **TinEye** | tineye.com | Birebir eşleşmeler |
| **Bing Images** | bing.com/images | Alternatif genel arama |
| **PimEyes** | pimeyes.com | Gelişmiş yüz tanıma |

---

## 📷 Metaveri Çıkarma (EXIF)

### ExifTool
```bash
# Tüm metaverileri görüntüleme
exiftool image.jpg

# GPS koordinatlarını ayıklama
exiftool -gps* image.jpg

# Kamera ve çekim bilgileri
exiftool -make -model -datetime image.jpg

# Metaverileri temizleme/silme
exiftool -all= image.jpg
```

### Çevrim İçi Araçlar
- [Pic2Map](https://www.pic2map.com/)
- [FotoForensics](https://fotoforensics.com/)
- [Metadata Remover Viewer](https://metadataremover.ai/metadata-viewer) — Üçüncü taraf tarayıcı tabanlı EXIF/IPTC/XMP inceleme aracı. Hassas olmayan dosyalarla test edin.

> Metaveriler sonradan düzenlenebilir veya silinebilir; bulguları her zaman diğer kanıtlarla destekleyin.

---

## 🔎 Görsel Analizi

### Adli Bilişim (Forensic) Analizi
```
FotoForensics.com özellikleri:
- ELA (Hata Seviyesi Analizi / Error Level Analysis)
- Metaveri çıkarımı
- Gizli veri tespiti
- Sıkıştırma analizi
```

### Görsellerden Coğrafi Konum Belirleme (Geolocation)
```
1. EXIF GPS koordinat verilerini ayıklayın
2. Görseldeki anıt, tabela ve simgesel yapıları araştırın
3. Sokak görünümü (Street View) karşılaştırması yapın
4. Binalar, araç plakaları ve dil göstergelerini kontrol edin
```

---

## 📋 Görsel OSINT Kontrol Listesi

```markdown
□ Tersine görsel arama (Google, Yandex)
□ EXIF metaverilerini ayıkla
□ GPS koordinatlarını kontrol et
□ Yüz tanıma araması yap (PimEyes/Yandex)
□ Montaj/manipülasyon analizini gerçekleştir (ELA)
□ Görseldeki metinleri ara (OCR)
□ Görsel geçmişini incele (TinEye)
```

---

**OSINT Ana Sayfasına Dön:** [🔍 OSINT Genel Bakış](./README.tr.md)
