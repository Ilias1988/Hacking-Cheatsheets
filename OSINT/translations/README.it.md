# 🔍 Scheda di riferimento OSINT (Open Source Intelligence)

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
 ██████╗ ███████╗██╗███╗   ██╗████████╗
██╔═══██╗██╔════╝██║████╗  ██║╚══██╔══╝
██║   ██║███████╗██║██╔██╗ ██║   ██║   
██║   ██║╚════██║██║██║╚██╗██║   ██║   
╚██████╔╝███████║██║██║ ╚████║   ██║   
 ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝   ╚═╝   
```

> **Open Source Intelligence** (intelligence da fonti aperte) - Raccolta e analisi di informazioni disponibili pubblicamente.

> **Nota sulle versioni:** gli esempi di Sherlock conservano la sintassi originale, che può riferirsi a versioni precedenti; confrontala con la [documentazione ufficiale](https://sherlockproject.xyz/installation). Le fonti disponibili di theHarvester e i moduli di Recon-ng dipendono dalla versione e dalle API dei servizi. Le categorie “passiva” e “attiva” descrivono qui il livello di interazione, non garantiscono anonimato né autorizzazione.

---

## 📑 Indice

| Argomento | Descrizione | Guida |
|-------|-------------|-------|
| **Ricerca di persone** | Trovare informazioni sulle persone | [📄 Visualizza](./People-Search.it.md) |
| **OSINT sulle email** | Ricognizione e verifica degli indirizzi email | [📄 Visualizza](./Email-OSINT.it.md) |
| **Social media** | Indagini sui social media | [📄 Visualizza](./Social-Media-OSINT.it.md) |
| **Domini e IP** | Ricognizione su domini e indirizzi IP | [📄 Visualizza](./Domain-IP-OSINT.it.md) |
| **OSINT sulle immagini** | Ricerca inversa e analisi delle immagini | [📄 Visualizza](./Image-OSINT.it.md) |

---

## 🎯 Metodologia OSINT

```
┌─────────────────────────────────────────────────────────────┐
│                    CICLO DI VITA OSINT                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  1. REQUISITI       →  Definire le esigenze informative     │
│         ↓                                                   │
│  2. RACCOLTA        →  Raccogliere dati grezzi dalle fonti  │
│         ↓                                                   │
│  3. ELABORAZIONE    →  Rendere i dati utilizzabili          │
│         ↓                                                   │
│  4. ANALISI         →  Valutare e correlare i risultati     │
│         ↓                                                   │
│  5. DIFFUSIONE      →  Comunicare e presentare i risultati  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Strumenti OSINT essenziali

### Motori di ricerca
| Strumento | URL | Scopo |
|------|-----|---------|
| **Google** | https://google.com | Ricerca generale |
| **Bing** | bing.com | Ricerca alternativa |
| **DuckDuckGo** | duckduckgo.com | Orientato alla privacy |
| **Yandex** | yandex.com | Motore di ricerca russo (utile per le immagini) |
| **Baidu** | baidu.com | Motore di ricerca cinese |

### Ricerca di persone
| Strumento | URL | Scopo |
|------|-----|---------|
| **Pipl** | pipl.com | Ricerca approfondita di persone |
| **Spokeo** | spokeo.com | Ricerca di persone negli Stati Uniti |
| **WhitePages** | whitepages.com | Ricerca di numeri di telefono e indirizzi |
| **ThatsThem** | thatsthem.com | Ricerca gratuita di persone |
| **BeenVerified** | beenverified.com | Verifiche dei precedenti |

### Strumenti per le email
| Strumento | URL | Scopo |
|------|-----|---------|
| **Hunter.io** | hunter.io | Trovare indirizzi email aziendali |
| **Have I Been Pwned** | haveibeenpwned.com | Verifica delle violazioni di dati |
| **EmailRep** | emailrep.io | Reputazione degli indirizzi email |
| **Phonebook.cz** | phonebook.cz | Ricerca di email e domini |
| **h8mail** | GitHub | Ricerca di credenziali in violazioni di dati |

### Social media
| Strumento | URL | Scopo |
|------|-----|---------|
| **Sherlock** | GitHub | Ricerca di nomi utente |
| **Social Searcher** | social-searcher.com | Ricerca sui social media |
| **Namechk** | namechk.com | Disponibilità dei nomi utente |
| **KnowEm** | knowem.com | Verifica dei nomi utente |
| **Xquik** | https://github.com/Xquik-dev/x-twitter-scraper | API indipendente per i dati di X (Twitter): ricerca, esportazione dei follower, monitoraggio e MCP. |

