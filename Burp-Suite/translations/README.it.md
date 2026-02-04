# 🔶 Burp Suite - Cheatsheet Completo

```
    ____                      _____       _ __     
   / __ )__  ___________     / ___/__  __(_) /____
  / __  / / / / ___/ __ \    \__ \/ / / / / __/ _ \
 / /_/ / /_/ / /  / /_/ /   ___/ / /_/ / / /_/  __/
/_____/\__,_/_/  / .___/   /____/\__,_/_/\__/\___/ 
                /_/                                
    Web Application Security Testing Platform
```

<p align="center">
  <img src="https://img.shields.io/badge/Burp-Suite-orange?style=for-the-badge" alt="Burp Suite">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/PortSwigger-blue?style=for-the-badge" alt="PortSwigger">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🌐 La piattaforma standard per il testing della sicurezza delle applicazioni web</b>
</p>

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione & Setup](#-installazione--setup)
- [Configurazione Proxy](#-configurazione-proxy)
- [Intercettazione del Traffico](#-intercettazione-del-traffico)
- [Target & Scope](#-target--scope)
- [Spider/Crawler](#-spidercrawler)
- [Scanner](#-scanner)
- [Repeater](#-repeater)
- [Intruder](#-intruder)
- [Decoder](#-decoder)
- [Comparer](#-comparer)
- [Sequencer](#-sequencer)
- [Estensioni](#-estensioni)
- [Scorciatoie da Tastiera](#-scorciatoie-da-tastiera)
- [Workflow di Testing](#-workflow-di-testing)
- [Riferimenti Rapidi](#-riferimenti-rapidi)
- [Consigli & Best Practice](#-consigli--best-practice)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

**Burp Suite** è una piattaforma integrata per eseguire test di sicurezza sulle applicazioni web. Sviluppata da PortSwigger, è lo standard di settore per il penetration testing web.

### Edizioni

| Edizione | Funzionalità | Prezzo |
|----------|--------------|--------|
| **Community** | Proxy base, strumenti manuali | Gratuito |
| **Professional** | Scanner, Intruder (illimitato), Collaborator | $449/anno |
| **Enterprise** | Integrazione CI/CD, funzionalità di team | Contatta vendite |

### Strumenti Principali

| Strumento | Descrizione |
|-----------|-------------|
| **Proxy** | Intercetta e modifica traffico HTTP/HTTPS |
| **Spider** | Scansiona automaticamente le applicazioni web |
| **Scanner** | Trova vulnerabilità automaticamente |
| **Intruder** | Attacchi automatizzati e personalizzati |
| **Repeater** | Manipolazione manuale delle richieste |
| **Decoder** | Codifica/decodifica dati |
| **Comparer** | Confronta risposte |
| **Sequencer** | Analizza la casualità dei token |

---

## 📥 Installazione & Setup

### Download

```bash
# Scarica dal sito ufficiale
https://portswigger.net/burp/releases

# Disponibile per:
# - Windows (installer/standalone)
# - macOS (DMG)
# - Linux (script/JAR)
```

### Requisiti di Sistema

| Requisito | Minimo | Raccomandato |
|-----------|--------|--------------|
| RAM | 4 GB | 8+ GB |
| Disco | 500 MB | 2+ GB |
| Java | JRE 8+ | JRE incluso |
| Display | 1280x800 | 1920x1080 |

### Prima Configurazione

1. **Seleziona Tipo di Progetto**
   - Progetto temporaneo (test)
   - Nuovo progetto su disco (salva lavoro)
   - Apri progetto esistente

2. **Configurazione**
   - Usa impostazioni predefinite (consigliato per principianti)
   - Carica da file di configurazione

3. **Allocazione Memoria**
   ```bash
   # Aumenta la memoria per progetti grandi
   java -jar -Xmx4g burpsuite_pro.jar
   ```

---

## 🔌 Configurazione Proxy

### Configurazione Browser

#### Configurazione Proxy Manuale

| Browser | Dove trovare le impostazioni proxy |
|---------|-----------------------------------|
| Firefox | Impostazioni → Rete → Proxy manuale |
| Chrome | Usa proxy di sistema o estensione |
| Safari | Preferenze di sistema → Rete → Proxy |

**Proxy predefinito di Burp:**
```
Host: 127.0.0.1
Porta: 8080
```

#### Configurazione Firefox
```
1. Apri Preferenze di Firefox
2. Cerca "proxy"
3. Clicca su "Impostazioni..."
4. Seleziona "Configurazione manuale proxy"
5. HTTP Proxy: 127.0.0.1  Porta: 8080
6. Spunta "Usa questo proxy anche per HTTPS"
7. Clicca OK
```

#### Estensione FoxyProxy (Consigliata)
```
1. Installa l'estensione FoxyProxy
2. Aggiungi nuovo proxy:
   - Titolo: Burp Suite
   - Tipo Proxy: HTTP
   - IP Proxy: 127.0.0.1
   - Porta: 8080
3. Abilita durante i test
```

### Installazione Certificato SSL/TLS

```bash
# Passo 1: Con proxy attivo, visita:
http://burp

# Passo 2: Clicca "CA Certificate" per scaricare

# Passo 3: Importa certificato:
# Firefox: Impostazioni → Certificati → Importa → Fidati per siti web
# Chrome: Impostazioni → Sicurezza → Gestisci certificati → Importa
# Sistema: Aggiungi ai certificati root attendibili
```

### Proxy Listeners

```
Proxy → Options → Proxy Listeners

Predefinito: 127.0.0.1:8080

Aggiungi listener personalizzati:
- Porte diverse per test diversi
- Ascolta su tutte le interfacce (0.0.0.0) per test su mobile
- Reindirizza verso host diversi
```

### Proxy Invisibile (Per Client non Proxy-Aware)

```
1. Proxy → Options → Proxy Listeners
2. Modify Listener → Request handling
3. Abilita "Support invisible proxying"
4. Configura file host o DNS
```

---

## 🎣 Intercettazione del Traffico

### Controlli di Intercettazione

| Pulsante | Funzione |
|----------|----------|
| **Intercept is on/off** | Attiva/disattiva intercettazione |
| **Forward** | Invia richiesta al server |
| **Drop** | Scarta richiesta |
| **Action** | Menu opzioni aggiuntive |

### Opzioni di Intercettazione

```
Proxy → Options → Intercept Client Requests

Esempi di regole:
- Intercetta solo elementi in scope
- Intercetta solo tipi di file specifici
- Escludi immagini e file statici
```

### Modifica delle Richieste

```http
# Richiesta originale
GET /page.php?id=1 HTTP/1.1
Host: target.com

# Richiesta modificata (durante intercettazione)
GET /page.php?id=1' OR '1'='1 HTTP/1.1
Host: target.com
```

### Match and Replace

```
Proxy → Options → Match and Replace

Regole comuni:
- Rimuovi header di sicurezza
- Modifica User-Agent
- Aggiungi header personalizzati
- Sostituisci parametri richiesta
```

| Tipo | Match | Replace | Scopo |
|------|-------|---------|-------|
| Request header | `User-Agent: .*` | `User-Agent: Custom` | Cambia UA |
| Request body | `password=test` | `password=admin` | Test credenziali |
| Response header | `X-Frame-Options.*` | ` ` | Rimuovi header |
| Response body | `disabled` | `enabled` | Abilita pulsanti |

### Modifica delle Risposte

```
Proxy → Options → Intercept Server Responses

Abilita per:
- Rimuovere header di sicurezza
- Modificare JavaScript
- Cambiare contenuto risposta
- Testare controlli lato client
```

---

## 🎯 Target & Scope

### Aggiunta allo Scope

```
Metodo 1: Tasto destro nella Site map → Add to scope
Metodo 2: Target → Scope → Add (manuale)
Metodo 3: Dalla Proxy history → Add to scope
```

### Configurazione Scope

```
Target → Scope

Includi nello scope:
- Protocollo: Qualsiasi/HTTP/HTTPS
- Host/IP: target.com
- Porta: Qualsiasi/Specifico
- File: Pattern regex

Esempi pattern:
.*\.target\.com$          # Tutti i sottodomini
^https://target\.com/app  # Path specifico
```

### Impostazioni Avanzate Scope

```
Target → Scope → Advanced scope control

Includi:
  Protocollo: HTTPS
  Host: target.com
  Porta: 443
  File: ^/api/.*

Escludi:
  Host: .*\.google\.com
  File: .*\.(jpg|png|gif|css|js)$
```

### Site Map

```
Target → Site map

Funzionalità:
- Visualizza tutti i contenuti scoperti
- Filtra per scope
- Cerca/filtra contenuti
- Evidenzia elementi interessanti
- Confronta site map
```

---

## 🕷️ Spider/Crawler

### Avvio dello Spider

```
1. Tasto destro sul target nella Site map
2. Seleziona "Spider this host" o "Spider this branch"
3. Oppure: Target → Site map → Tasto destro → Scan → Crawl
```

### Impostazioni Spider

```
Spider → Options (legacy) OPPURE
Dashboard → New scan → Crawl

Impostazioni crawler:
- Profondità massima link (Maximum link depth)
- Tempo massimo di crawl (Maximum crawl time)
- Invio form (Forms submissions)
- Gestione login (Login handling)
```

### Configurazione Crawl

| Impostazione | Descrizione | Raccomandato |
|--------------|-------------|--------------|
| **Link depth** | Profondità link da seguire | 5-10 |
| **Max requests** | Limite totale richieste | 1000-5000 |
| **Duplicate removal** | Salta pagine simili | Abilitato |
| **Passive scanning** | Scansiona durante crawl | Abilitato |

### Invio Form

```
Spider → Options → Application Login

Configura login per:
- Credenziali singole
- Lista credenziali
- Rilevamento sessione
```

---

## 🔍 Scanner

### Tipi di Scansione

| Tipo | Descrizione | Quando usarlo |
|------|-------------|--------------|
| **Passive** | Analizza traffico proxy | Sempre attivo |
| **Active** | Invia payload per trovare vulnerabilità | Con permesso |
| **Full** | Scansione completa | Se hai tempo |
| **Custom** | Solo test specifici | Test mirati |

### Avvio Scansione

```
Metodo 1: Tasto destro → Scan
Metodo 2: Dashboard → New scan
Metodo 3: Avvia Scanner da altri strumenti
```

### Configurazione Scansione

```
Dashboard → New scan → Scan configuration

Configurazioni predefinite:
- Audit checks - all except JavaScript analysis
- Audit checks - critical issues only
- Audit checks - light, active
- Audit checks - medium, active
- Audit checks - full, active
```

### Categorie di Vulnerabilità

```
Lo scanner cerca:

Injection:
- SQL Injection
- OS Command Injection
- LDAP Injection
- XML/XPath Injection

Cross-Site Scripting:
- Reflected XSS
- Stored XSS
- DOM-based XSS

Autenticazione:
- Broken authentication
- Problemi gestione sessione
- Password deboli

Altro:
- CSRF
- Directory traversal
- File inclusion
- Information disclosure
- Problemi lato server
```

### Risultati Scansione

```
Dashboard → Issue activity
Target → Site map → Issues

Per ogni issue:
- Gravità (Alta/Media/Bassa/Info)
- Confidenza (Certa/Forte/Tentativa)
- Descrizione
- Remediation
- Evidenza (richiesta/risposta)
```

### Profili di Scansione Personalizzati

```
1. Dashboard → New scan
2. Seleziona "Scan configuration"
3. Clicca "New" o modifica esistente
4. Salva per riutilizzo

Personalizza:
- Quali vulnerabilità testare
- Intensità payload
- Frequenza richieste
- Gestione autenticazione
```

---

## 🔁 Repeater

### Scopo

Test manuale e manipolazione di singole richieste.

### Invia al Repeater

```
1. Tasto destro su una richiesta
2. Seleziona "Send to Repeater"
3. Scorciatoia: Ctrl+R
```

### Utilizzo del Repeater

```
1. Modifica richiesta nel pannello sinistro
2. Clicca "Send" (o Ctrl+Space)
3. Visualizza risposta nel pannello destro
4. Confronta risposte
```

### Funzionalità Repeater

| Funzione | Descrizione |
|----------|-------------|
| **Multiple tabs** | Testa più richieste |
| **Request history** | Naviga con < > |
| **Follow redirects** | Attiva/disattiva auto-follow |
| **Process cookies** | Aggiorna cookie automaticamente |
| **Content-Length** | Aggiorna header automaticamente |

### Esempi di Test

```http
# Test SQL Injection
GET /user.php?id=1' HTTP/1.1

# Test XSS
GET /search?q=<script>alert(1)</script> HTTP/1.1

# Test bypass autenticazione
GET /admin HTTP/1.1
Cookie: role=admin

# Test IDOR
GET /api/user/1 HTTP/1.1  # Il tuo utente
GET /api/user/2 HTTP/1.1  # Altro utente
```

---

## 🔫 Intruder

### Tipi di Attacco

| Tipo | Posizioni | Payload | Quando usarlo |
|------|-----------|---------|--------------|
| **Sniper** | 1+ | 1 set, uno alla volta | Test singolo parametro |
| **Battering Ram** | 1+ | 1 set, stesso ovunque | Stesso valore ovunque |
| **Pitchfork** | 1+ | Più set, paralleli | Coppie username+password |
| **Cluster Bomb** | 1+ | Più set, tutte combinazioni | Brute force completo |

### Invia all'Intruder

```
1. Tasto destro sulla richiesta
2. Seleziona "Send to Intruder"
3. Scorciatoia: Ctrl+I
```

### Impostazione Posizioni

```
Intruder → Positions

Segna i punti di iniezione con i simboli §:

GET /login.php?user=§admin§&pass=§test§ HTTP/1.1

Opzioni:
- Add § - Segna selezione
- Clear § - Rimuovi marcatori
- Auto § - Rileva automaticamente
```

### Tipi di Payload

| Tipo | Descrizione |
|------|-------------|
| **Lista semplice** | Wordlist personalizzata |
| **File runtime** | Carica da file |
| **Numeri** | Numeri sequenziali/casuali |
| **Date** | Sequenze di date |
| **Brute forcer** | Combinazioni di caratteri |
| **Null payloads** | Payload vuoti (test velocità) |
| **Generatore username** | Pattern comuni username |

### Elaborazione Payload

```
Intruder → Payloads → Payload Processing

Aggiungi regole:
- Prefisso/suffisso
- URL encode
- Base64 encode
- Hash (MD5, SHA)
- Match/replace
```

### Grep - Match

```
Intruder → Options → Grep - Match

Aggiungi stringhe da cercare nelle risposte:
- "Login successful"
- "Invalid password"
- "error"
- Regex personalizzato

Aiuta a identificare attacchi riusciti.
```

### Esempi di Attacco

#### Brute Force Login
```
POST /login HTTP/1.1
Content-Type: application/x-www-form-urlencoded

username=§admin§&password=§password§

Attack type: Cluster bomb
Payload 1: Username list
Payload 2: Password list
```

#### Fuzzing Parametri
```
GET /api/user/§1§ HTTP/1.1

Attack type: Sniper
Payload: Numbers 1-1000
```

#### Content Discovery
```
GET /§admin§ HTTP/1.1

Attack type: Sniper
Payload: Directory wordlist
Filter: Status code != 404
```

### Analisi Risultati

```
Dopo l'attacco:
- Ordina per lunghezza (trova risposte diverse)
- Ordina per status code
- Filtra con match tramite grep
- Esporta risultati
```

---

## 🔓 Decoder

### Codifica/Decodifica

```
Tab Decoder

Operazioni:
- URL encode/decode
- HTML encode/decode
- Base64 encode/decode
- Hex encode/decode
- Gzip compress/decompress
- ASCII Hex
- Binario
```

### Smart Decode

```
Decoder → Smart decode

Rileva e decodifica automaticamente:
- Base64
- URL encoding
- Entità HTML
- Codifiche combinate
```

### Usi Comuni

```
# Decodifica token JWT
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
→ Decodifica come Base64

# Decodifica parametro URL
%3Cscript%3Ealert(1)%3C%2Fscript%3E
→ URL decode
→ <script>alert(1)</script>

# Codifica payload
<script>alert(1)</script>
→ Base64 encode
→ Usa nell'attacco
```

---

## 📊 Comparer

### Scopo

Confronta due elementi (richieste, risposte o qualsiasi dato).

### Come Usarlo

```
1. Tasto destro su elemento 1 → Send to Comparer
2. Tasto destro su elemento 2 → Send to Comparer
3. Vai al tab Comparer
4. Seleziona elementi e clicca "Words" o "Bytes"
```

### Modalità di Confronto

| Modalità | Descrizione |
|----------|-------------|
| **Words** | Confronto parola per parola |
| **Bytes** | Confronto byte per byte (esatto) |

### Casi d'Uso

```
- Confronta risposte login riuscito vs fallito
- Trova differenze nei token di sessione
- Confronta prima/dopo modifiche
- Identifica campi nascosti nei form
```

---

## 🔢 Sequencer

### Scopo

Analizza la qualità della casualità nei token (session ID, CSRF token).

### Come Usarlo

```
1. Cattura richiesta che restituisce token
2. Tasto destro → Send to Sequencer
3. Configura posizione token
4. Avvia acquisizione live
5. Analizza risultati
```

### Metriche di Analisi

| Metrica | Valore Buono | Descrizione |
|---------|--------------|-------------|
| **Qualità complessiva** | >100 bit | Punteggio casualità |
| **Livello carattere** | Alta entropia | Casualità per carattere |
| **Livello bit** | Alta entropia | Casualità per bit |

### Interpretazione

```
Risultati:
- Eccellente: Token crittograficamente casuale
- Buono: Accettabile per la maggior parte degli usi  
- Scarso: Potenzialmente prevedibile
- Molto scarso: Token forse indovinabile
```

---

## 🧩 Estensioni

### BApp Store

```
Extender → BApp Store

Estensioni popolari:
```

### Estensioni Consigliate

| Estensione | Descrizione |
|------------|-------------|
| **Autorize** | Test autorizzazione |
| **Logger++** | Logging avanzato |
| **Retire.js** | Rileva librerie JS vulnerabili |
| **Active Scan++** | Check di scansione aggiuntivi |
| **AuthMatrix** | Test matrice autorizzazioni |
| **Turbo Intruder** | Intruder alternativo veloce |
| **Param Miner** | Scoperta parametri nascosti |
| **JSON Web Tokens** | Analisi/manipolazione JWT |
| **Hackvertor** | Codifica/decodifica avanzata |
| **CO2** | Suite di strumenti utili |

### Installazione Estensioni

```
Metodo 1: BApp Store
1. Extender → BApp Store
2. Trova estensione
3. Clicca Install

Metodo 2: Manuale
1. Scarica file .jar o .py
2. Extender → Extensions → Add
3. Seleziona tipo file e percorso
```

### Impostazioni Estensioni

```
Extender → Options

- Python environment (per estensioni Python)
- Java environment settings
- Logging options
```

---

## ⌨️ Scorciatoie da Tastiera

### Scorciatoie Globali

| Scorciatoia | Azione |
|-------------|--------|
| `Ctrl+I` | Invia all'Intruder |
| `Ctrl+R` | Invia al Repeater |
| `Ctrl+S` | Cerca |
| `Ctrl+F` | Inoltra (Proxy) |
| `Ctrl+D` | Scarta (Proxy) |
| `Ctrl+T` | Attiva/disattiva Intercept |
| `Ctrl+Shift+T` | Attiva/disattiva Intercept Proxy |

### Navigazione Tab

| Scorciatoia | Azione |
|-------------|--------|
| `Ctrl+Shift+D` | Dashboard |
| `Ctrl+Shift+T` | Target |
| `Ctrl+Shift+P` | Proxy |
| `Ctrl+Shift+I` | Intruder |
| `Ctrl+Shift+R` | Repeater |

### Nel Repeater

| Scorciatoia | Azione |
|-------------|--------|
| `Ctrl+Space` | Invia richiesta |
| `Ctrl+G` | Vai a (cronologia richieste) |
| `Ctrl+Plus` | Nuova tab |

### Nell'Editor

| Scorciatoia | Azione |
|-------------|--------|
| `Ctrl+U` | Codifica URL della selezione |
| `Ctrl+Shift+U` | Decodifica URL della selezione |
| `Ctrl+B` | Codifica Base64 della selezione |
| `Ctrl+Shift+B` | Decodifica Base64 della selezione |

---

## 🎯 Workflow di Testing

### Flusso Standard Test Web App

```
Step 1: Setup
├── Configura proxy browser
├── Installa certificato SSL
└── Definisci scope

Step 2: Ricognizione
├── Naviga applicazione manualmente
├── Avvia Spider/Crawler
└── Rivedi Site map

Step 3: Analisi
├── Controlla Proxy history
├── Rivedi risultati passive scan
└── Identifica superficie d'attacco

Step 4: Testing
├── Testa con Repeater (manuale)
├── Testa con Intruder (automatizzato)
├── Avvia active scan
└── Usa estensioni

Step 5: Reporting
├── Rivedi tutti i risultati
├── Genera report
└── Documenta evidenze
```

### Checklist di Testing

```
Autenticazione:
□ Brute force login
□ Policy password
□ Blocco account
□ Gestione sessione
□ Funzione "ricordami"

Autorizzazione:
□ Test IDOR
□ Privilege escalation
□ Accesso a livello funzione

Validazione Input:
□ SQL injection
□ XSS (riflesso/stored/DOM)
□ Command injection
□ Upload file
□ XXE

Sessione:
□ Session fixation
□ Timeout sessione
□ Randomness token

Business Logic:
□ Manipolazione prezzi
□ Bypass workflow
□ Race conditions
```

---

## 📊 Riferimenti Rapidi

### Riepilogo Strumenti

| Strumento | Uso Principale | Scorciatoia |
|-----------|---------------|-------------|
| Proxy | Intercetta traffico | Ctrl+T (toggle) |
| Repeater | Test manuale | Ctrl+R (send to) |
| Intruder | Attacchi automatizzati | Ctrl+I (send to) |
| Scanner | Trova vulnerabilità | Tasto destro → Scan |
| Spider | Scoperta contenuti | Tasto destro → Spider |
| Decoder | Codifica/Decodifica | - |
| Comparer | Diff risposte | Send to Comparer |
| Sequencer | Analisi token | Send to Sequencer |

### Riferimento Tipi Attacco Intruder

| Attacco | Quando usarlo |
|---------|--------------|
| **Sniper** | Testa un parametro alla volta |
| **Battering Ram** | Stesso payload ovunque |
| **Pitchfork** | Coppie username:password |
| **Cluster Bomb** | Tutte le combinazioni username × password |

### Codici di Stato Comuni

| Codice | Significato | Rilevanza Intruder |
|--------|-------------|--------------------|
| 200 | OK | Risposta normale |
| 301/302 | Redirect | Può indicare successo |
| 401 | Non autorizzato | Credenziali errate |
| 403 | Vietato | Accesso negato |
| 404 | Non trovato | Path non valido |
| 500 | Errore server | Possibile vulnerabilità |

---

## 💡 Consigli & Best Practice

### Consigli Performance

1. **Limita Scope**
   ```
   Tieni solo i domini target nello scope
   Riduce il rumore nella Proxy history
   ```

2. **Disabilita Estensioni**
   ```
   Disabilita estensioni inutilizzate per performance
   ```

3. **Usa File Progetto**
   ```
   Salva spesso il lavoro
   Usa file progetto per test grandi
   ```

### Consigli di Testing

1. **Inizia Passivo**
   ```
   Lascia che lo scanner passivo trovi vulnerabilità semplici
   Rivedi prima di avviare lo scanner attivo
   ```

2. **Usa prima Repeater**
   ```
   Il test manuale rivela di più
   Comprendi l'applicazione prima dell'automazione
   ```

3. **Organizza con Tab**
   ```
   Dai nomi significativi ai tab di Repeater
   Usa nomi attacchi in Intruder
   ```

### Evitare Rilevamento

```
Impostazioni per stealth:

Opzioni progetto → Connessioni:
- Limita richieste
- Ritardi casuali
- Limite connessioni concorrenti

Intruder → Options:
- Ritardo richieste
- Orario di inizio
```

### Risoluzione Problemi

| Problema | Soluzione |
|----------|-----------|
| Non intercetta HTTPS | Installa certificato CA |
| Performance lenta | Riduci scope, disabilita estensioni |
| Mancano richieste | Controlla "Intercept Server Responses" |
| Scanner non trova vulnerabilità | Aumenta intensità scan |

---

## ⚠️ Disclaimer Legale

> **ATTENZIONE:** Burp Suite è uno strumento potente per il testing di sicurezza e va usato **solo per test autorizzati**.
> 
> - ✅ Testa applicazioni di tua proprietà
> - ✅ Testa con permesso scritto esplicito
> - ✅ Testa in ambienti dedicati
> - ❌ Mai scansionare sistemi di produzione senza autorizzazione
> - ❌ Mai usare per accessi non autorizzati
> 
> **Il testing di sicurezza non autorizzato è illegale e può portare a conseguenze penali.**

---

## 📚 Risorse

### Risorse Ufficiali
- [PortSwigger Web Security Academy](https://portswigger.net/web-security)
- [Documentazione Burp Suite](https://portswigger.net/burp/documentation)
- [BApp Store](https://portswigger.net/bappstore)

### Risorse di Apprendimento
- [OWASP Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Cheatsheet Correlate
- [SQLMap](../SQLMap/translations/README.it.md)
- [Metasploit Framework](../Metasploit/translations/README.it.md)

---

<p align="center">
  <b>🔶 Diventa un esperto di Web Application Security Testing!</b><br>
  <i>Il modo migliore per imparare è fare pratica: crea un laboratorio e sperimenta!</i>
</p>
