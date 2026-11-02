# ☁️ Guia de referência de contorno da Cloudflare

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗██╗      ██████╗ ██╗   ██╗██████╗ ███████╗██╗      █████╗ ██████╗ ███████╗
  ██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗██╔════╝██║     ██╔══██╗██╔══██╗██╔════╝
  ██║     ██║     ██║   ██║██║   ██║██║  ██║█████╗  ██║     ███████║██████╔╝█████╗  
  ██║     ██║     ██║   ██║██║   ██║██║  ██║██╔══╝  ██║     ██╔══██║██╔══██╗██╔══╝  
  ╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝██║     ███████╗██║  ██║██║  ██║███████╗
   ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
                    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
                    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
                    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
                    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
                    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
                    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

> **Notas de versão e precisão:** As listas de IP do original são estáticas; a primeira inclui um intervalo mais amplo do que os blocos apresentados no final. Consultar os [intervalos oficiais](https://www.cloudflare.com/ips/) para uma comparação atual. Encontrar um candidato a origem não prova um contorno: o servidor pode filtrar a rede ou exigir [Authenticated Origin Pulls](https://developers.cloudflare.com/ssl/origin-configuration/authenticated-origin-pull/). Em HTTPS, alterar apenas Host não equivale a enviar o nome correto em SNI; a escolha do nome TLS depende do URL e das opções do cliente ([curl](https://curl.se/docs/manpage.html)). Cabeçalhos de IP, User-Agent, automatização de navegador e ferramentas de resolução de desafios não garantem contorno de regras, limitação de pedidos ou Turnstile. APIs de pesquisa, sintaxe Censys e ferramentas históricas dependem da versão e do serviço.

---

<a id="-detect-cloudflare"></a>
## 🎯 Detetar a Cloudflare

### Cabeçalhos
```bash
curl -I https://target.com

# Procurar:
cf-ray: XXXXX-YYY
server: cloudflare
cf-cache-status: DYNAMIC
cf-request-id: XXXX
```

### DNS
```bash
dig target.com

# Intervalos de IP da Cloudflare:
# 104.16.0.0/12
# 172.64.0.0/13
# 131.0.72.0/22
# 173.245.48.0/20
# 103.21.244.0/22
# 103.22.200.0/22
# 103.31.4.0/22
```

---

<a id="-find-origin-ip"></a>
## 🌐 Encontrar o IP de origem

### 1. Ferramentas de histórico DNS

| Serviço | URL |
|---------|-----|
| SecurityTrails | https://securitytrails.com |
| ViewDNS | https://viewdns.info/iphistory |
| DNSDumpster | https://dnsdumpster.com |
| CrimeFlare | http://www.crimeflare.org:82/cfs.html |
| CloudFlair | Ferramenta no GitHub |

```bash
# Utilizar o CloudFlair
python3 cloudflair.py target.com

# Bypass-Firewalls-by-DNS-History
./bypass-firewalls-by-DNS-history.sh target.com
```

### 2. Descoberta de subdomínios
```bash
# Vários subdomínios podem expor a origem
subfinder -d target.com | httpx -ip

# Subdomínios comuns que podem expor o IP de origem:
mail.target.com
ftp.target.com
cpanel.target.com
webmail.target.com
direct.target.com
origin.target.com
server.target.com
api.target.com
stage.target.com
dev.target.com
old.target.com
```

### 3. Pesquisa de certificados SSL
```bash
# Pesquisa no Censys.io
# Consulta: parsed.names: target.com AND NOT ip: 104.*

# Certificate Transparency (transparência de certificados)
curl -s "https://crt.sh/?q=%.target.com&output=json" | jq -r '.[].common_name' | sort -u

# Pesquisar no Shodan
shodan search "ssl.cert.subject.cn:target.com"
```

### 4. Análise de email
```bash
# Enviar um email para um endereço inexistente
# Verificar os cabeçalhos da mensagem de devolução

# Ou subscrever newsletters
# Procurar o IP de origem nos cabeçalhos Received:

# Procurar nos cabeçalhos:
Received: from [ORIGIN_IP]
X-Originating-IP: [IP]
X-PM-Message-Id: (sometimes contains origin)
```

### 5. Hash do favicon (Shodan)
```python
import mmh3
import requests
import codecs

# Calcular o hash do favicon
response = requests.get('https://target.com/favicon.ico')
favicon = codecs.encode(response.content, 'base64')
hash = mmh3.hash(favicon)
print(f"Favicon hash: {hash}")

# Pesquisar no Shodan: http.favicon.hash:{hash}
```

```bash
# Shodan CLI
shodan search "http.favicon.hash:HASH_HERE"
```

### 6. Histórico WHOIS
```bash
# Consultar o histórico WHOIS à procura de IP antigos
# whoxy.com
# domaintools.com
```

### 7. Código-fonte do site
```bash
# Procurar IP fixos no código em:
# - Ficheiros JavaScript
# - Comentários HTML
# - Endpoints de API
# - Mensagens de erro

curl -s https://target.com | grep -oE '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'

# Verificar os ficheiros JavaScript
curl -s https://target.com/main.js | grep -oE 'https?://[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'
```

### 8. Registos MX
```bash
dig MX target.com

# O servidor de email pode utilizar o mesmo IP da origem
nslookup mail.target.com
```

### 9. Registos SPF
```bash
dig TXT target.com | grep spf

# O SPF pode incluir o IP de origem
# v=spf1 ip4:1.2.3.4 include:_spf.google.com ~all
```

---

<a id="-direct-connection"></a>
## 🔥 Ligação direta

### Testar o IP encontrado
```bash
# Depois de encontrar um possível IP de origem:
curl -k -H "Host: target.com" https://ORIGIN_IP/

# Ou editar /etc/hosts
echo "ORIGIN_IP target.com" >> /etc/hosts

# Depois, navegar normalmente
curl https://target.com
```

### Confirmar a origem
```bash
# Verificar se o conteúdo é igual
diff <(curl -s https://target.com) <(curl -sk -H "Host: target.com" https://ORIGIN_IP/)

# Verificar o certificado SSL
openssl s_client -connect ORIGIN_IP:443 -servername target.com
```

---

<a id="-cloudflare-waf-bypass"></a>
## 🛡️ Contorno do WAF da Cloudflare

### Contorno da limitação do ritmo de pedidos
```http
# Alternar estes cabeçalhos
X-Forwarded-For: RANDOM_IP
CF-Connecting-IP: RANDOM_IP
True-Client-IP: RANDOM_IP
X-Real-IP: RANDOM_IP
```

### Contorno da proteção contra bots
```bash
# Utilizar o User-Agent de um navegador real
curl -A "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" https://target.com

# Utilizar curl-impersonate
curl_chrome116 https://target.com
```

### Contorno de Turnstile/desafios
```bash
# Utilizar automatização de navegador
# - Puppeteer + stealth plugin
# - Playwright
# - undetected-chromedriver

# FlareSolverr (Docker)
docker run -p 8191:8191 flaresolverr/flaresolverr
```

---

<a id="-tools"></a>
## 🛠️ Ferramentas

| Ferramenta | Finalidade |
|------|---------|
| **CloudFlair** | Encontrar a origem através do Censys |
| **CrimeFlare** | Base de dados de IP de origem |
| **Bypass-Firewalls-by-DNS-History** | Consulta do histórico DNS |
| **FlareSolverr** | Resolução de desafios de navegador |
| **curl-impersonate** | Reprodução da impressão digital TLS de um navegador |
| **CloudBunny** | Ferramenta de contorno da Cloudflare |

### Utilização do CloudFlair
```bash
git clone https://github.com/christophetd/CloudFlair
cd CloudFlair
pip install -r requirements.txt

# Definir as credenciais da API Censys
export CENSYS_API_ID=YOUR_ID
export CENSYS_API_SECRET=YOUR_SECRET

python cloudflair.py target.com
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Lista de verificação do IP de origem
```markdown
- [ ] Histórico DNS (SecurityTrails, ViewDNS)
- [ ] Subdomínios (mail, ftp, cpanel, dev)
- [ ] Certificados SSL (Censys, crt.sh)
- [ ] Shodan (hash do favicon, SSL)
- [ ] Cabeçalhos de email
- [ ] Registos MX/SPF
- [ ] Análise do código-fonte
- [ ] Histórico WHOIS
- [ ] Comparação de intervalos de IP (fora dos intervalos da Cloudflare)
```

### Intervalos de IP da Cloudflare
```
173.245.48.0/20
103.21.244.0/22
103.22.200.0/22
103.31.4.0/22
141.101.64.0/18
108.162.192.0/18
190.93.240.0/20
188.114.96.0/20
197.234.240.0/22
198.41.128.0/17
162.158.0.0/15
104.16.0.0/13
104.24.0.0/14
172.64.0.0/13
131.0.72.0/22
```

---

<a id="-resources"></a>
## 📚 Recursos

- [Intervalos de IP da Cloudflare](https://www.cloudflare.com/ips/)
- [Ferramenta CloudFlair](https://github.com/christophetd/CloudFlair)
- [CrimeFlare](http://www.crimeflare.org:82/cfs.html)
- [FlareSolverr](https://github.com/FlareSolverr/FlareSolverr)

---

<p align="center">
  <b>☁️ Encontre a origem!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
