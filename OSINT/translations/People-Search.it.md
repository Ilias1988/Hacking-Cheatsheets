# 👤 OSINT per la ricerca di persone

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="People-Search.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="People-Search.el.md">🇬🇷 Ελληνικά</a>
</p>

```
██████╗ ███████╗ ██████╗ ██████╗ ██╗     ███████╗
██╔══██╗██╔════╝██╔═══██╗██╔══██╗██║     ██╔════╝
██████╔╝█████╗  ██║   ██║██████╔╝██║     █████╗  
██╔═══╝ ██╔══╝  ██║   ██║██╔═══╝ ██║     ██╔══╝  
██║     ███████╗╚██████╔╝██║     ███████╗███████╗
╚═╝     ╚══════╝ ╚═════╝ ╚═╝     ╚══════╝╚══════╝
███████╗███████╗ █████╗ ██████╗  ██████╗██╗  ██╗ 
██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝██║  ██║ 
███████╗█████╗  ███████║██████╔╝██║     ███████║ 
╚════██║██╔══╝  ██╔══██║██╔══██╗██║     ██╔══██║ 
███████║███████╗██║  ██║██║  ██║╚██████╗██║  ██║ 
╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝ 
```

> **Nota sugli esempi storici:** i comandi Sherlock sono conservati, ma vanno confrontati con la [documentazione ufficiale](https://sherlockproject.xyz/installation). L’installazione `pip3 install phoneinfoga` non descrive la distribuzione attuale documentata di PhoneInfoga, che usa un binario o una compilazione da sorgente ([guida ufficiale](https://sundowndev.github.io/phoneinfoga/getting-started/install/)). Il [repository Twint](https://github.com/twintproject/twint) è archiviato dal 2023: gli esempi non garantiscono compatibilità con X. Le categorie gratuito/a pagamento e i giudizi “migliore” sono quelli dell’originale e non garantiscono disponibilità o accuratezza.

---

## 🔍 Motori di ricerca di persone

### Servizi gratuiti
| Strumento | URL | Copertura |
|------|-----|----------|
| **ThatsThem** | thatsthem.com | Stati Uniti |
| **FastPeopleSearch** | fastpeoplesearch.com | Stati Uniti |
| **TruePeopleSearch** | truepeoplesearch.com | Stati Uniti |
| **Webmii** | webmii.com | Globale |
| **PeekYou** | peekyou.com | Stati Uniti |
| **Pipl** | pipl.com | Globale |

### Servizi a pagamento
| Strumento | URL | Funzionalità |
|------|-----|----------|
| **Spokeo** | spokeo.com | Ricerca approfondita e social media |
| **BeenVerified** | beenverified.com | Verifiche dei precedenti |
| **Intelius** | intelius.com | Precedenti penali |
| **US Search** | ussearch.com | Atti giudiziari |
| **Radaris** | radaris.com | Registri immobiliari |

---

## 🔎 Google dorking per la ricerca di persone

### Ricerche di base
```
# Ricerca per nome
"John Smith" site:linkedin.com
"John Smith" site:facebook.com
"John Smith" site:twitter.com

# Nome e località
"John Smith" "New York"
"John Smith" "NYC" OR "New York City"

# Nome e azienda
"John Smith" "Microsoft"
"John Smith" site:microsoft.com

# Nome ed email
"John Smith" "@gmail.com"
"John Smith" email
```

### Informazioni professionali
```
# Ricerca di curriculum/CV
"John Smith" filetype:pdf resume
"John Smith" filetype:doc CV
intitle:"curriculum vitae" "John Smith"

# Ricerche specifiche su LinkedIn
site:linkedin.com/in "John Smith"
site:linkedin.com "John Smith" "Software Engineer"

# Profili professionali
site:about.me "John Smith"
site:crunchbase.com "John Smith"
```

### Individuazione dei profili sui social media
```
# Trova tutti i profili
"John Smith" (site:facebook.com OR site:twitter.com OR site:instagram.com)

# Ricerca di nomi utente
"johnsmith123" site:twitter.com
"johnsmith123" site:reddit.com
"johnsmith123" site:github.com
```

---

## 🛠️ Strumenti da riga di comando

### Sherlock (ricerca di nomi utente)
```bash
# Installa
git clone https://github.com/sherlock-project/sherlock.git
cd sherlock
pip3 install -r requirements.txt

# Cerca il nome utente su diverse piattaforme
python3 sherlock username

# Più nomi utente
python3 sherlock user1 user2 user3

# Solo siti specifici
python3 sherlock username --site twitter instagram facebook

# Esporta i risultati
python3 sherlock username --output results.txt
python3 sherlock username --csv
python3 sherlock username --xlsx
```

### Maigret (ricerca avanzata di nomi utente)
```bash
# Installa
pip3 install maigret

# Cerca
maigret username

# Con Tor
maigret username --tor

# Output JSON
maigret username --json results.json
```

### holehe (verifica delle registrazioni tramite email)
```bash
# Installa
pip3 install holehe

# Verifica l'email su diversi servizi
holehe email@example.com

# Mostra solo i siti con una registrazione
holehe email@example.com --only-used
```

---

## 📧 Dall'email alla persona

### Individuare una persona a partire dall'email
```
1. Cerca l'email su Google: "email@example.com"
2. Consulta HIBP: haveibeenpwned.com
3. Cerca nei database delle violazioni di dati
4. Verifica le registrazioni sui social media
5. Servizi di ricerca inversa tramite email
```

### Individuazione del formato delle email
```bash
# Formati email comuni
first.last@company.com
flast@company.com
firstl@company.com
first_last@company.com
first@company.com

# Ricerca del formato con Hunter.io
https://hunter.io/email-finder
```

---

## 📱 OSINT sui numeri di telefono

### Servizi di ricerca di numeri di telefono
| Servizio | URL | Tipo |
|---------|-----|------|
| **TrueCaller** | truecaller.com | Identificazione del chiamante |
| **Sync.me** | sync.me | Identificazione del chiamante |
| **NumLookup** | numlookup.com | Ricerca gratuita |
| **SpyDialer** | spydialer.com | Ricerca inversa |
| **CallerID Test** | calleridtest.com | Informazioni sull'operatore |

### Google dork sui numeri di telefono
```
# Ricerca di base
"555-123-4567"
"+1 555 123 4567"
"5551234567"

# Trova informazioni associate
"555-123-4567" site:facebook.com
"555-123-4567" "@"
"555-123-4567" "email"
```

### OSINT sui numeri di telefono
```bash
# phoneinfoga
pip3 install phoneinfoga
phoneinfoga scan -n "+1234567890"
phoneinfoga serve -p 8080  # Interfaccia web

# Uso delle API
curl "https://api.numlookupapi.com/v1/validate/14155551234?apikey=YOUR_KEY"
```

---

## 🏠 Indirizzi e registri immobiliari

### Ricerca di immobili
| Servizio | URL | Copertura |
|---------|-----|----------|
| **Zillow** | zillow.com | Stati Uniti |
| **Redfin** | redfin.com | Stati Uniti |
| **County Assessor** | [varies] | Registri locali |
| **Whitepages** | whitepages.com | Stati Uniti |

### Registri pubblici
```
# Atti giudiziari
PACER (pacer.gov) - Tribunali federali
Siti web dei tribunali statali - Tribunali statali

# Registri elettorali
Database delle liste elettorali (variano da stato a stato)

# Registrazioni delle imprese
Siti web dei Secretary of State dei singoli stati
```

---

## 👥 Analisi delle reti sociali

### OSINT su LinkedIn
```
# Ricerca di profili
site:linkedin.com/in "John Smith"
site:linkedin.com/in "John Smith" "Company Name"

# Individua le connessioni
Consulta "People Also Viewed" (profili visualizzati anche da altri)
Controlla le connessioni in comune
Elenco dei dipendenti dell'azienda
```

> **Nota su Graph Search:** le due ricerche in linguaggio naturale restano in inglese come nell’originale; sono esempi storici, non una garanzia di funzionalità disponibile.

### OSINT su Facebook
```
# URL dei profili
facebook.com/search/people/?q=john%20smith
facebook.com/search/pages/?q=john%20smith

# Graph Search (ora con funzionalità limitate)
People named "John" who work at "Company"
People who live in "City" and like "Page"
```

### Strumenti per l'analisi dei social media
```bash
# Social Analyzer
pip3 install social-analyzer
social-analyzer --username "target" --metadata

# Twint (Twitter)
pip3 install twint
twint -u username --followers
twint -u username --following
```

---

## 📸 Ricerca di foto e volti

### Ricerca inversa delle immagini
| Servizio | URL | Ideale per |
|---------|-----|----------|
| **Google Images** | images.google.com | Uso generale |
| **TinEye** | tineye.com | Corrispondenze esatte |
| **Yandex** | yandex.com/images | Particolarmente adatto ai volti |
| **Bing Images** | bing.com/images | Buona alternativa |
| **PimEyes** | pimeyes.com | Riconoscimento facciale |

### Ricerca di volti
```
1. Yandex Images - Il più indicato per trovare volti secondo l'originale
2. PimEyes - Riconoscimento facciale a pagamento
3. Social Catfish - Ricerca inversa di immagini per i social media
```

---

## 📋 Checklist per la ricerca di persone

```markdown
## Bersaglio: [Nome completo]

### Informazioni di base
□ Nome completo anagrafico
□ Data di nascita
□ Indirizzo attuale
□ Numeri di telefono
□ Indirizzi email

### Presenza online
□ Profilo LinkedIn
□ Profilo Facebook
□ Account Twitter/X
□ Instagram
□ TikTok
□ Altri social media
□ Sito web o blog personale

### Informazioni professionali
□ Datore di lavoro attuale
□ Ruolo professionale
□ Esperienze lavorative
□ Istruzione
□ Abilitazioni professionali
□ Pubblicazioni e brevetti

### Registri
□ Registri immobiliari
□ Atti giudiziari
□ Registrazioni delle imprese
□ Iscrizione nelle liste elettorali
□ Atti di matrimonio e divorzio

### Connessioni
□ Familiari
□ Persone associate
□ Partner commerciali
□ Connessioni sociali
```

---

## ⚠️ Considerazioni legali ed etiche

```
✓ Usa solo informazioni disponibili pubblicamente
✓ Rispetta le norme sulla privacy (GDPR, CCPA)
✓ Non impersonare altre persone e non ingannare
✓ Documenta la tua metodologia
✓ Usa le informazioni solo per scopi legittimi
✗ Non perseguitare o molestare
✗ Non accedere ad account privati
✗ Non condividere pubblicamente i risultati senza consenso
```

---

**Torna a OSINT:** [🔍 Panoramica OSINT](./README.it.md)
