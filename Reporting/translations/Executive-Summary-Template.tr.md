# 📊 Yönetici Özeti Şablonu (Executive Summary Template)

```
███████╗██╗  ██╗███████╗ ██████╗██╗   ██╗████████╗██╗██╗   ██╗███████╗
██╔════╝╚██╗██╔╝██╔════╝██╔════╝██║   ██║╚══██╔══╝██║██║   ██║██╔════╝
█████╗   ╚███╔╝ █████╗  ██║     ██║   ██║   ██║   ██║██║   ██║█████╗  
██╔══╝   ██╔██╗ ██╔══╝  ██║     ██║   ██║   ██║   ██║╚██╗ ██╔╝██╔══╝  
███████╗██╔╝ ██╗███████╗╚██████╗╚██████╔╝   ██║   ██║ ╚████╔╝ ███████╗
╚══════╝╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚═════╝    ╚═╝   ╚═╝  ╚═══╝  ╚══════╝
███████╗██╗   ██╗███╗   ███╗███╗   ███╗ █████╗ ██████╗ ██╗   ██╗
██╔════╝██║   ██║████╗ ████║████╗ ████║██╔══██╗██╔══██╗╚██╗ ██╔╝
███████╗██║   ██║██╔████╔██║██╔████╔██║███████║██████╔╝ ╚████╔╝ 
╚════██║██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██╔══██╗  ╚██╔╝  
███████║╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║  ██║   ██║   
╚══════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   
```

> **Amacı:** C-seviye yöneticiler, yönetim kurulu üyeleri ve karar vericiler için hazırlanmış, teknik jargondan arındırılmış, kurumsal etki ve yatırım odaklı kısa özet.

---

## 📋 Yönetici Özeti Rapor Yapısı

```markdown
# GÜVENLİK DEĞERLENDİRMESİ YÖNETİCİ ÖZETİ

**Müşteri Kurum:** [Şirket Adı]
**Değerlendirme Türü:** [Sızma Testi / Güvenlik Denetimi / Zafiyet Değerlendirmesi]
**Tarih:** [Değerlendirme Dönemi]
**Sınıflandırma:** GİZLİ / HİZMETE ÖZEL

---

## 1. Amaç ve Kapsam

[Danışmanlık Şirketiniz], [Müşteri Kurum]'un [kapsamın ticari/operasyonel tanımı] varlıklarına 
yönelik bir [değerlendirme türü] gerçekleştirmek üzere görevlendirilmiştir. Çalışmanın 
amacı; kurumun siber güvenlik duruşunu değerlendirmek ve ticari operasyonlara yönelik 
potansiyel riskleri tespit etmektir.

**Kapsama Dahil Edilen Varlıklar:**
- [Kurumsal İş Sistemi 1]
- [Kurumsal İş Sistemi 2]
- [Ağ ve Bulut Altyapısı]

---

## 2. Genel Güvenlik Derecelendirmesi

┌─────────────────────────────────────────────────────┐
│                                                     │
│            GENEL RİSK DERECESİ: YÜKSEK (HIGH)       │
│                                                     │
│    ████████████████████░░░░░░░░░░  70/100          │
│                                                     │
│    Acil yönetim aksiyonu gerekmektedir               │
│                                                     │
└─────────────────────────────────────────────────────┘

| Seviye | Tanım |
|--------|-------|
| 🔴 **Kritik (Critical)** | Derhal aksiyon alınmalıdır. Doğrudan veri sızıntısı ve sistem ele geçirilmesi riski mevcuttur. |
| 🟠 **Yüksek (High)** | Ciddi güvenlik açıkları bulunmaktadır. Acil iyileştirme planlanmalıdır. |
| 🟡 **Orta (Medium)** | Orta düzey kurumsal risk. 30-60 gün içinde iyileştirme planlanmalıdır. |
| 🟢 **Düşük (Low)** | Küçük çaplı riskler. Normal bakım takviminde ele alınabilir. |

---

## 3. Temel Bulguların Özeti

| Kritiklik | Sayı | Örnek Bulgular |
|-----------|------|----------------|
| 🔴 Kritik | X | Yetkisiz veritabanı erişimi, kimlik doğrulama atlatma |
| 🟠 Yüksek | X | Müşteri verilerinin ifşası, yetki yükseltme |
| 🟡 Orta | X | Güvenlik yapılandırma eksiklikleri, sınırlı veri erişimi |
| 🟢 Düşük | X | İyi uygulama (best practice) önerileri |
| **Toplam** | **X** | |

### Bulgu Dağılımı

```
Kritik    ████████░░░░░░░░░░░░  X bulgu (%XX)
Yüksek    ██████████████░░░░░░  X bulgu (%XX)
Orta      ████████████░░░░░░░░  X bulgu (%XX)
Düşük     ████░░░░░░░░░░░░░░░░  X bulgu (%XX)
```

---

## 4. Kurumsal ve Ticari Etki Değerlendirmesi

### Finansal Risk Tablosu
| Risk Kategorisi | Potansiyel Etki | Gerçekleşme Olasılığı |
|-----------------|-----------------|-----------------------|
| Veri Sızıntısı | $X - $X Milyon | Yüksek |
| Operasyon Kesintisi | Günlük $X Kayıp | Orta |
| Düzenleyici Kurum Cezaları | KVKK / GDPR Kapsamında $X | Yüksek |
| Kurumsal İtibar Kaybı | Ciddi Müşteri Kaybı | Yüksek |

### Risk Matrisi

```
                      OLASILIK
               Düşük    Orta     Yüksek
          ┌────────┬─────────┬────────┐
   Yüksek │  Orta  │ Yüksek  │ Kritik │  ETKİ
          ├────────┼─────────┼────────┤
     Orta │ Düşük  │  Orta   │ Yüksek │
          ├────────┼─────────┼────────┤
    Düşük │ Düşük  │  Düşük  │  Orta  │
          └────────┴─────────┴────────┘
