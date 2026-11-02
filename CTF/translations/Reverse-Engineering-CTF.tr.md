# 🔄 Tersine Mühendislik CTF Kopya Kağıdı (Reverse Engineering CTF Cheatsheet)

---

## 🔍 İlk Analiz

```bash
# Dosya türü tespiti
file binary

# Dize analizi
strings binary | grep -i flag
strings -n 8 binary

# ELF başlık bilgisi
readelf -h binary

# Güvenlik korumaları
checksec binary
```

---

## 🛠️ Ayrıştırıcılar (Disassemblers)

### Ghidra
```
1. Dosya → İçe Aktar (File → Import File)
2. Analiz Et → Otomatik Analiz (Analyze → Auto Analyze)
3. Sembol ağacından main() fonksiyonunu bulun
4. Parola kontrolleri için strcmp, memcmp veya döngüleri inceleyin
5. Sözde koda (pseudocode) dönüştürmek için F5 tuşuna basın
```

### IDA Pro / Free
```
1. İkili dosyayı açın
2. Hex-Rays sözde kod için F5 tuşuna basın
3. Çapraz referansları (Xrefs) görmek için X tuşuna basın
4. Değişkenleri yeniden adlandırmak için N tuşuna basın
```

### Radare2
```bash
r2 binary
aaa          # Otomatik analiz
afl          # Fonksiyonları listeleme
pdf @main    # main fonksiyonunu ayrıştırma
VV           # Grafik/Görsel moda geçiş
```

---

## 🐛 GDB ile Hata Ayıklama

```bash
# Başlatma
gdb ./binary

# Kesme Noktaları (Breakpoints)
b main
b *0x401234

# Çalıştırma
r
r arg1 arg2

# Adımlama
n           # Bir sonraki satıra geç (Next)
s           # Fonksiyonun içine gir (Step into)
c           # Bir sonraki kesme noktasına kadar devam et (Continue)

# İnceleme (Examine)
x/s $rdi    # RDI yazmacındaki adreste bulunan dizeyi oku
x/10x $rsp  # Yığından (stack) 10 adet onaltılık değer oku
info reg    # Yazmaç (register) durumlarını listele

# Bellekte dize arama
find 0x0, 0xffffffff, "flag"
```

---

## 🐍 Python ile İkili Dosya Yamalama (Patching)

```python
# İkili dosyayı okuma
with open("binary", "rb") as f:
    data = bytearray(f.read())

# Baytları yamalama (Örn: koşullu atlamayı NOP ile geçme)
data[0x1234] = 0x90  # 0x90 = NOP komutu

# Yamalanmış dosyayı kaydetme
with open("patched", "wb") as f:
    f.write(data)
```

---

## 📊 Yaygın Kontrol Kalıpları

| Kontrol | Atlatma Yaklaşımı |
|---------|-------------------|
| `strcmp(input, "password")` | Bellekten veya sözde koddan sabit parolayı okuyun |
| `if (x == 0x1337)` | Karşılaştırılan onaltılık değeri tespit edin |
| Anti-Debug (ptrace kontrolü) | ptrace çağrısını NOP (0x90) ile yamalayın |
| Karartılmış (Obfuscated) Dizeler | Dinamik analizle çalışma anında bellekten okuyun |

---

## 📋 Tersine Mühendislik Kontrol Listesi

```markdown
□ file ve strings komutlarını çalıştırın
□ Sembol tablosunu ve hata ayıklama bilgilerini denetleyin
□ Ghidra veya IDA ile sözde kodu inceleyin
□ main() fonksiyonunu ve ana akış mantığını bulun
□ Dize karşılaştırmalarını ve matematiksel dönüşümleri takip edin
□ Varsa anti-debug kontrollerini yamalayın
□ GDB ile dinamik olarak çalıştırıp yazmaçları takip edin
```

---

**CTF'e Dön:** [🏁 CTF Genel Bakış](./README.tr.md)
