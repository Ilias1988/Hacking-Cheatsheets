# ⚡ OWASP ZAP - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ____  _    _  ___   _____ _____    _____   ___  _____
  / __ \| |  | |/ _ \ / ____|  __ \  |__  /  / _ \|  __ \
 | |  | | |  | | | | | (___ | |__) |   / /  / /_\ \ |__) |
 | |  | | |/\| | |_| |\___ \|  ___/   / /  |  _  |  ___/
 | |__| \  /\  / | | |____) | |      / /__ | | | | |
  \____/ \/  \/|_| |_|_____/|_|     /_____|_| |_|_|
                                                          
    Zed Attack Proxy - Strumento gratuito per i test di sicurezza
```

<p align="center">
  <img src="https://img.shields.io/badge/OWASP-ZAP-blue?style=for-the-badge" alt="OWASP ZAP">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/Open-Source-green?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Free-Tool-purple?style=for-the-badge" alt="Free">
</p>

<p align="center">
  <b>🔒 Lo scanner gratuito di sicurezza web più utilizzato al mondo</b>
</p>

> **Nota sul contesto:** il nome “OWASP ZAP”, l’attribuzione della manutenzione, i prezzi e i confronti riportano l’originale e non descrivono necessariamente la situazione attuale. Il progetto oggi usa il nome ZAP; la [nota ufficiale sulla sua storia](https://www.zaproxy.org/blog/2023-08-01-zap-is-joining-the-software-security-project/) chiarisce il cambiamento. Nomi dei menu e opzioni possono variare secondo versione e componenti aggiuntivi: i percorsi dell’interfaccia restano in inglese per facilitarne il riconoscimento.

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Panoramica dell'interfaccia](#-panoramica-dellinterfaccia)
- [Configurazione del proxy](#-configurazione-del-proxy)
- [Test manuali](#-test-manuali)
- [Spider e crawler](#-spider-e-crawler)
- [Scansione attiva](#-scansione-attiva)
- [Scansione passiva](#-scansione-passiva)
- [Contesti e sessioni](#-contesti-e-sessioni)
- [Fuzzer](#-fuzzer)
- [API e automazione](#-api-e-automazione)
- [Riga di comando](#-riga-di-comando)
- [Componenti aggiuntivi](#-componenti-aggiuntivi)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

**OWASP ZAP** (Zed Attack Proxy) è uno strumento gratuito e open source per il penetration testing, presentato nell'originale come mantenuto da OWASP. È progettato per individuare vulnerabilità di sicurezza nelle applicazioni web.

### Perché ZAP?

| Funzionalità | Descrizione |
|---------|-------------|
| **Gratuito e open source** | Nessun costo di licenza, sviluppato dalla comunità |
| **Facile da usare** | Accessibile ai principianti e ricco di funzionalità potenti |
| **Scansione automatizzata** | Spider e scansioni attive e passive |
| **Test manuali** | Proxy di intercettazione e fuzzer (test con input variabili) |
| **Supporto API** | API REST completa per l'automazione |
| **Multipiattaforma** | Windows, Linux, macOS, Docker |
| **Estensibile** | Marketplace con oltre 100 componenti aggiuntivi |

### Confronto tra ZAP e Burp Suite

| Funzionalità | ZAP | Burp Suite |
|---------|-----|------------|
| **Prezzo** | Gratuito | $449+/year (all'anno, valore riportato nell'originale) |
| **Open source** | ✅ Sì | ❌ No |
| **Scansione automatizzata** | ✅ Completa | Solo Pro |
| **Accesso alle API** | ✅ Gratuito | Solo Pro |
| **Sviluppo attivo** | ✅ Sì | ✅ Sì |
| **Comunità** | OWASP | PortSwigger |

---

## 📥 Installazione

### Windows
```bash
# Scarica dal sito ufficiale
https://www.zaproxy.org/download/

# Oppure usa Chocolatey
choco install owasp-zap
```

### Linux (Kali/Ubuntu)
```bash
# Kali (preinstallato)
zaproxy

# Ubuntu/Debian
sudo apt install zaproxy

# Snap
sudo snap install zaproxy --classic
```

### macOS
```bash
# Homebrew
brew install --cask owasp-zap

# Scarica il DMG dal sito ufficiale
```

### Docker
```bash
# Scarica l'immagine più recente
docker pull ghcr.io/zaproxy/zaproxy:stable

