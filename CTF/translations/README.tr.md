# 🏁 CTF Kopya Kağıtları (Capture The Flag Cheatsheets)

```
 ██████╗████████╗███████╗
██╔════╝╚══██╔══╝██╔════╝
██║        ██║   █████╗  
██║        ██║   ██╔══╝  
╚██████╗   ██║   ██║     
 ╚═════╝   ╚═╝   ╚═╝     
 ██████╗██╗  ██╗███████╗ █████╗ ████████╗███████╗██╗  ██╗███████╗███████╗████████╗███████╗
██╔════╝██║  ██║██╔════╝██╔══██╗╚══██╔══╝██╔════╝██║  ██║██╔════╝██╔════╝╚══██╔══╝██╔════╝
██║     ███████║█████╗  ███████║   ██║   ███████╗███████║█████╗  █████╗     ██║   ███████╗
██║     ██╔══██║██╔══╝  ██╔══██║   ██║   ╚════██║██╔══██║██╔══╝  ██╔══╝     ██║   ╚════██║
╚██████╗██║  ██║███████╗██║  ██║   ██║   ███████║██║  ██║███████╗███████╗   ██║   ███████║
 ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝   ╚═╝   ╚══════╝
```

---

## 📑 İçindekiler

| Kategori | Açıklama | Kılavuz |
|----------|----------|---------|
| **Web** | SQLi, XSS, SSTI, LFI, kimlik doğrulama atlatma | [📄 Görüntüle](./Web-CTF.tr.md) |
| **Kriptografi (Crypto)** | RSA, AES, hash türleri, kodlama sistemleri | [📄 Görüntüle](./Crypto-CTF.tr.md) |
| **Tersine Mühendislik (Reverse)** | Kod çözme (disassembly), hata ayıklama, yamalama | [📄 Görüntüle](./Reverse-Engineering-CTF.tr.md) |
| **Adli Bilişim (Forensics)** | Steganografi, bellek (RAM) ve disk analizi | [📄 Görüntüle](./Forensics-CTF.tr.md) |
| **Pwn / İkili İstismar** | Tampon bellek taşması (Buffer overflow), ROP, kabuk kodu | [📄 Görüntüle](./Pwn-CTF.tr.md) |

---

## 🎯 CTF Platformları

| Platform | URL | Tür |
|----------|-----|-----|
| **HackTheBox** | hackthebox.com | Sanal Makineler + CTF |
| **TryHackMe** | tryhackme.com | Rehberli öğrenme odaları |
| **PicoCTF** | picoctf.org | Yeni başlayanlar için ideal |
| **CTFtime** | ctftime.org | Küresel CTF takvimi ve puan durumu |
| **OverTheWire** | overthewire.org | Pratik wargame oyunları |
| **RootMe** | root-me.org | Güvenlik meydan okumaları |
| **CryptoHack** | cryptohack.org | Kriptografi görevleri |
| **pwnable.kr** | pwnable.kr | İkili istismar (pwn) görevleri |

---

## 🛠️ Temel CTF Araçları

| Kategori | Araçlar |
|----------|---------|
| **Web** | Burp Suite, SQLMap, ffuf, Gobuster |
| **Kriptografi** | CyberChef, RsaCtfTool, hashcat, John the Ripper |
| **Tersine Mühendislik** | Ghidra, IDA Pro, Radare2, GDB (GEF/PEDA) |
| **Adli Bilişim** | Volatility, Autopsy, Binwalk, Foremost |
| **Pwn** | pwntools, ROPgadget, checksec, gef |
| **Steganografi** | steghide, zsteg, exiftool, stegsolve |

---

## 🔥 Hızlı CTF Komutları

### Web
```bash
# SQL Enjeksiyonu
sqlmap -u "http://url?id=1" --dbs

# Dizin kaba kuvveti (brute force)
gobuster dir -u http://url -w /usr/share/wordlists/dirb/common.txt
```

### Kriptografi
```bash
# Hash tespiti
hashid 'hash_degeri'
hash-identifier

# Base64 çözme
echo "dize" | base64 -d
```

### Adli Bilişim
```bash
# Dosya türünü belirleme
file bilinmeyen_dosya
binwalk bilinmeyen_dosya

# Bayrak (flag) dizesi arama
strings -n 6 dosya | grep -i flag
```

### Pwn
```bash
# İkili korumalarını denetleme
checksec binary

# GDB ile hata ayıklama
gdb ./binary
```

---

## 📋 CTF Çözüm Metodolojisi

```
1. KEŞİF VE BİLGİ EDİNME (RECONNAISSANCE)
   - Soru açıklamasını ve ipuçlarını dikkatle okuyun
   - Verilen tüm dosyaları indirin
   - Kategoriyi belirleyin (web, crypto, pwn vb.)

2. NUMARALANDIRMA (ENUMERATION)
   - Kategoriye uygun araçları çalıştırın
   - Bariz güvenlik açıklarını kontrol edin
   - Dosya adları veya üstverilerdeki (metadata) ipuçlarını araştırın

3. İSTİSMAR (EXPLOITATION)
   - Bilinen teknikleri ve yükleri deneyin
   - Hata mesajlarını araştırın
   - Yaygın istismar senaryolarını uygulayın

4. BAYRAĞI YAKALAMA (FLAG CAPTURE)
   - Bayrak formatını arayın: CTF{...}
   - Çıktıları ve gizli alanları kontrol edin
   - Bayrağı sisteme girerek puanınızı alın
```

---

## 🏆 Yaygın Bayrak Formatları

```
Yaygın bayrak formatları:
- flag{...}
- FLAG{...}
- CTF{...}
- picoCTF{...}
- HTB{...}
- DUCTF{...}
- ozel_bayrak_formati
```

---

**Ana Sayfaya Dön:** [🔴 Hacking Kopya Kağıtları](../../README.tr.md)