### Strumenti per domini e indirizzi IP
| Strumento | URL | Scopo |
|------|-----|---------|
| **Shodan** | shodan.io | Ricerca di dispositivi |
| **Censys** | censys.io | Scansione di Internet |
| **SecurityTrails** | securitytrails.com | Storico DNS |
| **ViewDNS** | viewdns.info | Strumenti DNS |
| **BuiltWith** | builtwith.com | Identificazione delle tecnologie utilizzate |

---

## 🔥 Comandi di riferimento rapido

### theHarvester
```bash
# Raccolta di email e sottodomini
theHarvester -d target.com -l 500 -b all

# Fonti specifiche
theHarvester -d target.com -b google,linkedin,twitter

# Salva su file
theHarvester -d target.com -b all -f results.html
```

### Sherlock (ricerca di nomi utente)
```bash
# Cerca su tutte le piattaforme
python3 sherlock.py username

# Siti specifici
python3 sherlock.py username --site twitter instagram facebook

# Salva l'output su file
python3 sherlock.py username --output results.txt
```

### Maltego
```
# Tipi di trasformazioni:
- Da persona a email
- Da email a dominio
- Da dominio a IP
- Da IP a posizione geografica
- Profili sui social media
```

### Recon-ng
```bash
# Avvia recon-ng
recon-ng

# Crea un'area di lavoro (workspace)
workspaces create target_name

# Carica i moduli
modules load recon/domains-hosts/hackertarget
modules load recon/contacts-credentials/hibp_breach

# Imposta il bersaglio
options set SOURCE target.com

# Esegui il modulo
run
```

---

## 📊 Categorie OSINT

### OSINT passiva
```
Nessuna interazione diretta con il bersaglio:
- Motori di ricerca
- Social media
- Registri pubblici
- Archive.org
- Dati WHOIS
```

### OSINT attiva
```
Una certa interazione con il bersaglio:
- Scansione delle porte
- Query DNS
- Esplorazione automatizzata dei siti web (crawling)
- Verifica degli indirizzi email
```

---

## 🔒 Sicurezza operativa (OPSEC)

### Buone pratiche
```
✓ Usa VPN/Tor
✓ Crea account con identità fittizie (sock puppet)
✓ Usa macchine virtuali
✓ Non usare account personali
✓ Cancella regolarmente cookie e cache
✓ Usa profili del browser separati
✓ Documenta tutto
```

### Account con identità fittizie (sock puppet)
```
- Usa identità generate
- Mantieni un'identità coerente tra le piattaforme
- Account creati da tempo (considerati più affidabili)
- Dettagli del profilo credibili
- Non collegarli alla tua identità reale
```

---

## 📋 Checklist OSINT

```markdown
## Bersaglio: [Nome/Azienda/Dominio]

### Persone
□ Varianti del nome completo
□ Numeri di telefono
□ Indirizzi email
□ Indirizzi fisici
□ Profili sui social media
□ Esperienze professionali
□ Percorso di studi
□ Foto/Immagini

### Azienda
□ Registrazione del dominio (WHOIS)
□ Insieme delle tecnologie utilizzate (stack)
□ Elenco dei dipendenti
□ Formato degli indirizzi email
□ Presenza sui social media
□ Notizie e comunicati stampa
□ Informazioni finanziarie
□ Sedi fisiche

### Aspetti tecnici
□ Indirizzi IP
□ Sottodomini
□ Porte e servizi aperti
□ Record DNS
□ Certificati SSL
□ Dati storici
```

---

## 🔗 Framework OSINT

### OSINT Framework
```
https://osintframework.com/
- Organizzato per categoria
- Collegamenti a centinaia di strumenti
- Struttura visiva ad albero
```

### IntelTechniques
```
https://inteltechniques.com/tools/
- Strumenti di Michael Bazzell
- Strumenti di ricerca personalizzati
- Procedure di indagine
```

---

## 📚 Guide correlate in questo repository

- [Google Dorking](../../Google-Dorking/README.md)
- [GitHub Dorking](../../GitHub-Dorking/README.md)
- [Shodan](../../Shodan/README.md)
- [Amass](../../Amass/translations/README.it.md)
- [Subfinder](../../Subfinder/README.md)

---

**Torna all’indice principale:** [🔴 Hacking Cheatsheets](../../README.it.md)
