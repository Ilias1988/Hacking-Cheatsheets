# 🎯 Hurtigreferanse for overtakelse av underdomener

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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

---

> **Begreper og presisering:** En dangling record er en DNS-oppføring uten forventet tilknyttet ressurs. Et fingeravtrykk eller en 404/NoSuchBucket-respons er en kandidat, ikke alene bevis på at ressursen kan registreres av andre. Tjenestene kan kreve domene- eller eierverifisering; originalens enkle oppskrifter og GitHub Pages-reponavn er ikke universelt gyldige. Se [GitHubs domeneverifisering](https://docs.github.com/en/pages/configuring-a-custom-domain-for-your-github-pages-site/verifying-your-custom-domain-for-github-pages). Cookie-/OAuth-konsekvenser avhenger også av avgrensning og konfigurasjon. Eksemplene og deres opprinnelige verdier er bevart.


## 🎯 Hva er overtakelse av underdomener?

**Overtakelse av underdomener** kan oppstå når et underdomene peker via CNAME til en fjernet tjeneste, og en angriper kan registrere den aktuelle ressursen og levere ondsinnet innhold.

### Konsekvenser
- 🔴 **Phishing** – publiser troverdige nettfiskingssider
- 🔴 **Tyveri av informasjonskapsler** – stjel kapsler som er satt for det overordnede domenet
- 🔴 **Kontoovertakelse** – fang opp OAuth-tilbakekall
- 🔴 **Omdømmeskade** – lever ondsinnet innhold

---

## 🔍 Metodikk for identifikasjon

### Trinn 1: Kartlegg underdomener
```bash
# Flere verktøy for bedre dekning
subfinder -d target.com -o subs.txt
amass enum -d target.com -o amass.txt
assetfinder --subs-only target.com >> subs.txt

# Slå sammen og fjern duplikater
cat subs.txt amass.txt | sort -u > all_subs.txt
```

### Trinn 2: Finn CNAME-oppføringer
```bash
# Kontroller DNS-oppføringer
cat all_subs.txt | while read sub; do
    cname=$(dig CNAME +short $sub)
    if [ ! -z "$cname" ]; then
        echo "$sub -> $cname"
    fi
done

# Eller bruk dnsrecon
dnsrecon -d target.com -t crt
```

### Trinn 3: Se etter CNAME-oppføringer uten tilknyttet ressurs
```bash
# Manuell kontroll
dig CNAME subdomain.target.com +short
# Returns: something.cloudfront.net
# Undersøk deretter om tjenesten er uregistrert

# httpx for HTTP-statuskoder
cat all_subs.txt | httpx -status-code -title -tech-detect -no-color
```

---

## 🛠️ Automatiserte verktøy

### Subjack
```bash
# Installer
go install github.com/haccer/subjack@latest

# Undersøk
subjack -w subdomains.txt -t 100 -timeout 30 -o results.txt -ssl

# Med fingeravtrykk
subjack -w subdomains.txt -t 100 -a -timeout 30 -o results.txt -ssl
```

### Nuclei (maler for overtakelse)
```bash
# Bruk av Nuclei-maler
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/ -o takeovers.txt

# Bestemte kontroller for overtakelse
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/subdomain-takeover.yaml
```

### Can-I-Take-Over-XYZ
```bash
# Se i GitHub-repoet etter fingeravtrykk
# https://github.com/EdOverflow/can-i-take-over-xyz

# Bruk fingeravtrykkene manuelt eller med automatiserte verktøy
```

---

## 📋 Sårbare tjenester og fingeravtrykk

### AWS S3
```
CNAME: bucket.s3.amazonaws.com
CNAME: bucket.s3-website-region.amazonaws.com

Fingerprint: "NoSuchBucket"
Fingerprint: "The specified bucket does not exist"

Overtakelse: Opprett en bøtte med samme navn
```

### AWS CloudFront
```
CNAME: xxxxx.cloudfront.net

Fingerprint: "Bad Request"
Fingerprint: "ERROR: The request could not be satisfied"

Overtakelse: Opprett en CloudFront-distribusjon med samme alternative domene
```

### GitHub Pages
```
CNAME: username.github.io
CNAME: organization.github.io

Fingerprint: "There isn't a GitHub Pages site here"

Overtakelse:
1. Opprett repoet username.github.io
2. Legg til en CNAME-fil med målunderdomenet
```

### Heroku
```
CNAME: xxx.herokuapp.com
CNAME: xxx.herokudns.com

Fingerprint: "No such app"
Fingerprint: "There's nothing here, yet"

Overtakelse: Opprett en Heroku-app med samme navn
```

### Azure
```
CNAME: xxx.azurewebsites.net
CNAME: xxx.cloudapp.azure.com
CNAME: xxx.blob.core.windows.net

Fingerprint: "404 - Web Site not found"

Overtakelse: Opprett en ressurs med samme navn
```

### Shopify
```
CNAME: shops.myshopify.com

Fingerprint: "Sorry, this shop is currently unavailable"
Fingerprint: "Only one step left"

Overtakelse: Opprett en Shopify-butikk og legg til et eget domene
```

### WordPress.com
```
CNAME: xxx.wordpress.com

Fingerprint: "Do you want to register *.wordpress.com?"

Overtakelse: Registrer underdomenet på WordPress.com
```

### Zendesk
```
CNAME: xxx.zendesk.com

Fingerprint: "Help Center Closed"
Fingerprint: "This help center no longer exists"

Overtakelse: Ikke alltid mulig – avhenger av Zendesk-oppsettet
```

### Fastly
```
CNAME: xxx.fastly.net

Fingerprint: "Fastly error: unknown domain"

Overtakelse: Legg domenet til en Fastly-tjeneste
```

### Pantheon
```
CNAME: xxx.pantheonsite.io

Fingerprint: "The gods are wise but do not know of the site"
Fingerprint: "404 error unknown site!"

Overtakelse: Legg nettstedet til i Pantheon
```

---

## 🔥 Trinn for utnyttelse

### Eksempel: Overtakelse av GitHub Pages

```bash
# 1. Finn et sårbart underdomene
dig CNAME blog.target.com
# Returnerer: target.github.io (men repoet finnes ikke)

# 2. Opprett et GitHub-repo
# Opprett: yourusername/target (eller org/target)

# 3. Aktiver GitHub Pages i repoinnstillingene

# 4. Legg til en CNAME-fil med innholdet:
blog.target.com

# 5. Vent på DNS-spredning

# 6. Kontroller overtakelsen
curl https://blog.target.com
```

### Eksempel: S3-overtakelse

```bash
# 1. Bekreft sårbarheten
dig CNAME assets.target.com
# Returns: assets-target.s3.amazonaws.com

curl http://assets-target.s3.amazonaws.com
# Returns: NoSuchBucket

# 2. Opprett en S3-bøtte (samme region)
aws s3 mb s3://assets-target --region us-east-1

# 3. Konfigurer bøtten
# Legg til nettstedshosting eller offentlig tilgang

# 4. Last opp innhold
echo "Subdomain Takeover PoC" > index.html
aws s3 cp index.html s3://assets-target/

# 5. Kontroller
curl http://assets.target.com
```

---

## 📊 Hurtigreferanse

### Identifikasjon av sårbar tjeneste

| Tjeneste | CNAME-mønster | Fingeravtrykk |
|---------|---------------|-------------|
| AWS S3 | `*.s3.amazonaws.com` | NoSuchBucket |
| CloudFront | `*.cloudfront.net` | Bad Request |
| GitHub | `*.github.io` | No GitHub Pages |
| Heroku | `*.herokuapp.com` | No such app |
| Azure | `*.azurewebsites.net` | 404 Web Site not found |
| Shopify | `shops.myshopify.com` | Shop unavailable |

### Sjekkliste for overtakelse

```markdown
- [ ] Kartlegg alle underdomener
- [ ] Identifiser CNAME-oppføringer
- [ ] Se etter oppføringer uten tilknyttet ressurs
- [ ] Sammenlign fingeravtrykk
- [ ] Kontroller at tjenesten er uregistrert
- [ ] Forsøk overtakelse innenfor avtalt omfang
- [ ] Dokumenter og rapporter
```

---

## 🛠️ Verktøyoversikt

| Verktøy | Formål |
|------|---------|
| **subjack** | Automatisk identifikasjon av mulig overtakelse |
| **nuclei** | Malbasert skanning |
| **can-i-take-over-xyz** | Referanse for fingeravtrykk |
| **dnsrecon** | DNS-kartlegging |
| **subfinder** | Oppdagelse av underdomener |
| **httpx** | Undersøkelse av HTTP-tjenester |

---

## 📚 Ressurser

- [can-i-take-over-xyz](https://github.com/EdOverflow/can-i-take-over-xyz)
- [Subjack](https://github.com/haccer/subjack)
- [Nuclei Takeover Templates](https://github.com/projectdiscovery/nuclei-templates/tree/main/takeovers)

---

<p align="center">
  <b>🎯 Undersøk overtakelse av underdomener!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
