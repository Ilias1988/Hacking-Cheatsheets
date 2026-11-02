# 🎣 Scheda di riferimento sul phishing

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Phishing.md) • [🇹🇷 Türkçe Sürüm](Phishing.tr.md) • [🇬🇷 Ελληνικά](Phishing.el.md)

```
██████╗ ██╗  ██╗██╗███████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██║  ██║██║██╔════╝██║  ██║██║████╗  ██║██╔════╝ 
██████╔╝███████║██║███████╗███████║██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██║██║╚════██║██╔══██║██║██║╚██╗██║██║   ██║
██║     ██║  ██║██║███████║██║  ██║██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚═╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📊 Tipi di phishing

| Tipo | Bersaglio | Descrizione |
|------|--------|-------------|
| **Phishing** | Di massa | Email generiche inviate a molti utenti |
| **Spear phishing** | Specifico | Rivolto a singole persone |
| **Whaling** | Dirigenti | Rivolto al vertice aziendale e ai responsabili |
| **Clone phishing** | Qualsiasi | Copia di un'email legittima con contenuto malevolo |
| **Vishing** | Qualsiasi | Phishing tramite voce o telefono |
| **Smishing** | Qualsiasi | Phishing tramite SMS |
| **Pharming** | Qualsiasi | Manipolazione del DNS per reindirizzare a un sito falso |

---

## 🛠️ Configurazione e utilizzo di GoPhish

### Installazione
```bash
# Scarica GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
cd gophish

# Avvia GoPhish
./gophish

# Accedi al pannello di amministrazione
https://localhost:3333
# Credenziali iniziali: admin / consulta l'output della console
```

> **Nota sulla versione:** l’esempio usa GoPhish `v0.12.1`. Il percorso di estrazione dipende dal contenuto dello ZIP: `cd gophish` presuppone che quella cartella esista. I comandi originali sono conservati. Consulta la [documentazione ufficiale sull’installazione](https://docs.getgophish.com/user-guide/installation).

### Procedura di configurazione della campagna
```
1. Sending Profile   - Configura il server SMTP
2. Landing Page      - Crea una pagina di accesso fittizia
3. Email Template    - Prepara l'email di phishing
4. Users & Groups    - Importa l'elenco dei destinatari
5. Launch Campaign   - Invia le email di phishing
6. Monitor Results   - Monitora aperture, clic e credenziali
```

> **Nota sui modelli:** gli esempi HTML sono conservati integralmente, comprese le stringhe e le variabili `{{.FirstName}}` e `{{.URL}}`, per mantenere invariato il codice originale.

### Esempio di modello email
```html
<!DOCTYPE html>
<html>
<body>
<p>Dear {{.FirstName}},</p>
<p>Your IT department requires you to verify your account credentials.</p>
<p>Please click the link below to complete verification:</p>
<p><a href="{{.URL}}">Verify Account</a></p>
<p>This action is required within 24 hours.</p>
<br>
<p>IT Support Team</p>
</body>
</html>
```

### Esempio di landing page (pagina di destinazione)
```html
<!DOCTYPE html>
<html>
<head>
    <title>Microsoft Login</title>
</head>
<body>
    <form method="post" action="">
        <h2>Sign in</h2>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Sign in</button>
    </form>
</body>
</html>
```

---

## 🔥 Evilginx2 (bypass della 2FA)

### Installazione
```bash
# Installa Evilginx2
go install github.com/kgretzky/evilginx2@latest

# Oppure scarica il binario
wget https://github.com/kgretzky/evilginx2/releases/...

# Avvia
sudo evilginx2
```

> **Nota sulla versione:** i comandi e i nomi dei phishlet sono quelli dell’originale. Il percorso di download contenente `...` è incompleto; non è un URL di release pronto all’uso. La disponibilità dei phishlet dipende dalla versione e dai file installati.

### Configurazione di base
```bash
# Nella console di Evilginx2
config domain yourdomain.com
config ip YOUR_SERVER_IP

# Elenca i phishlet
phishlets

# Abilita il phishlet per Microsoft 365
phishlets hostname o365 login.yourdomain.com
phishlets enable o365

# Crea un lure (link esca)
lures create o365
lures get-url 0
```

### Bersagli dei phishlet
```
o365        - Microsoft 365
google      - Account Google
linkedin    - LinkedIn
twitter     - Twitter/X
github      - GitHub
instagram   - Instagram
```

---

## 📧 Raccolta di indirizzi email (email harvesting)

### theHarvester
```bash
# Raccolta di indirizzi email
theHarvester -d target.com -b all

# Fonti specifiche
theHarvester -d target.com -b google,linkedin,bing

# Salva l'output su file
theHarvester -d target.com -b all -f output.html
```

### Hunter.io
```bash
# Tramite API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Ricava il formato degli indirizzi email
curl "https://api.hunter.io/v2/email-finder?domain=target.com&first_name=John&last_name=Doe&api_key=YOUR_KEY"
```

### Enumerazione tramite LinkedIn
```bash
# Usando linkedin2username
python linkedin2username.py -u USERNAME -c COMPANY_NAME

# Ipotesi sul formato
john.doe@company.com
jdoe@company.com
j.doe@company.com
johnd@company.com
```

---

## 📮 Infrastruttura di invio

### Servizi SMTP per i test
```
- Amazon SES
- SendGrid
- Mailgun
- SMTP2GO
- Servizio autogestito (rischio elevato di classificazione come spam)
```

### Bypass di SPF/DKIM/DMARC
```bash
# Controlla il record SPF
dig txt target.com | grep spf

