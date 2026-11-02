# 🤝 Hacking Cheatsheets Projesine Katkıda Bulunma

Öncelikle Hacking Cheatsheets projesine katkıda bulunmayı düşündüğünüz için teşekkür ederiz! 🎉

Bu belge, depoya katkıda bulunmanız için geçerli kuralları ve yönergeleri açıklar.

---

## 📋 İçindekiler

- [Davranış Kuralları (Code of Conduct)](#-davranış-kuralları-code-of-conduct)
- [Nasıl Katkıda Bulunabilirim?](#-nasıl-katkıda-bulunabilirim)
- [Kılavuz Kuralları & Şablonu](#-kılavuz-kuralları--şablonu)
- [Stil ve Biçimlendirme Rehberi](#-stil-ve-biçimlendirme-rehberi)
- [Pull Request (PR) Süreci](#-pull-request-pr-süreci)
- [Katkıda Bulunanların Tanınması](#-katkıda-bulunanların-tanınması)

---

## 📜 Davranış Kuralları (Code of Conduct)

### Taahhüdümüz

Tüm katılımcılar için samimi, güvenli ve kapsayıcı bir ortam sağlamaya kararlıyız.

### Standartlarımız

- ✅ Saygılı ve kapsayıcı olun
- ✅ Yapıcı geri bildirim sağlayın
- ✅ Topluluk için en iyi olana odaklanın
- ❌ Taciz, hakaret veya ayrımcılık yapmayın
- ❌ Yasa dışı içerik veya kötü niyetli kullanım talimatları paylaşmayın

### Etik İlkeler

> ⚠️ **Tüm katkılar yalnızca eğitim ve etik güvenlik testi amaçlı olmalıdır.**
> 
> Aşağıdakiler kabul edilmez:
> - Yasa dışı faaliyetleri teşvik eden içerikler
> - Yetkisiz sistem erişim talimatları
> - Kötü niyetli kullanım amaçlı zararlı yazılım (malware) veya exploit kodları

---

## 🎯 Nasıl Katkıda Bulunabilirim?

### 📝 Yeni Kılavuzlar Ekleme

Henüz projede yer almayan bir güvenlik aracında uzmanlığınız mı var? Yeni bir kılavuz oluşturun!

1. Aracın [README.tr.md](README.tr.md#-kılavuzlar-cheatsheets) listesinde olup olmadığını kontrol edin
2. Araç için yeni bir klasör açın
3. [Kılavuz Kuralları](#-kılavuz-kuralları--şablonu) bölümünü takip edin
4. Bir Pull Request gönderin

### 🔧 Mevcut Kılavuzları İyileştirme

- İmla ve dilbilgisi hatalarını düzeltin
- Eksik komutları veya faydalı parametreleri ekleyin
- Açıklamaları daha iyi gerçek hayat örnekleriyle güçlendirin
- Güncelliğini yitirmiş bilgileri yenileyin
- Hızlı başvuru tabloları ekleyin

### 🐛 Hata Bildirme (Issues)

Bir hata bulduysanız veya bir öneriniz varsa:

1. Benzer bir konunun daha önce açılıp açılmadığını kontrol edin
2. Aşağıdaki bilgileri içeren yeni bir Issue açın:
   - Net bir açıklama
   - Varsa adımları yeniden üretme yöntemi
   - Varsa çözüm öneriniz

### 🌐 Çeviriler (Translations)

Bu kılavuzların İngilizce dışındaki dilleri konuşanlara ulaşmasına yardımcı olun:

1. Araç klasöründe `translations/` adında bir alt klasör açın (örnek: `Metasploit/translations/`)
2. Dil kodunu son ek olarak ekleyerek dosyayı oluşturun: `README.tr.md`, `README.es.md`
3. Orijinal dosyanın başlık ve yapı düzenini koruyun

---

## 📖 Kılavuz Kuralları & Şablonu

### Yapı

Her kılavuz şu şablonu takip etmelidir:

```markdown
# 🔴 [Araç Adı] - Kapsamlı Başvuru Kılavuzu

[ASCII Art Başlık - İsteğe Bağlı]

[Rozetler - Badges]

> Aracın kısa ve öz tanımı

---

## 📋 İçindekiler
- [Bölüm 1](#bölüm-1)
- [Bölüm 2](#bölüm-2)
...

---

## 🎯 Giriş
Bu araç nedir? Neden ve hangi durumlarda kullanılır?

---

## 📥 Kurulum
Aracın farklı platformlara kurulum yöntemleri

---

## ⌨️ Temel Komutlar
Örneklerle birlikte temel komut kullanımları

---

## 🎬 Gerçek Hayat Örnekleri
Pratik senaryolar ve kullanım durumları

---

## 📊 Hızlı Başvuru Tablosu
Özet komut tablosu

---

## 💡 İpuçları ve En İyi Pratikler

---

## ⚠️ Yasal Uyarı

---

## 📚 Kaynaklar
Resmi dokümantasyon ve öğrenme kaynakları bağlantıları
```

### Zorunlu Bölümler

| Bölüm | Zorunlu | Açıklama |
|-------|---------|----------|
| Emojili Başlık | ✅ | Açık araç adı |
| Rozetler (Badges) | ✅ | En az 2-3 adet ilgili rozet |
| İçindekiler | ✅ | Tüm bölümlere yönlendiren linkler |
| Giriş | ✅ | Ne olduğu ve neden kullanıldığı |
| Temel Komutlar | ✅ | Çekirdek işlevsellik |
| Hızlı Başvuru Tablosu | ✅ | Özet komut tablosu |
| Yasal Uyarı | ✅ | Etik kullanım uyarısı |
| Kurulum | 📋 | Tavsiye edilir |
| Gerçek Hayat Örnekleri | 📋 | Şiddetle tavsiye edilir |
| İpuçları | 📋 | Tavsiye edilir |

---

## 🎨 Stil ve Biçimlendirme Rehberi

### Markdown Biçimlendirmesi

```markdown
# H1 - Ana Başlık (dosya başına sadece bir kez)
## H2 - Ana Bölümler
### H3 - Alt Başlıklar
#### H4 - Detay Başlıklar

Vurgu için **Kalın (Bold)**
Komutlar ve dosya adları için `kod`
Çok satırlı komutlar için ```kod blokları```
Notlar ve uyarılar için > Alıntılar (Blockquotes)
```

### Kod Blokları

Sözdizimi vurgulaması (syntax highlighting) için dili daima belirtin:

```bash
# Doğru - dil belirtilmiş
```bash
msfconsole -q
```

# Yanlış - dil belirtilmemiş
```
msfconsole -q
```
```

### Emojilerin Tutarlı Kullanımı

| Emoji | Anlamı / Kullanım Alanı |
|-------|--------------------------|
| 🔴 | İstismar / Saldırı (Exploitation) |
| 🔍 | Keşif / Tarama (Reconnaissance) |
| 🌐 | Web / Ağ (Network) |
| 🔓 | Parola / Kimlik Bilgisi (Access/Credentials) |
| ⚠️ | Uyarı (Warning) |
| 💡 | İpucu (Tip) |
| ✅ | Yapılması Gereken / Doğru |
| ❌ | Yapılmaması Gereken / Yanlış |
| 📋 | Referans / Doküman |
| 🎯 | Hedef / Amaç |

---

## 🔄 Pull Request (PR) Süreci

### Göndermeden Önce

1. ✅ Katkı kılavuzunu (bu belgeyi) okuyun
2. ✅ Aynı konuda açılmış mevcut bir PR olup olmadığını kontrol edin
3. ✅ Eklediğiniz tüm komut ve örneklerin doğruluğunu test edin
4. ✅ Yazım ve dilbilgisini kontrol edin
5. ✅ Doğru biçimlendirildiğinden emin olun

### PR Gönderme Adımları

1. Repoyu GitHub üzerinden **Fork** edin
2. Yeni bir dal (feature branch) oluşturun:
   ```bash
   git checkout -b feature/nmap-turkce-kilavuz
   ```
3. Değişikliklerinizi commit'leyin:
   ```bash
   git commit -m "Docs: Nmap Türkçe başvuru kılavuzu eklendi"
   ```
4. Kendi fork'unuza push edin:
   ```bash
   git push origin feature/nmap-turkce-kilavuz
   ```
5. GitHub üzerinde ana repoya **Pull Request** açın

### Commit Mesajı Formatı

```
Tür: Kısa başlık

- Detaylı açıklama 1
- Detaylı açıklama 2

Closes #123 (varsa ilgili issue)
```

Türler:
- `Add:` Yeni içerik
- `Fix:` Hata düzeltmeleri
- `Update:` Mevcut içerik güncellemeleri
- `Docs:` Dokümantasyon ve çeviri değişiklikleri
- `Style:` Biçimlendirme düzenlemeleri

---

## 🏆 Katkıda Bulunanların Tanınması

Projeye katkı sağlayan herkes GitHub üzerinden onurlandırılır:
- Katkıda Bulunanlar (Contributors) listesine eklenirsiniz
- Sürüm notlarında adınız anılır
- GitHub profilinizde açık kaynak katkısı olarak listelenir

---

## ❓ Sorularınız mı Var?

Bir sorunuz olursa GitHub üzerinden `question` etiketiyle bir Issue açabilirsiniz.

---

<p align="center">
  <b>Katkılarınız için teşekkür ederiz! 🙏</b><br>
  <i>Siber güvenlik bilgisini birlikte herkes için daha erişilebilir kılıyoruz.</i>
</p>
