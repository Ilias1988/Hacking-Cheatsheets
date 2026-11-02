# 🔬 YARA Kuralları Kopya Kağıdı (YARA Rules Cheatsheet)

```
██╗   ██╗ █████╗ ██████╗  █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
╚██╗ ██╔╝██╔══██╗██╔══██╗██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
 ╚████╔╝ ███████║██████╔╝███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
  ╚██╔╝  ██╔══██║██╔══██╗██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
   ██║   ██║  ██║██║  ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**YARA**; metinsel veya ikili (binary) kalıplara dayanarak zararlı yazılımları tanımlamak, sınıflandırmak ve tespit etmek için kullanılan endüstri standardı bir araçtır.

---

## 📖 YARA Kural Yapısı

```yara
rule KuralAdi : etiket1 etiket2
{
    meta:
        author = "Yazar Adı"
        description = "Kuralın açıklaması ve amacı"
        date = "2024-01-15"
        reference = "https://example.com"
        hash = "abc123..."
        
    strings:
        $dize1 = "zararli metin dizesi"
        $hex1 = { 4D 5A 90 00 }       // İkili/Hex bayt dizilimi
        $regex1 = /parola[0-9]+/i      // Düzenli ifade
        
    condition:
        $dize1 or $hex1
}
```

---

## 🎯 Kural Örnekleri

### Mimikatz Tespiti
```yara
rule Mimikatz_Tespiti
{
    meta:
        author = "Guvenlik Ekibi"
        description = "Mimikatz kimlik bilgisi hırsızlığı aracını tespit eder"
        
    strings:
        $s1 = "sekurlsa::" ascii wide
        $s2 = "kerberos::" ascii wide
        $s3 = "lsadump::" ascii wide
        $s4 = "privilege::debug" ascii wide
        $s5 = "mimikatz" ascii wide nocase
        $s6 = "gentilkiwi" ascii wide
        
    condition:
        uint16(0) == 0x5A4D and 3 of ($s*)
}
```

### Cobalt Strike Beacon Tespiti
```yara
rule CobaltStrike_Beacon
{
    meta:
        description = "Cobalt Strike Beacon ikili kodlarını tespit eder"
        
    strings:
        $s1 = "%02d/%02d/%02d %02d:%02d:%02d" ascii
        $s2 = "beacon.dll" ascii
        $s3 = "ReflectiveLoader" ascii
        $pipe1 = "\\\\.\\pipe\\msagent_" ascii
        $pipe2 = "\\\\.\\pipe\\MSSE-" ascii
        
    condition:
        uint16(0) == 0x5A4D and (2 of ($s*) or any of ($pipe*))
}
```

### Genel Web Kabuğu (Webshell) Tespiti
```yara
rule Webshell_Genel
{
    meta:
        description = "PHP ve ASP tabanlı genel web kabuklarını tespit eder"
        
    strings:
        $php1 = "<?php" ascii nocase
        $asp1 = "<%@ " ascii nocase
        
        $eval1 = "eval(" ascii nocase
        $eval2 = "assert(" ascii nocase
        $exec1 = "system(" ascii nocase
        $exec2 = "shell_exec(" ascii nocase
        $exec3 = "passthru(" ascii nocase
        $exec4 = "exec(" ascii nocase
        
        $b64 = "base64_decode" ascii nocase
        $input1 = "$_REQUEST" ascii nocase
        $input2 = "$_POST" ascii nocase
        $input3 = "$_GET" ascii nocase
        
    condition:
        ($php1 or $asp1) and 
        any of ($eval*) and 
        ($b64 or any of ($input*))
}
```

### Fidye Yazılımı (Ransomware) Kalıp Tespiti
```yara
rule Ransomware_Genel
{
    meta:
        description = "Genel fidye yazılımı metin ve API kalıplarını tespit eder"
        
    strings:
        $note1 = "your files have been encrypted" ascii wide nocase
        $note2 = "bitcoin" ascii wide nocase
        $note3 = "decrypt" ascii wide nocase
        $note4 = "ransom" ascii wide nocase
        $note5 = ".onion" ascii wide
        
        $ext1 = ".encrypted" ascii wide
        $ext2 = ".locked" ascii wide
        $ext3 = ".crypto" ascii wide
        
        $api1 = "CryptEncrypt" ascii
        $api2 = "CryptGenKey" ascii
        $api3 = "CryptAcquireContext" ascii
        
    condition:
        uint16(0) == 0x5A4D and
        (2 of ($note*) or any of ($ext*)) and
        2 of ($api*)
}
```

---

## 🔧 Dize Niteleyicileri (String Modifiers)

| Niteleyici | Açıklama |
|------------|----------|
| `ascii` | 1 baytlık ASCII dizelerini arar |
| `wide` | 2 baytlık UTF-16 Unicode dizelerini arar |
| `nocase` | Büyük/küçük harf duyarsız arama yapar |
| `fullword` | Dizenin tam sözcük olarak eşleşmesini sağlar |
| `base64` | Dizenin olası Base64 kodlamalarını arar |
| `xor` | Dizenin 1 baytlık XOR işlemlerini arar |

---

## 🛠️ YARA Komut Satırı Kullanımı

```bash
# Tek bir dosyayı kuralla tarama
yara rules.yar file.exe

# Bir dizini özyinelemeli (recursive) tarama
yara -r rules.yar /path/to/scan/

# Eşleşen dizeleri ekranda gösterme
yara -s rules.yar file.exe

# Kural meta verilerini gösterme
yara -m rules.yar file.exe

# Çalışan sürecin RAM belleğini tarama
yara -p rules.yar <pid>

# Kuralları derleme (Performans artışı için)
yarac rules.yar derlenmis_kural.yarc
```

---

## 🔗 İlgili Kopya Kağıtları

- [Sigma Kuralları](./Sigma-Rules.tr.md)
- [Log Analizi](./Log-Analysis.tr.md)

---

**Önceki:** [← Sigma Kuralları](./Sigma-Rules.tr.md) | **Genel Bakışa Dön:** [🛡️ Mavi Takım](./README.tr.md)
