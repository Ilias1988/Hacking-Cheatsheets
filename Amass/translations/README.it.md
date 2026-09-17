# 🌐 Amass - Attack Surface Mapping

```
    █████╗ ███╗   ███╗ █████╗ ███████╗███████╗
   ██╔══██╗████╗ ████║██╔══██╗██╔════╝██╔════╝
   ███████║██╔████╔██║███████║███████╗███████╗
   ██╔══██║██║╚██╔╝██║██╔══██║╚════██║╚════██║
   ██║  ██║██║ ╚═╝ ██║██║  ██║███████║███████║
   ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
           In-depth Attack Surface Mapping
```

<p align="center">
  <img src="https://img.shields.io/badge/Amass-OWASP-blue?style=for-the-badge" alt="Amass">
  <img src="https://img.shields.io/badge/Subdomain-Discovery-green?style=for-the-badge" alt="Subdomain">
  <img src="https://img.shields.io/badge/ASN-Mapping-red?style=for-the-badge" alt="ASN">
</p>

---

## 📋 Tabella dei Contenuti

* [Cos'è Amass](#-cosè-amass)
* [Installazione](#-installazione)
* [Enumeration Mode](#-enumeration-mode)
* [Intel Mode](#-intel-mode)
* [DNS Mode](#-dns-mode)
* [Configurazione](#️-configurazione)
* [Riferimento Rapido](#-riferimento-rapido)

---

## 🎯 Cos'è Amass

**Amass** è un progetto OWASP per la mappatura della rete e il discovery di asset esterni:

* 🔍 **Enumeration Sottodomini** - Lo strumento più completo disponibile
* 🏢 **ASN Discovery** - Trova i range IP di un'organizzazione
* 📡 **DNS Intelligence** - Ricognizione attiva e passiva
* 🗺️ **Attack Surface Mapping** - Visualizza l'infrastruttura

### Perché Amass?

| Funzionalità | Vantaggio |
| --- | --- |
| Oltre 55 fonti dati | Risultati estremamente completi |
| Passivo e Attivo | Scegli il livello di "rumore" |
| ASN/Org Intel | Trova infrastrutture nascoste |
| Graph Database | Visualizza le relazioni |

---

## 🚀 Installazione

```bash
# Go install
go install -v github.com/owasp-amass/amass/v4/...@master

# Kali Linux
sudo apt install amass

# Docker
docker pull caffix/amass

# Verifica
amass -version
```

---

## 🔍 Enumeration Mode

### Enumeration Sottodomini Base

```bash
# Enumeration passiva (nessun contatto diretto con il target)
amass enum -passive -d example.com

# Enumeration attiva (DNS brute forcing)
amass enum -active -d example.com

# Brute force con wordlist
amass enum -active -brute -d example.com -w wordlist.txt
```

### Opzioni di Output

```bash
# Salva su file
amass enum -passive -d example.com -o subdomains.txt

# Output in formato JSON
amass enum -passive -d example.com -json output.json

# Salva in una directory specifica
amass enum -passive -d example.com -dir ./amass_output/
```

### Target Multipli

```bash
# Domini multipli
amass enum -passive -d example.com -d target.com

# Da file
amass enum -passive -df domains.txt -o results.txt
```

### Enumeration Avanzata

```bash
# Imposta timeout (minuti)
amass enum -passive -d example.com -timeout 30

# Limita le fonti
amass enum -passive -d example.com -src -include CertSpotter,Crtsh,HackerTarget

# Escludi fonti
amass enum -passive -d example.com -exclude Google,Bing

# Mostra le fonti dati utilizzate
amass enum -passive -d example.com -src
```

### Enumeration Ricorsiva

```bash
# Abilita la ricerca ricorsiva
amass enum -passive -d example.com -r

# Imposta profondità di ricorsione
amass enum -passive -d example.com -max-depth 3
```

---

## 🏢 Intel Mode

> La intel mode scopre domini radice, ASN e range CIDR associati alle organizzazioni.

### Trovare Asset di un'Organizzazione

```bash
# Per nome dell'organizzazione
amass intel -org "Tesla Inc"
amass intel -org "Microsoft"

# WHOIS inverso
amass intel -whois -d example.com
```

### ASN Discovery

```bash
# Trova l'ASN per un dominio
amass intel -d example.com

# Enumeration per ASN
amass intel -asn 13335
amass intel -active -asn 13335

# ASN multipli
amass intel -asn 13335,15169
```

### Range CIDR

```bash
# Trova asset in un CIDR
amass intel -active -cidr 192.168.1.0/24

# CIDR multipli
amass intel -cidr 192.168.1.0/24 -cidr 10.0.0.0/8
```

### IP Range

```bash
# Range di indirizzi IP
amass intel -active -ip 192.168.1.1-254
```

---

## 📡 DNS Mode

### Query DNS

```bash
# Lookup DNS base
amass dns -d example.com

# Tipi di record specifici
amass dns -d example.com -t A,AAAA,MX,NS,TXT
```

### Risoluzione Sottodomini

```bash
# Risolve i sottodomini scoperti
amass dns -df subdomains.txt
```

---

## ⚙️ Configurazione

### Posizione File di Configurazione

```bash
# Posizione predefinita
~/.config/amass/config.yaml

# Specifica una configurazione personalizzata
amass enum -config ./my_config.yaml -d example.com
```

### Esempio di File Config

```yaml
# config.yaml
scope:
  domains:
    - example.com
    - target.com

resolvers:
  - 8.8.8.8
  - 8.8.4.4
  - 1.1.1.1

options:
  log: /tmp/amass.log
  max_dns_queries: 10000

data_sources:
  Crtsh:
  - 
  SecurityTrails:
  - credentials:
      apikey: TUA_API_KEY
  Shodan:
  - credentials:
      apikey: TUA_SHODAN_KEY
  VirusTotal:
  - credentials:
      apikey: TUA_VT_KEY
```

### Fonti Dati con Chiavi API

| Fonte | Piano Free | API Richiesta |
| --- | --- | --- |
| Crt.sh | ✅ | No |
| SecurityTrails | ✅ | Sì |
| Shodan | ✅ | Sì |
| VirusTotal | ✅ | Sì |
| Censys | ✅ | Sì |
| PassiveTotal | Limitato | Sì |
| BinaryEdge | Limitato | Sì |

---

## 📊 Riferimento Rapido

### Comandi Enumeration

| Comando | Descrizione |
| --- | --- |
| `amass enum -passive -d domain.com` | Enum passiva sottodomini |
| `amass enum -active -d domain.com` | Enum DNS attiva |
| `amass enum -active -brute -d domain.com` | Brute force sottodomini |
| `amass enum -d domain.com -o out.txt` | Salva su file |
| `amass enum -d domain.com -json out.json` | Output JSON |

### Comandi Intel

| Comando | Descrizione |
| --- | --- |
| `amass intel -org "Azienda"` | Trova asset per nome org |
| `amass intel -asn 12345` | Enumeration per ASN |
| `amass intel -cidr 10.0.0.0/8` | Enumeration range CIDR |
| `amass intel -whois -d domain.com` | WHOIS inverso |

### Flag Essenziali

| Flag | Descrizione |
| --- | --- |
| `-d` | Dominio target |
| `-df` | Domini da file |
| `-o` | File di output |
| `-passive` | Solo passivo (no DNS attivo) |
| `-active` | Abilita DNS attivo |
| `-brute` | Brute forcing DNS |
| `-src` | Mostra la fonte del dato |
| `-timeout` | Timeout in minuti |

---

## 🎯 Workflow Bug Bounty

```bash
# 1. Enumeration passiva (sicura)
amass enum -passive -d target.com -o passive_subs.txt

# 2. Attiva con brute force
amass enum -active -brute -d target.com -w dns_wordlist.txt -o active_subs.txt

# 3. Trova asset dell'organizzazione
amass intel -org "Target Inc" -o org_domains.txt

# 4. Combina i risultati
cat passive_subs.txt active_subs.txt | sort -u > all_subdomains.txt

# 5. Risoluzione host attivi
cat all_subdomains.txt | httpx -silent > live_hosts.txt
```

---

## 📚 Risorse

- [OWASP Amass GitHub](https://github.com/owasp-amass/amass)
- [Amass User Guide](https://github.com/owasp-amass/amass/blob/master/doc/user_guide.md)

### Cheatsheets Collegate
- [Bug Bounty Methodology](../Bug-Bounty-Methodology/translations/README.it.md)
- [Subfinder](../Subfinder/translations/README.it.md)
- [httpx](../httpx/translations/README.it.md)

---

<p align="center">
  <b>🌐 Mappa la Superficie di Attacco!</b>
  <i>Amass - Lo strumento più completo per i sottodomini</i>
</p>