```

### Tespit Edilen Temel Kurumsal Riskler

1. **Müşteri Verilerinin İfşası**
   - X sayıda müşteri kaydı yetkisiz erişime açık durumdadır
   - KVKK ve sektörel mevzuat cezaları riski doğurmaktadır

2. **İş Sürekliliği Riski**
   - Kritik sistemler hizmet aksatma saldırılarına karşı kırılgandır
   - X saatlik operasyonel duruş riski mevcuttur

---

## 5. Kritik Bulgular (Acil Eylem Gerektirenler)

### Bulgu 1: [Başlık - Ticari Etki Odaklı]
> **Risk:** Bir saldırgan [anlaşılır dille ticari etki] gerçekleştirebilir.
>
> **Etkilenen Sistem:** [İş birimi / sistem adı]
>
> **Öneri:** [Yüksek seviye çözüm yaklaşımı]

---

## 6. Önceliklendirilmiş İyileştirme Planı

### Acil Eylemler (0 - 30 Gün)
| Öncelik | Eylem | Gereken Yatırım | Risk Azaltma Oranı |
|---------|-------|-----------------|-------------------|
| 1 | [Eylem 1] | Düşük ($) | Yüksek |
| 2 | [Eylem 2] | Orta ($$) | Yüksek |

### Kısa Vadeli Eylemler (30 - 90 Gün)
| Öncelik | Eylem | Gereken Yatırım | Risk Azaltma Oranı |
|---------|-------|-----------------|-------------------|
| 3 | [Eylem 3] | Orta ($$) | Orta |
| 4 | [Eylem 4] | Yüksek ($$$) | Orta |

---

## 7. Sonuç ve Özet

[Müşteri Kurum]'un mevcut güvenlik duruşu ticari operasyonlar açısından **[YÜKSEK/ORTA/DÜŞÜK]** 
risk barındırmaktadır. Yapılan değerlendirmede **X** adet güvenlik açığı tespit edilmiş olup, 
bunların **X tanesi acil müdahale gerektiren kritik** seviyededir.

**Önerilen Yatırım:** 12 aylık süreçte $XXX,XXX - $XXX,XXX
**Beklenen Fayda:** Kurumsal siber riskte %XX oranında azalma

---

## 8. Sonraki Adımlar

| Zaman Dilimi | Eylem | Sorumlu | Durum |
|--------------|-------|---------|-------|
| Acil | Kritik bulguların kapatılması | Güvenlik Ekibi | Beklemede |
| 1. Hafta | Detaylı teknik raporun incelenmesi | BT Direktörü | Beklemede |
| 2. Hafta | İyileştirme bütçesinin onaylanması | Finans Direktörü (CFO) | Beklemede |
| 3. Ay | Kritik bulguların doğrulama testi (Retest) | Danışman Firma | Beklemede |
```

---

## 💡 Yöneticilere Rapor Yazma İpuçları

### Yapılması Gerekenler ✅
```
✓ Raporu teknik ayrıntılar yerine ticari etkiyle başlatın
✓ Sade ve kurumsal bir dil kullanın (jargondan kaçının)
✓ Net maliyet ve fayda (ROI) analizi sunun
✓ Uygulanabilir, takvime bağlanmış öneriler getirin
✓ Belgeyi maksimum 2-3 sayfa ile sınırlandırın
✓ Görsel ögelerden (grafikler, matrisler, özet tablolar) yararlanın
```

### Kaçınılması Gerekenler ❌
```
✗ CVSS, CVE veya payload kodları gibi teknik jargonlara boğmayın
✗ Her teknik bulguyu listelemeyin - kritik olanlara odaklanın
✗ Çözüm sunmaksızın yalnızca felaket senaryoları çizmekten kaçının
✗ Maliyet ve zaman çizelgesini belirtmeyi unutmayın
```

---

**Raporlamaya Dön:** [📝 Raporlama Şablonları](../../README.tr.md#📝-raporlama-şablonları-reporting-templates)