# Avvia ZAP in modalità demone
docker run -u zap -p 8080:8080 -i ghcr.io/zaproxy/zaproxy:stable zap.sh -daemon -host 0.0.0.0 -port 8080

# Avvia con interfaccia grafica (Linux)
docker run -u zap -p 8080:8080 -p 8090:8090 -v /tmp:/zap/wrk/:rw \
    --rm -it ghcr.io/zaproxy/zaproxy:stable zap-webswing.sh
```

### Verifica dell'installazione
```bash
# Avvia ZAP
zaproxy
zap.sh       # Linux
zap.bat      # Windows
```

---

## 🖥 Panoramica dell'interfaccia

### Pannelli principali

| Pannello | Descrizione |
|-------|-------------|
| **Sites Tree (albero dei siti)** | Vista gerarchica dei siti visitati |
| **History (cronologia)** | Tutte le richieste e risposte |
| **Alerts (segnalazioni)** | Vulnerabilità individuate |
| **Active Scan (scansione attiva)** | Avanzamento della scansione corrente |
| **Spider** | URL esplorati dal crawler |
| **Search (ricerca)** | Ricerca in tutti i dati |
| **Request/Response (richiesta/risposta)** | Visualizza i dettagli |

### Icone della barra degli strumenti

| Icona | Funzione |
|------|----------|
| 🌐 | Scansione Quick Start |
| 🔍 | Spider |
| ⚡ | Active Scan (scansione attiva) |
| 🛑 | Break/Intercept (intercettazione) |
| 📊 | Report |
| ⚙️ | Opzioni |

### Modalità

| Modalità | Descrizione |
|------|-------------|
| **Safe** | Nessuna operazione pericolosa |
| **Protected** | Scansione dei soli bersagli inclusi nell'ambito (scope) |
| **Standard** | Scansione completa (predefinita) |
| **ATTACK** | Scansione aggressiva |

---

## 🔌 Configurazione del proxy

### Configurazione del browser

**Proxy ZAP predefinito:**
```
Host: localhost (127.0.0.1)
Port: 8080
```

### Configurazione di Firefox
```
1. Settings → Network Settings → Manual proxy
2. HTTP Proxy: 127.0.0.1  Port: 8080
3. Seleziona "Also use this proxy for HTTPS"
4. Oppure usa l'estensione FoxyProxy
```

### Importazione del certificato ZAP
```
1. Nel browser, apri: http://zap
2. Fai clic su "Download ZAP Root CA Certificate"
3. Importa nel browser:
   Firefox: Settings → Privacy → Certificates → Import
   Chrome: Settings → Security → Manage certificates
```

### Configurazione delle opzioni del proxy ZAP

> **Nota sulle versioni:** il percorso storico `Local Proxies` può differire nelle release recenti: la configurazione è documentata nel componente [Network, Local Servers/Proxies](https://www.zaproxy.org/docs/desktop/addons/network/options/localservers/).
```
Tools → Options → Local Proxies

Impostazioni:
- Address: localhost
- Port: 8080
- Behind NAT: abilita se necessario
- Remove unsupported encodings: abilita
```

### Proxy a monte (catena di proxy)
```
Tools → Options → Network → Connection

Usa una catena di proxy:
- Address: corporate-proxy.com
- Port: 8080
- Autenticazione, se richiesta
```

---

## 🔧 Test manuali

### Intercettazione delle richieste (Break)

```
1. Fai clic sul pulsante verde "Break" (🛑) nella barra degli strumenti
2. Esplora l'applicazione
3. Modifica la richiesta intercettata
4. Fai clic su "Submit and Continue" oppure "Drop"

Opzioni di intercettazione:
- Interrompi su tutte le richieste
- Interrompi su tutte le risposte
- Interrompi su un pattern URL specifico
```

### Editor delle richieste

```
Clic destro sulla richiesta → Open/Resend with Request Editor

Funzionalità:
- Modifica qualsiasi parte della richiesta
- Aggiungi o rimuovi header
- Cambia il metodo HTTP
- Invia e visualizza la risposta
```

### Richiesta manuale
```
Tools → Manual Request Editor

Oppure: Ctrl+M

Crea richieste personalizzate da zero
```

### Codifica, decodifica e hash
```
Tools → Encode/Decode/Hash

Supporta:
- Codifica e decodifica URL
- Codifica e decodifica Base64
- Codifica e decodifica HTML
- Codifica e decodifica esadecimale
- Calcolo di hash MD5, SHA1 e SHA256
```

### Funzionalità di ricerca
```
Scheda Search → inserisci il pattern

