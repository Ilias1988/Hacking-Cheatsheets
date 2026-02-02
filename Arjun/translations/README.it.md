# 🔍 Arjun - Hidden Parameter Discovery

```
    █████╗ ██████╗      ██╗██╗   ██╗███╗   ██╗
   ██╔══██╗██╔══██╗     ██║██║   ██║████╗  ██║
   ███████║██████╔╝     ██║██║   ██║██╔██╗ ██║
   ██╔══██║██╔══██╗██   ██║██║   ██║██║╚██╗██║
   ██║  ██║██║  ██║╚█████╔╝╚██████╔╝██║ ╚████║
   ╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚═╝  ╚═══╝
           HTTP Parameter Discovery Suite

```
<p align="center">
  <img src="https://img.shields.io/badge/Arjun-Parameter_Discovery-blue?style=for-the-badge" alt="Arjun">
  <img src="https://img.shields.io/badge/Hidden_Params-green?style=for-the-badge" alt="Hidden">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 Tabella dei Contenuti

* [Cos'è Arjun](#-cosè-arjun)
* [Installazione](#-installazione)
* [Utilizzo di Base](#-utilizzo-di-base)
* [Discovery Mode](#-discovery-mode)
* [Opzioni Avanzate](#️-opzioni-avanzate)
* [Opzioni di Output](#-opzioni-di-output)
* [Riferimento Rapido](#-riferimento-rapido)

---

## 🎯 Cos'è Arjun

**Arjun** scopre parametri HTTP nascosti nelle applicazioni web:

* 🔍 **Parameter Discovery** - Trova parametri GET/POST nascosti
* 🎯 **Supporto JSON** - Scopre parametri all'interno di strutture JSON
* 📡 **Multi-Metodo** - Modalità GET, POST e JSON
* ⚡ **Veloce** - Gestisce le richieste con un alto livello di concorrenza

### Perché Arjun?

| Funzionalità | Vantaggio |
| --- | --- |
| Parametri Nascosti | Trova endpoint non documentati |
| Formati Multipli | Supporta GET, POST e JSON |
| Modalità Passiva | Analizza i file JavaScript |
| Importazione da Burp | Si integra nei workflow esistenti |

---

## 🚀 Installazione

```bash
# Installazione via pip (raccomandata)
pip3 install arjun

# Da sorgente
git clone https://github.com/s0md3v/Arjun.git
cd Arjun
python3 setup.py install

# Verifica
arjun --help
```

---

## 📖 Utilizzo di Base

### Singolo URL

```bash
# Discovery base dei parametri
arjun -u https://example.com/endpoint

# Con salvataggio su file
arjun -u https://example.com/endpoint -o results.txt
```

### URL Multipli

```bash
# Da file
arjun -i urls.txt

# Da stdin
cat urls.txt | arjun --stdin
```

### Specificare il Metodo HTTP

```bash
# Parametri GET (default)
arjun -u https://example.com/search -m GET

# Parametri POST
arjun -u https://example.com/api -m POST

# Body JSON
arjun -u https://example.com/api -m JSON
```

---

## 🔎 Discovery Mode

### Parametri GET

```bash
# Scoperta parametri GET
arjun -u https://example.com/search?known=value -m GET

# Esempio di output:
# [param] id
# [param] user
# [param] debug
```

### Parametri POST

```bash
# Scoperta parametri POST (form data)
arjun -u https://example.com/login -m POST

# Con inclusione di dati esistenti
arjun -u https://example.com/login -m POST --include 'username=admin'
```

### Parametri JSON

```bash
# Scoperta parametri nel body JSON
arjun -u https://example.com/api/v1/users -m JSON

# Con JSON di base incluso
arjun -u https://example.com/api -m JSON --include '{"known":"value"}'
```

### Discovery Combinata

```bash
# Tutti i metodi simultaneamente
arjun -u https://example.com/endpoint -m GET,POST,JSON
```

---

## ⚙️ Opzioni Avanzate

### Wordlist Personalizzata

```bash
# Usa una wordlist specifica
arjun -u https://example.com -w /percorso/della/wordlist.txt

# Usa i nomi dei parametri di SecLists
arjun -u https://example.com -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt
```

### Threading e Rate Limiting

```bash
# Imposta i thread (default: 2)
arjun -u https://example.com -t 10

# Limite di velocità (richieste al secondo)
arjun -u https://example.com --rate-limit 10

# Ritardo tra le richieste (in secondi)
arjun -u https://example.com --delay 1
```

### Header Personalizzati

```bash
# Aggiungi un header
arjun -u https://example.com --headers "Authorization: Bearer token"

# Header multipli
arjun -u https://example.com --headers "Cookie: session=abc;X-Custom: value"

# Da file
arjun -u https://example.com -H headers.txt
```

### Includere Parametri Noti

```bash
# Include parametri già conosciuti
arjun -u https://example.com/search --include 'q=test&page=1'

# Per POST
arjun -u https://example.com/api -m POST --include 'action=submit'
```

### Modalità Passiva

```bash
# Estrae potenziali parametri dai file JavaScript
arjun -u https://example.com --passive

# Analizza i file JS alla ricerca di nomi di parametri papabili
```

### Disabilitare i Redirect

```bash
# Non seguire i reindirizzamenti
arjun -u https://example.com --disable-redirects
```

### Timeout e Stabilità

```bash
# Imposta il timeout (secondi)
arjun -u https://example.com --timeout 15

# Modalità stabile (più affidabile, ma più lenta)
arjun -u https://example.com --stable
```

---

## 📤 Opzioni di Output

### Formati di Output

```bash
# Output testuale (default)
arjun -u https://example.com -oT results.txt

# Output JSON
arjun -u https://example.com -oJ results.json

# Entrambi i formati
arjun -u https://example.com -oT testo.txt -oJ json.json
```

### Esempio di Output JSON

```json
{
  "https://example.com/api": {
    "method": "GET",
    "params": [
      {"name": "id", "reason": "body differs"},
      {"name": "debug", "reason": "body differs"}
    ]
  }
}
```

### Importazione da Burp Suite

```bash
# Importa richieste esportate da Burp (XML)
arjun --import burp_export.xml

# Elabora file di Burp
arjun -i burp_requests.txt --burp
```

---

## 📊 Riferimento Rapido

### Comandi Base

| Comando | Descrizione |
| --- | --- |
| `arjun -u URL` | Scopre parametri |
| `arjun -u URL -m POST` | Metodo POST |
| `arjun -u URL -m JSON` | Body JSON |
| `arjun -i file.txt` | URL multipli |
| `arjun -u URL -oJ out.json` | Output JSON |

### Opzioni dei Metodi

| Flag | Descrizione |
| --- | --- |
| `-m GET` | Parametri GET |
| `-m POST` | Dati form POST |
| `-m JSON` | Body JSON |
| `-m GET,POST` | Metodi multipli |

### Opzioni di Performance

| Flag | Descrizione |
| --- | --- |
| `-t 10` | Numero di thread |
| `--rate-limit 10` | Richieste/secondo |
| `--delay 1` | Ritardo tra richieste |
| `--timeout 15` | Timeout della richiesta |

---

## 🎯 Workflow Bug Bounty

### Discovery Massivo e Pipeline

```bash
# Trova endpoint, poi scopri i parametri
gau target.com | grep -E "\.php|\.asp" | httpx -silent | \
  arjun --stdin -m GET,POST -oJ params.json

# Integrazione Katana + Arjun
katana -u https://target.com -d 3 | \
  grep -E "\.(php|asp|aspx|jsp)$" | \
  arjun --stdin -m GET,POST
```

### Testare i Parametri Scoperti

```bash
# 1. Scopri i parametri
arjun -u https://target.com/search -oJ params.json

# 2. Estrai i nomi dei parametri (usando jq)
cat params.json | jq -r '.[].params[].name' > params.txt

# 3. Test con ffuf per potenziali vulnerabilità
ffuf -u "https://target.com/search?FUZZ=test" -w params.txt
```

---

## 💡 Pro Tips

1. **Inizia in modo passivo** - Controlla prima i file JavaScript.
2. **Usa wordlist di qualità** - Quella dei nomi dei parametri di Burp è caldamente raccomandata.
3. **Attenzione al Rate Limit** - Non farti bloccare dal WAF.
4. **Testa tutti i metodi** - Prova GET, POST e JSON sullo stesso endpoint.
5. **Controlla gli Header** - Cerca parametri come `X-Custom-Param`, `X-Debug`, ecc.

### Parametri Nascosti Comuni

```text
debug, test, admin
id, user_id, uid
token, api_key, key
callback, redirect, url
format, output, type
page, limit, offset
```

---

## 📚 Risorse

- [Arjun GitHub](https://github.com/s0md3v/Arjun)
- [Burp Parameter Names](https://github.com/danielmiessler/SecLists/blob/master/Discovery/Web-Content/burp-parameter-names.txt)

### Cheatsheets Correlate
- [Bug Bounty Methodology](../Bug-Bounty-Methodology/translations/README.it.md)
- [ffuf](../ffuf/translations/README.it.md)
- [Burp Suite](../Burp-Suite/translations/README.it.md)

---

<p align="center">
  <b>🔍 Trova i Parametri Nascosti!</b>
  <i>Arjun - Il Cacciatore di Parametri</i>
</p>
