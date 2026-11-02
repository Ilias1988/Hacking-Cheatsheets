# ☁️ Guía rápida de evasión de Cloudflare

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>


> **Precisiones del original:** Se conservan todos los comandos y rangos históricos. El primer bloque usa `104.16.0.0/12`; la [lista oficial de Cloudflare](https://www.cloudflare.com/ips/) publica `104.16.0.0/13` y `104.24.0.0/14`, por lo que deben consultarse los rangos vigentes. Una IP encontrada en certificados, correo o historial DNS es una candidata, no una prueba del origen. Conectar por IP y cambiar solo `Host` puede no seleccionar el mismo sitio TLS debido a SNI. Alterar cabeceras de IP no constituye una evasión general: importa qué intermediarios y cabeceras confía el servidor ([Cloudflare](https://developers.cloudflare.com/fundamentals/reference/http-headers/)). La automatización del navegador o un User-Agent concreto no garantiza superar Turnstile; el servidor debe validar sus tokens ([validación de Turnstile](https://developers.cloudflare.com/turnstile/get-started/server-side-validation/)). Las consultas y herramientas externas pueden depender de versiones y API antiguas.


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

---

## 🎯 Detectar Cloudflare

### Cabeceras
```bash
curl -I https://target.com

# Buscar:
cf-ray: XXXXX-YYY
server: cloudflare
cf-cache-status: DYNAMIC
cf-request-id: XXXX
```

### DNS
```bash
dig target.com

# Rangos de IP de Cloudflare:
# 104.16.0.0/12
# 172.64.0.0/13
# 131.0.72.0/22
# 173.245.48.0/20
# 103.21.244.0/22
# 103.22.200.0/22
# 103.31.4.0/22
```

---

## 🌐 Encontrar la IP del servidor de origen

### 1. Herramientas de historial DNS

| Servicio | URL |
|---------|-----|
| SecurityTrails | https://securitytrails.com |
| ViewDNS | https://viewdns.info/iphistory |
| DNSDumpster | https://dnsdumpster.com |
| CrimeFlare | http://www.crimeflare.org:82/cfs.html |
| CloudFlair | Herramienta de GitHub |

```bash
# Usar CloudFlair
python3 cloudflair.py target.com

# Bypass-Firewalls-by-DNS-History
./bypass-firewalls-by-DNS-history.sh target.com
```

### 2. Descubrimiento de subdominios
```bash
# Muchos subdominios pueden exponer el servidor de origen
subfinder -d target.com | httpx -ip

# Subdominios habituales con la IP del servidor de origen:
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

### 3. Búsqueda de certificados SSL
```bash
# Búsqueda en Censys.io
# Consulta: parsed.names: target.com AND NOT ip: 104.*

# Transparencia de certificados
curl -s "https://crt.sh/?q=%.target.com&output=json" | jq -r '.[].common_name' | sort -u

# Buscar en Shodan
shodan search "ssl.cert.subject.cn:target.com"
```

### 4. Análisis de correo electrónico
```bash
# Enviar un correo a una dirección inexistente
# Comprobar las cabeceras del mensaje devuelto

# O suscribirse a boletines
# Buscar la IP del origen en las cabeceras Received:

# Examinar las cabeceras:
Received: from [ORIGIN_IP]
X-Originating-IP: [IP]
X-PM-Message-Id: (sometimes contains origin)
```

### 5. Hash del favicon (Shodan)
```python
import mmh3
import requests
import codecs

# Calcular el hash del favicon
response = requests.get('https://target.com/favicon.ico')
favicon = codecs.encode(response.content, 'base64')
hash = mmh3.hash(favicon)
print(f"Favicon hash: {hash}")

# Buscar en Shodan: http.favicon.hash:{hash}
```

```bash
# Shodan CLI
shodan search "http.favicon.hash:HASH_HERE"
```

### 6. WHOIS histórico
```bash
# Consultar el WHOIS histórico en busca de IP antiguas
# whoxy.com
# domaintools.com
```

### 7. Código fuente del sitio web
```bash
# Buscar IP incrustadas en:
# - Archivos JavaScript
# - Comentarios HTML
# - Endpoints de API
# - Mensajes de error

curl -s https://target.com | grep -oE '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'

# Comprobar archivos JavaScript
curl -s https://target.com/main.js | grep -oE 'https?://[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'
```

### 8. Registros MX
```bash
dig MX target.com

# El servidor de correo puede compartir la IP del servidor de origen
nslookup mail.target.com
```

### 9. Registros SPF
```bash
dig TXT target.com | grep spf

# SPF puede incluir la IP del servidor de origen
# v=spf1 ip4:1.2.3.4 include:_spf.google.com ~all
```

---

## 🔥 Conexión directa

### Probar la IP encontrada
```bash
# Una vez localizada una posible IP del servidor de origen:
curl -k -H "Host: target.com" https://ORIGIN_IP/

# O editar /etc/hosts
echo "ORIGIN_IP target.com" >> /etc/hosts

# Después, navegar de la forma habitual
curl https://target.com
```

### Verificar el servidor de origen
```bash
# Comprobar si el contenido es el mismo
diff <(curl -s https://target.com) <(curl -sk -H "Host: target.com" https://ORIGIN_IP/)

# Comprobar el certificado SSL
openssl s_client -connect ORIGIN_IP:443 -servername target.com
```

---

## 🛡️ Evasión del WAF de Cloudflare

### Evasión de la limitación de tasa
```http
# Rotar estas cabeceras
X-Forwarded-For: RANDOM_IP
CF-Connecting-IP: RANDOM_IP
True-Client-IP: RANDOM_IP
X-Real-IP: RANDOM_IP
```

### Evasión de la protección contra bots
```bash
# Usar el User-Agent de un navegador real
curl -A "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" https://target.com

# Usar curl-impersonate
curl_chrome116 https://target.com
```

### Evasión de Turnstile y desafíos
```bash
# Usar automatización del navegador
# - Puppeteer y un plugin de ocultación
# - Playwright
# - undetected-chromedriver

# FlareSolverr (Docker)
docker run -p 8191:8191 flaresolverr/flaresolverr
```

---

## 🛠️ Herramientas

| Herramienta | Finalidad |
|------|---------|
| **CloudFlair** | Encontrar el servidor de origen mediante Censys |
| **CrimeFlare** | Base de datos de IP de servidores de origen |
| **Bypass-Firewalls-by-DNS-History** | Consulta del historial DNS |
| **FlareSolverr** | Resolución de desafíos de navegador |
| **curl-impersonate** | Huella TLS de navegador |
| **CloudBunny** | Herramienta de evasión de Cloudflare |

### Uso de CloudFlair
```bash
git clone https://github.com/christophetd/CloudFlair
cd CloudFlair
pip install -r requirements.txt

# Establecer las credenciales de la API de Censys
export CENSYS_API_ID=YOUR_ID
export CENSYS_API_SECRET=YOUR_SECRET

python cloudflair.py target.com
```

---

## 📊 Referencia rápida

### Lista de comprobación de IP del servidor de origen
```markdown
- [ ] Historial DNS (SecurityTrails, ViewDNS)
- [ ] Subdominios (mail, ftp, cpanel, dev)
- [ ] Certificados SSL (Censys, crt.sh)
- [ ] Shodan (hash del favicon, SSL)
- [ ] Cabeceras de correo electrónico
- [ ] Registros MX/SPF
- [ ] Análisis del código fuente
- [ ] WHOIS histórico
- [ ] Comparación de rangos de IP (fuera de los rangos de Cloudflare)
```

### Rangos de IP de Cloudflare
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

## 📚 Recursos

- [Rangos de IP de Cloudflare](https://www.cloudflare.com/ips/)
- [Herramienta CloudFlair](https://github.com/christophetd/CloudFlair)
- [CrimeFlare](http://www.crimeflare.org:82/cfs.html)
- [FlareSolverr](https://github.com/FlareSolverr/FlareSolverr)

---

<p align="center">
  <b>☁️ ¡Encuentra el servidor de origen!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