Cerca in:
- URL
- Richieste
- Risposte
- Header
- Tutto
```

---

## 🕷 Spider e crawler

### Spider standard

```
Clic destro sul bersaglio → Spider → Spider...

Oppure: Ctrl+Alt+S

Opzioni:
- Profondità massima di esplorazione
- Numero massimo di elementi figli
- Durata massima
- Gestione dei parametri
```

### Opzioni dello spider
```
Tools → Options → Spider

Impostazioni:
- Max Depth: 5-10 (predefinito: 5)
- Max Children: 10
- Max Duration: 0 (illimitata)
- Thread Count: 5
- Handle forms: Yes (gestisci i moduli)
- Post forms: Yes (invia i moduli)
```

### AJAX Spider

```
Per applicazioni che fanno ampio uso di JavaScript:

Clic destro sul bersaglio → AJAX Spider → AJAX Spider...

Richiede un browser:
- Firefox (consigliato)
- Chrome
- HtmlUnit (senza interfaccia grafica)
```

### Opzioni di AJAX Spider
```
Tools → Options → AJAX Spider

Impostazioni:
- Browser: Firefox
- Max Crawl Depth: 10
- Max Crawl States: 0 (illimitati)
- Max Duration: 60 minuti
- Fai clic sugli elementi predefiniti
- Fai clic sugli elementi con: onclick, ecc.
```

---

## ⚡ Scansione attiva

### Avvio di una scansione attiva

```
Clic destro sul bersaglio → Active Scan...

Oppure: Ctrl+Alt+A

