# 💾 Bellenim Analizi Kopya Kağıdı (Firmware Analysis Cheatsheet)

---

## 📥 Bellenim Elde Etme (Firmware Acquisition)

```bash
# Üretici web sitesinden indirme
# OTA güncellemesini ağ üzerinden yakalama (MITM)
# Cihazdan doğrudan çekme (UART, SPI, JTAG)
# Mobil uygulamadan çıkarma (APK/IPA içeriğinden)
```

---

## 🔍 İlk Analiz

```bash
# Dosya türünü belirleme
file firmware.bin

# Dize (string) analizi
strings firmware.bin | grep -i password
strings firmware.bin | grep -i admin
strings firmware.bin | grep -i http

# Entropi analizi (sıkıştırma veya şifreleme tespiti)
binwalk -E firmware.bin
```

---

## 📦 Binwalk ile Dosya Çıkarma

```bash
# Bellenimi tarama
binwalk firmware.bin

# Tüm bileşenleri çıkarma
binwalk -e firmware.bin

# Belirli bir dizine çıkarma
binwalk -e -C output firmware.bin

# Özyinelemeli (recursive) çıkarma
binwalk -eM firmware.bin

# Belirli imzaları filtreleme
binwalk -D 'elf:elf' firmware.bin
```

---

## 🗂️ Dosya Sistemleri

### SquashFS
```bash
# Çıkarma
unsquashfs squashfs-root
unsquashfs -d output filesystem.squashfs

# Yeniden paketleme
mksquashfs output modified.squashfs -comp xz
```

### JFFS2
```bash
# Bağlama (Mount)
modprobe jffs2
modprobe mtdram total_size=32768 erase_size=256
dd if=jffs2.img of=/dev/mtdblock0
mount -t jffs2 /dev/mtdblock0 /mnt
```

### UBIFS
```bash
# ubi_reader ile çıkarma
ubireader_extract_files image.ubi
```

---

## 🔐 Gizli Bilgiler ve Kimlik Bilgileri

```bash
# Kimlik bilgilerini arama
grep -rni "password" filesystem/
grep -rni "admin" filesystem/
grep -rni "api_key" filesystem/
grep -rni "secret" filesystem/

# SSH anahtarlarını bulma
find . -name "id_rsa*"
find . -name "*.pem"

# Yapılandırma dosyalarını bulma
find . -name "*.conf"
find . -name "*.cfg"
cat etc/shadow
cat etc/passwd
```

---

## 🔄 İkili (Binary) Analiz

```bash
# ELF ikili dosyalarını bulma
find . -type f -exec file {} \; | grep ELF

# Ghidra ile analiz etme
ghidraRun

# Sabit kodlanmış dizeleri arama
strings binary | grep -E "http|ftp|telnet|ssh"
```

---

## 🛠️ Araçlar

| Araç | Kullanım Amacı |
|------|----------------|
| **Binwalk** | Dosya ve bellenim çıkarma |
| **firmware-mod-kit** | Bellenim içeriğini değiştirme ve yeniden paketleme |
| **EMBA** | Otomatik bellenim güvenlik analizi |
| **Ghidra** | İkili tersine mühendislik |
| **firmwalker** | Hassas veri ve gizli bilgi arama |

---

## 📋 Bellenim Analizi Kontrol Listesi

```markdown
□ Dosya türünü belirleyin
□ Entropiyi kontrol edin (şifreli/sıkıştırılmış mı?)
□ Binwalk ile içeriği çıkarın
□ Dosya sistemini bağlayın veya açın
□ Kimlik bilgileri ve API anahtarlarını arayın
□ İkili dosyaları analiz edin
□ Arka kapı (backdoor) denetimi yapın
□ Hata ayıklama arayüzlerini ve açık servisleri kontrol edin
```

---

**IoT'ye Dön:** [📡 IoT Hacking](./README.tr.md)
