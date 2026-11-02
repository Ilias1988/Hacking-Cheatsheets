# 📧 Scheda di riferimento per l'OSINT sulle email

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Email-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Email-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

```
███████╗███╗   ███╗ █████╗ ██╗██╗     
██╔════╝████╗ ████║██╔══██╗██║██║     
█████╗  ██╔████╔██║███████║██║██║     
██╔══╝  ██║╚██╔╝██║██╔══██║██║██║     
███████╗██║ ╚═╝ ██║██║  ██║██║███████╗
╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚══════╝
```

> **Nota sulla verifica SMTP:** la regola `250 = exists, 550 = doesn't exist` è una semplificazione dell’originale. `250` indica l’accettazione del comando, non prova da solo che la casella sia reale; `550` può indicare anche un rifiuto per accesso o policy. Server catch-all e difese contro l’enumerazione possono rendere il risultato inconcludente ([RFC 5321](https://datatracker.ietf.org/doc/html/rfc5321)). I record MX indicano l’instradamento della posta, non l’esistenza di una specifica casella.

---

## 🔍 Strumenti per individuare indirizzi email

| Strumento | URL | Scopo |
|------|-----|---------|
| **Hunter.io** | hunter.io | Trovare indirizzi email aziendali |
| **Phonebook.cz** | phonebook.cz | Ricerca gratuita di indirizzi email |
| **Snov.io** | snov.io | Ricerca di indirizzi email |
| **Clearbit Connect** | clearbit.com | Arricchimento dei dati relativi alle email |
| **RocketReach** | rocketreach.co | Ricerca di contatti |
| **Voila Norbert** | voilanorbert.com | Ricerca di indirizzi email |

---

## 🛠️ Comandi per l'enumerazione degli indirizzi email

### theHarvester
```bash
# Raccolta di indirizzi email
theHarvester -d target.com -l 500 -b all
theHarvester -d target.com -b google,linkedin,bing
theHarvester -d target.com -b all -f emails.html
```

> **Nota terminologica:** sotto “Hunter.io CLI” l’originale mostra richieste HTTP all’API tramite `curl`, non un client CLI dedicato di Hunter.io.

### Hunter.io CLI
```bash
# Query all'API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Verifica degli indirizzi email
curl "https://api.hunter.io/v2/email-verifier?email=test@target.com&api_key=YOUR_KEY"
```

### h8mail (ricerca nelle violazioni di dati)
```bash
pip3 install h8mail
h8mail -t target@email.com
h8mail -t target@email.com -c h8mail_config.ini
```

### holehe (verifica delle registrazioni)
```bash
pip3 install holehe
holehe target@email.com
holehe target@email.com --only-used
```

---

## 📧 Schemi dei formati email

```bash
# Formati aziendali comuni
first.last@company.com     # john.smith@company.com
flast@company.com          # jsmith@company.com
firstl@company.com         # johns@company.com
first_last@company.com     # john_smith@company.com
first@company.com          # john@company.com
last.first@company.com     # smith.john@company.com
```

### Generatore di combinazioni di indirizzi email
```python
# Genera varianti degli indirizzi email
names = ["john", "smith"]
domain = "company.com"

patterns = [
    f"{names[0]}.{names[1]}@{domain}",
    f"{names[0][0]}{names[1]}@{domain}",
    f"{names[0]}{names[1][0]}@{domain}",
    f"{names[0]}_{names[1]}@{domain}",
    f"{names[0]}@{domain}",
]
```

---

## 🔐 Verifica delle violazioni di dati

| Servizio | URL | Tipo |
|---------|-----|------|
| **Have I Been Pwned** | haveibeenpwned.com | Gratuito |
| **DeHashed** | dehashed.com | A pagamento |
| **LeakCheck** | leakcheck.io | A pagamento |
| **Snusbase** | snusbase.com | A pagamento |
| **IntelX** | intelx.io | Freemium (funzioni gratuite e a pagamento) |

### HIBP API
```bash
curl -H "hibp-api-key: YOUR_KEY" \
  "https://haveibeenpwned.com/api/v3/breachedaccount/test@example.com"
```

---

## ✅ Verifica degli indirizzi email

### Metodi di verifica
```
1. Verifica dei record MX - Il dominio dispone di un server di posta
2. Verifica SMTP - Esistenza della casella di posta
3. Verifica della sintassi - Formato email valido
4. Verifica degli indirizzi usa e getta - L'email non è temporanea
5. Verifica degli indirizzi di ruolo - Non è un indirizzo generico (info@, admin@)
```

### Verifica SMTP semplice
```bash
# Verifica i record MX
nslookup -type=mx target.com
dig mx target.com

# Verifica tramite Telnet
telnet mail.target.com 25
HELO test.com
MAIL FROM:<test@test.com>
RCPT TO:<target@target.com>
# 250 = esiste, 550 = non esiste
```

---

## 📋 Checklist OSINT sulle email

```markdown
## Email bersaglio: [email@domain.com]

□ Verifica l'esistenza dell'email (SMTP/Hunter)
□ Verifica le violazioni di dati (HIBP)
□ Trova gli account associati (holehe)
□ Cerca su Google/Bing
□ Verifica le registrazioni sui social media
□ Cerca profili pubblici
□ Cerca nei siti di condivisione di testo (paste site)
□ Verifica le reti professionali
```

---

**Torna a OSINT:** [🔍 Panoramica OSINT](./README.it.md)