Configura:
- Starting Point (punto iniziale)
- Context (facoltativo)
- User (per l'autenticazione)
- Policy (criteri di scansione)
- Technology (tecnologia)
```

### Criteri di scansione

```
Analyze → Scan Policy Manager

Crea o modifica i criteri:
- Quali test eseguire
- Threshold (soglia di sospetto)
- Strength (intensità dei test)
```

### Categorie dei criteri

| Categoria | Test |
|----------|-------|
| **Injection** | SQL, comandi del sistema operativo, LDAP |
| **Browser client** | XSS, DOM XSS |
| **Sicurezza del server** | Inclusione di file remoti |
| **Informazioni** | Path traversal e divulgazione di informazioni |
| **Varie** | Reindirizzamenti controllati dall'utente |

### Intensità della scansione

| Livello | Descrizione |
|-------|-------------|
| **Low (bassa)** | Poche richieste, rapida |
| **Medium (media)** | Bilanciata |
| **High (alta)** | Molte richieste, approfondita |
| **Insane (massima)** | Numero massimo di test (lenta) |

### Selezione delle tecnologie
```
Clic destro sul bersaglio → Technology → Select...

Filtra le scansioni per tecnologia:
- PHP, ASP, JSP
- MySQL, PostgreSQL, Oracle
- Apache, IIS, Nginx
- Linux, Windows
```

---

## 👁 Scansione passiva

### Che cos'è la scansione passiva?

Analizza il traffico senza inviare richieste aggiuntive.

```
Automatica - analizza continuamente il traffico che passa dal proxy

Individua:
- Header di sicurezza mancanti
- Divulgazione di informazioni
- Problemi dei cookie
- Debolezze SSL/TLS
- Divulgazione di indirizzi IP privati
```

### Regole di scansione passiva

```
Analyze → Scan Policy Manager → Passive Scan

Le regole includono:
- Application Error Disclosure (divulgazione di errori applicativi)
- Cookie Without Secure Flag (cookie senza flag Secure)
- Cross-Domain JavaScript (JavaScript tra domini)
- Information Disclosure (divulgazione di informazioni)
- Private IP Disclosure (divulgazione di IP privati)
- X-Frame-Options Header Missing (header mancante)
```

### Configurazione della scansione passiva
```
Tools → Options → Passive Scan

Impostazioni:
- Scan only in scope: consigliato
- Max alerts per rule: 10
- Tags: abilita
```

---

## 🎯 Contesti e sessioni

### Che cosa sono i contesti?

Raggruppamenti logici di URL correlati per organizzare i test.

### Creazione di un contesto
```
Clic destro su Sites → Include in Context → New Context

Oppure: File → New Context
```

### Impostazioni del contesto

| Impostazione | Scopo |
|---------|---------|
| **Include in Context (includi nel contesto)** | URL da includere |
| **Exclude from Context (escludi dal contesto)** | URL da escludere |
| **Technology (tecnologia)** | Tecnologia del server |
| **Autenticazione** | Metodo di accesso |
| **Gestione delle sessioni** | Gestione di cookie e token |
| **Utenti** | Account degli utenti di test |

### Tipi di autenticazione

| Tipo | Caso d'uso |
|------|----------|
| **Form-based (basata sui moduli)** | Moduli di accesso |
| **HTTP Basic** | Autenticazione Basic |
| **JSON-based (basata su JSON)** | Autenticazione delle API |
| **Script-based (basata su script)** | Autenticazione personalizzata |

### Autenticazione basata sui moduli
```
1. Registra la richiesta di accesso in ZAP
2. Clic destro → Include in Context
3. Context → Authentication → Form-based
4. Imposta Login URL
5. Imposta Login Request POST Data
6. Configura i parametri di nome utente e password
7. Imposta gli indicatori di accesso effettuato o non effettuato
```

### Gestione delle sessioni
```
Context → Session Management

Tipi:
- Cookie-based (basata sui cookie, la più comune)
- HTTP Auth
- Script-based (basata su script)
```

### Utenti
```
Context → Users → Add

Aggiungi utenti di test:
- Name: admin
- Credentials: admin/password123
```

---

## 🎲 Fuzzer

### Avvio del fuzzer
```
1. Seleziona la richiesta in History
2. Clic destro → Attack → Fuzz...
3. Evidenzia il punto su cui eseguire il fuzzing
4. Aggiungi i payload
5. Avvia
```

### Aggiunta di un punto di fuzzing
```
1. Nella finestra Fuzzer, evidenzia il testo da sottoporre a fuzzing
2. Fai clic su "Add..."
3. Seleziona il tipo di payload
```

### Tipi di payload

| Tipo | Descrizione |
|------|-------------|
| **Strings (stringhe)** | Elenco di stringhe personalizzato |
| **File** | Caricamento da file |
| **File Fuzzers (raccolte di payload)** | Wordlist integrate |
| **Numbers (numeri)** | Sequenze numeriche |
| **Regex** | Generati da espressioni regolari |
| **Script** | Script personalizzato |

### Fuzzer integrati
```
Add → File Fuzzers

Categorie:
- jbrofuzz (XSS, SQL, ecc.)
- dirbuster (directory)
- fuzzdb (raccolta completa)
```

### Opzioni del fuzzer
```
Opzioni:
- Thread simultanei
- Ritardo tra le richieste
- Segui i reindirizzamenti
- Mostra tutti i risultati oppure quelli filtrati
```

### Esempio di fuzzer: fuzzing di un parametro
```
1. Acquisisci: GET /page?id=1
2. Clic destro → Fuzz
3. Evidenzia "1"
4. Aggiungi payload di SQL injection
5. Avvia il fuzzer
6. Analizza i risultati per lunghezza o codice della risposta
```

---

## 🤖 API e automazione

### Abilitazione delle API
```
Tools → Options → API

Impostazioni:
- Enabled: Yes (abilitato)
- API Key: (generata automaticamente o personalizzata)
- Permitted addresses: localhost
```

### Accesso alle API
```bash
# Radice delle API
http://localhost:8080/JSON/

# Con chiave API
http://localhost:8080/JSON/core/view/alerts/?apikey=YOUR_API_KEY

# Ottieni la versione
curl "http://localhost:8080/JSON/core/view/version/?apikey=API_KEY"
```

### Endpoint API comuni

| Endpoint | Scopo |
|----------|---------|
| `/core/view/alerts/` | Ottieni tutte le segnalazioni |
| `/core/view/hosts/` | Ottieni tutti gli host |
| `/spider/action/scan/` | Avvia lo spider |
| `/ascan/action/scan/` | Avvia la scansione attiva |
| `/core/view/urls/` | Ottieni tutti gli URL |
| `/core/action/shutdown/` | Arresta ZAP |

### Esempi di API

```bash
# Esplora un URL con lo spider
curl "http://localhost:8080/JSON/spider/action/scan/?url=http://target.com&apikey=API_KEY"

# Verifica lo stato dello spider
curl "http://localhost:8080/JSON/spider/view/status/?apikey=API_KEY"

# Avvia la scansione attiva
curl "http://localhost:8080/JSON/ascan/action/scan/?url=http://target.com&apikey=API_KEY"

# Ottieni le segnalazioni
curl "http://localhost:8080/JSON/core/view/alerts/?apikey=API_KEY"

# Genera un report HTML
curl "http://localhost:8080/OTHER/core/other/htmlreport/?apikey=API_KEY" > report.html
```

### Client API Python

> **Nota sull’automazione:** le scansioni API sono asincrone; questo frammento non attende il completamento dello spider o della scansione attiva prima di leggere gli alert. È un esempio parziale, non una procedura completa. Vedi la [documentazione API](https://www.zaproxy.org/docs/api/).
```python
from zapv2 import ZAPv2

# Collegati a ZAP
zap = ZAPv2(apikey='your-api-key', proxies={'http': 'http://127.0.0.1:8080'})

# Spider
zap.spider.scan(url='http://target.com')

# Active Scan (scansione attiva)
zap.ascan.scan(url='http://target.com')

# Ottieni le segnalazioni
alerts = zap.core.alerts()
for alert in alerts:
    print(f"{alert['risk']}: {alert['name']}")
```

---

## ⌨ Riga di comando

### Opzioni di base della riga di comando
```bash
# Avvia ZAP in modalità demone
zap.sh -daemon

# Avvia su una porta specifica
zap.sh -daemon -port 8090

# Scansione rapida
zap.sh -quickurl http://target.com -quickout report.html

# Scansione baseline (Docker)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t http://target.com
```

### Framework di automazione
```bash
# Esegui la scansione automatizzata
zap.sh -cmd -autorun automation.yaml
```

### Esempio YAML di automazione
```yaml
env:
  contexts:
    - name: "Default Context"
      urls:
        - "http://target.com"

jobs:
  - type: spider
    parameters:
      url: "http://target.com"
      maxDuration: 5
      
  - type: passiveScan-wait
    parameters:
      maxDuration: 5
      
  - type: activeScan
    parameters:
      scanOnlyInScope: true
      
  - type: report
    parameters:
      template: "traditional-html"
      reportFile: "report.html"
```

### Scansioni Docker

> **Precisazione sulla baseline:** “solo passiva” riguarda le regole di analisi, non l’assenza di richieste: il baseline script esegue prima lo spider e poi attende l’analisi passiva ([documentazione ufficiale](https://www.zaproxy.org/docs/docker/baseline-scan/)).

```bash
# Scansione baseline (solo passiva)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://target.com \
    -r report.html

# Scansione completa (attiva)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-full-scan.py \
    -t https://target.com \
    -r report.html

# Scansione API
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py \
    -t https://target.com/api/openapi.json \
    -f openapi \
    -r report.html
```

---

## 🧩 Componenti aggiuntivi

### Installazione dei componenti aggiuntivi
```
Help → Check for Updates → Marketplace

Oppure: Tools → Add-ons → Marketplace
```

### Componenti aggiuntivi essenziali

| Componente aggiuntivo | Descrizione |
|--------|-------------|
| **Active Scan Rules (regole attive)** | Regole di scansione aggiuntive |
| **Regole di scansione passiva** | Regole passive aggiuntive |
| **FuzzDB** | Payload per il fuzzing |
| **Retire.js** | Rilevamento di librerie JavaScript vulnerabili |
| **AJAX Spider** | Crawler che gestisce JavaScript |
| **Selenium** | Automazione del browser |
| **Wappalyzer** | Rilevamento delle tecnologie |
| **Access Control (controllo degli accessi)** | Test del controllo degli accessi |
| **Directory List (elenco di directory)** | Wordlist di directory |

### Gestione dei componenti aggiuntivi
```
# Elenca quelli installati
Tools → Add-ons → Installed

# Aggiorna tutto
Help → Check for Updates → Update All
```

---

## 🎬 Esempi pratici

### Esempio 1: Scansione web rapida
```
1. Inserisci l'URL nella scheda Quick Start
2. Fai clic su "Attack"
3. ZAP esplora ed esegue automaticamente la scansione
4. Esamina le segnalazioni al termine
```

### Esempio 2: Test manuali e automatizzati
```
1. Configura il browser per usare il proxy ZAP
2. Esplora manualmente l'applicazione (tutte le funzionalità)
3. Clic destro sul bersaglio in Sites → Spider
4. Attendi il completamento dello spider
5. Clic destro sul bersaglio → Active Scan
6. Esamina le segnalazioni
7. Genera il report
```

### Esempio 3: Scansione autenticata
```
1. Crea un contesto per il bersaglio
2. Configura l'autenticazione Form-based
3. Aggiungi le credenziali dell'utente di test
4. Configura la modalità Forced User
5. Esplora con lo spider come utente autenticato
6. Active Scan (scansione attiva)
```

### Esempio 4: Test delle API
```
1. Importa la definizione API (OpenAPI/Swagger)
2. File → Import → Import OpenAPI definition
3. Esplora le API con lo spider
4. Active Scan (scansione attiva)
```

### Esempio 5: Integrazione CI/CD
```bash
# Pipeline Jenkins
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://staging.myapp.com \
    -r zap_report.html \
    -x zap_report.xml
```

---

## 📊 Riferimento rapido

### Azioni essenziali

| Attività | Metodo |
|------|--------|
| Avvio del proxy | ZAP si avvia automaticamente sulla porta 8080 |
| Spider | Clic destro → Spider |
| Active Scan (scansione attiva) | Clic destro → Active Scan |
| Intercettazione | Fai clic sul pulsante Break (🛑) |
| Fuzz | Clic destro sulla richiesta → Fuzz |
| Generazione del report | Report → Generate Report |

### Scorciatoie da tastiera

| Scorciatoia | Azione |
|----------|--------|
| `Ctrl+Alt+S` | Finestra Spider |
| `Ctrl+Alt+A` | Finestra Active Scan |
| `Ctrl+M` | Richiesta manuale |
| `Ctrl+B` | Attiva o disattiva Break |
| `Ctrl+F` | Ricerca |
| `Ctrl+R` | Invia nuovamente la richiesta |

### Modalità di scansione

| Modalità | Quando usarla |
|------|----------|
| **Safe** | Quando non si vogliono apportare modifiche |
| **Protected** | Test limitati a un ambito specifico |
| **Standard** | Test normali |
| **ATTACK** | Test aggressivi (con autorizzazione!) |

---

## 💡 Consigli e buone pratiche

### Consigli sulle prestazioni

1. **Limita l'ambito**
   ```
   Definisci il contesto ed esegui scansioni solo sugli elementi inclusi nell'ambito
   ```

2. **Usa AJAX Spider per le SPA**
   ```
   Le app moderne richiedono AJAX Spider, non soltanto quello standard
   ```

3. **Configura le tecnologie**
   ```
   Seleziona soltanto le tecnologie pertinenti per ridurre i tempi di scansione
   ```

### Consigli sull'accuratezza

1. **Autenticati prima**
   ```
   Configura l'autenticazione per una copertura più approfondita
   ```

2. **Esplora manualmente prima dei test automatici**
   ```
   Esplora prima l'applicazione manualmente per individuarne tutte le funzionalità
   ```

3. **Esamina tutte le segnalazioni**
   ```
   Controlla i falsi positivi e verifica quelli reali
   ```

### Procedura di lavoro

```
1. Configura il proxy
2. Esplorazione manuale (con autenticazione)
3. Spider (standard e AJAX)
4. Esame della scansione passiva
5. Scansione attiva
6. Test manuali (fuzzing e casi limite)
7. Genera il report
```

---

## ⚠ Avvertenza legale

> **ATTENZIONE:** OWASP ZAP è uno strumento per i test di sicurezza da utilizzare soltanto per **test autorizzati**.
>
> - ✅ Verifica le applicazioni di tua proprietà
> - ✅ Esegui test con un'autorizzazione scritta esplicita
> - ✅ Usalo negli ambienti di test o staging
> - ❌ Non eseguire scansioni in produzione senza autorizzazione
> - ❌ Non eseguire scansioni su sistemi non tuoi
>
> **I test di sicurezza non autorizzati sono illegali e possono comportare conseguenze penali.**

---

## 📚 Risorse

### Risorse ufficiali
- [Sito di OWASP ZAP](https://www.zaproxy.org/)
- [Documentazione ZAP](https://www.zaproxy.org/docs/)
- [ZAP GitHub](https://github.com/zaproxy/zaproxy)
- [Documentazione API ZAP](https://www.zaproxy.org/docs/api/)

### Risorse per lo studio
- [OWASP Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [ZAP in Ten](https://www.zaproxy.org/zap-in-ten/)
- [Video di approfondimento su ZAP](https://www.zaproxy.org/docs/videos/)

### Schede correlate
- [Burp Suite](../../Burp-Suite/translations/README.it.md)
- [Nikto](../../Nikto/translations/README.it.md)
- [SQLMap](../../SQLMap/translations/README.it.md)

---

<p align="center">
  <b>⚡ Padroneggia i test di sicurezza delle applicazioni web!</b><br>
  <i>Gratuito, potente e sviluppato dalla comunità</i>
</p>