# Debolezze SPF da sfruttare:
# - "include:" con terze parti permissive
# - "~all" (softfail) anziché "-all"
# - Assenza di un record SPF

# Controlla DMARC
dig txt _dmarc.target.com
```

> **Precisazione su SPF:** `~all` indica un *softfail*, non un’autorizzazione del mittente. L’assenza di SPF o un softfail non garantiscono la consegna né il superamento degli altri controlli. Il titolo originale non dimostra un bypass di DKIM/DMARC. Vedi [RFC 7208, risultati SPF](https://www.rfc-editor.org/rfc/rfc7208.html#section-2.6).

### Configurazione del dominio per il phishing
```bash
# Domini simili (typosquatting)
target.com → tarqet.com
target.com → target-secure.com
target.com → target.co
target.com → target-login.com

# Controlla la disponibilità
whois potential-phish-domain.com
```

---

## 📱 Smishing (phishing tramite SMS)

### Pretesti comuni
```
- Notifica di consegna di un pacco
- Avviso di sicurezza della banca
- Vincita di un premio o di una lotteria
- Verifica dell'account
- Codice per l'autenticazione a due fattori
```

### Servizi per l'invio di SMS
```
- Twilio
- Nexmo/Vonage
- Plivo
- AWS SNS
```

### Messaggi di esempio
```
[FedEx] Il tuo pacco è in attesa. Conferma la consegna: http://short.url/abc

[BankName] Rilevata attività insolita. Verifica subito: http://secure-link.com

[Netflix] Il pagamento non è riuscito. Aggiorna i dati: http://netflix-billing.com
```

---

## 📞 Vishing (phishing vocale)

### Scenari comuni
```
□ Assistenza IT che richiede il ripristino della password
□ Ufficio antifrode della banca
□ Ente pubblico (IRS, ufficio delle imposte)
□ Truffa dell'assistenza tecnica
□ Richiesta urgente di CEO/CFO
□ Verifica del fornitore
```

### Esempio di copione per il vishing
```
"Buongiorno, sono [Name] dell'help desk IT. Abbiamo rilevato
un'attività insolita sul suo account e dobbiamo verificare le sue
credenziali per prevenire una violazione della sicurezza. Può confermare
il nome utente e la password attuale per consentirmi di verificare il suo
account?"
```

### Strumenti
```bash
# Falsificazione dell'identificativo del chiamante (caller ID spoofing)
- SpoofCard
- SpoofTel

# Modificatori della voce
- Voicemod
- Clownfish
```

---

## 🎯 SET (Social Engineering Toolkit)

### Installazione
```bash
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
sudo python setoolkit
```

### Opzioni del menu
```
1) Spear-Phishing Attack Vectors
2) Website Attack Vectors
3) Infectious Media Generator
4) Create a Payload and Listener
5) Mass Mailer Attack
```

### Raccolta delle credenziali (credential harvester)
```bash
# In SET:
1) Social-Engineering Attacks
2) Website Attack Vectors
3) Credential Harvester Attack Method
2) Site Cloner
# Inserisci l'URL da clonare (ad es. https://login.microsoft.com)
# SET ospiterà il sito fittizio e raccoglierà le credenziali
```

---

## 📝 Modelli di email di phishing

### Ripristino della password
```
Oggetto: [AZIONE RICHIESTA] Richiesta di ripristino della password

Gentile {Name},

Abbiamo ricevuto una richiesta di ripristino della tua password. Se
non sei stato tu a inviarla, fai clic qui sotto per proteggere subito il tuo
account.

[Proteggi il mio account]

Questo link scadrà tra 24 ore.

Cordiali saluti,
Team di sicurezza
```

### Assistenza IT
```
Oggetto: Manutenzione programmata dei sistemi - Azione richiesta

Gentile {Name},

Il nostro team IT eseguirà una manutenzione programmata. Per
garantire un accesso senza interruzioni, verifica le tue credenziali:

[Verifica ora]

La mancata verifica potrebbe comportare il blocco dell'account.

Reparto IT
```

### Consegna di un pacco
```
Oggetto: Non è stato possibile consegnare il tuo pacco

Non è stato possibile consegnare il tuo pacco Amazon. Ti chiediamo di
confermare l'indirizzo di consegna:

[Conferma la consegna]

Numero di tracciamento: {Random}

Grazie,
Team consegne
```

---

## 📋 Checklist della campagna di phishing

```markdown
Prima della campagna:
□ Definisci perimetro e destinatari (test autorizzati)
□ Raccogli gli indirizzi email dei destinatari
□ Registra un dominio simile
□ Prepara l'infrastruttura SMTP
□ Configura SPF/DKIM, se possibile
□ Crea la pagina di destinazione
□ Crea il modello email

Esecuzione della campagna:
□ Verifica la consegna delle email (filtri antispam)
□ Importa l'elenco dei destinatari
□ Pianifica la campagna
□ Monitora i tassi di consegna
□ Monitora aperture, clic e invii dei moduli

Dopo la campagna:
□ Genera i report
□ Analizza i tassi di clic
□ Esamina i dati raccolti
□ Individua le esigenze formative
□ Documenta i risultati
```

---

## 🔗 Schede correlate

- [Pretexting](./Pretexting.it.md)
- [OSINT - Google Dorking](../../Google-Dorking/README.md)

---

**Torna alla panoramica:** [🎭 Social engineering](./README.it.md)
